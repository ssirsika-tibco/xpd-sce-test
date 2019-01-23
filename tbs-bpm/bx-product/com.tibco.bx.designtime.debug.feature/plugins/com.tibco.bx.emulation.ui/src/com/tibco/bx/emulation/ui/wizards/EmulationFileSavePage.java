package com.tibco.bx.emulation.ui.wizards;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.Messages;
public class EmulationFileSavePage extends EmulationAbstractFileCreationPage {

	EmulationFileSavePage(IEmulationWizard parentWizard, IStructuredSelection selection) {
		super(parentWizard, selection); //$NON-NLS-1$
		//user must call setFileExtension() and then call init();
		setFileExtension(EmulationCoreActivator.EMULATION_FILE_EXTENSION);
		init(selection);
		setTitle(Messages.getString("EmulationFileCreationPage_TITLE")); //$NON-NLS-1$
		setDescription(Messages.getString("EmulationFileCreationPage_DESC")); //$NON-NLS-1$
	}
	
	protected EObject getInitialContents(){
		List<ProcessNode> pList = null;
		IWizardPage nextPage = getNextPage();
		if(nextPage != null && nextPage instanceof ProcessNodesSelectionPage){
			pList = ((ProcessNodesSelectionPage)nextPage).getAllChecked();
		}
		
		EmulationData data = EmulationFactory.eINSTANCE.createEmulationData();
		for(ProcessNode node : pList){
			data.getProcessNodes().add(node);
		}
		return data;
	}
	
}