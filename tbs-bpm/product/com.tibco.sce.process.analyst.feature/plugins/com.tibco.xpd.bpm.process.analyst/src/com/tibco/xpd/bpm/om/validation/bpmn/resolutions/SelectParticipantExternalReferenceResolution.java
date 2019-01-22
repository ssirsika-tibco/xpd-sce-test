/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bpm.om.validation.bpmn.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.bpm.om.BPMProcessOrgModelUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantsContainer;
import com.tibco.xpd.xpdl2.edit.ui.ComplexDataUIUtil;

/**
 * Resolution to set an external reference to a participant
 * 
 * @author bharge
 * 
 */
public class SelectParticipantExternalReferenceResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Participant) {
            Participant participant = (Participant) target;
            IProject project = WorkingCopyUtil.getProjectFor(target);
            EObject elementFromPicker =
                    ComplexDataUIUtil.getOMElementFromPicker(Display
                            .getCurrent().getActiveShell(), project, null);
            if (null != elementFromPicker) {
                return BPMProcessOrgModelUtil
                        .getSetOrgModelParticipantRefCommand(editingDomain,
                                (ParticipantsContainer) participant
                                        .eContainer(),
                                participant,
                                elementFromPicker);
            }
        }
        return null;
    }
}
