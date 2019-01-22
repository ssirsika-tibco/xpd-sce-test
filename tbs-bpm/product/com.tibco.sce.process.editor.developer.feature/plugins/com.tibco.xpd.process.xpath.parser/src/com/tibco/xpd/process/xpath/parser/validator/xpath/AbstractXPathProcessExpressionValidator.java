/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.xpath.parser.validator.xpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.process.js.parser.validator.IProcessValidator;
import com.tibco.xpd.process.xpath.parser.util.XPathScriptParserUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.ISymbolTable;

/**
 * 
 * 
 * <p>
 * <i>Created: 20 Feb 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public abstract class AbstractXPathProcessExpressionValidator extends
        AbstractXPathExpressionValidator implements IProcessValidator {

    private String processDestination;

    private String scriptType;

    public void setProcessDestination(String processDestination) {
        this.processDestination = processDestination;
    }

    protected String getProcessDestination() {
        return this.processDestination;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    protected String getScriptType() {
        return this.scriptType;
    }

    public List<JsClass> getSupportedJsClasses() {
        List<JsClass> supportedJsClasses = XPathScriptParserUtil
                .getSupportedJsClasses(getProcessDestination(), getScriptType());
        return supportedJsClasses;
    }

    @Override
    protected Map<String, IScriptRelevantData> getSupportedScriptRelevantData() {
        Map<String, IScriptRelevantData> supportedVariables = null;
        ISymbolTable symbolTable = getSymbolTable();
        if (symbolTable != null) {
            supportedVariables = symbolTable.getScriptRelevantDataTypeMap();
        }
        if (supportedVariables == null) {
            supportedVariables = new HashMap<String, IScriptRelevantData>();
        }
        return supportedVariables;
    }

    @Override
    protected IScriptRelevantData getSupportedScriptRelevantData(String name) {
        IScriptRelevantData supportedScriptRelevantData = null;
        Map<String, IScriptRelevantData> supportedVariables = null;
        ISymbolTable symbolTable = getSymbolTable();
        if (symbolTable != null) {
            supportedVariables = symbolTable.getScriptRelevantDataTypeMap();
        }
        if (supportedVariables == null) {
            supportedVariables = new HashMap<String, IScriptRelevantData>();
        }
        supportedScriptRelevantData = supportedVariables.get(name);
        return supportedScriptRelevantData;
    }

    @Override
    protected List<String> getSupportedMethodNameList() {
        List<String> supportedMethodNameList = new ArrayList<String>();
        List<JsClass> supportedJsClasses = getSupportedJsClasses();
        if (supportedJsClasses != null) {
            for (JsClass jsClass : supportedJsClasses) {
                if (jsClass != null) {
                    // Get the list of all the methods
                    List<JsMethod> methodList = jsClass.getMethodList();
                    if (methodList != null) {
                        for (JsMethod jsMethod : methodList) {
                            if (jsMethod != null) {
                                supportedMethodNameList.add(jsMethod.getName());
                            }
                        }
                    }
                }
            }
        }
        return supportedMethodNameList;
    }

    @Override
    protected List<JsMethod> getSupportedMethodList() {
        List<JsMethod> supportedMethodList = new ArrayList<JsMethod>();
        List<JsClass> supportedJsClasses = getSupportedJsClasses();
        if (supportedJsClasses != null) {
            for (JsClass jsClass : supportedJsClasses) {
                if (jsClass != null) {
                    // Get the list of all the methods
                    List<JsMethod> methodList = jsClass.getMethodList();
                    if (methodList != null) {
                        for (JsMethod jsMethod : methodList) {
                            if (jsMethod != null) {
                                supportedMethodList.add(jsMethod);
                            }
                        }
                    }
                }
            }
        }
        return supportedMethodList;
    }

    @Override
    protected JsMethod getSupportedMethod(String methodName) {
        JsMethod returnJsMethod = null;
        List<JsClass> supportedJsClasses = getSupportedJsClasses();
        if (supportedJsClasses != null) {
            for (JsClass jsClass : supportedJsClasses) {
                if (jsClass != null) {
                    // Get the list of all the methods
                    List<JsMethod> methodList = jsClass.getMethodList();
                    if (methodList != null) {
                        for (JsMethod jsMethod : methodList) {
                            if (jsMethod != null
                                    && jsMethod.getName().equals(methodName)) {
                                return jsMethod;
                            }
                        }
                    }
                }
            }
        }
        return returnJsMethod;
    }

    public void setVarNameResolver(IVarNameResolver varNameResolver) {
        // Do nothing
    }

    private String expectedResultantDataType = null;

    public void setExpectedResultantDataType(String dataType) {
        this.expectedResultantDataType = dataType;
    }

    public String getExpectedResultantDataType() {
        return this.expectedResultantDataType;
    }
    

}
