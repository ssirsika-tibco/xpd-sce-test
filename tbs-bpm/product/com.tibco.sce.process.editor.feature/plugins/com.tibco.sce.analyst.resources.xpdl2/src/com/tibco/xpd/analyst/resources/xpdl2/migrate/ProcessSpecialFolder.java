package com.tibco.xpd.analyst.resources.xpdl2.migrate;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.migrateproject.MigrateProject;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;

public class ProcessSpecialFolder extends MigrateProject {

    private static final String FILE_EXTENSION = "xpdl"; //$NON-NLS-1$

    // private static Logger
    // log=LoggerFactory.createLogger(Xpdl2ResourcesPlugin.PLUGIN_ID);

    public ProcessSpecialFolder() {
    }

    @Override
    public void migrate(final IProject project, IProgressMonitor monitor)
            throws CoreException {
        SubProgressMonitor sub = new SubProgressMonitor(monitor, 5);
        sub
                .beginTask(com.tibco.xpd.analyst.resources.xpdl2.Messages.ProcessSpecialFolder_projectFileReorg_message,
                        5);
        sub.worked(1);
        performMigration(project, sub);
    }

    private void performMigration(IProject project, IProgressMonitor monitor)
            throws CoreException {
        //
        if (!project.isAccessible()) {
            monitor.worked(4);
            throw new IllegalStateException(
                    com.tibco.xpd.analyst.resources.xpdl2.Messages.ProcessSpecialFolder_projectNotAccessible_message);
        }

        // 1. Search for files
        Set<IResource> resources = findFiles(project, FILE_EXTENSION);
        monitor.worked(1);

        // 1a. Proceed if there are any files
        if (resources.size() != 0) {
            // 2. Create folder
            IFolder folder =
                    project
                            .getFolder(Messages.BusinessProcessAssetConfig_DEFAULT_PROCESS_FOLDER_NAME);
            if (!folder.isAccessible()) {
                boolean force = true;
                boolean local = true;
                folder.create(force, local, null);
            }
            monitor.worked(1);

            // 3. Move files
            for (IResource resource : resources) {
                IFile destinationFile = folder.getFile(resource.getName());
                if (destinationFile.exists()) {
                    /*
                     * If there is already a file with same name loop adding
                     * suffix to file name until find new unique name, eg.: if
                     * File.xpdl is there then new file will have File-1.xpdl
                     * name.
                     */
                    String fileExtension = destinationFile.getFileExtension();
                    String resName = resource.getName();
                    resName =
                            resName.substring(0, resName.length()
                                    - fileExtension.length() - 1);
                    int counter = 1;
                    while (destinationFile.exists()) {
                        resName = resName + "-" + counter + "." + fileExtension; //$NON-NLS-1$ //$NON-NLS-2$
                        destinationFile = folder.getFile(resName);
                        counter++;
                    }
                }
                IPath fullPath = destinationFile.getFullPath();
                resource.move(fullPath, true, null);
            }
            monitor.worked(1);

            // 4. Mark folder as "process special folder"
            ProjectConfig projectConfig =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);

            AssetType assetType =
                    ProjectConfigFactory.eINSTANCE.createAssetType();
            assetType.setId("com.tibco.xpd.asset.businessProcess");//$NON-NLS-1$
            projectConfig.getAssetTypes().add(assetType);

            SpecialFolders specialFolders = projectConfig.getSpecialFolders();
            if (specialFolders.getFolder(folder) == null) {
                specialFolders.addFolder(folder,
                        Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
            }
            monitor.worked(1);
        } else {
            monitor.worked(3);
        }
    }

    private Set<IResource> findFiles(IProject project, final String ext)
            throws CoreException {
        final Set<IResource> resources = new HashSet<IResource>();
        IResourceVisitor visitor = new IResourceVisitor() {

            public boolean visit(IResource resource) throws CoreException {
                String fileExtension = resource.getFileExtension();
                if (resource.getType() == IResource.FILE
                        && ext.equals(fileExtension) && resource.isAccessible()) {
                    resources.add(resource);
                }
                // don't dive into folders
                boolean cont = resource.getType() == IResource.PROJECT;// (resource.getType()==IResource.FOLDER)
                // ||
                // (resource.getType()==IResource.PROJECT);
                return cont;
            }
        };
        project.accept(visitor);
        return resources;
    }
}
