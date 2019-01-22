package com.tibco.xpd.bom.test.copypaste;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardContentsHelper;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardManager;
import org.eclipse.gmf.runtime.common.ui.util.CustomData;
import org.eclipse.gmf.runtime.common.ui.util.ICustomData;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.render.clipboard.AWTClipboardHelper;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditor;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.clipboard.BOMCopyPasteCommandFactory;
import com.tibco.xpd.bom.resources.ui.internal.navigator.actions.BOMEditActionProvider;
import com.tibco.xpd.bom.test.IConstants;
import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * Abstract class for all copy / paste tests. Two kind of tests ares supported:
 * The first one tests if the copy command can be executed sucessfully, where
 * the second kind expects that the test fails. This is for those tests, where
 * the paste command is not supported, eg. copying a class to a attribute.
 * 
 * @author rassisi
 * 
 */
public abstract class AbstractCopyPasteTest extends TestCase {

    protected static final String MODEL_FILE =
            "/test-resources/test.profile.uml";

    protected String PROJECT_SOURCE_NAME;

    protected String PROJECT_TARGET_NAME;

    protected static final String CONCEPT_FILE_NAME_1 = "c1."
            + IConstants.BOM_FILE_EXTENTION;

    protected static final String CONCEPT_FILE_NAME_2 = "c2."
            + IConstants.BOM_FILE_EXTENTION;

    protected boolean hasTargetProject = false;

    protected WorkingCopy sourceWorkingCopy;

    protected WorkingCopy targetWorkingCopy;

    protected EditingDomain editingDomain;

    protected SpecialFolder conceptsSourceFolder;

    protected SpecialFolder conceptsTargetFolder;

    protected ArrayList<EObject> sourceElements = new ArrayList<EObject>();

    /**
     * 
     */
    protected void doTest() {
        System.out.println(new Date() + " Executing copy/paste test: "
                + this.getClass().getName());
        createBusinessObjectModels();
        createSourceElements();
        createTargetElements();
        treatSource();
        saveSourceModel();
        copy();
        paste(getTarget(), getTargetRootEditPart());
        treatTarget();
        saveTargetModel();
        validateCopies();
    }

    private DiagramEditPart getTargetRootEditPart() {
        DiagramEditPart ep = null;
        UMLDiagramEditor ude = null;
        String path =
                targetWorkingCopy.getRootElement().eResource().getURI()
                        .toPlatformString(true);
        IResource workspaceResource =
                ResourcesPlugin.getWorkspace().getRoot()
                        .findMember(new Path(path));
        if (workspaceResource instanceof IFile) {
            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            IEditorPart editor = null;
            try {
                editor =
                        page.openEditor(new FileEditorInput(
                                (IFile) workspaceResource), UMLDiagramEditor.ID);
            } catch (PartInitException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (editor instanceof UMLDiagramEditor) {
                ude = (UMLDiagramEditor) editor;
                ep = ude.getDiagramEditPart();
            }
        }
        return ep;
    }

    /**
     * 
     */
    protected void doTestPasteShouldFail() {
        createBusinessObjectModels();
        createSourceElements();
        createTargetElements();
        treatSource();
        copy();
        pasteShouldFail(getTarget());
        treatTarget();
        validateCopies();
    }

    /**
     * 
     */
    protected void createBusinessObjectModels() {
        conceptsSourceFolder =
                TestUtil.createSpecialFolder(PROJECT_SOURCE_NAME,
                        "Business Objects",
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        conceptsTargetFolder = conceptsSourceFolder;

        if (!PROJECT_SOURCE_NAME.equals(PROJECT_TARGET_NAME)) {
            conceptsTargetFolder =
                    TestUtil.createSpecialFolder(PROJECT_TARGET_NAME,
                            "Business Objects",
                            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        }

        BOMTestUtils.createBOMdiagram(conceptsSourceFolder.getProject()
                .getName(), conceptsSourceFolder.getLocation() + "/"
                + CONCEPT_FILE_NAME_1);
        sourceWorkingCopy =
                XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(conceptsSourceFolder.getFolder()
                                .getFile(CONCEPT_FILE_NAME_1));
        editingDomain = sourceWorkingCopy.getEditingDomain();
        BOMTestUtils.createBOMdiagram(conceptsTargetFolder.getProject()
                .getName(), conceptsTargetFolder.getLocation() + "/"
                + CONCEPT_FILE_NAME_2);
        targetWorkingCopy =
                XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(conceptsTargetFolder.getFolder()
                                .getFile(CONCEPT_FILE_NAME_2));
    }

    /**
     * @param targetObject
     */
    @SuppressWarnings("restriction")
    protected void paste(EObject targetObject, DiagramEditPart editPart) {
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        ArrayList<EObject> pasteSel = new ArrayList<EObject>();
        pasteSel.add(targetObject);
        final List<EObject> notationsModel =
                BOMEditActionProvider.getNotationObjects(pasteSel,
                        editingDomain);
        ICustomData[] data = getCustomDataFromClipboard();

        IMapMode mm = MapModeUtil.getMapMode(editPart.getFigure());

        if (data != null) {
            ICommand pasteCommand =
                    BOMCopyPasteCommandFactory
                            .getInstance()
                            .createPasteCommand((TransactionalEditingDomain) editingDomain,
                                    notationsModel.get(0),
                                    data,
                                    mm);

            // EMFOperationCommand emfCommand = new EMFOperationCommand(
            // (TransactionalEditingDomain) editingDomain, pasteCommand);
            //
            // assertTrue("The Command to paste the class could not beexecuted",
            // emfCommand.canExecute());
            // emfCommand.execute();

            if (!pasteCommand.canExecute()) {
                BOMTestUtils
                        .fail("The Command to paste the class could not be executed");
            }
            try {
                stack.execute(pasteCommand, new NullProgressMonitor(), null);
                // emfCommand.execute();

            } catch (ExecutionException e) {
                fail("The Command to paste the class failed.");
            }

        } else {
            fail("Clipboard had no data.");
        }
    }

    /**
     * @param targetObject
     */
    @SuppressWarnings("restriction")
    protected void pasteShouldFail(EObject targetObject) {
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        ArrayList<EObject> pasteSel = new ArrayList<EObject>();
        pasteSel.add(targetObject);
        final List<EObject> notationsModel =
                BOMEditActionProvider.getNotationObjects(pasteSel,
                        editingDomain);
        ICustomData[] data = getCustomDataFromClipboard();
        if (data != null) {
            ICommand pasteCommand =
                    BOMCopyPasteCommandFactory
                            .getInstance()
                            .createPasteCommand((TransactionalEditingDomain) editingDomain,
                                    notationsModel.get(0),
                                    data,
                                    null);
            assertFalse("The Command to paste the class could be executed, but it should not",
                    pasteCommand.canExecute());
            try {
                stack.execute(pasteCommand, new NullProgressMonitor(), null);
            } catch (ExecutionException e) {
                fail("The Command to paste the class failed.");
            }
        } else {
            fail("Clipboard had no data.");
        }
    }

    /**
     * Get the custom data from the clipboard.
     * 
     * @return TODO: this code has to be moved in the production code to an
     *         internal package and the visibility has to be changed then to
     *         public to be able to access the code from the test.
     */
    private ICustomData[] getCustomDataFromClipboard() {
        ICustomData[] data = null;

        if (AWTClipboardHelper.getInstance().isImageCopySupported()) {
            CustomData customData =
                    AWTClipboardHelper.getInstance().getCustomData();

            if (customData != null) {
                data = new ICustomData[] { customData };
            }
        }

        if (data == null) {
            data =
                    ClipboardManager
                            .getInstance()
                            .getClipboardData(BOMCopyPasteCommandFactory.DRAWING_SURFACE,
                                    ClipboardContentsHelper.getInstance());

            if (data == null) {
                data =
                        ClipboardManager
                                .getInstance()
                                .getClipboardData(ClipboardManager.COMMON_FORMAT,
                                        ClipboardContentsHelper.getInstance());
            }
        }

        return data;
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setProjectNames();
        BOMTestUtils.closeWelcomePage();
        TestUtil.createProject(PROJECT_SOURCE_NAME);
        if (!PROJECT_SOURCE_NAME.equals(PROJECT_TARGET_NAME)) {
            TestUtil.createProject(PROJECT_TARGET_NAME);
        }
    }

    /**
     * Sets the name of the source and target projects.
     */
    protected void setProjectNames() {
        /*
         * Kapil: Some of the tests failed as we used the same source and target
         * project names for all the tests. This created problem while clearing
         * projects. Hence adding this method which with provide unique project
         * names per test.
         */
        PROJECT_SOURCE_NAME = getClass().getName();
        PROJECT_TARGET_NAME = PROJECT_SOURCE_NAME;
        if (hasTargetProject) {
            PROJECT_TARGET_NAME = PROJECT_TARGET_NAME + "_target";
        }
    }

    /*
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        TestUtil.waitForBuilds(300, 300);
        TestUtil.removeProject(PROJECT_SOURCE_NAME);
        if (!PROJECT_SOURCE_NAME.equals(PROJECT_TARGET_NAME)) {
            TestUtil.removeProject(PROJECT_TARGET_NAME);
        }
        super.tearDown();

    }

    /**
     * @throws Exception
     */
    protected void superTearDown() throws Exception {
        super.tearDown();
    }

    /**
     * 
     */
    protected void saveSourceModel() {
        // IEditorPart editor = PlatformUI.getWorkbench()
        // .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        //
        // if (editor != null) {
        // editor.doSave(new NullProgressMonitor());
        // }
        try {
            sourceWorkingCopy.save();
        } catch (IOException e) {
            fail("Failed to save the source model.");
        }
        TestUtil.waitForJobs(200);
    }

    /**
     * 
     */
    protected void saveTargetModel() {
        // IEditorPart editor = PlatformUI.getWorkbench()
        // .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        //
        // if (editor != null) {
        // editor.doSave(new NullProgressMonitor());
        // }
        try {
            targetWorkingCopy.save();
        } catch (IOException e) {
            fail("Failed to save the target model.");
        }
        TestUtil.waitForJobs(200);
    }

    /**
     * @param copyContext
     */
    @SuppressWarnings("restriction")
    protected void copy() {
        List<EObject> notationObjects =
                BOMEditActionProvider.getNotationObjects(sourceElements,
                        editingDomain);

        ICommand command =
                BOMCopyPasteCommandFactory
                        .getInstance()
                        .createCopyCommand((TransactionalEditingDomain) editingDomain,
                                getCopyContext(),
                                notationObjects);
        IOperationHistory stack =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();
        assertTrue("Copy Command could not be executed", command.canExecute());
        try {
            stack.execute(command, new NullProgressMonitor(), null);
        } catch (ExecutionException e) {
            fail("Execution of the copy command failed.");
        }
    }

    abstract protected void createSourceElements();

    protected void createTargetElements() {
    }

    protected EObject getCopyContext() {
        return sourceElements.get(0);
    }

    abstract protected void treatSource();

    abstract protected void treatTarget();

    abstract protected void validateCopies();

    protected EObject getTarget() {
        return targetWorkingCopy.getRootElement();
    }

    /**
     * @param sourceElement
     */
    protected void addSourceElement(EObject sourceElement) {
        sourceElements.add(sourceElement);
    }

}
