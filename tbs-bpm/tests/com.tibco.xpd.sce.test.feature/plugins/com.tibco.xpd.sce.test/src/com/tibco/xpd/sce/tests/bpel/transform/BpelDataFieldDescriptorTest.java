/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.sce.tests.bpel.transform;

import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Test additional bpws:process attributes added for the dataFieldDescriptor JS
 * file path and process-specific class name.
 *
 *
 * @author aallway
 * @since 13 Jun 2019
 */
@SuppressWarnings("nls")
public class BpelDataFieldDescriptorTest extends AbstractBpelTransformTest {
    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getProjectLocations()
     *
     * @return
     */
    @Override
    protected String[] getProjectLocations() {
        return new String[] {
                "resources/BpelTransform/DataFieldDescriptorTest/DataFieldDescriptorData/",
                "resources/BpelTransform/DataFieldDescriptorTest/DataFieldDescriptorProcess/" };
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProjectName()
     *
     * @return
     */
    @Override
    protected String getTestProjectName() {
        return "DataFieldDescriptorProcess";
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestXpdlName()
     *
     * @return
     */
    @Override
    protected String getTestXpdlName() {
        return "DataFieldDescriptor.xpdl";
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProcessName()
     *
     * @return
     */
    @Override
    protected String getTestProcessName() {
        return "MainProcess";
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#checkBpelContent(org.w3c.dom.Document)
     *
     * @param document
     */
    @Override
    protected void checkBpelContent(Document document) {
        // Check REST task shared resources
        NodeList invokeElems =
                document.getElementsByTagNameNS(BPWS_NS, "process");
        assertEquals("One process element.", 1, invokeElems.getLength());
        Node invokeElem = invokeElems.item(0);

        /*
         * The following should appear in the bpws:process element to identify
         * the data field descriptor script for the process.
         * tibex:dataFieldDescriptorScript=
         * "/process-js/<process-package-name>/<process-name>/<process-name>.js"
         * 
         * tibex:dataFieldDescriptorClass=
         * "<application-id>.<process-package-name>.<process-name>"
         */

        assertAttr(invokeElem,
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorScript",
                Optional.of(
                        "/process-js/Datafielddescriptor/MainProcess/MainProcess.js"));

        assertAttr(invokeElem,
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorClass",
                Optional.of(
                        "com.tibco.ace.datafielddescriptor.test.Datafielddescriptor.MainProcess"));

    }
}
