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
 * Test business data correlation (ACE-3290) for incoming request activities in BPEL Model converted from xpdl.
 * 
 * @author aallway
 * @since Oct 2022
 */
@SuppressWarnings("nls")
public class BpelIncomingRequestCorrelationTest extends AbstractBpelTransformTest {

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getProjectLocations()
     *
     * @return
     */
    @Override
    protected String[] getProjectLocations() {
        return new String[] { "resources/Ace3290IncomingeuqestCorrelation/IncomingRequestAct/" };
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProjectName()
     *
     * @return
     */
    @Override
    protected String getTestProjectName() {
        return "IncomingRequestAct";
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProcessDescriptors()
     *
     * @return
     */
    @Override
    protected Collection<String> getTestProcessDescriptors() {
        return Arrays.asList("EventBaseGateway.xpdl/EventBaseGatewayProcess",
                "EventSubProcess.xpdl/EventSubProcessProcess",
                "InterEventHandler.xpdl/InterEventHandlerProcess",
                "InterInFlowEvent.xpdl/InterInFlowEventProcess",
                "ReceiveTask.xpdl/ReceiveTaskProcess",
                "TaskBoundaryCatchEvent.xpdl/TaskBoundaryCatchEventProcess",
                "IncomingRequest.xpdl/IncomingRequestProcessNoCorrelation");
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#checkBpelContent(org.w3c.dom.Document)
     *
     * @param document
     */
    @Override
    protected void checkBpelContent(Map<String, Document> documentMap) {
        {
            Document document = documentMap.get("EventBaseGateway.xpdl/EventBaseGatewayProcess");
            checkEventBasedGatewayBpel(document);
        }
        {
            Document document = documentMap.get("EventSubProcess.xpdl/EventSubProcessProcess");
            checkEventSubProcessBpel(document);
        }
        {
            Document document = documentMap.get("InterEventHandler.xpdl/InterEventHandlerProcess");
            checkInterEventHandlerBpel(document);
        }
        {
            Document document = documentMap.get("InterInFlowEvent.xpdl/InterInFlowEventProcess");
            checkInterInFlowEventBpel(document);
        }
        {
            Document document = documentMap.get("ReceiveTask.xpdl/ReceiveTaskProcess");
            checkReceiveTaskBpel(document);
        }
        {
            Document document = documentMap.get("TaskBoundaryCatchEvent.xpdl/TaskBoundaryCatchEventProcess");
            checkTaskBoundaryCatchEventBpel(document);
        }
        {
            Document document = documentMap.get("IncomingRequest.xpdl/IncomingRequestProcessNoCorrelation");
            checkIncomingRequestProcessNoCorrelation(document);
        }

    }

    /**
     * @param document
     */
    private void checkEventBasedGatewayBpel(Document document) {
        // Check pick bpel activities
        NodeList pickElems = document.getElementsByTagNameNS(BPWS_NS, "pick");
        assertEquals("Two pick elements expected.", 2, pickElems.getLength());
        // Check first pick with atrr name="_BX_pick"
        {
            Optional<Node> pick = findFirstElement(pickElems, node -> hasAttrValue(node, "name", "_BX_pick"));
            assertTrue("Missing pick", pick.isPresent());

            Optional<Node> onMessage =
                    findFirstElement(pick.get().getChildNodes(), node -> "onMessage".equals(node.getLocalName()));
            assertTrue("Missing onMessage of the pick", onMessage.isPresent());

			// check if timeout attr is present
			assertNull(onMessage.get().getAttributes().getNamedItem("messageTimeout"));

            // ACE-2388 check if correlateImmediate is set
            assertAttrNsValue(onMessage.get(), TIBEX_NS, "correlateImmediate", "yes");

            // check onMessage has the assign for specific xpdl gateway element
            Optional<Node> assign = findFirstElement(onMessage.get().getChildNodes(),
                    node -> hasAttrNsValue(node, TIBEX_NS, "xpdlId", "_OJp1MbNrEemyOeCi73RnLA"));
            assertTrue("Missing assigne of the onMessage of the pick", assign.isPresent());
            // check the tibex type of the assignment
            assertAttrNsValue(assign.get(), TIBEX_NS, "type", "catchMessageIntermediateEvent");

            // Sid ACE-3332 Check parameters are added
            Optional<Node> paramDesc = findChildElement(assign.get(), "tibex:parameters/parameterDescription");
            assertTrue("Missing tibex:parameters/parameterDescription", paramDesc.isPresent());
            assertAttrValue(paramDesc.get(), "name", "Parameter");
            assertAttrValue(paramDesc.get(), "mode", "In");
            assertAttrValue(paramDesc.get(), "mandatory", "yes");

            /* Check correlations */
            checkCorrelations(onMessage,
                    new String[] { "CorrelationField", "CorrelationField2", "CorrelationField3", "CorrelationField4" });
        }
        // Check second pick with atrr name="_BX_pick_2"
        {
            Optional<Node> pick = findFirstElement(pickElems, node -> hasAttrValue(node, "name", "_BX_pick_2"));
            assertTrue("Missing pick", pick.isPresent());

            Optional<Node> onMessage =
                    findFirstElement(pick.get().getChildNodes(), node -> "onMessage".equals(node.getLocalName()));
            assertTrue("Missing onMessage of the pick", onMessage.isPresent());

			// check if timeout attr is present
			assertNull(onMessage.get().getAttributes().getNamedItem("messageTimeout"));

            // ACE-2388 check if correlateImmediate is set
            assertAttrNsValue(onMessage.get(), TIBEX_NS, "correlateImmediate", "yes");

            // check onMessage has the assign for specific xpdl gateway element
            Optional<Node> assign = findFirstElement(onMessage.get().getChildNodes(),
                    node -> hasAttrNsValue(node, TIBEX_NS, "xpdlId", "_ThL6krNrEemyOeCi73RnLA"));
            assertTrue("Missing assigne of the onMessage of the pick", assign.isPresent());
            // check the tibex type of the assignment
            assertAttrNsValue(assign.get(), TIBEX_NS, "type", "receiveTask");

            // Sid ACE-3332 Check parameters are added
            Optional<Node> paramDesc = findChildElement(assign.get(), "tibex:parameters/parameterDescription");
            assertTrue("Missing tibex:parameters/parameterDescription", paramDesc.isPresent());
            assertAttrValue(paramDesc.get(), "name", "Parameter");
            assertAttrValue(paramDesc.get(), "mode", "In");
            assertAttrValue(paramDesc.get(), "mandatory", "yes");

            /* Check correlations */
            checkCorrelations(onMessage,
                    new String[] { "CorrelationField", "CorrelationField2", "CorrelationField3", "CorrelationField4" });

        }
    }

    /**
     * @param document
     */
    private void checkEventSubProcessBpel(Document document) {
        // Check onEvent
        NodeList onEventElems = document.getElementsByTagNameNS(BPWS_NS, "onEvent");
        assertEquals("Element onEvent is missing.", 1, onEventElems.getLength());
        Node onEventElem = onEventElems.item(0);
        // Check attrs. have the expected values
		assertNull(onEventElem.getAttributes().getNamedItem("messageTimeout"));
        assertAttrNsValue(onEventElem, TIBEX_NS, "blockUntilCompleted", "yes");
        assertAttrNsValue(onEventElem, TIBEX_NS, "xpdlId", "_fB8G0K1eEemheL-rDtMNiw");

        // ACE-2388 check if correlateImmediate is set
        assertAttrNsValue(onEventElem, TIBEX_NS, "correlateImmediate", "yes");

        // Check onEvent has a scope and a the flow in it.
        Optional<Node> flow = findChildElement(onEventElem, "bpws:scope/bpws:flow");
        assertTrue("Flow is missing.", flow.isPresent());
        assertAttrNsValue(flow.get(), TIBEX_NS, "type", "eventSubProcess");

        Optional<Node> assign = findFirstElement(flow.get().getChildNodes(),
                node -> "assign".equals(node.getLocalName())
                        && hasAttrNsValue(node, TIBEX_NS, "xpdlId", "_fB8G0K1eEemheL-rDtMNiw"));
        assertTrue("Element assign is missing.", assign.isPresent());
        assertAttrNsValue(assign.get(), TIBEX_NS, "type", "messageStartEvent");

        // Sid ACE-3332 Check parameters are added
        Optional<Node> paramDesc = findChildElement(assign.get(), "tibex:parameters/parameterDescription");
        assertTrue("Missing tibex:parameters/parameterDescription", paramDesc.isPresent());
        assertAttrValue(paramDesc.get(), "name", "login");
        assertAttrValue(paramDesc.get(), "mode", "In");
        assertAttrValue(paramDesc.get(), "mandatory", "yes");

        /* Check correlations */
        checkCorrelations(Optional.of(onEventElem),
                new String[] { "CorrelationField", "CorrelationField2", "CorrelationField3" });

    }

    /**
     * @param document
     */
    private void checkInterEventHandlerBpel(Document document) {
        // Check onEvent
        NodeList onEventElems = document.getElementsByTagNameNS(BPWS_NS, "onEvent");
        assertEquals("Element onEvent is missing.", 1, onEventElems.getLength());
        Node onEventElem = onEventElems.item(0);
        // Check attrs. have the expected values
		assertNull(onEventElem.getAttributes().getNamedItem("messageTimeout"));
        assertAttrNsValue(onEventElem, TIBEX_NS, "blockUntilCompleted", "yes");
        assertAttrNsValue(onEventElem, TIBEX_NS, "xpdlId", "_T9JOkK1cEemheL-rDtMNiw");
        // ACE-2388 check if correlateImmediate is set
        assertAttrNsValue(onEventElem, TIBEX_NS, "correlateImmediate", "yes");

        // Check onEvent has a scope and a the flow in it.
        Optional<Node> scope =
                findFirstElement(onEventElem.getChildNodes(), node -> "scope".equals(node.getLocalName()));
        Optional<Node> flow = findFirstElement(scope.get().getChildNodes(), node -> "flow".equals(node.getLocalName()));
        assertTrue("Flow is missing.", flow.isPresent());

        Optional<Node> assign = findFirstElement(flow.get().getChildNodes(),
                node -> "assign".equals(node.getLocalName())
                        && hasAttrNsValue(node, TIBEX_NS, "xpdlId", "_T9JOkK1cEemheL-rDtMNiw"));
        assertTrue("Element assign is missing.", assign.isPresent());
        assertAttrNsValue(assign.get(), TIBEX_NS, "type", "catchMessageIntermediateEvent");
        Optional<Node> completedScript = findFirstElement(assign.get().getChildNodes(),
                node -> "tibex:completedScript".equals(node.getNodeName()));
        assertTrue("Complete sctipt is missing.", completedScript.isPresent());
        assertAttrValue(completedScript.get(), "expressionLanguage", "urn:tibco:wsbpel:2.0:sublang:javascript");
        assertEquals("Script content invalid", "var x = \"complete\";", completedScript.get().getTextContent());

        // Sid ACE-3332 Check parameters are added
        Optional<Node> paramDesc = findChildElement(assign.get(), "tibex:parameters/parameterDescription");
        assertTrue("Missing tibex:parameters/parameterDescription", paramDesc.isPresent());
        assertAttrValue(paramDesc.get(), "name", "login");
        assertAttrValue(paramDesc.get(), "mode", "In");
        assertAttrValue(paramDesc.get(), "mandatory", "yes");

        /* Check correlations */
        checkCorrelations(Optional.of(onEventElem),
                new String[] { "CorrelationField", "CorrelationField2", "CorrelationField3", "CorrelationField4" });

    }

    /**
     * @param document
     */
    private void checkInterInFlowEventBpel(Document document) {
        // Check onEvent
        NodeList scopeElems = document.getElementsByTagNameNS(BPWS_NS, "scope");
        assertEquals("Should be a bpws:scope for each of 2 events", 2, scopeElems.getLength());

        {
            Node scopeElem = scopeElems.item(0);
            // Check attrs. have the expected values
            assertAttrValue(scopeElem, "name", "IntermediateEvent");
            assertAttrNsValue(scopeElem, TIBEX_NS, "migrationAllowed", "yes");
            assertAttrNsValue(scopeElem, TIBEX_NS, "type", "catchMessageIntermediateEvent");
            assertAttrNsValue(scopeElem, TIBEX_NS, "xpdlId", "_rWYJYK1dEemheL-rDtMNiw");

            // Check onEvent has a scope and a the flow in it.
            Optional<Node> receive = findChildElement(scopeElem, "bpws:sequence/bpws:receive");
            assertTrue("Receive is missing.", receive.isPresent());
			assertNull(receive.get().getAttributes().getNamedItem("messageTimeout"));
            assertAttrValue(receive.get(), "createInstance", "no");
            // ACE-2388 check if correlateImmediate is set
            assertAttrNsValue(receive.get(), TIBEX_NS, "correlateImmediate", "yes");

            Optional<Node> assign = findChildElement(scopeElem, "bpws:sequence/bpws:assign");
            assertTrue("Element assign is missing.", assign.isPresent());

            // Sid ACE-3332 Check parameters are added
            Optional<Node> paramDesc = findChildElement(scopeElem, "tibex:parameters/parameterDescription");
            assertTrue("Missing tibex:parameters/parameterDescription", paramDesc.isPresent());
            assertAttrValue(paramDesc.get(), "name", "login");
            assertAttrValue(paramDesc.get(), "mode", "In");
            assertAttrValue(paramDesc.get(), "mandatory", "yes");

            /* Check correlations - implicit all */
            checkCorrelations(receive,
                    new String[] { "CorrelationField", "CorrelationField2", "CorrelationField3", "CorrelationField4" });
        }

        {
            Node scopeElem = scopeElems.item(1);
            // Check attrs. have the expected values
            assertAttrValue(scopeElem, "name", "IntermediateEventExplicitCorrelation");
            assertAttrNsValue(scopeElem, TIBEX_NS, "migrationAllowed", "yes");
            assertAttrNsValue(scopeElem, TIBEX_NS, "type", "catchMessageIntermediateEvent");
            assertAttrNsValue(scopeElem, TIBEX_NS, "xpdlId", "_inq60U7wEe2qoreGpOJ_xw");

            // Check onEvent has a scope and a the flow in it.
            Optional<Node> receive = findChildElement(scopeElem, "bpws:sequence/bpws:receive");
            assertTrue("Receive is missing.", receive.isPresent());
			assertNull(receive.get().getAttributes().getNamedItem("messageTimeout"));
            assertAttrValue(receive.get(), "createInstance", "no");

            Optional<Node> assign = findChildElement(scopeElem, "bpws:sequence/bpws:assign");
            assertTrue("Element assign is missing.", assign.isPresent());

            // Sid ACE-3332 Check parameters are added
            Optional<Node> paramDesc = findChildElement(scopeElem, "tibex:parameters/parameterDescription");
            assertTrue("Missing tibex:parameters/parameterDescription", paramDesc.isPresent());
            assertAttrValue(paramDesc.get(), "name", "login");
            assertAttrValue(paramDesc.get(), "mode", "In");
            assertAttrValue(paramDesc.get(), "mandatory", "yes");

            /* Check correlations - explicit CorrelationField1 and 3 */
            checkCorrelations(receive, new String[] { "CorrelationField", "CorrelationField3" });
        }
    }

    /**
     * @param document
     */
    private void checkReceiveTaskBpel(Document document) {
        NodeList scopeElems = document.getElementsByTagNameNS(BPWS_NS, "scope");
        assertEquals("Should be a bpws:scope for each of 2 events", 2, scopeElems.getLength());
        {
            Optional<Node> receiveTaskScope =
                    findFirstElement(scopeElems, node -> hasAttrValue(node, "name", "ReceiveTask"));
            assertAttrNsValue(receiveTaskScope.get(), TIBEX_NS, "xpdlId", "_Dg_IsK1dEemheL-rDtMNiw");

            assertTrue("Missing scope for receive task", receiveTaskScope.isPresent());
            assertAttrNsValue(receiveTaskScope.get(), TIBEX_NS, "migrationAllowed", "yes");

            Optional<Node> receive = findChildElement(receiveTaskScope.get(), "bpws:sequence/bpws:receive");
            assertTrue("Missing bpws:receive", receive.isPresent());
			assertNull(receive.get().getAttributes().getNamedItem("messageTimeout"));
            assertAttrValue(receive.get(), "createInstance", "no");
            // ACE-2388 check if correlateImmediate is set
            assertAttrNsValue(receive.get(), TIBEX_NS, "correlateImmediate", "yes");

            // Sid ACE-3332 Check parameters are added
            Optional<Node> paramDesc =
                    findChildElement(receiveTaskScope.get(), "tibex:parameters/parameterDescription");
            assertTrue("Missing tibex:parameters/parameterDescription", paramDesc.isPresent());
            assertAttrValue(paramDesc.get(), "name", "login");
            assertAttrValue(paramDesc.get(), "mode", "In");
            assertAttrValue(paramDesc.get(), "mandatory", "yes");

            /* Check correlations - implicit all */
            checkCorrelations(receive,
                    new String[] { "CorrelationField", "CorrelationField2", "CorrelationField3", "CorrelationField4" });
        }

        {
            Optional<Node> receiveTaskScope =
                    findFirstElement(scopeElems, node -> hasAttrValue(node, "name", "ReceiveTaskExplicitCorrelation"));
            assertAttrNsValue(receiveTaskScope.get(), TIBEX_NS, "xpdlId", "_boDdUk7wEe2qoreGpOJ_xw");

            assertTrue("Missing outer scope for receive task", receiveTaskScope.isPresent());
            assertAttrNsValue(receiveTaskScope.get(), TIBEX_NS, "migrationAllowed", "yes");

            Optional<Node> receive = findChildElement(receiveTaskScope.get(), "bpws:sequence/bpws:receive");
            assertTrue("Missing bpws:receive", receive.isPresent());
			assertNull(receive.get().getAttributes().getNamedItem("messageTimeout"));
            assertAttrValue(receive.get(), "createInstance", "no");
            // ACE-2388 check if correlateImmediate is set
            assertAttrNsValue(receive.get(), TIBEX_NS, "correlateImmediate", "yes");

            // Sid ACE-3332 Check parameters are added
            Optional<Node> paramDesc =
                    findChildElement(receiveTaskScope.get(), "tibex:parameters/parameterDescription");
            assertTrue("Missing tibex:parameters/parameterDescription", paramDesc.isPresent());
            assertAttrValue(paramDesc.get(), "name", "login");
            assertAttrValue(paramDesc.get(), "mode", "In");
            assertAttrValue(paramDesc.get(), "mandatory", "yes");

            /* Check correlations - explicit CorrelationField1 and 3 */
            checkCorrelations(receive, new String[] { "CorrelationField", "CorrelationField3" });
        }
    }

    /**
     * @param document
     */
    private void checkTaskBoundaryCatchEventBpel(Document document) {
        NodeList scopeElems = document.getElementsByTagNameNS(BPWS_NS, "scope");
        assertTrue("Element scope is missing.", scopeElems.getLength() > 0);
        Optional<Node> receiveOuterScope =
                findFirstElement(scopeElems, node -> hasAttrValue(node, "name", "_BX_scope_ScriptTask"));
        assertTrue("Missing outer scope for script task", receiveOuterScope.isPresent());
        // Check attrs. have the expected values
        assertAttrNsValue(receiveOuterScope.get(), TIBEX_NS, "migrationAllowed", "yes");

        Optional<Node> eventHandlers = findFirstElement(receiveOuterScope.get().getChildNodes(),
                node -> "eventHandlers".equals(node.getLocalName()));
        Optional<Node> onEvent =
                findFirstElement(eventHandlers.get().getChildNodes(), node -> "onEvent".equals(node.getLocalName()));
        assertTrue("Missing onEvent for script task", onEvent.isPresent());
        assertAttrNsValue(onEvent.get(), TIBEX_NS, "cancel", "yes");
        assertAttrNsValue(onEvent.get(), TIBEX_NS, "xpdlId", "_MO0-UK4oEemYb8w_p5NPDw");
		assertNull(onEvent.get().getAttributes().getNamedItem("messageTimeout"));
        // ACE-2388 check if correlateImmediate is set
        assertAttrNsValue(onEvent.get(), TIBEX_NS, "correlateImmediate", "yes");

        Optional<Node> assign = findChildElement(onEvent.get(), "bpws:scope/bpws:assign");
        assertTrue("Missing bpws:assign", assign.isPresent());
        assertAttrNsValue(assign.get(), TIBEX_NS, "type", "boundaryMessageEvent");
        assertAttrValue(assign.get(), "name", "IntermediateEvent");

        // Sid ACE-3332 Check parameters are added
        Optional<Node> paramDesc = findChildElement(assign.get(), "tibex:parameters/parameterDescription");
        assertTrue("Missing tibex:parameters/parameterDescription", paramDesc.isPresent());
        assertAttrValue(paramDesc.get(), "name", "login");
        assertAttrValue(paramDesc.get(), "mode", "In");
        assertAttrValue(paramDesc.get(), "mandatory", "yes");

        // Checking _BX_fanVariable
        Optional<Node> copyFrom = findChildElement(assign.get(), "bpws:copy/bpws:from");
        assertTrue("Missing copy/from elem.", copyFrom.isPresent());
        assertEquals("copy/from content invalid", "1", copyFrom.get().getTextContent());
        Optional<Node> copyTo = findChildElement(assign.get(), "bpws:copy/bpws:to");
        assertTrue("Missing copy/to elem.", copyTo.isPresent());
        assertAttrValue(copyTo.get(), "variable", "_BX_fanVariable");

        // Checking declaration of _BX_fanVariable
        NodeList vaiablesElems = document.getElementsByTagNameNS(BPWS_NS, "variables");
        assertTrue("Element variables is missing.", vaiablesElems.getLength() > 0);
        Optional<Node> fanVariable = findFirstElement(vaiablesElems.item(0).getChildNodes(),
                node -> "bpws:variable".equals(node.getNodeName()) && hasAttrValue(node, "name", "_BX_fanVariable"));
        assertTrue("Missing fanVariable element", fanVariable.isPresent());
        assertAttrValue(fanVariable.get(), "type", "xsd:int");
        Optional<Node> literal = findChildElement(fanVariable.get(), "bpws:from/bpws:literal");
        assertTrue("Missing bpws:from/bpws:literal of fan variable.", literal.isPresent());
        assertEquals("bpws:from/bpws:literal of fan variable content invalid", "0", literal.get().getTextContent());

        /* Check correlations - implicit all */
        checkCorrelations(onEvent,
                new String[] { "CorrelationField", "CorrelationField2", "CorrelationField3", "CorrelationField4" });
    }

    /**
     * Check incoming requests in process with no correlation data defined.
     * 
     * @param document
     */
    private void checkIncomingRequestProcessNoCorrelation(Document document) {
        Optional<Node> flow = findChildElement(document, "bpws:process/bpws:flow");

        // Check inflow event
        {
            Optional<Node> intermediateEvent2 = findFirstElement(flow.get().getChildNodes(),
                    node -> hasAttrValue(node, "name", "IntermediateEvent2"));
            assertTrue("Missing IntermediateEvent2", intermediateEvent2.isPresent());

            Optional<Node> receive = findChildElement(intermediateEvent2.get(), "bpws:sequence/bpws:receive");
            assertTrue("Receive is missing.", receive.isPresent());

            checkCorrelations(receive, null);
        }

        {
            // Check inflow receive task
            Optional<Node> receiveTask =
                    findFirstElement(flow.get().getChildNodes(), node -> hasAttrValue(node, "name", "ReceiveTask"));
            assertTrue("Missing ReceiveTask", receiveTask.isPresent());

            Optional<Node> receive = findChildElement(receiveTask.get(), "bpws:sequence/bpws:receive");
            assertTrue("Receive is missing.", receive.isPresent());

            checkCorrelations(receive, null);
        }

        Optional<Node> eventHandlers = findChildElement(document, "bpws:process/bpws:eventHandlers");

        {
            // Check event handler
            Optional<Node> eventHandler = findFirstElement(eventHandlers.get().getChildNodes(),
                    node -> hasAttrValue(node, "tibex:xpdlId", "_Dly1sKl3Eemo6Y0qdhxLpw"));
            assertTrue("Missing wrapper for Intermediate event 3 (event handler)", eventHandler.isPresent());

            checkCorrelations(eventHandler, null);
        }
        {
            // Check event sub-process
            Optional<Node> eventSubProcess = findFirstElement(eventHandlers.get().getChildNodes(),
                    node -> hasAttrValue(node, "tibex:xpdlId", "_PXbQsKl3Eemo6Y0qdhxLpw"));
            assertTrue("Missing wrapper for Start event 2 (event sub-process)", eventSubProcess.isPresent());

            checkCorrelations(eventSubProcess, null);
        }
    }

    /**
     * Check that the given node has the given bpws:correlations entries
     * 
     * @param parentElement
     * @param correlationFields
     */
    private void checkCorrelations(Optional<Node> parentElement, String[] correlationFields) {
        Optional<Node> correlations = findChildElement(parentElement.get(), "bpws:correlations");
        if (correlationFields == null) {
            assertFalse("Node should not have correlations but has.", correlations.isPresent());
            return;
        }

        assertTrue("Missing bpws:correlations in incoming request activity", correlations.isPresent());

        for (String correlationField : correlationFields) {
            checkCorrelation(correlations, correlationField);
        }

    }

    /**
     * Check that the given bpws:correlations list has an entry for the given correlation field.
     * 
     * @param correlations
     * @param correlationField
     */
    private void checkCorrelation(Optional<Node> correlations, String correlationField) {
        Optional<Node> correlation = findFirstElement(correlations.get().getChildNodes(),
                node -> hasAttrNsValue(node, null, "set", correlationField));

        assertTrue("Correlations should in include field: " + correlationField, correlation.isPresent());

        assertAttrValue(correlation.get(), "initiate", "no");
    }

}
