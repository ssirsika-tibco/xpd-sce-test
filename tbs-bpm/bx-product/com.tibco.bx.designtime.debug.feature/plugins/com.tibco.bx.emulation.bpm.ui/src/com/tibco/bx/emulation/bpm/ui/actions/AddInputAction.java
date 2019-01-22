package com.tibco.bx.emulation.bpm.ui.actions;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.wst.wsdl.Operation;

import com.tibco.bx.debug.bpm.core.BxProcessModelHandler;
import com.tibco.bx.debug.core.invoke.datamodel.ISOAPMessage;
import com.tibco.bx.debug.core.invoke.datamodel.LaunchWsdlOperationElement;
import com.tibco.bx.debug.core.invoke.transport.SOAPMessageBuilder;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.bpm.core.common.InOutElement;
import com.tibco.bx.emulation.bpm.core.common.ProcessVariableElement;
import com.tibco.bx.emulation.bpm.ui.util.EmulationBPMUIUtil;
import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.Parameter;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.ui.actions.EmulationCommandHelper;
import com.tibco.bx.emulation.ui.dialogs.SoapMessageEditDialog;
import com.tibco.bx.emulation.ui.dialogs.TestpointEditDialog;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerType;

public class AddInputAction implements IActionDelegate{

	private BaseFlowNodeEditPart editPart;
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
		if(processNode == null){//processNode will not be null because this command is invisible while debugging
			emulationData = EmulationCacheManager.getDefault().getCurrentEmulationData();//emulationData can't be null
			processNode = EmulationUtil.getProcessNodeFromCache(process.getId());
		}else{
			emulationData = (EmulationData)processNode.eContainer();
		}
		//TODO if the processNode is not existed, create processNode and Input in one command
		ProcessNode processNode = getProcessNode(process);
		createInput(processNode, model);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (((IStructuredSelection)selection).size() >= 1) {
			Object element = ((IStructuredSelection)selection).getFirstElement();
			if(element instanceof BaseFlowNodeEditPart){
				editPart =(BaseFlowNodeEditPart) element;
			}  	
		}
	}

	/*
	 * create an Input
	 */
	private void createInput(ProcessNode processNode, Object model){
		Input input = null;
		IInOutElement inOutElement = null;
		if(model instanceof Activity){
			String activityId = ((Activity)model).getId();
			if(processNode.getInput() ==null){
				input = EmulationFactory.eINSTANCE.createInput();
				input.setId(activityId);
				input.setName(((Activity)model).getName());
				inOutElement = new InOutElement(input, processNode.getId(), processNode.getModelType());
			}else{
				input = EmulationFactory.eINSTANCE.createMultiInput();
				input.setId(activityId);
				input.setName(((Activity)model).getName());
				inOutElement = new InOutElement(input, processNode, processNode.getModelType());
			}
		}
		
		
		if(isForWebService()){
			try {
				Operation operation = BxProcessModelHandler.getOperationFromWorkSpace((Activity)model);
				ISOAPMessage message = SOAPMessageBuilder.instance.buildSoapMessage(new LaunchWsdlOperationElement(operation), null, null);
				SoapMessageEditDialog dialog = new SoapMessageEditDialog(editPart
						.getViewer().getControl().getShell(), message == null? "" : message.toXML(),//$NON-NLS-1$
						Messages.getString("AddInputAction.SoapMessageEditDialog.label")); //$NON-NLS-1$
				if(dialog.open()== Dialog.OK){
					input.setSoapMessage(((String)dialog.getResult()[0]));
				}else{
					return;
				}
			} catch (ParserConfigurationException e) {
				EmulationUIActivator.log(e);
			}
			
		}else{
			TestpointEditDialog dialog = new TestpointEditDialog(editPart
					.getViewer().getControl().getShell(), inOutElement);
			if (dialog.open() == IDialogConstants.OK_ID) {
				InOutElement activityElement = (InOutElement) dialog
						.getResult()[0];
				ProcessVariableElement[] elements = activityElement.getVariableElements();
				for (int i = 0; i < elements.length; i++) {
					Parameter parameter = EmulationFactory.eINSTANCE.createParameter();
					parameter.setName(elements[i].getName());
					parameter.setId(((ProcessRelevantData)elements[i].getEMFCharacter()).getId());
					parameter.setValue(elements[i].getVariableValueString());
					input.getParameters().add(parameter);
				}
			}else{
				return;
			}
		}
		
		IUndoableOperation operation = EmulationCommandHelper.getInOutCreateOperation(processNode, input);
        try {
			OperationHistoryFactory.getOperationHistory().execute(operation, null, null);
		} catch (ExecutionException e) {
			EmulationUIActivator.log(e);
		}
        // to call ((BpmnScrollingGraphicalViewer)editPart.getViewer()).fireSelectionChanged();
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
	
	private boolean isForWebService(){
		Activity activity = (Activity)editPart.getModel();
		Event event = activity.getEvent();
		Implementation implementation = activity.getImplementation();
		if(event instanceof StartEvent){
			TriggerType triggerType = ((StartEvent)event).getTrigger();
			if (triggerType == TriggerType.MESSAGE_LITERAL){
				return true;
			}
		}else if(event instanceof EndEvent){
			//TODO
		}else if (implementation instanceof Task && ((Task)implementation).getTaskReceive() != null) {
			return true;
		}else if (implementation instanceof Task && ((Task)implementation).getTaskSend() != null) {
			//TODO
		}
		return false;
	}
}
