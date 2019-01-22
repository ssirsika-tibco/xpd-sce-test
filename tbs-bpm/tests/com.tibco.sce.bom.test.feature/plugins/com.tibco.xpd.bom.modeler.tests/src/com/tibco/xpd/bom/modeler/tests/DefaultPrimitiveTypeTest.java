/**
 * 
 */
package com.tibco.xpd.bom.modeler.tests;

import junit.framework.TestCase;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * @author wzurek
 * 
 */
public class DefaultPrimitiveTypeTest extends TestCase {

    private static final String PROJECT_NAME = "DefaultPrimitiveTypeTest";

    private static final String BOM_FILE_NAME =
            "DefaultPrimitiveTypeTest.test.bom";

    private IFile bom;

    @Override
    protected void setUp() throws Exception {
        IProject pr = TestUtil.createProject(PROJECT_NAME);
        SpecialFolder sf =
                TestUtil.createSpecialFolder(pr,
                        "bom",
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        bom = BOMTestUtils.createBOMdiagram(sf, BOM_FILE_NAME);
    }

    @Override
    protected void tearDown() throws Exception {
        TestUtil.waitForJobs(200);
        TestUtil.removeProject(PROJECT_NAME);
    }

    public void testCreatePrimitiveType() throws Exception {
        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(bom);
        IClientContext cc =
                ClientContextManager.getInstance()
                        .getClientContextFor(wc.getRootElement());
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();

        IElementType pckType =
                ElementTypeRegistry.getInstance()
                        .getElementType(wc.getRootElement());
        IElementType ptType =
                ElementTypeRegistry
                        .getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getPrimitiveType(),
                                cc);

        CreateElementRequest req =
                new CreateElementRequest(ed, wc.getRootElement(), ptType);
        ICommand ecmd = pckType.getEditCommand(req);
        assertTrue(ecmd.canExecute());
        stack.execute(ecmd, new NullProgressMonitor(), null);

        PrimitiveType pt =
                (PrimitiveType) ((Model) wc.getRootElement())
                        .getPackagedElements().get(0);
        assertEquals("Text", pt.getGenerals().get(0).getName());
    }

    public void testCreateAttribute() throws Exception {
        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(bom);
        IClientContext cc =
                ClientContextManager.getInstance()
                        .getClientContextFor(wc.getRootElement());
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();

        IElementType pckType =
                ElementTypeRegistry.getInstance()
                        .getElementType(wc.getRootElement());
        IElementType classType =
                ElementTypeRegistry.getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getClass_(), cc);
        IElementType propType =
                ElementTypeRegistry.getInstance()
                        .getElementType(UMLPackage.eINSTANCE.getProperty(), cc);

        {
            CreateElementRequest req =
                    new CreateElementRequest(ed, wc.getRootElement(), classType);
            ICommand ecmd1 = pckType.getEditCommand(req);
            assertTrue(ecmd1.canExecute());
            stack.execute(ecmd1, new NullProgressMonitor(), null);

        }
        Class cls =
                (Class) ((Model) wc.getRootElement()).getPackagedElements()
                        .get(0);

        CreateElementRequest req = new CreateElementRequest(ed, cls, propType);
        ICommand ecmd1 = classType.getEditCommand(req);
        assertTrue(ecmd1.canExecute());
        stack.execute(ecmd1, new NullProgressMonitor(), null);

        Property prop = cls.getOwnedAttributes().get(0);

        assertEquals("Text", prop.getType().getName());
    }
}
