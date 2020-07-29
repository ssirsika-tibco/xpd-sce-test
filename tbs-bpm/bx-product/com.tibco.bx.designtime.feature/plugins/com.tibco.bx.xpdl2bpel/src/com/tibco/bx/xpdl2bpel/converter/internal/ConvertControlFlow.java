package com.tibco.bx.xpdl2bpel.converter.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Links;
import org.eclipse.bpel.model.Source;
import org.eclipse.bpel.model.Target;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.common.util.EList;

import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.analyzer.Analyzer;
import com.tibco.bx.xpdl2bpel.analyzer.AnalyzerLink;
import com.tibco.bx.xpdl2bpel.analyzer.AnalyzerParentTask;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;


  //todo consider FCTE handlers

  //todo if new flow created, do we need to set its merge type?

/**
 * One instance per Process.
 */
public class ConvertControlFlow {

    // When in crossing links are used, the flow has no starting point since it is a cycle and if a task had no incoming link it would not be part of the cycle group
    // The converter needs to add startpoint and adjust links. Need fan-in if more than one entry point.
    public static void doFanIn(ConverterContext context, AnalyzerParentTask flow) {
        Flow bpelFlow = (Flow)flow.getBpelReference();
        EList<Activity> children = bpelFlow.getActivities();
        Activity newStart = BPELFactory.eINSTANCE.createEmpty();
        context.setActivityName(null, newStart, "startingPoint"); //$NON-NLS-1$
        // add new starting point to flow
        children.add(newStart);
        List<AnalyzerLink> inLinks = flow.getInCrossingLinks();
        List<Link> bpelLinks = new ArrayList<Link>();
        for (AnalyzerLink link: inLinks) {
            bpelLinks.add((Link)link.getBpelReference());
        }
        List<Activity> targets = BPELUtils.getTargetActivities(bpelLinks);
        if (targets.size()==1) {
            // single entry point, fan in not required
            for (Link bpelLink: bpelLinks) {
                Activity oldTarget = BPELUtils.getTargetActivity(bpelLink);
                // redirect the in link to the new flow
                replaceLinkTarget(context, bpelLink, bpelFlow);
                // add link from start event in new flow to the old target, unless such link already exists.
                List<Activity> existingTargets = BPELUtils.getTargetActivities(newStart);
                if (!existingTargets.contains(oldTarget)) {
                    createLink(context, newStart, oldTarget, null, bpelFlow);
                }
            }
        } else {
            // need fan in logic
            Flow parentBpelFlow = (Flow)flow.getParent().getBpelReference();
            EList<Activity> parentChildren = parentBpelFlow.getActivities();
            Variable fanVar = ConvertBoundaryEvents.makeFanVariable(context, false);
            context.addVariable(fanVar);
            
            /**
             * Sid XPD-8199 - Used to have a local variable for fanNumber here,
             * but that meant that with each flow processing started at
             * fanNumber=1 BUT THE fanInSetup activity is added to the parent.
             * So if you have multiple points in the parent flow that jump into
             * different child flows then you would get duplicate activities in
             * the parent :o(
             * 
             * Moved fan number to the ConverterContext to keep track and ensure
             * uniqueness
             */
            for (Activity entryPoint: targets) {
                int fanNumber = context.getNextFlowFanInNumber();
                
                // add link with fan condition from newStart to target
                Condition condition = BPELFactory.eINSTANCE.createCondition();
                /* Sid ACE-4344 all expressions need to be in JavaScript now */
                condition.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
                condition.setBody(fanVar.getName()+"=="+fanNumber); //$NON-NLS-1$ //$NON-NLS-2$
                createLink(context, newStart, entryPoint, condition, bpelFlow);

                // for each link crossing in to this target, insert assign to fan number and redirect to flow
                for (Link link: bpelLinks) {
                    if (BPELUtils.getTargetActivity(link).equals(entryPoint)) {
                        Assign setUpActivity = makeFanAssignActivity(fanVar, Integer.toString(fanNumber));
                        setUpActivity.setName(context.generateActivityName("fanInSetup"+fanNumber, null, null)); //$NON-NLS-1$
                        parentChildren.add(setUpActivity);
                        createLink(context, setUpActivity, bpelFlow, null, parentBpelFlow);
                        replaceLinkTarget(context, link, setUpActivity);
                    }
                }
                
            }
        }
    }
    // only pick and cycle have multiple exit points
    // If pick or M-out-of-N, leave as crossing out link.
    // if only one link, can point to new exit point and add new link from flow to old target
    // if multiple links, use fan out.
    public static void doFanOut(ConverterContext context, AnalyzerParentTask parentFlow, AnalyzerParentTask flow) {
        // leave pick alone, link can exit out across pick boundary
        if (flow.getTaskType().equals(Analyzer.TaskType.Pick)) return;

        Flow bpelFlow = (Flow)flow.getBpelReference();
        Flow bpelParentFlow = (Flow)parentFlow.getBpelReference();
        EList<Activity> children = bpelFlow.getActivities();

        List<AnalyzerLink> outLinks = flow.getOutCrossingLinks();
        List<Link> bpelLinks = new ArrayList<Link>();
        for (AnalyzerLink link: outLinks) {
            bpelLinks.add((Link)link.getBpelReference());
        }

        if (bpelLinks.size()==1) {
            // if only one link, retarget to new exit point and add new link from flow to old target
            Activity newEnd = BPELFactory.eINSTANCE.createEmpty();
            context.setActivityName(null, newEnd, "endingPoint"); //$NON-NLS-1$
            // add new ending point to flow
            children.add(newEnd);
            Activity oldTarget = BPELUtils.getTargetActivity(bpelLinks.get(0));
            // adjust link parent
            adjustLinkParent(bpelLinks.get(0), bpelParentFlow, bpelFlow);
            // redirect the out link to the new end point
            replaceLinkTarget(context, bpelLinks.get(0), newEnd);
            // add link from from to the old target
            createLink(context, bpelFlow, oldTarget, null, bpelParentFlow);
        } else {
            // use fan out technique. each link retargeted to assign activity and new link add from flow to old target with matching condition
            List<Activity> nextPoints = BPELUtils.getTargetActivities(bpelLinks);
            Flow parentBpelFlow = (Flow)parentFlow.getBpelReference();
            Variable fanVar = ConvertBoundaryEvents.makeFanVariable(context, false);
            context.addVariable(fanVar);

            /**
             * Sid XPD-8199 - Used to have a local variable for fanNumber here,
             * but that meant that with each flow's processing started at
             * fanNumber=1 which means that other flow's will have 
             * 
             * Moved fan number to the ConverterContext to keep track and ensure uniqueness
             */
            for (Activity nextPoint: nextPoints) {
                int fanNumber = context.getNextFlowFanOutNumber();

                // add link with fan condition from flow to next point
                Condition condition = BPELFactory.eINSTANCE.createCondition();
                /* Sid ACE-4344 all expressions need to be in JavaScript now */
                condition.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
                condition.setBody(fanVar.getName()+"=="+fanNumber); //$NON-NLS-1$ //$NON-NLS-2$
                createLink(context, bpelFlow, nextPoint, condition, parentBpelFlow);
                // build setup activity to set var for controlling which exit is taken
                Assign setUpActivity = makeFanAssignActivity(fanVar, Integer.toString(fanNumber));
                setUpActivity.setName(context.generateActivityName("fanOutSetup"+fanNumber, null, null)); //$NON-NLS-1$
                children.add(setUpActivity);
                // for each link crossing in to this target, insert assign to fan number
                for (Link link: bpelLinks) {
                    if (BPELUtils.getTargetActivity(link).equals(nextPoint)) {
                        adjustLinkParent(link, bpelParentFlow, bpelFlow);
                        replaceLinkTarget(context, link, setUpActivity);
                    }
                }
            }
        }
    }

    // moves link from oldParent to newParent
    private static void adjustLinkParent(Link link, Flow oldParent, Flow newParent) {
    	Links parentLinks = oldParent.getLinks();
        if (parentLinks.getChildren().remove(link)) {
        	oldParent.setLinks(parentLinks);
        	Links links = newParent.getLinks();
        	links.getChildren().add(link);
        	newParent.setLinks(links);
        }
    }
    
    public static Assign makeFanAssignActivity(Variable var, String value) {
        Assign assignAct = BPELFactory.eINSTANCE.createAssign();
        Copy  assignCopy = BPELFactory.eINSTANCE.createCopy ();
        From  from = BPELFactory.eINSTANCE.createFrom ();
        Expression fromExpression = BPELFactory.eINSTANCE.createExpression();
        fromExpression.setBody(value);
        from.setExpression(fromExpression);
        To  to = BPELFactory.eINSTANCE.createTo ();
        to.setVariable(var);
        assignCopy.setTo(to);
        assignCopy.setFrom(from);
        assignAct.getCopy().add(assignCopy);
        return assignAct;
    }

    // changes the link target activity from the old target to new target
    // the Targets object of both the new and old target are updated accordingly
    // link is also renamed
    public static void replaceLinkTarget(ConverterContext context, Link link, Activity newTargetActivity) {
        Activity oldTarget = BPELUtils.getTargetActivity(link);
        // remove this link from oldTarget
        EList<Target> targets = oldTarget.getTargets().getChildren();
        for (Target target: targets) {
            if (target.getLink().equals(link)) {
            	targets.remove(target);
                link.getTargets().remove(target);
                break;
            }
        }
        // add this link to newTargetActivity
        Target newTarget = BPELFactory.eINSTANCE.createTarget ();
        newTarget.setActivity (newTargetActivity);
        link.getTargets().add(newTarget);
        String oldLinkName = link.getName();
        if (oldLinkName.indexOf(ConverterContext.LINK_CONNECTOR_WORD)>0) {
            // rename link if it was a generated name
            String uniqueLinkName = context.getUniqueLinkName (null, BPELUtils.getSourceActivity(link), newTargetActivity);
            link.setName(uniqueLinkName);
        }
    }

    // changes the link source activity from the old source to new source
    // the Sources object of both the new and old source are updated accordingly
    // link is also renamed
    public static void replaceLinkSource(ConverterContext context, Link link, Activity newSourceActivity) {
        Activity oldSource = BPELUtils.getSourceActivity(link);
        // remove this link from oldSource
        Source xSource = null;
        EList<Source> sources = oldSource.getSources().getChildren();
        for (Source source: sources) {
            if (source.getLink().equals(link)) {
            	sources.remove(source);
                link.getSources().remove(source);
                xSource = source;
                break;
            }
        }

        // add this link to newSourceActivity
        Source newSource = BPELFactory.eINSTANCE.createSource ();
        newSource.setActivity (newSourceActivity);
        if (xSource!=null) {
            newSource.setTransitionCondition(xSource.getTransitionCondition());
        }
        link.getSources().add(newSource);
        String oldLinkName = link.getName();
        if (oldLinkName.indexOf(ConverterContext.LINK_CONNECTOR_WORD)>0) {
            // rename link if it was a generated name
            String uniqueLinkName = context.getUniqueLinkName (null, newSourceActivity,  BPELUtils.getTargetActivity(link));
            link.setName(uniqueLinkName);
        }
    }
    
    public static void createLink(ConverterContext context, Activity sourceActivity, Activity targetActivity, Condition condition, Flow flow) {
        Link newLink = BPELFactory.eINSTANCE.createLink();
        String  uniqueLinkName = context.getUniqueLinkName (null, sourceActivity, targetActivity);
        newLink.setName(uniqueLinkName);
        Links links = flow.getLinks();
        if (links==null) {
            links = BPELFactory.eINSTANCE.createLinks();
        }
        links.getChildren().add(newLink);
        flow.setLinks(links);
        Target newTarget = BPELFactory.eINSTANCE.createTarget ();
        newTarget.setActivity(targetActivity);
        Source newSource = BPELFactory.eINSTANCE.createSource ();
        newSource.setTransitionCondition(condition);
        newSource.setActivity(sourceActivity);
        newLink.getSources().add(newSource);
        newLink.getTargets().add(newTarget);
    }

}
