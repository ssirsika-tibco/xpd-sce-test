/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.components;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.picker.PickerItem;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rest.schema.ui.JsonSchemaEditorOpener;
import com.tibco.xpd.rsd.PayloadRefContainer;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.RsdImage;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractPickerControl;

/**
 * A custom picker class to allow the selection of Classes and Primitive Types
 * from a JSON Schema.
 * 
 * @author jarciuch
 * @since 5 Feb 2015
 */
public class JsonRootTypePicker extends
        AbstractPickerControl<JsonTypeReference> {

    /**
     * Picker extension ID.
     */
    private static final String PICKER_EXTENSION_ID =
            "com.tibco.xpd.rest.schema.json.type.picker"; //$NON-NLS-1$

    /**
     * Index field to indicate if the class is a root class.
     */
    private static final String IS_ROOT = "isRoot"; //$NON-NLS-1$

    /**
     * The element we are picking the type for.
     */
    private PayloadRefContainer refContainer;

    private String label;

    private Image image = null;

    /**
     * Opens JSON schema root type picker and returns picked reference or
     * 'null'.
     * 
     * @param control
     *            the context control (used to determine shell).
     * @return JSON schema root type picked by user reference or 'null' if
     *         cancelled.
     */
    public static JsonTypeReference pickJsonRootType(PayloadRefContainer ref,
            Control control) {
        String title =
                com.tibco.xpd.rsd.ui.internal.Messages.JsonRootTypePicker_PayloadTypePicker_title;
        Shell shell = control.getShell();
        String classType =
                UMLPackage.eINSTANCE.getClass_().getInstanceTypeName();
        PickerTypeQuery typeQuery =
                new PickerTypeQuery(PICKER_EXTENSION_ID, classType);
        PickerTypeQuery[] typeQueries = new PickerTypeQuery[] { typeQuery };
        Object contentToPreselect = null;
        IResource[] queryResources = null;
        IFilter[] filters = new IFilter[] { new IFilter() {
            @Override
            public boolean select(Object toTest) {
                if (toTest instanceof PickerItem) {
                    PickerItem item = (PickerItem) toTest;
                    if (JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE
                            .equals(item.getType())) {
                        return true;
                    }
                    return Boolean.parseBoolean(item.get(IS_ROOT));
                }
                return false;
            }
        } };
        String initialPattern = null;
        Object[] contentToExclude = null;
        Object picked =
                PickerService.getInstance().openSinglePickerDialog(title,
                        shell,
                        typeQueries,
                        initialPattern,
                        queryResources,
                        contentToExclude,
                        filters,
                        contentToPreselect);
        if (picked instanceof Classifier) {
            Classifier c = (Classifier) picked;
            BomUIUtil.checkProjectDependencies(shell,
                    ref,
                    c,
                    Messages.ParamDataTypeColumn_DataTypeColumn_label);
            return JsonTypeReference.getJsonReference(c);
        } else if (JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE
                .equals(picked)) {
            return new JsonTypeReference(
                    JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE, null);
        }
        return null;
    }

    /**
     * 
     * @param parent
     * @param style
     * @param toolkit
     * @param editingDomain
     */
    public JsonRootTypePicker(Composite parent, int style,
            XpdFormToolkit toolkit, EditingDomain editingDomain) {
        super(parent, style, toolkit, editingDomain);
        setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                return label != null ? label : ""; //$NON-NLS-1$
            }

            @Override
            public Image getImage(Object element) {
                return image;
            }
        });
    }

    /**
     * Sets the reference container. This container will be then used to set the
     * picked reference.
     * 
     * @param element
     *            reference container to hold the picked reference.
     */
    public void setRefContainer(PayloadRefContainer element) {
        this.refContainer = element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JsonTypeReference doBrowse(Control control) {
        return pickJsonRootType(refContainer, control);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(JsonTypeReference value) {
        if (value != null) {
            if (JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE.equals(value
                    .getRef())) {
                label = Messages.JsonRootTypePicker_PayloadLabel;
                image = RsdImage.getImage(RsdImage.IMG_UNPROCESSED_TEXT_TYPE);

            } else {
                TransactionalEditingDomain ed =
                        XpdResourcesPlugin.getDefault().getEditingDomain();
                IProject contextProject =
                        WorkingCopyUtil.getProjectFor(refContainer);
                boolean resolvable =
                        value.resolveReference(ed, contextProject) != null;

                setHyperlinkActive(resolvable);
                label = value.getLabel(ed, contextProject);
                image =
                        resolvable ? RsdImage
                                .getImage(RsdImage.PAYLOAD_REFERENCE_JSON)
                                : RsdImage.getImage(RsdImage.ERROR);
            }
        } else {
            // Reference not set
            label =
                    com.tibco.xpd.rsd.ui.internal.Messages.JsonRootTypePicker_NotSet_label;
            image = null;
        }
        super.setValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command getSetValueCommand(EditingDomain editingDomain,
            JsonTypeReference value) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.JsonSchemaTypePicker_setPayloadType);
        cmd.append(new SetCommand(editingDomain, refContainer,
                RsdPackage.eINSTANCE.getPayloadRefContainer_PayloadReference(),
                value.toPayloadRefernce()));
        return cmd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command getClearValueCommand(EditingDomain editingDomain,
            JsonTypeReference value) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.JsonSchemaTypePicker_clearPayloadType);
        cmd.append(new SetCommand(editingDomain, refContainer,
                RsdPackage.eINSTANCE.getPayloadRefContainer_PayloadReference(),
                null));
        return cmd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void hyperlinkActivated(JsonTypeReference value) {
        if (value != null) {
            assert refContainer != null : "Reference container must be set."; //$NON-NLS-1$
            Classifier classifier =
                    value.resolveReference(XpdResourcesPlugin.getDefault()
                            .getEditingDomain(), WorkingCopyUtil
                            .getProjectFor(refContainer));
            if (classifier != null && classifier.eResource() != null) {
                IFile file = WorkingCopyUtil.getFile(classifier);
                new JsonSchemaEditorOpener().openEditor(file);

                // also select in project explorer if sync is off.
                selectInProjectExplorer(file);
            }
        }
    }

}
