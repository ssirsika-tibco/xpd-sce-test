/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

/**
 * @author nwilson
 */
public interface IMappingTransferValidator {
    /**
     * @param source The source object.
     * @param target The target object.
     * @return true if the object is valid, otherwise false.
     */
    boolean isValidTransfer(Object source, Object target);

    /**
     * Indicates whether or not the target element can have multiple input
     * elements mapped to it.
     * @param target The target element.
     * @return true if it can accept multiple inputs.
     * @deprecated Concatenation mapping is no longer supported, so this method
     *             is no longer used.
     */
    @Deprecated
    boolean canAcceptMultipleInputs(Object target);
}
