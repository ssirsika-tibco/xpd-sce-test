/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.catcherror.datamapper;

import com.tibco.xpd.n2.pe.datamapper.ProcessDataMapperScriptGeneratorInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * Script generation info provider for ERRORCODE and ERRORDETAIL.
 * 
 * @author sajain
 * @since 7 Jan 2016
 */
public class CatchErrorDataMapperScriptGeneratorInfoProvider extends
        ProcessDataMapperScriptGeneratorInfoProvider {

    /* Sid ACE-4110 reverted to original top level variables "var_errorCode" / "var_errorDetail" */

    public static final String ERROR_CODE_VAR = "errorCode"; //$NON-NLS-1$

    public static final String ERROR_DETAIL_VAR = "errorDetail"; //$NON-NLS-1$

    public static final String ERROR_CODE_TOKEN = "$ERRORCODE"; //$NON-NLS-1$

    public static final String ERROR_DETAIL_TOKEN = "$ERRORDETAIL"; //$NON-NLS-1$   

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

        if (pathOrJsVarAlias == null || pathOrJsVarAlias.length() == 0) {
            return "var_" + ERROR_CODE_VAR; //$NON-NLS-1$ 
        }
        if (ERROR_CODE_TOKEN.equals(pathOrJsVarAlias)) {
            return "var_" + ERROR_CODE_VAR; //$NON-NLS-1$ 
        }
        if (ERROR_DETAIL_TOKEN.equals(pathOrJsVarAlias)) {
            return "var_" + ERROR_DETAIL_VAR; //$NON-NLS-1$ 
        }
        return "var_" + pathOrJsVarAlias; //$NON-NLS-1$ 

    }
}
