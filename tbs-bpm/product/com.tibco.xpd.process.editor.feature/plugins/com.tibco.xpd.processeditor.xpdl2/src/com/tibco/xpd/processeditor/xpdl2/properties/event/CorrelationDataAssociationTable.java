/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.providers.DataFilterPickerProviderHelper.DataPickerItem;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveUpAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
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
 * CorrelationDataAssociationTable
 * 
 * 
 * @author bharge
 * @since 3.3 (27 Oct 2009)
 */
public class CorrelationDataAssociationTable extends BaseTableControl {
    private final EditingDomain editingDomain;

    private IContentProvider contentProvider;

    /**
     * @param parent
     * @param toolkit
     */
    public CorrelationDataAssociationTable(Composite parent,
            XpdToolkit toolkit, EditingDomain editingDomain) {
        super(parent, toolkit, null, false);
        this.editingDomain = editingDomain;
        createContents(parent, toolkit, null);
        enableCellSpecificTooltips();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * getViewerContentProvider()
     */
    @Override
    protected IContentProvider getViewerContentProvider() {
        if (contentProvider == null) {
            return new CorrelationDataAssociationContentProvider();
        }
        return contentProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        new CorrelationDataNameColumn(editingDomain, viewer);
        new ModeColumn(editingDomain, viewer);
        new DescriptionColumn(editingDomain, viewer);

        setColumnProportions(new float[] { 0.3f, 0.15f, 0.55f });
    }

    /**
     * Get the input of this table.
     * 
     * @return
     */
    private Object getInput() {
        return getViewer() != null ? getViewer().getInput() : null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer,
                Messages.TaskParametersSection_AddLabel,
                Messages.CorrelationDataAssociationControl_AddAssociationButton) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                Object input = getInput();
                if (input instanceof Activity) {
                    Activity activity = (Activity) input;
                    Object[] picks = pickParameters(activity);
                    if (picks != null) {
                        Set<DataField> associated =
                                getAssociatedFields(activity);
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
                    // default to initialize, in-flow should default to
                    // correlate.
                    EList<Transition> inTrans =
                            activity.getIncomingTransitions();
                    if (inTrans != null && inTrans.size() > 0) {
                        mode = CorrelationMode.CORRELATE;
                    }
                }

                Event event = activity.getEvent();

                /*
                 * ABPM-911: Saket: Message start events inside an event
                 * subprocess behave almost exactly like intermediate message
                 * events now and hence they should rather be suited for
                 * correlation type "correlate".
                 */
                if (event instanceof IntermediateEvent
                        || EventObjectUtil
                                .isEventSubProcessStartEvent(activity)) {
                    mode = CorrelationMode.CORRELATE;
                }
                return mode;
            }

            private Object[] pickParameters(Activity activity) {
                Object[] picked = null;
                DataFilterPicker picker = null;
                picker =
                        new DataFilterPicker(getShell(),
                                DataPickerType.DATAFIELD, true);
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
                                DataPickerItem pickerItem =
                                        (DataPickerItem) toTest;
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
                            associatedParameters
                                    .getAssociatedCorrelationField();
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

        };
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(
                viewer,
                Messages.TaskParametersSection_RemoveLabel,
                Messages.CorrelationDataAssociationControl_RemoveAssociationButton) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {
                Object input = getInput();
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
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * createMoveDownAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerMoveDownAction createMoveDownAction(ColumnViewer viewer) {
        return new TableMoveDownAction(
                viewer,
                Messages.PropertiesSection_DownLabel,
                Messages.CorrelationDataAssociationControl_MoveAssociationDownButton) {

            @Override
            protected void moveDown(Object element) {
                if (element instanceof AssociatedCorrelationField) {
                    AssociatedCorrelationField field =
                            (AssociatedCorrelationField) element;
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
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * createMoveUpAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerMoveUpAction createMoveUpAction(ColumnViewer viewer) {
        return new TableMoveUpAction(
                viewer,
                Messages.PropertiesSection_UpLabel,
                Messages.CorrelationDataAssociationControl_MoveAssociationUpButton) {

            @Override
            protected void moveUp(Object element) {
                if (element instanceof AssociatedCorrelationField) {
                    AssociatedCorrelationField field =
                            (AssociatedCorrelationField) element;
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
        };
    }

    private class CorrelationDataNameColumn extends AbstractColumn {
        /**
         * @param editingDomain
         * @param viewer
         */
        public CorrelationDataNameColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.CorrelationDataAssociationControl_NameColumnTitle,
                    200);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getShowImage()
         */
        @Override
        protected boolean getShowImage() {
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java
         * .lang.Object)
         */
        @Override
        protected Image getImage(Object element) {
            if (element instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField associatedCorrelationField =
                        (AssociatedCorrelationField) element;

                DataField field =
                        ProcessInterfaceUtil
                                .getCorrelationFieldForCorrelationAssociation(associatedCorrelationField);
                if (field != null) {
                    return WorkingCopyUtil.getImage(field);
                }

                return WorkingCopyUtil.getImage(associatedCorrelationField);

            } else if (element instanceof ImplicitAssociatedParamObject) {
                return ((ImplicitAssociatedParamObject) element).getImage();
            }

            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getForeground
         * (java.lang.Object)
         */
        @Override
        protected Color getForeground(Object element) {
            Color color =
                    Display.getDefault()
                            .getSystemColor(SWT.COLOR_LIST_FOREGROUND);
            if (element instanceof ImplicitAssociatedParamObject) {
                color =
                        Display.getDefault()
                                .getSystemColor(SWT.COLOR_DARK_GRAY);

            } else if (element instanceof String) {
                color =
                        Display.getDefault()
                                .getSystemColor(SWT.COLOR_DARK_GRAY);
            }
            return color;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            String text = null;
            if (element instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) element;

                /*
                 * Use the label of the referenced data instead of the Name
                 * stored in the associated parameter.
                 */
                DataField data =
                        ProcessInterfaceUtil
                                .getCorrelationFieldForCorrelationAssociation(field);
                if (data != null) {
                    text = Xpdl2ModelUtil.getDisplayNameOrName(data);
                } else {
                    text = field.getName();
                }

            } else if (element instanceof ImplicitAssociatedParamObject) {
                ImplicitAssociatedParamObject implAss =
                        (ImplicitAssociatedParamObject) element;

                text = implAss.getParamName();

            } else if (element instanceof String) {
                text = (String) element;
            }
            return text;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getToolTipText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected String getToolTipText(Object element) {
            if (element instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) element;

                /*
                 * Use the label of the referenced data instead of the Name
                 * stored in the associated parameter.
                 */
                DataField data =
                        ProcessInterfaceUtil
                                .getCorrelationFieldForCorrelationAssociation(field);
                if (data != null) {
                    String label = Xpdl2ModelUtil.getDisplayNameOrName(data);
                    String name = data.getName();

                    if (!label.equals(name)) {
                        return String.format("%s (%s)", label, name); //$NON-NLS-1$
                    } else {
                        return label;
                    }
                }

                return field.getName();

            } else if (element instanceof ImplicitAssociatedParamObject) {
                ImplicitAssociatedParamObject implAss =
                        (ImplicitAssociatedParamObject) element;

                return implAss.getParamName();

            } else if (element instanceof String) {
                return (String) element;
            }

            return super.getToolTipText(element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    private class ModeColumn extends AbstractColumn {
        private ComboBoxViewerCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         */
        public ModeColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.CorrelationDataAssociationControl_ModeColumnTitle,
                    150);

            /*
             * XPD-6789: Saket: This editor should be read only.
             */
            editor =
                    new ComboBoxViewerCellEditor(
                            (Composite) viewer.getControl(), SWT.READ_ONLY);
            editor.setActivationStyle(ComboBoxViewerCellEditor.DROP_DOWN_ON_KEY_ACTIVATION
                    | ComboBoxViewerCellEditor.DROP_DOWN_ON_MOUSE_ACTIVATION
                    | ComboBoxViewerCellEditor.DROP_DOWN_ON_PROGRAMMATIC_ACTIVATION
                    | ComboBoxViewerCellEditor.DROP_DOWN_ON_TRAVERSE_ACTIVATION);
            editor.setContenProvider(new IStructuredContentProvider() {

                @Override
                public Object[] getElements(Object inputElement) {
                    Object[] elements =
                            new Object[] { CorrelationMode.CORRELATE
                            // , CorrelationMode.INITIALIZE,
                            // CorrelationMode.JOIN
                    };
                    return elements;
                }

                @Override
                public void dispose() {
                }

                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                }

            });

            editor.setLabelProvider(new LabelProvider() {

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

            });
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            /* Sid ACE-6338 do not allow selection of correlation mode in BPMe (always default 'Correlate') */
            /*
             * Sid ACE-6366 ... unless it is already *not* 'Correlate' in which case allow editing to allow user to
             * change it.
             */
            if (element instanceof AssociatedCorrelationField) {
                if (!CorrelationMode.CORRELATE.equals(((AssociatedCorrelationField) element).getCorrelationMode())) {
                    editor.setInput(element);
                    return editor;
                }
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getForeground
         * (java.lang.Object)
         */
        @Override
        protected Color getForeground(Object element) {
            Color color =
                    Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);

            if (element instanceof AssociatedCorrelationField) {
                color =
                        Display.getDefault()
                                .getSystemColor(SWT.COLOR_LIST_FOREGROUND);

            }
            return color;
            // return super.getForeground(element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) element;
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.CorrelationDataAssociationControl_SetCorrelationModeCommand);
                cmd.append(SetCommand
                        .create(editingDomain,
                                field,
                                XpdExtensionPackage.eINSTANCE
                                        .getAssociatedCorrelationField_CorrelationMode(),
                                value));
                /*
                 * XPD-5368: Saket- Ensuring that the implementations of
                 * AbstractColumn.getSetValueCommand() return the command and do
                 * not execute it.
                 */

                return cmd;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            String text = null;

            if (element instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) element;
                CorrelationMode mode = field.getCorrelationMode();
                if (mode != null) {
                    text = mode.getName();
                }

            } else if (element instanceof ImplicitAssociatedParamObject) {
                ImplicitAssociatedParamObject implAss =
                        (ImplicitAssociatedParamObject) element;

                text = implAss.getMode();
            }
            return text;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    private class DescriptionColumn extends AbstractColumn {
        private TextCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         */
        public DescriptionColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(
                    editingDomain,
                    viewer,
                    SWT.NONE,
                    Messages.CorrelationDataAssociationControl_DescriptionColumnTitle,
                    350);
            editor = new TextCellEditor((Composite) viewer.getControl());
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof AssociatedCorrelationField) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof AssociatedCorrelationField
                    && value instanceof String) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) element;
                Description desc = field.getDescription();
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.CorrelationDataAssociationControl_SetAssociationDescriptionCommand);
                if (desc == null) {
                    desc = Xpdl2Factory.eINSTANCE.createDescription();
                    desc.setValue((String) value);
                    cmd.append(SetCommand.create(editingDomain,
                            field,
                            Xpdl2Package.eINSTANCE
                                    .getDescribedElement_Description(),
                            desc));
                } else {
                    cmd.append(SetCommand.create(editingDomain,
                            desc,
                            Xpdl2Package.eINSTANCE.getDescription_Value(),
                            value));
                }
                /*
                 * XPD-5368: Saket- Ensuring that the implementations of
                 * AbstractColumn.getSetValueCommand() return the command and do
                 * not execute it.
                 */

                return cmd;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getForeground
         * (java.lang.Object)
         */
        @Override
        protected Color getForeground(Object element) {
            Color color =
                    Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY);
            if (element instanceof AssociatedCorrelationField) {
                color =
                        Display.getDefault()
                                .getSystemColor(SWT.COLOR_LIST_FOREGROUND);
            }
            return color;
            // return super.getForeground(element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            String text = null;

            if (element instanceof AssociatedCorrelationField) {
                AssociatedCorrelationField field =
                        (AssociatedCorrelationField) element;
                Description desc = field.getDescription();
                if (desc != null) {
                    text = desc.getValue();
                }

            } else if (element instanceof ImplicitAssociatedParamObject) {
                ImplicitAssociatedParamObject implAss =
                        (ImplicitAssociatedParamObject) element;

                text = implAss.getDescription();
            }
            return text != null ? text : "";
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

}
