/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client;

/**
 * Enumeration that can be used to tell whether the parameter of a js method is
 * to allow subtype/supertype compatibility w.r.to the parameter definition and
 * the provided parameter value
 * 
 * @author bharge
 * @since 12 Dec 2013
 */
public enum ParameterCoercionCriteria {

    /***
     * Use
     * <p>
     * SUB_TYPE_TO_SUPER_TYPE - when you want to allow sub type assignment to
     * super type
     * </p>
     * 
     * <p>
     * SUPER_TYPE_TO_SUB_TYPE - when you want to allow super type assignment to
     * sub type (generally used in case of cac casting methods)
     * </p>
     * 
     * <p>
     * SUB_TYPE_AND_SUPER_TYPE - when you want to allow both types to be
     * assigned interchangeably to each other (for instance in case of bom
     * types)
     * </p>
     */
    SUB_TYPE_TO_SUPER_TYPE, SUPER_TYPE_TO_SUB_TYPE, SUB_TYPE_AND_SUPER_TYPE
}
