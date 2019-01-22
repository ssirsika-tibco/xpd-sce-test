/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Position;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.xpd.om.core.om.Position} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class PositionItemProvider extends NamedElementItemProvider implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public PositionItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            // These properties will not be visible on advanced properties page.
            // addPurposePropertyDescriptor(object);
            // addStartDatePropertyDescriptor(object);
            // addEndDatePropertyDescriptor(object);
            // addDescriptionPropertyDescriptor(object);
            // addTypePropertyDescriptor(object);
            // addAllocationMethodPropertyDescriptor(object);
            // addLocationPropertyDescriptor(object);
            // addIdealNumberPropertyDescriptor(object);
            // addFeaturePropertyDescriptor(object);
            // addPositionTypePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Purpose feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addPurposePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgElement_purpose_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgElement_purpose_feature", "_UI_OrgElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_ELEMENT__PURPOSE,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Start Date feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addStartDatePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgElement_startDate_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgElement_startDate_feature", "_UI_OrgElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_ELEMENT__START_DATE,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the End Date feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addEndDatePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgElement_endDate_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgElement_endDate_feature", "_UI_OrgElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_ELEMENT__END_DATE,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Description feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDescriptionPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgElement_description_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgElement_description_feature", "_UI_OrgElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_ELEMENT__DESCRIPTION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Type feature. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgTypedElement_type_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgTypedElement_type_feature", "_UI_OrgTypedElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_TYPED_ELEMENT__TYPE,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Location feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addLocationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Locatable_location_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Locatable_location_feature", "_UI_Locatable_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.LOCATABLE__LOCATION,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Allocation Method feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addAllocationMethodPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Allocable_allocationMethod_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Allocable_allocationMethod_feature", "_UI_Allocable_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ALLOCABLE__ALLOCATION_METHOD,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Ideal Number feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addIdealNumberPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Position_idealNumber_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Position_idealNumber_feature", "_UI_Position_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.POSITION__IDEAL_NUMBER,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Feature feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addFeaturePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Position_feature_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Position_feature_feature", "_UI_Position_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.POSITION__FEATURE,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Position Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addPositionTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Position_positionType_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Position_positionType_feature", "_UI_Position_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.POSITION__POSITION_TYPE,
                        false,
                        false,
                        false,
                        null,
                        null,
                        null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to
     * deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand},
     * {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in
     * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            // childrenFeatures.add(OMPackage.Literals.ORG_ELEMENT__PARAMETERS);
            // childrenFeatures
            // .add(OMPackage.Literals.POSITION__POS_REQUIREMENT_PARAMETER);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper
        // feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns Position.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {

        String imagePath = "full/obj16/Position"; //$NON-NLS-1$

        if (object instanceof Position) {
            Position pos = (Position) object;

            if (pos.getType() != null) {
                imagePath = "full/obj16/PositionFeature"; //$NON-NLS-1$
            }
        }

        return overlayImage(object, getResourceLocator().getImage(imagePath));
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public String getText(Object object) {
        String label = ((NamedElement) object).getLabel();
        return label == null || label.length() == 0 ? getString("_UI_Position_type") : //$NON-NLS-1$
                label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to
     * update any cached children and by creating a viewer notification, which
     * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(Position.class)) {
        case OMPackage.POSITION__PURPOSE:
        case OMPackage.POSITION__START_DATE:
        case OMPackage.POSITION__END_DATE:
        case OMPackage.POSITION__DESCRIPTION:
        case OMPackage.POSITION__ALLOCATION_METHOD:
        case OMPackage.POSITION__IDEAL_NUMBER:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
            return;
        case OMPackage.POSITION__ATTRIBUTE_VALUES:
        case OMPackage.POSITION__PRIVILEGE_ASSOCIATIONS:
        case OMPackage.POSITION__SYSTEM_ACTIONS:
        case OMPackage.POSITION__CAPABILITIES_ASSOCIATIONS:
        case OMPackage.POSITION__RESOURCE_ASSOCIATION:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing the children that can be created under this object. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        // newChildDescriptors.add(createChildParameter(
        // OMPackage.Literals.ORG_ELEMENT__PARAMETERS, OMFactory.eINSTANCE
        // .createParameter()));
        //
        // newChildDescriptors.add(createChildParameter(
        // OMPackage.Literals.POSITION__POS_REQUIREMENT_PARAMETER,
        // OMFactory.eINSTANCE.createParameter()));
    }

}
