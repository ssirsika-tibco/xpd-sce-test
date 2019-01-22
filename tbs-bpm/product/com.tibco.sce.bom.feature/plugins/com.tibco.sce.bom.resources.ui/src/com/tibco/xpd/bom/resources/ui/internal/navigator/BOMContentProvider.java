/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.navigator;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.util.IOpenEventListener;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.editor.IGotoObject;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.providers.DiagramGroupTransientItemProvider;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractWorkingCopySaveablesContentProvider;

/**
 * Project explorer navigator content provider for the BOM Special folder and
 * content.
 * 
 * @author njpatel
 * 
 */
public class BOMContentProvider extends
        AbstractWorkingCopySaveablesContentProvider {

    // Special folders handled by this content provider
    private static final String[] KINDS =
            new String[] { BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND };

    // Adapter factory content provider
    private final TransactionalAdapterFactoryContentProvider factoryContentProvider;

    private final IOpenEventListener openListener;

    private final Map<BOMWorkingCopy, DiagramGroupTransientItemProvider> diagGroups;

    private OpenStrategy handler;

    public BOMContentProvider() {
        diagGroups =
                new HashMap<BOMWorkingCopy, DiagramGroupTransientItemProvider>();
        factoryContentProvider =
                new TransactionalAdapterFactoryContentProvider(
                        XpdResourcesPlugin.getDefault().getEditingDomain(),
                        XpdResourcesPlugin.getDefault().getAdapterFactory());

        openListener = new IOpenEventListener() {

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.util.IOpenEventListener#handleOpen(org.eclipse
             * .swt.events.SelectionEvent)
             */
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
                                        Activator.PLUGIN_ID,
                                        partInitException.getLocalizedMessage(),
                                        partInitException);
                        ErrorDialog
                                .openError(shell,
                                        Messages.BOMContentProvider_editor_dialog_title,
                                        Messages.BOMContentProvider_editor_dialog_message,
                                        status);
                    }
                }
            }

        };
    }

    @Override
    protected Object[] doGetChildren(Object parentElement) {
        Object[] children = null;

        if (parentElement instanceof DiagramGroupTransientItemProvider
                || (parentElement instanceof EObject && !(parentElement instanceof Diagram))) {
            children = factoryContentProvider.getChildren(parentElement);
        }

        return children;
    }

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
            } else {
                parent = factoryContentProvider.getParent(element);
            }
        } else if (element instanceof DiagramGroupTransientItemProvider) {
            parent = factoryContentProvider.getParent(element);
        }

        return parent;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doGetPipelinedChildren(Object parent, Set theCurrentChildren) {

        // Register all BOM working copies for the given project - this will
        // allow the saveables for this working copies to be set
        if (parent instanceof IProject) {
            XpdProjectResourceFactory resourceFactory =
                    XpdResourcesPlugin.getDefault()
                            .getXpdProjectResourceFactory((IProject) parent);

            if (resourceFactory != null) {
                WorkingCopy[] workingCopies =
                        resourceFactory.getWorkingCopies();

                for (WorkingCopy wc : workingCopies) {
                    // Only interested in BOM working copies
                    if (wc instanceof BOMWorkingCopy) {
                        registerWorkingCopy(wc);
                    }
                }
            }
        }

        if (parent instanceof IFile) {
            WorkingCopy wc = getWorkingCopy((IResource) parent);

            if (wc instanceof BOMWorkingCopy) {
                EObject element = wc.getRootElement();

                if (element != null) {
                    theCurrentChildren.add(element);

                    DiagramGroupTransientItemProvider group =
                            getDiagramsGroup((BOMWorkingCopy) wc);
                    if (group != null) {
                        theCurrentChildren.add(group);
                    }
                }
            }
        }
    }

    /**
     * Get the diagram group for the given working copy.
     * 
     * @param wc
     * @return
     */
    private DiagramGroupTransientItemProvider getDiagramsGroup(BOMWorkingCopy wc) {
        DiagramGroupTransientItemProvider group = null;
        if (wc != null) {
            group = diagGroups.get(wc);
            if (group == null) {
                group =
                        new DiagramGroupTransientItemProvider(
                                XpdResourcesPlugin.getDefault()
                                        .getAdapterFactory(), wc);
                diagGroups.put(wc, group);
            }
        }
        return group;
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

    @Override
    protected boolean doRefresh(WorkingCopy wc, PropertyChangeEvent evt) {
        // If this is a change event and the notifications are to do with the
        // diagram then ignore refresh
        boolean refresh = super.doRefresh(wc, evt);

        if (refresh) {
            if (evt.getPropertyName().equals(WorkingCopy.CHANGED)) {
                Object value = evt.getNewValue();

                if (value instanceof ResourceSetChangeEvent) {
                    ResourceSetChangeEvent event =
                            (ResourceSetChangeEvent) value;
                    List<?> notifications = event.getNotifications();
                    refresh = false;

                    for (Object next : notifications) {
                        Notification notification = (Notification) next;

                        if (notification.getNotifier() instanceof EObject) {
                            EObject notifier =
                                    (EObject) notification.getNotifier();

                            if (notifier.eClass().getEPackage() instanceof UMLPackage) {
                                refresh = true;
                                break;
                            } else if (notifier instanceof DynamicEObjectImpl) {
                                /*
                                 * If this is the labelled element stereotype
                                 * application then refresh the tree as this is
                                 * probably a change to the display name
                                 */
                                EClass ec = notifier.eClass();
                                if (ec != null
                                        && PrimitivesUtil.LABELED_ELEMENT_STEREOTYPE_NAME
                                                .equals(ec.getName())) {
                                    // Make sure the stereotype is from the
                                    // primitive types profile
                                    Resource resource = ec.eResource();
                                    if (refresh =
                                            resource != null
                                                    && PrimitivesUtil.BOM_PRIMITIVE_TYPES_FACETS_PROFILE_URI
                                                            .equals(resource
                                                                    .getURI()
                                                                    .toString())) {
                                        // Refresh the content
                                        break;
                                    }
                                }
                            } else if (notifier instanceof Diagram) {
                                // If diagram name has been updated then refresh
                                if (refresh =
                                        notification.getFeature() == NotationPackage.eINSTANCE
                                                .getDiagram_Name()) {
                                    break;
                                }
                            }
                        } else {
                            // Probably a resource so need refresh
                            refresh = true;
                            break;
                        }
                    }
                }
            }
        }

        return refresh;
    }

    @Override
    public String[] getSpecialFolderKindInclusion() {
        return KINDS;
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
                                && file
                                        .getFileExtension()
                                        .equals(BOMResourcesPlugin.BOM_FILE_EXTENSION)) {
                            // Make sure the resource is in BOM special folder
                            SpecialFolder sf = getSpecialFolderContainer(file);

                            if (sf != null
                                    && sf
                                            .getKind()
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
                                                    IDE
                                                            .openEditor(page,
                                                                    new URIEditorInput(
                                                                            diagURI,
                                                                            editorName),
                                                                    Activator.BOM_EDITOR_ID);
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
                    XpdResourcesPlugin.getDefault().getProjectConfig(res
                            .getProject());

            if (config != null) {
                sf = config.getSpecialFolders().getFolderContainer(res);
            }
        }

        return sf;
    }

    @Override
    public void dispose() {
        factoryContentProvider.dispose();
        super.dispose();
    }
}
