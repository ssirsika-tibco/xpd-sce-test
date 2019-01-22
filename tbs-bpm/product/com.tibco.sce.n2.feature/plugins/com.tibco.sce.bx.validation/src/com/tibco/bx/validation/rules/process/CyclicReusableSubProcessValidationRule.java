/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validate against cycle's across XPDL files via re-usable sub-processes.
 * <p>
 * Each XPDL file becomes a component in deployed AMX BPM composite/DAA -
 * currently (AMX BPM 1.1+) components cannot have cyclic dependencies.
 * 
 * @author aallway
 * @since 12 May 2011
 */
public class CyclicReusableSubProcessValidationRule extends
        PackageValidationRule {

    private static final String ISSUE_IMPLEMENTED_INTERFACE_CAUSED_CYCLE =
            "bx.implementedInterfaceCausedXpdlRefCycle"; //$NON-NLS-1$

    private static final String ISSUE_SUBPROCESS_INVOKE_CAUSED_CYCLE =
            "bx.subProcessTaskCausedXpdlRefCycle"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     * 
     * @param pkg
     */
    @Override
    public void validate(Package pkg) {
        Map<Package, Set<Package>> packageReferenceCache =
                new HashMap<Package, Set<Package>>();

        for (Process process : pkg.getProcesses()) {
            validateProcess(pkg, process, packageReferenceCache);
        }

    }

    /**
     * @param originalPackage
     * @param process
     * @param cachedResults
     */
    private void validateProcess(Package originalPackage, Process process,
            Map<Package, Set<Package>> packageReferenceCache) {
        /*
         * Validate references via sub-process tasks
         */
        for (Activity activity : process.getActivities()) {
            validateActivity(originalPackage, activity, packageReferenceCache);
        }

        for (ActivitySet activitySet : process.getActivitySets()) {
            for (Activity activity : activitySet.getActivities()) {
                validateActivity(originalPackage,
                        activity,
                        packageReferenceCache);
            }
        }

        /*
         * Validate references via implemented process interface
         */
        ProcessInterface implementedProcessInterface =
                ProcessInterfaceUtil.getImplementedProcessInterface(process);
        if (implementedProcessInterface != null) {
            Package refPackage =
                    Xpdl2ModelUtil.getPackage(implementedProcessInterface);

            List<String> cyclePath = new ArrayList<String>();
            if (hasPackageReferenceCycle(originalPackage,
                    refPackage,
                    packageReferenceCache,
                    cyclePath)) {
                addIssue(ISSUE_IMPLEMENTED_INTERFACE_CAUSED_CYCLE,
                        process,
                        Collections
                                .singletonList(getReferencePath(originalPackage,
                                        cyclePath)));
            }
        }

    }

    /**
     * @param originalPackage
     * @param activity
     * @param packageReferenceCache
     */
    private void validateActivity(Package originalPackage, Activity activity,
            Map<Package, Set<Package>> packageReferenceCache) {

        if (TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {

            EObject subProcOrIfc =
                    TaskObjectUtil.getSubProcessOrInterface(activity);
            if (subProcOrIfc != null) {
                Package refPackage = Xpdl2ModelUtil.getPackage(subProcOrIfc);

                List<String> cyclePath = new ArrayList<String>();

                if (hasPackageReferenceCycle(originalPackage,
                        refPackage,
                        packageReferenceCache,
                        cyclePath)) {
                    addIssue(ISSUE_SUBPROCESS_INVOKE_CAUSED_CYCLE,
                            activity,
                            Collections
                                    .singletonList(getReferencePath(originalPackage,
                                            cyclePath)));
                }
            }
        }
    }

    /**
     * @param originalPackage
     * @param refPackage
     * @param packageReferenceCache
     * 
     * @return <code>true</code> If there is a cycle of references from
     *         originalPackage thru refPackage back to originalPackage
     */
    private boolean hasPackageReferenceCycle(Package originalPackage,
            Package refPackage,
            Map<Package, Set<Package>> packageReferenceCache,
            List<String> cyclePath) {
        if (refPackage != null && refPackage != originalPackage) {
            appendFileName(refPackage, cyclePath);

            /*
             * Recursively get dependencies looking for original package.
             */
            HashSet<Package> alreadyProcessed = new HashSet<Package>();
            alreadyProcessed.add(refPackage);

            return recursiveCycleCheck(originalPackage,
                    getReferencedPackages(refPackage, packageReferenceCache),
                    packageReferenceCache,
                    alreadyProcessed,
                    cyclePath);
        }
        return false;
    }

    /**
     * @param originalPackage
     * @param referencedPackages
     * @param packageReferenceCache
     * @param alreadyProcessed
     * @param cyclePath
     * @return <code>true</code> if the referencedPackages have references to
     *         the originalPackage
     */
    private boolean recursiveCycleCheck(Package originalPackage,
            Set<Package> referencedPackages,
            Map<Package, Set<Package>> packageReferenceCache,
            HashSet<Package> alreadyProcessed, List<String> cyclePath) {

        if (referencedPackages.contains(originalPackage)) {
            return true;
        }

        for (Package refPackage : referencedPackages) {
            /*
             * There may be cycles underneath the originalPackage
             * (A-->B-->C-->D-->C for instance) - we aren't looking for these
             * (that will be dealt with when validating the packages concerned
             * BUT we must stop recursing if we find one)!
             */
            if (!alreadyProcessed.contains(refPackage)) {
                alreadyProcessed.add(refPackage);

                appendFileName(refPackage, cyclePath);

                if (recursiveCycleCheck(originalPackage,
                        getReferencedPackages(refPackage, packageReferenceCache),
                        packageReferenceCache,
                        alreadyProcessed,
                        cyclePath)) {
                    return true;
                }

                cyclePath.remove(cyclePath.size() - 1);
            }

        }

        return false;
    }

    /**
     * @param refPackage
     * @param cyclePath
     */
    private void appendFileName(Package refPackage, List<String> cyclePath) {
        cyclePath.add(getFileName(refPackage));
    }

    private String getReferencePath(Package originalPackage,
            List<String> cyclePath) {
        String originalPackageName = getFileName(originalPackage);
        StringBuilder referencePath = new StringBuilder(originalPackageName);

        for (String refPackageName : cyclePath) {
            referencePath.append("->"); //$NON-NLS-1$
            referencePath.append(refPackageName);
        }

        referencePath.append("->"); //$NON-NLS-1$
        referencePath.append(originalPackageName);

        return referencePath.toString();
    }

    /**
     * @param pkg
     * @param cyclePath
     * @return file name.
     */
    private String getFileName(Package pkg) {
        IFile refPkgFile = WorkingCopyUtil.getFile(pkg);
        if (refPkgFile != null) {
            return refPkgFile.getName();
        } else {
            return "???"; //$NON-NLS-1$
        }
    }

    /**
     * @param pkg
     * @param previousResults
     * 
     * @return All the <b>other</b> packages that are referenced from the given
     *         package (but never the given package.
     */
    private Set<Package> getReferencedPackages(Package pkg,
            Map<Package, Set<Package>> packageReferenceCache) {

        Set<Package> refPackages = packageReferenceCache.get(pkg);
        if (refPackages == null) {
            refPackages = new HashSet<Package>();

            for (Process process : pkg.getProcesses()) {
                addReferencedPackages(process, refPackages);
            }

            packageReferenceCache.put(pkg, refPackages);
        }

        /*
         * Never return the package we were asked for (not interested in
         * self-references)
         */
        refPackages.remove(pkg);

        return refPackages;
    }

    /**
     * @param process
     * @param refPackages
     */
    private void addReferencedPackages(Process process, Set<Package> refPackages) {
        /*
         * Include references via sub-process tasks
         */
        for (Activity activity : process.getActivities()) {
            addReferencedPackages(activity, refPackages);
        }

        for (ActivitySet activitySet : process.getActivitySets()) {
            for (Activity activity : activitySet.getActivities()) {
                addReferencedPackages(activity, refPackages);
            }
        }

        /*
         * Include references via implemented process interface
         */
        ProcessInterface implementedProcessInterface =
                ProcessInterfaceUtil.getImplementedProcessInterface(process);
        if (implementedProcessInterface != null) {
            Package refPackage =
                    Xpdl2ModelUtil.getPackage(implementedProcessInterface);

            if (refPackage != null) {
                refPackages.add(refPackage);
            }
        }
    }

    /**
     * @param activity
     * @param refPackages
     */
    private void addReferencedPackages(Activity activity,
            Set<Package> refPackages) {
        if (TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {

            EObject subProcOrIfc =
                    TaskObjectUtil.getSubProcessOrInterface(activity);
            if (subProcOrIfc != null) {
                Package refPackage = Xpdl2ModelUtil.getPackage(subProcOrIfc);

                if (refPackage != null) {
                    refPackages.add(refPackage);
                }
            }
        }
    }

}
