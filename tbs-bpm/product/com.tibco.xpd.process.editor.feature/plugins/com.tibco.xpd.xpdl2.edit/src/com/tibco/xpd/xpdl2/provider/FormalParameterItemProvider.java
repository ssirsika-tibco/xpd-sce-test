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

import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.CommandsUtils;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.FormalParameter} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class FormalParameterItemProvider extends NamedElementItemProvider {
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
    public FormalParameterItemProvider(AdapterFactory adapterFactory) {
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
            addIsArrayPropertyDescriptor(object);
            addReadOnlyPropertyDescriptor(object);
            addModePropertyDescriptor(object);
            addRequiredPropertyDescriptor(object);
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
     * This adds a property descriptor for the Is Array feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addIsArrayPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_ProcessRelevantData_isArray_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_ProcessRelevantData_isArray_feature", //$NON-NLS-1$
                                "_UI_ProcessRelevantData_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.PROCESS_RELEVANT_DATA__IS_ARRAY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Mode feature.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     */
    protected void addModePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_FormalParameter_mode_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_FormalParameter_mode_feature", //$NON-NLS-1$
                                "_UI_FormalParameter_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.FORMAL_PARAMETER__MODE,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Required feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addRequiredPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_FormalParameter_required_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_FormalParameter_required_feature", //$NON-NLS-1$
                                "_UI_FormalParameter_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.FORMAL_PARAMETER__REQUIRED,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Read Only feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addReadOnlyPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_ProcessRelevantData_readOnly_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_ProcessRelevantData_readOnly_feature", //$NON-NLS-1$
                                "_UI_ProcessRelevantData_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.PROCESS_RELEVANT_DATA__READ_ONLY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
            childrenFeatures.add(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS);
            childrenFeatures.add(Xpdl2Package.Literals.PROCESS_RELEVANT_DATA__DATA_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.PROCESS_RELEVANT_DATA__LENGTH);
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
     * This returns FormalParameter.gif. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        String img = "FormalParamInOut"; //$NON-NLS-1$

        if (object instanceof FormalParameter) {
            FormalParameter fp = (FormalParameter) object;

            /*
             * XPD-7334: Saket: [Process Id] parameter doesn't have any
             * container, hence we need to check for it explicitly.
             */
            if (fp.eContainer() instanceof com.tibco.xpd.xpdl2.Process
                    || Xpdl2ModelUtil.REPLY_IMMEDIATE_PROCESS_ID_PARAMETER_NAME.equals(fp.getName())) {

                if (ModeType.IN_LITERAL.equals(fp.getMode())) {
                    img = "FormalParamIn"; //$NON-NLS-1$

                } else if (ModeType.OUT_LITERAL.equals(fp.getMode())) {
                    img = "FormalParamOut"; //$NON-NLS-1$

                } else if (ModeType.INOUT_LITERAL.equals(fp.getMode())) {
                    img = "FormalParamInOut"; //$NON-NLS-1$

                }
            } else if (fp.eContainer() instanceof ProcessInterface) {
                if (ModeType.IN_LITERAL.equals(fp.getMode())) {
                    img = "InterfaceParamIn"; //$NON-NLS-1$

                } else if (ModeType.OUT_LITERAL.equals(fp.getMode())) {
                    img = "InterfaceParamOut"; //$NON-NLS-1$

                } else if (ModeType.INOUT_LITERAL.equals(fp.getMode())) {
                    img = "InterfaceParamInOut"; //$NON-NLS-1$

                }
            }

            if (fp.isIsArray()) {
                img += "Array"; //$NON-NLS-1$
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

        switch (notification.getFeatureID(FormalParameter.class)) {
        case Xpdl2Package.FORMAL_PARAMETER__DESCRIPTION:
        case Xpdl2Package.FORMAL_PARAMETER__IS_ARRAY:
        case Xpdl2Package.FORMAL_PARAMETER__READ_ONLY:
        case Xpdl2Package.FORMAL_PARAMETER__MODE:
        case Xpdl2Package.FORMAL_PARAMETER__REQUIRED:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case Xpdl2Package.FORMAL_PARAMETER__OTHER_ELEMENTS:
        case Xpdl2Package.FORMAL_PARAMETER__DATA_TYPE:
        case Xpdl2Package.FORMAL_PARAMETER__LENGTH:
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
     * @generated not
     */
    @Override
    protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getDescribedElement_Description(),
                Xpdl2Factory.eINSTANCE.createDescription()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.eINSTANCE.getProcessRelevantData_Length(),
                Xpdl2Factory.eINSTANCE.createLength()));
    }

    @Override
    protected Command createSetCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value,
            int index) {
        Command cmd = super.createSetCommand(domain, owner, feature, value, index);
        cmd = CommandsUtils.checkExternalSetWrappers(cmd, domain, owner, feature, value, index);
        return cmd;
    }

    @Override
    protected Command createAddCommand(EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection, int index) {
        Command cmd = super.createAddCommand(domain, owner, feature, collection, index);
        cmd = CommandsUtils.checkExternalAddWrappers(cmd, domain, owner, feature, collection, index);
        return cmd;
    }

}
