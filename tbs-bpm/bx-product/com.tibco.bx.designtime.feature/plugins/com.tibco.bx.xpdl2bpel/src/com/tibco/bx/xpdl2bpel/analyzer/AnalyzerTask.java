package com.tibco.bx.xpdl2bpel.analyzer;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.bx.xpdl2bpel.analyzer.Analyzer.Token;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * Created by IntelliJ IDEA.
* User: goldberg
* Date: Sep 10, 2008
* Time: 4:57:07 PM
* To change this template use File | Settings | File Templates.
*/
public class AnalyzerTask {

    private com.tibco.xpd.xpdl2.Activity xpdlActivity = null;
    private AnalyzerParentTask parent;
    private AnalyzerTask boundaryParent;
    private AnalyzerTask association = null;   // currently, only supporting one assoication per task (used for compensation)
    private List<AnalyzerLink> inLinks = new ArrayList<AnalyzerLink>();
    private List<AnalyzerLink> outLinks = new ArrayList<AnalyzerLink>();
    private Analyzer.TaskType taskType = Analyzer.TaskType.OTHER;
    private Analyzer.GatewayType gatewayType = Analyzer.GatewayType.NA;
    private JoinSplitType joinSplitTypeIn = null;
    private JoinSplitType joinSplitTypeOut = null;
    private List<AnalyzerTask> boundaryTasks = new ArrayList<AnalyzerTask>();
    private List<AnalyzerTask> signalUpdateBoundaryTasks = new ArrayList<AnalyzerTask>();
    private boolean createInstance = false;
    private boolean uncontrolledMerge = false;
    private int genNumber = 0;
    private Object bpelReference = null;
    private Integer waitFor = null;
    private Integer maxTrue = null;
    private boolean migrationAllowed = false;
    private Token joinStarterToken = null;
    private AnalyzerParentTask eventHandlerBody;  // could be single task or parentTask (flow)
    private boolean earlyExit = false;
    private boolean adhocActivity = false;
    
	// the following may be reset on each pass of the analyzer
    private Set<AnalyzerTask> predecessors = new LinkedHashSet<AnalyzerTask>();

    public AnalyzerTask(com.tibco.xpd.xpdl2.Activity xpdlActivity) {
        this.xpdlActivity = xpdlActivity;
        if (xpdlActivity!=null) {
            taskType = AnalyzerUtil.determineTaskType(xpdlActivity);
            if (taskType.equals(Analyzer.TaskType.Gateway)) {
                gatewayType = AnalyzerUtil.determineGatewayType(xpdlActivity);
                if (xpdlActivity.getIncomingTransitions()!=null && xpdlActivity.getIncomingTransitions().size()>1) {
                	setJoinSplitTypeIn(xpdlActivity.getRoute().getGatewayType());
                }
                if (xpdlActivity.getOutgoingTransitions()!=null && xpdlActivity.getOutgoingTransitions().size()>1) {
                	setJoinSplitTypeOut(xpdlActivity.getRoute().getGatewayType());
                }
                if (gatewayType.equals(Analyzer.GatewayType.XOR))   {
                    EList<Transition> list = xpdlActivity.getOutgoingTransitions();
                    if (list!=null && list.size()>1) {
                        // XOR split. set MaxTrue=1.  Not always necessary, but won't hurt
                        setMaxTrue(1);
                    }
                }
            }
            adhocActivity = AnalyzerUtil.isAdhocActivity(xpdlActivity);
        }
    }

    public AnalyzerTask(int genNumber) {
        this.genNumber = genNumber;   // used in logs to more easily distinguish between generated tasks
    }
    
    public void resetPredecessors() {
        predecessors.clear();
    }

    public String getName() {
        if (xpdlActivity != null) {
            return xpdlActivity.getName();
        } else {
            return "generated"+genNumber+taskType; //$NON-NLS-1$
        }
    }

    public com.tibco.xpd.xpdl2.Activity getXpdlActivity() {
        return xpdlActivity;
    }

    public AnalyzerParentTask getParent() {
        return parent;
    }

    public void setParent(AnalyzerParentTask parent) {
        this.parent = parent;
    }

    public AnalyzerProcess getProcess() {
        if (this instanceof AnalyzerProcess) {
            return (AnalyzerProcess)this;
        } else if (getParent()!=null) {
        	if (getParent() instanceof AnalyzerProcess) {
        		return (AnalyzerProcess)getParent();
        	}
            return getParent().getProcess();
        } else {
            return getBoundaryParent().getProcess();
        }
    }

    public com.tibco.xpd.xpdl2.Process getXpdlProcess() {
        return getProcess().getXpdlProcess();
    }

    public void addInLink(AnalyzerLink link) {
        inLinks.add(link);
    }

    public void addOutLink(AnalyzerLink link) {
        outLinks.add(link);
    }

    public void removeInLink(AnalyzerLink link) {
        inLinks.remove(link);
    }

    public void removeOutLink(AnalyzerLink link) {
        outLinks.remove(link);
    }

    public JoinSplitType getJoinSplitTypeIn() {
        return joinSplitTypeIn;
    }

    public void setJoinSplitTypeIn(JoinSplitType joinSplitTypeIn) {
        this.joinSplitTypeIn = joinSplitTypeIn;
    }

    public JoinSplitType getJoinSplitTypeOut() {
        return joinSplitTypeOut;
    }

    public void setJoinSplitTypeOut(JoinSplitType joinSplitTypeOut) {
        this.joinSplitTypeOut = joinSplitTypeOut;
    }

    public Analyzer.TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(Analyzer.TaskType taskType) {
        this.taskType = taskType;
    }

    public Analyzer.GatewayType getGatewayType() {
        return gatewayType;
    }

    public List<AnalyzerLink> getInLinks() {
        return inLinks;
    }

    public List<AnalyzerLink> getAllInLinks() {
        return getInLinks();
    }

    public List<AnalyzerLink> getOutLinks() {
        return outLinks;
    }

    public List<AnalyzerLink> getAllOutLinks() {
        List<AnalyzerLink> result = new ArrayList<AnalyzerLink>(outLinks);
        for (AnalyzerTask task: boundaryTasks) {
            result.addAll(task.getOutLinks());
        }
        return result;
    }

    public boolean isCreateInstance() {
        return createInstance;
    }

    public void setCreateInstance(boolean createInstance) {
        this.createInstance = createInstance;
    }

    public List<AnalyzerTask> getBoundaryTasks() {
        return boundaryTasks;
    }

    public List<AnalyzerTask> getSignalUpdateBoundaryTasks() {
        return signalUpdateBoundaryTasks;
    }

    public void addBoundaryTask(AnalyzerTask boundaryTask) {
    	 com.tibco.xpd.xpdl2.Activity eventAct = boundaryTask.getXpdlActivity();
         com.tibco.xpd.xpdl2.Event event = eventAct.getEvent();
         TriggerType triggerType = ((IntermediateEvent)event).getTrigger();
         if (triggerType.getValue()==TriggerType.SIGNAL && EventObjectUtil.isNonCancellingEvent(eventAct)) {
             this.signalUpdateBoundaryTasks.add(boundaryTask);
         } else {
        	 this.boundaryTasks.add(boundaryTask);
         }
    }

    public boolean isBoundaryHost() {
        return getBoundaryTasks().size()>0;
    }

    public boolean isBoundaryTask() {
        return (boundaryParent!=null);
    }

    public AnalyzerTask getBoundaryParent() {
        return boundaryParent;
    }

    public void setBoundaryParent(AnalyzerTask boundaryParent) {
        this.boundaryParent = boundaryParent;
    }

    public Set<AnalyzerTask> getPredecessors() {
        return predecessors;
    }

    public void addPredecessors(Set<AnalyzerTask> predecessors) {
        this.predecessors.addAll(predecessors);
    }

    public boolean isUncontrolledMerge() {
        return uncontrolledMerge;
    }

    public void setUncontrolledMerge(boolean uncontrolledMerge) {
        this.uncontrolledMerge = uncontrolledMerge;
    }

    public Integer getWaitFor() {
        return waitFor;
    }

    public void setWaitFor(Integer waitFor) {
        this.waitFor = waitFor;
    }

    public Integer getMaxTrue() {
        return maxTrue;
    }

    public void setMaxTrue(Integer maxTrue) {
        this.maxTrue = maxTrue;
    }

    public String toString() {
        return getName()==null?getXpdlActivity().getId():getName();
    }

    public Object getBpelReference() {
        return bpelReference;
    }

    public void setBpelReference(Object bpelReference) {
        this.bpelReference = bpelReference;
    }

    public AnalyzerTask getAssociation() {
        return association;
    }

    public void setAssociation(AnalyzerTask association) {
        this.association = association;
    }
    
    public boolean isMigrationAllowed() {
		return migrationAllowed;
	}

	public void setMigrationAllowed(boolean migrationAllowed) {
		this.migrationAllowed = migrationAllowed;
	}

	public Token getJoinStarterToken() {
		return joinStarterToken;
	}

	public void setJoinStarterToken(Token joinStarterToken) {
		this.joinStarterToken = joinStarterToken;
	}

	public void setEventHandlerBody(AnalyzerParentTask eventHandlerBody) {
		this.eventHandlerBody = eventHandlerBody;
	}

	public AnalyzerParentTask getEventHandlerBody() {
		return eventHandlerBody;
	}
    
	public boolean isEarlyExit() {
		return earlyExit;
	}
	
	public void setEarlyExit(boolean earlyExit) {
		this.earlyExit = earlyExit;
	}

	public boolean isAdhocActivity() {
		return adhocActivity;
	}
	
	public boolean isEventSubProcess() {
		return false;
	}
	
}
