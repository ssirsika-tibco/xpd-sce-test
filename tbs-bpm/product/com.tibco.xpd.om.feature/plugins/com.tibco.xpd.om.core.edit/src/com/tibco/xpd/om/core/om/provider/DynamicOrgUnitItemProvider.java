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
import org.eclipse.emf.edit.command.AddCommand;
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

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Organization;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.xpd.om.core.om.DynamicOrgUnit} object. <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class DynamicOrgUnitItemProvider extends OrgUnitItemProvider implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public DynamicOrgUnitItemProvider(AdapterFactory adapterFactory) {
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

            /*
             * Only show label and name - to be consistent with the rest of the
             * Org Model.
             */
            // addDynamicOrganizationPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Dynamic Organization feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDynamicOrganizationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_DynamicOrgUnit_dynamicOrganization_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_DynamicOrgUnit_dynamicOrganization_feature", "_UI_DynamicOrgUnit_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.DYNAMIC_ORG_UNIT__DYNAMIC_ORGANIZATION,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * @see com.tibco.xpd.om.core.om.provider.NamedElementItemProvider#addDisplayNamePropertyDescriptor(java.lang.Object)
     * 
     * @param object
     */
    @Override
    protected void addDisplayNamePropertyDescriptor(Object object) {
        // Display name is derived from the referenced dynamic org so make it
        // read-only
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_NamedElement_labelKey_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_NamedElement_labelKey_feature", "_UI_NamedElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.NAMED_ELEMENT__DISPLAY_NAME,
                        false,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * @see com.tibco.xpd.om.core.om.provider.NamedElementItemProvider#addNamePropertyDescriptor(java.lang.Object)
     * 
     * @param object
     */
    @Override
    protected void addNamePropertyDescriptor(Object object) {
        // Name is derived from the referenced dynamic org so make it read-only
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_NamedElement_name_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_NamedElement_name_feature", "_UI_NamedElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.NAMED_ELEMENT__NAME,
                        false,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This returns DynamicOrgUnit.gif. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/DynamicOrgUnit.png")); //$NON-NLS-1$
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected boolean shouldComposeCreationImage() {
        return true;
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
        return label == null || label.length() == 0 ? getString("_UI_DynamicOrgUnit_type") : //$NON-NLS-1$
                label;

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
        /*
         * XPD-5300: Dynamic OrgUnit has no children.
         */
        // super.collectNewChildDescriptors(newChildDescriptors, object);
    }

    /**
     * @see com.tibco.xpd.om.core.om.provider.OrgUnitItemProvider#createSetCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject,
     *      org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
     * 
     * @param domain
     * @param owner
     * @param feature
     * @param value
     * @return
     */
    @Override
    protected Command createSetCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Object value) {

        if (feature == OMPackage.eINSTANCE
                .getDynamicOrgUnit_DynamicOrganization()) {
            DynamicOrgUnit ou = (DynamicOrgUnit) owner;
            DynamicOrgReference existingRef = ou.getDynamicOrganization();

            if (value instanceof Organization) {
                Organization org = (Organization) value;

                /*
                 * Add the dynamic org reference and then remove the current org
                 * reference if there is one.
                 */

                CompoundCommand cmd =
                        new CompoundCommand(
                                getString("_UI_DynamicOrgUnit_setReferenceCommand")); //$NON-NLS-1$

                DynamicOrgReference newRef =
                        OMFactory.eINSTANCE.createDynamicOrgReference();
                newRef.setFrom(ou);
                newRef.setTo(org);

                cmd.append(AddCommand.create(domain,
                        org.eContainer(),
                        OMPackage.eINSTANCE.getOrgModel_DynamicOrgReferences(),
                        newRef));

                cmd.append(super.createSetCommand(domain,
                        owner,
                        feature,
                        newRef));

                if (existingRef != null) {
                    cmd.append(RemoveCommand.create(domain,
                            org.eContainer(),
                            OMPackage.eINSTANCE
                                    .getOrgModel_DynamicOrgReferences(),
                            existingRef));
                }

                return cmd;

            } else if (value == SetCommand.UNSET_VALUE) {
                /*
                 * Need to remove the DynamicOrgReference and the reference to
                 * it from the Dynamic OrgUnit.
                 */
                if (existingRef != null) {
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    getString("_UI_DynamicOrgUnit_clearReferenceCommand")); //$NON-NLS-1$
                    cmd.append(super.createSetCommand(domain,
                            owner,
                            feature,
                            value));

                    cmd.append(RemoveCommand.create(domain,
                            ou.getOrganization().eContainer(),
                            OMPackage.eINSTANCE
                                    .getOrgModel_DynamicOrgReferences(),
                            existingRef));

                    return cmd;
                }
            }
        }

        return super.createSetCommand(domain, owner, feature, value);
    }
}
