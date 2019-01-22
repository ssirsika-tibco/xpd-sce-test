/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.core.om.impl;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.om.core.om.Namespace;
import com.tibco.xpd.om.core.om.util.OMUtil;

/**
 * 
 * <p>
 * <i>Created: 11 Feb 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class FeatureNamespaceImpl extends EObjectImpl implements Namespace {
    private final Namespace namespace;

    private final EStructuralFeature feature;

    /**
     * 
     */
    public FeatureNamespaceImpl(Namespace namespace, EStructuralFeature feature) {
        this.namespace = namespace;
        this.feature = feature;

    }

    /**
     * @return
     * @see org.eclipse.emf.common.notify.Notifier#eAdapters()
     */
    @Override
    public EList<Adapter> eAdapters() {
        return namespace.eAdapters();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eAllContents()
     */
    @Override
    public TreeIterator<EObject> eAllContents() {
        return namespace.eAllContents();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eClass()
     */
    @Override
    public EClass eClass() {
        return namespace.eClass();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eContainer()
     */
    @Override
    public EObject eContainer() {
        return namespace.eContainer();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eContainingFeature()
     */
    @Override
    public EStructuralFeature eContainingFeature() {
        return namespace.eContainingFeature();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eContainmentFeature()
     */
    @Override
    public EReference eContainmentFeature() {
        return namespace.eContainmentFeature();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eContents()
     */
    @Override
    public EList<EObject> eContents() {
        return namespace.eContents();
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eCrossReferences()
     */
    @Override
    public EList<EObject> eCrossReferences() {
        return namespace.eCrossReferences();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.notify.Notifier#eDeliver()
     */
    @Override
    public boolean eDeliver() {
        return namespace.eDeliver();
    }

    /**
     * @param feature
     * @param resolve
     * @return
     * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature,
     *      boolean)
     */
    @Override
    public Object eGet(EStructuralFeature feature, boolean resolve) {
        return namespace.eGet(feature, resolve);
    }

    /**
     * @param feature
     * @return
     * @see org.eclipse.emf.ecore.EObject#eGet(org.eclipse.emf.ecore.EStructuralFeature)
     */
    @Override
    public Object eGet(EStructuralFeature feature) {
        return namespace.eGet(feature);
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eIsProxy()
     */
    @Override
    public boolean eIsProxy() {
        return namespace.eIsProxy();
    }

    /**
     * @param feature
     * @return
     * @see org.eclipse.emf.ecore.EObject#eIsSet(org.eclipse.emf.ecore.EStructuralFeature)
     */
    @Override
    public boolean eIsSet(EStructuralFeature feature) {
        return namespace.eIsSet(feature);
    }

    /**
     * @param notification
     * @see org.eclipse.emf.common.notify.Notifier#eNotify(org.eclipse.emf.common.notify.Notification)
     */
    @Override
    public void eNotify(Notification notification) {
        namespace.eNotify(notification);
    }

    /**
     * @return
     * @see org.eclipse.emf.ecore.EObject#eResource()
     */
    @Override
    public Resource eResource() {
        return namespace.eResource();
    }

    /**
     * @param feature
     * @param newValue
     * @see org.eclipse.emf.ecore.EObject#eSet(org.eclipse.emf.ecore.EStructuralFeature,
     *      java.lang.Object)
     */
    @Override
    public void eSet(EStructuralFeature feature, Object newValue) {
        namespace.eSet(feature, newValue);
    }

    /**
     * @param deliver
     * @see org.eclipse.emf.common.notify.Notifier#eSetDeliver(boolean)
     */
    @Override
    public void eSetDeliver(boolean deliver) {
        namespace.eSetDeliver(deliver);
    }

    /**
     * @param feature
     * @see org.eclipse.emf.ecore.EObject#eUnset(org.eclipse.emf.ecore.EStructuralFeature)
     */
    @Override
    public void eUnset(EStructuralFeature feature) {
        namespace.eUnset(feature);
    }

    /**
     * @return
     * @see com.tibco.xpd.om.core.om.NamedElement#getLabel()
     */
    @Override
    public String getLabel() {
        return namespace.getLabel();
    }

    /**
     * @param localize
     * @return
     * @see com.tibco.xpd.om.core.om.NamedElement#getLabel(boolean)
     */
    @Override
    public String getLabel(boolean localize) {
        return namespace.getLabel(localize);
    }

    /**
     * @return
     * @see com.tibco.xpd.om.core.om.NamedElement#getName()
     */
    @Override
    public String getName() {
        String featureName = feature.getName();
        String namespaceName = namespace.getName();
        if (feature != null && featureName != null
                && featureName.trim().length() > 0) {
            return (namespaceName != null ? namespaceName : "") + '_' //$NON-NLS-1$
                    + featureName;
        }
        return namespaceName;
    }

    /**
     * @return
     * @see com.tibco.xpd.om.core.om.NamedElement#getNamespace()
     */
    @Override
    public Namespace getNamespace() {
        return namespace.getNamespace();
    }

    /**
     * @return
     * @see com.tibco.xpd.om.core.om.NamedElement#getQualifiedName()
     */
    @Override
    public String getQualifiedName() {
        return OMUtil.getQualifiedName(this);
    }

    /**
     * @param value
     * @see com.tibco.xpd.om.core.om.NamedElement#setName(java.lang.String)
     */
    @Override
    public void setName(String value) {
        namespace.setName(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.om.core.om.NamedElement#getLabelKey()
     */
    @Override
    public String getLabelKey() {
        return namespace.getLabelKey();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.om.core.om.ModelElement#getId()
     */
    @Override
    public String getId() {
        String featureName = feature.getName();
        String namespaceId = namespace.getId();
        if (feature != null && featureName != null
                && featureName.trim().length() > 0) {
            return namespaceId != null ? featureName + '_' + namespaceId : ""; //$NON-NLS-1$
        }
        return namespace.getId();
    }

    @Override
    public String getDisplayName() {
        String featureName = feature.getName();
        String namespaceName = namespace.getDisplayName();
        if (feature != null && featureName != null
                && featureName.trim().length() > 0) {
            return (namespaceName != null ? namespaceName : "") + '_' //$NON-NLS-1$
                    + featureName;
        }
        return namespaceName;
    }

    @Override
    public boolean isSetDisplayName() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setDisplayName(String value) {
        namespace.setDisplayName(value);

    }

    @Override
    public void unsetDisplayName() {
        // TODO Auto-generated method stub

    }
}
