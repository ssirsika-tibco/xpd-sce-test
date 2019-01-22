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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.ConnectionLabelPosition;
import com.tibco.xpd.processwidget.adapters.FlowNodeAdapter;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author wzurek
 */
public abstract class Xpdl2BaseFlowObjectAdapter extends
        Xpdl2BaseGraphicalNodeAdapter implements FlowNodeAdapter,
        NamedElementAdapter, BaseGraphicalNodeAdapter {

    private static class DeleteActivityCommand extends AbstractCommand {

        private final Activity activity;

        private CompoundCommand command;

        private final EditingDomain editingDomain;

        private final AdapterFactory adapterFactory;

        private Command removeCommand;

        public DeleteActivityCommand(EditingDomain editingDomain,
                Activity activity, AdapterFactory adapterFactory) {
            this.editingDomain = editingDomain;
            this.activity = activity;
            this.adapterFactory = adapterFactory;
        }

        @Override
        protected boolean prepare() {
            removeCommand = RemoveCommand.create(editingDomain, activity);
            command = new CompoundCommand();
            return removeCommand.canExecute();
        }

        public void execute() {
            Xpdl2BaseFlowObjectAdapter fo =
                    (Xpdl2BaseFlowObjectAdapter) adapterFactory.adapt(activity,
                            ProcessWidgetConstants.ADAPTER_TYPE);

            boolean flowRejoined = false;

            List targetSequenceFlows = fo.getTargetSequenceFlows();
            List sourceSequenceFlows = fo.getSourceSequenceFlows();

            if (targetSequenceFlows.size() == 1
                    && sourceSequenceFlows.size() == 1) {

                // If there is 1 sequence flow in and 1 out then join the soruce
                // and target objects.
                Transition inFlow = (Transition) targetSequenceFlows.get(0);
                Transition outFlow = (Transition) sourceSequenceFlows.get(0);

                /*
                 * Don't rejoin when the source of the incoming and target of
                 * the outgoing are the same because that would just create a
                 * loop-back which probably isn't what the user wanted.
                 */
                if (!inFlow.getFrom().equals(outFlow.getTo())) {
                    flowRejoined = true;

                    // If one sequence flow is "more interesting" than the other
                    // then prefer to keep it.
                    ConditionType inType =
                            (inFlow.getCondition() == null ? null : inFlow
                                    .getCondition().getType());
                    ConditionType outType =
                            (outFlow.getCondition() == null ? null : outFlow
                                    .getCondition().getType());

                    boolean outMoreInteresting = false;
                    // Always favour in flow if it is conditional/default.
                    if (inType == null) {
                        // Otherwise favour outflow if it is conditional/default
                        if (outType != null) {
                            outMoreInteresting = true;
                        } else {
                            // Both are uncontrolled. If Out has a label and In
                            // doesn't then favour it.
                            if (outFlow.getName() != null
                                    && outFlow.getName().length() > 0) {
                                if (inFlow.getName() == null
                                        || inFlow.getName().length() == 0) {
                                    outMoreInteresting = true;
                                }
                            }
                        }
                    }

                    if (outMoreInteresting) {
                        // out flow is 'more interesting' so remove inflow
                        // and reassign outflow's source.
                        command.appendAndExecute(SetCommand
                                .create(editingDomain,
                                        outFlow,
                                        Xpdl2Package.eINSTANCE
                                                .getTransition_From(),
                                        inFlow.getFrom()));
                        addAndExecute(targetSequenceFlows, command);

                    } else {
                        // in flow is 'more interesting' so remove outflow
                        // and reassign inflow's target.
                        command.appendAndExecute(SetCommand
                                .create(editingDomain,
                                        inFlow,
                                        Xpdl2Package.eINSTANCE
                                                .getTransition_To(),
                                        outFlow.getTo()));
                        addAndExecute(sourceSequenceFlows, command);
                    }
                }

            }

            if (!flowRejoined) {
                addAndExecute(targetSequenceFlows, command);
                addAndExecute(sourceSequenceFlows, command);
            }

            addAndExecute(fo.getSourceMessageFlows(), command);
            addAndExecute(fo.getTargetMessageFlows(), command);
            addAndExecute(fo.getSourceAssociations(), command);
            addAndExecute(fo.getTargetAssociations(), command);

            command.appendAndExecute(RemoveCommand.create(editingDomain,
                    activity));
        }

        void addAndExecute(List list, CompoundCommand cmd) {
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                EObject eo = (EObject) iter.next();
                BaseProcessAdapter ad =
                        (BaseProcessAdapter) adapterFactory.adapt(eo,
                                ProcessWidgetConstants.ADAPTER_TYPE);
                cmd.appendAndExecute(ad.getDeleteCommand(editingDomain));
            }
        }

        public void redo() {
            command.redo();
        }

        @Override
        public void undo() {
            command.undo();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter#getContainer
     * ()
     */
    public Object getContainer() {
        Object container = Xpdl2ModelUtil.getContainer(getActivity());
        return container;
    }

    /**
     * @return all incomming transitions
     */
    public List getTargetSequenceFlows() {
        String id = getActivity().getId();
        FlowContainer flow = getActivity().getFlowContainer();
        if (flow == null || id == null) {
            // TODO WZ this happen during execution of complex commands, need to
            // investigate why
            System.err.println("Unconnected Activity: " + getActivity()); //$NON-NLS-1$
            return Collections.EMPTY_LIST;
        }
        List result = new ArrayList();
        for (Iterator iter = flow.getTransitions().iterator(); iter.hasNext();) {
            Transition tr = (Transition) iter.next();
            if (id.equals(tr.getTo())) {
                result.add(tr);
            }
        }
        return result;
    }

    /**
     * @return all outgoing transitions
     */
    public List getSourceSequenceFlows() {
        String id = getActivity().getId();
        FlowContainer flow = getActivity().getFlowContainer();
        if (flow == null || id == null) {
            // TODO WZ this happen during execution of complex commands, need to
            // investigate why
            System.err.println("Unconnected Activity: " + getActivity()); //$NON-NLS-1$
            return Collections.EMPTY_LIST;
        }
        List result = new ArrayList();
        for (Iterator iter = flow.getTransitions().iterator(); iter.hasNext();) {
            Transition tr = (Transition) iter.next();
            if (id.equals(tr.getFrom())) {
                result.add(tr);
            }
        }
        return result;
    }

    /**
     * @return atapted activity: <code>(Activity) getTarget()</code>
     */
    protected Activity getActivity() {
        return (Activity) getTarget();
    }

    public String getId() {
        String id = getActivity().getId();
        return id == null ? "" : id; //$NON-NLS-1$
    }

    public String getName() {
        return ProcessDataUtil.getActivityName(getActivity());
    }

    public String getTokenName() {
        String name = null;

        if (getActivity() != null) {
            name = getActivity().getName();
        }

        if (name == null) {
            name = ""; //$NON-NLS-1$
        }

        return name;
    }

    private String getDisplayName(NamedElement named) {
        return (String) Xpdl2ModelUtil.getOtherAttribute(named,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName());
    }

    public Command getSetNameCommand(EditingDomain editingDomain, String newName) {

        // 
        // SID - sometimes it would be really useful to have a coherent easy to
        // follow naming convention for activities. Uncomment this and then
        // change any activity name on diaghram to name all the activities - all
        // tasks = "Tn" all events = "En" all gates "Gn"

        // CompoundCommand cmd = new CompoundCommand();
        //
        // Collection<Activity> acts =
        // Xpdl2ModelUtil.getAllActivitiesInProc((Process) getProcess());
        //
        // int actIdx = 1;
        // int evIdx = 1;
        // int gateIdx = 1;
        // for (Activity a : acts) {
        // String aName;
        // if (a.getEvent() != null) {
        //                aName = "E" + evIdx++; //$NON-NLS-1$
        // } else if (a.getRoute() != null) {
        //                aName = "G" + gateIdx++; //$NON-NLS-1$
        // } else {
        //                aName = "T" + actIdx++; //$NON-NLS-1$
        // }
        //            
        // cmd.append(Xpdl2ModelUtil
        // .getSetOtherAttributeCommand(editingDomain,
        // a,
        // XpdExtensionPackage.eINSTANCE
        // .getDocumentRoot_DisplayName(),
        // aName));
        //
        // }
        //
        // cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
        // getActivity(),
        // XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
        // newName));
        // return cmd;

        return Xpdl2ModelUtil.getSetOtherAttributeCommand(editingDomain,
                getActivity(),
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                newName);
    }

    @Override
    public String getIcon() {
        NodeGraphicsInfo gi = getGraphicalInfo(getActivity());
        if (gi != null) {
            return gi.getShape();
        }
        return null;
        // XPDActivity xAct = XPDExtUtil.getXPDActivity(getActivity());
        // return xAct.getIcon();
    }

    @Override
    public Command getSetIconCommand(EditingDomain editingDomain, String newIcon) {
        return UnexecutableCommand.INSTANCE;
        // XPDActivity xAct = XPDExtUtil.getXPDActivity(getActivity());
        // return SetCommand.create(editingDomain, xAct, XpdExtPackage.eINSTANCE
        // .getXPDActivity_Icon(), newIcon);
    }

    public String getFillColor() {
        NodeGraphicsInfo gi = getGraphicalInfo(getActivity());
        if (gi != null) {
            return gi.getFillColor();
        }
        return null;
    }

    public Command getSetFillColorCommand(EditingDomain editingDomain,
            String newColor) {

        CompoundCommand cmd = new CompoundCommand();
        GraphicalNode act = getGraphicalNode();

        NodeGraphicsInfo gInfo =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(act,
                        editingDomain,
                        cmd);

        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_FillColor(),
                newColor));

        cmd.setLabel(Messages.Xpdl2BaseFlowObjectAdapter_SetFillColor_menu);

        return cmd;
    }

    public String getLineColor() {
        NodeGraphicsInfo gi = getGraphicalInfo(getActivity());
        if (gi != null) {
            return gi.getBorderColor();
        }
        return null;
    }

    public Command getSetLineColorCommand(EditingDomain editingDomain,
            String newColor) {
        CompoundCommand cmd = new CompoundCommand();
        GraphicalNode act = getGraphicalNode();

        NodeGraphicsInfo gInfo =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(act,
                        editingDomain,
                        cmd);

        cmd.append(SetCommand.create(editingDomain,
                gInfo,
                Xpdl2Package.eINSTANCE.getNodeGraphicsInfo_BorderColor(),
                newColor));

        cmd.setLabel(Messages.Xpdl2BaseFlowObjectAdapter_SetLineColor_menu);
        return cmd;
    }

    @Override
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
    public Command getSetFontCommand(EditingDomain editingDomain, String newFont) {
        return UnexecutableCommand.INSTANCE;
    }

    public Command getDeleteCommand(EditingDomain editingDomain) {
        Activity act = getActivity();

        return new DeleteActivityCommand(editingDomain, act,
                getAdapterFactory());
    }

    public Command getCreateSequenceFlowCommand(EditingDomain editingDomain,
            EObject targetNode, SequenceFlowType flowType, String condition,
            String grammarId, List bendPoints, Double startAnchorPos,
            Double endAnchorPos, String name, ConnectionLabelPosition labelPos,
            String lineColor) {

        if (targetNode instanceof UniqueIdElement) {
            Activity act = getActivity();

            Transition tr =
                    ElementsFactory.createTransition(act,
                            (UniqueIdElement) targetNode,
                            flowType,
                            condition,
                            grammarId,
                            bendPoints,
                            startAnchorPos,
                            endAnchorPos,
                            name,
                            labelPos,
                            lineColor);

            return AddCommand.create(editingDomain,
                    act.getFlowContainer(),
                    Xpdl2Package.eINSTANCE.getFlowContainer_Transitions(),
                    tr);
        }
        return UnexecutableCommand.INSTANCE;
    }

    public List getSourceMessageFlows() {
        Process proc = getActivity().getProcess();
        if (proc != null) {
            return proc.getPackage().getMessageFlowFrom(getActivity().getId());
        }
        return Collections.EMPTY_LIST;
    }

    public List getTargetMessageFlows() {
        Process proc = getActivity().getProcess();
        if (proc != null) {
            return proc.getPackage().getMessageFlowTo(getActivity().getId());
        }
        return Collections.EMPTY_LIST;
    }

    public Command getCreateMessageFlowCommand(EditingDomain editingDomain,
            EObject targetNode, List bendPoints, Double startAnchorPos,
            Double endAnchorPos, String lineColor) {

        if (targetNode instanceof UniqueIdElement) {
            MessageFlow flow =
                    ElementsFactory.createMessageFlow(getActivity(),
                            (UniqueIdElement) targetNode,
                            bendPoints,
                            startAnchorPos,
                            endAnchorPos,
                            lineColor);

            return AddCommand.create(editingDomain, getActivity().getProcess()
                    .getPackage(), Xpdl2Package.eINSTANCE
                    .getPackage_MessageFlows(), flow);
        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public boolean canSetName() {
        if (getActivity() == null
                || ProcessInterfaceUtil.isEventImplemented(getActivity())) {
            // Cannot set the name of activity (start/intermediate event/recevie
            // task) that implements a process interface-defined method.
            return false;
        }

        return true;
    }

}
