/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Associated Correlation Fields</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldsImpl#getAssociatedCorrelationField <em>Associated Correlation Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AssociatedCorrelationFieldsImpl#isDisableImplicitAssociation <em>Disable Implicit Association</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssociatedCorrelationFieldsImpl extends EObjectImpl
        implements AssociatedCorrelationFields {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getAssociatedCorrelationField() <em>Associated Correlation Field</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAssociatedCorrelationField()
     * @generated
     * @ordered
     */
    protected EList<AssociatedCorrelationField> associatedCorrelationField;

    /**
     * The default value of the '{@link #isDisableImplicitAssociation() <em>Disable Implicit Association</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDisableImplicitAssociation()
     * @generated
     * @ordered
     */
    protected static final boolean DISABLE_IMPLICIT_ASSOCIATION_EDEFAULT =
            false;

    /**
     * The cached value of the '{@link #isDisableImplicitAssociation() <em>Disable Implicit Association</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDisableImplicitAssociation()
     * @generated
     * @ordered
     */
    protected boolean disableImplicitAssociation =
            DISABLE_IMPLICIT_ASSOCIATION_EDEFAULT;

    /**
     * This is true if the Disable Implicit Association attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean disableImplicitAssociationESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AssociatedCorrelationFieldsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.ASSOCIATED_CORRELATION_FIELDS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<AssociatedCorrelationField> getAssociatedCorrelationField() {
        if (associatedCorrelationField == null) {
            associatedCorrelationField =
                    new EObjectContainmentEList<AssociatedCorrelationField>(
                            AssociatedCorrelationField.class, this,
                            XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS__ASSOCIATED_CORRELATION_FIELD);
        }
        return associatedCorrelationField;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isDisableImplicitAssociation() {
        return disableImplicitAssociation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDisableImplicitAssociation(
            boolean newDisableImplicitAssociation) {
        boolean oldDisableImplicitAssociation = disableImplicitAssociation;
        disableImplicitAssociation = newDisableImplicitAssociation;
        boolean oldDisableImplicitAssociationESet =
                disableImplicitAssociationESet;
        disableImplicitAssociationESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS__DISABLE_IMPLICIT_ASSOCIATION,
                    oldDisableImplicitAssociation, disableImplicitAssociation,
                    !oldDisableImplicitAssociationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDisableImplicitAssociation() {
        boolean oldDisableImplicitAssociation = disableImplicitAssociation;
        boolean oldDisableImplicitAssociationESet =
                disableImplicitAssociationESet;
        disableImplicitAssociation = DISABLE_IMPLICIT_ASSOCIATION_EDEFAULT;
        disableImplicitAssociationESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS__DISABLE_IMPLICIT_ASSOCIATION,
                    oldDisableImplicitAssociation,
                    DISABLE_IMPLICIT_ASSOCIATION_EDEFAULT,
                    oldDisableImplicitAssociationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDisableImplicitAssociation() {
        return disableImplicitAssociationESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS__ASSOCIATED_CORRELATION_FIELD:
            return ((InternalEList<?>) getAssociatedCorrelationField())
                    .basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS__ASSOCIATED_CORRELATION_FIELD:
            return getAssociatedCorrelationField();
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS__DISABLE_IMPLICIT_ASSOCIATION:
            return isDisableImplicitAssociation();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS__ASSOCIATED_CORRELATION_FIELD:
            getAssociatedCorrelationField().clear();
            getAssociatedCorrelationField().addAll(
                    (Collection<? extends AssociatedCorrelationField>) newValue);
            return;
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS__DISABLE_IMPLICIT_ASSOCIATION:
            setDisableImplicitAssociation((Boolean) newValue);
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
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS__ASSOCIATED_CORRELATION_FIELD:
            getAssociatedCorrelationField().clear();
            return;
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS__DISABLE_IMPLICIT_ASSOCIATION:
            unsetDisableImplicitAssociation();
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
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS__ASSOCIATED_CORRELATION_FIELD:
            return associatedCorrelationField != null
                    && !associatedCorrelationField.isEmpty();
        case XpdExtensionPackage.ASSOCIATED_CORRELATION_FIELDS__DISABLE_IMPLICIT_ASSOCIATION:
            return isSetDisableImplicitAssociation();
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

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (disableImplicitAssociation: "); //$NON-NLS-1$
        if (disableImplicitAssociationESet)
            result.append(disableImplicitAssociation);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //AssociatedCorrelationFieldsImpl
