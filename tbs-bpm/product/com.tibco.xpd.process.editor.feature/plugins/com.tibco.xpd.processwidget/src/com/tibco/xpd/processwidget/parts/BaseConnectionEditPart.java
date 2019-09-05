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

package com.tibco.xpd.processwidget.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.help.HelpSystem;
import org.eclipse.help.IContext;
import org.eclipse.jface.viewers.ILabelProvider;

import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.BaseConnectionAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetEvent;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetListener;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.adapters.XPDBendpointType;
import com.tibco.xpd.processwidget.annotations.AnnotationFactory;
import com.tibco.xpd.processwidget.figures.IScaleableConnectionFigure;
import com.tibco.xpd.processwidget.figures.NonNegativeBendpoint;
import com.tibco.xpd.processwidget.figures.ProcessFigure.DiagramViewType;
import com.tibco.xpd.processwidget.figures.XPDFigureUtilities;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.policies.ConnectionLabelLayoutPolicy;
import com.tibco.xpd.processwidget.policies.ConnectionsAnimatorEditPolicy;
import com.tibco.xpd.processwidget.policies.ExternalAnnotationEditPolicy;
import com.tibco.xpd.processwidget.policies.FlowContainerXYLayoutEditPolicy;
import com.tibco.xpd.processwidget.policies.NamedElementDirectEditPolicy;
import com.tibco.xpd.processwidget.tools.FlowConnectionToolEntry;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;

/**
 * Connection edit part that install iteself as a listener to process adapter
 * 
 * @author wzurek
 */
public abstract class BaseConnectionEditPart extends AbstractConnectionEditPart
        implements ProcessWidgetListener, ModelAdapterEditPart, NodeEditPart {

    private final AdapterFactory adapterFactory;

    private BaseProcessAdapter adapter;

    private String helpContextId;

    private ConnectionLabelEditPart connLabelEditPart = null;

    private FlowLabelModel labelModel = new FlowLabelModel(this);

    // If the target object is inside an embedded sub-proc, this is
    // the content figure for the sub-proc task and a listener
    // that allows us to scale line decorations.
    private IFigure targetTaskContainerFigure = null;

    private LayoutListener.Stub targetTaskContainerLayoutListener = null;

    // And the same again for source object...
    private IFigure sourceTaskContainerFigure = null;

    private LayoutListener.Stub sourceTaskContainerLayoutListener = null;

    //
    // Listener for diagram refresh events (namely pool/lane closed events.
    private PropertyChangeListener poolOrLaneClosedListener =
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (PoolEditPart.PROPERTY_POOL_CLOSESTATE.equals(evt
                            .getPropertyName())
                            || LaneEditPart.PROPERTY_LANE_CLOSESTATE.equals(evt
                                    .getPropertyName())
                            || TaskEditPart.PROPERTY_EMBEDDEDSUBPROC_CLOSESTATE
                                    .equals(evt.getPropertyName())) {
                        refreshBendpoints();
                        refresh();
                    }
                }
            };

    public void setHelpContextId(String helpContextId) {
        this.helpContextId = helpContextId;
    }

    public String getHelpContextId() {
        return helpContextId;
    }

    /**
     * @return model adapter for this edit part
     */
    @Override
    public BaseProcessAdapter getModelAdapter() {
        if (adapter == null) {
            adapter =
                    (BaseProcessAdapter) adapterFactory.adapt(getModel(),
                            ProcessWidgetConstants.ADAPTER_TYPE);
        }
        return adapter;
    }

    public BaseConnectionAdapter getModelConnectionAdapter() {
        return (BaseConnectionAdapter) getModelAdapter();
    }

    /**
     * The Constructor
     * 
     * @param adapterFactory
     *            factory that can produce model adapters for the diagram
     */
    public BaseConnectionEditPart(AdapterFactory adapterFactory) {
        this.adapterFactory = adapterFactory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editparts.AbstractConnectionEditPart#setParent(org.eclipse
     * .gef.EditPart)
     */
    @Override
    public void setParent(EditPart parent) {
        //
        // Remove pool/lane closed listener from existing parent.
        // in case parent changes
        ProcessEditPart procEP = getProcessEditPart();

        if (procEP != null) {
            procEP.removeDiagramRefreshListener(poolOrLaneClosedListener);
        }

        super.setParent(parent);

        // Add listener for pools / lanes close state change.
        procEP = getProcessEditPart();

        if (procEP != null) {
            procEP.addDiagramRefreshListener(poolOrLaneClosedListener);
        }

    }

    /**
     * Return the process edit part.
     * 
     * @return
     */
    private ProcessEditPart getProcessEditPart() {
        EditPart parent = getParent();

        // When parent get's set add a any pool collapsed listener
        // so that we can show/hide sequence flows as appropriate.
        while (parent != null && !(parent instanceof WidgetRootEditPart)) {
            parent = parent.getParent();
        }

        if (parent != null) {
            ProcessEditPart procEP =
                    ((WidgetRootEditPart) parent).getProcessEditPart();
            return procEP;
        }

        return null;
    }

    /**
     * activate and install model listener
     */
    @Override
    public void activate() {
        super.activate();

        // make sure that object has its ItemProvider adapter
        EObject eo = (EObject) getModel();
        IEditingDomainProvider edp =
                (IEditingDomainProvider) EcoreUtil.getExistingAdapter(eo
                        .eResource(), IEditingDomainProvider.class);
        if (edp.getEditingDomain() instanceof AdapterFactoryEditingDomain) {
            ((AdapterFactoryEditingDomain) edp.getEditingDomain())
                    .getAdapterFactory().adapt(eo, eo.eClass().getEPackage());
        }

        getModelAdapter().addListener(this);
    }

    /**
     * deactivate and uninstal model listener
     */
    @Override
    public void deactivate() {
        getModelAdapter().removeListener(this);

        // Clean up any previous embedded sub-proc layout listener stuff.
        if (targetTaskContainerFigure != null) {
            if (targetTaskContainerLayoutListener != null) {
                targetTaskContainerFigure
                        .removeLayoutListener(targetTaskContainerLayoutListener);
                targetTaskContainerLayoutListener = null;
            }

            targetTaskContainerFigure = null;
        }

        if (sourceTaskContainerFigure != null) {
            if (sourceTaskContainerLayoutListener != null) {
                sourceTaskContainerFigure
                        .removeLayoutListener(sourceTaskContainerLayoutListener);
                sourceTaskContainerLayoutListener = null;
            }

            sourceTaskContainerFigure = null;
        }

        super.deactivate();
    }

    /**
     * @return adapter factory that can produce model adapters for the diagram <br>
     *         <b>Note:</b> the factory might not be able to produce EMF
     *         ItemAdapters
     */
    @Override
    public AdapterFactory getAdapterFactory() {
        return adapterFactory;
    }

    /*
     * @see
     * org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
     */
    @Override
    public EditingDomain getEditingDomain() {
        return (EditingDomain) getViewer()
                .getProperty(ProcessWidgetConstants.PROP_EDITING_DOMAIN);
    }

    @Override
    public Object getAdapter(Class key) {
        if (key.isAssignableFrom(getModel().getClass())) {
            return getModel();
        } else if (key == IContext.class) {
            if (getHelpContextId() != null) {
                return HelpSystem.getContext(getHelpContextId());
            }
            return null;
        } else if (key == ILabelProvider.class) {
            return getViewer()
                    .getProperty(ProcessWidgetConstants.PROP_LABEL_PROVIDER);
        }
        return super.getAdapter(key);
    }

    /**
     * Default acces to the label. Can be overriden when conneciton label should
     * be avaiable
     * 
     * @return the label (or null when there should be no label)
     */
    protected String getLabel() {
        BaseProcessAdapter ma = getModelAdapter();
        if (ma instanceof NamedElementAdapter) {
            return ((NamedElementAdapter) ma).getName();
        }
        return null;
    }

    /**
     * Return artificial model for label or empty list is there is no label.
     */
    @Override
    protected List getModelChildren() {
        if (getLabel() != null) {
            return Collections.singletonList(labelModel);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Create policy for managing labels
     */
    @Override
    protected void createEditPolicies() {
        // layout for managing labels
        installEditPolicy(EditPolicy.LAYOUT_ROLE,
                new ConnectionLabelLayoutPolicy(getAdapterFactory(),
                        getEditingDomain()));

        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
                new NamedElementDirectEditPolicy());

        List<AnnotationFactory> facts =
                (List<AnnotationFactory>) getViewer()
                        .getProperty(ProcessWidgetConstants.PROP_ANNOTATION_FACTORY);
        if (facts != null) {
            int n = 1;
            for (AnnotationFactory fact : facts) {
                installEditPolicy("Annotation" + n++, new ExternalAnnotationEditPolicy( //$NON-NLS-1$
                                fact, ProcessWidgetConstants.EXTENSIONS_LAYER));
            }
        }

    }

    /**
     * Get the list of bend points.
     * 
     * @return
     */
    public List getBendpoints() {

        // Remove bendpoints when to / from object in closed pool
        if (isObjectInClosedParent(getSource())
                || isObjectInClosedParent(getTarget())) {
            return Collections.EMPTY_LIST;
        }

        BaseConnectionAdapter fc = getModelConnectionAdapter();
        List points = fc.getBendpoints();

        List<RelativeBendpoint> result =
                new ArrayList<RelativeBendpoint>(points.size());
        for (int i = 0; i < points.size(); i++) {
            XPDBendpointType b = (XPDBendpointType) points.get(i);
            RelativeBendpoint rb =
                    new NonNegativeBendpoint(getConnectionFigure());
            rb.setRelativeDimensions(b.fromStart, b.fromEnd);
            rb.setWeight(b.weight);
            result.add(rb);

        }

        return result;
    }

    /**
     * Override to sort out line decoration scaling when in embedded subflow
     * 
     */
    @Override
    protected void refreshVisuals() {

        // Clean up any previous embedded sub-proc layout listener stuff.
        if (targetTaskContainerFigure != null) {
            if (targetTaskContainerLayoutListener != null) {
                targetTaskContainerFigure
                        .removeLayoutListener(targetTaskContainerLayoutListener);
                targetTaskContainerLayoutListener = null;
            }

            targetTaskContainerFigure = null;
        }

        if (sourceTaskContainerFigure != null) {
            if (sourceTaskContainerLayoutListener != null) {
                sourceTaskContainerFigure
                        .removeLayoutListener(sourceTaskContainerLayoutListener);
                sourceTaskContainerLayoutListener = null;
            }

            sourceTaskContainerFigure = null;
        }

        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(this)
                        .getGraphicalNodeColor(getModelConnectionAdapter(),
                                getLineColorIDForPartType());

        getFigure().setForegroundColor(lineColor.getColor());

        if (getFigure() instanceof IScaleableConnectionFigure) {

            if (getSource() instanceof GraphicalEditPart) {
                // Get scale for the source object
                // It may be zoomed inside an embedded sub-proc
                // so need to scale the decorations.
                IScaleableConnectionFigure flowFig =
                        (IScaleableConnectionFigure) getFigure();

                EditPart sourceParent = getSource().getParent();
                if (sourceParent instanceof TaskEditPart
                        && ((TaskEditPart) sourceParent).isEmbeddedSubProc()) {

                    // Need to listen for changes in embedded sub-proc
                    // content pane so can rescale when necessary.
                    sourceTaskContainerFigure =
                            ((TaskEditPart) sourceParent).getContentPane();

                    // First set the initial scale of decorations...
                    flowFig.setSourceDecorationScale(getDecorationScale(sourceTaskContainerFigure));

                    // Then Add a layout listener to the embedded sub-proc
                    // content figure
                    sourceTaskContainerLayoutListener =
                            new EmbeddedSubProcContentLayoutListener(true);
                    sourceTaskContainerFigure
                            .addLayoutListener(sourceTaskContainerLayoutListener);
                } else {
                    flowFig.setSourceDecorationScale(1.0);
                }

            }

            if (getTarget() instanceof GraphicalEditPart) {
                // Get scale for the target object
                // It may be zoomed inside an embedded sub-proc
                // so need to scale the decorations.
                IScaleableConnectionFigure flowFig =
                        (IScaleableConnectionFigure) getFigure();

                EditPart targetParent = getTarget().getParent();

                if (targetParent instanceof TaskEditPart
                        && ((TaskEditPart) targetParent).isEmbeddedSubProc()) {

                    // Need to listen for changes in embedded sub-proc
                    // content pane so can rescale when necessary.
                    targetTaskContainerFigure =
                            ((TaskEditPart) targetParent).getContentPane();

                    // First set the initial scale of decorations...
                    flowFig.setTargetDecorationScale(getDecorationScale(targetTaskContainerFigure));

                    // Then Add a layout listener to the embedded sub-proc
                    // content figure
                    targetTaskContainerLayoutListener =
                            new EmbeddedSubProcContentLayoutListener(false);
                    targetTaskContainerFigure
                            .addLayoutListener(targetTaskContainerLayoutListener);
                } else {
                    flowFig.setTargetDecorationScale(1.0);
                }
            }
        }

        // Give sub-class chance to make connection invisible
        // (certain types are invisible when pool/lane closed.
        boolean visible = shouldBeVisible();
        getFigure().setVisible(visible);

        super.refreshVisuals();
    }

    private double getDecorationScale(IFigure taskContentPane) {
        double tScale = XPDFigureUtilities.getFigureScale(taskContentPane);

        // Then discount the scale that will be applied to the
        // connection layer anyway (i.e. the user set zoom level).
        double lScale =
                XPDFigureUtilities.getFigureScale(getLayer(CONNECTION_LAYER));

        return (tScale / lScale);

    }

    /**
     * EmbeddedSubProcContentLayoutListener
     * 
     * When connected to or from a task inside an embedded sub-proc we need to
     * listen for the layout of the embedded sub-proc content so that we can
     * change the scale of the line decorations according to the scale of
     * objects inside the embedded sub-proc.
     * 
     * This class does just that!
     */
    private final class EmbeddedSubProcContentLayoutListener extends
            LayoutListener.Stub {

        private boolean isSourceDecoration = false;

        /**
         * 
         * @param isSourceDecoration
         *            true if this is listener for source decoration scale.
         */
        public EmbeddedSubProcContentLayoutListener(boolean isSourceDecoration) {
            this.isSourceDecoration = isSourceDecoration;
        }

        @Override
        public boolean layout(IFigure container) {
            // Reset the source / target decoration scale according to
            // the scale of the embedded sub-proc it belongs to.
            if (getFigure() instanceof IScaleableConnectionFigure) {
                IScaleableConnectionFigure flowFig =
                        (IScaleableConnectionFigure) getFigure();

                double scale = getDecorationScale(container);

                if (isSourceDecoration) {
                    flowFig.setSourceDecorationScale(scale);
                } else {
                    flowFig.setTargetDecorationScale(scale);
                }
            }
            return super.layout(container);
        }
    }

    /**
     * Override setSource to ensure bend points are refreshed when source /
     * target set (as is done on create / reconnect)
     */
    @Override
    public void setSource(EditPart editPart) {
        super.setSource(editPart);
        refreshBendpoints();
    }

    /**
     * Override setTarget to ensure bend points are refreshed when source /
     * target set (as is done on create / reconnect)
     */
    @Override
    public void setTarget(EditPart editPart) {
        super.setTarget(editPart);
        refreshBendpoints();
    }

    /**
     * Anything that needs to be done to refresh bendpoints on create / set
     * target etc etc.
     */
    protected void refreshBendpoints() {
        getConnectionFigure().setRoutingConstraint(getBendpoints());
    }

    @Override
    public void notifyChanged(ProcessWidgetEvent processEvent) {
        refreshBendpoints();
        refresh();
        for (Iterator iter = getChildren().iterator(); iter.hasNext();) {
            EditPart ep = (EditPart) iter.next();
            ep.refresh();
        }

        refreshSourceConnections();
        refreshTargetConnections();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections
     * ()
     */
    @Override
    protected List getModelSourceConnections() {
        return ((BaseConnectionAdapter) getModelAdapter())
                .getSourceAssociations();
    }

    @Override
    protected List getModelTargetConnections() {
        return ((BaseConnectionAdapter) getModelAdapter())
                .getTargetAssociations();
    }

    /**
     * Return the user specified non-default start anchor position for the
     * connection
     * 
     * @return Start anchor position (0.0 - 100.0) if specifically set by user
     *         OR null if none set.
     */
    public Double getStartAnchorPosition() {
        BaseConnectionAdapter adp = getModelConnectionAdapter();

        return (adp.getStartAnchorPosition());
    }

    /**
     * Return the user specified non-default end anchor position for the
     * connection
     * 
     * @return Start anchor position (0.0 - 100.0) if specifically set by user
     *         OR null if none set.
     */
    public Double getEndAnchorPosition() {
        BaseConnectionAdapter adp = getModelConnectionAdapter();

        return (adp.getEndAnchorPosition());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef
     * .ConnectionEditPart)
     */
    @Override
    public ConnectionAnchor getSourceConnectionAnchor(
            ConnectionEditPart connection) {

        // Check if there's a user specific anchor pos set for connection.
        if (connection instanceof BaseConnectionEditPart) {
            Double startPos =
                    ((BaseConnectionEditPart) connection)
                            .getStartAnchorPosition();

            if (startPos != null) {
                return new LineAnchor(getFigure(), startPos);
            }
        }

        // If all else fails return a box-standard line anchor
        // (which sets it's position to 50% of line.
        return new LineAnchor(getFigure());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef
     * .ConnectionEditPart)
     */
    @Override
    public ConnectionAnchor getTargetConnectionAnchor(
            ConnectionEditPart connection) {

        // Check if there's a user specific anchor pos set for connection.
        if (connection instanceof BaseConnectionEditPart) {
            Double endPos =
                    ((BaseConnectionEditPart) connection)
                            .getEndAnchorPosition();

            if (endPos != null) {
                return new LineAnchor(getFigure(), endPos);
            }
        }

        // If all else fails return a box-standard line anchor
        // (which sets it's position to 50% of line.
        return new LineAnchor(getFigure());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef
     * .Request)
     */
    @Override
    public ConnectionAnchor getSourceConnectionAnchor(Request request) {

        if (request instanceof DropRequest) {
            // Find out portion of line
            Point location = null;

            if (request instanceof CreateConnectionRequest) {
                // We may be asked for start connection anchor many times,
                // we have to use the original location clicked NOT the
                // current location whilst drag line is in progress.
                location =
                        (Point) request
                                .getExtendedData()
                                .get(FlowConnectionToolEntry.REQ_EXT_DATA_INITIAL_LOCATION);
            }

            if (location == null) {
                location = ((DropRequest) request).getLocation();
            }

            return new LineAnchor(getFigure(), location);

        }

        // If all else fails return a box-standard line anchor
        // (which sets it's position to 50% of line.
        return new LineAnchor(getFigure());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef
     * .Request)
     */
    @Override
    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        if (request instanceof DropRequest) {
            // Find out portion of line
            Point location = ((DropRequest) request).getLocation().getCopy();

            return new LineAnchor(getFigure(), location);

        }

        // If all else fails return a box-standard line anchor
        // (which sets it's position to 50% of line.
        return new LineAnchor(getFigure());
    }

    /**
     * Returns true if the connection should be made invisible. Primarily this
     * is for when connections need to be hidden when their source/target
     * objects are in closed pools/lanes.
     * 
     * @return
     */
    protected abstract boolean shouldBeVisible();

    /**
     * Return true if the flow is to an object that is within a closed pool /
     * lane.
     * 
     * @return
     */
    public boolean isObjectInClosedParent(EditPart ep) {
        if (ep != null) {
            EditPart parent = ep.getParent();

            while (parent != null) {
                if (parent instanceof LaneEditPart) {
                    if (((LaneEditPart) parent).isClosed()) {
                        return true;
                    }
                } else if (parent instanceof PoolEditPart) {
                    if (((PoolEditPart) parent).isClosed()) {
                        return true;
                    }
                }
                parent = parent.getParent();
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editparts.AbstractGraphicalEditPart#addChildVisual(org
     * .eclipse.gef.EditPart, int)
     */
    @Override
    protected void addChildVisual(EditPart childEditPart, int index) {
        super.addChildVisual(childEditPart, index);

        if (childEditPart instanceof ConnectionLabelEditPart) {
            connLabelEditPart = (ConnectionLabelEditPart) childEditPart;
        }
    }

    @Override
    public void performRequest(Request request) {
        if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT || request
                .getType() == RequestConstants.REQ_OPEN)
                && getEditPolicy(EditPolicy.DIRECT_EDIT_ROLE) != null) {
            // Delegate the job to the connection label editpart.
            if (connLabelEditPart != null) {
                connLabelEditPart.performDirectEdit();
            }
        } else {
            super.performRequest(request);
        }
    }

    /**
     * There are performance issues with creation/update of property sheets -
     * this means that when user performs click-drag of certain objects (like
     * end event for instance) the 'drag doesn't start until the property sheets
     * are loaded because they lock-out the UI thread. We could keep trying to
     * make property sheet loads/layout faster but it will always be an
     * underlying problem.
     * <p>
     * Therefore we will change the process editor so that it does not fire
     * selection changed events UNTIL a Mouse Up occurs (ratner than mouse
     * down).
     * <p>
     * GEF does not make this particularly easy and we have to fight quite hard
     * to get it top do so...
     * <li>{@link BpmnScrollingGraphicalViewer} allows us to switch to a mode
     * whereby set selection on IT without firing a sel change events to the
     * rest of the world.</li>
     * <li>So we use this to prevent sel change events causing prop sheet load
     * on mouse down.</li>
     * <li>Then on mouse up we fire the actual sel change.
     * <li>To do all this we use our own subclass of drag tracker.
     * 
     */
    @Override
    public DragTracker getDragTracker(Request request) {
        return new BpmnScrollingGraphicalViewer.SelectOnMouseUpDragTracker(this);
    }

    /**
     * Override fireSelectionChanged so that we take into account the fact that
     * firing selection events has been cancelled in the viewer (so that post
     * selection things can be backed-off until mouse up).
     * <p>
     * But NEVER ignore deselection events.
     */
    @Override
    public void fireSelectionChanged() {
        // Don't ignore deselections
        if (this.getSelected() != EditPart.SELECTED_NONE) {

            // Don't fire sel change if viewer has been told to not fire them
            if (getViewer() instanceof BpmnScrollingGraphicalViewer) {
                if (!((BpmnScrollingGraphicalViewer) getViewer())
                        .isFireSelChangeEnabled()) {
                    return;
                }
            }
        }

        super.fireSelectionChanged();
    }

    /**
     * Force Fire selection changed
     */
    public void forceFireSelectionChanged() {
        super.fireSelectionChanged();
    }

    public ProcessWidget getProcessWidget() {
        return (ProcessWidget) getViewer()
                .getProperty(ProcessWidgetConstants.PROP_WIDGET);
    }

    public ProcessWidgetType getProcessWidgetType() {
        ProcessWidget widget = getProcessWidget();
        if (widget != null) {
            return widget.getProcessWidgetType();
        }
        return ProcessWidgetType.BPMN_PROCESS;
    }

    public DiagramViewType getDiagramViewType() {
        ProcessWidget widget = getProcessWidget();
        if (widget != null) {
            return widget.getDiagramViewType();
        }
        return DiagramViewType.PROCESS;
    }

    /**
     * Allow force of refresh visuals (be very careful how you use this as it is
     * subverting the normal model-listener method of updating visuals).
     */
    public void forceRefreshVisuals() {
        refreshVisuals();
    }

    /**
     * Sid ACE-2879
     * 
     * @return <code>true</code> if the host process widget is in read-only mode.
     */
    public boolean isReadOnly() {
        ProcessWidget widget = getProcessWidget();
        if (widget != null) {
            return widget.isReadOnly();
        }
        return false;
    }

    /**
     * Sid ACE-2879 Override understandsRequest() to return <code>false</code> if the editor is in read-only mode. This
     * is just a catch in case there are edit requests that are handled directly by edit parts as opposed to the edit
     * policies associated with them.
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#understandsRequest(org.eclipse.gef.Request)
     *
     * @param req
     * @return
     */
    @Override
    public boolean understandsRequest(Request req) {
        if (isReadOnly()) {
            return false;
        }
        return super.understandsRequest(req);
    }

    /**
     * Sid ACE-2879 override installEditPolicy() so that when the editor is in read-only mode then only certain edit
     * policies (such as diagram annotations) will be installed and other edit policies (that allow editing) will be
     * ignored.
     * 
     * When in read-only mode a non-resizable selection policy is added (selection policy is normally a child policy of
     * the {@link FlowContainerXYLayoutEditPolicy} which is ignored because it is where add/delete/move commands are
     * handled
     * 
     * @see org.eclipse.gef.editparts.AbstractEditPart#installEditPolicy(java.lang.Object, org.eclipse.gef.EditPolicy)
     *
     * @param key
     * @param editPolicy
     */
    @Override
    public void installEditPolicy(Object key, EditPolicy editPolicy) {
        /*
         * In read-only mode, ignore policies that are not just for non-editing policies like diagram object annotations
         * etc.
         */
        if (!isReadOnly() || editPolicy instanceof ExternalAnnotationEditPolicy
                || editPolicy instanceof ConnectionsAnimatorEditPolicy) {
            super.installEditPolicy(key, editPolicy);

        } else if (isReadOnly()) {
            /*
             * When in read-only mode a non-resizable selection policy is added (selection policy is normally a child
             * policy of the {@link FlowContainerXYLayoutEditPolicy} which is ignored because it is where
             * add/delete/move commands are handled
             */
            if (this.getEditPolicy("read.only.selection.policy") == null) { //$NON-NLS-1$
                super.installEditPolicy("read.only.selection.policy", new NonResizableEditPolicy()); //$NON-NLS-1$
            }
        }
    }
}
