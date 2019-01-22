/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.provider;


import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaFactory;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class EAIJavaItemProvider
    extends ItemProviderAdapter
    implements	
        IEditingDomainItemProvider,	
        IStructuredItemContentProvider,	
        ITreeItemContentProvider,	
        IItemLabelProvider,	
        IItemPropertySource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAIJavaItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public List getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addProjectPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Project feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addProjectPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_EAIJava_project_feature"), //$NON-NLS-1$
                 getString("_UI_PropertyDescriptor_description", "_UI_EAIJava_project_feature", "_UI_EAIJava_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                 EaijavaPackage.Literals.EAI_JAVA__PROJECT,
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
    public Collection getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(EaijavaPackage.Literals.EAI_JAVA__POJO);
            childrenFeatures.add(EaijavaPackage.Literals.EAI_JAVA__FACTORY);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns EAIJava.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/EAIJava")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getText(Object object) {
        String label = ((EAIJava)object).getProject();
        return label == null || label.length() == 0 ?
            getString("_UI_EAIJava_type") : //$NON-NLS-1$
            getString("_UI_EAIJava_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(EAIJava.class)) {
            case EaijavaPackage.EAI_JAVA__PROJECT:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case EaijavaPackage.EAI_JAVA__POJO:
            case EaijavaPackage.EAI_JAVA__FACTORY:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing all of the children that can be created under this object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add
            (createChildParameter
                (EaijavaPackage.Literals.EAI_JAVA__POJO,
                 EaijavaFactory.eINSTANCE.createClassType()));

        newChildDescriptors.add
            (createChildParameter
                (EaijavaPackage.Literals.EAI_JAVA__FACTORY,
                 EaijavaFactory.eINSTANCE.createClassType()));
    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCreateChildText(Object owner, Object feature, Object child, Collection selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify =
            childFeature == EaijavaPackage.Literals.EAI_JAVA__POJO ||
            childFeature == EaijavaPackage.Literals.EAI_JAVA__FACTORY;

        if (qualify) {
            return getString
                ("_UI_CreateChild_text2", //$NON-NLS-1$
                 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceLocator getResourceLocator() {
        return EaijavaEditPlugin.INSTANCE;
    }

}
