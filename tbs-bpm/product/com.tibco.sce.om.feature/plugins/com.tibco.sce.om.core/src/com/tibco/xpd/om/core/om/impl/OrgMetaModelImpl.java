/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.om.core.om.LocationType;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.core.om.ResourceType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Org Meta Model</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgMetaModelImpl#getLocationTypes <em>Location Types</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgMetaModelImpl#getOrgUnitRelationshipTypes <em>Org Unit Relationship Types</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgMetaModelImpl#getOrganizationTypes <em>Organization Types</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgMetaModelImpl#getOrgUnitTypes <em>Org Unit Types</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgMetaModelImpl#getPositionTypes <em>Position Types</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgMetaModelImpl#getResourceTypes <em>Resource Types</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgMetaModelImpl#isEmbedded <em>Embedded</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrgMetaModelImpl extends BaseOrgModelImpl implements OrgMetaModel {
    /**
     * The cached value of the '{@link #getLocationTypes() <em>Location Types</em>}' containment reference list.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getLocationTypes()
     * @generated
     * @ordered
     */
    protected EList<LocationType> locationTypes;

    /**
     * The cached value of the '{@link #getOrgUnitRelationshipTypes()
     * <em>Org Unit Relationship Types</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getOrgUnitRelationshipTypes()
     * @generated
     * @ordered
     */
    protected EList<OrgUnitRelationshipType> orgUnitRelationshipTypes;

    /**
     * The cached value of the '{@link #getOrganizationTypes()
     * <em>Organization Types</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getOrganizationTypes()
     * @generated
     * @ordered
     */
    protected EList<OrganizationType> organizationTypes;

    /**
     * The cached value of the '{@link #getOrgUnitTypes() <em>Org Unit Types</em>}' containment reference list.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getOrgUnitTypes()
     * @generated
     * @ordered
     */
    protected EList<OrgUnitType> orgUnitTypes;

    /**
     * The cached value of the '{@link #getPositionTypes() <em>Position Types</em>}' containment reference list.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getPositionTypes()
     * @generated
     * @ordered
     */
    protected EList<PositionType> positionTypes;

    /**
     * The cached value of the '{@link #getResourceTypes() <em>Resource Types</em>}' containment reference list.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getResourceTypes()
     * @generated
     * @ordered
     */
    protected EList<ResourceType> resourceTypes;

    /**
     * The default value of the '{@link #isEmbedded() <em>Embedded</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isEmbedded()
     * @generated
     * @ordered
     */
    protected static final boolean EMBEDDED_EDEFAULT = false;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected OrgMetaModelImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.ORG_META_MODEL;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<LocationType> getLocationTypes() {
        if (locationTypes == null) {
            locationTypes =
                    new EObjectContainmentEList<LocationType>(
                            LocationType.class, this,
                            OMPackage.ORG_META_MODEL__LOCATION_TYPES);
        }
        return locationTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<OrgUnitRelationshipType> getOrgUnitRelationshipTypes() {
        if (orgUnitRelationshipTypes == null) {
            orgUnitRelationshipTypes =
                    new EObjectContainmentEList<OrgUnitRelationshipType>(
                            OrgUnitRelationshipType.class,
                            this,
                            OMPackage.ORG_META_MODEL__ORG_UNIT_RELATIONSHIP_TYPES);
        }
        return orgUnitRelationshipTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<OrganizationType> getOrganizationTypes() {
        if (organizationTypes == null) {
            organizationTypes =
                    new EObjectContainmentEList<OrganizationType>(
                            OrganizationType.class, this,
                            OMPackage.ORG_META_MODEL__ORGANIZATION_TYPES);
        }
        return organizationTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<OrgUnitType> getOrgUnitTypes() {
        if (orgUnitTypes == null) {
            orgUnitTypes =
                    new EObjectContainmentEList<OrgUnitType>(OrgUnitType.class,
                            this, OMPackage.ORG_META_MODEL__ORG_UNIT_TYPES);
        }
        return orgUnitTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<PositionType> getPositionTypes() {
        if (positionTypes == null) {
            positionTypes =
                    new EObjectContainmentEList<PositionType>(
                            PositionType.class, this,
                            OMPackage.ORG_META_MODEL__POSITION_TYPES);
        }
        return positionTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<ResourceType> getResourceTypes() {
        if (resourceTypes == null) {
            resourceTypes =
                    new EObjectContainmentEList<ResourceType>(
                            ResourceType.class, this,
                            OMPackage.ORG_META_MODEL__RESOURCE_TYPES);
        }
        return resourceTypes;
    }

    /**
     * <!-- begin-user-doc --> Returns true if the meta-model is embedded
     * (contained inside OrgModel) <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean isEmbedded() {
        return eContainer() instanceof OrgModel;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.ORG_META_MODEL__LOCATION_TYPES:
            return ((InternalEList<?>) getLocationTypes())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_META_MODEL__ORG_UNIT_RELATIONSHIP_TYPES:
            return ((InternalEList<?>) getOrgUnitRelationshipTypes())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_META_MODEL__ORGANIZATION_TYPES:
            return ((InternalEList<?>) getOrganizationTypes())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_META_MODEL__ORG_UNIT_TYPES:
            return ((InternalEList<?>) getOrgUnitTypes()).basicRemove(otherEnd,
                    msgs);
        case OMPackage.ORG_META_MODEL__POSITION_TYPES:
            return ((InternalEList<?>) getPositionTypes())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_META_MODEL__RESOURCE_TYPES:
            return ((InternalEList<?>) getResourceTypes())
                    .basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case OMPackage.ORG_META_MODEL__LOCATION_TYPES:
            return getLocationTypes();
        case OMPackage.ORG_META_MODEL__ORG_UNIT_RELATIONSHIP_TYPES:
            return getOrgUnitRelationshipTypes();
        case OMPackage.ORG_META_MODEL__ORGANIZATION_TYPES:
            return getOrganizationTypes();
        case OMPackage.ORG_META_MODEL__ORG_UNIT_TYPES:
            return getOrgUnitTypes();
        case OMPackage.ORG_META_MODEL__POSITION_TYPES:
            return getPositionTypes();
        case OMPackage.ORG_META_MODEL__RESOURCE_TYPES:
            return getResourceTypes();
        case OMPackage.ORG_META_MODEL__EMBEDDED:
            return isEmbedded() ? Boolean.TRUE : Boolean.FALSE;
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
        case OMPackage.ORG_META_MODEL__LOCATION_TYPES:
            getLocationTypes().clear();
            getLocationTypes()
                    .addAll((Collection<? extends LocationType>) newValue);
            return;
        case OMPackage.ORG_META_MODEL__ORG_UNIT_RELATIONSHIP_TYPES:
            getOrgUnitRelationshipTypes().clear();
            getOrgUnitRelationshipTypes()
                    .addAll((Collection<? extends OrgUnitRelationshipType>) newValue);
            return;
        case OMPackage.ORG_META_MODEL__ORGANIZATION_TYPES:
            getOrganizationTypes().clear();
            getOrganizationTypes()
                    .addAll((Collection<? extends OrganizationType>) newValue);
            return;
        case OMPackage.ORG_META_MODEL__ORG_UNIT_TYPES:
            getOrgUnitTypes().clear();
            getOrgUnitTypes()
                    .addAll((Collection<? extends OrgUnitType>) newValue);
            return;
        case OMPackage.ORG_META_MODEL__POSITION_TYPES:
            getPositionTypes().clear();
            getPositionTypes()
                    .addAll((Collection<? extends PositionType>) newValue);
            return;
        case OMPackage.ORG_META_MODEL__RESOURCE_TYPES:
            getResourceTypes().clear();
            getResourceTypes()
                    .addAll((Collection<? extends ResourceType>) newValue);
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
        case OMPackage.ORG_META_MODEL__LOCATION_TYPES:
            getLocationTypes().clear();
            return;
        case OMPackage.ORG_META_MODEL__ORG_UNIT_RELATIONSHIP_TYPES:
            getOrgUnitRelationshipTypes().clear();
            return;
        case OMPackage.ORG_META_MODEL__ORGANIZATION_TYPES:
            getOrganizationTypes().clear();
            return;
        case OMPackage.ORG_META_MODEL__ORG_UNIT_TYPES:
            getOrgUnitTypes().clear();
            return;
        case OMPackage.ORG_META_MODEL__POSITION_TYPES:
            getPositionTypes().clear();
            return;
        case OMPackage.ORG_META_MODEL__RESOURCE_TYPES:
            getResourceTypes().clear();
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
        case OMPackage.ORG_META_MODEL__LOCATION_TYPES:
            return locationTypes != null && !locationTypes.isEmpty();
        case OMPackage.ORG_META_MODEL__ORG_UNIT_RELATIONSHIP_TYPES:
            return orgUnitRelationshipTypes != null
                    && !orgUnitRelationshipTypes.isEmpty();
        case OMPackage.ORG_META_MODEL__ORGANIZATION_TYPES:
            return organizationTypes != null && !organizationTypes.isEmpty();
        case OMPackage.ORG_META_MODEL__ORG_UNIT_TYPES:
            return orgUnitTypes != null && !orgUnitTypes.isEmpty();
        case OMPackage.ORG_META_MODEL__POSITION_TYPES:
            return positionTypes != null && !positionTypes.isEmpty();
        case OMPackage.ORG_META_MODEL__RESOURCE_TYPES:
            return resourceTypes != null && !resourceTypes.isEmpty();
        case OMPackage.ORG_META_MODEL__EMBEDDED:
            return isEmbedded() != EMBEDDED_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

} // OrgMetaModelImpl
