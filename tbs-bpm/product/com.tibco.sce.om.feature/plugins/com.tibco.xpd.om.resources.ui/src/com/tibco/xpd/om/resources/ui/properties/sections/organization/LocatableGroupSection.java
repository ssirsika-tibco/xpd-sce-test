/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.organization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.themes.ColorUtil;

import com.tibco.xpd.om.core.om.Locatable;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.MultipleFeature;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.om.core.om.provider.TransientItemProvider;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.LabelColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.NameColumn;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.AbstractTableControl;
import com.tibco.xpd.resources.ui.components.DialogCellWithClearEditor;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveUpAction;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.SameResourceFilter;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Abstract section to be implements by all {@link Locatable} groups.
 * 
 * @author njpatel
 * 
 */
public abstract class LocatableGroupSection<T extends OrgElement, V extends MultipleFeature>
        extends AbstractTransactionalSection {

    private ElementsTable table;

    private final TransactionalAdapterFactoryLabelProvider modelLabelProvider;

    private ItemProviderAdapter itemProvider;

    private final String heading;

    private Font tableHeaderFont;

    /**
     * Abstract section to be implements by all {@link Locatable} groups.
     * 
     * @param heading
     *            heading of the section (printed above the table).
     */
    public LocatableGroupSection(String heading) {
        this.heading = heading;
        modelLabelProvider =
                new TransactionalAdapterFactoryLabelProvider(XpdResourcesPlugin
                        .getDefault().getEditingDomain(), XpdResourcesPlugin
                        .getDefault().getAdapterFactory());
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof EditPart) {
            return (EObject) ((EditPart) object).getAdapter(EObject.class);
        }

        return super.resollveInput(object);
    }

    /**
     * Create the new child element
     * 
     * @return
     */
    protected abstract T createNewElement();

    /**
     * Get the available features.
     * 
     * @return
     */
    protected abstract List<V> getFeatures();

    protected abstract V getFeature(Object element);

    /**
     * Get the {@link Command} to set the given feature on the element.
     * 
     * @param element
     * @param value
     *            feature or <code>null</code> if need to unset the feature
     * @return
     */
    protected abstract Command getSetFeatureCommand(Object element,
            Object feature);

    /**
     * Get the {@link Command} to move the given element by the given value.
     * Default implementation returns <code>null</code>, subclasses may override
     * if they wish to support this functionality in the table. This will be
     * called if {@link #isMoveSupported()} returns <code>true</code>.
     * 
     * @param ed
     *            {@link EditingDomain}
     * @param element
     *            element to move
     * @param moveBy
     *            positive value to move down the list, negative to move up.
     * @return <code>Command</code> or <code>null</code> if not supported.
     */
    protected Command getMoveCommand(EditingDomain ed, EObject element,
            int moveBy) {
        return null;
    }

    /**
     * Check if move is supported in the table. If this returns
     * <code>true</code> then {@link #getMoveCommand(EObject, int)} should
     * return a command.
     * 
     * @return <code>true</code> if moving of elements is supported.
     */
    protected boolean isMoveSupported() {
        return false;
    }

    /**
     * Delete the given selection.
     * 
     * @param selection
     */
    protected void deleteSelection(IStructuredSelection selection) {
        EditingDomain domain = getEditingDomain();
        EObject input = getInput();

        if (input != null && domain != null && selection != null
                && !selection.isEmpty()) {
            domain.getCommandStack().execute(RemoveCommand.create(domain,
                    selection.toList()));
        }
    }

    /**
     * Get the model {@link TransactionalAdapterFactoryLabelProvider label
     * provider}.
     * 
     * @return label provider.
     */
    protected ILabelProvider getModelLabelProvider() {
        return modelLabelProvider;
    }

    @Override
    public void dispose() {
        modelLabelProvider.dispose();
        if (tableHeaderFont != null) {
            tableHeaderFont.dispose();
            tableHeaderFont = null;
        }
        super.dispose();
    }

    @Override
    public void setInput(Collection<?> items) {
        if (items != null && !items.isEmpty()) {
            Object next = items.iterator().next();
            if (next instanceof TransientItemProvider) {
                itemProvider = (TransientItemProvider) next;
                Notifier target = ((TransientItemProvider) next).getTarget();

                if (target instanceof EObject) {
                    items = Collections.singleton((EObject) target);
                }
            } else {
                next = resollveInput(next);

                if (next instanceof EObject) {
                    IEditingDomainItemProvider provider =
                            (IEditingDomainItemProvider) XpdResourcesPlugin
                                    .getDefault()
                                    .getAdapterFactory()
                                    .adapt(next,
                                            IEditingDomainItemProvider.class);
                    if (provider instanceof ItemProviderAdapter) {
                        itemProvider = (ItemProviderAdapter) provider;
                    }
                }
            }
        }
        super.setInput(items);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());
        if (heading != null) {
            Label label = toolkit.createLabel(root, heading);
            GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
            data.horizontalIndent = 5;
            label.setLayoutData(data);
            label.setForeground(ColorConstants.darkGray);
            tableHeaderFont = new Font(root.getDisplay(), "Arial", 10, //$NON-NLS-1$
                    SWT.BOLD);
            label.setFont(tableHeaderFont);
        }
        table =
                new ElementsTable(root, toolkit, SWT.MULTI | SWT.FULL_SELECTION);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.widthHint = 200;
        data.heightHint = 150;
        table.setLayoutData(data);

        return root;
    }

    /**
     * Get the table.
     * 
     * @return
     */
    protected AbstractTableControl getTable() {
        return table;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Table will handle this
        return null;
    }

    /**
     * Get the children of the given input
     * 
     * @param input
     *            section input
     * @return list of children or empty list or <code>null</code> if no
     *         children present.
     */
    protected EList<? extends OrgElement> getChildren(EObject input) {
        if (itemProvider != null) {
            Collection<?> children = itemProvider.getChildren(input);

            if (children != null) {
                EList<OrgElement> elements = new BasicEList<OrgElement>();
                for (Object child : children) {
                    if (child instanceof OrgElement
                            && isChild((OrgElement) child)) {
                        elements.add((OrgElement) child);
                    }
                }
                return elements;
            }
        }
        return null;
    }

    /**
     * Check if the given {@link OrgElement} is a child of this section's input.
     * Default implementation returns <code>true</code>.
     * 
     * @param child
     * @return
     */
    protected boolean isChild(OrgElement child) {
        return true;
    }

    /**
     * Get the section input's item provider
     * 
     * @return item provider or <code>null</code> if not set.
     */
    protected ItemProviderAdapter getItemProvider() {
        return itemProvider;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input != null) {
            table.setElements(getChildren(input));
        }
    }

    /**
     * The <code>OrgElement</code>s table.
     * 
     * @author njpatel
     * 
     */
    private class ElementsTable extends AbstractTableControl {

        public ElementsTable(Composite parent, XpdFormToolkit toolkit, int style) {
            super(parent, toolkit, style);
        }

        @Override
        protected TableAddAction getAddAction(TableViewer viewer) {
            return new TableAddAction(viewer) {
                @Override
                protected Object addRow(StructuredViewer viewer) {
                    return createNewElement();
                }
            };
        }

        @Override
        protected TableDeleteAction getDeleteAction(TableViewer viewer) {
            return new TableDeleteAction(viewer) {

                @Override
                protected void deleteRows(IStructuredSelection selection) {
                    deleteSelection(selection);
                }
            };
        }

        @Override
        protected TableMoveUpAction getMoveUpAction(TableViewer viewer) {
            if (isMoveSupported()) {

                return new TableMoveUpAction(viewer) {
                    @Override
                    protected void moveUp(Object element) {
                        EditingDomain ed = getEditingDomain();
                        if (ed != null && element instanceof EObject) {
                            Command cmd =
                                    getMoveCommand(ed, (EObject) element, -1);
                            if (cmd != null) {
                                ed.getCommandStack().execute(cmd);
                            }
                        }
                    }
                };
            }

            return null;
        }

        @Override
        protected TableMoveDownAction getMoveDownAction(TableViewer viewer) {
            if (isMoveSupported()) {
                return new TableMoveDownAction(viewer) {
                    @Override
                    protected void moveDown(Object element) {
                        EditingDomain ed = getEditingDomain();
                        if (ed != null && element instanceof EObject) {
                            Command cmd =
                                    getMoveCommand(ed, (EObject) element, 1);
                            if (cmd != null) {
                                ed.getCommandStack().execute(cmd);
                            }
                        }
                    }
                };
            }
            return null;
        }

        @Override
        protected void addColumns(TableViewer viewer) {
            LocatableGroupSection.this.addColumns(viewer);
        }
    }

    /**
     * Add columns to the table. The base class adds the label, start date and
     * end date columns. Subclasses may extend to add/remove columns.
     * 
     * @see #addLabelColumn(TableViewer)
     * @see #addLocationColumn(TableViewer)
     * @see #addStartDateColumn(TableViewer)
     * @see #addEndDateColumn(TableViewer)
     * 
     * @param viewer
     */
    protected void addColumns(TableViewer viewer) {
        new LabelColumn(getEditingDomain(), viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(getEditingDomain(), viewer);
        }
        new FeatureColumn(getEditingDomain(), viewer);
        new LocationColumn(getEditingDomain(), viewer);
    }

    /**
     * {@link Location} column.
     * 
     * @author njpatel
     */
    private class LocationColumn extends AbstractColumn {

        private final DialogCellWithClearEditor editor;

        public LocationColumn(EditingDomain editingDomain, TableViewer viewer) {
            super(editingDomain, viewer, SWT.LEFT,
                    Messages.LocatableGroupSection_locationColumn_title, 150);
            editor =
                    new DialogCellWithClearEditor(
                            (Composite) viewer.getControl()) {
                        @Override
                        protected Object openDialogBox(Control cellEditorWindow) {
                            EObject input = getInput();
                            if (input != null) {
                                return PickerService
                                        .getInstance()
                                        .openSinglePickerDialog(cellEditorWindow
                                                .getShell(),
                                                new PickerTypeQuery[] { new OMTypeQuery(
                                                        OMTypeQuery.TYPE_ID_LOCATION) },
                                                null,
                                                null,
                                                null,
                                                new IFilter[] { new SameResourceFilter(
                                                        input) });
                            }
                            return null;
                        }
                    };
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return editor;
        }

        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof Locatable) {
                EditingDomain ed = getEditingDomain();

                if (ed != null) {
                    return SetCommand.create(ed,
                            element,
                            OMPackage.eINSTANCE.getLocatable_Location(),
                            value != null ? value : SetCommand.UNSET_VALUE);
                }
            }
            return null;
        }

        @Override
        protected String getText(Object element) {
            if (element instanceof Locatable) {
                Location location = ((Locatable) element).getLocation();
                return getModelLabelProvider().getText(location);
            }
            return ""; //$NON-NLS-1$
        }

        @Override
        protected Object getValueForEditor(Object element) {
            Object value = null;
            if (element instanceof Locatable) {
                Location loc = ((Locatable) element).getLocation();

                if (loc != null) {
                    return loc.getDisplayName();
                }
            }
            return value;
        }
    }

    /**
     * Feature type column that allows the user to select the feature.
     * 
     * @author njpatel
     * 
     */
    private class FeatureColumn extends AbstractColumn {

        private final ComboBoxCellEditor editor;

        private final Map<String, V> valueMap;

        private final TransactionalAdapterFactoryLabelProvider labelProvider;

        private Color disableColor;

        public FeatureColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.LEFT,
                    Messages.LocatableGroupSection_TypeColumnHeading_label, 150);
            editor =
                    new ComboBoxCellEditor((Composite) viewer.getControl(),
                            new String[0], SWT.READ_ONLY);
            valueMap = new HashMap<String, V>();
            labelProvider = getModelLabelProvider();
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            valueMap.clear();
            List<V> features = getFeatures();
            if (features != null) {
                for (V feature : features) {
                    valueMap.put(labelProvider.getText(feature), feature);
                }
            }

            if (!valueMap.isEmpty()) {
                // Update the editor values
                List<String> values = new ArrayList<String>();
                values.add(""); //$NON-NLS-1$
                values.addAll(valueMap.keySet());
                editor.setItems(values.toArray(new String[values.size()]));
                return editor;
            }

            return null;
        }

        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element != null && value instanceof Integer) {
                Object feature = null;
                int idx = (Integer) value;
                String[] items = editor.getItems();
                if (!valueMap.isEmpty() && idx >= 0 && items.length > 0) {
                    feature = valueMap.get(items[idx]);
                }
                return getSetFeatureCommand(element, feature);
            }

            return null;
        }

        @Override
        protected String getText(Object element) {
            List<V> features = getFeatures();

            if (features != null && !features.isEmpty()) {
                V feature = getFeature(element);
                return feature != null ? feature.getDisplayName() : ""; //$NON-NLS-1$
            }

            return Messages.LocatableGroupSection_NoTypesAvailable_label;
        }

        @Override
        protected Color getForeground(Object element) {
            List<V> features = getFeatures();
            if (features == null || features.isEmpty()) {
                if (disableColor == null) {
                    Color foreground =
                            getColumn().getViewer().getControl()
                                    .getForeground();
                    disableColor =
                            new Color(foreground.getDevice(),
                                    ColorUtil.blend(foreground.getRGB(),
                                            ColorConstants.white.getRGB(),
                                            40));
                }
                return disableColor;
            }
            return super.getForeground(element);
        }

        @Override
        protected Object getValueForEditor(Object element) {
            V feature = getFeature(element);
            String value =
                    feature != null ? labelProvider.getText(feature) : ""; //$NON-NLS-1$

            if (value != null) {
                String[] items = editor.getItems();
                if (items != null) {
                    for (int idx = 0; idx < items.length; idx++) {
                        if (value.equals(items[idx])) {
                            return idx;
                        }
                    }
                }
            }

            return 0;
        }

        @Override
        protected void dispose() {
            if (disableColor != null) {
                disableColor.dispose();
                disableColor = null;
            }
            super.dispose();
        }
    }
}
