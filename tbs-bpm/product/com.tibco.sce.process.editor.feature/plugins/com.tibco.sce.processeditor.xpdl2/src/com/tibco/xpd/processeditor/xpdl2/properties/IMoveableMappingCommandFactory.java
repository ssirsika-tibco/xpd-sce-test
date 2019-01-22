/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.Mapping;

/**
 * Sub-class of {@link IMappingCommandFactory2} that causes
 * {@link AbstractMappingSection} switch behaviour when a mapping is moved.
 * Sub-classes can return a factory implementing
 * {@link AbstractMappingSection#getMappingCommandFactory2()} to provide a move
 * command rather than a separate remove then and add command
 * 
 * @author aallway
 * @since 26 Jun 2015
 */
public interface IMoveableMappingCommandFactory extends IMappingCommandFactory2 {

    /**
     * Create to move the source / target of the mapping
     * 
     * @param ed
     * @param owner
     * @param beforeMove
     * @param afterMove
     * @return Move command or <code>null</code> if move cannot be performed
     */
    Command getMoveMappingCommand(EditingDomain ed, EObject owner,
            Mapping beforeMove, Mapping afterMove);

}
