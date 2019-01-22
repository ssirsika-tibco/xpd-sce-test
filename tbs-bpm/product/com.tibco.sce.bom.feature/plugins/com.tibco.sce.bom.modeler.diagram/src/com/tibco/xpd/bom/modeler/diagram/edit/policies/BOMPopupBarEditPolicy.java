/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.ui.services.icon.IconService;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.PopupBarEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.PopupBarTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantService;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.DynamicPaletteFactory;
import com.tibco.xpd.bom.modeler.diagram.part.DynamicPaletteFactory.BOMNodeToolEntry;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;

/**
 * Customised {@link PopupBarEditPolicy}. This will ensure that, if a
 * first-class profile model is being edited then any elements (tools) defined
 * in it are provided on the popup bar.
 * 
 * @author njpatel
 * 
 */
public class BOMPopupBarEditPolicy extends PopupBarEditPolicy {

    private final Map<ImageDescriptor, Image> images =
            new HashMap<ImageDescriptor, Image>();

    @Override
    protected void populatePopupBars() {
        List<?> types =
                ModelingAssistantService.getInstance()
                        .getTypesForPopupBar(getHost());

        // If editing first-class profile model then add its tool entries if any
        boolean addStandardElements = true;
        IFirstClassProfileExtension ext =
                FirstClassProfileEditPolicyHelper
                        .getFirstClassProfile(getHost());
        if (ext != null) {
            addStandardElements = ext.showBomPaletteElements();
            Collection<PaletteDrawer> drawers =
                    FirstClassProfileEditPolicyHelper
                            .getPaletteDrawers(getHost().getViewer());
            for (PaletteDrawer drawer : drawers) {
                fillPopupBarForFirstClassExtension(types, drawer);
            }
        }

        if (addStandardElements) {
            for (Object type : types) {
                if (type instanceof IElementType) {
                    addPopupBarDescriptor((IElementType) type, IconService
                            .getInstance().getIcon((IElementType) type));
                }
            }
        }

        // Need to also check for Global Data Types
        EditPart editPart = getHost();
        if (editPart instanceof ClassEditPart) {
            Class element = ((ClassEditPart) editPart).getElement();
            if (GlobalDataProfileManager.getInstance().isCase(element)) {
                // Need to add the Case Identifier icon
                Stereotype stereotype =
                        GlobalDataProfileManager
                                .getInstance()
                                .getStereotype(StereotypeKind.AUTO_CASE_IDENTIFIER);
                ToolEntry toolEntry =
                        DynamicPaletteFactory
                                .createAttributeCreationTool(stereotype);
                addToolEntryToPopup(toolEntry);
                // Need to add the Case State icon
                Stereotype stereotypeCaseState =
                        GlobalDataProfileManager
                                .getInstance()
                                .getStereotype(StereotypeKind.CASE_STATE);
                ToolEntry toolEntryCaseState =
                        DynamicPaletteFactory
                                .createAttributeCreationTool(stereotypeCaseState);
                addToolEntryToPopup(toolEntryCaseState);
            }
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

    /**
     * @param type
     * @param paletteContainer
     */
    private void fillPopupBarForFirstClassExtension(List<?> elementTypes,
            PaletteContainer paletteContainer) {
        if (elementTypes != null && !elementTypes.isEmpty()
                && paletteContainer != null) {

            Collection<BOMNodeToolEntry> toolEntries =
                    FirstClassProfileEditPolicyHelper
                            .getToolEntries(paletteContainer, elementTypes);
            for (BOMNodeToolEntry toolEntry : toolEntries) {
                addToolEntryToPopup(toolEntry);
            }
        }
    }

    private boolean isMirrored() {
        return ((getHost().getViewer().getControl().getStyle() & SWT.MIRRORED) != 0);
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    private Image convert(Image srcImage) {
        int height = srcImage.getBounds().height;
        int width = srcImage.getBounds().width;

        ImageData srcImageData = srcImage.getImageData();

        RGB backgroundRGB =
                ((GraphicalEditPart) getHost()).getFigure()
                        .getBackgroundColor().getRGB();
        int backgroundColor = srcImageData.palette.getPixel(backgroundRGB);

        // Set the transparent pixels to the background color
        int count = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (((srcImageData.maskData[count >> 3] >> (7 - (count % 8))) & 1) == 0) {
                    srcImageData.setPixel(x, y, backgroundColor);
                }
                count++;
            }
        }
        srcImageData.maskData = null;

        Image convertedImage =
                ImageDescriptor.createFromImageData(srcImageData)
                        .createImage(srcImage.getDevice());

        imagesToBeDisposed.add(convertedImage);

        return convertedImage;
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

            PopupBarTool theTracker =
                    new PopupBarTool(getHost(),
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
                    if (image != null && image.type == SWT.ICON && isMirrored()) {
                        image = convert(image);
                    }
                    images.put(smallIcon, image);
                }
            }

            // Get the tooltip label
            String label = toolEntry.getLabel();
            if (label != null) {
                label =
                        String.format(Messages.BOMPopupBarEditPolicy_AddElement_tooltip,
                                label);
            }

            addPopupBarDescriptor(creationTool.getElementType(),
                    image,
                    theTracker,
                    label);
        }
    }
}
