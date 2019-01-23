/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;

/**
 * Configurable dialog to enter a value.
 * 
 * @author aallway
 * @since 3.2
 */
public class EnterValueDialog extends Dialog {

    /** The value control. */
    private Text valueControl;

    private String title;

    private String valueLabel;

    private String initialValue;

    private String newValue;

    private CLabel invalidMessageLabel;

    /**
     * @param parentShell
     * @param title
     *            Title of dialog.
     * @param valueLabel
     *            Label before the value control
     * @param initialValue
     *            Initial value for value control
     */
    public EnterValueDialog(Shell parentShell, String title, String valueLabel,
            String initialValue) {
        super(parentShell);

        this.title = title;
        this.valueLabel = valueLabel;
        this.initialValue = initialValue;
        this.newValue = initialValue;

        return;
    }

    /**
     * @param shell
     *            The shell.
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(title);
    }

    /**
     * @return The new name.
     */
    public String getValue() {
        return newValue != null ? newValue : ""; //$NON-NLS-1$
    }

    protected int getValueControlWidth() {
        return 150;
    }

    /**
     * @param parent
     *            The parent composite.
     * @return The root dialog control.
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout(2, false);
        composite.setLayout(layout);

        Label label = new Label(composite, SWT.NONE);
        label.setText(valueLabel);
        label.setLayoutData(new GridData());

        valueControl = new Text(composite, SWT.BORDER);
        if (initialValue != null) {
            valueControl.setText(initialValue);
            valueControl.setSelection(0, initialValue.length());
        }

        valueControl.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                validate();
            }
        });

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.widthHint = getValueControlWidth();
        valueControl.setLayoutData(gd);

        invalidMessageLabel = new CLabel(composite, SWT.WRAP);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        invalidMessageLabel.setLayoutData(gd);

        return composite;
    }

    @Override
    protected Control createButtonBar(Composite parent) {
        Control c = super.createButtonBar(parent);
        validate();
        return c;
    }

    private void validate() {
        String val = null;
        if (valueControl != null && !valueControl.isDisposed()) {
            val = valueControl.getText();
        }
        if (val == null) {
            val = ""; //$NON-NLS-1$
        }

        String error = validateValue(val);
        Image img = null;
        String newMessage = ""; //$NON-NLS-1$

        if (error != null && error.length() != 0) {
            img =
                    Xpdl2UiPlugin.getDefault().getImageRegistry()
                            .get(Xpdl2UiPlugin.IMG_ERROR);
            newMessage = error;

            getButton(OK).setEnabled(false);

        } else {
            newMessage = ""; //$NON-NLS-1$
            getButton(OK).setEnabled(true);
        }

        if (invalidMessageLabel.getImage() != img
                || !newMessage.equals(invalidMessageLabel.getText())) {

            invalidMessageLabel.setImage(img);
            invalidMessageLabel.setText(newMessage);

            GridData gd = new GridData(GridData.FILL_HORIZONTAL);
            gd.horizontalSpan = 2;
            if (newMessage == null || newMessage.length() == 0) {
                gd.heightHint = 1;
            }

            invalidMessageLabel.setLayoutData(gd);

            invalidMessageLabel.getParent().layout(true);

            Point curSz = getShell().getSize();
            Point sz = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);

            if (!getShell().isVisible()) {
                Point loc = Display.getDefault().getCursorLocation();
                getShell().setBounds(loc.x, loc.y, sz.x, sz.y);
            } else {
                if (curSz.x > sz.x) {
                    sz.x = curSz.x;
                }
                if (curSz.y > sz.y) {
                    sz.y = curSz.y;
                }
                Point loc = getShell().getLocation();
                getShell().setBounds(loc.x, loc.y, sz.x, sz.y);
            }
            
        }

        return;
    }

    /**
     * Validate the value when changed by the user (or on initial configuration)
     * 
     * @param newValue
     * @return null or "" if value is valid else an appropriate error
     *         description to show user.
     */
    protected String validateValue(String newValue) {
        return null;
    }

    protected void okPressed() {
        newValue = valueControl.getText();
        super.okPressed();
    }

}