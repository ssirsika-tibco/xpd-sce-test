package com.tibco.xpd.rest.ui.internal.asset;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.rest.ui.internal.RestServicesActivator;
import com.tibco.xpd.rsd.ui.wizards.NewRsdFileWizard;

/**
 * Asset configurator to add the REST nature to projects containing REST
 * resources.
 * 
 * @author nwilson
 * @since 30 Jan 2015
 */
public class RestAssetConfigurator extends
        AbstractSpecialFolderAssetConfigurator implements
        IProjectAssetVersionProvider {

    @Override
    public void configure(IProject project, Object configuration)
            throws CoreException {
        super.configure(project, configuration);
        if (project != null) {
            ProjectUtil.addNature(project, RestServicesProjectNature.ID);

            if (configuration instanceof RestAssetConfiguration) {
                RestAssetConfiguration restAssetConf =
                        (RestAssetConfiguration) configuration;
                if (restAssetConf.createRsdFile()) {
                    String folderName = getSpecialFolderName();
                    String fileName = restAssetConf.getRsdFileName();
                    // Create the folder
                    if (folderName != null) {
                        IFolder folder = project.getFolder(folderName);
                        if (folder.exists()) {
                            /*
                             * Create the sample rsd file in this folder. We
                             * will reuse NwRsdFileWizard, to make sure that we
                             * create the same RSD model as if it was created by
                             * the wizard.
                             */

                            IPath path = folder.getFullPath();
                            IPath filePath =
                                    project.getFullPath().append(folderName)
                                            .append(fileName);
                            NewRsdFileWizard newRsdFileWizard =
                                    new NewRsdFileWizard();
                            IRunnableWithProgress op =
                                    newRsdFileWizard
                                            .getCreateOperation(filePath);
                            try {
                                op.run(new NullProgressMonitor());
                                IFile file =
                                        ResourcesPlugin.getWorkspace()
                                                .getRoot().getFile(filePath);
                                if (file != null) {
                                    newRsdFileWizard.openEditor(file);
                                    IWorkbenchWindow workbenchWindow =
                                            PlatformUI.getWorkbench()
                                                    .getActiveWorkbenchWindow();
                                    NewRsdFileWizard.selectAndReveal(file,
                                            workbenchWindow);
                                }
                            } catch (InterruptedException
                                    | InvocationTargetException e) {
                                RestServicesActivator
                                        .getDefault()
                                        .getLog()
                                        .log(new Status(
                                                IStatus.ERROR,
                                                RestServicesActivator.PLUGIN_ID,
                                                "Problem with default rsd file creation.", e)); //$NON-NLS-1$
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getVersion(IProject project) {
        return Integer.parseInt(RestServicesUtil.REST_ASSET_VERSION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getSpecialFolderKind() {
        return RestServicesUtil.REST_SPECIAL_FOLDER_KIND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getSpecialFolderName() {
        Object config = getConfiguration();
        if (config instanceof SpecialFolderAssetConfiguration) {
            String name =
                    ((SpecialFolderAssetConfiguration) config)
                            .getSpecialFolderName();

            if (name != null && name.length() > 0) {
                return name;
            }
        }

        return getDefaultFolderName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefaultFolderName() {
        return RestServicesUtil.REST_SPECIAL_FOLDER_DEFAULT_NAME;
    }
}
