/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.destinations.ui.ProcessDestinationEnvironment;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * <p>
 * <i>Created: 07 February 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public abstract class AbstractXpathScriptRule extends
        FlowContainerValidationRule {

    protected Map<String, String> getAdditionalInfoMap(ErrorMessage errorMessage) {
        Map<String, String> additionalInfoMap = new HashMap<String, String>();
        additionalInfoMap.put("LineNumber", Integer.toString(errorMessage //$NON-NLS-1$
                .getLineNumber()));
        additionalInfoMap.put("ColumnNumber", Integer.toString(errorMessage //$NON-NLS-1$
                .getColumnNumber()));
        additionalInfoMap.put("ErrorMessage", errorMessage.getErrorMessage()); //$NON-NLS-1$
        additionalInfoMap.put("ScriptContext", getScriptContext()); //$NON-NLS-1$
        return additionalInfoMap;
    }

    protected void reportError(EObject eObject, List<ErrorMessage> errorMsgList) {
        if (eObject != null) {
            Process process = Xpdl2ModelUtil.getProcess(eObject);
            if (process != null) {
                List<String> processDestinationList =
                        new ArrayList<String>(
                                DestinationUtil
                                        .getEnabledValidationDestinations(process));
                // if there are no destination environment then only we should
                // report errors.
                if (processDestinationList == null
                        || processDestinationList.size() < 1) {
                    reportIssue(eObject, errorMsgList, getErrorId());
                } else if (shouldReportXPathIssue(processDestinationList)) {
                    reportIssue(eObject, errorMsgList, getErrorId());
                }
            }
        }
    }

    protected void reportWarning(EObject eObject,
            List<ErrorMessage> errorMsgList) {
        if (eObject != null) {
            Process process = Xpdl2ModelUtil.getProcess(eObject);
            if (process != null) {
                List<String> processDestinationList =
                        new ArrayList<String>(
                                DestinationUtil
                                        .getEnabledValidationDestinations(process));
                // if there are no destination environment then only we should
                // report errors.
                if (processDestinationList == null
                        || processDestinationList.size() < 1) {
                    reportIssue(eObject, errorMsgList, getWarningId());
                } else if (shouldReportXPathIssue(processDestinationList)) {
                    reportIssue(eObject, errorMsgList, getWarningId());
                }
            }
        }
    }

    protected void reportIssue(EObject eObject,
            List<ErrorMessage> issueMsgList, String issueId) {
        for (ErrorMessage errorMessage : issueMsgList) {
            List<String> tempMsgList = getSubstitutionList(errorMessage);
            if (tempMsgList == null) {
                tempMsgList = Collections.emptyList();
            }
            Map<String, String> additionalInfoMap =
                    getAdditionalInfoMap(errorMessage);
            if (eObject instanceof ScriptInformation) {
                EObject parent = eObject.eContainer();
                if (parent instanceof DataMapping) {
                    DataMapping mapping = (DataMapping) parent;
                    String target = DataMappingUtil.getTarget(mapping);
                    additionalInfoMap
                            .put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                    target);
                }
            }
            addIssue(issueId, eObject, tempMsgList, additionalInfoMap);
        }
    }

    /**
     * This method returns whether the selected process destinations want to
     * show xpath validation errors or not
     * 
     * @param processDestinationList
     * @return <code>false</code> if all the selected process destinations are
     *         configured to NOT show xpath errors <code>true</code> otherwise
     **/
    private boolean shouldReportXPathIssue(List<String> processDestinationList) {
        if (processDestinationList != null && !processDestinationList.isEmpty()) {
            Collection<ProcessDestinationEnvironment> pdes =
                    DestinationUtil
                            .getAvailableProcessDestinationEnvironments();
            for (String processDestination : processDestinationList) {
                if (processDestination != null
                        && includePathErrorsForDestEnv(processDestination, pdes)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean includePathErrorsForDestEnv(String processDestination,
            Collection<ProcessDestinationEnvironment> pdes) {
        if (pdes != null && !pdes.isEmpty()) {
            for (ProcessDestinationEnvironment pde : pdes) {
                if (pde != null) {
                    String validationDestinationId =
                            pde.getValidationDestinationId();
                    if (validationDestinationId != null
                            && validationDestinationId
                                    .equals(processDestination)
                            && !pde.isIncludeXPathErrors()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected abstract List<String> getSubstitutionList(
            ErrorMessage errorMessage);

    protected abstract String getErrorId();

    protected abstract String getWarningId();

    protected abstract String getScriptGrammar();

    protected abstract String getScriptContext();

}
