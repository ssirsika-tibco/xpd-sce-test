/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Route</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Route#getGatewayType <em>Gateway Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Route#getDeprecatedXorType <em>Deprecated Xor Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Route#isDeprecatedInstantiate <em>Deprecated Instantiate</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Route#isMarkerVisible <em>Marker Visible</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Route#getIncomingCondition <em>Incoming Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Route#getOutgoingCondition <em>Outgoing Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Route#getExclusiveType <em>Exclusive Type</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRoute()
 * @model extendedMetaData="name='Route_._type' kind='elementOnly'"
 * @generated
 */
public interface Route extends OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Gateway Type</b></em>' attribute.
     * The default value is <code>"XOR"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.JoinSplitType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Used when needed for BPMN Gateways. Gate and sequence information is associated with the Transition Element.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Gateway Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.JoinSplitType
     * @see #isSetGatewayType()
     * @see #unsetGatewayType()
     * @see #setGatewayType(JoinSplitType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRoute_GatewayType()
     * @model default="XOR" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='GatewayType'"
     */
    JoinSplitType getGatewayType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Route#getGatewayType <em>Gateway Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Gateway Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.JoinSplitType
     * @see #isSetGatewayType()
     * @see #unsetGatewayType()
     * @see #getGatewayType()
     * @generated
     */
    void setGatewayType(JoinSplitType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Route#getGatewayType <em>Gateway Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGatewayType()
     * @see #getGatewayType()
     * @see #setGatewayType(JoinSplitType)
     * @generated
     */
    void unsetGatewayType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Route#getGatewayType <em>Gateway Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Gateway Type</em>' attribute is set.
     * @see #unsetGatewayType()
     * @see #getGatewayType()
     * @see #setGatewayType(JoinSplitType)
     * @generated
     */
    boolean isSetGatewayType();

    /**
     * Returns the value of the '<em><b>Deprecated Xor Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.DeprecatedXorType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Used when needed for BPMN Gateways. Gate and sequence information is associated with the Transition Element.
     * This element is deprecated in XPDL2.1
     * <!-- end-model-doc -->
     * @return the value of the '<em>Deprecated Xor Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.DeprecatedXorType
     * @see #isSetDeprecatedXorType()
     * @see #unsetDeprecatedXorType()
     * @see #setDeprecatedXorType(DeprecatedXorType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRoute_DeprecatedXorType()
     * @model unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='XORType'"
     * @generated
     */
    DeprecatedXorType getDeprecatedXorType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Route#getDeprecatedXorType <em>Deprecated Xor Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Xor Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.DeprecatedXorType
     * @see #isSetDeprecatedXorType()
     * @see #unsetDeprecatedXorType()
     * @see #getDeprecatedXorType()
     * @generated
     */
    void setDeprecatedXorType(DeprecatedXorType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Route#getDeprecatedXorType <em>Deprecated Xor Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDeprecatedXorType()
     * @see #getDeprecatedXorType()
     * @see #setDeprecatedXorType(DeprecatedXorType)
     * @generated
     */
    void unsetDeprecatedXorType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Route#getDeprecatedXorType <em>Deprecated Xor Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Deprecated Xor Type</em>' attribute is set.
     * @see #unsetDeprecatedXorType()
     * @see #getDeprecatedXorType()
     * @see #setDeprecatedXorType(DeprecatedXorType)
     * @generated
     */
    boolean isSetDeprecatedXorType();

    /**
     * Returns the value of the '<em><b>Deprecated Instantiate</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deprecated Instantiate</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deprecated Instantiate</em>' attribute.
     * @see #isSetDeprecatedInstantiate()
     * @see #unsetDeprecatedInstantiate()
     * @see #setDeprecatedInstantiate(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRoute_DeprecatedInstantiate()
     * @model default="false" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='Instantiate'"
     * @generated
     */
    boolean isDeprecatedInstantiate();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Route#isDeprecatedInstantiate <em>Deprecated Instantiate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Instantiate</em>' attribute.
     * @see #isSetDeprecatedInstantiate()
     * @see #unsetDeprecatedInstantiate()
     * @see #isDeprecatedInstantiate()
     * @generated
     */
    void setDeprecatedInstantiate(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Route#isDeprecatedInstantiate <em>Deprecated Instantiate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDeprecatedInstantiate()
     * @see #isDeprecatedInstantiate()
     * @see #setDeprecatedInstantiate(boolean)
     * @generated
     */
    void unsetDeprecatedInstantiate();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Route#isDeprecatedInstantiate <em>Deprecated Instantiate</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Deprecated Instantiate</em>' attribute is set.
     * @see #unsetDeprecatedInstantiate()
     * @see #isDeprecatedInstantiate()
     * @see #setDeprecatedInstantiate(boolean)
     * @generated
     */
    boolean isSetDeprecatedInstantiate();

    /**
     * Returns the value of the '<em><b>Exclusive Type</b></em>' attribute.
     * The default value is <code>"XOR"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.ExclusiveType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Exclusive Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ExclusiveType
     * @see #isSetExclusiveType()
     * @see #unsetExclusiveType()
     * @see #setExclusiveType(ExclusiveType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRoute_ExclusiveType()
     * @model default="XOR" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='ExclusiveType'"
     * 
     */
    ExclusiveType getExclusiveType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Route#getExclusiveType <em>Exclusive Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Exclusive Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ExclusiveType
     * @see #isSetExclusiveType()
     * @see #unsetExclusiveType()
     * @see #getExclusiveType()
     * @generated
     */
    void setExclusiveType(ExclusiveType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Route#getExclusiveType <em>Exclusive Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetExclusiveType()
     * @see #getExclusiveType()
     * @see #setExclusiveType(ExclusiveType)
     * @generated
     */
    void unsetExclusiveType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Route#getExclusiveType <em>Exclusive Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Exclusive Type</em>' attribute is set.
     * @see #unsetExclusiveType()
     * @see #getExclusiveType()
     * @see #setExclusiveType(ExclusiveType)
     * @generated
     */
    boolean isSetExclusiveType();

    /**
     * Returns the value of the '<em><b>Marker Visible</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Applicable only to XOR Gateways
     * <!-- end-model-doc -->
     * @return the value of the '<em>Marker Visible</em>' attribute.
     * @see #isSetMarkerVisible()
     * @see #unsetMarkerVisible()
     * @see #setMarkerVisible(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRoute_MarkerVisible()
     * @model default="false" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='MarkerVisible'"
     * @generated
     */
    boolean isMarkerVisible();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Route#isMarkerVisible <em>Marker Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Marker Visible</em>' attribute.
     * @see #isSetMarkerVisible()
     * @see #unsetMarkerVisible()
     * @see #isMarkerVisible()
     * @generated
     */
    void setMarkerVisible(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Route#isMarkerVisible <em>Marker Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMarkerVisible()
     * @see #isMarkerVisible()
     * @see #setMarkerVisible(boolean)
     * @generated
     */
    void unsetMarkerVisible();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Route#isMarkerVisible <em>Marker Visible</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Marker Visible</em>' attribute is set.
     * @see #unsetMarkerVisible()
     * @see #isMarkerVisible()
     * @see #setMarkerVisible(boolean)
     * @generated
     */
    boolean isSetMarkerVisible();

    /**
     * Returns the value of the '<em><b>Incoming Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Incoming Condition</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Incoming Condition</em>' attribute.
     * @see #setIncomingCondition(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRoute_IncomingCondition()
     * @model extendedMetaData="kind='attribute' name='IncomingCondition'"
     * @generated
     */
    String getIncomingCondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Route#getIncomingCondition <em>Incoming Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Incoming Condition</em>' attribute.
     * @see #getIncomingCondition()
     * @generated
     */
    void setIncomingCondition(String value);

    /**
     * Returns the value of the '<em><b>Outgoing Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Outgoing Condition</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Outgoing Condition</em>' attribute.
     * @see #setOutgoingCondition(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRoute_OutgoingCondition()
     * @model extendedMetaData="kind='attribute' name='OutgoingCondition'"
     * @generated
     */
    String getOutgoingCondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Route#getOutgoingCondition <em>Outgoing Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Outgoing Condition</em>' attribute.
     * @see #getOutgoingCondition()
     * @generated
     */
    void setOutgoingCondition(String value);

} // Route
