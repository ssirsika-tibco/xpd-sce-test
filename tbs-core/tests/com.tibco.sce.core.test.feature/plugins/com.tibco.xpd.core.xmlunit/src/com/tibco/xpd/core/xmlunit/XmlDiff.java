/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.core.xmlunit;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceEngine;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.ElementNameQualifier;
import org.custommonkey.xmlunit.ElementQualifier;
import org.custommonkey.xmlunit.NodeDetail;
import org.custommonkey.xmlunit.Transform;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XmlDiff
 * <p>
 * XML Differencer that allows flexible comparison by...
 * <li>Allowing certain elements / attributes to be ignored when comparing
 * control & test elements. (see {@link #addIgnoreNode(XmlDiffIgnoreNode)}).</li>
 * 
 * <li>Allowing repeating sequences of elements to be agnostic of the actual
 * sequence order in control & test documents (see
 * {@link #addSequenceElementQualifier(XmlDiffSequenceQualifier)})</li>
 * 
 * <li>Allowing custom comparisons to be performed on some elements/attributes
 * when they are found to be different (so that where values of node may alter
 * slightly (say, namespace prefix reference in their _value_) there is no need
 * to COMPLETELY ignore them)</li>
 * 
 * @author aallway
 * @since 3.3 (15 Sep 2009)
 */
public class XmlDiff extends Diff {

    private Collection<XmlDiffIgnoreNode> ignoreNodes =
            new ArrayList<XmlDiffIgnoreNode>();

    private Map<XmlDiffNodePath, Comparator<Node>> customComparisons =
            new HashMap<XmlDiffNodePath, Comparator<Node>>();

    private Collection<XmlDiffSequenceQualifier> sequenceQualifierPaths =
            new ArrayList<XmlDiffSequenceQualifier>();

    private DifferenceListener differenceListener;

    private ElementQualifier elementQualifier;

    private boolean shouldPrintFileContent;

    private static String testFileContent =
            "Test File Content Not Available For test Input used to construct diff.";

    /**
     * @param control
     * @param test
     * @throws SAXException
     * @throws IOException
     */
    public XmlDiff(String control, String test) throws SAXException,
            IOException {

        super(control, test);
        testFileContent = test;

        init();
    }

    public XmlDiff(Diff prototype) {
        super(prototype);

        init();
    }

    public XmlDiff(Document controlDoc, Document testDoc,
            DifferenceEngine comparator, ElementQualifier elementQualifier) {
        super(controlDoc, testDoc, comparator, elementQualifier);

        init();
    }

    public XmlDiff(Document controlDoc, Document testDoc,
            DifferenceEngine comparator) {
        super(controlDoc, testDoc, comparator);

        init();
    }

    public XmlDiff(Document controlDoc, Document testDoc) {
        super(controlDoc, testDoc);

        init();
    }

    public XmlDiff(DOMSource control, DOMSource test) {
        super(control, test);

        init();
    }

    public XmlDiff(InputSource control, InputSource test) throws SAXException,
            IOException {

        super(new InputStreamReader(control.getByteStream()),
                new ReaderWithPreservedStringContent(test));
        testFileContent =
                outputFromReaderWithPreservedStringContent.get().toString();

        init();
    }

    public void setTestFileContent(boolean shouldPrintFileContent) {

        this.shouldPrintFileContent = shouldPrintFileContent;
    }

    public XmlDiff(Reader control, Reader test) throws SAXException,
            IOException {

        super(control, new ReaderWithPreservedStringContent(test));
        testFileContent =
                outputFromReaderWithPreservedStringContent.get().toString();

        init();
    }

    public XmlDiff(String control, Transform testTransform) throws IOException,
            TransformerException, SAXException {
        super(control, testTransform);

        init();
    }

    protected void init() {
        differenceListener = new XmlDifferenceListener();
        overrideDifferenceListener(differenceListener);

        elementQualifier = new XmlElementQualifier();
        overrideElementQualifier(elementQualifier);

        XMLUnit.setIgnoreAttributeOrder(true);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
    }

    /**
     * Add a name/path of an element / attribute whose differences you wish to
     * be ignore.
     * 
     * @param ignoreNode
     */
    public void addIgnoreNode(XmlDiffIgnoreNode ignoreNode) {
        ignoreNodes.add(ignoreNode);

        return;
    }

    /**
     * Set all of the elements / attributes nodes to ignore differences in.
     * 
     * @param ignoreNodes
     */
    public void setIgnoreNodes(Collection<XmlDiffIgnoreNode> ignoreNodes) {
        this.ignoreNodes = ignoreNodes;

        return;
    }

    /**
     * Add a custom comparator to XmlDiff for the given path.
     * <p>
     * When basic XmlDiff finds a difference in a node that isn't included in
     * IgnodeNodes list then next we check for custom comparisons.
     * <p>
     * We will execute each Comparator added here with a path that matches the
     * given node - if ALL comparators for the given path return 0 (Nodes match)
     * then the difference will ignored.
     * 
     * @param path
     * @param comparator
     */
    public void addCustomComparison(XmlDiffNodePath path,
            Comparator<Node> comparator) {
        customComparisons.put(path, comparator);
    }

    /**
     * When XmlDiff discovers multiple siblings with same name, in order to cope
     * with potentially different sort order in control and test documents, it
     * must be able to ascertain which element in the test element list to
     * compare with a given element in the control element list.
     * <p>
     * This can be done by adding {@link XmlDiffSequenceQualifier}'s. Each one
     * specifies an {@link XmlDiffNodePath} specifying the repeating element and
     * a list of relative paths to child nodes to compare (and / or a custom
     * ElementQualifier class).
     * <p>
     * If all the relative child node values in control & test element match and
     * optionally the delegate element qualifier matches. Then the given control
     * and test element sequence members will be compared.
     * <p>
     * The control & test element will be compared if ANY individual
     * {@link XmlDiffSequenceQualifier} matches them.
     * <p>
     * An individual {@link XmlDiffSequenceQualifier} only matches the control &
     * test element if all the child qualifier nodes have same value and the
     * delegate element qualifier (if provided) matches as well.
     * 
     * 
     * @param sequenceQualifierPath
     */
    public void addSequenceElementQualifier(
            XmlDiffSequenceQualifier sequenceQualifierPath) {
        sequenceQualifierPaths.add(sequenceQualifierPath);

        return;
    }

    /**
     * Set all {@link XmlDiffSequenceQualifier}'s in one go.
     * <p>
     * See {@link #addSequenceElementQualifier(XmlDiffSequenceQualifier)} for
     * more details.
     * 
     * @param sequenceQualifierPaths
     */
    public void setSequenceElementQualifier(
            Collection<XmlDiffSequenceQualifier> sequenceQualifierPaths) {
        this.sequenceQualifierPaths = sequenceQualifierPaths;

        return;
    }

    /**
     * @return the differenceListener in case you wish to delegate to it.
     */
    public DifferenceListener getDifferenceListener() {
        return differenceListener;
    }

    /**
     * @return the elementQualifier in case you wish to delegate to it.
     */
    public ElementQualifier getElementQualifier() {
        return elementQualifier;
    }

    /**
     * @see org.custommonkey.xmlunit.Diff#toString()
     * 
     * @return
     */
    @Override
    public String toString() {

        StringBuffer sb =
                new StringBuffer(super.toString()
                        + "\n====== TEST FILE CONTENT =============\n"); //$NON-NLS-1$
        if (shouldPrintFileContent) {

            sb.append(testFileContent);
            sb.append("\n==================================\n");
        }
        return sb.toString();
    }

    /**
     * XmlDifferenceListener
     * <p>
     * Our own difference listener that takes account the ignoreNodes list of
     * the XmlDiff class.
     * 
     * 
     * @author aallway
     * @since 3.3 (15 Sep 2009)
     */
    private class XmlDifferenceListener implements DifferenceListener {

        public int differenceFound(Difference difference) {
            if (ignoreNodes != null && !ignoreNodes.isEmpty()) {
                /*
                 * Not sure what happens when XMLUnit finds an extra node in the
                 * test doc that isn't in the control doc - will it call
                 * difference without a control node - I would guess so, so if
                 * the control node is missing check the test node instead.
                 */
                NodeDetail nodeDetail = difference.getControlNodeDetail();
                if (nodeDetail == null || nodeDetail.getNode() == null) {
                    nodeDetail = difference.getTestNodeDetail();
                }

                if (nodeDetail != null && nodeDetail.getNode() != null) {
                    Node node = nodeDetail.getNode();

                    /*
                     * Look for an IgnoreNode that matches the path of the node
                     * that's different. If we find one then pretend that the
                     * node is same.
                     * 
                     * Also check for parent element that is being ignored.
                     */
                    while (node != null) {
                        for (XmlDiffIgnoreNode ignoreNode : ignoreNodes) {
                            if (ignoreNode.matches(node)) {
                                return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                            }
                        }

                        node = XmlDiffNodePath.getParentElement(node);

                    }
                }
            }

            Node controlNode = difference.getControlNodeDetail().getNode();
            Node testNode = difference.getTestNodeDetail().getNode();

            if (customComparisons != null && !customComparisons.isEmpty()) {
                /*
                 * True if all comparators for teh given paths are happy
                 */
                boolean allComparatorsHappy = true;
                boolean haveCustomComparators = false;

                for (Entry<XmlDiffNodePath, Comparator<Node>> customCompareEntry : customComparisons
                        .entrySet()) {
                    /*
                     * If both the control and test node match the path in this
                     * entry then use the comparator provided to override the
                     * result.
                     */
                    XmlDiffNodePath matchPath = customCompareEntry.getKey();

                    if (matchPath.matches(controlNode)
                            && matchPath.matches(testNode)) {
                        haveCustomComparators = true;

                        /*
                         * Both nodes match the path for this comparison so call
                         * the comparator for them
                         */
                        Comparator<Node> comparator =
                                customCompareEntry.getValue();

                        int result = comparator.compare(controlNode, testNode);
                        if (result != 0) {
                            allComparatorsHappy = false;
                            break;
                        }
                    }
                }

                if (haveCustomComparators && allComparatorsHappy) {
                    /*
                     * If we had some custom comparators for the node's path AND
                     * all of them are happy with the comparison. Then this is
                     * ok.
                     */
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }
            }

            if (difference.getId() == DifferenceConstants.CHILD_NODELIST_LENGTH_ID) {
                /*
                 * Sometimes Diff see's Text nodes and sometimes it does not -
                 * for instance if you have elemtn <a> with a single child <b>
                 * and in control doc there is a newline before <b> and in test
                 * doc there is not a newline then DIFF will imagine that the
                 * first case has an extra Text child node and hence we will get
                 * different child node count.
                 * 
                 * So we need to cover for this by ignoring different nodelist
                 * length when different is only empty children.
                 */
                List<Node> controlChildrenExceptEmptyText =
                        getChildNodesExceptEmptyText(controlNode);

                List<Node> testChildrenExceptEmptyText =
                        getChildNodesExceptEmptyText(testNode);

                if (controlChildrenExceptEmptyText.size() == testChildrenExceptEmptyText
                        .size()) {
                    return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
                }

            } else if (difference.getId() == DifferenceConstants.CHILD_NODE_NOT_FOUND_ID) {
                /*
                 * Having ignored empty text nodes (see CHILD_NODELIST_LENGTH_ID
                 * handling above) we will then be told that a Text node != null
                 * so we have to ignore that too.
                 */
                if (controlNode != null) {
                    if (isEmptyTextNode(controlNode) && testNode == null) {
                        return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                    }
                } else if (testNode != null) {
                    if (isEmptyTextNode(testNode) && controlNode == null) {
                        return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                    }
                }
            }

            return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
        }

        /**
         * @param controlNode
         * @return
         */
        private List<Node> getChildNodesExceptEmptyText(Node controlNode) {
            List<Node> childrenExceptEmptyText = new ArrayList<Node>();
            NodeList controlChildren = controlNode.getChildNodes();
            for (int i = 0; i < controlChildren.getLength(); i++) {
                Node child = controlChildren.item(i);
                if (!isEmptyTextNode(child)) {
                    childrenExceptEmptyText.add(child);
                }
            }
            return childrenExceptEmptyText;
        }

        private boolean isEmptyTextNode(Node node) {
            if (node.getNodeType() == Node.TEXT_NODE) {
                String value = node.getNodeValue();
                if (value == null || value.trim().length() == 0) {
                    return true;
                }
            }
            return false;
        }

        public void skippedComparison(Node control, Node test) {
        }

    }

    /**
     * XmlElementQualifier
     * 
     * When XmlDiff discovers multiple siblings with same name, in order to cope
     * with potentially different sort order in control and test documents, it
     * must be able to ascertain which element in the test element list to
     * compare with a given element in the control element list.
     * <p>
     * This can be done by adding {@link XmlDiffSequenceQualifier}'s. Each one
     * specifies an {@link XmlDiffNodePath} specifying the repeating element and
     * a list of relative paths to child nodes to compare (and / or a custom
     * ElementQualifier class).
     * <p>
     * If all the relative child node values in control & test element match and
     * optionally the delegate element qualifier matches. Then the given control
     * and test element sequence members will be compared.
     * <p>
     * The control & test element will be compared if ANY individual
     * {@link XmlDiffSequenceQualifier} matches them.
     * <p>
     * An individual {@link XmlDiffSequenceQualifier} only matches the control &
     * test element if all the child qualifier nodes have same value and the
     * delegate element qualifier (if provided) matches as well. *
     * 
     * @author aallway
     * @since 3.3 (15 Sep 2009)
     */
    private class XmlElementQualifier extends ElementNameQualifier {

        @Override
        public boolean qualifyForComparison(Element control, Element test) {
            /*
             * Ensure name and namespace match.
             */
            if (super.qualifyForComparison(control, test)) {
                if (sequenceQualifierPaths != null
                        && !sequenceQualifierPaths.isEmpty()) {

                    /*
                     * Find a SequenceQualifier Path applicable to the control
                     * element that states the control and test qualify for
                     * comparison.
                     */
                    for (XmlDiffSequenceQualifier qPath : sequenceQualifierPaths) {
                        if (qPath.isApplicable(control)) {
                            if (qPath.qualifiesForComparison(control, test)) {
                                /*
                                 * This one says "yes" that's good enough. i.e.
                                 * at this level the sequenceQualifierPaths are
                                 * OR'd (internally, theoir individual child
                                 * qualifiers are AND'd - this gives us the
                                 * flexibility we will need
                                 */

                                return true;
                            }

                            /*
                             * Check for any other qualifier paths applicable to
                             * this element.
                             */
                        }
                    }

                } else {
                    /*
                     * No qualifer paths specified so go with the name and NS.
                     */
                    return true;
                }

            }

            return false;
        }
    }

    private static ThreadLocal<Writer> outputFromReaderWithPreservedStringContent;

    /**
     * This is a thread safe inner class that ensures the content read from the
     * {@link InputSource} is preserved to be re-used through out the execution
     * of that thread.
     * 
     * @author bharge, nwilson, aallway
     * @since 20 Jun 2014
     */
    private static class ReaderWithPreservedStringContent extends Reader {

        private Reader in;

        public ReaderWithPreservedStringContent(InputSource in) {

            this(new InputStreamReader(in.getByteStream()));
        }

        public ReaderWithPreservedStringContent(Reader in) {

            this.in = in;
            outputFromReaderWithPreservedStringContent =
                    new ThreadLocal<Writer>();
            outputFromReaderWithPreservedStringContent.set(new StringWriter());
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {

            int result = in.read(cbuf, off, len);
            outputFromReaderWithPreservedStringContent.get().write(cbuf,
                    off,
                    len);
            return result;
        }

        @Override
        public void close() throws IOException {

            in.close();
            outputFromReaderWithPreservedStringContent.get().close();
        }

    }

}
