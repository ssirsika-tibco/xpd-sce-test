/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.javascript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest;

/**
 * Test that the correct Javascript is generated for Case Data Tasks.
 *
 * @author nwilson
 * @since 29 Jul 2019
 */
public class CaseDataScriptGenerationTest extends AbstractBpelTransformTest {

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getProjectLocations()
     *
     * @return
     */
    @Override
    protected String[] getProjectLocations() {
        return new String[] { "resources/CaseDataTaskTest/CaseDataTaskTest/" }; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProjectName()
     *
     * @return
     */
    @Override
    protected String getTestProjectName() {
        return "CaseDataTaskTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestXpdlName()
     *
     * @return
     */
    @Override
    protected String getTestXpdlName() {
        return "CaseDataTaskTest.xpdl"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#getTestProcessName()
     *
     * @return
     */
    @Override
    protected String getTestProcessName() {
        return "CaseDataTaskTestProcess"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.sce.tests.bpel.transform.AbstractBpelTransformTest#checkBpelContent(org.w3c.dom.Document)
     *
     * @param document
     */
    @Override
    protected void checkBpelContent(Document document) {
        Map<String, List<String>> expectedMap = getExpectedContents();
        List<Node> tasks = getCaseDataTasks(document);
        assertEquals("Should be " + expectedMap.size() + " tasks but found " + tasks.size(), //$NON-NLS-1$ //$NON-NLS-2$
                expectedMap.size(),
                tasks.size());
        for (Node task : tasks) {
            String name = getName(task);
            String script = getScript(task);
            List<String> expectedList = expectedMap.get(name);
            assertNotNull("Unexpected task " + name, expectedList); //$NON-NLS-1$
            for (String expected : expectedList) {
                assertTrue("\"" + expected + "\" not found in \"" + script + "\"", script.contains(expected)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            }
        }
    }

    /**
     * @param task
     * @return
     */
    private String getName(Node task) {
        return task.getAttributes().getNamedItem("name").getTextContent(); //$NON-NLS-1$
    }

    private Map<String, List<String>> getExpectedContents() {
        HashMap<String, List<String>> contents = new HashMap<>();
        contents.put("DeleteByCaseRef", //$NON-NLS-1$
                Arrays.asList("bpm.process.checkIfSafeToDeleteCase(data.CaseRef)", //$NON-NLS-1$
                        "bpm.caseData.deleteByRef(data.CaseRef)")); //$NON-NLS-1$
        contents.put("UpdateByCaseRef", Arrays.asList("bpm.caseData.updateByRef(data.CaseRef,data.CaseField)")); //$NON-NLS-1$ //$NON-NLS-2$
        contents.put("Link", Arrays.asList("bpm.caseData.link(data.CaseRef,data.RefCase1,data.RefAssocCase1)")); //$NON-NLS-1$ //$NON-NLS-2$
        contents.put("LinkAll", //$NON-NLS-1$
                Arrays.asList("bpm.caseData.linkAll(data.CaseRef,data.RefArrayCase1,data.RefAssovArrayCase1)")); //$NON-NLS-1$
        contents.put("Create", //$NON-NLS-1$
                Arrays.asList("data.CaseRef = bpm.caseData.create(data.CaseField,'com.example.data.MyCase')")); //$NON-NLS-1$
        contents.put("UpdateAllByCaseRef", //$NON-NLS-1$
                Arrays.asList("bpm.caseData.updateAllByRef(data.CaseRefArray,data.CaseFieldArray)")); //$NON-NLS-1$
        contents.put("Unlink", Arrays.asList("bpm.caseData.unlink(data.CaseRef,data.RefCase1,data.RefAssocCase1)")); //$NON-NLS-1$ //$NON-NLS-2$
        contents.put("UnlinkAll", //$NON-NLS-1$
                Arrays.asList("bpm.caseData.unlinkAll(data.CaseRef,data.RefArrayCase1,data.RefAssovArrayCase1)")); //$NON-NLS-1$
        contents.put("CreateAll", //$NON-NLS-1$
                Arrays.asList(
                        "data.CaseRefArray = bpm.caseData.createAll(data.CaseFieldArray,'com.example.data.MyCase')")); //$NON-NLS-1$
        return contents;
    }

    /**
     * @param document
     * @return
     */
    private List<Node> getCaseDataTasks(Document document) {
        List<Node> tasks = new ArrayList<>();
        NodeList extActivityElems = document.getElementsByTagNameNS(TIBEX_NS, "extActivity"); //$NON-NLS-1$
        for (int i = 0; i < extActivityElems.getLength(); i++) {
            Node node = extActivityElems.item(i);
            if ("serviceTask".equals(node.getAttributes().getNamedItemNS(TIBEX_NS, "type").getTextContent())) { //$NON-NLS-1$ //$NON-NLS-2$
                tasks.add(node);
            }
        }
        return tasks;
    }

    /**
     * @param node
     * @return
     */
    private String getScript(Node node) {
        String script = null;
        NodeList elements = node.getChildNodes();
        for (int i = 0; i < elements.getLength(); i++) {
            Node child = elements.item(i);
            if (TIBEX_NS.equals(child.getNamespaceURI()) && "script".equals(child.getLocalName())) { //$NON-NLS-1$
                Node cdata = child.getFirstChild();
                script = cdata.getTextContent();
                break;
            }
        }
        return script;
    }

}
