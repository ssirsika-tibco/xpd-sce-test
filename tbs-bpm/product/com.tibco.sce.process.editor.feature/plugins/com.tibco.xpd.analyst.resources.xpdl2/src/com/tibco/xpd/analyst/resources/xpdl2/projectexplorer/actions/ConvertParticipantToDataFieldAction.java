/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.actions.SelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;

/**
 * Converts a participant to a data field.
 * <p>
 * As we now encourage use of participants at package level only and data fields
 * at process level only we will create a data field in EACH process to match
 * the participant and replace any references to the participant with references
 * to the created field.
 * <p>
 * References to the participant are updated as required.
 * <p>
 * Sid XPD-8381: Converted to extend SelectionListenerAction not WorkspaceAction
 * (the latter has certain requirements of a selection, like it must be able to
 * relate to a resource properly etc which
 * org.eclipse.bpel.validator.factory.AdapterFactory spoils because it fails to
 * do so). Anyway, it's not a workspace resource change so should not have been
 * a WorkspaceAction in the first place.
 * 
 */
public class ConvertParticipantToDataFieldAction
        extends SelectionListenerAction {

    public ConvertParticipantToDataFieldAction(String text) {
        super(text);
        setImageDescriptor(Xpdl2ResourcesPlugin
                .getImageDescriptor(Xpdl2ResourcesConsts.ICON_PARAMTOFIELD));
    }

    @Override
    public void run() {
        List<Participant> participants = new ArrayList<Participant>();

        Iterator iterator = getStructuredSelection().iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj instanceof Participant) {
                Participant participant = (Participant) obj;

                if (participant.eContainer() instanceof Package) {
                    /*
                     * We now create data fields in each process for package
                     * participants (because we discourage package fields
                     */
                    if (((Package) participant.eContainer()).getProcesses()
                            .size() == 0) {
                        MessageDialog.openError(
                                Display.getCurrent().getActiveShell(),
                                Messages.ParticipantToDataFieldActionProvider_Title,
                                Messages.ConvertParticipantToDataFieldAction_CannotConvertWithNoProcesses_longdesc);
                        return;
                    }
                }
                participants.add(participant);
            }
        }

        if (participants.size() > 0) {
            EditingDomain ed =
                    WorkingCopyUtil.getEditingDomain(participants.get(0));

            CompoundCommand cmd = new CompoundCommand(
                    Messages.ConvertParticipantToDataFieldCommand_ConvertParticToField_menu);
            for (Participant participant : participants) {
                cmd.append(new ConvertParticipantToDataFieldCommand(ed,
                        participant));
            }

            if (cmd.canExecute()) {
                ed.getCommandStack().execute(cmd);
            } else {
                System.err.println(
                        "ConvertParticipatToDataFieldAction: can't execute"); //$NON-NLS-1$
            }
            cmd.dispose();
        }
        return;
    }

}
