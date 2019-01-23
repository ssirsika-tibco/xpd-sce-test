/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.provider;

import com.tibco.xpd.simulation.ExpressionType;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;

import com.tibco.xpd.xpdl2.Xpdl2Factory;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
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
 * This is the item provider adapter for a {@link com.tibco.xpd.simulation.ExpressionType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ExpressionTypeItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExpressionTypeItemProvider(AdapterFactory adapterFactory) {
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

            addScriptExpressionPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Script Expression feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addScriptExpressionPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_ExpressionType_scriptExpression_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_ExpressionType_scriptExpression_feature", "_UI_ExpressionType_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimulationPackage.Literals.EXPRESSION_TYPE__SCRIPT_EXPRESSION,
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures
                    .add(SimulationPackage.Literals.EXPRESSION_TYPE__ENUM_BASED_EXPRESSION);
            childrenFeatures
                    .add(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION);
            childrenFeatures
                    .add(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns ExpressionType.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator()
                .getImage("full/obj16/ExpressionType")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((ExpressionType) object).getScriptExpression();
        return label == null || label.length() == 0 ? getString("_UI_ExpressionType_type") : //$NON-NLS-1$
                getString("_UI_ExpressionType_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(ExpressionType.class)) {
        case SimulationPackage.EXPRESSION_TYPE__SCRIPT_EXPRESSION:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
            return;
        case SimulationPackage.EXPRESSION_TYPE__ENUM_BASED_EXPRESSION:
        case SimulationPackage.EXPRESSION_TYPE__STRUCTURED_EXPRESSION:
        case SimulationPackage.EXPRESSION_TYPE__DEFAULT:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), true, false));
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
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__ENUM_BASED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createEnumBasedExpressionType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createActivitySimulationDataType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createLoopControlTransitionType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE.createLoopControlType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createMaxElapseTimeStrategyType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createMaxLoopCountStrategyType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createParameterDistribution()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createParticipantSimulationDataType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createSimulationRealDistributionType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createSplitSimulationDataType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createStartSimulationDataType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createStructuredConditionType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE.createTimeUnitCostType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createTransitionSimulationDataType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createNormalDistributionStrategyType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        SimulationFactory.eINSTANCE
                                .createWorkflowProcessSimulationDataType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        Xpdl2Factory.eINSTANCE.createApplicationType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        Xpdl2Factory.eINSTANCE.createArtifactInput()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        Xpdl2Factory.eINSTANCE.createDataFieldsContainer()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        Xpdl2Factory.eINSTANCE.createMessage()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        Xpdl2Factory.eINSTANCE.createPackage()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        Xpdl2Factory.eINSTANCE.createPage()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        Xpdl2Factory.eINSTANCE.createPages()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        Xpdl2Factory.eINSTANCE.createProcess()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        Xpdl2Factory.eINSTANCE.createPropertyInput()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        Xpdl2Factory.eINSTANCE.createResourceCosts()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION,
                        XMLTypeFactory.eINSTANCE.createAnyType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createActivitySimulationDataType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createLoopControlTransitionType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE.createLoopControlType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createMaxElapseTimeStrategyType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createMaxLoopCountStrategyType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createParameterDistribution()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createParticipantSimulationDataType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createSimulationRealDistributionType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createSplitSimulationDataType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createStartSimulationDataType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createStructuredConditionType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE.createTimeUnitCostType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createTransitionSimulationDataType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createNormalDistributionStrategyType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        SimulationFactory.eINSTANCE
                                .createWorkflowProcessSimulationDataType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        Xpdl2Factory.eINSTANCE.createApplicationType()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        Xpdl2Factory.eINSTANCE.createArtifactInput()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        Xpdl2Factory.eINSTANCE.createDataFieldsContainer()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        Xpdl2Factory.eINSTANCE.createMessage()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        Xpdl2Factory.eINSTANCE.createPackage()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        Xpdl2Factory.eINSTANCE.createPage()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        Xpdl2Factory.eINSTANCE.createPages()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        Xpdl2Factory.eINSTANCE.createProcess()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        Xpdl2Factory.eINSTANCE.createPropertyInput()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        Xpdl2Factory.eINSTANCE.createResourceCosts()));

        newChildDescriptors
                .add(createChildParameter(SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT,
                        XMLTypeFactory.eINSTANCE.createAnyType()));
    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature,
            Object child, Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify =
                childFeature == SimulationPackage.Literals.EXPRESSION_TYPE__STRUCTURED_EXPRESSION
                        || childFeature == SimulationPackage.Literals.EXPRESSION_TYPE__DEFAULT;

        if (qualify) {
            return getString("_UI_CreateChild_text2", //$NON-NLS-1$
                    new Object[] { getTypeText(childObject),
                            getFeatureText(childFeature), getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return SimulationExtensionsEditPlugin.INSTANCE;
    }

}
