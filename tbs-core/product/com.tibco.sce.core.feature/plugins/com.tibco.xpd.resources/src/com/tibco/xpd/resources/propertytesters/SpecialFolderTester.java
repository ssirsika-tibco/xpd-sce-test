/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.propertytesters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Property Testers for Special Folder. The tests include:
 * <ul>
 * <li><b>inSpecialFolder</b>: Tests whether the given <code>IResource</code> is
 * contained within a given <code>SpecialFolder</code>.</li>
 * <li><b>containsSpecialFolder</b>: Tests whether the given
 * <code>IContainer</code> contains a folder that is marked as a special folder.
 * </li>
 * <li><b>isRootSpecialFolder</b>: Tests whether the root of the
 * <code>IResource</code> is a <code>SpecialFolder</code>.</li>
 * <li><b>isSpecialFolderNotEmpty</b>: Tests whether the given
 * <code>IResource</code> is a <code>SpecialFolder</code> and is not empty.</li>
 * </ul>
 * <p>
 * If the kind of <code>SpecialFolder</code> is provided (through the value
 * input in the extension point) then a further test will be carried out to
 * match the kind to the special folder, otherwise the tests will be against any
 * special folders.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class SpecialFolderTester extends PropertyTester {

    /**
     * Test to check if an IResource is contained in a SpecialFolder
     */
    public static final String PROP_INSPECIALFOLDER = "inSpecialFolder"; //$NON-NLS-1$

    /**
     * Test to check if an IResource is contained in a Generated Special Folder
     */
    public static final String PROP_ISINGENERATEDSPECIALFOLDER =
            "isInGeneratedSpecialFolder"; //$NON-NLS-1$

    /**
     * Test to check if an IContainer contains a SpecialFolder
     */
    public static final String PROP_CONTAINSSPECIALFOLDER =
            "containsSpecialFolder"; //$NON-NLS-1$

    /**
     * Test to check if a SpecialFolder is the root of an IResource
     */
    public static final String PROP_ISROOTSPECIALFOLDER = "isRootSpecialFolder"; //$NON-NLS-1$

    /**
     * To test whether a container contains a special folder of a given kind
     * (value) and has files of the given file extension (parameter)
     */
    public static final String PROP_ISSPECIALFOLDERNOTEMPTY =
            "isSpecialFolderNotEmpty"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.PropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        boolean passed = false;

        IResource resource = null;
        if (receiver instanceof IResource) {
            resource = (IResource) receiver;
        } else if (receiver instanceof SpecialFolder) {
            resource = ((SpecialFolder) receiver).getFolder();
        } else if (receiver instanceof EObject) {
            resource = WorkingCopyUtil.getFile((EObject) receiver);
        }

        if (resource != null) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(resource.getProject());

            if (config != null && config.getSpecialFolders() != null) {

                SpecialFolder sf = null;

                if (property.equalsIgnoreCase(PROP_ISROOTSPECIALFOLDER)
                        || property.equalsIgnoreCase(PROP_INSPECIALFOLDER)) {

                    if (property.equalsIgnoreCase(PROP_ISROOTSPECIALFOLDER)) {

                        sf =
                                config.getSpecialFolders()
                                        .getFolderContainer(resource);

                    } else if (property.equalsIgnoreCase(PROP_INSPECIALFOLDER)) {

                        IContainer container = resource.getParent();

                        /*
                         * Check if the container of this resource is a special
                         * folder
                         */
                        if (container instanceof IFolder) {
                            sf =
                                    config.getSpecialFolders()
                                            .getFolder((IFolder) container);
                        }
                    }
                    /*
                     * If we have a special folder then check it's kind if an
                     * expected value was provided, otherwise if there is a
                     * special folder then the test passed
                     */
                    if (sf != null) {

                        if (expectedValue != null) {
                            // Check that the kind value is valid
                            if (config.getSpecialFolders()
                                    .getFolderKindInfo((String) expectedValue) != null) {
                                passed = sf.getKind().equals(expectedValue);
                            }
                        } else {
                            passed = true;
                        }
                    }
                } else if (PROP_ISINGENERATEDSPECIALFOLDER
                        .equalsIgnoreCase(property)) {

                    IContainer container = resource.getParent();
                    if (container instanceof IFolder) {
                        sf =
                                config.getSpecialFolders()
                                        .getFolder((IFolder) container);
                        if (null != sf) {

                            String generated = sf.getGenerated();
                            if (null != generated && generated.length() > 0) {

                                passed = true;
                            }
                        }
                    }
                } else {
                    /*
                     * Check all special folders in the container given. If any
                     * of the special folders found match the given value then
                     * return passed
                     */
                    if (property.equalsIgnoreCase(PROP_CONTAINSSPECIALFOLDER)) {
                        if (resource instanceof IContainer) {
                            IProject project = resource.getProject();

                            if (project != null) {
                                EList folders = null;

                                if (expectedValue != null) {
                                    // Expected value provided so get special
                                    // folders of that kind
                                    folders =
                                            config.getSpecialFolders()
                                                    .getFoldersOfKind(expectedValue
                                                            .toString());
                                } else {
                                    // No expected value provided so get all
                                    // special folders
                                    folders =
                                            config.getSpecialFolders()
                                                    .getFolders();
                                }

                                if (folders != null) {
                                    /*
                                     * Check if any of the special folder's
                                     * resource is a child of this container
                                     */
                                    for (Iterator<?> iter = folders.iterator(); iter
                                            .hasNext() && sf == null;) {
                                        SpecialFolder folder =
                                                (SpecialFolder) iter.next();
                                        if (folder.getFolder() != null
                                                && folder.getFolder()
                                                        .getParent()
                                                        .equals(resource)) {
                                            passed = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } else if (property.equals(PROP_ISSPECIALFOLDERNOTEMPTY)) {
                        /*
                         * Check for the provided special folders in the
                         * container given. If the special folders match the
                         * given value then check if it has any file for special
                         * folder kind then return passed
                         */
                        if (resource instanceof IContainer && args.length > 0
                                && expectedValue != null) {
                            IProject project = resource.getProject();

                            if (null != project) {
                                /*
                                 * Expected value provided so get special
                                 * folders of that kind
                                 */
                                EList<SpecialFolder> specialFolders =
                                        config.getSpecialFolders()
                                                .getFoldersOfKind(expectedValue.toString());
                                /*
                                 * check for the members of the special folder
                                 * has any files in it
                                 */
                                outer: for (SpecialFolder specialFolder : specialFolders) {
                                    if (specialFolder.getFolder() != null
                                            && specialFolder.getFolder()
                                                    .isAccessible()) {
                                        for (Object arg : args) {
                                            if (arg instanceof String) {
                                                try {
                                                    if (hasFileWithExtension(specialFolder
                                                            .getFolder(),
                                                            (String) arg)) {
                                                        passed = true;
                                                        break outer;
                                                    }
                                                } catch (CoreException e) {
                                                    XpdResourcesPlugin
                                                            .getDefault()
                                                            .getLogger()
                                                            .error(e,
                                                                    "Error in Special Folder Property Tester"); //$NON-NLS-1$
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

        return passed;
    }

    /**
     * Check if the given container (or any subfolders) has a file with the
     * given file extension.
     * 
     * 
     * @param container
     * @param fileExt
     * @return
     * @throws CoreException
     */
    private boolean hasFileWithExtension(IContainer container, String fileExt)
            throws CoreException {

        List<IFolder> folders = new ArrayList<IFolder>();

        for (IResource res : container.members()) {
            if (res instanceof IFile) {
                if (res.getFileExtension() != null
                        && res.getFileExtension().equalsIgnoreCase(fileExt)) {
                    return true;
                }
            } else if (res instanceof IFolder) {

                /*
                 * Saket XPD-4521: add the folder to the 'folders' list
                 */
                folders.add((IFolder) res);
            }
        }

        /*
         * Saket XPD-4521: if folders list is not empty, iterate through
         * 'folders' list calling hasFileWithExtension(...) on each folder. This
         * way we make sure that we always check the contents of the special
         * folder first to see if it contains the relevant files. If it doesn’t,
         * then only we process any sub-folders.
         */
        if (!folders.isEmpty()) {
            for (IFolder currentFolder : folders) {
                if (hasFileWithExtension(currentFolder, fileExt)) {
                    return true;
                }
            }
        }
        return false;
    }
}
