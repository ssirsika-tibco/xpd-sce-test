/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validate that all web-service activities in project that reference the same
 * port-type use the same participant for the same transport type.
 * <p>
 * This is done separately for processapi activities and service invocation
 * activities.
 * 
 * @author aallway
 * @since 3.3 (22 Feb 2010)
 */
public class ParticipantForSamePortTypeRule extends PackageValidationRule {

    private static final String ISSUE_SAME_PORTTYPE_SAME_PARTIC_API =
            "bx.sameApiPortTypeRefMustHaveSameParticName"; //$NON-NLS-1$

    private static final String ISSUE_SAME_PORTTYPE_SAME_PARTIC_NONAPI =
            "bx.sameInvokePortTypeRefMustHaveSameParticName"; //$NON-NLS-1$

    /**
     * A single web service participant must be used for a given port type in
     * the same xpdl (irrespective of the transport type)
     * */
    private static final String ISSUE_SAME_XPDL_SAME_PORT_DIFF_PARTIC =
            "bx.sameXpdlSamePortDiffPartic"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com
     * .tibco.xpd.xpdl2.Package)
     */
    @Override
    public void validate(Package pkg) {

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(pkg);
        IProject project = WorkingCopyUtil.getProjectFor(pkg);
        IFile xpdlFile = WorkingCopyUtil.getFile(pkg);

        if (wc != null && project != null && xpdlFile != null) {

            /*
             * Check api activities.
             */
            boolean doApiActivities = true;
            checkActivityPortTypeReferences(pkg,
                    project,
                    xpdlFile,
                    doApiActivities);

            /*
             * Then do the same for web-service invokes.
             */
            doApiActivities = false;
            checkActivityPortTypeReferences(pkg,
                    project,
                    xpdlFile,
                    doApiActivities);

        }

        return;
    }

    /**
     * Check references to same portType and transport type pair by all
     * activities in this project use endpoint alias participants with the same
     * name.
     * 
     * @param pkg
     * @param project
     * @param xpdlFile
     * @param doApiActivities
     */
    private void checkActivityPortTypeReferences(Package pkg, IProject project,
            IFile xpdlFile, boolean doApiActivities) {

        /*
         * Get the indexed list of references to web services from activities in
         * this project.
         */
        List<ActivityWebServiceReference> webSvcRefs =
                getIndexedWebServiceReferences(project, doApiActivities);

        /*
         * Go thru each activity in THIS package.
         * 
         * If it is web service related and participant set. Check if any other
         * activities reference same port type and check that there participant
         * name is the same.
         */

        for (Process process : pkg.getProcesses()) {
            for (Activity activity : process.getActivities()) {
                checkRef(activity, webSvcRefs, xpdlFile, doApiActivities);
            }

            for (ActivitySet activitySet : process.getActivitySets()) {
                for (Activity activity : activitySet.getActivities()) {
                    checkRef(activity, webSvcRefs, xpdlFile, doApiActivities);
                }
            }
        }
    }

    /**
     * Check that if the given activity is a web-service related one (and
     * matches 'doApiActivities' status) then all other activities that use the
     * same porttype from the same wsdl namespace MUST reference a participant
     * with the same name.
     * 
     * @param activity
     * @param ref
     * @param webSvcRefs
     * @param xpdlFile
     * @param issueId
     */
    private void checkRef(Activity activity,
            List<ActivityWebServiceReference> webSvcRefs, IFile xpdlFile,
            boolean doApiActivities) {
        /*
         * Create a web svc ref for the activity so we know we're comparing on
         * an equitable basis
         */
        ActivityWebServiceReference activityWebSvcRef =
                ActivityWebServiceReference
                        .createActivityWebServiceReference(activity);
        if (activityWebSvcRef != null
                && doApiActivities == activityWebSvcRef.getIsApiActivity()) {

            /*
             * Only need to check activities with endpoint alias participant.
             */
            String particName = activityWebSvcRef.getParticipantName();
            if (isValidParticName(particName)) {

                String wsdlNamespace = activityWebSvcRef.getWsdlNamespace();
                String portName = activityWebSvcRef.getPortName();
                String portTypeName = activityWebSvcRef.getPortTypeName();
                String transportType = activityWebSvcRef.getTransportType();

                String issueId =
                        doApiActivities ? ISSUE_SAME_PORTTYPE_SAME_PARTIC_API
                                : ISSUE_SAME_PORTTYPE_SAME_PARTIC_NONAPI;

                /*
                 * Look for other activities with same namespace and port type
                 */
                for (ActivityWebServiceReference otherRef : webSvcRefs) {

                    if (wsdlNamespace.equals(otherRef.getWsdlNamespace())
                            && portNamesOrPortTypeNamesMatch(portName,
                                    portTypeName,
                                    otherRef)) {

                        String otherParticName = otherRef.getParticipantName();

                        /*
                         * Make sure it's not the same activity.
                         */
                        if (!activityWebSvcRef.getActivityUri().equals(otherRef
                                .getActivityUri())) {

                            /*
                             * Make sure it's not the same participant.
                             */
                            if (isValidParticName(otherParticName)
                                    && !particName.equals(otherParticName)) {

                                /*
                                 * For non-api activities and activities in
                                 * different XPDL's altogether, you must use the
                                 * same participant name for the same port and
                                 * transport type.
                                 */
                                if (transportType.equals(otherRef
                                        .getTransportType())) {
                                    /*
                                     * 2 different activities reference same
                                     * port type but use different named
                                     * participants.
                                     */
                                    addPortTypeSameNameParticIssue(activity,
                                            xpdlFile,
                                            issueId,
                                            otherRef);
                                }

                            }
                        }
                    }
                }
            }
        }
        return;
    }

    /**
     * @param portName
     * @param portTypeName
     * @param otherRef
     * @return
     */
    private boolean portNamesOrPortTypeNamesMatch(String portName,
            String portTypeName, ActivityWebServiceReference otherRef) {
        return (isValidAndPortNamesEqual(portName, otherRef) || isValidAndPortTypeNamesEqual(portTypeName,
                otherRef));
    }

    /**
     * @param portTypeName
     * @param otherRef
     */
    private boolean isValidAndPortTypeNamesEqual(String portTypeName,
            ActivityWebServiceReference otherRef) {
        return (null != portTypeName && null != otherRef.getPortTypeName())
                && (portTypeName.length() > 0 && otherRef.getPortTypeName()
                        .length() > 0)
                && portTypeName.equals(otherRef.getPortTypeName());
    }

    /**
     * @param portName
     * @param otherRef
     */
    private boolean isValidAndPortNamesEqual(String portName,
            ActivityWebServiceReference otherRef) {
        return (null != portName && null != otherRef.getPortName())
                && (portName.length() > 0 && otherRef.getPortName().length() > 0)
                && portName.equals(otherRef.getPortName());
    }

    /**
     * @param particName
     * @return
     */
    private boolean isValidParticName(String particName) {
        return particName != null && particName.length() > 0;
    }

    /**
     * The given activity references a portType with a different endpoint partic
     * name that the other referenced by otherRef - add an issue problem marker
     * for it.
     * 
     * @param activity
     * @param xpdlFile
     * @param issueId
     * @param otherRef
     */
    private void addPortTypeSameNameParticIssue(Activity activity,
            IFile xpdlFile, String issueId, ActivityWebServiceReference otherRef) {
        URI otherActUri = URI.createURI(otherRef.getActivityUri());
        if (otherActUri != null) {
            Object o = ProcessUIUtil.getEObjectFrom(otherActUri);
            if (o instanceof Activity) {
                Activity otherActivity = (Activity) o;

                IFile otherFile = WorkingCopyUtil.getFile(otherActivity);
                if (otherFile != null) {
                    ArrayList<String> msgParams = new ArrayList<String>();
                    msgParams.add(otherRef.getWsdlNamespace());
                    if (null != otherRef.getPortName()
                            && otherRef.getPortName().length() > 0) {
                        msgParams.add(otherRef.getPortName());
                    } else if (null != otherRef.getPortTypeName()
                            && otherRef.getPortTypeName().length() > 0) {
                        msgParams.add(otherRef.getPortTypeName());
                    }

                    String dn = Xpdl2ModelUtil.getDisplayNameOrName(activity);
                    if (dn == null || dn.length() == 0) {
                        dn = "??"; //$NON-NLS-1$
                    }
                    msgParams.add(dn);

                    msgParams.add(getActivityPath(otherFile, otherActivity));

                    /*
                     * If other activity is in a different xpdl file then we
                     * must link the 2 resources.
                     */
                    if (!xpdlFile.equals(otherFile)) {
                        String resURI =
                                URI.createPlatformResourceURI(otherFile
                                        .getFullPath().toPortableString(),
                                        true).toString();
                        addIssue(issueId,
                                activity,
                                msgParams,
                                Collections
                                        .singletonMap(ValidationActivator.LINKED_RESOURCE,
                                                resURI));

                    } else {
                        addIssue(issueId, activity, msgParams);
                    }
                }
            }
        }

        return;
    }

    /**
     * @param otherFile
     * @param otherActivity
     * @return descriptive path to activity for problem marker text purposes.
     */
    private String getActivityPath(IFile otherFile, Activity otherActivity) {
        String dn = Xpdl2ModelUtil.getDisplayNameOrName(otherActivity);
        if (dn == null || dn.length() == 0) {
            dn = "??"; //$NON-NLS-1$
        }

        return otherFile.getName()
                + "/" //$NON-NLS-1$
                + Xpdl2ModelUtil.getDisplayNameOrName(otherActivity
                        .getProcess()) + "/" //$NON-NLS-1$
                + dn;
    }

    /**
     * @param project
     * @param ignorePackagePath
     * 
     * @return List of web service reference info.
     */
    private List<ActivityWebServiceReference> getIndexedWebServiceReferences(
            IProject project, Boolean apiActivities) {
        List<ActivityWebServiceReference> webSvcRefs =
                new ArrayList<ActivityWebServiceReference>();

        Map<String, String> additionalAttributes =
                new HashMap<String, String>();

        additionalAttributes.put(IndexerServiceImpl.ATTRIBUTE_PROJECT, project
                .getName());
        additionalAttributes
                .put(ProcessUIUtil.WEBSERVICE_REF_COLUMN_IS_API_ACTIVITY,
                        apiActivities.toString());

        IndexerItem criteria =
                new IndexerItemImpl(null,
                        ProcessUIUtil.ACTIVITY_WEBSERVICE_REF_INDEX_TYPE, null,
                        additionalAttributes);

        Collection<IndexerItem> result =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(ProcessUIUtil.ACTIVITY_WEBSERVICE_REF_INDEX_ID,
                                criteria);

        if (result != null && !result.isEmpty()) {
            for (IndexerItem item : result) {
                ActivityWebServiceReference ref =
                        ActivityWebServiceReference
                                .createActivityWebServiceReference(item);
                if (ref != null) {
                    webSvcRefs.add(ref);
                }
            }
        }

        return webSvcRefs;
    }

}
