/**
 * 
 */
package com.tibco.xpd.xpdl2.commands;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Command to remove an object from one list sequence to a different one.
 * <p>
 * The command is 'late executed' so that the RemoveCommand does not get
 * confused about seuqcne indexes etc.
 * 
 * @author aallway
 * @since 3.4.2 (17 Sep 2010)
 */
public class ReparentListElementCommand extends CompoundCommand {

    private EStructuralFeature ownerFeature;

    private Object newOwner;

    private Object toReparent;

    private EditingDomain editingDomain;

    public static Command create(EditingDomain editingDomain,
            Object toReparent, Object newOwner, EStructuralFeature ownerFeature) {
        return new ReparentListElementCommand(editingDomain, toReparent,
                newOwner, ownerFeature);
    }

    /**
     * @param editingDomain
     * @param contextObject
     */
    private ReparentListElementCommand(EditingDomain editingDomain,
            Object toReparent, Object newOwner, EStructuralFeature ownerFeature) {
        super();
        this.editingDomain = editingDomain;
        this.toReparent = toReparent;
        this.newOwner = newOwner;
        this.ownerFeature = ownerFeature;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
     */
    @Override
    public boolean canExecute() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.CompoundCommand#execute()
     */
    @Override
    public void execute() {
        this.appendAndExecute(RemoveCommand.create(editingDomain, toReparent));
        this.appendAndExecute(AddCommand.create(editingDomain,
                newOwner,
                ownerFeature,
                toReparent));
        return;
    }

}
