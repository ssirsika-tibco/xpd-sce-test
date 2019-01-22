/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.Mapping;

/**
 * This interface is a replacement for the existing IMappingCommandFactory. The
 * significant change it supports is with the remove method based on the
 * selected mapping instead of the source and target. This remove method should
 * be effective in case of broken mappings as well where source OR target can be
 * null.
 * 
 * @author aprasad
 * @since 22 Feb 2013
 */
public interface IMappingCommandFactory2 {
    /**
     * Create a command to add a mapping between the source and target option.
     * 
     * @param ed
     * @param owner
     * @param source
     * @param target
     * 
     * @return Add command or <code>null</code> if cannot perform the move.
     */
    Command getAddMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target);

    /**
     * This method takes the mapping to be deleted.
     * 
     * @param ed
     * @param owner
     * @param mapping
     * @return
     */
    Command getRemoveMappingCommand(EditingDomain ed, EObject owner,
            Mapping mapping);
}
