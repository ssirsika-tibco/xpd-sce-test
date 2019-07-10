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

import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.Artifact} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class ArtifactItemProvider extends NamedElementItemProvider {
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
    public ArtifactItemProvider(AdapterFactory adapterFactory) {
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

            addArtifactTypePropertyDescriptor(object);
            addTextAnnotationPropertyDescriptor(object);
            addGroupPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Artifact Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addArtifactTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Artifact_artifactType_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_Artifact_artifactType_feature", //$NON-NLS-1$
                                "_UI_Artifact_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.ARTIFACT__ARTIFACT_TYPE,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Group feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addGroupPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_Artifact_group_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", "_UI_Artifact_group_feature", "_UI_Artifact_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Xpdl2Package.Literals.ARTIFACT__GROUP,
                true,
                false,
                false,
                null,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Text Annotation feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addTextAnnotationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Artifact_textAnnotation_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_Artifact_textAnnotation_feature", //$NON-NLS-1$
                                "_UI_Artifact_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.ARTIFACT__TEXT_ANNOTATION,
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
            childrenFeatures.add(Xpdl2Package.Literals.GRAPHICAL_NODE__NODE_GRAPHICS_INFOS);
            childrenFeatures.add(Xpdl2Package.Literals.ARTIFACT__OBJECT);
            childrenFeatures.add(Xpdl2Package.Literals.ARTIFACT__DATA_OBJECT);
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
     * This returns Artifact.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        String img = "full/obj16/Artifact"; //$NON-NLS-1$

        if (object instanceof Artifact) {
            Artifact artifact = (Artifact) object;

            if (ArtifactType.ANNOTATION_LITERAL.equals(artifact.getArtifactType())) {
                img = "full/obj16/Annotation"; //$NON-NLS-1$

            } else if (ArtifactType.GROUP_LITERAL.equals(artifact.getArtifactType())) {
                img = "full/obj16/Group"; //$NON-NLS-1$

            } else if (ArtifactType.DATA_OBJECT_LITERAL.equals(artifact.getArtifactType())) {
                img = "full/obj16/DataObject"; //$NON-NLS-1$
            }
        }

        return overlayImage(object, getResourceLocator().getImage(img));
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public String getText(Object object) {
        String text = null;

        if (object instanceof Artifact) {
            Artifact artifact = (Artifact) object;

            if (ArtifactType.ANNOTATION_LITERAL.equals(artifact.getArtifactType())) {
                text = artifact.getTextAnnotation();

            } else if (ArtifactType.GROUP_LITERAL.equals(artifact.getArtifactType())) {
                text = super.getText(object);

            } else if (ArtifactType.DATA_OBJECT_LITERAL.equals(artifact.getArtifactType())) {
                text = super.getText(object);
            }
        }

        return text != null ? text : ""; //$NON-NLS-1$
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

        switch (notification.getFeatureID(Artifact.class)) {
        case Xpdl2Package.ARTIFACT__ARTIFACT_TYPE:
        case Xpdl2Package.ARTIFACT__TEXT_ANNOTATION:
        case Xpdl2Package.ARTIFACT__GROUP:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case Xpdl2Package.ARTIFACT__NODE_GRAPHICS_INFOS:
        case Xpdl2Package.ARTIFACT__OBJECT:
        case Xpdl2Package.ARTIFACT__DATA_OBJECT:
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

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.GRAPHICAL_NODE__NODE_GRAPHICS_INFOS,
                Xpdl2Factory.eINSTANCE.createNodeGraphicsInfo()));

        newChildDescriptors.add(
                createChildParameter(Xpdl2Package.Literals.ARTIFACT__OBJECT, Xpdl2Factory.eINSTANCE.createObject()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.ARTIFACT__DATA_OBJECT,
                Xpdl2Factory.eINSTANCE.createDataObject()));
    }

}
