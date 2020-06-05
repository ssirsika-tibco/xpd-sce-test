package com.tibco.bx.xpdl2bpel.analyzer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.xpdl2bpel.Tracing;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Created by IntelliJ IDEA.
 * User: goldberg
 * Date: Aug 26, 2008
 * Time: 8:06:38 AM
 */

/**
 * This class an analysis and conversion of an xpdl package into an internal structure as the first step in the convert to bpel procedure.
 * The internal structure is expanded to insure cycles and controlled merges are separated into separate blocks.
 * Any issues detected are saved.
 * The validator just needs to call getIssues after the contructor is complete.
 * The converter would call toBpelProcess.
 */
public class Analyzer {

    public static final String NO_STARTER = "n2pe.noStarter"; //$NON-NLS-1$
    public static final String MISSING_INCOMING_SEQUENCE_FLOW = "n2pe.missingIncomingSequenceFlow"; //$NON-NLS-1$
    public static final String INCORRECT_TASK_TYPE_FOR_PICK = "n2pe.incorrectTaskTypeForPick"; //$NON-NLS-1$
    public static final String EXTRA_INCOMING_LINK = "n2pe.extraIncomingLink"; //$NON-NLS-1$
    public static final String LOOP_NOT_VALID_HERE = "n2pe.loopNotValidHere"; //$NON-NLS-1$
    public static final String BOUNDARY_EVENT_NOT_VALID_HERE = "n2pe.boundaryEventNotValidHere"; //$NON-NLS-1$
    public static final String MULTIPLE_LEADING_GATEWAYS = "n2pe.multipleLeadingGateways"; //$NON-NLS-1$
    public static final String MULTIPLE_START_EVENTS_IN_SUBPROCESS = "n2pe.multipleStartEventsInSubprocess"; //$NON-NLS-1$
    public static final String UNBALANCED_PATH_TO_CONTROLLED_MERGE_FROM_UNCONTROLLED_MERGE = "n2pe.unbalancedPathToControlledMergeFromUncontrolledMerge"; //$NON-NLS-1$
    public static final String CANNOT_EXECUTE = "n2pe.cannotExecute"; //$NON-NLS-1$
    public static final String NO_INCOMING_LINK = "n2pe.noIncomingLink"; //$NON-NLS-1$
    public static final String INVALID_DISCRIMINATOR_WAITFOR_VALUE = "n2pe.invalidWaitFor"; //$NON-NLS-1$
    public static final String INVALID_SEQUENCE_FLOW = "n2pe.invalidSequenceFlow"; //$NON-NLS-1$
    public static final String TOO_MANY_COMPENSATION_ACTIVITIES= "n2pe.tooManyCompensationActivities"; //$NON-NLS-1$
	public static final String JOIN_CORRELATIONS_NOT_MATCHING = "n2pe.joinCorrelationsNotMatching"; //$NON-NLS-1$
	public static final String MIGRATION_POINT = "n2pe.migrationPoint"; //$NON-NLS-1$
	public static final String FLOW_ANALYSIS_TIMEOUT = "n2pe.flowAnalysisTimeout"; //$NON-NLS-1$

    public static enum TaskType {
        StartEvent,
        IntermediateEvent,
        IntermediateBoundaryEvent,
        EndEvent,
        Gateway,
        UserTask,
        ManaualTask,
        ServiceTask,
        SendTask,
        ReceiveTask,
        ScriptTask,
        Task,
        EmbeddedSubProcess,
        IndependedSubProcess,
        EventSubProcess,
        OTHER,
        Pick,
        Flow,
        Process
    }

    public static enum GatewayType {
        XOR,
        AND,
        OR,
        XOR_EVENT,
        COMPLEX,
        NA
    }

    // token types
    enum Type {
        XOR,
        OR,
        AND
    }

    com.tibco.xpd.xpdl2.Package xpdlPackage;
    List<AnalyzerIssue> issues = new ArrayList<AnalyzerIssue>();
    List<AnalyzerProcess> processes = new ArrayList<AnalyzerProcess>();
    long analyzeTaskCount = 0;
    long startTime = 0;
    long maxTime = 0;  // max analyze time in seconds (0 for unlimited).
    Integer orgModelVersion = null;

    /**
     * Analyze all processes in the package.
     * @param xpdlPackage package to analyze
     */
    public Analyzer(com.tibco.xpd.xpdl2.Package xpdlPackage) {
        this(xpdlPackage, 0);	// with no time limit
    }

    /**
     * Analyze specific process
     * @param xpdlProcess process to analyze
     */
    public Analyzer(com.tibco.xpd.xpdl2.Process xpdlProcess) {
    	this(xpdlProcess, 0);   // with no time limit
    }
    
    /**
     * Analyze all processes in the package.
     * @param xpdlPackage package to analyze
     * @param maxTime max analyzer time in seconds
     */
    public Analyzer(com.tibco.xpd.xpdl2.Package xpdlPackage, long maxTime) {
        this.xpdlPackage = xpdlPackage;
        this.maxTime = maxTime;
        List<com.tibco.xpd.xpdl2.Process> xpdlProcesses = xpdlPackage.getProcesses();
        for (com.tibco.xpd.xpdl2.Process xpdlProcess: xpdlProcesses) {
            processes.add(makeInternalStructure(xpdlProcess));
        }
    }

    /**
     * Analyze specific process
     * @param xpdlProcess process to analyze
     * @param maxTime max analyzer time in seconds
     */
    public Analyzer(com.tibco.xpd.xpdl2.Process xpdlProcess, long maxTime) {
        this.xpdlPackage = xpdlProcess.getPackage();
        this.maxTime = maxTime;
        processes.add(makeInternalStructure(xpdlProcess));
    }
    
    public Integer getOrgModelVersion() {
		return orgModelVersion;
	}

	public void setOrgModelVersion(Integer orgModelVersion) {
		this.orgModelVersion = orgModelVersion;
	}

	public List<AnalyzerProcess> getProcesses() {
        return processes;
    }

    public List<AnalyzerIssue> getIssues() {
        List<AnalyzerIssue> issues = new ArrayList<AnalyzerIssue>(this.issues);
        for (AnalyzerProcess process: getProcesses()) {
            for (AnalyzerParentTask flow: process.getSubFlows()) {
                issues.addAll(flow.getIssues());
            }
        }
        return issues;
    }

    public void addIssue(String issueId, EObject obj) {
        issues.add(new AnalyzerIssue(issueId, obj));
    }

    public void addIssue(String issueId, EObject obj, String msgData) {
        issues.add(new AnalyzerIssue(issueId, obj, msgData));
    }

    public void addIssue(String issueId, EObject obj, String[] msgData) {
        issues.add(new AnalyzerIssue(issueId, obj, msgData));
    }

    public com.tibco.xpd.xpdl2.Package getXpdlPackage() {
        return xpdlPackage;
    }

    private AnalyzerProcess makeInternalStructure(com.tibco.xpd.xpdl2.Process xpdlProcess) {
    	long startTime = System.currentTimeMillis();
        AnalyzerProcess process = new AnalyzerProcess(xpdlProcess);
        activitySetToInternalStructure(process, xpdlProcess.getActivities(), xpdlProcess.getTransitions(), process);
        analyzeStarters(process, process);
        analyzeEventBasedGateways(process, process);
        
        /* Sid XPD-8298 Previously, event based gateways did not work in event handlers (they were treated as parallel
         * gateways.
         * This was because the above call to analyzeEventBasedGateways() handled the main process flow only (and 
         * recursively down thru emb sub-processes on main flow.
         * 
         * However, Event handler sub-flows ARE NOT activity children of analyzer process and therefore they weren't
         * being considered. So now we do that here by going thru all the sub-flows (which includes the main process itself
         * (for some bizarre reason), main-flow embedded sub-procs and event handlers (identified by not having parent 
         * and no incoming flow as this SEEMED to be the case under debug conditions)
         */
        for (AnalyzerParentTask subFlow : process.getSubFlows()) {
            if (subFlow != process && subFlow.getParent() == null 
                    && (subFlow.getInLinks() == null || subFlow.getInLinks().size() == 0)) {
                analyzeEventBasedGateways(subFlow, process);
            }
        }
        
        analyzeReceives(process);
        try {
        	analyzeAllFlows(process);
        } catch (RuntimeException ex) {
        	//ignore
        	return process;
        }
        if (!Xpdl2ModelUtil.isPageflow(xpdlProcess)) { 
        	AnalyzeMigration.detectAllowedMigrationPoints(process);
        }
        if (Tracing.ENABLED) Tracing.trace("Analyzer total time for "+xpdlProcess.getName()+": "+(System.currentTimeMillis()-startTime));
        return process;
    }

    private void activitySetToInternalStructure(AnalyzerParentTask parent,
                                                List<com.tibco.xpd.xpdl2.Activity> xpdlActivities,
                                                List<com.tibco.xpd.xpdl2.Transition> xpdlTransitions, AnalyzerProcess process) {
        /* Sid XPD-8298 added process parameter as we now need to pass that to analyzeStarters() 
         */


        // process tasks
        for(com.tibco.xpd.xpdl2.Activity xpdlActivity: xpdlActivities) {
            if (xpdlActivity.getBlockActivity() != null) {
                // embedded sub-process
                AnalyzerParentTask subProc = new AnalyzerParentTask(xpdlActivity);
                parent.addChild(subProc);
                parent.getProcess().addSubFlow(subProc);
                String id = xpdlActivity.getBlockActivity().getActivitySetId();
                com.tibco.xpd.xpdl2.ActivitySet activitySet = parent.getXpdlProcess().getActivitySet(id);
                activitySetToInternalStructure(subProc, activitySet.getActivities(), activitySet.getTransitions(), process);
            } else {
                // activity, not sub-process
                AnalyzerTask task = new AnalyzerTask(xpdlActivity);
                //todo update receives
                // if task type is receive or start message event or message event within a pick or maybe message event on the boundary


                parent.addChild(task);
            }
        }

        Set<AnalyzerTask> possibleDeadEndTasks = new LinkedHashSet<AnalyzerTask>();
        // process transitions
        for (com.tibco.xpd.xpdl2.Transition xpdlTransition: xpdlTransitions) {
        	AnalyzerTask fromTask = parent.findTask(xpdlTransition.getFrom());
        	AnalyzerTask toTask = parent.findTask(xpdlTransition.getTo());
        	if (Analyzer.TaskType.IntermediateBoundaryEvent.equals(fromTask.getTaskType())
        			&& EventObjectUtil.isNonCancellingEvent(fromTask.getXpdlActivity()) 
        			&& Analyzer.TaskType.EndEvent.equals(toTask.getTaskType())
        			&& ResultType.NONE==(((EndEvent)toTask.getXpdlActivity().getEvent()).getResult().getValue())) {
        		// ignore dead end after non-canceling boundary event
        		// so skip this link and save task for possible removal
        		if (!possibleDeadEndTasks.contains(toTask)) {
        			possibleDeadEndTasks.add(toTask);
        		}
        	} else {
        		AnalyzerLink link = new AnalyzerLink(xpdlTransition, fromTask, toTask);
        		parent.addLink(link);
        	}
        }
		// remove possible dead end tasks if no incoming link
        for (AnalyzerTask toTask: possibleDeadEndTasks) {
        	if (toTask.getAllInLinks().size()==0)
    			parent.removeChild(toTask);
        }

        // process associations
        for (com.tibco.xpd.xpdl2.Association xpdlAssociation: getXpdlPackage().getAssociations()) {
        	AnalyzerTask sourceTask = parent.findTask(xpdlAssociation.getSource());
        	// these are package level associations so this one may not apply here
        	if (sourceTask!=null) {
        		AnalyzerTask targetTask = parent.findTask(xpdlAssociation.getTarget());
        		if (targetTask!=null) {
        			if (AnalyzerUtil.isCompensationEvent(sourceTask)) {
        				if (sourceTask.getAssociation()!=null) {
        					addIssue(TOO_MANY_COMPENSATION_ACTIVITIES, sourceTask.getXpdlActivity());
        				}
        				if (targetTask.getOutLinks().size()>0 || targetTask.getInLinks().size()>0) {
        					addIssue(INVALID_SEQUENCE_FLOW, targetTask.getXpdlActivity());
        				}
        				parent.removeChild(targetTask);
        				sourceTask.setAssociation(targetTask);
        			}
        		}
        	}
        }

        // handle boundary tasks, link events, discriminators
        List<AnalyzerTask> children = new ArrayList<AnalyzerTask>(parent.getChildren());
        for (AnalyzerTask task: children) {
            // handle boundary tasks
            if (task.getTaskType().equals(TaskType.IntermediateBoundaryEvent)) {
                String hostId = ((IntermediateEvent)task.getXpdlActivity().getEvent()).getTarget();
                AnalyzerTask hostTask = findTask(parent, hostId);
                if (hostTask!=null) {
                    // adding boundary task to host so that when getting out links can also include the boundary task's out links as well.
                    // since boundary tasks aren't starting points, there outbound links will only be followed when the host task's are.
                    // only one compensation event allowed per task
                    if (AnalyzerUtil.isCompensationEvent(task)) {
                        // make sure no other compensation event
                        for (AnalyzerTask boundaryTask: hostTask.getBoundaryTasks()) {
                            if (AnalyzerUtil.isCompensationEvent(boundaryTask)) {
                                addIssue(TOO_MANY_COMPENSATION_ACTIVITIES, task.getXpdlActivity());
                                break;
                            }
                        }
                    }
                    hostTask.addBoundaryTask(task);
                    // remove it from parent task
                    parent.removeChild(task);
                    task.setBoundaryParent(hostTask);
                }
            }
        }
        
        children = new ArrayList<AnalyzerTask>(parent.getChildren());
        for (AnalyzerTask task: children) {
            // handle link events & event handlers
            if (task.getTaskType().equals(TaskType.IntermediateEvent)) {
                if (((IntermediateEvent)task.getXpdlActivity().getEvent()).getTrigger().getValue() == (TriggerType.LINK)) {
                    String linkToId = ((IntermediateEvent)task.getXpdlActivity().getEvent()).getTriggerResultLink().getName();
                    if (linkToId!=null && linkToId.length()>1) {
                        // add transition to connect to two link tasks
                        AnalyzerTask toTask = findTask(parent, linkToId); // find the task linking to
                        if (toTask!=null) {
                        	AnalyzerLink link = new AnalyzerLink(null, task, toTask);
                        	parent.addLink(link);
                        }
                    }
                } else if (task.getInLinks().isEmpty()) {
                	// treat as event handler (intermediate event with no incoming links)
                	parent.removeChild(task);
                	parent.addEventHandler(task);
                	AnalyzerParentTask eventHandlerBody = new AnalyzerParentTask(parent.getProcess().getNextGenNumber());
                	task.setEventHandlerBody(eventHandlerBody);
                	eventHandlerBody.addChild(task);
                	//follow out link path to collect all transitions and tasks and move to event body
                	AnalyzerUtil.moveDecendants(parent, eventHandlerBody, task);
                	parent.getProcess().addSubFlow(eventHandlerBody);
                }
            }
            // handle adhoc task or event sub-process
            if (task.isAdhocActivity() || task.isEventSubProcess()) {
            	parent.removeChild(task);
            	parent.addEventHandler(task);
            	if (task.isEventSubProcess()) {
            		analyzeStarters((AnalyzerParentTask)task, process);
            	}
            }
            // handle complex gateway, discriminator
            if (task.getTaskType().equals(TaskType.Gateway) && task.getGatewayType().equals(GatewayType.COMPLEX)) {
                // check maxWait not greater than in links
                com.tibco.xpd.xpdl2.Activity xpdlActivity = task.getXpdlActivity();
                Integer waitFor = XPDLUtils.getDiscriminatorWaitFor(xpdlActivity);
                task.setWaitFor(waitFor);
                task.setEarlyExit(true);
                if (waitFor != null) {
                    EList<Transition> list = xpdlActivity.getIncomingTransitions();
                    int incomingLinks = 0;
                    if (list!=null && list.size()>0) {
                        incomingLinks = list.size();
                    }
                    if (waitFor>=incomingLinks || waitFor==0) {
                            // add issue, invalid waitFor
                        //addIssue(MULTIPLE_LEADING_GATEWAYS, xpdlActivity);
                    }
                }
            }
        }
    }

    // A starter can be a start event, a receive activity with no incoming link or an event-based(complex?) gateway with no incoming link
    // If none of the above, any task with no incoming link (treat this as if there is a none start event with a link to these tasks).
    // If more than one starter, they will be grouped into a separate parent task representing a pick.
    // If the parent is the process, then create instance is yes
    // IF the parent is a sub-process, then pick is only needed if actual multiple start events (when supported) and
    //   leanding receives and leading event-based(complex?) gateways are treated the same as other leading tasks with no incoming links.
    private void analyzeStarters(AnalyzerParentTask parent, AnalyzerProcess process) {
        
        /* Sid XPD-8298 added process parameter as we now need to pass that to createPick() 
         */

        
        Set<AnalyzerTask> startEvents = new LinkedHashSet<AnalyzerTask>();
        Set<AnalyzerTask> leadingReceives = new LinkedHashSet<AnalyzerTask>();
        Set<AnalyzerTask> leadingGatewayTasks = new LinkedHashSet<AnalyzerTask>();
        Set<AnalyzerTask> leadingTasks = new LinkedHashSet<AnalyzerTask>();
        for (AnalyzerTask task: parent.getChildren()) {
            if (task.getTaskType().equals(TaskType.StartEvent)) {
                startEvents.add(task);
            } else if (task.getInLinks().isEmpty()) {
                if (task.getTaskType().equals(TaskType.ReceiveTask)) {
                    leadingReceives.add(task);
                } else if (task.getTaskType().equals(TaskType.Gateway) &&
                           task.getGatewayType().equals(GatewayType.XOR_EVENT)) {
                    leadingGatewayTasks.add(task);
                } else if (!task.getTaskType().equals(TaskType.IntermediateBoundaryEvent) &&
                		   !task.getTaskType().equals(TaskType.IntermediateEvent) &&
                		   !task.isAdhocActivity() && !task.isEventSubProcess()) {
                    // other task with no incoming links.  will be starter if no real start events
                    leadingTasks.add(task);
                }
            }
            if (task instanceof AnalyzerParentTask) {
                analyzeStarters((AnalyzerParentTask)task, process);
            }
        }
        if (parent instanceof AnalyzerProcess) {
            //  These steps apply to a process.  Sub-process steps would be different.
            //   if all are empty, issue for "no starter"
            //   if start events plus leading gateway tasks, issue for this?  (or should we lump them all together into single pick)
            //   if leading tasks plus something else, issue for this?

            //   if only one start event or only one leading receive, done
            //   if multiple start events and/or multiple leading receives, combine into pick
            //   if gateWayTasks, combine into pick along with successor tasks
            //   if only leading tasks, generate startTask with links to each leading task
            int total = startEvents.size() + leadingReceives.size() + leadingGatewayTasks.size() + leadingTasks.size();
            if (total == 0) {
                // issue for 'no starter'
                addIssue(NO_STARTER, process.getXpdlProcess());
                if (Tracing.ENABLED) Tracing.trace("Validation error: "+parent + ": no starter"); //$NON-NLS-1$
            } else if (total == 1) {
                if (startEvents.size() == 1 || leadingReceives.size() == 1) {
                    // most common case, just need to save starting point
                    /*
                     * Sid ACE-3736 isable join correlation validation as these are no longer applicable in ACE
                     */
//                	if (startEvents.size() == 1) {
//                		for (AnalyzerTask task: startEvents) {
//                			if (usesJoinCorrelation(startEvents)) {
//                				addIssue(JOIN_CORRELATIONS_NOT_MATCHING, task.getXpdlActivity());
//                				if (Tracing.ENABLED) Tracing.trace("Validation error: "+task + ": join correlations for multiple starters must match"); //$NON-NLS-1$                    		
//                			} else {
//                				task.setCreateInstance(true);
//                			}
//                		}
//                	} else {
//                        for (AnalyzerTask task: leadingReceives) {
//                            if (XPDLUtils.isLoopActivity(task.getXpdlActivity())) {
//                                addIssue(LOOP_NOT_VALID_HERE, task.getXpdlActivity());
//                                if (Tracing.ENABLED) Tracing.trace("Validation error: "+task + ": loop not valid here"); //$NON-NLS-1$
//                            } else if (usesJoinCorrelation(leadingReceives)) {
//                				addIssue(JOIN_CORRELATIONS_NOT_MATCHING, task.getXpdlActivity());
//                				if (Tracing.ENABLED) Tracing.trace("Validation error: "+task + ": join correlations for multiple starters must match"); //$NON-NLS-1$                    		
//                			} else {
//                                task.setCreateInstance(true);
//                            }
//                        }
//                    }
                } else if (leadingTasks.size() == 1) {
                    // insert leading none start event
                    insertLeadingStartEvent(leadingTasks, parent, true);
                } else {
                    // must be leading gateway so create pick from gateway
                    AnalyzerTask gateway = (AnalyzerTask)leadingGatewayTasks.toArray()[0];
                    createPick(gateway, parent, true, process);
                }
            } else if (startEvents.size() + leadingReceives.size() == total) {
                if (usesJoinCorrelation(startEvents) || usesJoinCorrelation(leadingReceives)) {
                    //treat as multi-starter non-pick case
                    Set<AnalyzerTask> combinedTasks = new LinkedHashSet<AnalyzerTask>(startEvents);
                    combinedTasks.addAll(leadingReceives);
                    //verify all use join on same correlation(s), else error (means all must be message based too)
                    if (!hasMatchingJoins(combinedTasks)) {
                        /*
                         * Sid ACE-3736 isable join correlation validation as these are no longer applicable in ACE
                         */
//                        for (AnalyzerTask task: combinedTasks) {
//                        	addIssue(JOIN_CORRELATIONS_NOT_MATCHING, task.getXpdlActivity());
//                        	if (Tracing.ENABLED) Tracing.trace("Validation error: "+parent + ": join correlations for multiple starters must match"); //$NON-NLS-1$
//                        }
                    } else {
                    	// Add tokens from a fake starting point so that all the join starters behave as in parallel flow
                    	AnalyzerTask fakeStartPoint = new AnalyzerTask(0);
                    	int i = 1;
                        for (AnalyzerTask task: startEvents) {
                    		task.setCreateInstance(true);
                            Token newToken = new Token(fakeStartPoint, i++, total, JoinSplitType.PARALLEL_LITERAL);
                            task.setJoinStarterToken(newToken);
                    	}
                    	for (AnalyzerTask task: leadingReceives) {
                    		if (XPDLUtils.isLoopActivity(task.getXpdlActivity())) {
                    			addIssue(LOOP_NOT_VALID_HERE, task.getXpdlActivity());
                    			if (Tracing.ENABLED) Tracing.trace("Validation error: "+task + ": loop not valid here"); //$NON-NLS-1$
                    		} else {
                    			task.setCreateInstance(true);
                    			Token newToken = new Token(fakeStartPoint, i++, total, JoinSplitType.PARALLEL_LITERAL);
                                task.setJoinStarterToken(newToken);                    		}
                    	}
                    }
                } else {
                    // combine start events and leading receives into pick
                    Set<AnalyzerTask> combine = new LinkedHashSet<AnalyzerTask>(startEvents);
                    combine.addAll(leadingReceives);
                    createPick(combine, parent, true, process);
                }
            } else if (leadingGatewayTasks.size() == total) {
                // why would there be 2 gateway tasks?
                for (AnalyzerTask leadingGateway: leadingGatewayTasks) {
                    addIssue(MULTIPLE_LEADING_GATEWAYS, leadingGateway.getXpdlActivity());
                    if (Tracing.ENABLED) Tracing.trace("Validation error: "+leadingGateway + ": multiple leading gateways"); //$NON-NLS-1$
                }
            } else if (leadingTasks.size() == total) {
                // only multiple leading tasks. insert none start event in front
                insertLeadingStartEvent(leadingTasks, parent, true);
            } else {
                // unsupported combination of starters
                for (AnalyzerTask leadingTask: leadingTasks) {
                	if (leadingTask.getXpdlActivity() == null || XPDLUtils.isEventHandlerActivity(leadingTask.getXpdlActivity())) {
                		continue;
                	}
                    addIssue(MISSING_INCOMING_SEQUENCE_FLOW, leadingTask.getXpdlActivity());
                    if (Tracing.ENABLED) Tracing.trace("Validation error: "+leadingTask + ": missing incoming sequence flow"); //$NON-NLS-1$
                }
                for (AnalyzerTask leadingTask: leadingGatewayTasks) {
                	if (leadingTask.getXpdlActivity() == null || XPDLUtils.isEventHandlerActivity(leadingTask.getXpdlActivity())) {
                		continue;
                	}
                    addIssue(MISSING_INCOMING_SEQUENCE_FLOW, leadingTask.getXpdlActivity());
                    if (Tracing.ENABLED) Tracing.trace("Validation error: "+leadingTask + ": missing incoming sequence flow"); //$NON-NLS-1$
                }
            }
        } else {
            // sub-process steps
            Set<AnalyzerTask> allLeadingTasks = new LinkedHashSet<AnalyzerTask>(leadingTasks);
            allLeadingTasks.addAll(leadingGatewayTasks);
            allLeadingTasks.addAll(leadingReceives);
            if (allLeadingTasks.size()>0 && startEvents.size()>0) {
                for (AnalyzerTask leadingTask: allLeadingTasks) {
                	if (leadingTask.getXpdlActivity() == null || XPDLUtils.isEventHandlerActivity(leadingTask.getXpdlActivity())) {
                		continue;
                	}
                    addIssue(MISSING_INCOMING_SEQUENCE_FLOW, leadingTask.getXpdlActivity());
                    if (Tracing.ENABLED) Tracing.trace("Validation error: "+leadingTask + ": missing incoming sequence flow"); //$NON-NLS-1$
                }
            } else {
                if (allLeadingTasks.size() > 1) {
                    insertLeadingStartEvent(allLeadingTasks, parent, false);
                } else if (startEvents.size() > 1 && !parent.isEventSubProcess()) {
                	// if event subprocess, error issued by Studio
                    // multiple start events in an embedded sub-process
                    addIssue(MULTIPLE_START_EVENTS_IN_SUBPROCESS, parent.getXpdlActivity());
                    if (Tracing.ENABLED) Tracing.trace("Validation error: "+parent + ": multipe start events in subprocess"); //$NON-NLS-1$
                    // todo when supported, replace addIssue with createPick
                    // createPick(startEvents, parent, false);
                }
            }
            if (parent.isEventSubProcess()) {
            	for (AnalyzerTask eventTask: startEvents) {
            		parent.setStartEvent(eventTask);
            		eventTask.setTaskType(Analyzer.TaskType.OTHER);
            		break; // should only be 1 start event
            	}
            }
        }
    }

    /**
     * Determines if any of these starters use join correlation.
     * @param tasks Could be start event or receive task
     * @return  return true if any tasks uses join correlation
     */
    boolean usesJoinCorrelation(Set<AnalyzerTask> tasks) {
    	for (AnalyzerTask task: tasks) {
    		Map<String, CorrelationMode> cmap = XPDLUtils.getCorrelationDataFields(task.getXpdlActivity());
    		if (cmap.containsValue(CorrelationMode.JOIN)) {
    			return true;
    		} else {
    			continue;
    		}
    	}
    	return false;
    }

    /**
     * Determines if all tasks have the same set of join correlations
     * @param tasks
     * @return
     */
    boolean hasMatchingJoins(Set<AnalyzerTask> tasks) {
    	if (tasks.size()<2) return false;
    	// this will hold the join correlation names that all tasks much match 
    	Set<String> expected = null;
    	for (AnalyzerTask task: tasks) {
    		if (expected==null) {
    			// first one through so set the expected correlations
    			expected = new LinkedHashSet<String>();
        		Map<String, CorrelationMode> cmap = XPDLUtils.getCorrelationDataFields(task.getXpdlActivity());
        		for (String key: cmap.keySet()) {
        			if (CorrelationMode.JOIN.equals(cmap.get(key))) {
        				expected.add(key);
        			}
        		}
     		} else {
     			//Map<String, CorrelationMode> cmap = XPDLUtils.getCorrelationDataFields(task.getXpdlActivity());
     			Set<String> temp = new LinkedHashSet<String>();
     			Map<String, CorrelationMode> cmap2 = XPDLUtils.getCorrelationDataFields(task.getXpdlActivity());
        		for (String key: cmap2.keySet()) {
        			if (CorrelationMode.JOIN.equals(cmap2.get(key))) {
        				temp.add(key);
        			}
        		}
        		if (!expected.containsAll(temp)) {
        			return false;
        		}
        	}
       	}
    	return true;
    }
    
    /**
     * An event based gateway in the flow is equivalent to a pick.
     * This routine will wrap the events into a pick and adjust links according
     * @param parent
     */
    private void analyzeEventBasedGateways(AnalyzerParentTask parent, AnalyzerProcess process) {
        /* Sid XPD-8298 added process parameter as we now need to pass that to createPick() 
         */

        Set<AnalyzerTask> interiorEventGatewayTasks = new LinkedHashSet<AnalyzerTask>();
        for (AnalyzerTask task: parent.getChildren()) {
            if (task.getTaskType().equals(TaskType.Gateway) &&
                    task.getGatewayType().equals(GatewayType.XOR_EVENT)) {
                interiorEventGatewayTasks.add(task);
            } else if (task instanceof AnalyzerParentTask) {
                analyzeEventBasedGateways((AnalyzerParentTask)task, process);
            }
        }
        for (AnalyzerTask task: interiorEventGatewayTasks) {
            createPick(task, parent, false, process);
        }
    }
       
    private void analyzeReceives(AnalyzerParentTask parent) {
         // if task type is receive or start message event or message event within a pick or message event on the boundary, it is a known receive
        for (AnalyzerTask task: parent.getChildren()) {
            switch (task.getTaskType()) {
                case ReceiveTask:
                    WebServiceOperation op = ((com.tibco.xpd.xpdl2.Task)task.getXpdlActivity().getImplementation()).getTaskReceive().getWebServiceOperation();
                    if (op!=null) {
                        parent.getProcess().addWebServiceOperations(op);
                    }
                    break;
                case StartEvent:
                	if (task.getXpdlActivity()!=null && (StartEvent)task.getXpdlActivity().getEvent()!=null) {
                		TriggerResultMessage triggerMsg = ((StartEvent)task.getXpdlActivity().getEvent()).getTriggerResultMessage();
                		if (triggerMsg!=null) {
                			WebServiceOperation op2 = triggerMsg.getWebServiceOperation();
                			if (op2!=null) {
                				parent.getProcess().addWebServiceOperations(op2);
                			}
                		}
                	}
                    break;
                case Flow: case EmbeddedSubProcess:
                    //todo not needed now.   analyzeReceives((AnalyzerParentTask)task);
                    break;
                case Pick:
                    if (task.isCreateInstance()) {
                        analyzeReceives((AnalyzerParentTask)task);
                    }
                    break;
                case IntermediateEvent:
                    //todo handle if inside pick
                case IntermediateBoundaryEvent:
                    // todo
            }
        }
    }


    private void createPick(AnalyzerTask gateway, AnalyzerParentTask parent, boolean createInstance, AnalyzerProcess process) {
        /* Sid XPD-8298 added process parameter as we now need to pass that to createPick() 
         */

        // need to create pick with the gateways target tasks
        // leave gateway as placeholder to be visible to debugger
        // replace gateway outgoing links with single link to pick

        // collect tasks to place into pick
        Set<AnalyzerTask> tasks = new LinkedHashSet<AnalyzerTask>();
        for (AnalyzerLink link: gateway.getOutLinks()) {
            tasks.add(link.getRealTargetTask());
        }

        boolean hasIssue = false;
        for (AnalyzerTask task: tasks) {
            if (task.getInLinks().size()>1) {
                // add issue if target tasks have any other incoming links
                addIssue(EXTRA_INCOMING_LINK, task.getXpdlActivity());
                if (Tracing.ENABLED) Tracing.trace("Validation error: "+task + ": extra incoming link"); //$NON-NLS-1$
                hasIssue = true;
            }
            if (task.getTaskType().equals(TaskType.IntermediateEvent) ||
                task.getTaskType().equals(TaskType.ReceiveTask)) {
                    if (task.isBoundaryHost()) {
                        addIssue(BOUNDARY_EVENT_NOT_VALID_HERE, task.getXpdlActivity());
                        if (Tracing.ENABLED) Tracing.trace("Validation error: "+task + ": boundary event not valid here"); //$NON-NLS-1$
                        hasIssue = true;
                    }
                    if (XPDLUtils.isLoopActivity(task.getXpdlActivity())) {
                        addIssue(LOOP_NOT_VALID_HERE, task.getXpdlActivity());
                        if (Tracing.ENABLED) Tracing.trace("Validation error: "+task + ": loop not valid here"); //$NON-NLS-1$
                        hasIssue = true;
                    }
            } else {
                // task must be intermediate event or receive
                addIssue(INCORRECT_TASK_TYPE_FOR_PICK, task.getXpdlActivity());
                if (Tracing.ENABLED) Tracing.trace("Validation error: "+task + ": incorrect task type for pick"); //$NON-NLS-1$
                hasIssue = true;
            }
        }
        // by-pass action if issues detected
        if (!hasIssue) {
            // remove links from gateway
        	List<AnalyzerLink> gatewayLinks = new ArrayList<AnalyzerLink>(gateway.getOutLinks());
            for (AnalyzerLink link: gatewayLinks) {
                parent.removeLink(link);
            }
            // create pick
            AnalyzerTask pick = createPick(tasks, parent, createInstance, process);
            // add link from gateway to pick
            AnalyzerLink link = new AnalyzerLink(null, gateway, pick);
            parent.addLink(link);
        }
    }

    private AnalyzerTask createPick(Set<AnalyzerTask> tasks, AnalyzerParentTask parent, boolean createInstance, AnalyzerProcess process) {
        /* Sid XPD-8298 added process parameter as we may now be called for event handler flows which do not have 'parent' set and therefore 
         * will fail with NPE in call to parent.getProcess() below.
         */
        
        
        // create new parent
        // move tasks from current parent into new parent
        // mark links that they now cross a boundary.  that needs to be taken into account when generating code or checking for cycles, alternate flow, etc.
        // add intermediate task after pick if necessary so that each task in the pick has only one output link
        AnalyzerParentTask pick = new AnalyzerParentTask(process.getNextGenNumber());
        parent.addChild(pick);
        pick.setTaskType(TaskType.Pick);
        pick.setCreateInstance(createInstance);
        pick.setJoinSplitTypeOut(JoinSplitType.EXCLUSIVE_LITERAL);
        for (AnalyzerTask task: tasks) {
            if (task.isBoundaryHost()) {
                addIssue(BOUNDARY_EVENT_NOT_VALID_HERE, task.getXpdlActivity());
                if (Tracing.ENABLED) Tracing.trace("Validation error: "+task + ": boundary event not valid here"); //$NON-NLS-1$
            }
            if (XPDLUtils.isLoopActivity(task.getXpdlActivity())) {
                addIssue(LOOP_NOT_VALID_HERE, task.getXpdlActivity());
                if (Tracing.ENABLED) Tracing.trace("Validation error: "+task + ": loop not valid here"); //$NON-NLS-1$
            }
            parent.removeChild(task);
            pick.addChild(task);
            if (task.getOutLinks().size()>1) {
                //if a task has more than one link out, generate intermediate task so that pick remains XOR
                AnalyzerTask intermediateTask = new AnalyzerTask(process.getNextGenNumber());
                parent.addChild(intermediateTask);
                List<AnalyzerLink> outLinks = new ArrayList<AnalyzerLink>(task.getOutLinks());
                for (AnalyzerLink link: outLinks) {
                    parent.redirectLinkSource(link, intermediateTask);
                }
                AnalyzerLink link = new AnalyzerLink(null, task, intermediateTask);
                parent.addLink(link);
                pick.addOutCrossingLink(link);
                link.setBoundaryCrossedOnExit(pick);
            } else {
                for (AnalyzerLink link: task.getOutLinks()) {
                    pick.addOutCrossingLink(link);
                    link.setBoundaryCrossedOnExit(pick);
                }
            }
        }
        return pick;
    }


    private void insertLeadingStartEvent(Set<AnalyzerTask> tasks, AnalyzerParentTask parent, boolean createInstance) {
        AnalyzerTask startTask = new AnalyzerTask(parent.getProcess().getNextGenNumber());
        startTask.setTaskType(TaskType.StartEvent);
        startTask.setCreateInstance(createInstance);
        startTask.setJoinSplitTypeOut(JoinSplitType.PARALLEL_LITERAL);
        parent.addChild(startTask);
        for (AnalyzerTask task: tasks) {
            AnalyzerLink link = new AnalyzerLink(null, startTask, task);
            parent.addLink(link);
        }
    }

    private void analyzeAllFlows(AnalyzerProcess process) {
        boolean allDone = false;
        while (!allDone) {
        	List<AnalyzerParentTask> flows = new ArrayList<AnalyzerParentTask>(process.getSubFlows());
            allDone = true;
            // repeats until no more flow adjustments
            for (AnalyzerParentTask flow: flows) {
                AnalyzerUtil.logFlow(flow);
                if (!flow.isDone()) {
                    allDone = false;
                    adjustFlow(flow);
                    AnalyzerUtil.logFlow(flow);
                }
            }
        }
    }

    /**
     * Flow may need to be split into two flows to separate controlled merges from cycles
     * @param flow Flow to adjust
     */
    private void adjustFlow(AnalyzerParentTask flow) {
        analyzeFlow(flow);
        boolean finished = true;
        if (flow.getLoopbackLinks().size()<1) {
            // no cycles
            if (flow.getUncontrolledMerges().size()<1) {
                // no uncontrolled merges either and no adjustments required
            } else {
                // handled uncontrolled merges
                for (AnalyzerTask task: flow.getUncontrolledMerges()) {
                    task.setUncontrolledMerge(true);
                }
                if (flow.getControlledMerges().size()>0) {
                    //check for deadlocks. For each controlled merge activity that has an uncontrolled merge activity as a predecessor,
                    //verify that all its immediate predecessors also have the uncontrolled merge as a predecessor.
                    //Failure to meet this restriction will mean there is a path to the controlled merge that
                    //does not go through the uncontrolled merge, causing the inability of the two paths to merge.
                    for (AnalyzerTask controlledMerge: flow.getControlledMerges()) {
                        Set<AnalyzerTask> predecessors = controlledMerge.getPredecessors();
                        for (AnalyzerTask uncontrolledMerge: flow.getUncontrolledMerges()) {
                            if (predecessors.contains(uncontrolledMerge)) {
                                // controlled merge follows uncontrolled merge.  Make sure all paths come from same point
                                List<AnalyzerLink> incomingLinks = controlledMerge.getAllInLinks();
                                List<AnalyzerTask> preceedingTasks = AnalyzerUtil.getSourceActivities(incomingLinks);
                                for (AnalyzerTask preceedingTask: preceedingTasks) {
                                    Set<AnalyzerTask> preceedingsPreceeding = preceedingTask.getPredecessors();
                                    if (preceedingsPreceeding==null || !preceedingsPreceeding.contains(uncontrolledMerge)) {
                                        // violates constraint   (deadlockDetected, unsupported merge, improper merge????
                                        flow.addIssue(UNBALANCED_PATH_TO_CONTROLLED_MERGE_FROM_UNCONTROLLED_MERGE, controlledMerge.getXpdlActivity());
                                        if (Tracing.ENABLED) Tracing.trace("Validation error: "+controlledMerge + ": unbalanced path to controlled merge from uncontrolled merge"); //$NON-NLS-1$
                                        finished=true; // to stop trying this flow
                                        flow.setDone(true);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // cycles present
            if (flow.getControlledMerges().size()<1) {
                // cycles with no controlled merges so just switch entire flow to sequenceFlow mode
                flow.setContainsCycle(true);
                for (AnalyzerLink link: flow.getLoopbackLinks()) {
                    link.setLoopBack(true);
                }
            } else {
                // both cycles and controlled merges are present.
                // need to separate them into different flows by boxing up cycles or split/merges or both
                finished = false;
                if (flow.isContainsCycle()) { // || flow.getControlledMerges().size()<flow.getLoopbackLinks().size()) {
                    if (!boxSplitMerge(flow)) {
                        if (!boxCycle(flow)) {
                            // neither box helped. can not proceed
                            com.tibco.xpd.xpdl2.Activity act = flow.getXpdlActivity();
                            if (act==null && !flow.getControlledMerges().isEmpty()) {
                                act = ((AnalyzerTask)flow.getControlledMerges().toArray()[0]).getXpdlActivity();
                            }
//                            addIssue(CANNOT_EXECUTE, flow.getXpdlActivity());
                            flow.addIssue(CANNOT_EXECUTE, act);
                            if (Tracing.ENABLED) Tracing.trace("Validation error: "+flow + " can not separate cycle from controlled split/merge"); //$NON-NLS-1$
                            finished=true; // to stop trying this flow
                        }
                    }
                } else {
                    if (!boxCycle(flow)) {
                        if (!boxSplitMerge(flow)) {
                            // neither box helped. can not proceed
                            com.tibco.xpd.xpdl2.Activity act = flow.getXpdlActivity();
                            if (act==null && !flow.getControlledMerges().isEmpty()) {
                                act = ((AnalyzerTask)flow.getControlledMerges().toArray()[0]).getXpdlActivity();
                            }
                            flow.addIssue(CANNOT_EXECUTE, act);
                            if (Tracing.ENABLED) Tracing.trace("Validation error: "+flow + " can not separate cycle from controlled split/merge"); //$NON-NLS-1$
                            finished=true; // to stop trying this flow
                        }
                    }
                }
            }
        }

        if (finished) {
            flow.setDone(true);
        } else {
            flow.reset();
        }
    }

    /**
     * Extract cycle into a new flow
     * @param flow parent flow for the group of activities
     * @return true if change made, false if no new box constructed
     */
    private boolean boxCycle(AnalyzerParentTask flow) {
        List<Set<AnalyzerTask>> cycles = new ArrayList<Set<AnalyzerTask>>();

        // for each loopback link, determine the list of activities comprising a cycle
        int i=0;
        for (AnalyzerLink link: flow.getLoopbackLinks()) {
            Set<AnalyzerTask> cycle = AnalyzerUtil.determineCycle(link);
            AnalyzerUtil.logGroups(Arrays.asList(cycle), "Cycles "+(i++));
            cycles.add(cycle);
        }

        // remove cycles that contain controlled merge
        Iterator<Set<AnalyzerTask>> it = cycles.iterator();
        while (it.hasNext()) {
            Set<AnalyzerTask> cycle = it.next();
            AnalyzerTask mergeTask = AnalyzerUtil.containsMerge(flow, cycle);
            if (mergeTask!=null) {
                it.remove();
                // try to box that instead of cycle
                Set<AnalyzerTask> mergeGroup = new LinkedHashSet<AnalyzerTask>();
                AnalyzerUtil.determineMergeGroup(mergeTask, mergeGroup);
                if (!AnalyzerUtil.containsCycle(flow, mergeGroup, cycles)) {
                	if (boxSplitMerge(flow, mergeTask)) {
                		return true;
                	}
                }
            }
        }

        // combine cycles that have a common member
        if (cycles.size()>1) {
            AnalyzerUtil.logGroups(cycles, "Cycles to combine"); //$NON-NLS-1$
            cycles = AnalyzerUtil.combineSets(cycles);
            AnalyzerUtil.logGroups(cycles, "Combined cycles"); //$NON-NLS-1$
        }

        boolean cyclesChanged = false;
        // need to expand end of any cycle group that is MI
        it = cycles.iterator();
        while (it.hasNext()) {
            Set<AnalyzerTask> cycle = it.next();
            if (AnalyzerUtil.isMIDetected(flow, cycle, true)) {
                if (!AnalyzerUtil.expandToEnd(flow, cycle, true, this)) {
                    it.remove(); // can not box this group
                } else {
                    cyclesChanged = true;
                }
            }
        }

        // combine cycles that have a common member
        if (cyclesChanged && cycles.size()>1) {
            AnalyzerUtil.logGroups(cycles, "Cycles to combine"); //$NON-NLS-1$
            cycles = AnalyzerUtil.combineSets(cycles);
            AnalyzerUtil.logGroups(cycles, "Combined cycles");
        }

        AnalyzerUtil.logGroups(cycles, "Cycles to wrap"); //$NON-NLS-1$

        // if only one cycle and it contains everything except start or end activities, then no need to continue
        if (cycles.size()==1) {
            if (!AnalyzerUtil.isBoxNecessary(flow, cycles.get(0))) {
                flow.setContainsCycle(true);
                return false;   // no need to box this group
            }
        }

        if (cycles.size()==0) {
            //nothing can be done
            return false;
        }

        // each remaining cycle is to be placed into a new flow
        boolean result = false;
        for (Set<AnalyzerTask> cycle: cycles) {
            List<AnalyzerLink> inLinks = AnalyzerUtil.getIncomingLinks(cycle);
            if (inLinks.size()<1) {
                // this is an error. should be at least one
                AnalyzerTask cycleTask = (AnalyzerTask)cycle.toArray()[0];
                flow.addIssue(NO_INCOMING_LINK, cycleTask.getXpdlActivity());
                if (Tracing.ENABLED) Tracing.trace("Validation error: "+cycleTask + ": no incoming link"); //$NON-NLS-1$
            } else {
            	result = buildBox(flow, cycle, true);        	
            }
        }

        return result;
    }

    /**
     * Extracts the group activities from the current flow and inserts them into a new flow which is itself
     * inserted into the current flow.  Links into and out of new flow are left intact, but are recorded as crossing the flow boundary.
     * These will need to be taken into account during the conversion processing.
     * Some differences between cycle and merge group exist.
     * - cycle needs to add starting point, mergeGroup does not.
     * - cycle needs at least one incoming link
     * @param flow parent flow for the group of activities
     * @param group group of activities to box up into a new flow
     * @param cycle true if cycle group, false if merge group
     * @return TODO
     */
    private boolean buildBox(AnalyzerParentTask flow, Set<AnalyzerTask> group, boolean cycle) {
    	//used for possible undo after exception
    	Map<AnalyzerLink, AnalyzerTask> linkTargets = new LinkedHashMap<AnalyzerLink, AnalyzerTask>();
    	Map<AnalyzerLink, AnalyzerTask> linkSources = new LinkedHashMap<AnalyzerLink, AnalyzerTask>();
    	Map<AnalyzerLink, AnalyzerParentTask> boundaryCrossedOnExit = new LinkedHashMap<AnalyzerLink, AnalyzerParentTask>();
    	Map<AnalyzerLink, AnalyzerParentTask> boundaryCrossedOnEnter = new LinkedHashMap<AnalyzerLink, AnalyzerParentTask>();   	
    	
        List<AnalyzerLink> inLinks = AnalyzerUtil.getIncomingLinks(group);

        AnalyzerParentTask newFlow = new AnalyzerParentTask(flow.getProcess().getNextGenNumber());
        newFlow.setTaskType(TaskType.Flow);
        flow.getProcess().addSubFlow(newFlow);
        //    newFlow.setContainsCycle(true);

        // add newFlow to oldFlow
        flow.addChild(newFlow);

        // move tasks from old flow into new flow
        for (AnalyzerTask task: group) {
            if (task.getParent()==null) continue; // can not move if not a child such as boundary task
            flow.removeChild(task);
            if (task.getTaskType().equals(TaskType.Gateway) && task.getGatewayType().equals(GatewayType.COMPLEX)) {
            	// new box needs to be early exit for n-of-m support
            	newFlow.setEarlyExit(true);
                //newFlow.setJoinSplitTypeOut(JoinSplitType.COMPLEX_LITERAL);
            }
            newFlow.addChild(task);
        }
        
        try {
        	// handle incoming links
        	//List<AnalyzerTask> targets = AnalyzerUtil.getTargetActivities(inLinks);
        	if (!cycle) {
        		// single entry point. redirect the in links to the new flow
        		for (AnalyzerLink link: inLinks) {
        			linkTargets.put(link, link.getRealTargetTask());
        			flow.redirectLinkTarget(link, newFlow);
        		}
        	} else {
        		// new flow has no starting point since it is a cycle and if a task had no incoming link it would not be part of the cycle group
        		// the converter needs to add startpoint and adjust links. Need fan-in if more than one entry point.
        		// for now, just mark entry links as an in crossing links to the new flow
        		for (AnalyzerLink link: inLinks) {
        			// setup for undo
        			boundaryCrossedOnEnter.put(link, link.getBoundaryCrossedOnEnter());
        			link.setBoundaryCrossedOnEnter(newFlow);
        			newFlow.addInCrossingLink(link);
        		}
        	}

        	// handle outgoing links
        	List<AnalyzerLink> outLinks = AnalyzerUtil.getOutgoingLinks(group);
        	List<AnalyzerTask> exitPoints = AnalyzerUtil.getSourceActivities(outLinks);
        	if (exitPoints.size()==1) {
        		AnalyzerTask exitPoint = exitPoints.get(0);
        		if (exitPoint.getAllOutLinks().size() == outLinks.size()) {
        			// if all links from exit point exit they can be resourced
        			for (AnalyzerLink link: outLinks) {
        				// redirect outLinks source to newFlow
            			linkSources.put(link, link.getRealSourceTask());
        				flow.redirectLinkSource(link, newFlow);
        			}
        		} else {
        			// all links from exit point don't exit so none can be resourced. The converter needs to add new exit point if only one link or fan out if multiple links, but for now just mark exit links as out crossing links
        			for (AnalyzerLink link: outLinks) {
        				//setup for undo
            			boundaryCrossedOnExit.put(link, link.getBoundaryCrossedOnExit());
        				link.setBoundaryCrossedOnExit(newFlow);
        				newFlow.addOutCrossingLink(link);
        			}
        		}
        	} else if (exitPoints.size()>1) {
        		//more than one exit point. the converter will need to do fan out. for now, just mark exit links as out crossing links
        		for (AnalyzerLink link: outLinks) {
        			// setup for undo
        			boundaryCrossedOnExit.put(link, link.getBoundaryCrossedOnExit());
        			link.setBoundaryCrossedOnExit(newFlow);
        			newFlow.addOutCrossingLink(link);
        		}
        		//mark newFlow as XOR split. If it isn't XOR, the group would have been expanded to the end.
        		newFlow.setJoinSplitTypeOut(JoinSplitType.EXCLUSIVE_LITERAL);
        	}

        	// handle interior links
        	List<AnalyzerLink> interiorLinks = AnalyzerUtil.getInteriorLinks(group);
        	for (AnalyzerLink link: interiorLinks) {
        		// move link from parent into new flow
        		flow.redirectLinkOwner(link, newFlow);
        	}
        	if (getRootTasks(newFlow).isEmpty()) {
    			if (Tracing.ENABLED) Tracing.trace("no start task in flow "+newFlow.getName());
            	throw new Exception("no start task in flow "+newFlow.getName());
    		}
        	newFlow.reset();
        	return true;
        } catch (Exception ex) {
        	// restore back to original state
        	//ex.printStackTrace();
            flow.getProcess().getSubFlows().remove(newFlow);
            flow.removeChild(newFlow);
            List<AnalyzerTask> childTasks = new ArrayList<AnalyzerTask>(newFlow.getChildren());
            for (AnalyzerTask task: childTasks) {
                newFlow.removeChild(task);
                flow.addChild(task);
            }
            for (Entry<AnalyzerLink, AnalyzerTask> linkTarget: linkTargets.entrySet()) {
    			flow.redirectLinkTarget(linkTarget.getKey(), linkTarget.getValue());
    		}
            for (Entry<AnalyzerLink, AnalyzerTask> linkSource: linkSources.entrySet()) {
    			flow.redirectLinkSource(linkSource.getKey(), linkSource.getValue());
    		}
            for (Entry<AnalyzerLink, AnalyzerParentTask> crossedOnEnter: boundaryCrossedOnEnter.entrySet()) {
            	crossedOnEnter.getKey().resetBoundaryCrossedOnEnter(crossedOnEnter.getValue());
    		}
            for (Entry<AnalyzerLink, AnalyzerParentTask> crossedOnExit: boundaryCrossedOnExit.entrySet()) {
            	crossedOnExit.getKey().resetBoundaryCrossedOnExit(crossedOnExit.getValue());
    		}
            List<AnalyzerLink> interiorLinks = newFlow.getLinks();
        	for (AnalyzerLink link: interiorLinks) {
        		// move link from parent into new flow
        		flow.redirectLinkOwner(link, flow);
        	}
        	return false;
        }
    }

    /**
     * Try to extract merge group into a new flow for specified merge task
     * @param flow parent flow for the group of activities
     * @param mergeTask starting point from merge group
     * @return true if change made, false if no new box constructed
     */
    private boolean boxSplitMerge(AnalyzerParentTask flow, AnalyzerTask mergeTask) {
        Set<AnalyzerTask> mergeTasks = new LinkedHashSet<AnalyzerTask>();
        mergeTasks.add(mergeTask);
        return boxSplitMerge(flow, mergeTasks);
    }

    /**
     * Try to extract merge group into a new flow for all merge tasks in the flow
     * @param flow parent flow for the group of activities
     * @return true if change made, false if no new box constructed
     */
    private boolean boxSplitMerge(AnalyzerParentTask flow) {
        return boxSplitMerge(flow, flow.getControlledMerges());
    }

    /**
     * Extract merge group into a new flow
     * @param flow parent flow for the group of activities
     * @return true if change made, false if no new box constructed
     */
    private boolean boxSplitMerge(AnalyzerParentTask flow, Set<AnalyzerTask> controlledMerges) {
        List<Set<AnalyzerTask>> mergeGroups = new ArrayList<Set<AnalyzerTask>>();

        // for each controlled merge, construct related group of activities to box up
        for (AnalyzerTask act: controlledMerges) {
            Set<AnalyzerTask> mergeGroup = new LinkedHashSet<AnalyzerTask>();
            AnalyzerUtil.determineMergeGroup(act, mergeGroup);
            mergeGroups.add(mergeGroup);
        }
        
        AnalyzerUtil.logGroups(mergeGroups, "Merge groups");

        // combine merge groups that have a common member
        if (mergeGroups.size()>1) {
            AnalyzerUtil.logGroups(mergeGroups, "Merge groups to combine"); //$NON-NLS-1$
            mergeGroups = AnalyzerUtil.combineSets(mergeGroups);
        }

        // need to expand any merge group that has multiple entry points until there is only one entry point
        boolean done = false;
        while (!done) {
            done = true;
            for (Set<AnalyzerTask> mergeGroup: mergeGroups) {
                List<AnalyzerLink> inLinks = AnalyzerUtil.getIncomingLinks(mergeGroup);
                List<AnalyzerTask> targets = AnalyzerUtil.getTargetActivities(inLinks);
                if (targets.size() > 1) {
                    //need to expand group to include immediate predecessors
                    if (AnalyzerUtil.expandMergeGroup(mergeGroup)) {
                        // change had been made, re-combine groups
                        if (mergeGroups.size()>1) {
                            AnalyzerUtil.logGroups(mergeGroups, "Merge groups to combine"); //$NON-NLS-1$
                            mergeGroups = AnalyzerUtil.combineSets(mergeGroups);
                        }
                        done = false; // start over with modified groups
                        break;
                    }
                }
            }
        }

        // need to expand any merge group that has multiple exit points
        done = false;
        while (!done) {
            done = true;
            for (Set<AnalyzerTask> mergeGroup: mergeGroups) {
                List<AnalyzerLink> outLinks = AnalyzerUtil.getOutgoingLinks(mergeGroup);
                List<AnalyzerTask> exitPoints = AnalyzerUtil.getSourceActivities(outLinks);
                if (exitPoints.size()>1 || AnalyzerUtil.isMIDetected(flow, mergeGroup, false)) {
                    // more than one exit point or MI inside so need to expand
                    try {
                        if (AnalyzerUtil.expandToEnd(flow, mergeGroup, false, this)) {
                            // change had been made, re-combine groups
                            if (mergeGroups.size()>1) {
                                AnalyzerUtil.logGroups(mergeGroups, "Merge groups to combine"); //$NON-NLS-1$
                                mergeGroups = AnalyzerUtil.combineSets(mergeGroups);
                            }
                            done = false; // start over with modified groups
                            break;
                        }
                    } catch (Exception ex) {
                        return false; // problem with this group
                    }
                }
            }
        }

        AnalyzerUtil.logGroups(mergeGroups, "Merge groups to wrap"); //$NON-NLS-1$

        // if only one group and it contains everything except start or end activities, then no need to continue
        if (mergeGroups.size()==1) {
            if (!AnalyzerUtil.isBoxNecessary(flow, mergeGroups.get(0))) {
                return false;   // no need to box this group
            }
        }

        // each remaining mergeGroup is to be placed into a new flow
        boolean result = false;
        for (Set<AnalyzerTask> mergeGroup: mergeGroups) {
            //if exit point is not merge point, something is wrong
            if (AnalyzerUtil.isValidExitPoints(mergeGroup, flow)) {
                result = buildBox(flow, mergeGroup, false);
            }
        }

        return result;
    }

    private void analyzeFlow(AnalyzerParentTask flow) {
    	//clear link tokens
    	List<AnalyzerLink> links = flow.getLinks();
    	for (AnalyzerLink link: links) {
    		link.setTokens(null);
    	}
        List<AnalyzerTask> rootTasks = getRootTasks(flow);
        for (AnalyzerTask task : rootTasks) {
            Set<AnalyzerTask> visited = new LinkedHashSet<AnalyzerTask>();  // each root task starts with an empty visited list
            analyzeTaskCount = 0;
            startTime = System.currentTimeMillis();
            analyzeTask(flow, task, visited, null);
        }
        //adjust uncontrolled merges based on tokens
        updateUncontrolledMerges(flow);
    }

    private void analyzeTask(AnalyzerParentTask flow, AnalyzerTask task, Set<AnalyzerTask> visited, AnalyzerLink link) {
    	if (Tracing.ENABLED) {
    		StringBuffer buf = new StringBuffer();
    		if (link!=null)
    			AnalyzerUtil.logLink(link, buf, "** ");
    		Tracing.trace("Task "+task+ " entering from "+buf.toString());
    	}
    	analyzeTaskCount++;
        if (maxTime>0 && System.currentTimeMillis()-startTime>maxTime*1000) {
        	flow.addIssue(FLOW_ANALYSIS_TIMEOUT, task.getXpdlActivity());
        	if (Tracing.ENABLED) Tracing.trace("Flow analysis timeout, analyzeTaskCount="+analyzeTaskCount);
        	throw new RuntimeException("time limit exceeded");
        }
        if (visited.contains(task)) {
            // loop back
            flow.addLoopbackLink(link);
        } else {
            // update visited list
            task.addPredecessors(visited);

            // process merge
            List<AnalyzerLink> links = task.getAllInLinks();
            if (links.size()>1) {
                if (task.getTaskType().equals(TaskType.Gateway)) {
                    switch (task.getGatewayType()) {
                        case XOR_EVENT:
                        case XOR:
                            flow.addUncontrolledMerge(task);
                            break;
                        case OR:
                        case AND:
                        case COMPLEX:
                        default:
                            flow.addControlledMerge(task);
                    }
                } else {
                    // more than one incoming link to a task defaults to uncontrolled merge
                    flow.addUncontrolledMerge(task);
                }
            } else if (links.size()==1) {
            	// uncontrolled merge if target of timer boundary event with continue option and cycle
            	AnalyzerLink inLink = links.get(0);
            	if (AnalyzerUtil.isTimerContinueEvent(inLink.getRealSourceTask())) {
            	     TriggerTimer timer = ((IntermediateEvent)inLink.getRealSourceTask().getXpdlActivity().getEvent()).getTriggerTimer();
                     if (timer.getTimeCycle() != null) {
                    	 flow.addUncontrolledMerge(task);
                    	 task.setUncontrolledMerge(true); // force uncontrolled merge
                     }
            	}
            }

            // prepare outgoing tokens and follow outbound links to next activity
            List<AnalyzerLink> outLinks = task.getAllOutLinks();
            // remove outlinks leading outside this flow
            for (AnalyzerLink outLink: new ArrayList<AnalyzerLink>(outLinks)) {
                if (!flow.getChildren().contains(outLink.getTargetTask())) {
                    outLinks.remove(outLink);
                }
            }
            AnalyzerLink nextLink;
            Set<AnalyzerTask> nextVisited = new LinkedHashSet<AnalyzerTask>(task.getPredecessors());
        	nextVisited.add(task); // add self to visited list to pass on
            if (outLinks.size()==0) {
                // path ends
            } else if (outLinks.size()==1) {
                // only one path out, no new tokens, just pass on
            	nextLink = outLinks.get(0);
                //if already visited with the same tokens then ignore
            	if (!alreadyVisited(nextVisited, nextLink, link==null?null:link.getTokens(), null)) {
            		nextLink.setTokens(mergeIncomingTokens(flow, task));
	                analyzeTask(flow, nextLink.getTargetTask(), new LinkedHashSet<AnalyzerTask>(nextVisited), nextLink);  
            	}
            	else if (Tracing.ENABLED) {
            		Tracing.trace("Task "+nextLink.getTargetTask()+" was already visited from "+task+", link= "+nextLink);
            	}
            } else {
                // multiple paths out, create new token for each path
                for (int i=0; i<outLinks.size(); i++) {
                	nextLink = outLinks.get(i);
                    Token newToken = new Token(task, i+1, outLinks.size(), AnalyzerUtil.getTaskOutputType(task));
                	//if already visited with the same tokens then ignore
                	if (!alreadyVisited(nextVisited, nextLink, link==null?null:link.getTokens(), newToken)) {
                		Map<AnalyzerTask, Set<Token>> nextTokens = new LinkedHashMap<AnalyzerTask, Set<Token>>(mergeIncomingTokens(flow, task));
                        if (nextTokens.get(newToken.getOriginator())==null) {
                            // first token for this originator
                            nextTokens.put(newToken.getOriginator(), new LinkedHashSet<Analyzer.Token>());
                        }
                        nextTokens.get(newToken.getOriginator()).add(newToken);
                		nextLink.setTokens(nextTokens);
	                    analyzeTask(flow, nextLink.getTargetTask(), new LinkedHashSet<AnalyzerTask>(nextVisited), nextLink);
                	}
                	else if (Tracing.ENABLED) {
                		Tracing.trace("Task "+nextLink.getTargetTask()+" was already visited from "+task+", link= "+nextLink);
                	}
                }
            }
        }
    }

    private static boolean alreadyVisited(Set<AnalyzerTask> nextVisited, AnalyzerLink nextLink, Map<AnalyzerTask, Set<Token>> incomingTokens, Token newToken) {
    	if (nextLink.getTargetTask().getPredecessors().containsAll(nextVisited)) {
	    	if (incomingTokens==null || incomingTokens.isEmpty()) {
	    		if (newToken == null) {
	    			return nextLink.getTokens()==null || nextLink.getTokens().isEmpty();
		    	} else {
		    		incomingTokens = new LinkedHashMap<AnalyzerTask, Set<Token>>();
	    		}
	    	}	
	    	// at least 1 token to pass on
			if (nextLink.getTokens()==null || nextLink.getTokens().isEmpty()) {
				//no tokens set - need to visit
				return false;
			}
			boolean removeToken = false;
			if (newToken!=null) {
				//add to map
				if (incomingTokens.get(newToken.getOriginator())==null) {
                    // first token for this originator
					incomingTokens.put(newToken.getOriginator(), new LinkedHashSet<Analyzer.Token>());
                }
				removeToken = incomingTokens.get(newToken.getOriginator()).add(newToken);
			}
			// nextLink has at least 1 token
			for (Entry<AnalyzerTask, Set<Token>> entry:nextLink.getTokens().entrySet()) {
				boolean found = false;
				for (Entry<AnalyzerTask, Set<Token>> entry2:incomingTokens.entrySet()) {
					if (entry.equals(entry2)) {
						found = true;
						break;
					}
				}
				if (!found) {
					//no need to revert map as we'll override the tokens anyways
					return false;
				}
			}
			if (removeToken) {
				//revert map
				incomingTokens.get(newToken.getOriginator()).remove(newToken);
				if (incomingTokens.get(newToken.getOriginator()).isEmpty()) {
					incomingTokens.remove(newToken.getOriginator());
				}
			}
	    	return true;
    	}
    	return false;
    }
    
    // prepare tokens to pass on to next activity
    // if all of one source are present, they can be removed at this point unless it's uncontrolled merge
    public static Map<AnalyzerTask, Set<Token>> mergeIncomingTokens(AnalyzerParentTask flow, AnalyzerTask task) {
        Map<AnalyzerTask, Set<Token>> result = new LinkedHashMap<AnalyzerTask, Set<Token>>();
        if (task.getJoinStarterToken()!=null) {
        	// join starter case
        	Set<Token> tokenSet = new LinkedHashSet<Token>();
        	tokenSet.add(task.getJoinStarterToken());
        	result.put(task.getJoinStarterToken().getOriginator(), tokenSet);
        	return result;
        }
        for (AnalyzerLink link: task.getAllInLinks()) {
            Map<AnalyzerTask, Set<Token>> tokens = link.getTokens();
            if (tokens!=null) {
                for (AnalyzerTask key: tokens.keySet()) {
                    Set<Token> tokenSet = tokens.get(key);
                    if (result.containsKey(key)) {
                        //merge
                        Set<Token> resultTokenSet = result.get(key);
                        for (Token token: tokenSet) {
                            if (!resultTokenSet.contains(token)) {
                                resultTokenSet.add(token);
                            }
                        }
                    } else {
                        //add
                        result.put(key, new LinkedHashSet<Token>(tokenSet));
                    }
                }
            }
        }
        
        boolean isUncontrolledMerge = task.isUncontrolledMerge() || (flow.getUncontrolledMerges().contains(task) && !isAlternativeFlow(task));
        // compress tokens unless it's an uncontrolled merge 
        if (!isUncontrolledMerge) {
	        for (AnalyzerTask sourceTask: new LinkedHashMap<AnalyzerTask, Set<Token>>(result).keySet()) {
	            Set<Token> tokens = result.get(sourceTask);
	            if (tokens.size()==tokens.iterator().next().getOf()) {
	                // all the tokens from this source are merged and can be removed 
	            	result.remove(sourceTask);
	            }
	        }
        }
        return result;
    }
    
    private void updateUncontrolledMerges(AnalyzerParentTask flow) {
        Iterator<AnalyzerTask> it = flow.getUncontrolledMerges().iterator();
        while (it.hasNext()) {
            AnalyzerTask task = it.next();
            if (!task.isUncontrolledMerge() && isAlternativeFlow(task)) {
                it.remove();
            }
        }
    }

    private static boolean isAlternativeFlow(AnalyzerTask task) {
        // The task is already known to be a potential uncontrolled merge.
        // This check is to see if that can reduced because it is alternative flow and only one path at a time can ever be taken.
        // The XOR token test is applied first. If does not indicate alternative flow, than the AND token test is applied.
        // XOR token test:
        //   It may be alternative flow if each link has at least one XOR token from a common originator.
        //   If multiple links have the same XOR token from the same originator, than those links need to be grouped and evaluated by the AND tests to see if the each group is alternative flow.
        // AND token test:
        //   It is aternative flow if each link has the same AND/OR tokens.
        List<AnalyzerLink> links = task.getAllInLinks();
        for (AnalyzerLink link: links) {
            Map<AnalyzerTask, Set<Token>> tokens = link.getTokens();
            if (tokens==null) {
                // if one link has no tokens then all the links need to have no tokens to be alternative flow
                for (AnalyzerLink link2: links) {
                    Map<AnalyzerTask, Set<Token>> tokens2 = link2.getTokens();
                    if (tokens2!=null && tokens2.size()>0) {
                        // not equal, parallel flow
                        return false;
                    }
                }
            } else {
                // isolate XOR tokens and check that each link has at least one XOR token from a common originator
                Set<AnalyzerTask> originators = tokens.keySet();
                for (AnalyzerTask originator: originators) {
                	if (Tracing.ENABLED) {
	                    StringBuffer buf = new StringBuffer();
	                    AnalyzerUtil.logLink(link, buf, "** ");
	                    Tracing.trace("checking for XOR for ["+originator+"] : "+buf.toString());
                	}
                    if (isXorTokenPresent(tokens.get(originator))) {
                        // originator has XOR token
                        boolean allAgree = true;
                        for (AnalyzerLink link2: links) {
                            Map<AnalyzerTask, Set<Token>> tokens2 = link2.getTokens();
                            if (tokens2==null || !tokens2.containsKey(originator)) {
                                allAgree = false; // this originator not present
                                break;
                            }
                        }
                        if (allAgree) {
                            // all have XOR token from same originator. now apply rest of XOR test
                            if (!XorTestWithCommonOriginator(links, originator)) {
                                return false;
                            }
                        }
                    }
                }

                // remove XOR tokens and check that each link has the same tokens
                return andAlternativeFlowTest(tokens, links);
            }
        }
        return true;
    }

    /**
     * All links have an XOR token from this originator.
     * If multiple links have the same XOR token, than those links need to be grouped and evaluated by the AND tests to see if the each group is alternative flow.
     * @param links
     * @param originator
     * @return return true if alternative flow
     */
    private static boolean XorTestWithCommonOriginator(List<AnalyzerLink> links, AnalyzerTask originator) {
        for (AnalyzerLink link: links) {
            Set<AnalyzerLink> tempLinks = new LinkedHashSet<AnalyzerLink>();
            boolean addedSelf = false;
            Set<Analyzer.Token> xorTokens = separateTokens(link.getTokens(), true).get(originator);
            for (AnalyzerLink link2: links) {
                if (link == link2) continue; // skip checking self
                Set<Analyzer.Token> xorTokens2 = separateTokens(link2.getTokens(), true).get(originator);
                for (Analyzer.Token tk1: xorTokens) {
                    for (Analyzer.Token tk2: xorTokens2) {
                        if (tk1.equals(tk2)) {
                            if (!addedSelf) {
                                tempLinks.add(link);
                                addedSelf = true;
                            }
                            tempLinks.add(link2);
                            break;
                        }
                    }
                }
            }
            if (!andAlternativeFlowTest(link.getTokens(), tempLinks)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Apply AND test for alternative flow.
     * It is alternative flow if the non-XOR tokens for each link are the same
     * @param links
     * @return return true if alternative flow
     */
    private static boolean andAlternativeFlowTest(Map<AnalyzerTask, Set<Token>> tokens, Collection<AnalyzerLink> links) {
        // remove XOR tokens and check that each link has the same tokens
        Map<AnalyzerTask, Set<Token>> andTokens = separateTokens(tokens, false);
        for (AnalyzerLink link: links) {
            Map<AnalyzerTask, Set<Token>> andTokens2 = link.getTokens();
            andTokens2 = separateTokens(andTokens2, false);
            if (!andTokens.equals(andTokens2)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isXorTokenPresent(Set<Token> tokens) {
        for (Token token: tokens) {
            if (token.getType().getValue()==JoinSplitType.EXCLUSIVE) {
                return true;
            }
        }
        return false;
    }

    private static Map<AnalyzerTask, Set<Token>> separateTokens(Map<AnalyzerTask, Set<Token>> tokens, boolean keepXOR) {
    	if (tokens == null) {
    		return Collections.emptyMap();
    	}

        Map<AnalyzerTask, Set<Token>> result = new LinkedHashMap<AnalyzerTask, Set<Token>>(tokens);
    	for (AnalyzerTask task: tokens.keySet()) {
            Set<Token> tokenSet = tokens.get(task);
            Set<Token> newSet = new LinkedHashSet<Token>();
            for (Token token: tokenSet) {
                if (keepXOR && token.getType().equals(JoinSplitType.EXCLUSIVE_LITERAL)) {
                    newSet.add(token);
                }
                if (!keepXOR && !token.getType().equals(JoinSplitType.EXCLUSIVE_LITERAL)) {
                    newSet.add(token);
                }
            }
            if (newSet.size()>0) {
                result.put(task, newSet);
            } else {
                // all tokens removed
                result.remove(task);
            }
        }
    	return result;
    }

    private AnalyzerTask findTask(AnalyzerParentTask parent, String xpdlId) {
        for (AnalyzerTask task: parent.getChildren()) {
            com.tibco.xpd.xpdl2.Activity xpdlActivity = task.getXpdlActivity();
            if (xpdlActivity!=null) {
                if (xpdlActivity.getId().equals(xpdlId)) {
                    // found
                    return task;
                } else {
                    if (task instanceof AnalyzerParentTask) {
                        // check next level down
                        AnalyzerTask result = findTask((AnalyzerParentTask)task, xpdlId);
                        if (result != null) {
                            return result;
                        }
                    }
                }
            }
        }
        return null;
    }

    private static List<AnalyzerTask> getRootTasks(AnalyzerParentTask parent) {
        List<AnalyzerTask> rootTasks = new ArrayList<AnalyzerTask>();
        for (AnalyzerTask child: parent.getChildren()) {
            if (child.getAllInLinks().size()==0) {
                // has no incoming link so is a root task unless it is a boundary event
                if (!child.getTaskType().equals(TaskType.IntermediateBoundaryEvent)) {
                    rootTasks.add(child);
                }
            }
        }
        if (parent.getInCrossingLinks().size()>0) {
            // add in crossing links (rootTasks should really be empty at this point if there are any in crossing links
            List<AnalyzerTask> startingPoints = AnalyzerUtil.getRealTargetActivities(parent.getInCrossingLinks());
            rootTasks.addAll(startingPoints);
        }
        return rootTasks;
    }

    // supporting classes (Token and Issue)

    public static class Token {
        AnalyzerTask originator;
        int id;
        int of;
        JoinSplitType type;

        public Token(AnalyzerTask originator, int id, int of, JoinSplitType type) {
            this.originator = originator;
            this.id = id;
            this.of = of;
            this.type = type;
        }

        public AnalyzerTask getOriginator() {
            return originator;
        }

        public int getId() {
            return id;
        }

        public int getOf() {
            return of;
        }

        public JoinSplitType getType() {
            return type;
        }

        public String toString() {
            return "from ["+originator.toString()+"] "+id+" of "+of+" ("+type+")";
        }

        public boolean equals(Object obj) {
            if (obj instanceof Token) {
                Token token = (Token)obj;
                if (originator==token.originator && id==token.id && of==token.of) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return  16*originator.hashCode() + 4*(id-1) + (of-1);
        }

    }
}
