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
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Split;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TransitionRef;
import com.tibco.xpd.xpdl2.TransitionRestriction;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ocl.NotificationsConstants;
import com.tibco.xpd.xpdl2.util.ocl.SimpleViewerNotification;

/**
 * SequenceFlow (BPMN's Sequence Flow) adapter to the XPDL2 Transition
 * 
 * 
 * TODO WZ add listener to Conditions's type
 * 
 * @author wzurek
 */
public class Xpdl2SequenceFlowAdapter extends Xpdl2BaseConnectionAdapter
        implements SequenceFlowAdapter {

    private static final String EMPTY_STRING = "";//$NON-NLS-1$

    public Transition getTransition() {
        return (Transition) getTarget();
    }

    @Override
    public EObject getSourceNode() {
        Transition tr = getTransition();
        return tr.getFlowContainer().getActivity(tr.getFrom());
    }

    @Override
    public EObject getTargetNode() {
        Transition tr = getTransition();
        return tr.getFlowContainer().getActivity(tr.getTo());
    }

    @Override
    public String getCondition() {
        if (getFlowType() == SequenceFlowType.CONDITIONAL_LITERAL) {
            Transition tr = getTransition();
            Condition cnd = tr.getCondition();
            if (cnd != null) {

                FeatureMap mixed;
                if (cnd.getExpression() != null) {
                    mixed = cnd.getExpression().getMixed();
                } else {
                    mixed = cnd.getMixed();
                }

                List texts = (List) mixed.get(
                        XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                        false);
                if (texts == null) {
                    return EMPTY_STRING;
                } else if (texts.size() == 1) {
                    return (String) texts.get(0);
                } else {
                    StringBuffer sb = new StringBuffer();
                    for (Iterator iter = texts.iterator(); iter.hasNext();) {
                        sb.append(iter.next());
                    }
                    return sb.toString();
                }
            }
            return EMPTY_STRING;
        } else {
            return null;
        }
    }

    /**
     * 
     */
    @Override
    public SequenceFlowType getFlowType() {
        return getFlowType(getTransition());
    }

    public static SequenceFlowType getFlowType(Transition tr) {
        SequenceFlowType result = SequenceFlowType.UNCONTROLLED_LITERAL;
        Condition cnd = tr.getCondition();
        if (cnd != null) {
            ConditionType tp = cnd.getType();
            if (tp != null) {
                switch (tp.getValue()) {
                case ConditionType.CONDITION:
                    result = SequenceFlowType.CONDITIONAL_LITERAL;
                    break;
                case ConditionType.OTHERWISE:
                    result = SequenceFlowType.DEFAULT_LITERAL;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void notifyChanged(Notification msg) {
        if (msg instanceof SimpleViewerNotification) {
            /* Sid XPD-8302 - pass message in so can ignore adapter removal */
            fireDiagramNotification(msg);
        } else {
            if (!msg.isTouch()) {

                Transition tr = getTransition();
                FlowContainer fc = tr.getFlowContainer();

                if (msg.getNotifier() == getTarget()
                        && (msg.getEventType() == Notification.SET
                                || msg.getEventType() == Notification.UNSET)) {
                    switch (msg.getFeatureID(Transition.class)) {
                    case Xpdl2Package.NAMED_ELEMENT__NAME:
                        /*
                         * Sid XPD-8302 - pass message in so can ignore adapter
                         * removal
                         */
                        fireDiagramNotification(msg);
                        break;
                    case Xpdl2Package.TRANSITION__FROM:
                    case Xpdl2Package.TRANSITION__TO:
                        // notify activities about connection reconnect
                        String oldVal = msg.getOldStringValue();
                        String newVal = msg.getNewStringValue();
                        EObject act;
                        act = fc.getActivity(oldVal);
                        if (act != null) {
                            act.eNotify(new NotificationImpl(
                                    NotificationsConstants.REFRESH_REFERENCED_ELEMENT,
                                    null, null));
                        }
                        act = fc.getActivity(newVal);
                        if (act != null) {
                            act.eNotify(new NotificationImpl(
                                    NotificationsConstants.REFRESH_REFERENCED_ELEMENT,
                                    null, null));
                        }
                        break;
                    case Xpdl2Package.TRANSITION__CONDITION:
                        // notify about change of transition type
                        /*
                         * Sid XPD-8302 - pass message in so can ignore adapter
                         * removal
                         */
                        fireDiagramNotification(msg);
                        break;

                    default:
                        if (msg.getFeature() == XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName()) {
                            // The display (label) name has changed..
                            /*
                             * Sid XPD-8302 - pass message in so can ignore
                             * adapter removal
                             */
                            fireDiagramNotification(msg);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Command getSetFlowTypeCommand(EditingDomain editingDomain,
            SequenceFlowType data) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.setLabel(
                Messages.Xpdl2SequenceFlowAdapter_SetTypeSequenceFlow_menu);
        switch (data.getValue()) {
        case SequenceFlowType.UNCONTROLLED: {
            Transition tr = getTransition();
            cmd.append(SetCommand.create(editingDomain,
                    tr,
                    Xpdl2Package.eINSTANCE.getTransition_Condition(),
                    null));

            return cmd;
        }
        case SequenceFlowType.DEFAULT: {
            Condition c = Xpdl2Factory.eINSTANCE.createCondition();
            c.setType(ConditionType.OTHERWISE_LITERAL);

            Transition tr = getTransition();
            cmd.append(SetCommand.create(editingDomain,
                    tr,
                    Xpdl2Package.eINSTANCE.getTransition_Condition(),
                    c));
            return cmd;
        }
        case SequenceFlowType.CONDITIONAL: {
            Condition c = Xpdl2Factory.eINSTANCE.createCondition();
            c.setType(ConditionType.CONDITION_LITERAL);

            Expression exp = Xpdl2Factory.eINSTANCE.createExpression();
            c.setExpression(exp);

            Transition tr = getTransition();
            cmd.append(SetCommand.create(editingDomain,
                    tr,
                    Xpdl2Package.eINSTANCE.getTransition_Condition(),
                    c));
            return cmd;
        }
        }
        return cmd;
    }

    @Override
    public Command getSetTargetCommand(EditingDomain editingDomain,
            EObject targetNode, Double endAnchorPos) {
        if (targetNode instanceof UniqueIdElement) {

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(
                    Messages.Xpdl2SequenceFlowAdapter_ReconnectSequenceFlow_menu);
            cmd.append(SetCommand.create(editingDomain,
                    getTransition(),
                    Xpdl2Package.eINSTANCE.getTransition_To(),
                    ((UniqueIdElement) targetNode).getId()));

            appendResetEndAnchorCommand(editingDomain, cmd, endAnchorPos);
            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public Command getSetSourceCommand(EditingDomain editingDomain,
            EObject sourceNode, Double startAnchorPos) {
        if (sourceNode instanceof UniqueIdElement) {

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(
                    Messages.Xpdl2SequenceFlowAdapter_ReconnectSequenceFlow_menu);
            cmd.append(SetCommand.create(editingDomain,
                    getTransition(),
                    Xpdl2Package.eINSTANCE.getTransition_From(),
                    ((UniqueIdElement) sourceNode).getId()));

            appendResetStartAnchorCommand(editingDomain, cmd, startAnchorPos);
            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.
     * Xpdl2BaseFlowAdapter
     * #getDeleteCommand(org.eclipse.emf.edit.domain.EditingDomain)
     */
    @Override
    public Command getDeleteCommand(EditingDomain editingDomain) {
        CompoundCommand cmd = new CompoundCommand();

        cmd.setLabel(Messages.Xpdl2SequenceFlowAdapter_DeleteSequenceFlow_menu);

        Command delCmd = super.getDeleteCommand(editingDomain);

        if (delCmd == null) {
            return UnexecutableCommand.INSTANCE;
        }

        cmd.append(delCmd);
        return cmd;
    }

    @Override
    public String getExistingSetScriptGrammarId() {
        String grammarId = null;
        if (getFlowType() != SequenceFlowType.CONDITIONAL_LITERAL) {
            return grammarId;
        }
        Transition tr = getTransition();
        Condition cnd = tr.getCondition();
        if (cnd != null) {
            Expression expression = cnd.getExpression();
            if (expression != null) {
                grammarId = expression.getScriptGrammar();
            }
        }
        return grammarId;
    }

    @Override
    public EObject getTransitionObject() {
        return getTransition();
    }

    @Override
    public List<Object> getOutgoingFlowInOrder() {
        List<Object> flowsInOrder = new ArrayList<Object>();

        Activity act = (Activity) getSourceNode();
        if (act != null) {
            Split split = null;

            EList<TransitionRestriction> restrictions =
                    act.getTransitionRestrictions();
            for (TransitionRestriction tr : restrictions) {
                split = tr.getSplit();
                if (split != null) {
                    break;
                }
            }

            EList<Transition> outTrans = act.getOutgoingTransitions();

            if (split != null) {
                for (TransitionRef tr : split.getTransitionRefs()) {
                    String id = tr.getId();
                    if (id != null) {
                        for (Transition t : outTrans) {
                            if (id.equals(t.getId())) {
                                flowsInOrder.add(t);
                                break;
                            }
                        }
                    }
                }
            } else {
                for (Transition t : outTrans) {
                    flowsInOrder.add(t);
                }
            }
        }

        return flowsInOrder;
    }

    @Override
    public Command getSwapSequenceFlowOrderCommand(EditingDomain domain,
            Object swapWithSequenceFlow) {
        if (swapWithSequenceFlow instanceof Transition) {
            Transition otherTransition = (Transition) swapWithSequenceFlow;
            Transition transition = getTransition();
            Activity act = (Activity) getSourceNode();

            String thisId = transition.getId();
            String otherId = otherTransition.getId();

            Split split = null;

            EList<TransitionRestriction> restrictions =
                    act.getTransitionRestrictions();
            for (TransitionRestriction tr : restrictions) {
                split = tr.getSplit();
                if (split != null) {
                    break;
                }
            }

            if (split != null) {
                // Find the 2 TransitionRef's for our 2 transitions...
                TransitionRef thisRef = null;
                TransitionRef otherRef = null;

                for (TransitionRef ref : split.getTransitionRefs()) {
                    if (thisId.equals(ref.getId())) {
                        thisRef = ref;
                    } else if (otherId.equals(ref.getId())) {
                        otherRef = ref;
                    }
                }

                if (thisRef != null && otherRef != null) {
                    CompoundCommand cmd = new CompoundCommand();

                    cmd.append(SetCommand.create(domain,
                            thisRef,
                            Xpdl2Package.eINSTANCE.getTransitionRef_Id(),
                            otherId));
                    cmd.append(SetCommand.create(domain,
                            otherRef,
                            Xpdl2Package.eINSTANCE.getTransitionRef_Id(),
                            thisId));

                    return cmd;

                }

            } else {
                // We don't have a split TransitionRestriction at all (probably
                // xpdl from some other app) so just perform a dummy change to
                // our transition (the MaintainTransitionRestrictions preCommit
                // listener should then fix-up the TransitionRestrictions).
                //
                // Ok, they won't reflect the 'sort order change' user has just
                // performed, but when the user does it again it will work.
                CompoundCommand cmd = new CompoundCommand();

                String fromId = transition.getFrom();

                cmd.append(SetCommand.create(domain,
                        transition,
                        Xpdl2Package.eINSTANCE.getTransition_From(),
                        "dummChange")); //$NON-NLS-1$

                cmd.append(SetCommand.create(domain,
                        transition,
                        Xpdl2Package.eINSTANCE.getTransition_From(),
                        fromId));

                return cmd;

            }

        }

        return UnexecutableCommand.INSTANCE;
    }
}
