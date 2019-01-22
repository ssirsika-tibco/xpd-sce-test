/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.script.model.client;

import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * THis class is specifically for wrapping static JS Classes defined in UML
 * models (which we happen to need to wrap up in a IScriptRelevantData - usually
 * because by doing so we can contribute in a relevant-data-provider which has
 * opportunity to return section-input-specific content.
 * 
 * 
 * @author aallway
 * @since 15 Apr 2015
 */
public class UMLScriptClassWrapperRelevantData extends
        DefaultUMLScriptRelevantData {

    /**
     * Create IScriptRelevantData wrapper for the given UML definition reader JS
     * Class
     */
    public UMLScriptClassWrapperRelevantData(JsClass wrappedClass) {
        super(wrappedClass.getName(), wrappedClass.getName(), false,
                wrappedClass);

        setAdditionalInfo(JScriptUtils.getUmlElementComment(wrappedClass
                .getUmlClass()));
    }

    /**
     * @see com.tibco.xpd.script.model.client.DefaultScriptRelevantData#isStatic()
     * 
     * @return
     */
    @Override
    public boolean isStatic() {
        /*
         * THis class is specifcally for wrapping static JS Classes defined in
         * UML models (which we happen to need to wrap up in a
         * IScriptRelevantData - usually because by doing so we can contribute
         * in a relevant-data-provider which has opportunity to return
         * section-input-specific content.
         * 
         * So is always static
         */
        return true;
    }
}
