/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public class ProcessSeparationOfDutiesSection extends
        AbstractSeparationOfDutiesSection {

    public ProcessSeparationOfDutiesSection() {
        super(Xpdl2Package.eINSTANCE.getProcess());
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.resource.
     * AbstractSeparationOfDutiesSection#getDescText()
     */
    @Override
    public String getDescText() {
        return Messages.ProcessSeparationOfDutiesSashSection_SeparationOfDutiesSectionDescription;
    }

    /**
     * @param toTest
     * @return
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        boolean ok = false;
        toTest = getBaseSelectObject(toTest);
        if (toTest instanceof Process) {
            Process process = (Process) toTest;
            if (!Xpdl2ModelUtil.isPageflow(process)) {
                ok = true;
            }
        }
        return ok;
    }

}
