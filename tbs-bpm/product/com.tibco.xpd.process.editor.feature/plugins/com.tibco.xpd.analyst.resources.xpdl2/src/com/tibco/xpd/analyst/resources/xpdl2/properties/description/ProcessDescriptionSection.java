/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.properties.description;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessHeader;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Description property section
 * 
 * @author njpatel
 * 
 */
public class ProcessDescriptionSection extends AbstractDescriptionSection {

    /**
     * Description property section
     */
    public ProcessDescriptionSection() {
        super(Xpdl2Package.eINSTANCE.getProcess());
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.description.AbstractDescriptionSection#getDescribedElement()
     * 
     * @return
     */
    @Override
    protected DescribedElement getDescribedElement() {
        if (getInput() instanceof Process) {
            return ((Process) getInput()).getProcessHeader();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.description.AbstractDescriptionSection#getOrCreateDescribedElement(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.common.command.CompoundCommand)
     * 
     * @param editingDomain
     * @param cmd
     * @return
     */
    @Override
    protected DescribedElement getOrCreateDescribedElement(
            EditingDomain editingDomain, CompoundCommand cmd) {

        if (getInput() instanceof Process) {
            Process process = (Process) getInput();

            if (process.getProcessHeader() != null) {
                return process.getProcessHeader();
            }

            ProcessHeader processHeader =
                    Xpdl2Factory.eINSTANCE.createProcessHeader();
            cmd.append(SetCommand.create(editingDomain,
                    process,
                    Xpdl2Package.eINSTANCE.getProcess_ProcessHeader(),
                    processHeader));

            return processHeader;
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.description.AbstractDescriptionSection#canEdit()
     * 
     * @return
     */
    @Override
    protected boolean canEdit() {
        return true;
    }

}
