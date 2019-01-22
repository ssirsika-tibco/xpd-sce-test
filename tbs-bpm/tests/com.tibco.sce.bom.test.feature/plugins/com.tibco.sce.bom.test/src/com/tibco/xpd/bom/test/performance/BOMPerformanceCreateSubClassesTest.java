package com.tibco.xpd.bom.test.performance;

import java.io.File;
import java.io.FileWriter;

import junit.framework.TestCase;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
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

public class BOMPerformanceCreateSubClassesTest extends TestCase {

    // private static final String MODEL_FILE = "/test-resources/mymodel.bom";
    private static final String PROJECT1_NAME = "Proj";
    private static final String BOM1_FILENAME = "bom1.bom";
    private static final String BOM2_FILENAME = "bom2.bom";
    private IProject project1;
    private WorkingCopy wc;
    private WorkingCopy wc2;
    private static final String OUTPUT_DIR = "c:\\BOMTestOutput";

    public void testCreateSubClasses() {

        // Do three tests so that we can compare and take an average
        for (int i = 0; i < 5; i++) {
            createSubClassesTest(i);
            //TestUtil.waitForJobs();
        }

    }

    public void createSubClassesTest(int i) {

        System.out.println("==> testCreateSubClasses"
                + System.currentTimeMillis());
        project1 = TestUtil.createProject(PROJECT1_NAME + i);
        assertNotNull("Failed to create Project1", project1);
        assertTrue("Cannot access Project1", project1.isAccessible());

        // Create and Open a BOM diagram
        BOMTestUtils.createBOMdiagram(project1.getProject().getName(),
                BOM1_FILENAME);

        // Create a second diagram that will contain the superclass
        // of all the classes we create in bom1
        BOMTestUtils.createBOMdiagram(project1.getProject().getName(),
                BOM2_FILENAME);

        // Create the superclass
        wc2 = XpdResourcesPlugin.getDefault().getWorkingCopy(
                project1.getFile(BOM2_FILENAME));



        // Open file for output
        String fileName = OUTPUT_DIR + "\\createSubClassesTest_"
                + System.currentTimeMillis() + ".txt";
        File file = new File(fileName);
        FileWriter fw = null;

        try {
            file.createNewFile();
            fw = new FileWriter(file);

            final Class superClass = (createClasses((Package) wc2.getRootElement(), 1))[0];

            
            // Now create the classes
            wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    project1.getFile(BOM1_FILENAME));

            long timeStartCreateClasses = System.currentTimeMillis();

            fw.write(String.valueOf(timeStartCreateClasses) + "\n");


            for (int x = 0; x < 500; x++) {
                try {
                    
                    Model mdl = (Model) wc.getRootElement();
                    
                    final Class subClass = (createClasses(mdl, 1))[0];

                    TransactionalEditingDomain ed = TransactionUtil
                            .getEditingDomain(mdl);

                    RecordingCommand cmd = new RecordingCommand(ed) {
                        @Override
                        protected void doExecute() {
                            UMLFactory f = UMLFactory.eINSTANCE;
                            Generalization g1 = f.createGeneralization();
                            g1.setGeneral(superClass);
                            g1.setSpecific(subClass);
                            //TestUtil.waitForValidatior();
                        }
                    };
                    ed.getCommandStack().execute(cmd);

                    fw.write(String.valueOf(System.currentTimeMillis()) + "\n");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                
                
            }

            wc.save();
            wc2.save();
            
            fw.flush();
            fw.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
        
        //TestUtil.removeProject(PROJECT1_NAME);
        System.out
                .println("<== testCreateSubClasses" + System.currentTimeMillis());

    }

    @Override
    protected void setUp() throws Exception {
        // Create two projects
        System.out.println("==> setup: " + System.currentTimeMillis());


//        IWorkspaceDescription description = ResourcesPlugin.getWorkspace()
//                        .getDescription();
//                
//        if (description != null) {
//             description.setAutoBuilding(false);
//              try {
//                  ResourcesPlugin.getWorkspace().setDescription(description);
//              } catch (CoreException e) {
//                  // TODO Auto-generated catch block
//                  e.printStackTrace();
//              }
//        }
        
        
        // Create the output directory if it doesn't exist already
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }

        System.out.println("<== setup: " + System.currentTimeMillis());
    }

    @Override
    protected void tearDown() throws Exception {
        System.out.println("==> tearDown: " + System.currentTimeMillis());
        super.tearDown();
        System.out.println("<== tearDown: " + System.currentTimeMillis());
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
        // int before = parent.getPackagedElements().size();
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
                    "Unexpected unexecutable command for class creation", cmd1
                            .canExecute());
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
