/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import java.util.List;

/**
 * @author nwilson
 */
public class MapperWidgetInput {
    /** The source input. */
    private Object sourceInput;

    /** The target input. */
    private Object targetInput;

    /** The mappings list. */
    private List<Mapping> mappings;

    /**
     * @param sourceInput The source input.
     * @param targetInput The target input.
     * @param mappings The mappings list.
     */
    public MapperWidgetInput(Object sourceInput, Object targetInput,
            List<Mapping> mappings) {
        this.sourceInput = sourceInput;
        this.targetInput = targetInput;
        this.mappings = mappings;
    }

    /**
     * @return The source input.
     */
    public Object getSourceInput() {
        return sourceInput;
    }

    /**
     * @return The target input.
     */
    public Object getTargetInput() {
        return targetInput;
    }

    /**
     * @return The mappings list.
     */
    public List<Mapping> getMappings() {
        return mappings;
    }
}
