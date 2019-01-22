/**
 */
package com.tibco.xpd.bom.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import junit.framework.TestCase;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditor;
import com.tibco.xpd.bom.modeler.tests.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * <h1>Description</h1> All test cases should extend this class and implement
 * the IBOMTestCase.<br>
 * A method must be implemented to define the expected issues.<br>
 * <h2>command line parameters</h2> -testissues : all occured issues will be
 * send to the console for later definitions <h1>Example (without all comments):
 * </h1> <span style="color: rgb(0, 153, 0);"><br>
 * public class ClassDuplicateNameRuleTest extends BOMTestCase implements
 * IBOMTestCase{<br>
 * <br>
 * &nbsp;&nbsp;&nbsp;public void setConceptName(){<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;// can be empty<br>
 * <br>
 * <br>
 * &nbsp;&nbsp;&nbsp;public void setProjectName(){<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;super.setProjectName(
 * "com.tibco.xpd.bom.validator.test.PropertyNameDuplicateRuleTest");<br>
 * <br>
 * <br>
 * &nbsp;&nbsp;&nbsp;protected boolean doClearBuild(){<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return true;<br>
 * &nbsp;&nbsp;&nbsp;}<br>
 * <br>
 * &nbsp;&nbsp;&nbsp;protected void defineMarkerIssues() {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;// addExpectedMarkerIssues(1, new
 * String[]{"propertyNameDuplicate.issue"});<br>
 * &nbsp;&nbsp;&nbsp;}<br>
 * 
 * <br>
 * &nbsp;&nbsp;&nbsp;public void testPropertyNameDuplicateRule() throws
 * Exception {<br>
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;doTest(1);<br>
 * <br>
 * &nbsp;&nbsp;&nbsp;}<br>
 * <br>
 * &nbsp;&nbsp;&nbsp;protected void execute(int testNumber) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;switch(testNumber){<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;case 1:<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * testClassNameDuplicateRuleTest();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;break;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * &nbsp;&nbsp;&nbsp;}<br>
 * <br>
 * &nbsp;&nbsp;&nbsp;protected void testClassNameDuplicateRuleTest() {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;addClass("Concept1");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;addClass("Concept1");<br>
 * &nbsp;&nbsp;&nbsp;}<br>
 * <br>
 * <br>
 * </span>
 * 
 * @author wzurek, ramin
 * 
 */
public abstract class BOMTestCase extends TestCase implements IBOMTestCase {

    /**
     * name of the file
     */
    protected static String CONCEPT_NAME;

    /**
     * actual number of the sub test, will be set by the doTest method
     */
    protected int actualSubTestNumber;

    /**
     * name of the test project
     */
    private static String PROJECT_NAME;

    /**
     * cached variable
     */
    private WorkingCopy workingCopy;

    /**
     * cached variable
     */
    private SpecialFolder conceptFolder;

    /**
     * cached variable
     */
    private IEditorPart editor;

    /**
     * cached variable
     */
    private Model model;

    private Hashtable<Integer, String[]> expectedMarkerIssues;

    private Hashtable<Integer, Hashtable<String, Integer>> issuesOccured;

    static protected String bomResourceName = IConstants.BOM_FOLDER_NAME;

    private String conceptFolderName = "Concepts";

    protected boolean multipleSubTests = false;

    /**
     * Normally tests produces markers and this framework will check for them.
     * (see expectedMarkers). But there are test cases where parts of the system
     * will be tested, t5aht do not produce markers (e.g. set certain flags). In
     * this cases this flag has to be set to false.
     */
    protected boolean checkMarkers = true;

    /**
     * In most cases the welcome page is disturbing. So it will be set
     * invisible. If it is needed for special test of the welcome page / or
     * other intro pages this flag ahould be set to false.
     */
    protected boolean hideWelcomePage = true;

    // protected boolean showStatus = true;

    /**
     * 
     */
    protected String EXPECTED_ISSUES_SUFFIX = ".issueexpected";

    /**
     * constructor
     */
    public BOMTestCase() {
        super();
        if (hideWelcomePage) {
            closeWelcomePage();
        }
        initFields();
        setConceptName();
        setProjectName();
        defineMarkerIssues();
    }

    /**
     * init all fields
     */
    private void initFields() {
        expectedMarkerIssues = new Hashtable<Integer, String[]>();
        issuesOccured = new Hashtable<Integer, Hashtable<String, Integer>>();
        CONCEPT_NAME =
                getClass().getSimpleName() + "."
                        + IConstants.BOM_FILE_EXTENTION;
    }

    /**
     * @param testNumber
     * @throws Exception
     */
    abstract protected void execute(int testNumber) throws Exception;

    /**
     * this method must be implemented by all test cases and should add marker
     * issues for all sub tests. <br>
     * <br>
     * example:<br>
     * <br>
     * addExpectedMarkerIssues(1, new String[]{"resourceReservedWord.issue"});
     * 
     * @see com.tibco.xpd.bom.test.BOMTestCase#addExpectedMarkerIssues(int,
     *      String[])
     */
    abstract protected void defineMarkerIssues();

    /**
     * @param name
     * @throws Exception
     */
    protected void doTest(int number) throws Exception {
        this.actualSubTestNumber = number;
        // conceptFolderName = "Concepts_" + actualSubTestNumber;
        conceptFolderName = "Concepts";
        actionCreateDiagram();
        _testOpenedEditor();
        execute(number);
        _testIsWorkingCopyDirty("Working Copy not dirty after executing a command.");
        actionSaveDiagram();
        TestUtil.waitForJobs(1000);
        checkResults();
        if (!multipleSubTests) {
            closeAll();
        }
    }

    /**
     * @param methodName
     */
    private void callTestMethod(String methodName) {
        Method meth;
        try {
            meth = getClass().getMethod(methodName, new java.lang.Class[0]);
            meth.invoke(this);
        } catch (SecurityException e) {
            TestCase.fail(e.getMessage());
        } catch (NoSuchMethodException e) {
            TestCase.fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            TestCase.fail(e.getMessage());
        } catch (IllegalAccessException e) {
            TestCase.fail(e.getMessage());
        } catch (InvocationTargetException e) {
            TestCase.fail(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.bom.test.IBOMTestCase#setConceptFileName()
     */
    @Override
    abstract public void setConceptName();

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.bom.test.IBOMTestCase#setProjectName()
     */
    @Override
    abstract public void setProjectName();

    /**
     * @return
     */
    abstract protected boolean doClearBuild();

    /**
     * This method must be implemented, but the inherited class may leave it
     * empty, because by default the concept name will be set to the name of the
     * class.
     * 
     * @param conceptName
     */
    protected void setConceptName(String conceptName) {
        CONCEPT_NAME = conceptName + "." + IConstants.BOM_FILE_EXTENTION;
    }

    /**
     * @param projectName
     */
    protected void setProjectName(String projectName) {
        PROJECT_NAME = projectName;
    }

    /**
     * Check the content of the Problems View
     * 
     * @param editor
     * @throws CoreException
     */
    protected void checkResults() throws CoreException {

        // not all tests analse the markers. Some look at different things.

        if (!checkMarkers) {
            return;
        }

        if (getEditor() instanceof UMLDiagramEditor) {
            IEditorInput input = editor.getEditorInput();
            if (input instanceof FileEditorInput) {
                FileEditorInput fileEditorInput = (FileEditorInput) input;
                IFile file = fileEditorInput.getFile();
                IMarker[] markers =
                        file.findMarkers(XpdConsts.VALIDATION_MARKER_TYPE,
                                true,
                                IResource.DEPTH_ZERO);

                if (markers.length == 0) {
                    TestCase.fail("No Markers produced while some were expected");
                } else {

                    // int expectedIssueFoundNumber = 0;

                    Object expectedIssues =
                            expectedMarkerIssues.get(actualSubTestNumber);
                    for (IMarker marker : markers) {
                        Object issueId = marker.getAttribute("issueId");

                        // if no expected issues were defined then send all
                        // issues
                        // to the console. then this info can be used to define
                        // them

                        if (expectedIssues instanceof String[]) {
                            if (findExpectedIssue(issueId,
                                    (String[]) expectedIssues)) {
                                // break;
                                System.out.println(getClass().getSimpleName()
                                        + ": Issue to be tested: " + issueId
                                        + " occured");
                            } else {
                                System.out.println(getClass().getSimpleName()
                                        + ": Issue: " + issueId);
                            }
                        } else {
                            System.out.println(getClass().getSimpleName()
                                    + ": Issue: " + issueId);
                        }
                    }

                    // if issues were not defined the only purpose of this
                    // carrying
                    // out this test was to display the issues occured to define
                    // them later.
                    // see also the command line paramter -testissues
                    if (!(expectedIssues instanceof String[])) {
                        TestCase.fail("No expected issues were definid for this test case.");
                    } else {
                        analyzeIssues();
                    }
                }
            }

        } else {
            TestCase.fail("wrong editor part (UMLDiagramEditor expected)");
        }
    }

    /**
     * Analyses if the number of occured issues match the number of the expected
     * ones.
     */
    void analyzeIssues() {
        Hashtable<String, Integer> issuesTable = getIssuesTable();

        Enumeration<String> keys = issuesTable.keys();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key.endsWith(EXPECTED_ISSUES_SUFFIX)) {

                String occuredKey =
                        key.substring(0,
                                key.length() - EXPECTED_ISSUES_SUFFIX.length())
                                + ".issue";
                Object o = issuesTable.get(occuredKey);
                int issuesOccured = 0;
                if (o instanceof Integer) {
                    issuesOccured = (Integer) o;
                }
                int issuesExpected = issuesTable.get(key);
                if (issuesExpected == -1) {
                    if (issuesOccured < 1) {
                        TestCase.fail("Number of expected issues (at least 1) didn't match with the occured ones ("
                                + issuesOccured + ") for issue " + key);
                    }
                } else {
                    if (issuesExpected != issuesOccured) {
                        TestCase.fail("Number of expected issues ("
                                + issuesExpected
                                + ") didn't match with the occured ones ("
                                + issuesOccured + ") for issue " + key);
                    }
                }
            }
        }

    }

    /**
     * Increase the number of a detected issue. Every sub test will be
     * separately considered.
     * 
     * @param issue
     */
    void increaseIssuesFound(String issue) {
        Hashtable<String, Integer> issuesTable = getIssuesTable();
        Object o = issuesTable.get(issue);
        if (o instanceof Integer) {
            int newCount = (Integer) o;
            newCount++;
            issuesTable.put(issue, newCount);
        } else {
            issuesTable.put(issue, 1);
        }
    }

    /**
     * separates the occurences number from the issue and store it in the
     * issuesOccured table if present;
     * 
     * @param rawIssue
     * @return
     */
    String getIssueString(String rawIssue) {
        StringTokenizer tok = new StringTokenizer(rawIssue, "#");
        int tokCount = tok.countTokens();
        Hashtable<String, Integer> issuesTable;
        switch (tokCount) {
        case 0:
            issuesTable = getIssuesTable();
            issuesTable.put(rawIssue + "expected", -1);
            return rawIssue;
        case 1:
            issuesTable = getIssuesTable();
            issuesTable.put(rawIssue + "expected", 1);
            return rawIssue;
        case 2:
            String issue = tok.nextToken();
            String occurencies = tok.nextToken();
            int occ = Integer.valueOf(occurencies);
            issuesTable = getIssuesTable();
            issuesTable.put(issue + "expected", occ);
            return issue;
        default:
            TestCase.fail("Syntax of the issue was wrong (" + rawIssue + ")");
            return rawIssue;
        }
    }

    /**
     * returns the issues table of the current sub test
     * 
     * @return
     */
    Hashtable<String, Integer> getIssuesTable() {
        Hashtable<String, Integer> issuesTable =
                issuesOccured.get(actualSubTestNumber);
        if (issuesTable == null) {
            issuesTable = new Hashtable<String, Integer>();
            issuesOccured.put(actualSubTestNumber, issuesTable);
        }
        return issuesTable;
    }

    /**
     * @param issueId
     * @return
     */
    boolean findExpectedIssue(Object issueId, String[] expectedIssues) {
        if (expectedIssues == null) {
            return false;
        }
        for (String expectedIssue : expectedIssues) {
            String issue = getIssueString(expectedIssue);
            if (issueId.equals(issue)) {
                increaseIssuesFound(issue);
                return true;
            }
        }
        return false;
    }

    /**
     * creates a special folder named 'Concept' depending on the project name
     * 
     * @return
     */
    protected SpecialFolder createConceptFolder() {
        if (conceptFolder == null) {
            SpecialFolder cf =
                    TestUtil.createSpecialFolder(PROJECT_NAME,
                            conceptFolderName,
                            IConstants.BOM_FOLDER_NAME);
            if (this.conceptFolder == null) {
                this.conceptFolder = cf;
            }
        }

        return conceptFolder;
    }

    /**
     * @return
     */
    protected SpecialFolder getConceptFolder() {
        assertNotNull("Testprogram tried to access the cached SpecialFolder conceptFolder but that was null",
                conceptFolder);
        return conceptFolder;
    }

    /**
     * creates a BOM diagram, if something goes wrong it produces a JUnit
     * message
     */
    protected void actionCreateDiagram() {
        if (conceptFolder == null) {
            createConceptFolder();
        }
        createDiagram(getConceptFolder());
        createWorkingCopy(getConceptFolder());
    }

    /**
     * creates a BOM diagram, if something goes wrong it produces a JUnit
     * message
     */
    protected void createDiagram(SpecialFolder conceptsFolder) {
        BOMTestUtils.createBOMdiagram(conceptsFolder, CONCEPT_NAME);
    }

    /**
     * @throws CoreException
     */
    protected void actionDeleteDiagram() throws CoreException {
        deleteDiagram(getConceptFolder());
    }

    /**
     * @param conceptsFolder
     * @throws CoreException
     */
    protected void deleteDiagram(SpecialFolder conceptsFolder)
            throws CoreException {
        conceptsFolder.getFolder().getFile(CONCEPT_NAME)
                .delete(true, new NullProgressMonitor());
    }

    /**
     * @throws CoreException
     */
    protected void closeAll() throws CoreException {
        actionDeleteDiagram();
        closeEditor();
        // reset all cached fields to prepare for any new test
        workingCopy = null;
        conceptFolder = null;
        editor = null;
        model = null;
        expectedMarkerIssues = null;
        issuesOccured = null;
    }

    /**
     * @param conceptsFolder
     * @return
     */
    protected WorkingCopy createWorkingCopy(SpecialFolder conceptsFolder) {
        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(conceptsFolder
                        .getFolder().getFile(CONCEPT_NAME));
        if (workingCopy == null) {
            workingCopy = wc;
            createModel();
        }
        return wc;
    }

    /**
     * @return
     */
    protected WorkingCopy getWorkingCopy() {
        assertNotNull("Testprogram tried to access the cached WorkingCopy but that was null",
                workingCopy);
        return workingCopy;
    }

    /**
     * tries to close the editor, if it fails it will be reported
     */
    protected void closeEditor() {
        // give a chance to the editor to close
        TestUtil.delay(500);
        // test if editor is closed
        IEditorPart editor =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActiveEditor();

        if (editor != null) {
            /*
             * IF after first try it is not there the may have needed a little
             * more time to complete, so wait here only after we're sure we need
             * to.
             */
            TestUtil.delay(2000);
            editor =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().getActiveEditor();
        }

        assertNull("After removing a concept file, "
                + "the editor should be closed", editor);
    }

    /**
     * test if the editor was opened after creating the diagram
     */
    protected IEditorPart _testOpenedEditor() {
        // test if editor is open
        IEditorPart ed =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActiveEditor();
        assertEquals("After creating a concept, the editor should be open", ed
                .getSite().getId(), UMLDiagramEditor.ID);
        if (editor == null) {
            editor = ed;
        }

        return editor;
    }

    /**
     * @return
     */
    protected IEditorPart getEditor() {
        assertNotNull("Testprogram tried to access the cached EditorPart but that was null",
                editor);
        return editor;
    }

    /**
     * @param wc
     * @throws IOException
     */
    protected void actionSaveDiagram() throws IOException {
        getWorkingCopy().save();
        if (doClearBuild()) {
            clearProject(getWorkingCopy());
        }
    }

    /**
     * @param wc
     * @throws IOException
     */
    protected void actionSaveDiagram(WorkingCopy wc) throws IOException {
        wc.save();
    }

    /**
     * test if the WorkingCopy has become dirty
     * 
     */
    protected void _testIsWorkingCopyDirty() {
        _testIsWorkingCopyDirty(getWorkingCopy());
    }

    /**
     * test if the WorkingCopy has become dirty
     * 
     */
    protected void _testIsWorkingCopyDirty(String message) {
        _testIsWorkingCopyDirty(getWorkingCopy(), message);
    }

    /**
     * test if the WorkingCopy has become dirty
     * 
     * @param wc
     */
    protected void _testIsWorkingCopyDirty(WorkingCopy wc) {
        _testIsWorkingCopyDirty(wc, "Working copy should be dirty");
    }

    /**
     * test if the WorkingCopy has become dirty
     * 
     * @param wc
     */
    protected void _testIsWorkingCopyDirty(WorkingCopy wc, String message) {
        assertTrue(message, wc.isWorkingCopyDirty());
    }

    /**
     * @param wc
     */
    protected void clearProject(WorkingCopy wc) {
        try {
            IProject project = wc.getEclipseResources().get(0).getProject();
            project.build(IncrementalProjectBuilder.CLEAN_BUILD,
                    new NullProgressMonitor());

        } catch (CoreException e) {
            e.printStackTrace();
            TestCase.fail(e.getMessage());
        }
    }

    /**
     * @return
     */
    private Model createModel() {
        Model m = (Model) getWorkingCopy().getRootElement();
        if (model == null) {
            model = m;
        }
        return m;
    }

    /**
     * @return
     */
    protected Model getModel() {
        assertNotNull("Testprogram tried to access the cached Model but that was null",
                model);
        return model;
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestUtil.createProject(PROJECT_NAME);
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        TestUtil.waitForBuilds(300, 300);

        TestUtil.removeProject(PROJECT_NAME);
        // THIS WAIT IS NECESSARY TO PROCESS DELTA AFTER PROJECT DELETE
    }

    /**
     * @return
     */
    public String[] getExpectedMarkerIssues() {
        assertNotNull("Testprogram tried to access the markerIssues but it was null",
                expectedMarkerIssues);
        return expectedMarkerIssues.get(actualSubTestNumber);
    }

    /**
     * Adds an expected marker issue. every entry in the string array can
     * specify the expected number spearated with '#'. If this is missing at
     * least one occurency will be required for a successfull test.<br>
     * <br>
     * example:<br>
     * <br>
     * addExpectedMarkerIssues(1, new String[]{"resourceReservedWord.issue#3"});
     * 
     * @param markerIssues
     */
    protected void addExpectedMarkerIssues(int subTestNumber,
            String[] markerIssues) {
        this.expectedMarkerIssues.put(subTestNumber, markerIssues);
    }

    protected void executeCommand(Command cmd) {
        getWorkingCopy().getEditingDomain().getCommandStack().execute(cmd);
    }

    /**
     * add UML class to the diagram without a name
     * 
     * @return
     * @throws Exception
     */
    protected Class addClass() throws Exception {
        return addClass(null);
    }

    /**
     * @param name
     * @return
     * @throws Exception
     */
    protected Class addClass(String name) throws Exception {
        Class[] classes = BOMTestUtils.createClasses(getModel(), 1);
        Class clazz = classes[0];
        if (name != null) {
            BOMTestUtils.changeClassName(clazz, name);
        }

        // assertSame("Class was not added to the model", getModel(), clazz
        // .eContainer());

        return clazz;
    }

    protected void addAttribute(Class clazz) {
        // BOMTestUtils.set(clazz, , attr, value)
    }

    void xText(Class class1) {
        Generalization generalization =
                UMLFactory.eINSTANCE.createGeneralization();
        // generalization.s

    }

    /**
     * add a package with no name to the diagram
     * 
     * @return
     * @throws Exception
     */
    protected Package addPackage() throws Exception {
        return addPackage(null);
    }

    /**
     * add a package to the diagram with the given name
     * 
     * @param name
     * @return
     * @throws Exception
     */
    protected Package addPackage(String name) throws Exception {
        Package _package = BOMTestUtils.createPackages(getModel(), 1)[0];
        if (name != null) {
            BOMTestUtils.changePackageName(_package, name);
        }
        return _package;
    }

    /**
     * add a primitive type to the diagram with the given name
     * 
     * @param name
     * @return
     * @throws Exception
     */
    protected PrimitiveType addPrimitiveType(String name) throws Exception {
        PrimitiveType _primitiveType =
                BOMTestUtils.createPrimitiveTypes(getModel(), 1)[0];
        if (name != null) {
            BOMTestUtils.changePrimitiveTypeName(_primitiveType, name);
        }
        return _primitiveType;
    }

    /**
     * @param class1
     * @return
     */
    protected Property addProperty(Class class1) {
        Property property = UMLFactory.eINSTANCE.createProperty();
        property.setName("AAA");
        Command cmd =
                AddCommand.create(getWorkingCopy().getEditingDomain(),
                        class1,
                        UMLPackage.eINSTANCE
                                .getStructuredClassifier_OwnedAttribute(),
                        property);
        assertTrue("Cannot add property", cmd.canExecute());
        executeCommand(cmd);
        return property;
    }

    /**
     * Close the Welcome Page to see what happens on the workbench window.
     */
    void closeWelcomePage() {
        try {
            IViewReference welcomeViewRef =
                    PlatformUI
                            .getWorkbench()
                            .getActiveWorkbenchWindow()
                            .getActivePage()
                            .findViewReference("org.eclipse.ui.internal.introview");
            if (welcomeViewRef != null) {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().hideView(welcomeViewRef);
            }
        } catch (Exception ignore) {
        }
    }

    /**
     * @param package_
     */
    protected void remove(Package package_) {
        Command cmd =
                RemoveCommand.create(getWorkingCopy().getEditingDomain(),
                        package_.getOwner(),
                        UMLPackage.eINSTANCE.getPackage_PackagedElement(),
                        package_);
        assertTrue("Cannot remove class", cmd.canExecute());
        executeCommand(cmd);
    }

    /**
     * @param class_
     */
    protected void remove(Class class_) {
        Command cmd =
                RemoveCommand.create(getWorkingCopy().getEditingDomain(),
                        class_.getOwner(),
                        UMLPackage.eINSTANCE.getPackage_PackagedElement(),
                        class_);
        assertTrue("Cannot remove class", cmd.canExecute());
        executeCommand(cmd);
    }

    /**
     * @param primitiveType
     */
    protected void remove(PrimitiveType primitiveType) {
        Command cmd =
                RemoveCommand.create(getWorkingCopy().getEditingDomain(),
                        primitiveType.getOwner(),
                        UMLPackage.eINSTANCE.getPackage_PackagedElement(),
                        primitiveType);
        assertTrue("Cannot remove class", cmd.canExecute());
        executeCommand(cmd);
    }

    /**
     * @param property
     */
    protected void remove(Property property) {

        // Object owner = property.getOwner();
        // if(owner instanceof Class){
        // Class
        // }

        Command cmd =
                RemoveCommand.create(getWorkingCopy().getEditingDomain(),
                        property.getOwner(),
                        UMLPackage.eINSTANCE
                                .getStructuredClassifier_OwnedAttribute(),
                        property);
        assertTrue("Cannot remove property", cmd.canExecute());
        executeCommand(cmd);
    }

    /**
     * @param eObject
     * @param name
     * @throws ExecutionException
     */
    protected void changeAssociationName(EObject eObject, String name)
            throws ExecutionException {
        BOMTestUtils.changeAssociationName(eObject, name);
    }

    /**
     * @param eObject
     * @param name
     * @throws ExecutionException
     */
    protected void changePackageName(EObject eObject, String name)
            throws ExecutionException {
        BOMTestUtils.changePackageName(eObject, name);
    }

    /**
     * @param eObject
     * @param name
     * @throws ExecutionException
     */
    protected void changeClassName(EObject eObject, String name)
            throws ExecutionException {
        BOMTestUtils.changeClassName(eObject, name);
    }

    /**
     * @param eObject
     * @param name
     * @throws ExecutionException
     */
    protected void changePrimitiveTypeName(EObject eObject, String name)
            throws ExecutionException {
        BOMTestUtils.changePropertyName(eObject, name);
    }

    /**
     * @param eObject
     * @param name
     * @throws ExecutionException
     */
    protected void changePropertyName(EObject eObject, String name)
            throws ExecutionException {
        BOMTestUtils.changePropertyName(eObject, name);
    }
}
