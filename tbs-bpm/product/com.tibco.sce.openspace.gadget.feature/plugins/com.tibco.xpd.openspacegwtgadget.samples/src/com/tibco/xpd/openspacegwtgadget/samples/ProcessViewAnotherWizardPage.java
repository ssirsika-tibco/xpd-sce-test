/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.samples;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.openspacegwtgadget.integration.api.AbstractOpenspaceGadgetWizardPage;

/**
 * Simple example additional openspace sample creation wizard page.
 * <p>
 * Has some controls that add extra properties that can be used in sample
 * contribition's SampleFile paths and SourceJetEmitters.
 * 
 * @author aallway
 * @since 21 Feb 2013
 */
public class ProcessViewAnotherWizardPage extends
        AbstractOpenspaceGadgetWizardPage {

    private Map<String, String> sampleProperties;

    private Button[] choices;

    private static final String RADIO_PROPERTY = "radioProperty";

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    @Override
    public void createControl(Composite parent) {

        Composite root = new Composite(parent, SWT.NONE);

        root.setLayout(new GridLayout(1, false));

        Label lab = new Label(root, NONE);
        lab.setText("I name thee....");
        lab.setLayoutData(new GridData());

        choices = new Button[10];

        for (int i = 0; i < choices.length; i++) {
            Button choice = new Button(root, SWT.RADIO);
            choices[i] = choice;
            choice.setText("This is Choice " + i);
            choice.setData("Choice" + i);

            choice.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            choice.addSelectionListener(new SelectionListener() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    String choiceVal = (String) ((Button) e.widget).getData();

                    sampleProperties.put(RADIO_PROPERTY, choiceVal);

                    setPageComplete(validateControls());
                }

                @Override
                public void widgetDefaultSelected(SelectionEvent e) {
                }
            });

        }

        choices[0].setSelection(true);

        setControl(root);

        /*
         * In this example page we'll just allow the user to finish before
         * stepping onto page 9which is fine as we set up a default value etc.
         */
        setPageComplete(true);
    }

    /**
     * Check validity of the selections made in all controls
     */
    private boolean validateControls() {
        return true;
    }

    /**
     * @see com.tibco.xpd.openspacegwtgadget.integration.api.IOpenspaceGadgetWizardPage#setVariableProperties(java.util.Map)
     * 
     * @param sampleProperties
     */
    @Override
    public void setVariableProperties(Map<String, String> sampleProperties) {
        this.sampleProperties = sampleProperties;

        /* Set up defaults. */
        this.sampleProperties.put(RADIO_PROPERTY, "Choice1");

    }

}
