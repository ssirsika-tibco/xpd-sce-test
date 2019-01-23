/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */package com.tibco.xpd.script.model.client;

import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.resources.XpdResourcesPlugin;

public class DefaultUMLScriptRelevantData extends AbstractUMLScriptRelevantData {

    public DefaultUMLScriptRelevantData() {

    }

    public DefaultUMLScriptRelevantData(String name, String className,
            boolean isArray, JsClass jsClass) {
        setName(name);
        setClassName(className);
        addJsClass(jsClass);
        setIsArray(isArray);
        setLoadModel(false);
        if (jsClass != null && jsClass.getIcon() != null) {
            if (!XpdResourcesPlugin.isInHeadlessMode()) {
                setIcon(jsClass.getIcon());
            }
        }
    }

    @Override
    public URI getURI() {
        return null;
    }
}
