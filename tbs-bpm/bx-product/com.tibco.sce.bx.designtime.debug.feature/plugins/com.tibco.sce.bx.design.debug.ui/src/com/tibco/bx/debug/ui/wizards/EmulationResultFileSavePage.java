package com.tibco.bx.debug.ui.wizards;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.wizards.IResultCandidate;
import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.wizards.EmulationAbstractFileCreationPage;
import com.tibco.bx.emulation.ui.wizards.IEmulationWizard;
public class EmulationResultFileSavePage extends EmulationAbstractFileCreationPage {

	public EmulationResultFileSavePage(IEmulationWizard parentWizard, IStructuredSelection selection) {
		super(parentWizard, selection); //$NON-NLS-1$
		//user must call setFileExtension() and then call init();
		setFileExtension(EmulationCoreActivator.EMULATION_FILE_EXTENSION);
		init(selection);
		setTitle(Messages.getString("EmulationResultFileSavePage_TITLE")); //$NON-NLS-1$
		setDescription(Messages.getString("EmulationResultFileSavePage_DESC")); //$NON-NLS-1$
	}
	
	protected EObject getInitialContents(){
		List<IResultCandidate> pList = null;
		IWizardPage nextPage = getNextPage();
		if(nextPage != null && nextPage instanceof ResultCandidatesSelectionPage){
			pList = ((ResultCandidatesSelectionPage)nextPage).getAllChecked();
		}
		
		EmulationData result = EmulationFactory.eINSTANCE.createEmulationData();
		for(IResultCandidate candidate : pList){
			ProcessNode node = (ProcessNode)EcoreUtil.copy(candidate.getProcessNode());
			if(node.getOutput().getId()!= null){
				result.getProcessNodes().add(node);
			}else{
				node.setOutput(null);
				result.getProcessNodes().add(node);
			}
		}
		
		return result;
	}
	
}