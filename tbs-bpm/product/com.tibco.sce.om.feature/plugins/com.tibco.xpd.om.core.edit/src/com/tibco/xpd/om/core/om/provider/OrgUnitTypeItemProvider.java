/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.util.OMUtil;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.om.core.om.OrgUnitType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class OrgUnitTypeItemProvider extends OrgElementTypeItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /** {@inheritDoc} */
    @Override
    public Object getParent(Object object) {
        Object parent = super.getParent(object);
        if (parent instanceof OrgMetaModel) {
            // item provider for patent has to be TransientItemProviderParent
            TransientItemProviderParent patentItemProvider =
                    (TransientItemProviderParent) adapterFactory.adapt(parent,
                            IEditingDomainItemProvider.class);
            return patentItemProvider != null ? patentItemProvider
                    .getTransientParent(parent,
                            OMPackage.Literals.ORG_META_MODEL__ORG_UNIT_TYPES)
                    : null;
        }
        return parent;
    }

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public OrgUnitTypeItemProvider(AdapterFactory adapterFactory) {
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

        }
        return itemPropertyDescriptors;
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
                    .add(OMPackage.Literals.ORG_UNIT_TYPE__ORG_UNIT_FEATURES);
            childrenFeatures
                    .add(OMPackage.Literals.ORG_UNIT_TYPE__POSITION_FEATURES);
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
     * This returns OrgUnitType.gif. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator()
                .getImage("full/obj16/OrgUnitType")); //$NON-NLS-1$
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
        return label == null || label.length() == 0 ? getString("_UI_OrgUnitType_type") : //$NON-NLS-1$
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

        switch (notification.getFeatureID(OrgUnitType.class)) {
        case OMPackage.ORG_UNIT_TYPE__ORG_UNIT_FEATURES:
        case OMPackage.ORG_UNIT_TYPE__POSITION_FEATURES:
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

        OrgUnitFeature orgUnitFeature =
                OMFactory.eINSTANCE.createOrgUnitFeature();
        orgUnitFeature
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_OrgUnitFeature_label"), //$NON-NLS-1$
                                getDisplayNamesArray(getChildren(object))));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_UNIT_TYPE__ORG_UNIT_FEATURES,
                        orgUnitFeature));

        PositionFeature positionFeature =
                OMFactory.eINSTANCE.createPositionFeature();
        positionFeature
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_PositionFeature_label"), //$NON-NLS-1$
                                getDisplayNamesArray(getChildren(object))));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_UNIT_TYPE__POSITION_FEATURES,
                        positionFeature));
    }

}
