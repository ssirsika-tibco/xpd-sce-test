/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.components;

import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.BaseTreeControl;
import com.tibco.xpd.resources.ui.components.ViewerAction;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TreeViewerDeleteEMFAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.ui.RsdImage;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.actions.ShowPropertiesViewAction;

/**
 * Main RSD viewer used by RSD editor.
 * 
 * @author jarciuch
 * @since 10 Feb 2015
 */
public class RsdMainControl extends BaseTreeControl {

    private AddResourceAction addResourceAction;

    private AddMethodAction addMethodAction;

    private ShowPropertiesViewAction showPropertiesViewAction;

    /**
     * @param parent
     * @param toolkit
     */
    public RsdMainControl(Composite parent, XpdToolkit toolkit) {
        super(parent, toolkit);
    }

    /** {@inheritDoc} */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        if (movableFeatures == null) {
            movableFeatures = super.getMovableFeatures();
            movableFeatures.add(RsdPackage.Literals.SERVICE__RESOURCES);
            movableFeatures.add(RsdPackage.Literals.RESOURCE__METHODS);
        }
        return movableFeatures;
    }

    /** {@inheritDoc} */
    @Override
    protected Set<EStructuralFeature> getDeletableFeatures() {
        if (deletableFeatures == null) {
            deletableFeatures = super.getDeletableFeatures();
            deletableFeatures.add(RsdPackage.Literals.SERVICE__RESOURCES);
            deletableFeatures.add(RsdPackage.Literals.RESOURCE__METHODS);
        }
        return deletableFeatures;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createContents(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.resources.ui.components.XpdToolkit, java.lang.Object)
     * 
     * @param parent
     * @param toolkit
     * @param viewerInput
     */
    @Override
    protected void createContents(Composite parent, XpdToolkit toolkit,
            Object viewerInput) {
        super.createContents(parent, toolkit, viewerInput);
        getViewer().addDoubleClickListener(new IDoubleClickListener() {
            @Override
            public void doubleClick(DoubleClickEvent event) {
                showPropertiesViewAction.run();
            }
        });
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getViewerContentProvider()
     */
    @Override
    protected IContentProvider getViewerContentProvider() {
        if (viewerContentProvider == null) {
            TransactionalAdapterFactoryContentProvider delegate =
                    new TransactionalAdapterFactoryContentProvider(
                            XpdResourcesPlugin.getDefault().getEditingDomain(),
                            XpdResourcesPlugin.getDefault().getAdapterFactory());
            viewerContentProvider = new RsdContentProvider(delegate);
        }
        return viewerContentProvider;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getViewerLabelProvider()
     */
    @Override
    protected ILabelProvider getViewerLabelProvider() {
        return super.getViewerLabelProvider();
    }

    /**
     * @see org.eclipse.swt.widgets.Widget#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (viewerContentProvider != null) {
            viewerContentProvider.dispose();
        }
        super.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createActions(ColumnViewer viewer) {
        super.createActions(viewer);
        addResourceAction =
                new AddResourceAction(getViewer(), getEditingDomain());
        addMethodAction = new AddMethodAction(getViewer(), getEditingDomain());
        showPropertiesViewAction = new ShowPropertiesViewAction();
        viewer.addSelectionChangedListener(addResourceAction);
        viewer.addSelectionChangedListener(addMethodAction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillViewerButtonsBar(IContributionManager manager,
            ColumnViewer viever) {
        super.fillViewerButtonsBar(manager, viever);
        manager.appendToGroup(ADD_ACTIONS_END_MARKER, addResourceAction);
        manager.appendToGroup(ADD_ACTIONS_END_MARKER, addMethodAction);
        manager.appendToGroup(ADDITIONS_MARKER, showPropertiesViewAction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void fillViewerContextMenu(IMenuManager manager,
            ColumnViewer columnViewer) {
        super.fillViewerContextMenu(manager, columnViewer);
        manager.add(addResourceAction);
        manager.add(addMethodAction);
        manager.add(showPropertiesViewAction);
    }

    /**
     * Adds a new Resource to the Service.
     * 
     */
    public static class AddResourceAction extends ViewerAction {

        private ShowPropertiesViewAction showProperties =
                new ShowPropertiesViewAction();

        private EditingDomain ed;

        public AddResourceAction(StructuredViewer viewer, EditingDomain ed) {
            super(viewer, Messages.RsdMainControl_AddResource_label, RsdImage
                    .getImageDescriptor(RsdImage.CREATE_RESOURCE));
            setToolTipText(Messages.RsdMainControl_AddResource_tooltip);
            this.ed = ed;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void selectionChanged(IStructuredSelection selection) {
            if (selection.size() == 1) {
                Object elem = selection.getFirstElement();
                if (elem instanceof EObject
                        && RsdEditingUtil.getParentOfType((EObject) elem,
                                Service.class) != null) {
                    setEnabled(true);
                    return;
                }
            }
            setEnabled(false);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            IStructuredSelection selection =
                    (IStructuredSelection) getViewer().getSelection();
            if (selection.size() == 1) {
                Service service = null;
                Object context = selection.getFirstElement();
                if (context instanceof EObject) {
                    service =
                            RsdEditingUtil.getParentOfType((EObject) context,
                                    Service.class);
                }

                /*
                 * Insert before current element if the context is a sibling
                 * element (or it's descendant).
                 */
                int index = CommandParameter.NO_INDEX;
                Resource contextResource =
                        RsdEditingUtil.getParentOfType((EObject) context,
                                Resource.class);
                if (contextResource != null) {
                    index = service.getResources().indexOf(contextResource) + 1;
                }

                if (service != null) {
                    Resource resource =
                            com.tibco.xpd.rsd.RsdFactory.eINSTANCE
                                    .createResource();
                    String name =
                            RsdEditingUtil
                                    .getDefaultName(RsdEditingUtil.NEW_RESOURCE_NAME_PREFIX,
                                            service,
                                            RsdPackage.eINSTANCE.getResource());
                    resource.setName(name);

                    Command cmd =
                            AddCommand
                                    .create(ed,
                                            service,
                                            RsdPackage.eINSTANCE
                                                    .getService_Resources(),
                                            resource,
                                            index);
                    ((AddCommand) cmd)
                            .setLabel(Messages.RsdMainControl_AddResourceCmd_label);
                    ed.getCommandStack().execute(cmd);

                    /*
                     * Refreshes from the content provider are asynchronous so
                     * we refresh parent synchronously to ensure that the new
                     * object's reveal and selection is successful.
                     */
                    getViewer().refresh(service);
                    getViewer().setSelection(new StructuredSelection(resource),
                            true);
                    showProperties.run();
                }

            }
        }

    }

    /**
     * Adds a new Method to the Resource.
     */
    public static class AddMethodAction extends ViewerAction {

        private ShowPropertiesViewAction showProperties =
                new ShowPropertiesViewAction();

        private EditingDomain ed;

        public AddMethodAction(StructuredViewer viewer, EditingDomain ed) {
            super(viewer, Messages.RsdMainControl_AddMethod_label, RsdImage
                    .getImageDescriptor(RsdImage.CREATE_METHOD));
            setToolTipText(Messages.RsdMainControl_AddMethod_tooltip);
            this.ed = ed;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void selectionChanged(IStructuredSelection selection) {
            if (selection.size() == 1) {
                Object elem = selection.getFirstElement();
                if (elem instanceof EObject
                        && RsdEditingUtil.getParentOfType((EObject) elem,
                                Resource.class) != null) {
                    setEnabled(true);
                    return;

                }
            }
            setEnabled(false);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            IStructuredSelection selection =
                    (IStructuredSelection) getViewer().getSelection();
            if (selection.size() == 1) {
                Resource resource = null;
                Object context = selection.getFirstElement();
                if (context instanceof EObject) {
                    resource =
                            RsdEditingUtil.getParentOfType((EObject) context,
                                    Resource.class);
                }

                /*
                 * Insert before current element if the context is a sibling
                 * element.
                 */
                int index = CommandParameter.NO_INDEX;
                if (context instanceof Method) {
                    Method contextMethod = (Method) context;
                    index = resource.getMethods().indexOf(contextMethod) + 1;
                }

                if (resource != null) {
                    Method method =
                            com.tibco.xpd.rsd.RsdFactory.eINSTANCE
                                    .createMethod();
                    Service service =
                            RsdEditingUtil.getParentOfType(resource,
                                    Service.class);
                    String name =
                            RsdEditingUtil
                                    .getDefaultName(RsdEditingUtil.NEW_METHOD_NAME_PREFIX,
                                            service,
                                            RsdPackage.eINSTANCE.getMethod());
                    method.setName(name);

                    Command cmd =
                            AddCommand.create(ed,
                                    resource,
                                    RsdPackage.eINSTANCE.getResource_Methods(),
                                    method,
                                    index);
                    ((AddCommand) cmd)
                            .setLabel(Messages.RsdMainControl_AddMethodCmd_label);
                    ed.getCommandStack().execute(cmd);

                    /*
                     * Refreshes from the content provider are asynchronous so
                     * we refresh parent synchronously to ensure that the new
                     * object's reveal and selection is successful.
                     */
                    getViewer().refresh(resource);
                    getViewer().setSelection(new StructuredSelection(method),
                            true);
                    showProperties.run();
                }
            }
        }
    }

    /**
     * @return the addResourceAction
     */
    public AddResourceAction getAddResourceAction() {
        return addResourceAction;
    }

    /**
     * @return the addMethodAction
     */
    public AddMethodAction getAddMethodAction() {
        return addMethodAction;
    }

    /**
     * @return the showPropertiesViewAction
     */
    public ShowPropertiesViewAction getShowPropertiesViewAction() {
        return showPropertiesViewAction;
    }

    /**
     * @return delete action for this viewer.
     */
    public ViewerDeleteAction getDeleteAction() {
        return deleteAction;
    }

    /**
     * Helper method to get the global editing domain.
     */
    private TransactionalEditingDomain getEditingDomain() {
        return XpdResourcesPlugin.getDefault().getEditingDomain();
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(final ColumnViewer viewer) {
        return new TreeViewerDeleteEMFAction(viewer, getDeletableFeatures());
    }
}
