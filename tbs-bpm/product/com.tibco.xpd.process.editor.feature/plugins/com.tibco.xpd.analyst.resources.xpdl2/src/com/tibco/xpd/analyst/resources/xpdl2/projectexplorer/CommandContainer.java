package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * This is an Container class which contains the editing domain and the command
 * to execute on the editing domain.
 * 
 * @author KamleshU
 * 
 */
public class CommandContainer {
	private EditingDomain editingDomain;

	private Command command;

	/**
	 * 
	 * @param editingDomain
	 * @param command
	 */
	public CommandContainer(EditingDomain editingDomain, Command command) {
		this.editingDomain = editingDomain;
		this.command = command;
	}

	/**
	 * 
	 * 
	 */
	public void executeCommand() {
		editingDomain.getCommandStack().execute(command);
	}

	public Command getCommand() {
		return this.command;
	}

	public EditingDomain getEditingDomain() {
		return this.editingDomain;
	}

}
