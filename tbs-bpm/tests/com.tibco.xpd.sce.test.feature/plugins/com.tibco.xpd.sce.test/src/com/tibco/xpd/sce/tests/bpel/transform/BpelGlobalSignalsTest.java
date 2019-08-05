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
 * Test additional generation of global signal patterns in BPEL for new data
 * mapper mapping grammar.
 *
 *
 * @author aallway
 * @since 31 Jul 2019
 */
@SuppressWarnings("nls")
public class BpelGlobalSignalsTest extends AbstractBpelTransformTest {
    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getProjectLocations()
     *
     * @return
     */
    @Override
    protected String[] getProjectLocations() {
        return new String[] { "resources/AceGlobalSignalMappingDeployTest/GlobalSignalData/", //$NON-NLS-1$
                "resources/AceGlobalSignalMappingDeployTest/GlobalSignal/", //$NON-NLS-1$
                "resources/AceGlobalSignalMappingDeployTest/GlobalSignalProcess/" }; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProjectName()
     *
     * @return
     */
    @Override
    protected String getTestProjectName() {
        return "GlobalSignalProcess";
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProcessDescriptors()
     */
    @Override
    protected Collection<String> getTestProcessDescriptors() {
        return Arrays.asList("GlobalSignalProcess.xpdl/GlobalSignalProcessProcess");
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#checkBpelContent(Map)
     *
     * @param document
     */
    @Override
    protected void checkBpelContent(Map<String, Document> documentMap) {
        Document document = documentMap.get("GlobalSignalProcess.xpdl/GlobalSignalProcessProcess");
        // Check REST task shared resources
        NodeList processElems = document.getElementsByTagNameNS(BPWS_NS, "process");
        assertEquals("One process element.", 1, processElems.getLength());

        /*
         * =================================================================
         * Validate event handler pattern (free-floating intermediate catch
         * signal event)
         * =================================================================
         */
        checkGlobalSignalActivity(
                findFirstElement(document.getElementsByTagNameNS(TIBEX_NS, "onReceiveEvent"),
                        node -> node.getAttributes().getNamedItem("tibex:xpdlId") != null && "_uyPkMLH7Eemhueri8__qmA"
                                .equals(node.getAttributes().getNamedItem("tibex:xpdlId").getNodeValue())),
                "EventHandler",
                "data.TextField = SIGNAL_TextPayload;",
                true);

        /*
         * =================================================================
         * Validate event sub-process pattern
         * =================================================================
         */
        checkGlobalSignalActivity(
                findFirstElement(document.getElementsByTagNameNS(TIBEX_NS, "onReceiveEvent"),
                        node -> node.getAttributes().getNamedItem("tibex:xpdlId") != null && "_4mPqULH6Eemhueri8__qmA"
                                .equals(node.getAttributes().getNamedItem("tibex:xpdlId").getNodeValue())),
                "_BX_EventSubProcess__",
                "data.TextField = SIGNAL_TextPayload;",
                true);

        /*
         * =================================================================
         * Validate throw end event
         * =================================================================
         */
        checkGlobalSignalActivity(
                findFirstElement(document.getElementsByTagNameNS(TIBEX_NS, "extActivity"),
                        node -> node.getAttributes().getNamedItem("tibex:xpdlId") != null && "_2vLbA7H6Eemhueri8__qmA"
                                .equals(node.getAttributes().getNamedItem("tibex:xpdlId").getNodeValue())),
                "ThrowEnd",
                "SIGNAL_TextPayload = data.TextField;",
                false);

        /*
         * =================================================================
         * Validate throw intermediate event
         * =================================================================
         */
        checkGlobalSignalActivity(
                findFirstElement(document.getElementsByTagNameNS(TIBEX_NS, "extActivity"),
                        node -> node.getAttributes().getNamedItem("tibex:xpdlId") != null && "_gNx1MbH7Eemhueri8__qmA"
                                .equals(node.getAttributes().getNamedItem("tibex:xpdlId").getNodeValue())),
                "ThrowIntermediate",
                "SIGNAL_TextPayload = data.TextField;",
                false);

    }

    /**
     * Check the content of a catch/throw signal activity
     * 
     * @param globalSignalActivity
     * @param eventName
     * @param expectedScriptContains
     * @param checkCorrelation
     */
    private void checkGlobalSignalActivity(Optional<Node> globalSignalActivity, String eventName,
            String expectedScriptContains, boolean checkCorrelation) {

        assertTrue("Process has a <tibex:onReceiveEvent name=\"" + eventName + "\"... element",
                globalSignalActivity.isPresent());

        /* Should have a <tibex:mappingScript> child with some content.. */
        Optional<Node> mappingScript = findFirstElement(globalSignalActivity.get().getChildNodes(),
                node -> "mappingScript".equals(node.getLocalName()));

        assertTrue(eventName + " should contain a <tibex:mappingScript> element", mappingScript.isPresent());

        assertTrue(eventName + " mappingScript should contain a mapping script.",
                mappingScript.get().getChildNodes().getLength() > 0
                        && mappingScript.get().getChildNodes().item(0).toString().contains(expectedScriptContains));

        /* EventHandler Should still contain a GlobalSignalMappings element */
        Optional<Node> globalSignalMappings = findFirstElement(globalSignalActivity.get().getChildNodes(),
                node -> "GlobalSignalMappings".equals(node.getLocalName()));

        assertTrue(eventName + " should contain a <tibex:GlobalSignalMappings> element",
                globalSignalMappings.isPresent());

        /* Which should have signalVariables element. */
        Optional<Node> signalVariables = findFirstElement(globalSignalMappings.get().getChildNodes(),
                node -> "signalVariables".equals(node.getLocalName()));

        assertTrue(eventName + " GlobalSignalMappings should have a <tibex:signalVariables> element",
                signalVariables.isPresent());

        Optional<Node> variables = findFirstElement(signalVariables.get().getChildNodes(),
                node -> "variables".equals(node.getLocalName()));

        assertTrue(eventName + " GlobalSignalMappings/signalVariables should have a <bpws:variables> element",
                variables.isPresent());

        /* Which should have several variables. */
        Optional<Node> signalVariable = findFirstElement(variables.get().getChildNodes(),
                node -> "variable".equals(node.getLocalName()) && node.getAttributes().getNamedItem("name") != null
                        && node.getAttributes().getNamedItem("name").getNodeValue().startsWith("SIGNAL_"));

        assertTrue(eventName + " signalVariables has SIGNAL_ attributes", signalVariable.isPresent());

        if (checkCorrelation) {
            /*
             * Catch GlobalSignalMappings should also have correlationCopy
             * element.
             */
            Optional<Node> correlationCopy = findFirstElement(globalSignalMappings.get().getChildNodes(),
                    node -> "correlationCopy".equals(node.getLocalName()));

            assertTrue(eventName + " GlobalSignalMappings should have a <tibex:correlationCopy> element",
                    correlationCopy.isPresent());

            /* Which should have a single copy element. */
            Optional<Node> copy =
                    findFirstElement(correlationCopy.get().getChildNodes(), node -> "copy".equals(node.getLocalName()));

            assertTrue(eventName + " correlationCopy should have an <bpws:copy> element", copy.isPresent());

            /* That has copy from. */
            Optional<Node> from =
                    findFirstElement(copy.get().getChildNodes(), node -> "from".equals(node.getLocalName()));

            assertTrue(eventName + " correlationCopy <bpws:copy> element has a <bpws:from> element", from.isPresent());
            assertTrue(
                    eventName + " correlationCopy <bpws:copy>/<bpws:from> element should be a SIGNAL_xxx payload param",
                    from.get().getAttributes().getNamedItem("variable") != null && from.get().getAttributes()
                            .getNamedItem("variable").getNodeValue().equals("SIGNAL_CorrelationPayloadData"));

            /* That has copy to. */
            Optional<Node> to = findFirstElement(copy.get().getChildNodes(), node -> "to".equals(node.getLocalName()));

            assertTrue(eventName + " correlationCopy <bpws:copy> element has a <bpws:to> element", to.isPresent());
            assertTrue(eventName
                    + " correlationCopy <bpws:copy>/<bpws:to> element should be the process CorrelationField field",
                    to.get().getAttributes().getNamedItem("variable") != null && to.get().getAttributes()
                            .getNamedItem("variable").getNodeValue().equals("CorrelationField"));

        }

        /* We should not have variableCopy elements any more. */
        Optional<Node> variableCopy = findFirstElement(globalSignalMappings.get().getChildNodes(),
                node -> "variableCopy".equals(node.getLocalName()));

        assertTrue(eventName + " GlobalSignalMappings should have a <tibex:variableCopy> element",
                !variableCopy.isPresent() || variableCopy.get().getChildNodes().getLength() == 0);
    }
}
