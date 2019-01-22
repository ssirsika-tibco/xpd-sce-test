/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
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

import com.tibco.xpd.xpdExtension.DocumentRoot;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Xpdl2Factory;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdExtension.DocumentRoot} object.
 * <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * @generated
 */
public class DocumentRootItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public DocumentRootItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addImplementationTypePropertyDescriptor(object);
            addContinueOnTimeoutPropertyDescriptor(object);
            addAliasPropertyDescriptor(object);
            addReplyImmediatelyPropertyDescriptor(object);
            addInitialValuesPropertyDescriptor(object);
            addAssociatedParametersPropertyDescriptor(object);
            addInlineSubProcessPropertyDescriptor(object);
            addDocumentationURLPropertyDescriptor(object);
            addImplementsPropertyDescriptor(object);
            addProcessIdentifierFieldPropertyDescriptor(object);
            addVisibilityPropertyDescriptor(object);
            addSecurityProfilePropertyDescriptor(object);
            addLanguagePropertyDescriptor(object);
            addInitialValueMappingPropertyDescriptor(object);
            addTransportPropertyDescriptor(object);
            addIsChainedPropertyDescriptor(object);
            addRequireNewTransactionPropertyDescriptor(object);
            addDiscriminatorPropertyDescriptor(object);
            addDisplayNamePropertyDescriptor(object);
            addCatchThrowPropertyDescriptor(object);
            addIsRemotePropertyDescriptor(object);
            addPublishAsBusinessServicePropertyDescriptor(object);
            addBusinessServiceCategoryPropertyDescriptor(object);
            addGeneratedPropertyDescriptor(object);
            addReplyToActivityIdPropertyDescriptor(object);
            addSetPerformerInProcessPropertyDescriptor(object);
            addEmbSubprocOtherStateHeightPropertyDescriptor(object);
            addEmbSubprocOtherStateWidthPropertyDescriptor(object);
            addApiEndPointParticipantPropertyDescriptor(object);
            addRequestActivityIdPropertyDescriptor(object);
            addTargetPrimitivePropertyPropertyDescriptor(object);
            addSourcePrimitivePropertyPropertyDescriptor(object);
            addXpdModelTypePropertyDescriptor(object);
            addFlowRoutingStylePropertyDescriptor(object);
            addNonCancellingPropertyDescriptor(object);
            addActivityDeadlineEventIdPropertyDescriptor(object);
            addStartStrategyPropertyDescriptor(object);
            addOverwriteAlreadyModifiedTaskDataPropertyDescriptor(object);
            addEventHandlerFlowStrategyPropertyDescriptor(object);
            addSuspendResumeWithParentPropertyDescriptor(object);
            addSystemErrorActionPropertyDescriptor(object);
            addBxUseUnqualifiedPropertyNamesPropertyDescriptor(object);
            addPublishAsRestServicePropertyDescriptor(object);
            addRestActivityIdPropertyDescriptor(object);
            addSignalHandlerAsynchronousPropertyDescriptor(object);
            addAllowUnqualifiedSubProcessIdentificationPropertyDescriptor(
                    object);
            addIsCaseServicePropertyDescriptor(object);
            addIsEventSubProcessPropertyDescriptor(object);
            addNonInterruptingEventPropertyDescriptor(object);
            addCorrelateImmediatelyPropertyDescriptor(object);
            addAsyncExecutionModePropertyDescriptor(object);
            addSignalTypePropertyDescriptor(object);
            addLikeMappingPropertyDescriptor(object);
            addSourceContributorIdPropertyDescriptor(object);
            addTargetContributorIdPropertyDescriptor(object);
            addBusinessServicePublishTypePropertyDescriptor(object);
            addSuppressMaxMappingsErrorPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Implementation Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addImplementationTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_implementationType_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_implementationType_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__IMPLEMENTATION_TYPE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Continue On Timeout feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addContinueOnTimeoutPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_continueOnTimeout_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_continueOnTimeout_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__CONTINUE_ON_TIMEOUT,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Alias feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addAliasPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_alias_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_alias_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__ALIAS,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Reply Immediately feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addReplyImmediatelyPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_replyImmediately_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_replyImmediately_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__REPLY_IMMEDIATELY,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Initial Values feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addInitialValuesPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_initialValues_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_initialValues_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__INITIAL_VALUES,
                true,
                false,
                true,
                null,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Associated Parameters feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addAssociatedParametersPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_associatedParameters_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_associatedParameters_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__ASSOCIATED_PARAMETERS,
                true,
                false,
                true,
                null,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Inline Sub Process feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addInlineSubProcessPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_inlineSubProcess_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_inlineSubProcess_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__INLINE_SUB_PROCESS,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Documentation URL feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDocumentationURLPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_documentationURL_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_documentationURL_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__DOCUMENTATION_URL,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Implements feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addImplementsPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_implements_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_implements_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__IMPLEMENTS,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Process Identifier Field feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addProcessIdentifierFieldPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_processIdentifierField_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_processIdentifierField_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__PROCESS_IDENTIFIER_FIELD,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Visibility feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addVisibilityPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_visibility_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_visibility_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__VISIBILITY,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Security Profile feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addSecurityProfilePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_securityProfile_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_securityProfile_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SECURITY_PROFILE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Language feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addLanguagePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_language_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_language_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__LANGUAGE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Initial Value Mapping feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addInitialValueMappingPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_initialValueMapping_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_initialValueMapping_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__INITIAL_VALUE_MAPPING,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Transport feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addTransportPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_transport_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_transport_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__TRANSPORT,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Is Chained feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addIsChainedPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_isChained_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_isChained_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__IS_CHAINED,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Require New Transaction feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addRequireNewTransactionPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_requireNewTransaction_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_requireNewTransaction_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__REQUIRE_NEW_TRANSACTION,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Discriminator feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDiscriminatorPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_discriminator_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_discriminator_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__DISCRIMINATOR,
                true,
                false,
                true,
                null,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Display Name feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDisplayNamePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_displayName_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_displayName_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__DISPLAY_NAME,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Catch Throw feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addCatchThrowPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_catchThrow_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_catchThrow_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__CATCH_THROW,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Is Remote feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addIsRemotePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_isRemote_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_isRemote_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__IS_REMOTE,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Publish As Business Service feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addPublishAsBusinessServicePropertyDescriptor(
            Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_publishAsBusinessService_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_publishAsBusinessService_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__PUBLISH_AS_BUSINESS_SERVICE,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Business Service Category feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addBusinessServiceCategoryPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_businessServiceCategory_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_businessServiceCategory_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__BUSINESS_SERVICE_CATEGORY,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Generated feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addGeneratedPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_generated_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_generated_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__GENERATED,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Reply To Activity Id feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addReplyToActivityIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_replyToActivityId_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_replyToActivityId_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__REPLY_TO_ACTIVITY_ID,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Set Performer In Process feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addSetPerformerInProcessPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_setPerformerInProcess_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_setPerformerInProcess_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SET_PERFORMER_IN_PROCESS,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Emb Subproc Other State Height feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addEmbSubprocOtherStateHeightPropertyDescriptor(
            Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString(
                        "_UI_DocumentRoot_embSubprocOtherStateHeight_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_embSubprocOtherStateHeight_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_HEIGHT,
                true,
                false,
                false,
                ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Emb Subproc Other State Width feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addEmbSubprocOtherStateWidthPropertyDescriptor(
            Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_embSubprocOtherStateWidth_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_embSubprocOtherStateWidth_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_WIDTH,
                true,
                false,
                false,
                ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Api End Point Participant feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addApiEndPointParticipantPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_apiEndPointParticipant_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_apiEndPointParticipant_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__API_END_POINT_PARTICIPANT,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Request Activity Id feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addRequestActivityIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_requestActivityId_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_requestActivityId_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__REQUEST_ACTIVITY_ID,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Target Primitive Property feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addTargetPrimitivePropertyPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_targetPrimitiveProperty_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_targetPrimitiveProperty_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__TARGET_PRIMITIVE_PROPERTY,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Source Primitive Property feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addSourcePrimitivePropertyPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_sourcePrimitiveProperty_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_sourcePrimitiveProperty_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SOURCE_PRIMITIVE_PROPERTY,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Xpd Model Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addXpdModelTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_xpdModelType_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_xpdModelType_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__XPD_MODEL_TYPE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Flow Routing Style feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addFlowRoutingStylePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_flowRoutingStyle_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_flowRoutingStyle_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__FLOW_ROUTING_STYLE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Non Cancelling feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addNonCancellingPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_nonCancelling_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_nonCancelling_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__NON_CANCELLING,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Activity Deadline Event Id feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addActivityDeadlineEventIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_activityDeadlineEventId_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_activityDeadlineEventId_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__ACTIVITY_DEADLINE_EVENT_ID,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Start Strategy feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addStartStrategyPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_startStrategy_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_startStrategy_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__START_STRATEGY,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Overwrite Already Modified Task Data feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addOverwriteAlreadyModifiedTaskDataPropertyDescriptor(
            Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString(
                        "_UI_DocumentRoot_overwriteAlreadyModifiedTaskData_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_overwriteAlreadyModifiedTaskData_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__OVERWRITE_ALREADY_MODIFIED_TASK_DATA,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Event Handler Flow Strategy feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addEventHandlerFlowStrategyPropertyDescriptor(
            Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_eventHandlerFlowStrategy_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_eventHandlerFlowStrategy_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__EVENT_HANDLER_FLOW_STRATEGY,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Suspend Resume With Parent feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addSuspendResumeWithParentPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_suspendResumeWithParent_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_suspendResumeWithParent_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SUSPEND_RESUME_WITH_PARENT,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the System Error Action feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addSystemErrorActionPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_systemErrorAction_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_systemErrorAction_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SYSTEM_ERROR_ACTION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Bx Use Unqualified Property Names feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addBxUseUnqualifiedPropertyNamesPropertyDescriptor(
            Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString(
                        "_UI_DocumentRoot_bxUseUnqualifiedPropertyNames_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_bxUseUnqualifiedPropertyNames_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__BX_USE_UNQUALIFIED_PROPERTY_NAMES,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Publish As Rest Service feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addPublishAsRestServicePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_publishAsRestService_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_publishAsRestService_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__PUBLISH_AS_REST_SERVICE,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Rest Activity Id feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addRestActivityIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_restActivityId_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_restActivityId_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__REST_ACTIVITY_ID,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Signal Handler Asynchronous feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addSignalHandlerAsynchronousPropertyDescriptor(
            Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_signalHandlerAsynchronous_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_signalHandlerAsynchronous_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SIGNAL_HANDLER_ASYNCHRONOUS,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Allow Unqualified Sub Process Identification feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addAllowUnqualifiedSubProcessIdentificationPropertyDescriptor(
            Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString(
                        "_UI_DocumentRoot_allowUnqualifiedSubProcessIdentification_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_allowUnqualifiedSubProcessIdentification_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Is Case Service feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addIsCaseServicePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_isCaseService_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_isCaseService_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__IS_CASE_SERVICE,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Is Event Sub Process feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addIsEventSubProcessPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_isEventSubProcess_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_isEventSubProcess_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__IS_EVENT_SUB_PROCESS,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Non Interrupting Event feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addNonInterruptingEventPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_nonInterruptingEvent_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_nonInterruptingEvent_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__NON_INTERRUPTING_EVENT,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Correlate Immediately feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addCorrelateImmediatelyPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_correlateImmediately_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_correlateImmediately_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__CORRELATE_IMMEDIATELY,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Async Execution Mode feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addAsyncExecutionModePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_asyncExecutionMode_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_asyncExecutionMode_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__ASYNC_EXECUTION_MODE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Signal Type feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addSignalTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_signalType_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_signalType_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SIGNAL_TYPE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Like Mapping feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addLikeMappingPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_likeMapping_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_likeMapping_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__LIKE_MAPPING,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Source Contributor Id feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addSourceContributorIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_sourceContributorId_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_sourceContributorId_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SOURCE_CONTRIBUTOR_ID,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Target Contributor Id feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addTargetContributorIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_targetContributorId_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_targetContributorId_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__TARGET_CONTRIBUTOR_ID,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Business Service Publish Type feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addBusinessServicePublishTypePropertyDescriptor(
            Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString(
                        "_UI_DocumentRoot_businessServicePublishType_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_businessServicePublishType_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__BUSINESS_SERVICE_PUBLISH_TYPE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Suppress Max Mappings Error feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addSuppressMaxMappingsErrorPropertyDescriptor(
            Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_DocumentRoot_suppressMaxMappingsError_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_DocumentRoot_suppressMaxMappingsError_feature", //$NON-NLS-1$
                        "_UI_DocumentRoot_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SUPPRESS_MAX_MAPPINGS_ERROR,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__DATA_OBJECT_ATTRIBUTES);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__EXTENDED_ATTRIBUTES);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__CONSTANT_PERIOD);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__USER_TASK_SCRIPTS);
            childrenFeatures
                    .add(XpdExtensionPackage.Literals.DOCUMENT_ROOT__AUDIT);
            childrenFeatures
                    .add(XpdExtensionPackage.Literals.DOCUMENT_ROOT__SCRIPT);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__ASSOCIATED_CORRELATION_FIELDS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__IMPLEMENTED_INTERFACE);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__PROCESS_INTERFACES);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__WSDL_EVENT_ASSOCIATION);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__MULTI_INSTANCE_SCRIPTS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__EXPRESSION);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__INITIAL_PARAMETER_VALUE);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__PORT_TYPE_OPERATION);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__EXTERNAL_REFERENCE);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__PROCESS_RESOURCE_PATTERNS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__EVENT_HANDLER_INITIALISERS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__ACTIVITY_RESOURCE_PATTERNS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__DOCUMENT_OPERATION);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__DURATION_CALCULATION);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__CORRELATION_DATA_MAPPINGS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__TRANSFORM_SCRIPT);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__ERROR_THROWER_INFO);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__CATCH_ERROR_MAPPINGS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__CONDITIONAL_PARTICIPANT);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__TASK_LIBRARY_REFERENCE);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__FORM_IMPLEMENTATION);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__PARTICIPANT_QUERY);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__FAULT_MESSAGE);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__BUSINESS_PROCESS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__WSDL_GENERATION);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__DECISION_SERVICE);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__PARTICIPANT_SHARED_RESOURCE);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__CALENDAR_REFERENCE);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__SIGNAL_DATA);
            childrenFeatures
                    .add(XpdExtensionPackage.Literals.DOCUMENT_ROOT__RETRY);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__NAMESPACE_PREFIX_MAP);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__CORRELATION_TIMEOUT);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__VALIDATION_CONTROL);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__REPLY_IMMEDIATE_DATA_MAPPINGS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__CASE_REF_TYPE);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__REST_SERVICES);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__DYNAMIC_ORGANIZATION_MAPPINGS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__GLOBAL_DATA_OPERATION);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__BPM_RUNTIME_CONFIGURATION);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__REQUIRED_ACCESS_PRIVILEGES);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__CASE_SERVICE);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__AD_HOC_TASK_CONFIGURATION);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__SERVICE_PROCESS_CONFIGURATION);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__SCRIPT_DATA_MAPPER);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__LIKE_MAPPING_EXCLUSIONS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__REST_SERVICE_OPERATION);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__INPUT_MAPPINGS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DOCUMENT_ROOT__OUTPUT_MAPPINGS);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns DocumentRoot.gif. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/DocumentRoot")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((DocumentRoot) object).getDisplayName();
        return label == null || label.length() == 0
                ? getString("_UI_DocumentRoot_type") //$NON-NLS-1$
                : getString("_UI_DocumentRoot_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(DocumentRoot.class)) {
        case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTATION_TYPE:
        case XpdExtensionPackage.DOCUMENT_ROOT__CONTINUE_ON_TIMEOUT:
        case XpdExtensionPackage.DOCUMENT_ROOT__ALIAS:
        case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATELY:
        case XpdExtensionPackage.DOCUMENT_ROOT__INLINE_SUB_PROCESS:
        case XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENTATION_URL:
        case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTS:
        case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_IDENTIFIER_FIELD:
        case XpdExtensionPackage.DOCUMENT_ROOT__VISIBILITY:
        case XpdExtensionPackage.DOCUMENT_ROOT__SECURITY_PROFILE:
        case XpdExtensionPackage.DOCUMENT_ROOT__LANGUAGE:
        case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_VALUE_MAPPING:
        case XpdExtensionPackage.DOCUMENT_ROOT__TRANSPORT:
        case XpdExtensionPackage.DOCUMENT_ROOT__IS_CHAINED:
        case XpdExtensionPackage.DOCUMENT_ROOT__REQUIRE_NEW_TRANSACTION:
        case XpdExtensionPackage.DOCUMENT_ROOT__DISPLAY_NAME:
        case XpdExtensionPackage.DOCUMENT_ROOT__CATCH_THROW:
        case XpdExtensionPackage.DOCUMENT_ROOT__IS_REMOTE:
        case XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_BUSINESS_SERVICE:
        case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_CATEGORY:
        case XpdExtensionPackage.DOCUMENT_ROOT__GENERATED:
        case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_TO_ACTIVITY_ID:
        case XpdExtensionPackage.DOCUMENT_ROOT__SET_PERFORMER_IN_PROCESS:
        case XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_HEIGHT:
        case XpdExtensionPackage.DOCUMENT_ROOT__EMB_SUBPROC_OTHER_STATE_WIDTH:
        case XpdExtensionPackage.DOCUMENT_ROOT__API_END_POINT_PARTICIPANT:
        case XpdExtensionPackage.DOCUMENT_ROOT__REQUEST_ACTIVITY_ID:
        case XpdExtensionPackage.DOCUMENT_ROOT__TARGET_PRIMITIVE_PROPERTY:
        case XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_PRIMITIVE_PROPERTY:
        case XpdExtensionPackage.DOCUMENT_ROOT__XPD_MODEL_TYPE:
        case XpdExtensionPackage.DOCUMENT_ROOT__FLOW_ROUTING_STYLE:
        case XpdExtensionPackage.DOCUMENT_ROOT__NON_CANCELLING:
        case XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_DEADLINE_EVENT_ID:
        case XpdExtensionPackage.DOCUMENT_ROOT__START_STRATEGY:
        case XpdExtensionPackage.DOCUMENT_ROOT__OVERWRITE_ALREADY_MODIFIED_TASK_DATA:
        case XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_FLOW_STRATEGY:
        case XpdExtensionPackage.DOCUMENT_ROOT__SUSPEND_RESUME_WITH_PARENT:
        case XpdExtensionPackage.DOCUMENT_ROOT__SYSTEM_ERROR_ACTION:
        case XpdExtensionPackage.DOCUMENT_ROOT__BX_USE_UNQUALIFIED_PROPERTY_NAMES:
        case XpdExtensionPackage.DOCUMENT_ROOT__PUBLISH_AS_REST_SERVICE:
        case XpdExtensionPackage.DOCUMENT_ROOT__REST_ACTIVITY_ID:
        case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_HANDLER_ASYNCHRONOUS:
        case XpdExtensionPackage.DOCUMENT_ROOT__ALLOW_UNQUALIFIED_SUB_PROCESS_IDENTIFICATION:
        case XpdExtensionPackage.DOCUMENT_ROOT__IS_CASE_SERVICE:
        case XpdExtensionPackage.DOCUMENT_ROOT__IS_EVENT_SUB_PROCESS:
        case XpdExtensionPackage.DOCUMENT_ROOT__NON_INTERRUPTING_EVENT:
        case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATE_IMMEDIATELY:
        case XpdExtensionPackage.DOCUMENT_ROOT__ASYNC_EXECUTION_MODE:
        case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_TYPE:
        case XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING:
        case XpdExtensionPackage.DOCUMENT_ROOT__SOURCE_CONTRIBUTOR_ID:
        case XpdExtensionPackage.DOCUMENT_ROOT__TARGET_CONTRIBUTOR_ID:
        case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_SERVICE_PUBLISH_TYPE:
        case XpdExtensionPackage.DOCUMENT_ROOT__SUPPRESS_MAX_MAPPINGS_ERROR:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
            return;
        case XpdExtensionPackage.DOCUMENT_ROOT__DATA_OBJECT_ATTRIBUTES:
        case XpdExtensionPackage.DOCUMENT_ROOT__EXTENDED_ATTRIBUTES:
        case XpdExtensionPackage.DOCUMENT_ROOT__CONSTANT_PERIOD:
        case XpdExtensionPackage.DOCUMENT_ROOT__USER_TASK_SCRIPTS:
        case XpdExtensionPackage.DOCUMENT_ROOT__AUDIT:
        case XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT:
        case XpdExtensionPackage.DOCUMENT_ROOT__ASSOCIATED_CORRELATION_FIELDS:
        case XpdExtensionPackage.DOCUMENT_ROOT__IMPLEMENTED_INTERFACE:
        case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_INTERFACES:
        case XpdExtensionPackage.DOCUMENT_ROOT__WSDL_EVENT_ASSOCIATION:
        case XpdExtensionPackage.DOCUMENT_ROOT__MULTI_INSTANCE_SCRIPTS:
        case XpdExtensionPackage.DOCUMENT_ROOT__EXPRESSION:
        case XpdExtensionPackage.DOCUMENT_ROOT__INITIAL_PARAMETER_VALUE:
        case XpdExtensionPackage.DOCUMENT_ROOT__PORT_TYPE_OPERATION:
        case XpdExtensionPackage.DOCUMENT_ROOT__EXTERNAL_REFERENCE:
        case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_RESOURCE_PATTERNS:
        case XpdExtensionPackage.DOCUMENT_ROOT__EVENT_HANDLER_INITIALISERS:
        case XpdExtensionPackage.DOCUMENT_ROOT__ACTIVITY_RESOURCE_PATTERNS:
        case XpdExtensionPackage.DOCUMENT_ROOT__DOCUMENT_OPERATION:
        case XpdExtensionPackage.DOCUMENT_ROOT__DURATION_CALCULATION:
        case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_DATA_MAPPINGS:
        case XpdExtensionPackage.DOCUMENT_ROOT__TRANSFORM_SCRIPT:
        case XpdExtensionPackage.DOCUMENT_ROOT__ERROR_THROWER_INFO:
        case XpdExtensionPackage.DOCUMENT_ROOT__CATCH_ERROR_MAPPINGS:
        case XpdExtensionPackage.DOCUMENT_ROOT__CONDITIONAL_PARTICIPANT:
        case XpdExtensionPackage.DOCUMENT_ROOT__TASK_LIBRARY_REFERENCE:
        case XpdExtensionPackage.DOCUMENT_ROOT__FORM_IMPLEMENTATION:
        case XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_QUERY:
        case XpdExtensionPackage.DOCUMENT_ROOT__FAULT_MESSAGE:
        case XpdExtensionPackage.DOCUMENT_ROOT__BUSINESS_PROCESS:
        case XpdExtensionPackage.DOCUMENT_ROOT__WSDL_GENERATION:
        case XpdExtensionPackage.DOCUMENT_ROOT__DECISION_SERVICE:
        case XpdExtensionPackage.DOCUMENT_ROOT__PARTICIPANT_SHARED_RESOURCE:
        case XpdExtensionPackage.DOCUMENT_ROOT__CALENDAR_REFERENCE:
        case XpdExtensionPackage.DOCUMENT_ROOT__SIGNAL_DATA:
        case XpdExtensionPackage.DOCUMENT_ROOT__RETRY:
        case XpdExtensionPackage.DOCUMENT_ROOT__NAMESPACE_PREFIX_MAP:
        case XpdExtensionPackage.DOCUMENT_ROOT__CORRELATION_TIMEOUT:
        case XpdExtensionPackage.DOCUMENT_ROOT__VALIDATION_CONTROL:
        case XpdExtensionPackage.DOCUMENT_ROOT__REPLY_IMMEDIATE_DATA_MAPPINGS:
        case XpdExtensionPackage.DOCUMENT_ROOT__CASE_REF_TYPE:
        case XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICES:
        case XpdExtensionPackage.DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT:
        case XpdExtensionPackage.DOCUMENT_ROOT__DYNAMIC_ORGANIZATION_MAPPINGS:
        case XpdExtensionPackage.DOCUMENT_ROOT__GLOBAL_DATA_OPERATION:
        case XpdExtensionPackage.DOCUMENT_ROOT__PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS:
        case XpdExtensionPackage.DOCUMENT_ROOT__BPM_RUNTIME_CONFIGURATION:
        case XpdExtensionPackage.DOCUMENT_ROOT__REQUIRED_ACCESS_PRIVILEGES:
        case XpdExtensionPackage.DOCUMENT_ROOT__CASE_SERVICE:
        case XpdExtensionPackage.DOCUMENT_ROOT__AD_HOC_TASK_CONFIGURATION:
        case XpdExtensionPackage.DOCUMENT_ROOT__SERVICE_PROCESS_CONFIGURATION:
        case XpdExtensionPackage.DOCUMENT_ROOT__SCRIPT_DATA_MAPPER:
        case XpdExtensionPackage.DOCUMENT_ROOT__LIKE_MAPPING_EXCLUSIONS:
        case XpdExtensionPackage.DOCUMENT_ROOT__REST_SERVICE_OPERATION:
        case XpdExtensionPackage.DOCUMENT_ROOT__INPUT_MAPPINGS:
        case XpdExtensionPackage.DOCUMENT_ROOT__OUTPUT_MAPPINGS:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing the children that can be created under this object. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__DATA_OBJECT_ATTRIBUTES,
                XpdExtensionFactory.eINSTANCE
                        .createXpdExtDataObjectAttributes()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__EXTENDED_ATTRIBUTES,
                XpdExtensionFactory.eINSTANCE.createXpdExtAttributes()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__CONSTANT_PERIOD,
                XpdExtensionFactory.eINSTANCE.createConstantPeriod()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__USER_TASK_SCRIPTS,
                XpdExtensionFactory.eINSTANCE.createUserTaskScripts()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__AUDIT,
                XpdExtensionFactory.eINSTANCE.createAudit()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SCRIPT,
                XpdExtensionFactory.eINSTANCE.createScriptInformation()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__ASSOCIATED_CORRELATION_FIELDS,
                XpdExtensionFactory.eINSTANCE
                        .createAssociatedCorrelationFields()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__IMPLEMENTED_INTERFACE,
                XpdExtensionFactory.eINSTANCE.createImplementedInterface()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__PROCESS_INTERFACES,
                XpdExtensionFactory.eINSTANCE.createProcessInterfaces()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__WSDL_EVENT_ASSOCIATION,
                XpdExtensionFactory.eINSTANCE.createWsdlEventAssociation()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__MULTI_INSTANCE_SCRIPTS,
                XpdExtensionFactory.eINSTANCE.createMultiInstanceScripts()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__EXPRESSION,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__EXPRESSION,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__INITIAL_PARAMETER_VALUE,
                XpdExtensionFactory.eINSTANCE.createInitialParameterValue()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__PORT_TYPE_OPERATION,
                XpdExtensionFactory.eINSTANCE.createPortTypeOperation()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__EXTERNAL_REFERENCE,
                Xpdl2Factory.eINSTANCE.createExternalReference()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__PROCESS_RESOURCE_PATTERNS,
                XpdExtensionFactory.eINSTANCE.createProcessResourcePatterns()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__EVENT_HANDLER_INITIALISERS,
                XpdExtensionFactory.eINSTANCE
                        .createEventHandlerInitialisers()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__ACTIVITY_RESOURCE_PATTERNS,
                XpdExtensionFactory.eINSTANCE
                        .createActivityResourcePatterns()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__DOCUMENT_OPERATION,
                XpdExtensionFactory.eINSTANCE.createDocumentOperation()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__DURATION_CALCULATION,
                XpdExtensionFactory.eINSTANCE.createDurationCalculation()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__CORRELATION_DATA_MAPPINGS,
                XpdExtensionFactory.eINSTANCE.createCorrelationDataMappings()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__TRANSFORM_SCRIPT,
                XpdExtensionFactory.eINSTANCE.createTransformScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__ERROR_THROWER_INFO,
                XpdExtensionFactory.eINSTANCE.createErrorThrowerInfo()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__CATCH_ERROR_MAPPINGS,
                XpdExtensionFactory.eINSTANCE.createCatchErrorMappings()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__CONDITIONAL_PARTICIPANT,
                XpdExtensionFactory.eINSTANCE.createConditionalParticipant()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__TASK_LIBRARY_REFERENCE,
                XpdExtensionFactory.eINSTANCE.createTaskLibraryReference()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__FORM_IMPLEMENTATION,
                XpdExtensionFactory.eINSTANCE.createFormImplementation()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__PARTICIPANT_QUERY,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__PARTICIPANT_QUERY,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__FAULT_MESSAGE,
                XpdExtensionFactory.eINSTANCE.createFaultMessage()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__FAULT_MESSAGE,
                Xpdl2Factory.eINSTANCE.createMessage()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__BUSINESS_PROCESS,
                XpdExtensionFactory.eINSTANCE.createBusinessProcess()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__WSDL_GENERATION,
                XpdExtensionFactory.eINSTANCE.createWsdlGeneration()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__DECISION_SERVICE,
                Xpdl2Factory.eINSTANCE.createSubFlow()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__PARTICIPANT_SHARED_RESOURCE,
                XpdExtensionFactory.eINSTANCE
                        .createParticipantSharedResource()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__CALENDAR_REFERENCE,
                XpdExtensionFactory.eINSTANCE.createCalendarReference()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SIGNAL_DATA,
                XpdExtensionFactory.eINSTANCE.createSignalData()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__RETRY,
                XpdExtensionFactory.eINSTANCE.createRetry()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__NAMESPACE_PREFIX_MAP,
                XpdExtensionFactory.eINSTANCE.createNamespacePrefixMap()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__CORRELATION_TIMEOUT,
                XpdExtensionFactory.eINSTANCE.createConstantPeriod()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__VALIDATION_CONTROL,
                XpdExtensionFactory.eINSTANCE.createValidationControl()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__REPLY_IMMEDIATE_DATA_MAPPINGS,
                XpdExtensionFactory.eINSTANCE
                        .createReplyImmediateDataMappings()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__CASE_REF_TYPE,
                Xpdl2Factory.eINSTANCE.createRecordType()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__REST_SERVICES,
                XpdExtensionFactory.eINSTANCE.createRESTServices()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__DYNAMIC_ORGANIZATION_MAPPINGS,
                XpdExtensionFactory.eINSTANCE
                        .createDynamicOrganizationMappings()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__GLOBAL_DATA_OPERATION,
                XpdExtensionFactory.eINSTANCE.createGlobalDataOperation()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS,
                XpdExtensionFactory.eINSTANCE
                        .createProcessDataWorkItemAttributeMappings()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__BPM_RUNTIME_CONFIGURATION,
                XpdExtensionFactory.eINSTANCE.createBpmRuntimeConfiguration()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__REQUIRED_ACCESS_PRIVILEGES,
                XpdExtensionFactory.eINSTANCE
                        .createRequiredAccessPrivileges()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__CASE_SERVICE,
                XpdExtensionFactory.eINSTANCE.createCaseService()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__AD_HOC_TASK_CONFIGURATION,
                XpdExtensionFactory.eINSTANCE
                        .createAdHocTaskConfigurationType()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SERVICE_PROCESS_CONFIGURATION,
                XpdExtensionFactory.eINSTANCE
                        .createServiceProcessConfiguration()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__SCRIPT_DATA_MAPPER,
                XpdExtensionFactory.eINSTANCE.createScriptDataMapper()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__LIKE_MAPPING_EXCLUSIONS,
                XpdExtensionFactory.eINSTANCE.createLikeMappingExclusions()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__REST_SERVICE_OPERATION,
                XpdExtensionFactory.eINSTANCE.createRestServiceOperation()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__INPUT_MAPPINGS,
                XpdExtensionFactory.eINSTANCE.createScriptDataMapper()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DOCUMENT_ROOT__OUTPUT_MAPPINGS,
                XpdExtensionFactory.eINSTANCE.createScriptDataMapper()));
    }

    /**
     * This returns the label text for
     * {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child,
            Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify =
                childFeature == XpdExtensionPackage.Literals.DOCUMENT_ROOT__CONSTANT_PERIOD
                        || childFeature == XpdExtensionPackage.Literals.DOCUMENT_ROOT__CORRELATION_TIMEOUT
                        || childFeature == XpdExtensionPackage.Literals.DOCUMENT_ROOT__EXPRESSION
                        || childFeature == XpdExtensionPackage.Literals.DOCUMENT_ROOT__PARTICIPANT_QUERY
                        || childFeature == XpdExtensionPackage.Literals.DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT
                        || childFeature == XpdExtensionPackage.Literals.DOCUMENT_ROOT__SCRIPT_DATA_MAPPER
                        || childFeature == XpdExtensionPackage.Literals.DOCUMENT_ROOT__INPUT_MAPPINGS
                        || childFeature == XpdExtensionPackage.Literals.DOCUMENT_ROOT__OUTPUT_MAPPINGS;

        if (qualify) {
            return getString("_UI_CreateChild_text2", //$NON-NLS-1$
                    new Object[] { getTypeText(childObject),
                            getFeatureText(childFeature), getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return XpdExtensionEditPlugin.INSTANCE;
    }

}
