/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.AddLinkAssociationsType;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.DeleteCaseReferenceOperationType;
import com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType;
import com.tibco.xpd.xpdExtension.UpdateCaseOperationType;
import com.tibco.xpd.xpdExtension.UpdateType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Case Reference Operations Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseReferenceOperationsTypeImpl#getCaseRefField <em>Case Ref Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseReferenceOperationsTypeImpl#getUpdate <em>Update</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseReferenceOperationsTypeImpl#getDelete <em>Delete</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseReferenceOperationsTypeImpl#getAddLinkAssociations <em>Add Link Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseReferenceOperationsTypeImpl#getRemoveLinkAssociations <em>Remove Link Associations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CaseReferenceOperationsTypeImpl extends EObjectImpl
        implements CaseReferenceOperationsType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getCaseRefField() <em>Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCaseRefField()
     * @generated
     * @ordered
     */
    protected static final String CASE_REF_FIELD_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCaseRefField() <em>Case Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCaseRefField()
     * @generated
     * @ordered
     */
    protected String caseRefField = CASE_REF_FIELD_EDEFAULT;

    /**
     * The cached value of the '{@link #getUpdate() <em>Update</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUpdate()
     * @generated
     * @ordered
     */
    protected UpdateCaseOperationType update;

    /**
     * The cached value of the '{@link #getDelete() <em>Delete</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDelete()
     * @generated
     * @ordered
     */
    protected DeleteCaseReferenceOperationType delete;

    /**
     * The cached value of the '{@link #getAddLinkAssociations() <em>Add Link Associations</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAddLinkAssociations()
     * @generated
     * @ordered
     */
    protected AddLinkAssociationsType addLinkAssociations;

    /**
     * The cached value of the '{@link #getRemoveLinkAssociations() <em>Remove Link Associations</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRemoveLinkAssociations()
     * @generated
     * @ordered
     */
    protected RemoveLinkAssociationsType removeLinkAssociations;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CaseReferenceOperationsTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.CASE_REFERENCE_OPERATIONS_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCaseRefField() {
        return caseRefField;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCaseRefField(String newCaseRefField) {
        String oldCaseRefField = caseRefField;
        caseRefField = newCaseRefField;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__CASE_REF_FIELD,
                    oldCaseRefField, caseRefField));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UpdateCaseOperationType getUpdate() {
        return update;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetUpdate(UpdateCaseOperationType newUpdate,
            NotificationChain msgs) {
        UpdateCaseOperationType oldUpdate = update;
        update = newUpdate;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__UPDATE,
                    oldUpdate, newUpdate);
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
    public void setUpdate(UpdateCaseOperationType newUpdate) {
        if (newUpdate != update) {
            NotificationChain msgs = null;
            if (update != null)
                msgs = ((InternalEObject) update).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__UPDATE,
                        null,
                        msgs);
            if (newUpdate != null)
                msgs = ((InternalEObject) newUpdate).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__UPDATE,
                        null,
                        msgs);
            msgs = basicSetUpdate(newUpdate, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__UPDATE,
                    newUpdate, newUpdate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteCaseReferenceOperationType getDelete() {
        return delete;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDelete(
            DeleteCaseReferenceOperationType newDelete,
            NotificationChain msgs) {
        DeleteCaseReferenceOperationType oldDelete = delete;
        delete = newDelete;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__DELETE,
                    oldDelete, newDelete);
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
    public void setDelete(DeleteCaseReferenceOperationType newDelete) {
        if (newDelete != delete) {
            NotificationChain msgs = null;
            if (delete != null)
                msgs = ((InternalEObject) delete).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__DELETE,
                        null,
                        msgs);
            if (newDelete != null)
                msgs = ((InternalEObject) newDelete).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__DELETE,
                        null,
                        msgs);
            msgs = basicSetDelete(newDelete, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__DELETE,
                    newDelete, newDelete));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AddLinkAssociationsType getAddLinkAssociations() {
        return addLinkAssociations;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetAddLinkAssociations(
            AddLinkAssociationsType newAddLinkAssociations,
            NotificationChain msgs) {
        AddLinkAssociationsType oldAddLinkAssociations = addLinkAssociations;
        addLinkAssociations = newAddLinkAssociations;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS,
                    oldAddLinkAssociations, newAddLinkAssociations);
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
    public void setAddLinkAssociations(
            AddLinkAssociationsType newAddLinkAssociations) {
        if (newAddLinkAssociations != addLinkAssociations) {
            NotificationChain msgs = null;
            if (addLinkAssociations != null)
                msgs = ((InternalEObject) addLinkAssociations).eInverseRemove(
                        this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS,
                        null,
                        msgs);
            if (newAddLinkAssociations != null)
                msgs = ((InternalEObject) newAddLinkAssociations).eInverseAdd(
                        this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS,
                        null,
                        msgs);
            msgs = basicSetAddLinkAssociations(newAddLinkAssociations, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS,
                    newAddLinkAssociations, newAddLinkAssociations));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RemoveLinkAssociationsType getRemoveLinkAssociations() {
        return removeLinkAssociations;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRemoveLinkAssociations(
            RemoveLinkAssociationsType newRemoveLinkAssociations,
            NotificationChain msgs) {
        RemoveLinkAssociationsType oldRemoveLinkAssociations =
                removeLinkAssociations;
        removeLinkAssociations = newRemoveLinkAssociations;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this,
                    Notification.SET,
                    XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS,
                    oldRemoveLinkAssociations, newRemoveLinkAssociations);
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
    public void setRemoveLinkAssociations(
            RemoveLinkAssociationsType newRemoveLinkAssociations) {
        if (newRemoveLinkAssociations != removeLinkAssociations) {
            NotificationChain msgs = null;
            if (removeLinkAssociations != null)
                msgs = ((InternalEObject) removeLinkAssociations)
                        .eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS,
                                null,
                                msgs);
            if (newRemoveLinkAssociations != null)
                msgs = ((InternalEObject) newRemoveLinkAssociations)
                        .eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS,
                                null,
                                msgs);
            msgs = basicSetRemoveLinkAssociations(newRemoveLinkAssociations,
                    msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS,
                    newRemoveLinkAssociations, newRemoveLinkAssociations));
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
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__UPDATE:
            return basicSetUpdate(null, msgs);
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__DELETE:
            return basicSetDelete(null, msgs);
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS:
            return basicSetAddLinkAssociations(null, msgs);
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS:
            return basicSetRemoveLinkAssociations(null, msgs);
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
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__CASE_REF_FIELD:
            return getCaseRefField();
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__UPDATE:
            return getUpdate();
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__DELETE:
            return getDelete();
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS:
            return getAddLinkAssociations();
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS:
            return getRemoveLinkAssociations();
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
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__CASE_REF_FIELD:
            setCaseRefField((String) newValue);
            return;
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__UPDATE:
            setUpdate((UpdateCaseOperationType) newValue);
            return;
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__DELETE:
            setDelete((DeleteCaseReferenceOperationType) newValue);
            return;
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS:
            setAddLinkAssociations((AddLinkAssociationsType) newValue);
            return;
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS:
            setRemoveLinkAssociations((RemoveLinkAssociationsType) newValue);
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
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__CASE_REF_FIELD:
            setCaseRefField(CASE_REF_FIELD_EDEFAULT);
            return;
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__UPDATE:
            setUpdate((UpdateCaseOperationType) null);
            return;
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__DELETE:
            setDelete((DeleteCaseReferenceOperationType) null);
            return;
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS:
            setAddLinkAssociations((AddLinkAssociationsType) null);
            return;
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS:
            setRemoveLinkAssociations((RemoveLinkAssociationsType) null);
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
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__CASE_REF_FIELD:
            return CASE_REF_FIELD_EDEFAULT == null ? caseRefField != null
                    : !CASE_REF_FIELD_EDEFAULT.equals(caseRefField);
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__UPDATE:
            return update != null;
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__DELETE:
            return delete != null;
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS:
            return addLinkAssociations != null;
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS:
            return removeLinkAssociations != null;
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
        result.append(" (caseRefField: "); //$NON-NLS-1$
        result.append(caseRefField);
        result.append(')');
        return result.toString();
    }

} //CaseReferenceOperationsTypeImpl
