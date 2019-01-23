/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.precommit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.AbstractProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * FixWsdlEndpointLocationPreCommitListener
 * <p>
 * When WSDL is imported from a URL, pre-v3.3 Studio would tend to place the
 * original import location URL in the
 * Activity/WebServiceOperation/EndPoint/ExternalReference regardless of the
 * other contigurtion of the task/event.
 * <p>
 * In reality the WSDL endpoint location should only be defined in the model as
 * the original import URL location if UseRemote is sewlected (with no Endpoint
 * Alias participant selected).
 * <p>
 * Currently the activity WebService code is a complete rat's nest and any
 * changes to it could have a very destabilising effect (and also it would be
 * difficult to catch every situation in which we should swap the location
 * between special folder relative file path and original import URL.
 * <p>
 * Therefore this pre-Commit listener is used to catch ALL changes to message
 * activities and validate/change the location in the endpoint to that
 * appropriate to the activity configuration.
 * <p>
 * <b>WE ALSO</b> fix the xpdl2:Service/xpdExt:IsRemote flag which should be set
 * if either the user has explicitly selected it (when has a choice to do so) or
 * the selection operation is from abstract WSDL (i.e. operation from portType
 * instead oif service port). Currently on selecting abstract operation in
 * Studio UI the flag is not always set explicitly.
 * 
 * @author aallway
 * @since 3.3 (7 Oct 2009)
 */
public class FixWsdlEndpointLocationPreCommitListener extends
        AbstractProcessPreCommitContributor {

    private static boolean loggingOn = false;

    @Override
    protected boolean allowContributionRecursion(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications) {
        return true;
    }

    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {

        CompoundCommand cmd = new CompoundCommand();
        Set<Activity> alreadyDone = new HashSet<Activity>();

        for (ENotificationImpl notification : notifications) {
            if (loggingOn) {
                outputNotfication(notification);
                log("==============================================="); //$NON-NLS-1$
            }

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

                /*
                 * See if it is an interesting activity (factory will return
                 * message command provider if acitivity is web-service related)
                 */
                ActivityMessageProvider activityMessageProvider =
                        ActivityMessageProviderFactory.INSTANCE
                                .getMessageProvider(activity);
                if (activityMessageProvider != null
                        && activityMessageProvider.isActualWebServiceActivity()) {
                    /*
                     * Check whether xpdExt:IsRemote is not set when it should
                     * be.
                     */
                    Command c =
                            getFixIsRemoteCommand(event.getEditingDomain(),
                                    activity,
                                    activityMessageProvider);
                    if (c != null) {
                        cmd.append(c);
                    }

                    /*
                     * Check that the web-service related activity has the
                     * correct values in xpdl2:EndPoint/ExternalReference and
                     * XpdExt:PortTypeOperation/ExternalReference for the rest
                     * of the config.
                     */
                    /*
                     * XPD-3637: do not correct the values in
                     * xpdl2:EndPoint/ExternalReference and
                     * XpdExt:PortTypeOperation/ExternalReference if the
                     * notifier is BusinessProcess and the notification is
                     * 'SET'; because there would be no scenario where-in we
                     * ourselves are setting the Business Process and want the
                     * listener tp fix the lovcation.
                     */
                    if (!(Notification.SET == notification.getEventType() && notification
                            .getNotifier() instanceof BusinessProcess)) {
                        c =
                                getFixLocationCommand(event.getEditingDomain(),
                                        activity,
                                        activityMessageProvider);
                    }
                    if (c != null) {
                        cmd.append(c);
                    }
                }
            }

            // log("===============================================");

        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Check the xpdl2:Service/xpdExt:IsRemote value.
     * 
     * @param editingDomain
     * @param activity
     * @param activityMessageProvider
     * 
     * @return Command to change IsRemote attribute to correct value else null
     *         if already correct.
     */
    private Command getFixIsRemoteCommand(
            TransactionalEditingDomain editingDomain, Activity activity,
            ActivityMessageProvider activityMessageProvider) {
        Command cmd = null;

        /*
         * Do not bother with API reply activities (there is another post commit
         * listener that will copy any changes we make to receive message api
         * activities to the reply activities and we do not want to interfere
         * with that.
         * 
         * Same goes for activities that are generating their own wsdl
         * operations and content WebServiceOperation content.
         */
        if (!ReplyActivityUtil.isReplyActivity(activity)
                && !Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {

            WebServiceOperation wso =
                    activityMessageProvider.getWebServiceOperation(activity);
            if (wso != null) {

                Service ws = wso.getService();
                if (ws != null) {

                    PortTypeOperation pto =
                            activityMessageProvider
                                    .getPortTypeOperation(activity);
                    if (pto != null) {
                        String portTypeOperationName = pto.getOperationName();

                        if (portTypeOperationName != null
                                && portTypeOperationName.length() != 0) {

                            boolean shouldBeRemote = false;

                            String serviceName = ws.getServiceName();
                            if (serviceName == null
                                    || serviceName.length() == 0) {
                                shouldBeRemote = true;
                            }

                            boolean isRemote =
                                    Xpdl2ModelUtil
                                            .getOtherAttributeAsBoolean(ws,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_IsRemote());

                            if (shouldBeRemote && isRemote != shouldBeRemote) {
                                System.out
                                        .println("  ** IsRemote should be true for abstract operation incorrect value - changing to " //$NON-NLS-1$
                                                + shouldBeRemote);
                                cmd =
                                        Xpdl2ModelUtil
                                                .getSetOtherAttributeCommand(editingDomain,
                                                        ws,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_IsRemote(),
                                                        shouldBeRemote);
                            }
                        }
                    }
                }
            }
        }

        return cmd;
    }

    /**
     * Get command to fix the end point location of the WSDL if necessary.
     * <p>
     * We must be very careful not to change it unnecessarily else we will
     * recurs forever.
     * 
     * @param editingDomain
     * 
     * @param activity
     * @param activityMessageProvider
     * 
     * @return command or null if no fix required.
     */
    public static Command getFixLocationCommand(EditingDomain editingDomain,
            Activity activity, ActivityMessageProvider activityMessageProvider) {
        CompoundCommand cmd = null;

        /*
         * Do not bother with API reply activities (there is another post commit
         * listener that will copy any changes we make to receive message api
         * activities to the reply activities and we do not want to interfere
         * with that.
         * 
         * Same goes for activities that are generating their own wsdl
         * operations and content WebServiceOperation content.
         */
        if (!ReplyActivityUtil.isReplyActivity(activity)
                && !Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {

            WebServiceOperation wso =
                    activityMessageProvider.getWebServiceOperation(activity);
            if (wso != null) {

                Service ws = wso.getService();
                if (ws != null) {

                    PortTypeOperation pto =
                            activityMessageProvider
                                    .getPortTypeOperation(activity);

                    /*
                     * Get the value that should be in the external reference
                     * location.
                     */
                    String correctLocation =
                            getCorrectLocation(activity, wso, ws, pto);
                    if (correctLocation != null) {

                        /*
                         * Get the actual values
                         */
                        ExternalReference wsdlLocationRef =
                                getServiceEndPointLocation(ws);
                        ExternalReference ptoLocationRef =
                                getServiceEndPointLocation(activityMessageProvider
                                        .getPortTypeOperation(activity));

                        /*
                         * If actual location values are defined and don't match
                         * correctLocation then fix them.
                         */
                        if (wsdlLocationRef != null) {
                            if (!correctLocation.equals(wsdlLocationRef
                                    .getLocation())) {
                                log("  **" //$NON-NLS-1$
                                        + "FixWsdlEndpointLocationPreCommitListener" //$NON-NLS-1$
                                        + ": Updating EndPoint/ExternalReference/Location (from '" //$NON-NLS-1$
                                        + wsdlLocationRef.getLocation()
                                        + "' to '" + correctLocation + "'."); //$NON-NLS-1$ //$NON-NLS-2$

                                if (cmd == null) {
                                    cmd = new CompoundCommand();
                                }

                                cmd.append(SetCommand
                                        .create(editingDomain,
                                                wsdlLocationRef,
                                                Xpdl2Package.eINSTANCE
                                                        .getExternalReference_Location(),
                                                correctLocation));
                            }
                        }

                        if (ptoLocationRef != null) {
                            if (!correctLocation.equals(ptoLocationRef
                                    .getLocation())) {

                                log("  **" //$NON-NLS-1$
                                        + "FixWsdlEndpointLocationPreCommitListener" //$NON-NLS-1$
                                        + ": Updating EndPoint/ExternalReference/Location (from '" //$NON-NLS-1$
                                        + ptoLocationRef.getLocation()
                                        + "' to '" + correctLocation + "'."); //$NON-NLS-1$ //$NON-NLS-2$

                                if (cmd == null) {
                                    cmd = new CompoundCommand();
                                }

                                cmd.append(SetCommand
                                        .create(editingDomain,
                                                ptoLocationRef,
                                                Xpdl2Package.eINSTANCE
                                                        .getExternalReference_Location(),
                                                correctLocation));
                            }
                        }
                    }
                }
            }
        }

        return cmd;
    }

    /**
     * @param wso
     * @param activity
     * @param ws
     * 
     * @return Get the correct value for the externalReference/Location for the
     *         given webservice's config (null if cannot be ascertained).
     */
    private static String getCorrectLocation(Activity activity,
            WebServiceOperation wso, Service ws, PortTypeOperation pto) {
        String correctLocation = null;

        IProject project = WorkingCopyUtil.getProjectFor(activity);
        if (project != null) {
            String filePath = Xpdl2WsdlUtil.getLocation(ws);
            String serviceName = ws.getServiceName();
            String operationName = wso.getOperationName();
            String portName = ws.getPortName();
            String portTypeName = null;
            String portTypeOperationName = null;

            if (pto != null) {
                portTypeName = pto.getPortTypeName();
                portTypeOperationName = pto.getOperationName();

                if (filePath == null) {
                    filePath = Xpdl2WsdlUtil.getLocation(pto);
                }
            }

            /*
             * The only time that the location should be original import URL is
             * when the endpoint is set to UseRemote and there is no Alias
             * participant set.
             */
            boolean isRemote =
                    Xpdl2ModelUtil.getOtherAttributeAsBoolean(ws,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_IsRemote());

            WsdlServiceKey key =
                    new WsdlServiceKey(serviceName, portName, operationName,
                            portTypeName, portTypeOperationName, filePath);

            if (isRemote) {
                String endPointAlias =
                        (String) Xpdl2ModelUtil.getOtherAttribute(wso,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Alias());
                if (endPointAlias == null || endPointAlias.length() == 0) {
                    /*
                     * When remote is selected but no endpoint alias, then try
                     * to get original (import) URL location. This will be
                     * available if the WSDL was originally imported from URL
                     */

                    IPath relativePath =
                            WsdlIndexerUtil.getRelativePath(project,
                                    key,
                                    true,
                                    true);
                    if (relativePath != null) {
                        correctLocation = relativePath.toPortableString();
                    }
                }
            }

            /*
             * If correct location is not the original import URL (or that was
             * not available) then put the special folder relative path in).
             */
            if (correctLocation == null || correctLocation.length() == 0) {
                IPath p =
                        WsdlIndexerUtil.getRelativePath(project,
                                key,
                                true,
                                true);
                if (p != null) {
                    correctLocation = p.toString();
                }
            }
        }

        return correctLocation;
    }

    /**
     * This static utility can be used to get exactly the same impression of
     * correct value for a WSDL ExternalReference location as the this
     * pre-Commit fixer.
     * 
     * @param activity
     * 
     * @return Get the correct value for the externalReference/Location for the
     *         given webservice's config (null if cannot be ascertained).
     */
    public static String getCorrectWsdlExternalReferenceLocation(
            Activity activity) {
        if (activity != null) {
            ActivityMessageProvider activityMessageProvider =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);
            if (activityMessageProvider != null
                    && activityMessageProvider.isActualWebServiceActivity()) {
                WebServiceOperation wso =

                activityMessageProvider.getWebServiceOperation(activity);
                if (wso != null) {

                    Service ws = wso.getService();
                    if (ws != null) {

                        PortTypeOperation pto =
                                activityMessageProvider
                                        .getPortTypeOperation(activity);

                        /*
                         * Get the value that should be in the external
                         * reference location.
                         */
                        return getCorrectLocation(activity, wso, ws, pto);
                    }
                }
            }
        }

        return null;
    }

    /**
     * @param ws
     * @return The external reference location from
     *         Service/EndPoint/ExternalReference.
     */
    private static ExternalReference getServiceEndPointLocation(Service ws) {
        if (ws != null) {
            EndPoint endPoint = ws.getEndPoint();
            if (endPoint != null) {
                ExternalReference externalReference =
                        endPoint.getExternalReference();
                if (externalReference != null) {
                    return externalReference;
                }
            }
        }

        return null;
    }

    /**
     * @param ws
     * @return The external reference location from
     *         Activity/xpdExt:PortTypeOperation/ExternalReference.
     */
    private static ExternalReference getServiceEndPointLocation(
            PortTypeOperation portTypeOperation) {

        if (portTypeOperation != null) {
            ExternalReference externalReference =
                    portTypeOperation.getExternalReference();

            if (externalReference != null) {
                return externalReference;
            }
        }

        return null;
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

    private static void log(String msg) {
        if (loggingOn) {
            System.out.println(msg);
        }
    }
}
