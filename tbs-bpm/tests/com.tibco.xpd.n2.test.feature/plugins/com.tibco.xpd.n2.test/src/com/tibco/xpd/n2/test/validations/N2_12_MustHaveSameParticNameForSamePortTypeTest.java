/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * MustHaveSameParticNameForSamePortTypeTest
 * <p>
 * MustHaveSameParticNameForSamePortTypeTest - Test selected validations are
 * correctly raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 * 
 * @author
 * @since
 */
public class N2_12_MustHaveSameParticNameForSamePortTypeTest extends
        AbstractN2BaseValidationTest {

    /**
     * MustHaveSameParticNameForSamePortTypeTest
     * 
     * @throws Exception
     */
    public void testMustHaveSameParticNameForSamePortTypeTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {
                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_a2EcBSBrEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '3 - addressLookup' & 'ProcessPackage.xpdl/ProcessPackage-Process/1 - addressLookup'). (ProcessPackageProcess:3addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 
                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_a2EcBSBrEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '3 - addressLookup' & 'ProcessPackage.xpdl/ProcessPackage-Process/2 - addressLookup'). (ProcessPackageProcess:3addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_a2EcBSBrEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '3 - addressLookup' & 'Copy of ProcessPackage.xpdl/ProcessPackage-Process/BB 1 - addressLookup'). (ProcessPackageProcess:3addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 
                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_a2EcBSBrEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '3 - addressLookup' & 'Copy of ProcessPackage.xpdl/ProcessPackage-Process/BB 2 - addressLookup'). (ProcessPackageProcess:3addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 
                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_a2EcBSBrEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '3 - addressLookup' & 'Copy of ProcessPackage.xpdl/ProcessPackage-Process/BB 3 - addressLookup'). (ProcessPackageProcess:3addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameApiPortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_nEcygiBtEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All process API services of the same port type and transport (http://www.tibco.com/bs3.0/_c31MwB1oEd-snYdvDX4uvw#ProcessPackageProcess) must have the same endpoint alias (activities 'Throw Intermediate Message Event1' & 'ProcessPackage.xpdl/ProcessPackage-Process/Receive Task2'). (ProcessPackageProcess:ThrowIntermediateMessageEvent1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameApiPortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_nEcygiBtEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All process API services of the same port type and transport (http://www.tibco.com/bs3.0/_c31MwB1oEd-snYdvDX4uvw#ProcessPackageProcess) must have the same endpoint alias (activities 'Throw Intermediate Message Event1' & 'ProcessPackage.xpdl/ProcessPackage-Process/Throw Intermediate Message Event2'). (ProcessPackageProcess:ThrowIntermediateMessageEvent1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameApiPortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_eFmBICBtEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All process API services of the same port type and transport (http://www.tibco.com/bs3.0/_c31MwB1oEd-snYdvDX4uvw#ProcessPackageProcess) must have the same endpoint alias (activities 'Receive Task' & 'ProcessPackage.xpdl/ProcessPackage-Process/Receive Task2'). (ProcessPackageProcess:ReceiveTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameApiPortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_eFmBICBtEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All process API services of the same port type and transport (http://www.tibco.com/bs3.0/_c31MwB1oEd-snYdvDX4uvw#ProcessPackageProcess) must have the same endpoint alias (activities 'Receive Task' & 'ProcessPackage.xpdl/ProcessPackage-Process/Throw Intermediate Message Event2'). (ProcessPackageProcess:ReceiveTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameApiPortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_2n75GSBuEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All process API services of the same port type and transport (http://www.tibco.com/bs3.0/_c31MwB1oEd-snYdvDX4uvw#ProcessPackageProcess) must have the same endpoint alias (activities 'Throw Intermediate Message Event2' & 'ProcessPackage.xpdl/ProcessPackage-Process/Throw Intermediate Message Event1'). (ProcessPackageProcess:ThrowIntermediateMessageEvent2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameApiPortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_2n75GSBuEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All process API services of the same port type and transport (http://www.tibco.com/bs3.0/_c31MwB1oEd-snYdvDX4uvw#ProcessPackageProcess) must have the same endpoint alias (activities 'Throw Intermediate Message Event2' & 'ProcessPackage.xpdl/ProcessPackage-Process/Receive Task'). (ProcessPackageProcess:ThrowIntermediateMessageEvent2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_-F6f0iBpEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '2 - addressLookup' & 'ProcessPackage.xpdl/ProcessPackage-Process/1 - addressLookup'). (ProcessPackageProcess:2addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_-F6f0iBpEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '2 - addressLookup' & 'ProcessPackage.xpdl/ProcessPackage-Process/3 - addressLookup'). (ProcessPackageProcess:2addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_-F6f0iBpEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '2 - addressLookup' & 'Copy of ProcessPackage.xpdl/ProcessPackage-Process/BB 1 - addressLookup'). (ProcessPackageProcess:2addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_-F6f0iBpEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '2 - addressLookup' & 'Copy of ProcessPackage.xpdl/ProcessPackage-Process/BB 2 - addressLookup'). (ProcessPackageProcess:2addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_-F6f0iBpEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '2 - addressLookup' & 'Copy of ProcessPackage.xpdl/ProcessPackage-Process/BB 3 - addressLookup'). (ProcessPackageProcess:2addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameApiPortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_2n75HCBuEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All process API services of the same port type and transport (http://www.tibco.com/bs3.0/_c31MwB1oEd-snYdvDX4uvw#ProcessPackageProcess) must have the same endpoint alias (activities 'Receive Task2' & 'ProcessPackage.xpdl/ProcessPackage-Process/Throw Intermediate Message Event1'). (ProcessPackageProcess:ReceiveTask2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameApiPortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_2n75HCBuEd-B6fy6sUlvoQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All process API services of the same port type and transport (http://www.tibco.com/bs3.0/_c31MwB1oEd-snYdvDX4uvw#ProcessPackageProcess) must have the same endpoint alias (activities 'Receive Task2' & 'ProcessPackage.xpdl/ProcessPackage-Process/Receive Task'). (ProcessPackageProcess:ReceiveTask2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_pwShAx-ZEd-JdMaOZ0tgjQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '1 - addressLookup' & 'Copy of ProcessPackage.xpdl/ProcessPackage-Process/BB 2 - addressLookup'). (ProcessPackageProcess:1addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_pwShAx-ZEd-JdMaOZ0tgjQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '1 - addressLookup' & 'ProcessPackage.xpdl/ProcessPackage-Process/3 - addressLookup'). (ProcessPackageProcess:1addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_pwShAx-ZEd-JdMaOZ0tgjQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '1 - addressLookup' & 'ProcessPackage.xpdl/ProcessPackage-Process/2 - addressLookup'). (ProcessPackageProcess:1addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        // OK
                        new ValidationsTestProblemMarkerInfo(
                                "/BPM Project/Process Packages/ProcessPackage.xpdl", //$NON-NLS-1$ 
                                "bx.sameInvokePortTypeRefMustHaveSameParticName", //$NON-NLS-1$ 
                                "_pwShAx-ZEd-JdMaOZ0tgjQ", //$NON-NLS-1$ 
                                "Process Manager 1.x : All invocations of services of the same port type and transport (http://com.tibco.addresslookup#AddressLookupAppPort) must have the same endpoint alias participant name (activities '1 - addressLookup' & 'Copy of ProcessPackage.xpdl/ProcessPackage-Process/BB 3 - addressLookup'). (ProcessPackageProcess:1addressLookup)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "MustHaveSameParticNameForSamePortTypeTest"; //$NON-NLS-1$
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
                                "resources/Must Have Same Partic Name For Same PortType Test", "BPM Project/Service Descriptors{wsdl}/AddressLookupAppService.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/Must Have Same Partic Name For Same PortType Test", "BPM Project/Service Descriptors{wsdl}/makepayment.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/Must Have Same Partic Name For Same PortType Test", "BPM Project/Business Objects{bom}/BusinessObjectModel.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/Must Have Same Partic Name For Same PortType Test", "BPM Project/Generated Business Objects{bom}/addresslookup.tibco.com.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/Must Have Same Partic Name For Same PortType Test", "BPM Project/Generated Business Objects{bom}/com.tibco.studio.makepayment.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/Must Have Same Partic Name For Same PortType Test", "BPM Project/Generated Services{wsdl}/Copy of ProcessPackage.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/Must Have Same Partic Name For Same PortType Test", "BPM Project/Generated Services{wsdl}/ProcessPackage.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/Must Have Same Partic Name For Same PortType Test", "BPM Project/Process Packages{processes}/Copy of ProcessPackage.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/Must Have Same Partic Name For Same PortType Test", "BPM Project/Process Packages{processes}/ProcessPackage.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/Must Have Same Partic Name For Same PortType Test", "BPM Project/Process Packages{processes}/ProcessPackage.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
