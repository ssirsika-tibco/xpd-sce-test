package com.tibco.xpd.bom.test.performance.suite;

import junit.framework.TestCase;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Diagram UI performance test. This test deliberately creates the bom models
 * under the project (as opposed to the BOM special folder) as it intends to
 * test the performance of the Diagram creation and the UI response when
 * creating a large number of elements. If the file was created in a special
 * folder then systems like the indexer would influence the test.
 * 
 * @author njpatel, rgreen
 * 
 */
public class BOMPerfCreateSubClassesTest extends TestCase {

    // private static final String MODEL_FILE = "/test-resources/mymodel.bom";
    private static final String BOM1_FILENAME = "bom1.bom";

    private static final String BOM2_FILENAME = "bom2.bom";

    private IProject project1;

    private IProject project2;

    private IProject project3;

    private WorkingCopy wc;

    private WorkingCopy wc2;

    /*
     * 09Apr10 The threshold was originally set to 10sec which was not a problem
     * in the development env. but on the build system this was regularly
     * breached. Therefore, the value has been raised to 20sec.
     * 
     * 07Sep10 Raised threshold again to 25s
     */
    private static final long PerformanceThreshold = 60000;

    private static final TransactionalEditingDomain ed = XpdResourcesPlugin
            .getDefault().getEditingDomain();;

    @Override
    protected void setUp() throws Exception {
        project1 = TestUtil.createProject("Proj1");
        assertNotNull("Failed to create Project1", project1);
        assertTrue("Cannot access Project1", project1.isAccessible());
        TestUtil.waitForJobs();

        project2 = TestUtil.createProject("Proj2");
        assertNotNull("Failed to create Project2", project2);
        assertTrue("Cannot access Project2", project2.isAccessible());
        TestUtil.waitForJobs();

        project3 = TestUtil.createProject("Proj3");
        assertNotNull("Failed to create Project3", project3);
        assertTrue("Cannot access Project3", project3.isAccessible());
        TestUtil.waitForJobs();

    }

    public void testCreateSubClasses() {
        // Do three tests so that we can compare and take an average
        createSubClassesTest(project1);
        TestUtil.waitForJobs();
        createSubClassesTest(project2);
        TestUtil.waitForJobs();
        createSubClassesTest(project3);
        TestUtil.waitForJobs();
    }

    public void createSubClassesTest(IProject project) {

        // Create and Open a BOM diagram
        BOMTestUtils.createBOMdiagram(project.getName(), BOM1_FILENAME);

        // Create a second diagram that will contain the superclass
        // of all the classes we create in bom1
        BOMTestUtils.createBOMdiagram(project.getName(), BOM2_FILENAME);

        // Create the superclass
        wc2 =
                XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(project.getFile(BOM2_FILENAME));

        try {

            final Class superClass =
                    (createClasses((Package) wc2.getRootElement(), 1))[0];

            // Now create the classes
            wc =
                    XpdResourcesPlugin.getDefault()
                            .getWorkingCopy(project.getFile(BOM1_FILENAME));

            long timeStartCreateClasses = System.currentTimeMillis();

            for (int x = 0; x < 200; x++) {
                try {
                    Model mdl = (Model) wc.getRootElement();
                    final Class subClass = (createClasses(mdl, 1))[0];

                    RecordingCommand cmd = new RecordingCommand(ed) {
                        @Override
                        protected void doExecute() {
                            UMLFactory f = UMLFactory.eINSTANCE;
                            Generalization g1 = f.createGeneralization();
                            g1.setGeneral(superClass);
                            g1.setSpecific(subClass);
                            // TestUtil.waitForValidatior();
                        }
                    };
                    ed.getCommandStack().execute(cmd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            long timeEndCreateClasses = System.currentTimeMillis();

            long duration = timeEndCreateClasses - timeStartCreateClasses;
            System.out.println("Duration = " + duration + "ms");

            if (duration > PerformanceThreshold) {
                System.out.println(String
                        .format("Test with project '%s' failed", project));
            } else {
                System.out.println(String
                        .format("Test with project '%s' passed", project));
            }

            String errMessage =
                    "Threshold exceeded: duration=" + duration + " threshold="
                            + PerformanceThreshold;
            assertTrue(errMessage, duration < PerformanceThreshold);

            // wc.save();
            // wc2.save();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void tearDown() throws Exception {
        System.out.println("teardown");
        TestUtil.removeProject(project1.getName());
        TestUtil.removeProject(project2.getName());
        TestUtil.removeProject(project3.getName());

        super.tearDown();
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
        IClientContext cc =
                ClientContextManager.getInstance().getClientContextFor(parent);
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();

        IElementType pckType =
                ElementTypeRegistry.getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getPackage(), cc);
        // int before = parent.getPackagedElements().size();
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
                    new CreateElementRequest(ed, parent, classType,
                            UMLPackage.eINSTANCE.getPackage_PackagedElement());
            createreq.setClientContext(cc);

            // create class i
            ICommand cmd1 = pckType.getEditCommand(createreq);
            TestCase.assertTrue("Unexpected unexecutable command for class creation",
                    cmd1.canExecute());
            stack.execute(cmd1, new NullProgressMonitor(), null);
            // TestCase.assertEquals("Failed to create class " + (i + 1), before
            // + 1 + i, parent.getPackagedElements().size());
            result[i] = (Class) cmd1.getCommandResult().getReturnValue();
        }
        // TestCase.assertEquals("Failed to create given number of classes",
        // before + number, parent.getPackagedElements().size());
        return result;
    }

}
