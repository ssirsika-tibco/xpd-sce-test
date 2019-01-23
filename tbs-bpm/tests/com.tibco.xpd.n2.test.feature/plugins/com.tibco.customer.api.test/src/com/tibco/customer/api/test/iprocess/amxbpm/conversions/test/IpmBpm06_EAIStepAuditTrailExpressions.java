/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * JUnit test to protect 'EAI Step audit trail expressions' contribution.
 * 
 * @author sajain
 * @since May 14, 2014
 */
public class IpmBpm06_EAIStepAuditTrailExpressions extends
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
        return "IpmBpm06_EAIStepAuditTrailExpressions"; //$NON-NLS-1$
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
                        "resources/ConversionTests", "IpmBpm06_EAIStepAuditTrailExpressions/ImportIpmXpdls/acttest1.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
                    if (eachProcess.getName().equalsIgnoreCase("ACTTEST1")) { //$NON-NLS-1$
                        Collection<Activity> allActs =
                                Xpdl2ModelUtil
                                        .getAllActivitiesInProc(eachProcess);
                        for (Activity eachProcessActivity : allActs) {
                            TaskType taskType =
                                    TaskObjectUtil
                                            .getTaskTypeStrict(eachProcessActivity);

                            if (TaskType.SERVICE_LITERAL.equals(taskType)) {
                                Object auditElement =
                                        Xpdl2ModelUtil
                                                .getOtherElement(eachProcessActivity,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_Audit());

                                if (auditElement instanceof Audit) {
                                    Audit aI = (Audit) auditElement;
                                    EList<AuditEvent> allAuditEvents =
                                            aI.getAuditEvent();
                                    for (int i = 0; i < allAuditEvents.size(); i++) {
                                        AuditEvent eachAuditEvent =
                                                allAuditEvents.get(i);
                                        AuditEventType eachAuditEventType =
                                                eachAuditEvent.getType();
                                        String auditEventInfoText =
                                                eachAuditEvent.getInformation()
                                                        .getText();
                                        if (AuditEventType.INITIATED_LITERAL
                                                .equals(eachAuditEventType)) {
                                            if (auditEventInfoText != null
                                                    && !auditEventInfoText
                                                            .isEmpty()) {
                                                assertEquals("Process.auditLog(FIELD1);",//$NON-NLS-1$
                                                        auditEventInfoText);
                                            }
                                        } else if (AuditEventType.COMPLETED_LITERAL
                                                .equals(eachAuditEventType)) {
                                            if (auditEventInfoText != null
                                                    && !auditEventInfoText
                                                            .isEmpty()) {
                                                assertEquals("Process.auditLog(FIELD3);",//$NON-NLS-1$
                                                        auditEventInfoText);
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
