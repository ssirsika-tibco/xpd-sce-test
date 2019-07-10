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
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.xpdExtension.FieldFormat;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.TypeDeclaration} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TypeDeclarationItemProvider extends NamedElementItemProvider {
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
    public TypeDeclarationItemProvider(AdapterFactory adapterFactory) {
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
            childrenFeatures.add(Xpdl2Package.Literals.TYPE_DECLARATION__BASIC_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.TYPE_DECLARATION__DECLARED_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.TYPE_DECLARATION__SCHEMA_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.TYPE_DECLARATION__EXTERNAL_REFERENCE);
            childrenFeatures.add(Xpdl2Package.Literals.TYPE_DECLARATION__RECORD_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.TYPE_DECLARATION__UNION_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.TYPE_DECLARATION__ENUMERATION_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.TYPE_DECLARATION__ARRAY_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.TYPE_DECLARATION__LIST_TYPE);
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
     * This returns TypeDeclaration.gif. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {

        String img = "TypeDeclaration"; //$NON-NLS-1$

        if (object instanceof TypeDeclaration) {
            TypeDeclaration td = (TypeDeclaration) object;

            if (td.getDeclaredType() != null) {
                img = "TypeDeclDeclaredType"; //$NON-NLS-1$

            } else if (td.getExternalReference() != null) {
                img = "TypeDeclExtRef"; //$NON-NLS-1$

            } else if (td.getBasicType() != null) {
                BasicType bt = td.getBasicType();

                switch (bt.getType().getValue()) {
                case BasicTypeType.BOOLEAN:
                    img = "TypeDeclBoolean"; //$NON-NLS-1$
                    break;
                case BasicTypeType.DATETIME:
                    img = "TypeDeclDateTime"; //$NON-NLS-1$
                    break;
                case BasicTypeType.FLOAT:
                    img = "TypeDeclFloat"; //$NON-NLS-1$
                    break;
                case BasicTypeType.INTEGER:
                    img = "TypeDeclInt"; //$NON-NLS-1$
                    break;
                case BasicTypeType.REFERENCE:
                    img = "TypeDeclReference"; //$NON-NLS-1$
                    break;
                case BasicTypeType.PERFORMER:
                    img = "TypeDeclPerformer"; //$NON-NLS-1$

                    break;
                case BasicTypeType.STRING:
                    /*
                     * Sid ACE-1192 use different image for URI
                     */
                    Object fieldFormat = Xpdl2ModelUtil.getOtherAttribute(bt,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_FieldFormat());

                    if (fieldFormat != null && FieldFormat.URI.equals(fieldFormat)) {
                        img = "TypeDeclURI"; //$NON-NLS-1$
                    } else {
                        img = "TypeDeclString"; //$NON-NLS-1$
                    }

                    break;
                }
            }
        }
        return overlayImage(object, getResourceLocator().getImage("full/obj16/" + img)); //$NON-NLS-1$
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

        switch (notification.getFeatureID(TypeDeclaration.class)) {
        case Xpdl2Package.TYPE_DECLARATION__DESCRIPTION:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case Xpdl2Package.TYPE_DECLARATION__EXTENDED_ATTRIBUTES:
        case Xpdl2Package.TYPE_DECLARATION__BASIC_TYPE:
        case Xpdl2Package.TYPE_DECLARATION__DECLARED_TYPE:
        case Xpdl2Package.TYPE_DECLARATION__SCHEMA_TYPE:
        case Xpdl2Package.TYPE_DECLARATION__EXTERNAL_REFERENCE:
        case Xpdl2Package.TYPE_DECLARATION__RECORD_TYPE:
        case Xpdl2Package.TYPE_DECLARATION__UNION_TYPE:
        case Xpdl2Package.TYPE_DECLARATION__ENUMERATION_TYPE:
        case Xpdl2Package.TYPE_DECLARATION__ARRAY_TYPE:
        case Xpdl2Package.TYPE_DECLARATION__LIST_TYPE:
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

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TYPE_DECLARATION__BASIC_TYPE,
                Xpdl2Factory.eINSTANCE.createBasicType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TYPE_DECLARATION__DECLARED_TYPE,
                Xpdl2Factory.eINSTANCE.createDeclaredType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TYPE_DECLARATION__SCHEMA_TYPE,
                Xpdl2Factory.eINSTANCE.createSchema()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TYPE_DECLARATION__EXTERNAL_REFERENCE,
                Xpdl2Factory.eINSTANCE.createExternalReference()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TYPE_DECLARATION__RECORD_TYPE,
                Xpdl2Factory.eINSTANCE.createRecordType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TYPE_DECLARATION__UNION_TYPE,
                Xpdl2Factory.eINSTANCE.createUnionType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TYPE_DECLARATION__ENUMERATION_TYPE,
                Xpdl2Factory.eINSTANCE.createEnumerationType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TYPE_DECLARATION__ARRAY_TYPE,
                Xpdl2Factory.eINSTANCE.createArrayType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TYPE_DECLARATION__LIST_TYPE,
                Xpdl2Factory.eINSTANCE.createListType()));
    }

}
