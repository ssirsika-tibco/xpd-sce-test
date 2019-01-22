package com.tibco.bx.emulation.ui.wizards;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.Messages;
public class EmulationFileCreationPage extends EmulationAbstractFileCreationPage {

	public EmulationFileCreationPage(IEmulationWizard parentWizard, IStructuredSelection selection) {
		super(parentWizard, selection); //$NON-NLS-1$
		//user must call setFileExtension() and then call init();
		setFileExtension(EmulationCoreActivator.EMULATION_FILE_EXTENSION);
		init(selection);
		setTitle(Messages.getString("EmulationFileCreationPage_TITLE")); //$NON-NLS-1$
		setDescription(Messages.getString("EmulationFileCreationPage_DESC")); //$NON-NLS-1$
	}
	
	protected EmulationData getInitialContents(){
		List<EObject> pList = null;
		IWizardPage nextPage = getNextPage();
		if(nextPage != null && nextPage instanceof ProcessesSelectionPage){
			pList = ((ProcessesSelectionPage)nextPage).getAllChecked();
		}
		
		EmulationData data = EmulationFactory.eINSTANCE.createEmulationData();
		for(EObject process : pList){
			ProcessNode node = EmulationFactory.eINSTANCE.createProcessNode();
			node.setId(ProcessUtil.getElementId(process));
			node.setName(ProcessUtil.getDisplayName(process));
			node.setModelType(ProcessUtil.getModelType(process));
			data.getProcessNodes().add(node);
		}
		return data;
	}
	
}