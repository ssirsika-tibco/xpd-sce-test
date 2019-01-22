/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.general;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.xml.sax.InputSource;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.n2.test.core.AbstractN2BaseResourceTest;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * RESTServiceTest - Adds REST Services to various incoming web service request
 * activities inside a package and compares with the gold package file to test
 * that the expected REST Services have been successfully generated.
 * 
 * @author agondal
 * @since 9 Jan 2013
 */
public class N2_03_RESTServiceTest extends AbstractN2BaseResourceTest {

    /**
     * testRESTServiceTest
     * 
     * @throws Exception
     */
    public void testRESTServiceTest() throws Exception {
        // Check all files created correctly.
        checkTestFilesCreated();

        StringBuilder failureLog = new StringBuilder();

        /* Get all the incoming web service request activities */

        Set<Activity> restfulActivities = new HashSet<Activity>();

        IFile testFile = getTestFile("UC12_RESTServiceForActivities.xpdl"); //$NON-NLS-1$

        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(testFile);

        if (wc != null && wc.getRootElement() instanceof Package) {

            Package pckg = (Package) wc.getRootElement();

            for (Process process : pckg.getProcesses()) {

                for (Activity activity : process.getActivities()) {

                    if (ReplyActivityUtil.isIncomingRequestActivity(activity)) {

                        restfulActivities.add(activity);
                    }
                }
            }
        }

        /* Set the REST flag for each activit to create REST service */

        EditingDomain ed = wc.getEditingDomain();

        CompoundCommand cmd = new CompoundCommand();

        for (Activity activity : restfulActivities) {

            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    activity,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_PublishAsRestService(),
                    Boolean.TRUE));
        }

        if (cmd.canExecute())
            try {
                {
                    ed.getCommandStack().execute(cmd);
                    wc.save();
                }
            } catch (IOException e) {
                fail(e.getMessage());
            }

        /*
         * Get the gold xpdl file and Compare it with the test xpdl file
         */

        IFile goldFile = getTestFile("UC12_RESTServiceForActivitiesGold.xpdl"); //$NON-NLS-1$

        failureLog.append(compareGoldAndTest(goldFile, testFile));

        if (failureLog.length() != 0) {
            failureLog.insert(0, "REST service test failed:\n"); //$NON-NLS-1$
            fail(failureLog.toString());
        }

        return;
    }

    @Override
    protected String getTestName() {
        return "RESTServiceTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/RESTServiceTest", "UC12_RESTServiceForActivities/Business Objects{bom}/UC12_RESTServiceForActivities.bom"), //$NON-NLS-1$ //$NON-NLS-2$                        
                        new TestResourceInfo(
                                "resources/RESTServiceTest", "UC12_RESTServiceForActivities/Process Packages{processes}/UC12_RESTServiceForActivities.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/RESTServiceTest", "UC12_RESTServiceForActivities/GoldFiles/UC12_RESTServiceForActivitiesGold.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/RESTServiceTest", "UC12_RESTServiceForActivities/Service Descriptors{wsdl}/RequestOnlyWSDLdocLiteral.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/RESTServiceTest", "UC12_RESTServiceForActivities/Service Descriptors{wsdl}/RequestOnlyWSDLrpcLiteral.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/RESTServiceTest", "UC12_RESTServiceForActivities/Service Descriptors{wsdl}/RequestReplyWSDLdocLiteral.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/RESTServiceTest", "UC12_RESTServiceForActivities/Service Descriptors{wsdl}/RequestReplyWSDLrpcLiteral.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

    /**
     * Compare the two given xpdl files for REST Services (ignores other parts
     * of the package)
     * 
     * @param goldFile
     * @param testFile
     * 
     * @return empty string buffer on success else it will contain the failure
     *         state of failed comparisons.
     * @throws IOException
     */
    private StringBuffer compareGoldAndTest(IFile goldFile, IFile testFile)
            throws IOException {

        StringBuffer errorResults = new StringBuffer();

        if (!goldFile.exists()) {
            errorResults.append(String.format("Gold file '%s' is missing.", //$NON-NLS-1$
                    goldFile.getFullPath()));
            errorResults
                    .append("-------------------------------------------------------------------\n\n"); //$NON-NLS-1$
        }

        if (!testFile.exists()) {
            errorResults.append(String.format("Test file '%s' is missing.", //$NON-NLS-1$
                    testFile.getFullPath()));
            errorResults
                    .append("-------------------------------------------------------------------\n\n"); //$NON-NLS-1$
        }

        InputStream goldStream = null;
        InputStream testStream = null;

        try {

            goldStream = goldFile.getContents();
            InputSource goldSource = new InputSource(goldStream);

            testStream = testFile.getContents();
            InputSource testSource = new InputSource(testStream);

            RESTServiceXmlDiff xpdlDiff =
                    new RESTServiceXmlDiff(goldSource, testSource);

            if (!xpdlDiff.similar()) {
                errorResults
                        .append(String
                                .format("Gold file '%1$s' and Test file '%2$s' do not match: ", //$NON-NLS-1$
                                        goldFile.getName(),
                                        testFile.getName()));
                xpdlDiff.appendMessage(errorResults);
                errorResults
                        .append("-------------------------------------------------------------------\n\n"); //$NON-NLS-1$

            }

        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            if (goldStream != null) {
                goldStream.close();
            }
            if (testStream != null) {
                testStream.close();
            }
        }

        return errorResults;
    }

}
