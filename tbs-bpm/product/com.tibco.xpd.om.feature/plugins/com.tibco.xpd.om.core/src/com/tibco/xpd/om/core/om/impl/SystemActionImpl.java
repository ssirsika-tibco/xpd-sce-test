/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import com.tibco.xpd.om.core.om.ModelElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.SystemAction;
import com.tibco.xpd.om.core.om.SystemActionIdentifier;

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
 * An implementation of the model object '<em><b>System Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.SystemActionImpl#getPrivilegeAssociations <em>Privilege Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.SystemActionImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.SystemActionImpl#getActionId <em>Action Id</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.SystemActionImpl#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SystemActionImpl extends EObjectImpl implements SystemAction {
    /**
     * The cached value of the '{@link #getPrivilegeAssociations() <em>Privilege Associations</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrivilegeAssociations()
     * @generated
     * @ordered
     */
    protected EList<PrivilegeAssociation> privilegeAssociations;

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = ""; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getActionId() <em>Action Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActionId()
     * @generated
     * @ordered
     */
    protected static final String ACTION_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getActionId() <em>Action Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActionId()
     * @generated
     * @ordered
     */
    protected String actionId = ACTION_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getComponent() <em>Component</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getComponent()
     * @generated
     * @ordered
     */
    protected static final String COMPONENT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getComponent() <em>Component</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getComponent()
     * @generated
     * @ordered
     */
    protected String component = COMPONENT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SystemActionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.SYSTEM_ACTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PrivilegeAssociation> getPrivilegeAssociations() {
        if (privilegeAssociations == null) {
            privilegeAssociations =
                    new EObjectContainmentEList<PrivilegeAssociation>(
                            PrivilegeAssociation.class, this,
                            OMPackage.SYSTEM_ACTION__PRIVILEGE_ASSOCIATIONS);
        }
        return privilegeAssociations;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        // TODO: implement this method to return the 'Id' attribute
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getActionId() {
        return actionId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActionId(String newActionId) {
        String oldActionId = actionId;
        actionId = newActionId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.SYSTEM_ACTION__ACTION_ID, oldActionId, actionId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getComponent() {
        return component;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setComponent(String newComponent) {
        String oldComponent = component;
        component = newComponent;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.SYSTEM_ACTION__COMPONENT, oldComponent, component));
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
        case OMPackage.SYSTEM_ACTION__PRIVILEGE_ASSOCIATIONS:
            return ((InternalEList<?>) getPrivilegeAssociations())
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
        case OMPackage.SYSTEM_ACTION__PRIVILEGE_ASSOCIATIONS:
            return getPrivilegeAssociations();
        case OMPackage.SYSTEM_ACTION__ID:
            return getId();
        case OMPackage.SYSTEM_ACTION__ACTION_ID:
            return getActionId();
        case OMPackage.SYSTEM_ACTION__COMPONENT:
            return getComponent();
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
        case OMPackage.SYSTEM_ACTION__PRIVILEGE_ASSOCIATIONS:
            getPrivilegeAssociations().clear();
            getPrivilegeAssociations()
                    .addAll((Collection<? extends PrivilegeAssociation>) newValue);
            return;
        case OMPackage.SYSTEM_ACTION__ACTION_ID:
            setActionId((String) newValue);
            return;
        case OMPackage.SYSTEM_ACTION__COMPONENT:
            setComponent((String) newValue);
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
        case OMPackage.SYSTEM_ACTION__PRIVILEGE_ASSOCIATIONS:
            getPrivilegeAssociations().clear();
            return;
        case OMPackage.SYSTEM_ACTION__ACTION_ID:
            setActionId(ACTION_ID_EDEFAULT);
            return;
        case OMPackage.SYSTEM_ACTION__COMPONENT:
            setComponent(COMPONENT_EDEFAULT);
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
        case OMPackage.SYSTEM_ACTION__PRIVILEGE_ASSOCIATIONS:
            return privilegeAssociations != null
                    && !privilegeAssociations.isEmpty();
        case OMPackage.SYSTEM_ACTION__ID:
            return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT
                    .equals(getId());
        case OMPackage.SYSTEM_ACTION__ACTION_ID:
            return ACTION_ID_EDEFAULT == null ? actionId != null
                    : !ACTION_ID_EDEFAULT.equals(actionId);
        case OMPackage.SYSTEM_ACTION__COMPONENT:
            return COMPONENT_EDEFAULT == null ? component != null
                    : !COMPONENT_EDEFAULT.equals(component);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == ModelElement.class) {
            switch (derivedFeatureID) {
            case OMPackage.SYSTEM_ACTION__ID:
                return OMPackage.MODEL_ELEMENT__ID;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == ModelElement.class) {
            switch (baseFeatureID) {
            case OMPackage.MODEL_ELEMENT__ID:
                return OMPackage.SYSTEM_ACTION__ID;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(" (actionId: "); //$NON-NLS-1$
        result.append(actionId);
        result.append(", component: "); //$NON-NLS-1$
        result.append(component);
        result.append(')');
        return result.toString();
    }

} //SystemActionImpl
