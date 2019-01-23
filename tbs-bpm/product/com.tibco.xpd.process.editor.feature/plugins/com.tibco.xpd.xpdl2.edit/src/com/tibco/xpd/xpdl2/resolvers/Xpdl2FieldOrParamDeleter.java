/**
 * Xpdl2DataFieldReplacer.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.xpdl2.resolvers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.util.LocalPackageProcessInterfaceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * <b>Xpdl2FieldOrParamReplacer</b>
 * <p>
 * The data field deleter provides the ability to check for data field
 * references within given model objects and delete appropriate model items.
 * </p>
 * <p>
 * This allows plug-able contribution to the delete data field/formal param
 * behaviour. For instance a plug-in can contribute to the
 * "com.tibco.xpd.xpdl2.edit.fieldOrParamReferenceResolver" extrension point and
 * delete references to a field that is about to be deleted.
 * </p>
 * <p>
 * This frame work deletes references to fields/params in the standard
 * non-plugable poarts of the model (performer data field task participant
 * reference, sub-proc task parameter mapping, user task parmeters). Then it
 * asks any extenders to contribute (if they wish) for each activity.
 * 
 */
public class Xpdl2FieldOrParamDeleter {

    /**
     * Create a deleter replacer for the given map of data fields
     * 
     */
    public Xpdl2FieldOrParamDeleter() {

    }

    /**
     * One stop shop for delete data references command provision. Will remove
     * all references to the given data from all objects within scope of that
     * data.
     * <p>
     * This combines all of the old methods to do this from individual
     * activities / flows etc.
     * 
     * @param editingDomain
     * @param data
     * @return Command to delete references to the given data field (that are in
     *         scope of that data or <code>null</code> if nothing to do.
     */
    public Command getDeleteDataReferencesCommand(EditingDomain editingDomain,
            ProcessRelevantData data) {
        LateExecuteCompoundCommand cmd = new LateExecuteCompoundCommand();

        if (data.eContainer() instanceof Package) {
            /*
             * Do every process in pkg, note that we DELIBERATELY do not handle
             * data resovler contributions for Package data because we recurs
             * for each process and call resolvers for each one (becuase we do
             * not expect refereces to data at package level).
             */
            for (Process process : ((Package) data.eContainer()).getProcesses()) {
                appendDeleteRefsFromProcess(editingDomain, cmd, process, data);
            }

        } else if (data.eContainer() instanceof ProcessInterface) {
            appendDeleteRefsFromProcessInterface(editingDomain,
                    cmd,
                    (ProcessInterface) data.eContainer(),
                    data);

        } else if (data.eContainer() instanceof Process) {
            appendDeleteRefsFromProcess(editingDomain,
                    cmd,
                    (Process) data.eContainer(),
                    data);

        } else if (data.eContainer() instanceof Activity) {
            appendDeleteRefsFromActivity(editingDomain,
                    cmd,
                    (Activity) data.eContainer(),
                    data);
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Append commands to delete refs to data in the given package
     * <p>
     * The difference between this and
     * {@link #getDeleteFieldReferencesCommand(EditingDomain, Activity, ProcessRelevantData)}
     * is that this method deals with Activity as the container of the data
     * field so that it deals with references in the activity and if it is
     * embedded sub-=process then deals with content.
     * 
     * @param editingDomain
     * @param cmd
     * @param eContainer
     * @param data
     */
    private void appendDeleteRefsFromActivity(EditingDomain editingDomain,
            LateExecuteCompoundCommand cmd, Activity activity,
            ProcessRelevantData data) {

        Command c =
                getDeleteFieldReferencesCommand(editingDomain, activity, data);

        if (c != null) {
            cmd.append(c);
        }

        /*
         * If this is embedded sub-process recurs to all nested activities /
         * flows
         */
        if (activity.getBlockActivity() != null) {
            for (Activity subAct : Xpdl2ModelUtil
                    .getAllActivitiesInEmbeddedSubProc(activity)) {

                Command delCmd =
                        getDeleteFieldReferencesCommand(editingDomain,
                                subAct,
                                data);
                if (delCmd != null) {
                    cmd.append(delCmd);
                }
            }

            for (Transition transition : Xpdl2ModelUtil
                    .getAllTransitionsInEmbeddedSubProc(activity)) {

                Command delCmd =
                        getDeleteFieldReferencesCommand(editingDomain,
                                transition,
                                data);
                if (delCmd != null) {
                    cmd.append(delCmd);
                }
            }
        }

        /*
         * Get the contributed data resolvers that dela with things outside of
         * activity.
         */
        appendContributedDataResolverCommands(editingDomain,
                cmd,
                activity,
                data);

    }

    /**
     * Append commands to delete refs to data in the given process
     * 
     * @param editingDomain
     * @param cmd
     * @param process
     * @param data
     */
    private void appendDeleteRefsFromProcess(EditingDomain editingDomain,
            CompoundCommand cmd, Process process, ProcessRelevantData data) {
        Collection<Activity> acts =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Iterator iter = acts.iterator(); iter.hasNext();) {
            Activity act = (Activity) iter.next();

            Command delCmd =
                    getDeleteFieldReferencesCommand(editingDomain, act, data);
            if (delCmd != null) {
                cmd.append(delCmd);
            }
        }

        Collection<Transition> trans =
                Xpdl2ModelUtil.getAllTransitionsInProc(process);

        for (Iterator iter = trans.iterator(); iter.hasNext();) {
            Transition tran = (Transition) iter.next();

            Command delCmd =
                    getDeleteFieldReferencesCommand(editingDomain, tran, data);
            if (delCmd != null) {
                cmd.append(delCmd);
            }
        }

        /*
         * Get the contributed data resolvers that dela with things outside of
         * activity.
         */
        appendContributedDataResolverCommands(editingDomain, cmd, process, data);

        return;
    }

    /**
     * Append commands to deleted data references from whole process interface
     * and processes that are implement it in the local package.
     * 
     * @param editingDomain
     * @param cmd
     * @param processInterface
     * @param data
     */
    private void appendDeleteRefsFromProcessInterface(
            EditingDomain editingDomain, CompoundCommand cmd,
            ProcessInterface processInterface, ProcessRelevantData data) {
        /*
         * Replace references from the process interface methods.
         */
        for (StartMethod method : processInterface.getStartMethods()) {
            Command c =
                    getDeleteFieldReferencesCommand(editingDomain, method, data);
            if (c != null) {
                cmd.append(c);
            }

            for (ErrorMethod errorMethod : method.getErrorMethods()) {
                Command c2 =
                        getDeleteFieldReferencesCommand(editingDomain,
                                errorMethod,
                                data);
                if (c2 != null) {
                    cmd.append(c2);
                }
            }
        }

        for (IntermediateMethod method : processInterface
                .getIntermediateMethods()) {
            Command c =
                    getDeleteFieldReferencesCommand(editingDomain, method, data);
            if (c != null) {
                cmd.append(c);
            }

            for (ErrorMethod errorMethod : method.getErrorMethods()) {
                Command c2 =
                        getDeleteFieldReferencesCommand(editingDomain,
                                errorMethod,
                                data);
                if (c2 != null) {
                    cmd.append(c2);
                }
            }
        }

        /*
         * Replace references from the locally implementing processes.
         */
        List<Process> implementingProcesses =
                LocalPackageProcessInterfaceUtil
                        .getImplementingProcesses(processInterface);
        for (Process implementingProcess : implementingProcesses) {
            appendDeleteRefsFromProcess(editingDomain,
                    cmd,
                    implementingProcess,
                    data);
        }

        /*
         * Get the contributed data resolvers that dela with things outside of
         * activity.
         */
        appendContributedDataResolverCommands(editingDomain,
                cmd,
                processInterface,
                data);
    }

    /**
     * Return EMF command to delete any references to data fields in the given
     * activity
     * 
     * @param editingDomain
     * @param cmd
     *            Add EMF modification commands to this command.
     * @param activity
     * @param field
     *            Data field or Formal Parameter being deleted.
     * 
     * @return EMF command to replace references or null if nothing to do.
     * @deprecated This method only for internal use. Use new
     *             {@link #getDeleteDataReferencesCommand(EditingDomain, ProcessRelevantData)}
     *             instead.
     */
    @Deprecated
    public Command getDeleteFieldReferencesCommand(EditingDomain editingDomain,
            Activity activity, ProcessRelevantData field) {
        LateExecuteCompoundCommand resultCmd = null;

        LateExecuteCompoundCommand replaceCmd =
                new LateExecuteCompoundCommand();

        if (deleteStandardActivityReferences(activity,
                field,
                editingDomain,
                replaceCmd)) {
            resultCmd = new LateExecuteCompoundCommand();
            resultCmd.append(replaceCmd);
        }

        // Pass the activity to all our data field resolver extenders.
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {
            Command extCmd = null;

            extCmd =
                    resolver.getDeleteDataFromActivityCommand(editingDomain,
                            activity,
                            field);

            if (extCmd != null) {
                if (resultCmd == null) {
                    resultCmd = new LateExecuteCompoundCommand();
                }
                resultCmd.append(extCmd);
            }
        }

        return resultCmd;
    }

    /**
     * Return EMF command to delete any references to data fields in the given
     * transition
     * 
     * @param editingDomain
     * @param cmd
     *            Add EMF modification commands to this command.
     * @param transition
     * @param field
     *            Data field or Formal Parameter being deleted.
     * 
     * @return EMF command to replace references or null if nothing to do.
     * @deprecated This method only for internal use. Use new
     *             {@link #getDeleteDataReferencesCommand(EditingDomain, ProcessRelevantData)}
     *             instead.
     */
    @Deprecated
    public Command getDeleteFieldReferencesCommand(EditingDomain editingDomain,
            Transition transition, ProcessRelevantData field) {
        LateExecuteCompoundCommand resultCmd = null;

        // Pass the transition to all our data field resolver extenders.
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {
            Command extCmd = null;

            extCmd =
                    resolver.getDeleteDataFromTransitionCommand(editingDomain,
                            transition,
                            field);

            if (extCmd != null) {
                if (resultCmd == null) {
                    resultCmd = new LateExecuteCompoundCommand();
                }
                resultCmd.append(extCmd);
            }
        }

        return resultCmd;
    }

    /**
     * Return EMF command to delete any references to data fields in the given
     * process interface start / intermediate method.
     * 
     * @param editingDomain
     * @param cmd
     *            Add EMF modification commands to this command.
     * @param assocParamsContainer
     * @param field
     *            Data field or Formal Parameter being deleted.
     * 
     * @return EMF command to replace references or null if nothing to do.
     * @deprecated This method only for internal use. Use new
     *             {@link #getDeleteDataReferencesCommand(EditingDomain, ProcessRelevantData)}
     *             instead.
     */
    @Deprecated
    public Command getDeleteFieldReferencesCommand(EditingDomain editingDomain,
            AssociatedParametersContainer assocParamsContainer,
            ProcessRelevantData field) {

        String fieldName = field.getName();
        if (fieldName == null || fieldName.length() == 0) {
            return null;
        }

        LateExecuteCompoundCommand resultCmd = null;

        EList assocParams = assocParamsContainer.getAssociatedParameters();

        for (Iterator iterator = assocParams.iterator(); iterator.hasNext();) {
            AssociatedParameter param = (AssociatedParameter) iterator.next();

            if (fieldName.equals(param.getFormalParam())) {
                if (resultCmd == null) {
                    resultCmd = new LateExecuteCompoundCommand();
                }

                resultCmd.append(RemoveCommand.create(editingDomain, param));
            }
        }
        return resultCmd;
    }

    /**
     * Do a data field / formal param reference deletion on the given activity.
     * <p>
     * If the replaceCmd is supplied (not null) then commands are added to it.
     * Otherwise the changes are made directly to the model.
     * <p>
     * Here we can deal with user tasks and independent sub-process call tasks
     * because we know that their parameter mapping is by field/param name.
     * <br/>
     * 
     * @param activity
     * @param field
     * @param editingDomain
     * @param replaceCmd
     * 
     * @return true if any replacements were made.
     */
    private boolean deleteStandardActivityReferences(Activity activity,
            ProcessRelevantData field, EditingDomain editingDomain,
            CompoundCommand replaceCmd) {
        boolean deletionsMade = false;

        Implementation impl = activity.getImplementation();

        String fieldName = field.getName();
        if (fieldName == null || fieldName.length() == 0) {
            return false;
        }

        // Delete participant references to data field
        EList perfList = activity.getPerformerList();
        if (perfList != null) {
            String id = field.getId();

            for (Iterator iter = perfList.iterator(); iter.hasNext();) {
                Performer perf = (Performer) iter.next();

                if (id.equals(perf.getValue())) {
                    replaceCmd
                            .append(RemoveCommand.create(editingDomain, perf));
                    deletionsMade = true;
                }
            }
        }

        if (impl instanceof SubFlow) {
            // Replace mappings from the given field.
            SubFlow subFlow = (SubFlow) impl;

            EList dataMappings = subFlow.getDataMappings();

            if (deleteFieldByNameFromDataMappingActual(editingDomain,
                    dataMappings,
                    fieldName,
                    replaceCmd)) {
                deletionsMade = true;
            }

            // Replace process Identifier field name
            Object obj =
                    Xpdl2ModelUtil.getOtherAttribute(subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ProcessIdentifierField());
            if (obj instanceof String) {
                String oldFieldName = (String) obj;
                if (fieldName.equals(oldFieldName)) {
                    // This param is mapped from deleted field - delete it.
                    replaceCmd
                            .append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(editingDomain,
                                            subFlow,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ProcessIdentifierField(),
                                            null));
                    deletionsMade = true;
                }
            }

        } else if (impl instanceof Task && ((Task) impl).getTaskUser() != null) {
            // User task actual parameters are field / param references.
            TaskUser taskUser = ((Task) impl).getTaskUser();

            Message messageIn = taskUser.getMessageIn();
            if (messageIn != null) {
                EList params = messageIn.getActualParameters();

                int index = 0;
                for (Iterator iter = params.iterator(); iter.hasNext(); index++) {
                    Expression param = (Expression) iter.next();

                    if (fieldName.equals(param.getText())) {
                        // This param is mapped from deleted field - delete it.
                        replaceCmd.append(RemoveCommand.create(editingDomain,
                                param));

                        deletionsMade = true;

                    }
                }

            } // End of message in params

            Message messageOut = taskUser.getMessageOut();
            if (messageOut != null) {
                EList params = messageOut.getActualParameters();

                int index = 0;
                for (Iterator iter = params.iterator(); iter.hasNext(); index++) {
                    Expression param = (Expression) iter.next();

                    if (fieldName.equals(param.getText())) {
                        // This param is mapped from deleted field - delete it.
                        replaceCmd.append(RemoveCommand.create(editingDomain,
                                param));

                        deletionsMade = true;

                    }
                }
            } // End of message out params.

        } else if (activity.getEvent() instanceof IntermediateEvent) {
            if (activity.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
                //
                // Catch error events may have mappings to data from error
                // params.
                //
                ResultError resError =
                        (ResultError) activity.getEvent()
                                .getEventTriggerTypeNode();

                CatchErrorMappings catchErrorMappings =
                        (CatchErrorMappings) Xpdl2ModelUtil
                                .getOtherElement(resError,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_CatchErrorMappings());
                if (catchErrorMappings != null
                        && catchErrorMappings.getMessage() != null) {

                    EList<DataMapping> dataMappings =
                            catchErrorMappings.getMessage().getDataMappings();

                    if (deleteFieldByNameFromDataMappingActual(editingDomain,
                            dataMappings,
                            fieldName,
                            replaceCmd)) {
                        deletionsMade = true;
                    }
                }
            } else if (activity.getEvent().getEventTriggerTypeNode() instanceof TriggerTimer) {
                TriggerTimer timer =
                        (TriggerTimer) activity.getEvent()
                                .getEventTriggerTypeNode();

                /*
                 * Check for runtime identified field in Calendar References -
                 * if it matches then remove the calendar reference
                 */
                CalendarReference calendarReference =
                        getCalendarReference(timer);
                if (calendarReference != null) {
                    String dataFieldId = calendarReference.getDataFieldId();
                    if (dataFieldId != null
                            && dataFieldId.equals(field.getId())) {
                        replaceCmd
                                .append(Xpdl2ModelUtil
                                        .getRemoveOtherElementCommand(editingDomain,
                                                timer,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_CalendarReference(),
                                                calendarReference));
                        deletionsMade = true;
                    }
                }
            }
        }

        //
        // Delete references to formal parameters from activity associated
        // parameters (start / intermediate event and receive task.
        //
        EList associatedParams =
                LocalPackageProcessInterfaceUtil
                        .getActivityAssociatedParameters(activity);
        if (associatedParams != null) {
            for (Iterator iter = associatedParams.iterator(); iter.hasNext();) {
                AssociatedParameter assParam =
                        (AssociatedParameter) iter.next();
                if (fieldName.equals(assParam.getFormalParam())) {
                    deletionsMade = true;
                    replaceCmd.append(RemoveCommand.create(editingDomain,
                            assParam));
                }
            }
        }

        // Delete Correlation Data associations
        if (Xpdl2ModelUtil.canHaveCorrelationAssociated(activity)) {
            Object other =
                    Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AssociatedCorrelationFields());
            if (other instanceof AssociatedCorrelationFields) {
                AssociatedCorrelationFields fieldContainer =
                        (AssociatedCorrelationFields) other;
                List<AssociatedCorrelationField> fieldList =
                        fieldContainer.getAssociatedCorrelationField();
                for (AssociatedCorrelationField correlation : fieldList) {
                    if (fieldName.equals(correlation.getName())) {
                        deletionsMade = true;
                        replaceCmd.append(RemoveCommand.create(editingDomain,
                                correlation));
                    }
                }
            }
        }
        return deletionsMade;
    }

    /**
     * Get the {@link CalendarReference} from the given container.
     * 
     * @param container
     * @return CalendarReference, or <code>null</code> if one is not set.
     */
    private CalendarReference getCalendarReference(
            OtherElementsContainer container) {
        Object element =
                Xpdl2ModelUtil.getOtherElement(container,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CalendarReference());

        return (CalendarReference) (element instanceof CalendarReference ? element
                : null);
    }

    /**
     * @param editingDomain
     * @param dataMappings
     * @param fieldName
     * @param replaceCmd
     * @param deletionsMade
     * @return
     */
    private boolean deleteFieldByNameFromDataMappingActual(
            EditingDomain editingDomain, EList<DataMapping> dataMappings,
            String fieldName, CompoundCommand replaceCmd) {
        boolean deletionsMade = false;
        for (Iterator iter = dataMappings.iterator(); iter.hasNext();) {
            DataMapping dm = (DataMapping) iter.next();

            Expression actual = dm.getActual();
            if (actual != null) {
                String oldName = actual.getText();

                if (fieldName.equals(oldName)) {
                    // This param is mapped from deleted field - delete it.
                    replaceCmd.append(RemoveCommand.create(editingDomain, dm));

                    deletionsMade = true;
                }
            }
        }
        return deletionsMade;
    }

    /**
     * append the contributed data resolvers that dela with things outside of
     * activity.
     * 
     * @param editingDomain
     * @param cmd
     * @param process
     * @param data
     */
    private void appendContributedDataResolverCommands(
            EditingDomain editingDomain, CompoundCommand cmd,
            EObject scopeObject, ProcessRelevantData data) {
        List<IDataReferenceResolver> dataReferenceResolvers =
                ResolverExtPointHelper.INSTANCE.getDataReferenceResolvers();
        if (dataReferenceResolvers != null) {
            for (IDataReferenceResolver dataReferenceResolver : dataReferenceResolvers) {
                Command c =
                        dataReferenceResolver
                                .getDeleteDataReferencesCommand(editingDomain,
                                        scopeObject,
                                        data);

                if (c != null) {
                    cmd.append(c);
                }
            }
        }
    }

}
