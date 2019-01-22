package com.tibco.xpd.bom.test.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.ShapeStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.commands.ClassCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.edit.commands.PropertyCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditor;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.modeler.diagram.view.factories.ClassViewFactory;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.internal.navigator.actions.BOMEditActionProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.wc.InvalidFileException;

/*
 *This class was moved to a separate plug-in (and is now built to jar),
 * as there was a problem to compile it when used from n2 test feature in the unpacked form.
 *
 */
public class BOMTestUtils {

    /**
     * Create BOM diagram and open the editor
     * 
     * @param projectName
     * @param BOMFileName
     * @return
     */
    static public IFile createBOMdiagram(String projectName, String BOMFileName) {

        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProject project = workspace.getRoot().getProject(projectName);
        IFile file = project.getFile(BOMFileName);
        URI uri =
                URI.createPlatformResourceURI(file.getFullPath().toString(),
                        false);
        IRunnableWithProgress op =
                new BOMTestUtils.CreateBOMWorkspaceModifyOperation(uri);

        try {
            op.run(new NullProgressMonitor());
        } catch (Exception e) {
            e.printStackTrace();
            TestCase.fail(e.getMessage());
        }
        return file;
    }

    /**
     * Create BOM diagram and open the editor
     * 
     * @param specialFolderName
     * @param BOMFileName
     * @return
     */
    static public IFile createBOMdiagram(SpecialFolder specialFolder,
            String BOMFileName) {
        IFile file = specialFolder.getFolder().getFile(BOMFileName);
        URI uri =
                URI.createPlatformResourceURI(file.getFullPath().toString(),
                        true);

        IRunnableWithProgress op =
                new BOMTestUtils.CreateBOMWorkspaceModifyOperation(uri);

        try {
            op.run(new NullProgressMonitor());
        } catch (Exception e) {
            e.printStackTrace();
            TestCase.fail(e.getMessage());
        }
        return file;
    }

    public static final class CreateBOMWorkspaceModifyOperation extends
            WorkspaceModifyOperation {

        URI uri = null;

        Resource diagram = null;

        public CreateBOMWorkspaceModifyOperation(URI uri) {
            super();
            this.uri = uri;
        }

        @Override
        protected void execute(IProgressMonitor monitor) throws CoreException,
                InterruptedException {

            diagram = UMLDiagramEditorUtil.createDiagram(uri, monitor, null);
            if (diagram != null) {
                try {
                    boolean wasOpened =
                            UMLDiagramEditorUtil.openDiagram(diagram);
                    TestCase.assertTrue(wasOpened);
                } catch (PartInitException e) {
                    System.out.println("Error"); //$NON-NLS-1$
                    TestCase.fail(e.getMessage());
                }
            }
        }
    }

    /**
     * Create a number classes in given package. It validate if the creation was
     * successful. Each class will be created in separate command.
     * 
     * @param parent
     *            where to create the classes
     * @param number
     *            of classes to create
     * @return array of newly created classes
     * @throws Exception
     */
    public static Class[] createClasses(Package parent, int number)
            throws Exception {
        TransactionalEditingDomain ted =
                TransactionUtil.getEditingDomain(parent);
        IClientContext cc =
                ClientContextManager.getInstance().getClientContextFor(parent);
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();

        IElementType pckType =
                ElementTypeRegistry.getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getPackage(), cc);
        int before = parent.getPackagedElements().size();
        Class[] result = new Class[number];
        // create classes
        for (int i = 0; i < number; i++) {
            CreateElementRequest createreq;
            IElementType classType =
                    ElementTypeRegistry.getInstance()
                            .getElementType(UMLPackage.eINSTANCE.getClass_(),
                                    cc);

            // request creation of class i
            createreq =
                    new CreateElementRequest(ted, parent, classType,
                            UMLPackage.eINSTANCE.getPackage_PackagedElement());
            createreq.setClientContext(cc);

            // create class i
            ICommand cmd1 = pckType.getEditCommand(createreq);
            TestCase.assertTrue("Unexpected unexecutable command for class creation", //$NON-NLS-1$
                    cmd1.canExecute());
            stack.execute(cmd1, new NullProgressMonitor(), null);
            TestCase.assertEquals("Failed to create class " + (i + 1), before //$NON-NLS-1$
                    + 1 + i, parent.getPackagedElements().size());
            result[i] = (Class) cmd1.getCommandResult().getReturnValue();
        }
        TestCase.assertEquals("Failed to create given number of classes", //$NON-NLS-1$
                before + number,
                parent.getPackagedElements().size());
        return result;
    }

    /**
     * Changes the name of the given association.
     * 
     * @param eObject
     * @param name
     * @throws ExecutionException
     */
    public static void changeAssociationName(EObject eObject, String name)
            throws ExecutionException {
        changeName(eObject, UMLPackage.eINSTANCE.getAssociation(), name);
    }

    /**
     * Changes the name of the given class.
     * 
     * @param eObject
     * @param name
     * @throws ExecutionException
     */
    public static void changeClassName(EObject eObject, String name)
            throws ExecutionException {
        changeName(eObject, UMLPackage.eINSTANCE.getClass_(), name);
    }

    /**
     * Changes the name of the given property.
     * 
     * @param eObject
     * @param name
     * @throws ExecutionException
     */
    public static void changePropertyName(EObject eObject, String name)
            throws ExecutionException {
        changeName(eObject, UMLPackage.eINSTANCE.getProperty(), name);
    }

    /**
     * Changes the name of the given package.
     * 
     * @param eObject
     * @param name
     * @throws ExecutionException
     */
    public static void changePackageName(EObject eObject, String name)
            throws ExecutionException {
        changeName(eObject, UMLPackage.eINSTANCE.getPackage(), name);
    }

    /**
     * Changes the name of the given primitive type.
     * 
     * @param eObject
     * @param name
     * @throws ExecutionException
     */
    public static void changePrimitiveTypeName(EObject eObject, String name)
            throws ExecutionException {
        changeName(eObject, UMLPackage.eINSTANCE.getPrimitiveType(), name);
    }

    /**
     * Changes the name of the given emf object. The class has to be specified
     * as well.
     * 
     * @param eObject
     * @param eClass
     * @param name
     * @throws ExecutionException
     */
    public static void changeName(EObject eObject, EClass eClass, String name)
            throws ExecutionException {
        TransactionalEditingDomain ted =
                TransactionUtil.getEditingDomain(eObject);

        IClientContext cc =
                ClientContextManager.getInstance().getClientContextFor(eObject);
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();

        IElementType pckType =
                ElementTypeRegistry.getInstance().getElementType(eClass, cc);
        SetRequest setreq;

        setreq =
                new SetRequest(ted, eObject,
                        UMLPackage.eINSTANCE.getNamedElement_Name(), name);
        setreq.setClientContext(cc);

        // rename the Package

        ICommand cmd1 = pckType.getEditCommand(setreq);
        TestCase.assertTrue("Unexpected unexecutable command for renaming the package", //$NON-NLS-1$
                cmd1.canExecute());
        stack.execute(cmd1, new NullProgressMonitor(), null);

    }

    public static void set(EObject eObject, EClass eClass, EAttribute attr,
            Object value) throws ExecutionException {
        TransactionalEditingDomain ted =
                TransactionUtil.getEditingDomain(eObject);

        IClientContext cc =
                ClientContextManager.getInstance().getClientContextFor(eObject);
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();

        IElementType pckType =
                ElementTypeRegistry.getInstance().getElementType(eClass, cc);
        SetRequest setreq;

        setreq = new SetRequest(ted, eObject, attr, value);
        setreq.setClientContext(cc);

        // rename the Package

        ICommand cmd1 = pckType.getEditCommand(setreq);
        TestCase.assertTrue("Unexpected unexecutable command for renaming the package", //$NON-NLS-1$
                cmd1.canExecute());
        stack.execute(cmd1, new NullProgressMonitor(), null);

    }

    /**
     * Create a number sub-packages in given package. It validate if the
     * creation was successful. Each package will be created in separate
     * command.
     * 
     * @param parent
     *            where to create the packages
     * @param number
     *            of classes to create
     * @return array of newly created packages
     * @throws Exception
     */
    public static Package[] createPackages(Package parent, int number)
            throws Exception {
        TransactionalEditingDomain ted =
                TransactionUtil.getEditingDomain(parent);
        IClientContext cc =
                ClientContextManager.getInstance().getClientContextFor(parent);
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        IElementType pckType =
                ElementTypeRegistry.getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getPackage(), cc);

        int before = parent.getPackagedElements().size();
        Package[] result = new Package[number];
        // create packages
        for (int i = 0; i < number; i++) {
            CreateElementRequest createreq;

            // request creation of package i
            createreq =
                    new CreateElementRequest(ted, parent, pckType,
                            UMLPackage.eINSTANCE.getPackage_PackagedElement());
            createreq.setClientContext(cc);

            // create package i
            ICommand cmd1 = pckType.getEditCommand(createreq);
            TestCase.assertTrue("Unexpected unexecutable command for package creation", //$NON-NLS-1$
                    cmd1.canExecute());
            stack.execute(cmd1, new NullProgressMonitor(), null);
            TestCase.assertEquals("Failed to create package " + (i + 1), before //$NON-NLS-1$
                    + 1 + i, parent.getPackagedElements().size());
            result[i] = (Package) cmd1.getCommandResult().getReturnValue();
        }
        TestCase.assertEquals("Failed to create given number of package", //$NON-NLS-1$
                before + number,
                parent.getPackagedElements().size());
        return result;
    }

    /**
     * Create a number primitive types in given package. It validate if the
     * creation was successful. Each package will be created in separate
     * command.
     * 
     * @param parent
     *            where to create the primitive types
     * @param number
     *            of primitive types to create
     * @return array of newly created primitive types
     * @throws Exception
     * 
     *             TODO: does not work!
     */
    public static PrimitiveType[] createPrimitiveTypes(Package parent,
            int number) throws Exception {
        TransactionalEditingDomain ted =
                TransactionUtil.getEditingDomain(parent);
        IClientContext cc =
                ClientContextManager.getInstance().getClientContextFor(parent);
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        IElementType pckType =
                ElementTypeRegistry.getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getPackage(), cc);

        int before = parent.getPackagedElements().size();
        PrimitiveType[] result = new PrimitiveType[number];

        // create primitive types

        for (int i = 0; i < number; i++) {
            CreateElementRequest createreq;
            IElementType ptType =
                    ElementTypeRegistry
                            .getInstance()
                            .getElementType(UMLPackage.eINSTANCE.getPrimitiveType(),
                                    cc);

            // request creation of a primitive type i
            createreq = new CreateElementRequest(ted, parent, ptType);

            EClass x = UMLPackage.eINSTANCE.getPrimitiveType();

            createreq.setClientContext(cc);

            // create package i
            ICommand cmd1 = pckType.getEditCommand(createreq);
            TestCase.assertTrue("Unexpected unexecutable command for package creation", //$NON-NLS-1$
                    cmd1.canExecute());
            stack.execute(cmd1, new NullProgressMonitor(), null);
            TestCase.assertEquals("Failed to create package " + (i + 1), before //$NON-NLS-1$
                    + 1 + i, parent.getPackagedElements().size());
            result[i] =
                    (PrimitiveType) cmd1.getCommandResult().getReturnValue();
        }
        TestCase.assertEquals("Failed to create given number of package", //$NON-NLS-1$
                before + number,
                parent.getPackagedElements().size());
        return result;
    }

    /**
     * Create a class in the editor of the given bom file. If the editor is not
     * found then test will fail.
     * 
     * @param bomFileName
     */
    public static void createClassInEditor(String bomFileName) {

        List<IElementType> types = new ArrayList<IElementType>();
        types.add(UMLElementTypes.Class_1002);
        CreateRequest req =
                new CreateUnspecifiedTypeRequest(types, new PreferencesHint(
                        "TestPrefHint")); //$NON-NLS-1$

        IEditorPart editor = getEditor(bomFileName);

        if (editor instanceof UMLDiagramEditor) {
            GraphicalViewer viewer =
                    (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
            RootEditPart rep = viewer.getRootEditPart();
            EditPart ep = (EditPart) rep.getChildren().get(0);

            Command cmd = ep.getCommand(req);
            TestCase.assertTrue(cmd.canExecute());
            viewer.getEditDomain().getCommandStack().execute(cmd);

            TestCase.assertEquals(1, ep.getChildren().size());
            TestCase.assertTrue(editor.isDirty());
            editor.doSave(new NullProgressMonitor());

            View v = (View) ((EditPart) ep.getChildren().get(0)).getModel();
            EObject el = v.getElement();

            TestCase.assertTrue(el instanceof org.eclipse.uml2.uml.Class);

            IEditCommandRequest gmfreq = new DestroyElementRequest(el, false);
            ICommand editCommand2 =
                    ElementTypeRegistry.getInstance().getElementType(el)
                            .getEditCommand(gmfreq);
            TestCase.assertTrue(editCommand2.canExecute());

        } else {
            TestCase.fail("Invalid editor: " + editor); //$NON-NLS-1$
        }
    }

    /**
     * Create a class in the editor of the given bom file. If the editor is not
     * found then test will fail.
     * 
     * @param bomFileName
     */
    public static Class createClassInEditor2(String bomFileName) {

        List<IElementType> types = new ArrayList<IElementType>();
        types.add(UMLElementTypes.Class_1002);
        CreateRequest req =
                new CreateUnspecifiedTypeRequest(types, new PreferencesHint(
                        "TestPrefHint")); //$NON-NLS-1$

        IEditorPart editor = getEditor(bomFileName);

        if (editor instanceof UMLDiagramEditor) {
            GraphicalViewer viewer =
                    (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
            RootEditPart rep = viewer.getRootEditPart();
            EditPart ep = (EditPart) rep.getChildren().get(0);

            Command cmd = ep.getCommand(req);
            TestCase.assertTrue(cmd.canExecute());
            viewer.getEditDomain().getCommandStack().execute(cmd);

            TestCase.assertEquals(1, ep.getChildren().size());
            TestCase.assertTrue(editor.isDirty());
            editor.doSave(new NullProgressMonitor());

            View v = (View) ((EditPart) ep.getChildren().get(0)).getModel();
            EObject el = v.getElement();

            TestCase.assertTrue(el instanceof org.eclipse.uml2.uml.Class);

            IEditCommandRequest gmfreq = new DestroyElementRequest(el, false);
            ICommand editCommand2 =
                    ElementTypeRegistry.getInstance().getElementType(el)
                            .getEditCommand(gmfreq);
            TestCase.assertTrue(editCommand2.canExecute());

            return (Class) el;

        } else {
            TestCase.fail("Invalid editor: " + editor); //$NON-NLS-1$
        }
        return null;
    }

    /**
     * Find open editor for filename
     * 
     * @return open editor or null if not found
     */
    public static IEditorPart getEditor(String filename) {
        IEditorPart editor;
        editor = null;
        IEditorReference[] eds =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getEditorReferences();
        for (IEditorReference er : eds) {
            try {
                IEditorInput ei = er.getEditorInput();
                if (ei instanceof IFileEditorInput) {
                    if (((IFileEditorInput) ei).getFile().getName()
                            .equals(filename)) {
                        editor = er.getEditor(true);
                    }
                }
            } catch (PartInitException e) {
                e.printStackTrace();
                TestCase.fail(e.getMessage());
            }
        }
        return editor;
    }

    public static EObject doLoadModel(IFile file, EditingDomain editingDomain)
            throws InvalidFileException {
        EObject eo = null;
        if (file.exists()) {
            URI uri =
                    URI.createPlatformResourceURI(file.getFullPath().toString(),
                            true);
            LoadModelRunnable runnable =
                    new LoadModelRunnable(editingDomain, uri);
            try {
                ((TransactionalEditingDomain) editingDomain)
                        .runExclusive(runnable);
                eo = (EObject) runnable.getResult();
            } catch (InterruptedException e) {
                // ignore eo==null
            }

            if (eo == null) {
                throw new InvalidFileException();
            }
        }

        return eo;
    }

    /**
     * Load the root model from the given resource.
     */
    static public class LoadModelRunnable extends RunnableWithResult.Impl {

        private final URI uri;

        private final EditingDomain editingDomain;

        /**
         * Load model from the resource.
         * 
         * @param resource
         */
        public LoadModelRunnable(final EditingDomain editingDomain, URI uri) {
            this.uri = uri;
            this.editingDomain = editingDomain;
        }

        @Override
        public void run() {
            Resource resource = editingDomain.loadResource(uri.toString());
            if (resource != null && resource.getErrors().isEmpty()) {
                EList<EObject> contents = resource.getContents();

                for (EObject eObj : contents) {
                    if (eObj instanceof Profile) {
                        setResult(eObj);
                        break;
                    }
                }
            }
        }
    }

    /**
     * This method compares the names, the styles and the layout constraints of
     * two GMF-EObjects.
     * 
     * @param editingDomain
     * @param originalEObject
     * @param copiedEObject
     */
    static public void validateNamesAndViews(EditingDomain editingDomain,
            Namespace originalEObject, Namespace copiedEObject) {
        Node nodeOriginal = getView(originalEObject, editingDomain);
        TestCase.assertNotNull("The original Object (" //$NON-NLS-1$
                + originalEObject.getName() + ") has no view.", //$NON-NLS-1$
                nodeOriginal);
        Node nodeCopied = getView(copiedEObject, editingDomain);
        TestCase.assertNotNull("The copied Object (" //$NON-NLS-1$
                + originalEObject.getName() + ") has no view.", //$NON-NLS-1$
                nodeCopied);
        Bounds boundsOriginal = (Bounds) nodeOriginal.getLayoutConstraint();
        EList<?> originalNodesList = nodeOriginal.getStyles();
        ShapeStyle originalShape = (ShapeStyle) originalNodesList.get(0);
        Bounds boundsCopy = (Bounds) nodeCopied.getLayoutConstraint();
        EList<?> s2List = nodeCopied.getStyles();
        ShapeStyle copiedShape = (ShapeStyle) s2List.get(0);
        if (originalNodesList.size() != s2List.size()) {
            TestCase.fail("The number of applied styles of the origin class does not match with the copy."); //$NON-NLS-1$
        }

        if (originalEObject.getName() != null) {
            TestCase.assertTrue("The name of the copied class is not equal to the origin one.", //$NON-NLS-1$
                    copiedEObject.getName().equals(originalEObject.getName()));
        }

        // if (boundsOriginal.getX() != (boundsCopy.getX() - 10)
        // && !(boundsOriginal.getX() == boundsCopy.getX())) {
        // TestCase.fail("The LayoutConstraint (X: original="
        // + boundsOriginal.getX() + ", copy=" + boundsCopy.getX()
        // + ") was not copied properly (EObject="
        // + originalEObject.getName() + ").");
        // }
        // if (boundsOriginal.getY() != (boundsCopy.getY() - 10)
        // && !(boundsOriginal.getY() == boundsCopy.getY())) {
        // TestCase.fail("The LayoutConstraint (X: original="
        // + boundsOriginal.getY() + ", copy=" + boundsCopy.getY()
        // + ") was not copied properly (EObject="
        // + originalEObject.getName() + ").");
        // }
        // if (boundsOriginal.getWidth() != boundsCopy.getWidth()) {
        // TestCase.fail("The LayoutConstraint (width: original="
        // + boundsOriginal.getWidth() + ", copy="
        // + boundsCopy.getWidth()
        // + ") was not copied properly (EObject="
        // + originalEObject.getName() + ").");
        // }
        // if (boundsOriginal.getHeight() != boundsCopy.getHeight()) {
        // TestCase.fail("The LayoutConstraint (width: original="
        // + boundsOriginal.getHeight() + ", copy="
        // + boundsCopy.getHeight()
        // + ") was not copied properly (EObject="
        // + originalEObject.getName() + ").");
        // }
        //
        // if (originalShape != null
        // && copiedShape != null
        // && !originalShape.getDescription().equals(
        // copiedShape.getDescription())) {
        // TestCase.fail("The styles of the Class were not copied properly.");
        // }
        // if (originalShape.getFillColor() != copiedShape.getFillColor()) {
        // TestCase.fail("The styles of the Class were not copied properly.");
        // }
        // if (originalShape.getFontColor() != copiedShape.getFontColor()) {
        // TestCase.fail("The styles of the Class were not copied properly.");
        // }
        // if (originalShape.getFontHeight() != copiedShape.getFontHeight()) {
        // TestCase.fail("The styles of the Class were not copied properly.");
        // }
        // if (originalShape != null
        // && !originalShape.getFontName().equals(
        // copiedShape.getFontName())) {
        // TestCase.fail("The styles of the Class were not copied properly.");
        // }
    }

    /**
     * @param eObject
     * @param editingDomain
     * @return
     */
    @SuppressWarnings("restriction")
    public static Node getView(EObject eObject, EditingDomain editingDomain) {
        ArrayList<EObject> selection = new ArrayList<EObject>();
        selection.add(eObject);
        Collection<?> notation =
                BOMEditActionProvider.getNotationObjects(selection,
                        editingDomain);

        for (Object object : notation) {
            if (object instanceof Node) {
                Node node = (Node) object;
                if (node.getElement() == eObject) {
                    return node;
                }
            }
        }
        return null; // notation.toArray()[0];
    }

    /**
     * Selects a tree item in the project explorer view.
     * 
     * @param segments
     */
    @SuppressWarnings("restriction")
    public static void selectPathInProjectExplorer(Object[] segments) {
        IViewPart explorer = getProjectView();
        if (explorer instanceof CommonNavigator) {
            TreePath selectPath = new TreePath(segments);
            ((CommonNavigator) explorer).setLinkingEnabled(true);
            ISelectionProvider selectionProvider =
                    explorer.getSite().getSelectionProvider();
            selectionProvider.setSelection(new TreeSelection(selectPath));
        } else if (explorer instanceof PackageExplorerPart) {
            TreePath selectPath = new TreePath(segments);
            ((PackageExplorerPart) explorer).setLinkingEnabled(true);
            ISelectionProvider selectionProvider =
                    explorer.getSite().getSelectionProvider();
            selectionProvider.setSelection(new TreeSelection(selectPath));
        } else if (explorer instanceof TreeViewer) {
            TreePath selectPath = new TreePath(segments);
            ISelectionProvider selectionProvider =
                    explorer.getSite().getSelectionProvider();
            selectionProvider.setSelection(new TreeSelection(selectPath));
        }

    }

    /**
     * Finds and return the property view.
     * 
     * @return
     */
    @SuppressWarnings("nls")
    public static IViewPart getProjectView() {
        IViewPart viewPart =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage()
                        .findView("org.eclipse.ui.navigator.ProjectExplorer");
        if (viewPart == null) {
            viewPart =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage()
                            .findView("org.eclipse.jdt.ui.PackageExplorer");
        }
        return viewPart;
    }

    /**
     * Close the Welcome Page to see what happens on the workbench window.
     */
    public static void closeWelcomePage() {
        try {
            IViewReference welcomeViewRef =
                    PlatformUI
                            .getWorkbench()
                            .getActiveWorkbenchWindow()
                            .getActivePage()
                            .findViewReference("org.eclipse.ui.internal.introview"); //$NON-NLS-1$
            if (welcomeViewRef != null) {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().hideView(welcomeViewRef);
            }
        } catch (Exception ignore) {
        }
    }

    /**
     * @author rassisi
     * 
     */
    static private final class CreateAndSelectChildAction extends
            CreateChildAction {

        public Object getResult() {
            return result;
        }

        /**
         * @param workbenchPart
         * @param selection
         * @param descriptor
         */
        private CreateAndSelectChildAction(IWorkbenchPart workbenchPart,
                ISelection selection, Object descriptor) {
            super(workbenchPart, selection, descriptor);
        }

        Object result;

        @Override
        public void run() {
            if (this.command != null && this.command.canExecute()) {
                if (getProjectView() != null) {
                    super.run();
                }
            }
        }
    }

    public static void createClass(EObject model, ISelection selection) {
        IEditingDomainItemProvider provider =
                (IEditingDomainItemProvider) XpdResourcesPlugin.getDefault()
                        .getAdapterFactory()
                        .adapt(model, IEditingDomainItemProvider.class);
        if (provider != null) {
            Collection<?> descriptors =
                    provider.getNewChildDescriptors(model, XpdResourcesPlugin
                            .getDefault().getEditingDomain(), null);

            IWorkbenchPart activePart =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().getActivePart();

            if (descriptors != null) {
                // for (Object descriptor : descriptors) {
                // if (descriptor instanceof CommandParameter) {
                // CommandParameter cm = (CommandParameter) descriptor;
                // // if (cm.getValue() instanceof Attribute) {
                // // if (!firstCellVal
                // //
                // .startsWith(Messages.AttributeTable_defaultAttrName_label)) {
                // // ((Attribute) cm.getValue())
                // // .setName(firstCellVal);
            }
            // // CreateAndSelectChildAction action = new
            // CreateAndSelectChildAction(
            // // activePart, selection,
            // // descriptor);
            // // action.run();
            // // retValue = action.getResult();
            // break;

        }
    }

    /**
     * create association in given package between given classes
     * 
     * @param parent
     * @param c1
     * @param c2
     * @return
     * @throws Exception
     */
    public static Association createAssociation(Package parent, Class c1,
            Class c2) throws Exception {
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        IClientContext cc =
                ClientContextManager.getInstance().getClientContextFor(parent);

        Association assoc = null;
        // create association
        {
            IElementType assocType =
                    ElementTypeRegistry
                            .getInstance()
                            .getElementType(UMLPackage.eINSTANCE.getAssociation(),
                                    cc);
            CreateRelationshipRequest createreq =
                    new CreateRelationshipRequest(c1, c2, assocType);
            ICommand cmd1 = assocType.getEditCommand(createreq);

            TestCase.assertTrue("Unexpected unexecutable command for association creation", //$NON-NLS-1$
                    cmd1.canExecute());
            stack.execute(cmd1, new NullProgressMonitor(), null);

            assoc = (Association) cmd1.getCommandResult().getReturnValue();

            TestCase.assertNotNull("Failed to create association", //$NON-NLS-1$
                    assoc.eResource());

            TestCase.assertEquals(c1, assoc.getEndTypes().get(0));
            TestCase.assertEquals(c2, assoc.getEndTypes().get(1));

            TestCase.assertTrue(assoc.getMemberEnds().get(0).getName()
                    .toLowerCase().startsWith(c1.getName().toLowerCase()));
            TestCase.assertTrue(assoc.getMemberEnds().get(1).getName()
                    .toLowerCase().startsWith(c2.getName().toLowerCase()));
        }
        return assoc;
    }

    /**
     * create aggregation in given package between given classes
     * 
     * @param parent
     * @param c1
     * @param c2
     * @return
     * @throws Exception
     */
    public static Association createAggregation(EditingDomain editingDomain,
            Package parent, Class c1, Class c2) throws Exception {
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        IClientContext cc =
                ClientContextManager.getInstance().getClientContextFor(parent);

        Association aggregation = null;
        IElementType assocType =
                ElementTypeRegistry.getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getAssociation(),
                                cc);
        CreateRelationshipRequest createreq =
                new CreateRelationshipRequest(c1, c2, assocType);
        ICommand cmd1 = assocType.getEditCommand(createreq);

        TestCase.assertTrue("Unexpected unexecutable command for aggreagation creation", //$NON-NLS-1$
                cmd1.canExecute());
        stack.execute(cmd1, new NullProgressMonitor(), null);
        aggregation = (Association) cmd1.getCommandResult().getReturnValue();
        TestCase.assertNotNull("Failed to create aggregation", //$NON-NLS-1$
                aggregation.eResource());
        TestCase.assertEquals(c1, aggregation.getEndTypes().get(0));
        TestCase.assertEquals(c2, aggregation.getEndTypes().get(1));
        TestCase.assertTrue(aggregation.getMemberEnds().get(0).getName()
                .toLowerCase().startsWith(c1.getName().toLowerCase()));
        TestCase.assertTrue(aggregation.getMemberEnds().get(1).getName()
                .toLowerCase().startsWith(c2.getName().toLowerCase()));

        // TODO: does not work

        Property prop = aggregation.getMemberEnds().get(0);

        org.eclipse.emf.common.command.Command command =
                SetCommand.create(editingDomain,
                        prop,
                        UMLPackage.Literals.PROPERTY__AGGREGATION,
                        AggregationKind.SHARED_LITERAL);
        TestCase.assertTrue("The command to set the association type to aggregation could not be executed.", //$NON-NLS-1$
                command.canExecute());
        editingDomain.getCommandStack().execute(command);
        return aggregation;
    }

    /**
     * create in given package between given classes
     * 
     * @param parent
     * @param c1
     * @param c2
     * @return
     * @throws Exception
     */
    public static Association createComposition(EditingDomain editingDomain,
            Package parent, Class c1, Class c2) throws Exception {
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        IClientContext cc =
                ClientContextManager.getInstance().getClientContextFor(parent);

        Association composition = null;
        IElementType assocType =
                ElementTypeRegistry
                        .getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getAggregationKind(),
                                cc);
        CreateRelationshipRequest createreq =
                new CreateRelationshipRequest(c1, c2, assocType);
        ICommand cmd1 = assocType.getEditCommand(createreq);
        TestCase.assertTrue("Unexpected unexecutable command for composition creation", //$NON-NLS-1$
                cmd1.canExecute());
        stack.execute(cmd1, new NullProgressMonitor(), null);
        composition = (Association) cmd1.getCommandResult().getReturnValue();

        TestCase.assertNotNull("Failed to create composition", //$NON-NLS-1$
                composition.eResource());
        TestCase.assertEquals(c1, composition.getEndTypes().get(0));
        TestCase.assertEquals(c2, composition.getEndTypes().get(1));
        TestCase.assertTrue(composition.getMemberEnds().get(0).getName()
                .toLowerCase().startsWith(c1.getName().toLowerCase()));
        TestCase.assertTrue(composition.getMemberEnds().get(1).getName()
                .toLowerCase().startsWith(c2.getName().toLowerCase()));

        // TODO: does not work

        Property prop = composition.getMemberEnds().get(0);

        org.eclipse.emf.common.command.Command command =
                SetCommand.create(editingDomain,
                        prop,
                        UMLPackage.eINSTANCE.getAggregationKind(),
                        AggregationKind.COMPOSITE_LITERAL);
        TestCase.assertTrue("The command to set the association type to composition could not be executed.", //$NON-NLS-1$
                command.canExecute());
        editingDomain.getCommandStack().execute(command);
        return composition;
    }

    /**
     * Validate if all supplied objects have at least one notation reference.
     * 
     * @param eos
     *            ...
     */
    public static void validateView(EObject... eos) {
        if (eos == null || eos.length == 0) {
            return;
        }
        CrossReferenceAdapter referenceAdapter =
                CrossReferenceAdapter.getCrossReferenceAdapter(eos[0]
                        .eResource().getResourceSet());

        for (EObject eo : eos) {
            @SuppressWarnings("unchecked")
            Set refs =
                    referenceAdapter.getInverseReferencers(eo,
                            NotationPackage.eINSTANCE.getView_Element(),
                            null);
            TestCase.assertFalse("No notation element found for: " + eo, //$NON-NLS-1$
                    refs.isEmpty());
        }
    }

    public static void createView(WorkingCopy wc, EObject selectedElement) {

        PreferencesHint hint = new PreferencesHint("TestPrefHint"); //$NON-NLS-1$

        String className = UMLDiagramEditor.class.getName();

        CreateViewRequest.ViewDescriptor viewDescriptor =
                new CreateViewRequest.ViewDescriptor(new EObjectAdapter(
                        selectedElement), Node.class, className, hint);

        // <%=importManager.getImportedName(genDiagram.getEditorGen().getPlugin()
        // .
        // getActivatorQualifiedClassName())%>.DIAGRAM_PREFERENCES_HINT);

        View containerView = null;

        if (selectedElement instanceof PackageableElement) {
            PackageableElement p = (PackageableElement) selectedElement;
            containerView = getView(p, wc.getEditingDomain());
        }

        IAdaptable semanticAdapter = new IAdaptable() {

            @Override
            public Object getAdapter(java.lang.Class arg0) {
                return null;
            }
        };

        ClassViewFactory factory = new ClassViewFactory();
        factory.createView(semanticAdapter,
                containerView,
                "TestPrefHint", //$NON-NLS-1$
                0,
                true,
                hint);

        CreateCommand command =
                new CreateCommand(
                        (TransactionalEditingDomain) wc.getEditingDomain(),
                        viewDescriptor, containerView) {

                    @Override
                    protected CommandResult doExecuteWithResult(
                            IProgressMonitor monitor, IAdaptable info)
                            throws ExecutionException {
                        CommandResult result =
                                super.doExecuteWithResult(monitor, info);
                        View view =
                                (View) ((IAdaptable) result.getReturnValue())
                                        .getAdapter(View.class);
                        return result;
                    }

                };

        try {
            OperationHistoryFactory.getOperationHistory().execute(command,
                    new NullProgressMonitor(),
                    null);
        } catch (ExecutionException e) {
            TestCase.fail("Failed to create the view."); //$NON-NLS-1$
        }

    }

    /**
     * @param wc
     */
    private static void createClassWojtek(WorkingCopy wc) {
        DiagramEditor editor = getDiagramEditor(wc);
        List<IElementType> types = new ArrayList<IElementType>(1);
        types.add(UMLElementTypes.Class_1002);
        UnspecifiedTypeCreationTool tool =
                new UnspecifiedTypeCreationTool(types);
        GraphicalViewer gv =
                (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
        Request req = tool.createCreateRequest();
        org.eclipse.gef.commands.Command cmd =
                editor.getDiagramEditPart().getCommand(req);
        gv.getEditDomain().getCommandStack().execute(cmd);
    }

    /**
     * @param wc
     */
    public static void createGMFElement(WorkingCopy wc, IElementType type,
            Rectangle rect) {
        createGMFElement(wc, type, rect.x, rect.y, rect.width, rect.height);
    }

    /**
     * @param wc
     */
    public static void createGMFElement(WorkingCopy wc, IElementType type,
            int x, int y, int width, int height) {
        DiagramEditor editor = getDiagramEditor(wc);
        UnspecifiedTypeCreationTool tool =
                new UnspecifiedTypeCreationTool(Collections.singletonList(type));
        EditDomain editDomain =
                (EditDomain) editor.getDiagramEditPart().getDiagramEditDomain();
        tool.setViewer(editor.getDiagramGraphicalViewer());
        Request request = tool.createCreateRequest();
        ((CreateRequest) request).setLocation(new Point(x, y));
        ((CreateRequest) request).setSize(new Dimension(width, height));
        org.eclipse.gef.commands.Command cmd =
                editor.getDiagramEditPart().getCommand(request);
        editDomain.getCommandStack().execute(cmd);
    }

    /**
     * @param wc
     * @param type
     */
    public static void createSingleGMFElement(WorkingCopy wc, IElementType type) {
        DiagramEditor editor = getDiagramEditor(wc);
        CreationTool tool = new CreationTool(type);
        Request request = tool.createCreateRequest();
        EditDomain editDomain =
                (EditDomain) editor.getDiagramEditPart().getDiagramEditDomain();
        org.eclipse.gef.commands.Command cmd =
                editor.getDiagramEditPart().getCommand(request);
        editDomain.getCommandStack().execute(cmd);
    }

    /**
     * This method provides the most realistic scenario for creating element as
     * children of the editors root edit part.
     * 
     * @param wc
     * @param x
     * @param y
     */
    public static void creatGMFElementByMouse(WorkingCopy wc, IElementType type) {
        DiagramEditor editor = getDiagramEditor(wc);
        UnspecifiedTypeCreationTool tool =
                new UnspecifiedTypeCreationTool(Collections.singletonList(type));
        EditDomain editDomain =
                (EditDomain) editor.getDiagramEditPart().getDiagramEditDomain();
        EditPartViewer viewer = editor.getDiagramEditPart().getViewer();
        Event event = new Event();
        event.x = 0;
        event.y = 0;
        event.button = 1;
        event.count = 1;
        event.display =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
                        .getDisplay();
        event.widget = viewer.getControl();
        MouseEvent mouseEvent = new MouseEvent(event);
        tool.setEditDomain(editDomain);
        tool.activate();
        tool.mouseDoubleClick(mouseEvent, viewer);
    }

    /**
     * @param wc
     */
    public static Object createClass2(WorkingCopy wc) {
        CreateElementRequest req =
                new CreateElementRequest(
                        (TransactionalEditingDomain) wc.getEditingDomain(),
                        wc.getRootElement(), UMLElementTypes.Class_1002);
        ClassCreateCommand classCmd = new ClassCreateCommand(req);

        if (classCmd.canExecute()) {
            try {
                classCmd.execute(new NullProgressMonitor(), null);
                return classCmd.getCommandResult().getReturnValue();
            } catch (ExecutionException e) {
                BOMTestUtils.fail("Fialed to create the class."); //$NON-NLS-1$
            }
        }
        return null;
    }

    /**
     * @param wc
     */
    public static Object createProperty(WorkingCopy wc, Class cls) {
        CreateElementRequest req =
                new CreateElementRequest(
                        (TransactionalEditingDomain) wc.getEditingDomain(),
                        cls, UMLElementTypes.Property_2004);
        PropertyCreateCommand classCmd = new PropertyCreateCommand(req);

        if (classCmd.canExecute()) {
            try {
                classCmd.execute(new NullProgressMonitor(), null);
                return classCmd.getCommandResult().getReturnValue();
            } catch (ExecutionException e) {
                BOMTestUtils.fail("Fialed to create the property."); //$NON-NLS-1$
            }
        }
        return null;
    }

    /**
     * 
     * @param wc
     */
    public static Object createTypedProperty(WorkingCopy wc, Class cls,
            Class type) {
        CreateElementRequest req =
                new CreateElementRequest(
                        (TransactionalEditingDomain) wc.getEditingDomain(),
                        cls, UMLElementTypes.Property_2004);
        req.setParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.TYPE,
                type);
        PropertyCreateCommand classCmd = new PropertyCreateCommand(req);

        if (classCmd.canExecute()) {
            try {
                classCmd.execute(new NullProgressMonitor(), null);
                return classCmd.getCommandResult().getReturnValue();
            } catch (ExecutionException e) {
                BOMTestUtils.fail("Fialed to create the property."); //$NON-NLS-1$
            }
        }
        return null;
    }

    /**
     * @return
     */
    public static DiagramEditor getDiagramEditor(WorkingCopy wc) {
        final IResource resource = wc.getEclipseResources().get(0);
        IWorkbenchPage page =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage();
        IEditorReference[] editors = page.getEditorReferences();
        IEditorPart editorPart = null;
        for (IEditorReference editorReference : editors) {
            String id = editorReference.getId();
            if (id.equals(UMLDiagramEditor.ID)) {
                try {
                    IEditorInput input = editorReference.getEditorInput();
                    if (input instanceof IFileEditorInput) {
                        IFileEditorInput fi = (IFileEditorInput) input;
                        String name1 = fi.getName();
                        if (name1.equals(resource.getName())) {
                            editorPart = editorReference.getEditor(true);
                            break;
                        }
                    }
                } catch (PartInitException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        if (editorPart instanceof DiagramEditor) {
            return (DiagramEditor) editorPart;
        }
        return null;
    }

    /**
     * @author rassisi
     * 
     */
    public enum FailureType {
        REASON_UNKNOWN("REASON UNKNOWN"), //$NON-NLS-1$ 
        CODE_FAILURE("CODE_FAILURE"), //$NON-NLS-1$ 
        FAILURE_OF_THE_TEST("FAILURE_OF_THE_TEST"); //$NON-NLS-1$ 
        @SuppressWarnings("unused")
        private final String message;

        FailureType(String message) {
            this.message = message;
        }
    }

    public static void fail(String message) {
        TestCase.fail(FailureType.CODE_FAILURE.toString() + ": " + message); //$NON-NLS-1$
    }

    public static void fail(FailureType type, String message) {
        TestCase.fail(type.toString() + ": " + message); //$NON-NLS-1$
    }

}
