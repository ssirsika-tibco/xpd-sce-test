/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.n2.wp.archive.service.WPPackage
 * @generated
 */
public interface WPFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    WPFactory eINSTANCE = com.tibco.n2.wp.archive.service.impl.WPFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Business Service Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Business Service Type</em>'.
     * @generated
     */
    BusinessServiceType createBusinessServiceType();

    /**
     * Returns a new object of class '<em>Channel Extention Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Channel Extention Type</em>'.
     * @generated
     */
    ChannelExtentionType createChannelExtentionType();

    /**
     * Returns a new object of class '<em>Channels Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Channels Type</em>'.
     * @generated
     */
    ChannelsType createChannelsType();

    /**
     * Returns a new object of class '<em>Channel Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Channel Type</em>'.
     * @generated
     */
    ChannelType createChannelType();

    /**
     * Returns a new object of class '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Extended Properties Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Extended Properties Type</em>'.
     * @generated
     */
    ExtendedPropertiesType createExtendedPropertiesType();

    /**
     * Returns a new object of class '<em>Form Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Form Type</em>'.
     * @generated
     */
    FormType createFormType();

    /**
     * Returns a new object of class '<em>Page Activity Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Page Activity Type</em>'.
     * @generated
     */
    PageActivityType createPageActivityType();

    /**
     * Returns a new object of class '<em>Page Flow Ref Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Page Flow Ref Type</em>'.
     * @generated
     */
    PageFlowRefType createPageFlowRefType();

    /**
     * Returns a new object of class '<em>Page Flow Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Page Flow Type</em>'.
     * @generated
     */
    PageFlowType createPageFlowType();

    /**
     * Returns a new object of class '<em>Property Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Property Type</em>'.
     * @generated
     */
    PropertyType createPropertyType();

    /**
     * Returns a new object of class '<em>Service Archive Descriptor Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Service Archive Descriptor Type</em>'.
     * @generated
     */
    ServiceArchiveDescriptorType createServiceArchiveDescriptorType();

    /**
     * Returns a new object of class '<em>Work Type Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Work Type Type</em>'.
     * @generated
     */
    WorkTypeType createWorkTypeType();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    WPPackage getWPPackage();

} //WPFactory
