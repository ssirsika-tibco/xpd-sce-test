/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.transform.de.script;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;

import com.tibco.xpd.om.core.om.OrgQuery;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.ui.internal.JScriptResourceMarkerAnnotationModel;
import com.tibco.xpd.script.ui.internal.ResourceMarkerAnnotationModelProvider;

/**
 * Resource Marker annotation provider for Org query section.
 * 
 * @author rsomayaj
 * 
 */
public class OrgQueryResourceMarkerAnnotationProvider extends
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
        ResourceMarkerAnnotationModel annotationModel = null;
        if (getInput() instanceof OrgQuery) {
            OrgQuery orgQuery = (OrgQuery) getInput();
            IFile file = WorkingCopyUtil.getFile(orgQuery);
            String scriptContainerId = orgQuery.getId();
            if (file != null) {
                annotationModel =
                        new JScriptResourceMarkerAnnotationModel(file,
                                scriptContainerId, scriptContext);
            }
        }
        return annotationModel;
    }
}
