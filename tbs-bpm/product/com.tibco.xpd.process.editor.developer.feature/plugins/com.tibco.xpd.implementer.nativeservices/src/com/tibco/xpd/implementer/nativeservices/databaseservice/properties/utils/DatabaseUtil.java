/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.properties.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseFactory;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabasePackage;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.OperationType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParameterType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.ParametersType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.database.SqlType;
import com.tibco.xpd.implementer.nativeservices.internal.Messages;
import com.tibco.xpd.implementer.nativeservices.utilities.TaskServiceExtUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class to create and delete the <code>DatabaseType</code> object from
 * the given <code>TaskService</code> object. Also provides methods to get
 * parameter information from the procedure defined.
 * 
 * @author njpatel
 * 
 */
public final class DatabaseUtil {
    /**
     * Get the <code>DatabaseType</code> extended object from the given
     * <code>TaskService</code> object.
     * 
     * @return The <code>DatabaseType</code> object if set. It will return
     *         <b>null</b> if the database object isn't set or the input is
     *         null.
     */
    public static DatabaseType getDatabaseObj(TaskService taskService) {
        DatabaseType database = null;

        if (taskService != null) {
            // Get the database object from the TaskService object
            EObject eo =
                    TaskServiceExtUtil.getExtendedModel(taskService,
                            DatabasePackage.eINSTANCE
                                    .getDocumentRoot_Database());

            if (eo instanceof DatabaseType) {
                database = (DatabaseType) eo;
            }
        }

        return database;
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
     * @return The new <code>DatabaseType</code> object created, if any of the
     *         input is null then <b>null</b> will be returned.
     */
    public static DatabaseType createDatabaseObj(EditingDomain ed,
            CompoundCommand cmd, TaskService taskService) {
        DatabaseType database = null;

        if (ed != null && cmd != null && taskService != null) {
            database = DatabaseFactory.eINSTANCE.createDatabaseType();
            // Add operation
            OperationType opType =
                    DatabaseFactory.eINSTANCE.createOperationType();

            opType.setType(SqlType.UPDATE_LITERAL);

            database.setOperation(opType);

            // Add the database object to the given TaskService object
            cmd.append(TaskServiceExtUtil.addExtendedModel(ed,
                    taskService,
                    DatabasePackage.eINSTANCE.getDocumentRoot_Database(),
                    database));
        }

        return database;
    }

    /**
     * Get the parameters of the stored procedure in the given database object.
     * This will return a copy of the list.
     * 
     * @param database
     * @return A list of <code>ParameterType</code> for the stored procedure. If
     *         the database object is null or no procedure/parameters are
     *         defined then <b>null</b> will be returned.
     */
    public static List<ParameterType> getParametersFromOperation(
            DatabaseType database) {
        List<ParameterType> params = null;

        if (database != null && database.getOperation() != null
                && database.getOperation().getParameters() != null) {
            params = database.getOperation().getParameters().getParameter();
        }

        return params != null ? new ArrayList<ParameterType>(params) : null;
    }

    /**
     * Get the parameter from the stored procedure with the given formal
     * parameter name.
     * 
     * @param formalName
     *            Name of the parameter to get
     * @return <code>ParameterType</code> object that references the given
     *         formal parameter name. If no parameter is found then <b>null</b>
     *         will be returned.
     */
    public static ParameterType getParameterByName(DatabaseType database,
            String formalName) {
        ParameterType ret = null;
        List<ParameterType> params = getParametersFromOperation(database);

        if (params != null && formalName != null) {
            for (ParameterType param : params) {
                if (param.getName().equals(formalName)) {
                    ret = param;
                    break;
                }
            }
        }

        return ret;
    }

    /**
     * Get the index of the stored procedure parameter with the given name.
     * 
     * @param database
     * @param formalName
     * @return
     */
    public static int getParameterIndex(DatabaseType database, String formalName) {
        List<ParameterType> params = getParametersFromOperation(database);
        int idx = -1;

        if (params != null) {
            int count = 0;
            for (ParameterType param : params) {
                if (param.getName().equals(formalName)) {
                    idx = count;
                    break;
                }

                ++count;
            }
        }

        return idx;
    }

    /**
     * Get the count of parameters defined for the stored procedure
     * 
     * @param database
     * @return
     */
    public static int getParameterCount(DatabaseType database) {
        List<ParameterType> params = getParametersFromOperation(database);

        return params != null ? params.size() : 0;
    }

    /**
     * Get <code>CompoundCommand</code> to remove the given mappings from the
     * database. This will remove the mappings from the MessageIn/MessageOut
     * sections of the <code>TaskService</code> object and also the
     * corresponding mappings from the operation section of the
     * <code>DatabaseType</code> object.
     * 
     * @param ed
     *            Editing domain
     * @param taskService
     *            <code>TaskService</code> object containing the data mappings
     * @param mappingsToRemove
     *            List of <code>DataMapping</code> objects to remove
     * @return <code>CompoundCommand</code> containing the command to remove the
     *         mappings.
     */
    public static CompoundCommand getDataMappingsRemoveCmd(EditingDomain ed,
            TaskService taskService, List<DataMapping> mappingsToRemove) {
        CompoundCommand cmd = null;

        if (ed != null && taskService != null && mappingsToRemove != null
                && !mappingsToRemove.isEmpty()) {
            cmd = new CompoundCommand();
            cmd.setLabel(Messages.DatabaseUtil_RemoveParamCmdLabel);

            // Identify where the selected DataMapping is contained - in
            // MessageIn or MessageOut
            List<DataMapping> messageInList = new ArrayList<DataMapping>();
            List<DataMapping> messageOutList = new ArrayList<DataMapping>();

            for (DataMapping mapping : mappingsToRemove) {
                if (taskService.getMessageIn().getDataMappings()
                        .contains(mapping)) {
                    messageInList.add(mapping);

                } else if (taskService.getMessageOut().getDataMappings()
                        .contains(mapping)) {
                    messageOutList.add(mapping);
                }
            }

            /*
             * Combine the commands to delete so we can execute in single
             * command
             */

            // Get command to remove the parameter references from the database
            // stored procedure
            addRemoveParamsFromOperationCmd(ed,
                    getDatabaseObj(taskService),
                    cmd,
                    messageInList,
                    messageOutList);

            // Delete the data mappings that appear in the MessageIn section
            if (!messageInList.isEmpty()) {
                cmd.append(RemoveCommand.create(ed,
                        taskService.getMessageIn(),
                        Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                        messageInList));
            }

            // Delete the data mappings that appear in the MessageOut section
            if (!messageOutList.isEmpty()) {
                cmd.append(RemoveCommand.create(ed,
                        taskService.getMessageOut(),
                        Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                        messageOutList));
            }
        }

        return cmd;
    }

    public static DataMapping createDefaultParameterCmd(EditingDomain ed,
            TaskService taskService, CompoundCommand cmd, String paramName) {

        DataMapping mapping = null;

        if (ed != null && taskService != null && cmd != null
                && paramName != null) {
            mapping = Xpdl2Factory.eINSTANCE.createDataMapping();

            mapping.setFormal(paramName);
            mapping.setDirection(DirectionType.IN_LITERAL);
            mapping.setActual(createExpression(null));

            // Add the new item
            cmd.append(AddCommand.create(ed,
                    taskService.getMessageIn(),
                    Xpdl2Package.eINSTANCE.getMessage_DataMappings(),
                    mapping));

            // If database operation defined then add the parameter to it
            DatabaseType database = getDatabaseObj(taskService);
            if (database != null && database.getOperation() != null) {
                ParametersType parameters =
                        database.getOperation().getParameters();

                // Create the parameter
                ParameterType param =
                        DatabaseFactory.eINSTANCE.createParameterType();
                param.setName(paramName);

                /*
                 * if the parameter group hasn't been created then create it and
                 * add the mapping to it, otherwise add the mapping to the
                 * existing group
                 */
                if (parameters != null) {
                    cmd.append(AddCommand.create(ed,
                            parameters,
                            DatabasePackage.eINSTANCE
                                    .getParametersType_Parameter(),
                            param));
                } else {
                    // Create group
                    parameters =
                            DatabaseFactory.eINSTANCE.createParametersType();
                    parameters.getParameter().add(param);

                    cmd.append(SetCommand.create(ed,
                            database.getOperation(),
                            DatabasePackage.eINSTANCE
                                    .getOperationType_Parameters(),
                            parameters));
                }

            }
        }
        return mapping;
    }

    /**
     * Add command to remove parameter references in the database operation that
     * reference the formal parameters about to be deleted.
     * 
     * @param ed
     *            The editing domain.
     * @param cmd
     *            The <code>CompoundCommand</code> to append the remove command
     *            to.
     * @param messageInList
     *            Formal parameters being removed in the MessageIn section.
     * @param messageOutList
     *            Formal parameters being removed in the MessageOut section.
     * @return
     */
    private static void addRemoveParamsFromOperationCmd(EditingDomain ed,
            DatabaseType database, CompoundCommand cmd,
            List<DataMapping> messageInList, List<DataMapping> messageOutList) {

        // Make sure parameters are defined for an operation before proceeding
        if (ed != null && database != null && database.getOperation() != null
                && database.getOperation().getParameters() != null) {
            // Create a list of ParameterType to remove from the stored
            // procedure
            List<ParameterType> paramsToRemove = new ArrayList<ParameterType>();

            if (messageInList != null) {
                for (DataMapping mapping : messageInList) {
                    ParameterType param =
                            DatabaseUtil.getParameterByName(database, mapping
                                    .getFormal());

                    if (param != null) {
                        paramsToRemove.add(param);
                    }
                }
            }

            if (messageOutList != null) {
                for (DataMapping mapping : messageOutList) {
                    ParameterType param =
                            DatabaseUtil.getParameterByName(database, mapping
                                    .getFormal());

                    if (param != null) {
                        paramsToRemove.add(param);
                    }
                }
            }

            // If there are parameters to remove from the stored procedure then
            // create command to do so
            cmd.append(RemoveCommand.create(ed, database.getOperation()
                    .getParameters(), DatabasePackage.eINSTANCE
                    .getParametersType_Parameter(), paramsToRemove));
        }
    }

    /**
     * Create an <code>Expression</code> object contain the content given.
     * 
     * @param content
     *            The content to use in the expression.
     * @return The new Expression.
     */
    public static Expression createExpression(String content) {
        Expression expression = Xpdl2Factory.eINSTANCE.createExpression();

        if (content == null)
            content = ""; //$NON-NLS-1$

        // if (content != null && !content.equals("")) {
        expression.getMixed().add(XMLTypePackage.eINSTANCE
                .getXMLTypeDocumentRoot_Text(),
                content);
        // }
        return expression;
    }

    /**
     * Gets the internalised text for this Direction Type
     * 
     * @param directionType
     * @return
     */
    public static String getInternalisedParamType(DirectionType directionType) {
        switch (directionType.getValue()) {
        case DirectionType.IN:
            return Messages.DatabaseTabSection_ParamType_IN;
        case DirectionType.OUT:
            return Messages.DatabaseTabSection_ParamType_OUT;
        case DirectionType.INOUT:
            return Messages.DatabaseTabSection_ParamType_INOUT;
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Get the connection profile that the activity references. This is done
     * through indirect reference. The activity refers to a participant which
     * has JDBC configured as its shared resource configuration.
     * 
     * The JDBC shared reou
     * 
     * @param activity
     * @return
     */
    public static IConnectionProfile getConnectionProfile(Activity activity) {
        IConnectionProfile profile = null;
        if (activity != null) {
            // Get participant
            EObject[] performers =
                    TaskObjectUtil.getActivityPerformers(activity);
            if (performers.length == 1) {
                if (performers[0] instanceof Participant) {
                    Participant participant = (Participant) performers[0];
                    if (ParticipantType.SYSTEM_LITERAL.equals(participant
                            .getParticipantType().getType())) {
                        Object other =
                                Xpdl2ModelUtil
                                        .getOtherElement(participant,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ParticipantSharedResource());
                        if (other instanceof ParticipantSharedResource) {
                            ParticipantSharedResource shared =
                                    (ParticipantSharedResource) other;
                            if (shared.getJdbc() != null) {
                                String name =
                                        shared.getJdbc().getJdbcProfileName();
                                profile =
                                        ProfileManager.getInstance()
                                                .getProfileByName(name);
                            }
                        }
                    }
                }
            }
        }
        return profile;
    }

    public static IStatus connect(Shell shell, IConnectionProfile profile) {
        IStatus status = Status.CANCEL_STATUS;
        if (profile != null) {
            if (profile.getConnectionState() == IConnectionProfile.DISCONNECTED_STATE) {
                ConnectRunnable runnable = new ConnectRunnable(profile);
                ProgressMonitorDialog progress =
                        new ProgressMonitorDialog(shell);
                try {
                    progress.run(false, false, runnable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                status = runnable.getStatus();
            } else {
                status = Status.OK_STATUS;
            }
        }
        return status;
    }

    static class ConnectRunnable implements IRunnableWithProgress {

        private IConnectionProfile profile;

        private IStatus status;

        public ConnectRunnable(IConnectionProfile profile) {
            this.profile = profile;
            status = Status.CANCEL_STATUS;
        }

        public void run(IProgressMonitor monitor)
                throws InvocationTargetException, InterruptedException {
            if (profile != null) {
                String message =
                        String
                                .format(Messages.AbstractDatabaseSection_ConnectMessage,
                                        profile.getName());
                monitor.beginTask(message, 1);
                status = profile.connect();
                monitor.done();
            }
        }

        public IStatus getStatus() {
            return status;
        }
    }

    public static SqlType getSqlType(Activity activity) {
        SqlType sqlType = null;
        Implementation impl = activity.getImplementation();
        if (impl instanceof Task) {
            Task task = (Task) impl;
            TaskService service = task.getTaskService();
            if (service != null) {
                DatabaseType database = getDatabaseObj(service);
                if (database != null) {
                    OperationType operation = database.getOperation();
                    if (operation != null) {
                        sqlType = operation.getType();
                    }
                }
            }
        }
        return sqlType;
    }

    public static String getSql(Activity activity) {
        String sql = null;
        Implementation impl = activity.getImplementation();
        if (impl instanceof Task) {
            Task task = (Task) impl;
            TaskService service = task.getTaskService();
            if (service != null) {
                DatabaseType database = getDatabaseObj(service);
                if (database != null) {
                    OperationType operation = database.getOperation();
                    if (operation != null) {
                        sql = operation.getSql();
                    }
                }
            }
        }
        return sql;
    }

}
