/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * {@link CalendarReference} properties section for a {@link Process}.
 * 
 * @author njpatel
 * 
 */
public class ProcessCalendarReferenceSection extends
        AbstractCalendarReferenceSection {

    /**
     * @param eClass
     */
    public ProcessCalendarReferenceSection() {
        super(Xpdl2Package.eINSTANCE.getProcess());
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.resource.AbstractCalendarReferenceSection#getOtherElementsContainer()
     * 
     * @return
     */
    @Override
    protected OtherElementsContainer getOtherElementsContainer() {
        return getProcess();
    }

    @Override
    protected ProcessRelevantData getProcessRelevantData(String id) {
        Process process = getProcess();
        if (id != null && process != null) {
            for (ProcessRelevantData data : ProcessInterfaceUtil
                    .getAllProcessRelevantData(process)) {
                if (id.equals(data.getId())) {
                    return data;
                }
            }
        }
        return null;
    }

    /**
     * Get the input Process of this section.
     * 
     * @return
     */
    private Process getProcess() {
        EObject input = getInput();
        if (input instanceof Process) {
            return (Process) input;
        }
        return null;
    }
}
