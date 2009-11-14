/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.spellchecker.inspections;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.codeStyle.NameUtil;
import com.intellij.spellchecker.util.Strings;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shkate@jetbrains.com
 */
public class TextSplitter {

  @NonNls
  private static final Pattern NON_SPACE = Pattern.compile("\\S+");

  @NonNls
  private static final Pattern HTML = Pattern.compile("<(0)>");

  @NonNls
  /*private static final Pattern WORD = Pattern.compile("\\b\\p{L}+'?\\p{L}*\\b");*/
  private static final Pattern WORD = Pattern.compile("\\b\\p{Alpha}*'?\\p{Alpha}*");

  private static final Pattern EXTENDED_WORD = Pattern.compile("\\b\\p{Alpha}*'?\\p{Alpha}(_*\\p{Alpha})*");

  private static final String WORD_SPLITTER = "\\s+|<[^>]+>";


  @NonNls
  private static final Pattern URL = Pattern.compile("(https?|ftp|mailto)\\:\\/\\/");
  @NonNls
  private static final Pattern COMPLEX = Pattern.compile("(\\.[^\\.]+)|([@]+)");

  @NonNls
  private static final Pattern SPECIAL = Pattern.compile("^&\\p{Alnum}{4};");


  private TextSplitter() {
  }


  @Nullable
  public static List<CheckArea> splitText(@Nullable String text) {
    if (text == null || StringUtil.isEmpty(text)) {
      return null;
    }

    int i = Math.max(text.indexOf("<!--"), text.indexOf("<%--"));
    i = (i > -1) ? i + 4 : 0;
    List<CheckArea> results = new ArrayList<CheckArea>();
    String[] pieces = text.substring(i).split(WORD_SPLITTER);
    for (String s : pieces) {
      if (s.length() > 0) {
        int p1 = text.indexOf(s, i);
        TextRange range = TextRange.from(p1, s.length());
        List<CheckArea> areaList = splitNonSpace(text, range);
        if (areaList != null) {
          results.addAll(areaList);
        }
        i += (range.getEndOffset() - range.getStartOffset());
      }
    }
    return (results.size() == 0) ? null : results;
  }

  @Nullable
  private static List<CheckArea> splitNonSpace(String text, TextRange range) {
    String nonSpaceArea = text.substring(range.getStartOffset(), range.getEndOffset());
    if (URL.matcher(nonSpaceArea).find() || COMPLEX.matcher(nonSpaceArea).find()) {
      return null;
    }
    return splitWord(text, range);
  }


  @NotNull
  private static List<CheckArea> splitSimpleWord(String text, TextRange range) {
    List<CheckArea> results = new ArrayList<CheckArea>();
    if (text==null || range==null || range.getLength()<1){
      return results;
    }
    String word = text.substring(range.getStartOffset(), range.getEndOffset());
    String[] words = NameUtil.splitNameIntoWords(word);
    if (words == null || words.length==0) {
      return results;
    }

    if (words.length == 1) {
      Matcher matcher = WORD.matcher(words[0]);
      if (matcher.find()) {
        TextRange found = matcherRange(range, matcher);
        addWord(text, results, false, found);
      }
      return results;
    }

    boolean isCapitalized = Strings.isCapitalized(words[0]);
    boolean containsShortWord = containsShortWord(words);

    if (isCapitalized && containsShortWord) {
      results.add(new CheckArea(text, range, true));
      return results;
    }

    boolean isAllWordsAreUpperCased = isAllWordsAreUpperCased(words);
    int index = 0;
    for (String s : words) {
      int start = word.indexOf(s, index);
      int end = start + s.length();
      boolean isUpperCase = Strings.isUpperCase(s);
      boolean flag = (isUpperCase && !isAllWordsAreUpperCased) || isKeyword(s);
      Matcher matcher = WORD.matcher(s);
      if (matcher.find()) {
        TextRange found = matcherRange(subRange(range, start, end), matcher);
        addWord(text, results, flag, found);
      }
      index = end;
    }
    return results;

  }

  @Nullable
  private static List<CheckArea> splitWord(String text, TextRange range) {
    if (StringUtil.isEmpty(text) || range.getLength() <= 1) {
      return null;
    }

    List<CheckArea> results = new ArrayList<CheckArea>();
    String word = text.substring(range.getStartOffset(), range.getEndOffset());

    Matcher specialMatcher = SPECIAL.matcher(word);
    if (specialMatcher.find()) {
      TextRange found = matcherRange(range, specialMatcher);
      addWord(text, results, true, found);
      return results;
    }

    Matcher extendedMatcher = EXTENDED_WORD.matcher(word);
    if (extendedMatcher.find()) {
      TextRange found = matcherRange(range, extendedMatcher);
      results.addAll(splitSimpleWord(text, found));
    }

    return results;

  }

  private static void addWord(String text, List<CheckArea> results, boolean flag, TextRange found) {
    boolean tooShort = (found.getEndOffset() - found.getStartOffset()) <= 3;
    results.add(new CheckArea(text, found, flag || tooShort));
  }

  private static boolean isKeyword(String s) {
    return false;
  }

  private static boolean isAllWordsAreUpperCased(String[] words) {
    if (words == null) return false;
    for (String word : words) {
      if (!Strings.isUpperCase(word)) {
        return false;
      }
    }
    return true;
  }

  private static boolean containsShortWord(String[] words) {
    if (words == null) return false;
    for (String word : words) {
      if (word.length() < 2) {
        return true;
      }
    }
    return false;
  }

  @NotNull
  private static TextRange matcherRange(@NotNull TextRange range, @NotNull Matcher matcher) {
    return subRange(range, matcher.start(), matcher.end());
  }

  @NotNull
  private static TextRange subRange(@NotNull TextRange range, int start, int end) {
    return TextRange.from(range.getStartOffset() + start, end - start);
  }
}
