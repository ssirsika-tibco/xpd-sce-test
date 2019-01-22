/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.script.transform.document;

/**
 * @author mtorres
 * 
 */
public enum TransformDirection {
    /** Input transform direction. */
    INPUT {
        /**
         * @return MappingDriection.OUT
         * @see com.tibco.xpd.mapper.MappingDirection#opposite()
         */
        TransformDirection opposite() {
            return OUTPUT;
        }
    },
    /** Output transform direction. */
    OUTPUT {
        /**
         * @return MappingDriection.IN
         * @see com.tibco.xpd.mapper.MappingDirection#opposite()
         */
        TransformDirection opposite() {
            return INPUT;
        }
    };

    /**
     * @return The opposite direction.
     */
    abstract TransformDirection opposite();
}
