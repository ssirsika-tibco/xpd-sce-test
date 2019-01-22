/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * implementation of base mapping rule for mappings contained in activity.
 * 
 * @author aallway
 * @since 8 Oct 2013
 */
public abstract class AbstractActivityMappingRule extends
        AbstractMappingRuleBase {

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getObjectsToValidate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     * @return
     */
    @Override
    protected Collection<? extends EObject> getObjectsToValidate(Process process) {
        return Xpdl2ModelUtil.getAllActivitiesInProc(process);
    }

    /**
     * Returns the script relevant data type map
     * <p>
     * For shared code in sub-classes to support previous method in
     * AbstractMappingRule
     * <p>
     * 
     * @param process
     * @param input
     * @return the map of script relevant data
     */
    protected Map<String, IScriptRelevantData> getScriptRelevantDataTypeMap(
            Process process, EObject input) {
        Map<String, IScriptRelevantData> scriptRelevantDataTypeMap =
                new HashMap<String, IScriptRelevantData>();

        List<IScriptRelevantData> scriptRelevantDataList =
                Collections.emptyList();
        try {
            scriptRelevantDataList =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getScriptRelevantData(getProcessDestinationList(process),
                                    getScriptType(),
                                    getScriptGrammar(Xpdl2ModelUtil
                                            .getParentActivity(input)),
                                    input,
                                    getDefaultScriptDestination());
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        if (scriptRelevantDataList != null) {
            for (IScriptRelevantData scriptRelevantData : scriptRelevantDataList) {
                if (scriptRelevantData != null
                        && scriptRelevantData.getName() != null) {
                    scriptRelevantDataTypeMap.put(scriptRelevantData.getName(),
                            scriptRelevantData);
                }
            }
        }
        return scriptRelevantDataTypeMap;
    }

}
