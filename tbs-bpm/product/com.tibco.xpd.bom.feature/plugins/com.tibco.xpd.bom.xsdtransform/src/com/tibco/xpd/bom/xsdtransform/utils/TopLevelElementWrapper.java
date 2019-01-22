package com.tibco.xpd.bom.xsdtransform.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper;

/**
 * @author glewis 
 * 
 * Wrapper for setting and getting all top level elements for the current Package. The
 *         UML has a Top Level Element stereotype which contains a list of
 *         TopLevelElement classes) and so this class is
 *         used as a single entry point for setting and getting attributes
 *         corresponding to the top level element.
 */
public class TopLevelElementWrapper {
    /**
     * the actual top level element EObject (the UML Class instance itself)
     */
    private EObject topLevelElement;

    /**
     * EClass representing the UML Class for topLevelElement
     */
    private EClass topLevelElementUMLEClass;

    /**
     * @param eObj
     */
    public TopLevelElementWrapper(EObject eObj) {
        this(eObj.eResource().getResourceSet());
        topLevelElement = eObj;
    }

    /**
     * Constructor setting the topLevelElement EClass specific for the resource set
     * and its applied xsd notation profile
     * 
     * @param rSet
     * 
     */
    protected TopLevelElementWrapper(ResourceSet rSet) {
        Resource res =
                rSet.getResource(URI
                        .createURI(TransformHelper.XSD_NOTATION_URI), true);
        if (res != null) {
            EObject object = res.getContents().get(0);

            if (object instanceof Profile) {
                Profile profile = (Profile) object;

                if (profile.getName()
                        .equals(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME)) {
                    EClassifier classifier =
                            profile
                                    .getDefinition()
                                    .getEClassifier(XsdStereotypeUtils.TOP_LEVEL_ELEMENT);
                    if (classifier instanceof EClass) {
                    	topLevelElementUMLEClass = (EClass) classifier;
                    }
                }
            }
        }
    }

    /**
     * @param rSet
     * @return
     */
    public static TopLevelElementWrapper create(ResourceSet rSet) {
        TopLevelElementWrapper inst = new TopLevelElementWrapper(rSet);
        inst.topLevelElement = EcoreUtil.create(inst.topLevelElementUMLEClass);

        return inst;
    }

    public EObject getEObject() {
        return topLevelElement;
    }

    /**
     * @param featureName
     * @return
     */
    private Object get(String featureName) {
        EStructuralFeature feature =
        	topLevelElementUMLEClass.getEStructuralFeature(featureName);

        if (feature != null) {
            return topLevelElement.eGet(feature);
        }
        return null;
    }

    /**
     * @param featureName
     * @param value
     */
    private void set(String featureName, Object value) {
        EStructuralFeature feature =
        	topLevelElementUMLEClass.getEStructuralFeature(featureName);

        if (feature != null) {
        	topLevelElement.eSet(feature, value);
        }
    }

    public String getName() {
        Object value = get("name"); //$NON-NLS-1$
        if (value instanceof String) {
            return (String) value;
        } else {
            return null;
        }
    }
    
    public Classifier getType() {
        Object value = get("type"); //$NON-NLS-1$
        if (value instanceof Classifier) {
            return (Classifier) value;
        } else {
            return null;
        }
    }
    
    public void setName(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("name", value); //$NON-NLS-1$
        }
    } 
    
    public void setType(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("type", value); //$NON-NLS-1$
        }
    }   
    
    public String getTargetNamespace() {
        Object value = get("targetNamespace"); //$NON-NLS-1$
        if (value instanceof String) {
            return (String) value;
        } else {
            return null;
        }
    }

    public Boolean getIsAnonymous() {
        Object value = get("isAnonymous"); //$NON-NLS-1$
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            return null;
        }
    }

    public Boolean getIsBaseXSDType() {
        Object value = get("isBaseXSDType"); //$NON-NLS-1$
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            return null;
        }
    }

    public String getID() {
        Object value = get("id"); //$NON-NLS-1$
        if (value instanceof String) {
            return (String) value;
        } else {
            return null;
        }
    }

    public String getFixed() {
        Object value = get("fixed"); //$NON-NLS-1$
        if (value instanceof String) {
            return (String) value;
        } else {
            return null;
        }
    }

    public String getFinal() {
        Object value = get("final"); //$NON-NLS-1$
        if (value instanceof String) {
            return (String) value;
        } else {
            return null;
        }
    }

    public String getBlock() {
        Object value = get("block"); //$NON-NLS-1$
        if (value instanceof String) {
            return (String) value;
        } else {
            return null;
        }
    }

    public Boolean getNillable() {
        Object value = get("nillable"); //$NON-NLS-1$
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            return null;
        }
    }

    public Boolean getAbstract() {
        Object value = get("abstract"); //$NON-NLS-1$
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            return null;
        }
    }

    public String getSubstitutionGroup() {
        Object value = get("substitutionGroup"); //$NON-NLS-1$
        if (value instanceof String && ((String) value).trim().length() > 0) {
            return (String) value;
        } else {
            return null;
        }
    }

    public String getDefault() {
        Object value = get("defaultVal"); //$NON-NLS-1$
        if (value instanceof String) {
            return (String) value;
        } else {
            return null;
        }
    }

    public void setTargetNamespace(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("targetNamespace", value); //$NON-NLS-1$
        }
    }

    public void setIsAnonymous(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("isAnonymous", value); //$NON-NLS-1$
        }
    }

    public void setisBaseXSDType(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("isBaseXSDType", value); //$NON-NLS-1$
        }
    }

    public void setID(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("id", value); //$NON-NLS-1$
        }
    }

    public void setFixed(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("fixed", value); //$NON-NLS-1$
        }
    }

    public void setFinal(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("final", value); //$NON-NLS-1$
        }
    }

    public void setBlock(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("block", value); //$NON-NLS-1$
        }
    }

    public void setNillable(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("nillable", value); //$NON-NLS-1$
        }
    }

    public void setAbstract(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("abstract", value); //$NON-NLS-1$
        }
    }

    public void setSubstitutionGroup(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("substitutionGroup", value); //$NON-NLS-1$
        }
    }

    public void setDefault(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("defaultVal", value); //$NON-NLS-1$
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TopLevelElementWrapper){
            TopLevelElementWrapper tmpWrapper = (TopLevelElementWrapper)obj;
            if (tmpWrapper.getName().equals(getName()) && tmpWrapper.getIsAnonymous().equals(getIsAnonymous()) && tmpWrapper.getTargetNamespace().equals(getTargetNamespace()) && tmpWrapper.getIsBaseXSDType().equals(getIsBaseXSDType())){
                return true;
            }
            return false;
        }else{
            return super.equals(obj);
        }
    }
}