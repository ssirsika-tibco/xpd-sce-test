package com.tibco.xpd.om.modeler.subdiagram.edit.commands.custom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgUnitCustomEditPart;
import com.tibco.xpd.om.modeler.subdiagram.part.custom.DiagramUtils;

public class ExpandCollapseOrgUnitNodeHierarchyCommand extends RecordingCommand {

    public static int HIER_EXPAND = 0;
    public static int HIER_COLLAPSE = 1;
    private OrgUnitCustomEditPart orgUnitEP;
    private boolean forceExpandCollapseOptionChosen = false;
    private boolean forceExpand = false;

    public ExpandCollapseOrgUnitNodeHierarchyCommand(
            TransactionalEditingDomain domain, OrgUnitCustomEditPart ep) {
        super(domain);
        orgUnitEP = ep;
    }

    public ExpandCollapseOrgUnitNodeHierarchyCommand(
            TransactionalEditingDomain domain, OrgUnitCustomEditPart ep,
            int expandOrCollapse) {
        super(domain);
        orgUnitEP = ep;
        forceExpandCollapseOptionChosen = true;

        if (expandOrCollapse == HIER_EXPAND) {
            forceExpand = true;
        } else {
            forceExpand = false;
        }

    }

    @Override
    protected void doExecute() {
        boolean collapsed = false;
        List<View> toRefresh = new ArrayList<View>();
        Object model = orgUnitEP.getModel();
        if (model instanceof View) {
            View view = (View) model;

            if (view instanceof Node) {
                Node n = (Node) view;

                if (forceExpandCollapseOptionChosen) {
                    collapsed = hideChildren(n, true, forceExpand, toRefresh);
                } else {
                    collapsed = hideChildren(n, true, false, toRefresh);
                }

            }
        }

        if (collapsed) {
            orgUnitEP.setFigureExpandIcon();
        } else {
            orgUnitEP.setFigureCollapseIcon();
        }

        orgUnitEP.refresh();

        for (View view : toRefresh) {
            EditPart ep = DiagramUtils.getEditPart(view);

            if (ep != null) {
                ep.refresh();
            }
        }
    }

    private boolean hideChildren(Node node, boolean isRoot, boolean setVisible,
            List<View> lstRefresh) {

        boolean hierarchyHidden = false;

        // Get the nodes outgoing edges
        EList<Edge> sourceEdges = node.getSourceEdges();

        for (Edge edge : sourceEdges) {
            if (edge.getElement() instanceof OrgUnitRelationship) {
                OrgUnitRelationship rel = (OrgUnitRelationship) edge
                        .getElement();

                // Only set the visibility if we are
                // called from the root OrgUnit and we are not specifying a
                // forced expansion. We determine the setting dynamically based
                // on the current state. If currently collapsed we flip to
                // expanded and vice versa. This will mainly be relevant for
                // mouse-clicks on the expand/collpase icon
                if (isRoot == true) {
                    if (!forceExpandCollapseOptionChosen) {
                        if (!edge.isVisible()) {
                            setVisible = true;
                            hierarchyHidden = false;
                        } else {
                            // Just to make sure this is set
                            // OK
                            setVisible = false;
                            hierarchyHidden = true;
                        }

                    } else {
                        if (setVisible) {
                            hierarchyHidden = false;
                        } else {
                            hierarchyHidden = true;
                        }
                    }
                }

                if (rel.isIsHierarchical()) {
                    // Recurse down to the end node, passing
                    // down the boolean whether to hide/expand
                    // tree
                    View targetNode = edge.getTarget();

                    if (targetNode instanceof Node) {
                        hideChildren((Node) targetNode, false, setVisible,
                                lstRefresh);

                        // OK, we've reached the end of the
                        // recursion so start setting the
                        // visibility
                        targetNode.setVisible(setVisible);
                        edge.setVisible(setVisible);
                    }

                } else {
                    // Just hide/show the edge. Don't drill down
                    // to the node
                    if (!isRoot) {
                        View targetNode = edge.getTarget();

                        if (targetNode instanceof Node) {
                            edge.setVisible(setVisible);
                            lstRefresh.add(targetNode);
                        }
                    }

                }

            } else {
                // This edge doesn't need to be recursed as it is probably a
                // NoteAttachment, so just hide/show it.
                // Don't need to test specifically for NoteAttachment because
                // we may have other link types in the future and this will be a
                // catch-all for them
                View targetNode = edge.getTarget();

                if (targetNode instanceof Node) {
                    edge.setVisible(setVisible);
                    lstRefresh.add(targetNode);
                }

            }

        }

        EList<Edge> targetEdges = node.getTargetEdges();

        for (Edge targetEdge : targetEdges) {
            // These edges should only be non-hierarchical
            // OrgUnitRelationships. We still want to hide them.
            if (targetEdge.getElement() instanceof OrgUnitRelationship) {
                OrgUnitRelationship association = (OrgUnitRelationship) targetEdge
                        .getElement();

                if (!association.isIsHierarchical() && !isRoot) {
                    targetEdge.setVisible(setVisible);

                    // Add the source node to the list to
                    // refresh
                    View source = targetEdge.getSource();
                    lstRefresh.add(source);
                }
            } else {
                // Probably a note attachment
                if (!isRoot) {
                    targetEdge.setVisible(setVisible);

                    // Add the source node to the list to
                    // refresh
                    View source = targetEdge.getSource();
                    lstRefresh.add(source);
                }

            }
        }

        return hierarchyHidden;

    }

}
