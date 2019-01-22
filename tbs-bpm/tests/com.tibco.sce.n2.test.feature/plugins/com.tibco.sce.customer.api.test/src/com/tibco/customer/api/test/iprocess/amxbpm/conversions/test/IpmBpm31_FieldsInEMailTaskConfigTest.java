/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType;
import com.tibco.xpd.implementer.nativeservices.emailservice.utilities.EmailExtUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * JUnit to protect "Fields In EMail Task Config contribution".
 * 
 * @author sajain
 * @since Jun 13, 2014
 */
public class IpmBpm31_FieldsInEMailTaskConfigTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPM_TO_BPM_IMPORT_AND_CONVERT;
    }

    /**
     * SW_ field text.
     */
    private final String SW_ = "SW_"; //$NON-NLS-1$

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        for (IFile file : convertedXpdls) {

            if ("EM2.xpdl".equalsIgnoreCase(file.getName())) { //$NON-NLS-1$

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);

                EObject rootElement = wc.getRootElement();

                if (rootElement instanceof Package) {

                    Package pkg = (Package) rootElement;

                    for (Process eachProcess : pkg.getProcesses()) {

                        checkEmailTaskConfig(eachProcess);

                    }
                }
            }
        }
    }

    /**
     * Check configurations of all EMail tasks in the specified process.
     * 
     * @param proc
     *            The process to look into.
     */
    private void checkEmailTaskConfig(Process proc) {

        /*
         * Fetch all activities from the current process.
         */
        Collection<Activity> allActivities =
                Xpdl2ModelUtil.getAllActivitiesInProc(proc);

        /*
         * Go through all activities to check if there are any EMail tasks.
         */
        for (Activity eachActivity : allActivities) {

            /*
             * Fetch activity implementation.
             */
            com.tibco.xpd.xpdl2.Implementation actImpl =
                    eachActivity.getImplementation();

            /*
             * Check if it's an instance of Task.
             */
            if (actImpl instanceof Task) {

                Task task = (Task) actImpl;

                /*
                 * Fetch TaskService.
                 */
                TaskService taskService = task.getTaskService();

                /*
                 * Check if it is a service task.
                 */
                if (taskService != null) {

                    /*
                     * If it is, then check if it's implementation type is
                     * EMail.
                     */
                    Object extensionAttribute =
                            Xpdl2ModelUtil
                                    .getOtherAttribute(taskService,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());

                    if (extensionAttribute instanceof String) {

                        String implType = extensionAttribute.toString();

                        if (TaskImplementationTypeDefinitions.EMAIL_SERVICE
                                .equalsIgnoreCase(implType)) {

                            checkForBrokenDataReferences(eachActivity,
                                    taskService);

                        }

                    }

                }
            }
        }
    }

    /**
     * Check for broken data reference for the specified email task which we
     * could have resolved (i.e., references broken by mismatch in case).
     * 
     * @param activity
     *            The activity.
     * @param taskService
     *            The enclosed service task.
     */
    private void checkForBrokenDataReferences(Activity activity,
            TaskService taskService) {

        /*
         * Get email extension.
         */
        EmailType email = EmailExtUtil.getEmailExtension(taskService);

        if (email != null) {

            /*
             * Get email definition.
             */
            DefinitionType def = email.getDefinition();

            if (def != null) {

                /*
                 * Get "From".
                 */
                FromType from = def.getFrom();

                /*
                 * Get it's configuration type.
                 */
                ConfigurationType configType = from.getConfiguration();

                /*
                 * See if the configuration type is custom because in that case
                 * only there may be a string value.
                 */
                if (ConfigurationType.CUSTOM_LITERAL.equals(configType)) {
                    /*
                     * Get the from string value.
                     */
                    String fromValue = from.getValue();

                    if (fromValue != null && !fromValue.isEmpty()) {

                        /*
                         * Check value for "From".
                         */
                        checkIfFieldReferencesAreResolved(fromValue, activity);
                    }
                }

                /*
                 * Get "To".
                 */
                String to = def.getTo();
                if (to != null) {

                    /*
                     * Check value for "To".
                     */
                    checkIfFieldReferencesAreResolved(to, activity);
                }

                /*
                 * Get "Cc".
                 */
                String cc = def.getCc();
                if (cc != null) {

                    /*
                     * Check value for "Cc".
                     */
                    checkIfFieldReferencesAreResolved(cc, activity);
                }

                /*
                 * Get "Bcc".
                 */
                String bcc = def.getBcc();
                if (bcc != null) {

                    /*
                     * Check value for "Bcc".
                     */
                    checkIfFieldReferencesAreResolved(bcc, activity);
                }

                /*
                 * Get "Reply to".
                 */
                String replyTo = def.getReplyTo();
                if (replyTo != null) {

                    /*
                     * Check value for "Reply to".
                     */
                    checkIfFieldReferencesAreResolved(replyTo, activity);

                }

                /*
                 * Get headers.
                 */
                String headers = def.getHeaders();
                if (headers != null) {

                    /*
                     * Check value for "headers".
                     */
                    checkIfFieldReferencesAreResolved(headers, activity);
                }

                /*
                 * Get priority.
                 */
                String priority = def.getPriority();
                if (priority != null) {

                    /*
                     * Check value for "priority".
                     */
                    checkIfFieldReferencesAreResolved(priority, activity);

                }

                /*
                 * Get "subject".
                 */
                String subject = def.getSubject();
                if (subject != null) {

                    /*
                     * Check value for "subject".
                     */
                    checkIfFieldReferencesAreResolved(subject, activity);

                }
            }

            /*
             * Get body.
             */
            String body = email.getBody();
            if (body != null) {

                /*
                 * Check value for "body".
                 */
                checkIfFieldReferencesAreResolved(body, activity);

            }

            /*
             * Get "attachment".
             */
            AttachmentType attachment = email.getAttachment();
            if (attachment != null) {

                /*
                 * Check value for "attachment".
                 */
                checkIfFieldReferencesAreResolved(attachment.getValue(),
                        activity);

            }
        }
    }

    /**
     * Check if all field references are maintained.
     * 
     * @param str
     *            The string to be checked.
     * @param activity
     *            The EMail task activity.
     */
    private void checkIfFieldReferencesAreResolved(String str, Activity activity) {

        if (str != null) {

            /*
             * Split into lines (don't allow split of variable name over lines).
             */
            StringTokenizer lines = new StringTokenizer(str, "\n", true); //$NON-NLS-1$

            /*
             * Go through all the lines.
             */
            while (lines.hasMoreTokens()) {

                /*
                 * Get the next line.
                 */
                String line = lines.nextToken();

                /*
                 * Go through the current line Look for % field reference
                 * characters.
                 */
                while (true) {

                    /*
                     * Get the index of first '%'.
                     */
                    int leadPercent = line.indexOf('%');

                    /*
                     * This index shouldn't be negative.
                     */
                    if (leadPercent >= 0) {

                        /*
                         * Get the rest of the line after the first '%'.
                         */
                        String remainder = line.substring(leadPercent + 1);

                        /*
                         * Get the index of the next '%' in 'remainder'.
                         */
                        int tailPercent = remainder.indexOf('%');

                        if (tailPercent > 0) {

                            /*
                             * tailPercent is greater than 0, then it means that
                             * there must be a field referenced by the name
                             * enclosed between the lead % and tail %.
                             */

                            /*
                             * Probable name of the data since it's enclosed
                             * between two '%'s.
                             */
                            String fieldRef =
                                    remainder.substring(0, tailPercent);

                            /*
                             * This fieldRef should at least be of one
                             * character.
                             */
                            if (fieldRef.length() > 0) {

                                /*
                                 * Check if field with name fieldRef exists.
                                 */
                                checkForExistenceOfTheFieldWithName(activity,
                                        fieldRef);

                            }

                        }

                        /*
                         * Get remainder of line after fieldRef.
                         */
                        line = remainder.substring(tailPercent + 1);

                    } else {

                        /*
                         * No more %'s on line, so append it on new value and
                         * BREAK the loop.
                         */

                        break;
                    }
                }
            }
        }
    }

    /**
     * Check if there's an SW field referred from the specified email task OR
     * there's a field reference broken due to mismatch in case and fail the
     * test if any of these two conditions is true.
     * 
     * @param activity
     *            Activity to look into.
     * @param fieldRefString
     *            The string by which a field is being referenced CURRENTLY.
     */
    private void checkForExistenceOfTheFieldWithName(Activity activity,
            String fieldRefString) {

        /*
         * See if it's an SW_ field because if it is, then test will fail.
         */
        if (fieldRefString.startsWith(SW_)) {

            if (fieldRefString.equals("SW_NA") || fieldRefString.equals("SW_TIME") || fieldRefString.equals("SW_DATE") || fieldRefString.equals("SW_PRONAME") || fieldRefString.equals("SW_CP_VALUE") || fieldRefString.equals("SW_CASENUM")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

                fail(String
                        .format("SW_ field (%1$s) reference was found in EMail task (%2$s) which should've been dealt with.", //$NON-NLS-1$
                                fieldRefString,
                                activity.getName()));

            }

        } else {

            /*
             * Not an SW field, so just check references.
             */

            /*
             * See if we have any data with this name (here we WILL match case.)
             */
            ProcessRelevantData data =
                    getProcRelDataByName(fieldRefString, activity);

            /*
             * See if we've found any data.
             */
            if (data == null) {

                /*
                 * Couldn't find any data with that name, so look for resembling
                 * data (i.e., data with name in DIFFERENT case).
                 */
                ProcessRelevantData resemblingData =
                        getResemblingData(fieldRefString, activity);

                /*
                 * See if we've found a data with resembling name.
                 */
                if (resemblingData != null) {

                    /*
                     * This reference should've been fixed, so test fails.
                     */

                    fail(String
                            .format("Broken field reference found in EMail task %1$s, should've been fixed from %2$s to %3$s", //$NON-NLS-1$
                                    activity.getName(),
                                    fieldRefString,
                                    resemblingData.getName()));

                }
            }
        }
    }

    /**
     * Get process relevant data by name.
     * 
     * @param dataName
     *            Name of the process relevant data.
     * @param activity
     *            The EMail task activity.
     * @return Process relevant data if that exists by the name specified,
     *         <code>null</code> otherwise.
     */
    private ProcessRelevantData getProcRelDataByName(String dataName,
            Activity activity) {

        if (dataName != null && activity != null) {

            /*
             * Get all process relevant data.
             */
            List<ProcessRelevantData> prdList =
                    ProcessDataUtil.getProcessRelevantData(activity);

            /*
             * See if there's any data.
             */
            if (prdList != null && !prdList.isEmpty()) {

                /*
                 * Go through the list.
                 */
                for (ProcessRelevantData prd : prdList) {

                    /*
                     * See if we've found a process relevant data with the
                     * specified name.
                     */
                    if (prd != null && prd.getName() != null
                            && prd.getName().equals(dataName)) {

                        /*
                         * Return it if we have.
                         */
                        return prd;
                    }
                }
            }
        }

        /*
         * Return null if we haven't found anything.
         */
        return null;
    }

    /**
     * Get the process relevant data which might have been named in a different
     * case as that of the name specified, but spells exactly the same.
     * 
     * @param dataName
     *            Name of the data.
     * @param activity
     *            The EMail task activity.
     * @return The process relevant data which might have been named in a
     *         different case as that of the name specified, but spells exactly
     *         the same. Return <code>null</code> if there's no such data.
     */
    private ProcessRelevantData getResemblingData(String dataName,
            Activity activity) {

        if (dataName != null && activity != null) {

            /*
             * Get all process relevant data.
             */
            List<ProcessRelevantData> prdList =
                    ProcessDataUtil.getProcessRelevantData(activity);

            /*
             * See if there's any data.
             */
            if (prdList != null && !prdList.isEmpty()) {

                /*
                 * Go through the list.
                 */
                for (ProcessRelevantData prd : prdList) {

                    /*
                     * See if we've found a process relevant data with the
                     * specified name, but ignore case while comparing.
                     */
                    if (prd != null && prd.getName() != null
                            && prd.getName().equalsIgnoreCase(dataName)) {

                        /*
                         * Return it if we have.
                         */
                        return prd;
                    }
                }
            }
        }

        /*
         * Return null if we haven't found anything.
         */
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ConversionTests", "IpmBpm31_FieldsInEMailTaskConfigTest/ImportIpmXpdls/em2.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };
        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        // Don't need anything here.
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm31_FieldsInEMailTaskConfigTest"; //$NON-NLS-1$
    }

}
