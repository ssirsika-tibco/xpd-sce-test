/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Collections;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker.ProcessPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * SelectSubProcessResolution - resolution quick fix that allows the selection
 * of a sub process for a call sub-proc activity
 * 
 * 
 * @author aallway
 * @since 3.3 (9 Nov 2009)
 */
public class SelectSubProcessResolution extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {

            Activity activity = (Activity) target;
            EObject process = getProcessFromPicker(activity);
            if (process != null) {

                return TaskObjectUtil.getSetSubprocessCommand(editingDomain,
                        activity,
                        process);
            }
        }

        return null;
    }

    private EObject getProcessFromPicker(Activity activity) {

        EObject processFromPicker = null;

        if (activity != null) {

            final boolean pickPageflow =
                    Xpdl2ModelUtil.isPageflowOrSubType(activity.getProcess());

            ProcessFilterPicker picker =
                    new ProcessFilterPicker(
                            Display.getDefault().getActiveShell(),
                            pickPageflow ? ProcessPickerType.ALL_PROCESS_TYPES
                                    : ProcessPickerType.BUSINESS_OR_SERVICE_PROCESS,
                            false);

            Object curSel = TaskObjectUtil.getSubProcessOrInterface(activity);
            if (curSel != null) {
                picker.setInitialElementSelections(Collections
                        .singletonList(curSel));
            }

            processFromPicker =
                    ProcessUIUtil.getResultFromPicker(picker, Display
                            .getDefault().getActiveShell(), activity);
        }

        return processFromPicker;
    }

}
