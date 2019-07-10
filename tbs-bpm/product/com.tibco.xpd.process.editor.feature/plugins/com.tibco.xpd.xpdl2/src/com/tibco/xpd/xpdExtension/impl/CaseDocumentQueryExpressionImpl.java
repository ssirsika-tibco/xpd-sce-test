/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.CMISQueryOperator;
import com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression;
import com.tibco.xpd.xpdExtension.QueryExpressionJoinType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Case Document Query Expression</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocumentQueryExpressionImpl#getQueryExpressionJoinType <em>Query Expression Join Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocumentQueryExpressionImpl#getOpenBracketCount <em>Open Bracket Count</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocumentQueryExpressionImpl#getCmisProperty <em>Cmis Property</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocumentQueryExpressionImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocumentQueryExpressionImpl#getProcessDataField <em>Process Data Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocumentQueryExpressionImpl#getCloseBracketCount <em>Close Bracket Count</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.CaseDocumentQueryExpressionImpl#isCmisDocumentPropertySelected <em>Cmis Document Property Selected</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CaseDocumentQueryExpressionImpl extends EObjectImpl implements CaseDocumentQueryExpression {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getQueryExpressionJoinType()
     * <em>Query Expression Join Type</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getQueryExpressionJoinType()
     * @generated NOT
     * @ordered
     */
    protected static final QueryExpressionJoinType QUERY_EXPRESSION_JOIN_TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getQueryExpressionJoinType() <em>Query Expression Join Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQueryExpressionJoinType()
     * @generated
     * @ordered
     */
    protected QueryExpressionJoinType queryExpressionJoinType = QUERY_EXPRESSION_JOIN_TYPE_EDEFAULT;

    /**
     * This is true if the Query Expression Join Type attribute has been set.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean queryExpressionJoinTypeESet;

    /**
     * The default value of the '{@link #getOpenBracketCount() <em>Open Bracket Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOpenBracketCount()
     * @generated
     * @ordered
     */
    protected static final int OPEN_BRACKET_COUNT_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getOpenBracketCount() <em>Open Bracket Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOpenBracketCount()
     * @generated
     * @ordered
     */
    protected int openBracketCount = OPEN_BRACKET_COUNT_EDEFAULT;

    /**
     * This is true if the Open Bracket Count attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean openBracketCountESet;

    /**
     * The default value of the '{@link #getCmisProperty() <em>Cmis Property</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getCmisProperty()
     * @generated
     * @ordered
     */
    protected static final String CMIS_PROPERTY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCmisProperty() <em>Cmis Property</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getCmisProperty()
     * @generated
     * @ordered
     */
    protected String cmisProperty = CMIS_PROPERTY_EDEFAULT;

    /**
     * This is true if the Cmis Property attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean cmisPropertyESet;

    /**
     * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getOperator()
     * @generated
     * @ordered
     */
    protected static final CMISQueryOperator OPERATOR_EDEFAULT = CMISQueryOperator.EQUAL;

    /**
     * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getOperator()
     * @generated
     * @ordered
     */
    protected CMISQueryOperator operator = OPERATOR_EDEFAULT;

    /**
     * This is true if the Operator attribute has been set.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean operatorESet;

    /**
     * The default value of the '{@link #getProcessDataField() <em>Process Data Field</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getProcessDataField()
     * @generated
     * @ordered
     */
    protected static final String PROCESS_DATA_FIELD_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProcessDataField() <em>Process Data Field</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getProcessDataField()
     * @generated
     * @ordered
     */
    protected String processDataField = PROCESS_DATA_FIELD_EDEFAULT;

    /**
     * This is true if the Process Data Field attribute has been set. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean processDataFieldESet;

    /**
     * The default value of the '{@link #getCloseBracketCount() <em>Close Bracket Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCloseBracketCount()
     * @generated
     * @ordered
     */
    protected static final int CLOSE_BRACKET_COUNT_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getCloseBracketCount() <em>Close Bracket Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCloseBracketCount()
     * @generated
     * @ordered
     */
    protected int closeBracketCount = CLOSE_BRACKET_COUNT_EDEFAULT;

    /**
     * This is true if the Close Bracket Count attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean closeBracketCountESet;

    /**
     * The default value of the '{@link #isCmisDocumentPropertySelected() <em>Cmis Document Property Selected</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isCmisDocumentPropertySelected()
     * @generated
     * @ordered
     */
    protected static final boolean CMIS_DOCUMENT_PROPERTY_SELECTED_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isCmisDocumentPropertySelected() <em>Cmis Document Property Selected</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isCmisDocumentPropertySelected()
     * @generated
     * @ordered
     */
    protected boolean cmisDocumentPropertySelected = CMIS_DOCUMENT_PROPERTY_SELECTED_EDEFAULT;

    /**
     * This is true if the Cmis Document Property Selected attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean cmisDocumentPropertySelectedESet;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected CaseDocumentQueryExpressionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.CASE_DOCUMENT_QUERY_EXPRESSION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public QueryExpressionJoinType getQueryExpressionJoinType() {
        return queryExpressionJoinType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setQueryExpressionJoinType(QueryExpressionJoinType newQueryExpressionJoinType) {
        QueryExpressionJoinType oldQueryExpressionJoinType = queryExpressionJoinType;
        queryExpressionJoinType =
                newQueryExpressionJoinType == null ? QUERY_EXPRESSION_JOIN_TYPE_EDEFAULT : newQueryExpressionJoinType;
        boolean oldQueryExpressionJoinTypeESet = queryExpressionJoinTypeESet;
        queryExpressionJoinTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__QUERY_EXPRESSION_JOIN_TYPE,
                    oldQueryExpressionJoinType, queryExpressionJoinType, !oldQueryExpressionJoinTypeESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetQueryExpressionJoinType() {
        QueryExpressionJoinType oldQueryExpressionJoinType = queryExpressionJoinType;
        boolean oldQueryExpressionJoinTypeESet = queryExpressionJoinTypeESet;
        queryExpressionJoinType = QUERY_EXPRESSION_JOIN_TYPE_EDEFAULT;
        queryExpressionJoinTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__QUERY_EXPRESSION_JOIN_TYPE,
                    oldQueryExpressionJoinType, QUERY_EXPRESSION_JOIN_TYPE_EDEFAULT, oldQueryExpressionJoinTypeESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetQueryExpressionJoinType() {
        return queryExpressionJoinTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int getOpenBracketCount() {
        return openBracketCount;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setOpenBracketCount(int newOpenBracketCount) {
        int oldOpenBracketCount = openBracketCount;
        openBracketCount = newOpenBracketCount;
        boolean oldOpenBracketCountESet = openBracketCountESet;
        openBracketCountESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPEN_BRACKET_COUNT, oldOpenBracketCount,
                    openBracketCount, !oldOpenBracketCountESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetOpenBracketCount() {
        int oldOpenBracketCount = openBracketCount;
        boolean oldOpenBracketCountESet = openBracketCountESet;
        openBracketCount = OPEN_BRACKET_COUNT_EDEFAULT;
        openBracketCountESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPEN_BRACKET_COUNT, oldOpenBracketCount,
                    OPEN_BRACKET_COUNT_EDEFAULT, oldOpenBracketCountESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetOpenBracketCount() {
        return openBracketCountESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public CMISQueryOperator getOperator() {
        return operator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setOperator(CMISQueryOperator newOperator) {
        CMISQueryOperator oldOperator = operator;
        operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
        boolean oldOperatorESet = operatorESet;
        operatorESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPERATOR, oldOperator, operator,
                    !oldOperatorESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetOperator() {
        CMISQueryOperator oldOperator = operator;
        boolean oldOperatorESet = operatorESet;
        operator = OPERATOR_EDEFAULT;
        operatorESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPERATOR, oldOperator, OPERATOR_EDEFAULT,
                    oldOperatorESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetOperator() {
        return operatorESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getCmisProperty() {
        return cmisProperty;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setCmisProperty(String newCmisProperty) {
        String oldCmisProperty = cmisProperty;
        cmisProperty = newCmisProperty;
        boolean oldCmisPropertyESet = cmisPropertyESet;
        cmisPropertyESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_PROPERTY, oldCmisProperty, cmisProperty,
                    !oldCmisPropertyESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetCmisProperty() {
        String oldCmisProperty = cmisProperty;
        boolean oldCmisPropertyESet = cmisPropertyESet;
        cmisProperty = CMIS_PROPERTY_EDEFAULT;
        cmisPropertyESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_PROPERTY, oldCmisProperty,
                    CMIS_PROPERTY_EDEFAULT, oldCmisPropertyESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetCmisProperty() {
        return cmisPropertyESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getProcessDataField() {
        return processDataField;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setProcessDataField(String newProcessDataField) {
        String oldProcessDataField = processDataField;
        processDataField = newProcessDataField;
        boolean oldProcessDataFieldESet = processDataFieldESet;
        processDataFieldESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__PROCESS_DATA_FIELD, oldProcessDataField,
                    processDataField, !oldProcessDataFieldESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetProcessDataField() {
        String oldProcessDataField = processDataField;
        boolean oldProcessDataFieldESet = processDataFieldESet;
        processDataField = PROCESS_DATA_FIELD_EDEFAULT;
        processDataFieldESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__PROCESS_DATA_FIELD, oldProcessDataField,
                    PROCESS_DATA_FIELD_EDEFAULT, oldProcessDataFieldESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetProcessDataField() {
        return processDataFieldESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int getCloseBracketCount() {
        return closeBracketCount;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setCloseBracketCount(int newCloseBracketCount) {
        int oldCloseBracketCount = closeBracketCount;
        closeBracketCount = newCloseBracketCount;
        boolean oldCloseBracketCountESet = closeBracketCountESet;
        closeBracketCountESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CLOSE_BRACKET_COUNT, oldCloseBracketCount,
                    closeBracketCount, !oldCloseBracketCountESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetCloseBracketCount() {
        int oldCloseBracketCount = closeBracketCount;
        boolean oldCloseBracketCountESet = closeBracketCountESet;
        closeBracketCount = CLOSE_BRACKET_COUNT_EDEFAULT;
        closeBracketCountESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CLOSE_BRACKET_COUNT, oldCloseBracketCount,
                    CLOSE_BRACKET_COUNT_EDEFAULT, oldCloseBracketCountESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetCloseBracketCount() {
        return closeBracketCountESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isCmisDocumentPropertySelected() {
        return cmisDocumentPropertySelected;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setCmisDocumentPropertySelected(boolean newCmisDocumentPropertySelected) {
        boolean oldCmisDocumentPropertySelected = cmisDocumentPropertySelected;
        cmisDocumentPropertySelected = newCmisDocumentPropertySelected;
        boolean oldCmisDocumentPropertySelectedESet = cmisDocumentPropertySelectedESet;
        cmisDocumentPropertySelectedESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_DOCUMENT_PROPERTY_SELECTED,
                    oldCmisDocumentPropertySelected, cmisDocumentPropertySelected,
                    !oldCmisDocumentPropertySelectedESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void unsetCmisDocumentPropertySelected() {
        boolean oldCmisDocumentPropertySelected = cmisDocumentPropertySelected;
        boolean oldCmisDocumentPropertySelectedESet = cmisDocumentPropertySelectedESet;
        cmisDocumentPropertySelected = CMIS_DOCUMENT_PROPERTY_SELECTED_EDEFAULT;
        cmisDocumentPropertySelectedESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_DOCUMENT_PROPERTY_SELECTED,
                    oldCmisDocumentPropertySelected, CMIS_DOCUMENT_PROPERTY_SELECTED_EDEFAULT,
                    oldCmisDocumentPropertySelectedESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isSetCmisDocumentPropertySelected() {
        return cmisDocumentPropertySelectedESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__QUERY_EXPRESSION_JOIN_TYPE:
            return getQueryExpressionJoinType();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPEN_BRACKET_COUNT:
            return getOpenBracketCount();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_PROPERTY:
            return getCmisProperty();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPERATOR:
            return getOperator();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__PROCESS_DATA_FIELD:
            return getProcessDataField();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CLOSE_BRACKET_COUNT:
            return getCloseBracketCount();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_DOCUMENT_PROPERTY_SELECTED:
            return isCmisDocumentPropertySelected();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__QUERY_EXPRESSION_JOIN_TYPE:
            setQueryExpressionJoinType((QueryExpressionJoinType) newValue);
            return;
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPEN_BRACKET_COUNT:
            setOpenBracketCount((Integer) newValue);
            return;
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_PROPERTY:
            setCmisProperty((String) newValue);
            return;
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPERATOR:
            setOperator((CMISQueryOperator) newValue);
            return;
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__PROCESS_DATA_FIELD:
            setProcessDataField((String) newValue);
            return;
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CLOSE_BRACKET_COUNT:
            setCloseBracketCount((Integer) newValue);
            return;
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_DOCUMENT_PROPERTY_SELECTED:
            setCmisDocumentPropertySelected((Boolean) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__QUERY_EXPRESSION_JOIN_TYPE:
            unsetQueryExpressionJoinType();
            return;
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPEN_BRACKET_COUNT:
            unsetOpenBracketCount();
            return;
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_PROPERTY:
            unsetCmisProperty();
            return;
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPERATOR:
            unsetOperator();
            return;
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__PROCESS_DATA_FIELD:
            unsetProcessDataField();
            return;
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CLOSE_BRACKET_COUNT:
            unsetCloseBracketCount();
            return;
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_DOCUMENT_PROPERTY_SELECTED:
            unsetCmisDocumentPropertySelected();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__QUERY_EXPRESSION_JOIN_TYPE:
            return isSetQueryExpressionJoinType();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPEN_BRACKET_COUNT:
            return isSetOpenBracketCount();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_PROPERTY:
            return isSetCmisProperty();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPERATOR:
            return isSetOperator();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__PROCESS_DATA_FIELD:
            return isSetProcessDataField();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CLOSE_BRACKET_COUNT:
            return isSetCloseBracketCount();
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_DOCUMENT_PROPERTY_SELECTED:
            return isSetCmisDocumentPropertySelected();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (queryExpressionJoinType: "); //$NON-NLS-1$
        if (queryExpressionJoinTypeESet)
            result.append(queryExpressionJoinType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", openBracketCount: "); //$NON-NLS-1$
        if (openBracketCountESet)
            result.append(openBracketCount);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", cmisProperty: "); //$NON-NLS-1$
        if (cmisPropertyESet)
            result.append(cmisProperty);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", operator: "); //$NON-NLS-1$
        if (operatorESet)
            result.append(operator);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", processDataField: "); //$NON-NLS-1$
        if (processDataFieldESet)
            result.append(processDataField);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", closeBracketCount: "); //$NON-NLS-1$
        if (closeBracketCountESet)
            result.append(closeBracketCount);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", cmisDocumentPropertySelected: "); //$NON-NLS-1$
        if (cmisDocumentPropertySelectedESet)
            result.append(cmisDocumentPropertySelected);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

    /**
     * @see com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression#getCmisPropertyForDisplay()
     * 
     * @return
     */
    @Override
    public String getCmisPropertyForDisplay() {

        String property = getCmisProperty();

        if (property == null && isCmisDocumentPropertySelected()) {

            return CMIS_DOCUMENT_PROPERTY;
        }
        return property;
    }

} // CaseDocumentQueryExpressionImpl
