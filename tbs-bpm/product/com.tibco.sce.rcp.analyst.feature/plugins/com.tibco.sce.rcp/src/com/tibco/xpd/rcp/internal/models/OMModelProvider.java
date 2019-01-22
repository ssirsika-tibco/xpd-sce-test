/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
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

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditor;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.editor.IGotoObject;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.RCPConsts;
import com.tibco.xpd.rcp.internal.models.actions.ModelAction;
import com.tibco.xpd.rcp.internal.models.actions.NewOrganizationAction;
import com.tibco.xpd.rcp.internal.models.actions.NewOrganizationModelAction;
import com.tibco.xpd.rcp.internal.models.actions.OpenEditorForFileAction;
import com.tibco.xpd.rcp.internal.models.actions.OpenOrganizationAction;
import com.tibco.xpd.rcp.internal.models.actions.SeparatorAction;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener.RCPResourceEventType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.NotificationPropertyChangeEvent;

/**
 * Organization Model provider.
 * 
 * @author njpatel
 * 
 */
public class OMModelProvider extends AbstractModelProvider {

    public static final String OM_SFOLDER_KIND = RCPConsts.OM_SFOLDER_KIND;

    private static final String SUB_DIAGRAM_EDITOR_ID =
            "com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorID"; //$NON-NLS-1$

    // private static final Object[] EMPTY_ARRAY = new Object[] {};
    // Special folders handled by this content provider
    private static final String[] KINDS =
            new String[] { OMResourcesActivator.OM_SPECIAL_FOLDER_KIND };

    private final IOpenEventListener openListener;

    private OpenStrategy handler;

    private ToolBarManager toolBarManager;

    private NewOrganizationModelAction newOrganizationModelAction;

    private NewOrganizationAction newOrganizationAction;

    public OMModelProvider(IWorkbenchWindow window) {
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
                                        OMResourcesUIActivator.PLUGIN_ID,
                                        partInitException.getLocalizedMessage(),
                                        partInitException);
                        ErrorDialog.openError(shell,
                                Messages.OMModelProvider_EditorTitle,
                                Messages.OMModelProvider_EditorDescription,
                                status);
                    }
                }
            }
        };
        newOrganizationModelAction =
                new NewOrganizationModelAction(getWindow(),
                        Messages.OMModelProvider_newOrganizationModel_action,
                        RCPImages.NEW_ORG_MODEL.getPath(), null, null);
        newOrganizationModelAction.setEnabled(false);

        newOrganizationAction =
                new NewOrganizationAction(getWindow(),
                        Messages.OMModelProvider_newOrganization_action,
                        RCPImages.NEW_ORGANIZATION.getPath(), null);
        newOrganizationAction.setEnabled(false);
    }

    /**
     * Open editor for the given selected object.
     * 
     * @param selection
     * @throws PartInitException
     */
    private void openEditor(EObject selection) throws PartInitException {
        EObject objToOpen = selection;

        if (selection != null && selection.eClass() != null
                && selection.eClass().getEPackage() == OMPackage.eINSTANCE) {
            IEditorPart editorPart = null;
            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            if (page != null) {
                while (!(objToOpen instanceof Organization || objToOpen instanceof OrgModel)) {
                    objToOpen = objToOpen.eContainer();
                    if (objToOpen == null) {
                        return;
                    }
                }

                if (objToOpen instanceof Organization) {
                    Diagram diagram = getDiagram(objToOpen);

                    if (diagram != null && diagram.eResource() != null) {
                        URI uri = EcoreUtil.getURI(diagram);

                        if (uri != null) {
                            String editorTitle =
                                    ((Organization) objToOpen).getDisplayName();
                            editorPart =
                                    IDE.openEditor(page,
                                            new URIEditorInput(uri, editorTitle),
                                            SUB_DIAGRAM_EDITOR_ID);

                            if (editorPart instanceof IGotoObject
                                    && objToOpen != selection) {
                                ((IGotoObject) editorPart).reveal(selection);
                            }

                            objToOpen = null;
                        }
                    } else {
                        objToOpen = ((Organization) objToOpen).eContainer();
                    }
                }

                if (objToOpen instanceof OrgModel) {
                    IFile file = WorkingCopyUtil.getFile(objToOpen);

                    if (file != null) {
                        editorPart = IDE.openEditor(page, file);
                        if (editorPart instanceof IGotoObject
                                && objToOpen != selection) {
                            ((IGotoObject) editorPart).reveal(selection);
                        }
                    }
                }
            }
        }
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
                    getSpecialFolders(project, RCPConsts.OM_SFOLDER_KIND);

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
                    getSpecialFolders(project, OM_SFOLDER_KIND);

            if (sFolders != null && !sFolders.isEmpty()) {
                boolean doAddSep = false;
                List<WorkingCopy> wcs =
                        getWorkingCopies(sFolders, RCPConsts.OM_FILEEXT);

                // Add action to add Organization Model
                actions.add(new NewOrganizationModelAction(getWindow(),
                        Messages.OMModelProvider_newOrganizationModel_action,
                        RCPImages.ADD.getPath(), project, sFolders.get(0)));

                for (WorkingCopy wc : wcs) {
                    // Add separator between Org Models
                    if (doAddSep) {
                        actions.add(new SeparatorAction());
                    } else {
                        doAddSep = true;
                    }

                    OrgModel model = (OrgModel) wc.getRootElement();

                    actions.add(new OpenEditorForFileAction(getWindow(),
                            getLabel(model), getImage(model), (IFile) wc
                                    .getEclipseResources().get(0),
                            OrganizationModelDiagramEditor.ID));

                    // Add action to add a new Organization
                    actions.add(new NewOrganizationAction(getWindow(),
                            Messages.OMModelProvider_newOrganization_action,
                            RCPImages.ADD.getPath(), model));

                    // Add actions to open the Organizations
                    EList<Organization> organizations =
                            sort(model.getOrganizations());
                    for (Organization org : organizations) {
                        actions.add(new OpenOrganizationAction(getWindow(),
                                getLabel(org), getImage(org), org));
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
                    if (notification.getNotifier() instanceof OrgModel
                            || (notification.getNotifier() instanceof Organization && notification
                                    .getEventType() == Notification.SET)) {
                        // Any change to an OrgModel or Organization affects
                        // this
                        // section
                        return true;
                    }
                }
            } else if (eventObj instanceof PropertyChangeEvent) {
                /*
                 * Check if an OM working copy has been reloaded
                 */
                if (WorkingCopy.PROP_RELOADED
                        .equals(((PropertyChangeEvent) eventObj)
                                .getPropertyName())
                        && ((PropertyChangeEvent) eventObj).getSource() instanceof OMWorkingCopy) {
                    return true;
                }
            }
        } else if (eventType == RCPResourceEventType.ADDED
                || eventType == RCPResourceEventType.REMOVED) {

            if (eventObj instanceof IFile) {
                // If an om file is added then update section
                IFile file = (IFile) eventObj;
                if (RCPConsts.OM_FILEEXT.equals(file.getFileExtension())) {
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(file.getProject());
                    if (config != null) {
                        SpecialFolder sf =
                                config.getSpecialFolders()
                                        .getFolderContainer(file);
                        if (sf != null
                                && RCPConsts.OM_SFOLDER_KIND.equals(sf
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
        return element instanceof Organization || element instanceof OrgModel;
    }

    /**
     * Get the {@link Diagram} of the given semantic element.
     * 
     * @param semanticElement
     * @return
     */
    private Diagram getDiagram(EObject semanticElement) {
        if (semanticElement != null) {
            ECrossReferenceAdapter adapter =
                    ECrossReferenceAdapter
                            .getCrossReferenceAdapter(semanticElement);
            if (adapter != null) {
                Collection<Setting> references =
                        adapter.getInverseReferences(semanticElement);

                if (references != null) {
                    for (Setting ref : references) {
                        if (ref.getEObject() instanceof Diagram) {
                            return (Diagram) ref.getEObject();
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ITreeViewerListener#treeCollapsed(org.eclipse.jface.viewers.TreeExpansionEvent)
     * 
     * @param event
     */
    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
        // TODO Auto-generated method stub

    }

    /**
     * @see org.eclipse.jface.viewers.ITreeViewerListener#treeExpanded(org.eclipse.jface.viewers.TreeExpansionEvent)
     * 
     * @param event
     */
    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        // TODO Auto-generated method stub

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
                        getSpecialFolders(project, OM_SFOLDER_KIND);

                if (sFolders != null && !sFolders.isEmpty()) {
                    List<WorkingCopy> wcs =
                            getWorkingCopies(sFolders, RCPConsts.OM_FILEEXT);
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
        } else if (parentElement instanceof OrgModel) {
            OrgModel orgModel = (OrgModel) parentElement;
            List<EObject> organizations = new ArrayList<EObject>();
            // Add all organizations
            organizations.addAll(sort(orgModel.getOrganizations()));
            children = organizations.toArray();
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

        if (element instanceof INavigatorGroup) {
            // This is a navigator group, so return it's parent
            parent = ((INavigatorGroup) element).getParent();

        } else if (element instanceof EObject) {
            EObject eo = (EObject) element;

            // Get working copy of the EObject
            WorkingCopy wc = getWorkingCopy(eo);

            if (wc != null) {
                // If this is a Package then the parent will be the IFile
                if (eo instanceof OrgModel) {
                    // Get the IResource of the working copy
                    parent = wc.getEclipseResources().get(0);

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
    protected void doGetPipelinedChildren(Object arg0, Set arg1) {
        // TODO Auto-generated method stub

    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doHasChildren(java.lang.Object)
     * 
     * @param arg0
     * @return
     */
    @Override
    protected boolean doHasChildren(Object element) {
        boolean gotChildren = false;
        if (element instanceof OrgModel) {
            gotChildren = true;
        }
        return gotChildren;
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
     * @see com.tibco.xpd.rcp.internal.models.AbstractModelProvider#createContentLabelProvider()
     * 
     * @return
     */
    @Override
    protected ILabelProvider createContentLabelProvider() {
        return new OMOverviewLabelProvider();
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
            toolBarManager.add(newOrganizationModelAction);
            toolBarManager.add(newOrganizationAction);
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

        if (selectedItem instanceof IProject) {
            IProject project =
                    (IProject) ((IStructuredSelection) selection)
                            .getFirstElement();
            if (newOrganizationModelAction.getProject() == null
                    || newOrganizationModelAction.getProject() != project) {
                List<SpecialFolder> sFolders =
                        getSpecialFolders(project, OM_SFOLDER_KIND);
                if (sFolders != null && !sFolders.isEmpty()) {
                    newOrganizationModelAction.setProject(project);
                    newOrganizationModelAction.setsFolder(sFolders.get(0));
                    newOrganizationModelAction.setEnabled(true);
                }
                newOrganizationAction.setEnabled(false);
                changed = true;
            }
        } else if (selectedItem instanceof OrgModel
                || selectedItem instanceof Organization) {
            OrgModel orgModel =
                    (OrgModel) (selectedItem instanceof OrgModel ? selectedItem
                            : ((Organization) selectedItem).eContainer());

            newOrganizationAction.setOrgModel(orgModel);
            newOrganizationAction.setEnabled(orgModel != null);
            changed = true;
        } else {
            newOrganizationAction.setEnabled(false);
            changed = true;
        }
        if (changed) {
            getToolbarManager().update(true);
        }
    }

}
