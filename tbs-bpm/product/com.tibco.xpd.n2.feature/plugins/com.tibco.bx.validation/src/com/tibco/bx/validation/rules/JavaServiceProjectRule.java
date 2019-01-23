/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.pde.core.plugin.IPluginModelBase;

import com.tibco.xpd.daa.internal.util.PluginManifestHelper;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.utils.EAIJavaModelUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * JavaServiceProjectRule
 * 
 * 
 * @author bharge
 * @since 3.3 (21 Jan 2010)
 */
public class JavaServiceProjectRule extends ProcessValidationRule {

    private static final String ISSUE_PROJECTNOTFOUND_ID = "bx.projectNotFound"; //$NON-NLS-1$

    private static final String ISSUE_MANY_PROJECTS_WITH_THE_SAME_ID =
            "bx.manyProjectsWithTheSameId"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            validateActivity(activity);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#validate(
     * com.tibco.xpd.xpdl2.Activity)
     */
    private void validateActivity(Activity activity) {
        if (activity != null) {
            if (activity.getImplementation() instanceof Task) {
                TaskService taskService =
                        ((Task) activity.getImplementation()).getTaskService();
                if (taskService != null) {
                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(taskService,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if ("Java".equals(type)) { //$NON-NLS-1$
                        EAIJava eaiJava =
                                EAIJavaModelUtil.getEAIJavaObj(taskService);
                        if (eaiJava != null) {
                            String projectName = eaiJava.getProject();
                            if (projectName != null) {
                                // Validate project
                                IProject project =
                                        ResourcesPlugin.getWorkspace()
                                                .getRoot()
                                                .getProject(projectName);
                                if (project != null) {
                                    if (!project.isAccessible()) {
                                        // Project is closed or does not exist
                                        addIssue(ISSUE_PROJECTNOTFOUND_ID,
                                                activity,
                                                Collections
                                                        .singletonList(projectName));
                                    } else {
                                        String bundleId =
                                                PluginManifestHelper
                                                        .getPluginProjectDetails(project)
                                                        .getBundleId();
                                        List<IPluginModelBase> pluginModels =
                                                PluginManifestHelper
                                                        .getWorkspaceModelsFor(bundleId);
                                        int noOfPlugins = pluginModels.size();
                                        if (noOfPlugins > 1) {
                                            StringBuilder sb =
                                                    new StringBuilder();
                                            int processed = 0;
                                            for (IPluginModelBase pm : pluginModels) {
                                                IResource underlyingResource =
                                                        pm
                                                                .getUnderlyingResource();
                                                if (underlyingResource != null
                                                        && underlyingResource
                                                                .getProject() != null) {
                                                    sb
                                                            .append('\'')
                                                            .append(underlyingResource
                                                                    .getProject()
                                                                    .getName())
                                                            .append('\'');
                                                    processed++;
                                                    if (processed != noOfPlugins) {
                                                        sb.append(", "); //$NON-NLS-1$
                                                    }
                                                }
                                            }
                                            addIssue(ISSUE_MANY_PROJECTS_WITH_THE_SAME_ID,
                                                    activity,
                                                    Arrays
                                                            .asList(sb
                                                                    .toString(),
                                                                    bundleId));

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
