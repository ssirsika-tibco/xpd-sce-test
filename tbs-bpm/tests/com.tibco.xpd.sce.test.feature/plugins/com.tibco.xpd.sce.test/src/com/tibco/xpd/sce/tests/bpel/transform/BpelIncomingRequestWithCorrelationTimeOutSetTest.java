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
 * Test business data correlation timeout for incoming request activities in BPEL Model converted from xpdl.
 *
 * @author cbabar
 * @since Feb 9, 2024
 */
@SuppressWarnings("nls")
public class BpelIncomingRequestWithCorrelationTimeOutSetTest extends AbstractBpelTransformTest
{

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getProjectLocations()
     *
     * @return
     */
    @Override
    protected String[] getProjectLocations() {
		return new String[]{"resources/AceIncomingRequestCorrelationTimeOutSet/IncomingRequestAct/"};
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

			Optional<Node> onMessage = findFirstElement(pick.get().getChildNodes(),
					node -> "onMessage".equals(node.getLocalName()));
			assertTrue("Missing onMessage of the pick", onMessage.isPresent());

			// check if timeout is set
			assertAttrNsValue(onMessage.get(), TIBEX_NS, "messageTimeout", "93670");
        }
        // Check second pick with atrr name="_BX_pick_2"
        {
			Optional<Node> pick = findFirstElement(pickElems, node -> hasAttrValue(node, "name", "_BX_pick_2"));
			assertTrue("Missing pick", pick.isPresent());

			Optional<Node> onMessage = findFirstElement(pick.get().getChildNodes(),
					node -> "onMessage".equals(node.getLocalName()));
			assertTrue("Missing onMessage of the pick", onMessage.isPresent());

			// check if timeout is set
			assertAttrNsValue(onMessage.get(), TIBEX_NS, "messageTimeout", "183911");

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
		assertAttrNsValue(onEventElem, TIBEX_NS, "messageTimeout", "13");

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
		assertAttrNsValue(onEventElem, TIBEX_NS, "messageTimeout", "172865");
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
            Optional<Node> receive = findChildElement(scopeElem, "bpws:sequence/bpws:receive");
            assertTrue("Receive is missing.", receive.isPresent());

			assertAttrNsValue(receive.get(), TIBEX_NS, "messageTimeout", "267030");
        }

        {
            Node scopeElem = scopeElems.item(1);
            Optional<Node> receive = findChildElement(scopeElem, "bpws:sequence/bpws:receive");
            assertTrue("Receive is missing.", receive.isPresent());
			assertAttrNsValue(receive.get(), TIBEX_NS, "messageTimeout", "90061");
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


            Optional<Node> receive = findChildElement(receiveTaskScope.get(), "bpws:sequence/bpws:receive");
            assertTrue("Missing bpws:receive", receive.isPresent());
			assertAttrNsValue(receive.get(), TIBEX_NS, "messageTimeout", "93662");
		}

        {
            Optional<Node> receiveTaskScope =
                    findFirstElement(scopeElems, node -> hasAttrValue(node, "name", "ReceiveTaskExplicitCorrelation"));
            assertAttrNsValue(receiveTaskScope.get(), TIBEX_NS, "xpdlId", "_boDdUk7wEe2qoreGpOJ_xw");


            Optional<Node> receive = findChildElement(receiveTaskScope.get(), "bpws:sequence/bpws:receive");
            assertTrue("Missing bpws:receive", receive.isPresent());
			assertAttrNsValue(receive.get(), TIBEX_NS, "messageTimeout", "104643");
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


        Optional<Node> eventHandlers = findFirstElement(receiveOuterScope.get().getChildNodes(),
                node -> "eventHandlers".equals(node.getLocalName()));
        Optional<Node> onEvent =
                findFirstElement(eventHandlers.get().getChildNodes(), node -> "onEvent".equals(node.getLocalName()));
        assertTrue("Missing onEvent for script task", onEvent.isPresent());

		assertAttrNsValue(onEvent.get(), TIBEX_NS, "messageTimeout", "93722");
	}

	/**
	 * Check incoming requests in process with no correlation data defined.
	 * 
	 * @param document
	 */
	private void checkIncomingRequestProcessNoCorrelation(Document document)
	{
		Optional<Node> flow = findChildElement(document, "bpws:process/bpws:flow");

		// Check inflow event
		{
			Optional<Node> intermediateEvent2 = findFirstElement(flow.get().getChildNodes(),
					node -> hasAttrValue(node, "name", "IntermediateEvent2"));
			assertTrue("Missing IntermediateEvent2", intermediateEvent2.isPresent());

			Optional<Node> receive = findChildElement(intermediateEvent2.get(), "bpws:sequence/bpws:receive");
			assertTrue("Receive is missing.", receive.isPresent());

			assertAttrNsValue(receive.get(), TIBEX_NS, "messageTimeout", "13");
		}

		{
			// Check inflow receive task
			Optional<Node> receiveTask = findFirstElement(flow.get().getChildNodes(),
					node -> hasAttrValue(node, "name", "ReceiveTask"));
			assertTrue("Missing ReceiveTask", receiveTask.isPresent());

			Optional<Node> receive = findChildElement(receiveTask.get(), "bpws:sequence/bpws:receive");
			assertTrue("Receive is missing.", receive.isPresent());

			assertAttrNsValue(receive.get(), TIBEX_NS, "messageTimeout", "14");
		}

		Optional<Node> eventHandlers = findChildElement(document, "bpws:process/bpws:eventHandlers");

		{
			// Check event handler
			Optional<Node> eventHandler = findFirstElement(eventHandlers.get().getChildNodes(),
					node -> hasAttrValue(node, "tibex:xpdlId", "_Dly1sKl3Eemo6Y0qdhxLpw"));
			assertTrue("Missing wrapper for Intermediate event 3 (event handler)", eventHandler.isPresent());

			Optional<Node> onEvent = findFirstElement(eventHandlers.get().getChildNodes(),
					node -> "onEvent".equals(node.getLocalName()));
			assertTrue("Missing onEvent for script task", onEvent.isPresent());

			assertAttrNsValue(onEvent.get(), TIBEX_NS, "messageTimeout", "94215");
		}
		{
			// Check event sub-process
			Optional<Node> eventSubProcess = findFirstElement(eventHandlers.get().getChildNodes(),
					node -> hasAttrValue(node, "tibex:xpdlId", "_PXbQsKl3Eemo6Y0qdhxLpw"));
			assertTrue("Missing wrapper for Start event 2 (event sub-process)", eventSubProcess.isPresent());

		}
	}


}
