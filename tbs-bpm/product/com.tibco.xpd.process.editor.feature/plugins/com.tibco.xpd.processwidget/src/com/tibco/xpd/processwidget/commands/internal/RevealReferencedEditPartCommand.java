/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.commands.internal;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;

import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;

/**
 *
 * @author aallway
 * @since 
 */
public class RevealReferencedEditPartCommand extends Command {

    private GraphicalEditPart referenceSource;
    private GraphicalEditPart referenceTarget;

    public RevealReferencedEditPartCommand(GraphicalEditPart referenceSource, GraphicalEditPart referenceTarget) {
        this.referenceSource = referenceSource;
        this.referenceTarget = referenceTarget;
    }
    
    @Override
    public void execute() {
        EditPartViewer viewer = referenceTarget.getViewer();
        viewer.select(referenceTarget);
        if (viewer instanceof BpmnScrollingGraphicalViewer) {
            ((BpmnScrollingGraphicalViewer)viewer).reveal(referenceTarget, true, false);
        } else {
            viewer.reveal(referenceTarget);
        }
        
    }
    
}
