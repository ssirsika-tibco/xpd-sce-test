package com.tibco.bx.emulation.bpm.core.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.InOutDataType;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.Parameter;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

public class InOutElement extends ActivityElement implements IInOutElement {

	private ModeType[] showDataType;

	private InOutDataType inOutData;
	private String soapMessage;
	private com.tibco.xpd.xpdl2.Process process;
	private Activity startActivity;
	private ProcessNode processNode;
	public InOutElement(InOutDataType inOutData, String processId, String modelType) {
		super(processId, modelType);
		this.inOutData = inOutData;
		if (inOutData instanceof Input)
			showDataType = new ModeType[] { ModeType.IN_LITERAL, ModeType.INOUT_LITERAL };
		else
			showDataType = new ModeType[] { ModeType.OUT_LITERAL, ModeType.INOUT_LITERAL };
		init(processId);
	}

	public InOutElement(InOutDataType inOutData,ProcessNode processNode, String modelType){
		super(processNode.getId(), modelType);
		this.processNode = processNode;
		this.inOutData = inOutData;
		if (inOutData instanceof Input)
			showDataType = new ModeType[] { ModeType.IN_LITERAL, ModeType.INOUT_LITERAL };
		else
			showDataType = new ModeType[] { ModeType.OUT_LITERAL, ModeType.INOUT_LITERAL };
		init(processNode.getId());
	}
	
	@Override
	protected void init(String processId) {
		list = new ArrayList<ProcessVariableElement>();
		process = Xpdl2WorkingCopyImpl.locateProcess(processId);
		if(processNode==null){
			startActivity = getActivity(process, inOutData.getId());
			
		}else{
			String activityId = processNode.getInput().getId();
			startActivity = (Activity) ProcessUtil.getActivity(process, activityId);
		}
		Assert.isNotNull(startActivity);
		List<EObject> pList = ProcessUtil.filterOutDataField(ProcessUtil.getAllParms(process));
		EList<Parameter> expList = inOutData.getParameters();

		for (EObject prd : pList) {
			if (prd instanceof FormalParameter) {
				if (isVisual((FormalParameter) prd)) {
					handleVariableElement((FormalParameter) prd, expList);
				}
			} else if (prd instanceof DataField) {
				handleVariableElement((DataField) prd, expList);
			}
		}

	}

	private void handleVariableElement(ProcessRelevantData prd, EList<Parameter> eList) {
		String valueString = null;
		Parameter parm = getParameter(eList, (ProcessRelevantData) prd);
		if (parm != null) {
			valueString = parm.getValue();
		}
		createVariableElement(prd, valueString);
	}

	private Activity getActivity(Process process, String activityId) {
		List<Activity> activities = process.getActivities();
		for (Activity a : activities) {
			if (a.getId().equals(activityId)) {
				return a;
			}
		}
		return null;
	}

	private Parameter getParameter(EList<Parameter> expList, ProcessRelevantData data) {
		for (Parameter parm : expList) {
			if (data.getId().equals(parm.getId()))
				return parm;
		}
		return null;
	}

	@Override
	public String getVariableValueString(String valiableName) {
		return null;
	}

	@Override
	public InOutDataType getInOutDataType() {
		return inOutData;
	}

	public boolean isVisual(EObject parameter) {
		AssociatedParameters associatedParameters = (AssociatedParameters) startActivity.getOtherElement("associatedParameters"); //$NON-NLS-1$
		if (associatedParameters != null) {
			EList<AssociatedParameter> list = associatedParameters.getAssociatedParameter();
			for (AssociatedParameter associatedParameter : list) {
				if (associatedParameter.getFormalParam().equals(((FormalParameter) parameter).getName()))
					return true;
			}
		}
		if (parameter instanceof FormalParameter) {
			ModeType mode = ((FormalParameter) parameter).getMode();
			for (int i = 0; i < showDataType.length; i++) {
				if (showDataType[i] == mode) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public IInOutElement clone() {
		InOutDataType dataClone = null;
		if (inOutData instanceof Input) {
			dataClone = EmulationFactory.eINSTANCE.createInput();
			dataClone.setId(inOutData.getId());
			dataClone.setName(inOutData.getName());
			for (ProcessVariableElement vElement : getVariableElements()) {
				Parameter pClone = EmulationFactory.eINSTANCE.createParameter();
				pClone.setId(((ProcessRelevantData) vElement.getEMFCharacter()).getId());
				pClone.setName(((ProcessRelevantData) vElement.getEMFCharacter()).getName());
				pClone.setValue(vElement.getVariableValueString());
				dataClone.getParameters().add(pClone);
			}
		}
		InOutElement element = new InOutElement(dataClone, getProcessId(), getProcessModelType());

		return element;
	}

	@Override
	public String getSoapMessage() {
		return soapMessage;
	}

	@Override
	public void setSoapMessage(String soapMessage) {
		this.soapMessage = soapMessage;
	}
}