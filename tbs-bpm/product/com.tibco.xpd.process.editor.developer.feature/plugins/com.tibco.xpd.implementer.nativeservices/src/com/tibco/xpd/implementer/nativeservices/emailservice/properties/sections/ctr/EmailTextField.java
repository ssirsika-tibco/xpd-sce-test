/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr;

import java.io.File;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;

import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Text input control that will consist of a label (optional), text control and
 * a file picker browse button. If multiple selections are made in the file
 * picker then they will be added to the text control using a semi-colon as the
 * delimeter
 * 
 * @author njpatel
 * 
 */
public class EmailTextField extends EmailDelimTextField {

    private final IFDProvider fileDialogProvider;

    /**
     * This interface will be implemented by the file dialog provider
     */
    public interface IFDProvider {
        public FileDialog getFileDialog();
    }

    public EmailTextField(XpdFormToolkit toolkit, Composite parent,
            String label, EStructuralFeature eFeature,
            IFDProvider fileDialogProvider) {
        super(toolkit, parent, label, eFeature, null);
        this.fileDialogProvider = fileDialogProvider;
    }

    @Override
    protected void doInit() {
        // Update tooltip message
        browseToolTipText = Messages.EmailFileTextInputField_BrowseSelectFileToolTip;
    }

    @Override
    protected void handleBrowse() {
        if (fileDialogProvider != null) {
            FileDialog fDialog = fileDialogProvider.getFileDialog();

            if (fDialog != null) {
                if (fDialog.open() != null) {
                    String[] fileNames = fDialog.getFileNames();
                    String filterPath = fDialog.getFilterPath()
                            + File.separator;

                    if (fileNames != null) {
                        String fieldStr = "";   //$NON-NLS-1$

                        for (String name : fileNames) {
                            if (fieldStr.length() > 0) {
                                fieldStr += EMAIL_FIELD_DELIM;
                            }

                            fieldStr += filterPath + name;
                        }

                        // Combine the field name with the content of the text
                        // control
                        String value = textControl.getText();

                        // If the current value in the control has not got the
                        // field delimeter then insert it
                        if (value != null
                                && value.length() > EMAIL_FIELD_DELIM.length()
                                && !value.substring(
                                        value.length()
                                                - EMAIL_FIELD_DELIM.length())
                                        .equals(EMAIL_FIELD_DELIM)) {
                            value += EMAIL_FIELD_DELIM;
                        }

                        textControl.setText(value != null ? value += fieldStr
                                : fieldStr);
                    }
                }
            }
        }
    }

}
