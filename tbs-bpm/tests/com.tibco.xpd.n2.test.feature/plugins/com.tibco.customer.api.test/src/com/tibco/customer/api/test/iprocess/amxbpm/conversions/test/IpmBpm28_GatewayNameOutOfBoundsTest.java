/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.processeditor.xpdl2.util.GatewayObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * JUnit to protect Gateway name out of bounds contribution.
 * 
 * @author sajain
 * @since Jul 17, 2014
 */
public class IpmBpm28_GatewayNameOutOfBoundsTest extends
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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm28_GatewayNameOutOfBoundsTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iProcessToAMXBPMConversion.AbstractConversionTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ConversionTests", "IpmBpm28_GatewayNameOutOfBoundsTest/ImportIpmXpdls/gatewaytest.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };
        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        for (IFile file : convertedXpdls) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);

            EObject rootElement = wc.getRootElement();

            if (rootElement instanceof Package) {

                Package pkg = (Package) rootElement;

                for (Process eachProcess : pkg.getProcesses()) {

                    Collection<Activity> allProcessActivities =
                            Xpdl2ModelUtil.getAllActivitiesInProc(eachProcess);

                    /*
                     * Process only if there's at least one activity.
                     */
                    if (allProcessActivities != null
                            && !allProcessActivities.isEmpty()) {

                        /*
                         * Handle out of bound gateway names by truncating all
                         * the characters following the 58th character.
                         */
                        checkOutOfBoundGatewayNames(allProcessActivities);
                    }
                }
            }
        }
    }

    /**
     * Look for out of bound gateway names if they exceed 58 characters.
     * 
     * @param allProcessActivities
     *            List of activities and methods in processes.
     */
    private void checkOutOfBoundGatewayNames(
            Collection<Activity> allProcessActivities) {

        /*
         * Process all activities.
         */
        for (Activity eachActivity : allProcessActivities) {

            /*
             * Check if the activity currently being processed is a gateway and
             * then only proceed.
             */
            if (null != GatewayObjectUtil.getGatewayType(eachActivity)) {

                /*
                 * Check the name of the specified activity if it's length
                 * exceeds 58.
                 */
                checkGatewayName(eachActivity);

            }
        }
    }

    /**
     * Check the name of the specified activity if it's length exceeds 58.
     * 
     * @param act
     *            Activity to be processed.
     */
    private void checkGatewayName(NamedElement act) {

        /*
         * Fetch activity name.
         */
        String name = act.getName();

        /*
         * Check if it's length exceeds 58 characters.
         */
        if (null != name && name.length() > 58) {

            /*
             * Label length exceeds 58, hence test fails.
             */
            fail(String
                    .format("Name length of gateway %s exceeds 58 characters!", //$NON-NLS-1$
                            act.getName()));
        }
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        // No other resources.
        return null;
    }
}
