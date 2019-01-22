package com.tibco.xpd.bom.qa.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditor;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

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
        URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(),
                false);

        IRunnableWithProgress op = new BOMTestUtils.CreateBOMWorkspaceModifyOperation(
                uri);

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
        URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(),
                true);

        IRunnableWithProgress op = new BOMTestUtils.CreateBOMWorkspaceModifyOperation(
                uri);

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

        protected void execute(IProgressMonitor monitor) throws CoreException,
                InterruptedException {

            diagram = UMLDiagramEditorUtil.createDiagram(uri, monitor, null);
            if (diagram != null) {
                try {
                    boolean wasOpened = UMLDiagramEditorUtil
                            .openDiagram(diagram);
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
        TransactionalEditingDomain ted = TransactionUtil
                .getEditingDomain(parent);
        IClientContext cc = ClientContextManager.getInstance()
                .getClientContextFor(parent);
        IOperationHistory stack = PlatformUI.getWorkbench()
                .getOperationSupport().getOperationHistory();

        IElementType pckType = ElementTypeRegistry.getInstance()
                .getElementType(UMLPackage.eINSTANCE.getPackage(), cc);
        int before = parent.getPackagedElements().size();
        Class[] result = new Class[number];
        // create classes
        for (int i = 0; i < number; i++) {
            CreateElementRequest createreq;
            IElementType classType = ElementTypeRegistry.getInstance()
                    .getElementType(UMLPackage.eINSTANCE.getClass_(), cc);

            // request creation of class i
            createreq = new CreateElementRequest(ted, parent, classType,
                    UMLPackage.eINSTANCE.getPackage_PackagedElement());
            createreq.setClientContext(cc);

            // create class i
            ICommand cmd1 = pckType.getEditCommand(createreq);
            TestCase.assertTrue(
                    "Unexpected unexecutable command for class creation", cmd1 //$NON-NLS-1$
                            .canExecute());
            stack.execute(cmd1, new NullProgressMonitor(), null);
            TestCase.assertEquals("Failed to create class " + (i + 1), before //$NON-NLS-1$
                    + 1 + i, parent.getPackagedElements().size());
            result[i] = (Class) cmd1.getCommandResult().getReturnValue();
        }
        TestCase.assertEquals("Failed to create given number of classes", //$NON-NLS-1$
                before + number, parent.getPackagedElements().size());
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
        TransactionalEditingDomain ted = TransactionUtil
                .getEditingDomain(eObject);

        IClientContext cc = ClientContextManager.getInstance()
                .getClientContextFor(eObject);
        IOperationHistory stack = PlatformUI.getWorkbench()
                .getOperationSupport().getOperationHistory();

        IElementType pckType = ElementTypeRegistry.getInstance()
                .getElementType(eClass, cc);
        SetRequest setreq;

        setreq = new SetRequest(ted, eObject, UMLPackage.eINSTANCE
                .getNamedElement_Name(), name);
        setreq.setClientContext(cc);

        // rename the Package

        ICommand cmd1 = pckType.getEditCommand(setreq);
        TestCase.assertTrue(
                "Unexpected unexecutable command for renaming the package", //$NON-NLS-1$
                cmd1.canExecute());
        stack.execute(cmd1, new NullProgressMonitor(), null);

    }

    public static void set(EObject eObject, EClass eClass, EAttribute attr,
            Object value) throws ExecutionException {
        TransactionalEditingDomain ted = TransactionUtil
                .getEditingDomain(eObject);

        IClientContext cc = ClientContextManager.getInstance()
                .getClientContextFor(eObject);
        IOperationHistory stack = PlatformUI.getWorkbench()
                .getOperationSupport().getOperationHistory();

        IElementType pckType = ElementTypeRegistry.getInstance()
                .getElementType(eClass, cc);
        SetRequest setreq;

        setreq = new SetRequest(ted, eObject, attr, value);
        setreq.setClientContext(cc);

        // rename the Package

        ICommand cmd1 = pckType.getEditCommand(setreq);
        TestCase.assertTrue(
                "Unexpected unexecutable command for renaming the package", //$NON-NLS-1$
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
        TransactionalEditingDomain ted = TransactionUtil
                .getEditingDomain(parent);
        IClientContext cc = ClientContextManager.getInstance()
                .getClientContextFor(parent);
        IOperationHistory stack = PlatformUI.getWorkbench()
                .getOperationSupport().getOperationHistory();
        IElementType pckType = ElementTypeRegistry.getInstance()
                .getElementType(UMLPackage.eINSTANCE.getPackage(), cc);

        int before = parent.getPackagedElements().size();
        Package[] result = new Package[number];
        // create packages
        for (int i = 0; i < number; i++) {
            CreateElementRequest createreq;

            // request creation of package i
            createreq = new CreateElementRequest(ted, parent, pckType,
                    UMLPackage.eINSTANCE.getPackage_PackagedElement());
            createreq.setClientContext(cc);

            // create package i
            ICommand cmd1 = pckType.getEditCommand(createreq);
            TestCase.assertTrue(
                    "Unexpected unexecutable command for package creation", //$NON-NLS-1$
                    cmd1.canExecute());
            stack.execute(cmd1, new NullProgressMonitor(), null);
            TestCase.assertEquals("Failed to create package " + (i + 1), before //$NON-NLS-1$
                    + 1 + i, parent.getPackagedElements().size());
            result[i] = (Package) cmd1.getCommandResult().getReturnValue();
        }
        TestCase.assertEquals("Failed to create given number of package", //$NON-NLS-1$
                before + number, parent.getPackagedElements().size());
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
     * TODO: does not work!
     */
    public static PrimitiveType[] createPrimitiveTypes(Package parent, int number)
            throws Exception {
        TransactionalEditingDomain ted = TransactionUtil
                .getEditingDomain(parent);
        IClientContext cc = ClientContextManager.getInstance()
                .getClientContextFor(parent);
        IOperationHistory stack = PlatformUI.getWorkbench()
                .getOperationSupport().getOperationHistory();
        IElementType pckType = ElementTypeRegistry.getInstance()
                .getElementType(UMLPackage.eINSTANCE.getPackage(), cc);

        int before = parent.getPackagedElements().size();
        PrimitiveType[] result = new PrimitiveType[number];
        
        // create primitive types
        
        for (int i = 0; i < number; i++) {
            CreateElementRequest createreq;
            IElementType ptType = ElementTypeRegistry.getInstance()
            .getElementType(UMLPackage.eINSTANCE.getPrimitiveType(), cc);

            // request creation of a primitive type i
            createreq = new CreateElementRequest(ted, parent, ptType
                    );
            
            EClass x = UMLPackage.eINSTANCE.getPrimitiveType();
            
            createreq.setClientContext(cc);

            // create package i
            ICommand cmd1 = pckType.getEditCommand(createreq);
            TestCase.assertTrue(
                    "Unexpected unexecutable command for package creation", //$NON-NLS-1$
                    cmd1.canExecute());
            stack.execute(cmd1, new NullProgressMonitor(), null);
            TestCase.assertEquals("Failed to create package " + (i + 1), before //$NON-NLS-1$
                    + 1 + i, parent.getPackagedElements().size());
            result[i] = (PrimitiveType) cmd1.getCommandResult().getReturnValue();
        }
        TestCase.assertEquals("Failed to create given number of package", //$NON-NLS-1$
                before + number, parent.getPackagedElements().size());
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
        CreateRequest req = new CreateUnspecifiedTypeRequest(types,
                new PreferencesHint("TestPrefHint")); //$NON-NLS-1$

        IEditorPart editor = getEditor(bomFileName);

        if (editor instanceof UMLDiagramEditor) {
            GraphicalViewer viewer = (GraphicalViewer) editor
                    .getAdapter(GraphicalViewer.class);
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
            ICommand editCommand2 = ElementTypeRegistry.getInstance()
                    .getElementType(el).getEditCommand(gmfreq);
            TestCase.assertTrue(editCommand2.canExecute());

        } else {
            TestCase.fail("Invalid editor: " + editor); //$NON-NLS-1$
        }
    }

    /**
     * Find open editor for filename
     * 
     * @return open editor or null if not found
     */
    public static IEditorPart getEditor(String filename) {
        IEditorPart editor;
        editor = null;
        IEditorReference[] eds = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage()
                .getEditorReferences();
        for (IEditorReference er : eds) {
            try {
                IEditorInput ei = er.getEditorInput();
                if (ei instanceof IFileEditorInput) {
                    if (((IFileEditorInput) ei).getFile().getName().equals(
                            filename)) {
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

}
