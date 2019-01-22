/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.Messages;

/**
 * Properties dialog that allows user to set name and description of
 * fragment/category.
 * 
 * @author njpatel
 * 
 */
public class PropertiesDialog extends Dialog {

    private String name;
    private String description;
    private boolean isSystem;
    private final IFragmentElement element;

    /**
     * Properties dialog
     * 
     * @param parentShell
     *            <code>Shell</code>.
     * @param element
     *            fragment element to edit.
     */
    public PropertiesDialog(Shell parentShell, IFragmentElement element) {
        super(parentShell);
        this.element = element;

        name = element.getName();
        description = element.getDescription();
        isSystem = element.isSystem();
    }

    /**
     * Get the name set in the dialog.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description set in the dialog.
     * 
     * @return description, or <code>null</code> if no description set.
     */
    public String getDescription() {
        return description;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite root = (Composite) super.createDialogArea(parent);
        root.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        root.setLayout(new GridLayout(2, false));

        Label nameLbl = new Label(root, SWT.NONE);
        nameLbl.setText(Messages.PropertiesDialog_name_label);

        int style = SWT.BORDER;
        if (isSystem) {
            style |= SWT.READ_ONLY;
        }
        final Text nameTxt = new Text(root, style);
        GridData gData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gData.minimumWidth = 150;
        gData.widthHint = 350;
        nameTxt.setLayoutData(gData);
        if (name != null) {
            nameTxt.setText(name);
        }
        nameTxt.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                name = nameTxt.getText().trim();

                enableOK(name != null && name.length() > 0);
            }

        });

        Label descLbl = new Label(root, SWT.NONE);
        descLbl.setText(Messages.PropertiesDialog_description_label);

        style = SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP;
        if (isSystem) {
            style |= SWT.READ_ONLY;
        }
        final Text descTxt = new Text(root, style);
        gData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gData.heightHint = 50;
        gData.minimumWidth = 150;
        gData.widthHint = 350;
        descTxt.setLayoutData(gData);
        descTxt.setTextLimit(2048);
        if (description != null) {
            descTxt.setText(description);
        }
        descTxt.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                description = descTxt.getText().trim();
            }

        });

        if (isSystem) {
            // First column spacing
            new Label(root, SWT.NONE);

            Label lbl = new Label(root, SWT.NONE);
            lbl
                    .setText(isCategory() ? Messages.PropertiesDialog_systemCategory_shortdesc
                            : Messages.PropertiesDialog_systemFragment_shortdesc);

        }

        Shell shell = getShell();
        shell
                .setText(isCategory() ? Messages.PropertiesDialog_category_dialog_title
                        : Messages.PropertiesDialog_fragment_dialog_title);

        return root;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        if (!isSystem) {
            enableOK(name != null && name.trim().length() > 0);
        } else {
            // System element so cannot edit
            enableOK(false);
        }
    }

    /**
     * Set the status of the OK button.
     * 
     * @param enabled
     *            <code>true</code> to enable.
     */
    private void enableOK(boolean enabled) {
        Button okBtn = getButton(IDialogConstants.OK_ID);

        if (okBtn != null && !okBtn.isDisposed()) {
            okBtn.setEnabled(enabled);
        }
    }

    /**
     * Check if the element being edited is a category.
     * 
     * @return <code>true</code> if category, <code>false</code> otherwise.
     */
    private boolean isCategory() {
        return element instanceof IFragmentCategory;
    }

}
