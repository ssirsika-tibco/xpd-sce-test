/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.daa.internal.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Utility class for composite
 * 
 * @author mtorres
 * 
 */
public final class CompositeUtil {

    /** HttpClient resource type. */
    public static final QName HTTP_CLIENT_RES_TYPE = new QName(
            "http://xsd.tns.tibco.com/amf/models/sharedresource/httpclient", //$NON-NLS-1$
            "HttpClientConfiguration"); //$NON-NLS-1$

    /** JDBC resource type. */
    public static final QName JDBC_RES_TYPE = new QName(
            "http://xsd.tns.tibco.com/amf/models/sharedresource/jdbc", //$NON-NLS-1$
            "JdbcDataSource"); //$NON-NLS-1$

    /** SMTP resource type. */
    public static final QName SMTP_RES_TYPE = new QName(
            "http://xsd.tns.tibco.com/amf/models/sharedresource/smtp", //$NON-NLS-1$
            "SmtpConfiguration"); //$NON-NLS-1$

    /** String resource type. */
    public static final QName STRING_RES_TYPE = new QName(
            "http://www.w3.org/2001/XMLSchema", //$NON-NLS-1$
            "string"); //$NON-NLS-1$

    /** JMS connection factory resource type. */
    public static final QName JMS_CONNECTION_FACTORY_RES_TYPE = new QName(
            "http://xsd.tns.tibco.com/amf/models/sharedresource/jms", //$NON-NLS-1$
            "JMSConnectionFactory"); //$NON-NLS-1$

    /** JMS connection factory configuration resource type. */
    public static final QName JMS_CONNECTION_FACTORY_CONFIGURATION_RES_TYPE =
            new QName("http://xsd.tns.tibco.com/amf/models/sharedresource/jms", //$NON-NLS-1$
                    "JMSConnectionFactoryConfiguration"); //$NON-NLS-1$

    /** JMS destination resource type. */
    public static final QName JMS_DESTINATION_RES_TYPE = new QName(
            "http://xsd.tns.tibco.com/amf/models/sharedresource/jms", //$NON-NLS-1$
            "JMSDestination"); //$NON-NLS-1$

    /** JMS destination configuration resource type. */
    public static final QName JMS_DESTINATION_CONFIGURATION_RES_TYPE =
            new QName("http://xsd.tns.tibco.com/amf/models/sharedresource/jms", //$NON-NLS-1$
                    "JMSDestinationConfiguration"); //$NON-NLS-1$

    public static final Pattern SVAR_PATTERN = Pattern.compile("^%%.*%%$"); //$NON-NLS-1$

    /** Collection of resource type that should be substituted. */
    public static final Collection<QName> SUBSTITUABLE_PROPERTY_TYPES =
            Collections.unmodifiableCollection(Arrays
                    .asList(HTTP_CLIENT_RES_TYPE,
                            JDBC_RES_TYPE,
                            SMTP_RES_TYPE,
                            STRING_RES_TYPE,
                            JMS_CONNECTION_FACTORY_RES_TYPE,
                            JMS_CONNECTION_FACTORY_CONFIGURATION_RES_TYPE,
                            JMS_DESTINATION_RES_TYPE,
                            JMS_DESTINATION_CONFIGURATION_RES_TYPE));

    public static final String DEFAULT_PROJECT_VERSION = "1.0.0.qualifier"; //$NON-NLS-1$

    public static String getCompositeName(IProject project) {
        return CompositeUtil.getProjectId(project) + ".composite"; //$NON-NLS-1$
    }

    public static String getProjectId(IProject project) {
        ProjectDetails projectDetails =
                CompositeUtil.getProjectDetails(project);
        if (projectDetails != null) {
            return projectDetails.getId();
        }
        return null;
    }

    private static ProjectDetails getProjectDetails(IProject project) {
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (projectConfig != null) {
            return projectConfig.getProjectDetails();
        }
        return null;
    }

    public static String getVersionNumber(IProject project) {
        String projectVersion = ProjectUtil.getProjectVersion(project);
        if (projectVersion != null && projectVersion.trim().length() > 0) {
            return projectVersion;
        }
        return DEFAULT_PROJECT_VERSION;
    }

    /**
     * For a given Studio Project tells whether there are error level problem
     * markers on it or on referenced projects.
     * 
     * @param studioProject
     * @return
     * @throws CoreException
     */
    public static boolean hasErrorLevelProblemMarkers(IProject studioProject)
            throws CoreException {
        return hasErrorLevelProblemMarkers(Collections.singleton(studioProject));
    }

    /**
     * For a given collection of Studio Projects tells whether there are error
     * level problem markers on it or their referenced projects.
     * 
     * @param studioProjects
     *            collection of projects.
     * @return true if one of the studioProjects or one of their referenced
     *         project has error marker.
     * @throws CoreException
     */
    public static boolean hasErrorLevelProblemMarkers(
            Collection<IProject> studioProjects) throws CoreException {
        Set<IProject> projectsHierarchy = new HashSet<IProject>();
        for (IProject studioProject : studioProjects) {
            Set<IProject> referencedProjectsHierarchy = new HashSet<IProject>();
            ProjectUtil.getReferencedProjectsHierarchy(studioProject,
                    referencedProjectsHierarchy);
            projectsHierarchy.addAll(referencedProjectsHierarchy);
            projectsHierarchy.add(studioProject);
        }
        boolean errorLevelProblemMarker = false;
        for (IProject eachProject : projectsHierarchy) {
            if (!eachProject.isAccessible()) {
                continue;
            }
            IMarker[] markers =
                    eachProject.findMarkers(null,
                            true,
                            IResource.DEPTH_INFINITE);
            for (int i = 0; i < markers.length; i++) {
                IMarker marker = markers[i];
                int severity =
                        marker.getAttribute(IMarker.SEVERITY,
                                IMarker.SEVERITY_WARNING);
                if (severity == IMarker.SEVERITY_ERROR) {
                    errorLevelProblemMarker = true;
                    // no need to check further
                    return errorLevelProblemMarker;
                }
            }
        }
        return errorLevelProblemMarker;
    }

    public static String getCompositeBaseName(IProject project) {
        return CompositeUtil.getProjectId(project);
    }

    public static IFile getFileFromWorkspace(Path path) {
        return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
    }

}