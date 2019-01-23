/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.ui.util.NameUtil;

/**
 * @author rsomayaj
 * 
 */
public class UpdateXPathMappingCommand extends UpdateMappingsCommand {

    /** The path identifier for a web service operation. */
    public static final String WSO_ID = "wso:"; //$NON-NLS-1$

    /** The path identifier for a web service Part. */
    public static final String PART_ID = "part:"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String XPATH = "XPath"; //$NON-NLS-1$

    /**
     * 
     * @param editingDomain
     * @param mappingObject
     */
    public UpdateXPathMappingCommand(EditingDomain editingDomain,
            EObject mappingObject) {
        super(editingDomain, mappingObject);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateMappingsCommand#getFormalString(java.lang.String,
     *      java.lang.String)
     * 
     * @param operationName
     * @param dataMappingName
     * @return
     */
    @Override
    protected String getFormalString(String operationName, String dataMappingName) {
        if (operationName != null && dataMappingName != null) {
            StringBuffer formalString = new StringBuffer(WSO_ID);
            formalString.append(NameUtil.getInternalName(operationName, false));
            formalString.append("/"); //$NON-NLS-1$
            formalString.append(PART_ID);
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
        return XPATH;
    }

}
