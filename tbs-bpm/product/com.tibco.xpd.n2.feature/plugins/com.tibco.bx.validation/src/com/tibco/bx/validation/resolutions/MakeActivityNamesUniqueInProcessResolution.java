/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.rules.process.ActivityNameRule;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolves duplicate activity name problem in a process by renaming activities,
 * i.e., suffix each duplicating activity name with incrementing number
 * (XPD-4451)
 * 
 * @author agondal
 * @since 4 Feb 2013
 */
public class MakeActivityNamesUniqueInProcessResolution extends
        AbstractWorkingCopyResolution implements IResolution {

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

        if (target instanceof Process) {

            Process process = (Process) target;

            String oldName = getOldName(marker);

            if (oldName != null) {

                List<Activity> dupliactes = new ArrayList<Activity>();

                // get all the duplicate activities
                for (Activity activity : Xpdl2ModelUtil
                        .getAllActivitiesInProc(process)) {

                    if (activity.getName() != null
                            && activity.getName().equals(oldName)) {
                        dupliactes.add(activity);
                    }
                }

                CompoundCommand cmd = new CompoundCommand();

                renameDuplicateActivities(editingDomain,
                        process,
                        dupliactes,
                        oldName,
                        cmd);

                if (!cmd.isEmpty()) {
                    return cmd;
                }
            }
        }
        return null;
    }

    /**
     * Rename duplicate activities in the process by suffixing a number
     * 
     * @param ed
     * @param process
     * @param dupliactes
     * @param oldName
     * @param cmd
     * @return
     */
    private void renameDuplicateActivities(EditingDomain ed, Process process,
            List<Activity> dupliactes, String oldName, CompoundCommand cmd) {

        int suffixCounter = 1;
        String newName;
        Activity activity;

        for (int i = 1; i < dupliactes.size(); i++) {

            activity = dupliactes.get(i);

            Boolean renamed = false;

            do {

                newName = oldName + suffixCounter;

                /*
                 * update activity name if the newName doesn't conflict with an
                 * existing activity
                 */

                if (Xpdl2ModelUtil.getActivityByName(process, newName) == null) {

                    cmd.append(SetCommand.create(ed,
                            activity,
                            Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                            newName));

                    String oldLabel = Xpdl2ModelUtil.getDisplayName(activity);
                    
                    /* if name and label are same then update the label as well */
                    if (NameUtil.getInternalName(oldLabel, false)
                            .equals(oldName)) {

                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(ed,
                                        activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_DisplayName(),
                                        oldLabel + " " + suffixCounter)); //$NON-NLS-1$

                    }

                    renamed = true;
                }

                suffixCounter++;

            } while (!renamed);

        }

    }

    /**
     * Get duplicate activity name from the problem marker
     * 
     * @param marker
     * @return name or <code>null</code>
     */
    private String getOldName(IMarker marker) {

        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);

        if (addInfo != null) {
            String oldName =
                    addInfo.getProperty(ActivityNameRule.ISSUE_DUPLICATE_ACTIVITY_NAME);
            if (oldName != null && oldName.length() > 0) {
                return oldName;
            }
        }

        return null;
    }

}
