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
 * @author mtorres
 */
public class PEJScriptProcessDefinitionReader extends
        CdsExtendedJScriptProcessDefinitionReader {
   
    
    @Override
    protected URI getURI() {
        URL entry =
                PEActivator.getDefault().getBundle()
                        .getEntry(PEN2Utils.PE_MODEL_FILE_NAME);
        return URI.createURI(entry.toExternalForm());
    }

}
