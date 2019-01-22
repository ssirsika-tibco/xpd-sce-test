/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.script.model.client;

import java.util.Collections;
import java.util.List;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.internal.Messages;

/**
 * Generic toString method that can be added to a class.
 * 
 * @author nwilson
 * @since 9 Apr 2014
 */
public class ToStringMethod extends AbstractJsMethod {

    public ToStringMethod() {
        methodName = "toString"; //$NON-NLS-1$
        comment = getJavaDoc();
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getName()
     * 
     * @return The method name "toString".
     */
    @Override
    public String getName() {
        return methodName;
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getReturnType()
     * 
     * @return The return type String.
     */
    @Override
    public JsMethodParam getReturnType() {
        return new BaseJsMethodParam(JsConsts.STRING, null, JsConsts.STRING,
                true, false, 1, 1);
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getParameterType()
     * 
     * @return An empty list.
     */
    @Override
    public List<JsMethodParam> getParameterType() {
        return Collections.emptyList();
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getComment()
     * 
     * @return The toString javadoc.
     */
    @Override
    public String getComment() {
        return comment;
    }

    /**
     * @return The toString javadoc.
     */
    private String getJavaDoc() {
        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.Generic_comment_to_string_description
                        + "\n\n" //$NON-NLS-1$
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.Generic_comment_to_string_syntax
                        + "\n\n" //$NON-NLS-1$
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.Generic_comment_to_string_return;
        comment = String.format(comment, methodName, JsConsts.STRING);

        return comment;
    }

}
