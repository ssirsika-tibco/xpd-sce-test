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
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.destinations.ui.GlobalDestinationHelper.GlobalDestinationInfo;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;

/**
 * @author wzurek
 */
public interface ProcessDiagramAdapter extends BaseProcessAdapter,
        ProcessContainerAdapter, NamedElementAdapter {

    /**
     * List of Pools in the process
     * 
     * @return
     */
    List getPools();

    /**
     * Create new pool command
     * 
     * @param ed
     * @param index
     * @param poolName
     * @return
     */
    Command getCreateNewPoolCommand(EditingDomain ed, int index,
            String poolName, String fillColor, String lineColor);

    /**
     * Chage order of the pools command
     * 
     * @param ed
     * @param index
     * @return
     */
    Command getMovePoolCommand(EditingDomain ed, int fromIndex, int toIndex);

    /**
     * Diagram description
     * 
     * @return
     */
    String getDescription();

    /**
     * Set description command
     * 
     * @param ed
     * @param text
     * @return
     */
    Command getSetDescriptionCommand(EditingDomain ed, String text);

    /**
     * List of BPMN's groups defined in process
     * 
     * @return
     */
    List getGroups();

    /**
     * Create new group command
     * 
     * @param ed
     * @param location
     *            , in diagram coordinates
     * @param size
     * @return
     */
    Command getCreateNewGroupCommand(EditingDomain ed, Point location,
            Dimension size, String lineColor);

    /**
     * @return The flow routing style to be used for the process.
     */
    public XpdFlowRoutingStyle getFlowRoutingStyle();

    /**
     * Get the copy objects for the given selection. That is, return a COMPLETE
     * list of <b>COPIES</b> of the model objects that are required for the
     * complete definition of the given list of model objects (thus allowing the
     * model object hierarchy structure to be different from the process
     * widget's edit part hierarchy).
     * <p>
     * <b> NOTE: The copied objects MAY be readded to the model (via
     * getPasteObjectsCommand()) so if this will cause problems (for instance
     * with unique id's on model objects) then this method must perform any
     * necessary operations to fix such issues.
     * <p>
     * The selection list will always be rationalised so that it does NOT
     * contain model EObjects for child diagram objects whose parent diagram
     * object is also selected.
     * <p>
     * i.e. If activity AND it's parent lane are selected then the list will
     * contain ONLY the lane object.
     * <p>
     * If activity within embedded sub-process (activity set) and parent
     * embedded sub-process are both selected then list will only contain
     * embedded sub-proc
     * <p>
     * Selection list can contain the following items, it is up to adapter
     * whether it can cope with mixed selection... - Process(collection of all
     * pools) - Pool(s) - Lane(s) - Activity(s) Data Objects - Transition(s)
     * <p>
     * 
     * @param ed
     *            Model editing domain
     * @param selection
     *            List of model EObjects to copy.
     *            <p>
     * @param wantProjectReferences
     *            when set, return collection contains projects for required
     *            references
     * @return Complete list of model objects required for the definition of the
     *         given list. or <b>null</b> on error
     */
    Collection getCopyObjects(EditingDomain ed, Collection selection,
            boolean wantProjectReferences);

    /**
     * Validate that the given set of selected objects can be copied and remain
     * self-consistent.
     * <p>
     * The selection list will always be rationalised so that it does NOT
     * contain model EObjects for child diagram objects whose parent diagram
     * object is also selected.
     * <p>
     * i.e. If activity AND it's parent lane are selected then the list will
     * contain ONLY the lane object.
     * <p>
     * If activity within embedded sub-process (activity set) and parent
     * embedded sub-process are both selected then list will only contain
     * embedded sub-proc
     * <p>
     * Selection list can contain the following items, it is up to adapter
     * whether it can cope with mixed selection... - Process(collection of all
     * pools) - Pool(s) - Lane(s) - Activity(s) Data Objects - Transition(s)
     * <p>
     * 
     * @param selection
     * @return <code>true</code> If
     *         {@link #getCopyObjects(EditingDomain, Collection)} will succeed
     *         if called for the given selection.
     */
    boolean validateCopyObjects(Collection selection);

    /**
     * Get the command that will delete the given objects AFTER a copy to
     * clipboard.
     * 
     * The selection list will always be rationalised so that it does NOT
     * contain model EObjects for child daigram whose parent daigram object is
     * also selected.
     * 
     * i.e.
     * 
     * If activity AND it's parent lane are selected then the list will contain
     * ONLY the lane object.
     * 
     * If activity within embedded sub-process (activity set) and parent
     * embedded sub-process are both selected then list will only contain
     * embedded sub-proc
     * 
     * Selection list can contain the following items, it is up to adapter
     * whether it can cope with mixed selection... - Process - Pool(s) - Lane(s)
     * - Activity(s) Data Objects - Transition(s)
     * 
     * @param ed
     *            Model editing domain
     * @param selection
     *            List of model EObjects to copy.
     * 
     * @return EMF command on success
     * @return UnexecutableCommand.INSTANCE to disable option.
     */
    Command getDeleteCutObjects(EditingDomain ed, Collection selection);

    /**
     * Paste objects from the clipboard (or elsewhere) as children of given
     * target on given loacation. The location is a hint, may not be available.
     * 
     * @see #getCopyObjectsCommand(EditingDomain, List)
     * @param ed
     *            EditingDomain that should be used
     * @param target
     *            parent EObject that represent where to paste
     * @param location
     *            a hint - location within parent, may be null if not avaiable
     * 
     * @return
     */
    ProcessPasteCommand getPasteObjectsCommand(EditingDomain ed, Object target,
            Point location, Collection pasteObjects);

    /**
     * Get a list of all activities in process.
     * 
     * @param filterFlags
     *            combination of ACTIVITY_LIST_TASKS | ACTIVITY_LIST_EVENTS |
     *            ACTIVITY_LIST_GATEWAYS or 0 for all.
     * @return List of adapted NamedElementAdapter's
     */
    List<NamedElementAdapter> getActivityList(int filterFlags);

    public final static int ACTIVITY_LIST_TASKS = 0x0001;

    public final static int ACTIVITY_LIST_EVENTS = 0x0002;

    public final static int ACTIVITY_LIST_GATEWAYS = 0x0004;

    public final static int ACTIVITY_LIST_REFERENCES = 0x0008;

    public final static int ACTIVITY_LIST_SUBFLOWS = 0x0010;

    /**
     * Return the model object that has the given unique id
     * 
     * @return found object or null if not found.
     */
    Object getModelObjectById(String id);

    /**
     * Refactor the given objects (task/gateway/event/dataobject/annotation) as
     * an embedded sub-process.
     * <p>
     * <b>NOTE: </b>This method will be passed ONLY top level objects (i.e. it
     * will not be passed objects contained by an embedded sub-process object
     * that is itself selected)</b>
     * </p>
     * <p>
     * <b>NOTE 2:</b>The implementation of this method need not convern itself
     * with laying out the content of the new embedded sub-process. The process
     * widget will layout and set the new object to the optimum size for the
     * given contents.
     * </p>
     * 
     * @param editingDomain
     * @param objects
     *            List of activity and artifact model objects.
     * @return
     */
    CreateAccessibleObjectCommand getRefactorAsEmbeddedSubProcCommand(
            EditingDomain editingDomain, List objects,
            DiagramModelImageProvider imageProvider);

    /**
     * Refactor an event handler flow (that may include
     * task/gateway/event/dataobject/annotation) as an event sub-process.
     * <p>
     * <b>NOTE: </b>This method will be passed ONLY top level objects (i.e. it
     * will not be passed objects contained by an event sub-process object that
     * is itself selected)</b>
     * </p>
     * <p>
     * <b>NOTE 2:</b>The implementation of this method need not convern itself
     * with laying out the content of the new event sub-process. The process
     * widget will layout and set the new object to the optimum size for the
     * given contents.
     * </p>
     * 
     * @param editingDomain
     * @param objects
     *            List of activity and artifact model objects.
     * @return
     */
    CreateAccessibleObjectCommand getRefactorAsEventSubProcCommand(
            EditingDomain editingDomain, List objects,
            DiagramModelImageProvider imageProvider);

    /**
     * Refactor the given objects (task/gateway/event/dataobject/annotation) as
     * an independent sub-process.
     * <p>
     * <b>NOTE: </b>This method will be passed ONLY top level objects (i.e. it
     * will not be passed objects contained by an embedded sub-process object
     * that is itself selected)</b>
     * </p>
     * <p>
     * 
     * @param editingDomain
     * @param objects
     *            List of activity and artifact model objects.
     * @return
     */
    CreateAccessibleObjectCommand getRefactorAsIndependentSubProcCommand(
            EditingDomain editingDomain, List objects,
            DiagramModelImageProvider imageProvider);

    /**
     * Refactor the given objects into an independent new sub process that will
     * be invoked from re-usable sub proc task from the parent process
     * 
     * 
     * @param editingDomain
     * @param objects
     *            List of activity and artifact model objects.
     * @param imageProvider
     * @return
     */
    CreateAccessibleObjectCommand getRefactorAsIndependentServiceProcessCommand(
            EditingDomain editingDomain, List objects,
            DiagramModelImageProvider imageProvider);

    /**
     * Refactor the given objects (task/gateway/event/dataobject/annotation) as
     * an independent pageflow sub-process.
     * <p>
     * <b>NOTE: </b>This method will be passed ONLY top level objects (i.e. it
     * will not be passed objects contained by an embedded sub-process object
     * that is itself selected)</b>
     * </p>
     * <p>
     * 
     * @param editingDomain
     * @param objects
     *            List of activity and artifact model objects.
     * @return
     */
    CreateAccessibleObjectCommand getRefactorAsIndependentPageflowSubProcCommand(
            EditingDomain editingDomain, List objects,
            DiagramModelImageProvider imageProvider);

    /**
     * Refactor the given objects (task/gateway/event/dataobject/annotation) as
     * a pageflow process.
     * <p>
     * <b>NOTE: </b>This method will be passed ONLY top level objects (i.e. it
     * will not be passed objects contained by an embedded sub-process object
     * that is itself selected)</b>
     * </p>
     * <p>
     * 
     * @param editingDomain
     * @param objects
     *            List of activity and artifact model objects.
     * @return
     */
    CreateAccessibleObjectCommand getRefactorAsPageflowProcCommand(
            EditingDomain editingDomain, List objects,
            DiagramModelImageProvider imageProvider);

    /**
     * Given a list of model objects, return a list containing... <li>The given
     * list of activities/artifacts</li> <li>Any transitions between them</li>
     * <li>Any sub-activities (recursively) for any embedded sub-proc activity</li>
     * <li>All transitions between sub-activities</li> <li>Optionally, any
     * associations between anyy of the objects collected above</li>
     * 
     * @return
     */

    Collection getNodeConnections(Collection nodeObjects, boolean wantFlows,
            boolean wantAssociations);

    /**
     * @param ed
     * @param parts
     * @return
     */
    Command getSeparationOfDutiesCommand(EditingDomain ed,
            List<BaseFlowNodeEditPart> parts);

    /**
     * @param ed
     * @param editParts
     * @return
     */
    Command getRetainFamiliarCommand(EditingDomain ed,
            List<BaseFlowNodeEditPart> editParts);

    /**
     * @return The undo context that the process diagram editor should use for
     *         undo/redo actions.
     */
    IUndoContext getUndoContext();

    /**
     * Given a list of global destination environments, returns a command which
     * adds the environments to the process if not addressed already.
     * 
     * @param ed
     * @param destEnvs
     * @return
     */
    Command getSetGlobalDestinationEnvironmentCommand(EditingDomain ed,
            HashMap<String, String> destEnvs);

    /**
     * 
     */
    Collection<GlobalDestinationInfo> getGlobalDestinationsAssociated();

    /**
     * Add a listener for when process diagram adapter fire an event that says
     * it has recached the problem marker list after a revalidaiton of model.
     * <p>
     * The listener's job is to listen for end of validation runs, recache the
     * problem marker list for the process and then fire
     * ProblemMarkerListChanged events.
     * 
     * @param listener
     */
    void addProblemMakerListChangedListener(
            ProblemMarkerListChangedListener listener);

    /**
     * Remove a listener for when process diagram adapter fire an event that
     * says it has recached the problem marker list after a revalidaiton of
     * model.
     * 
     * @param listener
     */
    void removeProblemMakerListChangedListener(
            ProblemMarkerListChangedListener listener);

    public interface ProblemMarkerListChangedListener {
        void problemMarkerListChanged(Collection<MarkerAndModelObject> oldList,
                Collection<MarkerAndModelObject> newList);
    }

    /**
     * Return <code>true</code> if the selection is a Single Event Handler
     * selection or a complete Event Handler Flow. In other words, the selected
     * objects do not have any potential start activity and there's only one
     * event handler activity in them. Return <code>false</code> otherwise.
     * 
     * @param objectList
     *            List of selected objects.
     * 
     * @return <code>true</code> if the selection is a Single Event Handler
     *         selection or a complete Event Handler Flow. In other words, the
     *         selected objects do not have any potential start activity and
     *         there's only one event handler activity in them. Return
     *         <code>false</code> otherwise. met.
     */
    boolean isEventHandlerFlow(Set<Object> objectList);

}
