/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.subprocess.datamapper;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.n2.pe.datamapper.ProcessDataMapperScriptGeneratorInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.StandardMappingUtil;

/**
 * Script generation info provider for sub-process data data-mapper
 * input/output/catch-error content (for the sub-process side of the data)
 * 
 * @author sajain
 * @since 7 Jan 2016
 */
public class SubProcessDataMapperScriptGeneratorInfoProvider extends
        ProcessDataMapperScriptGeneratorInfoProvider {

    /**
     * Override to provide the appropriate runtime representation of the sBX
     * scope variable that is created and then used in the generated script to
     * represent the sub-process formal parameters.
     * 
     * Unless it is the special __PROCESS_ID__ field (process instance id
     * returned by asynh sub-process call - in which case we leave it alone.
     * 
     * @param designTimePath
     * @param pathOrJsVarAlias
     * @return
     */
    @Override
    protected String finaliseObjectPath(ConceptPath designTimePath,
            String pathOrJsVarAlias) {

        if (pathOrJsVarAlias.equals(StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER.getName())) {
            /*
             * For special __PROCESS_ID__ field use the temp variable
             * _BX___PROCESS_ID__ name.
             */
            return ReservedWords.BX_TEMP_VAR_PREFIX + "_" + pathOrJsVarAlias; //$NON-NLS-1$
        }

        return ReservedWords.SUBPROCESS_PARAMS_WRAPPER_OBJECT_NAME + ConceptPath.CONCEPTPATH_SEPARATOR
                + pathOrJsVarAlias;

    }
}
