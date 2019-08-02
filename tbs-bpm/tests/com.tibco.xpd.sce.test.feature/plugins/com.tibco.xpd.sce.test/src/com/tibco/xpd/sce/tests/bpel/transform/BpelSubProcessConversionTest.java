/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.sce.tests.bpel.transform;

import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Test XPDL2BPEL changes for sub-process input/output and catch-sub-process
 * error events
 *
 *
 * 
 * @author aallway
 * @since 31 Jul 2019
 */
@SuppressWarnings("nls")
public class BpelSubProcessConversionTest extends AbstractBpelTransformTest {
    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getProjectLocations()
     *
     * @return
     */
    @Override
    protected String[] getProjectLocations() {

        return new String[] { "resources/WrappedProcessDataTests/DataWrappingData/", //$NON-NLS-1$
                "resources/WrappedProcessDataTests/DataWrappingREST/", //$NON-NLS-1$
                "resources/WrappedProcessDataTests/DataWrappingOrganisation/", //$NON-NLS-1$
                "resources/WrappedProcessDataTests/DataWrappingMapperTests/" };
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProjectName()
     *
     * @return
     */
    @Override
    protected String getTestProjectName() {
        return "DataWrappingMapperTests";
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestXpdlName()
     *
     * @return
     */
    @Override
    protected String getTestXpdlName() {
        return "DataWrappingMappingsTests.xpdl";
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProcessName()
     *
     * @return
     */
    @Override
    protected String getTestProcessName() {
        return "DataWrappingMappingsTestsProcess";
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#checkBpelContent(org.w3c.dom.Document)
     *
     * @param document
     */
    @Override
    protected void checkBpelContent(Document document) {
        checkSubProcessCall(document);

        checkCatchSubProcessError(document);

        checkAsynchSubProcessMapFromProcessId(document);

    }

    /**
     * @param document
     */
    private void checkSubProcessCall(Document document) {
        String mainActivityName = "Directsubprocessmappings";

        Optional<Node> callSubProcScope = findFirstElement(document.getElementsByTagNameNS(BPWS_NS, "scope"),
                node -> node.getAttributes().getNamedItem("tibex:xpdlId") != null && "_SuLQwIhfEemL0JNuli1Mqw"
                        .equals(node.getAttributes().getNamedItem("tibex:xpdlId").getNodeValue()));

        assertTrue(
                "Process should have a <bpws:scope name='Directsubprocessmappings'> with tibex:xpdlid='_SuLQwIhfEemL0JNuli1Mqw'",
                callSubProcScope.isPresent());

        /*
         * For ACE For ACE the sub-process params are wrapped in a "parameters"
         * data-field-descriptor object and therefore we don't need to create
         * _BX_xxx temporary variables or map them into the payload. Run-time
         * call-sub-process handling will explicitly use the data in the
         * "parameters" object
         */

        /* Ensure there are no variables - or it is empty. */
        Optional<Node> variables = findFirstElement(callSubProcScope.get().getChildNodes(),
                node -> "variables".equals(node.getLocalName()));

        assertTrue(mainActivityName + " should not have a <bpws:variables> element (or it should be empty)",
                !variables.isPresent() || variables.get().getChildNodes().getLength() == 0);

        /*
         * Ensure there is no <tibex:subProcessInput> element (don't need to map
         * to individual temp variables anymore.
         */
        Optional<Node> callProcessExtActivity =
                findFirstElement(document.getElementsByTagNameNS(TIBEX_NS, "extActivity"),
                        node -> node.getAttributes().getNamedItem("name") != null && "Inflatesubprocessmappings"
                                .equals(node.getAttributes().getNamedItem("name").getNodeValue()));

        assertTrue(mainActivityName + " should have a <bpws:extActivity name='Inflatesubprocessmappings'>",
                callProcessExtActivity.isPresent());

        Optional<Node> callProcess = findFirstElement(callProcessExtActivity.get().getChildNodes(),
                node -> "callProcess".equals(node.getLocalName()));

        assertTrue(mainActivityName
                + " <bpws:extActivity name='Inflatesubprocessmappings'> should have a <tibex:callProcess> element",
                callProcess.isPresent());

        Optional<Node> subProcessInput = findFirstElement(callProcess.get().getChildNodes(),
                node -> "subProcessInput".equals(node.getLocalName()));

        assertTrue(mainActivityName + " <bpws:callProcess> should NOT have a <tibex:subProcessInput> element",
                !subProcessInput.isPresent());

        /*
         * Ensure there is no <tibex:subProcessOutput> element (don't need to
         * map to individual temp variables anymore.
         */
        Optional<Node> subProcessOutput = findFirstElement(callProcess.get().getChildNodes(),
                node -> "subProcessOutput".equals(node.getLocalName()));

        assertTrue(mainActivityName + " <bpws:callProcess> should NOT have a <subProcessOutput> element",
                !subProcessOutput.isPresent());
    }

    /**
     * @param document
     */
    private void checkCatchSubProcessError(Document document) {
        /*
         * For catch sub-process error we have decided to keep exactly the same
         * as today - so let's check the salient parts are still output.
         */
        String mainActivityName = "ErrorEvent";

        Optional<Node> callSubProcScope = findFirstElement(document.getElementsByTagNameNS(BPWS_NS, "scope"),
                node -> node.getAttributes().getNamedItem("name") != null && "_BX_scope_Directsubprocessmappings"
                        .equals(node.getAttributes().getNamedItem("name").getNodeValue()));

        assertTrue(
                "Process should have a <bpws:scope name='_BX_scope_Directsubprocessmappings'>",
                callSubProcScope.isPresent());


        /* Ensure still has <bpws:faultHandlers> */
        Optional<Node> faultHandlers = findFirstElement(callSubProcScope.get().getChildNodes(),
                node -> "faultHandlers".equals(node.getLocalName()));

        assertTrue(mainActivityName + " should have a <bpws:faultHandlers> element",
                faultHandlers.isPresent());

        /* Ensure still has a <bpws:catch> */
        Optional<Node> catchEl =
                findFirstElement(faultHandlers.get().getChildNodes(), node -> "catch".equals(node.getLocalName()));

        assertTrue(mainActivityName + " should have a <bpws:catch> element",
                catchEl.isPresent());

        /* Ensure still has a <bpws:scope> */
        Optional<Node> scope =
                findFirstElement(catchEl.get().getChildNodes(), node -> "scope".equals(node.getLocalName()));

        assertTrue(mainActivityName + " should have a <bpws:scope> element",
                scope.isPresent());

        /* Ensure still has a <bpws:variables> */
        Optional<Node> variables =
                findFirstElement(scope.get().getChildNodes(), node -> "variables".equals(node.getLocalName()));

        assertTrue(mainActivityName + " should have a <bpws:variables> element", variables.isPresent());

        /*
         * Ensure still has variables declared with _BX_ for parameters
         * (checking one should suffice).
         */
        boolean variableFound = false;

        for (int i = 0; i < variables.get().getChildNodes().getLength(); i++) {
            Node node = variables.get().getChildNodes().item(i);

            if ("variable".equals(node.getLocalName())) {
                Node namedItem = node.getAttributes().getNamedItem("name");
                if (namedItem != null) {
                    if ("_BX_TextParameter".equals(namedItem.getNodeValue())) {
                        variableFound = true;
                        break;
                    }
                }
            }
        }

        assertTrue(mainActivityName + " should have a <bpws:variable name='_BX_TextParameter> element", variableFound);

        /*
         * check assign task <bpws:copy> elements (just one will do.
         */

        /* Ensure still has a <bpws:sequence> */
        Optional<Node> sequence =
                findFirstElement(scope.get().getChildNodes(), node -> "sequence".equals(node.getLocalName()));

        assertTrue(mainActivityName + " should have a <bpws:sequence> element", sequence.isPresent());

        /* Ensure still has a <bpws:assign> */
        Optional<Node> assign =
                findFirstElement(sequence.get().getChildNodes(), node -> "assign".equals(node.getLocalName()));

        assertTrue(mainActivityName + " should have a <bpws:assign> element", assign.isPresent());

        /* And assign assigns to _BX_TextParameter from TextParameter. */
        boolean copyFound = false;

        for (int i = 0; i < assign.get().getChildNodes().getLength(); i++) {
            Node node = assign.get().getChildNodes().item(i);

            if ("copy".equals(node.getLocalName())) {
                boolean fromFound = false;
                boolean toFound = false;

                for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                    Node node2 = node.getChildNodes().item(j);

                    if ("to".equals(node2.getLocalName())) {
                        Node namedItem = node2.getAttributes().getNamedItem("variable");
                        if (namedItem != null) {
                            if ("_BX_TextParameter".equals(namedItem.getNodeValue())) {
                                toFound = true;
                            }
                        }
                    }

                    if ("from".equals(node2.getLocalName())) {
                        Node namedItem = node2.getAttributes().getNamedItem("part");
                        if (namedItem != null) {
                            if ("TextParameter".equals(namedItem.getNodeValue())) {
                                fromFound = true;
                            }
                        }
                    }
                }

                if (fromFound && toFound) {
                    copyFound = true;
                    break;
                }
            }
        }

        assertTrue(mainActivityName + " should have a <bpws:copy> element with from=TextParameter to=_BX_TextParameter",
                copyFound);

    }

    /**
     * @param document
     */
    private void checkAsynchSubProcessMapFromProcessId(Document document) {
        String mainActivityName = "AsynchSubProcCall";

        Optional<Node> callSubProcScope = findFirstElement(document.getElementsByTagNameNS(BPWS_NS, "scope"),
                node -> node.getAttributes().getNamedItem("tibex:xpdlId") != null && "_HjhHkLVAEemNVcjt_4R9hQ"
                        .equals(node.getAttributes().getNamedItem("tibex:xpdlId").getNodeValue()));

        assertTrue(
                "Process should have a <bpws:scope name='AsynchSubProcCall'> with tibex:xpdlid='_HjhHkLVAEemNVcjt_4R9hQ'",
                callSubProcScope.isPresent());

        /*
         * For ACE when doing asynch sub-process output mapping from the special
         * __PROCESS_ID__ process instance we revert to the old
         * _BX___PROCESS_ID__ temp variable method.
         */

        /* Ensure thas <bpws:variables> */
        Optional<Node> variables = findFirstElement(callSubProcScope.get().getChildNodes(),
                node -> "variables".equals(node.getLocalName()));

        assertTrue(mainActivityName + " should have a <bpws:variables> element", variables.isPresent());

        assertTrue(mainActivityName + " should have a <bpws:variables>  with only one <bpws:variable> element",
                countElementNodes(variables.get().getChildNodes()) == 1);

        /* There should be a <bpws:variable name="_BS___PROCESS_ID__" */

        Optional<Node> processIdVariable = findFirstElement(variables.get().getChildNodes(),
                node -> "variable".equals(node.getLocalName()) && node.getAttributes().getNamedItem("name") != null
                        && node.getAttributes().getNamedItem("name").getNodeValue().equals("_BX___PROCESS_ID__"));

        assertTrue(mainActivityName + " should be a <bpws:variable name=\"_BX___PROCESS_ID__\" element",
                processIdVariable.isPresent());

        /* Ensure still has a <bpws:sequence> */
        Optional<Node> sequence = findFirstElement(callSubProcScope.get().getChildNodes(),
                node -> "sequence".equals(node.getLocalName()));

        assertTrue(mainActivityName + " should have a <bpws:sequence> element", sequence.isPresent());

        /* Ensure there is a <tibex:extActivity> for the callProcess */
        Optional<Node> callProcessExtActivity =
                findFirstElement(document.getElementsByTagNameNS(TIBEX_NS, "extActivity"),
                        node -> node.getAttributes().getNamedItem("name") != null && "AsynchSubProcCall"
                        .equals(node.getAttributes().getNamedItem("name").getNodeValue()));

        assertTrue(mainActivityName + " should have a <tibex:extActivity name='AsynchSubProcCall'>",
                callProcessExtActivity.isPresent());

        /* Should have a <tibex:callProcess> */
        Optional<Node> callProcess = findFirstElement(callProcessExtActivity.get().getChildNodes(),
                node -> "callProcess".equals(node.getLocalName()));

        assertTrue(mainActivityName
                + " <tibex:extActivity name='AsynchSubProcCall'> should have a <tibex:callProcess> element",
                callProcess.isPresent());

        /*
         * There should not be a <tibex:subProcessInput>
         */
        Optional<Node> subProcessInput = findFirstElement(callProcess.get().getChildNodes(),
                node -> "subProcessInput".equals(node.getLocalName()));

        assertTrue(mainActivityName + " <bpws:callProcess> should NOT have a <tibex:subProcessInput> element",
                !subProcessInput.isPresent());

        /*
         * There should be a single <tibex:subProcessOutput>
         */
        Optional<Node> subProcessOutput = findFirstElement(callProcess.get().getChildNodes(),
                node -> "subProcessOutput".equals(node.getLocalName()));

        assertTrue(mainActivityName + " <bpws:callProcess> should have single <tibex:subProcessOutput> element",
                subProcessOutput.isPresent() && countElementNodes(subProcessOutput.get().getChildNodes()) == 1);

        /*
         * There should be a single <tibex:subProcessOutput>
         */
        Optional<Node> mapping =
                findFirstElement(subProcessOutput.get().getChildNodes(), node -> "mapping".equals(node.getLocalName()));

        assertTrue(mainActivityName + " <bpws:callProcess> should have single <tibex:mapping> element",
                mapping.isPresent() && countElementNodes(subProcessOutput.get().getChildNodes()) == 1);

        /* There should be field and formalparameter attributes set correctly. */
        assertTrue(mainActivityName + " <tibex:mapping> should have field='_BX___PROCESS_ID__'",
                mapping.get().getAttributes() != null && mapping.get().getAttributes().getNamedItem("field") != null
                        && "_BX___PROCESS_ID__"
                                .equals(mapping.get().getAttributes().getNamedItem("field").getNodeValue()));

        assertTrue(mainActivityName + " <tibex:mapping> should have formalParameter='__PROCESS_ID__'",
                mapping.get().getAttributes() != null
                        && mapping.get().getAttributes().getNamedItem("formalParameter") != null
                        && "__PROCESS_ID__"
                                .equals(mapping.get().getAttributes().getNamedItem("formalParameter").getNodeValue()));
        
        
        
        /* ============== */

        /* Ensure there is a <tibex:extActivity> for the mapping script */
        Optional<Node> scriptExtActivity = findFirstElement(document.getElementsByTagNameNS(TIBEX_NS, "extActivity"),
                node -> node.getAttributes().getNamedItem("name") != null && "_BX_SPOutput_AsynchSubProcCall"
                        .equals(node.getAttributes().getNamedItem("name").getNodeValue()));

        assertTrue(mainActivityName + " should have a <tibex:extActivity name='_BX_SPOutput_AsynchSubProcCall'>",
                scriptExtActivity.isPresent());

        /* Ensure there is a <tibex:script> */
        Optional<Node> mappingScript =
                findFirstElement(scriptExtActivity.get().getChildNodes(), node -> "script".equals(node.getLocalName()));

        assertTrue(
                mainActivityName
                        + " <tibex:extActivity name='_BX_SPOutput_AsynchSubProcCall'> should have a <tibex:script>",
                mappingScript.isPresent());

        /* And that it has appropriate process id mapping. */
        assertTrue(mainActivityName + " <tibex:script> should map from _BX___PROCESS_ID__ correctly",
                mappingScript.get().getChildNodes().getLength() > 0 && mappingScript.get().getChildNodes().item(0)
                        .toString().contains("data.SubProcInstanceIdField = _BX___PROCESS_ID_"));

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
}
