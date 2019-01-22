package com.tibco.xpd.xpdl2.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.internal.Messages;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2ParticipantReplacer;
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
public class RenameParticipantCommand extends AbstractCommand {

    private CompoundCommand cmd = new CompoundCommand();

    private boolean isNewField = false;

    private String newName = ""; //$NON-NLS-1$

    private String oldName = ""; //$NON-NLS-1$

    private Participant renameField = null;

    private EditingDomain editingDomain = null;

    private Participant duplicateParticipant = null;

    List<ProcessRelevantData> prdList = new ArrayList<ProcessRelevantData>();
    
    private static Logger logger = XpdResourcesPlugin.getDefault().getLogger();

    /**
     * Command for renaming participant.
     * <p>
     * This will also rename references to the participant in activities,
     * process relevant data and transitions etc UNLESS isNewField=true
     * <p>
     * This command will set itself as unexecutable if a duplicate named field
     * exists within scope (i.e. for a process field this is means other
     * fields/params/participants in process and package.
     * 
     * @param editingDomain
     * @param data
     * @param oldName
     * @param newName
     * @param isNewField
     * @return
     */
    public static RenameParticipantCommand create(EditingDomain editingDomain,
            Participant data, EObject container, String oldName,
            String newName, boolean isNewField) {
        return new RenameParticipantCommand(editingDomain, data, container,
                oldName, newName, isNewField, false);
    }

    /**
     * Command for renaming participant.
     * <p>
     * This will also rename references to the participant in activities,
     * process relevant data and transitions etc UNLESS isNewField=true
     * <p>
     * This command will set itself as unexecutable if a duplicate named field
     * exists within scope (i.e. for a process field this is means other
     * fields/params/participants in process and package.
     * 
     * @param editingDomain
     * @param data
     * @param oldName
     * @param newName
     * @param isNewField
     * @return
     */
    public static RenameParticipantCommand create(EditingDomain editingDomain,
            Participant data, EObject container, String oldName,
            String newName, boolean isNewField, boolean refactoryOnly) {
        return new RenameParticipantCommand(editingDomain, data, container,
                oldName, newName, isNewField, refactoryOnly);
    }

    public RenameParticipantCommand(EditingDomain editingDomain,
            Participant data, EObject container, String oldName,
            String newName, boolean isNewField, boolean refactorOnly) {
        this.newName = newName;
        this.oldName = oldName;
        this.renameField = data;
        this.editingDomain = editingDomain;
        this.isNewField = isNewField;

        /** Check for duplicates up front. */
        duplicateParticipant =
                Xpdl2ModelUtil
                        .getDuplicateParticipant(container, data, newName);
        if (duplicateParticipant != null) {
            cmd.append(UnexecutableCommand.INSTANCE);
            logger
                    .info(getClass().getSimpleName()
                            + " - Not permitting rename token name of field/param '" //$NON-NLS-1$
                            + oldName
                            + "' to '" //$NON-NLS-1$
                            + newName
                            + "' as this would duplicate an existing field/param's token name."); //$NON-NLS-1$

        } else if (!refactorOnly) {
            /**
             * Rename the field (Provided we're not doing just the refactor.
             */
            cmd.append(SetCommand.create(editingDomain,
                    data,
                    Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                    newName));
        }

        if (data instanceof Participant) {
            cmd
                    .setLabel(Messages.RenameParticipantCommand_SetParticipantName_menu);
        }

        return;
    }

    public void execute() {
        if (!cmd.isEmpty()) {
            cmd.execute();
        }

        if (!isNewField) {
            /**
             * If not a new field and can execute then append the refactoring
             * references to field commands.
             */
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
    public Participant getDuplicateParticipant() {
        return duplicateParticipant;
    }

    /**
     * Add the commands necessary for refactoring references to the renamed
     * field/param.
     * 
     */
    private CompoundCommand getRefactorCommand() {
        CompoundCommand refactorCmd = new CompoundCommand();
        /**
         * Use a field reference replacer to allow other plug-ins (like service
         * task implementation etc) to replace the references in the model
         * extensions that they control.
         */

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put(oldName, newName);

        Xpdl2ParticipantReplacer replacer =
                new Xpdl2ParticipantReplacer(nameMap, false);

        EObject container = renameField.eContainer();
        /**
         * If participant is package level then replace all references in all
         * processes.
         */
        if (container instanceof Package) {
            Package pkg = (Package) container;
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
        /**
         * If participant is process level then replace all references in this
         * process.
         */
        else if (container instanceof Process) {
            Command replaceCmd =
                    getReplaceCmdForProcess(editingDomain,
                            (Process) container,
                            replacer);
            if (replaceCmd != null) {
                refactorCmd.append(replaceCmd);
            }
        } else {
            refactorCmd.append(UnexecutableCommand.INSTANCE);
        }

        return refactorCmd;
    }

    /**
     * Create a command to replace old participant name with new one.
     * 
     * @param editingDomain
     * 
     * @param process
     * @param replacer
     * @return
     */
    private Command getReplaceCmdForProcess(EditingDomain editingDomain,
            Process process, Xpdl2ParticipantReplacer replacer) {

        List<Command> replaceCmds = new ArrayList<Command>();

        /** Get commands for replacing participant name in process relevant data */
        if (process.getDataFields().size() > 0) {
            prdList.addAll(process.getDataFields());
        }
        if (process.getFormalParameters().size() > 0) {
            prdList.addAll(process.getFormalParameters());
        }
        for (Iterator iter = prdList.iterator(); iter.hasNext();) {
            ProcessRelevantData processRelevantData =
                    (ProcessRelevantData) iter.next();
            DataType dataType = processRelevantData.getDataType();
            if (dataType instanceof BasicType) {
                BasicType basicType = (BasicType) dataType;
                if (basicType.getType().equals(BasicTypeType.PERFORMER_LITERAL)) {
                    Command cmd =
                            replacer
                                    .getReplaceFieldReferencesCommand(editingDomain,
                                            processRelevantData);
                    if (null != cmd) {
                        replaceCmds.add(cmd);
                    }
                }
            }
        }

        /**
         * If anything did anything then create compound command and return it.
         */
        if (replaceCmds.size() > 0) {
            CompoundCommand result = new CompoundCommand();

            for (Iterator iter = replaceCmds.iterator(); iter.hasNext();) {
                Command cmd = (Command) iter.next();

                result.append(cmd);
            }

            return result;
        }

        /** Nothing needed changing. */
        return null;
    }

    public boolean canExecute() {
        if (cmd.isEmpty()) {
            return true; // May be doing refactor only
        }

        return cmd.canExecute();
    }

    public boolean canUndo() {
        return cmd.canUndo();
    }

    public Command chain(Command command) {
        return cmd.chain(command);
    }

    public void dispose() {
        cmd.dispose();
    }

    public Collection getAffectedObjects() {
        return cmd.getAffectedObjects();
    }

    public String getDescription() {
        return cmd.getDescription();
    }

    public String getLabel() {
        return cmd.getLabel();
    }

    public Collection getResult() {
        return cmd.getResult();
    }

    public void redo() {
        if (!cmd.isEmpty()) {
            cmd.redo();
        }
    }

    public void undo() {
        if (!cmd.isEmpty()) {
            cmd.undo();
        }
    }

}
