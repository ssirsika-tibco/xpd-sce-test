/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Quick-fix resolution to select a new Runtime Field Indentifier in a Calendar
 * Reference.
 * 
 * @author njpatel
 */
public class SelectCalendarReferenceFieldIdentifierResolution extends
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
                DataFilterPicker picker =
                        new DataFilterPicker(XpdResourcesPlugin
                                .getStandardDisplay().getActiveShell(),
                                DataPickerType.DATAFIELD_FORMALPARAMETER, false);
                picker.setScope(container);
                if (picker.open() == DataFilterPicker.OK) {
                    Object result = picker.getFirstResult();
                    if (result instanceof ProcessRelevantData) {
                        cmd =
                                SetCommand
                                        .create(editingDomain,
                                                element,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getCalendarReference_DataFieldId(),
                                                ((ProcessRelevantData) result)
                                                        .getId());
                    }
                }
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
