/*
 * Copyright 2006 Sascha Weinreuter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.intellij.plugins.intelliLang.util;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.codeInspection.dataFlow.DfaUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.util.CachedValue;
import com.intellij.util.containers.ConcurrentHashMap;
import org.intellij.plugins.intelliLang.Configuration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * Computes the constant value of an expression while considering the substitution annotation for non-compile-time
 * constant expressions.
 * <p/>
 * This is a quite simplified implementation at the moment.
 */
public class SubstitutedExpressionEvaluationHelper {
  private static final Key<CachedValue<ConcurrentMap<PsiElement, Object>>> COMPUTED_MAP_KEY = Key.create("COMPUTED_MAP_KEY");

  private final PsiConstantEvaluationHelper myHelper;
  private final Configuration myConfiguration;

  public SubstitutedExpressionEvaluationHelper(final Project project) {
    myHelper = JavaPsiFacade.getInstance(project).getConstantEvaluationHelper();
    myConfiguration = Configuration.getInstance();
  }

  public Object computeExpression(final PsiExpression e, final boolean includeUncomputablesAsLiterals) {
    return computeExpression(e, includeUncomputablesAsLiterals, null);
  }

  public Object computeExpression(final PsiExpression e, final boolean includeUncomputablesAsLiterals, final List<PsiExpression> uncomputables) {
    final ConcurrentMap<PsiElement, Object> map = new ConcurrentHashMap<PsiElement, Object>();
    //if (true) return myHelper.computeConstantExpression(e, false);
    return myHelper.computeExpression(e, false, new PsiConstantEvaluationHelper.AuxEvaluator() {
      @Nullable
      public Object computeExpression(final PsiExpression o, final PsiConstantEvaluationHelper.AuxEvaluator auxEvaluator) {
        if (o instanceof PsiMethodCallExpression) {
          final PsiMethodCallExpression c = (PsiMethodCallExpression)o;
          final PsiMethod m = (PsiMethod)c.getMethodExpression().resolve();
          final PsiType returnType = m != null? m.getReturnType() : null;
          if (returnType != null && returnType != PsiType.VOID) {
            // find substitution
            final Object substituted = calcSubstituted(m);
            if (substituted != null) return substituted;
            //return returnType.getCanonicalText().equals(CommonClassNames.JAVA_LANG_STRING)? "\""+ o.getText()+"\"" : o.getText();
          }
        }
        else if (o instanceof PsiReferenceExpression) {
          final PsiElement resolved = ((PsiReferenceExpression)o).resolve();
          if (resolved instanceof PsiModifierListOwner) {
            // find substitution
            final Object substituted = calcSubstituted((PsiModifierListOwner)resolved);
            if (substituted != null) return substituted;
            if (resolved instanceof PsiVariable) {
              final Collection<PsiExpression> values = DfaUtil.getCachedVariableValues(((PsiVariable)resolved), o);
              // return the first computed value as far as we do not support multiple injection
              for (PsiExpression value : values) {
                final Object computedValue = auxEvaluator.computeExpression(value, this);
                if (computedValue != null) {
                  return computedValue;
                }
              }
            }
          }
        }
        if (uncomputables != null) uncomputables.add(o);
        if (includeUncomputablesAsLiterals) {
          final PsiExpression expression = o instanceof PsiMethodCallExpression ? ((PsiMethodCallExpression)o).getMethodExpression() : o;
          final String text = expression.getText().replaceAll("\\s", "");
          return "\"" + StringUtil.escapeStringCharacters(text) + "\"";
        }
        return null;
      }

      public ConcurrentMap<PsiElement, Object> getCacheMap(final boolean overflow) {
        return map;
        //return PsiManager.getInstance(project).getCachedValuesManager().getCachedValue(project, COMPUTED_MAP_KEY, PROVIDER, false);
      }
    });
  }

  @Nullable
  private Object calcSubstituted(final PsiModifierListOwner owner) {
    final PsiAnnotation annotation = AnnotationUtil.findAnnotation(owner, myConfiguration.getSubstAnnotationPair().second);
    if (annotation != null) {
      return AnnotationUtilEx.calcAnnotationValue(annotation, "value");
    }
    return null;
  }

  /**
   * Computes the value for the passed expression.
   *
   * @param e           The expression whose value to compute
   * @param nonConstant list that returns non-constant and non-substituted expressions
   * @return the computed value, or null if the expression isn't compile time constant and not susbtituted
   */
  @Nullable
  public static String computeExpression(@NotNull final PsiExpression e, @Nullable List<PsiExpression> nonConstant) {
    final Object result = new SubstitutedExpressionEvaluationHelper(e.getProject()).computeExpression(e, false, nonConstant);
    return result == null? null : String.valueOf(result);
  }
}
