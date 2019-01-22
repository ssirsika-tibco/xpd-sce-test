/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.SelectionStatusDialog;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableError;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorFolder;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.IBpmnCatchableErrorTreeItem;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ResultError;

/**
 * Dialog for picking error code thrown by the activity that the given error
 * event is attached to.
 * 
 * @author aallway
 * @since 3.2
 */
public class CatchErrorCodePickerDialog extends SelectionStatusDialog {

    private TreeViewer treeViewer = null;

    private Button btnSelectByNameOnly = null;

    private Activity errorEvent;

    private Collection<IBpmnCatchableErrorTreeItem> catchableErrorTree =
            Collections.emptyList();

    private boolean selectUnspecificErrorByName = false;

    private Label messageLabel = null;

    private boolean showingInitialClosestMatchMessage = false;

    /**
     * 
     * @param parentShell
     * @param errorEvent
     * @param revealClosestIfCurrentNotFound
     *            true means that if exact match for current error caught by
     *            input catchErrorEvent is not found in the errors that it might
     *            potentially catch then the initial selection will be set to
     *            the first event that matches on error code label alone. This
     *            is only relevant if selectByNameOnly=false;
     */
    public CatchErrorCodePickerDialog(Shell parentShell, Activity errorEvent) {
        super(parentShell);

        this.errorEvent = errorEvent;

        setImage(Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.IMG_ERRORCODE_PICKER));

        setTitle(Messages.CatchErrorCodePickerDialog_SelectErroroCatch_title);
        setStatusLineAboveButtons(true);
        return;
    }

    @Override
    protected void computeResult() {
        setResult(((IStructuredSelection) treeViewer.getSelection()).toList());
    }

    public BpmnCatchableError getCatchableErrorResult() {
        Object[] res = super.getResult();
        if (res != null && res.length > 0
                && res[0] instanceof BpmnCatchableError) {
            return (BpmnCatchableError) res[0];
        }
        return null;
    }

    /**
     * @return Whether the user chose to select unspecific error.
     */
    public boolean isSelectUnspecificErrorByName() {
        return selectUnspecificErrorByName;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    protected Control createDialogArea(Composite parent) {

        //
        // On initialisation we will set to select unspecific error (by name
        // only) ONLY if the error event catches an unspecific error.
        // Otherwise (if currently catch all or specific) we will default to
        // select error from specific thrower.
        //
        ErrorCatchType currentCatchType =
                BpmnCatchableErrorUtil.getCatchType(errorEvent);
        if (ErrorCatchType.CATCH_BY_NAME.equals(currentCatchType)) {
            selectUnspecificErrorByName = true;
        } else {
            selectUnspecificErrorByName = false;
        }

        //
        // Set the initial message text according to select by name
        //
        updateMessageText();

        //
        // Create the controls.
        //
        Composite composite = (Composite) super.createDialogArea(parent);

        //
        // Create the message area.
        //
        messageLabel = createMessageArea(composite);
        messageLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        //
        // Create the select by name (else specific thrown error
        //
        btnSelectByNameOnly = new Button(composite, SWT.CHECK);
        btnSelectByNameOnly
                .setText(Messages.CatchErrorCodePickerDialog_SelectUnspecificErrorDescription_message2);

        btnSelectByNameOnly.setSelection(selectUnspecificErrorByName);

        btnSelectByNameOnly
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        btnSelectByNameOnly.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }

            @Override
            public void widgetSelected(SelectionEvent e) {
                selectUnspecificErrorByName =
                        btnSelectByNameOnly.getSelection();
                updateMessageText();
                updateTreeViewer();
                updateOKEnabled();
                return;
            }
        });

        //
        // Create the error viewer.
        //
        treeViewer = new TreeViewer(composite, SWT.BORDER | SWT.SINGLE);

        treeViewer.setLabelProvider(new ErrorCodePickerLabelProvider());
        treeViewer.setContentProvider(new ErrorCodePickerContentProvider());
        //
        // Setup the treeviewer content.
        //
        updateTreeViewer();

        setupInitialSelection(currentCatchType);

        GridData data = new GridData(GridData.FILL_BOTH);
        data.widthHint = convertWidthInCharsToPixels(60);
        data.heightHint = convertHeightInCharsToPixels(18);

        Tree treeWidget = treeViewer.getTree();
        treeWidget.setLayoutData(data);
        treeWidget.setFont(parent.getFont());

        treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                showingInitialClosestMatchMessage = false;
                updateOKEnabled();
            }

        });

        treeViewer.addDoubleClickListener(new IDoubleClickListener() {

            @Override
            public void doubleClick(DoubleClickEvent event) {
                if (updateOKEnabled()) {
                    okPressed();
                }
            }
        });
        return composite;
    }

    /**
     * @param currentCatchType
     */
    private void setupInitialSelection(ErrorCatchType currentCatchType) {
        //
        // If we can, select the specific catchable error that event is
        // currently set up for.
        //
        BpmnCatchableError currError = getCurrentCaughtError(currentCatchType);
        if (currError != null) {
            treeViewer.setSelection(new StructuredSelection(currError));
            treeViewer.reveal(currError);

        } else if (ErrorCatchType.CATCH_SPECIFIC.equals(currentCatchType)
                && !selectUnspecificErrorByName) {
            //
            // If we did not find a specific error currently caught by event,
            // then reveal the best match if the error event is currently setup
            // for a specific event.
            //
            BpmnCatchableError revealError = getNearestMatchingError();
            if (revealError != null) {
                treeViewer.setSelection(new StructuredSelection(revealError));
                treeViewer.reveal(revealError);

                showingInitialClosestMatchMessage = true;
                updateStatus(new Status(
                        IStatus.ERROR,
                        Xpdl2ResourcesPlugin.PLUGIN_ID,
                        Messages.CatchErrorCodePickerDialog_ClosesetMatch_message));
            } else {
                treeViewer.expandAll();
            }
        } else {
            treeViewer.expandAll();
        }
    }

    /**
     * @return Return the nearest matching error to the specific one currently
     *         caught by the event.
     */
    private BpmnCatchableError getNearestMatchingError() {
        BpmnCatchableError catchableError = null;

        if (errorEvent.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
            ResultError resultError =
                    (ResultError) errorEvent.getEvent()
                            .getEventTriggerTypeNode();
            String errorCode = resultError.getErrorCode();
            if (errorCode != null && errorCode.length() > 0) {

                ErrorThrowerInfo errorThrowerId =
                        BpmnCatchableErrorUtil
                                .getExtendedErrorThrowerInfo(errorEvent);

                catchableError =
                        BpmnCatchableErrorUtil
                                .locateCatchableErrorClosestMatch(catchableErrorTree,
                                        errorCode,
                                        errorThrowerId.getThrowerId(),
                                        errorThrowerId.getThrowerContainerId());
            }
        }

        return catchableError;
    }

    /**
     * Update the dialog message text.
     */
    private void updateMessageText() {
        if (selectUnspecificErrorByName) {
            setMessage(Messages.CatchErrorCodePickerDialog_SelectUnspecificError_message);
        } else {
            setMessage(Messages.CatchErrorCodePickerDialog_SelectSpecificError_message);
        }

        if (messageLabel != null) {
            messageLabel.setText(getMessage());
        }
        return;
    }

    /**
     * Set/reset the treeviewer.
     */
    private void updateTreeViewer() {
        Activity attachedToTask =
                BpmnCatchableErrorUtil.getAttachedToTask(errorEvent);
        if (attachedToTask != null) {
            if (!selectUnspecificErrorByName) {
                catchableErrorTree =
                        BpmnCatchableErrorUtil
                                .getCatchableErrors(attachedToTask);
            } else {
                catchableErrorTree =
                        BpmnCatchableErrorUtil
                                .getCatchableErrorsFlatList(attachedToTask);
            }

        }

        BpmnCatchableError curSelError = null;
        if (treeViewer.getSelection() instanceof StructuredSelection) {
            StructuredSelection sel =
                    (StructuredSelection) treeViewer.getSelection();
            if (!sel.isEmpty()
                    && sel.getFirstElement() instanceof BpmnCatchableError) {
                curSelError = (BpmnCatchableError) sel.getFirstElement();
            }
        }

        treeViewer.setInput(this);

        if (curSelError != null) {
            BpmnCatchableError newSel =
                    BpmnCatchableErrorUtil
                            .locateCatchableErrorExact(catchableErrorTree,
                                    curSelError.getErrorCode(),
                                    curSelError.getErrorThrowerId(),
                                    curSelError.getErrorThrowerContainerId());
            if (newSel != null) {
                treeViewer.setSelection(new StructuredSelection(newSel));
            }
        }
        return;
    }

    @Override
    protected Control createButtonBar(Composite parent) {
        Control ctrl = super.createButtonBar(parent);

        updateOKEnabled();

        return ctrl;
    }

    /**
     * Update the enablement state of the OK button depending on the user's
     * selection.
     */
    private boolean updateOKEnabled() {
        boolean isOk = false;

        Status status = null;
        if (!showingInitialClosestMatchMessage) {
            if (treeViewer.getSelection() instanceof StructuredSelection) {
                StructuredSelection sel =
                        (StructuredSelection) treeViewer.getSelection();
                if (sel.size() == 1
                        && sel.getFirstElement() instanceof BpmnCatchableError) {
                    status =
                            new Status(
                                    IStatus.INFO,
                                    Xpdl2ResourcesPlugin.PLUGIN_ID,
                                    Messages.CatchErrorCodePickerDialog_ThrownBy_label
                                            + ": " //$NON-NLS-1$
                                            + BpmnCatchableErrorUtil
                                                    .getPathToErrorDescription((BpmnCatchableError) sel
                                                            .getFirstElement()));
                    isOk = true;
                }
            }

            if (status == null) {
                status =
                        new Status(IStatus.ERROR,
                                Xpdl2ResourcesPlugin.PLUGIN_ID,
                                Messages.YouMustSelectError_message0);
            }

            updateStatus(status);
        }
        return isOk;
    }

    /**
     * In the absence of a current selection when we are first displayed, we
     * will find and reveal the tree down to the first available error (without
     * selecting it).
     * 
     * This is so that the user can see the first thing without expanding
     * everything.
     * 
     * @param errorList
     * @return The frst catchable error in the catchable error tree.
     */
    private BpmnCatchableError getFirstAvailableError(
            Collection<IBpmnCatchableErrorTreeItem> errorList) {
        if (errorList != null) {
            for (IBpmnCatchableErrorTreeItem item : errorList) {
                if ((item instanceof BpmnCatchableError)) {
                    return (BpmnCatchableError) item;
                }

                BpmnCatchableError err =
                        getFirstAvailableError(item.getChildren());
                if (err != null) {
                    return err;
                }
            }
        }
        return null;
    }

    /**
     * Find the BpmnCatchableError in the tree of catchable errors that can be
     * thrown by the task the event is attached to (or any of it's sub-tasks).
     * 
     * @return The currently set catchable error or null if not found / none
     *         set.
     */
    private BpmnCatchableError getCurrentCaughtError(
            ErrorCatchType currentCatchType) {
        BpmnCatchableError catchableError = null;

        if (ErrorCatchType.CATCH_SPECIFIC.equals(currentCatchType)) {
            if (errorEvent.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
                ResultError resultError =
                        (ResultError) errorEvent.getEvent()
                                .getEventTriggerTypeNode();
                String errorCode = resultError.getErrorCode();
                if (errorCode != null && errorCode.length() > 0) {

                    ErrorThrowerInfo errorThrowerId =
                            BpmnCatchableErrorUtil
                                    .getExtendedErrorThrowerInfo(errorEvent);

                    catchableError =
                            BpmnCatchableErrorUtil
                                    .locateCatchableErrorExact(catchableErrorTree,
                                            errorCode,
                                            errorThrowerId.getThrowerId(),
                                            errorThrowerId
                                                    .getThrowerContainerId());
                }
            }

        } else if (ErrorCatchType.CATCH_BY_NAME.equals(currentCatchType)
                && selectUnspecificErrorByName) {
            if (errorEvent.getEvent() instanceof IntermediateEvent
                    && errorEvent.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
                ResultError resultError =
                        (ResultError) errorEvent.getEvent()
                                .getEventTriggerTypeNode();
                String errorCode = resultError.getErrorCode();
                if (errorCode != null && errorCode.length() > 0) {
                    // Look for the first error by name alone in (what should
                    // be)
                    // the flat list of errors.
                    for (IBpmnCatchableErrorTreeItem item : catchableErrorTree) {
                        if (item instanceof BpmnCatchableError) {
                            if (errorCode.equals(((BpmnCatchableError) item)
                                    .getErrorCode())) {
                                catchableError = (BpmnCatchableError) item;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return catchableError;
    }

    /**
     * Catchable Error Tree viewer label provider
     */
    private class ErrorCodePickerLabelProvider extends LabelProvider {
        @Override
        public String getText(Object element) {
            return super.getText(element);
        }

        @Override
        public Image getImage(Object element) {
            if (element instanceof IBpmnCatchableErrorTreeItem) {
                return ((IBpmnCatchableErrorTreeItem) element).getImage();
            }
            return super.getImage(element);
        }
    }

    /**
     * Catchable Error Tree viewer content provider
     */
    private class ErrorCodePickerContentProvider implements
            ITreeContentProvider {

        @Override
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof BpmnCatchableErrorFolder) {
                //
                // FIlter out folders that have no actual error descendents
                // (just empty sub-folders).
                List<IBpmnCatchableErrorTreeItem> filtered =
                        new ArrayList<IBpmnCatchableErrorTreeItem>();

                for (IBpmnCatchableErrorTreeItem item : ((BpmnCatchableErrorFolder) parentElement)
                        .getChildren()) {
                    if (item instanceof BpmnCatchableErrorFolder) {
                        if (hasErrorChildren((BpmnCatchableErrorFolder) item)) {
                            filtered.add(item);
                        }
                    } else {
                        filtered.add(item);
                    }
                }

                return filtered.toArray();
            }

            return new Object[0];
        }

        @Override
        public Object getParent(Object element) {
            if (element instanceof IBpmnCatchableErrorTreeItem) {
                return ((IBpmnCatchableErrorTreeItem) element)
                        .getParentFolder();
            }
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element instanceof BpmnCatchableErrorFolder) {
                return true;
            }

            return false;
        }

        /**
         * @param folder
         * @return true if the folder contains any descendents that have error
         *         items (rather than just empty folders).
         */
        private boolean hasErrorChildren(BpmnCatchableErrorFolder folder) {
            for (IBpmnCatchableErrorTreeItem item : folder.getChildren()) {
                if ((item instanceof BpmnCatchableErrorFolder)) {
                    if (hasErrorChildren((BpmnCatchableErrorFolder) item)) {
                        return true;
                    }
                } else {
                    // Not a folder, must be an actual error.
                    return true;
                }
            }

            return false;
        }

        @Override
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof CatchErrorCodePickerDialog) {

                //
                // FIlter out folders that have no actual error descendents
                // (just empty sub-folders).
                List<IBpmnCatchableErrorTreeItem> filtered =
                        new ArrayList<IBpmnCatchableErrorTreeItem>();

                for (IBpmnCatchableErrorTreeItem item : ((CatchErrorCodePickerDialog) inputElement).catchableErrorTree) {
                    if (item instanceof BpmnCatchableErrorFolder) {
                        if (hasErrorChildren((BpmnCatchableErrorFolder) item)) {
                            filtered.add(item);
                        }
                    } else {
                        filtered.add(item);
                    }
                }

                return filtered.toArray();

            }
            return new Object[0];
        }

        @Override
        public void dispose() {
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
    }

}
