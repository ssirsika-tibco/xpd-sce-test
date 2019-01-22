/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.progression.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.gef.EditPart;

import com.tibco.xpd.processwidget.progression.AbstractDiagramProgression;
import com.tibco.xpd.processwidget.progression.AbstractProgressionPart;

/**
 * The basic progression-thru-diagram state model allocated for the model object
 * underlying each diagram {@link EditPart}).
 * <p>
 * The {@link ProgressionModel} stores the {@link ProgressionState} for
 * the diagram object plus any extended properties that might be required for
 * use with particular diagram / progression visualisation.
 * <p>
 * Use of the underlying model object rather than edit part allows the user of
 * {@link AbstractDiagramProgression} a degree of independence from the diagram
 * edit parts used to visualise those diagram model objects. In other words, it
 * is intended that the subclass of {@link AbstractDiagramProgression} for a
 * particular diagram provides progression state visualisation via its
 * implementation of {@link AbstractProgressionPart}. The user of this diagram
 * progression type uses {@link ProgressionModel} for the diagram model object
 * to effect the progression state of that object.
 * <p>
 * When {@link AbstractDiagramProgression#activateProgression()} is performed,
 * the diagram model object underlying each {@link EditPart} in the diagram is
 * assigned a {@link ProgressionModel} (via the passed
 * {@link ProgressionModelFactory}).
 * <p>
 * Once the progression is activated, the owner of the progression can effect
 * the progression thru the diagram by setting the
 * {@link ProgressionState} in the {@link ProgressionModel} for a diagram
 * model object. To do this the {@link ProgressionModel} for a given diagram
 * model object is accessed using
 * {@link AbstractDiagramProgression#getProgressionModelForDiagramObject(Object)}
 * method, passing the diagram model object. Then
 * {@link ProgressionModel#setProgressionState(ProgressionState)} is used
 * to change the progression state of that object.
 * <p>
 * When the {@link ProgressionState} is set then the
 * {@link ProgressionModel} {@link IProgressionModelStateChangeListener}'s are
 * notified of the state change. Nominally, this means the
 * {@link AbstractProgressionPart} (the class that ties the
 * {@link ProgressionModel} to the original diagram {@link EditPart}) is
 * notified and can then ask it's subclass to make some visual modification to
 * the diagram figure and/or Diagram to visualise that state change.
 * <p>
 * It possible to extend {@link ProgressionModel} for your own purposes and use
 * an alternative {@link ProgressionModelFactory} that returns these extended
 * progression model classes. However, it is not anticipated that this will be
 * required because of ability to extend the basic {@link ProgressionModel}
 * using {@link #setExtendedProperty(Object, Object)} and
 * {@link #getExtendedProperty(Object)}.
 * 
 * @author aallway
 * @since 3.3 (30 Oct 2009)
 */
public class ProgressionModel {

    /**
     * The diagram model object that this is the progression model for. This
     * will be a model object that underlies the diagram {@link EditPart}.
     */
    private Object diagramModelObject;

    /**
     * The current progression state of this object in the overall progression.
     */
    private ProgressionState progressionState =
            ProgressionState.NOT_PROCESSED;

    /**
     * Progression State change listeners
     */
    private Set<IProgressionModelStateChangeListener> stateChangeListeners =
            new HashSet<IProgressionModelStateChangeListener>();

    /**
     * Stores owner's optional extra properties (created on demand).
     */
    private Map<Object, Object> extendedProperties = null;

    /**
     * Construct a progression model object for the given diagram model object
     * (that is, the model object underlying a diagram {@link EditPart}).
     * 
     * @param diagramModelObject
     */
    public ProgressionModel(Object diagramModelObject) {
        this.diagramModelObject = diagramModelObject;
    }

    /**
     * @return The current progressionState
     */
    public ProgressionState getProgressionState() {
        return progressionState;
    }

    /**
     * Set the progression state of this object.
     * 
     * @param progressionState
     *            the progressionState to set
     */
    public void setProgressionState(ProgressionState newProgressionState) {
        ProgressionState oldState = progressionState;
        progressionState = newProgressionState;

        ProgressionStateChangeEvent event =
                new ProgressionStateChangeEvent(this, oldState,
                        progressionState);
        for (IProgressionModelStateChangeListener listener : stateChangeListeners) {
            listener.progressionStateChanged(event);
        }

        return;
    }

    /**
     * @return The diagram model object that this {@link ProgressionModel} is
     *         tied to.
     */
    public Object getDiagramModelObject() {
        return diagramModelObject;
    }

    /**
     * Add listener for progression model state changes.
     * 
     * @param listener
     */
    public void addObjectProgressionStateChangeListener(
            IProgressionModelStateChangeListener listener) {
        stateChangeListeners.add(listener);
        return;
    }

    /**
     * Remove listener for progression model state changes.
     * 
     * @param listener
     */
    public void removeObjectProgressionStateChangeListener(
            IProgressionModelStateChangeListener listener) {
        stateChangeListeners.remove(listener);
        return;
    }

    /**
     * Set the value of an extended property
     * 
     * @param property
     * @param value
     */
    public void setExtendedProperty(Object property, Object value) {
        if (extendedProperties == null) {
            extendedProperties = new HashMap<Object, Object>();
        }
        extendedProperties.put(property, value);
        return;
    }

    /**
     * Get the value of an extended property.
     * 
     * @param property
     * 
     * @return The value of an extended property
     */
    public Object getExtendedProperty(Object property) {
        if (extendedProperties != null) {
            return extendedProperties.get(property);
        }
        return null;
    }

    /**
     * Remove the given extended property.
     * 
     * @param property
     */
    public void removeExtendedProperty(Object property) {
        if (extendedProperties != null) {
            extendedProperties.remove(property);
        }
        return;
    }
}
