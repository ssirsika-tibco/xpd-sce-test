/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.providers.DataFilterPickerProviderHelper.DataPickerItem;
import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.table.TableWithButtons;
import com.tibco.xpd.ui.properties.table.TableWithButtonsDeleteRowAction;
import com.tibco.xpd.ui.properties.table.TableWithButtonsMoveRowDownAction;
import com.tibco.xpd.ui.properties.table.TableWithButtonsMoveRowUpAction;
import com.tibco.xpd.ui.properties.table.TableWithButtonsNewRowAction;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.CorrelationMode;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class CorrelationDataAssociationControl extends Composite {

    private TableWithButtons table;

    private CorrelationDataAssociationAddAction addAction;

    private CorrelationDataAssociationRemoveAction removeAction;

    private CorrelationDataAssociationUpAction upAction;

    private CorrelationDataAssociationDownAction downAction;

    public CorrelationDataAssociationControl(XpdFormToolkit toolkit,
            Composite parent) {
        super(parent, SWT.NONE);
        setLayout(new FillLayout());

        // Table
        table =
                new TableWithButtons(toolkit, this, SWT.MULTI | SWT.H_SCROLL
                        | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
        table.createControl();
        TableViewer tableViewer = table.getViewer();
        Table tableControl = (Table) tableViewer.getControl();
        tableViewer
                .setContentProvider(new CorrelationDataAssociationContentProvider());
        tableViewer.getTable().setHeaderVisible(true);
        tableViewer.getTable().setLinesVisible(true);

        // Columns
        TableViewerColumn nameColumn =
                new TableViewerColumn(tableViewer, SWT.LEFT);
        nameColumn
                .getColumn()
                .setText(Messages.CorrelationDataAssociationControl_NameColumnTitle);
        nameColumn.setLabelProvider(new NameColumnLabelProvider());

        TableViewerColumn modeColumn =
                new TableViewerColumn(tableViewer, SWT.LEFT);
        modeColumn
                .getColumn()
                .setText(Messages.CorrelationDataAssociationControl_ModeColumnTitle);
        modeColumn.setLabelProvider(new ModeColumnLabelProvider());
        modeColumn.setEditingSupport(new ModeEditingSupport(modeColumn
                .getViewer()));

        TableViewerColumn descriptionColumn =
                new TableViewerColumn(tableViewer, SWT.LEFT);
        descriptionColumn
                .getColumn()
                .setText(Messages.CorrelationDataAssociationControl_DescriptionColumnTitle);
        descriptionColumn
                .setLabelProvider(new DescriptionColumnLabelProvider());
        descriptionColumn.setEditingSupport(new DescriptionEditingSupport(
                descriptionColumn.getViewer()));

        setTableLayout(tableControl);

        // Buttons
        addAction = new CorrelationDataAssociationAddAction(tableViewer);
        removeAction = new CorrelationDataAssociationRemoveAction(tableViewer);
        upAction = new CorrelationDataAssociationUpAction(tableViewer);
        downAction = new CorrelationDataAssociationDownAction(tableViewer);
        IContributionManager actionManager = table.getActionsManager();
        actionManager.add(addAction);
        actionManager.add(removeAction);
        actionManager.add(upAction);
        actionManager.add(downAction);
        actionManager.update(true);

    }

    /**
     * @param tableControl
     */
    private void setTableLayout(Table tableControl) {
        TableLayout tl = new TableLayout();

        tl.addColumnData(new ColumnWeightData(30, 10, false));
        tl.addColumnData(new ColumnWeightData(20, 10, false));
        tl.addColumnData(new ColumnWeightData(50, 10, false));
        tableControl.setLayout(tl);
        tableControl.layout();
    }

    public void setInput(EObject input) {
        table.getViewer().setInput(input);
    }

    public ISelection getSelection() {
        return table.getViewer().getSelection();
    }

    public void addSelectionChangeListener(ISelectionChangedListener listener) {
        table.getViewer().addSelectionChangedListener(listener);
    }

    public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        table.getViewer().removeSelectionChangedListener(listener);
    }

    /**
     * @return the addAction
     */
    public CorrelationDataAssociationAddAction getAddAction() {
        return addAction;
    }

    /**
     * @return the removeAction
     */
    public CorrelationDataAssociationRemoveAction getRemoveAction() {
        return removeAction;
    }

    /**
     * @return the upAction
     */
    public CorrelationDataAssociationUpAction getUpAction() {
        return upAction;
    }

    /**
     * @return the downAction
     */
    public CorrelationDataAssociationDownAction getDownAction() {
        return downAction;
    }

    private boolean getIsDisableImplicit(Activity activity) {
        boolean isDisableImplicit = false;
        Object other =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedCorrelationFields());
        if (other instanceof AssociatedCorrelationFields) {
            AssociatedCorrelationFields fieldContainer =
                    (AssociatedCorrelationFields) other;
            isDisableImplicit = fieldContainer.isDisableImplicitAssociation();
        }
        return isDisableImplicit;
    }

    class NameColumnLabelProvider extends CellLabelProvider {

        @Override
        public void update(ViewerCell cell) {
            String text = null;
            Image image = null;
            Object element = cell.getElement();
            Color color =
                    Display.getDefault()
                            .getSystemColor(SWT.COLOR_LIST_FOREGROUND);
            if (element instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) element;
                text = field.getName();
                image = WorkingCopyUtil.getImage(field);

            } else if (cell.getElement() instanceof ImplicitAssociatedParamObject) {
                ImplicitAssociatedParamObject implAss =
                        (ImplicitAssociatedParamObject) cell.getElement();

                text = implAss.getParamName();

                color =
                        Display.getDefault()
                                .getSystemColor(SWT.COLOR_DARK_GRAY);

            } else if (element instanceof String) {
                text = (String) element;
                color =
                        Display.getDefault()
                                .getSystemColor(SWT.COLOR_DARK_GRAY);
            }

            cell.setText(text != null ? text : ""); //$NON-NLS-1$
            cell.setImage(image);
            cell.setForeground(color);
        }
    }

    class ModeColumnLabelProvider extends CellLabelProvider {

        @Override
        public void update(ViewerCell cell) {
            String text = null;
            Color color =
                    Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);

            if (cell.getElement() instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) cell.getElement();
                CorrelationMode mode = field.getCorrelationMode();
                if (mode != null) {
                    text = mode.getName();
                }

                color =
                        Display.getDefault()
                                .getSystemColor(SWT.COLOR_LIST_FOREGROUND);

            } else if (cell.getElement() instanceof ImplicitAssociatedParamObject) {
                ImplicitAssociatedParamObject implAss =
                        (ImplicitAssociatedParamObject) cell.getElement();

                text = implAss.getMode();

            }

            cell.setText(text != null ? text : ""); //$NON-NLS-1$
            cell.setForeground(color);

            return;
        }

    }

    class DescriptionColumnLabelProvider extends CellLabelProvider {

        @Override
        public void update(ViewerCell cell) {
            String text = null;
            Color color =
                    Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);

            if (cell.getElement() instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) cell.getElement();
                Description desc = field.getDescription();
                if (desc != null) {
                    text = desc.getValue();
                }

                color =
                        Display.getDefault()
                                .getSystemColor(SWT.COLOR_LIST_FOREGROUND);

            } else if (cell.getElement() instanceof ImplicitAssociatedParamObject) {
                ImplicitAssociatedParamObject implAss =
                        (ImplicitAssociatedParamObject) cell.getElement();

                text = implAss.getDescription();

            }

            cell.setText(text != null ? text : ""); //$NON-NLS-1$
            cell.setForeground(color);

            return;
        }

    }

    class CorrelationDataAssociationAddAction extends
            TableWithButtonsNewRowAction {

        public CorrelationDataAssociationAddAction(StructuredViewer viewer) {
            super(
                    viewer,
                    Messages.CorrelationDataAssociationControl_AddAssociationButton);
        }

        @Override
        protected Object createNewRow(String firstCellVal) {
            Object input = table.getViewer().getInput();
            if (input instanceof Activity) {
                Activity activity = (Activity) input;
                Object[] picks = pickParameters(activity);
                if (picks != null) {
                    Set<DataField> associated = getAssociatedFields(activity);
                    Set<DataField> picked = new HashSet<DataField>();
                    Set<DataField> toAdd = new HashSet<DataField>();
                    for (Object pick : picks) {
                        if (pick instanceof DataField) {
                            DataField field = (DataField) pick;
                            picked.add(field);
                        }
                    }
                    for (DataField field : picked) {
                        if (associated.contains(field)) {
                            associated.remove(field);
                        } else {
                            toAdd.add(field);
                        }
                    }
                    if (associated.size() != 0 || toAdd.size() != 0) {
                        EditingDomain ed =
                                WorkingCopyUtil.getEditingDomain(activity);
                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.CorrelationDataAssociationControl_AssociateDataCommand);
                        Object other =
                                Xpdl2ModelUtil
                                        .getOtherElement(activity,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_AssociatedCorrelationFields());
                        if (other instanceof AssociatedCorrelationFields) {
                            AssociatedCorrelationFields fields =
                                    (AssociatedCorrelationFields) other;
                            for (DataField field : associated) {
                                AssociatedCorrelationField correlation =
                                        (AssociatedCorrelationField) EMFSearchUtil
                                                .findInList(fields
                                                        .getAssociatedCorrelationField(),
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getAssociatedCorrelationField_Name(),
                                                        field.getName());
                                if (correlation != null) {
                                    cmd.append(RemoveCommand.create(ed,
                                            correlation));
                                }
                            }
                            for (DataField field : toAdd) {
                                AssociatedCorrelationField correlation =
                                        XpdExtensionFactory.eINSTANCE
                                                .createAssociatedCorrelationField();
                                correlation.setName(field.getName());
                                correlation
                                        .setCorrelationMode(getDefaultMode(activity));
                                cmd.append(AddCommand
                                        .create(ed,
                                                fields,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getAssociatedCorrelationFields_AssociatedCorrelationField(),
                                                correlation));
                            }
                            cmd.append(SetCommand
                                    .create(ed,
                                            fields,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAssociatedCorrelationFields_DisableImplicitAssociation(),
                                            false));
                        } else {
                            AssociatedCorrelationFields fields =
                                    XpdExtensionFactory.eINSTANCE
                                            .createAssociatedCorrelationFields();
                            EList<AssociatedCorrelationField> correlationFields =
                                    fields.getAssociatedCorrelationField();
                            for (DataField field : toAdd) {
                                AssociatedCorrelationField correlation =
                                        XpdExtensionFactory.eINSTANCE
                                                .createAssociatedCorrelationField();
                                correlation.setName(field.getName());
                                correlation
                                        .setCorrelationMode(getDefaultMode(activity));
                                correlationFields.add(correlation);
                            }
                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherElementCommand(ed,
                                            activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AssociatedCorrelationFields(),
                                            fields));
                        }
                        if (cmd.canExecute()) {
                            ed.getCommandStack().execute(cmd);
                        }
                    }
                }
            }
            return null;
        }

        /**
         * @param activity
         * @return
         */
        private CorrelationMode getDefaultMode(Activity activity) {
            CorrelationMode mode = CorrelationMode.INITIALIZE;
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                // MR 40869 Receive task with no incoming transitions should
                // default to initialize, in-flow should default to correlate.
                EList<Transition> inTrans = activity.getIncomingTransitions();
                if (inTrans != null && inTrans.size() > 0) {
                    mode = CorrelationMode.CORRELATE;
                }
            }

            Event event = activity.getEvent();

            /*
             * ABPM-911: Saket: Message start events inside an event subprocess
             * behave almost exactly like intermediate message events now and
             * hence they should rather be suited for correlation type
             * "correlate".
             */
            if (event instanceof IntermediateEvent
                    || EventObjectUtil.isEventSubProcessStartEvent(activity)) {
                mode = CorrelationMode.CORRELATE;
            }
            return mode;
        }

        @Override
        protected String getNewRowFirstCellVal() {
            return null;
        }

        private Object[] pickParameters(Activity activity) {
            Object[] picked = null;
            DataFilterPicker picker = null;
            picker =
                    new DataFilterPicker(getShell(), DataPickerType.DATAFIELD,
                            true);
            if (activity != null) {
                picker.setScope(activity.getProcess());
                Set<DataField> prdList = getAssociatedFields(activity);
                if (prdList != null) {
                    picker.setInitialElementSelections(new ArrayList<ProcessRelevantData>(
                            prdList));
                }
                picker.addFilter(new IFilter() {

                    @Override
                    public boolean select(Object toTest) {
                        boolean ok = false;
                        if (toTest instanceof DataPickerItem) {
                            DataPickerItem pickerItem = (DataPickerItem) toTest;
                            Object item = pickerItem.getItem();
                            if (item instanceof DataField) {
                                DataField field = (DataField) item;
                                ok = field.isCorrelation();
                            }
                        }
                        return ok;
                    }

                });
            }
            if (picker != null && picker.open() == BaseObjectPicker.OK) {
                picked = picker.getResult();
            }
            return picked;
        }

        private Set<DataField> getAssociatedFields(Activity activity) {
            Set<DataField> prdList = new HashSet<DataField>();
            AssociatedCorrelationFields associatedParameters =
                    (AssociatedCorrelationFields) activity
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedCorrelationFields()
                                    .getName());
            if (associatedParameters != null
                    && associatedParameters.getAssociatedCorrelationField() != null) {
                List<AssociatedCorrelationField> associatedParameterList =
                        associatedParameters.getAssociatedCorrelationField();
                Process process = activity.getProcess();
                prdList =
                        resolveProcessRelevantData(process,
                                associatedParameterList);
            }
            return prdList;
        }

        private Set<DataField> resolveProcessRelevantData(Process process,
                List<AssociatedCorrelationField> associatedParameterList) {
            Set<DataField> prdList = new HashSet<DataField>();
            if (associatedParameterList != null) {
                for (AssociatedCorrelationField associatedParameter : associatedParameterList) {
                    DataField prd =
                            (DataField) EMFSearchUtil.findInList(process
                                    .getDataFields(),
                                    Xpdl2Package.eINSTANCE
                                            .getNamedElement_Name(),
                                    associatedParameter.getName());
                    if (prd != null) {
                        prdList.add(prd);
                    }
                }
            }
            return prdList;
        }
    }

    class CorrelationDataAssociationRemoveAction extends
            TableWithButtonsDeleteRowAction {

        public CorrelationDataAssociationRemoveAction(StructuredViewer viewer) {
            super(
                    viewer,
                    Messages.CorrelationDataAssociationControl_RemoveAssociationButton);
        }

        @Override
        protected void deleteRows(IStructuredSelection selection) {
            Object input = table.getViewer().getInput();
            if (input instanceof Activity) {
                Activity activity = (Activity) input;
                Collection<AssociatedCorrelationField> fields =
                        new ArrayList<AssociatedCorrelationField>();
                for (Object next : selection.toList()) {
                    if (next instanceof AssociatedCorrelationField) {
                        AssociatedCorrelationField field =
                                (AssociatedCorrelationField) next;
                        if (!fields.contains(field)) {
                            fields.add(field);
                        }
                    }
                }
                if (fields.size() != 0) {
                    EditingDomain ed =
                            WorkingCopyUtil.getEditingDomain(activity);
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.CorrelationDataAssociationControl_DisassociateDataCommand);
                    cmd.append(RemoveCommand.create(ed, fields));
                    if (cmd.canExecute()) {
                        ed.getCommandStack().execute(cmd);
                    }
                }
            }
        }
    }

    class CorrelationDataAssociationUpAction extends
            TableWithButtonsMoveRowUpAction {

        public CorrelationDataAssociationUpAction(StructuredViewer viewer) {
            super(
                    viewer,
                    Messages.CorrelationDataAssociationControl_MoveAssociationUpButton);
        }

        @Override
        protected void moveRowUp(Object rowData) {
            if (rowData instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) rowData;
                EObject parent = field.eContainer();
                if (parent instanceof AssociatedCorrelationFields) {
                    AssociatedCorrelationFields fields =
                            (AssociatedCorrelationFields) parent;
                    final EList<AssociatedCorrelationField> fieldList =
                            fields.getAssociatedCorrelationField();
                    final int index = fieldList.indexOf(field);
                    if (index > 0) {
                        TransactionalEditingDomain ed =
                                (TransactionalEditingDomain) WorkingCopyUtil
                                        .getEditingDomain(field);
                        RecordingCommand cmd = new RecordingCommand(ed) {

                            @Override
                            protected void doExecute() {
                                fieldList.move(index, index - 1);
                            }

                        };
                        if (cmd.canExecute()) {
                            ed.getCommandStack().execute(cmd);
                        }
                    }
                }
            }
        }
    }

    class CorrelationDataAssociationDownAction extends
            TableWithButtonsMoveRowDownAction {

        public CorrelationDataAssociationDownAction(StructuredViewer viewer) {
            super(
                    viewer,
                    Messages.CorrelationDataAssociationControl_MoveAssociationDownButton);
        }

        @Override
        protected void moveRowDown(Object rowData) {
            if (rowData instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) rowData;
                EObject parent = field.eContainer();
                if (parent instanceof AssociatedCorrelationFields) {
                    AssociatedCorrelationFields fields =
                            (AssociatedCorrelationFields) parent;
                    final EList<AssociatedCorrelationField> fieldList =
                            fields.getAssociatedCorrelationField();
                    final int index = fieldList.indexOf(field);
                    if (index != -1 && (index + 1) < fieldList.size()) {
                        TransactionalEditingDomain ed =
                                (TransactionalEditingDomain) WorkingCopyUtil
                                        .getEditingDomain(field);
                        RecordingCommand cmd = new RecordingCommand(ed) {

                            @Override
                            protected void doExecute() {
                                fieldList.move(index, index + 1);
                            }

                        };
                        if (cmd.canExecute()) {
                            ed.getCommandStack().execute(cmd);
                        }
                    }
                }
            }
        }
    }

    class ModeEditingSupport extends EditingSupport {

        private ComboBoxViewerCellEditor editor;

        public ModeEditingSupport(ColumnViewer viewer) {
            super(viewer);
            /*
             * XPD-6789: Saket: This editor should be read only.
             */
            editor =
                    new ComboBoxViewerCellEditor(table.getViewer().getTable(),
                            SWT.READ_ONLY);
            editor.setActivationStyle(ComboBoxViewerCellEditor.DROP_DOWN_ON_KEY_ACTIVATION
                    | ComboBoxViewerCellEditor.DROP_DOWN_ON_MOUSE_ACTIVATION
                    | ComboBoxViewerCellEditor.DROP_DOWN_ON_PROGRAMMATIC_ACTIVATION
                    | ComboBoxViewerCellEditor.DROP_DOWN_ON_TRAVERSE_ACTIVATION);
            editor.setContenProvider(new ModeContentProvider());
            editor.setLabelProvider(new ModeLabelProvider());
        }

        @Override
        protected boolean canEdit(Object element) {
            if (element instanceof AssociatedCorrelationField) {
                return true;
            }
            return false;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            editor.setInput(element);
            return editor;
        }

        @Override
        protected Object getValue(Object element) {
            CorrelationMode value = CorrelationMode.INITIALIZE;
            if (element instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) element;
                value = field.getCorrelationMode();
            }
            return value;
        }

        @Override
        protected void setValue(Object element, Object value) {
            if (element instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) element;
                EditingDomain ed = WorkingCopyUtil.getEditingDomain(field);
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.CorrelationDataAssociationControl_SetCorrelationModeCommand);
                cmd.append(SetCommand.create(ed,
                        field,
                        XpdExtensionPackage.eINSTANCE
                                .getAssociatedCorrelationField_CorrelationMode(),
                        value));
                if (cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);
                }
            }
        }

    }

    class DescriptionEditingSupport extends EditingSupport {

        private TextCellEditor editor;

        public DescriptionEditingSupport(ColumnViewer viewer) {
            super(viewer);
            editor = new TextCellEditor(table.getViewer().getTable());
        }

        @Override
        protected boolean canEdit(Object element) {
            if (element instanceof AssociatedCorrelationField) {
                return true;
            }
            return false;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return editor;
        }

        @Override
        protected Object getValue(Object element) {
            String value = ""; //$NON-NLS-1$
            if (element instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) element;
                Description desc = field.getDescription();
                if (desc != null) {
                    value = desc.getValue();
                }
            }
            return value;
        }

        @Override
        protected void setValue(Object element, Object value) {
            if (element instanceof AssociatedCorrelationField
                    && value instanceof String) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) element;
                Description desc = field.getDescription();
                EditingDomain ed = WorkingCopyUtil.getEditingDomain(field);
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.CorrelationDataAssociationControl_SetAssociationDescriptionCommand);
                if (desc == null) {
                    desc = Xpdl2Factory.eINSTANCE.createDescription();
                    desc.setValue((String) value);
                    cmd.append(SetCommand.create(ed,
                            field,
                            Xpdl2Package.eINSTANCE
                                    .getDescribedElement_Description(),
                            desc));
                } else {
                    cmd.append(SetCommand.create(ed,
                            desc,
                            Xpdl2Package.eINSTANCE.getDescription_Value(),
                            value));
                }
                if (cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);
                }
            }
        }

    }

    class ModeContentProvider implements IStructuredContentProvider {

        @Override
        public Object[] getElements(Object inputElement) {
            Object[] elements =
                    new Object[] { CorrelationMode.INITIALIZE,
                            CorrelationMode.CORRELATE, CorrelationMode.JOIN };
            return elements;
        }

        @Override
        public void dispose() {
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }

    class ModeLabelProvider extends LabelProvider {

        @Override
        public String getText(Object element) {
            String text;
            if (element instanceof CorrelationMode) {
                text = ((CorrelationMode) element).getName();
            } else {
                text = super.getText(element);
            }
            return text;
        }

    }
}
