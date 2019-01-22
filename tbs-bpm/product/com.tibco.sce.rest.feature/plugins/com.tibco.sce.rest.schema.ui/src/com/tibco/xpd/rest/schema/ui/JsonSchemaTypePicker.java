/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rest.schema.ui.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;

/**
 * A custom picker class to allow the selection of Classes and Primitive Types
 * from a JSON Schema.
 * 
 * @author nwilson
 * @since 19 Jan 2015
 */
public class JsonSchemaTypePicker extends AbstractPickerControl<Classifier> {

    /**
     * Picker extension ID.
     */
    private static final String PICKER_EXTENSION_ID =
            "com.tibco.xpd.rest.schema.json.type.picker"; //$NON-NLS-1$

    /**
     * Picker extension ID.
     */
    private static final String PICKER_PRIMITIVE_ID =
            "com.tibco.xpd.rest.schema.json.primitive.picker"; //$NON-NLS-1$

    /**
     * The element we are picking the type for.
     */
    private TypedElement element;

    /**
     * @param parent
     *            The parent composite for this picker.
     * @param style
     *            The style for this picker composite.
     * @param toolkit
     *            The toolkit to use to create controls.
     * @param editingDomain
     *            The editing domain for the field.
     */
    public JsonSchemaTypePicker(Composite parent, int style,
            XpdFormToolkit toolkit, EditingDomain editingDomain) {
        super(parent, style, toolkit, editingDomain);
    }

    /**
     * @param element
     *            The element we are picking the type for.
     */
    public void setElement(TypedElement element) {
        this.element = element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Classifier value) {
        super.setValue(value);
        /*
         * If this value is from a resource in the workspace then enable the
         * hyperlink, otherwise don't as user won't be able to 'go to' the
         * resource
         */
        setHyperlinkActive(value != null && !value.eIsProxy()
                && value.eResource() != null
                && value.eResource().getURI().isPlatformResource());
    }

    /**
     * @see com.tibco.xpd.ui.properties.components.AbstractPickerControl
     *      #doBrowse(org.eclipse.swt.widgets.Control)
     * 
     * @param control
     *            The browse button.
     * @return The selected UML Classifier.
     */
    @Override
    protected Classifier doBrowse(Control control) {
        Shell shell = getShell();
        String type1 = Class.class.getName();
        PickerTypeQuery typeQuery =
                new PickerTypeQuery(PICKER_EXTENSION_ID, type1);
        PickerTypeQuery primitiveQuery =
                new PickerTypeQuery(PICKER_PRIMITIVE_ID,
                        BOMTypeQuery.BASE_PRIMITIVE_TYPE);
        PickerTypeQuery[] typeQueries =
                new PickerTypeQuery[] { typeQuery, primitiveQuery };
        Object contentToPreselect = null;
        IResource[] queryResources = null;
        IFilter jsonFilter = new IFilter() {
            @Override
            public boolean select(Object toTest) {
                if (toTest instanceof PickerItem) {
                    PickerItem item = (PickerItem) toTest;
                    if (JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE.equals(item
                            .getType())) {
                        return false;
                    }
                }
                return true;
            }
        };
        IFilter[] filters = new IFilter[] { jsonFilter, new PrimitiveFilter() };
        String initialPattern = null;
        Object[] contentToExclude = null;
        Classifier result = null;
        Object picked =
                PickerService.getInstance().openSinglePickerDialog(shell,
                        typeQueries,
                        initialPattern,
                        queryResources,
                        contentToExclude,
                        filters,
                        contentToPreselect);
        if (picked instanceof Classifier) {
            result = (Classifier) picked;
        }
        return result;
    }

    /**
     * @see com.tibco.xpd.ui.properties.components.AbstractPickerControl#getSetValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.Object)
     * 
     * @param editingDomain
     *            The editing domain for this command.
     * @param value
     *            The Classifier to set.
     * @return The command to set the Classifier.
     */
    @Override
    protected Command getSetValueCommand(EditingDomain editingDomain,
            Classifier value) {
        if (!BomUIUtil.checkProjectDependencies(null,
                element,
                value,
                Messages.JsonSchemaTypePicker_PropertyType)) {
            return UnexecutableCommand.INSTANCE;
        }
        CompoundCommand cmd =
                new CompoundCommand(Messages.JsonSchemaTypePicker_setJsonType);
        cmd.append(new SetCommand(editingDomain, element, UMLPackage.eINSTANCE
                .getTypedElement_Type(), value));
        return cmd;
    }

    /**
     * @see com.tibco.xpd.ui.properties.components.AbstractPickerControl#getClearValueCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.Object)
     * 
     * @param editingDomain
     *            The editing domain for this command.
     * @param value
     *            not used.
     * @return The command to clear the currently set Classifier.
     */
    @Override
    protected Command getClearValueCommand(EditingDomain editingDomain,
            Classifier value) {
        CompoundCommand cmd =
                new CompoundCommand(Messages.JsonSchemaTypePicker_clearJsonType);
        cmd.append(new SetCommand(editingDomain, element, UMLPackage.eINSTANCE
                .getTypedElement_Type(), null));
        return cmd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void hyperlinkActivated(Classifier classifier) {
        if (classifier != null) {
            assert element != null : "Typed element must be set."; //$NON-NLS-1$
            if (classifier instanceof Class) {
                new JsonSchemaEditorOpener().openEditor(classifier, true);
            }
        }
    }

}
