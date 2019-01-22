
package com.tibco.bx.emulation.ui.navigator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.util.IOpenEventListener;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorPart;

import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationElement;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.ui.util.EmulationUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractWorkingCopySaveablesContentProvider;

public class EMNavigatorContentProvider extends AbstractWorkingCopySaveablesContentProvider{
	private static final String[] specialFolderFilter = { EmulationCoreActivator.EMULATION_SPECIAL_FOLDER_KIND };
	private final TransactionalAdapterFactoryContentProvider adapterFactoryContentProvider;
	private final IOpenEventListener openEventListener;
	private OpenStrategy openStrategy;
	
	public EMNavigatorContentProvider(){
		adapterFactoryContentProvider = new TransactionalAdapterFactoryContentProvider(
				XpdResourcesPlugin.getDefault().getEditingDomain(), 
				XpdResourcesPlugin.getDefault().getAdapterFactory());
		openEventListener = new IOpenEventListener(){

			 @Override
				public void handleOpen(SelectionEvent e) {
					 Object localObject = e.item;

				        if (localObject instanceof Widget) {
				          localObject = ((Widget)localObject).getData();
				        }

				        if (!(localObject instanceof NamedElement)) return;
				        
				        try {
							IEditorPart editorPart = EmulationUIUtil.openEmulationDiagramEditor((NamedElement)localObject);
							if(editorPart != null){
								EmulationUIUtil.goToEmulationElement(editorPart, (NamedElement)localObject);
							}
						} catch (Exception exception) {
							 Shell shell = 
						            (EMNavigatorContentProvider.this.getViewer() != null) ? 
						            		EMNavigatorContentProvider.this.getViewer().getTree().getShell() : 
						            XpdResourcesPlugin.getStandardDisplay().getActiveShell();
							IStatus status = EmulationUIActivator.newStatus(exception, exception.getMessage());
							ErrorDialog.openError(shell, 
									Messages.getString("EMNavigatorContentProvider_ErrorDialog_title"), //$NON-NLS-1$
									Messages.getString("EMNavigatorContentProvider_ErrorDialog_message"),//$NON-NLS-1$
									status);
						}
				}
			
		};
	}
	 protected Object[] doGetChildren(Object element) {
		 if (getViewer() != null && openStrategy == null) {
             this.openStrategy = new OpenStrategy(getViewer().getControl());
             this.openStrategy.addOpenListener(openEventListener);
           }
		 if(element instanceof IFile && ((IFile)element).getFileExtension().equalsIgnoreCase(specialFolderFilter[0])){
			 WorkingCopy workingcopy = getWorkingCopy((IResource)element);
		     org.eclipse.emf.ecore.EObject eobject = workingcopy.getRootElement();
		     return adapterFactoryContentProvider.getChildren(eobject);//ProcessNodes
		 }else if(element instanceof ProcessNode){
			 List<EmulationElement> list = new ArrayList<EmulationElement>();
			 Input input =  ((ProcessNode)element).getInput();
			 if(input != null) list.add(input);
			 list.addAll(((ProcessNode)element).getTestpoints());
			 list.addAll(((ProcessNode)element).getAssertions());
			 Output output = ((ProcessNode)element).getOutput();
			 if(output != null) list.add(output);
			 list.addAll(((ProcessNode)element).getIntermediateInputs());
			 list.addAll(((ProcessNode)element).getMultiInputNodes());
			return list.toArray();
		 }else{
			 return (Object[])null;
		 }
	}

	 protected Object doGetParent(Object element) {
		 Object object = null;
	        object = adapterFactoryContentProvider.getParent(element);
	        if(object instanceof Resource)
	            object = WorkspaceSynchronizer.getFile((Resource)object);
	        return object;
	 }

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	        if (getViewer() != viewer) {
	            if (this.openStrategy != null) {
	              this.openStrategy.removeOpenListener(openEventListener);
	              this.openStrategy = null;
	            }
	            if (viewer != null && viewer.getControl().getDisplay().getThread() == Thread.currentThread()) {
	              this.openStrategy = new OpenStrategy(viewer.getControl());
	              this.openStrategy.addOpenListener(openEventListener);
	            }
	          }
	        super.inputChanged(viewer, oldInput, newInput);
	}

	public void dispose() {
		
	}
	
	protected void doGetPipelinedChildren(Object aParent, Set theCurrentChildren) {
		 if(aParent instanceof IFile && ((IFile)aParent).getFileExtension().equalsIgnoreCase(specialFolderFilter[0]))
	        {
	            WorkingCopy workingcopy = getWorkingCopy((IResource)aParent);
	            org.eclipse.emf.ecore.EObject eobject = workingcopy.getRootElement();
	            if(eobject != null)
	            	theCurrentChildren.addAll(((EmulationData)eobject).getProcessNodes());//ProcessNodes
	        }
	}

	protected boolean doHasChildren(Object element) {
		 boolean flag = false;
	        if(element instanceof IFile)
	            flag = true;
	        else
	            flag = getChildren(element).length > 0;
	        return flag;
	}

	public java.lang.String[] getSpecialFolderKindInclusion() {
		return specialFolderFilter;
	}
}