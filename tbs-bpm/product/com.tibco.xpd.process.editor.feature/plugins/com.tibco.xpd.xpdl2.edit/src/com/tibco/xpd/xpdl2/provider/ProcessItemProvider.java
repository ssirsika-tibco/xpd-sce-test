/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.CommandsUtils;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.Process} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class ProcessItemProvider extends NamedElementItemProvider {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ProcessItemProvider(AdapterFactory adapterFactory) {
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

            addAdHocPropertyDescriptor(object);
            addAdHocCompletionConditionPropertyDescriptor(object);
            addAdHocOrderingPropertyDescriptor(object);
            addDefaultStartActivityIdPropertyDescriptor(object);
            addAccessLevelPropertyDescriptor(object);
            addDefaultStartActivitySetIdPropertyDescriptor(object);
            addEnableInstanceCompensationPropertyDescriptor(object);
            addProcessTypePropertyDescriptor(object);
            addStatusPropertyDescriptor(object);
            addSuppressJoinFailurePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Ad Hoc feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addAdHocPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_FlowContainer_adHoc_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_FlowContainer_adHoc_feature", //$NON-NLS-1$
                                "_UI_FlowContainer_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.FLOW_CONTAINER__AD_HOC,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Ad Hoc Completion Condition feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addAdHocCompletionConditionPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_FlowContainer_adHocCompletionCondition_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_FlowContainer_adHocCompletionCondition_feature", //$NON-NLS-1$
                                "_UI_FlowContainer_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.FLOW_CONTAINER__AD_HOC_COMPLETION_CONDITION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Ad Hoc Ordering feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addAdHocOrderingPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_FlowContainer_adHocOrdering_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_FlowContainer_adHocOrdering_feature", //$NON-NLS-1$
                                "_UI_FlowContainer_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.FLOW_CONTAINER__AD_HOC_ORDERING,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Default Start Activity Id feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addDefaultStartActivityIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_FlowContainer_defaultStartActivityId_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_FlowContainer_defaultStartActivityId_feature", //$NON-NLS-1$
                                "_UI_FlowContainer_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.FLOW_CONTAINER__DEFAULT_START_ACTIVITY_ID,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Access Level feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addAccessLevelPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_Process_accessLevel_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_Process_accessLevel_feature", "_UI_Process_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Xpdl2Package.Literals.PROCESS__ACCESS_LEVEL,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Default Start Activity Set Id feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addDefaultStartActivitySetIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Process_defaultStartActivitySetId_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_Process_defaultStartActivitySetId_feature", //$NON-NLS-1$
                                "_UI_Process_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.PROCESS__DEFAULT_START_ACTIVITY_SET_ID,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Enable Instance Compensation feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addEnableInstanceCompensationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Process_enableInstanceCompensation_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_Process_enableInstanceCompensation_feature", //$NON-NLS-1$
                                "_UI_Process_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.PROCESS__ENABLE_INSTANCE_COMPENSATION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Process Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addProcessTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_Process_processType_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_Process_processType_feature", "_UI_Process_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Xpdl2Package.Literals.PROCESS__PROCESS_TYPE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Status feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addStatusPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_Process_status_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_Process_status_feature", "_UI_Process_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Xpdl2Package.Literals.PROCESS__STATUS,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Suppress Join Failure feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addSuppressJoinFailurePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Process_suppressJoinFailure_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_Process_suppressJoinFailure_feature", //$NON-NLS-1$
                                "_UI_Process_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.PROCESS__SUPPRESS_JOIN_FAILURE,
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
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(Xpdl2Package.Literals.FLOW_CONTAINER__ACTIVITIES);
            childrenFeatures.add(Xpdl2Package.Literals.FLOW_CONTAINER__TRANSITIONS);
            childrenFeatures.add(Xpdl2Package.Literals.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES);
            childrenFeatures.add(Xpdl2Package.Literals.FORMAL_PARAMETERS_CONTAINER__FORMAL_PARAMETERS);
            childrenFeatures.add(Xpdl2Package.Literals.ASSIGMENTS_CONTAINER__ASSIGNMENTS);
            childrenFeatures.add(Xpdl2Package.Literals.DATA_FIELDS_CONTAINER__DATA_FIELDS);
            childrenFeatures.add(Xpdl2Package.Literals.PARTICIPANTS_CONTAINER__PARTICIPANTS);
            childrenFeatures.add(Xpdl2Package.Literals.APPLICATIONS_CONTAINER__APPLICATIONS);
            childrenFeatures.add(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS);
            childrenFeatures.add(Xpdl2Package.Literals.PROCESS__PROCESS_HEADER);
            childrenFeatures.add(Xpdl2Package.Literals.PROCESS__REDEFINABLE_HEADER);
            childrenFeatures.add(Xpdl2Package.Literals.PROCESS__PARTNER_LINKS);
            childrenFeatures.add(Xpdl2Package.Literals.PROCESS__OBJECT);
            childrenFeatures.add(Xpdl2Package.Literals.PROCESS__EXTENSIONS);
            childrenFeatures.add(Xpdl2Package.Literals.PROCESS__ACTIVITY_SETS);
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
     * This returns Process.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {

        if (object instanceof Process && Xpdl2ModelUtil.isCaseService((Process) object)) {

            return overlayImage(object, getResourceLocator().getImage("full/obj16/CaseService.png")); //$NON-NLS-1$
        } else if (object instanceof Process && Xpdl2ModelUtil.isPageflowBusinessService((Process) object)) {
            return overlayImage(object, getResourceLocator().getImage("full/obj16/ProcessBusinessService.png")); //$NON-NLS-1$
        } else if (object instanceof Process && Xpdl2ModelUtil.isPageflow(((Process) object))) {
            return overlayImage(object, getResourceLocator().getImage("full/obj16/ProcessPageflow")); //$NON-NLS-1$
        } else if (object instanceof Process && Xpdl2ModelUtil.isServiceProcess((Process) object)) {

            return overlayImage(object, getResourceLocator().getImage("full/obj16/ServiceProcess.png")); //$NON-NLS-1$
        } else if (Xpdl2ModelUtil.isTaskLibrary((Process) object)) {
            return overlayImage(object, getResourceLocator().getImage("full/obj16/TaskLibrary")); //$NON-NLS-1$
        } else if (DecisionFlowUtil.isDecisionFlow((Process) object)) {
            return overlayImage(object, getResourceLocator().getImage("full/obj16/DecisionFlow")); //$NON-NLS-1$
        }
        return overlayImage(object, getResourceLocator().getImage("full/obj16/ProcessBusiness")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public String getText(Object object) {
        return super.getText(object);
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

        switch (notification.getFeatureID(com.tibco.xpd.xpdl2.Process.class)) {
        case Xpdl2Package.PROCESS__AD_HOC:
        case Xpdl2Package.PROCESS__AD_HOC_COMPLETION_CONDITION:
        case Xpdl2Package.PROCESS__AD_HOC_ORDERING:
        case Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_ID:
        case Xpdl2Package.PROCESS__ACCESS_LEVEL:
        case Xpdl2Package.PROCESS__DEFAULT_START_ACTIVITY_SET_ID:
        case Xpdl2Package.PROCESS__ENABLE_INSTANCE_COMPENSATION:
        case Xpdl2Package.PROCESS__PROCESS_TYPE:
        case Xpdl2Package.PROCESS__STATUS:
        case Xpdl2Package.PROCESS__SUPPRESS_JOIN_FAILURE:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case Xpdl2Package.PROCESS__ACTIVITIES:
        case Xpdl2Package.PROCESS__TRANSITIONS:
        case Xpdl2Package.PROCESS__EXTENDED_ATTRIBUTES:
        case Xpdl2Package.PROCESS__FORMAL_PARAMETERS:
        case Xpdl2Package.PROCESS__ASSIGNMENTS:
        case Xpdl2Package.PROCESS__DATA_FIELDS:
        case Xpdl2Package.PROCESS__PARTICIPANTS:
        case Xpdl2Package.PROCESS__APPLICATIONS:
        case Xpdl2Package.PROCESS__OTHER_ELEMENTS:
        case Xpdl2Package.PROCESS__PROCESS_HEADER:
        case Xpdl2Package.PROCESS__REDEFINABLE_HEADER:
        case Xpdl2Package.PROCESS__PARTNER_LINKS:
        case Xpdl2Package.PROCESS__OBJECT:
        case Xpdl2Package.PROCESS__EXTENSIONS:
        case Xpdl2Package.PROCESS__ACTIVITY_SETS:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.FLOW_CONTAINER__ACTIVITIES,
                Xpdl2Factory.eINSTANCE.createActivity()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.FLOW_CONTAINER__TRANSITIONS,
                Xpdl2Factory.eINSTANCE.createTransition()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES,
                        Xpdl2Factory.eINSTANCE.createExtendedAttribute()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.FORMAL_PARAMETERS_CONTAINER__FORMAL_PARAMETERS,
                        Xpdl2Factory.eINSTANCE.createFormalParameter()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.ASSIGMENTS_CONTAINER__ASSIGNMENTS,
                Xpdl2Factory.eINSTANCE.createAssignment()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.DATA_FIELDS_CONTAINER__DATA_FIELDS,
                Xpdl2Factory.eINSTANCE.createDataField()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PARTICIPANTS_CONTAINER__PARTICIPANTS,
                Xpdl2Factory.eINSTANCE.createParticipant()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.APPLICATIONS_CONTAINER__APPLICATIONS,
                Xpdl2Factory.eINSTANCE.createApplication()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__PROCESS_HEADER,
                Xpdl2Factory.eINSTANCE.createProcessHeader()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__REDEFINABLE_HEADER,
                Xpdl2Factory.eINSTANCE.createRedefinableHeader()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__PARTNER_LINKS,
                Xpdl2Factory.eINSTANCE.createPartnerLink()));

        newChildDescriptors.add(
                createChildParameter(Xpdl2Package.Literals.PROCESS__OBJECT, Xpdl2Factory.eINSTANCE.createObject()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__EXTENSIONS,
                Xpdl2Factory.eINSTANCE.createApplicationType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__EXTENSIONS,
                Xpdl2Factory.eINSTANCE.createArtifactInput()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__EXTENSIONS,
                Xpdl2Factory.eINSTANCE.createDataFieldsContainer()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__EXTENSIONS,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__EXTENSIONS,
                Xpdl2Factory.eINSTANCE.createMessage()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__EXTENSIONS,
                Xpdl2Factory.eINSTANCE.createPackage()));

        newChildDescriptors.add(
                createChildParameter(Xpdl2Package.Literals.PROCESS__EXTENSIONS, Xpdl2Factory.eINSTANCE.createPage()));

        newChildDescriptors.add(
                createChildParameter(Xpdl2Package.Literals.PROCESS__EXTENSIONS, Xpdl2Factory.eINSTANCE.createPages()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__EXTENSIONS,
                Xpdl2Factory.eINSTANCE.createProcess()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__EXTENSIONS,
                Xpdl2Factory.eINSTANCE.createPropertyInput()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__EXTENSIONS,
                Xpdl2Factory.eINSTANCE.createResourceCosts()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__EXTENSIONS,
                XMLTypeFactory.eINSTANCE.createAnyType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PROCESS__ACTIVITY_SETS,
                Xpdl2Factory.eINSTANCE.createActivitySet()));
    }

    @Override
    protected Command createAddCommand(EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection, int index) {
        Command cmd = super.createAddCommand(domain, owner, feature, collection, index);
        cmd = CommandsUtils.checkExternalAddWrappers(cmd, domain, owner, feature, collection, index);
        return cmd;
    }

    @Override
    protected Command createRemoveCommand(EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection) {
        Command cmd = super.createRemoveCommand(domain, owner, feature, collection);
        cmd = CommandsUtils.checkExternalDeleteWrappers(cmd, domain, owner, feature, collection);
        return cmd;
    }

    @Override
    protected Command createSetCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value,
            int index) {
        Command cmd = super.createSetCommand(domain, owner, feature, value, index);
        cmd = CommandsUtils.checkExternalSetWrappers(cmd, domain, owner, feature, value, index);
        return cmd;
    }

}
