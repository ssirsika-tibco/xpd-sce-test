/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.MappingDelta;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author scrossle
 */
public class SubProcMappingCommandFactory extends
        AbstractDataMappingCommandFactory implements IMappingCommandFactory {

    private MappingDirection createDirection;

    /**
     * @param createDirection
     */
    public SubProcMappingCommandFactory(MappingDirection createDirection) {
        super();
        this.createDirection = createDirection;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractDataMappingCommandFactory#getUnmappedScriptContainerObject(org.eclipse.emf.ecore.EObject,
     *      com.tibco.xpd.xpdl2.DataMapping)
     * 
     * @param owner
     * @param dataMapping
     * @return
     */
    @Override
    protected OtherElementsContainer getUnmappedScriptContainerObject(
            EObject owner, DataMapping dataMapping) {
        if (owner instanceof Activity) {
            return (Activity) owner;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.properties.IMappingCommandFactory
     * #getAddMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, java.lang.Object, java.lang.Object,
     * com.tibco.xpd.mapper.MappingDirection)
     */
    @Override
    public Command getAddMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target) {
        Activity activity = (Activity) owner;
        SubFlow subFlow = getSubFlow(owner);
        if (subFlow != null) {
            return StandardMappingUtil
                    .createMapFromScriptOrProcDataToProcDataCommand(ed,
                            source,
                            target,
                            activity,
                            subFlow,
                            Xpdl2Package.eINSTANCE.getSubFlow_DataMappings(),
                            subFlow.getDataMappings(),
                            createDirection);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.implementer.resources.xpdl2.properties.IMappingCommandFactory
     * #getRemoveMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, java.lang.Object, java.lang.Object,
     * com.tibco.xpd.mapper.MappingDirection)
     */
    @Override
    public Command getRemoveMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target) {
        // XPD-4275:Interface ImappingCommandFactory is deprecated and the new
        // interface IMappingCommandFactory2 and its methods will be used, hence
        // this method is no more required to do anything.
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @param ed
     *            The editing domain.
     * @param input
     *            The input object.
     * @param delta
     *            The mapping delta.
     * @return The command to make the changes.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory#getChangeMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, com.tibco.xpd.mapper.MappingDelta)
     * @deprecated This method is never called via the Mapper which always does
     *             remove and re-add
     */
    @Override
    @Deprecated
    public Command getChangeMappingCommand(EditingDomain ed, EObject owner,
            Collection<MappingDelta> changes) {
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @param target
     * @return
     */
    private SubFlow getSubFlow(EObject owner) {
        SubFlow subFlow = null;
        if (owner instanceof Activity) {
            Activity activity = (Activity) owner;
            Implementation impl = activity.getImplementation();
            if (impl instanceof SubFlow) {
                subFlow = (SubFlow) impl;
            }
        }
        return subFlow;
    }

}
