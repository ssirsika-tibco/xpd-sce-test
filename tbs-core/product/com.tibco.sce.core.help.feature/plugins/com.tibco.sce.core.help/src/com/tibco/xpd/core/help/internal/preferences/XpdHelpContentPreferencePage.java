/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.core.help.internal.preferences;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.xpd.core.help.CoreHelpActivator;
import com.tibco.xpd.core.help.internal.Messages;

/**
 * Preference page for help content.
 * 
 * @author jarciuch
 * @since 5 Aug 2014
 */
public class XpdHelpContentPreferencePage extends FieldEditorPreferencePage
        implements IWorkbenchPreferencePage {

    private final static class DirectoryFieldEditorExtension extends
            DirectoryFieldEditor {
        private DirectoryFieldEditorExtension(String name, String labelText,
                Composite parent) {
            super();
            init(name, labelText);
            setErrorMessage(JFaceResources
                    .getString("DirectoryFieldEditor.errorMessage"));//$NON-NLS-1$
            setChangeButtonText(JFaceResources.getString("openBrowse"));//$NON-NLS-1$
            setValidateStrategy(VALIDATE_ON_KEY_STROKE);
            createControl(parent);
        }

        @Override
        protected boolean doCheckState() {
            String fileName = getTextControl().getText();
            fileName = fileName.trim();
            if (fileName.length() == 0 && isEmptyStringAllowed()) {
                return true;
            }
            File file = new File(fileName);
            if (file.exists() && file.isFile()) {
                setErrorMessage(Messages.XpdHelpContentPreferencePage_existingFileError_message);
                return false;
            }
            try {
                file.getCanonicalPath();
            } catch (IOException e) {
                setErrorMessage(Messages.XpdHelpContentPreferencePage_invalidLocation_message);
                return false;
            }
            setErrorMessage(null);
            return true;
        }

        /**
         * @see org.eclipse.jface.preference.FieldEditor#getLabelControl()
         * 
         * @return
         */
        @Override
        public Label getLabelControl(Composite parent) {
            Label l = new Label(parent, SWT.NONE);
            GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.TOP)
                    .hint(1, 1).applyTo(l);
            l.setVisible(false);

            Link fLocationLink = new Link(parent, SWT.NONE);
            fLocationLink.setText("<a>" + getLabelText() + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$

            fLocationLink.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    try {
                        String path = getTextControl().getText().trim();
                        File f = new File(path);
                        if (f.exists())
                            Program.launch(f.getCanonicalPath());
                        else
                            MessageDialog
                                    .openWarning(getShell(),
                                            Messages.XpdHelpContentPreferencePage_openingLocation_message,
                                            Messages.XpdHelpContentPreferencePage_folderDoesntExist_message);
                    } catch (Exception ex) {
                        MessageDialog
                                .openWarning(getShell(),
                                        Messages.XpdHelpContentPreferencePage_openingLocation_message,
                                        Messages.XpdHelpContentPreferencePage_locationCantBeOpened_message);
                    }
                }
            });
            return l;
        }
    }

    private XpdHelpContentControl contentControl;

    public XpdHelpContentPreferencePage() {
        super(GRID);
        setPreferenceStore(CoreHelpActivator.getDefault().getPreferenceStore());
    }

    @Override
    public void init(IWorkbench workbench) {
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite content = (Composite) super.createContents(parent);
        return content;
    }

    @Override
    protected void createFieldEditors() {
        RadioGroupFieldEditor accessModeEditor =
                new RadioGroupFieldEditor(
                        XpdHelpPreferences.P_DOC_ACCESS_MODE,
                        Messages.XpdHelpContentPreferencePage_contentAccessStrategy_label,
                        1, XpdHelpPreferences.AccessStrategy.getAsArray(),
                        getFieldEditorParent());
        addField(accessModeEditor);

        contentControl =
                new XpdHelpContentControl(getFieldEditorParent(), SWT.NONE);
        GridDataFactory.fillDefaults().span(3, 1).grab(true, true)
                .applyTo(contentControl);

        Group group = new Group(getFieldEditorParent(), SWT.NONE);
        GridDataFactory.fillDefaults().span(3, 1).grab(true, false)
                .applyTo(group);
        group.setText(Messages.XpdHelpContentPreferencePage_baseOfflineDocFolder_label);
        DirectoryFieldEditor baseOfflineDirEditor =
                new DirectoryFieldEditorExtension(
                        XpdHelpPreferences.P_BASE_OFFLINE_FOLDER,
                        Messages.XpdHelpContentPreferencePage_location_label,
                        group);
        GridLayoutFactory.fillDefaults().numColumns(4).applyTo(group);
        addField(baseOfflineDirEditor);
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performApply()
     * 
     */
    @Override
    protected void performApply() {
        super.performApply();
        if (contentControl != null) {
            contentControl.refreshHelpContentsViewer();
        }
    }
}
