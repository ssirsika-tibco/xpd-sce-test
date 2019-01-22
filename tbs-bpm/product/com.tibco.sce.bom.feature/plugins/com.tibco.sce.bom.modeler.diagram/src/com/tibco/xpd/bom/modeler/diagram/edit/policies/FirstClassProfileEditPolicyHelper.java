/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.uml2.uml.Element;

import com.tibco.xpd.bom.modeler.diagram.part.DynamicPaletteFactory.BOMNodeToolEntry;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;

/**
 * Helper class to get all first-class profile extension {@link ToolEntry
 * ToolEntries} from the palette.
 * 
 * @author njpatel
 * 
 */
/* public */final class FirstClassProfileEditPolicyHelper {

    /**
     * Get the first-class profile extension if this is a first-class profile
     * model.
     * 
     * @return profile extension if first-class profile model, <code>null</code>
     *         otherwise.
     */
    public static IFirstClassProfileExtension getFirstClassProfile(EditPart host) {
        if (host != null) {
            EObject eo = (EObject) host.getAdapter(EObject.class);
            if (eo instanceof Element) {
                return FirstClassProfileManager.getInstance()
                        .getAppliedFirstClassProfile(((Element) eo).getModel());
            }
        }
        return null;
    }

    /**
     * Get all {@link PaletteDrawer}s from the editor's palette.
     * 
     * @param viewer
     * @return collection of drawers, empty collection if none found.
     */
    public static Collection<PaletteDrawer> getPaletteDrawers(
            EditPartViewer viewer) {
        List<PaletteDrawer> drawers = new ArrayList<PaletteDrawer>();
        if (viewer != null) {
            PaletteViewer paletteViewer =
                    viewer.getEditDomain().getPaletteViewer();

            if (paletteViewer != null && paletteViewer.getPaletteRoot() != null) {
                for (Object child : paletteViewer.getPaletteRoot()
                        .getChildren()) {
                    if (child instanceof PaletteDrawer) {
                        PaletteDrawer drawer = (PaletteDrawer) child;
                        if (drawer.isInitiallyOpen()) {
                            drawers.add(drawer);
                        }
                    }
                }
            }
        }
        return drawers;
    }

    /**
     * Get all {@link BOMNodeToolEntry Tool Entries} from the palette container
     * that match the given element types. This is used to get all first-class
     * profile extension element extensions of the elementTypes.
     * 
     * @param paletteContainer
     * @param type
     *            include only element types in this list. If <code>null</code>
     *            then include all tool entries.
     * @return collection of tool entries in the palette container that match
     *         the given element types, empty collection if none found.
     */
    public static Collection<BOMNodeToolEntry> getToolEntries(
            PaletteContainer paletteContainer, List<?> elementTypes) {
        List<BOMNodeToolEntry> tools = new ArrayList<BOMNodeToolEntry>();
        if ((elementTypes == null || !elementTypes.isEmpty())
                && paletteContainer != null) {
            for (Object child : paletteContainer.getChildren()) {
                if (child instanceof BOMNodeToolEntry) {
                    BOMNodeToolEntry toolEntry = (BOMNodeToolEntry) child;
                    Tool tool = toolEntry.createTool();
                    if (tool != null && tool instanceof CreationTool) {
                        CreationTool creationTool = (CreationTool) tool;

                        IElementType type = creationTool.getElementType();
                        if (elementTypes == null
                                || (type != null && elementTypes.contains(type))) {
                            tools.add(toolEntry);
                        }
                    }

                } else if (child instanceof PaletteStack) {
                    // add all the entries from a palette stack as well
                    tools.addAll(getToolEntries((PaletteContainer) child,
                            elementTypes));
                }
            }
        }
        return tools;
    }
}
