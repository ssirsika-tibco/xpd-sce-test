/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.transform.properties;

import com.tibco.xpd.implementer.resources.xpdl2.properties.MapperScriptProperties;
import com.tibco.xpd.implementer.resources.xpdl2.properties.WebServiceXPathScriptProvider;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * @author nwilson
 */
public class TransformScriptProperties extends MapperScriptProperties {

    /**
     * @param mappingDirection
     */
    public TransformScriptProperties(MappingDirection mappingDirection) {
        super(mappingDirection);
    }

    @Override
    protected AbstractScriptInfoProvider getScriptInfoProvider(String grammar) {
        return new WebServiceXPathScriptProvider(grammar);
    }

    /**
     * @return
     * @see com.com.tibco.xpd.script.ui.internal.BaseScriptSection#
     *      getScriptContext()
     */
    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.TRANSFORM_SCRIPT_TASK;
    }

    @Override
    public boolean isLoadProcessData() {
        MappingDirection mappingDirection = getMappingDirection();
        if (mappingDirection != null && mappingDirection.name() != null) {
            if (mappingDirection.name().equals(MappingDirection.OUT.name())) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return
     * @see com.com.tibco.xpd.script.ui.internal.BaseScriptSection#
     *      getCurrentSetScriptGrammarId()
     */
    @Override
    public String getCurrentSetScriptGrammarId() {
        Activity activity = (Activity) getInput();
        String grammarId = null;
        DirectionType dir;
        if (MappingDirection.IN.equals(getMappingDirection())) {
            dir = DirectionType.IN_LITERAL;
        } else {
            dir = DirectionType.OUT_LITERAL;
        }
        grammarId = ScriptGrammarFactory.getScriptGrammar(activity, dir);
        if (grammarId == null) {
            grammarId = ScriptGrammarFactory.XPATH;
        }
        return grammarId;
    }

}
