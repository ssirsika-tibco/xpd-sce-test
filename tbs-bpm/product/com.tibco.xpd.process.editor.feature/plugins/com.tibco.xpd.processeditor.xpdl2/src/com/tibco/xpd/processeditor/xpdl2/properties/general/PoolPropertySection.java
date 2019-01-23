/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author aallway
 *
 */
public class PoolPropertySection extends AbstractNamedDiagramObjectSection {

    public PoolPropertySection() {
        super(Xpdl2Package.eINSTANCE.getPool());    
        setInstrumentationPrefixName("Pool"); //$NON-NLS-1$
    }   

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeCreateControls(org.eclipse.swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     *
     * @param parent
     * @param toolkit
     */
    @Override
    protected void objectTypeCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        // Pools only have a name control at the moment.
        return; 
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeGetCommand(java.lang.Object)
     *
     * @param obj
     * @return
     */
    @Override
    protected Command objectTypeGetCommand(Object obj) {
        // Pools only have a name control at the moment.
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeGetDescriptor()
     *
     * @return
     */
    @Override
    protected String objectTypeGetDescriptor() {
        return Messages.PoolPropertySection_PoolTypeName_label;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeRefresh()
     *
     */
    @Override
    protected void objectTypeRefresh() {
        // Pools only have a name control at the moment.
        return; 
    }

}
