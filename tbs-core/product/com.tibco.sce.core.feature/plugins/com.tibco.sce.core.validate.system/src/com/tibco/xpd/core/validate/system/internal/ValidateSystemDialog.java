/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.core.validate.system.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.tibco.xpd.core.validate.system.Activator;

/**
 * Validate system dialog that will run a check on the Studio installation and
 * report the results to the user. Note that this is not a blocking dialog.
 * 
 * @author njpatel
 * 
 */
public class ValidateSystemDialog extends Dialog implements
        IConfigurationLogger, IRunnableContext {

    private final FormToolkit toolkit;
    private StringBuffer validationMessage;
    private ScrolledForm form;
    private Button okBtn;
    private final Image doneImg;
    private boolean isComplete = false;
    private FormText text;
    private final IWorkbenchWindow window;
    private final ValidationProgressMonitor monitor;
    private Composite dialogArea;
    private final IHyperlinkListener hyperlinkListener;
    private final Cursor waitCursor;
    private Cursor defaultCursor;
    /**
     * List of all hyperlink hrefs that need re-validating when activated in the
     * form.
     */
    private List<String> hrefsToRevalidate = null;

    /**
     * Validate system dialog that will run checks on the Studio installation.
     * 
     * @param window
     *            workbench window.
     */
    public ValidateSystemDialog(IWorkbenchWindow window) {
        super(window.getShell());
        this.window = window;
        toolkit = new FormToolkit(window.getShell().getDisplay());

        setShellStyle(getShellStyle() | SWT.RESIZE);
        doneImg = Activator.getImageDescriptor("icons/obj16/step_done.gif") //$NON-NLS-1$
                .createImage();
        setBlockOnOpen(false);
        monitor = new ValidationProgressMonitor();
        waitCursor = new Cursor(window.getShell().getDisplay(), SWT.CURSOR_WAIT);

        // Create hyperlink listener to re-validate if requested
        hyperlinkListener = new HyperlinkAdapter() {
            @Override
            public void linkActivated(HyperlinkEvent e) {
                Object href = e.getHref();

                if (href != null && hrefsToRevalidate != null
                        && hrefsToRevalidate.contains(href)) {
                    validate();
                }
            }
        };
    }

    @Override
    public boolean close() {
        waitCursor.dispose();
        text.dispose();
        text = null;
        form.dispose();
        form = null;
        toolkit.dispose();
        doneImg.dispose();
        dialogArea.dispose();
        dialogArea = null;

        return super.close();
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        dialogArea = (Composite) super.createDialogArea(parent);
        getShell().setText(Messages.ValidateSystemDialog_title_notrans);

        // Header
        Section section = toolkit
                .createSection(dialogArea, Section.DESCRIPTION);
        section.setLayout(new FillLayout());
        section.setText(Messages.ValidateSystemDialog_section_title);
        section.setDescription(Messages.ValidateSystemDialog_section_shortdesc);

        section.marginWidth = 10;
        section.marginHeight = 5;
        section.clientVerticalSpacing = 15;
        toolkit.createCompositeSeparator(section);
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd.widthHint = 600;
        gd.heightHint = 450;
        section.setLayoutData(gd);

        Composite client = createClientArea(section);
        section.setClient(client);

        return dialogArea;
    }

    @Override
    protected void okPressed() {
        // If process is not complete then OK button is the Cancel process
        // button so indicate that the process is being cancelled
        if (!isComplete) {
            monitor.setCanceled(true);
            okBtn.setEnabled(false);
            return;
        }
        super.okPressed();
    }

    /**
     * Create the main client area that will occupy the part above the progress
     * bar and button.
     * 
     * @param parent
     *            parent composite
     * @return client composite
     */
    private Composite createClientArea(Composite parent) {
        Composite root = toolkit.createComposite(parent, SWT.BORDER);
        FillLayout layout = new FillLayout();
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        root.setLayout(layout);

        form = toolkit.createScrolledForm(root);
        form.getBody().setLayout(new TableWrapLayout());
        form.setExpandHorizontal(true);
        form.setExpandVertical(true);
        defaultCursor = form.getCursor();

        Image warnImg = PlatformUI.getWorkbench().getSharedImages().getImage(
                ISharedImages.IMG_OBJS_WARN_TSK);
        Image errorImg = PlatformUI.getWorkbench().getSharedImages().getImage(
                ISharedImages.IMG_OBJS_ERROR_TSK);
        Image infoImg = PlatformUI.getWorkbench().getSharedImages().getImage(
                ISharedImages.IMG_OBJS_INFO_TSK);

        text = toolkit.createFormText(form.getBody(), false);
        text.setBackground(form.getBackground());
        text.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

        // Set resources
        text.setColor("header", toolkit.getColors().getColor( //$NON-NLS-1$
                IFormColors.TITLE));
        text.setColor(
                "advice", Display.getDefault().getSystemColor(SWT.COLOR_BLUE)); //$NON-NLS-1$
        text.setFont("header", JFaceResources.getHeaderFont()); //$NON-NLS-1$
        text.setFont("code", JFaceResources.getTextFont()); //$NON-NLS-1$
        text.setImage("success", doneImg); //$NON-NLS-1$
        text.setImage("warn", warnImg); //$NON-NLS-1$
        text.setImage("info", infoImg); //$NON-NLS-1$
        text.setImage("error", errorImg); //$NON-NLS-1$

        form.setFocus();

        return root;
    }

    @Override
    public int open() {
        int ret = super.open();

        validate();

        return ret;
    }

    private void validate() {
        /*
         * Run the validation check
         */
        try {
            run(true, true, new IRunnableWithProgress() {

                public void run(IProgressMonitor monitor)
                        throws InvocationTargetException, InterruptedException {
                    // Create a buffer that will be used to store all the output
                    // for the dialog before its printed to the form
                    isComplete = false;
                    validationMessage = new StringBuffer();
                    start();
                    SystemCheckRules rules = new SystemCheckRules(window,
                            ValidateSystemDialog.this);
                    /*
                     * Sleep to stop the "please wait..." message from flashing
                     * off screen too quickly. Generally the first time the
                     * validation is run it will take some time. All subsequent
                     * runs happen in a flash.
                     */
                    Thread.sleep(500);

                    try {
                        rules.runChecks(monitor);
                    } catch (OperationCanceledException e) {
                        setFormMessage(
                                Messages.ValidateSystemDialog_processCancelled_shortdesc,
                                false, IMessageProvider.ERROR);
                    }
                }
            });
        } catch (InvocationTargetException e) {
            // Log error in the dialog
            Throwable cause = e.getCause();
            if (cause == null) {
                cause = e;
            }
            message(cause.getLocalizedMessage(), IStatus.ERROR);
        } catch (InterruptedException e) {
            // Log error in the dialog
            if (e.getLocalizedMessage() != null) {
                message(e.getLocalizedMessage(), IStatus.ERROR);
            }
        } finally {
            isComplete = true;
            complete();
        }
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        // Set the text to Cancel by default
        okBtn = createButton(parent, IDialogConstants.OK_ID,
                IDialogConstants.CANCEL_LABEL, true);
        okBtn
                .setToolTipText(Messages.ValidateSystemDialog_cancelProcess_tooltip);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.core.validate.internal.system.IConfigurationLogger#heading
     * (java.lang.String)
     */
    public void heading(String message) {
        if (message != null) {
            validationMessage
                    .append("<p><br/><span color=\"header\" font=\"header\">"); //$NON-NLS-1$
            validationMessage.append(escapeHTML(message));
            validationMessage.append("</span></p>"); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.core.validate.internal.system.IConfigurationLogger#message
     * (java.lang.String, int)
     */
    public void message(String message, int severity) {
        message(message, null, severity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.core.validate.internal.system.IConfigurationLogger#message
     * (java.lang.String, java.lang.String[], int)
     */
    public void message(final String message, final String[] additionalBullets,
            final int severity) {
        if (form != null && !form.isDisposed()) {
            getShell().getDisplay().syncExec(new Runnable() {
                public void run() {
                    switch (severity) {
                    case IStatus.ERROR:
                        error(message, additionalBullets);
                        break;
                    case IStatus.WARNING:
                        warn(message, additionalBullets);
                        break;

                    case IStatus.INFO:
                        info(message, additionalBullets);
                        break;

                    case IStatus.OK:
                        success(message, additionalBullets);
                        break;

                    default:
                        validationMessage.append(appendBullets(
                                escapeHTML(message), additionalBullets));
                        break;
                    }

                }
            });
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.core.validate.internal.system.IConfigurationLogger#hyperlink
     * (java.lang.String, org.eclipse.ui.forms.events.IHyperlinkListener)
     */
    public void hyperlink(String message, String href,
            IHyperlinkListener listener, boolean revalidateOnLinkActivate) {
        if (message != null) {
            validationMessage.append("<p><a href=\""); //$NON-NLS-1$
            validationMessage.append(href);
            validationMessage.append("\">"); //$NON-NLS-1$
            validationMessage.append(escapeHTML(message));
            validationMessage.append("</a></p>"); //$NON-NLS-1$
            text.addHyperlinkListener(listener);

            // If re-validation is required when this link is activated then add
            // to monitoring list
            if (href != null && revalidateOnLinkActivate) {
                if (hrefsToRevalidate == null) {
                    hrefsToRevalidate = new ArrayList<String>();
                }
                hrefsToRevalidate.add(href);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.core.validate.internal.system.IConfigurationLogger#advice
     * (java.lang.String)
     */
    public void advice(String message) {
        if (message != null) {
            validationMessage.append("<p><span color=\"advice\">"); //$NON-NLS-1$
            validationMessage.append(escapeHTML(message));
            validationMessage.append("</span></p>"); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.core.validate.internal.system.IConfigurationLogger#result
     * (org.eclipse.core.runtime.IStatus)
     */
    public void result(final IStatus status) {
        if (status != null && toolkit != null && !form.isDisposed()) {
            int messageType = IMessageProvider.INFORMATION;

            switch (status.getSeverity()) {
            case IStatus.ERROR:
                messageType = IMessageProvider.ERROR;
                break;
            case IStatus.WARNING:
                messageType = IMessageProvider.WARNING;
                break;
            }
            setFormMessage(status.getMessage(), false, messageType);
        }
    }

    /**
     * Print error message to the dialog. optionally, include additional bullet
     * points under the message.
     * 
     * @param message
     * @param additionalBullets
     */
    private void error(String message, String[] additionalBullets) {

        if (message != null) {
            message = appendBullets(
                    "<img href=\"error\"/> " //$NON-NLS-1$
                            + escapeHTML(String
                                    .format(
                                            Messages.ValidateSystemDialog_errorMessage_shortdesc,
                                            message)), additionalBullets);
            validationMessage.append(message);
        }
    }

    /**
     * Print warning message to the dialog. optionally, include additional
     * bullet points under the message.
     * 
     * @param message
     * @param additionalBullets
     */
    private void warn(String message, String[] additionalBullets) {

        if (message != null) {
            message = appendBullets(
                    "<img href=\"warn\"/> " //$NON-NLS-1$
                            + escapeHTML(String
                                    .format(
                                            Messages.ValidateSystemDialog_warningMessage_shortdesc,
                                            message)), additionalBullets);

            validationMessage.append(message);
        }
    }

    /**
     * Print info message to the dialog. optionally, include additional bullet
     * points under the message.
     * 
     * @param message
     * @param additionalBullets
     */
    private void info(String message, String[] additionalBullets) {
        if (message != null) {
            message = appendBullets(
                    "<img href=\"info\"/> " //$NON-NLS-1$
                            + escapeHTML(String
                                    .format(
                                            Messages.ValidateSystemDialog_infoMessage_shortdesc,
                                            message)), additionalBullets);
            validationMessage.append(message);
        }
    }

    /**
     * Print success message to the dialog. optionally, include additional
     * bullet points under the message.
     * 
     * @param message
     * @param additionalBullets
     */
    private void success(String message, String[] additionalBullets) {
        if (message != null) {
            message = appendBullets(
                    "<img href=\"success\"/> " + escapeHTML(message), //$NON-NLS-1$
                    additionalBullets);
            validationMessage.append(message);
        }
    }

    /**
     * Validation process completed so update form and the OK button
     */
    private void complete() {
        if (form != null && !form.isDisposed()) {
            getShell().getDisplay().syncExec(new Runnable() {
                public void run() {
                    try {
                        text.setText("<form>" + validationMessage.toString() //$NON-NLS-1$
                                + "</form>", true, false); //$NON-NLS-1$

                        // If re-validation is required when hyperlink is
                        // activated
                        // then add listener here
                        if (hrefsToRevalidate != null
                                && !hrefsToRevalidate.isEmpty()) {
                            text.addHyperlinkListener(hyperlinkListener);
                        }
                    } finally {
                        okBtn.setToolTipText(""); //$NON-NLS-1$
                        okBtn.setText(IDialogConstants.OK_LABEL);
                        okBtn.setEnabled(true);

                        form.pack();
                        form.setBusy(false);
                        setDisplayCursor(defaultCursor);

                        // Force the scroll bars in the form to be drawn
                        // properly
                        Rectangle bounds = getShell().getBounds();
                        bounds.height += 1;
                        getShell().setBounds(bounds);
                        getShell().layout();
                    }

                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.operation.IRunnableContext#run(boolean, boolean,
     * org.eclipse.jface.operation.IRunnableWithProgress)
     */
    public void run(boolean fork, boolean cancelable,
            IRunnableWithProgress runnable) throws InvocationTargetException,
            InterruptedException {

        ModalContext.run(runnable, fork, monitor, getShell().getDisplay());
    }

    /**
     * Start the validation process. This will display busy message in form and
     * change the OK button to CANCEL.
     */
    private void start() {
        // Set the button to Cancel
        setFormMessage(
                Messages.ValidateSystemDialog_checkingInstallation_wait_shortdesc,
                true, IMessageProvider.NONE);
        // Clear the text control
        if (text != null && !text.isDisposed()) {
            getShell().getDisplay().syncExec(new Runnable() {
                public void run() {
                    text.setText("", false, false); //$NON-NLS-1$
                    form.pack();
                    setDisplayCursor(waitCursor);
                    okBtn.setText(IDialogConstants.CANCEL_LABEL);
                }
            });
        }

    }

    private void setDisplayCursor(Cursor cursor) {
        if (form != null && !form.isDisposed()) {
            form.setCursor(cursor);
            form.getParent().setCursor(cursor);
            text.setCursor(cursor);
        }
    }

    /**
     * Set the form message.
     * 
     * @param message
     * @param isBusy
     *            <code>true</code> if the form should be set to busy state
     * @param messageType
     *            {@link IMessageProvider type} of message
     */
    private void setFormMessage(final String message, final boolean isBusy,
            final int messageType) {
        if (form != null && !form.isDisposed()) {
            getShell().getDisplay().syncExec(new Runnable() {
                public void run() {
                    form.setBusy(isBusy);
                    form.setMessage(message, messageType);
                }

            });
        }
    }

    /**
     * Append the given bullet points to the message.
     * 
     * @param message
     *            main message (this should already be escaped if required)
     * @param bullets
     *            bullets to include under the message
     * @return
     */
    private String appendBullets(String message, String[] bullets) {
        if (message != null) {
            message = "<p>" + message + "</p>"; //$NON-NLS-1$ //$NON-NLS-2$

            if (bullets != null && bullets.length > 0) {
                for (String bullet : bullets) {
                    message += "<li bindent=\"20\">" + //$NON-NLS-1$ 
                            escapeHTML(bullet) + "</li>"; //$NON-NLS-1$
                }
            }
        }
        return message;
    }

    /**
     * Escape string to be used as HTML. This will:
     * <ul>
     * <li>Replace '&amp;' with &amp;amp;</li>
     * <li>Replace '&quot;' with &amp;quot;</li>
     * <li>Replace '&lt;' with &amp;lt;</li>
     * <li>Replace '&gt;' with &amp;gt;</li>
     * </ul>
     * Note that this will not escape the following html tags:
     * &lt;br/&gt;&lt;b&gt;&lt;/b&gt;&lt;span&gt;&lt;/span&gt;
     * 
     * @param text
     * @return escaped string.
     */
    private String escapeHTML(String text) {
        if (text != null) {
            text = text.replaceAll("&", "&amp;");
            text = text.replaceAll("\"", "&quot;");
            text = text.replaceAll("<(?!((br/)|([/]?(b|span)))>)", "&lt;");
            text = text.replaceAll("(?<!(<((br/)|([/]?(b|span)))))>", "&gt;");
        }

        return text;
    }

    /**
     * Validation progress monitor used to monitor cancel state.
     * 
     * @author njpatel
     * 
     */
    private class ValidationProgressMonitor implements IProgressMonitor {
        private boolean isCancelled = false;

        public void beginTask(String name, int totalWork) {
            // Do nothing
        }

        public void done() {
            // Do nothing
        }

        public void internalWorked(double work) {
            // Do nothing
        }

        public boolean isCanceled() {
            return isCancelled;
        }

        public void setCanceled(boolean value) {
            if (value) {
                // Update the title in the form to indicate to user that they
                // are in cancel stage
                setFormMessage(
                        Messages.ValidateSystemDialog_Cancelling_shortdesc,
                        true, IMessageProvider.NONE);
            }
            this.isCancelled = value;
        }

        public void setTaskName(String name) {
            // Do nothing
        }

        public void subTask(String name) {
            // Do nothing
        }

        public void worked(int work) {
            // Do nothing
        }

    }
}
