/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.n2.ut.resources.dynamicparticipant.mapper;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.n2.ut.resources.internal.Messages;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdExtension.DynamicOrgIdentifierRef;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMapping;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Command Factory to manipulate mappings between Process data and Dynamic Org
 * Identifier.
 * 
 * @author kthombar
 * @since 26-Sep-2013
 */
public class ProcessDataToDynamicOrgIdentifierMappingCommandFactory extends
        AbstractMappingCommandFactory {

    public ProcessDataToDynamicOrgIdentifierMappingCommandFactory() {
        super();
    }

    /**
     * Returns command to create mappings between Process data and Dynamic Org
     * Identifiers.
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory#getAddMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.Object, java.lang.Object)
     * 
     * @param ed
     * @param owner
     * @param source
     * @param target
     * @return
     */
    public Command getAddMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target) {

        if (owner instanceof Process && source instanceof ConceptPath
                && target instanceof DynamicOrgIdentiferPath) {
            final Process process = (Process) owner;

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.ProcessDataToDynamicOrgIdentifierMappingCommandFactory_CreateProcessDataToDynamicIdentiferMappingCommand_label);

            Object otherElement =
                    Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DynamicOrganizationMappings());

            final DynamicOrganizationMappings organizationMappings;
            if (otherElement instanceof DynamicOrganizationMappings) {
                organizationMappings =
                        (DynamicOrganizationMappings) otherElement;
            } else {
                organizationMappings =
                        XpdExtensionFactory.eINSTANCE
                                .createDynamicOrganizationMappings();

                cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                        process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DynamicOrganizationMappings(),
                        organizationMappings));
            }

            DynamicOrganizationMapping dynOrganizationMapping =
                    XpdExtensionFactory.eINSTANCE
                            .createDynamicOrganizationMapping();

            DynamicOrgIdentifierRef dynOrgIdentifierRef =
                    XpdExtensionFactory.eINSTANCE
                            .createDynamicOrgIdentifierRef();

            DynamicOrgIdentiferPath targetDynamicOrgIdentiferPath =
                    (DynamicOrgIdentiferPath) target;

            ConceptPath sourceConceptPath = (ConceptPath) source;

            dynOrganizationMapping.setSourcePath(sourceConceptPath.getPath());

            dynOrgIdentifierRef.setIdentifierName(targetDynamicOrgIdentiferPath
                    .getName());
            dynOrgIdentifierRef.setDynamicOrgId(targetDynamicOrgIdentiferPath
                    .getParent().getId());
            dynOrgIdentifierRef.setOrgModelPath(targetDynamicOrgIdentiferPath
                    .getOrgModelPath());

            dynOrganizationMapping
                    .setDynamicOrgIdentifierRef(dynOrgIdentifierRef);

            cmd.append(new AddCommand(ed, organizationMappings
                    .getDynamicOrganizationMapping(), dynOrganizationMapping));
            return cmd;

        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingCommandFactory#getRemoveCommandLabel()
     * 
     * @return
     */
    @Override
    protected String getRemoveCommandLabel() {
        return Messages.ProcessDataToDynamicOrgIdentifierMappingCommandFactory_RemoveProcessDataToOrgMapiing_label0;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingCommandFactory#getRemoveMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, com.tibco.xpd.mapper.Mapping)
     * 
     * @param ed
     * @param owner
     * @param mapping
     * @return
     */
    @Override
    public Command getRemoveMappingCommand(EditingDomain ed, EObject owner,
            Mapping mapping) {

        /*
         * Underlying class now supports non-Activity mappings, so we just deal
         * with our one special case that removes whole
         * DynamicOrganisatioIdentifierMappings element when last mapping is
         * removed.
         */
        if (mapping.getMappingModel() instanceof DynamicOrganizationMapping) {

            DynamicOrganizationMapping dynamicOrganizationMapping =
                    (DynamicOrganizationMapping) mapping.getMappingModel();

            if (dynamicOrganizationMapping.eContainer() instanceof DynamicOrganizationMappings) {
                DynamicOrganizationMappings dynamicOrganizationMappings =
                        (DynamicOrganizationMappings) dynamicOrganizationMapping
                                .eContainer();

                if (dynamicOrganizationMappings.getDynamicOrganizationMapping()
                        .size() == 1
                        && dynamicOrganizationMappings.eContainer() instanceof OtherElementsContainer) {
                    /*
                     * If the last Mapping is removed , in that case remove the
                     * entire <xpdExt:DynamicOrganizationMappings> tag from the
                     * xpdl file, i.e. delete all the mappings.
                     */
                    CompoundCommand cmd =
                            new CompoundCommand(getRemoveCommandLabel());

                    cmd.append(Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(ed,
                                    (OtherElementsContainer) dynamicOrganizationMappings
                                            .eContainer(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DynamicOrganizationMappings(),
                                    dynamicOrganizationMappings));

                    return cmd;
                }
            }
        }

        return super.getRemoveMappingCommand(ed, owner, mapping);
    }
}
