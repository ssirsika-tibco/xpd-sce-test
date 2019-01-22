/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.ISaveablesLifecycleListener;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.internal.DefaultSaveable;
import org.eclipse.ui.internal.SaveableHelper;
import org.eclipse.ui.internal.SaveablesList;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.EventLoopProgressMonitor;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.model.WorkbenchPartLabelProvider;
import org.eclipse.ui.statushandlers.StatusManager;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Helper class for handling deploy actions.
 * 
 * @author Miguel Torres
 * 
 */
public class DeployHelper {

    /**
     * Save all dirty editors in the workbench. Opens a dialog to prompt the
     * user if <code>confirm</code> is true. Return true if successfull. Return
     * false if the user has canceled the command.
     * 
     * @param confirm
     *            <code>true</code> to ask the user before saving unsaved
     *            changes (recommended), and <code>false</code> to save unsaved
     *            changes without asking
     * @return <code>true</code> if the command succeeded, and
     *         <code>false</code> if the operation was canceled by the user or
     *         an error occurred while saving
     */
    public static boolean saveAllEditors(boolean confirm) {
        final boolean finalConfirm = confirm;
        final boolean[] result = new boolean[1];
        result[0] = true;

        SafeRunner.run(new SafeRunnable(WorkbenchMessages.ErrorClosing) {
            @Override
            public void run() {
                // Collect dirtyParts
                ArrayList dirtyParts = new ArrayList();
                ArrayList dirtyEditorsInput = new ArrayList();
                IWorkbenchWindow windows[] =
                        PlatformUI.getWorkbench().getWorkbenchWindows();
                for (int i = 0; i < windows.length; i++) {
                    IWorkbenchPage pages[] = windows[i].getPages();
                    for (int j = 0; j < pages.length; j++) {
                        WorkbenchPage page = (WorkbenchPage) pages[j];

                        ISaveablePart[] parts = page.getDirtyParts();

                        for (int k = 0; k < parts.length; k++) {
                            ISaveablePart part = parts[k];

                            if (part.isSaveOnCloseNeeded()) {
                                if (part instanceof IEditorPart) {
                                    IEditorPart editor = (IEditorPart) part;
                                    if (!dirtyEditorsInput.contains(editor
                                            .getEditorInput())) {
                                        dirtyParts.add(editor);
                                        dirtyEditorsInput.add(editor
                                                .getEditorInput());
                                    }
                                } else {
                                    dirtyParts.add(part);
                                }
                            }
                        }
                    }
                }
                IShellProvider shellProvider;
                IRunnableContext runnableContext;
                IWorkbenchWindow w =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                if (w == null && windows.length > 0) {
                    w = windows[0];
                }
                if (w != null) {
                    shellProvider = w;
                    runnableContext = w;
                } else {
                    shellProvider = new IShellProvider() {
                        @Override
                        public Shell getShell() {
                            return null;
                        }
                    };
                    runnableContext = new ProgressMonitorDialog(null);
                }
                // The fourth parameter is true to also save saveables from
                // non-part sources, see bug 139004.
                result[0] =
                        saveAll(dirtyParts,
                                finalConfirm,
                                false,
                                true,
                                runnableContext,
                                shellProvider);
            }
        });
        return result[0];
    }

    /**
     * Saves the given dirty editors and views, optionally prompting the user.
     * 
     * @param dirtyParts
     *            the dirty views and editors
     * @param confirm
     *            <code>true</code> to prompt whether to save,
     *            <code>false</code> to save without prompting
     * @param closing
     *            <code>true</code> if the parts are being closed,
     *            <code>false</code> if just being saved without closing
     * @param addNonPartSources
     *            true if non-part sources should be saved too
     * @param runnableContext
     *            the context in which to run long-running operations
     * @param shellProvider
     *            providing the shell to use as the parent for the dialog that
     *            prompts to save multiple dirty editors and views
     * @return <code>true</code> on success, <code>false</code> if the user
     *         canceled the save
     */
    private static boolean saveAll(List dirtyParts, final boolean confirm,
            final boolean closing, boolean addNonPartSources,
            final IRunnableContext runnableContext,
            final IShellProvider shellProvider) {
        // clone the input list
        dirtyParts = new ArrayList(dirtyParts);
        List modelsToSave;
        if (confirm) {
            boolean saveable2Processed = false;
            // Process all parts that implement ISaveablePart2.
            // These parts are removed from the list after saving
            // them. We then need to restore the workbench to
            // its previous state, for now this is just last
            // active perspective.
            // Note that the given parts may come from multiple
            // windows, pages and perspectives.
            ListIterator listIterator = dirtyParts.listIterator();

            WorkbenchPage currentPage = null;
            // MPerspective currentPageOriginalPerspective = null;
            while (listIterator.hasNext()) {
                IWorkbenchPart part = (IWorkbenchPart) listIterator.next();
                if (part instanceof ISaveablePart2) {
                    WorkbenchPage page =
                            (WorkbenchPage) part.getSite().getPage();
                    if (!Util.equals(currentPage, page)) {
                        currentPage = page;
                    }
                    if (confirm) {
                        // show the window containing the page?
                        IWorkbenchWindow partsWindow =
                                page.getWorkbenchWindow();
                        if (partsWindow != partsWindow.getWorkbench()
                                .getActiveWorkbenchWindow()) {
                            Shell shell = partsWindow.getShell();
                            if (shell.getMinimized()) {
                                shell.setMinimized(false);
                            }
                            shell.setActive();
                        }
                        page.bringToTop(part);
                    }
                    // try to save the part
                    int choice =
                            savePart((ISaveablePart2) part,
                                    page.getWorkbenchWindow(),
                                    confirm);
                    if (choice == ISaveablePart2.CANCEL) {
                        // If the user cancels, don't restore the previous
                        // workbench state, as that will
                        // be an unexpected switch from the current state.
                        return false;
                    } else if (choice != ISaveablePart2.DEFAULT) {
                        saveable2Processed = true;
                        listIterator.remove();
                    }
                }
            }

            // if processing a ISaveablePart2 caused other parts to be
            // saved, remove them from the list presented to the user.
            if (saveable2Processed) {
                listIterator = dirtyParts.listIterator();
                while (listIterator.hasNext()) {
                    ISaveablePart part = (ISaveablePart) listIterator.next();
                    if (!part.isDirty()) {
                        listIterator.remove();
                    }
                }
            }

            modelsToSave =
                    convertToSaveables(dirtyParts, closing, addNonPartSources);

            for (IProject project : ResourcesPlugin.getWorkspace().getRoot()
                    .getProjects()) {
                XpdProjectResourceFactory factory =
                        XpdResourcesPlugin.getDefault()
                                .getXpdProjectResourceFactory(project);
                if (factory != null) {
                    Map dirtyResources = factory.getDirtyResources();
                    if (dirtyResources != null) {
                        // All dirty working copies
                        Collection values = dirtyResources.values();
                        if (values != null) {
                            for (Object object : values) {
                                if (object instanceof WorkingCopy) {
                                    WorkingCopy dirtyWc = (WorkingCopy) object;
                                    Saveable saveable = dirtyWc.getSaveable();
                                    if (saveable != null
                                            && !modelsToSave.contains(saveable)) {
                                        modelsToSave.add(saveable);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // If nothing to save, return.
            if (modelsToSave.isEmpty()) {
                return true;
            }
            boolean canceled =
                    SaveableHelper.waitForBackgroundSaveJobs(modelsToSave);
            if (canceled) {
                return false;
            }
            filterResources(modelsToSave);
            // Use a simpler dialog if there's only one
            if (modelsToSave.size() == 1) {
                Saveable model = (Saveable) modelsToSave.get(0);
                String message =
                        NLS.bind(WorkbenchMessages.EditorManager_saveChangesQuestion,
                                model.getName());
                // Show a dialog.
                String[] buttons =
                        new String[] { IDialogConstants.YES_LABEL,
                                IDialogConstants.CANCEL_LABEL };
                MessageDialog d =
                        new MessageDialog(shellProvider.getShell(),
                                WorkbenchMessages.Save_Resource, null, message,
                                MessageDialog.QUESTION, buttons, 0);

                int choice = SaveableHelper.testGetAutomatedResponse();
                if (SaveableHelper.testGetAutomatedResponse() == SaveableHelper.USER_RESPONSE) {
                    choice = d.open();
                }

                // Branch on the user choice.
                // The choice id is based on the order of button labels
                // above.
                switch (choice) {
                case ISaveablePart2.YES: // yes
                    break;
                default:
                case ISaveablePart2.CANCEL: // cancel
                    return false;
                }
            } else {
                DeployListSelectionDialog dlg =
                        new DeployListSelectionDialog(shellProvider.getShell(),
                                modelsToSave, new ArrayContentProvider(),
                                new WorkbenchPartLabelProvider(),
                                "The following resources need saving before deployment");
                dlg.setInitialSelections(modelsToSave.toArray());
                dlg.setTitle("Some resources need saving");

                // this "if" statement aids in testing.
                if (SaveableHelper.testGetAutomatedResponse() == SaveableHelper.USER_RESPONSE) {
                    int result = dlg.open();
                    // Just return false to prevent the operation continuing
                    if (result == IDialogConstants.CANCEL_ID) {
                        return false;
                    }

                    modelsToSave = Arrays.asList(dlg.getResult());
                }
            }
        } else {
            modelsToSave =
                    convertToSaveables(dirtyParts, closing, addNonPartSources);
        }

        // If the editor list is empty return.
        if (modelsToSave.isEmpty()) {
            return true;
        }

        // Create save block.
        final List finalModels = modelsToSave;
        IRunnableWithProgress progressOp = new IRunnableWithProgress() {
            @Override
            public void run(IProgressMonitor monitor) {
                IProgressMonitor monitorWrap =
                        new EventLoopProgressMonitor(monitor);
                monitorWrap.beginTask("", finalModels.size()); //$NON-NLS-1$
                for (Iterator i = finalModels.iterator(); i.hasNext();) {
                    Saveable model = (Saveable) i.next();
                    // handle case where this model got saved as a result of
                    // saving another
                    if (!model.isDirty()) {
                        monitor.worked(1);
                        continue;
                    }
                    SaveableHelper.doSaveModel(model, new SubProgressMonitor(
                            monitorWrap, 1), shellProvider, closing || confirm);
                    if (monitorWrap.isCanceled()) {
                        break;
                    }
                }
                monitorWrap.done();
            }
        };

        // Do the save.
        return runProgressMonitorOperation(WorkbenchMessages.Save_All,
                progressOp,
                runnableContext,
                shellProvider);
    }

    private static List filterResources(List modelsToSave) {
        if (modelsToSave != null && !modelsToSave.isEmpty()) {
            for (Object saveableModule : modelsToSave) {
                IResource resource = ResourceUtil.getResource(saveableModule);
                if (resource != null) {
                    System.out.println();

                }
            }
        }
        return Collections.emptyList();
    }

    /**
     * Saves the workbench part ... this is similar to
     * {@link SaveableHelper#savePart(ISaveablePart, IWorkbenchPart, IWorkbenchWindow, boolean) }
     * except that the {@link ISaveablePart2#DEFAULT } case must cause the
     * calling function to allow this part to participate in the default saving
     * mechanism.
     * 
     * @param saveable
     *            the part
     * @param window
     *            the workbench window
     * @param confirm
     *            request confirmation
     * @return the ISaveablePart2 constant
     */
    private static int savePart(final ISaveablePart2 saveable,
            IWorkbenchWindow window, boolean confirm) {
        // Short circuit.
        if (!saveable.isDirty()) {
            return ISaveablePart2.YES;
        }

        // If confirmation is required ..
        if (confirm) {
            int choice = saveable.promptToSaveOnClose();

            // Branch on the user choice.
            // The choice id is based on the order of button labels above.
            if (choice != ISaveablePart2.YES) {
                return (choice == SaveableHelper.USER_RESPONSE ? ISaveablePart2.DEFAULT
                        : choice);
            }
        }

        // Create save block.
        IRunnableWithProgress progressOp = new IRunnableWithProgress() {
            @Override
            public void run(IProgressMonitor monitor) {
                IProgressMonitor monitorWrap =
                        new EventLoopProgressMonitor(monitor);
                saveable.doSave(monitorWrap);
            }
        };

        // Do the save.
        if (!runProgressMonitorOperation(WorkbenchMessages.Save,
                progressOp,
                window)) {
            return ISaveablePart2.CANCEL;
        }
        return ISaveablePart2.YES;
    }

    /**
     * Runs a progress monitor operation. Returns true if success, false if
     * canceled.
     */
    private static boolean runProgressMonitorOperation(String opName,
            IRunnableWithProgress progressOp, IWorkbenchWindow window) {
        return runProgressMonitorOperation(opName, progressOp, window, window);
    }

    /**
     * Runs a progress monitor operation. Returns true if success, false if
     * canceled or an error occurred.
     */
    private static boolean runProgressMonitorOperation(String opName,
            final IRunnableWithProgress progressOp,
            final IRunnableContext runnableContext,
            final IShellProvider shellProvider) {
        final boolean[] success = new boolean[] { false };
        IRunnableWithProgress runnable = new IRunnableWithProgress() {
            @Override
            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException, InterruptedException {
                progressOp.run(monitor);
                // Only indicate success if the monitor wasn't canceled
                if (!monitor.isCanceled())
                    success[0] = true;
            }
        };

        try {
            runnableContext.run(false, true, runnable);
        } catch (InvocationTargetException e) {
            String title =
                    NLS.bind(WorkbenchMessages.EditorManager_operationFailed,
                            opName);
            Throwable targetExc = e.getTargetException();
            WorkbenchPlugin.log(title, new Status(IStatus.WARNING,
                    PlatformUI.PLUGIN_ID, 0, title, targetExc));
            StatusUtil.handleStatus(title,
                    targetExc,
                    StatusManager.SHOW,
                    shellProvider.getShell());
            // Fall through to return failure
        } catch (InterruptedException e) {
            // The user pressed cancel. Fall through to return failure
        } catch (OperationCanceledException e) {
            // The user pressed cancel. Fall through to return failure
        }
        return success[0];
    }

    /**
     * For each part (view or editor) in the given list, attempts to convert it
     * to one or more saveable models. Duplicate models are removed. If closing
     * is true, then models that will remain open in parts other than the given
     * parts are removed.
     * 
     * @param parts
     *            the parts (list of IViewPart or IEditorPart)
     * @param closing
     *            whether the parts are being closed
     * @param addNonPartSources
     *            whether non-part sources should be added (true for the Save
     *            All action, see bug 139004)
     * @return the dirty models
     */
    private static List convertToSaveables(List parts, boolean closing,
            boolean addNonPartSources) {
        ArrayList result = new ArrayList();
        HashSet seen = new HashSet();
        for (Iterator i = parts.iterator(); i.hasNext();) {
            IWorkbenchPart part = (IWorkbenchPart) i.next();
            Saveable[] saveables = getSaveables(part);
            for (int j = 0; j < saveables.length; j++) {
                Saveable saveable = saveables[j];
                if (saveable.isDirty() && !seen.contains(saveable)) {
                    seen.add(saveable);
                    if (!closing
                            || closingLastPartShowingModel(saveable,
                                    parts,
                                    part.getSite().getPage())) {
                        result.add(saveable);
                    }
                }
            }
        }
        if (addNonPartSources) {
            SaveablesList saveablesList =
                    (SaveablesList) PlatformUI.getWorkbench()
                            .getService(ISaveablesLifecycleListener.class);
            ISaveablesSource[] nonPartSources =
                    saveablesList.getNonPartSources();
            for (int i = 0; i < nonPartSources.length; i++) {
                Saveable[] saveables = nonPartSources[i].getSaveables();
                for (int j = 0; j < saveables.length; j++) {
                    Saveable saveable = saveables[j];
                    if (saveable.isDirty() && !seen.contains(saveable)) {
                        seen.add(saveable);
                        result.add(saveable);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Returns the saveable models provided by the given part. If the part does
     * not provide any models, a default model is returned representing the
     * part.
     * 
     * @param part
     *            the workbench part
     * @return the saveable models
     */
    private static Saveable[] getSaveables(IWorkbenchPart part) {
        if (part instanceof ISaveablesSource) {
            ISaveablesSource source = (ISaveablesSource) part;
            return source.getSaveables();
        }
        return new Saveable[] { new DefaultSaveable(part) };
    }

    /**
     * Returns true if, in the given page, no more parts will reference the
     * given model if the given parts are closed.
     * 
     * @param model
     *            the model
     * @param closingParts
     *            the parts being closed (list of IViewPart or IEditorPart)
     * @param page
     *            the page
     * @return <code>true</code> if no more parts in the page will reference the
     *         given model, <code>false</code> otherwise
     */
    private static boolean closingLastPartShowingModel(Saveable model,
            List closingParts, IWorkbenchPage page) {
        HashSet closingPartsWithSameModel = new HashSet();
        for (Iterator i = closingParts.iterator(); i.hasNext();) {
            IWorkbenchPart part = (IWorkbenchPart) i.next();
            Saveable[] models = getSaveables(part);
            if (Arrays.asList(models).contains(model)) {
                closingPartsWithSameModel.add(part);
            }
        }
        IWorkbenchPartReference[] pagePartRefs =
                getAllParts((WorkbenchPage) page);
        HashSet pagePartsWithSameModels = new HashSet();
        for (int i = 0; i < pagePartRefs.length; i++) {
            IWorkbenchPartReference partRef = pagePartRefs[i];
            IWorkbenchPart part = partRef.getPart(false);
            if (part != null) {
                Saveable[] models = getSaveables(part);
                if (Arrays.asList(models).contains(model)) {
                    pagePartsWithSameModels.add(part);
                }
            }
        }
        for (Iterator i = closingPartsWithSameModel.iterator(); i.hasNext();) {
            IWorkbenchPart part = (IWorkbenchPart) i.next();
            pagePartsWithSameModels.remove(part);
        }
        return pagePartsWithSameModels.isEmpty();
    }

    /**
     * Returns all parts that are owned by this page
     * 
     * @return
     */
    private static IWorkbenchPartReference[] getAllParts(WorkbenchPage page) {
        IViewReference[] views = page.getViewReferences();
        IEditorReference[] editors = page.getEditorReferences();

        IWorkbenchPartReference[] result =
                new IWorkbenchPartReference[views.length + editors.length];
        int resultIdx = 0;

        for (int i = 0; i < views.length; i++) {
            result[resultIdx++] = views[i];
        }

        for (int i = 0; i < editors.length; i++) {
            result[resultIdx++] = editors[i];
        }

        return result;
    }
}
