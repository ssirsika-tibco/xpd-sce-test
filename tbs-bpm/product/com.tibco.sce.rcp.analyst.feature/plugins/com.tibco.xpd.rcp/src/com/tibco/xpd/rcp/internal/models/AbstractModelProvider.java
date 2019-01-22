/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.services.IDisposable;
import org.tigris.subversion.subclipse.core.util.Assert;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.models.actions.ModelAction;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener.RCPResourceEventType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractWorkingCopySaveablesContentProvider;

/**
 * Abstract class to be implemented by the model handlers. This will provide
 * with various actions for a given model that will be displayed in the Model
 * button menu and the Overview page.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractModelProvider extends
        AbstractWorkingCopySaveablesContentProvider implements IDisposable,
        ITreeViewerListener {

    /**
     * Change listener that will be notified of a change in the model provider.
     * 
     */
    public interface IModelProviderChangeListener {
        /**
         * Indicate that some change has occurred in the given element.
         * 
         * @param source
         *            the model provider that fired this change event.
         * @param element
         *            the element affected, <code>null</code> if change affects
         *            all resources.
         * @param event
         *            the original event
         */
        void handleProviderChange(AbstractModelProvider source, Object element,
                Object event);
    }

    private IWorkbenchWindow window;

    private TransactionalAdapterFactoryLabelProvider factoryLabelProvider;

    private ILabelProvider labelProvider;

    private ILabelProviderListener labelProviderListener;

    private final List<IModelProviderChangeListener> changeListeners;

    public AbstractModelProvider(IWorkbenchWindow window) {
        this.window = window;
        this.factoryLabelProvider =
                new TransactionalAdapterFactoryLabelProvider(XpdResourcesPlugin
                        .getDefault().getEditingDomain(), XpdResourcesPlugin
                        .getDefault().getAdapterFactory());

        changeListeners =
                Collections
                        .synchronizedList(new ArrayList<IModelProviderChangeListener>());
    }

    protected IWorkbenchWindow getWindow() {
        return window;
    }

    /**
     * Add a change listener.
     * 
     * @param listener
     */
    public void addChangeListener(IModelProviderChangeListener listener) {
        if (listener != null && !changeListeners.contains(listener)) {
            changeListeners.add(listener);
        }
    }

    /**
     * Remove a change listener.
     * 
     * @param listener
     */
    public void removeChangeListener(IModelProviderChangeListener listener) {
        if (listener != null) {
            changeListeners.remove(listener);
        }
    }

    /**
     * Notify all listeners of the change.
     * 
     * @param element
     *            affected element.
     * @param event
     *            original event that caused this notification.
     */
    private void notifyChangeListeners(Object element, Object event) {
        synchronized (changeListeners) {
            for (IModelProviderChangeListener listener : changeListeners) {
                listener.handleProviderChange(this, element, event);
            }
        }
    }

    /**
     * Check if the RCP resource change event affects this model type.
     * 
     * @see RCPResourceChangeListener
     * 
     * @param eventType
     * @param eventObj
     * @return <code>true</code> if the change event affects this model type.
     */
    public abstract boolean isAffectingEvent(RCPResourceEventType eventType,
            Object eventObj);

    /**
     * Check if the given resource is of interest to this provider.
     * 
     * @param element
     * @return <code>true</code> if this resource has an impact on this
     *         provider.
     */
    public abstract boolean isElementOfInterest(Object element);

    /**
     * Get the model actions for a given project
     * 
     * @param project
     * @return
     */
    public abstract List<ModelAction> getActions(IProject project);

    /**
     * Returns the content provider
     * 
     * @return
     */
    public IContentProvider getContentProvider() {
        return this;
    }

    /**
     * Returns the Label Provider. (This should return the same instance of the
     * label provider every time.)
     * 
     * @return
     */
    public final ILabelProvider getContentLabelProvider() {
        if (labelProvider == null) {
            labelProvider = createContentLabelProvider();
            Assert.isNotNull(labelProvider);
            labelProviderListener = new ILabelProviderListener() {

                @Override
                public void labelProviderChanged(LabelProviderChangedEvent event) {
                    Object[] elements = event.getElements();
                    if (elements != null) {
                        for (Object element : elements) {
                            if (element instanceof IFile) {
                                WorkingCopy wc =
                                        getWorkingCopy((IFile) element);
                                if (wc != null) {
                                    element = wc.getRootElement();
                                }
                            }
                            if (element != null && isElementOfInterest(element)) {
                                notifyChangeListeners(element, event);
                            }
                        }
                    } else {
                        // Affects all resources
                        notifyChangeListeners(null, event);
                    }
                }
            };

            labelProvider.addListener(labelProviderListener);
        }

        return labelProvider;
    }

    /**
     * Create the label provider for this provider.
     * 
     * @return
     */
    protected abstract ILabelProvider createContentLabelProvider();

    /**
     * Returns the ToolBarManager
     * 
     * @return
     */
    public ToolBarManager getToolbarManager() {
        return null;
    }

    /**
     * This method gets called when a selection is changed in any of the viewers
     * in the Overview Page
     * 
     * @param selection
     */
    public abstract void selectionChanged(ISelection selection);

    /**
     * Get all special folders of the given kind from the project. This will
     * ignore generated special folders.
     * 
     * @param project
     * @param kind
     *            special folder kind
     * @return list of special folder, empty list if none found.
     */
    public static List<SpecialFolder> getSpecialFolders(IProject project,
            String kind) {
        List<SpecialFolder> ret = new ArrayList<SpecialFolder>();
        List<SpecialFolder> sFolders =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(project, kind);

        if (sFolders != null) {
            for (SpecialFolder sf : sFolders) {
                if (sf.getGenerated() == null) {
                    ret.add(sf);
                }
            }
        }

        return ret;
    }

    /**
     * Check if this provider will have actions.
     * 
     * @param project
     * @return <code>true</code> if this provider has actions.
     */
    public abstract boolean hasActions(IProject project);

    /**
     * Get all working copies of resource with the given file extension from the
     * special folders.
     * 
     * @param sFolders
     * @param fileExt
     * @return
     */
    protected List<WorkingCopy> getWorkingCopies(List<SpecialFolder> sFolders,
            final String fileExt) {
        final List<WorkingCopy> wcs = new ArrayList<WorkingCopy>();

        if (sFolders != null && !sFolders.isEmpty()) {
            for (SpecialFolder sf : sFolders) {
                if (sf.getFolder() != null && sf.getFolder().isAccessible()) {
                    try {
                        sf.getFolder().accept(new IResourceProxyVisitor() {
                            @Override
                            public boolean visit(IResourceProxy proxy)
                                    throws CoreException {
                                if (proxy.getType() == IResource.FILE) {
                                    if (proxy.getName().endsWith("." + fileExt)) { //$NON-NLS-1$
                                        final IResource resource =
                                                proxy.requestResource();
                                        if (resource.exists()
                                                && fileExt.equals(resource
                                                        .getFileExtension())) {
                                            WorkingCopy wc =
                                                    WorkingCopyUtil
                                                            .getWorkingCopy(resource);
                                            if (wc != null) {
                                                wcs.add(wc);
                                            }
                                        }
                                    }
                                    return false;
                                }
                                return true;
                            }

                        },
                                0);
                    } catch (CoreException e) {
                        RCPActivator
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Cannot read path '%s'", //$NON-NLS-1$
                                                sf.getLocation()));
                    }
                }
            }
        }

        return sort(wcs);
    }

    /**
     * Sort the working copies in order of the root elements.
     * 
     * @param wcs
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<WorkingCopy> sort(List<WorkingCopy> wcs) {
        final Comparator<Object> comparator = Policy.getComparator();

        Collections.sort(wcs, new Comparator<WorkingCopy>() {

            @Override
            public int compare(WorkingCopy o1, WorkingCopy o2) {
                EObject root1 = o1.getRootElement();
                EObject root2 = o2.getRootElement();

                if (root1 == null && root2 == null) {
                    return 0;
                } else if (root1 == null) {
                    return 1;
                } else if (root2 == null) {
                    return -1;
                }
                return comparator.compare(getLabel(root1), getLabel(root2));
            }
        });

        return wcs;
    }

    /**
     * Sort the given list.
     * 
     * @param elements
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T extends EObject> EList<T> sort(EList<T> elements) {
        final Comparator<Object> comparator = Policy.getComparator();

        if (elements != null && !elements.isEmpty()) {
            EList<T> sortedList = new BasicEList<T>(elements);
            Collections.sort(sortedList, new Comparator<T>() {

                @Override
                public int compare(T o1, T o2) {
                    return comparator.compare(getLabel(o1), getLabel(o2));
                }
            });

            return sortedList;
        }

        return elements;
    }

    /**
     * Get the label of the given object.
     * 
     * @param eo
     * @return
     */
    protected String getLabel(EObject eo) {
        return factoryLabelProvider.getText(eo);
    }

    /**
     * Get the image of the given object.
     * 
     * @param eo
     * @return
     */
    protected Image getImage(EObject eo) {
        return factoryLabelProvider.getImage(eo);
    }

    @Override
    public void dispose() {

        if (labelProvider != null) {
            labelProvider.removeListener(labelProviderListener);
            labelProvider.dispose();
        }

        factoryLabelProvider.dispose();
    }

}
