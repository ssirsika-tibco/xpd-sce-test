/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.DeleteCaseDocOperation;
import com.tibco.xpd.xpdExtension.LinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.MoveCaseDocOperation;
import com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Case Doc Ref Operations</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocRefOperationsImpl#getMoveCaseDocOperation <em>Move Case Doc Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocRefOperationsImpl#getUnlinkCaseDocOperation <em>Unlink Case Doc Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocRefOperationsImpl#getLinkCaseDocOperation <em>Link Case Doc Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocRefOperationsImpl#getDeleteCaseDocOperation <em>Delete Case Doc Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocRefOperationsImpl#getCaseDocumentRefField <em>Case Document Ref Field</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CaseDocRefOperationsImpl extends EObjectImpl implements CaseDocRefOperations {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getMoveCaseDocOperation() <em>Move Case Doc Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMoveCaseDocOperation()
     * @generated
     * @ordered
     */
    protected MoveCaseDocOperation moveCaseDocOperation;

    /**
     * The cached value of the '{@link #getUnlinkCaseDocOperation() <em>Unlink Case Doc Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUnlinkCaseDocOperation()
     * @generated
     * @ordered
     */
    protected UnlinkCaseDocOperation unlinkCaseDocOperation;

    /**
     * The cached value of the '{@link #getLinkCaseDocOperation() <em>Link Case Doc Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLinkCaseDocOperation()
     * @generated
     * @ordered
     */
    protected LinkCaseDocOperation linkCaseDocOperation;

    /**
     * The cached value of the '{@link #getDeleteCaseDocOperation() <em>Delete Case Doc Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeleteCaseDocOperation()
     * @generated
     * @ordered
     */
    protected DeleteCaseDocOperation deleteCaseDocOperation;

    /**
     * The default value of the '{@link #getCaseDocumentRefField() <em>Case Document Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCaseDocumentRefField()
     * @generated
     * @ordered
     */
    protected static final String CASE_DOCUMENT_REF_FIELD_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCaseDocumentRefField() <em>Case Document Ref Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCaseDocumentRefField()
     * @generated
     * @ordered
     */
    protected String caseDocumentRefField = CASE_DOCUMENT_REF_FIELD_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CaseDocRefOperationsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.CASE_DOC_REF_OPERATIONS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MoveCaseDocOperation getMoveCaseDocOperation() {
        return moveCaseDocOperation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMoveCaseDocOperation(MoveCaseDocOperation newMoveCaseDocOperation,
            NotificationChain msgs) {
        MoveCaseDocOperation oldMoveCaseDocOperation = moveCaseDocOperation;
        moveCaseDocOperation = newMoveCaseDocOperation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION, oldMoveCaseDocOperation,
                    newMoveCaseDocOperation);
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
    public void setMoveCaseDocOperation(MoveCaseDocOperation newMoveCaseDocOperation) {
        if (newMoveCaseDocOperation != moveCaseDocOperation) {
            NotificationChain msgs = null;
            if (moveCaseDocOperation != null)
                msgs = ((InternalEObject) moveCaseDocOperation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION,
                        null,
                        msgs);
            if (newMoveCaseDocOperation != null)
                msgs = ((InternalEObject) newMoveCaseDocOperation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION,
                        null,
                        msgs);
            msgs = basicSetMoveCaseDocOperation(newMoveCaseDocOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION, newMoveCaseDocOperation,
                    newMoveCaseDocOperation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UnlinkCaseDocOperation getUnlinkCaseDocOperation() {
        return unlinkCaseDocOperation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetUnlinkCaseDocOperation(UnlinkCaseDocOperation newUnlinkCaseDocOperation,
            NotificationChain msgs) {
        UnlinkCaseDocOperation oldUnlinkCaseDocOperation = unlinkCaseDocOperation;
        unlinkCaseDocOperation = newUnlinkCaseDocOperation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION, oldUnlinkCaseDocOperation,
                    newUnlinkCaseDocOperation);
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
    public void setUnlinkCaseDocOperation(UnlinkCaseDocOperation newUnlinkCaseDocOperation) {
        if (newUnlinkCaseDocOperation != unlinkCaseDocOperation) {
            NotificationChain msgs = null;
            if (unlinkCaseDocOperation != null)
                msgs = ((InternalEObject) unlinkCaseDocOperation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION,
                        null,
                        msgs);
            if (newUnlinkCaseDocOperation != null)
                msgs = ((InternalEObject) newUnlinkCaseDocOperation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION,
                        null,
                        msgs);
            msgs = basicSetUnlinkCaseDocOperation(newUnlinkCaseDocOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION, newUnlinkCaseDocOperation,
                    newUnlinkCaseDocOperation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LinkCaseDocOperation getLinkCaseDocOperation() {
        return linkCaseDocOperation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLinkCaseDocOperation(LinkCaseDocOperation newLinkCaseDocOperation,
            NotificationChain msgs) {
        LinkCaseDocOperation oldLinkCaseDocOperation = linkCaseDocOperation;
        linkCaseDocOperation = newLinkCaseDocOperation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION, oldLinkCaseDocOperation,
                    newLinkCaseDocOperation);
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
    public void setLinkCaseDocOperation(LinkCaseDocOperation newLinkCaseDocOperation) {
        if (newLinkCaseDocOperation != linkCaseDocOperation) {
            NotificationChain msgs = null;
            if (linkCaseDocOperation != null)
                msgs = ((InternalEObject) linkCaseDocOperation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION,
                        null,
                        msgs);
            if (newLinkCaseDocOperation != null)
                msgs = ((InternalEObject) newLinkCaseDocOperation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION,
                        null,
                        msgs);
            msgs = basicSetLinkCaseDocOperation(newLinkCaseDocOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION, newLinkCaseDocOperation,
                    newLinkCaseDocOperation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeleteCaseDocOperation getDeleteCaseDocOperation() {
        return deleteCaseDocOperation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeleteCaseDocOperation(DeleteCaseDocOperation newDeleteCaseDocOperation,
            NotificationChain msgs) {
        DeleteCaseDocOperation oldDeleteCaseDocOperation = deleteCaseDocOperation;
        deleteCaseDocOperation = newDeleteCaseDocOperation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION, oldDeleteCaseDocOperation,
                    newDeleteCaseDocOperation);
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
    public void setDeleteCaseDocOperation(DeleteCaseDocOperation newDeleteCaseDocOperation) {
        if (newDeleteCaseDocOperation != deleteCaseDocOperation) {
            NotificationChain msgs = null;
            if (deleteCaseDocOperation != null)
                msgs = ((InternalEObject) deleteCaseDocOperation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION,
                        null,
                        msgs);
            if (newDeleteCaseDocOperation != null)
                msgs = ((InternalEObject) newDeleteCaseDocOperation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION,
                        null,
                        msgs);
            msgs = basicSetDeleteCaseDocOperation(newDeleteCaseDocOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION, newDeleteCaseDocOperation,
                    newDeleteCaseDocOperation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCaseDocumentRefField() {
        return caseDocumentRefField;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCaseDocumentRefField(String newCaseDocumentRefField) {
        String oldCaseDocumentRefField = caseDocumentRefField;
        caseDocumentRefField = newCaseDocumentRefField;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__CASE_DOCUMENT_REF_FIELD, oldCaseDocumentRefField,
                    caseDocumentRefField));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION:
            return basicSetMoveCaseDocOperation(null, msgs);
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION:
            return basicSetUnlinkCaseDocOperation(null, msgs);
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION:
            return basicSetLinkCaseDocOperation(null, msgs);
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION:
            return basicSetDeleteCaseDocOperation(null, msgs);
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
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION:
            return getMoveCaseDocOperation();
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION:
            return getUnlinkCaseDocOperation();
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION:
            return getLinkCaseDocOperation();
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION:
            return getDeleteCaseDocOperation();
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__CASE_DOCUMENT_REF_FIELD:
            return getCaseDocumentRefField();
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
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION:
            setMoveCaseDocOperation((MoveCaseDocOperation) newValue);
            return;
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION:
            setUnlinkCaseDocOperation((UnlinkCaseDocOperation) newValue);
            return;
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION:
            setLinkCaseDocOperation((LinkCaseDocOperation) newValue);
            return;
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION:
            setDeleteCaseDocOperation((DeleteCaseDocOperation) newValue);
            return;
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__CASE_DOCUMENT_REF_FIELD:
            setCaseDocumentRefField((String) newValue);
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
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION:
            setMoveCaseDocOperation((MoveCaseDocOperation) null);
            return;
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION:
            setUnlinkCaseDocOperation((UnlinkCaseDocOperation) null);
            return;
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION:
            setLinkCaseDocOperation((LinkCaseDocOperation) null);
            return;
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION:
            setDeleteCaseDocOperation((DeleteCaseDocOperation) null);
            return;
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__CASE_DOCUMENT_REF_FIELD:
            setCaseDocumentRefField(CASE_DOCUMENT_REF_FIELD_EDEFAULT);
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
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION:
            return moveCaseDocOperation != null;
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION:
            return unlinkCaseDocOperation != null;
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION:
            return linkCaseDocOperation != null;
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION:
            return deleteCaseDocOperation != null;
        case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__CASE_DOCUMENT_REF_FIELD:
            return CASE_DOCUMENT_REF_FIELD_EDEFAULT == null ? caseDocumentRefField != null
                    : !CASE_DOCUMENT_REF_FIELD_EDEFAULT.equals(caseDocumentRefField);
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
        result.append(" (caseDocumentRefField: "); //$NON-NLS-1$
        result.append(caseDocumentRefField);
        result.append(')');
        return result.toString();
    }

} //CaseDocRefOperationsImpl
