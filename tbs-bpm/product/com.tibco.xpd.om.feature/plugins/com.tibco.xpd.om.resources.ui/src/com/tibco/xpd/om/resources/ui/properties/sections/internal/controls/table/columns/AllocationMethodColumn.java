package com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.om.core.om.Allocable;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.PropertySheetsConstants;
import com.tibco.xpd.resources.ui.components.AbstractColumn;

public class AllocationMethodColumn extends AbstractColumn {

    private ComboBoxCellEditor editor;

    private final boolean isReadOnly;

    private final String[] allocChoices;

    /**
     * Name column
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     */
    public AllocationMethodColumn(EditingDomain editingDomain,
            ColumnViewer viewer) {
        this(editingDomain, viewer, false);
    }

    /**
     * Name column
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param isReadOnly
     *            <code>true</code> if this is a read-only column,
     *            <code>false</code> if editing is allowed.
     */
    public AllocationMethodColumn(EditingDomain editingDomain,
            ColumnViewer viewer, boolean isReadOnly) {
        super(editingDomain, viewer,
                Messages.AllocationColumn_allocationColumn_title, 100);
        this.isReadOnly = isReadOnly;

        if (!isReadOnly) {
            editor =
                    new ComboBoxCellEditor((Composite) viewer.getControl(),
                            new String[0], SWT.READ_ONLY);
        }
        setShowImage(false);

        allocChoices = new String[2];
        allocChoices[0] =
                PropertySheetsConstants.AllocationMethod.ANY.getLabel();
        allocChoices[1] =
                PropertySheetsConstants.AllocationMethod.NEXT.getLabel();

    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        editor.setItems(allocChoices);
        return editor;
    }

    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        if (element instanceof Allocable && value instanceof Integer) {
            Allocable alloc = (Allocable) element;
            Integer choice = (Integer) value;
            String chosenMethod = ""; //$NON-NLS-1$

            switch (choice) {
            case 0:
                chosenMethod = PropertySheetsConstants.ALLOCATION_METHOD_ANY;
                break;
            case 1:
                chosenMethod = PropertySheetsConstants.ALLOCATION_METHOD_NEXT;
                break;
            default:
                return null;
            }

            return SetCommand.create(getEditingDomain(),
                    alloc,
                    OMPackage.eINSTANCE.getAllocable_AllocationMethod(),
                    chosenMethod);

        }

        return null;
    }

    @Override
    protected String getText(Object element) {
        if (element instanceof Allocable) {
            Allocable alloc = (Allocable) element;
            String method = alloc.getAllocationMethod();

            if (method.equals(PropertySheetsConstants.ALLOCATION_METHOD_ANY)) {
                return allocChoices[0];
            }
            if (method.equals(PropertySheetsConstants.ALLOCATION_METHOD_NEXT)) {
                return allocChoices[1];
            }
        }
        return null;
    }

    @Override
    protected Object getValueForEditor(Object element) {
        if (element instanceof Allocable) {
            Allocable alloc = (Allocable) element;

            String method = alloc.getAllocationMethod();

            if (method.equals(PropertySheetsConstants.ALLOCATION_METHOD_ANY)) {
                return 0;
            }
            if (method.equals(PropertySheetsConstants.ALLOCATION_METHOD_NEXT)) {
                return 1;
            }
        }
        return null;
    }

}
