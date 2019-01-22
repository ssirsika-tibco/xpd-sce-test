/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.AllocationMethod;
import com.tibco.n2.directory.model.de.CapabilityHolding;
import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.Group;
import com.tibco.n2.directory.model.de.PrivilegeHolding;
import com.tibco.n2.directory.model.de.SystemAction;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.GroupImpl#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.GroupImpl#getReqCapability <em>Req Capability</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.GroupImpl#getPrivilegeHeld <em>Privilege Held</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.GroupImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.GroupImpl#getSystemAction <em>System Action</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.GroupImpl#getAllocMethod <em>Alloc Method</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.GroupImpl#getPlugin <em>Plugin</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.GroupImpl#isUndeliveredQueue <em>Undelivered Queue</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GroupImpl extends NamedEntityImpl implements Group {
    /**
     * The cached value of the '{@link #getChoiceGroup() <em>Choice Group</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChoiceGroup()
     * @generated
     * @ordered
     */
    protected FeatureMap choiceGroup;

    /**
     * The default value of the '{@link #getAllocMethod() <em>Alloc Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAllocMethod()
     * @generated
     * @ordered
     */
    protected static final AllocationMethod ALLOC_METHOD_EDEFAULT = AllocationMethod.ANY;

    /**
     * The cached value of the '{@link #getAllocMethod() <em>Alloc Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAllocMethod()
     * @generated
     * @ordered
     */
    protected AllocationMethod allocMethod = ALLOC_METHOD_EDEFAULT;

    /**
     * This is true if the Alloc Method attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean allocMethodESet;

    /**
     * The default value of the '{@link #getPlugin() <em>Plugin</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPlugin()
     * @generated
     * @ordered
     */
    protected static final String PLUGIN_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPlugin() <em>Plugin</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPlugin()
     * @generated
     * @ordered
     */
    protected String plugin = PLUGIN_EDEFAULT;

    /**
     * The default value of the '{@link #isUndeliveredQueue() <em>Undelivered Queue</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUndeliveredQueue()
     * @generated
     * @ordered
     */
    protected static final boolean UNDELIVERED_QUEUE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUndeliveredQueue() <em>Undelivered Queue</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUndeliveredQueue()
     * @generated
     * @ordered
     */
    protected boolean undeliveredQueue = UNDELIVERED_QUEUE_EDEFAULT;

    /**
     * This is true if the Undelivered Queue attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean undeliveredQueueESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected GroupImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.GROUP;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getChoiceGroup() {
        if (choiceGroup == null) {
            choiceGroup = new BasicFeatureMap(this, DePackage.GROUP__CHOICE_GROUP);
        }
        return choiceGroup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<CapabilityHolding> getReqCapability() {
        return getChoiceGroup().list(DePackage.Literals.GROUP__REQ_CAPABILITY);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PrivilegeHolding> getPrivilegeHeld() {
        return getChoiceGroup().list(DePackage.Literals.GROUP__PRIVILEGE_HELD);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Group> getGroup() {
        return getChoiceGroup().list(DePackage.Literals.GROUP__GROUP);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<SystemAction> getSystemAction() {
        return getChoiceGroup().list(DePackage.Literals.GROUP__SYSTEM_ACTION);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocationMethod getAllocMethod() {
        return allocMethod;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAllocMethod(AllocationMethod newAllocMethod) {
        AllocationMethod oldAllocMethod = allocMethod;
        allocMethod = newAllocMethod == null ? ALLOC_METHOD_EDEFAULT : newAllocMethod;
        boolean oldAllocMethodESet = allocMethodESet;
        allocMethodESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.GROUP__ALLOC_METHOD, oldAllocMethod, allocMethod, !oldAllocMethodESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAllocMethod() {
        AllocationMethod oldAllocMethod = allocMethod;
        boolean oldAllocMethodESet = allocMethodESet;
        allocMethod = ALLOC_METHOD_EDEFAULT;
        allocMethodESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DePackage.GROUP__ALLOC_METHOD, oldAllocMethod, ALLOC_METHOD_EDEFAULT, oldAllocMethodESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAllocMethod() {
        return allocMethodESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPlugin() {
        return plugin;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPlugin(String newPlugin) {
        String oldPlugin = plugin;
        plugin = newPlugin;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.GROUP__PLUGIN, oldPlugin, plugin));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isUndeliveredQueue() {
        return undeliveredQueue;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUndeliveredQueue(boolean newUndeliveredQueue) {
        boolean oldUndeliveredQueue = undeliveredQueue;
        undeliveredQueue = newUndeliveredQueue;
        boolean oldUndeliveredQueueESet = undeliveredQueueESet;
        undeliveredQueueESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.GROUP__UNDELIVERED_QUEUE, oldUndeliveredQueue, undeliveredQueue, !oldUndeliveredQueueESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetUndeliveredQueue() {
        boolean oldUndeliveredQueue = undeliveredQueue;
        boolean oldUndeliveredQueueESet = undeliveredQueueESet;
        undeliveredQueue = UNDELIVERED_QUEUE_EDEFAULT;
        undeliveredQueueESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DePackage.GROUP__UNDELIVERED_QUEUE, oldUndeliveredQueue, UNDELIVERED_QUEUE_EDEFAULT, oldUndeliveredQueueESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetUndeliveredQueue() {
        return undeliveredQueueESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.GROUP__CHOICE_GROUP:
                return ((InternalEList<?>)getChoiceGroup()).basicRemove(otherEnd, msgs);
            case DePackage.GROUP__REQ_CAPABILITY:
                return ((InternalEList<?>)getReqCapability()).basicRemove(otherEnd, msgs);
            case DePackage.GROUP__PRIVILEGE_HELD:
                return ((InternalEList<?>)getPrivilegeHeld()).basicRemove(otherEnd, msgs);
            case DePackage.GROUP__GROUP:
                return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
            case DePackage.GROUP__SYSTEM_ACTION:
                return ((InternalEList<?>)getSystemAction()).basicRemove(otherEnd, msgs);
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
            case DePackage.GROUP__CHOICE_GROUP:
                if (coreType) return getChoiceGroup();
                return ((FeatureMap.Internal)getChoiceGroup()).getWrapper();
            case DePackage.GROUP__REQ_CAPABILITY:
                return getReqCapability();
            case DePackage.GROUP__PRIVILEGE_HELD:
                return getPrivilegeHeld();
            case DePackage.GROUP__GROUP:
                return getGroup();
            case DePackage.GROUP__SYSTEM_ACTION:
                return getSystemAction();
            case DePackage.GROUP__ALLOC_METHOD:
                return getAllocMethod();
            case DePackage.GROUP__PLUGIN:
                return getPlugin();
            case DePackage.GROUP__UNDELIVERED_QUEUE:
                return isUndeliveredQueue();
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
            case DePackage.GROUP__CHOICE_GROUP:
                ((FeatureMap.Internal)getChoiceGroup()).set(newValue);
                return;
            case DePackage.GROUP__REQ_CAPABILITY:
                getReqCapability().clear();
                getReqCapability().addAll((Collection<? extends CapabilityHolding>)newValue);
                return;
            case DePackage.GROUP__PRIVILEGE_HELD:
                getPrivilegeHeld().clear();
                getPrivilegeHeld().addAll((Collection<? extends PrivilegeHolding>)newValue);
                return;
            case DePackage.GROUP__GROUP:
                getGroup().clear();
                getGroup().addAll((Collection<? extends Group>)newValue);
                return;
            case DePackage.GROUP__SYSTEM_ACTION:
                getSystemAction().clear();
                getSystemAction().addAll((Collection<? extends SystemAction>)newValue);
                return;
            case DePackage.GROUP__ALLOC_METHOD:
                setAllocMethod((AllocationMethod)newValue);
                return;
            case DePackage.GROUP__PLUGIN:
                setPlugin((String)newValue);
                return;
            case DePackage.GROUP__UNDELIVERED_QUEUE:
                setUndeliveredQueue((Boolean)newValue);
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
            case DePackage.GROUP__CHOICE_GROUP:
                getChoiceGroup().clear();
                return;
            case DePackage.GROUP__REQ_CAPABILITY:
                getReqCapability().clear();
                return;
            case DePackage.GROUP__PRIVILEGE_HELD:
                getPrivilegeHeld().clear();
                return;
            case DePackage.GROUP__GROUP:
                getGroup().clear();
                return;
            case DePackage.GROUP__SYSTEM_ACTION:
                getSystemAction().clear();
                return;
            case DePackage.GROUP__ALLOC_METHOD:
                unsetAllocMethod();
                return;
            case DePackage.GROUP__PLUGIN:
                setPlugin(PLUGIN_EDEFAULT);
                return;
            case DePackage.GROUP__UNDELIVERED_QUEUE:
                unsetUndeliveredQueue();
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
            case DePackage.GROUP__CHOICE_GROUP:
                return choiceGroup != null && !choiceGroup.isEmpty();
            case DePackage.GROUP__REQ_CAPABILITY:
                return !getReqCapability().isEmpty();
            case DePackage.GROUP__PRIVILEGE_HELD:
                return !getPrivilegeHeld().isEmpty();
            case DePackage.GROUP__GROUP:
                return !getGroup().isEmpty();
            case DePackage.GROUP__SYSTEM_ACTION:
                return !getSystemAction().isEmpty();
            case DePackage.GROUP__ALLOC_METHOD:
                return isSetAllocMethod();
            case DePackage.GROUP__PLUGIN:
                return PLUGIN_EDEFAULT == null ? plugin != null : !PLUGIN_EDEFAULT.equals(plugin);
            case DePackage.GROUP__UNDELIVERED_QUEUE:
                return isSetUndeliveredQueue();
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
        result.append(" (choiceGroup: ");
        result.append(choiceGroup);
        result.append(", allocMethod: ");
        if (allocMethodESet) result.append(allocMethod); else result.append("<unset>");
        result.append(", plugin: ");
        result.append(plugin);
        result.append(", undeliveredQueue: ");
        if (undeliveredQueueESet) result.append(undeliveredQueue); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //GroupImpl
