/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception;

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
 * @see com.tibco.n2.common.api.exception.ExceptionFactory
 * @model kind="package"
 * @generated
 */
public interface ExceptionPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "exception";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://exception.api.common.n2.tibco.com";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "exception";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ExceptionPackage eINSTANCE = com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.n2.common.api.exception.impl.DeploymentParameterFaultTypeImpl <em>Deployment Parameter Fault Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.api.exception.impl.DeploymentParameterFaultTypeImpl
     * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getDeploymentParameterFaultType()
     * @generated
     */
    int DEPLOYMENT_PARAMETER_FAULT_TYPE = 0;

    /**
     * The feature id for the '<em><b>Error</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DEPLOYMENT_PARAMETER_FAULT_TYPE__ERROR = 0;

    /**
     * The number of structural features of the '<em>Deployment Parameter Fault Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DEPLOYMENT_PARAMETER_FAULT_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.api.exception.impl.ErrorLineImpl <em>Error Line</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.api.exception.impl.ErrorLineImpl
     * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getErrorLine()
     * @generated
     */
    int ERROR_LINE = 3;

    /**
     * The feature id for the '<em><b>Parameter</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_LINE__PARAMETER = 0;

    /**
     * The feature id for the '<em><b>Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_LINE__CODE = 1;

    /**
     * The feature id for the '<em><b>Message</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_LINE__MESSAGE = 2;

    /**
     * The number of structural features of the '<em>Error Line</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_LINE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.api.exception.impl.DetailedErrorLineImpl <em>Detailed Error Line</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.api.exception.impl.DetailedErrorLineImpl
     * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getDetailedErrorLine()
     * @generated
     */
    int DETAILED_ERROR_LINE = 1;

    /**
     * The feature id for the '<em><b>Parameter</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DETAILED_ERROR_LINE__PARAMETER = ERROR_LINE__PARAMETER;

    /**
     * The feature id for the '<em><b>Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DETAILED_ERROR_LINE__CODE = ERROR_LINE__CODE;

    /**
     * The feature id for the '<em><b>Message</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DETAILED_ERROR_LINE__MESSAGE = ERROR_LINE__MESSAGE;

    /**
     * The feature id for the '<em><b>Column Number</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DETAILED_ERROR_LINE__COLUMN_NUMBER = ERROR_LINE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Line Number</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DETAILED_ERROR_LINE__LINE_NUMBER = ERROR_LINE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Severity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DETAILED_ERROR_LINE__SEVERITY = ERROR_LINE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Detailed Error Line</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DETAILED_ERROR_LINE_FEATURE_COUNT = ERROR_LINE_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.api.exception.impl.DetailedExceptionImpl <em>Detailed Exception</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.api.exception.impl.DetailedExceptionImpl
     * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getDetailedException()
     * @generated
     */
    int DETAILED_EXCEPTION = 2;

    /**
     * The feature id for the '<em><b>Error</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DETAILED_EXCEPTION__ERROR = 0;

    /**
     * The number of structural features of the '<em>Detailed Exception</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DETAILED_EXCEPTION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.api.exception.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.api.exception.impl.DocumentRootImpl
     * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 4;

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
     * The feature id for the '<em><b>Deployment Parameter Fault</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__DEPLOYMENT_PARAMETER_FAULT = 3;

    /**
     * The feature id for the '<em><b>Internal Service Fault</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__INTERNAL_SERVICE_FAULT = 4;

    /**
     * The feature id for the '<em><b>Invalid Work Type Fault</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__INVALID_WORK_TYPE_FAULT = 5;

    /**
     * The feature id for the '<em><b>Work Type Fault</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__WORK_TYPE_FAULT = 6;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 7;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.api.exception.impl.InternalServiceFaultTypeImpl <em>Internal Service Fault Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.api.exception.impl.InternalServiceFaultTypeImpl
     * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getInternalServiceFaultType()
     * @generated
     */
    int INTERNAL_SERVICE_FAULT_TYPE = 5;

    /**
     * The feature id for the '<em><b>Error</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERNAL_SERVICE_FAULT_TYPE__ERROR = 0;

    /**
     * The number of structural features of the '<em>Internal Service Fault Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INTERNAL_SERVICE_FAULT_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.api.exception.impl.InvalidWorkTypeFaultTypeImpl <em>Invalid Work Type Fault Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.api.exception.impl.InvalidWorkTypeFaultTypeImpl
     * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getInvalidWorkTypeFaultType()
     * @generated
     */
    int INVALID_WORK_TYPE_FAULT_TYPE = 6;

    /**
     * The feature id for the '<em><b>Error</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INVALID_WORK_TYPE_FAULT_TYPE__ERROR = 0;

    /**
     * The number of structural features of the '<em>Invalid Work Type Fault Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INVALID_WORK_TYPE_FAULT_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.api.exception.impl.WorkTypeFaultTypeImpl <em>Work Type Fault Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.api.exception.impl.WorkTypeFaultTypeImpl
     * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getWorkTypeFaultType()
     * @generated
     */
    int WORK_TYPE_FAULT_TYPE = 7;

    /**
     * The feature id for the '<em><b>Error</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_FAULT_TYPE__ERROR = 0;

    /**
     * The number of structural features of the '<em>Work Type Fault Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_TYPE_FAULT_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.api.exception.SeverityType <em>Severity Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.api.exception.SeverityType
     * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getSeverityType()
     * @generated
     */
    int SEVERITY_TYPE = 8;

    /**
     * The meta object id for the '<em>Severity Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.api.exception.SeverityType
     * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getSeverityTypeObject()
     * @generated
     */
    int SEVERITY_TYPE_OBJECT = 9;


    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.api.exception.DeploymentParameterFaultType <em>Deployment Parameter Fault Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Deployment Parameter Fault Type</em>'.
     * @see com.tibco.n2.common.api.exception.DeploymentParameterFaultType
     * @generated
     */
    EClass getDeploymentParameterFaultType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.common.api.exception.DeploymentParameterFaultType#getError <em>Error</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Error</em>'.
     * @see com.tibco.n2.common.api.exception.DeploymentParameterFaultType#getError()
     * @see #getDeploymentParameterFaultType()
     * @generated
     */
    EReference getDeploymentParameterFaultType_Error();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.api.exception.DetailedErrorLine <em>Detailed Error Line</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Detailed Error Line</em>'.
     * @see com.tibco.n2.common.api.exception.DetailedErrorLine
     * @generated
     */
    EClass getDetailedErrorLine();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getColumnNumber <em>Column Number</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Column Number</em>'.
     * @see com.tibco.n2.common.api.exception.DetailedErrorLine#getColumnNumber()
     * @see #getDetailedErrorLine()
     * @generated
     */
    EAttribute getDetailedErrorLine_ColumnNumber();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getLineNumber <em>Line Number</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Line Number</em>'.
     * @see com.tibco.n2.common.api.exception.DetailedErrorLine#getLineNumber()
     * @see #getDetailedErrorLine()
     * @generated
     */
    EAttribute getDetailedErrorLine_LineNumber();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.api.exception.DetailedErrorLine#getSeverity <em>Severity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Severity</em>'.
     * @see com.tibco.n2.common.api.exception.DetailedErrorLine#getSeverity()
     * @see #getDetailedErrorLine()
     * @generated
     */
    EAttribute getDetailedErrorLine_Severity();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.api.exception.DetailedException <em>Detailed Exception</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Detailed Exception</em>'.
     * @see com.tibco.n2.common.api.exception.DetailedException
     * @generated
     */
    EClass getDetailedException();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.common.api.exception.DetailedException#getError <em>Error</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Error</em>'.
     * @see com.tibco.n2.common.api.exception.DetailedException#getError()
     * @see #getDetailedException()
     * @generated
     */
    EReference getDetailedException_Error();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.api.exception.ErrorLine <em>Error Line</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Error Line</em>'.
     * @see com.tibco.n2.common.api.exception.ErrorLine
     * @generated
     */
    EClass getErrorLine();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.common.api.exception.ErrorLine#getParameter <em>Parameter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Parameter</em>'.
     * @see com.tibco.n2.common.api.exception.ErrorLine#getParameter()
     * @see #getErrorLine()
     * @generated
     */
    EAttribute getErrorLine_Parameter();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.api.exception.ErrorLine#getCode <em>Code</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Code</em>'.
     * @see com.tibco.n2.common.api.exception.ErrorLine#getCode()
     * @see #getErrorLine()
     * @generated
     */
    EAttribute getErrorLine_Code();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.api.exception.ErrorLine#getMessage <em>Message</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Message</em>'.
     * @see com.tibco.n2.common.api.exception.ErrorLine#getMessage()
     * @see #getErrorLine()
     * @generated
     */
    EAttribute getErrorLine_Message();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.api.exception.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.n2.common.api.exception.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.common.api.exception.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.n2.common.api.exception.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.common.api.exception.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.n2.common.api.exception.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.common.api.exception.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.n2.common.api.exception.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.api.exception.DocumentRoot#getDeploymentParameterFault <em>Deployment Parameter Fault</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Deployment Parameter Fault</em>'.
     * @see com.tibco.n2.common.api.exception.DocumentRoot#getDeploymentParameterFault()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_DeploymentParameterFault();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.api.exception.DocumentRoot#getInternalServiceFault <em>Internal Service Fault</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Internal Service Fault</em>'.
     * @see com.tibco.n2.common.api.exception.DocumentRoot#getInternalServiceFault()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_InternalServiceFault();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.api.exception.DocumentRoot#getInvalidWorkTypeFault <em>Invalid Work Type Fault</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Invalid Work Type Fault</em>'.
     * @see com.tibco.n2.common.api.exception.DocumentRoot#getInvalidWorkTypeFault()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_InvalidWorkTypeFault();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.api.exception.DocumentRoot#getWorkTypeFault <em>Work Type Fault</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Type Fault</em>'.
     * @see com.tibco.n2.common.api.exception.DocumentRoot#getWorkTypeFault()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_WorkTypeFault();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.api.exception.InternalServiceFaultType <em>Internal Service Fault Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Internal Service Fault Type</em>'.
     * @see com.tibco.n2.common.api.exception.InternalServiceFaultType
     * @generated
     */
    EClass getInternalServiceFaultType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.api.exception.InternalServiceFaultType#getError <em>Error</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Error</em>'.
     * @see com.tibco.n2.common.api.exception.InternalServiceFaultType#getError()
     * @see #getInternalServiceFaultType()
     * @generated
     */
    EReference getInternalServiceFaultType_Error();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.api.exception.InvalidWorkTypeFaultType <em>Invalid Work Type Fault Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Invalid Work Type Fault Type</em>'.
     * @see com.tibco.n2.common.api.exception.InvalidWorkTypeFaultType
     * @generated
     */
    EClass getInvalidWorkTypeFaultType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.api.exception.InvalidWorkTypeFaultType#getError <em>Error</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Error</em>'.
     * @see com.tibco.n2.common.api.exception.InvalidWorkTypeFaultType#getError()
     * @see #getInvalidWorkTypeFaultType()
     * @generated
     */
    EReference getInvalidWorkTypeFaultType_Error();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.api.exception.WorkTypeFaultType <em>Work Type Fault Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Type Fault Type</em>'.
     * @see com.tibco.n2.common.api.exception.WorkTypeFaultType
     * @generated
     */
    EClass getWorkTypeFaultType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.api.exception.WorkTypeFaultType#getError <em>Error</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Error</em>'.
     * @see com.tibco.n2.common.api.exception.WorkTypeFaultType#getError()
     * @see #getWorkTypeFaultType()
     * @generated
     */
    EReference getWorkTypeFaultType_Error();

    /**
     * Returns the meta object for enum '{@link com.tibco.n2.common.api.exception.SeverityType <em>Severity Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Severity Type</em>'.
     * @see com.tibco.n2.common.api.exception.SeverityType
     * @generated
     */
    EEnum getSeverityType();

    /**
     * Returns the meta object for data type '{@link com.tibco.n2.common.api.exception.SeverityType <em>Severity Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Severity Type Object</em>'.
     * @see com.tibco.n2.common.api.exception.SeverityType
     * @model instanceClass="com.tibco.n2.common.api.exception.SeverityType"
     *        extendedMetaData="name='severity_._type:Object' baseType='severity_._type'"
     * @generated
     */
    EDataType getSeverityTypeObject();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ExceptionFactory getExceptionFactory();

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
         * The meta object literal for the '{@link com.tibco.n2.common.api.exception.impl.DeploymentParameterFaultTypeImpl <em>Deployment Parameter Fault Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.api.exception.impl.DeploymentParameterFaultTypeImpl
         * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getDeploymentParameterFaultType()
         * @generated
         */
        EClass DEPLOYMENT_PARAMETER_FAULT_TYPE = eINSTANCE.getDeploymentParameterFaultType();

        /**
         * The meta object literal for the '<em><b>Error</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DEPLOYMENT_PARAMETER_FAULT_TYPE__ERROR = eINSTANCE.getDeploymentParameterFaultType_Error();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.api.exception.impl.DetailedErrorLineImpl <em>Detailed Error Line</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.api.exception.impl.DetailedErrorLineImpl
         * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getDetailedErrorLine()
         * @generated
         */
        EClass DETAILED_ERROR_LINE = eINSTANCE.getDetailedErrorLine();

        /**
         * The meta object literal for the '<em><b>Column Number</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DETAILED_ERROR_LINE__COLUMN_NUMBER = eINSTANCE.getDetailedErrorLine_ColumnNumber();

        /**
         * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DETAILED_ERROR_LINE__LINE_NUMBER = eINSTANCE.getDetailedErrorLine_LineNumber();

        /**
         * The meta object literal for the '<em><b>Severity</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DETAILED_ERROR_LINE__SEVERITY = eINSTANCE.getDetailedErrorLine_Severity();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.api.exception.impl.DetailedExceptionImpl <em>Detailed Exception</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.api.exception.impl.DetailedExceptionImpl
         * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getDetailedException()
         * @generated
         */
        EClass DETAILED_EXCEPTION = eINSTANCE.getDetailedException();

        /**
         * The meta object literal for the '<em><b>Error</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DETAILED_EXCEPTION__ERROR = eINSTANCE.getDetailedException_Error();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.api.exception.impl.ErrorLineImpl <em>Error Line</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.api.exception.impl.ErrorLineImpl
         * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getErrorLine()
         * @generated
         */
        EClass ERROR_LINE = eINSTANCE.getErrorLine();

        /**
         * The meta object literal for the '<em><b>Parameter</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_LINE__PARAMETER = eINSTANCE.getErrorLine_Parameter();

        /**
         * The meta object literal for the '<em><b>Code</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_LINE__CODE = eINSTANCE.getErrorLine_Code();

        /**
         * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_LINE__MESSAGE = eINSTANCE.getErrorLine_Message();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.api.exception.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.api.exception.impl.DocumentRootImpl
         * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getDocumentRoot()
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
        EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

        /**
         * The meta object literal for the '<em><b>Deployment Parameter Fault</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__DEPLOYMENT_PARAMETER_FAULT = eINSTANCE.getDocumentRoot_DeploymentParameterFault();

        /**
         * The meta object literal for the '<em><b>Internal Service Fault</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__INTERNAL_SERVICE_FAULT = eINSTANCE.getDocumentRoot_InternalServiceFault();

        /**
         * The meta object literal for the '<em><b>Invalid Work Type Fault</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__INVALID_WORK_TYPE_FAULT = eINSTANCE.getDocumentRoot_InvalidWorkTypeFault();

        /**
         * The meta object literal for the '<em><b>Work Type Fault</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__WORK_TYPE_FAULT = eINSTANCE.getDocumentRoot_WorkTypeFault();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.api.exception.impl.InternalServiceFaultTypeImpl <em>Internal Service Fault Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.api.exception.impl.InternalServiceFaultTypeImpl
         * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getInternalServiceFaultType()
         * @generated
         */
        EClass INTERNAL_SERVICE_FAULT_TYPE = eINSTANCE.getInternalServiceFaultType();

        /**
         * The meta object literal for the '<em><b>Error</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INTERNAL_SERVICE_FAULT_TYPE__ERROR = eINSTANCE.getInternalServiceFaultType_Error();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.api.exception.impl.InvalidWorkTypeFaultTypeImpl <em>Invalid Work Type Fault Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.api.exception.impl.InvalidWorkTypeFaultTypeImpl
         * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getInvalidWorkTypeFaultType()
         * @generated
         */
        EClass INVALID_WORK_TYPE_FAULT_TYPE = eINSTANCE.getInvalidWorkTypeFaultType();

        /**
         * The meta object literal for the '<em><b>Error</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INVALID_WORK_TYPE_FAULT_TYPE__ERROR = eINSTANCE.getInvalidWorkTypeFaultType_Error();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.api.exception.impl.WorkTypeFaultTypeImpl <em>Work Type Fault Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.api.exception.impl.WorkTypeFaultTypeImpl
         * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getWorkTypeFaultType()
         * @generated
         */
        EClass WORK_TYPE_FAULT_TYPE = eINSTANCE.getWorkTypeFaultType();

        /**
         * The meta object literal for the '<em><b>Error</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_TYPE_FAULT_TYPE__ERROR = eINSTANCE.getWorkTypeFaultType_Error();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.api.exception.SeverityType <em>Severity Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.api.exception.SeverityType
         * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getSeverityType()
         * @generated
         */
        EEnum SEVERITY_TYPE = eINSTANCE.getSeverityType();

        /**
         * The meta object literal for the '<em>Severity Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.api.exception.SeverityType
         * @see com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl#getSeverityTypeObject()
         * @generated
         */
        EDataType SEVERITY_TYPE_OBJECT = eINSTANCE.getSeverityTypeObject();

    }

} //ExceptionPackage
