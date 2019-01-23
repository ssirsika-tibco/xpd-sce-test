/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Command to duplicate a BOM model.
 * <p>
 * <strong>Note: This is for the RCP application.</strong>
 * </p>
 * 
 * @author njpatel
 * 
 */
public class BOMDuplicateCommand extends AbstractCommand {

    private final EObject toDuplicate;

    private WorkingCopy wc;

    private IFile duplicate;

    Collection<?> result;

    public BOMDuplicateCommand(String label, EObject toDuplicate) {
        super(label);
        this.toDuplicate = toDuplicate;
    }

    @Override
    protected boolean prepare() {
        if (toDuplicate instanceof Model) {
            // Duplicate the BOM file
            wc = WorkingCopyUtil.getWorkingCopyFor(toDuplicate);

            if (wc instanceof BOMWorkingCopy) {
                List<IResource> resources = wc.getEclipseResources();
                if (resources != null && !resources.isEmpty()) {
                    IResource resource = resources.get(0);
                    return resource instanceof IFile && resource.exists();
                }
            }
        }
        return false;
    }

    @Override
    public void execute() {
        if (wc != null) {
            IResource resource = wc.getEclipseResources().get(0);
            int idx = getCopyIndex((IFile) resource);
            duplicate =
                    resource.getParent().getFile(new Path(String
                            .format("Copy_%d_%s", idx, resource.getName()))); //$NON-NLS-1$
            try {
                resource.copy(duplicate.getFullPath(), true, null);
                updateBomModelLabel(duplicate, idx);
                result = Collections.singleton(duplicate);
            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
        if (duplicate != null) {
            // Delete the duplicate file
            try {
                /*
                 * if the editor is open for this file then close it and then
                 * delete the file
                 */
                IWorkbenchWindow window =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                if (window != null) {
                    IEditorPart editor =
                            window.getActivePage()
                                    .findEditor(new FileEditorInput(duplicate));
                    if (editor != null) {
                        window.getActivePage().closeEditor(editor, false);
                    }
                }

                duplicate.delete(true, null);

                // Rebuild the original file
                IFile file = WorkingCopyUtil.getFile(toDuplicate);
                if (file != null && file.exists()) {
                    file.touch(null);
                }
            } catch (CoreException e) {
                ErrorDialog.openError(XpdResourcesPlugin.getStandardDisplay()
                        .getActiveShell(), "Undo", String
                        .format("Undo failed: %s", e.getLocalizedMessage()), e
                        .getStatus());
            }
        }
    }

    /**
     * Get the copy index count to set as the file name.
     * 
     * @param original
     * @return
     */
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

    /**
     * Update the duplicate Model with a new (copy) name.
     * 
     * @param duplicate
     * @param copyIndex
     */
    private void updateBomModelLabel(IFile duplicate, int copyIndex) {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(duplicate);
        if (wc != null && wc.getRootElement() instanceof Model) {
            Model model = (Model) wc.getRootElement();
            List<String> modelLabels =
                    getModelLabels(duplicate.getProject().getName());
            String label = PrimitivesUtil.getDisplayLabel(model);
            String duplicateLabel =
                    String.format("Copy_%d_%s", copyIndex, label); //$NON-NLS-1$

            int idx = 1;
            while (modelLabels.contains(duplicateLabel)) {
                duplicateLabel = String.format("Copy_%d_%s", idx++, label); //$NON-NLS-1$
            }

            PrimitivesUtil.setDisplayLabel(model, duplicateLabel);
        }
    }

    /**
     * Get all the BOM model labels that have already been used in this project.
     * 
     * @param projectName
     * @return
     */
    private List<String> getModelLabels(String projectName) {
        List<String> names = new ArrayList<String>();

        for (IndexerItem item : BOMIndexerService.getInstance().getAllModels()) {
            if (projectName.equals(item.get("internal_project"))) { //$NON-NLS-1$
                names.add(item
                        .get(BOMIndexerService.INDEXER_ATTR_DISPLAY_LABEL));
            }
        }

        return names;
    }

}
