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
import org.jetbrains.annotations.Nullable;

public class CheckArea {

  private String text;
  private TextRange textRange;
  private boolean ignored;

  public CheckArea(String text, TextRange range) {
    this.text = text;
    textRange = range;
  }

  public CheckArea(String text, TextRange textRange, boolean ignored) {
    this.text = text;
    this.textRange = textRange;
    this.ignored = ignored;
  }

  public TextRange getTextRange() {
    return textRange;
  }


  public boolean isIgnored() {
    return ignored;
  }


  @Nullable
  public String getWord() {
    if (text == null || textRange == null) return null;
    return text.substring(textRange.getStartOffset(), textRange.getEndOffset());
  }

  @Override
  public String toString() {
    return "CheckArea{range = " + textRange + ", ignored=" + ignored + ", word=" + (getWord() != null ? getWord() : "") + '}';
  }
}
