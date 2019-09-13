/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.pe.internal.reader;

import java.net.URL;

import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.n2.cds.reader.CdsExtendedJScriptProcessDefinitionReader;
import com.tibco.xpd.n2.pe.PEActivator;
import com.tibco.xpd.n2.pe.util.PEN2Utils;

/**
 * Sid ACE-2807 This is the script definition reader that contributes the "bpm" class for case signal flows.
 *
 * The UML should contain a copy of all the stuff in
 *
 * @author aallway
 * @since 12 Sep 2019
 */
public class PEPlusCaseSignalJScriptProcessDefinitionReader extends
        CdsExtendedJScriptProcessDefinitionReader {
   
    
    @Override
    protected URI getURI() {
        URL entry =
                PEActivator.getDefault().getBundle()
                        .getEntry(PEN2Utils.PE_MODEL_FILE_NAME);
        return URI.createURI(entry.toExternalForm());
    }

}
