/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.resources.ui.projectimport;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.zip.ZipEntry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.internal.wizards.datatransfer.ILeveledImportStructureProvider;
import org.eclipse.ui.internal.wizards.datatransfer.TarEntry;

import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * 
 * 
 * @author patkinso
 * @since 23 Apr 2013
 */
public class ArchiveFileProjectImporters {

    public abstract static class ArchiveFileProjectImporter extends
            ProjectImporter {

        protected Object parent;

        protected int level;

        protected ILeveledImportStructureProvider archiveStructureProvider;

        protected Object dotProjectFileEntry;

        /**
         * @param dotProjectFile
         *            The Object representing the .project file
         * @param parent
         *            The parent folder of the .project file
         * @param level
         *            The number of levels deep in the provider the file is
         */
        public ArchiveFileProjectImporter(Object dotProjectFileEntry,
                Object parent, int level,
                ILeveledImportStructureProvider archiveStructureProvider) {

            this.dotProjectFileEntry = dotProjectFileEntry;
            this.parent = parent;
            this.level = level;
            this.archiveStructureProvider = archiveStructureProvider;
            setProperties(dotProjectFileEntry);
        }

        /**
         * @param dotProjectFileEntry
         */
        protected void setProperties(Object dotProjectFileEntry) {

            if (dotProjectFileEntry != null) {
                InputStream stream =
                        archiveStructureProvider
                                .getContents(dotProjectFileEntry);

                // If we can get a description pull the name from there
                if (stream != null) {
                    try {
                        setDescription(ResourcesPlugin.getWorkspace()
                                .loadProjectDescription(stream));
                        stream.close();
                        setName(description.getName());
                    } catch (CoreException e) {
                        // log: no good couldn't set the description
                    } catch (IOException e) {
                        // log: no good couldn't get name
                    }
                } else {
                    IPath path =
                            getDotProjectFileEntryPath(dotProjectFileEntry);
                    setName(path.segment(path.segmentCount() - 2));
                }
            }

        }

        /**
         * @param dotProjectFileEntry2
         * @return
         */
        protected abstract IPath getDotProjectFileEntryPath(
                Object dotProjectFileEntry);

        /**
         * @see com.tibco.xpd.resources.ui.projectimport.ProjectImporter#importProject(com.tibco.xpd.resources.util.SubProgressMonitorEx)
         * 
         * @param progressMonitor
         * @return
         * @throws InterruptedException
         * @throws InvocationTargetException
         */
        @Override
        public IProject importProject(SubProgressMonitorEx monitor) {

            final IProject project =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(getName());

            List<?> fileSystemObjects =
                    archiveStructureProvider.getChildren(parent);
            archiveStructureProvider.setStrip(level);
            Object root = archiveStructureProvider.getRoot();

            StudioImportOperation operation =
                    new StudioImportOperation(project.getFullPath(), root,
                            archiveStructureProvider, fileSystemObjects);
            try {
                operation.run(monitor);
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return project;
        }
    }

    public static class ZipFileProjectImporter extends
            ArchiveFileProjectImporter {

        /**
         * @param dotProjectFileEntry
         * @param parent
         * @param level
         * @param archiveStructureProvider
         */
        public ZipFileProjectImporter(Object dotProjectFileEntry,
                Object parent, int level,
                ILeveledImportStructureProvider archiveStructureProvider) {
            super(dotProjectFileEntry, parent, level, archiveStructureProvider);
        }

        /**
         * @see com.tibco.xpd.resources.ui.projectimport.ArchiveFileProjectImporters.ArchiveFileProjectImporter#getDotProjectFileEntryPath(java.lang.Object)
         * 
         * @param dotProjectFileEntry
         * @return
         */
        @Override
        protected IPath getDotProjectFileEntryPath(Object dotProjectFileEntry) {
            ZipEntry entry = (ZipEntry) dotProjectFileEntry;
            return new Path(entry.getName());
        }

    }

    public static class TarFileProjectImporter extends
            ArchiveFileProjectImporter {

        /**
         * @param dotProjectFileEntry
         * @param parent
         * @param level
         * @param archiveStructureProvider
         */
        public TarFileProjectImporter(Object dotProjectFileEntry,
                Object parent, int level,
                ILeveledImportStructureProvider archiveStructureProvider) {
            super(dotProjectFileEntry, parent, level, archiveStructureProvider);
        }

        /**
         * @see com.tibco.xpd.resources.ui.projectimport.ArchiveFileProjectImporters.ArchiveFileProjectImporter#getDotProjectFileEntryPath(java.lang.Object)
         * 
         * @param dotProjectFileEntry
         * @return
         */
        @Override
        protected IPath getDotProjectFileEntryPath(Object dotProjectFileEntry) {
            TarEntry entry = (TarEntry) dotProjectFileEntry;
            return new Path(entry.getName());
        }

    }

}
