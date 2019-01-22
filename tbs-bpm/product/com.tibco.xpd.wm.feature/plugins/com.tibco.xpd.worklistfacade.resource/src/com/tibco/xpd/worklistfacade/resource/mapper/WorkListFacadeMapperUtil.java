/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkItemAttributes;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.worklistfacade.resource.workingcopy.WorkListFacadeWorkingCopy;
import com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping;
import com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class for supporting WorkListFacade mapper , provides utility methods
 * to get existing work List Facade file and read Display Labels from Work List
 * Facade file available in the workspace.
 * 
 * @author aprasad
 * @since 11-Nov-2013
 */
public class WorkListFacadeMapperUtil {

    /**
     * Returns the first Facade file available in the workspace. Although having
     * multiple Facade files is not supported, even if it exists we pick the
     * first available.
     * 
     * @return First WorkListFacade file available in the workspace, null if no
     *         WLF file is available in workspace.
     */
    public static IResource getFirstWorkListFacadeFileInWorkspace() {

        // return the first file found.
        List<IResource> allWorkListFacadeFilesInWorkspace =
                getAllWorkListFacadeFilesInWorkspace();

        return (allWorkListFacadeFilesInWorkspace.size() > 0) ? allWorkListFacadeFilesInWorkspace
                .get(0) : null;

    }

    /**
     * Although multiple Work List Facade files are not allowed and errors
     * markers are displayed if exists, this method returns list of all Work
     * List Facade files exisiting in the workspace.
     * 
     * @return list of Work List Facade Files existing in the workspace.Returns
     *         empty list if no Work List Facade File exists in the workspace.
     */
    public static List<IResource> getAllWorkListFacadeFilesInWorkspace() {
        List<IResource> allFacadeFiles = new ArrayList<IResource>();
        // All Studio projects
        IProject[] allStudioProjects = ProjectUtil.getAllStudioProjects();

        for (IProject iProject : allStudioProjects) {
            // fetch Work List Facade files in these projects
            ArrayList<IResource> facadeFiles =
                    SpecialFolderUtil
                            .getResourcesInSpecialFolderOfKind(iProject,
                                    WorkListFacadeResourcePlugin.WLF_SPECIAL_FOLDER_KIND,
                                    WorkListFacadeResourcePlugin.WLF_FILE_EXTENSION);

            if (facadeFiles != null && !facadeFiles.isEmpty()) {
                // collect all the files found.
                allFacadeFiles.addAll(facadeFiles);
            }
        }

        return allFacadeFiles;
    }

    /**
     * Returns the Display Attribute entry {@link WorkItemAttribute} from the
     * Work List Facade file for the given Physical Work Item Attribute
     * {@link Property}. Returns <b>null</b> when either no entry for the given
     * Physical Work Item Attribute is found in the Work List Facade file or
     * there is no Work List Facade file in the workspace.
     * 
     * @param property
     * @return {@link WorkItemAttribute}, Display Attribute entry from the Work
     *         List Facade file for the given Physical Work Item Attribute
     *         {@link Property}.
     */
    public static WorkItemAttribute getDisplayAttributeFromFacade(
            Property property) {
        String name = property == null ? "" : property.getName(); //$NON-NLS-1$
        if (name != null && !name.isEmpty()) {
            // get facade file available in workspace
            IResource facadeFileInWorkspace =
                    WorkListFacadeMapperUtil
                            .getFirstWorkListFacadeFileInWorkspace();

            // when file is found check and return the DisplayLabel for
            // attribute with given name
            if (facadeFileInWorkspace != null) {
                // facade working Copy
                WorkingCopy workingCopy =
                        WorkingCopyUtil.getWorkingCopy(facadeFileInWorkspace);

                if (workingCopy instanceof WorkListFacadeWorkingCopy) {

                    WorkListFacadeWorkingCopy wlfWCopy =
                            (WorkListFacadeWorkingCopy) workingCopy;
                    // Get Attributes
                    WorkItemAttributes workItemAttributes =
                            wlfWCopy.getWorkListFacade()
                                    .getWorkItemAttributes();

                    if (workItemAttributes != null) {
                        // Check if this attribute has a Display Label Defined.
                        for (WorkItemAttribute workItemAttrib : workItemAttributes
                                .getWorkItemAttribute()) {

                            if (name.equals(workItemAttrib.getName())) {
                                // return Defined Display Label.
                                return workItemAttrib;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns a {@link Collection} of {@link ProcessRelevantData} which is
     * mapped to Physical Work Item Attribute.
     * 
     * @param process
     *            , {@link Process}
     * @return {@link Collection} of {@link ProcessRelevantData} , all Process
     *         Data which is mapped to a Physical Work Item Attribute.
     */
    public static Collection<ProcessRelevantData> getAllProcessDataMappedToPhysicalAttribute(
            Process process) {
        Object otherElement =
                Xpdl2ModelUtil
                        .getOtherElement(process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ProcessDataWorkItemAttributeMappings());
        // Get all Process Relevant Data
        Map<String, ProcessRelevantData> allProcessData =
                ProcessDataUtil.getDataMap(ProcessInterfaceUtil
                        .getAllDataDefinedInProcess(process));
        // To collect Mapped Process Data.
        Collection<ProcessRelevantData> mappedProcessData =
                new ArrayList<ProcessRelevantData>();

        if (otherElement != null && allProcessData != null) {
            // Mappings Exist
            ProcessDataWorkItemAttributeMappings processDataWIAttribMappings =
                    (ProcessDataWorkItemAttributeMappings) otherElement;

            // get mappings list for facade files
            EList<DataWorkItemAttributeMapping> dataWIAttribMappings =
                    processDataWIAttribMappings
                            .getDataWorkItemAttributeMapping();

            for (DataWorkItemAttributeMapping dataWIAttribMapping : dataWIAttribMappings) {

                String processData = dataWIAttribMapping.getProcessData();
                String[] sourcePath = processData.split("\\."); //$NON-NLS-1$
                // This will handle the complex Type [parent.child.nameText]
                // as well as the Primitive type [nameText]
                ProcessRelevantData prd = null;
                if (sourcePath.length > 0) {
                    prd = allProcessData.get(sourcePath[0]);
                }
                if (prd != null) {
                    mappedProcessData.add(prd);
                }
            }

        }
        return mappedProcessData;
    }

    public static Collection<ProcessRelevantData> getAllUnAssociatedProcessDataForActivity(
            Activity activity, Collection<ProcessRelevantData> allProcessData) {

        Collection<ProcessRelevantData> unAssociatedData =
                new HashSet<ProcessRelevantData>();

        Collection<ActivityInterfaceData> activityInterfaceData =
                ActivityInterfaceDataUtil.getActivityInterfaceData(activity);

        for (ProcessRelevantData procData : allProcessData) {
            boolean associated = false;
            // check if this is associated with the User Task
            for (ActivityInterfaceData activityInterfaceData2 : activityInterfaceData) {
                if (procData.equals(activityInterfaceData2.getData())) {
                    associated = true;
                }
            }
            // raise issue if it is not associated with the User Task.
            if (!associated) {
                unAssociatedData.add(procData);
            }

        }
        return unAssociatedData;
    }
}
