/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkItemScriptOperation;
import com.tibco.n2.brm.api.WorkItemScriptType;
import com.tibco.n2.brm.api.WorkModelScript;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Model Script</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelScriptImpl#getScriptBody <em>Script Body</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelScriptImpl#getScriptLanguage <em>Script Language</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelScriptImpl#getScriptLanguageExtension <em>Script Language Extension</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelScriptImpl#getScriptLanguageVersion <em>Script Language Version</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelScriptImpl#getScriptOperation <em>Script Operation</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkModelScriptImpl#getScriptTypeID <em>Script Type ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkModelScriptImpl extends EObjectImpl implements WorkModelScript {
    /**
     * The default value of the '{@link #getScriptBody() <em>Script Body</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptBody()
     * @generated
     * @ordered
     */
    protected static final String SCRIPT_BODY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getScriptBody() <em>Script Body</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptBody()
     * @generated
     * @ordered
     */
    protected String scriptBody = SCRIPT_BODY_EDEFAULT;

    /**
     * The default value of the '{@link #getScriptLanguage() <em>Script Language</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptLanguage()
     * @generated
     * @ordered
     */
    protected static final WorkItemScriptType SCRIPT_LANGUAGE_EDEFAULT = WorkItemScriptType.JSCRIPT;

    /**
     * The cached value of the '{@link #getScriptLanguage() <em>Script Language</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptLanguage()
     * @generated
     * @ordered
     */
    protected WorkItemScriptType scriptLanguage = SCRIPT_LANGUAGE_EDEFAULT;

    /**
     * This is true if the Script Language attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean scriptLanguageESet;

    /**
     * The default value of the '{@link #getScriptLanguageExtension() <em>Script Language Extension</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptLanguageExtension()
     * @generated
     * @ordered
     */
    protected static final String SCRIPT_LANGUAGE_EXTENSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getScriptLanguageExtension() <em>Script Language Extension</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptLanguageExtension()
     * @generated
     * @ordered
     */
    protected String scriptLanguageExtension = SCRIPT_LANGUAGE_EXTENSION_EDEFAULT;

    /**
     * The default value of the '{@link #getScriptLanguageVersion() <em>Script Language Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptLanguageVersion()
     * @generated
     * @ordered
     */
    protected static final String SCRIPT_LANGUAGE_VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getScriptLanguageVersion() <em>Script Language Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptLanguageVersion()
     * @generated
     * @ordered
     */
    protected String scriptLanguageVersion = SCRIPT_LANGUAGE_VERSION_EDEFAULT;

    /**
     * The default value of the '{@link #getScriptOperation() <em>Script Operation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptOperation()
     * @generated
     * @ordered
     */
    protected static final WorkItemScriptOperation SCRIPT_OPERATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getScriptOperation() <em>Script Operation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptOperation()
     * @generated
     * @ordered
     */
    protected WorkItemScriptOperation scriptOperation = SCRIPT_OPERATION_EDEFAULT;

    /**
     * The default value of the '{@link #getScriptTypeID() <em>Script Type ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptTypeID()
     * @generated
     * @ordered
     */
    protected static final String SCRIPT_TYPE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getScriptTypeID() <em>Script Type ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScriptTypeID()
     * @generated
     * @ordered
     */
    protected String scriptTypeID = SCRIPT_TYPE_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkModelScriptImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_MODEL_SCRIPT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getScriptBody() {
        return scriptBody;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScriptBody(String newScriptBody) {
        String oldScriptBody = scriptBody;
        scriptBody = newScriptBody;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_BODY, oldScriptBody, scriptBody));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemScriptType getScriptLanguage() {
        return scriptLanguage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScriptLanguage(WorkItemScriptType newScriptLanguage) {
        WorkItemScriptType oldScriptLanguage = scriptLanguage;
        scriptLanguage = newScriptLanguage == null ? SCRIPT_LANGUAGE_EDEFAULT : newScriptLanguage;
        boolean oldScriptLanguageESet = scriptLanguageESet;
        scriptLanguageESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE, oldScriptLanguage, scriptLanguage, !oldScriptLanguageESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetScriptLanguage() {
        WorkItemScriptType oldScriptLanguage = scriptLanguage;
        boolean oldScriptLanguageESet = scriptLanguageESet;
        scriptLanguage = SCRIPT_LANGUAGE_EDEFAULT;
        scriptLanguageESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE, oldScriptLanguage, SCRIPT_LANGUAGE_EDEFAULT, oldScriptLanguageESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetScriptLanguage() {
        return scriptLanguageESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getScriptLanguageExtension() {
        return scriptLanguageExtension;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScriptLanguageExtension(String newScriptLanguageExtension) {
        String oldScriptLanguageExtension = scriptLanguageExtension;
        scriptLanguageExtension = newScriptLanguageExtension;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_EXTENSION, oldScriptLanguageExtension, scriptLanguageExtension));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getScriptLanguageVersion() {
        return scriptLanguageVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScriptLanguageVersion(String newScriptLanguageVersion) {
        String oldScriptLanguageVersion = scriptLanguageVersion;
        scriptLanguageVersion = newScriptLanguageVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_VERSION, oldScriptLanguageVersion, scriptLanguageVersion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemScriptOperation getScriptOperation() {
        return scriptOperation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScriptOperation(WorkItemScriptOperation newScriptOperation) {
        WorkItemScriptOperation oldScriptOperation = scriptOperation;
        scriptOperation = newScriptOperation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_OPERATION, oldScriptOperation, scriptOperation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getScriptTypeID() {
        return scriptTypeID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setScriptTypeID(String newScriptTypeID) {
        String oldScriptTypeID = scriptTypeID;
        scriptTypeID = newScriptTypeID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_TYPE_ID, oldScriptTypeID, scriptTypeID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_BODY:
                return getScriptBody();
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE:
                return getScriptLanguage();
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_EXTENSION:
                return getScriptLanguageExtension();
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_VERSION:
                return getScriptLanguageVersion();
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_OPERATION:
                return getScriptOperation();
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_TYPE_ID:
                return getScriptTypeID();
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
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_BODY:
                setScriptBody((String)newValue);
                return;
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE:
                setScriptLanguage((WorkItemScriptType)newValue);
                return;
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_EXTENSION:
                setScriptLanguageExtension((String)newValue);
                return;
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_VERSION:
                setScriptLanguageVersion((String)newValue);
                return;
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_OPERATION:
                setScriptOperation((WorkItemScriptOperation)newValue);
                return;
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_TYPE_ID:
                setScriptTypeID((String)newValue);
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
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_BODY:
                setScriptBody(SCRIPT_BODY_EDEFAULT);
                return;
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE:
                unsetScriptLanguage();
                return;
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_EXTENSION:
                setScriptLanguageExtension(SCRIPT_LANGUAGE_EXTENSION_EDEFAULT);
                return;
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_VERSION:
                setScriptLanguageVersion(SCRIPT_LANGUAGE_VERSION_EDEFAULT);
                return;
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_OPERATION:
                setScriptOperation(SCRIPT_OPERATION_EDEFAULT);
                return;
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_TYPE_ID:
                setScriptTypeID(SCRIPT_TYPE_ID_EDEFAULT);
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
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_BODY:
                return SCRIPT_BODY_EDEFAULT == null ? scriptBody != null : !SCRIPT_BODY_EDEFAULT.equals(scriptBody);
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE:
                return isSetScriptLanguage();
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_EXTENSION:
                return SCRIPT_LANGUAGE_EXTENSION_EDEFAULT == null ? scriptLanguageExtension != null : !SCRIPT_LANGUAGE_EXTENSION_EDEFAULT.equals(scriptLanguageExtension);
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_LANGUAGE_VERSION:
                return SCRIPT_LANGUAGE_VERSION_EDEFAULT == null ? scriptLanguageVersion != null : !SCRIPT_LANGUAGE_VERSION_EDEFAULT.equals(scriptLanguageVersion);
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_OPERATION:
                return SCRIPT_OPERATION_EDEFAULT == null ? scriptOperation != null : !SCRIPT_OPERATION_EDEFAULT.equals(scriptOperation);
            case N2BRMPackage.WORK_MODEL_SCRIPT__SCRIPT_TYPE_ID:
                return SCRIPT_TYPE_ID_EDEFAULT == null ? scriptTypeID != null : !SCRIPT_TYPE_ID_EDEFAULT.equals(scriptTypeID);
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
        result.append(" (scriptBody: ");
        result.append(scriptBody);
        result.append(", scriptLanguage: ");
        if (scriptLanguageESet) result.append(scriptLanguage); else result.append("<unset>");
        result.append(", scriptLanguageExtension: ");
        result.append(scriptLanguageExtension);
        result.append(", scriptLanguageVersion: ");
        result.append(scriptLanguageVersion);
        result.append(", scriptOperation: ");
        result.append(scriptOperation);
        result.append(", scriptTypeID: ");
        result.append(scriptTypeID);
        result.append(')');
        return result.toString();
    }

} //WorkModelScriptImpl
