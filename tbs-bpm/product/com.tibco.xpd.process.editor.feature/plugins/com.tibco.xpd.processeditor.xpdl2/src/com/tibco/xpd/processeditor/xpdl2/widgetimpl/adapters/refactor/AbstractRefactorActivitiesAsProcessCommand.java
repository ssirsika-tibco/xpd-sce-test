/**
 * RefactorAsIndependentSubProcCommand.java
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.RefactorAsIndiSubprocWizardInfo.TaskGroupReferenceInfo;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.XpdEcoreUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.ReparentListElementCommand;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamReplacer;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamResolver;
import com.tibco.xpd.xpdl2.util.LocalPackageProcessInterfaceUtil;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * RefactorAsIndependentSubProcCommand
 * 
 * EMF Command to refactor selected objects into independent sub-process.
 * 
 */
@SuppressWarnings({ "rawtypes", "deprecation" })
public abstract class AbstractRefactorActivitiesAsProcessCommand
        implements Command {

    // Amount of extra space around content selected for sub-proc.
    private static final int CONTENT_MARGIN = 20;

    // How much bigger subproc needs to be than content.
    private static final int INSERT_EVENT_EXTRA_CX =
            ProcessWidgetConstants.START_EVENT_SIZE * 4;

    // Command delegate.
    private CompoundCommand delegateCmd = null;

    // The new independent sub-process object we'll be creating.
    private Activity newProcessInvokeActivity = null;

    // The new process
    private Process newProcess = null;

    // And the pool in it.
    private Pool newPool = null;

    // And the new lane in that.
    private Lane newLane = null;

    // Editing domain providing model edit framework.
    private EditingDomain editingDomain = null;

    // Map of object ID to Model Object. This is for quick lookup in various
    // cricumstances (mainly dealing with connections). NOTE: This contains all
    // objects including those in embedded sub-processes (not just top level
    // objects we are moving).
    private HashMap<String, EObject> objectMap = null;

    // List of object adapters.
    private List<EObject> objectList = null;

    /**
     * List of selected objects including transitions between them and
     * activities / transitions from embedded sub-process contents
     */
    private Collection<EObject> allSelActsAndTrans = null;

    // If a SINGLE embedded sub-process activity is being refactored then we
    // move it's content to the independent sub-process and convert it to an
    // independent sub-proc call activity - this flags says we are doing so.
    private boolean refactoringSingleEmbSubProc = false;

    // List of activity sets that we are moving.
    private List<ActivitySet> activitySets = null;

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
    // selection, but not part of any Event Handler flow IN THE SELECTION.
    private List<Activity> exitPathSources = null;

    /**
     * Keeps track of The flow objects in selection that are connected to
     * objects outside of selection, this List is used to relink the exit points
     * in the main process.
     */
    private List<Activity> relinkExitPoints = null;

    // flow objects with no outgoing flow.
    private List<Activity> nonFlowExitPathSources = null;

    // Already existing start event object (or new one once command is
    // underway).
    private Activity endEventObject = null;

    // The parent container (lane / embedded sub-process) of the objects
    // selected (and hence the container of the new embedded sub-process.
    private EObject parentContainer = null;

    // The parent lane of the selected objects (may be same as parentContainer).
    private Lane parentLane = null;

    // The parent pool of the selected objects.
    private Pool parentPool = null;

    // The parent process.
    private Process parentProcess = null;

    // The bounds of the content to place in the new sub-process
    private Rectangle contentBounds;

    // Info passed to / from refactor wizard.
    private RefactorAsIndiSubprocWizardInfo refactorInfo;

    private ProcessWidgetType sourceProcessType;

    protected ProcessFlowAnalyser flowAnalyzer;

    /**
     * Keeps track of the AdHoc Task Configuration for the Task , when this is
     * the case of Single AdHoc Task Refactor.For multiple tasks refactor this
     * element will be null.Existence of this element is also used as a flag to
     * indicate that this is the scenario of Single Ad-Hoc Task refactor.
     */
    protected AdHocTaskConfigurationType singleSelAdHocTaskConfiguration = null;

    /**
     * Prepares refactor of the given objects into an independent sub-proc.
     * 
     * @param objects
     *            List of model activities and artifacts to refactor into
     *            sub-proc.
     */
    public AbstractRefactorActivitiesAsProcessCommand(
            EditingDomain editingDomain, List<EObject> objects,
            RefactorAsIndiSubprocWizardInfo refactorInfo) {
        this.editingDomain = editingDomain;
        this.refactorInfo = refactorInfo;

        // Create our delegate command.
        delegateCmd = new CompoundCommand();
        delegateCmd.setLabel(
                Messages.RefactorAsIndependentSubProcCommand_RefactorAsIndiSubproc_menu2);

        parentProcess = Xpdl2ModelUtil.getProcess(objects.get(0));

        sourceProcessType = TaskObjectUtil.getProcessType(parentProcess);

        // Keep track of top left object.
        contentBounds = new Rectangle(Integer.MAX_VALUE, Integer.MAX_VALUE,
                Integer.MIN_VALUE, Integer.MIN_VALUE);

        // Construct a map and a list of object ID to model object
        objectMap = new HashMap<String, EObject>(objects.size());

        objectList = new ArrayList<EObject>(objects.size());

        activitySets = new ArrayList<ActivitySet>();

        Point bottomRight = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

        //
        // If single embedded sub-process activity is
        // selected THEN make the object list the content of it. Then later we
        // can move the content out and make the embedded sub-proc an
        // independent sub-proc task.
        List<EObject> objectsToRefactor = objects;

        if (objects.size() == 1) {
            EObject obj = objects.get(0);

            if (obj instanceof Activity) {
                Activity act = (Activity) obj;

                BlockActivity ba = act.getBlockActivity();

                if (ba != null) {
                    // We are refactoring a single embedded sub-proc.
                    refactoringSingleEmbSubProc = true;

                    objectsToRefactor = getEmbeddedSubProcChildren(act);

                    newProcessInvokeActivity = act;

                }

            }
        }

        if (objectsToRefactor.size() == 0) {
            // No objects, probably converting single Empty emb sub-proc.
            // Make sure content bounds is sensible.
            contentBounds = new Rectangle(0, 0, 30, 30);
        } else {
            for (Object obj : objectsToRefactor) {
                String id = null;

                NodeGraphicsInfo gi = null;
                Rectangle objBounds = null;

                if (obj instanceof Activity) {
                    Activity act = (Activity) obj;

                    // If this is emb sub-proc add sub-objects to the
                    // objectMap.
                    BlockActivity ba = act.getBlockActivity();
                    if (ba != null) {
                        addSubActivitiesToMap(ba.getActivitySetId());
                    }

                    id = act.getId();

                    org.eclipse.swt.graphics.Rectangle tmpBnds =
                            Xpdl2ModelUtil.getObjectBounds(act);
                    objBounds = new Rectangle(tmpBnds.x, tmpBnds.y,
                            tmpBnds.width, tmpBnds.height);

                } else if (obj instanceof Artifact) {
                    Artifact art = (Artifact) obj;

                    id = art.getId();

                    org.eclipse.swt.graphics.Rectangle tmpBnds =
                            Xpdl2ModelUtil.getObjectBounds(art);
                    objBounds = new Rectangle(tmpBnds.x, tmpBnds.y,
                            tmpBnds.width, tmpBnds.height);

                } else {
                    throw new UnsupportedOperationException(
                            "Attempt to refactor non activity/artifact object into sub-proc"); //$NON-NLS-1$
                }

                // Store in map and list.
                objectMap.put(id, (EObject) obj);

                objectList.add((EObject) obj);

                if (!Xpdl2ModelUtil.isEventAttachedToTask(obj)) {
                    // Keep track of required position / size of independent
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

            // Sort out the new independent sub-proc bounding rectangle.
            contentBounds.width = bottomRight.x - contentBounds.x;
            contentBounds.height = bottomRight.y - contentBounds.y;
        }

        //
        // Add artifacts contained by embedded sub-processes to object map.
        List artifacts = parentProcess.getPackage().getArtifacts();
        for (Iterator iter = artifacts.iterator(); iter.hasNext();) {
            Artifact art = (Artifact) iter.next();

            EObject container = Xpdl2ModelUtil.getContainer(art);

            if (container instanceof ActivitySet) {
                if (activitySets.contains(container)) {
                    objectMap.put(art.getId(), art);
                }
            }

        }

        // Precreate the new sub-proc call task.
        preCreateIndSubProcTask();

        /* Initialize necessary data to refactor a Single Adhoc Activity */
        initialzeAdhocActivityData(objects);
    }

    /**
     * Initializes the Necessary Data to Refactor a single Adhoc Activity.
     * 
     * @param objects
     *            the objects being refactored.
     */
    private void initialzeAdhocActivityData(List objects) {

        if (objects.size() == 1 && objects.get(0) instanceof Activity) {

            Activity activity = (Activity) objects.get(0);

            EList<Transition> incomingTransitions =
                    activity.getIncomingTransitions();

            if (incomingTransitions == null || incomingTransitions.isEmpty()) {

                Object otherElement = Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AdHocTaskConfiguration());

                if (otherElement instanceof AdHocTaskConfigurationType) {

                    singleSelAdHocTaskConfiguration =
                            (AdHocTaskConfigurationType) otherElement;

                }
            }
        }
    }

    /**
     * Create the new process object.
     */
    protected abstract Process createNewProcess();

    /**
     * @return The type of the sub-process to be extracted.
     */
    protected abstract ProcessWidgetType getTargetSubProcessType();

    /**
     * Get a list of child activities / artifacts that are contained by the
     * given embedded sub-process activity
     * 
     * @param act
     * 
     * @return
     */
    private List<EObject> getEmbeddedSubProcChildren(Activity act) {
        List<EObject> childObjects = new ArrayList<EObject>();

        // Find the activity set for the object.
        BlockActivity ba = act.getBlockActivity();

        if (ba != null) {
            String activitySetId = ba.getActivitySetId();
            ActivitySet actSet = parentProcess.getActivitySet(activitySetId);

            if (actSet != null) {
                // Add the activities to the list.
                childObjects.addAll(actSet.getActivities());

                // Add any artifacts.
                List<Artifact> artifacts =
                        parentProcess.getPackage().getArtifacts();

                for (Artifact art : artifacts) {
                    NodeGraphicsInfo gi =
                            Xpdl2ModelUtil.getNodeGraphicsInfo(art);

                    if (gi != null) {
                        if (activitySetId.equals(gi.getLaneId())) {
                            childObjects.add(art);
                        }
                    }
                }
            }

        }

        return childObjects;
    }

    /**
     * Add the activity set to list of activity sets and all objects to the
     * object map. This method is RECURSIVE!
     * 
     * @param activitySetId
     * @param objectMap2
     */
    private void addSubActivitiesToMap(String activitySetId) {
        ActivitySet actSet = parentProcess.getActivitySet(activitySetId);

        if (actSet != null) {
            // Only add once!
            if (!activitySets.contains(actSet)) {
                activitySets.add(actSet);

                List activities = actSet.getActivities();
                for (Iterator iter = activities.iterator(); iter.hasNext();) {
                    Activity act = (Activity) iter.next();

                    objectMap.put(act.getId(), act);

                    // If this is an embedded sub-process, recurs.
                    BlockActivity ba = act.getBlockActivity();
                    if (ba != null) {
                        addSubActivitiesToMap(ba.getActivitySetId());
                    }
                }

            }
        }
        return;
    }

    /**
     * @return the newEmbeddedSubProcObject
     */
    public Activity getCreatedProcessInvokeActivity() {
        return newProcessInvokeActivity;
    }

    /**
     * Validates that the refactor to independent sub-proc can be performed for
     * selected objects.
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

        refactorInfo.selectedObjects = objectList;
        refactorInfo.sourceProcess = parentProcess;

        if (!checkContainment()) {
            validationResult = -1;

        } else {
            //
            // Get a list of all selected activities and any sub-activities in
            // selected embedded sub-processes. And all transitions connecting
            // them.
            allSelActsAndTrans =
                    Xpdl2ModelUtil.getAllSelectedActivitiesAndTransitions(
                            refactorInfo.selectedObjects,
                            true,
                            false);

            if (hasImplementingActivities(allSelActsAndTrans)) {
                // Prevent refactor of activities that implement interface
                // events into indi sub-proc.
                validationResult =
                        RefactorAsSubProcWizardInfo.SUBPROC_INDI_HAS_IMPLEMENTING_EVENTS;

            } else {

                // Validate incoming sequence flows
                validationResult |= checkIncomingFlows();

                // Validate outgoing sequence flows
                validationResult |= checkOutgoingFlows();

                // Validate and collect participant info.
                validationResult |= collectParticipants();

                // Validate and collect data field info.
                validationResult |= collectDataFields();

                validationResult |= collectTaskGroups();
            }
        }

        return validationResult;
    }

    /**
     * Gather information about any retain familiar groups that will be broken
     * by refactoring some user tasks into reusable sub-process.
     * 
     * @return 0 if no broken task groups or
     *         {@link RefactorAsSubProcWizardInfo#SUBPROC_INDI_BREAKS_SEP_OF_DUTY_GROUP}
     *         and/or
     *         {@link RefactorAsSubProcWizardInfo#SUBPROC_INDI_BREAKS_RETAIN_FAMILIAR_GROUP}
     *         as bit flags.
     */
    private int collectTaskGroups() {
        /* Clear current content. */
        refactorInfo.taskGroups = new ArrayList<TaskGroupReferenceInfo>();

        /*
         * Cache task groups for easy lookup.
         */
        ProcessResourcePatterns resourcePatterns =
                (ProcessResourcePatterns) Xpdl2ModelUtil.getOtherElement(
                        parentProcess,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessResourcePatterns());
        if (resourcePatterns != null) {
            if (resourcePatterns.getSeparationOfDutiesActivities() != null) {
                for (SeparationOfDutiesActivities sepOfDutyGroup : resourcePatterns
                        .getSeparationOfDutiesActivities()) {

                    TaskGroupReferenceInfo taskGroup =
                            new TaskGroupReferenceInfo(sepOfDutyGroup,
                                    parentProcess);
                    refactorInfo.taskGroups.add(taskGroup);

                    for (ActivityRef actRef : sepOfDutyGroup.getActivityRef()) {
                        taskGroup.unselectedActivitiesInGroup
                                .add(actRef.getIdRef());
                    }
                }
            }

            if (resourcePatterns.getRetainFamiliarActivities() != null) {
                for (RetainFamiliarActivities retainFamiliarGroup : resourcePatterns
                        .getRetainFamiliarActivities()) {

                    TaskGroupReferenceInfo taskGroup =
                            new TaskGroupReferenceInfo(retainFamiliarGroup,
                                    parentProcess);
                    refactorInfo.taskGroups.add(taskGroup);

                    for (ActivityRef actRef : retainFamiliarGroup
                            .getActivityRef()) {
                        taskGroup.unselectedActivitiesInGroup
                                .add(actRef.getIdRef());
                    }
                }
            }
        }

        /*
         * Compile information about referenced groups
         */
        for (EObject nodeObject : objectList) {
            if (nodeObject instanceof Activity) {
                Activity activity = (Activity) nodeObject;

                TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);
                if (TaskType.USER_LITERAL.equals(taskType)
                        || TaskType.MANUAL_LITERAL.equals(taskType)) {
                    String id = activity.getId();

                    /*
                     * Find task groups that this activity is referenced in.
                     */
                    for (TaskGroupReferenceInfo taskGroup : refactorInfo.taskGroups) {
                        if (taskGroup.unselectedActivitiesInGroup
                                .contains(id)) {
                            /*
                             * This activity is referenced in this group so tag
                             * group as referenced AND move the activity from
                             * list of acts left in group after refactor.
                             */
                            taskGroup.isReferenced = true;
                            taskGroup.unselectedActivitiesInGroup.remove(id);
                            taskGroup.selectedActivitiesInGroup.add(id);
                        }
                    }
                }
            }
        }

        /*
         * Compile return flags.
         */
        int returnFlags = 0;

        for (TaskGroupReferenceInfo taskGroup : refactorInfo.taskGroups) {
            if (taskGroup.isReferenced) {
                /*
                 * If this task group is referenced AND some activities in that
                 * group are not selected for refactor then the refactor will
                 * break the group.
                 */
                if (!taskGroup.unselectedActivitiesInGroup.isEmpty()) {
                    if (taskGroup.taskGroup instanceof SeparationOfDutiesActivities) {
                        returnFlags |=
                                RefactorAsSubProcWizardInfo.SUBPROC_INDI_BREAKS_SEP_OF_DUTY_GROUP;
                    } else if (taskGroup.taskGroup instanceof RetainFamiliarActivities) {
                        returnFlags |=
                                RefactorAsSubProcWizardInfo.SUBPROC_INDI_BREAKS_RETAIN_FAMILIAR_GROUP;
                    } else {
                        throw new RuntimeException(
                                "Unexpected task group type"); //$NON-NLS-1$
                    }
                }
            }
        }

        return returnFlags;
    }

    /**
     * Does the selection contain activities that implement process interface
     * events?
     * 
     * @param allSelActsAndTrans2
     * @return true if selection contain activities that implement process
     *         interface events.
     */
    private boolean hasImplementingActivities(
            Collection<EObject> allSelActsAndTrans2) {

        for (EObject eo : allSelActsAndTrans2) {
            if (eo instanceof Activity) {
                if (ProcessInterfaceUtil.isEventImplemented((Activity) eo)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Collect all the referenced data fields into the refactor info object.
     * 
     * @return
     */
    private int collectDataFields() {
        refactorInfo.referencedDataFields.clear();

        //
        // Create a data field reference / formal parameter resolver JUST for
        // data fields in the parent process. References to package data fields
        // don't matter as these won't change.
        HashSet<ProcessRelevantData> inScopeMappableDataFields =
                new HashSet<ProcessRelevantData>();

        inScopeMappableDataFields.addAll(
                ProcessInterfaceUtil.getAllFormalParameters(parentProcess));
        inScopeMappableDataFields.addAll(parentProcess.getDataFields());

        //
        // If the objects are in an embedded sub-process then add the data that
        // is in scope of that too.
        if (parentContainer instanceof ActivitySet) {
            ActivitySet set = (ActivitySet) parentContainer;

            if (refactoringSingleEmbSubProc) {
                // if moving the content of a single embedded sub-process to
                // sub-process then don't want to factor in this embedded's
                // data (just the parent if we're nested).
                Activity embAct = Xpdl2ModelUtil.getEmbSubProcActivityForActSet(
                        parentProcess,
                        set.getId());
                if (embAct != null
                        && embAct.eContainer() instanceof ActivitySet) {
                    set = (ActivitySet) embAct.eContainer();
                    /*
                     * XPD-7681: We said that in case a single embedded sub-proc
                     * is refactored we refactor the data of its parent embedded
                     * sub-process (if any) but we were not really doing that.
                     */
                    inScopeMappableDataFields
                            .addAll(LocalPackageProcessInterfaceUtil
                                    .getEmbeddedSubProcessSetScopeData(set));
                }
            } else {
                /*
                 * more than one embedded sub-processes refactored.
                 */
                inScopeMappableDataFields
                        .addAll(LocalPackageProcessInterfaceUtil
                                .getEmbeddedSubProcessSetScopeData(set));
            }
        }

        Xpdl2FieldOrParamResolver dataFieldResolver =
                new Xpdl2FieldOrParamResolver(inScopeMappableDataFields);

        //
        // Get a list of all activities, transitions and messageflows in
        // process as we need to know where else data fields might be
        // referenced.
        Collection<EObject> allActsAndConns = new ArrayList<EObject>();

        allActsAndConns
                .addAll(Xpdl2ModelUtil.getAllActivitiesInProc(parentProcess));

        allActsAndConns
                .addAll(Xpdl2ModelUtil.getAllTransitionsInProc(parentProcess));

        allActsAndConns
                .addAll(Xpdl2ModelUtil.getAllMessageFlowsInProc(parentProcess));

        //
        // Now go thru list of all activities, transitions and message flows
        // creating 2 lists - one of referenced data fields / params in objects
        // that are in the selection and another of objects referenced in
        // objects that are NOT in selection.
        HashSet<ProcessRelevantData> fieldsRefdInSelected =
                new HashSet<ProcessRelevantData>();
        HashSet<ProcessRelevantData> fieldsRefdInNotSelected =
                new HashSet<ProcessRelevantData>();

        for (EObject actOrConn : allActsAndConns) {

            //
            // Check for data field references in the given object.
            Set<ProcessRelevantData> refdFields = null;

            if (actOrConn instanceof Activity) {
                Activity activity = (Activity) actOrConn;

                refdFields = dataFieldResolver.getDataReferences(activity);

                /*
                 * XPD-205 - normally we would not count IMPLICITLY associated
                 * data for activities however user tasks are a slightly special
                 * case in that if the user has explicitly created a form and
                 * then the user has also explicitly said
                 * "I want all the process data in the form".
                 * 
                 * Therefore for a user task with an explicitly created form we
                 * should consider all data as associated.
                 */
                if (TaskType.USER_LITERAL
                        .equals(TaskObjectUtil.getTaskTypeStrict(activity))) {

                    List<AssociatedParameter> activityAssociatedParameters =
                            ProcessInterfaceUtil
                                    .getActivityAssociatedParameters(activity);
                    if (activityAssociatedParameters == null
                            || activityAssociatedParameters.size() == 0) {
                        /*
                         * Sid XPD-2087: Only use all data implicitly if
                         * implicit association has not been disabled.
                         */
                        if (!ProcessInterfaceUtil
                                .isImplicitAssociationDisabled(activity)) {
                            FormImplementation impl = TaskObjectUtil
                                    .getUserTaskFormImplementation(activity);
                            if (impl != null && FormImplementationType.FORM
                                    .equals(impl.getFormType())) {
                                refdFields = new HashSet<ProcessRelevantData>();
                                refdFields.addAll(ProcessInterfaceUtil
                                        .getAllAvailableRelevantDataForActivity(
                                                activity));
                            }
                        }
                    }
                }

            } else if (actOrConn instanceof Transition) {
                refdFields = dataFieldResolver
                        .getDataReferences((Transition) actOrConn);

            } else if (actOrConn instanceof MessageFlow) {
                refdFields = dataFieldResolver
                        .getDataReferences((MessageFlow) actOrConn);

            }

            if (refdFields != null && refdFields.size() > 0) {
                // Combine the referenced fields of this object to complete
                // list.
                if (allSelActsAndTrans.contains(actOrConn)) {
                    // Add discovered data fields to selected list
                    fieldsRefdInSelected.addAll(refdFields);
                } else {
                    // Add discovered data fields to NOT selected list
                    fieldsRefdInNotSelected.addAll(refdFields);
                }

            }

        } // Check data fields in next activity / connection.

        //
        // Now we can create the list of data fields / formal params that need
        // to be created / moved into new sub-process.
        for (ProcessRelevantData selFieldOrParam : fieldsRefdInSelected) {
            RefactorReferencedDataFieldInfo refDataField =
                    new RefactorReferencedDataFieldInfo();

            refDataField.dataFieldOrParam = selFieldOrParam;

            // Data fields used in selection but NOT elsewhere can be moved
            // rather than duplicated. (provided user says it's ok).
            //
            // Formal parameters are always duplicated.
            if ((selFieldOrParam instanceof DataField)
                    && !fieldsRefdInNotSelected.contains(selFieldOrParam)) {
                refDataField.moveDataField = true;

                refDataField.referencedElseWhere = false;

            } else {
                // Data field is used elsewhere in process.
                // Therefore it MUST be copied.
                refDataField.moveDataField = false;

                refDataField.referencedElseWhere = true;

            }

            refactorInfo.referencedDataFields.add(refDataField);
        }

        return 0; // Everything OK.
    }

    /**
     * Collect all the referenced participants into the refactor info object.
     * 
     * @return
     */
    private int collectParticipants() {
        refactorInfo.referencedParticipants.clear();

        List procPartics = parentProcess.getParticipants();

        if (procPartics != null && procPartics.size() > 0) {

            //
            // Build a quick lookup list of process participant id's.
            HashMap<String, Participant> particIds =
                    new HashMap<String, Participant>(procPartics.size());

            // NOTE: We will only bother with PROCESS participants, as refactor
            // is always to process in same package.
            for (Iterator iter = procPartics.iterator(); iter.hasNext();) {
                Participant partic = (Participant) iter.next();

                particIds.put(partic.getId(), partic);
            }

            //
            // Get a list of all activities in process.
            Collection<Activity> allActs =
                    Xpdl2ModelUtil.getAllActivitiesInProc(parentProcess);

            //
            // Now go thru list of all activities creating 2 lists - one of
            // referenced participants in objects that in the selection and
            // another of objects referenced in objects that are NOT in
            // selection.
            HashSet<Participant> particsRefdInSelected =
                    new HashSet<Participant>();
            HashSet<Participant> particsRefdInNotSelected =
                    new HashSet<Participant>();

            for (Activity act : allActs) {

                HashSet addToList;

                if (allSelActsAndTrans.contains(act)) {
                    // Add discovered participants to selected list
                    addToList = particsRefdInSelected;
                } else {
                    // Add discovered participants to NOT selected list
                    addToList = particsRefdInNotSelected;
                }

                //
                // NOTE: xpdl v2 has Activity->Performer AND
                // Activity->Performers->Performer[].
                //
                // We may want multiple performers later so we'll grab
                // participant references from both.
                List<Performer> performers = act.getPerformerList();

                for (Performer perf : performers) {
                    String particId = perf.getValue();

                    Participant partic = particIds.get(particId);

                    if (partic != null) {
                        // Reference to a process participant.
                        addToList.add(partic);
                    }
                }

            } // Check participants in next activity.

            //
            // Now we can create the list of participants that need to be
            // created / moved into new sub-process.
            for (Participant selPartic : particsRefdInSelected) {
                RefactorReferencedParticipantInfo refPartic =
                        new RefactorReferencedParticipantInfo();

                refPartic.participant = selPartic;

                if (!particsRefdInNotSelected.contains(selPartic)) {
                    // Partic used in selection but NOT elsewhere.
                    refPartic.moveParticipant = true; // By default move it.

                    refPartic.referencedElseWhere = false;

                } else {
                    // Particiant is used elsewhere in process.
                    // Therefore it MUST be copied.
                    refPartic.moveParticipant = false;

                    refPartic.referencedElseWhere = true;

                }

                refactorInfo.referencedParticipants.add(refPartic);
            }
        }

        return 0; // Everything OK.
    }

    /**
     * Return the command that will refactor selected objects into independent
     * subprocess.
     * 
     * @return
     */
    private Command getRefactorCommand() {
        Command retCmd = UnexecutableCommand.INSTANCE;

        CompoundCommand cmd = new CompoundCommand();
        retCmd = cmd;

        //
        // Setup new independent sub-process activity and it's activity set...
        //
        setupNewProcess(cmd);

        //
        // If we found a definite start object then reconnect it's incoming
        // transitions to new sub-proc.
        //
        relinkEntryPoint(cmd);

        //
        // If we found a definite end object then reconnect it's outgoing
        // transitions from new-sub-proc.
        //
        relinkExitPoint(cmd);

        //
        // Relink associations to / from objects in selection.
        relinkAssociations(cmd);

        //
        // Move selected objects and all transitions between them into new
        // activity set.
        // (offset positions to reoriganise them within sub-proc ).
        //
        Rectangle newArea = moveContentToSubProc(cmd);

        //
        // Relink message flows to / from objects in selection
        relinkMessageFlows(cmd);

        //
        // If required, add the start event and connect it to entry point
        // object.
        //
        addStartEvent(cmd, newArea);

        //
        // If required, add end events to exit point objects.
        //
        addEndEvent(cmd, newArea);

        //
        // Move / duplicate referenced participants
        //
        addParticipants(cmd);

        //
        // Move / duplicate referenced data fields
        //
        addDataFields(cmd);

        //
        // Inherit the destination environments of the parent process.
        //
        addDestinations(cmd);

        /*
         * Update resource pattern task group stuff
         */
        updateTaskGroups(cmd, refactorInfo, newProcess);

        performFinalConfiguration(cmd, objectList);

        //
        // Finally, if refactoring single emb sub-proc then we can now delete
        // the activity set.
        if (refactoringSingleEmbSubProc) {
            cmd.append(RemoveCommand.create(editingDomain, parentContainer));
        }

        return retCmd;
    }

    /**
     * This is the last chance to append commands to configure any of the
     * objects in the list of those being refactored.
     * 
     * @param cmd
     * @param objectList
     *            List of node/connection objects affected by the refactor
     */
    protected void performFinalConfiguration(CompoundCommand cmd,
            List<EObject> objectList) {
        for (EObject object : objectList) {
            performFinalConfiguration(cmd, object);

            if (object instanceof Activity) {
                Activity activity = (Activity) object;

                /*
                 * ABPM-911: Saket: An event subprocess should mostly behave
                 * like an embedded sub-process.
                 */
                if (TaskType.EMBEDDED_SUBPROCESS_LITERAL
                        .equals(TaskObjectUtil.getTaskTypeStrict(activity))
                        || TaskType.EVENT_SUBPROCESS_LITERAL.equals(
                                TaskObjectUtil.getTaskTypeStrict(activity))) {
                    Collection<Activity> allActivitiesInEmbeddedSubProc =
                            Xpdl2ModelUtil.getAllActivitiesInEmbeddedSubProc(
                                    activity);
                    for (Activity subAct : allActivitiesInEmbeddedSubProc) {
                        performFinalConfiguration(cmd, subAct);
                    }

                    Collection<Transition> allTransitionsInEmbeddedSubProc =
                            Xpdl2ModelUtil.getAllTransitionsInEmbeddedSubProc(
                                    activity);
                    for (Transition transition : allTransitionsInEmbeddedSubProc) {
                        performFinalConfiguration(cmd, transition);
                    }
                }
            }
        }

        return;
    }

    /**
     * This is the last chance to append commands to configure any of the
     * objects in the list of those being refactored.
     * 
     * @param cmd
     * @param object
     */
    protected void performFinalConfiguration(CompoundCommand cmd,
            EObject object) {

        if (object instanceof Activity) {
            /*
             * XPD-7400: Fix activity references which may be affected because
             * of refactor as new sub-process
             */
            fixActivityReferences(cmd,
                    (Activity) object,
                    getParentProcess(),
                    newProcess);
        }

        return;
    }

    /**
     * Populates the passed command with the command to fix all the activity
     * references which might be affected due to the refactor.
     * 
     * @param cmd
     * @param object
     * @param parentProcess
     * @param newProcess
     */
    protected void fixActivityReferences(CompoundCommand cmd,
            Activity refactoredActivity, Process parentProcess,
            Process newProcess) {

        if (parentProcess != null && newProcess != null
                && refactoredActivity != null) {

            /*
             * XPD-7400, XPD-7238: Fix Container-Id references in error events.
             */
            fixErrorEventReferences(cmd,
                    refactoredActivity,
                    getParentProcess(),
                    newProcess);
            /*
             * Fix other references here in future, if any spotted.
             */

        }
    }

    /**
     * Fixes the container ID references in the error events if they reference
     * the activity being refactored.
     * 
     * @param cmd
     * @param refactoredActivity
     * @param parentProcess
     * @param newProcess
     */
    private void fixErrorEventReferences(CompoundCommand cmd,
            Activity refactoredActivity, Process parentProcess,
            Process newProcess) {
        /*
         * get the new container ID
         */
        String newContainerID = newProcess.getId();
        String refactoredActivityId = refactoredActivity.getId();

        if (newContainerID != null && !newContainerID.isEmpty()
                && refactoredActivityId != null
                && !refactoredActivityId.isEmpty()) {

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(parentProcess);

            for (Activity activity : allActivitiesInProc) {

                Event event = activity.getEvent();
                /*
                 * Only applicable for error events
                 */
                if (event instanceof IntermediateEvent
                        && EventTriggerType.EVENT_ERROR_LITERAL
                                .equals(EventObjectUtil
                                        .getEventTriggerType(activity))) {

                    ResultError resultError =
                            ((IntermediateEvent) event).getResultError();

                    if (resultError != null) {
                        Object otherElement = Xpdl2ModelUtil.getOtherElement(
                                resultError,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ErrorThrowerInfo());

                        if (otherElement instanceof ErrorThrowerInfo) {
                            ErrorThrowerInfo errorThrowerInfo =
                                    (ErrorThrowerInfo) otherElement;
                            String throwerId = errorThrowerInfo.getThrowerId();
                            /*
                             * If the Throw ID of the error activity is the same
                             * as the refactored activity then we need to update
                             * the container ID.
                             */
                            if (refactoredActivityId.equals(throwerId)) {

                                cmd.append(SetCommand.create(editingDomain,
                                        errorThrowerInfo,
                                        XpdExtensionPackage.eINSTANCE
                                                .getErrorThrowerInfo_ThrowerContainerId(),
                                        newContainerID));

                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Modify command to update resource pattern task group stuff.
     * 
     * @param cmd
     * @param refactorInfo
     * @param newProcess
     */
    protected void updateTaskGroups(CompoundCommand cmd,
            RefactorAsIndiSubprocWizardInfo refactorInfo, Process newProcess) {
        /*
         * Add task groups whose tasks are completely selected to the new
         * process (expected to be implemented only by indi sub-proc command).
         */
        ProcessResourcePatterns newProcessPatterns = null;

        for (TaskGroupReferenceInfo taskGroupInfo : refactorInfo.taskGroups) {
            if (taskGroupInfo.isReferenced) {

                if (taskGroupInfo.unselectedActivitiesInGroup.isEmpty()) {
                    /*
                     * When all tasks in the group are being refactored and then
                     * we need to recreate it in the target process.
                     */
                    EObject copyTaskGroup =
                            EcoreUtil.copy(taskGroupInfo.taskGroup);

                    if (newProcessPatterns == null) {
                        newProcessPatterns =
                                (ProcessResourcePatterns) Xpdl2ModelUtil
                                        .getOtherElement(newProcess,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ProcessResourcePatterns());
                        if (newProcessPatterns == null) {
                            newProcessPatterns = XpdExtensionFactory.eINSTANCE
                                    .createProcessResourcePatterns();
                            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(
                                    editingDomain,
                                    newProcess,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ProcessResourcePatterns(),
                                    newProcessPatterns));
                        }
                    }

                    if (copyTaskGroup instanceof SeparationOfDutiesActivities) {
                        cmd.append(AddCommand.create(editingDomain,
                                newProcessPatterns,
                                XpdExtensionPackage.eINSTANCE
                                        .getProcessResourcePatterns_SeparationOfDutiesActivities(),
                                copyTaskGroup));
                    } else if (copyTaskGroup instanceof RetainFamiliarActivities) {
                        cmd.append(AddCommand.create(editingDomain,
                                newProcessPatterns,
                                XpdExtensionPackage.eINSTANCE
                                        .getProcessResourcePatterns_RetainFamiliarActivities(),
                                copyTaskGroup));
                    }
                }

                /*
                 * Then remove the original task group even when not moving it
                 * to new process because the user will have elected to remove
                 * the 'broken' task group when it was only partially selected).
                 * 
                 * Perform as late exec commands because pre-preping separate
                 * individual removes from same collection can cause problems.
                 */
                cmd.append(RemoveCommand.create(editingDomain,
                        taskGroupInfo.taskGroup));
            }
        }

        return;
    }

    /**
     * Inherit the destination environments of the parent process.
     * 
     * @param cmd
     */
    private void addDestinations(CompoundCommand cmd) {
        Set<String> dests =
                DestinationUtil.getEnabledGlobalDestinations(parentProcess);
        if (dests != null) {
            for (String dest : dests) {
                cmd.append(DestinationUtil.getSetDestinationEnabledCommand(
                        editingDomain,
                        newProcess,
                        dest,
                        true));
            }
        }
        return;
    }

    /**
     * Move or duplicate participants in new sub-process
     * 
     * @param cmd
     */
    protected abstract void addParticipants(CompoundCommand cmd);

    /**
     * Move or duplicate data fields in new sub-process
     * 
     * @param cmd
     */
    private void addDataFields(CompoundCommand cmd) {
        HashMap<String, String> copyFieldMap = new HashMap<String, String>();

        // First collect all the data fields that are to be copied.
        HashMap<String, ProcessRelevantData> fieldsToCopy =
                new HashMap<String, ProcessRelevantData>();

        for (RefactorReferencedDataFieldInfo fieldInfo : refactorInfo.referencedDataFields) {
            if (fieldInfo.referencedElseWhere || !fieldInfo.moveDataField) {
                fieldsToCopy.put(fieldInfo.dataFieldOrParam.getName(),
                        fieldInfo.dataFieldOrParam);
            }
        }

        //
        // Go thru the data fields / formal parameters to be copied and create
        // sub-process formal parameters for them and also create a map of old
        // to new.
        if (fieldsToCopy.size() > 0) {

            for (Iterator iter = fieldsToCopy.values().iterator(); iter
                    .hasNext();) {
                ProcessRelevantData oldFieldOrParam =
                        (ProcessRelevantData) iter.next();

                // Create a formal parameter matching the type of the data field
                // / actual param.
                FormalParameter newFormalParam =
                        Xpdl2Factory.eINSTANCE.createFormalParameter();

                EAttribute ea = XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_DisplayName();
                Xpdl2ModelUtil.setOtherAttribute(newFormalParam,
                        ea,
                        Xpdl2ModelUtil.getOtherAttribute(oldFieldOrParam, ea));
                newFormalParam.setName(oldFieldOrParam.getName());
                newFormalParam.setDescription(oldFieldOrParam.getDescription());
                newFormalParam.setLength(oldFieldOrParam.getLength());
                newFormalParam.setIsArray(oldFieldOrParam.isIsArray());

                // Source formal parameters obey the original direction mode (as
                // we now validate against mapping an IN param from a Sub-Proc
                // OUT param and visa versa we would get validation errors for
                // mapping original IN param from the created INOUT param in new
                // sub-proc).
                ModeType newParamMode = ModeType.INOUT_LITERAL;

                if (oldFieldOrParam instanceof FormalParameter) {
                    newParamMode =
                            ((FormalParameter) oldFieldOrParam).getMode();
                }

                newFormalParam.setMode(newParamMode);

                if (oldFieldOrParam.getDataType() != null) {
                    newFormalParam.setDataType(
                            EcoreUtil.copy(oldFieldOrParam.getDataType()));
                }

                // Create a new id.
                copyFieldMap.put(oldFieldOrParam.getId(),
                        newFormalParam.getId());

                newProcess.getFormalParameters().add(newFormalParam);

                configureProcessInvokeActivityForData(cmd,
                        oldFieldOrParam,
                        newFormalParam,
                        newParamMode);

            }

            //
            // Internally (for actual params / conditions etc we reference
            // data fields by name. But extension plug-ins may have
            // references by
            // id etc. So give them a chance to modify the object as
            // appropriate.
            //
            Xpdl2FieldOrParamReplacer fieldReplacer =
                    new Xpdl2FieldOrParamReplacer(copyFieldMap, true);

            for (EObject modelObject : allSelActsAndTrans) {
                Command replaceCmd = null;

                if (modelObject instanceof Activity) {
                    replaceCmd = fieldReplacer.getReplaceFieldReferencesCommand(
                            editingDomain,
                            (Activity) modelObject);

                } else if (modelObject instanceof Transition) {
                    replaceCmd = fieldReplacer.getReplaceFieldReferencesCommand(
                            editingDomain,
                            (Transition) modelObject);

                }

                if (replaceCmd != null) {
                    cmd.append(replaceCmd);
                }
            }

        } else {
            configureProcessInvokeActivityForNoData();
        }

        //
        // Now deal with the data fields to move.
        for (RefactorReferencedDataFieldInfo fieldInfo : refactorInfo.referencedDataFields) {
            if (!fieldInfo.referencedElseWhere && fieldInfo.moveDataField) {
                if (fieldInfo.dataFieldOrParam instanceof FormalParameter) {
                    // Shoiuldn't ever be moving formal parameters to sub-proc.
                } else {
                    /*
                     * DonLast-ditch check as to whether remove datafield is ok
                     * or not.
                     */
                    if (isOkToRemoveDataField(
                            (DataField) fieldInfo.dataFieldOrParam)) {
                        cmd.append(ReparentListElementCommand.create(
                                editingDomain,
                                fieldInfo.dataFieldOrParam,
                                newProcess,
                                Xpdl2Package.eINSTANCE
                                        .getDataFieldsContainer_DataFields()));
                    } else {
                        DataField copyField = (DataField) EcoreUtil
                                .copy(fieldInfo.dataFieldOrParam);
                        newProcess.getDataFields().add(copyField);
                    }
                }
            }
        }

        if (refactoringSingleEmbSubProc
                && parentContainer instanceof ActivitySet) {
            //
            // When refactoring a single embedded sub-process then move it's
            // local data to the new sub-process.
            Activity embAct =
                    Xpdl2ModelUtil.getEmbSubProcActivityForActSet(parentProcess,
                            ((ActivitySet) parentContainer).getId());
            if (embAct != null) {
                for (DataField field : embAct.getDataFields()) {
                    if (isOkToRemoveDataField(field)) {
                        cmd.append(ReparentListElementCommand.create(
                                editingDomain,
                                field,
                                newProcess,
                                Xpdl2Package.eINSTANCE
                                        .getDataFieldsContainer_DataFields()));
                    } else {
                        DataField copyField = EcoreUtil.copy(field);
                        newProcess.getDataFields().add(copyField);
                    }
                }
            }
        }

        return;
    }

    /**
     * Allows the sub-class chance to do something sensible about have no
     * associated data to be passed to/from refactored sub-process.
     */
    protected void configureProcessInvokeActivityForNoData() {
        return;
    }

    /**
     * Last-ditch check that it is ok to move rtaher than copy the data field
     * from source process to new sub-process.
     * 
     * @param field
     * @return true if it is ok to move the data field from source process to
     *         new sub-process or false to duplicate the field.
     */
    protected boolean isOkToRemoveDataField(DataField field) {
        return true;
    }

    /**
     * The given parameter has been created into the new process from the given
     * source data field / parameter - configure the new process invocation
     * activity as appropriate.
     * 
     * @param cmd
     * @param oldFieldOrParam
     * @param newFormalParam
     * @param newParamMode
     */
    protected abstract void configureProcessInvokeActivityForData(
            CompoundCommand cmd, ProcessRelevantData oldFieldOrParam,
            FormalParameter newFormalParam, ModeType newParamMode);

    /**
     * Create and add a start event if necessary.
     * 
     * Join the start event to all entry path objects.
     * 
     * @param cmd
     */
    private void addStartEvent(CompoundCommand cmd, Rectangle newArea) {
        //
        // If user has asked to create start event then do so.
        if (startEventObject == null && (refactorInfo.validationInfo
                & RefactorAsSubProcWizardInfo.SUBPROC_CREATE_STARTEVENT) != 0) {
            WidgetRGB fillColor =
                    ProcessWidgetColors.getInstance(getTargetSubProcessType())
                            .getGraphicalNodeColor(null,
                                    ProcessWidgetColors.START_EVENT_FILL);
            WidgetRGB lineColor =
                    ProcessWidgetColors.getInstance(getTargetSubProcessType())
                            .getGraphicalNodeColor(null,
                                    ProcessWidgetColors.START_EVENT_LINE);

            Dimension size =
                    new Dimension(ProcessWidgetConstants.START_EVENT_SIZE,
                            ProcessWidgetConstants.START_EVENT_SIZE);

            Point loc = new Point(
                    newArea.x - INSERT_EVENT_EXTRA_CX + (size.width / 2),
                    newArea.y + (ProcessWidgetConstants.TASK_HEIGHT_SIZE / 2)
                            - 1);

            startEventObject = ElementsFactory.createEvent(loc,
                    size,
                    newLane.getId(),
                    EventFlowType.FLOW_START_LITERAL,
                    EventTriggerType.EVENT_NONE_LITERAL,
                    fillColor.toString(),
                    lineColor.toString(),
                    null);

            cmd.append(AddCommand.create(editingDomain,
                    newProcess,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                    startEventObject));
        }

        //
        // At this point we either have an existing start event object, or a new
        // one or none at all.
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(getTargetSubProcessType())
                        .getGraphicalNodeColor(null,
                                ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE);

        // If we have a start event object, join all the entry path objects
        // (that we have either cut the connection to or didn't have one in the
        // first place).
        if (startEventObject != null) {
            // First for objects we have cut connections to.
            for (Iterator iter = entryPathTargets.iterator(); iter.hasNext();) {
                Activity activity = (Activity) iter.next();

                // Don't connect to something that is already a start event!
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
                            newProcess,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions(),
                            trans));

                }
            }

            // Then the objects that didn't have incoming flow in the first
            // place.
            for (Iterator iter = nonFlowEntryPathTargets.iterator(); iter
                    .hasNext();) {
                Activity activity = (Activity) iter.next();

                // Don't connect to something that is already a start event!
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
                            newProcess,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions(),
                            trans));
                }
            }

            performFinalConfiguration(cmd, startEventObject);
        }
        return;
    }

    /**
     * Create and add an end event if necessary.
     * 
     * Join the all exit path objects to the end event
     * 
     * @param cmd
     */
    private void addEndEvent(CompoundCommand cmd, Rectangle newArea) {
        //
        // If user has asked to create end event then do so.
        if (endEventObject == null && (refactorInfo.validationInfo
                & RefactorAsSubProcWizardInfo.SUBPROC_CREATE_ENDEVENT) != 0) {
            WidgetRGB fillColor =
                    ProcessWidgetColors.getInstance(getTargetSubProcessType())
                            .getGraphicalNodeColor(null,
                                    ProcessWidgetColors.END_EVENT_FILL);
            WidgetRGB lineColor =
                    ProcessWidgetColors.getInstance(getTargetSubProcessType())
                            .getGraphicalNodeColor(null,
                                    ProcessWidgetColors.END_EVENT_LINE);

            Dimension size =
                    new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                            ProcessWidgetConstants.END_EVENT_SIZE);

            Point loc = new Point(
                    newArea.right() + INSERT_EVENT_EXTRA_CX - (size.width / 2),
                    newArea.y + (ProcessWidgetConstants.TASK_HEIGHT_SIZE / 2)
                            - 1);

            endEventObject = ElementsFactory.createEvent(loc,
                    size,
                    newLane.getId(),
                    EventFlowType.FLOW_END_LITERAL,
                    EventTriggerType.EVENT_NONE_LITERAL,
                    fillColor.toString(),
                    lineColor.toString(),
                    null);

            cmd.append(AddCommand.create(editingDomain,
                    newProcess,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                    endEventObject));
        }

        //
        // At this point we either have an existing end event object, or a new
        // one or none at all.
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(getTargetSubProcessType())
                        .getGraphicalNodeColor(null,
                                ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE);

        // If we have a start event object, join all the exit path objects
        // (that we have either cut the connection to or didn't have one in the
        // first place).
        if (endEventObject != null) {
            // First for objects we have cut connections to.
            for (Iterator iter = exitPathSources.iterator(); iter.hasNext();) {
                Activity activity = (Activity) iter.next();

                // Don't connect to something that is already a end event!
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
                            newProcess,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions(),
                            trans));

                }
            }

            // Then the objects that didn't have outgoing flow in the first
            // place.
            for (Iterator iter = nonFlowExitPathSources.iterator(); iter
                    .hasNext();) {
                Activity activity = (Activity) iter.next();

                // Don't connect to something that is already a end event!
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
                            newProcess,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions(),
                            trans));
                }
            }

            performFinalConfiguration(cmd, endEventObject);
        }

        return;
    }

    /**
     * Move all the given objects into the independent sub-process along with
     * any transitions between them.
     * 
     * @param cmd
     * @param newProcessInvokeActivity
     * @param activitySet
     * @return Area occupied by content in new lane after readjusting offsets.
     */
    private Rectangle moveContentToSubProc(CompoundCommand cmd) {
        // Move the activity sets across first.
        if (activitySets != null) {
            for (ActivitySet actSet : activitySets) {
                cmd.append(ReparentListElementCommand.create(editingDomain,
                        actSet,
                        newProcess,
                        Xpdl2Package.eINSTANCE.getProcess_ActivitySets()));
            }
        }

        //
        // Then the activities and transitions etc.

        // Work out how much we need to shift content objects by.
        int xOffset = -contentBounds.x;
        int yOffset = -contentBounds.y;

        xOffset += CONTENT_MARGIN;
        yOffset += CONTENT_MARGIN;

        if ((refactorInfo.validationInfo
                & RefactorAsSubProcWizardInfo.SUBPROC_CREATE_STARTEVENT) != 0) {
            xOffset += INSERT_EVENT_EXTRA_CX;
        }

        for (EObject obj : objectList) {

            NodeGraphicsInfo gi = null;

            if (obj instanceof Activity) {
                Activity act = (Activity) obj;

                gi = Xpdl2ModelUtil.getNodeGraphicsInfo(act);

                //
                // Remove transitions from current container into the new
                // process
                // set (Must only move each transition ONCE so we will ONLY deal
                // with outgoing transitions (one object's incoming must be
                // another's outgoing so don't need to do both anyway)
                List transSet = act.getOutgoingTransitions();
                List<Transition> movedTrans =
                        new ArrayList<Transition>(transSet.size());

                for (Iterator iter = transSet.iterator(); iter.hasNext();) {
                    Transition transition = (Transition) iter.next();

                    // Only deal with transitions between objects in selected
                    // set.
                    if (objectMap.containsKey(transition.getFrom())
                            && objectMap.containsKey(transition.getTo())) {

                        cmd.append(RemoveCommand.create(editingDomain,
                                transition));

                        movedTrans.add(transition);
                    }
                }

                //
                // Remove the object from the old container.
                cmd.append(RemoveCommand.create(editingDomain, act));

                //
                // Add object to the new sub-process
                cmd.append(AddCommand.create(editingDomain,
                        newProcess,
                        Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                        act));

                //
                // Move it the object and reset the lane id to new lane.
                if (gi != null) {
                    offsetContentObject(cmd, xOffset, yOffset, gi);

                    // And Reset the lane id.
                    cmd.append(SetCommand.create(editingDomain,
                            gi,
                            Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_LaneId(),
                            newLane.getId()));

                }

                //
                // Add the moved transitions into new sub-process.
                for (Iterator iter = movedTrans.iterator(); iter.hasNext();) {
                    Transition transition = (Transition) iter.next();

                    cmd.append(AddCommand.create(editingDomain,
                            newProcess,
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions(),
                            transition));
                }

            } else if (obj instanceof Artifact) {
                // Artifacts stay at package level.
                Artifact art = (Artifact) obj;

                gi = Xpdl2ModelUtil.getNodeGraphicsInfo(art);

                //
                // Move it.
                if (gi != null) {
                    offsetContentObject(cmd, xOffset, yOffset, gi);
                }

                // Artifacts declare their parents via the graphics info lane id
                // attribute.
                cmd.append(SetCommand.create(editingDomain,
                        gi,
                        Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_LaneId(),
                        newLane.getId()));

            }
        }

        Rectangle newArea = contentBounds.getCopy();
        newArea.x += xOffset;
        newArea.y += yOffset;

        return newArea;
    }

    /**
     * @param cmd
     * @param xOffset
     * @param yOffset
     * @param gi
     */
    private void offsetContentObject(CompoundCommand cmd, int xOffset,
            int yOffset, NodeGraphicsInfo gi) {
        Coordinates coords = gi.getCoordinates();

        if (coords != null) {
            int newX = (int) coords.getXCoordinate() + xOffset;

            cmd.append(SetCommand.create(editingDomain,
                    coords,
                    Xpdl2Package.eINSTANCE.getCoordinates_XCoordinate(),
                    new Double(newX)));

            int newY = (int) coords.getYCoordinate() + yOffset;

            cmd.append(SetCommand.create(editingDomain,
                    coords,
                    Xpdl2Package.eINSTANCE.getCoordinates_YCoordinate(),
                    new Double(newY)));

        }
    }

    /**
     * If we found a definite start object then reconnect it's incoming
     * transitions to new sub-proc.
     * 
     * @param cmd
     * @param newProcessInvokeActivity
     */
    private void relinkEntryPoint(CompoundCommand cmd) {
        if (entryPathTargets.size() > 0) {
            // Redirect the entry point object's incoming transitions to new
            // sub-proc.
            for (Iterator actIter = entryPathTargets.iterator(); actIter
                    .hasNext();) {
                Activity activity = (Activity) actIter.next();

                List inTransitions = activity.getIncomingTransitions();

                for (Iterator transIter = inTransitions.iterator(); transIter
                        .hasNext();) {
                    Transition trans = (Transition) transIter.next();

                    // Don't relink incoming transitions that are from objects
                    // in the selection.
                    if (!objectMap.containsKey(trans.getFrom())) {

                        cmd.append(SetCommand.create(editingDomain,
                                trans,
                                Xpdl2Package.eINSTANCE.getTransition_To(),
                                newProcessInvokeActivity.getId()));

                        // Remove the fixed-pos end anchor (i.e. use default
                        // anchor positioning)
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

        return;
    }

    /**
     * If we found a definite end object then reconnect it's outgoing
     * transitions from new-sub-proc.
     * 
     * @param cmd
     */
    private void relinkExitPoint(CompoundCommand cmd) {
        if (relinkExitPoints.size() > 0) {

            for (Iterator actTrans = relinkExitPoints.iterator(); actTrans
                    .hasNext();) {
                Activity activity = (Activity) actTrans.next();

                // Redirect the exit point object's outgoing transitions to new
                // sub-proc.
                List outTransitions = activity.getOutgoingTransitions();

                for (Iterator transIter = outTransitions.iterator(); transIter
                        .hasNext();) {
                    Transition trans = (Transition) transIter.next();

                    // Don't relink outgoing transitions that are to objects in
                    // the selection.
                    if (!objectMap.containsKey(trans.getTo())) {

                        cmd.append(SetCommand.create(editingDomain,
                                trans,
                                Xpdl2Package.eINSTANCE.getTransition_From(),
                                newProcessInvokeActivity.getId()));

                        // Remove the fixed-pos start anchor (i.e. use
                        // default
                        // anchor positioning)
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

        return;
    }

    /**
     * Relink any associations from / to objects in the selection to the new
     * embedded sub-process.
     * 
     * @param cmd
     */
    private void relinkAssociations(CompoundCommand cmd) {
        Package pkg = parentProcess.getPackage();

        List associations = pkg.getAssociations();

        HashSet<String> alreadyAssociated = new HashSet<String>();

        for (Iterator iter = associations.iterator(); iter.hasNext();) {
            Association ass = (Association) iter.next();

            // Check if association to/from an object in the selection.
            EObject fromObject = objectMap.get(ass.getSource());
            if (fromObject == null) {
                // Association could be from sequence/message flow.
                // If so we count flow as "in selection" if the flow is between
                // objects in selection.
                EObject src = pkg.findNamedElement(ass.getSource());

                if (src instanceof Transition || src instanceof MessageFlow) {
                    String connSrc = null;
                    String connTgt = null;

                    if (src instanceof Transition) {
                        connSrc = ((Transition) src).getFrom();
                        connTgt = ((Transition) src).getTo();

                    } else if (src instanceof MessageFlow) {
                        connSrc = ((MessageFlow) src).getSource();
                        connTgt = ((MessageFlow) src).getTarget();
                    }

                    if (objectMap.containsKey(connSrc)
                            && objectMap.containsKey(connTgt)) {
                        // Association to a flow between objects within the
                        // selection, that means that the flow will be moving
                        // too. Therefore same rules apply as for object that's
                        // moving.
                        fromObject = src;
                    }
                }
            }

            EObject toObject = objectMap.get(ass.getTarget());
            if (toObject == null) {
                // Association could be to sequence/message flow.
                // If so we count flow as "in selection" if the flow is between
                // objects in selection.
                EObject tgt = pkg.findNamedElement(ass.getTarget());

                if (tgt instanceof Transition || tgt instanceof MessageFlow) {
                    String connSrc = null;
                    String connTgt = null;

                    if (tgt instanceof Transition) {
                        connSrc = ((Transition) tgt).getFrom();
                        connTgt = ((Transition) tgt).getTo();

                    } else if (tgt instanceof MessageFlow) {
                        connSrc = ((MessageFlow) tgt).getSource();
                        connTgt = ((MessageFlow) tgt).getTarget();
                    }

                    if (objectMap.containsKey(connSrc)
                            && objectMap.containsKey(connTgt)) {
                        // Association to a flow between objects within the
                        // selection, that means that the flow will be moving
                        // too. Therefore same rules apply as for object that's
                        // moving.
                        toObject = tgt;
                    }
                }
            }

            if (fromObject != null) {
                // Association from object that is being moved to sub-proc.
                // If to Object is null then it could be because this

                if (toObject == null) {
                    // Association to object outside of selection.
                    // Redirect it to new independent sub-proc call task.

                    if (alreadyAssociated.contains(ass.getTarget())) {
                        // More than one object inside the selection has an
                        // association to this object outside the selection.
                        // Don't want multiple associations to/from same object
                        // so delete this one.
                        cmd.append(RemoveCommand.create(editingDomain, ass));

                    } else {
                        alreadyAssociated.add(ass.getTarget());

                        cmd.append(SetCommand.create(editingDomain,
                                ass,
                                Xpdl2Package.eINSTANCE.getAssociation_Source(),
                                newProcessInvokeActivity.getId()));

                        // Remove the fixed-pos start anchor (i.e. use default
                        // anchor positioning)
                        ConnectorGraphicsInfo gConnectorInfo =
                                Xpdl2ModelUtil.getConnectorGraphicsInfo(ass,
                                        Xpdl2ModelUtil.START_ANCHOR_IDSUFFIX);

                        if (gConnectorInfo != null) {
                            cmd.append(RemoveCommand.create(editingDomain,
                                    gConnectorInfo));
                        }
                    }
                }

            } else if (toObject != null) {
                // Association to object that is being moved to sub-proc.
                if (fromObject == null) {
                    // Association from object outside of selection.
                    // Redirect it to new indi sub-proc object.

                    if (alreadyAssociated.contains(ass.getSource())) {
                        // More than one object inside the selection has an
                        // association to this object outside the selection.
                        // Don't want multiple associations to/from same object
                        // so delete this one.
                        cmd.append(RemoveCommand.create(editingDomain, ass));

                    } else {
                        alreadyAssociated.add(ass.getSource());

                        cmd.append(SetCommand.create(editingDomain,
                                ass,
                                Xpdl2Package.eINSTANCE.getAssociation_Target(),
                                newProcessInvokeActivity.getId()));

                        // Remove the fixed-pos start anchor (i.e. use default
                        // anchor positioning)
                        ConnectorGraphicsInfo gConnectorInfo =
                                Xpdl2ModelUtil.getConnectorGraphicsInfo(ass,
                                        Xpdl2ModelUtil.START_ANCHOR_IDSUFFIX);

                        if (gConnectorInfo != null) {
                            cmd.append(RemoveCommand.create(editingDomain,
                                    gConnectorInfo));
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * Relink any message flows from / to objects in the selection to the new
     * embedded sub-process.
     * 
     * @param cmd
     */
    private void relinkMessageFlows(CompoundCommand cmd) {
        Package pkg = parentProcess.getPackage();

        List messageFlows = pkg.getMessageFlows();

        HashMap<Pool, Pool> extraPools = new HashMap<Pool, Pool>();

        List<MessageFlow> extraMessageFlows = new ArrayList<MessageFlow>();

        for (Iterator iter = messageFlows.iterator(); iter.hasNext();) {
            MessageFlow msgFlow = (MessageFlow) iter.next();

            // Check if message to/from an object in the selection.
            EObject fromObject = objectMap.get(msgFlow.getSource());

            EObject toObject = objectMap.get(msgFlow.getTarget());

            if (fromObject != null) {
                // MessageFlow is FROM object that is being moved to sub-proc.
                if (toObject == null) {
                    // MessageFlow is to object outside of selection.

                    // We have several things to do.
                    // - Relink the current message flow to the independent
                    // sub-process call activity.
                    //
                    // - Setup an extra pool to add to the new sub-process.
                    //
                    // - Create a new message flow to link the original
                    // activity to the extra pool.

                    //
                    // Relink it.
                    cmd.append(SetCommand.create(editingDomain,
                            msgFlow,
                            Xpdl2Package.eINSTANCE.getMessageFlow_Source(),
                            newProcessInvokeActivity.getId()));

                    // Remove the fixed-pos start anchor (i.e. use default
                    // anchor positioning)
                    ConnectorGraphicsInfo gConnectorInfo =
                            Xpdl2ModelUtil.getConnectorGraphicsInfo(msgFlow,
                                    Xpdl2ModelUtil.START_ANCHOR_IDSUFFIX);

                    if (gConnectorInfo != null) {
                        cmd.append(RemoveCommand.create(editingDomain,
                                gConnectorInfo));
                    }

                    //
                    // Add the target pool if not already done so.
                    Pool targetPool = null;

                    String targetId = msgFlow.getTarget();
                    toObject = pkg.findNamedElement(targetId);

                    targetPool = Xpdl2ModelUtil.getParentPool(toObject);

                    if (targetPool != null) {
                        Pool extraPool = extraPools.get(targetPool);

                        if (extraPool == null) {
                            // We don't already have this down as an extra pool,
                            // create a new one.
                            extraPool = createPoolCopy(targetPool);
                            extraPool.setProcessId(newProcess.getId());

                            extraPools.put(targetPool, extraPool);
                        }

                        //
                        // Create a new messageflow from the original object to
                        // new pool in
                        MessageFlow newFlow =
                                createMessageFlowCopy(msgFlow, false, true);
                        newFlow.setTarget(extraPool.getId());

                        extraMessageFlows.add(newFlow);
                    }

                }

            } else if (toObject != null) {
                // Message flow is TO object that is being moved to sub-proc.
                if (fromObject == null) {
                    // Message flow is from object outside of selection.

                    // We have several things to do.
                    // - Relink the current message flow to the independent
                    // sub-process call activity.
                    //
                    // - Setup an extra pool to add to the new sub-process.
                    //
                    // - Create a new message flow to link the original
                    // activity to the extra pool.

                    //
                    // Relink it.
                    cmd.append(SetCommand.create(editingDomain,
                            msgFlow,
                            Xpdl2Package.eINSTANCE.getMessageFlow_Target(),
                            newProcessInvokeActivity.getId()));

                    // Remove the fixed-pos end anchor (i.e. use default
                    // anchor positioning)
                    ConnectorGraphicsInfo gConnectorInfo =
                            Xpdl2ModelUtil.getConnectorGraphicsInfo(msgFlow,
                                    Xpdl2ModelUtil.END_ANCHOR_IDSUFFIX);

                    if (gConnectorInfo != null) {
                        cmd.append(RemoveCommand.create(editingDomain,
                                gConnectorInfo));
                    }

                    //
                    // Add the target pool if not already done so.
                    Pool targetPool = null;

                    String sourceId = msgFlow.getSource();
                    fromObject = pkg.findNamedElement(sourceId);

                    targetPool = Xpdl2ModelUtil.getParentPool(fromObject);

                    if (targetPool != null) {
                        Pool extraPool = extraPools.get(targetPool);

                        if (extraPool == null) {
                            // We don't already have this down as an extra pool,
                            // create a new one.
                            extraPool = createPoolCopy(targetPool);
                            extraPool.setProcessId(newProcess.getId());

                            extraPools.put(targetPool, extraPool);
                        }

                        //
                        // Create a new messageflow from the original object to
                        // new pool in
                        MessageFlow newFlow =
                                createMessageFlowCopy(msgFlow, true, false);
                        newFlow.setSource(extraPool.getId());

                        extraMessageFlows.add(newFlow);
                    }
                }
            }
        }

        //
        // Now add the extra pools and message flows to the new process.
        for (Pool extraPool : extraPools.values()) {
            cmd.append(AddCommand.create(editingDomain,
                    pkg,
                    Xpdl2Package.eINSTANCE.getPackage_Pools(),
                    extraPool));
        }

        for (MessageFlow extraFlow : extraMessageFlows) {
            cmd.append(AddCommand.create(editingDomain,
                    pkg,
                    Xpdl2Package.eINSTANCE.getPackage_MessageFlows(),
                    extraFlow));
        }

        return;
    }

    /**
     * @param msgFlow
     * @return
     */
    private MessageFlow createMessageFlowCopy(MessageFlow curMsgFlow,
            boolean removeStartAnchor, boolean removeEndAnchor) {
        Command cmd = CopyCommand.create(editingDomain, curMsgFlow);
        cmd.execute();

        MessageFlow newFlow = (MessageFlow) cmd.getResult().toArray()[0];

        newFlow.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                XpdEcoreUtil.generateUUID());

        // Remove bend point coordinates and start / end anchor
        ConnectorGraphicsInfo gi =
                Xpdl2ModelUtil.getConnectorGraphicsInfo(newFlow);

        if (gi != null) {
            List coords = gi.getCoordinates();

            if (coords != null && coords.size() > 0) {
                Command rcmd = RemoveCommand.create(editingDomain, coords);
                rcmd.execute();
            }
        }

        if (removeStartAnchor) {
            gi = Xpdl2ModelUtil.getConnectorGraphicsInfo(newFlow,
                    Xpdl2ModelUtil.START_ANCHOR_IDSUFFIX);
            if (gi != null) {
                Command rcmd = RemoveCommand.create(editingDomain, gi);
                rcmd.execute();
            }
        }

        if (removeEndAnchor) {
            gi = Xpdl2ModelUtil.getConnectorGraphicsInfo(newFlow,
                    Xpdl2ModelUtil.END_ANCHOR_IDSUFFIX);
            if (gi != null) {
                Command rcmd = RemoveCommand.create(editingDomain, gi);
                rcmd.execute();
            }
        }

        return newFlow;
    }

    /**
     * Create a copy of the given pool for the new process. Without lanes.
     * 
     * @param curPool
     * @return
     */
    private Pool createPoolCopy(Pool curPool) {

        Command cmd = CopyCommand.create(editingDomain, curPool);
        cmd.execute();

        Pool poolCopy = (Pool) cmd.getResult().toArray()[0];

        poolCopy.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                XpdEcoreUtil.generateUUID());

        Collection lanes = poolCopy.getLanes();

        if (lanes != null && lanes.size() > 0) {
            Command rcmd = RemoveCommand.create(editingDomain, lanes);
            rcmd.execute();
        }

        return poolCopy;
    }

    /**
     * Setup the new independent sub-process and append necessary commands to
     * cmd.
     * 
     * 
     * @return
     */
    private void setupNewProcess(CompoundCommand cmd) {

        //
        // Add the new process.
        Package pkg = Xpdl2ModelUtil.getPackage(parentProcess);

        newProcess = createNewProcess();

        /*
         * XPD-7828: Use the user entered value for label and getInternalName()
         * (that removes the spaces) for name
         */
        String baseName = refactorInfo.subprocName; // NameUtil.getInternalName(refactorInfo.subprocName,
                                                    // false);
        String finalName = baseName;
        int idx = 1;
        while (Xpdl2ModelUtil.getDuplicateDisplayNameProcess(pkg,
                newProcess,
                finalName) != null
                || Xpdl2ModelUtil.getDuplicateProcess(pkg,
                        newProcess,
                        NameUtil.getInternalName(finalName, false)) != null) {
            idx++;
            finalName = baseName + idx;
        }
        newProcess.setName(NameUtil.getInternalName(finalName, false));
        Xpdl2ModelUtil.setOtherAttribute(newProcess,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                finalName);

        cmd.append(AddCommand.create(editingDomain,
                pkg,
                Xpdl2Package.eINSTANCE.getPackage_Processes(),
                newProcess));

        //
        // Create Pool.
        newPool = createNewPool();

        cmd.append(AddCommand.create(editingDomain,
                pkg,
                Xpdl2Package.eINSTANCE.getPackage_Pools(),
                newPool));

        //
        // Add lane to pool.
        newLane = createNewLane();

        cmd.append(AddCommand.create(editingDomain,
                newPool,
                Xpdl2Package.eINSTANCE.getPool_Lanes(),
                newLane));

        if (refactoringSingleEmbSubProc) {
            //
            // Refactoring from single emb sub-proc - we will convert existing
            // into indi sub-proc.
            BlockActivity ba = newProcessInvokeActivity.getBlockActivity();

            // BEfore removing block activity unset the id of activity set (this
            // should ensure that visuals are refreshed before children become
            // orphaned).
            cmd.append(SetCommand.create(editingDomain,
                    ba,
                    Xpdl2Package.eINSTANCE.getBlockActivity_ActivitySetId(),
                    "")); //$NON-NLS-1$

            cmd.append(RemoveCommand.create(editingDomain, ba));

            Dimension size = configureProcessInvokeActivity(cmd,
                    newProcessInvokeActivity);

            cmd.append(SetCommand.create(editingDomain,
                    newProcessInvokeActivity,
                    Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                    refactorInfo.subprocName));

            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                    newProcessInvokeActivity,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                    refactorInfo.subprocName));

            NodeGraphicsInfo gi = Xpdl2ModelUtil
                    .getNodeGraphicsInfo(newProcessInvokeActivity);
            if (gi != null) {
                Coordinates coords = gi.getCoordinates();

                org.eclipse.swt.graphics.Rectangle tmpBnds = Xpdl2ModelUtil
                        .getObjectBounds(newProcessInvokeActivity);
                Rectangle rc = new Rectangle(tmpBnds.x, tmpBnds.y,
                        tmpBnds.width, tmpBnds.height);

                cmd.append(SetCommand.create(editingDomain,
                        coords,
                        Xpdl2Package.eINSTANCE.getCoordinates_XCoordinate(),
                        new Double(rc.x + (size.width / 2))));

                cmd.append(SetCommand.create(editingDomain,
                        coords,
                        Xpdl2Package.eINSTANCE.getCoordinates_YCoordinate(),
                        new Double(rc.y + (size.height / 2))));

                cmd.append(SetCommand.create(editingDomain,
                        gi,
                        Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Width(),
                        new Double(size.width)));

                cmd.append(SetCommand.create(editingDomain,
                        gi,
                        Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Height(),
                        new Double(size.height)));
            }

        } else {
            //
            // Add new sub-proc to source lane / activity set.
            // Lane Id is null if container is not a lane.
            String laneId = null;
            if (parentContainer instanceof Lane) {
                laneId = ((Lane) parentContainer).getId();
            }

            Dimension size = configureProcessInvokeActivity(cmd,
                    newProcessInvokeActivity);

            NodeGraphicsInfo gi = Xpdl2ModelUtil
                    .getNodeGraphicsInfo(newProcessInvokeActivity);

            gi.setLaneId(laneId);

            Coordinates coords = Xpdl2Factory.eINSTANCE.createCoordinates();
            coords.setXCoordinate(contentBounds.x + (size.width / 2));
            coords.setYCoordinate(contentBounds.y + (size.height / 2));
            gi.setCoordinates(coords);

            gi.setWidth(size.width);
            gi.setHeight(size.height);

            newProcessInvokeActivity.setName(
                    NameUtil.getInternalName(refactorInfo.subprocName, false));
            Xpdl2ModelUtil.setOtherAttribute(newProcessInvokeActivity,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                    refactorInfo.subprocName);

            if ((refactorInfo.validationInfo
                    & RefactorAsSubProcWizardInfo.SUBPROC_IS_TRANSACTION) != 0) {
                newProcessInvokeActivity.setIsATransaction(true);
            }

            // Add command to add new task to process or activity set.
            if (parentContainer instanceof Lane) {
                cmd.append(AddCommand.create(editingDomain,
                        Xpdl2ModelUtil.getProcess(parentContainer),
                        Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                        newProcessInvokeActivity));

            } else {
                // Activity set.
                cmd.append(AddCommand.create(editingDomain,
                        parentContainer,
                        Xpdl2Package.eINSTANCE.getFlowContainer_Activities(),
                        newProcessInvokeActivity));

            }
        }

        return;
    }

    /**
     * Append commands to configure the activity to invoke the new process.
     * 
     * @param cmd
     * @param newProcessInvokeActivity
     * 
     * @return size
     */
    protected abstract Dimension configureProcessInvokeActivity(
            CompoundCommand cmd, Activity newProcessInvokeActivity);

    /**
     * Pre-create the independent sub-proc call activity (so that we can return
     * it to caller via getNewIndependentSubProcObject()).
     */
    private void preCreateIndSubProcTask() {
        // If single embedded sub-proc is selected then convert it to
        // independent ratehr than create a new one.
        if (refactoringSingleEmbSubProc) {
            // We'll leave swapping over to indi sub-proc until setup.

        } else {
            //
            // Create independent sub-proc object.
            WidgetRGB fillColor = ProcessWidgetColors
                    .getInstance(sourceProcessType).getGraphicalNodeColor(null,
                            ProcessWidgetColors.SUBPROCESS_TASK_FILL);
            WidgetRGB lineColor = ProcessWidgetColors
                    .getInstance(sourceProcessType).getGraphicalNodeColor(null,
                            ProcessWidgetColors.SUBPROCESS_TASK_LINE);

            newProcessInvokeActivity =
                    ElementsFactory.createTask(contentBounds.getCenter(),
                            contentBounds.getSize(),
                            "", //$NON-NLS-1$
                            TaskType.NONE_LITERAL,
                            fillColor.toString(),
                            lineColor.toString());
        }
    }

    /**
     * Create the pool for new sub-process and pick up some properties etc from
     * the source pool.
     * 
     * @return
     */
    private Pool createNewPool() {

        Pool pool = ElementsFactory.createPool(parentPool.getName(),
                newProcess.getId());

        NodeGraphicsInfo poolGI =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(pool);

        NodeGraphicsInfo curGI = Xpdl2ModelUtil.getNodeGraphicsInfo(parentPool);
        if (curGI != null) {
            String colorStr = curGI.getFillColor();
            if (colorStr != null) {
                poolGI.setFillColor(colorStr);
            }

            colorStr = curGI.getBorderColor();
            if (colorStr != null) {
                poolGI.setBorderColor(colorStr);
            }

        }

        return pool;
    }

    /**
     * Create the lane for new sub-process and pick up some properties etc from
     * the source lane.
     * 
     * @return
     */
    private Lane createNewLane() {
        Lane lane = Xpdl2Factory.eINSTANCE.createLane();
        lane.setName(parentLane.getName());
        EAttribute ea =
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName();
        Xpdl2ModelUtil.setOtherAttribute(lane,
                ea,
                Xpdl2ModelUtil.getOtherAttribute(parentLane, ea));

        NodeGraphicsInfo gi = Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(lane);

        NodeGraphicsInfo curGI = Xpdl2ModelUtil.getNodeGraphicsInfo(parentLane);
        if (curGI != null) {
            String colorStr = curGI.getFillColor();
            if (colorStr != null) {
                gi.setFillColor(colorStr);
            }

            colorStr = curGI.getBorderColor();
            if (colorStr != null) {
                gi.setBorderColor(colorStr);
            }

        }

        gi.setHeight(contentBounds.height + (2 * CONTENT_MARGIN));

        return lane;
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
    private int checkIncomingFlows() {
        int ret = 0;

        nonFlowEntryPathTargets = new ArrayList<Activity>();

        entryPathTargets = new ArrayList<Activity>();

        refactorInfo.entryPathActsAndTrans.clear();

        // Find the start object (object with incoming transition from outside
        // of selection).
        for (EObject nodeObject : objectList) {

            if (nodeObject instanceof Activity) {
                Activity activity = (Activity) nodeObject;

                // If this object is a start event
                Event ev = activity.getEvent();
                if (ev instanceof StartEvent) {
                    startEventObject = activity;
                    ret |= RefactorAsSubProcWizardInfo.SUBPROC_EXISTING_STARTEVENT;
                }

                List inFlows = activity.getIncomingTransitions();

                if (inFlows != null && inFlows.size() > 0) {
                    // Check sequence flows that this object is the target of...
                    for (Iterator iter = inFlows.iterator(); iter.hasNext();) {
                        Transition flow = (Transition) iter.next();

                        Activity sourceNode = flow.getFlowContainer()
                                .getActivity(flow.getFrom());

                        if (sourceNode != null) {
                            // If the source object is NOT within selected
                            // objects then this is the start object.
                            if (!objectMap.containsKey(sourceNode.getId())) {
                                // Only add once.
                                if (!entryPathTargets.contains(activity)) {
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
                    if (shouldConnectNonFlowToStartAndEnd(activity)) {

                        nonFlowEntryPathTargets.add(activity);
                        refactorInfo.entryPathActsAndTrans.add(activity);
                    }
                }
            }
        }

        if (entryPathTargets.size() > 1) {
            // There are multiple potential entry points.
            ret = RefactorAsSubProcWizardInfo.SUBPROC_MULTIPLE_ENTRYPATHS;

        } else if (entryPathTargets.size() > 0
                && nonFlowEntryPathTargets.size() > 0) {
            // there is at least one definitive entry path + other unconnected
            // ones
            ret = RefactorAsSubProcWizardInfo.SUBPROC_MULTIPLE_ENTRYPATHS;
        } else if (entryPathTargets.size() == 1) {
            // If there is ONE definitive incoming path then default to
            // creating start event.
            ret = RefactorAsSubProcWizardInfo.SUBPROC_CREATE_STARTEVENT;
        }

        if (singleSelAdHocTaskConfiguration != null) {

            return RefactorAsSubProcWizardInfo.SUBPROC_CREATE_STARTEVENT;
        }

        return ret;
    }

    /**
     * Checks if the given non flow Activity (missing incoming/outgoing) should
     * be merged to the common Start/End flow.
     * 
     * @param activity
     * @return <code>true</code> if the Activity should be added as non flow
     *         target else <code>false</code>
     */
    protected boolean shouldConnectNonFlowToStartAndEnd(Activity activity) {
        /*
         * This sequenceflow capable object has no incoming flows. This 'may'
         * become the start object
         * 
         * Unless it is an event attached to task border and the task in
         * question is also in the selection.
         */
        boolean isBorderEvent = false;

        if (activity.getEvent() instanceof IntermediateEvent) {
            String taskId =
                    ((IntermediateEvent) activity.getEvent()).getTarget();

            if (taskId != null && taskId.length() > 0) {
                // Its an event attached to task border.
                // Finally check if given task is in selection.
                if (objectMap.containsKey(taskId)) {
                    isBorderEvent = true;
                }
            }
        }

        /* If Not a Border Event check further */
        if (!isBorderEvent) {
            /*
             * True if this is not the Single Ad-Hoc Activity refactor or does
             * not contain Standalone Tasks like Ad-Hoc activity, Event
             * SubProcess or Event handler
             */
            if ((singleSelAdHocTaskConfiguration == null)
                    && ((Xpdl2ModelUtil.getOtherElement(
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AdHocTaskConfiguration()) != null)
                            || (TaskType.EVENT_SUBPROCESS_LITERAL.equals(
                                    TaskObjectUtil.getTaskTypeStrict(activity)))
                            || (Xpdl2ModelUtil.isEventHandlerActivity(activity))
                            || isEventHandlerFlowWithAllUpStreamElementsSelected(
                                    activity))) {

                return false;
            }
        }

        return !isBorderEvent;
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
    private int checkOutgoingFlows() {
        int ret = 0;
        nonFlowExitPathSources = new ArrayList<Activity>();

        exitPathSources = new ArrayList<Activity>();
        relinkExitPoints = new ArrayList<Activity>();

        refactorInfo.exitPathActsAndTrans.clear();

        // Find the end object (object with outgoing transition from outside
        // of selection).
        for (EObject nodeObject : objectList) {

            if (nodeObject instanceof Activity) {
                Activity activity = (Activity) nodeObject;

                // If this object is a end event the store it
                Event ev = activity.getEvent();
                /* Exclude End Events part of Event Flow Handler */
                /*
                 * XPD-7400: Only connect to the existing end event if it is not
                 * attached to event handler flow or to an boundary event.
                 */
                if (ev instanceof EndEvent
                        && !isEventConnectedToSelectedBoundaryEvent(activity,
                                objectMap)

                        && !isEventHandlerFlowWithAllUpStreamElementsSelected(
                                activity)) {

                    endEventObject = activity;
                    ret |= RefactorAsSubProcWizardInfo.SUBPROC_EXISTING_ENDEVENT;
                }

                List outFlows = activity.getOutgoingTransitions();

                if (outFlows != null && outFlows.size() > 0) {
                    /**
                     * If there are outgoing from ythis activity then check
                     * whether they are to activities that are OUTSIDE of the
                     * selected activity set.
                     * 
                     * If so then we should join them to the end event of the
                     * new sub-process
                     */

                    // Check sequence flows that this object is the source of...
                    for (Iterator iter = outFlows.iterator(); iter.hasNext();) {
                        Transition flow = (Transition) iter.next();

                        Activity targetNode = flow.getFlowContainer()
                                .getActivity(flow.getTo());

                        if (targetNode != null) {
                            // If the target object is NOT within selected
                            // objects then this is an end object.
                            if (!objectMap.containsKey(targetNode.getId())) {
                                // Only add once.
                                if (!exitPathSources.contains(activity)) {

                                    if (shouldConnectInFlowToStartAndEnd(
                                            activity)) {
                                        exitPathSources.add(activity);

                                        refactorInfo.exitPathActsAndTrans
                                                .add(flow);
                                        refactorInfo.exitPathActsAndTrans
                                                .add(activity);
                                    }
                                    /*
                                     * Collect to relink exit point in Main
                                     * process
                                     */
                                    if (!relinkExitPoints.contains(activity)) {
                                        relinkExitPoints.add(activity);
                                    }
                                }

                            }
                        }
                    }

                } else {

                    if (!(ev instanceof EndEvent)
                            && shouldConnectNonFlowToStartAndEnd(activity)) {

                        nonFlowExitPathSources.add(activity);
                        refactorInfo.exitPathActsAndTrans.add(activity);
                    }
                }
            }
        }

        if (exitPathSources.size() > 1) {
            // There are multiple definitive exit points.
            ret = RefactorAsSubProcWizardInfo.SUBPROC_MULTIPLE_EXITPATHS;

        } else if (exitPathSources.size() > 0
                && nonFlowExitPathSources.size() > 0) {
            // there is at least one definitive exit path + other unconnected
            // ones
            ret = RefactorAsSubProcWizardInfo.SUBPROC_MULTIPLE_EXITPATHS;
        } else if (exitPathSources.size() == 1) {
            // If there is ONE definitive exit path path then default to
            // creating endevent.
            ret = RefactorAsSubProcWizardInfo.SUBPROC_CREATE_ENDEVENT;
        }

        /*
         * If we have a single Adhoc activity to refactor then we should create
         * a end event after it.
         */
        if (singleSelAdHocTaskConfiguration != null) {

            return RefactorAsSubProcWizardInfo.SUBPROC_CREATE_ENDEVENT;
        }

        return ret;
    }

    /**
     * Populates the passed Set 'upstreamActivities' with all the activites that
     * are upstream of the passed 'activity'.
     * 
     * @param activity
     * @param upstreamActivities
     */
    private static void populateUpstreamActivities(Activity activity,
            HashSet<Activity> upstreamActivities) {

        /**
         * XPD-8211 - prevent stack overflow when refactoring embedded
         * sub-process. when selected activities include end event and flow
         * upstream of selection contains a loop
         * 
         * Did not used to check whether we'd already processed an upstream
         * activity before recursing into it
         */
        if (upstreamActivities.contains(activity)) {
            return;
        }

        EList<Transition> incomingTransitions =
                activity.getIncomingTransitions();

        for (Transition transition : incomingTransitions) {

            String fromActivityId = transition.getFrom();

            Activity activityById = Xpdl2ModelUtil
                    .getActivityById(activity.getProcess(), fromActivityId);

            upstreamActivities.add(activityById);
            /*
             * recursively populate the upstream activities.
             */
            populateUpstreamActivities(activityById, upstreamActivities);

        }
    }

    /**
     * @param activity
     * @param objsSelectedForRefactoring
     * @return <code>true</code> if the passed activity has an upstream Boundary
     *         event which is also selected for refactoring.
     */
    protected static boolean isEventConnectedToSelectedBoundaryEvent(
            Activity activity,
            HashMap<String, EObject> objsSelectedForRefactoring) {

        /*
         * Note that we cannot use 'ProcessFlowAnalyser' here to get the
         * upstream boundary as the constructor of 'ProcessFlowAnalyser' should
         * be passed false to get the boundary events, but if we pass it false
         * then ProcessFlowAnalyser.getUpstreamActivities() throws error, see
         * ProcessFlowAnalyser.getUpstreamActivities()
         */

        HashSet<Activity> upstreamActivities = new HashSet<Activity>();
        /*
         * populate the has set with all upstream activities
         */
        populateUpstreamActivities(activity, upstreamActivities);

        for (Activity act : upstreamActivities) {
            /*
             * Check if any of the activities is Attached to task
             */
            if (Xpdl2ModelUtil.isEventAttachedToTask(act)) {
                /*
                 * Check if the event attached to task is also selected for
                 * refactoring.
                 */
                if (objsSelectedForRefactoring.containsValue(act)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check rules on objects.
     * <p>
     * <li>All objects must have same parent lane / independent sub-process</li>
     * </p>
     * 
     * @return true on success false on failure.
     */
    private boolean checkContainment() {
        boolean ret = true;

        parentContainer = null;
        parentPool = null;
        parentLane = null;

        if (refactoringSingleEmbSubProc) {
            String activitySetId = newProcessInvokeActivity.getBlockActivity()
                    .getActivitySetId();
            parentContainer = parentProcess.getActivitySet(activitySetId);

            if (parentContainer instanceof ActivitySet) {
                parentLane = Xpdl2ModelUtil.getActivitySetParentLane(
                        (ActivitySet) parentContainer);
            } else {
                parentLane = (Lane) parentContainer;
            }

            parentPool = parentLane.getParentPool();

        } else {
            // Find the end object (object with outgoing transition from outside
            // of selection).
            for (EObject nodeObject : objectList) {

                EObject thisContainer = Xpdl2ModelUtil.getContainer(nodeObject);

                if (parentContainer == null) {
                    parentContainer = thisContainer;

                    if (parentContainer instanceof ActivitySet) {
                        parentLane = Xpdl2ModelUtil.getActivitySetParentLane(
                                (ActivitySet) parentContainer);
                    } else {
                        parentLane = (Lane) parentContainer;
                    }

                    parentPool = parentLane.getParentPool();

                } else if (thisContainer != parentContainer) {
                    ret = false;

                    MessageDialog.openError(getShell(),
                            Messages.RefactorAsIndependentSubProcCommand_RefactorAsIndiSubprocMixedSourceError_title2,
                            Messages.RefactorAsIndependentSubProcCommand_RefactorAsIndiSubprocMixedSourceError_message2);
                    break;
                }

                if (nodeObject instanceof Activity) {
                    Activity activity = (Activity) nodeObject;

                    // Check for Task border event selected without task itself
                    // selected.
                    boolean isBorderEvent = false;

                    if (activity.getEvent() instanceof IntermediateEvent) {
                        String taskId =
                                ((IntermediateEvent) activity.getEvent())
                                        .getTarget();

                        if (taskId != null && taskId.length() > 0) {
                            // Its an event attached to task border.
                            // Finally check if given task is in selection.
                            if (!objectMap.containsKey(taskId)) {
                                ret = false;

                                MessageDialog.openError(getShell(),
                                        Messages.RefactorAsIndependentSubProcCommand_RefactorAsIndiSubprocMixedSourceError_title2,
                                        Messages.RefactorAsEmbeddedSubProcCommand_DisallowEventOnTaskBorder_longdesc);
                                break;
                            }
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

        // We have to validate AND complain when asked if we can execute. This
        // is because if we return true from can execute then the command will
        // ALWAYS get added to the undo stack (we can't just return false from
        // canUndo because the entry is still put on command stack but is
        // disabled).

        // So make sure we only message box once in case we get called multiple
        // times.
        if (validationAttempted) {
            // Already done validation once, just return according to last
            // result.
            if (validationResult == -1) {
                return false;
            } else {
                return true;
            }
        }

        validateRefactor();

        if (validationResult != -1) {
            refactorInfo.validationInfo = validationResult;

            // If refactoring a single object then use its name in preference to
            // the default "Independent Sub-Process"
            if (refactorInfo.selectedObjects.size() == 1) {
                EObject eo = refactorInfo.selectedObjects.get(0);
                if (eo instanceof NamedElement) {
                    String name = ((NamedElement) eo).getName();
                    if (name != null && name.length() > 0) {
                        refactorInfo.subprocName = new String(name);
                    }
                }
            } else if (refactoringSingleEmbSubProc) {
                refactorInfo.subprocName =
                        new String(newProcessInvokeActivity.getName());
            }

            BaseRefactorAsSubProcWizard wizard = createRefactorWizard();

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
     * @return the refactorInfo
     */
    protected RefactorAsIndiSubprocWizardInfo getRefactorInfo() {
        return refactorInfo;
    }

    /**
     * @return the editingDomain
     */
    protected EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /**
     * @return the newProcess
     */
    protected Process getNewProcess() {
        return newProcess;
    }

    /**
     * @return the allSelActsAndTrans
     */
    protected Collection<EObject> getAllSelActsAndTrans() {
        return allSelActsAndTrans;
    }

    /**
     * @return the parentProcess
     */
    protected Process getParentProcess() {
        return parentProcess;
    }

    /**
     * @return The process refactor wizard.
     */
    protected abstract BaseRefactorAsSubProcWizard createRefactorWizard();

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
    public Collection<?> getResult() {
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
     * This method decides if the given Inflow Activity should be connected to
     * Start/End Event. This method id called when the target/source of the
     * given Activity is not in the selection.An inflow Activity should not be
     * connected if it is part of an Event Handler Flow OR the complete Event
     * handler flow is selected.
     * 
     * @return
     */

    protected boolean shouldConnectInFlowToStartAndEnd(Activity activity) {

        /*
         * An inflow Activity should not be connected if it is part of an Event
         * Handler Flow and the complete Event handler flow till this Activity
         * is selected.
         */
        boolean eventHandlerFlowSelected = Xpdl2ModelUtil
                .isEventHandlerActivity(activity)
                || isEventHandlerFlowWithAllUpStreamElementsSelected(activity);

        return !eventHandlerFlowSelected;
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

}
