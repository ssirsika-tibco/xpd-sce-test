/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Contribution to refactor duplicate {@link FormalParameter} labels in a
 * {@link Process} OR {@link ProcessInterface}.
 * 
 * @author sajain
 * @since Jun 27, 2014
 */
public class SetUniqueParameterLabelContribution extends
        AbstractIProcessToBPMContribution {

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
     *      java.util.List, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param processes
     * @param processInterfaces
     * @param monitor
     * @return
     */
    @Override
    public IStatus convert(List<Process> processes,
            List<ProcessInterface> processInterfaces, IProgressMonitor monitor) {

        try {

            monitor.beginTask("", 1); //$NON-NLS-1$

            /*
             * Go through all processes.
             */
            for (Process eachProcess : processes) {

                /*
                 * Fetch all parameters from the current process.
                 */

                List<FormalParameter> allProcessParameters =
                        new ArrayList<FormalParameter>();

                allProcessParameters.addAll(eachProcess.getFormalParameters());

                /*
                 * Proceed only when there are any formal parameters present.
                 */
                if (allProcessParameters != null
                        && !allProcessParameters.isEmpty()) {

                    /*
                     * Refactor duplicate formal parameter labels in each
                     * process.
                     */
                    refactorDuplicateParameterLabels(allProcessParameters,
                            eachProcess);
                }

            }

            /*
             * Go through all process interfaces.
             */
            for (ProcessInterface eachProcessInterface : processInterfaces) {

                /*
                 * Fetch all parameters from the current process interface.
                 */

                List<FormalParameter> allProcessInterfaceParameters =
                        new ArrayList<FormalParameter>();

                allProcessInterfaceParameters.addAll(eachProcessInterface
                        .getFormalParameters());

                /*
                 * Proceed only when there are any formal parameters present.
                 */
                if (allProcessInterfaceParameters != null
                        && !allProcessInterfaceParameters.isEmpty()) {

                    /*
                     * Refactor duplicate formal parameter labels in each
                     * process interface.
                     */
                    refactorDuplicateParameterLabels(allProcessInterfaceParameters,
                            eachProcessInterface);
                }
            }

            monitor.worked(1);

            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    /**
     * Refactor duplicate {@link FormalParameter} labels.
     * 
     * @param allParameters
     *            List of parameters and methods in the process/process
     *            interface currently being processed.
     * 
     * @param parameterContainer
     *            Process/process interface enclosing these parameters.
     */
    private void refactorDuplicateParameterLabels(
            List<FormalParameter> allParameters,
            FormalParametersContainer parameterContainer) {

        /*
         * Go through all parameters to make their label unique.
         */
        for (FormalParameter eachParameter : allParameters) {

            /*
             * Fetch label of current parameter.
             */
            String label = Xpdl2ModelUtil.getDisplayName(eachParameter);

            /*
             * Validate parameter's label.
             */
            if (label != null && !label.isEmpty()) {

                /*
                 * See if there's a parameter with duplicate label. If we find
                 * such parameter, then modify the labels of both the
                 * parameters: the parameter currently being processed AND the
                 * parameter which duplicates it's label.
                 */

                FormalParameter parameterWithDuplicateLabel =
                        getParameterWithDuplicateLabel(eachParameter,
                                allParameters);

                if (parameterWithDuplicateLabel != null) {

                    /*
                     * Duplicate labels detected, make them unique.
                     */

                    String newLabel = getUniqueNameForParameter(eachParameter);

                    if (null != newLabel) {

                        setExtensionAttribute(eachParameter,
                                getXpdExtensionPackage()
                                        .getDocumentRoot_DisplayName(),
                                newLabel);

                    }

                    newLabel =
                            getUniqueNameForParameter(parameterWithDuplicateLabel);

                    if (null != newLabel) {

                        setExtensionAttribute(parameterWithDuplicateLabel,
                                getXpdExtensionPackage()
                                        .getDocumentRoot_DisplayName(),
                                newLabel);

                    }
                }
            }
        }
    }

    /**
     * Return a formal parameter with same label (but different id), but if no
     * such parameter exists in the process/process interface, return
     * <code>null</code>.
     * 
     * @param currentParameter
     *            Parameter currently being processed.
     * @param allParameters
     *            List of all parameters in current process/process interface.
     * 
     * @return A formal parameter with same label (but different id) as that of
     *         currentParameter, but if no such parameter exists in the
     *         process/process interface, return <code>null</code>.
     */
    private FormalParameter getParameterWithDuplicateLabel(
            FormalParameter currentParameter,
            List<FormalParameter> allParameters) {

        /*
         * Go through all formal parameters.
         */
        for (FormalParameter eachParameter : allParameters) {

            String currentParametersLabel =
                    Xpdl2ModelUtil.getDisplayName(currentParameter);

            String eachParametersLabel =
                    Xpdl2ModelUtil.getDisplayName(eachParameter);

            if (eachParametersLabel != null) {

                /*
                 * See if we've found a parameter with duplicate label.
                 */
                if (currentParametersLabel.equals(eachParametersLabel)
                        && !currentParameter.getId()
                                .equals(eachParameter.getId())) {

                    /*
                     * If we have, then return it.
                     */
                    return eachParameter;

                }
            }
        }

        /*
         * Return null if we don't find any parameter with duplicate label.
         */
        return null;

    }

    /**
     * Return a new label for the specified parameter based on whether it's of
     * mode type IN or OUT. Return <code>null</code> if it's of none of those
     * types.
     * 
     * @param param
     *            Parameter currently being processed.
     * 
     * @return A new label for the specified parameter based on whether it's of
     *         mode type IN or OUT. Return <code>null</code> if it's of none of
     *         those types.
     */
    private String getUniqueNameForParameter(FormalParameter param) {

        String newLabel = null;

        /*
         * Check for type.
         */
        if (param.getMode().equals(ModeType.IN_LITERAL)) {

            /*
             * Append (IN) to the label if the type is IN.
             */
            newLabel =
                    Xpdl2ModelUtil.getDisplayName(param)
                            + " (" //$NON-NLS-1$
                            + com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages.SetUniqueParameterLabelContribution_label_IN
                            + ")"; //$NON-NLS-1$

        } else if (param.getMode().equals(ModeType.OUT_LITERAL)) {

            /*
             * Append (OUT) to the label if the type is OUT.
             */
            newLabel =
                    Xpdl2ModelUtil.getDisplayName(param)
                            + " (" //$NON-NLS-1$
                            + com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages.SetUniqueParameterLabelContribution_label_OUT
                            + ")"; //$NON-NLS-1$ 
        }

        return newLabel;
    }
}
