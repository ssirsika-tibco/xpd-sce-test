/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.provider;

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
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandActionDelegate;
import org.eclipse.emf.edit.command.CommandParameter;
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

import com.tibco.xpd.om.core.om.AssociableWithPrivileges;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.PrivilegeCategory;
import com.tibco.xpd.om.core.om.QualifiedOrgElement;
import com.tibco.xpd.om.core.om.util.OMSwitch;
import com.tibco.xpd.om.core.om.util.OMUtil;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.xpd.om.core.om.PrivilegeCategory} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class PrivilegeCategoryItemProvider extends NamedElementItemProvider
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

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
        PrivilegeCategory category = (PrivilegeCategory) object;
        if (category.getMembers().size() > 0) {
            List<EObject> children = new ArrayList<EObject>();
            children.addAll((Collection<EObject>) super.getChildren(object));
            children.addAll(category.getMembers());
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
        Object orgModel = super.getParent(object);
        if (orgModel instanceof OrgModel) {
            OrgModelItemProvider orgModelItemProvider =
                    (OrgModelItemProvider) adapterFactory.adapt(orgModel,
                            IEditingDomainItemProvider.class);
            return orgModelItemProvider != null ? orgModelItemProvider
                    .getTransientParent(orgModel,
                            OMPackage.Literals.ORG_MODEL__PRIVILEGES) : null;
        }
        return super.getParent(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#hasChildren(java.lang
     * .Object)
     */
    @Override
    public boolean hasChildren(Object object) {
        return super.hasChildren(object)
                || !((PrivilegeCategory) object).getMembers().isEmpty();
    }

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public PrivilegeCategoryItemProvider(AdapterFactory adapterFactory) {
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
            // addMembersPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Members feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addMembersPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_PrivilegeCategory_members_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_PrivilegeCategory_members_feature", "_UI_PrivilegeCategory_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.PRIVILEGE_CATEGORY__MEMBERS,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Members feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addPrivilegesPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(new ItemPropertyDescriptor(
                        ((ComposeableAdapterFactory) adapterFactory)
                                .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_PrivilegeCategory_members_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_PrivilegeCategory_members_feature", "_UI_PrivilegeCategory_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        OMPackage.Literals.PRIVILEGE_CATEGORY__MEMBERS, true,
                        false, true, null, null, null) {
                    /*
                     * (non-Javadoc)
                     * 
                     * @see
                     * org.eclipse.emf.edit.provider.ItemPropertyDescriptor#
                     * getChoiceOfValues(java.lang.Object)
                     */
                    @Override
                    public Collection<?> getChoiceOfValues(Object object) {
                        final List<EObject> list = new ArrayList<EObject>();
                        EObject container =
                                getEParent(((PrivilegeCategory) object),
                                        OrgModel.class);
                        for (TreeIterator<Object> allContents =
                                EcoreUtil.getAllContents(Collections
                                        .singletonList(container)); allContents
                                .hasNext();) {
                            (new OMSwitch<Object>() {
                                /*
                                 * (non-Javadoc)
                                 * 
                                 * @seecom.tibco.xpd.om.core.om.util.OMSwitch#
                                 * casePrivilege
                                 * (com.tibco.xpd.om.core.om.Privilege)
                                 */
                                @Override
                                public Object caseQualifiedOrgElement(
                                        QualifiedOrgElement object) {
                                    list.add(object);
                                    return object;
                                }
                            }).doSwitch((EObject) allContents.next());

                        }
                        return list;
                    }
                });
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
                    .add(OMPackage.Literals.PRIVILEGE_CATEGORY__SUB_CATEGORIES);
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
     * This returns PrivilegeCategory.gif. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/PrivilegeCategory")); //$NON-NLS-1$
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
        return label == null || label.length() == 0 ? getString("_UI_PrivilegeCategory_type") : //$NON-NLS-1$
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

        switch (notification.getFeatureID(PrivilegeCategory.class)) {
        case OMPackage.PRIVILEGE_CATEGORY__SUB_CATEGORIES:
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

        OrgModel model =
                object instanceof EObject ? OMUtil.getModel((EObject) object)
                        : null;

        PrivilegeCategory privilegeCategory =
                OMFactory.eINSTANCE.createPrivilegeCategory();
        privilegeCategory
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_PrivilegeCategory_label"), //$NON-NLS-1$
                                getDisplayNamesArray(model != null ? getAllCategories(model)
                                        : getChildren(object))));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.PRIVILEGE_CATEGORY__SUB_CATEGORIES,
                        privilegeCategory));

        Privilege privilege = OMFactory.eINSTANCE.createPrivilege();
        privilege
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_Privilege_label"), //$NON-NLS-1$
                                getDisplayNamesArray(model != null ? getAllPrivileges(model)
                                        : getChildren(object))));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__PRIVILEGES,
                        privilege));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#createCreateChildCommand
     * (org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature,
     * java.lang.Object, int, java.util.Collection)
     */
    @Override
    protected Command createCreateChildCommand(EditingDomain domain,
            EObject owner, EStructuralFeature feature, Object value, int index,
            Collection<?> collection) {
        if (feature == OMPackage.Literals.ORG_MODEL__PRIVILEGES) {
            CompoundCommand cmd = new CompoundCommand();
            OrgModel orgModel = (OrgModel) getEParent(owner, OrgModel.class);
            Command createCreateChildCommand =
                    super.createCreateChildCommand(domain,
                            orgModel,
                            feature,
                            value,
                            index,
                            collection);
            cmd.append(createCreateChildCommand);
            Command setParentCommand =
                    new SetCommand(domain, (EObject) value,
                            OMPackage.Literals.PRIVILEGE__CATEGORY, owner) {
                        /*
                         * This command will assume that it will always be
                         * possible to add privilege to (non-Javadoc)
                         * 
                         * @see
                         * org.eclipse.emf.edit.command.AbstractOverrideableCommand
                         * # doCanExecute()
                         */
                        @Override
                        public boolean doCanExecute() {
                            return true;
                        }
                    };
            cmd.append(setParentCommand);
            cmd.setLabel(getString("_UI_Privilege_type")); //$NON-NLS-1$
            cmd.setDescription(createCreateChildCommand.getDescription());
            if (createCreateChildCommand instanceof CommandActionDelegate) {
                Object image =
                        ((CommandActionDelegate) createCreateChildCommand)
                                .getImage();
                return new ActionCommandWrapper(cmd, image);
            }
            return cmd;

        }
        return super.createCreateChildCommand(domain,
                owner,
                feature,
                value,
                index,
                collection);
    }

    /**
     * This method factors a {@link org.eclipse.emf.edit.command.RemoveCommand}
     * for a collection of objects into one or more primitive remove commands,
     * i.e., one per unique feature.
     */
    @Override
    protected Command factorRemoveCommand(EditingDomain domain,
            CommandParameter commandParameter) {
        if (commandParameter.getCollection() == null
                || commandParameter.getCollection().isEmpty()) {
            return UnexecutableCommand.INSTANCE;
        }

        final EObject eObject = commandParameter.getEOwner();
        final List<Object> list =
                new ArrayList<Object>(commandParameter.getCollection());

        CompoundCommand removeCommand =
                new CompoundCommand(CompoundCommand.MERGE_COMMAND_ALL);

        // Factor commands for removing privileges.
        EObject featureOwner = getEParent(eObject, OrgModel.class);
        factorRemoveCommandsForFeature(domain,
                list,
                removeCommand,
                OMPackage.Literals.ORG_MODEL__PRIVILEGES,
                featureOwner);

        // Iterator over all the child references to factor each child to the
        // right reference.
        //
        for (EStructuralFeature feature : getChildrenFeatures(eObject)) {
            // If it is a list type value...
            //
            if (feature.isMany()) {
                List<?> value = (List<?>) getFeatureValue(eObject, feature);

                // These will be the children belonging to this feature.
                //
                Collection<Object> childrenOfThisFeature =
                        new ArrayList<Object>();
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
                    removeCommand.append(createRemoveCommand(domain,
                            eObject,
                            feature,
                            childrenOfThisFeature));
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
                        Command setCommand =
                                createSetCommand(domain, eObject, feature, null);
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

    private void factorRemoveCommandsForFeature(EditingDomain domain,
            final List<Object> list, CompoundCommand removeCommand,
            EStructuralFeature feature, EObject featureOwner) {
        if (featureOwner != null) {
            List<?> value = (List<?>) getFeatureValue(featureOwner, feature);

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
                removeCommand.append(createRemoveCommand(domain,
                        featureOwner,
                        feature,
                        childrenOfThisFeature));
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    protected Command createRemoveCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Collection<?> collection) {
        CompoundCommand removeWithRefCommand = new CompoundCommand();
        for (Object object : collection) {
            if (object instanceof Privilege) {
                Privilege p = ((Privilege) object);
                ECrossReferenceAdapter crossReferenceAdapter =
                        ECrossReferenceAdapter.getCrossReferenceAdapter(p);
                Collection<Setting> inverseReferences =
                        crossReferenceAdapter.getInverseReferences(p, true);
                for (Setting ref : inverseReferences) {
                    if (ref.getEObject() instanceof PrivilegeAssociation
                            && ref.getEObject().eContainer() instanceof AssociableWithPrivileges) {
                        PrivilegeAssociation privAssoc =
                                ((PrivilegeAssociation) ref.getEObject());
                        removeWithRefCommand
                                .append(RemoveCommand.create(domain,
                                        privAssoc.eContainer(),
                                        OMPackage.eINSTANCE
                                                .getAssociableWithPrivileges_PrivilegeAssociations(),
                                        Collections.singletonList(privAssoc)));

                    }
                }
            }
        }
        if (!removeWithRefCommand.isEmpty()) {
            removeWithRefCommand.append(super.createRemoveCommand(domain,
                    owner,
                    feature,
                    collection));
            return removeWithRefCommand;
        }
        return super.createRemoveCommand(domain, owner, feature, collection);
    }

    private static EObject getEParent(EObject child,
            Class<? extends EObject> parentClass) {
        EObject parent = child;
        do {
            parent = parent.eContainer();
            if (parent == null)
                break;
        } while (!parentClass.isInstance(parent));
        return parent;
    }

    /**
     * Get all {@link Privilege}s from the model.
     * 
     * @param model
     * @return
     */
    private Collection<Privilege> getAllPrivileges(OrgModel model) {
        return model != null ? model.getPrivileges()
                : new ArrayList<Privilege>(0);
    }

    /**
     * Get all the Privilege categories from the given object.
     * 
     * @param eo
     *            {@link OrgModel} or {@link PrivilegeCategory}.
     * @return
     */
    private Collection<PrivilegeCategory> getAllCategories(EObject eo) {
        Collection<PrivilegeCategory> categories =
                new ArrayList<PrivilegeCategory>();
        Collection<PrivilegeCategory> subCategories = null;
        if (eo instanceof OrgModel) {
            subCategories = ((OrgModel) eo).getPrivilegeCategories();
        } else if (eo instanceof PrivilegeCategory) {
            categories.add((PrivilegeCategory) eo);
            subCategories = ((PrivilegeCategory) eo).getSubCategories();
        }

        if (subCategories != null) {
            for (PrivilegeCategory category : subCategories) {
                categories.addAll(getAllCategories(category));
            }
        }

        return categories;
    }

}
