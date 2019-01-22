/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.brm.validation.rules;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.osgi.framework.Version;

import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Validates a project to confirm all OMs within the scope of the project are of
 * the same major version number.
 * 
 * @author patkinso
 * @since 25 Jul 2012
 */
public class EnsureSameMajorVersionOMRule implements WorkspaceResourceValidator {

    private static final String DUPLICATE_VERSION_ID =
            "brm.ensureSameMajorVersionOM"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      org.eclipse.core.resources.IResource)
     * 
     * @param scope
     * @param resource
     */
    public void validate(IValidationScope scope, IResource resource) {

        if (resource instanceof IProject && resource.isAccessible()) {
            IProject project = (IProject) resource;

            Map<String, String> map = BRMUtils.getReferencedOMVersions(project);
            Set<Entry<String, String>> sortedVersionMapEntries =
                    getMapAsSortedEntries(map);

            // determine whether multiple major version numbers have been used
            // for referenced OMs
            boolean failed = false;
            Integer refVersion = null;
            for (Map.Entry<String, String> entry : sortedVersionMapEntries) {
                Integer version = (new Version(entry.getValue())).getMajor();

                if (refVersion == null) {
                    refVersion = version;
                }

                if (!refVersion.equals(version)) {
                    failed = true;
                    break;
                }
            }

            // if multiple major version numbers raise issue
            if (failed) {

                // create list of full versions for issue message
                final String separator = ", "; //$NON-NLS-1$
                StringBuilder listOfVersions = new StringBuilder();
                for (Map.Entry<String, String> entry : sortedVersionMapEntries) {
                    listOfVersions.append(entry.getValue()).append(separator);
                }
                int endIndex = listOfVersions.lastIndexOf(separator);
                listOfVersions.setLength(endIndex);

                scope.createIssue(DUPLICATE_VERSION_ID,
                        project.getName(),
                        project.getProjectRelativePath().toString(),
                        Arrays.asList(new String[] { project.getName(),
                                listOfVersions.toString() }));
            }

        }
    }

    /**
     * Sorts map by its values
     * 
     * @param map
     * @return sorted set of map entries
     */
    protected Set<Entry<String, String>> getMapAsSortedEntries(
            Map<String, String> map) {

        Set<Map.Entry<String, String>> sortedEntries =
                new TreeSet<Map.Entry<String, String>>(
                        new Comparator<Map.Entry<String, String>>() {

                            public int compare(Entry<String, String> o1,
                                    Entry<String, String> o2) {
                                int ret =
                                        o1.getValue().compareTo(o2.getValue());
                                return (ret != 0) ? ret : 1;
                            }
                        });

        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    /**
     * @see com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    public void setProject(IProject project) {
        // TODO Auto-generated method stub

    }

}
