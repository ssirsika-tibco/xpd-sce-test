/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.viewer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * Extension of GEF command stack. It replace compound GEF comand composed of a
 * number of wrapped EMF comand with one compound EMF command.
 * 
 * @see com.tibco.xpd.processwidget.viewer.EMFCommandWrapper
 * 
 * @author wzurek
 */
public final class GEFtoEMFCommandStack extends CommandStack {
    private final EditingDomain editingDomain;

    public GEFtoEMFCommandStack(EditingDomain editingDomain) {
        this.editingDomain = editingDomain;

    }

    public void execute(Command command) {
        // if the command is complex, check if particular pards of this
        // command areEMF commands, and if so, execute them as complex
        // EMF command,instead as a sequence of command
        if (command instanceof CompoundCommand) {
            org.eclipse.emf.common.command.CompoundCommand emfCompound = new org.eclipse.emf.common.command.CompoundCommand();
            command = resolveCompoundGEFCommand((CompoundCommand) command,
                    emfCompound);
            if (!emfCompound.isEmpty()) {
                editingDomain.getCommandStack().execute(emfCompound);
            }
            if (command != null) {
                super.execute(command);
            }
        } else {
            super.execute(command);
        }
    }

    private Command resolveCompoundGEFCommand(CompoundCommand command,
            org.eclipse.emf.common.command.CompoundCommand emfCompound) {
        List cmds = command.getCommands();
        List gefCmds = new ArrayList();
        for (Iterator iter = cmds.iterator(); iter.hasNext();) {
            Command cmd = (Command) iter.next();
            if (cmd instanceof EMFCommandWrapper) {
                EMFCommandWrapper cmdWrapper = (EMFCommandWrapper) cmd;
                emfCompound.append((cmdWrapper).getEmfCommand());
            } else if (cmd instanceof CompoundCommand) {
                cmd = resolveCompoundGEFCommand((CompoundCommand) cmd,
                        emfCompound);
                if (cmd != null) {
                    gefCmds.add(cmd);
                }
            } else {
                gefCmds.add(cmd);
            }
        }
        if (gefCmds.isEmpty()) {
            return null;
        }
        command = new CompoundCommand();
        command.getCommands().addAll(gefCmds);
        return command;
    }
}