/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.js.validation.rules;

import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract class for mapped script expression output rules.
 * 
 * 
 * @author agondal
 * @since 19 Sep 2013
 */
// TODO AbstractOutMappingExpressionRule doesn't care if mapped or unmapped.
// Same for input
public abstract class AbstractMappedScriptExpressionOutputRule extends
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

        /*
         * Output mapping will be inside an xpdl2 scriptInformatiion element
         * contained in a DataMapping element
         */
        if (expression.eContainer() instanceof ScriptInformation) {

            ScriptInformation scriptInformation =
                    (ScriptInformation) expression.eContainer();

            if (scriptInformation.eContainer() instanceof DataMapping) {

                DataMapping dataMapping =
                        (DataMapping) scriptInformation.eContainer();

                if (DirectionType.OUT_LITERAL.equals(MappingRuleUtil
                        .getMappingDirection(dataMapping))) {

                    return getScriptTool(scriptInformation);

                }
            }
        }

        return null;
    }

    /**
     * Returns script tool for the given scriptInformation (which contains the
     * expression being validated). This method will be implemented by
     * sub-classes to provide appropriate script tool based on the given
     * scriptInformation and the ancestor activity type.
     * 
     * @param scriptInformation
     * @return ScriptTool
     */
    protected abstract ScriptTool getScriptTool(
            ScriptInformation scriptInformation);

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractExpressionRule#getExpressionHostForScriptTool(com.tibco.xpd.xpdl2.Expression)
     * 
     * @param expression
     * @return
     */
    @Override
    protected final UniqueIdElement getExpressionHostForScriptTool(
            Expression expression) {

        return (ScriptInformation) Xpdl2ModelUtil.getAncestor(expression,
                ScriptInformation.class);
    }
}
