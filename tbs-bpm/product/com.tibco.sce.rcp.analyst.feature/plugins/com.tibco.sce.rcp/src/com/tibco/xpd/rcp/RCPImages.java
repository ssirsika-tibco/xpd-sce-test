/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp;

/**
 * Images used by actions in the ribbon.
 * 
 * @author njpatel
 * 
 */
public enum RCPImages {

    TIBCO32("32x32/tibco.png", null), //$NON-NLS-1$
    TIBCO16("16x16/tibco.png", "16x16/tibco_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    GANTT("16x16/gantt.gif", null), //$NON-NLS-1$
    PASTE("32x32/paste.png", "32x32/paste_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    ZOOM_IN("32x32/zoomin.png", "32x32/zoomin_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    ZOOM_OUT("32x32/zoomout.png", "32x32/zoomout_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    ZOOM_TOFIT("16x16/zoomtofit.png", "16x16/zoomtofit_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    ZOOM_WIDTH("16x16/zoomwidth.png", "16x16/zoomwidth_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    ZOOM_HEIGHT("16x16/zoomheight.png", "16x16/zoomheight_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    SEARCH("search.png", "search_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    PROCESS("process.png", "process_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    BOM("bom.png", "bom_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    BOM_16("16x16/bom.png", null), //$NON-NLS-1$ 
    OM("om.png", "om_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    OM_16("16x16/om.gif", null), //$NON-NLS-1$ 
    RENAME("16x16/rename.png", "16x16/rename_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    DELETE("16x16/delete.png", "16x16/delete_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    EXPORT("16x16/export.gif", "16x16/export_disabled.gif"), //$NON-NLS-1$ //$NON-NLS-2$
    IMPORT("16x16/import.gif", "16x16/import_disabled.gif"), //$NON-NLS-1$ //$NON-NLS-2$
    NIMBUS("16x16/nimbus.png", null), //$NON-NLS-1$ 
    PUBLISH("16x16/publish.png", "16x16/publish_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    HELP("32x32/help.png", null), //$NON-NLS-1$
    INFO("16x16/info.png", null), //$NON-NLS-1$
    CONFIG("16x16/config.png", null), //$NON-NLS-1$
    BUSINESS_PROCESS("16x16/processBusiness.png", null), //$NON-NLS-1$
    PAGEFLOW_PROCESS("16x16/processPageflow.png", null), //$NON-NLS-1$
    BUSINESS_SERVICE("16x16/ProcessBusinessService.png", null), //$NON-NLS-1$
    CASE_SERVICE("16x16/CaseService.png", null), //$NON-NLS-1$
    PROCESS_INTERFACE("16x16/processInterface.gif", null), //$NON-NLS-1$
    SERVICE_PROCESS("16x16/ServiceProcess.png", null), //$NON-NLS-1$
    SERVICE_PROCESS_INTERFACE("16x16/ServiceProcessInterface.png", null), //$NON-NLS-1$
    ADD("16x16/add.gif", null), //$NON-NLS-1$
    PREFERENCES("16x16/preferences.png", null), //$NON-NLS-1$
    PROJECT("16x16/project.png", null), //$NON-NLS-1$
    RUNSIMULATION("16x16/runSimulation.png", "16x16/runSimulation_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    COMPARESIMULATION(
            "16x16/compareSimulation.gif", "16x16/compareSimulation_disabled.gif"), //$NON-NLS-1$ //$NON-NLS-2$
    PREPARESIMULATION(
            "16x16/prepareSimulation.png", "16x16/prepareSimulation_disabled.png"), //$NON-NLS-1$ //$NON-NLS-2$
    NEW_PROJECT("16x16/newProject.png", "16x16/newProject.png"), //$NON-NLS-1$ //$NON-NLS-2$
    NEW_BOM_MODEL("16x16/newBOMModel.png", "16x16/newBOMModel.png"), //$NON-NLS-1$ //$NON-NLS-2$
    NEW_ORG_MODEL("16x16/newOrgModel.png", "16x16/newOrgModel.png"), //$NON-NLS-1$ //$NON-NLS-2$
    NEW_ORGANIZATION("16x16/newOrganization.png", "16x16/newOrganization.png"), //$NON-NLS-1$ //$NON-NLS-2$
    NEW_PROCESS_PACKAGE(
            "16x16/newProcessPackage.png", "16x16/newProcessPackage.png"), //$NON-NLS-1$ //$NON-NLS-2$
    SVN("16x16/svn.png", null), //$NON-NLS-1$
    MAA("16x16/maa.png", null), //$NON-NLS-1$
    REFRESH("16x16/refresh.gif", null); //$NON-NLS-1$

    private static final String ICONS = "icons/"; //$NON-NLS-1$

    private final String imgPath;

    private final String disabledImgPath;

    private RCPImages(String imgPath, String disabledImgPath) {
        this.imgPath = imgPath;
        this.disabledImgPath = disabledImgPath;
    }

    /**
     * Get the path of the image.
     * 
     * @return
     */
    public String getPath() {
        return ICONS + imgPath;
    }

    /**
     * Get the path of the disabled image.
     * 
     * @return
     */
    public String getDisabledPath() {
        return ICONS + disabledImgPath;
    }

    @Override
    public String toString() {
        return getPath();
    }

}
