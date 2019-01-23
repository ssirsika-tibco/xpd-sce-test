/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig.provider;

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
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.projectconfig.specialfolders.extpoint.SpecialFoldersExtensionPoint;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.resources.projectconfig.SpecialFolder} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SpecialFolderItemProvider extends UniqueIdContainerItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SpecialFolderItemProvider(AdapterFactory adapterFactory) {
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

            addKindPropertyDescriptor(object);
            addLocationPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Kind feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addKindPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_SpecialFolder_kind_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_SpecialFolder_kind_feature",
                        "_UI_SpecialFolder_type"),
                ProjectConfigPackage.Literals.SPECIAL_FOLDER__KIND, true,
                false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null,
                null));
    }

    /**
     * This adds a property descriptor for the Location feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addLocationPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_SpecialFolder_location_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_SpecialFolder_location_feature",
                        "_UI_SpecialFolder_type"),
                ProjectConfigPackage.Literals.SPECIAL_FOLDER__LOCATION, true,
                false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null,
                null));
    }

    /**
     * This returns SpecialFolder.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public Object getImage(Object object) {
        /*
         * Get the image assigned to the special folder kind, if on is not
         * assigned then use the default image.
         */
        if (object instanceof SpecialFolder) {
            SpecialFolder sf = (SpecialFolder) object;

            ISpecialFolderModel info = SpecialFoldersExtensionPoint
                    .getInstance().getExtensionByKind(sf.getKind());

            if (info != null) {
                ImageDescriptor icon = info.getIcon();

                if (icon != null) {
                    return icon;
                }
            }
        }

        return overlayImage(object, getResourceLocator().getImage(
                "full/obj16/SpecialFolder")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getText(Object object) {
        String label = ((SpecialFolder) object).getLocation();
        return label == null || label.length() == 0 ? getString("_UI_SpecialFolder_type") : //$NON-NLS-1$
                label;
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

        switch (notification.getFeatureID(SpecialFolder.class)) {
        case ProjectConfigPackage.SPECIAL_FOLDER__KIND:
        case ProjectConfigPackage.SPECIAL_FOLDER__LOCATION:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
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

}
