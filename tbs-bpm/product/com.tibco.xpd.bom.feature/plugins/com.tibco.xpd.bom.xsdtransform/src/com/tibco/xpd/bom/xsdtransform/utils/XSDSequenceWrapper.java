package com.tibco.xpd.bom.xsdtransform.utils;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper;

/**
 * @author glewis 
 *         Wrapper for setting and getting all explicit group information. The
 *         UML has property in the XSDBasedClass stereotype which contains a list of
 *         sequences/choices (i.e. list of XsdSequence classes) and so this class is
 *         used as a single entry point for setting and getting attributes
 *         corresponding to the explicit group details.
 */
public class XSDSequenceWrapper {
    /**
     * the actual sequence EObject (the UML Class instance itself)
     */
    private EObject xsdSequence;

    /**
     * EClass representing the UML Class for xsdSequence
     */
    private EClass xsdSequenceUMLEClass;

    /**
     * @param eObj
     */
    public XSDSequenceWrapper(EObject eObj) {
        this(eObj.eResource().getResourceSet());
        xsdSequence = eObj;
    }

    /**
     * Constructor setting the xsdSequence EClass specific for the resource set
     * and its applied xsd notation profile
     * 
     * @param rSet
     * 
     */
    protected XSDSequenceWrapper(ResourceSet rSet) {
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
                                    .getEClassifier(XsdStereotypeUtils.XSD_SEQUENCE);
                    if (classifier instanceof EClass) {
                        xsdSequenceUMLEClass = (EClass) classifier;
                    }
                }
            }
        }
    }

    /**
     * @param rSet
     * @return
     */
    public static XSDSequenceWrapper create(ResourceSet rSet) {
        XSDSequenceWrapper inst = new XSDSequenceWrapper(rSet);
        inst.xsdSequence = EcoreUtil.create(inst.xsdSequenceUMLEClass);

        return inst;
    }

    public EObject getEObject() {
        return xsdSequence;
    }

    /**
     * @param featureName
     * @return
     */
    private Object get(String featureName) {
        EStructuralFeature feature =
            xsdSequenceUMLEClass.getEStructuralFeature(featureName);

        if (feature != null) {
            return xsdSequence.eGet(feature);
        }
        return null;
    }

    /**
     * @param featureName
     * @param value
     */
    private void set(String featureName, Object value) {
        EStructuralFeature feature =
            xsdSequenceUMLEClass.getEStructuralFeature(featureName);

        if (feature != null) {
            xsdSequence.eSet(feature, value);
        }
    }

    /**
     * @return
     */
    public String getName() {
        Object value = get("name"); //$NON-NLS-1$
        if (value instanceof String) {
            return (String) value;
        } else {
            return null;
        }
    }

    /**
     * @return
     */
    public int getMinOccurs() {
        Object value = get("minOccurs"); //$NON-NLS-1$
        if (value instanceof Integer) {
            return ((Integer) value).intValue();
        } else {
            return 1;
        }
    }

    /**
     * @return
     */
    public int getMaxOccurs() {
        Object value = get("maxOccurs"); //$NON-NLS-1$
        if (value instanceof Integer) {
            return ((Integer) value).intValue();
        } else {
            return 1;
        }
    }

    /**
     * @return
     */
    public EObject getParent() {
        Object value = get("parent"); //$NON-NLS-1$
        if (value instanceof EObject) {
            return (EObject) value;
        } else {
            return null;
        }
    }
    
    /**
     * @return
     */
    public EList<EObject> getChildren() {
        Object value = get("children"); //$NON-NLS-1$
        if (value instanceof EList) {
            return (EList<EObject>) value;
        } else {
            return null;
        }
    }
    
    /**
     * @return
     */
    public XSDSequenceWrapper getParentSequence(){
        EObject parent = getParent();
        XSDSequenceWrapper parentSequence = null;
        if (parent != null){
            return new XSDSequenceWrapper(parent);
        }
        return parentSequence;
    }

    /**
     * @return
     */
    public boolean isChoice() {
        Object value = get("isChoice"); //$NON-NLS-1$
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        } else {
            return false;
        }
    }
    
    /**
     * @return
     */
    public boolean isAll() {
        Object value = get("isAll"); //$NON-NLS-1$
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        } else {
            return false;
        }
    }

    /**
     * @param value
     */
    public void setName(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("name", value); //$NON-NLS-1$
        }
    }

    /**
     * @param value
     */
    public void setMinOccurs(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("minOccurs", value); //$NON-NLS-1$
        }
    }

    /**
     * @param value
     */
    public void setMaxOccurs(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("maxOccurs", value); //$NON-NLS-1$
        }
    }

    /**
     * @param value
     */
    public void setParent(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("parent", value); //$NON-NLS-1$
        }
    }    

    /**
     * @param value
     */
    public void setIsChoice(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("isChoice", value); //$NON-NLS-1$
        }
    }
    
    /**
     * @param value
     */
    public void setIsAll(Object value) {
        if (value != null && !value.equals("null")) { //$NON-NLS-1$
            set("isAll", value); //$NON-NLS-1$
        }
    }
    
    /**
     * @return
     */
    public int getDepth(){
        String[] depth_pos = getName().replace("MS", "").split("_"); //$NON-NLS-1$ //$NON-NLS-2$
        return new Integer(depth_pos[0].trim()).intValue();
    }
    
    /**
     * @return
     */
    public int getExplicitGroupPosition(){
        String[] explicit_group_pos = getName().replace("MS", "").split("_"); //$NON-NLS-1$ //$NON-NLS-2$
        return new Integer(explicit_group_pos[1].trim()).intValue();
    }
    
    /**
     * @return
     */
    public int getElementPosition(){
        String[] elem_pos = getName().replace("MS", "").split("_"); //$NON-NLS-1$ //$NON-NLS-2$
        return new Integer(elem_pos[2].trim()).intValue();
    }
}