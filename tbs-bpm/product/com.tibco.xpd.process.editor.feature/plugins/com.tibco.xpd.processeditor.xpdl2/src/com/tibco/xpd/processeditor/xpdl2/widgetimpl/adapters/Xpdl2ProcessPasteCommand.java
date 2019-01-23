/**
 * Xpdl2ProcessPasteCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2007
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil.ProjectReference;
import com.tibco.xpd.processwidget.adapters.CopyPasteScope;
import com.tibco.xpd.processwidget.adapters.ProcessPasteCommand;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Xpdl2ProcessPasteCommand
 * 
 * May throw PasteCancelledByUserException
 */
public class Xpdl2ProcessPasteCommand extends ProcessPasteCommand {
    /**
     * Set when user cancels paste of objects by cancelling the Project
     * reference add.
     */
    private boolean pasteCancelledByUser = false;

    /**
     * ActivityAndTransitions
     * 
     * Little data class linking an activity to lists of its incmoing and
     * outgoing transitions.
     * 
     */
    private class ActivityAndTransitions implements
            Comparable<ActivityAndTransitions> {
        private static final String EMPTY_STRING = ""; //$NON-NLS-1$

        Activity activity = null;

        List<Transition> inTrans = Collections.EMPTY_LIST;

        List<Transition> outTrans = Collections.EMPTY_LIST;

        boolean isCompensationAssociationTarget = false;

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        @Override
        public int compareTo(ActivityAndTransitions actTrans) {
            String name1 = activity.getName();
            String name2 = actTrans.activity.getName();

            if (name1 == null) {
                name1 = EMPTY_STRING;
            }

            if (name2 == null) {
                name2 = EMPTY_STRING;
            }

            return name2.compareTo(name1);
        }

    }

    private boolean startEndSetupDone = false;

    private Object startFlowActivity = null;

    private Object endFlowActivity = null;

    private Collection<ProjectReference> listProjectReferencesToAdd;

    private IProject targetProject = null;

    /**
     * Wrap the given command. and provide various info that process widget will
     * require.Does not handle required project references.
     */
    public Xpdl2ProcessPasteCommand(Command cmd, Rectangle occupiedArea,
            CopyPasteScope pasteScope, Collection pasteObjects) {
        super(cmd, occupiedArea, pasteScope, pasteObjects);

    }

    /**
     * Wrap the given command. and provide various info that process widget will
     * require.Use this constructor when required project references should be
     * handled. When required project references list is available, this command
     * will pop up a dialog asking the confirmation to add required project
     * references.Does not paste objects if user cancels it.
     * 
     * @param cmd
     * @param occupiedArea
     * @param pasteScope
     * @param pasteObjects
     * @param refProjectsRequired
     * @param targetProject
     */
    public Xpdl2ProcessPasteCommand(Command cmd, Rectangle occupiedArea,
            CopyPasteScope pasteScope, Collection pasteObjects,
            Collection<ProjectReference> refProjectsRequired,
            IProject targetProject) {
        super(cmd, occupiedArea, pasteScope, pasteObjects);
        listProjectReferencesToAdd = refProjectsRequired;
        this.targetProject = targetProject;
    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.ProcessPasteCommand#execute()
     * 
     */
    @Override
    public void execute() {
        /*
         * XPD-3033 check requirement for new project references and ask user.
         * Check if there is a Projects reference List to check
         */
        // Required project references are available and
        // should be handled,
        if (listProjectReferencesToAdd != null
                && listProjectReferencesToAdd.size() > 0) {
            if (ActionUtil.checkAndAddProjectReference(targetProject,
                    listProjectReferencesToAdd)) {
                super.execute();
            } else {
                // set when, user cancels adding references
                pasteCancelledByUser = true;
                throw new PasteCancelledByUserException();
            }

        } else {
            // otherwise proceed with paste
            super.execute();
        }

    }

    /**
     * @see com.tibco.xpd.processwidget.adapters.ProcessPasteCommand#getAffectedObjects()
     * 
     * @return
     */
    @Override
    public Collection getAffectedObjects() {

        return pasteCancelledByUser ? Collections.EMPTY_LIST : super
                .getAffectedObjects();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.ProcessPasteCommand#getFlowEndActivity
     * ()
     */
    @Override
    public Object getFlowEndActivity() {
        setupFlowStartAndEnd();
        return endFlowActivity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.ProcessPasteCommand#getFlowEndActivityId
     * ()
     */
    @Override
    public String getFlowEndActivityId() {
        setupFlowStartAndEnd();

        if (endFlowActivity instanceof Activity) {
            return ((Activity) endFlowActivity).getId();
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.ProcessPasteCommand#getFlowStartActivity
     * ()
     */
    @Override
    public Object getFlowStartActivity() {
        setupFlowStartAndEnd();
        return startFlowActivity;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.ProcessPasteCommand#
     * getFlowStartActivityId()
     */
    @Override
    public String getFlowStartActivityId() {
        setupFlowStartAndEnd();

        if (startFlowActivity instanceof Activity) {
            return ((Activity) startFlowActivity).getId();
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.ProcessPasteCommand#offsetToLocation
     * (java.lang.Object, org.eclipse.draw2d.geometry.Point)
     */
    @Override
    public void offsetToLocation(Object pasteObject, Point location) {
        Point offset = getOffsetForLocation(pasteObject, location);

        if (offset != null) {

            // Go thru the top level activities / artifacts and offset their
            // positions.
            for (Iterator iter = getPasteObjects().iterator(); iter.hasNext();) {
                Object obj = iter.next();

                if (obj instanceof GraphicalNode) {
                    GraphicalNode node = (GraphicalNode) obj;

                    NodeGraphicsInfo gn =
                            Xpdl2ModelUtil
                                    .getNodeGraphicsInfo((GraphicalNode) obj);
                    if (gn != null) {
                        Coordinates coords = gn.getCoordinates();
                        if (coords != null) {
                            int x = (int) coords.getXCoordinate() - offset.x;
                            coords.setXCoordinate(x);

                            int y = (int) coords.getYCoordinate() - offset.y;
                            coords.setYCoordinate(y);

                        }
                    }
                }
            } // Offset Next object.

            // Finally, change the occupied area to match.
            Rectangle occArea = getOccupiedArea().getCopy();
            occArea.x -= offset.x;
            occArea.y -= offset.y;
            setOccupiedArea(occArea);
        }

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.ProcessPasteCommand#getOffsetForLocation
     * (java.lang.Object, org.eclipse.draw2d.geometry.Point)
     */
    @Override
    public Point getOffsetForLocation(Object pasteObject, Point location) {
        Point offset = null;

        if (getPasteScope().getCopyScope() == CopyPasteScope.COPY_ACTIVITIES_AND_ARTIFACTS
                && pasteObject instanceof GraphicalNode) {

            Rectangle occArea = getOccupiedArea().getCopy();

            // The object in question will be one of the top level activities.
            // Get its location and calculate offset from the top left of
            // bounding rectangle that is the occupied area.
            NodeGraphicsInfo gn =
                    Xpdl2ModelUtil
                            .getNodeGraphicsInfo((GraphicalNode) pasteObject);

            if (gn != null) {
                offset =
                        new Point(gn.getCoordinates().getXCoordinate(), gn
                                .getCoordinates().getYCoordinate());

                offset.x -= occArea.x;
                offset.y -= occArea.y;

                // Make sure that offset will not cause objects to go into
                // negative positions.
                if ((occArea.x - offset.x) < 0) {
                    // Max we can move objects up is space left of occupied
                    // area.
                    offset.x = occArea.x;
                }

                if ((occArea.y - offset.y) < 0) {
                    // Max we can move objects up is space above occupied area.
                    offset.y = occArea.y;

                }
            }
        }

        return offset;
    }

    /**
     * Examine the paste objects and find the start and end activities from a
     * flow perspective.
     * 
     * i.e. STart Activity... the first object without incoming sequence flow.
     * Ignoring events on task borders.
     * 
     * i.e. the first object without outgoing sequence flow that is...
     * Discounting event on task border. Downstream from start object.
     * 
     */
    private void setupFlowStartAndEnd() {
        if (startEndSetupDone) {
            return;

        }
        startEndSetupDone = true;

        startFlowActivity = null;
        endFlowActivity = null;

        if (getPasteScope().getCopyScope() != CopyPasteScope.COPY_ACTIVITIES_AND_ARTIFACTS) {
            // Can only do this for activity level copies.
            return;
        }

        Collection pasteObjects = getPasteObjects();

        // Get a map of Activity Id -> Activity and it's in/out transitions.
        List<ActivityAndTransitions> acts = getSortedActivityList(pasteObjects);

        HashMap<String, ActivityAndTransitions> actMap =
                getActivityIdMap(acts, pasteObjects);

        //
        // Go thru the activities looking for the start object.
        ActivityAndTransitions startAct = null;

        for (ActivityAndTransitions act : acts) {
            //
            // Always ignore event on task border, these are never start of
            // flow.
            if (!Xpdl2ModelUtil.isEventAttachedToTask(act.activity)) {

                // If activity has no incoming transitions then it is the start
                // activity (and has no incoming compensation association)
                if (act.inTrans.isEmpty()
                        && !act.isCompensationAssociationTarget) {
                    startFlowActivity = act.activity;

                    startAct = act;
                }
            }
        }

        /*
         * If we could not find a start activity without incoming transions then
         * allow for the special circumstance that we may be pasting a
         * connection to a new object i.e. a transition whose source is outside
         * of the pasted objects.
         */
        if (startAct == null) {
            for (ActivityAndTransitions act : acts) {
                //
                // Always ignore event on task border, these are never start of
                // flow.
                if (!Xpdl2ModelUtil.isEventAttachedToTask(act.activity)) {

                    // If activity has no incoming compensation association.
                    if (!act.isCompensationAssociationTarget) {
                        // Check if the transition has a source within the
                        // pasted objects.
                        for (Transition t : act.inTrans) {
                            if (!actMap.containsKey(t.getFrom())) {
                                /*
                                 * This transition has a source object outside
                                 * of pasted object content then its probably a
                                 * paste of an object with link from existing
                                 * object.
                                 */
                                startFlowActivity = act.activity;

                                startAct = act;
                                break;
                            }
                        }
                    }
                }
            }

        }

        if (startAct != null) {
            //
            // Find the end activity. This is the first activity that has no
            // outgoing transitions that is downstream from the start activity.
            HashSet<String> alreadyCheckedActs = new HashSet<String>();

            endFlowActivity =
                    findEndFlowActivity(startAct, alreadyCheckedActs, actMap);

        }

        return;

    }

    /**
     * Find the end activity. This is the first activity that has no outgoing
     * transitions that is downstream from the start activity.
     * 
     * (Note, transitions from event on task border will always be ignored
     * because there is no transition link to them from the activity AND because
     * we already checked that the start activity was not an event on task
     * border).
     * 
     * @param startAct
     * @param alreadyCheckedActs
     *            List of activities whose transitions have already been checked
     *            (to prevent infinite looping)
     * @param actMap
     * @return
     */
    private Object findEndFlowActivity(ActivityAndTransitions fromAct,
            HashSet<String> alreadyCheckedActs,
            HashMap<String, ActivityAndTransitions> actMap) {

        if (fromAct.outTrans.isEmpty()) {
            // No outgoing transitions, no need to check any further.
            return fromAct.activity;
        }

        // Add this object to the list of objects already checked (or in process
        // of being checked) to make sure we don't get any loop backs.
        alreadyCheckedActs.add(fromAct.activity.getId());

        // Path - Follow the outgoing transitions

        for (Transition trans : fromAct.outTrans) {

            // Don't follow path into object we have already checked.
            if (!alreadyCheckedActs.contains(trans.getTo())) {

                ActivityAndTransitions nextAct = actMap.get(trans.getTo());

                if (nextAct != null) {
                    // Recurs and check the target activity of transition.
                    Object endAct =
                            findEndFlowActivity(nextAct,
                                    alreadyCheckedActs,
                                    actMap);

                    if (endAct != null) {
                        // Found it!
                        return endAct;
                    }

                    // Not found, try next transition.
                }
            }
        }

        return null;
    }

    private List getSortedActivityList(Collection pasteObjects) {
        ArrayList<ActivityAndTransitions> acts =
                new ArrayList<ActivityAndTransitions>();

        for (Iterator iter = pasteObjects.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof Activity) {
                Activity act = (Activity) obj;

                ActivityAndTransitions actTrans = new ActivityAndTransitions();
                actTrans.activity = act;

                acts.add(actTrans);
            }
        }

        // Make sure list is sorted on object id. That way we are consistent
        // with what we return when re-run.
        Collections.sort(acts);

        return acts;
    }

    /**
     * Build a map of the top level activities keyed on id.
     * 
     * @param pasteObjects
     * 
     * @return Map of Activity Id to Activity object.
     */
    private HashMap<String, ActivityAndTransitions> getActivityIdMap(
            List<ActivityAndTransitions> acts, Collection pasteObjects) {

        HashMap<String, ActivityAndTransitions> actMap =
                new HashMap<String, ActivityAndTransitions>();
        for (ActivityAndTransitions actTrans : acts) {
            actMap.put(actTrans.activity.getId(), actTrans);
        }

        // Grab the list of transitions for each activity.
        for (Iterator iter = pasteObjects.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof Transition) {
                Transition transition = (Transition) obj;

                ActivityAndTransitions src = actMap.get(transition.getFrom());
                if (src != null) {
                    // Add this transition to outgoing list of activity.
                    if (src.outTrans == Collections.EMPTY_LIST) {
                        src.outTrans = new ArrayList<Transition>();
                    }

                    src.outTrans.add(transition);
                }

                ActivityAndTransitions tgt = actMap.get(transition.getTo());
                if (tgt != null) {
                    // Add this transition to incoming list of activity.
                    if (tgt.inTrans == Collections.EMPTY_LIST) {
                        tgt.inTrans = new ArrayList<Transition>();
                    }

                    tgt.inTrans.add(transition);
                }
            } else if (obj instanceof Association) {
                // If an activity has an incoming association from an event on
                // task border (i.e. a compensation association) then count that
                // as an incoming transition - we wouldn't want to consider a
                // compensation task as the start of flow.
                Association ass = (Association) obj;

                ActivityAndTransitions src = actMap.get(ass.getSource());
                if (src != null) {
                    if (Xpdl2ModelUtil.isEventAttachedToTask(src.activity)) {
                        // its an associaiton from a task border event.
                        ActivityAndTransitions tgt =
                                actMap.get(ass.getTarget());

                        if (tgt != null) {
                            tgt.isCompensationAssociationTarget = true;
                        }
                    }
                }
            }
        }

        return actMap;
    }

}
