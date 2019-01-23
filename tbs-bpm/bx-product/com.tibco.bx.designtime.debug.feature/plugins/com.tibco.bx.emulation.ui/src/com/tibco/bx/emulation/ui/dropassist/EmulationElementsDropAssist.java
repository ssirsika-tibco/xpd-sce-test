package com.tibco.bx.emulation.ui.dropassist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.CompositeEMFOperation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationElement;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.ui.actions.EmulationCommandHelper;
import com.tibco.bx.emulation.ui.util.EmulationUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class EmulationElementsDropAssist extends CommonDropAdapterAssistant{

	
	public IStatus handleDrop(CommonDropAdapter dropAdapter,
			DropTargetEvent dropTargetEvent, Object target) {
		ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
		if(dropAdapter.getCurrentTarget() != null && dropTargetEvent.data != null 
				&& (selection instanceof TreeSelection)){
			CompositeEMFOperation compositeEMFOperation = null;
			if(target instanceof IFile && EmulationCoreActivator.EMULATION_FILE_EXTENSION.equalsIgnoreCase(((IFile)target).getFileExtension())){
				WorkingCopy workingCopy = XpdResourcesPlugin.getDefault().getWorkingCopy((IFile)target);
	            Collection<ProcessNode> collection = new ArrayList<ProcessNode>();
                ProcessNode node = null;
                List objects = ((TreeSelection)selection).toList();
                for (Object object : objects) {
                	node = (ProcessNode)EcoreUtil.copy((ProcessNode)object);
                	collection.add(node);
				}
                EmulationData emulationData = (EmulationData)workingCopy.getRootElement();
            	EditingDomain editingDomain = workingCopy.getEditingDomain();
            	compositeEMFOperation = new CompositeEMFOperation((TransactionalEditingDomain)editingDomain, Messages.getString("DropProcessNodesAction_LABEL")); //$NON-NLS-1$
            	compositeEMFOperation.add(EmulationCommandHelper.getProcessNodesAddOperation(emulationData, collection));
            	compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, objects));
                    
			}else{//target is a ProcessNode
				ProcessNode processNode = (ProcessNode)target;
				EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(processNode.eContainer());
				compositeEMFOperation = new CompositeEMFOperation((TransactionalEditingDomain)editingDomain, Messages.getString("DropEmulationElementsAction_LABEL")); //$NON-NLS-1$
				List objects = ((TreeSelection)selection).toList();
				for (Object object : objects) {
	               if(object instanceof Input){
	            	   if(processNode.getInput() == null){
	            		   compositeEMFOperation.add(EmulationCommandHelper.getInOutCreateOperation(processNode, (Input)EcoreUtil.copy((Input)object)));
	            		   compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, (Input)object));
	            	   }else{
	            		  if(openConfirmDialog((EmulationElement)object)){
	            			  compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, processNode.getInput()));
	            			  compositeEMFOperation.add(EmulationCommandHelper.getInOutCreateOperation(processNode, (Input)EcoreUtil.copy((Input)object)));
		            		  compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, (Input)object));
	            		  }
	            	   }   
	               }else if(object instanceof Output){
	            	   if(processNode.getOutput() == null){
	            		   compositeEMFOperation.add(EmulationCommandHelper.getInOutCreateOperation(processNode, (Output)EcoreUtil.copy((Output)object)));
	            		   compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, (Output)object));
	            	   }else{
	            		  if(openConfirmDialog((EmulationElement)object)){
	            			  compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, processNode.getOutput()));
	            			  compositeEMFOperation.add(EmulationCommandHelper.getInOutCreateOperation(processNode, (Output)EcoreUtil.copy((Output)object)));
		            		  compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, (Output)object));
	            		  }
	            	   }   
	               }else if(object instanceof Testpoint){
	            	   Testpoint testpoint = EmulationUtil.getTestpointById(processNode, ((Testpoint)object).getId());
	            	   if(testpoint == null){
	            		   compositeEMFOperation.add(EmulationCommandHelper.getTestpointAddOperation(processNode, (Testpoint)EcoreUtil.copy((Testpoint)object)));
	            		   compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, (Testpoint)object));
	            	   }else{
	            		  if(openConfirmDialog((EmulationElement)object)){
	            			  compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, testpoint));
	            			  compositeEMFOperation.add(EmulationCommandHelper.getTestpointAddOperation(processNode, (Testpoint)EcoreUtil.copy((Testpoint)object)));
		            		  compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, (Testpoint)object));
	            		  }
	            	   }   
	               }else if(object instanceof Assertion){
	            	   Assertion assertion = EmulationUtil.getAssertionById(processNode, ((Assertion)object).getId());
	            	   if(assertion == null){
	            		   compositeEMFOperation.add(EmulationCommandHelper.getAssertionAddOperation(processNode, (Assertion)EcoreUtil.copy((Assertion)object)));
	            		   compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, (Assertion)object));
	            	   }else{
	            		  if(openConfirmDialog((EmulationElement)object)){
	            			  compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, assertion));
	            			  compositeEMFOperation.add(EmulationCommandHelper.getAssertionAddOperation(processNode, (Assertion)EcoreUtil.copy((Assertion)object)));
		            		  compositeEMFOperation.add(EmulationCommandHelper.getDeleteOperation(editingDomain, (Assertion)object));
	            		  }
	            	   }   
	               }
				}  
			}
			if(compositeEMFOperation != null){
				try {
	     			OperationHistoryFactory.getOperationHistory().execute(compositeEMFOperation, null, null);
	     			return Status.OK_STATUS;
	     		} catch (ExecutionException e) {
	     			EmulationUIActivator.log(e);
	     		}
			}
		}
		return Status.CANCEL_STATUS;
	}

	public IStatus validateDrop(Object target, int operation,TransferData transferType) {
		if(isSupportedType(transferType)){
			ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
			if(selection instanceof TreeSelection)
            {
				List objects = ((TreeSelection)selection).toList();
				if(EmulationUIUtil.canDrop(objects, target)){
            		return Status.OK_STATUS;
            	}
            }
		}
		return Status.CANCEL_STATUS;
	}
	
	private boolean openConfirmDialog(EmulationElement element){
		String title = Messages.getString("DropConfirmDialog_TITLE"); //$NON-NLS-1$
		String name = null;
		if (element instanceof Input) {
			name = "Input";//$NON-NLS-1$
		}else if (element instanceof Output) {
			name = "Output";//$NON-NLS-1$
		}else if (element instanceof NamedElement) {
			name = ((NamedElement)element).getName();
		}
		String message = String.format(Messages.getString("DropConfirmDialog_MESSAGE"),//$NON-NLS-1$
				new Object[] {name});
		return MessageDialog.openConfirm(this.getShell(), title, message);
	}
}
