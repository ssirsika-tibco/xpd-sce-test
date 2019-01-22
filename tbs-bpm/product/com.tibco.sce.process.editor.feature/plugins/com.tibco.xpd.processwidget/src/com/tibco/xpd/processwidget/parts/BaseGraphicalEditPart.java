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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.help.HelpSystem;
import org.eclipse.help.IContext;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter.ProcessWidgetListener;
import com.tibco.xpd.processwidget.adapters.FlowNodeAdapter;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.annotations.AnnotationFactory;
import com.tibco.xpd.processwidget.figures.ProcessFigure.DiagramViewType;
import com.tibco.xpd.processwidget.figures.TaskFigure;
import com.tibco.xpd.processwidget.figures.anchors.FixedOrientationAnchor;
import com.tibco.xpd.processwidget.figures.anchors.FromClosedAncestorAnchor;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.policies.ExternalAnnotationEditPolicy;
import com.tibco.xpd.processwidget.tools.ConfigurableDirectEditManager;
import com.tibco.xpd.processwidget.viewer.BpmnScrollingGraphicalViewer;
import com.tibco.xpd.processwidget.viewer.WidgetTextActionHandlers;

/**
 * Graphical edit part that install iteself as a listener to process adapter
 * 
 * @author wzurek
 */
public abstract class BaseGraphicalEditPart extends AbstractGraphicalEditPart
        implements ProcessWidgetListener, ModelAdapterEditPart, NodeEditPart {

    /**
     * 
     */
    public static final String XPD_CHILD_INVALIDATED_REQUEST =
            "XPD Child Invalidated Request"; //$NON-NLS-1$

    public static final class BoundsBasedLocator implements CellEditorLocator {
        private final IFigure figure;

        public BoundsBasedLocator(IFigure figure) {
            this.figure = figure;
        }

        @Override
        public void relocate(CellEditor celleditor) {
            Rectangle b = figure.getBounds().getCopy();
            figure.translateToAbsolute(b);

            Text t = (Text) celleditor.getControl();
            Point pref = t.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            pref.x = Math.max(pref.x, b.width);
            t.setBounds(b.x, b.y, pref.x, pref.y);
        }
    }

    private final AdapterFactory adapterFactory;

    private BaseProcessAdapter adapter;

    private DirectEditManager manager;

    private String helpContextId;

    /*
     * @see
     * com.tibco.xpd.processwidget.parts.ModelAdapterEditPart#getModelAdapter()
     */
    @Override
    public BaseProcessAdapter getModelAdapter() {
        if (adapter == null) {
            adapter =
                    (BaseProcessAdapter) adapterFactory.adapt(getModel(),
                            ProcessWidgetConstants.ADAPTER_TYPE);
            if (adapter == null) {
                throw new IllegalStateException();
            }
        }
        return adapter;
    }

    public BaseGraphicalEditPart(AdapterFactory adapterFactory) {
        this.adapterFactory = adapterFactory;
    }

    @Override
    protected void createEditPolicies() {
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

        createClickOrDragGadgetPolicies();
    }

    /**
     * Create the click-drag gadget edit policies.
     */
    protected void createClickOrDragGadgetPolicies() {
        return;
    }

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

    @Override
    public void deactivate() {
        getModelAdapter().removeListener(this);
        super.deactivate();
    }

    /*
     * @see
     * com.tibco.xpd.processwidget.parts.ModelAdapterEditPart#getAdapterFactory
     * ()
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

    /**
     * Translate given translatable from diagram to model cordinates.
     * 
     * @param translatable
     * @return
     */
    public Translatable translateToModel(Translatable translatable) {
        return translatable;
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
            if (getParent() != null) {
                return getViewer()
                        .getProperty(ProcessWidgetConstants.PROP_LABEL_PROVIDER);
            }
        }
        return super.getAdapter(key);
    }

    public void setHelpContextId(String helpContextId) {
        this.helpContextId = helpContextId;
    }

    public String getHelpContextId() {
        return helpContextId;
    }

    @Override
    public void performRequest(Request request) {
        if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT || request
                .getType() == RequestConstants.REQ_OPEN)
                && getEditPolicy(EditPolicy.DIRECT_EDIT_ROLE) != null) {
            performDirectEdit();
        } else {
            super.performRequest(request);
        }
    }

    protected CellEditorLocator getDirectEditEditorLocator() {
        return new BoundsBasedLocator(getFigure());
    }

    /**
     * Return the required Text control SWT.xxx styles for the direct edit text
     * control.
     * 
     * @return
     */
    protected int getDirectEditStyle() {
        return SWT.NONE;
    }

    protected void performDirectEdit() {
        if (manager == null) {
            CellEditorLocator locator = getDirectEditEditorLocator();

            manager =
                    new ConfigurableDirectEditManager(this, locator,
                            getDirectEditStyle()) {

                        @Override
                        protected void initCellEditor() {
                            String name = getInitialDirectEditText();
                            if (name == null) {
                                name = ""; //$NON-NLS-1$
                            }
                            getCellEditor().setValue(name);
                            Text textControl =
                                    (Text) getCellEditor().getControl();
                            textControl.selectAll();

                            EditPartViewer viewer = getViewer();
                            WidgetTextActionHandlers handlers =
                                    ((WidgetTextActionHandlers) viewer
                                            .getProperty(ProcessWidgetConstants.PROP_TEXT_HANDLERS));
                            handlers.addText(textControl);
                        }

                        /**
                         * @see org.eclipse.gef.tools.DirectEditManager#bringDown()
                         */
                        @Override
                        protected void bringDown() {
                            super.bringDown();
                            refreshVisuals();
                        }
                    };
        }
        manager.show();

    }

    /**
     * return initial falue for direct edit
     */
    protected String getInitialDirectEditText() {
        return ((NamedElementAdapter) getModelAdapter()).getName();
    }

    /**
     * Return the parent pool or embedded sub-proc task.
     * 
     * @return task edit part if in sub-proc, pool if not else null
     */
    public BaseGraphicalEditPart getParentPoolOrTask() {
        EditPart parent = null;

        parent = getParent();

        while (parent != null && !(parent instanceof TaskEditPart)
                && !(parent instanceof PoolEditPart)) {
            parent = parent.getParent();
        }

        return ((BaseGraphicalEditPart) parent);
    }

    /**
     * Return the parent lane or embedded sub-proc task.
     * 
     * @return task edit part if in sub-proc, lane if not else null
     */
    public BaseGraphicalEditPart getParentLaneOrTask() {
        EditPart parent = null;

        parent = getParent();

        while (parent != null && !(parent instanceof TaskEditPart)
                && !(parent instanceof LaneEditPart)) {
            parent = parent.getParent();
        }

        return ((BaseGraphicalEditPart) parent);
    }

    /**
     * Return the parent pool
     */
    public PoolEditPart getParentPool() {
        EditPart parent = null;

        parent = getParent();

        while (parent != null && !(parent instanceof PoolEditPart)) {
            parent = parent.getParent();
        }

        return ((PoolEditPart) parent);
    }

    /**
     * Return the parent lane
     */
    public LaneEditPart getParentLane() {
        EditPart parent = null;

        parent = getParent();

        while (parent != null && !(parent instanceof LaneEditPart)) {
            parent = parent.getParent();
        }

        return ((LaneEditPart) parent);
    }

    /**
     * Return the parent process edit part
     */
    public ProcessEditPart getParentProcess() {
        EditPart parent = null;

        parent = getParent();

        while (parent != null && !(parent instanceof ProcessEditPart)) {
            parent = parent.getParent();
        }

        return ((ProcessEditPart) parent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getDragTracker(org
     * .eclipse.gef.Request)
     */
    /*
     * public DragTracker getDragTracker(Request req) { return new
     * ValidateConnectionsDragEditPartsTracker(this); }
     */

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.editparts.AbstractEditPart#getTargetEditPart(org.eclipse
     * .gef.Request)
     */
    @Override
    public EditPart getTargetEditPart(Request request) {

        Boolean childInvalidated =
                (Boolean) request.getExtendedData()
                        .get(XPD_CHILD_INVALIDATED_REQUEST);
        if (childInvalidated != null && childInvalidated.booleanValue() == true) {
            // Another editpart has already chcked the validity of move/add so
            // we don't wantto re-check for ourselves.
            //
            // i.e. child has said 'nominally this request would be allowed but
            // because of some side effect then it is not" therefore we DON'T
            // want the parent edit part to say " but it's ok for me!
            return null;
        }

        if (REQ_RECONNECT_SOURCE.equals(request.getType())
                || REQ_RECONNECT_TARGET.equals(request.getType())) {
            ReconnectRequest creq = (ReconnectRequest) request;

            BaseConnectionEditPart connectionEP =
                    (BaseConnectionEditPart) creq.getConnectionEditPart();

            // Disallow retarget of connection start/end if teh object that
            // is conencted to is hidden because it's parent lane / pool is
            // collapsed.

            String errStr = null;

            if (REQ_RECONNECT_TARGET.equals(request.getType())) {

                if (connectionEP.isObjectInClosedParent(connectionEP
                        .getTarget())) {
                    errStr =
                            Messages.BaseGraphicalEditPart_ConnectionToHiddenObject_message;
                }
            } else if (REQ_RECONNECT_SOURCE.equals(request.getType())) {
                if (connectionEP.isObjectInClosedParent(connectionEP
                        .getSource())) {
                    errStr =
                            Messages.BaseGraphicalEditPart_ConnectionFromHiddenObject_message;
                }
            }

            // If not valid, set up an Error Tool Tip.
            WidgetRootEditPart root = (WidgetRootEditPart) getRoot();

            if (errStr != null) {
                root.setErrorTipHelperText(connectionEP.getFigure(), errStr);

                // And say that we're not a valid target for this request.
                return null;
            } else {
                root.clearErrorTipHelper();
                return super.getTargetEditPart(request);
            }
        }

        else if (REQ_ADD.equals(request.getType())
                && !(this instanceof ProcessEditPart)) {
            // Validate that none of the objects being moved have sequence flows
            // that are connected to objects not being moved.
            ChangeBoundsRequest req = (ChangeBoundsRequest) request;

            // Cache a list of all id's of objects we're moving.
            Set objs = new HashSet();

            for (Iterator iter = req.getEditParts().iterator(); iter.hasNext();) {
                EditPart ep = (EditPart) iter.next();

                if (ep instanceof BaseFlowNodeEditPart) {
                    BaseFlowNodeEditPart node = (BaseFlowNodeEditPart) ep;

                    objs.add(((NamedElementAdapter) node.getModelAdapter())
                            .getId());
                }
            }

            // Go thru the sequence flows of each object checking that
            // the other end of sequence flow is also being moved.
            // if not then we must check seuqnce flow validity.

            String badMove = null;

            for (Iterator iter = req.getEditParts().iterator(); iter.hasNext();) {
                EditPart ep = (EditPart) iter.next();

                if (ep instanceof BaseFlowNodeEditPart) {
                    BaseFlowNodeEditPart node = (BaseFlowNodeEditPart) ep;

                    boolean isAttachEventToBorder = false;

                    if (node instanceof EventEditPart
                            && this instanceof TaskEditPart) {
                        // If we are going to attach this event to the border of
                        // a emb-subproc task then don't complain that seq flow
                        // will cross boundary because it won't.
                        // (Only bother checking if it's not being moved with
                        // it's current attached-to-task).
                        TaskEditPart borderTask =
                                ((EventEditPart) node)
                                        .getBorderAttachmentTask();
                        if (borderTask == null
                                || !req.getEditParts().contains(borderTask)) {

                            // Check if we're moving onto border of task.
                            Rectangle rc =
                                    node.getFigure().getBounds().getCopy();
                            node.getFigure().translateToAbsolute(rc);
                            rc = req.getTransformedRectangle(rc);
                            this.getFigure().getParent()
                                    .translateToRelative(rc);

                            TaskFigure taskFigure =
                                    (TaskFigure) this.getFigure();
                            if (taskFigure.getSelectionBounds().intersects(rc)
                                    && !taskFigure.getHandleBounds()
                                            .contains(rc)) {
                                isAttachEventToBorder = true;
                            }
                        }

                    }

                    // Get sequence flows we are source of, check target is in
                    // copy list.
                    List srcFlows = node.getSourceConnections();

                    for (Iterator f = srcFlows.iterator(); f.hasNext();) {
                        BaseConnectionEditPart conn =
                                (BaseConnectionEditPart) f.next();

                        if (conn instanceof SequenceFlowEditPart) {
                            BaseGraphicalEditPart tgt =
                                    (BaseGraphicalEditPart) conn.getTarget();

                            if (tgt instanceof BaseFlowNodeEditPart) {
                                FlowNodeAdapter adp =
                                        ((BaseFlowNodeEditPart) tgt)
                                                .getFlowNodeAdapter();

                                // Check that target is in copy. if not
                                // then we need to validate it.
                                if (!objs.contains(adp.getId())) {
                                    // Other end of transition is not also being
                                    // moved validate what the sequence flow
                                    // will be.
                                    if (!isAttachEventToBorder
                                            && this instanceof TaskEditPart
                                            && ((TaskEditPart) this)
                                                    .isEmbeddedSubProc()
                                            && tgt.getParent() != this) {
                                        // If this is an embedded sub-proc
                                        // then it is not valid because seq flow
                                        // will cross boundary (unless the we're
                                        // moving within same parent
                                        // sub-proc).
                                        badMove =
                                                Messages.BaseGraphicalEditPart_BadMoveAcrossBoundary_message;
                                        break;
                                    }

                                    else if (this instanceof LaneEditPart
                                            && tgt.getParent() instanceof TaskEditPart) {
                                        // same this for trying to leave
                                        // something behind in sub-proc
                                        badMove =
                                                Messages.BaseGraphicalEditPart_BadMoveAcrossBoundary_message;
                                        break;
                                    } else if (this.getParentPool() != tgt
                                            .getParentPool()) {
                                        // Cannot allow sequence flow to cross
                                        // pool boundary.
                                        badMove =
                                                Messages.BaseGraphicalEditPart_CannotConnectDiffPools_message;
                                        break;
                                    }
                                }
                            }

                        } else if (conn instanceof MessageFlowEditPart) {
                            BaseGraphicalEditPart tgt =
                                    (BaseGraphicalEditPart) conn.getTarget();

                            if (tgt instanceof BaseFlowNodeEditPart) {
                                FlowNodeAdapter adp =
                                        ((BaseFlowNodeEditPart) tgt)
                                                .getFlowNodeAdapter();

                                // Check that target is in copy. if not
                                // then have to validate it.
                                if (!objs.contains(adp.getId())) {
                                    // Other end of transition is not also being
                                    // moved validate what the sequence flow
                                    // will be.

                                    // Message flows HAVE to cross pool
                                    // boundaries.
                                    if (this.getParentPool() == tgt
                                            .getParentPool()) {
                                        badMove =
                                                Messages.BaseGraphicalEditPart_CannotConnectInSamePool_message;
                                        break;
                                    }
                                }
                            }
                        }

                    }

                    // Get sequence flows we are target of, check target is in
                    // copy list.
                    if (badMove == null) {
                        List tgtFlows = node.getTargetConnections();

                        for (Iterator f = tgtFlows.iterator(); f.hasNext();) {
                            BaseConnectionEditPart conn =
                                    (BaseConnectionEditPart) f.next();

                            if (conn instanceof SequenceFlowEditPart) {
                                BaseGraphicalEditPart src =
                                        (BaseGraphicalEditPart) conn
                                                .getSource();

                                if (src instanceof BaseFlowNodeEditPart) {
                                    FlowNodeAdapter adp =
                                            ((BaseFlowNodeEditPart) src)
                                                    .getFlowNodeAdapter();

                                    // Check that target is in copy. if not then
                                    // have to validte it.
                                    if (!objs.contains(adp.getId())) {
                                        // Other end of transition is not also
                                        // being moved then validate what the
                                        // sequence flow will be.

                                        if (this instanceof TaskEditPart
                                                && ((TaskEditPart) this)
                                                        .isEmbeddedSubProc()
                                                && src.getParent() != this) {
                                            // If this is an embedded sub-proc
                                            // then it is not valid because seq
                                            // flow
                                            // will cross boundary (unless the
                                            // we're
                                            // moving within same parent
                                            // sub-proc).
                                            badMove =
                                                    Messages.BaseGraphicalEditPart_BadMoveAcrossBoundary_message;
                                            break;
                                        } else if (this instanceof LaneEditPart
                                                && src.getParent() instanceof TaskEditPart) {
                                            // same this for trying to leave
                                            // something behind in sub-proc
                                            badMove =
                                                    Messages.BaseGraphicalEditPart_BadMoveAcrossBoundary_message;
                                            break;
                                        } else if (this.getParentPool() != src
                                                .getParentPool()) {
                                            // Cannot allow sequence flow to
                                            // cross pool boundary.
                                            badMove =
                                                    Messages.BaseGraphicalEditPart_CannotConnectDiffPools_message;
                                            break;
                                        }
                                    }
                                }
                            } else if (conn instanceof MessageFlowEditPart) {
                                BaseGraphicalEditPart src =
                                        (BaseGraphicalEditPart) conn
                                                .getSource();

                                if (src instanceof BaseFlowNodeEditPart) {
                                    FlowNodeAdapter adp =
                                            ((BaseFlowNodeEditPart) src)
                                                    .getFlowNodeAdapter();

                                    // Check that target is in copy. if not
                                    // then have to validate it.
                                    if (!objs.contains(adp.getId())) {
                                        // Other end of transition is not also
                                        // being
                                        // moved validate what the sequence flow
                                        // will be.

                                        // Message flows HAVE to cross pool
                                        // boundaries.
                                        if (this.getParentPool() == src
                                                .getParentPool()) {
                                            badMove =
                                                    Messages.BaseGraphicalEditPart_CannotConnectInSamePool_message;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (badMove != null) {
                        ((WidgetRootEditPart) getRoot())
                                .setErrorTipHelperText(getFigure(), badMove);

                        // Set flag on request to tell our parent (task or lane)
                        // that we've already
                        // said 'no can do' so don't return yourself as valid
                        // target edit part.
                        req.getExtendedData()
                                .put(XPD_CHILD_INVALIDATED_REQUEST,
                                        new Boolean(true));

                        return null;
                    } else {
                        ((WidgetRootEditPart) getRoot()).clearErrorTipHelper();
                    }

                }
            }

        }

        return super.getTargetEditPart(request);
    }

    /**
     * We standardise redirection of anchor from object to closed parent
     * lane/pool when necessary.
     * 
     * Caller should override getXpdSourceConnectionAnchor to provide their
     * usual anchor for given connection.
     * 
     * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    public final ConnectionAnchor getSourceConnectionAnchor(
            ConnectionEditPart connection) {
        ConnectionAnchor anchor = null;

        anchor = getXpdSourceConnectionAnchor(connection);

        if (EditPartUtil.isInClosedParent(this)) {
            // Check if any closeable ancestor is closed in which case we need
            // to re-direct to closest visible ancestor
            anchor =
                    new FromClosedAncestorAnchor(getFigure(), anchor,
                            PositionConstants.VERTICAL);

        } else if (!(anchor instanceof LineAnchor)
                && !(connection instanceof AssociationEditPart)) {
            // If other end of connection is in a closed pool force anchor
            // into vertical direction. (Delegated to original required
            // anchor.
            if (connection.getTarget() instanceof BaseGraphicalEditPart) {
                PoolEditPart targetPool =
                        ((BaseGraphicalEditPart) connection.getTarget())
                                .getParentPool();
                LaneEditPart targetLane =
                        ((BaseGraphicalEditPart) connection.getTarget())
                                .getParentLane();

                if ((targetPool != null && targetPool.isClosed())
                        || (targetLane != null && targetLane.isClosed())) {

                    anchor =
                            new FixedOrientationAnchor(getFigure(), anchor,
                                    PositionConstants.VERTICAL);
                }
            }
        }

        return anchor;
    }

    /**
     * Provide source connection anchor for the given connection. Override this
     * instead of getSourceConnectionAnchor
     * 
     * @param connection
     * @return
     */
    protected ConnectionAnchor getXpdSourceConnectionAnchor(
            ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
    }

    /**
     * We standardise redirection of anchor from object to closed parent
     * lane/pool when necessary.
     * 
     * Caller should override getXpdTargetConnectionAnchor to provide their
     * usual anchor for given connection.
     * 
     * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    public final ConnectionAnchor getTargetConnectionAnchor(
            ConnectionEditPart connection) {
        ConnectionAnchor anchor = null;

        anchor = getXpdTargetConnectionAnchor(connection);

        if (EditPartUtil.isInClosedParent(this)) {
            // Check if any closeable ancestor is closed in which case we need
            // to re-direct to closest visible ancestor
            anchor =
                    new FromClosedAncestorAnchor(getFigure(), anchor,
                            PositionConstants.VERTICAL);

        } else if (!(anchor instanceof LineAnchor)
                && !(connection instanceof AssociationEditPart)) {
            // If other end of connection is in a closed pool force anchor
            // into vertical direction. (Delegated to original required
            // anchor.
            if (connection.getSource() instanceof BaseGraphicalEditPart) {
                PoolEditPart sourcePool =
                        ((BaseGraphicalEditPart) connection.getSource())
                                .getParentPool();
                LaneEditPart sourceLane =
                        ((BaseGraphicalEditPart) connection.getSource())
                                .getParentLane();

                if ((sourcePool != null && sourcePool.isClosed())
                        || (sourceLane != null && sourceLane.isClosed())) {

                    anchor =
                            new FixedOrientationAnchor(getFigure(), anchor,
                                    PositionConstants.VERTICAL);
                }
            }
        }
        return anchor;
    }

    /**
     * Provide target connection anchor for the given connection. Override this
     * instead of getTargetConnectionAnchor
     * 
     * @param connection
     * @return
     */
    protected ConnectionAnchor getXpdTargetConnectionAnchor(
            ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
    }

    /**
     * We standardise redirection of anchor from object to closed parent
     * lane/pool when necessary.
     * 
     * Caller should override getXpdSourceConnectionAnchor to provide their
     * usual anchor for given connection request.
     * 
     * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
     */
    @Override
    public final ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return getXpdSourceConnectionAnchor(request);
    }

    /**
     * Provide source connection anchor for the given connection. Override this
     * instead of getSourceConnectionAnchor
     * 
     * @param connection
     * @return
     */
    protected ConnectionAnchor getXpdSourceConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
    }

    /**
     * We standardise redirection of anchor from object to closed parent
     * lane/pool when necessary.
     * 
     * Caller should override getXpdTargetConnectionAnchor to provide their
     * usual anchor for given connection.
     * 
     * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    public final ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return getXpdTargetConnectionAnchor(request);
    }

    /**
     * Provide target connection anchor for the given connection. Override this
     * instead of getTargetConnectionAnchor
     * 
     * @param connection
     * @return
     */
    protected ConnectionAnchor getXpdTargetConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
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

    /** XPD-1140: Allow tag process editor figure as readonly. */
    public boolean isReadOnly() {
        ProcessWidget widget = getProcessWidget();
        if (widget != null) {
            return widget.isReadOnly();
        }
        return false;
    }

    /**
     * Allow force of refresh visuals (be very careful how you use this as it is
     * subverting the normal model-listener method of updating visuals).
     */
    public void forceRefreshVisuals() {
        refreshVisuals();
    }
}
