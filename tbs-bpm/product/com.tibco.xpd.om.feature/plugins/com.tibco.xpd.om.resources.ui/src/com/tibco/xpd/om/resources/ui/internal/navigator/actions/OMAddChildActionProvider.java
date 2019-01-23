/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.navigator.actions;

import java.util.Collection;

import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Action provider to add a children for OM model's objects in the project
 * explorer.
 * 
 * @author Jan Arciuchiewicz
 */
public class OMAddChildActionProvider extends CommonActionProvider {

    /**
     * @author wzurek
     * 
     */
    private final class CreateAndSelectChildAction extends CreateChildAction {

        /**
         * @param workbenchPart
         * @param selection
         * @param descriptor
         */
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
                Image img = null;
                if (obj instanceof Organization) {
                    Organization org = (Organization) obj;
                    if (org.getType() != null) {
                        setText(org.getType().getDisplayName());

                        img =
                                ExtendedImageRegistry.INSTANCE
                                        .getImage(OMModelImages
                                                .getImageObject(OMModelImages.IMAGE_ORGANISATION_TYPE));

                    }

                }

                if (obj instanceof OrgUnit) {
                    OrgUnit ou = (OrgUnit) obj;
                    if (ou.getFeature() != null) {
                        img =
                                ExtendedImageRegistry.INSTANCE
                                        .getImage(OMModelImages
                                                .getImageObject(OMModelImages.IMAGE_ORG_UNIT_FEATURE));
                    }

                }

                if (obj instanceof Position) {
                    Position pos = (Position) obj;
                    if (pos.getFeature() != null) {
                        img =
                                ExtendedImageRegistry.INSTANCE
                                        .getImage(OMModelImages
                                                .getImageObject(OMModelImages.IMAGE_POSITION_FEATURE));
                    }
                }

                if (obj instanceof Resource) {
                    Resource res = (Resource) obj;
                    if (res.getType() != null) {
                        setText(res.getType().getDisplayName());
                        img =
                                ExtendedImageRegistry.INSTANCE
                                        .getImage(OMModelImages
                                                .getImageObject(OMModelImages.IMAGE_RESOURCE_TYPE));
                    }
                }

                if (obj instanceof Location) {
                    Location location = (Location) obj;
                    if (location.getType() != null) {
                        setText(location.getType().getDisplayName());
                        img =
                                ExtendedImageRegistry.INSTANCE
                                        .getImage(OMModelImages
                                                .getImageObject(OMModelImages.IMAGE_LOCATION_TYPE));
                    }
                }

                if (img != null) {
                    ImageDescriptor imgDesc =
                            ImageDescriptor.createFromImage(img);
                    setImageDescriptor(imgDesc);
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
    public OMAddChildActionProvider() {
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
        MenuManager subMenu = new MenuManager(Messages.Menu_AddChild_label);

        if (getContext() != null
                && getContext().getSelection() instanceof IStructuredSelection) {
            IStructuredSelection selection =
                    (IStructuredSelection) getContext().getSelection();

            if (selection.size() == 1) {
                Object sel = selection.getFirstElement();
                IEditingDomainItemProvider provider =
                        (IEditingDomainItemProvider) XpdResourcesPlugin
                                .getDefault().getAdapterFactory().adapt(sel,
                                        IEditingDomainItemProvider.class);
                if (provider != null) {
                    Collection<?> descriptors =
                            provider.getNewChildDescriptors(sel,
                                    XpdResourcesPlugin.getDefault()
                                            .getEditingDomain(),
                                    null);
                    if (descriptors != null) {
                        for (Object descriptor : descriptors) {
                            subMenu.add(new CreateAndSelectChildAction(
                                    activePart, selection, descriptor));
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
