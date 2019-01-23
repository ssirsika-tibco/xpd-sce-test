/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityCategory;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgQuery;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeCategory;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.resources.propertytesters.PlatformPropertyTester;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.xpd.om.core.om.OrgModel} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated NOT
 */
public class OrgModelItemProvider extends BaseOrgModelItemProvider implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource,
        TransientItemProviderParent {

    protected List<TransientItemProvider> transientChildren;

    /** {@inheritDoc} */
    @Override
    public TransientItemProvider getTransientParent(Object object,
            Object feature) {
        for (TransientItemProvider group : getTransientChildren(object)) {
            if (group.isParentForFeature(feature)) {
                return group;
            }
        }
        return null;
    }

    @Override
    protected Command createSetCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Object value) {

        // Cannot unset Human resource type
        if (feature == OMPackage.eINSTANCE.getOrgModel_HumanResourceType()
                && (value == null || value == SetCommand.UNSET_VALUE)) {
            return UnexecutableCommand.INSTANCE;
        }
        return super.createSetCommand(domain, owner, feature, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildren(java.lang
     * .Object)
     */
    @Override
    public Collection<?> getChildren(Object object) {
        List<Object> children = new ArrayList<Object>();
        children.addAll(getTransientChildren(object));
        children.addAll(super.getChildren(object));
        return children;
    }

    /** {@inheritDoc} */
    @Override
    public Collection<TransientItemProvider> getTransientChildren(Object object) {
        if (transientChildren == null) {
            transientChildren = new ArrayList<TransientItemProvider>();
            transientChildren.add(new CapabilitiesTransientItemProvider(
                    adapterFactory, (EObject) object));
            transientChildren.add(new PrivilegesTransientItemProvider(
                    adapterFactory, (EObject) object));
            transientChildren.add(new GroupsTransientItemProvider(
                    adapterFactory, (EObject) object));
            transientChildren.add(new LocationsTransientItemProvider(
                    adapterFactory, (EObject) object));
            transientChildren.add(new ResourcesTransientItemProvider(
                    adapterFactory, (EObject) object));
            transientChildren.add(new OrganizationsTransientItemProvider(
                    adapterFactory, (EObject) object));

            if (isWMinstalled()) {
                transientChildren.add(new OrgQueriesTransientItemProvider(
                        adapterFactory, (EObject) object));
            }
        }
        return transientChildren;
    }

    private boolean isWMinstalled() {
        return new PlatformPropertyTester().test(null,
                PlatformPropertyTester.PROPERTY_IS_BUNDLE_INSTALLED,
                new Object[] { "com.tibco.xpd.wm.resources.ui" }, //$NON-NLS-1$
                null);
    }

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public OrgModelItemProvider(AdapterFactory adapterFactory) {
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

            // addMetamodelsPropertyDescriptor(object);
            // addHumanResourceTypePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Metamodels feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addMetamodelsPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgModel_metamodels_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_OrgModel_metamodels_feature", //$NON-NLS-1$
                                "_UI_OrgModel_type"), //$NON-NLS-1$
                        OMPackage.Literals.ORG_MODEL__METAMODELS,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Human Resource Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addHumanResourceTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgModel_humanResourceType_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_OrgModel_humanResourceType_feature", //$NON-NLS-1$
                                "_UI_OrgModel_type"), //$NON-NLS-1$
                        OMPackage.Literals.ORG_MODEL__HUMAN_RESOURCE_TYPE,
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
            childrenFeatures
                    .add(OMPackage.Literals.ORG_MODEL__EMBEDDED_METAMODEL);
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
     * This returns OrgModel.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/OrgModel")); //$NON-NLS-1$
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
        return label == null || label.length() == 0 ? getString("_UI_OrgModel_type") //$NON-NLS-1$
                : label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to
     * update any cached children and by creating a viewer notification, which
     * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(OrgModel.class)) {
        case OMPackage.ORG_MODEL__GROUPS:
        case OMPackage.ORG_MODEL__CAPABILITY_CATEGORIES:
        case OMPackage.ORG_MODEL__PRIVILEGE_CATEGORIES:
        case OMPackage.ORG_MODEL__CAPABILITIES:
        case OMPackage.ORG_MODEL__PRIVILEGES:
        case OMPackage.ORG_MODEL__ORGANIZATIONS:
        case OMPackage.ORG_MODEL__LOCATIONS:
        case OMPackage.ORG_MODEL__RESOURCES:
        case OMPackage.ORG_MODEL__EMBEDDED_METAMODEL:
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
    @SuppressWarnings("unchecked")
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        OrgModel orgModel = (OrgModel) object;
        Group group = OMFactory.eINSTANCE.createGroup();
        group.setDisplayName(OMUtil
                .getDefaultName(getString("_UI_DefaultName_Group_label"), //$NON-NLS-1$
                        getDisplayNamesArray(orgModel.getGroups())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__GROUPS,
                        group));

        CapabilityCategory capabilityCategory =
                OMFactory.eINSTANCE.createCapabilityCategory();
        capabilityCategory
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_CapabilityCategory_label"), //$NON-NLS-1$
                                getDisplayNamesArray(orgModel
                                        .getCapabilityCategories())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__CAPABILITY_CATEGORIES,
                        capabilityCategory));

        Capability capability = OMFactory.eINSTANCE.createCapability();
        capability.setDisplayName(OMUtil
                .getDefaultName(getString("_UI_DefaultName_Capability_label"), //$NON-NLS-1$
                        getDisplayNamesArray(orgModel.getCapabilities())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__CAPABILITIES,
                        capability));

        PrivilegeCategory privilegeCategory =
                OMFactory.eINSTANCE.createPrivilegeCategory();
        privilegeCategory
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_PrivilegeCategory_label"), //$NON-NLS-1$
                                getDisplayNamesArray(orgModel
                                        .getPrivilegeCategories())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__PRIVILEGE_CATEGORIES,
                        privilegeCategory));

        Privilege privilege = OMFactory.eINSTANCE.createPrivilege();
        privilege.setDisplayName(OMUtil
                .getDefaultName(getString("_UI_DefaultName_Privilege_label"), //$NON-NLS-1$
                        getDisplayNamesArray(orgModel.getPrivileges())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__PRIVILEGES,
                        privilege));

        Organization organization = OMFactory.eINSTANCE.createOrganization();
        organization
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_Organisation_label"), //$NON-NLS-1$
                                getDisplayNamesArray(orgModel
                                        .getOrganizations())));
        // newCompany.setType(OMTypesHelper.getIstance()
        // .getDefaultOrganizationType(object));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__ORGANIZATIONS,
                        organization));

        // Add a child descriptor for each OrganizationType in the embedded
        // schema
        EList<OrganizationType> organizationTypes =
                orgModel.getEmbeddedMetamodel().getOrganizationTypes();

        for (OrganizationType organizationType : organizationTypes) {
            Organization org = OMFactory.eINSTANCE.createOrganization();
            org.setDisplayName(OMUtil.getDefaultName(organizationType
                    .getDisplayName(), getDisplayNamesArray(orgModel
                    .getOrganizations())));

            org.setType(organizationType);
            newChildDescriptors
                    .add(createChildParameter(OMPackage.Literals.ORG_MODEL__ORGANIZATIONS,
                            org));
        }

        Location location = OMFactory.eINSTANCE.createLocation();
        location.setDisplayName(OMUtil
                .getDefaultName(getString("_UI_DefaultName_Location_label"), //$NON-NLS-1$
                        getDisplayNamesArray(orgModel.getLocations())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__LOCATIONS,
                        location));

        Resource resource = OMFactory.eINSTANCE.createResource();
        resource.setDisplayName(OMUtil
                .getDefaultName(getString("_UI_DefaultName_Resource_label"), //$NON-NLS-1$
                        getDisplayNamesArray(orgModel.getResources())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__RESOURCES,
                        resource));

        if (isWMinstalled()) {
            OrgQuery orgQuery = OMFactory.eINSTANCE.createOrgQuery();
            orgQuery.setDisplayName(OMUtil
                    .getDefaultName(getString("_UI_DefaultName_OrgQuery_label"), //$NON-NLS-1$
                            getDisplayNamesArray(orgModel.getQueries())));
            newChildDescriptors
                    .add(createChildParameter(OMPackage.Literals.ORG_MODEL__QUERIES,
                            orgQuery));
        }
    }
}
