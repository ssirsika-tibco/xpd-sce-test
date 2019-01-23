package com.tibco.bx.emulation.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.bx.emulation.model.EmulationElement;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.util.EmulationUIUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class EmulationDeleteAction extends DeleteAction{

	public EmulationDeleteAction(TransactionalEditingDomain editingDomain) {
		super(editingDomain, true);
	}

	@Override
	public Command createCommand(Collection<?> selection) {
		Command command = super.createCommand(selection);
		TransactionalEditingDomain editingDomain = (TransactionalEditingDomain)getEditingDomain();
		IUndoableOperation operation = new EMFCommandOperation(editingDomain, command);
	    return new EMFOperationCommand(editingDomain, operation);
	}
	

	@Override
	public boolean updateSelection(IStructuredSelection selection) {
		List objects = selection.toList();
		return canDelete(objects) && super.updateSelection(selection);
	}
	
	public static boolean canDelete(Collection collection){
		EmulationElement first = null;
		boolean isProcessNodes = false;
		int inputNum = 0, outputNum = 0;
		List<String> ids = new ArrayList<String>(); 
		String containerId = null;
		if(collection.isEmpty()) return false;
		
		for (Object emulationElement : collection) {
			if(emulationElement instanceof EmulationElement && 
					((EmulationElement)emulationElement).eContainer() != null){// does not handle removed elements
				if(first == null){
					first = (EmulationElement)emulationElement;
					if(first instanceof ProcessNode){
						isProcessNodes = true;
					}
				}else if(!isProcessNodes && (emulationElement instanceof NamedElement) && !(emulationElement instanceof ProcessNode)){
					
				}else{
					return false;
				}
			}else{
				return false;
			}	
		}
		return true;
	}
}
