/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Converts a participant to a data field.
 * <p>
 * As we now encourage use of participants at package level only and data fields
 * at process level only we will create a data field in EACH process to match
 * the participant and replace any references to the participant with references
 * to the created field.
 * <p>
 * References to the participant are updated as required.
 * 
 * @author aallway
 * @since 3.3 (6 Jan 2010)
 */
public class ConvertParticipantToDataFieldCommand extends
        AbstractLateExecuteCommand {

    /**
     * @param editingDomain
     * @param contextObject
     */
    public ConvertParticipantToDataFieldCommand(EditingDomain editingDomain,
            Participant participant) {
        super(editingDomain, participant);
        setLabel(Messages.ConvertParticipantToDataFieldCommand_ConvertParticToField_menu);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand
     * (org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object)
     */
    @Override
    protected Command createCommand(EditingDomain editingDomain,
            Object contextObject) {
        /*
         * Safe to assume that context object is that which we passed to super
         * constructor.
         */
        Participant participant = (Participant) contextObject;

        EObject container = participant.eContainer();

        if (container instanceof Process) {
            return getConvertProcessParticipantCommand(editingDomain,
                    (Process) container,
                    participant);

        } else if (container instanceof Package) {
            return getConvertPackageParticipantCommand(editingDomain,
                    (Package) container,
                    participant);
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @param editingDomain
     * @param container
     * @param participant
     * 
     * @return Command to convert process level participant to a data field in
     *         the same process.
     */
    private Command getConvertProcessParticipantCommand(
            EditingDomain editingDomain, Process process,
            Participant participant) {
        CompoundCommand cmd = new CompoundCommand();

        /* Remove the old participant. */
        cmd.append(RemoveCommand.create(editingDomain, participant));

        /* Create field and update references. */
        cmd.append(createProcessFieldAndUpdateReferences(editingDomain,
                process,
                participant));

        return cmd;
    }

    /**
     * @param editingDomain
     * @param container
     * @param participant
     * 
     * @return Command to convert package level participant to a data field in
     *         the referencing processes.
     */
    private Command getConvertPackageParticipantCommand(
            EditingDomain editingDomain, Package pkg, Participant participant) {
        /*
         * Create a field for participant in each process.
         */
        if (pkg.getProcesses().size() == 0) {
            /*
             * Don't allow partic to be deleted if we aren't going to replace it
             * with anything!
             */
            return UnexecutableCommand.INSTANCE;

        }

        CompoundCommand cmd = new CompoundCommand();

        /* Remove the old participant. */
        cmd.append(RemoveCommand.create(editingDomain, participant));

        /* Create field in each process and update performer references. */
        for (Process process : pkg.getProcesses()) {
            cmd.append(createProcessFieldAndUpdateReferences(editingDomain,
                    process,
                    participant));
        }

        return cmd;
    }

    /**
     * Add commands to the given compound command
     * 
     * @param editingDomain
     * @param process
     * @param participant
     * 
     *            return command to create field for field in given process and
     *            update any references.
     */
    private Command createProcessFieldAndUpdateReferences(
            EditingDomain editingDomain, Process process,
            Participant participant) {
        CompoundCommand cmd = new CompoundCommand();

        /* Create field from participant */
        DataField dataField =
                createProcessFieldFromParticipant(process, participant);

        /* Add the new data field. */
        cmd.append(AddCommand.create(editingDomain,
                process,
                Xpdl2Package.eINSTANCE.getDataFieldsContainer_DataFields(),
                dataField));

        /* Replace references to participant in activity performers. */
        String particId = participant.getId();
        String fieldId = dataField.getId();

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity activity : allActivitiesInProc) {
            EList<Performer> performerList = activity.getPerformerList();

            for (Performer performer : performerList) {
                if (particId.equals(performer.getValue())) {
                    cmd.append(SetCommand.create(editingDomain,
                            performer,
                            Xpdl2Package.eINSTANCE.getPerformer_Value(),
                            fieldId));
                }
            }
        }

        return cmd;
    }

    /**
     * @param process
     * @param participant
     * 
     * @return a data field to match the participant.
     */
    private DataField createProcessFieldFromParticipant(Process process,
            Participant participant) {
        /*
         * Create the new data field based on participant.
         */
        DataField dataField = Xpdl2Factory.eINSTANCE.createDataField();
        BasicType dataType = Xpdl2Factory.eINSTANCE.createBasicType();
        dataType.setType(BasicTypeType.PERFORMER_LITERAL);
        dataField.setDataType(dataType);
        dataField.setDescription(participant.getDescription());

        /*
         * Name the new data field based on participant but ensure uniquely
         * within scope.
         */
        Collection<ProcessRelevantData> allDataDefinedInProcess =
                ProcessInterfaceUtil.getAllDataDefinedInProcess(process);

        String fieldName =
                Xpdl2ModelUtil.getUniqueDisplayNameInSet(Xpdl2ModelUtil
                        .getDisplayNameOrName(participant),
                        allDataDefinedInProcess,
                        false);
        Xpdl2ModelUtil.setOtherAttribute(dataField,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                fieldName);

        dataField.setName(NameUtil.getInternalName(fieldName, false));
        return dataField;
    }

}
