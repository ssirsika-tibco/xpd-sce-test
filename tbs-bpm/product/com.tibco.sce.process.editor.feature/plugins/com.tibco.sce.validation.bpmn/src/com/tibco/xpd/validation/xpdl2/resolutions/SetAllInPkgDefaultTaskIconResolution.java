/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Icon;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * SetDefaultFormatResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (6 Oct 2009)
 */
public class SetAllInPkgDefaultTaskIconResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            IProject project = WorkingCopyUtil.getProjectFor(activity);

            if (project != null && activity.getProcess() != null
                    && activity.getProcess().getPackage() != null) {
                Package pkg = activity.getProcess().getPackage();

                if (SetDefaultTaskIconResolution
                        .validateDefaultIconForActivity(activity)) {

                    CompoundCommand cmd = new CompoundCommand();

                    for (Process process : pkg.getProcesses()) {
                        Collection<Activity> acts =
                                Xpdl2ModelUtil.getAllActivitiesInProc(process);

                        for (Activity act : acts) {
                            Icon icon = activity.getIcon();
                            if (icon != null) {
                                String path = icon.getValue();
                                if (path != null && path.length() > 0) {
                                    IPath p = new Path(path);

                                    IFile file = project.getFile(p);
                                    if (!file.exists()) {
                                        Command c =
                                                SetDefaultTaskIconResolution
                                                        .getSetDefaultIconCommand(editingDomain,
                                                                act);
                                        if (c != null) {
                                            cmd.append(c);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    return cmd;
                }
            }
        }
        return null;
    }

}
