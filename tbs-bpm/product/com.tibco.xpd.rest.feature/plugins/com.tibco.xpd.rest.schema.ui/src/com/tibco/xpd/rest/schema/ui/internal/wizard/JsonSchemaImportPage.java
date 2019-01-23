/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.wizard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.rest.schema.ui.RestConstants;
import com.tibco.xpd.rest.schema.ui.internal.Messages;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;

/**
 * Wizard page for specifying the external resource to import and convert to a
 * JSON Schema file.
 * 
 * @author nwilson
 * @since 9 Jan 2015
 */
public class JsonSchemaImportPage extends JsonSchemaFilePage {

    private Button importJsonSchema;

    private Button importJsonSample;

    private Button pasteJsonSample;

    private Text jsonSchemaFile;

    private Text jsonSampleFile;

    private Text jsonSample;

    private Button jsonSchemaBrowse;

    private Button jsonSampleBrowse;

    /**
     * @param selection
     *            The current navigator selection.
     */
    public JsonSchemaImportPage(IStructuredSelection selection) {
        super(Messages.JsonSchemaImportPage_pageTitle, selection);
        setSpecialFolderSelectionValidator(RestConstants.REST_SPECIAL_FOLDER_KIND,
                new Status(IStatus.ERROR, RestSchemaUiPlugin.PLUGIN_ID,
                        Messages.JsonSchemaFilePage_Description));

        // Only show REST special folders in the tree viewer
        addFilter(new ViewerFilter() {
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof SpecialFolder) {
                    return RestConstants.REST_SPECIAL_FOLDER_KIND
                            .equals(((SpecialFolder) element).getKind());
                }
                return true;
            }
        });
    }

    /**
     * @see com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage#createAdvancedControls(org.eclipse.swt.widgets.Composite)
     * 
     * @param topLevel
     *            The composite to add the controls to.
     * @return The root control that was added to topLevel.
     */
    @Override
    protected Composite createAdvancedControls(Composite topLevel) {
        Composite advanced = new Composite(topLevel, SWT.NONE);
        advanced.setLayout(new GridLayout(3, false));
        importJsonSchema = new Button(advanced, SWT.RADIO);
        importJsonSchema.setLayoutData(new GridData(SWT.LEAD, SWT.TOP, false,
                false));
        importJsonSchema
                .setText(Messages.JsonSchemaImportPage_importJsonSchemaButton);
        importJsonSchema
                .setToolTipText(Messages.JsonSchemaImportPage_importJsonSchemaTooltip);
        jsonSchemaFile = new Text(advanced, SWT.BORDER);
        jsonSchemaFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));
        jsonSchemaBrowse = new Button(advanced, SWT.PUSH);
        jsonSchemaBrowse.setLayoutData(new GridData(SWT.TRAIL, SWT.CENTER,
                false, false));
        jsonSchemaBrowse
                .setText(Messages.JsonSchemaImportPage_browseJsonSchemaButton);

        importJsonSample = new Button(advanced, SWT.RADIO);
        importJsonSample.setLayoutData(new GridData(SWT.LEAD, SWT.TOP, false,
                false));
        importJsonSample
                .setText(Messages.JsonSchemaImportPage_importJsonSampleButton);
        importJsonSample
                .setToolTipText(Messages.JsonSchemaImportPage_importJsonSampleTooltip);
        jsonSampleFile = new Text(advanced, SWT.BORDER);
        jsonSampleFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));
        jsonSampleBrowse = new Button(advanced, SWT.PUSH);
        jsonSampleBrowse.setLayoutData(new GridData(SWT.TRAIL, SWT.CENTER,
                false, false));
        jsonSampleBrowse
                .setText(Messages.JsonSchemaImportPage_browseJsonSampleButton);

        pasteJsonSample = new Button(advanced, SWT.RADIO);
        pasteJsonSample.setLayoutData(new GridData(SWT.LEAD, SWT.TOP, false,
                false));
        pasteJsonSample
                .setText(Messages.JsonSchemaImportPage_pasteJsonSampleButton);
        pasteJsonSample
                .setToolTipText(Messages.JsonSchemaImportPage_pasteJsonSampleTooltip);
        jsonSample = new Text(advanced, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
        GridData jsonSampleLayout =
                new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
        jsonSampleLayout.minimumHeight = 100;
        jsonSample.setLayoutData(jsonSampleLayout);

        jsonSchemaBrowse.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                String file = selectFile();
                if (file != null) {
                    jsonSchemaFile.setText(file);
                }
            }
        });

        jsonSampleBrowse.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                String file = selectFile();
                if (file != null) {
                    jsonSampleFile.setText(file);
                }
            }
        });

        SelectionListener radioListener = new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                updateControls();
            }
        };
        importJsonSchema.addSelectionListener(radioListener);
        importJsonSample.addSelectionListener(radioListener);
        pasteJsonSample.addSelectionListener(radioListener);
        ModifyListener textListener = new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage());
            }
        };
        jsonSchemaFile.addModifyListener(textListener);
        jsonSampleFile.addModifyListener(textListener);
        jsonSample.addModifyListener(textListener);

        updateControls();

        return advanced;
    }

    /**
     * Update the control enabled states and page complete flag.
     */
    protected void updateControls() {
        jsonSchemaFile.setEnabled(importJsonSchema.getSelection());
        jsonSchemaBrowse.setEnabled(importJsonSchema.getSelection());
        jsonSampleFile.setEnabled(importJsonSample.getSelection());
        jsonSampleBrowse.setEnabled(importJsonSample.getSelection());
        jsonSample.setEnabled(pasteJsonSample.getSelection());
        setPageComplete(validatePage());
    }

    /**
     * @see com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage
     *      #validatePage()
     * 
     * @return true if the page data is valid.
     */
    @Override
    protected boolean validatePage() {
        boolean valid = super.validatePage();
        if (valid) {
            if (importJsonSchema.getSelection()
                    || importJsonSample.getSelection()
                    || pasteJsonSample.getSelection()) {
                if (importJsonSchema.getSelection()
                        && jsonSchemaFile.getText().isEmpty()) {
                    valid = false;
                    setErrorMessage(Messages.JsonSchemaImportPage_noSchemaFileError);
                } else if (importJsonSample.getSelection()
                        && jsonSampleFile.getText().isEmpty()) {
                    valid = false;
                    setErrorMessage(Messages.JsonSchemaImportPage_noSampleFileError);
                } else if (pasteJsonSample.getSelection()
                        && jsonSample.getText().isEmpty()) {
                    valid = false;
                    setErrorMessage(Messages.JsonSchemaImportPage_noSampleError);
                }
            } else {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Open a file selection dialog and return the selected file.
     * 
     * @return the selected file.
     */
    protected String selectFile() {
        FileDialog dlg = new FileDialog(getShell(), SWT.OPEN);
        return dlg.open();
    }

    /**
     * @return The selected import method.
     */
    public JsonSchemaImportMethod getImportMethod() {
        JsonSchemaImportMethod method = null;
        if (importJsonSchema.getSelection()) {
            method = JsonSchemaImportMethod.IETF_JSON_SCHEMA;
        } else if (importJsonSample.getSelection()) {
            method = JsonSchemaImportMethod.JSON_SAMPLE;
        } else if (pasteJsonSample.getSelection()) {
            method = JsonSchemaImportMethod.JSON_SAMPLE;
        }
        return method;
    }

    /**
     * @return A reader for the import source resource.
     * @throws FileNotFoundException
     *             If a file resource could not be found.
     */
    public Reader getSource() throws FileNotFoundException {
        Reader reader = null;
        if (importJsonSchema.getSelection()) {
            reader = new FileReader(jsonSchemaFile.getText());
        } else if (importJsonSample.getSelection()) {
            reader = new FileReader(jsonSampleFile.getText());
        } else if (pasteJsonSample.getSelection()) {
            reader = new StringReader(jsonSample.getText());
        }
        return reader;
    }
}
