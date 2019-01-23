/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.email;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailFactory
 * @model kind="package"
 *        extendedMetaData="qualified='true'"
 * @generated
 */
public interface EmailPackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved."; //$NON-NLS-1$

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "email"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.tibco.com/XPD/email1.0.0"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "email"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EmailPackage eINSTANCE = com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.AttachmentTypeImpl <em>Attachment Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.AttachmentTypeImpl
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getAttachmentType()
	 * @generated
	 */
	int ATTACHMENT_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTACHMENT_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTACHMENT_TYPE__FILES = 1;

	/**
	 * The number of structural features of the '<em>Attachment Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTACHMENT_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DefinitionTypeImpl <em>Definition Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DefinitionTypeImpl
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getDefinitionType()
	 * @generated
	 */
	int DEFINITION_TYPE = 1;

	/**
	 * The feature id for the '<em><b>From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINITION_TYPE__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINITION_TYPE__TO = 1;

	/**
	 * The feature id for the '<em><b>Cc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINITION_TYPE__CC = 2;

	/**
	 * The feature id for the '<em><b>Bcc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINITION_TYPE__BCC = 3;

	/**
	 * The feature id for the '<em><b>Reply To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINITION_TYPE__REPLY_TO = 4;

	/**
	 * The feature id for the '<em><b>Headers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINITION_TYPE__HEADERS = 5;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINITION_TYPE__PRIORITY = 6;

	/**
	 * The feature id for the '<em><b>Subject</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINITION_TYPE__SUBJECT = 7;

	/**
	 * The number of structural features of the '<em>Definition Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFINITION_TYPE_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DocumentRootImpl
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 2;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Email</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EMAIL = 3;

	/**
	 * The feature id for the '<em><b>Configuration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONFIGURATION = 4;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailTypeImpl
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getEmailType()
	 * @generated
	 */
	int EMAIL_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_TYPE__DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_TYPE__BODY = 1;

	/**
	 * The feature id for the '<em><b>Attachment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_TYPE__ATTACHMENT = 2;

	/**
	 * The feature id for the '<em><b>SMTP</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_TYPE__SMTP = 3;

	/**
	 * The feature id for the '<em><b>Error</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_TYPE__ERROR = 4;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMAIL_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.ErrorTypeImpl <em>Error Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.ErrorTypeImpl
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getErrorType()
	 * @generated
	 */
	int ERROR_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Return Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_TYPE__RETURN_CODE = 0;

	/**
	 * The feature id for the '<em><b>Return Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_TYPE__RETURN_MESSAGE = 1;

	/**
	 * The number of structural features of the '<em>Error Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.FilesTypeImpl <em>Files Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.FilesTypeImpl
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getFilesType()
	 * @generated
	 */
	int FILES_TYPE = 5;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILES_TYPE__FILE = 0;

	/**
	 * The number of structural features of the '<em>Files Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILES_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.FromTypeImpl <em>From Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.FromTypeImpl
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getFromType()
	 * @generated
	 */
	int FROM_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Configuration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TYPE__CONFIGURATION = 1;

	/**
	 * The number of structural features of the '<em>From Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FROM_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.SMTPTypeImpl <em>SMTP Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.SMTPTypeImpl
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getSMTPType()
	 * @generated
	 */
	int SMTP_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Configuration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMTP_TYPE__CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Host</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMTP_TYPE__HOST = 1;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMTP_TYPE__PORT = 2;

	/**
	 * The number of structural features of the '<em>SMTP Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMTP_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.TextMappingTypeImpl <em>Text Mapping Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.TextMappingTypeImpl
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getTextMappingType()
	 * @generated
	 */
	int TEXT_MAPPING_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_MAPPING_TYPE__TEXT = 0;

	/**
	 * The number of structural features of the '<em>Text Mapping Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_MAPPING_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType <em>Configuration Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getConfigurationType()
	 * @generated
	 */
	int CONFIGURATION_TYPE = 9;

	/**
	 * The meta object id for the '<em>Configuration Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getConfigurationTypeObject()
	 * @generated
	 */
	int CONFIGURATION_TYPE_OBJECT = 10;

	/**
	 * The meta object id for the '<em>Parameter Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getParameterType()
	 * @generated
	 */
	int PARAMETER_TYPE = 11;

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType <em>Attachment Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attachment Type</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType
	 * @generated
	 */
	EClass getAttachmentType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType#getValue()
	 * @see #getAttachmentType()
	 * @generated
	 */
	EAttribute getAttachmentType_Value();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType#getFiles <em>Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Files</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType#getFiles()
	 * @see #getAttachmentType()
	 * @generated
	 */
	EReference getAttachmentType_Files();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType <em>Definition Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Definition Type</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType
	 * @generated
	 */
	EClass getDefinitionType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>From</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getFrom()
	 * @see #getDefinitionType()
	 * @generated
	 */
	EReference getDefinitionType_From();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>To</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getTo()
	 * @see #getDefinitionType()
	 * @generated
	 */
	EAttribute getDefinitionType_To();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getCc <em>Cc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cc</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getCc()
	 * @see #getDefinitionType()
	 * @generated
	 */
	EAttribute getDefinitionType_Cc();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getBcc <em>Bcc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bcc</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getBcc()
	 * @see #getDefinitionType()
	 * @generated
	 */
	EAttribute getDefinitionType_Bcc();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getReplyTo <em>Reply To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reply To</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getReplyTo()
	 * @see #getDefinitionType()
	 * @generated
	 */
	EAttribute getDefinitionType_ReplyTo();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getHeaders <em>Headers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Headers</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getHeaders()
	 * @see #getDefinitionType()
	 * @generated
	 */
	EAttribute getDefinitionType_Headers();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getPriority()
	 * @see #getDefinitionType()
	 * @generated
	 */
	EAttribute getDefinitionType_Priority();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getSubject <em>Subject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subject</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType#getSubject()
	 * @see #getDefinitionType()
	 * @generated
	 */
	EAttribute getDefinitionType_Subject();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DocumentRoot#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Email</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DocumentRoot#getEmail()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Email();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.DocumentRoot#getConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Configuration</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.DocumentRoot#getConfiguration()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Configuration();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType
	 * @generated
	 */
	EClass getEmailType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Definition</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getDefinition()
	 * @see #getEmailType()
	 * @generated
	 */
	EReference getEmailType_Definition();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Body</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getBody()
	 * @see #getEmailType()
	 * @generated
	 */
	EAttribute getEmailType_Body();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getAttachment <em>Attachment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attachment</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getAttachment()
	 * @see #getEmailType()
	 * @generated
	 */
	EReference getEmailType_Attachment();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getSMTP <em>SMTP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>SMTP</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getSMTP()
	 * @see #getEmailType()
	 * @generated
	 */
	EReference getEmailType_SMTP();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getError <em>Error</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Error</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType#getError()
	 * @see #getEmailType()
	 * @generated
	 */
	EReference getEmailType_Error();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType <em>Error Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Error Type</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType
	 * @generated
	 */
	EClass getErrorType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType#getReturnCode <em>Return Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Return Code</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType#getReturnCode()
	 * @see #getErrorType()
	 * @generated
	 */
	EAttribute getErrorType_ReturnCode();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType#getReturnMessage <em>Return Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Return Message</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.ErrorType#getReturnMessage()
	 * @see #getErrorType()
	 * @generated
	 */
	EAttribute getErrorType_ReturnMessage();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.FilesType <em>Files Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Files Type</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.FilesType
	 * @generated
	 */
	EClass getFilesType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.FilesType#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>File</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.FilesType#getFile()
	 * @see #getFilesType()
	 * @generated
	 */
	EAttribute getFilesType_File();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType <em>From Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>From Type</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType
	 * @generated
	 */
	EClass getFromType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType#getValue()
	 * @see #getFromType()
	 * @generated
	 */
	EAttribute getFromType_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType#getConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Configuration</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType#getConfiguration()
	 * @see #getFromType()
	 * @generated
	 */
	EAttribute getFromType_Configuration();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType <em>SMTP Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>SMTP Type</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType
	 * @generated
	 */
	EClass getSMTPType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Configuration</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getConfiguration()
	 * @see #getSMTPType()
	 * @generated
	 */
	EAttribute getSMTPType_Configuration();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getHost <em>Host</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Host</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getHost()
	 * @see #getSMTPType()
	 * @generated
	 */
	EAttribute getSMTPType_Host();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType#getPort()
	 * @see #getSMTPType()
	 * @generated
	 */
	EAttribute getSMTPType_Port();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.TextMappingType <em>Text Mapping Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Mapping Type</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.TextMappingType
	 * @generated
	 */
	EClass getTextMappingType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.TextMappingType#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.TextMappingType#getText()
	 * @see #getTextMappingType()
	 * @generated
	 */
	EAttribute getTextMappingType_Text();

	/**
	 * Returns the meta object for enum '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType <em>Configuration Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Configuration Type</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType
	 * @generated
	 */
	EEnum getConfigurationType();

	/**
	 * Returns the meta object for data type '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType <em>Configuration Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Configuration Type Object</em>'.
	 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType
	 * @model instanceClass="com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType"
	 *        extendedMetaData="name='Configuration_._type:Object' baseType='Configuration_._type'" 
	 * @generated
	 */
	EDataType getConfigurationTypeObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Parameter Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='ParameterType' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getParameterType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EmailFactory getEmailFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.AttachmentTypeImpl <em>Attachment Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.AttachmentTypeImpl
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getAttachmentType()
		 * @generated
		 */
		EClass ATTACHMENT_TYPE = eINSTANCE.getAttachmentType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTACHMENT_TYPE__VALUE = eINSTANCE.getAttachmentType_Value();

		/**
		 * The meta object literal for the '<em><b>Files</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTACHMENT_TYPE__FILES = eINSTANCE.getAttachmentType_Files();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DefinitionTypeImpl <em>Definition Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DefinitionTypeImpl
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getDefinitionType()
		 * @generated
		 */
		EClass DEFINITION_TYPE = eINSTANCE.getDefinitionType();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEFINITION_TYPE__FROM = eINSTANCE.getDefinitionType_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINITION_TYPE__TO = eINSTANCE.getDefinitionType_To();

		/**
		 * The meta object literal for the '<em><b>Cc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINITION_TYPE__CC = eINSTANCE.getDefinitionType_Cc();

		/**
		 * The meta object literal for the '<em><b>Bcc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINITION_TYPE__BCC = eINSTANCE.getDefinitionType_Bcc();

		/**
		 * The meta object literal for the '<em><b>Reply To</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINITION_TYPE__REPLY_TO = eINSTANCE
				.getDefinitionType_ReplyTo();

		/**
		 * The meta object literal for the '<em><b>Headers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINITION_TYPE__HEADERS = eINSTANCE
				.getDefinitionType_Headers();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINITION_TYPE__PRIORITY = eINSTANCE
				.getDefinitionType_Priority();

		/**
		 * The meta object literal for the '<em><b>Subject</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEFINITION_TYPE__SUBJECT = eINSTANCE
				.getDefinitionType_Subject();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.DocumentRootImpl
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE
				.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE
				.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EMAIL = eINSTANCE.getDocumentRoot_Email();

		/**
		 * The meta object literal for the '<em><b>Configuration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__CONFIGURATION = eINSTANCE
				.getDocumentRoot_Configuration();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailTypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailTypeImpl
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getEmailType()
		 * @generated
		 */
		EClass EMAIL_TYPE = eINSTANCE.getEmailType();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EMAIL_TYPE__DEFINITION = eINSTANCE.getEmailType_Definition();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EMAIL_TYPE__BODY = eINSTANCE.getEmailType_Body();

		/**
		 * The meta object literal for the '<em><b>Attachment</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EMAIL_TYPE__ATTACHMENT = eINSTANCE.getEmailType_Attachment();

		/**
		 * The meta object literal for the '<em><b>SMTP</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EMAIL_TYPE__SMTP = eINSTANCE.getEmailType_SMTP();

		/**
		 * The meta object literal for the '<em><b>Error</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EMAIL_TYPE__ERROR = eINSTANCE.getEmailType_Error();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.ErrorTypeImpl <em>Error Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.ErrorTypeImpl
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getErrorType()
		 * @generated
		 */
		EClass ERROR_TYPE = eINSTANCE.getErrorType();

		/**
		 * The meta object literal for the '<em><b>Return Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ERROR_TYPE__RETURN_CODE = eINSTANCE
				.getErrorType_ReturnCode();

		/**
		 * The meta object literal for the '<em><b>Return Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ERROR_TYPE__RETURN_MESSAGE = eINSTANCE
				.getErrorType_ReturnMessage();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.FilesTypeImpl <em>Files Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.FilesTypeImpl
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getFilesType()
		 * @generated
		 */
		EClass FILES_TYPE = eINSTANCE.getFilesType();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILES_TYPE__FILE = eINSTANCE.getFilesType_File();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.FromTypeImpl <em>From Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.FromTypeImpl
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getFromType()
		 * @generated
		 */
		EClass FROM_TYPE = eINSTANCE.getFromType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FROM_TYPE__VALUE = eINSTANCE.getFromType_Value();

		/**
		 * The meta object literal for the '<em><b>Configuration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FROM_TYPE__CONFIGURATION = eINSTANCE
				.getFromType_Configuration();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.SMTPTypeImpl <em>SMTP Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.SMTPTypeImpl
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getSMTPType()
		 * @generated
		 */
		EClass SMTP_TYPE = eINSTANCE.getSMTPType();

		/**
		 * The meta object literal for the '<em><b>Configuration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SMTP_TYPE__CONFIGURATION = eINSTANCE
				.getSMTPType_Configuration();

		/**
		 * The meta object literal for the '<em><b>Host</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SMTP_TYPE__HOST = eINSTANCE.getSMTPType_Host();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SMTP_TYPE__PORT = eINSTANCE.getSMTPType_Port();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.TextMappingTypeImpl <em>Text Mapping Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.TextMappingTypeImpl
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getTextMappingType()
		 * @generated
		 */
		EClass TEXT_MAPPING_TYPE = eINSTANCE.getTextMappingType();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_MAPPING_TYPE__TEXT = eINSTANCE
				.getTextMappingType_Text();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType <em>Configuration Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getConfigurationType()
		 * @generated
		 */
		EEnum CONFIGURATION_TYPE = eINSTANCE.getConfigurationType();

		/**
		 * The meta object literal for the '<em>Configuration Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getConfigurationTypeObject()
		 * @generated
		 */
		EDataType CONFIGURATION_TYPE_OBJECT = eINSTANCE
				.getConfigurationTypeObject();

		/**
		 * The meta object literal for the '<em>Parameter Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.tibco.xpd.implementer.nativeservices.emailservice.email.impl.EmailPackageImpl#getParameterType()
		 * @generated
		 */
		EDataType PARAMETER_TYPE = eINSTANCE.getParameterType();

	}

} //EmailPackage
