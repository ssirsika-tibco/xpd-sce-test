package com.tibco.xpd.bom.test.performance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
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
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

public class BOMPerformanceCreatePackagesTest extends TestCase {

    // private static final String MODEL_FILE = "/test-resources/mymodel.bom";
    private static final String PROJECT1_NAME = "Project1";
    private static final String BOM_FILENAME = "test.bom";
    private IProject project1;
    private WorkingCopy wc;
    private static final String OUTPUT_DIR = "c:\\BOMTestOutput";

    public void testCreateClasses() {

        // Do three tests so that we can compare and take an average
        for (int i = 0; i < 3; i++) {
            createPackagesTest();
        }

    }

    public void createPackagesTest() {

        System.out.println("==> createPackagesTest"
                + System.currentTimeMillis());
        project1 = TestUtil.createProject(PROJECT1_NAME);
        assertNotNull("Failed to create Project1", project1);
        assertTrue("Cannot access Project1", project1.isAccessible());

        // Create and Open a BOM diagram
        BOMTestUtils.createBOMdiagram(project1.getProject().getName(),
                BOM_FILENAME);

        // Open file for output
        String fileName = OUTPUT_DIR + "\\createPackagesTest_"
                + System.currentTimeMillis() + ".txt";
        File file = new File(fileName);
        FileWriter fw = null;

        try {
            file.createNewFile();
            fw = new FileWriter(file);

            // Now create the classes
            wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                    project1.getFile(BOM_FILENAME));

            long timeStartCreateClasses = System.currentTimeMillis();

            fw.write(String.valueOf(timeStartCreateClasses) + "\n");

            for (int x = 0; x < 200; x++) {
                try {
                    createPackages((Package) wc.getRootElement(), 1);
                    fw.write(String.valueOf(System.currentTimeMillis()) + "\n");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            fw.flush();
            fw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        TestUtil.removeProject(PROJECT1_NAME);
        System.out.println("<== createPackagesTest"
                + System.currentTimeMillis());

    }

    @Override
    protected void setUp() throws Exception {
        // Create two projects
        System.out.println("==> setup: " + System.currentTimeMillis());

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
                    "Unexpected unexecutable command for package creation",
                    cmd1.canExecute());
            stack.execute(cmd1, new NullProgressMonitor(), null);
//            TestCase.assertEquals("Failed to create package " + (i + 1), before
//                    + 1 + i, parent.getPackagedElements().size());
            result[i] = (Package) cmd1.getCommandResult().getReturnValue();
        }
//        TestCase.assertEquals("Failed to create given number of package",
//                before + number, parent.getPackagedElements().size());
        return result;
    }

}
