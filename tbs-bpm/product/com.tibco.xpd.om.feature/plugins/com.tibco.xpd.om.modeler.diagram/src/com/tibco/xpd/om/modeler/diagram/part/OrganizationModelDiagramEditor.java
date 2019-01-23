package com.tibco.xpd.om.modeler.diagram.part;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.ui.parts.DomainEventDispatcher;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.internal.parts.PaletteToolTransferDropTargetListener;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;

import com.tibco.xpd.om.modeler.diagram.edit.parts.OrgModelEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationOrgUnitCompartmentEditPart;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OpenSubDiagramEditPolicy;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationOrgUnitCompartmentCanonicalEditPolicy;
import com.tibco.xpd.om.modeler.diagram.navigator.OrganizationModelNavigatorItem;
import com.tibco.xpd.om.modeler.diagram.part.custom.OrganizationModelCustomPaletteFactory;
import com.tibco.xpd.om.resources.ui.clipboard.LocalSelectionDropTargetListener;
import com.tibco.xpd.om.resources.ui.editor.IGotoObject;
import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipEnabledDomainEventDispatcher;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.wc.TransactionalWorkingCopy;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;
import com.tibco.xpd.resources.wc.gmf.WorkingCopyDocumentProvider;

/**
 * @generated
 */
/**
 * @author rgreen
 * 
 */
public class OrganizationModelDiagramEditor extends DiagramDocumentEditor
        implements ISaveablesSource, IGotoMarker, IGotoObject, ISaveablePart2 {

    private OMDiagramPartListener listener;

    private TransactionalEditingDomain editorEditingDomain;

    /**
     * @generated
     */
    public static final String ID =
            "com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorID"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static final String CONTEXT_ID =
            "com.tibco.xpd.om.modeler.diagram.ui.diagramContext"; //$NON-NLS-1$

    /**
     * @generated
     */
    public OrganizationModelDiagramEditor() {
        super(true);
    }

    @Override
    protected void initializeGraphicalViewer() {
        /*
         * 29.06.09: When GMF2.1.3 was introduced in the target platform DND got
         * broken. Whenever a OM element was dragged from the project explorer
         * to the diagram an SWT exception occurred. This was because of the
         * change to the GMF class ImageFileDropTargetListener which is
         * registered in the super class (the previous version used to check for
         * a FileTransfer and if there wasn't on would check for a
         * LocalSelectionTransfer, the new version only checks for a
         * FileTransfer). The workaround is to not call the super class and
         * instead call the initializeGraphicalViewerContent which the
         * super.super method does. The bugzilla report on this matter can be
         * found at: https://bugs.eclipse.org/bugs/show_bug.cgi?id=249718
         */

        // super.initializeGraphicalViewer();
        initializeGraphicalViewerContents();

        // Add listener for local selection transfers
        getDiagramGraphicalViewer()
                .addDropTargetListener(new LocalSelectionDropTargetListener(
                        getDiagramGraphicalViewer()));

        // Add a transfer drag target listener that is supported on
        // palette template entries whose template is a creation tool.
        // This will enable drag and drop of the palette shape creation
        // tools.
        getDiagramGraphicalViewer()
                .addDropTargetListener(new PaletteToolTransferDropTargetListener(
                        getGraphicalViewer()) {
                    @Override
                    public boolean isEnabled(DropTargetEvent event) {
                        // Set this so that we get the unexecutable icon when
                        // mouse is over the canvas
                        setEnablementDeterminedByCommand(true);
                        return super.isEnabled(event);
                    }
                });
    }

    @Override
    protected void setPartName(String partName) {
        IDocumentProvider provider = getDocumentProvider(getEditorInput());
        if (provider != null && provider.isReadOnly(getEditorInput())) {
            partName =
                    String.format(Messages.OrganizationModelDiagramEditor_readOnlyTagged_editor_title,
                            partName);
        }
        super.setPartName(partName);
    }

    /**
     * @generated
     */
    @Override
    protected String getContextID() {
        return CONTEXT_ID;
    }

    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {

        super.init(site, input);

        // Create a listener that informs us when this editor has been activated
        // so that we can invoke an autoarrange
        listener = new OMDiagramPartListener(this);
        site.getPage().addPartListener(listener);
    }

    @Override
    public void dispose() {
        getSite().getPage().removePartListener(listener);
        listener = null;

        super.dispose();
    }

    @Override
    protected ScrollingGraphicalViewer createScrollingGraphicalViewer() {
        /*
         * Create our own graphical viewer that supports the quickfix tooltip
         * for problem decorators.
         */
        return new OMGraphicalViewer();
    }

    @Override
    protected IUndoContext getUndoContext() {
        IDocumentProvider provider = getDocumentProvider();

        if (provider instanceof WorkingCopyDocumentProvider
                && getEditorInput() != null) {
            WorkingCopy wc =
                    ((WorkingCopyDocumentProvider) provider)
                            .getWorkingCopy(getEditorInput());

            if (wc instanceof TransactionalWorkingCopy) {
                return ((TransactionalWorkingCopy) wc).getUndoContext();
            }
        }
        return super.getUndoContext();
    }

    @Override
    protected boolean shouldAddUndoContext(IUndoableOperation operation) {
        /*
         * The default implementation of this adds this editor's context to all
         * operation on the same editing domain as it was designed for multiple
         * editing domains. As we use a single editing domain we should not add
         * this editor's undo context (the correct context will already be
         * applied to the operation).
         */
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.om.resources.ui.editor.IGotoObject#reveal(org.eclipse.emf
     * .ecore.EObject)
     */
    @Override
    public void reveal(EObject eo) {
        TreeIterator<EObject> content = getDiagram().eAllContents();
        while (content.hasNext()) {
            EObject ref = content.next();

            if (ref instanceof View) {
                if (((View) ref).getElement() != eo) {
                    continue;
                }
                EditPart ep =
                        (EditPart) getGraphicalViewer().getEditPartRegistry()
                                .get(ref);
                getGraphicalViewer().select(ep);
                getGraphicalViewer().reveal(ep);

                if (ep instanceof OrganizationEditPart) {
                    EditPolicy editPolicy =
                            ep.getEditPolicy(EditPolicyRoles.OPEN_ROLE);

                    if (editPolicy instanceof OpenSubDiagramEditPolicy) {
                        OpenSubDiagramEditPolicy openEP =
                                (OpenSubDiagramEditPolicy) editPolicy;

                        SelectionRequest req = new SelectionRequest();

                        req.setType(RequestConstants.REQ_OPEN);

                        Command command = openEP.getCommand(req);

                        if (command != null && command.canExecute()) {
                            command.execute();
                        }

                    }

                }

                return;
            }
        }
    }

    /**
     * @generated NOT
     */
    @Override
    protected PaletteRoot createPaletteRoot(PaletteRoot existingPaletteRoot) {
        PaletteRoot root = super.createPaletteRoot(existingPaletteRoot);
        new OrganizationModelCustomPaletteFactory().fillPalette(root);
        return root;
    }

    /**
     * @generated
     */
    @Override
    protected PreferencesHint getPreferencesHint() {
        return OrganizationModelDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
    }

    /**
     * @generated
     */
    @Override
    public String getContributorId() {
        return OrganizationModelDiagramEditorPlugin.ID;
    }

    /**
     * @generated NOT
     */
    @Override
    public Object getAdapter(Class type) {

        AbstractGMFWorkingCopy wc = getWorkingCopy(getEditorInput());

        if (type == WorkingCopy.class) {
            return wc;
        }

        if (type == IShowInTargetList.class) {
            return new IShowInTargetList() {
                @Override
                public String[] getShowInTargetIds() {
                    return new String[] { ProjectExplorer.VIEW_ID };
                }
            };
        }
        return super.getAdapter(type);
    }

    /**
     * @param editorInput
     * @return
     */
    protected AbstractGMFWorkingCopy getWorkingCopy(IEditorInput editorInput) {
        AbstractGMFWorkingCopy gmfWorkingCopy = null;

        if (editorInput != null) {
            WorkingCopy wc = null;
            IDocumentProvider provider = getDocumentProvider(editorInput);
            if (provider instanceof WorkingCopyDocumentProvider) {
                wc =
                        ((WorkingCopyDocumentProvider) provider)
                                .getWorkingCopy(editorInput);
            }

            if (wc == null) {
                wc =
                        (WorkingCopy) Platform.getAdapterManager()
                                .getAdapter(editorInput, WorkingCopy.class);
            }

            if (wc instanceof AbstractGMFWorkingCopy) {
                gmfWorkingCopy = (AbstractGMFWorkingCopy) wc;
            }
        }

        return gmfWorkingCopy;
    }

    /**
     * @generated
     */
    @Override
    protected IDocumentProvider getDocumentProvider(IEditorInput input) {
        if (input instanceof IStorageEditorInput
                || input instanceof URIEditorInput) {
            return OrganizationModelDiagramEditorPlugin.getInstance()
                    .getDocumentProvider();
        }
        return super.getDocumentProvider(input);
    }

    /**
     * @generated NOT
     */
    @Override
    public TransactionalEditingDomain getEditingDomain() {
        if (editorEditingDomain == null) {
            IDocument document =
                    (getEditorInput() != null && getDocumentProvider() != null) ? getDocumentProvider()
                            .getDocument(getEditorInput()) : null;
            if (document instanceof IDiagramDocument) {
                editorEditingDomain =
                        ((IDiagramDocument) document).getEditingDomain();
            } else {
                editorEditingDomain = super.getEditingDomain();
            }

        }
        return editorEditingDomain;
    }

    /**
     * @generated
     */
    @Override
    protected void setDocumentProvider(IEditorInput input) {
        if (input instanceof IStorageEditorInput
                || input instanceof URIEditorInput) {
            setDocumentProvider(OrganizationModelDiagramEditorPlugin
                    .getInstance().getDocumentProvider());
        } else {
            super.setDocumentProvider(input);
        }
    }

    /**
     * @generated NOT
     */
    @Override
    public void gotoMarker(IMarker marker) {
        Object id;
        try {
            id = marker.getAttribute(IMarker.LOCATION);
            if (id instanceof String) {
                Diagram diagram = getDiagram();
                EObject eo = diagram.eResource().getEObject((String) id);
                if (eo != null) {
                    OrganizationModelDiagramEditorUtil.openDiagram(eo);
                }
            }
        } catch (CoreException e) {
            OrganizationModelDiagramEditorPlugin.getInstance().getLogger()
                    .error(e);
        }
    }

    /**
     * @generated
     */
    @Override
    public boolean isSaveAsAllowed() {
        return true;
    }

    /**
     * @generated
     */
    @Override
    public void doSaveAs() {
        performSaveAs(new NullProgressMonitor());
    }

    /**
     * @generated
     */
    @Override
    protected void performSaveAs(IProgressMonitor progressMonitor) {
        Shell shell = getSite().getShell();
        IEditorInput input = getEditorInput();
        SaveAsDialog dialog = new SaveAsDialog(shell);
        IFile original =
                input instanceof IFileEditorInput ? ((IFileEditorInput) input)
                        .getFile() : null;
        if (original != null) {
            dialog.setOriginalFile(original);
        }
        dialog.create();
        IDocumentProvider provider = getDocumentProvider();
        if (provider == null) {
            // editor has been programmatically closed while the dialog was open
            return;
        }
        if (provider.isDeleted(input) && original != null) {
            String message =
                    NLS.bind(Messages.OrganizationModelDiagramEditor_SavingDeletedFile,
                            original.getName());
            dialog.setErrorMessage(null);
            dialog.setMessage(message, IMessageProvider.WARNING);
        }
        if (dialog.open() == Window.CANCEL) {
            if (progressMonitor != null) {
                progressMonitor.setCanceled(true);
            }
            return;
        }
        IPath filePath = dialog.getResult();
        if (filePath == null) {
            if (progressMonitor != null) {
                progressMonitor.setCanceled(true);
            }
            return;
        }
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IFile file = workspaceRoot.getFile(filePath);
        final IEditorInput newInput = new FileEditorInput(file);
        // Check if the editor is already open
        IEditorMatchingStrategy matchingStrategy =
                getEditorDescriptor().getEditorMatchingStrategy();
        IEditorReference[] editorRefs =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getEditorReferences();
        for (int i = 0; i < editorRefs.length; i++) {
            if (matchingStrategy.matches(editorRefs[i], newInput)) {
                MessageDialog
                        .openWarning(shell,
                                Messages.OrganizationModelDiagramEditor_SaveAsErrorTitle,
                                Messages.OrganizationModelDiagramEditor_SaveAsErrorMessage);
                return;
            }
        }
        boolean success = false;
        try {
            provider.aboutToChange(newInput);
            getDocumentProvider(newInput).saveDocument(progressMonitor,
                    newInput,
                    getDocumentProvider().getDocument(getEditorInput()),
                    true);
            success = true;
        } catch (CoreException x) {
            IStatus status = x.getStatus();
            if (status == null || status.getSeverity() != IStatus.CANCEL) {
                ErrorDialog
                        .openError(shell,
                                Messages.OrganizationModelDiagramEditor_SaveErrorTitle,
                                Messages.OrganizationModelDiagramEditor_SaveErrorMessage,
                                x.getStatus());
            }
        } finally {
            provider.changed(newInput);
            if (success) {
                setInput(newInput);
            }
        }
        if (progressMonitor != null) {
            progressMonitor.setCanceled(!success);
        }
    }

    /**
     * @generated
     */
    @Override
    public ShowInContext getShowInContext() {
        return new ShowInContext(getEditorInput(), getNavigatorSelection());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISaveablesSource#getActiveSaveables()
     */
    @Override
    public Saveable[] getActiveSaveables() {
        IDocumentProvider documentProvider =
                getDocumentProvider(getEditorInput());

        if (documentProvider instanceof OrganizationModelDocumentProvider) {
            return ((OrganizationModelDocumentProvider) documentProvider)
                    .getSaveables(getEditorInput());
        }

        return new Saveable[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.ISaveablesSource#getSaveables()
     */
    @Override
    public Saveable[] getSaveables() {
        return getActiveSaveables();
    }

    /**
     * @generated NOT
     */
    private ISelection getNavigatorSelection() {
        IDiagramDocument document = getDiagramDocument();
        if (document == null) {
            return StructuredSelection.EMPTY;
        }
        Diagram diagram = document.getDiagram();
        if (diagram != null && diagram.eResource() != null) {
            IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());
            if (file != null) {
                OrganizationModelNavigatorItem item =
                        new OrganizationModelNavigatorItem(diagram, file, false);
                return new StructuredSelection(item);
            }
        }
        return StructuredSelection.EMPTY;
    }

    /**
     * @generated
     */
    @Override
    protected void configureGraphicalViewer() {
        super.configureGraphicalViewer();
        DiagramEditorContextMenuProvider provider =
                new DiagramEditorContextMenuProvider(this,
                        getDiagramGraphicalViewer());
        getDiagramGraphicalViewer().setContextMenu(provider);
        getSite().registerContextMenu(ActionIds.DIAGRAM_EDITOR_CONTEXT_MENU,
                provider,
                getDiagramGraphicalViewer());
    }

    /**
     * 
     * This listener will invoke an autoarrange on the Organization editparts
     * compartment whenever this editor is activated.
     * 
     * @author rgreen
     * 
     */
    private class OMDiagramPartListener implements IPartListener {

        DiagramDocumentEditor editor;

        public OMDiagramPartListener(DiagramDocumentEditor ed) {
            editor = ed;
        }

        @Override
        public void partActivated(IWorkbenchPart part) {
            if ((part instanceof OrganizationModelDiagramEditor)
                    && (part == editor)) {

                DiagramEditPart diagramEditPart = getDiagramEditPart();

                if (diagramEditPart instanceof OrgModelEditPart) {
                    Map<?, ?> editPartRegistry =
                            getDiagramGraphicalViewer().getEditPartRegistry();

                    Collection<?> values = editPartRegistry.values();

                    for (Object object : values) {
                        if (object instanceof OrganizationOrgUnitCompartmentEditPart) {
                            OrganizationOrgUnitCompartmentEditPart cpt =
                                    (OrganizationOrgUnitCompartmentEditPart) object;

                            EditPolicy editPolicy =
                                    cpt.getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);

                            if (editPolicy instanceof OrganizationOrgUnitCompartmentCanonicalEditPolicy) {
                                OrganizationOrgUnitCompartmentCanonicalEditPolicy canonicalEP =
                                        (OrganizationOrgUnitCompartmentCanonicalEditPolicy) editPolicy;

                                canonicalEP.performAutoArrange();

                            }

                        }
                    }

                }

            }
        }

        @Override
        public void partBroughtToTop(IWorkbenchPart part) {
        }

        @Override
        public void partClosed(IWorkbenchPart part) {
        }

        @Override
        public void partDeactivated(IWorkbenchPart part) {
        }

        @Override
        public void partOpened(IWorkbenchPart part) {
        }

    }

    /**
     * Extension of the diagram graphical viewer that includes the quick fix
     * tooltip.
     * 
     * @author njpatel
     * 
     */
    private class OMGraphicalViewer extends DiagramGraphicalViewer {
        private QuickFixToolTipEnabledDomainEventDispatcher stickyToolTipEnabledDomainEventDispatcher;

        @Override
        public void setEditDomain(EditDomain domain) {
            super.setEditDomain(domain);
            stickyToolTipEnabledDomainEventDispatcher =
                    new QuickFixToolTipEnabledDomainEventDispatcher(domain,
                            this);
            getLightweightSystem()
                    .setEventDispatcher(stickyToolTipEnabledDomainEventDispatcher);
        }

        @Override
        protected DomainEventDispatcher getEventDispatcher() {
            /*
             * Override to set our own domain event dispatcher, doing so seems
             * to be the ONLY way of providing a different ToolTipHelper which
             * seems to be the only way of providing a way of not destroying the
             * tooltip if the mouse is moved awy from original host figure and
             * over the tooltip.
             * 
             * We overrode the domain event dispatcher so we should get
             * underlying class to use ours.
             */
            return stickyToolTipEnabledDomainEventDispatcher;
        }
    }

    @Override
    public int promptToSaveOnClose() {
        /*
         * Don't prompt the user to save on editor close when running in RCP
         * application
         */
        if (XpdResourcesPlugin.isRCP()) {
            return NO;
        }

        return DEFAULT;
    }
}
