/**
 * CutCopyPasteActionHandler.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2007
 */
package com.tibco.xpd.processwidget.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.gef.Disposable;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.xpd.analyst.resources.xpdl2.action.ProcessClipboardUtils;
import com.tibco.xpd.analyst.resources.xpdl2.action.ProcessClipboardUtils.ProcessClipboardDataList;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.commands.internal.CopyCutClipboardCommand;
import com.tibco.xpd.processwidget.commands.internal.XPDPasteCommand;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;

/**
 * Handler for Cut/Copy/Paste actions
 * 
 * 
 * @author WojciechZ
 */
public class CutCopyPasteActionHandler extends Action implements Disposable,
        ISelectionChangedListener {

    public static final int CUT = 0;

    public static final int COPY = 1;

    public static final int PASTE = 2;

    private final int type;

    private final ISelectionProvider selectionProvider;

    private Command command;

    private EditingDomain editingDomain;

    private ProcessWidgetImpl widget;

    private List selectedParentEditParts = Collections.EMPTY_LIST;

    /**
     * @see org.eclipse.jface.action.Action#getId()
     */
    @Override
    public String getId() {
        return type == CUT ? ActionFactory.CUT.getId()
                : type == COPY ? ActionFactory.COPY.getId()
                        : ActionFactory.PASTE.getId();
    }

    /**
     * @param stack
     * @param type
     */
    public CutCopyPasteActionHandler(ProcessWidgetImpl widget, int type) {
        this.selectionProvider = widget;
        this.widget = widget;
        if (type != CUT && type != COPY && type != PASTE) {
            throw new IllegalArgumentException();
        }
        this.type = type;
        selectionProvider.addSelectionChangedListener(this);
        setEnabled(false);
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {

        // For Paste command we MUST reget the command when we actually run.
        // This is because Normally the command is built when an object is
        // selected and this stores the command according to the values in
        // clipboard AT THAT TIME.
        //
        // But the clipboard can change without the
        // selection changing (switch to different app, press Ctrl+C on
        // currently selected item) this would mean that the paste will
        // paste the old contents of the clipboard).
        // Worst that can happen if we rebuild command here is that the menu
        // item won't be disabled (when content is invalid) until
        if (type == PASTE) {
            setUpPasteCommand();

            if (command == null || !command.canExecute()) {
                setEnabled(false);
            } else {
                setEnabled(true);
            }
        }

        if (command != null && editingDomain != null) {

            // Copy to clipboard should NOT go thru the undo command stack!
            if (type == COPY) {
                command.execute();

            } else {
                editingDomain.getCommandStack().execute(command);
            }

            // For paste command, selected the edit parts for pasted objects.
            if (type == PASTE) {
                if (command instanceof XPDPasteCommand) {
                    // Select the model objects.
                    widget.setSelFromPastedObjects((XPDPasteCommand) command);
                }
            }

            // create new command, if the user want to cut/copy/paste this again
            if (type != CUT) {
                setUpCommand();
            } else {
                setEnabled(false);
                command = null;
            }

        }
    }

    @Override
    public void dispose() {
        selectionProvider.removeSelectionChangedListener(this);
    }

    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        // Reduce the list to the highest level edit parts selected
        // (i.e. if sub-task is selected inside selected embedded sub-proc
        // then it is removed from the list OR if activity
        // selected inside selected lane then it is removed from list).
        selectedParentEditParts = Collections.EMPTY_LIST;

        ISelection selection = event.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;

            List editParts = new ArrayList();

            if (sel.size() > 0) {
                for (Iterator iter = sel.iterator(); iter.hasNext();) {
                    Object obj = iter.next();
                    if (obj instanceof ModelAdapterEditPart) {
                        editParts.add(obj);
                    }
                }

                // Remove children of selected parents from list.
                selectedParentEditParts =
                        ToolUtilities.getSelectionWithoutDependants(editParts);
            }

        }

        setUpCommand();
    }

    private void setUpCommand() {
        if (selectedParentEditParts.isEmpty()) {
            command = null;
            setEnabled(false);
            return;
        }

        EObject firstEO =
                (EObject) ((ModelAdapterEditPart) selectedParentEditParts
                        .get(0)).getModel();
        Resource res = firstEO.eResource();

        if (res == null) {
            // can be refreshed during complex commands
            command = null;
            setEnabled(false);
            return;
        }

        IEditingDomainProvider prov =
                (IEditingDomainProvider) EcoreUtil.getExistingAdapter(res,
                        IEditingDomainProvider.class);
        if (prov == null) {
            command = null;
            setEnabled(false);
            return;
        }
        editingDomain = prov.getEditingDomain();

        EObject eo = widget.getInput();
        ProcessDiagramAdapter diagAdapter =
                (ProcessDiagramAdapter) widget.getWidgetAdapter(eo);
        switch (type) {
        case PASTE:
            // Have to always set to enabled because clipboard may not contain
            // valid data when object is selected BUT user can then do ctrl+c
            // and change clipboard data, after this ctrl+v should work still.
            setEnabled(true);
            break;

        case COPY:
            command =
                    new CopyCutClipboardCommand(selectedParentEditParts,
                            widget, false);
            setEnabled(command.canExecute());
            break;

        case CUT:
            command =
                    new CopyCutClipboardCommand(selectedParentEditParts,
                            widget, true);
            setEnabled(command.canExecute());
            break;

        }

    }

    /**
     * Set up the paste command
     */
    private void setUpPasteCommand() {
        command = null;

        EObject firstEO =
                (EObject) ((ModelAdapterEditPart) selectedParentEditParts
                        .get(0)).getModel();
        Resource res = firstEO.eResource();

        if (res == null) {
            // can be refreshed during complex commands
            return;
        }

        EObject eo = widget.getInput();
        ProcessDiagramAdapter diagAdapter =
                (ProcessDiagramAdapter) widget.getWidgetAdapter(eo);

        if (selectedParentEditParts.size() < 1
                || !isClipboardContentSuitableForTargetContext(widget)) {
            setEnabled(false);
            return;
        }

        // Support select multi objects then Ctrl+C Ctrl+V - objects will
        // still be selected so we must select parent of first one as the
        // destination.
        if (selectedParentEditParts.size() > 1) {
            EditPart parent = (EditPart) selectedParentEditParts.get(0);

            selectedParentEditParts = new ArrayList(1);
            selectedParentEditParts.add(parent);
        }

        Collection clipboardObjects = ProcessClipboardUtils.getClipboard();

        EditPart pasteDest = (EditPart) selectedParentEditParts.get(0);

        Point location = null;
        ModelAdapterEditPart seqFlowTargetContainer = null;

        if (pasteDest instanceof SequenceFlowEditPart) {
            location =
                    (Point) pasteDest
                            .getViewer()
                            .getProperty(ProcessWidgetConstants.VIEWPROP_FLOWCONTAINER_LASTCLICKPOS);
            seqFlowTargetContainer =
                    (ModelAdapterEditPart) pasteDest
                            .getViewer()
                            .getProperty(ProcessWidgetConstants.VIEWPROP_SEQFLOW_LASTCLICKFLOWCONTAINER);
        }

        while (pasteDest instanceof ModelAdapterEditPart) {
            command =
                    new XPDPasteCommand(editingDomain, diagAdapter,
                            (ModelAdapterEditPart) pasteDest, location,
                            seqFlowTargetContainer, clipboardObjects);

            if (command != null && command.canExecute()) {
                // found a valid paste target
                break;
            }

            // Selected object is not a valid paste target, try its
            // parent.
            pasteDest = pasteDest.getParent();

            location = null;
            seqFlowTargetContainer = null;
        }

        return;
    }

    /**
     * Check whether the source context of clipboard is valid for the target
     * context
     * 
     * 
     * @return <code>false</code> if not <code>true</code> if it is.
     */
    public static boolean isClipboardContentSuitableForTargetContext(
            ProcessWidgetImpl targetWidget) {
        /*
         * Check that the source and target editors are BOTh decision flow
         * editors or BOTH not! i.e. they don't have any content in common so
         * don't allow it.
         */
        ProcessClipboardDataList sourceContext =
                ProcessClipboardUtils.getSourceContextForClipboardContent();

        if (sourceContext != null && targetWidget != null) {
            if (sourceContext.getViewerContext() instanceof ProcessWidgetImpl) {
                ProcessWidgetImpl sourceWidget =
                        (ProcessWidgetImpl) sourceContext.getViewerContext();

                boolean sourceIsDflow =
                        ProcessWidgetType.DECISION_FLOW.equals(sourceWidget
                                .getProcessWidgetType());
                boolean targetIsDflow =
                        ProcessWidgetType.DECISION_FLOW.equals(targetWidget
                                .getProcessWidgetType());

                if (sourceIsDflow != targetIsDflow) {
                    return false;
                }
            }
        }

        /*
         * If can't figure out source context then allow it to go thru (might be
         * a fragment etc).
         */
        return true;
    }
}
