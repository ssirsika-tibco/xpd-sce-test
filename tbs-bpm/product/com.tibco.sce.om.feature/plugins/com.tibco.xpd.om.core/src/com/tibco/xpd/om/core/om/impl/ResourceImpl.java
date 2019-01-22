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
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.core.om.ResourceType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Resource</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.ResourceImpl#getPurpose <em>Purpose</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.ResourceImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.ResourceImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.ResourceImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.ResourceImpl#getAttributeValues <em>Attribute Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.ResourceImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.ResourceImpl#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.ResourceImpl#getDn <em>Dn</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceImpl extends NamedElementImpl implements Resource {
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
     * The cached value of the '{@link #getResourceType() <em>Resource Type</em>}' reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getResourceType()
     * @generated
     * @ordered
     */
    protected ResourceType resourceType;

    /**
     * The default value of the '{@link #getDn() <em>Dn</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getDn()
     * @generated
     * @ordered
     */
    protected static final String DN_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDn() <em>Dn</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getDn()
     * @generated
     * @ordered
     */
    protected String dn = DN_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ResourceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.RESOURCE;
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
                    OMPackage.RESOURCE__PURPOSE, oldPurpose, purpose));
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
                    OMPackage.RESOURCE__START_DATE, oldStartDate, startDate));
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
                    OMPackage.RESOURCE__END_DATE, oldEndDate, endDate));
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
                    OMPackage.RESOURCE__DESCRIPTION, oldDescription,
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
                            OMPackage.RESOURCE__ATTRIBUTE_VALUES);
        }
        return attributeValues;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public OrgElementType getType() {
        return getResourceType();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public void setType(OrgElementType newType) {
        setResourceType((ResourceType) newType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ResourceType getResourceType() {
        if (resourceType != null && resourceType.eIsProxy()) {
            InternalEObject oldResourceType = (InternalEObject) resourceType;
            resourceType = (ResourceType) eResolveProxy(oldResourceType);
            if (resourceType != oldResourceType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.RESOURCE__RESOURCE_TYPE, oldResourceType,
                            resourceType));
            }
        }
        return resourceType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ResourceType basicGetResourceType() {
        return resourceType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setResourceType(ResourceType newResourceType) {
        ResourceType oldResourceType = resourceType;
        resourceType = newResourceType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.RESOURCE__RESOURCE_TYPE, oldResourceType,
                    resourceType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getDn() {
        return dn;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDn(String newDn) {
        String oldDn = dn;
        dn = newDn;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.RESOURCE__DN, oldDn, dn));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.RESOURCE__ATTRIBUTE_VALUES:
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
        case OMPackage.RESOURCE__PURPOSE:
            return getPurpose();
        case OMPackage.RESOURCE__START_DATE:
            return getStartDate();
        case OMPackage.RESOURCE__END_DATE:
            return getEndDate();
        case OMPackage.RESOURCE__DESCRIPTION:
            return getDescription();
        case OMPackage.RESOURCE__ATTRIBUTE_VALUES:
            return getAttributeValues();
        case OMPackage.RESOURCE__TYPE:
            return getType();
        case OMPackage.RESOURCE__RESOURCE_TYPE:
            if (resolve)
                return getResourceType();
            return basicGetResourceType();
        case OMPackage.RESOURCE__DN:
            return getDn();
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
        case OMPackage.RESOURCE__PURPOSE:
            setPurpose((String) newValue);
            return;
        case OMPackage.RESOURCE__START_DATE:
            setStartDate((Date) newValue);
            return;
        case OMPackage.RESOURCE__END_DATE:
            setEndDate((Date) newValue);
            return;
        case OMPackage.RESOURCE__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case OMPackage.RESOURCE__ATTRIBUTE_VALUES:
            getAttributeValues().clear();
            getAttributeValues()
                    .addAll((Collection<? extends AttributeValue>) newValue);
            return;
        case OMPackage.RESOURCE__TYPE:
            setType((OrgElementType) newValue);
            return;
        case OMPackage.RESOURCE__RESOURCE_TYPE:
            setResourceType((ResourceType) newValue);
            return;
        case OMPackage.RESOURCE__DN:
            setDn((String) newValue);
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
        case OMPackage.RESOURCE__PURPOSE:
            setPurpose(PURPOSE_EDEFAULT);
            return;
        case OMPackage.RESOURCE__START_DATE:
            setStartDate(START_DATE_EDEFAULT);
            return;
        case OMPackage.RESOURCE__END_DATE:
            setEndDate(END_DATE_EDEFAULT);
            return;
        case OMPackage.RESOURCE__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case OMPackage.RESOURCE__ATTRIBUTE_VALUES:
            getAttributeValues().clear();
            return;
        case OMPackage.RESOURCE__TYPE:
            setType((OrgElementType) null);
            return;
        case OMPackage.RESOURCE__RESOURCE_TYPE:
            setResourceType((ResourceType) null);
            return;
        case OMPackage.RESOURCE__DN:
            setDn(DN_EDEFAULT);
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
        case OMPackage.RESOURCE__PURPOSE:
            return PURPOSE_EDEFAULT == null ? purpose != null
                    : !PURPOSE_EDEFAULT.equals(purpose);
        case OMPackage.RESOURCE__START_DATE:
            return START_DATE_EDEFAULT == null ? startDate != null
                    : !START_DATE_EDEFAULT.equals(startDate);
        case OMPackage.RESOURCE__END_DATE:
            return END_DATE_EDEFAULT == null ? endDate != null
                    : !END_DATE_EDEFAULT.equals(endDate);
        case OMPackage.RESOURCE__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case OMPackage.RESOURCE__ATTRIBUTE_VALUES:
            return attributeValues != null && !attributeValues.isEmpty();
        case OMPackage.RESOURCE__TYPE:
            return getType() != null;
        case OMPackage.RESOURCE__RESOURCE_TYPE:
            return resourceType != null;
        case OMPackage.RESOURCE__DN:
            return DN_EDEFAULT == null ? dn != null : !DN_EDEFAULT.equals(dn);
        }
        return super.eIsSet(featureID);
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
        result.append(", dn: "); //$NON-NLS-1$
        result.append(dn);
        result.append(')');
        return result.toString();
    }

} // ResourceImpl
