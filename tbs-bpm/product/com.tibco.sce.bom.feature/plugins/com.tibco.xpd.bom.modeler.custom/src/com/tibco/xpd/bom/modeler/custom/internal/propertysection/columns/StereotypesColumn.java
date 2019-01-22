/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.profiles.StereotypeUtil;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.DialogCellWithClearEditor;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * BOM table column to display and allow applying {@link Stereotype}s.
 * 
 * @author njpatel
 * 
 */
public class StereotypesColumn extends AbstractColumn {

    /**
     * Set as the value when the user selects Clear in the dialog cell editor.
     */
    private static final Object CLEAR = new Object();

    private final StereotypeEditor editor;

    /**
     * Stereotypes column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     */
    public StereotypesColumn(EditingDomain editingDomain, ColumnViewer viewer) {
        this(editingDomain, viewer, SWT.NONE,
                Messages.StereotypesColumn_column_title, 250);
    }

    /**
     * Stereotypes column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param style
     *            column style
     * @param heading
     *            column heading
     * @param width
     *            initial column width
     */
    public StereotypesColumn(EditingDomain editingDomain, ColumnViewer viewer,
            int style, String heading, int width) {
        super(editingDomain, viewer, style, heading, width);
        setShowImage(true);
        editor = new StereotypeEditor((Composite) viewer.getControl());
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        if (element instanceof Element) {
            editor.setInput((Element) element);
            return editor;
        }
        return null;
    }

    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        if (element instanceof Element && value != null) {
            List<Stereotype> types = new ArrayList<Stereotype>();

            if (value instanceof Collection<?>) {
                for (Object obj : (Collection<?>) value) {
                    if (obj instanceof Stereotype) {
                        types.add((Stereotype) obj);
                    }
                }
            }
            try {
                return StereotypeUtil
                        .getSetStereotypesCommand((TransactionalEditingDomain) getEditingDomain(),
                                (Element) element,
                                types);
            } catch (CoreException e) {
                ErrorDialog.openError(getViewer().getControl().getShell(),
                        Messages.StereotypesColumn_profileError_dialog_title,
                        Messages.StereotypesColumn_profileError_dialog_message,
                        e.getStatus());
            }
        }
        return null;
    }

    @Override
    protected String getText(Object element) {
        if (element instanceof Element) {
            return printContent(getStereotypes((Element) element));
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Print the stereotype string, this will show a comma-separated list of
     * stereotype names.
     * 
     * @param stereotypes
     * @return
     */
    private String printContent(Collection<?> stereotypes) {
        StringBuffer txt = new StringBuffer(""); //$NON-NLS-1$

        if (stereotypes != null) {
            for (Object type : stereotypes) {
                if (type instanceof Stereotype) {
                    if (txt.length() > 0) {
                        txt.append(", "); //$NON-NLS-1$
                    }
                    txt.append(((Stereotype) type).getName());
                }
            }
        }
        return txt.toString();
    }

    @Override
    protected Image getImage(Object element) {
        if (element instanceof Element) {
            EList<Stereotype> stereotypes = getStereotypes((Element) element);
            if (!stereotypes.isEmpty()) {
                return WorkingCopyUtil.getImage(stereotypes.get(0));
            }
        }
        return null;
    }

    @Override
    protected Object getValueForEditor(Object element) {
        return element instanceof Element ? getStereotypes((Element) element)
                : null;
    }

    /**
     * Get all the Stereotypes applied to the given {@link Element}. All
     * internal Stereotypes will be ignored.
     * 
     * @param element
     * @return
     */
    private EList<Stereotype> getStereotypes(Element element) {
        EList<Stereotype> types = new BasicEList<Stereotype>();

        if (element != null) {
            for (Stereotype type : element.getAppliedStereotypes()) {
                if (type.eResource() != null
                        && type.eResource().getURI().isPlatformResource()) {
                    types.add(type);
                }
            }
        }

        return types;
    }

    /**
     * Column editor for Stereotypes. This will show the stereotype picker.
     * 
     * @author njpatel
     * 
     */
    private class StereotypeEditor extends DialogCellWithClearEditor {

        private Element input;

        private final Shell shell;

        public StereotypeEditor(Composite parent) {
            super(parent);
            shell = parent.getShell();
        }

        public void setInput(Element input) {
            this.input = input;
        }

        @Override
        protected Object getClearValue() {
            return CLEAR;
        }

        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            return input != null ? StereotypeUtil
                    .getStereotypesFromPicker(shell, input) : null;
        }

        @Override
        protected void updateContents(Object value) {
            if (value instanceof Collection<?>) {
                value = printContent((Collection<?>) value);
            }
            super.updateContents(value);
        }
    }
}
