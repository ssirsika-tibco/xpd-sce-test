package com.tibco.xpd.n2.test.schemas;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.eclipse.ui.wizards.datatransfer.ZipFileStructureProvider;

/**
 * Install sample project in the workspace.
 * 
 * @author glewis
 */
public class InstallProjectOperation extends WorkspaceModifyOperation {
    private final String projectName;

    private final URL zipLocation;

    /**
     * The Constructor.
     * 
     * @param projectName
     *            name of the project
     * @param zipLocation
     *            zipped content of the project
     */
    public InstallProjectOperation(String projectName, URL zipLocation) {
        this.projectName = projectName;
        this.zipLocation = zipLocation;
    }

    @Override
    protected void execute(IProgressMonitor monitor) throws CoreException,
            InvocationTargetException, InterruptedException {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();

        try {
            IProject project = root.getProject(projectName);
            if (!project.exists()) {
                project.create(monitor);
            }
            project.open(monitor);
            ZipFile zipFile;
            zipFile = new ZipFile(FileLocator.toFileURL(zipLocation).getFile());
            ZipFileStructureProvider structureProvider =
                    new ZipFileStructureProvider(zipFile);
            ImportOperation op =
                    new ImportOperation(project.getFullPath(),
                            structureProvider.getRoot(), structureProvider,
                            null);
            op.run(monitor);

            // switch to modeling perspective
            IWorkbench workbench = PlatformUI.getWorkbench();
            IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
            workbench.showPerspective("com.tibco.modeling.perspective", window); //$NON-NLS-1$

            // select project in the project explorer
            IViewReference[] views = window.getActivePage().getViewReferences();
            for (IViewReference vr : views) {
                if ("org.eclipse.ui.navigator.ProjectExplorer".equals(vr.getId())) { //$NON-NLS-1$
                    IViewPart explorer = vr.getView(true);
                    if (explorer != null) {
                        window.getActivePage().activate(explorer);
                        explorer.getSite().getSelectionProvider()
                                .setSelection(new StructuredSelection(project));
                    }
                    break;
                }
            }

        } catch (IOException e) {
        }
    }
}
