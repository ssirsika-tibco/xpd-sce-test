/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.wsdl.Binding;
import javax.wsdl.BindingOperation;
import javax.wsdl.Definition;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.progress.UIJob;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider;
import com.tibco.xpd.wsdl.ui.internal.Messages;

/**
 * Project Explorer content provider for the <b>WSDL Content</b>.
 * 
 * @author njpatel
 */
public class WsdlContentProvider extends AbstractSpecialFoldersContentProvider {

    /** Only show wsdl folders. */
    private static final String[] SPECIAL_FOLDERS_FILTER = { WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND };

    private final Set<WsdlWorkingCopy> workingCopies;
    private final WsdlWorkingCopyListener wcListener;

    /**
     * Constructor.
     */
    public WsdlContentProvider() {
        updateInclusionList();
        workingCopies = new HashSet<WsdlWorkingCopy>();
        wcListener = new WsdlWorkingCopyListener();
    }

    @Override
    public void dispose() {
        /* Unregister all working copy listeners */
        for (WsdlWorkingCopy wc : workingCopies) {
            wc.removeListener(wcListener);
        }
        workingCopies.clear();
        super.dispose();
    }

    /**
     * @param parentElement
     *            The parent element.
     * @return An array of child elements.
     * @see com.tibco.xpd.ui.projectexplorer.providers.
     *     
     *      AbstractSpecialFoldersContentProvider#doGetChildren(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Object[] doGetChildren(Object parentElement) {
        List<Object> children = new ArrayList<Object>();

        Object unwrappedParentElement = parentElement;
        if (parentElement instanceof IWsdlObjectWrapper) {
            parentElement = ((IWsdlObjectWrapper) parentElement).getObject();
        }

        if (parentElement instanceof IFile) {
            IFile file = (IFile) parentElement;
            WsdlWorkingCopy wc = getWorkingCopy(file);
            if (wc != null) {
                EObject root = wc.getRootElement();
                if (root instanceof Definition) {
                    Definition definition = (Definition) root;
                    if (definition != null) {
                        Collection services = definition.getServices().values();
                        for (Object service : services) {
                            if (service instanceof Service) {
                                children.add(new WsdlServiceWrapper(
                                        unwrappedParentElement,
                                        (Service) service));
                            }
                        }
                        Collection portTypes = definition.getPortTypes()
                                .values();
                        for (Object portType : portTypes) {
                            if (portType instanceof PortType) {
                                children.add(new WsdlPortTypeWrapper(
                                        unwrappedParentElement,
                                        (PortType) portType));
                            }
                        }
                    }
                }
            }
        } else if (parentElement instanceof Service) {
            Service service = (Service) parentElement;
            Collection ports = service.getPorts().values();
            for (Object port : ports) {
                if (port instanceof Port) {
                    children.add(new WsdlPortWrapper(unwrappedParentElement,
                            (Port) port));
                }
            }
        } else if (parentElement instanceof Port) {
            Port port = (Port) parentElement;
            Binding binding = port.getBinding();
            if (binding != null) {
                List bindings = binding.getBindingOperations();
                for (Object next : bindings) {
                    BindingOperation bindingOp = (BindingOperation) next;
                    if (bindingOp.getOperation() != null) {
                        children.add(new WsdlBindingOperationWrapper(
                                unwrappedParentElement, bindingOp));
                    }
                }
            }
        } else if (parentElement instanceof PortType) {
            PortType portType = (PortType) parentElement;
            for (Object operation : portType.getOperations()) {
                children.add(new WsdlOperationWrapper(unwrappedParentElement,
                        (Operation) operation));
            }
        }

        return children.toArray();
    }

    /**
     * @param element
     *            The element.
     * @return The parent element.
     * @see com.tibco.xpd.ui.projectexplorer.providers.
     *      AbstractSpecialFoldersContentProvider#doGetParent(java.lang.Object)
     */
    @Override
    protected Object doGetParent(Object element) {
        if (element instanceof IWsdlObjectWrapper) {
            return ((IWsdlObjectWrapper) element).getParent();
        }
        return null;
    }

    /**
     * @param aParent
     *            The parent.
     * @param theCurrentChildren
     *            The current children.
     * @see com.tibco.xpd.ui.projectexplorer.providers.
     *     
     *     
     *     
     *     
     *      AbstractSpecialFoldersContentProvider#doGetPipelinedChildren(java.lang.Object,
     *      java.util.Set)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void doGetPipelinedChildren(Object aParent, Set theCurrentChildren) {
        // If this is a WSDL file then get it's children
        if (aParent instanceof IFile) {
            Object[] children = doGetChildren(aParent);

            if (children != null && children.length > 0) {
                // Add children to the set
                theCurrentChildren.addAll(Arrays.asList(children));
            }
        }
    }

    /**
     * @param element
     *            The element.
     * @return true if it has children.
     * @see com.tibco.xpd.ui.projectexplorer.providers.
     *     
     *      AbstractSpecialFoldersContentProvider#doHasChildren(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected boolean doHasChildren(Object element) {
        boolean hasChildren = false;

        if (element instanceof IWsdlObjectWrapper) {
            element = ((IWsdlObjectWrapper) element).getObject();
        }

        if (element instanceof IFile) {
            // Load the working copy - if one is available then this file will
            // have children
            WsdlWorkingCopy wc = getWorkingCopy((IFile) element);
            hasChildren = (wc != null && !wc.isInvalidFile());

        } else if (element instanceof Service) {
            if (((Service) element).getPorts().size() > 0) {
                hasChildren = true;
            }
        } else if (element instanceof PortType) {
            if (((PortType) element).getOperations().size() > 0) {
                hasChildren = true;
            }
        } else if (element instanceof Port) {
            Port port = (Port) element;
            Binding binding = port.getBinding();
            if (binding != null) {
                List<BindingOperation> bindings = binding
                        .getBindingOperations();
                if (bindings.size() > 0) {
                    hasChildren = true;
                }
            }
        }
        return hasChildren;
    }

    /**
     * @return An array of special folder types.
     * @see com.tibco.xpd.ui.projectexplorer.providers.
     *      AbstractSpecialFoldersContentProvider#getSpecialFolderKindFilter()
     */
    @Override
    public String[] getSpecialFolderKindInclusion() {
        return SPECIAL_FOLDERS_FILTER;
    }

    /**
     * @param inputElement
     *            The input element.
     * @return An array of root elements.
     * @see com.tibco.xpd.ui.projectexplorer.providers.
     *      AbstractSpecialFoldersContentProvider#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    /**
     * Get the {@link WsdlWorkingCopy} of the given file. If this working copy
     * is accessed for the first time then a change listener will be registered
     * with the working copy so that the viewer can be refreshed if the wsdl
     * file changes.
     * 
     * @param file
     * @return working copy or <code>null</code> if one is not found or is not a
     *         <code>WsdlWorkingCopy</code>.
     */
    private WsdlWorkingCopy getWorkingCopy(IFile file) {
        if (file != null) {
            WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    file);
            if (wc instanceof WsdlWorkingCopy) {
                WsdlWorkingCopy wsdlWorkingCopy = (WsdlWorkingCopy) wc;
                // If this working copy is not already in our cache then add it
                // and register listener
                if (!workingCopies.contains(wsdlWorkingCopy)) {
                    wsdlWorkingCopy.addListener(wcListener);
                    workingCopies.add(wsdlWorkingCopy);
                }
                return wsdlWorkingCopy;
            }
        }
        return null;
    }

    /**
     * Refresh the given objects in the tree. If collapse is set to
     * <code>true</code> then collapse the tree of these objects.
     * 
     * @param objs
     *            objects to refresh
     * @param collapse
     *            if <code>true</code> collapse the trees of these objects.
     */
    private void refresh(final List<IResource> resources) {
        if (resources != null && !resources.isEmpty()) {
            new UIJob(Messages.WsdlContentProvider_refreshJob_title) {
                @Override
                public IStatus runInUIThread(IProgressMonitor monitor) {
                    TreeViewer viewer = getViewer();
                    if (viewer != null && !viewer.getControl().isDisposed()) {
                        SubProgressMonitor mon = new SubProgressMonitor(
                                monitor, resources.size());
                        for (IResource res : resources) {
                            mon
                                    .subTask(String
                                            .format(
                                                    Messages.WsdlContentProvider_refreshJob_sub_title,
                                                    res.getName()));
                            viewer.refresh(res);
                            mon.worked(1);
                        }
                    }

                    return Status.OK_STATUS;
                }
            }.schedule();
        }
    }

    /**
     * Working copy listener that will refresh the project explorer if the wsdl
     * working copy is changed or reloaded.
     * 
     * @author njpatel
     * 
     */
    private class WsdlWorkingCopyListener implements PropertyChangeListener {

        /*
         * (non-Javadoc)
         * 
         * @seejava.beans.PropertyChangeListener#propertyChange(java.beans.
         * PropertyChangeEvent)
         */
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(WorkingCopy.CHANGED)
                    || evt.getPropertyName().equals(WorkingCopy.PROP_RELOADED)) {
                // Refresh the content
                WorkingCopy wc = (WorkingCopy) evt.getSource();
                if (wc != null && wc.getEclipseResources() != null
                        && !wc.getEclipseResources().isEmpty()) {
                    refresh(wc.getEclipseResources());
                }
            } else if (evt.getPropertyName().equals(WorkingCopy.PROP_REMOVED)) {
                // Remove the working copy from local cache
                WorkingCopy wc = (WorkingCopy) evt.getSource();
                if (wc != null) {
                    workingCopies.remove(wc);
                }
            }
        }

    }

}
