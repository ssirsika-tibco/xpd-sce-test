/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.script.ui.internal.IScriptDetailsProvider;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author nwilson
 */
public interface IProcessScriptProvider extends IScriptDetailsProvider {
    /**
     * 
     * @param input
     * @return
     */
    Process getProcess(EObject input);

}
