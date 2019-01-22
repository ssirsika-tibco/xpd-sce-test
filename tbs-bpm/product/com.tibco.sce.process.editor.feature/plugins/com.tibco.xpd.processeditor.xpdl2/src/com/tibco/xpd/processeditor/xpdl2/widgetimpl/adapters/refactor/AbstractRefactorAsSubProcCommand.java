/**
 * RefactorAsEmbeddedSubProcHelper.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract Command Class for Refactor to SubProcess providing basic refactor
 * support. The Sub classes should provide appropriate implementation of the
 * abstract class for the specific kind of Refactor they represent.
 * 
 */
public abstract class AbstractRefactorAsSubProcCommand implements Command {

    // Amount of extra space around content selected for sub-proc.
    private static final int CONTENT_MARGIN = 20;

    // How much bigger subproc needs to be than content.
    private static final int SUBPROC_EXTRA_CX = 10;

    private static final int SUBPROC_EXTRA_CY = 21;

    // Command delegate.
    private CompoundCommand delegateCmd = null;

    // The new sub-process object we'll be creating.
    protected Activity newSubProcActivity = null;

    // The activity set for the new sub-process.
    private ActivitySet newActivitySet = null;

    // Editing domain providing model edit framework.
    EditingDomain editingDomain = null;

    // Map of object ID to object adapter
    private HashMap<String, EObject> objectMap = null;

    // List of object adapters.
    private List<EObject> objectList = null;

    // Validation was previously attempted.
    private boolean validationAttempted = false;

    // Result of previous validation.
    private int validationResult = 0;

    // The flow objects in selection that are connected from objects
    // outside of selection.
    private List<Activity> entryPathTargets = null;

    // Objects that have no incoming flow (i.e. potential start objects).
    private List<Activity> nonFlowEntryPathTargets = null;

    // Already existing start event object (or new one once command is
    // underway).
    private Activity startEventObject = null;

    // The flow objects in selection that are connected to objects outside of
    // selection
    private List<Activity> exitPathSources = null;

    // The flow objects with no outgoing flow.
    private List<Activity> nonFlowExitPathSources = null;

    // Already existing start event object (or new one once command is
    // underway).
    private Activity endEventObject = null;

    // The parent container (lane / sub-process) of the objects
    // selected (and hence the container of the new sub-process.
    private EObject parentContainer = null;

    // The bounds of the content to place in the new sub-process
    private Rectangle contentBounds;

    // Info for new sub-process...
    private RefactorAsSubProcWizardInfo refactorInfo;

    private ProcessWidgetType processType = ProcessWidgetType.BPMN_PROCESS;

    protected ProcessFlowAnalyser flowAnalyzer;

    /**
     * Prepares refactor of the given objects into an sub-proc.
     * 
     * @param objects
     *            List of model activities and artifacts to refactor into
     *            sub-proc.
     */
    public AbstractRefactorAsSubProcCommand(EditingDomain editingDomain,
            List<Object> objects, DiagramModelImageProvider imageProvider) {
        this.editingDomain = editingDomain;

        refactorInfo = new RefactorAsSubProcWizardInfo();
        refactorInfo.imageProvider = imageProvider;

        // Create our delegate command.
        delegateCmd = new CompoundCommand();

        delegateCmd.setLabel(getCommandLabel());

        // Keep track of top left object.
        contentBounds =
                new Rectangle(Integer.MAX_VALUE, Integer.MAX_VALUE,
                        Integer.MIN_VALUE, Integer.MIN_VALUE);

        // Construct a map and a list of object ID to model object
        objectMap = new HashMap<String, EObject>(objects.size());

        objectList = new ArrayList<EObject>(objects.size());
        /*
         * Do some implicit selection of objects based on the explicitly
         * selected objects for to include in refactor.
         */
        objects = selectRelatedObjects(objects);

        Point bottomRight = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (Object obj : objects) {
            String id = null;

            Rectangle objBounds = null;

            if (obj instanceof Activity) {
                Activity act = (Activity) obj;

                id = act.getId();

                org.eclipse.swt.graphics.Rectangle tmpBnds =
                        Xpdl2ModelUtil.getObjectBounds(act);
                objBounds =
                        new Rectangle(tmpBnds.x, tmpBnds.y, tmpBnds.width,
                                tmpBnds.height);
                processType = TaskObjectUtil.getProcessType(act.getProcess());

            } else if (obj instanceof Artifact) {
                Artifact art = (Artifact) obj;

                id = art.getId();

                org.eclipse.swt.graphics.Rectangle tmpBnds =
                        Xpdl2ModelUtil.getObjectBounds(art);
                objBounds =
                        new Rectangle(tmpBnds.x, tmpBnds.y, tmpBnds.width,
                                tmpBnds.height);

            } else {
                throw new UnsupportedOperationException(
                        "Attempt to refactor non activity/artifact object into sub-proc"); //$NON-NLS-1$
            }

            // Store in map and list.
            objectMap.put(id, (EObject) obj);

            objectList.add((EObject) obj);

            if (!Xpdl2ModelUtil.isEventAttachedToTask(obj)) {
                // Keep track of required position / size of
                // sub-process..

                if (objBounds.x < contentBounds.x) {
                    contentBounds.x = objBounds.x;
                }

                if (objBounds.y < contentBounds.y) {
                    contentBounds.y = objBounds.y;
                }

                if (objBounds.right() > bottomRight.x) {
                    bottomRight.x = objBounds.right();
                }

                if (objBounds.bottom() > bottomRight.y) {
                    bottomRight.y = objBounds.bottom();
                }
            }
        }

        /* Sort out the new sub-proc bounding rectangle. */
        contentBounds.width = bottomRight.x - contentBounds.x;
        contentBounds.height = bottomRight.y - contentBounds.y;

        /* Precreate the sub-proc and activity set */
        preCreateEmbeddedSubProc();

    }

    /**
     * This method allows implicitly selecting additional objects for refactor,
     * related to the actually selected Objects.Subclasess can provide their
     * implementation for such additional implicit selection.Default does
     * nothing and returns the same list which is passed.
     * 
     * @param objects
     * @return List of Implicitly and explicitly selected objects for refactor.
     */
    protected List<Object> selectRelatedObjects(List<Object> objects) {
        /* does nothing */
        return objects;
    }

    /**
     * Validates that the refactor to sub-proc can be performed for selected
     * objects.
     * <p>
     * On failure returns the reason for the failure.
     * </p>
     * 
     * @return RefactorAsSubProcWizardInfo.SUBPROC_xxxx flags or -1 on fatal
     *         error.
     */
    private int validateRefactor() {
        // Only need to do validation once.
        if (validationAttempted) {
            return validationResult;
        }

        validationAttempted = true;

        validationResult = 0;

        if (!checkContainment()) {
            validationResult = -1;

        } else {
            validationResult |= checkIncomingFlows();

            validationResult |= checkOutgoingFlows();
        }

        return validationResult;
    }

    /**
     * Return the command that will refactor selected objects into subprocess.
     * 
     * @return
     */
    protected Command getRefactorCommand() {
        Command retCmd = UnexecutableCommand.INSTANCE;

        CompoundCommand cmd = new CompoundCommand();
        retCmd = cmd;

        /*
         * Setup new sub-process activity and it's activity set...
         */
        setupSubProc(cmd);

        /*
         * If we found a definite start object then reconnect it's incoming
         * transitions to new sub-proc.
         */
        relinkEntryPoint(cmd);

        /*
         * If we found a definite end object then reconnect it's outgoing
         * transitions from new-sub-proc.
         */

        relinkExitPoint(cmd);

        /*
         * Move selected objects and all transitions between them into new
         * activity set. (offset positions to reoriganise them within sub-proc
         * ).
         */

        moveContentToSubProc(cmd);

        /*
         * If required, add the start event and connect it to entry point
         * object.
         */
        addStartEvent(cmd);

        /* If required, add end events to exit point objects. */
        addEndEvent(cmd);

        return retCmd;
    }

    /**
     * Create and add a start event if necessary.
     * 
     * Join the start event to all entry path objects.
     * 
     * @param cmd
     */
    @SuppressWarnings("rawtypes")
    protected void addStartEvent(CompoundCommand cmd) {

        /* If user has asked to create start event then do so. */
        if (startEventObject == null
                && (refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_CREATE_STARTEVENT) != 0) {
            WidgetRGB fillColor =
                    ProcessWidgetColors.getInstance(processType)
                            .getGraphicalNodeColor(null,
                                    ProcessWidgetColors.START_EVENT_FILL);
            WidgetRGB lineColor =
                    ProcessWidgetColors.getInstance(processType)
                            .getGraphicalNodeColor(null,
                                    ProcessWidgetColors.START_EVENT_LINE);

            Dimension size =
                    new Dimension(ProcessWidgetConstants.START_EVENT_SIZE,
                            ProcessWidgetConstants.START_EVENT_SIZE);

            Point loc =
                    new Point(
                            contentBounds.x - (3 * size.width),
                            contentBounds.y
                                    + (ProcessWidgetConstants.TASK_HEIGHT_SIZE / 2)
                                    - 1);

            startEventObject =
                    ElementsFactory.createEvent(loc,
                            size,
                            null,
                            EventFlowType.FLOW_START_LITERAL,
                            EventTriggerType.EVENT_NONE_LITERAL,
                            fillColor.toString(),
                            lineColor.toString(),
                            null);
            /*
             * XPD-6485 - make activity name unique.
             */
            setUniqueNameToActivity(startEventObject, cmd);

            cmd.append(AddCommand.create(editingDomain,
                    newActivitySet,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                    startEventObject));
        }

        /*
         * At this point we either have an existing start event object, or a new
         * one or none at all.
         */
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE);

        /*
         * If we have a start event object, join all the entry path objects
         * (that we have either cut the connection to or didn't have one in the
         * first place).
         */
        if (startEventObject != null) {
            /* First for objects we have cut connections to. */
            for (Iterator iter = entryPathTargets.iterator(); iter.hasNext();) {
                Activity activity = (Activity) iter.next();

                /*
                 * Don't connect to something that is already a start event!
                 */Event ev = activity.getEvent();
                if (!(ev instanceof StartEvent)) {

                    Transition trans =
                            ElementsFactory.createTransition(startEventObject,
                                    activity,
                                    SequenceFlowType.UNCONTROLLED_LITERAL,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    lineColor.toString());

                    cmd.append(AddCommand.create(editingDomain,
                            newActivitySet,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions(),
                            trans));

                }
            }

            /*
             * Then the objects that didn't have incoming flow in the first
             * place.
             */
            for (Iterator iter = nonFlowEntryPathTargets.iterator(); iter
                    .hasNext();) {
                Activity activity = (Activity) iter.next();

                /*
                 * Don't connect to something that is already a start event!
                 */
                Event ev = activity.getEvent();
                if (!(ev instanceof StartEvent)) {

                    Transition trans =
                            ElementsFactory.createTransition(startEventObject,
                                    activity,
                                    SequenceFlowType.UNCONTROLLED_LITERAL,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    lineColor.toString());

                    cmd.append(AddCommand.create(editingDomain,
                            newActivitySet,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions(),
                            trans));
                }
            }

        }

    }

    /**
     * Create and add an end event if necessary.
     * 
     * Join the all exit path objects to the end event
     * 
     * @param cmd
     */
    @SuppressWarnings("rawtypes")
    protected void addEndEvent(CompoundCommand cmd) {

        /* If user has asked to create end event then do so. */
        if (endEventObject == null
                && (refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_CREATE_ENDEVENT) != 0) {
            WidgetRGB fillColor =
                    ProcessWidgetColors.getInstance(processType)
                            .getGraphicalNodeColor(null,
                                    ProcessWidgetColors.END_EVENT_FILL);
            WidgetRGB lineColor =
                    ProcessWidgetColors.getInstance(processType)
                            .getGraphicalNodeColor(null,
                                    ProcessWidgetColors.END_EVENT_LINE);

            Dimension size =
                    new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                            ProcessWidgetConstants.END_EVENT_SIZE);

            Point loc =
                    new Point(
                            contentBounds.right() + (3 * size.width),
                            contentBounds.y
                                    + (ProcessWidgetConstants.TASK_HEIGHT_SIZE / 2)
                                    - 1);

            endEventObject =
                    ElementsFactory.createEvent(loc,
                            size,
                            null,
                            EventFlowType.FLOW_END_LITERAL,
                            EventTriggerType.EVENT_NONE_LITERAL,
                            fillColor.toString(),
                            lineColor.toString(),
                            null);
            /*
             * XPD-6485 - make activity name unique.
             */
            setUniqueNameToActivity(endEventObject, cmd);

            cmd.append(AddCommand.create(editingDomain,
                    newActivitySet,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                    endEventObject));
        }

        /*
         * At this point we either have an existing end event object, or a new
         * one or none at all.
         */
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE);

        /*
         * If we have a start event object, join all the exit path objects (that
         * we have either cut the connection to or didn't have one in the first
         * place).
         */
        if (endEventObject != null) {
            // First for objects we have cut connections to.
            for (Iterator iter = exitPathSources.iterator(); iter.hasNext();) {
                Activity activity = (Activity) iter.next();

                /*
                 * Don't connect to something that is already a end event!
                 */
                Event ev = activity.getEvent();
                if (!(ev instanceof EndEvent)) {

                    Transition trans =
                            ElementsFactory.createTransition(activity,
                                    endEventObject,
                                    SequenceFlowType.UNCONTROLLED_LITERAL,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    lineColor.toString());

                    cmd.append(AddCommand.create(editingDomain,
                            newActivitySet,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions(),
                            trans));

                }
            }

            /*
             * Then the objects that didn't have outgoing flow in the first
             * place.
             */
            for (Iterator iter = nonFlowExitPathSources.iterator(); iter
                    .hasNext();) {
                Activity activity = (Activity) iter.next();

                /*
                 * Don't connect to something that is already a end event!
                 */
                Event ev = activity.getEvent();
                if (!(ev instanceof EndEvent)) {

                    Transition trans =
                            ElementsFactory.createTransition(activity,
                                    endEventObject,
                                    SequenceFlowType.UNCONTROLLED_LITERAL,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    lineColor.toString());

                    cmd.append(AddCommand.create(editingDomain,
                            newActivitySet,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions(),
                            trans));
                }
            }

        }

    }

    /**
     * Sets the name of the passed {@link Activity} unique
     * 
     * @param activity
     *            the activity whose name is to be made unique
     * @param cmd
     */
    private void setUniqueNameToActivity(Activity activity, CompoundCommand cmd) {

        /* get the process */
        Process process = Xpdl2ModelUtil.getProcess(parentContainer);

        if (process != null) {
            /* get unique label */
            String uniqueLabel =
                    Xpdl2ModelUtil.getUniqueLabelInSet(Xpdl2ModelUtil
                            .getDisplayName(activity), Xpdl2ModelUtil
                            .getAllActivitiesInProc(process));
            /*
             * get name from label
             */
            String internalName = NameUtil.getInternalName(uniqueLabel, false);
            /*
             * set display name
             */
            cmd.append(SetCommand
                    .create(editingDomain,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            uniqueLabel));
            /*
             * set name
             */
            cmd.append(SetCommand.create(editingDomain,
                    activity,
                    Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                    internalName));
        }
    }

    /**
     * Move all the given objects into the sub-process along with any
     * transitions between them.
     * 
     * @param cmd
     */
    @SuppressWarnings("rawtypes")
    private void moveContentToSubProc(CompoundCommand cmd) {

        /* Work out how much we need to shift content objects by. */
        // int xOffset = -contentBounds.x;
        // int yOffset = -contentBounds.y;
        //
        // xOffset += CONTENT_MARGIN;
        // yOffset += CONTENT_MARGIN;

        // if (addStartEvent) {
        // xOffset += INSERT_EVENT_EXTRA_CX;
        // }

        for (EObject obj : objectList) {

            NodeGraphicsInfo gi = null;

            if (obj instanceof Activity) {
                Activity act = (Activity) obj;

                gi = Xpdl2ModelUtil.getNodeGraphicsInfo(act);

                /* Remove the object from the old container. */
                cmd.append(RemoveCommand.create(editingDomain, act));

                cmd.append(SetCommand.create(editingDomain,
                        gi,
                        Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_LaneId(),
                        null));

                /* Move it. */
                // if (gi != null) {
                // offsetContentObject(cmd, xOffset, yOffset, gi);
                // }

                /* Add it to the new activity set */
                cmd.append(AddCommand.create(editingDomain,
                        newActivitySet,
                        Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                        act));

                /*
                 * Move transitions from current container into the activity set
                 * (Must only move each transition ONCE so we will ONLY deal
                 * with outgoing transitions (one object's incoming must be
                 * another's outgoing so don't need to do both anyway)
                 */

                List transSet = act.getOutgoingTransitions();

                for (Iterator iter = transSet.iterator(); iter.hasNext();) {
                    Transition transition = (Transition) iter.next();

                    /*
                     * Only deal with transitions between objects in selected
                     * set.
                     */
                    if (objectMap.containsKey(transition.getFrom())
                            && objectMap.containsKey(transition.getTo())) {

                        cmd.append(RemoveCommand.create(editingDomain,
                                transition));

                        cmd.append(AddCommand.create(editingDomain,
                                newActivitySet,
                                Xpdl2Package.eINSTANCE
                                        .getFlowContainer_Transitions(),
                                transition));
                    }
                }

            } else if (obj instanceof Artifact) {
                /* Artifacts stay at package level. */
                Artifact art = (Artifact) obj;

                gi = Xpdl2ModelUtil.getNodeGraphicsInfo(art);

                /* Move it. */
                // if (gi != null) {
                // offsetContentObject(cmd, xOffset, yOffset, gi);
                // }

                /*
                 * Artifacts declare their parents via the graphics info lane id
                 * attribute.
                 */
                cmd.append(SetCommand.create(editingDomain,
                        gi,
                        Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_LaneId(),
                        newActivitySet.getId()));

                /* Move it. */
                // if (gi != null) {
                // offsetContentObject(cmd, xOffset, yOffset, gi);
                // }

            }
        }

    }

    /**
     * If we found a definite start object then reconnect it's incoming
     * transitions to new sub-proc.
     * 
     * @param cmd
     */
    @SuppressWarnings("rawtypes")
    private void relinkEntryPoint(CompoundCommand cmd) {
        if (entryPathTargets.size() > 0) {
            /*
             * Redirect the entry point object's incmoing transitions to new
             * sub-proc.
             */
            for (Iterator actIter = entryPathTargets.iterator(); actIter
                    .hasNext();) {
                Activity activity = (Activity) actIter.next();

                List inTransitions = activity.getIncomingTransitions();

                for (Iterator transIter = inTransitions.iterator(); transIter
                        .hasNext();) {
                    Transition trans = (Transition) transIter.next();

                    /*
                     * Don't relink incoming transitions that are from objects
                     * in the selection.
                     */
                    if (!objectMap.containsKey(trans.getFrom())) {

                        cmd.append(SetCommand.create(editingDomain,
                                trans,
                                Xpdl2Package.eINSTANCE.getTransition_To(),
                                newSubProcActivity.getId()));

                        /*
                         * Remove the fixed-pos end anchor (i.e. use default
                         * anchor positioning)
                         */
                        ConnectorGraphicsInfo gConnectorInfo =
                                Xpdl2ModelUtil.getConnectorGraphicsInfo(trans,
                                        Xpdl2ModelUtil.END_ANCHOR_IDSUFFIX);

                        if (gConnectorInfo != null) {
                            cmd.append(RemoveCommand.create(editingDomain,
                                    gConnectorInfo));
                        }
                    }
                }
            }
        }
    }

    /**
     * If we found a definite end object then reconnect it's outgoing
     * transitions from new-sub-proc.
     * 
     * @param cmd
     */
    @SuppressWarnings("rawtypes")
    private void relinkExitPoint(CompoundCommand cmd) {
        if (exitPathSources.size() > 0) {

            for (Iterator actTrans = exitPathSources.iterator(); actTrans
                    .hasNext();) {
                Activity activity = (Activity) actTrans.next();

                /*
                 * Redirect the exit point object's outgoing transitions to new
                 * sub-proc.
                 */
                List outTransitions = activity.getOutgoingTransitions();

                for (Iterator transIter = outTransitions.iterator(); transIter
                        .hasNext();) {
                    Transition trans = (Transition) transIter.next();

                    /*
                     * Don't relink outgoing transitions that are to objects in
                     * the selection.
                     */
                    if (!objectMap.containsKey(trans.getTo())) {

                        cmd.append(SetCommand.create(editingDomain,
                                trans,
                                Xpdl2Package.eINSTANCE.getTransition_From(),
                                newSubProcActivity.getId()));

                        /*
                         * Remove the fixed-pos start anchor (i.e. use default
                         * anchor positioning)
                         */
                        ConnectorGraphicsInfo gConnectorInfo =
                                Xpdl2ModelUtil.getConnectorGraphicsInfo(trans,
                                        Xpdl2ModelUtil.START_ANCHOR_IDSUFFIX);

                        if (gConnectorInfo != null) {
                            cmd.append(RemoveCommand.create(editingDomain,
                                    gConnectorInfo));
                        }
                    }
                }
            }
        }
    }

    /**
     * Create the new sub-process and its activity set.
     * 
     * @return
     */
    private void preCreateEmbeddedSubProc() {
        newActivitySet = Xpdl2Factory.eINSTANCE.createActivitySet();

        WidgetRGB fillColor =
                ProcessWidgetColors
                        .getInstance(processType)
                        .getGraphicalNodeColor(null,
                                ProcessWidgetColors.EMBEDDEDSUBPROCESS_TASK_FILL);
        WidgetRGB lineColor =
                ProcessWidgetColors
                        .getInstance(processType)
                        .getGraphicalNodeColor(null,
                                ProcessWidgetColors.EMBEDDEDSUBPROCESS_TASK_LINE);

        newSubProcActivity = createSubProcTask(fillColor, lineColor);

        // Set the content activity set reference
        BlockActivity ba = newSubProcActivity.getBlockActivity();
        ba.setActivitySetId(newActivitySet.getId());

        return;
    }

    /**
     * Create sub-process task. Override this method in sub-classes if you want
     * to create a task other than an sub-process.
     * 
     * @param lineColor
     * @param fillColor
     * @param contentBounds2
     * @return new Sub Process Task Activity.
     */
    abstract protected Activity createSubProcTask(WidgetRGB fillColor,
            WidgetRGB lineColor);

    /**
     * Setup the new sub-process and append necessary commands to cmd.
     * 
     * 
     * @return
     */
    private void setupSubProc(CompoundCommand cmd) {

        /* Add the activity set. */
        Process process = Xpdl2ModelUtil.getProcess(parentContainer);

        cmd.append(AddCommand.create(editingDomain,
                process,
                Xpdl2Package.eINSTANCE.getProcess_ActivitySets(),
                newActivitySet));

        /* Lane Id is null if container is not a lane. */
        String laneId = null;
        if (parentContainer instanceof Lane) {
            laneId = ((Lane) parentContainer).getId();
        }

        NodeGraphicsInfo gi =
                Xpdl2ModelUtil.getNodeGraphicsInfo(newSubProcActivity);

        gi.setLaneId(laneId);

        /*
         * Add a reasonable margin around area occupied by content. We don't
         * have to worry too much because process widget tides things up for us
         * anyway after this command is run.
         */
        Rectangle spBounds = contentBounds.getCopy();
        spBounds.x -= SUBPROC_EXTRA_CX + CONTENT_MARGIN;
        spBounds.y -= SUBPROC_EXTRA_CY + CONTENT_MARGIN;

        if (spBounds.x < 5) {
            spBounds.x = 5;
        }
        if (spBounds.y < 5) {
            spBounds.y = 5;
        }

        Coordinates coords = Xpdl2Factory.eINSTANCE.createCoordinates();
        Point centre = spBounds.getCenter();
        coords.setXCoordinate(centre.x);
        coords.setYCoordinate(centre.y);
        gi.setCoordinates(coords);

        gi.setWidth(spBounds.width);
        gi.setHeight(spBounds.height);

        newSubProcActivity.setName(NameUtil
                .getInternalName(refactorInfo.subprocName, false));

        Xpdl2ModelUtil.setOtherAttribute(newSubProcActivity,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                refactorInfo.subprocName);

        if ((refactorInfo.validationInfo & RefactorAsSubProcWizardInfo.SUBPROC_IS_TRANSACTION) != 0) {
            newSubProcActivity.setIsATransaction(true);
        }

        /* Add command to add new task to process or activity set. */
        if (parentContainer instanceof Lane) {
            cmd.append(AddCommand.create(editingDomain,
                    Xpdl2ModelUtil.getProcess(parentContainer),
                    Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                    newSubProcActivity));

        } else {
            /* Activity set. */
            cmd.append(AddCommand.create(editingDomain,
                    parentContainer,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                    newSubProcActivity));

        }

        return;
    }

    /**
     * Check rules on incoming flows.
     * <p>
     * <li>Only one object can have incoming transitions from outside of
     * selection.</li>
     * </p>
     * 
     * @return RefactorAsSubProcWizardInfo.SUBPROC_xxxx flags.
     */
    @SuppressWarnings("rawtypes")
    protected int checkIncomingFlows() {
        int ret = 0;

        nonFlowEntryPathTargets = new ArrayList<Activity>();

        entryPathTargets = new ArrayList<Activity>();

        refactorInfo.entryPathActsAndTrans.clear();

        /*
         * Find the start object (object with incoming transition from outside
         * of selection).
         */
        for (EObject nodeObject : objectList) {

            if (nodeObject instanceof Activity) {
                Activity activity = (Activity) nodeObject;

                /* If this object is a start event */
                Event ev = activity.getEvent();
                if (ev instanceof StartEvent) {
                    startEventObject = activity;
                    ret |=
                            RefactorAsSubProcWizardInfo.SUBPROC_EXISTING_STARTEVENT;
                }

                List inFlows = activity.getIncomingTransitions();

                if (inFlows != null && inFlows.size() > 0) {
                    /* Check sequence flows that this object is the target of... */
                    for (Iterator iter = inFlows.iterator(); iter.hasNext();) {
                        Transition flow = (Transition) iter.next();

                        Activity sourceNode =
                                flow.getFlowContainer()
                                        .getActivity(flow.getFrom());

                        if (sourceNode != null) {
                            /*
                             * If the source object is NOT within selected
                             * objects then this is the start object.
                             */
                            if (!objectMap.containsKey(sourceNode.getId())) {
                                /* Only add once. */
                                if (!entryPathTargets.contains(activity)
                                        && shouldConnectToIncomingFlow()) {
                                    entryPathTargets.add(activity);

                                    refactorInfo.entryPathActsAndTrans
                                            .add(flow);
                                    refactorInfo.entryPathActsAndTrans
                                            .add(activity);
                                }

                            }
                        }

                    }

                } else {
                    /*
                     * This sequenceflow capable object has no incoming flows.
                     * This 'may' become the start object
                     */

                    /*
                     * Unless it is an event attached to task border and the
                     * task in question is also in the selection.
                     */
                    boolean isBorderEvent = false;

                    if (activity.getEvent() instanceof IntermediateEvent) {
                        String taskId =
                                ((IntermediateEvent) activity.getEvent())
                                        .getTarget();

                        if (taskId != null && taskId.length() > 0) {
                            /*
                             * Its an event attached to task border. Finally
                             * check if given task is in selection.
                             */
                            if (objectMap.containsKey(taskId)) {
                                isBorderEvent = true;
                            }
                        }
                    }

                    /*
                     * XPD-7400: Do not attached incoming paths to both Boundary
                     * events as well as event handler activities.
                     */
                    if (!isBorderEvent
                            && !Xpdl2ModelUtil.isEventHandlerActivity(activity)) {
                        nonFlowEntryPathTargets.add(activity);
                        refactorInfo.entryPathActsAndTrans.add(activity);
                    }
                }
            }
        }

        if (entryPathTargets.size() > 1) {
            /* There are multiple potential entry points. */
            ret = RefactorAsSubProcWizardInfo.SUBPROC_MULTIPLE_ENTRYPATHS;

        } else if (entryPathTargets.size() > 0
                && nonFlowEntryPathTargets.size() > 0) {
            /*
             * there is at least one definitive entry path + other unconnected
             * ones
             */
            ret = RefactorAsSubProcWizardInfo.SUBPROC_MULTIPLE_ENTRYPATHS;
        } else if (entryPathTargets.size() == 1) {
            /*
             * If there is ONE definitive incoming path then default to creating
             * start event.
             */
            ret = RefactorAsSubProcWizardInfo.SUBPROC_CREATE_STARTEVENT;
        }

        return ret;
    }

    /**
     * @return
     */
    protected boolean shouldConnectToIncomingFlow() {
        return true;
    }

    /**
     * Check rules on outgoing flows.
     * <p>
     * <li>Only one object can have outgoing transitions to outside of
     * selection.</li>
     * </p>
     * 
     * @return RefactorAsSubProcWizardInfo.SUBPROC_xxxx flags.
     */
    @SuppressWarnings("rawtypes")
    protected int checkOutgoingFlows() {
        int ret = 0;
        nonFlowExitPathSources = new ArrayList<Activity>();

        exitPathSources = new ArrayList<Activity>();

        refactorInfo.exitPathActsAndTrans.clear();

        /*
         * Find the end object (object with outgoing transition from outside of
         * selection).
         */
        for (EObject nodeObject : objectList) {

            if (nodeObject instanceof Activity) {
                Activity activity = (Activity) nodeObject;

                /* If this object is a end event the store it */
                Event ev = activity.getEvent();
                /*
                 * XPD-7400: Only connect to the existing end event if it is not
                 * attached to event handler flow or to an boundary event..
                 */
                if (ev instanceof EndEvent
                        && !AbstractRefactorActivitiesAsProcessCommand
                                .isEventConnectedToSelectedBoundaryEvent(activity,
                                        objectMap)
                        && !isEventHandlerFlowWithAllUpStreamElementsSelected(activity)) {
                    endEventObject = activity;
                    ret |=
                            RefactorAsSubProcWizardInfo.SUBPROC_EXISTING_ENDEVENT;
                }

                List inFlows = activity.getOutgoingTransitions();

                if (inFlows != null && inFlows.size() > 0) {

                    /* Check sequence flows that this object is the source of... */
                    for (Iterator iter = inFlows.iterator(); iter.hasNext();) {
                        Transition flow = (Transition) iter.next();

                        Activity targetNode =
                                flow.getFlowContainer()
                                        .getActivity(flow.getTo());

                        if (targetNode != null) {
                            /*
                             * If the target object is NOT within selected
                             * objects then this is an end object.
                             */
                            if (!objectMap.containsKey(targetNode.getId())) {
                                /* Only add once. */
                                if (!exitPathSources.contains(activity)) {
                                    exitPathSources.add(activity);

                                    refactorInfo.exitPathActsAndTrans.add(flow);
                                    refactorInfo.exitPathActsAndTrans
                                            .add(activity);
                                }

                            }
                        }
                    }

                } else {

                    /*
                     * This sequenceflow capable object has no outgoing flows.
                     * This 'may' become the end object
                     */

                    if (!(ev instanceof EndEvent)) {
                        /*
                         * Unless it is an event attached to task border and the
                         * task in question is also in the selection.
                         */
                        boolean isBorderEvent = false;

                        if (activity.getEvent() instanceof IntermediateEvent) {
                            String taskId =
                                    ((IntermediateEvent) activity.getEvent())
                                            .getTarget();

                            if (taskId != null && taskId.length() > 0) {
                                /*
                                 * Its an event attached to task border. Finally
                                 * check if given task is in selection.
                                 */
                                if (objectMap.containsKey(taskId)) {
                                    isBorderEvent = true;
                                }
                            }
                        }

                        if (!isBorderEvent) {
                            nonFlowExitPathSources.add(activity);

                            refactorInfo.exitPathActsAndTrans.add(activity);
                        }
                    }
                }
            }
        }

        if (exitPathSources.size() > 1) {
            /* There are multiple definitive exit points. */
            ret = RefactorAsSubProcWizardInfo.SUBPROC_MULTIPLE_EXITPATHS;

        } else if (exitPathSources.size() > 0
                && nonFlowExitPathSources.size() > 0) {
            /*
             * there is at least one definitive exit path + other unconnected
             * ones
             */
            ret = RefactorAsSubProcWizardInfo.SUBPROC_MULTIPLE_EXITPATHS;
        } else if (exitPathSources.size() == 1) {
            /*
             * If there is ONE definitive exit path path then default to
             * creating endevent.
             */
            ret = RefactorAsSubProcWizardInfo.SUBPROC_CREATE_ENDEVENT;
        }

        return ret;
    }

    /**
     * Checks if the given {@link Activity} is part of an Event Handler Flow and
     * the complete Event Handler flow is selected.Returns false if either this
     * is not part of an Event Handler Flow OR all Upstream Activities in Event
     * Handler flow are not part of the selection.
     * 
     * @param activity
     * @return true if the given Activity is part of the Event Handler Flow, and
     *         all Upstream Activities in Event Handler flow are part of the
     *         selection.
     */
    private boolean isEventHandlerFlowWithAllUpStreamElementsSelected(
            Activity activity) {

        flowAnalyzer =
                new ProcessFlowAnalyser(true, activity.getFlowContainer());

        Set<Activity> upstreamActivities =
                flowAnalyzer.getUpstreamActivities(activity.getId());

        return isPartOfEventHandlerFlow(activity, upstreamActivities)
                && objectMap.values().containsAll(upstreamActivities);
    }

    /**
     * Checks if the given Activity is part of an Event Handler Flow., returns
     * true if it is , false otherwise.
     * 
     * @param activity
     * @param upstreamActivities
     *            , upstream activities for this Activity, pass null to allow
     *            this method to fetch the upstream Activities.
     * @return true if this Activity is part of an Event Handler Flow, false
     *         otherwise.
     */
    private boolean isPartOfEventHandlerFlow(Activity activity,
            Set<Activity> upstreamActivities) {

        if (upstreamActivities == null) {
            flowAnalyzer =
                    new ProcessFlowAnalyser(true, activity.getFlowContainer());
            upstreamActivities =
                    flowAnalyzer.getUpstreamActivities(activity.getId());
        }

        boolean isPartOfEventHandlerFlow = false;
        for (Activity flowActivity : upstreamActivities) {

            /* if this is an Event handler Activity and is also part of the */
            if (Xpdl2ModelUtil.isEventHandlerActivity(flowActivity)) {

                isPartOfEventHandlerFlow = true;

            }

        }
        return isPartOfEventHandlerFlow;
    }

    /**
     * Check rules on objects.
     * <p>
     * <li>All objects must have same parent lane / sub-process</li>
     * </p>
     * 
     * @return true on success false on failure.
     */
    private boolean checkContainment() {
        boolean ret = true;

        parentContainer = null;

        /*
         * Find the end object (object with outgoing transition from outside of
         * selection).
         */
        for (EObject nodeObject : objectList) {

            EObject thisContainer = Xpdl2ModelUtil.getContainer(nodeObject);

            if (parentContainer == null) {
                parentContainer = thisContainer;

            } else if (thisContainer != parentContainer) {
                ret = false;

                MessageDialog
                        .openError(getShell(),
                                Messages.RefactorAsEmbeddedSubProcCommand_RefactorAsEmbSubprocError_title,
                                Messages.RefactorAsEmbeddedSubProcCommand_RefactorAsEmbSubprocMixedSourceError_longdesc);
                break;

            }

            if (nodeObject instanceof Activity) {
                Activity activity = (Activity) nodeObject;

                /*
                 * Check for Task border event selected without task itself
                 * selected.
                 */
                boolean isBorderEvent = false;

                if (activity.getEvent() instanceof IntermediateEvent) {
                    String taskId =
                            ((IntermediateEvent) activity.getEvent())
                                    .getTarget();

                    if (taskId != null && taskId.length() > 0) {
                        /*
                         * Its an event attached to task border. Finally check
                         * if given task is in selection.
                         */
                        if (!objectMap.containsKey(taskId)) {
                            ret = false;

                            MessageDialog
                                    .openError(getShell(),
                                            Messages.RefactorAsEmbeddedSubProcCommand_RefactorAsEmbSubprocError_title,
                                            Messages.RefactorAsEmbeddedSubProcCommand_DisallowEventOnTaskBorder_longdesc);
                            break;
                        }
                    }
                }

            }

        }

        return ret;
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#canExecute()
     */
    @Override
    public boolean canExecute() {
        boolean ret = false;

        /*
         * We have to validate AND complain when asked if we can execute. This
         * is because if we return true from can execute then the command will
         * ALWAYS get added to the undo stack (we can't just return false from
         * canUndo because the entry is still put on command stack but is
         * disabled).
         */

        /*
         * So make sure we only message box once in case we get called multiple
         * times.
         */
        if (validationAttempted) {
            /*
             * Already done validation once, just return according to last
             * result.
             */
            if (validationResult == -1) {
                return false;
            } else {
                return true;
            }
        }

        validateRefactor();

        if (validationResult != -1) {
            refactorInfo.validationInfo = validationResult;

            refactorInfo.selectedObjects = objectList;

            Process process = Xpdl2ModelUtil.getProcess(parentContainer);

            if (process != null) {
                /*
                 * Show unique sub-proc name in wizard.
                 */
                refactorInfo.subprocName = getUniqueSubProcName(process);

            }

            BaseRefactorAsSubProcWizard wizard = getRefactorWizard();

            WizardDialog wizDig = new WizardDialog(getShell(), wizard);
            if (wizDig.open() == WizardDialog.OK) {
                ret = true;
            }

        } else {
            ret = false;
        }
        return ret;
    }

    /**
     * Get refactor wizard. Override this method if you want to show up some
     * other wizard than "refactor to  subprocess" wizard.
     * 
     * @return Refactor Wizard.
     */
    abstract protected BaseRefactorAsSubProcWizard getRefactorWizard();

    /**
     * Get a unique name for the newly created sub-process.
     * 
     * @param process
     *            Process to look in.
     * @return Unique name for the newly created sub-process.
     */
    abstract protected String getUniqueSubProcName(Process process);

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#canUndo()
     */
    @Override
    public boolean canUndo() {
        return delegateCmd.canUndo();
    }

    /**
     * @param command
     * @return
     * @see org.eclipse.emf.common.command.Command#chain(org.eclipse.emf.common.command.Command)
     */
    @Override
    public Command chain(Command command) {
        return delegateCmd.chain(command);
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#dispose()
     */
    @Override
    public void dispose() {
        delegateCmd.dispose();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    @Override
    public void execute() {
        Command cmd = getRefactorCommand();

        delegateCmd.appendAndExecute(cmd);
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getAffectedObjects()
     */
    @Override
    public Collection getAffectedObjects() {
        return delegateCmd.getAffectedObjects();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getDescription()
     */
    @Override
    public String getDescription() {
        return delegateCmd.getDescription();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getLabel()
     */
    @Override
    public String getLabel() {
        return delegateCmd.getLabel();
    }

    /**
     * @return
     * @see org.eclipse.emf.common.command.Command#getResult()
     */
    @Override
    public Collection getResult() {
        return delegateCmd.getResult();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#redo()
     */
    @Override
    public void redo() {
        delegateCmd.redo();
    }

    /**
     * 
     * @see org.eclipse.emf.common.command.Command#undo()
     */
    @Override
    public void undo() {
        delegateCmd.undo();
    }

    private Shell getShell() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    }

    /**
     * Get label to put on the refactor command.
     * 
     * @return label to put on the refactor command.
     */
    abstract protected String getCommandLabel();

    /**
     * Returns new Subprocess Activity.
     * 
     * @return the newSubProcObject
     */
    public Activity getNewSubProcActivity() {
        return newSubProcActivity;
    }

    /**
     * Returns List of objects selected for refactor.
     * 
     * @return List of objects selected for refactor.
     */
    protected List<EObject> getObjectList() {
        return objectList;
    }

    /**
     * Returns refactored Contents bounds.
     * 
     * @return contentBounds
     */
    protected Rectangle getContentBounds() {
        return contentBounds;
    }

    /**
     * Returns Refactor Wizard Info.
     * 
     * @return RefactorAsSubProcWizardInfo.
     */
    protected RefactorAsSubProcWizardInfo getRefactorInfo() {
        return refactorInfo;
    }

}
