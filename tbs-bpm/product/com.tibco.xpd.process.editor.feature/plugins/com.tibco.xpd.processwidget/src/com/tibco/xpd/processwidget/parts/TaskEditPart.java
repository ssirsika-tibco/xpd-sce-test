/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.processwidget.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.draw2d.ActionEvent;
import org.eclipse.draw2d.ActionListener;
import org.eclipse.draw2d.Button;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.editpolicies.SnapFeedbackPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker.ProcessPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.actions.XPDSnapToGrid;
import com.tibco.xpd.processwidget.adapters.ActivityMarkerType;
import com.tibco.xpd.processwidget.adapters.AssociationAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetEvent;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.processwidget.commands.SetTaskNameCommand;
import com.tibco.xpd.processwidget.commands.internal.CloseEmbSubProcCommand;
import com.tibco.xpd.processwidget.commands.internal.OpenEmbSubProcCommand;
import com.tibco.xpd.processwidget.figures.PrintableImageFigure;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.TaskFigure;
import com.tibco.xpd.processwidget.figures.TooltipFigure;
import com.tibco.xpd.processwidget.figures.XPDGridLayer;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.figures.anchors.TaskActivityAnchor;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.neatstuff.GrowFigureAnimationMouseMotionListener;
import com.tibco.xpd.processwidget.policies.BpmnSnapToGeometry;
import com.tibco.xpd.processwidget.policies.ClickOrDragReplyActivityPolicy;
import com.tibco.xpd.processwidget.policies.ClickOrDragTaskReferencePolicy;
import com.tibco.xpd.processwidget.policies.ClickOrDragThrowFaultEventPolicy;
import com.tibco.xpd.processwidget.policies.FlowContainerXYLayoutEditPolicy;
import com.tibco.xpd.processwidget.policies.HoverInfo;
import com.tibco.xpd.processwidget.policies.HoverInfoEditPolicy.HoverProvider;
import com.tibco.xpd.processwidget.policies.SpyglassHoverEditPolicy;
import com.tibco.xpd.processwidget.policies.cycletypegadget.CycleTaskTypeGadgetPolicy;
import com.tibco.xpd.processwidget.tools.AutoSizingDirectEditLocator;
import com.tibco.xpd.processwidget.tools.FlowConnectionToolEntry;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * EditPart of the Activity (Task and Subflow)
 * 
 */
public class TaskEditPart extends BaseFlowNodeEditPart implements
        HoverProvider, NodeEditPart {

    private ConnectionAnchor associationAnchor;

    private ImageFigure openCloseButton = null;

    public static final String PROPERTY_EMBEDDEDSUBPROC_CLOSESTATE =
            "PROPERTY_EMBEDDEDSUBPROC_CLOSESTATE"; //$NON-NLS-1$

    private List<PropertyChangeListener> diagramRefreshListeners =
            new ArrayList<PropertyChangeListener>();

    boolean firstRefresh = true;

    boolean previousClosedState = false;

    private boolean enableShrinkWhenDeselected = false;

    private boolean shrinkWhenDeselected = false;

    private GrowFigureAnimationMouseMotionListener growTaskMouseMotionListener;

    private ActivityHyperLinkListener activityHyperLinkListener =
            new ActivityHyperLinkListener(this);

    private ActivityHyperLinkListener activityHyperLinkListenerRefIcon =
            new ActivityHyperLinkListener(this);

    // This is the embedded sub-proc opened/closed property change listener that
    // is set on each task added. This will fire a diagram refresh event that
    // allows other edit parts to find out when tsub-proc is closed without
    // having to check for individual task changes.
    private PropertyChangeListener embSubProcDiagramRefreshListener =
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    // Propagate embedded exp/collapse to our listeners
                    for (PropertyChangeListener listener : diagramRefreshListeners) {
                        listener.propertyChange(evt);
                    }
                }
            };

    /**
     * For embedded sub-proc tasks we have to listen to grid on/off events.
     */
    private PropertyChangeListener gridListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String property = evt.getPropertyName();
            if (property.equals(SnapToGrid.PROPERTY_GRID_ORIGIN)
                    || property.equals(SnapToGrid.PROPERTY_GRID_SPACING)
                    || property.equals(SnapToGrid.PROPERTY_GRID_VISIBLE))
                refreshGridLayer();
        }
    };

    /**
     * The Constructor
     * 
     * @param adapterFactory
     *            adapter factory for producing process widget adapters
     */
    public TaskEditPart(AdapterFactory adapterFactory) {
        super(adapterFactory);
        setHelpContextId("com.tibco.xpd.analyst.doc.activity_sel"); //$NON-NLS-1$
    }

    /**
     * @return TaskAdapter of the model
     */
    public TaskAdapter getActivityAdapter() {
        return (TaskAdapter) getModelAdapter();
    }

    /*
     * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
     */
    @Override
    protected IFigure createFigure() {
        TaskFigure fig = new TaskFigure();
        fig.setToolTip(new TooltipFigure(this));

        growTaskMouseMotionListener =
                new GrowFigureAnimationMouseMotionListener(fig, new Dimension(
                        12, 12), new Dimension(
                        ProcessWidgetConstants.TASK_WIDTH_SIZE,
                        ProcessWidgetConstants.TASK_HEIGHT_SIZE), 150, 250);
        growTaskMouseMotionListener.setEnabled(false);

        growTaskMouseMotionListener.setMouseEnterDelay(150);
        growTaskMouseMotionListener.setMouseExitDelay(0);

        fig.addMouseMotionListener(growTaskMouseMotionListener);

        return fig;
    }

    /*
     * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
     */
    @Override
    protected void refreshVisuals() {

        TaskAdapter adapter = getActivityAdapter();

        TaskFigure f = (TaskFigure) getFigure();
        List markers = new ArrayList();

        /*
         * Unset the figure used as hyperlink listener (the icon fig) in case we
         * end up without one.
         */
        activityHyperLinkListener.setHyperlinkFigure(null);
        activityHyperLinkListenerRefIcon.setHyperlinkFigure(null);

        ImageRegistry ir = ProcessWidgetPlugin.getDefault().getImageRegistry();

        Set set = adapter.getMarkers();
        if (set.contains(ActivityMarkerType.MARKER_AD_HOC_LITERAL)) {
            Image img = ir.get(ProcessWidgetConstants.IMG_AD_HOC_MARKER);
            markers.add(createMarkerFigure(img,
                    Messages.TaskEditPart_MarkerAdHoc_tooltip));
        }

        if (set.contains(ActivityMarkerType.MARKER_LOOP_LITERAL)) {
            Image img = ir.get(ProcessWidgetConstants.IMG_LOOP_MARKER);
            markers.add(createMarkerFigure(img,
                    Messages.TaskEditPart_MarkerLoop_tooltip));
        }

        if (set.contains(ActivityMarkerType.MARKER_MULTIPLE_LITERAL)) {
            if (adapter.isMultiInstanceLoopParallel()) {
                Image img =
                        ir.get(ProcessWidgetConstants.IMG_MULTIPLE_MARKER_PARALLEL);
                markers.add(createMarkerFigure(img,
                        Messages.TaskEditPart_ParallelMultiple_tooltip));

            } else if (adapter.isMultiInstanceLoopSequential()) {
                Image img =
                        ir.get(ProcessWidgetConstants.IMG_MULTIPLE_MARKER_SEQUENTIAL);
                markers.add(createMarkerFigure(img,
                        Messages.TaskEditPart_SequentialMultiple_tooltip));

            } else {
                Image img = ir.get(ProcessWidgetConstants.IMG_LOOP_MARKER);
                markers.add(createMarkerFigure(img,
                        Messages.TaskEditPart_MarkerLoop_tooltip));
            }
        }

        // If this task is the target of a compensation association then set the
        // compensation marker.
        boolean isCompensationTask = false;
        List conns = getTargetConnections();
        for (Iterator iter = conns.iterator(); iter.hasNext();) {
            Object conn = iter.next();

            if (conn instanceof AssociationEditPart) {
                AssociationEditPart assEP = (AssociationEditPart) conn;

                if (assEP.isCompensationAssocation()) {
                    isCompensationTask = true;
                }
            }
        }

        if (isCompensationTask) {
            Image img = ir.get(ProcessWidgetConstants.IMG_COMPENSATION_MARKER);
            markers.add(createMarkerFigure(img,
                    Messages.TaskEditPart_MarkerCompensation_tooltip));
        }

        TaskType realTaskType = adapter.getTaskType();
        TaskType referencedOrRealTaskType = realTaskType;
        boolean isSatisfiedReferenceTask = false;
        shrinkWhenDeselected = false;

        /*
         * Sid XPD-6542 - change in ref task handling - isReferenceTask is now
         * changed to isStatisfiedReferenceTask meaning that it is a reference
         * task AND the reference is satisfied. If not then we just treat using
         * it's basic type (that way we get a full basic icon for it until
         * refernece is set and then we get an overlay arrow once it is
         * satisfied.
         */
        if (TaskType.REFERENCE_LITERAL.equals(referencedOrRealTaskType)) {
            referencedOrRealTaskType = adapter.getReferencedTaskType();

            if (referencedOrRealTaskType == null) {
                /*
                 * No referenced task so just use basic task type icon with no
                 * overlay.
                 */
                referencedOrRealTaskType = TaskType.REFERENCE_LITERAL;

            } else {
                /*
                 * We have a referenced task so want to overlay it's icon with
                 * ours.
                 */
                isSatisfiedReferenceTask = true;
            }
        }

        f.setTransactional(false);
        f.setContentsVisible(false);

        if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(realTaskType)
                || TaskType.EVENT_SUBPROCESS_LITERAL.equals(realTaskType)) {
            f.setTransactional(adapter.getSubprocessIsTransactional());

            if (!adapter.isEmbeddedSubProcessCollapsed()) {
                f.setContentsVisible(true);
                ImageFigure expCollapseMarker = createEmbSubCollapseMarker(ir);
                f.setOpenCloseButton(expCollapseMarker);
            } else {
                Button b = createSubProcessMarker(ir);
                markers.add(b);
                f.setOpenCloseButton(null);
            }

        } else {
            f.setOpenCloseButton(null);
        }

        if (TaskType.EVENT_SUBPROCESS_LITERAL.equals(realTaskType)) {
            f.setNeedDottedBorder(true);
        } else {
            f.setNeedDottedBorder(false);
        }

        if (TaskType.SUBPROCESS_LITERAL.equals(realTaskType)) {
            f.setTransactional(adapter.getSubprocessIsTransactional());

            Button b = createSubProcessMarker(ir);

            markers.add(b);

            /*
             * BPMN 2.0 says that the Call Sub-Process activity has a thicker
             * border.
             */
            f.setBorderWidth(2);

        } else {
            /* Non call sub-process activity has standard border. */
            f.setBorderWidth(1);

        }

        setTaskIcon(adapter,
                f,
                ir,
                referencedOrRealTaskType,
                isSatisfiedReferenceTask);

        f.setMarkers(markers);

        /*
         * If we have an icon figure then make it the hyperlink action figure.
         * The hyperlink is handled via contribution to
         * activityHyperLinkProvider extension point.
         */
        if (f.getIconFigure() != null) {
            activityHyperLinkListener.setHyperlinkFigure(f.getIconFigure());
        }
        if (f.getReferenceImageFigure() != null) {
            activityHyperLinkListenerRefIcon.setHyperlinkFigure(f
                    .getReferenceImageFigure());
        }

        ProcessWidgetColors colors = ProcessWidgetColors.getInstance(this);

        // Get colors for object.
        WidgetRGB fillColor =
                colors.getGraphicalNodeColor(adapter,
                        getFillColorIDForPartType());
        WidgetRGB borderColor =
                colors.getGraphicalNodeColor(adapter,
                        getLineColorIDForPartType());

        f.setBackgroundColor(fillColor.getColor());
        f.setForegroundColor(borderColor.getColor());

        IFigure p = f.getParent();
        if (p != null) {
            LayoutManager layout = p.getLayoutManager();
            Dimension min = f.getMinimumSize();
            Dimension act = adapter.getSize();
            if (act == null)
                act = new Dimension();

            Point loc = adapter.getLocation().getCopy();
            if (loc == null)
                loc = new Point();

            // SID - from now on we'll treat location of tasks as centred as
            // well as events/gateways etc. This'll make other widget things
            // easier to deal with and more consistent (such as snap to grid
            // etc)
            int width = Math.max(min.width, act.width);
            int height = Math.max(min.height, act.height);

            growTaskMouseMotionListener.setFinalSize(new Dimension(width,
                    height));

            if (shrinkWhenDeselected) {
                /*
                 * if we're currently unselected then enable grow animation.
                 */
                if (this.getSelected() == SELECTED_NONE) {
                    growTaskMouseMotionListener.setEnabled(true);

                    Dimension s = growTaskMouseMotionListener.getInitialSize();
                    width = s.width;
                    height = s.height;

                } else {
                    growTaskMouseMotionListener.setEnabled(false);
                }

            } else {
                shrinkWhenDeselected = false;

                growTaskMouseMotionListener.setEnabled(false);
            }

            loc.x = loc.x - (width / 2);
            loc.y = loc.y - (height / 2);

            Rectangle r = new Rectangle(loc, new Dimension(width, height));
            if (!r.equals(layout.getConstraint(f))) {
                layout.setConstraint(f, r);

                layout.layout(p);

                p.revalidate();

                // When task is moved we have to refresh visuals on any events
                // attached to the boundary.
                Collection<EventEditPart> attachedEvents = getAttachedEvents();

                if (attachedEvents != null) {
                    for (EventEditPart eventEP : attachedEvents) {
                        eventEP.refresh();
                    }
                }
            }
        }
        f.setText(adapter.getName());

        // Set up the grid layer...
        XPDGridLayer grd = f.getGridLayer();
        if (grd != null) {
            grd.setSpacing((Dimension) getViewer()
                    .getProperty(SnapToGrid.PROPERTY_GRID_SPACING));

        }

    }

    /**
     * Set the task icon appropriate to the task configuration. Sid XPD-7421
     * Extracted from from {@link #refreshVisuals()}
     * 
     * @param adapter
     * @param f
     * @param ir
     * @param referencedOrRealTaskType
     * @param isSatisfiedReferenceTask
     */
    private void setTaskIcon(TaskAdapter adapter, TaskFigure f,
            ImageRegistry ir, TaskType referencedOrRealTaskType,
            boolean isSatisfiedReferenceTask) {
        Image customImage =
                TaskIconImageCache.getImage(adapter.getTaskIcon(), getModel());
        if (customImage != null) {
            f.setImage(customImage, isSatisfiedReferenceTask);
            return;
        }

        /*
         * Sid XPD-2516: Allow overrides from processEditorConfiguration
         * extension point.
         */
        ProcessEditorObjectType processEditorObjectType =
                referencedOrRealTaskType.getProcessEditorObjectType();

        /*
         * For reference tasks use teh referenced activity model for getting
         * activity icon override.
         */
        Object activityModel = null;
        if (isSatisfiedReferenceTask) {
            activityModel = adapter.getReferencedTaskObject();
        }
        if (activityModel == null) {
            activityModel = getModel();
        }

        Image overrideImage =
                getActivityIconOverride(processEditorObjectType, activityModel);

        if (overrideImage != null) {
            f.setImage(overrideImage, isSatisfiedReferenceTask);
            return;
        }

        /*
         * Sid XPD-7421 Allow adapter to provide default icon if it wishes.
         */
        Image defaultTaskIcon = adapter.getDefaultTaskIcon();
        if (defaultTaskIcon != null) {
            f.setImage(defaultTaskIcon, isSatisfiedReferenceTask);
            return;
        }

        if (TaskType.EVENT_SUBPROCESS_LITERAL.equals(referencedOrRealTaskType)) {
            /*
             * ABPM-911: Saket: Agreed with Sid, removed icon for embedded
             * subprocesses as not specified by BPMN spec anymore.
             */

            IFigure embeddedStartEventFig = getCollapsedEventSubProcessImage();

            /*
             * Set it to the subprocess image figure.
             */
            f.setImageFigure(embeddedStartEventFig);

        } else if (TaskType.EMBEDDED_SUBPROCESS_LITERAL
                .equals(referencedOrRealTaskType)) {
            /*
             * ABPM-911: Agreed with Sid, removed icon for embedded subprocesses
             * as not specified by BPMN spec anymore.
             */
            f.setImage(null, isSatisfiedReferenceTask);
        } else if (TaskType.SUBPROCESS_LITERAL.equals(referencedOrRealTaskType)) {

            f.setImage(ir.get(ProcessWidgetConstants.IMG_SUBPROCTASK_ICON),
                    isSatisfiedReferenceTask);

        } else {
            if (TaskType.NONE_LITERAL.equals(referencedOrRealTaskType)) {
                f.setImage(null, isSatisfiedReferenceTask);

            } else if (TaskType.SERVICE_LITERAL
                    .equals(referencedOrRealTaskType)) {
                f.setImage(ir.get(ProcessWidgetConstants.IMG_SERVICETASK_ICON),
                        isSatisfiedReferenceTask);
            } else if (TaskType.DTABLE_LITERAL.equals(referencedOrRealTaskType)) {
                f.setImage(ir.get(ProcessWidgetConstants.IMG_DTABLETASK_ICON),
                        isSatisfiedReferenceTask);
            } else if (TaskType.USER_LITERAL.equals(referencedOrRealTaskType)) {
                if (ProcessWidgetType.PAGEFLOW_PROCESS.equals(adapter
                        .getProcessType())
                        || ProcessWidgetType.CASE_SERVICE.equals(adapter
                                .getProcessType())
                        || ProcessWidgetType.BUSINESS_SERVICE.equals(adapter
                                .getProcessType())) {
                    f.setImage(ir.get(ProcessWidgetConstants.IMG_PAGEFLOW_ICON),
                            isSatisfiedReferenceTask);
                } else {
                    f.setImage(ir.get(ProcessWidgetConstants.IMG_USERTASK_ICON),
                            isSatisfiedReferenceTask);
                }
            } else if (TaskType.SCRIPT_LITERAL.equals(referencedOrRealTaskType)) {
                f.setImage(ir.get(ProcessWidgetConstants.IMG_SCRIPTTASK_ICON),
                        isSatisfiedReferenceTask);

                shrinkWhenDeselected = true && enableShrinkWhenDeselected;

            } else if (TaskType.MANUAL_LITERAL.equals(referencedOrRealTaskType)) {
                f.setImage(ir.get(ProcessWidgetConstants.IMG_MANUALTASK_ICON),
                        isSatisfiedReferenceTask);
            } else if (TaskType.SEND_LITERAL.equals(referencedOrRealTaskType)) {
                f.setImage(ir.get(ProcessWidgetConstants.IMG_SENDTASK_ICON),
                        isSatisfiedReferenceTask);
            } else if (TaskType.RECEIVE_LITERAL
                    .equals(referencedOrRealTaskType)) {
                if (!adapter.hasIncomingFlowsToActivity()) {

                    /*
                     * XPD-6804: Saket: A receive task with no incoming flows
                     * (i.e., a receive task being used as a start activity)
                     * should have a start message event with an envelope icon.
                     */

                    /*
                     * Create a new event figure.
                     */
                    IFigure eventFigure =
                            (new EventEditPart(getAdapterFactory())
                                    .createFigure());

                    if (eventFigure instanceof ShapeWithDescriptionFigure) {

                        ShapeWithDescriptionFigure swdf =
                                (ShapeWithDescriptionFigure) eventFigure;

                        /*
                         * Configure the ShapeWithDescriptionFigure as a message
                         * start event.
                         */
                        swdf.setImage(ir
                                .get(ProcessWidgetConstants.IMG_INCOMING_REQUEST_EVENT_ICON));

                        /*
                         * Set ellipse size.
                         */
                        swdf.setEllipseSize(20, 1);

                        /*
                         * Set background color as that of the current task
                         * figure.
                         */
                        swdf.setBackgroundColor(this.getFigure()
                                .getBackgroundColor());

                        /*
                         * Set image figure.
                         */
                        f.setImageFigure(swdf);
                    }

                } else {
                    f.setImage(ir
                            .get(ProcessWidgetConstants.IMG_RECEIVETASK_ICON),
                            isSatisfiedReferenceTask);
                }
            } else if (TaskType.REFERENCE_LITERAL
                    .equals(referencedOrRealTaskType)) {
                f.setImage(ir
                        .get(ProcessWidgetConstants.IMG_REFERENCETASK_ICON),
                        isSatisfiedReferenceTask);
            } else {
                f.setImage(null, isSatisfiedReferenceTask);
            }
        }
    }

    /**
     * @return The start event figure to use as the icon for collapsed event
     *         sub-process (or null if no start event in sub-proc etc).
     */
    private IFigure getCollapsedEventSubProcessImage() {
        IFigure embeddedStartEventFig = null;

        if (isCollapsedEmbeddedSubproc()) {

            /*
             * ABPM-911: Saket: Update the subprocess icon according to the
             * trigger type of the start event placed inside it.
             */

            /*
             * Get child start event edit part of the task edit part instance
             * currently being processed.
             */
            EventEditPart evtEditPart = getChildrenStartEventEditPart(this);

            /*
             * If the event edit part exists and is an edit part of a start
             * event, then set the image figure of the subprocess to the image
             * figure of the start event (as we want to show up the image of the
             * enclosed start event on the collapsed subprocess)
             */
            if (evtEditPart != null) {
                /*
                 * Get a clone of the event image figure.
                 */
                embeddedStartEventFig = evtEditPart.cloneFigure();

                /* Sid XPD-6452 make a little smaller for better look. */
                if (embeddedStartEventFig instanceof ShapeWithDescriptionFigure) {
                    ((ShapeWithDescriptionFigure) embeddedStartEventFig)
                            .setEllipseSize(21, 1);
                }

                /*
                 * Set background color of this event figure to what it is for
                 * its parent task figure.
                 */
                embeddedStartEventFig.setBackgroundColor(this.getFigure()
                        .getBackgroundColor());

            }
        }
        return embeddedStartEventFig;
    }

    /**
     * Get children start event edit part of the current task edit part
     * instance.
     * 
     * @param The
     *            task edit part object.
     * @return The children event edit part of the current task edit part
     *         instance.
     */
    private EventEditPart getChildrenStartEventEditPart(EditPart tskEditPart) {

        for (Object object : tskEditPart.getChildren()) {

            /*
             * Check if it's an event edit part.
             */
            if (object instanceof EventEditPart) {

                EventEditPart evEditPart = (EventEditPart) object;

                /*
                 * We are only interested in start events.
                 */
                if (evEditPart.getEventFlowType() == EventFlowType.FLOW_START) {
                    return (EventEditPart) object;
                }
            }

            /*
             * XPD-6922: Saket: We don't need to recurse through the children
             * edit parts, just go through the direct children.
             */
        }
        return null;
    }

    @Override
    public void setSelected(int value) {
        super.setSelected(value);

        if (shrinkWhenDeselected && value == SELECTED_NONE) {
            if (!growTaskMouseMotionListener.isEnabled()) {
                growTaskMouseMotionListener
                        .returnToInitialStateAndSetEnabled(true);

            }
        } else {
            if (growTaskMouseMotionListener.isEnabled()) {
                growTaskMouseMotionListener.setEnabled(false);
                refreshVisuals();

            }
        }

        return;
    }

    /**
     * Get list of edit parts for events that are attached to border of this
     * task.
     * 
     * @return
     */
    public Collection<EventEditPart> getAttachedEvents() {
        Map editPartRegistry = getViewer().getEditPartRegistry();
        Collection attachedEvents = getActivityAdapter().getAttachedEvents();

        List<EventEditPart> attachedEPs = new ArrayList<EventEditPart>();

        if (attachedEvents != null) {
            for (Iterator iter = attachedEvents.iterator(); iter.hasNext();) {
                Object ev = iter.next();

                EditPart ep = (EditPart) editPartRegistry.get(ev);
                if (ep instanceof EventEditPart) {
                    attachedEPs.add((EventEditPart) ep);
                }
            }
        }

        return attachedEPs;
    }

    /**
     * @param ir
     * @return
     */
    private Button createSubProcessMarker(ImageRegistry ir) {
        Image img = ir.get(ProcessWidgetConstants.IMG_SUBFLOW_MARKER);
        Button b = new Button(img);
        b.setStyle(0);
        b.setRolloverEnabled(true);
        b.setFocusTraversable(false);
        b.setBorder(null);
        b.getModel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                TaskAdapter aa = (TaskAdapter) getModelAdapter();

                ProcessWidget widget =
                        (ProcessWidget) getViewer()
                                .getProperty(ProcessWidgetConstants.PROP_WIDGET);

                /*
                 * ABPM-911: Saket: An event subprocess should mostly behave
                 * like an embedded sub-process.
                 */
                if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(aa
                        .getTaskType())
                        || TaskType.EVENT_SUBPROCESS_LITERAL.equals(aa
                                .getTaskType())) {
                    //
                    // Expand a collapsed embedded sub-process.
                    if (aa.isEmbeddedSubProcessCollapsed()) {
                        CompoundCommand cmd = new CompoundCommand();
                        cmd.setLabel(Messages.TaskEditPart_OpenEmbSubProc_menu);

                        cmd.append(new OpenEmbSubProcCommand(getThis()));

                        getEditingDomain().getCommandStack().execute(cmd);

                        getViewer().select(getThis());

                    }

                } else {
                    //
                    // Goto / browse for sub-process.
                    EObject subProc = aa.getSubprocess();
                    if (subProc == null) {
                        final boolean pickPageflow =
                                ProcessWidgetType.PAGEFLOW_PROCESS.equals(aa
                                        .getProcessType());

                        ProcessFilterPicker picker =
                                new ProcessFilterPicker(
                                        widget.getSite().getShell(),
                                        pickPageflow ? ProcessPickerType.ALL_PROCESS_TYPES
                                                : ProcessPickerType.BUSINESS_OR_SERVICE_PROCESS,
                                        false);

                        if (aa.getTarget() instanceof EObject) {
                            picker.setScope((EObject) aa.getTarget());
                            Object newTarget =
                                    ProcessUIUtil.getResultFromPicker(picker,
                                            widget.getSite().getShell(),
                                            widget.getInput());
                            if (newTarget != null) {
                                Command cmd =
                                        aa.getSetSubprocessCommand(getEditingDomain(),
                                                (EObject) newTarget);
                                getEditingDomain().getCommandStack()
                                        .execute(cmd);
                                subProc = aa.getSubprocess();
                                if (subProc != null) {
                                    // widget.navigateTo(subProc);
                                    revealObject(subProc);
                                }
                            }
                        }
                    } else {
                        // widget.navigateTo(subProc);
                        revealObject(subProc);
                    }
                }
            }
        });
        b.setCursor(SharedCursors.HAND);

        Label tooltipFig;

        if (isEmbeddedSubProc() && isCollapsedEmbeddedSubproc()) {
            tooltipFig =
                    new Label(
                            Messages.TaskEditPart_ClickToExpandEmbSubProc_tooltip,
                            img);
        } else {
            tooltipFig =
                    new Label(Messages.TaskEditPart_OpenSubProc_message, img);
        }

        tooltipFig.setBorder(new MarginBorder(3));
        b.setToolTip(tooltipFig);
        return b;
    }

    /**
     * @param ir
     * @return
     */
    private ImageFigure createEmbSubCollapseMarker(ImageRegistry ir) {
        if (openCloseButton == null) {
            Image img = ir.get(ProcessWidgetConstants.IMG_COLLAPSE_EMB);
            openCloseButton = new ImageFigure(img);

            openCloseButton.setFocusTraversable(false);
            openCloseButton.setBorder(null);

            openCloseButton.addMouseListener(new MouseListener() {

                @Override
                public void mouseDoubleClicked(MouseEvent me) {
                }

                @Override
                public void mousePressed(MouseEvent me) {
                    me.consume();
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                    CompoundCommand cmd = new CompoundCommand();
                    cmd.setLabel(Messages.TaskEditPart_CloseEmbSubProc_menu);

                    cmd.append(new CloseEmbSubProcCommand(getThis()));
                    getEditingDomain().getCommandStack().execute(cmd);
                    getViewer().select(getThis());
                }
            });

            openCloseButton.setCursor(SharedCursors.HAND);

            Label tooltipFig =
                    new Label(Messages.TaskEditPart_CloseEmbSubProc_tooltip,
                            img);
            tooltipFig.setBorder(new MarginBorder(3));
            openCloseButton.setToolTip(tooltipFig);

        }
        return openCloseButton;
    }

    public boolean isCollapsedEmbeddedSubproc() {
        // We'll class anything under minimum size for content as closed.
        if (isEmbeddedSubProc()) {
            TaskAdapter adapter = getActivityAdapter();
            return adapter.isEmbeddedSubProcessCollapsed();
        }

        return false;
    }

    private TaskEditPart getThis() {
        return this;
    }

    /**
     * @param img
     * @param tooltip
     * @return
     */
    private PrintableImageFigure createMarkerFigure(Image img, String tooltip) {
        PrintableImageFigure fig = new PrintableImageFigure(img);
        Label tooltipFig = new Label(tooltip, img);
        tooltipFig.setBorder(new MarginBorder(3));
        fig.setToolTip(tooltipFig);
        return fig;
    }

    /**
     * Override default bounds based locator with locator thats take care of
     * bounds of the text instead of the whole activity
     */
    @Override
    protected CellEditorLocator getDirectEditEditorLocator() {
        return new AutoSizingDirectEditLocator() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * com.tibco.xpd.processwidget.parts.AutoSizingDirectEditLocator
             * #getTextBoundsLocation()
             */
            @Override
            public Rectangle getTextBoundsLocation() {
                TaskFigure taskFig = (TaskFigure) getFigure();
                Rectangle textBnds = taskFig.getDirectEditTextBounds();

                taskFig.translateToAbsolute(textBnds);

                return textBnds;
            }

            @Override
            public int getDesiredWidth() {
                TaskFigure taskFig = (TaskFigure) getFigure();
                Rectangle textBnds = taskFig.getDirectEditTextBounds();

                return textBnds.width;
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getDirectEditStyle
     * ()
     */
    @Override
    protected int getDirectEditStyle() {
        if (isEmbeddedSubProc()) {
            return SWT.WRAP | SWT.LEFT;
        }
        return SWT.WRAP | SWT.CENTER;
    }

    /*
     * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
     */
    @Override
    protected void createEditPolicies() {

        super.createEditPolicies();

        installEditPolicy(EditPolicy.LAYOUT_ROLE,
                new FlowContainerXYLayoutEditPolicy(getAdapterFactory(),
                        getEditingDomain()));

        installEditPolicy("Hover", new SpyglassHoverEditPolicy()); //$NON-NLS-1$

        // SID - we now use new gadgets for highlighting AND selecting
        // references (on selected object) so no need for old HighlightRefPolicy
        //
        // installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE,
        // new TaskRefHighlightPolicy());

        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new DirectEditPolicy() {
            @Override
            protected org.eclipse.gef.commands.Command getDirectEditCommand(
                    DirectEditRequest request) {
                TaskEditPart taskEP = (TaskEditPart) getHost();
                TaskAdapter ta = taskEP.getActivityAdapter();

                EditingDomain editingDomain =
                        AdapterFactoryEditingDomain.getEditingDomainFor(ta
                                .getTarget());

                String val = (String) request.getCellEditor().getValue();

                return new EMFCommandWrapper(editingDomain,
                        SetTaskNameCommand.create(editingDomain,
                                (EObject) taskEP.getModel(),
                                val));
            }

            @Override
            protected void showCurrentEditValue(DirectEditRequest request) {
            }

        });

        /*
         * Change to permanent use of SnapFeedbackPolicy conditionalised
         * internally on whether the task is embedded sub-proc. This then
         * ensures taht it works when task type is changed to embedded from
         * something else.
         */
        installEditPolicy("Snap Feedback", new SnapFeedbackPolicy() { //$NON-NLS-1$
                    @Override
                    public void showTargetFeedback(Request req) {
                        if (isEmbeddedSubProc()) {
                            super.showTargetFeedback(req);
                        }
                    }
                });

        return;
    }

    @Override
    protected void createClickOrDragGadgetPolicies() {
        ProcessWidget processWidget = getProcessWidget();

        // Install the change type policy before the standard ones (so that it
        // always stays in the same place visually

        /*
         * There is only one task type in decision flwo at the moment so don't
         * create the gadget for decision flows
         */
        if (!ProcessWidgetType.DECISION_FLOW.equals(processWidget
                .getProcessWidgetType())) {
            installEditPolicy(CycleTaskTypeGadgetPolicy.EDIT_POLICY_ID,
                    new CycleTaskTypeGadgetPolicy(getAdapterFactory(),
                            getEditingDomain(), processWidget
                                    .getEditPolicyEnablementProvider()));
        }

        super.createClickOrDragGadgetPolicies();

        installEditPolicy(ClickOrDragTaskReferencePolicy.EDIT_POLICY_ID,
                new ClickOrDragTaskReferencePolicy(getAdapterFactory(),
                        getEditingDomain(), processWidget
                                .getEditPolicyEnablementProvider()));

        //
        // There should be no reply activity selection for Task Libraries
        // (because there are no receive tasks.
        if (!ProcessWidgetType.TASK_LIBRARY.equals(getProcessWidgetType())) {

            /*
             * Sid ACE-1350 we don't do incoming request/reply activity anymore
             * for ACE so hide the gadget options we no longer support
             */
            if (false) {
                installEditPolicy(ClickOrDragReplyActivityPolicy.EDIT_POLICY_ID,
                        new ClickOrDragReplyActivityPolicy(getAdapterFactory(),
                                getEditingDomain(), processWidget
                                        .getEditPolicyEnablementProvider()));

                installEditPolicy(
                        ClickOrDragThrowFaultEventPolicy.EDIT_POLICY_ID,
                        new ClickOrDragThrowFaultEventPolicy(
                                getAdapterFactory(), getEditingDomain(),
                                processWidget
                                        .getEditPolicyEnablementProvider()));
            }
        }

        return;
    }

    /*
     * @see
     * com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetListener
     * #notifyChanged(com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.
     * ProcessWidgetEvent)
     */
    @Override
    public void notifyChanged(ProcessWidgetEvent processEvent) {
        refresh();
    }

    @Override
    public HoverInfo getHoverInfo() {
        TaskAdapter aa = getActivityAdapter();

        TaskType tt = aa.getTaskType();

        HoverInfo info = new HoverInfo(tt.toString());

        String name;
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            name = String.format("%s (%s)", aa.getName(), aa.getTokenName()); //$NON-NLS-1$
        } else {
            name = aa.getName();
        }

        info.addProperty(Messages.TaskEditPart_HoverName_label, name);

        EObject[] performers = aa.getActivityPerformers();

        String desc = null;
        for (int i = 0; i < performers.length; i++) {
            if (i == 0) {
                desc =
                        String.valueOf(aa
                                .getPerformerDescription(performers[i]));

            } else {
                desc = desc + "; " + String.valueOf(aa //$NON-NLS-1$
                        .getPerformerDescription(performers[i]));
            }
        }

        if (desc != null) {
            info.addProperty(Messages.TaskEditPart_HoverParticipant_label, desc);
        }

        if (TaskType.SUBPROCESS_LITERAL.equals(tt)) {
            TaskAdapter sa = aa;
            String subProcDesc = sa.getSubprocessDescription();
            if (subProcDesc == null) {
                subProcDesc = Messages.TaskEditPart_HoverNotSet_label;
            }
            info.addProperty(Messages.TaskEditPart_HoverSubProc_label,
                    subProcDesc);
            String loc = sa.getSubprocessLocationDescription();
            if (loc != null) {
                info.addProperty(Messages.TaskEditPart_HoverSubProcLocation_label,
                        loc);
            }
        }

        info.setDocumentationURL(aa.getDocumentationURL(), this);

        return info;
    }

    /**
     * returns true if the given location in absolute co-ords is counted as 'in
     * the task' bounds. If this is an embedded sub-proc then the content pane
     * is EXCLUDED
     * 
     * @param location
     * @return
     */
    public boolean taskContainsAbsPoint(Point location) {
        boolean ret = false;

        TaskFigure tf = (TaskFigure) getFigure();

        Rectangle bnds = tf.getSelectionBounds();

        tf.translateToAbsolute(bnds);

        if (bnds.contains(location)) {
            // It's within main bounds, now exclude emb subproc content pane.
            if (isEmbeddedSubProc() && !isCollapsedEmbeddedSubproc()) {
                IFigure content = getContentPane();

                Rectangle cbnds = content.getBounds().getCopy();
                content.translateToAbsolute(cbnds);

                if (!cbnds.contains(location)) {
                    ret = true; // point not in content pane.
                }
            } else {
                ret = true;
            }

        }
        return (ret);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getXpdSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    protected ConnectionAnchor getXpdSourceConnectionAnchor(
            ConnectionEditPart connection) {
        ConnectionAnchor anchor = null;

        // Check if there's a user specific anchor pos set for connection.
        // If so use line anchor.
        if (connection instanceof BaseConnectionEditPart) {
            Double startPos =
                    ((BaseConnectionEditPart) connection)
                            .getStartAnchorPosition();

            if (startPos != null) {
                anchor = new LineAnchor(getFigure(), startPos);
            }
        }

        if (anchor == null) {
            // Otherwise use chopbox anchor for associations
            if (connection instanceof AssociationEditPart) {
                if (associationAnchor == null) {
                    associationAnchor = new ChopboxAnchor(getFigure());
                }

                anchor = associationAnchor;
            }
            // For sequence / message flow use special activity anchor
            else {
                anchor = new TaskActivityAnchor(getFigure(), true);
            }
        }

        return anchor;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getXpdTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    protected ConnectionAnchor getXpdTargetConnectionAnchor(
            ConnectionEditPart connection) {
        ConnectionAnchor anchor = null;

        // Check if there's a user specific anchor pos set for connection.
        // If so use line anchor.
        if (connection instanceof BaseConnectionEditPart) {
            Double endPos =
                    ((BaseConnectionEditPart) connection)
                            .getEndAnchorPosition();

            if (endPos != null) {
                anchor = new LineAnchor(getFigure(), endPos);
            }
        }

        if (anchor == null) {
            boolean isFromCatchCompensation = false;
            if (connection.getSource() instanceof EventEditPart) {
                EventEditPart ev = (EventEditPart) connection.getSource();
                if (ev.isAttachedToTaskBorder()) {
                    if (EventFlowType.FLOW_INTERMEDIATE == ev
                            .getEventFlowType()
                            && EventTriggerType.EVENT_COMPENSATION_CATCH == ev
                                    .getEventTriggerType()) {
                        isFromCatchCompensation = true;
                    }
                }
            }

            // Otherwise use chopbox anchor for associations
            if (!isFromCatchCompensation
                    && connection instanceof AssociationEditPart) {
                if (associationAnchor == null) {
                    associationAnchor = new ChopboxAnchor(getFigure());
                }

                anchor = associationAnchor;
            }
            // For sequence / message flow use special activity anchor
            else {
                anchor = new TaskActivityAnchor(getFigure(), false);
            }
        }

        return anchor;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getXpdSourceConnectionAnchor(org.eclipse.gef.Request)
     */
    @Override
    protected ConnectionAnchor getXpdSourceConnectionAnchor(Request request) {
        return getAnchorFromRequest(request, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#
     * getXpdTargetConnectionAnchor(org.eclipse.gef.Request)
     */
    @Override
    public ConnectionAnchor getXpdTargetConnectionAnchor(Request request) {
        return getAnchorFromRequest(request, false);
    }

    /**
     * @return lazy created anchor from request
     */
    private ConnectionAnchor getAnchorFromRequest(Request request,
            boolean isSource) {
        ConnectionAnchor anchor = null;

        if (request instanceof DropRequest) {

            // If it's on border of task use a line anchor
            // for specific attachment position to border
            TaskFigure fig = (TaskFigure) getFigure();

            Point location = null;

            if (isSource && request instanceof CreateConnectionRequest) {
                location =
                        (Point) request
                                .getExtendedData()
                                .get(FlowConnectionToolEntry.REQ_EXT_DATA_INITIAL_LOCATION);
            }

            if (location == null) {
                location = ((DropRequest) request).getLocation();
            }

            Point rel = location.getCopy();

            fig.translateToRelative(rel);
            if (fig.borderLineContainsPoint(rel)) {
                anchor = new LineAnchor(fig, location.getCopy());
            }
        }

        if (anchor == null) {
            boolean isFromCatchCompensation = false;

            if (!isSource && request instanceof CreateConnectionRequest) {
                CreateConnectionRequest creq =
                        (CreateConnectionRequest) request;

                if (creq.getSourceEditPart() instanceof EventEditPart) {
                    EventEditPart ev = (EventEditPart) creq.getSourceEditPart();
                    if (ev.isAttachedToTaskBorder()) {
                        if (EventFlowType.FLOW_INTERMEDIATE == ev
                                .getEventFlowType()
                                && EventTriggerType.EVENT_COMPENSATION_CATCH == ev
                                        .getEventTriggerType()) {
                            isFromCatchCompensation = true;
                        }
                    }
                }
            }

            // Not a fixed position anchor.
            // For associations use a plain chop box anchor.
            if (!isFromCatchCompensation
                    && (((request instanceof CreateConnectionRequest) && ((CreateConnectionRequest) request)
                            .getNewObjectType() == AssociationAdapter.class) || ((request instanceof ReconnectRequest) && ((ReconnectRequest) request)
                            .getConnectionEditPart() instanceof AssociationEditPart))) {
                if (associationAnchor == null) {
                    associationAnchor = new ChopboxAnchor(getFigure());
                }

                anchor = associationAnchor;
            }
            // For sequence / message flow use special activity anchor
            else {
                anchor = new TaskActivityAnchor(getFigure(), isSource);
            }
        }

        return anchor;
    }

    /*
     * @see org.eclipse.gef.EditPart#setModel(java.lang.Object)
     */
    @Override
    public void setModel(Object model) {
        if (((EObject) model).eContainer() == null) {
            throw new IllegalArgumentException("Disconnected activity!"); //$NON-NLS-1$
        }
        super.setModel(model);
    }

    @Override
    public void activate() {
        super.activate();

        refreshVisuals();
    }

    @Override
    public IFigure getContentPane() {
        if (isEmbeddedSubProc()) {
            TaskFigure fig = (TaskFigure) getFigure();
            return fig.getContentPane();
        }

        return getFigure();
    }

    @Override
    protected List getModelChildren() {
        TaskAdapter ta = getActivityAdapter();

        List children = ta.getChildGraphicalNodes();

        List attachedEvents = new ArrayList();

        // Make sure that events attached to border of child tasks are last in
        // list!
        // This ensures that edit parts and figures are kept in correct order
        // for updates and display.
        for (Iterator iter = children.iterator(); iter.hasNext();) {
            Object child = iter.next();

            Object adapter =
                    getAdapterFactory().adapt(child,
                            ProcessWidgetConstants.ADAPTER_TYPE);

            if (adapter instanceof EventAdapter) {
                attachedEvents.add(child);

                iter.remove();
            }
        }

        if (attachedEvents.size() > 0) {
            children.addAll(attachedEvents);
        }

        return children;
    }

    @Override
    public Translatable translateToModel(Translatable translatable) {
        // SID - Task co-ordinates are now centred.
        IFigure fig = getFigure();
        if (fig != null && translatable instanceof Rectangle) {
            Rectangle rc = (Rectangle) translatable;

            // Convert top-left location to centre.
            rc.x += rc.width / 2;
            rc.y += rc.height / 2;
            return (translatable);
        } else {
            return super.translateToModel(translatable);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getFillColorID()
     */
    @Override
    public String getFillColorIDForPartType() {
        String colorID = null;

        TaskAdapter ta = getActivityAdapter();

        TaskType tt = ta.getTaskType();

        return tt.getGetDefaultFillColorId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getLineColorID()
     */
    @Override
    public String getLineColorIDForPartType() {
        TaskAdapter ta = getActivityAdapter();

        TaskType tt = ta.getTaskType();

        return tt.getGetDefaultLineColorId();

    }

    public boolean isEmbeddedSubProc() {
        TaskAdapter aa = getActivityAdapter();

        /*
         * ABPM-911: Saket: An event subprocess should mostly behave like an
         * embedded sub-process.
         */
        if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(aa.getTaskType())
                || TaskType.EVENT_SUBPROCESS_LITERAL.equals(aa.getTaskType())) {
            return (true);
        }
        return (false);
    }

    /**
     * Return <code>true</code> if the task type is event subprocess,
     * <code>false</code> otherwise.
     * 
     * @return <code>true</code> if the task type is event subprocess,
     *         <code>false</code> otherwise.
     */
    public boolean isEventSubProc() {
        TaskAdapter aa = getActivityAdapter();

        if (TaskType.EVENT_SUBPROCESS_LITERAL.equals(aa.getTaskType())) {
            return (true);
        }
        return (false);
    }

    /**
     * return true if this is a task object false if sub-proc (embedded or
     * independent).s
     * 
     * @return
     */
    public boolean isNonSubProcTask() {
        return !getActivityAdapter().getTaskType().isSubProcessType();
    }

    /**
     * Override getTargetEditPart() so that we can redirect selection requests
     * within content to root edit part so that marquee (drag-select) selection
     * of items within embedded sub-proc content works the same as drag select
     * of items within lane content.
     */

    NoteEditPart badMoveRequestRedirectEditPart = null;

    @Override
    public EditPart getTargetEditPart(Request request) {

        if (RequestConstants.REQ_SELECTION == request.getType()
                && isEmbeddedSubProc() && !isCollapsedEmbeddedSubproc()) {

            if (request instanceof SelectionRequest) {
                SelectionRequest req = (SelectionRequest) request;

                if (!(req.getLastButtonPressed() == 3)) {
                    Point loc = req.getLocation();
                    if (loc != null) {
                        loc = loc.getCopy();
                        IFigure fig = getFigure();
                        // fig.translateToRelative(loc);

                        IFigure content = getContentPane();
                        content.translateToRelative(loc);
                        Rectangle rc = content.getBounds().getCopy();

                        if (rc.contains(loc)) {
                            return getRoot();
                        }
                    }
                }
            }
        } else if (REQ_CONNECTION_START.equals(request.getType())
                || REQ_CONNECTION_END.equals(request.getType())) {
            CreateConnectionRequest creq = (CreateConnectionRequest) request;

            // background of content pane is not a valid target for
            // connections...
            if (!taskContainsAbsPoint(creq.getLocation())) {
                return null;
            }

        } else if (REQ_RECONNECT_TARGET.equals(request.getType())
                || REQ_RECONNECT_SOURCE.equals(request.getType())) {

            ReconnectRequest creq = (ReconnectRequest) request;

            // background of content pane is not a valid target for
            // connections...
            if (!taskContainsAbsPoint(creq.getLocation())) {
                return null;
            }

        }

        return super.getTargetEditPart(request);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart#getAdapter(java
     * .lang.Class)
     */
    @Override
    public Object getAdapter(Class key) {
        if (key == SnapToHelper.class) {
            Boolean grid =
                    (Boolean) getViewer()
                            .getProperty(SnapToGrid.PROPERTY_GRID_ENABLED);
            /*
             * Alignment guide doesn't seem to work very well within a task so
             * disabled for now...
             */
            Boolean geom =
                    (Boolean) getViewer()
                            .getProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED);
            if (grid != null && grid.booleanValue() && geom != null
                    && geom.booleanValue()) {
                SnapToHelper[] helpers =
                        new SnapToHelper[] { new XPDSnapToGrid(this),
                                new BpmnSnapToGeometry(this) };
                return new CompoundSnapToHelper(helpers);
            }
            if (geom != null && geom.booleanValue()) {
                return new BpmnSnapToGeometry(this);
            }

            if (grid != null && grid.booleanValue()) {
                return new XPDSnapToGrid(this);
            }
        }
        return super.getAdapter(key);
    }

    /*
     * Refresh the grid layer in the embedded sub-proc content from system
     * props.
     */
    protected void refreshGridLayer() {
        boolean visible = false;
        GridLayer grid = ((TaskFigure) getFigure()).getGridLayer();
        Boolean val =
                (Boolean) getViewer()
                        .getProperty(SnapToGrid.PROPERTY_GRID_VISIBLE);
        if (val != null)
            visible = val.booleanValue();
        grid.setOrigin((Point) getViewer()
                .getProperty(SnapToGrid.PROPERTY_GRID_ORIGIN));
        grid.setSpacing((Dimension) getViewer()
                .getProperty(SnapToGrid.PROPERTY_GRID_SPACING));
        grid.setVisible(visible);
    }

    /*
     * Register the grid properties change listener for the embedded sub-proc
     * content grid.
     */
    @Override
    protected void register() {
        super.register();
        if (((TaskFigure) getFigure()).getGridLayer() != null) {
            getViewer().addPropertyChangeListener(gridListener);
            refreshGridLayer();
        }
    }

    /*
     * UnRegister the grid properties change listener for the embedded sub-proc
     * content grid.
     */
    @Override
    protected void unregister() {
        getViewer().removePropertyChangeListener(gridListener);
        super.unregister();
    }

    /**
     * Return this embedded sub-process optimum size for given size of content.
     * (Nominally, this means setting its size to fit its content at normal 100%
     * zoom level (if the editor uses this method of ensuring that all content
     * is visible).
     * 
     * @return new dimension
     */
    public Dimension getOptimumSize(Dimension contentSize) {
        return (((TaskFigure) getFigure()).getOptimumSize(contentSize));
    }

    /**
     * Get the minimum size of task to display it's contents. If the task is not
     * an embedded sub-process then the minimum size is returned as 10x10.
     * 
     * @return
     */
    public Dimension getMinimumSizeForContent() {
        Dimension minSize = null;

        if (isEmbeddedSubProc() && !isCollapsedEmbeddedSubproc()) {
            // Then get optimum size of figure for the given content size.
            Rectangle minContentArea = getMinimumContentArea();

            if (minContentArea != null) {
                minSize = getOptimumSize(minContentArea.getSize());
            }
        }

        if (minSize == null) {
            minSize = new Dimension(40, 25);
        }

        return minSize;
    }

    /**
     * @return
     */
    public Rectangle getMinimumContentArea() {
        Rectangle minContentArea = null;

        if (isEmbeddedSubProc()) {

            IFigure contentFig = getContentPane();

            if (contentFig != null) {
                List children = contentFig.getChildren();

                Rectangle contentRect = contentFig.getBounds().getCopy();

                if (children.size() > 0) {
                    minContentArea = new Rectangle(0, 0, 0, 0);

                    for (Iterator iter = children.iterator(); iter.hasNext();) {
                        IFigure child = (IFigure) iter.next();

                        Rectangle bnds = child.getBounds().getCopy();
                        bnds.x -= contentRect.x;
                        bnds.y -= contentRect.y;

                        if (bnds.right() > minContentArea.right()) {
                            minContentArea.width =
                                    bnds.right() - minContentArea.x;
                        }

                        if (bnds.bottom() > minContentArea.bottom()) {
                            minContentArea.height =
                                    bnds.bottom() - minContentArea.y;
                        }
                    }
                } else {
                    minContentArea =
                            new Rectangle(0, 0,
                                    ProcessWidgetConstants.TASK_WIDTH_SIZE,
                                    ProcessWidgetConstants.TASK_HEIGHT_SIZE);
                }

                minContentArea.width +=
                        ProcessWidgetConstants.EMB_SUBPROC_CONTENT_MARGIN;
                minContentArea.height +=
                        ProcessWidgetConstants.EMB_SUBPROC_CONTENT_MARGIN;

            }

        }
        return minContentArea;
    }

    public boolean revealObject(EObject processOrInterface) {
        try {
            IConfigurationElement facConfig =
                    XpdResourcesUIActivator
                            .getEditorFactoryConfigFor(processOrInterface);
            String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            EditorInputFactory f =
                    (EditorInputFactory) facConfig
                            .createExecutableExtension("factory"); //$NON-NLS-1$
            IEditorInput input = f.getEditorInputFor(processOrInterface);
            page.openEditor(input, editorID);
            return true;
        } catch (CoreException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Add listener for diagram refresh.
     * 
     * Pool's will fire a diagram refresh event when the closed state is changed
     * OR when a child lane fires a diagram refresh event.
     * 
     * NOTE: If you wish to listen to ANY pool's close (current or pool added in
     * future) then you can add a listener to process edit part.
     * 
     * @param listener
     */
    public void addDiagramRefreshListener(PropertyChangeListener listener) {
        if (diagramRefreshListeners.contains(listener)) {
            diagramRefreshListeners.remove(listener);
        }

        diagramRefreshListeners.add(listener);
    }

    public void removeDiagramRefreshListener(PropertyChangeListener listener) {
        diagramRefreshListeners.remove(listener);
    }

    private void fireExpandCollapseChange(boolean newState) {

        PropertyChangeEvent evt =
                new PropertyChangeEvent(this,
                        PROPERTY_EMBEDDEDSUBPROC_CLOSESTATE, new Boolean(
                                !newState), new Boolean(newState));

        for (PropertyChangeListener listener : diagramRefreshListeners) {
            listener.propertyChange(evt);
        }
    }

    @Override
    public void refresh() {
        super.refresh();

        if (isEmbeddedSubProc()) {
            if (firstRefresh) {
                firstRefresh = false;

                previousClosedState =
                        isEmbeddedSubProc() && isCollapsedEmbeddedSubproc();
            }

            boolean newState =
                    isEmbeddedSubProc() && isCollapsedEmbeddedSubproc();

            if (previousClosedState != newState) {
                previousClosedState = newState;

                fireExpandCollapseChange(newState);
            }

        } else {
            firstRefresh = true;
        }
    }

    @Override
    protected void addChildVisual(EditPart childEditPart, int index) {
        super.addChildVisual(childEditPart, index);

        if (childEditPart instanceof TaskEditPart) {
            ((TaskEditPart) childEditPart)
                    .addDiagramRefreshListener(embSubProcDiagramRefreshListener);
        }
    }

    @Override
    protected void removeChildVisual(EditPart childEditPart) {
        /* Detach the hyperlink listener from the figure. */
        if (activityHyperLinkListener != null) {
            activityHyperLinkListener.setHyperlinkFigure(null);
        }

        if (activityHyperLinkListenerRefIcon != null) {
            activityHyperLinkListenerRefIcon.setHyperlinkFigure(null);
        }

        // remove diagram refresh listener from lanes.
        if (childEditPart instanceof TaskEditPart) {
            ((TaskEditPart) childEditPart)
                    .removeDiagramRefreshListener(embSubProcDiagramRefreshListener);
        }

        super.removeChildVisual(childEditPart);

    }
}
