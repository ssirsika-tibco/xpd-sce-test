/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.Collection;

import org.eclipse.core.resources.IFile;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.customer.api.test.iprocess.amxbpm.testcontributions.IpmBpm10_LifeCycleListenerTest_DummyListener;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.iprocess.amxbpm.converter.contributions.DelayedReleaseAndEAIWithdrawContribution;
import com.tibco.xpd.iprocess.amxbpm.converter.contributions.DynamicSubProcedureStepsContribution;
import com.tibco.xpd.iprocess.amxbpm.converter.contributions.PublicStartEventStepConversionContribution;
import com.tibco.xpd.iprocess.amxbpm.converter.contributions.StandaloneInlineScriptConversionContribution;
import com.tibco.xpd.xpdl2.Package;

/**
 * JUnit to protect iProcess to BPM conversion LifeCycle listener extension
 * point.
 * 
 * @author sajain
 * @since Jun 10, 2014
 */
public class IpmBpm10_LifeCycleListenerTest extends AbstractIProcessConversionTest {

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
        IpmBpm10_LifeCycleListenerTest_DummyListener dummy =
                new IpmBpm10_LifeCycleListenerTest_DummyListener(false);

        /*
         * Check importAndMigrationComplete() method parameters.
         */
        checkImportAndMigrationCompleteParameters(dummy.getInitialFileSetAttr());

        /*
         * Check packageSeparationComplete() method parameters.
         */
        checkPackageSeparationCompleteParameters(dummy
                .getStudioIProcessPackagesAttr());

        /*
         * Check conversionExtensionsComplete() method parameters.
         */
        checkConversionExtensionsCompleteParameters(dummy.getStudioBPMPackagesAttr(),
                dummy.getExecutedExtensionsAttr());

        /*
         * Check conversionComplete() method parameters.
         */
        checkConversionCompleteParameters(dummy.getFinalFileSetAttr(),
                dummy.getFinalStudioBPMPackagesAttr(),
                dummy.getIgnoredDuplicatePackagesAttr());

    }

    /**
     * Check importAndMigrationComplete() method parameters.
     * 
     * @param initialFileSet
     */
    private void checkImportAndMigrationCompleteParameters(
            Collection<IFile> initialFileSet) {

        /*
         * There should be just one file in the initial file set and that should
         * be actxpdl4.xpdl.
         */
        assertEquals(1, initialFileSet.size());

        for (IFile eachInitialFile : initialFileSet) {
            assertEquals("actxpdl4.xpdl", eachInitialFile.getName()); //$NON-NLS-1$
        }
    }

    /**
     * Check packageSeparationComplete() method parameters.
     * 
     * @param studioIProcessPackages
     */
    private void checkPackageSeparationCompleteParameters(
            Collection<Package> studioIProcessPackages) {

        /*
         * There should be 2 packages in studio iprocess packages list: ACTXPDL4
         * and SUB3.
         */
        assertEquals(2, studioIProcessPackages.size());

        for (Package eachStudioIProcessPackage : studioIProcessPackages) {

            if (!("ACTXPDL4".equals(eachStudioIProcessPackage.getName()) || "SUB3" //$NON-NLS-1$//$NON-NLS-2$
            .equals(eachStudioIProcessPackage.getName()))) {

                fail("Studio iProcess package names are not as expected. We expect them to be either ACTXPDL4 or SUB3."); //$NON-NLS-1$

            }
        }
    }

    /**
     * Check conversionExtensionsComplete() method parameters.
     * 
     * @param studioBPMPackages
     * @param executedExtensions
     */
    private void checkConversionExtensionsCompleteParameters(
            Collection<Package> studioBPMPackages,
            Collection<AbstractIProcessToBPMContribution> executedExtensions) {

        /*
         * There should be 2 packages in studio BPM packages list: ACTXPDL4 and
         * SUB3.
         */
        assertEquals(2, studioBPMPackages.size());

        for (Package eachStudioBPMPackage : studioBPMPackages) {
            if (!("ACTXPDL4".equals(eachStudioBPMPackage.getName()) || "SUB3" //$NON-NLS-1$//$NON-NLS-2$
            .equals(eachStudioBPMPackage.getName()))) {

                fail("Studio BPM package names are not as expected. We expect them to be either ACTXPDL4 or SUB3."); //$NON-NLS-1$
            }
        }

        boolean eaiWithdrawConversionExecuted = false;
        boolean publicStartEventStepConversionExecuted = false;
        boolean dynamicSybProcedureStepsConversionExecuted = false;
        boolean standaloneInlineScriptConversionExecuted = false;

        for (AbstractIProcessToBPMContribution eachExecutedExtension : executedExtensions) {

            if (eachExecutedExtension instanceof DelayedReleaseAndEAIWithdrawContribution) {
                eaiWithdrawConversionExecuted = true;
            } else if (eachExecutedExtension instanceof PublicStartEventStepConversionContribution) {
                publicStartEventStepConversionExecuted = true;
            } else if (eachExecutedExtension instanceof DynamicSubProcedureStepsContribution) {
                dynamicSybProcedureStepsConversionExecuted = true;
            } else if (eachExecutedExtension instanceof StandaloneInlineScriptConversionContribution) {
                standaloneInlineScriptConversionExecuted = true;
            }
        }

        if (!(eaiWithdrawConversionExecuted
                && publicStartEventStepConversionExecuted
                && dynamicSybProcedureStepsConversionExecuted && standaloneInlineScriptConversionExecuted)) {
            fail("Few expected extensions weren't executed."); //$NON-NLS-1$
        }
    }

    /**
     * Check conversionComplete() method parameters.
     * 
     * @param finalFileSet
     * @param studioBPMPackages
     * @param ignoredDuplicatePackages
     */
    private void checkConversionCompleteParameters(
            Collection<IFile> finalFileSet,
            Collection<Package> studioBPMPackages,
            Collection<Package> ignoredDuplicatePackages) {

        /*
         * There should be just one file in the final file set and that should
         * be ACTXPDL4.xpdl.
         */
        assertEquals(1, finalFileSet.size());

        for (IFile eachFinalFile : finalFileSet) {
            assertEquals("ACTXPDL4.xpdl", eachFinalFile.getName()); //$NON-NLS-1$
        }

        /*
         * There should be just one package in the list of studio BPM packages
         * and that should be ACTXPDL4.
         */
        assertEquals(1, studioBPMPackages.size());

        for (Package eachStudioBPMPackage : studioBPMPackages) {
            assertEquals("ACTXPDL4", eachStudioBPMPackage.getName()); //$NON-NLS-1$
        }

        /*
         * There should be just one package in the list of ignored duplicate
         * packages and that should be SUB3.
         */
        assertEquals(1, ignoredDuplicatePackages.size());

        for (Package eachIgnoredPackage : ignoredDuplicatePackages) {
            assertEquals("SUB3", eachIgnoredPackage.getName()); //$NON-NLS-1$
        }
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] importResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkTests", //$NON-NLS-1$
                        "IpmBpm10_LifeCycleListenerTest/ImportIpmXpdls/actxpdl4.ipmxpdl") }; //$NON-NLS-1$

        return importResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        TestResourceInfo[] otherResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkTests", //$NON-NLS-1$
                        "IpmBpm10_LifeCycleListenerTest/Process Packages{processes}/SUB3.xpdl") }; //$NON-NLS-1$

        return otherResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm10_LifeCycleListenerTest"; //$NON-NLS-1$
    }

}
