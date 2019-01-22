package com.tibco.bx.debug.bpm.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.bx.debug.bpm.core.models.BxEnumType;
import com.tibco.bx.debug.bpm.core.models.BxEnumValue;
import com.tibco.bx.debug.common.model.ProcessContainer;
import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.common.model.VariableType;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.models.variable.BxComplexType;
import com.tibco.bx.debug.core.models.variable.BxComplexValue;
import com.tibco.bx.debug.core.models.variable.BxListType;
import com.tibco.bx.debug.core.models.variable.BxPrimitiveType;
import com.tibco.bx.debug.core.models.variable.BxSubVariable;
import com.tibco.bx.debug.core.models.variable.BxValue;
import com.tibco.bx.debug.core.models.variable.BxVariable;
import com.tibco.bx.debug.core.models.variable.ICDSDeserializer;
import com.tibco.bx.debug.core.models.variable.ICDSSerializer;
import com.tibco.bx.debug.core.models.variable.IVariableHandler;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.bpm.core.util.CDSManager;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class BxVariableHandler implements IVariableHandler {

	private static Map<String, VariableType> variableTypeMap;
	static {
		variableTypeMap = new HashMap<String, VariableType>();
		variableTypeMap.put("boolean", BxPrimitiveType.BOOLEAN); //$NON-NLS-1$
		variableTypeMap.put("Boolean", BxPrimitiveType.BOOLEAN); //$NON-NLS-1$
		variableTypeMap.put("java.lang.Boolean", BxPrimitiveType.BOOLEAN); //$NON-NLS-1$
		variableTypeMap.put("char", BxPrimitiveType.CHAR); //$NON-NLS-1$
		variableTypeMap.put("Char", BxPrimitiveType.CHAR); //$NON-NLS-1$
		variableTypeMap.put("java.lang.Char", BxPrimitiveType.CHAR); //$NON-NLS-1$
		variableTypeMap.put("byte", BxPrimitiveType.BYTE); //$NON-NLS-1$
		variableTypeMap.put("java.lang.Byte", BxPrimitiveType.BYTE); //$NON-NLS-1$
		variableTypeMap.put("Byte", BxPrimitiveType.BYTE); //$NON-NLS-1$
		variableTypeMap.put("short", BxPrimitiveType.SHORT); //$NON-NLS-1$
		variableTypeMap.put("Short", BxPrimitiveType.SHORT); //$NON-NLS-1$
		variableTypeMap.put("java.lang.Short", BxPrimitiveType.SHORT); //$NON-NLS-1$
		variableTypeMap.put("int", BxPrimitiveType.INTEGER); //$NON-NLS-1$
		variableTypeMap.put("integer", BxPrimitiveType.INTEGER); //$NON-NLS-1$
		variableTypeMap.put("Integer", BxPrimitiveType.INTEGER); //$NON-NLS-1$
		variableTypeMap.put("java.lang.Integer", BxPrimitiveType.INTEGER); //$NON-NLS-1$
		variableTypeMap.put("long", BxPrimitiveType.LONG); //$NON-NLS-1$
		variableTypeMap.put("Long", BxPrimitiveType.LONG); //$NON-NLS-1$
		variableTypeMap.put("java.lang.Long", BxPrimitiveType.LONG); //$NON-NLS-1$
		variableTypeMap.put("float", BxPrimitiveType.FLOAT); //$NON-NLS-1$
		variableTypeMap.put("Float", BxPrimitiveType.FLOAT); //$NON-NLS-1$
		variableTypeMap.put("java.lang.Float", BxPrimitiveType.FLOAT); //$NON-NLS-1$
		variableTypeMap.put("double", BxPrimitiveType.DOUBLE); //$NON-NLS-1$
		variableTypeMap.put("Double", BxPrimitiveType.DOUBLE); //$NON-NLS-1$
		variableTypeMap.put("java.lang.Double", BxPrimitiveType.DOUBLE); //$NON-NLS-1$
		variableTypeMap.put("decimal", BxPrimitiveType.DECIMAL); //$NON-NLS-1$
		variableTypeMap.put("Decimal", BxPrimitiveType.DECIMAL); //$NON-NLS-1$
		variableTypeMap.put("java.lang.Decimal", BxPrimitiveType.DECIMAL); //$NON-NLS-1$
		variableTypeMap.put("date", BxPrimitiveType.DATE); //$NON-NLS-1$
		variableTypeMap.put("Date", BxPrimitiveType.DATE); //$NON-NLS-1$
		variableTypeMap.put("datetime", BxPrimitiveType.DATETIME); //$NON-NLS-1$
		variableTypeMap.put("dateTime", BxPrimitiveType.DATETIME); //$NON-NLS-1$
		variableTypeMap.put("DateTime", BxPrimitiveType.DATETIME); //$NON-NLS-1$
		variableTypeMap.put("time", BxPrimitiveType.TIME); //$NON-NLS-1$
		variableTypeMap.put("Time", BxPrimitiveType.TIME); //$NON-NLS-1$
		variableTypeMap.put("java.lang.String", BxPrimitiveType.STRING); //$NON-NLS-1$
		variableTypeMap.put("string", BxPrimitiveType.STRING); //$NON-NLS-1$
		variableTypeMap.put("String", BxPrimitiveType.STRING); //$NON-NLS-1$
		variableTypeMap.put("java.math.BigInteger", BxPrimitiveType.BIGINTEGER); //$NON-NLS-1$
		variableTypeMap.put("java.math.BigDecimal", BxPrimitiveType.BIGDECIMAL); //$NON-NLS-1$

		variableTypeMap.put(String.valueOf(BasicTypeType.BOOLEAN), BxPrimitiveType.BOOLEAN);
		variableTypeMap.put(String.valueOf(BasicTypeType.FLOAT), BxPrimitiveType.FLOAT);
		variableTypeMap.put(String.valueOf(BasicTypeType.INTEGER), BxPrimitiveType.INTEGER);
		variableTypeMap.put(String.valueOf(BasicTypeType.STRING), BxPrimitiveType.STRING);
		variableTypeMap.put(String.valueOf(BasicTypeType.DATE), BxPrimitiveType.DATE);
		variableTypeMap.put(String.valueOf(BasicTypeType.TIME), BxPrimitiveType.TIME);
		variableTypeMap.put(String.valueOf(BasicTypeType.DATETIME), BxPrimitiveType.DATETIME);
		variableTypeMap.put(String.valueOf(BasicTypeType.PERFORMER), BxPrimitiveType.PERFORMER);

		variableTypeMap.put("Duration", BxPrimitiveType.DURATION); //$NON-NLS-1$
		variableTypeMap.put("duration", BxPrimitiveType.DURATION); //$NON-NLS-1$
		variableTypeMap.put("DateTimeZone", BxPrimitiveType.DATETIMEZ); //$NON-NLS-1$
		variableTypeMap.put("javax.xml.datatype.XMLGregorianCalendar", BxPrimitiveType.XMLCALENDAR); //$NON-NLS-1$
		variableTypeMap.put("XMLGregorianCalendar", BxPrimitiveType.XMLCALENDAR); //$NON-NLS-1$
		variableTypeMap.put("javax.xml.datatype.Duration", BxPrimitiveType.DURATION); //$NON-NLS-1$
	}

	public BxVariableHandler() {
	}

	@Override
	public ICDSDeserializer getDeserializer() {
		return CDSManager.getDefault();
	}

	@Override
	public ICDSSerializer getSerializer() {
		return CDSManager.getDefault();
	}

	@Override
	public Object getValue(VariableType variableType, String valueString) throws CoreException {
		return variableType.getValue(valueString);
	}

	private static DataType getGlobalDataType(String processId, String variableName) {
		com.tibco.xpd.xpdl2.Process process = Xpdl2WorkingCopyImpl.locateProcess(processId);
		if (process != null) {
			List<EObject> pList = ProcessUtil.getAllParms(process);
			for (EObject data : pList) {
				DataType type = ((ProcessRelevantData) data).getDataType();
				if (variableName.equals(((ProcessRelevantData) data).getName())) {
					if (type instanceof DeclaredType) {
						TypeDeclaration declaration = Xpdl2ModelUtil.getPackage(type)
								.getTypeDeclaration(((DeclaredType) type).getTypeDeclarationId());
						ExternalReference reference = declaration.getExternalReference();
						if (reference != null) {
							type = reference;
						} else {
							type = declaration.getBasicType();
						}
					}
					return type;
				}
			}
		} else {

		}
		return null;// TODO it can't be null
	}

	private DataType getLocalDataType(String processId, String activityId, String variableName) {
		com.tibco.xpd.xpdl2.Process process = Xpdl2WorkingCopyImpl.locateProcess(processId);
		if (process != null) {
			Activity activity = com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil.getActivityById(process, activityId);
			List<DataField> dfList = activity.getDataFields();
			if (dfList == null || dfList.size() == 0) {
				EObject container = activity.eContainer();
				if (container instanceof ActivitySet) {
					((ActivitySet) container).getId();
					// TODO embedded sub-process/database task/email task
					Activity containerActivity = getEmbeddedSubProcess(process.getActivities(), (ActivitySet) container);
					if (containerActivity != null) {
						dfList = containerActivity.getDataFields();
						for (DataField data : dfList) {
							DataType type = data.getDataType();
							if (variableName.equals(data.getName())) {
								TypeDeclaration declaration = Xpdl2ModelUtil.getPackage(type).getTypeDeclaration(
										((DeclaredType) type).getTypeDeclarationId());
								ExternalReference reference = declaration.getExternalReference();
								if (reference != null) {
									type = reference;
								} else {
									type = declaration.getBasicType();
								}
							}
							return type;
						}
					}
				}
			} else {
				for (DataField data : dfList) {
					DataType type = data.getDataType();
					if (variableName.equals(data.getName()))
						return type;
				}
			}

		}
		return null;// TODO it can't be null
	}

	private Activity getEmbeddedSubProcess(List<Activity> activities, ActivitySet targetActivitySet) {
		for (Activity activity : activities) {
			if (activity.getBlockActivity() != null) {
				String activitySetId = activity.getBlockActivity().getActivitySetId();
				if (activitySetId.equals(targetActivitySet.getId())) {
					return activity;
				} else {
					ActivitySet activitySet = activity.getProcess().getActivitySet(activitySetId);
					Activity activity1 = getEmbeddedSubProcess(activitySet.getActivities(), targetActivitySet);
					if (activity1 != null)
						return activity1;
				}
			}
		}
		return null;
	}

	@Override
	public VariableType getGlobalVariableType(String processId, String variableName, String nameSpace, String typeName) {
		if (typeName.toUpperCase().equals("LIST") || typeName.toUpperCase().equals("CDS_ARRAY")) { //$NON-NLS-1$ //$NON-NLS-2$
			DataType type = getGlobalDataType(processId, variableName);
			if (type instanceof ExternalReference) {
				String typeString = BOMWorkingCopy.getQualifiedClassName(XPDLUtils.getBomClass((ExternalReference) type));
				return new BxListType(new BxComplexType(getDeserializer(), typeString));
			} else if (type instanceof BasicType) {
				return new BxListType(variableTypeMap.get(String.valueOf(((BasicType) type).getType().getValue())));
			}
			return null;// TODO
		} else {
			VariableType variableType = variableTypeMap.get(typeName);
			if (variableType != null) {
				return variableType;
			} else if (typeName.toUpperCase().equals("CDS")) { //$NON-NLS-1$
				ExternalReference reference = (ExternalReference) getGlobalDataType(processId, variableName);
				String typeString = BOMWorkingCopy.getQualifiedClassName(XPDLUtils.getBomClass(reference));
				return new BxComplexType(getDeserializer(), typeString);
			} else {
				// include non-reference CDS type and "OBJECT_SPACE,ID"
				// TODO
				return new BxComplexType(getDeserializer(), typeName);
			}
		}
	}

	@Override
	public VariableType getLocalVariableType(String processId, String activityId, String variableName, String nameSpace, String typeName) {
		if (typeName.toUpperCase().equals("LIST") || typeName.toUpperCase().equals("CDS_ARRAY")) { //$NON-NLS-1$ //$NON-NLS-2$
			DataType type = getLocalDataType(processId, activityId, variableName);
			if (type instanceof ExternalReference) {
				String typeString = BOMWorkingCopy.getQualifiedClassName(XPDLUtils.getBomClass((ExternalReference) type));
				return new BxListType(new BxComplexType(getDeserializer(), typeString));
			} else if (type instanceof BasicType) {
				return new BxListType(variableTypeMap.get(String.valueOf(((BasicType) type).getType().getValue())));
			}
			return null;// TODO
		} else {
			VariableType variableType = variableTypeMap.get(typeName);
			if (variableType != null) {
				return variableType;
			} else if (typeName.toUpperCase().equals("CDS")) { //$NON-NLS-1$
				ExternalReference reference = (ExternalReference) getLocalDataType(processId, activityId, variableName);
				String typeString = BOMWorkingCopy.getQualifiedClassName(XPDLUtils.getBomClass(reference));
				return new BxComplexType(getDeserializer(), typeString);
			} else {
				// include non-reference CDS type and "OBJECT_SPACE,ID"
				// TODO
				return new BxComplexType(getDeserializer(), typeName);
			}
		}
	}

	@Override
	public void setChildValue(Object CDSValue, String varName, Object value) {
		if (CDSValue instanceof EObject) {
			EList<EStructuralFeature> featureList = ((EObject) CDSValue).eClass().getEAllStructuralFeatures();
			for (EStructuralFeature eStructuralFeature : featureList) {
				if (eStructuralFeature.getName().equals(varName)) {
					((EObject) CDSValue).eSet(eStructuralFeature, value);
					break;
				}
			}
		}
	}

	@Override
	public List<BxVariable> generateSubVariables(BxComplexValue parent, Object parentValue) {
		List<BxVariable> varList = new ArrayList<BxVariable>();
		if (parent != null && parentValue != null) {
			EList<EStructuralFeature> featureList = ((EObject) parentValue).eClass().getEAllStructuralFeatures();
			ProcessVariable processVariable = (ProcessVariable) parent.getProcessElement();
			processVariable.getElements().clear();
			ProcessContainer container = getNonVariableParent(processVariable);
			for (EStructuralFeature eStructuralFeature : featureList) {
				String typeName = getTypeName(eStructuralFeature);
				String className = eStructuralFeature.getEType().getInstanceClassName();
				String varName = eStructuralFeature.getName();
				ProcessVariable cVariable = new ProcessVariable(varName, varName, processVariable);
				if (className.equals("org.eclipse.emf.common.util.AbstractEnumerator")) {
					cVariable.setType(new BxEnumType(this, typeName));
				} else {
					if (container instanceof ProcessInstance) {
						cVariable.setType(getGlobalVariableType(container.getIndex(), varName, processVariable.getNamespace(), typeName));
					} else {// activity
						cVariable.setType(getLocalVariableType(container.getProcessInstance().getIndex(), container.getIndex(), varName,
								processVariable.getNamespace(), typeName));
					}
				}
				cVariable.setValue(((EObject) parentValue).eGet(eStructuralFeature));
				BxVariable dVariable = new BxSubVariable(cVariable, (IBxDebugTarget) parent.getDebugTarget());
				varList.add(dVariable);
			}
		}
		return varList;
	}

	/**
	 * @param processVariable
	 * @return get high-level container which is not a ProcessVariable
	 */
	private ProcessContainer getNonVariableParent(ProcessVariable processVariable) {
		ProcessContainer container = processVariable.getParent();
		return (container instanceof ProcessVariable) ? getNonVariableParent((ProcessVariable) container) : container;
	}

	@Override
	public BxValue getExtDebugValue(ProcessVariable commonVariable, IBxDebugTarget debugTarget) {
		return new BxEnumValue(commonVariable, debugTarget);
	}

	private String getTypeName(EStructuralFeature eStructuralFeature) {
		String varName = eStructuralFeature.getName();
		//varName = varName.substring(0, 1).toUpperCase() + varName.substring(1, varName.length()) + "Type"; //$NON-NLS-1$
		String typeName = eStructuralFeature.getEType().getInstanceTypeName();
		if ("javax.xml.datatype.XMLGregorianCalendar".equals(typeName)) { //$NON-NLS-1$
			typeName = eStructuralFeature.getEType().getName();
			if (typeName.startsWith(varName)) {
				typeName = "DateTimeZone"; //$NON-NLS-1$
			}
		}
		return typeName;
	}

}
