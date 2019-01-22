/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.wsdl.Message;

import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class WebServiceScriptProperties extends MapperScriptProperties {

    /** BW service identifier. */
    private static final String BW_SERVICE = "BW Service"; //$NON-NLS-1$

    /**
     * @param mappingDirection
     */
    public WebServiceScriptProperties(MappingDirection mappingDirection) {
        super(mappingDirection);
    }

    @Override
    protected AbstractScriptInfoProvider getScriptInfoProvider(
            String scriptGrammar) {
        if (ProcessJsConsts.JAVASCRIPT_GRAMMAR.equals(scriptGrammar)) {
            return new WebServiceJavaScriptProvider(scriptGrammar);
        } else {
            return new WebServiceXPathScriptProvider(scriptGrammar);
        }
    }

    /**
     * @return
     * @see com.com.tibco.xpd.script.ui.internal.BaseScriptSection#
     *      getScriptContext()
     */
    @Override
    public String getScriptContext() {
        if (isBwService()) {
            return ProcessScriptContextConstants.BW_SERVICE_TASK;
        } else {
            return ProcessScriptContextConstants.WEB_SERVICE_TASK;
        }
    }

    /**
     * @return
     */
    private boolean isBwService() {
        boolean isBw = false;
        Activity activity = (Activity) getInput();
        if (activity != null) {
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                TaskService service = task.getTaskService();
                if (service != null) {
                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(service,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if (BW_SERVICE.equals(type)) {
                        isBw = true;
                    }
                }
            }
        }
        return isBw;
    }

    @Override
    public boolean isLoadProcessData() {
        MappingDirection mappingDirection = getMappingDirection();
        if (mappingDirection != null && mappingDirection.name() != null) {
            if (mappingDirection.name().equals(MappingDirection.OUT.name())) {
                return false;
            }
        }
        return true;
    }

    protected List<Part> getWsdlPart() {
        List<Part> wsldParts = new ArrayList<Part>();

        if (getInput() instanceof Activity) {
            ActivityMessageProvider messageAdapter =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider((Activity) getInput());
            if (messageAdapter != null) {
                WebServiceOperation wso =
                        messageAdapter
                                .getWebServiceOperation((Activity) getInput());
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
    protected Message getWsdlMessage() {
        return Xpdl2WsdlUtil.getMessage((Activity) getInput());
    }

}
