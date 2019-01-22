/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.propertytesters;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.decorator.XPDProblemDecorator;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author rsomayaj
 * 
 */
public class ParamRefactorToIfcTester extends PropertyTester {

    private static final Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    /**
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     *      java.lang.String, java.lang.Object[], java.lang.Object)
     * 
     * @param receiver
     * @param property
     * @param args
     * @param expectedValue
     * @return
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        boolean testResult = false;
        if (receiver instanceof FormalParameter) {

            FormalParameter formalParameter = (FormalParameter) receiver;

            EObject container = formalParameter.eContainer();
            if (container instanceof Process) {

                Process process = (Process) container;
                ProcessInterface implementedProcessInterface =
                        ProcessInterfaceUtil
                                .getImplementedProcessInterface(process);
                if (implementedProcessInterface != null) {
                    if (!isExternalPackageProcIfc(process,
                            implementedProcessInterface)) {
                        IFile resource =
                                WorkingCopyUtil.getFile(formalParameter);
                        int severity = IMarker.PRIORITY_LOW;

                        try {
                            severity =
                                    XPDProblemDecorator
                                            .getSeverity(formalParameter,
                                                    resource);
                        } catch (CoreException e) {
                            LOG.error(e.getMessage());
                        }

                        if (IMarker.SEVERITY_ERROR != severity) {
                            testResult = true;
                        }
                    }
                }
            }
        }
        return testResult;

    }

    /**
     * @param process
     * @param implementedProcessInterface
     * @return
     */
    private boolean isExternalPackageProcIfc(Process process,
            ProcessInterface implementedProcessInterface) {
        WorkingCopy ifcWc =
                WorkingCopyUtil.getWorkingCopyFor(implementedProcessInterface);
        WorkingCopy procWc = WorkingCopyUtil.getWorkingCopyFor(process);
        if (ifcWc != procWc) {
            return true;
        }
        return false;
    }
}
