/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.provider;

import com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression;
import com.tibco.xpd.xpdExtension.QueryExpressionJoinType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class CaseDocumentQueryExpressionItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
        IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CaseDocumentQueryExpressionItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addQueryExpressionJoinTypePropertyDescriptor(object);
            addOpenBracketCountPropertyDescriptor(object);
            addCmisPropertyPropertyDescriptor(object);
            addOperatorPropertyDescriptor(object);
            addProcessDataFieldPropertyDescriptor(object);
            addCloseBracketCountPropertyDescriptor(object);
            addCmisDocumentPropertySelectedPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Query Expression Join Type feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addQueryExpressionJoinTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_CaseDocumentQueryExpression_queryExpressionJoinType_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_queryExpressionJoinType_feature", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.CASE_DOCUMENT_QUERY_EXPRESSION__QUERY_EXPRESSION_JOIN_TYPE,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Open Bracket Count feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addOpenBracketCountPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_CaseDocumentQueryExpression_openBracketCount_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_openBracketCount_feature", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.CASE_DOCUMENT_QUERY_EXPRESSION__OPEN_BRACKET_COUNT,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Operator feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addOperatorPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_CaseDocumentQueryExpression_operator_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_operator_feature", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.CASE_DOCUMENT_QUERY_EXPRESSION__OPERATOR,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Cmis Property feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addCmisPropertyPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_CaseDocumentQueryExpression_cmisProperty_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_cmisProperty_feature", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_PROPERTY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Process Data Field feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addProcessDataFieldPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_CaseDocumentQueryExpression_processDataField_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_processDataField_feature", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.CASE_DOCUMENT_QUERY_EXPRESSION__PROCESS_DATA_FIELD,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Close Bracket Count feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addCloseBracketCountPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_CaseDocumentQueryExpression_closeBracketCount_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_closeBracketCount_feature", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.CASE_DOCUMENT_QUERY_EXPRESSION__CLOSE_BRACKET_COUNT,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Cmis Document Property Selected feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addCmisDocumentPropertySelectedPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_CaseDocumentQueryExpression_cmisDocumentPropertySelected_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_cmisDocumentPropertySelected_feature", //$NON-NLS-1$
                                "_UI_CaseDocumentQueryExpression_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_DOCUMENT_PROPERTY_SELECTED,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This returns CaseDocumentQueryExpression.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/CaseDocumentQueryExpression")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        QueryExpressionJoinType labelValue = ((CaseDocumentQueryExpression) object).getQueryExpressionJoinType();
        String label = labelValue == null ? null : labelValue.toString();
        return label == null || label.length() == 0 ? getString("_UI_CaseDocumentQueryExpression_type") : //$NON-NLS-1$
                getString("_UI_CaseDocumentQueryExpression_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(CaseDocumentQueryExpression.class)) {
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__QUERY_EXPRESSION_JOIN_TYPE:
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPEN_BRACKET_COUNT:
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_PROPERTY:
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__OPERATOR:
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__PROCESS_DATA_FIELD:
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CLOSE_BRACKET_COUNT:
        case XpdExtensionPackage.CASE_DOCUMENT_QUERY_EXPRESSION__CMIS_DOCUMENT_PROPERTY_SELECTED:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
     * that can be created under this object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return XpdExtensionEditPlugin.INSTANCE;
    }

}
