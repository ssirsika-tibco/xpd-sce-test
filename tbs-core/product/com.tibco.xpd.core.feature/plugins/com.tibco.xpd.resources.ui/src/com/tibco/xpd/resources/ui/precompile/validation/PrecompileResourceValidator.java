/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.resources.ui.precompile.validation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.precompile.PreCompileContributorManager;
import com.tibco.xpd.resources.precompile.PreCompileUtil;
import com.tibco.xpd.resources.precompile.PrecompileResourceVisitor;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator2;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Validator class that validates changes in the workspace. If the change (ADD /
 * REMOVE / UPDATE) is on a source resource that contributes to pre-compile then
 * a problem marker is raised.
 * 
 * @author bharge
 * @since 15 May 2015
 */
public class PrecompileResourceValidator extends WorkspaceResourceValidator2 {

    IProject project;

    private static final String ADDED_NEW_RESOURCE_ISSUE_ID =
            "precompile.addedNewResource"; //$NON-NLS-1$

    private static final String DELETED_EXISTING_RESOURCE_ISSUE_ID =
            "precompile.deletedExistingResource"; //$NON-NLS-1$

    private static final String CHANGED_EXISTING_RESOURCE_ISSUE_ID =
            "precompile.changedExistingResource"; //$NON-NLS-1$

    private static final String CHANGED_PROJECT_DETAILS_ISSUE_ID =
            "precompile.changedProjectDetails"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator2#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      org.eclipse.core.resources.IResource, boolean)
     * 
     * @param scope
     * @param resource
     * @param isReferencingResourceValidation
     */
    @Override
    public void validate(IValidationScope scope, IResource resource,
            boolean isReferencingResourceValidation) {

        /*
         * Check if the changed resource is a source resource enabled for
         * pre-compile.
         * 
         * Don't bother validating if we are called to validate a project that
         * is being validated just because it referenced a changed
         * resource/project (we're only interested in our own project's content,
         * referenced project contents do not affect our result).
         */

        if (project.isAccessible() && ProjectUtil.isPrecompiledProject(project)
                && !isReferencingResourceValidation) {

            /*
             * Visit the project resources to create a new map of source
             * resource and check sum
             */
            PrecompileResourceVisitor precompileResVisitor =
                    new PrecompileResourceVisitor();

            try {

                project.accept(precompileResVisitor,
                        IResource.DEPTH_INFINITE,
                        false);
                /*
                 * Get the new map of source resource and checksum
                 */
                Map<String, String> newSourceResChecksumMap =
                        precompileResVisitor.getSourceResChecksumMap();

                try {

                    /*
                     * Get the properties file (having workspace relative path
                     * (key) = checksum (value) pairs) saved under .precompiled
                     * folder
                     */
                    IFolder preCompileFolder =
                            project.getFolder(PreCompileContributorManager
                                    .getInstance().PRECOMPILE_OUTPUTFOLDER_NAME);
                    String fileName =
                            PreCompileContributorManager.getInstance().SOURCE_PRECOMPILE_RESOURCES_PROPERTIES;

                    if (preCompileFolder.exists()) {

                        IFile propertiesFile =
                                preCompileFolder.getFile(fileName);
                        if (propertiesFile.exists()) {

                            InputStream contents = propertiesFile.getContents();
                            Properties properties = new Properties();

                            properties.load(contents);
                            contents.close();

                            /* Create a hash map from the properties file */
                            Map<String, String> oldSrcResChecksumMap =
                                    getOldSrcResChecksumMap(properties);

                            /*
                             * Compare the old hash map (created from properties
                             * file) with the current hash map
                             */
                            comparePrecompileSourceChecksumWithCurrent(scope,
                                    oldSrcResChecksumMap,
                                    newSourceResChecksumMap);

                            /*
                             * Add the checksum check on project's id, version
                             * and destination. (If a decisions project
                             * references a business data project, then we have
                             * to add decisions destination on business data
                             * project. If the business data project is already
                             * pre-compiled, then we want to validate the
                             * addition of destination as a change.
                             */
                            String newProjectDetailsChecksumValue =
                                    PreCompileUtil
                                            .getProjectDetailsChecksumValue(project);

                            String oldChecksumValue_ProjectDetails =
                                    properties
                                            .getProperty(PreCompileUtil.PROJECT_DETAILS_CHECK_SUM_KEY);

                            if (!newProjectDetailsChecksumValue
                                    .equals(oldChecksumValue_ProjectDetails)) {

                                createIssue(CHANGED_PROJECT_DETAILS_ISSUE_ID,
                                        scope,
                                        project.getName());
                            }
                        }
                    } else {

                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .info(".precompile folder does not exist. Could not get the checksum properties file"); //$NON-NLS-1$
                    }

                } catch (FileNotFoundException e) {

                    XpdResourcesPlugin
                            .getDefault()
                            .getLogger()
                            .error(e, "Error validating .precompiled resources"); //$NON-NLS-1$
                } catch (IOException e) {

                    XpdResourcesPlugin
                            .getDefault()
                            .getLogger()
                            .error(e, "Error validating .precompiled resources"); //$NON-NLS-1$
                }

            } catch (CoreException e1) {

                XpdResourcesPlugin.getDefault().getLogger()
                        .error(e1, "Error validating .precompiled resources"); //$NON-NLS-1$
            }

        }
    }

    /**
     * Iterate thru the old map of source resource and checksum from the
     * properties file created during pre-compile and compare with the new map.
     * Raise problem marker for any discrepancies
     * 
     * If the new map has any key that doesn't exist in the old map then that
     * means it is ADD.
     * 
     * If the new map does not have any key that exists in the old map then that
     * means it is DELETE.
     * 
     * If the new map and old map has got same key but different value then that
     * means it is UPDATE.
     * 
     * @param scope
     * @param oldSrcResChecksumMap
     * @param newSourceResChecksumMap
     */
    private void comparePrecompileSourceChecksumWithCurrent(
            IValidationScope scope, Map<String, String> oldSrcResChecksumMap,
            Map<String, String> newSourceResChecksumMap) {

        Set<String> oldKeySet = oldSrcResChecksumMap.keySet();
        Set<String> newKeySet = newSourceResChecksumMap.keySet();

        /* Iterate thru the old map to find for deletions or modifications */
        for (String oldKey : oldKeySet) {

            String oldValue = oldSrcResChecksumMap.get(oldKey);
            String newValue = newSourceResChecksumMap.get(oldKey);

            String resourceName =
                    oldKey.substring(oldKey.lastIndexOf("/") + 1, oldKey.length()); //$NON-NLS-1$
            /*
             * If the new map does not have the key that exists in the old, that
             * means that resource has been deleted after pre-compile is enabled
             */
            if (!newSourceResChecksumMap.containsKey(oldKey)) {

                /* DELETED */
                createIssue(DELETED_EXISTING_RESOURCE_ISSUE_ID,
                        scope,
                        resourceName);
            }
            /*
             * Key exists in the new map but the value differs, that means
             * resource is modified after pre-compile is enabled
             */
            if (null != newValue && !oldValue.equalsIgnoreCase(newValue)) {

                /* UPDATED */
                createIssue(CHANGED_EXISTING_RESOURCE_ISSUE_ID,
                        scope,
                        resourceName);
            }
        }

        /* Iterate thru the new map to find for additions */
        for (String newKey : newKeySet) {

            String resourceName =
                    newKey.substring(newKey.lastIndexOf("/") + 1, newKey.length()); //$NON-NLS-1$
            /*
             * If a key exists in new map but doesn't exist in the old, that
             * means a new resource has been added after pre-compile is enabled
             */
            if (!oldSrcResChecksumMap.containsKey(newKey)) {

                /* ADDED */
                createIssue(ADDED_NEW_RESOURCE_ISSUE_ID, scope, resourceName);
            }
        }
    }

    /**
     * From the given properties file create a hash map
     * 
     * @param properties
     * @return {@link HashMap} of key value pairs from the given properties file
     */
    private Map<String, String> getOldSrcResChecksumMap(Properties properties) {

        Map<String, String> oldSrcResChecksumMap =
                new HashMap<String, String>();
        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {

            String oldKey = (String) propertyNames.nextElement();
            /*
             * Do not add the project details check sum key value pair, because
             * it is not specific to any source resource but for the whole
             * project.
             */
            if (PreCompileUtil.PROJECT_DETAILS_CHECK_SUM_KEY.equals(oldKey)) {

                continue;
            }
            String oldValue = properties.getProperty(oldKey);
            oldSrcResChecksumMap.put(oldKey, oldValue);
        }
        return oldSrcResChecksumMap;
    }

    /**
     * Create the issue with the given issue id
     * 
     * @param issueId
     * @param scope
     * @param resource
     */
    private void createIssue(String issueId, IValidationScope scope,
            String resourceName) {

        scope.createIssue(issueId,
                project.getName(),
                project.getName(),
                Arrays.asList(new String[] { resourceName }));
    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator2#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    @Override
    public void setProject(IProject project) {

        this.project = project;
    }

}
