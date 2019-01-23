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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task User</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskUserImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskUserImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskUserImpl#getPerformers <em>Performers</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskUserImpl#getMessageIn <em>Message In</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskUserImpl#getMessageOut <em>Message Out</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskUserImpl#getWebServiceOperation <em>Web Service Operation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TaskUserImpl#getImplementation <em>Implementation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TaskUserImpl extends EObjectImpl implements TaskUser {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

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
     * The cached value of the '{@link #getPerformers() <em>Performers</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPerformers()
     * @generated
     * @ordered
     */
    protected EList<Performer> performers;

    /**
     * The cached value of the '{@link #getMessageIn() <em>Message In</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMessageIn()
     * @generated
     * @ordered
     */
    protected Message messageIn;

    /**
     * The cached value of the '{@link #getMessageOut() <em>Message Out</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMessageOut()
     * @generated
     * @ordered
     */
    protected Message messageOut;

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
     * The default value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getImplementation()
     * @generated
     * @ordered
     */
    protected static final ImplementationType IMPLEMENTATION_EDEFAULT =
            ImplementationType.WEB_SERVICE_LITERAL;

    /**
     * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getImplementation()
     * @generated
     * @ordered
     */
    protected ImplementationType implementation = IMPLEMENTATION_EDEFAULT;

    /**
     * This is true if the Implementation attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean implementationESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TaskUserImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TASK_USER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherAttributes() {
        if (otherAttributes == null) {
            otherAttributes =
                    new BasicFeatureMap(this,
                            Xpdl2Package.TASK_USER__OTHER_ATTRIBUTES);
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
            otherElements =
                    new BasicFeatureMap(this,
                            Xpdl2Package.TASK_USER__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Performer> getPerformers() {
        if (performers == null) {
            performers =
                    new EObjectContainmentEList<Performer>(Performer.class,
                            this, Xpdl2Package.TASK_USER__PERFORMERS);
        }
        return performers;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Message getMessageIn() {
        return messageIn;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMessageIn(Message newMessageIn,
            NotificationChain msgs) {
        Message oldMessageIn = messageIn;
        messageIn = newMessageIn;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TASK_USER__MESSAGE_IN, oldMessageIn,
                            newMessageIn);
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
    public void setMessageIn(Message newMessageIn) {
        if (newMessageIn != messageIn) {
            NotificationChain msgs = null;
            if (messageIn != null)
                msgs =
                        ((InternalEObject) messageIn).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK_USER__MESSAGE_IN,
                                null,
                                msgs);
            if (newMessageIn != null)
                msgs =
                        ((InternalEObject) newMessageIn).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK_USER__MESSAGE_IN,
                                null,
                                msgs);
            msgs = basicSetMessageIn(newMessageIn, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK_USER__MESSAGE_IN, newMessageIn,
                    newMessageIn));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Message getMessageOut() {
        return messageOut;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMessageOut(Message newMessageOut,
            NotificationChain msgs) {
        Message oldMessageOut = messageOut;
        messageOut = newMessageOut;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TASK_USER__MESSAGE_OUT, oldMessageOut,
                            newMessageOut);
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
    public void setMessageOut(Message newMessageOut) {
        if (newMessageOut != messageOut) {
            NotificationChain msgs = null;
            if (messageOut != null)
                msgs =
                        ((InternalEObject) messageOut).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK_USER__MESSAGE_OUT,
                                null,
                                msgs);
            if (newMessageOut != null)
                msgs =
                        ((InternalEObject) newMessageOut).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TASK_USER__MESSAGE_OUT,
                                null,
                                msgs);
            msgs = basicSetMessageOut(newMessageOut, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK_USER__MESSAGE_OUT, newMessageOut,
                    newMessageOut));
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
    public NotificationChain basicSetWebServiceOperation(
            WebServiceOperation newWebServiceOperation, NotificationChain msgs) {
        WebServiceOperation oldWebServiceOperation = webServiceOperation;
        webServiceOperation = newWebServiceOperation;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TASK_USER__WEB_SERVICE_OPERATION,
                            oldWebServiceOperation, newWebServiceOperation);
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
    public void setWebServiceOperation(
            WebServiceOperation newWebServiceOperation) {
        if (newWebServiceOperation != webServiceOperation) {
            NotificationChain msgs = null;
            if (webServiceOperation != null)
                msgs =
                        ((InternalEObject) webServiceOperation)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.TASK_USER__WEB_SERVICE_OPERATION,
                                        null,
                                        msgs);
            if (newWebServiceOperation != null)
                msgs =
                        ((InternalEObject) newWebServiceOperation)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.TASK_USER__WEB_SERVICE_OPERATION,
                                        null,
                                        msgs);
            msgs = basicSetWebServiceOperation(newWebServiceOperation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK_USER__WEB_SERVICE_OPERATION,
                    newWebServiceOperation, newWebServiceOperation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ImplementationType getImplementation() {
        return implementation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setImplementation(ImplementationType newImplementation) {
        ImplementationType oldImplementation = implementation;
        implementation =
                newImplementation == null ? IMPLEMENTATION_EDEFAULT
                        : newImplementation;
        boolean oldImplementationESet = implementationESet;
        implementationESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TASK_USER__IMPLEMENTATION, oldImplementation,
                    implementation, !oldImplementationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetImplementation() {
        ImplementationType oldImplementation = implementation;
        boolean oldImplementationESet = implementationESet;
        implementation = IMPLEMENTATION_EDEFAULT;
        implementationESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.TASK_USER__IMPLEMENTATION, oldImplementation,
                    IMPLEMENTATION_EDEFAULT, oldImplementationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetImplementation() {
        return implementationESet;
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
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.TASK_USER__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.TASK_USER__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.TASK_USER__PERFORMERS:
            return ((InternalEList<?>) getPerformers()).basicRemove(otherEnd,
                    msgs);
        case Xpdl2Package.TASK_USER__MESSAGE_IN:
            return basicSetMessageIn(null, msgs);
        case Xpdl2Package.TASK_USER__MESSAGE_OUT:
            return basicSetMessageOut(null, msgs);
        case Xpdl2Package.TASK_USER__WEB_SERVICE_OPERATION:
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
        case Xpdl2Package.TASK_USER__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.TASK_USER__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.TASK_USER__PERFORMERS:
            return getPerformers();
        case Xpdl2Package.TASK_USER__MESSAGE_IN:
            return getMessageIn();
        case Xpdl2Package.TASK_USER__MESSAGE_OUT:
            return getMessageOut();
        case Xpdl2Package.TASK_USER__WEB_SERVICE_OPERATION:
            return getWebServiceOperation();
        case Xpdl2Package.TASK_USER__IMPLEMENTATION:
            return getImplementation();
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
        case Xpdl2Package.TASK_USER__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.TASK_USER__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.TASK_USER__PERFORMERS:
            getPerformers().clear();
            getPerformers().addAll((Collection<? extends Performer>) newValue);
            return;
        case Xpdl2Package.TASK_USER__MESSAGE_IN:
            setMessageIn((Message) newValue);
            return;
        case Xpdl2Package.TASK_USER__MESSAGE_OUT:
            setMessageOut((Message) newValue);
            return;
        case Xpdl2Package.TASK_USER__WEB_SERVICE_OPERATION:
            setWebServiceOperation((WebServiceOperation) newValue);
            return;
        case Xpdl2Package.TASK_USER__IMPLEMENTATION:
            setImplementation((ImplementationType) newValue);
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
        case Xpdl2Package.TASK_USER__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.TASK_USER__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.TASK_USER__PERFORMERS:
            getPerformers().clear();
            return;
        case Xpdl2Package.TASK_USER__MESSAGE_IN:
            setMessageIn((Message) null);
            return;
        case Xpdl2Package.TASK_USER__MESSAGE_OUT:
            setMessageOut((Message) null);
            return;
        case Xpdl2Package.TASK_USER__WEB_SERVICE_OPERATION:
            setWebServiceOperation((WebServiceOperation) null);
            return;
        case Xpdl2Package.TASK_USER__IMPLEMENTATION:
            unsetImplementation();
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
        case Xpdl2Package.TASK_USER__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.TASK_USER__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.TASK_USER__PERFORMERS:
            return performers != null && !performers.isEmpty();
        case Xpdl2Package.TASK_USER__MESSAGE_IN:
            return messageIn != null;
        case Xpdl2Package.TASK_USER__MESSAGE_OUT:
            return messageOut != null;
        case Xpdl2Package.TASK_USER__WEB_SERVICE_OPERATION:
            return webServiceOperation != null;
        case Xpdl2Package.TASK_USER__IMPLEMENTATION:
            return isSetImplementation();
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
            case Xpdl2Package.TASK_USER__OTHER_ELEMENTS:
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
                return Xpdl2Package.TASK_USER__OTHER_ELEMENTS;
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
        result.append(" (otherAttributes: "); //$NON-NLS-1$
        result.append(otherAttributes);
        result.append(", otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", implementation: "); //$NON-NLS-1$
        if (implementationESet)
            result.append(implementation);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //TaskUserImpl
