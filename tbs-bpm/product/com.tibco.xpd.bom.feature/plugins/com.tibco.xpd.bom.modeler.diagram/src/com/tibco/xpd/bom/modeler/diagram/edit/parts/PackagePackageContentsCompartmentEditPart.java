/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.RectangularDropShadowLineBorder;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.AssociationClass;

import com.tibco.xpd.bom.modeler.diagram.edit.policies.BOMDiagramPopupBarEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.PackagePackageContentsCompartmentCanonicalEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.PackagePackageContentsCompartmentItemSemanticEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.part.Messages;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

/**
 * @generated
 */
public class PackagePackageContentsCompartmentEditPart extends
        ShapeCompartmentEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 5001;

    /**
     * @generated
     */
    public PackagePackageContentsCompartmentEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    @Override
    public String getCompartmentName() {
        return Messages.PackagePackageContentsCompartmentEditPart_title;
    }

    /**
     * @generated NOT
     */
    @Override
    public IFigure createFigure() {
        ResizableCompartmentFigure result =
                (ResizableCompartmentFigure) super.createFigure();

        // Set opacity and border
        result.setOpaque(true);
        result.setBorder(new RectangularDropShadowLineBorder());

        result.setTitleVisibility(false);
        return result;
    }

    /**
     * @generated NOT
     * 
     *            add CANONICAL_ROLE editpolicy
     */
    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new PackagePackageContentsCompartmentItemSemanticEditPolicy());

        // Need to specify this manually now that we do not have any
        // child reference nodes inside this compartment (which
        // we removed to eliminate nested editparts.
        // Otherwise this compartment doesn't think it has any semantic
        // children to manage
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new PackagePackageContentsCompartmentCanonicalEditPolicy());

        // Override drag drop policy to not provide any command - this will be
        // left to the edit policies
        removeEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE);
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new DragDropEditPolicy() {
                    @Override
                    public Command getCommand(Request request) {
                        if (request.getType()
                                .equals(BOMResourcesPlugin.REQ_FRAGMENT_DRAG)) {
                            return new Command() {
                                // Return dummy command to show selection when
                                // fragment is dragged onto diagram
                            };
                        } else if (request instanceof ChangeBoundsRequest) {
                            /*
                             * XPD-5196: Don't allow the drag-drop of
                             * Enumeration Literals directly into a Package (for
                             * some reason the EnumLit is a packageable
                             * element!).
                             */
                            List<?> editParts =
                                    ((ChangeBoundsRequest) request)
                                            .getEditParts();
                            if (editParts != null) {
                                for (Object obj : editParts) {
                                    if (obj instanceof EnumerationLiteralEditPart) {
                                        return null;
                                    }
                                }
                            }
                        }
                        return super.getCommand(request);
                    }
                });

        removeEditPolicy(EditPolicyRoles.CREATION_ROLE);

        installEditPolicy(EditPolicyRoles.CREATION_ROLE,
                new CreationEditPolicy() {
                    @Override
                    protected ICommand getReparentCommand(IGraphicalEditPart gep) {
                        ISelection selection =
                                PlatformUI.getWorkbench()
                                        .getActiveWorkbenchWindow()
                                        .getSelectionService().getSelection();

                        if (selection instanceof StructuredSelection) {
                            StructuredSelection sel =
                                    (StructuredSelection) selection;

                            List<?> list = sel.toList();
                            for (Object object : list) {
                                if (object instanceof AssociationClassEditPart
                                        || object instanceof AssociationClassDanglingNodeEditPart) {
                                    if (CanReparentSelectionWithAssociationClass(list)) {
                                        return super.getReparentCommand(gep);
                                    }
                                }
                            }
                        }

                        View view = (View) gep.getModel();
                        EObject element = ViewUtil.resolveSemanticElement(view);

                        if (element instanceof AssociationClass) {
                            return null;
                        }

                        return super.getReparentCommand(gep);
                    }

                });

        /*
         * Handle first-class profile extensions.
         */
        removeEditPolicy(EditPolicyRoles.POPUPBAR_ROLE);
        installEditPolicy(EditPolicyRoles.POPUPBAR_ROLE,
                new BOMDiagramPopupBarEditPolicy());

        /*
         * XPD-5156: In Eclipse 3.7 dragging a property / enumlit from one
         * class/enumeration unit to another respectively causes a NPE in the
         * log. This is due to the XYLayoutEditPolicy of this edit part being
         * asked to provide a command during the drag of a these elements and
         * during the handling of this request an NPE gets thrown. The end
         * result seems to be OK but the log gets polluted with a lot of
         * exceptions. There seems to be some change in the way this layout edit
         * policy works in Eclipse 3.7.
         */
        removeEditPolicy(EditPolicy.LAYOUT_ROLE);
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new XYLayoutEditPolicy() {
            /**
             * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy#getCommand(org.eclipse.gef.Request)
             * 
             * @param request
             * @return
             */
            @Override
            public Command getCommand(Request request) {
                if (request instanceof ChangeBoundsRequest) {
                    List<?> editParts =
                            ((ChangeBoundsRequest) request).getEditParts();

                    if (editParts != null) {
                        for (Object editPart : editParts) {
                            if (editPart instanceof PropertyEditPart
                                    || editPart instanceof OperationEditPart
                                    || editPart instanceof EnumerationLiteralEditPart) {
                                return null;
                            }
                        }
                    }
                }
                return super.getCommand(request);
            }
        });
    }

    private boolean CanReparentSelectionWithAssociationClass(List<?> list) {

        List<AssociationClassEditPart> lstAssocClassEp =
                new ArrayList<AssociationClassEditPart>();
        Map<AssociationClassDanglingNodeEditPart, EObject> assocClassDanglingNodeMap =
                new HashMap<AssociationClassDanglingNodeEditPart, EObject>();

        for (Object object : list) {
            if (object instanceof AssociationClassEditPart) {
                AssociationClassEditPart assocEP =
                        (AssociationClassEditPart) object;
                lstAssocClassEp.add(assocEP);
                continue;
            }

            if (object instanceof AssociationClassDanglingNodeEditPart) {
                AssociationClassDanglingNodeEditPart assocDanglingEP =
                        (AssociationClassDanglingNodeEditPart) object;
                AssociationClass element =
                        (AssociationClass) assocDanglingEP
                                .resolveSemanticElement();
                assocClassDanglingNodeMap.put(assocDanglingEP, element);
                continue;
            }
        }

        // First check:
        if (lstAssocClassEp.size() != assocClassDanglingNodeMap.size()) {
            return false;
        }

        // Second check: Is there a matching dangling node for this editpart?
        for (AssociationClassEditPart associationClassEP : lstAssocClassEp) {
            if (!assocClassDanglingNodeMap.containsValue(associationClassEP
                    .resolveSemanticElement())) {
                return false;
            }
        }

        return true;
    }

    /**
     * @generated
     */
    @Override
    protected void setRatio(Double ratio) {
        if (getFigure().getParent().getLayoutManager() instanceof ConstrainedToolbarLayout) {
            super.setRatio(ratio);
        }
    }

    /**
     * generated NOT
     * 
     * @override
     */
    /** Invoke the editpart's refresh mechanism. */
    @Override
    public void refresh() {
        PackagePackageContentsCompartmentCanonicalEditPolicy epol =
                (PackagePackageContentsCompartmentCanonicalEditPolicy) getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);
        epol.refresh();
        super.refresh();
    }

}
