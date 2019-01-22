package com.tibco.bx.emulation.ui.actions;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFCommandOperation;

import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.InOutDataType;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.Parameter;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class EmulationCommandHelper {

	public static IUndoableOperation getProcessNodesAddOperation(EmulationData emulationData, Collection<ProcessNode> collection){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(emulationData);
		EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getEmulationData_ProcessNodes();
        Command command = AddCommand.create(editingDomain, emulationData, eStructuralFeature, collection);
        EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
        operation.setLabel(Messages.getString("AddProcessNodesAction_LABEL")); //$NON-NLS-1$
        return operation;
	}
	
	public static IUndoableOperation getProcessNodeAddOperation(EmulationData emulationData, ProcessNode processNode){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(emulationData);
		EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getEmulationData_ProcessNodes();
        Command command = AddCommand.create(editingDomain, emulationData, eStructuralFeature, processNode);
        EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
        operation.setLabel(Messages.getString("AddProcessNodeAction_LABEL")); //$NON-NLS-1$
        return operation;
	}
	
	public static IUndoableOperation getDeleteOperation(EmulationData emulationData, Collection collection){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(emulationData);
		EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getEmulationData_ProcessNodes();
		Command command = RemoveCommand.create(editingDomain,emulationData,eStructuralFeature,collection);
		editingDomain.getCommandStack().execute(command);
		return null;
	}
	
	public static IUndoableOperation getDeleteOperation(EditingDomain editingDomain, Collection collection){
		Command command = DeleteCommand.create(editingDomain, collection);
    	IUndoableOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
    	return operation;
	}
	
	public static IUndoableOperation getDeleteOperation(EditingDomain editingDomain, EObject object){
		Command command = DeleteCommand.create(editingDomain, object);
    	IUndoableOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
    	return operation;
	}
	
	public static IUndoableOperation getTestpointAddOperation(ProcessNode processNode, Testpoint testpoint){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(processNode.eContainer());
        EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_Testpoints();
        Command command = AddCommand.create(editingDomain, processNode, eStructuralFeature, testpoint);
        EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
        operation.setLabel(Messages.getString("AddTestpointAction_LABEL")); //$NON-NLS-1$
        return operation;
	}
	
	public static IUndoableOperation getAssertionAddOperation(ProcessNode processNode, Assertion assertion){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(processNode.eContainer());
        EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_Assertions();
        Command command = AddCommand.create(editingDomain, processNode, eStructuralFeature, assertion);
        EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
        operation.setLabel(Messages.getString("AddAssertionAction_LABEL")); //$NON-NLS-1$
        return operation;
	}
	
	public static IUndoableOperation getSetTestpointExpressionOperation(Testpoint testpoint, String text){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(testpoint.eContainer());
        EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getTestpoint_Expression();
        Command command = SetCommand.create(editingDomain, testpoint, eStructuralFeature, text);
        EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
        operation.setLabel(Messages.getString("SetTestpointExpression_LABEL")); //$NON-NLS-1$
        return operation;
	}
	
	public static IUndoableOperation getInOutCreateOperation(ProcessNode processNode, InOutDataType inOut){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(processNode.eContainer());
        EStructuralFeature eStructuralFeature = null;
        Command command = null;
        	if(inOut instanceof Input){
        		if(inOut instanceof MultiInput){
        			eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_MultiInputNodes();
        			command = AddCommand.create(editingDomain, processNode, eStructuralFeature, inOut);
        		}else{
        			eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_Input() ;
        			command = SetCommand.create(editingDomain, processNode, eStructuralFeature, inOut);
        		}
        	}else{
        		eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_Output();
        		command = SetCommand.create(editingDomain, processNode, eStructuralFeature, inOut);
        	}
        
        EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
        if (inOut instanceof Input){ 
        	operation.setLabel(Messages.getString("CreateInputAction_LABEL"));//$NON-NLS-1$
        }else{
        	operation.setLabel(Messages.getString("CreateOutputAction_LABEL")); //$NON-NLS-1$
        }
        return operation;
	}
	
	public static IUndoableOperation getIntermediateInputAddOperation(ProcessNode processNode, IntermediateInput input){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(processNode.eContainer());
		EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_IntermediateInputs();
		Command command = AddCommand.create(editingDomain, processNode, eStructuralFeature, input);
		EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
        operation.setLabel(Messages.getString("AddIntermediateInputAction_LABEL")); //$NON-NLS-1$
        return operation;
	}
	
	public static IUndoableOperation getSetInputSoapMessageOperation(InOutDataType inOut, String text){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(inOut.eContainer());
        EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getInOutDataType_SoapMessage();
        Command command = SetCommand.create(editingDomain, inOut, eStructuralFeature, text);
        EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
        operation.setLabel(Messages.getString("SetSoapMessageAction_LABEL")); //$NON-NLS-1$
        return operation;
	}
	
	public static IUndoableOperation getSetIntermediateInputRequestMessageOperation(IntermediateInput intermediateInput, String text){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(intermediateInput.eContainer());
        EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getIntermediateInput_RequestMessage();
        Command command = SetCommand.create(editingDomain, intermediateInput, eStructuralFeature, text);
        EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
        operation.setLabel(Messages.getString("SetSoapMessageAction_LABEL")); //$NON-NLS-1$
        return operation;
	}
	
	public static IUndoableOperation getSetInOutOperation(InOutDataType inOut, List<Parameter> list){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(inOut.eContainer());
		EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getInOutDataType_Parameters();
		CompoundCommand command = new CompoundCommand();
		command.append(RemoveCommand.create(editingDomain, inOut, eStructuralFeature, inOut.getParameters()));
		if(list.size() > 0){
			command.append(AddCommand.create(editingDomain, inOut, eStructuralFeature, list));
		}
        EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
        operation.setLabel(Messages.getString("SetParametersAction_LABEL")); //$NON-NLS-1$
        return operation;
	}
	
	public static IUndoableOperation getSetOperation(ProcessNode processNode , Output out){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(processNode.eContainer());
		Command setCommand = SetCommand.create(editingDomain, processNode, EmulationPackage.eINSTANCE.getProcessNode_Output(), out);
		EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, setCommand);
		return operation;
	}
	
	public static IUndoableOperation getSetTestPointOperation(ProcessNode processNode, List<Testpoint> testpoints){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(processNode.eContainer());
		EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_Testpoints();
		CompoundCommand command = new CompoundCommand();
		
		command.append(RemoveCommand.create(editingDomain, processNode, eStructuralFeature, processNode.getTestpoints()));
		if(testpoints.size() > 0){
			command.append(AddCommand.create(editingDomain, processNode, eStructuralFeature, testpoints));
		}
        EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
        operation.setLabel(Messages.getString("SetParametersAction_LABEL")); //$NON-NLS-1$
        return operation;
	}
	
	public static IUndoableOperation getSetAssertOperation(ProcessNode processNode, EList<Assertion> assertions){
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(processNode.eContainer());
		EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getProcessNode_Assertions();
		CompoundCommand command = new CompoundCommand();
		
		command.append(RemoveCommand.create(editingDomain, processNode, eStructuralFeature, processNode.getTestpoints()));
		if(assertions.size() > 0){
			command.append(AddCommand.create(editingDomain, processNode, eStructuralFeature, assertions));
		}
        EMFCommandOperation operation = new EMFCommandOperation((TransactionalEditingDomain)editingDomain, command);
        operation.setLabel(Messages.getString("SetParametersAction_LABEL")); //$NON-NLS-1$
        return operation;
	}
}
