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
package com.intellij.spellchecker.tokenizer;

import com.intellij.lang.Language;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlText;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 *
 * @author shkate@jetbrains.com
 */
public class HtmlSpellcheckingStrategy extends SpellcheckingStrategy {
  @NotNull
  @Override
    public Tokenizer getTokenizer(PsiElement element) {
      if (element instanceof PsiComment) return new CommentTokenizer();
      if (element instanceof XmlAttributeValue) return new XmlAttributeTokenizer();
      if (element instanceof XmlText) return new XmlTextTokenizer();
      return new Tokenizer();
    }

  @NotNull
  @Override
  public Language getLanguage() {
    return Language.findLanguageByID("HTML");
  }
}