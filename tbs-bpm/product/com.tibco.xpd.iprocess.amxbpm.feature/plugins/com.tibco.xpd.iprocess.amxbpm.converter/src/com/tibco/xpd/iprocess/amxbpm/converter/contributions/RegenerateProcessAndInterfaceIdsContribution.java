/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Contribution to re-generate Process and Process Interface Id's and update all
 * the references which refer the old ids.
 * 
 * @author kthombar
 * @since 06-Jul-2014
 */
public class RegenerateProcessAndInterfaceIdsContribution extends
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
        /* get all eObjects together. */
        List<EObject> allEObjects =
                getAllEObjects(processes, processInterfaces);

        Map<String, String> oldIdToNewIdMap = new HashMap<String, String>();
        /* Generate new Ids, store the old id to new id info in the passed map */
        generateNewIds(allEObjects, oldIdToNewIdMap);

        /* Update references */
        for (EObject eachEObject : allEObjects) {
            updateIdReferences(eachEObject, oldIdToNewIdMap);
        }

        return Status.OK_STATUS;
    }

    /**
     * Updates the Id references. Does the following
     * <p>
     * <li>If the passed {@link EObject} is a {@link Process} and if the process
     * implements an interface then updates the references with the help of
     * passed map.
     * <li>If the passed {@link EObject} is a {@link Process} then scans through
     * all the {@link Activity}'s of the process and updates all sub-proces and
     * Dynamic sub process references.
     * <li>Gets the Pool of the passed EObject ({@link Process} or
     * {@link ProcessInterface}) and updated the process id in the pool.
     * 
     * @param eachEObject
     *            the {@link EObject} whose Id references are to be updated.
     * @param oldIdToNewIdMap
     *            the map of old id to new id
     */
    private void updateIdReferences(EObject eachEObject,
            Map<String, String> oldIdToNewIdMap) {

        if (eachEObject instanceof Process) {
            /* update process refernces */
            updateProcessReferences((Process) eachEObject, oldIdToNewIdMap);

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil
                            .getAllActivitiesInProc((Process) eachEObject);
            /* update activity references */
            updateActivityReferences(allActivitiesInProc, oldIdToNewIdMap);
        }

        Package pkg = Xpdl2ModelUtil.getPackage(eachEObject);
        /* update pool references */
        updatePoolReferences(pkg.getPools(), oldIdToNewIdMap);

    }

    /**
     * Updates the Process Id that the pool has based on the Id's present in the
     * map.
     * 
     * @param pools
     *            the pool containing the process or interface
     * @param oldIdToNewIdMap
     *            the map of old id to new id
     */
    private void updatePoolReferences(EList<Pool> pools,
            Map<String, String> oldIdToNewIdMap) {

        for (Pool pool : pools) {

            String referencedProcessId = pool.getProcessId();

            if (referencedProcessId != null && !referencedProcessId.isEmpty()) {

                String newIdInMap = oldIdToNewIdMap.get(referencedProcessId);

                if (newIdInMap != null && !newIdInMap.isEmpty()) {
                    pool.setProcessId(newIdInMap);
                }
            }
        }
    }

    /**
     * Updates all the Sub-process/Dynamic Sub Process process id references
     * with the help of passed map.
     * 
     * @param allActivitiesInProc
     *            all the acticities in process
     * @param oldIdToNewIdMap
     *            the map of old id to new id
     */
    private void updateActivityReferences(
            Collection<Activity> allActivitiesInProc,
            Map<String, String> oldIdToNewIdMap) {

        for (Activity eachActivity : allActivitiesInProc) {

            Implementation impl = eachActivity.getImplementation();

            if (impl instanceof SubFlow) {

                SubFlow subFlow = (SubFlow) impl;
                String referencedProcessId = subFlow.getProcessId();

                if (referencedProcessId != null
                        && !referencedProcessId.isEmpty()) {

                    String newIdInMap =
                            oldIdToNewIdMap.get(referencedProcessId);

                    if (newIdInMap != null && !newIdInMap.isEmpty()) {

                        subFlow.setProcessId(newIdInMap);
                    }
                }
            }
        }
    }

    /**
     * Updates all the implemented process interface Id's with he help of map.
     * 
     * @param process
     *            the process
     * @param oldIdToNewIdMap
     *            the map of old id to new id
     */
    private void updateProcessReferences(Process process,
            Map<String, String> oldIdToNewIdMap) {

        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementedInterface());

        if (otherElement instanceof ImplementedInterface) {
            /* go ahead only if the process implements an interface */
            ImplementedInterface implementedIfc =
                    (ImplementedInterface) otherElement;

            String processInterfaceId = implementedIfc.getProcessInterfaceId();

            if (processInterfaceId != null && !processInterfaceId.isEmpty()) {

                String newIdInMap = oldIdToNewIdMap.get(processInterfaceId);

                if (newIdInMap != null && !newIdInMap.isEmpty()) {

                    implementedIfc.setProcessInterfaceId(newIdInMap);
                }
            }
        }
    }

    /**
     * Generates new Id's for process and interfaces and stores the old id to
     * new id info in the map.
     * 
     * @param allEObjects
     *            the {@link EObject} whose Id is to be generated.
     * @param oldIdToNewIdMap
     *            the map that will store the info of the old id to its new
     *            generated id.
     */
    private void generateNewIds(List<EObject> allEObjects,
            Map<String, String> oldIdToNewIdMap) {

        for (EObject eachEObject : allEObjects) {
            String newId = EcoreUtil.generateUUID();
            oldIdToNewIdMap.put(EcoreUtil.getID(eachEObject), newId);
            EcoreUtil.setID(eachEObject, newId);
        }
    }

    /**
     * 
     * @param processes
     * @param processInterfaces
     * @return all the {@link Process} and {@link ProcessInterface} together as
     *         a list of {@link EObject}.
     */
    private List<EObject> getAllEObjects(List<Process> processes,
            List<ProcessInterface> processInterfaces) {

        List<EObject> allEObjects = new ArrayList<EObject>();

        allEObjects.addAll(processInterfaces);
        allEObjects.addAll(processes);

        return allEObjects;
    }
}
