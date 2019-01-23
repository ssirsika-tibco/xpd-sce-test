/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.process.progression;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;

import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.GatewayEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.progression.AbstractProgressionPart;
import com.tibco.xpd.processwidget.progression.AbstractProgressionPartFactory;
import com.tibco.xpd.processwidget.progression.BaseFadingProgressionPart;
import com.tibco.xpd.processwidget.progression.model.ProgressionModel;

/**
 * Progression part factory specific to BPMN process diagrams.
 * 
 * 
 * @author aallway
 * @since 3.3 (2 Nov 2009)
 */
public class ProcessProgressionPartFactory extends
        AbstractProgressionPartFactory {

    public ProcessProgressionPartFactory() {
    }

    /**
     * Create an appropriate {@link AbstractProgressionPart} for the given
     * progression model and process diagram object edit part.
     * 
     * @param progressionModel
     * @param originalGraphicalEditPart
     * 
     * @return Progression Edit Part.
     */
    @Override
    public AbstractProgressionPart createObjectProgressionPart(
            ProgressionModel progressionModel,
            GraphicalEditPart originalGraphicalEditPart,
            IFigure progressionFiguresLayer) {

        if (originalGraphicalEditPart instanceof BaseConnectionEditPart) {
            return new BaseConnectionProgressionPart(progressionModel,
                    originalGraphicalEditPart, progressionFiguresLayer);

        } else if (originalGraphicalEditPart instanceof BaseFlowNodeEditPart) {
            if (originalGraphicalEditPart instanceof TaskEditPart) {
                if (((TaskEditPart) originalGraphicalEditPart)
                        .isEmbeddedSubProc()) {
                    return new EmbeddedSubProcessProgressionPart(
                            progressionModel, originalGraphicalEditPart,
                            progressionFiguresLayer);
                } else {
                    return new TaskProgressionPart(progressionModel,
                            originalGraphicalEditPart, progressionFiguresLayer);
                }

            } else if (originalGraphicalEditPart instanceof EventEditPart) {
                return new EventProgressionPart(progressionModel,
                        originalGraphicalEditPart, progressionFiguresLayer);

            } else if (originalGraphicalEditPart instanceof GatewayEditPart) {
                return new GatewayProgressionPart(progressionModel,
                        originalGraphicalEditPart, progressionFiguresLayer);
            }

            return new BaseFadingProgressionPart(progressionModel,
                    originalGraphicalEditPart, progressionFiguresLayer);
        }

        return super.createObjectProgressionPart(progressionModel,
                originalGraphicalEditPart,
                progressionFiguresLayer);
    }

}
