package com.tibco.bds.designtime.generator.test;

import junit.framework.TestCase;

import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;

/**
 * Exercises the namespace URI to Java package name mapper via its API in
 * com.tibco.xpd.bom.xsdtransform.api
 * 
 * @author smorgan
 * 
 */
public class NamespaceURIToJavaPackageMapperTest extends TestCase {

    private void doTest(String ns, String expectedPkg) {

        String actualPkg = XSDUtil.getJavaPackageNameFromNamespaceURI(null, ns);
        assertEquals("Mapping gave unexpected package name", //$NON-NLS-1$
                expectedPkg,
                actualPkg);
    }

    public void test1() {
        doTest("http://www.example.com", "com.example"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void test2() {
        doTest("http://new.org", "org.new_"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void test3() {
        doTest("http://www.new.HelloWorld.com/int/2010-01-06/12345", //$NON-NLS-1$

                "com.HelloWorld.new_.int_._2010._01._06._12345"); //$NON-NLS-1$
        // Before XPD-1461, this was:
        // "com.world.hello.new_.int_._2010._01._06._12345"
    }

    public void test4() {
        doTest("http://www.sid.com/2009-12-10/abc/1234", //$NON-NLS-1$
                "com.sid._2009._12._10.abc._1234"); //$NON-NLS-1$
    }

    public void test5() {
        doTest("http://new.int.short.public.private.long.banana.protected", //$NON-NLS-1$
                "protected_.banana.long_.private_.public_.short_.int_.new_"); //$NON-NLS-1$

    }

    public void testTI1() {
        doTest("http://telecomitalia.it/SOA/E-ESB/AccountCreation", //$NON-NLS-1$
                "it.telecomitalia.SOA.E.ESB.AccountCreation"); //$NON-NLS-1$
        // Before XPD-1461, this was:
        // "it.telecomitalia.soa.e.esb.account.creation"
    }

    public void testTI2() {
        doTest("http://telecomitalia.it/SOA/AccountCreation/2009-09-28", //$NON-NLS-1$
                "it.telecomitalia.SOA.AccountCreation._2009._09._28"); //$NON-NLS-1$
        // Before XPD-1461, this was:
        // "it.telecomitalia.soa.account.creation._2009._09._28"
    }

    public void testTI3() {
        doTest("http://telecomitalia.it/SOA/SOAP/SOAPHeader", //$NON-NLS-1$
                "it.telecomitalia.SOA.SOAP.SOAPHeader"); //$NON-NLS-1$
        // Before XPD-1461, this was:
        // "it.telecomitalia.soa.soap.soap.header"
    }

    public void testTI4() {
        doTest("http://telecomitalia.it/SOA/AccountCreationCustomTypes/2009-09-28", //$NON-NLS-1$
                "it.telecomitalia.SOA.AccountCreationCustomTypes._2009._09._28"); //$NON-NLS-1$
    }

    public void testCov01() {
        doTest("http://www.amsbqa.com/linda01/XSDDefineTypes/types", //$NON-NLS-1$
                "com.amsbqa.linda01.XSDDefineTypes.types"); //$NON-NLS-1$
    }

    public void testCov02a() {
        doTest("http://www.amsbqa.com/Linda02/XSD1IncludeXSD2/BothDefineTypes/wsdl", //$NON-NLS-1$
                "com.amsbqa.Linda02.XSD1IncludeXSD2.BothDefineTypes.wsdl"); //$NON-NLS-1$
    }

    public void testCov02b() {
        doTest("http://www.amsbqa.com/linda02/XSD1ImportsXSD2_Both_Define_types02/XSD1", //$NON-NLS-1$
                "com.amsbqa.linda02.XSD1ImportsXSD2_Both_Define_types02.XSD1"); //$NON-NLS-1$
    }

    public void testCov02c() {
        doTest("http://www.amsbqa.com/Linda02/XSD1importXSD2/XSD2", //$NON-NLS-1$
                "com.amsbqa.Linda02.XSD1importXSD2.XSD2"); //$NON-NLS-1$

    }

    public void testCov03a() {
        doTest("http://www.amsbqa.com/linda03/XSD1IncludesXSD2/BothDefineTypes/wsdl", //$NON-NLS-1$
                "com.amsbqa.linda03.XSD1IncludesXSD2.BothDefineTypes.wsdl"); //$NON-NLS-1$
    }

    public void testCov03b() {
        doTest("http://www.amsbqa.com/linda03/XSD1IncludesXSD2_Both_Define_Types03", //$NON-NLS-1$
                "com.amsbqa.linda03.XSD1IncludesXSD2_Both_Define_Types03"); //$NON-NLS-1$
    }

    public void testCov03c() {
        doTest("http://www.amsbqa.com/linda03/XSD1IncludesXSD2_Both_Define_Types03/", //$NON-NLS-1$
                "com.amsbqa.linda03.XSD1IncludesXSD2_Both_Define_Types03"); //$NON-NLS-1$
    }

    public void testCov07() {
        doTest("http://www.amsbqa.com/Linda07/WSDLEmbedXSD1/XSD1importXSD2/Query/wsdl", //$NON-NLS-1$
                "com.amsbqa.Linda07.WSDLEmbedXSD1.XSD1importXSD2.Query.wsdl"); //$NON-NLS-1$
    }

    public void testCov07Airline() {
        doTest("http://www.xyzcorp/procureservice/Airline", //$NON-NLS-1$
                "xyzcorp.procureservice.Airline"); //$NON-NLS-1$
    }

    public void testCov08() {
        doTest("http://www.amsbqa.com/linda08/WSDLEmbedSchema1/Schema1IncludesSchema2", //$NON-NLS-1$
                "com.amsbqa.linda08.WSDLEmbedSchema1.Schema1IncludesSchema2"); //$NON-NLS-1$
    }

    public void testCov08XSD() {
        doTest("http://www.amsbqa.com/linda08/WSDLEmbedSchema1/Schema1IncludeSchema2", //$NON-NLS-1$
                "com.amsbqa.linda08.WSDLEmbedSchema1.Schema1IncludeSchema2"); //$NON-NLS-1$
    }

    public void testCov09() {
        doTest("http://www.amsbqa.com/Linda09/Schema1importSchema2/Schema1", //$NON-NLS-1$
                "com.amsbqa.Linda09.Schema1importSchema2.Schema1"); //$NON-NLS-1$
    }

    public void testCov10() {
        doTest("http://www.amsbqa.com/Linda10/Schema1importSchema2/WSDL/OperationImpl/WSDLEmbedSchema1__WSDLEmbedSchame2__Schema1IncludeSchma2__BothDefineTypes10", //$NON-NLS-1$
                "com.amsbqa.Linda10.Schema1importSchema2.WSDL.OperationImpl.WSDLEmbedSchema1__WSDLEmbedSchame2__Schema1IncludeSchma2__BothDefineTypes10"); //$NON-NLS-1$
    }
}
