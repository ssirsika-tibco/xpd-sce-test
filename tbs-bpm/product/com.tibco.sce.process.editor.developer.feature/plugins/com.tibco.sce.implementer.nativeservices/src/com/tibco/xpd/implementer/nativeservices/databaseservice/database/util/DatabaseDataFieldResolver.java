package com.tibco.xpd.implementer.nativeservices.databaseservice.database.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.nativeservices.databaseservice.database.DatabaseType;
import com.tibco.xpd.implementer.nativeservices.databaseservice.properties.utils.DatabaseUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;

/**
 * 
 * <p>
 * Resolve data fields for database tasks. <i>Created: 18 Apr 2007</i>
 * 
 * @author scrossle
 * 
 */
public class DatabaseDataFieldResolver implements
        IFieldContextResolverExtension {

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension#getDataReferences(com.tibco.xpd.xpdl2.Activity,
     *      java.util.Set)
     * 
     * @param activity
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessDataReferenceAndContexts> getDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {

        Set<ProcessRelevantData> dataReferences =
                getActivityDataReferences(activity, dataSet);

        if (dataReferences != null) {
            Set<ProcessDataReferenceAndContexts> dataRefAndContexts =
                    new HashSet<ProcessDataReferenceAndContexts>();

            for (ProcessRelevantData data : dataReferences) {
                dataRefAndContexts.add(new ProcessDataReferenceAndContexts(
                        data,
                        DataReferenceContext.CONTEXT_ACTIVITY_IMPLEMENTATION));
            }

            return dataRefAndContexts;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#
     * getActivityDataReferences(com.tibco.xpd.xpdl2.Activity, java.util.Set)
     */
    @Override
    public Set<ProcessRelevantData> getActivityDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {
        TaskService dbTaskService = getDbTaskService(activity);
        if (dbTaskService != null) {
            return getDbTaskDataReferences(dbTaskService, dataSet);
        } else {
            return null;
        }
    }

    /**
     * If given activity is a Db Task Service then return it, null otherwise.
     * 
     * @param activity
     * @return
     */
    private TaskService getDbTaskService(Activity activity) {
        TaskService dbTaskService = null;
        if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();
            if (task.getTaskService() != null) {
                TaskService taskService = task.getTaskService();
                DatabaseType dbType = DatabaseUtil.getDatabaseObj(taskService);
                if (dbType != null) {
                    dbTaskService = taskService;
                }
            }
        }
        return dbTaskService;
    }

    /**
     * Return references from the dataset within the db task service.
     * 
     * @param taskService
     * @param dataSet
     * @return
     */
    private Set<ProcessRelevantData> getDbTaskDataReferences(
            TaskService taskService, Set<ProcessRelevantData> dataSet) {
        Set<ProcessRelevantData> result = new HashSet<ProcessRelevantData>();
        for (ProcessRelevantData procData : dataSet) {
            String name = procData.getName();
            if (messageUsesData(taskService.getMessageIn(), name)
                    || messageUsesData(taskService.getMessageOut(), name)) {
                result.add(procData);
            }
        }
        return result;
    }

    /**
     * Check if the given message uses the given data name. Database mappings
     * only use simple expressions with the name.
     * 
     * @param message
     * @param dataName
     * @return Whether the message uses the data.
     */
    private boolean messageUsesData(Message message, String dataName) {
        if (message != null) {
            for (Object mappingObj : message.getDataMappings()) {
                DataMapping mapping = (DataMapping) mappingObj;
                if (mapping.getActual() != null
                        && mapping.getActual().getText() != null
                        && mapping.getActual().getText().equals(dataName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<ProcessRelevantData> getTransitionDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {
        // Database does not appear in transitions
        return null;
    }

    @Override
    public Command getSwapActivityDataIdReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> idMap) {
        // Database references data by name, not id.
        return null;
    }

    @Override
    public Command getSwapActivityDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {
        TaskService dbTaskService = getDbTaskService(activity);
        if (dbTaskService != null) {
            return getDbTaskDataNameReferencesCommand(editingDomain,
                    dbTaskService,
                    nameMap);
        } else {
            return null;
        }
    }

    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {
        TaskService dbTaskService = getDbTaskService(activity);
        if (dbTaskService != null) {
            return getDbTaskDeleteReferencesCommand(editingDomain,
                    dbTaskService,
                    data);
        } else {
            return null;
        }
    }

    /**
     * Get the command to delete parameters that reference the given data field
     * / formal parameter.
     * 
     * @param editingDomain
     * @param dbTaskService
     * @param data
     * @return
     */
    private Command getDbTaskDeleteReferencesCommand(
            EditingDomain editingDomain, TaskService dbTaskService,
            ProcessRelevantData data) {

        CompoundCommand ccmd = new CompoundCommand();

        appendDeleteFieldCommand(editingDomain,
                dbTaskService,
                dbTaskService.getMessageIn(),
                data,
                ccmd);
        appendDeleteFieldCommand(editingDomain,
                dbTaskService,
                dbTaskService.getMessageOut(),
                data,
                ccmd);

        return ccmd.getCommandList().size() > 0 ? ccmd : null;
    }

    private void appendDeleteFieldCommand(EditingDomain editingDomain,
            TaskService dbTaskService, Message message,
            ProcessRelevantData data, CompoundCommand ccmd) {
        String dataName = data.getName();

        if (message != null && dataName != null) {
            List<DataMapping> toDelete = new ArrayList<DataMapping>();

            for (Object mappingObj : message.getDataMappings()) {
                DataMapping mapping = (DataMapping) mappingObj;
                if (mapping.getActual() != null
                        && dataName.equals(mapping.getActual().getText())) {
                    toDelete.add(mapping);
                }
            }

            if (toDelete.size() > 0) {
                Command delCmd =
                        DatabaseUtil.getDataMappingsRemoveCmd(editingDomain,
                                dbTaskService,
                                toDelete);
                ccmd.append(delCmd);
            }
        }
    }

    /**
     * Get the commands for the database tasks that this extension handles.
     * 
     * @param editingDomain
     * @param dbTaskService
     * @param nameMap
     * @return
     */
    private Command getDbTaskDataNameReferencesCommand(
            EditingDomain editingDomain, TaskService dbTaskService,
            Map<String, String> nameMap) {
        CompoundCommand ccmd = new CompoundCommand();
        for (Map.Entry<String, String> mapEntry : nameMap.entrySet()) {
            appendSwapNameCommand(editingDomain,
                    dbTaskService.getMessageIn(),
                    mapEntry.getKey(),
                    mapEntry.getValue(),
                    ccmd);
            appendSwapNameCommand(editingDomain,
                    dbTaskService.getMessageOut(),
                    mapEntry.getKey(),
                    mapEntry.getValue(),
                    ccmd);
        }

        return ccmd.getCommandList().size() > 0 ? ccmd : null;
    }

    /**
     * Append commands to the compound command to rename the actual parameter in
     * the mappings in the given message. Db mappings use the field name as the
     * actual value, so this is easy!
     * 
     * @param editingDomain
     * @param message
     * @param oldName
     * @param newName
     * @param ccmd
     */
    private void appendSwapNameCommand(EditingDomain editingDomain,
            Message message, String oldName, String newName,
            CompoundCommand ccmd) {
        if (message != null) {
            for (Object mappingObj : message.getDataMappings()) {
                DataMapping mapping = (DataMapping) mappingObj;
                if (mapping.getActual() != null
                        && mapping.getActual().getText() != null
                        && mapping.getActual().getText().equals(oldName)) {
                    Command cmd =
                            SetCommand.create(editingDomain,
                                    mapping,
                                    Xpdl2Package.eINSTANCE
                                            .getDataMapping_Actual(),
                                    DatabaseUtil.createExpression(newName));
                    ccmd.append(cmd);
                }
            }
        }
    }

    @Override
    public Command getSwapTransitionDataIdReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> idMap) {
        // Database does not appear in transitions
        return null;
    }

    @Override
    public Command getSwapTransitionDataNameReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> nameMap) {
        // Database does not appear in transitions
        return null;
    }

    @Override
    public Command getDeleteDataFromTransitionCommand(
            EditingDomain editingDomain, Transition transition,
            ProcessRelevantData data) {
        // Database does not appear in transitions
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension#getDataReferences(com.tibco.xpd.xpdl2.Transition,
     *      java.util.Set)
     * 
     * @param transition
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessDataReferenceAndContexts> getDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {
        // Database does not appear in transitions
        return null;
    }

}
