/**
 * Copyright 2006 TIBCO Software Inc. 
 */
package com.tibco.xpd.processwidget.parts;


/**
 * 'Artificial' model for flow label.
 * 
 * Instance of this model is maintained by BaseConnectionEditPart and is created
 * when the connection has label
 * 
 * @author wzurek
 */
public class FlowLabelModel {

    private final BaseConnectionEditPart parentPart;

    public FlowLabelModel(BaseConnectionEditPart parentPart) {
        this.parentPart = parentPart;
    }

    public BaseConnectionEditPart getConnectionEditPart() {
        return parentPart;
    }
}
