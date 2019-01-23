/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttributesContainer;
import com.tibco.xpd.xpdl2.Process;

/**
 * iPM_2_iPS.xslt now leaves 'hint' extended attributes (such as top field
 * marking properties in the original iProcess form).
 * <p>
 * These are xpdl:ExtendedAttribute's whose name is prefixed with "IPM2BPM_".
 * <p>
 * This contribution removes all extended attributes that are prefixed this way.
 * 
 * @author aallway
 * @since 12 Aug 2014
 */
public class RemoveTempIPM2BPMExtAttrsContribution extends
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
            monitor.beginTask("", processInterfaces.size() + processes.size());

            for (Process process : processes) {
                removeIPM2BPMExtAttrs(process);
                monitor.worked(1);
            }

            for (ProcessInterface processInterface : processInterfaces) {
                removeIPM2BPMExtAttrs(processInterface);
                monitor.worked(1);
            }

        } finally {

            monitor.done();
        }

        return Status.OK_STATUS;
    }

    /**
     * Remove all "IPM2BPM_xxx" attributes from anywhere in the child content
     * tree of the given container.
     * 
     * @param process
     */
    private void removeIPM2BPMExtAttrs(EObject container) {
        /*
         * Collect attrs to remove in separate list because we cannot delete
         * from collection we are iterating.
         */
        List<ExtendedAttribute> removeExtAttrs =
                new ArrayList<ExtendedAttribute>();

        for (TreeIterator<Object> allContents =
                EcoreUtil.getAllContents(container, false); allContents
                .hasNext();) {
            Object o = allContents.next();

            if (o instanceof ExtendedAttribute) {
                ExtendedAttribute extendedAttribute = (ExtendedAttribute) o;

                if (extendedAttribute.getName() != null
                        && extendedAttribute.getName().startsWith("IPM2BPM_")) { //$NON-NLS-1$
                    removeExtAttrs.add(extendedAttribute);
                }
            }
        }

        /*
         * Remove ext attrs
         */
        for (ExtendedAttribute extendedAttribute : removeExtAttrs) {
            /*
             * This shoudl always be the case for our xpdl model.
             */
            if (extendedAttribute.eContainer() instanceof ExtendedAttributesContainer) {
                ((ExtendedAttributesContainer) extendedAttribute.eContainer())
                        .getExtendedAttributes().remove(extendedAttribute);
            }
        }
    }

}
