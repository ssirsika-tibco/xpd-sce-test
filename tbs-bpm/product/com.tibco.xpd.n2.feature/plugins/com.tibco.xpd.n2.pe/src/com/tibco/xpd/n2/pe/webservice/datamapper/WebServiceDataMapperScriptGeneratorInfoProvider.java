/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.n2.pe.webservice.datamapper;

import com.tibco.xpd.n2.pe.catcherror.datamapper.CatchErrorDataMapperScriptGeneratorInfoProvider;
import com.tibco.xpd.n2.pe.datamapper.ProcessDataMapperScriptGeneratorInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * Script Generator Info Provider pertaining to Web-Service scenario.
 * 
 * @author sajain
 * @since Feb 15, 2016
 */
public class WebServiceDataMapperScriptGeneratorInfoProvider extends
        ProcessDataMapperScriptGeneratorInfoProvider {

    public static final String ERROR_CODE_VAR =
            CatchErrorDataMapperScriptGeneratorInfoProvider.ERROR_CODE_VAR;

    public static final String ERROR_DETAIL_VAR =
            CatchErrorDataMapperScriptGeneratorInfoProvider.ERROR_DETAIL_VAR;

    public static final String ERROR_CODE_TOKEN =
            CatchErrorDataMapperScriptGeneratorInfoProvider.ERROR_CODE_TOKEN;

    public static final String ERROR_DETAIL_TOKEN =
            CatchErrorDataMapperScriptGeneratorInfoProvider.ERROR_DETAIL_TOKEN;

    private static String MESSAGE_PARAM_NAME_PREFIX = "MESSAGE_"; //$NON-NLS-1$

    /**
     * Override to provide the appropriate runtime representation of the sBX
     * scope variable that is created and then used in the generated script to
     * represent the sub-process formal parameters.
     * 
     * @param designTimePath
     * @param pathOrJsVarAlias
     * @return
     */
    @Override
    protected String finaliseObjectPath(ConceptPath designTimePath,
            String pathOrJsVarAlias) {

        if (ERROR_CODE_TOKEN.equals(pathOrJsVarAlias)) {
            return "var_" + ERROR_CODE_VAR; //$NON-NLS-1$ 
        }
        if (ERROR_DETAIL_TOKEN.equals(pathOrJsVarAlias)) {
            return "var_" + ERROR_DETAIL_VAR; //$NON-NLS-1$ 
        }
        
        /*
         * XPD-8327: Sanitize message part name to ensure that the generated JavaScript is valid.
         */
        if (pathOrJsVarAlias.indexOf('.') > 0) {
        	String messagePart = pathOrJsVarAlias.substring(0, pathOrJsVarAlias.indexOf("."));
        	String remainingPathJsVarAlias = pathOrJsVarAlias.substring(pathOrJsVarAlias.indexOf("."));
        	pathOrJsVarAlias = NameUtil.sanitizeMessagePartVariableName(messagePart) + remainingPathJsVarAlias;
        } else {
        	pathOrJsVarAlias = NameUtil.sanitizeMessagePartVariableName(pathOrJsVarAlias);
        }

        return MESSAGE_PARAM_NAME_PREFIX + pathOrJsVarAlias;

    }
}
