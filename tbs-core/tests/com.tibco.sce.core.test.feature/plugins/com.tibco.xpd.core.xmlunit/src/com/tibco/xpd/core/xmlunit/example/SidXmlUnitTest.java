/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.xmlunit.example;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.XMLUnit;

import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.XmlDiffIgnoreNode;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier;

/**
 * SidXmlUnitTest
 * 
 * 
 * @author aallway
 * @since 3.3 (14 Sep 2009)
 */
public class SidXmlUnitTest extends TestCase {

    public void testIgnoreNode() throws Exception {
        String control =
                "<root xmlns:yy=\"sid.comY\" xmlns:xx=\"sid.com\" Name=\"Of all evil\">"
                        + "    <GrandParent Name=\"Really Oldey\" xx:Id=\"1\" >"
                        + "        <Parent xx:Id=\"2\" Name=\"Oldey\">"
                        + "            <Child yy:Id=\"3\" Name=\"Young One\">"
                        + "           This is my child text."
                        + "        </Child>" + "        </Parent>"

                        + "        <DontCareAboutChildren>"
                        + "            <c Id=\"c1\"/>"
                        + "        </DontCareAboutChildren>"

                        + "    </GrandParent>" + "</root>";

        String test =
                "<root xmlns:xx=\"sid.com\" xmlns:yy=\"sid.comY\" Name=\"Of all evil2\">"
                        + "    <GrandParent xx:Id=\"4\" Name=\"Really Oldey2\">"
                        + "        <Parent xx:Id=\"5\" Name=\"Oldey\">"
                        + "            <Child yy:Id=\"6\" Name=\"Young One2\">"
                        + "           This is my child text2."
                        + "        </Child>" + "      </Parent>"

                        + "        <DontCareAboutChildren>"
                        + "            <c Id=\"c1\"/>"
                        + "            <c Id=\"c2\"/>"
                        + "        </DontCareAboutChildren>"

                        + "    </GrandParent>" + "</root>";

        XmlDiff diff = new XmlDiff(control, test);

        diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                new String[] { "root", "@Name" }, true)));

        diff
                .addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                        "@yy:Id")));
        diff
                .addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                        "@xx:Id")));

        diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                new String[] { "GrandParent", "@Name" }, false)));

        diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                new String[] { "Parent", "Child", "@Name" }, false)));

        diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                XmlDiffNodePath.TEXT_CONTENT)));

        diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                new String[] { "GrandParent", "Child", "@Name" }, false)));

        diff
                .addIgnoreNode(new XmlDiffIgnoreNode(
                        new XmlDiffNodePath(new String[] { "GrandParent",
                                "DontCareAboutChildren" }, false)));

        if (!diff.similar()) {
            fail("Documents are different: \n    " + diff.toString());
        }

    }

    public void testSequenceOrder() throws Exception {
        String control =
                "<root Name=\"Of all evil\">"
                        + "    <GrandParent Name=\"Really Oldey\" Id=\"1\">\n" //$NON-NLS-1$
                        + "      <Seq1Sequence>\n" //$NON-NLS-1$"
                        + "        <Seq1 Id=\"1\" Name=\"aaa\">\n" //$NON-NLS-1$ 
                        + "           XXX\n" //$NON-NLS-1$
                        + "           <SeqChild a=\"xxx\">C111</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq1>\n" //$NON-NLS-1$

                        + "        <Seq1 Id=\"2\" Name=\"bbb\">\n" //$NON-NLS-1$
                        + "           YYY"
                        + "           <SeqChild a=\"yyy\">C222</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq1>\n" //$NON-NLS-1$

                        + "        <Seq1 Id=\"3\" Name=\"ccc\">\n" //$NON-NLS-1$
                        + "           ZZZ\n" //$NON-NLS-1$
                        + "           <SeqChild a=\"zzz\">C333</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq1>\n" //$NON-NLS-1$
                        + "      </Seq1Sequence>\n" //$NON-NLS-1$

                        /*
                         * Sequence 2 will be qualified on EITHER "SeqChild/@a"
                         * (if present) OTHERWISE on "SeqChild/!!text!!"
                         * 
                         * remember that Id attrs are setup to be ignored.
                         * 
                         * i.e. the Seq/@Id=b2 element is qualified on
                         */
                        + "        <Seq2 Id=\"b1\" Name=\"aaa\">\n" //$NON-NLS-1$
                        + "           XXX\n" //$NON-NLS-1$
                        + "           <SeqChild a=\"xxx\">C111</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq2>\n" //$NON-NLS-1$

                        + "        <Seq2 Id=\"b2\" Name=\"bbb\">\n" //$NON-NLS-1$
                        + "           YYY\n" //$NON-NLS-1$
                        + "           <SeqChild>  \nC222</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq2>\n" //$NON-NLS-1$

                        + "        <Seq2 Id=\"b3\" Name=\"ccc\">\n" //$NON-NLS-1$
                        + "           ZZZ\n" //$NON-NLS-1$
                        + "           <SeqChild a=\"zzz\">C333</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq2>\n" //$NON-NLS-1$

                        + "        <Seq2 Id=\"b4\" Name=\"bbb\">\n" //$NON-NLS-1$
                        + "           YYY\n" //$NON-NLS-1$
                        + "           <SeqChild>C444</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq2>\n" //$NON-NLS-1$

                        /*
                         * <Seq3> is qualified by the SeqChild sequence (@a and
                         * SubChild/text) within it - we should be able to cope
                         * with the SeqChild sequence being different order in
                         * source and target
                         */
                        + "       <Seq3 Id=\"c1\" Name=\"aaa\">\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c1 a1\"><SubChild>c1 111</SubChild><SubChild>c1 222</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c1 b1\"><SubChild>c1 333</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c1 c1\"><SubChild>c1 444</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c1 d1\"><SubChild>c1 555</SubChild><SubChild>c1 666</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqMainChild>WWW</SeqMainChild>\n" //$NON-NLS-1$
                        + "       </Seq3>\n" //$NON-NLS-1$

                        + "       <Seq3 Id=\"c2\" Name=\"aaa\">\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c2 a1\"><SubChild>c2 111</SubChild><SubChild>c2 222</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c2 b1\"><SubChild>c2 333</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c2 c1\"><SubChild>c2 444</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c2 d1\"><SubChild>c2 555</SubChild><SubChild>c2 666</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqMainChild>XXX</SeqMainChild>\n" //$NON-NLS-1$
                        + "       </Seq3>\n" //$NON-NLS-1$

                        + "       <Seq3 Id=\"c3\" Name=\"aaa\">\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c3 a1\"><SubChild>c3 111</SubChild><SubChild>c3 222</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c3 b1\"><SubChild>c3 333</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c3 c1\"><SubChild>c3 444</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c3 d1\"><SubChild>c3 555</SubChild><SubChild>c3 666</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqMainChild>YYY</SeqMainChild>\n" //$NON-NLS-1$
                        + "       </Seq3>\n" //$NON-NLS-1$

                        + "       <Seq3 Id=\"c4\" Name=\"aaa\">\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c4 a1\"><SubChild>c4 111</SubChild><SubChild>c4 222</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c4 b1\"><SubChild>c4 333</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c4 c1\"><SubChild>c4 444</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c4 d1\"><SubChild>c4 555</SubChild><SubChild>c4 666</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqMainChild>ZZZ</SeqMainChild>\n" //$NON-NLS-1$
                        + "       </Seq3>\n" //$NON-NLS-1$

                        + "    </GrandParent>\n" //$NON-NLS-1$ 
                        + "</root>\n"; //$NON-NLS-1$;

        String test =
                "<root Name=\"Of all evil\">\n" //$NON-NLS-1$
                        + "    <GrandParent Name=\"Really Oldey\" Id=\"1\" >\n" //$NON-NLS-1$
                        + "      <Seq1Sequence>\n" //$NON-NLS-1$

                        + "        <Seq1 Id=\"3x\" Name=\"ccc\">\n" //$NON-NLS-1$
                        + "           ZZZ\n" //$NON-NLS-1$
                        + "           <SeqChild a=\"zzz\">C333</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq1>\n" //$NON-NLS-1$

                        + "        <Seq1 Id=\"1x\" Name=\"aaa\">\n" //$NON-NLS-1$
                        + "           XXX\n" //$NON-NLS-1$
                        + "           <SeqChild a=\"xxx\">C111</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq1>\n" //$NON-NLS-1$

                        + "        <Seq1 Id=\"2x\" Name=\"bbb\">\n" //$NON-NLS-1$
                        + "           YYY\n" //$NON-NLS-1$
                        + "           <SeqChild a=\"yyy\">C222</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq1>\n" //$NON-NLS-1$
                        + "      </Seq1Sequence>\n" //$NON-NLS-1$

                        + "        <Seq2 Id=\"b3x\" Name=\"ccc\">\n" //$NON-NLS-1$
                        + "           ZZZ\n" //$NON-NLS-1$
                        + "           <SeqChild a=\"zzz\">C333</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq2>\n" //$NON-NLS-1$

                        + "        <Seq2 Id=\"b1x\" Name=\"aaa\">\n" //$NON-NLS-1$
                        + "           XXX\n" //$NON-NLS-1$
                        + "           <SeqChild a=\"xxx\">C111</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq2>\n" //$NON-NLS-1$

                        /*
                         * Sequence 2 will be qualified on EITHER "SeqChild/@a"
                         * (if present) OTHERWISE on "SeqChild/!!text!!"
                         * 
                         * remember that Id attrs are setup to be ignored.
                         * 
                         * i.e. the Seq/@Id=b2 and b4 element is qualified on
                         * text content instead
                         */
                        + "        <Seq2 Id=\"b4x\" Name=\"bbb\">\n" //$NON-NLS-1$
                        + "           YYY\n" //$NON-NLS-1$
                        + "           <SeqChild>C444</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq2>\n" //$NON-NLS-1$

                        + "        <Seq2 Id=\"b2x\" Name=\"bbb\">\n" //$NON-NLS-1$
                        + "           YYY\n" //$NON-NLS-1$
                        + "           <SeqChild>C222</SeqChild>\n" //$NON-NLS-1$
                        + "        </Seq2>\n" //$NON-NLS-1$

                        /*
                         * <Seq3> is qualified by the SeqChild sequence (@a and
                         * SubChild/text) within it - we should be able to cope
                         * with the SeqChild sequence being different order in
                         * source and target
                         */
                        + "       <Seq3 Id=\"c2\" Name=\"aaa\">\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c2 a1\"><SubChild>c2 111</SubChild><SubChild>c2 222</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c2 b1\"><SubChild>c2 333</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c2 c1\"><SubChild>c2 444</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c2 d1\"><SubChild>c2 555</SubChild><SubChild>c2 666</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqMainChild>XXX</SeqMainChild>\n" //$NON-NLS-1$
                        + "       </Seq3>\n" //$NON-NLS-1$

                        + "       <Seq3 Id=\"c3\" Name=\"aaa\">\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c3 a1\"><SubChild>c3 111</SubChild><SubChild>c3 222</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c3 b1\"><SubChild>c3 333</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c3 c1\"><SubChild>c3 444</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c3 d1\"><SubChild>c3 555</SubChild><SubChild>c3 666</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqMainChild>YYY</SeqMainChild>\n" //$NON-NLS-1$
                        + "       </Seq3>\n" //$NON-NLS-1$

                        + "       <Seq3 Id=\"c4\" Name=\"aaa\">\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c4 a1\"><SubChild>c4 111</SubChild><SubChild>c4 222</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c4 b1\"><SubChild>c4 333</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c4 c1\"><SubChild>c4 444</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c4 d1\"><SubChild>c4 555</SubChild><SubChild>c4 666</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqMainChild>ZZZ</SeqMainChild>\n" //$NON-NLS-1$
                        + "       </Seq3>\n" //$NON-NLS-1$

                        + "       <Seq3 Id=\"c1\" Name=\"aaa\">\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c1 c1\"><SubChild>c1 444</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c1 a1\"><SubChild>c1 222</SubChild><SubChild>c1 111</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c1 d1\"><SubChild>c1 555</SubChild><SubChild>c1 666</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqChild a=\"c1 b1\"><SubChild>c1 333</SubChild></SeqChild>\n" //$NON-NLS-1$
                        + "          <SeqMainChild>WWW</SeqMainChild>\n" //$NON-NLS-1$
                        + "       </Seq3>\n" //$NON-NLS-1$

                        + "    </GrandParent>\n" //$NON-NLS-1$ 
                        + "</root>\n"; //$NON-NLS-1$

        // System.out.println("CONTROL:\n" + control);
        // System.out.println("\n\nTEST:\n" + test);

        XmlDiff diff = new XmlDiff(control, test);
        XMLUnit.setIgnoreWhitespace(true);

        /* Ignore all Id's */
        diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath("@Id")));

        /*
         * SEQ1 Qualify comparable <Seq1> elements by Name attribute and
         * <SeqChild> text value.
         */
        List<XmlDiffNodePath> qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@Name"));

        qualifierPaths.add(new XmlDiffNodePath(new String[] { "SeqChild",
                XmlDiffNodePath.TEXT_CONTENT }, false));

        XmlDiffSequenceQualifier sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("Seq1"),
                        qualifierPaths);

        diff.addSequenceElementQualifier(sequenceQualifier);

        /*
         * SEQ2 Qualify comparable <Seq1> elements by Name attribute and
         * <SeqChild>@a OR @a is missing in control or test then on
         * <SeqChild>!!text!!
         */
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath(
                new String[] { "SeqChild", "@a" }, false));
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("Seq2"),
                        qualifierPaths);
        diff.addSequenceElementQualifier(sequenceQualifier);

        /* OR <SeqChild>!!text!! */
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath(new String[] { "SeqChild",
                XmlDiffNodePath.TEXT_CONTENT }, false));
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("Seq2"),
                        qualifierPaths);
        diff.addSequenceElementQualifier(sequenceQualifier);

        /*
         * <Seq3> is qualified by the SeqChild sequence (@a and SubChild/text)
         * within it - we should be able to cope with the SeqChild sequence
         * being different order in source and target and still state correctly
         * which Seq3 elements qualify for comparison.
         */
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath(
                new String[] { "SeqChild", "@a" }, false));
        qualifierPaths.add(new XmlDiffNodePath(new String[] { "SeqChild",
                "SubChild", XmlDiffNodePath.TEXT_CONTENT }, false));
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath("Seq3"),
                        qualifierPaths);
        diff.addSequenceElementQualifier(sequenceQualifier);

        /*
         * Which means we also have to provide a qualifier for the Seq3/SeqChild
         * sequence itself. Qualify the Seq3/SeqChild sequence by SeqChild/@a
         */
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath("@a"));
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(new String[] {
                        "Seq3", "SeqChild" }, false), qualifierPaths);
        diff.addSequenceElementQualifier(sequenceQualifier);

        /*
         * Which means we also have to provide a qualifier for the
         * SeqChild/SubChild sequence itself. Qualify the SeqChild/SubChild
         * sequence by SubChild/!!text!!
         */
        qualifierPaths = new ArrayList<XmlDiffNodePath>();
        qualifierPaths.add(new XmlDiffNodePath(XmlDiffNodePath.TEXT_CONTENT));
        sequenceQualifier =
                new XmlDiffSequenceQualifier(new XmlDiffNodePath(new String[] {
                        "SeqChild", "SubChild" }, false), qualifierPaths);
        diff.addSequenceElementQualifier(sequenceQualifier);

        /*
         * COMPARE!
         */
        if (!diff.similar()) {
            fail("Documents are different: \n    " + diff.toString());
        }
    }
}
