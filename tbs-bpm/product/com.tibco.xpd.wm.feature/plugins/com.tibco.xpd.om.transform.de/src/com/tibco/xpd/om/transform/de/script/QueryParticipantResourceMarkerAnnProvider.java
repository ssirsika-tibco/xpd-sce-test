/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.transform.de.script;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.ui.internal.JScriptResourceMarkerAnnotationModel;
import com.tibco.xpd.script.ui.internal.ResourceMarkerAnnotationModelProvider;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Participant;

/**
 * @author rsomayaj
 * 
 */
public class QueryParticipantResourceMarkerAnnProvider extends
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
        if (getInput() instanceof Participant) {
            Participant participant = (Participant) getInput();
            IFile file = WorkingCopyUtil.getFile(participant);
            String scriptContainerId = participant.getId();
            if (file != null) {
                annotationModel =
                        new JScriptResourceMarkerAnnotationModel(file,
                                scriptContainerId, scriptContext);
            }
        } else if (getInput() instanceof DataField) {
            DataField df = (DataField) getInput();
            if (df.getDataType() instanceof BasicType
                    && ((BasicType) df.getDataType()).getType() != null
                    && ((BasicType) df.getDataType()).getType()
                            .equals(BasicTypeType.PERFORMER_LITERAL)) {
                IFile file = WorkingCopyUtil.getFile(df);
                String scriptContainerId = df.getId();
                if (file != null) {
                    annotationModel =
                            new JScriptResourceMarkerAnnotationModel(file,
                                    scriptContainerId, scriptContext);
                }
            }
        }
        return annotationModel;
    }
}
