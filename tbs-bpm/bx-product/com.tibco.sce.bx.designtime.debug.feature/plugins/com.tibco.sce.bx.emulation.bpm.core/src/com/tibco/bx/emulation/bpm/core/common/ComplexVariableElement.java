package com.tibco.bx.emulation.bpm.core.common;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;

import com.tibco.bx.emulation.bpm.core.util.CDSManager;
import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.core.IEmulationConstants;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class ComplexVariableElement extends ProcessVariableElement{

	private ClassVariableElement classVariableElement;
	private TypeDeclaration declaration;
	public ComplexVariableElement(ProcessVariableElementList parent, ProcessRelevantData relevantData, Map<String, String> valueMap) {
		super(parent, relevantData, null);
		initValue(valueMap);
	}
	
	public ComplexVariableElement(ProcessRelevantData relevantData, Map<String, String> valueMap) {
		super(null, relevantData, null);
		initValue(valueMap);
	}
	
	public ComplexVariableElement(ProcessVariableElementList parent, ProcessRelevantData relevantData, String value) {
		super(parent, relevantData, null);
		initValue(value);
	}
	
	public ComplexVariableElement(ProcessRelevantData relevantData, String value) {
		super(null, relevantData, null);
		initValue(value);
	}
	
	protected void initValue(Map<String, String> valueMap){
		if(valueMap != null){
			//TODO if element== null throw an exception
			classVariableElement = new ClassVariableElement(this, getName(), getBomClass(), valueMap);
		}
	}

	protected void initValue(final String value){
		try {
			EObject object = CDSManager.getDefault().deserialize(value);
			classVariableElement = new ClassVariableElement(this, getName(), getBomClass(), object);
		} catch (IOException e) {
			EmulationCoreActivator.log(e);
		}
	}
	
	private Class getBomClass(){
		DataType dataType = ((ProcessRelevantData)getEMFCharacter()).getDataType();
		ExternalReference reference = null;
		if(dataType instanceof DeclaredType){
			declaration = Xpdl2ModelUtil.getPackage(dataType).getTypeDeclaration(((DeclaredType)dataType).getTypeDeclarationId());
			reference = declaration.getExternalReference();
		}else{//it can't be other types
			reference = (ExternalReference)dataType;
		}
		return XPDLUtils.getBomClass(reference);
	}
	
	public String getScriptString() {
		String script = classVariableElement.getScriptString();
		if(getParent() != null){
			
			String[] lines = script.split(IEmulationConstants.LINE_SEPERATOR);
			boolean defined = false;
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < lines.length; i++) {
				String line = lines[i];
				if(lines[i].startsWith("if(") && !defined){ //$NON-NLS-1$
					line = lines[i].substring(lines[i].indexOf(") ")+2, lines[i].length());// remove first "if(...) " //$NON-NLS-1$
				}
				line = line.replaceFirst(getName(), getInitAbsolateName());
				if(!defined){//
					line = "var " + line; //$NON-NLS-1$
					defined = true;
				}
				buffer.append(line);
				if(i != lines.length -1){
					buffer.append(IEmulationConstants.LINE_SEPERATOR);
				}
			}
			script = buffer.toString();
		}
		return script;
	}

	public Object getType() {
		if(declaration != null){
			return declaration;
		}else{
			return classVariableElement.getType();
		}
	}
	
	public String getValueString() {
		return null;
	}

	public Object getValue() {
		return classVariableElement.getValue();
	}
	
	public IVariableElement[] getVariableElements() {
		return classVariableElement.getVariableElements();
	}

	public boolean hasVariables() {
		return classVariableElement.hasVariables();
	}

	public void setValueString(String value) {
		classVariableElement.setValueString(value);
	}

	@Override
	public String getVariableValueString() {
		try {
			EObject object = (EObject)classVariableElement.getValue();
			if(object != null) {
			    return CDSManager.getDefault().serialize(object);
			}
		} catch (IOException e) {
			EmulationCoreActivator.log(e);
		}
		return null;
	}
	
	@Override
	public boolean isValid(){
		return classVariableElement.isValid();
	}

	public ClassVariableElement getClassVariableElement() {
		return classVariableElement;
	}
}
