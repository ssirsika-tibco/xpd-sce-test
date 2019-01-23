/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.resources.ui.compare;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IResourceProvider;
import org.eclipse.compare.ISharedDocumentAdapter;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.compare.structuremergeviewer.IStructureCreator;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IStorageEditorInput;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode;
import com.tibco.xpd.resources.ui.compare.nodes.emf.WorkingCopyCompareNode;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

/**
 * Base comparison editor structure creator for generic basic comparison of EMF
 * models.
 * <p>
 * <b>Note that the StructurCreator MUST NOT maintain state.</b> This is because
 * the same creator will be used for the left and right model's of the top
 * (structured diff) viewer
 * 
 * @author aallway
 * @since 28 Sep 2010
 */
public abstract class EMFCompareStructureCreator implements IStructureCreator {

    private Set<PropertyChangeListener> listeners =
            new HashSet<PropertyChangeListener>();

    public static final String WORKINGCOPY_NODE_CREATED =
            "EMFCompareStructureCreator.WorkingCopyNodeCreated"; //$NON-NLS-1$

    /**
     * <code>true</code> if you wish to use the Xpod working copy for
     * structure's WorkingCopyCompareNode if the given input is of editable
     * resource type.
     * 
     * Default is to create temporary working copies from input stream
     * <p>
     * DON'T CHANGE THIS UNLESS YOU PLAN TO WORK OUT HOW TO BEST DEAL WITH
     * UNDO-REDO SITUATIONS AND REFRESHING OF DiffViewer (top Window) when
     * working copy is changed outside of compare editor.
     */
    boolean useLocalResourceWorkingCopyForEditable = false;

    /**
     * @param useLocalResourceWorkingCopyForEditable
     *            <code>true</code> if you wish to use the Xpod working copy for
     *            structure's WorkingCopyCompareNode if the given input is of
     *            editable resource type
     * 
     * @deprecated DON'T CHANGE THIS UNLESS YOU PLAN TO WORK OUT HOW TO BEST
     *             DEAL WITH UNDO-REDO SITUATIONS AND REFRESHING OF DiffViewer
     *             (top Window) when working copy is changed outside of compare
     *             editor.
     */
    @Deprecated
    public void setUseLocalResourceWorkingCopyForEditable(
            boolean useLocalResourceWorkingCopyForEditable) {
        this.useLocalResourceWorkingCopyForEditable =
                useLocalResourceWorkingCopyForEditable;
    }

    /**
     * Create the {@link EMFCompareNodeFactory}. This factory is used when
     * iterating thru a model to create appropriate EMF model relevant
     * {@link XpdCompareNode} implementations for standard parts of the model
     * (lists, object, attributes and so on.
     * <p>
     * By default these compare nodes will return the structure for comparison
     * according to the structure of the EMF model.
     * <p>
     * This factory can be sub-classed to provide special handling for given
     * model elements (by creating specialised {@link XpdCompareNode} (or other
     * subclass) implementations for particular model elements.
     * 
     * @return factory for creation of {@link XpdCompareNode} implementations
     *         for given model elements.
     */
    protected abstract EMFCompareNodeFactory createEMFCompareNodeFactory();

    /**
     * Create a working copy from the given input stream.
     * <p>
     * Note: The resource may be <code>null</code>. This can happen when
     * comparing nodes from an SVN revision etc.
     * 
     * @param inputStream
     * @param resource
     * 
     * @return working copy.
     */
    protected abstract AbstractWorkingCopy createWorkingCopyForInput(
            InputStream inputStream, IResource resource);

    /**
     * Create the working copy compare node element in tree (becomes the root of
     * the tree structure.
     * 
     * @param workingCopy
     * @param freeWorkingCopyOnDispose
     * @param parentObject
     * @param compareNodeFactory
     */
    protected abstract WorkingCopyCompareNode createWorkingCopyCompareNode(
            AbstractWorkingCopy workingCopy, boolean freeWorkingCopyOnDispose,
            Object parentObject, EMFCompareNodeFactory compareNodeFactory);

    /**
     * @see org.eclipse.compare.structuremergeviewer.IStructureCreator#getStructure(java.lang.Object)
     * 
     * @param input
     * @return
     */
    @Override
    public IStructureComparator getStructure(Object input) {

        AbstractWorkingCopy workingCopy = null;
        boolean disposeWorkingCopyOnClose = false;
        /*
         * If the input represents the base revision of a local resource then
         * use the real working copy for it if we have been requested to.
         */
        if (useLocalResourceWorkingCopyForEditable) {
            IResource baseResource = getBaseLocalResourceFromInput(input);
            if (baseResource != null) {
                workingCopy =
                        createWorkingCopyForBaseLocalResource(baseResource);
            }
        }

        boolean isEditable = false;
        if (input instanceof IEditableContent) {
            isEditable = (((IEditableContent) input).isEditable());
        }

        if (workingCopy == null) {
            /*
             * Otherwise create a temporary working copy from teh input stream
             * and _optional_ resource (may be null for SVN historical revisions
             * etc)
             */
            InputStream inputStream = getInputStreamFromInput(input);

            if (inputStream != null) {
                workingCopy =
                        createWorkingCopyForInput(inputStream,
                                getResourceFromInput(input));

                /*
                 * This isn't the local resource working copy so should be
                 * disposed.
                 */
                disposeWorkingCopyOnClose = true;
            }
        }

        if (workingCopy == null) {
            throw new RuntimeException("No working copy created for input: "
                    + input);
        }

        EMFCompareNodeFactory compareNodeFactory =
                createEMFCompareNodeFactory();
        if (compareNodeFactory == null) {
            throw new RuntimeException(
                    "No compare node factory created for input: " + input);
        }

        WorkingCopyCompareNode workingCopyCompareNode =
                createWorkingCopyCompareNode(workingCopy,
                        disposeWorkingCopyOnClose,
                        input,
                        compareNodeFactory);
        if (workingCopyCompareNode == null) {
            throw new RuntimeException(
                    "No working copy compare node created for input: " + input);
        }

        /*
         * Set the working copy compare node to editable if the input is (this
         * is true when the given input represents a Local base workspace
         * resource
         */
        workingCopyCompareNode.setEditable(isEditable);

        /*
         * Tell listeners that working copy was created (this allows viewers to
         * start listening to working copy node for changes/disposal at most
         * appropriate time (and independent of life-cycle of underlying copare
         * editor)
         */
        firePropertyChange(new PropertyChangeEvent(this,
                WORKINGCOPY_NODE_CREATED, null, workingCopyCompareNode));

        return workingCopyCompareNode;
    }

    /**
     * @param input
     * @return {@link WorkingCopyCompareNode}
     */
    public WorkingCopyCompareNode getWorkingCopyCompareNodeStructure(
            Object input) {
        return (WorkingCopyCompareNode) getStructure(input);
    }

    /**
     * The structure creator has ascertained that the 'side of the compare' that
     * this structure is created for is for the local base revision of a
     * resource.
     * <p>
     * Access the local resource working copy for it (i.e. the editable working
     * copy which will then enable editability of EMF based compare nodes.
     * 
     * @param baseResource
     * 
     * @return working copy
     */
    protected AbstractWorkingCopy createWorkingCopyForBaseLocalResource(
            IResource baseResource) {
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(baseResource);

        if (workingCopy instanceof AbstractWorkingCopy) {
            return (AbstractWorkingCopy) workingCopy;
        }

        return null;
    }

    /**
     * If the given input represents the local base revision of a resource (i.e.
     * the current version of a file in the workspace) then return the IResource
     * for it.
     * <p>
     * Otherwise (for historical versions etc) returns <code>null</code>
     * 
     * @param input
     * 
     * @return resource if the input represents an actual Local workspace file
     *         else <code>null</code>.
     */
    protected IResource getBaseLocalResourceFromInput(Object input) {
        /*
         * Sid: As far as I can tell, whenever a comparison is performed against
         * a local workspace file the editor inptu is such that it implemnets
         * IEditableContent and isEditable returns true.
         */
        if (input instanceof IEditableContent) {
            if (((IEditableContent) input).isEditable()) {
                /*
                 * Ok, input is tagged as editable, so as long as we can get the
                 * resource we're laughing!
                 */
                return getResourceFromInput(input);
            }
        }

        return null;
    }

    /**
     * @param input
     * @return input stream from given input.
     */
    private InputStream getInputStreamFromInput(Object input) {
        InputStream inputStream = null;
        if (input instanceof IStreamContentAccessor) {
            try {
                inputStream = ((IStreamContentAccessor) input).getContents();
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }

        if (inputStream == null) {
            throw new RuntimeException(
                    "failed to access inputStream for input: " + input.toString()); //$NON-NLS-1$
        }

        return inputStream;
    }

    /**
     * @param input
     * @return
     */
    private IResource getResourceFromInput(Object input) {
        IResource resource = null;

        if (input instanceof IResourceProvider) {
            resource = ((IResourceProvider) input).getResource();

        } else if (input instanceof IAdaptable) {
            ISharedDocumentAdapter adp =
                    (ISharedDocumentAdapter) ((IAdaptable) input)
                            .getAdapter(ISharedDocumentAdapter.class);
            IEditorInput documentKey = adp.getDocumentKey(input);
            if (documentKey instanceof IStorageEditorInput) {
                try {
                    IStorage storage =
                            ((IStorageEditorInput) documentKey).getStorage();
                    IPath fullPath = storage.getFullPath();

                    resource =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .getFile(fullPath);

                } catch (CoreException e) {
                    e.printStackTrace();
                }
            }
        }

        return resource;
    }

    /**
     * @see org.eclipse.compare.structuremergeviewer.IStructureCreator#getContents(java.lang.Object,
     *      boolean)
     * 
     * @param node
     * @param ignoreWhitespace
     * @return
     */
    @Override
    public String getContents(Object node, boolean ignoreWhitespace) {
        //        System.out.println(this.getClass().getName() + ".getContents()"); //$NON-NLS-1$
        if (node instanceof XpdCompareNode) {
            String text = ((XpdCompareNode) node).getTextContent();
            if (text != null) {
                if (ignoreWhitespace) {
                    return text.replaceAll("[ \t\r\n]", ""); //$NON-NLS-1$//$NON-NLS-2$
                } else {
                    return text;
                }
            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.compare.structuremergeviewer.IStructureCreator#locate(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param path
     * @param input
     * @return
     */
    @Override
    public IStructureComparator locate(Object path, Object input) {
        System.out.println(this.getClass().getName() + ".locate()"); //$NON-NLS-1$
        return null;
    }

    /**
     * @see org.eclipse.compare.structuremergeviewer.IStructureCreator#save(org.eclipse.compare.structuremergeviewer.IStructureComparator,
     *      java.lang.Object)
     * 
     * @param node
     * @param input
     */
    @Override
    public void save(IStructureComparator node, Object input) {
        System.out.println(this.getClass().getName() + ".save()"); //$NON-NLS-1$

    }

    /**
     * Propagate the given working copy change event.
     * 
     * @param evt
     */
    protected void firePropertyChange(PropertyChangeEvent evt) {
        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(evt);
        }
        return;
    }

    public void addListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

}
