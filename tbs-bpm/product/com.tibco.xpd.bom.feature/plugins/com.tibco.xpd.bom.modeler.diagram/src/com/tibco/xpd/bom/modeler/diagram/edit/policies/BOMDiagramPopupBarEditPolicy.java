/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramPopupBarEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.PopupBarTool;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackagePackageContentsCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.DynamicPaletteFactory;
import com.tibco.xpd.bom.modeler.diagram.part.DynamicPaletteFactory.BOMNodeToolEntry;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;

/**
 * Customised {@link DiagramPopupBarEditPolicy}. This will ensure that, if a
 * first-class profile model is being edited then any elements (tools) defined
 * in it are provided on the popup bar.
 * 
 * @author njpatel
 * 
 */
public class BOMDiagramPopupBarEditPolicy extends DiagramPopupBarEditPolicy {

    private final Map<ImageDescriptor, Image> images =
            new HashMap<ImageDescriptor, Image>();

    @Override
    protected void fillWithPaletteToolsInContainer(
            PaletteContainer palContainer) {

        // Check to see if this is a Global Data palette, in which case will
        // will add the correct entries to the palette
        boolean isGlobalData = fillWithGloablDataPaletteTools(palContainer);

        // If we have the global data profile, we do not want to process a
        // custom profile
        if (isGlobalData) {
            return;
        }

        // If this is a first class profile model then process the elements
        // contributed by it, if any
        if (FirstClassProfileEditPolicyHelper
                .getFirstClassProfile(getHost()) != null) {
            if (palContainer != null) {
                Collection<BOMNodeToolEntry> toolEntries =
                        FirstClassProfileEditPolicyHelper
                                .getToolEntries(palContainer, null);
                for (BOMNodeToolEntry toolEntry : toolEntries) {
                    addToolEntryToPopup(toolEntry);
                }
            }
        } else {
            super.fillWithPaletteToolsInContainer(palContainer);
        }
    }

    @Override
    protected void populatePopupBars() {
        IFirstClassProfileExtension ext = FirstClassProfileEditPolicyHelper
                .getFirstClassProfile(getHost());
        if (ext != null && !ext.showBomPaletteElements()) {
            // Only show the first-class extension elements
            fillPopupBarDescriptors();
        } else {
            super.populatePopupBars();
        }
    }

    @Override
    public void deactivate() {
        for (Image img : images.values()) {
            if (img != null) {
                img.dispose();
            }
        }
        images.clear();
        super.deactivate();
    }

    private boolean isMirrored() {
        return ((getHost().getViewer().getControl().getStyle()
                & SWT.MIRRORED) != 0);
    }

    @SuppressWarnings("unchecked") //$NON-NLS-1$
    private Image convert(Image srcImage) {
        int height = srcImage.getBounds().height;
        int width = srcImage.getBounds().width;

        ImageData srcImageData = srcImage.getImageData();

        RGB backgroundRGB = ((GraphicalEditPart) getHost()).getFigure()
                .getBackgroundColor().getRGB();
        int backgroundColor = srcImageData.palette.getPixel(backgroundRGB);

        // Set the transparent pixels to the background color
        int count = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (((srcImageData.maskData[count >> 3] >> (7 - (count % 8)))
                        & 1) == 0) {
                    srcImageData.setPixel(x, y, backgroundColor);
                }
                count++;
            }
        }
        srcImageData.maskData = null;

        Image convertedImage = ImageDescriptor.createFromImageData(srcImageData)
                .createImage(srcImage.getDevice());

        imagesToBeDisposed.add(convertedImage);

        return convertedImage;
    }

    /**
     * Checks if the Global Data palette needs to be set, returns true if it was
     * set
     * 
     * @param palContainer
     * @return
     */
    private boolean fillWithGloablDataPaletteTools(
            PaletteContainer palContainer) {
        boolean isGlobalDataPalette = false;

        // Check if the BOM we are dealing with is a global data BOM
        EditPart editPart = getHost();
        if (editPart != null) {
            Object eo = editPart.getAdapter(EObject.class);
            if (eo instanceof Package) {
                eo = ((Package) eo).getModel();
            }
            if (eo instanceof Model) {
                isGlobalDataPalette =
                        BOMGlobalDataUtils.isGlobalDataBOM((Model) eo);
            }
        }

        // Not a Global Data BOM so nothing to add, and return false
        if (!isGlobalDataPalette) {
            return false;
        }

        // Now add the global data specific tools that should be displayed in
        // the diagram view when hovering over the background
        if ((editPart instanceof CanvasPackageEditPart)
                || (editPart instanceof PackagePackageContentsCompartmentEditPart)) {
            // Need to add the Case Class icon
            Stereotype caseStereotype = GlobalDataProfileManager.getInstance()
                    .getStereotype(StereotypeKind.CASE);
            addToolEntryToPopup(DynamicPaletteFactory
                    .createClassCreationTool(caseStereotype));
        }

        return true;
    }

    /**
     * Adds a given tool entry to the popup
     * 
     * @param toolEntry
     */
    private void addToolEntryToPopup(ToolEntry toolEntry) {
        Tool createTool = toolEntry.createTool();
        if (createTool instanceof CreationTool) {
            CreationTool creationTool = (CreationTool) createTool;
            creationTool.setViewer(getHost().getViewer());

            PopupBarTool theTracker = new PopupBarTool(getHost(),
                    (CreateRequest) creationTool.createCreateRequest());

            // Get the image, we need to store this in the internal
            // list. Doing this enables us to keep track of the images
            // we have created and we can then remove them when the popup
            // is deactivated
            Image image = null;
            ImageDescriptor smallIcon = toolEntry.getSmallIcon();
            if (smallIcon != null) {
                // Check the list to see if we already have the image
                image = images.get(smallIcon);
                if (image == null) {
                    image = smallIcon.createImage();
                    // Workaround for mirroring and SWT.ICON issue
                    if (image != null && image.type == SWT.ICON
                            && isMirrored()) {
                        image = convert(image);
                    }
                    images.put(smallIcon, image);
                }
            }

            // Get the tooltip label
            String label = toolEntry.getLabel();
            if (label != null) {
                label = String.format(
                        Messages.BOMDiagramPopupBarEditPolicy_AddElement_tooltip,
                        label);
            }

            addPopupBarDescriptor(creationTool.getElementType(),
                    image,
                    theTracker,
                    label);
        }
    }

    @Override
    protected boolean shouldShowDiagramAssistant() {
        // There is a case where there could be a bom file with an invalid name
        // (i.e. has a space in it) and the user tries to open it. An error
        // message will be displayed, however it has been seen that occasionally
        // a sort of race condition happens where it actually starts to load the
        // tool popups etc on another thread. However, when it does this, not
        // everything has been created correctly - and a null pointer exception
        // is thrown from DiagramAssistantEditPolicy.isDiagramPartActive when it
        // tries to access activePart.getDiagramEditPart() because it does not
        // exist by the time it is accessed. This is a work-around to make sure
        // we get to check it before eclipse does it's normal work
        IWorkbenchWindow window =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow();

        if (window != null) {
            IWorkbenchPage page = window.getActivePage();
            if (page != null) {
                IWorkbenchPart activePart = page.getActivePart();
                if (activePart instanceof IDiagramWorkbenchPart) {
                    // Prevent the Null Pointer access by checking it here
                    if (((IDiagramWorkbenchPart) activePart)
                            .getDiagramEditPart() == null) {
                        return false;
                    }
                }
            }
        }

        return super.shouldShowDiagramAssistant();
    }
}
