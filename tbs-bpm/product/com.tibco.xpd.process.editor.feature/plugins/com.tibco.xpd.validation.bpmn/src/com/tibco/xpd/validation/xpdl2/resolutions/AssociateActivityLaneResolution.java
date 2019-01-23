/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj, aallway
 * @since 3.2
 */
public class AssociateActivityLaneResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            CompoundCommand cmd = new LateExecuteCompoundCommand();
            Activity activity = (Activity) target;
            Process process = activity.getProcess();
            Lane lane = getFirstLaneInTheProcess(cmd, editingDomain, process);
            if (lane != null) {
                // Replace the whole graphics info element so that diagram
                // editor picks up the change and refreshes correctly.
                NodeGraphicsInfo nodeGraphicsInfo =
                        Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(activity,
                                editingDomain,
                                cmd);

                cmd.append(SetCommand.create(editingDomain,
                        nodeGraphicsInfo,
                        Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_LaneId(),
                        lane.getId()));

                Coordinates coords = nodeGraphicsInfo.getCoordinates();

                if (coords != null) {
                    // Rest the coordinates too, in order that diagram
                    // editor 'sees' the change.
                    coords =
                            (Coordinates) EcoreUtil.copy(nodeGraphicsInfo
                                    .getCoordinates());
                    cmd.append(SetCommand.create(editingDomain,
                            nodeGraphicsInfo,
                            Xpdl2Package.eINSTANCE
                                    .getNodeGraphicsInfo_Coordinates(),
                            coords));
                } else {
                    coords = Xpdl2Factory.eINSTANCE.createCoordinates(100, 100);

                    cmd.append(SetCommand.create(editingDomain,
                            nodeGraphicsInfo,
                            Xpdl2Package.eINSTANCE
                                    .getNodeGraphicsInfo_Coordinates(),
                            coords));
                }

                // Must set size of anything except event or gateway
                if (activity.getEvent() == null && activity.getRoute() == null) {
                    if (nodeGraphicsInfo.getHeight() < 1) {
                        cmd.append((SetCommand.create(editingDomain,
                                nodeGraphicsInfo,
                                Xpdl2Package.eINSTANCE
                                        .getNodeGraphicsInfo_Height(),
                                (double)ProcessWidgetConstants.TASK_HEIGHT_SIZE)));
                    }

                    if (nodeGraphicsInfo.getWidth() < 1) {
                        cmd.append((SetCommand.create(editingDomain,
                                nodeGraphicsInfo,
                                Xpdl2Package.eINSTANCE
                                        .getNodeGraphicsInfo_Width(),
                                (double)ProcessWidgetConstants.TASK_WIDTH_SIZE)));
                    }
                }

                NodeGraphicsInfo laneNgi =
                        Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(lane,
                                editingDomain,
                                cmd);

                if ((coords.getYCoordinate() + nodeGraphicsInfo.getHeight()) > laneNgi
                        .getHeight()) {
                    // Make sure tyhere's enough room in lane.
                    cmd
                            .append((SetCommand
                                    .create(editingDomain,
                                            laneNgi,
                                            Xpdl2Package.eINSTANCE
                                                    .getNodeGraphicsInfo_Height(),
                                            coords.getYCoordinate()
                                                    + nodeGraphicsInfo
                                                            .getHeight() + 50)));

                } else {
                    // Even if there is enough room in lane we need to make sure
                    // lane gets refreshed correctly in diagram editor (if
                    // displaying).
                    // Resetting its height will do so.
                    cmd
                            .append((SetCommand
                                    .create(editingDomain,
                                            laneNgi,
                                            Xpdl2Package.eINSTANCE
                                                    .getNodeGraphicsInfo_Height(),
                                            laneNgi.getHeight())));
                }

                return cmd;
            }
        }
        return null;
    }

    /**
     * @param editingDomain
     * @param cmd
     * @param process
     * @return
     */
    private Lane getFirstLaneInTheProcess(CompoundCommand cmd,
            EditingDomain editingDomain, Process process) {
        Collection<Pool> processPools = Xpdl2ModelUtil.getProcessPools(process);
        Lane lane = null;
        if (processPools.isEmpty()) {
            Pool pool =
                    ElementsFactory.createPool(Messages.DefaultPool_label,
                            process.getId());
            lane = createLane(pool);
            pool.getLanes().add(lane);
            Package xpdl2Package = process.getPackage();
            cmd.append(AddCommand.create(editingDomain,
                    xpdl2Package,
                    Xpdl2Package.eINSTANCE.getPackage_Pools(),
                    pool));
        } else {
            Pool pool = processPools.iterator().next();
            List<Lane> lanes = pool.getLanes();
            if (lanes.isEmpty()) {
                lane = createLane(pool);
                cmd.append(AddCommand.create(editingDomain,
                        pool,
                        Xpdl2Package.eINSTANCE.getPool_Lanes(),
                        lane));
            } else {
                lane = lanes.get(0);
            }
        }
        return lane;
    }

    /**
     * @param pool
     * @return
     */
    private Lane createLane(Pool pool) {
        Lane lane;
        lane = Xpdl2Factory.eINSTANCE.createLane();
        lane
                .setName(NameUtil
                        .getInternalName(Messages.AssociateActivityLaneResolution_DefaultLane_label,
                                false));
        Xpdl2ModelUtil.setOtherAttribute(lane,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Messages.AssociateActivityLaneResolution_1);
        return lane;
    }
}
