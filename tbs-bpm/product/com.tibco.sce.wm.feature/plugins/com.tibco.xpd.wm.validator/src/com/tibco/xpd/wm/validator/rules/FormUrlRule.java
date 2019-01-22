/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.wm.validator.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author NWilson
 * 
 */
public class FormUrlRule extends ProcessValidationRule {

    private static final String FORM_FILE_EXTENSION = "form"; //$NON-NLS-1$

    private static final String FORMS_FOLDER_KIND = "forms"; //$NON-NLS-1$

    private static final String INVALID = "n2.wp.invalidFormUrl"; //$NON-NLS-1$

    private static final String DUPLICATE = "n2.wp.duplicateFormUrl"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                TaskUser user = task.getTaskUser();
                if (user != null) {
                    for (ExtendedAttribute ea : activity
                            .getExtendedAttributes()) {
                        if (TaskObjectUtil.USER_TASK_ATTR.equals(ea.getName())) {
                            String url = ea.getValue();
                            validateUrl(activity, url);
                        }
                    }
                }
            }
        }
    }

    private void validateUrl(Activity activity, String url) {
        boolean valid = true;
        List<IResource> matches = new ArrayList<IResource>();
        if (url != null && url.startsWith("form://")) {
            String path = url.substring(7, url.length());
            IProject project = WorkingCopyUtil.getProjectFor(activity);
            String extension = getExtension(url);
            valid = validatePath(path, extension, project, matches);
        }
        if (!valid) {
            addIssue(INVALID, activity);
        }
        if (matches.size() > 1) {
            addIssue(DUPLICATE, activity);
        }
    }

    private boolean validatePath(String path, String extension,
            IProject project, List<IResource> matches) {
        boolean valid = false;
        List<IResource> files =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                FORMS_FOLDER_KIND,
                                extension,
                                false);
        for (IResource file : files) {
            IPath relative =
                    SpecialFolderUtil.getSpecialFolderRelativePath(file,
                            FORMS_FOLDER_KIND);
            String relativeString = relative.toString();
            if (path.equals(relativeString)) {
                valid = true;
                matches.add(file);
            }
        }
        return valid;
    }

    private String getExtension(String url) {
        String extension = FORM_FILE_EXTENSION;
        int i = url.lastIndexOf('/');
        if (i != -1) {
            url = url.substring(i);
        }
        i = url.lastIndexOf('.');
        if (i != -1 && url.length() > (i + 1)) {
            extension = url.substring(i + 1);
        }
        return extension;
    }

}
