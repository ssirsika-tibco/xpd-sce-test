/**
 * LateExecuteCompoundCommand.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.xpdl2.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;

/**
 * LateExecuteCompoundCommand
 * <p>
 * This version of EMF CompoundCommand prepares and executes sub-commands ONLY
 * when the command is executed NOT when the command is prepared.
 * <p>
 * This means that when you need to compose a command that for instance does
 * multiple adds/removes from the same containment list of objects, you don't
 * get into trouble because of the fact that add and remove often store the
 * index of item to remove on preparation NOT on execution (which means that
 * multiple adds/removes to same list can get screwed up.
 * 
 */
public class LateExecuteCompoundCommand extends CompoundCommand {
    
    List<Command> appendedCommands = new ArrayList<Command>();
    
    /* (non-Javadoc)
     * @see org.eclipse.emf.common.command.CompoundCommand#append(org.eclipse.emf.common.command.Command)
     */
    @Override
    public void append(Command command) {
        // We'll save appending the commands til later.
        appendedCommands.add(command);
    }

    /* (non-Javadoc)
     * @see org.eclipse.emf.common.command.CompoundCommand#appendIfCanExecute(org.eclipse.emf.common.command.Command)
     */
    @Override
    public boolean appendIfCanExecute(Command command) {
        if (command == null)
        {
          return false;
        }
        else if (command.canExecute())
        {
          appendedCommands.add(command);
          return true;
        }
        else
        {
          command.dispose();
          return false;
        }
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
     */
    @Override
    public boolean canExecute() {
        return !appendedCommands.isEmpty();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.emf.common.command.CompoundCommand#execute()
     */
    @Override
    public void execute() {
        for (Command cmd : appendedCommands) {
            this.appendAndExecute(cmd);
        }
    }
    
    @Override
    public boolean isEmpty() {
        return appendedCommands.isEmpty();
    }
    
    @Override
    public List<Command> getCommandList() {
        return appendedCommands;
    }
}
