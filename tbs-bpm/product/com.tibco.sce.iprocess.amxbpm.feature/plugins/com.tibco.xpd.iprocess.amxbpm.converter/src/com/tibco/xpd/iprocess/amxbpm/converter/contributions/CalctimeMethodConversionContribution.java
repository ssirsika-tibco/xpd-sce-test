/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.List;

import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;

/**
 * Contribution that Converts IpeScriptutil.CALCTIME method which has 4
 * parameters.(the 4th parameter is Days over which is not supported in BPM java
 * scripts) , the days over information will be added to the line below this
 * script line.
 * 
 * <p>
 * Example:
 * <p>
 * NEWTIME = IpeScriptutil.CALCTIME(TIME, <add hours>, <add mins>,
 * DAYSOVERFIELD) will be converted to
 * <li>
 * NEWTIME = IpeScriptutil.CALCTIME(TIME, <add hours>, <add mins>);</li>
 * <li>DAYSOVERFIELD = IpeScriptUtil.CALCTIMECARRYOVER(TIME, <add hours>, <add
 * mins>);</li>
 * 
 * @author kthombar
 * @since 01-Jul-2014
 */
public class CalctimeMethodConversionContribution extends
        AbstractiProcessScriptMethodConversionContribution {

    /**
     * Stores the days carry over Script.
     */
    private StringBuffer daysCarryOverScript;

    private static final String IPE_SCRIPT_UTIL_CALCTIMECARRYOVER =
            " = IpeScriptUtil.CALCTIMECARRYOVER("; //$NON-NLS-1$

    /**
     * @param scriptMethodToConvert
     */
    public CalctimeMethodConversionContribution() {
        super("IpeScriptutil.CALCTIME", 4); //$NON-NLS-1$
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

        /* Append the first three parameters of calctime method in the buffer */
        StringBuffer firstThreeParams = new StringBuffer();
        firstThreeParams.append(listOfParametersInOrder.get(0));
        firstThreeParams.append(","); //$NON-NLS-1$
        firstThreeParams.append(listOfParametersInOrder.get(1));
        firstThreeParams.append(","); //$NON-NLS-1$
        firstThreeParams.append(listOfParametersInOrder.get(2));

        /* append days carry over info */
        /*
         * WE add \n to the days to carry because we want it to be written on
         * the line below the calctime call.
         */
        if (firstThreeParams.toString().contains(listOfParametersInOrder.get(3)
                .trim())) {
            daysCarryOverScript.append("\n");//$NON-NLS-1$
            daysCarryOverScript
                    .append(String
                            .format(Messages.CalctimeMethodConversionContribution_HumanInterventionParamNeedsToBeDeleted_comment,
                                    listOfParametersInOrder.get(3).trim()));
        }

        daysCarryOverScript.append("\n");//$NON-NLS-1$
        daysCarryOverScript.append(listOfParametersInOrder.get(3).trim());
        daysCarryOverScript.append(IPE_SCRIPT_UTIL_CALCTIMECARRYOVER);
        daysCarryOverScript.append(firstThreeParams);
        daysCarryOverScript.append(");");//$NON-NLS-1$

        String stringBeforeCalctimeStartBracket =
                stringtoConvert.substring(0, startBracketIndex + 1);
        String stringAfterCalctimeMethod =
                stringtoConvert.substring(endBracketIndex,
                        stringtoConvert.length());
        /* convert the Script */
        StringBuffer convertedString = new StringBuffer();
        convertedString.append(stringBeforeCalctimeStartBracket);
        convertedString.append(firstThreeParams);
        convertedString.append(stringAfterCalctimeMethod);

        return convertedString.toString();
    }

    /**
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractiProcessScriptMethodConversionContribution#reccursivelyConvertEachScriptLine(java.lang.String)
     * 
     * @param scriptLine
     * @return
     */
    @Override
    protected String reccursivelyConvertEachScriptLine(String scriptLine) {
        daysCarryOverScript = new StringBuffer();
        String convertedScript =
                super.reccursivelyConvertEachScriptLine(scriptLine);
        /*
         * add days to carry information to the converted Script.
         */
        return convertedScript + daysCarryOverScript.toString();
    }

}
