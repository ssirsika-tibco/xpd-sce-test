/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.pe.subprocess.datamapper;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.n2.pe.catcherror.datamapper.CatchErrorDataMapperScriptGeneratorInfoProvider;
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
     * In this the process engine scopes into the script a "parameters" JSON object that contains all of the sub-process
     * output parameters as properties.
     * 
     * Except for ERROR_CODE and ERROR_DETAIL data - but these are handled by the script generation info provider
     * {@link CatchErrorDataMapperScriptGeneratorInfoProvider}
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

        /*
         * Sid ACE-3834 PE now implicitly scopes a single "parameters" object containing the sub-process output
         * parmeters as properties.
         */
        return ReservedWords.SUBPROCESS_PARAMS_WRAPPER_OBJECT_NAME + ConceptPath.CONCEPTPATH_SEPARATOR
                + pathOrJsVarAlias;

    }
}
