/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Text input control that will consist of a label (optional), text control and
 * a data picker browse button. If multiple selections are made in the data
 * picker then they will be added to the text control using <i><b>space</b></i>
 * as the delimeter
 * 
 * @author njpatel
 * 
 */
public class EmailTextInputField extends EmailIF implements
        SelectionListener {

    protected Button browseButton;

    protected Text textControl;

    public EmailTextInputField(XpdFormToolkit toolkit, Composite parent,
            String label, EStructuralFeature eFeature,
            IDataPP pickerProvider) {

        super(toolkit, parent, label, eFeature, pickerProvider);
    }

    @Override
    public void createControls(Composite parent, String label) {
        if (parent != null) {
            GridData gData;

            // If label has been provided then add label
            if (label != null) {
                Label lbl = getToolkit().createLabel(parent, label, SWT.WRAP);
                lbl.setToolTipText(label);
                gData = new GridData();
                gData.widthHint = getLabelWidthHint();
                lbl.setLayoutData(gData);
            }

            textControl = getToolkit().createText(parent, ""); //$NON-NLS-1$
            gData = new GridData(GridData.FILL_HORIZONTAL);
            gData.widthHint = getTextWidthHint();
            textControl.setLayoutData(gData);
            textControl.setData(this);

            browseButton = getToolkit().createButton(parent, BROWSE_LABEL,
                    SWT.PUSH);
            browseButton.setToolTipText(browseToolTipText);
            browseButton.addSelectionListener(this);
        }

    }

    /**
     * Get the <code>Text</code> control
     * 
     * @return <code>Text</code>
     */
    public Text getTextControl() {
        return textControl;
    }

    @Override
    public String getText() {
        return textControl != null ? textControl.getText() : null;
    }

    @Override
    public void setText(String text) {
        if (textControl != null && !textControl.isDisposed()) {
            text = (text != null ? text : "");  //$NON-NLS-1$
            int position = textControl.getCaretPosition();
            textControl.setText(text);
            position = Math.min(position, text.length() + 1);
            textControl.setSelection(position);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public final void widgetDefaultSelected(SelectionEvent e) {
        // Do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public final void widgetSelected(SelectionEvent e) {
        handleBrowse();
    }

    @Override
    public void setEnabled(boolean enabled) {
        textControl.setEnabled(enabled);
        browseButton.setEnabled(enabled);
    }

    /**
     * Handle the browse button click.
     */
    protected void handleBrowse() {
        // Get the datafields selected in the picker
        ProcessRelevantData[] fields = getDataFields();

        if (fields != null) {
            // Combine all fields with spaces in between
            String fieldStr = ""; //$NON-NLS-1$

            if (fieldStr.length() > 0) {
                fieldStr += " "; //$NON-NLS-1$
            }

            for (ProcessRelevantData field : fields) {
                fieldStr += PROCESS_FIELD_DELIM + field.getName()
                        + PROCESS_FIELD_DELIM;
            }

            // Add the string to the text field
            if (fieldStr.length() > 0) {
                textControl.setText(textControl.getText() + fieldStr);
            }
        }
    }

}
