package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author rsomayaj
 * 
 */

public class DeleteActivityCommand extends AbstractCommand {

    private final Activity activity;

    private CompoundCommand command;

    private final EditingDomain editingDomain;

    private Command removeCommand;

    private Package rootPackage;

    public DeleteActivityCommand(EditingDomain editingDomain, Activity activity) {
        this.editingDomain = editingDomain;
        this.activity = activity;
        this.rootPackage = activity.getProcess().getPackage();
    }

    protected boolean prepare() {
        removeCommand = RemoveCommand.create(editingDomain, activity);
        command = new CompoundCommand(Messages.DeleteActivityCommand_RemoveIfcEvents_shortdesc);
        return removeCommand.canExecute();
    }

    public void execute() {
        addAndExecute(Xpdl2ModelUtil.getOutgoingTransitions(activity.getId(),
                activity.getProcess()), command);
        addAndExecute(Xpdl2ModelUtil.getIncomingTransitions(activity.getId(),
                activity.getProcess()), command);
        addAndExecute(getIncomingMessageFlows(activity.getId(), activity
                .getProcess()), command);
        addAndExecute(getOutgoingMessageFlows(activity.getId(), activity
                .getProcess()), command);
        addAndExecute(getIncomingAssociations(activity.getId(), activity
                .getProcess()), command);
        addAndExecute(getOutgoingAssociations(activity.getId(), activity
                .getProcess()), command);

        command.appendAndExecute(RemoveCommand.create(editingDomain, activity));
    }

    void addAndExecute(List list, CompoundCommand cmd) {
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            EObject eo = (EObject) iter.next();
            cmd.appendAndExecute(RemoveCommand.create(editingDomain, eo));
        }
    }

    public void redo() {
        command.redo();
    }

    public void undo() {
        command.undo();
    }

    /**
     * Get all incoming transitions for given activity.
     * 
     * @param activityId
     * 
     * @return LIst of transitions (sequence flow) whose target is the given
     *         activity.
     */
    public static List<MessageFlow> getIncomingMessageFlows(String activityId,
            Process process) {
        List<MessageFlow> messageFlows = new ArrayList<MessageFlow>();

        Collection<MessageFlow> allMessageFlows =
                Xpdl2ModelUtil.getAllMessageFlowsInProc(process);

        if (process != null) {
            for (MessageFlow messageFlow : allMessageFlows) {
                if (activityId.equals(messageFlow.getSource())) {
                    messageFlows.add(messageFlow);
                }
            }
        }

        return messageFlows;

    }

    /**
     * Get all outgoing transitions for given activity.
     * 
     * @param activityId
     * 
     * @return List of transitions (sequence flow) whose source is the given
     *         activity.
     */
    public static List<MessageFlow> getOutgoingMessageFlows(String actId,
            Process process) {
        List<MessageFlow> messageFlows = new ArrayList<MessageFlow>();

        Collection<MessageFlow> allMessageFlows =
                Xpdl2ModelUtil.getAllMessageFlowsInProc(process);

        if (process != null) {
            for (MessageFlow messageFlow : allMessageFlows) {
                if (actId.equals(messageFlow.getTarget())) {
                    messageFlows.add(messageFlow);
                }
            }
        }

        return messageFlows;

    }

    /**
     * Get all incoming transitions for given activity.
     * 
     * @param activityId
     * 
     * @return LIst of transitions (sequence flow) whose target is the given
     *         activity.
     */
    public static List<Association> getIncomingAssociations(String activityId,
            Process process) {
        List<Association> associations = new ArrayList<Association>();

        Collection<Association> allMessageFlows =
                Xpdl2ModelUtil.getAllAssociationsInProc(process);

        if (process != null) {
            for (Association association : allMessageFlows) {
                if (activityId.equals(association.getSource())) {
                    associations.add(association);
                }
            }
        }
        return associations;
    }

    /**
     * Get all outgoing transitions for given activity.
     * 
     * @param activityId
     * 
     * @return List of transitions (sequence flow) whose source is the given
     *         activity.
     */
    /**
     * Get all incoming transitions for given activity.
     * 
     * @param activityId
     * 
     * @return LIst of transitions (sequence flow) whose target is the given
     *         activity.
     */
    public static List<Association> getOutgoingAssociations(String activityId,
            Process process) {
        List<Association> associations = new ArrayList<Association>();

        Collection<Association> allMessageFlows =
                Xpdl2ModelUtil.getAllAssociationsInProc(process);

        if (process != null) {
            for (Association association : allMessageFlows) {
                if (activityId.equals(association.getTarget())) {
                    associations.add(association);
                }
            }
        }
        return associations;
    }
}