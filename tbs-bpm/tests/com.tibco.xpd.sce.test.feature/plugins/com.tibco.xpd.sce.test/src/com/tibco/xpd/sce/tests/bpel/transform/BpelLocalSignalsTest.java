/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.sce.tests.bpel.transform;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
public class BpelLocalSignalsTest extends AbstractBpelTransformTest {
    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getProjectLocations()
     *
     * @return
     */
    @Override
    protected String[] getProjectLocations() {
        return new String[] { "resources/AceLocalSignalMappingDeployTest/LocalSignalData/", //$NON-NLS-1$
                "resources/AceLocalSignalMappingDeployTest/LocalSignalProcess/" }; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProjectName()
     *
     * @return
     */
    @Override
    protected String getTestProjectName() {
        return "LocalSignalProcess";
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProcessDescriptors()
     *
     * @return
     */
    @Override
    protected Collection<String> getTestProcessDescriptors() {
        return Collections.singletonList("LocalSignalProcess.xpdl/LocalSignalProcessProcess");
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#checkBpelContent(org.w3c.dom.Document)
     *
     * @param document
     */
    @Override
    protected void checkBpelContent(Map<String, Document> documentMap) {
        // Check REST task shared resources
        Document document = documentMap.get("LocalSignalProcess.xpdl/LocalSignalProcessProcess");

        NodeList processElems = document.getElementsByTagNameNS(BPWS_NS, "process");
        assertEquals("One process element.", 1, countElementNodes(processElems));

        /*
         * =================================================================
         * Validate Catch Signal 1 - this maps all data and switches attributes
         * on a complex type inflation mapping so we can check source and target
         * are used correctly in the script generation.
         * =================================================================
         */
        String mappingScript = checkLocalSignalActivity(
                findFirstElement(document.getElementsByTagNameNS(TIBEX_NS, "signalUpdateEvent"),
                        node -> node.getAttributes().getNamedItem("event") != null
                                && "Signal1".equals(node.getAttributes().getNamedItem("event").getNodeValue())),
                "Catch: Signal1",
                /*
                 * all fields are associated with the throw signal so they're
                 * all in signalVariables
                 */
                new String[] { "TextArrayField", "ComplexArrayField", "TextField", "ComplexField" },
                /*
                 * all fields are mapped to on RHS so all should appear in
                 * updateVariables
                 */
                new String[] { "TextArrayField", "ComplexArrayField", "TextField", "ComplexField" });

        assertTrue("Catch: Signal1: mapping script should use correct LHS/RHS order for assignments",
                mappingScript != null && mappingScript
                        .contains("data.ComplexField.attribute1 = SIGNAL_ComplexField.class2.attribute1;"));

        assertTrue("Catch: Signal1: target data should use BOM factories correctly.",
                mappingScript != null && mappingScript
                        .contains("data.ComplexField = factory.com_example_localsignaldata.createClass1();"));

        /*
         * .... everything else in mapping is tested to death elsewhere (as
         * we're using standard process data.
         */

        /*
         * =================================================================
         * Validate Catch Signal 1 - this maps all data and switches attributes
         * on a complex type inflation mapping so we can check source and target
         * are used correctly in the script generation.
         * =================================================================
         */
        mappingScript = checkLocalSignalActivity(
                findFirstElement(document.getElementsByTagNameNS(TIBEX_NS, "signalUpdateEvent"),
                        node -> node.getAttributes().getNamedItem("event") != null
                                && "Signal2".equals(node.getAttributes().getNamedItem("event").getNodeValue())),
                "Catch: Signal2",
                /*
                 * only 3 fields are associated with the throw signal so they're
                 * the only ones that should be in signalVariables
                 */
                new String[] { "ComplexArrayField", "TextField", "ComplexField" },
                /*
                 * only two fields are mapped to/into on RHS so only these two
                 * should appear in updateVariables
                 */
                new String[] { "ComplexArrayField", "ComplexField" });

        assertTrue("Catch: Signal1: mapping script should use correct LHS/RHS order for assignments",
                mappingScript != null
                        && mappingScript.contains("data.ComplexField.class2.attribute1 = SIGNAL_TextField;"));

    }

    /**
     * Checks the catch local signal.
     * 
     * @param signalUpdateEvent
     * @param eventName
     * @param expectedSignalVariables
     * @param expectedUpdateVariables
     * 
     * @return the mapping script text for the catch signal
     */
    private String checkLocalSignalActivity(Optional<Node> signalUpdateEvent, String eventName,
            String[] expectedSignalVariables, String[] expectedUpdateVariables) {

        assertTrue("Process should have a <tibex:signalUpdateEvent event=\"" + eventName + "\"... element",
                signalUpdateEvent.isPresent());

        /*
         * Which should have signalVariables element.
         */
        Optional<Node> signalVariables = findFirstElement(signalUpdateEvent.get().getChildNodes(),
                node -> "signalVariables".equals(node.getLocalName()));

        assertTrue(eventName + " SignalUpdateEvent should have a <tibex:signalVariables> element",
                signalVariables.isPresent());

        Optional<Node> variablesContainer = findFirstElement(signalVariables.get().getChildNodes(),
                node -> "variables".equals(node.getLocalName()));

        assertTrue(eventName + " SignalUpdateEvent/signalVariables should have a <bpws:variables> element",
                variablesContainer.isPresent());

        /* Which should have the expected variables. */
        NodeList variables = variablesContainer.get().getChildNodes();

        assertTrue(String.format("%s SignalUpdateEvent/signalVariables/variables should have %d variables in - was %d.  Nodes: (%s)",
                eventName,
                expectedSignalVariables.length,
                countElementNodes(variables),
                listElements(variables)), expectedSignalVariables.length == countElementNodes(variables));

        for (int i = 0; i < variables.getLength(); i++) {
            Node variable = variables.item(i);

            if (variable instanceof Element) {
                assertTrue(
                        eventName + "SignalUpdateEvent/signalVariables/variables should have all of: "
                                + expectedSignalVariables.toString(),
                        variable.getAttributes() != null && arrayContains(expectedSignalVariables,
                                variable.getAttributes().getNamedItem("name")));
            }
        }

        /*
         * Which should have updateVariables element.
         */
        Optional<Node> updateVariables = findFirstElement(signalUpdateEvent.get().getChildNodes(),
                node -> "updateVariables".equals(node.getLocalName()));

        assertTrue(eventName + " SignalUpdateEvent should have a <tibex:updateVariables> element",
                updateVariables.isPresent());

        variablesContainer = findFirstElement(updateVariables.get().getChildNodes(),
                node -> "variables".equals(node.getLocalName()));

        assertTrue(eventName + " SignalUpdateEvent/updateVariables should have a <bpws:variables> element",
                variablesContainer.isPresent());

        /* Which should have several variables. */
        variables = variablesContainer.get().getChildNodes();

        assertTrue(String.format(
                "%s SignalUpdateEvent/updateVariables/variables should have %d variables in - was %d.  Nodes: (%s)",
                eventName,
                expectedUpdateVariables.length,
                countElementNodes(variables),
                listElements(variables)), expectedUpdateVariables.length == countElementNodes(variables));

        for (int i = 0; i < variables.getLength(); i++) {
            Node variable = variables.item(i);

            if (variable instanceof Element) {
                assertTrue(
                        eventName + "SignalUpdateEvent/updateVariables/variables should have all of: "
                                + expectedUpdateVariables.toString(),
                        variable.getAttributes() != null && arrayContains(expectedUpdateVariables,
                                variable.getAttributes().getNamedItem("name")));
            }
        }

        /* We should not have <bpws:copy> elements any more. */
        Optional<Node> copy =
                findFirstElement(signalUpdateEvent.get().getChildNodes(), node -> "copy".equals(node.getLocalName()));

        assertTrue(eventName + " SignalUpdateEvent should have a <bpws:copy> element", !copy.isPresent());

        /*
         * Should have a <tibex:mappingScript> child with some content (checked
         * by caller).
         */
        Optional<Node> mappingScript = findFirstElement(signalUpdateEvent.get().getChildNodes(),
                node -> "mappingScript".equals(node.getLocalName()));

        assertTrue(eventName + " SignalUpdateEvent should contain a <tibex:mappingScript> element",
                mappingScript.isPresent());

        String mappingScriptText = "";

        if (mappingScript.get().getChildNodes().getLength() > 0) {
            mappingScriptText = mappingScript.get().getChildNodes().item(0).toString();
        }

        return mappingScriptText;
    }

    /**
     * @param nodes
     * @return number of nodes in the list that are XML Elements.
     */
    public int countElementNodes(NodeList nodes) {
        int count = 0;

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node instanceof Element) {
                count++;
            }
        }

        return count;
    }

    /**
     * @param nodes
     * @return List of node names in the list that are XML Elements.
     */
    public String listElements(NodeList nodes) {
        String elements = "";

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node instanceof Element) {
                if (elements.length() > 0) {
                    elements += ", ";
                }
                elements += "\n<" + node.getNodeName();

                for (int j = 0; j < node.getAttributes().getLength(); j++) {
                    Node attr = node.getAttributes().item(j);

                    elements += " " + attr.getNodeName();
                    elements += "\"" + attr.getNodeValue() + "\"";
                }

                elements += ">";

            }
        }

        return elements;
    }

    private boolean arrayContains(Object[] arr, Node value) {
        if (arr != null && value != null) {
            for (Object a : arr) {
                if (a.equals(value.getNodeValue())) {
                    return true;
                }
            }
        }
        return false;
    }

}
