/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.tools;

import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.util.ProcessFlowAnalyser;

/**
 * ProcessFlowAnalyserPreProcessor
 * <p>
 * Wrapper to bridge between {@link IPreProcessor} required by the validaiton
 * tools framework and the {@link ProcessFlowAnalyser}
 * <p>
 * COntributing the process flow analyser means that it needs only be created
 * ONCE per validation per process.
 * 
 * @author aallway
 * @since 3.3
 */
public class ProcessFlowAnalyserPreProcessor implements IPreProcessor {

    private ProcessFlowAnalyser processFlowAnalyser;

    public ProcessFlowAnalyserPreProcessor(FlowContainer flowContainer) {
        processFlowAnalyser = new ProcessFlowAnalyser(true, flowContainer);
    }

    /**
     * @return the processFlowAnalyser
     */
    public ProcessFlowAnalyser getProcessFlowAnalyser() {
        return processFlowAnalyser;
    }
}
