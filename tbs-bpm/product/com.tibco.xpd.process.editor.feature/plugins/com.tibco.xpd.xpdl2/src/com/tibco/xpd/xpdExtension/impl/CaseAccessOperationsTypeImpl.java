/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CreateCaseOperationType;
import com.tibco.xpd.xpdExtension.CreateType;
import com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType;
import com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.ExternalReference;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Case Access Operations Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseAccessOperationsTypeImpl#getCaseClassExternalReference <em>Case Class External Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseAccessOperationsTypeImpl#getCreate <em>Create</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseAccessOperationsTypeImpl#getDeleteByCaseIdentifier <em>Delete By Case Identifier</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseAccessOperationsTypeImpl#getDeleteByCompositeIdentifiers <em>Delete By Composite Identifiers</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CaseAccessOperationsTypeImpl extends EObjectImpl implements CaseAccessOperationsType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getCaseClassExternalReference() <em>Case Class External Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCaseClassExternalReference()
     * @generated
     * @ordered
     */
    protected ExternalReference caseClassExternalReference;

    /**
     * The cached value of the '{@link #getCreate() <em>Create</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCreate()
     * @generated
     * @ordered
     */
    protected CreateCaseOperationType create;

    /**
     * The cached value of the '{@link #getDeleteByCaseIdentifier() <em>Delete By Case Identifier</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeleteByCaseIdentifier()
     * @generated
     * @ordered
     */
    protected DeleteByCaseIdentifierType deleteByCaseIdentifier;

    /**
     * The cached value of the '{@link #getDeleteByCompositeIdentifiers() <em>Delete By Composite Identifiers</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeleteByCompositeIdentifiers()
     * @generated
     * @ordered
     */
    protected DeleteByCompositeIdentifiersType deleteByCompositeIdentifiers;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CaseAccessOperationsTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.CASE_ACCESS_OPERATIONS_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExternalReference getCaseClassExternalReference() {
        return caseClassExternalReference;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCaseClassExternalReference(ExternalReference newCaseClassExternalReference,
            NotificationChain msgs) {
        ExternalReference oldCaseClassExternalReference = caseClassExternalReference;
        caseClassExternalReference = newCaseClassExternalReference;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CASE_CLASS_EXTERNAL_REFERENCE,
                    oldCaseClassExternalReference, newCaseClassExternalReference);
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
    public void setCaseClassExternalReference(ExternalReference newCaseClassExternalReference) {
        if (newCaseClassExternalReference != caseClassExternalReference) {
            NotificationChain msgs = null;
            if (caseClassExternalReference != null)
                msgs = ((InternalEObject) caseClassExternalReference).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CASE_CLASS_EXTERNAL_REFERENCE,
                        null,
                        msgs);
            if (newCaseClassExternalReference != null)
                msgs = ((InternalEObject) newCaseClassExternalReference).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CASE_CLASS_EXTERNAL_REFERENCE,
                        null,
                        msgs);
            msgs = basicSetCaseClassExternalReference(newCaseClassExternalReference, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CASE_CLASS_EXTERNAL_REFERENCE,
                    newCaseClassExternalReference, newCaseClassExternalReference));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CreateCaseOperationType getCreate() {
        return create;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCreate(CreateCaseOperationType newCreate, NotificationChain msgs) {
        CreateCaseOperationType oldCreate = create;
        create = newCreate;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CREATE, oldCreate, newCreate);
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
    public void setCreate(CreateCaseOperationType newCreate) {
        if (newCreate != create) {
            NotificationChain msgs = null;
            if (create != null)
                msgs = ((InternalEObject) create).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CREATE,
                        null,
                        msgs);
            if (newCreate != null)
                msgs = ((InternalEObject) newCreate).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CREATE,
                        null,
                        msgs);
            msgs = basicSetCreate(newCreate, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CREATE, newCreate, newCreate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteByCaseIdentifierType getDeleteByCaseIdentifier() {
        return deleteByCaseIdentifier;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeleteByCaseIdentifier(DeleteByCaseIdentifierType newDeleteByCaseIdentifier,
            NotificationChain msgs) {
        DeleteByCaseIdentifierType oldDeleteByCaseIdentifier = deleteByCaseIdentifier;
        deleteByCaseIdentifier = newDeleteByCaseIdentifier;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_CASE_IDENTIFIER,
                    oldDeleteByCaseIdentifier, newDeleteByCaseIdentifier);
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
    public void setDeleteByCaseIdentifier(DeleteByCaseIdentifierType newDeleteByCaseIdentifier) {
        if (newDeleteByCaseIdentifier != deleteByCaseIdentifier) {
            NotificationChain msgs = null;
            if (deleteByCaseIdentifier != null)
                msgs = ((InternalEObject) deleteByCaseIdentifier).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_CASE_IDENTIFIER,
                        null,
                        msgs);
            if (newDeleteByCaseIdentifier != null)
                msgs = ((InternalEObject) newDeleteByCaseIdentifier).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_CASE_IDENTIFIER,
                        null,
                        msgs);
            msgs = basicSetDeleteByCaseIdentifier(newDeleteByCaseIdentifier, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_CASE_IDENTIFIER,
                    newDeleteByCaseIdentifier, newDeleteByCaseIdentifier));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteByCompositeIdentifiersType getDeleteByCompositeIdentifiers() {
        return deleteByCompositeIdentifiers;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeleteByCompositeIdentifiers(
            DeleteByCompositeIdentifiersType newDeleteByCompositeIdentifiers, NotificationChain msgs) {
        DeleteByCompositeIdentifiersType oldDeleteByCompositeIdentifiers = deleteByCompositeIdentifiers;
        deleteByCompositeIdentifiers = newDeleteByCompositeIdentifiers;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_COMPOSITE_IDENTIFIERS,
                    oldDeleteByCompositeIdentifiers, newDeleteByCompositeIdentifiers);
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
    public void setDeleteByCompositeIdentifiers(DeleteByCompositeIdentifiersType newDeleteByCompositeIdentifiers) {
        if (newDeleteByCompositeIdentifiers != deleteByCompositeIdentifiers) {
            NotificationChain msgs = null;
            if (deleteByCompositeIdentifiers != null)
                msgs = ((InternalEObject) deleteByCompositeIdentifiers).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_COMPOSITE_IDENTIFIERS,
                        null,
                        msgs);
            if (newDeleteByCompositeIdentifiers != null)
                msgs = ((InternalEObject) newDeleteByCompositeIdentifiers).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE
                                - XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_COMPOSITE_IDENTIFIERS,
                        null,
                        msgs);
            msgs = basicSetDeleteByCompositeIdentifiers(newDeleteByCompositeIdentifiers, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_COMPOSITE_IDENTIFIERS,
                    newDeleteByCompositeIdentifiers, newDeleteByCompositeIdentifiers));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CASE_CLASS_EXTERNAL_REFERENCE:
            return basicSetCaseClassExternalReference(null, msgs);
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CREATE:
            return basicSetCreate(null, msgs);
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_CASE_IDENTIFIER:
            return basicSetDeleteByCaseIdentifier(null, msgs);
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_COMPOSITE_IDENTIFIERS:
            return basicSetDeleteByCompositeIdentifiers(null, msgs);
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
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CASE_CLASS_EXTERNAL_REFERENCE:
            return getCaseClassExternalReference();
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CREATE:
            return getCreate();
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_CASE_IDENTIFIER:
            return getDeleteByCaseIdentifier();
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_COMPOSITE_IDENTIFIERS:
            return getDeleteByCompositeIdentifiers();
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
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CASE_CLASS_EXTERNAL_REFERENCE:
            setCaseClassExternalReference((ExternalReference) newValue);
            return;
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CREATE:
            setCreate((CreateCaseOperationType) newValue);
            return;
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_CASE_IDENTIFIER:
            setDeleteByCaseIdentifier((DeleteByCaseIdentifierType) newValue);
            return;
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_COMPOSITE_IDENTIFIERS:
            setDeleteByCompositeIdentifiers((DeleteByCompositeIdentifiersType) newValue);
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
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CASE_CLASS_EXTERNAL_REFERENCE:
            setCaseClassExternalReference((ExternalReference) null);
            return;
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CREATE:
            setCreate((CreateCaseOperationType) null);
            return;
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_CASE_IDENTIFIER:
            setDeleteByCaseIdentifier((DeleteByCaseIdentifierType) null);
            return;
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_COMPOSITE_IDENTIFIERS:
            setDeleteByCompositeIdentifiers((DeleteByCompositeIdentifiersType) null);
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
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CASE_CLASS_EXTERNAL_REFERENCE:
            return caseClassExternalReference != null;
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__CREATE:
            return create != null;
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_CASE_IDENTIFIER:
            return deleteByCaseIdentifier != null;
        case XpdExtensionPackage.CASE_ACCESS_OPERATIONS_TYPE__DELETE_BY_COMPOSITE_IDENTIFIERS:
            return deleteByCompositeIdentifiers != null;
        }
        return super.eIsSet(featureID);
    }

} //CaseAccessOperationsTypeImpl
