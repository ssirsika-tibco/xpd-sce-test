/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.dialogs;

import java.text.MessageFormat;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Message dialog with text field detail component.
 * 
 * @author Jan Arciuchiewicz
 */
public class TextDetailsDialog extends DetailsDialog {

    private Text detailsText;

    private final String message;

    private final String details;

    /**
     * Severity of dialog determines dialog icon.
     * <p>
     * <i>Created: 8 Aug 2007</i>
     * </p>
     * 
     * @author Jan Arciuchiewicz
     * 
     */
    public enum Severity {
        /** Indicates information message. */
        INFO,

        /** Indicates warning message. */
        WARNING,

        /** Indicate error message. */
        ERROR
    };

    /**
     * Creates dialog.
     * 
     * @param parentShell
     *            Parent shell for the dialog.
     * @param title
     *            The dialog title.
     * @param severity
     *            The severity (determines icon).
     * @param message
     *            The short message.
     * @param details
     *            The details message.
     */
    public TextDetailsDialog(Shell parentShell, String title,
            Severity severity, String message, String details) {
        super(parentShell, title);
        this.message = message;
        this.details = details;
        setImageKeyForStatus(severity);

    }

    /**
     * Displays info message dialog with text details. This method waits until
     * the window is closed by the end user, and then it returns the window's
     * return code; otherwise, this method returns immediately. A window's
     * return codes are window-specific, although two standard return codes are
     * predefined: <code>OK</code> and <code>CANCEL</code>.
     * 
     * @param parentShell
     *            Parent shell for the dialog.
     * @param title
     *            The dialog title.
     * @param message
     *            The short message.
     * @param details
     *            The details message.
     * 
     * @return The Window return code.
     *         <code>Window.OK</code> <code>Window.CANCEL</code>
     */
    public static int openInfo(Shell parentShell, String title, String message,
            String details) {
        return new TextDetailsDialog(parentShell, title, Severity.INFO,
                message, details).open();
    }

    /**
     * Displays warning message dialog with text details. This method waits
     * until the window is closed by the end user, and then it returns the
     * window's return code; otherwise, this method returns immediately. A
     * window's return codes are window-specific, although two standard return
     * codes are predefined: <code>OK</code> and <code>CANCEL</code>.
     * 
     * @param parentShell
     *            Parent shell for the dialog.
     * @param title
     *            The dialog title.
     * @param message
     *            The short message.
     * @param details
     *            The details message.
     * @return The Window return code.
     *         <code>Window.OK</code> <code>Window.CANCEL</code>
     */
    public static int openWarning(Shell parentShell, String title,
            String message, String details) {
        return new TextDetailsDialog(parentShell, title, Severity.WARNING,
                message, details).open();
    }

    /**
     * Displays error message dialog with text details.This method waits until
     * the window is closed by the end user, and then it returns the window's
     * return code; otherwise, this method returns immediately. A window's
     * return codes are window-specific, although two standard return codes are
     * predefined: <code>OK</code> and <code>CANCEL</code>.
     * 
     * @param parentShell
     *            Parent shell for the dialog.
     * @param title
     *            The dialog title.
     * @param message
     *            The short message.
     * @param details
     *            The details message.
     * @return The Window return code.
     *         <code>Window.OK</code> <code>Window.CANCEL</code>
     */
    public static int openError(Shell parentShell, String title,
            String message, String details) {
        return new TextDetailsDialog(parentShell, title, Severity.ERROR,
                message, details).open();
    }

    /**
     * Sets the dialog icon depending on a status.
     */
    private void setImageKeyForStatus(Severity severtity) {
        switch (severtity) {
        case INFO:
            setImageKey(DLG_IMG_MESSAGE_INFO);
            break;
        case WARNING:
            setImageKey(DLG_IMG_MESSAGE_WARNING);
            break;
        case ERROR:
            setImageKey(DLG_IMG_MESSAGE_ERROR);
            break;
        default:
            Assert.isLegal(false, MessageFormat.format(
                    "Invalid status. [{0}]", severtity)); //$NON-NLS-1$
        }
    }

    @Override
    protected Composite createDropDownDialogArea(Composite parent) {
        // create a composite with standard margins and spacing
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
        layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
        layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        detailsText = new Text(composite, SWT.READ_ONLY | SWT.BORDER
                | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        detailsText.setText(details);
        GridData data = new GridData();
        data.heightHint = 75;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        detailsText.setLayoutData(data);

        return composite;
    }

    @Override
    protected void createMainDialogArea(Composite parent) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(message);
        GridData data = new GridData(GridData.GRAB_HORIZONTAL
                | GridData.GRAB_VERTICAL | GridData.HORIZONTAL_ALIGN_FILL
                | GridData.VERTICAL_ALIGN_CENTER);
        data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
        label.setLayoutData(data);
        updateEnablements();
    }

    @Override
    protected Control createStatusArea(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        container.setLayout(layout);
        return container;
    }

    @Override
    protected void updateEnablements() {
        // do nothing
    }

    @Override
    protected boolean includeCancelButton() {
        return false;
    }

    @Override
    protected boolean isMainGrabVertical() {
        return false;
    }

}
