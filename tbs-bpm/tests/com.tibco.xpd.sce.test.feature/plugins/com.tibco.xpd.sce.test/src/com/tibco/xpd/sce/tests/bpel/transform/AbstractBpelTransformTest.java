/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.sce.tests.bpel.transform;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

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
    protected static final String BPWS_NS =
            "http://docs.oasis-open.org/wsbpel/2.0/process/executable";

    protected static final String TIBEX_NS =
            "http://www.tibco.com/bpel/2007/extensions";

    protected static final String BXST_NS =
            "http://www.tibco.com/bpel/2007/extensions/email";

    /**
     * @return List of project locations for ProjectImporter
     */
    protected abstract String[] getProjectLocations();

    /**
     * @return The name of the project for the XPDL file under test
     */
    protected abstract String getTestProjectName();

    /**
     * @return The name of the XPDL file under test
     */
    protected abstract String getTestXpdlName();

    /**
     * @return The name of the process to test
     */
    protected abstract String getTestProcessName();

    /**
     * Check the content of the transformed BPEL for the test process.
     * 
     * @param document
     */
    protected abstract void checkBpelContent(Document document);

    public void testBpelTransform() throws Exception {
        String[] locations = getProjectLocations();

        ProjectImporter projectImporter = importProjects(locations);
        try {

            IProject processProject = ResourcesPlugin.getWorkspace().getRoot()
                    .getProject(getTestProjectName());

            // Run bpel builder on the process project.
            BPELOnDemandBuilder bpelBuilder =
                    new BPELOnDemandBuilder(processProject);
            IStatus status =
                    bpelBuilder.buildProject(new NullProgressMonitor());
            if (!status.isOK()) {
                fail(String.format("BPEL builder failed on project: %s ",
                        processProject.getName()));
            }

            // Get expected bpel file.
            IFile processBpelFile = processProject.getFile(new Path(
                    ".processOut/process/" + getTestXpdlName() + "/"
                            + getTestProcessName() + ".bpel"));
            assertTrue("Generated process bpel exists",
                    processBpelFile.exists());
            
            Document document = getXmlDocument(processBpelFile);

            checkBpelContent(document);


        } finally {
            projectImporter.performDelete();
        }
    }



    /**
     * Assert attribute exists in the element and it has the expected value (if
     * provided).
     * 
     * @param elementNode
     * @param attrNsUri
     *            the optional uri of the attribute namespace.
     * @param attrName
     * @param attrExpectedValue
     *            the optional attribute name (if not provided then it won't be
     *            checked)
     */
    protected void assertAttr(Node elementNode, Optional<String> attrNsUri,
            String attrName, Optional<String> attrExpectedValue) {
        String elemName = elementNode.getLocalName();
        NamedNodeMap namedNodeMap = elementNode.getAttributes();
        Node attrNode = attrNsUri.isPresent()
                ? namedNodeMap.getNamedItemNS(attrNsUri.get(), attrName)
                : namedNodeMap.getNamedItem(attrName);
        assertTrue(String.format(
                "Element '%s' is missing attr '%s' (also checking NS)",
                elemName,
                attrName), attrNode != null);
        if (attrExpectedValue.isPresent()) { // assert value if present.
            String attrValue = attrNode.getTextContent();
            assertEquals(String.format(
                    "Element '%s' attr '%s' has unexpected value;",
                    elemName,
                    attrName), attrExpectedValue.get(), attrValue);
        }
    }

    /**
     * Finds first element that passes predicate in the node list.
     * 
     * @param nodeList
     * @param predicate
     * @return
     */
    protected Optional<Node> findFirstElement(NodeList nodeList,
            Predicate<Node> predicate) {
        int length = nodeList.getLength();
        for (int i = 0; i < length; i++) {
            Node item = nodeList.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE
                    && predicate.test(item)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if the provided node has the attribute. If optional expected value
     * is provided it will also be checked (if not then the attribute value will
     * be ignored).
     * 
     * @param elementNode
     * @param attrNsUri
     *            the optional uri of the attribute namespace.
     * @param attrName
     * @param attrExpectedValue
     *            optional expected value (if not provided then will be ignored)
     * @return <code>true</code> if element has the specified attribute (if
     *         attrExpectedValue is provided then it must also match).
     */
    protected boolean hasAttribute(Node elementNode, Optional<String> attrNsUri,
            String attrName, Optional<String> attrExpectedValue) {
        NamedNodeMap namedNodeMap = elementNode.getAttributes();
        Node attrNode = attrNsUri.isPresent()
                ? namedNodeMap.getNamedItemNS(attrNsUri.get(), attrName)
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
     * Parses and returns xml Document for an IFile.
     * 
     * @param iFile
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    protected Document getXmlDocument(IFile iFile)
            throws ParserConfigurationException, SAXException, IOException {
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

        String[] projectNames = Arrays.stream(projectLocations)
                .map(loc -> (new Path(loc)).lastSegment())
                .toArray(String[]::new);
        /*
         * Import and mgirate the project
         */
        ProjectImporter projectImporter =
                TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                        projectLocations,
                        projectNames);

        assertTrue("Failed to load projects " + projectNames,
                projectImporter != null);

        for (String name : projectNames) {
            IProject project =
                    ResourcesPlugin.getWorkspace().getRoot().getProject(name);
            assertTrue(name + " project does not exist",
                    project.isAccessible());
        }

        TestUtil.buildAndWait();
        return projectImporter;
    }

}
