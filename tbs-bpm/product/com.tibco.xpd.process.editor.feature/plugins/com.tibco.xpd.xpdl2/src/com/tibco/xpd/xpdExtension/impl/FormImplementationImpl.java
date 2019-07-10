/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Form Implementation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.FormImplementationImpl#getFormType <em>Form Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.FormImplementationImpl#getFormURI <em>Form URI</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FormImplementationImpl extends EObjectImpl implements FormImplementation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getFormType() <em>Form Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormType()
     * @generated
     * @ordered
     */
    protected static final FormImplementationType FORM_TYPE_EDEFAULT = FormImplementationType.USER_DEFINED;

    /**
     * The cached value of the '{@link #getFormType() <em>Form Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormType()
     * @generated
     * @ordered
     */
    protected FormImplementationType formType = FORM_TYPE_EDEFAULT;

    /**
     * This is true if the Form Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean formTypeESet;

    /**
     * The default value of the '{@link #getFormURI() <em>Form URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormURI()
     * @generated
     * @ordered
     */
    protected static final String FORM_URI_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFormURI() <em>Form URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFormURI()
     * @generated
     * @ordered
     */
    protected String formURI = FORM_URI_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FormImplementationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.FORM_IMPLEMENTATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FormImplementationType getFormType() {
        return formType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFormType(FormImplementationType newFormType) {
        FormImplementationType oldFormType = formType;
        formType = newFormType == null ? FORM_TYPE_EDEFAULT : newFormType;
        boolean oldFormTypeESet = formTypeESet;
        formTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.FORM_IMPLEMENTATION__FORM_TYPE,
                    oldFormType, formType, !oldFormTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetFormType() {
        FormImplementationType oldFormType = formType;
        boolean oldFormTypeESet = formTypeESet;
        formType = FORM_TYPE_EDEFAULT;
        formTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.FORM_IMPLEMENTATION__FORM_TYPE,
                    oldFormType, FORM_TYPE_EDEFAULT, oldFormTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetFormType() {
        return formTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFormURI() {
        return formURI;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFormURI(String newFormURI) {
        String oldFormURI = formURI;
        formURI = newFormURI;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.FORM_IMPLEMENTATION__FORM_URI,
                    oldFormURI, formURI));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.FORM_IMPLEMENTATION__FORM_TYPE:
            return getFormType();
        case XpdExtensionPackage.FORM_IMPLEMENTATION__FORM_URI:
            return getFormURI();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.FORM_IMPLEMENTATION__FORM_TYPE:
            setFormType((FormImplementationType) newValue);
            return;
        case XpdExtensionPackage.FORM_IMPLEMENTATION__FORM_URI:
            setFormURI((String) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.FORM_IMPLEMENTATION__FORM_TYPE:
            unsetFormType();
            return;
        case XpdExtensionPackage.FORM_IMPLEMENTATION__FORM_URI:
            setFormURI(FORM_URI_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.FORM_IMPLEMENTATION__FORM_TYPE:
            return isSetFormType();
        case XpdExtensionPackage.FORM_IMPLEMENTATION__FORM_URI:
            return FORM_URI_EDEFAULT == null ? formURI != null : !FORM_URI_EDEFAULT.equals(formURI);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (formType: "); //$NON-NLS-1$
        if (formTypeESet)
            result.append(formType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", formURI: "); //$NON-NLS-1$
        result.append(formURI);
        result.append(')');
        return result.toString();
    }

} //FormImplementationImpl
