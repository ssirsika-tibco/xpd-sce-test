/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.commands.AssociationClassCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.AssociationClassDanglingNodeCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.BadgeCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.ClassCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.EnumerationCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.PackageCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.PrimitiveTypeCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.ui.clipboard.DropCommandFactory;

/**
 * @generated
 */
public class CanvasPackageItemSemanticEditPolicy extends
        UMLBaseItemSemanticEditPolicy {

    /**
     * Mark the request with the property that the edit helper will not create
     * the another create command.
     */
    protected Command getCreateCommand(CreateElementRequest req) {
        req.setParameter(IEditCommandRequest.REPLACE_DEFAULT_COMMAND,
                Boolean.TRUE);
        return getCreateCommandGen(req);
    }

    /**
     * @generated
     */
    protected Command getCreateCommandGen(CreateElementRequest req) {
        if (UMLElementTypes.Package_1001 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(UMLPackage.eINSTANCE
                        .getPackage_PackagedElement());
            }
            return getGEFWrapper(new PackageCreateCommand(req));
        }
        if (UMLElementTypes.Class_1002 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(UMLPackage.eINSTANCE
                        .getPackage_PackagedElement());
            }
            return getGEFWrapper(new ClassCreateCommand(req));
        }
        if (UMLElementTypes.PrimitiveType_1003 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(UMLPackage.eINSTANCE
                        .getPackage_PackagedElement());
            }
            return getGEFWrapper(new PrimitiveTypeCreateCommand(req));
        }
        if (UMLElementTypes.Enumeration_1004 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(UMLPackage.eINSTANCE
                        .getPackage_PackagedElement());
            }
            return getGEFWrapper(new EnumerationCreateCommand(req));
        }
        if (UMLElementTypes.AssociationClass_1005 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(UMLPackage.eINSTANCE
                        .getPackage_PackagedElement());
            }
            return getGEFWrapper(new AssociationClassCreateCommand(req));
        }
        if (UMLElementTypes.AssociationClass_1006 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(UMLPackage.eINSTANCE
                        .getPackage_PackagedElement());
            }
            return getGEFWrapper(new AssociationClassDanglingNodeCreateCommand(
                    req));
        }
        if (UMLElementTypes.Package_1007 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(UMLPackage.eINSTANCE
                        .getPackage_PackagedElement());
            }
            return getGEFWrapper(new BadgeCreateCommand(req));
        }
        return super.getCreateCommand(req);
    }

    /**
     * @generated
     */
    protected Command getDuplicateCommand(DuplicateElementsRequest req) {
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
                .getEditingDomain();
        return getGEFWrapper(new DuplicateAnythingCommand(editingDomain, req));
    }

    @Override
    protected Command getDropCommand(DropObjectsRequest request) {
        EObject element = getHostElement();
        Command cmd = null;
        List<?> objects = request.getObjects();

        if (objects != null && !objects.isEmpty()) {
            // Drop objects cannot contain model
            boolean containsModel = false;

            for (Object obj : objects) {
                if (containsModel = obj instanceof Model) {
                    break;
                }
            }

            if (!containsModel) {
                if (element instanceof Package) {
                    ICommand dropCmd = DropCommandFactory.getInstance()
                            .createDropObjectsOnPackageCommand(
                                    (Package) element, getEditingDomain(),
                                    (IGraphicalEditPart) getHost(), request);

                    if (dropCmd != null) {
                        cmd = new ICommandProxy(dropCmd);
                    }
                }
            } else {
                cmd = UnexecutableCommand.INSTANCE;
            }
        }

        return cmd;
    }

    /**
     * @generated
     */
    private static class DuplicateAnythingCommand extends
            DuplicateEObjectsCommand {

        /**
         * @generated
         */
        public DuplicateAnythingCommand(
                TransactionalEditingDomain editingDomain,
                DuplicateElementsRequest req) {
            super(editingDomain, req.getLabel(), req
                    .getElementsToBeDuplicated(), req
                    .getAllDuplicatedElementsMap());
        }

    }

}
