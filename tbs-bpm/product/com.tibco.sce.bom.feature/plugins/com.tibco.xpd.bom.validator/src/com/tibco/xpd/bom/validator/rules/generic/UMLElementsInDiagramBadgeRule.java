/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.generic;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.BadgeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimitiveTypeEditPart;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.validator.rules.BOMValidationRule;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * At some point in the past it seems that it was possible to corrupt the BOM
 * model in such a way that normal uml diagram elemetns (class, primitive type,
 * enum, package etc) get added to BADGE model element (rather than being a
 * child of the overall diagram).
 * <p>
 * This would cause a {@link ClassCastException} on opening the diagram editor.
 * 
 * @author aallway
 * @since 23 Jan 2012
 */
public class UMLElementsInDiagramBadgeRule extends BOMValidationRule {

    public static final String UML_ELEMENTS_IN_BADGE_ISSUE =
            "umlElementsInBadge.issue"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
        return Model.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Model) {
            Model model = (Model) o;

            /* Get the first (main) diagram element, that has the badge in. */
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(model);

            if (wc instanceof BOMWorkingCopy) {
                BOMWorkingCopy bwc = (BOMWorkingCopy) wc;

                for (Diagram diagram : bwc.getDiagrams()) {

                    for (Iterator iterator =
                            diagram.getPersistedChildren().iterator(); iterator
                            .hasNext();) {
                        EObject child = (EObject) iterator.next();

                        /*
                         * Should only need to check for badge at top level of
                         * diagram.
                         */
                        if (isBadgeElement(child)) {

                            /*
                             * Check if badge element has visual nodes for
                             * Class/PrimitiveType/Package/Enumeration
                             */
                            if (hasUMLElements((Node) child)) {
                                scope.createIssue(UML_ELEMENTS_IN_BADGE_ISSUE,
                                        BOMValidationUtil.getLocation(model),
                                        model.eResource().getURIFragment(model));
                            }
                        }
                    }

                }
            }
        }

    }

    /**
     * @param child
     * @return <code>true</code> if the child is a diagram Badge node.
     */
    public static boolean isBadgeElement(EObject child) {
        if (child instanceof Node) {
            Node node = (Node) child;

            if (BadgeEditPart.VISUAL_ID == getNodeType(node)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param badge
     * @return <code>true</code> if there are UML (Class, PrimitiveType,
     *         Enumeration or Inner Package) elements in the given badge node
     */
    public static boolean hasUMLElements(Node badge) {

        for (Iterator iterator = badge.getPersistedChildren().iterator(); iterator
                .hasNext();) {
            EObject eo = (EObject) iterator.next();

            if (eo instanceof Node) {
                Node node = (Node) eo;

                if (isUMLElementNode(node)) {
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * 
     * @param node
     * 
     * @return <code>true</code> if the Node is one for a UML diagram element
     *         (Class, Enumeration, Package, Primitive)
     */
    public static boolean isUMLElementNode(Node node) {
        switch (getNodeType(node)) {
        case ClassEditPart.VISUAL_ID:
        case PrimitiveTypeEditPart.VISUAL_ID:
        case EnumerationEditPart.VISUAL_ID:
        case PackageEditPart.VISUAL_ID:
            return true;
        }
        return false;
    }

    /**
     * @param node
     * @return TRype of diagram node expressed as int.
     */
    public static int getNodeType(Node node) {
        try {
            int type = Integer.parseInt(node.getType());
            return type;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
