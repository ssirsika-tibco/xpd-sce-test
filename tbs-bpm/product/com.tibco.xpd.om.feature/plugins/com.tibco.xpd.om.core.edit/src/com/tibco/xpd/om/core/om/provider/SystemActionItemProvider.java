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
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.SystemAction;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.xpd.om.core.om.SystemAction} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class SystemActionItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public SystemActionItemProvider(AdapterFactory adapterFactory) {
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

            addIdPropertyDescriptor(object);
            addActionIdPropertyDescriptor(object);
            addComponentPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    @Override
    protected Command createRemoveCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Collection<?> collection) {

        /*
         * Sid ACE-4180 We have to remove all the Privilege associations from the system action before removing the
         * system action record itself (when we're deleting all associations) OTHERWISE they remain in the
         * cross-reference adapters with the system action as their parent and then the privileges themselves cannot be
         * deleted because the system thinks they are still referenced.
         * 
         * So always delete the privilege associations and THEN delete the system action if thses are the only ones.
         */
        Command removePrivAssocCmd = super.createRemoveCommand(domain, owner, feature, collection);

        if (removePrivAssocCmd == null) {
            return null;
        }

        CompoundCommand cmd = new CompoundCommand();
        cmd.append(removePrivAssocCmd);

        /*
         * If all privilege associations from a System Action are being removed then remove the System Action itself
         * from its parent
         */
        if (owner instanceof SystemAction
                && ((SystemAction) owner).getPrivilegeAssociations().size() == collection
                        .size()) {


            cmd.append(RemoveCommand.create(domain, owner.eContainer(), owner.eContainingFeature(), owner));
        }

        return cmd;
    }

    /**
     * This adds a property descriptor for the Id feature. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_ModelElement_id_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_ModelElement_id_feature", "_UI_ModelElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.MODEL_ELEMENT__ID,
                        false,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Action Id feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addActionIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SystemAction_actionId_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SystemAction_actionId_feature", "_UI_SystemAction_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.SYSTEM_ACTION__ACTION_ID,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Component feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addComponentPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SystemAction_component_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SystemAction_component_feature", "_UI_SystemAction_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.SYSTEM_ACTION__COMPONENT,
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
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures
                    .add(OMPackage.Literals.ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS);
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
     * This returns SystemAction.gif. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator()
                .getImage("full/obj16/SystemAction")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((SystemAction) object).getId();
        return label == null || label.length() == 0 ? getString("_UI_SystemAction_type") : //$NON-NLS-1$
                getString("_UI_SystemAction_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(SystemAction.class)) {
        case OMPackage.SYSTEM_ACTION__ID:
        case OMPackage.SYSTEM_ACTION__ACTION_ID:
        case OMPackage.SYSTEM_ACTION__COMPONENT:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
            return;
        case OMPackage.SYSTEM_ACTION__PRIVILEGE_ASSOCIATIONS:
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
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ASSOCIABLE_WITH_PRIVILEGES__PRIVILEGE_ASSOCIATIONS,
                        OMFactory.eINSTANCE.createPrivilegeAssociation()));
    }

    /**
     * This returns the icon image for
     * {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object getCreateChildImage(Object owner, Object feature,
            Object child, Collection<?> selection) {
        if (feature instanceof EStructuralFeature
                && FeatureMapUtil.isFeatureMap((EStructuralFeature) feature)) {
            FeatureMap.Entry entry = (FeatureMap.Entry) child;
            feature = entry.getEStructuralFeature();
            child = entry.getValue();
        }

        if (feature instanceof EReference && child instanceof EObject) {
            String name = "full/obj16/" + ((EObject) child).eClass().getName(); //$NON-NLS-1$

            try {
                return getResourceLocator().getImage(name);
            } catch (Exception e) {
                OrganisationModelEditPlugin.INSTANCE.log(e);
            }
        }

        return super.getCreateChildImage(owner, feature, child, selection);
    }

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return OrganisationModelEditPlugin.INSTANCE;
    }

}
