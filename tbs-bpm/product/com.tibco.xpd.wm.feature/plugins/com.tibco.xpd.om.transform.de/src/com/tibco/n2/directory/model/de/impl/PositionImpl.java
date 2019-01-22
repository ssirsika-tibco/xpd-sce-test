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
import com.tibco.n2.directory.model.de.Feature;
import com.tibco.n2.directory.model.de.Location;
import com.tibco.n2.directory.model.de.Position;
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
 * An implementation of the model object '<em><b>Position</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.PositionImpl#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.PositionImpl#getReqCapability <em>Req Capability</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.PositionImpl#getPrivilegeHeld <em>Privilege Held</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.PositionImpl#getSystemAction <em>System Action</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.PositionImpl#getAllocMethod <em>Alloc Method</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.PositionImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.PositionImpl#getIdealNumber <em>Ideal Number</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.PositionImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.PositionImpl#getPlugin <em>Plugin</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PositionImpl extends TypedEntityImpl implements Position {
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
     * The cached value of the '{@link #getFeature() <em>Feature</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFeature()
     * @generated
     * @ordered
     */
    protected Feature feature;

    /**
     * The default value of the '{@link #getIdealNumber() <em>Ideal Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdealNumber()
     * @generated
     * @ordered
     */
    protected static final long IDEAL_NUMBER_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getIdealNumber() <em>Ideal Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIdealNumber()
     * @generated
     * @ordered
     */
    protected long idealNumber = IDEAL_NUMBER_EDEFAULT;

    /**
     * This is true if the Ideal Number attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean idealNumberESet;

    /**
     * The cached value of the '{@link #getLocation() <em>Location</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLocation()
     * @generated
     * @ordered
     */
    protected Location location;

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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PositionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.POSITION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getChoiceGroup() {
        if (choiceGroup == null) {
            choiceGroup = new BasicFeatureMap(this, DePackage.POSITION__CHOICE_GROUP);
        }
        return choiceGroup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<CapabilityHolding> getReqCapability() {
        return getChoiceGroup().list(DePackage.Literals.POSITION__REQ_CAPABILITY);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PrivilegeHolding> getPrivilegeHeld() {
        return getChoiceGroup().list(DePackage.Literals.POSITION__PRIVILEGE_HELD);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<SystemAction> getSystemAction() {
        return getChoiceGroup().list(DePackage.Literals.POSITION__SYSTEM_ACTION);
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
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.POSITION__ALLOC_METHOD, oldAllocMethod, allocMethod, !oldAllocMethodESet));
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, DePackage.POSITION__ALLOC_METHOD, oldAllocMethod, ALLOC_METHOD_EDEFAULT, oldAllocMethodESet));
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
    public Feature getFeature() {
        return feature;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFeature(Feature newFeature) {
        Feature oldFeature = feature;
        feature = newFeature;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.POSITION__FEATURE, oldFeature, feature));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getIdealNumber() {
        return idealNumber;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIdealNumber(long newIdealNumber) {
        long oldIdealNumber = idealNumber;
        idealNumber = newIdealNumber;
        boolean oldIdealNumberESet = idealNumberESet;
        idealNumberESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.POSITION__IDEAL_NUMBER, oldIdealNumber, idealNumber, !oldIdealNumberESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetIdealNumber() {
        long oldIdealNumber = idealNumber;
        boolean oldIdealNumberESet = idealNumberESet;
        idealNumber = IDEAL_NUMBER_EDEFAULT;
        idealNumberESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DePackage.POSITION__IDEAL_NUMBER, oldIdealNumber, IDEAL_NUMBER_EDEFAULT, oldIdealNumberESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetIdealNumber() {
        return idealNumberESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Location getLocation() {
        return location;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLocation(Location newLocation) {
        Location oldLocation = location;
        location = newLocation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.POSITION__LOCATION, oldLocation, location));
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
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.POSITION__PLUGIN, oldPlugin, plugin));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.POSITION__CHOICE_GROUP:
                return ((InternalEList<?>)getChoiceGroup()).basicRemove(otherEnd, msgs);
            case DePackage.POSITION__REQ_CAPABILITY:
                return ((InternalEList<?>)getReqCapability()).basicRemove(otherEnd, msgs);
            case DePackage.POSITION__PRIVILEGE_HELD:
                return ((InternalEList<?>)getPrivilegeHeld()).basicRemove(otherEnd, msgs);
            case DePackage.POSITION__SYSTEM_ACTION:
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
            case DePackage.POSITION__CHOICE_GROUP:
                if (coreType) return getChoiceGroup();
                return ((FeatureMap.Internal)getChoiceGroup()).getWrapper();
            case DePackage.POSITION__REQ_CAPABILITY:
                return getReqCapability();
            case DePackage.POSITION__PRIVILEGE_HELD:
                return getPrivilegeHeld();
            case DePackage.POSITION__SYSTEM_ACTION:
                return getSystemAction();
            case DePackage.POSITION__ALLOC_METHOD:
                return getAllocMethod();
            case DePackage.POSITION__FEATURE:
                return getFeature();
            case DePackage.POSITION__IDEAL_NUMBER:
                return getIdealNumber();
            case DePackage.POSITION__LOCATION:
                return getLocation();
            case DePackage.POSITION__PLUGIN:
                return getPlugin();
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
            case DePackage.POSITION__CHOICE_GROUP:
                ((FeatureMap.Internal)getChoiceGroup()).set(newValue);
                return;
            case DePackage.POSITION__REQ_CAPABILITY:
                getReqCapability().clear();
                getReqCapability().addAll((Collection<? extends CapabilityHolding>)newValue);
                return;
            case DePackage.POSITION__PRIVILEGE_HELD:
                getPrivilegeHeld().clear();
                getPrivilegeHeld().addAll((Collection<? extends PrivilegeHolding>)newValue);
                return;
            case DePackage.POSITION__SYSTEM_ACTION:
                getSystemAction().clear();
                getSystemAction().addAll((Collection<? extends SystemAction>)newValue);
                return;
            case DePackage.POSITION__ALLOC_METHOD:
                setAllocMethod((AllocationMethod)newValue);
                return;
            case DePackage.POSITION__FEATURE:
                setFeature((Feature)newValue);
                return;
            case DePackage.POSITION__IDEAL_NUMBER:
                setIdealNumber((Long)newValue);
                return;
            case DePackage.POSITION__LOCATION:
                setLocation((Location)newValue);
                return;
            case DePackage.POSITION__PLUGIN:
                setPlugin((String)newValue);
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
            case DePackage.POSITION__CHOICE_GROUP:
                getChoiceGroup().clear();
                return;
            case DePackage.POSITION__REQ_CAPABILITY:
                getReqCapability().clear();
                return;
            case DePackage.POSITION__PRIVILEGE_HELD:
                getPrivilegeHeld().clear();
                return;
            case DePackage.POSITION__SYSTEM_ACTION:
                getSystemAction().clear();
                return;
            case DePackage.POSITION__ALLOC_METHOD:
                unsetAllocMethod();
                return;
            case DePackage.POSITION__FEATURE:
                setFeature((Feature)null);
                return;
            case DePackage.POSITION__IDEAL_NUMBER:
                unsetIdealNumber();
                return;
            case DePackage.POSITION__LOCATION:
                setLocation((Location)null);
                return;
            case DePackage.POSITION__PLUGIN:
                setPlugin(PLUGIN_EDEFAULT);
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
            case DePackage.POSITION__CHOICE_GROUP:
                return choiceGroup != null && !choiceGroup.isEmpty();
            case DePackage.POSITION__REQ_CAPABILITY:
                return !getReqCapability().isEmpty();
            case DePackage.POSITION__PRIVILEGE_HELD:
                return !getPrivilegeHeld().isEmpty();
            case DePackage.POSITION__SYSTEM_ACTION:
                return !getSystemAction().isEmpty();
            case DePackage.POSITION__ALLOC_METHOD:
                return isSetAllocMethod();
            case DePackage.POSITION__FEATURE:
                return feature != null;
            case DePackage.POSITION__IDEAL_NUMBER:
                return isSetIdealNumber();
            case DePackage.POSITION__LOCATION:
                return location != null;
            case DePackage.POSITION__PLUGIN:
                return PLUGIN_EDEFAULT == null ? plugin != null : !PLUGIN_EDEFAULT.equals(plugin);
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
        result.append(", idealNumber: ");
        if (idealNumberESet) result.append(idealNumber); else result.append("<unset>");
        result.append(", plugin: ");
        result.append(plugin);
        result.append(')');
        return result.toString();
    }

} //PositionImpl
