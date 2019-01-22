/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.internal.commands;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;

/**
 * Set the lower or upper value of the range of an enumeration literal.
 * 
 * @author njpatel
 */
public class SetRangeValueCommand extends RecordingCommand {

    private final String value;

    private ValueType type;

    private final EnumerationLiteral literal;

    public enum ValueType {
        LOWER, UPPER;
    }

    public SetRangeValueCommand(TransactionalEditingDomain domain,
            EnumerationLiteral literal, String value, ValueType type) {
        super(domain);
        this.literal = literal;
        this.value = value;
        this.type = type;
    }

    @Override
    protected void doExecute() {
        RangeValue range = EnumLitValueUtil.setRangeValue(literal);
        if (range != null) {
            if (type == ValueType.LOWER) {
                range.setLower(value);
            } else {
                range.setUpper(value);
            }
        }
    }
}
