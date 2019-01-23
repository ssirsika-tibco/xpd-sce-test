/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * move local activity data fields from unsupported activity to its parent
 * process / embedded sub process
 * 
 * 
 * @author bharge
 * @since 3.3 (4 Aug 2010)
 */
public class MoveLocalFieldsToParentResolution extends
        AbstractWorkingCopyResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            CompoundCommand cmd = new CompoundCommand();
            if (activity.getDataFields().size() > 0) {
                /**
                 * removing it from the unsupported activity
                 */
                cmd.append(RemoveCommand.create(editingDomain, activity
                        .getDataFields()));

                /** moving to the parent */
                if (activity.eContainer() instanceof ActivitySet) {
                    /** parent of this activity is embedded sub proc */
                    ActivitySet activitySet =
                            (ActivitySet) activity.eContainer();
                    Activity parentAct =
                            Xpdl2ModelUtil
                                    .getEmbSubProcActivityForActSet(activity
                                            .getProcess(), activitySet.getId());
                    if (null != parentAct.getBlockActivity()) {
                        cmd.append(AddCommand
                                .create(editingDomain,
                                        parentAct,
                                        Xpdl2Package.eINSTANCE
                                                .getDataFieldsContainer(),
                                        activity.getDataFields()));
                    }
                } else if (activity.eContainer() instanceof Process) {
                    /** parent of this activity is process */
                    Process process = (Process) activity.eContainer();
                    cmd.append(AddCommand.create(editingDomain,
                            process,
                            Xpdl2Package.eINSTANCE.getDataFieldsContainer(),
                            activity.getDataFields()));
                }
            }
            return cmd;
        }
        return null;
    }

}
