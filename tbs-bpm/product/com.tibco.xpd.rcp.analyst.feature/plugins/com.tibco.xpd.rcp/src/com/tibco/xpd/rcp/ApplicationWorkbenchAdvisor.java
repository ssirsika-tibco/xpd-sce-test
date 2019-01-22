/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp;

import java.io.File;
import java.net.URI;
import java.text.Collator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.Policy;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.IDEInternalWorkbenchImages;

import com.tibco.xpd.rcp.internal.RCPConsts;
import com.tibco.xpd.rcp.internal.actions.OpenFileAction;
import com.tibco.xpd.rcp.internal.utils.SplashDialog;

/**
 * Workbench configuration.
 * 
 * @author njpatel
 * 
 */
@SuppressWarnings("restriction")
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

    private static final String PERSPECTIVE_ID =
            "com.tibco.xpd.rcp.perspective"; //$NON-NLS-1$

    private ApplicationWorkbenchWindowAdvisor appWorkbenchWindowAdvisor;

    private IWorkbenchWindowConfigurer workbenchWindowConfigurer;

    public ApplicationWorkbenchAdvisor() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.application.WorkbenchAdvisor#createWorkbenchWindowAdvisor
     * (org.eclipse.ui.application.IWorkbenchWindowConfigurer)
     */
    @Override
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
            IWorkbenchWindowConfigurer configurer) {
        /* Sid XPD-8302 Need this later. */
        appWorkbenchWindowAdvisor =
                new ApplicationWorkbenchWindowAdvisor(configurer);
        workbenchWindowConfigurer = configurer;
        return appWorkbenchWindowAdvisor;
    }

    @Override
    public void initialize(IWorkbenchConfigurer configurer) {
        super.initialize(configurer);

        /*
         * Register workspace adapters - this is required, for instance, by the
         * workbench operation history for the undo context (which is also used
         * by the refactoring framework).
         */
        IDE.registerAdapters();

        Policy.setComparator(Collator.getInstance());

        // See method for details
        registerImagesForProblemsView(configurer);
    }

    /**
     * Register the Error, Warning and Info images for the problems view. These
     * images are registered in the IDE application which is not available in
     * this RCP. This is a workaround as described in bug report
     * https://bugs.eclipse.org/bugs/show_bug.cgi?id=155299
     * 
     * @param configurer
     */
    private void registerImagesForProblemsView(
            IWorkbenchConfigurer configurer) {

        configurer.declareImage(
                IDEInternalWorkbenchImages.IMG_ETOOL_PROBLEM_CATEGORY,
                RCPActivator
                        .getImageDescriptor("icons/16x16/problem_category.gif"), //$NON-NLS-1$
                true);
        configurer.declareImage(IDEInternalWorkbenchImages.IMG_OBJS_ERROR_PATH,
                RCPActivator.getImageDescriptor("icons/16x16/error_tsk.gif"), //$NON-NLS-1$
                true);
        configurer.declareImage(
                IDEInternalWorkbenchImages.IMG_OBJS_WARNING_PATH,
                RCPActivator.getImageDescriptor("icons/16x16/warn_tsk.gif"), //$NON-NLS-1$
                true);
        configurer.declareImage(IDEInternalWorkbenchImages.IMG_OBJS_INFO_PATH,
                RCPActivator.getImageDescriptor("icons/16x16/info_tsk.gif"), //$NON-NLS-1$
                true);

        configurer.declareImage(
                IDEInternalWorkbenchImages.IMG_OBJS_FIXABLE_ERROR,
                RCPActivator.getImageDescriptor(
                        "icons/16x16/quickfix_error_obj.gif"), //$NON-NLS-1$
                true);

        configurer.declareImage(
                IDEInternalWorkbenchImages.IMG_OBJS_FIXABLE_WARNING,
                RCPActivator.getImageDescriptor(
                        "icons/16x16/quickfix_warning_obj.gif"), //$NON-NLS-1$
                true);

        /*
         * XPD-5656: Declare the images used by the problems view header tab
         */
        configurer.declareImage(
                IDEInternalWorkbenchImages.IMG_ETOOL_PROBLEMS_VIEW,
                RCPActivator
                        .getImageDescriptor("icons/16x16/problems_view.png"), //$NON-NLS-1$
                true);
        configurer.declareImage(
                IDEInternalWorkbenchImages.IMG_ETOOL_PROBLEMS_VIEW_ERROR,
                RCPActivator.getImageDescriptor(
                        "icons/16x16/problems_view_err.png"), //$NON-NLS-1$
                true);
        configurer.declareImage(
                IDEInternalWorkbenchImages.IMG_ETOOL_PROBLEMS_VIEW_WARNING,
                RCPActivator.getImageDescriptor(
                        "icons/16x16/problems_view_warn.png"), //$NON-NLS-1$
                true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.application.WorkbenchAdvisor#getInitialWindowPerspectiveId
     * ()
     */
    @Override
    public String getInitialWindowPerspectiveId() {
        return PERSPECTIVE_ID;
    }

    @Override
    public void postStartup() {

        /*
         * Sid XPD-8302. Something has changed about startup in Eclipse 4.7 and
         * now when we clean up (delete) the workspace projects left behind by
         * previous session the editors are not closed automatically and run
         * into problems as the resource is ripped out from underneath them. So
         * close all editors to be on the safe side!.
         * 
         * We also close all editors on shutdown, this is just here in case that
         * didn't happen for some reason.
         */
        if (PlatformUI.getWorkbench() != null
                && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null
                && PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage() != null) {
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                    .closeAllEditors(false);
        }

        /*
         * Check if this workspace has any projects (shouldn't have any). If
         * there are any then delete them to 'clean' the workspace before it's
         * used.
         */
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        if (workspace != null && workspace.getRoot() != null) {
            for (IProject project : workspace.getRoot().getProjects()) {

                if (project.getName().startsWith(".")) { //$NON-NLS-1$
                    continue;
                }

                URI location = null;
                try {
                    IProjectDescription description = project.getDescription();
                    if (description != null) {
                        location = description.getLocationURI();
                    }

                } catch (CoreException e) {
                    // Do nothing
                }

                try {
                    if (location != null) {
                        // Delete project without deleting content
                        project.delete(false, true, new NullProgressMonitor());
                    } else {
                        // Delete project and content
                        project.delete(true, new NullProgressMonitor());
                    }
                } catch (CoreException e) {
                    RCPActivator.getDefault().getLogger()
                            .error(String.format(
                                    "Failed to delete project '%s' on startup.", //$NON-NLS-1$
                                    project.getName()));
                }

            }
        }

        /*
         * XPD-5332: Find a file path in the arguments rather than assume the
         * location in the argument array. This is because different platforms
         * provide different arguments on startup.
         */
        String[] args = Platform.getApplicationArgs();
        if (args.length > 0) {
            final File file = findFileInArgs(args);
            if (file != null) {
                OpenFileAction.open(
                        getWorkbenchConfigurer().getWorkbench().getDisplay()
                                .getActiveShell(),
                        file);
                return;
            }
        }

        // Open the splash dialog

        /*
         * XPD-6526: On Mac OS or linux, getActiveShell() returns null sometimes
         * and leads to a NPE, so we try to find and use the main shell instead
         * (or the first one) or create new shell using the display (this case
         * is less likely though)
         */
        String initialWindowTitle = workbenchWindowConfigurer.getTitle();
        if (initialWindowTitle == null || initialWindowTitle.trim().isEmpty()) {
            initialWindowTitle = "TIBCO Business Studio"; //$NON-NLS-1$
        }
        Display display = getWorkbenchConfigurer().getWorkbench().getDisplay();
        Shell shell = null;
        if (display.getActiveShell() != null) {
            shell = display.getActiveShell();
        } else if (display.getShells().length > 0) {
            // Try to find main shell by title. (To avoid problems with extra
            // window on linux - XPD-8413)
            for (Shell s : display.getShells()) {
                if (initialWindowTitle.equals(s.getText())) {// $NON-NLS-1$
                    shell = s;
                    break;
                }
            }
            // Otherwise take the first one.
            if (shell == null) {
                shell = display.getShells()[0];
            }
        } else {
            shell = new Shell(display);
        }

        /*
         * Sid XPD-8302 After creation do any additional setup of window (now
         * required to do something explicitly because
         * ApplicationWorkbenchWindowAdvisor changed in Eclipse and desn't call
         * back to our implementation of createWindowContents() anymore. So we
         * have to contribute top ribbon control as a view part and then setup
         * some stuff to fiddle it's fixed height.
         */
        appWorkbenchWindowAdvisor.initialiseWindowConfiguration();

        SplashDialog dlg = new SplashDialog(shell);
        dlg.open();

    }

    /**
     * Find a MAA file in the arguments, if specified.
     * 
     * @param args
     *            command-line args passed to this application
     * @return a File if args contains a file path to a valid file,
     *         <code>null</code> otherwise.
     */
    private File findFileInArgs(String[] args) {
        for (String arg : args) {
            IPath path = Path.fromOSString(arg);
            if (path != null && path.isAbsolute()) {
                // Assume that this is a path so check for file extension
                if (RCPConsts.FILE_EXT
                        .equalsIgnoreCase(path.getFileExtension())) {
                    // This must be the MAA file being opened.
                    File file = new File(path.toOSString());
                    if (file != null && file.exists()) {
                        return file;
                    }
                }
            }
        }
        return null;
    }
}
