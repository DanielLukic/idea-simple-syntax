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
package com.intellij.spellchecker.quickfixes;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.codeStyle.NameUtil;
import com.intellij.psi.codeStyle.SuggestedNameInfo;
import com.intellij.refactoring.rename.NameSuggestionProvider;
import com.intellij.spellchecker.SpellCheckerManager;

import java.util.*;


public class DictionarySuggestionProvider implements NameSuggestionProvider {
  private boolean active;

  public void setActive(boolean active) {
    this.active = active;
  }

  public SuggestedNameInfo getSuggestedNames(PsiElement element, PsiElement nameSuggestionContext, List<String> result) {
    assert result != null;
    if (!active) {
      return null;
    }
    String text = nameSuggestionContext.getText();
    if (nameSuggestionContext instanceof PsiNamedElement) {
      //noinspection ConstantConditions
      text = ((PsiNamedElement)element).getName();
    }
    if (text == null) {
      return null;
    }

    SpellCheckerManager manager = SpellCheckerManager.getInstance(element.getProject());

    String[] words = NameUtil.nameToWords(text);

    int index = 0;
    List[] res = new List[words.length];
    int i = 0;
    for (String word : words) {
      int start = text.indexOf(word, index);
      int end = start + word.length();
      if (!manager.hasProblem(word)) {
        List<String> variants = new ArrayList<String>();
        variants.add(word);
        res[i++] = variants;
      }
      else {
        List<String> variants = manager.getSuggestions(word);
        res[i++] = variants;
      }
      index = end;
    }

    String[] all = null;
    int counter[] = new int[i];
    int size = 1;
    for (int j = 0; j < i; j++) {
      size *= res[j].size();
    }
    all = new String[size];

    for (int k = 0; k < size; k++) {
      for (int j = 0; j < i; j++) {
        if (all[k] == null) {
          all[k] = "";
        }
        all[k] += res[j].get(counter[j]);
        counter[j]++;
        if (counter[j] >= res[j].size()) {
          counter[j] = 0;
        }
      }
    }

    Set<String> set = new TreeSet<String>();
    set.addAll(Arrays.asList(all));
    result.addAll(set);
    return null;
  }

  public Collection<LookupElement> completeName(PsiElement element, PsiElement nameSuggestionContext, String prefix) {
    return null;
  }
}
