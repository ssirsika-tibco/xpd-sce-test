/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.catcherror.datamapper;

import com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchErrorMapperSourceContentProvider;

/**
 * Extension of {@link CatchErrorMapperSourceContentProvider} which suppress
 * adding of script element on source (RHS).
 * 
 * @author sajain
 * @since Feb 26, 2016
 */
public class CatchErrorDataMapperSourceContentProvider extends
        CatchErrorMapperSourceContentProvider {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.error.CatchErrorMapperSourceContentProvider#doAddScriptElements()
     * 
     * @return
     */
    @Override
    protected boolean doAddScriptElements() {
        return false;
    }

}
