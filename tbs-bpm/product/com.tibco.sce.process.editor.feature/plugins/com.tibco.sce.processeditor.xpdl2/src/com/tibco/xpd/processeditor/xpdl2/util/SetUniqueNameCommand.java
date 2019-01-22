/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * SetUniqueNameCommand
 * <p>
 * Command to set the name of a process related object to a given named with a
 * suffixed index if the name is a duplicate of an existing name in a given set
 * of siblings.
 * 
 * @author aallway
 * @since 3.3 (5 Nov 2009)
 */
public class SetUniqueNameCommand extends CompoundCommand {
    private NamedElement namedElement;

    private String baseName;

    private EditingDomain editingDomain;

    private Collection<? extends NamedElement> namedSiblings;

    public SetUniqueNameCommand(EditingDomain editingDomain,
            NamedElement namedElement, String baseName,
            Collection<? extends NamedElement> namedSiblings) {
        this.namedElement = namedElement;
        this.baseName = baseName;
        this.editingDomain = editingDomain;
        this.namedSiblings = namedSiblings;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() {
        Set<String> existingLabels = new HashSet<String>();
        Set<String> existingNames = new HashSet<String>();

        for (NamedElement el : namedSiblings) {
            existingLabels.add(Xpdl2ModelUtil.getDisplayNameOrName(el));
            existingNames.add(el.getName());
        }

        int idx = 1;
        String finalName = baseName;
        while (existingLabels.contains(finalName)
                || existingNames.contains(NameUtil.getInternalName(finalName,
                        false))) {
            idx++;
            finalName = baseName + " " + idx; //$NON-NLS-1$
        }

        this.appendAndExecute(SetCommand.create(editingDomain,
                namedElement,
                Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                NameUtil.getInternalName(finalName, false)));

        this.appendAndExecute(Xpdl2ModelUtil
                .getSetOtherAttributeCommand(editingDomain,
                        namedElement,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        finalName));
        return;
    }

}
