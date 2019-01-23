/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections;

import java.util.Arrays;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailFactory;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailPackage;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FilesType;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.AbstractEmailSection;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailDelimTextField;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailIF;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailTextField;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailIF.IDataPP;
import com.tibco.xpd.implementer.nativeservices.emailservice.properties.sections.ctr.EmailTextField.IFDProvider;
import com.tibco.xpd.implementer.nativeservices.emailservice.utilities.EmailExtUtil;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * Email tab's Attachment section.
 * 
 * @author njpatel
 * 
 */
public class EmailAttachmentsSection extends AbstractEmailSection implements
        IDataPP {

    private static final String EMAIL_FIELD_DELIM = NativeServicesConsts.EMAIL_FIELD_DELIM;

    private EmailDelimTextField fieldAttachment;

    private EmailTextField fileAttachment;

    private Shell shell;

    public EmailAttachmentsSection(EClass eclass) {
        super(eclass);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        ScrolledComposite scrolledComposite = toolkit.createScrolledComposite(
                parent, SWT.V_SCROLL | SWT.H_SCROLL);

        shell = parent.getShell();

        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);

        Composite container = toolkit.createComposite(scrolledComposite);
        scrolledComposite.setContent(container);
        container.setLayout(new GridLayout(3, false));

        // Field content attachment
        fieldAttachment = new EmailDelimTextField(toolkit, container,
                Messages.EmailAttachmentsSection_FieldContentsLabel,
                EmailPackage.eINSTANCE.getAttachmentType_Value(), this);
        manageControl(fieldAttachment.getTextControl());

        // File content attachment
        fileAttachment = new EmailTextField(toolkit, container,
                Messages.EmailAttachmentsSection_FilesLabel,
                EmailPackage.eINSTANCE.getAttachmentType_Files(),
                new IFDProvider() {
                    private FileDialog fDialog = null;

                    public FileDialog getFileDialog() {

                        if (fDialog == null) {
                            fDialog = new FileDialog(shell, SWT.MULTI);
                        }
                        return fDialog;
                    }

                });

        manageControl(fileAttachment.getTextControl());

        scrolledComposite.setMinSize(container.computeSize(SWT.DEFAULT,
                SWT.DEFAULT));

        return scrolledComposite;
    }

    @Override
    public Command doGetCommand(Object obj) {
        CompoundCommand cmd = null;
        TaskService service = getTaskServiceInput();

        if (service != null) {

            EStructuralFeature feature = null;
            Object value = null;
            Widget widget = (Widget) obj;

            if (widget != null) {
                if (widget.getData() instanceof EmailIF) {
                    EmailIF inputField = (EmailIF) widget.getData();
                    feature = inputField.getEFeature();
                    String text = inputField.getText();

                    // Get value to set
                    if (text != null && text.length() > 0) {
                        // If this is file attachments then create the FilesType
                        // model
                        if (feature == EmailPackage.eINSTANCE
                                .getAttachmentType_Files()) {
                            FilesType files = EmailFactory.eINSTANCE
                                    .createFilesType();
                            String[] fileNames = text.split(EMAIL_FIELD_DELIM);

                            if (fileNames != null && fileNames.length > 0) {
                                files.getFile()
                                        .addAll(Arrays.asList(fileNames));
                            }

                            value = files;
                        } else {
                            value = text;
                        }
                    }
                }
            }

            if (feature != null) {
                cmd = new CompoundCommand();
                EditingDomain ed = getEditingDomain();
                EmailType email = EmailExtUtil.getEmailExtension(service);

                // If the email extension hasn't been added then do so
                if (email == null) {
                    email = EmailExtUtil.createEmailExtension(ed, cmd, service);

                }

                if (email != null) {
                    // Get attachment
                    AttachmentType attachment = email.getAttachment();

                    // If attachment is not present then create it
                    if (attachment == null) {
                        attachment = EmailExtUtil.createAttachment(ed, cmd,
                                email);

                    }

                    if (attachment != null) {
                        // Set the feature with the value given. If the
                        // value is
                        // blank or null the feature will be unset
                        cmd.append(SetCommand
                                .create(ed, attachment, feature,
                                        (value != null ? value
                                                : SetCommand.UNSET_VALUE)));
                    }
                }
            }
        }

        return cmd;
    }

    @Override
    protected void doRefresh() {
        boolean clearAttachment = true;
        TaskService service = getTaskServiceInput();

        if (service != null) {
            EmailType email = EmailExtUtil.getEmailExtension(service);

            if (email != null) {
                AttachmentType attachment = email.getAttachment();

                if (attachment != null) {
                    clearAttachment = false;

                    // Update the value
                    fieldAttachment.setText(attachment.getValue());

                    // Update the file attachments
                    if (attachment.getFiles() != null) {
                        EList fileList = attachment.getFiles().getFile();

                        if (fileList != null) {
                            String value = ""; //$NON-NLS-1$
                            for (Object fileName : fileList) {
                                if (value.length() > 0) {
                                    value += EMAIL_FIELD_DELIM;
                                }
                                value += fileName;
                            }
                            fileAttachment.setText(value);
                        }
                    } else {
                        fileAttachment.setText(""); //$NON-NLS-1$
                    }
                }
            }
        }

        if (clearAttachment) {
            // Clear all values
            fileAttachment.setText(""); //$NON-NLS-1$
            fieldAttachment.setText(""); //$NON-NLS-1$
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.nativeservices.properties.sections.controls
     * .EmailInputField.IDataPickerProvider#getDataPicker()
     */
    public DataFilterPicker getDataPicker(boolean multi) {
        DataFilterPicker dataPicker = null;
        Activity activity = getActivityInput();
        if (activity != null) {
            dataPicker = new DataFilterPicker(shell,
                    DataPickerType.DATAFIELD_FORMALPARAMETER, multi);
            dataPicker.setScope(activity);
        }
        return dataPicker;
    }

}
