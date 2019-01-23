/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.n2.decisions.internal.configuration.mapping;

import org.eclipse.uml2.uml.Type;

import com.tibco.bx.xpdl2bpel.extensions.Expression;
import com.tibco.bx.xpdl2bpel.extensions.IDataModel;


/**
 * Decision Service Data Model
 * 
 * 
 * @author mtorres
 * 
 */
public class DecisionServiceDataModel implements IDataModel{

    private String grammar;
    private String text;
    private Type type;
    private boolean isArray;
    
    public DecisionServiceDataModel( String grammar, String text, Type type, boolean isArray) {
        this.grammar = grammar;
        this.text = text;
        this.type = type;
        this.isArray = isArray;
    }

    @Override
    public Type getType() {
        return type;
    }


    @Override
    public Expression getExpression() {
        return new Expression(grammar, text);
    }

    @Override
    public boolean isArray() {
        return isArray;
    }


}
