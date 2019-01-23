/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.databaseservice.properties.table;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.implementer.nativeservices.NativeServicesActivator;
import com.tibco.xpd.implementer.nativeservices.NativeServicesConsts;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParameterType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.properties.utils.DataMappingUtil;
import com.tibco.xpd.implementer.nativeservices.databaseservice.properties.utils.DatabaseUtil;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
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
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ParameterTableForDBTabSection
 * 
 * 
 * @author bharge
 * @since 3.3 (17 Nov 2009)
 */
public class DBTabSectionParameterTable extends BaseTableControl {
    private final EditingDomain editingDomain;

    /**
     * IstructuredContentProvider - don't use this variable directly, use
     * {@link #getContentProvider()} instead.
     */
    private ContentProvider contentProvider;

    // Get error task image
    private Image problemImg = PlatformUI.getWorkbench().getSharedImages()
            .getImage(ISharedImages.IMG_OBJS_ERROR_TSK);

    private Image extImg = NativeServicesActivator.getDefault()
            .getImageRegistry().get(NativeServicesConsts.IMG_BOM);

    /**
     * @param parent
     * @param toolkit
     * @param viewerInput
     */
    public DBTabSectionParameterTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {
        super(parent, toolkit, null, false);
        this.editingDomain = editingDomain;
        createContents(parent, toolkit, null);
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
        new ParameterColumn(editingDomain, viewer);
        new TypeColumn(editingDomain, viewer);
        new DataFieldColumn(editingDomain, viewer);
        new LinkColumn(editingDomain, viewer);

        setColumnProportions(new float[] { 0.3f, 0.15f, 0.3f, 0.2f });
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
            contentProvider = new ContentProvider();
        }
        return contentProvider;
    }

    // TODO: comment the below three methods if up and down arrows are
    // not required.
    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * createMoveDownAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerMoveDownAction createMoveDownAction(ColumnViewer viewer) {
        return new TableMoveDownAction(viewer,
                Messages.TableComposite_MoveDownLabel,
                Messages.TableComposite_MoveDownLabel) {

            @Override
            protected void moveDown(Object element) {
                Command cmd =
                        getMoveParameterCommand(editingDomain,
                                getDatabase(),
                                new StructuredSelection(element),
                                false);

                // If there is a command the execute it
                if (cmd != null) {
                    editingDomain.getCommandStack().execute(cmd);
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
        return new TableMoveUpAction(viewer,
                Messages.TableComposite_MoveUpLabel,
                Messages.TableComposite_MoveUpLabel) {

            @Override
            protected void moveUp(Object element) {
                Command cmd =
                        getMoveParameterCommand(editingDomain,
                                getDatabase(),
                                new StructuredSelection(element),
                                true);

                // If there is a command the execute it
                if (cmd != null) {
                    editingDomain.getCommandStack().execute(cmd);
                }
            }

        };
    }

    /**
     * Get <code>Command</code> to move the parameter up or down in the table.
     * 
     * @param ed
     *            Editing domain
     * @param database
     *            the <code>DatabaseType</code> object that is affected
     * @param selection
     *            Selection in the table
     * @param moveUp
     *            Set to <b>true</b> if the parameter needs moving up and
     *            <b>false</b> if the parameter needs moving down
     * @return <code>Command</code> to update the parameter listing
     */
    private Command getMoveParameterCommand(EditingDomain ed,
            DatabaseType database, IStructuredSelection selection,
            boolean moveUp) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.DatabaseTabSection_MoveProcedureParamCmdLabel) {
                    @Override
                    public boolean canExecute() {
                        return true;
                    }
                };
        if (ed != null && database != null && selection != null
                && selection.getFirstElement() instanceof DataMapping) {
            TaskService taskService = getTaskServiceInput();
            DataMapping selectedMapping =
                    (DataMapping) selection.getFirstElement();

            List<DataMapping> messageInDataMappingsList =
                    new ArrayList<DataMapping>();
            List<DataMapping> messageOutDataMappingsList =
                    new ArrayList<DataMapping>();

            if (taskService.getMessageIn().getDataMappings().size() > 0)
                messageInDataMappingsList.addAll(taskService.getMessageIn()
                        .getDataMappings());
            if (taskService.getMessageOut().getDataMappings().size() > 0)
                messageOutDataMappingsList.addAll(taskService.getMessageOut()
                        .getDataMappings());

            int index = 0;
            if (selectedMapping.getDirection().equals(DirectionType.IN_LITERAL)
                    || selectedMapping.getDirection()
                            .equals(DirectionType.INOUT_LITERAL)) {
                index = messageInDataMappingsList.indexOf(selectedMapping);
                if (moveUp && index > 0) {
                    cmd.append(RemoveCommand.create(editingDomain,
                            selectedMapping));
                    cmd.append(AddCommand.create(editingDomain,
                            taskService.getMessageIn(),
                            Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                            selectedMapping,
                            index - 1));
                } else if (!moveUp
                        && index < messageInDataMappingsList.size() - 1) {
                    cmd.append(RemoveCommand.create(editingDomain,
                            selectedMapping));
                    cmd.append(AddCommand.create(editingDomain,
                            taskService.getMessageIn(),
                            Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                            selectedMapping,
                            index + 1));
                }
            } else if (selectedMapping.getDirection()
                    .equals(DirectionType.OUT_LITERAL)) {
                index = messageOutDataMappingsList.indexOf(selectedMapping);
                if (moveUp && index > 0) {
                    cmd.append(RemoveCommand.create(editingDomain,
                            selectedMapping));
                    cmd.append(AddCommand.create(editingDomain,
                            taskService.getMessageOut(),
                            Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                            selectedMapping,
                            index - 1));
                } else if (!moveUp && index < messageOutDataMappingsList.size()) {
                    cmd.append(RemoveCommand.create(editingDomain,
                            selectedMapping));
                    cmd.append(AddCommand.create(editingDomain,
                            taskService.getMessageOut(),
                            Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                            selectedMapping,
                            index + 1));
                }
            }
        }

        return cmd;
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
                Messages.TableComposite_NewParameterCmdLabel,
                Messages.TableComposite_NewParameterCmdLabel) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                TaskService taskService = getTaskServiceInput();
                String firstCellVal = getNewRowFirstCellVal();

                // Can't allow a blank or just spaces in the id
                if (taskService != null && editingDomain != null
                        && firstCellVal != null
                        && firstCellVal.trim().length() != 0) {

                    if (!doesParameterExist(firstCellVal)) {
                        // Get command to create default param
                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.TableComposite_NewParameterCmdLabel);

                        // creating a database object if it doesn't already
                        // exist
                        DatabaseType database =
                                DatabaseUtil.getDatabaseObj(taskService);
                        String implementationType =
                                (String) Xpdl2ModelUtil
                                        .getOtherAttribute(taskService,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ImplementationType());
                        if (null == database
                                && implementationType
                                        .equalsIgnoreCase(TaskImplementationTypeDefinitions.DATABASE_SERVICE)) {
                            CompoundCommand newcmd =
                                    new CompoundCommand(
                                            Messages.TableComposite_NewParameterCmdLabel);
                            database =
                                    DatabaseUtil
                                            .createDatabaseObj(editingDomain,
                                                    newcmd,
                                                    taskService);
                            if (null != newcmd && newcmd.canExecute()) {
                                // Execute the command
                                editingDomain.getCommandStack().execute(newcmd);
                            }
                        }
                        Object newRowData =
                                DatabaseUtil
                                        .createDefaultParameterCmd(editingDomain,
                                                taskService,
                                                cmd,
                                                firstCellVal);

                        if (cmd != null) {
                            // Execute the command
                            editingDomain.getCommandStack().execute(cmd);

                            return newRowData;
                        }
                    } else {
                        // Data mapping already exists with the given formal
                        // parameter
                        // name
                        showDuplicateParameterError(firstCellVal);
                    }
                }
                return null;
            }

            protected String getNewRowFirstCellVal() {
                return DataMappingUtil
                        .getUniqueParameterName(getTaskServiceInput(),
                                DirectionType.INOUT_LITERAL);

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
        return new TableDeleteAction(viewer,
                Messages.TableComposite_DeleteParamLabel,
                Messages.TableComposite_DeleteParamLabel) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {
                TaskService taskService = getTaskServiceInput();

                if (editingDomain != null && taskService != null
                        && selection != null && !selection.isEmpty()) {
                    List<DataMapping> mappingsToRemove =
                            new ArrayList<DataMapping>();

                    // Create a list of data mappings to remove
                    for (Iterator<?> iter = selection.iterator(); iter
                            .hasNext();) {
                        Object obj = iter.next();

                        if (obj instanceof DataMapping) {
                            // Add to list to remove
                            mappingsToRemove.add((DataMapping) obj);
                        }
                    }

                    if (!mappingsToRemove.isEmpty()) {
                        // Get command to remove the mappings
                        CompoundCommand cmd =
                                DatabaseUtil
                                        .getDataMappingsRemoveCmd(editingDomain,
                                                taskService,
                                                mappingsToRemove);

                        // If there is a command to execute then do it
                        if (!cmd.isEmpty()) {
                            editingDomain.getCommandStack().execute(cmd);
                        }
                    }
                }
            }

        };
    }

    /**
     * Get the input of this table.
     * 
     * @return
     */
    private EObject getInput() {
        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }

    /**
     * Get the <code>TaskService</code> input.
     * 
     * @return <code>TaskService</code> input. If the input is not a
     *         <code>TaskService</code> object then <b>null</b> will be
     *         returned.
     */
    private TaskService getTaskServiceInput() {

        if (getInput() instanceof TaskService) {
            return (TaskService) getInput();
        }

        return null;
    }

    /**
     * Check if a data mapping with the given formal parameter name exists in
     * the <code>TaskService</code> messages.
     * 
     * @param formalName
     * @return <b>true</b> if the data mapping exists, <b>false</b> if it
     *         doesn't or can't be determined.
     */
    private boolean doesParameterExist(String formalName) {
        boolean ret = false;
        TaskService taskService = getTaskServiceInput();

        if (taskService != null) {
            // Check if a data mapping with the given formal parameter name
            // exists in the MessageIn section.
            ret = doesParameterExist(formalName, taskService.getMessageIn());

            // If the parameter wasn't found then search the MessageOut
            if (!ret) {
                ret =
                        doesParameterExist(formalName,
                                taskService.getMessageOut());
            }
        }

        return ret;
    }

    /**
     * Check if a data mapping with the given formal parameter name exists in
     * the <code>Message</code>.
     * 
     * @param formalName
     * @param message
     * @return <b>true</b> if the parameter exists. If the parameter doesn't
     *         exist or cannot get a list of parameters from the
     *         <code>Message</code> <b>false</b> will be returned.
     */
    private boolean doesParameterExist(String formalName, Message message) {
        boolean ret = false;

        if (formalName != null && message != null
                && message.getDataMappings() != null) {
            for (Iterator<?> iter = message.getDataMappings().iterator(); iter
                    .hasNext() && !ret;) {
                DataMapping mapping = (DataMapping) iter.next();

                if (mapping != null) {
                    ret = formalName.equals(mapping.getFormal());
                }
            }
        }

        return ret;
    }

    /**
     * Show the duplicate parameter message.
     * 
     * @param paramName
     *            Name of the parameter being duplicated.
     */
    private void showDuplicateParameterError(String paramName) {
        MessageDialog
                .openError(getShell(),
                        Messages.TableComposite_DuplicateParamErrDlgTitle,
                        MessageFormat
                                .format(Messages.TableComposite_DuplicateParamErrDlgMsg,
                                        new Object[] { paramName }));
    }

    /**
     * Get the database extended object from the <code>TaskService</code> input.
     * 
     * @return <code>DatabaseType</code> object of the extended model. If the
     *         input is not available or no database object has been defined
     *         then <b>null</b> will be returned.
     */
    private DatabaseType getDatabase() {
        TaskService taskService = getTaskServiceInput();

        if (taskService != null) {
            return DatabaseUtil.getDatabaseObj(taskService);
        }

        return null;
    }

    private class ParameterColumn extends AbstractColumn {
        private final TextCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         */
        public ParameterColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.ColumnsEnum_ParameterColumn, 150);
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
            if (element instanceof DataMapping) {
                return editor;
            }
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
            Image img = null;
            if (element instanceof DataMapping) {
                DataMapping mapping = (DataMapping) element;
                boolean parameterNameUnique = true;
                TaskService taskService = getTaskService((DataMapping) element);

                if (taskService != null) {
                    parameterNameUnique =
                            DataMappingUtil.isParameterNameUnique(taskService,
                                    mapping.getFormal(),
                                    mapping.getDirection());
                }

                // If the parameter name is unique then show formal parameter
                // icon, otherwise show the error icon
                if (parameterNameUnique) {
                    img =
                            NativeServicesActivator
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(NativeServicesConsts.IMG_FORMALPARAMETER);
                } else {
                    img = problemImg;
                }
            }
            return img;
        }

        /**
         * Get the <code>TaskService</code> object that the given
         * <code>DataMapping</code> belongs to.
         * 
         * @param mapping
         * @return <code>TaskService</code> object on success, <b>null</b>
         *         otherwise.
         */
        private TaskService getTaskService(DataMapping mapping) {
            TaskService taskService = null;

            if (mapping != null) {
                // eContainer should be MessageIn or MessageOut
                if (mapping.eContainer() != null) {
                    // eContainer should be TaskService
                    if (mapping.eContainer().eContainer() instanceof TaskService) {
                        taskService =
                                (TaskService) mapping.eContainer().eContainer();
                    }
                }
            }

            return taskService;
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
            DataMapping mapping = null;
            CompoundCommand cmd = null;
            if (element instanceof DataMapping) {
                mapping = (DataMapping) element;
                cmd = new CompoundCommand();
                // If value is not string then set it to empty string
                if (!(value instanceof String)) {
                    value = ""; //$NON-NLS-1$
                }

                // Only update if the parameter name has changed and is not
                // blank
                String newValue = (String) value;

                if (newValue != null
                        && !newValue.trim().equals("") && !mapping.getFormal().equals(value)) { //$NON-NLS-1$
                    // Make sure the parameter name doesn't already exist
                    if (!doesParameterExist((String) value)) {
                        /*
                         * Parameter name changed - this will need the stored
                         * procedure parameter to be updated and the
                         * corresponding formal parameter in the TaskService
                         * message to be updated
                         */
                        DatabaseType database = getDatabase();

                        if (database != null) {
                            // Get the parameter mapped in the operation
                            ParameterType param =
                                    DatabaseUtil.getParameterByName(database,
                                            mapping.getFormal());

                            if (param != null) {
                                // Set command to change the parameter name
                                cmd.append(SetCommand.create(editingDomain,
                                        param,
                                        DatabasePackage.eINSTANCE
                                                .getParameterType_Name(),
                                        value));
                            }

                            // Update the data mapping
                            cmd.append(SetCommand.create(editingDomain,
                                    mapping,
                                    Xpdl2Package.eINSTANCE
                                            .getDataMapping_Formal(),
                                    value));

                            cmd.setLabel(Messages.TableComposite_ChangeParameterNameCmdLabel);
                        }

                    } else {
                        // Parameter already exists
                        showDuplicateParameterError((String) value);
                    }
                }
            }
            return cmd;
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
            if (element instanceof DataMapping) {
                DataMapping mapping = (DataMapping) element;
                text = mapping.getFormal();
            }
            return text != null ? text : ""; //$NON-NLS-1$
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

    private class TypeColumn extends AbstractColumn {
        private final ComboBoxViewerCellEditor editor;

        /**
         * internalised version of the enum valued Direction Types
         */
        private List<String> internalisedParameterTypes;

        /**
         * @param editingDomain
         * @param viewer
         */
        public TypeColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.None,
                    Messages.ColumnsEnum_ParameterTypeColumn, 75);
            /*
             * XPD-6789: Saket: This editor should be read only.
             */
            editor =
                    new ComboBoxViewerCellEditor(
                            (Composite) viewer.getControl(), SWT.READ_ONLY);
            String[] types = null;

            if (getInternalisedParameterTypes() != null) {
                types =
                        getInternalisedParameterTypes()
                                .toArray(new String[getInternalisedParameterTypes()
                                        .size()]);
            }
            editor.setContenProvider(new ArrayContentProvider());
            editor.setLabelProvider(new LabelProvider());
            editor.setInput(types);
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
            if (element instanceof DataMapping) {
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
            DataMapping mapping = null;
            CompoundCommand cmd = null;

            // Get the data mapping
            if (element instanceof DataMapping) {
                mapping = (DataMapping) element;
                cmd = new CompoundCommand();

                if (null != value) {
                    DirectionType newDirection =
                            DirectionType.get((String) value);
                    if (DatabaseUtil
                            .getInternalisedParamType(DirectionType.IN_LITERAL)
                            .equals(value)) {
                        newDirection = DirectionType.IN_LITERAL;
                    } else if (DatabaseUtil
                            .getInternalisedParamType(DirectionType.OUT_LITERAL)
                            .equals(value)) {
                        newDirection = DirectionType.OUT_LITERAL;
                    } else if (DatabaseUtil
                            .getInternalisedParamType(DirectionType.INOUT_LITERAL)
                            .equals(value)) {
                        newDirection = DirectionType.INOUT_LITERAL;
                    }

                    /*
                     * Change in the parameter type. If the parameter type is
                     * changed to OUT from IN or INOUT then the parameter should
                     * be moved from MessageIn section to the MessageOut
                     * section. Similarly, if an OUT parameter is changed to IN
                     * or INOUT then move to MessageIn section.
                     */
                    DirectionType currDirection = mapping.getDirection();

                    if (newDirection != null) {
                        // Only update the parameter type if it has
                        // changed
                        if (currDirection == null
                                || currDirection != newDirection) {
                            TaskService taskService = getTaskServiceInput();

                            if (taskService != null) {
                                cmd.setLabel(Messages.TableComposite_ChangeParameterTypeCmdLabel);
                                if (newDirection.getValue() == DirectionType.OUT) {
                                    /*
                                     * If new direction is OUT then move the
                                     * mapping to the MessageOut section
                                     */
                                    cmd.append(new MoveMappingCommand(
                                            editingDomain, taskService
                                                    .getMessageIn(),
                                            taskService.getMessageOut(),
                                            newDirection, mapping));

                                } else if (currDirection.getValue() == DirectionType.OUT) {
                                    /*
                                     * If the current direction is OUT then the
                                     * new direction has to be in the MessageIn
                                     * section as it is either IN or INOUT
                                     */
                                    cmd.append(new MoveMappingCommand(
                                            editingDomain, taskService
                                                    .getMessageOut(),
                                            taskService.getMessageIn(),
                                            newDirection, mapping));

                                } else {
                                    /*
                                     * This is a change from IN to INOUT or
                                     * vice-versa so just change the mapping's
                                     * direction type
                                     */
                                    cmd.append(SetCommand
                                            .create(editingDomain,
                                                    mapping,
                                                    Xpdl2Package.eINSTANCE
                                                            .getDataMapping_Direction(),
                                                    newDirection));
                                }
                            }
                        }
                    }
                }
            }
            return cmd;
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
            if (element instanceof DataMapping) {
                DataMapping mapping = (DataMapping) element;
                if (mapping.getDirection() != null) {
                    text =
                            DatabaseUtil.getInternalisedParamType(mapping
                                    .getDirection());
                }
            }
            return text != null ? text : ""; //$NON-NLS-1$
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

        /**
         * Get a list of internalised parameter types. This is the internalised
         * values of the <code>DirectionType</code> values.
         * 
         * @return
         */
        private List<String> getInternalisedParameterTypes() {

            if (internalisedParameterTypes == null) {
                List<?> types = DirectionType.VALUES;
                internalisedParameterTypes =
                        new ArrayList<String>(types.size());

                if (types != null) {
                    for (Object type : types) {
                        if (type instanceof DirectionType) {
                            internalisedParameterTypes
                                    .add(DatabaseUtil
                                            .getInternalisedParamType(((DirectionType) type)));
                        }
                    }
                }
            }

            return internalisedParameterTypes;
        }
    }

    private class DataFieldColumn extends AbstractColumn {
        private final DataFieldCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         */
        public DataFieldColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.ColumnsEnum_DataFieldColumn, 150);
            // This will be a data field text editor
            editor =
                    new DataFieldCellEditor(getTableViewer().getTable(),
                            new IDFProvider() {

                                @Override
                                public Activity getActivity() {
                                    if (getInput() != null) {
                                        EObject eo = getInput();
                                        // Get the activity container
                                        while (eo != null
                                                && !(eo instanceof Activity)) {
                                            eo = eo.eContainer();
                                        }
                                        if (eo instanceof Activity) {
                                            return (Activity) eo;
                                        }
                                    }
                                    return null;
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
            if (element instanceof DataMapping) {
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
            DataMapping mapping = null;
            CompoundCommand cmd = null;
            if (element instanceof DataMapping) {
                mapping = (DataMapping) element;
                cmd = new CompoundCommand();

                /*
                 * Change to data field - update the Actual parameter of the
                 * mapping if the data field has changed
                 */
                if (value instanceof ProcessRelevantData) {
                    value = ((ProcessRelevantData) value).getName();
                } else if (value instanceof ConceptPath) {
                    ConceptPath path = (ConceptPath) value;
                    if (path.getItem() instanceof ReturnTypeGenerator) {
                        ReturnTypeGenerator generator =
                                (ReturnTypeGenerator) path.getItem();
                        try {
                            Activity activity =
                                    Xpdl2ModelUtil
                                            .getParentActivity(getInput());
                            path = generator.generate(cmd, activity);
                        } catch (IOException e) {
                            Logger log =
                                    LoggerFactory
                                            .createLogger(NativeServicesActivator.PLUGIN_ID);
                            log.error(e);
                        } catch (CoreException e) {
                            Logger log =
                                    LoggerFactory
                                            .createLogger(NativeServicesActivator.PLUGIN_ID);
                            log.error(e);
                        }
                        if (path != null) {
                            value = path.getBaseName();
                        }
                    } else {
                        value = path.getBaseName();
                    }
                }
                if (!(value instanceof String)) {
                    value = ""; //$NON-NLS-1$
                }
                if ((mapping.getActual() == null && value != null)
                        || !mapping.getActual().getText().equals(value)) {
                    cmd.append(SetCommand.create(editingDomain,
                            mapping,
                            Xpdl2Package.eINSTANCE.getDataMapping_Actual(),
                            DatabaseUtil.createExpression((String) value)));
                }

            }
            return cmd;
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
            if (element instanceof DataMapping) {
                DataMapping mapping = (DataMapping) element;
                // Get data field
                text = DataFieldValueUtil.getLabelForDataMapping(mapping);
            }
            return text != null ? text : ""; //$NON-NLS-1$
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

    private class LinkColumn extends AbstractColumn {
        private final ExternalReferenceLinkCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         */
        public LinkColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE, "", 20); //$NON-NLS-1$
            editor = new ExternalReferenceLinkCellEditor(getTableViewer());
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
            if (element instanceof DataMapping) {
                return editor;
            }
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
            Image img = null;
            if (element instanceof DataMapping) {
                DataMapping mapping = (DataMapping) element;
                String formal = mapping.getFormal();
                if (formal != null) {
                    ConceptPath path =
                            DataFieldValueUtil
                                    .getConceptPathForDataMapping(mapping);
                    if (path != null) {
                        Object item = path.getItem();
                        if (item instanceof ProcessRelevantData) {
                            ProcessRelevantData prd =
                                    (ProcessRelevantData) item;
                            if (prd.getDataType() instanceof ExternalReference) {
                                img = extImg;
                            }
                        }
                    }
                }
            }
            return img;
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
            return null;
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

    private class MoveMappingCommand extends CompoundCommand {
        private EditingDomain editingDomain;

        // private Message fromMsg;

        private Message toMsg;

        private DirectionType newDirection;

        private DataMapping dataMapping;

        /**
         * @param editingDomain
         * @param fromMsg
         * @param toMsg
         * @param newDirection
         */
        public MoveMappingCommand(EditingDomain editingDomain, Message fromMsg,
                Message toMsg, DirectionType newDirection,
                DataMapping dataMapping) {
            super();
            this.editingDomain = editingDomain;
            // this.fromMsg = fromMsg;
            this.toMsg = toMsg;
            this.newDirection = newDirection;
            this.dataMapping = dataMapping;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
         */
        @Override
        public boolean canExecute() {
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.CompoundCommand#execute()
         */
        @Override
        public void execute() {
            super.appendAndExecute(RemoveCommand.create(editingDomain,
                    dataMapping));
            super.appendAndExecute(SetCommand.create(editingDomain,
                    dataMapping,
                    Xpdl2Package.eINSTANCE.getDataMapping_Direction(),
                    newDirection));
            super.appendAndExecute(AddCommand.create(editingDomain,
                    toMsg,
                    Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                    dataMapping));
        }

    }

}
