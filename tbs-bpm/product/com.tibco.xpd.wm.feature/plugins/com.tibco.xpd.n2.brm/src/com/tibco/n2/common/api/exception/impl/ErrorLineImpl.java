/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception.impl;

import com.tibco.n2.common.api.exception.ErrorLine;
import com.tibco.n2.common.api.exception.ExceptionPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Error Line</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.ErrorLineImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.ErrorLineImpl#getCode <em>Code</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.ErrorLineImpl#getMessage <em>Message</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ErrorLineImpl extends EObjectImpl implements ErrorLine {
    /**
     * The cached value of the '{@link #getParameter() <em>Parameter</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameter()
     * @generated
     * @ordered
     */
    protected EList<String> parameter;

    /**
     * The default value of the '{@link #getCode() <em>Code</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCode()
     * @generated
     * @ordered
     */
    protected static final String CODE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCode() <em>Code</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCode()
     * @generated
     * @ordered
     */
    protected String code = CODE_EDEFAULT;

    /**
     * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMessage()
     * @generated
     * @ordered
     */
    protected static final String MESSAGE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMessage()
     * @generated
     * @ordered
     */
    protected String message = MESSAGE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ErrorLineImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ExceptionPackage.Literals.ERROR_LINE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getParameter() {
        if (parameter == null) {
            parameter = new EDataTypeEList<String>(String.class, this, ExceptionPackage.ERROR_LINE__PARAMETER);
        }
        return parameter;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCode() {
        return code;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCode(String newCode) {
        String oldCode = code;
        code = newCode;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ExceptionPackage.ERROR_LINE__CODE, oldCode, code));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMessage() {
        return message;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMessage(String newMessage) {
        String oldMessage = message;
        message = newMessage;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ExceptionPackage.ERROR_LINE__MESSAGE, oldMessage, message));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ExceptionPackage.ERROR_LINE__PARAMETER:
                return getParameter();
            case ExceptionPackage.ERROR_LINE__CODE:
                return getCode();
            case ExceptionPackage.ERROR_LINE__MESSAGE:
                return getMessage();
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
            case ExceptionPackage.ERROR_LINE__PARAMETER:
                getParameter().clear();
                getParameter().addAll((Collection<? extends String>)newValue);
                return;
            case ExceptionPackage.ERROR_LINE__CODE:
                setCode((String)newValue);
                return;
            case ExceptionPackage.ERROR_LINE__MESSAGE:
                setMessage((String)newValue);
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
            case ExceptionPackage.ERROR_LINE__PARAMETER:
                getParameter().clear();
                return;
            case ExceptionPackage.ERROR_LINE__CODE:
                setCode(CODE_EDEFAULT);
                return;
            case ExceptionPackage.ERROR_LINE__MESSAGE:
                setMessage(MESSAGE_EDEFAULT);
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
            case ExceptionPackage.ERROR_LINE__PARAMETER:
                return parameter != null && !parameter.isEmpty();
            case ExceptionPackage.ERROR_LINE__CODE:
                return CODE_EDEFAULT == null ? code != null : !CODE_EDEFAULT.equals(code);
            case ExceptionPackage.ERROR_LINE__MESSAGE:
                return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
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
        result.append(" (parameter: ");
        result.append(parameter);
        result.append(", code: ");
        result.append(code);
        result.append(", message: ");
        result.append(message);
        result.append(')');
        return result.toString();
    }

} //ErrorLineImpl
