/**
 * 
 */
package com.tibco.xpd.bom.modeler.tests;

import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;

/**
 * Tests related to associations and the way how they are reated and removed.
 * 
 * @author wzurek
 */
public class AssociationTest extends TestCase {

    private static final String PROJECT_NAME = "AssociationTest";

    private static final String BOM_FILE_NAME = "test.bom";

    private static final int ASSOC_END_SOURCE = 1;

    private static final int ASSOC_END_TARGET = 2;

    private IProgressMonitor prog = new NullProgressMonitor();

    /**
     * Test scenario:
     * <ol>
     * <li><i>create 2 classes</i>
     * <li><i>create association between these two classes</i>
     * <li>check if all elements has 'views' created
     * <li>destroy association
     * <li>check if there is no view without model
     * </ol>
     * <i>Notes:</i><br>
     * The BOM editor have to be open in order to create notation elements for
     * newly created uml (domain) elements. First two point use utility methods.
     * 
     * @throws ExecutionException
     * 
     */
    public void testAssociationCreateRemove() throws Exception {

        IFile file =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME).getFile(BOM_FILE_NAME);
        AbstractGMFWorkingCopy wc =
                (AbstractGMFWorkingCopy) XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(file);
        Model model = (Model) wc.getRootElement();

        BOMTestUtils.createClasses(model, 2);

        Class c1 = (Class) model.getPackagedElements().get(0);
        Class c2 = (Class) model.getPackagedElements().get(1);

        // check the names of the classes
        assertTrue("Invalid name of the class 1 - " + c1.getName(), c1
                .getName().endsWith("1"));
        assertTrue("Invalid name of the class 2 - " + c2.getName(), c2
                .getName().endsWith("2"));

        // create association
        Association assoc = createAssociation(model, c1, c2);

        // validate if all three elements has related notation elements
        // Note: this will fail if the editor is not open
        validateView(c1, c2, assoc);
        validateView(wc.getDiagrams().get(0));

        // validate if we have two nodes and one edge
        Diagram diag = wc.getDiagrams().get(0);
        assertEquals(3, diag.getChildren().size());
        assertEquals(1, diag.getEdges().size());

        // destroy the association
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        DestroyElementRequest deps =
                new DestroyElementRequest(
                        TransactionUtil.getEditingDomain(assoc), assoc, false);
        ICommand cmd =
                ElementTypeRegistry.getInstance().getElementType(model)
                        .getEditCommand(deps);
        assertTrue(cmd.canExecute());
        stack.execute(cmd, prog, null);

        // check if all dependents has been removed:
        // 1. attributes on end classes
        assertEquals(0, c1.getAllAttributes().size());
        assertEquals(0, c2.getAllAttributes().size());
        // 2. notation model
        assertEquals(0, diag.getEdges().size());
        // 3. validate the diagram
        validateView(wc.getDiagrams().get(0));
    }

    /**
     * Test scenario:
     * <ol>
     * <li>Create 3 classes
     * <li>Create association between these Class 1 and Class 2
     * <li>check if all elements has 'views' created
     * <li>create a third class
     * <li>Reorient association. Move Class 2 end to Class 3
     * <li>Reorient association. Move Class 1 end to Class 2
     * <li>check if there is no view without model
     * </ol>
     * <i>Notes:</i><br>
     * The BOM editor have to be open in order to create notation elements for
     * newly created uml (domain) elements. First two point use utility methods.
     * 
     * @throws ExecutionException
     * 
     */
    public void testAssociationReorient() throws Exception {

        IFile file =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME).getFile(BOM_FILE_NAME);
        AbstractGMFWorkingCopy wc =
                (AbstractGMFWorkingCopy) XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(file);
        Model model = (Model) wc.getRootElement();

        BOMTestUtils.createClasses(model, 3);

        Class c1 = (Class) model.getPackagedElements().get(0);
        Class c2 = (Class) model.getPackagedElements().get(1);
        Class c3 = (Class) model.getPackagedElements().get(2);

        // check the names of the classes
        assertTrue("Invalid name of the class 1 - " + c1.getName(), c1
                .getName().endsWith("1"));
        assertTrue("Invalid name of the class 2 - " + c2.getName(), c2
                .getName().endsWith("2"));

        // create association
        Association assoc = createAssociation(model, c1, c2);

        // validate if all three elements has related notation elements
        // Note: this will fail if the editor is not open
        validateView(c1, c2, assoc);
        validateView(wc.getDiagrams().get(0));

        // validate if we have two nodes and one edge
        Diagram diag = wc.getDiagrams().get(0);
        assertEquals(4, diag.getChildren().size());
        assertEquals(1, diag.getEdges().size());

        // Reorient target end Class 2 end to Class 3
        performReorient(model, assoc, c3, c2, ASSOC_END_TARGET);

        // Reorient source end Class 1 to Class 2
        performReorient(model, assoc, c2, c1, ASSOC_END_SOURCE);

        // destroy the association, which should also destro C2 and C3
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        DestroyElementRequest deps =
                new DestroyElementRequest(
                        TransactionUtil.getEditingDomain(assoc), assoc, false);
        ICommand cmd =
                ElementTypeRegistry.getInstance().getElementType(model)
                        .getEditCommand(deps);
        assertTrue(cmd.canExecute());
        stack.execute(cmd, prog, null);

        // check if all dependents has been removed: // 1. attributes on end
        // classes
        assertEquals(0, c2.getAllAttributes().size());
        assertEquals(0, c3.getAllAttributes().size());
        // 2. notation model
        assertEquals(0, diag.getEdges().size());

        // Now destroy c1
        deps =
                new DestroyElementRequest(TransactionUtil.getEditingDomain(c1),
                        c1, false);
        cmd =
                ElementTypeRegistry.getInstance().getElementType(model)
                        .getEditCommand(deps);
        assertTrue(cmd.canExecute());
        stack.execute(cmd, prog, null);

        // 3. validate the diagram
        validateView(wc.getDiagrams().get(0));

    }

    /**
     * Test scenario:
     * <ol>
     * <li><i>create 2 classes</i>
     * <li><i>create association between these two classes</i>
     * <li>check if all elements has 'views' created
     * <li>class (the source of the association)
     * <li>check if there is no view without model
     * </ol>
     * <i>Notes:</i><br>
     * The BOM editor have to be open in order to create notation elements for
     * newly created uml (domain) elements. First two point use utility methods.
     * 
     * @throws ExecutionException
     * 
     */
    public void testAssociationCreateRemoveSourceNode() throws Exception {

        IFile file =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME).getFile(BOM_FILE_NAME);
        AbstractGMFWorkingCopy wc =
                (AbstractGMFWorkingCopy) XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(file);
        Model model = (Model) wc.getRootElement();

        BOMTestUtils.createClasses(model, 2);

        Class c1 = (Class) model.getPackagedElements().get(0);
        Class c2 = (Class) model.getPackagedElements().get(1);

        // check the names of the classes
        assertTrue("Invalid name of the class 1 - " + c1.getName(), c1
                .getName().endsWith("1"));
        assertTrue("Invalid name of the class 2 - " + c2.getName(), c2
                .getName().endsWith("2"));

        // create association
        Association assoc = createAssociation(model, c1, c2);

        // validate if all three elements has related notation elements
        // Note: this will fail if the editor is not open
        validateView(c1, c2, assoc);

        // validate if we have two nodes and one edge
        Diagram diag = wc.getDiagrams().get(0);
        assertEquals(3, diag.getChildren().size());
        assertEquals(1, diag.getEdges().size());

        // destroy the association
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        DestroyElementRequest deps =
                new DestroyElementRequest(TransactionUtil.getEditingDomain(c1),
                        c1, false);
        ICommand cmd1 =
                ElementTypeRegistry.getInstance().getElementType(model)
                        .getEditCommand(deps);
        assertTrue(cmd1.canExecute());
        stack.execute(cmd1, prog, null);

        // check if all dependents has been removed:
        // 1. class and the association
        assertEquals(1, model.getPackagedElements().size());
        // 2. an attribute on the other class
        assertEquals(0, c2.getAllAttributes().size());
        // 3. notation model
        assertEquals(0, diag.getEdges().size());
        assertEquals(2, diag.getChildren().size());
        // 4. validate the diagram
        validateView(wc.getDiagrams().get(0));

    }

    public void testAssociationBetweenPackages() throws Exception {
        IFile file =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME).getFile(BOM_FILE_NAME);
        AbstractGMFWorkingCopy wc =
                (AbstractGMFWorkingCopy) XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(file);
        Model model = (Model) wc.getRootElement();

        Class[] c1s = BOMTestUtils.createClasses(model, 2);
        Class c1 = c1s[0];
        Class cx = c1s[1];

        Package[] p1s = BOMTestUtils.createPackages(model, 1);
        Package p1 = p1s[0];

        Class[] c2s = BOMTestUtils.createClasses(p1, 2);
        Class c2 = c2s[0];
        Class c3 = c2s[1];

        // check the names of the classes
        assertTrue("Invalid name of the class 1 - " + c1.getName(), c1
                .getName().endsWith("1"));
        assertTrue("Invalid name of the class 2 - " + c2.getName(), c2
                .getName().endsWith("1"));
        assertTrue("Invalid name of the class 3 - " + c2.getName(), c3
                .getName().endsWith("2"));

        // create association
        Association assocx = createAssociation(model, c1, cx);
        Association assoc1 = createAssociation(model, c1, c2);
        Association assoc2 = createAssociation(p1, c1, c3);

        // validate if all three elements has related notation elements
        // Note: this will fail if the editor is not open
        validateView(p1, c1, c2, c3);
        validateView(assocx);
        // FIXME: why this doesn't work?
        // validateView(assoc1, assoc2);

        // validate if we have two nodes and one edge
        Diagram diag = wc.getDiagrams().get(0);
        assertEquals(3, diag.getEdges().size());

        // destroy the class 1
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        DestroyElementRequest deps =
                new DestroyElementRequest(TransactionUtil.getEditingDomain(c1),
                        c1, false);
        ICommand cmd1 =
                ElementTypeRegistry.getInstance().getElementType(model)
                        .getEditCommand(deps);
        assertTrue(cmd1.canExecute());
        stack.execute(cmd1, prog, null);

        // check if all dependents has been removed:
        // 1. one class and one package with two classes, without associations
        // should stay
        assertEquals(2, model.getPackagedElements().size());
        assertNotNull(p1.eResource());
        assertEquals(p1.getPackagedElements().size(), 2);
        // 2. an attribute on the other class
        assertEquals(0, c2.getAllAttributes().size());
        assertEquals(0, c3.getAllAttributes().size());
        // 3. notation model
        assertEquals(0, diag.getEdges().size());
        assertEquals(3, diag.getChildren().size());
        // 4. validate the diagram
        validateView(wc.getDiagrams().get(0));
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
    private Association createAssociation(Package parent, Class c1, Class c2)
            throws Exception {
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

            assertTrue("Unexpected unexecutable command for association creation",
                    cmd1.canExecute());
            stack.execute(cmd1, prog, null);

            assoc = (Association) cmd1.getCommandResult().getReturnValue();

            assertNotNull("Failed to create association", assoc.eResource());

            assertEquals(c1, assoc.getEndTypes().get(0));
            assertEquals(c2, assoc.getEndTypes().get(1));

            assertTrue(assoc.getMemberEnds().get(0).getName().toLowerCase()
                    .startsWith(c1.getName().toLowerCase()));
            assertTrue(assoc.getMemberEnds().get(1).getName().toLowerCase()
                    .startsWith(c2.getName().toLowerCase()));
        }
        return assoc;
    }

    /**
     * create association in given package between given classes
     * 
     * @param parent
     * @param assoc
     * @param old
     *            end c1
     * @param new end c2
     * @return
     * @throws Exception
     */
    private Association performReorient(Package parent, Association assoc,
            Class newCl, Class oldCl, int EndType) throws Exception {
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        IClientContext cc =
                ClientContextManager.getInstance().getClientContextFor(parent);
        TransactionalEditingDomain ted =
                TransactionUtil.getEditingDomain(parent);

        IElementType assocType1 =
                ElementTypeRegistry.getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getAssociation(),
                                cc);

        ReorientRelationshipRequest req =
                new ReorientRelationshipRequest(ted, assoc, newCl, oldCl,
                        EndType);

        // ICommand cmd1 = assocType.getEditCommand(req);
        ICommand cmd1 = assocType1.getEditCommand(req);

        assertTrue("Unexpected unexecutable command for association creation",
                cmd1.canExecute());
        stack.execute(cmd1, prog, null);

        if (EndType == ASSOC_END_SOURCE) {
            assertEquals(newCl, assoc.getEndTypes().get(0));
        }

        if (EndType == ASSOC_END_TARGET) {
            assertEquals(newCl, assoc.getEndTypes().get(1));
        }

        return assoc;
    }

    /**
     * Validate if all supplied objects have at least one notation reference.
     * 
     * @param eos
     *            ...
     */
    public void validateView(EObject... eos) {
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
            assertFalse("No notation element found for: " + eo, refs.isEmpty());
        }
    }

    /**
     * Validate if all view elements in the diagram has proper reference to
     * domain objects.
     * 
     * @param diagram
     */
    public void validateView(Diagram diagram) {
        TreeIterator<EObject> contents = diagram.eAllContents();
        while (contents.hasNext()) {
            EObject next = contents.next();
            if (next instanceof View) {
                View v = (View) next;
                assertNotNull("View element doesn't have related domain element - "
                        + v,
                        v.getElement());
                assertNotNull("View element has stale reference to removed domain element - "
                        + v,
                        v.getElement().eResource());
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .resetPerspective();

        TestUtil.createProject(PROJECT_NAME);

        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME);
        assertNotNull(project);
        assertEquals(true, project.isAccessible());

        BOMTestUtils.createBOMdiagram(PROJECT_NAME, BOM_FILE_NAME);
    }

    @Override
    protected void tearDown() throws Exception {
        TestUtil.waitForBuilds(300, 200);
        TestUtil.removeProject(PROJECT_NAME);
        super.tearDown();
    }

}
