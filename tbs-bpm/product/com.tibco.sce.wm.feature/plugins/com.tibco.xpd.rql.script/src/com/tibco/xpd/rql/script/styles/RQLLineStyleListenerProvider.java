/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.rql.script.styles;

import com.tibco.xpd.script.sourceviewer.internal.viewer.listeners.AbstractLineStyleListener;
import com.tibco.xpd.script.sourceviewer.internal.viewer.listeners.AbstractLineStyleListenerProvider;

/**
 * @author rsomayaj
 * 
 */
public class RQLLineStyleListenerProvider extends
        AbstractLineStyleListenerProvider {

    /**
     * @see com.tibco.xpd.script.sourceviewer.internal.viewer.listeners.AbstractLineStyleListenerProvider#getLineStyleListener()
     * 
     * @return
     */
    @Override
    public AbstractLineStyleListener getLineStyleListener() {
        return new RQLLineStyleListener();
    }
}
