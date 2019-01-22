/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.subprocess.datamapper;

import com.tibco.xpd.n2.pe.datamapper.ProcessDataMapperScriptGeneratorInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * Script generation info provider for sub-process data data-mapper IN content.
 * 
 * @author sajain
 * @since 7 Jan 2016
 */
public class SubProcessDataMapperScriptGeneratorInfoProvider extends
        ProcessDataMapperScriptGeneratorInfoProvider {

    private static String BX_PARAM_NAME_PREFIX = "_BX_"; //$NON-NLS-1$

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

        return BX_PARAM_NAME_PREFIX + pathOrJsVarAlias;

    }
}
