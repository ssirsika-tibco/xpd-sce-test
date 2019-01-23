package com.tibco.xpd.processwidget.adapters;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;

/**
 * Class to store a problem marker and the modelObject it is associated
 * with.
 */
public class MarkerAndModelObject {
    private IMarker marker;

    private EObject modelObject;

    public MarkerAndModelObject(IMarker marker, EObject modelObject) {
        super();
        this.marker = marker;
        this.modelObject = modelObject;
    }

    /**
     * @return the marker
     */
    public IMarker getMarker() {
        return marker;
    }

    /**
     * @return the modelObject
     */
    public EObject getModelObject() {
        return modelObject;
    }
}