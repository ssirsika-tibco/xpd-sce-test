/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Dynamic Org Unit</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link com.tibco.xpd.om.core.om.impl.DynamicOrgUnitImpl#getDynamicOrganization
 * <em>Dynamic Organization</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class DynamicOrgUnitImpl extends OrgUnitImpl implements DynamicOrgUnit {
    /**
     * The cached value of the '{@link #getDynamicOrganization()
     * <em>Dynamic Organization</em>}' reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getDynamicOrganization()
     * @generated
     * @ordered
     */
    protected DynamicOrgReference dynamicOrganization;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected DynamicOrgUnitImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.DYNAMIC_ORG_UNIT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public DynamicOrgReference getDynamicOrganization() {
        if (dynamicOrganization != null && dynamicOrganization.eIsProxy()) {
            InternalEObject oldDynamicOrganization =
                    (InternalEObject) dynamicOrganization;
            dynamicOrganization =
                    (DynamicOrgReference) eResolveProxy(oldDynamicOrganization);
            if (dynamicOrganization != oldDynamicOrganization) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.DYNAMIC_ORG_UNIT__DYNAMIC_ORGANIZATION,
                            oldDynamicOrganization, dynamicOrganization));
            }
        }
        return dynamicOrganization;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public DynamicOrgReference basicGetDynamicOrganization() {
        return dynamicOrganization;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setDynamicOrganization(
            DynamicOrgReference newDynamicOrganization) {
        DynamicOrgReference oldDynamicOrganization = dynamicOrganization;
        dynamicOrganization = newDynamicOrganization;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.DYNAMIC_ORG_UNIT__DYNAMIC_ORGANIZATION,
                    oldDynamicOrganization, dynamicOrganization));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case OMPackage.DYNAMIC_ORG_UNIT__DYNAMIC_ORGANIZATION:
            if (resolve)
                return getDynamicOrganization();
            return basicGetDynamicOrganization();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case OMPackage.DYNAMIC_ORG_UNIT__DYNAMIC_ORGANIZATION:
            setDynamicOrganization((DynamicOrgReference) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case OMPackage.DYNAMIC_ORG_UNIT__DYNAMIC_ORGANIZATION:
            setDynamicOrganization((DynamicOrgReference) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case OMPackage.DYNAMIC_ORG_UNIT__DYNAMIC_ORGANIZATION:
            return dynamicOrganization != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * @see com.tibco.xpd.om.core.om.impl.NamedElementImpl#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        /*
         * The name of a dynamic org unit is derived from the root org unit of
         * the referenced dynamic organization. If no root org unit is present
         * then the name of the dynamic organization will be taken.
         */
        OrgUnit refOrgUnit = getReferencedDynamicRootOrgUnit();
        if (refOrgUnit != null) {
            return refOrgUnit.getName();
        } else if (getDynamicOrganization() != null
                && getDynamicOrganization().getTo() != null) {
            return getDynamicOrganization().getTo().getName();
        }

        return super.getName();
    }

    /**
     * @see com.tibco.xpd.om.core.om.impl.NamedElementImpl#getDisplayName()
     * 
     * @return
     */
    @Override
    public String getDisplayName() {
        /*
         * The display name of a dynamic org unit is derived from the root org
         * unit of the referenced dynamic organization. If no root org unit is
         * present then the display name of the dynamic organization will be
         * taken.
         */
        OrgUnit refOrgUnit = getReferencedDynamicRootOrgUnit();
        if (refOrgUnit != null) {
            return refOrgUnit.getDisplayName();
        } else if (getDynamicOrganization() != null
                && getDynamicOrganization().getTo() != null) {
            return getDynamicOrganization().getTo().getDisplayName();
        }
        return super.getDisplayName();
    }

    /**
     * Get the root org unit of the dynamic organization referenced from the
     * given dynamic org unit.
     * 
     * @param unit
     * @return root org unit or <code>null</code> if one is not set or no
     *         dynamic org is referenced
     */
    private OrgUnit getReferencedDynamicRootOrgUnit() {
        if (getDynamicOrganization() != null
                && getDynamicOrganization().getTo() != null) {
            Organization dynOrg = getDynamicOrganization().getTo();
            EList<OrgUnit> subUnits = dynOrg.getSubUnits();
            if (!subUnits.isEmpty()) {
                return subUnits.get(0);
            }
        }

        return null;
    }

} // DynamicOrgUnitImpl
