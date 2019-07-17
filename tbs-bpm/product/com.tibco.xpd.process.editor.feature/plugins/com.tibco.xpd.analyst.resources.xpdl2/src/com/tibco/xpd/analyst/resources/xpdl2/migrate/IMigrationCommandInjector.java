/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.migrate;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdl2.Package;

/**
 * For contributors to the
 * xpdlMigrationInjector/endOfMigrationCommandInjector/class extension element.
 * 
 * @author aallway
 * @since 28 Jan 2011
 */
public interface IMigrationCommandInjector {
    /**
     * Always called by the framework to set the format-version of the file as
     * it was prior to the xslt migrations
     * 
     * @param originalFormatVersion
     */
    default void setOriginalFormatVersion(int originalFormatVersion) {

    }

    /**
     * Called at the end of migration cycle.
     * <p>
     * 
     * @param editingDomain
     * @param pkg
     * @return Return a command if required or <code>null</code> if not
     */
    Command getCommand(EditingDomain editingDomain, Package pkg);

}
