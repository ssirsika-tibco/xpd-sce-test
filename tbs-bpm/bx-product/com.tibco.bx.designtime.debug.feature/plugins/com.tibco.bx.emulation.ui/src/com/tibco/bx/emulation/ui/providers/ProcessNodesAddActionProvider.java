package com.tibco.bx.emulation.ui.providers;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.ui.wizards.ProcessNodesCreationWizard;

public class ProcessNodesAddActionProvider extends CommonActionProvider{

	Action createAction;
	@Override
	public void init(ICommonActionExtensionSite site) {
		super.init(site);
		createAction = new CreateProcessNodesAction(Messages.getString("CreateProcessNodesAction_LABEL"),//$NON-NLS-1$
				EmulationUIActivator.getDefault().getImageRegistry().getDescriptor(EmulationUIActivator.IMG_PROCESSNODE));
	}

	public void fillContextMenu(IMenuManager menu) {
		menu.appendToGroup("group.edit", createAction);//$NON-NLS-1$
		
	}
	
	class CreateProcessNodesAction extends Action {	
		public CreateProcessNodesAction(String text, ImageDescriptor image) {
			super(text, image);
		}

		@Override
		public void run() {
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			ISelection selection = window.getSelectionService().getSelection();
			IWorkbenchWizard wizard = new ProcessNodesCreationWizard();
			if (selection instanceof IStructuredSelection) {
				wizard.init(window.getWorkbench(),(IStructuredSelection) selection);
			} else {
				wizard.init(window.getWorkbench(), StructuredSelection.EMPTY);
			}
			WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
			dialog.create();
			dialog.open();
			
		}

		
	}
	
}
