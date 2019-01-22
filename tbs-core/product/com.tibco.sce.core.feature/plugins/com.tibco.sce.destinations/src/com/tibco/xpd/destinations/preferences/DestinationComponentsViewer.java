/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.internal.Messages;

/**
 * @author nwilson
 * 
 */
public class DestinationComponentsViewer extends Viewer implements
        DestinationPreferencesListener {

    private Composite composite;

    private DestinationPreferences preferences;

    private TableViewer viewer;

    public DestinationComponentsViewer(Composite parent) {
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());
        Label componentsLabel = new Label(composite, SWT.NONE);
        componentsLabel
                .setText(Messages.DestinationComponentsViewer_destinationComponentsTableLabel);
        componentsLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
                false));
        viewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
        viewer.setContentProvider(new DestinationComponentsContentProvider());
        viewer.setComparator(null);
        viewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));
        viewer.getTable().setHeaderVisible(true);

        ColumnViewerEditorActivationStrategy editorActivationStrategy =
                new ColumnViewerEditorActivationStrategy(viewer);
        TableViewerEditor.create(viewer,
                editorActivationStrategy,
                TableViewerEditor.DEFAULT);

        TableViewerColumn nameColumn = new TableViewerColumn(viewer, SWT.LEFT);
        nameColumn
                .getColumn()
                .setText(Messages.DestinationComponentsViewer_ComponentColumnLabel);
        nameColumn.getColumn().setWidth(250);
        nameColumn.setLabelProvider(new DestinationComponentsLabelProvider());

        TableViewerColumn versionColumn =
                new TableViewerColumn(viewer, SWT.LEFT);
        versionColumn
                .getColumn()
                .setText(Messages.DestinationComponentsViewer_VersionColumnLabel);
        versionColumn.getColumn().setWidth(100);
        versionColumn
                .setLabelProvider(new DestinationComponentsLabelProvider());
        versionColumn
                .setEditingSupport(new DestinationComponentsVersionEditingSupport(
                        viewer));
    }

    @Override
    public Control getControl() {
        return composite;
    }

    @Override
    public Object getInput() {
        return viewer.getInput();
    }

    @Override
    public ISelection getSelection() {
        return viewer.getSelection();
    }

    @Override
    public void refresh() {
        viewer.refresh();
    }

    public void setPreferences(DestinationPreferences preferences) {
        if (this.preferences != null) {
            this.preferences.removeDestinationPreferencesListener(this);
        }
        this.preferences = preferences;
        this.preferences.addDestinationPreferencesListener(this);
    }

    @Override
    public void setInput(Object input) {
        viewer.setInput(input);
    }

    @Override
    public void destinationPreferencesChanged(DestinationPreferencesEvent event) {
        refresh();
    }

    @Override
    public void setSelection(ISelection selection, boolean reveal) {
        viewer.setSelection(selection, reveal);
    }

    class DestinationComponentsContentProvider implements
            IStructuredContentProvider {

        @Override
        public Object[] getElements(Object inputElement) {
            Object[] elements = new Object[0];
            if (inputElement instanceof String) {
                String globalName = (String) inputElement;
                String globalId =
                        preferences.getGlobalDestinationId(globalName);
                elements =
                        GlobalDestinationUtil
                                .getAllComponentsWithoutHidden(globalId);
            }
            return elements;
        }

        @Override
        public void dispose() {
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }

    class DestinationComponentsLabelProvider extends CellLabelProvider {

        @Override
        public void update(ViewerCell cell) {
            Object element = cell.getElement();
            if (element instanceof String) {
                String componentId = (String) element;
                if (cell.getColumnIndex() == 0) {
                    String componentName =
                            GlobalDestinationUtil.getComponentName(componentId);
                    cell.setText(componentName);
                } else {
                    String validationId =
                            preferences
                                    .getValidationDestinationId((String) getInput(),
                                            componentId);
                    if (validationId == null) {
                        validationId =
                                GlobalDestinationUtil
                                        .getDefaultValdationDestinationId(componentId);
                    }
                    boolean isDisabled =
                            GlobalDestinationUtil
                                    .isDisabledComponentVersion(validationId);
                    String validationName;
                    if (isDisabled) {
                        validationName =
                                Messages.DestinationComponentsViewer_notApplicableVerisonLabel;
                    } else {
                        validationName =
                                GlobalDestinationUtil
                                        .getValidationDestinationName(validationId);
                    }
                    cell.setText(validationName);
                }
            }
        }

    }

    class DestinationComponentsVersionEditingSupport extends EditingSupport {

        private ComboBoxViewerCellEditor editor;

        public DestinationComponentsVersionEditingSupport(
                ColumnViewer columnViewer) {
            super(columnViewer);
            /*
             * XPD-6789: Saket: This editor should be read only.
             */
            editor =
                    new ComboBoxViewerCellEditor(viewer.getTable(),
                            SWT.READ_ONLY);
            editor.setActivationStyle(ComboBoxViewerCellEditor.DROP_DOWN_ON_KEY_ACTIVATION
                    | ComboBoxViewerCellEditor.DROP_DOWN_ON_MOUSE_ACTIVATION
                    | ComboBoxViewerCellEditor.DROP_DOWN_ON_PROGRAMMATIC_ACTIVATION
                    | ComboBoxViewerCellEditor.DROP_DOWN_ON_TRAVERSE_ACTIVATION);
            editor.setContenProvider(new ComponentVersionsContentProvider());
            editor.setLabelProvider(new ComponentVersionsLabelProvider());
        }

        @Override
        protected boolean canEdit(Object element) {
            return true;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            editor.setInput(element);
            return editor;
        }

        @Override
        protected Object getValue(Object element) {
            String text = null;
            Object input = viewer.getInput();
            if (element instanceof String && input instanceof String) {
                String globalComponentId = (String) element;
                String name = (String) input;
                String validationDestinationId =
                        preferences.getValidationDestinationId(name,
                                globalComponentId);
                if (validationDestinationId != null) {
                    text = validationDestinationId;
                }
                if (text == null) {
                    validationDestinationId =
                            GlobalDestinationUtil
                                    .getDefaultValdationDestinationId(globalComponentId);
                    if (validationDestinationId != null) {
                        text = validationDestinationId;
                    }
                }
            }
            return text;
        }

        @Override
        protected void setValue(Object element, Object value) {
            Object input = viewer.getInput();
            if (element instanceof String && input instanceof String
                    && value instanceof String) {
                String globalComponentId = (String) element;
                String name = (String) input;
                String validationDestinationId = (String) value;
                preferences.setValidationDestinationId(name,
                        globalComponentId,
                        validationDestinationId);
                viewer.update(element, null);
            }
        }

    }
}
