/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.MIOrderingType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * JUnit test to protect the Dynamic sub procedure steps contribution.
 * 
 * @author sajain
 * @since Jul 14, 2014
 */
public class IpsBpm08_DynamicSubProcStepsTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPS_TO_BPM_CONVERT;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpsBpm08_DynamicSubProcStepsTest"; //$NON-NLS-1$
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
                        "resources/ConversionTests", "IpsBpm08_DynamicSubProcStepsTest/Process Packages{processes}/testdyn.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };
        return testResources;
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
            if (rootElement instanceof com.tibco.xpd.xpdl2.Package) {
                com.tibco.xpd.xpdl2.Package pkg = (Package) rootElement;
                for (com.tibco.xpd.xpdl2.Process eachProcess : pkg
                        .getProcesses()) {
                    if (eachProcess.getName().equalsIgnoreCase("MSTMAIN")) { //$NON-NLS-1$
                        Collection<Activity> allActs =
                                Xpdl2ModelUtil
                                        .getAllActivitiesInProc(eachProcess);

                        for (Activity eachProcessActivity : allActs) {
                            TaskType taskTypeStrict =
                                    TaskObjectUtil
                                            .getTaskTypeStrict(eachProcessActivity);

                            if (TaskType.SUBPROCESS_LITERAL
                                    .equals(taskTypeStrict)) {

                                Implementation implementation =
                                        eachProcessActivity.getImplementation();

                                if (implementation instanceof SubFlow) {
                                    SubFlow subFlow = (SubFlow) implementation;

                                    if (subFlow != null) {

                                        /*
                                         * It is a dynamic sub process.
                                         */

                                        /*
                                         * 1. Check if dynamic sub-process tasks
                                         * is multi-instance-parallel.
                                         */
                                        Loop loop =
                                                eachProcessActivity.getLoop();

                                        if (null != loop) {
                                            if (!LoopType.MULTI_INSTANCE_LITERAL
                                                    .equals(loop.getLoopType())) {
                                                fail(String
                                                        .format("Dynamic subprocess task %s is not set to multi-instance!", //$NON-NLS-1$
                                                                eachProcessActivity
                                                                        .getName()));
                                            }

                                            LoopMultiInstance loopMultiInstance =
                                                    loop.getLoopMultiInstance();
                                            if (null != loopMultiInstance) {
                                                if (!MIOrderingType.PARALLEL_LITERAL
                                                        .equals(loopMultiInstance
                                                                .getMIOrdering())) {
                                                    fail(String
                                                            .format("Dynamic subprocess task %s is not set to parallel!", //$NON-NLS-1$
                                                                    eachProcessActivity
                                                                            .getName()));
                                                }
                                            }
                                        }

                                        /*
                                         * 2. Check if multi-instance ‘loop
                                         * expression’ (number of instances in
                                         * the case of parallel) is
                                         * RuntimeIdentifierArrayField.size().
                                         */
                                        Object procIdFieldAttr =
                                                Xpdl2ModelUtil
                                                        .getOtherAttribute(subFlow,
                                                                XpdExtensionPackage.eINSTANCE
                                                                        .getDocumentRoot_ProcessIdentifierField());

                                        if (procIdFieldAttr != null) {
                                            String expressionText =
                                                    procIdFieldAttr.toString()
                                                            + ".size();"; //$NON-NLS-1$

                                            if (!expressionText
                                                    .equals(eachProcessActivity
                                                            .getLoop()
                                                            .getLoopMultiInstance()
                                                            .getMICondition()
                                                            .getText())) {
                                                fail(String
                                                        .format("Multi-instance loop expression in dynamic subprocess task %s is not set to RuntimeIdentifierField.size()!", //$NON-NLS-1$
                                                                eachProcessActivity
                                                                        .getName()));
                                            }
                                        }

                                        /*
                                         * 3. Check if “Allow Unqualified
                                         * Sub-Process Identification” property
                                         * of all dynamic sub-process tasks is
                                         * set by default.
                                         */
                                        Object allowUnqualifiedProcIdentificationAttr =
                                                Xpdl2ModelUtil
                                                        .getOtherAttribute(subFlow,
                                                                XpdExtensionPackage.eINSTANCE
                                                                        .getDocumentRoot_AllowUnqualifiedSubProcessIdentification());
                                        if (allowUnqualifiedProcIdentificationAttr instanceof Boolean) {
                                            Boolean allowUnqualifiedProcIdentification =
                                                    (Boolean) allowUnqualifiedProcIdentificationAttr;
                                            if (!allowUnqualifiedProcIdentification
                                                    .booleanValue()) {
                                                fail(String
                                                        .format("'Allow unqualified sub-process identification' property of dynamic subprocess task %s is not set to true!", //$NON-NLS-1$
                                                                eachProcessActivity
                                                                        .getName()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}