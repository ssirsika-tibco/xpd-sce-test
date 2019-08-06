package com.tibco.xpd.n2.pe.datamapper;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * The datamapper script generation info provider for Local Signal Payload data.
 * (the LHS data on a catch local signal event mappings).
 * 
 * @author aallway
 * @since 29 Jul 2019
 */
public class LocalSignalPayloadScriptGeneratorInfoProvider extends
        ProcessDataMapperScriptGeneratorInfoProvider {

    /**
     * Override to provide the appropriate runtime representation of the
     * SIGNAL_paramName scope variable that is created and then used in the
     * generated script to represent the sub-process formal parameters.
     * 
     * The process engine puts these SIGNAL_someprocessdatafield in scope when
     * invoking the script
     * 
     * @param designTimePath
     * @param pathOrJsVarAlias
     * @return
     */
    @Override
    protected String finaliseObjectPath(ConceptPath designTimePath, String pathOrJsVarAlias) {

        return ReservedWords.BX_SIGNAL_PAYLOAD_PREFIX + pathOrJsVarAlias;

    }
}
