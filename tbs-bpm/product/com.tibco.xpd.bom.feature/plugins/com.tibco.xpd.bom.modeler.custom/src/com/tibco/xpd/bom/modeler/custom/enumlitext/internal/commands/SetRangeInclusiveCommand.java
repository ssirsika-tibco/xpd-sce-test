/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.internal.commands;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.bom.modeler.custom.enumlitext.RangeValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.internal.commands.SetRangeValueCommand.ValueType;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;

/**
 * Set the lower or upper inclusive of the range of an enumeration literal.
 * 
 * @author njpatel
 */
public class SetRangeInclusiveCommand extends RecordingCommand {

    private final boolean isInclusive;

    private ValueType type;

    private final EnumerationLiteral literal;

    public SetRangeInclusiveCommand(TransactionalEditingDomain domain,
            EnumerationLiteral literal, boolean isInclusive, ValueType type) {
        super(domain);
        this.literal = literal;
        this.isInclusive = isInclusive;
        this.type = type;
    }

    @Override
    protected void doExecute() {
        RangeValue range = EnumLitValueUtil.setRangeValue(literal);
        if (range != null) {
            if (type == ValueType.LOWER) {
                range.setLowerInclusive(isInclusive);
            } else {
                range.setUpperInclusive(isInclusive);
            }
        }
    }

}
