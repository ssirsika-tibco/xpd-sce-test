/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


/**
 * Base delayed command text handler, see subclasses for more comments
 * 
 * @author wzurek
 */
public abstract class BaseStyledTextFieldHandler implements Listener {
    private boolean enabled = true;

    /**
     * Delayed command runnable
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
         * Set the <code>Command</code> to execute when this runnable runs.
         * 
         * @param command
         */
        public void setCommand(Command command) {
            this.command = command;
        }

        /**
         * Get the current <code>Command</code>.
         * 
         * @return
         */
        public Command getCommand() {
            return command;
        }

        /**
         * Get the editing domain.
         * 
         * @return
         */
        public EditingDomain getEditingDomain() {
            return editingDomain;
        }

        /**
         * Clear the current command.
         * 
         * @return
         */
        public Command clearCommand() {
            Command cmd = command;
            command = null;
            return cmd;
        }
    }

    protected final StyledText text;

    private DelayedCommand delayedCommand;

    private final CommandProvider commandProvider;

    private boolean shouldRefresh;

    private boolean modifyOnDeactivateOnly = false;

    /**
     * Constructor.
     * 
     * @param text
     * @param commandProvider
     */
    public BaseStyledTextFieldHandler(StyledText text,
            CommandProvider commandProvider) {
        this.text = text;
        this.commandProvider = commandProvider;
        register(this.text);
    }

    /**
     * Register listeners to the <code>Text</code> control.
     * 
     * @param text
     */
    protected void register(StyledText text) {
        text.addListener(SWT.Modify, this);
        text.addListener(SWT.Verify, this);
        text.addListener(SWT.Deactivate, this);
    }

    /**
     * When true this will prevent timed modifies for getting commands (you will
     * only get request to get command to change model when user deactivates
     * (moves away from) text box
     * <p>
     * <b>When Using on deactivate only you MUST check for change when requested
     * for commands - you may get asked for command even when the field has not
     * changed.
     * 
     * @param modifyOnDeactivateOnly
     */
    public void setModifyOnDeactivateOnly(boolean modifyOnDeactivateOnly) {
        this.modifyOnDeactivateOnly = modifyOnDeactivateOnly;
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
            case SWT.Verify:
                verify(event);
                break;

            case SWT.Deactivate:
                doDeactivate(event);
                break;
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
            // added fix for MR 35394
            EditingDomain ed = getEditingDomain();
            if (ed == null) {
                return;
            }
            // added fix for MR 35394
            Command cmd = createCommand();

            if (cmd != null) {
                if (cmd.canExecute()) {
                    //
                    // SID 20/09/2007 - I have removed the calls to ignore model
                    // events when running the command for a modify on
                    // deactivate only text handler.
                    //
                    // I think that these were in place (in normal 'timed' run
                    // of command to stop the value being reset on the field (as
                    // this would reset the cursor etc).
                    //
                    // The timed update method works fine where the section is
                    // already listening for changes to the EObject under which
                    // the given text attribute is (because it will receive
                    // notification).
                    //
                    // However, if the command to set the text creates a new
                    // sub-element under an EObject being listened to for
                    // notification and places the text in the sub-element
                    // attribute, then the section will not get notified of the
                    // addition of the new sub-element because we used to be
                    // ignoring events around its creation.
                    //
                    // It should be safe not to ignore evernts when running
                    // command on text control deactivate because the control
                    // will be losing focus anyway and therefore we shouldn't
                    // get nasty effects of the cursor changing underneath the
                    // users typing.

                    //
                    // So should you be in a situation where you are changing
                    // text in a sub-element of section main input element and
                    // the sub-element may be created in order to add the text
                    // to it - such as DataObject State or Description text -
                    // THEN USE manageControlUpdateOnDeactivate() AND YOU WILL
                    // receive proper notification when user deactivates
                    // control.

                    // ignoreModelEvents(true);
                    ed.getCommandStack().execute(cmd);
                    // ignoreModelEvents(false);
                }
            }
        }
        if (shouldRefresh) {
            commandProvider.refresh();
            shouldRefresh = false;
        }
    }

    /**
     * Get the editing domain.
     * 
     * @return
     */
    protected abstract EditingDomain getEditingDomain();

    /**
     * Called in response to a verify event.
     * 
     * @param event
     */
    protected abstract void verify(Event event);

    /**
     * Called in response to a modify event.
     * 
     * @param event
     */
    protected void doModify(Event event) {
        Command cmd = createCommand();

        if (cmd != null) {
            if (cmd.canExecute()) {
                if (delayedCommand != null) {
                    delayedCommand.clearCommand();
                }
                EditingDomain ed = getEditingDomain();
                delayedCommand = new DelayedCommand(ed);
                delayedCommand.setCommand(cmd);
                shouldRefresh = false;
                text.getDisplay().timerExec(300, delayedCommand);
            } else {
                shouldRefresh = true;
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
     * @return the commandProvider
     */
    public CommandProvider getCommandProvider() {
        return commandProvider;
    }
}
