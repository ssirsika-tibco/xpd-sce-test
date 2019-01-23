/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.emf.validations;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author rsomayaj
 * 
 */
public class ValidReferenceConstraint extends AbstractModelConstraint {

    /**
     * 
     */
    public ValidReferenceConstraint() {
    }

    /**
     * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
     * 
     * @param context
     * @return
     */
    @Override
    public IStatus validate(IValidationContext context) {

        EObject target = context.getTarget();

        if (target instanceof com.tibco.xpd.xpdl2.Package) {
            com.tibco.xpd.xpdl2.Package xpdlPackage =
                    (com.tibco.xpd.xpdl2.Package) target;

            // Check formal Parameters in Process Interfaces
            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(xpdlPackage);
            if (processInterfaces != null) {
                List<ProcessInterface> processInterfacesList =
                        processInterfaces.getProcessInterface();
                for (ProcessInterface procIfc : processInterfacesList) {
                    if (isAnyFormalParamUnresolvedExternalReference(procIfc
                            .getFormalParameters())) {
                        return context.createFailureStatus();
                    }
                }

            }
            // Check formal Parameters in Processes
            List<Process> processes = xpdlPackage.getProcesses();
            for (Process proc : processes) {
                if (isAnyFormalParamUnresolvedExternalReference(proc
                        .getFormalParameters())) {
                    return context.createFailureStatus();
                }
            }

        }
        return context.createSuccessStatus();
    }

    /**
     * @param procIfc
     */
    private boolean isAnyFormalParamUnresolvedExternalReference(
            List<FormalParameter> formalParameters) {
        for (FormalParameter param : formalParameters) {
            if (isParamUnresolvedExternalReference(param)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param param
     * @return
     */
    private boolean isParamUnresolvedExternalReference(FormalParameter param) {
        DataType dataType = param.getDataType();
        if (dataType instanceof ExternalReference) {
            ExternalReference externalReference = (ExternalReference) dataType;
            return ProcessUIUtil
                    .isUnresolvedExternalReference(externalReference);
        }
        return false;
    }
}
