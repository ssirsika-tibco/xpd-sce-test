/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cac Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a CAC Type (Case Access Class).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.script.descriptor.CacType#getScriptingName <em>Scripting Name</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.CacType#getCanonicalClassName <em>Canonical Class Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getCacType()
 * @model extendedMetaData="name='CacType' kind='elementOnly'"
 * @generated
 */
public interface CacType extends EObject {
    /**
     * Returns the value of the '<em><b>Scripting Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Name that will be used to refer to the Case Access Class from within the script. For example: cac_com_example_claimmodel_Claim. 
     * <!-- end-model-doc -->
     * @return the value of the '<em>Scripting Name</em>' attribute.
     * @see #setScriptingName(String)
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getCacType_ScriptingName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='scriptingName'"
     * @generated
     */
    String getScriptingName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.script.descriptor.CacType#getScriptingName <em>Scripting Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Scripting Name</em>' attribute.
     * @see #getScriptingName()
     * @generated
     */
    void setScriptingName(String value);

    /**
     * Returns the value of the '<em><b>Canonical Class Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Fully qualified class name. For example: com.example.claimmodel.si.cac.ClaimCAC.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Canonical Class Name</em>' attribute.
     * @see #setCanonicalClassName(String)
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getCacType_CanonicalClassName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='canonicalClassName'"
     * @generated
     */
    String getCanonicalClassName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.script.descriptor.CacType#getCanonicalClassName <em>Canonical Class Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Canonical Class Name</em>' attribute.
     * @see #getCanonicalClassName()
     * @generated
     */
    void setCanonicalClassName(String value);

} // CacType
