/**
 * Copyright (c) TIBCO Software Inc 2004-2011. All rights reserved.
 */
package com.tibco.bx.xpdl2bpel.extensions;

import org.eclipse.uml2.uml.Type;

/**
 * Interface that contains the data model information
 *
 * @author mtorres
 *
 */
public interface IDataModel {

    /**
     * Returns the expression
     * @return String
     */
    public Expression getExpression();
    /**
     * Returns the type of the Data Model
     * @return Type
     */
    public Type getType();
    /**
     * Returns true if the expression is an array
     * @return
     */
    public boolean isArray();

}
