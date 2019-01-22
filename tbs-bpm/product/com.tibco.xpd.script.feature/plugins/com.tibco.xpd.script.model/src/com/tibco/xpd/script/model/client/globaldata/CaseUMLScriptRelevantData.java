/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.script.model.client.globaldata;

import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;

/**
 * UMLScript Relevant data class for the CAC.
 * 
 * @author bharge
 * @since 10 Dec 2012
 */
public class CaseUMLScriptRelevantData extends DefaultUMLScriptRelevantData {

    private String typeFQN;

    public CaseUMLScriptRelevantData(String name, String className,
            boolean isArray, JsClass jsClass, String type) {
        setName(name);
        setClassName(className);
        setIsArray(isArray);
        setLoadModel(false);
        this.typeFQN = type;
        if (jsClass != null) {
            if (jsClass.getIcon() != null
                    && !XpdResourcesPlugin.isInHeadlessMode()) {
                setIcon(jsClass.getIcon());
            }
            addJsClass(jsClass);
        }
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractUMLScriptRelevantData#createJsClass(org.eclipse.uml2.uml.Class,
     *      org.eclipse.uml2.uml.Class)
     * 
     * @param umlClass
     * @param multipleUmlClass
     * @return
     */
    @Override
    protected JsClass createJsClass(Class umlClass, Class multipleUmlClass) {

        return getJsClass();
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractUMLScriptRelevantData#getType()
     * 
     * @return
     */
    @Override
    public String getType() {
        return typeFQN;
    }

    @Override
    public void addClass(Class umlClass, Class multipleClass) {
        if (getJsClass() != null) {
            super.addClass(umlClass, multipleClass);
        }
    }
}
