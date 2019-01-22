/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties.processifc;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.PartProxy;
import com.tibco.xpd.implementer.script.WsdlPartPath;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.mapper.Mapper;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Content provider to the {@link Mapper}, which is responsible for constructing
 * the lines in between the source and target elements of the mapper.
 * 
 * @author rsomayaj
 * 
 */
public class ProcessIfcMappingContentProvider implements
        IStructuredContentProvider {

    private final DirectionType directionType;

    /**
     * @param directionType
     * @param inLiteral
     */
    public ProcessIfcMappingContentProvider(DirectionType directionType) {
        this.directionType = directionType;

    }

    @SuppressWarnings("unchecked")
    public Object[] getElements(Object inputElement) {
        Activity activity = null;
        List<Mapping> result = new ArrayList<Mapping>();
        InterfaceMethod interfaceMethod = null;
        TriggerResultMessage trm = null;
        List<DataMapping> dataMappings = new ArrayList<DataMapping>();
        if (inputElement instanceof InterfaceMethod) {
            interfaceMethod = (InterfaceMethod) inputElement;
            if (interfaceMethod.getTriggerResultMessage() != null) {
                trm = interfaceMethod.getTriggerResultMessage();
                dataMappings.addAll(trm.getMessage().getDataMappings());
            }
        }
        List<DataMapping> inDatamappings =
                EMFSearchUtil.findManyInList(dataMappings,
                        Xpdl2Package.eINSTANCE.getDataMapping_Direction(),
                        DirectionType.IN_LITERAL);

        List<DataMapping> outDatamappings =
                EMFSearchUtil.findManyInList(dataMappings,
                        Xpdl2Package.eINSTANCE.getDataMapping_Direction(),
                        DirectionType.OUT_LITERAL);

        if (DirectionType.OUT_LITERAL.equals(directionType)) {
            for (DataMapping dataMapping : outDatamappings) {
                String target = dataMapping.getActual().getText();
                ConceptPath resolveParameter =
                        ConceptUtil.resolveConceptPath(interfaceMethod, target);
                if (resolveParameter == null && activity != null) {
                    resolveParameter =
                            ConceptUtil.resolveConceptPath(activity, target);
                }

                String source = dataMapping.getFormal();
                IWsdlPath wsdlPath =
                        WsdlUtil.resolveParameter(interfaceMethod,
                                source,
                                false);

                if (wsdlPath == null) {
                    TriggerResultMessage triggerResultMessage =
                            interfaceMethod.getTriggerResultMessage();
                    if (triggerResultMessage != null) {
                        Object otherElement =
                                Xpdl2ModelUtil
                                        .getOtherElement(triggerResultMessage,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_PortTypeOperation());
                        if (otherElement instanceof PortTypeOperation
                                && resolveParameter != null) {
                            Object item = resolveParameter.getItem();
                            if (item instanceof FormalParameter) {

                                wsdlPath =
                                        new WsdlPartPath(
                                                (PortTypeOperation) otherElement,
                                                PartProxy
                                                        .getPart((FormalParameter) item),
                                                true, false);
                            }
                        }
                    }
                }

                Mapping mapping =
                        new Mapping(wsdlPath, resolveParameter, dataMapping);
                mapping.setEditable(false);
                result.add(mapping);
            }
        } else {
            for (DataMapping dataMapping : inDatamappings) {
                String target = dataMapping.getFormal();
                IWsdlPath wsdlPath =
                        WsdlUtil
                                .resolveParameter(interfaceMethod, target, true);

                String source = dataMapping.getActual().getText();

                ConceptPath resolveParameter =
                        ConceptUtil.resolveConceptPath(interfaceMethod, source);

                if (wsdlPath == null) {
                    TriggerResultMessage triggerResultMessage =
                            interfaceMethod.getTriggerResultMessage();
                    if (triggerResultMessage != null) {
                        Object otherElement =
                                Xpdl2ModelUtil
                                        .getOtherElement(triggerResultMessage,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_PortTypeOperation());
                        if (otherElement instanceof PortTypeOperation
                                && resolveParameter != null) {
                            Object item = resolveParameter.getItem();
                            if (item instanceof FormalParameter) {
                                wsdlPath =
                                        new WsdlPartPath(
                                                (PortTypeOperation) otherElement,
                                                PartProxy
                                                        .getPart((FormalParameter) item),
                                                false, true);
                            }
                        }
                    }
                }

                Mapping mapping =
                        new Mapping(resolveParameter, wsdlPath, dataMapping);
                mapping.setEditable(false);
                result.add(mapping);
            }
        }
        return result.toArray();

    }

    public void dispose() {

    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
