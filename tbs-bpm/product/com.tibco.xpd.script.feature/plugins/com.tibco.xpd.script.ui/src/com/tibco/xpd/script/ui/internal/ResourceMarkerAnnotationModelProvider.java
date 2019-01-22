/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.script.ui.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;

/**
 * Provider for ResourceMarkerAnnotationModel
 * 
 * @author rsomayaj
 * 
 */
public abstract class ResourceMarkerAnnotationModelProvider {

    private EObject input;

    /**
     * Guide to provide ResourceMarkerAnnotationModel to the Script Grammar
     * Editor section.
     * 
     * Since it is an optional contribution. Implementation will be required
     * only if necessary
     * 
     * @param resource
     * @param markerContainerId
     * @param scriptContext
     * @return
     */
    public ResourceMarkerAnnotationModel getResourceMarkerAnnotationModel(
            IResource resource, String markerContainerId, String scriptContext) {
        return null;
    }

    /**
     * @param input
     */
    public void setInput(EObject input) {
        this.input = input;
    }

    /**
     * @return the input
     */
    public EObject getInput() {
        return input;
    }
}
