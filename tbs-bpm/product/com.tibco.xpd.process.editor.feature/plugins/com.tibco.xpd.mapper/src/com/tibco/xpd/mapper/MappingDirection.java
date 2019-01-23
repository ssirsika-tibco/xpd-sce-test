/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

/**
 * @author scrossle
 * 
 */
public enum MappingDirection {
    /** In mapping direction. */
    IN {
        /**
         * @return MappingDriection.OUT
         * @see com.tibco.xpd.mapper.MappingDirection#opposite()
         */
        MappingDirection opposite() {
            return OUT;
        }
    },
    /** Out mapping direction. */
    OUT {
        /**
         * @return MappingDriection.IN
         * @see com.tibco.xpd.mapper.MappingDirection#opposite()
         */
        MappingDirection opposite() {
            return IN;
        }
    };

    /**
     * @return The opposite direction.
     */
    abstract MappingDirection opposite();
}
