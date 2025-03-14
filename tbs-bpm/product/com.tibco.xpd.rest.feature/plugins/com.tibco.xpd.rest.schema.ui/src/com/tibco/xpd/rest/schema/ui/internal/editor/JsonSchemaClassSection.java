/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rest.schema.ui.internal.Messages;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaImage;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * The property sheet for types selected in the JSON schema editor. This allows
 * modification of type names and, for the root type, allows the Package
 * namespace to be changed.
 * 
 * @author nwilson
 * @since 20 Jan 2015
 */
public class JsonSchemaClassSection extends AbstractTransactionalSection {

    private Text className;

    private CLabel classNameLabel;

    private Button isInternalButton; // a.k.a. Private

    private final static JsonSchemaUtil JSON_SCHEMA_UTIL = new JsonSchemaUtil();

    /**
     * Updates the contents of a Text control only if it has changed. This
     * prevents loss of cursor position on edit.
     * 
     * @param text
     *            The text control to update.
     * @param value
     *            The value to set.
     */
    private void setIfChanged(Text text, String value) {
        if (!text.getText().equals(value)) {
            text.setText(value);
        }
        /*
         * XPD-7715: verify the name on UI itself
         */
        verifyName(value);

    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input instanceof Class) {
            Class cls = (Class) input;
            setIfChanged(className, cls.getName());
            isInternalButton.setSelection(!JSON_SCHEMA_UTIL.isRootClass(cls));
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     *            The parent composite.
     * @param toolkit
     *            The form toolkit used to create controls.
     * @return The root control.
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        root.setLayout(layout);

        classNameLabel =
                toolkit.createCLabel(root,
                        Messages.JsonSchemaEditorDetails_classNameLabel);
        classNameLabel.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER, false,
                false));
        className = toolkit.createText(root, ""); //$NON-NLS-1$
        className
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        className
                .setToolTipText(Messages.JsonSchemaEditorDetails_classNameTooltip);
        manageControl(className);

        // checkbox labels needs indentation to align to text labels.
        GridDataFactory checkboxLabelGDF = GridDataFactory.swtDefaults().indent(/* hIndent */3, /* vIndent */0);

        Label internalLabel = toolkit.createLabel(root, Messages.JsonSchemaClassSection_Internal_label);
        internalLabel.setToolTipText(Messages.JsonSchemaClassSection_Internal_tooltip);
        internalLabel.setLayoutData(checkboxLabelGDF.create());
        isInternalButton = toolkit.createButton(root, "", SWT.CHECK); //$NON-NLS-1$
        isInternalButton.setToolTipText(Messages.JsonSchemaClassSection_Internal_tooltip);
        isInternalButton.setLayoutData(new GridData(SWT.LEAD, SWT.TOP, false, false));
        manageControl(isInternalButton);

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     *            The triggering object.
     * @return The triggered command.
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        EObject input = getInput();
        if (input instanceof Class) {
            final Class cls = (Class) input;
            if (obj == className) {
                CompoundCommand cc =
                        new CompoundCommand(
                                Messages.JsonSchemaEditorDetails_classNameCommand);
                String name = className.getText();
                cc.append(new SetCommand(getEditingDomain(), cls,
                        UMLPackage.eINSTANCE.getNamedElement_Name(), name));
                cmd = cc;
            }
            if (obj == isInternalButton) {
                final boolean isInternal = isInternalButton.getSelection();
                String cmdLabel = isInternal ? Messages.JsonSchemaClassSection_SetAsInternal_label
                        : Messages.JsonSchemaClassSection_SetAsPublic_label;
                CompoundCommand cc =
                        new CompoundCommand(
                                cmdLabel);
                cc.append(new RecordingCommand((TransactionalEditingDomain) getEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        JSON_SCHEMA_UTIL.setClassAsRoot(cls, !isInternal);
                    }
                });
                cmd = cc;
            }
        }
        return cmd;
    }

    /**
     * Verify that the specified name text follows all naming conventions.
     * 
     * @param nameText
     */
    private void verifyName(String nameText) {

        String errorMsg = null;

        if ((nameText == null || nameText.length() == 0)) {
            /*
             * Empty Type name not allowed
             */
            errorMsg =
                    Messages.JsonSchemaClassSection_EmptyTypeNotAllowedError_msg;

        } else {

            EObject inputContainer = getInputContainer();

            if (inputContainer instanceof Package) {
                /*
                 * Duplicate Type name not allowed
                 */

                if (JsonSchemaUtil.isDuplicateSchemaTypeName(getInput(),
                        (Package) inputContainer,
                        nameText)) {
                    errorMsg =
                            Messages.JsonSchemaClassSection_DuplicateTypeNotAllowedError_msg;
                }

            }
        }

        if (errorMsg != null) {
            /*
             * show the error icon and tooltip
             */
            classNameLabel.setImage(RestSchemaUiPlugin.getDefault()
                    .getImage(RestSchemaImage.IMG_ERROR));

            classNameLabel.setToolTipText(errorMsg);
            classNameLabel.getParent().layout(true);

        } else {
            /*
             * remove the error Icon and tooltip
             */
            classNameLabel.setImage(null);
            classNameLabel.setToolTipText(""); //$NON-NLS-1$
            classNameLabel.getParent().layout(true);
        }
    }
}
