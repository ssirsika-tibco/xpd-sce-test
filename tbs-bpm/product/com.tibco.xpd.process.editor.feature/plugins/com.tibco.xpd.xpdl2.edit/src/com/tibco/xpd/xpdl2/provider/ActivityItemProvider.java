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

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.CommandsUtils;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.Activity} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class ActivityItemProvider extends NamedElementItemProvider {
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
    public ActivityItemProvider(AdapterFactory adapterFactory) {
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

            addDescriptionPropertyDescriptor(object);
            addFinishModePropertyDescriptor(object);
            addIsATransactionPropertyDescriptor(object);
            addStartActivityPropertyDescriptor(object);
            addStartModePropertyDescriptor(object);
            addStartQuantityPropertyDescriptor(object);
            addStatusPropertyDescriptor(object);
            addProcessPropertyDescriptor(object);
            addIsForCompensationPropertyDescriptor(object);
            addCompletionQuantityPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Description feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDescriptionPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_DescribedElement_description_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_DescribedElement_description_feature", //$NON-NLS-1$
                                "_UI_DescribedElement_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.DESCRIBED_ELEMENT__DESCRIPTION,
                        true,
                        false,
                        false,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Finish Mode feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addFinishModePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_Activity_finishMode_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_Activity_finishMode_feature", "_UI_Activity_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Xpdl2Package.Literals.ACTIVITY__FINISH_MODE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Is ATransaction feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addIsATransactionPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Activity_isATransaction_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_Activity_isATransaction_feature", //$NON-NLS-1$
                                "_UI_Activity_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.ACTIVITY__IS_ATRANSACTION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Start Activity feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addStartActivityPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Activity_startActivity_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_Activity_startActivity_feature", //$NON-NLS-1$
                                "_UI_Activity_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.ACTIVITY__START_ACTIVITY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Start Mode feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addStartModePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_Activity_startMode_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_Activity_startMode_feature", "_UI_Activity_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Xpdl2Package.Literals.ACTIVITY__START_MODE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Start Quantity feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addStartQuantityPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Activity_startQuantity_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_Activity_startQuantity_feature", //$NON-NLS-1$
                                "_UI_Activity_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.ACTIVITY__START_QUANTITY,
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
                getString("_UI_Activity_status_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_Activity_status_feature", "_UI_Activity_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Xpdl2Package.Literals.ACTIVITY__STATUS,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Process feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addProcessPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_Activity_process_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_Activity_process_feature", "_UI_Activity_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Xpdl2Package.Literals.ACTIVITY__PROCESS,
                false,
                false,
                false,
                null,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Is For Compensation feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addIsForCompensationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Activity_isForCompensation_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_Activity_isForCompensation_feature", //$NON-NLS-1$
                                "_UI_Activity_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.ACTIVITY__IS_FOR_COMPENSATION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Completion Quantity feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addCompletionQuantityPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Activity_completionQuantity_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_Activity_completionQuantity_feature", //$NON-NLS-1$
                                "_UI_Activity_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.ACTIVITY__COMPLETION_QUANTITY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
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
            childrenFeatures.add(Xpdl2Package.Literals.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES);
            childrenFeatures.add(Xpdl2Package.Literals.GRAPHICAL_NODE__NODE_GRAPHICS_INFOS);
            childrenFeatures.add(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS);
            childrenFeatures.add(Xpdl2Package.Literals.DATA_FIELDS_CONTAINER__DATA_FIELDS);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__LIMIT);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__ROUTE);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__IMPLEMENTATION);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__BLOCK_ACTIVITY);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__EVENT);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__TRANSACTION);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__PERFORMER);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__PERFORMERS);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__PRIORITY);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__DEADLINE);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__SIMULATION_INFORMATION);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__ICON);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__DOCUMENTATION);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__TRANSITION_RESTRICTIONS);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__INPUT_SETS);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__OUTPUT_SETS);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__IO_RULES);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__LOOP);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__ASSIGNMENTS);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__OBJECT);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY__EXTENSIONS);
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
     * This returns Activity.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        String img = Xpdl2ModelUtil.getActivityImagePath((Activity) object);

        return overlayImage(object, getResourceLocator().getImage(img));
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
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

        switch (notification.getFeatureID(Activity.class)) {
        case Xpdl2Package.ACTIVITY__DESCRIPTION:
        case Xpdl2Package.ACTIVITY__FINISH_MODE:
        case Xpdl2Package.ACTIVITY__IS_ATRANSACTION:
        case Xpdl2Package.ACTIVITY__START_ACTIVITY:
        case Xpdl2Package.ACTIVITY__START_MODE:
        case Xpdl2Package.ACTIVITY__START_QUANTITY:
        case Xpdl2Package.ACTIVITY__STATUS:
        case Xpdl2Package.ACTIVITY__IS_FOR_COMPENSATION:
        case Xpdl2Package.ACTIVITY__COMPLETION_QUANTITY:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case Xpdl2Package.ACTIVITY__EXTENDED_ATTRIBUTES:
        case Xpdl2Package.ACTIVITY__NODE_GRAPHICS_INFOS:
        case Xpdl2Package.ACTIVITY__OTHER_ELEMENTS:
        case Xpdl2Package.ACTIVITY__DATA_FIELDS:
        case Xpdl2Package.ACTIVITY__LIMIT:
        case Xpdl2Package.ACTIVITY__ROUTE:
        case Xpdl2Package.ACTIVITY__IMPLEMENTATION:
        case Xpdl2Package.ACTIVITY__BLOCK_ACTIVITY:
        case Xpdl2Package.ACTIVITY__EVENT:
        case Xpdl2Package.ACTIVITY__TRANSACTION:
        case Xpdl2Package.ACTIVITY__PERFORMER:
        case Xpdl2Package.ACTIVITY__PERFORMERS:
        case Xpdl2Package.ACTIVITY__PRIORITY:
        case Xpdl2Package.ACTIVITY__DEADLINE:
        case Xpdl2Package.ACTIVITY__SIMULATION_INFORMATION:
        case Xpdl2Package.ACTIVITY__ICON:
        case Xpdl2Package.ACTIVITY__DOCUMENTATION:
        case Xpdl2Package.ACTIVITY__TRANSITION_RESTRICTIONS:
        case Xpdl2Package.ACTIVITY__INPUT_SETS:
        case Xpdl2Package.ACTIVITY__OUTPUT_SETS:
        case Xpdl2Package.ACTIVITY__IO_RULES:
        case Xpdl2Package.ACTIVITY__LOOP:
        case Xpdl2Package.ACTIVITY__ASSIGNMENTS:
        case Xpdl2Package.ACTIVITY__OBJECT:
        case Xpdl2Package.ACTIVITY__EXTENSIONS:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds to the collection of
     * {@link org.eclipse.emf.edit.command.CommandParameter}s describing all of
     * the children that can be created under this object. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.eINSTANCE.getExtendedAttributesContainer_ExtendedAttributes(),
                        Xpdl2Factory.eINSTANCE.createExtendedAttribute()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getGraphicalNode_NodeGraphicsInfos(),
                Xpdl2Factory.eINSTANCE.createNodeGraphicsInfo()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getDescribedElement_Description(),
                Xpdl2Factory.eINSTANCE.createDescription()));

        newChildDescriptors.add(
                createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Limit(), Xpdl2Factory.eINSTANCE.createLimit()));

        newChildDescriptors.add(
                createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Route(), Xpdl2Factory.eINSTANCE.createRoute()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_BlockActivity(),
                Xpdl2Factory.eINSTANCE.createBlockActivity()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Transaction(),
                Xpdl2Factory.eINSTANCE.createTransaction()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Performer(),
                Xpdl2Factory.eINSTANCE.createPerformer()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Performers(),
                Xpdl2Factory.eINSTANCE.createPerformer()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Priority(),
                Xpdl2Factory.eINSTANCE.createPriority()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Deadline(),
                Xpdl2Factory.eINSTANCE.createDeadline()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_SimulationInformation(),
                Xpdl2Factory.eINSTANCE.createSimulationInformation()));

        newChildDescriptors.add(
                createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Icon(), Xpdl2Factory.eINSTANCE.createIcon()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Documentation(),
                Xpdl2Factory.eINSTANCE.createDocumentation()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_TransitionRestrictions(),
                Xpdl2Factory.eINSTANCE.createTransitionRestriction()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_InputSets(),
                Xpdl2Factory.eINSTANCE.createInputSet()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_OutputSets(),
                Xpdl2Factory.eINSTANCE.createOutputSet()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_IoRules(),
                Xpdl2Factory.eINSTANCE.createIORules()));

        newChildDescriptors.add(
                createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Loop(), Xpdl2Factory.eINSTANCE.createLoop()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Assignments(),
                Xpdl2Factory.eINSTANCE.createAssignment()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Object(),
                Xpdl2Factory.eINSTANCE.createObject()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Extensions(),
                Xpdl2Factory.eINSTANCE.createApplicationType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Extensions(),
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Extensions(),
                Xpdl2Factory.eINSTANCE.createMessage()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Extensions(),
                Xpdl2Factory.eINSTANCE.createPackage()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Extensions(),
                Xpdl2Factory.eINSTANCE.createDataFieldsContainer()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getActivity_Extensions(),
                Xpdl2Factory.eINSTANCE.createProcess()));
    }

    @Override
    protected Command createAddCommand(EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection, int index) {
        Command cmd = super.createAddCommand(domain, owner, feature, collection, index);
        cmd = CommandsUtils.checkExternalAddWrappers(cmd, domain, owner, feature, collection, index);
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
