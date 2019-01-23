/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.ResourceType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Resource Type</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.ResourceTypeImpl#isHumanResourceType <em>Human Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.ResourceTypeImpl#isDurableResourceType <em>Durable Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.ResourceTypeImpl#isConsumableResourceType <em>Consumable Resource Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceTypeImpl extends OrgElementTypeImpl implements
        ResourceType {
    /**
     * The default value of the '{@link #isHumanResourceType() <em>Human Resource Type</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #isHumanResourceType()
     * @generated
     * @ordered
     */
    protected static final boolean HUMAN_RESOURCE_TYPE_EDEFAULT = false;

    /**
     * The default value of the '{@link #isDurableResourceType() <em>Durable Resource Type</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #isDurableResourceType()
     * @generated
     * @ordered
     */
    protected static final boolean DURABLE_RESOURCE_TYPE_EDEFAULT = false;

    /**
     * The default value of the '{@link #isConsumableResourceType() <em>Consumable Resource Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isConsumableResourceType()
     * @generated
     * @ordered
     */
    protected static final boolean CONSUMABLE_RESOURCE_TYPE_EDEFAULT = false;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ResourceTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.RESOURCE_TYPE;
    }

    /**
     * <!-- begin-user-doc --> The resource type is referenced by OrgModel as
     * human resource type.<!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean isHumanResourceType() {
        return isSpecialResourceType(OMPackage.ORG_MODEL__HUMAN_RESOURCE_TYPE);
    }

    /**
     * <!-- begin-user-doc --> The resource type is referenced by OrgModel as
     * durable resource type.<!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean isDurableResourceType() {
        return isSpecialResourceType(OMPackage.ORG_MODEL__DURABLE_RESOURCE_TYPE);
    }

    /**
     * <!-- begin-user-doc --> The resource type is referenced by OrgModel as
     * consumable resource type.<!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean isConsumableResourceType() {
        return isSpecialResourceType(OMPackage.ORG_MODEL__CONSUMABLE_RESOURCE_TYPE);
    }

    /**
     * Checks if the resource type is one of special resource types.
     * 
     * @param orgModelSpecialTypeFeatureId
     * @return true if the resource type is the special resource type indicated
     *         by orgModelSpecialTypeFeatureId.
     */
    public boolean isSpecialResourceType(int orgModelSpecialTypeFeatureId) {
        ECrossReferenceAdapter crossReferenceAdapter =
                ECrossReferenceAdapter.getCrossReferenceAdapter(this);
        Collection<Setting> inverseReferences =
                crossReferenceAdapter.getInverseReferences(this, true);
        for (Setting ref : inverseReferences) {
            if (ref.getEObject() instanceof OrgModel
                    && ref.getEStructuralFeature().getFeatureID() == orgModelSpecialTypeFeatureId) {
                return true;
            }
        }
        return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case OMPackage.RESOURCE_TYPE__HUMAN_RESOURCE_TYPE:
            return isHumanResourceType() ? Boolean.TRUE : Boolean.FALSE;
        case OMPackage.RESOURCE_TYPE__DURABLE_RESOURCE_TYPE:
            return isDurableResourceType() ? Boolean.TRUE : Boolean.FALSE;
        case OMPackage.RESOURCE_TYPE__CONSUMABLE_RESOURCE_TYPE:
            return isConsumableResourceType() ? Boolean.TRUE : Boolean.FALSE;
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case OMPackage.RESOURCE_TYPE__HUMAN_RESOURCE_TYPE:
            return isHumanResourceType() != HUMAN_RESOURCE_TYPE_EDEFAULT;
        case OMPackage.RESOURCE_TYPE__DURABLE_RESOURCE_TYPE:
            return isDurableResourceType() != DURABLE_RESOURCE_TYPE_EDEFAULT;
        case OMPackage.RESOURCE_TYPE__CONSUMABLE_RESOURCE_TYPE:
            return isConsumableResourceType() != CONSUMABLE_RESOURCE_TYPE_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

} // ResourceTypeImpl
