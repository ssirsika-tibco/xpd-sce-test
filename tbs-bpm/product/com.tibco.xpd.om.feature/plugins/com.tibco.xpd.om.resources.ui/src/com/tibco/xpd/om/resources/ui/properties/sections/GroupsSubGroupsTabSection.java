/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.provider.GroupsTransientItemProvider;
import com.tibco.xpd.om.core.om.provider.OrgModelItemProvider;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.AllocationMethodColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.LabelColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.NameColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.PurposeColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.groups.AbstractGroupSection;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.BaseTreeControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Properties section for the Groups tab.
 * 
 * @author rgreen
 * 
 */
public class GroupsSubGroupsTabSection extends AbstractGroupSection implements
        IFilter {

    private TreeViewer viewer;

    private EObject currentInput;

    /**
     * Properties section for the System Actions tab.
     */
    public GroupsSubGroupsTabSection() {
        super(Messages.GroupsSubGroupsTabSection_title);
        // actionManager = SystemActionManager.getInstance();
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof EditPart) {
            return (EObject) ((EditPart) object).getAdapter(EObject.class);
        }

        return super.resollveInput(object);
    }

    @Override
    public void setInput(Collection<?> items) {
        if (!items.isEmpty()) {
            Object item = items.iterator().next();

            if (item instanceof EditPart) {
                items = Collections.singleton(resollveInput(item));
            }
        }
        super.setInput(items);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = resollveInput(toTest);
        return eo instanceof OrgModel;
    }

    @Override
    protected Control createClientArea(Composite parent, XpdToolkit toolkit) {
        GroupsTable table = new GroupsTable(parent, toolkit);
        table.setBackground(parent.getBackground());
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        viewer = table.getTreeViewer();
        viewer.getTree().setHeaderVisible(true);
        viewer.getTree().setLinesVisible(true);

        return table;
    }

    @Override
    protected void addColumns(TableViewer viewer) {
        // Not used as we are creating our own tree view table
    }

    @Override
    protected boolean isRequiredChildDescriptor(CommandParameter descriptor) {
        return descriptor != null && descriptor.getValue() instanceof Group;
    }

    @Override
    protected void doRefresh() {
        if (viewer != null && !viewer.getControl().isDisposed()) {
            EObject input = getInput();
            if (input instanceof OrgModel) {
                // Update input only if it has changed
                if (input != currentInput) {
                    currentInput = input;
                    viewer.setInput(input);
                } else {
                    viewer.refresh();
                }
            }

        }
    }

    /**
     * The groups tree view table.
     * 
     * @author njpatel
     */
    private class GroupsTable extends BaseTreeControl {

        public GroupsTable(Composite parent, XpdToolkit toolkit) {
            super(parent, toolkit);
        }

        @Override
        protected void addColumns(ColumnViewer viewer) {
            new LabelColumn(getEditingDomain(), viewer);
            if (CapabilityUtil.isDeveloperActivityEnabled()) {
                new NameColumn(getEditingDomain(), viewer);
            }
            new PurposeColumn(getEditingDomain(), viewer);
            new AllocationMethodColumn(getEditingDomain(), viewer);
        }

        @Override
        protected IContentProvider getViewerContentProvider() {
            return new GroupContentProvider();
        }

        @Override
        protected Set<EStructuralFeature> getMovableFeatures() {
            Set<EStructuralFeature> features = super.getMovableFeatures();
            features.add(OMPackage.Literals.ORG_MODEL__GROUPS);
            features.add(OMPackage.Literals.GROUP__SUB_GROUPS);
            return features;
        }

        @Override
        protected Set<EStructuralFeature> getDeletableFeatures() {
            Set<EStructuralFeature> features = super.getDeletableFeatures();
            features.add(OMPackage.Literals.ORG_MODEL__GROUPS);
            features.add(OMPackage.Literals.GROUP__SUB_GROUPS);
            return features;
        }

        @Override
        protected ViewerAddAction createAddAction(final ColumnViewer viewer) {
            return new ViewerAddAction(viewer) {
                @Override
                public void run() {
                    IStructuredSelection sel =
                            (IStructuredSelection) viewer.getSelection();
                    EObject parent = null;

                    if (sel.isEmpty()) {
                        parent = getInput();
                    } else {
                        parent = (EObject) sel.getFirstElement();
                    }

                    if (parent != null) {
                        IEditingDomainItemProvider provider =
                                (IEditingDomainItemProvider) XpdResourcesPlugin
                                        .getDefault()
                                        .getAdapterFactory()
                                        .adapt(parent,
                                                IEditingDomainItemProvider.class);
                        if (provider instanceof ItemProviderAdapter) {
                            if (provider instanceof OrgModelItemProvider) {
                                provider =
                                        (IEditingDomainItemProvider) getGroupsTransientItemProvider((OrgModelItemProvider) provider,
                                                parent);
                            }
                            CommandParameter descriptor = null;
                            Collection<?> descriptors =
                                    provider.getNewChildDescriptors(parent,
                                            getEditingDomain(),
                                            null);
                            for (Object object : descriptors) {
                                if (object instanceof CommandParameter
                                        && isRequiredChildDescriptor((CommandParameter) object)) {
                                    descriptor = (CommandParameter) object;
                                    break;
                                }
                            }
                            if (descriptor != null) {
                                Command cmd =
                                        CreateChildCommand
                                                .create(getEditingDomain(),
                                                        parent,
                                                        descriptor,
                                                        Collections
                                                                .singleton(parent));

                                if (cmd != null) {
                                    getEditingDomain().getCommandStack()
                                            .execute(cmd);
                                    if (descriptor.getValue() != null) {
                                        getViewer()
                                                .setSelection(new StructuredSelection(
                                                        parent),
                                                        true);
                                        if (!((TreeViewer) getViewer())
                                                .getExpandedState(parent)) {
                                            ((TreeViewer) getViewer())
                                                    .setExpandedState(parent,
                                                            true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                /**
                 * If add a model to the top-level (orgModel) then we need to
                 * get the groups transient item provider to get the child
                 * descriptor from.
                 * 
                 * @param provider
                 * @param model
                 * @return
                 */
                private ItemProviderAdapter getGroupsTransientItemProvider(
                        OrgModelItemProvider provider, EObject model) {
                    for (Object child : provider.getChildren(model)) {
                        if (child instanceof GroupsTransientItemProvider) {
                            return (ItemProviderAdapter) child;
                        }
                    }
                    return null;
                }
            };
        }
    }

    /**
     * Content provider for the system action tree view table.
     * 
     * @author njpatel
     */
    private class GroupContentProvider implements ITreeContentProvider {

        @Override
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof Group) {
                return ((Group) parentElement).getSubGroups().toArray();

            }
            return new Object[0];
        }

        @Override
        public Object getParent(Object element) {
            if (element instanceof Group) {
                return ((Group) element).eContainer();
            }
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            return getChildren(element).length > 0;
        }

        @Override
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof OrgModel) {
                return ((OrgModel) inputElement).getGroups().toArray();
            }

            return new Object[0];
        }

        @Override
        public void dispose() {
            // Nothing to dispose
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // Nothing to do
        }
    }

}
