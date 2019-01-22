/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.process.js.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author mtorres
 */
public class PerformerScriptRelevantDataProvider extends
        DefaultJavaScriptRelevantDataProvider {

    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        List<IScriptRelevantData> scriptRelevantDataList =
                new ArrayList<IScriptRelevantData>();
        ProcessRelevantData processRelevantData = getProcessRelevantData();
        if (processRelevantData != null) {
            Package pckg = Xpdl2ModelUtil.getPackage(processRelevantData);
            Process process = getProcess();
            IProject project =
                    WorkingCopyUtil.getProjectFor(processRelevantData);

            List<String> processDestinationList = new ArrayList<String>();
            List<ProcessRelevantData> dataList =
                    new ArrayList<ProcessRelevantData>();
            List<Participant> participantList = new ArrayList<Participant>();

            if (processRelevantData != null
                    && processRelevantData.eContainer() != null) {
                dataList =
                        ProcessDataUtil
                                .getProcessRelevantData(processRelevantData
                                        .eContainer());
            }

            if (null != process) {
                participantList.addAll(process.getParticipants());
            }

            if (null != pckg) {
                participantList.addAll(pckg.getParticipants());
            }

            processDestinationList = getProcessDestinationList(process);
            if (project != null) {
                List<IScriptRelevantData> srdList =
                        ProcessUtil
                                .convertToScriptRelevantData(dataList,
                                        project,
                                        readContributedDefinitionReaders(processDestinationList));
                if (!participantList.isEmpty()) {
                    srdList.addAll(ProcessUtil
                            .convertToScriptRelevantData(participantList));
                }
                if (srdList != null) {
                    for (Iterator<IScriptRelevantData> iterator =
                            srdList.iterator(); iterator.hasNext();) {
                        IScriptRelevantData scriptRelevantData =
                                (IScriptRelevantData) iterator.next();
                        if (scriptRelevantData != null) {
                            scriptRelevantDataList.add(scriptRelevantData);
                        }
                    }
                }
            }
        }
        return scriptRelevantDataList;
    }

    protected ProcessRelevantData getProcessRelevantData() {
        if (getEObject() instanceof ProcessRelevantData) {
            return (ProcessRelevantData) getEObject();
        }
        return null;
    }
    
    @Override
    protected List<String> getProcessDestinationList(Process process) {
        List<String> processDestinationList =
                super.getProcessDestinationList(process);
        if(processDestinationList == null){
            processDestinationList = new ArrayList<String>();
        }
        if (!processDestinationList
                        .contains(ProcessJsConsts.JSCRIPT_DESTINATION)) {
            processDestinationList.add(ProcessJsConsts.JSCRIPT_DESTINATION);
        }
        return processDestinationList;
    }

}
