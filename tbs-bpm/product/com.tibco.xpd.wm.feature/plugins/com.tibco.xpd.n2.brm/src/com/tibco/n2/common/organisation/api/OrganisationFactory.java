/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.common.organisation.api.OrganisationPackage
 * @generated
 */
public interface OrganisationFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    OrganisationFactory eINSTANCE = com.tibco.n2.common.organisation.api.impl.OrganisationFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Qualifier Set Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Qualifier Set Type</em>'.
     * @generated
     */
    QualifierSetType createQualifierSetType();

    /**
     * Returns a new object of class '<em>Xml Calendar Assignment</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Xml Calendar Assignment</em>'.
     * @generated
     */
    XmlCalendarAssignment createXmlCalendarAssignment();

    /**
     * Returns a new object of class '<em>Xml Calendar Ref</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Xml Calendar Ref</em>'.
     * @generated
     */
    XmlCalendarRef createXmlCalendarRef();

    /**
     * Returns a new object of class '<em>Xml Execute Query</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Xml Execute Query</em>'.
     * @generated
     */
    XmlExecuteQuery createXmlExecuteQuery();

    /**
     * Returns a new object of class '<em>Xml Model Entity Id</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Xml Model Entity Id</em>'.
     * @generated
     */
    XmlModelEntityId createXmlModelEntityId();

    /**
     * Returns a new object of class '<em>Xml Org Model Version</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Xml Org Model Version</em>'.
     * @generated
     */
    XmlOrgModelVersion createXmlOrgModelVersion();

    /**
     * Returns a new object of class '<em>Xml Participant Id</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Xml Participant Id</em>'.
     * @generated
     */
    XmlParticipantId createXmlParticipantId();

    /**
     * Returns a new object of class '<em>Xml Resource Query</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Xml Resource Query</em>'.
     * @generated
     */
    XmlResourceQuery createXmlResourceQuery();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    OrganisationPackage getOrganisationPackage();

} //OrganisationFactory
