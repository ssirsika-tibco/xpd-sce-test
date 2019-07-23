/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.process.localsignal.datamapper.mapping;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.n2.process.globalsignal.mapping.AbstractCatchSignalMapperScriptProvider;

/**
 * Mapper Script Provider implementation for CATCH local signal.
 *
 * @author sajain
 * @since Jul 17, 2019
 */
public class CatchLocalSignalMapperScriptProvider extends AbstractCatchSignalMapperScriptProvider {

    /**
     * Mapper Script Provider implementation for CATCH local signal.
     * 
     * @param grammar
     * @param direction
     */
    public CatchLocalSignalMapperScriptProvider(String grammar,
            MappingDirection direction) {
        super(grammar, direction);
    }

    /**
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptProvider#getDecriptionLabel()
     */
    @Override
    public String getDecriptionLabel() {
        return Messages.CatchLocalSignalMapperScriptProvider_CatchLocalSignalScriptMappingSection_title;
    }

    /**
     * @see com.tibco.xpd.n2.process.globalsignal.mapping.AbstractCatchSignalMapperScriptProvider#getCommandLabel()
     *
     * @return
     */
    @Override
    protected String getCommandLabel() {
        return Messages.CatchLocalSignalMapperScriptProvider_SetLocalSignalScriptCommand_label;
    }
}
