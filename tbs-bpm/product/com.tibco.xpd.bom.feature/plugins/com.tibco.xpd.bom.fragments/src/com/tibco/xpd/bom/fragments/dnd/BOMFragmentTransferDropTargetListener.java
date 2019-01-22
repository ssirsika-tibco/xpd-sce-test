/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.fragments.dnd;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.xpd.bom.fragments.Activator;
import com.tibco.xpd.bom.fragments.internal.Messages;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.clipboard.BOMCopyPasteCommandFactory;
import com.tibco.xpd.fragments.dnd.FragmentTransferDropTargetListener;

/**
 * BOM's fragment transfer drop target listener.
 * 
 * @author njpatel
 * 
 */
public class BOMFragmentTransferDropTargetListener extends
        FragmentTransferDropTargetListener {

    private View targetView;

    private final TransactionalEditingDomain ed;

    private IWorkbenchPage page;

    private final DiagramEditor editor;

    private EditPart lastSelectedEditPart;

    private IFigure feedbackGhost;

    private IFigure feedbackLayer;

    private SelectionRequest selRequest;

    private double zoom = 1.0;

    private IStatus errorStatus;

    /**
     * BOM's fragment drop target listener
     * 
     * @param editor
     * @param ed
     *            the transactional editing domain
     */
    public BOMFragmentTransferDropTargetListener(DiagramEditor editor,
            TransactionalEditingDomain ed) {
        super(editor.getDiagramGraphicalViewer());
        this.editor = editor;
        this.page = editor.getSite().getPage();
        this.ed = ed;

        IDiagramGraphicalViewer graphicalViewer =
                editor.getDiagramGraphicalViewer();
        if (graphicalViewer != null
                && graphicalViewer.getRootEditPart() instanceof DiagramRootEditPart) {
            feedbackLayer =
                    ((DiagramRootEditPart) graphicalViewer.getRootEditPart())
                            .getLayer(LayerConstants.FEEDBACK_LAYER);

            ZoomManager manager =
                    (ZoomManager) editor.getAdapter(ZoomManager.class);

            if (manager != null) {
                zoom = manager.getZoom();
                manager.addZoomListener(new ZoomListener() {

                    public void zoomChanged(double zoom) {
                        BOMFragmentTransferDropTargetListener.this.zoom = zoom;
                    }
                });
            }
        }
    }

    @Override
    public void dragEnter(DropTargetEvent event) {
        super.dragEnter(event);
        errorStatus = null;

        // Activate the editor when drag enters
        if (page != null && editor != null) {
            page.activate(editor);
        }

        if (feedbackLayer != null) {
            clearFeedBack();
            try {
                feedbackGhost = createFeedbackFigure();
                if (feedbackGhost != null) {
                    feedbackLayer.add(feedbackGhost);
                }
            } catch (CoreException e) {
                errorStatus = e.getStatus();
                Activator.getDefault().getLogger().log(errorStatus);
                event.operations = DND.DROP_NONE;
            }
        }
    }

    @Override
    public void dragLeave(DropTargetEvent event) {
        // Clear any feedback
        if (lastSelectedEditPart != null) {
            lastSelectedEditPart.eraseTargetFeedback(getSelectionRequest());
            lastSelectedEditPart = null;
        }
        clearFeedBack();
        super.dragLeave(event);
    }

    private void clearFeedBack() {
        if (feedbackLayer != null && feedbackGhost != null) {
            feedbackLayer.remove(feedbackGhost);
            feedbackGhost = null;
        }
    }

    @Override
    protected void doDragOver(DropTargetEvent event, final EditPart target) {
        // Update feedback image
        updateFeedback(event, feedbackGhost);
        if (target.getModel() instanceof View) {
            targetView = (View) target.getModel();
            EObject element = ((View) target.getModel()).getElement();
            if (element instanceof org.eclipse.uml2.uml.Package) {
                // Update target edit part selection visual feedback
                if (lastSelectedEditPart != target) {
                    SelectionRequest request = getSelectionRequest();
                    request.setLocation(getDropLocation(event));
                    if (lastSelectedEditPart != null) {
                        lastSelectedEditPart.eraseTargetFeedback(request);
                    }
                    lastSelectedEditPart = target;
                    target.showTargetFeedback(request);
                }

                if ((event.operations & DND.DROP_COPY) != 0) {
                    event.detail = DND.DROP_COPY;
                }
            }
        }
    }

    @Override
    protected void doDrop(DropTargetEvent event, final String fragmentData,
            final EditPart targetEditPart) {
        clearFeedBack();

        // If dnd was cancelled then quit
        if (event.operations == DND.DROP_NONE) {
            if (errorStatus != null) {
                ErrorDialog
                        .openError(getViewer().getControl().getShell(),
                                Messages.BOMFragmentTransferDropTargetListener_failedToCreateFragment_errorDialog_title,
                                Messages.BOMFragmentTransferDropTargetListener_failedToCreateFragment_errorDialog_message,
                                errorStatus);
            }
            return;
        }

        if (fragmentData != null && targetView != null) {
            if (ed != null) {
                final Point pt = getDropLocation(event);

                // Get the location relative to the edit part being dropped into
                if (targetEditPart instanceof GraphicalEditPart) {
                    IFigure figure =
                            ((GraphicalEditPart) targetEditPart).getFigure();

                    figure.translateToRelative(pt);

                    Rectangle bounds = figure.getBounds();

                    pt.performTranslate(-bounds.x - figure.getInsets().right,
                            -bounds.y - figure.getInsets().bottom);
                }

                RecordingCommand cmd =
                        new RecordingCommand(
                                ed,
                                Messages.BOMFragmentTransferDropTargetListener_dropFragment_command_label) {

                            @Override
                            protected void doExecute() {
                                try {
                                    IMapMode mapMode = null;
                                    if (targetEditPart instanceof GraphicalEditPart) {
                                        mapMode =
                                                MapModeUtil
                                                        .getMapMode(((GraphicalEditPart) targetEditPart)
                                                                .getFigure());
                                    }
                                    BOMCopyPasteCommandFactory.getInstance()
                                            .pasteFromString(targetView,
                                                    fragmentData,
                                                    mapMode,
                                                    pt);

                                } catch (Exception e) {
                                    Activator
                                            .getDefault()
                                            .getLogger()
                                            .error(e,
                                                    Messages.BOMFragmentTransferDropTargetListener_fragmentPasteFail_error_log_shortdesc);
                                }
                            }

                        };

                ed.getCommandStack().execute(cmd);
            }
        }
    }

    /**
     * Get the edit part selection request for fragment drags.
     * 
     * @return
     */
    private SelectionRequest getSelectionRequest() {
        if (selRequest == null) {
            selRequest = new SelectionRequest();
            selRequest.setType(BOMResourcesPlugin.REQ_FRAGMENT_DRAG);
        }
        return selRequest;
    }

    /**
     * Create the feedback figure for the fragment.
     * 
     * @return feedback {@link IFigure}, <code>null</code> if no feedback data
     *         is found in the fragment.
     * @throws CoreException
     */
    private IFigure createFeedbackFigure() throws CoreException {
        List<Bounds> bounds = getFeedbackBounds(getTransferData());
        if (!bounds.isEmpty()) {
            IFigure fig = new Figure();
            Rectangle overallRect = null;
            for (Bounds bound : bounds) {
                RectangleFigure rect = new RectangleFigure();
                FigureUtilities.makeGhostShape(rect);
                Rectangle r =
                        new Rectangle(bound.getX(), bound.getY(), bound
                                .getWidth(), bound.getHeight());
                // Apply zoom settings
                r.scale(zoom);
                rect.setBounds(r);

                // Calculate overall bounds of the feedback figure
                if (overallRect != null) {
                    overallRect.union(r);
                } else {
                    overallRect = r;
                }
                fig.add(rect);
            }
            fig.setVisible(false);
            fig.setBounds(overallRect);

            return fig;
        }
        return null;
    }

    /**
     * Update the feedback figure with the location of the mouse from the event.
     * 
     * @param event
     *            drop target event
     * @param feedbackFig
     *            feedback figure
     */
    private void updateFeedback(DropTargetEvent event, IFigure feedbackFig) {
        if (feedbackFig != null && event != null) {
            Point location = getDropLocation(event);
            feedbackFig.translateToRelative(location);
            feedbackFig.setLocation(location);
            if (!feedbackFig.isVisible())
                feedbackFig.setVisible(true);
        }
    }

    /**
     * Get the feedback bounds from the fragment data.
     * 
     * @param data
     *            fragment data
     * @return feedback rectangle bounds, empty list if no feedback information
     *         is found in the data.
     * @throws CoreException
     */
    private List<Bounds> getFeedbackBounds(String data) throws CoreException {
        List<Bounds> bounds = new ArrayList<Bounds>();

        if (data != null) {
            XMIResource res = new XMIResourceImpl();
            res.setURI(URI.createURI("platform://resource/bomFragments")); //$NON-NLS-1$
            ed.getResourceSet().getResources().add(res);
            try {
                res.load(new ByteArrayInputStream(data.getBytes()), null);
                if (res.isLoaded()) {
                    EAnnotation feedbackAnnot = null;
                    EList<EObject> contents = res.getContents();
                    List<Node> nodes = new ArrayList<Node>();
                    for (EObject content : contents) {
                        if (content instanceof EAnnotation
                                && BOMCopyPasteCommandFactory.ANNOT_FEEDBACK_RECTANGLES
                                        .equals(((EAnnotation) content)
                                                .getSource())) {
                            feedbackAnnot = (EAnnotation) content;
                            break;
                        } else if (content instanceof Node) {
                            // Collect all nodes in case we have to create the
                            // feedback rectangles
                            nodes.add((Node) content);
                        }
                    }

                    /*
                     * If no feedback annotation was found (which may be the
                     * case with fragment produced prior to version 3.2 BOM)
                     * then try to create feedback rectangles here using the
                     * nodes found in the resource.
                     */
                    if (feedbackAnnot == null && !nodes.isEmpty()) {
                        feedbackAnnot =
                                BOMCopyPasteCommandFactory.getInstance()
                                        .getFeedbackAnnotation(nodes);
                    }

                    if (feedbackAnnot != null) {
                        /*
                         * Get the feedback display bounds to build the ghost
                         * figure of the fragment
                         */
                        contents = feedbackAnnot.eContents();

                        for (EObject c : contents) {
                            if (c instanceof Bounds) {
                                bounds.add((Bounds) c);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                Activator.PLUGIN_ID,
                                Messages.BOMFragmentTransferDropTargetListener_failedToLoadFragment_error_log_shortdesc,
                                e));
            } finally {
                if (res != null) {
                    // Unload the resource from the resource set
                    res.unload();
                    ed.getResourceSet().getResources().remove(res);
                    res = null;
                }
            }
        }
        return bounds;
    }

}
