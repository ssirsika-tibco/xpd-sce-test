/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.process.xpath.model.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.internal.AbstractScriptRelevantDataProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Default implementation for xpath of the Abstract class
 * {@link AbstractScriptRelevantDataProvider}.
 * 
 * @author mtorres
 */
public class DefaultXpathRelevantDataProvider extends
        AbstractScriptRelevantDataProvider {

    protected static String XPATH_DESTINATION = "xPath1.x"; //$NON-NLS-1$

    public static final String XPATH_GRAMMAR = "XPath"; //$NON-NLS-1$

    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        Process process = getProcess();
        List<ProcessRelevantData> processDataList =
                new ArrayList<ProcessRelevantData>();
        if (getActivity() != null) {
            processDataList =
                    ProcessInterfaceUtil
                            .getAssociatedProcessRelevantDataForActivity(getActivity());
        } else if (process != null) {
            // Check if we are in scope of an embedded sub-process actvity.
            Activity embSubProcAct = null;

            EObject dataCont = getEObject();
            if (dataCont != null) {
                while (dataCont != null) {
                    if (dataCont instanceof ActivitySet) {
                        embSubProcAct =
                                Xpdl2ModelUtil
                                        .getEmbSubProcActivityForActSet(process,
                                                ((ActivitySet) dataCont)
                                                        .getId());
                        break;
                    }
                    dataCont = dataCont.eContainer();
                }
            }
            if (embSubProcAct != null) {
                processDataList.addAll(ProcessInterfaceUtil
                        .getAllAvailableRelevantDataForActivity(embSubProcAct));
            } else {
                processDataList.addAll(ProcessDataUtil
                        .getProcessRelevantData(process));
            }
        }

        if (process != null) {
            IProject project = WorkingCopyUtil.getProjectFor(process);
            if (project != null) {
                List<IScriptRelevantData> srdList =
                        ProcessUtil
                                .convertToScriptRelevantData(processDataList,
                                        project,
                                        new ArrayList<JsClassDefinitionReader>(),
                                        false);
                if (srdList != null) {
                    return srdList;
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List getComplexScriptRelevantDataList() {
        return Collections.emptyList();
    }

    protected Process getProcess() {
        if (getEObject() != null) {
            return Xpdl2ModelUtil.getProcess(getEObject());
        }
        return null;
    }

    protected Activity getActivity() {
        if (getEObject() != null) {
            return Xpdl2ModelUtil.getParentActivity(getEObject());
        }
        return null;
    }

    protected EObject getEObject() {
        if (getContext() instanceof EObject) {
            return (EObject) getContext();
        }
        return null;
    }

    protected IProject getProject() {
        if (getEObject() != null) {
            return WorkingCopyUtil.getProjectFor(getEObject());
        }
        return null;
    }

    protected List<String> getProcessDestinationList(Process process) {
        List<String> processDestList =
                new ArrayList<String>(DestinationUtil
                        .getEnabledValidationDestinations(process));

        if (processDestList == null || processDestList.isEmpty()) {
            processDestList = new ArrayList<String>();
        }
        processDestList.add(XPATH_DESTINATION);
        return processDestList;
    }

    protected List<JsClassDefinitionReader> readContributedDefinitionReaders(
            List<String> processDestList) {
        List<JsClassDefinitionReader> jsClassProvider = Collections.EMPTY_LIST;
        try {
            jsClassProvider =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getJsClassDefinitionReader(processDestList,
                                    getScriptType(),
                                    XPATH_GRAMMAR,
                                    XPATH_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return jsClassProvider;
    }
}
