/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.util.NodeGraphicInfoOclListener;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.util.OldNodeGraphicInfoOclListener;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.adapters.PoolAdapter;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.ocl.OclQueryListener;

/**
 * BPMN Pool wrapper around XPDL2's Pool
 * 
 * @author wzurek
 */
public class Xpdl2PoolAdapter extends Xpdl2BaseProcessAdapter
        implements PoolAdapter, OclQueryListener {

    private NodeGraphicInfoOclListener nodeGraphicChangeListener;

    private OldNodeGraphicInfoOclListener oldNodeGraphicChangeListener;

    private Pool getPool() {
        return (Pool) getTarget();
    }

    @Override
    public Command getDeleteCommand(EditingDomain editingDomain) {
        CompoundCommand result = new CompoundCommand();

        // Delete the Lanes contained by this pool...
        List lanes = getLanes();

        for (Iterator l = lanes.iterator(); l.hasNext();) {
            Lane lane = (Lane) l.next();

            // Get adapter for lane so that we can use it's adapter
            // delete command which should deal with deleting objects
            // that are contained within them in diagram but not in model
            BaseProcessAdapter adapter = (BaseProcessAdapter) factory
                    .adapt(lane, ProcessWidgetConstants.ADAPTER_TYPE);
            result.append(adapter.getDeleteCommand(editingDomain));
        }

        // Delete Message flows that this pool is a source/target of.
        List sourceMessageFlows = getSourceMessageFlows();
        if (sourceMessageFlows != null && sourceMessageFlows.size() > 0) {
            result.append(
                    RemoveCommand.create(editingDomain, sourceMessageFlows));
        }

        List targetMessageFlows = getTargetMessageFlows();
        if (targetMessageFlows != null && targetMessageFlows.size() > 0) {
            result.append(
                    RemoveCommand.create(editingDomain, targetMessageFlows));
        }

        // and finally, delete the pool itself
        Pool pool = getPool();
        result.append(RemoveCommand.create(editingDomain,
                pool.eContainer(),
                pool.eContainingFeature(),
                pool));

        result.setLabel(Messages.Xpdl2PoolAdapter_DeletePool_menu);

        return result;
    }

    @Override
    public List getLanes() {
        return getPool().getLanes();
    }

    @Override
    public Command getCreateNewLaneCommand(EditingDomain editingDomain,
            int position, String fillColor, String lineColor) {
        Pool pool = getPool();

        Lane lane = Xpdl2Factory.eINSTANCE.createLane();

        String defaultName;
        if (ProcessWidgetType.TASK_LIBRARY.equals(getProcessType())) {
            defaultName = Messages.TaskSet_labelTaskSetDefaultName_label;
        } else if (ProcessWidgetType.DECISION_FLOW.equals(getProcessType())) {
            defaultName =
                    Messages.DecisionFlow_labelDecisionFlowDefaultName_label;
        } else {
            defaultName = Messages.Xpdl2PoolAdapter_DefaultLaneName_label;
        }

        String finalName = defaultName;
        int index = 1;
        while (isDuplicateLaneName(pool, finalName)) {
            index++;
            finalName = defaultName + " " + index; //$NON-NLS-1$
        }

        lane.setName(NameUtil.getInternalName(finalName, false));

        Xpdl2ModelUtil.setOtherAttribute(lane,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                finalName);

        NodeGraphicsInfo nodeGraphicsInfo =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(lane);

        nodeGraphicsInfo.setIsVisible(true);

        nodeGraphicsInfo.setHeight(LaneAdapter.DEFAULT_LANE_SIZE);
        nodeGraphicsInfo.setFillColor(fillColor);
        nodeGraphicsInfo.setBorderColor(lineColor);

        Command add = AddCommand.create(editingDomain,
                pool,
                Xpdl2Package.eINSTANCE.getPool_Lanes(),
                lane,
                position);

        CompoundCommand cmd = new CompoundCommand();
        cmd.append(add);
        cmd.setLabel(Messages.Xpdl2PoolAdapter_AddLanePool_menu);

        return cmd;
    }

    private boolean isDuplicateLaneName(Pool pool, String finalName) {
        for (Lane lane : pool.getLanes()) {
            if (finalName.equals(Xpdl2ModelUtil.getDisplayNameOrName(lane))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getName() {
        String name = null;

        if (getPool() != null) {
            name = Xpdl2ModelUtil.getDisplayNameOrName(getPool());
        }

        if (name == null) {
            name = ""; //$NON-NLS-1$
        }

        return name;
    }

    @Override
    public String getTokenName() {
        String name = null;

        if (getPool() != null) {
            name = getPool().getName();
        }

        if (name == null) {
            name = ""; //$NON-NLS-1$
        }

        return name;
    }

    @Override
    public String getId() {
        return getPool().getId();
    }

    @Override
    public Command getSetNameCommand(EditingDomain editingDomain, String name) {
        return Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                getPool(),
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                name);
    }

    @Override
    public void notifyChanged(Notification msg) {
        /* Sid XPD-8302 - pass message in so can ignore adapter removal */
        fireDiagramNotification(msg);
    }

    public Command getSetIdCommand(EditingDomain editingDomain, String newId) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public String getFillColor() {
        NodeGraphicsInfo gi = getGraphicalInfo(getPool());
        if (gi != null) {
            return gi.getFillColor();
        }
        return null;
    }

    @Override
    public Command getSetFillColorCommand(EditingDomain editingDomain,
            String newColor) {

        CompoundCommand cmd = new CompoundCommand();
        GraphicalNode gn = getPool();

        NodeGraphicsInfo gInfo = Xpdl2ModelUtil
                .getOrCreateNodeGraphicsInfo(gn, editingDomain, cmd);

        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_FillColor(),
                newColor));

        cmd.setLabel(Messages.Xpdl2PoolAdapter_SetFillColorPool_menu);

        return cmd;
    }

    @Override
    public String getLineColor() {
        NodeGraphicsInfo gi = getGraphicalInfo(getPool());
        if (gi != null) {
            return gi.getBorderColor();
        }
        return null;
    }

    @Override
    public Command getSetLineColorCommand(EditingDomain editingDomain,
            String newColor) {

        CompoundCommand cmd = new CompoundCommand();
        GraphicalNode gn = getPool();

        NodeGraphicsInfo gInfo = Xpdl2ModelUtil
                .getOrCreateNodeGraphicsInfo(gn, editingDomain, cmd);

        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_BorderColor(),
                newColor));

        cmd.setLabel(Messages.Xpdl2PoolAdapter_SetLineColorPool_menu);

        return cmd;
    }

    /*
     * Called with newTarget = null when removing from model object.
     */
    @Override
    public void setTarget(Notifier newTarget) {
        if (getTarget() != null) {
            if (nodeGraphicChangeListener != null) {
                nodeGraphicChangeListener.getTarget().eAdapters()
                        .remove(nodeGraphicChangeListener);
            }

            if (oldNodeGraphicChangeListener != null) {
                oldNodeGraphicChangeListener.getTarget().eAdapters()
                        .remove(oldNodeGraphicChangeListener);
            }
        }
        super.setTarget(newTarget);
        if (getTarget() != null) {
            if (nodeGraphicChangeListener == null) {
                nodeGraphicChangeListener =
                        new NodeGraphicInfoOclListener(this);
            }
            getTarget().eAdapters().add(nodeGraphicChangeListener);

            if (oldNodeGraphicChangeListener == null) {
                oldNodeGraphicChangeListener =
                        new OldNodeGraphicInfoOclListener(this);
            }
            getTarget().eAdapters().add(oldNodeGraphicChangeListener);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.models.edit.util.ocl.OclQueryListener#oclNotifyChanged(org.
     * eclipse.emf.ecore.EObject, java.lang.Object,
     * org.eclipse.emf.common.notify.Notification)
     */
    @Override
    public void oclNotifyChanged(EObject base, Object target,
            Notification notification) {
        /* Sid XPD-8302 - pass message in so can ignore adapter removal */
        fireDiagramNotification(notification);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.PoolAdapter#getSetIsClosedCommand
     * (org.eclipse.emf.edit.domain.EditingDomain, boolean)
     */
    @Override
    public Command getSetIsClosedCommand(EditingDomain editingDomain,
            boolean isClosed) {
        CompoundCommand cmd = new CompoundCommand();
        GraphicalNode gn = getPool();

        NodeGraphicsInfo gInfo = Xpdl2ModelUtil
                .getOrCreateNodeGraphicsInfo(gn, editingDomain, cmd);

        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_IsVisible(),
                !isClosed));

        if (isClosed) {
            cmd.setLabel(Messages.Xpdl2PoolAdapter_OpenPool_menu);
        } else {
            cmd.setLabel(Messages.Xpdl2PoolAdapter_ClosePool_menu);
        }

        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.adapters.PoolAdapter#isClosed()
     */
    @Override
    public boolean isClosed() {
        boolean closed = false;

        NodeGraphicsInfo gi = getGraphicalInfo(getPool());
        if (gi != null) {
            closed = !gi.isIsVisible();
        }

        return closed;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.MessageFlowNodeAdapter#
     * getCreateMessageFlowCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, java.util.List, java.lang.Double,
     * java.lang.Double)
     */
    @Override
    public Command getCreateMessageFlowCommand(EditingDomain editingDomain,
            EObject targetNode, List bendPoints, Double startAnchorPos,
            Double endAnchorPos, String lineColor) {
        if (targetNode instanceof UniqueIdElement) {
            MessageFlow flow = ElementsFactory.createMessageFlow(getPool(),
                    (UniqueIdElement) targetNode,
                    bendPoints,
                    startAnchorPos,
                    endAnchorPos,
                    lineColor);

            return AddCommand.create(editingDomain,
                    getPool().getParentPackage(),
                    Xpdl2Package.eINSTANCE.getPackage_MessageFlows(),
                    flow);
        }

        return UnexecutableCommand.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.MessageFlowNodeAdapter#
     * getSourceMessageFlows()
     */
    @Override
    public List getSourceMessageFlows() {
        Package pkg = getPool().getParentPackage();
        if (pkg != null) {
            return pkg.getMessageFlowFrom(getPool().getId());
        }
        return Collections.EMPTY_LIST;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.adapters.MessageFlowNodeAdapter#
     * getTargetMessageFlows()
     */
    @Override
    public List getTargetMessageFlows() {
        Package pkg = getPool().getParentPackage();
        if (pkg != null) {
            return pkg.getMessageFlowTo(getPool().getId());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Command getAddLaneCommand(EditingDomain editingDomain,
            Object laneModel, int toIndex) {
        if (laneModel instanceof Lane) {
            Lane lane = (Lane) laneModel;

            CompoundCommand cmd = new CompoundCommand();

            cmd.append(RemoveCommand.create(editingDomain, lane));

            cmd.append(AddCommand.create(editingDomain,
                    getPool(),
                    Xpdl2Package.eINSTANCE.getPool_Lanes(),
                    lane,
                    toIndex));

            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public Command getMoveLaneCommand(EditingDomain editingDomain,
            int fromIndex, int toIndex) {

        EList lanes = getPool().getLanes();

        if (fromIndex != toIndex && fromIndex >= 0 && fromIndex < lanes.size()
                && toIndex >= 0 && toIndex < lanes.size()) {

            Lane laneToMove = (Lane) lanes.get(fromIndex);

            return MoveCommand.create(editingDomain,
                    getPool(),
                    Xpdl2Package.eINSTANCE.getPool_Lanes(),
                    laneToMove,
                    toIndex);
        }

        return UnexecutableCommand.INSTANCE;
    }

}
