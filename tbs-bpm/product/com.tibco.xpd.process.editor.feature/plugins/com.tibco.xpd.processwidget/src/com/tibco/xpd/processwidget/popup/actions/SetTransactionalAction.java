/**
 * 
 */
package com.tibco.xpd.processwidget.popup.actions;

import java.util.Iterator;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.ActionDelegate;

import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * @author aallway
 *
 */
public class SetTransactionalAction extends ActionDelegate {

	private CompoundCommand cmd;
	private EditingDomain	editingDomain;
	
	public void run(IAction action) {
		if (cmd != null && editingDomain != null) {
			editingDomain.getCommandStack().execute(cmd);
		}
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		boolean cmd_ok = true;
		
		if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() > 0) {
    
            	cmd = new CompoundCommand();
                boolean tt = true;
                
                for (Iterator iter = sel.iterator(); iter.hasNext();) {
                    Object obj = iter.next();
                    if (obj instanceof TaskEditPart) {
                        TaskAdapter adapter = (TaskAdapter) ((TaskEditPart) obj)
                                .getModelAdapter();
                        
                        if (adapter.getTaskType().isSubProcessType()) {
                        	TaskAdapter subproc = (TaskAdapter)adapter;

                        	// set to checked unless one is not already transactional.
	                        if (!subproc.getSubprocessIsTransactional()) {
	                            tt = false;
	                        }
                        } else {
                        	cmd_ok = false;
                        	break;
                        }
                    } else {
                    	cmd_ok = false;
                    	break;
                    }
                }

                // If everything is ok create the command to change the transactional
                // status of the selected subprocess activity(s)
                if (cmd_ok) {
	                action.setEnabled(true);
	                action.setChecked(tt);
	                

	                for (Iterator iter = sel.iterator(); iter.hasNext();) {
	                    Object obj = iter.next();
	                    if (obj instanceof TaskEditPart) {
	                        TaskAdapter subproc = (TaskAdapter) 
	                        				((TaskEditPart) obj).getModelAdapter();
	    
	                        editingDomain = ((TaskEditPart)obj).getEditingDomain(); 
	                        
	                        cmd.append(subproc.getSetSubProcessIsTransactionalCommand(editingDomain, 
	                        			!tt));
	             
	                    }
	                }
                }

            }
        }
		else {
        	cmd_ok = false;
		}
		
		if (!cmd_ok) {
			action.setEnabled(false);
	        action.setChecked(false);
			cmd = null;
			editingDomain = null;
		}
		
	}
	
	public void dispose() {
		cmd = null;
		editingDomain = null;

		super.dispose();
	}
}

