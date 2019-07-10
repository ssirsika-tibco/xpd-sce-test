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
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.OrientationType;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pool</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PoolImpl#getNodeGraphicsInfos <em>Node Graphics Infos</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PoolImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PoolImpl#getLanes <em>Lanes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PoolImpl#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PoolImpl#isBoundaryVisible <em>Boundary Visible</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PoolImpl#getOrientation <em>Orientation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PoolImpl#getParticipantId <em>Participant Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PoolImpl#getProcessId <em>Process Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PoolImpl#getParentPackage <em>Parent Package</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PoolImpl#isMainPool <em>Main Pool</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PoolImpl extends NamedElementImpl implements Pool {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getNodeGraphicsInfos() <em>Node Graphics Infos</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
     * The cached value of the '{@link #getLanes() <em>Lanes</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLanes()
     * @generated
     * @ordered
     */
    protected EList<Lane> lanes;

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
     * The default value of the '{@link #isBoundaryVisible() <em>Boundary Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isBoundaryVisible()
     * @generated
     * @ordered
     */
    protected static final boolean BOUNDARY_VISIBLE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isBoundaryVisible() <em>Boundary Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isBoundaryVisible()
     * @generated
     * @ordered
     */
    protected boolean boundaryVisible = BOUNDARY_VISIBLE_EDEFAULT;

    /**
     * This is true if the Boundary Visible attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean boundaryVisibleESet;

    /**
     * The default value of the '{@link #getOrientation() <em>Orientation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrientation()
     * @generated
     * @ordered
     */
    protected static final OrientationType ORIENTATION_EDEFAULT = OrientationType.HORIZONTAL_LITERAL;

    /**
     * The cached value of the '{@link #getOrientation() <em>Orientation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrientation()
     * @generated
     * @ordered
     */
    protected OrientationType orientation = ORIENTATION_EDEFAULT;

    /**
     * This is true if the Orientation attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean orientationESet;

    /**
     * The default value of the '{@link #getParticipantId() <em>Participant Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParticipantId()
     * @generated
     * @ordered
     */
    protected static final String PARTICIPANT_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getParticipantId() <em>Participant Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParticipantId()
     * @generated
     * @ordered
     */
    protected String participantId = PARTICIPANT_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getProcessId() <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessId()
     * @generated
     * @ordered
     */
    protected static final String PROCESS_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProcessId() <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessId()
     * @generated
     * @ordered
     */
    protected String processId = PROCESS_ID_EDEFAULT;

    /**
     * The default value of the '{@link #isMainPool() <em>Main Pool</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isMainPool()
     * @generated
     * @ordered
     */
    protected static final boolean MAIN_POOL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isMainPool() <em>Main Pool</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isMainPool()
     * @generated
     * @ordered
     */
    protected boolean mainPool = MAIN_POOL_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PoolImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.POOL;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<NodeGraphicsInfo> getNodeGraphicsInfos() {
        if (nodeGraphicsInfos == null) {
            nodeGraphicsInfos = new EObjectContainmentEList<NodeGraphicsInfo>(NodeGraphicsInfo.class, this,
                    Xpdl2Package.POOL__NODE_GRAPHICS_INFOS);
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
            otherElements = new BasicFeatureMap(this, Xpdl2Package.POOL__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Lane> getLanes() {
        if (lanes == null) {
            lanes = new EObjectContainmentWithInverseEList<Lane>(Lane.class, this, Xpdl2Package.POOL__LANES,
                    Xpdl2Package.LANE__PARENT_POOL);
        }
        return lanes;
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
    public NotificationChain basicSetObject(com.tibco.xpd.xpdl2.Object newObject, NotificationChain msgs) {
        com.tibco.xpd.xpdl2.Object oldObject = object;
        object = newObject;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.POOL__OBJECT, oldObject, newObject);
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
                msgs = ((InternalEObject) object)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.POOL__OBJECT, null, msgs);
            if (newObject != null)
                msgs = ((InternalEObject) newObject)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.POOL__OBJECT, null, msgs);
            msgs = basicSetObject(newObject, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.POOL__OBJECT, newObject, newObject));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isBoundaryVisible() {
        return boundaryVisible;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBoundaryVisible(boolean newBoundaryVisible) {
        boolean oldBoundaryVisible = boundaryVisible;
        boundaryVisible = newBoundaryVisible;
        boolean oldBoundaryVisibleESet = boundaryVisibleESet;
        boundaryVisibleESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.POOL__BOUNDARY_VISIBLE,
                    oldBoundaryVisible, boundaryVisible, !oldBoundaryVisibleESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetBoundaryVisible() {
        boolean oldBoundaryVisible = boundaryVisible;
        boolean oldBoundaryVisibleESet = boundaryVisibleESet;
        boundaryVisible = BOUNDARY_VISIBLE_EDEFAULT;
        boundaryVisibleESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.POOL__BOUNDARY_VISIBLE,
                    oldBoundaryVisible, BOUNDARY_VISIBLE_EDEFAULT, oldBoundaryVisibleESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetBoundaryVisible() {
        return boundaryVisibleESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrientationType getOrientation() {
        return orientation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOrientation(OrientationType newOrientation) {
        OrientationType oldOrientation = orientation;
        orientation = newOrientation == null ? ORIENTATION_EDEFAULT : newOrientation;
        boolean oldOrientationESet = orientationESet;
        orientationESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.POOL__ORIENTATION, oldOrientation,
                    orientation, !oldOrientationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetOrientation() {
        OrientationType oldOrientation = orientation;
        boolean oldOrientationESet = orientationESet;
        orientation = ORIENTATION_EDEFAULT;
        orientationESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.POOL__ORIENTATION, oldOrientation,
                    ORIENTATION_EDEFAULT, oldOrientationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetOrientation() {
        return orientationESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getParticipantId() {
        return participantId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParticipantId(String newParticipantId) {
        String oldParticipantId = participantId;
        participantId = newParticipantId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.POOL__PARTICIPANT_ID, oldParticipantId,
                    participantId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProcessId(String newProcessId) {
        String oldProcessId = processId;
        processId = newProcessId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.POOL__PROCESS_ID, oldProcessId,
                    processId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Package getParentPackage() {
        if (eContainerFeatureID() != Xpdl2Package.POOL__PARENT_PACKAGE)
            return null;
        return (com.tibco.xpd.xpdl2.Package) eInternalContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetParentPackage(com.tibco.xpd.xpdl2.Package newParentPackage,
            NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject) newParentPackage, Xpdl2Package.POOL__PARENT_PACKAGE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParentPackage(com.tibco.xpd.xpdl2.Package newParentPackage) {
        if (newParentPackage != eInternalContainer()
                || (eContainerFeatureID() != Xpdl2Package.POOL__PARENT_PACKAGE && newParentPackage != null)) {
            if (EcoreUtil.isAncestor(this, newParentPackage))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newParentPackage != null)
                msgs = ((InternalEObject) newParentPackage)
                        .eInverseAdd(this, Xpdl2Package.PACKAGE__POOLS, com.tibco.xpd.xpdl2.Package.class, msgs);
            msgs = basicSetParentPackage(newParentPackage, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.POOL__PARENT_PACKAGE, newParentPackage,
                    newParentPackage));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isMainPool() {
        return mainPool;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMainPool(boolean newMainPool) {
        boolean oldMainPool = mainPool;
        mainPool = newMainPool;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.POOL__MAIN_POOL, oldMainPool, mainPool));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
     * @generated NOT
     */
    public EList getIncomingAssociations() {
        if (getParentPackage() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getParentPackage().getAssociations(),
                Xpdl2Package.eINSTANCE.getAssociation_Target(),
                getId());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EList getOutgoingAssociations() {
        if (getParentPackage() == null) {
            return new BasicEList();
        }
        return EMFSearchUtil.findManyInList(getParentPackage().getAssociations(),
                Xpdl2Package.eINSTANCE.getAssociation_Source(),
                getId());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public Lane getLane(String id) {
        return (Lane) EMFSearchUtil.findInList(getLanes(), Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
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
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.POOL__LANES:
            return ((InternalEList<InternalEObject>) (InternalEList<?>) getLanes()).basicAdd(otherEnd, msgs);
        case Xpdl2Package.POOL__PARENT_PACKAGE:
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            return basicSetParentPackage((com.tibco.xpd.xpdl2.Package) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.POOL__NODE_GRAPHICS_INFOS:
            return ((InternalEList<?>) getNodeGraphicsInfos()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.POOL__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.POOL__LANES:
            return ((InternalEList<?>) getLanes()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.POOL__OBJECT:
            return basicSetObject(null, msgs);
        case Xpdl2Package.POOL__PARENT_PACKAGE:
            return basicSetParentPackage(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
        switch (eContainerFeatureID()) {
        case Xpdl2Package.POOL__PARENT_PACKAGE:
            return eInternalContainer()
                    .eInverseRemove(this, Xpdl2Package.PACKAGE__POOLS, com.tibco.xpd.xpdl2.Package.class, msgs);
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
        case Xpdl2Package.POOL__NODE_GRAPHICS_INFOS:
            return getNodeGraphicsInfos();
        case Xpdl2Package.POOL__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.POOL__LANES:
            return getLanes();
        case Xpdl2Package.POOL__OBJECT:
            return getObject();
        case Xpdl2Package.POOL__BOUNDARY_VISIBLE:
            return isBoundaryVisible();
        case Xpdl2Package.POOL__ORIENTATION:
            return getOrientation();
        case Xpdl2Package.POOL__PARTICIPANT_ID:
            return getParticipantId();
        case Xpdl2Package.POOL__PROCESS_ID:
            return getProcessId();
        case Xpdl2Package.POOL__PARENT_PACKAGE:
            return getParentPackage();
        case Xpdl2Package.POOL__MAIN_POOL:
            return isMainPool();
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
        case Xpdl2Package.POOL__NODE_GRAPHICS_INFOS:
            getNodeGraphicsInfos().clear();
            getNodeGraphicsInfos().addAll((Collection<? extends NodeGraphicsInfo>) newValue);
            return;
        case Xpdl2Package.POOL__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.POOL__LANES:
            getLanes().clear();
            getLanes().addAll((Collection<? extends Lane>) newValue);
            return;
        case Xpdl2Package.POOL__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) newValue);
            return;
        case Xpdl2Package.POOL__BOUNDARY_VISIBLE:
            setBoundaryVisible((Boolean) newValue);
            return;
        case Xpdl2Package.POOL__ORIENTATION:
            setOrientation((OrientationType) newValue);
            return;
        case Xpdl2Package.POOL__PARTICIPANT_ID:
            setParticipantId((String) newValue);
            return;
        case Xpdl2Package.POOL__PROCESS_ID:
            setProcessId((String) newValue);
            return;
        case Xpdl2Package.POOL__PARENT_PACKAGE:
            setParentPackage((com.tibco.xpd.xpdl2.Package) newValue);
            return;
        case Xpdl2Package.POOL__MAIN_POOL:
            setMainPool((Boolean) newValue);
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
        case Xpdl2Package.POOL__NODE_GRAPHICS_INFOS:
            getNodeGraphicsInfos().clear();
            return;
        case Xpdl2Package.POOL__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.POOL__LANES:
            getLanes().clear();
            return;
        case Xpdl2Package.POOL__OBJECT:
            setObject((com.tibco.xpd.xpdl2.Object) null);
            return;
        case Xpdl2Package.POOL__BOUNDARY_VISIBLE:
            unsetBoundaryVisible();
            return;
        case Xpdl2Package.POOL__ORIENTATION:
            unsetOrientation();
            return;
        case Xpdl2Package.POOL__PARTICIPANT_ID:
            setParticipantId(PARTICIPANT_ID_EDEFAULT);
            return;
        case Xpdl2Package.POOL__PROCESS_ID:
            setProcessId(PROCESS_ID_EDEFAULT);
            return;
        case Xpdl2Package.POOL__PARENT_PACKAGE:
            setParentPackage((com.tibco.xpd.xpdl2.Package) null);
            return;
        case Xpdl2Package.POOL__MAIN_POOL:
            setMainPool(MAIN_POOL_EDEFAULT);
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
        case Xpdl2Package.POOL__NODE_GRAPHICS_INFOS:
            return nodeGraphicsInfos != null && !nodeGraphicsInfos.isEmpty();
        case Xpdl2Package.POOL__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.POOL__LANES:
            return lanes != null && !lanes.isEmpty();
        case Xpdl2Package.POOL__OBJECT:
            return object != null;
        case Xpdl2Package.POOL__BOUNDARY_VISIBLE:
            return isSetBoundaryVisible();
        case Xpdl2Package.POOL__ORIENTATION:
            return isSetOrientation();
        case Xpdl2Package.POOL__PARTICIPANT_ID:
            return PARTICIPANT_ID_EDEFAULT == null ? participantId != null
                    : !PARTICIPANT_ID_EDEFAULT.equals(participantId);
        case Xpdl2Package.POOL__PROCESS_ID:
            return PROCESS_ID_EDEFAULT == null ? processId != null : !PROCESS_ID_EDEFAULT.equals(processId);
        case Xpdl2Package.POOL__PARENT_PACKAGE:
            return getParentPackage() != null;
        case Xpdl2Package.POOL__MAIN_POOL:
            return mainPool != MAIN_POOL_EDEFAULT;
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
        if (baseClass == GraphicalNode.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.POOL__NODE_GRAPHICS_INFOS:
                return Xpdl2Package.GRAPHICAL_NODE__NODE_GRAPHICS_INFOS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.POOL__OTHER_ELEMENTS:
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
        if (baseClass == GraphicalNode.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.GRAPHICAL_NODE__NODE_GRAPHICS_INFOS:
                return Xpdl2Package.POOL__NODE_GRAPHICS_INFOS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.POOL__OTHER_ELEMENTS;
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
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", boundaryVisible: "); //$NON-NLS-1$
        if (boundaryVisibleESet)
            result.append(boundaryVisible);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", orientation: "); //$NON-NLS-1$
        if (orientationESet)
            result.append(orientation);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", participantId: "); //$NON-NLS-1$
        result.append(participantId);
        result.append(", processId: "); //$NON-NLS-1$
        result.append(processId);
        result.append(", mainPool: "); //$NON-NLS-1$
        result.append(mainPool);
        result.append(')');
        return result.toString();
    }

} //PoolImpl
