/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal.analysis;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;

/**
 * Analyser for find the cycles in Graph.
 * 
 * @author Ali
 * @since 13 Nov 2014
 */
public class GraphCyclesAnalyser {

	private static List<CyclicNodeData> cyclicNodesData;

	/**
	 * Clear the old history of cyclicNodesData
	 */
	public static void resetCyclicNodesData() {
		if (cyclicNodesData != null) {
			cyclicNodesData.clear();
		}
	}

	/**
	 * Analyze cycles in the graph and return the list of {@link CyclicNodeData}
	 * @param graph {@link Graph}
	 * @return list of connected nodes data or null
	 */
	public static List<CyclicNodeData> analyzeCycles(Graph graph) {
		if (cyclicNodesData == null || cyclicNodesData.isEmpty()) {

			if (graph != null) {

				analyseCyclesInGraph(graph);
			}
		}
		return cyclicNodesData;
	}

	/**
	 * Check if there exists any cycles between src and dest
	 * @param src
	 * @param dest
	 * @return true if there is a cycle, false otherwise
	 */
	public static boolean hasCycle(IResource src, IResource dest) {

		if (cyclicNodesData != null) {

			for (CyclicNodeData nodeData : cyclicNodesData) {
				if (nodeData.srcNodeName.equals(src.getName()) && nodeData.destNodeName.equals(dest.getName())) {
					return nodeData.getHasCycle();
				}
			}
		}
		return false;
	}

	/**
	 * Find cycles in the graph
	 * @param graph {@link Graph}
	 */
	private static void analyseCyclesInGraph(Graph graph) {
		if (graph != null) {

			cyclicNodesData = new ArrayList<>();

			// Get the list of graph nodes
			List<GraphNode> graphNodes = graph.getNodes();

			// Check for cyclic connections starting from each of the node as a
			// root
			for (GraphNode topNode : graphNodes) {
				checkCycle(topNode, new ArrayList<GraphNode>());
			}
		}
	}

	/**
	 * For the given 'node', get the list of nodes it depends on and recursively
	 * check for cycles in each of the dependent node. If a dependent node X is
	 * already processed, traverse backwards and add each of the connection as a
	 * cycle until it reaches back to node X.
	 * 
	 * 
	 * @param node
	 * @param alreadyProcessed
	 */
	private static void checkCycle(GraphNode node, List<GraphNode> alreadyProcessed) {
		List<GraphConnection> sourceConnections = node.getSourceConnections();

		for (GraphConnection conn : sourceConnections) {
			GraphNode dependantNode = conn.getDestination();

			if (alreadyProcessed.contains(dependantNode)) {
				addCycle(dependantNode, alreadyProcessed, dependantNode);

			} else {
				alreadyProcessed.add(dependantNode);
				checkCycle(dependantNode, alreadyProcessed);
				alreadyProcessed.remove(dependantNode);
			}
		}
	}

	/**
	 * Add cycles for all the connections found while traversing backwards from
	 * the given 'node' using the path calculated through the given
	 * 'alreadyProcessed' nodes until it reaches back to the 'node'.
	 * 
	 * @param node
	 * @param alreadyProcessed
	 * @param terminalNode
	 */
	private static void addCycle(GraphNode node, List<GraphNode> alreadyProcessed, GraphNode terminalNode) {

		List<GraphConnection> sourceConnections = node.getSourceConnections();

		for (GraphConnection conn : sourceConnections) {
			GraphNode n = conn.getDestination();
			if (!n.equals(terminalNode) && alreadyProcessed.contains(n)) {
				CyclicNodeData cyclicNodeData = new CyclicNodeData(node.getText(), n.getText(), true);
				if (!cyclicNodeDataExists(cyclicNodeData)) {
					cyclicNodesData.add(cyclicNodeData);
				}
			}
		}

	}

	/**
	 * @param cyclicNodeData
	 * @return
	 */
	private static boolean cyclicNodeDataExists(CyclicNodeData cyclicNodeData) {
		for (CyclicNodeData nodeData : cyclicNodesData) {
			if (nodeData.srcNodeName.equals(cyclicNodeData.srcNodeName) && nodeData.destNodeName.equals(cyclicNodeData.destNodeName)) {
				return true;
			}
		}
		return false;
	}
}
