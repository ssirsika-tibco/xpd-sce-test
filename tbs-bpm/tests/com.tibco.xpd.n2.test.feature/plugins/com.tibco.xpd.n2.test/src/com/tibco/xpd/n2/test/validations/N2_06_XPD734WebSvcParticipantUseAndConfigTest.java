/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * XPD734WebSvcParticipantUseAndConfigTest
 * <p>
 * XPD734WebSvcParticipantUseAndConfigTest - Test selected validations are
 * correctly raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 * 
 * This is for that the various validations (see XPD-734 description item (4)
 * for the use of web service participants in web service activities and their
 * configuration
 * 
 * @author
 * @since
 */
public class N2_06_XPD734WebSvcParticipantUseAndConfigTest extends
        AbstractN2BaseValidationTest {

    public N2_06_XPD734WebSvcParticipantUseAndConfigTest() {
        super(true);
    }

    /**
     * XPD734WebSvcParticipantUseAndConfigTest
     * 
     * @throws Exception
     */
    public void testXPD734WebSvcParticipantUseAndConfigTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/XPD734/Process Packages/XPD734_1.xpdl", //$NON-NLS-1$ 
                                "bx.webActParticSharedResMustBeWebService", //$NON-NLS-1$ 
                                "_iJp-gGM_Ed-NG5g927tRwA", //$NON-NLS-1$ 
                                "Process Manager 2.x : Participants used for web-service activities must be shared resource type Web-Service (see Shared Resource section of participant General properties). (WebPartic_MustBe_SharedResWebType)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPD734/Process Packages/XPD734_1.xpdl", //$NON-NLS-1$ 
                                "bx.sysParticSharedResMustBeProvider", //$NON-NLS-1$ 
                                "_LuNykWNHEd-KGrW_5A-o9w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Participant used for providing web service must be shared resource type web service Provider (see Shared Resource section of participant General properties). (WebPartic_ForSoapHttp_MustNotHave_Jms)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPD734/Process Packages/XPD734_1.xpdl", //$NON-NLS-1$ 
                                "bx.issueSameSharedResourceforDiffrentParticipants", //$NON-NLS-1$ 
                                "_LuNykWNHEd-KGrW_5A-o9w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Identical participant shared resource configurations must use same participant name ('WebPartic_ForSoapHttp_MustNotHave_Jms' and 'WebPartic_ForSoapJms_MustNotHaveUri'). (WebPartic_ForSoapHttp_MustNotHave_Jms)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPD734/Process Packages/XPD734_1.xpdl", //$NON-NLS-1$ 
                                "bx.sysParticSharedResMustBeProvider", //$NON-NLS-1$ 
                                "_a9O2QWNOEd-m28CR39a-8w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Participant used for providing web service must be shared resource type web service Provider (see Shared Resource section of participant General properties). (WebPartic_ForSoapJms_MustNotHaveUri)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPD734/Process Packages/XPD734_1.xpdl", //$NON-NLS-1$ 
                                "bx.issueSameSharedResourceforDiffrentParticipants", //$NON-NLS-1$ 
                                "_a9O2QWNOEd-m28CR39a-8w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Identical participant shared resource configurations must use same participant name ('WebPartic_ForSoapJms_MustNotHaveUri' and 'WebPartic_ForSoapHttp_MustNotHave_Jms'). (WebPartic_ForSoapJms_MustNotHaveUri)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPD734/Process Packages/XPD734_1.xpdl", //$NON-NLS-1$ 
                                "bx.sysParticSharedResMustBeProvider", //$NON-NLS-1$ 
                                "_FyWz8WNOEd-m28CR39a-8w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Participant used for providing web service must be shared resource type web service Provider (see Shared Resource section of participant General properties). (WebPartic_ForSoapJms_MustHaveJmsProps)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPD734/Process Packages/XPD734_1.xpdl", //$NON-NLS-1$ 
                                "bx.issueSameSharedResourceforDiffrentParticipants", //$NON-NLS-1$ 
                                "_FyWz8WNOEd-m28CR39a-8w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Identical participant shared resource configurations must use same participant name ('WebPartic_ForSoapJms_MustHaveJmsProps' and 'WebPartic_ForSoapHttp_MustHaveUri'). (WebPartic_ForSoapJms_MustHaveJmsProps)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPD734/Process Packages/XPD734_1.xpdl", //$NON-NLS-1$ 
                                "bx.sysParticSharedResMustBeProvider", //$NON-NLS-1$ 
                                "_s5-ewGNFEd-KGrW_5A-o9w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Participant used for providing web service must be shared resource type web service Provider (see Shared Resource section of participant General properties). (WebPartic_ForSoapHttp_MustHaveUri)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPD734/Process Packages/XPD734_1.xpdl", //$NON-NLS-1$ 
                                "bx.issueSameSharedResourceforDiffrentParticipants", //$NON-NLS-1$ 
                                "_s5-ewGNFEd-KGrW_5A-o9w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Identical participant shared resource configurations must use same participant name ('WebPartic_ForSoapHttp_MustHaveUri' and 'WebPartic_ForSoapJms_MustHaveJmsProps'). (WebPartic_ForSoapHttp_MustHaveUri)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/XPD734/Process Packages/XPD734_1.xpdl", //$NON-NLS-1$ 
                                "bx.sysParticSharedResMustBeProvider", //$NON-NLS-1$ 
                                "_ztpc4WNDEd-KGrW_5A-o9w", //$NON-NLS-1$ 
                                "Process Manager 2.x : Participant used for providing web service must be shared resource type web service Provider (see Shared Resource section of participant General properties). (WebPartic_SharedResName_IgnoredForApiTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "XPD734WebSvcParticipantUseAndConfigTest"; //$NON-NLS-1$
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
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Process Packages{processes}/XPD734_1.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Process Packages{processes}/XPD734_2.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/AddressLookupAppService.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/DateTimeService.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/WSDL_10.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/WSDL_11.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/WSDL_12.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/WSDL_13.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/WSDL_3.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/WSDL_4.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/WSDL_5.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/WSDL_6.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/WSDL_7.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/WSDL_8.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/XPD734 WebSvc Participant Use And Config", "XPD734/Service Descriptors{wsdl}/WSDL_9.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
