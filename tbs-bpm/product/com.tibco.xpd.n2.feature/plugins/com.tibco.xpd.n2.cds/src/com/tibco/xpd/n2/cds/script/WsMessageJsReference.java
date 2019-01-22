/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptConceptUtil;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsDataType;
import com.tibco.xpd.script.model.client.JsExpression;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.internal.client.IContentAssistIconProvider;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;

/**
 * 
 * <p>
 * <i>Created: 18 Oct 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class WsMessageJsReference implements JsReference, IJsElementExt {

	protected Part part;

	protected String contentAssistString;

	private Image image;
	
	private JsClass referencedJsClass;
	
    private IContentAssistIconProvider contentAssistIconProvider;

    public WsMessageJsReference(Part part) {
        this.part = part;
    }

	public String getComment() {		
		return "";//$NON-NLS-1$
	}

	public String getDataType() {
		return JsConsts.UNDEFINED_DATA_TYPE;
	}
	
	public JsClass getReferencedJsClass(){
	    if(referencedJsClass == null){	        
	        if (part != null) {
                Class conceptClass =
                    JavaScriptConceptUtil.INSTANCE
                            .getConceptClass(part);
                if (conceptClass != null) {
                    DefaultJsClass jsClass =
                            new DefaultJsClass(conceptClass, CDSUtils
                                    .getDefaultCDSMultipleClass());
                    jsClass.setContentAssistIconProvider(contentAssistIconProvider);
                    referencedJsClass = jsClass;
                }
            }
	    }
	    return referencedJsClass;
	}
	
	public boolean isMultiple() {        
        if (isPartMultiple()){
            return true;
        }
        return false;
    }
	
	private boolean isPartMultiple(){
	    if (part != null) {
            //TODO
        }
        return false;
	}

	public String getName() {
		return part.getName();
	}

	public String getContentAssistString() { 
	    return getName();
	}

	public Image getIcon() {
        if (this.image != null) {
            return this.image;
        } else if (contentAssistIconProvider != null) {
            return contentAssistIconProvider.getIcon(this);
        }
        return null;
    }

	public void setIcon(Image image) {
		this.image = image;
	}

    public JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses) {
        return new JsDataType();
    }
    
    public void setContentAssistIconProvider(
            IContentAssistIconProvider contentAssistIconProvider) {
        this.contentAssistIconProvider = contentAssistIconProvider;
    }

    public boolean isStatic() {
        return true;
    }

    public boolean isReadOnly() {
        return false;
    }
    
    public String getBaseDataType() {
        return getDataType();
    }
    
}
