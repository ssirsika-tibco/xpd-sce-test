/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.Tool;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.icon.IconService;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateViewAndOptionallyElementCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.PromptForConnectionAndEndCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.DynamicPaletteFactory;
import com.tibco.xpd.bom.modeler.diagram.part.DynamicPaletteFactory.BOMNodeToolEntry;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;

/**
 * BOM extension to the {@link ContainerNodeEditPolicy}. This allows the
 * addition of connections to any first-class profile extension elements.
 * 
 * @author njpatel
 * 
 */
public class BOMContainerNodeEditPolicy extends ContainerNodeEditPolicy {

    protected PromptForConnectionAndEndCommand getPromptForConnectionAndEndCommand(
            CreateConnectionRequest request) {
        return new PromptConnectionCommand(request,
                (IGraphicalEditPart) getHost());
    }

    @Override
    protected CreateViewAndOptionallyElementCommand getCreateOtherEndCommand(
            IAdaptable endAdapter, Point location) {

        return new CreateViewAndOptionallyElementCommand(endAdapter,
                (IGraphicalEditPart) getHost(), location,
                ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint()) {

            @Override
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor progressMonitor, IAdaptable info)
                    throws ExecutionException {

                // Get all first-class profile element tool entries
                BOMNodeToolEntry toolEntry =
                        (BOMNodeToolEntry) getElementAdapter()
                                .getAdapter(BOMNodeToolEntry.class);

                if (toolEntry != null) {
                    Tool tool = toolEntry.createTool();

                    if (tool instanceof CreationTool) {
                        CreationTool cTool = (CreationTool) tool;
                        cTool.setViewer(getHost().getViewer());
                        Request request = cTool.createCreateRequest();

                        if (request instanceof CreateRequest) {
                            CreateRequest createRequest =
                                    (CreateRequest) request;
                            createRequest.setLocation(getLocation());
                            IGraphicalEditPart target =
                                    (IGraphicalEditPart) getContainerEP()
                                            .getTargetEditPart(createRequest);
                            if (target != null) {
                                Command theCmd =
                                        target.getCommand(createRequest);
                                setCommand(theCmd);

                                if (getCommand().canExecute()) {
                                    ICommand cmd =
                                            DiagramCommandStack
                                                    .getICommand(getCommand());
                                    cmd.execute(progressMonitor, info);
                                    if (progressMonitor.isCanceled()) {
                                        return CommandResult
                                                .newCancelledCommandResult();
                                    } else if (!(cmd.getCommandResult()
                                            .getStatus().isOK())) {
                                        return cmd.getCommandResult();
                                    }
                                    Object obj =
                                            ((List<?>) createRequest
                                                    .getNewObject()).get(0);
                                    setResult((IAdaptable) obj);
                                    return CommandResult
                                            .newOKCommandResult(getResult());
                                }
                            }
                        }
                    }
                }

                return super.doExecuteWithResult(progressMonitor, info);
            }
        };
    }

    /**
     * Extension of the {@link PromptForConnectionAndEndCommand} to handle
     * first-class profile elements.
     * 
     * @author njpatel
     * 
     */
    private class PromptConnectionCommand extends
            PromptForConnectionAndEndCommand {

        private CreateConnectionRequest origRequest;

        private ILabelProvider endLabelProvider;

        private ILabelProvider connectionAndEndLabelProvider;

        private final Map<ImageDescriptor, Image> images;

        public PromptConnectionCommand(CreateConnectionRequest request,
                IGraphicalEditPart containerEP) {
            super(request, containerEP);
            // Save the request - we will need this to work out what the source
            // object of the link is
            origRequest = request;
            images = new HashMap<ImageDescriptor, Image>();
        }

        @Override
        public void dispose() {
            if (endLabelProvider != null) {
                endLabelProvider.dispose();
            }
            if (connectionAndEndLabelProvider != null) {
                connectionAndEndLabelProvider.dispose();
            }
            for (Image img : images.values()) {
                if (img != null) {
                    img.dispose();
                }
            }
            images.clear();

            super.dispose();
        }

        @Override
        protected List<?> getEndMenuContent(Object connectionItem) {
            List<?> content = super.getEndMenuContent(connectionItem);

            List<Object> myList = new ArrayList<Object>();

            if (content != null && !content.isEmpty()) {
                IFirstClassProfileExtension ext =
                        FirstClassProfileEditPolicyHelper
                                .getFirstClassProfile(getHost());
                if (ext != null) {
                    Collection<PaletteDrawer> drawers =
                            FirstClassProfileEditPolicyHelper
                                    .getPaletteDrawers(getHost().getViewer());
                    for (PaletteDrawer drawer : drawers) {
                        myList.addAll(FirstClassProfileEditPolicyHelper
                                .getToolEntries(drawer, content));
                    }
                }

                if (ext == null || ext.showBomPaletteElements()) {
                    // Include the "standard" elements
                    myList.addAll(content);
                }
            }

            // Check for global data tools
            myList = getGlobalDataTools(myList, connectionItem);

            if (myList.isEmpty()) {
                return content;
            }

            return myList;
        }

        @Override
        protected ILabelProvider getEndLabelProvider() {

            if (endLabelProvider == null) {
                endLabelProvider = new EndLabelProvider() {
                    @Override
                    public String getText(Object element) {
                        if (element instanceof BOMNodeToolEntry) {
                            String label =
                                    ((BOMNodeToolEntry) element).getLabel();
                            if (label != null) {
                                return NLS
                                        .bind(DiagramUIMessages.ConnectionHandle_Popup_NewX,
                                                label);
                            }
                        }
                        return super.getText(element);
                    }

                    @Override
                    public Image getImage(Object object) {
                        if (object instanceof BOMNodeToolEntry) {
                            return getToolImage((BOMNodeToolEntry) object);
                        } else if (object instanceof IElementType) {
                            return IconService.getInstance()
                                    .getIcon((IElementType) object);
                        }
                        return null;
                    }
                };
            }
            return endLabelProvider;
        }

        @Override
        protected ILabelProvider getConnectionAndEndLabelProvider(
                Object connectionItem) {
            if (connectionAndEndLabelProvider == null) {
                connectionAndEndLabelProvider =
                        new ConnectionAndEndLabelProvider(connectionItem) {
                            @Override
                            public String getText(Object element) {
                                if (element instanceof BOMNodeToolEntry) {
                                    String inputStr;
                                    if (isDirectionReversed())
                                        inputStr =
                                                DiagramUIMessages.ConnectionHandle_Popup_CreateXFromNewY;
                                    else
                                        inputStr =
                                                DiagramUIMessages.ConnectionHandle_Popup_CreateXToNewY;
                                    return NLS
                                            .bind(inputStr,
                                                    new Object[] {
                                                            getConnectionItemLabel(),
                                                            ((BOMNodeToolEntry) element)
                                                                    .getLabel() });
                                }
                                return super.getText(element);
                            }

                            @Override
                            public Image getImage(Object object) {
                                if (object instanceof BOMNodeToolEntry) {
                                    return getToolImage((BOMNodeToolEntry) object);
                                } else if (object instanceof IElementType) {
                                    return IconService.getInstance()
                                            .getIcon((IElementType) object);
                                }
                                return null;
                            }

                            /**
                             * Get the label of the source connection item.
                             * 
                             * @return
                             */
                            private String getConnectionItemLabel() {
                                String label = null;
                                Object item = getConnectionItem();
                                if (item instanceof IElementType) {
                                    label =
                                            ((IElementType) item)
                                                    .getDisplayName();
                                } else {
                                    label = item.toString();
                                }

                                return label != null ? label : ""; //$NON-NLS-1$
                            }
                        };
            }
            return connectionAndEndLabelProvider;
        }

        /**
         * Get image for the given tool entry.
         * 
         * @param toolEntry
         * @return
         */
        private Image getToolImage(BOMNodeToolEntry toolEntry) {
            Image img = null;
            if (toolEntry != null) {
                ImageDescriptor descriptor = toolEntry.getSmallIcon();
                if (descriptor != null) {
                    img = images.get(descriptor);
                    if (img == null) {
                        img = descriptor.createImage();
                        images.put(descriptor, img);
                    }
                }
            }
            return img;
        }

        /**
         * Get the tools for the Global Data Profile, removing items from the
         * original list if they should not be available to the target type
         * 
         * @param existingList
         * @return
         */
        private List<Object> getGlobalDataTools(List<Object> existingList,
                Object connectionItem) {
            // Check for the case where we have a global data Class as the
            // source
            EditPart editPart = getHost();
            if (editPart == null) {
                return existingList;
            }
            EObject eo = (EObject) editPart.getAdapter(EObject.class);

            // Check to see if the global data profile is applied
            if (!(eo instanceof Model)
                    || !BOMGlobalDataUtils.hasGlobalDataProfile((Model) eo)) {
                return existingList;
            }

            // Find out what the source of the connector is, based on this will
            // decide what items are displayed on the popup menu
            EditPart srcEditPart = origRequest.getSourceEditPart();

            // Make sure it is a class type - we do not add anything for
            // anything other than a Class as the origin, for example we do not
            // want these items appearing off of an enumeration or primitive
            // type
            if (srcEditPart instanceof ClassEditPart) {
                // Create the ToolEntries for the global types
                Stereotype caseStereotype =
                        GlobalDataProfileManager.getInstance()
                                .getStereotype(StereotypeKind.CASE);
                ToolEntry classToolEntry =
                        DynamicPaletteFactory
                                .createClassCreationTool(caseStereotype);
                Stereotype globalStereotype =
                        GlobalDataProfileManager.getInstance()
                                .getStereotype(StereotypeKind.GLOBAL);
                ToolEntry globalToolEntry =
                        DynamicPaletteFactory
                                .createClassCreationTool(globalStereotype);

                // Find out what type of class it is
                Class element = ((ClassEditPart) srcEditPart).getElement();

                // Check if this is a Case Class
                if (GlobalDataProfileManager.getInstance().isCase(element)) {
                    // Case classes can only be the source for either Case or
                    // Global Classes, so clear the existing entries first
                    existingList.clear();
                    // Only add a global to a case if it is a different type
                    if (connectionItem == UMLElementTypes.Generalization_3001) {
                        existingList.add(classToolEntry);
                    } else { // Association
                        // Check which type of association this is
                        Object obj =
                                origRequest.getExtendedData().get("AggKind"); //$NON-NLS-1$
                        if (obj != null) {
                            String agKind = obj.toString();

                            // Standard association can only go to a Case Class
                            if (agKind.equals("none")) { //$NON-NLS-1$
                                existingList.add(classToolEntry);
                            }
                            // Composition can only go to a Global class
                            if (agKind.equals("composition") || agKind.equals("composite")) { //$NON-NLS-1$ //$NON-NLS-2$
                                existingList.add(globalToolEntry);
                            }
                            // Aggregation can only go to a Case Class
                            if (agKind.equals("aggregation") || agKind.equals("shared")) { //$NON-NLS-1$ //$NON-NLS-2$
                                existingList.add(classToolEntry);
                            }
                        }
                    }
                } else if (GlobalDataProfileManager.getInstance()
                        .isGlobal(element)) {
                    // Global classes can link to Other Globals only
                    existingList.clear();
                    existingList.add(globalToolEntry);
                }
            }

            return existingList;
        }
    }
}
