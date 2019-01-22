/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event.error;

import java.util.Collection;
import java.util.EventObject;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableError;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.util.RevealProcessDiagramEObject;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Section for Catch Error Intermediate Event
 * 
 * @author aallway
 * @since 3.2
 */
public class EventTriggerTypeCatchErrorSection extends
        AbstractFilteredTransactionalSection implements CommandStackListener {

    private CLabel errorCodeLabel;

    private CLabel errorCodeValue;

    private CLabel thrownByLabel;

    private CLabel thrownByValue;

    private Button btnBrowseForError;

    private Button btnSetCatchAll;

    private Button btnGoto;

    private boolean listeningToCmdStack;

    private Color labelForeground;

    private EObject errorThrower = null;

    private BpmnCatchableError errorOnLastRefresh = null;

    public EventTriggerTypeCatchErrorSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(4, false));

        errorCodeLabel =
                toolkit.createCLabel(root,
                        Messages.EventTriggerTypeCatchErrorSection_CatchErrorCode_label,
                        SWT.NONE);
        labelForeground = errorCodeLabel.getForeground();
        errorCodeLabel.setLayoutData(new GridData());

        errorCodeValue = toolkit.createCLabel(root, ""); //$NON-NLS-1$
        errorCodeValue.setData(FormToolkit.KEY_DRAW_BORDER,
                FormToolkit.TEXT_BORDER);
        errorCodeValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        btnBrowseForError =
                toolkit.createButton(root,
                        Messages.EventTriggerTypeCatchErrorSection_SelectSpecificErrorButton_label,
                        SWT.PUSH);
        btnBrowseForError
                .setToolTipText(Messages.EventTriggerTypeCatchErrorSection_SelectSpecificError_tooltip);
        btnBrowseForError.setLayoutData(new GridData());
        manageControl(btnBrowseForError);

        btnSetCatchAll =
                toolkit.createButton(root,
                        Messages.EventTriggerTypeCatchErrorSection_CatchAll_button,
                        SWT.PUSH);
        btnSetCatchAll
                .setToolTipText(Messages.EventTriggerTypeCatchErrorSection_SetToCatchAll_tooltip);
        btnSetCatchAll.setLayoutData(new GridData());
        manageControl(btnSetCatchAll);

        thrownByLabel =
                toolkit.createCLabel(root,
                        Messages.EventTriggerTypeCatchErrorSection_ThrowBy_label,
                        SWT.NONE);
        thrownByLabel.setLayoutData(new GridData());
        thrownByValue = toolkit.createCLabel(root, ""); //$NON-NLS-1$
        thrownByValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        thrownByValue.setData(FormToolkit.KEY_DRAW_BORDER,
                FormToolkit.TEXT_BORDER);

        btnGoto =
                toolkit.createButton(root, Messages.GotoButton_Label, SWT.PUSH);
        GridData gotoGd = new GridData();
        gotoGd.horizontalSpan = 2;
        btnGoto.setLayoutData(gotoGd);
        btnGoto.setEnabled(false);

        btnGoto.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (errorThrower != null) {
                    RevealProcessDiagramEObject
                            .revealEObject(EventTriggerTypeCatchErrorSection.this
                                    .getSite(),
                                    errorThrower,
                                    true);
                }
            }
        });

        return root;
    }

    @Override
    protected void doRefresh() {
        // If controls have been disposed then unregister adapter
        if (errorCodeValue.isDisposed()) {
            return;
        }

        Activity errorCatchEvent = getActivity();

        if (errorCatchEvent != null) {
            boolean needsLayout = false;
            boolean needsRefreshTabs = false;

            BpmnCatchableError catchableError =
                    BpmnCatchableErrorUtil
                            .locateCatchableErrorExact(errorCatchEvent);

            // Check if event has changed and if so refresh tasb (to pick up
            // latest mapping tabs etc.
            if (errorOnLastRefresh != null && catchableError != null) {
                if (!errorOnLastRefresh.equals(catchableError)) {
                    needsRefreshTabs = true;
                }
            } else if ((errorOnLastRefresh == null && catchableError != null)
                    || (errorOnLastRefresh != null && catchableError == null)) {
                needsRefreshTabs = true;
            }

            errorOnLastRefresh = catchableError;

            if (updateErrorCodeControls(catchableError)) {
                needsLayout = true;
            }

            if (checkValidity(getActivity(), catchableError)) {
                needsLayout = true;
            }

            if (needsRefreshTabs) {
                refreshTabs();
            }
            if (needsLayout) {
                forceLayout();
            }
        }

        return;
    }

    /**
     * @param reachableCatchableError
     * @return whether relayout is required.
     */
    private boolean updateErrorCodeControls(
            BpmnCatchableError reachableCatchableError) {
        boolean needsLayout = false;

        ErrorCatchType catchType =
                BpmnCatchableErrorUtil.getCatchType(getActivity());

        //
        // Update error code itself first.
        //
        if (ErrorCatchType.CATCH_ALL.equals(catchType)) {
            errorCodeValue
                    .setText("<" + Messages.EventTriggerTypeCatchErrorSection_CatchAll_label + ">"); //$NON-NLS-1$//$NON-NLS-2$
            thrownByValue
                    .setText("<" + Messages.EventTriggerTypeCatchErrorSection_AnyActivity_label + ">"); //$NON-NLS-1$//$NON-NLS-2$

        } else {
            // Catch Error By Name from unspecific activity or catch specific
            // error from specific activity.

            // Either way, they both have eror code names.
            String errorCodeValueText =
                    ((ResultError) getActivity().getEvent()
                            .getEventTriggerTypeNode()).getErrorCode();
            if (ErrorCatchType.CATCH_BY_NAME.equals(catchType)) {
                thrownByValue
                        .setText("<" + Messages.EventTriggerTypeCatchErrorSection_AnyActivity_label + ">"); //$NON-NLS-1$//$NON-NLS-2$

            } else if (ErrorCatchType.CATCH_SPECIFIC.equals(catchType)) {
                if (reachableCatchableError != null) {
                    errorCodeValueText = reachableCatchableError.getLabel();
                    thrownByValue
                            .setText(BpmnCatchableErrorUtil
                                    .getPathToErrorDescription(reachableCatchableError));
                } else {
                    thrownByValue
                            .setText("<" + Messages.EventTriggerTypeCatchErrorSection_UnableToLocateThrower_tooltip + ">"); //$NON-NLS-1$//$NON-NLS-2$
                }

            }
            errorCodeValue.setText(errorCodeValueText);

        }

        //
        // When Catching specific error - if still exists then we should be
        // able to get it's icon image. Otherwise we can unset the image in
        // label..
        Image catchableErrorImage = null;
        if (reachableCatchableError != null) {
            catchableErrorImage = reachableCatchableError.getImage();
        }

        if (errorCodeValue.getImage() != catchableErrorImage) {
            errorCodeValue.setImage(catchableErrorImage);
            needsLayout = true;
        }

        Image thrownByImage = null;
        if (reachableCatchableError != null
                && reachableCatchableError.getParentFolder() != null) {
            thrownByImage =
                    reachableCatchableError.getParentFolder().getImage();
        }

        if (thrownByValue.getImage() != thrownByImage) {
            thrownByValue.setImage(thrownByImage);
            needsLayout = true;
        }

        return needsLayout;
    }

    /**
     * @param errorCatchEvent
     * @param reachableCatchableError
     * @return void
     */
    private boolean checkValidity(Activity errorCatchEvent,
            BpmnCatchableError reachableCatchableError) {
        String invalidMessageString = null;
        boolean disableButtons = false;
        boolean needsLayout = false;

        if (!EventObjectUtil.isAttachedToTask(errorCatchEvent)) {
            disableButtons = true;

        } else {
            ErrorCatchType catchType =
                    BpmnCatchableErrorUtil.getCatchType(errorCatchEvent);
            if (ErrorCatchType.CATCH_SPECIFIC.equals(catchType)) {
                // If the event is set up to catch a specific error then the
                // error MUST exists somewheere in the tree of errors that are
                // reachable from the task we're attached to.
                if (reachableCatchableError == null) {
                    invalidMessageString =
                            Messages.EventTriggerTypeCatchErrorSection_CannotLocateError_tooltip;
                }
            }
        }

        btnBrowseForError.setEnabled(!disableButtons);
        btnSetCatchAll.setEnabled(!disableButtons);

        if (invalidMessageString != null) {
            errorCodeLabel.setToolTipText(invalidMessageString);
            errorCodeValue.setToolTipText(invalidMessageString);

            if (errorCodeLabel.getImage() == null) {
                errorCodeLabel.setImage(Xpdl2UiPlugin.getDefault()
                        .getImageRegistry().get(Xpdl2UiPlugin.IMG_ERROR));
                errorCodeLabel.setLayoutData(new GridData());
                thrownByLabel.setImage(Xpdl2UiPlugin.getDefault()
                        .getImageRegistry().get(Xpdl2UiPlugin.IMG_ERROR));
                thrownByLabel.setLayoutData(new GridData());
                needsLayout = true;
            }
        } else {
            errorCodeLabel.setToolTipText(""); //$NON-NLS-1$
            errorCodeValue.setToolTipText(""); //$NON-NLS-1$
            if (errorCodeLabel.getImage() != null) {
                errorCodeLabel.setImage(null);
                errorCodeLabel.setLayoutData(new GridData());
                thrownByLabel.setImage(null);
                thrownByLabel.setLayoutData(new GridData());
                needsLayout = true;
            }

        }

        boolean enableGoto = false;
        errorThrower = null;
        if (reachableCatchableError != null) {
            if (ErrorThrowerType.PROCESS_ACTIVITY
                    .equals(reachableCatchableError.getErrorThrowerType())) {

                if (reachableCatchableError.getErrorThrower() instanceof EObject) {
                    errorThrower =
                            (EObject) reachableCatchableError.getErrorThrower();
                    enableGoto = true;
                }
            }
        }
        btnGoto.setEnabled(enableGoto);

        return needsLayout;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;

        if (getActivity() != null) {
            if (obj == btnSetCatchAll) {
                cmd =
                        BpmnCatchableErrorUtil
                                .createSetCatchAllCommand(getEditingDomain(),
                                        getActivity());

            } else if (obj == btnBrowseForError) {
                cmd =
                        BpmnCatchableErrorUtil.selectErrorCode(this.getSite()
                                .getShell(), getEditingDomain(), getActivity());
            }
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        //
        // Because a multitude of things can effect the valid status of the
        // reference to error code (event being detached from task, something in
        // sub-process being removed AND because the user might have selected
        // the error catch event and pressed undo/redo which causes a change
        // that invalidate the reference THEN we will listen to the command
        // stack for ANY change (so as to correctly update the status of the
        // controls (error icon and such).
        Activity act = getActivity();
        if (act != null) {
            //
            // The following is a kludge to ensure that referenced WSDL's are
            // loaded into working copy's BEFORE the first refresh() is
            // performed (as refresh is performed in a read only transaction and
            // the load fails if done inside one).
            // It's crap but it ensures that if the error refers to an external
            // resource that it will be loaded into its working copy before
            // refresh.
            //
            BpmnCatchableErrorUtil.locateCatchableErrorExact(act);
            // DO NOT REMOVE ABOVE CODE UNLESS WSDL Load problem solve (when
            // error event cataches WSDL derived fault).

            if (!listeningToCmdStack) {
                getEditingDomain().getCommandStack()
                        .addCommandStackListener(this);
                listeningToCmdStack = true;
            }

        } else if (listeningToCmdStack) {
            getEditingDomain().getCommandStack()
                    .removeCommandStackListener(this);
            listeningToCmdStack = false;
        }

    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (listeningToCmdStack) {
            getEditingDomain().getCommandStack()
                    .removeCommandStackListener(this);
            listeningToCmdStack = false;
        }

        super.dispose();
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    public Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }

    /**
     * @see org.eclipse.emf.common.command.CommandStackListener#commandStackChanged(java.util.EventObject)
     * 
     * @param event
     */
    @Override
    public void commandStackChanged(EventObject event) {
        Display.getDefault().asyncExec(new Runnable() {

            @Override
            public void run() {
                if (!isIgnoreEvents() && getActivity() != null
                        && !errorCodeValue.isDisposed()
                        && errorCodeValue.isVisible()) {
                    refresh();
                }
            }
        });
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean ret = false;

        ret = super.shouldRefresh(notifications);
        if (!ret && getInput() != null) {
            //
            // Lots of things can effect catch error (like deleting caught
            // event, selecting different service in task and so on).
            //
            // So refresh on just about anything.

            for (Notification notification : notifications) {
                if (!notification.isTouch()) {
                    // We get notifications from everything in transactional
                    // editing domain - so we must make sure that we only
                    // refresh on changes to Xpdl2 packages.
                    Object notifier = notification.getNotifier();
                    if (notifier instanceof EObject) {
                        EObject eo = (EObject) notifier;
                        if (Xpdl2ModelUtil.getPackage(eo) != null) {
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }
}
