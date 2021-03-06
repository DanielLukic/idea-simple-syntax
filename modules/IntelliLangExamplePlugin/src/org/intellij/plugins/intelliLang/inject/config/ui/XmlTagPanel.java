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
package org.intellij.plugins.intelliLang.inject.config.ui;

import com.intellij.openapi.project.Project;
import org.intellij.plugins.intelliLang.inject.config.XmlTagInjection;

import javax.swing.*;

public class XmlTagPanel extends AbstractInjectionPanel<XmlTagInjection> {

  // read by reflection
  LanguagePanel myLanguagePanel;
  TagPanel myPanel;
  AdvancedPanel myAdvancedPanel;

  private JPanel myRoot;

  public XmlTagPanel(XmlTagInjection injection, Project project) {
    super(injection, project);
    $$$setupUI$$$(); // see IDEA-9987

    init(injection.copy());
  }

  protected void apply(XmlTagInjection other) {
    // nothing to do, TagPanel.apply() already does this
  }

  protected void resetImpl() {
    // same here^
  }

  public JPanel getComponent() {
    return myRoot;
  }

  private void createUIComponents() {
    myLanguagePanel = new LanguagePanel(myProject, myOrigInjection);
    myPanel = new TagPanel(myProject, myOrigInjection);
    myAdvancedPanel = new AdvancedPanel(myProject, myOrigInjection);
  }

  private void $$$setupUI$$$() {
  }
}
