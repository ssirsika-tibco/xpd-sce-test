/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.enumlitext.internal.commands;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;

/**
 * Command to set the 'single value' of an enumeration literal.
 * 
 * @author njpatel
 */
public class SetSingleValueCommand extends RecordingCommand {

    private final String value;

    private final EnumerationLiteral literal;

    public SetSingleValueCommand(TransactionalEditingDomain domain,
            EnumerationLiteral literal, String value) {
        super(domain);
        this.literal = literal;
        this.value = value;
    }

    @Override
    protected void doExecute() {
        EnumLitValueUtil.setSingleValue(literal, value);
    }

}
