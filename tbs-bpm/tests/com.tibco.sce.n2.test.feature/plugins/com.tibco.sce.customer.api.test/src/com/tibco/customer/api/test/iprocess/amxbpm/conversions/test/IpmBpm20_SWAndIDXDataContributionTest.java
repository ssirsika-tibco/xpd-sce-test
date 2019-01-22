/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * JUnit to protect "Remove SW_ and IDX_ process data" contribution.
 * 
 * @author sajain
 * @since Jun 13, 2014
 */
public class IpmBpm20_SWAndIDXDataContributionTest extends
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
     * IDX_ field text.
     */
    private static final String IDX_ = "IDX_"; //$NON-NLS-1$

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        for (IFile file : convertedXpdls) {

            if ("SWIDX.xpdl".equalsIgnoreCase(file.getName())) { //$NON-NLS-1$

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);

                EObject rootElement = wc.getRootElement();

                if (rootElement instanceof Package) {

                    Package pkg = (Package) rootElement;

                    for (Process eachProcess : pkg.getProcesses()) {

                        searchForUnsupportedSWAndIDXData(eachProcess);

                    }
                }
            }
        }

    }

    /**
     * Search for SW_ and IDX_ fields/parameters which we don't support in AMX
     * BPM and the test should fail if we find them.
     * 
     * @param eachProcess
     *            Current process.
     */
    private void searchForUnsupportedSWAndIDXData(Process eachProcess) {

        /*
         * Get all process relevant data from the process currently being
         * processed.
         */
        List<ProcessRelevantData> allProcessRelData =
                new ArrayList<ProcessRelevantData>();

        /*
         * Add all data fields.
         */
        allProcessRelData.addAll(eachProcess.getDataFields());

        /*
         * Add all formal parameters.
         */
        allProcessRelData.addAll(eachProcess.getFormalParameters());

        /*
         * Look through all process relevant data.
         */
        for (ProcessRelevantData eachProcessRelData : allProcessRelData) {

            /*
             * Remove the field/parameter if it is either SW data OR and IDX
             * data.
             */
            if (isSWDataField(eachProcessRelData)) {

                /*
                 * It is SW data, so test fails.
                 */
                fail(String
                        .format("A SW_ process data (%1$s) still exists in the process %2$s.", //$NON-NLS-1$
                                eachProcessRelData.getName(),
                                eachProcess.getName()));

            } else if (isIDXDataField(eachProcessRelData, allProcessRelData)) {

                /*
                 * It is IDX data, so test fails.
                 */
                fail(String
                        .format("An IDX_ process data (%1$s) still exists in the process %2$s.", //$NON-NLS-1$
                                eachProcessRelData.getName(),
                                eachProcess.getName()));

            }
        }
    }

    /**
     * Return <code>true</code> if the name of the specified field/parameter
     * starts with "SW_", <code>false</code> otherwise.
     * 
     * @param eachProcessRelData
     *            Field/parameter currently being processed.
     * 
     * @return <code>true</code> if the name of the specified field/parameter
     *         starts with "SW_", <code>false</code> otherwise.
     */
    private boolean isSWDataField(ProcessRelevantData eachProcessRelData) {

        if (null != eachProcessRelData.getName()) {

            return eachProcessRelData.getName().startsWith(SW_);
        }

        return false;
    }

    /**
     * Return <code>true</code> if the name of the specified field/parameter
     * starts with "IDX_", <code>false</code>> otherwise.
     * 
     * @param eachProcessRelData
     *            Field/parameter currently being processed
     * 
     * @return <code>true</code> if the name of the specified field/parameter
     *         starts with "IDX_", <code>false</code> otherwise.
     */
    private boolean isIDXDataField(ProcessRelevantData eachProcessRelData,
            List<ProcessRelevantData> allProcessRelData) {

        String dataName = eachProcessRelData.getName();

        if (null != dataName) {

            /*
             * Check if the field/parameter name starts with "IDX_".
             */
            if (dataName.startsWith(IDX_)) {

                /*
                 * If it does, then check if it ends with an 'array'
                 * field/parameter name.
                 */
                return dataNameEndsWithArrayFieldName(dataName,
                        allProcessRelData);

            }
        }

        return false;
    }

    /**
     * Return <code>true</code> if the specified field/parameter name ends with
     * an array field/parameter name, returns <code>false</code> otherwise.
     * 
     * @param dataName
     *            Name of the field/parameter currently being processed.
     * @param allProcessRelData
     *            List of all the process relevant data.
     * 
     * @return <code>true</code> if the specified field/parameter name ends with
     *         an array field/parameter name, returns <code>false</code>
     *         otherwise.
     */
    private boolean dataNameEndsWithArrayFieldName(String dataName,
            List<ProcessRelevantData> allProcessRelData) {

        /*
         * Go through all the data fields.
         */
        for (ProcessRelevantData eachProcessRelData : allProcessRelData) {

            /*
             * Check if this field is an array.
             */
            if (eachProcessRelData.isIsArray()) {

                /*
                 * If it is an array, then check if the name of the field
                 * currently being processed ends with that array field name.
                 */
                if (dataName.endsWith(eachProcessRelData.getName())) {

                    /*
                     * Return 'true' if it does.
                     */
                    return true;
                }
            }
        }

        /*
         * The field name doesn't end with any array field name, so return
         * 'false'.
         */
        return false;
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
                        "resources/ConversionTests", "IpmBpm20_SWAndIDXDataContributionTest/ImportIpmXpdls/swidx.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        return "IpmBpm20_SWAndIDXDataContributionTest"; //$NON-NLS-1$
    }

}
