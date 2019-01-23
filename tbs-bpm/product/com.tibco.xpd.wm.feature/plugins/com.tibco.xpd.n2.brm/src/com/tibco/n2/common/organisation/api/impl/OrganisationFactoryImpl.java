/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api.impl;

import com.tibco.n2.common.organisation.api.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class OrganisationFactoryImpl extends EFactoryImpl implements OrganisationFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static OrganisationFactory init() {
        try {
            OrganisationFactory theOrganisationFactory = (OrganisationFactory)EPackage.Registry.INSTANCE.getEFactory("http://api.organisation.common.n2.tibco.com"); 
            if (theOrganisationFactory != null) {
                return theOrganisationFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new OrganisationFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrganisationFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case OrganisationPackage.QUALIFIER_SET_TYPE: return createQualifierSetType();
            case OrganisationPackage.XML_CALENDAR_ASSIGNMENT: return createXmlCalendarAssignment();
            case OrganisationPackage.XML_CALENDAR_REF: return createXmlCalendarRef();
            case OrganisationPackage.XML_EXECUTE_QUERY: return createXmlExecuteQuery();
            case OrganisationPackage.XML_MODEL_ENTITY_ID: return createXmlModelEntityId();
            case OrganisationPackage.XML_ORG_MODEL_VERSION: return createXmlOrgModelVersion();
            case OrganisationPackage.XML_PARTICIPANT_ID: return createXmlParticipantId();
            case OrganisationPackage.XML_RESOURCE_QUERY: return createXmlResourceQuery();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case OrganisationPackage.ORGANISATIONAL_ENTITY_TYPE:
                return createOrganisationalEntityTypeFromString(eDataType, initialValue);
            case OrganisationPackage.ORGANISATIONAL_ENTITY_TYPE_OBJECT:
                return createOrganisationalEntityTypeObjectFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case OrganisationPackage.ORGANISATIONAL_ENTITY_TYPE:
                return convertOrganisationalEntityTypeToString(eDataType, instanceValue);
            case OrganisationPackage.ORGANISATIONAL_ENTITY_TYPE_OBJECT:
                return convertOrganisationalEntityTypeObjectToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public QualifierSetType createQualifierSetType() {
        QualifierSetTypeImpl qualifierSetType = new QualifierSetTypeImpl();
        return qualifierSetType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XmlCalendarAssignment createXmlCalendarAssignment() {
        XmlCalendarAssignmentImpl xmlCalendarAssignment = new XmlCalendarAssignmentImpl();
        return xmlCalendarAssignment;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XmlCalendarRef createXmlCalendarRef() {
        XmlCalendarRefImpl xmlCalendarRef = new XmlCalendarRefImpl();
        return xmlCalendarRef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XmlExecuteQuery createXmlExecuteQuery() {
        XmlExecuteQueryImpl xmlExecuteQuery = new XmlExecuteQueryImpl();
        return xmlExecuteQuery;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XmlModelEntityId createXmlModelEntityId() {
        XmlModelEntityIdImpl xmlModelEntityId = new XmlModelEntityIdImpl();
        return xmlModelEntityId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XmlOrgModelVersion createXmlOrgModelVersion() {
        XmlOrgModelVersionImpl xmlOrgModelVersion = new XmlOrgModelVersionImpl();
        return xmlOrgModelVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XmlParticipantId createXmlParticipantId() {
        XmlParticipantIdImpl xmlParticipantId = new XmlParticipantIdImpl();
        return xmlParticipantId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XmlResourceQuery createXmlResourceQuery() {
        XmlResourceQueryImpl xmlResourceQuery = new XmlResourceQueryImpl();
        return xmlResourceQuery;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrganisationalEntityType createOrganisationalEntityTypeFromString(EDataType eDataType, String initialValue) {
        OrganisationalEntityType result = OrganisationalEntityType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertOrganisationalEntityTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrganisationalEntityType createOrganisationalEntityTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createOrganisationalEntityTypeFromString(OrganisationPackage.Literals.ORGANISATIONAL_ENTITY_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertOrganisationalEntityTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertOrganisationalEntityTypeToString(OrganisationPackage.Literals.ORGANISATIONAL_ENTITY_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrganisationPackage getOrganisationPackage() {
        return (OrganisationPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static OrganisationPackage getPackage() {
        return OrganisationPackage.eINSTANCE;
    }

} //OrganisationFactoryImpl
