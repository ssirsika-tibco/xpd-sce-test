/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.js.validation.rules;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Util methods for use in multiple data mapping rules.
 * 
 * 
 * @author aallway
 * @since 18 Feb 2016
 */
public class MappingRuleUtil {
    /**
     * Check whether the DataMapping or ScriprtInformation is related to a
     * process Data mapper Script mapping Script.
     * 
     * This util is handy as non process data mapper script mapping script rules
     * need to ignore them.
     * 
     * @return <code>true</code> if the datamapping used for web-se3rvice
     *         mapping.
     */
    public static boolean isProcessDataScriptMappingScript(
            EObject dataMappingOrScriptInfo) {

        if (Xpdl2ModelUtil.getAncestor(dataMappingOrScriptInfo,
                TaskScript.class) != null) {
            return true; /* it's in a script task. */

        } else if (Xpdl2ModelUtil.getAncestor(dataMappingOrScriptInfo,
                Audit.class) != null) {
            return true; /* It's an initiate/complete/cancel/timeout script etc */

        } else if (Xpdl2ModelUtil.getAncestor(dataMappingOrScriptInfo,
                UserTaskScripts.class) != null) {
            return true; /* It's an open/close/submit script etc */
        }

        return false;
    }

    /**
     * 
     * 
     * @param activity
     * @return <code>true</code> if activity is web-service related.
     */
    public static boolean isWebServiceActivity(Activity activity) {
        if (activity != null) {
            if (activity.getImplementation() instanceof Task) {
                Task task = (Task) activity.getImplementation();

                if (task.getTaskService() != null) {
                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(task.getTaskService(),
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());

                    if (TaskImplementationTypeDefinitions.WEB_SERVICE
                            .equals(type)) {
                        return true;
                    }

                } else if (task.getTaskSend() != null) {
                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(task.getTaskSend(),
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());

                    if (TaskImplementationTypeDefinitions.WEB_SERVICE
                            .equals(type)) {
                        return true;
                    }

                } else if (task.getTaskReceive() != null) {
                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(task.getTaskReceive(),
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());

                    if (TaskImplementationTypeDefinitions.WEB_SERVICE
                            .equals(type)) {
                        return true;
                    }
                }
            }

            if (EventObjectUtil.isWebServiceRelatedEvent(activity)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get the mapping direction for the given datamapping.
     * 
     * For DataMapper grammar ALL DataMapping elements have directio=IN (for
     * some historical reason). So in that case we need to look at the
     * ScriptDatamapper element instead.
     * 
     * @param dataMappingOrScriptInfo
     * 
     * @return mapping direction
     */
    public static DirectionType getMappingDirection(DataMapping dataMapping) {
        ScriptDataMapper sdm =
                (ScriptDataMapper) Xpdl2ModelUtil.getAncestor(dataMapping,
                        ScriptDataMapper.class);

        if (sdm != null) {
            return sdm.getMappingDirection();
        }

        /*
         * For non-dataMapper, the direction is on the DataMapping /
         * ScriptInformation element
         */
        return dataMapping.getDirection();
    }

    /**
     * Get the mapping direction for the given scriptInformation.
     * 
     * For DataMapper grammar ALL DataMapping elements have directio=IN (for
     * some historical reason). So in that case we need to look at the
     * ScriptDatamapper element instead.
     * 
     * @param dataMappingOrScriptInfo
     * 
     * @return mapping direction
     */
    public static DirectionType getMappingDirection(
            ScriptInformation scriptInformation) {
        ScriptDataMapper sdm =
                (ScriptDataMapper) Xpdl2ModelUtil
                        .getAncestor(scriptInformation, ScriptDataMapper.class);

        if (sdm != null) {
            return sdm.getMappingDirection();
        }

        /*
         * For non-dataMapper, the direction is on the ScriptInformation element
         * (if not present then means IN
         */
        return scriptInformation.getDirection() != null ? scriptInformation
                .getDirection() : DirectionType.IN_LITERAL;
    }
}
