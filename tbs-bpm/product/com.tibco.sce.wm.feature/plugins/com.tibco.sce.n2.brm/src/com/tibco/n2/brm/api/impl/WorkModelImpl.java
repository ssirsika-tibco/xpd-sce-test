/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.AttributeAliasListType;
import com.tibco.n2.brm.api.BaseModelInfo;
import com.tibco.n2.brm.api.ItemPrivilege;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkModel;
import com.tibco.n2.brm.api.WorkModelEntities;
import com.tibco.n2.brm.api.WorkModelScripts;
import com.tibco.n2.brm.api.WorkModelSpecification;
import com.tibco.n2.brm.api.WorkModelTypes;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelImpl#getBaseModelInfo <em>Base Model Info</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelImpl#getWorkModelSpecification <em>Work Model Specification</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelImpl#getWorkModelEntities <em>Work Model Entities</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelImpl#getWorkModelTypes <em>Work Model Types</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelImpl#getItemPrivileges <em>Item Privileges</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelImpl#getWorkModelScripts <em>Work Model Scripts</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelImpl#getAttributeAliasList <em>Attribute Alias List</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelImpl#getWorkModelUID <em>Work Model UID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkModelImpl extends EObjectImpl implements WorkModel {
    /**
     * The cached value of the '{@link #getBaseModelInfo() <em>Base Model Info</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBaseModelInfo()
     * @generated
     * @ordered
     */
    protected BaseModelInfo baseModelInfo;

    /**
     * The cached value of the '{@link #getWorkModelSpecification() <em>Work Model Specification</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelSpecification()
     * @generated
     * @ordered
     */
    protected WorkModelSpecification workModelSpecification;

    /**
     * The cached value of the '{@link #getWorkModelEntities() <em>Work Model Entities</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelEntities()
     * @generated
     * @ordered
     */
    protected WorkModelEntities workModelEntities;

    /**
     * The cached value of the '{@link #getWorkModelTypes() <em>Work Model Types</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelTypes()
     * @generated
     * @ordered
     */
    protected WorkModelTypes workModelTypes;

    /**
     * The cached value of the '{@link #getItemPrivileges() <em>Item Privileges</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getItemPrivileges()
     * @generated
     * @ordered
     */
    protected ItemPrivilege itemPrivileges;

    /**
     * The cached value of the '{@link #getWorkModelScripts() <em>Work Model Scripts</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelScripts()
     * @generated
     * @ordered
     */
    protected WorkModelScripts workModelScripts;

    /**
     * The cached value of the '{@link #getAttributeAliasList() <em>Attribute Alias List</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeAliasList()
     * @generated
     * @ordered
     */
    protected AttributeAliasListType attributeAliasList;

    /**
     * The default value of the '{@link #getWorkModelUID() <em>Work Model UID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelUID()
     * @generated
     * @ordered
     */
    protected static final String WORK_MODEL_UID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWorkModelUID() <em>Work Model UID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkModelUID()
     * @generated
     * @ordered
     */
    protected String workModelUID = WORK_MODEL_UID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkModelImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_MODEL;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BaseModelInfo getBaseModelInfo() {
        return baseModelInfo;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBaseModelInfo(BaseModelInfo newBaseModelInfo, NotificationChain msgs) {
        BaseModelInfo oldBaseModelInfo = baseModelInfo;
        baseModelInfo = newBaseModelInfo;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__BASE_MODEL_INFO, oldBaseModelInfo, newBaseModelInfo);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBaseModelInfo(BaseModelInfo newBaseModelInfo) {
        if (newBaseModelInfo != baseModelInfo) {
            NotificationChain msgs = null;
            if (baseModelInfo != null)
                msgs = ((InternalEObject)baseModelInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__BASE_MODEL_INFO, null, msgs);
            if (newBaseModelInfo != null)
                msgs = ((InternalEObject)newBaseModelInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__BASE_MODEL_INFO, null, msgs);
            msgs = basicSetBaseModelInfo(newBaseModelInfo, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__BASE_MODEL_INFO, newBaseModelInfo, newBaseModelInfo));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelSpecification getWorkModelSpecification() {
        return workModelSpecification;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkModelSpecification(WorkModelSpecification newWorkModelSpecification, NotificationChain msgs) {
        WorkModelSpecification oldWorkModelSpecification = workModelSpecification;
        workModelSpecification = newWorkModelSpecification;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__WORK_MODEL_SPECIFICATION, oldWorkModelSpecification, newWorkModelSpecification);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkModelSpecification(WorkModelSpecification newWorkModelSpecification) {
        if (newWorkModelSpecification != workModelSpecification) {
            NotificationChain msgs = null;
            if (workModelSpecification != null)
                msgs = ((InternalEObject)workModelSpecification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__WORK_MODEL_SPECIFICATION, null, msgs);
            if (newWorkModelSpecification != null)
                msgs = ((InternalEObject)newWorkModelSpecification).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__WORK_MODEL_SPECIFICATION, null, msgs);
            msgs = basicSetWorkModelSpecification(newWorkModelSpecification, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__WORK_MODEL_SPECIFICATION, newWorkModelSpecification, newWorkModelSpecification));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelEntities getWorkModelEntities() {
        return workModelEntities;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkModelEntities(WorkModelEntities newWorkModelEntities, NotificationChain msgs) {
        WorkModelEntities oldWorkModelEntities = workModelEntities;
        workModelEntities = newWorkModelEntities;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__WORK_MODEL_ENTITIES, oldWorkModelEntities, newWorkModelEntities);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkModelEntities(WorkModelEntities newWorkModelEntities) {
        if (newWorkModelEntities != workModelEntities) {
            NotificationChain msgs = null;
            if (workModelEntities != null)
                msgs = ((InternalEObject)workModelEntities).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__WORK_MODEL_ENTITIES, null, msgs);
            if (newWorkModelEntities != null)
                msgs = ((InternalEObject)newWorkModelEntities).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__WORK_MODEL_ENTITIES, null, msgs);
            msgs = basicSetWorkModelEntities(newWorkModelEntities, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__WORK_MODEL_ENTITIES, newWorkModelEntities, newWorkModelEntities));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelTypes getWorkModelTypes() {
        return workModelTypes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkModelTypes(WorkModelTypes newWorkModelTypes, NotificationChain msgs) {
        WorkModelTypes oldWorkModelTypes = workModelTypes;
        workModelTypes = newWorkModelTypes;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__WORK_MODEL_TYPES, oldWorkModelTypes, newWorkModelTypes);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkModelTypes(WorkModelTypes newWorkModelTypes) {
        if (newWorkModelTypes != workModelTypes) {
            NotificationChain msgs = null;
            if (workModelTypes != null)
                msgs = ((InternalEObject)workModelTypes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__WORK_MODEL_TYPES, null, msgs);
            if (newWorkModelTypes != null)
                msgs = ((InternalEObject)newWorkModelTypes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__WORK_MODEL_TYPES, null, msgs);
            msgs = basicSetWorkModelTypes(newWorkModelTypes, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__WORK_MODEL_TYPES, newWorkModelTypes, newWorkModelTypes));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemPrivilege getItemPrivileges() {
        return itemPrivileges;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetItemPrivileges(ItemPrivilege newItemPrivileges, NotificationChain msgs) {
        ItemPrivilege oldItemPrivileges = itemPrivileges;
        itemPrivileges = newItemPrivileges;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__ITEM_PRIVILEGES, oldItemPrivileges, newItemPrivileges);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setItemPrivileges(ItemPrivilege newItemPrivileges) {
        if (newItemPrivileges != itemPrivileges) {
            NotificationChain msgs = null;
            if (itemPrivileges != null)
                msgs = ((InternalEObject)itemPrivileges).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__ITEM_PRIVILEGES, null, msgs);
            if (newItemPrivileges != null)
                msgs = ((InternalEObject)newItemPrivileges).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__ITEM_PRIVILEGES, null, msgs);
            msgs = basicSetItemPrivileges(newItemPrivileges, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__ITEM_PRIVILEGES, newItemPrivileges, newItemPrivileges));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkModelScripts getWorkModelScripts() {
        return workModelScripts;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkModelScripts(WorkModelScripts newWorkModelScripts, NotificationChain msgs) {
        WorkModelScripts oldWorkModelScripts = workModelScripts;
        workModelScripts = newWorkModelScripts;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__WORK_MODEL_SCRIPTS, oldWorkModelScripts, newWorkModelScripts);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkModelScripts(WorkModelScripts newWorkModelScripts) {
        if (newWorkModelScripts != workModelScripts) {
            NotificationChain msgs = null;
            if (workModelScripts != null)
                msgs = ((InternalEObject)workModelScripts).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__WORK_MODEL_SCRIPTS, null, msgs);
            if (newWorkModelScripts != null)
                msgs = ((InternalEObject)newWorkModelScripts).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__WORK_MODEL_SCRIPTS, null, msgs);
            msgs = basicSetWorkModelScripts(newWorkModelScripts, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__WORK_MODEL_SCRIPTS, newWorkModelScripts, newWorkModelScripts));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeAliasListType getAttributeAliasList() {
        return attributeAliasList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAttributeAliasList(AttributeAliasListType newAttributeAliasList, NotificationChain msgs) {
        AttributeAliasListType oldAttributeAliasList = attributeAliasList;
        attributeAliasList = newAttributeAliasList;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__ATTRIBUTE_ALIAS_LIST, oldAttributeAliasList, newAttributeAliasList);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAttributeAliasList(AttributeAliasListType newAttributeAliasList) {
        if (newAttributeAliasList != attributeAliasList) {
            NotificationChain msgs = null;
            if (attributeAliasList != null)
                msgs = ((InternalEObject)attributeAliasList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__ATTRIBUTE_ALIAS_LIST, null, msgs);
            if (newAttributeAliasList != null)
                msgs = ((InternalEObject)newAttributeAliasList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_MODEL__ATTRIBUTE_ALIAS_LIST, null, msgs);
            msgs = basicSetAttributeAliasList(newAttributeAliasList, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__ATTRIBUTE_ALIAS_LIST, newAttributeAliasList, newAttributeAliasList));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWorkModelUID() {
        return workModelUID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkModelUID(String newWorkModelUID) {
        String oldWorkModelUID = workModelUID;
        workModelUID = newWorkModelUID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL__WORK_MODEL_UID, oldWorkModelUID, workModelUID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.WORK_MODEL__BASE_MODEL_INFO:
                return basicSetBaseModelInfo(null, msgs);
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_SPECIFICATION:
                return basicSetWorkModelSpecification(null, msgs);
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_ENTITIES:
                return basicSetWorkModelEntities(null, msgs);
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_TYPES:
                return basicSetWorkModelTypes(null, msgs);
            case N2BRMPackage.WORK_MODEL__ITEM_PRIVILEGES:
                return basicSetItemPrivileges(null, msgs);
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_SCRIPTS:
                return basicSetWorkModelScripts(null, msgs);
            case N2BRMPackage.WORK_MODEL__ATTRIBUTE_ALIAS_LIST:
                return basicSetAttributeAliasList(null, msgs);
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
            case N2BRMPackage.WORK_MODEL__BASE_MODEL_INFO:
                return getBaseModelInfo();
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_SPECIFICATION:
                return getWorkModelSpecification();
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_ENTITIES:
                return getWorkModelEntities();
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_TYPES:
                return getWorkModelTypes();
            case N2BRMPackage.WORK_MODEL__ITEM_PRIVILEGES:
                return getItemPrivileges();
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_SCRIPTS:
                return getWorkModelScripts();
            case N2BRMPackage.WORK_MODEL__ATTRIBUTE_ALIAS_LIST:
                return getAttributeAliasList();
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_UID:
                return getWorkModelUID();
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
            case N2BRMPackage.WORK_MODEL__BASE_MODEL_INFO:
                setBaseModelInfo((BaseModelInfo)newValue);
                return;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_SPECIFICATION:
                setWorkModelSpecification((WorkModelSpecification)newValue);
                return;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_ENTITIES:
                setWorkModelEntities((WorkModelEntities)newValue);
                return;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_TYPES:
                setWorkModelTypes((WorkModelTypes)newValue);
                return;
            case N2BRMPackage.WORK_MODEL__ITEM_PRIVILEGES:
                setItemPrivileges((ItemPrivilege)newValue);
                return;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_SCRIPTS:
                setWorkModelScripts((WorkModelScripts)newValue);
                return;
            case N2BRMPackage.WORK_MODEL__ATTRIBUTE_ALIAS_LIST:
                setAttributeAliasList((AttributeAliasListType)newValue);
                return;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_UID:
                setWorkModelUID((String)newValue);
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
            case N2BRMPackage.WORK_MODEL__BASE_MODEL_INFO:
                setBaseModelInfo((BaseModelInfo)null);
                return;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_SPECIFICATION:
                setWorkModelSpecification((WorkModelSpecification)null);
                return;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_ENTITIES:
                setWorkModelEntities((WorkModelEntities)null);
                return;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_TYPES:
                setWorkModelTypes((WorkModelTypes)null);
                return;
            case N2BRMPackage.WORK_MODEL__ITEM_PRIVILEGES:
                setItemPrivileges((ItemPrivilege)null);
                return;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_SCRIPTS:
                setWorkModelScripts((WorkModelScripts)null);
                return;
            case N2BRMPackage.WORK_MODEL__ATTRIBUTE_ALIAS_LIST:
                setAttributeAliasList((AttributeAliasListType)null);
                return;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_UID:
                setWorkModelUID(WORK_MODEL_UID_EDEFAULT);
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
            case N2BRMPackage.WORK_MODEL__BASE_MODEL_INFO:
                return baseModelInfo != null;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_SPECIFICATION:
                return workModelSpecification != null;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_ENTITIES:
                return workModelEntities != null;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_TYPES:
                return workModelTypes != null;
            case N2BRMPackage.WORK_MODEL__ITEM_PRIVILEGES:
                return itemPrivileges != null;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_SCRIPTS:
                return workModelScripts != null;
            case N2BRMPackage.WORK_MODEL__ATTRIBUTE_ALIAS_LIST:
                return attributeAliasList != null;
            case N2BRMPackage.WORK_MODEL__WORK_MODEL_UID:
                return WORK_MODEL_UID_EDEFAULT == null ? workModelUID != null : !WORK_MODEL_UID_EDEFAULT.equals(workModelUID);
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
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (workModelUID: ");
        result.append(workModelUID);
        result.append(')');
        return result.toString();
    }

} //WorkModelImpl
