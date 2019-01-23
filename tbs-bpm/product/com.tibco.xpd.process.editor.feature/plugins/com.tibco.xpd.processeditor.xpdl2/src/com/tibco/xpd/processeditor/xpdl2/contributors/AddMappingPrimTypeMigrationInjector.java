/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.contributors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.migrate.IMigrationCommandInjector;
import com.tibco.xpd.processeditor.xpdl2.preCommit.MappingsProcessDataPrimitiveTypeCommand;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Injects command as necessary to add the xpdExt:SourcePrimitiveType and
 * xpdExt:TargetPrimitiveType attributes to data mappings if they are not there
 * already.
 * 
 * @author aallway
 * @since 28 Jan 2011
 */
public class AddMappingPrimTypeMigrationInjector implements
        IMigrationCommandInjector {

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.migrate.IMigrationCommandInjector#getCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Package)
     * 
     * @param editingDomain
     * @param pkg
     * @return
     */
    @Override
    public Command getCommand(EditingDomain editingDomain, Package pkg) {
        CompoundCommand cmd =
                new CompoundCommand(
                        "Add WS Activity data mapping primitive type attributes"); //$NON-NLS-1$

        for (Process process : pkg.getProcesses()) {
            cmd.append(MappingsProcessDataPrimitiveTypeCommand
                    .createForMigration(editingDomain, process));
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }
        return null;
    }
}
