/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import com.tibco.xpd.processeditor.xpdl2.inlineSubProcess.AnalyseInlineSubProcesses;
import com.tibco.xpd.processwidget.adapters.DiagramModelImageProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author aallway
 *
 */
public class RefactorIndiSubInlineInfo {

    public Activity subProcessTask = null;
    
    public Process subProcess = null;
    
    public boolean inlineAsEmbeddedSubProc = false;

    public DiagramModelImageProvider callingProcessImageProvider;

    public DiagramModelImageProvider subProcessImageProvider;
    
    public AnalyseInlineSubProcesses inlineSubProcAnalysis;
}
