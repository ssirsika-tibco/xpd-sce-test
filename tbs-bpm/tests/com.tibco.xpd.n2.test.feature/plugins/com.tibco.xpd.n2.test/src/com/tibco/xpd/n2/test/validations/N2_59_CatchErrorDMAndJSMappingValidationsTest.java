/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * JUnit test to protect the Data Mapper and JS mapping validations for
 * Catch-All, Catch Sub-Process Error and Catch WS Fault.
 * 
 * @author sajain
 * @since Mar 16, 2016
 */
public class N2_59_CatchErrorDMAndJSMappingValidationsTest extends
        AbstractN2BaseValidationTest {

    public N2_59_CatchErrorDMAndJSMappingValidationsTest() {
        super(true);
    }

    /**
     * N2_59_CatchErrorDMAndJSMappingValidationsTest
     * 
     * @throws Exception
     */
    public void testN2_59_CatchErrorDMAndJSMappingValidationsTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@resultError/@otherElements.0/@message/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Map From Error: The data types are incompatible for mapping '$ERRORCODE' to 'Error_Code'. (UC25_DataMapperForCatchAllErrorEventProcess:CatchAllErrorEventJS:Error_Code)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@resultError/@otherElements.0/@message/@dataMappings.1", //$NON-NLS-1$ 
                                "Process Manager  : Map From Error: The data types are incompatible for mapping '$ERRORDETAIL' to 'Error_Details'. (UC25_DataMapperForCatchAllErrorEventProcess:CatchAllErrorEventJS:Error_Details)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.13/@event/@resultError/@otherElements.1/@message/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Map From Error: The data types are incompatible for mapping '$ERRORCODE' to 'Error_Code'. (UC25_DataMapperForCatchAllErrorEventProcess:CatchSubProcErrorEventJS:Error_Code)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.13/@event/@resultError/@otherElements.1/@message/@dataMappings.1", //$NON-NLS-1$ 
                                "Process Manager  : Map From Error: The data types are incompatible for mapping '$ERRORDETAIL' to 'Error_Details'. (UC25_DataMapperForCatchAllErrorEventProcess:CatchSubProcErrorEventJS:Error_Details)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.15/@event/@resultError/@otherElements.1/@message/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Map From Error: The data types are incompatible for mapping 'parameters.newOperationFault' to 'Error_Code'. (UC25_DataMapperForCatchAllErrorEventProcess:CatchWSFaultEventJS:Error_Code)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@event/@resultError/@otherElements.0/@message/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Data Mapper: The data types are incompatible for mapping '$ERRORCODE' to 'Error_Code'. (UC25_DataMapperForCatchAllErrorEventProcess:CatchAllErrorEvent:Error_Code)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@event/@resultError/@otherElements.0/@message/@otherElements.0/@dataMappings.1", //$NON-NLS-1$ 
                                "Process Manager  : Data Mapper: The data types are incompatible for mapping '$ERRORDETAIL' to 'Error_Details'. (UC25_DataMapperForCatchAllErrorEventProcess:CatchAllErrorEvent:Error_Details)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.6/@event/@resultError/@otherElements.1/@message/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Map From Error: The data types are incompatible for mapping '$ERRORCODE' to 'Error_Code'. (UC25_DataMapperForCatchAllErrorEventProcess:CatchSubProcErrorEvent:Error_Code)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.6/@event/@resultError/@otherElements.1/@message/@otherElements.0/@dataMappings.1", //$NON-NLS-1$ 
                                "Process Manager  : Map From Error: The data types are incompatible for mapping '$ERRORDETAIL' to 'Error_Details'. (UC25_DataMapperForCatchAllErrorEventProcess:CatchSubProcErrorEvent:Error_Details)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.6/@event/@resultError/@otherElements.1/@message/@otherElements.0/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : Map From Error: The data types are incompatible for mapping 'Parameter' to 'Process.priority'. (UC25_DataMapperForCatchAllErrorEventProcess:CatchSubProcErrorEvent:Process_priority$)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.9/@event/@resultError/@otherElements.1/@message/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Data Mapper: The data types are incompatible for mapping 'parameters.newOperationFault' to 'Error_Code'. (UC25_DataMapperForCatchAllErrorEventProcess:CatchWSFaultEvent:Error_Code)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_1AR-gOtVEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (UC25_DataMapperForCatchAllErrorEventProcess:CatchAllErrorEventJS:Scr10)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_35UBIOtVEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (UC25_DataMapperForCatchAllErrorEventProcess:CatchAllErrorEventJS:Scr11)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_5R8dcOtUEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Scr03') are not supported (UC25_DataMapperForCatchAllErrorEventProcess:CatchSubProcErrorEvent:Scr03)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_6FdIkOtVEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: Statement  (UC25_DataMapperForCatchAllErrorEventProcess:CatchAllErrorEventJS:Scr12)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_7kVW0OtUEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (UC25_DataMapperForCatchAllErrorEventProcess:CatchSubProcErrorEvent:Scr04)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_A5Q0IOtVEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (UC25_DataMapperForCatchAllErrorEventProcess:CatchAllErrorEvent:Scr05)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_A5wt8OtWEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (UC25_DataMapperForCatchAllErrorEventProcess:CatchSubProcErrorEventJS:Scr13)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_bFGZcOtWEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (UC25_DataMapperForCatchAllErrorEventProcess:CatchWSFaultEventJS:Scr17)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_C51vUOtWEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (UC25_DataMapperForCatchAllErrorEventProcess:CatchSubProcErrorEventJS:Scr14)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_duHDEOtWEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (UC25_DataMapperForCatchAllErrorEventProcess:CatchWSFaultEventJS:Scr18)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_F3CVsOtVEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (UC25_DataMapperForCatchAllErrorEventProcess:CatchSubProcErrorEvent:Scr06)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_FcZysOtWEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (UC25_DataMapperForCatchAllErrorEventProcess:CatchSubProcErrorEventJS:Scr15)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_KXS00OtVEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Scr07') are not supported (UC25_DataMapperForCatchAllErrorEventProcess:CatchWSFaultEvent:Scr07)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_NfacUetVEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (UC25_DataMapperForCatchAllErrorEventProcess:CatchWSFaultEvent:Scr08)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_P3MMAutVEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (UC25_DataMapperForCatchAllErrorEventProcess:CatchWSFaultEvent:Scr09)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_pOu4gOtUEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Scr01') are not supported (UC25_DataMapperForCatchAllErrorEventProcess:CatchAllErrorEvent:Scr01)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_xBNWUOtUEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (UC25_DataMapperForCatchAllErrorEventProcess:CatchAllErrorEvent:Scr02)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/CatchErrorDataMapperValidationsTest/Process Packages/UC25_DataMapperForCatchAllErrorEvent.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_ZSNfgOtWEeWWUJMB9JzIzQ", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (UC25_DataMapperForCatchAllErrorEventProcess:CatchWSFaultEventJS:Scr16)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_59_CatchErrorDMAndJSMappingValidationsTest"; //$NON-NLS-1$
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
                                "resources/N259CatchErrorDMAndJSMappingValidationsTest", "CatchErrorDataMapperValidationsTest/Process Packages{processes}/UC25_DataMapperForCatchAllErrorEvent.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N259CatchErrorDMAndJSMappingValidationsTest", "CatchErrorDataMapperValidationsTest/BOM Folder{bom}/BusinessObjectModel.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N259CatchErrorDMAndJSMappingValidationsTest", "CatchErrorDataMapperValidationsTest/SD Folder{wsdl}/NewWSDLFile.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
