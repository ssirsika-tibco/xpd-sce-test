/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api.util;

import com.tibco.n2.common.organisation.api.*;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.organisation.api.OrganisationPackage
 * @generated
 */
public class OrganisationSwitch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static OrganisationPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OrganisationSwitch() {
        if (modelPackage == null) {
            modelPackage = OrganisationPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(EObject theEObject) {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(EClass theEClass, EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        }
        else {
            List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return
                eSuperTypes.isEmpty() ?
                    defaultCase(theEObject) :
                    doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case OrganisationPackage.QUALIFIER_SET_TYPE: {
                QualifierSetType qualifierSetType = (QualifierSetType)theEObject;
                T result = caseQualifierSetType(qualifierSetType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case OrganisationPackage.XML_CALENDAR_ASSIGNMENT: {
                XmlCalendarAssignment xmlCalendarAssignment = (XmlCalendarAssignment)theEObject;
                T result = caseXmlCalendarAssignment(xmlCalendarAssignment);
                if (result == null) result = caseXmlOrgModelVersion(xmlCalendarAssignment);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case OrganisationPackage.XML_CALENDAR_REF: {
                XmlCalendarRef xmlCalendarRef = (XmlCalendarRef)theEObject;
                T result = caseXmlCalendarRef(xmlCalendarRef);
                if (result == null) result = caseXmlOrgModelVersion(xmlCalendarRef);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case OrganisationPackage.XML_EXECUTE_QUERY: {
                XmlExecuteQuery xmlExecuteQuery = (XmlExecuteQuery)theEObject;
                T result = caseXmlExecuteQuery(xmlExecuteQuery);
                if (result == null) result = caseXmlResourceQuery(xmlExecuteQuery);
                if (result == null) result = caseXmlOrgModelVersion(xmlExecuteQuery);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case OrganisationPackage.XML_MODEL_ENTITY_ID: {
                XmlModelEntityId xmlModelEntityId = (XmlModelEntityId)theEObject;
                T result = caseXmlModelEntityId(xmlModelEntityId);
                if (result == null) result = caseXmlOrgModelVersion(xmlModelEntityId);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case OrganisationPackage.XML_ORG_MODEL_VERSION: {
                XmlOrgModelVersion xmlOrgModelVersion = (XmlOrgModelVersion)theEObject;
                T result = caseXmlOrgModelVersion(xmlOrgModelVersion);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case OrganisationPackage.XML_PARTICIPANT_ID: {
                XmlParticipantId xmlParticipantId = (XmlParticipantId)theEObject;
                T result = caseXmlParticipantId(xmlParticipantId);
                if (result == null) result = caseXmlOrgModelVersion(xmlParticipantId);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case OrganisationPackage.XML_RESOURCE_QUERY: {
                XmlResourceQuery xmlResourceQuery = (XmlResourceQuery)theEObject;
                T result = caseXmlResourceQuery(xmlResourceQuery);
                if (result == null) result = caseXmlOrgModelVersion(xmlResourceQuery);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Qualifier Set Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Qualifier Set Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseQualifierSetType(QualifierSetType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xml Calendar Assignment</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xml Calendar Assignment</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseXmlCalendarAssignment(XmlCalendarAssignment object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xml Calendar Ref</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xml Calendar Ref</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseXmlCalendarRef(XmlCalendarRef object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xml Execute Query</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xml Execute Query</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseXmlExecuteQuery(XmlExecuteQuery object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xml Model Entity Id</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xml Model Entity Id</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseXmlModelEntityId(XmlModelEntityId object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xml Org Model Version</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xml Org Model Version</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseXmlOrgModelVersion(XmlOrgModelVersion object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xml Participant Id</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xml Participant Id</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseXmlParticipantId(XmlParticipantId object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xml Resource Query</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xml Resource Query</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseXmlResourceQuery(XmlResourceQuery object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(EObject object) {
        return null;
    }

} //OrganisationSwitch
