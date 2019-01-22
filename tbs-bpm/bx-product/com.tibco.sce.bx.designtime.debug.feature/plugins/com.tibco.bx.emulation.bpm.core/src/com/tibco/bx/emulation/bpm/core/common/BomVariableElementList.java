package com.tibco.bx.emulation.bpm.core.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bx.emulation.core.IEmulationConstants;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.core.common.IVariableElementList;

public class BomVariableElementList extends BomVariableElement implements IVariableElementList{

	List<IVariableElement> itemList;
	Property property;
	public BomVariableElementList(BomVariableElement parent, Property property,
			Type type, Map<String, String> valueMap) {
		super(parent, property.getName(), type);
		this.property = property;
		itemList = new ArrayList<IVariableElement>();
		initValue(valueMap);
	}

	public BomVariableElementList(BomVariableElement parent, Property property,
			Type type, EList<Object> oList) {
		super(parent, property.getName(), type);
		this.property = property;
		itemList = new ArrayList<IVariableElement>();
		initValue(oList);
	}
	
	protected void initValue(Map<String, String> valueMap){
		int i =0;
		String prefix = getInitAbsolateName().replace('.', '_');
		Map<String, String> map = new HashMap<String, String>();
		Type type = property.getType();
		do {
			String key = prefix+"_"+i+"_"; //$NON-NLS-1$ //$NON-NLS-2$
			getItems(valueMap, map, key);
			if(!map.isEmpty()){
				itemList.add(createVariableElement(map, type, key));
				i++;
			}
		} while (!map.isEmpty());
		int lower = property.getLower();
		while(lower > itemList.size()) {
				itemList.add(createVariableElement(map, type, null));
		}
	}
	
	protected void initValue(EList<Object> oList){
		for(Object object : oList){
			itemList.add(createVariableElement((Type)getType(), object));
		}
	}
	
	private IVariableElement createVariableElement(Map<String, String> map,Type type, String key){
		IVariableElement element = null;
		if(type instanceof PrimitiveType){
			element = new PrimitiveVariableElement(this, property, type, map==null?null:map.get(key));
		}else if(type instanceof Enumeration){
			element = new EnumerationVariableElement(this, property.getName(), (Enumeration)type, map==null?null:map.get(key));
		}else if(type instanceof Class){
			element = new ClassVariableElement(this, property.getName(), (Class)type, map==null?new HashMap<String, String>():map);
		}
		return element;
	}
	
	private IVariableElement createVariableElement(Type type, Object object){
		IVariableElement element = null;
		if(type instanceof PrimitiveType){
			element = new PrimitiveVariableElement(this, property, type, object.toString());
		}else if(type instanceof Enumeration){
			element = new EnumerationVariableElement(this, property.getName(), (Enumeration)type, object.toString());
		}else if(type instanceof Class){
			element = new ClassVariableElement(this, property.getName(), (Class)type, (EObject)object);
		}
		return element;
	}
	
	public String getInitAbsolateName(){
		return super.getInitAbsolateName();
	}
	public String getScriptString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getInitAbsolateName());
		buffer.append(".clear();"); //$NON-NLS-1$
		buffer.append(IEmulationConstants.LINE_SEPERATOR);
		int i = 0;
		for(IVariableElement element : itemList){
			if(element != null){//TODO can only handle primitive and Class type
				buffer.append(element.getScriptString());
				if(++i != itemList.size()){
					buffer.append(IEmulationConstants.LINE_SEPERATOR);
				}
			}
		}
		return buffer.toString();
	}

	public String getValueString() {
		return null;
	}

	public Object getValue() {
		List list = new ArrayList();
		for(IVariableElement element : itemList){
			list.add(element.getValue());
		}
		return list;
	}
	
	public IVariableElement[] getVariableElements() {
		return itemList.toArray(new IVariableElement[itemList.size()]);
	}

	public boolean hasVariables() {
		return itemList.size()>0;
	}

	public void setValueString(String value) {
		// does nothing
	}

	public IVariableElement createVariableElement(){
		Type type = property.getType();
		IVariableElement element = createVariableElement(null,type,null);
		itemList.add(element);
		return element;
	}
	
	public boolean removeVariableElement(IVariableElement variableElement){
		return itemList.remove(variableElement);
	}
	
	public int getIndex(IVariableElement variableElement){
		return itemList.indexOf(variableElement);
	}
	
	private Map<String, String> getItems(Map<String, String> sMap,Map<String, String> tMap, String pName){
		tMap.clear();
		Set<String> keys = sMap.keySet();
		for(String key :keys){
			if(key.startsWith(pName)){
				tMap.put(key, sMap.get(key));
			}
		}
		return tMap;
	}
	
	@Override
	public boolean isValid(){
		boolean isValid = getLower()==0? true : ! itemList.isEmpty();
		for(IVariableElement element : itemList){
			isValid = isValid&& element.isValid();
		}
		return isValid;
	}

	@Override
	public int getLower() {
		return property.getLower();
	}

	@Override
	public int getUpper() {
		return property.getUpper();
	}
}
