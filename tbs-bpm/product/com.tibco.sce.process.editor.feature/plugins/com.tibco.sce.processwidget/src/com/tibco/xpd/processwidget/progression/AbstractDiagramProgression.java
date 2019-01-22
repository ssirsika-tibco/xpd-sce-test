/*
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.processwidget.progression;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartListener;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeListener;
import org.eclipse.gef.RootEditPart;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.processwidget.progression.model.ProgressionModel;
import com.tibco.xpd.processwidget.progression.model.ProgressionModelFactory;
import com.tibco.xpd.processwidget.progression.model.ProgressionState;

/**
 * Class for the overall control of a diagram progression setup/run
 * <p>
 * There are two main parts to progression...
 * <p>
 * - Creation of the <b><i>ability</i></b> to visualise progression thru/on a
 * diagram for a particular diagram type. Nominally this will be written by the
 * diagram editor vendor.
 * </p>
 * <p>
 * - Using the resulting 'diagram progression' to visualise a particular way of
 * progressing thru the diagram.
 * </p>
 * <p>
 * A diagram progression is effectively a state model for each diagram object
 * (represented by the {@link ProgressionModel}) tied to each diagram model
 * object and progression parts (implementations of
 * {@link AbstractProgressionPart}) which tie the {@link ProgressionModel} to
 * particular diagramn's {@link EditPart}'s and listen for
 * {@link ProgressionModel} state changes in order to provide visualisation of
 * particular state change transitions.
 * </p>
 * <p>
 * Nominally, the vendor of a particular diagram type will subclass
 * {@link AbstractDiagramProgression} and provide 4 things...
 * <p>
 * - Implementation of {@link AbstractProgressionPartFactory}. This is used to
 * create {@link AbstractProgressionPart}'s that tie a {@link ProgressionModel}
 * for a diagram model object to the {@link EditPart} for that model. When the
 * user of the diagram progression changes the {@link ProgressionState} of a
 * diagram model object, the {@link AbstractProgressionPart} is notified so that
 * it can modify the diagram edit part (or annotate it in the diagram's
 * progression figures layer).
 * </p>
 * <p>
 * - A {@link ProgressionModelFactory} (optionally) for creation of
 * {@link ProgressionModel}'s for each diagram model object. Nominally, the
 * default {@link ProgressionModelFactory} is all that is required because the
 * {@link ProgressionModel} has extensible properties.
 * </p>
 * <p>
 * - An {@link IFigure} for the progression figures layer. This is passed to
 * {@link AbstractProgressionPart}'s when {@link ProgressionModel} state changes
 * occur so that they may (if required) add extra figures to the diagram in
 * order to annotate particular states. Nominally, it is recommended that this
 * layer is NOT mouse/key sensitive (i.e. pass a layer that as been disabled
 * with {@link IFigure#setEnabled(boolean)}.
 * </p>
 * <p>
 * - An {@link IFigure} for the progression controls layer. The user of the
 * diagram progression is given the opportunity (if required) of adding extra
 * user-control type figures to the diagram. These figures are added to this
 * mouse-sensitive layer.
 * </p>
 * <p>
 * Effectively then, the vendor of the diagram 'knows' what {@link EditPart}'s
 * are used to represent given diagram model objects and hence 'knows' how best
 * to visualise particular states / state changes.
 * <p>
 * The 'user' of a particular diagram progression implementation, in simple
 * cases, needs only to know the model objects underlying particular diagram
 * elements. It can then use the
 * {@link AbstractDiagramProgression#getProgressionModelForDiagramObject(Object)}
 * to access the progression model for a given diagram model object and set the
 * {@link ProgressionState} as desired. The 'vendor' provided
 * {@link AbstractProgressionPart} will then change/annotate the diagram visuals
 * appropriately for the given state / state-change.
 * </p>
 * <p>
 * If you wish to implement {@link AbstractDiagramProgression} see
 * {@link AbstractProgressionPartFactory} and {@link AbstractProgressionPart}.
 * </p>
 * <p>
 * If you wish to use a particular implementation of
 * {@link AbstractDiagramProgression} then see the javadoc for particular
 * implementation details,
 * {@link AbstractDiagramProgression#getProgressionModelForDiagramObject(Object)}
 * and {@link ProgressionModel} for more details.
 * </p>
 * 
 * @author aallway
 * @since 3.3 (30 Oct 2009)
 */
public abstract class AbstractDiagramProgression {

    /**
     * Registry of {@link ProgressionModel} objects for diagram model objects.
     * 
     * Key'd on the diagram model object.
     */
    private Map<Object, ProgressionModel> progressionModelRegistry =
            new LinkedHashMap<Object, ProgressionModel>();

    /**
     * Registry of {@link AbstractProgressionPart} Key'd on the
     * {@link ProgressionModel} for diagrma model object.
     */
    private Map<ProgressionModel, AbstractProgressionPart> progressionPartRegistry =
            new LinkedHashMap<ProgressionModel, AbstractProgressionPart>();

    /**
     * Factory that is used to create {@link AbstractProgressionPart} (each of
     * which links a diagram object {@link EditPart} to it's progression model
     * and any other ancillary progression related items.
     */
    private AbstractProgressionPartFactory progressionPartFactory = null;

    /**
     * Factory used to create {@link ProgressionModel} objects for diagram
     * object model elements (links a diagram model object with a progression
     * state).
     */
    private ProgressionModelFactory progressionModelFactory = null;

    /**
     * The diagram root edit part.
     */
    private RootEditPart rootEditPart;

    /**
     * Diagram layer for extra non-user-interactive figures to annotate
     * progression states of diagram objects.
     */
    private IFigure progressionFiguresLayer = null;

    /**
     * Diagram layer for extra user-interactive figures to allow control over
     * progression.
     */
    private IFigure progressionControlsLayer = null;

    /**
     * Has this progression been disposed.
     */
    private boolean isDisposed = false;

    /**
     * Is this progression currently active.
     */
    private boolean isActivitated = false;

    private boolean isShown = false;

    private EditPartChangeListener editPartChangeListener =
            new EditPartChangeListener();

    /**
     * Construct a DiagramProgression with your own progression factory (see
     * class javadoc).
     * 
     * @param rootGraphicalEditPart
     *            Root edit part of diagram.
     */
    public AbstractDiagramProgression(RootEditPart rootGraphicalEditPart) {
        this.rootEditPart = rootGraphicalEditPart;
    }

    /**
     * Create and return the factory responsible for creating
     * {@link AbstractProgressionPart} implementation for the particular
     * diagram's {@link EditPart}'s.
     * 
     * @return factory
     */
    protected abstract AbstractProgressionPartFactory createProgressionPartFactory();

    /**
     * @return A factory fr creation of {@link ProgressionModel} for particular
     *         diagram model object's.
     */
    protected ProgressionModelFactory createProgressionModelFactory() {
        return new ProgressionModelFactory();
    }

    /**
     * Return the IFigure representing the layer in diagram for any extra
     * figures needed to visualise progression thru the diagram.
     * <p>
     * This layer is passed to the {@link AbstractProgressionPart} on a
     * {@link ProgressionModel} state change.
     * 
     * @return IFigure (nominally a scalable freeform layer).
     */
    protected abstract IFigure getProgressionFiguresLayer();

    /**
     * Return the IFigure representing the layer in diagram for any extra
     * user-interactive control figures needed to for progression thru the
     * diagram.
     * <p>
     * This layer is passed to the {@link AbstractProgressionPart} on a
     * {@link ProgressionModel} state change.
     * 
     * @return IFigure (nominally a scalable freeform layer).
     */
    protected abstract IFigure getProgressionControlsLayer();

    /**
     * @return the progressionModelRegistry which is a map of edit part model
     *         objects to the progression model for it.
     */
    public Map<Object, ProgressionModel> getProgressionModelRegistry() {
        return Collections.unmodifiableMap(progressionModelRegistry);
    }

    /**
     * @return the progressionPartRegistry which is a map of progression model
     *         to progression part.
     */
    public Map<ProgressionModel, AbstractProgressionPart> getProgressionPartRegistry() {
        return Collections.unmodifiableMap(progressionPartRegistry);
    }

    /**
     * Initialise this progression.
     * <p>
     * This must be called prior to {@link #activateProgression()}
     */
    public void initialiseProgressionModel() {
        /*
         * Make sure we clear up from and previous.
         */
        if (isActivitated()) {
            deactivateProgression();
        }

        if (!isDisposed) {
            dispose();
        }

        isDisposed = false;

        /*
         * Create the factories and layers we will need.
         */
        progressionPartFactory = createProgressionPartFactory();
        progressionModelFactory = createProgressionModelFactory();
        progressionFiguresLayer = getProgressionFiguresLayer();
        progressionControlsLayer = getProgressionControlsLayer();

        /*
         * Create ProgressionModel and ProgressionParts for each edit part in
         * diagram.
         */
        createProgressionModelAndParts(rootEditPart.getChildren());

        return;
    }

    /**
     * @return The root edit part of diagram.
     */
    public RootEditPart getRootEditPart() {
        return rootEditPart;
    }

    /**
     * @param diagramModelObject
     * 
     * @return Returns the progression model for the given diagram model object.
     */
    public ProgressionModel getProgressionModelForDiagramObject(
            Object diagramModelObject) {
        if (progressionPartRegistry == null) {
            throw new RuntimeException("Progression has not been initialised."); //$NON-NLS-1$
        }
        return progressionModelRegistry.get(diagramModelObject);
    }

    /**
     * Recursively create the object progression models and edit parts for the
     * 
     * @param editParts
     */
    private void createProgressionModelAndParts(List editParts) {
        if (editParts != null && !editParts.isEmpty()) {
            for (Object o : editParts) {
                if (o instanceof GraphicalEditPart) {
                    GraphicalEditPart editPart = (GraphicalEditPart) o;

                    createProgressionModelAndPart(editPart);
                }
            }
        }

        return;
    }

    /**
     * Create the progression model for an individual diagram edit part and it's
     * children.
     * 
     * @param editPart
     */
    private AbstractProgressionPart createProgressionModelAndPart(
            GraphicalEditPart editPart) {
        /*
         * Create object progression model and part for diagram object edit part
         * and model if not already registered.
         */
        if (progressionModelRegistry != null && progressionPartRegistry != null) {
            Object model = editPart.getModel();

            if (!progressionModelRegistry.containsKey(model)) {

                ProgressionModel progressionModel =
                        progressionModelFactory
                                .createObjectProgressionModel(model);

                progressionModelRegistry.put(model, progressionModel);

                AbstractProgressionPart progressionPart =
                        progressionPartFactory
                                .createObjectProgressionPart(progressionModel,
                                        editPart,
                                        progressionFiguresLayer);

                progressionPartRegistry.put(progressionModel, progressionPart);

                /*
                 * Recurs and do children and outgoing connections.
                 */
                createProgressionModelAndParts(editPart.getChildren());

                createProgressionModelAndParts(editPart.getSourceConnections());

                /*
                 * Listen for changes to edit part.
                 */
                editPart.addEditPartListener(editPartChangeListener);
                editPart.addNodeListener(editPartChangeListener);
                return progressionPart;

            } else {
                /* Already there = just return the existing progression part. */
                ProgressionModel progModel =
                        progressionModelRegistry.get(model);

                if (progModel != null) {
                    return progressionPartRegistry.get(model);
                }
            }
        }
        return null;
    }

    /**
     * Recursively remove the object progression models and edit parts for the
     * edit parts
     * 
     * @param editParts
     */
    private void removeProgressionModelAndParts(List editParts) {
        if (editParts != null && !editParts.isEmpty()) {
            for (Object o : editParts) {
                if (o instanceof GraphicalEditPart) {
                    GraphicalEditPart editPart = (GraphicalEditPart) o;

                    removeProgressionModelAndPart(editPart);
                }
            }
        }

        return;
    }

    /**
     * Create the progression model for an individual diagram edit part and it's
     * children.
     * 
     * @param editPart
     */
    private void removeProgressionModelAndPart(GraphicalEditPart editPart) {
        /* Stop listening to the edit part. */
        editPart.removeEditPartListener(editPartChangeListener);

        /*
         * Remove progression model and parts for any children.
         */
        List children = editPart.getChildren();
        if (children != null) {
            for (Object child : children) {
                if (child instanceof GraphicalEditPart) {
                    removeProgressionModelAndPart((GraphicalEditPart) child);
                }
            }
        }

        /*
         * Remove the progression model and part from registry
         */

        Object model = editPart.getModel();

        ProgressionModel progModel = progressionModelRegistry.get(model);

        if (progModel != null) {
            AbstractProgressionPart progPart =
                    progressionPartRegistry.get(progModel);

            if (progPart != null) {
                if (progPart.isEnabled()) {
                    progPart.setEnabled(false);
                    progPart.terminateProgressionVisuals();
                }
                progPart.dispose();
                progressionPartRegistry.remove(progModel);
            }

            progressionModelRegistry.remove(model);
        }

        return;
    }

    /**
     * Activate the progression.
     * <p>
     * This includes initialising the progression visuals for each progression
     * part linked to a diagram part according to the initial state in the
     * progression model.
     * <p>
     * If also includes setting the progression figure and controls layer
     * visible if available.
     */
    public void activateProgression() {
        if (progressionPartRegistry == null) {
            throw new RuntimeException(
                    "Progression cannot be activiated, it has not been initialised."); //$NON-NLS-1$
        }

        showProgressionVisuals();

        /*
         * Refresh diagram visuals.
         */
        rootEditPart.refresh();
        rootEditPart.getViewer().flush();

        isActivitated = true;

        return;
    }

    /**
     * Update the progression visuals according to the latest progression model.
     * <p>
     * The progresison is activated if not already activiated.
     */
    public void updateProgression() {
        if (!isActivitated()) {
            activateProgression();
        } else {
            showProgressionVisuals();

            /*
             * Refresh diagram visuals.
             */
            rootEditPart.refresh();
            rootEditPart.getViewer().flush();
        }
        return;
    }

    /**
     * Make the progression layer and figures visible.
     */
    private void showProgressionVisuals() {
        if (!isShown) {
            isShown = true;

            /*
             * Initialise the visuals and progression controls on each
             * progression part.
             */
            for (AbstractProgressionPart part : progressionPartRegistry
                    .values()) {
                part.initialiseProgressionVisuals();
                part.setEnabled(true);
            }

            /*
             * Make the extra progression annotation figures layer visible
             */
            if (progressionFiguresLayer != null) {
                progressionFiguresLayer.setVisible(true);
                /* Make sure it's not interactive. */
                progressionFiguresLayer.setEnabled(false);
            }

            /*
             * Make the extra progression control figures layer visible
             */
            if (progressionControlsLayer != null) {
                progressionControlsLayer.setVisible(true);
                progressionControlsLayer.setEnabled(true);
            }
        }

        return;
    }

    /**
     * Deactivate the progression.
     * <p>
     * It is permitted for the diagram progression to be re-activiated using
     * {@link #activateProgression()}
     * <p>
     * If also includes setting the progression figure and controls layer
     * invisible if available.
     */
    public void deactivateProgression() {
        if (progressionPartRegistry == null) {
            throw new RuntimeException(
                    "Progression cannot be deactiviated, it has not been initialised."); //$NON-NLS-1$
        }

        hideProgressionVisuals();

        /*
         * Refresh diagram visuals.
         */
        rootEditPart.refresh();
        rootEditPart.getViewer().flush();

        isActivitated = false;

        return;
    }

    /**
     * Hide the progression layer and figures.
     */
    public void hideProgressionVisuals() {
        if (isShown) {
            isShown = false;

            for (AbstractProgressionPart part : progressionPartRegistry
                    .values()) {
                if (part.isEnabled()) {
                    part.setEnabled(false);
                    part.terminateProgressionVisuals();
                }
            }

            /*
             * Make the extra progression annotation figures layer visible
             */
            if (progressionFiguresLayer != null) {
                progressionFiguresLayer.setEnabled(false);
                progressionFiguresLayer.setVisible(false);
            }

            /*
             * Make the extra progression control figures layer visible
             */
            if (progressionControlsLayer != null) {
                progressionControlsLayer.setEnabled(false);
                progressionControlsLayer.setVisible(false);
            }
        }
        return;
    }

    /**
     * @return true if the progression is active.
     */
    public boolean isActivitated() {
        return isActivitated;
    }

    /**
     * Perform any final clean up!
     * <p>
     * The progression can be re-initialised and re-sued after disposal
     */
    public void dispose() {
        isDisposed = true;
        if (progressionPartRegistry != null) {
            /* Make sure we clean up any visuals. */
            EditPartViewer viewer = rootEditPart.getViewer();
            if (viewer != null) {
                Control control = viewer.getControl();
                if (control != null) {
                    if (!control.isDisposed()) {
                        deactivateProgression();
                    }
                }
            }

            removeProgressionModelAndParts(rootEditPart.getChildren());
        }

        progressionPartRegistry =
                new HashMap<ProgressionModel, AbstractProgressionPart>();

        progressionModelRegistry = new HashMap<Object, ProgressionModel>();

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (!isDisposed) {
            dispose();
            throw new IllegalStateException(
                    "Progression was not properly disposed."); //$NON-NLS-1$
        }
    }

    /**
     * Listen to new edit parts being added / removed and add/remove the
     * progression model and parts for them.
     * <p>
     * This means that the owner of progression does noit have to reinitialise
     * every time the model changes.
     * 
     * @author aallway
     * @since 26 Jan 2011
     */
    private class EditPartChangeListener implements EditPartListener,
            NodeListener {

        /**
         * @see org.eclipse.gef.EditPartListener#childAdded(org.eclipse.gef.EditPart,
         *      int)
         * 
         * @param child
         * @param index
         */
        @Override
        public void childAdded(EditPart child, int index) {
            if (child instanceof GraphicalEditPart) {
                AbstractProgressionPart progressionPart =
                        createProgressionModelAndPart((GraphicalEditPart) child);

                /*
                 * If the progression is being shown then show this latest one.
                 */
                if (isShown) {
                    if (progressionPart != null) {
                        if (!progressionPart.isEnabled()) {
                            progressionPart.initialiseProgressionVisuals();
                            progressionPart.setEnabled(true);
                        }
                    }
                }
            }
        }

        /**
         * @see org.eclipse.gef.NodeListener#sourceConnectionAdded(org.eclipse.gef.ConnectionEditPart,
         *      int)
         * 
         * @param connection
         * @param index
         */
        @Override
        public void sourceConnectionAdded(ConnectionEditPart connection,
                int index) {
            /*
             * In progression model, connections are counted as parented by the
             * source edit part, so we only respond to source connection added..
             */
            childAdded(connection, index);

        }

        /**
         * @see org.eclipse.gef.EditPartListener#removingChild(org.eclipse.gef.EditPart,
         *      int)
         * 
         * @param child
         * @param index
         */
        @Override
        public void removingChild(EditPart child, int index) {
            if (child instanceof GraphicalEditPart) {
                removeProgressionModelAndPart((GraphicalEditPart) child);
            }
        }

        /**
         * @see org.eclipse.gef.NodeListener#removingSourceConnection(org.eclipse.gef.ConnectionEditPart,
         *      int)
         * 
         * @param connection
         * @param index
         */
        @Override
        public void removingSourceConnection(ConnectionEditPart connection,
                int index) {
            /*
             * In progression model, connections are counted as parented by the
             * source edit part, so we only respond to source connection
             * removed..
             */
            removingChild(connection, index);
        }

        /**
         * @see org.eclipse.gef.EditPartListener#partActivated(org.eclipse.gef.EditPart)
         * 
         * @param editpart
         */
        @Override
        public void partActivated(EditPart editpart) {
        }

        /**
         * @see org.eclipse.gef.EditPartListener#partDeactivated(org.eclipse.gef.EditPart)
         * 
         * @param editpart
         */
        @Override
        public void partDeactivated(EditPart editpart) {
        }

        /**
         * @see org.eclipse.gef.EditPartListener#selectedStateChanged(org.eclipse.gef.EditPart)
         * 
         * @param editpart
         */
        @Override
        public void selectedStateChanged(EditPart editpart) {
        }

        /**
         * @see org.eclipse.gef.NodeListener#removingTargetConnection(org.eclipse.gef.ConnectionEditPart,
         *      int)
         * 
         * @param connection
         * @param index
         */
        @Override
        public void removingTargetConnection(ConnectionEditPart connection,
                int index) {
            /*
             * In progression model, connections are counted as parented by the
             * source edit part, so we only respond to source connection
             * removed..
             */
        }

        /**
         * @see org.eclipse.gef.NodeListener#targetConnectionAdded(org.eclipse.gef.ConnectionEditPart,
         *      int)
         * 
         * @param connection
         * @param index
         */
        @Override
        public void targetConnectionAdded(ConnectionEditPart connection,
                int index) {
            /*
             * In progression model, connections are counted as parented by the
             * source edit part, so we only respond to source connection added..
             */
        }

    }

}
