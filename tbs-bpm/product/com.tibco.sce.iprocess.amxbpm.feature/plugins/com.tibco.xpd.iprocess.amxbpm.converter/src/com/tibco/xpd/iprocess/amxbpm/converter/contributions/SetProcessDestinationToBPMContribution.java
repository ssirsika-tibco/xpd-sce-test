/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.iprocess.amxbpm.converter.IProcessAMXBPMConverterPlugin;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;

/**
 * Conversion contribution that removes all IProcess destinations enabled on the
 * process / process interface and adds BPM destination to them.
 * 
 * @author kthombar
 * @since 06-Apr-2014
 */
public class SetProcessDestinationToBPMContribution extends
        AbstractIProcessToBPMContribution {

    private final static String IPROCESS_DESTINATION = "iProcess"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.customer.api.iprocess.bpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
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
             * Sid XPD-6230 No need to set task name if it's judst a repeat of
             * the contribution plugin.xml desc' as that is output by framework
             * anyway.
             */

            Collection<String> iprocessDestinationNames =
                    GlobalDestinationHelper
                            .getGlobalDestinationNamesForId(ProcessDestinationUtil.IPROCESS_GLOBAL_DESTINATION);

            Collection<String> bpmDestinationNames =
                    GlobalDestinationHelper
                            .getGlobalDestinationNamesForId(N2Utils.N2_GLOBAL_DESTINATION_ID);

            if (!bpmDestinationNames.isEmpty()) {

                setProcessDestinationToBPM(processes,
                        iprocessDestinationNames,
                        bpmDestinationNames);

                setProcessInterfaceDestinationToBPM(processInterfaces,
                        iprocessDestinationNames,
                        bpmDestinationNames);
                monitor.worked(1);

                /*
                 * XPD-6370: Main framework now reports status with each
                 * conversion description. so no need to do it here also.
                 */
                return Status.OK_STATUS;

            } else {
                monitor.worked(1);
                /*
                 * if no BPM destinations are available then add an IStatus
                 * error entry indicating the same.
                 */
                /* We did not find any BPM destinations, return immediately. */
                return new Status(
                        IStatus.ERROR,
                        IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                        Messages.SetProcessDestinationToBPMContribution_NoBpmDestinationEnabledIstatus_msg);

            }

        } finally {
            monitor.done();
        }
    }

    /**
     * Sets the destination of {@link ProcessInterface} to BPM and removed all
     * the IProcess destinations enabled.
     * 
     * @param processInterfaces
     * @param bpmDestinationNames
     * @param iprocessDestinationNames
     */
    private void setProcessInterfaceDestinationToBPM(
            List<ProcessInterface> processInterfaces,
            Collection<String> iprocessDestinationNames,
            Collection<String> bpmDestinationNames) {

        if (processInterfaces != null && !processInterfaces.isEmpty()) {

            for (ProcessInterface eachInterface : processInterfaces) {

                EList<ExtendedAttribute> extendedAttributes =
                        eachInterface.getExtendedAttributes();

                /* remove iProcess destinations */
                removeIProcessDestination(extendedAttributes,
                        iprocessDestinationNames);

                /* add BPM destination */
                addBpmDestination(extendedAttributes, bpmDestinationNames);
            }
        }
    }

    /**
     * Set the destination of {@link Process} to BPM and removes all the
     * IProcess destinations enabled
     * 
     * @param processes
     * @param bpmDestinationNames
     * @param iprocessDestinationNames
     */
    private void setProcessDestinationToBPM(List<Process> processes,
            Collection<String> iprocessDestinationNames,
            Collection<String> bpmDestinationNames) {

        if (processes != null && !processes.isEmpty()) {

            for (Process eachProcess : processes) {

                EList<ExtendedAttribute> extendedAttributes =
                        eachProcess.getExtendedAttributes();

                /* Remove iProcess destinations */
                removeIProcessDestination(extendedAttributes,
                        iprocessDestinationNames);

                /* add BPM Destinations */
                addBpmDestination(extendedAttributes, bpmDestinationNames);
            }
        }
    }

    /**
     * Removes all the IProcess destinations from the extended attributes
     * 
     * @param extendedAttributes
     * @param iprocessDestinationNames
     *            all the IProcess destinations available
     */
    private void removeIProcessDestination(
            EList<ExtendedAttribute> extendedAttributes,
            Collection<String> iprocessDestinationNames) {

        List<ExtendedAttribute> extendedAttributesToRemove =
                new ArrayList<ExtendedAttribute>();

        if (!iprocessDestinationNames.isEmpty()) {

            for (ExtendedAttribute eachExtendedAttribute : extendedAttributes) {
                if (DestinationUtil.DESTINATION_ATTRIBUTE
                        .equals(eachExtendedAttribute.getName())) {

                    String value = eachExtendedAttribute.getValue();
                    if (value != null && !value.isEmpty()) {

                        if (iprocessDestinationNames.contains(value)) {
                            /* add extended attribute to the list to be deleted */
                            extendedAttributesToRemove
                                    .add(eachExtendedAttribute);
                        }
                    }
                }
            }
        } else {
            /*
             * if no iProcess destination types are enabled, then we simply
             * remove the iProcess destinations by fetching it by value i.e.
             * "iProcess"
             */
            for (ExtendedAttribute eachExtendedAttribute : extendedAttributes) {
                if (DestinationUtil.DESTINATION_ATTRIBUTE
                        .equals(eachExtendedAttribute.getName())) {
                    String value = eachExtendedAttribute.getValue();
                    /*
                     * came across scenarios where the attribute name was
                     * "Destination" but the attribute value was empty, removing
                     * these empty destinations as well :-)
                     */
                    if (value == null || value.isEmpty()
                            || IPROCESS_DESTINATION.equals(value)) {
                        extendedAttributesToRemove.add(eachExtendedAttribute);
                    }
                }
            }
        }

        if (!extendedAttributesToRemove.isEmpty()) {
            /* remove all IProcess destination extended attributes */
            extendedAttributes.removeAll(extendedAttributesToRemove);
        }
    }

    /**
     * Adds BPM destination to the extended attribute passed.
     * 
     * @param extendedAttributes
     * @param bpmDestinationNames
     *            list of all BPM destinations available
     */
    private void addBpmDestination(EList<ExtendedAttribute> extendedAttributes,
            Collection<String> bpmDestinationNames) {
        /*
         * No destination available, create an extended attribute with
         * destination = BPM
         */
        ExtendedAttribute iProcessExtendedAttribute =
                Xpdl2Factory.eINSTANCE.createExtendedAttribute();

        iProcessExtendedAttribute
                .setName(DestinationUtil.DESTINATION_ATTRIBUTE);
        iProcessExtendedAttribute.setValue(new ArrayList<String>(
                bpmDestinationNames).get(0));
        extendedAttributes.add(iProcessExtendedAttribute);
    }
}
