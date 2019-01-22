/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.process.xpath.parser.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.Message;

import org.eclipse.wst.wsdl.Part;
import org.w3c.dom.Node;

import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.process.xpath.model.script.DefaultXpathRelevantDataProvider;
import com.tibco.xpd.process.xpath.parser.util.ProcessXPathUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
 /** 
  *@author mtorres
  */
public class BwServiceMapperXpathRelevantDataProvider extends
        DefaultXpathRelevantDataProvider {
    
    
    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        if (!isOutScriptInformation()) {
            return super.getScriptRelevantDataList();
        } else {
            return Collections.emptyList();
        }
    }
    
    @Override
    public List getComplexScriptRelevantDataList() {
        if (isOutScriptInformation()) {
            return getOutputAdditionalScriptData();
        } else {
            return Collections.emptyList();
        }
    }
    
    private boolean isOutScriptInformation() {
        boolean isOutScriptInformation = false;
        if (getScriptInformation() != null) {
            DirectionType direction = getScriptInformation().getDirection();
            if (direction != null
                    && direction.getName().equals(DirectionType.OUT_LITERAL
                            .getName())) {
                isOutScriptInformation = true;
            }
        }
        return isOutScriptInformation;
    }
    
    private ScriptInformation getScriptInformation(){
        if(getContext() instanceof ScriptInformation){
            return (ScriptInformation) getContext();
        }
        return null;
    }
    
    private List getOutputAdditionalScriptData() {
        List additionalScriptData = new ArrayList();
        List<Part> wsdlParts = getWsdlPart();
        for (Part wsdlPart : wsdlParts) {
            Node documentNode =
                    ProcessXPathUtil.getRootNode(wsdlPart,
                            new HashMap<String, String>());
            additionalScriptData.add(documentNode);
            Map<String, String> namespaces =
                    ProcessXPathUtil.getPartNamespaces(wsdlPart);
            additionalScriptData.add(namespaces);
        }        
        return additionalScriptData;
    }
    
    private List<Part> getWsdlPart() {
        List<Part> wsldParts = new ArrayList<Part>();

        if (getActivity() != null) {
            ActivityMessageProvider messageAdapter =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(getActivity());
            if (messageAdapter != null) {
                WebServiceOperation wso =
                        messageAdapter
                                .getWebServiceOperation(getActivity());
                if (wso != null) {
                    Message message = getWsdlMessage();
                    if (message instanceof org.eclipse.wst.wsdl.Message) {
                        Map parts = message.getParts();
                        if (parts != null && !parts.isEmpty()) {
                            wsldParts.addAll(parts.values());
                        }
                    }
                }
            }
        }

        return wsldParts;
    }

    /**
     * @return
     */
    private Message getWsdlMessage() {
        return Xpdl2WsdlUtil.getMessage(getActivity());
    }
}
