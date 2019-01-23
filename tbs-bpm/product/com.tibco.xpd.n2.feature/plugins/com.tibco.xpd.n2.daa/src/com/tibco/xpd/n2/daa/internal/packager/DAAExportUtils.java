/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.daa.internal.packager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.zip.CRC32;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.bom.gen.BOMGenActivator;
import com.tibco.xpd.core.validate.system.internal.TargetPlatformValidationUtils;
import com.tibco.xpd.daa.internal.preferences.DAAGenPreferences;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.internal.Messages;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.resources.ui.util.ResourceLocator;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.ui.resources.StatusInfo;

/**
 * Utility methods used by different parts of DAA export.
 * 
 * @author jarciuch
 * @since 5 Oct 2012
 */
public class DAAExportUtils {

    public static final String BOM_CACHE_PRECOMPILE_CONTRIBUTOR_ID =
            "bomCacheJarEnablePrecompileContributor"; //$NON-NLS-1$

    private static final QualifiedName SRC_FILE = new QualifiedName(
            BOMGenActivator.PLUGIN_ID, "srcFile"); //$NON-NLS-1$

    private static final QualifiedName SRC_JAVA_SERVICE_PROJECT =
            new QualifiedName(Activator.PLUGIN_ID, "srcJavaServiceProject"); //$NON-NLS-1$

    /**
     * Gets (or gets and create) a folder where BOM jars used in custom feature
     * are cached.
     * 
     * @param create
     *            if resources should be created if exists.
     * @return a folder where cached BOM jars are stored. This will only return
     *         a folder handle if create parameter is set to 'false' (if the
     *         project containing cache folder is closed it will be opened after
     *         method is called regardless of create parameter value).
     * @throws CoreException
     */
    public static IFolder getCreateBOMJarsCacheFolder(boolean create)
            throws CoreException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = root.getProject(N2PENamingUtils.BS_CACHE_PROJECT);
        if (!project.exists() && create) {
            project.create(new NullProgressMonitor());
        }
        if (project.exists() && !project.isOpen()) {
            project.open(new NullProgressMonitor());
        }
        IFolder bomJarsFolder =
                project.getFolder(N2PENamingUtils.BOM_JARS_CACHE_FOLDER);
        if (create) {
            ProjectUtil.createFolder(bomJarsFolder,
                    true,
                    new NullProgressMonitor());
        }
        return bomJarsFolder;
    }

    /**
     * Gets (or gets and create) a folder where BOM jars used in custom feature
     * are cached.
     * 
     * @param create
     *            if resources should be created if exists.
     * @return a folder where cached BOM jars are stored. This will only return
     *         a folder handle if create parameter is set to 'false' (if the
     *         project containing cache folder is closed it will be opened after
     *         method is called regardless of create parameter value).
     * @throws CoreException
     */
    public static IFolder getCreateBOMJarsCacheFolder(IProject project,
            boolean create) throws CoreException {

        IFolder bomJarsFolder =
                project.getFolder(N2PENamingUtils.BOM_JARS_CACHE_FOLDER);

        boolean isPreCompiled = ProjectUtil.isPrecompiledProject(project);

        if (isPreCompiled) {

            IResource precompiledBomJarCacheFolder =
                    ResourceLocator.getTargetResource(project, bomJarsFolder);

            return (IFolder) precompiledBomJarCacheFolder;

        }

        if (create) {

            ProjectUtil.createFolder(bomJarsFolder,
                    true,
                    new NullProgressMonitor());
        }

        return bomJarsFolder;
    }

    /**
     * Returns a collection of cached jar files for BOMs.
     * 
     * @param boms
     *            a collection of BOMs
     * @return cached jars for provided BOMs.
     * @throws CoreException
     */
    public static Collection<IFile> getBomCachedJars(Collection<IFile> boms)
            throws CoreException {
        ArrayList<IFile> cachedJars = new ArrayList<IFile>(16);
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IFolder cacheFolder = getCreateBOMJarsCacheFolder(false);
        if (cacheFolder.isAccessible()) {
            for (IResource r : cacheFolder.members()) {
                if (r instanceof IFile
                        && "jar".equalsIgnoreCase(r.getFileExtension())) { //$NON-NLS-1$
                    String srcFile = r.getPersistentProperty(SRC_FILE);
                    if (srcFile != null
                            && boms.contains(root.getFile(new Path(srcFile)))) {
                        cachedJars.add((IFile) r);
                    }
                }
            }
        }
        return cachedJars;
    }

    /**
     * Returns a collection of cached jar files for java services for a project.
     * 
     * @param boms
     *            a collection of BOMs
     * @return cached jars for provided BOMs.
     * @throws CoreException
     */
    public static Collection<IFile> getJavaServiceCachedJars(IProject project)
            throws CoreException {
        /*
         * JA: At the moment it takes jars for all java service projects
         * (regardless of context project).
         */
        ArrayList<IFile> cachedJars = new ArrayList<IFile>(8);
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IFolder cacheFolder = getCreateBOMJarsCacheFolder(project, false);
        if (cacheFolder.isAccessible()) {
            for (IResource r : cacheFolder.members()) {
                if (r instanceof IFile
                        && "jar".equalsIgnoreCase(r.getFileExtension())) { //$NON-NLS-1$
                    String srcProject =
                            r.getPersistentProperty(SRC_JAVA_SERVICE_PROJECT);
                    if (srcProject != null) {
                        cachedJars.add((IFile) r);
                    }
                }
            }
        }
        return cachedJars;
    }

    /**
     * Returns calculated checksum for the container. The checksum is calculated
     * recursively for all children (content and resource name).
     * 
     * @param container
     *            project or folder to calculate checksum.
     * @return calculated checksum for the container.
     * @throws CoreException
     */
    public static String getChecksum(final IContainer container)
            throws CoreException {
        if (container.isAccessible()) {
            final byte[] dataBytes = new byte[1024];
            final CRC32 checksum = new CRC32();
            container.accept(new IResourceVisitor() {
                @Override
                public boolean visit(IResource r) throws CoreException {
                    checksum.update(r.getName().getBytes());
                    if (r instanceof IFile) {
                        try {
                            InputStream is = ((IFile) r).getContents(true);
                            int nread = 0;
                            while ((nread = is.read(dataBytes)) != -1) {
                                checksum.update(dataBytes, 0, nread);
                            }
                            is.close();
                        } catch (IOException e) {
                            String msg =
                                    String.format("Problem while processing checksum for: %1$s", //$NON-NLS-1$
                                            r.getFullPath());
                            throw new CoreException(new Status(IStatus.ERROR,
                                    Activator.PLUGIN_ID, msg, e));
                        }
                    }
                    return true;
                }
            });
            return Long.toString(checksum.getValue());
        }
        String msg =
                String.format("Resource is not available for checksum: %1$s", //$NON-NLS-1$
                        container.getFullPath());
        throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                msg));
    }

    /**
     * Returns calculated checksum for the list of files. The checksum is
     * calculated for content only, so two files with different names and same
     * content should return the same checksum.
     * 
     * @param files
     *            eclipse IFile to calculate checksum.
     * @return calculated checksum for the given list of files.
     * @throws CoreException
     *             if file is not available or there was IO problem.
     */
    public static String getFilesChecksum(List<IFile> files)
            throws CoreException {

        final CRC32 checksum = new CRC32();
        for (IFile file : files) {

            if (file.isAccessible()) {
                final byte[] dataBytes = new byte[1024];
                try {
                    InputStream is = file.getContents(true);
                    int nread = 0;
                    while ((nread = is.read(dataBytes)) != -1) {
                        checksum.update(dataBytes, 0, nread);
                    }
                    is.close();
                } catch (IOException e) {
                    String msg =
                            String.format("Problem while calculating checksum for: %1$s", //$NON-NLS-1$
                                    file.getFullPath());
                    throw new CoreException(new Status(IStatus.ERROR,
                            Activator.PLUGIN_ID, msg, e));
                }

            } else {
                String msg =
                        String.format("File is not available for checksum: %1$s", //$NON-NLS-1$
                                file.getFullPath());
                throw new CoreException(new Status(IStatus.ERROR,
                        Activator.PLUGIN_ID, msg));
            }
        }
        return Long.toString(checksum.getValue());
    }

    /**
     * Returns calculated checksum for the file. The checksum is calculated for
     * content only, so two files with different names and same content should
     * return the same checksum.
     * 
     * @param file
     *            eclipse IFile to calculate checksum.
     * @return calculated checksum for the project parameter.
     * @throws CoreException
     *             if file is not available or there was IO problem.
     * @deprecated Use {@link DAAExportUtils#getFilesChecksum(List)}
     */
    @Deprecated
    public static String getFileChecksum(final IFile file) throws CoreException {

        return getFilesChecksum(Collections.singletonList(file));
    }

    /**
     * Returns list of cached jars pointing to non existing resources.
     * 
     * @param cacheJarsFolder
     *            the forlder with cached jars.
     * @return list of cached jars pointing to non existing resources.
     */
    public static Collection<IFile> getCachedJarsWithoutSource(
            IFolder cacheJarsFolder) throws CoreException {
        final ArrayList<IFile> jarsNoSrc = new ArrayList<IFile>(16);
        final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        if (cacheJarsFolder.isAccessible()) {
            cacheJarsFolder.accept(new IResourceVisitor() {
                @Override
                public boolean visit(IResource r) throws CoreException {
                    if (r instanceof IFile
                            && "jar".equalsIgnoreCase(r.getFileExtension())) { //$NON-NLS-1$
                        String srcBomPath = r.getPersistentProperty(SRC_FILE);
                        String srcProjectPath =
                                r.getPersistentProperty(SRC_JAVA_SERVICE_PROJECT);
                        if (srcBomPath != null
                                && !(root.findMember(srcBomPath) instanceof IFile)) {
                            jarsNoSrc.add((IFile) r);
                        } else if (srcProjectPath != null
                                && !(root.findMember(srcProjectPath) instanceof IProject)) {
                            jarsNoSrc.add((IFile) r);
                        }
                    }
                    return true;
                }
            });
        }
        return jarsNoSrc;
    }

    private static final String DIALOG_RETURN_CODE_PREFIX =
            "DIALOG_RETURN_CODE_PREFIX"; //$NON-NLS-1$

    /**
     * This method wraps the target platform validation to include specific
     * dialog, pop-up for interaction with the user.
     * 
     * @param activeShell
     * @param object
     * @return
     */
    public static boolean isTargetPlatformDefaultORValid(Shell activeShell) {
        boolean valid = true;
        StatusInfo status = validateTargetPlatform();
        // log status
        Activator.getDefault().getLogger().log(status);
        if (status.isError()) {
            if (activeShell != null) {
                MessageDialog
                        .openError(activeShell,
                                Messages.DAAExportUtils_TargetPlatform_InvalidError_Title,
                                status.getMessage());
            }

            valid = false;
        } else if (status.isWarning()) {
            valid = true;
            if (activeShell != null) {
                // get values from preference store
                IPreferenceStore store = DAAGenPreferences.getPreferenceStore();
                String key = DAAGenPreferences.CONFIRM_TARGET_PLATFORM;
                boolean doNotConfirm = store.getBoolean(key);
                if (!doNotConfirm) {
                    String warnMsg =
                            status.getMessage()
                                    + Messages.DAAExportUtils_TargetPlatform_Default_Not_Used_Msg_Que;
                    int value =
                            openYesNoQuestionWithWarning(activeShell,
                                    Messages.DAAExportUtils_Default_TargetPlatform_Not_Used_Title,
                                    warnMsg,
                                    null,
                                    key,
                                    store);
                    valid = (value == IDialogConstants.OK_ID);
                } else {
                    int returnCode =
                            store.getInt(getMessageDialogReturnCodeStoreKey(key));
                    valid = (returnCode == IDialogConstants.OK_ID);
                }
            }

        }
        return valid;
    }

    /**
     * Simply provides access to the Core functionality
     * TargetPlatformValidationUtils.validateTargetPlatform().
     * 
     * @return
     */
    public static StatusInfo validateTargetPlatform() {
        return TargetPlatformValidationUtils.validateTargetPlatform();
    }

    public static int openYesNoQuestionWithWarning(Shell parent, String title,
            String message, String toggleMessage, String key,
            IPreferenceStore store) {
        MessageDialogWithToggle dialog =
                new MessageDialogWithToggle(
                        parent,
                        title,
                        MessageDialog
                                .getImage(MessageDialog.DLG_IMG_MESSAGE_WARNING),
                        message, MessageDialog.WARNING, new String[] {
                                IDialogConstants.OK_LABEL,
                                IDialogConstants.CANCEL_LABEL }, 1,
                        Messages.DAAExportUtils_DO_NOT_ASK_AGAIN, false);

        dialog.setPrefKey(key);
        dialog.setPrefStore(store);
        dialog.open();
        int returnCode = dialog.getReturnCode();
        if (returnCode != MessageDialogWithToggle.CANCEL) {
            store.setValue(getMessageDialogReturnCodeStoreKey(dialog
                    .getPrefKey()), returnCode);
            store.setValue(key, dialog.getToggleState());
        }

        return returnCode;
    }

    private static String getMessageDialogReturnCodeStoreKey(String key) {
        return DIALOG_RETURN_CODE_PREFIX + key;
    }
}
