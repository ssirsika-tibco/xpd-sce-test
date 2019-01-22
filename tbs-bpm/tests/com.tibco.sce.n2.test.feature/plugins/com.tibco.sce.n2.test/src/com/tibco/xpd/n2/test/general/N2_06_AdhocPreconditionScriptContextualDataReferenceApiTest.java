/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataContextReferenceResolver;
import com.tibco.xpd.core.test.util.AbstractBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Junit test that checks if the {@link ProcessDataContextReferenceResolver}
 * returns the process data correctly which is being used in the Adhoc Task
 * Precondition Scripts.
 * 
 * 
 * @author kthombar
 * @since 19-Aug-2014
 */
public class N2_06_AdhocPreconditionScriptContextualDataReferenceApiTest extends
        AbstractBaseResourceTest {

    /**
     * AdhocPreconditionScriptContextualDataReferenceApiTest
     * 
     * @throws Exception
     */
    public void testAdhocPreconditionScriptContextualDataReferenceApiTest()
            throws Exception {
        // Check all files created correctly.
        checkTestFilesCreated();

        checkAdhocPreconditionDataReference();

        return;
    }

    /**
     * 
     */
    @SuppressWarnings("nls")
    private void checkAdhocPreconditionDataReference() {
        /* get the file */
        IFile testFile =
                getTestFile("AdhocPreconditionScriptContextualDataReferenceApiTest.xpdl");

        if (testFile == null) {
            fail("The test file cannot be null");
        }

        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(testFile);

        if (workingCopy == null) {
            fail("The Working copy cannot be null");
        }

        Package pkg = (Package) workingCopy.getRootElement();

        Process process = pkg.getProcesses().get(0);

        ProcessDataContextReferenceResolver resolver =
                new ProcessDataContextReferenceResolver();

        List<String> parameterNames = new ArrayList<String>();
        parameterNames.add("Parameter");
        parameterNames.add("Parameter2");
        parameterNames.add("Parameter3");

        List<String> dataFieldNames = new ArrayList<String>();
        dataFieldNames.add("Field");
        dataFieldNames.add("Field2");
        dataFieldNames.add("Field3");

        Activity activityByName =
                Xpdl2ModelUtil.getActivityByName(process, "Adhoctaskusingdata");

        Set<ProcessRelevantData> parameterReferences =
                resolver.getDataReferences(activityByName,
                        DataReferenceContext.CONTEXT_ADHOC_PRECONDITION);
        /*
         * we expect there to be 3 parameters to be referenced by Adhoc
         * Precondition expression
         */
        assertEquals("Un-expected number of Referenced Parameters by the Adhoc Precondition Expression",
                3,
                parameterReferences.size());

        List<String> paramOrDataFieldNames =
                getParamOrDataFieldNames(parameterReferences);
        /*
         * Check if the name of the parameters are correct.
         */
        if (!paramOrDataFieldNames.containsAll(parameterNames)) {

            fail("Error, did not find expected Parameters Referenced in Adhoc Precondition Script.");
        }

        Activity activityByName2 =
                Xpdl2ModelUtil.getActivityByName(process,
                        "Adhoctaskusingdatafields");

        Set<ProcessRelevantData> dataFieldsReferernced =
                resolver.getDataReferences(activityByName2,
                        DataReferenceContext.CONTEXT_ADHOC_PRECONDITION);
        /*
         * we expect there to be 3 data fields to be referenced by Adhoc
         * Precondition expression
         */
        assertEquals("Un-expected number of Referenced Data-Fields by the Adhoc Precondition Expression",
                3,
                dataFieldsReferernced.size());

        paramOrDataFieldNames = getParamOrDataFieldNames(dataFieldsReferernced);
        /*
         * Check if the name of the data fields are correct.
         */
        if (!paramOrDataFieldNames.containsAll(dataFieldNames)) {

            fail("Error, did not find expected Parameters Referenced in Adhoc Precondition Script.");
        }
    }

    /**
     * @param parameterReferences
     * @return
     */
    private List<String> getParamOrDataFieldNames(
            Set<ProcessRelevantData> paramOrField) {

        List<String> paramOrDataFieldNames = new ArrayList<String>();

        for (ProcessRelevantData eachParamOrField : paramOrField) {
            paramOrDataFieldNames.add(eachParamOrField.getName());
        }
        return paramOrDataFieldNames;
    }

    @Override
    protected String getTestName() {
        return "N2_06_AdhocPreconditionScriptContextualDataReferenceApiTest"; //$NON-NLS-1$
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
                                "resources/AdhocPreconditionScriptContextualDataReferenceApiTest", "AdhocPreconditionScriptContextualDataReferenceApiTest/Business Objects{bom}/AdhocPreconditionScriptContextualDataReferenceApiTest.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/AdhocPreconditionScriptContextualDataReferenceApiTest", "AdhocPreconditionScriptContextualDataReferenceApiTest/Process Packages{processes}/AdhocPreconditionScriptContextualDataReferenceApiTest.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }
}
