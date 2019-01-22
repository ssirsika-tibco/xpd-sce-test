/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.popup.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;

/**
 * @author wzurek
 */
public class DeleteObject implements IActionDelegate {

    Command cmd = UnexecutableCommand.INSTANCE;

    private EditingDomain ed;

    /*
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        if (ed != null) {
            ed.getCommandStack().execute(cmd);
        }
        cmd = UnexecutableCommand.INSTANCE;
        ed = null;
    }

    /*
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        boolean success = false;

        ed = null;
        
        if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            cmd = new CompoundCommand();

            // Extract and validate list of edit parts.
            success = true;
            
            List editParts = new ArrayList();
            for (Iterator iter = sel.iterator(); iter.hasNext();) {
                Object obj = (Object) iter.next();

                if (obj instanceof ModelAdapterEditPart) {
                    ModelAdapterEditPart part = (ModelAdapterEditPart) obj;
                    editParts.add(part);

                    if (ed == null) {
                        ed = part.getEditingDomain();
                    }
                } else {
                    success = false;
                    break;
                }
            }

            if (success && ed != null) {
                // Reduce the list to the highest level edit parts selected
                // (i.e. if sub-task is selected inside selected embedded
                // sub-proc then it is removed from the list OR if activity
                // selected inside selected lane then it is removed from list).
                List parentList = ToolUtilities
                        .getSelectionWithoutDependants(editParts);

                if (parentList == null || parentList.isEmpty()) {
                    success = false;
                    
                } else {
                    // Add any missing attached events that are attached to selected tasks.
                    EditPartUtil.addAttachedEventsToEditParts(parentList);

                    // Ok, we have a list of top level objects without children.
                    // Seperate this into connections and everything else.
                    CompoundCommand delConnectionsCmd = new CompoundCommand();
                    CompoundCommand delObjectsCmd = new CompoundCommand();

                    // Only set generic 'Delete' label if there are multiple slections
                    // otherwise leave it to the the individual object's delete.
                    if (parentList.size() > 1) {
                        ((CompoundCommand)cmd).setLabel(Messages.DeleteObject_menu);
                    }

                    for (Iterator iter = parentList.iterator(); iter.hasNext();) {
                        ModelAdapterEditPart ep = (ModelAdapterEditPart) iter.next();
                        
                        if (ep instanceof BaseConnectionEditPart) {
                            delConnectionsCmd.append(ep.getModelAdapter().getDeleteCommand(ed));
                        } else {
                            delObjectsCmd.append(ep.getModelAdapter().getDeleteCommand(ed));
                        }
                    }

                    // Delete connections before anything else (if they're still
                    // there when connected object is deleted then we would end
                    // up trying to delete twice).
                    if (!delConnectionsCmd.isEmpty()) {
                        ((CompoundCommand)cmd).append(delConnectionsCmd);
                    }
                    
                    if (!delObjectsCmd.isEmpty()) {
                        ((CompoundCommand)cmd).append(delObjectsCmd);
                    }
                    
                    if (!cmd.canExecute()) {
                        success = false;
                    }
                }
            }
        }
    
        action.setEnabled(success);
        
        if (!success) {
            ed = null;
            cmd = UnexecutableCommand.INSTANCE;
        }
     
        return;
    }
    
}


