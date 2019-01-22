/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author bharge
 * 
 */
public class ProcessRetainFamiliarSection extends AbstractRetainFamiliarSection {

    /**
     * @param activityOrProcessClass
     */
    public ProcessRetainFamiliarSection() {
        super(Xpdl2Package.eINSTANCE.getProcess());
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.resource.
     * AbstractRetainFamiliarSection#getDescText()
     */
    @Override
    public String getDescText() {
        return Messages.ProcessRetainFamiliarSection_RetainFamiliarSectionDescription;
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
            ok = true;
        }
        return ok;
    }
}
