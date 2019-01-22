/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.daa.internal;

import java.util.List;

import org.eclipse.core.resources.IProject;

/**
 * @author kupadhya
 * 
 */
public interface IChangeRecorder {

    /**
     * records version changes to the passed project
     * 
     * @param projectName
     * @param originalVersion
     * @param projectInfoList
     */
    public void recordProjectVersionChange(String projectName,
            String originalVersion);
    
    /**
     * 
     * @param project
     */
    public void revertProjectChanges(IProject project);

    /**
     * 
     * @return
     */
    public List<ProjectInfo> getProjectInfoList();

}
