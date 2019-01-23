/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.samples;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

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
public class ProcessViewSampleExtraWizardPage extends
        AbstractOpenspaceGadgetWizardPage {

    private Map<String, String> sampleProperties;

    private Text addInfoText;

    private static final String EXTRA_PROPERTY = "extraProperty"; //$NON-NLS-1$

    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    @Override
    public void createControl(Composite parent) {

        Composite root = new Composite(parent, SWT.NONE);

        root.setLayout(new GridLayout(2, false));

        Label label = new Label(root, SWT.NONE);
        label.setText("Some Additional Info:");
        label.setLayoutData(new GridData());

        addInfoText = new Text(root, SWT.BORDER);
        addInfoText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        addInfoText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                sampleProperties.put(EXTRA_PROPERTY, addInfoText.getText());

                setPageComplete(validateControls());
            }

        });

        addInfoText.setText(sampleProperties.get(EXTRA_PROPERTY));

        setControl(root);

        /*
         * We want to only allow user to [Finish] after they've stepped onto
         * this page first time so we'll set pagege incomplete until the first
         * call to setVisible()).
         */
        setPageComplete(false);
    }

    /**
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * 
     * @param visible
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        /*
         * We want to only allow user to [Finish] after they've stepped onto
         * page first time so we'll set PageComplete only on first setVisible).
         */
        setPageComplete(validateControls());
    }

    /**
     * Check validity of the selections made in all controls
     */
    private boolean validateControls() {
        String text = addInfoText.getText();

        return text != null && text.length() > 0;
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
        this.sampleProperties.put(EXTRA_PROPERTY, "Extra Property Value");

    }

}
