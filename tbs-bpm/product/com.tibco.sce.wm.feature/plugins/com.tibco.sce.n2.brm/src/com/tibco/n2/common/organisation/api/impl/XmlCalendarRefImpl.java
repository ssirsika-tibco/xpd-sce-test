/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api.impl;

import com.tibco.n2.common.organisation.api.OrganisationPackage;
import com.tibco.n2.common.organisation.api.OrganisationalEntityType;
import com.tibco.n2.common.organisation.api.XmlCalendarRef;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Xml Calendar Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlCalendarRefImpl#getCalendarAlias <em>Calendar Alias</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlCalendarRefImpl#getEntityGuid <em>Entity Guid</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlCalendarRefImpl#getEntityLabel <em>Entity Label</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlCalendarRefImpl#getEntityName <em>Entity Name</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlCalendarRefImpl#getEntityType <em>Entity Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class XmlCalendarRefImpl extends XmlOrgModelVersionImpl implements XmlCalendarRef {
    /**
     * The default value of the '{@link #getCalendarAlias() <em>Calendar Alias</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCalendarAlias()
     * @generated
     * @ordered
     */
    protected static final String CALENDAR_ALIAS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCalendarAlias() <em>Calendar Alias</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCalendarAlias()
     * @generated
     * @ordered
     */
    protected String calendarAlias = CALENDAR_ALIAS_EDEFAULT;

    /**
     * The default value of the '{@link #getEntityGuid() <em>Entity Guid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityGuid()
     * @generated
     * @ordered
     */
    protected static final String ENTITY_GUID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEntityGuid() <em>Entity Guid</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityGuid()
     * @generated
     * @ordered
     */
    protected String entityGuid = ENTITY_GUID_EDEFAULT;

    /**
     * The default value of the '{@link #getEntityLabel() <em>Entity Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityLabel()
     * @generated
     * @ordered
     */
    protected static final String ENTITY_LABEL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEntityLabel() <em>Entity Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityLabel()
     * @generated
     * @ordered
     */
    protected String entityLabel = ENTITY_LABEL_EDEFAULT;

    /**
     * The default value of the '{@link #getEntityName() <em>Entity Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityName()
     * @generated
     * @ordered
     */
    protected static final String ENTITY_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEntityName() <em>Entity Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityName()
     * @generated
     * @ordered
     */
    protected String entityName = ENTITY_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getEntityType() <em>Entity Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityType()
     * @generated
     * @ordered
     */
    protected static final OrganisationalEntityType ENTITY_TYPE_EDEFAULT = OrganisationalEntityType.ORGANIZATION;

    /**
     * The cached value of the '{@link #getEntityType() <em>Entity Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEntityType()
     * @generated
     * @ordered
     */
    protected OrganisationalEntityType entityType = ENTITY_TYPE_EDEFAULT;

    /**
     * This is true if the Entity Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean entityTypeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected XmlCalendarRefImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OrganisationPackage.Literals.XML_CALENDAR_REF;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCalendarAlias() {
        return calendarAlias;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCalendarAlias(String newCalendarAlias) {
        String oldCalendarAlias = calendarAlias;
        calendarAlias = newCalendarAlias;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_CALENDAR_REF__CALENDAR_ALIAS, oldCalendarAlias, calendarAlias));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getEntityGuid() {
        return entityGuid;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEntityGuid(String newEntityGuid) {
        String oldEntityGuid = entityGuid;
        entityGuid = newEntityGuid;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_CALENDAR_REF__ENTITY_GUID, oldEntityGuid, entityGuid));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getEntityLabel() {
        return entityLabel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEntityLabel(String newEntityLabel) {
        String oldEntityLabel = entityLabel;
        entityLabel = newEntityLabel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_CALENDAR_REF__ENTITY_LABEL, oldEntityLabel, entityLabel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEntityName(String newEntityName) {
        String oldEntityName = entityName;
        entityName = newEntityName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_CALENDAR_REF__ENTITY_NAME, oldEntityName, entityName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrganisationalEntityType getEntityType() {
        return entityType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEntityType(OrganisationalEntityType newEntityType) {
        OrganisationalEntityType oldEntityType = entityType;
        entityType = newEntityType == null ? ENTITY_TYPE_EDEFAULT : newEntityType;
        boolean oldEntityTypeESet = entityTypeESet;
        entityTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_CALENDAR_REF__ENTITY_TYPE, oldEntityType, entityType, !oldEntityTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetEntityType() {
        OrganisationalEntityType oldEntityType = entityType;
        boolean oldEntityTypeESet = entityTypeESet;
        entityType = ENTITY_TYPE_EDEFAULT;
        entityTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, OrganisationPackage.XML_CALENDAR_REF__ENTITY_TYPE, oldEntityType, ENTITY_TYPE_EDEFAULT, oldEntityTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetEntityType() {
        return entityTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OrganisationPackage.XML_CALENDAR_REF__CALENDAR_ALIAS:
                return getCalendarAlias();
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_GUID:
                return getEntityGuid();
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_LABEL:
                return getEntityLabel();
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_NAME:
                return getEntityName();
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_TYPE:
                return getEntityType();
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
            case OrganisationPackage.XML_CALENDAR_REF__CALENDAR_ALIAS:
                setCalendarAlias((String)newValue);
                return;
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_GUID:
                setEntityGuid((String)newValue);
                return;
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_LABEL:
                setEntityLabel((String)newValue);
                return;
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_NAME:
                setEntityName((String)newValue);
                return;
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_TYPE:
                setEntityType((OrganisationalEntityType)newValue);
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
            case OrganisationPackage.XML_CALENDAR_REF__CALENDAR_ALIAS:
                setCalendarAlias(CALENDAR_ALIAS_EDEFAULT);
                return;
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_GUID:
                setEntityGuid(ENTITY_GUID_EDEFAULT);
                return;
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_LABEL:
                setEntityLabel(ENTITY_LABEL_EDEFAULT);
                return;
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_NAME:
                setEntityName(ENTITY_NAME_EDEFAULT);
                return;
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_TYPE:
                unsetEntityType();
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
            case OrganisationPackage.XML_CALENDAR_REF__CALENDAR_ALIAS:
                return CALENDAR_ALIAS_EDEFAULT == null ? calendarAlias != null : !CALENDAR_ALIAS_EDEFAULT.equals(calendarAlias);
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_GUID:
                return ENTITY_GUID_EDEFAULT == null ? entityGuid != null : !ENTITY_GUID_EDEFAULT.equals(entityGuid);
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_LABEL:
                return ENTITY_LABEL_EDEFAULT == null ? entityLabel != null : !ENTITY_LABEL_EDEFAULT.equals(entityLabel);
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_NAME:
                return ENTITY_NAME_EDEFAULT == null ? entityName != null : !ENTITY_NAME_EDEFAULT.equals(entityName);
            case OrganisationPackage.XML_CALENDAR_REF__ENTITY_TYPE:
                return isSetEntityType();
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
        result.append(" (calendarAlias: ");
        result.append(calendarAlias);
        result.append(", entityGuid: ");
        result.append(entityGuid);
        result.append(", entityLabel: ");
        result.append(entityLabel);
        result.append(", entityName: ");
        result.append(entityName);
        result.append(", entityType: ");
        if (entityTypeESet) result.append(entityType); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //XmlCalendarRefImpl
