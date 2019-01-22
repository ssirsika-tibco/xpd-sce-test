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
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Lane</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LaneImpl#getNodeGraphicsInfos <em>Node Graphics Infos</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LaneImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LaneImpl#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LaneImpl#getDeprecatedParentLane <em>Deprecated Parent Lane</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LaneImpl#getDeprecatedParentPoolId <em>Deprecated Parent Pool Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LaneImpl#getParentPool <em>Parent Pool</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LaneImpl#getPerformers <em>Performers</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.LaneImpl#getNestedLane <em>Nested Lane</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LaneImpl extends NamedElementImpl implements Lane {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getNodeGraphicsInfos() <em>Node Graphics Infos</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getNodeGraphicsInfos()
     * @generated
     * @ordered
     */
    protected EList<NodeGraphicsInfo> nodeGraphicsInfos;

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
     * The cached value of the '{@link #getObject() <em>Object</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getObject()
     * @generated
     * @ordered
     */
    protected com.tibco.xpd.xpdl2.Object object;

    /**
     * The default value of the '{@link #getDeprecatedParentLane() <em>Deprecated Parent Lane</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedParentLane()
     * @generated
     * @ordered
     */
    protected static final String DEPRECATED_PARENT_LANE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDeprecatedParentLane() <em>Deprecated Parent Lane</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedParentLane()
     * @generated
     * @ordered
     */
    protected String deprecatedParentLane = DEPRECATED_PARENT_LANE_EDEFAULT;

    /**
     * The default value of the '{@link #getDeprecatedParentPoolId() <em>Deprecated Parent Pool Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedParentPoolId()
     * @generated
     * @ordered
     */
    protected static final String DEPRECATED_PARENT_POOL_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDeprecatedParentPoolId() <em>Deprecated Parent Pool Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedParentPoolId()
     * @generated
     * @ordered
     */
    protected String deprecatedParentPoolId =
            DEPRECATED_PARENT_POOL_ID_EDEFAULT;

    /**
     * The cached value of the '{@link #getPerformers() <em>Performers</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPerformers()
     * @generated
     * @ordered
     */
    protected Performers performers;

    /**
     * The cached value of the '{@link #getNestedLane() <em>Nested Lane</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNestedLane()
     * @generated
     * @ordered
     */
    protected EList<Lane> nestedLane;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected LaneImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.LANE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<NodeGraphicsInfo> getNodeGraphicsInfos() {
        if (nodeGraphicsInfos == null) {
            nodeGraphicsInfos =
                    new EObjectContainmentEList<NodeGraphicsInfo>(
                            NodeGraphicsInfo.class, this,
                            Xpdl2Package.LANE__NODE_GRAPHICS_INFOS);
        }
        return nodeGraphicsInfos;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements =
                    new BasicFeatureMap(this, Xpdl2Package.LANE__OTHER_ELEMENTS);
        }
        return otherElements;
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
                            Xpdl2Package.LANE__OBJECT, oldObject, newObject);
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
                                        - Xpdl2Package.LANE__OBJECT,
                                null,
                                msgs);
            if (newObject != null)
                msgs =
                        ((InternalEObject) newObject).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.LANE__OBJECT,
                                null,
                                msgs);
            msgs = basicSetObject(newObject, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LANE__OBJECT, newObject, newObject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDeprecatedParentLane() {
        return deprecatedParentLane;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedParentLane(String newDeprecatedParentLane) {
        String oldDeprecatedParentLane = deprecatedParentLane;
        deprecatedParentLane = newDeprecatedParentLane;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LANE__DEPRECATED_PARENT_LANE,
                    oldDeprecatedParentLane, deprecatedParentLane));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDeprecatedParentPoolId() {
        return deprecatedParentPoolId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedParentPoolId(String newDeprecatedParentPoolId) {
        String oldDeprecatedParentPoolId = deprecatedParentPoolId;
        deprecatedParentPoolId = newDeprecatedParentPoolId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LANE__DEPRECATED_PARENT_POOL_ID,
                    oldDeprecatedParentPoolId, deprecatedParentPoolId));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Pool getParentPool() {
        if (eContainerFeatureID() != Xpdl2Package.LANE__PARENT_POOL)
            return null;
        return (Pool) eInternalContainer();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetParentPool(Pool newParentPool,
            NotificationChain msgs) {
        msgs =
                eBasicSetContainer((InternalEObject) newParentPool,
                        Xpdl2Package.LANE__PARENT_POOL,
                        msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setParentPool(Pool newParentPool) {
        if (newParentPool != eInternalContainer()
                || (eContainerFeatureID() != Xpdl2Package.LANE__PARENT_POOL && newParentPool != null)) {
            if (EcoreUtil.isAncestor(this, newParentPool))
                throw new IllegalArgumentException(
                        "Recursive containment not allowed for " + toString()); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newParentPool != null)
                msgs =
                        ((InternalEObject) newParentPool).eInverseAdd(this,
                                Xpdl2Package.POOL__LANES,
                                Pool.class,
                                msgs);
            msgs = basicSetParentPool(newParentPool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LANE__PARENT_POOL, newParentPool,
                    newParentPool));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Performers getPerformers() {
        return performers;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPerformers(Performers newPerformers,
            NotificationChain msgs) {
        Performers oldPerformers = performers;
        performers = newPerformers;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.LANE__PERFORMERS, oldPerformers,
                            newPerformers);
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
    public void setPerformers(Performers newPerformers) {
        if (newPerformers != performers) {
            NotificationChain msgs = null;
            if (performers != null)
                msgs =
                        ((InternalEObject) performers).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.LANE__PERFORMERS,
                                null,
                                msgs);
            if (newPerformers != null)
                msgs =
                        ((InternalEObject) newPerformers).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.LANE__PERFORMERS,
                                null,
                                msgs);
            msgs = basicSetPerformers(newPerformers, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.LANE__PERFORMERS, newPerformers, newPerformers));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Lane> getNestedLane() {
        if (nestedLane == null) {
            nestedLane =
                    new EObjectContainmentEList<Lane>(Lane.class, this,
                            Xpdl2Package.LANE__NESTED_LANE);
        }
        return nestedLane;
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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public NodeGraphicsInfo getNodeGraphicsInfoForTool(String id) {
        EList<NodeGraphicsInfo> ngis = getNodeGraphicsInfos();
        if (ngis != null) {
            for (NodeGraphicsInfo ngi : ngis) {
                String toolId = ngi.getToolId();

                if (id == null || id.length() == 0) {
                    if (toolId == null || toolId.length() == 0) {
                        return ngi;
                    }
                } else if (id.equals(toolId)) {
                    return ngi;
                }
            }

        }

        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Association> getIncomingAssociations() {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList getOutgoingAssociations() {
        if (getParentPool() == null
                || getParentPool().getParentPackage() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getParentPool().getParentPackage()
                .getAssociations(), Xpdl2Package.eINSTANCE
                .getAssociation_Source(), getId());
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.LANE__PARENT_POOL:
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParentPool((Pool) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.LANE__NODE_GRAPHICS_INFOS:
            return ((InternalEList<?>) getNodeGraphicsInfos())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.LANE__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.LANE__OBJECT:
            return basicSetObject(null, msgs);
        case Xpdl2Package.LANE__PARENT_POOL:
            return basicSetParentPool(null, msgs);
        case Xpdl2Package.LANE__PERFORMERS:
            return basicSetPerformers(null, msgs);
        case Xpdl2Package.LANE__NESTED_LANE:
            return ((InternalEList<?>) getNestedLane()).basicRemove(otherEnd,
                    msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(
            NotificationChain msgs) {
        switch (eContainerFeatureID()) {
        case Xpdl2Package.LANE__PARENT_POOL:
            return eInternalContainer().eInverseRemove(this,
                    Xpdl2Package.POOL__LANES,
                    Pool.class,
                    msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.LANE__NODE_GRAPHICS_INFOS:
            return getNodeGraphicsInfos();
        case Xpdl2Package.LANE__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.LANE__OBJECT:
            return getObject();
        case Xpdl2Package.LANE__DEPRECATED_PARENT_LANE:
            return getDeprecatedParentLane();
        case Xpdl2Package.LANE__DEPRECATED_PARENT_POOL_ID:
            return getDeprecatedParentPoolId();
        case Xpdl2Package.LANE__PARENT_POOL:
            return getParentPool();
        case Xpdl2Package.LANE__PERFORMERS:
            return getPerformers();
        case Xpdl2Package.LANE__NESTED_LANE:
            return getNestedLane();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case Xpdl2Package.LANE__NODE_GRAPHICS_INFOS:
            getNodeGraphicsInfos().clear();
            getNodeGraphicsInfos()
                    .addAll((Collection<? extends NodeGraphicsInfo>) newValue);
            return;
        case Xpdl2Package.LANE__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.LANE__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) newValue);
            return;
        case Xpdl2Package.LANE__DEPRECATED_PARENT_LANE:
            setDeprecatedParentLane((String) newValue);
            return;
        case Xpdl2Package.LANE__DEPRECATED_PARENT_POOL_ID:
            setDeprecatedParentPoolId((String) newValue);
            return;
        case Xpdl2Package.LANE__PARENT_POOL:
            setParentPool((Pool) newValue);
            return;
        case Xpdl2Package.LANE__PERFORMERS:
            setPerformers((Performers) newValue);
            return;
        case Xpdl2Package.LANE__NESTED_LANE:
            getNestedLane().clear();
            getNestedLane().addAll((Collection<? extends Lane>) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case Xpdl2Package.LANE__NODE_GRAPHICS_INFOS:
            getNodeGraphicsInfos().clear();
            return;
        case Xpdl2Package.LANE__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.LANE__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) null);
            return;
        case Xpdl2Package.LANE__DEPRECATED_PARENT_LANE:
            setDeprecatedParentLane(DEPRECATED_PARENT_LANE_EDEFAULT);
            return;
        case Xpdl2Package.LANE__DEPRECATED_PARENT_POOL_ID:
            setDeprecatedParentPoolId(DEPRECATED_PARENT_POOL_ID_EDEFAULT);
            return;
        case Xpdl2Package.LANE__PARENT_POOL:
            setParentPool((Pool) null);
            return;
        case Xpdl2Package.LANE__PERFORMERS:
            setPerformers((Performers) null);
            return;
        case Xpdl2Package.LANE__NESTED_LANE:
            getNestedLane().clear();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case Xpdl2Package.LANE__NODE_GRAPHICS_INFOS:
            return nodeGraphicsInfos != null && !nodeGraphicsInfos.isEmpty();
        case Xpdl2Package.LANE__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.LANE__OBJECT:
            return object != null;
        case Xpdl2Package.LANE__DEPRECATED_PARENT_LANE:
            return DEPRECATED_PARENT_LANE_EDEFAULT == null ? deprecatedParentLane != null
                    : !DEPRECATED_PARENT_LANE_EDEFAULT
                            .equals(deprecatedParentLane);
        case Xpdl2Package.LANE__DEPRECATED_PARENT_POOL_ID:
            return DEPRECATED_PARENT_POOL_ID_EDEFAULT == null ? deprecatedParentPoolId != null
                    : !DEPRECATED_PARENT_POOL_ID_EDEFAULT
                            .equals(deprecatedParentPoolId);
        case Xpdl2Package.LANE__PARENT_POOL:
            return getParentPool() != null;
        case Xpdl2Package.LANE__PERFORMERS:
            return performers != null;
        case Xpdl2Package.LANE__NESTED_LANE:
            return nestedLane != null && !nestedLane.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == GraphicalNode.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.LANE__NODE_GRAPHICS_INFOS:
                return Xpdl2Package.GRAPHICAL_NODE__NODE_GRAPHICS_INFOS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.LANE__OTHER_ELEMENTS:
                return Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;
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
        if (baseClass == GraphicalNode.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.GRAPHICAL_NODE__NODE_GRAPHICS_INFOS:
                return Xpdl2Package.LANE__NODE_GRAPHICS_INFOS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.LANE__OTHER_ELEMENTS;
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
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", deprecatedParentLane: "); //$NON-NLS-1$
        result.append(deprecatedParentLane);
        result.append(", deprecatedParentPoolId: "); //$NON-NLS-1$
        result.append(deprecatedParentPoolId);
        result.append(')');
        return result.toString();
    }

    public EList eAdapters() {
        if (eAdapters == null) {
            eAdapters = new EAdapterList(this);
        }
        return eAdapters;
    }

} // LaneImpl
