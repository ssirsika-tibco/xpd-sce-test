package com.tibco.xpd.bom.gen.biz;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.openarchitectureware.workflow.monitor.ProgressMonitor;

/**
 *@deprecated since 3.5
 */
public interface IOawGenerator {

    boolean run(final ProgressMonitor theMonitor,
            final Map<String, String> theParams,
            final Map<String, ?> externalSlotContents, IResource bomResource)
            throws Exception;
}