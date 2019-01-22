/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties.processifc;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
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
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * @author NWilson
 * 
 */
public class ProcessIfcMessageMethodOutputMappingSection extends
        AbstractItemProviderMappingSection implements IPluginContribution {

    @Override
    protected IStructuredContentProvider createMappingContentProvider() {
        return new ProcessIfcMappingContentProvider(DirectionType.IN_LITERAL);
    }

    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        return new ParamMappingContentProvider(DirectionType.IN_LITERAL);
    }

    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return new ProcIfcPartMappingContentProvider(DirectionType.IN_LITERAL);
    }

    @Override
    protected IMappingCommandFactory getMappingCommandFactory() {
        return new ProcessIfcMappingCmdFactory(MappingDirection.IN);
    }

    @Override
    protected String getTitle() {
        return Messages.ProcessIfcEndEventMappingSection_ProcessToService_title;
    }

    // The mapping section required to be shown as a read-only section.
    @Override
    protected IMappingTransferValidator getTransferValidator() {
        return new IMappingTransferValidator() {
            public boolean isValidTransfer(Object source, Object target) {
                return false;
            }

            public boolean canAcceptMultipleInputs(Object target) {
                return false;
            }
        };
    }

    public ProcessIfcMessageMethodOutputMappingSection() {
        super(MappingDirection.IN);
        ParameterLabelProvider parameterLabelProvider =
                new ParameterLabelProvider();
        setMapperLabelProvider(new MapperLabelProvider(parameterLabelProvider,
                parameterLabelProvider));

    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        getMapperViewer().setDeleteDisable(true);
        EObject input = getInput();
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

    @Override
    public boolean select(Object toTest) {
        boolean result = false; // super.select(toTest);
        if (!CapabilityUtil.isDeveloperActivityEnabled()) {
            return false;
        }
        EObject eo = null;
        if (toTest instanceof EObject) {
            eo = (EObject) toTest;
        } else if (toTest instanceof IAdaptable) {
            eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
        }

        if (eo != null) {
            if (eo instanceof StartMethod) {
                StartMethod startMethod = (StartMethod) eo;
                if (TriggerType.MESSAGE_LITERAL == startMethod.getTrigger()
                        && hasMappings(startMethod)) {
                    result = true;
                }
            } else if (eo instanceof Activity
                    && ProcessInterfaceUtil.isEventImplemented((Activity) eo)
                    && EventFlowType.FLOW_END_LITERAL == EventObjectUtil
                            .getFlowType((Activity) eo)) {
                if (ProcessInterfaceUtil
                        .getImplementedStartMethod((Activity) eo) != null) {

                    StartMethod startMethod =
                            ProcessInterfaceUtil
                                    .getImplementedStartMethod((Activity) eo);
                    if (TriggerType.MESSAGE_LITERAL == startMethod.getTrigger()
                            && hasMappings(startMethod)) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public String getLocalId() {
        return "developer.interfaceEndEventMapping"; //$NON-NLS-1$
    }

    public String getPluginId() {
        return Activator.PLUGIN_ID;
    }

    /**
     * @param eo
     * @return
     */
    private boolean hasMappings(InterfaceMethod ifcMethod) {
        if (ifcMethod.getTriggerResultMessage() != null) {
            Message message = ifcMethod.getTriggerResultMessage().getMessage();
            if (message != null) {
                List<DataMapping> dataMappings = message.getDataMappings();
                for (DataMapping dataMapping : dataMappings) {
                    if (DirectionType.IN_LITERAL == dataMapping.getDirection()) {
                        return true;
                    }
                }
            }
        }
        return false;
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
