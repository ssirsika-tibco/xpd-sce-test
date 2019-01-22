package com.tibco.bx.emulation.bpm.ui.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.bpm.core.common.TestpointElement;
import com.tibco.bx.emulation.bpm.ui.util.EmulationBPMUIUtil;
import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.core.IEmulationConstants;
import com.tibco.bx.emulation.core.common.ITestpointElement;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.actions.EmulationCommandHelper;
import com.tibco.bx.emulation.ui.dialogs.TestpointEditDialog;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

public class AddTestpointAction implements IActionDelegate{

	private TaskEditPart editPart;
	private EmulationData emulationData;
	private ProcessNode processNode;
	private Process process;
	public void run(IAction action) {
		Object model = editPart.getModel();
		if(model instanceof Activity){
			process = ((Activity)model).getProcess();
		}else{
			return;
		}
		processNode = EmulationBPMUIUtil.getProcessNode();
		if(processNode == null){
			emulationData = EmulationCacheManager.getDefault().getCurrentEmulationData();
			processNode = EmulationUtil.getProcessNodeFromCache(process.getId());
		}else{
			emulationData = (EmulationData)processNode.eContainer();
		}
		//TODO in debug status, if the processNode is not existed, create processNode and testpoint in one command
		ProcessNode processNode = getProcessNode(process);
		createTestpoint(processNode, model);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (((IStructuredSelection)selection).size() >= 1) {
			Object element = ((IStructuredSelection)selection).getFirstElement();
			if(element instanceof TaskEditPart){
				editPart=(TaskEditPart) element;
			}  	
		}
	}

	/*
	 * create a Testpoint
	 */
	private void createTestpoint(ProcessNode processNode, Object model){
		Testpoint testpoint = null;
		if(model instanceof Activity){
			testpoint = EmulationFactory.eINSTANCE.createTestpoint();
			testpoint.setName(((Activity)model).getName());
			testpoint.setId(((Activity)model).getId());
			testpoint.setLang(IEmulationConstants.JSCRIPT_LANGUAGE);
			testpoint.setExpression("");//$NON-NLS-1$
		}
		
		//if(emulationData == EmulationCacheManager.getDefault().getCurrentEmulationData()){
			//open TestpointEditDialog to edit created testpoint
			TestpointEditDialog dialog = new TestpointEditDialog(editPart
					.getViewer().getControl().getShell(), new TestpointElement(
					testpoint, processNode.getId(), processNode.getModelType()));
			if (dialog.open() == IDialogConstants.OK_ID) {
				ITestpointElement activityElement = (ITestpointElement) dialog
						.getResult()[0];
				testpoint.setExpression(activityElement.getValueString());
			}else{
				return;
			}
		//}
		IUndoableOperation operation = EmulationCommandHelper.getTestpointAddOperation(processNode, testpoint);
        try {
			OperationHistoryFactory.getOperationHistory().execute(operation, null, null);
		} catch (ExecutionException e) {
			EmulationUIActivator.log(e);
		}
        
        ((BpmnScrollingGraphicalViewer)editPart.getViewer()).select(editPart.getParent());
        ((BpmnScrollingGraphicalViewer)editPart.getViewer()).select(editPart);
	}
	
	private ProcessNode getProcessNode(Process process){
		if(processNode == null){
			processNode = EmulationFactory.eINSTANCE.createProcessNode();
			processNode.setId(ProcessUtil.getElementId(process));
			processNode.setName(ProcessUtil.getDisplayName(process));
			processNode.setModelType(ProcessUtil.getModelType(process));
			IUndoableOperation operation = EmulationCommandHelper.getProcessNodeAddOperation(emulationData, processNode);
	        try {
				OperationHistoryFactory.getOperationHistory().execute(operation, null, null);
			} catch (ExecutionException e) {
				EmulationUIActivator.log(e);
			}
		}
		return processNode;
	}
	
	
}
