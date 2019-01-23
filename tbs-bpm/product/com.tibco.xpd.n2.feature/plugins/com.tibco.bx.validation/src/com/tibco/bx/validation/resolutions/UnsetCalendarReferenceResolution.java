/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Quick-fix resolution to unset a Calendar Reference.
 * 
 * @author njpatel
 */
public class UnsetCalendarReferenceResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;

        OtherElementsContainer container =
                getCalendarReferenceContainer(target);
        if (container != null) {
            Object element =
                    Xpdl2ModelUtil.getOtherElement(container,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CalendarReference());
            if (element instanceof CalendarReference) {
                // Get command to remove the Calendar Reference
                cmd =
                        Xpdl2ModelUtil
                                .getRemoveOtherElementCommand(editingDomain,
                                        container,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_CalendarReference(),
                                        element);
            }
        }

        return cmd;
    }

    /**
     * Get the element that will contain the {@link CalendarReference} from the
     * given target.
     * 
     * @param target
     * @return
     */
    private OtherElementsContainer getCalendarReferenceContainer(EObject target) {
        OtherElementsContainer container = null;

        if (target instanceof Process) {
            container = (OtherElementsContainer) target;
        } else if (target instanceof Activity) {
            container = EventObjectUtil.getTriggerTimer((Activity) target);
        }

        return container;
    }

}
