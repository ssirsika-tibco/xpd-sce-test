/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.rql.parser.script;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;

/**
 * 
 * @author Miguel Torres
 * 
 */
public class RQLJsAttribute implements JsAttribute{

	protected Attribute attribute;

	protected String contentAssistString;

	private Image image;
    
    public RQLJsAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public String getComment() {
		return null;
	}

	public String getDataType() {
		return null;
	}
	
	public boolean isMultiple() {
	     return false; 
	}

	public String getName() {
		return attribute.getName();
	}

	public String getContentAssistString() {
	    return getName();
	}

	public Image getIcon() {
		return image;
	}

	public void setIcon(Image image) {
		this.image = image;
	}
	
    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        return null;
    }

    /** {@inheritDoc}. */
    public IScriptRelevantData getScriptRelevantData()  {
        return null;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsAttribute#getUmlType()
     */
    public Type getUmlType() {
        return null;
    }
}
