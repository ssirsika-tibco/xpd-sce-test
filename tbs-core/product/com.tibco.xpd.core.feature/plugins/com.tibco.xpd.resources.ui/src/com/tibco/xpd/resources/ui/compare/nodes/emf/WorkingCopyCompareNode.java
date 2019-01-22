/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.resources.ui.compare.nodes.emf;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.resources.ui.compare.viewer.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

/**
 * Compare node that wraps uses the root element of a given {@link WorkingCopy}
 * as a node for comparison structure creator.
 * 
 * @author aallway
 * @since 28 Sep 2010
 */
public abstract class WorkingCopyCompareNode extends EMFCompareNode {

    private AbstractWorkingCopy workingCopy;

    private EObject rootElement = null;

    private boolean freeWorkingCopyOnDispose;

    private boolean disposed;

    private boolean editable = false;

    public static final String WORKINGCOPY_NODE_DISPOSED =
            "WorkingCopyCompareNode.WorkingCopyNodeDisposed"; //$NON-NLS-1$

    public static final String WORKINGCOPY_PROPERTY_CHANGE_MADE_BY_COMPARE_EDITOR =
            "WorkingCopyCompareNode.DoingMergeCopy"; //$NON-NLS-1$

    private Set<PropertyChangeListener> listeners =
            new HashSet<PropertyChangeListener>();

    private WorkingCopyChangedListener workingCopyChangedListener =
            new WorkingCopyChangedListener();

    /**
     * @param workingCopy
     * @param freeWorkingCopyOnDispose
     */
    public WorkingCopyCompareNode(AbstractWorkingCopy workingCopy,
            EStructuralFeature documentRootFeature,
            boolean freeWorkingCopyOnDispose, Object parentObject,
            EMFCompareNodeFactory compareNodeFactory) {
        super(parentObject, documentRootFeature, compareNodeFactory);
        this.workingCopy = workingCopy;
        this.rootElement = workingCopy.getRootElement();
        this.workingCopy.addListener(workingCopyChangedListener);
        this.freeWorkingCopyOnDispose = freeWorkingCopyOnDispose;

    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#createChildren()
     * 
     * @return EObjectCOmpareNode wrapping the root element(s) of the working
     *         copy.
     */
    @Override
    protected Object[] createChildren(EMFCompareNodeFactory compareNodeFactory) {
        if (rootElement == null) {

            if (workingCopy.isInvalidVersion()) {
                /*
                 * Create a special-case compare node that XpdCompareNode will
                 * match with any other node (i.e. if other side can be loaded
                 * and has a root element).
                 */
                return new Object[] { new XpdMatchAnyCompareNode(
                        Messages.WorkingCopyCompareNode_CannotLoadFromPreviousVersion_message,
                        XpdResourcesUIActivator.getDefault().getImageRegistry()
                                .get(XpdResourcesUIConstants.ERROR_OVERLAY),
                        this, compareNodeFactory.getCompareNodeContentType()) };
            }

            return new Object[] { new XpdMatchAnyCompareNode(
                    Messages.WorkingCopyCompareNode_FailedToLoadContent_message, XpdResourcesUIActivator
                            .getDefault().getImageRegistry()
                            .get(XpdResourcesUIConstants.ERROR_OVERLAY), this,
                    compareNodeFactory.getCompareNodeContentType()) };
        }

        return new Object[] { compareNodeFactory.createForEObject(rootElement,
                EObjectCompareNode.NO_LIST_INDEX,
                rootElement.eContainingFeature(),
                this) };
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getImage()
     * 
     * @return
     */
    @Override
    public Image getImage() {
        Image img = WorkingCopyUtil.getImage(rootElement);
        if (img == null) {
            img = super.getImage();
        }
        return img;
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        List<IResource> eclipseResources = workingCopy.getEclipseResources();
        if (!eclipseResources.isEmpty()) {
            return eclipseResources.get(0).getName();
        }
        return "???.xxx"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.compare.ITypedElement#getType()
     * 
     * @return
     */
    @Override
    public String getType() {
        List<IResource> eclipseResources = workingCopy.getEclipseResources();
        if (!eclipseResources.isEmpty()) {
            return eclipseResources.get(0).getFullPath().getFileExtension();
        }
        return super.getType();
    }

    /**
     * @return the workingCopy
     */
    @Override
    public AbstractWorkingCopy getWorkingCopy() {
        return workingCopy;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#calculateLocationInParent(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param object
     * @param parentNode
     * @return
     */
    @Override
    public String calculateLocationInParent(Object object,
            EStructuralFeature feature, Object parentNode) {
        return "WorkingCopy"; //$NON-NLS-1$
    }

    /**
     * @param isEditable
     *            the isEditable to set
     */
    public void setEditable(boolean isEditable) {
        this.editable = isEditable;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.XpdCompareNode#isEditable()
     * 
     * @return true if we were told that our working copy is editable.
     */
    @Override
    public boolean isEditable() {
        return editable;
    }

    /**
     * Set the root element of the working copy.
     * <p>
     * This happens when the user performs a copy-right-to-left on root of
     * WorkingCopyCompareNode tree.
     * 
     * @param workingCopy
     * @param newRootElement
     */
    protected abstract void setWorkingCopyRootElement(
            AbstractWorkingCopy workingCopy, EObject newRootElement);

    /**
     * Save the working copy to file.
     * 
     * @param monitor
     */
    public void save(IProgressMonitor monitor) {
        if (!workingCopy.getEclipseResources().isEmpty()) {
            /* Working copy has a resource */
            try {
                workingCopy.getSaveable().doSave(monitor);
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e,
                        "Failed saving working copy for: " //$NON-NLS-1$
                                + workingCopy.getEclipseResources().get(0)
                                        .getFullPath().toString());
            }
        }

        return;
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#getObject()
     * 
     * @return
     */
    @Override
    public Object getObject() {
        return rootElement;
    }

    /**
     * Free resources.
     */
    @Override
    public void dispose() {
        firePropertyChange(new PropertyChangeEvent(this,
                WORKINGCOPY_NODE_DISPOSED, null, null));

        if (workingCopy != null) {
            workingCopy.removeListener(workingCopyChangedListener);
        }

        if (!disposed) {
            disposed = true;

            super.dispose();

            if (freeWorkingCopyOnDispose) {
                workingCopy.dispose();
            }
        }
        return;
    }

    /**
     * Get command to replace the root element with a COPY of the given soruce
     * root element.
     * 
     * @param editingDomain
     * @param sourceRootElement
     * 
     * @return command
     */
    public Command getReplaceRootElementCommand(EditingDomain editingDomain,
            EObject sourceRootElement) {
        if (true) {
            throw new RuntimeException(
                    "Need to resolve how we might replace mnultiple root elements in GMF type models"); //$NON-NLS-1$
        }
        EObject copy = EcoreUtil.copy(sourceRootElement);

        return new ReplaceWorkingCopyRootElementCommand(editingDomain,
                getWorkingCopy(), copy);
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

    /**
     * Command to replace root element of the working copy with a new element.
     * 
     * 
     * @author aallway
     * @since 13 Oct 2010
     */
    private class ReplaceWorkingCopyRootElementCommand extends AbstractCommand {
        private EditingDomain editingDomain;

        private AbstractWorkingCopy workingCopy;

        private EObject newRootElement;

        private EObject oldRootElement;

        /**
         * @param editingDomain
         * @param workingCopy
         * @param newRootElement
         */
        public ReplaceWorkingCopyRootElementCommand(
                EditingDomain editingDomain, AbstractWorkingCopy workingCopy,
                EObject newRootElement) {
            super();
            this.editingDomain = editingDomain;
            this.workingCopy = workingCopy;
            this.newRootElement = newRootElement;

            oldRootElement = rootElement;
        }

        /**
         * @see org.eclipse.emf.common.command.Command#execute()
         * 
         */
        @Override
        public void execute() {
            rootElement = newRootElement;
            setWorkingCopyRootElement(workingCopy, newRootElement);
        }

        /**
         * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
         * 
         * @return
         */
        @Override
        public boolean canExecute() {
            return newRootElement != null;
        }

        /**
         * @see org.eclipse.emf.common.command.Command#redo()
         * 
         */
        @Override
        public void redo() {
            execute();
        }

        /**
         * @see org.eclipse.emf.common.command.AbstractCommand#undo()
         * 
         */
        @Override
        public void undo() {
            rootElement = oldRootElement;
            setWorkingCopyRootElement(workingCopy, oldRootElement);
        }

    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#doAddYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     * @return
     */
    @Override
    protected Object doAddYourself(ITypedElement targetParent) {
        throw new RuntimeException(
                "doAddYourself() should never be called on WorkingCopyCOmpareNode"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#doRemoveYourself(org.eclipse.compare.ITypedElement)
     * 
     * @param targetParent
     */
    @Override
    protected void doRemoveYourself(ITypedElement targetParent) {
        throw new RuntimeException(
                "doAddYourself() should never be called on WorkingCopyCOmpareNode"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode#doReplaceYourself(org.eclipse.compare.ITypedElement,
     *      EMFCompareNode)
     * 
     * @param targetParent
     * @return
     */
    @Override
    protected Object doReplaceYourself(ITypedElement targetParent,
            EMFCompareNode targetNode) {
        throw new RuntimeException(
                "doAddYourself() should never be called on WorkingCopyCOmpareNode"); //$NON-NLS-1$
    }

    /**
     * Listen for changes in the working copy.
     * <p>
     * If the change was NOT done by the user pressing the copy right/left
     * button in a comapre merge viewer then refresh this
     * {@link WorkingCopyCompareNode} by disposing all of it's chidlren.
     * <p>
     * i.e. we repsond to undo/redo /cahnge in other editors editing the same
     * local resource working copy but NOT copies in the merge viewer. This is
     * so that the outline diff window doesn't trigger e a reresh and remove the
     * item that the user has just copied right to left (becuase of after the
     * copy it will no longer be different).
     * <p>
     * Then listeners to this {@link WorkingCopyCompareNode} are notified.
     * 
     * 
     * @author aallway
     * @since 22 Oct 2010
     */
    private class WorkingCopyChangedListener implements PropertyChangeListener {
        /**
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         * 
         * @param evt
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getSource() == workingCopy) {

                boolean changed =
                        WorkingCopy.CHANGED.equals(evt.getPropertyName())
                                || WorkingCopy.PROP_RELOADED.equals(evt
                                        .getPropertyName())
                                || WorkingCopy.PROP_REMOVED.equals(evt
                                        .getPropertyName());

                if (changed) {
                    /*
                     * Dispose our children IF change was not caused by our
                     * compare editor itself (the content merge viewer is
                     * expected to dispose children of objects it copies
                     * right->left etc) and Difference viewer onbly wants to
                     * re-diff when someone outside ed changes the model.
                     * 
                     * OR the user performs an undo / redo!
                     */
                    if (workingCopy
                            .getAttributes()
                            .get(WORKINGCOPY_PROPERTY_CHANGE_MADE_BY_COMPARE_EDITOR) == null) {
                        disposeChildren();
                    } else {
                        refreshAsNecessary();
                    }
                }

                /* Propagate the evnt to our listeners. */
                /* CLone it with ourselves as the source of event. */
                PropertyChangeEvent cloneEvt =
                        new PropertyChangeEvent(WorkingCopyCompareNode.this,
                                evt.getPropertyName(), evt.getOldValue(), evt
                                        .getNewValue());

                firePropertyChange(cloneEvt);
            }
            return;
        }
    }

}
