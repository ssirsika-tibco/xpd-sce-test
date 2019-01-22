/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.dnd;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.FileModificationValidator;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandUtilities;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.PopupMenuCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.menus.PopupMenu;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;

/**
 * Drop menu manager for the BOM editor. This will provide functionality to
 * create the popup menus during a drop operation.
 * 
 * @author njpatel
 * 
 */
public final class DropMenuManager {

    /**
     * Drop menu option.
     * 
     * @author njpatel
     * 
     */
    public interface MenuOption {
        /**
         * Get the menu option label
         * 
         * @return label
         */
        String getLabel();

        /**
         * Get the image for the menu option
         * 
         * @return <code>Image</code>
         */
        Image getImage();

        /**
         * Get command to execute this menu option.
         * 
         * @param ed
         *            transactional editing domain
         * @param semanticTarget
         *            drop semantic target
         * @param editPart
         *            host edit part, can be <code>null</code>.
         * @param request
         *            drop request
         * @return <code>ICommand</code>, or <code>null</code> if command cannot
         *         be created for the drop request.
         */
        ICommand getCommand(TransactionalEditingDomain ed,
                EObject semanticTarget, IGraphicalEditPart editPart,
                DropObjectsRequest request);
    }

    private static final DropMenuManager INSTANCE = new DropMenuManager();

    /**
     * Drop menu manager - private constructor, use {@link #getInstance()}.
     */
    private DropMenuManager() {
        // Private constructor
    }

    public static final DropMenuManager getInstance() {
        return INSTANCE;
    }

    /**
     * Get the create view option (for user defined diagrams).
     * 
     * @return menu option
     * @since 3.3
     */
    public MenuOption getCreateViewOption() {
        return new MenuOption() {

            public ICommand getCommand(TransactionalEditingDomain ed,
                    EObject semanticTarget, IGraphicalEditPart editPart,
                    DropObjectsRequest request) {
                return DropCommandManager
                        .createCreateViewCommand(semanticTarget,
                                ed,
                                editPart,
                                request);
            }

            public Image getImage() {
                return Activator.getDefault().getImageRegistry()
                        .get(BOMImages.DIAGRAM);
            }

            public String getLabel() {
                return "Create View";
            }

        };
    }

    /**
     * Get move menu option.
     * 
     * @return menu option
     */
    public MenuOption getMoveOption() {
        return new MenuOption() {

            public ICommand getCommand(TransactionalEditingDomain ed,
                    EObject semanticTarget, IGraphicalEditPart editPart,
                    DropObjectsRequest request) {
                return DropCommandManager
                        .createMoveObjectsCommand(semanticTarget,
                                ed,
                                editPart,
                                request);
            }

            public Image getImage() {
                return PlatformUI.getWorkbench().getSharedImages()
                        .getImage(ISharedImages.IMG_TOOL_CUT);
            }

            public String getLabel() {
                return Messages.DropCommandFactory_moveObjects_menu;
            }

        };
    }

    /**
     * Get copy menu option.
     * 
     * @return menu option
     */
    public MenuOption getCopyOption() {
        return new MenuOption() {

            public ICommand getCommand(TransactionalEditingDomain ed,
                    EObject semanticTarget, IGraphicalEditPart editPart,
                    DropObjectsRequest request) {
                return DropCommandManager
                        .createCopyObjectsCommand(semanticTarget,
                                editPart,
                                ed,
                                request);
            }

            public Image getImage() {
                return PlatformUI.getWorkbench().getSharedImages()
                        .getImage(ISharedImages.IMG_TOOL_COPY);
            }

            public String getLabel() {
                return Messages.DropCommandFactory_copyObjects_menu;
            }

        };
    }

    /**
     * Get set as superclass menu option.
     * 
     * @return menu option
     */
    public MenuOption getSuperclassOption() {
        return new MenuOption() {

            public ICommand getCommand(TransactionalEditingDomain ed,
                    EObject semanticTarget, IGraphicalEditPart editPart,
                    DropObjectsRequest request) {
                ICommand cmd = null;
                if (semanticTarget instanceof Classifier
                        && !request.getObjects().isEmpty()
                        && request.getObjects().get(0) instanceof Classifier) {
                    cmd =
                            DropCommandManager
                                    .createGeneralizationCommand(getContext(semanticTarget),
                                            ed,
                                            semanticTarget,
                                            (EObject) request.getObjects()
                                                    .get(0));
                }
                return cmd;
            }

            public Image getImage() {
                return ExtendedImageRegistry.getInstance().getImage(Activator
                        .getDefault().getImageRegistry()
                        .get(BOMImages.GENERALIZATION));
            }

            public String getLabel() {
                return Messages.DropCommandFactory_superclass_menu;
            }

        };
    }

    /**
     * Get set as <code>Property</code> menu option.
     * 
     * @return menu option
     */
    public MenuOption getPropertyOption() {
        return new MenuOption() {

            public ICommand getCommand(TransactionalEditingDomain ed,
                    EObject semanticTarget, IGraphicalEditPart editPart,
                    DropObjectsRequest request) {
                ICommand cmd = null;
                if (semanticTarget != null && request.getObjects() != null
                        && !request.getObjects().isEmpty()) {
                    cmd =
                            DropCommandManager
                                    .createPropertyCommand(getContext(semanticTarget),
                                            ed,
                                            semanticTarget,
                                            (EObject) request.getObjects()
                                                    .get(0));
                }
                return cmd;
            }

            public Image getImage() {
                return ExtendedImageRegistry.getInstance().getImage(Activator
                        .getDefault().getImageRegistry()
                        .get(BOMImages.PROPERTY));
            }

            public String getLabel() {
                return Messages.DropCommandFactory_property_menu;
            }
        };
    }

    /**
     * Get create <code>Association</code> menu option.
     * 
     * @return menu option
     */
    public MenuOption getAssociationOption() {
        return new MenuOption() {

            public ICommand getCommand(TransactionalEditingDomain ed,
                    EObject semanticTarget, IGraphicalEditPart editPart,
                    DropObjectsRequest request) {
                ICommand cmd = null;
                if (ed != null && semanticTarget != null
                        && request.getObjects() != null
                        && !request.getObjects().isEmpty()) {
                    cmd =
                            DropCommandManager
                                    .createAssociationCommand(getContext(semanticTarget),
                                            ed,
                                            semanticTarget,
                                            (EObject) request.getObjects()
                                                    .get(0));
                }
                return cmd;
            }

            public Image getImage() {
                return Activator.getDefault().getImageRegistry()
                        .get(BOMImages.ASSOCIATION);
            }

            public String getLabel() {
                return Messages.DropCommandFactory_association_menu;
            }
        };
    }

    /**
     * Get create subclass option.
     * 
     * @return
     */
    public MenuOption getSubclassOption() {
        return new MenuOption() {

            public ICommand getCommand(TransactionalEditingDomain ed,
                    EObject semanticTarget, IGraphicalEditPart editPart,
                    DropObjectsRequest request) {
                ICommand cmd = null;

                if (semanticTarget instanceof Package) {
                    cmd =
                            new CreateSubclassCommand(ed,
                                    (Package) semanticTarget, editPart, request);
                }

                return cmd;
            }

            public Image getImage() {
                return Activator.getDefault().getImageRegistry()
                        .get(BOMImages.CLASS);
            }

            public String getLabel() {
                return Messages.DropMenuManager_createSubclass_menu;
            }

        };
    }

    /**
     * Get the popup menu command with the given options.
     * 
     * @param shell
     *            parent <code>Shell</code>
     * @param editingDomain
     *            transactional editing domain
     * @param semanticTarget
     *            the semantic target of the drop
     * @param request
     *            drop object request
     * @param editPart
     *            host edit part, can be <code>null</code>
     * @param options
     *            menu options
     * @return <code>PopupMenuCommand</code>.
     */
    public PopupMenuCommand getMenuCommand(Shell shell,
            TransactionalEditingDomain editingDomain, EObject semanticTarget,
            DropObjectsRequest request, IGraphicalEditPart editPart,
            List<MenuOption> options) {

        return new DropMenuCommand(shell, editingDomain, semanticTarget,
                request, editPart, options);
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

    /**
     * Drop menu command that presents users with options during a drop.
     * 
     * @author njpatel
     * 
     */
    private class DropMenuCommand extends PopupMenuCommand {
        private final EObject semanticTarget;

        private final TransactionalEditingDomain ed;

        private Command optionCmd;

        private final DropObjectsRequest request;

        private final IGraphicalEditPart editPart;

        /**
         * Drop menu.
         * 
         * @param parentShell
         *            parent <code>Shell</code>
         * @param ed
         *            transactional editing domain
         * @param semanticTarget
         *            drop target
         * @param request
         *            drop object request
         * @param editPart
         *            host edit part
         * @param options
         *            menu options
         */
        public DropMenuCommand(Shell parentShell,
                TransactionalEditingDomain ed, EObject semanticTarget,
                DropObjectsRequest request, IGraphicalEditPart editPart,
                List<MenuOption> options) {
            super(Messages.DropMenuManager_drop_title, parentShell);
            this.ed = ed;
            this.semanticTarget = semanticTarget;
            this.request = request;
            this.editPart = editPart;
            setPopupMenu(new PopupMenu(options, new LabelProvider() {
                @Override
                public String getText(Object element) {
                    if (element instanceof MenuOption) {
                        return ((MenuOption) element).getLabel();
                    }
                    return super.getText(element);
                }

                @Override
                public Image getImage(Object element) {
                    if (element instanceof MenuOption) {
                        return ((MenuOption) element).getImage();
                    }
                    return super.getImage(element);
                }
            }));
        }

        @Override
        protected CommandResult doExecuteWithResult(
                IProgressMonitor progressMonitor, IAdaptable info)
                throws ExecutionException {
            CommandResult result =
                    super.doExecuteWithResult(progressMonitor, info);

            if (result != null && result.getStatus().isOK()) {
                Object returnValue = result.getReturnValue();

                if (returnValue instanceof MenuOption) {
                    ICommand command =
                            ((MenuOption) returnValue).getCommand(ed,
                                    semanticTarget,
                                    editPart,
                                    request);

                    if (command != null) {
                        optionCmd = new ICommandProxy(command);

                        IStatus status = validateAffectedFiles(optionCmd);

                        if (status.isOK() && optionCmd.canExecute()) {
                            optionCmd.execute();
                        } else {
                            result = new CommandResult(status);
                        }
                    }
                }
            }

            return result;
        }

        @Override
        protected CommandResult doUndoWithResult(
                IProgressMonitor progressMonitor, IAdaptable info)
                throws ExecutionException {
            if (optionCmd != null && optionCmd.canUndo()) {
                IStatus status = validateAffectedFiles(optionCmd);

                if (!status.isOK()) {
                    return new CommandResult(status);
                }

                optionCmd.undo();
            }
            return super.doUndoWithResult(progressMonitor, info);
        }

        @Override
        protected CommandResult doRedoWithResult(
                IProgressMonitor progressMonitor, IAdaptable info)
                throws ExecutionException {
            if (optionCmd != null && CommandUtilities.canRedo(optionCmd)) {
                IStatus status = validateAffectedFiles(optionCmd);

                if (!status.isOK()) {
                    return new CommandResult(status);
                }

                optionCmd.redo();
            }
            return super.doRedoWithResult(progressMonitor, info);
        }

        /**
         * Validate the files that are affected by the given command.
         * 
         * @param command
         * @return validation status
         */
        private IStatus validateAffectedFiles(Command command) {
            Collection<?> affectedFiles =
                    CommandUtilities.getAffectedFiles(command);
            int fileCount = affectedFiles.size();
            if (fileCount > 0) {
                return FileModificationValidator
                        .approveFileModification((IFile[]) affectedFiles
                                .toArray(new IFile[fileCount]));
            }
            return Status.OK_STATUS;
        }
    }

    /**
     * Create subclass command.
     * 
     * @author njpatel
     * 
     */
    private class CreateSubclassCommand extends AbstractTransactionalCommand {

        private final Package container;

        private final IGraphicalEditPart editPart;

        private final DropObjectsRequest request;

        public CreateSubclassCommand(TransactionalEditingDomain domain,
                Package container, IGraphicalEditPart editPart,
                DropObjectsRequest request) {
            super(domain, Messages.DropMenuManager_createSubclass_title,
                    getWorkspaceFiles(container));
            this.container = container;
            this.editPart = editPart;
            this.request = request;
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {
            IClientContext context = getContext(container);

            IElementType type =
                    ElementTypeRegistry.getInstance()
                            .getElementType(UMLPackage.eINSTANCE.getClass_(),
                                    context);
            IElementType pkgType =
                    ElementTypeRegistry.getInstance().getElementType(container);

            if (type != null && pkgType != null) {
                if (editPart != null) {
                    CreateViewRequest req =
                            CreateViewRequestFactory
                                    .getCreateShapeRequest(type, editPart
                                            .getDiagramPreferencesHint());
                    Point location = request.getLocation();
                    for (Object obj : request.getObjects()) {

                        if (obj instanceof Type) {
                            if (!BomUIUtil
                                    .checkProjectDependencies(Display
                                            .getCurrent().getActiveShell(),
                                            container,
                                            (Type) obj,
                                            Messages.DropMenuManager_checkProjectDependency_operation_label)) {
                                // Project dependency not set so cancel
                                // operation
                                return CommandResult
                                        .newCancelledCommandResult();
                            }
                        }

                        if (location != null) {
                            req.setLocation(location);
                        }

                        Command command = editPart.getCommand(req);

                        if (command != null && command.canExecute()) {
                            command.execute();
                            List<?> viewDescriptors = req.getViewDescriptors();

                            for (Object desc : viewDescriptors) {
                                ViewDescriptor vDesc = (ViewDescriptor) desc;

                                Class semanticObj =
                                        (Class) vDesc.getElementAdapter()
                                                .getAdapter(Class.class);

                                if (semanticObj != null) {
                                    // Create generalization
                                    ICommand cmd =
                                            DropCommandManager
                                                    .createGeneralizationCommand(context,
                                                            getEditingDomain(),
                                                            semanticObj,
                                                            (EObject) obj);
                                    IStatus status = cmd.execute(monitor, info);

                                    if (!status.isOK()) {
                                        return CommandResult
                                                .newErrorCommandResult(status
                                                        .getException());
                                    }
                                }
                            }
                        } else {
                            return CommandResult
                                    .newErrorCommandResult(Messages.DropMenuManager_failedToCreateSubclass_shortdesc);
                        }
                    }
                } else {
                    for (Object obj : request.getObjects()) {
                        if (obj instanceof Type) {
                            if (!BomUIUtil
                                    .checkProjectDependencies(Display
                                            .getCurrent().getActiveShell(),
                                            container,
                                            (Type) obj,
                                            Messages.DropMenuManager_checkProjectDependency_operation_label)) {
                                // Project dependency not set so cancel
                                // operation
                                return CommandResult
                                        .newCancelledCommandResult();
                            }
                        }
                        CreateElementRequest req =
                                new CreateElementRequest(getEditingDomain(),
                                        container, type);
                        req.setClientContext(context);

                        if (pkgType != null) {
                            ICommand cmd = pkgType.getEditCommand(req);
                            IStatus status = cmd.execute(monitor, info);

                            if (status.isOK()) {
                                // Add subclass
                                EObject newElement = req.getNewElement();

                                if (newElement instanceof Class) {
                                    cmd =
                                            DropCommandManager
                                                    .createGeneralizationCommand(context,
                                                            getEditingDomain(),
                                                            newElement,
                                                            (EObject) obj);
                                    status = cmd.execute(monitor, info);

                                    if (!status.isOK()) {
                                        return CommandResult
                                                .newErrorCommandResult(status
                                                        .getException());
                                    }
                                }
                            } else {
                                return CommandResult
                                        .newErrorCommandResult(status
                                                .getException());
                            }
                        }
                    }
                }
            }

            return CommandResult.newOKCommandResult();
        }
    }
}
