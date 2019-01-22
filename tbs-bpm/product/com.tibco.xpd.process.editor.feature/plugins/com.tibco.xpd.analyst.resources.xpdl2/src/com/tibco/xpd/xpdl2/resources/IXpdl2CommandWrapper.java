/**
 * IXpdl2CommandWrapper.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.xpdl2.resources;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * IXpdl2CommandWrapper
 * <p>
 * Interface supporting the genericCommandWrapper extension point.
 * 
 * @deprecated This class is no longer used since moving to Transactional
 *             Editing Domains
 */
public interface IXpdl2CommandWrapper {

    /**
     * Provides the opportunity to wrap the given Add Command with extra
     * commands.
     * <p>
     * <b>NOTE</b> the actual originalCommand is not guaranteed to still be an
     * actual AddCommand() because it may have already been wrapped by another
     * part of the system.
     * 
     * @param originalCommand
     * @param domain
     * @param owner
     * @param feature
     * @param collection
     * @param index
     * @return new command if additional commands wrapped or
     *         originalCommand/null ifno extra commands to add.
     */
    Command wrapAddCommand(Command originalCommand, EditingDomain domain,
            EObject owner, EStructuralFeature feature, Collection collection,
            int index);

    /**
     * Provides the opportunity to wrap the given Set Command with extra
     * commands.
     * <p>
     * <b>NOTE</b> the actual originalCommand is not guaranteed to still be an
     * actual SetCommand() because it may have already been wrapped by another
     * part of the system.
     * 
     * @param originalCommand
     * @param domain
     * @param owner
     * @param feature
     * @param newValue
     * @param index
     * @return new command if additional commands wrapped or
     *         originalCommand/null ifno extra commands to add.
     */
    Command wrapSetCommand(Command originalCommand, EditingDomain domain,
            EObject owner, EStructuralFeature feature, Object newValue,
            int index);
    
    /**
     * Provides the opportunity to wrap the given Set Command with extra
     * commands.
     * <p>
     * <b>NOTE</b> the actual originalCommand is not guaranteed to still be an
     * actual SetCommand() because it may have already been wrapped by another
     * part of the system.
     * 
     * @param originalCommand
     * @param domain
     * @param owner
     * @param feature
     * @param collection
     * 
     * @return new command if additional commands wrapped or
     *         originalCommand/null if no extra commands to add.
     */
    Command wrapDeleteCommand(Command originalCommand,
            EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection);
}
