/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.legacy.datamapper;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.datamapper.scripts.DataMapperMappingScriptsToJavaScriptContribution;

/**
 * Process data mapper script generation test. <li><b>Note</b> that incase the
 * Process data mapper test fails and we need to update the gold script stored
 * in the activity description we can use
 * {@link DataMapperMappingScriptsToJavaScriptContribution#convertScriptForGrammar(String, String, String, com.tibco.xpd.xpdl2.Expression, String)}
 * which takes the data mapper script abd return the relavent java script.
 * 
 * 
 * @author aallway
 * @since 7 May 2015
 */
public class ProcessDataMapperScriptGenerationTests extends
        AbstractProcessDataMapperScriptGenerationTest {

    @Override
    protected String getTestName() {
        return "ProcessDataMapperScriptGenerationTests"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/LegacyDataMapperGenerationTests/ProcessDataMapperScriptGenerationTests", //$NON-NLS-1$
                                "WLF/Work List Facade{wlf}/WorkListFacade.wlf"), //$NON-NLS-1$
                        new TestResourceInfo(
                                "resources/LegacyDataMapperGenerationTests/ProcessDataMapperScriptGenerationTests", //$NON-NLS-1$
                                "ProcessDataMapper/Business Objects{bom}/BDP.bom"), //$NON-NLS-1$
                        new TestResourceInfo(
                                "resources/LegacyDataMapperGenerationTests/ProcessDataMapperScriptGenerationTests", //$NON-NLS-1$
                                "ProcessDataMapper/Process Packages{processes}/AllContexts.xpdl"), //$NON-NLS-1$
                        new TestResourceInfo(
                                "resources/LegacyDataMapperGenerationTests/ProcessDataMapperScriptGenerationTests", //$NON-NLS-1$
                                "ProcessDataMapper/Process Packages{processes}/ProcessDataMapper.xpdl"), //$NON-NLS-1$
                };

        return testResources;
    }

}
