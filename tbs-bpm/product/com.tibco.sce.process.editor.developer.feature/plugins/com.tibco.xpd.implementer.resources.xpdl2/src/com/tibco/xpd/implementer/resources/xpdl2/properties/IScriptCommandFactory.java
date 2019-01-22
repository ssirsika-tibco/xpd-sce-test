/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * @deprecated
 * @author nwilson
 */
public interface IScriptCommandFactory {
    /**
     * @param ed The editing domain.
     * @param owner The owning object.
     * @param target The target object.
     * @param grammar The script grammar.
     * @param script The script.
     * @return The command.
     * @deprecated
     */
    Command getSetScriptedMappingCommand(EditingDomain ed, EObject owner,
            Object target, String grammar, String script);
}
