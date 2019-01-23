/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
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

import com.tibco.xpd.om.core.om.LocationType;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.core.om.ResourceType;
import com.tibco.xpd.om.core.om.util.OMUtil;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.xpd.om.core.om.OrgMetaModel} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated NOT
 */
public class OrgMetaModelItemProvider extends BaseOrgModelItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource,
        TransientItemProviderParent {

    protected List<TransientItemProvider> transientChildren;

    /** {@inheritDoc} */
    public TransientItemProvider getTransientParent(Object object,
            Object feature) {
        for (TransientItemProvider group : getTransientChildren(object)) {
            if (group.isParentForFeature(feature)) {
                return group;
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    public Collection<TransientItemProvider> getTransientChildren(Object object) {
        if (transientChildren == null) {
            transientChildren = new ArrayList<TransientItemProvider>();
            transientChildren.add(new ResourceTypesTransientItemProvider(
                    adapterFactory, (EObject) object));
            transientChildren.add(new LocationTypesTransientItemProvider(
                    adapterFactory, (EObject) object));
            transientChildren
                    .add(new OrgUnitRelationshipTypesTransientItemProvider(
                            adapterFactory, (EObject) object));
            transientChildren.add(new OrganizationTypesTransientItemProvider(
                    adapterFactory, (EObject) object));
            transientChildren.add(new OrgUnitTypesTransientItemProvider(
                    adapterFactory, (EObject) object));
            transientChildren.add(new PositionTypesTransientItemProvider(
                    adapterFactory, (EObject) object));
        }
        return transientChildren;
    }

    /** {@inheritDoc} */
    @Override
    public Collection<?> getChildren(Object object) {
        List<Object> children = new ArrayList<Object>();
        children.addAll(getTransientChildren(object));
        children.addAll(super.getChildren(object));
        return children;
    }

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public OrgMetaModelItemProvider(AdapterFactory adapterFactory) {
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
            // This should always be empty list for embedded meta-models
            itemPropertyDescriptors = new ArrayList<IItemPropertyDescriptor>();

            // addEmbeddedPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Embedded feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addEmbeddedPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_OrgMetaModel_embedded_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_OrgMetaModel_embedded_feature", "_UI_OrgMetaModel_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.ORG_META_MODEL__EMBEDDED,
                        false,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
            // if the children features are to be displayed directly under
            // meta-model then add appropriate lines simlar to:
            // childrenFeatures
            // .add(OMPackage.Literals.ORG_META_MODEL__ORGANIZATION_TYPES);

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
     * This returns OrgMetaModel.gif. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator()
                .getImage("full/obj16/OrgMetaModel")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public String getText(Object object) {
        OrgMetaModel metamodel = (OrgMetaModel) object;
        if (metamodel.eContainer() instanceof OrgModel) { // Embedded meta-model
            return getString("_UI_OrgMetaModel_schema_label"); //$NON-NLS-1$
        }
        String label = metamodel.getLabel();
        return label == null || label.length() == 0 ? getString("_UI_OrgMetaModel_type") : //$NON-NLS-1$
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

        switch (notification.getFeatureID(OrgMetaModel.class)) {
        case OMPackage.ORG_META_MODEL__EMBEDDED:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
            return;
        case OMPackage.ORG_META_MODEL__LOCATION_TYPES:
        case OMPackage.ORG_META_MODEL__ORG_UNIT_RELATIONSHIP_TYPES:
        case OMPackage.ORG_META_MODEL__ORGANIZATION_TYPES:
        case OMPackage.ORG_META_MODEL__ORG_UNIT_TYPES:
        case OMPackage.ORG_META_MODEL__POSITION_TYPES:
        case OMPackage.ORG_META_MODEL__RESOURCE_TYPES:
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
    @SuppressWarnings("unchecked")
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        OrgMetaModel metaModel = (OrgMetaModel) object;
        LocationType locationType = OMFactory.eINSTANCE.createLocationType();
        locationType
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_LocationType_label"), //$NON-NLS-1$
                                getDisplayNamesArray(metaModel
                                        .getLocationTypes())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_META_MODEL__LOCATION_TYPES,
                        locationType));

        OrgUnitRelationshipType orgUnitRelationshipType =
                OMFactory.eINSTANCE.createOrgUnitRelationshipType();
        orgUnitRelationshipType
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_OrgUnitRelationshipType_label"), //$NON-NLS-1$
                                getDisplayNamesArray(metaModel
                                        .getOrgUnitRelationshipTypes())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_META_MODEL__ORG_UNIT_RELATIONSHIP_TYPES,
                        orgUnitRelationshipType));

        OrganizationType organisationType =
                OMFactory.eINSTANCE.createOrganizationType();
        organisationType
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_OrganisationType_label"), //$NON-NLS-1$
                                getDisplayNamesArray(metaModel
                                        .getOrganizationTypes())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_META_MODEL__ORGANIZATION_TYPES,
                        organisationType));

        OrgUnitType orgUnitType = OMFactory.eINSTANCE.createOrgUnitType();
        orgUnitType.setDisplayName(OMUtil
                .getDefaultName(getString("_UI_DefaultName_OrgUnitType_label"), //$NON-NLS-1$
                        getDisplayNamesArray(metaModel.getOrgUnitTypes())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_META_MODEL__ORG_UNIT_TYPES,
                        orgUnitType));

        PositionType positionType = OMFactory.eINSTANCE.createPositionType();
        positionType
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_PositionType_label"), //$NON-NLS-1$
                                getDisplayNamesArray(metaModel
                                        .getPositionTypes())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_META_MODEL__POSITION_TYPES,
                        positionType));

        ResourceType resourceType = OMFactory.eINSTANCE.createResourceType();
        resourceType
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_ResourceType_label"), //$NON-NLS-1$
                                getDisplayNamesArray(metaModel
                                        .getResourceTypes())));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_META_MODEL__RESOURCE_TYPES,
                        resourceType));

    }

}
