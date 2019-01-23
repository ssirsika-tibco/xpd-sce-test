/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.analyst.resources.xpdl2.propertytesters.XpdlFileContentPropertyTester;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author aallway
 * 
 */
public class LanePropertySection extends AbstractNamedDiagramObjectSection {

    public LanePropertySection() {
        super(Xpdl2Package.eINSTANCE.getLane());
        setInstrumentationPrefixName("Lane"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     */
    @Override
    protected void objectTypeCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        // Lanes only have a name control at the moment.
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
        // Lanes only have a name control at the moment.
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeGetDescriptor()
     * 
     * @return
     */
    @Override
    protected String objectTypeGetDescriptor() {
        return Messages.LanePropertySection_LaneTypeName_label;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.util.AbstractNamedDiagramObjectSection#objectTypeRefresh()
     * 
     */
    @Override
    protected void objectTypeRefresh() {
        // Lanes only have a name control at the moment.
        return;
    }

    @Override
    public boolean select(Object toTest) {
        if (super.select(toTest)) {
            EObject eo = getBaseSelectObject(toTest);

            // Prevent insertion of filler section for task library lanes (which
            // have list of tasks.
            if (!XpdlFileContentPropertyTester.isTasksFileContent(eo)) {
                return true;
            }
        }
        return false;
    }
}
