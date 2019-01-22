/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.annotations.problems;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerHelpRegistry;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipProviderFigure;

/**
 * Quick fix tooltip content provider for Bpmn Process problems.
 * 
 * @author aallway
 * @since 3.2
 */
public class ProcessQuickFixToolTipContentProvider implements
        QuickFixToolTipProviderFigure.IQuickFixToolTipContentProvider {

    public Collection<IMarker> getMarkers(Object markerHost) {
        List<IMarker> markerList = null;

        if (markerHost instanceof BaseProcessAdapter) {
            markerList =
                    ((BaseProcessAdapter) markerHost)
                            .getProblemMarkerList(false);
        }

        if (markerList == null) {
            markerList = Collections.EMPTY_LIST;
        }

        return markerList;
    }

    public Collection<IMarkerResolution> getMarkerResolutions(IMarker marker) {
        IMarkerHelpRegistry markerHelpRegistry = IDE.getMarkerHelpRegistry();

        IMarkerResolution[] resolutions =
                markerHelpRegistry.getResolutions(marker);

        if (resolutions != null) {
            return Arrays.asList(resolutions);
        }

        return Collections.emptyList();
    }
}