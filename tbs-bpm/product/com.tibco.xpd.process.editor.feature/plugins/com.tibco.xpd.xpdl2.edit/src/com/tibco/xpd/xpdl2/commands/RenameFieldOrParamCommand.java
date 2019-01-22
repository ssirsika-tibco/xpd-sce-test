package com.tibco.xpd.xpdl2.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.internal.Messages;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamReplacer;
import com.tibco.xpd.xpdl2.util.LocalPackageProcessInterfaceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Command for renaming data field or formal parameter.
 * <p>
 * This will also rename references to the field / param in activities and
 * transitions etc UNLESS isNewField=true
 * <p>
 * This command will set itself as unexecutable if a duplicate named field
 * exists within scope (i.e. for a process field this is means other
 * fields/params in process and package.
 * 
 * @author aallway
 * 
 */
public class RenameFieldOrParamCommand extends AbstractCommand {

    /*
     * SID SDA-251: Use a special compound command that does not wrap and ignore
     * OperationCancelException when that is throiwn by underlying refactor
     * commands.
     */
    private AllowOperationCancelledCompoundCommand cmd =
            new AllowOperationCancelledCompoundCommand();

    private boolean isNewField = false;

    private String newName = ""; //$NON-NLS-1$

    private String oldName = ""; //$NON-NLS-1$

    private ProcessRelevantData renameField = null;

    private EditingDomain editingDomain = null;

    private ProcessRelevantData duplicateFieldOrParam = null;

    List<ProcessRelevantData> prdList = new ArrayList<ProcessRelevantData>();

    private static Logger logger = XpdResourcesPlugin.getDefault().getLogger();

    /**
     * Command for renaming data field or formal parameter.
     * <p>
     * This will also rename references to the field / param in activities and
     * transitions etc UNLESS isNewField=true
     * <p>
     * This command will set itself as unexecutable if a duplicate named field
     * exists within scope (i.e. for a process field this is means other
     * fields/params in process and package.
     * 
     * @param editingDomain
     * @param data
     * @param oldName
     * @param newName
     * @param isNewField
     * @return
     */
    public static RenameFieldOrParamCommand create(EditingDomain editingDomain,
            ProcessRelevantData data, EObject container, String oldName,
            String newName, boolean isNewField) {
        return new RenameFieldOrParamCommand(editingDomain, data, container,
                oldName, newName, isNewField, false);
    }

    /**
     * Command for renaming data field or formal parameter.
     * <p>
     * This will also rename references to the field / param in activities and
     * transitions etc UNLESS isNewField=true
     * <p>
     * This command will set itself as unexecutable if a duplicate named field
     * exists within scope (i.e. for a process field this is means other
     * fields/params in process and package.
     * 
     * @param editingDomain
     * @param data
     * @param oldName
     * @param newName
     * @param isNewField
     * @return
     */
    public static RenameFieldOrParamCommand create(EditingDomain editingDomain,
            ProcessRelevantData data, EObject container, String oldName,
            String newName, boolean isNewField, boolean refactoryOnly) {
        return new RenameFieldOrParamCommand(editingDomain, data, container,
                oldName, newName, isNewField, refactoryOnly);
    }

    public RenameFieldOrParamCommand(EditingDomain editingDomain,
            ProcessRelevantData data, EObject container, String oldName,
            String newName, boolean isNewField, boolean refactorOnly) {
        this.newName = newName;
        this.oldName = oldName;
        this.renameField = data;
        this.editingDomain = editingDomain;
        this.isNewField = isNewField;

        // Check for duplicates up front.
        duplicateFieldOrParam =
                Xpdl2ModelUtil.getDuplicateFieldOrParam(container,
                        data,
                        newName);
        if (duplicateFieldOrParam != null) {
            cmd.append(UnexecutableCommand.INSTANCE);
            logger.info(getClass().getSimpleName()
                    + " - Not permitting rename token name of field/param '" //$NON-NLS-1$
                    + oldName
                    + "' to '" //$NON-NLS-1$
                    + newName
                    + "' as this would duplicate an existing field/param's token name."); //$NON-NLS-1$

        } else if (!refactorOnly) {
            //
            // Rename the field (Provided we're not doing just the refactor.
            //
            cmd.append(SetCommand.create(editingDomain,
                    data,
                    Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                    newName));
        }

        if (data instanceof FormalParameter) {
            cmd.setLabel(Messages.RenameFieldOrParamCommand_SetParamName_menu);
        } else {
            cmd.setLabel(Messages.RenameFieldOrParamCommand_SetFieldName_menu);
        }

        return;
    }

    @Override
    public void execute() {
        if (!cmd.isEmpty()) {
            cmd.execute();
        }

        if (!isNewField) {
            // If not a new field and can execute then append the
            // refactoring references to field commands.
            CompoundCommand replaceCmd = getRefactorCommand();

            if (replaceCmd.canExecute()) {
                cmd.appendAndExecute(replaceCmd);
            }
        }
    }

    /**
     * Check whether, when this command was constructed, there was a duplicate
     * field with same name.
     * 
     * @return
     */
    public ProcessRelevantData getDuplicateFieldOrParam() {
        return duplicateFieldOrParam;
    }

    /**
     * Add the commands necessary for refactoring references to the renamed
     * field/param.
     * 
     */
    private CompoundCommand getRefactorCommand() {
        CompoundCommand refactorCmd = new CompoundCommand();

        //
        // Use a field reference replacer to allow other plug-ins (like
        // service task implementation etc) to replace the references in the
        // model extensions that they control.
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put(oldName, newName);

        Xpdl2FieldOrParamReplacer replacer =
                new Xpdl2FieldOrParamReplacer(nameMap, false);

        EObject container = renameField.eContainer();

        //
        // If field is package level then replace all references in all
        // processes.
        //
        if (container instanceof Package) {
            Package pkg = (Package) container;

            if (pkg.getDataFields().size() > 0) {
                // Performer fields can ref other fields in their scripts.
                List<EObject> fields =
                        new ArrayList<EObject>(pkg.getDataFields());
                Command replaceCommand =
                        getReplaceCmdForFieldReferencers(editingDomain,
                                fields,
                                replacer);
                if (refactorCmd != null) {
                    refactorCmd.append(replaceCommand);
                }
            }

            for (Iterator iter = pkg.getProcesses().iterator(); iter.hasNext();) {
                Process process = (Process) iter.next();

                Command replaceCmd =
                        getReplaceCmdForProcess(editingDomain,
                                process,
                                replacer);
                if (replaceCmd != null) {
                    refactorCmd.append(replaceCmd);
                }

            }

        }
        //
        // If field or param is process level then replace all references in
        // this process.
        // If its a formal param then we have to look for invocations to this
        // process and rename reference in parameter mappings in the independent
        // sub-process task.
        //
        else if (container instanceof Process) {
            Command replaceCmd =
                    getReplaceCmdForProcess(editingDomain,
                            (Process) container,
                            replacer);
            if (replaceCmd != null) {
                refactorCmd.append(replaceCmd);
            }

            // If this is a formal parameter rename then we have to search
            // specially for where this process is called from by indi sub-proc
            // tasks and rename these.
            Package pkg = (Package) container.eContainer();

            if (pkg != null && renameField instanceof FormalParameter
                    && oldName != null) {

                Set<String> calledProcessAndInterfacesIds =
                        new HashSet<String>();
                calledProcessAndInterfacesIds
                        .add(((Process) container).getId());

                replaceSubprocessMappingsToFormalParam(calledProcessAndInterfacesIds,
                        pkg,
                        refactorCmd);

            }

        }
        //
        // If field is activity level (currently this means embedded sub-process
        // activity only but this could potentialyy be any activity)
        else if (container instanceof Activity) {
            Command replaceCmd =
                    getReplaceCmdForActivity(editingDomain,
                            (Activity) container,
                            replacer);
            if (replaceCmd != null) {
                refactorCmd.append(replaceCmd);
            }
        }
        //
        // If parameter belongs to a process interface then have to replace
        // references within the process interface AND any process that
        // implements the interface.
        //
        // We must also replace reference in any independent sub-process task
        // mappings that invoke the interface or the processes that implement
        // it.
        //
        else if (container instanceof ProcessInterface
                && renameField instanceof FormalParameter) {
            ProcessInterface processIfc = (ProcessInterface) container;

            CompoundCommand result =
                    getReplaceCommandForProcessInterface(editingDomain,
                            processIfc,
                            replacer);

            if (result != null) {
                refactorCmd.append(result);
            }

        } else {
            refactorCmd.append(UnexecutableCommand.INSTANCE);
        }

        return refactorCmd;
    }

    /**
     * @param replacer
     * @param processIfc
     * @return a command to replace old field/param name with new one for
     *         everything in processinterface.
     */
    private CompoundCommand getReplaceCommandForProcessInterface(
            EditingDomain editingDomain, ProcessInterface processIfc,
            Xpdl2FieldOrParamReplacer replacer) {
        CompoundCommand result = new CompoundCommand();

        List<EObject> potentialReferencers = new ArrayList<EObject>();

        // First go thru the start and intermediate events in this interface
        EList methods = processIfc.getStartMethods();
        for (Iterator iterator = methods.iterator(); iterator.hasNext();) {
            StartMethod method = (StartMethod) iterator.next();

            potentialReferencers.add(method);

            List<ErrorMethod> errorMethods = method.getErrorMethods();
            for (ErrorMethod errorMethod : errorMethods) {
                potentialReferencers.add(errorMethod);
            }
        }

        methods = processIfc.getIntermediateMethods();
        for (Iterator iterator = methods.iterator(); iterator.hasNext();) {
            IntermediateMethod method = (IntermediateMethod) iterator.next();

            potentialReferencers.add(method);

            List<ErrorMethod> errorMethods = method.getErrorMethods();
            for (ErrorMethod errorMethod : errorMethods) {
                potentialReferencers.add(errorMethod);
            }
        }

        if (potentialReferencers.size() > 0) {
            Command replaceCmd =
                    getReplaceCmdForFieldReferencers(editingDomain,
                            potentialReferencers,
                            replacer);
            if (replaceCmd != null) {
                result.append(replaceCmd);
            }
        }

        // Then go thru all of the processes that implement interface in the
        // package and replace any of their references to the
        // interface-defined parameter.
        List<Process> implementingProcesses =
                LocalPackageProcessInterfaceUtil
                        .getImplementingProcesses(processIfc);
        for (Process implementingProcess : implementingProcesses) {
            Command replaceCmd =
                    getReplaceCmdForProcess(editingDomain,
                            implementingProcess,
                            replacer);
            if (replaceCmd != null) {
                result.append(replaceCmd);
            }
        }

        // And finally, replace the 'formal param' part of parameter
        // mappings from sub-process tasks that invoke the interface
        // directly or processes that implement the interface.
        Package pkg = Xpdl2ModelUtil.getPackage(processIfc);
        if (pkg != null) {
            Set<String> calledProcessAndInterfacesIds = new HashSet<String>();
            calledProcessAndInterfacesIds.add(processIfc.getId());

            for (Process implementingProcess : implementingProcesses) {
                calledProcessAndInterfacesIds.add(implementingProcess.getId());
            }

            replaceSubprocessMappingsToFormalParam(calledProcessAndInterfacesIds,
                    pkg,
                    result);
        }

        /*
         * XPD-5427: Replace miscellaneous refs outside of
         * activities/transitions.
         */
        Command c =
                replacer.getReplaceMiscellaneousRefsCommand(editingDomain,
                        processIfc);
        if (c != null) {
            cmd.append(c);
        }

        if (!result.isEmpty()) {
            return result;
        }

        return null;
    }

    /**
     * Go thru all processes in the given package checking for independent
     * sub-process tasks that invoke any of the process or process interfaces
     * specified in the given set of ids.
     * <p>
     * Replace the 'mapped to' formal param name in each for the param we are
     * renaming.
     * 
     * @param calledProcessAndInterfacesIds
     * @param pkg
     * @param refactorCmd
     */
    private void replaceSubprocessMappingsToFormalParam(
            Set<String> calledProcessAndInterfacesIds, Package pkg,
            CompoundCommand refactorCmd) {
        for (Iterator iter = pkg.getProcesses().iterator(); iter.hasNext();) {
            Process p = (Process) iter.next();

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(p);

            for (Activity act : activities) {
                // Check its an indi sub-proc call task
                if (act.getImplementation() instanceof SubFlow) {
                    SubFlow subFlow = (SubFlow) act.getImplementation();

                    // Check whether its a call to the formal param's
                    // parent proc
                    String pkgRef = subFlow.getPackageRefId();
                    if (pkgRef == null || pkgRef.length() == 0) {
                        if (calledProcessAndInterfacesIds.contains(subFlow
                                .getProcessId())) {

                            // Replace references to our formal param.
                            EList dataMappings = subFlow.getDataMappings();

                            for (Iterator iterator = dataMappings.iterator(); iterator
                                    .hasNext();) {
                                DataMapping dm = (DataMapping) iterator.next();

                                if (oldName.equals(dm.getFormal())) {
                                    refactorCmd
                                            .append(SetCommand
                                                    .create(editingDomain,
                                                            dm,
                                                            Xpdl2Package.eINSTANCE
                                                                    .getDataMapping_Formal(),
                                                            newName));
                                }
                            } // Next formal parameter mapping in sub-process
                              // task
                        }
                    }
                }

            } // next activity in process.

        } // next process in package.

        return;
    }

    /**
     * @param editingDomain
     * @param process
     * @param replacer
     * @return a command to replace old field/param name with new one for
     *         everything in process scope.
     */
    private Command getReplaceCmdForProcess(EditingDomain editingDomain,
            Process process, Xpdl2FieldOrParamReplacer replacer) {

        List<EObject> potentialReferencers = new ArrayList<EObject>();

        // Because of performer field scripts, fields/params can reference other
        // fields/params.
        potentialReferencers.addAll(process.getDataFields());
        potentialReferencers.addAll(process.getFormalParameters());

        // Add all activities and their data fields (emb subproc acts)
        Collection<Activity> activities =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity act : activities) {
            potentialReferencers.add(act);
            potentialReferencers.addAll(act.getDataFields());
        }

        // Add all transitions and their data fields.
        potentialReferencers.addAll(Xpdl2ModelUtil
                .getAllTransitionsInProc(process));

        // Get replace reference commands for all the potential referencers in
        // scope of the
        CompoundCommand cmd = new CompoundCommand();

        Command c =
                getReplaceCmdForFieldReferencers(editingDomain,
                        potentialReferencers,
                        replacer);

        if (c != null) {
            cmd.append(c);
        }

        /*
         * XPD-5427: Replace miscellaneous refs outside of
         * activities/transitions.
         */
        c = replacer.getReplaceMiscellaneousRefsCommand(editingDomain, process);
        if (c != null) {
            cmd.append(c);
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * @param editingDomain
     * @param process
     * @param replacer
     * @return a command to replace old field/param name with new one for
     *         everything in activity scope.
     */
    private Command getReplaceCmdForActivity(EditingDomain editingDomain,
            Activity activity, Xpdl2FieldOrParamReplacer replacer) {

        List<EObject> potentialReferencers = new ArrayList<EObject>();

        potentialReferencers.add(activity);

        // Because of performer field scripts, fields/params can reference other
        // fields/params.
        potentialReferencers.addAll(activity.getDataFields());

        if (activity.getBlockActivity() != null) {
            //
            // Deal with embedded sub-process children.

            // Add all activities and their data fields (emb subproc acts)
            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInEmbeddedSubProc(activity);
            for (Activity act : activities) {
                potentialReferencers.add(act);
                potentialReferencers.addAll(act.getDataFields());
            }

            // Add all transitions and their data fields.
            potentialReferencers.addAll(Xpdl2ModelUtil
                    .getAllTransitionsInEmbeddedSubProc(activity));
        }

        // Get replace reference commands for all the potential referencers in
        // scope of the
        CompoundCommand cmd = new CompoundCommand();

        Command c =
                getReplaceCmdForFieldReferencers(editingDomain,
                        potentialReferencers,
                        replacer);

        if (c != null) {
            cmd.append(c);
        }

        /*
         * XPD-5427: Replace miscellaneous refs outside of
         * activities/transitions.
         */
        c =
                replacer.getReplaceMiscellaneousRefsCommand(editingDomain,
                        activity);
        if (c != null) {
            cmd.append(c);
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * @param editingDomain
     * @param referencers
     * @param replacer
     * @return Command to replace references in given list of poss
     */
    private Command getReplaceCmdForFieldReferencers(
            EditingDomain editingDomain, Collection<EObject> referencers,
            Xpdl2FieldOrParamReplacer replacer) {

        CompoundCommand result = new CompoundCommand();

        for (EObject ref : referencers) {
            Command cmd =
                    replacer.getReplaceFieldReferencesCommand(editingDomain,
                            ref);

            if (cmd != null) {
                result.append(cmd);
            }
        }

        //
        // If anything did anything then create compound command and return it.
        if (!result.isEmpty()) {
            return result;
        }

        // Nothing needed changing.
        return null;
    }

    @Override
    public boolean canExecute() {
        if (cmd.isEmpty()) {
            return true; // May be doing refactor only
        }

        return cmd.canExecute();
    }

    @Override
    public boolean canUndo() {
        return cmd.canUndo();
    }

    @Override
    public Command chain(Command command) {
        return cmd.chain(command);
    }

    @Override
    public void dispose() {
        cmd.dispose();
    }

    @Override
    public Collection getAffectedObjects() {
        return cmd.getAffectedObjects();
    }

    @Override
    public String getDescription() {
        return cmd.getDescription();
    }

    @Override
    public String getLabel() {
        return cmd.getLabel();
    }

    @Override
    public Collection getResult() {
        return cmd.getResult();
    }

    @Override
    public void redo() {
        if (!cmd.isEmpty()) {
            cmd.redo();
        }
    }

    @Override
    public void undo() {
        if (!cmd.isEmpty()) {
            cmd.undo();
        }
    }

    /**
     * SID SDA-251: Use a special compound command that does not wrap and ignore
     * OperationCancelException when that is thrown by underlying refactor
     * commands.
     * <p>
     * When {@link RenameFieldOrParamCommand#execute()} is performed it gets the
     * refactor command and then performs {@link #appendAndExecute(Command)}.
     * The standard {@link CompoundCommand#appendAndExecute(Command)} call
     * catches all {@link RuntimeException}'s (which includes
     * {@link OperationCanceledException}) and logs them instead - it then
     * returns <code>false</code> instead of <code>true</code> <b>Unfortunately
     * this means that genuine reasons for cancellation are ignored and the
     * rename goes ahead anyway.</b>
     * <p>
     * We could have changed {@link RenameFieldOrParamCommand#execute()} to
     * check for <code>false</code> and throw it's own
     * {@link OperationCanceledException} however we would not want to do this
     * for all {@link RuntimeException} just {@link OperationCanceledException}
     * (it is vital that we don't allow contributors
     * {@link Xpdl2FieldOrParamReplacer}'s that are badly coded to prevent any
     * renaming of fields to be done!).
     * <p>
     * Hence we have our own {@link AllowOperationCancelledCompoundCommand}
     * which overrides {@link #appendAndExecute(Command)} in order to catch
     * {@link OperationCanceledException}'s excplitily an drethrow them instead
     * of logging them.
     * <p>
     * Unfortunately this mean copying the content of
     * {@link CompoundCommand#appendAndExecute(Command)} but there was no
     * choice.
     * 
     * @author aallway
     * @since 3 Nov 2011
     */
    private static class AllowOperationCancelledCompoundCommand extends
            CompoundCommand {
        /**
         * @see org.eclipse.emf.common.command.CompoundCommand#appendAndExecute(org.eclipse.emf.common.command.Command)
         * 
         * @param command
         * @return
         */
        @Override
        public boolean appendAndExecute(Command command) {

            if (command != null) {
                if (!isPrepared) {
                    if (commandList.isEmpty()) {
                        isPrepared = true;
                        isExecutable = true;
                    } else {
                        isExecutable = prepare();
                        isPrepared = true;
                        if (isExecutable) {
                            execute();
                        }
                    }
                }

                if (command.canExecute()) {
                    try {
                        command.execute();
                        commandList.add(command);
                        return true;

                    } catch (OperationCanceledException exception) {
                        throw exception;

                    } catch (RuntimeException exception) {
                        CommonPlugin.INSTANCE
                                .log(new WrappedException(
                                        CommonPlugin.INSTANCE
                                                .getString("_UI_IgnoreException_exception"), exception).fillInStackTrace()); //$NON-NLS-1$
                    }
                }

                command.dispose();
            }

            return false;
        }
    }
}
