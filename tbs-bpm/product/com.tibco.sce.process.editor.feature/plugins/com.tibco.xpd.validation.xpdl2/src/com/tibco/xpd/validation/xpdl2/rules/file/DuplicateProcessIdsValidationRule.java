/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.rules.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Validation rule that validate duplicate processIds in the workspace.
 * 
 * @author wzurek
 */
public class DuplicateProcessIdsValidationRule implements IValidationRule {

    private Set<String> sourceProcessIds;

    private WorkingCopy wc;

    private List<WorkingCopy> duplicates;

    /*
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    @Override
    public Class getTargetClass() {
        return Package.class;
    }

    /*
     * @see
     * com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd
     * .validation.provider.IValidationScope, java.lang.Object)
     */
    @Override
    public void validate(IValidationScope scope, Object o) {

        if (sourceProcessIds != null) {
            return;
        }

        try {
            duplicates = new ArrayList<WorkingCopy>();
            sourceProcessIds = new HashSet<String>();
            Package pck = (Package) o;
            wc = WorkingCopyUtil.getWorkingCopyFor(pck);
            String processIds =
                    wc.getCachedAttribute(Xpdl2WorkingCopyImpl.PROCESS_IDS_CACHED_ATT);
            StringTokenizer tokenizer =
                    new StringTokenizer(processIds,
                            Xpdl2WorkingCopyImpl.PROCESS_ID_SEPARATOR);
            boolean invalid = false;
            while (tokenizer.hasMoreTokens()) {
                String it = tokenizer.nextToken();
                if (!sourceProcessIds.contains(it)) {
                    sourceProcessIds.add(it);
                } else {
                    invalid = true;
                    break;
                }
            }
            if (invalid) {
                scope.createIssue("com.tibco.xpd.validation.xpdl2.duplicateIDsInTheFile", wc //$NON-NLS-1$
                                .getEclipseResources().get(0)
                                .getProjectRelativePath().toString(),
                        "", //$NON-NLS-1$
                        Arrays.asList(wc.getEclipseResources().get(0).getName()));
            } else {

                compareWithFilesInWorkspace();
                if (duplicates.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (WorkingCopy dwc : duplicates) {
                        if (sb.length() > 0) {
                            sb.append("; "); //$NON-NLS-1$
                        }
                        sb.append(dwc.getEclipseResources().get(0)
                                .getFullPath().toPortableString());
                    }
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("VALIDATION_DEPENDENCY", sb.toString()); //$NON-NLS-1$

                    //
                    // Validation now allows us to add implicit dependencies to
                    // other resources during validation.
                    // Setting up the LINKED_RESOURCE attribute will force a
                    // re-validation of the other package.
                    StringBuilder uris = new StringBuilder();
                    for (WorkingCopy dwc : duplicates) {
                        if (uris.length() > 0) {
                            uris.append(","); //$NON-NLS-1$
                        }
                        uris.append(URI.createPlatformResourceURI(dwc
                                .getEclipseResources().get(0).getFullPath()
                                .toPortableString(),
                                true).toString());

                    }

                    map.put(ValidationActivator.LINKED_RESOURCE,
                            uris.toString());

                    scope.createIssue("com.tibco.xpd.validation.xpdl2.duplicateIDs", wc //$NON-NLS-1$
                                    .getEclipseResources().get(0)
                                    .getProjectRelativePath().toString(),
                            "", //$NON-NLS-1$
                            Arrays.asList(sb.toString()),
                            map);
                }
            }
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            wc = null;
            sourceProcessIds = null;
            duplicates = null;
        }
    }

    /**
     * @param xpdlWC
     */
    protected void checkProcesses(Xpdl2WorkingCopyImpl xpdlWC) {
        if (xpdlWC != wc) {
            String processIds =
                    xpdlWC.getCachedAttribute(Xpdl2WorkingCopyImpl.PROCESS_IDS_CACHED_ATT);
            if (processIds != null) {
                StringTokenizer tokenizer =
                        new StringTokenizer(processIds,
                                Xpdl2WorkingCopyImpl.PROCESS_ID_SEPARATOR);
                while (tokenizer.hasMoreTokens()) {
                    String it = tokenizer.nextToken();
                    if (sourceProcessIds.contains(it)) {
                        duplicates.add(xpdlWC);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Compare the source package's process ids with other process packages on
     * the system.
     * 
     * @param runnable
     * @throws CoreException
     */
    @SuppressWarnings("unchecked")
    private void compareWithFilesInWorkspace() throws CoreException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject[] projects = root.getProjects();
        for (IProject project : projects) {
            try {
                if (project.isOpen()
                        && project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                    compareWithFilesInProject(project);
                }
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }
        }
    }

    /**
     * Compare the process ids in the source package with all other packages in
     * given project.
     * <p>
     * This will compare with file resource whose WOrking Copy is an
     * {@link Xpdl2WorkingCopyImpl}. This <i>should</i> be any file in the
     * workspace that is an xpdl model within a special folder of any kind and
     * regardless of the actual file extension.
     * 
     * @param project
     * @throws CoreException
     */

    private void compareWithFilesInProject(IProject project)
            throws CoreException {

        /*
         * Don't go thru EVERY file in project THEN check if it is in a special
         * folder.
         * 
         * This is a lot of work (especially after export of DAA's which can
         * create 20x or more the user-files.
         * 
         * Instead use the appropriate util that goes from special folders-down
         * which is much more efficient.
         */
        List<IResource> allXpdl =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderOfKind(project,
                                Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND,
                                Xpdl2ResourcesConsts.XPDL_EXTENSION,
                                false);

        if (allXpdl != null) {
            for (IResource xpdl : allXpdl) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdl);
                if (wc instanceof Xpdl2WorkingCopyImpl) {
                    checkProcesses((Xpdl2WorkingCopyImpl) wc);
                }
            }
        }

    }

}
