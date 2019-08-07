/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.sce.tests.bpel.transform;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Test shared resource references in BPEL Model in converted from xpdl.
 */
@SuppressWarnings("nls")
public class BpelSharedResourceTest extends AbstractBpelTransformTest {

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getProjectLocations()
     *
     * @return
     */
    @Override
    protected String[] getProjectLocations() {
        return new String[] {
                "resources/BpelTransform/SharedResourceTest/TestProcess/",
                "resources/BpelTransform/SharedResourceTest/TestRest/" };
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProjectName()
     *
     * @return
     */
    @Override
    protected String getTestProjectName() {
        return "TestProcess";
    }


    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProcessDescriptors()
     *
     * @return
     */
    @Override
    protected Collection<String> getTestProcessDescriptors() {
        return Arrays.asList("TestProcess.xpdl/TestProcessProcess");
    }


    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#checkBpelContent(org.w3c.dom.Document)
     *
     * @param document
     */
    @Override
    protected void checkBpelContent(Map<String, Document> documentMap) {
        Document document = documentMap.get("TestProcess.xpdl/TestProcessProcess");
        // Check REST task shared resources
        NodeList invokeElems =
                document.getElementsByTagNameNS(BPWS_NS, "invoke");
        assertEquals("One invoke element.", 1, invokeElems.getLength());
        Node invokeElem = invokeElems.item(0);
        assertAttr(invokeElem,
                Optional.of(TIBEX_NS),
                "invokeType",
                Optional.of("REST"));
        assertAttr(invokeElem,
                Optional.of(TIBEX_NS),
                "sharedResourceType",
                Optional.of("HTTPClient"));
        assertAttr(invokeElem,
                Optional.of(TIBEX_NS),
                "sharedResourceName",
                Optional.of("JanResName"));
        assertAttr(invokeElem,
                Optional.of(TIBEX_NS),
                "sharedResourceDescription",
                Optional.of("JanResDesc"));

        // Check Email task shared resources
        NodeList extActivityElems =
                document.getElementsByTagNameNS(TIBEX_NS, "extActivity");
        assertEquals("One extActivity element.",
                1,
                extActivityElems.getLength());
        Node extActivityElem = extActivityElems.item(0);
        assertAttr(extActivityElem,
                Optional.of(TIBEX_NS),
                "sharedResourceType",
                Optional.of("EMail"));
        assertAttr(extActivityElem,
                Optional.of(TIBEX_NS),
                "sharedResourceName",
                Optional.of("EmailSender"));

        // Check that Email sub-element doesn't have connectionResource
        // attribute.
        Optional<Node> emailElemnt =
                findFirstElement(extActivityElem.getChildNodes(),
                        node -> "Email".equals(node.getLocalName()));
        assertTrue("extActivity doesn't have Email subelement",
                emailElemnt.isPresent());
        assertTrue("Email element should NOT have 'connectionResource' attr.",
                !hasAttribute(emailElemnt.get(),
                        /* un-prefixed - no namespace */Optional.empty(),
                        "connectionResource",
                        /* don't care about value */Optional.empty()));
    }

}
