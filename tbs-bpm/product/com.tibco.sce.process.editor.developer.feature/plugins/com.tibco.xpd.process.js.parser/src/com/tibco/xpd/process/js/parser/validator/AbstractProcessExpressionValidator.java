package com.tibco.xpd.process.js.parser.validator;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.process.js.parser.util.Utility;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.internal.validator.jscript.AbstractExpressionValidator;

public abstract class AbstractProcessExpressionValidator extends
        AbstractExpressionValidator implements IProcessValidator {

    private String processDestination;

    private String scriptType;

    private String expectedResultDataType = null;

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

    @Override
    protected List<JsClassDefinitionReader> getClassDefinitionReader() {
        List<JsClassDefinitionReader> classDefinitionReader =
                Utility.getClassDefintionReader(getProcessDestination(),
                        getScriptType());
        return classDefinitionReader;
    }

    @Override
    protected List<String> getSupportedClasses() {
        List<String> supportedClassNames = new ArrayList<String>();
        for (JsClassDefinitionReader jsClassDefinitionReader : getClassDefinitionReader()) {
            List<String> tempSupportedClassNames =
                    jsClassDefinitionReader.getSupportedClassNames();
            if (tempSupportedClassNames != null) {
                supportedClassNames.addAll(tempSupportedClassNames);
            }
        }
        return supportedClassNames;
    }

    @Override
    public List<JsClass> getSupportedJsClasses() {
        List<JsClass> supportedJsClasses =
                Utility.getSupportedJsClasses(getProcessDestination(),
                        getScriptType());
        return supportedJsClasses;
    }

    public void setExpectedResultantDataType(String dataType) {
        this.expectedResultDataType = dataType;
    }

    public String getExpectedResultantDataType() {
        return this.expectedResultDataType;
    }

}
