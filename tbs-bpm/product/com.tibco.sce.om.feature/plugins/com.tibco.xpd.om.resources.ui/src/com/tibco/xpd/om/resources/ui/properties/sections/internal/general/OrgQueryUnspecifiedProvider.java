/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.properties.sections.internal.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgQuery;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;

/**
 * @author rsomayaj
 * 
 */
public class OrgQueryUnspecifiedProvider extends AbstractScriptInfoProvider {

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getScript(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public String getScript(EObject input) {
        if (input instanceof OrgQuery) {
            return ((OrgQuery) input).getQuery();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider#getSetScriptGrammarCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.String)
     * 
     * @param editingDomain
     * @param scriptContainer
     * @param scriptGrammar
     * @return
     */
    @Override
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject scriptContainer, String scriptGrammar) {
        CompoundCommand cmd = new CompoundCommand();
        cmd.append(SetCommand.create(editingDomain,
                scriptContainer,
                OMPackage.eINSTANCE.getOrgQuery_Grammar(),
                "Unspecified")); //$NON-NLS-1$
        cmd.append(SetCommand.create(editingDomain,
                scriptContainer,
                OMPackage.eINSTANCE.getOrgQuery_Query(),
                "")); //$NON-NLS-1$
        return cmd;
    }
}
