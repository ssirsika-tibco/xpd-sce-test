/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.OpenAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.packageeditor.AddBusinessServiceAction;
import com.tibco.xpd.processeditor.xpdl2.packageeditor.AddCaseServiceAction;
import com.tibco.xpd.processeditor.xpdl2.packageeditor.AddPageflowAction;
import com.tibco.xpd.processeditor.xpdl2.packageeditor.AddProcessAction;
import com.tibco.xpd.processeditor.xpdl2.packageeditor.AddProcessIfcAction;
import com.tibco.xpd.processeditor.xpdl2.packageeditor.AddServiceProcessAction;
import com.tibco.xpd.processeditor.xpdl2.packageeditor.AddServiceProcessIfcAction;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.RCPConsts;
import com.tibco.xpd.rcp.internal.models.actions.ModelAction;
import com.tibco.xpd.rcp.internal.models.actions.NewProcessAction;
import com.tibco.xpd.rcp.internal.models.actions.NewProcessPackageAction;
import com.tibco.xpd.rcp.internal.models.actions.OpenEditorForFileAction;
import com.tibco.xpd.rcp.internal.models.actions.OpenProcessAction;
import com.tibco.xpd.rcp.internal.models.actions.OpenProcessInterfaceAction;
import com.tibco.xpd.rcp.internal.models.actions.SeparatorAction;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener.RCPResourceEventType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.wc.NotificationPropertyChangeEvent;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process Package provider.
 * 
 * @author njpatel
 * 
 */
public class ProcessModelProvider extends AbstractModelProvider implements
        IOpenListener {

    public static final String PROCESSES_SFOLDER_KIND =
            RCPConsts.PROCESSES_SFOLDER_KIND;

    private ToolBarManager toolBarManager;

    private AddProcessAction addProcessAction;

    private AddPageflowAction addPageFlow;

    private AddBusinessServiceAction addBizService;

    private AddCaseServiceAction addCaseService;

    private AddServiceProcessAction addServiceProcess;

    private AddProcessIfcAction addProcIf;

    private AddServiceProcessIfcAction addServiceProcIf;

    private NewProcessPackageAction newProcessPackageAction;

    public ProcessModelProvider(IWorkbenchWindow window) {

        super(window);
        addProcessAction = new AddProcessAction(null);
        addProcessAction.setEnabled(false);
        addPageFlow = new AddPageflowAction(null);
        addPageFlow.setEnabled(false);
        addBizService = new AddBusinessServiceAction(null);
        addBizService.setEnabled(false);
        addCaseService = new AddCaseServiceAction(null);
        addCaseService.setEnabled(false);
        addServiceProcess = new AddServiceProcessAction(null);
        addServiceProcess.setEnabled(false);
        addProcIf = new AddProcessIfcAction(null);
        addProcIf.setEnabled(false);
        addServiceProcIf = new AddServiceProcessIfcAction(null);
        addServiceProcIf.setEnabled(false);
        newProcessPackageAction =
                new NewProcessPackageAction(getWindow(),
                        Messages.ProcessModelProvider_newProcessPackage_action,
                        RCPImages.NEW_PROCESS_PACKAGE.getPath(), null, null);
        newProcessPackageAction.setEnabled(false);
    }

    /**
     * @see com.tibco.xpd.rcp.internal.models.AbstractModelProvider#hasActions()
     * 
     * @return
     */
    @Override
    public boolean hasActions(IProject project) {
        if (project != null && project.isAccessible()) {
            List<SpecialFolder> sFolders =
                    getSpecialFolders(project, RCPConsts.PROCESSES_SFOLDER_KIND);

            if (sFolders != null && !sFolders.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ModelAction> getActions(IProject project) {
        List<ModelAction> actions = new ArrayList<ModelAction>();

        if (project != null && project.isAccessible()) {
            List<SpecialFolder> sFolders =
                    getSpecialFolders(project, PROCESSES_SFOLDER_KIND);

            if (sFolders != null && !sFolders.isEmpty()) {
                List<WorkingCopy> wcs =
                        getWorkingCopies(sFolders, RCPConsts.PROCESSES_FILEEXT);
                boolean doAddSep = false;

                /*
                 * Add action to add a new Package file.
                 */
                actions.add(new NewProcessPackageAction(getWindow(),
                        Messages.ProcessModelProvider_newProcessPackage_action,
                        RCPImages.ADD.getPath(), project, sFolders.get(0)));

                for (WorkingCopy wc : wcs) {
                    EObject element = wc.getRootElement();
                    if (element instanceof Package) {
                        final Package pkg = (Package) element;
                        // Add separators between packages
                        if (doAddSep) {
                            actions.add(new SeparatorAction());
                        } else {
                            doAddSep = true;
                        }

                        // Add open package action
                        actions.add(new OpenEditorForFileAction(getWindow(),
                                getLabel(pkg), getImage(pkg), (IFile) wc
                                        .getEclipseResources().get(0),
                                RCPConsts.PACKAGE_EDITOR_ID));

                        // add new process option
                        actions.add(new NewProcessAction(
                                getWindow(),
                                Messages.ProcessModelProvider_newProcess_action,
                                RCPImages.ADD.getPath(), (IFile) wc
                                        .getEclipseResources().get(0)));

                        // Add all process interfaces
                        ProcessInterfaces processInterfaces =
                                ProcessInterfaceUtil.getProcessInterfaces(pkg);
                        if (processInterfaces != null) {
                            for (ProcessInterface processInteface : sort(processInterfaces
                                    .getProcessInterface())) {
                                actions.add(new OpenProcessInterfaceAction(
                                        getWindow(), getLabel(processInteface),
                                        getImage(processInteface), wc,
                                        processInteface));
                            }
                        }

                        // Add all processes
                        EList<Process> processes = sort(pkg.getProcesses());
                        if (!processes.isEmpty()) {
                            for (Process process : processes) {
                                actions.add(new OpenProcessAction(getWindow(),
                                        getLabel(process), getImage(process),
                                        wc, process));
                            }
                        }
                    }
                }
            }
        }

        return actions;
    }

    @Override
    public boolean isAffectingEvent(RCPResourceEventType eventType,
            Object eventObj) {
        if (eventType == RCPResourceEventType.CHANGED) {
            if (eventObj instanceof NotificationPropertyChangeEvent) {
                for (Notification notification : ((NotificationPropertyChangeEvent) eventObj)
                        .getNotifications()) {
                    Object notifier = notification.getNotifier();

                    if (notifier instanceof Package
                            || notifier instanceof ProcessInterfaces) {
                        return true;
                    } else if (notification.getFeature() == Xpdl2Package.eINSTANCE
                            .getNamedElement_Name()
                            && (notifier instanceof Package
                                    || notifier instanceof Process || notifier instanceof ProcessInterface)) {
                        return true;
                    } else if (notifier instanceof Process
                            && ((EObject) notifier).eIsProxy()) {
                        // This will indicate that a process has possibly been
                        // removed
                        return true;
                    }
                }
            } else if (eventObj instanceof PropertyChangeEvent) {
                /*
                 * Check if an XPDL working copy has been reloaded
                 */
                if (WorkingCopy.PROP_RELOADED
                        .equals(((PropertyChangeEvent) eventObj)
                                .getPropertyName())
                        && ((PropertyChangeEvent) eventObj).getSource() instanceof Xpdl2WorkingCopyImpl) {
                    return true;
                }
            }
        } else if (eventType == RCPResourceEventType.ADDED
                || eventType == RCPResourceEventType.REMOVED) {
            if (eventObj instanceof IFile) {
                // If an xpdl file is added then update section
                IFile file = (IFile) eventObj;
                if (RCPConsts.PROCESSES_FILEEXT.equals(file.getFileExtension())) {
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(file.getProject());
                    if (config != null) {
                        SpecialFolder sf =
                                config.getSpecialFolders()
                                        .getFolderContainer(file);
                        if (sf != null
                                && RCPConsts.PROCESSES_SFOLDER_KIND.equals(sf
                                        .getKind())) {
                            return true;
                        }
                    }
                }
            } else if (eventObj instanceof IProject) {
                // Project added or removed so update
                return true;
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.models.AbstractModelProvider#isElementOfInterest(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public boolean isElementOfInterest(Object element) {
        return element instanceof Package || element instanceof Process
                || element instanceof ProcessInterface;
    }

    // Only show package folders
    private static final String[] specialFolderFilter =
            { Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND };

    @Override
    protected Object[] doGetChildren(Object parentElement) {
        Object[] children = null;

        if (parentElement instanceof IProject) {
            IProject project = (IProject) parentElement;
            if (project != null && project.isAccessible()) {
                List<SpecialFolder> sFolders =
                        getSpecialFolders(project, PROCESSES_SFOLDER_KIND);

                if (sFolders != null && !sFolders.isEmpty()) {
                    List<WorkingCopy> wcs =
                            getWorkingCopies(sFolders,
                                    RCPConsts.PROCESSES_FILEEXT);
                    List<EObject> root = new ArrayList<EObject>();
                    for (WorkingCopy wc : wcs) {
                        EObject element = wc.getRootElement();
                        if (element != null) {
                            root.add(element);
                        }
                    }
                    children = root.toArray();
                }
            }
        } else if (parentElement instanceof Package) {
            Package pkg = (Package) parentElement;
            List<EObject> xpdPackagesChildren = new ArrayList<EObject>();
            // Add all process interfaces
            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pkg);
            if (processInterfaces != null) {
                xpdPackagesChildren.addAll(processInterfaces
                        .getProcessInterface());
            }

            // Add all processes
            EList<Process> processes = sort(pkg.getProcesses());
            if (!processes.isEmpty()) {
                xpdPackagesChildren.addAll(processes);
            }
            children = xpdPackagesChildren.toArray();
        }
        // Return the children
        return children != null ? children : new Object[0];
    }

    @Override
    protected Object doGetParent(Object element) {
        Object parent = null;

        if (element instanceof INavigatorGroup) {
            // This is a navigator group, so return it's parent
            parent = ((INavigatorGroup) element).getParent();

        } else if (element instanceof EObject) {
            EObject eo = (EObject) element;

            // Get working copy of the EObject
            WorkingCopy wc = getWorkingCopy(eo);

            if (wc != null) {
                // If this is a Package then the parent will be the IFile
                if (eo instanceof Package) {
                    // Get the IResource of the working copy
                    parent = wc.getEclipseResources().get(0);

                }
            }
        }

        return parent;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doGetPipelinedChildren(Object aParent, Set theCurrentChildren) {
    }

    @Override
    protected boolean doHasChildren(Object element) {
        boolean gotChildren = false;
        if (element instanceof Package) {
            gotChildren = true;
        }
        return gotChildren;
    }

    @Override
    public String[] getSpecialFolderKindInclusion() {
        return specialFolderFilter;
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        TreeViewer oldViewer = getViewer();

        if (oldViewer != null) {
            // Unregister the tree listener
            oldViewer.removeTreeListener(this);
            oldViewer.removeOpenListener(this);
        }
        // This should register the new viewer
        super.inputChanged(viewer, oldInput, newInput);

        // Check if there is a new viewer, if it has then register the tree
        // listener
        TreeViewer newViewer = getViewer();

        if (newViewer != null) {
            newViewer.addTreeListener(this);
            newViewer.addOpenListener(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeViewerListener#treeCollapsed(org.eclipse
     * .jface.viewers.TreeExpansionEvent)
     */
    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
        // Do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeViewerListener#treeExpanded(org.eclipse
     * .jface.viewers.TreeExpansionEvent)
     */
    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        /*
         * If a packages XPDL file is expanded then we want to automatically
         * expand the package under it as well.
         */
        if (event.getElement() instanceof IFile) {
            WorkingCopy wc =
                    getWorkingCopyWithoutRegistering((IResource) event
                            .getElement());

            if (wc != null && wc.getRootElement() instanceof Package) {
                if (event.getSource() instanceof TreeViewer) {
                    final TreeViewer treeViewer =
                            (TreeViewer) event.getSource();
                    final Object element = event.getElement();
                    // Viewer may be busy so we need to queue this for the UI
                    // thread to run.
                    treeViewer.getControl().getDisplay()
                            .asyncExec(new Runnable() {

                                @Override
                                public void run() {
                                    if (treeViewer != null
                                            && !treeViewer.getControl()
                                                    .isDisposed()) {
                                        treeViewer.expandToLevel(element, 2);
                                    }
                                }
                            });

                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IOpenListener#open(org.eclipse.jface.viewers
     * .OpenEvent)
     */
    @Override
    public void open(OpenEvent event) {
        /*
         * If a Process was double-clicked on then open the process editor. If
         * the process tree has collapsed then re-expand it.
         */
        IStructuredSelection selection =
                (IStructuredSelection) event.getSelection();

        if (selection.size() == 1
                && (selection.getFirstElement() instanceof Process || selection
                        .getFirstElement() instanceof ProcessInterface)
                || selection.getFirstElement() instanceof Package) {
            NamedElement processOrInterface =
                    (NamedElement) selection.getFirstElement();

            if (event.getSource() instanceof TreeViewer) {
                TreeViewer viewer = (TreeViewer) event.getSource();

                // If the process tree has been collapsed then expand it
                if (!viewer.getExpandedState(processOrInterface)) {
                    viewer.setExpandedState(processOrInterface, true);
                }
                // Run the open action - to open the process
                OpenAction.run((IStructuredSelection) event.getSelection());
            }
        }
    }

    /**
     * @see com.tibco.xpd.rcp.internal.models.AbstractModelProvider#createContentLabelProvider()
     * 
     * @return
     */
    @Override
    protected ILabelProvider createContentLabelProvider() {
        return new ProcessOverviewLabelProvider();
    }

    /**
     * @see com.tibco.xpd.rcp.internal.models.AbstractModelProvider#getToolbarManager()
     * 
     * @return
     */
    @Override
    public ToolBarManager getToolbarManager() {

        if (toolBarManager == null) {

            toolBarManager = new ToolBarManager();
            toolBarManager.add(newProcessPackageAction);
            toolBarManager.add(addProcessAction);
            toolBarManager.add(addPageFlow);
            toolBarManager.add(addBizService);
            toolBarManager.add(addCaseService);
            toolBarManager.add(addServiceProcess);
            toolBarManager.add(addProcIf);
            toolBarManager.add(addServiceProcIf);
        }
        return toolBarManager;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.models.AbstractModelProvider#selectionChanged(org.eclipse.jface.viewers.ISelection)
     * 
     * @param selection
     */
    @Override
    public void selectionChanged(ISelection selection) {
        updateToolBarActions(selection);
    }

    /**
     * Updates the Tool Bar Actions
     * 
     * @param selection
     */
    private void updateToolBarActions(ISelection selection) {

        boolean changed = false;
        Object selectedItem = null;

        if (selection instanceof IStructuredSelection) {

            selectedItem = ((IStructuredSelection) selection).getFirstElement();
        }

        if (selectedItem instanceof EObject) {

            Package pkg = Xpdl2ModelUtil.getPackage((EObject) selectedItem);

            if (pkg != null) {

                addProcessAction.setDefaultSelection(pkg);
                addProcessAction.setEnabled(true);

                addPageFlow.setDefaultSelection(pkg);
                addPageFlow.setEnabled(true);

                addBizService.setDefaultSelection(pkg);
                addBizService.setEnabled(true);

                addCaseService.setDefaultSelection(pkg);
                addCaseService.setEnabled(true);

                addServiceProcess.setDefaultSelection(pkg);
                addServiceProcess.setEnabled(true);

                addProcIf.setDefaultSelection(pkg);
                addProcIf.setEnabled(true);

                addServiceProcIf.setDefaultSelection(pkg);
                addServiceProcIf.setEnabled(true);
            } else {

                addProcessAction.setEnabled(false);
                addPageFlow.setEnabled(false);
                addBizService.setEnabled(false);
                addCaseService.setEnabled(false);
                addProcIf.setEnabled(false);
                addServiceProcess.setEnabled(false);
                addServiceProcIf.setEnabled(false);
            }
            changed = true;

        } else if (selectedItem instanceof IProject) {
            IProject project =
                    (IProject) ((IStructuredSelection) selection)
                            .getFirstElement();
            if (newProcessPackageAction.getProject() == null
                    || newProcessPackageAction.getProject() != project) {
                List<SpecialFolder> sFolders =
                        getSpecialFolders(project, PROCESSES_SFOLDER_KIND);
                if (sFolders != null && !sFolders.isEmpty()) {
                    newProcessPackageAction.setProject(project);
                    newProcessPackageAction.setsFolder(sFolders.get(0));
                    newProcessPackageAction.setEnabled(true);
                }
            }
            addProcessAction.setEnabled(false);
            addPageFlow.setEnabled(false);
            addBizService.setEnabled(false);
            addCaseService.setEnabled(false);
            addProcIf.setEnabled(false);
            addServiceProcess.setEnabled(false);
            addServiceProcIf.setEnabled(false);
            changed = true;
        } else {
            addProcessAction.setEnabled(false);
            addPageFlow.setEnabled(false);
            addBizService.setEnabled(false);
            addCaseService.setEnabled(false);
            addProcIf.setEnabled(false);
            addServiceProcess.setEnabled(false);
            addServiceProcIf.setEnabled(false);
            changed = true;
        }
        if (changed) {
            getToolbarManager().update(true);
        }
    }

}
