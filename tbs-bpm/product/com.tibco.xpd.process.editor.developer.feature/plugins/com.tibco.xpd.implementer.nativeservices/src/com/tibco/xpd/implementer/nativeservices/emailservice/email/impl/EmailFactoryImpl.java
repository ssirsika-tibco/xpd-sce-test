/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.email.impl;

import com.tibco.xpd.implementer.nativeservices.emailservice.email.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EmailFactoryImpl extends EFactoryImpl implements EmailFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EmailFactory init() {
		try {
			EmailFactory theEmailFactory = (EmailFactory) EPackage.Registry.INSTANCE
					.getEFactory("http://www.tibco.com/XPD/email1.0.0"); //$NON-NLS-1$ 
			if (theEmailFactory != null) {
				return theEmailFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EmailFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmailFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case EmailPackage.ATTACHMENT_TYPE:
			return createAttachmentType();
		case EmailPackage.DEFINITION_TYPE:
			return createDefinitionType();
		case EmailPackage.DOCUMENT_ROOT:
			return createDocumentRoot();
		case EmailPackage.EMAIL_TYPE:
			return createEmailType();
		case EmailPackage.ERROR_TYPE:
			return createErrorType();
		case EmailPackage.FILES_TYPE:
			return createFilesType();
		case EmailPackage.FROM_TYPE:
			return createFromType();
		case EmailPackage.SMTP_TYPE:
			return createSMTPType();
		case EmailPackage.TEXT_MAPPING_TYPE:
			return createTextMappingType();
		default:
			throw new IllegalArgumentException(
					"The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case EmailPackage.CONFIGURATION_TYPE:
			return createConfigurationTypeFromString(eDataType, initialValue);
		case EmailPackage.CONFIGURATION_TYPE_OBJECT:
			return createConfigurationTypeObjectFromString(eDataType,
					initialValue);
		case EmailPackage.PARAMETER_TYPE:
			return createParameterTypeFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException(
					"The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case EmailPackage.CONFIGURATION_TYPE:
			return convertConfigurationTypeToString(eDataType, instanceValue);
		case EmailPackage.CONFIGURATION_TYPE_OBJECT:
			return convertConfigurationTypeObjectToString(eDataType,
					instanceValue);
		case EmailPackage.PARAMETER_TYPE:
			return convertParameterTypeToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException(
					"The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttachmentType createAttachmentType() {
		AttachmentTypeImpl attachmentType = new AttachmentTypeImpl();
		return attachmentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefinitionType createDefinitionType() {
		DefinitionTypeImpl definitionType = new DefinitionTypeImpl();
		return definitionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmailType createEmailType() {
		EmailTypeImpl emailType = new EmailTypeImpl();
		return emailType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ErrorType createErrorType() {
		ErrorTypeImpl errorType = new ErrorTypeImpl();
		return errorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FilesType createFilesType() {
		FilesTypeImpl filesType = new FilesTypeImpl();
		return filesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FromType createFromType() {
		FromTypeImpl fromType = new FromTypeImpl();
		return fromType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SMTPType createSMTPType() {
		SMTPTypeImpl smtpType = new SMTPTypeImpl();
		return smtpType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextMappingType createTextMappingType() {
		TextMappingTypeImpl textMappingType = new TextMappingTypeImpl();
		return textMappingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationType createConfigurationTypeFromString(
			EDataType eDataType, String initialValue) {
		ConfigurationType result = ConfigurationType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertConfigurationTypeToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationType createConfigurationTypeObjectFromString(
			EDataType eDataType, String initialValue) {
		return (ConfigurationType) createConfigurationTypeFromString(
				EmailPackage.Literals.CONFIGURATION_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertConfigurationTypeObjectToString(EDataType eDataType,
			Object instanceValue) {
		return convertConfigurationTypeToString(
				EmailPackage.Literals.CONFIGURATION_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createParameterTypeFromString(EDataType eDataType,
			String initialValue) {
		return (String) XMLTypeFactory.eINSTANCE.createFromString(
				XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertParameterTypeToString(EDataType eDataType,
			Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(
				XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmailPackage getEmailPackage() {
		return (EmailPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static EmailPackage getPackage() {
		return EmailPackage.eINSTANCE;
	}

} //EmailFactoryImpl
