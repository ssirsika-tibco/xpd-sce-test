/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wm.tasklibrary.editor.resources;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.DataFieldGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ParticipantGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.TypeDeclarationGroup;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Logical project explorer folders for Task Libraries.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryAssetGroups {

    /**
     * <code>Package</code> owner of these groups
     */
    private final Process ownerTaskLibrary;

    /**
     * The groups under the <code>Package</code>
     */
    private final AbstractAssetGroup[] taskLibraryGroups;

    /**
     * Map to hold the <code>TaskLibraryAssetGroups</code> associated with a
     * <code>Process</code>.
     */
    private static Map<Process, TaskLibraryAssetGroups> taskLibraryToAssetGroupsMap =
            new HashMap<Process, TaskLibraryAssetGroups>();

    public TaskLibraryAssetGroups(Process owner) {
        this.ownerTaskLibrary = owner;

        if (owner != null) {
            // Define the Package groups
            taskLibraryGroups =
                    new AbstractAssetGroup[] {
                            new DataFieldGroup(ownerTaskLibrary),
                            new ParticipantGroup(ownerTaskLibrary),
                            new TypeDeclarationGroup(ownerTaskLibrary
                                    .getPackage()),
                            new TasksGroup(ownerTaskLibrary) };
        } else {
            taskLibraryGroups = null;
        }
    }

    /**
     * Get the logical groups for the owner <code>Package</code>.
     * 
     * @return Array of <code>AbstractAssetGroup</code> for each expected
     *         logical group under a <code>Package</code>. If no owner was
     *         defined then <b>null</b> will be returned.
     */
    public AbstractAssetGroup[] getTaskLibraryGroups() {
        return taskLibraryGroups;
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

            Process findTaskLib = null;

            // Most things are somewhere under process but some may be in
            // package (like TypeDeclarations).
            while (eContainer != null) {
                if (eContainer instanceof Process
                        || eContainer instanceof Package) {
                    break;
                }
                eContainer = eContainer.eContainer();
            }

            if (eContainer instanceof Process) {
                findTaskLib = (Process) eContainer;
            } else if (eContainer instanceof Package) {
                findTaskLib =
                        TaskLibraryFactory.INSTANCE
                                .getTaskLibrary((Package) eContainer);
            }

            if (findTaskLib != null) {

                AbstractAssetGroup[] assetGroups =
                        getTaskLibraryGroups(findTaskLib);

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
    public static AbstractAssetGroup[] getTaskLibraryGroups(Process taskLibrary) {
        AbstractAssetGroup[] groups = null;
        TaskLibraryAssetGroups assetGroups =
                getTaskLibraryAssetGroups(taskLibrary);

        if (assetGroups != null) {
            // Get the logical groups for the package
            groups = assetGroups.getTaskLibraryGroups();
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
    private static TaskLibraryAssetGroups getTaskLibraryAssetGroups(
            Process taskLibrary) {
        TaskLibraryAssetGroups assetGroups = null;

        if (taskLibrary != null) {
            if (taskLibraryToAssetGroupsMap.containsKey(taskLibrary)) {
                assetGroups = taskLibraryToAssetGroupsMap.get(taskLibrary);
            } else {
                // Create new set of groups
                assetGroups = new TaskLibraryAssetGroups(taskLibrary);
                // Update map
                taskLibraryToAssetGroupsMap.put(taskLibrary, assetGroups);
            }
        }

        return assetGroups;
    }

}
