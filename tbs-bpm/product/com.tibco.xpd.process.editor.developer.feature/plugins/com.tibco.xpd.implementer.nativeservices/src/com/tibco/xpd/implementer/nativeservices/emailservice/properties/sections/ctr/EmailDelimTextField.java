/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Text input control that will consist of a label (optional), text control and
 * a data picker browse button. If multiple selections are made in the data
 * picker then they will be added to the text control using <i><b>semi-colon
 * </b></i> as the delimeter
 * 
 * @author njpatel
 * 
 */
public class EmailDelimTextField extends EmailTextInputField {
    /*
     * Delimeter used to separate data in email parameters
     */
    protected static final String EMAIL_FIELD_DELIM = NativeServicesConsts.EMAIL_FIELD_DELIM;

    public EmailDelimTextField(XpdFormToolkit toolkit,
            Composite parent, String label, EStructuralFeature eFeature,
            IDataPP pickerProvider) {

        super(toolkit, parent, label, eFeature, pickerProvider);
    }

    @Override
    protected void handleBrowse() {
        // Get the datafields selected in the picker
        ProcessRelevantData[] fields = getDataFields();

        if (fields != null) {
            // Combine all fields with spaces in between
            String fieldStr = ""; //$NON-NLS-1$

            for (ProcessRelevantData field : fields) {

                // If string has content then add the delimeter
                if (fieldStr.length() > 0) {
                    fieldStr += EMAIL_FIELD_DELIM;
                }

                fieldStr += PROCESS_FIELD_DELIM + field.getName()
                        + PROCESS_FIELD_DELIM;
            }

            // Add the string to the text field
            if (fieldStr.length() > 0) {
                /*
                 * If the content of the text control has already got email
                 * field delimeter at the end then just concat the field str to
                 * it
                 */
                String content = textControl.getText();

                if (content.length() > EMAIL_FIELD_DELIM.length()) {
                    if (!content.substring(
                            content.length() - EMAIL_FIELD_DELIM.length())
                            .equals(EMAIL_FIELD_DELIM)) {
                        content += EMAIL_FIELD_DELIM;
                    }
                }

                // Combine the strings and set it to the text control
                content += fieldStr;
                textControl.setText(content);
            }
        }
    }

}
