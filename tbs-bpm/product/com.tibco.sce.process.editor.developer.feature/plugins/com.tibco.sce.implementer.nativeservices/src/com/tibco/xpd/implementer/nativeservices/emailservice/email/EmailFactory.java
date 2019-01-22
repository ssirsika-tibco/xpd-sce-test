/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.email;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage
 * @generated
 */
public interface EmailFactory extends EFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EmailFactory eINSTANCE = com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Attachment Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attachment Type</em>'.
	 * @generated
	 */
	AttachmentType createAttachmentType();

	/**
	 * Returns a new object of class '<em>Definition Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Definition Type</em>'.
	 * @generated
	 */
	DefinitionType createDefinitionType();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type</em>'.
	 * @generated
	 */
	EmailType createEmailType();

	/**
	 * Returns a new object of class '<em>Error Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Error Type</em>'.
	 * @generated
	 */
	ErrorType createErrorType();

	/**
	 * Returns a new object of class '<em>Files Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Files Type</em>'.
	 * @generated
	 */
	FilesType createFilesType();

	/**
	 * Returns a new object of class '<em>From Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>From Type</em>'.
	 * @generated
	 */
	FromType createFromType();

	/**
	 * Returns a new object of class '<em>SMTP Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>SMTP Type</em>'.
	 * @generated
	 */
	SMTPType createSMTPType();

	/**
	 * Returns a new object of class '<em>Text Mapping Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Text Mapping Type</em>'.
	 * @generated
	 */
	TextMappingType createTextMappingType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EmailPackage getEmailPackage();

} //EmailFactory
