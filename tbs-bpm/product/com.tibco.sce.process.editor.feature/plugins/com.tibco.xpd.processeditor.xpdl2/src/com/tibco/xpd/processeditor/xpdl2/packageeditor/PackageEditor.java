/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.packageeditor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessDialogUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.util.ShowViewUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.resources.wc.NotificationPropertyChangeEvent;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.edit.util.IGotoEObject;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyForTemporaryStream;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl.Xpdl2FileType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Dummy package editor to allow navigation to the process editor.
 * 
 * @author nwilson
 */
public class PackageEditor extends FormEditor implements IGotoMarker,
        IGotoEObject, PropertyChangeListener, INotifyChangedListener,
        ISaveablePart2, ITabbedPropertySheetPageContributor, ISaveablesSource {

    /**
     * 
     */
    private static final String PROCESS_PACKAGE_EDITOR_PAGE_ID =
            "ProcessPackageEditor"; //$NON-NLS-1$

    public static String PACKAGE_EDITOR_ID =
            "com.tibco.xpd.packageeditor.editor"; //$NON-NLS-1$

    private boolean invalidXpdlFile;

    private boolean outsideSpecialFolder;

    private boolean fromGotoEObject = false;

    private PackageEditorFormPage formPage;

    private AbstractWorkingCopy workingCopy;

    private boolean workingCopyCreatedHere = false;

    private static final Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    private static final String PROJECT_EXPLORER_VIEWID =
            "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    /**
     * Not implemented.
     * 
     * @param monitor
     *            n/a.
     * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public void doSave(IProgressMonitor monitor) {
        // Ask user whether they want to save all processes in this package
        boolean canSave =
                ProcessDialogUtil.canSavePackage(getEditorSite().getShell());

        if (canSave) {
            savePackage((IFileEditorInput) getEditorInput());
        }

    }

    /**
     * Save this package.
     * 
     * @param editorInput
     */
    private void savePackage(IFileEditorInput editorInput) {
        IFile file = editorInput.getFile();
        try {
            if (file != null) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
                if (wc != null) {
                    wc.save();
                }
            }
        } catch (IOException e) {
            ErrorDialog.openError(getEditorSite().getShell(),
                    "Error saving",
                    String.format("There was a problem saving %s.",
                            file.getName()),
                    new Status(IStatus.ERROR, Xpdl2ProcessEditorPlugin.ID,
                            e.getLocalizedMessage(), e));
        } finally {
            firePropertyChange(IEditorPart.PROP_DIRTY);
        }

    }

    /**
     * Not implemented.
     * 
     * @see org.eclipse.ui.part.EditorPart#doSaveAs()
     */
    @Override
    public void doSaveAs() {
    }

    /**
     * @param site
     *            The editor site.
     * @param input
     *            The editor input.
     * @throws PartInitException
     *             never.
     * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite,
     *      org.eclipse.ui.IEditorInput)
     */
    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        super.init(site, input);

        /*
         * XPD-1140: Don't not load the working copy _just_ because there's no
         * active workbench page (yet!)
         */
        /*
         * The file is only valid Studio XPDL if it has a FormatVerison extended
         * attribute defined.
         */
        boolean isStudioXpdl = false;

        if (input instanceof IFileEditorInput) {
            IFile file = ((IFileEditorInput) input).getFile();
            WorkingCopy workingCopyObj =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(file);
            if (workingCopyObj instanceof AbstractWorkingCopy) {

                workingCopy = (AbstractWorkingCopy) workingCopyObj;

                /*
                 * If the xpdl is loaded into working copy then it must be the
                 * correct FormatVersion BUT it may not be loaded if nothing has
                 * attempted to access it yet (for instance no validation, no
                 * expanding in the project explorer etc)
                 * 
                 * calling getRootElement will always attempt the load if it has
                 * not been loaded yet.
                 */
                if (workingCopy.getRootElement() != null
                        && workingCopy.isLoaded()) {
                    isStudioXpdl = true;

                } else {

                    if (workingCopy.isInvalidVersion()) {
                        /*
                         * If we found out enough to see that it was an older
                         * version of studio xpdl then it is indeed Studio xpdl.
                         */
                        isStudioXpdl = true;

                        final ProjectAssetMigrationManager migrationManager =
                                ProjectAssetMigrationManager.getInstance();
                        final IProject project = getProject(workingCopy);
                        WorkspaceModifyOperation op = null;
                        if (project != null && migrationManager
                                .doesProjectNeedMigrating(project)) {
                            /*
                             * Project needs migrating so provide user with
                             * choice to migrate entire project.
                             */
                            if (MessageDialog.openQuestion(getSite().getShell(),
                                    Messages.PackageEditor_InvalidVersionMsgBox_label,
                                    Messages.PackageEditor_ModelCannotBeLoadedErrNo_shortdesc)) {
                                op = new WorkspaceModifyOperation() {

                                    @Override
                                    protected void execute(
                                            IProgressMonitor monitor)
                                            throws CoreException,
                                            InvocationTargetException,
                                            InterruptedException {

                                        migrationManager.migrate(project,
                                                monitor);

                                        /*
                                         * Nominally the project migrate should
                                         * have addressed the migration of the
                                         * xpdl we are trying to open.
                                         * 
                                         * HOWEVER there is a slight chance
                                         * another project asset said that it
                                         * required migration when the process
                                         * asset is up-to-date (and the user has
                                         * manually copied in old xpdl whilst
                                         * project is in this state).
                                         * 
                                         * In that case the xpdl will NOT be
                                         * migrated because the project
                                         * migration only migrates individual
                                         * files of assets that say they need
                                         * migration.
                                         * 
                                         * So just in case, re-check the invalid
                                         * version and execute the migration for
                                         * our one xpdl if necessary.
                                         */

                                        if (workingCopy.isInvalidVersion()) {
                                            workingCopy.migrate();
                                        }
                                    }
                                };
                            }
                        } else {
                            /*
                             * Project is up-to-date so provide user with choice
                             * to migrate this file.
                             */
                            if (MessageDialog.openQuestion(getSite().getShell(),
                                    Messages.PackageEditor_InvalidVersionMsgBox_label,
                                    Messages.PackageEditor_ModelCannotBeLoadedErrYes_shortdesc)) {
                                op = new WorkspaceModifyOperation() {

                                    @Override
                                    protected void execute(
                                            IProgressMonitor monitor)
                                            throws CoreException,
                                            InvocationTargetException,
                                            InterruptedException {
                                        monitor.beginTask(
                                                Messages.PackageEditor_MigratingStatus_label,
                                                IProgressMonitor.UNKNOWN);
                                        workingCopy.migrate();
                                    }

                                };
                            }
                        }

                        if (op != null) {
                            try {
                                new ProgressMonitorDialog(getSite().getShell())
                                        .run(true, true, op);
                            } catch (InvocationTargetException e) {

                                LOG.error(e.getTargetException());
                            } catch (InterruptedException e) {
                                // Do nothing
                                LOG.error(e);
                            }
                        }
                    } else if (workingCopy.isInvalidFile()) {
                        invalidXpdlFile = true;
                    }
                }
            }

        } else if (input instanceof IStorageEditorInput) {
            /*
             * XPD-1140: StorageEditorInput is what we get for anything that's
             * an xpdl file but is not a woprkspce file (like for opening from
             * repository etc).
             */
            IStorageEditorInput storageInput = (IStorageEditorInput) input;

            try {
                /*
                 * IStorageEditorInput has the full path of the original
                 * resource (not the one in repo). So In order to try and make
                 * things consistent in the working copy we will find the actual
                 * resource and pass that in (although the EMF resource will be
                 * loaded from the inputStream from repo version)
                 */
                IPath fullPath = storageInput.getStorage().getFullPath();

                IResource resource = ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(fullPath);
                // .findMember(fullPath);
                if (resource instanceof IFile) {
                    workingCopy = new Xpdl2WorkingCopyForTemporaryStream(
                            resource, Xpdl2FileType.PROCESS,
                            storageInput.getStorage().getContents());

                    workingCopyCreatedHere = true;

                    /* Force load. */
                    EObject root = workingCopy.getRootElement();
                    if (workingCopy.isLoaded()
                            || workingCopy.isInvalidVersion()) {
                        isStudioXpdl = true;

                    }
                }

            } catch (CoreException e) {
                Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e,
                        "Failed loading XPDL package from temporary input stream."); //$NON-NLS-1$
            }
        }

        if (!isStudioXpdl) {
            throw new PartInitException(new Status(IStatus.OK,
                    Xpdl2ProcessEditorPlugin.ID,
                    "File does not appear to have been created by TIBCO Business Studio.")); //$NON-NLS-1$
        }

        /*
         * XPD-1140: Show correct title to same as editor title (display name
         * (tokenname) ( + [Read-Only] as appropriate)
         */

        String title = getEditorInput().getName();

        if (workingCopy != null) {
            /* Force load else isReadOnly returns false even when it is! */
            workingCopy.getRootElement();
            if (workingCopy.isReadOnly()) {
                title = title + " " + Messages.PackageEditor_ReadOnly_label; //$NON-NLS-1$
            }
        }

        setPartName(title);

        /*
         * XPD-1140: Package editor was not marking as dirty or updating title
         * when package name changed etc.
         */
        if (workingCopy != null && workingCopy.getRootElement() != null) {
            AdapterFactory adapterFactory = workingCopy.getAdapterFactory();
            if (adapterFactory != null) {
                ItemProviderAdapter ip = (ItemProviderAdapter) adapterFactory
                        .adapt(workingCopy.getRootElement(),
                                workingCopy.getRootElement().eClass()
                                        .getEPackage());
                ip.addListener(this);
            }
            workingCopy.addListener(this);
        }
        return;
    }

    /**
     * @return false.
     * @see org.eclipse.ui.part.EditorPart#isDirty()
     */
    @Override
    public boolean isDirty() {
        IFileEditorInput input = (IFileEditorInput) getEditorInput();

        if (input != null) {
            IFile file = input.getFile();
            if (file != null) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
                if (wc != null) {
                    return wc.isWorkingCopyDirty();
                }
            }
        }

        return false;
    }

    /**
     * @return false.
     * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
     */
    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    /**
     * Immediately closes the package editor. The user shouldn't see it.
     * 
     * @param parent
     *            The parent composite.
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    /*
     * @Override public void createPartControl(Composite parent) {
     * 
     * Display.getCurrent().asyncExec(new Runnable() { public void run() { if
     * (invalidXpdlFile) { if (!fromProblems) {
     * MessageDialog.openInformation(getSite().getShell(),
     * Messages.PackageEditor_messageTitle, Messages.PackageEditor_invalidFile);
     * } } else if (outsideSpecialFolder) { if (!fromProblems) {
     * MessageDialog.openInformation(getSite().getShell(),
     * Messages.PackageEditor_messageTitle,
     * Messages.PackageEditor_outsideProcessPackage); } } else { if
     * (!fromGotoEObject && !fromProblems && !fromProjectExplorer &&
     * !fromBookmarks) { MessageDialog .openInformation(getSite().getShell(),
     * Messages.PackageEditor_messageTitle,
     * Messages.PackageEditor_projectExplorerExplaination); } }
     * getSite().getPage().closeEditor(PackageEditor.this, false); } });
     * 
     * }
     */

    /*
     * XPD-1140: Used to override setFocus() and do nothing here BUT that caused
     * a nasty problem that when package editor is opened it is not fully
     * activitated and the currently activated window (project explorer is niot
     * fully deactivated, so when user uses project explorer again eclipse
     * thinks it's already activate and doesn't reacivate it. So selection
     * doesn't activate properties and so on.
     */

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();

        // If Working Copy changed or removed then close editor
        // else if Working Copy dirtied then fire dirty property change
        if (propName.equals(WorkingCopy.PROP_RELOADED)
                || propName.equals(WorkingCopy.PROP_REMOVED)) {
            closeEditor();
        } else if (propName.equals(WorkingCopy.PROP_DIRTY)) {
            firePropertyChange(IEditorPart.PROP_DIRTY);
        } else if (propName.equals(WorkingCopy.CHANGED)) {
            if (formPage != null
                    && evt instanceof NotificationPropertyChangeEvent) {
                Collection<Notification> notifications =
                        ((NotificationPropertyChangeEvent) evt)
                                .getNotifications();
                if (isRefreshRequired(notifications)) {
                    formPage.doRefreshSections();
                }
            }
        }
    }

    /**
     * @param notifications
     * @return
     */
    private boolean isRefreshRequired(Collection<Notification> notifications) {
        for (Notification notification : notifications) {
            Object notifier = notification.getNotifier();
            if (notifier instanceof Package) {
                return true;
            } else {
                if (notifier instanceof Process
                        || notifier instanceof ProcessInterfaces) {
                    return true;
                }

            }
        }
        return false;
    }

    private void closeEditor() {
        Display d = PlatformUI.getWorkbench().getDisplay();
        d.syncExec(new Runnable() {
            @Override
            public void run() {
                getSite().getPage().closeEditor(PackageEditor.this, false);
            }
        });
    }

    /**
     * Navigates to the marker by openening the appropriate process editor.
     * 
     * @param marker
     *            The marker to navigate to.
     * @see org.eclipse.ui.ide.IGotoMarker#gotoMarker(org.eclipse.core.resources.IMarker)
     */
    @Override
    public void gotoMarker(IMarker marker) {
        XpdProjectResourceFactory factory =
                XpdResourcesPlugin.getDefault().getXpdProjectResourceFactory(
                        marker.getResource().getProject());
        IResource res = marker.getResource();
        WorkingCopy workingCopy = factory.getWorkingCopy(res);
        if (workingCopy.isInvalidFile()) {
            return;
        }
        Package xpdlPackage = (Package) workingCopy.getRootElement();
        try {
            String location = (String) marker.getAttribute(IMarker.LOCATION);
            Resource resource = xpdlPackage.eResource();
            if (resource != null) {
                EObject target = resource.getEObject(location);
                if (target != null) {

                    if (!gotoProjectExplorerItem(target)) {

                        Process process = getProcess(target);
                        if (process != null) {
                            IConfigurationElement facConfig =
                                    XpdResourcesUIActivator
                                            .getEditorFactoryConfigFor(process);
                            if (facConfig != null) {
                                String editorId =
                                        facConfig.getAttribute("editorID"); //$NON-NLS-1$
                                EditorInputFactory f =
                                        (EditorInputFactory) facConfig
                                                .createExecutableExtension(
                                                        "factory"); //$NON-NLS-1$
                                IEditorInput input =
                                        f.getEditorInputFor(process);
                                IWorkbenchPage page = PlatformUI.getWorkbench()
                                        .getActiveWorkbenchWindow()
                                        .getActivePage();
                                IEditorPart part =
                                        IDE.openEditor(page, input, editorId);
                                if (part instanceof IGotoMarker) {
                                    ((IGotoMarker) part).gotoMarker(marker);
                                }
                            }
                        }
                    }
                }
            }
        } catch (CoreException e) {
            // Ignore.
        }
    }

    /**
     * @param target
     * @return true if the item is one that is displayed in the project explorer
     *         rather not teh diagram editor AND hence goto already performed!
     */
    private boolean gotoProjectExplorerItem(EObject target) {

        /*
         * Don't show the project explorer in the RCP application
         */
        if (!XpdResourcesPlugin.isRCP()) {
            if (target instanceof Package || target instanceof Participant
                    || target instanceof DataField
                    || target instanceof FormalParameter
                    || target instanceof TypeDeclaration
                    || target instanceof InterfaceMethod
                    || target instanceof ErrorMethod) {

                try {

                    ShowViewUtil.selectItemInProjectExplorer(target);

                } catch (PartInitException e) {

                    LOG.error(e);
                }

                return true;
            }
        }
        return false;
    }

    /**
     * @param target
     *            The target marker object.
     * @return The process that the target belongs to.
     */
    private Process getProcess(EObject target) {
        return Xpdl2ModelUtil.getProcess(target);
    }

    /**
     * @param target
     *            The target marker object.
     * @return The process interface that the target belongs to.
     */
    private ProcessInterface getProcessInterface(EObject target) {
        return ProcessInterfaceUtil.getProcessInterface(target);
    }

    /**
     * Open process editor for given eobject and reveal it.
     */
    @Override
    public boolean gotoEObject(boolean bSelectObject, EObject... target) {
        fromGotoEObject = true;
        try {
            if (target != null && target.length > 0) {
                if (!gotoProjectExplorerItem(target[0])) {
                    Process process = getProcess(target[0]);
                    if (process != null) {
                        openActivateEditor(bSelectObject, process, target);
                    } else {
                        ProcessInterface procIfc =
                                getProcessInterface(target[0]);
                        if (procIfc != null) {
                            return openActivateEditor(bSelectObject,
                                    procIfc,
                                    target);
                        }

                    }
                }
            }
        } catch (CoreException e) {

        }
        return false;
    }

    /**
     * @param bSelectObject
     * @param process
     * @param target
     * @throws CoreException
     * @throws PartInitException
     */
    private boolean openActivateEditor(boolean bSelectObject, Object obj,
            EObject... target) throws CoreException, PartInitException {
        // Open/activate the process editor
        IConfigurationElement facConfig =
                XpdResourcesUIActivator.getEditorFactoryConfigFor(obj);
        if (facConfig != null) {
            String editorId = facConfig.getAttribute("editorID"); //$NON-NLS-1$
            EditorInputFactory f = (EditorInputFactory) facConfig
                    .createExecutableExtension("factory"); //$NON-NLS-1$
            IEditorInput input = f.getEditorInputFor(obj);
            IWorkbenchPage page = PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getActivePage();
            IEditorPart part = IDE.openEditor(page, input, editorId);
            if (part instanceof IGotoEObject) {
                return ((IGotoEObject) part).gotoEObject(bSelectObject, target);
            }
        }
        return false;
    }

    /**
     * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
     * 
     */
    @Override
    protected void addPages() {
        try {
            formPage = new PackageEditorFormPage(this,
                    PROCESS_PACKAGE_EDITOR_PAGE_ID, this.getPartName(),
                    workingCopy);
            addPage(formPage);

        } catch (PartInitException e) {

        }
    }

    /**
     * Get the project that contains the resource managed by the given working
     * copy.
     * 
     * @param wc
     * @return project if found, <code>null</code> otherwise.
     */
    private IProject getProject(WorkingCopy wc) {
        IProject project = null;

        List<IResource> resources = wc.getEclipseResources();
        if (resources != null && !resources.isEmpty()) {
            project = resources.get(0).getProject();
        }
        return project;
    }

    /**
     * 
     * @return the parent {@link IProject} of this PackageEditor, else return
     *         <code>null</code> if the working copy of the editor could not be
     *         loaded.
     */
    public IProject getProject() {

        if (workingCopy != null) {
            return getProject(workingCopy);
        }

        return null;
    }

    /**
     * Refreshes the Form Page associated with this Package Editor.
     */
    public void refresh() {
        if (formPage != null) {
            formPage.doRefreshSections();
        }
    }

    /**
     * @see org.eclipse.ui.forms.editor.FormEditor#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (workingCopy != null) {
            workingCopy.removeListener(this);

            EObject element = workingCopy.getRootElement();
            if (element != null) {
                ItemProviderAdapter ip =
                        (ItemProviderAdapter) workingCopy.getAdapterFactory()
                                .adapt(element, element.eClass().getEPackage());
                ip.removeListener(this);
            }

            /*
             * XPD-1140: If we created the working copy here then we should
             * dispose it.
             */
            if (workingCopyCreatedHere) {
                (workingCopy).dispose();
            }
        }

        super.dispose();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.INotifyChangedListener#notifyChanged(org
     * .eclipse.emf.common.notify.Notification)
     */
    @Override
    public void notifyChanged(Notification notification) {
        /*
         * XPD-1128: Reset form title when things change in case package name
         * changed.
         */
        if (workingCopy != null && formPage != null
                && formPage.getForm() != null
                && !formPage.getForm().isDisposed()) {

            String packageName =
                    WorkingCopyUtil.getText(workingCopy.getRootElement());

            final String title = workingCopy.isReadOnly()
                    ? packageName + " " + Messages.PackageEditor_ReadOnly_label //$NON-NLS-1$
                    : packageName;

            /*
             * XPD-7800: When the notifychnaged is called from a non UI thread
             * this throws an "Invalid Thread Access" Exception. Hence we check
             * if it is called from non UI thread we do the
             * 'formPage.getForm().setText(title)' by spawning a async UI
             * thread.
             */
            if (Display.getCurrent() == null) {
                Display.getDefault().asyncExec(new Runnable() {

                    @Override
                    public void run() {
                        formPage.getForm().setText(title);

                    }
                });
            } else {
                /* Handle normally when UI thread is Available. */
                formPage.getForm().setText(title);
            }
        }
        return;
    }

    /**
     * @see org.eclipse.ui.part.MultiPageEditorPart#getAdapter(java.lang.Class)
     * 
     * @param adapter
     * @return
     */
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == IPropertySheetPage.class) {
            TabbedPropertySheetPage p = new TabbedPropertySheetPage(this);
            return p;
        }
        return super.getAdapter(adapter);
    }

    @Override
    public int promptToSaveOnClose() {
        /*
         * Don't prompt the user to save on editor close when running in RCP
         * application
         */
        if (XpdResourcesPlugin.isRCP()) {
            return NO;
        }

        return DEFAULT;
    }

    public static class BpmnPackageFilter implements IFilter {

        @Override
        public boolean select(Object toTest) {
            if (toTest instanceof Package) {
                IFile file = WorkingCopyUtil.getFile((Package) toTest);
                if (file != null) {
                    if (Xpdl2ResourcesConsts.XPDL_EXTENSION
                            .equals(file.getFileExtension())) {
                        return true;
                    }
                }
            }
            return false;
        }

    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        /*
         * Use the same contributor id as the Process Editor as this will avoid
         * us duplicating all extensions for Tabs,Sections,etc. for this editor.
         */
        return AbstractProcessDiagramEditor.PROCESSEDITOR_PROPERTY_CONTRIBUTOR_ID;
    }

    /**
     * @see org.eclipse.ui.ISaveablesSource#getSaveables()
     * 
     * @return
     */
    @Override
    public Saveable[] getSaveables() {
        return getActiveSaveables();
    }

    /**
     * @see org.eclipse.ui.ISaveablesSource#getActiveSaveables()
     * 
     * @return
     */
    @Override
    public Saveable[] getActiveSaveables() {

        if (workingCopy != null) {

            if (workingCopy.getSaveable() != null) {

                return new Saveable[] { workingCopy.getSaveable() };
            }
        }
        return new Saveable[0];
    }
}
