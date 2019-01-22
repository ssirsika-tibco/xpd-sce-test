/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;

/**
 * Wizard page allowing the user to enter the details of the Test Name, Java
 * Class and Test Plugin to be created.
 * 
 * @author aallway
 * @since 3.2
 */
public class CreateBaseTestPage extends WizardPage {

    public static final String PLUGIN_PROJECT_NATURE =
            "org.eclipse.pde.PluginNature"; //$NON-NLS-1$

    public static final String JAVA_EXT = ".java"; //$NON-NLS-1$

    public static final String JAVA_SRC_FOLDERNAME = "src"; //$NON-NLS-1$

    public static final String DEFAULT_BASE_TEST_RESOURCE_FOLDER = "resources"; //$NON-NLS-1$

    private XpdWizardToolkit toolkit;

    protected String currentTestName = ""; //$NON-NLS-1$ 

    //
    // CONTROLS....
    //
    protected Text cTestName;

    protected Text cJavaClassName;

    protected CCombo cTestPluginProject;

    protected CCombo cJavaPackageId;

    protected Text cTestPluginBaseResourceFolder;

    Map<String, IProject> projectNameMap = new HashMap<String, IProject>();

    Map<String, IFolder> packageIdMap = new HashMap<String, IFolder>();

    //
    // RETURN DATA.....
    //
    private String testName = null;

    private String testClassName = null;

    private String testPackageId = null;

    private IProject testProject = null;

    private IFolder testPackageFolder = null;

    private IPath testRelativeBaseResourceFolder = null;

    private Button includeDotResources = null;

    private Button generateAllTestsClass = null;

    private ContainerCheckedTreeViewer classList = null;

    private boolean initProjectDisableSelection = false;

    private boolean initPackageDisableSelection = false;

    private boolean initResourceFolderDisableSelection = false;

    protected CreateBaseTestPage() {
        super(Messages.CreateBaseTestPage_title);
        setDescription(Messages.CreateBaseTestPage_Description_longdesc);
        setPageComplete(false);
    }

    /**
     * @return the testName
     */
    public String getTestName() {
        return testName;
    }

    /**
     * @return the testClassName
     */
    public String getTestClassName() {
        return testClassName;
    }

    /**
     * @return the testPackageId
     */
    public String getTestPackageId() {
        return testPackageId;
    }

    /**
     * @return the testPackageFolder
     */
    public IFolder getTestPackageFolder() {
        return testPackageFolder;
    }

    /**
     * @param testPackageFolder
     *            the testPackageFolder to set
     */
    public void setTestPackageFolder(IFolder testPackageFolder,
            boolean disableSelection) {
        this.testPackageFolder = testPackageFolder;
        this.initPackageDisableSelection = disableSelection;

        testPackageId = getJavaPackageIdFromFolder(testPackageFolder);

        if (cJavaPackageId != null) {
            cJavaPackageId.setText(testPackageId);
            cJavaPackageId.setEnabled(!disableSelection);
        }

        resetPackageFolder(testPackageFolder);

        return;
    }

    /**
     * @return the test plugin project
     */
    public IProject getTestProject() {
        return testProject;
    }

    /**
     * @param testProject
     *            the testProject to set
     */
    public void setTestProject(IProject testProject, boolean disableSelection) {
        this.testProject = testProject;
        this.initProjectDisableSelection = disableSelection;
        if (cTestPluginProject != null) {
            cTestPluginProject.setText(testProject.getName());
            cTestPluginProject.setEnabled(!disableSelection);
        }
        return;
    }

    /**
     * @param testPackageFolder
     *            the testPackageFolder to set
     */
    public void setTestRelativeBaseResourceFolder(
            IPath testRelativeBaseResourceFolder, boolean disableSelection) {
        this.testRelativeBaseResourceFolder = testRelativeBaseResourceFolder;
        this.initResourceFolderDisableSelection = disableSelection;

        if (cTestPluginBaseResourceFolder != null) {
            if (testRelativeBaseResourceFolder != null) {
                cTestPluginBaseResourceFolder
                        .setText(testRelativeBaseResourceFolder.toString());
            } else {
                cTestPluginBaseResourceFolder.setText(""); //$NON-NLS-1$
            }
            cTestPluginBaseResourceFolder.setEnabled(!disableSelection);
        }

        return;
    }

    public IPath getTestRelativeBaseResourceFolder() {
        return testRelativeBaseResourceFolder;
    }

    public boolean isIncludeDotResources() {
        return includeDotResources.getSelection();
    }

    public boolean isGenerateAllTestsClass() {
        return generateAllTestsClass.getSelection();
    }

    public Set<String> getSelectedAllTestsClassNames() {
        Object[] checked = classList.getCheckedElements();

        Set<String> classNames = new HashSet<String>();

        if (checked != null) {
            for (Object c : checked) {
                if (c instanceof String) {
                    classNames.add((String) c);
                }
            }
        }

        // Always add the class about to be created.
        classNames.add(getTestClassName());

        return classNames;

    }

    public void createControl(Composite parent) {
        toolkit = new XpdWizardToolkit(parent);

        Composite root = toolkit.createComposite(parent, SWT.NONE);
        root.setLayout(new GridLayout(2, false));

        Label lab;

        //
        // Overall test name
        lab = toolkit.createLabel(root, "", SWT.NONE); //$NON-NLS-1$
        lab.setText(Messages.CreateBaseTestPage_TestName_label);
        lab.setToolTipText(Messages.CreateBaseTestPage_TestName_tooltip);

        cTestName = toolkit.createText(root, "", SWT.NONE); //$NON-NLS-1$
        cTestName.setToolTipText(Messages.CreateBaseTestPage_TestName_tooltip);
        cTestName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        //
        // Target java class info id (calculated from the test name)
        lab = toolkit.createLabel(root, "", SWT.NONE); //$NON-NLS-1$
        lab.setText(Messages.CreateBaseTestPage_JavaClassName_label);
        lab.setToolTipText(Messages.CreateBaseTestPage_JavaClassName_tooltip);

        cJavaClassName = toolkit.createText(root, "", SWT.WRAP); //$NON-NLS-1$
        cJavaClassName
                .setToolTipText(Messages.CreateBaseTestPage_JavaClassName_tooltip);
        cJavaClassName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        //
        // Target test plugin project.
        lab = toolkit.createLabel(root, "", SWT.NONE); //$NON-NLS-1$
        lab.setText(Messages.CreateBaseTestPage_TestProject_label);
        lab.setToolTipText(Messages.CreateBaseTestPage_TestProject_tooltip);

        cTestPluginProject = toolkit.createCCombo(root, SWT.READ_ONLY);
        cTestPluginProject
                .setToolTipText(Messages.CreateBaseTestPage_TestProject_tooltip);
        cTestPluginProject
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        loadProjectList();

        //
        // The java package id.
        lab = toolkit.createLabel(root, "", SWT.NONE); //$NON-NLS-1$
        lab.setText(Messages.CreateBaseTestPage_JavaPackage_label);
        lab.setToolTipText(Messages.CreateBaseTestPage_JavaPackage_tooltip);

        cJavaPackageId = toolkit.createCCombo(root, SWT.READ_ONLY);
        cJavaPackageId
                .setToolTipText(Messages.CreateBaseTestPage_JavaPackage_tooltip);
        cJavaPackageId.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        //
        // The target folder for test resources
        lab = toolkit.createLabel(root, "", SWT.NONE); //$NON-NLS-1$
        lab.setText(Messages.CreateBaseTestPage_BaseFolder_label);
        lab.setToolTipText(Messages.CreateBaseTestPage_BaseFolder_tooltip);

        cTestPluginBaseResourceFolder = toolkit.createText(root, "", SWT.WRAP); //$NON-NLS-1$
        cTestPluginBaseResourceFolder
                .setToolTipText(Messages.CreateBaseTestPage_JavaClassName_tooltip);
        cTestPluginBaseResourceFolder.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        currentTestName = Messages.CreateBaseTestPage_DefaultTestName_label;

        cTestName.setText(currentTestName);
        cTestName.selectAll();

        cJavaClassName.setText(toJavaName(cTestName.getText()
                + Messages.CreateBaseTestPage_TestSuffix));

        //
        // Generate / Update AllTests.java class.
        Label filler = toolkit.createLabel(root, ""); //$NON-NLS-1$

        generateAllTestsClass =
                toolkit.createButton(root,
                        String.format(Messages.CreateBaseTestPage_GenerateAlltests_label,
                                "AllTests.java"), //$NON-NLS-1$
                        SWT.CHECK);
        generateAllTestsClass.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        generateAllTestsClass.setSelection(true);
        generateAllTestsClass
                .setToolTipText(Messages.CreateBaseTestPage_GenerateAllTest_tooltip);

        //
        // For checkbox List the classes in the target package.
        filler = toolkit.createLabel(root, ""); //$NON-NLS-1$

        classList = new ContainerCheckedTreeViewer(root);
        classList.setContentProvider(new ClassListContentProvider());
        classList.setLabelProvider(new LabelProvider());
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 150;
        classList.getTree().setLayoutData(gd);

        classList.setInput(this);

        //
        // Include ".* resources check box".
        filler = toolkit.createLabel(root, ""); //$NON-NLS-1$

        includeDotResources =
                toolkit.createButton(root,
                        Messages.CreateBaseTestPage_IncludeDotResource_label,
                        SWT.CHECK);
        includeDotResources
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        includeDotResources.setSelection(false);

        //
        // Setup the control listeners
        setupControlListeners();
        setControl(root);

        cTestPluginProject.setEnabled(!initProjectDisableSelection);
        if (testProject != null) {
            cTestPluginProject.setText(testProject.getName());
        }

        cJavaPackageId.setEnabled(!initPackageDisableSelection);
        if (testPackageFolder != null) {
            String packageId = getJavaPackageIdFromFolder(testPackageFolder);
            cJavaPackageId.setText(packageId);
        }

        cTestPluginBaseResourceFolder
                .setEnabled(!initResourceFolderDisableSelection);

        if (testRelativeBaseResourceFolder != null) {
            cTestPluginBaseResourceFolder
                    .setText(testRelativeBaseResourceFolder.toString());
        } else if (!initResourceFolderDisableSelection) {
            cTestPluginBaseResourceFolder
                    .setText(toDefaultBaseResourceFolder(cTestName.getText()));
        }

        validatePage();

        return;
    }

    /**
     * Set up control listeners to save and update data.
     */
    protected void setupControlListeners() {
        //
        // Overall test name.
        //
        cTestName.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                String name = cTestName.getText();

                // If the current base resource folder path matches the
                // tokenised version of the current test name the reassign it.
                String baseTestFolderForCurrName =
                        toDefaultBaseResourceFolder(currentTestName);

                if (baseTestFolderForCurrName
                        .equals(cTestPluginBaseResourceFolder.getText())) {
                    if (!initResourceFolderDisableSelection) {
                        cTestPluginBaseResourceFolder
                                .setText(toDefaultBaseResourceFolder(name));
                    }
                }

                // If the current java class name matches the
                // tokenised version of the current test name the reassign it.
                String baseJavaClassNameForCurrName =
                        toJavaName(currentTestName);
                if (!baseJavaClassNameForCurrName
                        .endsWith(Messages.CreateBaseTestPage_TestSuffix)) {
                    baseJavaClassNameForCurrName +=
                            Messages.CreateBaseTestPage_TestSuffix;
                }

                if (baseJavaClassNameForCurrName.equals(cJavaClassName
                        .getText())) {
                    String namePlusTest = name;
                    if (!namePlusTest
                            .endsWith(Messages.CreateBaseTestPage_TestSuffix)) {
                        namePlusTest += Messages.CreateBaseTestPage_TestSuffix;
                    }

                    cJavaClassName.setText(toJavaName(namePlusTest));
                }

                currentTestName = name;

                validatePage();
                return;
            }
        });

        //
        // Test java class name
        //
        cJavaClassName.addVerifyListener(new VerifyListener() {
            // Make sure entered values are valid for hjava class names.
            public void verifyText(VerifyEvent e) {
                e.doit = false;

                Text textControl = ((Text) e.widget);
                String text =
                        textControl.getText(0, e.start - 1)
                                + e.text
                                + textControl.getText(e.end,
                                        textControl.getCharCount() - 1);

                if (toJavaName(text).equals(text)) {
                    // If the name does not change after conversion to
                    // java name then it's ok.
                    e.doit = true;
                }

                return;
            }
        });

        cJavaClassName.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                validatePage();
            }
        });

        cJavaClassName.addListener(SWT.Deactivate, new Listener() {

            public void handleEvent(Event event) {
                switch (event.type) {
                case SWT.Deactivate:
                    String name = cJavaClassName.getText();
                    if (name != null) {
                        if (!name
                                .endsWith(Messages.CreateBaseTestPage_TestSuffix)) {
                            cJavaClassName.setText(name
                                    + Messages.CreateBaseTestPage_TestSuffix);
                        }
                    }
                }
                return;
            }
        });

        //
        // Test plugin resource base path
        //
        cTestPluginBaseResourceFolder.addVerifyListener(new VerifyListener() {
            public void verifyText(VerifyEvent e) {
                e.doit = false;

                Text textControl = ((Text) e.widget);
                String text =
                        textControl.getText(0, e.start - 1)
                                + e.text
                                + textControl.getText(e.end,
                                        textControl.getCharCount() - 1);

                if (toValidPath(text).equals(text)) {
                    // If the name does not change after conversion to
                    // folder name then it's ok.
                    e.doit = true;
                }

                return;
            }
        });

        cTestPluginBaseResourceFolder.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                validatePage();
            }
        });

        //
        // Java Projects.
        cTestPluginProject.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                setProjectFromCombo();
                validatePage();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        //
        // Java Projects.
        cJavaPackageId.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                setPackageFromCombo();
                validatePage();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        return;
    }

    /**
     * Load any java plugin projects in the workspace.
     */
    private void loadProjectList() {
        projectNameMap = new HashMap<String, IProject>();

        List<IProject> projectList = new ArrayList<IProject>();
        IProject[] projects =
                ResourcesPlugin.getWorkspace().getRoot().getProjects();
        for (IProject project : projects) {
            projectList.add(project);
        }

        Collections.sort(projectList, new Comparator<IProject>() {
            public int compare(IProject o1, IProject o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        for (IProject project : projectList) {
            try {
                if (project.hasNature(PLUGIN_PROJECT_NATURE)
                        && !project.getName().startsWith(".")) { //$NON-NLS-1$
                    projectNameMap.put(project.getName(), project);
                    cTestPluginProject.add(project.getName());
                }
            } catch (CoreException e) {
            }
        }

        return;
    }

    /**
     * Setup when selected project changes
     */
    private void setProjectFromCombo() {
        testProject = null;
        testPackageId = null;
        testPackageFolder = null;

        int idx = cTestPluginProject.getSelectionIndex();

        if (idx >= 0) {
            String projectName = cTestPluginProject.getItem(idx);

            testProject = projectNameMap.get(projectName);

            // Reload the available packages ids.
            loadPackageList();
        }

        return;
    }

    /**
     * Load the list of available java packages for the selected project.
     */
    private void loadPackageList() {
        cJavaPackageId.removeAll();

        packageIdMap = new HashMap<String, IFolder>();

        if (testProject != null) {
            final List<String> packageIdList = new ArrayList<String>();

            final IFolder srcFolder =
                    testProject.getFolder(JAVA_SRC_FOLDERNAME);

            if (srcFolder.exists()) {
                final IPath srcPath = srcFolder.getFullPath();

                try {
                    srcFolder.accept(new IResourceVisitor() {
                        public boolean visit(IResource resource)
                                throws CoreException {
                            if (resource != srcFolder
                                    && resource instanceof IFolder) {

                                String packageId =
                                        getJavaPackageIdFromFolder((IFolder) resource);

                                if (packageId.length() > 0) {
                                    packageIdMap.put(packageId,
                                            (IFolder) resource);
                                    packageIdList.add(packageId);
                                }
                            }
                            return true;
                        }
                    });
                } catch (CoreException e) {
                }
            }

            Collections.sort(packageIdList, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return o1.compareToIgnoreCase(o2);
                }
            });

            for (String packageId : packageIdList) {
                cJavaPackageId.add(packageId);
            }
        }

        return;
    }

    /**
     * @param javaPackage
     * @return the Java package Id for the given java package folder.
     */
    private String getJavaPackageIdFromFolder(IFolder javaPackage) {
        String packageId = ""; //$NON-NLS-1$

        IProject project = javaPackage.getProject();

        IFolder srcFolder = project.getFolder(JAVA_SRC_FOLDERNAME);

        if (srcFolder.exists()) {
            IPath srcPath = srcFolder.getFullPath();

            IPath path = javaPackage.getFullPath();

            path =
                    path.removeFirstSegments(path
                            .matchingFirstSegments(srcPath));

            String[] segs = path.segments();

            for (String seg : segs) {
                if (packageId.length() != 0) {
                    packageId += "."; //$NON-NLS-1$
                }
                packageId += seg;
            }
        }

        return packageId;
    }

    private void setPackageFromCombo() {
        testPackageId = null;
        testPackageFolder = null;

        int idx = cJavaPackageId.getSelectionIndex();
        if (idx >= 0) {
            testPackageId = cJavaPackageId.getItem(idx);

        } else {
            testPackageId = cJavaPackageId.getText();
        }

        if (testPackageId != null && testPackageId.length() > 0) {
            IFolder folder = packageIdMap.get(testPackageId);

            resetPackageFolder(folder);
        }

        return;
    }

    /**
     * 
     */
    private void resetPackageFolder(IFolder testPackageFolder) {
        this.testPackageFolder = testPackageFolder;
        if (this.testPackageFolder != null) {
            generateAllTestsClass
                    .setText(String
                            .format(Messages.CreateBaseTestPage_GenerateAlltests_label,
                                    this.testPackageFolder.getName()
                                            + ".AllTests.java")); //$NON-NLS-1$
        }

        classList.refresh();

        List<String> classNames = getPackageFolderClassList();

        for (Iterator iterator = classNames.iterator(); iterator.hasNext();) {
            String name = (String) iterator.next();

            name = name.toLowerCase();

            if (!name.contains("test")) {
                iterator.remove();
            }
        }

        classList.setCheckedElements(classNames.toArray());

        return;
    }

    /**
     * Ensure that the values selected uin all controls are valid.
     * <p>
     * As a side effect, update the plugin id, package id and java class name
     * controls and store the values for later retrieval by wizard via getter
     * methods.
     * 
     * @return true if user selected values are correct.
     */
    protected boolean validatePage() {
        boolean ret = false;

        String pageDescription = null;
        // Messages.CreateBaseTestPage_Description_longdesc;

        testName = null;
        testClassName = null;
        testRelativeBaseResourceFolder = null;

        if (cTestName.getText().length() == 0) {
            pageDescription = Messages.CreateBaseTestPage_MustEnterName_message;

        } else {
            testName = cJavaClassName.getText();

            if (cJavaClassName.getText().length() == 0
                    || !cJavaClassName.getText()
                            .equals(toJavaName(cJavaClassName.getText()))) {
                pageDescription =
                        Messages.CreateBaseTestPage_MustEnterClassName_message;

            } else {
                testClassName = cJavaClassName.getText();

                if (testProject == null) {
                    pageDescription =
                            Messages.CreateBaseTestPage_MustSelectPlugin_message;

                } else {
                    if (testPackageId == null || testPackageId.length() == 0
                            || testPackageFolder == null) {
                        pageDescription =
                                Messages.CreateBaseTestPage_MustSelectPackage_message;
                    } else {
                        if (cTestPluginBaseResourceFolder.getText().length() == 0
                                || !cTestPluginBaseResourceFolder
                                        .getText()
                                        .equals(toValidPath(cTestPluginBaseResourceFolder
                                                .getText()))) {
                            if (!initResourceFolderDisableSelection) {
                                pageDescription =
                                        Messages.CreateBaseTestPage_MustSelectResourceFolder_message1;
                            } else {
                                ret = true;
                            }

                        } else {
                            testRelativeBaseResourceFolder =
                                    new Path(
                                            toValidPath(cTestPluginBaseResourceFolder
                                                    .getText()));

                            ret = true;
                        }
                    }
                }
            }
        }

        setErrorMessage(pageDescription);

        setPageComplete(ret);

        return ret;
    }

    /**
     * Convert name to valid java Class name.
     * 
     * @param name
     * @return tokenised name.
     */
    public static String toJavaName(String name) {
        if (name != null) {
            char[] chars = name.toCharArray();

            String className = ""; //$NON-NLS-1$
            boolean upperCaseNext = true;
            /*
             * true if we are scanning the first character.
             */
            boolean isFirstCharOfJavaClassName = true;
            for (char c : chars) {

                if (Character.isWhitespace(c)) {

                    upperCaseNext = true;

                } else {
                    /*
                     * XPD-6923 : Allow underscores '_' in Java class names;
                     * also Java class names should not begin with a digit.
                     * (i.e. 123MyClassName)
                     */
                    if ((isFirstCharOfJavaClassName && Character
                            .isJavaIdentifierStart(c))
                            || (!isFirstCharOfJavaClassName && Character
                                    .isJavaIdentifierPart(c))) {

                        /*
                         * for first character it will always be true, hence
                         * having it as uppercase.
                         */
                        if (upperCaseNext) {
                            /*
                             * If the previous letter was a space ' ' then this
                             * letter should be in upper cases.
                             */
                            className += Character.toUpperCase(c);
                            upperCaseNext = false;

                        } else {
                            className += c;
                        }
                        /*
                         * once the first character is processed, this flag
                         * should always be false so there is no harm in setting
                         * it false always
                         */
                        isFirstCharOfJavaClassName = false;
                    }

                }
            }

            return className;
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * Convert name to valid project name.
     * 
     * @param name
     * @return tokenised name.
     */
    private String toValidPath(String name) {
        if (name != null) {
            char[] chars = name.toCharArray();

            String pathName = ""; //$NON-NLS-1$
            for (char c : chars) {

                if (Character.isLetter(c) || Character.isWhitespace(c)
                        || Character.isDigit(c) || c == '/') {
                    pathName += c;
                }
            }

            return pathName;
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Convert name to default base test resoruce folder name/ path
     * 
     * @param name
     * @return tokenised name.
     */
    private String toDefaultBaseResourceFolder(String name) {
        if (name != null) {
            if (name.length() > 0) {
                name =
                        DEFAULT_BASE_TEST_RESOURCE_FOLDER
                                + TestResourceInfo.PATH_SEPARATOR + name;
            } else {
                name = DEFAULT_BASE_TEST_RESOURCE_FOLDER;
            }

            return toValidPath(name);
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * @param classNames
     */
    private List<String> getPackageFolderClassList() {
        final List<String> classNames = new ArrayList<String>();

        if (testPackageFolder != null) {
            try {
                testPackageFolder.accept(new IResourceVisitor() {
                    public boolean visit(IResource resource)
                            throws CoreException {
                        if (resource instanceof IFile) {
                            String name = resource.getName();
                            if (name.endsWith(".java")
                                    && !"AllTests.java".equalsIgnoreCase(name)) {

                                String className =
                                        name.substring(0, name.length() - 5);

                                classNames.add(className);
                            }
                        } else if (resource.equals(testPackageFolder)) {
                            return true;
                        }
                        return false;
                    }
                });
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(classNames, new Comparator<String>() {

            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        return classNames;
    }

    private class ClassListContentProvider implements ITreeContentProvider {

        public Object[] getChildren(Object parentElement) {
            return null;
        }

        public Object getParent(Object element) {
            return null;
        }

        public boolean hasChildren(Object element) {
            return false;
        }

        public Object[] getElements(Object inputElement) {
            CreateBaseTestPage page = (CreateBaseTestPage) inputElement;

            List<String> classNames = getPackageFolderClassList();

            return classNames.toArray();
        }

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
    }

}
