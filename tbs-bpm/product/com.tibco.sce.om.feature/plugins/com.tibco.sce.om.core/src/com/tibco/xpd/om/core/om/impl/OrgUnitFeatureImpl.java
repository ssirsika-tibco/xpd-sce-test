/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;
import com.tibco.xpd.om.core.om.OrgUnitType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Org Unit Feature</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitFeatureImpl#getPurpose <em>Purpose</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitFeatureImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitFeatureImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitFeatureImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitFeatureImpl#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitFeatureImpl#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitFeatureImpl#getFeatureType <em>Feature Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitFeatureImpl#getContextRelationshipType <em>Context Relationship Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrgUnitFeatureImpl extends NamedElementImpl implements
        OrgUnitFeature {
    /**
     * The default value of the '{@link #getPurpose() <em>Purpose</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPurpose()
     * @generated
     * @ordered
     */
    protected static final String PURPOSE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPurpose() <em>Purpose</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getPurpose()
     * @generated
     * @ordered
     */
    protected String purpose = PURPOSE_EDEFAULT;

    /**
     * The default value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected static final Date START_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected Date startDate = START_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected static final Date END_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected Date endDate = END_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The default value of the '{@link #getLowerBound() <em>Lower Bound</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getLowerBound()
     * @generated
     * @ordered
     */
    protected static final int LOWER_BOUND_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getLowerBound() <em>Lower Bound</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getLowerBound()
     * @generated
     * @ordered
     */
    protected int lowerBound = LOWER_BOUND_EDEFAULT;

    /**
     * The default value of the '{@link #getUpperBound() <em>Upper Bound</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getUpperBound()
     * @generated
     * @ordered
     */
    protected static final int UPPER_BOUND_EDEFAULT = 1;

    /**
     * The cached value of the '{@link #getUpperBound() <em>Upper Bound</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getUpperBound()
     * @generated
     * @ordered
     */
    protected int upperBound = UPPER_BOUND_EDEFAULT;

    /**
     * The cached value of the '{@link #getFeatureType() <em>Feature Type</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getFeatureType()
     * @generated
     * @ordered
     */
    protected OrgUnitType featureType;

    /**
     * The cached value of the '{@link #getContextRelationshipType() <em>Context Relationship Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getContextRelationshipType()
     * @generated
     * @ordered
     */
    protected OrgUnitRelationshipType contextRelationshipType;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected OrgUnitFeatureImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.ORG_UNIT_FEATURE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setPurpose(String newPurpose) {
        String oldPurpose = purpose;
        purpose = newPurpose;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT_FEATURE__PURPOSE, oldPurpose, purpose));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setStartDate(Date newStartDate) {
        Date oldStartDate = startDate;
        startDate = newStartDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT_FEATURE__START_DATE, oldStartDate,
                    startDate));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setEndDate(Date newEndDate) {
        Date oldEndDate = endDate;
        endDate = newEndDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT_FEATURE__END_DATE, oldEndDate, endDate));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT_FEATURE__DESCRIPTION, oldDescription,
                    description));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public int getLowerBound() {
        return lowerBound;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setLowerBound(int newLowerBound) {
        int oldLowerBound = lowerBound;
        lowerBound = newLowerBound;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT_FEATURE__LOWER_BOUND, oldLowerBound,
                    lowerBound));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public int getUpperBound() {
        return upperBound;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setUpperBound(int newUpperBound) {
        int oldUpperBound = upperBound;
        upperBound = newUpperBound;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT_FEATURE__UPPER_BOUND, oldUpperBound,
                    upperBound));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrgUnitType getFeatureType() {
        if (featureType != null && featureType.eIsProxy()) {
            InternalEObject oldFeatureType = (InternalEObject) featureType;
            featureType = (OrgUnitType) eResolveProxy(oldFeatureType);
            if (featureType != oldFeatureType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.ORG_UNIT_FEATURE__FEATURE_TYPE,
                            oldFeatureType, featureType));
            }
        }
        return featureType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrgUnitType basicGetFeatureType() {
        return featureType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setFeatureType(OrgUnitType newFeatureType) {
        OrgUnitType oldFeatureType = featureType;
        featureType = newFeatureType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT_FEATURE__FEATURE_TYPE, oldFeatureType,
                    featureType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public OrgElementType getType() {
        return getFeatureType();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public void setType(OrgElementType newType) {
        setFeatureType((OrgUnitType) newType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrgUnitRelationshipType getContextRelationshipType() {
        if (contextRelationshipType != null
                && contextRelationshipType.eIsProxy()) {
            InternalEObject oldContextRelationshipType =
                    (InternalEObject) contextRelationshipType;
            contextRelationshipType =
                    (OrgUnitRelationshipType) eResolveProxy(oldContextRelationshipType);
            if (contextRelationshipType != oldContextRelationshipType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(
                            this,
                            Notification.RESOLVE,
                            OMPackage.ORG_UNIT_FEATURE__CONTEXT_RELATIONSHIP_TYPE,
                            oldContextRelationshipType, contextRelationshipType));
            }
        }
        return contextRelationshipType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrgUnitRelationshipType basicGetContextRelationshipType() {
        return contextRelationshipType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setContextRelationshipType(
            OrgUnitRelationshipType newContextRelationshipType) {
        OrgUnitRelationshipType oldContextRelationshipType =
                contextRelationshipType;
        contextRelationshipType = newContextRelationshipType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT_FEATURE__CONTEXT_RELATIONSHIP_TYPE,
                    oldContextRelationshipType, contextRelationshipType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case OMPackage.ORG_UNIT_FEATURE__PURPOSE:
            return getPurpose();
        case OMPackage.ORG_UNIT_FEATURE__START_DATE:
            return getStartDate();
        case OMPackage.ORG_UNIT_FEATURE__END_DATE:
            return getEndDate();
        case OMPackage.ORG_UNIT_FEATURE__DESCRIPTION:
            return getDescription();
        case OMPackage.ORG_UNIT_FEATURE__LOWER_BOUND:
            return new Integer(getLowerBound());
        case OMPackage.ORG_UNIT_FEATURE__UPPER_BOUND:
            return new Integer(getUpperBound());
        case OMPackage.ORG_UNIT_FEATURE__FEATURE_TYPE:
            if (resolve)
                return getFeatureType();
            return basicGetFeatureType();
        case OMPackage.ORG_UNIT_FEATURE__CONTEXT_RELATIONSHIP_TYPE:
            if (resolve)
                return getContextRelationshipType();
            return basicGetContextRelationshipType();
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
        case OMPackage.ORG_UNIT_FEATURE__PURPOSE:
            setPurpose((String) newValue);
            return;
        case OMPackage.ORG_UNIT_FEATURE__START_DATE:
            setStartDate((Date) newValue);
            return;
        case OMPackage.ORG_UNIT_FEATURE__END_DATE:
            setEndDate((Date) newValue);
            return;
        case OMPackage.ORG_UNIT_FEATURE__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case OMPackage.ORG_UNIT_FEATURE__LOWER_BOUND:
            setLowerBound(((Integer) newValue).intValue());
            return;
        case OMPackage.ORG_UNIT_FEATURE__UPPER_BOUND:
            setUpperBound(((Integer) newValue).intValue());
            return;
        case OMPackage.ORG_UNIT_FEATURE__FEATURE_TYPE:
            setFeatureType((OrgUnitType) newValue);
            return;
        case OMPackage.ORG_UNIT_FEATURE__CONTEXT_RELATIONSHIP_TYPE:
            setContextRelationshipType((OrgUnitRelationshipType) newValue);
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
        case OMPackage.ORG_UNIT_FEATURE__PURPOSE:
            setPurpose(PURPOSE_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT_FEATURE__START_DATE:
            setStartDate(START_DATE_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT_FEATURE__END_DATE:
            setEndDate(END_DATE_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT_FEATURE__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT_FEATURE__LOWER_BOUND:
            setLowerBound(LOWER_BOUND_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT_FEATURE__UPPER_BOUND:
            setUpperBound(UPPER_BOUND_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT_FEATURE__FEATURE_TYPE:
            setFeatureType((OrgUnitType) null);
            return;
        case OMPackage.ORG_UNIT_FEATURE__CONTEXT_RELATIONSHIP_TYPE:
            setContextRelationshipType((OrgUnitRelationshipType) null);
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
        case OMPackage.ORG_UNIT_FEATURE__PURPOSE:
            return PURPOSE_EDEFAULT == null ? purpose != null
                    : !PURPOSE_EDEFAULT.equals(purpose);
        case OMPackage.ORG_UNIT_FEATURE__START_DATE:
            return START_DATE_EDEFAULT == null ? startDate != null
                    : !START_DATE_EDEFAULT.equals(startDate);
        case OMPackage.ORG_UNIT_FEATURE__END_DATE:
            return END_DATE_EDEFAULT == null ? endDate != null
                    : !END_DATE_EDEFAULT.equals(endDate);
        case OMPackage.ORG_UNIT_FEATURE__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case OMPackage.ORG_UNIT_FEATURE__LOWER_BOUND:
            return lowerBound != LOWER_BOUND_EDEFAULT;
        case OMPackage.ORG_UNIT_FEATURE__UPPER_BOUND:
            return upperBound != UPPER_BOUND_EDEFAULT;
        case OMPackage.ORG_UNIT_FEATURE__FEATURE_TYPE:
            return featureType != null;
        case OMPackage.ORG_UNIT_FEATURE__CONTEXT_RELATIONSHIP_TYPE:
            return contextRelationshipType != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == OrgElement.class) {
            switch (derivedFeatureID) {
            case OMPackage.ORG_UNIT_FEATURE__PURPOSE:
                return OMPackage.ORG_ELEMENT__PURPOSE;
            case OMPackage.ORG_UNIT_FEATURE__START_DATE:
                return OMPackage.ORG_ELEMENT__START_DATE;
            case OMPackage.ORG_UNIT_FEATURE__END_DATE:
                return OMPackage.ORG_ELEMENT__END_DATE;
            case OMPackage.ORG_UNIT_FEATURE__DESCRIPTION:
                return OMPackage.ORG_ELEMENT__DESCRIPTION;
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
        if (baseClass == OrgElement.class) {
            switch (baseFeatureID) {
            case OMPackage.ORG_ELEMENT__PURPOSE:
                return OMPackage.ORG_UNIT_FEATURE__PURPOSE;
            case OMPackage.ORG_ELEMENT__START_DATE:
                return OMPackage.ORG_UNIT_FEATURE__START_DATE;
            case OMPackage.ORG_ELEMENT__END_DATE:
                return OMPackage.ORG_UNIT_FEATURE__END_DATE;
            case OMPackage.ORG_ELEMENT__DESCRIPTION:
                return OMPackage.ORG_UNIT_FEATURE__DESCRIPTION;
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
        result.append(" (purpose: "); //$NON-NLS-1$
        result.append(purpose);
        result.append(", startDate: "); //$NON-NLS-1$
        result.append(startDate);
        result.append(", endDate: "); //$NON-NLS-1$
        result.append(endDate);
        result.append(", description: "); //$NON-NLS-1$
        result.append(description);
        result.append(", lowerBound: "); //$NON-NLS-1$
        result.append(lowerBound);
        result.append(", upperBound: "); //$NON-NLS-1$
        result.append(upperBound);
        result.append(')');
        return result.toString();
    }

} // OrgUnitFeatureImpl
