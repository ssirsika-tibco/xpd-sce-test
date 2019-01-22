/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.om.core.om.MultipleFeature;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.MultiplicityUtil;
import com.tibco.xpd.resources.ui.components.AbstractColumn;

/**
 * Multiplicity column for setting features in Organization and OrgUnit type
 * sections.
 * 
 * @author njpatel
 * 
 */
public class MultiplicityColumn extends AbstractColumn {

    private final TextCellEditor editor;

    public MultiplicityColumn(EditingDomain editingDomain, ColumnViewer viewer) {
        super(editingDomain, viewer, SWT.LEFT,
                Messages.MultiplicityColumn_multiplicityColumn_title, 50);

        editor = new TextCellEditor((Composite) viewer.getControl());
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        return editor;
    }

    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        if (element instanceof MultipleFeature && value instanceof String) {
            return MultiplicityUtil.createSetMultiplicityCommand(
                    getEditingDomain(), (MultipleFeature) element,
                    (String) value);
        }
        return null;
    }

    @Override
    protected String getText(Object element) {
        String text = null;
        if (element instanceof MultipleFeature) {
            text = MultiplicityUtil.getText((MultipleFeature) element);
        }
        return text != null ? text : ""; //$NON-NLS-1$
    }

    @Override
    protected Object getValueForEditor(Object element) {
        return getText(element);
    }

}
