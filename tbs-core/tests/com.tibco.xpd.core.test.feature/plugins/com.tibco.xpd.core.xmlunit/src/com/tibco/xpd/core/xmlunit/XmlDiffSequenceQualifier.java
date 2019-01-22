/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.xmlunit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.ElementQualifier;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XmlDiffSequenceQualifier
 * <p>
 * This is a class that is used in conjunction with the {@link XmlDiff}'s
 * ElementQualifier.
 * <p>
 * The ElementQualifier is used by {@link Diff} when a sequence of same-named
 * elements is compared and gives us the opportunity to cope with order of
 * elements in control and test sequences being different.
 * <p>
 * {@link XmlDiff} can be 'loaded up' with a number of
 * {@link XmlDiffSequenceQualifierPath}'s. Each one contains a
 * {@link XmlDiffNodePath} specifying the sequence element it should apply to
 * and a number of relative {@link XmlDiffNodePath}'s for child nodes whose
 * values relative to the control & test elements should be compared to check
 * whether the control & test sequence elements qualify for comparison.
 * <p>
 * Each child qualifier can reference a single-instance child element attribute
 * or text-content OR it can refer to a attribute / text-content from a child
 * sequence that appears beneath the sequence elements we are qualifying. In the
 * latter case, the values of the multiple qualifier nodes from child sequence
 * elements are extracted, sorted and then the control & test value lists are
 * compared.
 * <p>
 * This means that the xml...
 * 
 * <pre>
 *    &lt;root&gt;
 *       &lt;Seq&gt;
 *          &lt;Value&gt;XXX&lt;/Value&gt;
 *          &lt;Child&gt;111&lt;/Child&gt;
 *          &lt;Child&gt;222&lt;/Child&gt;
 *          &lt;Child&gt;333&lt;/Child&gt;
 *       &lt;/Seq&gt;
 *       &lt;Seq&gt;
 *          &lt;Value&gt;YYY&lt;/Value&gt;
 *          &lt;Child&gt;444&lt;/Child&gt;
 *          &lt;Child&gt;555&lt;/Child&gt;
 *          &lt;Child&gt;666&lt;/Child&gt;
 *       &lt;/Seq&gt;
 * </pre>
 * 
 * ... can be successfully compared with a test document with the two
 * &lt;Seq&gt; elements reversed based on the fact that the Seq(XXX) in the test
 * document has the same &lt;Child&gt; test value sequence as the Seq(XXX) node
 * in the control document. You can do this by creating an
 * {@link XmlDiffSequenceQualifierPath} with the following code...
 * 
 * <pre>
 *     // 
 *     // Qualify the correct elements to compare in the sequence of &lt;Seq&gt; elements 
 *     // by comparing &lt;Seq&gt; elements that have the same &lt;Child&gt; element sequence.
 *     //
 *     
 *     // Node to qualify on is Child/(text-content) 
 *     List&lt;{@link XmlDiffNodePath}&gt; qualifierPaths = new ArrayList&lt;{@link XmlDiffNodePath}&gt;();
 *     qualifierPaths.add(new {@link XmlDiffNodePath}(new String[] { &quot;Child&quot;,
 *             XmlDiffNodePath.TEXT_CONTENT }, false));
 * 
 *     {@link XmlDiffSequenceQualifierPath} sequenceQualifier =
 *             new {@link XmlDiffSequenceQualifierPath}(new {@link XmlDiffNodePath}(&quot;Seq1&quot;),
 *                 qualifierPaths);
 *     
 *     // Add this Sequence Qualifier to the {@link XmlDiff} we are going to run.
 *     myXmlDiff.addSequenceElementQualifier(sequenceQualifier);
 * </pre>
 * <p>
 * <b>Note: </b>The &lt;Child&gt; sequence CAN EVEN BE OUT OF ORDER - and the
 * Seq elements will still be correctly qualified for comparison. (However, you
 * will of course required a second {@link XmlDiffSequenceQualifierPath} in
 * order to correctly compare the Child element sequence.
 * 
 * <p>
 * <b>Note 2:</b> In order for an individual
 * {@link XmlDiffSequenceQualifierPath} for a given sequenced element to
 * 'qualify for comparison' <b>all</b> values of the relative child node
 * qualifiers must be the same in control & test document (if the qualifiers are
 * absent in both then the elements are not considered to be valid for
 * comparison).
 * <p>
 * However, {@link XmlDiff}'s ElementQualifier will use all
 * {@link XmlDiffSequenceQualifierPath}'s that are applicable to a given element
 * and will state that the control & test elements qualify for comparison if
 * <b>ANY</b> individual {@link XmlDiffSequenceQualifierPath} states that the
 * control & test qualify for comparison.
 * <p>
 * In other words, at the top level {@link XmlDiffSequenceQualifierPath} results
 * are OR'd and the individual child qualifiers within each are AND'd.
 * <p>
 * <b>Note 3:</b> Each {@link XmlDiffSequenceQualifierPath} can also/instead
 * have specified an ElementQualifier which is delegated too. This can be used
 * for more complex situations when matching child nodes to qualify the main
 * node for comparison. When used in conjunction with normal qualifierNodePaths,
 * all must match.
 * 
 * @author aallway
 * @since 3.3 (15 Sep 2009)
 */
public class XmlDiffSequenceQualifier {

    private XmlDiffNodePath sequenceNodePath;

    private Collection<XmlDiffNodePath> qualifierNodePaths;

    private ElementQualifier delegateQualifier = null;

    private Set<Object> alreadyMatchedTrackingSet = null;

    /**
     * @param sequenceNodePath
     * @param qualifierNodePaths
     * @param delegateQualifier
     */
    public XmlDiffSequenceQualifier(XmlDiffNodePath sequenceNodePath,
            List<XmlDiffNodePath> qualifierNodePaths,
            ElementQualifier delegateQualifier) {
        super();

        this.sequenceNodePath = sequenceNodePath;
        if (this.sequenceNodePath.isTextNode()
                || this.sequenceNodePath.isAttributeNode()) {
            throw new RuntimeException("SequenceNodePath '" //$NON-NLS-1$
                    + sequenceNodePath
                    + "' must not reference an Attribute or Element's Text Content."); //$NON-NLS-1$
        }

        this.qualifierNodePaths = qualifierNodePaths;

        this.delegateQualifier = delegateQualifier;

        if (this.qualifierNodePaths != null) {
            for (XmlDiffNodePath path : this.qualifierNodePaths) {
                if (!path.isTextNode() && !path.isCdataNode()
                        && !path.isAttributeNode()) {
                    throw new RuntimeException("SequenceQualifierPath '" //$NON-NLS-1$
                            + path
                            + "' does not reference an Attribute or Element Text Content"); //$NON-NLS-1$

                } else if (path.isAbsoluteNodePath()) {
                    throw new RuntimeException("SequenceQualifierPath '" + path //$NON-NLS-1$
                            + "' is not a relative path."); //$NON-NLS-1$
                }
            }
        }

    }

    /**
     * @param sequenceNodePath
     * @param qualifierNodePaths
     */
    public XmlDiffSequenceQualifier(XmlDiffNodePath sequenceNodePath,
            List<XmlDiffNodePath> qualifierNodePaths) {
        this(sequenceNodePath, qualifierNodePaths, null);
    }

    /**
     * @param sequenceNodePath
     * @param qualifierNodePaths
     */
    public XmlDiffSequenceQualifier(XmlDiffNodePath sequenceNodePath,
            XmlDiffNodePath qualifierNodePath) {
        this(sequenceNodePath, Collections.singletonList(qualifierNodePath),
                null);
    }

    /**
     * @param sequenceNodePath
     * @param delegateQualifier
     */
    public XmlDiffSequenceQualifier(XmlDiffNodePath sequenceNodePath,
            ElementQualifier delegateQualifier) {
        this(sequenceNodePath, null, delegateQualifier);
    }

    /**
     * 
     * @param control
     * @return true if this Sequence Qualifier path is applicable to the given
     *         element from the control document.
     */
    public boolean isApplicable(Element control) {
        return sequenceNodePath.matches(control);
    }

    /**
     * @param control
     * @param test
     * 
     * @return true if the control and test document elements should be compared
     *         with each other (nominally {@link #isApplicable(Element)} should
     *         be checked first).
     */
    public boolean qualifiesForComparison(Element control, Element test) {
        if (isApplicable(control)) {
            /*
             * Check against any configured qualifierNodePaths.
             * 
             * qualifierNodePaths are all relative path from the control / test
             * element that must have the same value.
             * 
             * ALL values of qualifierNodes must be equal for the control and
             * test element to qualify for comparison.
             * 
             * So we get a list of all nodes matching each qualiferPath in turn
             * - from the control element and the test element. Then create a
             * list of node values for each, sort them and compare them. If
             * they're the same then the elements qualify for comparison.
             * 
             * This is so that we can handle both the simple qualification
             * scenarios (compare 2 elements if some single instance child (like
             * their @Id attr is the same) AND the more complex scenario's such
             * as some attribute/value of a child-sequence must be the same BUT
             * may not themselves be in a specific order).
             */
            if (qualifierNodePaths != null) {
                for (XmlDiffNodePath qPath : qualifierNodePaths) {
                    /*
                     * Get the control and test qualifier nodes (the node from
                     * the relative path from control/test)
                     */
                    List<Node> controlQualifierNodes =
                            getRelativeNodes(control, qPath);
                    List<Node> testQualifierNodes =
                            getRelativeNodes(test, qPath);

                    if (controlQualifierNodes.size() != testQualifierNodes
                            .size()) {
                        /*
                         * Must be the same number of matching qualifier child
                         * nodes - can't be the same.
                         */
                        return false;

                    } else if (controlQualifierNodes.size() == 0) {
                        /*
                         * If there are NO elements to compare in either then
                         * the user may have configured a separate
                         * SequenceQualifierPath so that they can configure
                         * (Match on Child/@Id IF PRESENT else match on
                         * CHild/Fred/@Id and so on).
                         */
                        return false;
                    }

                    /*
                     * Get the sorted value list for control and test nodes
                     */
                    List<String> controlQualifierValues =
                            getNodeValues(controlQualifierNodes, true);
                    List<String> testQualifierValues =
                            getNodeValues(testQualifierNodes, true);

                    /*
                     * Compare them
                     */
                    if (!controlQualifierValues.equals(testQualifierValues)) {
                        /*
                         * The value(s) of the qualifier child(ren) are not the
                         * same so they should not be compared.
                         */
                        return false;
                    }
                } /* Next child qualifier path */
            }

            /*
             * There were no specific qualifierNodes OR values of all
             * qualifierNodes were the same.
             * 
             * If there is an extra qualifier to delegate to then run it.
             */
            if (delegateQualifier != null) {
                if (!delegateQualifier.qualifyForComparison(control, test)) {
                    return false;
                }
            }

            /**
             * The default DifferenceEngine doesn't handle sequence lists
             * qualification very well.
             * 
             * IF you have several elements in a gold and test list that all are
             * ok to compare against each other according to a
             * {@link XmlDiffSequenceQualifier} (because the attributes used for
             * identifying which element in the test sequence should be compared
             * with an element in the gold sequence all match)
             * 
             * THEN the default DifferenceEngine will keep matching every gold
             * element that against THE FIRST test element that matches the
             * criteria in the {@link XmlDiffSequenceQualifier}
             * 
             * So if configured to do so, ignore this element if already matched
             */
            if (alreadyMatchedTrackingSet != null) {
                if (alreadyMatchedTrackingSet.contains(test)) {
                    return false;
                }
                alreadyMatchedTrackingSet.add(test);
            }

            /*
             * Everything we're interested in matches so the difference engine
             * can compare these two elements.
             */
            return true;
        }

        return false;
    }

    /**
     * The default DifferenceEngine doesn't handle sequence lists qualification
     * very well.
     * 
     * IF you have several elements in a gold and test list that all are ok to
     * compare against each other according to a
     * {@link XmlDiffSequenceQualifier} (because the attributes used for
     * identifying which element in the test sequence should be compared with an
     * element in the gold sequence all match)
     * 
     * THEN the default DifferenceEngine will keep matching every gold element
     * that against THE FIRST test element that matches the criteria in the
     * {@link XmlDiffSequenceQualifier}
     * 
     * So if you expect that you may have multiple elements in the target list
     * that qualify for multiple elements in the source list then turn on this
     * capability.
     * 
     * If you are using multiple {@link XmlDiffSequenceQualifier} on the same
     * element list (because it contains items of different types for instance)
     * BUT the qualifier attributes for different types are similar (and
     * therefore different element qualifiers) THEN
     * <li>You should add the MOST stringent {@link XmlDiffSequenceQualifier}
     * first</li>
     * <li>Use the same HashSet for alreadyMatchedTrackingSet so that elements
     * matched in one {@link XmlDiffSequenceQualifier} will not be matvched by
     * another {@link XmlDiffSequenceQualifier} for the same node set.
     * 
     * @param neverMatchSameTwice
     *            the neverMatchSameTwice to set
     */
    public void setAlreadyMatchedTrackingSet(
            Set<Object> alreadyMatchedTrackingSet) {
        this.alreadyMatchedTrackingSet = alreadyMatchedTrackingSet;
    }

    /**
     * 
     * @param controlQualifierNodes
     * @return
     */
    private List<String> getNodeValues(List<Node> nodes, boolean trimValue) {

        List<String> values = new ArrayList<String>(nodes.size());

        for (Node node : nodes) {
            String value = getNodeValue(node);

            if (value == null) {
                value = ""; //$NON-NLS-1$
            }

            if (trimValue) {
                value = value.trim();
            }

            values.add(value != null ? value : ""); //$NON-NLS-1$
        }

        Collections.sort(values, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        return values;
    }

    /**
     * @param node
     * @return the value of a node.
     */
    protected String getNodeValue(Node node) {
        return node.getNodeValue();
    }

    /**
     * @param element
     * @param relativePath
     * 
     * @return The child nodes that match the given relative path.
     */
    private List<Node> getRelativeNodes(Element element,
            XmlDiffNodePath relativePath) {

        List<Node> nodeCollection = new ArrayList<Node>();
        recursiveCollectRelativeNodes(element, relativePath, nodeCollection);

        return nodeCollection;
    }

    /**
     * Recursively collect all the end-of-path-nodes referred to by the given
     * relative path from the given curNode.
     * 
     * @param curNode
     * @param relativePath
     * @param nodeCollection
     */
    private void recursiveCollectRelativeNodes(Element curNode,
            XmlDiffNodePath relativePath, List<Node> nodeCollection) {

        XmlDiffNodePath[] segments = relativePath.getPathSegments();
        if (segments != null && segments.length > 0) {
            XmlDiffNodePath segPath = segments[0];

            if (segPath.isAttributeNode()) {
                /*
                 * Look for matching attributes.
                 */
                NamedNodeMap attrs = curNode.getAttributes();
                if (attrs != null) {
                    for (int aidx = 0; aidx < attrs.getLength(); aidx++) {
                        Node attr = attrs.item(aidx);

                        if (segPath.matches(attr)) {
                            nodeCollection.add(attr);
                        }
                    }
                }

            } else if (segPath.isTextNode() || segPath.isCdataNode()) {
                /*
                 * Look for text nodes.
                 */
                NodeList childElements = curNode.getChildNodes();

                for (int cidx = 0; cidx < childElements.getLength(); cidx++) {
                    Node childElement = childElements.item(cidx);

                    if (segPath.matches(childElement)) {
                        nodeCollection.add(childElement);
                    }
                }

            } else {
                /*
                 * Look for Element nodes
                 */
                NodeList childElements = curNode.getChildNodes();

                for (int cidx = 0; cidx < childElements.getLength(); cidx++) {
                    Node childElement = childElements.item(cidx);

                    if (segPath.matches(childElement)
                            && childElement instanceof Element) {
                        /*
                         * If we're dealing with the last part of the path then
                         * add to node list, otherwise recurse to collect
                         * matching nodex from next part in tree.
                         */
                        if (segments.length == 1) {
                            nodeCollection.add(childElement);

                        } else {
                            /*
                             * rebuild path minus the first part we're dealing
                             * with on this recursion.
                             */
                            String[] subSegs = new String[segments.length - 1];
                            for (int i = 0; i < subSegs.length; i++) {
                                subSegs[i] = segments[i + 1].getPath();
                            }

                            XmlDiffNodePath subPath =
                                    new XmlDiffNodePath(subSegs, false);

                            recursiveCollectRelativeNodes(
                                    (Element) childElement,
                                    subPath,
                                    nodeCollection);
                        }
                    }
                }
            }
        }

        return;
    }

}
