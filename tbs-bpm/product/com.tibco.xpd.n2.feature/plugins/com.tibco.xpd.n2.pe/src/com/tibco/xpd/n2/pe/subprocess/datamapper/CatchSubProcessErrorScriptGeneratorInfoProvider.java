/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.pe.subprocess.datamapper;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.n2.pe.datamapper.ProcessDataMapperScriptGeneratorInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * Sid ACE-1599: DataMapper Script generation info provider for catch
 * sub-process error scenarios (representing the error payload side of the data.
 *
 * @author aallway
 * @since 2 Aug 2019
 */
public class CatchSubProcessErrorScriptGeneratorInfoProvider extends ProcessDataMapperScriptGeneratorInfoProvider {


    /**
     * In this the process engine scopes into the script the error payload
     * parameters from the subprocess as "_BX_MySubProcErrorParam" and so on
     * 
     * Except for ERROR_CODE and ERROR_DETAIL data (var_errorCode and
     * var_errorDetail temp variables. These have to remain the same.
     * 
     * @param designTimePath
     * @param pathOrJsVarAlias
     * @return
     */
    @Override
    protected String finaliseObjectPath(ConceptPath designTimePath, String pathOrJsVarAlias) {

        if (pathOrJsVarAlias.startsWith(ReservedWords.BX_ERROR_VAR_PREFIX)) {
            return pathOrJsVarAlias;
        }

        return ReservedWords.BX_TEMP_VAR_PREFIX + "_" + pathOrJsVarAlias; //$NON-NLS-1$

    }
}
