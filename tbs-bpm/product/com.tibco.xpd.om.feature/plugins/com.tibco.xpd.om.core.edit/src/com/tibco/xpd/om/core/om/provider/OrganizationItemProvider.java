/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
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

import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.MultipleFeature;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.util.OMUtil;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.xpd.om.core.om.Organization} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class OrganizationItemProvider extends NamedElementItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getParent(java.lang
     * .Object)
     */
    @Override
    public Object getParent(Object object) {
        Object parent = super.getParent(object);
        if (parent instanceof OrgModel) {
            OrgModelItemProvider orgModelItemProvider =
                    (OrgModelItemProvider) adapterFactory.adapt(parent,
                            IEditingDomainItemProvider.class);
            return orgModelItemProvider != null ? orgModelItemProvider
                    .getTransientParent(parent,
                            OMPackage.Literals.ORG_MODEL__ORGANIZATIONS) : null;
        }
        return parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildren(java.lang
     * .Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<?> getChildren(Object object) {
        Organization org = (Organization) object;
        EList<OrgUnit> subUnits = org.getSubUnits();
        List<Object> children =
                new ArrayList<Object>(super.getChildren(object));
        for (Iterator<Object> iterator = children.iterator(); iterator
                .hasNext();) {
            Object child = iterator.next();
            if (child instanceof OrgUnit && !subUnits.contains(child)) {
                iterator.remove();
            }

        }
        return children;

    }

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public OrganizationItemProvider(AdapterFactory adapterFactory) {
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
            // addPurposePropertyDescriptor(object);
            // addStartDatePropertyDescriptor(object);
            // addEndDatePropertyDescriptor(object);
            // addDescriptionPropertyDescriptor(object);
            // addTypePropertyDescriptor(object);
            // addAllocationMethodPropertyDescriptor(object);
            // addLocationPropertyDescriptor(object);
            // addOrganizationTypePropertyDescriptor(object);
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
     * This adds a property descriptor for the Location feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addLocationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Locatable_location_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Locatable_location_feature", "_UI_Locatable_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.LOCATABLE__LOCATION,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Organization Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addOrganizationTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Organization_organizationType_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Organization_organizationType_feature", "_UI_Organization_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORGANIZATION__ORGANIZATION_TYPE,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Dynamic feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDynamicPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Organization_dynamic_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Organization_dynamic_feature", "_UI_Organization_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORGANIZATION__DYNAMIC,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Allocation Method feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addAllocationMethodPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Allocable_allocationMethod_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_Allocable_allocationMethod_feature", "_UI_Allocable_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ALLOCABLE__ALLOCATION_METHOD,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Type feature. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgTypedElement_type_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgTypedElement_type_feature", "_UI_OrgTypedElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_TYPED_ELEMENT__TYPE,
                        true,
                        false,
                        true,
                        null,
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
            childrenFeatures.add(OMPackage.Literals.ORGANIZATION__UNITS);
            // XPD-5300 Dynamic Org Identifiers
            // childrenFeatures
            // .add(OMPackage.Literals.ORGANIZATION__DYNAMIC_ORG_IDENTIFIERS);
            // childrenFeatures
            // .add(OMPackage.Literals.ORG_TYPED_ELEMENT__ATTRIBUTE_VALUES);
            // childrenFeatures
            // .add(OMPackage.Literals.ORGANIZATION__ORG_UNIT_RELATIONSHIPS);
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
     * This returns ORGANISATION.gif. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {

        String imagePath = "full/obj16/Organization"; //$NON-NLS-1$

        if (object instanceof Organization) {
            Organization org = (Organization) object;

            if (!org.isDynamic()) {
                if (org.getType() != null) {
                    imagePath = "full/obj16/OrganizationType"; //$NON-NLS-1$
                }
            } else {
                // Dynamic Organization
                if (org.getType() != null) {
                    imagePath = "full/obj16/DynamicOrganizationType"; //$NON-NLS-1$
                } else {
                    imagePath = "full/obj16/DynamicOrganization.png"; //$NON-NLS-1$
                }
            }
        }

        return overlayImage(object, getResourceLocator().getImage(imagePath));
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

        if (label == null || label.length() == 0) {
            label = getString("_UI_Organisation_type"); //$NON-NLS-1$

            // If dynamic org then apply appropriate label
            if (object instanceof Organization
                    && ((Organization) object).isDynamic()) {
                label = getString("_UI_DynamicOrganisation_type"); //$NON-NLS-1$
            }
        }

        return label;
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

        switch (notification.getFeatureID(Organization.class)) {
        case OMPackage.ORGANIZATION__PURPOSE:
        case OMPackage.ORGANIZATION__START_DATE:
        case OMPackage.ORGANIZATION__END_DATE:
        case OMPackage.ORGANIZATION__DESCRIPTION:
        case OMPackage.ORGANIZATION__ALLOCATION_METHOD:
        case OMPackage.ORGANIZATION__DYNAMIC:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
            return;
        case OMPackage.ORGANIZATION__ATTRIBUTE_VALUES:
        case OMPackage.ORGANIZATION__UNITS:
        case OMPackage.ORGANIZATION__ORG_UNIT_RELATIONSHIPS:
        case OMPackage.ORGANIZATION__DYNAMIC_ORG_IDENTIFIERS:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), true, false));
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
        // OMPackage.Literals.ORG_ELEMENT__PARAMETERS, OMFactory.eINSTANCE
        // .createParameter()));

        Organization organization = (Organization) object;

        OrgUnit orgUnit = OMFactory.eINSTANCE.createOrgUnit();
        // orgUnit.setDisplayName(OMUtil.getDefaultName(
        //                getString("_UI_DefaultName_OrgUnit_label"), //$NON-NLS-1$
        // getDisplayNamesArray(getChildren(object))));

        String name =
                OMUtil.getDefaultName(getString("_UI_DefaultName_OrgSubUnit_label"), //$NON-NLS-1$
                        getDisplayNamesArray(organization.getUnits()));
        orgUnit.setDisplayName(name);

        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORGANIZATION__UNITS,
                        orgUnit));

        // newChildDescriptors.add(createChildParameter(
        // OMPackage.Literals.COMPANY__ORG_UNIT_RELATIONSHIPS,
        // OMFactory.eINSTANCE.createOrgUnitRelationship()));

        // XPD-5300 Add Dynamic Org Unit child to "static" Organization
        if (!organization.isDynamic()) {
            DynamicOrgUnit dynOrgUnit =
                    OMFactory.eINSTANCE.createDynamicOrgUnit();
            dynOrgUnit
                    .setDisplayName(OMUtil
                            .getDefaultName(getString("_UI_DefaultName_DynamicOrgUnit_label"), //$NON-NLS-1$
                                    getDisplayNamesArray(organization
                                            .getUnits())));
            newChildDescriptors
                    .add(createChildParameter(OMPackage.Literals.ORGANIZATION__UNITS,
                            dynOrgUnit));
        }

        OrganizationType type = organization.getOrganizationType();
        if (type != null) {
            for (OrgUnitFeature orgUintFeature : type.getOrgUnitFeatures()) {
                OrgUnit newOrgUnit = OMFactory.eINSTANCE.createOrgUnit();
                newOrgUnit.setFeature(orgUintFeature);
                newOrgUnit.setDisplayName(OMUtil.getDefaultName(orgUintFeature
                        .getDisplayName(), getDisplayNamesArray(organization
                        .getUnits())));
                newChildDescriptors.add(createChildParameter(orgUintFeature,
                        newOrgUnit));
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#createCommand(java.
     * lang.Object, org.eclipse.emf.edit.domain.EditingDomain, java.lang.Class,
     * org.eclipse.emf.edit.command.CommandParameter)
     */
    @Override
    public Command createCommand(Object object, EditingDomain domain,
            Class<? extends Command> commandClass,
            CommandParameter commandParameter) {
        Object feature = commandParameter.getFeature();
        Object value = commandParameter.getValue();
        if (commandParameter.getValue() instanceof CommandParameter) {
            feature =
                    ((CommandParameter) commandParameter.getValue())
                            .getFeature();
            value = ((CommandParameter) commandParameter.getValue()).getValue();
        }
        if (commandClass.equals(SetCommand.class)
                && object instanceof Organization
                && (feature == OMPackage.Literals.ORGANIZATION__ORGANIZATION_TYPE || feature == OMPackage.Literals.ORG_TYPED_ELEMENT__TYPE)) {
            Organization org = (Organization) object;
            OrganizationType oldType = org.getOrganizationType();
            if (oldType != null && !oldType.equals(value)) {
                CompoundCommand cmd = new CompoundCommand();
                for (OrgUnit orgUnit : org.getUnits()) {
                    if (orgUnit.getFeature() != null) {
                        cmd.append(SetCommand.create(domain,
                                orgUnit,
                                OMPackage.Literals.ORG_UNIT__FEATURE,
                                null));
                    }
                }
                cmd.append(super.createCommand(object,
                        domain,
                        commandClass,
                        commandParameter));
                return cmd;
            }
        }
        if (commandClass.equals(CreateChildCommand.class)
                && feature instanceof MultipleFeature) {
            if (feature instanceof OrgUnitFeature) {
                OrgUnitFeature orgUnitDescriptor = (OrgUnitFeature) feature;
                CompoundCommand cmd = new CompoundCommand();
                cmd.append(AddCommand.create(domain,
                        object,
                        OMPackage.Literals.ORGANIZATION__UNITS,
                        value));

                cmd.setLabel(orgUnitDescriptor.getDisplayName());

                cmd.setDescription(orgUnitDescriptor.getLabel());
                ActionCommandWrapper result =
                        new ActionCommandWrapper(
                                cmd,
                                ActionCommandWrapper
                                        .getImageForEObject(getAdapterFactory(),
                                                (EObject) value));
                return result;
            }
        }
        if (commandClass.equals(RemoveCommand.class)
                && commandParameter.getFeature() == null) {
            // Remove units. Also all relationships in which removed
            // units it participates will be removed.
            Collection<OrgUnitRelationship> relationshipsToRemove =
                    new ArrayList<OrgUnitRelationship>();
            for (Object o : commandParameter.getCollection()) {
                if (o instanceof OrgUnit) {
                    OrgUnit toRemove = (OrgUnit) o;
                    relationshipsToRemove.addAll(toRemove
                            .getOutgoingRelationships());
                    relationshipsToRemove.addAll(toRemove
                            .getIncomingRelationships());
                }
            }
            if (relationshipsToRemove.size() > 0) {
                CompoundCommand cmd = new CompoundCommand();
                for (OrgUnitRelationship orgUnitRelationship : relationshipsToRemove) {
                    cmd.append(RemoveCommand.create(domain,
                            orgUnitRelationship.eContainer(),
                            OMPackage.Literals.ORGANIZATION__ORG_UNIT_RELATIONSHIPS,
                            orgUnitRelationship));
                }
                cmd.append(super.createCommand(object,
                        domain,
                        commandClass,
                        commandParameter));
                return cmd;
            }
        }
        if (commandClass.equals(RemoveCommand.class)
                && OMPackage.Literals.ORGANIZATION__ORG_UNIT_RELATIONSHIPS
                        .equals(commandParameter.getFeature())) {
            CompoundCommand cmd = new CompoundCommand();
            for (Object o : commandParameter.getCollection()) {
                if (o instanceof OrgUnitRelationship) {
                    cmd.append(SetCommand.create(domain,
                            o,
                            OMPackage.Literals.ORG_UNIT_RELATIONSHIP__FROM,
                            null));
                    cmd.append(SetCommand.create(domain,
                            o,
                            OMPackage.Literals.ORG_UNIT_RELATIONSHIP__TO,
                            null));
                }
            }
            cmd.append(super.createCommand(object,
                    domain,
                    commandClass,
                    commandParameter));
            return cmd;
        }
        return super.createCommand(object,
                domain,
                commandClass,
                commandParameter);
    }
}
