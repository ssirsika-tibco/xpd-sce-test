/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.navigator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to wrap the Drag and drop deployment settings for a server
 * 
 * 
 * @author Miguel Torres
 * 
 */
public class DragAndDropDeployment {

    private final String serverTypeId;

    private String outputSpecialFolderKind;

    private String outputValidExtensions;

    private boolean multiselect;

    private String[] sourceSpecialFolderKinds;

    private String sourceValidExtensions;

    private List<String> validDropElementList;

    private BaseDragAndDropDeploymentSupport dragAndDropSupport;

    private boolean deployOnlySynchonizedArtifacts;

    public DragAndDropDeployment(String serverTypeId) {
        this.serverTypeId = serverTypeId;
    }

    private BaseDeploymentSynchronizer baseDeploymentSynchronizer;

    public String getOutputValidExtensions() {
        return outputValidExtensions;
    }

    public void setOutputValidExtensions(String outputValidExtensions) {
        this.outputValidExtensions = outputValidExtensions;
    }

    public String getSourceValidExtensions() {
        return sourceValidExtensions;
    }

    public void setSourceValidExtensions(String sourceValidExtensions) {
        this.sourceValidExtensions = sourceValidExtensions;
    }

    public String[] getSourceSpecialFolderKinds() {
        return sourceSpecialFolderKinds;
    }

    public void setSourceSpecialFolderKinds(String[] sourceSpecialFolderKinds) {
        this.sourceSpecialFolderKinds = sourceSpecialFolderKinds;
    }

    public List<String> getValidDropElementList() {
        if (validDropElementList == null) {
            validDropElementList = new ArrayList<String>();
        }
        return validDropElementList;
    }

    public String getOutputSpecialFolderKind() {
        return outputSpecialFolderKind;
    }

    public void setOutputSpecialFolderKind(String outputSpecialFolderKind) {
        this.outputSpecialFolderKind = outputSpecialFolderKind;
    }

    public boolean isMultiselect() {
        return multiselect;
    }

    public void setMultiselect(boolean multiselect) {
        this.multiselect = multiselect;
    }

    public void setValidDropElementList(List<String> validDropElementList) {
        this.validDropElementList = validDropElementList;
    }

    public String getServerTypeId() {
        return serverTypeId;
    }

    public BaseDragAndDropDeploymentSupport getDragAndDropSupport() {
        return dragAndDropSupport;
    }

    public void setDragAndDropSupport(
            BaseDragAndDropDeploymentSupport dragAndDropSupport) {
        if (dragAndDropSupport != null) {
            dragAndDropSupport.setParentDragAndDropDeployment(this);
        }
        this.dragAndDropSupport = dragAndDropSupport;
    }

    public BaseDeploymentSynchronizer getBaseDeploymentSynchronizer() {
        return baseDeploymentSynchronizer;
    }

    public void setBaseDeploymentSynchronizer(
            BaseDeploymentSynchronizer baseDeploymentSynchronizer) {
        this.baseDeploymentSynchronizer = baseDeploymentSynchronizer;
    }

    public boolean isDeployOnlySynchonizedArtifacts() {
        return deployOnlySynchonizedArtifacts;
    }

    public void setDeployOnlySynchonizedArtifacts(
            boolean deployOnlySynchonizedArtifacts) {
        this.deployOnlySynchonizedArtifacts = deployOnlySynchonizedArtifacts;
    }

}
