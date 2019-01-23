/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.inlineSubProcess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.graphics.Rectangle;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses.InlineSubProcessInfo;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamReplacer;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utilities to help in inlining sub-processes.
 * <p>
 * <b>NOTE that these utilities may be used by BOTH auto-inlining (as part of
 * Create Optimized Package facility) AND manual refactoring (via sub-process
 * task refactor in diagram editor).</b>
 * <p>
 * Therefore you must be careful not to place any functionality specific to
 * either of these areas in this class.
 * 
 * @author aallway
 * 
 */
public class InlineSubProcessUtil {

    /**
     * Get a COPY of all the objects (activities/transitions/artifacts etc) in
     * the subprocess.
     * <p>
     * If required, this method will also look for the start activity and if it
     * is a Start Event, NOT include it and its outgoing transitions. It will do
     * similar for the end event.
     * 
     * @param editingDomain
     * @param subProcess
     * @param inlineSubProcAnalysis
     * @param removeStartEndEvents
     *            True if you want the start and end event removed from the
     *            sub-proc object list.
     * @param isSubProcFromExternalPkg
     *            True if sub-process is external to the package to are
     *            in-lining into.
     * @param originalPackage
     *            The absolute original package that we are inlinging (not the
     *            copy package from auto inline).
     * @param idMap
     *            If the subProcess is in a copy package then this is a map of
     *            poriginalPkg id's for the new objects in the copy package.
     *            <b>Otherwise it should be null (if originalPkg is not a copy
     *            opackage.</b>
     * 
     * @return Copies of the sub-process activities, transitions, artifacts,
     *         associations, (and ExternalPackage elements if this ext
     *         sub-process calls other sub-processes in this (or other) ext pkg
     */
    public static List<EObject> getInlineSubProcObjects(
            EditingDomain editingDomain, Process subProcess,
            AnalyseInlineSubProcesses inlineSubProcAnalysis,
            boolean removeStartEndEvents, boolean isSubProcFromExternalPkg,
            Package originalPackage, Map<String, EObject> idMap) {
        List<EObject> subProcObjects = new ArrayList<EObject>();

        EList subProcActs = subProcess.getActivities();
        EList subProcTrans = subProcess.getTransitions();

        // Find start and end activities.
        InlineSubProcessInfo inlineSubProcInfo =
                inlineSubProcAnalysis.getInlineSubProcessInfo(subProcess);
        if (inlineSubProcInfo == null) {
            throw new RuntimeException(
                    "Can't access inline sub-process information stored for sub-process during analysis."); //$NON-NLS-1$
        }

        // The start/end act and 'is event' flags are only used if we are
        // removing start/end events.
        Activity startAct = null;
        Activity endAct = null;

        boolean startActIsStartEvent = false;
        boolean endActIsEndEvent = false;

        if (removeStartEndEvents) {
            if (inlineSubProcInfo.startActs.size() == 0) {
                throw new RuntimeException(
                        "Didn't find start activity of sub-process: " //$NON-NLS-1$
                                + subProcess.getName()
                                + ", problem was ignored when it should not have been."); //$NON-NLS-1$

            } else if (inlineSubProcInfo.startActs.size() > 1) {
                throw new RuntimeException(
                        "Found multiple start activities in sub-process: " //$NON-NLS-1$
                                + subProcess.getName()
                                + ", problem was ignored when it should not have been."); //$NON-NLS-1$
            }

            if (inlineSubProcInfo.endActs.size() == 0) {
                throw new RuntimeException(
                        "Didn't find end activity of sub-process: " //$NON-NLS-1$
                                + subProcess.getName()
                                + ", problem was ignored when it should not have been."); //$NON-NLS-1$

            } else if (inlineSubProcInfo.endActs.size() > 1) {
                throw new RuntimeException(
                        "Found multiple end activities in sub-process: " //$NON-NLS-1$
                                + subProcess.getName()
                                + ", problem was ignored when it should not have been."); //$NON-NLS-1$
            }

            startAct = inlineSubProcInfo.startActs.get(0);
            endAct = inlineSubProcInfo.endActs.get(0);

            startActIsStartEvent = (startAct.getEvent() instanceof StartEvent);
            endActIsEndEvent = (endAct.getEvent() instanceof EndEvent);

        }

        // Add all activities except start activity if its a start event (same
        // for end event)
        for (Iterator iterAct = subProcActs.iterator(); iterAct.hasNext();) {
            Activity act = (Activity) iterAct.next();

            if (removeStartEndEvents) {
                // Only add start activity if it isn't a start event.
                if (act == startAct && startActIsStartEvent) {
                    continue;
                }

                // Only add end activity if it isn't a end event.
                if (act == endAct && endActIsEndEvent) {
                    continue;
                }
            }

            subProcObjects.add(act);
        }

        // Add the transitions to the list (except those from start event / end
        // event which will not have been included).

        for (Iterator iterTrans = subProcTrans.iterator(); iterTrans.hasNext();) {
            Transition trans = (Transition) iterTrans.next();

            if (removeStartEndEvents) {
                // Only add transitions from start activity IF it wasn't a start
                // event that we're not including
                if (startAct.getId().equals(trans.getFrom())
                        && startActIsStartEvent) {
                    continue;
                }

                // Only add transitions to end activity IF it wasn't an end
                // event that we're not including.
                if (endAct.getId().equals(trans.getTo()) && endActIsEndEvent) {
                    continue;
                }
            }

            subProcObjects.add(trans);
        }

        //
        // Finally add the ancillary bits.
        subProcObjects.addAll(subProcess.getActivitySets());

        subProcObjects.addAll(Xpdl2ModelUtil
                .getAllArtifactsInProcess(subProcess));

        // Add all association connections from sub-proc except any that are
        // to/from start/;end events we may have removed.
        Collection<Association> associations =
                Xpdl2ModelUtil.getAllAssociationsInProc(subProcess);
        for (Association ass : associations) {
            if (removeStartEndEvents) {
                String startActId = startAct.getId();
                String endActId = endAct.getId();

                if (startActIsStartEvent) {
                    if (startActId.equals(ass.getSource())
                            || startActId.equals(ass.getTarget())) {
                        // Ignore associations to/from removed start event.
                        continue;
                    }
                }

                if (endActIsEndEvent) {
                    if (endActId.equals(ass.getSource())
                            || endActId.equals(ass.getTarget())) {
                        // Ignore associations to/from removed end event.
                        continue;
                    }
                }
            }

            subProcObjects.add(ass);
        }

        //
        // We must now create copies of the objects in the sub-process.
        // This is because we may need to change them (for instance, sub-proc
        // tasks that call processes in the same package as sub-process will
        // need package reference added if that package is external to package
        // into which we are in-lining).
        Command cpyCmd = CopyCommand.create(editingDomain, subProcObjects);
        if (!cpyCmd.canExecute()) {
            throw new RuntimeException(
                    "Failed to create copy of sub-proc objects."); //$NON-NLS-1$
        }
        cpyCmd.execute();

        // Recreate the list.
        List<EObject> newSubProcObjects = new ArrayList<EObject>();
        newSubProcObjects.addAll((Collection) cpyCmd.getResult());

        // 
        // If this sub-process is in a package external to the original
        // package that we are in-lining into then...
        //
        // - If this sub-process has sub-process call task that calls a process
        // in the sub-process's package it will NOT have a package reference.
        // Therefore will need to add the package reference so that when it is
        // in-lined into the original package, it will reference the original
        // correctly. We must also add the ExternalPackage element to package we
        // are inlining into.
        //
        // - If this sub-process has sub-process call task that references a
        // process in yet another package it will already have a package
        // reference. In this case, we need to make sure that we copy the
        // ExternalPackage element for it to the package we are in-lining into.
        //
        // - If this sub-process is in the package we are in-lining into then we
        // don't have anything to do (externally ref'd sub-process would
        // already have the correct package reference and the package already
        // has ExternalPackages for it.
        //
        // 
        ExternalPackage addSrcExtPkg = null;
        Set<ExternalPackage> otherExtPkgs = new HashSet<ExternalPackage>();

        if (isSubProcFromExternalPkg) {
            // Getting objects for a sub-process that is in pkg that is external
            // to the process we are inlining into.

            // Look for sub-process call tasks...
            for (EObject eo : newSubProcObjects) {
                if (eo instanceof Activity) {
                    Activity act = (Activity) eo;

                    addSrcExtPkg =
                            getExternalPackages(subProcess,
                                    addSrcExtPkg,
                                    otherExtPkgs,
                                    act);
                } else if (eo instanceof ActivitySet) {
                    for (Iterator iterator =
                            ((ActivitySet) eo).getActivities().iterator(); iterator
                            .hasNext();) {
                        Activity act = (Activity) iterator.next();

                        addSrcExtPkg =
                                getExternalPackages(subProcess,
                                        addSrcExtPkg,
                                        otherExtPkgs,
                                        act);
                    }
                }

            } // Next sub-process task.

            // Take a copy of any extra ExternalPackage elements and add to the
            // sub-proc objects.
            if (addSrcExtPkg != null) {
                otherExtPkgs.add(addSrcExtPkg);
            }

            if (otherExtPkgs.size() > 0) {
                Command cpyExtPkgsCmd =
                        CopyCommand.create(editingDomain, otherExtPkgs);
                if (!cpyExtPkgsCmd.canExecute()) {
                    throw new RuntimeException(
                            "Failed to copy ExternalPackage elements from sub-process pkg."); //$NON-NLS-1$
                }

                cpyExtPkgsCmd.execute();

                newSubProcObjects
                        .addAll((Collection) cpyExtPkgsCmd.getResult());
            }

        }

        //
        // If the sub-process implements an interface then we need to look for
        // activities that implement interface events and copy in the things
        // that are normally inherited from interface.

        // Must use absolute original subprocess because ProcessInterfaceUtil
        // uses working copy and the subprocess we were given to inline MAY be
        // from a copy package for auto inline
        Process originalSubProcess =
                getOriginalProcess(originalPackage, subProcess, idMap);

        ProcessInterface procInterface =
                ProcessInterfaceUtil
                        .getImplementedProcessInterface(originalSubProcess);

        if (procInterface != null) {
            // Look for activities that implement process interface events.
            for (EObject eo : newSubProcObjects) {
                if (eo instanceof Activity) {
                    Activity newCopyOfActivity = (Activity) eo;

                    resolveImplementedEventsData(editingDomain,
                            idMap,
                            originalSubProcess,
                            newCopyOfActivity,
                            subProcObjects);

                } else if (eo instanceof ActivitySet) {
                    ActivitySet actSet = (ActivitySet) eo;

                    for (Iterator iterator = actSet.getActivities().iterator(); iterator
                            .hasNext();) {
                        Activity newCopyOfActivity = (Activity) iterator.next();

                        resolveImplementedEventsData(editingDomain,
                                idMap,
                                originalSubProcess,
                                newCopyOfActivity,
                                subProcObjects);

                    }
                }
            }
        }

        return newSubProcObjects;
    }

    /**
     * Check for activities that implement process interface events and copy
     * relevant data up from interface event into implementation activity.
     * 
     * @param editingDomain
     * @param idMap
     * @param originalSubProcess
     * @param newCopyOfActivity
     * @param fallbackOriginalSubProcObjectList
     *            List of original sub-proc objects to fall back on to look up
     *            original activity (if idMap = null).
     */
    private static void resolveImplementedEventsData(
            EditingDomain editingDomain, Map<String, EObject> idMap,
            Process originalSubProcess, Activity newCopyOfActivity,
            List<EObject> fallbackOriginalSubProcObjectList) {
        if (ProcessInterfaceUtil.isEventImplemented(newCopyOfActivity)) {
            Activity originalActivity =
                    getOriginalActivity(originalSubProcess,
                            newCopyOfActivity,
                            idMap,
                            fallbackOriginalSubProcObjectList);

            List ifcAssocParams = null;

            com.tibco.xpd.xpdExtension.StartMethod ifcStartEv =
                    ProcessInterfaceUtil
                            .getImplementedStartMethod(originalActivity);
            if (ifcStartEv != null) {
                // Copy Name and get associated params from the
                // interface.
                newCopyOfActivity.setName(ifcStartEv.getName());
                EAttribute ea =
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName();
                Xpdl2ModelUtil.setOtherAttribute(newCopyOfActivity,
                        ea,
                        Xpdl2ModelUtil.getOtherAttribute(ifcStartEv, ea));

                ifcAssocParams =
                        ProcessInterfaceUtil
                                .getImplementedMethodAssociatedParameters(originalActivity);

            } else {
                com.tibco.xpd.xpdExtension.IntermediateMethod ifcInterEv =
                        ProcessInterfaceUtil
                                .getImplementedIntermediateMethod(originalActivity);
                if (ifcInterEv != null) {
                    // Copy Name and get associated params from the
                    // interface.
                    newCopyOfActivity.setName(ifcInterEv.getName());

                    ifcAssocParams =
                            ProcessInterfaceUtil
                                    .getImplementedMethodAssociatedParameters(originalActivity);

                }
            }

            if (ifcAssocParams != null && ifcAssocParams.size() > 0) {
                // Add the interface associated parameters to the
                // activity.
                AssociatedParameters associatedParameters =
                        (AssociatedParameters) newCopyOfActivity
                                .getOtherElement(XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AssociatedParameters()
                                        .getName());
                if (associatedParameters == null) {
                    associatedParameters =
                            XpdExtensionFactory.eINSTANCE
                                    .createAssociatedParameters();

                    Xpdl2ModelUtil.setOtherElement(newCopyOfActivity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters(),
                            associatedParameters);
                }

                Command cpyParamCmd =
                        CopyCommand.create(editingDomain, ifcAssocParams);
                if (!cpyParamCmd.canExecute()) {
                    throw new RuntimeException(
                            "Failed to copy interface event associated parameters."); //$NON-NLS-1$
                }

                cpyParamCmd.execute();

                Collection<?> copied = cpyParamCmd.getResult();
                for (Object next : copied) {
                    if (next instanceof AssociatedParameter) {
                        associatedParameters.getAssociatedParameter()
                                .add((AssociatedParameter) next);
                    }
                }
            }

            // And finally, remove the Implements attribute.
            Xpdl2ModelUtil.setOtherAttribute(newCopyOfActivity,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Implements(),
                    null);
        }
    }

    /**
     * If we are working on a copy of the original package then get the original
     * activity from it for the given copy activity.
     * 
     * @param originalPackage
     * @param subProcess
     * @param idMap
     *            null if not working on a copy of package (hence subProcess is
     *            the Original Process.
     * @param fallbackOriginalSubProcObjectList
     *            List of original sub-proc objects to fall back on to look up
     *            original activity (if idMap = null).
     * @return original activity
     */
    private static Activity getOriginalActivity(Process originalSubProcess,
            Activity activity, Map<String, EObject> idMap,
            List<EObject> fallbackOriginalSubProcObjectList) {
        if (idMap != null) {
            // subProcess is the NEW copy of a sub-process in a copy package.
            // Find it in the map of new objects.
            String newActivityId = activity.getId();
            String originalActivityId = null;

            for (Entry<String, EObject> mapEntry : idMap.entrySet()) {
                EObject newObj = mapEntry.getValue();

                if (newObj instanceof Activity) {
                    Activity newActivity = (Activity) newObj;

                    if (newActivityId.equals(newActivity.getId())) {
                        // Found the activity in the copy objects list.
                        // Get the OLD activity id from the map entry.
                        originalActivityId = mapEntry.getKey();
                        break;
                    }
                }
            }

            if (originalActivityId == null) {
                throw new RuntimeException("Cannot find new copy of activity (" //$NON-NLS-1$
                        + newActivityId + ") in new copies id map."); //$NON-NLS-1$
            }

            // Now find the original activity in the original package.
            Activity originalActivity = null;

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(originalSubProcess);
            for (Activity origAct : activities) {
                if (originalActivityId.equals(origAct.getId())) {
                    originalActivity = origAct;
                }
            }

            if (originalActivity == null) {
                throw new RuntimeException("Cannot find original activity (" //$NON-NLS-1$
                        + originalActivityId + ") in original process."); //$NON-NLS-1$
            }

            return originalActivity;
        }

        // Look up activity from fall back list.
        String actId = activity.getId();

        for (EObject eo : fallbackOriginalSubProcObjectList) {
            if (eo instanceof Activity) {
                if (actId.equals(((Activity) eo).getId())) {
                    return (Activity) eo;
                }
            } else if (eo instanceof ActivitySet) {
                Activity a = ((ActivitySet) eo).getActivity(actId);
                if (a != null) {
                    return a;
                }
            }
        }

        throw new RuntimeException("Could not find original for Activity(" //$NON-NLS-1$
                + actId
                + ") in either target pokg copies or original external pkg."); //$NON-NLS-1$

    }

    /**
     * If we are working on a copy of the original package then get the original
     * process from it for the given copy process.
     * 
     * @param originalPackage
     * @param subProcess
     * @param idMap
     *            null if not working on a copy of package (hence subProcess is
     *            the Original Process.
     * @return original sub-proc
     */
    public static Process getOriginalProcess(Package originalPackage,
            Process subProcess, Map<String, EObject> idMap) {
        if (idMap != null) {
            // subProcess is the NEW copy of a sub-process in a copy package.
            // Find it in the map of new objects.
            String newProcessId = subProcess.getId();
            String originalProcessId = null;

            for (Entry<String, EObject> mapEntry : idMap.entrySet()) {
                EObject newObj = mapEntry.getValue();

                if (newObj instanceof Process) {
                    Process newProcess = (Process) newObj;

                    if (newProcessId.equals(newProcess.getId())) {
                        // Found the process in the copy objects list.
                        // Get the OLD process id from the map entry.
                        originalProcessId = mapEntry.getKey();
                        break;
                    }
                }
            }

            if (originalProcessId == null) {
                throw new RuntimeException(
                        "Cannot find new copy of sub-process (" + newProcessId //$NON-NLS-1$
                                + ") in new copies id map."); //$NON-NLS-1$
            }

            // Now find the original process in the original package.
            Process originalSubProcess =
                    originalPackage.getProcess(originalProcessId);
            if (originalSubProcess == null) {
                throw new RuntimeException("Cannot find original sub-process (" //$NON-NLS-1$
                        + originalProcessId + ") in original package."); //$NON-NLS-1$
            }

            return originalSubProcess;
        }

        return subProcess;
    }

    /**
     * Add external package refs to otherExtPkgs and return ExternalPackage for
     * sub-process called from the subProcess to a process in its own package.
     * 
     * @param subProcess
     * @param addSrcExtPkg
     * @param otherExtPkgs
     * @param act
     * 
     * @return new ExternalPackage for syubProcess's pkg if it calls another
     *         process in its own pkg.
     */
    private static ExternalPackage getExternalPackages(Process subProcess,
            ExternalPackage addSrcExtPkg, Set<ExternalPackage> otherExtPkgs,
            Activity act) {
        if (act.getImplementation() instanceof SubFlow) {
            SubFlow subFlow = (SubFlow) act.getImplementation();

            String packageRefId = subFlow.getPackageRefId();
            if (packageRefId == null || packageRefId.length() == 0) {

                if (subProcess.getPackage().getProcess(subFlow.getProcessId()) != null) {
                    // This is a call from the ext pkg sub-process
                    // we're inlining to another process in the same
                    // package as it.
                    if (addSrcExtPkg == null) {
                        // If we haven't already then create a
                        // ExternalPackage that will be required in
                        // the target package once this sub-process
                        // is inlined into it.
                        addSrcExtPkg =
                                Xpdl2WorkingCopyImpl
                                        .createExternalPackage(WorkingCopyUtil
                                                .getWorkingCopyFor(subProcess
                                                        .getPackage()));
                    }

                    if (addSrcExtPkg != null) {
                        // Once in-lined into the target package
                        // then this sub-process task will need to
                        // reference the source package.
                        subFlow.setPackageRefId(addSrcExtPkg.getHref());
                    }
                }
            } else {
                // This is a call to a process that is in a pkg that
                // is external to the ext-pkg sub-process we're
                // in-lining. Get the source pkg's ExternalPackage
                // for it so we can copy it to the target package
                // we're inlining into.
                for (Iterator iter =
                        subProcess.getPackage().getExternalPackages()
                                .iterator(); iter.hasNext();) {
                    ExternalPackage extPkg = (ExternalPackage) iter.next();

                    if (packageRefId.equals(extPkg.getHref())) {
                        otherExtPkgs.add(extPkg);
                    }
                }
            }
        }
        return addSrcExtPkg;
    }

    /**
     * Adjust the coordinates of the objects in the sub-process to make them
     * absolute positions. (We will also compress space at the top and bottom of
     * lanes to use up less space in the calling process).
     * 
     * @param subProcess
     * @param subProcObjects
     * @return Map of graphical nodes coordinates to their old co-ords.
     */
    public static Map<Coordinates, Point> adjustSubProcObjectsLocations(
            Process subProcess, Collection<EObject> subProcObjects) {
        Map<Coordinates, Point> coordMap = new HashMap<Coordinates, Point>();

        int currLaneY = 0;

        for (Iterator iterator =
                Xpdl2ModelUtil.getProcessPools(subProcess).iterator(); iterator
                .hasNext();) {
            Pool object = (Pool) iterator.next();

            for (Iterator iterLane = object.getLanes().iterator(); iterLane
                    .hasNext();) {
                Lane lane = (Lane) iterLane.next();
                String laneId = lane.getId();

                // Get total bounds of objects within this lane.
                // And a list of objects while we're at it.
                Rectangle laneObjBnds = null;
                List<NodeGraphicsInfo> laneObjInfos =
                        new ArrayList<NodeGraphicsInfo>();

                for (EObject eo : subProcObjects) {
                    if (eo instanceof GraphicalNode) {
                        GraphicalNode gNode = (GraphicalNode) eo;

                        NodeGraphicsInfo gi =
                                Xpdl2ModelUtil.getNodeGraphicsInfo(gNode);

                        if (laneId.equals(gi.getLaneId())) {
                            laneObjInfos.add(gi);

                            Rectangle bnds =
                                    Xpdl2ModelUtil.getObjectBounds(gNode);
                            if (laneObjBnds == null) {
                                laneObjBnds = bnds;
                            } else {
                                laneObjBnds = laneObjBnds.union(bnds);
                            }
                        }
                    }
                }

                if (laneObjInfos.size() > 0) {
                    for (NodeGraphicsInfo gi : laneObjInfos) {
                        Coordinates coords = gi.getCoordinates();
                        if (coords != null) {
                            // Store the old coords in a map so that they can be
                            // put back later after we have copied and pasted
                            // the objects.
                            double yCoord = coords.getYCoordinate();
                            coordMap.put(coords, new Point(coords
                                    .getXCoordinate(), yCoord));

                            // Offset the coordinates up by the offset of the
                            // highest item in lane and down by the lane offset.
                            // (We don't worry about shifting left, the paste
                            // command later will handle that nicely for us
                            // later.
                            coords.setYCoordinate((yCoord - laneObjBnds.y)
                                    + currLaneY);
                        }
                    }

                    // Move top of objects in next lane down...
                    currLaneY +=
                            laneObjBnds.height
                                    + ProcessWidgetConstants.TASK_HEIGHT_SIZE;
                }

            } // Offset objects in next lane.
        }

        return coordMap;
    }

    /**
     * Get lane or embedded sub-proc activity for sub-process task.
     * 
     * @param task
     * @return
     */
    public static EObject getTaskContainer(Activity task) {
        EObject container = Xpdl2ModelUtil.getContainer(task);
        if (container instanceof ActivitySet) {
            // Find embedded sub-proc activity for activity set.
            String actSetId = ((ActivitySet) container).getId();

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(task.getProcess());
            for (Activity act : activities) {
                BlockActivity blockActivity = act.getBlockActivity();
                if (blockActivity != null) {
                    if (actSetId.equals(blockActivity.getActivitySetId())) {
                        container = act;
                    }
                }
            }
        }

        if (!(container instanceof Lane) && !(container instanceof Activity)) {
            throw new RuntimeException(
                    "Sub-Process Task is not contained in lane or embedded sub-process."); //$NON-NLS-1$
        }

        return container;
    }

    /**
     * @param subProcObjects
     * @return
     */
    public static Rectangle getSubProcObjectsBounds(
            Collection<EObject> subProcObjects) {
        Rectangle totalBnds = null;

        for (EObject obj : subProcObjects) {
            if (obj instanceof GraphicalNode) {
                Rectangle bnds =
                        Xpdl2ModelUtil.getObjectBounds((GraphicalNode) obj);

                if (totalBnds == null) {
                    totalBnds = bnds;
                } else {
                    totalBnds = totalBnds.union(bnds);
                }
            }
        }
        return totalBnds;
    }

    /**
     * Get sub-process participants from the sub-process (and it's package if
     * different from our own.
     * 
     * @param callingProcess
     * @param subProcess
     * @param subProcObjects
     */
    public static List<Participant> getSubProcParticipants(
            Process callingProcess, Process subProcess,
            Collection<EObject> subProcObjects) {

        List<Participant> participants = new ArrayList<Participant>();

        for (Iterator iter = subProcess.getParticipants().iterator(); iter
                .hasNext();) {
            Participant partic = (Participant) iter.next();
            participants.add(partic);
        }

        // If the sub-process is in a different package from the calling process
        // then copy the package participants too.
        if (subProcess.getPackage() != callingProcess.getPackage()) {
            for (Iterator iter =
                    subProcess.getPackage().getParticipants().iterator(); iter
                    .hasNext();) {
                Participant partic = (Participant) iter.next();
                participants.add(partic);
            }
        }

        return participants;
    }

    /**
     * Reconnect the existing sequence flow to sub-process task to newly inlined
     * start / end objects. Also reconnect associations/message flows if
     * possible.
     * 
     * @param editingDomain
     * @param srcActivityId
     * @param srcFlowContainer
     * @param startOfInlinedFlowAct
     * @param endOfInlinedFlowAct
     * 
     * @return reconnection command or null if nothing to do.
     */
    public static Command reconnectConnections(EditingDomain editingDomain,
            String srcActivityId, FlowContainer srcFlowContainer,
            Activity startOfInlinedFlowAct, Activity endOfInlinedFlowAct) {

        CompoundCommand cmd = new CompoundCommand();

        if (startOfInlinedFlowAct == null || endOfInlinedFlowAct == null) {
            throw new RuntimeException(
                    "Unexpected condition - No start/end activity in inlined content"); //$NON-NLS-1$
        }

        List<Transition> incoming =
                Xpdl2ModelUtil.getIncomingTransitions(srcActivityId,
                        srcFlowContainer);
        List<Transition> outgoing =
                Xpdl2ModelUtil.getOutgoingTransitions(srcActivityId,
                        srcFlowContainer);

        // Reconnect incoming to inlined start activity.
        for (Transition in : incoming) {
            cmd.append(SetCommand.create(editingDomain,
                    in,
                    Xpdl2Package.eINSTANCE.getTransition_To(),
                    startOfInlinedFlowAct.getId()));
        }

        // Reconnect outgoing to inlined end activity.
        for (Transition out : outgoing) {
            cmd.append(SetCommand.create(editingDomain,
                    out,
                    Xpdl2Package.eINSTANCE.getTransition_From(),
                    endOfInlinedFlowAct.getId()));
        }

        Process srcProcess;
        if (srcFlowContainer instanceof Process) {
            srcProcess = (Process) srcFlowContainer;
        } else if (srcFlowContainer instanceof ActivitySet) {
            srcProcess = ((ActivitySet) srcFlowContainer).getProcess();
        } else {
            throw new RuntimeException(
                    "Invalid condition: sub-proc task acitvity is not parented by process/activity set."); //$NON-NLS-1$
        }

        // Connect all associations previously connected to sub-proc task to
        // start of flow task.
        Collection<Association> associations =
                Xpdl2ModelUtil.getAllAssociationsInProc(srcProcess);
        for (Association assoc : associations) {
            if (srcActivityId.equals(assoc.getSource())) {
                cmd.append(SetCommand.create(editingDomain,
                        assoc,
                        Xpdl2Package.eINSTANCE.getAssociation_Source(),
                        startOfInlinedFlowAct.getId()));
            } else if (srcActivityId.equals(assoc.getTarget())) {
                cmd.append(SetCommand.create(editingDomain,
                        assoc,
                        Xpdl2Package.eINSTANCE.getAssociation_Target(),
                        startOfInlinedFlowAct.getId()));
            }
        }

        // 
        // Reconnect any message flows if possible (else remove them).
        Collection<MessageFlow> msgFlows =
                Xpdl2ModelUtil.getAllMessageFlowsInProc(srcProcess);
        for (MessageFlow msg : msgFlows) {
            if (srcActivityId.equals(msg.getSource())) {
                // If the original sub proc call task was the source of a
                // message flow that reconnect the message flow to the last end
                // of the sub-proc content (if it's a type that can be source of
                // message flow.
                if (isValidMessageFlowSource(endOfInlinedFlowAct)) {
                    cmd.append(SetCommand.create(editingDomain,
                            msg,
                            Xpdl2Package.eINSTANCE.getMessageFlow_Source(),
                            endOfInlinedFlowAct.getId()));
                } else {
                    // Can't have outgoing msg flow end of flow activity's type
                    // so delete it.
                    removeMessageFlow(editingDomain, cmd, srcProcess, msg);
                }

            } else if (srcActivityId.equals(msg.getTarget())) {
                // If the original sub proc call task was the target of a
                // message flow that reconnect the message flow to the start
                // of the sub-proc content flow (if it's a type that can be
                // target of message flow.
                if (isValidMessageFlowTarget(startOfInlinedFlowAct)) {
                    cmd.append(SetCommand.create(editingDomain,
                            msg,
                            Xpdl2Package.eINSTANCE.getMessageFlow_Target(),
                            startOfInlinedFlowAct.getId()));
                } else {
                    // Can't have outgoing msg flow end of flow activity's type
                    // so delete it.
                    removeMessageFlow(editingDomain, cmd, srcProcess, msg);
                }

            }
        }

        return (cmd.getCommandList().size() > 0) ? cmd : null;
    }

    /**
     * Remove the message flow and any associations connected to it.
     * 
     * @param editingDomain
     * @param cmd
     * @param process
     * @param msg
     */
    private static void removeMessageFlow(EditingDomain editingDomain,
            CompoundCommand cmd, Process process, MessageFlow msg) {
        // Remove any associations connected to message flow.
        Collection<Association> assocs =
                Xpdl2ModelUtil.getAllAssociationsInProc(process);

        String msgId = msg.getId();

        for (Association assoc : assocs) {
            if (msgId.equals(assoc.getSource())
                    || msgId.equals(assoc.getTarget())) {
                cmd.append(RemoveCommand.create(editingDomain, assoc));
            }
        }

        cmd.append(RemoveCommand.create(editingDomain, msg));

        return;
    }

    /**
     * @param act
     * 
     * @return true if given activity is a valid source for message flows.
     */
    private static boolean isValidMessageFlowSource(Activity act) {
        if (act.getEvent() != null) {
            // Start / intermediate events are not valid sources of message
            // flows.
            if (!(act.getEvent() instanceof EndEvent)) {
                return false;
            }
        } else if (act.getRoute() != null) {
            // Gateway is not valid source of message flow.
            return false;
        }

        // All others (tasks, sub-proc calls, embedded sub-proc and end event
        // are valid message flow sources.
        return true;
    }

    /**
     * @param act
     * 
     * @return true if given activity is a valid target for message flows.
     */
    private static boolean isValidMessageFlowTarget(Activity act) {
        if (act.getEvent() != null) {
            // End events are not valid targets of message
            // flows.
            if ((act.getEvent() instanceof EndEvent)) {
                return false;
            }
        } else if (act.getRoute() != null) {
            // Gateway is not valid target of message flow.
            return false;
        }

        // All others (tasks, sub-proc calls, embedded sub-proc and
        // start/intermediate event
        // are valid message flow targets.
        return true;
    }

    /**
     * Get any sub-process data fields (and formal parameters that task does not
     * map to as these will need to be created in the calling proc).
     * 
     * This returns a map of SubProcField/Param --> MainProcFieldOrParam where
     * MainProcFieldOrParam is either... <li>An existing calling process field
     * that was mapped to the given sub-proc param. <li>A copy of a sub-process
     * field (or unmapped parameter) (in which case it's eContainer=null). <li>A
     * renamed copy of a sub-process field (or unmapped param) (in which case
     * it's eContainer=null).
     * 
     * @param editingDomain
     * @param callingProcess
     * @param subProcessTask
     * @param subProcess
     * @param originalPkg
     *            The absolute original package that we are inlining (not the
     *            copy package from auto inline).
     * @param idMap
     *            If the subProcess is in a copy package then this is a map of
     *            originalPkg id's for the new objects in the copy package.
     *            <b>Otherwise it should be null (if originalPkg is not a copy
     *            package).</b>
     * @return see description above.
     */
    public static Map<ProcessRelevantData, ProcessRelevantData> getSubProcFields(
            EditingDomain editingDomain, Process callingProcess,
            Activity subProcessTask, Process subProcess, Package originalPkg,
            Map<String, EObject> idMap) {

        //
        // Get fields / params names available to calling process.
        //
        Map<String, ProcessRelevantData> callingFields =
                new HashMap<String, ProcessRelevantData>();
        Map<String, ProcessRelevantData> callingFieldDisplayNames =
                new HashMap<String, ProcessRelevantData>();

        for (Iterator iter = callingProcess.getFormalParameters().iterator(); iter
                .hasNext();) {
            ProcessRelevantData param = (ProcessRelevantData) iter.next();
            callingFields.put(param.getName(), param);

            String displayName = Xpdl2ModelUtil.getDisplayName(param);
            if (displayName != null && displayName.length() > 0) {
                callingFieldDisplayNames.put(displayName, param);
            }

        }

        // Check for parameters inherited from callingProcess implementing a
        // process interface.
        // Must use absolute original calling process because
        // ProcessInterfaceUtil
        // uses working copy and the subprocess we were given to inline MAY be
        // from a copy package for auto inline
        Process originalCallingProcess =
                InlineSubProcessUtil.getOriginalProcess(originalPkg,
                        callingProcess,
                        idMap);

        ProcessInterface callProcImplementedInterface =
                ProcessInterfaceUtil
                        .getImplementedProcessInterface(originalCallingProcess);
        if (callProcImplementedInterface != null) {
            for (Iterator iter =
                    callProcImplementedInterface.getFormalParameters()
                            .iterator(); iter.hasNext();) {
                ProcessRelevantData param = (ProcessRelevantData) iter.next();
                callingFields.put(param.getName(), param);

                String displayName = Xpdl2ModelUtil.getDisplayName(param);
                if (displayName != null && displayName.length() > 0) {
                    callingFieldDisplayNames.put(displayName, param);
                }
            }
        }

        // Add the fields from the calling process to calling fields.

        for (Iterator iter = callingProcess.getDataFields().iterator(); iter
                .hasNext();) {
            ProcessRelevantData field = (ProcessRelevantData) iter.next();
            callingFields.put(field.getName(), field);

            String displayName = Xpdl2ModelUtil.getDisplayName(field);
            if (displayName != null && displayName.length() > 0) {
                callingFieldDisplayNames.put(displayName, field);
            }
        }

        // Only include package fields if different package.
        if (callingProcess.getPackage() != subProcess.getPackage()) {
            for (Iterator iter = callingProcess.getDataFields().iterator(); iter
                    .hasNext();) {
                ProcessRelevantData field = (ProcessRelevantData) iter.next();
                callingFields.put(field.getName(), field);

                String displayName = Xpdl2ModelUtil.getDisplayName(field);
                if (displayName != null && displayName.length() > 0) {
                    callingFieldDisplayNames.put(displayName, field);
                }
            }
        }

        //
        // Get fields / params available to sub-process.
        //
        List<ProcessRelevantData> subProcFields =
                new ArrayList<ProcessRelevantData>();

        // Params from latest copy of sub-process (may have added some from
        // nested inlined sub-procs so must use latest copy).
        for (Iterator iter = subProcess.getFormalParameters().iterator(); iter
                .hasNext();) {
            ProcessRelevantData param = (ProcessRelevantData) iter.next();
            subProcFields.add(param);
        }

        // If subprocess implements a process interface then pick up interface
        // params.

        // Must use absolute original subprocess because ProcessInterfaceUtil
        // uses working copy and the subprocess we were given to inline MAY be
        // from a copy package for auto inline
        Process originalSubProcess =
                InlineSubProcessUtil.getOriginalProcess(originalPkg,
                        subProcess,
                        idMap);

        ProcessInterface subProcImplementedInterface =
                ProcessInterfaceUtil
                        .getImplementedProcessInterface(originalSubProcess);
        if (subProcImplementedInterface != null) {
            for (Iterator iter =
                    subProcImplementedInterface.getFormalParameters()
                            .iterator(); iter.hasNext();) {
                ProcessRelevantData param = (ProcessRelevantData) iter.next();
                subProcFields.add(param);
            }
        }

        // And the data fields.
        for (Iterator iter = subProcess.getDataFields().iterator(); iter
                .hasNext();) {
            ProcessRelevantData field = (ProcessRelevantData) iter.next();
            subProcFields.add(field);
        }

        // Only include package fields if different package.
        if (callingProcess.getPackage() != subProcess.getPackage()) {
            for (Iterator iter =
                    subProcess.getPackage().getDataFields().iterator(); iter
                    .hasNext();) {
                ProcessRelevantData field = (ProcessRelevantData) iter.next();
                subProcFields.add(field);
            }
        }

        //
        // Get a map of sub-proc formal params that have been mapped to
        // (from the sub-proc task) to the calling process field that they're
        // mapped to.
        Map<String, ProcessRelevantData> mappedSubProcParams =
                new HashMap<String, ProcessRelevantData>();

        SubFlow subFlow = (SubFlow) subProcessTask.getImplementation();
        EList dataMappings = subFlow.getDataMappings();
        for (Iterator iter = dataMappings.iterator(); iter.hasNext();) {
            DataMapping dataMap = (DataMapping) iter.next();

            String callingFieldName = dataMap.getActual().getText();

            if (callingFields.containsKey(callingFieldName)) {
                mappedSubProcParams.put(dataMap.getFormal(), callingFields
                        .get(callingFieldName));
            }
        }

        //
        // Create copies of fields that need to be copied up to calling process.
        // And a map of original field to new field.
        // If its a formal param in sub-process that is mapped from calling task
        // then map the original sub-proc param to the field it was mapped to
        // from in the calling proc.
        Map<ProcessRelevantData, ProcessRelevantData> fieldMap =
                new HashMap<ProcessRelevantData, ProcessRelevantData>();

        //
        // MUST sort the list of fields we're moving up to calling proc. Only
        // because when duplicate field names appear in multiple levels of
        // nesting we want to be consistent in the order they are processed so
        // the same sub-sub-process field always becomes the same sequence
        // numbered main process field
        Collections.sort(subProcFields, new Comparator<ProcessRelevantData>() {

            public int compare(ProcessRelevantData o1, ProcessRelevantData o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (Iterator iter = subProcFields.iterator(); iter.hasNext();) {
            ProcessRelevantData subProcData = (ProcessRelevantData) iter.next();

            // If its a mapped param then add entry to map it to the calling
            // proc field it was mapped from.
            if (mappedSubProcParams.containsKey(subProcData.getName())) {
                fieldMap.put(subProcData, mappedSubProcParams.get(subProcData
                        .getName()));

            } else {
                // If we're moving a field up to calling proc then always take a
                // copy.
                ProcessRelevantData newData =
                        (ProcessRelevantData) Xpdl2ModelUtil
                                .createEObjectCopy(editingDomain, subProcData);

                // Give new data a new Id - to keep it consistent EVERY time we
                // re-optimise same source package then we can just use...
                // <original field ID>_<Task Id>.
                // We add TaskId to it because multiple calls to same
                // sub-process will create multiple instances of the data field
                // and all must have different id's.
                newData.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                        subProcData.getId() + subProcessTask.getId());

                // If there is already a field in the calling process with same
                // name then rename the field.
                if (callingFields.containsKey(newData.getName())) {
                    // Take our base name as everything up to trailing digits.
                    String baseName =
                            subProcData.getName().replaceAll("[0-9]*$", ""); //$NON-NLS-1$ //$NON-NLS-2$

                    int suffix = 1;
                    do {
                        suffix++;

                        newData.setName(baseName + suffix);
                    } while (callingFields.containsKey(newData.getName()));
                }

                // And do the same for display name!
                String newDataDisplayName =
                        Xpdl2ModelUtil.getDisplayName(newData);
                if (callingFieldDisplayNames.containsKey(newDataDisplayName)) {
                    // Take our base name as everything up to trailing digits.
                    String baseName =
                            Xpdl2ModelUtil.getDisplayName(subProcData)
                                    .replaceAll("[0-9]*$", ""); //$NON-NLS-1$ //$NON-NLS-2$

                    String generatedDisplayName;
                    int suffix = 1;
                    do {
                        suffix++;

                        generatedDisplayName = baseName + suffix;

                    } while (callingFieldDisplayNames
                            .containsKey(generatedDisplayName));

                    Xpdl2ModelUtil.setOtherAttribute(newData,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            generatedDisplayName);

                }

                // 
                // Map from original to new.
                fieldMap.put(subProcData, newData);

                // We now MUST add our new data to the callingFields map so that
                // the next time round, it will be taken into account when
                // deciding a sequence number.
                // i.e. If sub-proc has 2 fields "DuplicateField2" and
                // "DuplicateField" and calling proc has "DuplicateField"
                // then...
                // - DuplicateField2 isn't a duplicate in calling proc so gets
                // moved up as is.
                // - DuplicateField IS a duplicate in calling proc so have to
                // re-sequence it. So we must account for the fact that we're
                // ABOUT to put DuplicateField2 up already so it must be called
                // DuplicateField3
                //
                // We'll put NULL as value because it isn't really a calling
                // proc field yet!
                callingFields.put(newData.getName(), null);

                String displayName = Xpdl2ModelUtil.getDisplayName(newData);
                if (displayName.length() > 0) {
                    callingFieldDisplayNames.put(displayName, null);
                }

            }
        }

        return fieldMap;
    }

    /**
     * This method is designed to be used in conjunction with the
     * getSubProcFields() method in this class.
     * <p>
     * It gets a command to add any sub-process fields to the calling process
     * and...
     * 
     * <li>For sub-process parameters that were mapped from calling process
     * data, replace refs by name/id to calling proc field.</li>
     * 
     * <li>Add sub-proc data fields (and unmapped parameters) to calling
     * process, replacing name/id references if they had to be renamed</li>
     * 
     * @param editingDomain
     * @param callingProcess
     * @param subProcess
     * @param pastedObjects
     *            The list of copies of sub-proc objects copied up from
     *            sub-process
     * @param fieldMap
     *            The map of fields returned by getSubProcFields() that will
     *            tell this mthod whether to copy a sub-process field or replace
     *            references to a mapped sub-process parameter to the calling
     *            process field that it was mapped from.
     * 
     * @return The command to perform the actions described above.
     */
    public static CompoundCommand getAddFieldsToCallingProcCommand(
            EditingDomain editingDomain, Process callingProcess,
            Process subProcess, Collection pastedObjects,
            Map<ProcessRelevantData, ProcessRelevantData> fieldMap) {

        CompoundCommand cmd = new CompoundCommand();

        //
        // Add the fields that need to be created in calling process
        // (These are the ones that aren't mapped from calling proc fields and
        // hence we will have created copies
        // and hence won't have parent containers).
        ArrayList<ProcessRelevantData> addParams =
                new ArrayList<ProcessRelevantData>();
        ArrayList<ProcessRelevantData> addFields =
                new ArrayList<ProcessRelevantData>();
        ArrayList<ProcessRelevantData> addFieldsAndParams =
                new ArrayList<ProcessRelevantData>();

        for (ProcessRelevantData data : fieldMap.values()) {
            if (data.eContainer() == null) {
                addFieldsAndParams.add(data);

                if (data instanceof FormalParameter) {
                    addParams.add(data);
                } else if (data instanceof DataField) {
                    addFields.add(data);
                }
            }
        }

        // If sub-process is in different package then we must copy up any
        // referenced type declarations.
        Command typeDeclCmd =
                resolveTypeDeclarations(editingDomain,
                        callingProcess.getPackage(),
                        subProcess.getPackage(),
                        addFieldsAndParams);
        if (typeDeclCmd != null) {
            cmd.append(typeDeclCmd);
        }

        // Sort the fields/params by name to give a consistent result in output
        // (means that testing will be simpler cos can just compare new xpdl
        // with previous in testspec).
        Collections.sort(addParams, new Comparator<ProcessRelevantData>() {
            public int compare(ProcessRelevantData o1, ProcessRelevantData o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        Collections.sort(addFields, new Comparator<ProcessRelevantData>() {
            public int compare(ProcessRelevantData o1, ProcessRelevantData o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        // Add any unmapped formal parameters from the sub-proc to the calling
        // proc.
        if (addParams.size() > 0) {
            cmd.append(AddCommand.create(editingDomain,
                    callingProcess,
                    Xpdl2Package.eINSTANCE
                            .getFormalParametersContainer_FormalParameters(),
                    addParams));
        }

        // Add the sub-process fields to the calling process.
        if (addFields.size() > 0) {
            cmd.append(AddCommand.create(editingDomain,
                    callingProcess,
                    Xpdl2Package.eINSTANCE.getDataFieldsContainer_DataFields(),
                    addFields));
        }

        //
        // Get maps of old id's to new id's and old names to new names (if
        // changing).
        Map<String, String> fieldIdMap = new HashMap<String, String>();
        Map<String, String> fieldNameMap = new HashMap<String, String>();

        for (Iterator iterator = fieldMap.entrySet().iterator(); iterator
                .hasNext();) {
            Entry<ProcessRelevantData, ProcessRelevantData> entry =
                    (Entry<ProcessRelevantData, ProcessRelevantData>) iterator
                            .next();

            // Only replace ref by id if id is changing (which it almost
            // certainly will be - as we even copy fields that don't need
            // renaming.
            String keyId = entry.getKey().getId();
            String valueId = entry.getValue().getId();

            if (!keyId.equals(valueId)) {
                fieldIdMap.put(keyId, valueId);
            }

            String keyName = entry.getKey().getName();
            String valueName = entry.getValue().getName();

            if (!keyName.equals(valueName)) {
                fieldNameMap.put(keyName, valueName);
            }
        }

        // Grab list of transitions and Activities
        List<Activity> activities = new ArrayList<Activity>();
        List<Transition> transitions = new ArrayList<Transition>();

        for (Iterator iterator = pastedObjects.iterator(); iterator.hasNext();) {
            Object o = (Object) iterator.next();
            if (o instanceof Activity) {
                activities.add((Activity) o);
            } else if (o instanceof Transition) {
                transitions.add((Transition) o);
            } else if (o instanceof ActivitySet) {
                ActivitySet actSet = (ActivitySet) o;

                if (actSet.getActivities().size() > 0) {
                    activities.addAll(actSet.getActivities());
                }

                if (actSet.getTransitions().size() > 0) {
                    transitions.addAll(actSet.getTransitions());
                }
            }
        }

        // 
        // Replace references by id to fields whose id's are changing.
        //
        if (fieldIdMap.size() > 0) {
            Xpdl2FieldOrParamReplacer idReplacer =
                    new Xpdl2FieldOrParamReplacer(fieldIdMap, true);

            for (Activity act : activities) {
                Command replaceCmd =
                        idReplacer
                                .getReplaceFieldReferencesCommand(editingDomain,
                                        act);
                if (replaceCmd != null) {
                    cmd.append(replaceCmd);
                }
            }

            for (Transition trans : transitions) {
                Command replaceCmd =
                        idReplacer
                                .getReplaceFieldReferencesCommand(editingDomain,
                                        trans);
                if (replaceCmd != null) {
                    cmd.append(replaceCmd);
                }
            }
        }

        // 
        // Replace references by name to fields whose names's are changing.
        //
        if (fieldNameMap.size() > 0) {
            Xpdl2FieldOrParamReplacer nameReplacer =
                    new Xpdl2FieldOrParamReplacer(fieldNameMap, false);

            for (Activity act : activities) {
                Command replaceCmd =
                        nameReplacer
                                .getReplaceFieldReferencesCommand(editingDomain,
                                        act);
                if (replaceCmd != null) {
                    cmd.append(replaceCmd);
                }
            }
            for (Transition trans : transitions) {
                Command replaceCmd =
                        nameReplacer
                                .getReplaceFieldReferencesCommand(editingDomain,
                                        trans);
                if (replaceCmd != null) {
                    cmd.append(replaceCmd);
                }
            }
        }

        return cmd;
    }

    /**
     * When we are copy fields/param from inline sub-process in a different
     * package then they may reference type declarations in that package.
     * <p>
     * If so we need to copy the type declarations too (if they do not already
     * exist - by name - in the calling process's package).
     * 
     * @param editingDomain
     * @param tgtPackage
     * @param srcPackage
     * @param addFieldsAndParams
     * 
     * @return Command to add any new type decls from sub-proc package or re-use
     *         existing target package type decls by changing refs in the
     *         sub-proc fields we are copying to calling process.
     */
    private static Command resolveTypeDeclarations(EditingDomain editingDomain,
            Package tgtPackage, Package srcPackage,
            List<ProcessRelevantData> addFieldsAndParams) {

        if (srcPackage == tgtPackage) {
            // sub-process and calling process share same package type
            // declarations.
            return null;
        }

        // Get a set of type declarations that are referenced from the
        // datafields that have to be added.
        Map<String, TypeDeclaration> referencedSourceTypes =
                getReferencedTypes(addFieldsAndParams, srcPackage);

        if (referencedSourceTypes.size() > 0) {
            CompoundCommand cmd = new CompoundCommand();

            // Get a list of types, fields and params that are
            // based on type declarations
            List<EObject> typedFieldsParamsAndTypes = new ArrayList<EObject>();

            for (Iterator iter = addFieldsAndParams.iterator(); iter.hasNext();) {
                Object obj = (Object) iter.next();

                if (obj instanceof TypeDeclaration) {
                    TypeDeclaration type = (TypeDeclaration) obj;

                    if (type.getDeclaredType() != null) {
                        typedFieldsParamsAndTypes.add(type);
                    }

                } else if (obj instanceof ProcessRelevantData) {
                    ProcessRelevantData data = (ProcessRelevantData) obj;

                    if (data.getDataType() instanceof DeclaredType) {
                        typedFieldsParamsAndTypes.add(data);
                    }
                }
            }

            // Get a map of existing types in target package.
            Map<String, TypeDeclaration> existingTypes =
                    new HashMap<String, TypeDeclaration>();

            for (Iterator iter = tgtPackage.getTypeDeclarations().iterator(); iter
                    .hasNext();) {
                TypeDeclaration type = (TypeDeclaration) iter.next();

                existingTypes.put(type.getName(), type);
            }

            //
            // Go thru pasted types checking for existing with same name in
            // pkg.
            for (Entry<String, TypeDeclaration> srcTypeEntry : referencedSourceTypes
                    .entrySet()) {
                TypeDeclaration tgtType =
                        existingTypes.get(srcTypeEntry.getKey());

                if (tgtType == null) {
                    // No existing type of same name, copy it into target
                    // pkg.
                    TypeDeclaration newType =
                            (TypeDeclaration) Xpdl2ModelUtil
                                    .createEObjectCopy(editingDomain,
                                            srcTypeEntry.getValue());

                    cmd.append(AddCommand.create(editingDomain,
                            tgtPackage,
                            Xpdl2Package.eINSTANCE
                                    .getPackage_TypeDeclarations(),
                            newType));

                } else {
                    // Already have a type of this name, use the existing
                    // one - so have to swap pasted data fields / type decls
                    // to use the existing type.
                    String srcTypeId = srcTypeEntry.getValue().getId();

                    for (EObject obj : typedFieldsParamsAndTypes) {
                        if (obj instanceof ProcessRelevantData) {
                            ProcessRelevantData data =
                                    (ProcessRelevantData) obj;

                            DeclaredType declaredType =
                                    ((DeclaredType) data.getDataType());
                            String refdSrcTypeId =
                                    declaredType.getTypeDeclarationId();

                            if (srcTypeId.equals(refdSrcTypeId)) {
                                // swap the field that refs type to existing
                                // type's id.
                                cmd
                                        .append(SetCommand
                                                .create(editingDomain,
                                                        declaredType,
                                                        Xpdl2Package.eINSTANCE
                                                                .getDeclaredType_TypeDeclarationId(),
                                                        tgtType.getId()));
                            }

                        } else if (obj instanceof TypeDeclaration) {
                            TypeDeclaration type = (TypeDeclaration) obj;

                            DeclaredType declaredType = type.getDeclaredType();

                            String refdSrcTypeId =
                                    declaredType.getTypeDeclarationId();

                            if (srcTypeId.equals(refdSrcTypeId)) {
                                // swap the type decl that refs type to
                                // existing type's id.
                                cmd
                                        .append(SetCommand
                                                .create(editingDomain,
                                                        declaredType,
                                                        Xpdl2Package.eINSTANCE
                                                                .getDeclaredType_TypeDeclarationId(),
                                                        tgtType.getId()));
                            }
                        }

                    }
                }
            }

            if (cmd.getCommandList().size() > 0) {
                return cmd;
            }
        }

        return null;
    }

    /**
     * Return a list of type declarations that are referenced by the given set
     * of data fields.
     * 
     * @param copyData
     * @return map of name to type
     */
    private static Map<String, TypeDeclaration> getReferencedTypes(
            Collection<ProcessRelevantData> copyData, Package srcPackage) {
        HashMap<String, TypeDeclaration> types =
                new HashMap<String, TypeDeclaration>();

        for (ProcessRelevantData data : copyData) {
            if (srcPackage == null) {
                srcPackage = Xpdl2ModelUtil.getPackage(data);
            }

            if (srcPackage != null) {
                TypeDeclaration declType = getDeclaredType(srcPackage, data);

                if (declType != null) {
                    types.put(declType.getName(), declType);

                    // Make sure we don't do infinite loop if there's an cycle
                    // in declared types.
                    Set<TypeDeclaration> doneTypes =
                            new HashSet<TypeDeclaration>();
                    doneTypes.add(declType);

                    // If this type declaration is based upon another type
                    // declaration then go down thru picking all the nested
                    // ones up.
                    TypeDeclaration nestedType = declType;
                    do {
                        nestedType = getDeclaredType(srcPackage, nestedType);
                        if (doneTypes.contains(nestedType)) {
                            // Already done this one! there's a cycle in type
                            // declarations, get out now.
                            break;
                        }

                        if (nestedType != null) {
                            types.put(nestedType.getName(), nestedType);
                        }
                    } while (nestedType != null);

                }
            }
        }

        return types;
    }

    /**
     * Get the type declaration used by the given data field / formal param or
     * Type declaration from the given package.
     * 
     * @param typeDeclsContainerPkg
     * @param fieldParamOrTypeDecl
     * @return type declaration or null if given field/param/declaration does
     *         not reference a type declaration.
     */
    private static TypeDeclaration getDeclaredType(
            Package typeDeclsContainerPkg, EObject fieldParamOrTypeDecl) {
        TypeDeclaration typeDecl = null;
        if (fieldParamOrTypeDecl instanceof ProcessRelevantData) {
            ProcessRelevantData data =
                    (ProcessRelevantData) fieldParamOrTypeDecl;

            DataType dataType = data.getDataType();

            if (dataType instanceof DeclaredType) {
                DeclaredType declType = (DeclaredType) dataType;

                String typeId = declType.getTypeDeclarationId();

                typeDecl = typeDeclsContainerPkg.getTypeDeclaration(typeId);
            }

        } else if (fieldParamOrTypeDecl instanceof TypeDeclaration) {
            TypeDeclaration type = (TypeDeclaration) fieldParamOrTypeDecl;

            DeclaredType declType = type.getDeclaredType();
            if (declType != null) {
                String typeId = declType.getTypeDeclarationId();

                typeDecl = typeDeclsContainerPkg.getTypeDeclaration(typeId);
            }
        }

        return typeDecl;
    }

}
