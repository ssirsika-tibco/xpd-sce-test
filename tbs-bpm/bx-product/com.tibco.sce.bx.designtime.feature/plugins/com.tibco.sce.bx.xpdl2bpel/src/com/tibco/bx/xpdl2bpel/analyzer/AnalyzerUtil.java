package com.tibco.bx.xpdl2bpel.analyzer;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.Tracing;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.MIFlowConditionType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * Created by IntelliJ IDEA.
 * User: goldberg
 * Date: Sep 3, 2008
 * Time: 6:58:34 PM
 */

public class AnalyzerUtil {
	
    public static final String PREF_ANALYZER_TIMEOUT = ConverterActivator.PLUGIN_ID + ".analyzer.timeout"; //$NON-NLS-1$
    public static final int PREF_ANALYZER_TIMEOUT_DEFAULT = 0; // no time limit

    /*
        Determine the list of activities leading into controlled merge that will be boxed up.
        Follow links backward from controlled merge activity until reaching a split activity, starting point
        or another controlled merge. Accumulate activities into group while traversing the backward links.
    */
    static Set<AnalyzerTask> determineMergeGroup(AnalyzerTask target, Set<AnalyzerTask> group) {
        group.add(target);
        for (AnalyzerLink link: target.getAllInLinks()) {
            AnalyzerTask sourceAct = link.getSourceTask();
            if (isControlledMerge(sourceAct) || isSplitActivity(sourceAct)) {
                // add to group and stop traversing
                group.add(sourceAct);
            } else {
                // repeat steps with new source act
                determineMergeGroup(sourceAct, group);
            }
        }
        return group;
    }

    //expands group to include immediate predecessors
    static boolean expandMergeGroup(Set<AnalyzerTask> group) {
        boolean changeMade = false;
        List<AnalyzerLink> inLinks = getIncomingLinks(group);
        for (AnalyzerLink link: inLinks) {
            group.add(link.getSourceTask());
            changeMade = true;
        }
        return changeMade;
    }

    /**
     * Verify that exit points are valid - must be a controlled merge
     * @param mergeGroup group to validate
     * @param flow
     * @return true if okay, false if not
     */
    static boolean isValidExitPoints(Set<AnalyzerTask> mergeGroup, AnalyzerParentTask flow) {
        List<AnalyzerLink> outLinks = getOutgoingLinks(mergeGroup);
        List<AnalyzerTask> exitPoints = getSourceActivities(outLinks);
        for (AnalyzerTask exitPoint: exitPoints) {
            if (!isControlledMerge(exitPoint)) {
                flow.addIssue(Analyzer.CANNOT_EXECUTE, exitPoint.getXpdlActivity());
                if (Tracing.ENABLED) Tracing.trace("Validation error: "+exitPoint + " is not a controlled merge exit point"); //$NON-NLS-1$
                return false;
            }
        }
        return true;
    }

    static boolean isSplitActivity(AnalyzerTask act){
        if (act.getAllOutLinks().size()>1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *  For each loopback link, the activities within the loop consist of the set of preceding activities of the source activity
     *  minus the set of preceding activities of the target activity plus the source activity.
     * @param link loopback link
     * @return returns set of tasks comprising the cycle
     */
    static Set<AnalyzerTask> determineCycle(AnalyzerLink link) {
        Set<AnalyzerTask> cycle = new LinkedHashSet<AnalyzerTask>(link.getSourceTask().getPredecessors());
        cycle.removeAll(link.getTargetTask().getPredecessors());
        cycle.add(link.getSourceTask());
        return cycle;
    }

    /**
     * Checks to see if proposed cycle group contains a controlled merge
     * @param flow parent of all tasks in cycle group
     * @param cycle cycle group to check
     * @return  controlled merge task contains within cycle group
     */
    static AnalyzerTask containsMerge(AnalyzerParentTask flow, Set<AnalyzerTask> cycle) {
        for (AnalyzerTask task: cycle) {
            if (flow.getControlledMerges().contains(task)) {
                return task;
            }
        }
        return null;
    }

    /**
     * Checks to see if proposed merge group intersects with a cycle
     * @param flow parent of all tasks in merge group
     * @param cycles to check
     * @return true if intersects with a cycle
     */
    static boolean containsCycle(AnalyzerParentTask flow, Set<AnalyzerTask> mergeGroup, List<Set<AnalyzerTask>> cycles) {
        for (Set<AnalyzerTask> cycle: cycles) {
        	Set<AnalyzerTask> testCycle = new LinkedHashSet<AnalyzerTask>(cycle);
        	testCycle.retainAll(mergeGroup);
        	if (!testCycle.isEmpty()) {
        		return true;
        	}
        }
        return false;
    }
    
    /**
     * For a cycle:
     * If any split is other than XOR, than more than one path at a time may be taken and the group is MI.
     * Merges along the way may be ignored.  If the group is a merge group, no new merges should be encountered.
     * If the group is a cycle, any merge that could change MI status (controlled merge) will eventually be extracted into
     * a separate box so no need to account for that now.
     *
     * For a merge group:
     * If any uncontrolled merge (after eliminating alternate flow cases) inside group than group is MI.
     * @param flow parent flow
     * @param group to check
     * @param cycle true for cycle group, false for a merge group
     * @return true if MI detected, false if MI not detected
     */
    static boolean isMIDetected(AnalyzerParentTask flow, Set<AnalyzerTask> group, boolean cycle) {
        if (cycle) {
            for (AnalyzerTask task: group) {
                if (isParallelSplit(task)) {
                    return true;
                }
            }
        } else {
            for (AnalyzerTask task: group) {
                if (flow.getUncontrolledMerges().contains(task)) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean isBoxNecessary(AnalyzerParentTask flow, Set<AnalyzerTask> group) {
        boolean makeBox = false;
        Set<AnalyzerTask> allActivities = new LinkedHashSet<AnalyzerTask>(flow.getChildren());
        allActivities.removeAll(group);
        Set<AnalyzerTask>remainingActivites = allActivities;
        for (AnalyzerTask task: remainingActivites) {
            if (task.getAllOutLinks().isEmpty() || task.getAllInLinks().isEmpty()) {
                // continue, this is a start or end task so check next one
            } else {
                makeBox = true;
                break;
            }
        }
        return makeBox;
    }

    /**
     * Expands group from exit points to end of flow
     * @param flow parent task of group members
     * @param group to expand
     * @param cycle true if cycle group, false if merge group
     * @param analyzer
     * @return  true if change made, false if no change made
     */
    static boolean expandToEnd(AnalyzerParentTask flow, Set<AnalyzerTask> group, boolean cycle, Analyzer analyzer) {
        List<AnalyzerTask> additions = new ArrayList<AnalyzerTask>();   // tasks to add to group
        List<AnalyzerLink> outLinks = getGroupOutgoingLinks(group);
        List<AnalyzerTask> exitPoints = getSourceActivities(outLinks);
        List<AnalyzerTask> flowActivities = flow.getChildren();
        for (AnalyzerTask task: flowActivities) {
            if (!group.contains(task)) {
                // continue with activities not already in the group
                Set<AnalyzerTask> predecessors = task.getPredecessors();
                for (AnalyzerTask exitPoint: exitPoints) {
                    if (predecessors.contains(exitPoint)) {
                        // a group exitPoint is a predecessor so add to box
                        if (isControlledMerge(task)) {
                            // can not add controlled merge
                            //TODO is this an issue?
                            return false;
                        }
                        if (!cycle) {
                            // check if task is predecessor to exit point
                            if (exitPoint.getPredecessors().contains(task)) {
                                // in a cycle, can not add
                                flow.addIssue(Analyzer.CANNOT_EXECUTE, task.getXpdlActivity());
                                if (Tracing.ENABLED) Tracing.trace("Validation error: "+task + " can not include cycle"); //$NON-NLS-1$
                                throw new RuntimeException("can not include cycle"); //$NON-NLS-1$
                            }
                        }
                        additions.add(task);
                        break;
                    }
                }
            }
        }
        if (additions.size()>0) {
            group.addAll(additions);
            return true;
        } else {
            return false;
        }
    }

    // collects the source tasks of the specified links
    static List<AnalyzerTask> getSourceActivities(List<AnalyzerLink> links) {
        List<AnalyzerTask> result = new ArrayList<AnalyzerTask>();
        for (AnalyzerLink link: links) {
            AnalyzerTask source = link.getSourceTask();
            if (!result.contains(source)) {
                result.add(source);
            }
        }
        return result;
    }

    // collects the target tasks of the specified links
    static List<AnalyzerTask> getTargetActivities(List<AnalyzerLink> links) {
        List<AnalyzerTask> result = new ArrayList<AnalyzerTask>();
        for (AnalyzerLink link: links) {
            AnalyzerTask target = link.getTargetTask();
            if (!result.contains(target)) {
                result.add(target);
            }
        }
        return result;
    }

    // collects the target tasks of the specified links
    static List<AnalyzerTask> getRealTargetActivities(List<AnalyzerLink> links) {
        List<AnalyzerTask> result = new ArrayList<AnalyzerTask>();
        for (AnalyzerLink link: links) {
            AnalyzerTask target = link.getRealTargetTask();
            if (!result.contains(target)) {
                result.add(target);
            }
        }
        return result;
    }

    // collects the links that cross into the group of tasks
    static List<AnalyzerLink> getIncomingLinks(Set<AnalyzerTask> group) {
        List<AnalyzerLink> result = new ArrayList<AnalyzerLink>();
        for (AnalyzerTask task: group) {
            for (AnalyzerLink link: task.getAllInLinks()) {
                if (group.contains(link.getSourceTask())) {
                    // okay, source task also in group
                } else if (link.getSourceTask().isBoundaryTask() && group.contains(link.getSourceTask().getBoundaryParent())) {
                    // okay, source task is a boundary task also in the group
                } else {
                    // source task not in group
                    result.add(link);
                }
            }
        }
        return result;
    }

    // collects the links that cross out of the group of tasks
     static List<AnalyzerLink> getOutgoingLinks(Set<AnalyzerTask> group) {
         List<AnalyzerLink> result = new ArrayList<AnalyzerLink>();
         for (AnalyzerTask task: group) {
             for (AnalyzerLink link: task.getAllOutLinks()) {
                 if (group.contains(link.getTargetTask())) {
                     // okay, target task also in group
                 } else {
                     // target task not in group
                     result.add(link);
                 }
             }
         }
         return result;
     }

     // collects the links that do not croos in or cross out of the group of tasks
     static List<AnalyzerLink> getInteriorLinks(Set<AnalyzerTask> group) {
         List<AnalyzerLink> result = new ArrayList<AnalyzerLink>();
         for (AnalyzerTask task: group) {
             for (AnalyzerLink link: task.getAllOutLinks()) {
                 if (group.contains(link.getTargetTask())) {
                     // target task also in group
                     if (!result.contains(link)) {
                        result.add(link);
                     }
                 } else {
                     // target task not in group
                 }
             }
             for (AnalyzerLink link: task.getAllInLinks()) {
                 if (group.contains(link.getSourceTask())) {
                     // source task also in group
                     if (!result.contains(link)) {
                        result.add(link);
                     }
                 } else {
                     // source task not in group
                 }
             }
         }
         return result;
     }

    static boolean isControlledMerge(AnalyzerTask task) {
        if (task.getJoinSplitTypeIn()!=null && task.getJoinSplitTypeIn().getValue()!=JoinSplitType.EXCLUSIVE && task.getAllInLinks().size()>1) {
            return true;
        } else {
            return false;
        }
    }

    static boolean isParallelSplit(AnalyzerTask task) {
        JoinSplitType splitType = getTaskOutputType(task);
        if (splitType!=null && splitType.getValue()!= JoinSplitType.EXCLUSIVE && task.getAllOutLinks().size()>1) {
            return true;
        } else {
            return false;
        }
    }

    // collects the links that cross out of the group of tasks
    static List<AnalyzerLink> getGroupOutgoingLinks(Set<AnalyzerTask> group) {
        List<AnalyzerLink> result = new ArrayList<AnalyzerLink>();
        for (AnalyzerTask task: group) {
            List<AnalyzerLink> outLinks = task.getAllOutLinks();
            for (AnalyzerLink link: outLinks) {
                if (group.contains(link.getTargetTask())) {
                    // okay, target task also in cycle
                } else {
                    // target task not in cycle
                    result.add(link);
                }
            }
        }
        return result;
    }

    /**
     * Combine sets that share at least one member
     * @param sets initial sets to combine
     * @return  resulting sets after being combined
     */
    static List<Set<AnalyzerTask>> combineSets(List<Set<AnalyzerTask>> sets) {
         boolean done=false;
         while (!done) {
        	 boolean startOver = false;
             for (int i=0; i<sets.size(); i++) {
                 for (int j=i+1; j<sets.size(); j++) {
                     Set<AnalyzerTask> setCopy = new LinkedHashSet<AnalyzerTask>(sets.get(i));
                     setCopy.retainAll(sets.get(j));
                     if (setCopy.isEmpty()) {
                         // continue, sets are disjoint, check next set
                     } else {
                         // sets are not disjoint so merge them
                         sets.get(i).addAll(sets.get(j));
                         sets.remove(j);
                         startOver = true;
                         break;  // start over
                     }
                 }
                 if (startOver) break;
             }
             if (!startOver) done=true;
         }
         return sets;
     }

    static Analyzer.TaskType determineTaskType(com.tibco.xpd.xpdl2.Activity xpdlActivity) {
        if (xpdlActivity.getEvent() instanceof StartEvent) return Analyzer.TaskType.StartEvent;
        else if (xpdlActivity.getEvent() instanceof IntermediateEvent) {
            if (((IntermediateEvent)xpdlActivity.getEvent()).getTarget()!=null) {
                return Analyzer.TaskType.IntermediateBoundaryEvent;
            } else {
                return Analyzer.TaskType.IntermediateEvent;
            }
        }
        else if (xpdlActivity.getEvent() instanceof EndEvent) return Analyzer.TaskType.EndEvent;
        else if (xpdlActivity.getBlockActivity()!= null) {
        	boolean isEventSrc = XPDLUtils.isEventSubProcess(xpdlActivity.getBlockActivity());
        	return isEventSrc ? Analyzer.TaskType.EventSubProcess : Analyzer.TaskType.EmbeddedSubProcess;
        }
        else if (xpdlActivity.getRoute() != null) return Analyzer.TaskType.Gateway;
        else if (xpdlActivity.getImplementation() != null) {
            Implementation impl = xpdlActivity.getImplementation();
            if (impl instanceof SubFlow) return Analyzer.TaskType.IndependedSubProcess;
            else if (impl instanceof com.tibco.xpd.xpdl2.Task) {
                com.tibco.xpd.xpdl2.Task xpdlTask = (com.tibco.xpd.xpdl2.Task)impl;
                if (xpdlTask.getTaskManual() != null) return Analyzer.TaskType.ManaualTask;
                else if (xpdlTask.getTaskReceive() != null) return Analyzer.TaskType.ReceiveTask;
                else if (xpdlTask.getTaskScript() != null) return Analyzer.TaskType.ScriptTask;
                else if (xpdlTask.getTaskSend() != null) return Analyzer.TaskType.SendTask;
                else if (xpdlTask.getTaskService() != null) return Analyzer.TaskType.ServiceTask;
                else if (xpdlTask.getTaskUser() != null) return Analyzer.TaskType.UserTask;
            }
            return Analyzer.TaskType.Task;
        }
        return Analyzer.TaskType.OTHER;
    }

    static Analyzer.GatewayType determineGatewayType(com.tibco.xpd.xpdl2.Activity gateway) {
        Route route = gateway.getRoute();
        JoinSplitType jsType = route.getGatewayType();
        if (JoinSplitType.PARALLEL_LITERAL.equals(jsType)) return Analyzer.GatewayType.AND;
        else if (JoinSplitType.COMPLEX_LITERAL.equals(jsType)) return Analyzer.GatewayType.COMPLEX;
        else if (JoinSplitType.INCLUSIVE_LITERAL.equals(jsType)) return Analyzer.GatewayType.OR;
        else if (JoinSplitType.EXCLUSIVE_LITERAL.equals(jsType)) {
            if (ExclusiveType.EVENT.equals(route.getExclusiveType())) {
                return Analyzer.GatewayType.XOR_EVENT;
            } else {
                return Analyzer.GatewayType.XOR;
            }
        }
        return Analyzer.GatewayType.NA;
    }

    static boolean isAdhocActivity(com.tibco.xpd.xpdl2.Activity xpdlActivity) {
        AdHocTaskConfigurationType adHocTaskConfiguration = XPDLUtils.getAdHocTaskConfiguration(xpdlActivity);
        return adHocTaskConfiguration!=null;
    }
    
    static boolean isEventSubProcess(com.tibco.xpd.xpdl2.Activity xpdlActivity) {
    	return xpdlActivity!=null && xpdlActivity.getBlockActivity()!=null && XPDLUtils.isEventSubProcess(xpdlActivity.getBlockActivity());
    	
    }
    
    static JoinSplitType getTaskOutputType(AnalyzerTask task) {
        //todo if we support boundary event with continue option, then return JoinSplitType.OR_LITERAL;
        if (task.getJoinSplitTypeOut()!=null) {
            // already determined
            return task.getJoinSplitTypeOut();
        } else if (task.getAllOutLinks().size()>1) {
            if (task.getOutLinks().size()==1) {
                // one normal flow plus one or more error flows
            	if (isEarlyExit(task)) {
            		return JoinSplitType.INCLUSIVE_LITERAL;
            	} else {
            		return JoinSplitType.EXCLUSIVE_LITERAL;
            	}
            } else if (task.getOutLinks().size()==2) {
                boolean otherwiseExist = isOtherwiseLink(task.getOutLinks().get(0));
                if (!otherwiseExist) {
                    // first link not otherwise, check 2nd link
                    otherwiseExist = isOtherwiseLink(task.getOutLinks().get(1));
                }
                if (otherwiseExist) {
                    // one of two links is otherwise, so alternate flow
                    return JoinSplitType.EXCLUSIVE_LITERAL;
                }
            }
            return JoinSplitType.PARALLEL_LITERAL;
        } else {
            // no split type since only one out link
            return null;
        }
    }

    static boolean isEarlyExit(AnalyzerTask task) {
    	// early exit if MI loop with flow condition of ONE
    	Loop loop = task.getXpdlActivity().getLoop();
		if (loop != null) {
			LoopType loopType = loop.getLoopType();
			if (loopType.getValue() == LoopType.MULTI_INSTANCE) {
				LoopMultiInstance loopMI = loop.getLoopMultiInstance();
				if (loopMI != null) {
					if (loopMI.getMIFlowCondition().getValue() == MIFlowConditionType.ONE) {
						return true;
					}
				}
			}
		}
		// early exit if has boundary timer event with continue option
		List<AnalyzerTask> boundaryTasks = task.getBoundaryTasks();
		for (AnalyzerTask boundaryTask: boundaryTasks) {
			if (isTimerContinueEvent(boundaryTask)) {
				return true;
			}
		}
		return false;
    }
    
    static boolean isCompensationEvent(AnalyzerTask task) {
        if (task.getTaskType().equals(Analyzer.TaskType.IntermediateBoundaryEvent)) {
            if (((IntermediateEvent)task.getXpdlActivity().getEvent()).getTrigger().getValue() == TriggerType.COMPENSATION) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTimerContinueEvent(AnalyzerTask task) {
        if (task.getTaskType().equals(Analyzer.TaskType.IntermediateBoundaryEvent)) {
            if (((IntermediateEvent)task.getXpdlActivity().getEvent()).getTrigger().getValue() == TriggerType.TIMER) {
            	TriggerTimer timer = ((IntermediateEvent)task.getXpdlActivity().getEvent()).getTriggerTimer();
            	FeatureMap map = timer.getOtherAttributes();
                ListIterator<FeatureMap.Entry> iter = map.listIterator();
            	while (iter.hasNext()) {
            		FeatureMap.Entry item = iter.next();
                     String thename = item.getEStructuralFeature().getName();
            		 if ("continueOnTimeout".equals(thename)) {
                         return (Boolean)item.getValue();
                     }
            	}
            	return false;
            }
        }
        return false;   	
    }
    
    static boolean isOtherwiseLink(AnalyzerLink link) {
        if (link.getXpdlTransition()!=null && link.getXpdlTransition().getCondition()!=null) {
            if (ConditionType.OTHERWISE_LITERAL.equals(link.getXpdlTransition().getCondition().getType())) {
                return true;
            }
        }
        return false;
    }
    
    public static void moveDecendants(AnalyzerParentTask oldParent, AnalyzerParentTask newParent, AnalyzerTask startingPoint) {
    	//follow out link path to collect all transitions and tasks and move from oldParent to newParent
    	List<AnalyzerLink> outLinks = startingPoint.getOutLinks();
    	for (AnalyzerLink link: outLinks) {
    		AnalyzerTask targetTask = link.getRealTargetTask();
    		if (oldParent.removeChild(targetTask)) {
        		newParent.addChild(targetTask);
        		moveDecendants(oldParent, newParent, targetTask);
        		for (AnalyzerTask boundaryTask: targetTask.getBoundaryTasks()) {
        			moveDecendants(oldParent, newParent, boundaryTask);
        		}
    		}
    		oldParent.redirectLinkOwner(link, newParent);
    	}
    }

    public static String lineSep = "\r\n"; //System.getProperty("line.separator");

    public static void logFlow(AnalyzerParentTask flow) {
        if (Tracing.ENABLED) {
            StringBuffer buf = new StringBuffer();
            buf.append("Flow summary for "+flow); //$NON-NLS-1$
            if (flow.isDone()) {
                buf.append(" , (Marked Finish)"); //$NON-NLS-1$
            }
            if (flow.isContainsCycle()) {
                buf.append(", (Marked cycle)"); //$NON-NLS-1$
            }
            buf.append(lineSep);
            for (AnalyzerIssue issue: flow.getIssues()) {
                buf.append("  Issue: ");
                buf.append(issue.getIssueId());
                if (issue.getObj()!=null) {
                    buf.append(" for ");
                    buf.append(((com.tibco.xpd.xpdl2.Activity)issue.getObj()).getName());
                    buf.append(lineSep);
                }
            }
            List<AnalyzerTask> list = (List<AnalyzerTask>)flow.getChildren();
            buf.append("  tasks = [") ; //$NON-NLS-1$
            for (AnalyzerTask act: list) {
                buf.append(act.toString());
                if (act.isUncontrolledMerge()) {
                    buf.append("(uncontrolledMerge)");
                }
                buf.append(", "); //$NON-NLS-1$
            }
            buf.append("]"); //$NON-NLS-1$
            buf.append(lineSep);
            List<AnalyzerLink>links = flow.getLinks();
            if (links!=null && links.size()>0) {
                buf.append("  links = ["); //$NON-NLS-1$
                for (AnalyzerLink link: links) {
                    buf.append(link.toString());
                    buf.append(", "); //$NON-NLS-1$
                }
                buf.append("]"); //$NON-NLS-1$
            }
            Tracing.trace(buf.toString());
        }
    }

    public static void logGroups(List<Set<AnalyzerTask>> boxes, String title) {
        if (Tracing.ENABLED) {
            StringBuffer buf = new StringBuffer();
            buf.append(title).append(" = ").append(boxes.size()); //$NON-NLS-1$
            int num = 1;
            for (Set<AnalyzerTask> group : boxes) {
                buf.append(lineSep);
                buf.append("  group ").append(num++).append("="); //$NON-NLS-1$ //$NON-NLS-2$
                for (AnalyzerTask task: group) {
                    buf.append('[').append(task.toString()).append(']');
                }
            }

            Tracing.trace(buf.toString());
        }
    }

    public static String logProcessDataStructure(AnalyzerProcess process) {
        StringBuffer buf = new StringBuffer();
        buf.append("Process ");
        buf.append(process.getXpdlProcess().getName());
        buf.append(lineSep);
        String indent = "  ";
        logTask(process, buf, indent);
        return buf.toString();
    }

    protected static void logParentTask(AnalyzerParentTask parent, StringBuffer buf, String indent) {
        if (parent.getLoopbackLinks().size()>0) {
            buf.append(indent);
            buf.append("loopback links: ");
            buf.append(parent.getLoopbackLinks());
            buf.append(lineSep);
        }
        if (parent.getControlledMerges().size()>0) {
            buf.append(indent);
            buf.append("controlled merges: ");
            buf.append(parent.getControlledMerges());
            buf.append(lineSep);
        }
        if (parent.getUncontrolledMerges().size()>0) {
            buf.append(indent);
            buf.append("uncontrolled merges: ");
            buf.append(parent.getUncontrolledMerges());
            buf.append(lineSep);
        }
        for (AnalyzerTask task: parent.getChildren()) {
            logTask(task, buf, indent);
        }
        for (AnalyzerLink link: parent.getLinks()) {
            logLink(link, buf, indent);
        }
    }

    protected static void logTask(AnalyzerTask task, StringBuffer buf, String indent) {
        String newIndent = "  " + indent;
        buf.append(indent);
        buf.append("Task [");
        buf.append(task.toString());
        buf.append("] ");
        buf.append(task.getTaskType());
        if (task.isCreateInstance()) {
            buf.append(" createInstance");
        }
        if (task.isUncontrolledMerge()) {
            buf.append(" uncontrolledMerge");
        }
        if (task instanceof AnalyzerParentTask) {
            if (((AnalyzerParentTask)task).isContainsCycle()) {
                buf.append(" cycle");
            }
        }
        buf.append(lineSep);
        if (task.getPredecessors().size()>0) {
            buf.append(newIndent);
            buf.append("Predecessors: ");
            buf.append(task.getPredecessors());
            buf.append(lineSep);
        }
        if (task.getBoundaryTasks().size()>0) {
            buf.append(newIndent);
            buf.append("Boundary tasks: ");
            buf.append(task.getBoundaryTasks());
            buf.append(lineSep);
        }
        if (task instanceof AnalyzerParentTask) {
            logParentTask((AnalyzerParentTask)task, buf, newIndent);
        }
    }

    protected static void logLink(AnalyzerLink link, StringBuffer buf, String indent) {
        buf.append(indent);
        buf.append("Link [");
        buf.append(link.toString());
//        buf.append("] from [");
//        buf.append(link.getSourceTask().toString());
//        buf.append("] to [");
//        buf.append(link.getTargetTask().toString());
        buf.append("]");
        if (link.isLoopBack()) {
            buf.append(" loopback");
        }
        buf.append(lineSep);
        String moreIndent = indent + "  ";
        if (link.getTokens()!=null) {
            for (AnalyzerTask task: link.getTokens().keySet()) {
                Set<Analyzer.Token> tokens = link.getTokens().get(task);
                buf.append(moreIndent);
                buf.append("tokens: ");
                buf.append(tokens);
                buf.append(lineSep);
            }
        }
    }

}
