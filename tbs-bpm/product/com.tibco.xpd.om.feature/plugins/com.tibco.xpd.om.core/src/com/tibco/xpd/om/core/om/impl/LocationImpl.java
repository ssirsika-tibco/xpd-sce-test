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

import com.tibco.xpd.om.core.om.Allocable;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.LocationType;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Location</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.LocationImpl#getPurpose <em>Purpose</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.LocationImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.LocationImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.LocationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.LocationImpl#getAttributeValues <em>Attribute Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.LocationImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.LocationImpl#getAllocationMethod <em>Allocation Method</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.LocationImpl#getLocationType <em>Location Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.LocationImpl#getLocale <em>Locale</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.LocationImpl#getTimeZone <em>Time Zone</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocationImpl extends NamedElementImpl implements Location {
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
     * The cached value of the '{@link #getAttributeValues()
     * <em>Attribute Values</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getAttributeValues()
     * @generated
     * @ordered
     */
    protected EList<AttributeValue> attributeValues;

    /**
     * The default value of the '{@link #getAllocationMethod() <em>Allocation Method</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getAllocationMethod()
     * @generated
     * @ordered
     */
    protected static final String ALLOCATION_METHOD_EDEFAULT = "ANY"; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getAllocationMethod() <em>Allocation Method</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getAllocationMethod()
     * @generated
     * @ordered
     */
    protected String allocationMethod = ALLOCATION_METHOD_EDEFAULT;

    /**
     * This is true if the Allocation Method attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean allocationMethodESet;

    /**
     * The cached value of the '{@link #getLocationType() <em>Location Type</em>}' reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getLocationType()
     * @generated
     * @ordered
     */
    protected LocationType locationType;

    /**
     * The default value of the '{@link #getLocale() <em>Locale</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getLocale()
     * @generated
     * @ordered
     */
    protected static final String LOCALE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLocale() <em>Locale</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getLocale()
     * @generated
     * @ordered
     */
    protected String locale = LOCALE_EDEFAULT;

    /**
     * The default value of the '{@link #getTimeZone() <em>Time Zone</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getTimeZone()
     * @generated
     * @ordered
     */
    protected static final String TIME_ZONE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTimeZone() <em>Time Zone</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getTimeZone()
     * @generated
     * @ordered
     */
    protected String timeZone = TIME_ZONE_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected LocationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.LOCATION;
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
                    OMPackage.LOCATION__PURPOSE, oldPurpose, purpose));
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
                    OMPackage.LOCATION__START_DATE, oldStartDate, startDate));
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
                    OMPackage.LOCATION__END_DATE, oldEndDate, endDate));
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
                    OMPackage.LOCATION__DESCRIPTION, oldDescription,
                    description));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<AttributeValue> getAttributeValues() {
        if (attributeValues == null) {
            attributeValues =
                    new EObjectContainmentEList<AttributeValue>(
                            AttributeValue.class, this,
                            OMPackage.LOCATION__ATTRIBUTE_VALUES);
        }
        return attributeValues;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getAllocationMethod() {
        return allocationMethod;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setAllocationMethod(String newAllocationMethod) {
        String oldAllocationMethod = allocationMethod;
        allocationMethod = newAllocationMethod;
        boolean oldAllocationMethodESet = allocationMethodESet;
        allocationMethodESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.LOCATION__ALLOCATION_METHOD, oldAllocationMethod,
                    allocationMethod, !oldAllocationMethodESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void unsetAllocationMethod() {
        String oldAllocationMethod = allocationMethod;
        boolean oldAllocationMethodESet = allocationMethodESet;
        allocationMethod = ALLOCATION_METHOD_EDEFAULT;
        allocationMethodESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    OMPackage.LOCATION__ALLOCATION_METHOD, oldAllocationMethod,
                    ALLOCATION_METHOD_EDEFAULT, oldAllocationMethodESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAllocationMethod() {
        return allocationMethodESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public LocationType getLocationType() {
        if (locationType != null && locationType.eIsProxy()) {
            InternalEObject oldLocationType = (InternalEObject) locationType;
            locationType = (LocationType) eResolveProxy(oldLocationType);
            if (locationType != oldLocationType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.LOCATION__LOCATION_TYPE, oldLocationType,
                            locationType));
            }
        }
        return locationType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public LocationType basicGetLocationType() {
        return locationType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setLocationType(LocationType newLocationType) {
        LocationType oldLocationType = locationType;
        locationType = newLocationType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.LOCATION__LOCATION_TYPE, oldLocationType,
                    locationType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public OrgElementType getType() {
        return getLocationType();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public void setType(OrgElementType newType) {
        setLocationType((LocationType) newType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getLocale() {
        return locale;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setLocale(String newLocale) {
        String oldLocale = locale;
        locale = newLocale;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.LOCATION__LOCALE, oldLocale, locale));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setTimeZone(String newTimeZone) {
        String oldTimeZone = timeZone;
        timeZone = newTimeZone;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.LOCATION__TIME_ZONE, oldTimeZone, timeZone));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.LOCATION__ATTRIBUTE_VALUES:
            return ((InternalEList<?>) getAttributeValues())
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
        case OMPackage.LOCATION__PURPOSE:
            return getPurpose();
        case OMPackage.LOCATION__START_DATE:
            return getStartDate();
        case OMPackage.LOCATION__END_DATE:
            return getEndDate();
        case OMPackage.LOCATION__DESCRIPTION:
            return getDescription();
        case OMPackage.LOCATION__ATTRIBUTE_VALUES:
            return getAttributeValues();
        case OMPackage.LOCATION__TYPE:
            return getType();
        case OMPackage.LOCATION__ALLOCATION_METHOD:
            return getAllocationMethod();
        case OMPackage.LOCATION__LOCATION_TYPE:
            if (resolve)
                return getLocationType();
            return basicGetLocationType();
        case OMPackage.LOCATION__LOCALE:
            return getLocale();
        case OMPackage.LOCATION__TIME_ZONE:
            return getTimeZone();
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
        case OMPackage.LOCATION__PURPOSE:
            setPurpose((String) newValue);
            return;
        case OMPackage.LOCATION__START_DATE:
            setStartDate((Date) newValue);
            return;
        case OMPackage.LOCATION__END_DATE:
            setEndDate((Date) newValue);
            return;
        case OMPackage.LOCATION__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case OMPackage.LOCATION__ATTRIBUTE_VALUES:
            getAttributeValues().clear();
            getAttributeValues()
                    .addAll((Collection<? extends AttributeValue>) newValue);
            return;
        case OMPackage.LOCATION__TYPE:
            setType((OrgElementType) newValue);
            return;
        case OMPackage.LOCATION__ALLOCATION_METHOD:
            setAllocationMethod((String) newValue);
            return;
        case OMPackage.LOCATION__LOCATION_TYPE:
            setLocationType((LocationType) newValue);
            return;
        case OMPackage.LOCATION__LOCALE:
            setLocale((String) newValue);
            return;
        case OMPackage.LOCATION__TIME_ZONE:
            setTimeZone((String) newValue);
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
        case OMPackage.LOCATION__PURPOSE:
            setPurpose(PURPOSE_EDEFAULT);
            return;
        case OMPackage.LOCATION__START_DATE:
            setStartDate(START_DATE_EDEFAULT);
            return;
        case OMPackage.LOCATION__END_DATE:
            setEndDate(END_DATE_EDEFAULT);
            return;
        case OMPackage.LOCATION__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case OMPackage.LOCATION__ATTRIBUTE_VALUES:
            getAttributeValues().clear();
            return;
        case OMPackage.LOCATION__TYPE:
            setType((OrgElementType) null);
            return;
        case OMPackage.LOCATION__ALLOCATION_METHOD:
            unsetAllocationMethod();
            return;
        case OMPackage.LOCATION__LOCATION_TYPE:
            setLocationType((LocationType) null);
            return;
        case OMPackage.LOCATION__LOCALE:
            setLocale(LOCALE_EDEFAULT);
            return;
        case OMPackage.LOCATION__TIME_ZONE:
            setTimeZone(TIME_ZONE_EDEFAULT);
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
        case OMPackage.LOCATION__PURPOSE:
            return PURPOSE_EDEFAULT == null ? purpose != null
                    : !PURPOSE_EDEFAULT.equals(purpose);
        case OMPackage.LOCATION__START_DATE:
            return START_DATE_EDEFAULT == null ? startDate != null
                    : !START_DATE_EDEFAULT.equals(startDate);
        case OMPackage.LOCATION__END_DATE:
            return END_DATE_EDEFAULT == null ? endDate != null
                    : !END_DATE_EDEFAULT.equals(endDate);
        case OMPackage.LOCATION__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case OMPackage.LOCATION__ATTRIBUTE_VALUES:
            return attributeValues != null && !attributeValues.isEmpty();
        case OMPackage.LOCATION__TYPE:
            return getType() != null;
        case OMPackage.LOCATION__ALLOCATION_METHOD:
            return isSetAllocationMethod();
        case OMPackage.LOCATION__LOCATION_TYPE:
            return locationType != null;
        case OMPackage.LOCATION__LOCALE:
            return LOCALE_EDEFAULT == null ? locale != null : !LOCALE_EDEFAULT
                    .equals(locale);
        case OMPackage.LOCATION__TIME_ZONE:
            return TIME_ZONE_EDEFAULT == null ? timeZone != null
                    : !TIME_ZONE_EDEFAULT.equals(timeZone);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == Allocable.class) {
            switch (derivedFeatureID) {
            case OMPackage.LOCATION__ALLOCATION_METHOD:
                return OMPackage.ALLOCABLE__ALLOCATION_METHOD;
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
        if (baseClass == Allocable.class) {
            switch (baseFeatureID) {
            case OMPackage.ALLOCABLE__ALLOCATION_METHOD:
                return OMPackage.LOCATION__ALLOCATION_METHOD;
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
        result.append(", allocationMethod: "); //$NON-NLS-1$
        if (allocationMethodESet)
            result.append(allocationMethod);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", locale: "); //$NON-NLS-1$
        result.append(locale);
        result.append(", timeZone: "); //$NON-NLS-1$
        result.append(timeZone);
        result.append(')');
        return result.toString();
    }

} // LocationImpl
