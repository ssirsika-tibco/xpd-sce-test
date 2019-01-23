/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.api.commands;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.xsdtransform.utils.NameAssignmentException;
import com.tibco.xpd.bom.xsdtransform.utils.XSDDerivedBOMNameAssigner;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * Executes the XSDDerivedBOMNameAssigner name cleanser on the supplied
 * model.
 * 
 * @author rgreen
 * 
 */
public class NameCleanseBOMCommand extends RecordingCommand {

    TransactionalEditingDomain ed;

    Model model;

    public NameCleanseBOMCommand(TransactionalEditingDomain domain, Model mod) {
        super(domain);
        ed = domain;
        model = mod;
    }

    @Override
    protected void doExecute() {
        XSDDerivedBOMNameAssigner cleanser =
                new XSDDerivedBOMNameAssigner();

        try {
            cleanser.setNames(model);
        } catch (NameAssignmentException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }

    }

    @Override
    public boolean canExecute() {

        if (ed != null && model != null) {
            return true;
        } else {
            return super.canExecute();
        }
    }

}
