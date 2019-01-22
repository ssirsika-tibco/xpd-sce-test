/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.inlineSubProcess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.graphics.Rectangle;

import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses.InlineSubProcessProblem;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses.InlineSubProcessProblemId;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses.ProcessCallHierarchy;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2ProcessDiagramUtils;
import com.tibco.xpd.processwidget.adapters.ProcessPasteCommand;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author aallway
 * 
 */
public class AutoInlineSubProcesses {

    private static final int EXPAND_CONTAINER_MARGIN = 40;

    public static final String OPTIMIZED_UNIQUEID_SUFFIX = "_Opt"; //$NON-NLS-1$ 

    private Xpdl2WorkingCopyImpl workingCopy = null;

    public AutoInlineSubProcesses(Xpdl2WorkingCopyImpl workingCopy) {
        super();
        this.workingCopy = workingCopy;
    }

    /**
     * Inline all calls to sub-processes in the given package flagged as
     * "inline".
     * <p>
     * If successful, the sub-processes themselves are deleted.
     * <p>
     * Note that the given package MUST first pass all tests defined by
     * {@link AnalyseInlineSubProcesses} before any conversion is performed.
     * 
     * @param editingDomain
     * @param copyPackage
     * @param idMap
     *            Map of original object's id to new object.
     * 
     * @return List of problems which caused inline not to be performed on given
     *         package (empty list means that either no inlining was performed
     *         OR everything was ok).
     */
    public List<InlineSubProcessProblem> autoInlinePackage(
            EditingDomain editingDomain, Package originalPackage,
            Package copyPackage, Map<String, EObject> idMap) {

        try {
            AnalyseInlineSubProcesses inlineSubProcAnalysis =
                    new AnalyseInlineSubProcesses(workingCopy, copyPackage);

            List<InlineSubProcessProblem> problems =
                    inlineSubProcAnalysis.analysePackage(copyPackage);
            if (problems.size() > 0) {
                // Package does not pass the tests. Cannot proceed.
                return problems;
            }

            // Get the process call hierarchy.
            List<ProcessCallHierarchy> processCallHierarchy =
                    inlineSubProcAnalysis.getLastAnalysedProcessHierarchy();

            // The calls to inlined sub-processes need only be inlined once.
            // So we will keep a set of processes that we have already
            // inline sub-process tasks for).
            Set<Process> processTasksAlreadyInlined = new HashSet<Process>();

            // We have a sub-process call hierarchy. As long as we recurse
            // down to the last item on each branch and perform the inlining on
            // the last process on branch and work our way back upwards we
            // should be fine.
            for (ProcessCallHierarchy ph : processCallHierarchy) {
                recursiveInlineSubProcesses(editingDomain,
                        copyPackage,
                        ph,
                        processTasksAlreadyInlined,
                        inlineSubProcAnalysis,
                        originalPackage,
                        idMap);
            }

            // Now we can delete all the inline sub-processes (that belong
            // to this package). Their content has been moved up to calling
            // processes so they aren't needed anymore.
            EList processes = copyPackage.getProcesses();
            for (ProcessCallHierarchy ph : processCallHierarchy) {
                // Don't need to recurs because all processes appear at top
                // level.
                if (ph.isInline) {
                    if (ph.process.getPackage() == copyPackage) {
                        removeInlineSubProc(ph.process);
                    }
                }
            }

        } catch (Exception e) {
            List<InlineSubProcessProblem> probs =
                    new ArrayList<InlineSubProcessProblem>();

            String message =
                    Messages.AutoInlineSubProcesses_ExceptionEncountered_label
                            + e.getLocalizedMessage();
            probs.add(new InlineSubProcessProblem(
                    InlineSubProcessProblemId.EXCEPTION_DURING_INLINE, null,
                    null, message, message, true, null));

            return probs;

        }

        return Collections.EMPTY_LIST;
    }

    /**
     * Delete the sub-process and any ancillary parts stored outside of process
     * in the model.
     * 
     * @param subProcess
     */
    private void removeInlineSubProc(Process subProcess) {
        Package pkg = subProcess.getPackage();

        // Remove associations, message flows, artifacts etc.
        Collection<Association> assocs =
                Xpdl2ModelUtil.getAllAssociationsInProc(subProcess);
        if (assocs != null && assocs.size() > 0) {
            pkg.getAssociations().removeAll(assocs);
        }

        Collection<MessageFlow> msgFlows =
                Xpdl2ModelUtil.getAllMessageFlowsInProc(subProcess);
        if (msgFlows != null && msgFlows.size() > 0) {
            pkg.getMessageFlows().removeAll(msgFlows);
        }

        Collection<Artifact> artifacts =
                Xpdl2ModelUtil.getAllArtifactsInProcess(subProcess);
        if (artifacts != null && artifacts.size() > 0) {
            pkg.getArtifacts().removeAll(artifacts);
        }

        // Remove pools for the process first
        Collection<Pool> procPools = Xpdl2ModelUtil.getProcessPools(subProcess);
        if (procPools != null && procPools.size() > 0) {
            pkg.getPools().removeAll(procPools);
        }

        // Finally, remove the process itself.
        pkg.getProcesses().remove(subProcess);

        return;
    }

    /**
     * Recursively inline sub-processes in the call tree (starting at the lowest
     * process and working our way back up.
     * 
     * @param editingDomain
     * @param inlineTargetPkg
     *            Original package we are inlining calls for
     * @param ph
     *            The process call hierarchy (tree node) we are working on.
     * @param processTasksAlreadyInlined
     *            List of processes we have already performed inline on.
     * @param inlineSubProcAnalysis
     * @return void
     */
    private void recursiveInlineSubProcesses(EditingDomain editingDomain,
            Package inlineTargetPkg, ProcessCallHierarchy ph,
            Set<Process> processTasksAlreadyInlined,
            AnalyseInlineSubProcesses inlineSubProcAnalysis,
            Package originalPkg, Map<String, EObject> idMap) {

        //
        // If we have already performed an inline of sub-process calls on this
        // process then we have nothing to do.
        if (processTasksAlreadyInlined.contains(ph.process)) {
            return;
        }

        //
        // Go thru all of the processes called from this process an inline
        // any calls to them.
        // As long as we recurse down to
        // the last item on each branch and perform the inlining on the last
        // process on branch and work our way back upwards we should be fine.
        if (ph.calledProcesses.size() > 0) {
            for (ProcessCallHierarchy subph : ph.calledProcesses) {
                recursiveInlineSubProcesses(editingDomain,
                        inlineTargetPkg,
                        subph,
                        processTasksAlreadyInlined,
                        inlineSubProcAnalysis,
                        originalPkg,
                        idMap);
            }

            //
            // Now we have dealt with everything underneath us (i.e. any inline
            // sub-process we call will have had any tasks from sub-processes IT
            // calls inlined already).
            // We can now inline the processes that our process calls.
            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(ph.process);

            // Take a separate list of inline sub-process call tasks because we
            // will be adding sub-proc objects to the original!
            List<Activity> inlineSubProcTasks = new ArrayList<Activity>();
            for (Activity act : activities) {
                // Get sub-process from indi-subproc task activity (if it is
                // one)
                EObject procOrIfc = getSubProcessFromTask(act, inlineTargetPkg);
                if (procOrIfc instanceof Process) {
                    Process subProcess = (Process) procOrIfc;
                    if (AnalyseInlineSubProcesses
                            .isInlineSubProcess(subProcess)) {
                        inlineSubProcTasks.add(act);
                    }
                }
            }

            for (Activity act : inlineSubProcTasks) {
                EObject procOrIfc = getSubProcessFromTask(act, inlineTargetPkg);
                if (procOrIfc instanceof Process) {
                    inlineSubProcessContent(editingDomain,
                            inlineTargetPkg,
                            ph.process,
                            act,
                            (Process) procOrIfc,
                            inlineSubProcAnalysis,
                            originalPkg,
                            idMap);
                }
            }
        }

        // This process has now been inlined - don't need to do it ever again.
        processTasksAlreadyInlined.add(ph.process);

        return;
    }

    /**
     * Take the content of the given process and inline them in place of the
     * given sub-process task.
     * <p>
     * Note that we won't have got this far if the analysis failed and that will
     * have made sure that we are in a known state. So for instance, we know
     * that the sub-process has only one start activity, we know that the
     * sub-process task has only one in and one out flow and so on...
     * 
     * 
     * @param editingDomain
     * @param inlineTargetPkg
     * @param callingProcess
     * @param subProcessTask
     * @param subProcess
     * @param inlineSubProcAnalysis
     * @param idMap
     * @param originalPkg
     */
    private void inlineSubProcessContent(EditingDomain editingDomain,
            Package inlineTargetPkg, Process callingProcess,
            Activity subProcessTask, Process subProcess,
            AnalyseInlineSubProcesses inlineSubProcAnalysis,
            Package originalPkg, Map<String, EObject> idMap) {
        //
        // Lots of little jobs to do. The overall way we will deal with this is
        // to get a list of objects from the sub-process that are to be
        // 'in-lined', add any extras (like participants/datafields etc) that
        // also need to be copied up to calling process then use the standard
        // paste facilities to paste them into calling process. Then all we
        // should have to do is link up to the first/last activity in the
        // objects that have been moved up.
        //
        // Lots of difficult conditions will have been analysed previously so we
        // have a known 'simple' state. For instance we know that the sub-proc
        // to be inlined has only one start activity and the task calling it has
        // only one incoming and one outgoing sequence flow.
        //

        FlowContainer subProcTaskFlowContainer =
                subProcessTask.getFlowContainer();

        // Get a list of all the objects (activities/transitions/artifacts etc)
        // in the subprocess.
        boolean isSubProcFromExtPkg =
                (subProcess.getPackage() != inlineTargetPkg);
        List<EObject> subProcObjects =
                InlineSubProcessUtil.getInlineSubProcObjects(editingDomain,
                        subProcess,
                        inlineSubProcAnalysis,
                        true,
                        isSubProcFromExtPkg,
                        originalPkg,
                        // If sub-proc is from ext pkg to the one we're inlining
                        // then don't pass in idMap because no look-ups of copy
                        // objs with new id's against original pkg are required.
                        (isSubProcFromExtPkg ? null : idMap));

        // Resolve any ExternalPackage references in the list of objects
        // (Xpdl2ProcessDiagramUtils.getAddDiagramObjectsCommand() will not be
        // able to do so because we're not working on a package that has a
        // working copy).
        resolveExternalPackageRefs(editingDomain,
                inlineTargetPkg,
                subProcObjects);

        // Sub-proc objects may be spread over multiple lanes, so have to change
        // their co-ords to absolute.
        Map<Coordinates, Point> oldObjectLocations =
                InlineSubProcessUtil.adjustSubProcObjectsLocations(subProcess,
                        subProcObjects);

        // Add any participants in the sub-process to the list (the 'paste' will
        // automatically re-use calling process participants if there are some
        // with same name).
        subProcObjects.addAll(InlineSubProcessUtil
                .getSubProcParticipants(callingProcess,
                        subProcess,
                        subProcObjects));

        // Add any sub-process data fields (and formal parameters that task does
        // not map to as these will need to be created in the calling proc).
        //
        // This returns a map of SubProcField/Param --> MainProcFieldOrParam
        // where MainProcFieldOrParam is either...
        // - An existing calling process field that was mapped to the given
        // sub-proc param.
        // - A copy of a sub-process field (or unmapped parameter) (in which
        // case it's eContainer=null).
        // - A renamed copy of a sub-process field (or unmapped param) (in which
        // case it's eContainer=null).
        Map<ProcessRelevantData, ProcessRelevantData> fieldMap =
                InlineSubProcessUtil.getSubProcFields(editingDomain,
                        callingProcess,
                        subProcessTask,
                        subProcess,
                        originalPkg,
                        // If sub-proc is from ext pkg to the one we're inlining
                        // then don't pass in idMap because no look-ups of copy
                        // objs with new id's against original pkg are required.
                        (isSubProcFromExtPkg ? null : idMap));

        //
        // Get lane or embedded sub-proc activity for sub-process task.
        // i.e. the destination for the task.
        EObject taskContainer =
                InlineSubProcessUtil.getTaskContainer(subProcessTask);
        Rectangle subProcTaskBnds =
                Xpdl2ModelUtil.getObjectBounds(subProcessTask);
        Point location = new Point(subProcTaskBnds.x, subProcTaskBnds.y);

        //
        // Remove the sub-process task.
        //
        removeSubProcTask(subProcessTask);

        //
        // Make space for objects we are pasting in.
        makeSpaceForInlinedObjects(taskContainer,
                subProcTaskBnds,
                InlineSubProcessUtil.getSubProcObjectsBounds(subProcObjects),
                subProcessTask);

        //
        // REassign the unique id's OUR WAY (by suffixing them with Sub-Proc
        // Call Task Instance id rather than completely new unique id's).
        // This way we can guarantee that id's in optimised package remain
        // consistent over multiple re-optimisations).
        String uniqueIdSuffix = subProcessTask.getId();

        //
        // Paste our list of objects into the calling process
        //
        ProcessPasteCommand pasteObjectsCommand =
                Xpdl2ProcessDiagramUtils
                        .getAddDiagramObjectsCommand(editingDomain,
                                callingProcess,
                                taskContainer,
                                location,
                                subProcObjects,
                                uniqueIdSuffix,
                                false,
                                false);

        if (pasteObjectsCommand == null || !pasteObjectsCommand.canExecute()) {
            throw new RuntimeException(
                    "Unexpected error adding Inline-SubProcess objects (for Task:" //$NON-NLS-1$
                            + subProcessTask.getName() + " in Process:" //$NON-NLS-1$
                            + callingProcess.getName());
        }

        pasteObjectsCommand.execute();

        // Grab the newly create objects from the paste command.
        Collection pastedObjects = pasteObjectsCommand.getPasteObjects();

        //
        // Put the original objects back where they were (because earlier we
        // reset their positions to change from lane-relative to absolute).
        restoreOldObjectLocations(oldObjectLocations);

        // Reconnect the removed sub-proc task's transitions to the start / end
        // objects that paste created.
        reconnectSubProcTaskTransitions(editingDomain,
                subProcessTask.getId(),
                subProcTaskFlowContainer,
                (Activity) pasteObjectsCommand.getFlowStartActivity(),
                (Activity) pasteObjectsCommand.getFlowEndActivity());

        // Finally, add any sub-process fields to the calling process and...
        // - For sub-process parameters that were mapped from calling process
        // data, replace refs by name/id to calling proc field.
        // - Add sub-proc data fields (and unmapped parameters) to calling
        // process, replacing name/id references if they had to be renamed.
        addFieldsToCallingProc(editingDomain,
                callingProcess,
                subProcess,
                pastedObjects,
                fieldMap);

        // And if all that has worked it'll be a miracle and we'll be done.
        return;

    }

    private void resolveExternalPackageRefs(EditingDomain editingDomain,
            Package originalPkg, List<EObject> subProcObjects) {

        // Get set of the current ExternalPackage locations
        Set<String> curExtPkgLocations = new HashSet<String>();

        for (Iterator iterator = originalPkg.getExternalPackages().iterator(); iterator
                .hasNext();) {
            ExternalPackage ep = (ExternalPackage) iterator.next();

            curExtPkgLocations.add(Xpdl2WorkingCopyImpl
                    .getExternalPackageLocation(ep));
        }

        // Now go thru the subProcObjects list looking for any ExternalPackage
        // elements and add them to the package we're in-lining into.
        for (Iterator iterator = subProcObjects.iterator(); iterator.hasNext();) {
            EObject eo = (EObject) iterator.next();

            if (eo instanceof ExternalPackage) {
                ExternalPackage ep = (ExternalPackage) eo;

                String pkgLoc =
                        Xpdl2WorkingCopyImpl.getExternalPackageLocation(ep);

                if (!curExtPkgLocations.contains(pkgLoc)) {
                    originalPkg.getExternalPackages().add(ep);

                    curExtPkgLocations.add(pkgLoc);
                }

                // We've dealt with the ExternalPackage so can remove from list
                // now.
                iterator.remove();
            }
        }

        return;
    }

    /**
     * @param subProcessTask
     */
    private void removeSubProcTask(Activity subProcessTask) {

        FlowContainer modelTaskContainer = subProcessTask.getFlowContainer();
        modelTaskContainer.getActivities().remove(subProcessTask);
    }

    /**
     * Restore the original sub-proc object locations (because earlier we //
     * reset their positions to change from lane-relative to absolute)
     * 
     * @param oldObjectLocations
     */
    private void restoreOldObjectLocations(
            Map<Coordinates, Point> oldObjectLocations) {
        for (Entry<Coordinates, Point> entry : oldObjectLocations.entrySet()) {
            Point p = entry.getValue();
            Coordinates c = entry.getKey();

            c.setXCoordinate(p.x);
            c.setYCoordinate(p.y);
        }

        return;
    }

    /**
     * Make space for inlined sub-proc objects in the sub-process task's
     * container (activity if embedded sub-process else lane)
     * <p>
     * Note that this is a more crude method than MakeSpaceInParentCommand which
     * is only available from within a process editor (with a process widget
     * etc).
     * <p>
     * This isn't the best solution but the auto-inlined package is not really
     * for 'viewing' it's just a pre-deployment step.
     * 
     * @param graphicalTaskContainer
     *            Lane or Activity (if embedded sub-proc container)
     * @param spaceBeingReplaced
     * @param newSpaceRequired
     * @param ignoreObject
     */
    private void makeSpaceForInlinedObjects(EObject graphicalTaskContainer,
            Rectangle spaceBeingReplaced, Rectangle newSpaceRequired,
            Activity ignoreObject) {

        // Make space for the area to be occupied by the sub-proc contents.
        //
        // This is done by moving all siblings of the sub-process task that are
        // to it's right to the right, all that are below are moved down and all
        // that are below and right are moved right and down.
        int x, y;
        x = (spaceBeingReplaced.x + spaceBeingReplaced.width);
        y = spaceBeingReplaced.y;
        Rectangle rightRect =
                new Rectangle(x, y, (Integer.MAX_VALUE - x),
                        spaceBeingReplaced.height);

        x = spaceBeingReplaced.x;
        y = (spaceBeingReplaced.y + spaceBeingReplaced.height);

        Rectangle belowRect =
                new Rectangle(x, y, spaceBeingReplaced.width,
                        (Integer.MAX_VALUE - y));

        Rectangle belowRightRect =
                new Rectangle(rightRect.x, belowRect.y,
                        (Integer.MAX_VALUE - rightRect.x),
                        (Integer.MAX_VALUE - belowRect.y));

        // The amount to move is the difference between subproc task size and
        // size of sub-proc objects bounding rect (don't bother with shrinking
        // so set to zero if subproc objects are smaller than the calling
        // task!)..
        int offsetX =
                Math.max(0, newSpaceRequired.width - spaceBeingReplaced.width);
        int offsetY =
                Math.max(0, newSpaceRequired.height - spaceBeingReplaced.height);

        if (offsetX == 0 && offsetY == 0) {
            // No need to make space.
            return;
        }

        //
        // Go thru the sub-process task's siblings moving them if necessary.

        // Track the maximum right/bottom edge of contents (for sizing parent
        // container afterwards.
        int maxWidth = spaceBeingReplaced.x + newSpaceRequired.width;
        int maxHeight = spaceBeingReplaced.y + newSpaceRequired.height;

        Collection<EObject> siblings = null;

        if (graphicalTaskContainer instanceof Lane) {
            siblings =
                    Xpdl2ModelUtil
                            .getAllNodesInLane((Lane) graphicalTaskContainer);
        } else if (graphicalTaskContainer instanceof Activity) {
            siblings =
                    Xpdl2ModelUtil
                            .getAllNodesInEmbeddedSubProc((Activity) graphicalTaskContainer);
        }

        // Sort objects from top-left to bottom right.
        List<GraphicalNode> nodes = new ArrayList<GraphicalNode>();
        for (EObject obj : siblings) {
            if (obj != ignoreObject && obj instanceof GraphicalNode) {
                GraphicalNode node = (GraphicalNode) obj;
                nodes.add(node);
            }
        }

        Collections.sort(nodes, new Comparator<GraphicalNode>() {

            @Override
            public int compare(GraphicalNode o1, GraphicalNode o2) {
                Rectangle bnds1 = Xpdl2ModelUtil.getObjectBounds(o1);
                Rectangle bnds2 = Xpdl2ModelUtil.getObjectBounds(o2);

                if (bnds1.y != bnds2.y) {
                    return bnds1.y - bnds2.y;
                }
                return bnds1.x - bnds2.x;
            }
        });

        for (GraphicalNode node : nodes) {

            Rectangle bnds = Xpdl2ModelUtil.getObjectBounds(node);

            if (bnds.intersects(rightRect)) {
                // Move items to right of orig task to the right.
                offsetCoords(node, offsetX, 0);
                bnds.x += offsetX;

                // need to modify intersection rects if we have moved an object
                // right whose top edge was further up than the original
                // rightRect (so that objects right of IT are correctly moved
                // right.
                if (bnds.y < rightRect.y) {
                    rightRect.height += (rightRect.y - bnds.y);
                    rightRect.y = bnds.y;
                }
            } else if (bnds.intersects(belowRect)) {
                // Move items below orig task down.
                offsetCoords(node, 0, offsetY);

                bnds.y += offsetY;

                // need to modify intersection rects if we have moved an object
                // down whose left edge was further left than the original
                // belowRect (so that objects below IT are correctly moved down.
                if (bnds.x < belowRect.x) {
                    belowRect.width += (belowRect.x - bnds.x);
                    belowRect.x = bnds.x;
                }
            } else if (bnds.intersects(belowRightRect)) {
                // Move items right and below orig task to the right and
                // down.
                offsetCoords(node, offsetX, offsetY);
                bnds.x += offsetX;
                bnds.y += offsetY;

                // need to modify intersection rects if we have moved an object
                // right whose top edge was further up than the original
                // rightRect (so that objects right of IT are correctly moved
                // right.
                if (bnds.y < rightRect.y) {
                    rightRect.height += (rightRect.y - bnds.y);
                    rightRect.y = bnds.y;
                }

                // need to modify intersection rects if we have moved an object
                // down whose left edge was further left than the original
                // belowRect (so that objects below IT are correctly moved down.
                if (bnds.x < belowRect.x) {
                    belowRect.width += (belowRect.x - bnds.x);
                    belowRect.x = bnds.x;
                }
            }

            if (maxWidth < (bnds.x + bnds.width)) {
                maxWidth = (bnds.x + bnds.width);
            }

            if (maxHeight < (bnds.y + bnds.height)) {
                maxHeight = (bnds.y + bnds.height);
            }

        }

        // make sure parent container is big enough for new size.
        if (graphicalTaskContainer instanceof Activity) {
            // Resize parent embedded sub-process.
            Activity embSubProcTask = (Activity) graphicalTaskContainer;

            NodeGraphicsInfo gn =
                    Xpdl2ModelUtil.getNodeGraphicsInfo(embSubProcTask);
            if (gn != null) {
                Rectangle bnds = Xpdl2ModelUtil.getObjectBounds(embSubProcTask);
                Rectangle newBnds =
                        new Rectangle(bnds.x, bnds.y, bnds.width, bnds.height);

                if (bnds.width <= (maxWidth + EXPAND_CONTAINER_MARGIN)) {
                    newBnds.width = maxWidth + EXPAND_CONTAINER_MARGIN;
                }

                if (bnds.height <= (maxHeight + EXPAND_CONTAINER_MARGIN)) {
                    newBnds.height = maxHeight + EXPAND_CONTAINER_MARGIN;
                }

                if (bnds.width != newBnds.width
                        || bnds.height != newBnds.height) {
                    Coordinates c = gn.getCoordinates();
                    c.setXCoordinate(newBnds.x + (newBnds.width / 2));
                    c.setYCoordinate(newBnds.y + (newBnds.height / 2));
                    gn.setWidth(newBnds.width);
                    gn.setHeight(newBnds.height);

                    // Need to make embedded sub-process bigger, so should FIRST
                    // recurs up to parent container and shuffle this embedded
                    // sub-proc's siblings out of the way etc.
                    makeSpaceForInlinedObjects(InlineSubProcessUtil.getTaskContainer(embSubProcTask),
                            bnds,
                            newBnds,
                            embSubProcTask);
                }

            }

        } else {
            // Parent container is a lane so resize its height if necessary,.
            Lane lane = (Lane) graphicalTaskContainer;

            NodeGraphicsInfo gn = Xpdl2ModelUtil.getNodeGraphicsInfo(lane);

            if (gn.getHeight() < (maxHeight + EXPAND_CONTAINER_MARGIN)) {
                gn.setHeight(maxHeight + EXPAND_CONTAINER_MARGIN);
            }
        }

        return;
    }

    /**
     * Offset the given graphical node by the given amounts.
     * 
     * @param node
     * @param offsetX
     * @param offsetY
     */
    private void offsetCoords(GraphicalNode node, int offsetX, int offsetY) {
        NodeGraphicsInfo gn = Xpdl2ModelUtil.getNodeGraphicsInfo(node);
        if (gn != null) {
            Coordinates c = gn.getCoordinates();
            c.setXCoordinate(c.getXCoordinate() + offsetX);
            c.setYCoordinate(c.getYCoordinate() + offsetY);
        }
    }

    /**
     * Add any sub-process fields to the calling process and...
     * 
     * <li>For sub-process parameters that were mapped from calling process
     * data, replace refs by name/id to calling proc field.</li>
     * 
     * <li>Add sub-proc data fields (and unmapped parameters) to calling
     * process, replacing name/id references if they had to be renamed</li>
     * 
     * @param editingDomain
     * 
     * @param callingProcess
     * @param subProcess
     * @param pastedObjects
     * @param fieldMap
     */
    private void addFieldsToCallingProc(EditingDomain editingDomain,
            Process callingProcess, Process subProcess,
            Collection pastedObjects,
            Map<ProcessRelevantData, ProcessRelevantData> fieldMap) {
        Command cmd =
                InlineSubProcessUtil
                        .getAddFieldsToCallingProcCommand(editingDomain,
                                callingProcess,
                                subProcess,
                                pastedObjects,
                                fieldMap);
        if (cmd != null) {
            cmd.execute();
        }

        return;
    }

    /**
     * Connect the transitions previously connected to the sub-process call task
     * to the start/end activity in the sub-process.
     * 
     * @param editingDomain
     * @param srcActivityId
     * @param srcFlowContainer
     * @param flowStartActivity
     * @param flowEndActivity
     */
    private void reconnectSubProcTaskTransitions(EditingDomain editingDomain,
            String srcActivityId, FlowContainer srcFlowContainer,
            Activity flowStartActivity, Activity flowEndActivity) {

        Command cmd =
                InlineSubProcessUtil.reconnectConnections(editingDomain,
                        srcActivityId,
                        srcFlowContainer,
                        flowStartActivity,
                        flowEndActivity);
        if (cmd != null && cmd.canExecute()) {
            cmd.execute();
        }

        return;
    }

    /**
     * Get location for pasting inline sub-proc content into calling proc.
     * 
     * @param subProcessTask
     * @param subProcObjects
     * @return
     */
    private Point getInlinePasteLocation(Activity subProcessTask,
            List<EObject> subProcObjects) {
        Rectangle subProcTaskBnds =
                Xpdl2ModelUtil.getObjectBounds(subProcessTask);

        // Don't bother trying to centre start activity on subproc task
        // position.
        // this would be nice except we don't know that start act is left=most
        // and we have to deal with making space for other objects.
        return new Point(subProcTaskBnds.x, subProcTaskBnds.y);
    }

    /**
     * @param act
     * @param originalPkg
     * @return
     */
    private EObject getSubProcessFromTask(Activity act, Package originalPkg) {
        // If the activity is in the original package we are analysing then use
        // the working copy we were passed.
        Package pkg = Xpdl2ModelUtil.getPackage(act);
        if (pkg == originalPkg) {
            // If the object is in the main package we're dealing with then use
            // the working copy we were passed (as the mainbPackage may be a
            // copy of original and hence won't have a working copy).
            return TaskObjectUtil.getSubProcessOrInterface(act, workingCopy);
        }

        // Otherwise if its an ext package ref then it will have a working copy.
        // So can use default method.
        return TaskObjectUtil.getSubProcessOrInterface(act);
    }
}
