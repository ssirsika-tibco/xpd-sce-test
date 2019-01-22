/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.navigator.BaseDeploymentSynchronizer;
import com.tibco.xpd.deploy.ui.navigator.BaseDragAndDropDeploymentSupport;
import com.tibco.xpd.deploy.ui.navigator.DragAndDropDeployment;

/**
 * @author Miguel Torres
 * 
 */
public class DragAndDropDeploymentUtil {

    private static final String DRAG_AND_DROP_DEPLOYMENT_EXTENSION =
            "dragAndDropDeployment"; //$NON-NLS-1$

    private static final String VALID_DROP_ELEMENTS = "validDropElements"; //$NON-NLS-1$

    private static final String DROP_VALIDATION = "dropValidation"; //$NON-NLS-1$

    private static final String DRAG_AND_DROP_SUPPORT_CLASS =
            "dragAndDropSupportClass"; //$NON-NLS-1$

    private static final String DROP_SUPPORT = "dropSupport"; //$NON-NLS-1$

    private static final String INSTANCEOF = "instanceof"; //$NON-NLS-1$

    private static final String VALUE = "value"; //$NON-NLS-1$

    private static final String SERVER_TYPE_ID = "serverTypeId"; //$NON-NLS-1$

    private static final String OUTPUT_SPECIAL_FOLDER_KIND =
            "outputSpecialFolderKind"; //$NON-NLS-1$

    private static final String SOURCE_SPECIAL_FOLDER_KIND =
            "sourceSpecialFolderKind"; //$NON-NLS-1$

    private static final String SOURCE_VALID_EXTENSIONS =
            "sourceValidExtensions"; //$NON-NLS-1$

    private static final String OUTPUT_VALID_EXTENSIONS =
            "outputValidExtensions"; //$NON-NLS-1$

    private static final String MULTISELECT = "multiselect"; //$NON-NLS-1$

    private static final String DEPLOYMENT_SYNCHRONIZATION =
            "deploymentSynchronization";//$NON-NLS-1$

    private static final String DEPLOY_ONLY_SYNCH_ARTIFACTS =
            "deployOnlySynchonizedArtifacts";

    private static final String DEPLOYMENT_SYNCHRONIZER =
            "deploymentSynchronizer";//$NON-NLS-1$

    private static Map<String, List<DragAndDropDeployment>> dragAndDropDeploymentMap =
            null;

    public static List<DragAndDropDeployment> getDragAndDropDeployments(
            String serverTypeId) {
        List<DragAndDropDeployment> dragAndDropDeploymentList = null;
        if (dragAndDropDeploymentMap == null) {
            DragAndDropDeploymentUtil.loadDragAndDropDeployments();
        }
        if (serverTypeId != null && dragAndDropDeploymentMap != null) {
            dragAndDropDeploymentList =
                    dragAndDropDeploymentMap.get(serverTypeId);
        }
        if (dragAndDropDeploymentList == null) {
            dragAndDropDeploymentList = new ArrayList<DragAndDropDeployment>();
        }
        return dragAndDropDeploymentList;
    }

    private static void loadDragAndDropDeployments() {
        dragAndDropDeploymentMap =
                new HashMap<String, List<DragAndDropDeployment>>();
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] elements =
                registry
                        .getConfigurationElementsFor(DeployUIActivator.PLUGIN_ID,
                                DRAG_AND_DROP_DEPLOYMENT_EXTENSION);
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                IConfigurationElement element = elements[i];
                String serverTypeId = element.getAttribute(SERVER_TYPE_ID);
                if (serverTypeId != null) {
                    DragAndDropDeployment deployment =
                            new DragAndDropDeployment(serverTypeId);
                    List<DragAndDropDeployment> dragAndDropDeploymentList =
                            dragAndDropDeploymentMap.get(serverTypeId);
                    if (dragAndDropDeploymentList == null) {
                        dragAndDropDeploymentList =
                                new ArrayList<DragAndDropDeployment>();
                    }
                    dragAndDropDeploymentList.add(deployment);
                    dragAndDropDeploymentMap.put(serverTypeId,
                            dragAndDropDeploymentList);
                    String outputSpecialFolderKind =
                            element.getAttribute(OUTPUT_SPECIAL_FOLDER_KIND);
                    deployment
                            .setOutputSpecialFolderKind(outputSpecialFolderKind);
                    String sourceSpecialFolderKind =
                            element.getAttribute(SOURCE_SPECIAL_FOLDER_KIND);
                    String[] sourceSpecialFolderKinds = new String[] {};
                    if (sourceSpecialFolderKind != null) {
                        sourceSpecialFolderKinds =
                                sourceSpecialFolderKind.split(" "); //$NON-NLS-1$
                    }
                    deployment
                            .setSourceSpecialFolderKinds(sourceSpecialFolderKinds);
                    String sourceValidExtensions =
                            element.getAttribute(SOURCE_VALID_EXTENSIONS);
                    deployment.setSourceValidExtensions(sourceValidExtensions);
                    String outputValidExtensions =
                            element.getAttribute(OUTPUT_VALID_EXTENSIONS);
                    deployment.setOutputValidExtensions(outputValidExtensions);
                    String multiselect = element.getAttribute(MULTISELECT);
                    if (multiselect != null) {
                        deployment.setMultiselect(Boolean.valueOf(multiselect));
                    }

                    // Get the dropSupport
                    IConfigurationElement[] dropSupports =
                            element.getChildren(DROP_SUPPORT);
                    if (dropSupports != null) {
                        if (dropSupports.length == 0) {
                            deployment
                                    .setDragAndDropSupport(new BaseDragAndDropDeploymentSupport());
                        } else {
                            for (int j = 0; j < dropSupports.length; j++) {
                                IConfigurationElement dropSupport =
                                        dropSupports[j];
                                if (dropSupport != null) {
                                    try {
                                        Object supportClass =
                                                dropSupport
                                                        .createExecutableExtension(DRAG_AND_DROP_SUPPORT_CLASS);
                                        if (supportClass instanceof BaseDragAndDropDeploymentSupport) {
                                            deployment
                                                    .setDragAndDropSupport((BaseDragAndDropDeploymentSupport) supportClass);
                                        }
                                    } catch (CoreException e) {
                                        // Ignore
                                    }
                                }
                            }
                        }
                    }

                    // Get the validDropElements
                    IConfigurationElement[] dropValidations =
                            element.getChildren(DROP_VALIDATION);
                    if (dropValidations != null) {
                        List<String> validDropElementList =
                                new ArrayList<String>();
                        for (int j = 0; j < dropValidations.length; j++) {
                            IConfigurationElement dropValidation =
                                    dropValidations[j];
                            if (dropValidation != null) {
                                IConfigurationElement[] validDropElements =
                                        dropValidation
                                                .getChildren(VALID_DROP_ELEMENTS);
                                if (validDropElements != null) {
                                    for (int k = 0; k < validDropElements.length; k++) {
                                        IConfigurationElement validDropElement =
                                                validDropElements[k];
                                        if (validDropElement != null) {
                                            IConfigurationElement[] instanceofs =
                                                    validDropElement
                                                            .getChildren(INSTANCEOF);
                                            for (int l = 0; l < instanceofs.length; l++) {
                                                IConfigurationElement anInstanceof =
                                                        instanceofs[l];
                                                if (anInstanceof != null) {
                                                    String value =
                                                            anInstanceof
                                                                    .getAttribute(VALUE);
                                                    if (value != null) {
                                                        validDropElementList
                                                                .add(value);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        deployment
                                .setValidDropElementList(validDropElementList);
                    }

                    // get the deploymentSynchronization
                    IConfigurationElement[] deploymentSynchronizations =
                            element.getChildren(DEPLOYMENT_SYNCHRONIZATION);
                    if (deploymentSynchronizations != null) {
                        if (deploymentSynchronizations.length == 0) {
                            deployment.setDeployOnlySynchonizedArtifacts(false);
                            deployment
                                    .setBaseDeploymentSynchronizer(new BaseDeploymentSynchronizer());
                        } else {
                            for (int j = 0; j < deploymentSynchronizations.length; j++) {
                                IConfigurationElement deploymentSynchronization =
                                        deploymentSynchronizations[j];
                                if (deploymentSynchronization != null) {
                                    String deployOnlySynchonizedArtifacts =
                                            deploymentSynchronization
                                                    .getAttribute(DEPLOY_ONLY_SYNCH_ARTIFACTS);
                                    if (deployOnlySynchonizedArtifacts != null) {
                                        deployment
                                                .setDeployOnlySynchonizedArtifacts(Boolean
                                                        .valueOf(deployOnlySynchonizedArtifacts));
                                    }
                                    String syncronizerType =
                                            deploymentSynchronization
                                                    .getAttribute(DEPLOYMENT_SYNCHRONIZER);
                                    if (syncronizerType != null
                                            && !syncronizerType.equals("")) {//$NON-NLS-1$
                                        try {
                                            Object deploymentSynchronizer =
                                                    deploymentSynchronization
                                                            .createExecutableExtension(DEPLOYMENT_SYNCHRONIZER);
                                            if (deploymentSynchronizer instanceof BaseDeploymentSynchronizer) {
                                                deployment
                                                        .setBaseDeploymentSynchronizer((BaseDeploymentSynchronizer) deploymentSynchronizer);
                                            }
                                        } catch (CoreException e) {
                                            // Ignore
                                        }
                                    } else {
                                        deployment
                                                .setBaseDeploymentSynchronizer(new BaseDeploymentSynchronizer());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static List<String> getValidModulesExtensions(String validExtensions) {
        if (validExtensions != null) {
            String validExtsStr[] = validExtensions.split(" "); //$NON-NLS-1$
            if (validExtsStr != null && validExtsStr.length > 0) {
                List<String> finalValidExt = new ArrayList<String>();
                for (int i = 0; i < validExtsStr.length; i++) {
                    String ext = validExtsStr[i];
                    if (ext != null) {
                        if (ext.startsWith(".") && ext.length() > 1) {//$NON-NLS-1$
                            finalValidExt.add(ext.substring(1));
                        } else {
                            finalValidExt.add(ext);
                        }
                    }
                }
                return finalValidExt;
            }
        }
        return null;
    }

}
