package com.tibco.xpd.om.modeler.diagram.navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;

import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * @generated
 */
public class OrganizationModelDomainNavigatorContentProvider implements
        ICommonContentProvider {

    /**
     * @generated
     */
    private AdapterFactoryContentProvider myAdapterFctoryContentProvier;

    /**
     * @generated
     */
    private static final Object[] EMPTY_ARRAY = new Object[0];

    /**
     * @generated
     */
    private Viewer myViewer;

    /**
     * @generated
     */
    private AdapterFactoryEditingDomain myEditingDomain;

    /**
     * @generated
     */
    private WorkspaceSynchronizer myWorkspaceSynchronizer;

    /**
     * @generated
     */
    private Runnable myViewerRefreshRunnable;

    /**
     * @generated NOT
     * 
     *            Set the editing domain to XPD
     * 
     */
    public OrganizationModelDomainNavigatorContentProvider() {
        myAdapterFctoryContentProvier = new AdapterFactoryContentProvider(
                OrganizationModelDiagramEditorPlugin.getInstance()
                        .getItemProvidersAdapterFactory());
        TransactionalEditingDomain editingDomain = XpdResourcesPlugin
                .getDefault().getEditingDomain();
        myEditingDomain = (AdapterFactoryEditingDomain) editingDomain;
        myEditingDomain.setResourceToReadOnlyMap(new HashMap() {
            public Object get(Object key) {
                if (!containsKey(key)) {
                    put(key, Boolean.TRUE);
                }
                return super.get(key);
            }
        });
        myViewerRefreshRunnable = new Runnable() {
            public void run() {
                if (myViewer != null) {
                    myViewer.refresh();
                }
            }
        };
        myWorkspaceSynchronizer = new WorkspaceSynchronizer(editingDomain,
                new WorkspaceSynchronizer.Delegate() {
                    public void dispose() {
                    }

                    public boolean handleResourceChanged(final Resource resource) {
                        for (Iterator it = myEditingDomain.getResourceSet()
                                .getResources().iterator(); it.hasNext();) {
                            Resource nextResource = (Resource) it.next();
                            nextResource.unload();
                        }
                        if (myViewer != null) {
                            myViewer.getControl().getDisplay().asyncExec(
                                    myViewerRefreshRunnable);
                        }
                        return true;
                    }

                    public boolean handleResourceDeleted(Resource resource) {
                        for (Iterator it = myEditingDomain.getResourceSet()
                                .getResources().iterator(); it.hasNext();) {
                            Resource nextResource = (Resource) it.next();
                            nextResource.unload();
                        }
                        if (myViewer != null) {
                            myViewer.getControl().getDisplay().asyncExec(
                                    myViewerRefreshRunnable);
                        }
                        return true;
                    }

                    public boolean handleResourceMoved(Resource resource,
                            final URI newURI) {
                        for (Iterator it = myEditingDomain.getResourceSet()
                                .getResources().iterator(); it.hasNext();) {
                            Resource nextResource = (Resource) it.next();
                            nextResource.unload();
                        }
                        if (myViewer != null) {
                            myViewer.getControl().getDisplay().asyncExec(
                                    myViewerRefreshRunnable);
                        }
                        return true;
                    }
                });
    }

    /**
     * @generated
     */
    public void dispose() {
        myWorkspaceSynchronizer.dispose();
        myWorkspaceSynchronizer = null;
        myViewerRefreshRunnable = null;
        for (Iterator it = myEditingDomain.getResourceSet().getResources()
                .iterator(); it.hasNext();) {
            Resource resource = (Resource) it.next();
            resource.unload();
        }
        ((TransactionalEditingDomain) myEditingDomain).dispose();
        myEditingDomain = null;
    }

    /**
     * @generated
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        myViewer = viewer;
    }

    /**
     * @generated
     */
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    /**
     * @generated
     */
    public void restoreState(IMemento aMemento) {
    }

    /**
     * @generated
     */
    public void saveState(IMemento aMemento) {
    }

    /**
     * @generated
     */
    public void init(ICommonContentExtensionSite aConfig) {
    }

    /**
     * @generated
     */
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof IFile) {
            IFile file = (IFile) parentElement;
            URI fileURI = URI.createPlatformResourceURI(file.getFullPath()
                    .toString(), true);
            Resource resource = myEditingDomain.getResourceSet().getResource(
                    fileURI, true);
            return wrapEObjects(myAdapterFctoryContentProvier
                    .getChildren(resource), parentElement);
        }

        if (parentElement instanceof OrganizationModelDomainNavigatorItem) {
            return wrapEObjects(
                    myAdapterFctoryContentProvier
                            .getChildren(((OrganizationModelDomainNavigatorItem) parentElement)
                                    .getEObject()), parentElement);
        }
        return EMPTY_ARRAY;
    }

    /**
     * @generated
     */
    public Object[] wrapEObjects(Object[] objects, Object parentElement) {
        Collection result = new ArrayList();
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof EObject) {
                result.add(new OrganizationModelDomainNavigatorItem(
                        (EObject) objects[i], parentElement,
                        myAdapterFctoryContentProvier));
            }
        }
        return result.toArray();
    }

    /**
     * @generated
     */
    public Object getParent(Object element) {
        if (element instanceof OrganizationModelAbstractNavigatorItem) {
            OrganizationModelAbstractNavigatorItem abstractNavigatorItem = (OrganizationModelAbstractNavigatorItem) element;
            return abstractNavigatorItem.getParent();
        }
        return null;
    }

    /**
     * @generated
     */
    public boolean hasChildren(Object element) {
        return element instanceof IFile || getChildren(element).length > 0;
    }

}
