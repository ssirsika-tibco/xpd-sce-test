/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

/**
 * Input for mapper viewer is collection of 3 objects which will be used as
 * input to the 3 content providers.
 * 
 * @author scrossle
 * 
 */
public final class MapperViewerInput {
    /** Null input. */
    public static final MapperViewerInput NULL_INPUT = new MapperViewerInput(
            null, null, null);
    /** The source input. */
    private Object sourceInput;
    /** The target input. */
    private Object targetInput;
    /** The mapping input. */
    private Object mappingInput;

    /**
     * @param sourceInput The source input.
     * @param targetInput The target input.
     * @param mappingInput The mapping input.
     */
    public MapperViewerInput(Object sourceInput, Object targetInput,
            Object mappingInput) {
        super();
        this.sourceInput = sourceInput;
        this.targetInput = targetInput;
        this.mappingInput = mappingInput;
    }

    /**
     * @return the mappingInput
     */
    public Object getMappingInput() {
        return mappingInput;
    }

    /**
     * @return the sourceInput
     */
    public Object getSourceInput() {
        return sourceInput;
    }

    /**
     * @return the targetInput
     */
    public Object getTargetInput() {
        return targetInput;
    }
}
