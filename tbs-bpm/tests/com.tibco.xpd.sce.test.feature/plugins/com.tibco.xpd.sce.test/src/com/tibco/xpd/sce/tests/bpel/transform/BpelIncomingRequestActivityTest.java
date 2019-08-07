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
 * Test incoming request activities in BPEL Model in converted from xpdl.
 */
@SuppressWarnings("nls")
public class BpelIncomingRequestActivityTest extends AbstractBpelTransformTest {

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getProjectLocations()
     *
     * @return
     */
    @Override
    protected String[] getProjectLocations() {
        return new String[] { "resources/AceIncomingRequestActivityTest/IncomingRequestAct/" };
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
                "TaskBoundaryCatchEvent.xpdl/TaskBoundaryCatchEventProcess");
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

            // check if timeout is set
            assertAttrNsValue(onMessage.get(), TIBEX_NS, "messageTimeout", "3600");

            // check onMessage has the assign for specific xpdl gateway element
            Optional<Node> assign = findFirstElement(onMessage.get().getChildNodes(),
                    node -> hasAttrNsValue(node, TIBEX_NS, "xpdlId", "_OJp1MbNrEemyOeCi73RnLA"));
            assertTrue("Missing assigne of the onMessage of the pick", assign.isPresent());
            // check the tibex type of the assignment
            assertAttrNsValue(assign.get(), TIBEX_NS, "type", "catchMessageIntermediateEvent");
        }
        // Check second pick with atrr name="_BX_pick_2"
        {
            Optional<Node> pick = findFirstElement(pickElems, node -> hasAttrValue(node, "name", "_BX_pick_2"));
            assertTrue("Missing pick", pick.isPresent());

            Optional<Node> onMessage =
                    findFirstElement(pick.get().getChildNodes(), node -> "onMessage".equals(node.getLocalName()));
            assertTrue("Missing onMessage of the pick", onMessage.isPresent());

            // check if timeout is set
            assertAttrNsValue(onMessage.get(), TIBEX_NS, "messageTimeout", "3600");

            // check onMessage has the assign for specific xpdl gateway element
            Optional<Node> assign = findFirstElement(onMessage.get().getChildNodes(),
                    node -> hasAttrNsValue(node, TIBEX_NS, "xpdlId", "_ThL6krNrEemyOeCi73RnLA"));
            assertTrue("Missing assigne of the onMessage of the pick", assign.isPresent());
            // check the tibex type of the assignment
            assertAttrNsValue(assign.get(), TIBEX_NS, "type", "receiveTask");
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
        assertAttrNsValue(onEventElem, TIBEX_NS, "messageTimeout", "3600");
        assertAttrNsValue(onEventElem, TIBEX_NS, "blockUntilCompleted", "yes");
        assertAttrNsValue(onEventElem, TIBEX_NS, "xpdlId", "_fB8G0K1eEemheL-rDtMNiw");

        // Check onEvent has a scope and a the flow in it.
        Optional<Node> flow = findChildElement(onEventElem, "bpws:scope/bpws:flow");
        assertTrue("Flow is missing.", flow.isPresent());
        assertAttrNsValue(flow.get(), TIBEX_NS, "type", "eventSubProcess");

        Optional<Node> assign = findFirstElement(flow.get().getChildNodes(),
                node -> "assign".equals(node.getLocalName())
                        && hasAttrNsValue(node, TIBEX_NS, "xpdlId", "_fB8G0K1eEemheL-rDtMNiw"));
        assertTrue("Element assign is missing.", assign.isPresent());
        assertAttrNsValue(assign.get(), TIBEX_NS, "type", "messageStartEvent");

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
        assertAttrNsValue(onEventElem, TIBEX_NS, "messageTimeout", "3600");
        assertAttrNsValue(onEventElem, TIBEX_NS, "blockUntilCompleted", "yes");
        assertAttrNsValue(onEventElem, TIBEX_NS, "xpdlId", "_T9JOkK1cEemheL-rDtMNiw");

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
    }

    /**
     * @param document
     */
    private void checkInterInFlowEventBpel(Document document) {
        // Check onEvent
        NodeList scopeElems = document.getElementsByTagNameNS(BPWS_NS, "scope");
        assertEquals("Element scope is missing.", 1, scopeElems.getLength());
        Node scopeElem = scopeElems.item(0);
        // Check attrs. have the expected values
        assertAttrValue(scopeElem, "name", "IntermediateEvent");
        assertAttrNsValue(scopeElem, TIBEX_NS, "migrationAllowed", "yes");
        assertAttrNsValue(scopeElem, TIBEX_NS, "type", "catchMessageIntermediateEvent");
        assertAttrNsValue(scopeElem, TIBEX_NS, "xpdlId", "_rWYJYK1dEemheL-rDtMNiw");

        // check completed scripts
        Optional<Node> completedScript =
                findFirstElement(scopeElem.getChildNodes(), node -> "tibex:completedScript".equals(node.getNodeName()));
        assertTrue("Complete sctipt is missing.", completedScript.isPresent());
        assertAttrValue(completedScript.get(), "expressionLanguage", "urn:tibco:wsbpel:2.0:sublang:javascript");
        assertEquals("Script content invalid", "var x = \"aaaa\";", completedScript.get().getTextContent());

        // Check onEvent has a scope and a the flow in it.
        Optional<Node> receive = findChildElement(scopeElem, "bpws:sequence/bpws:receive");
        assertTrue("Receive is missing.", receive.isPresent());
        assertAttrNsValue(receive.get(), TIBEX_NS, "messageTimeout", "3600");
        assertAttrValue(receive.get(), "createInstance", "no");

        Optional<Node> assign = findChildElement(scopeElem, "bpws:sequence/bpws:assign");
        assertTrue("Element assign is missing.", assign.isPresent());
    }

    /**
     * @param document
     */
    private void checkReceiveTaskBpel(Document document) {
        NodeList scopeElems = document.getElementsByTagNameNS(BPWS_NS, "scope");
        assertTrue("Element scope is missing.", scopeElems.getLength() > 0);
        Optional<Node> receiveOuterScope =
                findFirstElement(scopeElems, node -> hasAttrValue(node, "name", "_BX_scope_ReceiveTask"));
        assertTrue("Missing outer scope for receive task", receiveOuterScope.isPresent());
        assertAttrNsValue(receiveOuterScope.get(), TIBEX_NS, "migrationAllowed", "yes");

        Optional<Node> receiveTaskScope = findFirstElement(receiveOuterScope.get().getChildNodes(),
                node -> "scope".equals(node.getLocalName()));
        assertTrue("Missing scope for receive task", receiveTaskScope.isPresent());
        // Check scripts
        Optional<Node> completedScript = findChildElement(receiveTaskScope.get(), "tibex:completedScript");
        assertTrue("Complete sctipt is missing.", completedScript.isPresent());
        assertAttrValue(completedScript.get(), "expressionLanguage", "urn:tibco:wsbpel:2.0:sublang:javascript");
        assertEquals("Script content invalid", "var x = \"aaaaa\";", completedScript.get().getTextContent());

        Optional<Node> initiatedScript = findChildElement(receiveTaskScope.get(), "tibex:initiatedScript");  
        assertTrue("Initiated sctipt is missing.", initiatedScript.isPresent());
        assertAttrValue(initiatedScript.get(), "expressionLanguage", "urn:tibco:wsbpel:2.0:sublang:javascript");
        assertEquals("Script content invalid", "var x = \"initiate\";", initiatedScript.get().getTextContent());

        Optional<Node> cancelScript = findChildElement(receiveTaskScope.get(), "tibex:cancelScript"); 
        assertTrue("Cancel sctipt is missing.", cancelScript.isPresent());
        assertAttrValue(cancelScript.get(), "expressionLanguage", "urn:tibco:wsbpel:2.0:sublang:javascript");
        assertEquals("Script content invalid", "var x = \"cancel\";", cancelScript.get().getTextContent());

        Optional<Node> receive = findChildElement(receiveTaskScope.get(), "bpws:sequence/bpws:receive");
        assertTrue("Missing bpws:receive", receive.isPresent());
        assertAttrNsValue(receive.get(), TIBEX_NS, "messageTimeout", "3600");
        assertAttrValue(receive.get(), "createInstance", "no");

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
        assertAttrNsValue(onEvent.get(), TIBEX_NS, "messageTimeout", "3600");

        Optional<Node> assign = findChildElement(onEvent.get(), "bpws:scope/bpws:assign");
        assertTrue("Missing bpws:assign", assign.isPresent());
        assertAttrNsValue(assign.get(), TIBEX_NS, "type", "boundaryMessageEvent");
        assertAttrValue(assign.get(), "name", "IntermediateEvent");

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
        Optional<Node> fanVariable =
                findFirstElement(vaiablesElems.item(0).getChildNodes(),
                        node -> "bpws:variable".equals(node.getNodeName())
                                && hasAttrValue(node, "name", "_BX_fanVariable"));
        assertTrue("Missing fanVariable element", fanVariable.isPresent());
        assertAttrValue(fanVariable.get(), "type", "xsd:int");
        Optional<Node> literal = findChildElement(fanVariable.get(), "bpws:from/bpws:literal");
        assertTrue("Missing bpws:from/bpws:literal of fan variable.", literal.isPresent());
        assertEquals("bpws:from/bpws:literal of fan variable content invalid", "0", literal.get().getTextContent());

    }
}
