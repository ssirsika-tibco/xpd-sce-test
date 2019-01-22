/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.implementer.script.precommit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;

import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil.CantAccessWSDLException;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil.DifferentPrefixesForNamespaceException;
import com.tibco.xpd.implementer.script.NamespacePrefixMapUtil.DuplicateNamespacePrefixException;
import com.tibco.xpd.xpdExtension.NamespacePrefixMap;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.AbstractProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Adds the xpdExt:NamespacePrefixMap to any web-service related activity with
 * XPath mappings.
 * 
 * @author aallway
 * @since 29 May 2012
 */
public class AddNamespacePrefixMapPreCommitContributor extends
        AbstractProcessPreCommitContributor implements
        IProcessPreCommitContributor {

    public AddNamespacePrefixMapPreCommitContributor() {
    }

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

        Set<Activity> affectedWebSvcActivities = new HashSet<Activity>();
        Set<Activity> activityWithResetImplementation = new HashSet<Activity>();

        for (ENotificationImpl notification : notifications) {
            /*
             * If this is an add of a new activity check if it's a web-service
             * related activity.
             */
            Activity affectedActivity = null;
            if (Notification.ADD == notification.getEventType()
                    && Xpdl2Package.eINSTANCE.getFlowContainer_Activities()
                            .equals(notification.getFeature())
                    && (notification.getNewValue() instanceof Activity)) {

                affectedActivity = (Activity) notification.getNewValue();

            } else if (Notification.SET == notification.getEventType()
                    && Xpdl2Package.eINSTANCE.getActivity_Implementation()
                            .equals(notification.getFeature())
                    && (notification.getNotifier() instanceof Activity)) {
                /*
                 * Web-service select always replaces the whole activity
                 * implementation element. So when that is the case we need to
                 * remove namespace prefix mapping element (and potentially
                 * re-add it if activity is still an xpath web-service.
                 */
                activityWithResetImplementation.add((Activity) notification
                        .getNotifier());

            } else {
                affectedActivity =
                        (Activity) getTypedAncestor(notification,
                                Activity.class);
            }

            if (affectedActivity != null) {
                if (NamespacePrefixMapUtil
                        .isWebServiceActivity(affectedActivity)) {
                    affectedWebSvcActivities.add(affectedActivity);
                }
            }
        }

        if (!affectedWebSvcActivities.isEmpty()) {
            CompoundCommand cmd = new CompoundCommand();

            for (Activity activity : affectedWebSvcActivities) {

                if (activityWithResetImplementation.contains(activity)) {
                    /*
                     * If this changed activity is also one that's having it's
                     * implementation changed or operation reselected then
                     * ignore it and deal with it below in next loop.
                     */
                    continue;
                }

                boolean isXPath =
                        NamespacePrefixMapUtil
                                .isXPathWebServiceActivity(activity);

                boolean isGeneratedReplyImmediateStartEvent =
                        (ReplyActivityUtil
                                .isStartMessageWithReplyImmediate(activity) && Xpdl2ModelUtil
                                .isGeneratedRequestActivity(activity));

                NamespacePrefixMap namespacePrefixMap =
                        NamespacePrefixMapUtil.getNamespacePrefixMap(activity);

                /*
                 * Don't add prefix map for generated XPath output mappings of
                 * sreply-imemdaite-with-process-id start events
                 */
                if (isXPath && !isGeneratedReplyImmediateStartEvent) {

                    if (namespacePrefixMap == null) {
                        /*
                         * All XPath web-service activities must have a
                         * namespace prefix map element.
                         */
                        try {
                            namespacePrefixMap =
                                    NamespacePrefixMapUtil
                                            .createNamespacePrefixMap(activity);

                            cmd.append(NamespacePrefixMapUtil
                                    .getSetNamespacePrefixMapCommand(event
                                            .getEditingDomain(),
                                            activity,
                                            namespacePrefixMap));
                        }
                        /*
                         * We don't need to worry about these exceptions,
                         * they're dealt with by validaiton rules.
                         */
                        catch (DuplicateNamespacePrefixException e) {
                        } catch (DifferentPrefixesForNamespaceException e) {
                        } catch (CantAccessWSDLException e) {
                        }
                    }

                } else {
                    if (namespacePrefixMap != null) {
                        /*
                         * All non-XPath web-service activities should have
                         * prefix map removed.
                         */
                        cmd.append(NamespacePrefixMapUtil
                                .getSetNamespacePrefixMapCommand(event
                                        .getEditingDomain(), activity, null));
                    }
                }

            }

            for (Activity activity : activityWithResetImplementation) {
                /*
                 * Web-service select always replaces the whole activity
                 * implementation element. So when that is the case we need to
                 * remove namespace prefix mapping element (and potentially
                 * re-add it if activity is still an xpath web-service.
                 */
                if (NamespacePrefixMapUtil.isWebServiceActivity(activity)
                        && NamespacePrefixMapUtil
                                .isXPathWebServiceActivity(activity)) {
                    /*
                     * Remove the old map and try to create new one.
                     */
                    NamespacePrefixMap namespacePrefixMap =
                            NamespacePrefixMapUtil
                                    .getNamespacePrefixMap(activity);
                    if (namespacePrefixMap != null) {
                        cmd.append(NamespacePrefixMapUtil
                                .getSetNamespacePrefixMapCommand(event
                                        .getEditingDomain(), activity, null));
                    }

                    try {
                        namespacePrefixMap =
                                NamespacePrefixMapUtil
                                        .createNamespacePrefixMap(activity);

                        cmd.append(NamespacePrefixMapUtil
                                .getSetNamespacePrefixMapCommand(event
                                        .getEditingDomain(),
                                        activity,
                                        namespacePrefixMap));
                    } /*
                       * We don't need to worry about these exceptions, they're
                       * dealt with by validaiton rules.
                       */
                    catch (DuplicateNamespacePrefixException e) {
                    } catch (DifferentPrefixesForNamespaceException e) {
                    } catch (CantAccessWSDLException e) {
                    }

                } else {
                    /*
                     * No longer an xpath mapping web-service activity, remove
                     * the prefix map if there is one.
                     */
                    NamespacePrefixMap namespacePrefixMap =
                            NamespacePrefixMapUtil
                                    .getNamespacePrefixMap(activity);
                    if (namespacePrefixMap != null) {
                        cmd.append(NamespacePrefixMapUtil
                                .getSetNamespacePrefixMapCommand(event
                                        .getEditingDomain(), activity, null));
                    }
                }
            }

            if (!cmd.isEmpty()) {
                return cmd;
            }
        }

        return null;
    }
}
