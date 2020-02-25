/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.edit.command.AbstractOverrideableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Relationship;

import com.tibco.xpd.bom.modeler.custom.Activator;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;

/**
 * New wizard to add a new user {@link Diagram} to a BOM.
 * 
 * @author njpatel
 * 
 */
public class NewDiagramWizard extends BasicNewXpdResourceWizard {

    private SelectBOMElementsWizardPage elementSelectionPage;

    private BOMFileSelectionPage fileSelectionPage;

    private Model model;

    private IWorkbenchPage page;

    private final TransactionalEditingDomain ed;

    private Diagram newDiagram;

    private boolean showFileSelectionPage;

    /**
     * Wizard to add a new diagram. This will show a wizard that will include a
     * file selection page as the first page.
     * 
     * @see #NewDiagramWizard(boolean)
     */
    public NewDiagramWizard() {
        ed = XpdResourcesPlugin.getDefault().getEditingDomain();
        setWindowTitle(Messages.NewDiagramWizard_shortdesc);
        // Show the file selection page by default
        showFileSelectionPage = true;
    }

    /**
     * Hide the file selection page in this wizard.
     */
    public void hideFileSelectionPage() {
        showFileSelectionPage = false;
    }

    @Override
    public void addPages() {
        if (showFileSelectionPage) {
            fileSelectionPage =
                    new BOMFileSelectionPage("selectFile", getSelection()); //$NON-NLS-1$
            fileSelectionPage.setTitle(getWindowTitle());
            fileSelectionPage
                    .setDescription(Messages.NewDiagramWizard_fileSelectionPage_shortdesc);
            addPage(fileSelectionPage);
        }

        elementSelectionPage = new SelectBOMElementsWizardPage();
        elementSelectionPage.setTitle(getWindowTitle());
        elementSelectionPage
                .setDescription(Messages.NewDiagramWizard_elementSelectionPage_shortdesc);
        addPage(elementSelectionPage);
        elementSelectionPage.setInput(model);
    }

    @Override
    public IWizardPage getStartingPage() {
        IWizardPage page = super.getStartingPage();

        // If the selection is already made then move to subsequent page (to
        // skip the BOM selection page)
        if (showFileSelectionPage && model != null) {
            page = page.getNextPage();
        }

        return page;
    }

    @Override
    public boolean performFinish() {
        final Collection<EObject> selection =
                elementSelectionPage.getSelection();
        final String name = elementSelectionPage.getDiagramName();

        AddDiagramCommand cmd =
                new AddDiagramCommand(ed, model, name, selection, page,
                        getShell());
        ed.getCommandStack().execute(cmd);
        newDiagram = cmd.getDiagram();

        return true;
    }

    /**
     * Get the {@link Diagram} that was created.
     * 
     * @return Diagram if created, <code>null</code> otherwise.
     */
    public Diagram getNewDiagram() {
        return newDiagram;
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        super.init(workbench, selection);
        page = workbench.getActiveWorkbenchWindow().getActivePage();
        if (!selection.isEmpty()) {
            Object firstElement = selection.getFirstElement();
            if (firstElement instanceof IFile) {
                WorkingCopy wc =
                        WorkingCopyUtil
                                .getWorkingCopy((IResource) firstElement);
                if (wc instanceof BOMWorkingCopy) {
                    EObject root = wc.getRootElement();
                    if (root instanceof Model) {
                        setSelection((Model) root);
                    }
                }
            }
        }
    }

    /**
     * Set the input selection for this page.
     * 
     * @param model
     */
    protected void setSelection(Model model) {
        this.model = model;
        if (elementSelectionPage != null) {
            elementSelectionPage.setInput(model);
        }
    }

    /**
     * Command to add a new BOM Diagram.
     * 
     * @author njpatel
     * 
     */
    private static class AddDiagramCommand extends AbstractOverrideableCommand {

        private final Model model;

        private final String diagramName;

        private final Collection<EObject> selection;

        private Diagram diagram;

        private final IWorkbenchPage page;

        private final Shell shell;

        protected AddDiagramCommand(EditingDomain domain, Model model,
                String diagramName, Collection<EObject> selection,
                IWorkbenchPage page, Shell shell) {
            super(domain);
            this.model = model;
            this.diagramName = diagramName;
            this.selection = selection;
            this.page = page;
            this.shell = shell;
            setLabel(Messages.NewDiagramWizard_addDiagram_command_label);
        }

        /**
         * Get the diagram that was created.
         * 
         * @return
         */
        public Diagram getDiagram() {
            return diagram;
        }

        @Override
        protected boolean prepare() {
            return true;
        }

        @Override
        public void doExecute() {
            diagram =
                    createDiagram(model, diagramName, new NullProgressMonitor());
            IEditorPart part;
            try {
                // Open editor and add new nodes to the diagram
                part = UMLDiagramEditorUtil.openEditor(page, diagram);

                if (part instanceof IDiagramWorkbenchPart) {
                    final DiagramEditPart editPart =
                            ((IDiagramWorkbenchPart) part).getDiagramEditPart();
                    final IDiagramEditDomain editDomain =
                            ((IDiagramWorkbenchPart) part)
                                    .getDiagramEditDomain();
                    if (editPart != null && editDomain != null
                            && !selection.isEmpty()) {
                        Command cmd = getAddNodesCommand(editPart, selection);
                        if (cmd != null && cmd.canExecute()) {
                            cmd.execute();
                            /*
                             * Schedule an auto-arrange to be carried out in the
                             * new diagram. For some reason if the auto-arrange
                             * is done here directly then it doesn't work
                             * properly - it will leave the nodes in the diagram
                             * stacked.
                             */
                            new AutoArrangeJob(
                                    (TransactionalEditingDomain) getDomain(),
                                    diagramName, editPart).schedule();
                        }
                    }
                }
            } catch (PartInitException e) {
                Activator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                Messages.NewDiagramWizard_problemOpeningEditor_error_message);
                ErrorDialog
                        .openError(shell,
                                Messages.NewDiagramWizard_errorOpeningEditor_dialog_title,
                                Messages.NewDiagramWizard_errorOpeningEditor_dialog_shortdesc,
                                new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                                        e.getLocalizedMessage(), e));
            }
        }

        @Override
        public void doRedo() {
            if (diagram == null) {
                doExecute();
            }
        }

        @Override
        public void doUndo() {
            // Delete diagram
            DestroyElementRequest req =
                    new DestroyElementRequest(diagram, false);
            ICommand editCommand =
                    ElementTypeRegistry.getInstance().getElementType(diagram)
                            .getEditCommand(req);
            if (editCommand != null) {
                try {
                    IStatus status =
                            editCommand
                                    .execute(new NullProgressMonitor(), null);
                    if (status.isOK()) {
                        diagram = null;
                    } else if (status.getSeverity() == IStatus.ERROR) {
                        throw new ExecutionException(status.getMessage(),
                                new CoreException(status));
                    } else {
                        // For any other status type log the message
                        Activator.getDefault().getLogger().log(status);
                    }
                } catch (ExecutionException e) {
                    Activator
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    Messages.NewDiagramWizard_UndoFailed_log_error_message);
                    ErrorDialog
                            .openError(shell,
                                    Messages.NewDiagramWizard_errorOpeningEditor_dialog_title,
                                    Messages.NewDiagramWizard_undoFailed_errorDialog_message,
                                    new Status(IStatus.ERROR,
                                            Activator.PLUGIN_ID, e
                                                    .getLocalizedMessage(), e));
                }
            }
        }

        /**
         * Create the new diagram
         * 
         * @param model
         * @param name
         * @param monitor
         * @return
         */
        private Diagram createDiagram(Model model, String name,
                IProgressMonitor monitor) {
            Diagram diagram =
                    ViewService.createDiagram(model,
                            CanvasPackageEditPart.MODEL_ID,
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
            diagram.setName(name);
            EAnnotation annot = EcoreFactory.eINSTANCE.createEAnnotation();
            annot.setSource(BomUIUtil.USER_DIAGRAM);
            diagram.getEAnnotations().add(annot);

            if (diagram != null) {
                model.eResource().getContents().add(diagram);
            }
            return diagram;
        }

        /**
         * Get the add nodes command. This will create the view nodes of the
         * selected elements to add to the diagram.
         * 
         * @param diagEditPart
         * @param selectedSemanticElements
         * @return
         */
        private Command getAddNodesCommand(DiagramEditPart diagEditPart,
                Collection<EObject> selectedSemanticElements) {
            if (selectedSemanticElements != null
                    && !selectedSemanticElements.isEmpty()) {
                CompoundCommand commands = new CompoundCommand();
                IClientContext cc =
                        ClientContextManager.getInstance()
                                .getClientContextFor(model);

                int x = 120;
                for (EObject eo : selectedSemanticElements) {
                    if (!(eo instanceof Relationship)) {
                        String semanticHint = null;
                        IElementType type = null;

                        if (eo instanceof Package) {
                            // Make sure we use the Package and not Model type
                            semanticHint =
                                    String.valueOf(PackageEditPart.VISUAL_ID);
                        } else {
                            type =
                                    ElementTypeRegistry.getInstance()
                                            .getElementType(eo, cc);
                            if (type instanceof IHintedType) {
                                semanticHint =
                                        ((IHintedType) type).getSemanticHint();
                            }
                        }

                        CreateViewRequest req =
                                new CreateViewRequest(
                                        new ViewDescriptor(
                                                new EObjectAdapter(eo),
                                                Node.class,
                                                semanticHint,
                                                BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT));
                        // Add a suitable location point so that auto-arrange
                        // will place the items in a suitable layout
                        req.setLocation(new Point(x, 120));
                        x += 100;
                        Command command = diagEditPart.getCommand(req);

                        if (command != null) {
                            commands.add(command);
                        }
                    }
                }

                if (!commands.isEmpty()) {
                    if (commands.canExecute()) {
                        return commands;
                    }
                }
            }
            return null;
        }
    }

    /**
     * UI Job to carry out an auto-arrange on the new diagram.
     * 
     * @author njpatel
     * 
     */
    private static class AutoArrangeJob extends UIJob {

        private final EditPart parentEditPart;

        private final TransactionalEditingDomain ed;

        public AutoArrangeJob(TransactionalEditingDomain ed, String name,
                EditPart parentEditPart) {
            super(name);
            this.ed = ed;
            this.parentEditPart = parentEditPart;
        }

        @Override
        public IStatus runInUIThread(IProgressMonitor monitor) {

            if (parentEditPart != null) {
                List<EditPart> toArrange = new ArrayList<EditPart>();

                for (Object child : parentEditPart.getChildren()) {
                    if (child instanceof EditPart
                            && !(child instanceof BadgeEditPart)) {
                        toArrange.add((EditPart) child);
                    }
                }

                if (!toArrange.isEmpty()) {
                    ArrangeRequest request =
                            new ArrangeRequest(
                                    ActionIds.ACTION_ARRANGE_SELECTION);
                    request.setPartsToArrange(toArrange);

                    Command cmd = parentEditPart.getCommand(request);
                    if (cmd != null && cmd.canExecute()) {
                        Transaction transaction = null;
                        try {
                            transaction =
                                    ((InternalTransactionalEditingDomain) ed)
                                            .startTransaction(false,
                                                    Collections.EMPTY_MAP);
                            cmd.execute();
                        } catch (InterruptedException e) {
                            Activator
                                    .getDefault()
                                    .getLogger()
                                    .error(e,
                                            Messages.NewDiagramWizard_autoArrangeFailed_error_message);
                        } finally {
                            if (transaction != null) {
                                try {
                                    transaction.commit();
                                } catch (RollbackException e) {
                                    Activator
                                            .getDefault()
                                            .getLogger()
                                            .error(e,
                                                    Messages.NewDiagramWizard_autoArrangeFailed_error_message);
                                }
                            }
                        }
                    }
                }
            }

            return Status.OK_STATUS;
        }
    }


}
