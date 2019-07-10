/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Other Elements Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.OtherElementsContainer#getOtherElements <em>Other Elements</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getOtherElementsContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface OtherElementsContainer extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Other Elements</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Other Elements</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Other Elements</em>' attribute list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getOtherElementsContainer_OtherElements()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='elementWildcard' wildcards='##other' processing='lax' name='OtherElements'"
     * @generated
     */
    FeatureMap getOtherElements();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model elementNameDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    EObject getOtherElement(String elementName);

} // OtherElementsContainer