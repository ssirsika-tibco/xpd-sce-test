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
 * Test additional bpws:process attributes added for the dataFieldDescriptor JS file path and process-specific class
 * name.
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
        return new String[] { "resources/BpelTransform/DataFieldDescriptorTest/DataFieldDescriptorData/",
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
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProcessDescriptors()
     */
    @Override
    protected Collection<String> getTestProcessDescriptors() {
        return Arrays.asList("DataFieldDescriptor.xpdl/MainProcess");
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#checkBpelContent(Map)
     *
     * @param document
     */
    @Override
    protected void checkBpelContent(Map<String, Document> documentMap) {
        Document document = documentMap.get("DataFieldDescriptor.xpdl/MainProcess");
        // Check REST task shared resources
        NodeList processElems = document.getElementsByTagNameNS(BPWS_NS, "process");
        assertEquals("One process element.", 1, processElems.getLength());
        Node processElem = processElems.item(0);

        /*
         * The following should appear in the bpws:process element to identify the data field descriptor script for the
         * process. tibex:dataFieldDescriptorScript=
         * "/process-js/<process-package-name>/<process-name>/<process-name>.js"
         * 
         * tibex:dataFieldDescriptorClass= "<application-id>.<process-package-name>.<process-name>"
         */

        assertAttr(processElem,
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorScript",
                Optional.of("process-js/Datafielddescriptor/MainProcess/MainProcess.js"));

        assertAttr(processElem,
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorClass",
                Optional.of("com.tibco.ace.datafielddescriptor.test.Datafielddescriptor.MainProcess"));

        /*
         * Sid ACE-2936 Check additional activity scoped data field descriptors.
         */

        /* EVENT SUB-PROCESS with Activity scope data. */
        Optional<Node> eventSubProcScopeElement = findFirstElement(document.getElementsByTagNameNS(BPWS_NS, "scope"),
                node -> node.getAttributes().getNamedItem("name") != null && "_BX_scope_EventSubProcess"
                        .equals(node.getAttributes().getNamedItem("name").getNodeValue()));

        assertTrue("Can't find bpws:scope for the event sub-process", eventSubProcScopeElement.isPresent());

        assertAttr(eventSubProcScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorScript",
                Optional.of("process-js/Datafielddescriptor/MainProcess/EventSubProcess/EventSubProcess.js"));

        assertAttr(eventSubProcScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorClass",
                Optional.of("com.tibco.ace.datafielddescriptor.test.Datafielddescriptor.MainProcess.EventSubProcess"));

        /*
         * EMAIL TASK with activity scope data
         */
        Optional<Node> emailTaskScopeElement = findFirstElement(document.getElementsByTagNameNS(BPWS_NS, "scope"),
                node -> node.getAttributes().getNamedItem("name") != null && "_BX_scope_EmailServiceTask"
                        .equals(node.getAttributes().getNamedItem("name").getNodeValue()));

        assertTrue("Can't find bpws:scope for the email task", emailTaskScopeElement.isPresent());

        assertAttr(emailTaskScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorScript",
                Optional.of("process-js/Datafielddescriptor/MainProcess/EmailServiceTask/EmailServiceTask.js"));

        assertAttr(emailTaskScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorClass",
                Optional.of("com.tibco.ace.datafielddescriptor.test.Datafielddescriptor.MainProcess.EmailServiceTask"));

        /*
         * Embedded sub-process with activity scope data WITH catch boundary event
         * 
         * Need to be careful here that we are testing the correct data field descriptor BECAUSE hen you have a boundary
         * event on the embedded sub-process the bpws:variables for the local data fields get defined ON the scope that
         * surrounds the emb' sub-process and event AND ON the scope for the embedded sub-process. AFAIK this means that
         * we need to duplicate the dataFieldDescriptors on oth these scopes also.
         * 
         * The outer scope has the same name as inner scope BUT has tibex:migrationAllowed="yes" (inner scope doesn't)
         * 
         * The inner scope has tibx:xpdlId (outer one doesn't)
         * 
         * So deal with the outer scope first...
         */
        Optional<Node> embeddedSubProcWithBoundaryEventOuterScopeElement = findFirstElement(
                document.getElementsByTagNameNS(BPWS_NS, "scope"),
                node -> (node.getAttributes().getNamedItem("name") != null && "_BX_scope_EmbeddedSubProcess"
                        .equals(node.getAttributes().getNamedItem("name").getNodeValue()))
                        && (node.getAttributes().getNamedItem("tibex:migrationAllowed") != null && "yes"
                                .equals(node.getAttributes().getNamedItem("tibex:migrationAllowed").getNodeValue())));

        assertTrue("Can't find bpws:scope for the embedded sub-process with boundary event (outer scope)",
                embeddedSubProcWithBoundaryEventOuterScopeElement.isPresent());

        assertAttr(embeddedSubProcWithBoundaryEventOuterScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorScript",
                Optional.of("process-js/Datafielddescriptor/MainProcess/EmbeddedSubProcess/EmbeddedSubProcess.js"));

        assertAttr(embeddedSubProcWithBoundaryEventOuterScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorClass",
                Optional.of(
                        "com.tibco.ace.datafielddescriptor.test.Datafielddescriptor.MainProcess.EmbeddedSubProcess"));

        /*
         * Now deal with the outer scope first...
         */
        Optional<Node> embeddedSubProcWithBoundaryEventInnerScopeElement = findFirstElement(
                document.getElementsByTagNameNS(BPWS_NS, "scope"),
                node -> (node.getAttributes().getNamedItem("name") != null && "_BX_scope_EmbeddedSubProcess"
                        .equals(node.getAttributes().getNamedItem("name").getNodeValue()))
                        && (node.getAttributes().getNamedItem("tibex:xpdlId") != null && "_eeEKcNlgEemuBvSyFa0Btw"
                                .equals(node.getAttributes().getNamedItem("tibex:xpdlId").getNodeValue())));

        assertTrue("Can't find bpws:scope for the embedded sub-process with boundary event (inner scope)",
                embeddedSubProcWithBoundaryEventInnerScopeElement.isPresent());

        assertAttr(embeddedSubProcWithBoundaryEventInnerScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorScript",
                Optional.of("process-js/Datafielddescriptor/MainProcess/EmbeddedSubProcess/EmbeddedSubProcess.js"));

        assertAttr(embeddedSubProcWithBoundaryEventInnerScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorClass",
                Optional.of(
                        "com.tibco.ace.datafielddescriptor.test.Datafielddescriptor.MainProcess.EmbeddedSubProcess"));

        /*
         * STANDARD LOOP Embedded sub-process with activity scope data
         * 
         * In The model this isn't really any different than a normal embedded sub-process BUT it goes thru a different
         * part of xpdl2bpel, hence it's inclusion as a test case here.
         */
        Optional<Node> standardLoopScopeElement = findFirstElement(document.getElementsByTagNameNS(BPWS_NS, "scope"),
                node -> node.getAttributes().getNamedItem("name") != null
                        && "_BX_scope_StandardLoop".equals(node.getAttributes().getNamedItem("name").getNodeValue()));

        assertTrue("Can't find bpws:scope for the standard loop embedded sub-process",
                standardLoopScopeElement.isPresent());

        assertAttr(standardLoopScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorScript",
                Optional.of("process-js/Datafielddescriptor/MainProcess/StandardLoop/StandardLoop.js"));

        assertAttr(standardLoopScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorClass",
                Optional.of("com.tibco.ace.datafielddescriptor.test.Datafielddescriptor.MainProcess.StandardLoop"));

        /*
         * MULTI-INSTANCE LOOP Embedded sub-process with activity scope data
         * 
         * In The model this isn't really any different than a normal embedded sub-process BUT it goes thru a different
         * part of xpdl2bpel, hence it's inclusion as a test case here.
         */
        Optional<Node> multiInstScopeElement = findFirstElement(document.getElementsByTagNameNS(BPWS_NS, "scope"),
                node -> node.getAttributes().getNamedItem("name") != null
                        && "_BX_scopeMI_MultiinstanceLoop"
                                .equals(node.getAttributes().getNamedItem("name").getNodeValue()));

        assertTrue("Can't find bpws:scope for the standard loop embedded sub-process",
                multiInstScopeElement.isPresent());

        assertAttr(multiInstScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorScript",
                Optional.of("process-js/Datafielddescriptor/MainProcess/MultiinstanceLoop/MultiinstanceLoop.js"));

        assertAttr(multiInstScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorClass",
                Optional.of(
                        "com.tibco.ace.datafielddescriptor.test.Datafielddescriptor.MainProcess.MultiinstanceLoop"));

        /*
         * Nested Embedded sub-process with activity scope data
         */
        Optional<Node> nestedEmbeddedScopeElement = findFirstElement(document.getElementsByTagNameNS(BPWS_NS, "scope"),
                node -> node.getAttributes().getNamedItem("name") != null && "_BX_scope_NestedEmbeddedSubProcess"
                        .equals(node.getAttributes().getNamedItem("name").getNodeValue()));

        assertTrue("Can't find bpws:scope for the nested embedded sub-process", nestedEmbeddedScopeElement.isPresent());

        assertAttr(nestedEmbeddedScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorScript",
                Optional.of(
                        "process-js/Datafielddescriptor/MainProcess/NestedEmbeddedSubProcess/NestedEmbeddedSubProcess.js"));

        assertAttr(nestedEmbeddedScopeElement.get(),
                Optional.of(TIBEX_NS),
                "dataFieldDescriptorClass",
                Optional.of(
                        "com.tibco.ace.datafielddescriptor.test.Datafielddescriptor.MainProcess.NestedEmbeddedSubProcess"));

    }
}
