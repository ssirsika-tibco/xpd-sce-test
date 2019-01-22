/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.tools;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Message;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.BaseTypeUtil;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.WsdlPathFactory;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.process.xpath.model.XPathConsts;
import com.tibco.xpd.process.xpath.parser.util.ProcessXPathUtil;
import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.developer.rules.ServicesTypeUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * 
 * <p>
 * <i>Created: 07 February 2008</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class BwServiceMapperScriptTool extends XPathScriptTool {

    private ScriptInformation scriptInformation = null;

    public BwServiceMapperScriptTool(ScriptInformation scriptInformation) {
        super(scriptInformation);
        this.scriptInformation = scriptInformation;
    }

    @Override
    protected Process getProcess() {
        Process process = null;
        if (scriptInformation != null) {
            process = Xpdl2ModelUtil.getProcess(scriptInformation);
        }
        return process;
    }

    @Override
    protected String getScript() {
        return ProcessScriptUtil.getTaskScript(scriptInformation);
    }

    @Override
    protected String getScriptType() {
        return XPathScriptParserUtil.BWSERVICE_TASK;
    }

    @Override
    protected Activity getActivity() {
        Activity activity = null;
        if (scriptInformation != null) {
            activity = Xpdl2ModelUtil.getParentActivity(scriptInformation);
        }
        return activity;
    }

    protected DataMapping getDataMapping() {
        DataMapping dataMapping = null;
        if (scriptInformation != null
                && scriptInformation.eContainer() instanceof DataMapping) {
            dataMapping = (DataMapping) scriptInformation.eContainer();
        }
        return dataMapping;
    }

    @Override
    protected Part getWsdlPart() {
        Part wsldPart = null;
        if (isOutScriptInformation()) {
            ActivityMessageProvider messageAdapter =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(getActivity());
            if (messageAdapter != null) {
                WebServiceOperation wso =
                        messageAdapter.getWebServiceOperation(getActivity());
                if (wso != null) {
                    Message message = Xpdl2WsdlUtil.getMessage(getActivity());
                    if (message != null) {
                        Map parts = message.getParts();
                        if (parts != null && !parts.isEmpty()) {
                            String partName = null;
                            Set keys = parts.keySet();
                            for (Object key : keys) {
                                Object obj = parts.get(key);
                                if (obj instanceof Part) {
                                    Part part = (Part) obj;
                                    if (part.getElementName() != null) {
                                        partName =
                                                part.getElementName()
                                                        .getLocalPart();
                                    } else {
                                        partName = part.getName();
                                    }
                                    break;
                                }
                            }
                            if (partName != null) {
                                wsldPart =
                                        WsdlPathFactory.getPart(wso,
                                                partName,
                                                false);
                            }
                        }
                    }
                }
            }
        }
        return wsldPart;
    }

    @Override
    boolean isWsdlSupported() {
        boolean wsdlSupported = true;
        if (!isOutScriptInformation()) {
            wsdlSupported = false;
        }
        return wsdlSupported;
    }

    @Override
    IScriptRelevantData getMappingType() {
        IScriptRelevantData type = null;
        if (getDataMapping() != null) {
            String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
            type = new DefaultScriptRelevantData(sType, sType, false);
            if (!isOutScriptInformation()) {
                IWsdlPath wsdlPath =
                        WsdlUtil.resolveParameter(getActivity(),
                                getDataMapping().getFormal(),
                                true);
                if (wsdlPath != null) {
                    XSDTypeDefinition wsdlType = wsdlPath.getType();
                    if (wsdlType instanceof XSDComplexTypeDefinition
                            && wsdlType.getBaseType() != null
                            && (wsdlType.getBaseType() instanceof XSDSimpleTypeDefinition)) {
                        wsdlType = wsdlType.getRootType();
                    }
                    Set<? extends XSDTypeDefinition> types;
                    if (wsdlType != null) {
                        if (wsdlType instanceof XSDSimpleTypeDefinition) {
                            types =
                                    ServicesTypeUtil
                                            .getSupportedSimple((XSDSimpleTypeDefinition) wsdlType);
                        } else {
                            types = Collections.singleton(wsdlType);
                        }
                        StringBuilder names = new StringBuilder();
                        for (XSDTypeDefinition next : types) {
                            if (next.getName() != null) {
                                if (names.length() != 0) {
                                    names.append(',');
                                }
                                names.append(next.getName());
                            }
                        }
                        type =
                                new DefaultScriptRelevantData(wsdlType.getName(), names
                                        .toString(), wsdlPath.isArray());
                    }
                }
            } else {
                String target = DataMappingUtil.getTarget(getDataMapping());
                if (target != null) {
                    ConceptPath conceptPath =
                            ConceptUtil.resolveConceptPath(getActivity(),
                                    target);
                    if (conceptPath != null) {
                        if (conceptPath.getItem() instanceof ProcessRelevantData) {
                            ProcessRelevantData prd =
                                    (ProcessRelevantData) conceptPath.getItem();
                            type =
                                    ProcessUtil
                                            .convertToScriptRelevantData(prd,
                                                    WorkingCopyUtil
                                                            .getProjectFor(getActivity()),
                                                    prd.isIsArray());
                        } else if (conceptPath.getItem() instanceof Property) {
                            Property property =
                                    (Property) conceptPath.getItem();
                            if (property.getType() instanceof PrimitiveType) {
                                PrimitiveType primitiveType =
                                        (PrimitiveType) property.getType();
                                type =
                                        new DefaultScriptRelevantData(
                                                primitiveType.getName(),
                                                primitiveType.getName(),
                                                property.getUpper() == -1);
                            } else if (property.getType() instanceof Class) {
                                Class umlClass = (Class) property.getType();
                                String className = umlClass.getName();
                                type =
                                        new DefaultUMLScriptRelevantData(
                                                className, className, property
                                                        .getUpper() == -1,
                                                new DefaultJsClass(umlClass));
                            }
                        }
                    }
                }
            }
        }
        return type;
    }

    private boolean isOutScriptInformation() {
        boolean isOutScriptInformation = false;
        if (scriptInformation != null) {
            DirectionType direction = scriptInformation.getDirection();
            if (direction != null
                    && direction.getName().equals(DirectionType.OUT_LITERAL
                            .getName())) {
                isOutScriptInformation = true;
            }
        }
        return isOutScriptInformation;
    }

}
