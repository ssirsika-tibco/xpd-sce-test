package com.tibco.xpd.bom.test.resource;

import junit.framework.TestCase;

import com.tibco.xpd.bom.types.api.BOMEntityNameCleanser;
import com.tibco.xpd.bom.xsdtransform.utils.XSDDerivedBOMNameAssigner;

public class BOMEntityNameCleanserTest extends TestCase {

    private void classTest(String expected, String name) {
        String actual =
                BOMEntityNameCleanser.getInstance()
                        .cleanseClassName(name, true);
        assertEquals("Class name didn't cleanse as expected", expected, actual);
        // Also check it is now 'cleanse-proof' (i.e. EMF won't change it)
        String mapped =
                new XSDDerivedBOMNameAssigner().transformClassName(actual);
        assertTrue("Cleansed name was not cleanse-proof: " + actual,
                mapped.equals(actual));
    }

    private void attributeTest(String expected, String name) {
        String actual =
                BOMEntityNameCleanser.getInstance().cleanseAttributeName(name,
                        true);
        assertEquals("Attribute name didn't cleanse as expected",
                expected,
                actual);
        // Also check it is now 'cleanse-proof' (i.e. EMF won't change it)
        String mapped =
                new XSDDerivedBOMNameAssigner().transformAttributeName(actual);
        assertTrue("Cleansed name was not cleanse-proof: " + actual,
                mapped.equals(actual));
    }

    private void enumerationLiteralTest(String expected, String name) {
        String actual =
                BOMEntityNameCleanser.getInstance()
                        .cleanseEnumerationLiteralName(name);
        assertEquals("Enumeration literal name didn't cleanse as expected",
                expected,
                actual);
        // Also check it is now 'cleanse-proof' (i.e. EMF won't change it)
        String mapped =
                new XSDDerivedBOMNameAssigner()
                        .transformEnumerationLiteralName(actual);
        assertTrue("Cleansed name was not cleanse-proof: " + actual,
                mapped.equals(actual));
    }

    private void packageTest(String expected, String name) {
        String actual =
                BOMEntityNameCleanser.getInstance().cleansePackageName(name,
                        true,
                        false, 
                        true);
        assertEquals("Package name didn't cleanse as expected",
                expected,
                actual);
        // Also check it is now 'cleanse-proof' (i.e. EMF won't change it)
        // TODO This is the odd one out (half the logic is in Xtend). We could
        // consider
        // moving the package to NSURI logic to Java and making Xtend use it.
    }

    public void testPackages() {
        packageTest("n_ew.thin.I_NT", "n_ew.thin.I_NT");
        packageTest("tr________MP", "tr________MP!");
        packageTest("PROTe________Cted", "PROTe_____!'!___Cted");
        packageTest("CHEESE.SHORT", "873284723894C!H£E$E*S*E.SHORT");
        
        // Database reserved words in first position
        packageTest("select1", "select");
        packageTest("select1.update", "select.update");
        packageTest("update1.select", "update.select");
        packageTest("JOIN1.SAUsage", "JOIN.SAUsage");
        packageTest("from1.alexey.pajitnov.henk.rogers", "from.alexey.pajitnov.henk.rogers");
        
        // Database reserved words are fine as long as they're not at the start
        packageTest("hello.update", "hello.update");
        packageTest("goodbye.select.join.update", "goodbye.select.join.update");
    }

    public void testClasses() {
        classTest("_helloworld", "_hello world!");
        classTest("mightyMeaty", "mightyMeaty");
        classTest("funwithhyphens", "fun-with-hyphens");
        classTest("PunctuationFrenzy", "Punctuation.-=-.Frenzy!");
        classTest("void1", "void");
    }

    public void testAttributes() {
        attributeTest("age", "Age");
        attributeTest("size", "size");
        attributeTest("_helloworld", "_hello world!");
        attributeTest("mightyMeaty", "mightyMeaty");
        attributeTest("funwithhyphens", "fun-with-hyphens");
        attributeTest("punctuationFrenzy", "Punctuation.-=-.Frenzy!");
        attributeTest("int1", "int");
        attributeTest("public1", "Public");
        attributeTest("KABLAM", "123KABLAM!");
        attributeTest("_A_B_C", "_A_B_C!");
    }

    public void testEnumerationLiterals() {
        enumerationLiteralTest("RED", "red");
        enumerationLiteralTest("GREEN", "Green");
        enumerationLiteralTest("BLUE", "BLUE");
        enumerationLiteralTest("LIGHTPINK", "LIGHT PINK");
        enumerationLiteralTest("DARKORANGE", "DARK_ORANGE");
        enumerationLiteralTest("GLADOS", "G.L.a.DOS");
        enumerationLiteralTest("INKY", "_inky!");
        enumerationLiteralTest("BLINKY", "Bli Nky");
        enumerationLiteralTest("PINKY123", "_PINKY_123");
        enumerationLiteralTest("CLYDE456", "123clyde456");
        enumerationLiteralTest("PROTECTED", "protected");
        enumerationLiteralTest("VISION", "1vision");
    }
}
