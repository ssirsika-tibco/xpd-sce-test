/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.pickers;

import java.util.ArrayList;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.models.OMTypeDetails;
import com.tibco.xpd.om.resources.ui.providers.OMTypesContentProvider;
import com.tibco.xpd.om.resources.ui.providers.OMTypesLabelProvider;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.XpdPropertiesFormToolkit;
import com.tibco.xpd.ui.properties.table.TableWithButtons;

/**
 * Dialog to pick an om class type
 * 
 * @author glewis
 * @deprecated Use {@link PickerService} instead.
 */
@Deprecated
public class OMTypesPicker extends SelectionStatusDialog {

    private TableWithButtons omTypesTable;

    private Control omTypesTableControl;

    private OMTypesLabelProvider labelProvider;

    private final String PARAM_USE = "PARAM_USE"; //$NON-NLS-1$

    private final String PARAM_TYPE = "PARAM_TYPE"; //$NON-NLS-1$

    protected ICellModifier cellModifier = new ICellModifier() {
        public boolean canModify(Object element, String property) {
            return true;
        }

        public Object getValue(Object element, String property) {
            if (element instanceof OMTypeDetails) {
                OMTypeDetails omTypeDetails = (OMTypeDetails) element;
                if (property.equals(PARAM_USE)) {
                    return new Boolean(omTypeDetails.isSelected());
                }
            }
            return null;
        }

        public void modify(Object element, String property, Object value) {
            OMTypeDetails omTypeDetails =
                    (OMTypeDetails) ((TableItem) element).getData();
            if (property.equals(PARAM_USE)) {
                if (omTypesTable != null) {
                    omTypeDetails.setSelected(!omTypeDetails.isSelected());
                    omTypesTable.getViewer().refresh();
                }
            }
        }
    };

    /**
     * @param parent
     */
    public OMTypesPicker(Shell parent) {
        super(parent);
        setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
        setTitle(Messages.OMTypesPicker_dialogTitle);
        setMessage(Messages.OMTypesPicker_dialogMessage);

    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite mainComposite = (Composite) super.createDialogArea(parent);

        // Create a message and tree view area
        Label messageLabel = createMessageArea(mainComposite);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        Composite composite = new Composite(mainComposite, SWT.NONE);
        composite.setLayout(gridLayout);

        // create the table
        XpdFormToolkit toolkit =
                new XpdPropertiesFormToolkit(
                        new TabbedPropertySheetWidgetFactory());
        omTypesTable =
                new TableWithButtons(toolkit, composite, SWT.MULTI
                        | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER
                        | SWT.FULL_SELECTION);

        omTypesTableControl = omTypesTable.createControl();
        omTypesTableControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));

        // default selected are Group , Position and OrgUnit
        ArrayList<Class> initialTypesArr = new ArrayList<Class>();
        initialTypesArr.add(Group.class);
        initialTypesArr.add(Position.class);
        initialTypesArr.add(OrgUnit.class);

        // set content provider and label provider
        TableViewer tableViewer = omTypesTable.getViewer();
        tableViewer.setContentProvider(new OMTypesContentProvider(
                initialTypesArr));

        labelProvider = new OMTypesLabelProvider();
        tableViewer.setLabelProvider(labelProvider);

        tableViewer.getTable().setHeaderVisible(true);
        tableViewer.getTable().setLinesVisible(true);

        // create the 2 columns
        TableColumn deployColumn =
                new TableColumn(tableViewer.getTable(), SWT.LEFT, 0);
        deployColumn.setText(Messages.OMTypesPicker_UseColumn_label);
        deployColumn.setWidth(50);

        TableColumn activitiesColumn =
                new TableColumn(tableViewer.getTable(), SWT.LEFT, 1);
        activitiesColumn.setText(Messages.OMTypesPicker_TypeColumn_label);
        activitiesColumn.setWidth(200);

        // ensure 1st column is of check box cell type
        tableViewer.setCellEditors(new CellEditor[] {
                new com.tibco.xpd.ui.properties.table.CheckboxCellEditor(
                        tableViewer.getTable(), SWT.CHECK), null });

        omTypesTable.getActionsManager().update(false);
        tableViewer.setColumnProperties(new String[] { PARAM_USE, PARAM_TYPE });

        tableViewer.setCellModifier(cellModifier);

        // set the data (list of the om types) on the viewer
        ArrayList<Class> typesArr = new ArrayList<Class>();
        typesArr.add(Capability.class);
        typesArr.add(Group.class);
        typesArr.add(Location.class);
        typesArr.add(Privilege.class);
        typesArr.add(Attribute.class);
        typesArr.add(OrgUnit.class);
        typesArr.add(Position.class);

        tableViewer.setInput(typesArr);

        return composite;
    }

    @Override
    protected void computeResult() {
        ArrayList<String> selectedClassNames = new ArrayList<String>();
        int size = omTypesTable.getViewer().getTable().getItemCount();
        for (int i = 0; i < size; i++) {
            OMTypeDetails omTypeDetails =
                    (OMTypeDetails) omTypesTable.getViewer().getElementAt(i);
            if (omTypeDetails.isSelected()) {
                selectedClassNames.add(omTypeDetails.getTypeCls().getName());
            }
        }
        setResult(selectedClassNames);
    }

    /**
     * @return
     */
    public static Object[] getDefaultOMTypes() {
        ArrayList<String> defaultTypes = new ArrayList<String>();
        defaultTypes.add(Group.class.getName());
        defaultTypes.add(Position.class.getName());
        defaultTypes.add(OrgUnit.class.getName());
        return defaultTypes.toArray();
    }
}
