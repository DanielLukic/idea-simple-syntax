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

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.util.Trinity;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.util.PsiUtilBase;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Gregory.Shrago
 */
public class TemporaryPlacesInjector implements MultiHostInjector {

  @NotNull
  public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
    return Collections.singletonList(PsiLanguageInjectionHost.class);
  }

  public void getLanguagesToInject(@NotNull final MultiHostRegistrar registrar, @NotNull final PsiElement context) {
    final PsiLanguageInjectionHost host = (PsiLanguageInjectionHost)context;
    final PsiLanguageInjectionHost originalHost = PsiUtilBase.getOriginalElement(host, host.getClass());
    final List<TemporaryPlacesRegistry.TemporaryPlace> list = TemporaryPlacesRegistry.getInstance(context.getProject()).getTempInjectionsSafe(originalHost);
    for (final TemporaryPlacesRegistry.TemporaryPlace pair : list) {
      InjectorUtils.registerInjection(pair.language.getLanguage(), Collections.singletonList(
        Trinity.create(host, pair.language, ElementManipulators.getManipulator(host).getRangeInElement(host))), context.getContainingFile(),
                                            registrar);
    }
  }

}
