/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.properties;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.datatools.connectivity.ui.navigator.ConnectionProfileContentProvider;
import org.eclipse.datatools.connectivity.ui.navigator.ConnectionProfileLabelProvider;
import org.eclipse.datatools.modelbase.sql.query.QueryExpressionRoot;
import org.eclipse.datatools.modelbase.sql.query.QuerySelectStatement;
import org.eclipse.datatools.modelbase.sql.query.QueryStatement;
import org.eclipse.datatools.modelbase.sql.query.SQLQueryModelFactory;
import org.eclipse.datatools.modelbase.sql.query.util.SQLQuerySourceFormat;
import org.eclipse.datatools.modelbase.sql.query.util.SQLQuerySourceInfo;
import org.eclipse.datatools.modelbase.sql.routines.Function;
import org.eclipse.datatools.modelbase.sql.routines.Parameter;
import org.eclipse.datatools.modelbase.sql.routines.Routine;
import org.eclipse.datatools.modelbase.sql.schema.Schema;
import org.eclipse.datatools.sqltools.editor.core.connection.ISQLEditorConnectionInfo;
import org.eclipse.datatools.sqltools.sqlbuilder.model.IOmitSchemaInfo;
import org.eclipse.datatools.sqltools.sqlbuilder.model.OmitSchemaInfo;
import org.eclipse.datatools.sqltools.sqlbuilder.model.SQLBuilderConnectionInfo;
import org.eclipse.datatools.sqltools.sqlbuilder.views.source.SQLEditorDocumentProvider;
import org.eclipse.datatools.sqltools.sqlbuilder.views.source.SQLSourceEditingEnvironment;
import org.eclipse.datatools.sqltools.sqlbuilder.views.source.SQLSourceViewerConfiguration;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.texteditor.DefaultRangeIndicator;

import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseFactory;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParameterType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParametersType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.properties.utils.DatabaseUtil;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract class that defines the common behaviour of the property view
 * sections for the database service.
 * <p>
 * Use the following method to create a label control with a standard width:
 * <ul>
 * <li><code>{@link #createLabel(XpdFormToolkit, Composite, String)}</code>.</li>
 * </ul>
 * </p>
 * <p>
 * Use the following methods to create a text control with a standard width:
 * <ul>
 * <li><code>{@link #createText(XpdFormToolkit, Composite)}</code>.</li>
 * <li><code>{@link #createText(XpdFormToolkit, Composite, EAttribute)}</code>.</li>
 * </ul>
 * </p>
 * <p>
 * The following methods are provided to handle the Database model:
 * <ul>
 * <li><code>{@link #getDatabaseObj(TaskService)}</code> - Get the database
 * object from the XPDL2 model</li>
 * <li>
 * <code>{@link #createDatabaseObj(EditingDomain, CompoundCommand, TaskService)}</code>
 * - Create a database object in the given <code>TaskService</code> object</li>
 * </ul>
 * </p>
 * 
 * @author njpatel
 */
public abstract class AbstractDatabaseSection extends
        AbstractFilteredTransactionalSection {

    private Button stored;

    private Button query;

    private static final String SELECT_STATEMENT = "SELECT"; //$NON-NLS-1$

    private static final String QUERY = "QUERY"; //$NON-NLS-1$

    private static final String STORED = "STORED"; //$NON-NLS-1$

    private ScrolledPageBook book;

    private Text storedName;

    private SourceViewer sourceViewer;

    private Button sqlEditor;

    private Button storedProcedurePicker;

    private Button resetStoredProcedurePropertySheet;

    private Button resetSQLPropertySheet;

    /**
     * This would help us to store the context of the button (sql OR stored)
     * which is currently selected so that we can reset things ONLY when we
     * switch between 'sql' to 'stored' OR vice versa (and not when we are
     * already on 'sql' and we select it again.)
     */
    private Button operationButtonToggled;

    private final DatabasePackage dbPackage = DatabasePackage.eINSTANCE;

    private Composite storedPage;

    private Composite queryPage;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    public AbstractDatabaseSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * Get the <code>DatabaseType</code> extended object from the given
     * <code>TaskService</code> object.
     * 
     * @see DatabaseUtil#getDatabaseObj(TaskService)
     * 
     * @return The <code>DatabaseType</code> object if set.
     */
    protected DatabaseType getDatabaseObj(TaskService taskService) {
        return DatabaseUtil.getDatabaseObj(taskService);
    }

    /**
     * Create the <code>DatabaseType</code> object and add it to the given
     * <code>TaskService</code> object.
     * 
     * @param ed
     *            <code>EditingDomain</code> to use for the command.
     * @param cmd
     *            <code>CompoundCommand</code> to append the Add command to
     *            create the database object.
     * @param taskService
     *            Owner object to add the new database object to.
     * 
     * @see DatabaseUtil#createDatabaseObj(EditingDomain, CompoundCommand,
     *      TaskService)
     * 
     * @return The new <code>DatabaseType</code> object .
     */
    protected DatabaseType createDatabaseObj(EditingDomain ed,
            CompoundCommand cmd, TaskService taskService) {

        return DatabaseUtil.createDatabaseObj(ed, cmd, taskService);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite container = toolkit.createComposite(parent);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        container.setLayout(gridLayout);

        // Display operation selection
        createLabel(toolkit,
                container,
                Messages.DatabaseServiceSection_OperationLabel);
        Composite opContainer = doCreateOperationsControl(container, toolkit);
        opContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // Create PageBook
        book = toolkit.createPageBook(container, SWT.NONE);
        book.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

        storedPage = book.createPage(STORED);
        gridLayout = new GridLayout(4, false);
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        storedPage.setLayout(gridLayout);
        queryPage = book.createPage(QUERY);
        gridLayout = new GridLayout(4, false);
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        queryPage.setLayout(gridLayout);

        // Stored procedure name
        createLabel(toolkit,
                storedPage,
                Messages.DatabaseServiceSection_StoredProcedureLabel);
        storedName = toolkit.createText(storedPage, "", SWT.BORDER //$NON-NLS-1$
                | SWT.FLAT, ""); //$NON-NLS-1$
        storedName
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        manageControl(storedName);

        storedProcedurePicker = toolkit.createButton(storedPage, "...", //$NON-NLS-1$
                SWT.PUSH,
                "databaseSectionStoredProcedurePicker"); //$NON-NLS-1$
        storedProcedurePicker.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP,
                false, false));
        manageControl(storedProcedurePicker);

        /*
         * Saket XPD-2403: Adding 'Reset' button to clear the stored procedure
         * name and its parameters in one click.
         */
        resetStoredProcedurePropertySheet =
                toolkit.createButton(storedPage,
                        Messages.AbstractDatabaseSection_resetPropertySheets_ButtonLabel,
                        SWT.PUSH,
                        "databaseSectionResetStoredProcedurePropertySheet"); //$NON-NLS-1$
        resetStoredProcedurePropertySheet.setLayoutData(new GridData(SWT.RIGHT,
                SWT.TOP, false, false));
        manageControl(resetStoredProcedurePropertySheet);

        /*
         * XPD-2403
         */

        // SQL Source Viewer
        createLabel(toolkit,
                queryPage,
                Messages.DatabaseServiceSection_SqlLabel_notrans);

        SQLSourceViewerConfiguration configuration =
                new SQLSourceViewerConfiguration();

        SQLEditorDocumentProvider documentProvider =
                new SQLEditorDocumentProvider();
        SQLSourceEditingEnvironment.connect();

        int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER;
        sourceViewer =
                new SourceViewer(queryPage, new VerticalRuler(12), styles);
        sourceViewer.setRangeIndicator(new DefaultRangeIndicator());
        sourceViewer.configure(configuration);

        sourceViewer.getControl().setLayoutData(new GridData(SWT.FILL,
                SWT.FILL, true, true));
        QueryStatement empty = getEmptyStatement();

        try {
            documentProvider.connect(empty);
        } catch (CoreException e) {
            LOG.error(e);
        }
        sourceViewer.setDocument(documentProvider.getDocument(empty));

        /*
         * XPD-4852: Saket: Earlier we had
         * manageControlUpdateOnDeactivate(StyledText) in place of
         * manageControl(StyledText) here. But then the user had to click
         * outside the SQL editor in order to enable the 'Save' action which
         * this JIRA talks about. Hence we have changed this to fix this issue.
         */
        manageControl(sourceViewer.getTextWidget());

        sqlEditor = toolkit.createButton(queryPage, "...", //$NON-NLS-1$
                SWT.PUSH,
                "databaseSectionSqlEditor"); //$NON-NLS-1$
        sqlEditor.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));
        manageControl(sqlEditor);

        /*
         * Saket XPD-2403: Adding 'Reset' button to clear the SQL editor and
         * parameters associated with it in one click.
         */
        resetSQLPropertySheet =
                toolkit.createButton(queryPage,
                        Messages.AbstractDatabaseSection_resetPropertySheets_ButtonLabel,
                        SWT.PUSH,
                        "databaseSectionSqlEditor"); //$NON-NLS-1$
        resetSQLPropertySheet.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP,
                false, false));
        manageControl(resetSQLPropertySheet);

        /*
         * XPD-2403
         */

        return container;
    }

    /**
     * Create the operations options radio buttons.
     * 
     * @param container
     * @param toolkit
     * @return Composite containing the operation selection
     */
    private Composite doCreateOperationsControl(Composite parent,
            XpdFormToolkit toolkit) {
        Composite container = toolkit.createComposite(parent);
        container.setLayout(new RowLayout());

        // When query is chosen default to select.
        query =
                toolkit.createButton(container,
                        Messages.AbstractDatabaseSection_QueryButtonLabel,
                        SWT.RADIO,
                        "databaseSectionQueryButton"); //$NON-NLS-1$
        query.setData(SqlType.UPDATE_LITERAL);
        manageControl(query);

        stored =
                toolkit.createButton(container,
                        SqlType.STORED_PROC_LITERAL.getName(),
                        SWT.RADIO,
                        "databaseSectionStoredProcedureButton"); //$NON-NLS-1$
        stored.setData(SqlType.STORED_PROC_LITERAL);
        manageControl(stored);

        return container;
    }

    /**
     * Set the radio button associated with the <i>type</i> to selected.
     * 
     * @param type
     */
    protected void setOperationBtn(SqlType type) {
        if (SqlType.STORED_PROC_LITERAL.equals(type)) {
            stored.setSelection(true);
            query.setSelection(false);
        } else {
            stored.setSelection(false);
            query.setSelection(true);
        }
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        Widget widget = (Widget) obj;

        if (widget != null) {
            TaskService taskService = getTaskServiceInput();

            if (taskService != null) {

                if (widget instanceof StyledText) {

                    if (taskService != null) {

                        String sql = sourceViewer.getTextWidget().getText();
                        if (!sql.equals(getCurrentSql())) {
                            SqlType type = SqlType.UPDATE_LITERAL;
                            if (isSQLSelectStatement(sql)) {
                                type = SqlType.SELECT_LITERAL;
                            }
                            cmd = getSetSqlCommand(taskService, sql, type);
                        }
                    }
                } else if (widget instanceof Text) {
                    if (taskService != null) {

                        String sql = storedName.getText();
                        if (!sql.equals(getCurrentSql())) {
                            cmd =
                                    getSetSqlCommand(taskService,
                                            sql,
                                            SqlType.STORED_PROC_LITERAL);
                        }
                    }
                } else if (widget instanceof Button) {
                    Button btn = (Button) widget;
                    if (btn == sqlEditor) {
                        openEditor();
                    } else if (btn == storedProcedurePicker) {
                        Routine routine = pickStoredProcedure();
                        if (routine != null) {
                            String routineName = routine.getName();
                            String name = null;
                            Schema schema = routine.getSchema();
                            if (schema != null) {
                                String schemaName = schema.getName();
                                name = String.format("%1$s%2$s%3$s", //$NON-NLS-1$
                                        schemaName,
                                        ".", //$NON-NLS-1$
                                        routineName);
                            } else {
                                name = routineName;
                            }

                            if (!name.equals(storedName.getText())) {
                                Parameter returnParam = null;
                                if (routine instanceof Function) {
                                    Function function = (Function) routine;
                                    returnParam = function.getReturnCast();
                                    if (returnParam == null) {
                                        returnParam =
                                                function.getReturnScalar();
                                    }
                                }
                                cmd =
                                        getSetSqlCommand(taskService,
                                                name,
                                                SqlType.STORED_PROC_LITERAL,
                                                routine.getInputParameters(),
                                                routine.getOutputParameters(),
                                                returnParam);
                            }
                        }
                    } else if (btn == resetStoredProcedurePropertySheet) {
                        cmd =
                                getInitialiseToSqlTypeCommand(taskService,
                                        SqlType.STORED_PROC_LITERAL,
                                        true);
                    } else if (btn == resetSQLPropertySheet) {
                        cmd =
                                getInitialiseToSqlTypeCommand(taskService,
                                        SqlType.SELECT_LITERAL,
                                        true);
                    } else {
                        if (btn.getData() != null
                                && btn.getData() instanceof SqlType) {

                            if (operationButtonToggled != btn) {
                                /*
                                 * XPD-6048: Saket: We want to initialize the
                                 * SQL editor only when we switch between SQL
                                 * and Stored Procedures.
                                 */
                                operationButtonToggled = btn;
                                cmd =
                                        getInitialiseToSqlTypeCommand(taskService,
                                                (SqlType) btn.getData(),
                                                false);
                            }
                        }
                    }
                }
            }
        }

        return cmd;
    }

    /**
     * This method returns a compound command which comprises the commands that
     * set the type of the database service task to the SqlType specified in the
     * parameters and also removes the parameters defined in the database
     * service task. Hence the state of the database service task is set back to
     * the state when it was just created.
     * 
     * @param taskService
     * @param newType
     * @return compoundCommand
     */
    private Command getInitialiseToSqlTypeCommand(TaskService taskService,
            SqlType newType, boolean forceReset) {
        DatabaseType database = getDatabaseObj(taskService);
        CompoundCommand compoundCmd =
                new CompoundCommand(
                        Messages.DatabaseServiceSection_SetOperationTypeCommand);

        if (database == null || database.getOperation() == null
                || hasTypeChanged(newType, database) || forceReset) {
            EditingDomain ed = getEditingDomain();

            // If the database object hasn't been created
            // then do so
            if (database == null) {
                database = createDatabaseObj(ed, compoundCmd, taskService);
            }

            if (ed != null) {
                OperationType operation =
                        DatabaseFactory.eINSTANCE.createOperationType();
                operation.setType(newType);

                if (database.eContainer() != null) {
                    compoundCmd.append(SetCommand.create(ed,
                            database,
                            DatabasePackage.eINSTANCE
                                    .getDatabaseType_Operation(),
                            operation));
                } else {
                    database.setOperation(operation);
                }

                /*
                 * Clear the data mappings (parameters)
                 */
                if (taskService.getMessageIn() != null
                        && taskService.getMessageIn().getDataMappings() != null
                        && !taskService.getMessageIn().getDataMappings()
                                .isEmpty()) {

                    compoundCmd.append(DeleteCommand.create(ed, taskService
                            .getMessageIn().getDataMappings()));
                }

                if (taskService.getMessageOut() != null
                        && taskService.getMessageOut().getDataMappings() != null
                        && !taskService.getMessageOut().getDataMappings()
                                .isEmpty()) {

                    compoundCmd.append(DeleteCommand.create(ed, taskService
                            .getMessageOut().getDataMappings()));
                }
            }
        }
        return (compoundCmd);
    }

    private boolean hasTypeChanged(SqlType chosen, DatabaseType database) {
        SqlType current = database.getOperation().getType();
        return !(chosen.equals(current));
    }

    private Object getCurrentSql() {
        String sql = null;
        DatabaseType database = getDatabaseObj(getTaskServiceInput());

        if (database != null) {
            OperationType operation = database.getOperation();
            if (operation != null) {
                sql = operation.getSql();
            }
        }
        return sql;
    }

    private Command getSetSqlCommand(TaskService taskService, String sql,
            SqlType sqlType) {
        return getSetSqlCommand(taskService, sql, sqlType, null, null, null);
    }

    private Command getSetSqlCommand(TaskService taskService, String sql,
            SqlType sqlType, List<?> inputParameters, List<?> outputParameters,
            Parameter returnParam) {
        EStructuralFeature feat =
                DatabasePackage.eINSTANCE.getOperationType_Sql();
        Command cmd = null;
        EditingDomain ed = getEditingDomain();
        // Get the database object from the input
        DatabaseType database = getDatabaseObj(taskService);
        if (ed != null) {
            CompoundCommand compoundCmd = new CompoundCommand();

            // If the database object hasn't been created
            // then do so
            if (database == null) {
                database = createDatabaseObj(ed, compoundCmd, taskService);
            }

            if (database != null) {
                compoundCmd
                        .setLabel(Messages.DatabaseServiceSection_SetSqlCommand);
                // Update the sql for the operation
                OperationType operation = database.getOperation();

                // If new value is null set to ""
                if (sql == null) {
                    sql = ""; //$NON-NLS-1$
                }

                // If the procedure obj has not been
                // created
                // then do so
                if (operation == null) {
                    operation = DatabaseFactory.eINSTANCE.createOperationType();
                    operation.setSql(sql);
                    operation.setType(sqlType);

                    if (database.eContainer() != null) {
                        compoundCmd.append(SetCommand.create(ed,
                                database,
                                dbPackage.getDatabaseType_Operation(),
                                operation));
                    } else {
                        database.setOperation(operation);
                    }
                } else {
                    // Set the new procedure name
                    compoundCmd.append(SetCommand.create(ed,
                            operation,
                            feat,
                            sql));
                    compoundCmd.append(SetCommand.create(ed,
                            operation,
                            DatabasePackage.eINSTANCE.getOperationType_Type(),
                            sqlType));

                }
                if (inputParameters != null || outputParameters != null
                        || returnParam != null) {
                    ParametersType paramsType =
                            DatabaseFactory.eINSTANCE.createParametersType();
                    Message in = taskService.getMessageIn();
                    if (in.getDataMappings().size() != 0) {
                        compoundCmd.append(RemoveCommand.create(ed,
                                in,
                                Xpdl2Package.eINSTANCE
                                        .getMessage_DataMappings(),
                                in.getDataMappings()));
                    }
                    Message out = taskService.getMessageOut();
                    if (out.getDataMappings().size() != 0) {
                        compoundCmd.append(RemoveCommand.create(ed,
                                out,
                                Xpdl2Package.eINSTANCE
                                        .getMessage_DataMappings(),
                                out.getDataMappings()));
                    }
                    if (inputParameters != null) {
                        for (Object next : inputParameters) {
                            String name = ""; //$NON-NLS-1$
                            if (next instanceof Parameter) {
                                Parameter parameter = (Parameter) next;
                                name = parameter.getName();
                            }
                            ParameterType type =
                                    DatabaseFactory.eINSTANCE
                                            .createParameterType();
                            type.setName(name);
                            paramsType.getParameter().add(type);
                            DataMapping mapping =
                                    Xpdl2Factory.eINSTANCE.createDataMapping();
                            mapping.setDirection(DirectionType.IN_LITERAL);
                            mapping.setFormal(name);
                            compoundCmd.append(AddCommand.create(ed,
                                    in,
                                    Xpdl2Package.eINSTANCE
                                            .getMessage_DataMappings(),
                                    mapping));
                        }
                    }
                    if (outputParameters != null) {
                        for (Object next : outputParameters) {
                            String name = ""; //$NON-NLS-1$
                            if (next instanceof Parameter) {
                                Parameter parameter = (Parameter) next;
                                name = parameter.getName();
                            }
                            ParameterType type =
                                    DatabaseFactory.eINSTANCE
                                            .createParameterType();
                            type.setName(name);
                            paramsType.getParameter().add(type);
                            DataMapping mapping =
                                    Xpdl2Factory.eINSTANCE.createDataMapping();
                            mapping.setDirection(DirectionType.OUT_LITERAL);
                            mapping.setFormal(name);
                            compoundCmd.append(AddCommand.create(ed,
                                    out,
                                    Xpdl2Package.eINSTANCE
                                            .getMessage_DataMappings(),
                                    mapping));
                        }
                    }
                    if (returnParam != null) {
                        String name = returnParam.getName();
                        ParameterType type =
                                DatabaseFactory.eINSTANCE.createParameterType();
                        type.setName(name);
                        DataMapping mapping =
                                Xpdl2Factory.eINSTANCE.createDataMapping();
                        mapping.setDirection(DirectionType.OUT_LITERAL);
                        mapping.setFormal(name);
                        compoundCmd.append(AddCommand.create(ed,
                                out,
                                Xpdl2Package.eINSTANCE
                                        .getMessage_DataMappings(),
                                mapping));
                    }
                    compoundCmd.append(SetCommand.create(ed,
                            operation,
                            DatabasePackage.eINSTANCE
                                    .getOperationType_Parameters(),
                            paramsType));
                }
            }

            cmd = compoundCmd;
        }
        return cmd;
    }

    @Override
    protected void doRefresh() {
        DatabaseType database = getDatabaseObj(getTaskServiceInput());
        String page = QUERY;
        SqlType sqlType = SqlType.SELECT_LITERAL;

        if (database != null) {
            OperationType operation = database.getOperation();
            if (operation != null) {

                String name = operation.getSql();
                // Set the operation type
                sqlType = operation.getType();
                if (SqlType.STORED_PROC_LITERAL.equals(sqlType)) {
                    if (name == null) {
                        name = ""; //$NON-NLS-1$
                    }

                    /*
                     * XPD-6554: Saket: Initially we thought that we'd always
                     * have 'SQL' operation selected on startup, so we were
                     * setting 'operationButtonToggled' to 'sql' while creating
                     * controls. But while implementing iPM to BPM conversion
                     * framework, we had cases wherein the operation was set to
                     * 'Stored procedure' at the startup and hence we ended up
                     * doing 'nothing' when we switched from 'Stored procedure'
                     * to 'sql' for the 'first time' (because we reset the
                     * editor only when we switch between operations. So even
                     * when we 'actually' switched from 'Stored procedure' to
                     * 'SQL' for the first time, 'programatically' we were on
                     * the same button because 'operationButtonToggled' was
                     * already set to 'sql' :)). So the most apt way to fix this
                     * was to set 'operationButtonToggled' to 'stored' here.
                     */
                    operationButtonToggled = stored;

                    page = STORED;
                    updateText(storedName, name);
                } else {

                    /*
                     * XPD-6048: Saket: Since 'query' is being set as the
                     * default button at the start, we should initialize
                     * 'operationButtonToggled' with that.
                     */
                    operationButtonToggled = query;

                    if (name == null) {
                        name = ""; //$NON-NLS-1$
                    }
                    /*
                     * XPD-4852: Saket: In order to prevent the cursor from
                     * resetting to the starting position after every short
                     * delay (since now we update the SQL editor on
                     * timed-basis), we call the method
                     * updateStyledText(StyledText,String)
                     */
                    // sourceViewer.getTextWidget().setText(name);
                    updateText(sourceViewer.getTextWidget(), name);
                }
            } else {
                sourceViewer.getTextWidget().setText(""); //$NON-NLS-1$
            }
        } else {
            sourceViewer.getTextWidget().setText(""); //$NON-NLS-1$
        }
        setOperationBtn(sqlType);
        Control toShow = QUERY.equals(page) ? queryPage : storedPage;
        if (toShow != book.getCurrentPage()) {
            book.showPage(page);
        }

    }

    private QueryStatement getEmptyStatement() {
        QuerySelectStatement query =
                SQLQueryModelFactory.eINSTANCE.createQuerySelectStatement();
        query.setName(""); //$NON-NLS-1$
        QueryExpressionRoot root =
                SQLQueryModelFactory.eINSTANCE.createQueryExpressionRoot();
        query.setQueryExpr(root);
        SQLQuerySourceInfo info = new SQLQuerySourceInfo();
        SQLQuerySourceFormat sqlFormat =
                SQLQuerySourceFormat.SQL_SOURCE_FORMAT_DEFAULT;
        info.setSqlFormat(sqlFormat);
        query.setSourceInfo(info);
        return query;
    }

    private Routine pickStoredProcedure() {
        Routine routine = null;
        IConnectionProfile profile = getConnectionProfile();
        if (profile != null) {
            Shell shell = getSite().getShell();
            IStatus status = DatabaseUtil.connect(shell, profile);
            if (Status.OK_STATUS.equals(status)) {
                Object item = null;
                CommonNavigatorPicker dialog = new CommonNavigatorPicker(shell);
                dialog.addFilter(new ActivityDatabaseFilter());
                dialog.setTitle(Messages.AbstractDatabaseSection_SelectStoredProcedureMessage);
                dialog.setMessage(Messages.AbstractDatabaseSection_SelectStoredProcedureMessage);
                dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
                if (dialog.open() == ElementTreeSelectionDialog.OK) {
                    item = dialog.getFirstResult();
                }
                Object picked = item;
                if (picked instanceof Routine) {
                    routine = (Routine) picked;
                }
            }
        }
        return routine;
    }

    private void openEditor() {
        IConnectionProfile profile = getConnectionProfile();
        EObject input = getInput();

        if (profile != null && input != null) {
            Shell shell = getSite().getShell();
            IStatus status = DatabaseUtil.connect(shell, profile);
            if (Status.OK_STATUS.equals(status)) {
                String url = EcoreUtil.getURI(input).toString();
                IStorage storage = new DatabaseStorage();
                EmbeddedSQLBuilderStorageEditorInput sqlBuilderEditorInput =
                        new EmbeddedSQLBuilderStorageEditorInput(url, storage);
                ISQLEditorConnectionInfo connInfo =
                        new SQLBuilderConnectionInfo(profile);
                IOmitSchemaInfo omit = new OmitSchemaInfo();
                sqlBuilderEditorInput.setConnectionInfo(connInfo);
                sqlBuilderEditorInput.setOmitSchemaInfo(omit);
                IWorkbenchPage page =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage();
                try {
                    IEditorPart editor =
                            IDE.openEditor(page,
                                    sqlBuilderEditorInput,
                                    "com.tibco.xpd.implementer.nativeservices.sqlbuilder"); //$NON-NLS-1$
                    if (editor != null) {
                        IEditorInput currentInput = editor.getEditorInput();
                        if (currentInput instanceof EmbeddedSQLBuilderStorageEditorInput) {
                            EmbeddedSQLBuilderStorageEditorInput currentEmbeddedInput =
                                    (EmbeddedSQLBuilderStorageEditorInput) currentInput;
                            String sql = currentEmbeddedInput.getSQL();
                            if (sql != null
                                    && !sql.equals(sqlBuilderEditorInput
                                            .getSQL())) {
                                LOG.info("EXTERNAL CHANGE!"); //$NON-NLS-1$
                            }
                        }
                    }
                    editor.getEditorInput();
                } catch (PartInitException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Participant getDbUser() {
        Participant dbUser = null;
        Object input = getInput();
        if (input instanceof Activity) {
            // Get participant
            EObject[] performers =
                    TaskObjectUtil.getActivityPerformers((Activity) input);
            if (performers.length == 1) {
                if (performers[0] instanceof Participant) {
                    Participant participant = (Participant) performers[0];
                    if (ParticipantType.SYSTEM_LITERAL.equals(participant
                            .getParticipantType().getType())) {
                        dbUser = participant;
                    }
                }
            }
        }
        return dbUser;
    }

    private String getConnectionProfileName(Participant dbUser) {
        String name = null;
        if (dbUser != null) {
            Object other =
                    Xpdl2ModelUtil
                            .getOtherElement(dbUser,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ParticipantSharedResource());
            if (other instanceof ParticipantSharedResource) {
                ParticipantSharedResource shared =
                        (ParticipantSharedResource) other;
                if (shared.getJdbc() != null) {
                    name = shared.getJdbc().getJdbcProfileName();
                }
            }
        }
        return name;
    }

    private IConnectionProfile getConnectionProfile() {
        IConnectionProfile profile = null;
        Participant dbUser = getDbUser();
        String profileName = getConnectionProfileName(dbUser);
        if (profileName != null) {
            profile =
                    ProfileManager.getInstance().getProfileByName(profileName);
        }

        if (dbUser == null) {
            // No system participant assigned.
            String message =
                    Messages.DatabaseServiceSection_CreateParticipantCommand;
            profile = pickConnectionProfile(message);
            EObject input = getInput();
            if (profile != null && input instanceof Activity) {
                Activity act = (Activity) input;
                createDbParticipant(profile, message, act);
            }
        } else {
            if (profile == null) {
                // System participant assigned but not pointing to a valid
                // profile.
                String message =
                        Messages.DatabaseServiceSection_AssignProfileCommand;
                profile = pickConnectionProfile(message);
                if (profile != null) {
                    String connectionProfileName = profile.getName();
                    EditingDomain ed = getEditingDomain();
                    ParticipantSharedResource shared =
                            (ParticipantSharedResource) Xpdl2ModelUtil
                                    .getOtherElement(dbUser,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ParticipantSharedResource());
                    CompoundCommand cmd = new CompoundCommand(message);
                    if (shared == null) {
                        shared =
                                XpdExtensionFactory.eINSTANCE
                                        .createParticipantSharedResource();

                        JdbcResource jdbc =
                                XpdExtensionFactory.eINSTANCE
                                        .createJdbcResource();
                        jdbc.setJdbcProfileName(connectionProfileName);
                        shared.setJdbc(jdbc);

                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherElementCommand(ed,
                                        dbUser,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource(),
                                        shared));
                    } else {
                        JdbcResource jdbc = shared.getJdbc();
                        if (jdbc == null) {
                            jdbc =
                                    XpdExtensionFactory.eINSTANCE
                                            .createJdbcResource();
                            cmd.append(SetCommand
                                    .create(ed,
                                            shared,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getParticipantSharedResource_Jdbc(),
                                            jdbc));
                        }
                        cmd.append(SetCommand.create(ed,
                                jdbc,
                                XpdExtensionPackage.eINSTANCE
                                        .getJdbcResource_JdbcProfileName(),
                                connectionProfileName));
                    }
                    if (cmd.canExecute()) {
                        ed.getCommandStack().execute(cmd);
                    }
                }
            }
        }
        return profile;
    }

    /**
     * 
     * Creates participant only if one doesn't exist with the Jdbc profile name
     * the same as that is selected for this task.
     * 
     * @param profile
     * @param message
     * @param act
     */
    private void createDbParticipant(IConnectionProfile profile,
            String message, Activity act) {
        CompoundCommand cmd = new CompoundCommand(message);
        EditingDomain ed = getEditingDomain();
        Participant participant = findJdbcParticipant(act, profile.getName());
        if (participant == null) {

            participant = Xpdl2Factory.eINSTANCE.createParticipant();
            String name = profile.getName();
            ParticipantTypeElem type =
                    Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
            type.setType(ParticipantType.SYSTEM_LITERAL);
            String participantName = name;
            Package pckg = Xpdl2ModelUtil.getPackage(act);
            int idx = 0;
            while (Xpdl2ModelUtil.getDuplicateDisplayParticipant(pckg,
                    participant,
                    participantName) != null
                    || Xpdl2ModelUtil.getDuplicateParticipant(pckg,
                            participant,
                            NameUtil.getInternalName(participantName, true)) != null) {
                idx++;
                participantName = name + idx;
            }
            participant
                    .setName(NameUtil.getInternalName(participantName, true));
            Xpdl2ModelUtil
                    .setOtherAttribute(participant,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            participantName);
            participant.setParticipantType(type);
            ParticipantSharedResource shared =
                    XpdExtensionFactory.eINSTANCE
                            .createParticipantSharedResource();

            JdbcResource jdbc =
                    XpdExtensionFactory.eINSTANCE.createJdbcResource();
            jdbc.setJdbcProfileName(name);
            shared.setSharedResource(jdbc);

            Xpdl2ModelUtil.setOtherElement(participant,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ParticipantSharedResource(),
                    shared);

            cmd.append(AddCommand.create(ed, pckg, Xpdl2Package.eINSTANCE
                    .getParticipantsContainer_Participants(), participant));
        }
        EObject[] newPerformers = new EObject[] { participant };

        cmd.append(TaskObjectUtil.getSetPerformersCommand(ed,
                act,
                newPerformers));
        if (cmd.canExecute()) {
            ed.getCommandStack().execute(cmd);
        }
    }

    /**
     * 
     * @param act
     * @param name
     * @return {@link Participant} from the {@link Process} or the
     *         {@link Package}
     */
    private Participant findJdbcParticipant(Activity act, String name) {
        // Look in the process first
        Process process = act.getProcess();
        List<Participant> participants = process.getParticipants();
        for (Participant participant : participants) {
            Object participantShResObj =
                    Xpdl2ModelUtil
                            .getOtherElement(participant,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ParticipantSharedResource());

            if (participantShResObj instanceof ParticipantSharedResource) {
                ParticipantSharedResource pSR =
                        (ParticipantSharedResource) participantShResObj;

                EObject sharedResource = pSR.getSharedResource();
                if (sharedResource instanceof JdbcResource) {
                    JdbcResource jdbcRes = (JdbcResource) sharedResource;
                    if (jdbcRes.getJdbcProfileName() != null
                            && jdbcRes.getJdbcProfileName().equals(name)) {
                        return participant;
                    }
                }

            }

        }

        participants = Xpdl2ModelUtil.getPackage(process).getParticipants();
        for (Participant participant : participants) {
            Object participantShResObj =
                    Xpdl2ModelUtil
                            .getOtherElement(participant,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ParticipantSharedResource());

            if (participantShResObj instanceof ParticipantSharedResource) {
                ParticipantSharedResource pSR =
                        (ParticipantSharedResource) participantShResObj;

                EObject sharedResource = pSR.getSharedResource();
                if (sharedResource instanceof JdbcResource) {
                    JdbcResource jdbcRes = (JdbcResource) sharedResource;
                    if (jdbcRes.getJdbcProfileName() != null
                            && jdbcRes.getJdbcProfileName().equals(name)) {
                        return participant;
                    }
                }

            }

        }

        return null;
    }

    private IConnectionProfile pickConnectionProfile(String message) {
        IConnectionProfile profile = null;
        ITreeContentProvider content = new ConnectionProfileContentProvider();
        ILabelProvider label = new ConnectionProfileLabelProvider();
        Object item = null;
        Shell shell = getSite().getShell();
        ElementTreeSelectionDialog dialog =
                new ElementTreeSelectionDialog(shell, label, content);
        dialog.setTitle(Messages.AbstractDatabaseSection_ConnectionProfilePickerDialogTitle);
        dialog.setMessage(message);
        dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
        if (dialog.open() == ElementTreeSelectionDialog.OK) {
            item = dialog.getFirstResult();
        }
        if (item instanceof IConnectionProfile) {
            profile = (IConnectionProfile) item;
        }
        return profile;
    }

    /**
     * Get the <code>TaskService</code> input
     * 
     * @return
     */
    protected TaskService getTaskServiceInput() {
        TaskService service = null;
        EObject input = getInput();

        if (input != null && input instanceof Activity) {
            Activity activity = (Activity) input;

            Task task = (Task) activity.getImplementation();

            if (task != null) {
                service = task.getTaskService();
            }
        }

        return service;
    }

    /**
     * Create a label control using the toolkit and set it to the standard label
     * width
     * 
     * @param toolkit
     * @param parent
     * @param text
     * @return
     */
    protected Label createLabel(XpdFormToolkit toolkit, Composite parent,
            String text) {
        Label lbl = toolkit.createLabel(parent, text, SWT.WRAP);
        lbl.setToolTipText(text);
        GridData gData = new GridData();
        gData.widthHint = STANDARD_LABEL_WIDTH;
        gData.verticalAlignment = SWT.BEGINNING;
        lbl.setLayoutData(gData);

        return lbl;
    }

    /**
     * The given string is a SELECT if it starts with the word SELECT
     * (optionally followed by whitespace).
     * 
     * @param sql
     * @return true if it <i>looks like</i. the given sql is a select statment.
     */
    private boolean isSQLSelectStatement(String sql) {
        if (sql != null) {
            String upper = sql.toUpperCase().trim();

            if (upper.startsWith(SELECT_STATEMENT)) {
                if (upper.length() == SELECT_STATEMENT.length()) {
                    // Only has the word "SELECT" in.
                    return true;

                } else {
                    char next =
                            upper.substring(SELECT_STATEMENT.length())
                                    .charAt(0);
                    if (next == ' ' || next == '\t' || next == '\n'
                            || next == '\r') {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    class DatabaseStorage implements ISaveableStorage {

        @Override
        public InputStream getContents() throws CoreException {
            return new ByteArrayInputStream(sourceViewer.getTextWidget()
                    .getText().getBytes());
        }

        @Override
        public IPath getFullPath() {
            return null;
        }

        @Override
        public String getName() {
            String name = "SQL"; //$NON-NLS-1$
            EObject input = getInput();

            if (input != null && input instanceof Activity) {
                Activity activity = (Activity) input;
                name = activity.getName();
            }
            return name;
        }

        @Override
        public boolean isReadOnly() {
            return false;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object getAdapter(Class adapter) {
            return null;
        }

        @Override
        public void setContents(InputStream input) {
            StringBuilder builder = new StringBuilder();
            byte[] buffer = new byte[128];
            int count = 0;
            try {
                while ((count = input.read(buffer)) != -1) {
                    String sql = new String(buffer, 0, count);
                    builder.append(sql);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String name = builder.toString();

            SqlType sqlType = SqlType.UPDATE_LITERAL;
            if (isSQLSelectStatement(name)) {
                sqlType = SqlType.SELECT_LITERAL;
            }

            Command cmd =
                    getSetSqlCommand(getTaskServiceInput(), name, sqlType);
            if (cmd.canExecute()) {
                getEditingDomain().getCommandStack().execute(cmd);
            }
        }
    }

    class CommonNavigatorPicker extends SelectionStatusDialog {

        private CommonViewer fViewer;

        private ITreeContentProvider fContentProvider;

        private ISelectionStatusValidator fValidator = null;

        private ViewerComparator fComparator;

        private boolean fAllowMultiple = true;

        private boolean fDoubleClickSelects = true;

        private String fEmptyListMessage =
                Messages.AbstractDatabaseSection_ContentsEmptyMessage;

        private IStatus fCurrStatus = new Status(IStatus.OK,
                PlatformUI.PLUGIN_ID, IStatus.OK, "", null); //$NON-NLS-1$

        private List<ViewerFilter> fFilters;

        private Object fInput;

        private int fWidth = 60;

        private int fHeight = 18;

        private String fViewerId =
                "org.eclipse.datatools.connectivity.DataSourceExplorerNavigator"; //$NON-NLS-1$

        public CommonNavigatorPicker(Shell parent) {
            super(parent);

            setResult(new ArrayList<Object>(0));
            setStatusLineAboveButtons(true);
        }

        /**
         * Sets the initial selection. Convenience method.
         * 
         * @param selection
         *            the initial selection.
         */
        public void setInitialSelection(Object selection) {
            setInitialSelections(new Object[] { selection });
        }

        /**
         * Sets the message to be displayed if the list is empty.
         * 
         * @param message
         *            the message to be displayed.
         */
        public void setEmptyListMessage(String message) {
            fEmptyListMessage = message;
        }

        /**
         * Specifies if multiple selection is allowed.
         * 
         * @param allowMultiple
         */
        public void setAllowMultiple(boolean allowMultiple) {
            fAllowMultiple = allowMultiple;
        }

        /**
         * Specifies if default selected events (double click) are created.
         * 
         * @param doubleClickSelects
         */
        public void setDoubleClickSelects(boolean doubleClickSelects) {
            fDoubleClickSelects = doubleClickSelects;
        }

        /**
         * Sets the sorter used by the tree viewer.
         * 
         * @param sorter
         * @deprecated as of 3.3, use
         *             {@link ElementTreeSelectionDialog#setComparator(ViewerComparator)}
         *             instead
         */
        @Deprecated
        public void setSorter(ViewerSorter sorter) {
            fComparator = sorter;
        }

        /**
         * Sets the comparator used by the tree viewer.
         * 
         * @param comparator
         * @since 3.3
         */
        public void setComparator(ViewerComparator comparator) {
            fComparator = comparator;
        }

        /**
         * Adds a filter to the tree viewer.
         * 
         * @param filter
         *            a filter.
         */
        public void addFilter(ViewerFilter filter) {
            if (fFilters == null) {
                fFilters = new ArrayList<ViewerFilter>(4);
            }

            fFilters.add(filter);
        }

        /**
         * Sets an optional validator to check if the selection is valid. The
         * validator is invoked whenever the selection changes.
         * 
         * @param validator
         *            the validator to validate the selection.
         */
        public void setValidator(ISelectionStatusValidator validator) {
            fValidator = validator;
        }

        /**
         * Sets the tree input.
         * 
         * @param input
         *            the tree input.
         */
        public void setInput(Object input) {
            fInput = input;
        }

        /**
         * Sets the size of the tree in unit of characters.
         * 
         * @param width
         *            the width of the tree.
         * @param height
         *            the height of the tree.
         */
        public void setSize(int width, int height) {
            fWidth = width;
            fHeight = height;
        }

        /**
         * Validate the receiver and update the ok status.
         * 
         */
        protected void updateOKStatus() {
            if (!evaluateIfTreeEmpty(fInput)) {
                if (fValidator != null) {
                    fCurrStatus = fValidator.validate(getResult());
                    updateStatus(fCurrStatus);
                } else {
                    fCurrStatus =
                            new Status(IStatus.OK, PlatformUI.PLUGIN_ID,
                                    IStatus.OK, "", //$NON-NLS-1$
                                    null);
                }
            } else {
                fCurrStatus =
                        new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
                                IStatus.ERROR, fEmptyListMessage, null);
            }
            updateStatus(fCurrStatus);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.window.Window#open()
         */
        @Override
        public int open() {
            super.open();
            return getReturnCode();
        }

        private void access$superCreate() {
            super.create();
        }

        /**
         * Handles cancel button pressed event.
         */
        @Override
        protected void cancelPressed() {
            setResult(null);
            super.cancelPressed();
        }

        /*
         * @see SelectionStatusDialog#computeResult()
         */
        @Override
        protected void computeResult() {
            setResult(((IStructuredSelection) fViewer.getSelection()).toList());
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.window.Window#create()
         */
        @Override
        public void create() {
            BusyIndicator.showWhile(null, new Runnable() {
                @Override
                public void run() {
                    access$superCreate();
                    fViewer.setSelection(new StructuredSelection(
                            getInitialElementSelections()), true);
                    updateOKStatus();
                }
            });
        }

        /*
         * @see Dialog#createDialogArea(Composite)
         */
        @Override
        protected Control createDialogArea(Composite parent) {
            Composite composite = (Composite) super.createDialogArea(parent);

            Label messageLabel = createMessageArea(composite);
            TreeViewer treeViewer = createTreeViewer(composite);

            GridData data = new GridData(GridData.FILL_BOTH);
            data.widthHint = convertWidthInCharsToPixels(fWidth);
            data.heightHint = convertHeightInCharsToPixels(fHeight);

            Tree treeWidget = treeViewer.getTree();
            treeWidget.setLayoutData(data);
            treeWidget.setFont(parent.getFont());

            if (evaluateIfTreeEmpty(fInput)) {
                messageLabel.setEnabled(false);
                treeWidget.setEnabled(false);
            }

            return composite;
        }

        /**
         * Creates and initializes the tree viewer.
         * 
         * @param parent
         *            the parent composite
         * @return the tree viewer
         * @see #doCreateTreeViewer(Composite, int)
         */
        protected TreeViewer createTreeViewer(Composite parent) {
            int style = SWT.BORDER | (fAllowMultiple ? SWT.MULTI : SWT.SINGLE);

            fViewer = doCreateTreeViewer(parent, style);
            fContentProvider =
                    (ITreeContentProvider) fViewer.getContentProvider();
            fViewer.addSelectionChangedListener(new ISelectionChangedListener() {
                @Override
                public void selectionChanged(SelectionChangedEvent event) {
                    access$setResult(((IStructuredSelection) event
                            .getSelection()).toList());
                    updateOKStatus();
                }
            });

            fViewer.setComparator(fComparator);
            if (fFilters != null) {
                for (int i = 0; i != fFilters.size(); i++) {
                    fViewer.addFilter(fFilters.get(i));
                }
            }

            if (fDoubleClickSelects) {
                Tree tree = fViewer.getTree();
                tree.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetDefaultSelected(SelectionEvent e) {
                        updateOKStatus();
                        if (fCurrStatus.isOK()) {
                            access$superButtonPressed(IDialogConstants.OK_ID);
                        }
                    }
                });
            }
            fViewer.addDoubleClickListener(new IDoubleClickListener() {
                @Override
                public void doubleClick(DoubleClickEvent event) {
                    updateOKStatus();

                    // If it is not OK or if double click does not
                    // select then expand
                    if (!(fDoubleClickSelects && fCurrStatus.isOK())) {
                        ISelection selection = event.getSelection();
                        if (selection instanceof IStructuredSelection) {
                            Object item =
                                    ((IStructuredSelection) selection)
                                            .getFirstElement();
                            if (fViewer.getExpandedState(item)) {
                                fViewer.collapseToLevel(item, 1);
                            } else {
                                fViewer.expandToLevel(item, 1);
                            }
                        }
                    }
                }
            });

            fViewer.setInput(fInput);

            return fViewer;
        }

        /**
         * Creates the tree viewer.
         * 
         * @param parent
         *            the parent composite
         * @param style
         *            the {@link SWT} style bits
         * @return the tree viewer
         * @since 3.4
         */
        protected CommonViewer doCreateTreeViewer(Composite parent, int style) {
            return new CommonViewer(fViewerId, parent, style);
        }

        /**
         * Returns the tree viewer.
         * 
         * @return the tree viewer
         */
        protected TreeViewer getTreeViewer() {
            return fViewer;
        }

        private boolean evaluateIfTreeEmpty(Object input) {
            Object[] elements = fContentProvider.getElements(input);
            if (elements.length > 0) {
                if (fFilters != null) {
                    for (int i = 0; i < fFilters.size(); i++) {
                        ViewerFilter curr = fFilters.get(i);
                        elements = curr.filter(fViewer, input, elements);
                    }
                }
            }
            return elements.length == 0;
        }

        /**
         * Set the result using the super class implementation of buttonPressed.
         * 
         * @param id
         * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
         */
        protected void access$superButtonPressed(int id) {
            super.buttonPressed(id);
        }

        /**
         * Set the result using the super class implementation of setResult.
         * 
         * @param result
         * @see SelectionStatusDialog#setResult(int, Object)
         */
        protected void access$setResult(List<?> result) {
            super.setResult(result);
        }

        /**
         * @see org.eclipse.jface.window.Window#handleShellCloseEvent()
         */
        @Override
        protected void handleShellCloseEvent() {
            super.handleShellCloseEvent();

            // Handle the closing of the shell by selecting the close icon
            if (getReturnCode() == CANCEL) {
                setResult(null);
            }
        }
    }

    class ActivityDatabaseFilter extends ViewerFilter {

        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            boolean valid = true;
            if (element instanceof IConnectionProfile) {
                IConnectionProfile profile = (IConnectionProfile) element;
                valid = false;
                String profileName = getConnectionProfileName(getDbUser());
                if (profileName != null
                        && profileName.equals(profile.getName())) {
                    valid = true;
                }
            }
            return valid;
        }

    }
}
