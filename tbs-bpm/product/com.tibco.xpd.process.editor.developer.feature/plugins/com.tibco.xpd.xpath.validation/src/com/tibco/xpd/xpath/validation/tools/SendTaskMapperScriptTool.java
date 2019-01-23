/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.xpath.validation.tools;

import java.util.Collections;
import java.util.Set;

import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.implementer.script.BaseTypeUtil;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.implementer.script.WsdlUtil;
import com.tibco.xpd.process.xpath.model.XPathConsts;
import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.model.client.DefaultScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.developer.rules.ServicesTypeUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Process;
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
public class SendTaskMapperScriptTool extends XPathScriptTool {

    private ScriptInformation scriptInformation = null;

    public SendTaskMapperScriptTool(ScriptInformation scriptInformation) {
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
        return XPathScriptParserUtil.WEBSERVICE_TASK;
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
        return null;
    }

    @Override
    boolean isWsdlSupported() {
        return false;
    }

    @Override
    IScriptRelevantData getMappingType() {
        IScriptRelevantData type = null;
        if (getDataMapping() != null) {
            String sType = XPathConsts.XPATH_TYPE_UNDEFINED;
            type = new DefaultScriptRelevantData(sType, sType, false);
            IWsdlPath wsdlPath =
                    WsdlUtil.resolveParameter(getActivity(), getDataMapping()
                            .getFormal(), true);
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
        }
        return type;
    }

}
