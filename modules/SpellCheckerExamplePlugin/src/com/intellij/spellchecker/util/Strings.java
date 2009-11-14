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
package com.intellij.spellchecker.util;

import java.util.List;

/**
 * Text utility.
 */
public final class Strings {
  private Strings() {
  }

  public static boolean isCapitalized(String word) {
    if (word.length() == 0) return false;

    boolean lowCase = true;
    for (int i = 1; i < word.length() && lowCase; i++) {
      lowCase = Character.isLowerCase(word.charAt(i));
    }

    return Character.isUpperCase(word.charAt(0)) && lowCase;
  }

  public static boolean isUpperCase(String word) {
    boolean upperCase = true;
    for (int i = 0; i < word.length() && upperCase; i++) {
      upperCase = Character.isUpperCase(word.charAt(i));
    }

    return upperCase;
  }

  public static boolean isMixedCase(String word) {
    if (word.length() < 2) return false;

    String tail = word.substring(1);
    String lowerCase = tail.toLowerCase();
    return !tail.equals(lowerCase) && !isUpperCase(word);
  }

  public static String capitalize(String word) {
    if (word.length() == 0) return word;

    StringBuffer buf = new StringBuffer(word);
    buf.setCharAt(0, Character.toUpperCase(buf.charAt(0)));
    return buf.toString();
  }

  public static void capitalize(List<String> words) {
    for (int i = 0; i < words.size(); i++) {
      words.set(i, capitalize(words.get(i)));
    }
  }

  public static void upperCase(List<String> words) {
    for (int i = 0; i < words.size(); i++) {
      words.set(i, words.get(i).toUpperCase());
    }
  }
    

}
