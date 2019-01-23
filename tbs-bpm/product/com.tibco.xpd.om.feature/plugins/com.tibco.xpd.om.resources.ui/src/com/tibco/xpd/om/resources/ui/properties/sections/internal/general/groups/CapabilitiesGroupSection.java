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

import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityCategory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.provider.CapabilitiesTransientItemProvider;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
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
 * {@link Capability Capabilities} group section containing the table to
 * add/remove capabilities.
 * 
 * @author njpatel
 * 
 */
public class CapabilitiesGroupSection extends AbstractTreeTableSection
        implements IFilter {

    private ITreeContentProvider contentProvider;

    public CapabilitiesGroupSection() {
        super(Messages.CapabilitiesGroupSection_title);
    }

    @Override
    protected Set<EStructuralFeature> getDeletableFeatures() {
        Set<EStructuralFeature> features = new HashSet<EStructuralFeature>();
        features.add(OMPackage.eINSTANCE.getOrgModel_Capabilities());
        features.add(OMPackage.eINSTANCE.getOrgModel_CapabilityCategories());
        features.add(OMPackage.eINSTANCE.getCapabilityCategory_SubCategories());
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
                                    if (child instanceof CapabilitiesTransientItemProvider) {
                                        Collection<?> children2 =
                                                ((CapabilitiesTransientItemProvider) child)
                                                        .getChildren(parentElement);
                                        if (children2 != null) {
                                            return children2.toArray();
                                        }
                                    }
                                }
                            }
                        }
                    } else if (parentElement instanceof CapabilityCategory) {
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
                    if (element instanceof Capability) {
                        CapabilityCategory category =
                                ((Capability) element).getCategory();
                        return category != null ? category
                                : ((EObject) element).eContainer();
                    } else if (element instanceof CapabilityCategory) {
                        return ((CapabilityCategory) element).eContainer();
                    }
                    return null;
                }

                @Override
                public boolean hasChildren(Object element) {
                    if (element instanceof OrgModel) {
                        return getElements(element).length > 0;
                    } else if (element instanceof CapabilityCategory) {
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
    public boolean select(Object toTest) {
        toTest = resollveInput(toTest);
        return toTest instanceof OrgModel;
    }

    @Override
    protected ViewerAddAction[] createAddActions(ColumnViewer viewer) {

        return new ViewerAddAction[] { new NewCapabilityAction(viewer),
                new NewCategoryAction(viewer) };
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {

        /*
         * Default implementation won't work for categories as they don't 'own'
         * the children so we should refresh for any capability / group change
         */
        for (Notification notification : notifications) {
            if (!notification.isTouch()) {
                Object notifier = notification.getNotifier();
                if (notifier instanceof Capability
                        || notifier instanceof CapabilityCategory) {
                    return true;
                }
            }
        }

        return super.shouldRefresh(notifications);
    }

    /**
     * Get the capability transient item provider.
     * 
     * @param model
     * @return
     */
    private CapabilitiesTransientItemProvider getTransientItemProvider(
            OrgModel model) {
        IEditingDomainItemProvider provider = getItemProvider(model);
        if (provider != null) {
            Collection<?> children = provider.getChildren(model);
            if (children != null) {
                for (Object object : children) {
                    if (object instanceof CapabilitiesTransientItemProvider) {
                        return (CapabilitiesTransientItemProvider) object;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Get the image with the given path from the OM Model image library.
     * 
     * @param path
     * @return
     */
    private ImageDescriptor getImage(String path) {
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
    private class NewCapabilityAction extends ViewerAddAction {

        EObject selectedItem;

        public NewCapabilityAction(StructuredViewer viewer) {
            this(
                    viewer,
                    OMModelImages.IMAGE_CAPABILITY,
                    Messages.CapabilitiesGroupSection_addCapability_action,
                    Messages.CapabilitiesGroupSection_addCapability_action_tooltip);
        }

        public NewCapabilityAction(StructuredViewer viewer, String imgPath,
                String name, String tooltip) {
            super(viewer, name, tooltip);
            ImageDescriptor imgDesc = getImage(imgPath);
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
            if (selection instanceof Capability) {
                CapabilityCategory category =
                        ((Capability) selection).getCategory();
                if (category == null) {
                    // Set the selected item to be the input
                    selection = getInput();
                } else {
                    selection = category;
                }
            }

            if (selection instanceof OrgModel) {
                CapabilitiesTransientItemProvider transientItemProvider =
                        getTransientItemProvider((OrgModel) selection);
                if (transientItemProvider != null) {
                    parent = selection;
                    descriptor =
                            getCapabilityChild(transientItemProvider
                                    .getNewChildDescriptors(selection,
                                            getEditingDomain(),
                                            null));
                }
            } else if (selection instanceof CapabilityCategory) {
                parent = selection;
                IEditingDomainItemProvider provider =
                        getItemProvider(selection);
                if (provider != null) {
                    descriptor =
                            getCapabilityChild(provider
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
        protected CommandParameter getCapabilityChild(Collection<?> descriptors) {
            for (Object descriptor : descriptors) {
                if (descriptor instanceof CommandParameter
                        && (((CommandParameter) descriptor).getFeature() == OMPackage.eINSTANCE
                                .getOrgModel_Capabilities() || ((CommandParameter) descriptor)
                                .getFeature() == OMPackage.eINSTANCE
                                .getCapabilityCategory_Members())) {
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
    private class NewCategoryAction extends NewCapabilityAction {

        public NewCategoryAction(StructuredViewer viewer) {
            super(
                    viewer,
                    OMModelImages.IMAGE_CAPABILITY_CATEGORY,
                    Messages.CapabilitiesGroupSection_addCapabilityCategory_action,
                    Messages.CapabilitiesGroupSection_addCapabilityCategory_action_tooltip);
        }

        @Override
        protected CommandParameter getCapabilityChild(Collection<?> descriptors) {
            for (Object descriptor : descriptors) {
                if (descriptor instanceof CommandParameter
                        && (((CommandParameter) descriptor).getFeature() == OMPackage.eINSTANCE
                                .getOrgModel_CapabilityCategories() || ((CommandParameter) descriptor)
                                .getFeature() == OMPackage.eINSTANCE
                                .getCapabilityCategory_SubCategories())) {
                    return (CommandParameter) descriptor;
                }
            }
            return null;
        }

    }
}
