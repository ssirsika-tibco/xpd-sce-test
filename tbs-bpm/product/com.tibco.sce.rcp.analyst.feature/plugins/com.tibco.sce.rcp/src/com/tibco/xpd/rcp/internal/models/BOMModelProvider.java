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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.util.IOpenEventListener;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditor;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.editor.IGotoObject;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.RCPConsts;
import com.tibco.xpd.rcp.internal.models.actions.ModelAction;
import com.tibco.xpd.rcp.internal.models.actions.NewBOMAction;
import com.tibco.xpd.rcp.internal.models.actions.OpenEditorForFileAction;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener.RCPResourceEventType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.wc.NotificationPropertyChangeEvent;
import com.tibco.xpd.xpdl2.Package;

/**
 * Business Object Model provider.
 * 
 * @author njpatel
 * 
 */
public class BOMModelProvider extends AbstractModelProvider {

    public static final String BOM_SFOLDER_KIND = RCPConsts.BOM_SFOLDER_KIND;

    // Special folders handled by this content provider
    private static final String[] KINDS =
            new String[] { BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND };

    private final IOpenEventListener openListener;

    private OpenStrategy handler;

    private NewBOMAction newBOMAction;

    private ToolBarManager toolBarManager;

    public BOMModelProvider(IWorkbenchWindow window) {
        super(window);

        openListener = new IOpenEventListener() {

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.util.IOpenEventListener#handleOpen(org.eclipse
             * .swt.events.SelectionEvent)
             */
            @Override
            public void handleOpen(SelectionEvent e) {
                Object data = e.item;

                /*
                 * Open the selected item in the editor
                 */

                if (data instanceof Widget) {
                    data = ((Widget) data).getData();
                }

                if (data instanceof EObject) {
                    try {
                        openEditor((EObject) data);
                    } catch (PartInitException partInitException) {
                        Shell shell =
                                getViewer() != null ? getViewer().getTree()
                                        .getShell() : XpdResourcesPlugin
                                        .getStandardDisplay().getActiveShell();

                        IStatus status =
                                new Status(
                                        IStatus.ERROR,
                                        RCPActivator.PLUGIN_ID,
                                        partInitException.getLocalizedMessage(),
                                        partInitException);
                        ErrorDialog.openError(shell,
                                Messages.BOMModelProvider_EditorTitle,
                                Messages.BOMModelProvider_EditorDescription,
                                status);
                    }
                }
            }

        };

        newBOMAction =
                new NewBOMAction(getWindow(),
                        Messages.BOMModelProvider_newModel_action,
                        RCPImages.NEW_BOM_MODEL.getPath(), null, null);
        newBOMAction.setEnabled(false);
    }

    /**
     * @see com.tibco.xpd.rcp.internal.models.AbstractModelProvider#hasActions(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    public boolean hasActions(IProject project) {
        if (project != null && project.isAccessible()) {
            List<SpecialFolder> sFolders =
                    getSpecialFolders(project, RCPConsts.BOM_SFOLDER_KIND);

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
                    getSpecialFolders(project, BOM_SFOLDER_KIND);

            if (sFolders != null && !sFolders.isEmpty()) {
                List<WorkingCopy> wcs =
                        getWorkingCopies(sFolders, RCPConsts.BOM_FILEEXT);

                // Add action to create a new BOM
                actions.add(new NewBOMAction(getWindow(),
                        Messages.BOMModelProvider_newModel_action,
                        RCPImages.ADD.getPath(), project, sFolders.get(0)));

                for (WorkingCopy wc : wcs) {
                    EObject element = wc.getRootElement();
                    if (element instanceof Model) {
                        // Add action to open the BOM model
                        actions.add(new OpenEditorForFileAction(getWindow(),
                                getLabel(element), getImage(element),
                                (IFile) wc.getEclipseResources().get(0),
                                UMLDiagramEditor.ID));
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
                    if (notification.getNotifier() instanceof Model
                            && notification.getEventType() == Notification.SET) {
                        // Any change to a Model affects this section
                        return true;
                    }
                }
            } else if (eventObj instanceof PropertyChangeEvent) {
                /*
                 * Check if an BOM working copy has been reloaded
                 */
                if (WorkingCopy.PROP_RELOADED
                        .equals(((PropertyChangeEvent) eventObj)
                                .getPropertyName())
                        && ((PropertyChangeEvent) eventObj).getSource() instanceof BOMWorkingCopy) {
                    return true;
                }
            }
        } else if (eventType == RCPResourceEventType.ADDED
                || eventType == RCPResourceEventType.REMOVED) {

            if (eventObj instanceof IFile) {
                // If a bom file is added then update section
                IFile file = (IFile) eventObj;
                if (RCPConsts.BOM_FILEEXT.equals(file.getFileExtension())) {
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(file.getProject());
                    if (config != null) {
                        SpecialFolder sf =
                                config.getSpecialFolders()
                                        .getFolderContainer(file);
                        if (sf != null
                                && RCPConsts.BOM_SFOLDER_KIND.equals(sf
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
        return element instanceof Model;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeViewerListener#treeCollapsed(org.eclipse.jface.viewers.TreeExpansionEvent)
     * 
     * @param event
     */
    @Override
    public void treeCollapsed(TreeExpansionEvent event) {

    }

    /**
     * @see org.eclipse.jface.viewers.ITreeViewerListener#treeExpanded(org.eclipse.jface.viewers.TreeExpansionEvent)
     * 
     * @param event
     */
    @Override
    public void treeExpanded(TreeExpansionEvent event) {
    }

    /**
     * Open editor and select the given <code>EObject</code>. If the editor is
     * already open then this will bring the editor to the front and select the
     * object.
     * 
     * @param eObject
     *            object to select in editor.
     * @throws PartInitException
     */
    private void openEditor(EObject eObject) throws PartInitException {
        IWorkbenchPage page =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage();
        if (page == null) {
            return;
        }

        if (eObject != null && eObject.eClass() != null
                && eObject.eResource() != null
                && eObject.eClass().getEPackage() == UMLPackage.eINSTANCE) {
            URI uri = eObject.eResource().getURI();

            if (uri != null) {
                String path = uri.toPlatformString(true);

                if (path != null) {
                    IResource workspaceResource =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .findMember(new Path(path));
                    if (workspaceResource instanceof IFile) {
                        IFile file = (IFile) workspaceResource;
                        // Only open editor if BOM resource
                        if (file.getFileExtension() != null
                                && file.getFileExtension()
                                        .equals(BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                            // Make sure the resource is in BOM special folder
                            SpecialFolder sf = getSpecialFolderContainer(file);

                            if (sf != null
                                    && sf.getKind()
                                            .equals(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)) {
                                IEditorPart editor = null;
                                if (eObject instanceof Package
                                        && !(eObject instanceof Model)) {
                                    Package pkg = (Package) eObject;
                                    EList<EObject> contents =
                                            pkg.eResource().getContents();

                                    for (EObject content : contents) {
                                        if (content instanceof Diagram
                                                && ((Diagram) content)
                                                        .getElement() == pkg) {
                                            URI diagURI =
                                                    EcoreUtil.getURI(content);
                                            String editorName =
                                                    diagURI.lastSegment()
                                                            + "#" + content.eResource().getContents().indexOf(content); //$NON-NLS-1$
                                            editor =
                                                    IDE.openEditor(page,
                                                            new URIEditorInput(
                                                                    diagURI,
                                                                    editorName),
                                                            com.tibco.xpd.bom.resources.ui.Activator.BOM_EDITOR_ID);
                                            break;
                                        }
                                    }

                                }

                                if (editor == null) {
                                    editor = IDE.openEditor(page, file, true);

                                    if (editor instanceof IGotoObject) {
                                        ((IGotoObject) editor).reveal(eObject);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (eObject instanceof Diagram) {
            BomUIUtil.openEditor(PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getActivePage(),
                    (Diagram) eObject);
        }
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetChildren(java.lang.Object)
     * 
     * @param arg0
     * @return
     */
    @Override
    protected Object[] doGetChildren(Object parentElement) {
        Object[] children = null;

        if (parentElement instanceof IProject) {
            IProject project = (IProject) parentElement;
            if (project != null && project.isAccessible()) {
                List<SpecialFolder> sFolders =
                        getSpecialFolders(project, BOM_SFOLDER_KIND);

                if (sFolders != null && !sFolders.isEmpty()) {
                    List<WorkingCopy> wcs =
                            getWorkingCopies(sFolders, RCPConsts.BOM_FILEEXT);
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
        }

        return children;
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetParent(java.lang.Object)
     * 
     * @param arg0
     * @return
     */
    @Override
    protected Object doGetParent(Object element) {
        Object parent = null;

        if (element instanceof EObject) {
            // If the element is the model then its parent will be the IFile
            if (element instanceof Model) {
                Resource resource = ((Model) element).eResource();

                if (resource != null) {
                    parent = WorkspaceSynchronizer.getFile(resource);
                }
            }
        }

        return parent;
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetPipelinedChildren(java.lang.Object,
     *      java.util.Set)
     * 
     * @param arg0
     * @param arg1
     */
    @Override
    protected void doGetPipelinedChildren(Object parent, Set theCurrentChildren) {
    }

    @Override
    protected boolean doHasChildren(Object element) {
        boolean hasChildren = false;
        if (element instanceof IFile) {
            hasChildren = true;
        } else {
            hasChildren = getChildren(element).length > 0;
        }
        return hasChildren;
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#getSpecialFolderKindInclusion()
     * 
     * @return
     */
    @Override
    public String[] getSpecialFolderKindInclusion() {
        return KINDS;
    }

    /**
     * Get the <code>SpecialFolder</code> container of the given resource.
     * 
     * @param res
     * @return <code>SpecialFolder</code> if resource contained in one, else
     *         <code>null</code>.
     */
    private SpecialFolder getSpecialFolderContainer(IResource res) {
        SpecialFolder sf = null;

        if (res != null) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault()
                            .getProjectConfig(res.getProject());

            if (config != null) {
                sf = config.getSpecialFolders().getFolderContainer(res);
            }
        }

        return sf;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.models.AbstractModelProvider#createContentLabelProvider()
     * 
     * @return
     */
    @Override
    protected ILabelProvider createContentLabelProvider() {
        return new BOMOverviewLabelProvider();
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
            toolBarManager.add(newBOMAction);
        }
        return toolBarManager;
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

        // Viewer has changed so register open listener
        if (getViewer() != viewer) {
            if (handler != null) {
                handler.removeOpenListener(openListener);
                handler = null;
            }

            if (viewer != null) {
                handler = new OpenStrategy(viewer.getControl());
                handler.addOpenListener(openListener);
            }
        }

        super.inputChanged(viewer, oldInput, newInput);

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
        if (selection instanceof IStructuredSelection
                && ((IStructuredSelection) selection).getFirstElement() instanceof IProject) {
            IProject project =
                    (IProject) ((IStructuredSelection) selection)
                            .getFirstElement();
            if (newBOMAction.getProject() == null
                    || newBOMAction.getProject() != project) {
                List<SpecialFolder> sFolders =
                        getSpecialFolders(project, BOM_SFOLDER_KIND);
                if (sFolders != null && !sFolders.isEmpty()) {
                    newBOMAction.setProject(project);
                    newBOMAction.setsFolder(sFolders.get(0));
                    newBOMAction.setEnabled(true);
                }
                changed = true;
            }
        }
        if (changed) {
            getToolbarManager().update(true);
        }
    }
}
