/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
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
 * Test transformation of Case Data Operation Service task.
 */
@SuppressWarnings("nls")
public class BpelCaseDataOperationActivityTest extends AbstractBpelTransformTest {

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getProjectLocations()
     *
     * @return
     */
    @Override
    protected String[] getProjectLocations() {
        return new String[] { "resources/BpelTransform/CaseDataTaskTest/BomLinksProcess/",
                "resources/BpelTransform/CaseDataTaskTest/BomLinks/" };
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProjectName()
     *
     * @return
     */
    @Override
    protected String getTestProjectName() {
        return "BomLinksProcess";
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProcessDescriptors()
     *
     * @return
     */
    @Override
    protected Collection<String> getTestProcessDescriptors() {
        return Arrays.asList("BomLinksProcess.xpdl/BomLinksProcessProcess");
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#checkBpelContent(org.w3c.dom.Document)
     *
     * @param document
     */
    @Override
    protected void checkBpelContent(Map<String, Document> documentMap) {
        {
            Document document = documentMap.get("BomLinksProcess.xpdl/BomLinksProcessProcess");
            checkCaseDataTask(document);
        }
    }

    /**
     * @param document
     */
    private void checkCaseDataTask(Document document) {
        NodeList extActivities = document.getElementsByTagNameNS(TIBEX_NS, "extActivity");
        assertTrue("Element tibex:extActivity is missing.", extActivities.getLength() > 0);
        Optional<Node> deleteLinksTask =
                findFirstElement(extActivities, node -> hasAttrValue(node, "name", "DeleteLinks"));
        assertTrue("Missing 'DeleteLinks' activity", deleteLinksTask.isPresent());

        Optional<Node> scriptElement = findChildElement(deleteLinksTask.get(), "tibex:script");
        assertTrue("Script element is missing.", scriptElement.isPresent());
        assertAttrValue(scriptElement.get(), "expressionLanguage", "urn:tibco:wsbpel:2.0:sublang:javascript");
        assertEquals("Script content invalid",
                "bpm.caseData.unlinkAllByLinkName(data.OrderCaseRef,'product');",
                scriptElement.get().getTextContent());
    }
}
