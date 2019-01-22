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
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

public class OutputAddActionProvider extends CommonActionProvider{
	ProcessNode processNode;
	private IWorkbenchPart part;

	@Override
	public void init(ICommonActionExtensionSite site) {
		super.init(site);
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

		                  if (localCommandParameter.getValue() instanceof Output) {
		                	  CreateChildAction createOutputAction = new CreateChildAction(this.part, selection, localObject2);
		                	  createOutputAction.setText(Messages.getString("CreateOutputAction_LABEL")); //$NON-NLS-1$
		                	  createOutputAction.setImageDescriptor(
		                			  EmulationUIActivator.getDefault().getImageRegistry().
		                			  getDescriptor(EmulationUIActivator.IMG_OUTPUT));
		                	  menu.appendToGroup("group.edit", createOutputAction);//$NON-NLS-1$
		                  }
		               }
		          }
		        }
		          
		}
		      
	}
	
}
