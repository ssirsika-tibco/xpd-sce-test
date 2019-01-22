/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.daa.internal.ProjectInfo;
import com.tibco.xpd.daa.internal.util.PluginManifestHelper;

/**
 * @author kupadhya
 * 
 */
public class AmxBpmChangeRecorder implements IChangeRecorder {

    private List<ProjectInfo> projectList = new ArrayList<ProjectInfo>();

    /**
     * @see com.tibco.xpd.daa.internal.IChangeRecorder#recordProjectVersionChange(java.lang.String,
     *      java.lang.String)
     * 
     * @param projectName
     * @param originalVersion
     */
    public void recordProjectVersionChange(String projectName,
            String originalVersion) {
        List<ProjectInfo> projectInfoList = getProjectInfoList();
        ProjectInfo info = null;
        for (ProjectInfo eachProjInfo : projectInfoList) {
            if (eachProjInfo.getProjectName().equals(projectName)) {
                info = eachProjInfo;
                break;
            }
        }
        if (info == null) {
            info = new ProjectInfo();
            projectInfoList.add(info);
        }
        info.setProjectName(projectName);
        info.setOriginalProjectVersion(originalVersion);
    }

    /**
     * @see com.tibco.xpd.daa.internal.IChangeRecorder#getProjectInfoList()
     * 
     * @return
     */
    public List<ProjectInfo> getProjectInfoList() {
        return this.projectList;
    }

    /**
     * Responsible to reverting changes made to plugin projects during DAA
     * generation
     * 
     * @param project
     */
    public void revertProjectChanges(IProject project) {
        List<ProjectInfo> projectInfo = getProjectInfoList();
        for (ProjectInfo eachProjectInfo : projectInfo) {
            PluginManifestHelper
                    .revertBundleManifestTImestamptToOriginal(eachProjectInfo, this);
        }
        // just to be on safer side
        getProjectInfoList().clear();
    }

}
