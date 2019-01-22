/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.test.general;

import static org.junit.Assert.assertTrue;
import junit.framework.TestCase;

import org.eclipse.jface.text.Document;
import org.junit.Test;

import com.tibco.xpd.scripteditors.internal.javascript.JavaScriptContentAssistProcessor;

/**
 * Tester for the java script content-assist string completion algorithm:
 * {@link JavaScriptContentAssistProcessor#calculateStringToComplete(org.eclipse.jface.text.IDocument, int)}
 * 
 * @author aallway
 * @since 15 Jul 2013
 */
public class N2_01_JavaScriptStringCompleteTest extends TestCase {

    @Test
    public void testEmptyScript() {
        doTest("Empty Script", "", 0, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    }

    @Test
    public void testInSlashSlashComment() {

        String s = "// On First line"; //$NON-NLS-1$

        doTest("Just Before // Comment 1", s, 0, ""); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("In // Comment 1", s, 1, null); //$NON-NLS-1$ 
        doTest("In // Comment 2", s, 2, null); //$NON-NLS-1$
        doTest("In // Comment 3", s, 3, null); //$NON-NLS-1$
        doTest("In // Comment 4", s, s.length() - 1, null); //$NON-NLS-1$

        s += "\n"; //$NON-NLS-1$
        s += "data\n"; //$NON-NLS-1$
        s += "dataBefore comment; // On Last Line after data\n"; //$NON-NLS-1$

        doTest("Just before comment // Comment 2", //$NON-NLS-1$
                s,
                s.indexOf("comment; ") + 9, //$NON-NLS-1$
                ""); //$NON-NLS-1$
        doTest("In // Comment 5", //$NON-NLS-1$
                s,
                s.indexOf("comment; ") + 10, //$NON-NLS-1$
                null);
        doTest("In // Comment 6", //$NON-NLS-1$
                s,
                s.indexOf("comment; ") + 11, //$NON-NLS-1$
                null);
        doTest("In // Comment 7", //$NON-NLS-1$
                s,
                s.indexOf("comment; ") + 12, //$NON-NLS-1$
                null);

        s += "dataAtEnd;\n"; //$NON-NLS-1$
        doTest("In // Comment 5", //$NON-NLS-1$
                s,
                s.indexOf("comment; ") + 10, //$NON-NLS-1$
                null);

    }

    @Test
    public void testInSlashStarComment() {
        String s = "/* On First line"; //$NON-NLS-1$
        doTest("Before Broken /* End comment", s, 0, ""); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Broken /* End comment 1", s, 1, null); //$NON-NLS-1$
        doTest("Broken /* End comment 2", s, 2, null); //$NON-NLS-1$ 
        doTest("Broken /* End comment 3", s, 3, null); //$NON-NLS-1$ 
        doTest("Broken /* End comment 4", s, 4, null); //$NON-NLS-1$ 

        s = "/* On First line */"; //$NON-NLS-1$
        doTest("Just Before /* Comment 1", s, 0, ""); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("In /* Comment 1", s, 1, null); //$NON-NLS-1$
        doTest("In /* Comment 2", s, 2, null); //$NON-NLS-1$
        doTest("In /* Comment 3", s, 3, null); //$NON-NLS-1$
        doTest("In /* Comment 4", s, s.length() - 1, null); //$NON-NLS-1$

        s += "\n"; //$NON-NLS-1$
        s += "data\n"; //$NON-NLS-1$
        s += "dataBefore comment; /* On Last Line after data */\n"; //$NON-NLS-1$

        doTest("Just before comment // Comment 2", //$NON-NLS-1$
                s,
                s.indexOf("comment; ") + 9, //$NON-NLS-1$
                ""); //$NON-NLS-1$
        doTest("In /* Comment 5", //$NON-NLS-1$
                s,
                s.indexOf("comment; ") + 10, //$NON-NLS-1$
                null);
        doTest("In /* Comment 6", //$NON-NLS-1$
                s,
                s.indexOf("comment; ") + 11, //$NON-NLS-1$
                null);
        doTest("In /* Comment 7", //$NON-NLS-1$
                s,
                s.indexOf("comment; ") + 12, //$NON-NLS-1$
                null);

        s += "dataAtEnd;\n"; //$NON-NLS-1$
        doTest("In /* Comment 8", //$NON-NLS-1$
                s,
                s.indexOf("comment; ") + 10, //$NON-NLS-1$
                null);

        s += "/* Final Comment */"; //$NON-NLS-1$
        doTest("After final /* Comment 9", //$NON-NLS-1$
                s,
                s.length(),
                ""); //$NON-NLS-1$

    }

    @Test
    public void testCompleteSimpleName() {
        String s = "NameAtStart = NameAtEnd"; //$NON-NLS-1$

        doTest("Complete Simple Name 0", s, 0, ""); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Complete Simple Name 1", s, 1, "N"); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Complete Simple Name 2", s, 11, "NameAtStart"); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Complete Simple Name 3", s, s.indexOf("NameAtE") + 7, "NameAtE"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    }

    @Test
    public void testCompleteWithDotContent() {

        String s = "One.Abc = Two.xyz;\n"; //$NON-NLS-1$
        s += "SpaceAfter  .  sasa;\n"; //$NON-NLS-1$
        s += "FunctionSpaceAfter  . function (int, string)  .  fsafsa;\n"; //$NON-NLS-1$
        s += "CommentInMiddle  /* 111 */ . /* 222 */\n"; //$NON-NLS-1$
        s += "cimFunction (cimInt, cimString) // 333 .\n"; //$NON-NLS-1$
        s += ". cimcim;\n"; //$NON-NLS-1$

        doTest("Complete with dot content 0", s, 1, "O"); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Complete with dot content 1", s, 3, "One"); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Complete with dot content 2", s, 4, "One."); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Complete with dot content 3", s, 7, "One.Abc"); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Complete with dot content 4", s, s.indexOf("= Two.xyz"), null); //$NON-NLS-1$ //$NON-NLS-2$ 
        doTest("Complete with dot content 5", s, s.indexOf("Tw") + 2, "Tw"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Complete with dot content 6", s, s.indexOf("Two.") + 4, "Two."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Complete with dot content 7", s, s.indexOf("Two.xy") + 6, "Two.xy"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Complete with dot content 8", s, s.indexOf("Two.xy") + 7, "Two.xyz"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        /*
         * Invalid case. Attempting to auto-complete after whitespace after a
         * java identifier (effectively it's a broken word so inserting
         * something extra there cannot be valid
         */
        doTest("Invalid Complete after space after word with dot content 1", s, s.indexOf(" .  sasa"), null); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Invalid Complete after space after word with dot content 2", s, s.indexOf(" .  fsafsa"), null); //$NON-NLS-1$ //$NON-NLS-2$

        /* Where as content assist after space after . is ok. */
        doTest("Complete with dot content 9", s, s.indexOf(" sasa"), "SpaceAfter."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Complete with dot content 10", s, s.indexOf(" sasa") + 3, "SpaceAfter.sa"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Complete with dot content 11", s, s.indexOf(" fsafsa"), "FunctionSpaceAfter.function(int,string)."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Complete with dot content 12", s, s.indexOf(" fsafsa") + 4, "FunctionSpaceAfter.function(int,string).fsa"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        /*
         * Test valid cases where comment separates different parts of multi
         * part expression.
         * 
         * Invalid case. Attempting to auto-complete after whitespace after a
         * java identifier (effectively it's a broken word so inserting
         * something extra there cannot be valid
         */
        doTest("Invalid With Comment in middle of expr Complete after space after word with dot content 1", s, s.indexOf("/* 111"), null); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Invalid With Comment in middle of expr Complete after space after word with dot content 2", s, s.indexOf(" . /* 222"), null); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Invalid With Comment in middle of expr Complete after space after word with dot content 3", s, s.indexOf(". /* 222"), null); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Invalid With Comment in middle of expr Complete after space after word with dot content 4", s, s.indexOf("cimString)") + 11, null); //$NON-NLS-1$ //$NON-NLS-2$ 
        doTest("Invalid With Comment in middle of expr Complete after space after word with dot content 5", s, s.indexOf(". cimcim"), null); //$NON-NLS-1$ //$NON-NLS-2$ 

        /* Where as content assist after space after . is ok. */
        doTest("With Comment in middle of expr Complete with dot content 1", s, s.indexOf(" /* 222"), "CommentInMiddle."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("With Comment in middle of expr Complete with dot content 2", s, s.indexOf("222 */") + 6, "CommentInMiddle."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("With Comment in middle of expr Complete with dot content 3", s, s.indexOf("cimFunction"), "CommentInMiddle."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("With Comment in middle of expr Complete with dot content 4", s, s.indexOf("cimFunction") + 3, "CommentInMiddle.cim"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("With Comment in middle of expr Complete with dot content 5", s, s.indexOf("cimString") + 6, "cimStr"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("With Comment in middle of expr Complete with dot content 6", s, s.indexOf(". cimcim") + 1, "CommentInMiddle.cimFunction(cimInt,cimString)."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("With Comment in middle of expr Complete with dot content 7", s, s.indexOf(". cimcim") + 8, "CommentInMiddle.cimFunction(cimInt,cimString).cimcim"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    }

    @Test
    public void testInStrings() {
        /* Broken string at beginning / end. */
        String s = "\""; //$NON-NLS-1$

        doTest("Test in strings 0", s, 0, ""); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Test in strings 1", s, 1, null); //$NON-NLS-1$ 

        s += "\n"; //$NON-NLS-1$
        doTest("Test in strings 2", s, 1, null); //$NON-NLS-1$ 
        doTest("Test in strings 3", s, 2, ""); //$NON-NLS-1$ //$NON-NLS-2$ 

        /* In middle of string */
        s = "field = \"abc xyz\" ;"; //$NON-NLS-1$
        s += "field += \"escaped quote \\\" after escape\";"; //$NON-NLS-1$
        s += "field += \"string broken over lines is counted\n"; //$NON-NLS-1$
        s += "as being terminated at end of line otherwise\n"; //$NON-NLS-1$
        s += "it will disable content assist for entire rest of script\n"; //$NON-NLS-1$
        s +=
                "therefore HERE will produce string completion but not after this QUOTE\"; /* as that is counted as start of string on this line */\n"; //$NON-NLS-1$

        doTest("Test in strings 4", s, s.indexOf("\"abc"), ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
        doTest("Test in strings 5", s, s.indexOf("\"abc") + 1, null); //$NON-NLS-1$ //$NON-NLS-2$ 
        doTest("Test in strings 6", s, s.indexOf("\"abc") + 4, null); //$NON-NLS-1$ //$NON-NLS-2$ 
        doTest("Test in strings 7a", s, s.indexOf("xyz\" ;") + 4, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
        doTest("Test in strings 7b", s, s.indexOf("xyz\" ;") + 5, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
        doTest("Test in strings 7c", s, s.indexOf("xyz\" ;") + 6, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

        doTest("Test in strings 8", s, s.indexOf("xyz\"") + 3, null); //$NON-NLS-1$ //$NON-NLS-2$ 
        doTest("Test in strings 9", s, s.indexOf("field +=") + 5, "field"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

        doTest("Test in strings 8", s, s.indexOf("\\\" after escape"), null); //$NON-NLS-1$ //$NON-NLS-2$ 
        doTest("Test in strings 9", s, s.indexOf("\\\" after escape") + 1, null); //$NON-NLS-1$ //$NON-NLS-2$ 
        doTest("Test in strings 10", s, s.indexOf("\\\" after escape") + 2, null); //$NON-NLS-1$ //$NON-NLS-2$ 
        doTest("Test in strings 11", s, s.indexOf("\\\" after escape") + 3, null); //$NON-NLS-1$ //$NON-NLS-2$ 

        doTest("Test in strings 12", s, s.indexOf("after escape\";") + 14, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

        doTest("Test in strings 13", s, s.indexOf("is counted") + 10, null); //$NON-NLS-1$ //$NON-NLS-2$
        doTest("Test in strings 14", s, s.indexOf("is counted") + 11, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in strings 15", s, s.indexOf("HERE") + 3, "HER"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in strings 16", s, s.indexOf("QUOTE\";") + 7, null); //$NON-NLS-1$ //$NON-NLS-2$ 

    }

    @Test
    public void testWithBrackets() {
        String s = "/* Test content assist */\n"; //$NON-NLS-1$
        s += "field=field2+func.fff();\n"; //$NON-NLS-1$
        s += "field3=field4+func.fff2().;\n"; //$NON-NLS-1$
        s +=
                "field3=field4+func.fff3(field5.child.func.fff4( param1, func . fff5(param2) )).;\n"; //$NON-NLS-1$
        s += "if (field5[field6.child[]] + field6.func.fff6(param.) == true) {"; //$NON-NLS-1$
        s +=
                "broken=bracketsfunc.fff(field5.child.func.fff7( param3, func.fff8(param4).);\n"; //$NON-NLS-1$

        doTest("Test in brackets 0", s, s.indexOf("func.fff()") + 7, "func.ff"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
        doTest("Test in brackets 1", s, s.indexOf("func.fff()") + 8, "func.fff"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
        doTest("Test in brackets 2", s, s.indexOf("func.fff()") + 9, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
        doTest("Test in brackets 3", s, s.indexOf("func.fff()") + 10, null); //$NON-NLS-1$ //$NON-NLS-2$

        doTest("Test in brackets 4", s, s.indexOf("func.fff2().") + 12, "func.fff2()."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 5", s, s.indexOf("func.fff3(") + 9, "func.fff3"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 6", s, s.indexOf("func.fff3(fie") + 13, "fie"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        doTest("Test in brackets 7a", s, s.indexOf("param1") + 6, "param1"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 7b", s, s.indexOf("param1,") + 7, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 8", s, s.indexOf("func . fff5") + 4, "func"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 9", s, s.indexOf("func . fff5") + 5, null); //$NON-NLS-1$ //$NON-NLS-2$ 
        doTest("Test in brackets 9", s, s.indexOf("func . fff5") + 6, "func."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 10", s, s.indexOf("func . fff5") + 7, "func."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 11", s, s.indexOf("func . fff5") + 11, "func.fff5"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        doTest("Test in brackets 12", s, s.indexOf("fff5(param2)") + 12, null); //$NON-NLS-1$ //$NON-NLS-2$ 
        doTest("Test in brackets 13", s, s.indexOf("fff5(param2)") + 13, null); //$NON-NLS-1$ //$NON-NLS-2$ 

        doTest("Test in brackets 14", s, s.indexOf("fff5(param2) )).") + 16, "func.fff3(field5.child.func.fff4(param1,func.fff5(param2)))."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        doTest("Test in brackets 15", s, s.indexOf("if (") + 4, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 16", s, s.indexOf("field5") + 6, "field5"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 17", s, s.indexOf("field5[") + 7, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 18", s, s.indexOf("child[") + 6, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 19", s, s.indexOf("child[]") + 7, null); //$NON-NLS-1$ //$NON-NLS-2$ 

        doTest("Test in brackets 20", s, s.indexOf("fff6(param.") + 11, "param."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        doTest("Test in brackets 21", s, s.indexOf("== true") + 2, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 22a", s, s.indexOf("== true") + 3, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 22b", s, s.indexOf("== true") + 4, "t"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 23", s, s.indexOf(") {") + 3, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        doTest("Test in brackets 24", s, s.indexOf("broken=bracketsfunc.fff(") + 24, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 25", s, s.indexOf("broken=bracketsfunc.fff(field5.") + 28, "fiel"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 26", s, s.indexOf("broken=bracketsfunc.fff(field5.ch") + 33, "field5.ch"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        doTest("Test in brackets 27", s, s.indexOf("func.fff8(param4).") + 18, "func.fff8(param4)."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        doTest("Test in brackets 28", s, s.indexOf("func.fff8(param4).)") + 19, null); //$NON-NLS-1$ //$NON-NLS-2$ 

    }

    @Test
    public void testInCharacterConstant() {

    }

    private void doTest(String label, String script, int cursorLocation,
            String expectedResult) {
        Document doc = new Document(script);

        @SuppressWarnings("restriction")
        JavaScriptContentAssistProcessor processor =
                new JavaScriptContentAssistProcessor();

        String result =
                processor.calculateStringToComplete(doc, cursorLocation);

        if (expectedResult == null) {
            assertTrue("Unexpected Result for test '" + label //$NON-NLS-1$
                    + "' expectedResult=null actual result='" + result + "'", //$NON-NLS-1$ //$NON-NLS-2$
                    result == null);
        } else {
            assertTrue("Unexpected Result for test '" + label //$NON-NLS-1$
                    + "' expectedResult='" + expectedResult //$NON-NLS-1$
                    + "' actual result='" + result + "'", //$NON-NLS-1$ //$NON-NLS-2$
                    expectedResult.equals(result));
        }
    }
}
