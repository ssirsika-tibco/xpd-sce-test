/**
 * Copyright TIBCO inc (c) 2007
 */
package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.gef.GraphicalEditPart;

import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.EventEditPart;

/**
 * Show links to referenced / from referencing objects for link events and error
 * events.
 * 
 * @author aallway
 * 
 */
public class EventRefHighlightPolicy extends HighlightReferencesPolicy {

    @Override
    protected ConnectionAnchor getConnectionAnchor(GraphicalEditPart editPart) {
        // Events can only reference events so always an ellipse anchor.
        return new EllipseAnchor(((ShapeWithDescriptionFigure) editPart
                .getFigure()).getShape());
    }

    @Override
    protected Collection<GraphicalEditPart> getReferenceToObjects() {
        EventEditPart hostEventEditPart = (EventEditPart) getHost();

        Collection<GraphicalEditPart> returnEventParts =
                new ArrayList<GraphicalEditPart>();

        EventAdapter thisEventAdp = hostEventEditPart.getEventAdapter();
        if (EventTriggerType.EVENT_LINK_THROW_LITERAL.equals(thisEventAdp
                .getEventTriggerType())) {

            // XPDL21 Does throw link event need to be referenced?
            // Link events can only reference one object as target.
            EventEditPart linkPart = hostEventEditPart.getLinkedToEditPart();
            if (linkPart != null) {
                returnEventParts.add(linkPart);
            }

        } else if (hostEventEditPart.getEventTriggerType() == EventTriggerType.EVENT_ERROR) {
            // We always want reference lines to be FROM throwers TO
            // catchers.

            // Therefore we only need to deal with end events (intermediate
            // errors cannot throw in BPMN 1.2) when
            // returning reference To objects
            if (EventFlowType.FLOW_END == hostEventEditPart.getEventFlowType()) {

                String thrownErrCode = thisEventAdp.getErrorCode();
                if (thrownErrCode != null && thrownErrCode.length() > 0) {
                    // Get a list of all error events in same process.
                    Collection<BaseGraphicalEditPart> events =
                            EditPartUtil
                                    .getAllActivitiesInProcess(hostEventEditPart
                                            .getParentProcess(),
                                            EditPartUtil.ACTIVITY_FILTER_EVENTS);

                    for (BaseGraphicalEditPart ep : events) {
                        if (ep instanceof EventEditPart) {
                            EventEditPart event = (EventEditPart) ep;

                            // Only interested in error events on task
                            // border (catchers)
                            if (event.getEventTriggerType() == EventTriggerType.EVENT_ERROR
                                    && EventFlowType.FLOW_INTERMEDIATE == event
                                            .getEventFlowType()) {
                                String caughtErrCode =
                                        event.getEventAdapter().getErrorCode();

                                // thrown event is caught by any catch that
                                // has same error code or no error code at
                                // all.
                                if (caughtErrCode == null
                                        || caughtErrCode.length() == 0
                                        || thrownErrCode.equals(caughtErrCode)) {
                                    returnEventParts.add(event);
                                }
                            }
                        }
                    }
                }
            }

        } else if (hostEventEditPart.getEventTriggerType() == EventTriggerType.EVENT_SIGNAL_THROW) {
            // We always want reference lines to be FROM throwers TO
            // catchers.

            String signalName = thisEventAdp.getSignalName();
            if (signalName != null && signalName.length() > 0) {
                // Get a list of all error events in same process.
                Collection<BaseGraphicalEditPart> events =
                        EditPartUtil
                                .getAllActivitiesInProcess(hostEventEditPart
                                        .getParentProcess(),
                                        EditPartUtil.ACTIVITY_FILTER_EVENTS);

                for (BaseGraphicalEditPart ep : events) {
                    if (ep instanceof EventEditPart) {
                        EventEditPart event = (EventEditPart) ep;

                        // Only interested in error events on task
                        // border (catchers)
                        if (event.getEventTriggerType() == EventTriggerType.EVENT_SIGNAL_CATCH) {
                            String caughtSignalName =
                                    event.getEventAdapter().getSignalName();

                            // thrown event is caught by any catch that
                            // has same error code or no error code at
                            // all.
                            if (caughtSignalName == null
                                    || caughtSignalName.length() == 0
                                    || signalName.equals(caughtSignalName)) {
                                returnEventParts.add(event);
                            }
                        }
                    }
                }
            }
        }

        return returnEventParts;
    }

    @Override
    protected Collection<GraphicalEditPart> getReferencedFromObjects() {
        EventEditPart hostEventEditPart = (EventEditPart) getHost();

        Collection<GraphicalEditPart> returnEventParts =
                new ArrayList<GraphicalEditPart>();

        EventAdapter thisEventAdp = hostEventEditPart.getEventAdapter();
        if (EventTriggerType.EVENT_LINK_CATCH_LITERAL.equals(thisEventAdp
                .getEventTriggerType())) {
            // Return the link events that reference this event
            // Get a list of all error events in same process.
            Collection<BaseGraphicalEditPart> events =
                    EditPartUtil.getAllActivitiesInProcess(hostEventEditPart
                            .getParentProcess(),
                            EditPartUtil.ACTIVITY_FILTER_EVENTS);

            String thisId = thisEventAdp.getId();

            for (BaseGraphicalEditPart ep : events) {
                if (ep instanceof EventEditPart) {
                    EventEditPart event = (EventEditPart) ep;

                    if (EventTriggerType.EVENT_LINK_THROW == event
                            .getEventTriggerType()) {
                        String linkId =
                                event.getEventAdapter().getLinkEventId();

                        if (thisId.equals(linkId)) {
                            returnEventParts.add(event);
                        }
                    }
                }
            }

        } else if (hostEventEditPart.getEventTriggerType() == EventTriggerType.EVENT_ERROR) {
            // We always want reference lines to be FROM throwers TO
            // catchers.

            // Therefore when returning reference From objects we only need
            // to bother if we are on a catcher (always an intermediate event in
            // 1.2)
            if (EventFlowType.FLOW_INTERMEDIATE == hostEventEditPart
                    .getEventFlowType()) {

                String caughtErrCode = thisEventAdp.getErrorCode();

                // Get a list of all error events in same process.
                Collection<BaseGraphicalEditPart> events =
                        EditPartUtil
                                .getAllActivitiesInProcess(hostEventEditPart
                                        .getParentProcess(),
                                        EditPartUtil.ACTIVITY_FILTER_EVENTS);

                for (BaseGraphicalEditPart ep : events) {
                    if (ep instanceof EventEditPart) {
                        EventEditPart event = (EventEditPart) ep;

                        // Only interested in error end events (throwers)
                        if (event.getEventTriggerType() == EventTriggerType.EVENT_ERROR
                                && EventFlowType.FLOW_END == event
                                        .getEventFlowType()) {
                            String thrownErrCode =
                                    event.getEventAdapter().getErrorCode();

                            if (thrownErrCode != null
                                    && thrownErrCode.length() != 0) {
                                // Catch event catches either a specific
                                // error (if error code specified) or all
                                // errors (if no code specified.
                                if (caughtErrCode == null
                                        || caughtErrCode.length() == 0
                                        || thrownErrCode.equals(caughtErrCode)) {
                                    returnEventParts.add(event);
                                }
                            }
                        }
                    }
                }
            }

        } else if (hostEventEditPart.getEventTriggerType() == EventTriggerType.EVENT_SIGNAL_CATCH) {
            // We always want reference lines to be FROM throwers TO
            // catchers.

            // Therefore when returning reference From objects we only need
            // to bother if we are on a catcher.
            String caughtSignalName = thisEventAdp.getSignalName();

            // Get a list of all signal events in same process.
            Collection<BaseGraphicalEditPart> events =
                    EditPartUtil.getAllActivitiesInProcess(hostEventEditPart
                            .getParentProcess(),
                            EditPartUtil.ACTIVITY_FILTER_EVENTS);

            for (BaseGraphicalEditPart ep : events) {
                if (ep instanceof EventEditPart) {
                    EventEditPart event = (EventEditPart) ep;

                    // Only interested in error events not not on task
                    // border (throwers)
                    if (event.getEventTriggerType() == EventTriggerType.EVENT_SIGNAL_THROW) {
                        String thrownSignalName =
                                event.getEventAdapter().getSignalName();

                        if (thrownSignalName != null
                                && thrownSignalName.length() != 0) {
                            // Catch event catches either a specific
                            // error (if error code specified) or all
                            // errors (if no code specified.
                            if (caughtSignalName == null
                                    || caughtSignalName.length() == 0
                                    || thrownSignalName
                                            .equals(caughtSignalName)) {
                                returnEventParts.add(event);
                            }
                        }
                    }
                }
            }
        }

        return returnEventParts;
    }

}
