/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.xpdl2.edit.ui.properties.general;

import java.text.DateFormat;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.Documentation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.PackageHeader;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.contentassist.LanguageContentProposalProvider;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.edit.util.FieldAssistUtil;
import com.tibco.xpd.xpdl2.edit.util.LocaleUtils;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This is the page where the type of object to create is selected.
 */
public class PackageInformationSection extends
        AbstractFilteredTransactionalSection {

    private Text authorText;

    private Text createdText;

    private Text descriptionText;

    private Text documentationText;

    private Text languageText;

    /**
     * Pass in the selection.
     */
    public PackageInformationSection() {
        super(Xpdl2Package.eINSTANCE.getPackage());
        setMinimumHeight(SWT.DEFAULT);
    }

    /**
     * 
     * 
     * @param parent
     */
    @Override
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite composite = toolkit.createComposite(parent);
        GridLayout layout = new GridLayout(2, false);
        layout.marginLeft = 3; // line up with name controls.
        composite.setLayout(layout);

        Label label;
        label =
                toolkit.createLabel(composite,
                        Messages.PackageInformationSection_author_label);
        label.setLayoutData(new GridData());

        authorText =
                toolkit.createText(composite, null, Xpdl2Package.eINSTANCE
                        .getRedefinableHeader_Author());
        authorText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(authorText);

        label =
                toolkit.createLabel(composite,
                        Messages.PackageInformationSection_created_label);
        label.setLayoutData(new GridData());
        createdText =
                toolkit.createText(composite, null, Xpdl2Package.eINSTANCE
                        .getPackageHeader_Created());
        createdText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(createdText);

        label =
                toolkit.createLabel(composite,
                        Messages.PackageInformationSection_desc_label);
        label.setLayoutData(new GridData());
        descriptionText =
                toolkit.createText(composite, null, Xpdl2Package.eINSTANCE
                        .getDescription_Value());
        descriptionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(descriptionText);

        label =
                toolkit.createLabel(composite,
                        Messages.PackageInformationSection_docLocation_label);
        label.setLayoutData(new GridData());
        documentationText = toolkit.createText(composite, ""); //$NON-NLS-1$

        documentationText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(documentationText);


        // create anonymous text content adaptor that forces the selection of a
        // content assist to
        // overwrite the whole text box clearing any previous data rather than
        // concatenating it
        TextContentAdapter textContentAdapter = new TextContentAdapter() {
            @Override
            public void insertControlContents(Control control, String text,
                    int cursorPosition) {
                if (control instanceof Text) {
                    ((Text) control).setText(text);
                } else {
                    super.insertControlContents(control, text, cursorPosition);
                }
            }
        };

        label =
                toolkit.createLabel(composite,
                        Messages.PackageInformationSection_language_label);
        label.setLayoutData(new GridData());

        languageText = toolkit.createText(composite, ""); //$NON-NLS-1$
        languageText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(languageText);
        ContentAssistCommandAdapter contentAssistCommandAdapter =
                new ContentAssistCommandAdapter(languageText,
                        textContentAdapter,
                        new LanguageContentProposalProvider(LocaleUtils
                                .getISOCodes()), null, FieldAssistUtil
                                .getAlphaNumericChars(), true);

        return composite;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#doGetCommand(java.lang.Object)
     */
    @Override
    public Command doGetCommand(Object obj) {
        CompoundCommand command = new CompoundCommand();
        Package xpdlPackage = (Package) getInput();
        RedefinableHeader redefinableHeader =
                xpdlPackage.getRedefinableHeader();
        if (redefinableHeader == null) {
            redefinableHeader =
                    Xpdl2Factory.eINSTANCE.createRedefinableHeader();
            command.append(AddCommand.create(getEditingDomain(),
                    getInput(),
                    Xpdl2Package.eINSTANCE.getRedefinableHeader(),
                    redefinableHeader));
        }
        PackageHeader packageHeader = xpdlPackage.getPackageHeader();
        if (packageHeader == null) {
            packageHeader = Xpdl2Factory.eINSTANCE.createPackageHeader();
            command.append(AddCommand.create(getEditingDomain(),
                    getInput(),
                    Xpdl2Package.eINSTANCE.getPackageHeader(),
                    packageHeader));
        }
        Widget widget = (Widget) obj;
        EStructuralFeature feat =
                (EStructuralFeature) widget
                        .getData(XpdFormToolkit.FEATURE_DATA);
        if (obj == authorText) {
            if (redefinableHeader.eContainer() == null) {
                redefinableHeader.setAuthor(authorText.getText());
            } else {
                command.append(SetCommand.create(getEditingDomain(),
                        redefinableHeader,
                        feat,
                        authorText.getText()));
                command
                        .setLabel(Messages.PackageInformationSection_SetAuthor_menu);
            }
        } else if (obj == createdText) {
            if (packageHeader.eContainer() == null) {
                packageHeader.setCreated(createdText.getText());
            } else {
                command.append(SetCommand.create(getEditingDomain(),
                        packageHeader,
                        feat,
                        createdText.getText()));
                command
                        .setLabel(Messages.PackageInformationSection_SetCreated_menu);
            }
        } else if (obj == descriptionText) {
            Description description =
                    Xpdl2Factory.eINSTANCE.createDescription();
            description.setValue(descriptionText.getText());
            if (packageHeader.eContainer() == null) {
                packageHeader.setDescription(description);
            } else {
                command.append(SetCommand.create(getEditingDomain(),
                        packageHeader,
                        Xpdl2Package.eINSTANCE
                                .getDescribedElement_Description(),
                        description));
                command
                        .setLabel(Messages.PackageInformationSection_SetDescription_menu);
            }
        } else if (obj == documentationText) {
            Documentation doc = Xpdl2Factory.eINSTANCE.createDocumentation();
            doc.setValue(documentationText.getText());

            if (packageHeader.eContainer() == null) {
                packageHeader.setDocumentation(doc);
            } else {
                command.append(SetCommand
                        .create(getEditingDomain(),
                                packageHeader,
                                Xpdl2Package.eINSTANCE
                                        .getPackageHeader_Documentation(),
                                doc));
                command
                        .setLabel(Messages.PackageInformationSection_SetDocumentation_menu);
            }

        } else if (obj == languageText) {
            String tempLocaleIsaCode =
                    LocaleUtils.getLocaleISOFromDisplayName(languageText
                            .getText());
            // if can't find it we cannot store the isa code at moment else the
            // text will change to the user
            // so we store the 'faulty' input and let user resolve it
            if (tempLocaleIsaCode == null || tempLocaleIsaCode.length() == 0) {
                tempLocaleIsaCode = languageText.getText();
            }
            if (packageHeader.eContainer() == null) {
                Xpdl2ModelUtil.setOtherAttribute(packageHeader,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Language(),
                        tempLocaleIsaCode);
            } else {
                command.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(getEditingDomain(),
                                packageHeader,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Language(),
                                tempLocaleIsaCode));
                command
                        .setLabel(Messages.PackageInformationSection_SetLanguage_menu);
            }
        } else {
            return null;
        }
        return command;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        if (getInput() == null) {
            return;
        }
        Package xpdlPackage = (Package) getInput();
        RedefinableHeader redefinableHeader =
                xpdlPackage.getRedefinableHeader();

        PackageHeader packageHeader = xpdlPackage.getPackageHeader();
        // updateLanguage(packageHeader);

        if (redefinableHeader != null) {
            updateText(authorText, redefinableHeader.getAuthor());
            // updateCostUnit(packageHeader);

        } else {

            updateText(authorText, ""); //$NON-NLS-1$
        }
        // updateCCombo(publicationStatusCombo, statusText);

        if (packageHeader != null) {
            updateText(createdText,
                    LocaleUtils.getLocalisedDateTime(packageHeader.getCreated(), DateFormat.FULL, DateFormat.MEDIUM));
            if (createdText.getText().length() == 0
                    && packageHeader.getCreated() != null) {
                updateText(createdText, packageHeader.getCreated());
            }
            if (packageHeader.getDescription() != null) {
                updateText(descriptionText, packageHeader.getDescription()
                        .getValue());
            } else {
                updateText(descriptionText, ""); //$NON-NLS-1$
            }

            String language =
                    (String) Xpdl2ModelUtil.getOtherAttribute(packageHeader,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Language());
            if (language == null) {
                language = ""; //$NON-NLS-1$
            }
            String tempDisplayName =
                    LocaleUtils.getDisplayNameFromISOCode(language);
            if (tempDisplayName == null || tempDisplayName.length() == 0) {
                tempDisplayName = language;
            }
            updateText(languageText, language);

            if (packageHeader.getDocumentation() != null) {
                updateText(documentationText, packageHeader.getDocumentation()
                        .getValue());
            } else {
                updateText(documentationText, ""); //$NON-NLS-1$
            }
        } else {
            updateText(descriptionText, ""); //$NON-NLS-1$
            updateText(languageText, ""); //$NON-NLS-1$
            updateText(documentationText, ""); //$NON-NLS-1$
        }

    }
}
