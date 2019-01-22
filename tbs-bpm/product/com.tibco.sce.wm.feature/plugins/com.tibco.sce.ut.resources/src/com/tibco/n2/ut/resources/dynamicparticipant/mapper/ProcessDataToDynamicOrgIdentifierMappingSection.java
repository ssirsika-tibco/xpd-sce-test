/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.n2.ut.resources.dynamicparticipant.mapper;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.n2.ut.resources.internal.Messages;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MapperViewerInput;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2;
import com.tibco.xpd.processeditor.xpdl2.properties.ProcessScopeDataConceptPathProvider;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMapping;
import com.tibco.xpd.xpdExtension.DynamicOrganizationMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Collapsible section under Business Process Properties Work Resource tab.
 * Allows mappings from process data to Dynamic Org identifiers referenced by
 * Dynamic Organization Participants.
 * 
 * @author kthombar
 * @since 26-Sep-2013
 */
public class ProcessDataToDynamicOrgIdentifierMappingSection extends
        AbstractItemProviderMappingSection {

    private ProcessDataToDynamicOrgIdentifierMappingCommandFactory commandFactory;

    /**
     * @param direction
     */
    public ProcessDataToDynamicOrgIdentifierMappingSection() {
        super(MappingDirection.IN);
        setMapperLabelProvider(new MapperLabelProvider(
                new ConceptLabelProvider(), new DynamicOrgIdLabelProvider()));

        commandFactory =
                new ProcessDataToDynamicOrgIdentifierMappingCommandFactory();
    }

    /**
     * Create Source content provider, in this case a Concept path of the
     * Process data.
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection#createSourceContentProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {

        return new ProcessScopeDataConceptPathProvider();
    }

    /**
     * Create target content provider, in this this case create Tree having root
     * as Dynamic Organization(referenced by participants) and their child as
     * the dynamic org identifiers
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection#createTargetContentProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {

        return new DynamicOrgIdentifierContentProvider();

    }

    /**
     * Create mappings between Process data(i.e. Source) and Dynamic Org
     * Identifiers(i.e. Target)
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection#createMappingContentProvider()
     * 
     * @return
     */
    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new ProcessDataToDynamicOrgIdentifierMappingContentProvider();
    }

    /**
     * Validate if the mapping is appropriate. i.e. check for compatibility of
     * mapping or concatenated mapping allowed.
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTransferValidator()
     * 
     * @return
     */
    @Override
    protected IMappingTransferValidator getTransferValidator() {
        return new IMappingTransferValidator() {
            /**
             * Do not allow mappings between process data and Dynamic
             * Participant(i.e. Root of the target tree.)
             * 
             * @see com.tibco.xpd.mapper.IMappingTransferValidator#isValidTransfer(java.lang.Object,
             *      java.lang.Object)
             * 
             * @param source
             * @param target
             * @return
             */
            public boolean isValidTransfer(Object source, Object target) {
                if (target instanceof Organization || target instanceof String) {
                    /*
                     * Do not allow Mappings to the Root element.
                     */
                    return false;
                }
                return true;
            }

            /**
             * 
             * @see com.tibco.xpd.mapper.IMappingTransferValidator#canAcceptMultipleInputs(java.lang.Object)
             * 
             * @param target
             * @return
             */
            public boolean canAcceptMultipleInputs(Object target) {
                /**
                 * Concatenation of inputs not allowed.
                 */
                return false;
            }

        };
    }

    /**
     * Title for the Mapper.
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {

        return Messages.ProcessDataToDynamicOrgIdentifierMappingSection_ProcessDataToDynamicOrgIdentifierMapper_title0;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        super.doRefresh();
        aboutToBeShown();
    }

    /**
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        EObject input = getInput();
        if (input != null) {
            MapperViewerInput mapperInput =
                    new MapperViewerInput(input, input, input);
            setMapperViewerInput(mapperInput);

        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getMappingCommandFactory()
     * 
     * @return
     * @deprecated
     */

    @Deprecated
    @Override
    protected IMappingCommandFactory getMappingCommandFactory() {

        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getMappingCommandFactory2()
     * 
     * @return
     */
    @Override
    protected IMappingCommandFactory2 getMappingCommandFactory2() {

        return commandFactory;

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getProblemMarkerDataMappingSourcePath(java.lang.Object)
     * 
     * @param source
     * @return
     */
    @Override
    protected String getProblemMarkerDataMappingSourcePath(Object source) {
        String path = null;
        if (source instanceof ConceptPath) {
            path = ((ConceptPath) source).getPath();
        }
        return path;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getProblemMarkerDataMappingTargetPath(java.lang.Object)
     * 
     * @param target
     * @return
     */
    @Override
    protected String getProblemMarkerDataMappingTargetPath(Object target) {
        String path = null;
        if (target instanceof DynamicOrgIdentiferPath) {
            path = ((DynamicOrgIdentiferPath) target).getPath();
        } else if (target instanceof Organization) {
            path = ((Organization) target).getId();
        }
        return path;

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getProblemMarkerDataMappingURIs(java.lang.String,
     *      boolean)
     * 
     * @param dataMappingPath
     * @param isSourcePath
     * @return
     */
    @Override
    protected Collection<String> getProblemMarkerDataMappingURIs(
            String dataMappingPath, boolean isSourcePath) {
        Collection<String> uris = new ArrayList<String>();
        if (getInput() instanceof Process) {
            Process process = (Process) getInput();

            Object otherElement =
                    Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DynamicOrganizationMappings());
            if (otherElement != null) {
                DynamicOrganizationMappings dynamicOrganizationMappings =
                        (DynamicOrganizationMappings) otherElement;

                EList<DynamicOrganizationMapping> dynamicparticipantMapping =
                        dynamicOrganizationMappings
                                .getDynamicOrganizationMapping();

                for (DynamicOrganizationMapping eachDynamicparticipantMapping : dynamicparticipantMapping) {
                    Resource resource =
                            eachDynamicparticipantMapping.eResource();
                    uris.add(resource
                            .getURIFragment(eachDynamicparticipantMapping));
                }
            }
        }
        return uris;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getAutomapContribution()
     * 
     * @return
     */
    @Override
    protected ActionContributionItem getAutomapContribution() {
        /*
         * Return null as we do not want an automap button for this mapper
         * section
         */
        return null;
    }

}
