/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.ui.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Position;
import org.eclipse.ui.texteditor.MarkerAnnotation;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;

import com.tibco.xpd.resources.util.XpdConsts;

/**
 * @author rsomayaj
 * 
 */

public class JScriptResourceMarkerAnnotationModel extends
        ResourceMarkerAnnotationModel {

    private String scriptContainerId;

    private String scriptContext;

    public JScriptResourceMarkerAnnotationModel(IResource resource,
            String scriptContainerId, String scriptContext) {
        super(resource);
        this.scriptContainerId = scriptContainerId;
        this.scriptContext = scriptContext;
    }

    @Override
    protected boolean isAcceptable(IMarker marker) {
        if (!marker.exists()) {
            return false;
        }
        boolean bool = super.isAcceptable(marker);
        if (!bool) {
            return bool;
        }
        try {
            String markerActivityId =
                    (String) marker.getAttribute(IMarker.LOCATION);
            if (scriptContainerId.equals(markerActivityId)) {
                MarkerInfo markerInfo = getMarkerInfo(marker);
                if (scriptContext != null && markerInfo != null
                        && scriptContext.equals(markerInfo.scriptContext)) {
                    bool = true;
                } else {
                    bool = false;
                }
            } else {
                bool = false;
            }
        } catch (CoreException e) {

        }
        return bool;
    }

    @Override
    protected IMarker[] retrieveMarkers() throws CoreException {
        IMarker[] markers = super.retrieveMarkers();
        // filtering the markers for the script shown in the editor
        ArrayList<IMarker> validationMarkerList = new ArrayList<IMarker>();
        for (IMarker marker : markers) {
            if (!marker.exists()) {
                continue;
            }
            String markerActivityId =
                    (String) marker.getAttribute(IMarker.LOCATION);
            if (markerActivityId != null
                    && markerActivityId.equals(scriptContainerId)) {
                MarkerInfo markerInfo = getMarkerInfo(marker);
                if (scriptContext != null && markerInfo != null
                        && scriptContext.equals(markerInfo.scriptContext)) {
                    validationMarkerList.add(marker);
                }
            }
        }
        IMarker[] toReturn = new IMarker[validationMarkerList.size()];
        return validationMarkerList.toArray(toReturn);
    }

    @Override
    protected Position createPositionFromMarker(IMarker marker) {
        int start = -1;
        // marker line number is 1-based
        MarkerInfo markerInfo = getMarkerInfo(marker);
        if (markerInfo == null) {
            return null;
        }
        int line = markerInfo.lineNumber;
        if (line > 0 && fDocument != null) {
            try {
                start = fDocument.getLineOffset(line - 1);
            } catch (BadLocationException x) {
                start = fDocument.getNumberOfLines();
            }
        }
        if (start > -1) {
            return new Position(start);
        }
        return null;
    }

    /**
     * Returns the line number of the given marker.
     * 
     * @param marker
     *            the marker
     * @return the line number, or <code>-1</code> if not set
     * @see IMarker#LINE_NUMBER
     * @see IMarker#getAttribute(java.lang.String,int)
     */
    private MarkerInfo getMarkerInfo(IMarker marker) {
        if (!marker.exists()) {
            return null;
        }
        try {
            String additionalInfo =
                    String
                            .valueOf(marker
                                    .getAttribute(XpdConsts.VALIDATION_MARKER_ADDITIONAL_INFO));
            if (additionalInfo != null) {
                MarkerInfo info = null;
                ByteArrayInputStream stream =
                        new ByteArrayInputStream(additionalInfo.getBytes());
                try {
                    Properties props = new Properties();
                    props.load(stream);
                    String strLineNumber = props.getProperty("LineNumber"); //$NON-NLS-1$
                    String strColumnNumber = props.getProperty("ColumnNumber"); //$NON-NLS-1$
                    String errorMessage = props.getProperty("ErrorMessage"); //$NON-NLS-1$
                    String scriptContext = props.getProperty("ScriptContext"); //$NON-NLS-1$
                    if (strLineNumber != null && strColumnNumber != null
                            && errorMessage != null) {
                        info = new MarkerInfo();
                        info.lineNumber =
                                strLineNumber != null ? Integer
                                        .parseInt(strLineNumber) : -1;
                        info.columnNumber =
                                strColumnNumber != null ? Integer
                                        .parseInt(strColumnNumber) : -1;
                        info.errroMsg = errorMessage;
                        info.scriptContext = scriptContext;
                    }
                    validateMarkerInfo(info);
                    return info;
                } catch (IOException e) {
                    // ignore
                }
            }
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void validateMarkerInfo(MarkerInfo info) {
        if (info == null) {
            return;
        }
        if (info.lineNumber == 0) {
            info.lineNumber = 1;
        }
        if (info.columnNumber == 0) {
            info.columnNumber = 1;
        }
    }

    @Override
    protected MarkerAnnotation createMarkerAnnotation(IMarker marker) {
        if (!marker.exists()) {
            return null;
        }
        return new JsMarkerAnnotation(marker);
    }

    class MarkerInfo {

        int lineNumber;

        int columnNumber;

        String errroMsg;

        String scriptContext;
    }

    class JsMarkerAnnotation extends MarkerAnnotation {

        public JsMarkerAnnotation(IMarker marker) {
            super(marker);
        }

        @Override
        public String getText() {
            IMarker marker = getMarker();
            MarkerInfo markerInfo = getMarkerInfo(marker);
            if (markerInfo == null) {
                return ""; //$NON-NLS-1$
            }
            return markerInfo.errroMsg;
        }
    }

}
