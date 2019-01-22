package com.tibco.bx.emulation.bpm.core.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.bx.debug.core.util.NetUtil;
import com.tibco.bx.debug.core.util.URLUtils;
import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.core.IEmulationConstants;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElementList;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class ProcessVariableElementList extends ProcessVariableElement implements IVariableElementList{

	List<IVariableElement> itemList = new ArrayList<IVariableElement>();
	TypeDeclaration declaration;//it's null if basic DataType is not DeclaredType
	public ProcessVariableElementList(ProcessRelevantData relevantData,
			List<Map<String, String>> valueList ) {
		super(null, relevantData, null);
		getTypeDeclaration();
		initValue(valueList);
	}

	public ProcessVariableElementList(ProcessRelevantData relevantData,
			String valueString ) {
		super(null, relevantData, null);
		getTypeDeclaration();
		try{
			initValueWithString(NetUtil.deserializeList(valueString));
		}catch(IOException e){
			EmulationCoreActivator.log(e);
		}
	}
	
	protected void initValue(List<Map<String, String>> vList){
		if(vList != null){
			for(Map<String, String> item : vList){
				itemList.add(createVariableElement((ProcessRelevantData)getEMFCharacter(), item));
			}
		}
	}

	protected void initValueWithString(List<String> vList){
		if(vList != null){
			for(String item : vList){
				itemList.add(createVariableElement((ProcessRelevantData)getEMFCharacter(), item));
			}
		}
	}
	
	private void getTypeDeclaration(){
		DataType dataType = ((ProcessRelevantData)getEMFCharacter()).getDataType();
		if(dataType instanceof DeclaredType){
			declaration = Xpdl2ModelUtil.getPackage(dataType).getTypeDeclaration(((DeclaredType)dataType).getTypeDeclarationId());
		}
	}
	
	private IVariableElement createVariableElement(ProcessRelevantData relevantData, String value){
		ProcessVariableElement element = null;
		DataType dataType = relevantData.getDataType();
		if(declaration != null){
			ExternalReference reference = declaration.getExternalReference();
			if(reference != null)
				dataType = reference;
		}
		if(dataType instanceof ExternalReference){
			if(value == null){
				element = new ComplexVariableElement(this, relevantData, new HashMap<String, String>());
			}else{
				element = new ComplexVariableElement(this, relevantData, value);
			}
		}else{//dataType is BasicType or Declared BasicType
			element = new SimpleVariableElement(this, relevantData, value);
		}
		return element;
	}
	
	private IVariableElement createVariableElement(ProcessRelevantData relevantData, Map<String, String> values){
		ProcessVariableElement element = null;
		DataType dataType = relevantData.getDataType();
		if(declaration != null){
			ExternalReference reference = declaration.getExternalReference();
			if(reference != null)
				dataType = reference;
		}
		if(dataType instanceof ExternalReference){
			element = new ComplexVariableElement(this, relevantData, values);
		}else{//dataType is BasicType or Declared BasicType
			element = new SimpleVariableElement(this, relevantData, values.values().iterator().next());
		}
		return element;
	}
	
	@Override
	public String getVariableValueString() {
		List<String> values = new ArrayList<String>();
		for(IVariableElement element : itemList){
			if(element instanceof ProcessVariableElement){
				values.add(URLUtils.encode(((ProcessVariableElement)element).getVariableValueString())); 
			}else{
				values.add(URLUtils.encode(element.getValueString()));
			}
		}
		return values.toString();
	}

	@Override
	public IVariableElement createVariableElement() {
		IVariableElement element = createVariableElement((ProcessRelevantData)getEMFCharacter(),(String)null);
		itemList.add(element);
		return element;
	}

	@Override
	public int getIndex(IVariableElement variableElement) {
		return itemList.indexOf(variableElement);
	}

	@Override
	public boolean removeVariableElement(IVariableElement variableElement) {
		return itemList.remove(variableElement);
	}

	@Override
	public String getScriptString() {
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		buffer.append(getName());
		buffer.append(".clear();"); //$NON-NLS-1$
		buffer.append(IEmulationConstants.LINE_SEPERATOR);
		for(IVariableElement element : itemList){
			if(element != null){//TODO can only handle Simple type and CDS type
				buffer.append(element.getScriptString());
				if(element instanceof ComplexVariableElement){
					buffer.append(IEmulationConstants.LINE_SEPERATOR);
					buffer.append(getName());
					buffer.append(".add("); //$NON-NLS-1$
					buffer.append(getName());
					buffer.append("_"+i+"_"+");"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				
				if(++i != itemList.size()){
					buffer.append(IEmulationConstants.LINE_SEPERATOR);
				}
			}
		}
		return buffer.toString();
	}

	@Override
	public Object getType() {
		DataType dataType = ((ProcessRelevantData)getEMFCharacter()).getDataType();
		if(declaration != null){
			return declaration;
		}else if(dataType instanceof ExternalReference){
			return XPDLUtils.getBomClass(((ExternalReference)dataType));
		}else {
			return ((BasicType)dataType).getType();
		}
	}

	@Override
	public Object getValue() {
		List list = new ArrayList();
		for(IVariableElement element : itemList){
			list.add(element.getValue());
		}
		return list;
	}

	@Override
	public String getValueString() {
		return null;
	}

	@Override
	public IVariableElement[] getVariableElements() {
		return itemList.toArray(new IVariableElement[itemList.size()]);
	}

	@Override
	public boolean hasVariables() {
		return itemList.size()>0;
	}

	@Override
	public void setValueString(String value) {
		// does nothing
	}

	@Override
	public boolean isValid(){
		boolean isValid = ! itemList.isEmpty();
		for(IVariableElement element : itemList){
			isValid = isValid&& element.isValid();
		}
		return isValid;
	}

	@Override
	public int getLower() {
		return 1;
	}

	@Override
	public int getUpper() {
		return -1;
	}

	public void setItemList(List<IVariableElement> itemList) {
		this.itemList = itemList;
	}
	
}
