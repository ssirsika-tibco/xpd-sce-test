/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.actions.imports;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.nimbus.importprocessmap.ImportNimbusProcessMapWizard;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.IOpenResourceListener;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.models.ProcessModelProvider;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.ModelResource;
import com.tibco.xpd.rcp.internal.resources.ProjectResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * Import of Nimbus Process Diagrams.
 * 
 * @author njpatel
 */
public class NimbusImportAction extends Action {

    private IWorkbenchWindow window;

    private IRCPContainer resource;

    public NimbusImportAction(IWorkbenchWindow workbenchWindow) {
        super(Messages.NimbusImportAction_title);
        this.window = workbenchWindow;
        setId("nimbus-import-action"); //$NON-NLS-1$
        setToolTipText(Messages.NimbusImportAction_tooltip);
        setImageDescriptor(RCPActivator.getImageDescriptor(RCPImages.NIMBUS
                .getPath()));
        setEnabled(false);
        RCPResourceManager.addOpenListener(new IOpenResourceListener() {
            @Override
            public void opened(IRCPContainer resource) {
                NimbusImportAction.this.resource = resource;
                setEnabled(isValidTarget(resource));
            }
        });
    }

    /**
     * Check if the resource is a valid target of this import.
     * 
     * @param resource
     * @return
     */
    private boolean isValidTarget(IRCPContainer resource) {
        return !(resource instanceof ModelResource);
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {
        ImportNimbusWizard wizard = new ImportNimbusWizard();

        if (resource != null) {
            IProject project =
                    resource.getProjectResources().iterator().next()
                            .getProject();
            wizard.init(window.getWorkbench(), new StructuredSelection(project));
            WizardDialog dlg = new WizardDialog(window.getShell(), wizard);
            if (dlg.open() == WizardDialog.OK) {
                /*
                 * Open editor for all imported files.
                 */
                for (IFile file : wizard.getOutputFiles()) {
                    try {
                        IDE.openEditor(window.getActivePage(), file);
                    } catch (PartInitException e) {
                        RCPActivator
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format(Messages.NimbusImportAction_unableToOpenImportedDiagram_error_longdesc,
                                                file.getFullPath().toString()));
                    }
                }
            }
        }
    }

    /**
     * Extension of the {@link ImportNimbusProcessMapWizard} to allow the user
     * to select the target project, or create a new project to add the imported
     * model to.
     * 
     * @author njpatel
     */
    private class ImportNimbusWizard extends ImportNimbusProcessMapWizard {

        private ProjectSelectionPage projectSelectionPage;

        /**
         * @see com.tibco.xpd.nimbus.importprocessmap.ImportNimbusProcessMapWizard#addPages()
         * 
         */
        @Override
        public void addPages() {
            /*
             * Add the project selection page.
             */
            projectSelectionPage = new ProjectSelectionPage(resource);
            addPage(projectSelectionPage);

            super.addPages();
        }

        /**
         * @see com.tibco.xpd.nimbus.importprocessmap.ImportNimbusProcessMapWizard#getDestinationContainer()
         * 
         * @return
         */
        @Override
        protected IContainer getDestinationContainer() {
            IProject project = null;
            ProjectResource selection = projectSelectionPage.getSelection();
            if (selection != null) {
                project = selection.getProject();
            }

            if (project != null) {
                List<SpecialFolder> specialFolders =
                        ProcessModelProvider.getSpecialFolders(project,
                                ProcessModelProvider.PROCESSES_SFOLDER_KIND);
                for (SpecialFolder sf : specialFolders) {
                    if (sf.getFolder() != null && sf.getFolder().isAccessible()) {
                        return sf.getFolder();
                    }
                }
            }
            return super.getDestinationContainer();
        }
    }
}
