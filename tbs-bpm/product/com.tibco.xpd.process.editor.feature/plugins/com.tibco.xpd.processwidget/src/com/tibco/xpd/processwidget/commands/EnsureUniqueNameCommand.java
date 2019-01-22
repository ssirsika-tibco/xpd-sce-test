/**
 * EnsureUniqueNameCommand.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;

/**
 * EnsureUniqueNameCommand
 * 
 * Creates command to set name of a NamedElementAdapter adapted object with
 * uniqueness
 * 
 * Given a list of other NamedElementAdapter objects that are in the same scope
 * of name uniqueness, if an object with same name already exists then a new
 * name with a trailing number is used.
 */
public class EnsureUniqueNameCommand extends AbstractCommand {
    private EditingDomain editingDomain;

    // Preferred default name
    private String preferredName;

    // Model object to set the name of.
    // This will be adapted to NamedElement on execution.
    private Object namedElementTarget;

    // List of other objects to check names against
    private List<NamedElementAdapter> nameSiblings;

    private AdapterFactory adapterFactory;

    private Command cmd = null;

    /**
     * Create command that set name of given named element object to it's
     * preferred name appending a unique sequence number if the name is already
     * in use.
     * 
     * @param editingDomain
     * @param adapterFactory
     *            Factory to use to adapt namedElementTarget to
     *            NamedElementAdapter on execution.
     * @param namedElementTarget
     *            Model object that can be adapted to NamedElementAdapter on
     *            command execution.
     * @param preferredName
     *            or <code>null</code> to use existing name.
     * @param nameSiblings
     */
    public EnsureUniqueNameCommand(EditingDomain editingDomain,
            AdapterFactory adapterFactory, Object namedElementTarget,
            String preferredName, List<NamedElementAdapter> nameSiblings) {
        this.editingDomain = editingDomain;
        this.preferredName = preferredName;
        this.namedElementTarget = namedElementTarget;
        this.nameSiblings = nameSiblings;
        this.adapterFactory = adapterFactory;
    }

    /**
     * Create command that set name of given named element object to it's
     * preferred name appending a unique sequence number if the name is already
     * in use.
     * 
     * @param editingDomain
     * @param adapterFactory
     *            Factory to use to adapt namedElementTarget to
     *            NamedElementAdapter on execution.
     * @param namedElementTarget
     *            Model object that can be adapted to NamedElementAdapter on
     *            command execution.
     * @param preferredName
     * @param nameSiblings
     */
    public EnsureUniqueNameCommand(EditingDomain editingDomain,
            AdapterFactory adapterFactory, Object namedElementTarget,
            List<NamedElementAdapter> nameSiblings) {
        this(editingDomain, adapterFactory, namedElementTarget, null,
                nameSiblings);
    }

    public void execute() {
        //
        // Find all objects with start of name matching proposed name.
        NamedElementAdapter nameAdapter =
                (NamedElementAdapter) adapterFactory.adapt(namedElementTarget,
                        ProcessWidgetConstants.ADAPTER_TYPE);

        String defaultName;

        if (preferredName != null && preferredName.length() > 0) {
            defaultName = this.preferredName;
        } else {
            defaultName = nameAdapter.getName();
        }

        if (defaultName != null) {
            int sequenceNum = 1;
            for (Iterator iter = nameSiblings.iterator(); iter.hasNext();) {
                NamedElementAdapter namedElement =
                        (NamedElementAdapter) iter.next();

                if (!nameAdapter.getId().equals(namedElement.getId())) {
                    String name = namedElement.getName();

                    if (name.equals(defaultName)) {
                        if (sequenceNum < 2) {
                            sequenceNum = 2;
                        }
                    } else if (name.startsWith(defaultName)) {
                        // find out whats on the rest of the name.
                        int nameLen = defaultName.length();

                        try {
                            String next = name.substring(nameLen, nameLen + 1);
                            if (next.equals(" ")) { //$NON-NLS-1$

                                try {
                                    int thisNum =
                                            Integer.parseInt(name
                                                    .substring(nameLen + 1));

                                    sequenceNum =
                                            Math.max(sequenceNum, thisNum + 1);
                                } catch (NumberFormatException nme) {
                                    // Not a number after last space.
                                }
                            }
                        } catch (StringIndexOutOfBoundsException oobe) {

                        }
                    }
                }
            }

            String objectName;

            if (sequenceNum > 1) {
                objectName = defaultName + " " + sequenceNum; //$NON-NLS-1$
            } else {
                objectName = defaultName;
            }

            cmd = nameAdapter.getSetNameCommand(editingDomain, objectName);

            if (cmd.canExecute()) {
                cmd.execute();
            }
        }
    }

    public void redo() {
        if (cmd != null) {
            cmd.redo();
        }
    }

    @Override
    public void undo() {
        if (cmd != null) {
            cmd.undo();
        }
    }

    @Override
    protected boolean prepare() {
        return true;
    }
}
