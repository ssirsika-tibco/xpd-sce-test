/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.daa.internal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kupadhya
 * 
 */
public class ProjectInfo {
    private String projectName;

    private String originalProjectVersion;

    private List<String> requireBundles = new ArrayList<String>();

    /**
     * @return the requireBundles
     */
    public List<String> getRequireBundles() {
        return requireBundles;
    }

    /**
     * @param requireBundles
     *            the requireBundles to set
     */
    public void setRequireBundles(List<String> requireBundles) {
        this.requireBundles = requireBundles;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName
     *            the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the originalProjectVersion
     */
    public String getOriginalProjectVersion() {
        return originalProjectVersion;
    }

    /**
     * @param originalProjectVersion
     *            the originalProjectVersion to set
     */
    public void setOriginalProjectVersion(String originalProjectVersion) {
        this.originalProjectVersion = originalProjectVersion;
    }
}
