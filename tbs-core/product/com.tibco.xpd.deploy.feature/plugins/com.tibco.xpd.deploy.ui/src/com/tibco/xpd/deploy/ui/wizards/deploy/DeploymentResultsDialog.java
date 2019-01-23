/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import java.text.MessageFormat;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.deploy.model.extension.DeploymentStatus;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.actions.DeploymentActionStatus;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.DetailsDialog;

/**
 * Deployment result dialog.
 * 
 * @author jan
 * @author njpatel
 * 
 */
public class DeploymentResultsDialog extends DetailsDialog {

    private Text detailsText;

    private final String message;

    private final String details;

    private Button dontShow;

    private boolean showDialog;

    public DeploymentResultsDialog(Shell parentShell,
            DeploymentActionStatus status) {
        super(parentShell, Messages.DeploymentResultsDialog_Dialog_title);
        this.message = status.getMessage();
        this.details = status.getDetails();
        setImageKeyForStatus(status);

        showDialog = DeployUIActivator.getDefault().showDeployResultDialog();

    }

    /**
     * Sets the dialog icon depending on a status.
     */
    private void setImageKeyForStatus(DeploymentStatus status) {
        switch (status.getSeverity()) {
        case OK:
        case INFO:
            setImageKey(DLG_IMG_MESSAGE_INFO);
            break;
        case WARNNG:
            setImageKey(DLG_IMG_MESSAGE_WARNING);
            break;
        case ERROR:
        case CANCEL:
            setImageKey(DLG_IMG_MESSAGE_ERROR);
            break;
        default:
            Assert.isLegal(false, MessageFormat.format(
                    "Invalid status. [{0}]", status)); //$NON-NLS-1$
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

        dontShow = new Button(container, SWT.CHECK);
        dontShow.setText(Messages.DeploymentResultsDialog_DontShowAgain_label);
        dontShow.setLayoutData(new GridData(GridData.FILL, GridData.END, true,
                false));
        dontShow.setSelection(!showDialog);
        dontShow.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // Update the preference store with the selection
                DeployUIActivator.getDefault().setShowDeployResultDialog(
                        !dontShow.getSelection());
            }
        });

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
