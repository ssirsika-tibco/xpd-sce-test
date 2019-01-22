/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.AllocationMethod;
import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.Location;
import com.tibco.n2.directory.model.de.OrgUnit;
import com.tibco.n2.directory.model.de.Organization;
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
 * An implementation of the model object '<em><b>Organization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrganizationImpl#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrganizationImpl#getOrgUnit <em>Org Unit</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrganizationImpl#getSystemAction <em>System Action</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrganizationImpl#getAllocMethod <em>Alloc Method</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrganizationImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.OrganizationImpl#getPlugin <em>Plugin</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrganizationImpl extends TypedEntityImpl implements Organization {
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
    protected OrganizationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.ORGANIZATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getChoiceGroup() {
        if (choiceGroup == null) {
            choiceGroup = new BasicFeatureMap(this, DePackage.ORGANIZATION__CHOICE_GROUP);
        }
        return choiceGroup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<OrgUnit> getOrgUnit() {
        return getChoiceGroup().list(DePackage.Literals.ORGANIZATION__ORG_UNIT);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<SystemAction> getSystemAction() {
        return getChoiceGroup().list(DePackage.Literals.ORGANIZATION__SYSTEM_ACTION);
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
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.ORGANIZATION__ALLOC_METHOD, oldAllocMethod, allocMethod, !oldAllocMethodESet));
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
            eNotify(new ENotificationImpl(this, Notification.UNSET, DePackage.ORGANIZATION__ALLOC_METHOD, oldAllocMethod, ALLOC_METHOD_EDEFAULT, oldAllocMethodESet));
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
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.ORGANIZATION__LOCATION, oldLocation, location));
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
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.ORGANIZATION__PLUGIN, oldPlugin, plugin));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.ORGANIZATION__CHOICE_GROUP:
                return ((InternalEList<?>)getChoiceGroup()).basicRemove(otherEnd, msgs);
            case DePackage.ORGANIZATION__ORG_UNIT:
                return ((InternalEList<?>)getOrgUnit()).basicRemove(otherEnd, msgs);
            case DePackage.ORGANIZATION__SYSTEM_ACTION:
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
            case DePackage.ORGANIZATION__CHOICE_GROUP:
                if (coreType) return getChoiceGroup();
                return ((FeatureMap.Internal)getChoiceGroup()).getWrapper();
            case DePackage.ORGANIZATION__ORG_UNIT:
                return getOrgUnit();
            case DePackage.ORGANIZATION__SYSTEM_ACTION:
                return getSystemAction();
            case DePackage.ORGANIZATION__ALLOC_METHOD:
                return getAllocMethod();
            case DePackage.ORGANIZATION__LOCATION:
                return getLocation();
            case DePackage.ORGANIZATION__PLUGIN:
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
            case DePackage.ORGANIZATION__CHOICE_GROUP:
                ((FeatureMap.Internal)getChoiceGroup()).set(newValue);
                return;
            case DePackage.ORGANIZATION__ORG_UNIT:
                getOrgUnit().clear();
                getOrgUnit().addAll((Collection<? extends OrgUnit>)newValue);
                return;
            case DePackage.ORGANIZATION__SYSTEM_ACTION:
                getSystemAction().clear();
                getSystemAction().addAll((Collection<? extends SystemAction>)newValue);
                return;
            case DePackage.ORGANIZATION__ALLOC_METHOD:
                setAllocMethod((AllocationMethod)newValue);
                return;
            case DePackage.ORGANIZATION__LOCATION:
                setLocation((Location)newValue);
                return;
            case DePackage.ORGANIZATION__PLUGIN:
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
            case DePackage.ORGANIZATION__CHOICE_GROUP:
                getChoiceGroup().clear();
                return;
            case DePackage.ORGANIZATION__ORG_UNIT:
                getOrgUnit().clear();
                return;
            case DePackage.ORGANIZATION__SYSTEM_ACTION:
                getSystemAction().clear();
                return;
            case DePackage.ORGANIZATION__ALLOC_METHOD:
                unsetAllocMethod();
                return;
            case DePackage.ORGANIZATION__LOCATION:
                setLocation((Location)null);
                return;
            case DePackage.ORGANIZATION__PLUGIN:
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
            case DePackage.ORGANIZATION__CHOICE_GROUP:
                return choiceGroup != null && !choiceGroup.isEmpty();
            case DePackage.ORGANIZATION__ORG_UNIT:
                return !getOrgUnit().isEmpty();
            case DePackage.ORGANIZATION__SYSTEM_ACTION:
                return !getSystemAction().isEmpty();
            case DePackage.ORGANIZATION__ALLOC_METHOD:
                return isSetAllocMethod();
            case DePackage.ORGANIZATION__LOCATION:
                return location != null;
            case DePackage.ORGANIZATION__PLUGIN:
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
        result.append(", plugin: ");
        result.append(plugin);
        result.append(')');
        return result.toString();
    }

} //OrganizationImpl
