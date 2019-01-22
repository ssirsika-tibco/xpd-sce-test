/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Logical groups for the Xpdl2 Package in the Project Explorer tree. This
 * defines all the logical nodes under a <code>Package</code> and the
 * <code>Process</code> group.
 * <p>
 * <code>Package</code> will have the following groups:
 * <ul>
 * <li>DataFields</li>
 * <li>Participants</li>
 * <li>TypeDeclarations</li>
 * <li>Processes</li>
 * </ul>
 * </p>
 * <p>
 * <code>Process</code> will have the follwing groups:
 * <ul>
 * <li>DataFields</li>
 * <li>Parameters</li>
 * <li>Participants</li>
 * </ul>
 * </p>
 * 
 * @author njpatel
 */
public class PackageAssetGroups {

    /**
     * <code>Package</code> owner of these groups
     */
    private final Package owner;

    /**
     * The groups under the <code>Package</code>
     */
    private final AbstractAssetGroup[] packageGroups;

    /**
     * Map to hold the logical groups for each <code>Process</code> in this
     * <code>Package</code>
     */
    private Map<Process, AbstractAssetGroup[]> processGroupsMap =
            new HashMap<Process, AbstractAssetGroup[]>();

    /**
     * Map to hold the logical groups for each <code>ProcessInterface</code> in
     * this <code>Package</code>
     */
    private Map<ProcessInterface, AbstractAssetGroup[]> processInterfaceGroupsMap =
            new HashMap<ProcessInterface, AbstractAssetGroup[]>();

    /**
     * Map to hold the logical groups for each <code>InterfaceMethod</code> in
     * this <code>Package</code>
     */
    private Map<InterfaceMethod, AbstractAssetGroup[]> interfaceMethodGroupsMap =
            new HashMap<InterfaceMethod, AbstractAssetGroup[]>();

    /**
     * Map to hold the <code>PackageAssetGroups</code> associated with a
     * <code>Package</code>.
     */
    private static Map<Package, PackageAssetGroups> packageGroupsMap =
            new HashMap<Package, PackageAssetGroups>();

    public PackageAssetGroups(Package owner) {
        this.owner = owner;

        if (owner != null) {
            // Define the Package groups
            packageGroups =
                    new AbstractAssetGroup[] { new DataFieldGroup(owner),
                            new ParticipantGroup(owner),
                            new TypeDeclarationGroup(owner),
                            new ProcessInterfaceGroup(owner),
                            new ProcessGroup(owner) };
        } else {
            packageGroups = null;
        }
    }

    /**
     * Get the logical groups for the owner <code>Package</code>.
     * 
     * @return Array of <code>AbstractAssetGroup</code> for each expected
     *         logical group under a <code>Package</code>. If no owner was
     *         defined then <b>null</b> will be returned.
     */
    public AbstractAssetGroup[] getPackageGroups() {
        /*
         * Sid XPD-2516: Added filter of {@link ProcessEditorElementType} for
         * checking exclusion via processEditConfiguration/ElementTypeExclusion
         * extension point. A group will be filtered out IF it is excluded AND
         * it is not empty.
         */
        return getFilteredAssetGroups(packageGroups);
    }

    /**
     * Get the logical groups for the given <code>Process</code>.
     * 
     * @param process
     * @return Array of <code>AbstractAssetGroup</code> for each expected
     *         logical group under a Process. If the process is null, or does
     *         not belong to this owner <code>Package</code>, or the owner was
     *         not defined then <b>null</b> will be returned.
     */
    public AbstractAssetGroup[] getProcessGroup(Process process) {
        AbstractAssetGroup[] groups = null;

        if (owner != null && process != null) {
            if (processGroupsMap.containsKey(process)) {
                // Map already contains the groups
                groups = processGroupsMap.get(process);
            } else {
                // Check if this process belongs to the owner
                if (owner.getProcesses().contains(process)) {

                    // Define the groups for the process
                    groups =
                            new AbstractAssetGroup[] {
                                    new CorrelationDataGroup(process),
                                    new DataFieldGroup(process),
                                    new FormalParameterGroup(process),
                                    new ParticipantGroup(process) };
                    // Update the mapping
                    processGroupsMap.put(process, groups);
                }
            }
        }

        /*
         * Sid XPD-2516: Added filter of {@link ProcessEditorElementType} for
         * checking exclusion via processEditConfiguration/ElementTypeExclusion
         * extension point. A group will be filtered out IF it is excluded AND
         * it is not empty.
         */
        return getFilteredAssetGroups(groups);
    }

    /**
     * Get the logical groups for the given <code>ProcessInterface</code>.
     * 
     * @param process
     * @return Array of <code>AbstractAssetGroup</code> for each expected
     *         logical group under a Process. If the process is null, or does
     *         not belong to this owner <code>Package</code>, or the owner was
     *         not defined then <b>null</b> will be returned.
     */
    public AbstractAssetGroup[] getProcessInterfaceGroup(
            ProcessInterface processInterface) {
        AbstractAssetGroup[] groups = null;

        if (owner != null && processInterface != null) {
            if (processInterfaceGroupsMap.containsKey(processInterface)) {
                // Map already contains the groups
                groups = processInterfaceGroupsMap.get(processInterface);
            } else {
                // Check if this process interface belongs to the owner
                ProcessInterfaces processInterfaces =
                        (ProcessInterfaces) owner
                                .getOtherElement(XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ProcessInterfaces()
                                        .getName());
                if (processInterfaces != null
                        && processInterfaces.getProcessInterface()
                                .contains(processInterface)) {

                    // Define the groups for the process
                    groups =
                            new AbstractAssetGroup[] {
                                    new StartMethodGroup(processInterface),
                                    new IntermediateMethodGroup(
                                            processInterface),
                                    new FormalParameterGroup(processInterface) };

                    // Update the mapping
                    processInterfaceGroupsMap.put(processInterface, groups);
                }
            }
        }

        /*
         * Sid XPD-2516: Added filter of {@link ProcessEditorElementType} for
         * checking exclusion via processEditConfiguration/ElementTypeExclusion
         * extension point. A group will be filtered out IF it is excluded AND
         * it is not empty.
         */
        return getFilteredAssetGroups(groups);
    }

    /**
     * Get the logical groups for the given <code>ProcessInterface</code>.
     * 
     * @param process
     * @return Array of <code>AbstractAssetGroup</code> for each expected
     *         logical group under a Process. If the process is null, or does
     *         not belong to this owner <code>Package</code>, or the owner was
     *         not defined then <b>null</b> will be returned.
     */
    public AbstractAssetGroup[] getInterfaceMethodGroup(
            InterfaceMethod interfaceMethod) {
        AbstractAssetGroup[] groups = null;

        if (owner != null && interfaceMethod != null) {
            if (interfaceMethodGroupsMap.containsKey(interfaceMethod)) {
                // Map already contains the groups
                groups = interfaceMethodGroupsMap.get(interfaceMethod);
            } else {
                // Check if this interface method belongs to the owner

                Package pkg = Xpdl2ModelUtil.getPackage(interfaceMethod);
                if (owner.equals(pkg)) {

                    // Define the groups for the process
                    groups =
                            new AbstractAssetGroup[] { new EndErrorGroup(
                                    interfaceMethod) };

                    // Update the mapping
                    interfaceMethodGroupsMap.put(interfaceMethod, groups);
                }
            }
        }

        /*
         * Sid XPD-2516: Added filter of {@link ProcessEditorElementType} for
         * checking exclusion via processEditConfiguration/ElementTypeExclusion
         * extension point. A group will be filtered out IF it is excluded AND
         * it is not empty.
         */
        return getFilteredAssetGroups(groups);
    }

    /**
     * Get the logical group that contains the given <code>EObject</code>.
     * 
     * @param eo
     * @return <code>AbstractAssetGroup</code> that contains the given object,
     *         <b>null</b> will be returned if unable to get the group.
     */
    public static AbstractAssetGroup getParentGroup(EObject eo) {
        AbstractAssetGroup group = null;

        if (eo != null) {
            EObject eContainer = eo.eContainer();
            AbstractAssetGroup[] assetGroups = null;

            if (eContainer instanceof Package) {
                // Get the package asset groups
                assetGroups = getPackageGroups((Package) eContainer);
            } else if (eContainer instanceof Process) {
                // Get the process asset groups
                assetGroups = getProcessGroups((Process) eContainer);
            } else if (eContainer instanceof ProcessInterface) {
                assetGroups =
                        getProcessInterfaceGroups((ProcessInterface) eContainer);
            } else if (eContainer instanceof ProcessInterfaces) {
                // Ascertaining that the ProcessInterfaces element is covered.
                // Needed this because it is part of the model but doesn't have
                // a UI consideration
                Package pkg = Xpdl2ModelUtil.getPackage(eContainer);
                if (pkg != null) {
                    assetGroups = getPackageGroups(pkg);
                }
            } else if (eContainer instanceof InterfaceMethod) {
                assetGroups =
                        getInterfaceMethodGroups((InterfaceMethod) eContainer);
            }

            // If we have asset groups then check each on of them to see if any
            // holds the given EObject as it's child
            if (assetGroups != null) {
                for (AbstractAssetGroup a : assetGroups) {
                    if (a.getChildren().contains(eo)) {
                        group = a;
                        break;
                    }
                }
            }
        }

        return group;
    }

    /**
     * Get an array of <code>AbstractAssetGroup</code> for each logical group
     * associated with the given <code>Package</code>.
     * 
     * @param pkg
     * @return
     */
    public static AbstractAssetGroup[] getPackageGroups(Package pkg) {
        AbstractAssetGroup[] groups = null;
        PackageAssetGroups assetGroups = getPackageAssetGroups(pkg);

        if (assetGroups != null) {
            // Get the logical groups for the package
            groups = assetGroups.getPackageGroups();
        }

        return groups;
    }

    /**
     * Get an array of <code>AbstractAssetGroup</code> for each logical group
     * associated with the given <code>ProcessInterface</code>.
     * 
     * @param processInterface
     * @return
     */
    public static AbstractAssetGroup[] getProcessInterfaceGroups(
            ProcessInterface processInterface) {
        AbstractAssetGroup[] groups = null;

        if (processInterface != null) {
            // Get the PackageAssetGroups associated with the package of the
            // process
            ProcessInterfaces container =
                    (ProcessInterfaces) processInterface.eContainer();
            PackageAssetGroups assetGroups =
                    getPackageAssetGroups((Package) container.eContainer());
            // PackageAssetGroups assetGroups = getPackageAssetGroups((Package)
            // processInterface
            // .eContainer());

            if (assetGroups != null) {
                // Get the logical groups for the process
                groups = assetGroups.getProcessInterfaceGroup(processInterface);
            }
        }

        return groups;
    }

    /**
     * Get an array of <code>AbstractAssetGroup</code> for each logical group
     * associated with the given <code>InterfaceMethod</code>.
     * 
     * @param processInterface
     * @return
     */
    public static AbstractAssetGroup[] getInterfaceMethodGroups(
            InterfaceMethod interfaceMethod) {
        AbstractAssetGroup[] groups = null;

        if (interfaceMethod != null) {
            // Get the PackageAssetGroups associated with the package of the
            // process

            Package pkg = Xpdl2ModelUtil.getPackage(interfaceMethod);
            PackageAssetGroups assetGroups = getPackageAssetGroups(pkg);
            if (assetGroups != null) {
                // Get the logical groups for the process
                groups = assetGroups.getInterfaceMethodGroup(interfaceMethod);
            }
        }

        return groups;
    }

    /**
     * Get an array of <code>AbstractAssetGroup</code> for each logical group
     * associated with the given <code>Process</code>.
     * 
     * @param process
     * @return
     */
    public static AbstractAssetGroup[] getProcessGroups(Process process) {
        AbstractAssetGroup[] groups = null;

        if (process != null) {
            // Get the PackageAssetGroups associated with the package of the
            // process
            PackageAssetGroups assetGroups =
                    getPackageAssetGroups(process.getPackage());

            if (assetGroups != null) {
                // Get the logical groups for the process
                groups = assetGroups.getProcessGroup(process);
            }
        }

        return groups;
    }

    /**
     * Get the <code>PackageAssetGroups</code> associated with the given
     * <code>Package</code>.
     * 
     * @param pkg
     * @return
     */
    private static PackageAssetGroups getPackageAssetGroups(Package pkg) {
        PackageAssetGroups assetGroups = null;

        if (pkg != null) {
            if (packageGroupsMap.containsKey(pkg)) {
                assetGroups = packageGroupsMap.get(pkg);
            } else {
                // Create new group
                assetGroups = new PackageAssetGroups(pkg);
                // Update map
                packageGroupsMap.put(pkg, assetGroups);
            }
        }

        return assetGroups;
    }

    /**
     * Sid XPD-2516: Added filter of {@link ProcessEditorElementType} for
     * checking exclusion via processEditConfiguration/ElementTypeExclusion
     * extension point.
     * <p>
     * A group will be filtered out IF it is excluded AND it is not empty.
     * 
     * @return
     */
    private AbstractAssetGroup[] getFilteredAssetGroups(
            AbstractAssetGroup[] groups) {
        List<AbstractAssetGroup> filteredGroups =
                new ArrayList<AbstractAssetGroup>();

        if (groups != null) {

            for (AbstractAssetGroup group : groups) {
                if (group.hasChildren()
                        || !group.isAssetGroupElementTypeExcluded()) {
                    filteredGroups.add(group);
                }
            }
        }

        return filteredGroups.toArray(new AbstractAssetGroup[filteredGroups
                .size()]);
    }

}
