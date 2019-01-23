/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import com.tibco.xpd.xpdl2.Association;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.GraphicalConnector;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Message Flow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageFlowImpl#getConnectorGraphicsInfos <em>Connector Graphics Infos</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageFlowImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageFlowImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageFlowImpl#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageFlowImpl#getSource <em>Source</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageFlowImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageFlowImpl#getPackage <em>Package</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MessageFlowImpl extends NamedElementImpl implements MessageFlow {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getConnectorGraphicsInfos() <em>Connector Graphics Infos</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConnectorGraphicsInfos()
     * @generated
     * @ordered
     */
    protected EList<ConnectorGraphicsInfo> connectorGraphicsInfos;

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
     * The cached value of the '{@link #getObject() <em>Object</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getObject()
     * @generated
     * @ordered
     */
    protected com.tibco.xpd.xpdl2.Object object;

    /**
     * The default value of the '{@link #getSource() <em>Source</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSource()
     * @generated
     * @ordered
     */
    protected static final String SOURCE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSource() <em>Source</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSource()
     * @generated
     * @ordered
     */
    protected String source = SOURCE_EDEFAULT;

    /**
     * The default value of the '{@link #getTarget() <em>Target</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected static final String TARGET_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTarget() <em>Target</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected String target = TARGET_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MessageFlowImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.MESSAGE_FLOW;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ConnectorGraphicsInfo> getConnectorGraphicsInfos() {
        if (connectorGraphicsInfos == null) {
            connectorGraphicsInfos =
                    new EObjectContainmentEList<ConnectorGraphicsInfo>(
                            ConnectorGraphicsInfo.class, this,
                            Xpdl2Package.MESSAGE_FLOW__CONNECTOR_GRAPHICS_INFOS);
        }
        return connectorGraphicsInfos;
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
                            Xpdl2Package.MESSAGE_FLOW__OTHER_ELEMENTS);
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
    public NotificationChain basicSetMessage(Message newMessage,
            NotificationChain msgs) {
        Message oldMessage = message;
        message = newMessage;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.MESSAGE_FLOW__MESSAGE, oldMessage,
                            newMessage);
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
                msgs =
                        ((InternalEObject) message).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.MESSAGE_FLOW__MESSAGE,
                                null,
                                msgs);
            if (newMessage != null)
                msgs =
                        ((InternalEObject) newMessage).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.MESSAGE_FLOW__MESSAGE,
                                null,
                                msgs);
            msgs = basicSetMessage(newMessage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.MESSAGE_FLOW__MESSAGE, newMessage, newMessage));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Object getObject() {
        return object;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetObject(
            com.tibco.xpd.xpdl2.Object newObject, NotificationChain msgs) {
        com.tibco.xpd.xpdl2.Object oldObject = object;
        object = newObject;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.MESSAGE_FLOW__OBJECT, oldObject,
                            newObject);
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
    public void setObject(com.tibco.xpd.xpdl2.Object newObject) {
        if (newObject != object) {
            NotificationChain msgs = null;
            if (object != null)
                msgs =
                        ((InternalEObject) object).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.MESSAGE_FLOW__OBJECT,
                                null,
                                msgs);
            if (newObject != null)
                msgs =
                        ((InternalEObject) newObject).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.MESSAGE_FLOW__OBJECT,
                                null,
                                msgs);
            msgs = basicSetObject(newObject, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.MESSAGE_FLOW__OBJECT, newObject, newObject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getSource() {
        return source;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSource(String newSource) {
        String oldSource = source;
        source = newSource;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.MESSAGE_FLOW__SOURCE, oldSource, source));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTarget() {
        return target;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTarget(String newTarget) {
        String oldTarget = target;
        target = newTarget;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.MESSAGE_FLOW__TARGET, oldTarget, target));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Package getPackage() {
        if (eContainerFeatureID() != Xpdl2Package.MESSAGE_FLOW__PACKAGE)
            return null;
        return (com.tibco.xpd.xpdl2.Package) eInternalContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPackage(
            com.tibco.xpd.xpdl2.Package newPackage, NotificationChain msgs) {
        msgs =
                eBasicSetContainer((InternalEObject) newPackage,
                        Xpdl2Package.MESSAGE_FLOW__PACKAGE,
                        msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPackage(com.tibco.xpd.xpdl2.Package newPackage) {
        if (newPackage != eInternalContainer()
                || (eContainerFeatureID() != Xpdl2Package.MESSAGE_FLOW__PACKAGE && newPackage != null)) {
            if (EcoreUtil.isAncestor(this, newPackage))
                throw new IllegalArgumentException(
                        "Recursive containment not allowed for " + toString()); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newPackage != null)
                msgs =
                        ((InternalEObject) newPackage).eInverseAdd(this,
                                Xpdl2Package.PACKAGE__MESSAGE_FLOWS,
                                com.tibco.xpd.xpdl2.Package.class,
                                msgs);
            msgs = basicSetPackage(newPackage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.MESSAGE_FLOW__PACKAGE, newPackage, newPackage));
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
     * @generated NOT
     */
    public ConnectorGraphicsInfo getConnectorGraphicsInfoForTool(String id) {
        EList<ConnectorGraphicsInfo> cgis = getConnectorGraphicsInfos();
        if (cgis != null) {
            for (ConnectorGraphicsInfo cgi : cgis) {
                String toolId = cgi.getToolId();

                if (id == null || id.length() == 0) {
                    if (toolId == null || toolId.length() == 0) {
                        return cgi;
                    }
                } else if (id.equals(toolId)) {
                    return cgi;
                }
            }

        }

        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EList getIncomingAssociations() {
        if (getPackage() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getPackage().getAssociations(),
                Xpdl2Package.eINSTANCE.getAssociation_Target(),
                getId());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EList getOutgoingAssociations() {
        if (getPackage() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getPackage().getAssociations(),
                Xpdl2Package.eINSTANCE.getAssociation_Source(),
                getId());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.MESSAGE_FLOW__PACKAGE:
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            return basicSetPackage((com.tibco.xpd.xpdl2.Package) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
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
        case Xpdl2Package.MESSAGE_FLOW__CONNECTOR_GRAPHICS_INFOS:
            return ((InternalEList<?>) getConnectorGraphicsInfos())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.MESSAGE_FLOW__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.MESSAGE_FLOW__MESSAGE:
            return basicSetMessage(null, msgs);
        case Xpdl2Package.MESSAGE_FLOW__OBJECT:
            return basicSetObject(null, msgs);
        case Xpdl2Package.MESSAGE_FLOW__PACKAGE:
            return basicSetPackage(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(
            NotificationChain msgs) {
        switch (eContainerFeatureID()) {
        case Xpdl2Package.MESSAGE_FLOW__PACKAGE:
            return eInternalContainer().eInverseRemove(this,
                    Xpdl2Package.PACKAGE__MESSAGE_FLOWS,
                    com.tibco.xpd.xpdl2.Package.class,
                    msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.MESSAGE_FLOW__CONNECTOR_GRAPHICS_INFOS:
            return getConnectorGraphicsInfos();
        case Xpdl2Package.MESSAGE_FLOW__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.MESSAGE_FLOW__MESSAGE:
            return getMessage();
        case Xpdl2Package.MESSAGE_FLOW__OBJECT:
            return getObject();
        case Xpdl2Package.MESSAGE_FLOW__SOURCE:
            return getSource();
        case Xpdl2Package.MESSAGE_FLOW__TARGET:
            return getTarget();
        case Xpdl2Package.MESSAGE_FLOW__PACKAGE:
            return getPackage();
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
        case Xpdl2Package.MESSAGE_FLOW__CONNECTOR_GRAPHICS_INFOS:
            getConnectorGraphicsInfos().clear();
            getConnectorGraphicsInfos()
                    .addAll((Collection<? extends ConnectorGraphicsInfo>) newValue);
            return;
        case Xpdl2Package.MESSAGE_FLOW__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.MESSAGE_FLOW__MESSAGE:
            setMessage((Message) newValue);
            return;
        case Xpdl2Package.MESSAGE_FLOW__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) newValue);
            return;
        case Xpdl2Package.MESSAGE_FLOW__SOURCE:
            setSource((String) newValue);
            return;
        case Xpdl2Package.MESSAGE_FLOW__TARGET:
            setTarget((String) newValue);
            return;
        case Xpdl2Package.MESSAGE_FLOW__PACKAGE:
            setPackage((com.tibco.xpd.xpdl2.Package) newValue);
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
        case Xpdl2Package.MESSAGE_FLOW__CONNECTOR_GRAPHICS_INFOS:
            getConnectorGraphicsInfos().clear();
            return;
        case Xpdl2Package.MESSAGE_FLOW__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.MESSAGE_FLOW__MESSAGE:
            setMessage((Message) null);
            return;
        case Xpdl2Package.MESSAGE_FLOW__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) null);
            return;
        case Xpdl2Package.MESSAGE_FLOW__SOURCE:
            setSource(SOURCE_EDEFAULT);
            return;
        case Xpdl2Package.MESSAGE_FLOW__TARGET:
            setTarget(TARGET_EDEFAULT);
            return;
        case Xpdl2Package.MESSAGE_FLOW__PACKAGE:
            setPackage((com.tibco.xpd.xpdl2.Package) null);
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
        case Xpdl2Package.MESSAGE_FLOW__CONNECTOR_GRAPHICS_INFOS:
            return connectorGraphicsInfos != null
                    && !connectorGraphicsInfos.isEmpty();
        case Xpdl2Package.MESSAGE_FLOW__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.MESSAGE_FLOW__MESSAGE:
            return message != null;
        case Xpdl2Package.MESSAGE_FLOW__OBJECT:
            return object != null;
        case Xpdl2Package.MESSAGE_FLOW__SOURCE:
            return SOURCE_EDEFAULT == null ? source != null : !SOURCE_EDEFAULT
                    .equals(source);
        case Xpdl2Package.MESSAGE_FLOW__TARGET:
            return TARGET_EDEFAULT == null ? target != null : !TARGET_EDEFAULT
                    .equals(target);
        case Xpdl2Package.MESSAGE_FLOW__PACKAGE:
            return getPackage() != null;
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
        if (baseClass == GraphicalConnector.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.MESSAGE_FLOW__CONNECTOR_GRAPHICS_INFOS:
                return Xpdl2Package.GRAPHICAL_CONNECTOR__CONNECTOR_GRAPHICS_INFOS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.MESSAGE_FLOW__OTHER_ELEMENTS:
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
        if (baseClass == GraphicalConnector.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.GRAPHICAL_CONNECTOR__CONNECTOR_GRAPHICS_INFOS:
                return Xpdl2Package.MESSAGE_FLOW__CONNECTOR_GRAPHICS_INFOS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.MESSAGE_FLOW__OTHER_ELEMENTS;
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
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", source: "); //$NON-NLS-1$
        result.append(source);
        result.append(", target: "); //$NON-NLS-1$
        result.append(target);
        result.append(')');
        return result.toString();
    }

} //MessageFlowImpl
