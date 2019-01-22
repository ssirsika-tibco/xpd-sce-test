package com.tibco.bx.emulation.bpm.core.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.core.IEmulationConstants;
import com.tibco.bx.emulation.core.Messages;
import com.tibco.bx.emulation.core.common.IVariableElement;
import com.tibco.bx.emulation.core.common.TransformationException;

public class ClassVariableElement extends BomVariableElement{

	IVariableElement[] subElements;
	public ClassVariableElement(IVariableElement parent, String name, Class type, Map<String, String> valueMap) {
		super(parent, name, type);
		initValue(valueMap);
	}

	public ClassVariableElement(IVariableElement parent, String name, Class type, EObject object) {
		super(parent, name, type);
		initValue(object);
	}
	
	protected void initValue(Map<String, String> valueMap){
		EList<Property> list = ((Class)getType()).getAllAttributes();
		//now the valueString is empty string
		subElements = new IVariableElement[list.size()];
		int i = 0;
		for(Property property : list){
			Type type = property.getType();
			String pName = null;
			if(isListProperty(property)){
				pName = getInitAbsolateName() + "_" + property.getName(); //$NON-NLS-1$
				subElements[i++] = new BomVariableElementList(this, property, type, getValues4ListProperty(valueMap,pName));
			}else{
				if(type instanceof PrimitiveType){
					pName = getInitAbsolateName() + "." + property.getName(); //$NON-NLS-1$
					subElements[i++] = new PrimitiveVariableElement(this, property, type, valueMap.get(pName));
				}else if(type instanceof Enumeration){
					pName = getInitAbsolateName() + "." + property.getName(); //$NON-NLS-1$
					subElements[i++] = new EnumerationVariableElement(this, property.getName(), (Enumeration)type, valueMap.get(pName));
				}else if(type instanceof Class){
					pName = getInitAbsolateName() + "_" + property.getName(); //$NON-NLS-1$
					subElements[i++] = new ClassVariableElement(this, property.getName(), (Class)type, getValues4Property(valueMap,pName));
				}
			}
		}
	}
	
	protected void initValue(EObject object){
		EList<Property> list = ((Class)getType()).getAllAttributes();
		value = object;
		//now the valueString is empty string
		subElements = new IVariableElement[list.size()];
		int i = 0;
		for(Property property : list){
			Type type = property.getType();
			if(isListProperty(property)){
				String pName = property.getName();
				subElements[i++] = new BomVariableElementList(this, property, type, (EList)getValues4ListProperty(object,pName));
			}else{
				String pName = property.getName();
				if(type instanceof PrimitiveType){
					subElements[i++] = new PrimitiveVariableElement(this, property, type, getValues4ListProperty(object,pName).toString());
				}else if(type instanceof Enumeration){
					subElements[i++] = new EnumerationVariableElement(this, property.getName(), (Enumeration)type, getValues4ListProperty(object,pName).toString());
				}else if(type instanceof Class){
					subElements[i++] = new ClassVariableElement(this, property.getName(), (Class)type, (EObject)getValues4ListProperty(object,pName));
				}
			}
		}
	}
	
	
	public String getScriptString() {
		StringBuffer buffer = new StringBuffer();
		String varName = getInitAbsolateName();
		if(parent == null || !(parent instanceof BomVariableElement)){
			buffer.append("if("); //$NON-NLS-1$
			buffer.append(getName());
			buffer.append(" == null) {"); //$NON-NLS-1$
			buffer.append(getName());
			buffer.append("="); //$NON-NLS-1$
			buffer.append(getCreateMethod4Script());
			buffer.append(";}"); //$NON-NLS-1$
			buffer.append(IEmulationConstants.LINE_SEPERATOR);
		}else{
			buffer.append("var "); //$NON-NLS-1$
			buffer.append(varName);
			buffer.append("="); //$NON-NLS-1$
			buffer.append(getCreateMethod4Script());
			buffer.append(";"); //$NON-NLS-1$
			buffer.append(IEmulationConstants.LINE_SEPERATOR);
		}
		
		for(int i = 0; i < subElements.length; i++){
			if(subElements[i] != null){//TODO can only handle primitive and Class type
				buffer.append(subElements[i].getScriptString());
				buffer.append(IEmulationConstants.LINE_SEPERATOR);
			}
		}
		if(parent instanceof BomVariableElementList){
			buffer.append(((BomVariableElementList)parent).getInitAbsolateName() + ".add(" + varName + ");"); //$NON-NLS-1$ //$NON-NLS-2$
		}else if(parent != null && parent instanceof BomVariableElement){
			buffer.append(getAbsolateName() + "=" + varName + ";"); //$NON-NLS-1$ //$NON-NLS-2$
		}	
	
		return buffer.toString();
	}

	protected String getInitAbsolateName(){
		return super.getInitAbsolateName().replace('.', '_');
	}
	
	private String getCreateMethod4Script(){
		//capitalize the  first Char of class name
		String className = ((Class)getEMFCharacter()).getName();
		return getTypePackageName().replace('.', '_') + "_Factory" + ".create" + className + "()"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	public String getValueString() {
		return null;
	}

	public Object getValue() {
		try {
			return updateValue();
		} catch (TransformationException e) {
			EmulationCoreActivator.log(e);
			return null;
		}
	}
	
	public IVariableElement[] getVariableElements() {
		return subElements;
	}

	public boolean hasVariables() {
		return true;
	}

	public void setValueString(String value) {
		// does nothing
	}

	private Map<String, String> getValues4Property(Map<String, String> valueMap, String pName){
		Map<String, String> map = new HashMap<String, String>();
		Set<String> keys = valueMap.keySet();
		for(String key :keys){
			if(key.startsWith(pName + ".") || key.startsWith(pName + "_")){ //$NON-NLS-1$ //$NON-NLS-2$
				map.put(key, valueMap.get(key));
			}
		}
		return map;
	}
	
	private Map<String, String> getValues4ListProperty(Map<String, String> valueMap, String pName){
		Map<String, String> map = new HashMap<String, String>();
		Set<String> keys = valueMap.keySet();
		for(String key :keys){
			if(key.startsWith(pName + "_")){ //$NON-NLS-1$
				map.put(key, valueMap.get(key));
			}
		}
		return map;
	}
	
	private Object getValues4ListProperty(EObject object, String pName){
		EList<EStructuralFeature> featureList = object.eClass()
		.getEAllStructuralFeatures();
		for (EStructuralFeature eStructuralFeature : featureList) {
			if(eStructuralFeature.getName().equals(pName))
				return object.eGet(eStructuralFeature);
		}
		return null;
	}
	
	private String getTypePackageName() {
		return ((Class) getEMFCharacter()).getPackage().getQualifiedName().replaceAll("::", ".");
	}
	
	private Object getValueObject(EFactory factory, EStructuralFeature eStructuralFeature){
		String featureName = eStructuralFeature.getName();
		for (int i = 0; i < subElements.length; i++) {
			if(featureName.equals(subElements[i].getName())){
				if(subElements[i].getType() instanceof Enumeration){
					return factory.createFromString(((EAttribute)eStructuralFeature).getEAttributeType(), 
							((EnumerationLiteral)((BomVariableElement)subElements[i]).getValue()).getName());
				}else{ 
					return ((BomVariableElement)subElements[i]).getValue();
				}
			}
		}
		return null;
	}
	
	private boolean isListProperty(Property property){
		int upper = property.getUpper();
		return upper>1 || upper==-1;
	}
	
	private EObject updateValue()throws TransformationException{
		// get EFactory
		String packageName = getTypePackageName();
		EPackage ePackage = BomProjectManager.INSTANCE.getEPackage(packageName);
		if (ePackage == null) {
			return null;
		}
		EFactory factory = ePackage.getEFactoryInstance();
		EObject object = (EObject)value;
		if (factory != null) {
			try {
				EClass eClass = (EClass) ePackage.getEClassifier(((Type)getType()).getName());
				if(object == null){
					object = factory.create(eClass);
					value = object;
				}
				// set features
				EList<EStructuralFeature> featureList = eClass.getEAllStructuralFeatures();
				for (EStructuralFeature eStructuralFeature : featureList) {
					Object value = object.eGet(eStructuralFeature);
					if(value instanceof EList){
						((EList)value).addAll((Collection)getValueObject(factory, eStructuralFeature));
					}else{
						Object v = getValueObject(factory, eStructuralFeature);
						if (v != null)
							object.eSet(eStructuralFeature, v);
					}
				}
			}catch (Exception e) {
				throw new TransformationException(Messages.getString("ClassVariableElement.TransformationException.message"), e); //$NON-NLS-1$
			}
		}
		return (EObject)value;
	}
	
	@Override
	public boolean isValid(){
		boolean valid = true;
		for(IVariableElement element : subElements){
			valid = valid && element.isValid();
		}
		return valid;
	}
}
