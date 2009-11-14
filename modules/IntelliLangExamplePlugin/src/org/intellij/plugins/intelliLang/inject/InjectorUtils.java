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

package org.intellij.plugins.intelliLang.inject;

import com.intellij.lang.Language;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.Trinity;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.xml.XmlElement;
import com.intellij.util.NotNullFunction;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.xml.util.XmlUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author Gregory.Shrago
 */
public class InjectorUtils {
  public static final Comparator<TextRange> RANGE_COMPARATOR = new Comparator<TextRange>() {
    public int compare(final TextRange o1, final TextRange o2) {
      if (o1.intersects(o2)) return 0;
      return o1.getStartOffset() - o2.getStartOffset();
    }
  };

  private InjectorUtils() {
  }

  public static void addPlaceSafe(MultiHostRegistrar registrar, String prefix, String suffix, PsiLanguageInjectionHost host, TextRange textRange) {
    registrar.addPlace(prefix, suffix, host, textRange);
  }

  public static String getUnescapedText(final PsiElement host, final String text) {
    if (host instanceof PsiLiteralExpression) {
      return StringUtil.unescapeStringCharacters(text);
    }
    else if (host instanceof XmlElement) {
      return XmlUtil.unescape(text);
    }
    else {
      return text;
    }
  }// Avoid sticking text and prefix/suffix together in a way that it would form a single token.
  // See http://www.jetbrains.net/jira/browse/IDEADEV-8302#action_111865
  // This code assumes that for the injected language a single space character is a token separator
  // that doesn't (significantly) change the semantics if added to the prefix/suffix
  //
  // NOTE: This does not work in all cases, such as string literals in JavaScript where a
  // space character isn't a token separator. See also comments in IDEA-8561
  public static void adjustPrefixAndSuffix(String text, StringBuilder prefix, StringBuilder suffix) {
    if (prefix.length() > 0) {
      if (!endsWithSpace(prefix) && !startsWithSpace(text)) {
        prefix.append(" ");
      }
      else if (endsWithSpace(prefix) && startsWithSpace(text)) {
        trim(prefix);
      }
    }
    if (suffix.length() > 0) {
      if (text.length() == 0) {
        // avoid to stick whitespace from prefix and suffix together
        trim(suffix);
      }
      else if (!startsWithSpace(suffix) && !endsWithSpace(text)) {
        suffix.insert(0, " ");
      }
      else if (startsWithSpace(suffix) && endsWithSpace(text)) {
        trim(suffix);
      }
    }
  }

  public static void trim(StringBuilder string) {
    while (startsWithSpace(string)) string.deleteCharAt(0);
    while (endsWithSpace(string)) string.deleteCharAt(string.length() - 1);
  }

  public static boolean startsWithSpace(CharSequence sequence) {
    final int length = sequence.length();
    return length > 0 && sequence.charAt(0) <= ' ';
  }

  public static boolean endsWithSpace(CharSequence sequence) {
    final int length = sequence.length();
    return length > 0 && sequence.charAt(length - 1) <= ' ';
  }

  public static void registerInjection(Language language, List<Trinity<PsiLanguageInjectionHost, InjectedLanguage, TextRange>> list, PsiFile containingFile, MultiHostRegistrar registrar) {
    // if language isn't injected when length == 0, subsequent edits will not cause the language to be injected as well.
    // Maybe IDEA core is caching a bit too aggressively here?
    if (language == null/* && (pair.second.getLength() > 0*/) {
      return;
    }
    boolean injectionStarted = false;
    for (Trinity<PsiLanguageInjectionHost, InjectedLanguage, TextRange> trinity : list) {
      final PsiLanguageInjectionHost host = trinity.first;
      if (host.getContainingFile() != containingFile) continue;

      final TextRange textRange = trinity.third;
      final InjectedLanguage injectedLanguage = trinity.second;

      if (!injectionStarted) {
        registrar.startInjecting(language);
        injectionStarted = true;
      }
      if (injectedLanguage.isDynamic()) {
        // Only adjust prefix/suffix if it has been computed dynamically. Otherwise some other
        // useful cases may break. This system is far from perfect still...
        final StringBuilder prefix = new StringBuilder(injectedLanguage.getPrefix());
        final StringBuilder suffix = new StringBuilder(injectedLanguage.getSuffix());
        adjustPrefixAndSuffix(getUnescapedText(host, textRange.substring(host.getText())), prefix, suffix);

        addPlaceSafe(registrar, prefix.toString(), suffix.toString(), host, textRange);
      }
      else {
        addPlaceSafe(registrar, injectedLanguage.getPrefix(), injectedLanguage.getSuffix(), host, textRange);
      }
    }
    if (injectionStarted) {
      registrar.doneInjecting();
    }
  }

  @NotNull
  public static Set<String> getActiveInjectionSupportIds() {
    return ContainerUtil.map2Set(getActiveInjectionSupports(), new NotNullFunction<LanguageInjectionSupport, String>() {
      @NotNull
      public String fun(final LanguageInjectionSupport support) {
        return support.getId();
      }
    });
  }

  public static LanguageInjectionSupport[] getActiveInjectionSupports() {
    return Extensions.getExtensions(LanguageInjectionSupport.EP_NAME);
  }

  @Nullable
  public static LanguageInjectionSupport findInjectionSupport(final String id) {
    return ContainerUtil.find(getActiveInjectionSupports(), new Condition<LanguageInjectionSupport>() {
      public boolean value(final LanguageInjectionSupport support) {
        return support.getId().equals(id);
      }
    });
  }
}
