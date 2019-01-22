/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general.groups;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeCategory;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.core.om.provider.PrivilegesTransientItemProvider;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.LabelColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.NameColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.PurposeColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.QualifierColumn;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * {@link Privilege}s group section containing the table to add/remove
 * <code>Privilege</code>s.
 * 
 * @author njpatel
 * 
 */
public class PrivilegesGroupSection extends AbstractTreeTableSection implements
        IFilter {

    private ITreeContentProvider contentProvider;

    public PrivilegesGroupSection() {
        super(Messages.PrivilegesGroupSection_title);
    }

    @Override
    protected Set<EStructuralFeature> getDeletableFeatures() {
        Set<EStructuralFeature> features = new HashSet<EStructuralFeature>();
        features.add(OMPackage.eINSTANCE.getOrgModel_Privileges());
        features.add(OMPackage.eINSTANCE.getOrgModel_PrivilegeCategories());
        features.add(OMPackage.eINSTANCE.getPrivilegeCategory_SubCategories());
        return features;
    }

    @Override
    protected float[] addColumns(ColumnViewer viewer) {
        new LabelColumn(getEditingDomain(), viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(getEditingDomain(), viewer);
        }
        new PurposeColumn(getEditingDomain(), viewer);
        new QualifierColumn(getEditingDomain(), viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            return new float[] { 0.3f, 0.3f, 0.3f, 0.2f };
        }
        return new float[] { 0.3f, 0.3f, 0.2f };
    }

    @Override
    protected ITreeContentProvider getContentProvider() {
        if (contentProvider == null) {
            contentProvider = new ITreeContentProvider() {

                @Override
                public Object[] getChildren(Object parentElement) {
                    if (parentElement instanceof OrgModel) {
                        IEditingDomainItemProvider provider =
                                getItemProvider((EObject) parentElement);
                        if (provider != null) {
                            Collection<?> children =
                                    provider.getChildren(parentElement);
                            if (children != null) {
                                for (Object child : children) {
                                    if (child instanceof PrivilegesTransientItemProvider) {
                                        Collection<?> children2 =
                                                ((PrivilegesTransientItemProvider) child)
                                                        .getChildren(parentElement);
                                        if (children2 != null) {
                                            return children2.toArray();
                                        }
                                    }
                                }
                            }
                        }
                    } else if (parentElement instanceof PrivilegeCategory) {
                        IEditingDomainItemProvider provider =
                                getItemProvider((EObject) parentElement);
                        if (provider != null) {
                            Collection<?> children =
                                    provider.getChildren(parentElement);
                            if (children != null) {
                                return children.toArray();
                            }
                        }
                    }
                    return new Object[0];
                }

                @Override
                public Object getParent(Object element) {
                    if (element instanceof Privilege) {
                        PrivilegeCategory category =
                                ((Privilege) element).getCategory();
                        return category != null ? category
                                : ((EObject) element).eContainer();
                    } else if (element instanceof PrivilegeCategory) {
                        return ((PrivilegeCategory) element).eContainer();
                    }
                    return null;
                }

                @Override
                public boolean hasChildren(Object element) {
                    if (element instanceof OrgModel) {
                        return getElements(element).length > 0;
                    } else if (element instanceof PrivilegeCategory) {
                        return getChildren(element).length > 0;
                    }
                    return false;
                }

                @Override
                public Object[] getElements(Object inputElement) {
                    return getChildren(inputElement);
                }

                @Override
                public void dispose() {
                    // Nothing to do
                }

                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                    // Nothing to do
                }

            };
        }
        return contentProvider;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {

        /*
         * Default implementation won't work for categories as they don't 'own'
         * the children so we should refresh for any privilege / group change
         */
        for (Notification notification : notifications) {
            if (!notification.isTouch()) {
                Object notifier = notification.getNotifier();
                if (notifier instanceof Privilege
                        || notifier instanceof PrivilegeCategory) {
                    return true;
                }
            }
        }

        return super.shouldRefresh(notifications);
    }

    @Override
    public boolean select(Object toTest) {
        toTest = resollveInput(toTest);
        return toTest instanceof OrgModel;
    }

    @Override
    protected ViewerAddAction[] createAddActions(ColumnViewer viewer) {

        return new ViewerAddAction[] { new NewPrivilegeAction(viewer),
                new NewCategoryAction(viewer) };
    }

    /**
     * Get the capability transient item provider.
     * 
     * @param model
     * @return
     */
    private PrivilegesTransientItemProvider getTransientItemProvider(
            OrgModel model) {
        IEditingDomainItemProvider provider = getItemProvider(model);
        if (provider != null) {
            Collection<?> children = provider.getChildren(model);
            if (children != null) {
                for (Object object : children) {
                    if (object instanceof PrivilegesTransientItemProvider) {
                        return (PrivilegesTransientItemProvider) object;
                    }
                }
            }
        }

        return null;
    }

    private ImageDescriptor getAddImage(String path) {
        Object imgObj = OMModelImages.getImageObject(path);
        ImageDescriptor imgDesc = null;
        if (imgObj != null) {
            imgDesc =
                    ExtendedImageRegistry.getInstance()
                            .getImageDescriptor(imgObj);
        }

        // In case we failed to get the privilege icon then get default add
        // icon
        if (imgDesc == null) {
            imgDesc =
                    XpdResourcesUIActivator.getDefault().getImageRegistry()
                            .getDescriptor(XpdResourcesUIConstants.IMAGE_ADD);
        }
        return imgDesc;
    }

    /**
     * Action to add a new Capability to the OrgModel or Capability Category.
     * 
     */
    private class NewPrivilegeAction extends ViewerAddAction {

        EObject selectedItem;

        public NewPrivilegeAction(StructuredViewer viewer) {
            this(viewer, OMModelImages.IMAGE_PRIVILEGE,
                    Messages.PrivilegesGroupSection_addPrivilege_action,
                    Messages.PrivilegesGroupSection_addPrivilege_action_tooltip);
        }

        public NewPrivilegeAction(StructuredViewer viewer, String imgPath,
                String name, String tooltip) {
            super(viewer, name, tooltip);
            ImageDescriptor imgDesc = getAddImage(imgPath);
            if (imgDesc != null) {
                setImageDescriptor(imgDesc);
            }
        }

        @Override
        public void selectionChanged(IStructuredSelection selection) {
            selectedItem =
                    (EObject) (selection.getFirstElement() instanceof EObject ? selection
                            .getFirstElement() : null);
        }

        @Override
        public void run() {
            EObject parent = null;
            CommandParameter descriptor = null;

            EObject selection =
                    selectedItem != null ? selectedItem : getInput();

            // If this is a capability without a parent category then use the
            // org model as parent
            if (selection instanceof Privilege) {
                PrivilegeCategory category =
                        ((Privilege) selection).getCategory();
                if (category == null) {
                    // Set the selected item to be the input
                    selection = getInput();
                } else {
                    selection = category;
                }
            }

            if (selection instanceof OrgModel) {
                PrivilegesTransientItemProvider transientItemProvider =
                        getTransientItemProvider((OrgModel) selection);
                if (transientItemProvider != null) {
                    parent = selection;
                    descriptor =
                            getPrivilegeChild(transientItemProvider
                                    .getNewChildDescriptors(selection,
                                            getEditingDomain(),
                                            null));
                }
            } else if (selection instanceof PrivilegeCategory) {
                parent = selection;
                IEditingDomainItemProvider provider =
                        getItemProvider(selection);
                if (provider != null) {
                    descriptor =
                            getPrivilegeChild(provider
                                    .getNewChildDescriptors(selection,
                                            getEditingDomain(),
                                            null));
                }
            }

            if (parent != null && descriptor != null) {
                getEditingDomain().getCommandStack()
                        .execute(CreateChildCommand.create(getEditingDomain(),
                                parent,
                                descriptor,
                                Collections.singleton(parent)));

                if (descriptor.getValue() != null) {
                    getViewer().setSelection(new StructuredSelection(
                            descriptor.getValue()),
                            true);
                }
            }
        }

        /**
         * Get the descriptor for the child to add.
         * 
         * @param descriptors
         * @return
         */
        protected CommandParameter getPrivilegeChild(Collection<?> descriptors) {
            for (Object descriptor : descriptors) {
                if (descriptor instanceof CommandParameter
                        && (((CommandParameter) descriptor).getFeature() == OMPackage.eINSTANCE
                                .getOrgModel_Privileges() || ((CommandParameter) descriptor)
                                .getFeature() == OMPackage.eINSTANCE
                                .getPrivilegeCategory_Members())) {
                    return (CommandParameter) descriptor;
                }
            }
            return null;
        }
    }

    /**
     * Action to add a new Capability Category to an OrgModel or Capability
     * Category.
     * 
     */
    private class NewCategoryAction extends NewPrivilegeAction {

        public NewCategoryAction(StructuredViewer viewer) {
            super(
                    viewer,
                    OMModelImages.IMAGE_PRIVILEGE_CATEGORY,
                    Messages.PrivilegesGroupSection_addPrivilegeCategory_action,
                    Messages.PrivilegesGroupSection_addPrivilegeCategory_action_tooltip);
        }

        @Override
        protected CommandParameter getPrivilegeChild(Collection<?> descriptors) {
            for (Object descriptor : descriptors) {
                if (descriptor instanceof CommandParameter
                        && (((CommandParameter) descriptor).getFeature() == OMPackage.eINSTANCE
                                .getOrgModel_PrivilegeCategories() || ((CommandParameter) descriptor)
                                .getFeature() == OMPackage.eINSTANCE
                                .getPrivilegeCategory_SubCategories())) {
                    return (CommandParameter) descriptor;
                }
            }
            return null;
        }

    }
}
