/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.studioiprocess.scriptconverter;

import java.util.ArrayList;

/**
 * Pre-parse iProcessScript grammar string into a line list.
 * <p>
 * This takes continuation lines into account.
 * 
 * @author aallway
 * @since 18 May 2011
 */
public class IProcessScriptLineList extends ArrayList<String> {

    /**
     * Construct list of lines from an iProcess script.
     * <p>
     * This takes continuation lines into account.
     * 
     * @param script
     */
    public IProcessScriptLineList(String script) {
        super();

        parseLines(script);
    }

    /**
     * Parse script into lines, adding each to list.
     * <p>
     * Handles both \n and \r\n line termination (although does not output
     * \r's).
     * <p>
     * Handles "\" at end of line as a line continuation character.
     * 
     * @param script
     */
    private void parseLines(String script) {
        if (script != null && script.length() > 0) {
            /* Remove all \r's */
            script = script.replaceAll("\r", ""); //$NON-NLS-1$ //$NON-NLS-2$

            /*
             * Remove all "\\\n" (lines ending in backslash - effectively
             * joining those lines to the next)
             */
            script = script.replace("\\\n", ""); //$NON-NLS-1$//$NON-NLS-2$

            /*
             * Now all we need to do is parse thru on \n and add lines to the
             * underlying list.
             */
            int startLine = 0;

            while (true) {
                int endLine = script.indexOf('\n', startLine);
                if (endLine >= 0) {
                    String line = script.substring(startLine, endLine);
                    startLine = endLine + 1;
                    this.add(line);
                } else {
                    String line = script.substring(startLine);
                    if (line.trim().length() > 0) {
                        this.add(line);
                    }
                    break;
                }
            }

        }

        return;
    }

    /**
     * ===========================================================
     * 
     * TESTS...
     * 
     * ===========================================================
     */

    public static void main(String[] args) {
        TestScript[] scripts =
                {
                        new TestScript(null, new String[0]),
                        new TestScript("", new String[0]), //$NON-NLS-1$ 
                        new TestScript(" ", new String[0]), //$NON-NLS-1$
                        new TestScript("1 = 1", new String[] { "1 = 1" }), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestScript("1 = 1\n", new String[] { "1 = 1" }), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestScript("1 = 1\r\n", new String[] { "1 = 1" }), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestScript(
                                "1 = 1\n2 = 2", new String[] { "1 = 1", "2 = 2" }), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        new TestScript(
                                "1 = 1\r\n2 = 2", new String[] { "1 = 1", "2 = 2" }), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        new TestScript(
                                "1 = 1\\\n 2 = 2", new String[] { "1 = 1 2 = 2" }), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestScript(
                                "1 = 1\\\r\n 2 = 2", new String[] { "1 = 1 2 = 2" }), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestScript(
                                "1 = 1\n2 = 2\n", new String[] { "1 = 1", "2 = 2" }), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        new TestScript(
                                "1 = 1\n2 = 2\n3 = 3\n", new String[] { "1 = 1", "2 = 2", "3 = 3" }), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                        new TestScript(
                                "1 = 1\r\n\r\n\n2 = 2", new String[] { "1 = 1", "", "", "2 = 2" }), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
                };

        boolean failed = false;
        for (TestScript testScript : scripts) {
            IProcessScriptLineList list =
                    new IProcessScriptLineList(testScript.script);

            if (list.size() != testScript.expected.length) {
                System.out
                        .println("Bad line parse on script:\n==>" + testScript.script + "<=="); //$NON-NLS-1$ //$NON-NLS-2$
                failed = true;
            } else {
                for (int i = 0; i < testScript.expected.length; i++) {
                    if (!testScript.expected[i].equals(list.get(i))) {
                        System.out
                                .println("Bad line parse on script line(" + i + "):\n==>" + testScript.script + "<=="); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        failed = true;
                        break;
                    }
                }
            }

            if (failed) {
                break;
            }
        }

        System.out.println("DONE: " + (failed ? "FAILED" : "SUCCESS")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    private static class TestScript {
        private String script;

        private String[] expected;

        /**
         * @param script
         * @param expected
         */
        public TestScript(String script, String[] expected) {
            super();
            this.script = script;
            this.expected = expected;
        }

    }

}
