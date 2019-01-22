/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.wc.gmf;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.AbstractDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.DiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResourceFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.wc.NotificationPropertyChangeEvent;

/**
 * Provide document for GMF diagrams based on WorkingCopy.
 * <p>
 * Updated in 3.0 to use the single transactional editing domain.
 * </p>
 * 
 * @author wzurek, njpatel
 * 
 */
public class WorkingCopyDocumentProvider extends AbstractDocumentProvider
        implements IDiagramDocumentProvider {

    /**
     * Status code set to indicate working copy file problem.
     */
    public static final int INVALID_FILE = 0x100;

    /**
     * Status code set to indicate working copy version problem.
     */
    public static final int INVALID_VERSION = 0x101;

    // Invalid input message
    private static final String INVALID_INPUT =
            String
                    .format(Messages.WorkingCopyDocumentProvider_invalidInputError_message,
                            "org.eclipse.ui.part.FileEditorInput", //$NON-NLS-1$
                            "org.eclipse.emf.common.ui.URIEditorInput"); //$NON-NLS-1$

    @Override
    protected ElementInfo createElementInfo(Object element)
            throws CoreException {

        ResourceInfo info = null;

        if (!isInputValid(element)) {
            throw new CoreException(new Status(IStatus.ERROR,
                    XpdResourcesPlugin.ID_PLUGIN, 0, INVALID_INPUT, null));
        }
        IEditorInput editorInput = (IEditorInput) element;
        IDiagramDocument document = null;
        IStatus status = null;
        try {
            document = (IDiagramDocument) createDocument(editorInput);
        } catch (CoreException e) {
            status = e.getStatus();
        } finally {
            AbstractGMFWorkingCopy wc = getWorkingCopy(editorInput);
            if (wc != null) {
                info =
                        new ResourceInfo(document, editorInput,
                                (WorkingCopy) wc);
                info.fStatus = status;
            }
        }

        return info;
    }

    /**
     * Check if the input to this provider is valid. Default implementation will
     * accept {@link IFileEditorInput} and {@link URIEditorInput}. Subclasses
     * may override.
     * 
     * @param editorInput
     * @return <code>true</code> if <code>IFileEditorInput</code> or
     *         <code>URIEditorInput</code>, <code>false</code> otherwise.
     * @since 3.5
     */
    protected boolean isInputValid(Object editorInput) {
        return editorInput instanceof IFileEditorInput
                || editorInput instanceof URIEditorInput;
    }

    /**
     * Get the saveables from the editor input.
     * 
     * @param editorInput
     * @return list of <code>Saveable</code> objects if found, empty list
     *         otherwise.
     */
    public Saveable[] getSaveables(Object editorInput) {
        Saveable[] saveables = null;
        ResourceInfo resourceInfo = getResourceInfo(editorInput);

        if (resourceInfo != null) {
            saveables =
                    new Saveable[] { resourceInfo.getWorkingCopy()
                            .getSaveable() };
        }

        return saveables != null ? saveables : new Saveable[0];
    }

    /**
     * Get the {@link WorkingCopy} associated with the given editor input.
     * 
     * @param editorInput
     * @return <code>WorkingCopy</code> or null if one is not connected.
     * @since 3.2
     */
    public WorkingCopy getWorkingCopy(Object editorInput) {
        WorkingCopy wc = null;

        if (editorInput != null) {
            ResourceInfo info = getResourceInfo(editorInput);
            if (info != null) {
                wc = info.getWorkingCopy();
            }
        }

        return wc;
    }

    /**
     * Get the information related t the given editor input
     * 
     * @param editorInput
     * @return <code>ResourceInfo</code> related to the given input.
     */
    protected ResourceInfo getResourceInfo(Object editorInput) {
        return (ResourceInfo) super.getElementInfo(editorInput);
    }

    @Override
    protected IDocument createDocument(Object element) throws CoreException {
        if (!isInputValid(element)) {
            throw new CoreException(new Status(IStatus.ERROR,
                    XpdResourcesPlugin.ID_PLUGIN, 0, INVALID_INPUT, null));
        }

        IDocument document = createEmptyDocument();
        setDocumentContent(document, element);
        setupDocument(element, document);
        return document;
    }

    /**
     * Sets up the given document as it would be provided for the given element.
     * The content of the document is not changed. This default implementation
     * is empty. Subclasses may reimplement.
     * 
     * @param element
     *            the blue-print element
     * @param document
     *            the document to set up
     * @generated
     */
    protected void setupDocument(Object element, IDocument document) {
        // for subclasses
    }

    @Override
    protected IDocument createEmptyDocument() {
        DiagramDocument document = new DiagramDocument();
        document.setEditingDomain(getEditingDomain());

        return document;
    }

    /**
     * Get the editing domain. The default implementation returns the shared
     * single editing domain. Subclasses can override if another editing domain
     * is being used.
     * 
     * @return <code>TransactionalEditingDomain</code>.
     */
    protected TransactionalEditingDomain getEditingDomain() {
        return XpdResourcesPlugin.getDefault().getEditingDomain();
    }

    /**
     * Set the content of the document.
     * 
     * @param document
     *            <code>IDocument</code>
     * @param element
     *            input element
     * @throws CoreException
     */
    protected void setDocumentContent(IDocument document, Object element)
            throws CoreException {

        if (!isInputValid(element)) {
            throw new CoreException(new Status(IStatus.ERROR,
                    XpdResourcesPlugin.ID_PLUGIN, 0, INVALID_INPUT, null));
        }

        IEditorInput editorInput = (IEditorInput) element;
        // Get working copy of model being edited
        AbstractGMFWorkingCopy wc = getWorkingCopy(editorInput);

        if (wc != null) {

            if (wc.isInvalidFile()) {
                if (wc.isInvalidVersion()) {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    XpdResourcesPlugin.ID_PLUGIN,
                                    INVALID_VERSION,
                                    Messages.WorkingCopyDocumentProvider_versionProblem_error_shortdesc,
                                    null));
                } else {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    XpdResourcesPlugin.ID_PLUGIN,
                                    INVALID_FILE,
                                    Messages.WorkingCopyDocumentProvider_invalidFile_error_shortdesc,
                                    null));
                }
            }

            // Get the diagram
            Diagram diagram = null;

            if (editorInput instanceof URIEditorInput) {
                // This is looking at a sub section of the model so load the
                // correct diagram
                URI uri = ((URIEditorInput) editorInput).getURI();

                if (uri != null && uri.hasFragment()) {
                    diagram = wc.getDiagram(uri.fragment());
                }
            } else {
                List<Diagram> diagrams = wc.getDiagrams();

                if (diagrams != null && !diagrams.isEmpty()) {
                    diagram = diagrams.get(0);
                }
            }

            if (diagram != null) {
                document.setContent(diagram);
            } else {

                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                XpdResourcesPlugin.ID_PLUGIN,
                                Messages.WorkingCopyDocumentProvider_noDiagramFound_message,
                                null));
            }
        } else {
            String errMsg = null;
            if (editorInput instanceof URIEditorInput) {
                errMsg =
                        String
                                .format(Messages.WorkingCopyDocumentProvider_cannotOpenDiagram_error_message,
                                        ((URIEditorInput) editorInput).getURI()
                                                .lastSegment());
            } else {
                errMsg =
                        String
                                .format(Messages.WorkingCopyDocumentProvider_failedToGetWorkingCopy_message,
                                        (editorInput.getName()));
            }
            throw new RuntimeException(errMsg);
        }
    }

    @Override
    protected void doSaveDocument(IProgressMonitor monitor, Object element,
            IDocument document, boolean overwrite) throws CoreException {
        ResourceInfo info = getResourceInfo(element);

        if (info != null) {
            if (!overwrite && !info.isSynchronized()) {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                XpdResourcesPlugin.ID_PLUGIN,
                                IResourceStatus.OUT_OF_SYNC_LOCAL,
                                Messages.WorkingCopyDocumentProvider_fileChanged_message,
                                null));
            }

            info.stopResourceListening();
            fireElementStateChanging(element);
            WorkingCopy wc = info.getWorkingCopy();

            if (wc != null && !wc.isReadOnly()) {
                try {
                    monitor
                            .beginTask(Messages.WorkingCopyDocumentProvider_savingDiagram_message,
                                    1);
                    monitor
                            .setTaskName(String
                                    .format(Messages.WorkingCopyDocumentProvider_saving_message,
                                            wc.getName()));

                    try {
                        wc.save();
                    } catch (IOException e) {
                        fireElementStateChangeFailed(element);
                        throw new CoreException(new Status(IStatus.ERROR,
                                XpdResourcesPlugin.ID_PLUGIN, 0, e
                                        .getLocalizedMessage(), null));
                    }
                    monitor.worked(1);
                    monitor.done();
                } catch (RuntimeException e) {
                    fireElementStateChangeFailed(element);
                    throw e;
                } finally {
                    info.startResourceListening();
                }
            } else {
                throw new NullPointerException(
                        "Failed to get working copy to save document."); //$NON-NLS-1$
            }
        } else {
            URI newResoruceURI;
            List<IFile> affectedFiles = null;
            if (element instanceof FileEditorInput) {
                IFile newFile = ((FileEditorInput) element).getFile();
                affectedFiles = Collections.singletonList(newFile);
                newResoruceURI =
                        URI.createPlatformResourceURI(newFile.getFullPath()
                                .toString(), true);
            } else if (element instanceof URIEditorInput) {
                newResoruceURI = ((URIEditorInput) element).getURI();
            } else {
                fireElementStateChangeFailed(element);
                throw new CoreException(new Status(IStatus.ERROR,
                        XpdResourcesPlugin.ID_PLUGIN, 0, INVALID_INPUT, null));
            }
            if (false == document instanceof IDiagramDocument) {
                fireElementStateChangeFailed(element);
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                XpdResourcesPlugin.ID_PLUGIN,
                                0,
                                String
                                        .format(Messages.WorkingCopyDocumentProvider_incorrectDoc_message,
                                                document,
                                                "org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument"), //$NON-NLS-1$
                                null));
            }
            IDiagramDocument diagramDocument = (IDiagramDocument) document;
            final Resource newResource =
                    diagramDocument.getEditingDomain().getResourceSet()
                            .createResource(newResoruceURI);

            Diagram diagram = diagramDocument.getDiagram();
            Resource resource = diagram.eResource();

            if (resource != null) {
                // Copy all contents of the resource
                EList<EObject> contents = resource.getContents();
                final Collection<EObject> contentCopy =
                        EcoreUtil.copyAll(contents);

                try {
                    new AbstractTransactionalCommand(
                            diagramDocument.getEditingDomain(),
                            String
                                    .format(Messages.WorkingCopyDocumentProvider_savingDiagramCopy_message,
                                            diagram.getName()), affectedFiles) {
                        protected CommandResult doExecuteWithResult(
                                IProgressMonitor monitor, IAdaptable info)
                                throws ExecutionException {
                            newResource.getContents().addAll(contentCopy);
                            return CommandResult.newOKCommandResult();
                        }
                    }.execute(monitor, null);
                    newResource
                            .save(GMFResourceFactory.getDefaultSaveOptions());
                } catch (ExecutionException e) {
                    fireElementStateChangeFailed(element);
                    throw new CoreException(new Status(IStatus.ERROR,
                            XpdResourcesPlugin.ID_PLUGIN, 0, e
                                    .getLocalizedMessage(), null));
                } catch (IOException e) {
                    fireElementStateChangeFailed(element);
                    throw new CoreException(new Status(IStatus.ERROR,
                            XpdResourcesPlugin.ID_PLUGIN, 0, e
                                    .getLocalizedMessage(), null));
                } finally {
                    // Unload the new resource
                    if (newResource != null) {
                        newResource.unload();
                    }
                }
            } else {
                // Failed to get resource
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                XpdResourcesPlugin.ID_PLUGIN,
                                0,
                                String
                                        .format(Messages.WorkingCopyDocumentProvider_failedToGetResourceForDiagram_message,
                                                diagram.getName()), null));
            }
        }
    }

    @Override
    protected IRunnableContext getOperationRunner(IProgressMonitor monitor) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.diagram.ui.resources.editor.document.
     * IDiagramDocumentProvider
     * #createInputWithEditingDomain(org.eclipse.ui.IEditorInput,
     * org.eclipse.emf.transaction.TransactionalEditingDomain)
     */
    public IEditorInput createInputWithEditingDomain(IEditorInput editorInput,
            TransactionalEditingDomain domain) {
        return editorInput;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.diagram.ui.resources.editor.document.
     * IDiagramDocumentProvider#getDiagramDocument(java.lang.Object)
     */
    public IDiagramDocument getDiagramDocument(Object element) {
        IDocument doc = getDocument(element);
        if (doc instanceof IDiagramDocument) {
            return (IDiagramDocument) doc;
        }
        return null;
    }

    @Override
    public long getModificationStamp(Object element) {
        ResourceInfo info = getResourceInfo(element);
        if (info != null) {
            return info.getModificationStamp();
        }
        return super.getModificationStamp(element);
    }

    @Override
    public boolean isReadOnly(Object element) {
        ResourceInfo info = getResourceInfo(element);

        if (info != null) {
            return info.isReadOnly();
        }

        return super.isReadOnly(element);
    }

    @Override
    public boolean isModifiable(Object element) {
        if (!isStateValidated(element)) {
            ResourceInfo info = getResourceInfo(element);
            if (info != null) {
                return !info.isReadOnly();
            }
        }
        return super.isModifiable(element);
    }

    @Override
    public boolean isDeleted(Object element) {
        ResourceInfo info = getResourceInfo(element);

        if (info != null) {
            return !info.isExists();
        }

        return super.isDeleted(element);
    }

    @Override
    protected void disposeElementInfo(Object element, ElementInfo info) {
        if (info instanceof ResourceInfo) {
            ((ResourceInfo) info).dispose();
        }

        super.disposeElementInfo(element, info);
    }

    @Override
    protected void doValidateState(Object element, Object computationContext)
            throws CoreException {
        ResourceInfo info = getResourceInfo(element);
        if (info != null) {
            WorkingCopy wc = info.getWorkingCopy();

            if (wc != null) {
                List<IResource> resources = wc.getEclipseResources();
                List<IFile> files = new ArrayList<IFile>();

                if (resources != null) {
                    for (IResource res : resources) {
                        if (res instanceof IFile && !((IFile) res).isReadOnly()) {
                            files.add((IFile) res);
                        }
                    }
                }

                if (!files.isEmpty()) {
                    ResourcesPlugin.getWorkspace().validateEdit(files
                            .toArray(new IFile[files.size()]),
                            computationContext);
                }
            }
        }
        super.doValidateState(element, computationContext);
    }

    @Override
    public boolean isSynchronized(Object element) {
        ResourceInfo info = getResourceInfo(element);

        if (info != null) {
            return info.isSynchronized();
        }

        return super.isSynchronized(element);
    }

    @Override
    protected ISchedulingRule getResetRule(Object element) {
        ResourceInfo info = getResourceInfo(element);
        ISchedulingRule rule = null;

        if (info != null && info.getWorkingCopy() != null) {
            List<IResource> resources =
                    info.getWorkingCopy().getEclipseResources();

            if (resources != null && !resources.isEmpty()) {
                if (resources.size() == 1) {
                    rule =
                            ResourcesPlugin.getWorkspace().getRuleFactory()
                                    .modifyRule(resources.get(0));
                } else {
                    List<ISchedulingRule> rules =
                            new ArrayList<ISchedulingRule>();

                    for (IResource res : resources) {
                        rules.add(ResourcesPlugin.getWorkspace()
                                .getRuleFactory().modifyRule(res));
                    }

                    rule =
                            new MultiRule(rules
                                    .toArray(new ISchedulingRule[rules.size()]));
                }
            }
        }
        return rule != null ? rule : super.getResetRule(element);
    }

    @Override
    protected ISchedulingRule getSaveRule(Object element) {
        ResourceInfo info = getResourceInfo(element);
        ISchedulingRule rule = null;

        if (info != null && info.getWorkingCopy() != null) {
            List<IResource> resources =
                    info.getWorkingCopy().getEclipseResources();

            if (resources != null && !resources.isEmpty()) {
                if (resources.size() == 1) {
                    rule = computeSchedulingRule(resources.get(0));
                } else {
                    List<ISchedulingRule> rules =
                            new ArrayList<ISchedulingRule>();

                    for (IResource res : resources) {
                        rules.add(computeSchedulingRule(res));
                    }

                    rule =
                            new MultiRule(rules
                                    .toArray(new ISchedulingRule[rules.size()]));
                }
            }
        }
        return rule != null ? rule : super.getSaveRule(element);
    }

    @Override
    protected ISchedulingRule getSynchronizeRule(Object element) {
        ResourceInfo info = getResourceInfo(element);
        ISchedulingRule rule = null;

        if (info != null && info.getWorkingCopy() != null) {
            List<IResource> resources =
                    info.getWorkingCopy().getEclipseResources();

            if (resources != null && !resources.isEmpty()) {
                if (resources.size() == 1) {
                    rule =
                            ResourcesPlugin.getWorkspace().getRuleFactory()
                                    .refreshRule(resources.get(0));
                } else {
                    List<ISchedulingRule> rules =
                            new ArrayList<ISchedulingRule>();

                    for (IResource res : resources) {
                        rules.add(ResourcesPlugin.getWorkspace()
                                .getRuleFactory().refreshRule(res));
                    }

                    rule =
                            new MultiRule(rules
                                    .toArray(new ISchedulingRule[rules.size()]));
                }
            }
        }
        return rule != null ? rule : super.getSynchronizeRule(element);
    }

    @Override
    protected ISchedulingRule getValidateStateRule(Object element) {
        ResourceInfo info = getResourceInfo(element);

        if (info != null && info.getWorkingCopy() != null) {
            List<IResource> resources =
                    info.getWorkingCopy().getEclipseResources();

            if (resources != null && !resources.isEmpty()) {
                return ResourcesPlugin.getWorkspace().getRuleFactory()
                        .validateEditRule(resources
                                .toArray(new IResource[resources.size()]));
            }
        }
        return super.getValidateStateRule(element);
    }

    @Override
    protected void doSynchronize(Object element, IProgressMonitor monitor)
            throws CoreException {

        ResourceInfo info = getResourceInfo(element);

        if (info != null) {
            // Synchronize the workspace with the file system
            info.synchronize();
        }
    }

    /**
     * Handle move of resource
     * 
     * @param input
     *            current input
     * @param newPath
     *            new location of the resource
     */
    protected void handleElementMoved(IEditorInput input, IPath newPath) {

        try {
            if (input instanceof FileEditorInput) {
                IFile newFile =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(newPath);
                fireElementMoved(input, newFile == null ? null
                        : new FileEditorInput(newFile));

            } else if (input instanceof URIEditorInput) {
                URIEditorInput currentInput = (URIEditorInput) input;
                URI uri =
                        URI.createPlatformResourceURI(newPath.toString(), true);
                String name = null;
                String fragment = null;
                if (currentInput != null) {
                    fragment =
                            currentInput.getURI() != null ? currentInput
                                    .getURI().fragment() : null;

                    if (fragment != null) {
                        uri = uri.appendFragment(fragment);
                    }

                    if (currentInput.getName() != null) {
                        name = currentInput.getName();
                        if (name.indexOf('#') > 0) {
                            name = name.replaceFirst(".*(?=#)", uri //$NON-NLS-1$
                                    .lastSegment());
                        }
                    }
                }

                fireElementMoved(input, new URIEditorInput(uri, name));
            }
        } catch (Exception e) {
            fireElementStateChangeFailed(input);
        }
    }

    /**
     * Computes the scheduling rule needed to create or modify a resource. If
     * the resource exists, its modify rule is returned. If it does not, the
     * resource hierarchy is iterated towards the workspace root to find the
     * first parent of <code>toCreateOrModify</code> that exists. Then the
     * 'create' rule for the last non-existing resource is returned.
     * 
     * @param toCreateOrModify
     * @return
     */
    private ISchedulingRule computeSchedulingRule(IResource toCreateOrModify) {
        if (toCreateOrModify.exists())
            return ResourcesPlugin.getWorkspace().getRuleFactory()
                    .modifyRule(toCreateOrModify);

        IResource parent = toCreateOrModify;
        do {
            toCreateOrModify = parent;
            parent = toCreateOrModify.getParent();
        } while (parent != null && !parent.exists());

        return ResourcesPlugin.getWorkspace().getRuleFactory()
                .createRule(toCreateOrModify);
    }

    /**
     * Get the working copy of the model associated with the editor input.
     * 
     * @param editorInput
     * @return Working copy of the model being edited or <code>null</code> if
     *         working copy not found.
     */
    protected AbstractGMFWorkingCopy getWorkingCopy(IEditorInput editorInput)
            throws CoreException {
        WorkingCopy wc =
                (WorkingCopy) editorInput.getAdapter(WorkingCopy.class);

        if (wc == null) {
            wc =
                    (WorkingCopy) Platform.getAdapterManager()
                            .getAdapter(editorInput, WorkingCopy.class);
        }

        return (AbstractGMFWorkingCopy) (wc instanceof AbstractGMFWorkingCopy ? wc
                : null);
    }

    /**
     * Collection of information managed for a connected object.
     * 
     * @author njpatel
     * 
     */
    protected class ResourceInfo extends ElementInfo {

        private final IEditorInput editorInput;

        private final WorkingCopy wc;

        private boolean isSynchronized = true;

        private final PropertyChangeListener listener =
                new PropertyChangeListener() {

                    public void propertyChange(PropertyChangeEvent ev) {
                        String prop = ev.getPropertyName();

                        if (prop.equals(WorkingCopy.PROP_DIRTY)) {
                            boolean isDirty = wc.isWorkingCopyDirty();

                            if (isDirty != fCanBeSaved) {
                                fCanBeSaved = isDirty;
                                fireElementDirtyStateChanged(getEditorInput(),
                                        isDirty);
                            }

                            if (!isDirty) {
                                isSynchronized = true;
                            }
                        } else if (prop.equals(WorkingCopy.PROP_REMOVED)) {

                            if (!fCanBeSaved) {
                                // If this is a move then fire move, otherwise
                                // delete
                                if (ev.getNewValue() instanceof IPath) {
                                    handleElementMoved(getEditorInput(),
                                            (IPath) ev.getNewValue());
                                } else {
                                    fireElementDeleted(getEditorInput());
                                }
                            } else {
                                isSynchronized = false;
                            }
                        } else if (prop.equals(WorkingCopy.PROP_RELOADED)) {
                            isSynchronized = true;

                            // If the working copy is invalid then fire deleted
                            // to close
                            // editor
                            if (wc.isInvalidFile()) {
                                fireElementDeleted(getEditorInput());
                            } else {
                                try {
                                    resetDocument(getEditorInput());
                                } catch (CoreException e) {
                                    ErrorDialog
                                            .openError(PlatformUI
                                                    .getWorkbench()
                                                    .getActiveWorkbenchWindow()
                                                    .getShell(),
                                                    Messages.WorkingCopyDocumentProvider_reloadFailed_title,
                                                    Messages.WorkingCopyDocumentProvider_failedToReloadDiagram_message,
                                                    e.getStatus());
                                    fireElementStateChangeFailed(getEditorInput());
                                }
                            }
                        } else if (prop.equals(WorkingCopy.CHANGED)) {
                            /*
                             * If this is a sub-editor and the object being
                             * edited by this document provider has been removed
                             * then close the editor
                             */
                            if (getEditorInput() instanceof URIEditorInput
                                    && ev instanceof NotificationPropertyChangeEvent) {
                                URI uri =
                                        ((URIEditorInput) getEditorInput())
                                                .getURI();
                                Collection<Notification> notifications =
                                        ((NotificationPropertyChangeEvent) ev)
                                                .getNotifications();

                                if (notifications != null) {
                                    for (Notification notification : notifications) {
                                        if (notification.getEventType() == Notification.REMOVE) {
                                            /*
                                             * Try to resolve the URI of the
                                             * input to an EObject - if it
                                             * cannot be done then the source of
                                             * the editor has been deleted so
                                             * close editor
                                             */
                                            EObject eo =
                                                    getEditingDomain()
                                                            .getResourceSet()
                                                            .getEObject(uri,
                                                                    false);
                                            if (eo == null) {
                                                fireElementDeleted(getEditorInput());
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                };

        /**
         * Constructor.
         * 
         * @param document
         *            <code>IDocument</code>
         * @param editorInput
         *            <code>IEditorInput</code> input
         */
        public ResourceInfo(IDiagramDocument document,
                IEditorInput editorInput, WorkingCopy wc) {
            super(document);
            this.editorInput = editorInput;
            this.wc = wc;
            startResourceListening();
            fCanBeSaved = wc.isWorkingCopyDirty();
        }

        public void synchronize() {
            if (wc != null && wc.getEclipseResources() != null) {
                List<IResource> resources = wc.getEclipseResources();

                if (resources != null) {
                    // Synchronize the file system
                    SynchronizeWorkspaceJob job =
                            new SynchronizeWorkspaceJob(
                                    getSynchronizeRule(getEditorInput()),
                                    resources);
                    job.schedule();
                }
            }
        }

        /**
         * Compute the modification stamp of this working copy.
         * 
         * @return modification stamp. 0 will be returned if modification stamp
         *         cannot be computed.
         */
        public long getModificationStamp() {
            long result = 0;
            List<IResource> resources = wc.getEclipseResources();

            if (resources != null) {
                for (IResource res : resources) {
                    if (res.getLocation() != null) {
                        result += res.getLocation().toFile().lastModified();
                    } else {
                        result += res.getModificationStamp();
                    }
                }
            }

            return result;
        }

        /**
         * Get the working copy of the connected object
         * 
         * @return <code>WorkingCopy</code>.
         */
        public WorkingCopy getWorkingCopy() {
            return wc;
        }

        /**
         * Get the editor input of the connected object
         * 
         * @return <code>IEditorInput</code>.
         */
        public IEditorInput getEditorInput() {
            return editorInput;
        }

        /**
         * Check if the resource is in sync with the file system.
         * 
         * @return <code>true</code> if the resource is in sync,
         *         <code>false</code> otherwise.
         */
        public boolean isSynchronized() {

            if (isSynchronized) {
                // Check that the resources are synched with the file system
                List<IResource> resources = wc.getEclipseResources();

                if (resources != null) {
                    for (IResource res : resources) {
                        if (!(isSynchronized =
                                res.isSynchronized(IResource.DEPTH_ZERO))) {
                            // Not synched
                            break;
                        }
                    }
                }
            }

            return isSynchronized;
        }

        /**
         * Stop listening to resource changes.
         */
        public void stopResourceListening() {
            wc.removeListener(listener);
        }

        /**
         * Start listening to resource changes
         */
        public void startResourceListening() {
            wc.addListener(listener);
        }

        /**
         * Check if the resource is read-only.
         * 
         * @return <code>true</code> if read-only, <code>false</code> otherwise.
         */
        public boolean isReadOnly() {
            return wc.isReadOnly();
        }

        /**
         * Check if the file exists.
         * 
         * @return
         */
        public boolean isExists() {
            boolean exist = wc.isExist();

            if (exist) {
                // Check that the file on the file system exists
                List<IResource> resources = wc.getEclipseResources();

                if (resources != null) {
                    for (IResource res : resources) {
                        exist =
                                res.getLocation() != null
                                        && res.getLocation().toFile().exists();

                        if (!exist) {
                            break;
                        }
                    }
                }
            }
            return exist;
        }

        /**
         * Dispose off resources. Stop resource listening.
         */
        public void dispose() {
            stopResourceListening();
        }
    }

    /**
     * Workspace job to synchronize the given resources with the file system.
     * 
     * @author njpatel
     * 
     */
    private class SynchronizeWorkspaceJob extends WorkspaceJob {

        private final List<IResource> resources;

        /**
         * Synchronize the resources with the file system
         * 
         * @param rule
         *            synchronizing rule
         * @param resources
         *            resources to synchronize
         */
        public SynchronizeWorkspaceJob(ISchedulingRule rule,
                List<IResource> resources) {
            super(Messages.WorkingCopyDocumentProvider_syncWorkspace_title);
            this.resources = resources;

            if (rule != null) {
                setRule(rule);
            }
            setPriority(SHORT);
        }

        @Override
        public IStatus runInWorkspace(IProgressMonitor monitor)
                throws CoreException {

            if (resources != null) {
                for (IResource res : resources) {
                    res.refreshLocal(IResource.DEPTH_ZERO, monitor);
                }
            }

            return STATUS_OK;
        }
    }

}
