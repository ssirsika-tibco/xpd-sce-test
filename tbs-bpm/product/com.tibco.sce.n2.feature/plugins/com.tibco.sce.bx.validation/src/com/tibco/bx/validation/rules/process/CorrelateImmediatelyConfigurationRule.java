/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation Rules for "Correlate Immediately" Configuration which is available
 * on correlating activities.
 * 
 * 
 * @author kthombar
 * @since Dec 9, 2014
 */
public class CorrelateImmediatelyConfigurationRule extends
        ProcessValidationRule {

    /**
     * Correlating Activities for the same service operation ('%1$s/%2$s') must
     * have same 'Correlate Immediately' configuration.
     */
    private static final String UNIQUE_CORRELATION_CONFIG_FOR_ACTIVITES =
            "bx.uniqueCorrelationConfigForActivityWithSameOperation.error"; //$NON-NLS-1$

    /**
     * Incoming request activity 'Correlation Timeout' cannot be used in
     * conjunction with the 'Correlate Immediately' feature.
     */
    private static final String CORRELATE_TIMEOUT_IN_CONJUNCTION_WITH_CORRELATE_IMMEDIATELY =
            "bx.correlationTimeoutUsedInConjunctionWithCorrelateImmediately.error"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        List<ActivityWebServiceReference> webSvcRefs = null;

        for (Activity activity : allActivitiesInProc) {

            if (Xpdl2ModelUtil.isCorrelatingActivity(activity)) {
                /*
                 * proceed only for correlating activities.
                 */
                if (webSvcRefs == null) {
                    /*
                     * load the indexer only once
                     */
                    webSvcRefs = getIndexedWebServiceReferences();
                }

                if (!webSvcRefs.isEmpty()) {
                    /*
                     * Check if correlating activities sharing the same
                     * operation name have the same "Correlate Immediately"
                     * configuration.
                     */
                    checkCorrelateImmediatelyConfigForSameOperation(activity,
                            webSvcRefs);
                }

                /*
                 * Check if correlating activities having
                 * "Correlate Immediately" config do not have
                 * "Correlate Timeout" defined
                 */
                checkCorrelateImmediatelyAndCorrelateTimeoutConfig(activity);
            }
        }
    }

    /**
     * Checks if correlating activities having "Correlate Immediately" config do
     * not have "Correlate Timeout" defined
     * 
     * @param activityInProcess
     *            the activity being validated
     * @param webSvcRefs
     *            the web service references.
     */
    private void checkCorrelateImmediatelyConfigForSameOperation(
            Activity activityInProcess,
            List<ActivityWebServiceReference> webSvcRefs) {

        /*
         * create the activity web service ref.
         */
        ActivityWebServiceReference activityWebSvcRef =
                ActivityWebServiceReference
                        .createActivityWebServiceReference(activityInProcess);

        if (activityWebSvcRef != null) {
            /*
             * get all the necessary attributes to compare
             */
            String wsdlNamespace = activityWebSvcRef.getWsdlNamespace();
            String portTypeNamespace = activityWebSvcRef.getPortTypeNamespace();
            String portName = activityWebSvcRef.getPortName();
            String operation = activityWebSvcRef.getOperation();

            List<Activity> correlatingActivitiesWithSameConfig =
                    new ArrayList<Activity>();

            for (ActivityWebServiceReference activityWebServiceReference : webSvcRefs) {

                Activity act = activityWebServiceReference.getActivity();

                if (act != null && !act.equals(activityInProcess)
                        && Xpdl2ModelUtil.isCorrelatingActivity(act)) {

                    String indexedWebServiceActivitiesWsdlNS =
                            activityWebServiceReference.getWsdlNamespace();
                    String indexedWebServiceActivitiesPortTypeNS =
                            activityWebServiceReference.getPortTypeNamespace();
                    String indexedWebServiceActivitiesPortName =
                            activityWebServiceReference.getPortName();
                    String indexedWebServiceActivitiesOperation =
                            activityWebServiceReference.getOperation();

                    if (checkEquals(wsdlNamespace,
                            indexedWebServiceActivitiesWsdlNS)
                            && checkEquals(portTypeNamespace,
                                    indexedWebServiceActivitiesPortTypeNS)
                            && checkEquals(portName,
                                    indexedWebServiceActivitiesPortName)
                            && checkEquals(operation,
                                    indexedWebServiceActivitiesOperation)) {
                        /*
                         * collect all the correlation activities with same
                         * operation together.
                         */
                        correlatingActivitiesWithSameConfig.add(act);
                    }
                }
            }

            /*
             * Check if correlating activities having "Correlate Immediately"
             * config do not have "Correlate Timeout" defined
             */
            checkCorrelateImmediatelyConfigForSameOperation(activityInProcess,
                    correlatingActivitiesWithSameConfig,
                    activityWebSvcRef.getWsdlFileLocation(),
                    operation);
        }
    }

    /**
     * * Check if correlating activities having "Correlate Immediately" config
     * do not have "Correlate Timeout" defined
     * 
     * @param activityInProcess
     *            the activity being validated
     * @param allCorrelatingActivitiesWithSameConfig
     *            all correlating activities with the same config
     * @param wsdlName
     *            the wsdl name
     * @param operation
     *            the operation name
     */
    private void checkCorrelateImmediatelyConfigForSameOperation(
            Activity activityInProcess,
            List<Activity> allCorrelatingActivitiesWithSameConfig,
            String wsdlName, String operation) {

        /*
         * the expected value of correlate immediately.
         */
        boolean expectedValueOfCorelateImmediately =
                isActivityConfiguredToCorrelateImmediately(activityInProcess);

        for (Activity act : allCorrelatingActivitiesWithSameConfig) {
            /*
             * the value of "Correlate Immediately" in other correlating
             * activities.
             */
            boolean activityConfiguredToCorrelateImmediately =
                    isActivityConfiguredToCorrelateImmediately(act);

            if (expectedValueOfCorelateImmediately != activityConfiguredToCorrelateImmediately) {
                /*
                 * Create issue if the values dont match.
                 */
                createCorrelateImmediatelyConfigNotUniqueIssue(activityInProcess,
                        act,
                        wsdlName,
                        operation);
            }
        }
    }

    /**
     * Create "Correlate Immediately" config not unique issue.
     * 
     * @param activityInProcess
     *            the activity being validated
     * @param activityFromIndexer
     *            the correlating activity fetched from the indexer.
     * @param wsdlName
     *            the wsdl name
     * @param operation
     *            the operation name.
     */
    private void createCorrelateImmediatelyConfigNotUniqueIssue(
            Activity activityInProcess, Activity activityFromIndexer,
            String wsdlName, String operation) {

        String processName =
                Xpdl2ModelUtil.getDisplayNameOrName(activityFromIndexer
                        .getProcess());
        String activityName =
                Xpdl2ModelUtil.getDisplayNameOrName(activityFromIndexer);

        IFile otherFile = WorkingCopyUtil.getFile(activityFromIndexer);

        List<String> messages = new ArrayList<String>();
        messages.add(wsdlName);
        messages.add(operation);
        messages.add(otherFile.getName());
        messages.add(processName);
        messages.add(activityName);

        IFile file = WorkingCopyUtil.getFile(activityInProcess);

        if (!file.equals(otherFile)) {
            /*
             * If the activity from the web service ref is in other xpdl file
             * then add a link to it.
             */
            String resURI =
                    URI.createPlatformResourceURI(otherFile.getFullPath()
                            .toPortableString(),
                            true).toString();

            addIssue(UNIQUE_CORRELATION_CONFIG_FOR_ACTIVITES,
                    activityInProcess,
                    messages,
                    Collections
                            .singletonMap(ValidationActivator.LINKED_RESOURCE,
                                    resURI));

        } else {

            addIssue(UNIQUE_CORRELATION_CONFIG_FOR_ACTIVITES,
                    activityInProcess,
                    messages);
        }
    }

    /**
     * 
     * @param firstStringToCompare
     *            the first string to compare
     * @param secondStringToCompare
     *            the second string to compare
     * @return <code>true</code> if the input strings passed are not
     *         <code>null</code> and are equal, else return <code>false</code>
     */
    public static boolean checkEquals(String firstStringToCompare,
            String secondStringToCompare) {

        if (firstStringToCompare != null) {
            return firstStringToCompare.equals(secondStringToCompare);

        } else {
            if (secondStringToCompare == null) {
                /* Both are null */
                return true;
            }
        }

        return false;
    }

    /**
     * Check if correlating activities having "Correlate Immediately" config do
     * not have "Correlate Timeout" defined, if they have then raise an error
     * marker.
     * 
     * @param activity
     *            the correlating activity to inspect.
     */
    private void checkCorrelateImmediatelyAndCorrelateTimeoutConfig(
            Activity activity) {

        if (isActivityConfiguredToCorrelateImmediately(activity)) {
            /*
             * Go ahead only if the correlating activity has correlate
             * immediately enabled.
             */
            Object correlationTimeout =
                    Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_CorrelationTimeout());

            if (correlationTimeout != null) {
                addIssue(CORRELATE_TIMEOUT_IN_CONJUNCTION_WITH_CORRELATE_IMMEDIATELY,
                        activity);
            }
        }
    }

    /**
     * 
     * 
     * @param activity
     *            the activity under inspection.
     * @return <code>true</code> if the passed correlating activity has
     *         "Correlate Immediately" enabled, else <code>false</code>
     */
    public static boolean isActivityConfiguredToCorrelateImmediately(
            Activity activity) {

        boolean isCorrelateImmediately = false;

        Event event = activity.getEvent();

        if (event != null) {
            /*
             * If passed activity is an event, then get the
             * "Correlate Immediately" info from its TriggerMessageResult.
             */
            TriggerResultMessage triggerResultMessage =
                    EventObjectUtil.getTriggerResultMessage(activity);

            if (triggerResultMessage != null) {

                isCorrelateImmediately =
                        Xpdl2ModelUtil
                                .getOtherAttributeAsBoolean(triggerResultMessage,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_CorrelateImmediately());
            }
        } else {
            /*
             * If the passed Activity is an Receive Task then get the
             * "Correlate Immediately" info from its TaskReceive xpdl element.
             */
            Implementation impl = activity.getImplementation();

            if (impl instanceof Task) {

                Task task = (Task) impl;

                TaskReceive taskReceive = task.getTaskReceive();

                if (taskReceive != null) {

                    isCorrelateImmediately =
                            Xpdl2ModelUtil
                                    .getOtherAttributeAsBoolean(taskReceive,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_CorrelateImmediately());
                }
            }
        }
        return isCorrelateImmediately;
    }

    /**
     * 
     * @return Gets a list of web service reference info else returns empty
     *         list.
     */
    public static List<ActivityWebServiceReference> getIndexedWebServiceReferences() {

        List<ActivityWebServiceReference> webSvcRefs =
                new ArrayList<ActivityWebServiceReference>();

        Map<String, String> additionalAttributes =
                new HashMap<String, String>();

        additionalAttributes
                .put(ProcessUIUtil.WEBSERVICE_REF_COLUMN_IS_API_ACTIVITY,
                        Boolean.TRUE.toString());

        IndexerItem criteria =
                new IndexerItemImpl(null,
                        ProcessUIUtil.ACTIVITY_WEBSERVICE_REF_INDEX_TYPE, null,
                        additionalAttributes);

        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
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
