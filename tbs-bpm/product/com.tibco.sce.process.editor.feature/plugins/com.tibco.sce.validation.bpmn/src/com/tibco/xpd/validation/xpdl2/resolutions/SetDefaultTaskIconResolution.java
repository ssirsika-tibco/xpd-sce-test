/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Icon;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * SetDefaultFormatResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (6 Oct 2009)
 */
public class SetDefaultTaskIconResolution extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            if (validateDefaultIconForActivity(activity)) {
                return getSetDefaultIconCommand(editingDomain, activity);
            }

        }
        return null;
    }

    /**
     * @param editingDomain
     * @param activity
     * @return Command to set task icon to user defined default - <b>Note:</b>
     *         If the user-defined default does not exist in project then the a
     *         dialog is displayed to question the user whether to revert to
     *         factory default.
     */
    public static Command getSetDefaultIconCommand(EditingDomain editingDomain,
            Activity activity) {
        Icon newIcon = null;

        String iconRegId = TaskObjectUtil.getTaskTypeIconRegistryId(activity);
        if (iconRegId != null) {

            IPreferenceStore prefStore =
                    ProcessWidgetPlugin.getDefault().getPreferenceStore();
            String defIconPath = prefStore.getString(iconRegId);

            if (defIconPath != null && defIconPath.length() > 0) {

                newIcon = Xpdl2Factory.eINSTANCE.createIcon();
                newIcon.setValue(defIconPath);
            }
        }

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.SetDefaultTaskIconResolution_ResetTaskIconToDefault_menu);
        cmd.append(SetCommand.create(editingDomain,
                activity,
                Xpdl2Package.eINSTANCE.getActivity_Icon(),
                newIcon));

        return cmd;
    }

    /**
     * Check that the user defined default icon for the task type exists and if
     * not, question the user whether to revert to factory default.
     * 
     * @param activity
     * 
     * @return true if default icon exists in project OR there is no user
     *         defined default.
     */
    public static boolean validateDefaultIconForActivity(Activity activity) {
        String iconRegId = TaskObjectUtil.getTaskTypeIconRegistryId(activity);
        if (iconRegId != null) {
            IPreferenceStore prefStore =
                    ProcessWidgetPlugin.getDefault().getPreferenceStore();
            String defIconPath = prefStore.getString(iconRegId);

            if (defIconPath != null && defIconPath.length() > 0) {
                /*
                 * Check user defined default icon exists.
                 */
                IProject project = WorkingCopyUtil.getProjectFor(activity);
                if (project != null) {
                    IPath p = new Path(defIconPath);
                    IFile file = project.getFile(p);

                    if (!file.exists()) {
                        if (!MessageDialog
                                .openQuestion(Display.getDefault()
                                        .getActiveShell(),
                                        Messages.SetDefaultTaskIconResolution_DefIconMissing_title,
                                        String
                                                .format(Messages.SetDefaultTaskIconResolution_DefIconMissing_message,
                                                        defIconPath))) {
                            /*
                             * User declined to revert to factory default
                             */
                            return false;

                        } else {
                            /*
                             * Reset the factory default - basically just remove
                             * the preference.
                             */
                            prefStore.setToDefault(iconRegId);
                        }
                    }
                }
            }
        }

        return true;
    }

}
