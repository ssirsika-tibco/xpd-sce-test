/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * 
 * @author patkinso
 * @since 25 May 2012
 */
public class WsdlPathFactoryTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link com.tibco.xpd.implementer.script.WsdlPathFactory#createWsdlPath(com.tibco.xpd.xpdl2.WebServiceOperation, java.lang.String, boolean)}
     * .
     */
    @Test
    public void testRegenerateXPathesqPath() {

        String[] testPathValues =
                {
                        "wso:/part:param/group:sequence[0]/%1$s:CompanyAccount[1]",
                        "wso:/part:param/group:sequence[0]/%1$s:CompanyAccount{1}",
                        "wso:/part:param/group:sequence[0]/%1$s:CompanyAccount[1]/%2$s:TaxCode",
                        "wso:/part:param/group:sequence[0]/%1$s:CompanyAccount[1]/%2$s:TaxCode/%1$s:Detail" };

        String[] expectedPathValues =
                {
                        "wso:/part:param/group:sequence[0]/ns6:CompanyAccount[1]",
                        "wso:/part:param/group:sequence[0]/ns6:CompanyAccount{1}",
                        "wso:/part:param/group:sequence[0]/ns6:CompanyAccount[1]/ns4:TaxCode",
                        "wso:/part:param/group:sequence[0]/ns6:CompanyAccount[1]/ns4:TaxCode/ns6:Detail" };

        String[] testSetValues = { "ns6", "ns6", "ns6,ns4", "ns6,ns4" };

        for (int i = 0; i < testPathValues.length; i++) {
            LinkedHashSet<String> testSet = new LinkedHashSet<String>();
            for (String ns : testSetValues[i].split(",")) {
                testSet.add(ns);
            }
            String actualPath =
                    WsdlPathFactory.regenerateXPathesqPath(testPathValues[i],
                            testSet);
            Assert.isTrue(expectedPathValues[i].equals(actualPath));
        }

    }

    @Test
    public void testGetXPathesqPathWithExtractedNamespaces() {

        String[] testPathValues =
                {
                        "wso:setDeliveryBundleComplete/part:request/group:sequence[0]/bpmsdo:Header[0]/group:sequence[0]/esbenv:MessageId[0]",
                        "wso:setDeliveryBundleComplete/part:request/group:sequence[0]/bpmsdo:Body[1]/group:sequence[0]/bpmsdo:LOB[3]",
                        "wso:/part:param/xsd:any[1]/ns6:CompanyAccount[1]/ns4:TaxCode",
                        "wso:/part:param/group:choice[1]/ns6:CompanyAccount[1]/ns4:TaxCode",
                        "wso:/part:param/group:sequence[0]/ns6:CompanyAccount[1]",
                        "wso:/part:param/group:sequence[0]/ns6:CompanyAccount{1}",
                        "wso:/part:param/group:sequence[0]/ns6:CompanyAccount[1]/ns4:TaxCode",
                        "wso:/part:param/group:sequence[0]/ns6:CompanyAccount[1]/ns4:TaxCode/ns6:Detail",

                };

        String[] expectedPathValues =
                {
                        "wso:setDeliveryBundleComplete/part:request/group:sequence[0]/%1$s:Header[0]/group:sequence[0]/%2$s:MessageId[0]",
                        "wso:setDeliveryBundleComplete/part:request/group:sequence[0]/%1$s:Body[1]/group:sequence[0]/%1$s:LOB[3]",
                        "wso:/part:param/xsd:any[1]/%1$s:CompanyAccount[1]/%2$s:TaxCode",
                        "wso:/part:param/group:choice[1]/%1$s:CompanyAccount[1]/%2$s:TaxCode",
                        "wso:/part:param/group:sequence[0]/%1$s:CompanyAccount[1]",
                        "wso:/part:param/group:sequence[0]/%1$s:CompanyAccount{1}",
                        "wso:/part:param/group:sequence[0]/%1$s:CompanyAccount[1]/%2$s:TaxCode",
                        "wso:/part:param/group:sequence[0]/%1$s:CompanyAccount[1]/%2$s:TaxCode/%1$s:Detail",

                };

        int[] expectedMapSizes = { 2, 1, 2, 2, 1, 1, 2, 2 };

        assert (testPathValues.length == expectedPathValues.length) : "Number of test values does not match the number of expected results";

        for (int i = 0; i < testPathValues.length; i++) {

            LinkedHashSet<String> nsSet = new LinkedHashSet<String>();
            // Map<String, Integer> nsMap = new HashMap<String, Integer>();
            String actualResult =
                    WsdlPathFactory
                            .getXPathesqPathWithExtractedNamespaces(testPathValues[i],
                                    nsSet);

            Assert.isTrue(expectedPathValues[i].equals(actualResult));
            Assert.isTrue(expectedMapSizes[i] == nsSet.size());
            for (String s : nsSet) {
                System.out.println("> " + s);
            }
            System.out.println();
        }

    }

    @Test
    public void testUpdateNamespacesOnXPathesqPaths() {

        String[] testPathValues =
                {
                        "wso:/part:param/group:sequence[0]/ns6:CompanyAccount[1]",
                        "wso:/part:param/group:sequence[0]/ns6:CompanyAccount{1}",
                        "wso:/part:param/group:sequence[0]/ns6:CompanyAccount[1]/ns4:TaxCode",
                        "wso:/part:param/group:sequence[0]/ns6:CompanyAccount[1]/ns4:TaxCode/ns6:Detail",
                        "wso:setDeliveryBundleComplete/part:request/group:sequence[0]/bpmsdo:Header[0]/group:sequence[0]/esbenv:MessageId[0]",
                        "wso:setDeliveryBundleComplete/part:request/group:sequence[0]/bpmsdo:Body[1]/group:sequence[0]/bpmsdo:LOB[3]"

                };

        String[] expectedPathValues =
                {
                        "wso:/part:param/group:sequence[0]/lala6:CompanyAccount[1]",
                        "wso:/part:param/group:sequence[0]/lala6:CompanyAccount{1}",
                        "wso:/part:param/group:sequence[0]/lala6:CompanyAccount[1]/wawa4:TaxCode",
                        "wso:/part:param/group:sequence[0]/lala6:CompanyAccount[1]/wawa4:TaxCode/lala6:Detail",
                        "wso:setDeliveryBundleComplete/part:request/group:sequence[0]/blah:Header[0]/group:sequence[0]/fishcakes:MessageId[0]",
                        "wso:setDeliveryBundleComplete/part:request/group:sequence[0]/blah:Body[1]/group:sequence[0]/blah:LOB[3]"

                };

        Map<String, String> map = new HashMap<String, String>();
        map.put("ns6", "lala6");
        map.put("ns4", "wawa4");
        map.put("bpmsdo", "blah");
        map.put("esbenv", "fishcakes");

        assert (testPathValues.length == expectedPathValues.length) : "Number of test values does not match the number of expected results";

        for (int i = 0; i < testPathValues.length; i++) {

            String actualResult =
                    WsdlPathFactory
                            .updateNamespacesOnXPathesqPaths(testPathValues[i],
                                    map);

            Assert.isTrue(expectedPathValues[i].equals(actualResult));
        }

    }

}
