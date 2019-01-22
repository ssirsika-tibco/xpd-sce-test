/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.mapper;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2;
import com.tibco.xpd.processeditor.xpdl2.properties.ProcessScopeDataConceptPathProvider;
import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping;
import com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping Section for Process Data to Physical WorkItem Attribute Mapping ,
 * which is embedded/shown in Work Resource tab of Properties View for Business
 * Process.
 * 
 * @author aprasad
 * @since 30-Oct-2013
 */
public class ProcessDataToWIAttributeMappingSection extends
        AbstractItemProviderMappingSection {

    private ProcessDataToWIAttributeMappingCommandFactory commandFactory;

    /**
     * @param direction
     */
    public ProcessDataToWIAttributeMappingSection() {
        super(MappingDirection.IN);

        setMapperLabelProvider(new MapperLabelProvider(
                new ParameterLabelProvider(), new WorkListFacadeLabelProvider()));

        commandFactory = new ProcessDataToWIAttributeMappingCommandFactory();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection#createSourceContentProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        return new ProcessScopeDataConceptPathProvider();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection#createTargetContentProvider()
     * 
     * @return
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {

        return new WorkListFacadeMapperContentProvider();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection#createMappingContentProvider()
     * 
     * @return
     */
    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new ProcessDataToWIAttributeMappingContentProvider();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTitle()
     * 
     * @return
     */
    @Override
    protected String getTitle() {
        return Messages.ProcessDataToWorkItemAliasMappingSection_WorkListFacade_Mapper_Header_Title2;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getMappingCommandFactory()
     * 
     * @return null
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
        if (target instanceof WorkItemAttributeConceptPath) {
            path = ((WorkItemAttributeConceptPath) target).getPath();
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
        if (getInput() instanceof com.tibco.xpd.xpdl2.Process) {
            com.tibco.xpd.xpdl2.Process process =
                    (com.tibco.xpd.xpdl2.Process) getInput();

            Object otherElement =
                    Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ProcessDataWorkItemAttributeMappings());
            if (otherElement != null) {
                ProcessDataWorkItemAttributeMappings processDataWIAttribMappings =
                        (ProcessDataWorkItemAttributeMappings) otherElement;
                // collect URIs for all mappings
                for (DataWorkItemAttributeMapping dataAttribMapping : processDataWIAttribMappings
                        .getDataWorkItemAttributeMapping()) {

                    Resource resource = dataAttribMapping.eResource();
                    uris.add(resource.getURIFragment(dataAttribMapping));
                }
            }

        }
        return uris;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTransferValidator()
     * 
     * @return
     */
    @Override
    protected IMappingTransferValidator getTransferValidator() {

        return new IMappingTransferValidator() {
            /**
             * Do not allow mappings to WorkListFacade element i.e Root node
             * facade.
             * 
             * @see com.tibco.xpd.mapper.IMappingTransferValidator#isValidTransfer(java.lang.Object,
             *      java.lang.Object)
             * 
             * @param source
             * @param target
             * @return
             */
            @Override
            public boolean isValidTransfer(Object source, Object target) {
                // This is a single level tree and mapping is allowed at this
                // level.
                return true;
            }

            /**
             * 
             * @see com.tibco.xpd.mapper.IMappingTransferValidator#canAcceptMultipleInputs(java.lang.Object)
             * 
             * @param target
             * @return
             */
            @Override
            public boolean canAcceptMultipleInputs(Object target) {
                /**
                 * Concatenation of inputs not allowed.
                 */
                return false;
            }

        };

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
