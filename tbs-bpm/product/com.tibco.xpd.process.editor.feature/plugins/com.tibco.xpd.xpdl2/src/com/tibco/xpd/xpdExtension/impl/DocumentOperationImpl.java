/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.CaseDocFindOperations;
import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentOperationImpl#getCaseDocRefOperation <em>Case Doc Ref Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentOperationImpl#getCaseDocFindOperations <em>Case Doc Find Operations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DocumentOperationImpl#getLinkSystemDocumentOperation <em>Link System Document Operation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DocumentOperationImpl extends EObjectImpl implements DocumentOperation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getCaseDocRefOperation() <em>Case Doc Ref Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCaseDocRefOperation()
     * @generated
     * @ordered
     */
    protected CaseDocRefOperations caseDocRefOperation;

    /**
     * The cached value of the '{@link #getCaseDocFindOperations() <em>Case Doc Find Operations</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCaseDocFindOperations()
     * @generated
     * @ordered
     */
    protected CaseDocFindOperations caseDocFindOperations;

    /**
     * The cached value of the '{@link #getLinkSystemDocumentOperation() <em>Link System Document Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLinkSystemDocumentOperation()
     * @generated
     * @ordered
     */
    protected LinkSystemDocumentOperation linkSystemDocumentOperation;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DocumentOperationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.DOCUMENT_OPERATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CaseDocRefOperations getCaseDocRefOperation() {
        return caseDocRefOperation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCaseDocRefOperation(CaseDocRefOperations newCaseDocRefOperation,
            NotificationChain msgs) {
        CaseDocRefOperations oldCaseDocRefOperation = caseDocRefOperation;
        caseDocRefOperation = newCaseDocRefOperation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_REF_OPERATION, oldCaseDocRefOperation,
                    newCaseDocRefOperation);
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
    public void setCaseDocRefOperation(CaseDocRefOperations newCaseDocRefOperation) {
        if (newCaseDocRefOperation != caseDocRefOperation) {
            NotificationChain msgs = null;
            if (caseDocRefOperation != null)
                msgs = ((InternalEObject) caseDocRefOperation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_REF_OPERATION,
                        null,
                        msgs);
            if (newCaseDocRefOperation != null)
                msgs = ((InternalEObject) newCaseDocRefOperation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_REF_OPERATION,
                        null,
                        msgs);
            msgs = basicSetCaseDocRefOperation(newCaseDocRefOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_REF_OPERATION, newCaseDocRefOperation,
                    newCaseDocRefOperation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CaseDocFindOperations getCaseDocFindOperations() {
        return caseDocFindOperations;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCaseDocFindOperations(CaseDocFindOperations newCaseDocFindOperations,
            NotificationChain msgs) {
        CaseDocFindOperations oldCaseDocFindOperations = caseDocFindOperations;
        caseDocFindOperations = newCaseDocFindOperations;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_FIND_OPERATIONS, oldCaseDocFindOperations,
                    newCaseDocFindOperations);
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
    public void setCaseDocFindOperations(CaseDocFindOperations newCaseDocFindOperations) {
        if (newCaseDocFindOperations != caseDocFindOperations) {
            NotificationChain msgs = null;
            if (caseDocFindOperations != null)
                msgs = ((InternalEObject) caseDocFindOperations).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_FIND_OPERATIONS,
                        null,
                        msgs);
            if (newCaseDocFindOperations != null)
                msgs = ((InternalEObject) newCaseDocFindOperations).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_FIND_OPERATIONS,
                        null,
                        msgs);
            msgs = basicSetCaseDocFindOperations(newCaseDocFindOperations, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_FIND_OPERATIONS, newCaseDocFindOperations,
                    newCaseDocFindOperations));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LinkSystemDocumentOperation getLinkSystemDocumentOperation() {
        return linkSystemDocumentOperation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLinkSystemDocumentOperation(
            LinkSystemDocumentOperation newLinkSystemDocumentOperation, NotificationChain msgs) {
        LinkSystemDocumentOperation oldLinkSystemDocumentOperation = linkSystemDocumentOperation;
        linkSystemDocumentOperation = newLinkSystemDocumentOperation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DOCUMENT_OPERATION__LINK_SYSTEM_DOCUMENT_OPERATION,
                    oldLinkSystemDocumentOperation, newLinkSystemDocumentOperation);
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
    public void setLinkSystemDocumentOperation(LinkSystemDocumentOperation newLinkSystemDocumentOperation) {
        if (newLinkSystemDocumentOperation != linkSystemDocumentOperation) {
            NotificationChain msgs = null;
            if (linkSystemDocumentOperation != null)
                msgs = ((InternalEObject) linkSystemDocumentOperation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.DOCUMENT_OPERATION__LINK_SYSTEM_DOCUMENT_OPERATION,
                        null,
                        msgs);
            if (newLinkSystemDocumentOperation != null)
                msgs = ((InternalEObject) newLinkSystemDocumentOperation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.DOCUMENT_OPERATION__LINK_SYSTEM_DOCUMENT_OPERATION,
                        null,
                        msgs);
            msgs = basicSetLinkSystemDocumentOperation(newLinkSystemDocumentOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.DOCUMENT_OPERATION__LINK_SYSTEM_DOCUMENT_OPERATION,
                    newLinkSystemDocumentOperation, newLinkSystemDocumentOperation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_REF_OPERATION:
            return basicSetCaseDocRefOperation(null, msgs);
        case XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_FIND_OPERATIONS:
            return basicSetCaseDocFindOperations(null, msgs);
        case XpdExtensionPackage.DOCUMENT_OPERATION__LINK_SYSTEM_DOCUMENT_OPERATION:
            return basicSetLinkSystemDocumentOperation(null, msgs);
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
        case XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_REF_OPERATION:
            return getCaseDocRefOperation();
        case XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_FIND_OPERATIONS:
            return getCaseDocFindOperations();
        case XpdExtensionPackage.DOCUMENT_OPERATION__LINK_SYSTEM_DOCUMENT_OPERATION:
            return getLinkSystemDocumentOperation();
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
        case XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_REF_OPERATION:
            setCaseDocRefOperation((CaseDocRefOperations) newValue);
            return;
        case XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_FIND_OPERATIONS:
            setCaseDocFindOperations((CaseDocFindOperations) newValue);
            return;
        case XpdExtensionPackage.DOCUMENT_OPERATION__LINK_SYSTEM_DOCUMENT_OPERATION:
            setLinkSystemDocumentOperation((LinkSystemDocumentOperation) newValue);
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
        case XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_REF_OPERATION:
            setCaseDocRefOperation((CaseDocRefOperations) null);
            return;
        case XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_FIND_OPERATIONS:
            setCaseDocFindOperations((CaseDocFindOperations) null);
            return;
        case XpdExtensionPackage.DOCUMENT_OPERATION__LINK_SYSTEM_DOCUMENT_OPERATION:
            setLinkSystemDocumentOperation((LinkSystemDocumentOperation) null);
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
        case XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_REF_OPERATION:
            return caseDocRefOperation != null;
        case XpdExtensionPackage.DOCUMENT_OPERATION__CASE_DOC_FIND_OPERATIONS:
            return caseDocFindOperations != null;
        case XpdExtensionPackage.DOCUMENT_OPERATION__LINK_SYSTEM_DOCUMENT_OPERATION:
            return linkSystemDocumentOperation != null;
        }
        return super.eIsSet(featureID);
    }

} //DocumentOperationImpl
