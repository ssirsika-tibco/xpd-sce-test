/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.resources.migration;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Model;

/**
 * Interface that needs to be implemented by the Class specified in the BOM
 * migration extension contribution.
 * 
 * @author rgreen
 * 
 */
public interface IBOMMigration {

    Command getMigrationCommand(TransactionalEditingDomain domain, Model model);

}
