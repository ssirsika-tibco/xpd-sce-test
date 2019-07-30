package com.tibco.xpd.n2.pe.globalsignal.datamapper;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.n2.pe.datamapper.ProcessDataMapperScriptGeneratorInfoProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;

/**
 * The datamapper script generation info provider for Global Signal Payload
 * data.
 * 
 * As global signal data payload fields are effectively the same as process
 * fields, this is based on the process data script generation info provider. In
 * the case of global signal payload data, the global signal parameters are
 * scoped into the script as SIGNAL_paramName.
 *
 * @author aallway
 * @since 29 Jul 2019
 */
public class GlobalSignalDataMapperScriptGeneratorInfoProvider extends
        ProcessDataMapperScriptGeneratorInfoProvider {

    /**
     * Override to provide the appropriate runtime representation of the
     * SIGNAL_paramName scope variable that is created and then used in the
     * generated script to represent the sub-process formal parameters.
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
