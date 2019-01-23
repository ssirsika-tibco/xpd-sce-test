/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.processifc;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ParameterLabelProvider;
import com.tibco.xpd.implementer.script.PartProxy;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.ITransformSection;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MapperViewerInput;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * 
 * @author rsomayaj
 * 
 */
public abstract class BaseProcessIfcInputMappingSection extends
        AbstractItemProviderMappingSection implements IPluginContribution {

    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new ProcessIfcMappingContentProvider(DirectionType.OUT_LITERAL);
    }

    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        return new ProcIfcPartMappingContentProvider(DirectionType.OUT_LITERAL);
    }

    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new ParamMappingContentProvider(DirectionType.OUT_LITERAL);
    }

    @Override
    protected IMappingCommandFactory getMappingCommandFactory() {
        return new ProcessIfcMappingCmdFactory(MappingDirection.OUT);
    }

    @Override
    protected String getTitle() {
        return Messages.ProcessIfcMessageMappingSection_ServiceToProcess_label;
    }

    // The mapping section required to be shown as a read-only section.
    @Override
    protected IMappingTransferValidator getTransferValidator() {
        return new IMappingTransferValidator() {
            public boolean isValidTransfer(Object source, Object target) {
                // Not a valid transfer if either the source or the target is a
                // ChoiceConceptPath
                if (source instanceof ChoiceConceptPath
                        || target instanceof ChoiceConceptPath) {
                    return false;
                }
                if (target instanceof ConceptPath) {
                    ConceptPath path = (ConceptPath) target;
                    Object item = path.getItem();
                    if (item instanceof DataField) {
                        DataField dataField = (DataField) item;
                        if (dataField.isCorrelation()) {
                            return true;
                        }

                    }
                }
                return false;
            }

            public boolean canAcceptMultipleInputs(Object target) {
                return false;
            }
        };
    }

    public BaseProcessIfcInputMappingSection() {
        super(MappingDirection.IN);
        ParameterLabelProvider labelProvider = new ParameterLabelProvider();
        setMapperLabelProvider(new MapperLabelProvider(labelProvider,
                labelProvider));

    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        EObject input = getInput();
        getMapperViewer().setDeleteDisable(true);
        if (input != null) {
            MapperViewerInput mapperInput =
                    new MapperViewerInput(input, input, input);
            setMapperViewerInput(mapperInput);
            ITransformSection transform = getCurrentTransformSection();
            if (transform != null) {
                transform.setOwner(input, MappingDirection.IN);
            }
        }
    }

    @Override
    public void dispose() {
        PartProxy.clearParts();
        super.dispose();
    }

    public String getLocalId() {
        return "developer.interfaceMethodMapping"; //$NON-NLS-1$
    }

    public String getPluginId() {
        return Activator.PLUGIN_ID;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#
     * getProblemMarkerDataMappingSourcePath(java.lang.Object)
     */
    @Override
    protected String getProblemMarkerDataMappingSourcePath(Object source) {
        /*
         * Don't think we need to do anything about ifc method mappings because
         * we make up the mappings for ourselves anyway!
         */
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#
     * getProblemMarkerDataMappingTargetPath(java.lang.Object)
     */
    @Override
    protected String getProblemMarkerDataMappingTargetPath(Object target) {
        /*
         * Don't think we need to do anything about ifc method mappings because
         * we make up the mappings for ourselves anyway!
         */
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#
     * getProblemMarkerDataMappingURIs(java.lang.String)
     */
    @Override
    protected Collection<String> getProblemMarkerDataMappingURIs(
            String dataMappingTargetPath, boolean isSourcePath) {
        /*
         * Don't think we need to do anything about ifc method mappings because
         * we make up the mappings for ourselves anyway!
         */
        return null;
    }
}
