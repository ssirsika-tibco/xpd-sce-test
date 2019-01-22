package com.tibco.bx.xpdl2bpel.analyzer;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

/**
 * Created by IntelliJ IDEA.
* User: goldberg
* Date: Sep 10, 2008
* Time: 4:58:02 PM
* To change this template use File | Settings | File Templates.
*/
public class AnalyzerParentTask extends AnalyzerTask {

    private List<AnalyzerTask> children = new ArrayList<AnalyzerTask>();
    private List<AnalyzerLink> links = new ArrayList<AnalyzerLink>();
    private List<AnalyzerLink> outCrossingLinks = new ArrayList<AnalyzerLink>();  // these links go from a child to a task outside the parent
    private List<AnalyzerLink> inCrossingLinks = new ArrayList<AnalyzerLink>();  // these links go from a task outside the parent to a child
    private boolean done = false;   //set to true after analysis is complete
    private boolean containsCycle = false;  // set to true if this parent gets sequenceFlow=yes extension
    private List<AnalyzerTask> eventHandlers = new ArrayList<AnalyzerTask>();
    private boolean eventSubProcess = false;
    private AnalyzerTask startEvent = null;

    // the following may be reset on each pass of the analyzer
    private Set<AnalyzerLink> loopbackLinks = new LinkedHashSet<AnalyzerLink>();
    private Set<AnalyzerTask> controlledMerges = new LinkedHashSet<AnalyzerTask>();
    private Set<AnalyzerTask> uncontrolledMerges = new LinkedHashSet<AnalyzerTask>();
    private List<AnalyzerIssue> issues = new ArrayList<AnalyzerIssue>();

    public AnalyzerParentTask(com.tibco.xpd.xpdl2.Activity xpdlActivity) {
        super(xpdlActivity);
        eventSubProcess = AnalyzerUtil.isEventSubProcess(xpdlActivity);
    }

    public AnalyzerParentTask(int genNumber) {
        super(genNumber);
    }

    // reset analysis data, but not configuration data
    public void reset() {
    	loopbackLinks.clear();
        controlledMerges.clear();
        uncontrolledMerges.clear();
        issues.clear();
        for (AnalyzerTask child: getChildren()) {
            child.resetPredecessors();
        }
    }

    public void addChild(AnalyzerTask task) {
        children.add(task);
        task.setParent(this);
    }

    public boolean removeChild(AnalyzerTask task) {
        boolean ret = children.remove(task);
        if (ret) {
            task.setParent(null);
        }
        return ret;
    }

    public void addLink(AnalyzerLink link) {
        links.add(link);
        link.getRealSourceTask().addOutLink(link);
        link.getRealTargetTask().addInLink(link);
    }

    public void removeLink(AnalyzerLink link) {
        links.remove(link);
        link.getRealTargetTask().removeInLink(link);
        link.getRealSourceTask().removeOutLink(link);
    }

    public void redirectLinkTarget(AnalyzerLink link, AnalyzerTask newTarget) {
        link.getRealTargetTask().removeInLink(link);
        link.setTargetTask(newTarget);
        newTarget.addInLink(link);
    }

    public void redirectLinkSource(AnalyzerLink link, AnalyzerTask newSource) {
        link.getRealSourceTask().removeOutLink(link);
        link.setSourceTask(newSource);
        newSource.addOutLink(link);
    }

    public void redirectLinkOwner(AnalyzerLink link, AnalyzerParentTask newOwner) {
        links.remove(link);
        newOwner.links.add(link);
    }

    public List<AnalyzerTask> getChildren() {
        return children;
    }

    public List<AnalyzerLink> getLinks() {
        return links;
    }

    public AnalyzerTask findTask(String id) {
        for (AnalyzerTask task: children) {
            if (id.equals(task.getXpdlActivity().getId())) {
                return task;
            }
        }
        return null;
    }

    public void addOutCrossingLink(AnalyzerLink link) {
        outCrossingLinks.add(link);
    }

    public List<AnalyzerLink> getOutCrossingLinks() {
        return outCrossingLinks;
    }

    public List<AnalyzerLink> getAllOutLinks() {
        List<AnalyzerLink> result = new ArrayList<AnalyzerLink>(super.getAllOutLinks());
        result.addAll(outCrossingLinks);
        return result;
    }

    public List<AnalyzerLink> getAllInLinks() {
        List<AnalyzerLink> result = new ArrayList<AnalyzerLink>(super.getAllInLinks());
        result.addAll(inCrossingLinks);
        return result;
    }

    public List<AnalyzerLink> getInCrossingLinks() {
        return inCrossingLinks;
    }

    public void setInCrossingLinks(List<AnalyzerLink> inCrossingLinks) {
        this.inCrossingLinks = inCrossingLinks;
    }

    public void addInCrossingLink(AnalyzerLink link) {
        inCrossingLinks.add(link);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Set<AnalyzerLink> getLoopbackLinks() {
        return loopbackLinks;
    }

    public void addLoopbackLink(AnalyzerLink loopbackLink) {
    	loopbackLinks.add(loopbackLink);
    }

    public Set<AnalyzerTask> getControlledMerges() {
        return controlledMerges;
    }

    public void addControlledMerge(AnalyzerTask controlledMerge) {
        this.controlledMerges.add(controlledMerge);
    }
    
    public Set<AnalyzerTask> getUncontrolledMerges() {
        return uncontrolledMerges;
    }

    public void addUncontrolledMerge(AnalyzerTask uncontrolledMerge) {
        this.uncontrolledMerges.add(uncontrolledMerge);
    }

    public boolean isContainsCycle() {
        return containsCycle;
    }

    public void setContainsCycle(boolean containsCycle) {
        this.containsCycle = containsCycle;
    }
    
    public void addEventHandler(AnalyzerTask eventHandler) {
    	this.eventHandlers.add(eventHandler);
    	eventHandler.setParent(this);
    }

    public List<AnalyzerTask> getEventHandlers() {
    	return eventHandlers;
    }
    public List<AnalyzerIssue> getIssues() {
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
    
	public boolean isEventSubProcess() {
		return eventSubProcess;
	}

	public AnalyzerTask getStartEvent() {
		return startEvent;
	}

	public void setStartEvent(AnalyzerTask startEvent) {
		this.startEvent = startEvent;
	}
	
}
