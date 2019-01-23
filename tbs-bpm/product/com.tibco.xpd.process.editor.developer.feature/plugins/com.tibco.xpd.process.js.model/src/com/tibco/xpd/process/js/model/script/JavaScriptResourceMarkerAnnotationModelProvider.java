/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.process.js.model.script;

import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;

import com.tibco.xpd.script.ui.internal.JScriptResourceMarkerAnnotationModel;
import com.tibco.xpd.script.ui.internal.ResourceMarkerAnnotationModelProvider;

/**
 * Provides the Resource Marker Annotation Model
 * 
 * @author rsomayaj
 * 
 */
public class JavaScriptResourceMarkerAnnotationModelProvider extends
        ResourceMarkerAnnotationModelProvider {

    public ResourceMarkerAnnotationModel getResourceMarkerAnnotationModel(
            org.eclipse.core.resources.IResource resource,
            String markerContainerId, String scriptContext) {
        ResourceMarkerAnnotationModel annotationModel =
                new JScriptResourceMarkerAnnotationModel(resource,
                        markerContainerId, scriptContext);
        return annotationModel;
    };
}
