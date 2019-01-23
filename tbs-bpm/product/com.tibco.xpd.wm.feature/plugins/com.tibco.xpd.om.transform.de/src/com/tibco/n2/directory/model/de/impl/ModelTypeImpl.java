/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de.impl;

import com.tibco.n2.directory.model.de.AttributeType;
import com.tibco.n2.directory.model.de.Capability;
import com.tibco.n2.directory.model.de.CapabilityCategory;
import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.Group;
import com.tibco.n2.directory.model.de.Location;
import com.tibco.n2.directory.model.de.MetaModel;
import com.tibco.n2.directory.model.de.ModelTemplate;
import com.tibco.n2.directory.model.de.ModelType;
import com.tibco.n2.directory.model.de.Organization;
import com.tibco.n2.directory.model.de.Privilege;
import com.tibco.n2.directory.model.de.PrivilegeCategory;
import com.tibco.n2.directory.model.de.Resource;
import com.tibco.n2.directory.model.de.SystemAction;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getMetaModel <em>Meta Model</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getChoiceGroup <em>Choice Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getModelTemplate <em>Model Template</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getCapability <em>Capability</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getCapabilityCategory <em>Capability Category</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getPrivilege <em>Privilege</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getPrivilegeCategory <em>Privilege Category</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getOrganization <em>Organization</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getResourceAttribute <em>Resource Attribute</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getSystemAction <em>System Action</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getResource <em>Resource</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.directory.model.de.impl.ModelTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelTypeImpl extends EObjectImpl implements ModelType {
    /**
     * The cached value of the '{@link #getMetaModel() <em>Meta Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMetaModel()
     * @generated
     * @ordered
     */
    protected MetaModel metaModel;

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
     * The cached value of the '{@link #getResource() <em>Resource</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResource()
     * @generated
     * @ordered
     */
    protected EList<Resource> resource;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected static final String VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected String version = VERSION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ModelTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DePackage.Literals.MODEL_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MetaModel getMetaModel() {
        return metaModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMetaModel(MetaModel newMetaModel, NotificationChain msgs) {
        MetaModel oldMetaModel = metaModel;
        metaModel = newMetaModel;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DePackage.MODEL_TYPE__META_MODEL, oldMetaModel, newMetaModel);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMetaModel(MetaModel newMetaModel) {
        if (newMetaModel != metaModel) {
            NotificationChain msgs = null;
            if (metaModel != null)
                msgs = ((InternalEObject)metaModel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DePackage.MODEL_TYPE__META_MODEL, null, msgs);
            if (newMetaModel != null)
                msgs = ((InternalEObject)newMetaModel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DePackage.MODEL_TYPE__META_MODEL, null, msgs);
            msgs = basicSetMetaModel(newMetaModel, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.MODEL_TYPE__META_MODEL, newMetaModel, newMetaModel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getChoiceGroup() {
        if (choiceGroup == null) {
            choiceGroup = new BasicFeatureMap(this, DePackage.MODEL_TYPE__CHOICE_GROUP);
        }
        return choiceGroup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ModelTemplate> getModelTemplate() {
        return getChoiceGroup().list(DePackage.Literals.MODEL_TYPE__MODEL_TEMPLATE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Capability> getCapability() {
        return getChoiceGroup().list(DePackage.Literals.MODEL_TYPE__CAPABILITY);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<CapabilityCategory> getCapabilityCategory() {
        return getChoiceGroup().list(DePackage.Literals.MODEL_TYPE__CAPABILITY_CATEGORY);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Privilege> getPrivilege() {
        return getChoiceGroup().list(DePackage.Literals.MODEL_TYPE__PRIVILEGE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PrivilegeCategory> getPrivilegeCategory() {
        return getChoiceGroup().list(DePackage.Literals.MODEL_TYPE__PRIVILEGE_CATEGORY);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Location> getLocation() {
        return getChoiceGroup().list(DePackage.Literals.MODEL_TYPE__LOCATION);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Group> getGroup() {
        return getChoiceGroup().list(DePackage.Literals.MODEL_TYPE__GROUP);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Organization> getOrganization() {
        return getChoiceGroup().list(DePackage.Literals.MODEL_TYPE__ORGANIZATION);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<AttributeType> getResourceAttribute() {
        return getChoiceGroup().list(DePackage.Literals.MODEL_TYPE__RESOURCE_ATTRIBUTE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<SystemAction> getSystemAction() {
        return getChoiceGroup().list(DePackage.Literals.MODEL_TYPE__SYSTEM_ACTION);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Resource> getResource() {
        if (resource == null) {
            resource = new EObjectContainmentEList<Resource>(Resource.class, this, DePackage.MODEL_TYPE__RESOURCE);
        }
        return resource;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.MODEL_TYPE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getVersion() {
        return version;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVersion(String newVersion) {
        String oldVersion = version;
        version = newVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DePackage.MODEL_TYPE__VERSION, oldVersion, version));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DePackage.MODEL_TYPE__META_MODEL:
                return basicSetMetaModel(null, msgs);
            case DePackage.MODEL_TYPE__CHOICE_GROUP:
                return ((InternalEList<?>)getChoiceGroup()).basicRemove(otherEnd, msgs);
            case DePackage.MODEL_TYPE__MODEL_TEMPLATE:
                return ((InternalEList<?>)getModelTemplate()).basicRemove(otherEnd, msgs);
            case DePackage.MODEL_TYPE__CAPABILITY:
                return ((InternalEList<?>)getCapability()).basicRemove(otherEnd, msgs);
            case DePackage.MODEL_TYPE__CAPABILITY_CATEGORY:
                return ((InternalEList<?>)getCapabilityCategory()).basicRemove(otherEnd, msgs);
            case DePackage.MODEL_TYPE__PRIVILEGE:
                return ((InternalEList<?>)getPrivilege()).basicRemove(otherEnd, msgs);
            case DePackage.MODEL_TYPE__PRIVILEGE_CATEGORY:
                return ((InternalEList<?>)getPrivilegeCategory()).basicRemove(otherEnd, msgs);
            case DePackage.MODEL_TYPE__LOCATION:
                return ((InternalEList<?>)getLocation()).basicRemove(otherEnd, msgs);
            case DePackage.MODEL_TYPE__GROUP:
                return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
            case DePackage.MODEL_TYPE__ORGANIZATION:
                return ((InternalEList<?>)getOrganization()).basicRemove(otherEnd, msgs);
            case DePackage.MODEL_TYPE__RESOURCE_ATTRIBUTE:
                return ((InternalEList<?>)getResourceAttribute()).basicRemove(otherEnd, msgs);
            case DePackage.MODEL_TYPE__SYSTEM_ACTION:
                return ((InternalEList<?>)getSystemAction()).basicRemove(otherEnd, msgs);
            case DePackage.MODEL_TYPE__RESOURCE:
                return ((InternalEList<?>)getResource()).basicRemove(otherEnd, msgs);
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
            case DePackage.MODEL_TYPE__META_MODEL:
                return getMetaModel();
            case DePackage.MODEL_TYPE__CHOICE_GROUP:
                if (coreType) return getChoiceGroup();
                return ((FeatureMap.Internal)getChoiceGroup()).getWrapper();
            case DePackage.MODEL_TYPE__MODEL_TEMPLATE:
                return getModelTemplate();
            case DePackage.MODEL_TYPE__CAPABILITY:
                return getCapability();
            case DePackage.MODEL_TYPE__CAPABILITY_CATEGORY:
                return getCapabilityCategory();
            case DePackage.MODEL_TYPE__PRIVILEGE:
                return getPrivilege();
            case DePackage.MODEL_TYPE__PRIVILEGE_CATEGORY:
                return getPrivilegeCategory();
            case DePackage.MODEL_TYPE__LOCATION:
                return getLocation();
            case DePackage.MODEL_TYPE__GROUP:
                return getGroup();
            case DePackage.MODEL_TYPE__ORGANIZATION:
                return getOrganization();
            case DePackage.MODEL_TYPE__RESOURCE_ATTRIBUTE:
                return getResourceAttribute();
            case DePackage.MODEL_TYPE__SYSTEM_ACTION:
                return getSystemAction();
            case DePackage.MODEL_TYPE__RESOURCE:
                return getResource();
            case DePackage.MODEL_TYPE__NAME:
                return getName();
            case DePackage.MODEL_TYPE__VERSION:
                return getVersion();
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
            case DePackage.MODEL_TYPE__META_MODEL:
                setMetaModel((MetaModel)newValue);
                return;
            case DePackage.MODEL_TYPE__CHOICE_GROUP:
                ((FeatureMap.Internal)getChoiceGroup()).set(newValue);
                return;
            case DePackage.MODEL_TYPE__MODEL_TEMPLATE:
                getModelTemplate().clear();
                getModelTemplate().addAll((Collection<? extends ModelTemplate>)newValue);
                return;
            case DePackage.MODEL_TYPE__CAPABILITY:
                getCapability().clear();
                getCapability().addAll((Collection<? extends Capability>)newValue);
                return;
            case DePackage.MODEL_TYPE__CAPABILITY_CATEGORY:
                getCapabilityCategory().clear();
                getCapabilityCategory().addAll((Collection<? extends CapabilityCategory>)newValue);
                return;
            case DePackage.MODEL_TYPE__PRIVILEGE:
                getPrivilege().clear();
                getPrivilege().addAll((Collection<? extends Privilege>)newValue);
                return;
            case DePackage.MODEL_TYPE__PRIVILEGE_CATEGORY:
                getPrivilegeCategory().clear();
                getPrivilegeCategory().addAll((Collection<? extends PrivilegeCategory>)newValue);
                return;
            case DePackage.MODEL_TYPE__LOCATION:
                getLocation().clear();
                getLocation().addAll((Collection<? extends Location>)newValue);
                return;
            case DePackage.MODEL_TYPE__GROUP:
                getGroup().clear();
                getGroup().addAll((Collection<? extends Group>)newValue);
                return;
            case DePackage.MODEL_TYPE__ORGANIZATION:
                getOrganization().clear();
                getOrganization().addAll((Collection<? extends Organization>)newValue);
                return;
            case DePackage.MODEL_TYPE__RESOURCE_ATTRIBUTE:
                getResourceAttribute().clear();
                getResourceAttribute().addAll((Collection<? extends AttributeType>)newValue);
                return;
            case DePackage.MODEL_TYPE__SYSTEM_ACTION:
                getSystemAction().clear();
                getSystemAction().addAll((Collection<? extends SystemAction>)newValue);
                return;
            case DePackage.MODEL_TYPE__RESOURCE:
                getResource().clear();
                getResource().addAll((Collection<? extends Resource>)newValue);
                return;
            case DePackage.MODEL_TYPE__NAME:
                setName((String)newValue);
                return;
            case DePackage.MODEL_TYPE__VERSION:
                setVersion((String)newValue);
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
            case DePackage.MODEL_TYPE__META_MODEL:
                setMetaModel((MetaModel)null);
                return;
            case DePackage.MODEL_TYPE__CHOICE_GROUP:
                getChoiceGroup().clear();
                return;
            case DePackage.MODEL_TYPE__MODEL_TEMPLATE:
                getModelTemplate().clear();
                return;
            case DePackage.MODEL_TYPE__CAPABILITY:
                getCapability().clear();
                return;
            case DePackage.MODEL_TYPE__CAPABILITY_CATEGORY:
                getCapabilityCategory().clear();
                return;
            case DePackage.MODEL_TYPE__PRIVILEGE:
                getPrivilege().clear();
                return;
            case DePackage.MODEL_TYPE__PRIVILEGE_CATEGORY:
                getPrivilegeCategory().clear();
                return;
            case DePackage.MODEL_TYPE__LOCATION:
                getLocation().clear();
                return;
            case DePackage.MODEL_TYPE__GROUP:
                getGroup().clear();
                return;
            case DePackage.MODEL_TYPE__ORGANIZATION:
                getOrganization().clear();
                return;
            case DePackage.MODEL_TYPE__RESOURCE_ATTRIBUTE:
                getResourceAttribute().clear();
                return;
            case DePackage.MODEL_TYPE__SYSTEM_ACTION:
                getSystemAction().clear();
                return;
            case DePackage.MODEL_TYPE__RESOURCE:
                getResource().clear();
                return;
            case DePackage.MODEL_TYPE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case DePackage.MODEL_TYPE__VERSION:
                setVersion(VERSION_EDEFAULT);
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
            case DePackage.MODEL_TYPE__META_MODEL:
                return metaModel != null;
            case DePackage.MODEL_TYPE__CHOICE_GROUP:
                return choiceGroup != null && !choiceGroup.isEmpty();
            case DePackage.MODEL_TYPE__MODEL_TEMPLATE:
                return !getModelTemplate().isEmpty();
            case DePackage.MODEL_TYPE__CAPABILITY:
                return !getCapability().isEmpty();
            case DePackage.MODEL_TYPE__CAPABILITY_CATEGORY:
                return !getCapabilityCategory().isEmpty();
            case DePackage.MODEL_TYPE__PRIVILEGE:
                return !getPrivilege().isEmpty();
            case DePackage.MODEL_TYPE__PRIVILEGE_CATEGORY:
                return !getPrivilegeCategory().isEmpty();
            case DePackage.MODEL_TYPE__LOCATION:
                return !getLocation().isEmpty();
            case DePackage.MODEL_TYPE__GROUP:
                return !getGroup().isEmpty();
            case DePackage.MODEL_TYPE__ORGANIZATION:
                return !getOrganization().isEmpty();
            case DePackage.MODEL_TYPE__RESOURCE_ATTRIBUTE:
                return !getResourceAttribute().isEmpty();
            case DePackage.MODEL_TYPE__SYSTEM_ACTION:
                return !getSystemAction().isEmpty();
            case DePackage.MODEL_TYPE__RESOURCE:
                return resource != null && !resource.isEmpty();
            case DePackage.MODEL_TYPE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case DePackage.MODEL_TYPE__VERSION:
                return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
        result.append(", name: ");
        result.append(name);
        result.append(", version: ");
        result.append(version);
        result.append(')');
        return result.toString();
    }

} //ModelTypeImpl
