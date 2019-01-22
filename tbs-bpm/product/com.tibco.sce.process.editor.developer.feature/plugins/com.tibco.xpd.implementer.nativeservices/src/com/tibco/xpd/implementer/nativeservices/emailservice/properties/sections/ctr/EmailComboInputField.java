/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Combo input control that will consist of a label (optional), combo control
 * and a data picker browse button. The data picker only allows single selection
 * 
 * @author njpatel
 * 
 */
public class EmailComboInputField extends EmailIF implements SelectionListener {

    private Button browseBtn;

    private CCombo comboControl;

    private static final String possibleValues = "Possible_Value"; //$NON-NLS-1$

    public EmailComboInputField(XpdFormToolkit toolkit, Composite parent,
            String label, EStructuralFeature eFeature,
            Map<String, String> comboValueLabel, IDataPP pickerProvider) {
        super(toolkit, parent, label, eFeature, pickerProvider);
        // If combo values have been provided then add them
        if (comboValueLabel != null && comboControl != null) {
            for (Map.Entry<String, String> entry : comboValueLabel.entrySet()) {
                comboControl.add(entry.getValue());
            }
            comboControl.setData(possibleValues, comboValueLabel);
        } else if (comboValueLabel == null) {
            comboControl.setData(possibleValues, Collections.emptyMap());
        }
    }

    @Override
    public void createControls(Composite parent, String label) {
        if (parent != null && label != null) {
            GridData gData;

            if (label != null) {
                Label lbl = getToolkit().createLabel(parent, label, SWT.WRAP);
                gData = new GridData();
                gData.widthHint = getLabelWidthHint();
                lbl.setLayoutData(gData);
            }

            comboControl = getToolkit().createCCombo(parent, SWT.NONE);

            // Set the correct border
            comboControl
                    .setData(TabbedPropertySheetWidgetFactory.KEY_DRAW_BORDER,
                            TabbedPropertySheetWidgetFactory.TEXT_BORDER);

            gData = new GridData(GridData.FILL_HORIZONTAL);
            gData.widthHint = getTextWidthHint();
            comboControl.setLayoutData(gData);
            comboControl.setData(this);

            browseBtn =
                    getToolkit().createButton(parent, BROWSE_LABEL, SWT.PUSH);
            browseBtn.setToolTipText(browseToolTipText);
            browseBtn.addSelectionListener(this);
        }
    }

    /**
     * Get the combo control
     * 
     * @return <code>CCombo</code>
     */
    public CCombo getComboControl() {
        return comboControl;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getText() {
        String toReturn = ""; //$NON-NLS-1$
        if (comboControl != null) {
            String localisedText = comboControl.getText();
            Map<String, String> possibleValueMap =
                    (Map<String, String>) comboControl.getData(possibleValues);
            Set<Entry<String, String>> entrySet = possibleValueMap.entrySet();
            if (localisedText != null && localisedText.length() > 0) {
                toReturn = localisedText;
                for (Entry<String, String> mapEntry : entrySet) {
                    if (mapEntry.getValue().equals(localisedText)) {
                        toReturn = mapEntry.getKey();
                        break;
                    }
                }
            }
        }
        return toReturn;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setText(String text) {
        if (comboControl != null && !comboControl.isDisposed()) {
            if (text != null) {
                Map<String, String> possibleValueMap =
                        (Map<String, String>) comboControl
                                .getData(possibleValues);
                Set<Entry<String, String>> entrySet =
                        possibleValueMap.entrySet();
                String toSet = text;
                for (Entry<String, String> mapEntry : entrySet) {
                    if (mapEntry.getKey().equals(text)) {
                        toSet = mapEntry.getValue();
                        break;
                    }
                }
                comboControl.setText(toSet);
            } else {
                comboControl.setText(""); //$NON-NLS-1$
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse
     * .swt.events.SelectionEvent)
     */
    public final void widgetDefaultSelected(SelectionEvent e) {
        // Do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt
     * .events.SelectionEvent)
     */
    public final void widgetSelected(SelectionEvent e) {
        handleBrowse();
    }

    @Override
    public void setEnabled(boolean enabled) {
        comboControl.setEnabled(enabled);
        browseBtn.setEnabled(enabled);
    }

    /**
     * Handle the browse button click
     */
    protected void handleBrowse() {
        ProcessRelevantData field = getDataField();

        if (field != null) {
            comboControl.setText(PROCESS_FIELD_DELIM + field.getName()
                    + PROCESS_FIELD_DELIM);
        }
    }

}
