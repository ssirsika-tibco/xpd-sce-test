/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptConceptUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.WsdlFilter.WsdlDirection;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.WsdlPartConceptPath;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.WebServiceOperation;

/**
 * @author mtorres
 */
public class CdsWebServiceJavaScriptRelevantDataProvider extends
    CdsDefaultJavaScriptRelevantDataProvider {

    @Override
    public List<IScriptRelevantData> getScriptRelevantDataList() {
        if (isMappedScript()) {
            if (isInputScript()) {
                return super.getScriptRelevantDataList();
            } else {
                return getOutputWebServiceScriptRelevantData();
            }
        } else {
            if (isInputScript()) {
                return super.getScriptRelevantDataList();
            } else {
                return getOutputWebServiceScriptRelevantData();
            }
        }
    }   
    
    private List<IScriptRelevantData> getOutputWebServiceScriptRelevantData() {
        Activity activity = getActivity();
        if (activity != null) {
            List<ConceptPath> conceptPaths = getConceptPaths();
            if (conceptPaths != null && !conceptPaths.isEmpty()) {
                List<IScriptRelevantData> scriptRelevantDataList =
                        new ArrayList<IScriptRelevantData>();
                String prefix = "MESSAGE_";//$NON-NLS-1$
                for (ConceptPath conceptPath : conceptPaths) {
                    if (conceptPath instanceof WsdlPartConceptPath) {
                        Part wsdlPart =
                                ((WsdlPartConceptPath) conceptPath).getPart();
                        if (wsdlPart != null
                                && JavaScriptConceptUtil.INSTANCE
                                        .getConceptClass(wsdlPart) != null) {
                            WsMessageJsClass jsClass =
                                    new WsMessageJsClass(prefix, wsdlPart);
                            jsClass.setContentAssistIconProvider(CDSUtils
                                    .getCdsContentAssistIconProvider());
                            IScriptRelevantData newScriptRelevantData =
                                    new DefaultUMLScriptRelevantData(jsClass
                                            .getName(), jsClass.getName(),
                                            false, jsClass);
                            scriptRelevantDataList.add(newScriptRelevantData);
                        }
                    } else if (conceptPath != null) {
                        Object item = conceptPath.getItem();
                        if (item instanceof ProcessRelevantData) {
                            List<IScriptRelevantData> convertToScriptRelevantData =
                                    convertToScriptRelevantData(Collections
                                            .singletonList((ProcessRelevantData) item));
                            if (convertToScriptRelevantData != null
                                    && !convertToScriptRelevantData.isEmpty()) {
                                // Add the prefix to the name
                                for (IScriptRelevantData scriptRelevantData : convertToScriptRelevantData) {
                                    scriptRelevantData.setName(prefix
                                            + scriptRelevantData.getName());
                                    scriptRelevantData.setIcon(CDSUtils
                                            .getCdsContentAssistIconProvider()
                                            .getDefaultIcon());
                                    scriptRelevantDataList
                                            .add(scriptRelevantData);
                                }
                            }
                        }
                    }
                }
                return scriptRelevantDataList;
            }
        }
        return Collections.emptyList();
    }
    
    private List<ConceptPath> getConceptPaths() {
        List<ConceptPath> conceptPaths = new ArrayList<ConceptPath>();
        if (getActivity() != null) {
            ActivityMessageProvider messageAdapter =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(getActivity());
            if (messageAdapter != null) {
                WebServiceOperation wso =
                        messageAdapter.getWebServiceOperation(getActivity());
                if (wso != null) {
                    conceptPaths.addAll(JavaScriptConceptUtil.INSTANCE
                            .getWebServiceChildren(wso,
                                    getActivity(),
                                    getWsdlDirection()));

                }
            }
        }
        return conceptPaths;
    }
    
    /**
     * @return
     */
    private WsdlDirection getWsdlDirection() {
        if (isInputMessage()) {
            return WsdlDirection.IN;
        } else {
            return WsdlDirection.OUT;
        }
    }


}
