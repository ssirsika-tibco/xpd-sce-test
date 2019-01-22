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
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;

/**
 * Tests related to re-parenting diagram elements. For example, move a class
 * into a package.
 * 
 * @author Rob Green
 */
public class ReParentTest extends TestCase {

    private static final String PROJECT_NAME = "ReParentTest";

    private static final String BOM_FILE_NAME = "test.bom";

    private IProgressMonitor prog = new NullProgressMonitor();

    MoveRequest req;

    /**
     * Test scenario:
     * <ol>
     * <li>Create a class C1
     * <li>Create a package P1
     * <li>check if all elements has 'views' created
     * <li>move C1 into P1
     * <li>check if there is no view without model
     * </ol>
     * <i>Notes:</i><br>
     * The BOM editor have to be open in order to create notation elements for
     * newly created uml (domain) elements. First two point use utility methods.
     * 
     * @throws ExecutionException
     * 
     */
    public void testMoveClass() throws Exception {
        IFile file =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME).getFile(BOM_FILE_NAME);
        AbstractGMFWorkingCopy wc =
                (AbstractGMFWorkingCopy) XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(file);
        Model model = (Model) wc.getRootElement();

        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        IClientContext cc =
                ClientContextManager.getInstance().getClientContextFor(model);
        TransactionalEditingDomain ted =
                TransactionUtil.getEditingDomain(model);

        BOMTestUtils.createClasses(model, 1);

        Class C1 = (Class) model.getPackagedElements().get(0);

        BOMTestUtils.createPackages(model, 1);

        Package P1 = (Package) model.getPackagedElements().get(1);

        // Create a MoveRequest to reparent C1 with P1
        req = new MoveRequest(ted, P1, C1);

        IElementType pckType =
                ElementTypeRegistry.getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getPackage(), cc);

        ICommand cmd1 = pckType.getEditCommand(req);

        assertTrue("Unexpected unexecutable command for association creation",
                cmd1.canExecute());
        stack.execute(cmd1, prog, null);

        // Model should have one pacakged element P1
        TestCase.assertEquals("Incorrect number of packaged elements on Canvas",
                1,
                model.getPackagedElements().size());

        // P1 should have one pacakaged element C2
        TestCase.assertEquals("Incorrect number of packaged elements in P1",
                1,
                P1.getPackagedElements().size());

        validateView(P1, C1);

        // Now move the C1 back to main canvas
        req = new MoveRequest(ted, model, C1);

        IElementType modelType =
                ElementTypeRegistry.getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getModel(), cc);

        cmd1 = modelType.getEditCommand(req);

        assertTrue("Unexpected unexecutable command for association creation",
                cmd1.canExecute());
        stack.execute(cmd1, prog, null);

        // Model should have two packaged elements P1 and C1
        TestCase.assertEquals("Incorrect number of packaged elements on Canvas",
                2,
                model.getPackagedElements().size());

        // P1 should have no packaged elements
        TestCase.assertEquals("Incorrect number of packaged elements in P1",
                0,
                P1.getPackagedElements().size());

        validateView(P1, C1);

        // Delete P1 and P2
        // destroy the association
        DestroyElementRequest deps =
                new DestroyElementRequest(TransactionUtil.getEditingDomain(P1),
                        P1, false);
        ICommand cmd =
                ElementTypeRegistry.getInstance().getElementType(model)
                        .getEditCommand(deps);
        assertTrue(cmd.canExecute());
        stack.execute(cmd, prog, null);

        deps =
                new DestroyElementRequest(TransactionUtil.getEditingDomain(C1),
                        C1, false);
        cmd =
                ElementTypeRegistry.getInstance().getElementType(model)
                        .getEditCommand(deps);
        assertTrue(cmd.canExecute());
        stack.execute(cmd, prog, null);

        // P1 should have no packaged elements
        TestCase.assertEquals("Incorrect number of packaged elements in model",
                0,
                model.getPackagedElements().size());

    }

    /**
     * Test scenario:
     * <ol>
     * <li>Create a class P1
     * <li>Create a package P2
     * <li>check if all elements has 'views' created
     * <li>move P2 into P1
     * <li>check if there is no view without model
     * </ol>
     * <i>Notes:</i><br>
     * The BOM editor have to be open in order to create notation elements for
     * newly created uml (domain) elements. First two point use utility methods.
     * 
     * @throws ExecutionException
     * 
     */
    public void testMovePackage() throws Exception {
        IFile file =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME).getFile(BOM_FILE_NAME);
        AbstractGMFWorkingCopy wc =
                (AbstractGMFWorkingCopy) XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(file);
        Model model = (Model) wc.getRootElement();

        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        IClientContext cc =
                ClientContextManager.getInstance().getClientContextFor(model);
        TransactionalEditingDomain ted =
                TransactionUtil.getEditingDomain(model);

        BOMTestUtils.createPackages(model, 2);

        Package P1 = (Package) model.getPackagedElements().get(0);
        Package P2 = (Package) model.getPackagedElements().get(1);

        // Create a MoveRequest to reparent C1 with P1
        req = new MoveRequest(ted, P1, P2);

        IElementType pckType =
                ElementTypeRegistry.getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getPackage(), cc);

        ICommand cmd1 = pckType.getEditCommand(req);

        assertTrue("Unexpected unexecutable command for association creation",
                cmd1.canExecute());
        stack.execute(cmd1, prog, null);

        // Model should have one pacakged element P1
        TestCase.assertEquals("Incorrect number of packaged elements on Canvas",
                1,
                model.getPackagedElements().size());

        // P1 should have one pacakaged element P1
        TestCase.assertEquals("Incorrect number of packaged elements in P1",
                1,
                P1.getPackagedElements().size());

        validateView(P1, P2);

        // Now move the P2 back to main canvas
        req = new MoveRequest(ted, model, P2);

        IElementType modelType =
                ElementTypeRegistry.getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getModel(), cc);

        cmd1 = modelType.getEditCommand(req);

        assertTrue("Unexpected unexecutable command for association creation",
                cmd1.canExecute());
        stack.execute(cmd1, prog, null);

        // Model should have two packaged elements P1 and C1
        TestCase.assertEquals("Incorrect number of packaged elements on Canvas",
                2,
                model.getPackagedElements().size());

        // P1 should have no packaged elements
        TestCase.assertEquals("Incorrect number of packaged elements in P1",
                0,
                P1.getPackagedElements().size());

        validateView(P1, P2);

        // Delete P1 and P2
        // destroy the association
        DestroyElementRequest deps =
                new DestroyElementRequest(TransactionUtil.getEditingDomain(P1),
                        P1, false);
        ICommand cmd =
                ElementTypeRegistry.getInstance().getElementType(model)
                        .getEditCommand(deps);
        assertTrue(cmd.canExecute());
        stack.execute(cmd, prog, null);

        deps =
                new DestroyElementRequest(TransactionUtil.getEditingDomain(P2),
                        P2, false);
        cmd =
                ElementTypeRegistry.getInstance().getElementType(model)
                        .getEditCommand(deps);
        assertTrue(cmd.canExecute());
        stack.execute(cmd, prog, null);

        // P1 should have no packaged elements
        TestCase.assertEquals("Incorrect number of packaged elements in model",
                0,
                model.getPackagedElements().size());

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
