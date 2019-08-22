/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.navigator;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.ui.RsdImage;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * Action provider to add a children for RSD model objects in the project
 * explorer.
 * 
 * @author jarciuch
 * @since 4 Mar 2015
 */
public class RsdAddChildActionProvider extends CommonActionProvider {

    private final class CreateAndSelectChildAction extends CreateChildAction {

        private CreateAndSelectChildAction(IWorkbenchPart workbenchPart,
                ISelection selection, Object descriptor) {
            super(workbenchPart, selection, descriptor);
        }

        @Override
        public void configureAction(ISelection selection) {
            super.configureAction(selection);

            if (descriptor instanceof CommandParameter) {
                CommandParameter cp = (CommandParameter) descriptor;
                Object obj = cp.getValue();
                ImageDescriptor imageDesc = null;
                if (obj instanceof Resource) {
                    setText(Messages.RsdAddChildActionProvider_Resource_menu);
                    imageDesc = RsdImage.getImageDescriptor(RsdImage.RESOURCE);

                }

                if (obj instanceof Method) {
                    setText(Messages.RsdAddChildActionProvider_Method_menu);
                    imageDesc = RsdImage.getImageDescriptor(RsdImage.METHOD);
                }

                if (imageDesc != null) {
                    setImageDescriptor(imageDesc);
                }
            }

        }

        @Override
        public void run() {
            if (this.command != null && this.command.canExecute()) {
                StructuredViewer viewer = getActionSite().getStructuredViewer();
                IStructuredSelection oldSel =
                        (IStructuredSelection) viewer.getSelection();
                super.run();

                if (this.descriptor instanceof CommandParameter) {
                    Object child = ((CommandParameter) this.descriptor).value;
                    // make first element the selection
                    if (child != null) {
                        StructuredSelection newSelection =
                                new StructuredSelection(child);
                        if (!oldSel.isEmpty()) {
                            viewer.refresh(oldSel.getFirstElement());
                        }
                        viewer.setSelection(newSelection, true);
                    }
                }
            }
        }
    }

    private final IWorkbenchPart activePart;

    /**
     * Default constructor.
     */
    public RsdAddChildActionProvider() {
        activePart =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActivePart();
    }

    @Override
    public void init(ICommonActionExtensionSite site) {

        super.init(site);
    }

    @Override
    public void fillContextMenu(IMenuManager menu) {
        MenuManager subMenu =
                new MenuManager(
                        Messages.RsdAddChildActionProvider_AddChild_menu);

        if (getContext() != null
                && getContext().getSelection() instanceof IStructuredSelection) {
            IStructuredSelection selection =
                    (IStructuredSelection) getContext().getSelection();

            if (selection.size() == 1) {
                Object first = selection.getFirstElement();
                IEditingDomainItemProvider provider =
                        (IEditingDomainItemProvider) XpdResourcesPlugin
                                .getDefault().getAdapterFactory()
                                .adapt(first, IEditingDomainItemProvider.class);
                if (provider != null) {
                    Collection<?> descriptors =
                            provider.getNewChildDescriptors(first,
                                    XpdResourcesPlugin.getDefault()
                                            .getEditingDomain(),
                                    null);
                    if (descriptors != null) {
                        for (Object descriptor : descriptors) {
                            CreateAndSelectChildAction newAction =
                                    new CreateAndSelectChildAction(activePart, selection, descriptor);
                            /*
                             * ACE-2473: Saket: Action should be disabled for
                             * locked application.
                             */
                            if (selection.getFirstElement() instanceof EObject) {
                                boolean isLocked = (new GovernanceStateService())
                                        .isLockedForProduction((EObject) (selection.getFirstElement()));
                                newAction.setEnabled(!isLocked);
                            }
                            subMenu.add(newAction);
                        }
                    }
                }
            }
            if (!subMenu.isEmpty()) {
                menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, subMenu);
            }
        }
    }

}
