/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import com.tibco.xpd.xpdl2.CatchThrow;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trigger Result Message</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultMessageImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultMessageImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultMessageImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultMessageImpl#getWebServiceOperation <em>Web Service Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultMessageImpl#getCatchThrow <em>Catch Throw</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TriggerResultMessageImpl extends EObjectImpl implements TriggerResultMessage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getOtherAttributes() <em>Other Attributes</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherAttributes()
     * @generated
     * @ordered
     */
    protected FeatureMap otherAttributes;

    /**
     * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherElements()
     * @generated
     * @ordered
     */
    protected FeatureMap otherElements;

    /**
     * The cached value of the '{@link #getMessage() <em>Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMessage()
     * @generated
     * @ordered
     */
    protected Message message;

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
     * The default value of the '{@link #getCatchThrow() <em>Catch Throw</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCatchThrow()
     * @generated
     * @ordered
     */
    protected static final CatchThrow CATCH_THROW_EDEFAULT = CatchThrow.CATCH;

    /**
     * The cached value of the '{@link #getCatchThrow() <em>Catch Throw</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCatchThrow()
     * @generated
     * @ordered
     */
    protected CatchThrow catchThrow = CATCH_THROW_EDEFAULT;

    /**
     * This is true if the Catch Throw attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean catchThrowESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TriggerResultMessageImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TRIGGER_RESULT_MESSAGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherAttributes() {
        if (otherAttributes == null) {
            otherAttributes = new BasicFeatureMap(this, Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ATTRIBUTES);
        }
        return otherAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Message getMessage() {
        return message;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMessage(Message newMessage, NotificationChain msgs) {
        Message oldMessage = message;
        message = newMessage;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_RESULT_MESSAGE__MESSAGE, oldMessage, newMessage);
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
    public void setMessage(Message newMessage) {
        if (newMessage != message) {
            NotificationChain msgs = null;
            if (message != null)
                msgs = ((InternalEObject) message).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_RESULT_MESSAGE__MESSAGE,
                        null,
                        msgs);
            if (newMessage != null)
                msgs = ((InternalEObject) newMessage).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_RESULT_MESSAGE__MESSAGE,
                        null,
                        msgs);
            msgs = basicSetMessage(newMessage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TRIGGER_RESULT_MESSAGE__MESSAGE,
                    newMessage, newMessage));
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
                    Xpdl2Package.TRIGGER_RESULT_MESSAGE__WEB_SERVICE_OPERATION, oldWebServiceOperation,
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
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_RESULT_MESSAGE__WEB_SERVICE_OPERATION,
                        null,
                        msgs);
            if (newWebServiceOperation != null)
                msgs = ((InternalEObject) newWebServiceOperation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.TRIGGER_RESULT_MESSAGE__WEB_SERVICE_OPERATION,
                        null,
                        msgs);
            msgs = basicSetWebServiceOperation(newWebServiceOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_RESULT_MESSAGE__WEB_SERVICE_OPERATION, newWebServiceOperation,
                    newWebServiceOperation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CatchThrow getCatchThrow() {
        return catchThrow;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCatchThrow(CatchThrow newCatchThrow) {
        CatchThrow oldCatchThrow = catchThrow;
        catchThrow = newCatchThrow == null ? CATCH_THROW_EDEFAULT : newCatchThrow;
        boolean oldCatchThrowESet = catchThrowESet;
        catchThrowESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.TRIGGER_RESULT_MESSAGE__CATCH_THROW,
                    oldCatchThrow, catchThrow, !oldCatchThrowESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetCatchThrow() {
        CatchThrow oldCatchThrow = catchThrow;
        boolean oldCatchThrowESet = catchThrowESet;
        catchThrow = CATCH_THROW_EDEFAULT;
        catchThrowESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.TRIGGER_RESULT_MESSAGE__CATCH_THROW,
                    oldCatchThrow, CATCH_THROW_EDEFAULT, oldCatchThrowESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetCatchThrow() {
        return catchThrowESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EObject getOtherElement(String elementName) {
        return Xpdl2Operations.getOtherElement(this, elementName);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__MESSAGE:
            return basicSetMessage(null, msgs);
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__WEB_SERVICE_OPERATION:
            return basicSetWebServiceOperation(null, msgs);
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
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__MESSAGE:
            return getMessage();
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__WEB_SERVICE_OPERATION:
            return getWebServiceOperation();
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__CATCH_THROW:
            return getCatchThrow();
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
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__MESSAGE:
            setMessage((Message) newValue);
            return;
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__WEB_SERVICE_OPERATION:
            setWebServiceOperation((WebServiceOperation) newValue);
            return;
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__CATCH_THROW:
            setCatchThrow((CatchThrow) newValue);
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
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__MESSAGE:
            setMessage((Message) null);
            return;
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__WEB_SERVICE_OPERATION:
            setWebServiceOperation((WebServiceOperation) null);
            return;
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__CATCH_THROW:
            unsetCatchThrow();
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
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__MESSAGE:
            return message != null;
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__WEB_SERVICE_OPERATION:
            return webServiceOperation != null;
        case Xpdl2Package.TRIGGER_RESULT_MESSAGE__CATCH_THROW:
            return isSetCatchThrow();
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
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ELEMENTS:
                return Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;
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
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.TRIGGER_RESULT_MESSAGE__OTHER_ELEMENTS;
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (otherAttributes: "); //$NON-NLS-1$
        result.append(otherAttributes);
        result.append(", otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", catchThrow: "); //$NON-NLS-1$
        if (catchThrowESet)
            result.append(catchThrow);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //TriggerResultMessageImpl
