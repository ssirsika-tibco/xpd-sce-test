/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationWrapper;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.om.core.om.AssociableWithPrivileges;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.PrivilegeCategory;
import com.tibco.xpd.om.core.om.util.OMUtil;

/**
 * Transient group object grouping privileges and their categories.
 * 
 * <p>
 * <i>Created: 4 Jan 2008</i>
 * </p>
 * 
 * @author Gary Lewis
 */
public class PrivilegesTransientItemProvider extends TransientItemProvider {

    /**
     * The constructor.
     * 
     * @param adapterFactory
     *            the adapter factory for the model.
     * @param object
     *            parent for the group.
     */
    public PrivilegesTransientItemProvider(AdapterFactory adapterFactory,
            EObject object) {
        super(adapterFactory, object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.om.core.om.provider.TransientItemProvider#getChildren(java
     * .lang.Object)
     */
    @Override
    public Collection<?> getChildren(Object object) {
        List<Object> children = new ArrayList<Object>();
        children.addAll(super.getChildren(object));
        for (Iterator<Object> iterator = children.iterator(); iterator
                .hasNext();) {
            EObject child = (EObject) iterator.next();
            if (child instanceof Privilege) {
                if (((Privilege) child).getCategory() != null) {
                    iterator.remove();
                }
            }
        }
        return children;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildrenFeatures
     * (java.lang.Object)
     */
    @Override
    protected Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures
                    .add(OMPackage.Literals.ORG_MODEL__PRIVILEGE_CATEGORIES);
            childrenFeatures.add(OMPackage.Literals.ORG_MODEL__PRIVILEGES);
        }
        return childrenFeatures;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getText(java.lang.Object
     * )
     */
    @Override
    public String getText(Object object) {
        return getString("_UI_PrivilegesGroup_type"); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getImage(java.lang.
     * Object)
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator()
                .getImage("full/obj16/CapabilitiesGroup")); //$NON-NLS-1$ 
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse
     * .emf.common.notify.Notification)
     */
    @Override
    public void notifyChanged(Notification notification) {
        switch (notification.getFeatureID(OrgModel.class)) {
        case OMPackage.ORG_MODEL__PRIVILEGE_CATEGORIES:
        case OMPackage.ORG_MODEL__PRIVILEGES:
            fireNotifyChanged(new ViewerNotification(new NotificationWrapper(
                    this, notification), this, true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#collectNewChildDescriptors
     * (java.util.Collection, java.lang.Object)
     */
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        PrivilegeCategory privilegeCategory =
                OMFactory.eINSTANCE.createPrivilegeCategory();
        privilegeCategory
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_PrivilegeCategory_label"), //$NON-NLS-1$
                                getDisplayNamesArray(getAllCategories(object))));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__PRIVILEGE_CATEGORIES,
                        privilegeCategory));

        Privilege privilege = OMFactory.eINSTANCE.createPrivilege();
        privilege.setDisplayName(OMUtil
                .getDefaultName(getString("_UI_DefaultName_Privilege_label"), //$NON-NLS-1$
                        getDisplayNamesArray(getAllPrivileges(object))));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__PRIVILEGES,
                        privilege));
    }

    /** {@inheritDoc} */
    @Override
    protected Command createRemoveCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Collection<?> collection) {
        Command cmd =
                super.createRemoveCommand(domain, owner, feature, collection);
        CompoundCommand removeWithRefCommand = new CompoundCommand();
        /*
         * Group all associations to remove under their parents and create
         * single commands to delete all the siblings together
         */
        Map<AssociableWithPrivileges, EList<PrivilegeAssociation>> assocsToRemove =
                new HashMap<AssociableWithPrivileges, EList<PrivilegeAssociation>>();
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
                        EObject container = privAssoc.eContainer();

                        EList<PrivilegeAssociation> assocs =
                                assocsToRemove.get(container);
                        if (assocs == null) {
                            assocs = new BasicEList<PrivilegeAssociation>();
                            assocsToRemove
                                    .put((AssociableWithPrivileges) container,
                                            assocs);
                        }
                        assocs.add(privAssoc);
                    }
                }
            }
        }
        /*
         * If there are any privileges associations to remove then create
         * commands to remove them
         */
        if (!assocsToRemove.isEmpty()) {
            for (Entry<AssociableWithPrivileges, EList<PrivilegeAssociation>> entry : assocsToRemove
                    .entrySet()) {
                removeWithRefCommand
                        .append(RemoveCommand
                                .create(domain,
                                        entry.getKey(),
                                        OMPackage.eINSTANCE
                                                .getAssociableWithPrivileges_PrivilegeAssociations(),
                                        entry.getValue()));
            }
            removeWithRefCommand.append(cmd);
            cmd = removeWithRefCommand;
        }
        return cmd;
    }

    /**
     * Get all the Privileges in the model.
     * 
     * @param obj
     * @return
     */
    private Collection<Privilege> getAllPrivileges(Object obj) {
        if (obj instanceof TransientItemProvider) {
            return getAllPrivileges(((TransientItemProvider) obj)
                    .getParent(obj));
        } else if (obj instanceof OrgModel) {
            return ((OrgModel) obj).getPrivileges();
        }
        return new ArrayList<Privilege>(0);
    }

    /**
     * Get all the privilege categories in the model.
     * 
     * @param obj
     * @return
     */
    private Collection<PrivilegeCategory> getAllCategories(Object obj) {

        if (obj instanceof TransientItemProvider) {
            return getAllCategories(((TransientItemProvider) obj)
                    .getParent(obj));
        } else {
            List<PrivilegeCategory> categories =
                    new BasicEList<PrivilegeCategory>();
            Collection<PrivilegeCategory> subCategories = null;
            if (obj instanceof OrgModel) {
                subCategories = ((OrgModel) obj).getPrivilegeCategories();
            } else if (obj instanceof PrivilegeCategory) {
                categories.add((PrivilegeCategory) obj);
                subCategories = ((PrivilegeCategory) obj).getSubCategories();
            }

            if (subCategories != null) {
                for (PrivilegeCategory category : subCategories) {
                    categories.addAll(getAllCategories(category));
                }
            }
            return categories;
        }
    }
}
