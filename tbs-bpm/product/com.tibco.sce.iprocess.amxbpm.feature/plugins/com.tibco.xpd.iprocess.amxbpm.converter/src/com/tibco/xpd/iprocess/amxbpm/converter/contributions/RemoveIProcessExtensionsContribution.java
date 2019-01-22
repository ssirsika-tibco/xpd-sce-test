/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.ipm.iProcessExt.IProcessExtPackage;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Contribution to remove the IProcess Extension(IProcessExt:) attributes and
 * elements from the package. The is the last most conversion to take place and
 * should have the biggest numbered priority.
 * 
 * @author kthombar
 * @since 09-Apr-2014
 */
public class RemoveIProcessExtensionsContribution extends
        AbstractIProcessToBPMContribution {

    /**
     * @see com.tibco.xpd.customer.api.iprocess.bpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
     *      java.util.List, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param processes
     * @param processInterfaces
     * @param monitor
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    public IStatus convert(List<Process> processes,
            List<ProcessInterface> processInterfaces, IProgressMonitor monitor) {

        try {
            /*
             * Sid XPD-6230 No need to set task name if it's judst a repeat of
             * the contribution plugin.xml desc' as that is output by framework
             * anyway.
             */

            /*
             * Remove iProcess Extensions for all Processes.
             */

            /*
             * Create a single set of packages (although we _should_ have only
             * one process/interface per package, we don't nbeed to be so
             * specific). If we do happen to have multi procs in pkg then we'll
             * only do each pkg once!
             */
            Set<Package> pkgs = getPackageSet(processes, processInterfaces);

            monitor.beginTask("", pkgs.size()); //$NON-NLS-1$

            for (Package pkg : pkgs) {
                /*
                 * Take a copy of the tree iterator content as we will be
                 * removing data from it which would break things if we used it
                 * to iterate thru content directly)
                 */
                List<EObject> allContentList = new ArrayList<EObject>();
                for (Iterator iterator = pkg.eAllContents(); iterator.hasNext();) {
                    allContentList.add((EObject) iterator.next());
                }

                for (EObject eObject : allContentList) {
                    removeIProcessExtElementsAndAttributes(eObject);
                }

                monitor.worked(1);
            }

            /*
             * XPD-6370: Main framework now reports status with each conversion
             * description. so no need to do it here also.
             */
            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    /**
     * @param processes
     * @param processInterfaces
     * @return
     */
    protected Set<Package> getPackageSet(List<Process> processes,
            List<ProcessInterface> processInterfaces) {
        Set<Package> pkgs = new HashSet<Package>();

        for (ProcessInterface processInterface : processInterfaces) {
            Package pkg = Xpdl2ModelUtil.getPackage(processInterface);
            if (pkg != null) {
                pkgs.add(pkg);
            }
        }

        for (Process process : processes) {
            if (process.getPackage() != null) {
                pkgs.add(process.getPackage());
            }
        }
        return pkgs;
    }

    /**
     * Removes the 'IProcessExt:' attribute and element from the {@link EObject}
     * passed
     * 
     * @param obj
     */
    private void removeIProcessExtElementsAndAttributes(EObject obj) {

        if (obj instanceof OtherElementsContainer) {
            /* get other elements */
            OtherElementsContainer eachAttribute = (OtherElementsContainer) obj;

            FeatureMap featureMap = eachAttribute.getOtherElements();

            removeIProcessExt(obj, featureMap);
        }

        if (obj instanceof OtherAttributesContainer) {
            /* get other attributes */
            OtherAttributesContainer eachAttribute =
                    (OtherAttributesContainer) obj;

            FeatureMap featureMap = eachAttribute.getOtherAttributes();

            removeIProcessExt(obj, featureMap);
        }
    }

    /**
     * Remove the IProcess extension from the feature map passed.
     * 
     * @param obj
     * @param featureMap
     */
    private void removeIProcessExt(EObject obj, FeatureMap featureMap) {

        /*
         * Copy content to separate list so that we are not removing objects
         * from something we are iterating thru.
         */
        Set<EStructuralFeature> eStructuralfeature =
                new HashSet<EStructuralFeature>();

        Iterator<Entry> iterator = featureMap.iterator();

        for (; iterator.hasNext();) {

            Entry next = iterator.next();

            if (null != next.getEStructuralFeature()
                    && null != next.getEStructuralFeature()
                            .getEContainingClass()
                    && next.getEStructuralFeature().getEContainingClass()
                            .getEPackage() instanceof IProcessExtPackage) {
                /* get all the IProcessExt eStructural Features */
                eStructuralfeature.add(next.getEStructuralFeature());
            }
        }

        if (!eStructuralfeature.isEmpty()) {
            /* Remove all the IProcessExt EStructural Features from the Eobject */
            for (EStructuralFeature eachFeature : eStructuralfeature) {
                obj.eUnset(eachFeature);
            }
        }
    }
}