/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.DataMapping;

import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transform Script</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 *  Model for Transform Script Editor bound for iProcess.(currently not bound to the BPM destination and probably never will be).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.TransformScript#getDataMappings <em>Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.TransformScript#getInputDom <em>Input Dom</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.TransformScript#getOutputDom <em>Output Dom</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getTransformScript()
 * @model extendedMetaData="name='TransformScript' kind='elementOnly'"
 * @generated
 */
public interface TransformScript extends ExtendedAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Data Mappings</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.DataMapping}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Data Mappings</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Data Mappings</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getTransformScript_DataMappings()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DataMapping' namespace='##targetNamespace' wrap='DataMappings'"
     * @generated
     */
    EList<DataMapping> getDataMappings();

    /**
     * Returns the value of the '<em><b>Input Dom</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This attribute stores the input document for the transformation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Input Dom</em>' attribute list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getTransformScript_InputDom()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='elementOnly' name='InputDom' namespace='##targetNamespace'"
     * @generated
     */
    EList<String> getInputDom();

    /**
     * Returns the value of the '<em><b>Output Dom</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This attribute stores the output document for the transformation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Output Dom</em>' attribute list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getTransformScript_OutputDom()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='elementOnly' name='OutputDom' namespace='##targetNamespace'"
     * @generated
     */
    EList<String> getOutputDom();

} // TransformScript
