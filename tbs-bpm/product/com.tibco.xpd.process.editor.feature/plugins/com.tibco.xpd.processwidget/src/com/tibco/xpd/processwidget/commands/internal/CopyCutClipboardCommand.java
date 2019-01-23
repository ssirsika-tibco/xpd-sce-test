/**
 * CopyCutClipboardCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2007
 */
package com.tibco.xpd.processwidget.commands.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.action.ProcessClipboardUtils;
import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.figures.ProcessFigure.DiagramViewType;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.PoolEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;

/**
 * CopyCutClipboardCommand
 * <p>
 * Copy or Cut current selection to clipboard command. Ensures that all
 * supported clipboard formats are placed on the clipboard.
 * </p>
 */
public class CopyCutClipboardCommand extends AbstractCommand {
    private EditingDomain editingDomain = null;

    private ProcessWidgetImpl processWidget = null;

    private boolean doCut = false;

    private Command cutCommand = null;

    private Collection clipboardObjects;

    private ProcessDiagramAdapter diagramAdapter;

    private List selection;

    public CopyCutClipboardCommand(List selection,
            ProcessWidgetImpl processWidget, boolean doCut) {
        this.processWidget = processWidget;

        editingDomain = processWidget.getEditingDomain();

        this.doCut = doCut;

        diagramAdapter =
                (ProcessDiagramAdapter) processWidget
                        .getWidgetAdapter(processWidget.getInput());

        this.selection = selection;

        if (doCut) {
            setLabel(Messages.CopyCutClipboardCommand_CutToClipboard_menu);
        } else {
            setLabel(Messages.CopyCutClipboardCommand_CopyToClipboard_menu);

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    @Override
    public void execute() {
        Collection modelObjects = getModelObjects();
        // Null check not required
        if (modelObjects.size() > 0) {
            FragmentLocalSelectionTransfer frag =
                    FragmentLocalSelectionTransfer.getTransfer();
            clipboardObjects =
                    diagramAdapter.getCopyObjects(editingDomain,
                            modelObjects,
                            true);
            if (clipboardObjects != null) {
                processWidget.setupProcessFragmentTransfer(frag, selection);
                ProcessClipboardUtils.setClipboard(clipboardObjects,
                        (EObject) diagramAdapter.getProcess(),
                        processWidget);
            } else {
                ProcessWidgetPlugin
                        .getDefault()
                        .getLogger()
                        .error("Problem creating clipboard copy objects; selection may be invalid for copy but not detected by diagramAdapter.validateCopyObjects()."); //$NON-NLS-1$
            }
            if (doCut && cutCommand != null) {
                cutCommand.execute();
            }
        }
        return;

    }

    /**
     * @return
     * 
     */
    private Collection getModelObjects() {
        if (isValidCopyPartsForDiagramType()) {
            Collection modelObjects =
                    EditPartUtil.getModelObjectsFromSelection(selection);

            if (modelObjects != null) {
                return modelObjects;
            }
        }
        return Collections.EMPTY_LIST;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
     */
    @Override
    protected boolean prepare() {
        Collection modelObjects = getModelObjects();
        if (isValidCopyPartsForDiagramType()
                && diagramAdapter.validateCopyObjects(modelObjects)) {
            if (doCut) {
                // If this is a cut then get the delete object
                // for
                // cut command.
                cutCommand =
                        diagramAdapter.getDeleteCutObjects(editingDomain,
                                modelObjects);
                return (cutCommand != null && cutCommand.canExecute());
            }
            // for copy validate objects
            return true;
        }
        return false;

    }

    /**
     * @param ep
     * @return
     */
    private boolean isValidCopyPartsForDiagramType() {
        boolean validEditParts = true;
        if (!selection.isEmpty()) {
            if (selection.get(0) instanceof ModelAdapterEditPart) {
                // Get the stuff we can get from any old edit part
                // that's still there.
                ModelAdapterEditPart ep =
                        (ModelAdapterEditPart) selection.get(0);

                if (ep instanceof BaseGraphicalEditPart) {
                    if (DiagramViewType.TASK_LIBRARY_ALTERNATE
                            .equals(((BaseGraphicalEditPart) ep)
                                    .getDiagramViewType())) {
                        // Disallow copy / paste of pools in Task library
                        // alternate
                        // view (which shows only lanes).
                        for (Object o : selection) {
                            if (o instanceof PoolEditPart
                                    || o instanceof ProcessEditPart) {
                                validEditParts = false;
                                break;
                            }
                        }
                    } else if (DiagramViewType.NO_POOLS
                            .equals(((BaseGraphicalEditPart) ep)
                                    .getDiagramViewType())) {
                        // Disallow copy / paste of pools in pageflow
                        // view (which shows no pool or lanes).
                        for (Object o : selection) {
                            if (o instanceof LaneEditPart
                                    || o instanceof PoolEditPart
                                    || o instanceof ProcessEditPart) {
                                validEditParts = false;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return validEditParts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#undo()
     */
    @Override
    public void undo() {
        if (doCut && cutCommand != null) {
            cutCommand.undo();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#redo()
     */
    @Override
    public void redo() {
        if (doCut && cutCommand != null) {
            cutCommand.redo();
        }
    }

}
