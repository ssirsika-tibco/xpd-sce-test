/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.clipboard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.dnd.DropCommandManager;
import com.tibco.xpd.bom.resources.ui.internal.dnd.DropMenuManager;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;

/**
 * Commands factory used by drag and drop. This provides common drag-drop
 * commands to drag items in the project explorer and also from project explorer
 * to the editor.
 * <p>
 * Use {@link #getInstance()} to get singleton instance of this factory.
 * </p>
 * 
 * @author njpatel
 * 
 */
public final class DropCommandFactory {

    private static final DropCommandFactory INSTANCE = new DropCommandFactory();

    // Provides all drop menu functionality
    private final DropMenuManager menuManager;

    /**
     * Private constructor
     */
    private DropCommandFactory() {
        // Provides all menus
        menuManager = DropMenuManager.getInstance();
    }

    /**
     * Get singleton instance of this factory.
     * 
     * @return <code>DropCommandFactory</code>.
     */
    public static final DropCommandFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Create a command to drop objects into a <code>Package</code>. This will
     * show a menu option to either move, copy or subclass the selection into
     * the selected <code>Package</code>.
     * 
     * @param semanticTarget
     *            <code>Package</code> drop target
     * @param ed
     *            transactional editing domain
     * @param editPart
     *            the host edit part
     * @param req
     *            the drop request
     * 
     * @return <code>ICommand</code>
     */
    public ICommand createDropObjectsOnPackageCommand(Package semanticTarget,
            TransactionalEditingDomain ed, IGraphicalEditPart editPart,
            DropObjectsRequest req) {
        ICommand cmd = null;

        if (semanticTarget != null && ed != null && req != null
                && req.getObjects() != null && !req.getObjects().isEmpty()) {
            List<DropMenuManager.MenuOption> options =
                    new ArrayList<DropMenuManager.MenuOption>();
            boolean showMoveOption = true;
            boolean showSubclassOption = true;
            boolean allPackageableElements = true;
            // Determine whether the move and subclass options should be shown
            for (Object obj : req.getObjects()) {

                // If invalid object then return unexecutable command
                if (!(obj instanceof EObject)) {
                    return UnexecutableCommand.INSTANCE;
                }

                EObject eo = (EObject) obj;
                /*
                 * Can't move object into itself and cannot move if already in
                 * the target
                 */
                if (showMoveOption) {
                    showMoveOption =
                            eo != semanticTarget
                                    && eo.eContainer() != semanticTarget;
                }
                if (showSubclassOption) {
                    showSubclassOption = eo instanceof Class;
                }

                if (!(allPackageableElements =
                        eo instanceof PackageableElement
                                && !(eo instanceof EnumerationLiteral))) {
                    // Found element that cannot be dropped onto package
                    break;
                }

                if (!showMoveOption && !showSubclassOption
                        && !allPackageableElements) {
                    // all options have been discounted already so no need to
                    // check any more
                    break;
                }
            }

            if (allPackageableElements) {

                if (canCreateView(semanticTarget, editPart, req.getObjects())) {
                    options.add(menuManager.getCreateViewOption());
                }

                options.add(menuManager.getCopyOption());
                if (showMoveOption) {
                    // TODO: DISABLED FOR NOW AS MOVE DOESN'T WORK PROPERLY
                    // options.add(menuManager.getMoveIntoPackageOption());
                }
                if (showSubclassOption) {
                    options.add(menuManager.getSubclassOption());
                }

                cmd =
                        menuManager.getMenuCommand(Display.getCurrent()
                                .getActiveShell(),
                                ed,
                                semanticTarget,
                                req,
                                editPart,
                                options);
            }
        }

        return cmd != null ? cmd : UnexecutableCommand.INSTANCE;
    }

    /**
     * Check if this is a user defined diagram and views can be created for the
     * selected semantic elements.
     * 
     * @param semanticTarget
     * @param editPart
     * @param objects
     * @return
     * @since 3.3
     */
    private boolean canCreateView(EObject semanticTarget,
            IGraphicalEditPart editPart, List<?> objects) {

        // Check if all objects from the same resource
        Resource resource = semanticTarget.eResource();

        if (resource != null) {
            View notationView = editPart.getNotationView();
            Diagram diagram =
                    notationView != null ? notationView.getDiagram() : null;
            if (BomUIUtil.isUserDiagram(diagram)) {
                EList<?> children = notationView.getChildren();
                for (Object obj : objects) {
                    EObject eo = (EObject) obj;
                    if (eo instanceof Package || eo instanceof AssociationClass
                            || resource != eo.eResource()) {
                        // Element is a Package, Association Class or not from
                        // the same resource as
                        // the target
                        return false;
                    }

                    // Make sure none of the dragged elements already have a
                    // view in this diagram
                    for (Object child : children) {
                        if (child instanceof View) {
                            if (((View) child).getElement() == obj) {
                                return false;
                            }
                        }
                    }
                }

                /*
                 * If the target view is a Package then make sure that all the
                 * selected elements being dragged belong to that package in the
                 * semantic element
                 */
                if (!(notationView instanceof Diagram)) {
                    EObject elem = notationView.getElement();
                    if (elem instanceof Package) {
                        for (Object obj : objects) {
                            EObject eo = (EObject) obj;
                            if (eo.eContainer() != elem) {
                                return false;
                            }
                        }
                    }
                }
                // Can create view of selected elements in this diagram
                return true;
            }
        }

        return false;
    }

    /**
     * Create a command to drop objects onto a <code>Class</code>.
     * 
     * @param semanticTarget
     *            <code>Class</code> drop target
     * @param ed
     *            transactional editing domain
     * @param editPart
     *            host edit part
     * @param req
     *            drop request
     * @return <code>ICommand</code>.
     */
    public ICommand createDropObjectsOnEnumerationCommand(
            Enumeration semanticTarget, TransactionalEditingDomain ed,
            IGraphicalEditPart editPart, DropObjectsRequest req) {
        ICommand cmd = null;
        List<?> dropObjects = req.getObjects();
        // Can only drop one Enumeration on an Enumeration type - this will
        // create superclass
        if (semanticTarget != null && ed != null && dropObjects != null) {
            if (dropObjects.size() == 1
                    && dropObjects.get(0) instanceof Enumeration) {
                Enumeration target = (Enumeration) dropObjects.get(0);
                if (semanticTarget instanceof Enumeration) {
                    cmd =
                            DropCommandManager
                                    .createGeneralizationCommand(getContext(semanticTarget),
                                            ed,
                                            semanticTarget,
                                            target);
                }
            } else {
                // If this is a multi-select then check all the dropObjects are
                // valid
                // i.e. they should all be EnumerationLiterals

                /*
                 * If drop objects are from the target then just allow copy
                 */
                // Indicates that all drop objects are from target
                boolean allObjsFromTarget = true;
                // Indicates that atleast one drop object is from target (in
                // which
                // case only allow copy)
                boolean objFromTarget = false;

                for (Object obj : req.getObjects()) {
                    // If invalid object then return unexecutable command
                    if (!(obj instanceof EnumerationLiteral)) {
                        return UnexecutableCommand.INSTANCE;
                    }

                    EObject eo = (EObject) obj;

                    allObjsFromTarget =
                            allObjsFromTarget
                                    && eo.eContainer() == semanticTarget;
                    objFromTarget =
                            objFromTarget || eo.eContainer() == semanticTarget;
                }

                // If all objects are from target then only copy
                if (allObjsFromTarget) {
                    cmd =
                            DropCommandManager
                                    .createCopyObjectsCommand(semanticTarget,
                                            editPart,
                                            ed,
                                            req);
                } else {
                    /*
                     * Can copy or move properties or operations
                     */
                    List<DropMenuManager.MenuOption> options =
                            new ArrayList<DropMenuManager.MenuOption>();
                    options.add(menuManager.getCopyOption());
                    // options.add(menuManager.getMoveOption());

                    cmd =
                            menuManager.getMenuCommand(Display.getCurrent()
                                    .getActiveShell(),
                                    ed,
                                    semanticTarget,
                                    req,
                                    editPart,
                                    options);
                }
            }
        }

        return cmd != null ? cmd : UnexecutableCommand.INSTANCE;
    }

    /**
     * Create a command to drop objects onto a <code>Class</code>.
     * 
     * @param semanticTarget
     *            <code>Class</code> drop target
     * @param ed
     *            transactional editing domain
     * @param editPart
     *            host edit part
     * @param req
     *            drop request
     * @return <code>ICommand</code>.
     */
    public ICommand createDropObjectsOnClassCommand(Class semanticTarget,
            TransactionalEditingDomain ed, IGraphicalEditPart editPart,
            DropObjectsRequest req) {
        ICommand cmd = null;
        boolean isAllClassOrPrimitiveType = true;
        boolean isAllPropertyOrOperation = true;

        if (semanticTarget != null && ed != null && req != null
                && req.getObjects() != null && !req.getObjects().isEmpty()) {

            for (Object obj : req.getObjects()) {

                // If invalid object then return unexecutable command
                if (!(obj instanceof EObject)) {
                    return UnexecutableCommand.INSTANCE;
                }

                isAllClassOrPrimitiveType =
                        isAllClassOrPrimitiveType
                                && (obj instanceof Class || obj instanceof PrimitiveType);
                isAllPropertyOrOperation =
                        isAllPropertyOrOperation
                                && (obj instanceof Property || obj instanceof Operation);

                if (!isAllClassOrPrimitiveType && !isAllPropertyOrOperation) {
                    // Not allowed drop operation so quit searching any more
                    break;
                }
            }

            if (isAllClassOrPrimitiveType) {
                cmd =
                        createDropClassifiersOnClassCommand(semanticTarget,
                                ed,
                                editPart,
                                req);
            } else if (isAllPropertyOrOperation) {
                cmd =
                        createDropPropertyOrOperationOnClassCommand(semanticTarget,
                                ed,
                                editPart,
                                req);
            }
        }

        if (cmd == null) {
            return UnexecutableCommand.INSTANCE;
        } else {
            return cmd;
        }

    }

    /**
     * Create command to drop <code>Property</code> or <code>Operation</code> on
     * a <code>Class</code>.
     * 
     * @param semanticTarget
     *            drop target
     * @param ed
     *            transactional editing domain
     * @param editPart
     *            host edit part
     * @param req
     *            drop request
     * @return <code>ICommand</code>.
     */
    private ICommand createDropPropertyOrOperationOnClassCommand(
            Class semanticTarget, TransactionalEditingDomain ed,
            IGraphicalEditPart editPart, DropObjectsRequest req) {
        ICommand cmd = null;

        if (semanticTarget != null && ed != null && req != null
                && req.getObjects() != null && !req.getObjects().isEmpty()) {

            /*
             * If drop objects are from the target then just allow copy
             */
            // Indicates that all drop objects are from target
            boolean allObjsFromTarget = true;
            // Indicates that atleast one drop object is from target (in which
            // case only allow copy)
            boolean objFromTarget = false;

            for (Object obj : req.getObjects()) {

                // If invalid object then return unexecutable command
                if (!(obj instanceof EObject)) {
                    return UnexecutableCommand.INSTANCE;
                }

                EObject eo = (EObject) obj;

                allObjsFromTarget =
                        allObjsFromTarget && eo.eContainer() == semanticTarget;
                objFromTarget =
                        objFromTarget || eo.eContainer() == semanticTarget;
            }

            // If all objects are from target then only copy
            if (allObjsFromTarget) {
                cmd =
                        DropCommandManager
                                .createCopyObjectsCommand(semanticTarget,
                                        editPart,
                                        ed,
                                        req);
            } else {
                /*
                 * Can copy or move properties or operations
                 */
                List<DropMenuManager.MenuOption> options =
                        new ArrayList<DropMenuManager.MenuOption>();
                options.add(menuManager.getCopyOption());
                // options.add(menuManager.getMoveOption());

                cmd =
                        menuManager.getMenuCommand(Display.getCurrent()
                                .getActiveShell(),
                                ed,
                                semanticTarget,
                                req,
                                editPart,
                                options);
            }
        }

        return cmd;
    }

    /**
     * Create a command to drop <code>Class</code> or <code>PrimitiveType</code>
     * onto a <code>Class</code>.
     * 
     * @param semanticTarget
     *            <code>Class</code> drop target
     * @param ed
     *            transactional editing domain
     * @param editPart
     *            host edit part
     * @param req
     *            drop request
     * @return <code>ICommand</code>.
     */
    private ICommand createDropClassifiersOnClassCommand(Class semanticTarget,
            TransactionalEditingDomain ed, IGraphicalEditPart editPart,
            DropObjectsRequest req) {
        ICommand cmd = null;

        if (semanticTarget != null && ed != null && req != null
                && req.getObjects() != null && !req.getObjects().isEmpty()) {
            boolean showOption = false;

            if (req.getObjects().size() == 1) {
                /*
                 * If only one object contained in the drop list and this object
                 * is a class and not the same class as the target then show
                 * menu option
                 */
                Object obj = req.getObjects().get(0);

                showOption = obj instanceof Class && obj != semanticTarget;
            }

            if (showOption) {
                List<DropMenuManager.MenuOption> options =
                        new ArrayList<DropMenuManager.MenuOption>();

                if (semanticTarget.getModel() == ((Class) req.getObjects()
                        .get(0)).getModel()) {
                    // Create association
                    options.add(menuManager.getAssociationOption());
                } else {
                    options.add(menuManager.getPropertyOption());
                }

                options.add(menuManager.getSuperclassOption());
                // Show options to user
                cmd =
                        menuManager.getMenuCommand(Display.getCurrent()
                                .getActiveShell(),
                                ed,
                                semanticTarget,
                                req,
                                editPart,
                                options);
            } else {
                CompositeCommand comp =
                        new CompositeCommand(
                                Messages.DropCommandFactory_drop_title);
                // Create properties/association for each drop object
                IClientContext context = getContext(semanticTarget);
                for (Object obj : req.getObjects()) {
                    /*
                     * If the drop object is a Class and is in the same model as
                     * the target then create associaton, otherwise add a
                     * property of the drop object type
                     */
                    if (obj instanceof Class
                            && semanticTarget.getModel() == ((Class) obj)
                                    .getModel()) {
                        comp.add(DropCommandManager
                                .createAssociationCommand(context,
                                        ed,
                                        semanticTarget,
                                        (EObject) obj));
                    } else {
                        comp.add(DropCommandManager
                                .createPropertyCommand(context,
                                        ed,
                                        semanticTarget,
                                        (EObject) obj));
                    }
                }

                if (comp.size() > 0) {
                    cmd = comp;
                }
            }
        }

        return cmd;
    }

    /**
     * Create command to drop objects on <code>PrimitiveType</code>.
     * 
     * @param semanticTarget
     *            <code>PrimitiveType</code> drop target
     * @param ed
     *            transactional editing domain
     * @param dropObjects
     *            objects tp drop (semantic elements)
     * @return <code>ICommand</code>
     */
    public ICommand createDropObjectsOnPrimitiveTypeCommand(
            PrimitiveType semanticTarget, TransactionalEditingDomain ed,
            List<?> dropObjects) {
        ICommand cmd = null;
        // Can only drop one primitive type on a primitive type - this will
        // create superclass
        if (semanticTarget != null && ed != null && dropObjects != null
                && dropObjects.size() == 1
                && dropObjects.get(0) instanceof PrimitiveType) {
            PrimitiveType target = (PrimitiveType) dropObjects.get(0);
            if (semanticTarget instanceof PrimitiveType) {
                cmd =
                        DropCommandManager
                                .createGeneralizationCommand(getContext(semanticTarget),
                                        ed,
                                        semanticTarget,
                                        target);
            }
        }
        return cmd;
    }

    /**
     * Get client context for the given object.
     * 
     * @param obj
     *            object to get client context for
     * @return <code>IClientContext</code> if available, <code>null</code>
     *         otherwise.
     */
    private IClientContext getContext(EObject obj) {
        if (obj != null) {
            return ClientContextManager.getInstance().getClientContextFor(obj);
        }

        return null;
    }

}
