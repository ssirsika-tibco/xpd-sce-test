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

package com.tibco.xpd.processwidget.adapters;

import java.util.Collection;
import java.util.Set;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.graphics.Image;

/**
 * @author wzurek
 */
public interface TaskAdapter extends FlowNodeAdapter, ProcessContainerAdapter {

    /**
     * @return set of markers of the activity, the client should not modify the
     *         set
     */
    Set getMarkers();

    /**
     * Create command to set a set of markers for an activity
     * 
     * @param editingDomain
     * @param markers
     * @return
     */
    Command getSetMarkersCommand(EditingDomain editingDomain, Set markers);

    /**
     * Create command to change the activity into task of given type.
     * 
     * @param editingDomain
     * @param taskType
     * @return
     */
    Command getSetTaskTypeCommand(EditingDomain editingDomain, TaskType taskType);

    /**
     * Return the type of this task.
     * 
     * @return
     */
    TaskType getTaskType();

    /**
     * Create set performaer command List will container either Participant or
     * DataField of type Performer.
     * 
     * @param ed
     * @param performer
     * @return
     */
    Command getSetPerformersCommand(EditingDomain ed, EObject[] performer);

    /**
     * @return performer of the activity
     */
    EObject[] getActivityPerformers();

    /**
     * @return performar description
     */
    Object getPerformerDescription(EObject performer);

    /**
     * ========================================================================
     * == SUB-PROCESS Task Only methods... These methods will be called only for
     * task of type sub-process
     * ==================================================
     * ========================
     */

    /**
     * Create command to change set the process that this sub-process call task
     * references. The subflow can be null.
     * 
     * @param editingDomain
     * @param subflow
     * @return
     */
    Command getSetSubprocessCommand(EditingDomain editingDomain, EObject subflow);

    /**
     * EObject that represent subflow
     * 
     * @return
     */
    EObject getSubprocess();

    /**
     * Description of the subflow, can be null if subflow is not defined
     * 
     * @return
     */
    String getSubprocessDescription();

    /**
     * Description of the location of the subflow, can return null if the same
     * location as invoking process or subflow is not defined
     * 
     * @return
     */
    String getSubprocessLocationDescription();

    /**
     * Return true if the sub-process (embedded or external) is transactional.
     * 
     * @return true if sub-procewss is transactional.
     */
    boolean getSubprocessIsTransactional();

    /**
     * Return command to change set transactional status of sub-process.
     * 
     * @return the command that change transactional state of the subprocess
     */
    Command getSetSubProcessIsTransactionalCommand(EditingDomain ed,
            boolean transactional);

    /**
     * Get the id of the task that is referenced by this reference task.
     * 
     * @return
     */
    String getReferencedTask();

    /**
     * Get the mode object for of the task that is referenced by this reference
     * task.
     * 
     * @return
     */
    EObject getReferencedTaskObject();

    boolean isReferencedTaskLocal();

    /**
     * If this task is a reference task then this method is used to get the
     * referenced task's type.
     * 
     * @return TaskType or null
     */
    TaskType getReferencedTaskType();

    /**
     * 
     * @param ed
     * @param act
     * @param refTaskId
     * @return Command to set the refgerenced task
     */
    Command getSetReferencedTaskCommand(EditingDomain ed, String refTaskId);

    /**
     * Return the extension name of the last service task type implementation.
     * This will help determine which service type was used the last time
     */
    String getTaskImplementationExtensionId();

    /**
     * Get the command to create an interdediate event of given trigger type and
     * attach it to the border of this task.
     * 
     * @param ed
     * @param intermediateEventType
     * @param positionOnBorder
     * @param size
     * @param fillColor
     * @param lineColor
     * @return
     */
    CreateAccessibleObjectCommand getCreateEventOnBorderCommand(
            EditingDomain ed, EventTriggerType intermediateEventType,
            Double positionOnBorder, Dimension size, String fillColor,
            String lineColor);

    /**
     * Get list of model objects that represent events attached to the border of
     * this task.
     * 
     * @return List of model objects that represent Event activities or null if
     *         there are no attached events.
     */
    Collection getAttachedEvents();

    /**
     * Get tasks that reference this task.
     * 
     * @return
     */
    Collection<EObject> getReferencingTasks();

    /**
     * Return the command to inline the content of the sub-process reference
     * from this task (if it is an independent sub-process task).
     * 
     * @param ed
     * @return
     */
    CreateAccessibleObjectsCommand getInlineSubProcessContentCommand(
            EditingDomain ed,
            DiagramModelImageProvider callingProcessImageProvider,
            DiagramModelImageProvider subProcessImageProvider);

    /**
     * Check whether it is even possible to attempt in-lining of sub-process.
     * <p>
     * This is used by process widget in order to show/not show the refactor ->
     * in-line sub-process menu option.
     * 
     * @return true if the independent sub-proc task references a valid
     *         sub-process.
     */
    boolean isInlineEnabled();

    /**
     * @return true if the this task has MultiInstance Loop on and set to type
     *         Parallel.
     */
    boolean isMultiInstanceLoopParallel();

    /**
     * @return true if the this task has MultiInstance Loop on and set to type
     *         Sequential
     */
    boolean isMultiInstanceLoopSequential();

    /**
     * ========================================================================
     * == Pageflow User Task Only methods... These methods will be called only
     * for task of type user referencing a pageflow.
     * ========================================================================
     */

    /**
     * Open the current pageflow process.
     */
    void openPageflow();

    /**
     * Removes the pageflow reference from the user task. The pageflow process
     * itself is not deleted.
     */
    void removePageflow();

    /**
     * Assign an existing pageflow process to this user task.
     */
    void useExistingPageflow();

    /**
     * Update the current pageflow process to use parameters matching this user
     * task.
     */
    void updatePageflow();

    /**
     * Generate a new Pageflow process and assign to this user task. Parameters
     * for the task are taken from the user task associated parameters.
     */
    void generatePageflow();

    /**
     * @return Is task a pageflow referencing user task and the reference is
     *         valid.
     */
    boolean isValidPageflowUserTask();

    /**
     * @param ed
     * @return The command to set this send task as the reply to the given
     *         request actvitiy.
     */
    Command getSetRequestActivityForReplyActivityCommand(EditingDomain ed,
            String requestActivityId);

    /**
     * @return The ids of activities that are configured to reply to this
     *         receive task.
     */
    Collection<String> getReplyActivityIds();

    /**
     * @return The id of the activity that this Send Task is the reply to (null
     *         or "" if it is not configured as a reply.
     */
    String getRequestActivityId();

    /**
     * @return true if the embedded sub-proc activity is collapsed.
     */
    boolean isEmbeddedSubProcessCollapsed();

    /**
     * 
     * @param ed
     * @param defaultCollapsedSize
     *            If not previously expanded then this is the default collapsed
     *            size.
     * @param returnCollapsedSize
     *            Return the size as collapsed.
     * @return The command to set the embedded sub-process as collapsed.
     */
    Command getCollapseSubProcessCommand(EditingDomain ed,
            Dimension defaultCollapsedSize, Dimension returnCollapsedSize);

    /**
     * @param ed
     * @param minimumExpandedSize
     *            If not previously expanded then this is the default expanded
     *            size.
     * @param returnExpandedSize
     *            Return the size as expanded.
     * 
     * @return The command to set the embedded sub-process as expanded.
     */
    Command getExpandSubProcessCommand(EditingDomain ed,
            Dimension minimumExpandedSize, Dimension returnExpandedSize);

    /**
     * Get command to set/remove the path to a non-default icon for the task.
     * 
     * @param editingDomain
     * @param iconPath
     *            icon path or null to remove.
     * 
     * @return command
     */
    Command getSetTaskIconCommand(EditingDomain editingDomain, String iconPath);

    /**
     * @return The path for the task's custom icon (or null if it does not have
     *         a custom icon).
     */
    String getTaskIcon();

    /**
     * Get The default task icon (which may be overridden after by icon
     * indicated by {@link #getTaskIcon()}.
     * 
     * @return The task icon image, or null to use the process widget's base
     *         default for given task.
     */
    Image getDefaultTaskIcon();

    /**
     * @return The id for the default task type icon to be stored in the
     *         registry.
     */
    String getTaskTypeIconRegistryId();

    /**
     * @return List of Throw Error Event activities that throw errors for this
     *         incoming request activity.
     */
    Collection<String> getThrowErrorIdsForRequestActivity();

    /**
     * 
     * @return <code>true</code> if the activity has at least one incoming flow,
     *         <code>false</code> otherwise.
     */
    boolean hasIncomingFlowsToActivity();
}
