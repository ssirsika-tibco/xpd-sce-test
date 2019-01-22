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
 * A representation of the model object '<em><b>Loop</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Loop#getLoopStandard <em>Loop Standard</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Loop#getLoopMultiInstance <em>Loop Multi Instance</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Loop#getLoopType <em>Loop Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Loop#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoop()
 * @model extendedMetaData="name='Loop_._type' kind='elementOnly'"
 * @generated
 */
public interface Loop extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Loop Standard</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Loop Standard</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Loop Standard</em>' containment reference.
     * @see #setLoopStandard(LoopStandard)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoop_LoopStandard()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='LoopStandard' namespace='##targetNamespace'"
     * @generated
     */
    LoopStandard getLoopStandard();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Loop#getLoopStandard <em>Loop Standard</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Loop Standard</em>' containment reference.
     * @see #getLoopStandard()
     * @generated
     */
    void setLoopStandard(LoopStandard value);

    /**
     * Returns the value of the '<em><b>Loop Multi Instance</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Loop Multi Instance</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Loop Multi Instance</em>' containment reference.
     * @see #setLoopMultiInstance(LoopMultiInstance)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoop_LoopMultiInstance()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='LoopMultiInstance' namespace='##targetNamespace'"
     * @generated
     */
    LoopMultiInstance getLoopMultiInstance();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Loop#getLoopMultiInstance <em>Loop Multi Instance</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Loop Multi Instance</em>' containment reference.
     * @see #getLoopMultiInstance()
     * @generated
     */
    void setLoopMultiInstance(LoopMultiInstance value);

    /**
     * Returns the value of the '<em><b>Loop Type</b></em>' attribute.
     * The default value is <code>"Standard"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.LoopType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Loop Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Loop Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.LoopType
     * @see #isSetLoopType()
     * @see #unsetLoopType()
     * @see #setLoopType(LoopType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoop_LoopType()
     * @model default="Standard" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='LoopType'"
     * @generated
     */
    LoopType getLoopType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Loop#getLoopType <em>Loop Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Loop Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.LoopType
     * @see #isSetLoopType()
     * @see #unsetLoopType()
     * @see #getLoopType()
     * @generated
     */
    void setLoopType(LoopType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Loop#getLoopType <em>Loop Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetLoopType()
     * @see #getLoopType()
     * @see #setLoopType(LoopType)
     * @generated
     */
    void unsetLoopType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Loop#getLoopType <em>Loop Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Loop Type</em>' attribute is set.
     * @see #unsetLoopType()
     * @see #getLoopType()
     * @see #setLoopType(LoopType)
     * @generated
     */
    boolean isSetLoopType();

    /**
     * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Any Attribute</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Any Attribute</em>' attribute list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getLoop_AnyAttribute()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':3' processing='lax'"
     * @generated
     */
    FeatureMap getAnyAttribute();

} // Loop
