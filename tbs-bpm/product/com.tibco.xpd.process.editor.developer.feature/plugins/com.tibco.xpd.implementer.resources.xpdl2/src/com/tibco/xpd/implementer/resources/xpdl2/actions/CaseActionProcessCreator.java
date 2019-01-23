/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.processeditor.xpdl2.wizards.CaseServicePostProcessCommand;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Implementation class that does the additional stuff required for update/view
 * case data generated case action after the base process creation is done.
 * 
 * <strong>
 * <p>
 * This class basically</strong>
 * <p>
 * 1. generates data fields and formal parameters and adds project references
 * (if not set) as the data fields refer to bom types from different project
 * <p>
 * 2. generates scripts in script task and conditional gateway
 * <p>
 * 3. removes the extended attributes from page activities that get copied from
 * fragment process
 * <p>
 * 4. updates the case data for global data operation
 * 
 * 
 * @author bharge
 * @since 3 Sep 2014
 */
public class CaseActionProcessCreator extends AbstractCaseProcessCreator {

    Class caseCass;

    /**
     * @param rootCategoryId
     * @param defaultFragmentId
     * @param processType
     * @param caseClass
     */
    public CaseActionProcessCreator(String rootCategoryId,
            String defaultFragmentId, ProcessWidgetType processType,
            Class caseClass) {

        super(rootCategoryId, defaultFragmentId, processType);
        this.caseCass = caseClass;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.actions.AbstractCaseProcessCreator#postProcessCreate(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Package)
     * 
     * @param process
     * @param editingDomain
     * @param xpdlPackage
     * @return
     */
    @Override
    protected Command postProcessCreate(Process process,
            EditingDomain editingDomain, Package xpdlPackage) {

        return new CaseServicePostProcessCommand(editingDomain, process,
                xpdlPackage, getCaseClass());

    }
}
