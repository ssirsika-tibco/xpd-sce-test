/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.sce.tests.bpel.transform;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.n2.bpel.builder.BPELOnDemandBuilder;
import com.tibco.xpd.resources.util.ProjectImporter;

import junit.framework.TestCase;

/**
 * Base class for BPEL tests.
 */
@SuppressWarnings("nls")
public abstract class AbstractBpelTransformTest extends TestCase {
    protected static final String BPWS_NS = "http://docs.oasis-open.org/wsbpel/2.0/process/executable";

    protected static final String TIBEX_NS = "http://www.tibco.com/bpel/2007/extensions";

    protected static final String BXST_NS = "http://www.tibco.com/bpel/2007/extensions/email";

    /**
     * Process Descriptor. An immutable descriptor of a process within a project. Typically a client will create
     * descriptor by providing xpdl file name and process name: <code>ProcessDesc.of("Test.xpdl", "TestProcess")</code>
     * and will use the {@link #getId()} to get the descriptor identifier.
     * 
     * {@link AbstractBpelTransformTest#checkBpelContent(Map)} returns map of desciptor id to
     * 
     * 
     */
    public static class ProcessDesc {
        final String xpdlName;

        final String processName;

        private ProcessDesc(String xpdlName, String processName) {
            this.xpdlName = xpdlName;
            this.processName = processName;
        }

        public static ProcessDesc of(String xpdlName, String processName) {
            return new ProcessDesc(xpdlName, processName);
        }

        public String getId() {
            return this.xpdlName + "/" + this.processName;
        }
    }

    /**
     * @return List of project locations for ProjectImporter
     */
    protected abstract String[] getProjectLocations();

    /**
     * @return The name of the project for the XPDL file under test
     */
    protected abstract String getTestProjectName();

    /**
     * Returns a collection of process descriptors for processes to be tested in a given project. Process descriptor is
     * a string in a form of: <code>"&lt;xpdlFileName&gt;/&ltprocessName&gt;"</code>, for example:
     * <code>"Test.xpdl/TestProcess"</code>.
     * <p>
     * Typically the client will specify the collection of the test process descriptors as:
     * 
     * <pre>
     * <code>return Arrays.asList("TestProcess.xpdl/TestProcessProcess", ...);</code>
     * </pre>
     * </p>
     * 
     * @return A collection of process descriptors for the BPELs to be tested.
     */
    protected abstract Collection<String> getTestProcessDescriptors();

    /**
     * Check the content of the transformed BPELs for the test processes.
     * <p>
     * Typically the client will retrieve the corresponding generated BPEL document to check using the process
     * descriptor, for example:
     * 
     * <pre>
     * <code>Document document = documentMap.get("TestProcess.xpdl/TestProcessProcess");</code>
     * </pre>
     * </p>
     * 
     * @param documentMap
     *            the map of test process descriptors to the documents (documents for the generated bpel process).
     */
    protected abstract void checkBpelContent(Map<String, Document> documentMap);

    public void testBpelTransform() throws Exception {
        String[] locations = getProjectLocations();

        ProjectImporter projectImporter = importProjects(locations);
        try {

            IProject processProject = ResourcesPlugin.getWorkspace().getRoot().getProject(getTestProjectName());

            // Run bpel builder on the process project.
            BPELOnDemandBuilder bpelBuilder = new BPELOnDemandBuilder(processProject);
            IStatus status = bpelBuilder.buildProject(new NullProgressMonitor());
            if (!status.isOK()) {
                fail(String.format("BPEL builder failed on project: %s ", processProject.getName()));
            }

            // Get expected bpel files.
            Map<String, Document> documentMap = new LinkedHashMap<>();
            for (String pd : getTestProcessDescriptors()) {
                String[] pdSplit = pd.split("/");
                assertTrue(String.format("Invalid process descriptor: %s", pd), pdSplit.length == 2);
                String xpdlName = pdSplit[0];
                String processName = pdSplit[1];
                IFile processBpelFile = processProject
                        .getFile(new Path(".processOut/process/" + xpdlName + "/" + processName + ".bpel"));
                assertTrue(String.format("Generated process bpel doesn't exist: %s", processBpelFile),
                        processBpelFile.exists());

                Document document = getXmlDocument(processBpelFile);
                documentMap.put(pd, document);

            }

            checkBpelContent(documentMap);

        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    /**
     * Assert attribute exists in the element and it has the expected value (if provided).
     * 
     * @param elementNode
     * @param attrNsUri
     *            the optional uri of the attribute namespace.
     * @param attrName
     * @param attrExpectedValue
     *            the optional attribute name (if not provided then it won't be checked)
     */
    protected void assertAttr(Node elementNode, Optional<String> attrNsUri, String attrName,
            Optional<String> attrExpectedValue) {
        String elemName = elementNode.getLocalName();
        NamedNodeMap namedNodeMap = elementNode.getAttributes();
        Node attrNode = attrNsUri.isPresent() ? namedNodeMap.getNamedItemNS(attrNsUri.get(), attrName)
                : namedNodeMap.getNamedItem(attrName);
        assertTrue(String.format("Element '%s' is missing attr '%s' (also checking NS)", elemName, attrName),
                attrNode != null);
        if (attrExpectedValue.isPresent()) { // assert value if present.
            String attrValue = attrNode.getTextContent();
            assertEquals(String.format("Element '%s' attr '%s' has unexpected value;", elemName, attrName),
                    attrExpectedValue.get(),
                    attrValue);
        }
    }

    /**
     * Asserts that 'attrName' with the value of 'attrExpectedValue' exist in the 'elementNode'. Attribute has not
     * explicit namespace.
     * 
     * @param elementNode
     *            context node element.
     * @param attrName
     *            name of the attribute.
     * @param attrExpectedValue
     *            the expected value of the attribute.s
     * 
     * @see #assertAttrNsValue(Node, String, String, String)
     */
    protected void assertAttrValue(Node elementNode, String attrName, String attrExpectedValue) {
        assertAttr(elementNode, Optional.empty(), attrName, Optional.of(attrExpectedValue));
    }

    /**
     * Asserts that 'attrName' with the value of 'attrExpectedValue' (and 'attrNsUri' namespace ) exist in the
     * 'elementNode'.
     * 
     * @param elementNode
     *            context node element.
     * @param attrNsUri
     *            namespace URI of the attribute.
     * @param attrName
     *            name of the attribute.
     * @param attrExpectedValue
     *            the expected value of the attribute.s
     * 
     * @see #assertAttrValue(Node, String, String)
     */
    protected void assertAttrNsValue(Node elementNode, String attrNsUri, String attrName, String attrExpectedValue) {
        assertAttr(elementNode, Optional.of(attrNsUri), attrName, Optional.of(attrExpectedValue));
    }

    /**
     * Finds first element that passes predicate in the node list.
     * 
     * @param nodeList
     * @param predicate
     * @return
     */
    protected Optional<Node> findFirstElement(NodeList nodeList, Predicate<Node> predicate) {
        int length = nodeList.getLength();
        for (int i = 0; i < length; i++) {
            Node item = nodeList.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE && predicate.test(item)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    /**
     * Finds elements that passes predicate in the node list.
     * 
     * @param nodeList
     * @param predicate
     * @return
     */
    protected List<Node> findElements(NodeList nodeList, Predicate<Node> predicate) {
        ArrayList<Node> result = new ArrayList<Node>();
        int length = nodeList.getLength();
        for (int i = 0; i < length; i++) {
            Node item = nodeList.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE && predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Gets optional sub-element node (the context node) for the specified path.
     * 
     * @param node
     *            the context node.
     * @param pathStr
     *            string representation of the path that can contain index (1 based), for example:
     *            "bsws:from[1]/bpws:literal" or just "bsws:from/bpws:literal".
     * @return sub-element of the context node as specified by path.
     */
    protected Optional<Node> findChildElement(Node node, String pathStr) {
        final Pattern posElemPattern = Pattern.compile("(.*?)\\[(\\d+)\\]"); // <element-name>[<element-pos>]
        class ElemPos {

            final String elem; // element name.

            final int pos; // element position.

            public ElemPos(String elemSpec) {
                Matcher matcher = posElemPattern.matcher(elemSpec);
                if (matcher.matches()) {
                    this.elem = matcher.group(1);
                    this.pos = Integer.parseInt(matcher.group(2)) - 1;
                } else {
                    this.elem = elemSpec;
                    this.pos = 0;
                }
            }
        }
        // This construct probably doesn't work in code compiled in java-1.8 and executed in java-11
        // List<ElemPos> path = Arrays.stream(pathStr.split("/")).map(s -> new ElemPos(s)).collect(Collectors.toList());
        List<ElemPos> path = new ArrayList<ElemPos>();
        for (String token : pathStr.split("/")) {
            path.add(new ElemPos(token));
        }

        Node currentNode = node;
        for (ElemPos pathElem : path) {
            List<Node> elems = findElements(currentNode.getChildNodes(), n -> pathElem.elem.equals(n.getNodeName()));
            if (pathElem.pos < elems.size()) {
                currentNode = elems.get(pathElem.pos);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(currentNode);
    }

    /**
     * Checks if the provided node has the attribute. If optional expected value is provided it will also be checked (if
     * not then the attribute value will be ignored).
     * 
     * @param elementNode
     * @param attrNsUri
     *            the optional uri of the attribute namespace.
     * @param attrName
     * @param attrExpectedValue
     *            optional expected value (if not provided then will be ignored)
     * @return <code>true</code> if element has the specified attribute (if attrExpectedValue is provided then it must
     *         also match).
     */
    protected boolean hasAttribute(Node elementNode, Optional<String> attrNsUri, String attrName,
            Optional<String> attrExpectedValue) {
        NamedNodeMap namedNodeMap = elementNode.getAttributes();
        Node attrNode = attrNsUri.isPresent() ? namedNodeMap.getNamedItemNS(attrNsUri.get(), attrName)
                : namedNodeMap.getNamedItem(attrName);
        if (attrNode == null) {
            return false;
        }

        // Check the value if the optional expected value was provided.
        if (!attrExpectedValue.isPresent()) {
            return true;
        } else {
            String attrValue = attrNode.getTextContent();
            return attrExpectedValue.get().equals(attrValue);
        }
    }

    /**
     * Check if the 'elementNode' has the attribute 'attrName' with the value of 'attrValue'
     * 
     * @param elementNode
     * @param attrName
     * @param attrValue
     * 
     * @return true if the element has the attribute with the value.
     */
    protected boolean hasAttrValue(Node elementNode, String attrName, String attrValue) {
        return hasAttribute(elementNode, Optional.empty(), attrName, Optional.of(attrValue));
    }

    /**
     * Check if the 'elementNode' has the attribute 'attrName' (of 'attrNsUri' namespace) with the value of 'attrValue'
     * 
     * @param elementNode
     * @param attrName
     * @param attrValue
     * 
     * @return true if the element has the attribute with the value.
     */
    protected boolean hasAttrNsValue(Node elementNode, String namespace, String attrName, String attrValue) {
        return hasAttribute(elementNode, Optional.of(namespace), attrName, Optional.of(attrValue));
    }

    /**
     * Parses and returns xml Document for an IFile.
     * 
     * @param iFile
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    protected Document getXmlDocument(IFile iFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        File file = new File(iFile.getLocation().toString());
        return db.parse(file);
    }

    /**
     * Import the given projects from test plug-in resources.
     */
    protected ProjectImporter importProjects(String[] projectLocations) {

        String[] projectNames =
                Arrays.stream(projectLocations).map(loc -> (new Path(loc)).lastSegment()).toArray(String[]::new);
        /*
         * Import and mgirate the project
         */
        ProjectImporter projectImporter =
                TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", projectLocations, projectNames);

        assertTrue("Failed to load projects " + projectNames, projectImporter != null);

        for (String name : projectNames) {
            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
            assertTrue(name + " project does not exist", project.isAccessible());
        }

        TestUtil.buildAndWait();
        return projectImporter;
    }

}
