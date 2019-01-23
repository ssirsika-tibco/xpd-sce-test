/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Privilege Association</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PrivilegeAssociationImpl#getQualifierValue <em>Qualifier Value</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.PrivilegeAssociationImpl#getPrivilege <em>Privilege</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PrivilegeAssociationImpl extends EObjectImpl implements
        PrivilegeAssociation {
    /**
     * The cached value of the '{@link #getQualifierValue() <em>Qualifier Value</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQualifierValue()
     * @generated
     * @ordered
     */
    protected AttributeValue qualifierValue;

    /**
     * The cached value of the '{@link #getPrivilege() <em>Privilege</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrivilege()
     * @generated
     * @ordered
     */
    protected Privilege privilege;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PrivilegeAssociationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.PRIVILEGE_ASSOCIATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeValue getQualifierValue() {
        return qualifierValue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetQualifierValue(
            AttributeValue newQualifierValue, NotificationChain msgs) {
        AttributeValue oldQualifierValue = qualifierValue;
        qualifierValue = newQualifierValue;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            OMPackage.PRIVILEGE_ASSOCIATION__QUALIFIER_VALUE,
                            oldQualifierValue, newQualifierValue);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setQualifierValue(AttributeValue newQualifierValue) {
        if (newQualifierValue != qualifierValue) {
            NotificationChain msgs = null;
            if (qualifierValue != null)
                msgs =
                        ((InternalEObject) qualifierValue)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - OMPackage.PRIVILEGE_ASSOCIATION__QUALIFIER_VALUE,
                                        null,
                                        msgs);
            if (newQualifierValue != null)
                msgs =
                        ((InternalEObject) newQualifierValue)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - OMPackage.PRIVILEGE_ASSOCIATION__QUALIFIER_VALUE,
                                        null,
                                        msgs);
            msgs = basicSetQualifierValue(newQualifierValue, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.PRIVILEGE_ASSOCIATION__QUALIFIER_VALUE,
                    newQualifierValue, newQualifierValue));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Privilege getPrivilege() {
        if (privilege != null && privilege.eIsProxy()) {
            InternalEObject oldPrivilege = (InternalEObject) privilege;
            privilege = (Privilege) eResolveProxy(oldPrivilege);
            if (privilege != oldPrivilege) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.PRIVILEGE_ASSOCIATION__PRIVILEGE,
                            oldPrivilege, privilege));
            }
        }
        return privilege;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Privilege basicGetPrivilege() {
        return privilege;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPrivilege(Privilege newPrivilege) {
        Privilege oldPrivilege = privilege;
        privilege = newPrivilege;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.PRIVILEGE_ASSOCIATION__PRIVILEGE, oldPrivilege,
                    privilege));
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
        case OMPackage.PRIVILEGE_ASSOCIATION__QUALIFIER_VALUE:
            return basicSetQualifierValue(null, msgs);
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
        case OMPackage.PRIVILEGE_ASSOCIATION__QUALIFIER_VALUE:
            return getQualifierValue();
        case OMPackage.PRIVILEGE_ASSOCIATION__PRIVILEGE:
            if (resolve)
                return getPrivilege();
            return basicGetPrivilege();
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
        case OMPackage.PRIVILEGE_ASSOCIATION__QUALIFIER_VALUE:
            setQualifierValue((AttributeValue) newValue);
            return;
        case OMPackage.PRIVILEGE_ASSOCIATION__PRIVILEGE:
            setPrivilege((Privilege) newValue);
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
        case OMPackage.PRIVILEGE_ASSOCIATION__QUALIFIER_VALUE:
            setQualifierValue((AttributeValue) null);
            return;
        case OMPackage.PRIVILEGE_ASSOCIATION__PRIVILEGE:
            setPrivilege((Privilege) null);
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
        case OMPackage.PRIVILEGE_ASSOCIATION__QUALIFIER_VALUE:
            return qualifierValue != null;
        case OMPackage.PRIVILEGE_ASSOCIATION__PRIVILEGE:
            return privilege != null;
        }
        return super.eIsSet(featureID);
    }

} //PrivilegeAssociationImpl
