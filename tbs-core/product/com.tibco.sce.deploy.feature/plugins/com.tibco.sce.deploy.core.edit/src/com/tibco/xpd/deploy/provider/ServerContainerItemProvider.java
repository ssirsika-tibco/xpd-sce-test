/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerContainer;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.deploy.ServerContainer} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ServerContainerItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";
    private List<EStructuralFeature> childrenDeletableFeatures;

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ServerContainerItemProvider(AdapterFactory adapterFactory) {
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
                    .add(DeployPackage.Literals.SERVER_CONTAINER__SERVERS);
            childrenFeatures
                    .add(DeployPackage.Literals.SERVER_CONTAINER__SERVER_GROUPS);
        }
        return childrenFeatures;
    }

    /**
     * This specifies how to {@link org.eclipse.emf.edit.command.RemoveCommand}
     * or {@link #createCommand}.
     */
    public Collection<? extends EStructuralFeature> getAnyDeletableChildrenFeatures(
            Object object) {
        if (childrenDeletableFeatures == null) {
            childrenDeletableFeatures = new ArrayList<EStructuralFeature>();
            childrenDeletableFeatures
                    .add(DeployPackage.Literals.SERVER_CONTAINER__SERVERS);
            childrenDeletableFeatures
                    .add(DeployPackage.Literals.SERVER_CONTAINER__SERVER_GROUPS);
            childrenDeletableFeatures
                    .add(DeployPackage.Literals.SERVER_CONTAINER__SERVER_TYPES);
            childrenDeletableFeatures
                    .add(DeployPackage.Literals.SERVER_CONTAINER__SERVER_GROUP_TYPES);
            childrenDeletableFeatures
                    .add(DeployPackage.Literals.SERVER_CONTAINER__RUNTIMES);
            childrenDeletableFeatures
                    .add(DeployPackage.Literals.SERVER_CONTAINER__RUNTIME_TYPES);
        }
        return childrenDeletableFeatures;
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
     * This returns ServerContainer.gif.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage(
                "full/obj16/ServerContainer"));
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        return getString("_UI_ServerContainer_type");
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

        switch (notification.getFeatureID(ServerContainer.class)) {
        case DeployPackage.SERVER_CONTAINER__RUNTIMES:
        case DeployPackage.SERVER_CONTAINER__SERVER_TYPES:
        case DeployPackage.SERVER_CONTAINER__REPOSITORY_TYPES:
        case DeployPackage.SERVER_CONTAINER__RUNTIME_TYPES:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
            return;
        case DeployPackage.SERVER_CONTAINER__SERVERS:
        case DeployPackage.SERVER_CONTAINER__SERVER_GROUPS:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), true, true));
            return;
        }
        super.notifyChanged(notification);
    }

    /** {@inheritDoc} */
    @Override
    protected Command factorRemoveCommand(EditingDomain domain,
            CommandParameter commandParameter) {
        if (commandParameter.getCollection() == null
                || commandParameter.getCollection().isEmpty()) {
            return UnexecutableCommand.INSTANCE;
        }

        final EObject eObject = commandParameter.getEOwner();
        final List<Object> list = new ArrayList<Object>(commandParameter
                .getCollection());

        CompoundCommand removeCommand = new CompoundCommand(
                CompoundCommand.MERGE_COMMAND_ALL);

        // Iterator over all the child references to factor each child to the
        // right reference.
        //
        for (EStructuralFeature feature : getAnyDeletableChildrenFeatures(eObject)) {
            // If it is a list type value...
            // 
            if (feature.isMany()) {
                List<?> value = (List<?>) getFeatureValue(eObject, feature);

                // These will be the children belonging to this feature.
                //
                Collection<Object> childrenOfThisFeature = new ArrayList<Object>();
                for (ListIterator<Object> objects = list.listIterator(); objects
                        .hasNext();) {
                    Object o = objects.next();

                    // Is this object in this feature...
                    //
                    if (value.contains(o)) {
                        // Add it to the list and remove it from the other list.
                        //
                        childrenOfThisFeature.add(o);
                        objects.remove();
                    }
                }

                // If we have children to remove for this feature, create a
                // command for it.
                //
                if (!childrenOfThisFeature.isEmpty()) {
                    removeCommand.append(createRemoveCommand(domain, eObject,
                            feature, childrenOfThisFeature));
                }
            } else {
                // It's just a single value
                //
                final Object value = getFeatureValue(eObject, feature);
                for (ListIterator<Object> objects = list.listIterator(); objects
                        .hasNext();) {
                    Object o = objects.next();

                    // Is this object in this feature...
                    //
                    if (o == value) {
                        // Create a command to set this to null and remove the
                        // object from the other list.
                        //
                        Command setCommand = createSetCommand(domain, eObject,
                                feature, null);
                        removeCommand.append(new CommandWrapper(setCommand) {
                            protected Collection<?> affected;

                            @Override
                            public void execute() {
                                super.execute();
                                affected = Collections.singleton(eObject);
                            }

                            @Override
                            public void undo() {
                                super.undo();
                                affected = Collections.singleton(value);
                            }

                            @Override
                            public void redo() {
                                super.redo();
                                affected = Collections.singleton(eObject);
                            }

                            @Override
                            public Collection<?> getResult() {
                                return Collections.singleton(value);
                            }

                            @Override
                            public Collection<?> getAffectedObjects() {
                                return affected;
                            }
                        });
                        objects.remove();
                        break;
                    }
                }
            }
        }

        // If all the objects are used up by the above, then we can't do the
        // command.
        //
        if (list.isEmpty()) {
            return removeCommand.unwrap();
        } else {
            removeCommand.dispose();
            return UnexecutableCommand.INSTANCE;
        }
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

        newChildDescriptors.add(createChildParameter(
                DeployPackage.Literals.SERVER_CONTAINER__SERVERS,
                DeployFactory.eINSTANCE.createServer()));

        newChildDescriptors.add(createChildParameter(
                DeployPackage.Literals.SERVER_CONTAINER__RUNTIMES,
                DeployFactory.eINSTANCE.createRuntime()));

        newChildDescriptors.add(createChildParameter(
                DeployPackage.Literals.SERVER_CONTAINER__SERVER_TYPES,
                DeployFactory.eINSTANCE.createServerType()));

        newChildDescriptors.add(createChildParameter(
                DeployPackage.Literals.SERVER_CONTAINER__REPOSITORY_TYPES,
                DeployFactory.eINSTANCE.createRepositoryType()));

        newChildDescriptors.add(createChildParameter(
                DeployPackage.Literals.SERVER_CONTAINER__RUNTIME_TYPES,
                DeployFactory.eINSTANCE.createRuntimeType()));

        newChildDescriptors.add(createChildParameter(
                DeployPackage.Literals.SERVER_CONTAINER__SERVER_GROUP_TYPES,
                DeployFactory.eINSTANCE.createServerGroupType()));

        newChildDescriptors.add(createChildParameter(
                DeployPackage.Literals.SERVER_CONTAINER__SERVER_GROUPS,
                DeployFactory.eINSTANCE.createServerGroup()));
    }

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return DeployModelEditPlugin.INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public Collection<?> getChildren(Object object) {
        ArrayList<Object> ungrouppedChildren = new ArrayList<Object>();
        Collection<?> children = super.getChildren(object);
        for (Object child : children) {
            if (child instanceof Server) {
                Server server = ((Server) child);
                if (server.getServerGroup() == null) {
                    ungrouppedChildren.add(server);
                }
            } else {
                ungrouppedChildren.add(child);
            }
        }
        return ungrouppedChildren;

    }
}
