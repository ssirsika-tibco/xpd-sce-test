/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Fields Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.DataFieldsContainer#getDataFields <em>Data Fields</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataFieldsContainer()
 * @model
 * @generated
 */
public interface DataFieldsContainer extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Data Fields</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.DataField}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Data Fields</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Data Fields</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataFieldsContainer_DataFields()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DataField' namespace='##targetNamespace' wrap='DataFields'"
     * @generated
     */
    EList<DataField> getDataFields();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    DataField getDataField(String id);

} // DataFieldsContainer
