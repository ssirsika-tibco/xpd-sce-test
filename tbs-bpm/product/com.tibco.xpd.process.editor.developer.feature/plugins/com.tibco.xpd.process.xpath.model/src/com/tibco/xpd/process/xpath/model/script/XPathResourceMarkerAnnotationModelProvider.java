/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.process.xpath.model.script;

import org.eclipse.core.resources.IResource;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;

import com.tibco.xpd.script.ui.internal.JScriptResourceMarkerAnnotationModel;
import com.tibco.xpd.script.ui.internal.ResourceMarkerAnnotationModelProvider;

/**
 * Resource Marker Annotation model provider for XPath
 * 
 * @author rsomayaj
 * 
 */
public class XPathResourceMarkerAnnotationModelProvider extends
        ResourceMarkerAnnotationModelProvider {

    /**
     * @see com.tibco.xpd.script.ui.internal.ResourceMarkerAnnotationModelProvider#getResourceMarkerAnnotationModel(org.eclipse.core.resources.IResource,
     *      java.lang.String, java.lang.String)
     * 
     * @param resource
     * @param markerContainerId
     * @param scriptContext
     * @return
     */
    @Override
    public ResourceMarkerAnnotationModel getResourceMarkerAnnotationModel(
            IResource resource, String markerContainerId, String scriptContext) {
        ResourceMarkerAnnotationModel annotationModel =
                new JScriptResourceMarkerAnnotationModel(resource,
                        markerContainerId, scriptContext);
        return annotationModel;
    }
}
