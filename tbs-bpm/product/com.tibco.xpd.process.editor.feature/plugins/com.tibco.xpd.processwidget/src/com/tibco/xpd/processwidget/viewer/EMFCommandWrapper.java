/* 
 ** 
 **  MODULE:             $RCSfile: EMFCommandWrapper.java $ 
 **                      $Revision: 1.5 $ 
 **                      $Date: 2005/03/31 14:51:05Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: EMFCommandWrapper.java $ 
 **    Revision 1.5  2005/03/31 14:51:05Z  wzurek 
 **    Revision 1.4  2005/03/08 13:06:44Z  wzurek 
 **    Revision 1.3  2005/01/13 18:00:45Z  WojciechZ 
 **    work in progress 
 **    Revision 1.2  2004/12/15 13:16:01Z  WojciechZ 
 **    work in progress 
 **    Revision 1.1  2004/11/23 16:55:58Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  14-Oct-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.xpd.processwidget.viewer;

import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.gef.commands.Command;

/**
 * Wraps EMF command into GEF command<br>
 * Wrapper can only execute EMF command, it is not possible to undo EMF command
 * using this wrapper. Use this wrapper when there is need to execute single EMF
 * command.
 * 
 * @see com.tibco.xpd.processwidget.viewer.GEFtoEMFCommandStack
 * 
 * @author WojciechZ
 */
public class EMFCommandWrapper extends Command implements
        IEditingDomainProvider {

    protected org.eclipse.emf.common.command.Command emfCmd;

    protected EditingDomain editingDomain;

    /**
     * @param editingDomain
     * @param emfCmd
     *            EMF Command to wrap
     */
    public EMFCommandWrapper(EditingDomain editingDomain,
            org.eclipse.emf.common.command.Command emfCmd) {
        this.editingDomain = editingDomain;
        if (emfCmd != null) {
            this.emfCmd = emfCmd;
        } else {
            this.emfCmd = UnexecutableCommand.INSTANCE;
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.gef.commands.Command#getLabel()
     */
    @Override
    public String getLabel() {
        return emfCmd.getLabel();
    }
    
    /**
     * Delegate to EMF command
     * 
     * @see org.eclipse.gef.commands.Command#canExecute()
     */
    public boolean canExecute() {
        return emfCmd.canExecute();
    }

    /**
     * Allways false, undo of this GEF command is not supported, use command
     * stack of the EMF Editing Domain
     * 
     * @see org.eclipse.gef.commands.Command#canUndo()
     */
    public boolean canUndo() {
        return false;
    }

    /**
     * Execute underlaying EMF command
     * 
     * @see org.eclipse.gef.commands.Command#execute()
     */
    public void execute() {
        if (emfCmd != null && editingDomain != null) {
            editingDomain.getCommandStack().execute(emfCmd);
        }
    }

    /**
     * Unsupported (throws exception)
     * 
     * @see org.eclipse.gef.commands.Command#undo()
     */
    public void undo() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return Returns the wrapped EMF Command.
     */
    public org.eclipse.emf.common.command.Command getEmfCommand() {
        return emfCmd;
    }

    /**
     * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
     */
    public EditingDomain getEditingDomain() {
        return editingDomain;
    }
}