package com.tibco.bx.emulation.bpm.core.common;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bx.debug.core.util.DateUtil;
import com.tibco.bx.emulation.core.IEmulationConstants;
import com.tibco.bx.emulation.core.common.IPrimitiveVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElement;

public class PrimitiveVariableElement extends BomVariableElement implements IPrimitiveVariableElement{

	private Property property;
	private PrimitiveType primitiveType;
	private static final String ID = "ID", URI = "URI";
	
	public PrimitiveVariableElement(IVariableElement parent, Property property, Type type, String valueString) {
		super(parent, property.getName(), type);
		this.property = property;
		initValue(valueString);
	}

	protected void initValue(String valueString){
		setValueString(removeQuotations(valueString));
	}

	public String getScriptString() {
		StringBuffer buffer = new StringBuffer();
		if(parent instanceof BomVariableElementList){
			String varName = getInitAbsolateName();
			buffer.append("var "+ varName); //$NON-NLS-1$
			buffer.append("="); //$NON-NLS-1$
			buffer.append(getValueStringWithQuotations());
			buffer.append(";"); //$NON-NLS-1$
			buffer.append(IEmulationConstants.LINE_SEPERATOR);
			buffer.append(((BomVariableElementList)parent).getInitAbsolateName());
			buffer.append(".add("); //$NON-NLS-1$
			buffer.append(varName);
			buffer.append(");"); //$NON-NLS-1$
		}else{
			buffer.append(getInitAbsolateName());
			buffer.append("="); //$NON-NLS-1$
			buffer.append(getValueStringWithQuotations());
			buffer.append(";"); //$NON-NLS-1$
		}
		return buffer.toString();
	}

	public PrimitiveType getPrimitiveType() {
		if (primitiveType == null) {
			String typeName = ((Type) getType()).getName();
			if (PrimitiveType.BOOLEAN.getTypeName().equals(typeName)) {
				primitiveType = PrimitiveType.BOOLEAN;
			} else if (PrimitiveType.TIME.getTypeName().equals(typeName)) {
				primitiveType = PrimitiveType.TIME;
			} else if (PrimitiveType.DATE.getTypeName().equals(typeName)) {
				primitiveType = PrimitiveType.DATE;
			} else if (PrimitiveType.DATETIME.getTypeName().equals(typeName)) {
				primitiveType = PrimitiveType.DATETIME;
			} else if (PrimitiveType.DATETIMETZ.getTypeName().equals(typeName)) {
				primitiveType = PrimitiveType.DATETIMETZ;
			} else if (PrimitiveType.DURATION.getTypeName().equals(typeName)) {
				primitiveType = PrimitiveType.DURATION;
			} else if (PrimitiveType.DECIMAL.getTypeName().equals(typeName)) {
				primitiveType = PrimitiveType.DECIMAL;
			} else if (PrimitiveType.INTEGER.getTypeName().equals(typeName)) {
				primitiveType = PrimitiveType.INTEGER;
			} else if (PrimitiveType.TEXT.getTypeName().equals(typeName) || ID.equals(typeName) || URI.equals(typeName)) {
				primitiveType = PrimitiveType.TEXT;
			} else if (PrimitiveType.PERFORMER.getTypeName().equals(typeName)) {
				primitiveType = PrimitiveType.TEXT;
			}
		}
		return primitiveType;
	}
	
	private String getValueStringWithQuotations(){
		if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.TEXT) {
			return value == null ? "''" : "'"+((String)value).replace("'", "\\'")+"'";//in JavaScript the String value should in "" or '' //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}else if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.TIME){
			return value == null ? "null" : DateUtil.formatTimeScript(((XMLGregorianCalendar)value).toGregorianCalendar().getTime());	 //$NON-NLS-1$
		}else if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.DATE){
			return value == null ? "null" : DateUtil.formatDateScript(((XMLGregorianCalendar)value).toGregorianCalendar().getTime()); //$NON-NLS-1$
		}else if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.DATETIME){
			return value == null ? "null" : DateUtil.formatDateTimeScript(((XMLGregorianCalendar)value).toGregorianCalendar().getTime()); //$NON-NLS-1$
		}else if(getPrimitiveType().equals(IPrimitiveVariableElement.PrimitiveType.DATETIMETZ)){
			return value == null ? "null" : DateUtil.formatDateTimeZoneScript(((XMLGregorianCalendar)value).toGregorianCalendar().getTime(), ((XMLGregorianCalendar)value).getTimezone()); //$NON-NLS-1$
		}else if(getPrimitiveType().equals(IPrimitiveVariableElement.PrimitiveType.DURATION)){
			return value == null ? "null" : DateUtil.formatDurationScript((Duration) value); //$NON-NLS-1$
		}
		return getValueString();
	}
	
	public String getValueString() {
		if(value == null) return  ""; //$NON-NLS-1$
		if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.DATE){
			return DateUtil.formatLocaleDate(((XMLGregorianCalendar)value).toGregorianCalendar().getTime());
		}else if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.DATETIME){
			return DateUtil.formatLocaleDateTime(((XMLGregorianCalendar)value).toGregorianCalendar().getTime());
		}else if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.TIME){
			return DateUtil.formatLocaleTime(((XMLGregorianCalendar)value).toGregorianCalendar().getTime());
		}else if(getPrimitiveType().equals(IPrimitiveVariableElement.PrimitiveType.DATETIMETZ)){
			return DateUtil.formatLocaleDateTimeZone(((XMLGregorianCalendar)value).toGregorianCalendar().getTime(),((XMLGregorianCalendar)value).getTimeZone(0));
		}else if(getPrimitiveType().equals(IPrimitiveVariableElement.PrimitiveType.DURATION)){
			return DateUtil.formatLocaleDuration((Duration) value);
		}
		return value == null ? "" : value.toString(); //$NON-NLS-1$
	}

	public Object getValue() {
		return value;
	}
	
	public EObject getEMFCharacter() {
		
		return property;
	}
	
	public IVariableElement[] getVariableElements() {
		return new IVariableElement[0];
	}

	public boolean hasVariables() {
		return false;
	}

	public void setValueString(String value) {
		if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.BOOLEAN) {
			if(value == null || value.equals("")){ //$NON-NLS-1$
				this.value = Boolean.FALSE;
			}else{
				this.value = Boolean.valueOf(value.toString());
			}
	        return;
		}else if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.DECIMAL) {
			if(value == null || value.equals("")){ //$NON-NLS-1$
				this.value =  new Double(0.0);
			}else{
				this.value = new Double(value.toString());
			}
	        return;
		}else if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.INTEGER) {
			if(value == null || value.equals("")){ //$NON-NLS-1$
				this.value =  Integer.valueOf(0);
			}else{
				this.value = new Integer(value);
			}
	        return;
		}else if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.TEXT) {
			if(value == null || value.equals("")){ //$NON-NLS-1$
				this.value =  new String(""); //$NON-NLS-1$
			}else{
				this.value = ((String)value).replace("\\'", "'"); //$NON-NLS-1$ //$NON-NLS-2$
			}
	        return;
		}else if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.DATE
			|| primitiveType == IPrimitiveVariableElement.PrimitiveType.DATETIME) {
			if(value == null || "".equals(value) || "null".equals(value)){ //$NON-NLS-1$ //$NON-NLS-2$
				this.value =  null;
			}else{
				this.value = DateUtil.createXMLGregorianCalendar(value);
			}
		}else if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.TIME) {
			if(value == null || "".equals(value) || "null".equals(value)){ //$NON-NLS-1$ //$NON-NLS-2$
				this.value =  null;
			}else{
				this.value = DateUtil.createXMLGregorianCalendar(value);
			}
		}else if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.DATETIMETZ) {
			if(value == null || "".equals(value) || "null".equals(value)){ //$NON-NLS-1$ //$NON-NLS-2$
				this.value =  null;
			}else{
				this.value = DateUtil.createXMLGregorianCalendar(value);
			}
		}else if(getPrimitiveType() == IPrimitiveVariableElement.PrimitiveType.DURATION){
			if(value == null || "".equals(value) || "null".equals(value)){ //$NON-NLS-1$ //$NON-NLS-2$
				this.value =  null;
			}else{
				this.value = DateUtil.createDuration(value);
			}
		}
	}
}
