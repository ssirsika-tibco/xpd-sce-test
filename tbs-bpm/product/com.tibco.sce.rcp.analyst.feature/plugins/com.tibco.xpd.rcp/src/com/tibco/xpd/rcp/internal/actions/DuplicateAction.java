/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.analyst.processinterface.editor.inputs.ProcessInterfaceEditorInput;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.CopyAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.PasteAction;
import com.tibco.xpd.bom.modeler.custom.commands.BOMDuplicateCommand;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.diagram.part.OrganizationModelDiagramEditorUtil;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.om.resources.ui.commands.OMDuplicateCommand;
import com.tibco.xpd.processeditor.xpdl2.ProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.overview.OverviewEditor;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Action to duplicate the selected diagram.
 * 
 * @author njpatel
 * 
 */
public class DuplicateAction extends EditorEventAction {

    private static final String PROCESS_INTERFACE_EDITOR_ID =
            "com.tibco.xpd.analyst.editor.ProcessInterfaceEditor"; //$NON-NLS-1$

    public DuplicateAction(IWorkbenchWindow window) {
        super(window, Messages.DuplicateAction_title);
        setToolTipText(Messages.DuplicateAction_tooltip);
        setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
        setDisabledImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
    }

    @Override
    public void partActivated(IWorkbenchPart part) {
        IEditorPart editor = getEditorActivated(part);

        if (editor != null) {
            if (editor instanceof OverviewEditor) {
                setEnabled(false);
            } else if (editor.getEditorInput() instanceof IFileEditorInput
                    && !enableFor((IFileEditorInput) editor.getEditorInput())) {
                setEnabled(false);
            } else {
                super.partActivated(part);
            }
        } else {
            setEnabled(false);
        }
    }

    /**
     * Check if this action should be enabled for the given file editor input.
     * 
     * @param input
     * @return
     */
    private boolean enableFor(IFileEditorInput input) {
        IFile file = input.getFile();
        if (BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(file
                .getFileExtension())) {
            return true;
        } else if (Xpdl2ResourcesConsts.XPDL_EXTENSION.equals(file
                .getFileExtension())) {
            return true;
        } else if (OMResourcesActivator.OM_FILE_EXTENSION.equals(file
                .getFileExtension())) {
            return true;
        }
        return false;
    }

    @Override
    protected void run(IEditorInput input) throws CoreException {
        if (input instanceof ProcessEditorInput) {
            duplicateProcess(((ProcessEditorInput) input).getWorkingCopy(),
                    ((ProcessEditorInput) input).getProcess());
        } else if (input instanceof ProcessInterfaceEditorInput) {
            duplicateProcess(((ProcessInterfaceEditorInput) input).getWorkingCopy(),
                    ((ProcessInterfaceEditorInput) input).getProcessInterface());
        } else if (input instanceof URIEditorInput) {
            URI uri = ((URIEditorInput) input).getURI();
            EObject eo =
                    getEditingDomain().getResourceSet().getEObject(uri, false);

            if (eo instanceof View) {
                eo = ((View) eo).getElement();
            }

            if (eo instanceof Organization) {
                duplicateOrganizationElement(Messages.DuplicateAction_duplicateOrganization_command_label,
                        eo);
            }
        } else if (input instanceof IFileEditorInput) {
            WorkingCopy wc =
                    WorkingCopyUtil.getWorkingCopy(((IFileEditorInput) input)
                            .getFile());
            if (wc != null) {
                EObject eo = wc.getRootElement();
                if (eo instanceof Model) {
                    duplicateModel((Model) eo);
                } else if (eo instanceof Package) {
                    duplicateProcessPackage(eo);
                } else if (eo instanceof OrgModel) {
                    duplicateOrganizationElement(Messages.DuplicateAction_duplicateOrgModel_command_label,
                            eo);
                }
            }
        }
    }

    /**
     * Duplicate process package (xpdl file).
     * 
     * @param element
     * @throws PartInitException
     */
    private void duplicateProcessPackage(EObject element)
            throws PartInitException {
        Xpdl2DuplicateCommand cmd =
                new Xpdl2DuplicateCommand(
                        Messages.DuplicateAction_duplicateProcessPackage_command_label,
                        element);
        getEditingDomain().getCommandStack().execute(cmd);
        Collection<?> result = cmd.getResult();
        if (result != null && !result.isEmpty()) {
            Object next = result.iterator().next();
            if (next instanceof IFile) {
                IDE.openEditor(getWindow().getActivePage(), (IFile) next);
            }
        }
    }

    /**
     * Duplicate process or process interface.
     * 
     * @param wc
     * @param element
     * @throws PartInitException
     */
    private void duplicateProcess(WorkingCopy wc, EObject element)
            throws PartInitException {
        Xpdl2DuplicateCommand cmd =
                new Xpdl2DuplicateCommand(
                        Messages.DuplicateAction_duplicateProcess_command_label,
                        element);
        getEditingDomain().getCommandStack().execute(cmd);
        Collection<?> result = cmd.getResult();
        if (result != null && !result.isEmpty()) {
            for (Iterator<?> iter = result.iterator(); iter.hasNext();) {
                Object next = iter.next();
                if (next instanceof Process) {
                    IDE.openEditor(getWindow().getActivePage(),
                            new ProcessEditorInput(wc, (Process) next),
                            ProcessDiagramEditor.EDITOR_ID);
                    break;
                } else if (next instanceof ProcessInterface) {
                    IDE.openEditor(getWindow().getActivePage(),
                            new ProcessInterfaceEditorInput(wc,
                                    (ProcessInterface) next),
                            PROCESS_INTERFACE_EDITOR_ID);
                }
            }
        }
    }

    /**
     * Duplicate an OrgModel or Organization.
     * 
     * @param label
     *            command label
     * @param toDuplicate
     *            {@link OrgModel} or {@link Organization}
     * @throws PartInitException
     */
    private void duplicateOrganizationElement(String label,
            final EObject toDuplicate) throws PartInitException {

        OMDuplicateCommand cmd =
                new OMDuplicateCommand(label, getEditingDomain(), toDuplicate) {
                    /**
                     * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
                     * 
                     * @return
                     */
                    @Override
                    public boolean canUndo() {
                        /*
                         * (XPD-4441) Don't allow undo of Organization
                         * duplication as this does not work properly. If an
                         * Organization is duplicated multiple times and an undo
                         * is attempted then it fails with an exception and
                         * breaks the in-memory model.
                         */
                        return !(toDuplicate instanceof Organization);
                    }
                };
        getEditingDomain().getCommandStack().execute(cmd);
        Collection<?> result = cmd.getResult();
        if (result != null && !result.isEmpty()) {
            Object duplicateObj = result.iterator().next();
            if (duplicateObj instanceof Organization) {
                OrganizationModelDiagramEditorUtil
                        .openDiagram((EObject) duplicateObj);
            } else if (duplicateObj instanceof IFile) {
                IDE.openEditor(getWindow().getActivePage(),
                        (IFile) duplicateObj);
            }
        }
    }

    /**
     * Duplicate the BOM.
     * 
     * @param model
     * @throws PartInitException
     */
    private void duplicateModel(Model model) throws PartInitException {
        BOMDuplicateCommand cmd =
                new BOMDuplicateCommand(
                        Messages.DuplicateAction_duplicateBOM_command_label,
                        model);
        getEditingDomain().getCommandStack().execute(cmd);
        Collection<?> result = cmd.getResult();

        if (result != null && !result.isEmpty()) {
            Object duplicate = result.iterator().next();
            if (duplicate instanceof IFile) {
                IDE.openEditor(getWindow().getActivePage(), (IFile) duplicate);
            }
        }
    }

    /**
     * Command to duplicate a Package (duplicate the xpdl file), Process
     * Interface or Process.
     */
    private class Xpdl2DuplicateCommand extends AbstractCommand {

        private final EObject toDuplicate;

        private AbstractCommand command;

        public Xpdl2DuplicateCommand(String label, EObject toDuplicate) {
            super(label);
            this.toDuplicate = toDuplicate;
        }

        @Override
        protected boolean prepare() {
            if (toDuplicate instanceof Package) {
                // Duplicate xpdl2 file
                IFile file = WorkingCopyUtil.getFile(toDuplicate);
                if (file != null && file.exists()) {
                    command = new DuplicatePackageCommand(file);
                    return ((DuplicatePackageCommand) command).prepare();
                }
            } else {
                command =
                        new DuplicateProcessCommand(
                                WorkingCopyUtil.getWorkingCopyFor(toDuplicate),
                                toDuplicate);
                return ((DuplicateProcessCommand) command).prepare();
            }
            return false;
        }

        @Override
        public void execute() {
            if (command != null) {
                command.execute();
            }
        }

        @Override
        public Collection<?> getResult() {
            return command != null ? command.getResult() : null;
        }

        @Override
        public void redo() {
            if (command != null) {
                command.redo();
            }
        }

        @Override
        public void undo() {
            if (command != null) {
                command.undo();
            }
        }
    }

    /**
     * Command to duplicate the Package, ie duplicate the xpdl file.
     */
    private class DuplicatePackageCommand extends AbstractCommand {

        private final IFile file;

        private IFile duplicate;

        private Collection<?> result;

        public DuplicatePackageCommand(IFile file) {
            this.file = file;
        }

        @Override
        protected boolean prepare() {
            return file != null
                    && file.exists()
                    && Xpdl2ResourcesConsts.XPDL_EXTENSION.equals(file
                            .getFileExtension());
        }

        @Override
        public void execute() {
            if (file != null) {
                int idx = getCopyIndex(file);
                duplicate =
                        file.getParent()
                                .getFile(new Path(
                                        String.format("Copy_%d_%s", idx, file.getName()))); //$NON-NLS-1$
                try {
                    file.copy(duplicate.getFullPath(), true, null);
                    updatePackageLabel(duplicate, idx);
                    result = Collections.singleton(duplicate);
                } catch (CoreException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        private void updatePackageLabel(IFile duplicate, int copyIndex) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(duplicate);
            if (wc != null && wc.getRootElement() instanceof Package) {
                Package pkg = (Package) wc.getRootElement();
                List<String> modelLabels =
                        getAllPackageLabels(duplicate.getProject().getName());
                String label = Xpdl2ModelUtil.getDisplayName(pkg);
                String duplicateLabel =
                        String.format("Copy_%d_%s", copyIndex, label); //$NON-NLS-1$

                int idx = 1;
                while (modelLabels.contains(duplicateLabel)) {
                    duplicateLabel = String.format("Copy_%d_%s", idx++, label); //$NON-NLS-1$
                }

                Xpdl2ModelUtil.setOtherAttribute(pkg,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        duplicateLabel);
            }
        }

        private List<String> getAllPackageLabels(String projectName) {
            List<String> names = new ArrayList<String>();
            IndexerItemImpl criteria = new IndexerItemImpl();
            criteria.setType(ProcessResourceItemType.PROCESSPACKAGE.toString());
            criteria.set("internal_project", projectName); //$NON-NLS-1$
            Collection<IndexerItem> result =
                    XpdResourcesPlugin
                            .getDefault()
                            .getIndexerService()
                            .query(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                                    criteria);
            if (result != null) {
                for (IndexerItem item : result) {
                    names.add(item
                            .get(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME));
                }
            }

            return names;
        }

        @Override
        public Collection<?> getResult() {
            return result;
        }

        @Override
        public void redo() {
            if (prepare()) {
                execute();
            }
        }

        @Override
        public void undo() {
            if (duplicate != null) {
                // Delete the duplicate file
                try {
                    /*
                     * if the editor is open for this file then close it and
                     * then delete the file
                     */
                    IWorkbenchWindow window =
                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow();
                    if (window != null) {
                        IEditorPart editor =
                                window.getActivePage()
                                        .findEditor(new FileEditorInput(
                                                duplicate));
                        if (editor != null) {
                            window.getActivePage().closeEditor(editor, false);
                        }
                    }

                    duplicate.delete(true, null);
                } catch (CoreException e) {
                    Xpdl2ResourcesPlugin.getDefault().getLogger().error(e);
                    ErrorDialog
                            .openError(XpdResourcesPlugin.getStandardDisplay()
                                    .getActiveShell(),
                                    Messages.DuplicateAction_undoFailed_errorDlg_title,
                                    String.format(Messages.DuplicateAction_undoFailed_errorDlg_message,
                                            e.getLocalizedMessage()),
                                    e.getStatus());
                }
            }
        }

        private int getCopyIndex(IFile original) {
            IFile duplicate = null;
            IContainer parent = original.getParent();
            String name = original.getName();
            int idx = 0;
            while (duplicate == null || duplicate.exists()) {
                duplicate = parent.getFile(new Path(String.format("Copy_%d_%s", //$NON-NLS-1$
                        ++idx,
                        name)));
            }

            return idx;
        }

    }

    /**
     * Command to duplicate a Process.
     */
    private class DuplicateProcessCommand extends AbstractCommand {
        private final EObject toDuplicate;

        private CopyAction copyAction;

        private Object parent;

        private Collection<?> result;

        private final WorkingCopy wc;

        public DuplicateProcessCommand(WorkingCopy wc, EObject toDuplicate) {
            this.wc = wc;
            this.toDuplicate = toDuplicate;
        }

        @Override
        protected boolean prepare() {
            copyAction = new CopyAction();
            copyAction.selectionChanged(new StructuredSelection(toDuplicate));
            parent = getParent(toDuplicate);
            return parent != null && copyAction.isEnabled();
        }

        @Override
        public void execute() {
            result = null;
            copyAction.run();
            PasteAction pasteAction = new PasteAction();
            pasteAction.selectionChanged(new StructuredSelection(parent));
            pasteAction.run();
            result = pasteAction.getResult();
        }

        @Override
        public Collection<?> getResult() {
            return result;
        }

        @Override
        public void redo() {
            if (prepare()) {
                execute();
            }
        }

        @Override
        public void undo() {
            if (result != null && !result.isEmpty()) {
                Object next = result.iterator().next();
                if (wc != null) {
                    IEditorPart editor = null;
                    if (next instanceof Process) {
                        editor =
                                getWindow().getActivePage()
                                        .findEditor(new ProcessEditorInput(wc,
                                                (Process) next));
                    } else if (next instanceof ProcessInterface) {
                        editor =
                                getWindow()
                                        .getActivePage()
                                        .findEditor(new ProcessInterfaceEditorInput(
                                                wc, (ProcessInterface) next));
                    }

                    if (editor != null) {
                        getWindow().getActivePage().closeEditor(editor, false);
                    }
                }
                Command cmd = DeleteCommand.create(getEditingDomain(), next);

                if (cmd.canExecute()) {
                    cmd.execute();
                }
            }
        }

        /**
         * Get the parent of the given object.
         * 
         * @param element
         * @return if element is ProcessInterface then this will return its
         *         Package as the parent, otherwise the EObject's container will
         *         be returned.
         */
        private Object getParent(EObject element) {
            if (element instanceof ProcessInterface) {
                return getPackage(element);
            }
            return element.eContainer();
        }

        /**
         * Get the containing Package of the given object.
         * 
         * @param eo
         * @return
         */
        private Package getPackage(EObject eo) {
            if (eo != null) {
                if (eo.eContainer() instanceof Package) {
                    return (Package) eo.eContainer();
                }
                return getPackage(eo.eContainer());
            }
            return null;
        }
    }
}
