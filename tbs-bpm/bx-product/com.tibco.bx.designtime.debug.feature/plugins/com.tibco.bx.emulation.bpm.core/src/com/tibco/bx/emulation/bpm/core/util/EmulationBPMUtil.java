package com.tibco.bx.emulation.bpm.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.bx.emulation.bpm.core.common.BomProjectManager;
import com.tibco.bx.emulation.bpm.core.common.ComplexVariableElement;
import com.tibco.bx.emulation.bpm.core.common.ProcessVariableElementList;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

import org.eclipse.uml2.uml.Class;

public class EmulationBPMUtil {

	/*
	 * data fields and parameters in process and data fields in package
	 */
	public static List<ProcessRelevantData> getAllParms(Process process) {
		List<ProcessRelevantData> pList = getParms(process);
		List<DataField> fList = process.getPackage().getDataFields();
		pList.addAll(fList);
		return pList;
	}

	public static List<ProcessRelevantData> getParms(Process process) {
		List<ProcessRelevantData> pList = new ArrayList<ProcessRelevantData>();
		List<FormalParameter> fList = process.getFormalParameters();
		ProcessInterface processInterface = getImplementedInterface(process);
		if (processInterface != null) {
			pList.addAll(processInterface.getFormalParameters());
		}
		List<DataField> dList = process.getDataFields();
		pList.addAll(fList);
		pList.addAll(dList);
		return pList;
	}

	public static List<FormalParameter> getInputParms(Process process) {
		List<FormalParameter> pList = new ArrayList<FormalParameter>();
		for (FormalParameter parameter : process.getFormalParameters()) {
			if (isInputParameter(parameter))
				pList.add(parameter);
		}
		ProcessInterface processInterface = getImplementedInterface(process);
		if (processInterface != null) {
			for (FormalParameter parameter : processInterface.getFormalParameters()) {
				if (isInputParameter(parameter))
					pList.add(parameter);
			}
		}
		return pList;
	}

	public static List<FormalParameter> getOutputParms(Process process) {
		List<FormalParameter> pList = new ArrayList<FormalParameter>();
		for (FormalParameter parameter : process.getFormalParameters()) {
			if (isOutputParameter(parameter))
				pList.add(parameter);
		}
		ProcessInterface processInterface = getImplementedInterface(process);
		if (processInterface != null) {
			for (FormalParameter parameter : processInterface.getFormalParameters()) {
				if (isOutputParameter(parameter))
					pList.add(parameter);
			}
		}
		return pList;
	}

	public static boolean isInputParameter(FormalParameter parameter) {
		return parameter.getMode() == ModeType.IN_LITERAL || parameter.getMode() == ModeType.INOUT_LITERAL;
	}

	public static boolean isOutputParameter(FormalParameter parameter) {
		return parameter.getMode() == ModeType.OUT_LITERAL || parameter.getMode() == ModeType.INOUT_LITERAL;
	}

	public static ProcessInterface getImplementedInterface(Process process) {
		BasicFeatureMap map = ((BasicFeatureMap) process.getOtherElements());
		for (int i = 0; i < map.size(); i++) {
			Object object = map.get(i).getValue();
			if (object instanceof ImplementedInterface) {
				String processInterfaceId = ((ImplementedInterface) object).getProcessInterfaceId();
				return Xpdl2WorkingCopyImpl.locateProcessInterface(processInterfaceId);
			}
		}
		return null;
	}

	public static String getLabel(String xpdlId, Process process) {
		if (process.getId().equals(xpdlId)) {
			return getDisplayName(process);
		} else {
			EObject object = process.eResource().getEObject(xpdlId);
			if (object instanceof Activity) {
				return getDisplayName((OtherAttributesContainer) object);
			} else if (object instanceof Transition) {
				EObject from = process.eResource().getEObject(((Transition) object).getFrom());
				EObject to = process.eResource().getEObject(((Transition) object).getTo());
				return getDisplayName((OtherAttributesContainer) from) + " - " + getDisplayName((OtherAttributesContainer) to);//$NON-NLS-1$
			}
			return "";//$NON-NLS-1$
		}
	}

	public static String getDisplayName(OtherAttributesContainer attributesContainer) {
		FeatureMap featureMap = attributesContainer.getOtherAttributes();
		int size = featureMap.size();
		for (int i = 0; i < size; i++) {
			EStructuralFeature feature = featureMap.get(i).getEStructuralFeature();
			if (feature.getName().equals("displayName")) {//$NON-NLS-1$
				return (String) featureMap.getValue(i);
			}
		}
		return "";//$NON-NLS-1$
	}

	public static boolean isValidInOutElement(IInOutElement inoutElement) {
		boolean isValid = true;
		IVariableElement vars[] = inoutElement.getVariableElements();
		for (IVariableElement var : vars) {
			return isValidVariable(var);
		}
		return isValid;
	}

	private static boolean isValidVariable(IVariableElement var) {
		boolean isValid = true;
		if (var instanceof ComplexVariableElement) {
			ComplexVariableElement complexVar = (ComplexVariableElement) var;
			String packageName = ((Class) complexVar.getClassVariableElement().getEMFCharacter()).getPackage().getQualifiedName();
			EPackage ePackage = BomProjectManager.INSTANCE.getEPackage(packageName.replaceAll("::", "."));
			if (ePackage == null) {
				return false;
			}
		} else if (var instanceof ProcessVariableElementList) {
			for (IVariableElement varElement : ((ProcessVariableElementList) var).getVariableElements()) {
				return isValidVariable(varElement);
			}
		}
		return isValid;
	}

}
