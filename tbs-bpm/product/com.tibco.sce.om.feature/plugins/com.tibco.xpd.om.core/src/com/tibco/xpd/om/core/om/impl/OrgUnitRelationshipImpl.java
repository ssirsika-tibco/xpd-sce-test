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
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Org Unit Relationship</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl#getPurpose <em>Purpose</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl#getAttributeValues <em>Attribute Values</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl#getFrom <em>From</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl#getTo <em>To</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl#getRelationshipType <em>Relationship Type</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgUnitRelationshipImpl#isIsHierarchical <em>Is Hierarchical</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrgUnitRelationshipImpl extends NamedElementImpl implements
        OrgUnitRelationship {
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
     * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getFrom()
     * @generated
     * @ordered
     */
    protected OrgUnit from;

    /**
     * The cached value of the '{@link #getTo() <em>To</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTo()
     * @generated
     * @ordered
     */
    protected OrgUnit to;

    /**
     * The cached value of the '{@link #getRelationshipType() <em>Relationship Type</em>}' reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getRelationshipType()
     * @generated
     * @ordered
     */
    protected OrgUnitRelationshipType relationshipType;

    /**
     * The default value of the '{@link #isIsHierarchical() <em>Is Hierarchical</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #isIsHierarchical()
     * @generated
     * @ordered
     */
    protected static final boolean IS_HIERARCHICAL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsHierarchical() <em>Is Hierarchical</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #isIsHierarchical()
     * @generated
     * @ordered
     */
    protected boolean isHierarchical = IS_HIERARCHICAL_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected OrgUnitRelationshipImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.ORG_UNIT_RELATIONSHIP;
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
                    OMPackage.ORG_UNIT_RELATIONSHIP__PURPOSE, oldPurpose,
                    purpose));
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
                    OMPackage.ORG_UNIT_RELATIONSHIP__START_DATE, oldStartDate,
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
                    OMPackage.ORG_UNIT_RELATIONSHIP__END_DATE, oldEndDate,
                    endDate));
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
                    OMPackage.ORG_UNIT_RELATIONSHIP__DESCRIPTION,
                    oldDescription, description));
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
                            OMPackage.ORG_UNIT_RELATIONSHIP__ATTRIBUTE_VALUES);
        }
        return attributeValues;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrgUnit getFrom() {
        if (from != null && from.eIsProxy()) {
            InternalEObject oldFrom = (InternalEObject) from;
            from = (OrgUnit) eResolveProxy(oldFrom);
            if (from != oldFrom) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.ORG_UNIT_RELATIONSHIP__FROM, oldFrom,
                            from));
            }
        }
        return from;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrgUnit basicGetFrom() {
        return from;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetFrom(OrgUnit newFrom,
            NotificationChain msgs) {
        OrgUnit oldFrom = from;
        from = newFrom;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            OMPackage.ORG_UNIT_RELATIONSHIP__FROM, oldFrom,
                            newFrom);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setFrom(OrgUnit newFrom) {
        if (newFrom != from) {
            NotificationChain msgs = null;
            if (from != null)
                msgs =
                        ((InternalEObject) from).eInverseRemove(this,
                                OMPackage.ORG_UNIT__OUTGOING_RELATIONSHIPS,
                                OrgUnit.class,
                                msgs);
            if (newFrom != null)
                msgs =
                        ((InternalEObject) newFrom).eInverseAdd(this,
                                OMPackage.ORG_UNIT__OUTGOING_RELATIONSHIPS,
                                OrgUnit.class,
                                msgs);
            msgs = basicSetFrom(newFrom, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT_RELATIONSHIP__FROM, newFrom, newFrom));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrgUnit getTo() {
        if (to != null && to.eIsProxy()) {
            InternalEObject oldTo = (InternalEObject) to;
            to = (OrgUnit) eResolveProxy(oldTo);
            if (to != oldTo) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.ORG_UNIT_RELATIONSHIP__TO, oldTo, to));
            }
        }
        return to;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrgUnit basicGetTo() {
        return to;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTo(OrgUnit newTo, NotificationChain msgs) {
        OrgUnit oldTo = to;
        to = newTo;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            OMPackage.ORG_UNIT_RELATIONSHIP__TO, oldTo, newTo);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setTo(OrgUnit newTo) {
        if (newTo != to) {
            NotificationChain msgs = null;
            if (to != null)
                msgs =
                        ((InternalEObject) to).eInverseRemove(this,
                                OMPackage.ORG_UNIT__INCOMING_RELATIONSHIPS,
                                OrgUnit.class,
                                msgs);
            if (newTo != null)
                msgs =
                        ((InternalEObject) newTo).eInverseAdd(this,
                                OMPackage.ORG_UNIT__INCOMING_RELATIONSHIPS,
                                OrgUnit.class,
                                msgs);
            msgs = basicSetTo(newTo, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT_RELATIONSHIP__TO, newTo, newTo));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrgUnitRelationshipType getRelationshipType() {
        if (relationshipType != null && relationshipType.eIsProxy()) {
            InternalEObject oldRelationshipType =
                    (InternalEObject) relationshipType;
            relationshipType =
                    (OrgUnitRelationshipType) eResolveProxy(oldRelationshipType);
            if (relationshipType != oldRelationshipType) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE,
                            OMPackage.ORG_UNIT_RELATIONSHIP__RELATIONSHIP_TYPE,
                            oldRelationshipType, relationshipType));
            }
        }
        return relationshipType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public OrgUnitRelationshipType basicGetRelationshipType() {
        return relationshipType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setRelationshipType(OrgUnitRelationshipType newRelationshipType) {
        OrgUnitRelationshipType oldRelationshipType = relationshipType;
        relationshipType = newRelationshipType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT_RELATIONSHIP__RELATIONSHIP_TYPE,
                    oldRelationshipType, relationshipType));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public OrgElementType getType() {
        return getRelationshipType();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public void setType(OrgElementType newType) {
        setRelationshipType((OrgUnitRelationshipType) newType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isIsHierarchical() {
        return isHierarchical;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setIsHierarchical(boolean newIsHierarchical) {
        boolean oldIsHierarchical = isHierarchical;
        isHierarchical = newIsHierarchical;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_UNIT_RELATIONSHIP__IS_HIERARCHICAL,
                    oldIsHierarchical, isHierarchical));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.ORG_UNIT_RELATIONSHIP__FROM:
            if (from != null)
                msgs =
                        ((InternalEObject) from).eInverseRemove(this,
                                OMPackage.ORG_UNIT__OUTGOING_RELATIONSHIPS,
                                OrgUnit.class,
                                msgs);
            return basicSetFrom((OrgUnit) otherEnd, msgs);
        case OMPackage.ORG_UNIT_RELATIONSHIP__TO:
            if (to != null)
                msgs =
                        ((InternalEObject) to).eInverseRemove(this,
                                OMPackage.ORG_UNIT__INCOMING_RELATIONSHIPS,
                                OrgUnit.class,
                                msgs);
            return basicSetTo((OrgUnit) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.ORG_UNIT_RELATIONSHIP__ATTRIBUTE_VALUES:
            return ((InternalEList<?>) getAttributeValues())
                    .basicRemove(otherEnd, msgs);
        case OMPackage.ORG_UNIT_RELATIONSHIP__FROM:
            return basicSetFrom(null, msgs);
        case OMPackage.ORG_UNIT_RELATIONSHIP__TO:
            return basicSetTo(null, msgs);
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
        case OMPackage.ORG_UNIT_RELATIONSHIP__PURPOSE:
            return getPurpose();
        case OMPackage.ORG_UNIT_RELATIONSHIP__START_DATE:
            return getStartDate();
        case OMPackage.ORG_UNIT_RELATIONSHIP__END_DATE:
            return getEndDate();
        case OMPackage.ORG_UNIT_RELATIONSHIP__DESCRIPTION:
            return getDescription();
        case OMPackage.ORG_UNIT_RELATIONSHIP__ATTRIBUTE_VALUES:
            return getAttributeValues();
        case OMPackage.ORG_UNIT_RELATIONSHIP__TYPE:
            return getType();
        case OMPackage.ORG_UNIT_RELATIONSHIP__FROM:
            if (resolve)
                return getFrom();
            return basicGetFrom();
        case OMPackage.ORG_UNIT_RELATIONSHIP__TO:
            if (resolve)
                return getTo();
            return basicGetTo();
        case OMPackage.ORG_UNIT_RELATIONSHIP__RELATIONSHIP_TYPE:
            if (resolve)
                return getRelationshipType();
            return basicGetRelationshipType();
        case OMPackage.ORG_UNIT_RELATIONSHIP__IS_HIERARCHICAL:
            return isIsHierarchical() ? Boolean.TRUE : Boolean.FALSE;
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
        case OMPackage.ORG_UNIT_RELATIONSHIP__PURPOSE:
            setPurpose((String) newValue);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__START_DATE:
            setStartDate((Date) newValue);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__END_DATE:
            setEndDate((Date) newValue);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__ATTRIBUTE_VALUES:
            getAttributeValues().clear();
            getAttributeValues()
                    .addAll((Collection<? extends AttributeValue>) newValue);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__TYPE:
            setType((OrgElementType) newValue);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__FROM:
            setFrom((OrgUnit) newValue);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__TO:
            setTo((OrgUnit) newValue);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__RELATIONSHIP_TYPE:
            setRelationshipType((OrgUnitRelationshipType) newValue);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__IS_HIERARCHICAL:
            setIsHierarchical(((Boolean) newValue).booleanValue());
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
        case OMPackage.ORG_UNIT_RELATIONSHIP__PURPOSE:
            setPurpose(PURPOSE_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__START_DATE:
            setStartDate(START_DATE_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__END_DATE:
            setEndDate(END_DATE_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__ATTRIBUTE_VALUES:
            getAttributeValues().clear();
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__TYPE:
            setType((OrgElementType) null);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__FROM:
            setFrom((OrgUnit) null);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__TO:
            setTo((OrgUnit) null);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__RELATIONSHIP_TYPE:
            setRelationshipType((OrgUnitRelationshipType) null);
            return;
        case OMPackage.ORG_UNIT_RELATIONSHIP__IS_HIERARCHICAL:
            setIsHierarchical(IS_HIERARCHICAL_EDEFAULT);
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
        case OMPackage.ORG_UNIT_RELATIONSHIP__PURPOSE:
            return PURPOSE_EDEFAULT == null ? purpose != null
                    : !PURPOSE_EDEFAULT.equals(purpose);
        case OMPackage.ORG_UNIT_RELATIONSHIP__START_DATE:
            return START_DATE_EDEFAULT == null ? startDate != null
                    : !START_DATE_EDEFAULT.equals(startDate);
        case OMPackage.ORG_UNIT_RELATIONSHIP__END_DATE:
            return END_DATE_EDEFAULT == null ? endDate != null
                    : !END_DATE_EDEFAULT.equals(endDate);
        case OMPackage.ORG_UNIT_RELATIONSHIP__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case OMPackage.ORG_UNIT_RELATIONSHIP__ATTRIBUTE_VALUES:
            return attributeValues != null && !attributeValues.isEmpty();
        case OMPackage.ORG_UNIT_RELATIONSHIP__TYPE:
            return getType() != null;
        case OMPackage.ORG_UNIT_RELATIONSHIP__FROM:
            return from != null;
        case OMPackage.ORG_UNIT_RELATIONSHIP__TO:
            return to != null;
        case OMPackage.ORG_UNIT_RELATIONSHIP__RELATIONSHIP_TYPE:
            return relationshipType != null;
        case OMPackage.ORG_UNIT_RELATIONSHIP__IS_HIERARCHICAL:
            return isHierarchical != IS_HIERARCHICAL_EDEFAULT;
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
        result.append(", isHierarchical: "); //$NON-NLS-1$
        result.append(isHierarchical);
        result.append(')');
        return result.toString();
    }

} // OrgUnitRelationshipImpl
