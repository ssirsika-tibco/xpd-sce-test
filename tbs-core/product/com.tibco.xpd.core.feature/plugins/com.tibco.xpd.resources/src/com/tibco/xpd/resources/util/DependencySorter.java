package com.tibco.xpd.resources.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;

/**
 * Utility class for ordering dependencies. Dependencies between 'nodes' are
 * represented as directed arcs ({@link DependencySorter.Arc}). Nodes can be of
 * any generic type 'T' and should have correctly implemented
 * {@link #hashCode()} and {@link #equals(Object)}.
 * 
 * <pre>
 * Example:
 *   public static void main(String[] args) {
 *       String a1 = &quot;A1&quot;, a2 = &quot;A2&quot;, a3 = &quot;A3&quot;, a4 = &quot;A4&quot;, a5 = &quot;A5&quot;, a6 = &quot;A6&quot;, a7 =
 *               &quot;a7&quot;;
 *       List&lt;String&gt; aNodes = Arrays.asList(a1, a2, a3, a4, a5, a6, a7);
 *       List&lt;DependencySorter.Arc&lt;String&gt;&gt; aArcs = new ArrayList&lt;Arc&lt;String&gt;&gt;();
 *       aArcs.add(new DependencySorter.Arc&lt;String&gt;(a1, a2));
 *       aArcs.add(new DependencySorter.Arc&lt;String&gt;(a1, a3));
 *       aArcs.add(new DependencySorter.Arc&lt;String&gt;(a2, a4));
 *       aArcs.add(new DependencySorter.Arc&lt;String&gt;(a2, a5));
 *       aArcs.add(new DependencySorter.Arc&lt;String&gt;(a3, a5));
 *       aArcs.add(new DependencySorter.Arc&lt;String&gt;(a3, a6));
 *       final DependencySorter&lt;String&gt; dependencySorter =
 *               new DependencySorter&lt;String&gt;(aArcs, aNodes);
 *       System.out.println(dependencySorter.getOrderedList());
 *   }
 * </pre>
 * 
 * @author Jan Arciuchiewicz, njpatel
 * @since 3.3
 */
public class DependencySorter<T> {

    /**
     * Represents an node dependency relationship ('from' depends on 'to').
     */
    public static class Arc<E> {

        protected E from;

        protected E to;

        /**
         * Creates dependency arc between 'from' and 'to' nodes with semantic:
         * 'from' node depends on 'to' node ('from'->'to').
         * 
         * @param from
         *            the node depending on 'to'.
         * @param to
         *            the node on which 'from' depends.
         */
        public Arc(E from, E to) {

            this.from = from;
            this.to = to;
        }

        /**
         * Get the 'from' of this arc.
         * 
         * @return
         */
        public E getFrom() {

            return from;
        }

        /**
         * Get the 'to' of this arc.
         * 
         * @return
         */
        public E getTo() {

            return to;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {

            return (from.toString() + " -> " + to.toString()); //$NON-NLS-1$
        }
    }

    /**
     * List of nodes that appear in dependency relationships.
     */
    private final List<T> nodes;

    /**
     * Set of dependencies,
     */
    private final Set<Arc<T>> arcs;

    /**
     * Ordered list of the elements.
     */
    private final List<T> sortedList = new ArrayList<T>();

    /**
     * Creates instance.
     * 
     * @param arcs
     *            the collection of dependency arcs.
     * @param nodes
     *            the ordered list of nodes or <code>null</code> if all nodes
     *            can be inferred from arcs and non-dependent nodes order is not
     *            important.
     */
    public DependencySorter(Collection<Arc<T>> arcs, List<T> nodes) {

        this.arcs = new LinkedHashSet<Arc<T>>(arcs);
        /* Defensive copy of list. */
        this.nodes =
                (nodes != null) ? new ArrayList<T>(nodes)
                        : createNodesListFromArcs();
    }

    /**
     * Get the sorted dependency list.
     * 
     * @return
     * @throws IllegalArgumentException
     *             when a cyclic dependency has been detected. For details of
     *             the cyclic dependency see cause exception (this contains a
     *             multi-status with details of the cyclic dependency).
     */
    public List<T> getOrderedList() {

        while (!nodes.isEmpty()) {
            /* Get minimal node - node with no dependencies */
            T minimalNode = getMinimalNode();

            if (minimalNode != null) {

                addToSortedList(minimalNode);
                removeToNode(minimalNode);
            } else {

                /* There is a cyclic dependency so report it */
                IStatus[] errStatus = new IStatus[arcs.size()];
                /*
                 * XPD-6403: Here nodes list has had all of leaf nodes removed -
                 * so all nodes in list appear in an 'arc.from' which must be a
                 * cycle.
                 * 
                 * So take a copy of nodes so that we can remove all of the root
                 * nodes (all of the nodes that do not appear in an 'arc.to')
                 * 
                 * The remainder must be JUST the arcs in a cycle.
                 */
                while (!nodes.isEmpty()) {

                    T rootNode = getRootNode();

                    if (rootNode != null) {

                        removeFromNode(rootNode);
                    } else {
                        /* Thats' it, nothing but the nodes in a cycle left. */
                        break;
                    }
                }

                /* construct the message string */
                String msg = nodes.toString();

                int idx = 0;
                for (Arc<T> arc : arcs) {
                    errStatus[idx++] =
                            new Status(
                                    IStatus.ERROR,
                                    XpdResourcesPlugin.ID_PLUGIN,
                                    String.format(Messages.DependencySorter_dependsOn_message,
                                            arc.from,
                                            arc.to));
                }

                throw new IllegalArgumentException(
                        Messages.DependencySorter_cyclicDependency_error_shortdesc,
                        new CoreException(new Status(0,
                                XpdResourcesPlugin.ID_PLUGIN, msg)));
            }
        }

        return sortedList;
    }

    /**
     * Remove the root node (that appears in the 'arc.from') from the arc and
     * nodes collections.
     * 
     * @param rootNode
     */
    private void removeFromNode(T rootNode) {

        for (Iterator<Arc<T>> iter = arcs.iterator(); iter.hasNext();) {

            if (rootNode.equals(iter.next().from)) {

                iter.remove();
            }
        }
        nodes.remove(rootNode);
    }

    /**
     * @return first root node found in the list. This will be the node that is
     *         a starting node that does not appear in any 'arc.to'
     */
    private T getRootNode() {

        for (T node : nodes) {

            if (isRootNode(node)) {

                return node;
            }
        }

        return null;
    }

    /**
     * Returns <code>true</code> if there is an arc starting from this node and
     * this is not a 'to' in any of the arcs.
     * 
     * @param node
     * @return <code>true</code> if this is a root node <code>false</code>
     *         otherwise
     */
    private boolean isRootNode(T node) {

        for (Arc<T> arc : arcs) {

            if (node.equals(arc.to))

                return false;
        }
        return true;
    }

    /**
     * Creates list of nodes from arcs.
     * 
     * @return List of nodes.
     */
    private List<T> createNodesListFromArcs() {

        LinkedHashSet<T> nodeSet = new LinkedHashSet<T>();
        for (Arc<T> arc : arcs) {

            if (arc.from != null && !nodeSet.contains(arc.from)) {

                nodeSet.add(arc.from);
            }
        }
        for (Arc<T> arc : arcs) {

            if (arc.to != null && !nodeSet.contains(arc.to)) {

                nodeSet.add(arc.to);
            }
        }
        return new ArrayList<T>(nodeSet);
    }

    /**
     * Add the given asset to the sorted list if the list doesn't already
     * contain it.
     * 
     * @param node
     */
    private void addToSortedList(T node) {

        if (node != null && !sortedList.contains(node)) {

            sortedList.add(node);
        }
    }

    /**
     * Returns <code>true</code> if there is no arc such that the asset is on
     * its 'from' side, i.e. asset type with no dependencies.
     * 
     * @param node
     * @return
     */
    private boolean isMinimal(T node) {

        for (Arc<T> arc : arcs) {

            if (node.equals(arc.from))

                return false;
        }
        return true;
    }

    /**
     * Get the minimal node. This will be the node that has no dependencies.
     * 
     * @return minimal node or <code>null</code> if none found.
     */
    private T getMinimalNode() {

        for (T node : nodes) {

            if (isMinimal(node))

                return node;
        }
        return null;
    }

    /**
     * Remove the given node from the arc and nodes collections.
     * 
     */
    private void removeToNode(T node) {

        for (Iterator<Arc<T>> iter = arcs.iterator(); iter.hasNext();) {

            if (node.equals(iter.next().to)) {

                iter.remove();
            }
        }
        nodes.remove(node);
    }

}