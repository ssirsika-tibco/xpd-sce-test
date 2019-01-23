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
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.WebServiceOperation;

/**
 * @author mtorres
 */
public class WebServiceXpathRelevantDataProvider extends
        DefaultXpathRelevantDataProvider {
    
    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        if (isEvent()) {
            if (getEvent() instanceof EndEvent) {
                return super.getScriptRelevantDataList();
            } else {
                return Collections.emptyList();
            }
        } else if (isReceiveTask()) {
            return Collections.emptyList();
        } else if (isSendTask()) {
            return super.getScriptRelevantDataList();
        } else if (isWebServiceTask()) {
            if (isMappedScript()) {
                if (isInputScript()) {
                    return super.getScriptRelevantDataList();
                } else {
                    return Collections.emptyList();
                }
            } else {
                if (isInputScript()) {
                    return super.getScriptRelevantDataList();
                } else {
                    return Collections.emptyList();
                }
            }
        }
        return Collections.emptyList();
    }
    
    @Override
    public List getComplexScriptRelevantDataList() {
        if (isWebServiceTask()) {
            if (isMappedScript()) {
                if (isInputScript()) {
                    return Collections.emptyList();
                } else {
                    return getOutputAdditionalScriptData();
                }
            } else {
                if (isInputScript()) {
                    return Collections.emptyList();
                } else {
                    return getOutputAdditionalScriptData();
                }
            }
        }
        return Collections.emptyList();
    }
    
    protected Event getEvent(){
        Event event = null;
        if(getActivity() != null){
            Activity activity = getActivity();
            event = activity.getEvent();
        }
        return event;
    }
    
    private boolean isEvent(){
        if(getEvent() != null){
            return true;
        }
        return false;
    }
    
    private boolean isReceiveTask(){
        if(getActivity() != null){
            Activity activity = getActivity();
            Implementation implementation = activity.getImplementation();
            if(implementation instanceof Task){
                Task task = (Task) implementation;
                if(task.getTaskReceive() != null){
                    return true;
                }
            }
        }
        return false;
    }   
    
    private boolean isSendTask(){
        if(getActivity() != null){
            Activity activity = getActivity();
            Implementation implementation = activity.getImplementation();
            if(implementation instanceof Task){
                Task task = (Task) implementation;
                if(task.getTaskSend() != null){
                    return true;
                }
            }
        }
        return false;
    }   
    
    private boolean isWebServiceTask() {
        if (getActivity() != null) {
            Activity activity = getActivity();
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                if (task.getTaskService() != null) {
                    TaskService service = task.getTaskService();
                    if (service.getImplementation() != null
                            && service
                                    .getImplementation()
                                    .equals(ImplementationType.WEB_SERVICE_LITERAL)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    

    private boolean isMappedScript() {
        if (getEObject() instanceof ScriptInformation) {
            if (getEObject().eContainer() instanceof DataMapping) {
                return true;
            }
        }
        return false;
    }

    private boolean isInputScript() {
        if (getContext() instanceof DataMapping) {
            DataMapping dm = (DataMapping) getContext();
            if (dm.getDirection() != null
                    && dm.getDirection().equals(DirectionType.IN_LITERAL)) {
                return true;
            }
        } else if (getContext() instanceof ScriptInformation) {
            ScriptInformation si = (ScriptInformation) getContext();
            if (si.getDirection() != null
                    && si.getDirection().equals(DirectionType.IN_LITERAL)) {
                return true;
            }
        }
        return false;
    }
    
    private List<IScriptRelevantData> getOutputAdditionalScriptData() {
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
