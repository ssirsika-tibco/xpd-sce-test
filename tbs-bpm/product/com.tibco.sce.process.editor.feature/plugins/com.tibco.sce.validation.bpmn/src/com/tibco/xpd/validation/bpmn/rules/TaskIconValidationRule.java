/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Icon;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * TaskIconValidationRule
 * 
 * 
 * @author aallway
 * @since 3.3 (6 Oct 2009)
 */
public class TaskIconValidationRule extends ProcessValidationRule {

    public static String ICON_NOT_FOUND = "bpmn.taskIconNotFound"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        IProject project = WorkingCopyUtil.getProjectFor(process);
        if (project != null) {
            for (Activity activity : activities) {
                Icon icon = activity.getIcon();
                if (icon != null) {
                    String path = icon.getValue();
                    if (path != null && path.length() > 0) {
                        IPath p = new Path(path);

                        IFile file = project.getFile(p);
                        if (!file.exists()) {
                            addIssue(ICON_NOT_FOUND, activity, Collections
                                    .singletonList(path));
                        }
                    }
                }
            }
        }

        return;
    }

}
