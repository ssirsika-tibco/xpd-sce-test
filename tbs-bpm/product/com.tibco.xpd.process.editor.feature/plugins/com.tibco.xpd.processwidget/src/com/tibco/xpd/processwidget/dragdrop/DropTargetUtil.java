/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processwidget.dragdrop;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.swt.dnd.DropTargetEvent;

import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.figures.ProcessFigure.DiagramViewType;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.PoolEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;

/**
 * @author aallway
 * 
 */
public class DropTargetUtil {
    public static class DropTargetEditPartInfo {
        /** The raw drop event location. */
        public Point dropEventLocation = null;

        /** The location within the process widget (diagram editor) control. */
        public Point absWidgetLocation = null;

        /** The edit part at the given location. */
        public ModelAdapterEditPart targetEP = null;

        /**
         * If targetEP is a connection edit part then this is the Lane or
         * Embedded SubProcess at the given location behind the sequence flow.
         * <p>
         * Otherwise it is the same as targetEP.
         */
        public BaseGraphicalEditPart targetContainer = null;

        /** Location of drop relative to targetContainer. */
        public Point containerRelativeLocation = null;

        /** For 'sticky mouse cursor' when over things like conneciton lines. */
        public Point adjustedCursorLocation = null;

    }

    /**
     * Get information such as the target edit part for the given drop event.
     * 
     * @param event
     * @return Drop target information or null if no valid drop target at event
     *         location.
     */
    public static DropTargetEditPartInfo getDropTargetEditPartInfo(
            ProcessWidget processWidgetImpl, DropTargetEvent event) {

        DropTargetEditPartInfo info = new DropTargetEditPartInfo();

        // Store the original event location.
        info.dropEventLocation = new Point(event.x, event.y);

        // Get the event location as absolute within process widget control.
        org.eclipse.swt.graphics.Point tmpLoc =
                processWidgetImpl.getGraphicalViewer().getControl()
                        .toControl(event.x, event.y);

        info.absWidgetLocation = new Point(tmpLoc.x, tmpLoc.y);

        EditPart ep = findEditPartAtLocation(processWidgetImpl, info);

        Point adjusted = getAdjustedDropLocation(info.absWidgetLocation, ep);

        if (adjusted.x != info.absWidgetLocation.x
                || adjusted.y != info.absWidgetLocation.y) {
            // Also store the adjusted co-ords in system absolute
            org.eclipse.swt.graphics.Point tmpAdjLoc =
                    processWidgetImpl.getGraphicalViewer().getControl()
                            .toDisplay(adjusted.x, adjusted.y);

            info.adjustedCursorLocation = new Point(tmpAdjLoc.x, tmpAdjLoc.y);
        }

        info.absWidgetLocation = adjusted;

        if (ep instanceof ModelAdapterEditPart) {
            /*
             * Recalculate the location relative to the edit part under the
             * mouse.
             */
            info.targetEP = (ModelAdapterEditPart) ep;

            if (ep instanceof BaseGraphicalEditPart) {
                info.targetContainer = (BaseGraphicalEditPart) ep;

                /*
                 * Pageflow is a special case because it doesn't 'look' like it
                 * has a pool / lane, but it really does - it's just that the
                 * user cannot see it. Therefore for pageflow processes redirect
                 * the drop request to the invisible lane.
                 */
                if (info.targetContainer instanceof ProcessEditPart
                        && DiagramViewType.NO_POOLS
                                .equals(((ProcessEditPart) info.targetContainer)
                                        .getDiagramViewType())) {
                    LaneEditPart firstLane =
                            getFirstLane((ProcessEditPart) info.targetContainer);
                    if (firstLane != null) {
                        info.targetContainer = firstLane;
                        info.targetEP = firstLane;
                    }
                }

            } else if (ep instanceof BaseConnectionEditPart) {
                /*
                 * WHat's behind the connection linew? This exclusion condition
                 * will only return Lane or embedded sub-proc task. (Or
                 * ProcessEditPart if all else fails).
                 */
                EditPartViewer.Conditional exclusionConditional =
                        new EditPartViewer.Conditional() {
                            @Override
                            public boolean evaluate(EditPart editpart) {
                                if (editpart instanceof LaneEditPart) {
                                    return true;
                                } else if (editpart instanceof TaskEditPart) {
                                    TaskEditPart tep = (TaskEditPart) editpart;
                                    if (tep.isEmbeddedSubProc()) {
                                        return true;
                                    }
                                }
                                return false;
                            }

                        };

                EditPart behindSeq =
                        info.targetEP.getViewer()
                                .findObjectAtExcluding(info.absWidgetLocation,
                                        Collections.EMPTY_LIST,
                                        exclusionConditional);

                if (behindSeq instanceof LaneEditPart
                        || behindSeq instanceof TaskEditPart) {
                    info.targetContainer = (BaseGraphicalEditPart) behindSeq;
                }
            }

            if (info.targetContainer != null) {
                IFigure contentPane = info.targetContainer.getContentPane();

                info.containerRelativeLocation =
                        info.absWidgetLocation.getCopy();
                contentPane.translateToRelative(info.containerRelativeLocation);

                if (contentPane.getLayoutManager() instanceof XYLayout) {
                    XYLayout lay = (XYLayout) contentPane.getLayoutManager();
                    info.containerRelativeLocation.translate(lay
                            .getOrigin(contentPane).getCopy().negate());
                }

                return info;
            }
        }

        return null;
    }

    private static LaneEditPart getFirstLane(ProcessEditPart processEditPart) {
        List children = processEditPart.getChildren();
        for (Object c : children) {
            if (c instanceof PoolEditPart) {
                List lanes = ((PoolEditPart) c).getChildren();

                for (Object l : lanes) {
                    if (l instanceof LaneEditPart) {
                        return (LaneEditPart) l;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param processWidgetImpl
     * @param info
     * @return
     */
    private static EditPart findEditPartAtLocation(
            ProcessWidget processWidgetImpl, DropTargetEditPartInfo info) {
        EditPart ep =
                processWidgetImpl.getGraphicalViewer()
                        .findObjectAt(info.absWidgetLocation);

        // If target is WidgetRootEditPart swap it to the process.
        if (ep instanceof WidgetRootEditPart) {

            ProcessEditPart pep =
                    ((WidgetRootEditPart) ep).getProcessEditPart();

            // Check to see whether location is to the right of any lane, if so,
            // treat that as the target edit part.
            Point rel = info.absWidgetLocation.getCopy();
            pep.getFigure().translateToRelative(rel);
            LaneEditPart lane = pep.getLaneFromPosition(rel);

            if (lane != null) {
                ep = lane;
            } else {
                ep = pep;
            }

        }
        return ep;
    }

    /**
     * Return given point - if target edit part is a connection line then return
     * the point on sequence flow line that is closest to given point.
     * 
     * @param absLoc
     * @param ep
     * @return
     */
    public static Point getAdjustedDropLocation(Point absLoc, EditPart ep) {
        if (ep instanceof BaseConnectionEditPart
                && ((BaseConnectionEditPart) ep).getFigure() instanceof PolylineConnection) {
            PolylineConnection fig =
                    (PolylineConnection) ((BaseConnectionEditPart) ep)
                            .getFigure();
            PointList pl = fig.getPoints();

            Point rel = absLoc.getCopy();
            fig.translateToRelative(rel);

            Point closest =
                    XPDLineUtilities.getPolylinePointClosestToPoint(pl, rel);
            fig.translateToAbsolute(closest);

            absLoc = closest;
        }
        return absLoc;
    }

}
