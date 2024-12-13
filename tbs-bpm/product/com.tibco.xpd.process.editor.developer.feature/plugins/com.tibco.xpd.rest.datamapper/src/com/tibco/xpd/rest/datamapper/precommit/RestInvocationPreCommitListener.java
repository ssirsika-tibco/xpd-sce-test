/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper.precommit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.util.RestServiceTaskUtil;
import com.tibco.xpd.rest.datamapper.RestScriptDataMapperProvider;
import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.resources.AbstractActivityPreCommitContributor;
import com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pre-commit listener to ensure that ScriptDataMapper elements are present on
 * all REST invocation activites.
 * 
 * @author nwilson
 * @since 14 May 2015
 */
public class RestInvocationPreCommitListener extends
        AbstractActivityPreCommitContributor implements
        IProcessPreCommitContributor {

    /**
     * @see com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor#contributeCommand(org.eclipse.emf.transaction.ResourceSetChangeEvent,
     *      java.util.Collection)
     * 
     * @param event
     * @param notifications
     * @return
     * @throws RollbackException
     */
    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {
        CompoundCommand cmd = new CompoundCommand();
        Set<Activity> alreadyDone = new HashSet<Activity>();

        for (ENotificationImpl notification : notifications) {
            /*
             * If the model change notification is on or from within an
             * activity, and we haven't already handled it this time round
             * (could get multiple changes to activity in same transaction), and
             * the activity is still part of whole model (i.e. hasn't just been
             * deleted within transaction)
             * 
             * Then we can proceed.
             */
            Activity activity = getActivityAncestor(notification);
            if (activity != null && !alreadyDone.contains(activity)
                    && activity.eContainer() != null) {
                alreadyDone.add(activity);

                boolean restServiceActivity =
                        RestServiceTaskUtil.isRESTServiceActivity(activity);

                if (restServiceActivity) {
                    /*
                     * Actiivty is a Rest Service Activity, check and add the
                     * Script Data mapper if not already present.
                     */
                    checkAndAddRestScriptDataMapper(activity, cmd, event);

                } else {
                    /*
                     * If activity is not a Rest Activity, then check if it is a
                     * error event on the boundary of Rest Activity.
                     */
                    if (RestServiceTaskUtil
                            .isBoundaryRestServiceErrorEvent(activity)) {
                        /*
                         * Actiivty is an Error event on the boundary of Rest
                         * Activity then check and add the Script Data mapper if
                         * not already present.
                         */
                        checkAndAddRestScriptDataMapper(activity, cmd, event);
                    } else {
                        /*
                         * If we reach here that means neither the activity is a
                         * Rest activity nor it is an error event on the
                         * boundary or Rest Activity, hence remove the Script
                         * Data Mapper if it is present.
                         */
                        removeScriptDataMapper(cmd, event, activity);
                    }

                    RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

                    OtherElementsContainer restContainer =
                            rsta.getRSOContainer(activity);

                    /*
                     * get the rest container and check if the activity is the
                     * same as the passed activity because
                     * rsta.getRSOContainer(activity) return the Rest Activity
                     * for attached error events.
                     */
                    if (restContainer != null
                            && activity
                                    .equals(Xpdl2ModelUtil
                                            .getAncestor(restContainer,
                                                    Activity.class))) {

                        RestServiceOperation rso = rsta.getRSO(restContainer);

                        if (rso != null) {

                            /*
                             * remove the RestServiceOperation as the activity
                             * is no longer a Rest Service Activity.
                             */

                            removeRestServiceOperation(cmd,
                                    rso,
                                    restContainer,
                                    event);
                        }
                    }
                }
            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Appends the passed command with a command to remove the Script Data
     * Mapper (if it exists) as the pased activity is no longer a Rest Service
     * activity.
     * 
     * @param cmd
     *            the command that should be appended with command to remove the
     *            Script Data Mapper.
     * @param event
     *            the ResourceSetChangeEvent
     * @param activity
     *            the Activity which is no longer a REst Service Actiivty.
     */
    private void removeScriptDataMapper(CompoundCommand cmd,
            ResourceSetChangeEvent event, Activity activity) {

		/* Sid ACE-8864 getIn/OutMapperContext() moved to RestServiceTaskAdapter */
		RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

        /*
         * get the in mapping script provider
         */
        RestScriptDataMapperProvider inProvider =
				new RestScriptDataMapperProvider(MappingDirection.IN, rsta.getInMapperContext(activity));
        ScriptDataMapper inScriptDataMapper =
                inProvider.getScriptDataMapper(activity);

        if (inScriptDataMapper != null) {
            /*
             * remove the in Script mapping data
             */
            cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(event
                    .getEditingDomain(),
                    inProvider.getScriptDataMapperContainer(activity),
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ScriptDataMapper(),
                    inScriptDataMapper));
        }
        /*
         * get the out mapping script provider
         */
        RestScriptDataMapperProvider outProvider =
				new RestScriptDataMapperProvider(MappingDirection.OUT, rsta.getOutMapperContext(activity));

        ScriptDataMapper outScriptDataMapper =
                outProvider.getScriptDataMapper(activity);

        if (outScriptDataMapper != null) {
            /*
             * remove the out Script mapping data
             */
            cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(event
                    .getEditingDomain(),
                    outProvider.getScriptDataMapperContainer(activity),
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ScriptDataMapper(),
                    outScriptDataMapper));
        }
    }

    /**
     * Appends the passed command with a command to remove the
     * RestServiceOperation.
     * 
     * @param cmd
     *            the cmd that should be appended with command to remove the
     *            RestServiceOperation
     * @param rso
     *            the REst Service Operation
     * @param restContainer
     *            the Container of REst Service operation
     * @param event
     *            the ResourceSetChangeEvent
     */
    private void removeRestServiceOperation(CompoundCommand cmd,
            RestServiceOperation rso, OtherElementsContainer restContainer,
            ResourceSetChangeEvent event) {

        cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(event
                .getEditingDomain(),
                restContainer,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_RestServiceOperation(),
                rso));

    }

    /**
     * Checks if the Script Data Mapper is available for the Rest Activity, if
     * not then Creates one.
     * 
     * @param activity
     * @param cmd
     * @param event
     */
    private void checkAndAddRestScriptDataMapper(Activity activity,
            CompoundCommand cmd, ResourceSetChangeEvent event) {

		/* Sid ACE-8864 getIn/OutMapperContext() moved to RestServiceTaskAdapter */
		RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

        RestScriptDataMapperProvider inProvider =
				new RestScriptDataMapperProvider(MappingDirection.IN, rsta.getInMapperContext(activity));
        RestScriptDataMapperProvider outProvider =
				new RestScriptDataMapperProvider(MappingDirection.OUT, rsta.getOutMapperContext(activity));
        TransactionalEditingDomain ed = event.getEditingDomain();
        ensureScriptDataMapperExists(inProvider, activity, ed, cmd);
        ensureScriptDataMapperExists(outProvider, activity, ed, cmd);
    }


    /**
     * Ensures that a ScriptDataMapper element exists for the given activity and
     * provider.
     * 
     * @param provider
     *            The Script Data Mapper provider.
     * @param activity
     *            The activity.
     * @param ed
     *            The editing domain.
     * @param cmd
     *            The command to append to.
     */
    private void ensureScriptDataMapperExists(
            RestScriptDataMapperProvider provider, Activity activity,
            EditingDomain ed, CompoundCommand cmd) {
        if (provider != null) {
            provider.getOrCreateScriptDataMapper(activity, ed, cmd);
        }
    }

    /**
     * @param notification
     * @return The activity ancestor of the notifier eobject in the given
     *         notification.
     */
    private Activity getActivityAncestor(ENotificationImpl notification) {
        if (notification.getNotifier() instanceof EObject) {
            EObject eo = (EObject) notification.getNotifier();

            while (eo != null) {
                if (eo instanceof Activity) {
                    return (Activity) eo;
                }
                eo = eo.eContainer();
            }
        }

        return null;
    }

}
