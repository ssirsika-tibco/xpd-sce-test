/**
 * Xpdl2PreCommitResourceSetListener.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.xpdl2.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;

/**
 * XPDL2 transactional edit domain pre-commit listener.
 * <p>
 * This allows the process feature to support it's existing (pre-transactional
 * edit domain) PreExecutionCommandWrapper extension point with minimal (or no)
 * change).
 * <p>
 * It also supports the
 * <code>com.tibco.xpd.xpdl2.resources.processPreCommitCommandContributor</code>
 * extension point.
 * <p>
 * PreExecutionCommandWrapper used to allow contributors to wrap up a command
 * just before the command stack executed it.
 * <p>
 * With transactional edit domain we can't do that exactly, but what we can do
 * is execute some more commands AFTER the original commands are executed but
 * BEFORE the model changes are finally committed.
 * <p>
 * So what <i>I think</i> we can do is create a special command that 'fakes'
 * things that have already happened by returning the list of notifiers from the
 * original command execution as it's affected objects. The contributor can then
 * return a command that wraps this and we will return it for execution to the
 * transactional editing domain.
 * <p>
 * Yukk! Fingers crossed!
 * 
 * 
 * @author aallway
 * 
 */
public class Xpdl2PreCommitResourceSetListener extends ResourceSetListenerImpl {
    private int previousRootTransHashCode = 0;

    // This is JUST for logging purposes and checking that we haven't messed
    // things up too much.
    private int reentrancyCount = 0;

    private boolean logging = false;

    @Override
    public boolean isPrecommitOnly() {
        return true;
    }

    @Override
    public boolean isAggregatePrecommitListener() {
        return true; // Call only once for the root transaction
    }

    @Override
    public Command transactionAboutToCommit(ResourceSetChangeEvent event)
            throws RollbackException {
        CompoundCommand retCmd = null;

        //
        // First of all, we must ensure that we do not return commands again on
        // re-entry.
        // This is JUST IN CASE, one of the preExecutionCommandWRappers keeps
        // returning commands to change the same object when the object changes.
        //
        // To do this we get and compare the hashCode of the previous
        // transaction (via it's hashCode as we do not want to keep a reference
        // to the actual root transaction.
        //
        int rootId = getRootTransactionId(event);

        if (logging) {
            System.out
                    .println("==> " + getClass().getSimpleName() //$NON-NLS-1$
                            + ".transactionAboutToCommit(Transaction=" + event.getTransaction().hashCode() //$NON-NLS-1$
                            + ", RootTransaction=" + rootId + ")"); //$NON-NLS-1$//$NON-NLS-2$
        }

        if (rootId == previousRootTransHashCode) {
            if (reentrancyCount > 20) {
                Xpdl2ResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(".transactionAboutToCommit():\n   Has Prevented reentrancy for same root trans(" + rootId + "): MORE than 20 times!\n    Possible problem with mechanism (root transaction not changing for different operations?)"); //$NON-NLS-1$ //$NON-NLS-2$
            }

            if (logging) {
                System.out.println("<== Ignoring reentrancy (attempt " //$NON-NLS-1$
                        + reentrancyCount
                        + ") for same root transaction as previous commit."); //$NON-NLS-1$
            }

            reentrancyCount++;

        } else {
            previousRootTransHashCode = rootId;
            reentrancyCount = 0;
        }

        //
        // Get list of notifier EObjects from our model.
        //
        List<ENotificationImpl> applicableNotifications =
                new ArrayList<ENotificationImpl>();

        HashMap<Xpdl2WorkingCopyImpl, Set<EObject>> workingCopyNotifierMap =
                getApplicableNotifiers(event, applicableNotifications);

        if (workingCopyNotifierMap != null && workingCopyNotifierMap.size() > 0) {
            //
            // There are some changes to process models.
            //
            retCmd = new CompoundCommand();

            //
            // Support the new v3.1 processPreCommitCommandContributor extension
            // point
            //
            handlePreCommitCommandContributors(event,
                    retCmd,
                    applicableNotifications);

            if (reentrancyCount == 0) {
                //
                // Support the v3.0 (pre-transactional edit domain)
                // PreExecutionCommandWrapper extension point with minimal (or
                // no)
                // change).
                handlePreExecCmdWrappers(event, retCmd, workingCopyNotifierMap);

                if (retCmd.isEmpty()) {
                    retCmd = null;
                }
            }
        }

        if (logging) {
            System.out
                    .println("<== " + getClass().getSimpleName() //$NON-NLS-1$
                            + ".transactionAboutToCommit(Transaction=" + event.getTransaction().hashCode() //$NON-NLS-1$
                            + ", RootTransaction=" + rootId + ")"); //$NON-NLS-1$//$NON-NLS-2$
        }

        return retCmd;
    }

    /**
     * Support the new v3.1 processPreCommitCommandContributor extension point
     * 
     * @param event
     * @param retCmd
     * @param applicableNotifications
     */
    private void handlePreCommitCommandContributors(
            ResourceSetChangeEvent event, CompoundCommand retCmd,
            List<ENotificationImpl> applicableNotifications)
            throws RollbackException {
        //
        // Get the list of contributors (helper caches it for us so should be
        // nice and quick.
        //
        List<IProcessPreCommitContributor> contributions =
                ProcessPreCommitCommandHelper.getDefault().getContributions();

        for (IProcessPreCommitContributor contribution : contributions) {
            if (reentrancyCount > 0) {
                // If we are reacting to notifications caused by commands
                // previously contributed to the current transaction via this
                // class then only allow it if the contributor is specifically
                // designed to do so.,
                if (!(contribution instanceof AbstractProcessPreCommitContributor)
                        || !((AbstractProcessPreCommitContributor) contribution)
                                .allowContributionRecursion(event,
                                        applicableNotifications)) {
                    continue;
                }
            }

            Command cmd =
                    contribution.contributeCommand(event,
                            applicableNotifications);
            if (cmd != null && cmd.canExecute()) {
                retCmd.append(cmd);
            } else if (null != cmd && !cmd.canExecute()) {
                /*
                 * bharti: if any command is not executable and is contained in
                 * the command list then all the commands in the list will be
                 * rolled back when they are executed in the same transaction.
                 * so we log the warning message if we find any such
                 * unexecutable command
                 */
                Logger logger = Xpdl2ResourcesPlugin.getDefault().getLogger();
                AssertionError e =
                        new AssertionError(
                                String.format("This command '%1$s' is not executable", //$NON-NLS-1$
                                        cmd.getLabel()));
                logger.warn(e.toString());
            }
        }

        return;
    }

    /**
     * Support the v3.0 (pre-transactionaledit domain)
     * PreExecutionCommandWrapper extension point with minimal (or no) change).
     * 
     * @param event
     * @param retCmd
     * @param workingCopyNotifierMap
     */
    private void handlePreExecCmdWrappers(ResourceSetChangeEvent event,
            CompoundCommand retCmd,
            HashMap<Xpdl2WorkingCopyImpl, Set<EObject>> workingCopyNotifierMap) {
        //
        // Get the list of contributors to preExecutionCommandWrapper
        // ext point. (Note this is quick and cached).
        //
        List<IPreCommandExecutionWrapper> cmdWrappers =
                PreExecutionCommandWrapperHelper.getDefault()
                        .getContributions();

        //
        // The old PreExecutionCOmmandWrapper ext point interface
        // requires working copy, so we will call once for each working
        // copy.
        //
        for (Entry<Xpdl2WorkingCopyImpl, Set<EObject>> entry : workingCopyNotifierMap
                .entrySet()) {
            //
            // The old PreExecutionCommandWrapper interface is designed
            // to allow the contributor to wrap up the cmd about to
            // execute to add any subsequent commands. The way this
            // (nominally) is done is that the contributor creates a
            // command that upon execution, executes the original
            // command, gets the affacted objects from it to see if
            // anything has changed that it is interested in.
            //
            // In order to support this we will create a dummy command
            // (AffectedObjectsSupportCommand) that does NOTHING on
            // execute BUT still has a list of affected objects that the
            // contributor can access.
            //
            Xpdl2WorkingCopyImpl workingCopy = entry.getKey();
            Set<EObject> notifiers = entry.getValue();

            Command affectObjectSupportCmd =
                    new AffectedObjectsSupportCommand(notifiers);

            for (IPreCommandExecutionWrapper cmdWrapper : cmdWrappers) {
                if (logging) {
                    System.out
                            .println("    " //$NON-NLS-1$
                                    + workingCopy.getName()
                                    + ": Model elements have changed - activating preExecutionCommandWrapper: " //$NON-NLS-1$
                                    + cmdWrapper.getClass().getSimpleName());
                }

                Command cmd =
                        cmdWrapper.wrapCommandBeforeExecution(workingCopy,
                                event.getEditingDomain(),
                                affectObjectSupportCmd);

                //
                // wrapCommandBeforeExecution() is supposed to return
                // new command OR original command if no changes
                // required.
                //
                // So only bother doing anything if it's different.
                //
                if (cmd != null && cmd != affectObjectSupportCmd) {
                    retCmd.append(cmd);
                }
            }
        }

        return;
    }

    /**
     * Get the root transaction's id (well hashCode really).
     * 
     * @param event
     * @return The hashCode of the root transaction for the given event.
     */
    private int getRootTransactionId(ResourceSetChangeEvent event) {
        Transaction trans = event.getTransaction();
        Transaction rootTrans = trans;

        while (trans != null) {
            rootTrans = trans;

            trans = trans.getParent();
        }

        return rootTrans.hashCode();
    }

    /**
     * Get a list of objects PER-WORKING-COPY that are applicable to our XPDL
     * model.
     * 
     * @param event
     * @param applicableNotifications
     *            This list will get fileld with the notifications that are
     *            applicable to
     * 
     * @return list of notifiers per working copy or null if nothing related to
     *         process (or reentrancy detected).
     */
    private HashMap<Xpdl2WorkingCopyImpl, Set<EObject>> getApplicableNotifiers(
            ResourceSetChangeEvent event,
            List<ENotificationImpl> applicableNotifications) {
        HashMap<Xpdl2WorkingCopyImpl, Set<EObject>> workingCopyNotifierMap =
                null;

        List<Notification> notifications = event.getNotifications();
        if (notifications != null) {

            // Go thru the notifications.

            for (Notification notification : notifications) {
                // Onlyu interested in EMF notifications for EObjects
                if (notification instanceof ENotificationImpl) {

                    Object notifier = notification.getNotifier();
                    if (notifier instanceof EObject) {

                        // Only interested in EObjects from Xpdl
                        EObject eObject = (EObject) notifier;

                        Package notifierPackage = getPackage(eObject);
                        if (notifierPackage != null) {

                            // Add to the list of applicable notifications.
                            applicableNotifications
                                    .add((ENotificationImpl) notification);

                            if (workingCopyNotifierMap == null) {
                                workingCopyNotifierMap =
                                        new HashMap<Xpdl2WorkingCopyImpl, Set<EObject>>();
                            }

                            // OK, it's a process related element (cos it's in
                            // an XPDL package.
                            //
                            // We want to build up a picture of the notifier
                            // objects that have change PER-WORKING-COPY.

                            WorkingCopy wc =
                                    WorkingCopyUtil.getWorkingCopyFor(eObject);
                            if (wc instanceof Xpdl2WorkingCopyImpl) {
                                Xpdl2WorkingCopyImpl workingCopy =
                                        (Xpdl2WorkingCopyImpl) wc;

                                Set<EObject> wcNotifiers =
                                        workingCopyNotifierMap.get(workingCopy);
                                if (wcNotifiers == null) {
                                    // Don't have a list for this working copy
                                    // yet
                                    // so create one and add it.
                                    wcNotifiers = new HashSet<EObject>();
                                    workingCopyNotifierMap.put(workingCopy,
                                            wcNotifiers);
                                }

                                // Add this notifier (changed) object to the
                                // list of changed objects for this working
                                // copy.
                                wcNotifiers.add(eObject);

                                if (notification.getEventType() == ENotificationImpl.ADD) {
                                    // When certain Add's are done we need add
                                    // the object being added to the list of
                                    // notifiers.
                                    Object newValue =
                                            notification.getNewValue();
                                    if (newValue instanceof EObject) {
                                        wcNotifiers.add((EObject) newValue);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        return workingCopyNotifierMap;
    }

    /**
     * Return the root process package of the given eObject.
     * 
     * @param eObject
     * @return The process package ancestor.
     */
    private Package getPackage(EObject eObject) {

        EObject pkg = eObject;

        while (pkg != null && !(pkg instanceof Package)) {
            pkg = pkg.eContainer();
        }

        return (Package) pkg;
    }

    /**
     * Dummy command that merely allows a PreExecutionCommandWrapper contributor
     * access to the objects that were (already) affected by the trnasaction
     * about to be committed.
     * <p>
     * Then the contributor wraps this command and when execute is called, it
     * does nothing and when getAffectedObjects() is called then it returns the
     * list of notifier objects from the original transaction.
     * 
     * @author aallway
     * 
     */
    private class AffectedObjectsSupportCommand extends AbstractCommand {
        Set<EObject> notifiers = Collections.EMPTY_SET;

        public AffectedObjectsSupportCommand(Set<EObject> notifiers) {
            this.notifiers = notifiers;
        }

        @Override
        public Collection<?> getAffectedObjects() {
            return notifiers;
        }

        @Override
        public boolean canExecute() {
            return true;
        }

        @Override
        public void execute() {
            // Do nothing on execute, we're just a dummy cmd to be wrapped.
        }

        @Override
        public void redo() {
        }

        @Override
        public void undo() {
        }

    }

}
