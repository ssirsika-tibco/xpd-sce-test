/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.dnd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.clipboard.BOMCopyPasteCommandFactory;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Manager that provides the commonly used commands in the DragDrop operation in
 * the BOM. These are used by drop operations in the editor and the project
 * explorer.
 * 
 * @author njpatel
 * 
 */
public final class DropCommandManager {

    /**
     * Drop command manager - private constructor
     */
    private DropCommandManager() {
        // Private constructor
    }

    /**
     * Create a Command to create a view on a user defined diagram.
     * 
     * @param semanticTarget
     *            drop target semantic element
     * @param ed
     *            transactional editing domain
     * @param editPart
     *            host edit part, this can be <code>null</code>.
     * @param req
     *            drop request
     * @return <code>ICommand</code>
     * @since 3.3
     */
    public static ICommand createCreateViewCommand(EObject semanticTarget,
            TransactionalEditingDomain ed, IGraphicalEditPart editPart,
            DropObjectsRequest req) {
        ICommand cmd = null;

        if (semanticTarget instanceof Package && ed != null && req != null
                && req.getObjects() != null && !req.getObjects().isEmpty()) {
            CompoundCommand commands =
                    new CompoundCommand("Add Views to new Diagram");
            IClientContext cc =
                    ClientContextManager.getInstance()
                            .getClientContextFor(semanticTarget);
            Point location = req.getLocation();
            for (Object obj : req.getObjects()) {
                if (obj instanceof EObject) {
                    EObject eo = (EObject) obj;
                    String semanticHint = null;
                    IElementType type =
                            ElementTypeRegistry.getInstance()
                                    .getElementType(eo, cc);
                    if (type instanceof IHintedType) {
                        semanticHint = ((IHintedType) type).getSemanticHint();
                    }

                    if (semanticHint != null) {
                        CreateViewRequest viewReq =
                                new CreateViewRequest(
                                        new ViewDescriptor(
                                                new EObjectAdapter(eo),
                                                Node.class,
                                                semanticHint,
                                                new PreferencesHint(
                                                        "com.tibco.xpd.bom.modeler.diagram"))); //$NON-NLS-1$
                        viewReq.setLocation(location);
                        // Transpose the position so the next element doesn't
                        // end up exactly on top
                        location = location.translate(10, 10);

                        Command command = editPart.getCommand(viewReq);

                        if (command != null) {
                            commands.add(command);
                        }
                    }
                }
            }

            cmd = new CommandProxy(commands);
        }

        return cmd;
    }

    /**
     * Create a move objects command.
     * 
     * @param semanticTarget
     *            drop target semantic element
     * @param ed
     *            transactional editing domain
     * @param editPart
     *            host edit part, this can be <code>null</code>.
     * @param req
     *            drop request
     * @return <code>ICommand</code>
     */
    public static ICommand createMoveObjectsCommand(EObject semanticTarget,
            TransactionalEditingDomain ed, IGraphicalEditPart editPart,
            DropObjectsRequest req) {
        ICommand cmd = null;

        if (semanticTarget != null && ed != null && req != null
                && req.getObjects() != null && !req.getObjects().isEmpty()) {

            if (semanticTarget instanceof Element) {
                Model model = ((Element) semanticTarget).getModel();

                if (model != null) {

                    IClientContext context =
                            ClientContextManager.getInstance()
                                    .getClientContextFor(model);

                    if (context != null) {
                        IElementType type =
                                ElementTypeRegistry.getInstance()
                                        .getElementType(semanticTarget);
                        if (type != null) {
                            MoveRequest moveReq =
                                    new MoveRequest(ed, semanticTarget, req
                                            .getObjects());
                            cmd = type.getEditCommand(moveReq);
                        }
                    }
                }
            }
        }
        return cmd;
    }

    /**
     * Create a copy objects command.
     * 
     * @param semanticTarget
     *            drop target semantic element
     * @param targetEditPart
     *            target graphical edit part, <code>null</code> if not available
     * @param ed
     *            transactional editing domain
     * @param request
     *            drop object request
     * 
     * @return <code>ICommand</code>
     */
    public static ICommand createCopyObjectsCommand(EObject semanticTarget,
            IGraphicalEditPart targetEditPart, TransactionalEditingDomain ed,
            DropObjectsRequest request) {
        ICommand cmd = null;
        // Get target view from the edit part
        Object editPartModel =
                targetEditPart != null ? targetEditPart.getModel() : null;
        View semanticView =
                (View) (editPartModel instanceof View ? editPartModel : null);

        if (semanticView != null && ed != null && request != null
                && request.getObjects() != null
                && !request.getObjects().isEmpty()) {

            final List<View> objectViews =
                    getNotationObjects(ed, request.getObjects());

            IMapMode mapMode = null;
            if (targetEditPart != null) {
                IFigure figure = targetEditPart.getFigure();
                if (figure != null) {
                    mapMode = MapModeUtil.getMapMode(figure);
                }
            }

            if (!objectViews.isEmpty()) {
                cmd =
                        new CopyCommand(ed, semanticView, objectViews, mapMode,
                                request.getLocation());
            }
        }

        return cmd;
    }

    /**
     * Create command to set given type as <code>Property</code>.
     * 
     * @param context
     *            client context
     * @param ed
     *            transactional editing domain
     * @param parentClass
     *            <code>Class</code> to set the <code>Property</code> in
     * @param type
     *            type of <code>Property</code>
     * @return <code>ICommand</code>
     */
    public static ICommand createPropertyCommand(IClientContext context,
            TransactionalEditingDomain ed, EObject parentClass, EObject type) {
        ICommand cmd = null;

        if (context != null && ed != null && parentClass != null
                && type != null) {
            IElementType classType =
                    ElementTypeRegistry.getInstance()
                            .getElementType(parentClass);
            IElementType propType =
                    ElementTypeRegistry.getInstance()
                            .getElementType(UMLPackage.eINSTANCE.getProperty(),
                                    context);

            // Create property command
            CreateElementRequest req =
                    new CreateElementRequest(ed, parentClass, propType,
                            UMLPackage.eINSTANCE
                                    .getStructuredClassifier_OwnedAttribute());
            req.setClientContext(context);
            req
                    .setParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.TYPE,
                            type);

            if (type instanceof Type) {
                ICommand editCommand = classType.getEditCommand(req);

                if (editCommand != null) {
                    cmd =
                            new CheckProjectDependencyWrapperCommand(
                                    ed,
                                    parentClass,
                                    (Type) type,
                                    editCommand,
                                    Messages.DropCommandManager_checkProjectDependency_operation_label);
                }
            }
        }
        return cmd;
    }

    /**
     * Create a command to create a {@link Generalization}.
     * 
     * @param context
     *            client context
     * @param ed
     *            transactional editing domain
     * @param parentClass
     *            class to set generalization on
     * @param general
     *            the superclass
     * @return <code>ICommand</code>
     */
    public static ICommand createGeneralizationCommand(IClientContext context,
            TransactionalEditingDomain ed, EObject parentClass, EObject general) {
        ICommand cmd = null;
        if (context != null && ed != null && parentClass != null
                && general != null) {
            IElementType genType =
                    ElementTypeRegistry.getInstance()
                            .getElementType(UMLPackage.eINSTANCE
                                    .getGeneralization(),
                                    context);

            CreateRelationshipRequest req =
                    new CreateRelationshipRequest(ed, parentClass, general,
                            genType);
            req.setClientContext(context);

            if (general instanceof Type) {
                ICommand editCommand = genType.getEditCommand(req);

                if (editCommand != null) {
                    cmd =
                            new CheckProjectDependencyWrapperCommand(
                                    ed,
                                    parentClass,
                                    (Type) general,
                                    editCommand,
                                    Messages.DropCommandManager_generalization_checkProjectDependency_operation_label);
                }
            }
        }
        return cmd;
    }

    /**
     * Create command to create an <code>Association</code>.
     * 
     * @param context
     *            client context
     * @param ed
     *            transactional editing domain
     * @param parentClass
     *            <code>Class</code> to set <code>Association</code> in
     * @param target
     *            <code>Association</code> target
     * @return <code>ICommand</code>
     */
    public static ICommand createAssociationCommand(IClientContext context,
            TransactionalEditingDomain ed, EObject parentClass, EObject target) {
        ICommand cmd = null;

        if (context != null && ed != null && parentClass != null
                && target != null) {
            IElementType type =
                    ElementTypeRegistry.getInstance()
                            .getElementType(UMLPackage.eINSTANCE
                                    .getAssociation(),
                                    context);

            CreateRelationshipRequest req =
                    new CreateRelationshipRequest(ed, parentClass, target, type);
            req.setClientContext(context);

            // If parent and target are different then set source to target
            // navigability
            if (parentClass != target) {
                req
                        .setParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.SOURCE_IS_NAVIGABLE,
                                false);
            }
            cmd = type.getEditCommand(req);
        }

        return cmd;
    }

    /**
     * Get the notation objects for all semantic objects in the selection. If
     * notation objects are not found or they belong to different diagrams then
     * an empty list will be returned.
     * 
     * @param selection
     *            selection of UML semantic objects.
     * @return list of views or empty list.
     */
    private static List<View> getNotationObjects(TransactionalEditingDomain ed,
            Collection<?> selection) {

        ResourceSet rSet = ed.getResourceSet();
        List<View> views = new ArrayList<View>();
        Model model = null;

        if (rSet != null) {
            CrossReferenceAdapter referenceAdapter =
                    CrossReferenceAdapter.getCrossReferenceAdapter(rSet);

            if (referenceAdapter != null) {
                for (Object sel : selection) {
                    if (sel instanceof EObject) {
                        Set<?> referencers =
                                referenceAdapter
                                        .getInverseReferencers((EObject) sel,
                                                NotationPackage.eINSTANCE
                                                        .getView_Element(),
                                                null);

                        if (referencers != null && !referencers.isEmpty()) {
                            for (Object ref : referencers) {
                                if (ref instanceof View) {
                                    View view = (View) ref;
                                    if (view.getDiagram() != null
                                            && view.getDiagram().getElement() instanceof Model) {

                                        if (sel instanceof Model
                                                && view.getDiagram()
                                                        .getElement() == sel) {
                                            // This is the model so add the
                                            // diagram as the view
                                            views.add(view.getDiagram());
                                            break;
                                        } else {
                                            boolean foundNotation = false;
                                            if (model != null) {
                                                foundNotation =
                                                        model == view
                                                                .getDiagram()
                                                                .getElement();
                                            } else {
                                                model =
                                                        (Model) view
                                                                .getDiagram()
                                                                .getElement();
                                                foundNotation = true;
                                            }

                                            if (foundNotation
                                                    && view
                                                            .getDiagram()
                                                            .getType()
                                                            .equals("Business Object Model")) { //$NON-NLS-1$
                                                views.add(view);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return views.size() == selection.size() ? views
                : new ArrayList<View>(0);
    }

    /**
     * Copy command. This will use the clipboard functionality of serializing
     * the objects and then deserializing on copy (doesn't place the copy in
     * clipboard).
     * 
     * @author njpatel
     * 
     */
    private static class CopyCommand extends AbstractTransactionalCommand {

        private final View parentNode;

        private final List<?> objects;

        private final Point location;

        private final IMapMode mapMode;

        /**
         * Copy command.
         * 
         * @param domain
         *            transactional editing domain
         * @param parentNode
         *            target notation object
         * @param objects
         *            notation objects to copy
         */
        public CopyCommand(TransactionalEditingDomain domain, View parentNode,
                List<?> objects, IMapMode mapMode, Point location) {
            super(domain, Messages.DropCommandFactory_copy_title,
                    getWorkspaceFiles(parentNode));
            this.parentNode = parentNode;
            this.objects = objects;
            this.mapMode = mapMode;
            this.location = location;
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {
            Collection<?> pastedElements = null;
            String copyToString =
                    BOMCopyPasteCommandFactory.getInstance()
                            .copyToString(objects);

            if (copyToString != null) {
                try {
                    pastedElements =
                            BOMCopyPasteCommandFactory.getInstance()
                                    .pasteFromString(parentNode,
                                            copyToString,
                                            mapMode,
                                            location);

                    // Revalidate project references incase a copy object
                    // references a project resource that is not accessible
                    XpdResourcesPlugin.getDefault()
                            .revalidateReferences(parentNode.eResource());
                } catch (Exception e) {
                    return CommandResult.newErrorCommandResult(e);
                }
            } else {
                return CommandResult
                        .newErrorCommandResult(Messages.DropCommandFactory_unableToCopy_shortdesc);
            }

            return CommandResult.newOKCommandResult(pastedElements);
        }
    }

    /**
     * Command wrapper that will check the project dependencies before executing
     * the given command. This will check if the dragged object is from a
     * non-referenced project and ask user whether the reference should be
     * updated.
     * 
     * @author njpatel
     * 
     */
    private static class CheckProjectDependencyWrapperCommand extends
            AbstractTransactionalCommand {

        private final ICommand cmd;

        private final EObject semanticTarget;

        private final Type reference;

        private String operationLabel;

        private boolean isWrappedCommandExecuted = false;

        /**
         * Check project dependency command wrapper.
         * 
         * @param editingDomain
         *            transactional editing domain
         * @param semanticTarget
         *            the target object
         * @param reference
         *            the object being dropped
         * @param cmd
         *            command to execute if the project reference is ok.
         * @param operationLabel
         *            the label to insert in project reference dialog
         */
        public CheckProjectDependencyWrapperCommand(
                TransactionalEditingDomain editingDomain,
                EObject semanticTarget, Type reference, ICommand cmd,
                String operationLabel) {
            super(editingDomain, cmd.getLabel(), cmd.getAffectedFiles());
            this.semanticTarget = semanticTarget;
            this.reference = reference;
            this.cmd = cmd;
            this.operationLabel = operationLabel;
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {

            if (BomUIUtil.checkProjectDependencies(Display.getCurrent()
                    .getActiveShell(),
                    semanticTarget,
                    reference,
                    operationLabel)) {

                if (cmd.canExecute()) {
                    isWrappedCommandExecuted =
                            cmd.execute(monitor, info).isOK();
                }
            } else {
                return CommandResult.newCancelledCommandResult();
            }

            return cmd.getCommandResult();
        }

        @Override
        public boolean canExecute() {
            return cmd.canExecute();
        }

        @Override
        public boolean canUndo() {
            return isWrappedCommandExecuted ? cmd.canUndo() : false;
        }

        @Override
        public boolean canRedo() {
            return isWrappedCommandExecuted ? cmd.canRedo() : false;
        }

    }
}
