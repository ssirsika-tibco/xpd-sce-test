package com.tibco.bx.emulation.bpm.core.common;


import java.util.Date;

import com.tibco.bx.debug.core.util.DateUtil;
import com.tibco.bx.emulation.core.IEmulationConstants;
import com.tibco.bx.emulation.core.common.IPrimitiveVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class SimpleVariableElement extends ProcessVariableElement implements IPrimitiveVariableElement{

	private PrimitiveType primitiveType;
	public SimpleVariableElement(ProcessVariableElementList parent, ProcessRelevantData relevantData, String valueString) {
		super(parent, relevantData, valueString);
		initValue(valueString);
	}
	
	public SimpleVariableElement(ProcessRelevantData relevantData, String valueString) {
		super(null, relevantData, valueString);
		initValue(valueString);
	}
	
	protected void initValue(String valueString){
		getPrimitiveType();
		setValueString(removeQuotations(valueString));
	}
	public PrimitiveType getPrimitiveType(){
		if(primitiveType == null){
			DataType dataType = ((ProcessRelevantData)getEMFCharacter()).getDataType();
			if(dataType instanceof DeclaredType){
				dataType = (DataType)BasicTypeConverterFactory.INSTANCE.getBaseType(dataType, true);
			}
			BasicTypeType basicTypeType = ((com.tibco.xpd.xpdl2.BasicType)dataType).getType();
			int basicTypeTypeValue = basicTypeType.getValue();
			if(basicTypeTypeValue == BasicTypeType.BOOLEAN){
				primitiveType = PrimitiveType.BOOLEAN;
			}else if(basicTypeTypeValue == BasicTypeType.DATE){
				primitiveType = PrimitiveType.DATE;
			}else if(basicTypeTypeValue == BasicTypeType.DATETIME){
				primitiveType = PrimitiveType.DATETIME;
			}else if(basicTypeTypeValue == BasicTypeType.TIME){
				primitiveType = PrimitiveType.TIME;
			}else if(basicTypeTypeValue == BasicTypeType.FLOAT){
				primitiveType = PrimitiveType.DECIMAL;
			}else if(basicTypeTypeValue == BasicTypeType.INTEGER){
				primitiveType = PrimitiveType.INTEGER;
			}else if(basicTypeTypeValue == BasicTypeType.STRING || basicTypeTypeValue == BasicTypeType.PERFORMER){
				primitiveType = PrimitiveType.TEXT;
			}
		}
		return primitiveType;
	}
	
	public void setValueString(String value) {
		if(primitiveType == PrimitiveType.BOOLEAN) {
			if(value == null || value.equals("")){ //$NON-NLS-1$
				this.value = Boolean.FALSE;
			}else{
				this.value = Boolean.valueOf(value.toString());
			}
		}else if(primitiveType == PrimitiveType.DECIMAL) {
			if(value == null || value.equals("")){ //$NON-NLS-1$
				this.value =  new Double(0.0);
			}else{
				this.value = Double.valueOf(value.toString());
			}
		}else if(primitiveType == PrimitiveType.INTEGER) {
			if(value == null || value.equals("")){ //$NON-NLS-1$
				this.value =  new Integer(0);
			}else{
				this.value = Integer.valueOf(value.toString());
			}
		}else if(primitiveType == PrimitiveType.TEXT) {
			if(value == null || value.equals("")){ //$NON-NLS-1$
				this.value =  new String(""); //$NON-NLS-1$
			}else{
				this.value = value;
			}
		}else if(primitiveType == PrimitiveType.DATE || primitiveType == PrimitiveType.DATETIME) {
			if(value == null || "".equals(value) || "null".equals(value)){ //$NON-NLS-1$ //$NON-NLS-2$
				this.value =  null;
			}else{
				this.value = DateUtil.parse(value);
			}
		}else if(primitiveType == PrimitiveType.TIME) {
			if(value == null || "".equals(value) || "null".equals(value)){ //$NON-NLS-1$ //$NON-NLS-2$
				this.value =  null;
			}else{
				this.value = DateUtil.parse(value);
			}
		}
	}

	public String getScriptString() {
		StringBuffer buffer = new StringBuffer();
		String varName = getInitAbsolateName();
		if(parent != null){
			buffer.append("var "+ varName); //$NON-NLS-1$
			buffer.append("="); //$NON-NLS-1$
			buffer.append(getValueStringWithQuotations());
			buffer.append(";"); //$NON-NLS-1$
			buffer.append(IEmulationConstants.LINE_SEPERATOR);
			buffer.append(((ProcessRelevantData)getEMFCharacter()).getName());
			buffer.append(".add("); //$NON-NLS-1$
			buffer.append(varName);
			buffer.append(");"); //$NON-NLS-1$
		}else{
			buffer.append(varName);
			buffer.append("="); //$NON-NLS-1$
			buffer.append(getValueStringWithQuotations());
			buffer.append(";"); //$NON-NLS-1$
		}
		
		return buffer.toString();
	}

	public Object getType() {
		DataType dataType = ((ProcessRelevantData)getEMFCharacter()).getDataType();
		if(dataType instanceof DeclaredType){
			return Xpdl2ModelUtil.getPackage(dataType).getTypeDeclaration(((DeclaredType)dataType).getTypeDeclarationId());
		}else{
			return ((com.tibco.xpd.xpdl2.BasicType)dataType).getType();
		}
		
	}
	
	private String getValueStringWithQuotations(){
		if(primitiveType == PrimitiveType.TEXT) {
			return value == null ? "''" : "'"+((String)value).replace("\\", "\\\\").replace("'", "\\'")+"'";//add Escape Characters  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
		}else if(primitiveType == PrimitiveType.TIME){
			return value == null ? "null" : DateUtil.formatTimeScript((Date)value);	 //$NON-NLS-1$
		}else if(primitiveType == PrimitiveType.DATETIME){
			return value == null ? "null" : DateUtil.formatDateTimeScript((Date)value);	 //$NON-NLS-1$
		}else if(primitiveType == PrimitiveType.DATE){
			return value == null ? "null" : DateUtil.formatDateScript((Date)value);	 //$NON-NLS-1$
		}
		return getValueString();
	}
	
	public String getValueString() {
		if(value == null) return  ""; //$NON-NLS-1$
		if(primitiveType == PrimitiveType.DATE) {
			return DateUtil.formatLocaleDate((Date)value);
		}else if(primitiveType == PrimitiveType.DATETIME) {
			return DateUtil.formatLocaleDateTime((Date)value);
		}else if(primitiveType == PrimitiveType.TIME) {
			return DateUtil.formatLocaleTime((Date)value);
		}
		return value.toString();
	}

	public Object getValue() {
		return value;
	}
	
	public IVariableElement[] getVariableElements() {
		return new IVariableElement[0];
	}

	public boolean hasVariables() {
		return false;
	}

	@Override
	public String getVariableValueString() {
		if(primitiveType == PrimitiveType.DATE) {
			return DateUtil.formatDate((Date)value);
		}else if(primitiveType == PrimitiveType.DATETIME) {
			return DateUtil.formatDateTime((Date)value);
		}else if(primitiveType == PrimitiveType.TIME) {
			return DateUtil.formatTime((Date)value);
		}else{
			return getValueString();
		}
	}

	@Override
	public boolean isValid(){
		if(value instanceof String){
			return !((String) value).isEmpty();
		}
		return value != null;
	}
	
}
