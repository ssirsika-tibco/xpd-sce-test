/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.dependency.visualization.internal.views.provider;

import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;

import com.tibco.xpd.dependency.visualization.internal.analysis.GraphCyclesAnalyser;

/**
 * This class provides in set of artifacts such as nodes, relationships which needs to be highlighted.
 * This class populates cycles, direct and indirect relationships and nodes which are participating in the relationships. 
 * 
 * @author ssirsika
 * @since 01-Dec-2015
 */
public abstract class HighlightArtifactsProvider extends AbstractVisualizationLabelProvider {

	private GraphViewer viewer;

	/**
	 * @param viewer
	 * @param currentLabelProvider
	 */
	public HighlightArtifactsProvider(GraphViewer viewer, AbstractVisualizationLabelProvider currentLabelProvider) {
		super(viewer, currentLabelProvider);
		this.viewer = viewer;
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.internal.views.provider.AbstractVisualizationLabelProvider#calculateInterestingRelationships(java.util.HashSet, java.util.HashSet, java.util.HashSet, java.util.HashSet, java.util.HashSet, java.util.HashSet)
	 *
	 * @param interestingRels
	 * @param interestingEntities
	 * @param indirectRelationships
	 * @param indirectNodes
	 * @param cyclicRelationships
	 * @param cyclicNodes
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void calculateInterestingRelationships(HashSet interestingRels, HashSet interestingEntities, HashSet indirectRelationships, HashSet indirectNodes, HashSet cyclicRelationships, HashSet cyclicNodes) {

		Object[] connectionElements = viewer.getConnectionElements();
		Object selectedObj = getSelected();
		for (Object element : connectionElements) {
			if (element instanceof EntityConnectionData) {
				EntityConnectionData entityConnectionData = (EntityConnectionData) element;
				calculateCycles(entityConnectionData, cyclicRelationships, cyclicNodes);
				if (selectedObj != null && isReferencingResourceDependencies()) {
					if (selectedObj.equals(entityConnectionData.dest)) {
						interestingRels.add(entityConnectionData);
						interestingEntities.add(entityConnectionData.source);
						populateIndirectReferencingResources(entityConnectionData.source, indirectRelationships, indirectNodes, cyclicRelationships, cyclicNodes);
					}
				}
				if (selectedObj != null && isReferencedResourceDependencies()) {
					if (selectedObj.equals(entityConnectionData.source)) {
						interestingRels.add(entityConnectionData);
						interestingEntities.add(entityConnectionData.dest);
						populateIndirectReferencedResources(entityConnectionData.dest, indirectRelationships, indirectNodes, cyclicRelationships, cyclicNodes);
					}
				}
			}
		}
	}

	/**
	 * Populate Indirectly referencing resources. 
	 * @param source current graph item
	 * @param indirectRelationships {@link HashSet} to store indirect Relationships
	 * @param indirectNodes {@link HashSet} to store nodes present in the indirect Relationships
	 * @param cyclicRelationships {@link HashSet} of cyclic relationships 
	 * @param cyclicNodes {@link HashSet} of nodes present in cyclic relationships
	 */
	@SuppressWarnings("rawtypes")
	private void populateIndirectReferencingResources(Object source, HashSet indirectRelationships, HashSet indirectNodes, HashSet cyclicRelationships, HashSet cyclicNodes) {
		GraphNode gn = (GraphNode) viewer.findGraphItem(source);
		List<GraphConnection> targetConnections = gn.getTargetConnections();
		for (GraphConnection connection : targetConnections) {
			Object data = connection.getData();
			if (data instanceof EntityConnectionData) {
				EntityConnectionData entityConnectionData = (EntityConnectionData) data;
				calculateCycles(entityConnectionData, cyclicRelationships, cyclicNodes);
				indirectNodes.add(entityConnectionData.source);
				indirectRelationships.add(entityConnectionData);
				if (!(cyclicNodes.contains(entityConnectionData.source) && cyclicNodes.contains(entityConnectionData.dest))) {
					populateIndirectReferencingResources(entityConnectionData.source, indirectRelationships, indirectNodes, cyclicRelationships, cyclicNodes);
				}
			}
		}
	}

	/**
	 * Populate Indirectly referenced resources. 
	 *@param source current graph item
	 *@param indirectRelationships {@link HashSet} to store indirect Relationships
	 * @param indirectNodes {@link HashSet} to store nodes present in the indirect Relationships
	 * @param cyclicRelationships {@link HashSet} of cyclic relationships 
	 * @param cyclicNodes {@link HashSet} of nodes present in cyclic relationships
	 */
	@SuppressWarnings("rawtypes")
	private void populateIndirectReferencedResources(Object source, HashSet indirectRelationships, HashSet indirectNodes, HashSet cyclicRelationships, HashSet cyclicNodes) {
		GraphNode gn = (GraphNode) viewer.findGraphItem(source);
		List<GraphConnection> sourceConnections = gn.getSourceConnections();
		for (GraphConnection connection : sourceConnections) {
			Object data = connection.getData();
			if (data instanceof EntityConnectionData) {
				EntityConnectionData entityConnectionData = (EntityConnectionData) data;
				calculateCycles(entityConnectionData, cyclicRelationships, cyclicNodes);
				indirectNodes.add(entityConnectionData.dest);
				indirectRelationships.add(entityConnectionData);
				if (!(cyclicNodes.contains(entityConnectionData.source) && cyclicNodes.contains(entityConnectionData.dest))) {
					populateIndirectReferencedResources(entityConnectionData.dest, indirectRelationships, indirectNodes, cyclicRelationships, cyclicNodes);
				}
			}
		}
	}

	/**
	 * This method check it passed relationship is part of cycle and if it is part of cycle then populate cyclicRelationships and cyclicNodes {@link HashSet}.
	 * @param entityConnectionData relations under consideration
	 * @param cyclicRelationships {@link HashSet} of cyclic relationships
	 * @param cyclicNodes {@link HashSet} of nodes present in the cycles
	 */
	@SuppressWarnings("rawtypes")
	private void calculateCycles(EntityConnectionData entityConnectionData, HashSet cyclicRelationships, HashSet cyclicNodes) {
		if (cyclicRelationships.contains(entityConnectionData)) {
			return;
		}
		boolean hasCycle = GraphCyclesAnalyser.hasCycle((IResource) entityConnectionData.source, (IResource) entityConnectionData.dest);
		if (hasCycle) {
			cyclicRelationships.add(entityConnectionData);
			cyclicNodes.add(entityConnectionData.source);
			cyclicNodes.add(entityConnectionData.dest);
		}
	}

}