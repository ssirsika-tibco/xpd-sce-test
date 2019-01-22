/**
 * Copyright 2006 TIBCO Software Inc
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.util.NodeGraphicInfoOclListener;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.util.OldNodeGraphicInfoOclListener;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.ocl.OclQueryListener;
import com.tibco.xpd.xpdl2.util.ocl.SimpleViewerNotification;

/**
 * Artifact in XPDL2
 * 
 * @author wzurek
 */
public abstract class Xpdl2BaseGraphicalNodeAdapter
        extends Xpdl2BaseProcessAdapter
        implements BaseGraphicalNodeAdapter, OclQueryListener {

    /** listenr for xpdl graphical node info */
    private NodeGraphicInfoOclListener toolLst;

    private OldNodeGraphicInfoOclListener oldToolLst;

    @Override
    public Command getSetLocationCommand(EditingDomain ed, Point loc,
            Dimension dim) {
        CompoundCommand cmd = new CompoundCommand();
        GraphicalNode act = getGraphicalNode();

        NodeGraphicsInfo gInfo =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(act, ed, cmd);

        cmd.append(SetCommand.create(ed,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Coordinates(),
                Xpdl2Factory.eINSTANCE.createCoordinates(loc.x, loc.y)));

        cmd.append(SetCommand.create(ed,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Height(),
                new Double(dim.height)));
        cmd.append(SetCommand.create(ed,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_Width(),
                new Double(dim.width)));

        cmd.setLabel(
                Messages.Xpdl2BaseGraphicalNodeAdapter_SetObjectLocation_menu);

        return cmd;
    }

    protected GraphicalNode getGraphicalNode() {
        return (GraphicalNode) getTarget();
    }

    @Override
    public void setTarget(Notifier newTarget) {
        if (getTarget() != null) {
            if (toolLst != null) {
                toolLst.getTarget().eAdapters().remove(toolLst);
            }
            if (oldToolLst != null) {
                oldToolLst.getTarget().eAdapters().remove(oldToolLst);
            }
        }
        super.setTarget(newTarget);
        if (getTarget() != null) {
            if (toolLst == null) {
                toolLst = new NodeGraphicInfoOclListener(this);
            }
            getTarget().eAdapters().add(toolLst);

            if (oldToolLst == null) {
                oldToolLst = new OldNodeGraphicInfoOclListener(this);
            }
            getTarget().eAdapters().add(oldToolLst);

        }
    }

    @Override
    public Point getLocation() {
        NodeGraphicsInfo gi = getGraphicalInfo(getGraphicalNode());
        if (gi != null) {
            Coordinates coords = gi.getCoordinates();
            if (coords != null) {
                return new Point(coords.getXCoordinate(),
                        coords.getYCoordinate());
            }
        }
        return new Point(10, 10);
    }

    @Override
    public Dimension getSize() {
        NodeGraphicsInfo gi = getGraphicalInfo(getGraphicalNode());
        if (gi != null) {
            return new Dimension((int) gi.getWidth(), (int) gi.getHeight());
        }
        return new Dimension(10, 10);
    }

    /**
     * Notification from OclNotifier
     * 
     * @param base
     * @param target
     * @param notification
     */
    @Override
    public void oclNotifyChanged(EObject base, Object target, Notification n) {

        if (n.getFeature() == Xpdl2Package.eINSTANCE
                .getNodeGraphicsInfo_LaneId()) {

            Notifier graphicalObject = getTarget();

            Package pkg = null;

            if (graphicalObject instanceof Activity) {
                Process proc = ((Activity) graphicalObject).getProcess();
                if (proc != null) {
                    pkg = ((Activity) graphicalObject).getProcess()
                            .getPackage();
                }
            } else if (graphicalObject instanceof Artifact) {
                pkg = ((Artifact) graphicalObject).getPackage();
            }

            if (pkg != null) {
                // As activity is not a child of lane in xpdl2 model
                // we have to force notifaction to new and old lanes
                // that it has moved so that they update their edit parts.
                // (if the activity is child of activity set (embedded
                // subproc) then no need to worry because activity
                // set listeners will get notified about removal/add
                // of activity from their lists).
                EObject oldNonModelParent = null;
                EObject newNonModelParent = null;

                for (Iterator iter = pkg.getPools().iterator(); iter.hasNext()
                        && (oldNonModelParent == null
                                || newNonModelParent == null);) {
                    Pool pool = (Pool) iter.next();
                    if (n.getOldValue() != null && oldNonModelParent == null) {
                        // See if old parent referenced via lane id is lane in
                        // this pool.
                        oldNonModelParent =
                                pool.getLane((String) n.getOldValue());
                    }

                    if (n.getNewValue() != null && newNonModelParent == null) {
                        // See if old parent referenced via lane id is lane in
                        // this pool.
                        newNonModelParent =
                                pool.getLane((String) n.getNewValue());
                    }
                }

                // For artifacts we also have to notify on activity sets
                // for the same sort of reason.
                // (Optimisation... if modelParents already has 2 items in
                // then we don't need to check if lane id is activity set.
                if (graphicalObject instanceof Artifact) {
                    for (Iterator procs = pkg.getProcesses().iterator(); procs
                            .hasNext()
                            && (oldNonModelParent == null
                                    || newNonModelParent == null);) {
                        Process proc = (Process) procs.next();

                        if (n.getOldValue() != null
                                && oldNonModelParent == null) {
                            // See if old parent referenced via lane id is lane
                            // in this pool.
                            oldNonModelParent = proc
                                    .getActivitySet((String) n.getOldValue());
                        }

                        if (n.getNewValue() != null
                                && newNonModelParent == null) {
                            // See if old parent referenced via lane id is lane
                            // in this pool.
                            newNonModelParent = proc
                                    .getActivitySet((String) n.getNewValue());
                        }
                    }
                }

                // If we foudn parents then notify.
                if (oldNonModelParent != null) {
                    oldNonModelParent.eNotify(new SimpleViewerNotification(
                            oldNonModelParent, true, false));
                }

                if (newNonModelParent != null) {
                    newNonModelParent.eNotify(new SimpleViewerNotification(
                            newNonModelParent, true, false));
                }
            }
        }
        /* Sid XPD-8302 - pass message in so can ignore adapter removal */
        fireDiagramNotification(n);
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public Command getSetIconCommand(EditingDomain editingDomain,
            String newIcon) {
        return UnexecutableCommand.INSTANCE;
    }

    public String getShape() {
        return null;
    }

    @Override
    public Command getSetShapeCommand(EditingDomain editingDomain,
            String newShape) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public String getFont() {
        return null;
    }

    @Override
    public Command getSetFontCommand(EditingDomain editingDomain,
            String newFont) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public List getSourceAssociations() {
        GraphicalNode gn = getGraphicalNode();
        return gn.getOutgoingAssociations();
    }

    @Override
    public List getTargetAssociations() {
        GraphicalNode gn = getGraphicalNode();
        return gn.getIncomingAssociations();
    }

    @Override
    public Command getCreateAssociationCommand(EditingDomain editingDomain,
            EObject targetNode, List bendpoints, Double startAnchorPos,
            Double endAnchorPos, String lineColor) {

        UniqueIdElement sourceNode = (UniqueIdElement) getGraphicalNode();

        if (targetNode instanceof UniqueIdElement) {
            UniqueIdElement target = (UniqueIdElement) targetNode;

            if (sourceNode != target) {
                Package pck = null;
                if (sourceNode instanceof Artifact) {
                    pck = ((Artifact) sourceNode).getPackage();
                } else if (sourceNode instanceof Activity) {
                    if (((Activity) sourceNode).getProcess() != null) {
                        pck = ((Activity) sourceNode).getProcess().getPackage();
                    }
                } else if (sourceNode instanceof Lane) {
                    Pool pp = ((Lane) sourceNode).getParentPool();
                    if (pp != null) {
                        pck = pp.getParentPackage();
                    }
                } else if (sourceNode instanceof Pool) {
                    pck = ((Pool) sourceNode).getParentPackage();
                }

                if (pck == null) {
                    return UnexecutableCommand.INSTANCE;
                }

                Association assoc =
                        ElementsFactory.createAssociation(sourceNode,
                                target,
                                bendpoints,
                                startAnchorPos,
                                endAnchorPos,
                                lineColor);

                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(
                        Messages.Xpdl2BaseGraphicalNodeAdapter_CreateAssociation_menu);
                cmd.append(AddCommand.create(editingDomain,
                        pck,
                        Xpdl2Package.eINSTANCE.getPackage_Associations(),
                        assoc));

                return cmd;
            }
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * Default implementation for graphical nodes. If object being removed from
     * container then DON'T fire diagram notification.
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse
     * .emf.common.notify.Notification)
     */
    @Override
    public void notifyChanged(Notification msg) {
        // Do not fire diagram notification if activity has just had its
        // flow container set to null (else tries to access it's flow container
        // transitions etc when it doesn't have one.
        // Flow container gets set to null on delete activity inside embedded
        // sub-proc.
        if (!(msg.getFeatureID(
                Activity.class) == Xpdl2Package.ACTIVITY__FLOW_CONTAINER
                && msg.getNewValue() == null)) {
            /*
             * Sid XPD-8396 - this fireDiagramNotification() call was
             * accidentally changed to super.notifyChanged() (which does
             * nothing) by XPD-8306
             */
            fireDiagramNotification(msg);
        }
    }

    @Override
    public boolean canSetName() {
        return true;
    }
}
