package com.tibco.bx.emulation.ui.actions;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;

import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.actions.AbstractHandleTestpointsAction;
import com.tibco.bx.emulation.ui.dialogs.ProcessNodesSelectionDialog;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class LoadEmulationFileAction extends AbstractHandleTestpointsAction {
	
	public void run(IAction action) {
		if(getEmulationView() == null) return;
		ProcessNodesSelectionDialog dialog = new ProcessNodesSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot());
       
        if (dialog.open() == IDialogConstants.OK_ID) {
        	Object[] results = dialog.getResult();
        	if(results.length > 0){
				EmulationData currentData = EmulationCacheManager.getDefault().getCurrentEmulationData();
				 EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(currentData);
				 EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getEmulationData_ProcessNodes();
				 Collection toRemoveList = new ArrayList();
				 for(ProcessNode processNode : currentData.getProcessNodes()){
					 if(hasDuplicatedProcessNode(processNode, results)){
						 toRemoveList.add(processNode);
					 }
				 }
				 Collection toAdd = new ArrayList();
				 for (int i = 0; i < results.length; i++) {
					 toAdd.add(EcoreUtil.copy((EObject)results[i]));
				}
				CompoundCommand compoundCommand = new CompoundCommand();
				compoundCommand.append(RemoveCommand.create(editingDomain, currentData, eStructuralFeature, toRemoveList));
				compoundCommand.append(AddCommand.create(editingDomain, currentData, eStructuralFeature, toAdd));
				editingDomain.getCommandStack().execute(compoundCommand);
        		 
        	}
        }
	}

	private boolean hasDuplicatedProcessNode(ProcessNode processNode, Object[] processNodes){
		for (int i = 0; i < processNodes.length; i++) {
			if(((ProcessNode)processNodes[i]).getId().equals(processNode.getId()))
				return true;
		}
		return false;
	}
	
	protected boolean isEnabled() {
		boolean hasEmulationData = EmulationCacheManager.getDefault().getCurrentEmulationData() != null;
		return hasEmulationData && (getEmulationView() != null);
	}

}
