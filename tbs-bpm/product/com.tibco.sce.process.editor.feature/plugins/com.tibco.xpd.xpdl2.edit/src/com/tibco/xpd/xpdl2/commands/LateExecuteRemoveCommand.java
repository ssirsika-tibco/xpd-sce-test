/**
 * 
 */
package com.tibco.xpd.xpdl2.commands;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Remove command that late exec's (so is safe to use to remove several objects
 * individually from the same list).
 * <p>
 * This is unlike using RemoveCommand which gets very confused if multiple
 * indivudual removes are done because each ius prepared as it is created and
 * each will see the list as it was befoer any remove was done - so it gets all
 * it's indexes messed up.
 * 
 * @author aallway
 * @since 3.4.2 (17 Sep 2010)
 */
public class LateExecuteRemoveCommand extends AbstractLateExecuteCommand {

    public static Command create(EditingDomain editingDomain, Object toRemove) {
        return new LateExecuteRemoveCommand(editingDomain, toRemove);
    }

    /**
     * @param editingDomain
     * @param contextObject
     */
    public LateExecuteRemoveCommand(EditingDomain editingDomain, Object toRemove) {
        super(editingDomain, toRemove);
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
        return RemoveCommand.create(editingDomain, contextObject);
    }

}
