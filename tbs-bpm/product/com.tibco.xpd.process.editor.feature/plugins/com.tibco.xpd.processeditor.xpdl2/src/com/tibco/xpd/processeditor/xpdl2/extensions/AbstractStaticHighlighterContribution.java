/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.extensions;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.xpd.xpdl2.Process;

/**
 * When the user selects the menu item this contributoin is asked for a list of
 * objects in the current process that are relevant to it. The return is
 * combined with the list of diagram objects for the diagram objects highlighted
 * because of selected objects (or all diagram objects if current selection does
 * not apply).
 * 
 * @author aallway
 * @since 3 Feb 2011
 */
public abstract class AbstractStaticHighlighterContribution {

    /**
     * @return The text for the highlight references drop down menu item
     */
    public abstract String getMenuText();

    /**
     * @return return teh string to appear as part of the process diagram
     *         tooltip when the static highlighter is active.
     */
    public abstract String getActivatedTooltipLabel();

    /**
     * @return The image for the highlight references drop down menu item
     */
    public abstract ImageDescriptor getMenuImageDescriptor();

    /**
     * Get the diagram model objects (the model objects such as Activity,
     * Transition etc) for the main process diagram parts this contribution
     * wishes to highlight.
     * 
     * 
     * @param diagramProcess
     *            The process in which to search for references to
     *            referencedObject
     * 
     * @return Set of diagram part model objects.
     */
    public abstract Collection<? extends EObject> getHighlightedDiagramModelObjects(
            Process diagramProcess);

    /**
     * Check if the highlighter should be shown for the given process.
     * 
     * @param process
     * 
     * @return <code>true</code> if the highlighter should be shown.
     * @since v3.5.10
     */
    public boolean shouldShow(Process process) {
        return true;
    }
}
