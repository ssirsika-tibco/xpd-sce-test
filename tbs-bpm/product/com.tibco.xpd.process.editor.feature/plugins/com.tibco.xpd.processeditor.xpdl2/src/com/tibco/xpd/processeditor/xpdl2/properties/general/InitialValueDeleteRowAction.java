/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.xpdExtension.InitialValues;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

class InitialValueDeleteRowAction extends TableDeleteAction {

    private EditingDomain editingDomain;

    public InitialValueDeleteRowAction(StructuredViewer viewer, String label,
            String tooltip, EditingDomain editingDomain) {
        super(viewer, label, tooltip);
        this.editingDomain = editingDomain;
    }

    @Override
    protected void deleteRows(IStructuredSelection selection) {
        if (selection.size() > 0) {
            ProcessRelevantData data =
                    (ProcessRelevantData) getViewer().getInput();

            CompoundCommand cmd = new LateExecuteCompoundCommand();
            cmd
                    .setLabel(Messages.ParameterPropertySection_DeleteFormalParameterCommand_msg);
            InitialValues initialValues =
                    (InitialValues) Xpdl2ModelUtil.getOtherElement(data,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_InitialValues());
            EList<?> values = initialValues.getValue();

            // Delete the data values for the selected labels rather because
            // the labels may be different (such as date / time or
            // translations for true/false).
            Table initialValueTable = (Table) getViewer().getControl();

            if (initialValueTable.getSelection() != null
                    && !(initialValueTable.getSelection().length > values
                            .size())) {
                for (TableItem item : initialValueTable.getSelection()) {
                    int index = initialValueTable.indexOf(item);
                    if (index <= values.size() - 1) {
                        Object obj = values.get(index);
                        cmd
                                .append(getDeleteParameterInitialValueCommand(editingDomain,
                                        data,
                                        obj));
                    }
                }
            }

            if (cmd.canExecute()) {
                editingDomain.getCommandStack().execute(cmd);

            }
        }
    }

    private Command getDeleteParameterInitialValueCommand(EditingDomain ed,
            ProcessRelevantData data, Object obj) {
        InitialValues initValues =
                (InitialValues) Xpdl2ModelUtil.getOtherElement(data,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_InitialValues());
        if (initValues != null) {
            CompoundCommand removeCommand = new CompoundCommand();
            removeCommand
                    .setLabel(Messages.ParameterPropertySection_RemoveInitialValueFromParameterCommand_msg);
            if (initValues.getValue().size() > 1) {

                removeCommand.append(RemoveCommand.create(ed,
                        initValues,
                        XpdExtensionPackage.eINSTANCE.getInitialValues_Value(),
                        obj));
            } else {
                removeCommand.append(Xpdl2ModelUtil
                        .getRemoveOtherElementCommand(ed,
                                data,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_InitialValues(),
                                initValues));
            }
            return removeCommand;

        }
        return null;
    }

}