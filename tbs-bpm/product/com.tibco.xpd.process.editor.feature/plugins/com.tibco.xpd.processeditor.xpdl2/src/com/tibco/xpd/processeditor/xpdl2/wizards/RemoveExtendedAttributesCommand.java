/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdExtension.XpdExtAttribute;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;

/**
 * Late execute command class to remove the un-required extended attributes from
 * page activities, transitions and data fields in the new process, that get
 * copied from the fragment process
 * 
 * @author bharge
 * @since 23 Jan 2014
 */
public class RemoveExtendedAttributesCommand extends AbstractLateExecuteCommand {

    Process process;

    /**
     * @param editingDomain
     * @param contextObject
     */
    public RemoveExtendedAttributesCommand(EditingDomain editingDomain,
            Object contextObject) {

        super(editingDomain, contextObject);
        if (contextObject instanceof Process) {

            this.process = (Process) contextObject;
        }
    }

    /**
     * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      java.lang.Object)
     * 
     * @param editingDomain
     * @param contextObject
     * @return
     */
    @Override
    protected Command createCommand(EditingDomain editingDomain,
            Object contextObject) {

        if (null != process) {

            CompoundCommand cmd = new CompoundCommand();
            /*
             * Collect extended attributes to remove in separate list, because
             * we cannot delete from collection we are iterating.
             */
            List<EObject> removeExtAttrs = new ArrayList<EObject>();
            TreeIterator<Object> allContents =
                    EcoreUtil.getAllContents(process, false);
            while (allContents.hasNext()) {

                Object obj = allContents.next();
                if (obj instanceof ExtendedAttribute) {

                    ExtendedAttribute extendedAttribute =
                            (ExtendedAttribute) obj;

                    if (null != extendedAttribute.getName()
                            && "PostProcess".equalsIgnoreCase(extendedAttribute.getName())) { //$NON-NLS-1$

                        removeExtAttrs.add(extendedAttribute);
                    }
                } else if (obj instanceof XpdExtAttribute) {

                    XpdExtAttribute extendedAttribute = (XpdExtAttribute) obj;
                    if (null != extendedAttribute.getName()
                            && "PostProcess".equalsIgnoreCase(extendedAttribute.getName())) { //$NON-NLS-1$

                        removeExtAttrs.add(extendedAttribute);
                    }
                }
            }

            /*
             * Remove extended attributes
             */
            cmd.append(RemoveCommand.create(editingDomain, removeExtAttrs));

            return cmd;
        }
        return null;
    }
}
