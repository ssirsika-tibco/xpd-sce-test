/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.commands;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * @author rgreen
 * 
 */
/**
 * 
 * Sets the ULM2 NamedElment display label.
 * 
 * @author rgreen
 * 
 */
public class SetDisplayLabelCommand extends AbstractTransactionalCommand {

    TransactionalEditingDomain ed;

    NamedElement elem;

    String displayLabel;

    public SetDisplayLabelCommand(TransactionalEditingDomain domain,
            IFile file, NamedElement ne, String label) {
        super(domain, Messages.DisplayLabelParser_CommandDescription_label,
                Collections.singletonList(file));
        ed = domain;
        elem = ne;
        displayLabel = label;
    }

    @Override
    protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
            IAdaptable info) throws ExecutionException {

        PrimitivesUtil.setDisplayLabel(elem, displayLabel);

        return CommandResult.newOKCommandResult();
    }

}
