/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.preCommit;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.ui.util.NameUtil;

/**
 * Command to update the mappings for process activities and interface methods.
 * 
 * @author rsomayaj
 * 
 */
public class UpdateJavaScriptMappingsCommand extends UpdateMappingsCommand {

    /**
     * 
     */
    private static final String SCRIPTGRAMMAR_JAVASCRIPT = "JavaScript"; //$NON-NLS-1$

    public UpdateJavaScriptMappingsCommand(EditingDomain editingDomain,
            EObject obj) {
        super(editingDomain, obj);
    }

    @Override
    protected String getFormalString(String operationName, String dataMappingName) {
        if (operationName != null && dataMappingName != null) {
            StringBuffer formalString = new StringBuffer();
            formalString.append(NameUtil
                    .getInternalName(dataMappingName, false));
            return formalString.toString();
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateMappingsCommand#getScriptGrammar()
     * 
     * @return
     */
    @Override
    protected String getScriptGrammar() {
        return SCRIPTGRAMMAR_JAVASCRIPT;
    }

}