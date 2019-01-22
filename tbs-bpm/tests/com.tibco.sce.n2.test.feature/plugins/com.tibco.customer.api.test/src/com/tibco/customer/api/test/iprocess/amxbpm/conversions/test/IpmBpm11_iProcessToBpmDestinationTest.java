/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Junit to test that after IPM to BPM conversion all iProcess destinations are
 * removed and BPM destination is added.
 * 
 * @author kthombar
 * @since 14-May-2014
 */
public class IpmBpm11_iProcessToBpmDestinationTest extends
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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        checkDestinations(convertedXpdls);
    }

    /**
     * @param convertedXpdls
     */
    private void checkDestinations(Collection<IFile> convertedXpdls) {

        Collection<String> bpmDestinationNames =
                GlobalDestinationHelper
                        .getGlobalDestinationNamesForId(N2Utils.N2_GLOBAL_DESTINATION_ID);
        for (IFile iFile : convertedXpdls) {

            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(iFile);

            if (workingCopy == null) {
                fail("Working copy was found to be null"); //$NON-NLS-1$
            }

            EObject rootElement = workingCopy.getRootElement();

            if (rootElement instanceof Package) {
                Package pkg = (Package) rootElement;

                EList<Process> processes = pkg.getProcesses();
                // Processes
                for (Process process : processes) {
                    EList<ExtendedAttribute> extendedAttributes =
                            process.getExtendedAttributes();
                    checkValidDestination(extendedAttributes,
                            bpmDestinationNames);
                }

                // Process interfaces
                ProcessInterfaces processInterfaces =
                        ProcessInterfaceUtil.getProcessInterfaces(pkg);

                if (processInterfaces != null) {
                    EList<ProcessInterface> processInterface =
                            processInterfaces.getProcessInterface();
                    // Populate map of originating iProcess XPDL
                    for (ProcessInterface processInterface2 : processInterface) {

                        EList<ExtendedAttribute> extendedAttributes =
                                processInterface2.getExtendedAttributes();

                        checkValidDestination(extendedAttributes,
                                bpmDestinationNames);

                    }
                }
            }
        }
    }

    /**
     * @param extendedAttributes
     * @param iprocessDestinationNames
     * @param bpmDestinationNames
     */
    private void checkValidDestination(
            EList<ExtendedAttribute> extendedAttributes,

            Collection<String> bpmDestinationNames) {

        for (ExtendedAttribute eachExtendedAttribute : extendedAttributes) {
            if (DestinationUtil.DESTINATION_ATTRIBUTE
                    .equals(eachExtendedAttribute.getName())) {

                String value = eachExtendedAttribute.getValue();

                if (value == null || value.isEmpty()) {
                    fail("The destination cannot be null or empty"); //$NON-NLS-1$
                }

                if (!bpmDestinationNames.contains(value)) {
                    fail("Unexpected destination found, after conversion the processes/interfaces can only have BPM destination"); //$NON-NLS-1$
                }
            }
        }
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/ConversionTests", //$NON-NLS-1$
                                "IpmBpm11_iProcessToBpmDestinationTest/ImportIpmXpdls/iProcessToBpmDestinationConversionTest.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpmBpm11_iProcessToBpmDestinationTest/ImportIpmXpdls/ProcIfc.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo(
                                "resources/ConversionTests", //$NON-NLS-1$
                                "IpmBpm11_iProcessToBpmDestinationTest/ImportIpmXpdls/ProcessCallingProcessInterface_ProcIfc.ipmxpdl"), }; //$NON-NLS-1$

        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm11_iProcessToBpmDestinationTest"; //$NON-NLS-1$
    }

}