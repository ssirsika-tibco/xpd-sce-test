/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;

/**
 * Base class for spinner handlers.
 * 
 * @author njpatel
 * 
 */
public abstract class BaseSpinnerFieldHandler implements Listener,
        IEditingDomainProvider {

    private boolean enabled = true;

    /**
     * Delayed command runnable.
     * 
     * @author njpatel
     */
    private class DelayedCommand implements Runnable {

        private final EditingDomain editingDomain;

        private Command command;

        /**
         * Constructor.
         * 
         * @param editingDomain
         */
        public DelayedCommand(EditingDomain editingDomain) {
            this.editingDomain = editingDomain;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            Command cmd = clearCommand();
            if (cmd != null) {
                enabled = false;
                ignoreModelEvents(true);
                
                editingDomain.getCommandStack().execute(cmd);
                ignoreModelEvents(false);
                enabled = true;
            }
        }

        /**
         * Set <code>Command</code> to execute when this runnable is executed.
         * 
         * @param command
         */
        public void setCommand(Command command) {
            this.command = command;
        }

        /**
         * Get the current set <code>Command</code>.
         * 
         * @return
         */
        public Command getCommand() {
            return command;
        }

        public EditingDomain getEditingDomain() {
            return editingDomain;
        }

        /**
         * Clear the <code>Command</code>.
         * 
         * @return
         */
        public Command clearCommand() {
            Command cmd = command;
            command = null;
            return cmd;
        }
    }

    private DelayedCommand delayedCommand;

    private boolean modifyOnDeactivateOnly = false;

    private int valueWhenACtivated = -1;

    protected final Spinner spinner;

    /**
     * Constructor.
     * 
     * @param spinner
     */
    public BaseSpinnerFieldHandler(Spinner spinner) {
        this.spinner = spinner;
        register(spinner);
    }

    /**
     * Register listeners to the spinner.
     * 
     * @param spinner
     */
    protected void register(Spinner spinner) {
        spinner.addListener(SWT.Modify, this);
        spinner.addListener(SWT.Activate, this);
        spinner.addListener(SWT.Deactivate, this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.
     * Event)
     */
    public void handleEvent(Event event) {
        if (enabled) {
            switch (event.type) {
            case SWT.Modify:
                if (!modifyOnDeactivateOnly) {
                    doModify(event);
                }
                break;

            case SWT.Activate:
                // Get current value on activation.
                valueWhenACtivated = spinner.getSelection();
                break;

            case SWT.Deactivate:
                doDeactivate(event);
                break;
            }
        }
    }

    /**
     * Called in response to modify and deactivate of the spinner.
     * 
     * @param event
     */
    private void doModify(Event event) {
        Command cmd = createCommand();
        if (cmd != null) {
            if (cmd.canExecute()) {
                // getEditingDomain().getCommandStack().execute(cmd);
                if (delayedCommand != null) {
                    delayedCommand.clearCommand();
                }
                delayedCommand = new DelayedCommand(getEditingDomain());
                delayedCommand.setCommand(cmd);
                spinner.getDisplay().timerExec(300, delayedCommand);
            }
        }
    }

    /**
     * Called in response to a deactivate event.
     * 
     * @param event
     */
    protected void doDeactivate(Event event) {
        if (!modifyOnDeactivateOnly) {
            if (delayedCommand != null && delayedCommand.getCommand() != null) {
                Command cmd = delayedCommand.clearCommand();
                EditingDomain ed = getEditingDomain();
                ignoreModelEvents(true);

                ed.getCommandStack().execute(cmd);
                ignoreModelEvents(false);
            }
        } else {
            // Only do modify if value changed.
            if (spinner.getSelection() != valueWhenACtivated) {

                // added fix for MR 35394
                EditingDomain ed = getEditingDomain();
                if (ed == null) {
                    return;
                }
                // added fix for MR 35394
                Command cmd = createCommand();

                if (cmd != null) {
                    if (cmd.canExecute()) {
                        ed.getCommandStack().execute(cmd);
                    }
                }
            }
        }

    }

    /**
     * Create command to update the model.
     * 
     * @return <code>Command</code> to update model, <code>null</code> if model
     *         doesn't need updating.
     */
    protected abstract Command createCommand();

    /**
     * Set whether the model events should be ignored.
     * 
     * @param ignore
     *            <code>true</code> if events should be ignored,
     *            <code>false</code> otherwise.
     */
    protected abstract void ignoreModelEvents(boolean ignore);

    /**
     * When true this will prevent timed modifies for getting commands (you will
     * only get request to get command to change model when user deactivates
     * (moves away from) text box
     * <p>
     * 
     * @param modifyOnDeactivateOnly
     */
    public void setModifyOnDeactivateOnly(boolean modifyOnDeactivateOnly) {
        this.modifyOnDeactivateOnly = modifyOnDeactivateOnly;
    }

}
