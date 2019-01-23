/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.LinkedList;

/**
 * Parses the JavaScripts Line by Line and returns a List which contains each
 * line.
 * 
 * @author kthombar
 * @since 08-May-2014
 */
@SuppressWarnings("serial")
public class JavaScriptLineList extends LinkedList<String> {

    /**
     * Parses the JavaScripts Line by Line and returns a List which contains
     * each line.
     * 
     * @param javaScript
     */
    public JavaScriptLineList(String javaScript) {
        parseJavaScriptToLineList(javaScript);
    }

    /**
     * Parses the JavaScripts Line by Line
     * 
     * @param javaScript
     */
    private void parseJavaScriptToLineList(String javaScript) {
        int startLine = 0;
        while (true) {
            int endLine = javaScript.indexOf('\n', startLine);
            if (endLine < 0)
                break;
            String line = javaScript.substring(startLine, endLine);
            startLine = endLine + 1;
            add(line);
        }
        String line = javaScript.substring(startLine);
        if (line.trim().length() > 0)
            add(line);
    }
}
