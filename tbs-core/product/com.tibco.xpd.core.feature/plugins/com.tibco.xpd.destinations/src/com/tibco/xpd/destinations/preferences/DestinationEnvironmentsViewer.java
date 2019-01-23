/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.internal.Messages;
import com.tibco.xpd.ui.properties.PropertiesPlugin;
import com.tibco.xpd.ui.properties.PropertiesPluginConstants;

/**
 * @author nwilson
 * 
 */
public class DestinationEnvironmentsViewer extends Viewer implements
        DestinationPreferencesListener {

    private Composite composite;

    private DestinationPreferences preferences;

    private TableViewer viewer;

    private Button add;

    private Button remove;

    private Button up;

    private Button down;

    public DestinationEnvironmentsViewer(Composite parent) {
        composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));
        Label destinationsLabel = new Label(composite, SWT.NONE);
        destinationsLabel
                .setText(Messages.DestinationEnvironmentsViewer_destinationEnvironmentsTableLabel);
        destinationsLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
                true, false, 2, 1));
        viewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
        viewer.setContentProvider(new DestinationEnvironmentsContentProvider());
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
                .setText(Messages.DestinationEnvironmentsViewer_EnvironmentNameColumnLabel);
        nameColumn.getColumn().setWidth(200);
        nameColumn.setLabelProvider(new DestinationEnvironmentLabelProvider());
        nameColumn
                .setEditingSupport(new DestinationEnvironmentsNameEditingSupport(
                        viewer));

        TableViewerColumn globalColumn =
                new TableViewerColumn(viewer, SWT.LEFT);
        globalColumn
                .getColumn()
                .setText(Messages.DestinationEnvironmentsViewer_EnvironmentTypeColumnLabel);
        globalColumn.getColumn().setWidth(150);
        globalColumn
                .setLabelProvider(new DestinationEnvironmentLabelProvider());
        globalColumn
                .setEditingSupport(new DestinationEnvironmentsTypeEditingSupport(
                        viewer));

        Composite buttons = new Composite(composite, SWT.NONE);
        buttons.setLayout(new GridLayout());
        buttons.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));

        ImageRegistry ir = PropertiesPlugin.getDefault().getImageRegistry();

        viewer.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                fireSelectionChanged(event);
            }

        });

        add = new Button(buttons, SWT.PUSH);
        add.setImage(ir.get(PropertiesPluginConstants.IMG_TABLE_NEW_BUTTON));
        add.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        add.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (preferences != null) {
                    DestinationSetting setting =
                            preferences.addGlobalDestination();
                    viewer.editElement(setting.getName(), 1);
                }
            }

        });

        remove = new Button(buttons, SWT.PUSH);
        remove.setImage(ir.get(PropertiesPluginConstants.IMG_TABLE_DEL_BUTTON));
        remove.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        remove.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (preferences != null) {
                    ISelection selection = viewer.getSelection();
                    if (selection instanceof IStructuredSelection) {
                        IStructuredSelection structured =
                                (IStructuredSelection) selection;
                        for (Object next : structured.toList()) {
                            if (next instanceof String) {
                                boolean confirmation =
                                        MessageDialog
                                                .openConfirm(getControl()
                                                        .getShell(),
                                                        Messages.DestinationEnvironmentsViewer_RemoveDestinationDialogTitle,
                                                        Messages.DestinationEnvironmentsViewer_RemoveDestinationDialogMessage);
                                if (confirmation) {
                                    preferences
                                            .removeGlobalDestination((String) next);
                                }
                            }
                        }
                    }
                }
            }

        });

        up = new Button(buttons, SWT.PUSH);
        up.setImage(ir.get(PropertiesPluginConstants.IMG_TABLE_UP_BUTTON));
        up.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        up.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (preferences != null) {
                    ISelection selection = viewer.getSelection();
                    if (selection instanceof IStructuredSelection) {
                        IStructuredSelection structured =
                                (IStructuredSelection) selection;
                        if (structured.size() == 1) {
                            Object next = structured.getFirstElement();
                            if (next instanceof String) {
                                preferences
                                        .moveGlobalDestinationUp((String) next);
                            }
                        }
                    }
                }
            }
        });

        down = new Button(buttons, SWT.PUSH);
        down.setImage(ir.get(PropertiesPluginConstants.IMG_TABLE_DOWN_BUTTON));
        down.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        down.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if (preferences != null) {
                    ISelection selection = viewer.getSelection();
                    if (selection instanceof IStructuredSelection) {
                        IStructuredSelection structured =
                                (IStructuredSelection) selection;
                        if (structured.size() == 1) {
                            Object next = structured.getFirstElement();
                            if (next instanceof String) {
                                preferences
                                        .moveGlobalDestinationDown((String) next);
                            }
                        }
                    }
                }
            }
        });

        viewer.addSelectionChangedListener(new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                ISelection selection = event.getSelection();
                if (selection instanceof IStructuredSelection) {
                    IStructuredSelection structured =
                            (IStructuredSelection) selection;
                    boolean canDelete = true;
                    for (Object next : structured.toList()) {
                        if (next instanceof String) {
                            String name = (String) next;
                            if (!preferences.canDelete(name)) {
                                canDelete = false;
                                break;
                            }
                        }
                    }
                    remove.setEnabled(canDelete);
                }
            }

        });
    }

    @Override
    public Control getControl() {
        return composite;
    }

    @Override
    public Object getInput() {
        return preferences;
    }

    @Override
    public ISelection getSelection() {
        return viewer.getSelection();
    }

    @Override
    public void refresh() {
        viewer.refresh();
    }

    @Override
    public void setInput(Object input) {
        if (input instanceof DestinationPreferences) {
            if (preferences != null) {
                preferences.removeDestinationPreferencesListener(this);
            }
            preferences = (DestinationPreferences) input;
            viewer.setInput(preferences);
            preferences.addDestinationPreferencesListener(this);
        }
    }

    @Override
    public void destinationPreferencesChanged(DestinationPreferencesEvent event) {
        viewer.refresh();
    }

    @Override
    public void setSelection(ISelection selection, boolean reveal) {
        viewer.setSelection(selection, reveal);
    }

    class DestinationEnvironmentsContentProvider implements
            IStructuredContentProvider {

        @Override
        public Object[] getElements(Object inputElement) {
            Object[] elements = new Object[0];
            if (inputElement instanceof DestinationPreferences) {
                DestinationPreferences preferences =
                        (DestinationPreferences) inputElement;
                List<String> dest = preferences.getGlobalDestinations();
                elements = dest.toArray();
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

    class DestinationEnvironmentsNameEditingSupport extends EditingSupport {

        private TextCellEditor textEditor = new TextCellEditor(
                viewer.getTable());

        public DestinationEnvironmentsNameEditingSupport(ColumnViewer viewer) {
            super(viewer);
        }

        @Override
        protected boolean canEdit(Object element) {
            boolean canEdit = true;
            if (element instanceof String) {
                String name = (String) element;
                if (!preferences.canDelete(name)) {
                    canEdit = false;
                }
            }
            return canEdit;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return textEditor;
        }

        @Override
        protected Object getValue(Object element) {
            Object value = null;
            if (element instanceof String) {
                value = element;
            }
            return value;
        }

        @Override
        protected void setValue(Object element, Object value) {
            if (element instanceof String && value instanceof String) {
                String oldName = (String) element;
                String newName = (String) value;
                if (!oldName.equals(newName)) {
                    preferences.renameGlobalDestination(oldName, newName);
                }
            }
        }
    }

    class DestinationEnvironmentsTypeEditingSupport extends EditingSupport {

        private ComboBoxViewerCellEditor editor;

        public DestinationEnvironmentsTypeEditingSupport(
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
            editor.setContenProvider(new GlobalDestinationsContentProvider());
            editor.setLabelProvider(new GlobalDestinationsLabelProvider());
            ((CCombo) editor.getControl()).setEditable(false);
        }

        @Override
        protected boolean canEdit(Object element) {
            boolean canEdit = true;
            if (element instanceof String) {
                String name = (String) element;
                canEdit = preferences.canDelete(name);
            }
            return canEdit;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            editor.setInput(element);
            return editor;
        }

        @Override
        protected Object getValue(Object element) {
            String text = ""; //$NON-NLS-1$
            if (element instanceof String) {
                String name = (String) element;
                String globalId = preferences.getGlobalDestinationId(name);
                if (globalId != null) {
                    text = globalId;
                }
            }
            return text;
        }

        @Override
        protected void setValue(Object element, Object value) {
            if (element instanceof String && value instanceof String) {
                String name = (String) element;
                String globalId = (String) value;
                preferences.setGlobalDestinationId(name, globalId);
                viewer.update(element, null);
            }
        }
    }

    class DestinationEnvironmentLabelProvider extends CellLabelProvider {

        @Override
        public void update(ViewerCell cell) {
            Object element = cell.getElement();
            if (element instanceof String) {
                String name = (String) element;
                if (cell.getColumnIndex() == 0) {
                    cell.setText(name);
                } else {
                    String globalId = preferences.getGlobalDestinationId(name);
                    String globalName =
                            GlobalDestinationUtil
                                    .getGlobalDestinationName(globalId);
                    cell.setText(globalName);
                }
            }
        }

    }
}
