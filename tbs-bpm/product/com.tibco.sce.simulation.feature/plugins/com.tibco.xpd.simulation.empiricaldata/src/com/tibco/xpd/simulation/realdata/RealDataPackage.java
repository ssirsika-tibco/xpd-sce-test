/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.realdata;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see com.tibco.xpd.simulation.realdata.RealDataFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface RealDataPackage extends EPackage {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "realdata"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "file:/E:/XPD/RealData.xsd"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "RealData"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RealDataPackage eINSTANCE = com.tibco.xpd.simulation.realdata.impl.RealDataPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.realdata.impl.CaseImpl <em>Case</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.realdata.impl.CaseImpl
	 * @see com.tibco.xpd.simulation.realdata.impl.RealDataPackageImpl#getCase()
	 * @generated
	 */
	int CASE = 0;

	/**
	 * The feature id for the '<em><b>Paramter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE__PARAMTER = 0;

	/**
	 * The feature id for the '<em><b>Start Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE__START_TIME = 1;

	/**
	 * The number of structural features of the '<em>Case</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.realdata.impl.CasesImpl <em>Cases</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.realdata.impl.CasesImpl
	 * @see com.tibco.xpd.simulation.realdata.impl.RealDataPackageImpl#getCases()
	 * @generated
	 */
	int CASES = 1;

	/**
	 * The feature id for the '<em><b>Case</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASES__CASE = 0;

	/**
	 * The number of structural features of the '<em>Cases</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.realdata.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.realdata.impl.DocumentRootImpl
	 * @see com.tibco.xpd.simulation.realdata.impl.RealDataPackageImpl#getDocumentRoot()
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
	 * The feature id for the '<em><b>Cases</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CASES = 3;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.xpd.simulation.realdata.impl.ParamterImpl <em>Paramter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.xpd.simulation.realdata.impl.ParamterImpl
	 * @see com.tibco.xpd.simulation.realdata.impl.RealDataPackageImpl#getParamter()
	 * @generated
	 */
	int PARAMTER = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMTER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMTER__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Paramter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMTER_FEATURE_COUNT = 2;

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.simulation.realdata.Case <em>Case</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Case</em>'.
	 * @see com.tibco.xpd.simulation.realdata.Case
	 * @generated
	 */
	EClass getCase();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.xpd.simulation.realdata.Case#getParamter <em>Paramter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Paramter</em>'.
	 * @see com.tibco.xpd.simulation.realdata.Case#getParamter()
	 * @see #getCase()
	 * @generated
	 */
	EReference getCase_Paramter();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.realdata.Case#getStartTime <em>Start Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Time</em>'.
	 * @see com.tibco.xpd.simulation.realdata.Case#getStartTime()
	 * @see #getCase()
	 * @generated
	 */
	EAttribute getCase_StartTime();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.simulation.realdata.Cases <em>Cases</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cases</em>'.
	 * @see com.tibco.xpd.simulation.realdata.Cases
	 * @generated
	 */
	EClass getCases();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.xpd.simulation.realdata.Cases#getCase <em>Case</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Case</em>'.
	 * @see com.tibco.xpd.simulation.realdata.Cases#getCase()
	 * @see #getCases()
	 * @generated
	 */
	EReference getCases_Case();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.simulation.realdata.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.tibco.xpd.simulation.realdata.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.xpd.simulation.realdata.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.xpd.simulation.realdata.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.xpd.simulation.realdata.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.xpd.simulation.realdata.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.xpd.simulation.realdata.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.xpd.simulation.realdata.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.xpd.simulation.realdata.DocumentRoot#getCases <em>Cases</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cases</em>'.
	 * @see com.tibco.xpd.simulation.realdata.DocumentRoot#getCases()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Cases();

	/**
	 * Returns the meta object for class '{@link com.tibco.xpd.simulation.realdata.Paramter <em>Paramter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Paramter</em>'.
	 * @see com.tibco.xpd.simulation.realdata.Paramter
	 * @generated
	 */
	EClass getParamter();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.realdata.Paramter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.xpd.simulation.realdata.Paramter#getName()
	 * @see #getParamter()
	 * @generated
	 */
	EAttribute getParamter_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.xpd.simulation.realdata.Paramter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.xpd.simulation.realdata.Paramter#getValue()
	 * @see #getParamter()
	 * @generated
	 */
	EAttribute getParamter_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RealDataFactory getRealDataFactory();

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
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.realdata.impl.CaseImpl <em>Case</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.realdata.impl.CaseImpl
		 * @see com.tibco.xpd.simulation.realdata.impl.RealDataPackageImpl#getCase()
		 * @generated
		 */
		EClass CASE = eINSTANCE.getCase();

		/**
		 * The meta object literal for the '<em><b>Paramter</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASE__PARAMTER = eINSTANCE.getCase_Paramter();

		/**
		 * The meta object literal for the '<em><b>Start Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CASE__START_TIME = eINSTANCE.getCase_StartTime();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.realdata.impl.CasesImpl <em>Cases</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.realdata.impl.CasesImpl
		 * @see com.tibco.xpd.simulation.realdata.impl.RealDataPackageImpl#getCases()
		 * @generated
		 */
		EClass CASES = eINSTANCE.getCases();

		/**
		 * The meta object literal for the '<em><b>Case</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASES__CASE = eINSTANCE.getCases_Case();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.realdata.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.realdata.impl.DocumentRootImpl
		 * @see com.tibco.xpd.simulation.realdata.impl.RealDataPackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>Cases</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CASES = eINSTANCE.getDocumentRoot_Cases();

		/**
		 * The meta object literal for the '{@link com.tibco.xpd.simulation.realdata.impl.ParamterImpl <em>Paramter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.xpd.simulation.realdata.impl.ParamterImpl
		 * @see com.tibco.xpd.simulation.realdata.impl.RealDataPackageImpl#getParamter()
		 * @generated
		 */
		EClass PARAMTER = eINSTANCE.getParamter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMTER__NAME = eINSTANCE.getParamter_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMTER__VALUE = eINSTANCE.getParamter_Value();

	}

} //RealDataPackage
