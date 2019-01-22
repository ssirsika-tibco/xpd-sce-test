/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
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

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.om.core.om.Attribute} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AttributeItemProvider extends NamedElementItemProvider implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getParent(java.lang
     * .Object)
     */
    // @Override
    // public Object getParent(Object object) {
    // Object parent = super.getParent(object);
    // if (parent instanceof OrgModel) {
    // OrgModelItemProvider orgModelItemProvider = (OrgModelItemProvider)
    // adapterFactory
    // .adapt(parent, IEditingDomainItemProvider.class);
    // return orgModelItemProvider != null ? orgModelItemProvider
    // .getTransientParent(OMPackage.Literals.ORG_MODEL__RESOURCE_ATTRIBUTES)
    // : null;
    // }
    // return parent;
    // }
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public AttributeItemProvider(AdapterFactory adapterFactory) {
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
            // addTypePropertyDescriptor(object);
            // addDefaultValuePropertyDescriptor(object);
            // addDefaultEnumSetValuesPropertyDescriptor(object);
            // addDescriptionPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Type feature.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     */
    protected void addTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Attribute_type_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Attribute_type_feature", "_UI_Attribute_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ATTRIBUTE__TYPE,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Default Value feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDefaultValuePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Attribute_defaultValue_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Attribute_defaultValue_feature", "_UI_Attribute_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ATTRIBUTE__DEFAULT_VALUE,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Default Enum Set Values feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addDefaultEnumSetValuesPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Attribute_defaultEnumSetValues_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Attribute_defaultEnumSetValues_feature", "_UI_Attribute_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ATTRIBUTE__DEFAULT_ENUM_SET_VALUES,
                        true,
                        false,
                        true,
                        null,
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
                        getString("_UI_Attribute_description_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Attribute_description_feature", "_UI_Attribute_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ATTRIBUTE__DESCRIPTION,
                        true,
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
            // childrenFeatures.add(OMPackage.Literals.ATTRIBUTE__VALUES);
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
     * This returns Attribute.gif.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator()
                .getImage("full/obj16/Attribute")); //$NON-NLS-1$
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
        return label == null || label.length() == 0 ? getString("_UI_Attribute_type") : //$NON-NLS-1$
                label;
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

        switch (notification.getFeatureID(Attribute.class)) {
        case OMPackage.ATTRIBUTE__TYPE:
        case OMPackage.ATTRIBUTE__DEFAULT_VALUE:
        case OMPackage.ATTRIBUTE__DESCRIPTION:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
            return;
        case OMPackage.ATTRIBUTE__VALUES:
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
        // OMPackage.Literals.ATTRIBUTE__VALUES, OMFactory.eINSTANCE
        // .createEnumValue()));
    }

    @Override
    protected Command createSetCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Object value) {

        if (owner instanceof Attribute
                && feature == OMPackage.eINSTANCE.getAttribute_Type()
                && !owner.eGet(feature).equals(value)) {
            // When attribute type is updated then clear default values...
            CompoundCommand ccmd = new CompoundCommand();
            Attribute attr = (Attribute) owner;
            Command cmd = createClearDefaultValuesCommand(domain, attr, value);
            if (cmd != null) {
                ccmd.append(cmd);
            }
            // ...and clear all referencing attribute values
            cmd = createClearReferenceAttributeValues(domain, attr, value);
            if (cmd != null) {
                ccmd.append(cmd);
            }
            ccmd.append(super.createSetCommand(domain, owner, feature, value));

            return ccmd;
        }

        return super.createSetCommand(domain, owner, feature, value);
    }

    /**
     * Create a {@link Command} to clear the default values and if the new type
     * is not an Enum or EnumSet then clear any enumerations.
     * 
     * @param domain
     * @param ownerAttr
     * @param value
     * @return
     */
    private Command createClearDefaultValuesCommand(EditingDomain domain,
            Attribute ownerAttr, Object value) {
        CompoundCommand cmd = new CompoundCommand();
        if (!ownerAttr.getDefaultEnumSetValues().isEmpty()) {
            cmd.append(RemoveCommand.create(domain,
                    ownerAttr,
                    OMPackage.eINSTANCE.getAttribute_DefaultEnumSetValues(),
                    ownerAttr.getDefaultEnumSetValues()));
        }

        if (ownerAttr.getDefaultValue() != null
                && ownerAttr.getDefaultValue().length() > 0) {
            cmd.append(SetCommand.create(domain, ownerAttr, OMPackage.eINSTANCE
                    .getAttribute_DefaultValue(), SetCommand.UNSET_VALUE));
        }

        // If new type is not enum/enumSet and there are enumerations then
        // clear them
        if (value instanceof AttributeType) {
            AttributeType type = (AttributeType) value;
            if (!(type == AttributeType.ENUM || type == AttributeType.ENUM_SET)
                    && !ownerAttr.getValues().isEmpty()) {
                cmd.append(RemoveCommand.create(domain,
                        ownerAttr,
                        OMPackage.eINSTANCE.getAttribute_Values(),
                        ownerAttr.getValues()));
            }
        }
        return !cmd.isEmpty() ? cmd : null;
    }

    /**
     * Create {@link Command} to clear all values set for this attribute.
     * 
     * @param domain
     * @param ownerAttr
     * @param value
     * @return
     */
    private Command createClearReferenceAttributeValues(EditingDomain domain,
            Attribute ownerAttr, Object value) {
        CompoundCommand cmd = new CompoundCommand();
        // Attribute type changes so clear all attribute values set in the
        // model
        ECrossReferenceAdapter adapter =
                ECrossReferenceAdapter.getCrossReferenceAdapter(ownerAttr);
        if (adapter != null) {
            Collection<Setting> references =
                    adapter.getInverseReferences(ownerAttr);

            if (references != null) {
                for (Setting ref : references) {
                    EStructuralFeature feat = ref.getEStructuralFeature();
                    if (feat == OMPackage.eINSTANCE
                            .getAttributeValue_Attribute()) {
                        EObject object = ref.getEObject();
                        if (object instanceof AttributeValue) {
                            AttributeValue attrValue = (AttributeValue) object;
                            if (attrValue.getValue() != null
                                    || !attrValue.getEnumSetValues().isEmpty()) {

                                EObject container = attrValue.eContainer();
                                EStructuralFeature feature =
                                        attrValue.eContainingFeature();

                                if (container != null && feature != null) {
                                    if (feature.isMany()) {
                                        cmd.append(RemoveCommand.create(domain,
                                                container,
                                                feature,
                                                attrValue));
                                    } else {
                                        cmd.append(SetCommand.create(domain,
                                                container,
                                                feature,
                                                SetCommand.UNSET_VALUE));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return !cmd.isEmpty() ? cmd : null;
    }
}
