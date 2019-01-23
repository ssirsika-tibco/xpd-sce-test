/**
 * XPDCutToClipboardCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.xpdl2.resources;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.action.ProcessClipboardUtils;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;

/**
 * XPDCutToClipboardCommand
 * <p>
 * The EMF CutToClipboardCommand class includes copying of objects to clipboard
 * in the Undo Stack.
 * </p>
 * 
 * <p>
 * This means that if you do a Copy to clipboard, then undo, the clipboard will
 * contain the data prior to the copy in the clipboard.
 * </p>
 * 
 * <p>
 * This non-standard behaviour. So the XPD class prevents this from happening.
 * </p>
 * 
 * <p>
 * We could have just sub-classed CutToClipboard and overridden doUndo() /
 * doRedo() to prevent undo / redo from having any affect. However
 * CutToClipboard also keeps a copy of previous clipboard content which is a bit
 * of a waste as we will never use it.
 * </p>
 * 
 * <p>
 * Therefore this is a complete copy of EMF CutToClipboardCommand code with the
 * undo / redo and keep previous clipboard content stuff removed.
 * </p>
 * 
 * @deprecated Only used via {@link EditingDomainWithSystemClipboard} which is
 *             itself deprecated.
 */
@Deprecated
public class XPDCutToClipboardCommand extends CommandWrapper {

    /**
     * This caches the label.
     */
    protected static final String LABEL =
            Messages.XPDCutToClipboardCommand_label;

    /**
     * This caches the description.
     */
    protected static final String DESCRIPTION =
            Messages.XPDCutToClipboardCommand_message;

    /**
     * This is the editing doman in which this command operates.
     */
    protected EditingDomain domain;

    /**
     * This constructs an instance that ields the result of the given command as
     * its clipboard.
     */
    public XPDCutToClipboardCommand(EditingDomain domain, Command command) {
        super(LABEL, DESCRIPTION, command);

        this.domain = domain;
    }

    @Override
    public void execute() {
        super.execute();

        if (domain != null) {
            ProcessClipboardUtils.setClipboard(command.getResult());
        }
    }

    @Override
    public void undo() {
        super.undo();

    }

    @Override
    public void redo() {
        super.redo();

    }

    /**
     * This gives an abbreviated name using this object's own class' name,
     * without package qualification, followed by a space separated list of
     * <tt>field:value</tt> pairs.
     */
    @Override
    public String toString() {
        return String.format(Messages.XPDCutToClipboardCommand_shortdesc,
                domain.toString());
    }

}
