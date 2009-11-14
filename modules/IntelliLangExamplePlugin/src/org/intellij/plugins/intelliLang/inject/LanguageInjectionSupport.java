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
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Factory;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.ui.SimpleColoredText;
import com.intellij.util.Consumer;
import org.intellij.plugins.intelliLang.Configuration;
import org.intellij.plugins.intelliLang.inject.config.BaseInjection;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Gregory.Shrago
 */
public interface LanguageInjectionSupport {
  ExtensionPointName<LanguageInjectionSupport> EP_NAME = ExtensionPointName.create("org.intellij.intelliLang.languageSupport");

  Key<Boolean> HAS_UNPARSABLE_FRAGMENTS = Key.create("HAS_UNPARSABLE_FRAGMENTS");

  @NonNls String XML_SUPPORT_ID = "xml";
  @NonNls String JAVA_SUPPORT_ID = "java";

  @NonNls
  @NotNull
  String getId();

  @NotNull
  Class[] getPatternClasses();

  boolean useDefaultInjector(final PsiElement host);

  boolean addInjectionInPlace(final Language language, final PsiLanguageInjectionHost psiElement);

  boolean removeInjectionInPlace(final PsiLanguageInjectionHost psiElement);

  boolean editInjectionInPlace(final PsiLanguageInjectionHost psiElement);

  BaseInjection createInjection(final Element element);

  void setupPresentation(final BaseInjection injection, final SimpleColoredText presentation, final boolean isSelected);

  Configurable[] createSettings(final Project project, final Configuration configuration);

  AnAction[] createAddActions(final Project project, final Consumer<BaseInjection> consumer);

  AnAction createEditAction(final Project project, final Factory<BaseInjection> producer);
}
