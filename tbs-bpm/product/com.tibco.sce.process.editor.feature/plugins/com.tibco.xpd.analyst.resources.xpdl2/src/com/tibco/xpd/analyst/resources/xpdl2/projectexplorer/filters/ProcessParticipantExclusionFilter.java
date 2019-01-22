/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ParticipantGroup;
import com.tibco.xpd.xpdl2.Process;

/**
 * ProcessParticipantExclusionFilter
 * <p>
 * Exclude process level participants group (unless it is not empty).
 * 
 * @author bharge
 * @since 3.3 (8 Oct 2009)
 */
public class ProcessParticipantExclusionFilter extends ViewerFilter {

    public ProcessParticipantExclusionFilter() {
        super();
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean include = true;
        if (element instanceof ParticipantGroup) {
            ParticipantGroup participantGroup = (ParticipantGroup) element;
            if (participantGroup.getParent() instanceof Process) {
                if (!participantGroup.hasChildren()) {
                    include = false;
                }
            }
        }
        return include;
    }
}
