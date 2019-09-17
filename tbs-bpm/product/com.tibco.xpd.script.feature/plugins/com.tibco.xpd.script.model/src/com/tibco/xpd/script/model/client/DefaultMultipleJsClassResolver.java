/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.script.model.jscript.JScriptUtils;

public abstract class DefaultMultipleJsClassResolver implements IMultipleJsClassResolver {

    protected Class multipleClass;

    @Override
    public String getMultipleJsClassName() {
        JsClass multipleJsClass = getMultipleJsClass();
        return multipleJsClass.getName();
    }

    @Override
    public JsClass getMultipleJsClass() {
        DefaultJsClass defaultJsClass = new DefaultJsClass(getMultipleClass(), getMultipleClass());
        defaultJsClass.setIcon(getIcon());
        defaultJsClass.setContentAssistIconProvider(JScriptUtils.getJsContentAssistIconProvider());
        return defaultJsClass;
    }

    @Override
    public Class getMultipleClass() {
        if (multipleClass == null) {
            multipleClass = JScriptUtils.getDefaultMultipleClass();
        }
        return multipleClass;
    }

    @Override
    public void setMultipleClass(Class multipleClass) {
        this.multipleClass = multipleClass;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    abstract protected Image getIcon();

}
