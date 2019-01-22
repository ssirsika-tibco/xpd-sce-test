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
 * a data picker browse button. The text control is read-only. Only
 * single-selection is allowed in the data picker.
 * 
 * @author njpatel
 * 
 */
public class EmailROnlyField extends EmailIF implements SelectionListener {

    private Button browseBtn;

    private Text textControl;
    
    private Button clearBtn;

    public EmailROnlyField(XpdFormToolkit toolkit, Composite parent,
            String label, EStructuralFeature eFeature, IDataPP pickerProvider) {

        super(toolkit, parent, label, eFeature, pickerProvider);
    }

    @Override
    public void createControls(Composite parent, String label) {
        if (parent != null) {
            GridData gData;

            // If label available then add it
            if (label != null) {
                Label lbl = getToolkit().createLabel(parent, label, SWT.WRAP);
                gData = new GridData();
                gData.widthHint = getLabelWidthHint();
                lbl.setLayoutData(gData);
            }
            textControl = getToolkit().createText(parent, ""); //$NON-NLS-1$

            gData = new GridData(GridData.FILL_HORIZONTAL);
            gData.widthHint = getTextWidthHint();
            textControl.setLayoutData(gData);
            textControl.setEditable(false);
            textControl.setData(this);

            browseBtn = getToolkit().createButton(parent, BROWSE_LABEL,
                    SWT.PUSH);
            browseBtn.setToolTipText(browseToolTipText);
            browseBtn.addSelectionListener(this);
            
            clearBtn = getToolkit().createButton(parent, CLEAR_LABEL, SWT.PUSH);
            clearBtn.setToolTipText(clearToolTipText);
            clearBtn.addSelectionListener(this);
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
            text = (text != null ? text : ""); //$NON-NLS-1$
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
        Object source = e.getSource();
        if (source instanceof Button) {
            Button button = (Button) source;
            String text = button.getText();
            if (text.equals(CLEAR_LABEL)) {
                handleClear();
            } else if (text.equals(BROWSE_LABEL)) {                
                handleBrowse();
            }
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        textControl.setEnabled(enabled);
        browseBtn.setEnabled(enabled);
        clearBtn.setEnabled(enabled);
    }

    /**
     * Handle the browse button click
     */
    protected void handleBrowse() {
        ProcessRelevantData field = getDataField();

        if (field != null) {
            textControl.setText(field.getName());
        }
    }

    protected void handleClear() {
        if (textControl.getText() != null || textControl.getText().length() > 0) {
            textControl.setText(""); //$NON-NLS-1$
        }
    }
}
