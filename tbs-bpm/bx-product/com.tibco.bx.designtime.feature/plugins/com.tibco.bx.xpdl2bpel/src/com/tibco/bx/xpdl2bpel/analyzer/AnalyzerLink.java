package com.tibco.bx.xpdl2bpel.analyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.bx.xpdl2bpel.Tracing;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Split;
import com.tibco.xpd.xpdl2.TransitionRef;
import com.tibco.xpd.xpdl2.TransitionRestriction;

/**
 * Created by IntelliJ IDEA.
* User: goldberg
* Date: Sep 10, 2008
* Time: 4:58:32 PM
* To change this template use File | Settings | File Templates.
*/
public class AnalyzerLink implements Comparable<AnalyzerLink> {
    private com.tibco.xpd.xpdl2.Transition xpdlTransition;
    private AnalyzerTask sourceTask;
    private AnalyzerTask targetTask;
    private AnalyzerParentTask boundaryCrossedOnExit;  // link exits out across a parent task boundary
    private AnalyzerParentTask boundaryCrossedOnEnter;  // link enters in across a parent task boundary
    Map<AnalyzerTask, Set<Analyzer.Token>> tokens;  // keep tokens by token originator
    private boolean loopBack = false;
    private Object bpelReference = null;


    public AnalyzerLink(com.tibco.xpd.xpdl2.Transition xpdlTransition, AnalyzerTask sourceTask, AnalyzerTask targetTask) {
        this.xpdlTransition = xpdlTransition;
        this.sourceTask = sourceTask;
        this.targetTask = targetTask;
    }

    public void setSourceTask(AnalyzerTask sourceTask) {
        this.sourceTask = sourceTask;
    }

    public AnalyzerTask getSourceTask() {
        if (boundaryCrossedOnExit!=null) {
            return boundaryCrossedOnExit;
        } else {
            return sourceTask;
        }
    }

    public AnalyzerTask getRealSourceTask() {
        return sourceTask;
    }

    public String getName() {
        if (xpdlTransition!=null && xpdlTransition.getName()!=null && xpdlTransition.getName().length()>0) {
            return xpdlTransition.getName();
        } else {
            return sourceTask+"-to-"+targetTask;
        }
    }

    public com.tibco.xpd.xpdl2.Transition getXpdlTransition() {
        return xpdlTransition;
    }

    public void setTargetTask(AnalyzerTask targetTask) {
        this.targetTask = targetTask;
    }

    public AnalyzerTask getRealTargetTask() {
        return targetTask;
    }

    public AnalyzerTask getTargetTask() {
        if (boundaryCrossedOnEnter!=null) {
            return boundaryCrossedOnEnter;
        } else {
            return targetTask;
        }
    }

    public AnalyzerParentTask getBoundaryCrossedOnExit() {
        return boundaryCrossedOnExit;
    }

    public void setBoundaryCrossedOnExit(AnalyzerParentTask boundaryCrossedOnExit) {
        if (this.boundaryCrossedOnExit!=null) {
        	// throw new IllegalStateException("exit boundary already crossed for "+getName());
            if (Tracing.ENABLED) Tracing.trace("changing exit boundary crossed for "+getName()); //$NON-NLS-1$
        }
        this.boundaryCrossedOnExit = boundaryCrossedOnExit;
    }

    public void resetBoundaryCrossedOnExit(AnalyzerParentTask parent) {
    	this.boundaryCrossedOnExit = parent;
    }
    
    public AnalyzerParentTask getBoundaryCrossedOnEnter() {
        return boundaryCrossedOnEnter;
    }

    public void resetBoundaryCrossedOnEnter(AnalyzerParentTask parent) {
    	this.boundaryCrossedOnEnter = parent;
    }
    
    public void setBoundaryCrossedOnEnter(AnalyzerParentTask boundaryCrossedOnEnter) {
        if (this.boundaryCrossedOnEnter!=null) {
            if (Tracing.ENABLED) Tracing.trace("enter boundary already crossed for "+toString()+ ", old boundaryTask= "+this.boundaryCrossedOnEnter +", new boundaryTask= "+boundaryCrossedOnEnter); //$NON-NLS-1$
            throw new IllegalStateException("enter boundary already crossed for "+toString());
        }
        this.boundaryCrossedOnEnter = boundaryCrossedOnEnter;
    }

    public Map<AnalyzerTask, Set<Analyzer.Token>> getTokens() {
        return tokens;
    }

    public void addToken(Analyzer.Token token) {
        if (tokens==null) {
            tokens = new HashMap<AnalyzerTask, Set<Analyzer.Token>>();
        }
        Set<Analyzer.Token> currentTokens = tokens.get(token.getOriginator());
        if (currentTokens==null) {
            // first token for this originator
            Set<Analyzer.Token> newTokens = new HashSet<Analyzer.Token>();
            newTokens.add(token);
            tokens.put(token.getOriginator(), newTokens);
        } else {
            // add to existing tokens for this originator
            currentTokens.add(token);
        }
    }

    public void setTokens(Map<AnalyzerTask, Set<Analyzer.Token>> tokens) {
    	if (Tracing.ENABLED) {
    		Tracing.trace("set link tokens: "+getName()+ ", tokens= "+tokens);
    	}
        this.tokens = tokens;
    }

    public boolean isLoopBack() {
        return loopBack;
    }

    public void setLoopBack(boolean loopBack) {
        this.loopBack = loopBack;
    }

    public Object getBpelReference() {
        return bpelReference;
    }

    public void setBpelReference(Object bpelReference) {
        this.bpelReference = bpelReference;
    }

    public String toString() {
        return "from ["+getRealSourceTask()+"] to ["+getRealTargetTask()+"]";
    }
    
    @Override
    public int hashCode() {
    	Object source = this.getRealSourceTask();
    	if (this.getRealSourceTask().getXpdlActivity() != null) {
    		source = this.getRealSourceTask().getXpdlActivity();
    	}
    	Object target = this.getRealTargetTask();
    	if (this.getRealTargetTask().getXpdlActivity() != null) {
    		target = this.getRealTargetTask().getXpdlActivity();
    	}    	
    	return 31 * source.hashCode() + target.hashCode();
    }

    @Override
    public boolean equals(Object other) {
    	if (!(other instanceof AnalyzerLink))
            return false;
    	AnalyzerLink otherLink = (AnalyzerLink)other;
    	Object source = this.getRealSourceTask();
    	if (this.getRealSourceTask().getXpdlActivity() != null) {
    		source = this.getRealSourceTask().getXpdlActivity();
    	}
    	Object target = this.getRealTargetTask();
    	if (this.getRealTargetTask().getXpdlActivity() != null) {
    		target = this.getRealTargetTask().getXpdlActivity();
    	}   
    	Object otherSource = otherLink.getRealSourceTask();
    	if (otherLink.getRealSourceTask().getXpdlActivity() != null) {
    		otherSource = otherLink.getRealSourceTask().getXpdlActivity();
    	}
    	Object otherTarget = otherLink.getRealTargetTask();
    	if (otherLink.getRealTargetTask().getXpdlActivity() != null) {
    		otherTarget = otherLink.getRealTargetTask().getXpdlActivity();
    	}    
        return source.equals(otherSource) && target.equals(otherTarget);
    }
    
	@Override
	public int compareTo(AnalyzerLink otherLink) {
		Activity thisActivity = this.getRealSourceTask().getXpdlActivity();
		Activity otherActivity = otherLink.getRealSourceTask().getXpdlActivity();

        /**
         * Sid XPD-8154. The comparator used to be able to cause the
         * exception...
         * 
         * <pre>
         *  java.lang.IllegalArgumentException: Comparison method violates its general contract!
         *  at java.util.ComparableTimSort.mergeHi(Unknown Source)
         *  at java.util.ComparableTimSort.mergeAt(Unknown Source)
         *  at java.util.ComparableTimSort.mergeCollapse(Unknown Source)
         * at java.util.ComparableTimSort.sort(Unknown Source)
         * 
         * </pre>
         * 
         * The exception is caused when a sort reveals two elements in a list
         * who's comparator methods return inconsistently (for instance elementA
         * says it is less-than elementB but elementB says it is less than
         * elemetnA). The java sort routines then throw the above exception.
         * 
         * The xpdl2bpel ConvertProcess class sorts the list of AnalyzerLink
         * elements (flow analyzer links representing flows between xpdl
         * activities / additional runtime-activity-constructs). The comparator
         * method in AnalyzerLink uses the original XPDL activity that is
         * associated with the link to rank one link over another.
         * 
         * However not all AnalyzerLink elements link back to an xpdlActivity
         * (some AnalyzerLinks are between added runtime-only activities that
         * have been created to wrap up blocks of xpdl activities). The
         * comparator simply says if this.xpdlactivity == null return -1 this
         * means that when comparing linkA with linkB and both have no
         * xpdlActivity associated it will return -1 (less than) for both linkA
         * compared to linkB and linkB compared to linkA and you get the
         * exception.
         * 
         * Therefore we must not simply return -1 every time this.xpdActivity is
         * null. Instead we should find some other criteria on which they can
         * also be sorted. Having spoken to Steve Goldberg it seems that
         * link.realSourceTask will always be set so we might as well use that
         * in the absence of anything else. So we will use this name.
         * 
         * AS FAR AS I CAN TELL the actual sort order is not significant. When
         * we sort the list (in ConvertProcess.convertFlowToBpel() and then pass
         * the sorted list to convertAnalyzerLinksToBPELLinks() and that simply
         * iterates thru the links and DOESN'T SEEM to require that they are
         * grouped together in order to work. So I think the sorting is just
         * trying to ensure that the set of links output is in a consistent
         * order(?)
         */
        if (thisActivity == null && otherActivity == null) {
            AnalyzerTask taskA = this.getRealSourceTask();
            AnalyzerTask taskB = otherLink.getRealSourceTask();
            return taskA.getName().compareTo(taskB.getName());
        }	
		
		if (thisActivity == null) return -1;
		if (thisActivity != otherActivity) {
			if (otherActivity == null) return 1;
			return thisActivity.getId().compareTo(otherActivity.getId());
		}
		
		List<TransitionRestriction> transitionRestrictions = thisActivity.getTransitionRestrictions();
		for (TransitionRestriction transitionRestriction : transitionRestrictions) {
			Split split = transitionRestriction.getSplit();
			if (split != null) {
				EList<TransitionRef> transitionRefs = split.getTransitionRefs();
				for (TransitionRef transitionRef : transitionRefs) {
					if (this.getXpdlTransition().getId().equals(transitionRef.getId())) {
						return -1;
					} else if (otherLink.getXpdlTransition().getId().equals(transitionRef.getId())) {
						return 1;
					}
				}
			}
		}
		
		return 0;
	}
}
