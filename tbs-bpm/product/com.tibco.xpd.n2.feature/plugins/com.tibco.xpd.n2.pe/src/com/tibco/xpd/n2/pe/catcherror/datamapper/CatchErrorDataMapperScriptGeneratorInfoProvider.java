/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.catcherror.datamapper;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
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

    /*
     * Sid ACE-3834 error code / details are now wrapped in an implicitly scoped "parameters" object as
     * "parameters.$ERROR_CODE" and "parameters.$ERROR_DETAILS" respectively
     */

    public static final String ERROR_CODE_VAR = "$ERROR_CODE"; //$NON-NLS-1$

    public static final String ERROR_DETAIL_VAR = "$ERROR_DETAILS"; //$NON-NLS-1$

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
            return ReservedWords.SUBPROCESS_PARAMS_WRAPPER_OBJECT_NAME + ConceptPath.CONCEPTPATH_SEPARATOR
                    + ERROR_CODE_VAR;
        }
        if (ERROR_CODE_TOKEN.equals(pathOrJsVarAlias)) {
            return ReservedWords.SUBPROCESS_PARAMS_WRAPPER_OBJECT_NAME + ConceptPath.CONCEPTPATH_SEPARATOR
                    + ERROR_CODE_VAR;
        }
        if (ERROR_DETAIL_TOKEN.equals(pathOrJsVarAlias)) {
            return ReservedWords.SUBPROCESS_PARAMS_WRAPPER_OBJECT_NAME + ConceptPath.CONCEPTPATH_SEPARATOR
                    + ERROR_DETAIL_VAR;
        }
        return ReservedWords.SUBPROCESS_PARAMS_WRAPPER_OBJECT_NAME + ConceptPath.CONCEPTPATH_SEPARATOR
                + pathOrJsVarAlias;

    }
}
