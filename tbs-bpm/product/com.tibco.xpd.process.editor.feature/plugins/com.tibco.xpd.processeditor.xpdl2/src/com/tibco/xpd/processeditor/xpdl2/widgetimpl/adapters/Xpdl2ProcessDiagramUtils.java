/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil.ProjectReference;
import com.tibco.xpd.analyst.resources.xpdl2.propertytesters.XpdlFileContentPropertyTester;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.CopyPasteScope;
import com.tibco.xpd.processwidget.adapters.ProcessPasteCommand;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Application;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Assignment;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.PartnerLink;
import com.tibco.xpd.xpdl2.PartnerLinkType;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamReplacer;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamResolver;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2ParticipantReferenceResolver;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.LocalPackageProcessInterfaceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author aallway
 * 
 */
public class Xpdl2ProcessDiagramUtils {

    /**
     * Get the command to paste copies of the given the model objects and add
     * them to the local model.
     * 
     * @param ed
     * @param tgtProcess
     * @param targetObject
     *            Pool/Lane etc
     * @param location
     * @param pasteObjects
     * @param noSetLocation
     *            The caller has taken on the responsibility of setting the
     *            location of the added diagram objects.
     * 
     * @return the command to paste copies of the given the model objects and
     *         add them to the local model.
     * @deprecated Use getAddDiagramObjectsCommand( EditingDomain ed, Process
     *             tgtProcess, Object targetObject, Point origLocation,
     *             Collection pasteObjects, String uniqueIdSuffix, boolean
     *             noSetLocation, boolean handleProjectReferences) instead
     */
    @Deprecated
    public static ProcessPasteCommand getAddDiagramObjectsCommand(
            EditingDomain ed, Process tgtProcess, Object targetObject,
            Point location, Collection pasteObjects, boolean noSetSelection) {
        return getAddDiagramObjectsCommand(ed,
                tgtProcess,
                targetObject,
                location,
                pasteObjects,
                null,
                noSetSelection);
    }

    /**
     * Get the command to paste copies of the given the model objects and add
     * them to the local model.
     * 
     * @param ed
     * @param tgtProcess
     * @param targetObject
     *            Pool/Lane etc
     * @param location
     * @param pasteObjects
     * 
     * @return the command to paste copies of the given the model objects and
     *         add them to the local model.
     * @deprecated Use getAddDiagramObjectsCommand( EditingDomain ed, Process
     *             tgtProcess, Object targetObject, Point origLocation,
     *             Collection pasteObjects, String uniqueIdSuffix, boolean
     *             noSetLocation, boolean handleProjectReferences) instead
     */
    @Deprecated
    public static ProcessPasteCommand getAddDiagramObjectsCommand(
            EditingDomain ed, Process tgtProcess, Object targetObject,
            Point location, Collection pasteObjects) {
        return getAddDiagramObjectsCommand(ed,
                tgtProcess,
                targetObject,
                location,
                pasteObjects,
                null);
    }

    /**
     * Get the command to paste copies of the given the model objects and add
     * them to the local model.
     * 
     * @param ed
     * @param tgtProcess
     * @param targetObject
     *            Pool/Lane etc
     * @param location
     * @param pasteObjects
     * @param uniqueIdSuffix
     *            If not null then create new unique id's based on the existing
     *            id PLUS this suffix. (This allows the caller to maintain
     *            consistent id's over multiple executions). If null, then the
     *            added objects will be given completely new unique id's.
     * 
     * @return the command to paste copies of the given the model objects and
     *         add them to the local model.
     * @deprecated Use getAddDiagramObjectsCommand( EditingDomain ed, Process
     *             tgtProcess, Object targetObject, Point origLocation,
     *             Collection pasteObjects, String uniqueIdSuffix, boolean
     *             noSetLocation, boolean handleProjectReferences) instead
     */
    @Deprecated
    public static ProcessPasteCommand getAddDiagramObjectsCommand(
            EditingDomain ed, Process tgtProcess, Object targetObject,
            Point origLocation, Collection pasteObjects, String uniqueIdSuffix) {
        return getAddDiagramObjectsCommand(ed,
                tgtProcess,
                targetObject,
                origLocation,
                pasteObjects,
                uniqueIdSuffix,
                false);
    }

    /**
     * Get the command to paste copies of the given the model objects and add
     * them to the local model.
     * 
     * @param ed
     * @param tgtProcess
     * @param targetObject
     *            Pool/Lane etc
     * @param location
     * @param pasteObjects
     * @param uniqueIdSuffix
     *            If not null then create new unique id's based on the existing
     *            id PLUS this suffix. (This allows the caller to maintain
     *            consistent id's over multiple executions). If null, then the
     *            added objects will be given completely new unique id's.
     * @param noSetLocation
     *            The caller has taken on the responsibility of setting the
     *            location of the added diagram objects.
     * 
     * @return the command to paste copies of the given the model objects and
     *         add them to the local model.
     * @deprecated Use getAddDiagramObjectsCommand( EditingDomain ed, Process
     *             tgtProcess, Object targetObject, Point origLocation,
     *             Collection pasteObjects, String uniqueIdSuffix, boolean
     *             noSetLocation, boolean handleProjectReferences) instead
     */
    @Deprecated
    public static ProcessPasteCommand getAddDiagramObjectsCommand(
            EditingDomain ed, Process tgtProcess, Object targetObject,
            Point origLocation, Collection pasteObjects, String uniqueIdSuffix,
            boolean noSetLocation) {
        return getAddDiagramObjectsCommand(ed,
                tgtProcess,
                targetObject,
                origLocation,
                pasteObjects,
                uniqueIdSuffix,
                noSetLocation,
                false);
    }

    /**
     * Get the command to paste copies of the given the model objects and add
     * them to the local model.
     * 
     * @param ed
     * @param tgtProcess
     * @param targetObject
     *            Pool/Lane etc
     * @param location
     * @param pasteObjects
     * @param uniqueIdSuffix
     *            If not null then create new unique id's based on the existing
     *            id PLUS this suffix. (This allows the caller to maintain
     *            consistent id's over multiple executions). If null, then the
     *            added objects will be given completely new unique id's.
     * @param noSetLocation
     *            The caller has taken on the responsibility of setting the
     *            location of the added diagram objects.
     * 
     * @return the command to paste copies of the given the model objects and
     *         add them to the local model.
     */
    public static ProcessPasteCommand getAddDiagramObjectsCommand(
            EditingDomain ed, Process tgtProcess, Object targetObject,
            Point origLocation, Collection pasteObjects, String uniqueIdSuffix,
            boolean noSetLocation, boolean handleProjectReferences) {
        // XPD-3033 check requirement for new project references and ask user
        // get and filter out special
        // "project reference elemen5ts in pasteObejcts" + only use filtered
        // list in rest of code below
        //
        Collection<Object> filteredPasteObjects = new ArrayList<Object>();
        Collection<ProjectReference> filteredProjectRef =
                new ArrayList<ProjectReference>();
        ActionUtil.filterProjectReferencesAndOtherObjects(filteredPasteObjects,
                filteredProjectRef,
                pasteObjects);

        // When construct Xpdl2PasteCommand pass referenced projects in AND rthe
        // target project

        Point location = origLocation != null ? origLocation : new Point(0, 0);

        boolean isTaskLibrary =
                XpdlFileContentPropertyTester.isTasksFileContent(tgtProcess);

        if (isTaskLibrary) {
            if (targetObject instanceof Process
                    && CopyPasteScope.COPY_LANES == getCopyPasteScope(filteredPasteObjects)
                            .getCopyScope()) {
                /*
                 * Redirect paste of lanes into back drop to the last lane in
                 * the invisible pool.
                 */
                Collection<Pool> pools =
                        Xpdl2ModelUtil.getProcessPools(tgtProcess);
                if (!pools.isEmpty()) {
                    targetObject = pools.iterator().next();

                    location.y = Integer.MAX_VALUE;
                }
            }
        } else {
            if (targetObject instanceof Lane
                    && CopyPasteScope.COPY_POOLS == getCopyPasteScope(filteredPasteObjects)
                            .getCopyScope()) {
                /* If copying a pool then set the target as the process */
                targetObject = Xpdl2ModelUtil.getProcess((Lane) targetObject);
            }
        }

        //
        // Find out what the scope of objects is in the copy buffer.
        CopyPasteScope copyScope =
                Xpdl2ProcessDiagramUtils
                        .getCreateDiagramObjectsScope(targetObject,
                                filteredPasteObjects);

        if (copyScope.getCopyScope() != CopyPasteScope.COPY_NONE) {

            // First off get the model objects from clipboard.
            // This can be...
            // - A whole process (actually all pools in a given process).
            // - A collection of pools and associated objects
            // - A collection of lanes and associated objects
            // - A collection of activities and artifacts.
            //

            // NOTE! This copy command just allows us to create a copy of the
            // objects in the clipboard that we can play with and add to our
            // model.
            Command copyCmd = CopyCommand.create(ed, filteredPasteObjects);

            if (!copyCmd.canExecute()) {
                // clipboard doesn't contain any interesting data
                return new Xpdl2ProcessPasteCommand(
                        UnexecutableCommand.INSTANCE, null,
                        new CopyPasteScope(), null);
            }

            copyCmd.execute();
            Collection copyOfPasteObjects = copyCmd.getResult();

            if (isTaskLibrary) {
                copyOfPasteObjects =
                        removeUnwantedElementsForTaskLibrary(copyScope,
                                copyOfPasteObjects);
                if (copyOfPasteObjects.isEmpty()) {
                    // clipboard doesn't contain any interesting data
                    return new Xpdl2ProcessPasteCommand(
                            UnexecutableCommand.INSTANCE, null,
                            new CopyPasteScope(), null);
                }
            }

            //
            // Replace all unique Id's of given objects so that new objects are
            // guaranteed to have unique id's (also fixes references by ID to
            // uid objects (provided that attribute has the ReferenceIDString
            Map<String, EObject> newUniqueIdMap =
                    Xpdl2ModelUtil.reassignUniqueIds(copyOfPasteObjects,
                            ed,
                            uniqueIdSuffix);

            //
            // Ok, now we have to perform any modifications to the objects
            // to ensure proepr consistency for the scope of paste.
            CompoundCommand resultCmd = null;

            Rectangle newOccupiedArea = null;

            if (copyScope.getCopyScope() == CopyPasteScope.COPY_POOLS) {
                // Paste pools
                resultCmd = new CompoundCommand();
                (resultCmd)
                        .setLabel(Messages.Xpdl2ProcessDiagramAdapter_PastePoolsDiagram_menu);

                //
                // You can paste pool(s) either into process or pool
                int insertIndex = 0;
                if (targetObject instanceof Pool) {
                    // Pasting pool(s) above/below target pool.
                    Pool pool = (Pool) targetObject;

                    EList pools = tgtProcess.getPackage().getPools();

                    // Get the height of pool (sum of lanes)
                    int heightBeforePool = 0;

                    for (Iterator iter = pools.iterator(); iter.hasNext();) {
                        Pool p = (Pool) iter.next();

                        if (p == pool) {
                            break;
                        }

                        if (tgtProcess.getId().equals(p.getProcessId())) {
                            heightBeforePool +=
                                    Xpdl2ProcessDiagramUtils.getPoolHeight(p);
                        }
                    }

                    int poolHeight =
                            Xpdl2ProcessDiagramUtils.getPoolHeight(pool);

                    // If location is top half of lane then insert above.
                    // otherwise insert below.
                    insertIndex = pools.indexOf(pool);

                    if (!noSetLocation && location != null && poolHeight > 0) {
                        if ((location.y - heightBeforePool) > (poolHeight / 2)) {
                            insertIndex = pools.indexOf(pool) + 1;
                        }
                    }

                } else {
                    // Insert pool at end of current pools.
                    List procPools =
                            Xpdl2ProcessDiagramUtils
                                    .getProcessPools(tgtProcess);

                    if (procPools.size() > 0) {
                        insertIndex =
                                tgtProcess
                                        .getPackage()
                                        .getPools()
                                        .indexOf(procPools.get(procPools.size() - 1)) + 1;

                    } else {
                        insertIndex = 0;
                    }
                }

                //
                // Set the process id in all the pools...
                String procId = tgtProcess.getId();
                for (Iterator p = copyOfPasteObjects.iterator(); p.hasNext();) {
                    Object obj = p.next();

                    if (obj instanceof Pool) {
                        ((Pool) obj).setProcessId(procId);
                    }
                }

                //
                // Ok, we have found the insertion index.
                // Now add the objects to the model.
                resultCmd =
                        appendCopyElementCommands(ed,
                                tgtProcess,
                                resultCmd,
                                tgtProcess,
                                insertIndex,
                                null,
                                copyOfPasteObjects);

                //
                // END OF PROCESSING PASTE POOLS
                //

            } else if (copyScope.getCopyScope() == CopyPasteScope.COPY_LANES) {
                // Paste Lanes...
                resultCmd = new CompoundCommand();
                (resultCmd)
                        .setLabel(Messages.Xpdl2ProcessDiagramAdapter_PasteLanesDiagram_menu);

                //
                // You can paste lane(s) either into Pool or Lane header.
                // In either case, we need to come up with an index to insert
                // the lane(s) in the appropriate place in list of lanes.
                Pool targetPool = null;
                int insertIndex = 0; // default to insert at top.

                if (targetObject instanceof Pool) {
                    // Pasting into pool...
                    // - If pasting into top half of pool, insert at start
                    // - else append lane(s) to end.
                    targetPool = (Pool) targetObject;

                    if (!noSetLocation && location != null) {
                        // Location is relative to pool.
                        // Get current pool height (sum of lanes).
                        int poolHeight = 0;

                        List lanes = targetPool.getLanes();
                        for (Iterator l = lanes.iterator(); l.hasNext();) {
                            Lane lane = (Lane) l.next();

                            NodeGraphicsInfo gn =
                                    Xpdl2ModelUtil.getNodeGraphicsInfo(lane);
                            if (gn != null) {
                                poolHeight += gn.getHeight();
                            }
                        }

                        if (poolHeight > 0) {
                            if (location.y > (poolHeight / 2)) {
                                // Insert at end of current lanes.
                                insertIndex = lanes.size();
                            }
                        }
                    }

                } else if (targetObject instanceof Lane) {
                    //
                    // Pasting lane above/below target lane.
                    Lane lane = (Lane) targetObject;

                    targetPool = lane.getParentPool();

                    insertIndex = targetPool.getLanes().indexOf(lane);

                    // If location is top half of lane then insert above.
                    // otherwise insert below.
                    NodeGraphicsInfo gn =
                            Xpdl2ModelUtil.getNodeGraphicsInfo(lane);
                    if (!noSetLocation && location != null && gn != null) {
                        if (gn.getHeight() > 0) {
                            if (location.y > (gn.getHeight() / 2)) {
                                insertIndex++;
                            }
                        }
                    }

                } else {
                    // Not a lane and not a pool ARRRGH!
                    return new Xpdl2ProcessPasteCommand(
                            UnexecutableCommand.INSTANCE, null,
                            new CopyPasteScope(), null);
                }

                //
                // Ok, we have found the insertion index.
                // Now add the objects to the model.
                resultCmd =
                        appendCopyElementCommands(ed,
                                tgtProcess,
                                resultCmd,
                                tgtProcess,
                                insertIndex,
                                targetPool,
                                copyOfPasteObjects);

                //
                // END OF PROCESSING PASTE LANES
                //

            } else if (copyScope.getCopyScope() == CopyPasteScope.COPY_ACTIVITIES_AND_ARTIFACTS) {
                // Paste activities and artifacts into either a Lane or Embedded
                // sub-process.

                resultCmd = new CompoundCommand();

                resultCmd
                        .setLabel(Messages.Xpdl2ProcessDiagramAdapter_PasteObjectDiagram_menu);

                // If we need to add an activity set cos pasting
                // into embedde sub-proc that doesn't have one
                // then this will be it.
                ActivitySet newActSet = null;

                //
                // If the target is activity then check then the real target in
                // the model is the activity set.
                Object finalTarget = null;

                if (targetObject instanceof Activity) {
                    Activity act = (Activity) targetObject;

                    // Make sure it's a block activity implementation
                    // note (getPasteObjectsScope() will have verified this
                    // anyway.
                    BlockActivity ba = act.getBlockActivity();
                    if (ba != null) {
                        // get or create the activity set.

                        String actSetId = ba.getActivitySetId();

                        if (actSetId != null) {
                            finalTarget = tgtProcess.getActivitySet(actSetId);
                        }

                        // If we didn't find the activity set then we must
                        // create one.
                        if (finalTarget == null) {
                            newActSet =
                                    Xpdl2Factory.eINSTANCE.createActivitySet();

                            finalTarget = newActSet;
                        }

                    } else {
                        // URK!!! shouldn't get here getPasteObjectsScope()
                        // should have verified against this already!
                        resultCmd = null;
                    }
                } else if (targetObject instanceof Lane) {
                    // It's ok to paste into lane too!
                    finalTarget = targetObject;
                }

                // Check we've really got a target and go for it.
                if (finalTarget != null) {
                    // if location is unavailable, place top left of
                    // lane/activity.
                    if (location == null) {
                        location = new Point(20, 20);
                    }

                    // need to grab a list of activity sets in paste for later.
                    HashSet pasteActSetIds = new HashSet();

                    for (Iterator iter = copyOfPasteObjects.iterator(); iter
                            .hasNext();) {
                        Object obj = iter.next();
                        // need to grab a list of activity sets in paste for
                        // later.
                        if (obj instanceof ActivitySet) {
                            pasteActSetIds.add(((ActivitySet) obj).getId());
                        }

                    }

                    //
                    // Calculate offset for all activities (i.e. margin to top
                    // left activity).
                    double minx = Integer.MAX_VALUE;
                    double miny = Integer.MAX_VALUE;

                    for (Iterator iter = copyOfPasteObjects.iterator(); iter
                            .hasNext();) {
                        Object obj = iter.next();

                        NodeGraphicsInfo gn = null;
                        if (obj instanceof GraphicalNode) {
                            gn =
                                    Xpdl2ModelUtil
                                            .getNodeGraphicsInfo((GraphicalNode) obj);

                        }

                        if (gn != null
                                && !Xpdl2ModelUtil.isEventAttachedToTask(obj)) {
                            // Artifacts will appear in paste objects even if
                            // they
                            // are within activity set. So we don't want to
                            // include
                            // them in calculations if they are in activity set
                            // (their x/y will be within act set already)
                            if (!(obj instanceof Artifact)
                                    || !pasteActSetIds.contains(gn.getLaneId())) {

                                // Use proper getObjectBounds as text annot's
                                // are different offset from normal ones.
                                org.eclipse.swt.graphics.Rectangle bnds =
                                        Xpdl2ModelUtil
                                                .getObjectBounds((GraphicalNode) obj);

                                Coordinates coords = gn.getCoordinates();
                                if (coords != null) {
                                    minx = Math.min(minx, bnds.x);
                                    miny = Math.min(miny, bnds.y);
                                }
                            }
                        }

                    }
                    if (minx == Integer.MAX_VALUE || miny == Integer.MAX_VALUE) {
                        minx = 0;
                        miny = 0;
                    }

                    //
                    // Now go thru the activities and artifacts and...
                    // - Reset X/Y co-ordinates.
                    // - Reset the lane Id (if pasting into activity set then
                    // DELETE the existing laneid for activity and set laneid as
                    // activity set id for artifacts).
                    int margin = 5;
                    double maxy = 0;
                    minx = -minx + Math.max(location.x, margin);
                    miny = -miny + Math.max(location.y, margin);

                    for (Iterator iter = copyOfPasteObjects.iterator(); iter
                            .hasNext();) {
                        Object obj = iter.next();

                        NodeGraphicsInfo gn = null;
                        if (obj instanceof GraphicalNode) {
                            gn =
                                    Xpdl2ModelUtil
                                            .getNodeGraphicsInfo((GraphicalNode) obj);
                        }

                        if (gn != null) {
                            // Artifacts will appear in paste objects even if
                            // they are within activity set. So we don't want to
                            // include them in calculations if they are in
                            // activity set (their x/y will be within act set
                            // already)
                            if (!(obj instanceof Artifact)
                                    || !pasteActSetIds.contains(gn.getLaneId())) {

                                if (!Xpdl2ModelUtil.isEventAttachedToTask(obj)) {
                                    // Reset the co-ordinates to offset from
                                    // lane/activityset.
                                    Coordinates coords = gn.getCoordinates();
                                    if (!noSetLocation && coords != null) {
                                        double x =
                                                coords.getXCoordinate() + minx;
                                        double y =
                                                coords.getYCoordinate() + miny;

                                        maxy =
                                                Math.max(maxy,
                                                        y
                                                                + (gn.getHeight() / 2));

                                        coords.setXCoordinate(x);
                                        coords.setYCoordinate(y);

                                        int width = (int) gn.getWidth();
                                        int height = (int) gn.getHeight();

                                        // For text annotation, x coord is left
                                        // edge NOT centre.
                                        if (!(obj instanceof Artifact)
                                                || !ArtifactType.ANNOTATION_LITERAL
                                                        .equals(((Artifact) obj)
                                                                .getArtifactType())) {
                                            x -= width / 2;
                                        }

                                        y -= height / 2;

                                        Rectangle objBnds =
                                                new Rectangle((int) x, (int) y,
                                                        width, height);

                                        if (newOccupiedArea == null) {
                                            newOccupiedArea = objBnds.getCopy();
                                        } else {
                                            newOccupiedArea.union(objBnds);
                                        }

                                    }
                                }

                                if (finalTarget instanceof ActivitySet) {
                                    // If placing in activity set then ditch the
                                    // laneId
                                    if (obj instanceof Artifact) {
                                        gn.setLaneId(((ActivitySet) finalTarget)
                                                .getId());
                                    } else {
                                        gn.setLaneId(null);
                                    }

                                } else {
                                    // Pasting into lane so reset the laneid.
                                    gn.setLaneId(((Lane) finalTarget).getId());

                                }
                            }
                        }
                    } // Set X/Y coords and Lane Id - next object.

                    //
                    // If we had to create a new activity set for a currently
                    // empty embedded subflow then add the commands for that.
                    // Append the command to add the activity set.
                    if (newActSet != null) {
                        // Make sure that the act set gets added before
                        // we set reference to it.
                        copyOfPasteObjects.add(newActSet);

                    }

                    // Last thing of all to do is add all the elements into
                    // correct place in the model hierarchy.
                    resultCmd =
                            appendCopyElementCommands(ed,
                                    tgtProcess,
                                    resultCmd,
                                    (EObject) finalTarget,
                                    CommandParameter.NO_INDEX,
                                    null,
                                    copyOfPasteObjects);

                    if (newActSet != null) {
                        // Finally add / set the activity set reference in
                        // activity in case it wasn't there already.

                        resultCmd.append(SetCommand.create(ed,
                                ((Activity) targetObject).getBlockActivity(),
                                Xpdl2Package.eINSTANCE
                                        .getBlockActivity_ActivitySetId(),
                                newActSet.getId()));
                    }

                }

            } // if CopyScope.COPY_ACTIVITIES_AND_ARTIFACTS
            else if (copyScope.getCopyScope() == CopyPasteScope.COPY_DATA_ONLY) {
                // Only contains fields/params/partics - nothing special to do
                // other than standard stuff.
                resultCmd = new CompoundCommand();
            }

            //
            // Resolve the pasting of participants.
            if (resultCmd != null) {
                resultCmd =
                        resolvePasteParticipants(ed,
                                tgtProcess,
                                resultCmd,
                                copyOfPasteObjects,
                                newUniqueIdMap);

            }

            //
            // Paste in any type declarations used by the fields/params in paste
            // buffer.
            if (resultCmd != null) {
                resultCmd =
                        resolvePasteTypeDeclarations(ed,
                                tgtProcess,
                                resultCmd,
                                copyOfPasteObjects);
            }
            
			//
			// Nikita ACE-7384 Resolve the names of pasted activities in a process
            if (resultCmd != null) {
                resultCmd =
                		resolvePasteActivities(ed,
                                tgtProcess,
                                resultCmd,
                                copyOfPasteObjects);
            }            
            
            //
            // Resolve the pasting of data fields.
            if (resultCmd != null) {
                resultCmd =
                        resolvePasteFields(ed,
                                tgtProcess,
                                targetObject,
                                resultCmd,
                                copyOfPasteObjects,
                                newUniqueIdMap);
            }

            /*
             * resolve the process id in catch error event to replace with
             * actual target process's id. (This was first required for Create
             * Case Data pageflow process fragment selection in the new pageflow
             * process wizard)
             */

            if (null != resultCmd) {

                resultCmd =
                        resolveProcessIdReferences(ed,
                                tgtProcess,
                                resultCmd,
                                copyOfPasteObjects,
                                newUniqueIdMap);
            }

            //
            // Resolve references to processes from independent sub-process call
            // tasks.
            if (resultCmd != null) {
                resultCmd =
                        resolveProcessPkgReferences(ed,
                                tgtProcess,
                                resultCmd,
                                copyOfPasteObjects,
                                newUniqueIdMap);
            }

            // That's it, all done.
            if (resultCmd != null) {
                if (handleProjectReferences) { //
                    return (new Xpdl2ProcessPasteCommand(resultCmd,
                            newOccupiedArea, copyScope, copyOfPasteObjects,
                            filteredProjectRef,
                            WorkingCopyUtil.getProjectFor(tgtProcess)));
                } else {
                    return (new Xpdl2ProcessPasteCommand(resultCmd,
                            newOccupiedArea, copyScope, copyOfPasteObjects));
                }
            }
        }

        return new Xpdl2ProcessPasteCommand(UnexecutableCommand.INSTANCE, null,
                new CopyPasteScope(), null);
    }

    /**
     * resolves the process id in catch error event
     * 
     * @param ed
     * @param tgtProcess
     * @param resultCmd
     * @param copyOfPasteObjects
     * @param newUniqueIdMap
     * @return
     */
    private static CompoundCommand resolveProcessIdReferences(EditingDomain ed,
            Process tgtProcess, CompoundCommand resultCmd,
            Collection<?> copyOfPasteObjects,
            Map<String, EObject> newUniqueIdMap) {

        for (Iterator<?> iter = copyOfPasteObjects.iterator(); iter.hasNext();) {

            EObject obj = (EObject) iter.next();

            if (obj instanceof Activity) {

                Activity activity = (Activity) obj;
                if (activity.getEvent() instanceof IntermediateEvent) {

                    if (activity.getEvent().getEventTriggerTypeNode() instanceof ResultError) {

                        ResultError resError =
                                (ResultError) activity.getEvent()
                                        .getEventTriggerTypeNode();
                        ErrorThrowerInfo errorThrowerInfo =
                                (ErrorThrowerInfo) Xpdl2ModelUtil
                                        .getOtherElement(resError,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ErrorThrowerInfo());
                        if (null != errorThrowerInfo) {

                            if (errorThrowerInfo.getThrowerContainerId() != tgtProcess
                                    .getId()) {

                                errorThrowerInfo
                                        .setThrowerContainerId(tgtProcess
                                                .getId());
                                resultCmd
                                        .append(Xpdl2ModelUtil
                                                .getSetOtherElementCommand(ed,
                                                        resError,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_ErrorThrowerInfo(),
                                                        errorThrowerInfo));
                            }
                        }
                    }
                }
            }
        }

        return resultCmd;
    }

    /**
     * Remove any objects that are not allowed for task libraries.
     * 
     * @param copyOfPasteObjects
     * @return modified list.
     */
    private static Collection removeUnwantedElementsForTaskLibrary(
            CopyPasteScope copyScope, Collection copyOfPasteObjects) {
        List returnList = Collections.EMPTY_LIST;

        // Don't bother with whole pool, task library has no equivalent.
        if (copyScope.getCopyScope() != CopyPasteScope.COPY_POOLS) {
            returnList = new ArrayList<Object>();

            boolean hadNodes = false;

            for (Object o : copyOfPasteObjects) {
                if (o instanceof Artifact || o instanceof Activity) {
                    hadNodes = true;
                }

                if (isAllowedObjectForTaskLibrary(o)) {
                    returnList.add(o);
                }
            }

            //
            // If we had nodes (act's and artifacts) then check that there are
            // actually some activities left to paste. User may have intended to
            // copy whole lane, but ended up with nothing.
            //
            // Don't check if we never had nodes in the first place so that this
            // will allow copy/paste of an empty task set.
            if (hadNodes) {
                boolean nodesLeft = false;
                for (Object o : returnList) {
                    if (o instanceof Artifact || o instanceof Activity) {
                        nodesLeft = true;
                        break;
                    }
                }

                if (!nodesLeft) {
                    returnList = Collections.emptyList();
                }
            }
        }

        return returnList;
    }

    /**
     * Currently disallowed objects for task library are: <li>Pool <li>
     * MessageFlow <li>SequenceFlow <li>ActivitySet <li>Event <li>Gateway <li>
     * Embedded Sub-Process <li>Reference Task <li>Receive Task.
     * 
     * @param o
     * 
     * @return true if the object is allowed in task library model.
     */
    private static boolean isAllowedObjectForTaskLibrary(Object o) {
        if (o instanceof Pool || o instanceof MessageFlow
                || o instanceof Transition || o instanceof ActivitySet) {
            return false;

        } else if (o instanceof Activity) {
            Activity act = (Activity) o;

            if (act.getEvent() != null || act.getRoute() != null) {
                // Don't have events and gateways in task library.
                return false;
            }

            TaskType taskType = TaskObjectUtil.getTaskType(act);

            /*
             * ABPM-911: Saket: An event subprocess should mostly behave like an
             * embedded sub-process.
             */
            if (TaskType.REFERENCE_LITERAL.equals(taskType)
                    || TaskType.RECEIVE_LITERAL.equals(taskType)
                    || TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(taskType)
                    || TaskType.EVENT_SUBPROCESS_LITERAL.equals(taskType)) {
                return false;
            }
        }

        return true;
    }

	/**
	 * ACE-7384 Returns the new name for an element based on the existence of other elements in a container
	 * 
	 * @param originalName
	 * @param existingElements
	 * @return
	 */
	private static String getCopyOfPasteName(String originalName, List< ? extends NamedElement> existingElements)
	{
		if (originalName == null)
		{
			originalName = ""; //$NON-NLS-1$
		}

		Set<String> existingNames = new HashSet<String>();

		for (NamedElement el : existingElements)
		{
			String name = el.getName();
			if (name == null || name.length() == 0)
			{
				name = "?"; //$NON-NLS-1$
			}
			existingNames.add(name);
		}

		String finalName = originalName;
		if (existingNames.contains(finalName))
		{
			// Try Copy_Of_ first.
			finalName = Messages.CopyOf_tokenNoSpaces + finalName;

			int idx = 1;

			while (existingNames.contains(finalName))
			{
				// Already a CopyOf... use a sequence number.
				idx++;
				finalName = String.format(Messages.CopyNOf_tokenNoSpaces, idx) + originalName;
			}
		}

		return finalName;
	}

	/**
	 * ACE-7384 Returns the new display for an element based on the existence of other elements in a container
	 * 
	 * @param originalName
	 * @param existingElements
	 * @return
	 */
	private static String getCopyOfPasteDisplayName(String originalName,
			Collection< ? extends NamedElement> existingElements)
	{
		if (originalName == null)
		{
			originalName = ""; //$NON-NLS-1$
		}

		Set<String> existingNames = new HashSet<String>();

		for (NamedElement el : existingElements)
		{
			String name = Xpdl2ModelUtil.getDisplayNameOrName(el);
			if (name == null || name.length() == 0)
			{
				name = "?"; //$NON-NLS-1$
			}
			existingNames.add(name);
		}

		String finalName = originalName;
		if (existingNames.contains(finalName))
		{
			// Try Copy_Of_ first.
			finalName = Messages.CopyOf_tokenNoSpaces + finalName;

			int idx = 1;

			while (existingNames.contains(finalName))
			{
				// Already a CopyOf... use a sequence number.
				idx++;
				finalName = String.format(Messages.CopyNOf_tokenNoSpaces, idx) + originalName;
			}
		}

		return finalName;
	}

	/**
	 * ACE-7384 Resolves the name of an activity being pasted in a Process to avoid the same name conflict
	 * 
	 * @param ed
	 * @param tgtProcess
	 * @param resultCmd
	 * @param copyOfPasteObjects
	 * @return
	 */
	private static CompoundCommand resolvePasteActivities(EditingDomain ed, Process tgtProcess,
			CompoundCommand resultCmd, Collection copyOfPasteObjects)
	{
		List<NamedElement> activities = new ArrayList<NamedElement>();
		activities.addAll(tgtProcess.getActivities());

		for (Iterator iter = copyOfPasteObjects.iterator(); iter.hasNext();)
		{
			Object obj = iter.next();

			if (obj instanceof Activity)
			{
				Activity activity = ((Activity) obj);
				String name = getCopyOfPasteName(activity.getName(), activities);
				EAttribute ea = XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName();
				String token = getCopyOfPasteDisplayName((String) Xpdl2ModelUtil.getOtherAttribute(activity, ea),
						activities);

				activity.setName(name);
				Xpdl2ModelUtil.setOtherAttribute(activity, ea, token);
			}
		}

		return resultCmd;

	}
    /**
     * Paste type declarations in the copyOfPasteObjects into the target
     * process. If a type declaration already exists in the target process with
     * the same name then it is re-used and references to it from fields in the
     * copy paste objects will be replaced.
     * <p>
     * Otherwise the type declaration is added to dest process pkg if its not
     * there already.
     * 
     * @param ed
     * 
     * @param tgtProcess
     * @param resultCmd
     * @param copyOfPasteObjects
     * @return
     */
    private static CompoundCommand resolvePasteTypeDeclarations(
            EditingDomain ed, Process tgtProcess, CompoundCommand resultCmd,
            Collection copyOfPasteObjects) {

        // Get a map of pasted type name to type and fields/params that are
        // based on type declarations
        Map<String, TypeDeclaration> pasteTypes =
                new HashMap<String, TypeDeclaration>();
        List<EObject> pasteTypedFieldsAndTypes = new ArrayList<EObject>();

        for (Iterator iter = copyOfPasteObjects.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof TypeDeclaration) {
                TypeDeclaration type = (TypeDeclaration) obj;

                pasteTypes.put(type.getName(), type);

                if (type.getDeclaredType() != null) {
                    pasteTypedFieldsAndTypes.add(type);
                }

            } else if (obj instanceof ProcessRelevantData) {
                ProcessRelevantData data = (ProcessRelevantData) obj;

                if (data.getDataType() instanceof DeclaredType) {
                    pasteTypedFieldsAndTypes.add(data);
                }
            }
        }

        if (pasteTypes.size() > 0) {
            // Get a map of existing types in dst package.
            Package tgtPackage = tgtProcess.getPackage();
            if (tgtPackage != null) {
                Map<String, TypeDeclaration> pkgTypes =
                        new HashMap<String, TypeDeclaration>();

                for (Iterator iter =
                        tgtPackage.getTypeDeclarations().iterator(); iter
                        .hasNext();) {
                    TypeDeclaration type = (TypeDeclaration) iter.next();

                    pkgTypes.put(type.getName(), type);
                }

                //
                // Go thru pasted types checking for existing with same name in
                // pkg.
                for (Entry<String, TypeDeclaration> pasteType : pasteTypes
                        .entrySet()) {
                    TypeDeclaration pkgType = pkgTypes.get(pasteType.getKey());

                    if (pkgType == null) {
                        // No existing type of same name, copy it into target
                        // pkg.
                        resultCmd.append(AddCommand.create(ed,
                                tgtPackage,
                                Xpdl2Package.eINSTANCE
                                        .getPackage_TypeDeclarations(),
                                pasteType.getValue()));

                    } else {
                        // Already have a type of this name, use the existing
                        // one - so have to swap pasted data fields / type decls
                        // to use the existing type.
                        String pasteTypeId = pasteType.getValue().getId();

                        for (EObject obj : pasteTypedFieldsAndTypes) {
                            if (obj instanceof ProcessRelevantData) {
                                ProcessRelevantData data =
                                        (ProcessRelevantData) obj;

                                DeclaredType declaredType =
                                        ((DeclaredType) data.getDataType());
                                String fieldTypeId =
                                        declaredType.getTypeDeclarationId();

                                if (pasteTypeId.equals(fieldTypeId)) {
                                    // swap the field that refs type to existing
                                    // type's id.
                                    resultCmd
                                            .append(SetCommand
                                                    .create(ed,
                                                            declaredType,
                                                            Xpdl2Package.eINSTANCE
                                                                    .getDeclaredType_TypeDeclarationId(),
                                                            pkgType.getId()));
                                }
                            } else if (obj instanceof TypeDeclaration) {
                                TypeDeclaration type = (TypeDeclaration) obj;

                                DeclaredType declaredType =
                                        type.getDeclaredType();

                                String fieldTypeId =
                                        declaredType.getTypeDeclarationId();

                                if (pasteTypeId.equals(fieldTypeId)) {
                                    // swap the type decl that refs type to
                                    // existing type's id.
                                    resultCmd
                                            .append(SetCommand
                                                    .create(ed,
                                                            declaredType,
                                                            Xpdl2Package.eINSTANCE
                                                                    .getDeclaredType_TypeDeclarationId(),
                                                            pkgType.getId()));
                                }
                            }

                        }
                    }
                }
            }
        }

        return resultCmd;
    }

    /**
     * @return
     */
    public static List getProcessPools(Process process) {
        String id = process.getId();
        if (id == null || process.getPackage() == null) {
            return Collections.EMPTY_LIST;
        }

        List result = new ArrayList();
        EList pools = process.getPackage().getPools();
        for (Iterator iter = pools.iterator(); iter.hasNext();) {
            Pool pool = (Pool) iter.next();
            if (id.equals(pool.getProcessId())) {
                result.add(pool);
            }
        }
        return result;
    }

    /**
     * Get the total height of the given pool.
     * 
     * @param pool
     * @return
     */
    public static int getPoolHeight(Pool pool) {
        int poolHeight = 0;

        List lanes = pool.getLanes();
        for (Iterator l = lanes.iterator(); l.hasNext();) {
            Lane lane = (Lane) l.next();

            NodeGraphicsInfo gn = Xpdl2ModelUtil.getNodeGraphicsInfo(lane);
            if (gn != null) {
                poolHeight += gn.getHeight();
            }
        }
        return poolHeight;
    }

    /**
     * Append the add model elements commands to the given result Command. This
     * method sorts out the correct places to put the objects in the model or
     * finalTarget.
     * 
     * @param resultCmd
     * @param finalTarget
     *            If this is ActivitySet then top-level activities and
     *            transitions etc in pasteObjects etc are placed in the activity
     *            set, else they are placed at process level.
     * @param laneOrPoolIndex
     *            If pasting whole lanes then this will be insert index of lanes
     *            into parent pool's current list of lanes. If pasting whole
     *            pools then this will be insert index of pools into finalTarget
     *            process's current list of pools.
     * @param targetPool
     *            Only used if pasting lanes into pool.
     * @param pasteObjects
     *            List of EObjects to copy into model.
     * 
     * @return Same resultCmd or null on error.
     */
    private static CompoundCommand appendCopyElementCommands(EditingDomain ed,
            Process tgtProcess, CompoundCommand resultCmd, EObject finalTarget,
            int laneOrPoolIndex, EObject targetPool, Collection pasteObjects) {

        //
        // Ensure that anything that will be referenced by something
        // else is first in list. This ensures that when creating
        // adapters / edit parets etc, the things we ref are there
        // before we hand.
        List otherObjectCmds = new ArrayList();
        List artifactCmds = new ArrayList();
        List activitySetCmds = new ArrayList();
        List activityCmds = new ArrayList();
        List laneObjects = new ArrayList();
        List poolObjects = new ArrayList();
        List transAndMsgFlowCmds = new ArrayList();
        List associationCmds = new ArrayList();

        boolean failed = false;

        // Go thru paste objects sorting into various lists.
        // First some things that may come along in the future.
        // When copy starts picking up reference objects such as these...
        for (Iterator p = pasteObjects.iterator(); p.hasNext() && !failed;) {
            EObject eobj = (EObject) p.next();

            EObject containingObject = null;
            EStructuralFeature containingFeature = null;

            List addToList = null;

            if (eobj instanceof Artifact) {
                // No choice. No matter where they are, groups/dataObjects/Notes
                // go in the pacjkage artifacts
                containingObject = tgtProcess.getPackage();
                containingFeature =
                        Xpdl2Package.eINSTANCE.getPackage_Artifacts();

                addToList = artifactCmds;

            } else if (eobj instanceof ActivitySet) {
                // Acitvity sets go in process
                containingObject = tgtProcess;
                containingFeature =
                        Xpdl2Package.eINSTANCE.getProcess_ActivitySets();

                addToList = activitySetCmds;

            } else if (eobj instanceof Activity) {
                if (finalTarget instanceof ActivitySet) {
                    // If target is activity set then it goes there.
                    containingObject = finalTarget;
                    containingFeature =
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Activities();

                } else {
                    // Otherwise it goes in process.
                    containingObject = tgtProcess;
                    containingFeature =
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Activities();
                }

                addToList = activityCmds;

            } else if (eobj instanceof Lane) {
                // Can only paste lane into target pool.
                if (targetPool != null) {
                    laneObjects.add(eobj);

                    // We'll create a multiple insert cmd for lanes later
                    continue;
                }
            } else if (eobj instanceof Pool) {
                if (finalTarget instanceof Process) {
                    poolObjects.add(eobj);

                    // We'll create a multiple insert cmd for lanes later
                    continue;
                }

            } else if (eobj instanceof Transition) {
                if (finalTarget instanceof ActivitySet) {
                    // If target is activity set then it goes there.
                    containingObject = finalTarget;
                    containingFeature =
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions();
                } else {
                    // Otherwise it goes in process.
                    containingObject = tgtProcess;
                    containingFeature =
                            Xpdl2Package.eINSTANCE
                                    .getFlowContainer_Transitions();
                }

                addToList = transAndMsgFlowCmds;

            } else if (eobj instanceof MessageFlow) {
                // No choice. No matter where they are, messageflows
                // go in the package MessageFlows
                containingObject = tgtProcess.getPackage();
                containingFeature =
                        Xpdl2Package.eINSTANCE.getPackage_MessageFlows();

                addToList = transAndMsgFlowCmds;

            } else if (eobj instanceof Association) {
                // No choice. No matter where they are, associations
                // go in the package Associations
                containingObject = tgtProcess.getPackage();
                containingFeature =
                        Xpdl2Package.eINSTANCE.getPackage_Associations();

                addToList = transAndMsgFlowCmds;
            }

            // Other things that may come along in the future.
            // When copy starts picking up reference objects such as these...
            else if (eobj instanceof DataField) {
                containingObject = tgtProcess;
                containingFeature =
                        Xpdl2Package.eINSTANCE
                                .getDataFieldsContainer_DataFields();

                addToList = otherObjectCmds;

            } else if (eobj instanceof Participant) {
                containingObject = tgtProcess;
                containingFeature =
                        Xpdl2Package.eINSTANCE
                                .getParticipantsContainer_Participants();

                addToList = otherObjectCmds;

            } else if (eobj instanceof Application) {
                containingObject = tgtProcess;
                containingFeature =
                        Xpdl2Package.eINSTANCE
                                .getApplicationsContainer_Applications();

                addToList = otherObjectCmds;

            } else if (eobj instanceof Assignment) {
                containingObject = tgtProcess;
                containingFeature =
                        Xpdl2Package.eINSTANCE
                                .getAssigmentsContainer_Assignments();

                addToList = otherObjectCmds;

            } else if (eobj instanceof PartnerLink) {
                containingObject = tgtProcess;
                containingFeature =
                        Xpdl2Package.eINSTANCE.getProcess_PartnerLinks();

                addToList = otherObjectCmds;

            } else if (eobj instanceof PartnerLinkType) {
                containingObject = tgtProcess.getPackage();
                containingFeature =
                        Xpdl2Package.eINSTANCE.getPackage_PartnerLinkTypes();

                addToList = otherObjectCmds;

            } else if (eobj instanceof TypeDeclaration) {
                containingObject = tgtProcess.getPackage();
                containingFeature =
                        Xpdl2Package.eINSTANCE.getPackage_TypeDeclarations();

                addToList = otherObjectCmds;

            } else if (eobj instanceof Application) {
                containingObject = tgtProcess;
                containingFeature =
                        Xpdl2Package.eINSTANCE
                                .getApplicationsContainer_Applications();

                addToList = otherObjectCmds;

            } else if (eobj instanceof Participant || eobj instanceof DataField
                    || eobj instanceof FormalParameter
                    || eobj instanceof ExternalPackage) {
                // We'll deal with these later.
                continue;
            }

            // Add the command to put the object in the appropriate place.
            if (addToList != null && containingFeature != null
                    && containingObject != null) {
                addToList.add(AddCommand.create(ed,
                        containingObject,
                        containingFeature,
                        eobj));

            } else {
                failed = true;
            }

        }

        if (!failed) {
            //
            // Activity sets next (can be ref'd by activities and a\rtifacts.
            for (Iterator iter = activitySetCmds.iterator(); iter.hasNext();) {
                Command command = (Command) iter.next();
                resultCmd.append(command);
            }

            //
            // Pools next (these are contained by process).
            if (poolObjects.size() > 0) {
                resultCmd.append(AddCommand.create(ed,
                        tgtProcess.getPackage(),
                        Xpdl2Package.eINSTANCE.getPackage_Pools(),
                        poolObjects,
                        laneOrPoolIndex));
            }

            //
            // Lanes next (these can be referenced by artifacts and activities)
            if (laneObjects.size() > 0) {
                resultCmd.append(AddCommand.create(ed,
                        targetPool,
                        Xpdl2Package.eINSTANCE.getPool_Lanes(),
                        laneObjects,
                        laneOrPoolIndex));
            }

            //
            // Artifacts can be referenced by connections...
            for (Iterator iter = artifactCmds.iterator(); iter.hasNext();) {
                Command command = (Command) iter.next();
                resultCmd.append(command);
            }

            //
            // Activities next (can be ref'd by connections).
            for (Iterator iter = activityCmds.iterator(); iter.hasNext();) {
                Command command = (Command) iter.next();
                resultCmd.append(command);
            }

            //
            // Then transitions and message flows (can be ref'd by associations)
            for (Iterator iter = transAndMsgFlowCmds.iterator(); iter.hasNext();) {
                Command command = (Command) iter.next();
                resultCmd.append(command);
            }

            //
            // And finally Associations (these can ref just about anything).
            for (Iterator iter = associationCmds.iterator(); iter.hasNext();) {
                Command command = (Command) iter.next();
                resultCmd.append(command);
            }

        }

        return resultCmd;
    }

    /**
     * Get the 'scope' of the given objects to be copied (i.e. the highest level
     * of object to be copied such as Pools or Lanes etc)
     * 
     * @param target
     * @param copyObjects
     * @return
     */
    public static CopyPasteScope getCreateDiagramObjectsScope(Object target,
            final Collection copyObjects) {

        CopyPasteScope copyScope = getCopyPasteScope(copyObjects);
        //
        // We can only allow certain copy scopes for certain targets
        if (copyScope.getCopyScope() == CopyPasteScope.COPY_POOLS) {
            if (!(target instanceof Process) && !(target instanceof Pool)) {
                // Can only paste pools into process (diagram) or pool (as in
                // 'insert above selected pool')
                copyScope.setCopyScope(CopyPasteScope.COPY_NONE);
            }

        } else if (copyScope.getCopyScope() == CopyPasteScope.COPY_LANES) {
            if (!(target instanceof Pool) && !(target instanceof Lane)) {
                // Can only paste lanes into pool (diagram) or lane (as in
                // 'insert above selected lane')
                copyScope.setCopyScope(CopyPasteScope.COPY_NONE);
            }

        } else if (copyScope.getCopyScope() == CopyPasteScope.COPY_ACTIVITIES_AND_ARTIFACTS) {
            // Can paste any activitiy or artifact into lane,
            // but currently cannot put artifacts in activity sets
            if ((target instanceof Activity)) {
                // If we're pasting into an activity then it must be a block
                // activity (embedded sub-flow) and there must be no artifacts.
                if (((Activity) target).getBlockActivity() == null) {
                    copyScope.setCopyScope(CopyPasteScope.COPY_NONE);
                }
            } else if (!(target instanceof Lane)) {
                copyScope.setCopyScope(CopyPasteScope.COPY_NONE);
            }
        }
        return copyScope;
    }

    public static CopyPasteScope getCopyPasteScope(Collection copyObjects) {
        CopyPasteScope copyScope = new CopyPasteScope();
        copyScope.setCopyScope(CopyPasteScope.COPY_NONE);
        boolean hasPools = false, hasLanes = false;
        boolean hasActivities = false, hasArtifacts = false, hasOther = false;
        boolean hasParticipants = false, hasDataFields = false, hasFormalParams =
                false;
        boolean hasNonEObject = false;

        for (Iterator iter = copyObjects.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (!(obj instanceof EObject)) {
                hasNonEObject = true;
            } else if (obj instanceof Pool) {
                hasPools = true;
            } else if (obj instanceof Lane) {
                hasLanes = true;
            } else if (obj instanceof Activity) {
                hasActivities = true;
            } else if (obj instanceof Artifact) {
                hasArtifacts = true;
            } else if (obj instanceof Participant) {
                hasParticipants = true;
            } else if (obj instanceof DataField) {
                hasDataFields = true;
            } else if (obj instanceof FormalParameter) {
                hasFormalParams = true;
            } else {
                hasOther = true;
            }
        }

        if (!hasNonEObject) {
            // Get highest order of object in list.
            if (hasPools) {
                copyScope.setCopyScope(CopyPasteScope.COPY_POOLS);
            } else if (hasLanes) {
                copyScope.setCopyScope(CopyPasteScope.COPY_LANES);
            } else if (hasActivities || hasArtifacts) {
                copyScope
                        .setCopyScope(CopyPasteScope.COPY_ACTIVITIES_AND_ARTIFACTS);
            } else if (hasDataFields || hasFormalParams || hasParticipants) {
                copyScope.setCopyScope(CopyPasteScope.COPY_DATA_ONLY);
            }
        }
        return copyScope;
    }

    /**
     * Given a list of objects to copy to clipboard, extract all the activity
     * elements.
     * 
     * @return
     */
    public static Collection<Activity> getActivitiesFromCopyList(
            Collection elementsToCopy) {
        ArrayList<Activity> activities = new ArrayList<Activity>();

        for (Iterator iter = elementsToCopy.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            //
            // Activities always appear at top level or in activity sets at top
            // level of list

            if (obj instanceof Activity) {
                activities.add((Activity) obj);
            } else if (obj instanceof ActivitySet) {
                activities.addAll(((ActivitySet) obj).getActivities());
            }

        }

        return activities;
    }

    /**
     * Given a list of objects to copy to clipboard, extract all the transition
     * elements.
     * 
     * @return
     */
    public static Collection<Transition> getTransitionsFromCopyList(
            Collection elementsToCopy) {
        ArrayList<Transition> transitions = new ArrayList<Transition>();

        for (Iterator iter = elementsToCopy.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            //
            // Transitions always appear at top level or in activity sets at top
            // level of list

            if (obj instanceof Transition) {
                transitions.add((Transition) obj);
            } else if (obj instanceof ActivitySet) {
                transitions.addAll(((ActivitySet) obj).getTransitions());
            }

        }

        return transitions;
    }

    /**
     * Resolve the pasting of data fields and formal parameters.
     * <p>
     * <li>The paste objects will contain the data fields / params that are
     * referenced by activities in the paste objects)
     * <li>These will have new unique ids BUT references from
     * activity/transition performers will NOT have been resolved these will
     * still have original id's.
     * <p>
     * <li>If there is already a data field / formal parameter with same name in
     * the destination process then we will re-use that.
     * <li>In this situation we have to replace anything that references data
     * field by id with the latest id.
     * <p>
     * <li>If there is NOT a field / param with same name then we will create a
     * new field/param and then change the references in paste
     * activities/transitions to this newly created id.
     * <p>
     * <br>
     * 
     * @param ed
     * @param targetObject
     * 
     * @param resultCmd
     * @param copyOfPasteObjects
     * @param newUniqueIdMap
     * @return
     */
    private static CompoundCommand resolvePasteFields(EditingDomain ed,
            Process tgtProcess, Object targetObject, CompoundCommand resultCmd,
            Collection copyOfPasteObjects, Map<String, EObject> newUniqueIdMap) {

        // For quick lookups we'll create a map of current data fields / params
        // name to object. We will include package data fields to.
        HashMap<String, ProcessRelevantData> nameToFieldOrParam =
                new HashMap<String, ProcessRelevantData>();

        if (targetObject instanceof Activity
                && ((Activity) targetObject).getBlockActivity() != null) {
            // Pasting into embedded sub-process - add it's fields here.
            ActivitySet actSet =
                    tgtProcess.getActivitySet(((Activity) targetObject)
                            .getBlockActivity().getActivitySetId());
            if (actSet != null) {
                List<ProcessRelevantData> embData =
                        LocalPackageProcessInterfaceUtil
                                .getEmbeddedSubProcessSetScopeData(actSet);

                for (Iterator iter = embData.iterator(); iter.hasNext();) {
                    DataField d = (DataField) iter.next();

                    nameToFieldOrParam.put(d.getName(), d);
                }
            }
        }

        Collection dataFields = tgtProcess.getPackage().getDataFields();
        for (Iterator iter = dataFields.iterator(); iter.hasNext();) {
            DataField d = (DataField) iter.next();

            nameToFieldOrParam.put(d.getName(), d);
        }

        dataFields = tgtProcess.getDataFields();
        for (Iterator iter = dataFields.iterator(); iter.hasNext();) {
            DataField d = (DataField) iter.next();

            nameToFieldOrParam.put(d.getName(), d);
        }

        Collection params =
                ProcessInterfaceUtil.getAllFormalParameters(tgtProcess);
        for (Iterator iter = params.iterator(); iter.hasNext();) {
            FormalParameter p = (FormalParameter) iter.next();

            nameToFieldOrParam.put(p.getName(), p);
        }

        //
        // First thing to do is add commands to copy the data fields / params to
        // model.
        // Whilst we are doing this we will create a map that we can use to
        // resolve the references that should be mapped to existing data fields
        // in dest process..
        //

        HashMap<String, String> remappedData = new HashMap<String, String>();

        //
        // Look for fields/params to paste.
        for (Iterator iter = copyOfPasteObjects.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof ProcessRelevantData) {
                ProcessRelevantData pasteData = (ProcessRelevantData) obj;

                // Check whether same named field/param already exists in
                // destination model.
                ProcessRelevantData currData =
                        nameToFieldOrParam.get(pasteData.getName());

                if (currData != null) {
                    // We have a current field/param of same name, we will
                    // switch the performers to use this.

                    // Map the paste field/param to current field/param.
                    remappedData.put(pasteData.getId(), currData.getId());

                } else {
                    // We don't have a field/param of same name, so add command
                    // to create it.
                    if (pasteData instanceof FormalParameter) {
                        resultCmd
                                .append(AddCommand
                                        .create(ed,
                                                tgtProcess,
                                                Xpdl2Package.eINSTANCE
                                                        .getFormalParametersContainer_FormalParameters(),
                                                pasteData));

                    } else {
                        if (targetObject instanceof Activity
                                && ((Activity) targetObject).getBlockActivity() != null) {
                            // Pasting into embedded sub-process - add it's
                            // fields here.
                            resultCmd
                                    .append(AddCommand
                                            .create(ed,
                                                    targetObject,
                                                    Xpdl2Package.eINSTANCE
                                                            .getDataFieldsContainer_DataFields(),
                                                    pasteData));
                        } else {
                            resultCmd
                                    .append(AddCommand
                                            .create(ed,
                                                    tgtProcess,
                                                    Xpdl2Package.eINSTANCE
                                                            .getDataFieldsContainer_DataFields(),
                                                    pasteData));
                        }
                    }
                }
            }
        }

        //
        // Now resolve references to the data fields.
        if (remappedData.size() > 0) {
            Collection<Activity> activities =
                    getActivitiesFromCopyList(copyOfPasteObjects);

            // Ask the standard data field reference replacer to do the work for
            // us. Name isn't changing so just map the field id references.
            Xpdl2FieldOrParamReplacer replacer =
                    new Xpdl2FieldOrParamReplacer(remappedData, true);

            for (Activity act : activities) {

                Command replaceCmd =
                        replacer.getReplaceFieldReferencesCommand(ed, act);
                if (replaceCmd != null) {
                    resultCmd.append(replaceCmd);
                }
            }

            Collection<Transition> transitions =
                    getTransitionsFromCopyList(copyOfPasteObjects);

            // Ask the standard data field reference replacer to do the work for
            // us. Name isn't changing so just map the field id references.
            for (Transition trans : transitions) {

                Command replaceCmd =
                        replacer.getReplaceFieldReferencesCommand(ed, trans);
                if (replaceCmd != null) {
                    resultCmd.append(replaceCmd);
                }
            }
        }

        return resultCmd;
    }

    /**
     * Resolve the pasting of participants.
     * <p>
     * <li>The paste objects will contain the participants that are referenced
     * by activities in the paste objects)
     * <li>These will have new unique ids.
     * <p>
     * <li>If there is already a participant with same name in the destination
     * process then we will re-use that.
     * <li>In this situation we have to replace the performer ids in the paste
     * activities to the current partic id.
     * <p>
     * <li>If there is NOT a participant with same name then we will create a
     * new participant and then change the performers in paste activities to
     * this id.
     * <p>
     * <br>
     * 
     * @param ed
     * 
     * @param resultCmd
     * @param copyOfPasteObjects
     * @param newUniqueIdMap
     * @return
     */
    private static CompoundCommand resolvePasteParticipants(EditingDomain ed,
            Process tgtProcess, CompoundCommand resultCmd,
            Collection copyOfPasteObjects, Map<String, EObject> newUniqueIdMap) {

        // For quick lookups we'll create a map of current partics name to
        // participant object.
        HashMap<String, Participant> nameToPartic =
                new HashMap<String, Participant>();

        Collection partics = tgtProcess.getPackage().getParticipants();
        for (Iterator iter = partics.iterator(); iter.hasNext();) {
            Participant p = (Participant) iter.next();

            nameToPartic.put(p.getName(), p);
        }

        partics = tgtProcess.getParticipants();
        for (Iterator iter = partics.iterator(); iter.hasNext();) {
            Participant p = (Participant) iter.next();

            nameToPartic.put(p.getName(), p);
        }

        //
        // First thing to do is add commands to copy the participants to model.
        // Whilst we are doing this we will create a map that we can use to
        // resolve the id's used in activity performers.
        //

        // Map of paste participant id's to existing partic id for when we
        // re-use an existing participant.
        HashMap<String, String> remappedPartics = new HashMap<String, String>();

        //
        // Look for participants to paste.
        for (Iterator iter = copyOfPasteObjects.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof Participant) {
                Participant pastePartic = (Participant) obj;

                // Check whether same named partic already exists in destination
                // model.
                Participant currPartic =
                        nameToPartic.get(pastePartic.getName());

                if (currPartic != null) {
                    // We have a current partic of same name, we will switch the
                    // performers to use this.

                    // Map the paste participant to current participant.
                    remappedPartics
                            .put(pastePartic.getId(), currPartic.getId());

                } else {
                    /*
                     * We don't have a participant of same name, so add command
                     * to create it.
                     * 
                     * We now discourage use of process participants so we paste
                     * into package participants instead.
                     */
                    resultCmd.append(AddCommand.create(ed,
                            tgtProcess.getPackage(),
                            Xpdl2Package.eINSTANCE
                                    .getParticipantsContainer_Participants(),
                            pastePartic));
                }
            }
        }

        //
        // Now resolve references to the participants.
        if (remappedPartics.size() > 0) {
            Collection<Activity> activities =
                    getActivitiesFromCopyList(copyOfPasteObjects);

            for (Activity act : activities) {

                List<Performer> performers = act.getPerformerList();

                for (Performer performer : performers) {
                    String particId = performer.getValue();

                    if (particId != null) {

                        // Then lookup the past partic id iun the particIdMap
                        // This effectively checks whether the given partic was
                        // copied or whether we're reassigning to an existing
                        // partic of same name.
                        String finalParticId = remappedPartics.get(particId);

                        if (finalParticId != null) {
                            performer.setValue(finalParticId);
                        }
                    }
                }

                //
                // Also Replace references to participant in Web Service alias.
                //
                WebServiceOperation wso = getWebServiceOperation(act);
                if (wso != null) {
                    Object alias =
                            Xpdl2ModelUtil.getOtherAttribute(wso,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Alias());
                    if (alias instanceof String) {
                        String finalParticId = remappedPartics.get(alias);
                        if (finalParticId != null) {
                            Xpdl2ModelUtil.setOtherAttribute(wso,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_Alias(),
                                    finalParticId);
                        }

                    }
                }
            }
        }

        return resultCmd;
    }

    private static WebServiceOperation getWebServiceOperation(Activity act) {
        if (act.getEvent() != null
                && act.getEvent().getEventTriggerTypeNode() instanceof TriggerResultMessage) {
            TriggerResultMessage trm =
                    (TriggerResultMessage) act.getEvent()
                            .getEventTriggerTypeNode();

            return trm.getWebServiceOperation();

        } else if (act.getImplementation() instanceof Task) {
            Task task = (Task) act.getImplementation();

            if (task.getTaskSend() != null) {
                return task.getTaskSend().getWebServiceOperation();

            } else if (task.getTaskService() != null) {
                return task.getTaskService().getWebServiceOperation();

            } else if (task.getTaskReceive() != null) {
                return task.getTaskReceive().getWebServiceOperation();
            }
        }

        return null;
    }

    /**
     * Independent sub-process call tasks in the paste objects will all have
     * package references set.
     * <p>
     * We need to check each and if the package reference is actually the
     * package we are pasting into. If it is then we remove the package
     * reference. If pasting into a different package then we need to add the
     * ExternalPackage element that is also included in paste object.
     * 
     * @param ed
     * @param resultCmd
     * @param copyOfPasteObjects
     * @param newUniqueIdMap
     * @return
     */
    private static CompoundCommand resolveProcessPkgReferences(
            EditingDomain ed, Process tgtProcess, CompoundCommand resultCmd,
            Collection copyOfPasteObjects, Map<String, EObject> newUniqueIdMap) {

        // Gather the external package items in the paste objects list.
        List<ExternalPackage> pastePkgRefs = new ArrayList<ExternalPackage>();
        for (Iterator iter = copyOfPasteObjects.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof ExternalPackage) {
                pastePkgRefs.add((ExternalPackage) obj);
            }
        }

        // If there are no externalpackage elements in paste list then there are
        // no indi subproc calls so nothing to do.
        if (pastePkgRefs.size() > 0) {
            String ourPackageLocation =
                    Xpdl2ProcessDiagramUtils
                            .getOurPackageReferenceLocation(tgtProcess
                                    .getPackage());

            if (ourPackageLocation != null && ourPackageLocation.length() > 0) {

                // Create a map of sub-process package ref to external package
                // elements.
                HashMap<String, ExternalPackage> extPkgMap =
                        new HashMap<String, ExternalPackage>();
                for (ExternalPackage extPkg : pastePkgRefs) {
                    extPkgMap.put(extPkg.getHref(), extPkg);
                }

                // Keep track of any external packages to add to our package.
                Set<ExternalPackage> addPkgRefs =
                        new HashSet<ExternalPackage>();

                Collection<Activity> activities =
                        getActivitiesFromCopyList(copyOfPasteObjects);

                // Find and deal with each indi-sub-proc task.
                for (Activity act : activities) {
                    if (act.getImplementation() instanceof SubFlow) {
                        SubFlow subFlow = (SubFlow) act.getImplementation();

                        String pkgRef = subFlow.getPackageRefId();

                        if (pkgRef != null && pkgRef.length() > 0) {
                            // Check our own package reference
                            // details with those in the copy.
                            ExternalPackage srcPkg = extPkgMap.get(pkgRef);
                            if (srcPkg != null) {

                                String srcPkgLoc =
                                        Xpdl2WorkingCopyImpl
                                                .getExternalPackageLocation(srcPkg);

                                if (ourPackageLocation.equals(srcPkgLoc)) {
                                    // If target package is same as refd
                                    // package then we can remove the
                                    // package reference.
                                    subFlow.setPackageRefId(null);

                                } else {
                                    // Otherwise we need to add the
                                    // external package reference for it.
                                    addPkgRefs.add(srcPkg);
                                }
                            }
                        }
                    }

                } // Next activity

                // When we're all done, add any external package references
                // ref'd by subproc tasks.
                for (ExternalPackage srcPkg : addPkgRefs) {
                    Xpdl2WorkingCopyImpl.appendAddReferenceCommand(ed,
                            resultCmd,
                            srcPkg,
                            tgtProcess.getPackage());
                }
            }

        }

        return resultCmd;
    }

    private static String getOurPackageReferenceLocation(Package pkg) {
        // Get an external package reference for ourselves so that we can
        // compare like-for-like with those in the copy list
        String ourPackageLocation = null;

        ExternalPackage ourPackageRef =
                Xpdl2WorkingCopyImpl.createExternalPackage(WorkingCopyUtil
                        .getWorkingCopyFor(pkg));
        if (ourPackageRef != null) {
            ourPackageLocation =
                    Xpdl2WorkingCopyImpl
                            .getExternalPackageLocation(ourPackageRef);
        }
        return ourPackageLocation;
    }

    /**
     * Given a list of elements about to be copied add any referenced
     * fields/params/typ;es/participants and so on to them.
     * 
     * @param elementsToCopy
     * @param copied
     * @param wantReferencedProjectEntries
     */
    public static void addObjectsReferencedFromCopyObjects(Process srcProcess,
            List elementsToCopy, Collection copied,
            boolean wantReferencedProjectEntries) {
        //
        // Pick up referenced data fields, formal parameters and participants
        // referred in process relevant data.
        Collection<Participant> copyPartics = new HashSet<Participant>();
        IProject sourceProject = WorkingCopyUtil.getProjectFor(srcProcess);
        Collection<ProcessRelevantData> copyData =
                getReferencedData(srcProcess, elementsToCopy, copyPartics);
        if (copyData.size() > 0) {
            copied.addAll(copyData);
        }

        //
        // Pick up referenced type declarations.
        Set<TypeDeclaration> copyTypes = getReferencedTypes(copyData);
        if (copyTypes.size() > 0) {
            copied.addAll(copyTypes);
        }

        //
        // Pick up referenced participants.
        copyPartics
                .addAll(getReferencedParticipants(srcProcess, elementsToCopy));
        if (copyPartics.size() > 0) {
            copied.addAll(copyPartics);
        }
        // XPD-3033 check requirement for new project references and ask user
        if (wantReferencedProjectEntries) {
            copied.addAll(ActionUtil
                    .getExternalProjectReferencesForObjects(copied,
                            sourceProject));
        }
    }

    /**
     * Return a list of type declarations that are referenced by the given set
     * of data fields.
     * 
     * @param copyData
     * @return
     */

    private static Set<TypeDeclaration> getReferencedTypes(
            Collection<ProcessRelevantData> copyData) {
        HashSet<TypeDeclaration> types = new HashSet<TypeDeclaration>();

        Package pkg = null;

        for (ProcessRelevantData data : copyData) {
            if (pkg == null) {
                pkg = Xpdl2ModelUtil.getPackage(data);
            }

            if (pkg != null) {
                TypeDeclaration declType = getDeclaredType(pkg, data);

                if (declType != null) {
                    types.add(declType);

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
                        nestedType = getDeclaredType(pkg, nestedType);
                        if (doneTypes.contains(nestedType)) {
                            // Already done this one! there's a cycle in type
                            // declarations, get out now.
                            break;
                        }

                        if (nestedType != null) {
                            types.add(nestedType);
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

    /**
     * Return a list of the data fields and formal parameters that are
     * referenced by the selected objects.
     * 
     * @param elementsToCopy
     * @return
     */
    private static Collection<ProcessRelevantData> getReferencedData(
            Process srcProcess, Collection elementsToCopy,
            Collection<Participant> copyPartics) {

        // The return set.
        HashSet<ProcessRelevantData> copyData =
                new HashSet<ProcessRelevantData>();

        //
        // Create the field reference resolver for all fields in process AND
        // package.

        // IF all elements to copy are within the same parent embedded
        // sub-process then the field resolver context object should be the
        // embedded sub-proc activity set.
        ActivitySet actSetContext = null;
        for (Object o : elementsToCopy) {
            if (o instanceof Activity) {
                Activity act = (Activity) o;
                if (act.eContainer() instanceof ActivitySet) {
                    if (actSetContext == null) {
                        actSetContext = (ActivitySet) act.eContainer();
                    } else {
                        if (act.eContainer() != actSetContext) {
                            // There are actvities from TWO different activity
                            // sets (THIS SHOULD NOT BE POSSIBLE!).
                            throw new IllegalStateException(
                                    "Copying separate objects from different activity set parents should NOT be possible!"); //$NON-NLS-1$
                        }
                    }
                }
            }
        }

        Xpdl2FieldOrParamResolver fieldResolver =
                new Xpdl2FieldOrParamResolver(
                        actSetContext != null ? actSetContext : srcProcess);

        //
        // Get all the activity elements from list of objects to copy
        Collection<Activity> allCopyActs =
                Xpdl2ProcessDiagramUtils
                        .getActivitiesFromCopyList(elementsToCopy);

        //
        // Check each activity for references to data fields.
        for (Activity act : allCopyActs) {
            Set<ProcessRelevantData> referenced =
                    fieldResolver.getDataReferences(act);

            if (referenced != null && referenced.size() > 0) {
                copyData.addAll(referenced);

            } else {
                /*
                 * XPD-200 - normally we would not count IMPLICITLY associated
                 * data for activities however user tasks are a slightly special
                 * case in that if the user has explicitly created a form and
                 * then the user has also explicitly said
                 * "I want all the process data in the form".
                 * 
                 * Therefore for a user task with an explicitly created form we
                 * should consider all data as associated.
                 */
                if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(act))) {

                    List<AssociatedParameter> activityAssociatedParameters =
                            ProcessInterfaceUtil
                                    .getActivityAssociatedParameters(act);
                    if (activityAssociatedParameters == null
                            || activityAssociatedParameters.size() == 0) {
                        /*
                         * Sid XPD-2087: Only use all data implicitly if
                         * implicit association has not been disabled.
                         */
                        if (!ProcessInterfaceUtil
                                .isImplicitAssociationDisabled(act)) {
                            FormImplementation impl =
                                    TaskObjectUtil
                                            .getUserTaskFormImplementation(act);
                            if (impl != null
                                    && FormImplementationType.FORM.equals(impl
                                            .getFormType())) {
                                copyData.addAll(ProcessInterfaceUtil
                                        .getAllAvailableRelevantDataForActivity(act));
                            }
                        }
                    }
                }
            }
        }

        //
        // Get all the transition elements from list of objects to copy
        Collection<Transition> allCopyTrans =
                Xpdl2ProcessDiagramUtils
                        .getTransitionsFromCopyList(elementsToCopy);

        //
        // Check each activity for references to data fields.
        for (Transition trans : allCopyTrans) {
            Set<ProcessRelevantData> referenced =
                    fieldResolver.getDataReferences(trans);

            if (referenced != null && referenced.size() > 0) {
                copyData.addAll(referenced);
            }
        }

        /**
         * Go thru the fields in elements to copy AND the extra copyData looking
         * for references to other data fields (i.e. performer data field can
         * contain references to other fields/params in its script).
         */
        Set<ProcessRelevantData> extraFields =
                new HashSet<ProcessRelevantData>();
        for (ProcessRelevantData dataToCopy : copyData) {
            addFieldsRefdFromFields(fieldResolver,
                    dataToCopy,
                    extraFields,
                    copyPartics);
        }

        copyData.addAll(extraFields);

        return copyData;
    }

    private static void addFieldsRefdFromFields(
            Xpdl2FieldOrParamResolver fieldResolver,
            ProcessRelevantData checkField,
            Set<ProcessRelevantData> extraFields,
            Collection<Participant> copyPartics) {

        Set<ProcessRelevantData> dataRefs =
                fieldResolver.getDataReferences(checkField);
        if (!extraFields.contains(checkField)) {
            extraFields.add(checkField);
        }
        copyPartics
                .addAll(addParticipantsRefdFromPerformerDataFields(checkField));

        for (ProcessRelevantData refData : dataRefs) {
            if (!extraFields.contains(refData)) {
                extraFields.add(refData);

                addFieldsRefdFromFields(fieldResolver,
                        refData,
                        extraFields,
                        copyPartics);
            }
        }

    }

    private static Collection<Participant> addParticipantsRefdFromPerformerDataFields(
            ProcessRelevantData processRelevantData) {
        HashSet<Participant> copyPartics = new HashSet<Participant>();
        Set<Participant> participantDataReferences =
                Xpdl2ParticipantReferenceResolver
                        .getParticipantDataReferences(processRelevantData);
        if (participantDataReferences.size() > 0) {
            copyPartics.addAll(participantDataReferences);
        }
        return copyPartics;
    }

    /**
     * Returns a list of the participants that are referenced from the
     * activities in the given set of copy to clipboard objects.
     * 
     * @param elementsToCopy
     * @return
     */
    private static Collection<Participant> getReferencedParticipants(
            Process srcProcess, Collection elementsToCopy) {

        // The return set.
        HashSet<Participant> copyPartics = new HashSet<Participant>();

        // The participants available in process AND package.
        // We will add package participants to copy set because we may not be
        // pasting into same package.
        HashMap<String, Participant> availPartics =
                new HashMap<String, Participant>();

        Collection pkgPartics = srcProcess.getPackage().getParticipants();
        for (Iterator iter = pkgPartics.iterator(); iter.hasNext();) {
            Participant p = (Participant) iter.next();

            availPartics.put(p.getId(), p);
        }

        Collection procPartics = srcProcess.getParticipants();
        for (Iterator iter = procPartics.iterator(); iter.hasNext();) {
            Participant p = (Participant) iter.next();

            availPartics.put(p.getId(), p);
        }

        //
        // Get all the activity elements from list of objects to copy
        Collection<Activity> allCopyActs =
                Xpdl2ProcessDiagramUtils
                        .getActivitiesFromCopyList(elementsToCopy);

        // Create the result list of referenced participants.
        for (Activity act : allCopyActs) {

            List<Performer> performers = act.getPerformerList();

            for (Performer perf : performers) {
                String particId = perf.getValue();

                if (particId != null) {
                    Participant partic = availPartics.get(particId);

                    if (partic != null) {
                        // Reference to a process participant.
                        copyPartics.add(partic);
                    }
                }
            }

        }
        return copyPartics;

    }

}
