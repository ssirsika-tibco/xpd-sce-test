/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api.impl;

import com.tibco.n2.common.organisation.api.OrganisationPackage;
import com.tibco.n2.common.organisation.api.XmlCalendarAssignment;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Xml Calendar Assignment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlCalendarAssignmentImpl#getCalendarAlias <em>Calendar Alias</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlCalendarAssignmentImpl#getEntityGuid <em>Entity Guid</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class XmlCalendarAssignmentImpl extends XmlOrgModelVersionImpl implements XmlCalendarAssignment {
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected XmlCalendarAssignmentImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OrganisationPackage.Literals.XML_CALENDAR_ASSIGNMENT;
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
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_CALENDAR_ASSIGNMENT__CALENDAR_ALIAS, oldCalendarAlias, calendarAlias));
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
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_CALENDAR_ASSIGNMENT__ENTITY_GUID, oldEntityGuid, entityGuid));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OrganisationPackage.XML_CALENDAR_ASSIGNMENT__CALENDAR_ALIAS:
                return getCalendarAlias();
            case OrganisationPackage.XML_CALENDAR_ASSIGNMENT__ENTITY_GUID:
                return getEntityGuid();
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
            case OrganisationPackage.XML_CALENDAR_ASSIGNMENT__CALENDAR_ALIAS:
                setCalendarAlias((String)newValue);
                return;
            case OrganisationPackage.XML_CALENDAR_ASSIGNMENT__ENTITY_GUID:
                setEntityGuid((String)newValue);
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
            case OrganisationPackage.XML_CALENDAR_ASSIGNMENT__CALENDAR_ALIAS:
                setCalendarAlias(CALENDAR_ALIAS_EDEFAULT);
                return;
            case OrganisationPackage.XML_CALENDAR_ASSIGNMENT__ENTITY_GUID:
                setEntityGuid(ENTITY_GUID_EDEFAULT);
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
            case OrganisationPackage.XML_CALENDAR_ASSIGNMENT__CALENDAR_ALIAS:
                return CALENDAR_ALIAS_EDEFAULT == null ? calendarAlias != null : !CALENDAR_ALIAS_EDEFAULT.equals(calendarAlias);
            case OrganisationPackage.XML_CALENDAR_ASSIGNMENT__ENTITY_GUID:
                return ENTITY_GUID_EDEFAULT == null ? entityGuid != null : !ENTITY_GUID_EDEFAULT.equals(entityGuid);
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
        result.append(')');
        return result.toString();
    }

} //XmlCalendarAssignmentImpl
