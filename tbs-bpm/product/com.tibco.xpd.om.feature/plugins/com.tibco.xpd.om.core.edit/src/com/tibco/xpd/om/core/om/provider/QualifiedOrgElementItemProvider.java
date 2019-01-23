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
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.QualifiedOrgElement;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.xpd.om.core.om.QualifiedOrgElement} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class QualifiedOrgElementItemProvider extends NamedElementItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public QualifiedOrgElementItemProvider(AdapterFactory adapterFactory) {
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

            // addPurposePropertyDescriptor(object);
            // addStartDatePropertyDescriptor(object);
            // addEndDatePropertyDescriptor(object);
            // addDescriptionPropertyDescriptor(object);
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
            // childrenFeatures
            //.add(OMPackage.Literals.QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE
            // );
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
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((QualifiedOrgElement) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_QualifiedOrgElement_type") : //$NON-NLS-1$
                getString("_UI_QualifiedOrgElement_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(QualifiedOrgElement.class)) {
        case OMPackage.QUALIFIED_ORG_ELEMENT__PURPOSE:
        case OMPackage.QUALIFIED_ORG_ELEMENT__START_DATE:
        case OMPackage.QUALIFIED_ORG_ELEMENT__END_DATE:
        case OMPackage.QUALIFIED_ORG_ELEMENT__DESCRIPTION:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
            return;
        case OMPackage.QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE:
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

        // Attribute attribute = OMFactory.eINSTANCE.createAttribute();
        // attribute.setDisplayName(OMUtil
        //                .getDefaultName(getString("_UI_DefaultName_Attribute_label"), //$NON-NLS-1$
        // getDisplayNamesArray(getChildren(object))));
        //
        // newChildDescriptors
        // .add(createChildParameter(OMPackage.Literals.
        // QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE,
        // attribute));
    }

    @Override
    protected Command createSetCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Object value) {

        if (OMPackage.Literals.QUALIFIED_ORG_ELEMENT__QUALIFIER_ATTRIBUTE
                .equals(feature)
                && (value == null || value.equals(SetCommand.UNSET_VALUE))) {
            // Attribute is being unset so clear all qualifier attribute values
            // referencing this attribute
            Object obj = owner.eGet(feature);
            if (obj instanceof Attribute) {
                Command cmd =
                        createRemoveAttributeValuesCommand(domain,
                                (Attribute) obj);

                if (cmd != null) {
                    CompoundCommand ccmd = new CompoundCommand();
                    ccmd.append(cmd);
                    ccmd.append(super.createSetCommand(domain,
                            owner,
                            feature,
                            value));
                    return ccmd;
                }
            }
        }

        return super.createSetCommand(domain, owner, feature, value);
    }

    /**
     * Create Command to remove all references to the given eObject.
     * 
     * @param domain
     * @param attr
     * @return
     */
    private Command createRemoveAttributeValuesCommand(EditingDomain domain,
            Attribute attr) {
        if (attr != null) {
            CompoundCommand cmd = new CompoundCommand();
            ECrossReferenceAdapter adapter =
                    ECrossReferenceAdapter.getCrossReferenceAdapter(attr);
            if (adapter != null) {
                Collection<Setting> references =
                        adapter.getInverseReferences(attr);

                if (references != null) {
                    for (Setting ref : references) {
                        if (ref.getEObject() instanceof AttributeValue) {
                            EObject eo = ref.getEObject();
                            if (eo.eContainingFeature() != null
                                    && eo.eContainer() != null) {
                                if (eo.eContainingFeature().isMany()) {
                                    cmd.append(RemoveCommand.create(domain, eo
                                            .eContainer(), eo
                                            .eContainingFeature(), eo));
                                } else {
                                    cmd.append(SetCommand.create(domain,
                                            eo.eContainer(),
                                            eo.eContainingFeature(),
                                            SetCommand.UNSET_VALUE));
                                }

                            }
                        }
                    }
                }
            }
            return !cmd.isEmpty() ? cmd : null;
        }
        return null;
    }
}
