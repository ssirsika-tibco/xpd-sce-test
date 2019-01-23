/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets.util;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetMigration;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

/**
 * Project asset migration implementation to be used for asset migration. This
 * class can either be used directly in the extension or subclassed.
 * <p>
 * If this file is to be used directly in the extension then use
 * {@link AssetWorkingCopyMigrationFactory} to create an instance of this class
 * (see the class for more details).
 * 
 * @author njpatel
 * @since 3.5
 */
public class AssetWorkingCopyMigration implements IProjectAssetMigration {

    private final String sfKind;

    private final String fileExt;

    /**
     * Migrate the working copies of the files with the given file extension in
     * the special folder kind specified.
     * 
     * @param specialFolderKind
     * @param fileExtension
     */
    public AssetWorkingCopyMigration(String specialFolderKind,
            String fileExtension) {
        sfKind = specialFolderKind;
        fileExt = fileExtension;
    }

    @Override
    public IStatus migrate(IProject project, IProjectAsset asset,
            int assetVersion, int currentVersion) {
        List<SpecialFolder> sFolders =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(project, sfKind);

        if (sFolders != null) {
            for (SpecialFolder sFolder : sFolders) {
                IFolder folder = sFolder.getFolder();

                if (folder != null && folder.isAccessible()) {
                    try {
                        folder.accept(new MigrationProxyVisitor(fileExt), 0);
                    } catch (CoreException e) {
                        return e.getStatus();
                    }
                }
            }
        }

        return Status.OK_STATUS;
    }

    /**
     * Get the working copy of the given file.
     * 
     * @param file
     * @return working copy or <code>null</code> if the file has no working
     *         copy.
     */
    protected AbstractWorkingCopy getWorkingCopy(IFile file) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
        return (AbstractWorkingCopy) (wc instanceof AbstractWorkingCopy ? wc
                : null);
    }

    /**
     * Resource proxy visitor that will migrate the working copies of the files
     * with the given extension in the specified special folders.
     */
    private class MigrationProxyVisitor implements IResourceProxyVisitor {

        private final String fileExt;

        public MigrationProxyVisitor(String fileExt) {
            if (!fileExt.startsWith(".")) { //$NON-NLS-1$
                fileExt = "." + fileExt; //$NON-NLS-1$
            }
            this.fileExt = fileExt;
        }

        @Override
        public boolean visit(IResourceProxy proxy) throws CoreException {
            if (proxy.getType() == IResource.FILE) {
                if (proxy.getName().endsWith(fileExt)) {
                    IFile file = (IFile) proxy.requestResource();
                    AbstractWorkingCopy wc = getWorkingCopy(file);
                    if (wc != null) {
                        EObject eo = wc.getRootElement();
                        if (eo == null && wc.isInvalidVersion()) {
                            try {
                                wc.migrate();
                            } catch (Exception e) {
                                /*
                                 * Don't want the migration of other resources
                                 * to stop due to a problem with this file - so
                                 * log and continue
                                 */
                                XpdResourcesPlugin
                                        .getDefault()
                                        .getLogger()
                                        .error(e,
                                                String
                                                        .format(Messages.AssetWorkingCopyMigration_exceptionDuringMigration_error_shortdesc,
                                                                file
                                                                        .getFullPath()
                                                                        .toString()));
                            }
                        }
                    }
                }
                return false;
            }
            return true;
        }
    }

}
