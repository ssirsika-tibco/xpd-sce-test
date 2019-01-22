/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processwidget.annotations.problems;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.BaseConnectionAdapter;
import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.GatewayAdapter;
import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.adapters.MarkerAndModelObject;
import com.tibco.xpd.processwidget.adapters.NoteAdapter;
import com.tibco.xpd.processwidget.adapters.PoolAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter.ProblemMarkerListChangedListener;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.annotations.AbstractImageAnnotationFigure;
import com.tibco.xpd.processwidget.annotations.AnnotationFactoryEx;
import com.tibco.xpd.processwidget.annotations.AnnotationListener;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;

/**
 * Process diagram annotation factory for problem marker icons on process
 * objects.
 * 
 * @author aallway (extensive rework)
 * @since 15 Jun 2012
 */
public class ProcessDiagramProblemsAnnotationFactory extends
        AnnotationFactoryEx {

    private ProcessDiagramAdapter processDiagramAdapter;

    private ProblemMarkersChangedListener problemMarkersListener;

    private Set<AnnotationListener> annotationCreators =
            new HashSet<AnnotationListener>();

    /**
     * Create annotation factory that listens to the given process diagram
     * adapter for changes to the problem marker list and refreshes the problem
     * marker icons annotations accordingly.
     * 
     * @param processDiagramAdapter
     * @param editPartViewer
     */
    public ProcessDiagramProblemsAnnotationFactory(
            ProcessDiagramAdapter processDiagramAdapter) {

        this.processDiagramAdapter = processDiagramAdapter;

        //
        // When we are activated add a listener to "validation performed events"
        // When we get one of these we will tell the problems annotation layer
        // to refresh the problem marker annotation figures.
        //
        // Basically the process diagram adapter listens to the validation
        // engine (for validation performed events). Then (if necessary) it will
        // recahce the problem marker list and fire proceMarkerListChanged
        // events to it's listeners (i.e. us).
        //
        // Therefore when we get the notification that a validation has been
        // performed we can refresh the problem marker annotation layer
        // guaranteeing that the latest problem marker info is available.
        //

        problemMarkersListener = new ProblemMarkersChangedListener();

        processDiagramAdapter
                .addProblemMakerListChangedListener(problemMarkersListener);

    }

    @Override
    public IFigure createFigureEx(AnnotationListener listener,
            EditPart hostEditPart) {
        IFigure figure = null;
        Image img = null;

        if (hostEditPart instanceof ModelAdapterEditPart
                && hostEditPart instanceof AbstractGraphicalEditPart) {

            //            System.out.println(this.getClass().getSimpleName()+".createFigureEx() for: "+((AbstractGraphicalEditPart)hostEditPart).getFigure()); //$NON-NLS-1$

            BaseProcessAdapter adapter =
                    ((ModelAdapterEditPart) hostEditPart).getModelAdapter();

            int severity = adapter.getProblemMarkerSeverity();

            switch (severity) {
            case IMarker.SEVERITY_ERROR:
                img =
                        ProcessWidgetPlugin
                                .getDefault()
                                .getImageRegistry()
                                .get(ProcessWidgetConstants.IMG_ERRORLARGE_ICON);
                break;
            case IMarker.SEVERITY_WARNING:
                img =
                        ProcessWidgetPlugin.getDefault().getImageRegistry()
                                .get(ProcessWidgetConstants.IMG_WARNING_ICON);
                break;

            // case IMarker.SEVERITY_INFO:
            // img =
            // ProcessWidgetPlugin.getDefault().getImageRegistry()
            // .get(ProcessWidgetConstants.IMG_INFO_ICON);
            // break;
            }

            if (img != null) {
                figure =
                        getModelFigure((AbstractGraphicalEditPart) hostEditPart,
                                img,
                                adapter);
            }
        }

        return figure;
    }

    private IFigure getModelFigure(AbstractGraphicalEditPart graphicalEditPart,
            Image img, BaseProcessAdapter adapter) {
        IFigure figure = null;
        IFigure hostFigure = graphicalEditPart.getFigure();

        List<IMarker> markerList = adapter.getProblemMarkerList(true);
        if (markerList == null) {
            markerList = Collections.EMPTY_LIST;
        }
        if (!markerList.isEmpty()) {
            if (adapter instanceof EventAdapter) {
                figure =
                        new EventProblemsAnnotationFigure(hostFigure, img,
                                adapter);

            } else if (adapter instanceof GatewayAdapter) {
                figure =
                        new GatewayProblemsAnnotationFigure(hostFigure, img,
                                adapter);

            } else if (adapter instanceof BaseConnectionAdapter) {
                figure =
                        new PolyLineProblemsAnnotationFigure(hostFigure, img,
                                adapter);

            } else if (adapter instanceof TaskAdapter) {
                figure =
                        new DefaultProblemsAnnotationFigure(hostFigure, img,
                                adapter);

            } else if (adapter instanceof PoolAdapter
                    || adapter instanceof LaneAdapter) {
                figure =
                        new TopLeftProblemsAnnotationsFigure(hostFigure, img,
                                adapter);
            } else if (adapter instanceof NoteAdapter) {
                figure =
                        new TextAnnotProblemsAnnotationsFigure(hostFigure, img,
                                adapter);
            } else {
                figure =
                        new DefaultProblemsAnnotationFigure(hostFigure, img,
                                adapter);
            }
        }
        return figure;
    }

    @Override
    public void registerListener(AnnotationListener annotationCreator) {
        annotationCreators.add(annotationCreator);
        annotationCreator.createAnnotations();
    }

    @Override
    public void unregisterListener(AnnotationListener annotatonCreator) {
        annotatonCreator.removeAnnotations();
        annotationCreators.remove(annotatonCreator);
    }

    @Override
    public void disposeFactory() {
        // On clean up remove the validation performed listener.
        if (problemMarkersListener != null) {
            processDiagramAdapter
                    .removeProblemMakerListChangedListener(problemMarkersListener);
            problemMarkersListener = null;
        }

    }

    @Override
    public void removingAnnotationFigure(AnnotationListener listener,
            IFigure hostFigure, IFigure annotationFigure) {
        //        System.out.println(this.getClass().getSimpleName()+".removingAnnotationFigure() from: "+hostFigure); //$NON-NLS-1$

        if (annotationFigure instanceof AbstractProblemsAnnotationFigure) {
            ((AbstractImageAnnotationFigure) annotationFigure)
                    .aboutToRemove(hostFigure);
        }

        return;
    }

    /**
     * Listens for changes to the problem marker list for the process being
     * edited.
     * 
     * @author aallway
     * @since 15 Jun 2012
     */
    private class ProblemMarkersChangedListener implements
            ProblemMarkerListChangedListener {

        /**
         * @see com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter.ProblemMarkerListChangedListener#problemMarkerListChanged(java.util.Collection,
         *      java.util.Collection)
         * 
         * @param oldList
         * @param newList
         */
        @Override
        public void problemMarkerListChanged(
                Collection<MarkerAndModelObject> oldList,
                Collection<MarkerAndModelObject> newList) {

            /*
             * Go thru the set of 'annotation creators' telling them to remove
             * and then recreate the annotations (this will call back into our
             * createFigureEx() method which will look at the actual problem
             * markers.
             */
            for (AnnotationListener creator : annotationCreators) {
                creator.removeAnnotations();
                creator.createAnnotations();
            }
        }

    }

}
