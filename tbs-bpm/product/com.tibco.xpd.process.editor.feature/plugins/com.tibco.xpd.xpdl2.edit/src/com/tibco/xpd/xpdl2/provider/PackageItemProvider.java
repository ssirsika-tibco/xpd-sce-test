/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.provider;

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

import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.Package} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class PackageItemProvider extends NamedElementItemProvider {
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
    public PackageItemProvider(AdapterFactory adapterFactory) {
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

            addLanguagePropertyDescriptor(object);
            addQueryLanguagePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Language feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addLanguagePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_Package_language_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_Package_language_feature", "_UI_Package_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Xpdl2Package.Literals.PACKAGE__LANGUAGE,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Query Language feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addQueryLanguagePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Package_queryLanguage_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_Package_queryLanguage_feature", //$NON-NLS-1$
                                "_UI_Package_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.PACKAGE__QUERY_LANGUAGE,
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
            childrenFeatures.add(Xpdl2Package.Literals.APPLICATIONS_CONTAINER__APPLICATIONS);
            childrenFeatures.add(Xpdl2Package.Literals.PARTICIPANTS_CONTAINER__PARTICIPANTS);
            childrenFeatures.add(Xpdl2Package.Literals.DATA_FIELDS_CONTAINER__DATA_FIELDS);
            childrenFeatures.add(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE__PACKAGE_HEADER);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE__REDEFINABLE_HEADER);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE__CONFORMANCE_CLASS);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE__SCRIPT);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE__EXTERNAL_PACKAGES);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE__TYPE_DECLARATIONS);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE__PARTNER_LINK_TYPES);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE__POOLS);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE__MESSAGE_FLOWS);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE__ASSOCIATIONS);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE__ARTIFACTS);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE__PROCESSES);
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
     * This returns Package.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        if (DecisionFlowUtil.isDecisionFlowPackage((Package) object)) {
            return overlayImage(object, getResourceLocator().getImage("full/obj16/DecisionFlowPackage")); //$NON-NLS-1$
        } else {
            return overlayImage(object, getResourceLocator().getImage("full/obj16/Package")); //$NON-NLS-1$
        }
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

        switch (notification.getFeatureID(com.tibco.xpd.xpdl2.Package.class)) {
        case Xpdl2Package.PACKAGE__LANGUAGE:
        case Xpdl2Package.PACKAGE__QUERY_LANGUAGE:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case Xpdl2Package.PACKAGE__EXTENDED_ATTRIBUTES:
        case Xpdl2Package.PACKAGE__APPLICATIONS:
        case Xpdl2Package.PACKAGE__PARTICIPANTS:
        case Xpdl2Package.PACKAGE__DATA_FIELDS:
        case Xpdl2Package.PACKAGE__OTHER_ELEMENTS:
        case Xpdl2Package.PACKAGE__PACKAGE_HEADER:
        case Xpdl2Package.PACKAGE__REDEFINABLE_HEADER:
        case Xpdl2Package.PACKAGE__CONFORMANCE_CLASS:
        case Xpdl2Package.PACKAGE__SCRIPT:
        case Xpdl2Package.PACKAGE__EXTERNAL_PACKAGES:
        case Xpdl2Package.PACKAGE__TYPE_DECLARATIONS:
        case Xpdl2Package.PACKAGE__PARTNER_LINK_TYPES:
        case Xpdl2Package.PACKAGE__POOLS:
        case Xpdl2Package.PACKAGE__MESSAGE_FLOWS:
        case Xpdl2Package.PACKAGE__ASSOCIATIONS:
        case Xpdl2Package.PACKAGE__ARTIFACTS:
        case Xpdl2Package.PACKAGE__PROCESSES:
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

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES,
                        Xpdl2Factory.eINSTANCE.createExtendedAttribute()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.APPLICATIONS_CONTAINER__APPLICATIONS,
                Xpdl2Factory.eINSTANCE.createApplication()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PARTICIPANTS_CONTAINER__PARTICIPANTS,
                Xpdl2Factory.eINSTANCE.createParticipant()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.DATA_FIELDS_CONTAINER__DATA_FIELDS,
                Xpdl2Factory.eINSTANCE.createDataField()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE__PACKAGE_HEADER,
                Xpdl2Factory.eINSTANCE.createPackageHeader()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE__REDEFINABLE_HEADER,
                Xpdl2Factory.eINSTANCE.createRedefinableHeader()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE__CONFORMANCE_CLASS,
                Xpdl2Factory.eINSTANCE.createConformanceClass()));

        newChildDescriptors.add(
                createChildParameter(Xpdl2Package.Literals.PACKAGE__SCRIPT, Xpdl2Factory.eINSTANCE.createScript()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE__EXTERNAL_PACKAGES,
                Xpdl2Factory.eINSTANCE.createExternalPackage()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE__TYPE_DECLARATIONS,
                Xpdl2Factory.eINSTANCE.createTypeDeclaration()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE__PARTNER_LINK_TYPES,
                Xpdl2Factory.eINSTANCE.createPartnerLinkType()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.PACKAGE__POOLS, Xpdl2Factory.eINSTANCE.createPool()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE__MESSAGE_FLOWS,
                Xpdl2Factory.eINSTANCE.createMessageFlow()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE__ASSOCIATIONS,
                Xpdl2Factory.eINSTANCE.createAssociation()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE__ARTIFACTS,
                Xpdl2Factory.eINSTANCE.createArtifact()));

        newChildDescriptors.add(
                createChildParameter(Xpdl2Package.Literals.PACKAGE__PROCESSES, Xpdl2Factory.eINSTANCE.createProcess()));
    }

}
