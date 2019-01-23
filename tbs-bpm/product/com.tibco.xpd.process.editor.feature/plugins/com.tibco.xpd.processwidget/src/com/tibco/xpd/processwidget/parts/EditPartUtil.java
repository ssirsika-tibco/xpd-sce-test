/**
 * EditPartUtil.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.parts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseGraphicalNodeAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * EditPartUtil
 * 
 */
public class EditPartUtil {
    public static int ACTIVITY_FILTER_ALL = 0xFFFFFFFF;

    public static int ACTIVITY_FILTER_TASKS = 0x01;

    public static int ACTIVITY_FILTER_EVENTS = 0x02;

    public static int ACTIVITY_FILTER_GATEWAYS = 0x04;

    public static int ACTIVITY_FILTER_DATAOBJECTS = 0x08;

    public static int ACTIVITY_FILTER_NOTES = 0x10;

    /**
     * Get the given acttivity/artifact object's bounds in the MODEL co-ordinate
     * system.
     * 
     * @param gep
     * @return
     */
    public static Rectangle getModelBounds(BaseGraphicalEditPart gep) {
        BaseGraphicalNodeAdapter adp =
                (BaseGraphicalNodeAdapter) gep.getModelAdapter();

        Point location = new Point(0, 0);
        Dimension size = adp.getSize();

        // Special case is events attached to boundary of tasks. These are
        // stored relative to task boundary.
        if (adp instanceof EventAdapter
                && ((EventAdapter) adp).isAttachedToTask()) {
            Double pos = ((EventAdapter) adp).getTaskBorderAttachmentPosition();

            Object attachedToTask =
                    ((EventAdapter) adp).getBorderAttachmentTask();

            if (pos != null && attachedToTask != null) {

                Object ta =
                        gep.getAdapterFactory().adapt(attachedToTask,
                                ProcessWidgetConstants.ADAPTER_TYPE);

                if (ta instanceof TaskAdapter) {
                    TaskAdapter taskAdp = (TaskAdapter) ta;

                    Point tLoc = taskAdp.getLocation().getCopy();
                    Dimension tSize = taskAdp.getSize().getCopy();

                    Rectangle b =
                            new Rectangle(tLoc.x - (tSize.width / 2), tLoc.y
                                    - (tSize.height / 2), tSize.width,
                                    tSize.height);

                    PointList pts = new PointList();
                    pts.addPoint(b.getTopRight());
                    pts.addPoint(b.getBottomRight());
                    pts.addPoint(b.getBottomLeft());
                    pts.addPoint(b.getTopLeft());
                    pts.addPoint(b.getTopRight());

                    location =
                            XPDLineUtilities.getLinePointFromPortion(pts, pos
                                    .doubleValue());

                    location.x -= size.width / 2;
                    location.y -= size.height / 2;

                }
            }
        } else {

            location = adp.getLocation().getCopy();

            // Un-centre the location (except for width on diagram notes which
            // isn't
            // centred).
            if (!(gep instanceof NoteEditPart)) {
                location.x -= size.width / 2;
            }
            location.y -= size.height / 2;
        }

        return new Rectangle(location.x, location.y, size.width, size.height);
    }

    /**
     * Given a list of edit parts, add any events that are attached to border of
     * task edit parts in the list.
     * 
     * @param editParts
     */
    public static void addAttachedEventsToEditParts(List editParts) {
        List extraEditParts = new ArrayList();

        if (editParts.size() > 0) {
            EditPart first = (EditPart) editParts.get(0);

            Map editPartRegistry = first.getViewer().getEditPartRegistry();

            for (Iterator iter = editParts.iterator(); iter.hasNext();) {
                EditPart ep = (EditPart) iter.next();

                if (ep instanceof TaskEditPart) {
                    TaskEditPart taskEP = (TaskEditPart) ep;

                    TaskAdapter ta = (TaskAdapter) taskEP.getModelAdapter();

                    Collection attachedEvents = ta.getAttachedEvents();

                    if (attachedEvents != null) {
                        for (Iterator iterator = attachedEvents.iterator(); iterator
                                .hasNext();) {
                            Object event = iterator.next();

                            EditPart eventEP =
                                    (EditPart) editPartRegistry.get(event);
                            if (eventEP != null) {
                                if (!editParts.contains(eventEP)) {
                                    extraEditParts.add(eventEP);
                                }
                            }
                        }
                    }
                }
            }

            if (extraEditParts.size() > 0) {
                editParts.addAll(extraEditParts);
            }
        }
    }

    /**
     * Check whether attahced to task border events are selected without the
     * task itself being selected. If so returns false (don't allow copy/cut).
     * 
     * Also adds attahced event objects for selected tasks that aren't already
     * in list
     */
    public static boolean checkAttachedEvents(List selectedEditParts,
            List modelObjectsList) {
        // Add any missing attached events that are attached to selected tasks.
        EditPartUtil.addAttachedEventsToEditParts(selectedEditParts);

        // Make sure that no 'attached to border of task' events have been
        // selected without the given task selected too.
        for (Iterator iter = selectedEditParts.iterator(); iter.hasNext();) {
            EditPart ep = (EditPart) iter.next();

            if (ep instanceof EventEditPart) {
                // If attached event, make sure that the list of edit parts
                // includes task its attached to.
                TaskEditPart borderTask =
                        ((EventEditPart) ep).getBorderAttachmentTask();

                if (borderTask != null) {

                    if (!selectedEditParts.contains(borderTask)) {
                        // Task not selected.
                        return false;
                    }

                    // Add the event object to list if not already there.
                    if (modelObjectsList != null) {
                        Object evObject = ep.getModel();
                        if (!modelObjectsList.contains(evObject)) {
                            modelObjectsList.add(evObject);
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Create an image of the given edit parts, containing any child edit parts
     * and any connections between them.
     * 
     * @param shell
     * @param filePath
     * @param file
     */
    public static void createImageFileFromEditParts(
            List<ModelAdapterEditPart> editParts, File file) {
        Shell shell =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

        // Ok to overwrite.
        // Sort out file type...
        int fileType = -1;

        String filePath = file.getAbsolutePath();

        int extPos = filePath.lastIndexOf('.');
        String ext = null;
        if (extPos != -1) {
            ext = filePath.substring(extPos + 1);
        }

        if (ext != null && ext.length() > 0) {
            if (ext.equalsIgnoreCase("bmp")) { //$NON-NLS-1$
                fileType = SWT.IMAGE_BMP;
            } else if (ext.equalsIgnoreCase("jpg")) { //$NON-NLS-1$
                fileType = SWT.IMAGE_JPEG;
            }

            // When SWT image loader supports png and gif properly we
            // can use this code...
            // else if (ext.equalsIgnoreCase("png")) {
            // fileType = SWT.IMAGE_PNG;
            // } else if (ext.equalsIgnoreCase("gif")) {
            // fileType = SWT.IMAGE_GIF;
            // }
        }

        if (fileType == -1) {
            if (extPos == -1) {
                filePath = filePath + "."; //$NON-NLS-1$
            }
            filePath = filePath + "bmp"; //$NON-NLS-1$

            fileType = SWT.IMAGE_BMP;
        }

        // Create the image.
        Image img = createImageFromEditParts(editParts);

        if (img != null) {
            ImageData id = img.getImageData();
            img.dispose();

            ImageLoader loader = new ImageLoader();

            loader.data = new ImageData[] { id };

            ByteArrayOutputStream buf = new ByteArrayOutputStream();

            loader.save(buf, fileType);

            FileOutputStream fo;
            try {
                fo = new FileOutputStream(file);
                fo.write(buf.toByteArray());

                fo.close();

            } catch (FileNotFoundException e) {
                MessageDialog.openError(shell,
                        Messages.EditPartUtil_SaveAsDialog_title,
                        Messages.EditPartUtil_SaveAsDialog_CouldNotOpen_message
                                + "\"" + filePath + "\""); //$NON-NLS-1$ //$NON-NLS-2$
            } catch (IOException e) {
                MessageDialog.openError(shell,
                        Messages.EditPartUtil_SaveAsError_title,
                        Messages.EditPartUtil_SaveAsError_message
                                + "\"" + filePath + "\""); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
    }

    /**
     * @param editParts
     * @param widget
     * @return
     */
    public static Image createImageFromEditParts(
            List<ModelAdapterEditPart> editParts) {

        ProcessWidgetImpl widget =
                (ProcessWidgetImpl) editParts.get(0).getViewer()
                        .getProperty(ProcessWidgetConstants.PROP_WIDGET);

        if (widget != null) {
            // Remove edit parts whose parents are selected.
            List correctedEditParts =
                    ToolUtilities.getSelectionWithoutDependants(editParts);

            if (correctedEditParts.size() == 1
                    && correctedEditParts.get(0) instanceof ProcessEditPart) {
                // for process, create image of all pools.
                ProcessEditPart pep =
                        (ProcessEditPart) correctedEditParts.get(0);

                correctedEditParts = pep.getChildren();
            }

            // Get list of model objects
            List<Object> modelObjs =
                    new ArrayList<Object>(correctedEditParts.size());

            for (Iterator iter = correctedEditParts.iterator(); iter.hasNext();) {
                Object ep = iter.next();

                if (ep instanceof ModelAdapterEditPart) {
                    modelObjs.add(((ModelAdapterEditPart) ep).getModel());

                }
            }

            // Add connection between/within selected objects to list of objects
            EObject proc = widget.getInput();
            ProcessDiagramAdapter diagAdapter =
                    (ProcessDiagramAdapter) widget.getWidgetAdapter(proc);

            List objsAndConns = new ArrayList();
            objsAndConns.addAll(modelObjs);
            objsAndConns.addAll(diagAdapter.getNodeConnections(modelObjs,
                    true,
                    true));

            Image img =
                    widget.getDiagramImageFromModel(objsAndConns,
                            objsAndConns,
                            Collections.EMPTY_LIST,
                            null,
                            10,
                            DiagramModelImageProvider.HIGHLIGHT_CHILDREN);
            return img;
        }
        return null;
    }

    /**
     * Helper for getting the correct list of model objects for the given
     * selection (of process widget edit parts).
     * 
     * @param selection
     * @return The model objects required for selected edit parts or null on
     *         error.
     */
    public static Collection getModelObjectsFromSelection(ISelection selection) {
        if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
            IStructuredSelection sel = (IStructuredSelection) selection;

            return getModelObjectsFromSelection(sel.toList());
        }

        return null;
    }

    /**
     * Helper for getting the correct list of model objects for the given
     * selection (of process widget edit parts).
     * 
     * @param selection
     * @return The model objects required for selected edit parts or null on
     *         error.
     */
    public static Collection getModelObjectsFromSelection(Collection selection) {
        boolean success = true;

        if (!selection.isEmpty()) {

            // Extract and validate list of edit parts.

            List editParts = new ArrayList();
            for (Iterator iter = selection.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                if (obj instanceof ModelAdapterEditPart) {
                    ModelAdapterEditPart part = (ModelAdapterEditPart) obj;
                    editParts.add(part);
                } else {
                    success = false;
                    break;
                }
            }

            if (success) {
                // Reduce the list to the highest level edit parts selected
                // (i.e. if sub-task is selected inside selected embedded
                // sub-proc
                // then it is removed from the list OR if activity
                // selected inside selected lane then it is removed from list).
                List parentList =
                        ToolUtilities.getSelectionWithoutDependants(editParts);

                if (parentList == null || parentList.isEmpty()) {
                    success = false;
                } else {
                    // Check that there are no events on task border without
                    // the task itself selected.

                    if (!EditPartUtil.checkAttachedEvents(parentList, null)) {
                        success = false;
                    } else {
                        // Extract list of model objects from edit parts.
                        List selectedModelObjects =
                                new ArrayList(selection.size());

                        for (Iterator iter = parentList.iterator(); iter
                                .hasNext();) {
                            ModelAdapterEditPart adp =
                                    (ModelAdapterEditPart) iter.next();

                            selectedModelObjects.add(adp.getModel());
                        }

                        return selectedModelObjects;

                    }
                }
            }
        }

        return null;
    }

    /**
     * Return a list of reference tasks that reference the task.
     * 
     * @param task
     * @return
     */
    public static Collection<TaskEditPart> getReferencingTasks(
            TaskEditPart chkTask) {
        List<TaskEditPart> refTasks = new ArrayList<TaskEditPart>();

        ProcessEditPart process = chkTask.getParentProcess();

        Collection<BaseGraphicalEditPart> tasks =
                getAllActivitiesInProcess(process, ACTIVITY_FILTER_TASKS);

        String thisId = chkTask.getActivityAdapter().getId();

        for (BaseGraphicalEditPart ep : tasks) {
            if (ep instanceof TaskEditPart) {
                TaskEditPart task = (TaskEditPart) ep;
                if (TaskType.REFERENCE_LITERAL.equals(task.getActivityAdapter()
                        .getTaskType())) {
                    if (thisId.equals(task.getActivityAdapter()
                            .getReferencedTask())) {
                        refTasks.add(task);
                    }
                }
            }
        }

        return refTasks;
    }

    /**
     * Return a link events that have the given event as a target
     * 
     * @param task
     * @return
     */
    public static Collection<EventEditPart> getSourceLinkEvents(
            EventEditPart tgtEvent) {
        List<EventEditPart> srcEvents = new ArrayList<EventEditPart>();

        ProcessEditPart process = tgtEvent.getParentProcess();

        Collection<BaseGraphicalEditPart> tasks =
                getAllActivitiesInProcess(process, ACTIVITY_FILTER_EVENTS);

        String thisId = tgtEvent.getEventAdapter().getId();

        for (BaseGraphicalEditPart ep : tasks) {
            if (ep instanceof EventEditPart) {
                EventEditPart event = (EventEditPart) ep;

                if (EventTriggerType.EVENT_LINK_CATCH_LITERAL.equals(event
                        .getEventAdapter().getEventTriggerType())) {

                    if (thisId.equals(event.getEventAdapter().getLinkEventId())) {
                        srcEvents.add(event);
                    }
                }
            }
        }

        return srcEvents;
    }

    /**
     * Return list of all activities in the given process (including those in
     * embedded sub-process tasks).
     * 
     * @param pool
     * @param activityFilter
     *            Combination of ACTIVITY_FILTER_xxx values.
     * @return
     */
    public static Collection<BaseGraphicalEditPart> getAllActivitiesInProcess(
            ProcessEditPart process, int activityFilter) {
        Collection<BaseGraphicalEditPart> activities =
                new ArrayList<BaseGraphicalEditPart>();

        for (Iterator iter = process.getChildren().iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof PoolEditPart) {
                addAllActivitiesInPool((PoolEditPart) obj,
                        activities,
                        activityFilter);

            }
        }
        return activities;
    }

    /**
     * Return list of all tasks in the given pool (including those in embedded
     * sub-process tasks.
     * 
     * @param pool
     * @param activityFilter
     *            Combination of ACTIVITY_FILTER_xxx values.
     * @return
     */
    public static Collection<BaseGraphicalEditPart> getAllActivitiesInPool(
            PoolEditPart pool, int activityFilter) {
        Collection<BaseGraphicalEditPart> activities =
                new ArrayList<BaseGraphicalEditPart>();

        addAllActivitiesInPool(pool, activities, activityFilter);
        return activities;
    }

    /**
     * Return list of all tasks in the given lane (including those in embedded
     * sub-process tasks.
     * 
     * @param lane
     * @param activityFilter
     *            Combination of ACTIVITY_FILTER_xxx values.
     * @return
     */
    public static Collection<BaseGraphicalEditPart> getAllActivitiesInLane(
            LaneEditPart lane, int activityFilter) {
        Collection<BaseGraphicalEditPart> activities =
                new ArrayList<BaseGraphicalEditPart>();

        addAllActivitiesInLane(lane, activities, activityFilter);
        return activities;
    }

    /**
     * Return list of all tasks in the given embedded sub-process (including
     * those in sub-embedded sub-process tasks.
     * 
     * @param embSubProcTask
     * @param activityFilter
     *            Combination of ACTIVITY_FILTER_xxx values.
     * @return
     */
    public static Collection<BaseGraphicalEditPart> getAllActivitiesInEmbSubProc(
            TaskEditPart embSubProcTask, int activityFilter) {
        Collection<BaseGraphicalEditPart> activities =
                new ArrayList<BaseGraphicalEditPart>();

        addAllActivitiesInEmbeddedSubProc(embSubProcTask,
                activities,
                activityFilter);
        return activities;
    }

    /**
     * Add all tasks in the given pool (including those in embedded sub-process
     * tasks) to given list.
     * 
     * @param pool
     * @param activityFilter
     *            Combination of ACTIVITY_FILTER_xxx values.
     * @return
     */
    private static void addAllActivitiesInPool(PoolEditPart pool,
            Collection<BaseGraphicalEditPart> activities, int activityFilter) {

        for (Iterator iter = pool.getChildren().iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof LaneEditPart) {
                addAllActivitiesInLane((LaneEditPart) obj,
                        activities,
                        activityFilter);
            }

        }
    }

    /**
     * Add all tasks in the given lane (including those in embedded sub-process
     * tasks) to given list.
     * 
     * @param pool
     * @param activityFilter
     *            Combination of ACTIVITY_FILTER_xxx values.
     * @return
     */
    private static void addAllActivitiesInLane(LaneEditPart part,
            Collection<BaseGraphicalEditPart> activities, int activityFilter) {

        for (Iterator iter = part.getChildren().iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (matchesActivityFilter(obj, activityFilter)) {
                activities.add((BaseGraphicalEditPart) obj);
            }

            if (obj instanceof TaskEditPart) {
                TaskEditPart taskEP = (TaskEditPart) obj;

                if (taskEP.isEmbeddedSubProc()) {
                    addAllActivitiesInEmbeddedSubProc(taskEP,
                            activities,
                            activityFilter);
                }
            }
        }

    }

    /**
     * Add all tasks in the given embedded sub-process(including those in
     * sub-embedded sub-process tasks recursively) to given list.
     * 
     * @param pool
     * @param activityFilter
     *            Combination of ACTIVITY_FILTER_xxx values.
     * @return
     */
    private static void addAllActivitiesInEmbeddedSubProc(
            TaskEditPart embSubProcTask,
            Collection<BaseGraphicalEditPart> activities, int activityFilter) {

        for (Iterator iter = embSubProcTask.getChildren().iterator(); iter
                .hasNext();) {
            Object obj = iter.next();

            if (matchesActivityFilter(obj, activityFilter)) {
                activities.add((BaseGraphicalEditPart) obj);
            }

            if (obj instanceof TaskEditPart) {
                TaskEditPart taskEP = (TaskEditPart) obj;

                if (taskEP.isEmbeddedSubProc()) {
                    addAllActivitiesInEmbeddedSubProc(taskEP,
                            activities,
                            activityFilter);
                }
            }
        }
    }

    /**
     * Check given object to see if its an edit part that matches the
     * ACTIVITY_FILTER_xxx combination of flags.
     * 
     * @param obj
     * @param activityFilter
     * @return
     */
    private static boolean matchesActivityFilter(Object obj, int activityFilter) {
        if ((obj instanceof TaskEditPart)) {
            if ((activityFilter & ACTIVITY_FILTER_TASKS) == ACTIVITY_FILTER_TASKS) {
                return true;
            }
        } else if ((obj instanceof EventEditPart)) {
            if ((activityFilter & ACTIVITY_FILTER_EVENTS) == ACTIVITY_FILTER_EVENTS) {
                return true;
            }
        } else if ((obj instanceof GatewayEditPart)) {
            if ((activityFilter & ACTIVITY_FILTER_GATEWAYS) == ACTIVITY_FILTER_GATEWAYS) {
                return true;
            }
        } else if ((obj instanceof DataObjectEditPart)) {
            if ((activityFilter & ACTIVITY_FILTER_DATAOBJECTS) == ACTIVITY_FILTER_DATAOBJECTS) {
                return true;
            }
        } else if ((obj instanceof NoteEditPart)) {
            if ((activityFilter & ACTIVITY_FILTER_NOTES) == ACTIVITY_FILTER_NOTES) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get a list of edit parts that host the given model objects.
     * <p>
     * You can select the behaviour when model object is not hosted by an edit
     * part...
     * <li>If listNullIfNotFound=true then a null will be placed in list (this
     * ensures that the in and out lists match indexes for found edit parts).</li>
     * <li>If listNullIfNotFound=false then only edit parts that host one of the
     * model objects are returned</li>
     * </p>
     * 
     * @param modelObjects
     * @param listNullIfNotFound
     *            true if you want <code>null</code> placed in list for model
     *            objects that are not hosted by any edit part.
     * 
     * @return list of edit parts that host the given model objects.
     */
    public static List<EditPart> getEditPartsForModelObjects(
            GraphicalViewer graphicalViewer, Collection<Object> modelObjects,
            boolean listNullIfNotFound) {
        List<EditPart> editParts = new ArrayList<EditPart>();

        Map epReg = graphicalViewer.getEditPartRegistry();

        for (Object modelObj : modelObjects) {
            EditPart ep = (EditPart) epReg.get(modelObj);

            if (ep != null) {
                editParts.add(ep);

            } else if (listNullIfNotFound) {
                editParts.add(null);
            }
        }
        return editParts;
    }

    public static EObject resolveUri(URI uri) {
        EObject eo = null;
        if (uri.isPlatformResource()) {
            Path path = new Path(uri.toPlatformString(true));
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IFile file = workspace.getRoot().getFile(path);
            if (file != null && file.exists()) {
                XpdProjectResourceFactory factory =
                        XpdResourcesPlugin
                                .getDefault()
                                .getXpdProjectResourceFactory(file.getProject());
                WorkingCopy wc = factory.getWorkingCopy(file);
                if (wc != null) {
                    eo = wc.resolveEObject(uri.toString());
                }
            }
        }
        return eo;
    }

    /**
     * @param id
     * @return The event/task/gateway with the given id.
     */
    public static BaseFlowNodeEditPart findActivityEditPart(
            ProcessEditPart processEP, String id) {
        if (id != null && id.length() > 0) {
            Collection<BaseGraphicalEditPart> activityEPs =
                    EditPartUtil.getAllActivitiesInProcess(processEP,
                            EditPartUtil.ACTIVITY_FILTER_EVENTS
                                    | EditPartUtil.ACTIVITY_FILTER_TASKS
                                    | EditPartUtil.ACTIVITY_FILTER_GATEWAYS);

            for (BaseGraphicalEditPart act : activityEPs) {
                if (act instanceof BaseFlowNodeEditPart) {
                    BaseProcessAdapter adp =
                            ((BaseFlowNodeEditPart) act).getModelAdapter();
                    if (adp instanceof NamedElementAdapter) {
                        if (id.equals(((NamedElementAdapter) adp).getId())) {
                            return (BaseFlowNodeEditPart) act;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param ep
     * @return true if any of this edit parts ancestor parts is a closed
     *         embedded sub-process, lane or pool.
     */
    public static boolean isInClosedParent(EditPart ep) {
        if (ep != null) {
            EditPart parent = ep.getParent();
            while (parent != null) {
                if (parent instanceof PoolEditPart) {
                    if (((PoolEditPart) parent).isClosed()) {
                        return true;
                    }
                } else if (parent instanceof LaneEditPart) {
                    if (((LaneEditPart) parent).isClosed()) {
                        return true;
                    }
                } else if (parent instanceof TaskEditPart) {
                    TaskEditPart task = (TaskEditPart) parent;
                    if (task.isEmbeddedSubProc()
                            && task.isCollapsedEmbeddedSubproc()) {
                        return true;
                    }
                }
                parent = parent.getParent();
            }
        }
        return false;
    }

    /**
     * @param ep
     * @return true if it's an artifact edit part.
     */
    public static boolean isArtifactEditPart(EditPart ep) {
        if (ep instanceof DataObjectEditPart || ep instanceof NoteEditPart
                || ep instanceof GroupEditPart) {
            return true;
        }
        return false;
    }

    /**
     * @param ep
     * @return true if edit part is a catch compensation attached to task
     *         border.
     */
    public static boolean isAttachedCatchCompensation(EditPart ep) {
        if (ep instanceof EventEditPart) {
            // If the source of connection is a catch compensation attached to
            // task border then create an association.
            EventEditPart ev = (EventEditPart) ep;
            if (ev.isAttachedToTaskBorder()) {
                if (EventFlowType.FLOW_INTERMEDIATE == ev.getEventFlowType()
                        && EventTriggerType.EVENT_COMPENSATION_CATCH == ev
                                .getEventTriggerType()) {
                    return true;
                }
            }
        }
        return false;
    }
}
