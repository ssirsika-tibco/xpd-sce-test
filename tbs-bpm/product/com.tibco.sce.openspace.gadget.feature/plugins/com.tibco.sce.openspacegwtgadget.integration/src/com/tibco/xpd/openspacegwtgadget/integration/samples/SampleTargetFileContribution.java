/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.samples;

import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;

/**
 * Abstract base class and its implementations representing the TargetFile and
 * TargetPackageFile elements in a contribution to the
 * com.tibco.xpd.openspacegwtgadget.integration.OpenspaceSampleCreator extension
 * point.
 * 
 * @author aallway
 * @since 21 Jan 2013
 */
abstract class SampleTargetFileContribution {

    public static final String TOKEN_END_MARKER = "}"; //$NON-NLS-1$

    public static final String TOKEN_START_MARKER = "{"; //$NON-NLS-1$

    protected final String contributingPluginId;

    protected OpenspaceSampleOverwritePolicy overwritePolicy;

    /**
     * 
     * @param contributionElement
     * @throws Exception
     *             Error loading detail from plugin contribution.
     */
    SampleTargetFileContribution(IConfigurationElement contributionElement)
            throws Exception {
        contributingPluginId = contributionElement.getContributor().getName();

        overwritePolicy = null;
        try {
            overwritePolicy =
                    OpenspaceSampleOverwritePolicy.valueOf(contributionElement
                            .getAttribute("overwritePolicy")); //$NON-NLS-1$

        } catch (Exception e) {
            throw new Exception(
                    String.format("Invalid 'overwritePolicy' attribute (%1$s) contributed by plugin: %2$s", //$NON-NLS-1$
                            contributionElement.getAttribute("overwritePolicy"), //$NON-NLS-1$
                            contributingPluginId));
        }

    }

    /**
     * 
     * @param targetPackage
     * @param variableProperties
     *            There are currently two variable properties that can be used
     *            in source/target paths by placing them with { } delimiters.
     *            The currently available variable properties are are
     * 
     *            <li>
     *            {@link #PROPERTY_SAMPLE_NAME} ( {@value #PROPERTY_SAMPLE_NAME}
     *            ): The gadget tokenised name (valid for java names etc set by
     *            user during creation (initially set by ext point contribution.
     *            </li>
     * 
     *            <li>
     *            {@link #PROPERTY_SAMPLE_LABEL} (
     *            {@value #PROPERTY_SAMPLE_LABEL} ): The gadget label set by
     *            user during creation (initially set by ext point contribution.
     *            </li>
     * 
     *            <li>
     *            {@link OpenspaceCreateSampleWizard#PROPERTY_TARGET_PACKAGE_NAME}
     *            (
     *            {@value OpenspaceCreateSampleWizard#PROPERTY_TARGET_PACKAGE_NAME}
     *            ): The GWT gadget source package selected as a destination for
     *            new sample</li>
     * 
     * @return
     * @throws Exception
     */
    protected abstract IContainer createTargetContainer(
            IPackageFragment targetPackage,
            Map<String, String> variableProperties) throws Exception;

    /**
     * 
     * @param variableProperties
     *            There are currently two variable properties that can be used
     *            in source/target paths by placing them with { } delimiters.
     *            The currently available variable properties are are
     * 
     *            <li>
     *            {@link #PROPERTY_SAMPLE_NAME} ( {@value #PROPERTY_SAMPLE_NAME}
     *            ): The gadget tokenised name (valid for java names etc set by
     *            user during creation (initially set by ext point contribution.
     *            </li>
     * 
     *            <li>
     *            {@link #PROPERTY_SAMPLE_LABEL} (
     *            {@value #PROPERTY_SAMPLE_LABEL} ): The gadget label set by
     *            user during creation (initially set by ext point contribution.
     *            </li>
     * 
     *            <li>
     *            {@link OpenspaceCreateSampleWizard#PROPERTY_TARGET_PACKAGE_NAME}
     *            (
     *            {@value OpenspaceCreateSampleWizard#PROPERTY_TARGET_PACKAGE_NAME}
     *            ): The GWT gadget source package selected as a destination for
     *            new sample</li>
     * @return Target file name.
     */
    abstract String getTargetFileName(Map<String, String> variableProperties);

    /**
     * 
     * @param targetPackage
     * @param variableProperties
     *            There are currently two variable properties that can be used
     *            in source/target paths by placing them with { } delimiters.
     *            The currently available variable properties are are
     * 
     *            <li>
     *            {@link #PROPERTY_SAMPLE_NAME} ( {@value #PROPERTY_SAMPLE_NAME}
     *            ): The gadget tokenised name (valid for java names etc set by
     *            user during creation (initially set by ext point contribution.
     *            </li>
     * 
     *            <li>
     *            {@link #PROPERTY_SAMPLE_LABEL} (
     *            {@value #PROPERTY_SAMPLE_LABEL} ): The gadget label set by
     *            user during creation (initially set by ext point contribution.
     *            </li>
     * 
     *            <li>
     *            {@link OpenspaceCreateSampleWizard#PROPERTY_TARGET_PACKAGE_NAME}
     *            (
     *            {@value OpenspaceCreateSampleWizard#PROPERTY_TARGET_PACKAGE_NAME}
     *            ): The GWT gadget source package selected as a destination for
     *            new sample</li>
     * 
     * @return The file created.
     * 
     * @throws Exception
     *             Error container for target file.
     */
    IFile getTargetFile(IPackageFragment targetPackage,
            Map<String, String> variableProperties) throws Exception {

        IContainer container =
                createTargetContainer(targetPackage, variableProperties);

        IFile targetFile =
                container.getFile(new Path(
                        getTargetFileName(variableProperties)));

        return targetFile;
    }

    /**
     * @return the overwritePolicy
     */
    public OpenspaceSampleOverwritePolicy getOverwritePolicy() {
        return overwritePolicy;
    }

    /**
     * @param path
     * @param variableProperties
     * 
     * @return the source path with any variable properties tokens (
     *         "{propertyName}" ) in path replaced by the value from the
     *         variableProperties map.
     */
    protected String detokenisePath(String path,
            Map<String, String> variableProperties) {

        StringBuilder returnPath = new StringBuilder();

        StringTokenizer st =
                new StringTokenizer(
                        path,
                        SampleTargetFileContribution.TOKEN_START_MARKER
                                + SampleTargetFileContribution.TOKEN_END_MARKER,
                        true);

        boolean nextIsPropertyToken = false;

        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (nextIsPropertyToken) {
                String property = variableProperties.get(tok);

                if (property != null) {
                    returnPath.append(property);
                }

                nextIsPropertyToken = false;

            } else if (SampleTargetFileContribution.TOKEN_START_MARKER
                    .equals(tok)) {
                nextIsPropertyToken = true;
                ;
            } else if (!SampleTargetFileContribution.TOKEN_END_MARKER
                    .equals(tok)) {
                returnPath.append(tok);
            }
        }

        return returnPath.toString();

    }

    /**
     * * Wrapper for a TargetPackageFile element in openspace sample extension
     * point contribution
     * 
     * 
     * 
     * @author aallway
     * @since 21 Jan 2013
     */
    static class TargetFileContribution extends SampleTargetFileContribution {

        private boolean projectRelative;

        private String targetLocation;

        private String targetFileName;

        /**
         * @param contributionElement
         * @throws Exception
         */
        TargetFileContribution(IConfigurationElement contributionElement)
                throws Exception {
            super(contributionElement);

            String fullLocation =
                    contributionElement.getAttribute("targetLocation"); //$NON-NLS-1$

            if (fullLocation == null || fullLocation.length() == 0) {
                throw new Exception(
                        String.format("TargetFile->targetLocation attribute unspecified in contribution by plugin: %1$s", //$NON-NLS-1$
                                contributingPluginId));
            }

            if (fullLocation.startsWith("/")) { //$NON-NLS-1$
                projectRelative = true;
                fullLocation = fullLocation.substring(1);
            } else {
                projectRelative = false;
            }

            int lastSep = fullLocation.lastIndexOf(IPath.SEPARATOR);
            if (lastSep >= 0) {
                targetLocation = fullLocation.substring(0, lastSep);
                targetFileName = fullLocation.substring(lastSep + 1);

            } else {
                targetLocation = ""; //$NON-NLS-1$
                targetFileName = fullLocation;
            }

        }

        /**
         * @see com.tibco.xpd.openspacegwtgadget.integration.samples.SampleTargetFileContribution#createTargetContainer(org.eclipse.jdt.core.IPackageFragment,
         *      java.util.Map)
         * 
         * @param targetPackage
         * @param variableProperties
         * @return
         * @throws Exception
         */
        @Override
        protected IContainer createTargetContainer(
                IPackageFragment targetPackage,
                Map<String, String> variableProperties) throws Exception {

            try {
                IFolder targetFolder = null;

                if (projectRelative) {

                    IProject targetProject =
                            targetPackage.getResource().getProject();

                    if (targetLocation.length() == 0) {
                        /*
                         * If we're project relative and only have a file name,
                         * then the project itself is the container. So no need
                         * to go any further.
                         */
                        return targetProject;

                    }

                    targetFolder =
                            targetProject.getFolder(new Path(
                                    detokenisePath(targetLocation,
                                            variableProperties)));

                } else {
                    /* Get folder relative to target package. */
                    if (targetLocation.length() == 0) {
                        /*
                         * If we're target package relative and only have a file
                         * name then its the target package itself. So no need
                         * to go further.
                         */
                        return (IContainer) targetPackage.getResource();
                    }

                    targetFolder =
                            ((IContainer) targetPackage.getResource())
                                    .getFolder(new Path(
                                            detokenisePath(targetLocation,
                                                    variableProperties)));
                }

                /* Create the entire folder path if doesn't exist. */
                createTargetFolder(targetFolder);

                return targetFolder;

            } catch (Exception e) {
                throw new Exception(
                        String.format(Messages.SampleTargetFileContribution_CantCreateTargetLocation_message,
                                targetLocation), e);
            }
        }

        /**
         * Recursively create folder structure required for the given folder
         * path.
         * 
         * @param targetFolder
         * @throws CoreException
         */
        private void createTargetFolder(IFolder targetFolder)
                throws CoreException {

            if (!targetFolder.exists()) {
                /*
                 * Recurs up until we get to project
                 */
                IContainer parent = targetFolder.getParent();

                if (parent instanceof IFolder) {
                    createTargetFolder((IFolder) parent);
                }

                targetFolder.create(true, true, new NullProgressMonitor());
            }
        }

        /**
         * @see com.tibco.xpd.openspacegwtgadget.integration.samples.SampleTargetFileContribution#getTargetFileName(java.util.Map)
         * 
         * @param variableProperties
         * @return
         */
        @Override
        protected String getTargetFileName(
                Map<String, String> variableProperties) {
            return detokenisePath(targetFileName, variableProperties);
        }
    }

    /**
     * Wrapper for a TargetPackageFile element in openspace sample extension
     * point contribution
     * 
     * 
     * @author aallway
     * @since 21 Jan 2013
     */
    static class TargetPackageFileContribution extends
            SampleTargetFileContribution {

        private String packageName;

        private String fileName;

        /**
         * @param contributionElement
         * @throws Exception
         */
        TargetPackageFileContribution(IConfigurationElement contributionElement)
                throws Exception {
            super(contributionElement);

            packageName = contributionElement.getAttribute("packageName"); //$NON-NLS-1$

            fileName = contributionElement.getAttribute("fileName"); //$NON-NLS-1$

            if (packageName == null || packageName.length() == 0
                    || fileName == null || fileName.length() == 0) {
                throw new Exception(
                        String.format("TargetPackageFile packageName or fileName attribute unspecified in contribution by plugin: %1$s", //$NON-NLS-1$
                                contributingPluginId));
            }
        }

        /**
         * @see com.tibco.xpd.openspacegwtgadget.integration.samples.SampleTargetFileContribution#createTargetContainer(org.eclipse.jdt.core.IPackageFragment,
         *      java.util.Map)
         * 
         * @param targetPackage
         * @param variableProperties
         * @return
         * @throws Exception
         */
        @Override
        protected IContainer createTargetContainer(
                IPackageFragment targetPackage,
                Map<String, String> variableProperties) throws Exception {

            String finalPackageName =
                    detokenisePath(packageName, variableProperties);

            /* Package id is package root relative. */
            IPackageFragmentRoot root = getPackageRoot(targetPackage);

            if (root == null) {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                OpenspaceGadgetPlugin.PLUGIN_ID,
                                String.format(Messages.SampleTargetFileContribution_CantFindPackageRoot_message,
                                        targetPackage.getElementName())));
            }

            try {
                IPackageFragment packageFragment =
                        root.getPackageFragment(finalPackageName);

                if (packageFragment == null || !packageFragment.exists()) {
                    packageFragment =
                            root.createPackageFragment(finalPackageName,
                                    true,
                                    new NullProgressMonitor());

                }

                if (packageFragment != null) {
                    return (IContainer) packageFragment.getResource();
                }

                throw new Exception(
                        String.format(Messages.SampleTargetFileContribution_CantCreateDestinationPackage_message,
                                finalPackageName));

            } catch (JavaModelException e) {
                throw new Exception(
                        String.format(Messages.SampleTargetFileContribution_CantCreateDestinationPackage_message,
                                finalPackageName), e);

            }
        }

        /**
         * @param targetPackage
         * @return Package fragment root of given source package
         */
        private IPackageFragmentRoot getPackageRoot(
                IPackageFragment targetPackage) {
            IJavaElement parent = targetPackage.getParent();

            while (parent != null) {
                if (parent instanceof IPackageFragmentRoot) {
                    return (IPackageFragmentRoot) parent;
                }
                parent = parent.getParent();
            }
            return null;
        }

        /**
         * @see com.tibco.xpd.openspacegwtgadget.integration.samples.SampleTargetFileContribution#getTargetFileName(java.util.Map)
         * 
         * @param variableProperties
         * @return
         */
        @Override
        protected String getTargetFileName(
                Map<String, String> variableProperties) {
            return detokenisePath(fileName, variableProperties);
        }

    }

    /**
     * Enumeration describing action to take when openspace sample file already
     * exists.
     * 
     * @author aallway
     * @since 16 Jan 2013
     */
    static enum OpenspaceSampleOverwritePolicy {
        /**
         * Prompt user with Yes / No / Yes To All /No To All
         */
        prompt,

        /**
         * Leave existing target file exactly as it is.
         */
        never,

        /**
         * Overwrite eisting target files without prompting user.
         */
        always

    }

}