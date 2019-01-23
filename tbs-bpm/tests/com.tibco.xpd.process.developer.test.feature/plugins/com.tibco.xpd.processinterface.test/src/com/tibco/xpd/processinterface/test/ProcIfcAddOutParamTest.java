/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processinterface.test;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author rsomayaj
 * 
 */
public class ProcIfcAddOutParamTest extends BaseAnalystTest {

    List<IStatus> statuses = new ArrayList<IStatus>();

    private IProject project = null;

    /**
     * @see junit.framework.TestCase#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     * 
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        if (null != project) {
            TestUtil.removeProject(project.getName());
        }
        super.tearDown();
    }

    ILogListener logListener = new ILogListener() {

        public void logging(IStatus status, String plugin) {
            if (status.getSeverity() == IStatus.ERROR) {
                statuses.add(status);
            }
        }

    };

    public void testProcessIfc() {
        // create a BPM/SOA Project
        // Try and invoke the New BPM Wizard - include a process MAIN1
        Exception exception = null;

        ILog log =
                Platform.getLog(Platform.getBundle("com.tibco.xpd.validation")); //$NON-NLS-1$
        log.addLogListener(logListener);

        XpdResourcesPlugin.getDefault().getLog().addLogListener(logListener);
        try {
            project = createProject("New BPM Project"); //$NON-NLS-1$

            Package xpdlPackage = getDefaultXPDLPackage(project);

            EditingDomain editingDomain =
                    WorkingCopyUtil.getEditingDomain(xpdlPackage);

            Process mainProcess = getInitialProcess(xpdlPackage);

            renameProcess(editingDomain, mainProcess, "MAIN1"); //$NON-NLS-1$

            Activity subProc = createSubProc(editingDomain, mainProcess);

            ProcessInterface procIfc =
                    createProcessInterfaceInPackage(editingDomain,
                            xpdlPackage,
                            "PROC IFC1"); //$NON-NLS-1$

            FormalParameter fp1 =
                    createFormalParam(editingDomain,
                            procIfc,
                            "FP1", //$NON-NLS-1$
                            ModeType.INOUT_LITERAL);

            FormalParameter fp2 =
                    createFormalParam(editingDomain,
                            procIfc,
                            "FP2", //$NON-NLS-1$
                            ModeType.INOUT_LITERAL);

            FormalParameter fp3 =
                    createFormalParam(editingDomain,
                            procIfc,
                            "FP3", //$NON-NLS-1$
                            ModeType.INOUT_LITERAL);

            Process implementingProcess =
                    createProcessThatImplementsIfc(editingDomain,
                            xpdlPackage,
                            procIfc,
                            "PROC IMPLEMENTING IFC"); //$NON-NLS-1$

            setSubFlowId(editingDomain, subProc, procIfc.getId());

            FormalParameter outParam =
                    createFormalParam(editingDomain,
                            procIfc,
                            "OUTPARAM", //$NON-NLS-1$
                            ModeType.OUT_LITERAL);
            WorkingCopy workingCopyFor =
                    WorkingCopyUtil.getWorkingCopyFor(xpdlPackage);
            workingCopyFor.save();

            TestUtil.buildAndWait();
            assertTrue("Does not contain all the formal parameters added.", //$NON-NLS-1$
                    doesProcessOrProcIfcContainGivenParams(procIfc,
                            fp1,
                            fp2,
                            fp3,
                            outParam));

            TestUtil.waitForValidatior();
        } catch (CoreException coreException) {
            coreException.printStackTrace();
            exception = coreException;
        } catch (Exception genExceptions) {
            genExceptions.printStackTrace();
            exception = genExceptions;
        } finally {
            if (exception != null) {
                try {
                    tearDown();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            assertTrue(exception == null);
        }

        for (IStatus status : statuses) {
            if (status.getSeverity() == IStatus.ERROR) {
                status.getException().printStackTrace();
                fail("Exception in code:" + status.getException()); //$NON-NLS-1$
            }
        }

        // TestUtil.delay(60000);

        // Create a process interface in the package - PROCIFC - 3 parameters -
        // all INOUT
        // Create a new process implementing the process ifc - PROCIFC - SUB1

        //
    }
}
