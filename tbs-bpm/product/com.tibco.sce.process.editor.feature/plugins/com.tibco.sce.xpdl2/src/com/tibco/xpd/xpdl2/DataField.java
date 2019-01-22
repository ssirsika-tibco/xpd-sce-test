/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Data Field</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.DataField#getInitialValue <em>Initial Value</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.DataField#isCorrelation <em>Correlation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.DataField#getDeprecatedDataIsArray <em>Deprecated Data Is Array</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataField()
 * @model extendedMetaData="name='DataField_._type' kind='elementOnly' features-order='dataType initialValue length description extendedAttributes otherElements'"
 * @generated
 */
public interface DataField extends ProcessRelevantData,
        ExtendedAttributesContainer {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Initial Value</b></em>' containment
     * reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Initial Value</em>' containment reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Initial Value</em>' containment reference.
     * @see #setInitialValue(Expression)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataField_InitialValue()
     * @model containment="true" extendedMetaData=
     *        "kind='element' name='InitialValue' namespace='##targetNamespace'"
     * @generated NOT
     * @deprecated NOTE that Business Studio <b>no longer uses
     *             dataField/InitialValue</b> as this cannot cope properly wioth
     *             initial values for arrays etc. So instead the
     *             xpdExt:InitialValues ##other element should be used (this is
     *             then also consistent with formal parameter's inital values
     *             model.
     */
    @Deprecated
    Expression getInitialValue();

    /**
     * Sets the value of the '
     * {@link com.tibco.xpd.xpdl2.DataField#getInitialValue
     * <em>Initial Value</em>}' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Initial Value</em>' containment
     *            reference.
     * @see #getInitialValue()
     * @generated NOT
     * @deprecated NOTE that Business Studio <b>no longer uses
     *             dataField/InitialValue</b> as this cannot cope properly wioth
     *             initial values for arrays etc. So instead the
     *             xpdExt:InitialValues ##other element should be used (this is
     *             then also consistent with formal parameter's inital values
     *             model.
     */
    @Deprecated
    void setInitialValue(Expression value);

    /**
     * Returns the value of the '<em><b>Correlation</b></em>' attribute. The
     * default value is <code>"false"</code>. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc --> Used in BPMN to support mapping
     * to BPEL <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Correlation</em>' attribute.
     * @see #isSetCorrelation()
     * @see #unsetCorrelation()
     * @see #setCorrelation(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataField_Correlation()
     * @model default="false" unique="false" unsettable="true"
     *        dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='Correlation'"
     * @generated
     */
    boolean isCorrelation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.DataField#isCorrelation <em>Correlation</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Correlation</em>' attribute.
     * @see #isSetCorrelation()
     * @see #unsetCorrelation()
     * @see #isCorrelation()
     * @generated
     */
    void setCorrelation(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.DataField#isCorrelation <em>Correlation</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isSetCorrelation()
     * @see #isCorrelation()
     * @see #setCorrelation(boolean)
     * @generated
     */
    void unsetCorrelation();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.DataField#isCorrelation <em>Correlation</em>}' attribute is set.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return whether the value of the '<em>Correlation</em>' attribute is set.
     * @see #unsetCorrelation()
     * @see #isCorrelation()
     * @see #setCorrelation(boolean)
     * @generated
     */
    boolean isSetCorrelation();

    /**
     * Returns the value of the '<em><b>Deprecated Data Is Array</b></em>' attribute.
     * The default value is <code>"FALSE"</code>.
     * <!-- begin-user-doc
     * -->
     * <p>
     * If the meaning of the '<em>Deprecated Data Is Array</em>' attribute isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deprecated Data Is Array</em>' attribute.
     * @see #isSetDeprecatedDataIsArray()
     * @see #unsetDeprecatedDataIsArray()
     * @see #setDeprecatedDataIsArray(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getDataField_DeprecatedDataIsArray()
     * @model default="FALSE" unsettable="true"
     *        extendedMetaData="kind='attribute' name='IsArray'"
     * @generated
     */
    String getDeprecatedDataIsArray();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.DataField#getDeprecatedDataIsArray <em>Deprecated Data Is Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Data Is Array</em>' attribute.
     * @see #isSetDeprecatedDataIsArray()
     * @see #unsetDeprecatedDataIsArray()
     * @see #getDeprecatedDataIsArray()
     * @generated
     */
    void setDeprecatedDataIsArray(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.DataField#getDeprecatedDataIsArray <em>Deprecated Data Is Array</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDeprecatedDataIsArray()
     * @see #getDeprecatedDataIsArray()
     * @see #setDeprecatedDataIsArray(String)
     * @generated
     */
    void unsetDeprecatedDataIsArray();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.DataField#getDeprecatedDataIsArray <em>Deprecated Data Is Array</em>}' attribute is set.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @return whether the value of the '<em>Deprecated Data Is Array</em>' attribute is set.
     * @see #unsetDeprecatedDataIsArray()
     * @see #getDeprecatedDataIsArray()
     * @see #setDeprecatedDataIsArray(String)
     * @generated
     */
    boolean isSetDeprecatedDataIsArray();

} // DataField
