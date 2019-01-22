/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event.actions;

import java.util.Iterator;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.event.AssociatedParameterSection;
import com.tibco.xpd.ui.properties.table.TableWithButtonsDeleteRowAction;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * Action to delete an associated parameter from either a Start or Intermediate
 * method. The action is required by the <code>TableWithButtons</code> to enable
 * the Delete button.
 * 
 * @author rsomayaj
 * 
 * 
 */
public class DeleteAssociatedParamAction extends
        TableWithButtonsDeleteRowAction {

    private final AssociatedParameterSection paramSection;

    private Object input;

    public DeleteAssociatedParamAction(StructuredViewer viewer,
            AssociatedParameterSection paramSection) {
        super(
                viewer,
                Messages.DeleteAssociatedParamAction_DeleteInterfaceAssociatedParam_label);
        this.paramSection = paramSection;
    }

    @Override
    protected void deleteRows(IStructuredSelection selection) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.DeleteAssociatedParamAction_CmdRemoveAssociatedParam_label);

        for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            cmd.append(RemoveCommand.create(paramSection.getEditingDomain(),
                    obj));
        }

        if (cmd.canExecute()) {
            paramSection.getEditingDomain().getCommandStack().execute(cmd);
        }
        Display.getCurrent().asyncExec(new Runnable() {

            public void run() {
                paramSection.doRefreshTabs();
            }
        });
    }

    @Override
    public void selectionChanged(IStructuredSelection selection) {
        super.selectionChanged(selection);
        for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            if (obj instanceof AssociatedParameter) {
                AssociatedParameter associatedParameter =
                        (AssociatedParameter) obj;

                if (input instanceof Activity) {
                    Activity activity = (Activity) input;
                    if (ProcessInterfaceUtil
                            .isEventImplemented((activity))) {
                        ProcessRelevantData param =
                                ProcessInterfaceUtil
                                        .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                        if (param.eContainer() instanceof ProcessInterface) {
                            setEnabled(false);
                        }
                    } else {
                        // MR 39918 - begin
                        Activity requestActivityForReplyActivity =
                                ReplyActivityUtil
                                        .getRequestActivityForReplyActivity(activity);
                        if (null != requestActivityForReplyActivity) {
                            setEnabled(false);
                        }
                        // MR 39918 - end
                    }
                }
            } else {
                setEnabled(false);
            }
        }
    }

    public void setInput(Object input) {
        this.input = input;

    }

}
