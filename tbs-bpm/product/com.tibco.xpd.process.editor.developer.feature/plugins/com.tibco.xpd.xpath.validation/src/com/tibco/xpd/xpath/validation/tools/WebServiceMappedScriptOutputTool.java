/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Message;

import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Class;
import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.WsdlPathFactory;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.process.js.model.util.ProcessUtil;
import com.tibco.xpd.process.xpath.model.XPathConsts;
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
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * <p>
 * <i>Created: 5 Dec 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class WebServiceMappedScriptOutputTool extends XPathScriptTool {

    private DataMapping mapping = null;

    public WebServiceMappedScriptOutputTool(DataMapping mapping) {
        super(ProcessScriptUtil.getScriptInformationFromDataMapping(mapping));
        this.mapping = mapping;
    }

    @Override
    protected Process getProcess() {
        Process process = null;
        if (mapping != null) {
            process = Xpdl2ModelUtil.getProcess(mapping);
        }
        return process;
    }

    @Override
    protected String getScript() {
        return ProcessScriptUtil.getDataMappingScript(mapping);
    }

    @Override
    protected String getScriptType() {
        return XPathScriptParserUtil.WEBSERVICE_TASK;
    }

    @Override
    protected Activity getActivity() {
        Activity activity = null;
        if (mapping != null) {
            activity = Xpdl2ModelUtil.getParentActivity(mapping);
        }
        return activity;
    }
    
    protected Part getWsdlPart() {
        Part wsldPart = null;
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
                                if(part.getElementName() != null){
                                    partName = part.getElementName().getLocalPart();
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
        return wsldPart;
    }
    
    @Override
    boolean isWsdlSupported() {
        return true;
    }
    
    @Override
    IScriptRelevantData getMappingType() {
        String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
        IScriptRelevantData type = new DefaultScriptRelevantData(sType,sType,false);        
        String target = DataMappingUtil.getTarget(mapping);
        if (target != null) {
            ConceptPath conceptPath =ConceptUtil.resolveConceptPath(getActivity(), target);
            if(conceptPath != null){
                if (conceptPath.getItem() instanceof ProcessRelevantData) {
                    ProcessRelevantData prd =
                            (ProcessRelevantData) conceptPath.getItem();
                    type =
                            ProcessUtil.convertToScriptRelevantData(prd,
                                    WorkingCopyUtil
                                            .getProjectFor(getActivity()),
                                    prd.isIsArray());
                } else if (conceptPath.getItem() instanceof Property) {
                    Property property = (Property) conceptPath.getItem();
                    if (property.getType() instanceof PrimitiveType) {
                        PrimitiveType primitiveType =
                                (PrimitiveType) property.getType();
                        type =
                                new DefaultScriptRelevantData(primitiveType
                                        .getName(), primitiveType.getName(),
                                        property.getUpper() == -1);
                    } else if (property.getType() instanceof Class) {
                        Class umlClass = (Class) property.getType();
                        String className = umlClass.getName();
                        type =
                                new DefaultUMLScriptRelevantData(className,
                                        className, property.getUpper() == -1,
                                        new DefaultJsClass(umlClass));
                    }
                }
            }
        }
        return type;
    }
    
}
