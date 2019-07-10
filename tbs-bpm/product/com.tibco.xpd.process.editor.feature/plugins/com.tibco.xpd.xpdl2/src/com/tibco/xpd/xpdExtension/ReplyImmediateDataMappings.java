/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.DataMapping;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reply Immediate Data Mappings</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * ReplyImmediatelyDataMapping element helps configure the mapping of output process id when a start event is configured to 'reply immediately with process id'. This element is added to the xpdl element "Message".
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings#getDataMappings <em>Data Mappings</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getReplyImmediateDataMappings()
 * @model extendedMetaData="name='ReplyImmediateDataMappings' kind='empty'"
 * @generated
 */
public interface ReplyImmediateDataMappings extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

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
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getReplyImmediateDataMappings_DataMappings()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DataMapping' namespace='##targetNamespace'"
     * @generated
     */
    EList<DataMapping> getDataMappings();

} // ReplyImmediateDataMappings
