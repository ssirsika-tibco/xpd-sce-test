package com.tibco.bx.emulation.ui.actions;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;

import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class RemoveAllTestpointsAction extends SelectAllTestpointsAction {

	@Override
	public void run(IAction action) {
		if(getEmulationView() != null){
			EmulationData emulationData = getEmulationView().getEmulationData();
			if(emulationData != null){
				EList<ProcessNode> pnList = emulationData.getProcessNodes();
				EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(emulationData);
	        	EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getEmulationData_ProcessNodes();
	        	Collection commandCollection = new ArrayList();
       	     	commandCollection.addAll(pnList);
	        	editingDomain.getCommandStack().execute(RemoveCommand.create(editingDomain, emulationData, eStructuralFeature, commandCollection));
			}
		}
	}
	
}