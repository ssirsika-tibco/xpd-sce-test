/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.xpdExtension.InitialParameterValue;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.commands.LateExecuteRemoveCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * A factory for commands to maintain mappings between formal and actual
 * parameters on various base objects WHERE the mapping model is of type
 * {@link DataMapping}.
 * <p>
 * This command factory is capable of dealing with script mappings that use
 * {@link ScriptInformation} or initial value mappings that use
 * {@link InitialValue} (if mapping is deleted the source script is moved to a
 * specified container object).
 * 
 * @author aprasad
 * @since 22 Feb 2013
 */
public abstract class AbstractDataMappingCommandFactory extends
        AbstractMappingCommandFactory {

    /**
     * This method should return the container object to be used to place the
     * source-script of a script mapping when that mapping is deleted.
     * 
     * @param owner
     * @param dataMapping
     * 
     * @return Container object for unmapped source-scripts for deleted mappings
     *         or <code>null</code> if it is not retuired for unmapped scrpts to
     *         be stored.
     */
    protected abstract OtherElementsContainer getUnmappedScriptContainerObject(
            EObject owner, DataMapping dataMapping);

    @Override
    public Command getRemoveMappingCommand(EditingDomain ed, EObject owner,
            Mapping mapping) {
        Object source = mapping.getSource();

        if (mapping.getMappingModel() instanceof DataMapping) {
            DataMapping dataMapping = (DataMapping) mapping.getMappingModel();

            if (source instanceof ScriptInformation) {

                return createRemoveScriptMappingCommand(ed,
                        owner,
                        (ScriptInformation) source,
                        dataMapping);

            } else if (source instanceof InitialValue) {
                return createRemoveInitialValueMappingCommand(ed,
                        owner,
                        (InitialValue) source,
                        dataMapping);

            } else {
                return super.getRemoveMappingCommand(ed, owner, mapping);
            }

        } else {
            throw new IllegalStateException(
                    "Expected the data mapping model to be of type: " //$NON-NLS-1$
                            + DataMapping.class.getCanonicalName());
        }

    }

    /**
     * @param ed
     * @param owner
     * @param dataMapping
     * 
     * @return the command to remove the script mapping (by removing the
     */
    protected Command createRemoveScriptMappingCommand(EditingDomain ed,
            EObject owner, ScriptInformation scriptInformation,
            DataMapping dataMapping) {

        CompoundCommand cmd = new CompoundCommand(getRemoveCommandLabel());

        OtherElementsContainer unmappedScriptContainerObject =
                getUnmappedScriptContainerObject(owner, dataMapping);

        if (unmappedScriptContainerObject != null) {
            /*
             * XPD-5500: Don't always use DataMapping/Actual for script as the
             * location of script is different for OUT mappings than IN
             * mappings.
             */
            String currentScript = DataMappingUtil.getScript(dataMapping);

            if (currentScript != null) {
                Expression newExpression =
                        Xpdl2ModelUtil.createExpression(currentScript);
                newExpression.setScriptGrammar(DataMappingUtil
                        .getGrammar(dataMapping));

                cmd.append(SetCommand.create(ed,
                        scriptInformation,
                        XpdExtensionPackage.eINSTANCE
                                .getScriptInformation_Expression(),
                        newExpression));
            }

            cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                    unmappedScriptContainerObject,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                    scriptInformation));
        }

        /* Finally remove the mapping itself. */
        cmd.append(LateExecuteRemoveCommand.create(ed, dataMapping));

        return cmd;
    }

    /**
     * Create the command to remove the initial value mapping (by removing the
     * mapping and adding the script for the source initial value to the
     * sub-class provided 'unmappedScript; container.
     * 
     * @param ed
     * @param owner
     * @param source
     * @param dataMapping
     * 
     * @return the command to remove the initial value mapping (by removing the
     */
    protected Command createRemoveInitialValueMappingCommand(EditingDomain ed,
            EObject owner, InitialValue initialValue, DataMapping dataMapping) {

        CompoundCommand cmd = new CompoundCommand(getRemoveCommandLabel());

        OtherElementsContainer unmappedScriptContainerObject =
                getUnmappedScriptContainerObject(owner, dataMapping);

        if (unmappedScriptContainerObject != null) {
            String value = DataMappingUtil.getScript(dataMapping);

            InitialParameterValue initialParameter =
                    XpdExtensionFactory.eINSTANCE.createInitialParameterValue();

            initialParameter.setName(initialValue.getFormal().getName());
            initialParameter.setValue(value);

            cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                    unmappedScriptContainerObject,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_InitialParameterValue(),
                    initialParameter));
        }

        /* Finally remove the mapping itself. */
        cmd.append(LateExecuteRemoveCommand.create(ed, dataMapping));

        return cmd;
    }
}
