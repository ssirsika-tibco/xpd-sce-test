/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case Document Query Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getQueryExpressionJoinType <em>Query Expression Join Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getOpenBracketCount <em>Open Bracket Count</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCmisProperty <em>Cmis Property</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getOperator <em>Operator</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getProcessDataField <em>Process Data Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCloseBracketCount <em>Close Bracket Count</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#isCmisDocumentPropertySelected <em>Cmis Document Property Selected</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocumentQueryExpression()
 * @model
 * @generated
 */
public interface CaseDocumentQueryExpression extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    public static final String CMIS_DOCUMENT_PROPERTY = "cmis:document"; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Query Expression Join Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.QueryExpressionJoinType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Logical Operator to join the Conditional Expressions of type QueryExpressionJoinType
     * <!-- end-model-doc -->
     * @return the value of the '<em>Query Expression Join Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.QueryExpressionJoinType
     * @see #isSetQueryExpressionJoinType()
     * @see #unsetQueryExpressionJoinType()
     * @see #setQueryExpressionJoinType(QueryExpressionJoinType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocumentQueryExpression_QueryExpressionJoinType()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='QueryExpressionJoinType'"
     * @generated
     */
    QueryExpressionJoinType getQueryExpressionJoinType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getQueryExpressionJoinType <em>Query Expression Join Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Query Expression Join Type</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.QueryExpressionJoinType
     * @see #isSetQueryExpressionJoinType()
     * @see #unsetQueryExpressionJoinType()
     * @see #getQueryExpressionJoinType()
     * @generated
     */
    void setQueryExpressionJoinType(QueryExpressionJoinType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getQueryExpressionJoinType <em>Query Expression Join Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetQueryExpressionJoinType()
     * @see #getQueryExpressionJoinType()
     * @see #setQueryExpressionJoinType(QueryExpressionJoinType)
     * @generated
     */
    void unsetQueryExpressionJoinType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getQueryExpressionJoinType <em>Query Expression Join Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Query Expression Join Type</em>' attribute is set.
     * @see #unsetQueryExpressionJoinType()
     * @see #getQueryExpressionJoinType()
     * @see #setQueryExpressionJoinType(QueryExpressionJoinType)
     * @generated
     */
    boolean isSetQueryExpressionJoinType();

    /**
     * Returns the value of the '<em><b>Open Bracket Count</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Count of Opening Brackets in this Expression, used to Group Expressions.Type int
     * <!-- end-model-doc -->
     * @return the value of the '<em>Open Bracket Count</em>' attribute.
     * @see #isSetOpenBracketCount()
     * @see #unsetOpenBracketCount()
     * @see #setOpenBracketCount(int)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocumentQueryExpression_OpenBracketCount()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='OpenBracketCount'"
     * @generated
     */
    int getOpenBracketCount();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getOpenBracketCount <em>Open Bracket Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Open Bracket Count</em>' attribute.
     * @see #isSetOpenBracketCount()
     * @see #unsetOpenBracketCount()
     * @see #getOpenBracketCount()
     * @generated
     */
    void setOpenBracketCount(int value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getOpenBracketCount <em>Open Bracket Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetOpenBracketCount()
     * @see #getOpenBracketCount()
     * @see #setOpenBracketCount(int)
     * @generated
     */
    void unsetOpenBracketCount();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getOpenBracketCount <em>Open Bracket Count</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Open Bracket Count</em>' attribute is set.
     * @see #unsetOpenBracketCount()
     * @see #getOpenBracketCount()
     * @see #setOpenBracketCount(int)
     * @generated
     */
    boolean isSetOpenBracketCount();

    /**
     * Returns the value of the '<em><b>Operator</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.CMISQueryOperator}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Operator</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Operator used in the CMIS Query Conditional Expression. A valid value is of type CMISQueryOperator enum.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Operator</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.CMISQueryOperator
     * @see #isSetOperator()
     * @see #unsetOperator()
     * @see #setOperator(CMISQueryOperator)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocumentQueryExpression_Operator()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='CMISQueryOperator'"
     * @generated
     */
    CMISQueryOperator getOperator();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getOperator <em>Operator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Operator</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.CMISQueryOperator
     * @see #isSetOperator()
     * @see #unsetOperator()
     * @see #getOperator()
     * @generated
     */
    void setOperator(CMISQueryOperator value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getOperator <em>Operator</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetOperator()
     * @see #getOperator()
     * @see #setOperator(CMISQueryOperator)
     * @generated
     */
    void unsetOperator();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getOperator <em>Operator</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Operator</em>' attribute is set.
     * @see #unsetOperator()
     * @see #getOperator()
     * @see #setOperator(CMISQueryOperator)
     * @generated
     */
    boolean isSetOperator();

    /**
     * Returns the value of the '<em><b>Cmis Property</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cmis Property</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * CMIS Property  name ,used in the Conditional Expression.Type String.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Cmis Property</em>' attribute.
     * @see #isSetCmisProperty()
     * @see #unsetCmisProperty()
     * @see #setCmisProperty(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocumentQueryExpression_CmisProperty()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='CMISProperty'"
     * @generated
     */
    String getCmisProperty();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCmisProperty <em>Cmis Property</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cmis Property</em>' attribute.
     * @see #isSetCmisProperty()
     * @see #unsetCmisProperty()
     * @see #getCmisProperty()
     * @generated
     */
    void setCmisProperty(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCmisProperty <em>Cmis Property</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetCmisProperty()
     * @see #getCmisProperty()
     * @see #setCmisProperty(String)
     * @generated
     */
    void unsetCmisProperty();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCmisProperty <em>Cmis Property</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Cmis Property</em>' attribute is set.
     * @see #unsetCmisProperty()
     * @see #getCmisProperty()
     * @see #setCmisProperty(String)
     * @generated
     */
    boolean isSetCmisProperty();

    /**
     * Returns the value of the '<em><b>Process Data Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process Data Field</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Process Data Field  used in the Conditional Expression, Type String
     * <!-- end-model-doc -->
     * @return the value of the '<em>Process Data Field</em>' attribute.
     * @see #isSetProcessDataField()
     * @see #unsetProcessDataField()
     * @see #setProcessDataField(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocumentQueryExpression_ProcessDataField()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='ProcessDataField'"
     * @generated
     */
    String getProcessDataField();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getProcessDataField <em>Process Data Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Data Field</em>' attribute.
     * @see #isSetProcessDataField()
     * @see #unsetProcessDataField()
     * @see #getProcessDataField()
     * @generated
     */
    void setProcessDataField(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getProcessDataField <em>Process Data Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetProcessDataField()
     * @see #getProcessDataField()
     * @see #setProcessDataField(String)
     * @generated
     */
    void unsetProcessDataField();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getProcessDataField <em>Process Data Field</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Process Data Field</em>' attribute is set.
     * @see #unsetProcessDataField()
     * @see #getProcessDataField()
     * @see #setProcessDataField(String)
     * @generated
     */
    boolean isSetProcessDataField();

    /**
     * Returns the value of the '<em><b>Close Bracket Count</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Count of Closing Brackets in this Expression, used to Group Expressions.Type int
     * <!-- end-model-doc -->
     * @return the value of the '<em>Close Bracket Count</em>' attribute.
     * @see #isSetCloseBracketCount()
     * @see #unsetCloseBracketCount()
     * @see #setCloseBracketCount(int)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocumentQueryExpression_CloseBracketCount()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='CloseBracketCount'"
     * @generated
     */
    int getCloseBracketCount();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCloseBracketCount <em>Close Bracket Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Close Bracket Count</em>' attribute.
     * @see #isSetCloseBracketCount()
     * @see #unsetCloseBracketCount()
     * @see #getCloseBracketCount()
     * @generated
     */
    void setCloseBracketCount(int value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCloseBracketCount <em>Close Bracket Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetCloseBracketCount()
     * @see #getCloseBracketCount()
     * @see #setCloseBracketCount(int)
     * @generated
     */
    void unsetCloseBracketCount();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCloseBracketCount <em>Close Bracket Count</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Close Bracket Count</em>' attribute is set.
     * @see #unsetCloseBracketCount()
     * @see #getCloseBracketCount()
     * @see #setCloseBracketCount(int)
     * @generated
     */
    boolean isSetCloseBracketCount();

    /**
     * Returns the value of the '<em><b>Cmis Document Property Selected</b></em>
     * ' attribute. <!-- begin-user-doc --> This is for sole purpose of UI
     * display of cmis:document property and not to be used for non-UI. <!--
     * end-user-doc --> <!-- begin-model-doc --> Boolean to specify that the
     * expression uses cmis:document property.This property is solely for the
     * purpose of UI display and not to be used otherwise. <!-- end-model-doc
     * -->
     * 
     * @return the value of the '<em>Cmis Document Property Selected</em>'
     *         attribute.
     * @see #isSetCmisDocumentPropertySelected()
     * @see #unsetCmisDocumentPropertySelected()
     * @see #setCmisDocumentPropertySelected(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCaseDocumentQueryExpression_CmisDocumentPropertySelected()
     * @model unsettable="true"
     *        dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData=
     *        "kind='attribute' name='CmisDocumentPropertySelected'"
     * @generated
     */
    boolean isCmisDocumentPropertySelected();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#isCmisDocumentPropertySelected <em>Cmis Document Property Selected</em>}' attribute.
     * <!-- begin-user-doc
     * --> This is for sole purpose of UI display of cmis:document property and
     * not to be used for non-UI. <!-- end-user-doc -->
     * @param value the new value of the '<em>Cmis Document Property Selected</em>' attribute.
     * @see #isSetCmisDocumentPropertySelected()
     * @see #unsetCmisDocumentPropertySelected()
     * @see #isCmisDocumentPropertySelected()
     * @generated
     */
    void setCmisDocumentPropertySelected(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#isCmisDocumentPropertySelected <em>Cmis Document Property Selected</em>}' attribute.
     * <!-- begin-user-doc
     * --> This is for sole purpose of UI display of cmis:document property and
     * not to be used for non-UI. <!-- end-user-doc -->
     * @see #isSetCmisDocumentPropertySelected()
     * @see #isCmisDocumentPropertySelected()
     * @see #setCmisDocumentPropertySelected(boolean)
     * @generated
     */
    void unsetCmisDocumentPropertySelected();

    /**
     * Returns whether the value of the '
     * {@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#isCmisDocumentPropertySelected
     * <em>Cmis Document Property Selected</em>}' attribute is set. <!--
     * begin-user-doc --> This is for sole purpose of UI display of
     * cmis:document property and not to be used for non-UI. <!-- end-user-doc
     * -->
     * 
     * @return whether the value of the '
     *         <em>Cmis Document Property Selected</em>' attribute is set.
     * @see #unsetCmisDocumentPropertySelected()
     * @see #isCmisDocumentPropertySelected()
     * @see #setCmisDocumentPropertySelected(boolean)
     * @generated
     */
    boolean isSetCmisDocumentPropertySelected();

    /**
     * Returns CMIS Property String for display. In additional to the supported
     * Properties which can be set, when a property is not set checks for
     * isCmisDocumentProperty(), and returns cmis:document property when it is
     * set. This is for sole purpose of UI display of cmis:document property and
     * not to be used for non-UI.
     * 
     * @return CMIS Property name, returns cmis:document property name when
     *         cmisDocumentPropertySelected is set to true.
     * @generated NOT
     */
    String getCmisPropertyForDisplay();
} // CaseDocumentQueryExpression
