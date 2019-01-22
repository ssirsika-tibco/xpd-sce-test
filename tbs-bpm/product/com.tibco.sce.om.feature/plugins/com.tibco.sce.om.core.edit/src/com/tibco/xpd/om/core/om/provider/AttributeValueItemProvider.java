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
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
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

import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.OMPackage;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.om.core.om.AttributeValue} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AttributeValueItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeValueItemProvider(AdapterFactory adapterFactory) {
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

            addAttributePropertyDescriptor(object);
            addValuePropertyDescriptor(object);
            addEnumSetValuesPropertyDescriptor(object);
            addSetValuesPropertyDescriptor(object);
            addTypePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addAttributePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_AttributeValue_attribute_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_AttributeValue_attribute_feature", "_UI_AttributeValue_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ATTRIBUTE_VALUE__ATTRIBUTE,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Value feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addValuePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_AttributeValue_value_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_AttributeValue_value_feature", "_UI_AttributeValue_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ATTRIBUTE_VALUE__VALUE,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Enum Set Values feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addEnumSetValuesPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_AttributeValue_enumSetValues_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_AttributeValue_enumSetValues_feature", "_UI_AttributeValue_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ATTRIBUTE_VALUE__ENUM_SET_VALUES,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Set Values feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addSetValuesPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_AttributeValue_setValues_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_AttributeValue_setValues_feature", "_UI_AttributeValue_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ATTRIBUTE_VALUE__SET_VALUES,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Type feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_AttributeValue_type_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_AttributeValue_type_feature", "_UI_AttributeValue_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ATTRIBUTE_VALUE__TYPE,
                        false,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This returns AttributeValue.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/AttributeValue")); //$NON-NLS-1$
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected boolean shouldComposeCreationImage() {
        return true;
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((AttributeValue) object).getValue();
        return label == null || label.length() == 0 ? getString("_UI_AttributeValue_type") : //$NON-NLS-1$
                getString("_UI_AttributeValue_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(AttributeValue.class)) {
        case OMPackage.ATTRIBUTE_VALUE__VALUE:
        case OMPackage.ATTRIBUTE_VALUE__SET_VALUES:
        case OMPackage.ATTRIBUTE_VALUE__TYPE:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
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
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return OrganisationModelEditPlugin.INSTANCE;
    }

}
