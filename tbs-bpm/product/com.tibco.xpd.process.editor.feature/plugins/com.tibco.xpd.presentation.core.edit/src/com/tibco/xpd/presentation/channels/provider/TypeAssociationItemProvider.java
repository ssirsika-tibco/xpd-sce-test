/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
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

import com.tibco.xpd.presentation.channels.ChannelsFactory;
import com.tibco.xpd.presentation.channels.ChannelsPackage;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.ChannelType;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.xpd.presentation.channels.TypeAssociation} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class TypeAssociationItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public TypeAssociationItemProvider(AdapterFactory adapterFactory) {
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

            addChannelTypePropertyDescriptor(object);
            addChannelPropertyDescriptor(object);
            addDerivedIdPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Channel Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addChannelTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_TypeAssociation_channelType_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_TypeAssociation_channelType_feature", "_UI_TypeAssociation_type"),
                 ChannelsPackage.Literals.TYPE_ASSOCIATION__CHANNEL_TYPE,
                 true,
                 false,
                 true,
                 null,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Channel feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addChannelPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_TypeAssociation_channel_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_TypeAssociation_channel_feature", "_UI_TypeAssociation_type"),
                 ChannelsPackage.Literals.TYPE_ASSOCIATION__CHANNEL,
                 false,
                 false,
                 false,
                 null,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Derived Id feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addDerivedIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_TypeAssociation_derivedId_feature"),
                 getString("_UI_PropertyDescriptor_description", "_UI_TypeAssociation_derivedId_feature", "_UI_TypeAssociation_type"),
                 ChannelsPackage.Literals.TYPE_ASSOCIATION__DERIVED_ID,
                 false,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
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
            // childrenFeatures.add(ChannelsPackage.Literals.
            // TYPE_ASSOCIATION__ATTRIBUTE_VALUES);
            // childrenFeatures.add(ChannelsPackage.Literals.
            // TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES);
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
     * This returns TypeAssociation.gif.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/TypeAssociation"));
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public String getText(Object object) {
        TypeAssociation typeAssoc = (TypeAssociation) object;
        ChannelType channelType = typeAssoc.getChannelType();
        if (channelType != null) {
            if (channelType.eIsProxy()) {
                return String.format("Unresolved channel type: '%s'",
                        ((InternalEObject) channelType).eProxyURI().fragment());
            }
            return channelType.getName();
        }
        return getString("_UI_TypeAssociation_type");
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

        switch (notification.getFeatureID(TypeAssociation.class)) {
            case ChannelsPackage.TYPE_ASSOCIATION__DERIVED_ID:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case ChannelsPackage.TYPE_ASSOCIATION__ATTRIBUTE_VALUES:
            case ChannelsPackage.TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES:
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
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add
            (createChildParameter
                (ChannelsPackage.Literals.TYPE_ASSOCIATION__ATTRIBUTE_VALUES,
                 ChannelsFactory.eINSTANCE.createAttributeValue()));

        newChildDescriptors.add
            (createChildParameter
                (ChannelsPackage.Literals.TYPE_ASSOCIATION__EXTENDED_ATTRIBUTES,
                 ChannelsFactory.eINSTANCE.createExtendedAttribute()));
    }

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return ChannelsEditPlugin.INSTANCE;
    }

}
