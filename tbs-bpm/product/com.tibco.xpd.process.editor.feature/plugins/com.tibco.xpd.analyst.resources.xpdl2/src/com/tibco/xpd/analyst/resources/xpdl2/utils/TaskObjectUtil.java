/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * @author aprasad
 * @since 22 Aug 2012
 */
public class TaskObjectUtil {

    public static final String FORMS_KIND = "forms"; //$NON-NLS-1$

    public static final String FORM_SCHEMA = "form://"; //$NON-NLS-1$

    /**
     * Get the xpdExt:FormImplementation element for the given activity.
     * 
     * @param activity
     * 
     * @return xpdExt:FormImplementation or null if not set (or not user task
     *         activity).
     */
    public static FormImplementation getUserTaskFormImplementation(Activity act) {
        FormImplementation formImpl = null;

        if (act != null && act.getImplementation() instanceof Task) {
            Task task = (Task) act.getImplementation();
            if (task.getTaskUser() != null) {
                formImpl =
                        (FormImplementation) Xpdl2ModelUtil
                                .getOtherElement(task.getTaskUser(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_FormImplementation());
            }
        }

        return formImpl;
    }

    /**
     * @param activity
     *            A User Task activity referencing a pageflow
     * @return The pageflow process or null.
     */
    public static Process getUserTaskPageflowProcess(Activity activity) {
        return getUserTaskPageFlowProcess(activity, null);
    }

    /**
     * @param activity
     * @param sourceProject
     * @return
     */
    public static Process getUserTaskPageFlowProcess(Activity activity,
            IProject sourceProject) {
        Process pageflow = null;

        FormImplementation formImpl = getUserTaskFormImplementation(activity);
        if (formImpl != null
                && FormImplementationType.PAGEFLOW.equals(formImpl
                        .getFormType())) {
            String specialFolderRelativeUri = formImpl.getFormURI();
            if (specialFolderRelativeUri != null) {
                /*
                 * MR 41967: Used to use EcoreUtil.getURI(process) but that's
                 * rubbish because its an absolute workspace relateive uri which
                 * of course will break if you copy the xpdl to a different
                 * project when the user task and referenced pageflow are in the
                 * same xpdl.
                 * 
                 * Now we use <special folder relative path>#<process id>
                 */
                URI uri = URI.createURI(specialFolderRelativeUri);

                String path = uri.path();
                String id = uri.fragment();
                IProject project = sourceProject;
                if (project == null) {
                    project = WorkingCopyUtil.getProjectFor(activity);
                }
                if (project != null && path != null && path.length() > 0
                        && id != null && id.length() > 0) {

                    /*
                     * Otherwise access find the file in this or referenced
                     * projects and then get the pro9cess from that file.
                     */
                    IFile pageflowProcessFile =
                            SpecialFolderUtil
                                    .resolveSpecialFolderRelativePath(project,
                                            Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                                            path,
                                            true);

                    if (pageflowProcessFile != null
                            && pageflowProcessFile.exists()) {
                        WorkingCopy wc =
                                WorkingCopyUtil
                                        .getWorkingCopy(pageflowProcessFile);
                        if (wc.getRootElement() instanceof Package) {
                            pageflow =
                                    ((Package) wc.getRootElement())
                                            .getProcess(id);
                        }
                    }
                }
            }
        }
        return pageflow;
    }

    /**
     * This method calls {@link #getUserTaskFormFile(IProject, Activity)}
     * passing it the parent project of the Activity.
     * 
     * @param activity
     *            A User Task activity referencing a Form
     * @return The form file or null.
     */
    public static IFile getUserTaskFormFile(Activity activity) {
        return getUserTaskFormFile(WorkingCopyUtil.getProjectFor(activity),
                activity);
    }

    /**
     * @param srcProject
     *            The source project in which to search for the form.
     * @param activity
     *            A User Task activity referencing a form
     * @return The form file or <code>null</code>.
     * @since 3.5.20
     */
    public static IFile getUserTaskFormFile(IProject srcProject,
            Activity activity) {
        IFile formFile = null;

        FormImplementation formImpl = getUserTaskFormImplementation(activity);
        if (formImpl != null
                && FormImplementationType.FORM.equals(formImpl.getFormType())) {
            String url = formImpl.getFormURI();
            if (url != null) {
                if (url.startsWith(FORM_SCHEMA)) {
                    String path = url.substring(FORM_SCHEMA.length());
                    formFile =
                            SpecialFolderUtil
                                    .resolveSpecialFolderRelativePath(srcProject,
                                            FORMS_KIND,
                                            path,
                                            true);
                }
            }
        }

        return formFile;
    }
}
