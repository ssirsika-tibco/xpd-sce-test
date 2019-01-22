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
 * Wrapper for setting and getting all top level attributes for the current Package. The
 *         UML has a Top Level Attribute stereotype which contains a list of
 *         TopLevelAttribute classes) and so this class is
 *         used as a single entry point for setting and getting attributes
 *         corresponding to the top level attribute.
 */
public class TopLevelAttributeWrapper {
	/**
     * the actual top level Attribute EObject (the UML Class instance itself)
     */
    private EObject topLevelAttribute;

    /**
     * EClass representing the UML Class for topLevelAttribute
     */
    private EClass topLevelAttributeUMLEClass;

    /**
     * @param eObj
     */
    public TopLevelAttributeWrapper(EObject eObj) {
        this(eObj.eResource().getResourceSet());
        topLevelAttribute = eObj;
    }

    /**
     * Constructor setting the topLevelAttribute EClass specific for the resource set
     * and its applied xsd notation profile
     * 
     * @param rSet
     * 
     */
    protected TopLevelAttributeWrapper(ResourceSet rSet) {
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
                                    .getEClassifier(XsdStereotypeUtils.TOP_LEVEL_ATTRIBUTE);
                    if (classifier instanceof EClass) {
                    	topLevelAttributeUMLEClass = (EClass) classifier;
                    }
                }
            }
        }
    }

    /**
     * @param rSet
     * @return
     */
    public static TopLevelAttributeWrapper create(ResourceSet rSet) {
        TopLevelAttributeWrapper inst = new TopLevelAttributeWrapper(rSet);
        inst.topLevelAttribute = EcoreUtil.create(inst.topLevelAttributeUMLEClass);

        return inst;
    }

    public EObject getEObject() {
        return topLevelAttribute;
    }

    /**
     * @param featureName
     * @return
     */
    private Object get(String featureName) {
        EStructuralFeature feature =
        	topLevelAttributeUMLEClass.getEStructuralFeature(featureName);

        if (feature != null) {
            return topLevelAttribute.eGet(feature);
        }
        return null;
    }

    /**
     * @param featureName
     * @param value
     */
    private void set(String featureName, Object value) {
        EStructuralFeature feature =
        	topLevelAttributeUMLEClass.getEStructuralFeature(featureName);

        if (feature != null) {
        	topLevelAttribute.eSet(feature, value);
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

    public void setIsBaseXSDType(Object value) {
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

    public void setDefault(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("defaultVal", value); //$NON-NLS-1$
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TopLevelAttributeWrapper){
            TopLevelAttributeWrapper tmpWrapper = (TopLevelAttributeWrapper)obj;
            if (tmpWrapper.getName().equals(getName()) && tmpWrapper.getIsAnonymous().equals(getIsAnonymous()) && tmpWrapper.getTargetNamespace().equals(getTargetNamespace()) && tmpWrapper.getIsBaseXSDType().equals(getIsBaseXSDType())){
                return true;
            }
            return false;
        }else{
            return super.equals(obj);
        }
    }
}