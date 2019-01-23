/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.QualifiedOrgElement;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Qualified Org Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.QualifiedOrgElementImpl#getPurpose <em>Purpose</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.QualifiedOrgElementImpl#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.QualifiedOrgElementImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.QualifiedOrgElementImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.QualifiedOrgElementImpl#getQualifierAttribute <em>Qualifier Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class QualifiedOrgElementImpl extends NamedElementImpl
        implements QualifiedOrgElement {
    /**
     * The default value of the '{@link #getPurpose() <em>Purpose</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPurpose()
     * @generated
     * @ordered
     */
    protected static final String PURPOSE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPurpose() <em>Purpose</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPurpose()
     * @generated
     * @ordered
     */
    protected String purpose = PURPOSE_EDEFAULT;

    /**
     * The default value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected static final Date START_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected Date startDate = START_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected static final Date END_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected Date endDate = END_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The cached value of the '{@link #getQualifierAttribute() <em>Qualifier Attribute</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQualifierAttribute()
     * @generated
     * @ordered
     */
    protected Attribute qualifierAttribute;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected QualifiedOrgElementImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.QUALIFIED_ORG_ELEMENT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPurpose(String newPurpose) {
        String oldPurpose = purpose;
        purpose = newPurpose;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.QUALIFIED_ORG_ELEMENT__PURPOSE, oldPurpose,
                    purpose));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartDate(Date newStartDate) {
        Date oldStartDate = startDate;
        startDate = newStartDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.QUALIFIED_ORG_ELEMENT__START_DATE, oldStartDate,
                    startDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEndDate(Date newEndDate) {
        Date oldEndDate = endDate;
        endDate = newEndDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.QUALIFIED_ORG_ELEMENT__END_DATE, oldEndDate,
                    endDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.QUALIFIED_ORG_ELEMENT__DESCRIPTION,
                    oldDescription, description));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Attribute getQualifierAttribute() {
        return qualifierAttribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetQualifierAttribute(
            Attribute newQualifierAttribute, NotificationChain msgs) {
        Attribute oldQualifierAttribute = qualifierAttribute;
        qualifierAttribute = newQualifierAttribute;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            OMPackage.QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE,
                            oldQualifierAttribute, newQualifierAttribute);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setQualifierAttribute(Attribute newQualifierAttribute) {
        if (newQualifierAttribute != qualifierAttribute) {
            NotificationChain msgs = null;
            if (qualifierAttribute != null)
                msgs =
                        ((InternalEObject) qualifierAttribute)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - OMPackage.QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE,
                                        null,
                                        msgs);
            if (newQualifierAttribute != null)
                msgs =
                        ((InternalEObject) newQualifierAttribute)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - OMPackage.QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE,
                                        null,
                                        msgs);
            msgs = basicSetQualifierAttribute(newQualifierAttribute, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE,
                    newQualifierAttribute, newQualifierAttribute));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case OMPackage.QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE:
            return basicSetQualifierAttribute(null, msgs);
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
        case OMPackage.QUALIFIED_ORG_ELEMENT__PURPOSE:
            return getPurpose();
        case OMPackage.QUALIFIED_ORG_ELEMENT__START_DATE:
            return getStartDate();
        case OMPackage.QUALIFIED_ORG_ELEMENT__END_DATE:
            return getEndDate();
        case OMPackage.QUALIFIED_ORG_ELEMENT__DESCRIPTION:
            return getDescription();
        case OMPackage.QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE:
            return getQualifierAttribute();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case OMPackage.QUALIFIED_ORG_ELEMENT__PURPOSE:
            setPurpose((String) newValue);
            return;
        case OMPackage.QUALIFIED_ORG_ELEMENT__START_DATE:
            setStartDate((Date) newValue);
            return;
        case OMPackage.QUALIFIED_ORG_ELEMENT__END_DATE:
            setEndDate((Date) newValue);
            return;
        case OMPackage.QUALIFIED_ORG_ELEMENT__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case OMPackage.QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE:
            setQualifierAttribute((Attribute) newValue);
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
        case OMPackage.QUALIFIED_ORG_ELEMENT__PURPOSE:
            setPurpose(PURPOSE_EDEFAULT);
            return;
        case OMPackage.QUALIFIED_ORG_ELEMENT__START_DATE:
            setStartDate(START_DATE_EDEFAULT);
            return;
        case OMPackage.QUALIFIED_ORG_ELEMENT__END_DATE:
            setEndDate(END_DATE_EDEFAULT);
            return;
        case OMPackage.QUALIFIED_ORG_ELEMENT__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case OMPackage.QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE:
            setQualifierAttribute((Attribute) null);
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
        case OMPackage.QUALIFIED_ORG_ELEMENT__PURPOSE:
            return PURPOSE_EDEFAULT == null ? purpose != null
                    : !PURPOSE_EDEFAULT.equals(purpose);
        case OMPackage.QUALIFIED_ORG_ELEMENT__START_DATE:
            return START_DATE_EDEFAULT == null ? startDate != null
                    : !START_DATE_EDEFAULT.equals(startDate);
        case OMPackage.QUALIFIED_ORG_ELEMENT__END_DATE:
            return END_DATE_EDEFAULT == null ? endDate != null
                    : !END_DATE_EDEFAULT.equals(endDate);
        case OMPackage.QUALIFIED_ORG_ELEMENT__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null
                    : !DESCRIPTION_EDEFAULT.equals(description);
        case OMPackage.QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE:
            return qualifierAttribute != null;
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
        result.append(')');
        return result.toString();
    }

} //QualifiedOrgElementImpl
