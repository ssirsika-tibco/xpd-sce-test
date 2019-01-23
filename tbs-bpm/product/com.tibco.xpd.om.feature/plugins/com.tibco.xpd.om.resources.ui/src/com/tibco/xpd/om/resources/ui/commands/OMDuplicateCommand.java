/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.internal.indexing.OMResourceIndexProvider;
import com.tibco.xpd.om.resources.ui.internal.navigator.actions.OMCopyAction;
import com.tibco.xpd.om.resources.ui.internal.navigator.actions.OMPasteAction;
import com.tibco.xpd.om.resources.ui.types.OMTypesFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Command to duplicate Organization Model (duplicate the OM file and rename the
 * OrgModel) or duplicate an Organization.
 * <p>
 * <strong>Note: This is for the RCP application.</strong>
 * </p>
 * 
 * @author njpatel
 */
public class OMDuplicateCommand extends AbstractCommand {

    private final TransactionalEditingDomain ed;

    private final EObject toDuplicate;

    private AbstractCommand command;

    public OMDuplicateCommand(String label, TransactionalEditingDomain ed,
            EObject toDuplicate) {
        super(label);
        this.ed = ed;
        this.toDuplicate = toDuplicate;
    }

    @Override
    protected boolean prepare() {
        if (toDuplicate instanceof OrgModel) {
            IFile file = WorkingCopyUtil.getFile(toDuplicate);
            if (file != null && file.exists()) {
                command = new DuplicateOrgModelCommand(file);
                return ((DuplicateOrgModelCommand) command).prepare();
            }
        } else if (toDuplicate instanceof Organization) {
            command = new DuplicateOrganizationCommand(ed, toDuplicate);
            return ((DuplicateOrganizationCommand) command).prepare();
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

    private class DuplicateOrgModelCommand extends AbstractCommand {

        private final IFile file;

        private IFile duplicate;

        private Collection<?> result;

        public DuplicateOrgModelCommand(IFile file) {
            this.file = file;
        }

        @Override
        protected boolean prepare() {
            return file != null
                    && file.exists()
                    && OMResourcesActivator.OM_FILE_EXTENSION.equals(file
                            .getFileExtension());
        }

        @Override
        public void execute() {
            if (file != null) {
                int idx = getCopyIndex(file);
                duplicate =
                        file.getParent().getFile(new Path(String
                                .format("Copy_%d_%s", idx, file.getName()))); //$NON-NLS-1$
                try {
                    file.copy(duplicate.getFullPath(), true, null);
                    updateOrgModelLabel(duplicate, idx);
                    result = Collections.singleton(duplicate);
                } catch (CoreException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        private void updateOrgModelLabel(IFile duplicate, int copyIndex) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(duplicate);
            if (wc != null && wc.getRootElement() instanceof OrgModel) {
                OrgModel model = (OrgModel) wc.getRootElement();
                List<String> modelLabels =
                        getAllOrgModelLabels(duplicate.getProject().getName());
                String label = model.getDisplayName();
                String duplicateLabel =
                        String.format("Copy_%d_%s", copyIndex, label); //$NON-NLS-1$

                int idx = 1;
                while (modelLabels.contains(duplicateLabel)) {
                    duplicateLabel = String.format("Copy_%d_%s", idx++, label); //$NON-NLS-1$
                }

                model.setDisplayName(duplicateLabel);
            }
        }

        @SuppressWarnings("deprecation")
        private List<String> getAllOrgModelLabels(String projectName) {
            List<String> names = new ArrayList<String>();
            IndexerItemImpl criteria = new IndexerItemImpl();
            criteria.setType(OMTypesFactory.TYPE_BASE_ORG_MODEL.getTypeId());
            criteria.set("internal_project", projectName); //$NON-NLS-1$
            Collection<IndexerItem> result =
                    XpdResourcesPlugin
                            .getDefault()
                            .getIndexerService()
                            .query(OMResourceIndexProvider.INDEXER_ID, criteria);
            if (result != null) {
                for (IndexerItem item : result) {
                    names.add(item.get(OMResourceIndexProvider.DISPLAY_NAME));
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
                    OMResourcesUIActivator.getDefault().getLogger().error(e);
                    ErrorDialog
                            .openError(XpdResourcesPlugin.getStandardDisplay()
                                    .getActiveShell(),
                                    Messages.OMDuplicateCommand_undoOrgModelFailed_errorDlg_title,
                                    String
                                            .format(Messages.OMDuplicateCommand_undoOrgModelFailed_errorDlg_message,
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

    private class DuplicateOrganizationCommand extends AbstractCommand {

        private TransactionalEditingDomain ted;

        private EObject toDuplicate;

        private Object parent;

        private Command copyCommand;

        private Collection<?> result;

        public DuplicateOrganizationCommand(TransactionalEditingDomain ted,
                EObject toDuplicate) {
            this.ted = ted;
            this.toDuplicate = toDuplicate;
        }

        @Override
        protected boolean prepare() {
            if (ted != null && toDuplicate != null) {
                parent = getParent(toDuplicate);
                if (parent != null) {
                    OMCopyAction copyAction = new OMCopyAction(ted);
                    copyCommand =
                            copyAction.createCommand(Collections
                                    .singleton(toDuplicate));
                    return copyCommand.canExecute();
                }
            }

            return false;
        }

        @Override
        public void execute() {
            result = null;
            copyCommand.execute();
            OMPasteAction pasteAction = new OMPasteAction(ted);
            Command cmd =
                    pasteAction.createCommand(Collections.singleton(parent));
            if (cmd.canExecute()) {
                cmd.execute();
                result = cmd.getResult();
            }
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
                if (next instanceof Organization) {
                    Organization org = (Organization) next;

                    ICommand editCommand =
                            ElementTypeRegistry.getInstance()
                                    .getElementType(org)
                                    .getEditCommand(new DestroyElementRequest(
                                            org, false));
                    if (editCommand != null) {
                        try {
                            editCommand.execute(null, null);
                        } catch (ExecutionException e) {
                            OMResourcesUIActivator.getDefault().getLogger()
                                    .error(e);
                            ErrorDialog
                                    .openError(XpdResourcesPlugin
                                            .getStandardDisplay()
                                            .getActiveShell(),
                                            Messages.OMDuplicateCommand_undoOrgFailed_errorDlg_title,
                                            String
                                                    .format(Messages.OMDuplicateCommand_undoOrgFailed_errorDlg_message,
                                                            e
                                                                    .getLocalizedMessage()),
                                            null);
                        }
                    }
                }
            }
        }

        /**
         * Get the parent of the given element.
         * 
         * @param element
         * @return
         */
        private Object getParent(EObject element) {
            AdapterFactoryContentProvider provider =
                    new AdapterFactoryContentProvider(XpdResourcesPlugin
                            .getDefault().getAdapterFactory());
            if (provider != null) {
                return provider.getParent(element);
            }

            return null;
        }
    }

}
