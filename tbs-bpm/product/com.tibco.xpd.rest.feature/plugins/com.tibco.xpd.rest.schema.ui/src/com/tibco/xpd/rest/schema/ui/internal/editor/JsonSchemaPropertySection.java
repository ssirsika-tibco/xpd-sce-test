/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
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
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rest.schema.ui.JsonSchemaTypePicker;
import com.tibco.xpd.rest.schema.ui.internal.Messages;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaImage;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * The property sheet for Property elements selected in the JSON schema
 * editor.This allows for modification of the Property name, type and
 * mandatory/array flags.
 * 
 * @author nwilson
 * @since 20 Jan 2015
 */
public class JsonSchemaPropertySection extends AbstractTransactionalSection {

    private Text propertyName;

    private CLabel propertyNameLabel;

    private CLabel propertyTypeLabel;

    private JsonSchemaTypePicker propertyType;

    private Button isMandatory;

    private Button isArray;

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#resollveInput(java.lang.Object)
     * 
     * @param object
     * @return
     */
    @Override
    protected EObject resollveInput(Object object) {
        EObject input = null;
        if (object instanceof UmlTreePropertyNode) {
            input = ((UmlTreePropertyNode) object).getItem();
        } else if (object instanceof Property) {
            input = (Property) object;
        }
        return input;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input instanceof Property) {
            Property property = (Property) input;
            setIfChanged(propertyName, property.getName());
            Type type = property.getType();
            Classifier classifier = null;
            if (type instanceof Classifier) {
                classifier = (Classifier) type;
            }

            handlePropertyTypeRefresh(property, type);

            propertyType.setElement(property);
            propertyType.setValue(classifier);
            isMandatory.setSelection(property.getLower() == 1);
            isArray.setSelection(property.getUpper() == LiteralUnlimitedNatural.UNLIMITED);
        }
    }

    /**
     * Handle refresh of property type label.
     * 
     * @param property
     * @param type
     */
    private void handlePropertyTypeRefresh(Property property, Type type) {
        boolean needErrorOnPropertyTypeLabel = false;

        String errorMessageOnPropertyTypeLabel = new String();

        if (null == type) {

            needErrorOnPropertyTypeLabel = true;

            errorMessageOnPropertyTypeLabel =
                    Messages.JsonSchemaPropertySection_PropertyTypeNotSet_Error_msg;

        } else if (type instanceof Class) {

            Class propertyClass = (Class) type;

            URI uri = ((InternalEObject) propertyClass).eProxyURI();

            if (uri == null) {

                uri = EcoreUtil.getURI(propertyClass);
            }

            if (uri != null) {

                Collection<IndexerItem> results =
                        JsonSchemaUtil.getJSonSchemaIndexerItemList(uri);

                int count = results.size();

                if (count == 0) {

                    needErrorOnPropertyTypeLabel = true;

                    errorMessageOnPropertyTypeLabel =
                            Messages.JsonSchemaPropertySection_InvalidReference_Error_msg;

                } else if (count == 1) {

                    IndexerItem ii = results.iterator().next();

                    IProject project = WorkingCopyUtil.getProjectFor(property);

                    String itemProjectName =
                            ii.get(IndexerServiceImpl.ATTRIBUTE_PROJECT);

                    IProject itemProject =
                            project.getWorkspace().getRoot()
                                    .getProject(itemProjectName);

                    /*
                     * If IProject is the not same or is not in
                     * ProjectUtil.getReferencedProjectsHierarchy then throw an
                     * error as we have a missing project reference.
                     */

                    if (!project.equals(itemProject)) {

                        Set<IProject> projects = new HashSet<>();

                        ProjectUtil.getReferencedProjectsHierarchy(project,
                                projects);

                        if (!projects.contains(itemProject)) {

                            needErrorOnPropertyTypeLabel = true;

                            errorMessageOnPropertyTypeLabel =
                                    String.format(Messages.JsonSchemaPropertySection_MissingProjectReference_Error_msg,
                                            itemProjectName,
                                            property.getName(),
                                            ii.getName());
                        }
                    }
                }
            }
        }

        setCLabelIcon(propertyTypeLabel,
                needErrorOnPropertyTypeLabel ? RestSchemaUiPlugin.getDefault()
                        .getImage(RestSchemaImage.IMG_ERROR) : null,
                errorMessageOnPropertyTypeLabel);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        root.setLayout(layout);

        propertyNameLabel =
                toolkit.createCLabel(root,
                        Messages.JsonSchemaEditorDetails_propertyNameLabel);
        propertyNameLabel.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER,
                false, false));
        propertyName = toolkit.createText(root, ""); //$NON-NLS-1$
        propertyName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));
        propertyName
                .setToolTipText(Messages.JsonSchemaEditorDetails_propertyNameTooltip);

        propertyTypeLabel =
                toolkit.createCLabel(root,
                        Messages.JsonSchemaEditorDetails_propertyTypeLabel);
        propertyTypeLabel.setLayoutData(new GridData(SWT.LEAD, SWT.CENTER,
                false, false));
        propertyType =
                new JsonSchemaTypePicker(root, SWT.NONE, toolkit,
                        getEditingDomain());
        propertyType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));
        propertyType.setLabelProvider(new UmlJsonSchemaLabelProvider());

        // checkbox labels needs indentation to align to text labels.
        GridDataFactory checkboxLabelGDF = GridDataFactory.swtDefaults().indent(/* hIndent */3, /* vIndent */0);

        Label isMandatoryLabel =
                toolkit.createLabel(root,
                        Messages.JsonSchemaEditorDetails_mandatoryLabel);
        isMandatoryLabel.setLayoutData(checkboxLabelGDF.create());
        isMandatory = toolkit.createButton(root, "", SWT.CHECK); //$NON-NLS-1$
        isMandatory
                .setLayoutData(new GridData(SWT.LEAD, SWT.TOP, false, false));

        Label isArrayLabel =
                toolkit.createLabel(root,
                        Messages.JsonSchemaEditorDetails_arrayLabel);
        isArrayLabel.setLayoutData(checkboxLabelGDF.create());
        isArray = toolkit.createButton(root, "", SWT.CHECK); //$NON-NLS-1$
        isArray.setLayoutData(new GridData(SWT.LEAD, SWT.TOP, false, false));

        manageControl(propertyName);
        manageControl(isMandatory);
        manageControl(isArray);

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     *            The object triggering the command.
     * @return The triggered command.
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        EObject input = getInput();
        if (input instanceof Property) {
            if (obj == propertyName) {
                CompoundCommand cc =
                        new CompoundCommand(
                                Messages.JsonSchemaEditorDetails_propertyNameCommand);
                String name = propertyName.getText();
                cc.append(new SetCommand(getEditingDomain(), input,
                        UMLPackage.eINSTANCE.getNamedElement_Name(), name));
                cmd = cc;
            } else if (obj == isMandatory) {
                boolean selected = isMandatory.getSelection();
                String message =
                        selected ? Messages.JsonSchemaEditorDetails_setMandatoryCommand
                                : Messages.JsonSchemaEditorDetails_unsetMandatoryCommand;
                int value = selected ? 1 : 0;
                CompoundCommand cc = new CompoundCommand(message);
                cc.append(new SetCommand(getEditingDomain(), input,
                        UMLPackage.eINSTANCE.getMultiplicityElement_Lower(),
                        value));
                cmd = cc;
            } else if (obj == isArray) {
                boolean selected = isArray.getSelection();
                String message =
                        selected ? Messages.JsonSchemaEditorDetails_setArrayCommand
                                : Messages.JsonSchemaEditorDetails_unsetArrayCommand;
                int value = selected ? LiteralUnlimitedNatural.UNLIMITED : 1;
                CompoundCommand cc = new CompoundCommand(message);
                cc.append(new SetCommand(getEditingDomain(), input,
                        UMLPackage.eINSTANCE.getMultiplicityElement_Upper(),
                        value));
                cmd = cc;
            }
        }
        return cmd;
    }

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
     * Verify that the specified name text follows all naming conventions.
     * 
     * @param nameText
     */
    private void verifyName(String nameText) {

        String errorMsg = null;

        if ((nameText == null || nameText.length() == 0)) {
            /*
             * Empty Property name not allowed
             */
            errorMsg =
                    Messages.JsonSchemaPropertySection_EmptyPropertyNameNotAllowedError_msg;

        } else if (!JsonSchemaUtil.isValidPropertyName(nameText)) {
            /*
             * Check for invalid property names
             */
            errorMsg =
                    Messages.JsonSchemaPropertySection_PropertyNameInvalidError_msg;

        } else {
            /*
             * check for duplicate property names
             */
            EObject inputContainer = getInputContainer();

            if (inputContainer instanceof Class) {

                if (JsonSchemaUtil.isDuplicatePropertyName(getInput(),
                        (Class) inputContainer,
                        nameText)) {
                    errorMsg =
                            Messages.JsonSchemaPropertySection_DuplicatePropertyNameError_msg;
                }
            }
        }

        /*
         * Set/unset the error icon and tooltip.
         */
        setCLabelIcon(propertyNameLabel,
                (errorMsg != null) ? RestSchemaUiPlugin.getDefault()
                        .getImage(RestSchemaImage.IMG_ERROR) : null,
                errorMsg != null ? errorMsg : ""); //$NON-NLS-1$
    }
}
