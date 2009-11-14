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
package com.intellij.lang.properties.editor;

import com.intellij.ide.structureView.newStructureView.StructureViewComponent;
import com.intellij.lang.properties.PropertiesBundle;
import com.intellij.lang.properties.structureView.GroupByWordPrefixes;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.ComboBoxAction;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.GuiUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cdr
 */
public class PropertiesGroupingStructureViewComponent extends StructureViewComponent {
  protected PropertiesGroupingStructureViewComponent(Project project,
                                                  FileEditor editor,
                                                  PropertiesGroupingStructureViewModel structureViewModel) {
    super(editor, structureViewModel, project);
  }

  protected ActionGroup createActionGroup() {
    DefaultActionGroup actionGroup = (DefaultActionGroup)super.createActionGroup();
    actionGroup.add(new ChangeGroupSeparatorAction());
    return actionGroup;
  }

  private class ChangeGroupSeparatorAction extends ComboBoxAction {
    private DefaultActionGroup myActionGroup;
    // separator -> presentable text
    private final Map<String,String> myPredefindedSeparators = new LinkedHashMap<String, String>();
    private JPanel myPanel;

    public ChangeGroupSeparatorAction() {
      myPredefindedSeparators.put(".",".");
      myPredefindedSeparators.put("_","__");
      myPredefindedSeparators.put("/","/");
      String currentSeparator = getCurrentSeparator();
      if (!myPredefindedSeparators.containsKey(currentSeparator)) {
        myPredefindedSeparators.put(currentSeparator, currentSeparator);
      }
    }

    public final void update(AnActionEvent e) {
      Project project = PlatformDataKeys.PROJECT.getData(e.getDataContext());
      if (project == null) return;
      boolean isGroupActive = isActionActive(GroupByWordPrefixes.ID);
      String separator = getCurrentSeparator();
      Presentation presentation = e.getPresentation();
      presentation.setText(separator);
      presentation.setEnabled(isGroupActive);
      if (myPanel != null) {
        GuiUtils.enableChildren(myPanel, isGroupActive);
      }
    }

    private String getCurrentSeparator() {
      return ((PropertiesGroupingStructureViewModel)getTreeModel()).getSeparator();
    }

    @NotNull
    protected DefaultActionGroup createPopupActionGroup(JComponent button) {
      myActionGroup = new DefaultActionGroup();
      refillActionGroup();

      return myActionGroup;
    }

    private void refillActionGroup() {
      myActionGroup.removeAll();
      for (String separator : myPredefindedSeparators.keySet()) {
        String presentableText = myPredefindedSeparators.get(separator);
        myActionGroup.add(new SelectSeparatorAction(separator, presentableText));
      }
      myActionGroup.add(new SelectSeparatorAction(null, null));
    }

    public final JComponent createCustomComponent(Presentation presentation) {
      myPanel = new JPanel(new GridBagLayout());
      myPanel.add(new JLabel(PropertiesBundle.message("properties.structure.view.group.by.label")),
                  new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                                         new Insets(0, 5, 0, 0), 0, 0));
      myPanel.add(super.createCustomComponent(presentation),
                  new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                                         new Insets(0, 0, 0, 0), 0, 0));
      return myPanel;
    }

    private final class SelectSeparatorAction extends AnAction {
      private final String myActionSeparator;

      public SelectSeparatorAction(String separator, final String presentableText) {
        super(separator == null ? PropertiesBundle.message("select.separator.action.with.empty.separator.name") : presentableText);
        myActionSeparator = separator;
      }

      public final void actionPerformed(AnActionEvent e) {
        String separator;
        if (myActionSeparator == null) {
          String[] strings = myPredefindedSeparators.keySet().toArray(new String[myPredefindedSeparators.size()]);
          String current = getCurrentSeparator();
          separator = Messages.showEditableChooseDialog(PropertiesBundle.message("select.property.separator.dialog.text"),
                                                        PropertiesBundle.message("select.property.separator.dialog.title"), Messages.getQuestionIcon(),
                                                        strings, current, null);
          if (separator == null) {
            return;
          }
          myPredefindedSeparators.put(separator,separator);
          refillActionGroup();
        }
        else {
          separator = myActionSeparator;
        }

        ((PropertiesGroupingStructureViewModel)getTreeModel()).setSeparator(separator);
        rebuild();
      }
    }

  }

  @NonNls
  public String getHelpID() {
    return "editing.propertyFile.bundleEditor";
  }
}

