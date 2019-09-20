/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * 
 * <p>
 * <i>Created: 119 Dec 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class DynamicJsAttribute extends DefaultMultipleJsClassResolver implements JsAttribute {

	protected String contentAssistString;

	private Image image;
	
	private boolean isMultiple;

	private String comment = null;
	
	private String dataType = JsConsts.UNDEFINED_DATA_TYPE;
		
	private String name = null;
	
    private IContentAssistIconProvider contentAssistIconProvider;
    
    public DynamicJsAttribute(String name, String dataType, boolean isMultiple,
            String comment, Image image) {
        this.name = name;
        this.dataType = dataType;
        this.isMultiple = isMultiple;
        this.comment = comment;
        this.image = image;
    }
    
    public DynamicJsAttribute(String name, String dataType, boolean isMultiple,
            String comment, Image image, Class multipleClass) {
        this.name = name;
        this.dataType = dataType;
        this.isMultiple = isMultiple;
        this.comment = comment;
        this.image = image;
        setMultipleClass(multipleClass);
    }
	
	@Override
    public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
        this.comment = comment;
    }
	

	@Override
    public String getContentAssistString() {
//	    String contentAssistString = getName();
//	    if(isMultiple()){
//	        contentAssistString+= JsConsts.ARRAY_CONTENT_ASSIST_SUFFIX; //$NON-NLS-1$
//	    }
//	    return contentAssistString;
	    return getName();
	}

	@Override
    public Image getIcon() {
        if (this.image != null) {
            return this.image;
        } else if (contentAssistIconProvider != null) {
            return contentAssistIconProvider.getIcon(this);
        }
        return null;
    }

	void setIcon(Image image) {
		this.image = image;
	}
	
    @Override
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        JsDataType dataType = new JsDataType();
        dataType.setJsExpression(jsExpression);
        if (jsExpression != null) {
            IScriptRelevantData scriptRelevantData =
                    JScriptUtils.resolveJavaScriptStringType(getName(),
                            this.getDataType(),
                            JScriptUtils.isDataTypeMultiple(jsExpression,
                                    isMultiple()),
                            supportedJsClasses,
                            getMultipleClass(), null);
            // Check if multiplicity match
            if (!JScriptUtils.multiplicityMatch(jsExpression, isMultiple())) {
                if (isMultiple()) {
                    scriptRelevantData =
                            JScriptUtils.resolveJavaScriptStringType(getName(),
                                    getMultipleJsClassName(),
                                    false,
                                    supportedJsClasses,
                                    getMultipleClass(), null);
                } else {
                    dataType
                            .setUndefinedCause(JsConsts.ARRAY_NOT_EXPECTED_DATA_TYPE_CAUSE);
                    return dataType;
                }
            }
            if (!JScriptUtils.hasMoreJSChildren(jsExpression)) {
                if (this.getDataType() != null
                        && this.getDataType().equals(getMultipleJsClassName())
                        && !JScriptUtils.isDataTypeMultiple(jsExpression,
                                isMultiple())) {
                    scriptRelevantData =
                            JScriptUtils
                                    .resolveJavaScriptNotMultipleArrayType(getName(),
                                            JScriptUtils
                                                    .isDataTypeMultiple(jsExpression,
                                                            isMultiple()),
                                            supportedJsClasses,
                                            getMultipleClass());
                } else {
                    scriptRelevantData =
                            JScriptUtils.resolveJavaScriptStringType(getName(),
                                    this.getDataType(),
                                    JScriptUtils
                                            .isDataTypeMultiple(jsExpression,
                                                    isMultiple()),
                                    supportedJsClasses,
                                    getMultipleClass(), null);
                }
                dataType.setType(scriptRelevantData);
                return dataType;
            } else {
                JsExpression nextJsExpression =
                        jsExpression.getNextExpression();
                if (nextJsExpression != null) {
                    if (scriptRelevantData instanceof IUMLScriptRelevantData) {
                        JsClass attributeTypeClass =
                                ((IUMLScriptRelevantData) scriptRelevantData)
                                        .getJsClass();
                        attributeTypeClass.setIcon(this.getIcon());
                        dataType =
                                attributeTypeClass
                                        .getDataTypeForJSExpression(jsExpression,
                                                supportedJsClasses);
                        return dataType;
                    } else {
                        if (getDataType() != null
                                && !getDataType()
                                        .equals(JsConsts.UNDEFINED_DATA_TYPE)) {
                            if (nextJsExpression instanceof JsExpressionMethod) {
                                dataType
                                        .setUndefinedCause(JsConsts.UNKNOWN_METHOD_DATA_TYPE_CAUSE);
                            } else {
                                dataType
                                        .setUndefinedCause(JsConsts.UNKNOWN_PROPERTY_DATA_TYPE_CAUSE);
                            }
                        }
                    }
                }
            }
        }
        return dataType;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean isMultiple) {
        this.isMultiple = isMultiple;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IScriptRelevantData getScriptRelevantData() {
        return null;
    }    
    
    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }
}
