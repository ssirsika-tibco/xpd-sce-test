package com.tibco.bx.emulation.ui.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

public class ProcessNodesCreationWizard extends Wizard implements IEmulationWizard {

	private IProject project;
	private WorkingCopy workingCopy;
	ProcessesSelectionPage page1;
	
	public void addPages() {
		page1 = new ProcessesSelectionPage(this);
		addPage(page1);
		setProject(project);
		page1.setFilter(getProcessIDs());
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		Object object = selection.getFirstElement();
		if(object instanceof IFile && EmulationCoreActivator.EMULATION_FILE_EXTENSION.equalsIgnoreCase(((IFile)object).getFileExtension())){
			project = ((IFile)object).getProject();
			workingCopy = XpdResourcesPlugin.getDefault().getWorkingCopy((IFile)object);
		}
		setWindowTitle(Messages.getString("ProcessNodesCreationWizard_TITLE"));//$NON-NLS-1$
		setDefaultPageImageDescriptor(EmulationUIActivator.getDefault().getImageRegistry().getDescriptor(EmulationUIActivator.IMG_PROCESSNODE));
	}
	
	public boolean performFinish() {
		List<EObject> list = page1.getAllChecked();
		addProcessNodes(list);
		return true;
	}

	@Override
	public boolean needsProgressMonitor() {
		return true;
	}
	
	public Object getInput() {
		return project;
	}

	public void setProject(IProject project) {
		if(page1 != null && this.project != project){
			this.project = project;
			page1.update(project);
		}
	}
	
	private List<String> getProcessIDs(){
		List<String> list = new ArrayList<String>();
		/* return empty list
		if(workingCopy != null){
			EmulationData emulationData = (EmulationData)workingCopy.getRootElement();
			if(emulationData != null){
				Iterator<ProcessNode> iterator = emulationData.getProcessNodes().iterator();
				while(iterator.hasNext()){
					list.add(iterator.next().getId());
				}
				
			}
			
		}*/
		return list;
	}
	
	private void addProcessNodes(List<EObject> list) {
		if (workingCopy == null)
			return;
		Iterator<EObject> iterator = list.iterator();
		ProcessNode node = null;
		EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getEmulationData_ProcessNodes();
		Collection<EObject> commandCollection = new ArrayList<EObject>();
		while (iterator.hasNext()) {
			EObject process = iterator.next();
			node = EmulationFactory.eINSTANCE.createProcessNode();
			node.setId(ProcessUtil.getElementId(process));
			node.setName(ProcessUtil.getDisplayName(process));
			node.setModelType(ProcessUtil.getModelType(process));
			commandCollection.add(node);
		}
		EditingDomain editingDomain = workingCopy.getEditingDomain();
		EmulationData emulationData = (EmulationData) workingCopy.getRootElement();
		editingDomain.getCommandStack().execute(
				AddCommand.create(editingDomain, emulationData, eStructuralFeature, commandCollection));
	}
}
