/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.mapper;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping;
import com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping Command Factory for the Process Data To Physical Work Item Attribute
 * Mappings. The Factory provides getter methods for add/remove mapping
 * commands.
 * 
 * @author aprasad
 * @since 30-Oct-2013
 */
public class ProcessDataToWIAttributeMappingCommandFactory extends
        AbstractMappingCommandFactory {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2#getAddMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.Object, java.lang.Object)
     * 
     * @param ed
     *            , {@link EditingDomain} editing Domain.
     * @param owner
     *            , {@link Process} to which the data belongs.
     * @param source
     *            , {@link ConceptPath} representing source Process Data.
     * @param target
     *            , {@link WorkItemAttributeConceptPath} representing the
     *            Physical Work Item Attribute.
     * @return Command, to add {@link DataWorkItemAttributeMapping}
     */
    @Override
    public Command getAddMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target) {

        if (owner instanceof Process && source instanceof ConceptPath
                && target instanceof WorkItemAttributeConceptPath) {

            final Process process = (Process) owner;

            WorkItemAttributeConceptPath targetConceptPath =
                    (WorkItemAttributeConceptPath) target;

            Property attribute = targetConceptPath.getPhysicalAttribute();

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.ProcessDataToWIAttributeMappingCommandFactory_ADD_MAPPING_CMD_LABEL);
            // get ProcessDataWorkItemAttributeMappings for the given process
            Object otherElement =
                    Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ProcessDataWorkItemAttributeMappings());

            final ProcessDataWorkItemAttributeMappings processDataWIAttributeMappings;

            if (otherElement instanceof ProcessDataWorkItemAttributeMappings) {
                processDataWIAttributeMappings =
                        (ProcessDataWorkItemAttributeMappings) otherElement;
            } else {
                // create one if does not exist
                processDataWIAttributeMappings =
                        XpdExtensionFactory.eINSTANCE
                                .createProcessDataWorkItemAttributeMappings();

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(ed,
                                process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ProcessDataWorkItemAttributeMappings(),
                                processDataWIAttributeMappings));
            }

            // Create Process data to Work Item Attribute Mapping
            DataWorkItemAttributeMapping dataAttributeMapping =
                    XpdExtensionFactory.eINSTANCE
                            .createDataWorkItemAttributeMapping();

            ConceptPath sourceConceptPath = (ConceptPath) source;

            dataAttributeMapping.setProcessData(sourceConceptPath.getPath()
                    .toString());

            dataAttributeMapping.setAttribute(attribute.getName());

            cmd.append(new AddCommand(
                    ed,
                    processDataWIAttributeMappings,
                    XpdExtensionPackage.eINSTANCE
                            .getProcessDataWorkItemAttributeMappings_DataWorkItemAttributeMapping(),
                    dataAttributeMapping));
            return cmd;

        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingCommandFactory#getRemoveMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, com.tibco.xpd.mapper.Mapping) Returns
     *      command to remove {@link DataWorkItemAttributeMapping}, if this is
     *      not the last mapping to the Attribute Attribute of the parent Facade
     *      File OR {@link FacadeMapping}, if this is the last mapping to an
     *      attribute alias of the parent Facade file OR
     *      {@link ProcessDataWorkItemAttributeMappings}, if this is the last
     *      {@link DataWorkItemAttributeMapping} for the given owner i.e. the
     *      {@link Process}.
     * @param ed
     *            , {@link EditingDomain} editing domain.
     * @param owner
     *            , {@link Process} to which this mapping belongs.
     * @param mapping
     *            , {@link DataWorkItemAttributeMapping} , representing the
     *            Process Data to Physical Work Item Attribute Mapping.
     * @return Command to remove {@link DataWorkItemAttributeMapping} OR
     *         {@link ProcessDataWorkItemAttributeMappings} as per the
     *         requirement
     */
    @Override
    public Command getRemoveMappingCommand(EditingDomain ed, EObject owner,
            Mapping mapping) {

        /*
         * Underlying class now supports non-Activity mappings, so we just deal
         * with our one special case that removes whole
         * ProcessDataWorkItemAttributeMappings element when last mapping is
         * removed.
         */
        if (mapping.getMappingModel() instanceof DataWorkItemAttributeMapping) {

            DataWorkItemAttributeMapping dataAattribMapping =
                    (DataWorkItemAttributeMapping) mapping.getMappingModel();

            if (dataAattribMapping.eContainer() instanceof ProcessDataWorkItemAttributeMappings) {

                ProcessDataWorkItemAttributeMappings processDataWIAttribMappings =
                        (ProcessDataWorkItemAttributeMappings) dataAattribMapping
                                .eContainer();

                if (processDataWIAttribMappings
                        .getDataWorkItemAttributeMapping().size() == 1
                        && processDataWIAttribMappings.eContainer() instanceof OtherElementsContainer) {
                    /*
                     * If this is the last DataWorkItemAttributeMapping to be
                     * removed , in that case remove the entire
                     * <xpdExt:ProcessDataWorkItemAttributeMappings> tag from
                     * the xpdl file, i.e. delete all the mappings.
                     */
                    CompoundCommand cmd =
                            new CompoundCommand(getRemoveCommandLabel());

                    cmd.append(Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(ed,
                                    (OtherElementsContainer) processDataWIAttribMappings
                                            .eContainer(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ProcessDataWorkItemAttributeMappings(),
                                    processDataWIAttribMappings));

                    return cmd;
                } else {
                    /*
                     * delete the mapping.
                     */
                    CompoundCommand cmd =
                            new CompoundCommand(getRemoveCommandLabel());

                    cmd.append(new RemoveCommand(ed,
                            processDataWIAttribMappings
                                    .getDataWorkItemAttributeMapping(),
                            dataAattribMapping));

                    return cmd;
                }
            }

        }

        return super.getRemoveMappingCommand(ed, owner, mapping);
    }
}
