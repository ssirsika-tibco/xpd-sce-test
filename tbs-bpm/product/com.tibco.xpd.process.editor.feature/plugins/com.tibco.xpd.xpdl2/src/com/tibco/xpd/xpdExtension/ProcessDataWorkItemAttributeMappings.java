/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Data Work Item Attribute Mappings</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Container for Process Data and Work Item Attribute mapping, which is used at Process level to define which Work Item Attribute a Process Data is mapped to.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings#getDataWorkItemAttributeMapping <em>Data Work Item Attribute Mapping</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getProcessDataWorkItemAttributeMappings()
 * @model extendedMetaData="name='ProcessDataWorkItemAttributeMappings_._type' kind='elementOnly'"
 * @generated
 */
public interface ProcessDataWorkItemAttributeMappings extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Data Work Item Attribute Mapping</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Data Work Item Attribute Mapping</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Data Work Item Attribute Mapping</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getProcessDataWorkItemAttributeMappings_DataWorkItemAttributeMapping()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DataWorkItemAttributeMapping' namespace='##targetNamespace'"
     * @generated
     */
    EList<DataWorkItemAttributeMapping> getDataWorkItemAttributeMapping();

} // ProcessDataWorkItemAttributeMappings
