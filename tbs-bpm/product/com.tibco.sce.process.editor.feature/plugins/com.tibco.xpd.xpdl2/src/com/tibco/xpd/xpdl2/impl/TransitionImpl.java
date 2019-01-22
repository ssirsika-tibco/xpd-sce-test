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
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.Assignment;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.GraphicalConnector;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransitionImpl#getExtendedAttributes <em>Extended Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransitionImpl#getConnectorGraphicsInfos <em>Connector Graphics Infos</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransitionImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransitionImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransitionImpl#getAssignments <em>Assignments</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransitionImpl#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransitionImpl#getFrom <em>From</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransitionImpl#getQuantity <em>Quantity</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransitionImpl#getTo <em>To</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TransitionImpl#getFlowContainer <em>Flow Container</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionImpl extends NamedElementImpl implements Transition {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getExtendedAttributes() <em>Extended Attributes</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getExtendedAttributes()
     * @generated
     * @ordered
     */
    protected EList<ExtendedAttribute> extendedAttributes;

    /**
     * The cached value of the '{@link #getConnectorGraphicsInfos() <em>Connector Graphics Infos</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getConnectorGraphicsInfos()
     * @generated
     * @ordered
     */
    protected EList<ConnectorGraphicsInfo> connectorGraphicsInfos;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected Description description;

    /**
     * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getCondition()
     * @generated
     * @ordered
     */
    protected Condition condition;

    /**
     * The cached value of the '{@link #getAssignments() <em>Assignments</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAssignments()
     * @generated
     * @ordered
     */
    protected EList<Assignment> assignments;

    /**
     * The cached value of the '{@link #getObject() <em>Object</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getObject()
     * @generated
     * @ordered
     */
    protected com.tibco.xpd.xpdl2.Object object;

    /**
     * The default value of the '{@link #getFrom() <em>From</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getFrom()
     * @generated
     * @ordered
     */
    protected static final String FROM_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFrom() <em>From</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getFrom()
     * @generated
     * @ordered
     */
    protected String from = FROM_EDEFAULT;

    /**
     * The default value of the '{@link #getQuantity() <em>Quantity</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getQuantity()
     * @generated
     * @ordered
     */
    protected static final int QUANTITY_EDEFAULT = 1;

    /**
     * The cached value of the '{@link #getQuantity() <em>Quantity</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getQuantity()
     * @generated
     * @ordered
     */
    protected int quantity = QUANTITY_EDEFAULT;

    /**
     * This is true if the Quantity attribute has been set.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean quantityESet;

    /**
     * The default value of the '{@link #getTo() <em>To</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTo()
     * @generated
     * @ordered
     */
    protected static final String TO_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTo() <em>To</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTo()
     * @generated
     * @ordered
     */
    protected String to = TO_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected TransitionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TRANSITION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ExtendedAttribute> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes =
                    new EObjectContainmentEList<ExtendedAttribute>(
                            ExtendedAttribute.class, this,
                            Xpdl2Package.TRANSITION__EXTENDED_ATTRIBUTES);
        }
        return extendedAttributes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ConnectorGraphicsInfo> getConnectorGraphicsInfos() {
        if (connectorGraphicsInfos == null) {
            connectorGraphicsInfos =
                    new EObjectContainmentEList<ConnectorGraphicsInfo>(
                            ConnectorGraphicsInfo.class, this,
                            Xpdl2Package.TRANSITION__CONNECTOR_GRAPHICS_INFOS);
        }
        return connectorGraphicsInfos;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCondition(Condition newCondition,
            NotificationChain msgs) {
        Condition oldCondition = condition;
        condition = newCondition;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TRANSITION__CONDITION, oldCondition,
                            newCondition);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setCondition(Condition newCondition) {
        if (newCondition != condition) {
            NotificationChain msgs = null;
            if (condition != null)
                msgs =
                        ((InternalEObject) condition).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TRANSITION__CONDITION,
                                null,
                                msgs);
            if (newCondition != null)
                msgs =
                        ((InternalEObject) newCondition).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TRANSITION__CONDITION,
                                null,
                                msgs);
            msgs = basicSetCondition(newCondition, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRANSITION__CONDITION, newCondition,
                    newCondition));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Description getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDescription(Description newDescription,
            NotificationChain msgs) {
        Description oldDescription = description;
        description = newDescription;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TRANSITION__DESCRIPTION,
                            oldDescription, newDescription);
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
    public void setDescription(Description newDescription) {
        if (newDescription != description) {
            NotificationChain msgs = null;
            if (description != null)
                msgs =
                        ((InternalEObject) description).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TRANSITION__DESCRIPTION,
                                null,
                                msgs);
            if (newDescription != null)
                msgs =
                        ((InternalEObject) newDescription).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TRANSITION__DESCRIPTION,
                                null,
                                msgs);
            msgs = basicSetDescription(newDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRANSITION__DESCRIPTION, newDescription,
                    newDescription));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Assignment> getAssignments() {
        if (assignments == null) {
            assignments =
                    new EObjectContainmentEList<Assignment>(Assignment.class,
                            this, Xpdl2Package.TRANSITION__ASSIGNMENTS);
        }
        return assignments;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Object getObject() {
        return object;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetObject(
            com.tibco.xpd.xpdl2.Object newObject, NotificationChain msgs) {
        com.tibco.xpd.xpdl2.Object oldObject = object;
        object = newObject;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.TRANSITION__OBJECT, oldObject,
                            newObject);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setObject(com.tibco.xpd.xpdl2.Object newObject) {
        if (newObject != object) {
            NotificationChain msgs = null;
            if (object != null)
                msgs =
                        ((InternalEObject) object).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TRANSITION__OBJECT,
                                null,
                                msgs);
            if (newObject != null)
                msgs =
                        ((InternalEObject) newObject).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.TRANSITION__OBJECT,
                                null,
                                msgs);
            msgs = basicSetObject(newObject, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRANSITION__OBJECT, newObject, newObject));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getFrom() {
        return from;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setFrom(String newFrom) {
        String oldFrom = from;
        from = newFrom;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRANSITION__FROM, oldFrom, from));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setQuantity(int newQuantity) {
        int oldQuantity = quantity;
        quantity = newQuantity;
        boolean oldQuantityESet = quantityESet;
        quantityESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRANSITION__QUANTITY, oldQuantity, quantity,
                    !oldQuantityESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetQuantity() {
        int oldQuantity = quantity;
        boolean oldQuantityESet = quantityESet;
        quantity = QUANTITY_EDEFAULT;
        quantityESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.TRANSITION__QUANTITY, oldQuantity,
                    QUANTITY_EDEFAULT, oldQuantityESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetQuantity() {
        return quantityESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getTo() {
        return to;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setTo(String newTo) {
        String oldTo = to;
        to = newTo;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRANSITION__TO, oldTo, to));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FlowContainer getFlowContainer() {
        if (eContainerFeatureID() != Xpdl2Package.TRANSITION__FLOW_CONTAINER)
            return null;
        return (FlowContainer) eInternalContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetFlowContainer(
            FlowContainer newFlowContainer, NotificationChain msgs) {
        msgs =
                eBasicSetContainer((InternalEObject) newFlowContainer,
                        Xpdl2Package.TRANSITION__FLOW_CONTAINER,
                        msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFlowContainer(FlowContainer newFlowContainer) {
        if (newFlowContainer != eInternalContainer()
                || (eContainerFeatureID() != Xpdl2Package.TRANSITION__FLOW_CONTAINER && newFlowContainer != null)) {
            if (EcoreUtil.isAncestor(this, newFlowContainer))
                throw new IllegalArgumentException(
                        "Recursive containment not allowed for " + toString()); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newFlowContainer != null)
                msgs =
                        ((InternalEObject) newFlowContainer).eInverseAdd(this,
                                Xpdl2Package.FLOW_CONTAINER__TRANSITIONS,
                                FlowContainer.class,
                                msgs);
            msgs = basicSetFlowContainer(newFlowContainer, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRANSITION__FLOW_CONTAINER, newFlowContainer,
                    newFlowContainer));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
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
        if (getProcess() == null || getProcess().getPackage() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getProcess().getPackage()
                .getAssociations(), Xpdl2Package.eINSTANCE
                .getAssociation_Target(), getId());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EList getOutgoingAssociations() {
        if (getProcess() == null || getProcess().getPackage() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getProcess().getPackage()
                .getAssociations(), Xpdl2Package.eINSTANCE
                .getAssociation_Source(), getId());
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public com.tibco.xpd.xpdl2.Process getProcess() {
        EObject eo = eContainer();
        while (!(eo instanceof Process) && eo != null) {
            eo = eo.eContainer();
        }
        return (Process) eo;
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
        case Xpdl2Package.TRANSITION__FLOW_CONTAINER:
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            return basicSetFlowContainer((FlowContainer) otherEnd, msgs);
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
        case Xpdl2Package.TRANSITION__EXTENDED_ATTRIBUTES:
            return ((InternalEList<?>) getExtendedAttributes())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.TRANSITION__CONNECTOR_GRAPHICS_INFOS:
            return ((InternalEList<?>) getConnectorGraphicsInfos())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.TRANSITION__DESCRIPTION:
            return basicSetDescription(null, msgs);
        case Xpdl2Package.TRANSITION__CONDITION:
            return basicSetCondition(null, msgs);
        case Xpdl2Package.TRANSITION__ASSIGNMENTS:
            return ((InternalEList<?>) getAssignments()).basicRemove(otherEnd,
                    msgs);
        case Xpdl2Package.TRANSITION__OBJECT:
            return basicSetObject(null, msgs);
        case Xpdl2Package.TRANSITION__FLOW_CONTAINER:
            return basicSetFlowContainer(null, msgs);
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
        case Xpdl2Package.TRANSITION__FLOW_CONTAINER:
            return eInternalContainer().eInverseRemove(this,
                    Xpdl2Package.FLOW_CONTAINER__TRANSITIONS,
                    FlowContainer.class,
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
        case Xpdl2Package.TRANSITION__EXTENDED_ATTRIBUTES:
            return getExtendedAttributes();
        case Xpdl2Package.TRANSITION__CONNECTOR_GRAPHICS_INFOS:
            return getConnectorGraphicsInfos();
        case Xpdl2Package.TRANSITION__DESCRIPTION:
            return getDescription();
        case Xpdl2Package.TRANSITION__CONDITION:
            return getCondition();
        case Xpdl2Package.TRANSITION__ASSIGNMENTS:
            return getAssignments();
        case Xpdl2Package.TRANSITION__OBJECT:
            return getObject();
        case Xpdl2Package.TRANSITION__FROM:
            return getFrom();
        case Xpdl2Package.TRANSITION__QUANTITY:
            return getQuantity();
        case Xpdl2Package.TRANSITION__TO:
            return getTo();
        case Xpdl2Package.TRANSITION__FLOW_CONTAINER:
            return getFlowContainer();
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
        case Xpdl2Package.TRANSITION__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            getExtendedAttributes()
                    .addAll((Collection<? extends ExtendedAttribute>) newValue);
            return;
        case Xpdl2Package.TRANSITION__CONNECTOR_GRAPHICS_INFOS:
            getConnectorGraphicsInfos().clear();
            getConnectorGraphicsInfos()
                    .addAll((Collection<? extends ConnectorGraphicsInfo>) newValue);
            return;
        case Xpdl2Package.TRANSITION__DESCRIPTION:
            setDescription((Description) newValue);
            return;
        case Xpdl2Package.TRANSITION__CONDITION:
            setCondition((Condition) newValue);
            return;
        case Xpdl2Package.TRANSITION__ASSIGNMENTS:
            getAssignments().clear();
            getAssignments()
                    .addAll((Collection<? extends Assignment>) newValue);
            return;
        case Xpdl2Package.TRANSITION__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) newValue);
            return;
        case Xpdl2Package.TRANSITION__FROM:
            setFrom((String) newValue);
            return;
        case Xpdl2Package.TRANSITION__QUANTITY:
            setQuantity((Integer) newValue);
            return;
        case Xpdl2Package.TRANSITION__TO:
            setTo((String) newValue);
            return;
        case Xpdl2Package.TRANSITION__FLOW_CONTAINER:
            setFlowContainer((FlowContainer) newValue);
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
        case Xpdl2Package.TRANSITION__EXTENDED_ATTRIBUTES:
            getExtendedAttributes().clear();
            return;
        case Xpdl2Package.TRANSITION__CONNECTOR_GRAPHICS_INFOS:
            getConnectorGraphicsInfos().clear();
            return;
        case Xpdl2Package.TRANSITION__DESCRIPTION:
            setDescription((Description) null);
            return;
        case Xpdl2Package.TRANSITION__CONDITION:
            setCondition((Condition) null);
            return;
        case Xpdl2Package.TRANSITION__ASSIGNMENTS:
            getAssignments().clear();
            return;
        case Xpdl2Package.TRANSITION__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) null);
            return;
        case Xpdl2Package.TRANSITION__FROM:
            setFrom(FROM_EDEFAULT);
            return;
        case Xpdl2Package.TRANSITION__QUANTITY:
            unsetQuantity();
            return;
        case Xpdl2Package.TRANSITION__TO:
            setTo(TO_EDEFAULT);
            return;
        case Xpdl2Package.TRANSITION__FLOW_CONTAINER:
            setFlowContainer((FlowContainer) null);
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
        case Xpdl2Package.TRANSITION__EXTENDED_ATTRIBUTES:
            return extendedAttributes != null && !extendedAttributes.isEmpty();
        case Xpdl2Package.TRANSITION__CONNECTOR_GRAPHICS_INFOS:
            return connectorGraphicsInfos != null
                    && !connectorGraphicsInfos.isEmpty();
        case Xpdl2Package.TRANSITION__DESCRIPTION:
            return description != null;
        case Xpdl2Package.TRANSITION__CONDITION:
            return condition != null;
        case Xpdl2Package.TRANSITION__ASSIGNMENTS:
            return assignments != null && !assignments.isEmpty();
        case Xpdl2Package.TRANSITION__OBJECT:
            return object != null;
        case Xpdl2Package.TRANSITION__FROM:
            return FROM_EDEFAULT == null ? from != null : !FROM_EDEFAULT
                    .equals(from);
        case Xpdl2Package.TRANSITION__QUANTITY:
            return isSetQuantity();
        case Xpdl2Package.TRANSITION__TO:
            return TO_EDEFAULT == null ? to != null : !TO_EDEFAULT.equals(to);
        case Xpdl2Package.TRANSITION__FLOW_CONTAINER:
            return getFlowContainer() != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.TRANSITION__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == GraphicalConnector.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.TRANSITION__CONNECTOR_GRAPHICS_INFOS:
                return Xpdl2Package.GRAPHICAL_CONNECTOR__CONNECTOR_GRAPHICS_INFOS;
            default:
                return -1;
            }
        }
        if (baseClass == DescribedElement.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.TRANSITION__DESCRIPTION:
                return Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == ExtendedAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES:
                return Xpdl2Package.TRANSITION__EXTENDED_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == GraphicalConnector.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.GRAPHICAL_CONNECTOR__CONNECTOR_GRAPHICS_INFOS:
                return Xpdl2Package.TRANSITION__CONNECTOR_GRAPHICS_INFOS;
            default:
                return -1;
            }
        }
        if (baseClass == DescribedElement.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION:
                return Xpdl2Package.TRANSITION__DESCRIPTION;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (from: "); //$NON-NLS-1$
        result.append(from);
        result.append(", quantity: "); //$NON-NLS-1$
        if (quantityESet)
            result.append(quantity);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", to: "); //$NON-NLS-1$
        result.append(to);
        result.append(')');
        return result.toString();
    }

} // TransitionImpl
