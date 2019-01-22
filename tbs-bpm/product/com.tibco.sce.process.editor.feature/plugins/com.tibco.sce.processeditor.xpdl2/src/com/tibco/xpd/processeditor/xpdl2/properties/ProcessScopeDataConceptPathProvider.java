/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Concept path item provider for Process data for all data in scope of a given
 * process (useful for declarative mapping situations made at process level
 * rather than activity level).
 * 
 * @author kthombar
 * @since 27-Sep-2013
 */
public class ProcessScopeDataConceptPathProvider extends
        AbstractProcessDataConceptPathProvider {

    /**
     * @param direction
     */
    public ProcessScopeDataConceptPathProvider() {
        super(MappingDirection.IN);

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessDataConceptPathProvider#getConceptPaths(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    protected ConceptPath[] getConceptPaths(Object inputElement) {
        List<ConceptPath> processData = new ArrayList<ConceptPath>();
        Process process = (Process) inputElement;
        if (process != null) {
            createConceptPath(processData,
                    ProcessInterfaceUtil.getAllDataDefinedInProcess(process));

        }
        ConceptPath[] conceptPath =
                processData.toArray(new ConceptPath[processData.size()]);
        Arrays.sort(conceptPath, new ConceptPathComparator());

        return conceptPath;
    }

    /**
     * Create a Concept Path of the Process data.
     * 
     * @param processData
     * @param allDataDefinedInProcess
     */
    private void createConceptPath(List<ConceptPath> processData,
            Collection<ProcessRelevantData> allDataDefinedInProcess) {

        for (ProcessRelevantData pRD : allDataDefinedInProcess) {
            Class clss = ConceptUtil.getConceptClass(pRD);
            ConceptPath path = new ConceptPath(pRD, clss);
            processData.add(path);
        }

    }

}
