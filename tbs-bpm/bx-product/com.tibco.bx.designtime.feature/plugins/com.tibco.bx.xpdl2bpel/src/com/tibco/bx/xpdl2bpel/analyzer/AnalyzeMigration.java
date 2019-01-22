package com.tibco.bx.xpdl2bpel.analyzer;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.tibco.bx.xpdl2bpel.analyzer.Analyzer.Token;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.MIFlowConditionType;

public class AnalyzeMigration {
	
	
	static void detectAllowedMigrationPoints(AnalyzerParentTask parent) {
		for (AnalyzerTask task: parent.getChildren()) {
			
			Analyzer.TaskType taskType = task.getTaskType();
			if (taskType.equals(Analyzer.TaskType.StartEvent) || taskType.equals(Analyzer.TaskType.IntermediateBoundaryEvent)
					|| task.getEventHandlerBody() != null) {
				continue;
			}
			
			boolean migrationAllowed = isMigrationAllowed(parent, task);

          	task.setMigrationAllowed(migrationAllowed);
          	
          	if (migrationAllowed && task.getXpdlActivity() != null) {
          		parent.addIssue(Analyzer.MIGRATION_POINT, task.getXpdlActivity());
          	}

    		//if (migrationAllowed && links.size()>1 && task.getWaitFor()==null) {
          	// Only set if alternate flow BX-1584
          	boolean alternateFlow = task.getJoinSplitTypeIn()!=null && task.getJoinSplitTypeIn().getValue()==JoinSplitType.EXCLUSIVE;
          	
          	if (task.getAllInLinks().size()>1 && task.getWaitFor()==null && alternateFlow && !task.isUncontrolledMerge()) {
				// alternate flow merge so set waitFor=1
				task.setWaitFor(1);
			}
    		
			if (task instanceof AnalyzerParentTask) {
				// if this parent is an eligible migration point, and
				//    not an embedded sub-process or pick then process inside
				if (migrationAllowed &&
						!taskType.equals(Analyzer.TaskType.EmbeddedSubProcess) && 
						!taskType.equals(Analyzer.TaskType.Pick)) {
					detectAllowedMigrationPoints((AnalyzerParentTask)task);
				}	
			}
		}
	}

	private static boolean isMigrationAllowed(AnalyzerParentTask parent, AnalyzerTask task) {
		// check for task on parallel path (has non-exclusive token)
		Map<AnalyzerTask, Set<Token>> tokens = Analyzer.mergeIncomingTokens(parent, task);
		Set<AnalyzerTask> keys = tokens.keySet();
		for (AnalyzerTask key: keys) {
			Set<Token> tokenset = tokens.get(key);
			for (Token token : tokenset) {
				if (!token.getType().equals(JoinSplitType.EXCLUSIVE_LITERAL)) {
					return false;
				}
			}
		}
		
		if (task.getXpdlActivity()!=null && (task.getXpdlActivity().getName()==null || task.getXpdlActivity().getName().length()<1)) {
			// unnamed task cannot be migration point
			return false;
		}
		
		// if task is uncontrolled merge, migration not allowed
		if (task.isUncontrolledMerge()) {
			return false;
		}

		// if task n-of-m merge, migration not allowed
		if (task.isEarlyExit()) {
			return false;
		}

		// check predecessor
		Iterator<AnalyzerTask> precedingTasks = task.getPredecessors().iterator();
		while (precedingTasks.hasNext()) {
			AnalyzerTask precedingTask = precedingTasks.next();
			// if preceding task is uncontrolled merge, migration not allowed
			if (precedingTask.isUncontrolledMerge()) {
				return false;
			}
			
			// if preceding task is MI loop with flow condition of ONE, migration not allowed
			if (precedingTask.getXpdlActivity()!=null && XPDLUtils.isLoopActivity(precedingTask.getXpdlActivity()) && 
					precedingTask.getXpdlActivity().getLoop().getLoopType().getValue() == LoopType.MULTI_INSTANCE) {
				LoopMultiInstance loopMI = precedingTask.getXpdlActivity().getLoop().getLoopMultiInstance();
				if (loopMI!=null && MIFlowConditionType.ONE == loopMI.getMIFlowCondition().getValue()) {
					return false;
				}
			}
			
			// if preceding task n-of-m merge, migration not allowed
			if (precedingTask.isEarlyExit()) {
				return false;
			}
			
			// if within a cycle and preceding split task is not exclusive split, migration not allowed
			if (parent.isContainsCycle() && precedingTask.getParent().equals(parent) && AnalyzerUtil.isParallelSplit(precedingTask)) {
 				return false;
			}
		}
		
		if (parent.isContainsCycle()) {
			//if migration still allowed, check for loopback issues
			for (AnalyzerLink link: task.getAllInLinks()) {
				if (link.isLoopBack()) {
					Iterator<AnalyzerTask> loopbackPrecedingTasks = link.getSourceTask().getPredecessors().iterator();
					while (loopbackPrecedingTasks.hasNext()) {
						AnalyzerTask loopbackPrecedingTask = loopbackPrecedingTasks.next();
						if (loopbackPrecedingTask.isEarlyExit()) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}		
}
