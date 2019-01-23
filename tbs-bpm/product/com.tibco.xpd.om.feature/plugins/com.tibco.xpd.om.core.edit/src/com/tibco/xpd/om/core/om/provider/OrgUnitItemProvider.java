/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
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
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.util.OMUtil;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.xpd.om.core.om.OrgUnit} object. <!-- begin-user-doc --> <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class OrgUnitItemProvider extends NamedElementItemProvider implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    private static final String ORG_SUB_UNIT_FEATURE =
            "OrganizationSubUnitFeatue"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildren(java.lang
     * .Object)
     */
    @Override
    public Collection<?> getChildren(Object object) {
        OrgUnit orgUnit = (OrgUnit) object;
        List<Object> children = new ArrayList<Object>(orgUnit.getSubUnits());
        if (!children.isEmpty()) {
            children.addAll(super.getChildren(object));
            return children;
        }
        return super.getChildren(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getParent(java.lang
     * .Object)
     */
    @Override
    public Object getParent(Object object) {
        OrgUnit orgUnit = (OrgUnit) object;
        Organization organization = getOrgUnitOrganization(orgUnit);
        OrgUnit parentOrgUnit = orgUnit.getParentOrgUnit();
        return parentOrgUnit != null ? parentOrgUnit : organization;
    }

    @Override
    protected Command createSetCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Object value) {
        /*
         * Update the label/name of any relationships when the OrgUnit's
         * label/name changes. This should be only if the relationship has still
         * got the default name.
         */
        if (feature == OMPackage.Literals.NAMED_ELEMENT__NAME
                || feature == OMPackage.Literals.NAMED_ELEMENT__DISPLAY_NAME) {
            CompoundCommand ccmd = new CompoundCommand();
            OrgUnit unit = (OrgUnit) owner;
            EList<OrgUnitRelationship> incomings =
                    unit.getIncomingRelationships();
            EList<OrgUnitRelationship> outgoings =
                    unit.getOutgoingRelationships();

            if (incomings != null && !incomings.isEmpty()) {
                for (OrgUnitRelationship relationship : incomings) {
                    if (OMUtil.hasDefaultName(relationship)) {
                        // Update its name
                        ccmd.append(SetCommand
                                .create(domain,
                                        relationship,
                                        OMPackage.eINSTANCE
                                                .getNamedElement_DisplayName(),
                                        OMUtil.createOrgUnitRelationshipName(relationship
                                                .getFrom().getName(),
                                                (String) value,
                                                relationship.isIsHierarchical(),
                                                true)));
                    }
                }
            }

            if (outgoings != null && !outgoings.isEmpty()) {
                for (OrgUnitRelationship relationship : outgoings) {
                    if (OMUtil.hasDefaultName(relationship)) {
                        // Update its display name
                        ccmd.append(SetCommand
                                .create(domain,
                                        relationship,
                                        OMPackage.eINSTANCE
                                                .getNamedElement_DisplayName(),
                                        OMUtil.createOrgUnitRelationshipName((String) value,
                                                relationship.getTo().getName(),
                                                relationship.isIsHierarchical(),
                                                true)));
                    }
                }
            }

            ccmd.append(super.createSetCommand(domain, owner, feature, value));
            return ccmd;
        }
        return super.createSetCommand(domain, owner, feature, value);
    }

    /**
     * @param orgUnit
     * @return
     */
    private Organization getOrgUnitOrganization(OrgUnit orgUnit) {
        return (Organization) orgUnit.eContainer();
    }

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public OrgUnitItemProvider(AdapterFactory adapterFactory) {
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
            // addFeaturePropertyDescriptor(object);
            // addOrgUnitTypePropertyDescriptor(object);
            // addOutgoingRelationshipsPropertyDescriptor(object);
            // addIncomingRelationshipsPropertyDescriptor(object);
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
     * This adds a property descriptor for the Feature feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addFeaturePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgUnit_feature_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgUnit_feature_feature", "_UI_OrgUnit_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_UNIT__FEATURE,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Org Unit Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addOrgUnitTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgUnit_orgUnitType_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgUnit_orgUnitType_feature", "_UI_OrgUnit_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_UNIT__ORG_UNIT_TYPE,
                        false,
                        false,
                        false,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Outgoing Relationships feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addOutgoingRelationshipsPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgUnit_outgoingRelationships_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgUnit_outgoingRelationships_feature", "_UI_OrgUnit_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_UNIT__OUTGOING_RELATIONSHIPS,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Incoming Relationships feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addIncomingRelationshipsPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgUnit_incomingRelationships_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgUnit_incomingRelationships_feature", "_UI_OrgUnit_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_UNIT__INCOMING_RELATIONSHIPS,
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
            // childrenFeatures
            // .add(OMPackage.Literals.ORG_TYPED_ELEMENT__ATTRIBUTE_VALUES);
            // childrenFeatures
            // .add(OMPackage.Literals.
            // ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS);
            // childrenFeatures
            // .add(OMPackage.Literals.AUTHORIZABLE__SYSTEM_ACTIONS);
            childrenFeatures.add(OMPackage.Literals.ORG_UNIT__POSITIONS);
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
     * This returns OrgUnit.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {

        String imagePath = "full/obj16/OrgUnit"; //$NON-NLS-1$

        if (object instanceof OrgUnit) {
            OrgUnit ou = (OrgUnit) object;

            if (ou.getType() != null) {
                imagePath = "full/obj16/OrgUnitFeature"; //$NON-NLS-1$
            }
        }

        return overlayImage(object, getResourceLocator().getImage(imagePath));
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
        return label == null || label.length() == 0 ? getString("_UI_OrgUnit_type") : //$NON-NLS-1$
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

        switch (notification.getFeatureID(OrgUnit.class)) {
        case OMPackage.ORG_UNIT__PURPOSE:
        case OMPackage.ORG_UNIT__START_DATE:
        case OMPackage.ORG_UNIT__END_DATE:
        case OMPackage.ORG_UNIT__DESCRIPTION:
        case OMPackage.ORG_UNIT__ALLOCATION_METHOD:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
            return;
        case OMPackage.ORG_UNIT__ATTRIBUTE_VALUES:
        case OMPackage.ORG_UNIT__PRIVILEGE_ASSOCIATIONS:
        case OMPackage.ORG_UNIT__SYSTEM_ACTIONS:
        case OMPackage.ORG_UNIT__POSITIONS:
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

        OrgUnit orgUnit = ((OrgUnit) object);
        Organization organization = (Organization) orgUnit.eContainer();

        // general sub unit
        OrgUnit newOrgUnit = OMFactory.eINSTANCE.createOrgUnit();
        newOrgUnit.setFeature(null);
        newOrgUnit.setDisplayName(OMUtil
                .getDefaultName(getString("_UI_DefaultName_OrgSubUnit_label"), //$NON-NLS-1$
                        getDisplayNamesArray(organization.getUnits())));
        newChildDescriptors.add(createChildParameter(ORG_SUB_UNIT_FEATURE,
                newOrgUnit));

        /*
         * XPD-5300: Add Dynamic OrgUnit child if in "static" Organization
         */
        if (!organization.isDynamic()) {
            DynamicOrgUnit newDynamicOrgUnit =
                    OMFactory.eINSTANCE.createDynamicOrgUnit();
            newDynamicOrgUnit
                    .setDisplayName(OMUtil
                            .getDefaultName(getString("_UI_DefaultName_DynamicOrgUnit_label"), //$NON-NLS-1$
                                    getDisplayNamesArray(organization
                                            .getUnits())));
            newChildDescriptors.add(createChildParameter(ORG_SUB_UNIT_FEATURE,
                    newDynamicOrgUnit));
        }

        // newChildDescriptors.add(createChildParameter(
        // OMPackage.Literals.ORG_ELEMENT__PARAMETERS, OMFactory.eINSTANCE
        // .createParameter()));
        OrgUnitType type = orgUnit.getOrgUnitType();

        // any orgUnit features
        if (type != null) {
            for (OrgUnitFeature orgUnitFeature : type.getOrgUnitFeatures()) {
                newOrgUnit = OMFactory.eINSTANCE.createOrgUnit();
                newOrgUnit.setFeature(orgUnitFeature);
                newOrgUnit.setDisplayName(OMUtil.getDefaultName(orgUnitFeature
                        .getDisplayName(), getDisplayNamesArray(organization
                        .getUnits())));
                newChildDescriptors.add(createChildParameter(orgUnitFeature,
                        newOrgUnit));
            }
        }

        // general position
        Position position = OMFactory.eINSTANCE.createPosition();
        position.setDisplayName(OMUtil
                .getDefaultName(getString("_UI_DefaultName_Position_label"), //$NON-NLS-1$
                        getDisplayNamesArray(getChildren(object))));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_UNIT__POSITIONS,
                        position));

        // any position fetures
        if (type != null) {
            for (PositionFeature posFeature : type.getPositionFeatures()) {
                position = OMFactory.eINSTANCE.createPosition();
                position.setFeature(posFeature);
                position.setDisplayName(OMUtil.getDefaultName(posFeature
                        .getDisplayName(),
                        getDisplayNamesArray(getChildren(object))));

                newChildDescriptors.add(createChildParameter(posFeature,
                        position));
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
        if (commandClass.equals(SetCommand.class) && object instanceof OrgUnit
                && feature == OMPackage.Literals.ORG_UNIT__FEATURE) {
            OrgUnit orgUnit = (OrgUnit) object;
            OrgUnitFeature oldFeature = orgUnit.getFeature();
            if (oldFeature != null && !oldFeature.equals(value)) {
                CompoundCommand cmd = new CompoundCommand();

                for (Position postition : orgUnit.getPositions()) {
                    if (postition.getFeature() != null) {
                        cmd.append(SetCommand.create(domain,
                                postition,
                                OMPackage.Literals.POSITION__FEATURE,
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
                OrgUnitFeature orgUnitFeature = (OrgUnitFeature) feature;
                OrgUnit parentOrgUnit = (OrgUnit) object;
                EObject container = parentOrgUnit.eContainer();

                OrgUnit newOrgUnit = (OrgUnit) value;
                OrgUnitRelationship rel =
                        OMFactory.eINSTANCE.createOrgUnitRelationship();
                rel.setType(orgUnitFeature.getContextRelationshipType());
                rel.setIsHierarchical(true);

                // set the name of the relationship
                rel.setName(OMUtil.createOrgUnitRelationshipName(parentOrgUnit
                        .getName(), newOrgUnit.getName(), true, false));
                rel.setDisplayName(OMUtil
                        .createOrgUnitRelationshipName(parentOrgUnit.getName(),
                                newOrgUnit.getName(),
                                true,
                                true));

                CompoundCommand cmd = new CompoundCommand();
                cmd.append(AddCommand.create(domain,
                        container,
                        OMPackage.Literals.ORGANIZATION__UNITS,
                        newOrgUnit));
                cmd.append(AddCommand
                        .create(domain,
                                container,
                                OMPackage.Literals.ORGANIZATION__ORG_UNIT_RELATIONSHIPS,
                                rel));
                cmd.append(SetCommand.create(domain,
                        rel,
                        OMPackage.Literals.ORG_UNIT_RELATIONSHIP__FROM,
                        parentOrgUnit));
                cmd.append(SetCommand.create(domain,
                        rel,
                        OMPackage.Literals.ORG_UNIT_RELATIONSHIP__TO,
                        newOrgUnit));
                cmd.setLabel(orgUnitFeature.getDisplayName());
                cmd.setDescription(orgUnitFeature.getLabel());
                ActionCommandWrapper result =
                        new ActionCommandWrapper(
                                cmd,
                                ActionCommandWrapper
                                        .getImageForEObject(getAdapterFactory(),
                                                newOrgUnit));
                return result;
            } else if (feature instanceof PositionFeature) {
                PositionFeature positionFeature = (PositionFeature) feature;
                Position newPosition = (Position) value;
                CompoundCommand cmd = new CompoundCommand();
                cmd.append(AddCommand.create(domain,
                        object,
                        OMPackage.Literals.ORG_UNIT__POSITIONS,
                        newPosition));
                cmd.setLabel(positionFeature.getDisplayName());
                cmd.setDescription(positionFeature.getLabel());
                return new ActionCommandWrapper(cmd,
                        ActionCommandWrapper
                                .getImageForEObject(getAdapterFactory(),
                                        newPosition));
            }
            Assert.isLegal(false,
                    String.format(getString("_UI_OrgUnitChildDescriptorTypeNotSupported_error_shortdesc"), //$NON-NLS-1$
                            feature));
        } else if (commandClass.equals(CreateChildCommand.class)
                && ORG_SUB_UNIT_FEATURE.equals(feature)) {
            OrgUnit parentOrgUnit = (OrgUnit) object;
            EObject container = parentOrgUnit.eContainer();

            OrgUnit newOrgUnit = (OrgUnit) value;
            OrgUnitRelationship rel =
                    OMFactory.eINSTANCE.createOrgUnitRelationship();
            rel.setIsHierarchical(true);

            // Set the default name as concatenation of source and target names
            rel.setName(parentOrgUnit.getName() + newOrgUnit.getName());
            rel.setDisplayName(parentOrgUnit.getName() + "::" //$NON-NLS-1$
                    + newOrgUnit.getName());

            CompoundCommand cmd = new CompoundCommand();
            cmd.append(AddCommand.create(domain,
                    container,
                    OMPackage.Literals.ORGANIZATION__UNITS,
                    newOrgUnit));
            cmd.append(AddCommand.create(domain,
                    container,
                    OMPackage.Literals.ORGANIZATION__ORG_UNIT_RELATIONSHIPS,
                    rel));
            cmd.append(SetCommand.create(domain,
                    rel,
                    OMPackage.Literals.ORG_UNIT_RELATIONSHIP__FROM,
                    parentOrgUnit));
            cmd.append(SetCommand.create(domain,
                    rel,
                    OMPackage.Literals.ORG_UNIT_RELATIONSHIP__TO,
                    newOrgUnit));
            // XPD-5300: If Dynamic OrgUnit then use appropriate label
            if (newOrgUnit instanceof DynamicOrgUnit) {
                cmd.setLabel(getString("_UI_DynamicOrgUnit_type")); //$NON-NLS-1$
                cmd.setDescription(getString("_UI_DynamicOrgUnit_type")); //$NON-NLS-1$
            } else {
                cmd.setLabel(getString("_UI_OrgUnit_type")); //$NON-NLS-1$
                cmd.setDescription(getString("_UI_OrgUnit_type")); //$NON-NLS-1$
            }
            ActionCommandWrapper result =
                    new ActionCommandWrapper(cmd,
                            ActionCommandWrapper
                                    .getImageForEObject(getAdapterFactory(),
                                            newOrgUnit));
            return result;
        }
        if (commandClass.equals(RemoveCommand.class) && feature == null) {
            // Remove units (changing the command to remove units from the
            // correct parent and parent's feature). Also all relationships in
            // which removed
            // units it participates will be removed. All other objects removed
            // from different orgUnit's features will delegate command
            // creation to parent.
            Collection<?> allToRemove =
                    new LinkedList<Object>(commandParameter.getCollection());
            Collection<OrgUnit> orgUnitsToRemove = new ArrayList<OrgUnit>();
            Collection<OrgUnitRelationship> relationshipsToRemove =
                    new ArrayList<OrgUnitRelationship>();
            for (Object o : commandParameter.getCollection()) {
                if (o instanceof OrgUnit) {
                    OrgUnit toRemove = (OrgUnit) o;
                    allToRemove.remove(toRemove);
                    orgUnitsToRemove.add(toRemove);
                    relationshipsToRemove.addAll(toRemove
                            .getOutgoingRelationships());
                    relationshipsToRemove.addAll(toRemove
                            .getIncomingRelationships());
                }
            }
            if (orgUnitsToRemove.size() > 0) {
                CompoundCommand cmd = new CompoundCommand();
                if (allToRemove.size() > 0) {
                    cmd.append(super.createCommand(object,
                            domain,
                            commandClass,
                            new CommandParameter(object, null, allToRemove)));
                }
                OrgUnit parentOrgUnit = (OrgUnit) object;
                EObject container = parentOrgUnit.eContainer();
                if (container != null) {
                    for (OrgUnitRelationship orgUnitRelationship : relationshipsToRemove) {
                        cmd.append(RemoveCommand
                                .create(domain,
                                        orgUnitRelationship.eContainer(),
                                        OMPackage.Literals.ORGANIZATION__ORG_UNIT_RELATIONSHIPS,
                                        orgUnitRelationship));
                    }
                    cmd.append(RemoveCommand.create(domain,
                            container,
                            OMPackage.Literals.ORGANIZATION__UNITS,
                            orgUnitsToRemove));
                }
                return cmd;
            }
        }
        return super.createCommand(object,
                domain,
                commandClass,
                commandParameter);
    }
}
