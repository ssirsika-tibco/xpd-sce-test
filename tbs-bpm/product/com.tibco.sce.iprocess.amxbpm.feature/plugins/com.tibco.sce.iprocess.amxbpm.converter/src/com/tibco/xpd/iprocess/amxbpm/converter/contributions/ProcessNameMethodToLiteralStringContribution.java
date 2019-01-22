/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.List;

/**
 * Contribution that replace all the IPEProcessNameUtil.GETPROCESSNAME("sting")
 * method by the String Literal contained in this method.
 * <p>
 * example - if the IPS had the following script text =
 * IPEProcessNameUtil.GETPROCESSNAME("my-procname") then after conversion to
 * AMX-BPM the script will be
 * <p>
 * text = "my-procname";
 * 
 * @author kthombar
 * @since 30-Jun-2014
 */
public class ProcessNameMethodToLiteralStringContribution extends
        AbstractiProcessScriptMethodConversionContribution {

    /**
     * Calls the super constructor with the method name to convert
     */
    public ProcessNameMethodToLiteralStringContribution() {
        super("IPEProcessNameUtil.GETPROCESSNAME", 1); //$NON-NLS-1$
    }

    /**
     * 
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractiProcessScriptMethodConversionContribution#convertEachScriptLine(java.lang.String,
     *      int, int, int, java.util.List)
     * 
     * @param stringtoConvert
     * @param startOfMethodIndex
     * @param startBracketIndex
     * @param endBracketIndex
     * @param listOfParametersInOrder
     * @return
     */
    @Override
    protected String convertEachScriptLine(String stringtoConvert,
            int startOfMethodIndex, int startBracketIndex, int endBracketIndex,
            List<String> listOfParametersInOrder) {
        /* get the string before the method call */
        String stringBeforeGetProcessNameMethodCall =
                stringtoConvert.substring(0, startOfMethodIndex);
        /* get the string after the method call */
        String stringAfterGetProcessNameMethodCall =
                stringtoConvert.substring(endBracketIndex + 1,
                        stringtoConvert.length());

        StringBuffer buffer = new StringBuffer();

        buffer.append(stringBeforeGetProcessNameMethodCall);
        /* the first parameter is the literal String */
        buffer.append(listOfParametersInOrder.get(0));
        buffer.append(stringAfterGetProcessNameMethodCall);
        return buffer.toString();
    }
}
