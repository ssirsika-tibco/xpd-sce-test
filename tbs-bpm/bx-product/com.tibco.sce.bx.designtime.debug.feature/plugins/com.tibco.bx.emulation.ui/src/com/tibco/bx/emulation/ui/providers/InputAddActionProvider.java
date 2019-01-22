package com.tibco.bx.emulation.ui.providers;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonActionProvider;

import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

public class InputAddActionProvider extends CommonActionProvider{
	ProcessNode processNode;
	private IWorkbenchPart part;
	
	public InputAddActionProvider() {
		this.part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = (IStructuredSelection) getContext()
		.getSelection();
		if (selection.size() == 1) {
		      Object object = selection.getFirstElement();
		      AdapterFactory localAdapterFactory = 
		          XpdResourcesPlugin.getDefault().getAdapterFactory();
		        IEditingDomainItemProvider localIEditingDomainItemProvider = 
		          (IEditingDomainItemProvider)localAdapterFactory.adapt(object, 
		          IEditingDomainItemProvider.class);

		        if (localIEditingDomainItemProvider != null) {
		          Collection localCollection = 
		            localIEditingDomainItemProvider.getNewChildDescriptors(object, 
		            XpdResourcesPlugin.getDefault().getEditingDomain(), null);
		          for (Iterator localIterator = localCollection.iterator(); localIterator.hasNext(); ) {
		        	  Object localObject2 = localIterator.next();
		        	  if (localObject2 instanceof CommandParameter) {
		                  CommandParameter localCommandParameter = 
		                    (CommandParameter)localObject2;

		                  if (localCommandParameter.getValue() instanceof Input) {
		                	  CreateChildAction createInputAction = new CreateChildAction(this.part, selection, localObject2);
		                	  createInputAction.setText(Messages.getString("CreateInputAction_LABEL")); //$NON-NLS-1$
		                	  createInputAction.setImageDescriptor(
		                			  EmulationUIActivator.getDefault().getImageRegistry().
		                			  getDescriptor(EmulationUIActivator.IMG_INPUT));
		                	  menu.appendToGroup("group.edit", createInputAction);//$NON-NLS-1$
		                  }
		               }
		          }
		        }
		          
		}
		      
	}
	
}
