/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

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

import com.tibco.xpd.xpdl2.WebServiceApplication;
import com.tibco.xpd.xpdl2.WebServiceFaultCatch;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Web Service Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.WebServiceApplicationImpl#getWebServiceOperation <em>Web Service Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.WebServiceApplicationImpl#getWebServiceFaultCatch <em>Web Service Fault Catch</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.WebServiceApplicationImpl#getInputMsgName <em>Input Msg Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.WebServiceApplicationImpl#getOutputMsgName <em>Output Msg Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WebServiceApplicationImpl extends EObjectImpl implements WebServiceApplication {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getWebServiceOperation() <em>Web Service Operation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWebServiceOperation()
     * @generated
     * @ordered
     */
    protected WebServiceOperation webServiceOperation;

    /**
     * The cached value of the '{@link #getWebServiceFaultCatch() <em>Web Service Fault Catch</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWebServiceFaultCatch()
     * @generated
     * @ordered
     */
    protected EList<WebServiceFaultCatch> webServiceFaultCatch;

    /**
     * The default value of the '{@link #getInputMsgName() <em>Input Msg Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInputMsgName()
     * @generated
     * @ordered
     */
    protected static final String INPUT_MSG_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getInputMsgName() <em>Input Msg Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInputMsgName()
     * @generated
     * @ordered
     */
    protected String inputMsgName = INPUT_MSG_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getOutputMsgName() <em>Output Msg Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutputMsgName()
     * @generated
     * @ordered
     */
    protected static final String OUTPUT_MSG_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOutputMsgName() <em>Output Msg Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutputMsgName()
     * @generated
     * @ordered
     */
    protected String outputMsgName = OUTPUT_MSG_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WebServiceApplicationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.WEB_SERVICE_APPLICATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WebServiceOperation getWebServiceOperation() {
        return webServiceOperation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWebServiceOperation(WebServiceOperation newWebServiceOperation,
            NotificationChain msgs) {
        WebServiceOperation oldWebServiceOperation = webServiceOperation;
        webServiceOperation = newWebServiceOperation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_OPERATION, oldWebServiceOperation,
                    newWebServiceOperation);
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
    public void setWebServiceOperation(WebServiceOperation newWebServiceOperation) {
        if (newWebServiceOperation != webServiceOperation) {
            NotificationChain msgs = null;
            if (webServiceOperation != null)
                msgs = ((InternalEObject) webServiceOperation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_OPERATION,
                        null,
                        msgs);
            if (newWebServiceOperation != null)
                msgs = ((InternalEObject) newWebServiceOperation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_OPERATION,
                        null,
                        msgs);
            msgs = basicSetWebServiceOperation(newWebServiceOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_OPERATION, newWebServiceOperation,
                    newWebServiceOperation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<WebServiceFaultCatch> getWebServiceFaultCatch() {
        if (webServiceFaultCatch == null) {
            webServiceFaultCatch = new EObjectContainmentEList<WebServiceFaultCatch>(WebServiceFaultCatch.class, this,
                    Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_FAULT_CATCH);
        }
        return webServiceFaultCatch;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getInputMsgName() {
        return inputMsgName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInputMsgName(String newInputMsgName) {
        String oldInputMsgName = inputMsgName;
        inputMsgName = newInputMsgName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.WEB_SERVICE_APPLICATION__INPUT_MSG_NAME,
                    oldInputMsgName, inputMsgName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getOutputMsgName() {
        return outputMsgName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOutputMsgName(String newOutputMsgName) {
        String oldOutputMsgName = outputMsgName;
        outputMsgName = newOutputMsgName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.WEB_SERVICE_APPLICATION__OUTPUT_MSG_NAME,
                    oldOutputMsgName, outputMsgName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_OPERATION:
            return basicSetWebServiceOperation(null, msgs);
        case Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_FAULT_CATCH:
            return ((InternalEList<?>) getWebServiceFaultCatch()).basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_OPERATION:
            return getWebServiceOperation();
        case Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_FAULT_CATCH:
            return getWebServiceFaultCatch();
        case Xpdl2Package.WEB_SERVICE_APPLICATION__INPUT_MSG_NAME:
            return getInputMsgName();
        case Xpdl2Package.WEB_SERVICE_APPLICATION__OUTPUT_MSG_NAME:
            return getOutputMsgName();
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
        case Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_OPERATION:
            setWebServiceOperation((WebServiceOperation) newValue);
            return;
        case Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_FAULT_CATCH:
            getWebServiceFaultCatch().clear();
            getWebServiceFaultCatch().addAll((Collection<? extends WebServiceFaultCatch>) newValue);
            return;
        case Xpdl2Package.WEB_SERVICE_APPLICATION__INPUT_MSG_NAME:
            setInputMsgName((String) newValue);
            return;
        case Xpdl2Package.WEB_SERVICE_APPLICATION__OUTPUT_MSG_NAME:
            setOutputMsgName((String) newValue);
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
        case Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_OPERATION:
            setWebServiceOperation((WebServiceOperation) null);
            return;
        case Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_FAULT_CATCH:
            getWebServiceFaultCatch().clear();
            return;
        case Xpdl2Package.WEB_SERVICE_APPLICATION__INPUT_MSG_NAME:
            setInputMsgName(INPUT_MSG_NAME_EDEFAULT);
            return;
        case Xpdl2Package.WEB_SERVICE_APPLICATION__OUTPUT_MSG_NAME:
            setOutputMsgName(OUTPUT_MSG_NAME_EDEFAULT);
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
        case Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_OPERATION:
            return webServiceOperation != null;
        case Xpdl2Package.WEB_SERVICE_APPLICATION__WEB_SERVICE_FAULT_CATCH:
            return webServiceFaultCatch != null && !webServiceFaultCatch.isEmpty();
        case Xpdl2Package.WEB_SERVICE_APPLICATION__INPUT_MSG_NAME:
            return INPUT_MSG_NAME_EDEFAULT == null ? inputMsgName != null
                    : !INPUT_MSG_NAME_EDEFAULT.equals(inputMsgName);
        case Xpdl2Package.WEB_SERVICE_APPLICATION__OUTPUT_MSG_NAME:
            return OUTPUT_MSG_NAME_EDEFAULT == null ? outputMsgName != null
                    : !OUTPUT_MSG_NAME_EDEFAULT.equals(outputMsgName);
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
        result.append(" (inputMsgName: "); //$NON-NLS-1$
        result.append(inputMsgName);
        result.append(", outputMsgName: "); //$NON-NLS-1$
        result.append(outputMsgName);
        result.append(')');
        return result.toString();
    }

} //WebServiceApplicationImpl
