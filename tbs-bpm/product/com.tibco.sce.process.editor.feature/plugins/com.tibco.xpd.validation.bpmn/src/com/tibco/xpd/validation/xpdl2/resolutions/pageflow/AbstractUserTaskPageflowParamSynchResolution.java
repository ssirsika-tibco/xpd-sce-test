/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions.pageflow;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.validation.bpmn.rules.PageflowUserTaskRule;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * REsolution base for user task / pageflow parameter synchronisation
 * resolutions.
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractUserTaskPageflowParamSynchResolution extends
        AbstractWorkingCopyResolution {

    /**
     * Get parameter from given marker (that should have additional info as
     * defined by {@link PageflowUserTaskRule}.
     * 
     * @param marker
     * 
     * @return Pageflow FormalParameter or null if not found.
     */
    protected FormalParameter getPageflowParameterFromMarker(IMarker marker) {
        return getPageflowParameter(getPageflowProcessFromMarker(marker),
                getPageflowParamNameFromMarker(marker));
    }

    /**
     * Get given parameter from given process.
     * 
     * @param pageflowProcess
     * @param paramName
     * @return Pageflow FormalParameter or null if not found.
     */
    protected FormalParameter getPageflowParameter(Process pageflowProcess,
            String paramName) {
        if (pageflowProcess != null && paramName != null
                && paramName.length() > 0) {
            List<FormalParameter> params =
                    ProcessInterfaceUtil
                            .getAllFormalParameters(pageflowProcess);

            for (FormalParameter p : params) {
                if (paramName.equals(p.getName())) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * Get the pageflow process from additional info in marker (as added by
     * {@link PageflowUserTaskRule}
     * 
     * @param marker
     * @return pageflow process or null if cannot access.
     */
    protected Process getPageflowProcessFromMarker(IMarker marker) {
        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
        if (addInfo != null) {
            String pageflowProcessId =
                    addInfo
                            .getProperty(PageflowUserTaskRule.EXTRA_INFO_PAGEFLOWID);
            if (pageflowProcessId != null && pageflowProcessId.length() > 0) {
                return Xpdl2WorkingCopyImpl.locateProcess(pageflowProcessId);
            }
        }
        return null;
    }

    /**
     * Get the pageflow parameter name additional info in marker (as added by
     * {@link PageflowUserTaskRule}
     * 
     * @param marker
     * 
     * @return pageflow param name or null if cannot access.
     */
    protected String getPageflowParamNameFromMarker(IMarker marker) {
        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
        if (addInfo != null) {
            String paramName =
                    addInfo
                            .getProperty(PageflowUserTaskRule.EXTRA_INFO_PARAMNAME);

            if (paramName != null && paramName.length() > 0) {
                return paramName;
            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Get the named associated parameter from the given pageflow process
     * startevent.
     * 
     * @param pageflowProcess
     * @param paramName
     * 
     * @return Associated parameter or null if not found.
     */
    protected AssociatedParameter getPageflowAssociatedParam(
            Process pageflowProcess, String paramName) {
        Activity pageflowStart =
                SubProcUtil.getSubProcessStartEvent(pageflowProcess);

        if (pageflowStart != null) {
            return getAssociatedParameter(pageflowStart, paramName);
        }
        return null;
    }

    /**
     * Get the named associated parameter from the given activity.
     * 
     * @param activity
     * @param paramName
     * 
     * @return Associated parameter or null if not found.
     */
    protected AssociatedParameter getAssociatedParameter(Activity activity,
            String paramName) {
        AssociatedParameters associations =
                getActivityAssociatedParameters(activity);
        if (associations != null) {
            for (AssociatedParameter assocParam : associations
                    .getAssociatedParameter()) {
                if (paramName.equals(assocParam.getFormalParam())) {
                    return assocParam;
                }
            }
        }

        return null;
    }

    /**
     * @param activity
     * 
     * @return Activity's associated params.
     */
    protected AssociatedParameters getActivityAssociatedParameters(
            Activity activity) {
        AssociatedParameters associations =
                (AssociatedParameters) Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());
        return associations;
    }

    /**
     * @param activity
     * 
     * @return Activity's associated params.
     */
    protected AssociatedParameters getOrCreateActivityAssociatedParameters(
            Activity activity, EditingDomain editingDomain, CompoundCommand cmd) {
        AssociatedParameters associations =
                (AssociatedParameters) Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());
        if (associations == null) {
            associations =
                    XpdExtensionFactory.eINSTANCE.createAssociatedParameters();

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    activity,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_AssociatedParameters(),
                    associations));
        }
        return associations;
    }

    /**
     * @param activity
     * @return All data that is in scope of activity (regardless of whether it
     *         is explicitly associated in interface.
     */
    protected Map<String, ProcessRelevantData> getInScopeActivityData(
            Activity activity) {
        Map<String, ProcessRelevantData> allInScopeUserTaskData =
                ProcessDataUtil.getDataMap(ProcessInterfaceUtil
                        .getAllAvailableRelevantDataForActivity(activity));
        return allInScopeUserTaskData;
    }

}
