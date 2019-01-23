/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.datamapper.api;

import com.tibco.xpd.mapper.MappingDirection;

/**
 * Normally {@link AbstractDataMapperSection} does not expose the script grammar
 * selection combo (naturally because the grammar is always DataMapper).
 * <p>
 * However in some scenarios (such as sub-process and web-service in / out there
 * may be an existing supported grammar that we need to switch to / from.
 * 
 * @author aallway
 * @since 6 Jan 2016
 */
public abstract class AbstractSelectableGrammarDataMapperSection extends
        AbstractDataMapperSection {

    /**
     * @param direction
     */
    public AbstractSelectableGrammarDataMapperSection(MappingDirection direction) {
        super(direction);
    }

    /**
     * {@link AbstractDataMapperSection} supresses header controls as it doesn't
     * want grammar selection. We need grammar selection so overriding this
     * behavour.
     * 
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#showGrammarSelectionCombo()
     * 
     * @return
     */
    @Override
    protected boolean showGrammarSelectionCombo() {
        return true;
    }

}
