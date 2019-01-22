/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.destinations.ui.GlobalDestinationHelper;
import com.tibco.xpd.destinations.ui.GlobalDestinationHelper.GlobalDestinationInfo;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author rsomayaj
 * 
 */
public class XPDDestPasteCommand extends AbstractCommand {

    private static final String DESTINATION = "Destination"; //$NON-NLS-1$

    private Process proc;

    private final HashMap<String, String> globalDests;

    CompoundCommand mainCmd = new CompoundCommand("Set Destination Command"); //$NON-NLS-1$

    /**
     * Shared transactional editing domain id
     * 
     * @since 3.0
     */
    public static final String EDITING_DOMAIN_ID =
            "com.tibco.xpd.resources.editingDomain"; //$NON-NLS-1$

    public XPDDestPasteCommand(EditingDomain ed, Process proc,
            HashMap<String, String> globalDests) {
        this.proc = proc;
        this.globalDests = globalDests;
        Collection<GlobalDestinationInfo> enabledGlobalDestinationsInfo =
                GlobalDestinationHelper.getEnabledGlobalDestinationsInfo(proc);
        boolean flagDestExists = false;
        ExtendedAttribute ext = null;
        for (String destId : globalDests.keySet()) {
            flagDestExists = false;
            String destName = globalDests.get(destId);
            for (GlobalDestinationInfo gdi : enabledGlobalDestinationsInfo) {
                if (gdi.getId().equals(destId)
                        && gdi.getName().equals(destName)) {
                    flagDestExists = true;
                    break;
                }
            }
            if (!flagDestExists) {
                Collection<String> globalDestinationNames =
                        GlobalDestinationHelper
                                .getGlobalDestinationNamesForId(destId);

                if (globalDestinationNames.contains(destName)) {
                    ext = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
                    ext.setName(DESTINATION);
                    ext.setValue(destName);
                    mainCmd
                            .append(AddCommand
                                    .create(ed,
                                            proc,
                                            Xpdl2Package.eINSTANCE
                                                    .getExtendedAttributesContainer_ExtendedAttributes(),
                                            ext));
                }
            }
        }
    }

    public void execute() {
        mainCmd.execute();
    }

    public void redo() {
        mainCmd.redo();
    }

    @Override
    public void undo() {
        mainCmd.undo();
    }

    @Override
    public boolean canExecute() {
        return mainCmd.canExecute();
    }
}