/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.TaskLibraryActivityPicker;
import com.tibco.xpd.processeditor.xpdl2.util.ReferenceTaskUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Reference;

/**
 * Resolution to allow user to select a task library task reference for a
 * reference task.
 * 
 * @author aallway
 * @since 3.2
 */
public class SelectLibraryTaskReferenceResolution extends
        AbstractWorkingCopyResolution implements IFilter {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity
                && ((Activity) target).getImplementation() instanceof Reference) {
            TaskLibraryActivityPicker picker =
                    new TaskLibraryActivityPicker(Display.getDefault()
                            .getActiveShell(), false);
            if (picker.open() == picker.OK) {
                Object[] result = picker.getResult();

                if (result.length == 1 && result[0] instanceof Activity) {
                    Activity libraryTask = (Activity) result[0];
                    Process taskLibrary = libraryTask.getProcess();

                    return ReferenceTaskUtil
                            .getSetReferencedLibraryTaskCommand(editingDomain,
                                    (Activity) target,
                                    ((Activity) target).getProcess(),
                                    libraryTask.getId(),
                                    taskLibrary);
                }
            }
        }
        return null;
    }

    public boolean select(Object toTest) {
        // Only allow "Set Task Library Ref" if it's installed!
        if (Xpdl2ResourcesPlugin.isTaskLibraryPluginAvailable()) {
            return true;
        }
        return false;
    }
}

