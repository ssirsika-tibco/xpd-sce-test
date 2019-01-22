/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.resources;

import java.io.File;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

import com.tibco.xpd.rcp.internal.RCPConsts;

/**
 * 
 * 
 * @author njpatel
 * @since 24 Nov 2012
 */
public final class RCPResourceFactory {

    /**
     * The type of models that can be edited by this RCP application.
     * 
     * @author njpatel
     * 
     */
    public enum ModelType {
        BOM("businessModel", RCPConsts.BOM_SFOLDER_KIND, RCPConsts.BOM_FILEEXT), // //$NON-NLS-1$
        OM("organizations", RCPConsts.OM_SFOLDER_KIND, RCPConsts.OM_FILEEXT), // //$NON-NLS-1$
        PROCESS(
                "processes", RCPConsts.PROCESSES_SFOLDER_KIND, RCPConsts.PROCESSES_FILEEXT); //$NON-NLS-1$

        private final String folderName;

        private final String specialFolderKind;

        private final String fileExt;

        private ModelType(String folderName, String specialFolderKind,
                String fileExt) {
            this.folderName = folderName;
            this.specialFolderKind = specialFolderKind;
            this.fileExt = fileExt;
        }

        /**
         * @return the fileExt
         */
        public String getFileExtension() {
            return fileExt;
        }

        public String getFolderName() {
            return folderName;
        }

        public String getSpecialFolderKind() {
            return specialFolderKind;
        }

        /**
         * Get the model type based on the given file extension.
         * 
         * @param fileExt
         * @return
         */
        public static ModelType getTypeForFileExt(String fileExt) {
            ModelType type = null;

            if (PROCESS.getFileExtension().equals(fileExt)) {
                type = PROCESS;
            } else if (BOM.getFileExtension().equals(fileExt)) {
                type = BOM;
            } else if (OM.getFileExtension().equals(fileExt)) {
                type = OM;
            }

            return type;
        }
    }

    /**
     * Create the resource to manage the given resource.
     * 
     * @param file
     * @return
     */
    public static final IRCPContainer createResource(File file) {
        Assert.isNotNull(file);

        if (file.isDirectory()) {
            return new ExtFolderResource(file);
        } else if (RCPConsts.FILE_EXT.equals(getFileExtension(file))) {
            return new MAAResource(file);
        } else {
            ModelType type =
                    ModelType.getTypeForFileExt(getFileExtension(file));

            if (type != null) {
                return new ModelResource(file, type);
            }
        }

        return null;
    }

    /**
     * Create a new MAA application.
     * 
     * @return
     */
    public static final IRCPContainer createNewMAA() {
        return new MAAResource();
    }

    /**
     * Create a project resource with the given name.
     * 
     * @param name
     * @return
     */
    public static final ProjectResource createProjectResource(String name) {
        Assert.isNotNull(name);
        return new ProjectResource(name);
    }

    /**
     * Create a project resource with the given name and external location (this
     * will create a linked project).
     * 
     * @param name
     * @param location
     * @return
     */
    public static final ProjectResource createProjectResource(String name,
            IPath location) {
        Assert.isNotNull(name);
        Assert.isNotNull(location);
        return new ProjectResource(name, location);
    }

    /**
     * Get the file extension of the given file.
     * 
     * @param file
     * @return file extension, will be <code>null</code> if this file has no
     *         extension.
     */
    private static String getFileExtension(File file) {
        String name = file.getName();
        int idx = name.lastIndexOf('.');

        if (idx > 0) {
            return name.substring(idx + 1);
        }

        return null;
    }

}
