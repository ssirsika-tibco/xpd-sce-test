/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.validator.resolution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.validator.rules.generic.UMLElementsInDiagramBadgeRule;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * 
 * 
 * @author aallway
 * @since 23 Jan 2012
 */
public class UMLElementsInDiagramBadgeResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    @Override
    public void run(IMarker marker) throws ResolutionException {
        if (marker != null && marker.exists()) {

            try {
                EObject target = getTarget(marker);

                if (target != null) {
                    EditingDomain editingDomain =
                            WorkingCopyUtil.getEditingDomain(target);
                    if (editingDomain != null) {
                        Command cmd =
                                getResolutionCommand(editingDomain,
                                        target,
                                        marker);

                        if (cmd != null && cmd.canExecute()) {
                            editingDomain.getCommandStack().execute(cmd);

                            /*
                             * Force a save on the working copy because the
                             * valdiation rule is based on Model element we only
                             * change the Diagram element the recordingCommand
                             * we use does not get picked up in terms of
                             * re-validating the bom file.
                             */
                            WorkingCopy wc =
                                    WorkingCopyUtil.getWorkingCopyFor(target);

                            if (wc != null) {
                                try {
                                    wc.save();
                                } catch (IOException e) {
                                    throw new ResolutionException(e);
                                }
                            }

                        }
                    }
                }

            } catch (CoreException e) {
                throw new ResolutionException(e);
            }
        }
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Model
                && editingDomain instanceof TransactionalEditingDomain) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(target);

            if (wc instanceof BOMWorkingCopy) {
                final BOMWorkingCopy bwc = (BOMWorkingCopy) wc;

                RecordingCommand cmd =
                        new RecordingCommand(
                                (TransactionalEditingDomain) editingDomain) {

                            @Override
                            protected void doExecute() {
                                for (Diagram diagram : bwc.getDiagrams()) {

                                    List<EObject> addToDiagram =
                                            new ArrayList<EObject>();

                                    for (Iterator iterator =
                                            diagram.getPersistedChildren()
                                                    .iterator(); iterator
                                            .hasNext();) {
                                        EObject child =
                                                (EObject) iterator.next();

                                        /*
                                         * Should only need to check for badge
                                         * at top level of diagram.
                                         */
                                        if (child instanceof Node
                                                && UMLElementsInDiagramBadgeRule
                                                        .isBadgeElement(child)) {
                                            Node badge = (Node) child;

                                            /*
                                             * Check if badge element has visual
                                             * nodes for
                                             * Class/PrimitiveType/Package
                                             * /Enumeration
                                             */
                                            List<Node> umlElementNodes =
                                                    getUMLElements(badge);

                                            if (!umlElementNodes.isEmpty()) {
                                                /* Remove from badge. */
                                                badge.getPersistedChildren()
                                                        .removeAll(umlElementNodes);

                                                /*
                                                 * Save list of things to add to
                                                 * the diagram in a moment
                                                 * (because we're already
                                                 * iterating that list!
                                                 * 
                                                 * But only if the actual model
                                                 * element it represents is
                                                 * still there (though I don't
                                                 * think this is really
                                                 * necessary as the BOM working
                                                 * copy won't load if you have
                                                 * node with a broken reference
                                                 * to model..
                                                 */
                                                for (Iterator umlElemIter =
                                                        umlElementNodes
                                                                .iterator(); umlElemIter
                                                        .hasNext();) {
                                                    Node umlElem =
                                                            (Node) umlElemIter
                                                                    .next();

                                                    if (umlElem.getElement() != null) {
                                                        addToDiagram
                                                                .add(umlElem);
                                                    }
                                                }
                                            }
                                        }
                                    } /* Next diagram element. */

                                    /*
                                     * Re-add the UML element nodes previously
                                     * under the badge element node into the
                                     * diagram.
                                     */
                                    if (!addToDiagram.isEmpty()) {
                                        diagram.getPersistedChildren()
                                                .addAll(addToDiagram);
                                    }
                                }
                            }
                        };

                return cmd;

            }

        }

        return null;
    }

    /**
     * @param badge
     * 
     * @return List of UML elements that should not be child of teh given badge
     *         node.
     */
    private List<Node> getUMLElements(Node badge) {
        List<Node> umlElementNodes = new ArrayList<Node>();

        for (Iterator iterator = badge.getPersistedChildren().iterator(); iterator
                .hasNext();) {
            EObject child = (EObject) iterator.next();

            if (child instanceof Node) {
                if (UMLElementsInDiagramBadgeRule
                        .isUMLElementNode((Node) child)) {
                    umlElementNodes.add((Node) child);
                }
            }
        }

        return umlElementNodes;
    }

}
