/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract class for mapped script expression input rules.
 * 
 * 
 * @author agondal
 * @since 19 Sep 2013
 */
public abstract class AbstractMappedScriptExpressionInputRule extends
        AbstractExpressionRule {

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#createScriptToolIfInterested(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected final ScriptTool createScriptToolIfInterested(
            Expression expression) {

        DataMapping dataMapping =
                (DataMapping) Xpdl2ModelUtil.getAncestor(expression,
                        DataMapping.class);

        if (dataMapping != null
                && DirectionType.IN_LITERAL.equals(MappingRuleUtil
                        .getMappingDirection(dataMapping))) {

            // only interested if its a script mapping
            Object scriptInfoObj =
                    Xpdl2ModelUtil.getOtherElement(dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            if (scriptInfoObj instanceof ScriptInformation) {

                return getScriptTool(dataMapping);
            }
        }

        return null;
    }

    /**
     * Returns script tool for a given dataMapping (which contains the
     * expression being validated). This method will be implemented by
     * sub-classes to provide appropriate script tool based on the given mapping
     * and the ancestor activity type.
     * 
     * @param dataMapping
     * @return ScriptTool
     */
    protected abstract ScriptTool getScriptTool(DataMapping dataMapping);

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getExpressionHostForScriptTool(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected final UniqueIdElement getExpressionHostForScriptTool(
            Expression expression) {

        DataMapping dataMapping =
                (DataMapping) Xpdl2ModelUtil.getAncestor(expression,
                        DataMapping.class);

        if (dataMapping != null) {

            Object scriptInfoObj =
                    Xpdl2ModelUtil.getOtherElement(dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());

            if (scriptInfoObj instanceof ScriptInformation) {

                return (ScriptInformation) scriptInfoObj;
            }
        }
        return null;
    }
}
