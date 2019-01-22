/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.components.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.DialogCellWithClearEditor;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rsd.PayloadRefContainer;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.ui.RsdImage;
import com.tibco.xpd.rsd.ui.components.JsonRootTypePicker;
import com.tibco.xpd.rsd.ui.components.JsonTypeReference;
import com.tibco.xpd.rsd.ui.components.MediaType;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * Column for showing and picking the payload.
 * 
 * @author jarciuch
 * @since 17 Feb 2015
 */
public class PayloadTypeColumn extends AbstractColumn {

    private final JsonTypeEditor editor;

    /**
     * @param editingDomain
     * @param viewer
     * @param header
     * @param width
     */
    public PayloadTypeColumn(EditingDomain editingDomain, ColumnViewer viewer) {
        super(editingDomain, viewer,
                Messages.PayloadTypeColumn_PayloadTypeColumn_label, 250);
        editor = new JsonTypeEditor((Composite) viewer.getControl());
        setShowImage(true);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected String getText(Object element) {
        if (element instanceof PayloadRefContainer) {
            PayloadReference ref =
                    ((PayloadRefContainer) element).getPayloadReference();
            if (ref != null) {
                JsonTypeReference jsonReference =
                        JsonTypeReference.getJsonReference(ref);
                return jsonReference
                        .getLabel(XpdResourcesPlugin.getDefault()
                                .getEditingDomain(), WorkingCopyUtil
                                .getProjectFor(ref));
            } else {
                return Messages.PayloadTypeColumn_NotSet_label;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Image getImage(Object element) {
        if (element instanceof PayloadRefContainer) {
            PayloadReference ref =
                    ((PayloadRefContainer) element).getPayloadReference();
            if (ref != null) {
                JsonTypeReference jsonReference =
                        JsonTypeReference.getJsonReference(ref);
                if (MediaType.STANDARD_JSON.getModelValue()
                        .equals(jsonReference.getMediaType())) {
                    return RsdImage.getImage(RsdImage.PAYLOAD_REFERENCE_JSON);
                }
            }
        }
        return RsdImage.getImage(RsdImage.PAYLOAD_REFERENCE_JSON);

    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected CellEditor getCellEditor(Object element) {
        if (element instanceof PayloadRefContainer) {
            editor.setInput((PayloadRefContainer) element);
            return editor;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected Object getValueForEditor(Object element) {
        if (element instanceof PayloadRefContainer
                && ((PayloadRefContainer) element).getPayloadReference() != null) {
            return JsonTypeReference
                    .getJsonReference(((PayloadRefContainer) element)
                            .getPayloadReference());
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param element
     * @param value
     * @return
     */
    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        if (element instanceof PayloadRefContainer) {
            final PayloadRefContainer refContainer =
                    (PayloadRefContainer) element;
            PayloadReference ref = null;
            JsonTypeReference newRef = null;
            if (value instanceof JsonTypeReference) {
                newRef = (JsonTypeReference) value;
                ref = newRef.toPayloadRefernce();
            }
            JsonTypeReference oldRef =
                    refContainer.getPayloadReference() != null ? JsonTypeReference
                            .getJsonReference(refContainer
                                    .getPayloadReference()) : null;
            if (newRef == null || !newRef.equals(oldRef)) {
                if (newRef != oldRef) { // eliminate both null case
                    final PayloadReference reference = ref;
                    return new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain(),
                            Messages.PayloadTypeColumn_SetPayloadTypeCmd_label) {
                        @Override
                        protected void doExecute() {
                            refContainer.setPayloadReference(reference);
                        }
                    };
                }
            }

        }
        return null;
    }

    private static class JsonTypeEditor extends DialogCellWithClearEditor {
        private PayloadRefContainer input = null;

        private JsonTypeReference currentType;

        public JsonTypeEditor(Composite composite) {
            super(composite);
        }

        @Override
        protected Object openDialogBox(Control control) {
            return JsonRootTypePicker.pickJsonRootType(input, control);
        }

        protected void setInput(PayloadRefContainer input) {
            this.input = input;
        }

        @Override
        protected void doSetValue(Object value) {
            if (value instanceof JsonTypeReference) {
                currentType = (JsonTypeReference) value;
                value =
                        currentType.getLabel(XpdResourcesPlugin.getDefault()
                                .getEditingDomain(), WorkingCopyUtil
                                .getProjectFor(input));
            }
            if (value == null) {
                currentType = null;
                value = Messages.PayloadTypeColumn_NotSet_label;
            }
            super.doSetValue(value);
        }

        @Override
        protected Object doGetValue() {
            return currentType;
        }
    }

}
