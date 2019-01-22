/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.subprocess.datamapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.datamapper.common.ProcessDataMapperConceptPathProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.SubProcUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;

/**
 * Content contributor for Sub-Process Data mapper's mappings.
 * 
 * @author sajain
 * @since Oct 28, 2015
 */
public class SubProcessDataMapperContentProvider extends
        ProcessDataMapperConceptPathProvider {

    /**
     * Mapping direction.
     */
    private MappingDirection direction;

    /**
     * Content contributor for Sub-Process Data mapper's 'SubProcess to Process'
     * mapping use case.
     * 
     * @param direction
     */
    public SubProcessDataMapperContentProvider(MappingDirection direction) {

        this.direction = direction;
    }

    /**
     * @see com.tibco.xpd.n2.pe.datamapper.ProcessDataMapperConceptPathProvider#getInScopeProcessData(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected List<ProcessRelevantData> getInScopeProcessData(Activity activity) {

        if (activity != null) {

            if (activity.getImplementation() instanceof SubFlow) {

                /*
                 * Get the process/process interface being called.
                 */
                if (null != TaskObjectUtil.getSubProcessOrInterface(activity)) {

                    Map<ConceptPath, Boolean> cpMap =
                            new HashMap<ConceptPath, Boolean>();

                    /*
                     * Get the sub process/interface parameters in scope.
                     */

                    cpMap.putAll(SubProcUtil
                            .getSubProcessParametersAndMandatory(TaskObjectUtil
                                    .getSubProcessOrInterface(activity),
                                    direction));

                    List<ConceptPath> allConceptPaths =
                            new ArrayList<ConceptPath>();

                    allConceptPaths.addAll(cpMap.keySet());

                    List<ProcessRelevantData> allPrd =
                            new ArrayList<ProcessRelevantData>();

                    if (SubProcUtil.isAsyncCall(activity)
                            && MappingDirection.OUT.equals(direction)) {

                        FormalParameter fp =
                                StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER;

                        allPrd.add(fp);

                    } else {

                        for (ConceptPath eachConceptPath : allConceptPaths) {

                            if (eachConceptPath.getItem() instanceof ProcessRelevantData) {

                                allPrd.add((ProcessRelevantData) (eachConceptPath
                                        .getItem()));

                            }
                        }

                    }

                    return allPrd;
                }
            }
        }

        return new ArrayList<ProcessRelevantData>();
    }

}
