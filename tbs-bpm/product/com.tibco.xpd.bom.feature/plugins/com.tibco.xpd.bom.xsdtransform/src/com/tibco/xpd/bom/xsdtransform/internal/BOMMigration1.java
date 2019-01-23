/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.internal;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.resources.migration.IBOMMigration;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.commands.NameCleanseBOMCommand;

/**
 * Migration to BOM version 1. This will apply the XSD name cleanser to the
 * Model if the XSD profile is applied to the Model.
 * 
 * @author rgreen
 */
public class BOMMigration1 implements IBOMMigration {

    public BOMMigration1() {
    }

    @Override
    public Command getMigrationCommand(TransactionalEditingDomain domain,
            Model model) {

        if (model
                .getAppliedProfile(XsdStereotypeUtils.XSD_NOTATION_PROFILE_NAME) != null) {
            return new NameCleanseBOMCommand(domain, model);
        }
        return null;
    }

}
