/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.core.createtestwizards;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;

import com.tibco.xpd.core.createtestwizards.generatordata.AllTestsClassGeneratorData;
import com.tibco.xpd.core.createtestwizards.generatordata.BaseTestJavaClassGeneratorData;
import com.tibco.xpd.core.createtestwizards.generatordata.TestXmlGeneratorData;
import com.tibco.xpd.core.createtestwizards.generators.AllTestsClassGenerator;
import com.tibco.xpd.core.createtestwizards.generators.BaseTestJavaClassGenerator;
import com.tibco.xpd.core.createtestwizards.generators.TestXmlGenerator;
import com.tibco.xpd.core.test.util.AbstractBaseResourceTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtilPlugin;

/**
 * Wizard to aid in creation of a Business Studio resource based test.
 * <p>
 * The single page allows the user to select a test name, studio runtime test
 * project name and a target Java class in a plugin java package to create the
 * test in.
 * </p>
 * <p>
 * <b>The recommended (hopefully, easiest) method of sub-classing to create
 * extended test creation wizards</b> i.e. If you want to implement a new
 * creation wizard that stores selected Studio resources in a user defined test
 * plugin and generate a Junit test class with extended features (probably as
 * defiend by additional wizard pages)...
 * </p>
 * <p>
 * <li>Subclass this wizard</li>
 * 
 * <li>Extend {@link #addPages()} for more info-gathering wizard pages.</li>
 * 
 * <li>Sub-class {@link BaseTestJavaClassGeneratorData} and add storage of your
 * extra page's data. This class will be used to pass variable data to the JUnit
 * test java class generator.</li>
 * 
 * <li>Depending on how much <i>extra</i> code your new test type needs over and
 * above the base resource test, you may wish to consider sub-classing
 * {@link AbstractBaseResourceTest} and having your .javajet file generate a
 * class that extends it (i.e. implementing most of the extra standard test code
 * in the Abstract class rather than including it all in the .javajet template).
 * </li>
 * 
 * <li>Copy and extend the createBaseTest.javajet template (for instance
 * createNewTest.javajet). <b>Ensure that you change the class name in the
 * header</b> (i.e. "NewTestGenerator"). This will result in a new class
 * <code>NewTestGenerator.java</code>.</li>
 * 
 * <li>Now override the
 * {@link #generateTestClassContent(String, String, String, String, IPath, Collection)}
 * method in this class, copy it and modify to use your extended
 * BaseTestJavaClassGeneratorData with your NewTestGenerator class.</li>
 * 
 * <li>Finally, you can add your new Wizard to the Studio UI using the
 * <code>org.eclipse.ui.popupMenus</code> extension point.</li>
 * </p>
 * <br/>
 * 
 * @author aallway
 * @since 3.2
 */
public class CreateBaseTestWizard extends Wizard {

    public static final String BIN_INCLUDES = "bin.includes"; //$NON-NLS-1$

    public static final String BUILD_PROPERTIES = "build.properties"; //$NON-NLS-1$

    public static final String TEST_XML = "test.xml"; //$NON-NLS-1$

    public static final String JAVA_META_INF_FOLDERNAME = "META-INF"; //$NON-NLS-1$

    public static final String JAVA_MANIFEST_FILENAME = "MANIFEST.MF"; //$NON-NLS-1$

    private static final String MF_BUNDLE_SYMBOLICNAME = "Bundle-SymbolicName"; //$NON-NLS-1$

    private static final String MF_REQUIRE_BUNDLE = "Require-Bundle"; //$NON-NLS-1$

    protected CreateBaseTestPage baseTestPage;

    protected Collection<IResource> selectedStudioResources;

    public CreateBaseTestWizard(Collection<IResource> selectedStudioResources) {
        super();

        init(selectedStudioResources);
    }

    /**
     * Initialise during construction.
     * 
     * @param selectedStudioResources
     */
    protected void init(Collection<IResource> selectedStudioResources) {
        for (IResource res : selectedStudioResources) {
            if (!(res instanceof IFile) && !(res instanceof IFolder)
                    && !(res instanceof IProject)) {
                throw new RuntimeException(
                        "Only projects, folders and files are handled by resource based test creator."); //$NON-NLS-1$
            }
        }

        this.selectedStudioResources = selectedStudioResources;

        setWindowTitle(Messages.CreateBaseTestWizard_title);
        setDefaultPageImageDescriptor(CreateTestWizardsPlugin
                .getImageDescriptor(CreateTestWizardsConstants.IMG_CREATEBASETEST_WIZARD));
    }

    @Override
    public void addPages() {
        super.addPages();

        baseTestPage = new CreateBaseTestPage();

        addPage(baseTestPage);

        return;
    }

    /**
     * @return the baseTestPage
     */
    public CreateBaseTestPage getBaseTestPage() {
        return baseTestPage;
    }

    @Override
    public boolean performFinish() {

        try {

            //
            // Get the target test project from selection page.
            IProject testProject = baseTestPage.getTestProject();

            //
            // Get the test plugin manifest
            Manifest manifest = getManifest(testProject);

            if (manifest != null) {
                //
                // Save all the selected studio runtime resources to the user
                // defined resource folder within the plugin.
                IFolder testResourceFolder = getTestResourceFolder(testProject);

                //
                // COnfirm overwrites.
                if (!confirmOverwrites(testProject)) {
                    return false;
                }

                //
                // Save the studio resources to designated test plugin resource
                // folder.
                Collection<String> testResourceInfoPaths =
                        saveTestResources(selectedStudioResources,
                                testResourceFolder,
                                manifest,
                                baseTestPage.isIncludeDotResources());

                String testPluginId =
                        getManifestAttribute(manifest, MF_BUNDLE_SYMBOLICNAME);

                String testClassContent =
                        generateTestClassContent(baseTestPage.getTestName(),
                                baseTestPage.getTestClassName(),
                                baseTestPage.getTestPackageId(),
                                testPluginId,
                                baseTestPage
                                        .getTestRelativeBaseResourceFolder(),
                                testResourceInfoPaths);

                /*
                 * Save it to file.
                 */
                saveTestClassContent(testClassContent, manifest);

                /*
                 * Generate / Update the AllTests.java file if necessary.
                 */
                generateAndCreateAllTestsClass();

                /*
                 * Ensure that required plugins core.test and core.testutil are
                 * in required bundles in manifest.
                 */
                updateManifest(testProject, manifest);

                /*
                 * Create a test.xml file if not there already.
                 */
                addTestXml(testProject, testPluginId);

                /*
                 * Update the build properties file (ensure it has the resource
                 * base folder included in bin.includes)
                 */
                updateBuildProperties(testProject);

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Return a list of the entries that should be included in the
     * build.properties/bin.includes property.
     * <p>
     * 
     * @return list
     */
    protected Set<String> getRequiredBinIncludes() {
        Set<String> includes = new LinkedHashSet<String>();

        String includeTestResourceFolder =
                baseTestPage.getTestRelativeBaseResourceFolder() + "/"; //$NON-NLS-1$
        includes.add(includeTestResourceFolder);

        includes.add(TEST_XML);

        return includes;
    }

    /**
     * Ensure that the list of resources returned by getRequiredBinIncludes() is
     * present in the build.properties/bin.includes property.
     * 
     * @param testProject
     * @throws IOException
     * @throws CoreException
     */
    protected void updateBuildProperties(IProject testProject) {
        IFile buildProperties = testProject.getFile(BUILD_PROPERTIES);
        if (buildProperties.exists()) {
            Properties properties = new Properties();

            try {
                properties.load(buildProperties.getContents(true));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (CoreException e1) {
                e1.printStackTrace();
            }

            String binIncludes = properties.getProperty(BIN_INCLUDES);

            Set<String> requiredBinIncludes = getRequiredBinIncludes();

            if (binIncludes != null) {
                String[] includes = binIncludes.split(","); //$NON-NLS-1$

                for (String include : includes) {
                    if (requiredBinIncludes.contains(include)) {
                        // Already there so no longer required.
                        requiredBinIncludes.remove(include);
                    }
                }
            }

            if (!requiredBinIncludes.isEmpty()) {

                String newBinIncludes = binIncludes != null ? binIncludes : ""; //$NON-NLS-1$

                for (String required : requiredBinIncludes) {
                    if (newBinIncludes.length() > 0) {
                        newBinIncludes += ","; //$NON-NLS-1$
                    }
                    newBinIncludes += required;
                }

                properties.setProperty(BIN_INCLUDES, newBinIncludes);

                ByteArrayOutputStream outStream = null;

                try {
                    outStream = new ByteArrayOutputStream();
                    properties.store(outStream, null);

                    ByteArrayInputStream inputStream =
                            new ByteArrayInputStream(outStream.toByteArray());

                    buildProperties.setContents(inputStream,
                            IResource.FORCE,
                            new NullProgressMonitor());

                    testProject.refreshLocal(IResource.DEPTH_INFINITE,
                            new NullProgressMonitor());

                } catch (Exception e) {
                    e.printStackTrace();

                    MessageDialog
                            .openError(getShell(),
                                    Messages.CreateBaseTestWizard_title,
                                    Messages.CreateBaseTestWizard_FailedUpdatingBuildProperties_message
                                            + buildProperties.getFullPath()
                                                    .toFile());

                    throw new RuntimeException(
                            "Failed updating plug-in build properties: " //$NON-NLS-1$
                                    + buildProperties.getFullPath().toFile());

                } finally {
                    if (outStream != null) {
                        try {
                            outStream.close();
                            buildProperties.refreshLocal(1,
                                    new NullProgressMonitor());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * If necessary check for any required updates to the test manifest.
     * <p>
     * In general, any changes should be confirmed with the user.
     * 
     * @param testproject
     * @param manifest
     */
    protected void updateManifest(IProject testProject, Manifest manifest) {
        Collection<String> requiredPlugins = getRequiredPlugins();

        if (requiredPlugins != null && !requiredPlugins.isEmpty()) {
            //
            // Check if the test plugin bundle has necessary required bundles.

            String bundles = getManifestAttribute(manifest, MF_REQUIRE_BUNDLE);

            String newBundles = ""; //$NON-NLS-1$

            List<String> missingPluginIds = new ArrayList<String>();

            if (bundles == null || bundles.length() == 0) {
                for (String plugin : requiredPlugins) {
                    if (newBundles.length() != 0) {
                        newBundles += ",\n "; //$NON-NLS-1$
                    }

                    newBundles += plugin;

                    int idx = plugin.indexOf(";"); //$NON-NLS-1$
                    if (idx > 0) {
                        missingPluginIds.add(plugin.substring(0, idx));
                    } else {
                        missingPluginIds.add(plugin);
                    }
                }

            } else {
                //
                // Rebuild the bundle list with the new one.

                for (String plugin : requiredPlugins) {
                    int idx = plugin.indexOf(";"); //$NON-NLS-1$
                    if (idx > 0) {
                        missingPluginIds.add(plugin.substring(0, idx));
                    } else {
                        missingPluginIds.add(plugin);
                    }
                }

                String[] bundleArray = bundles.split(","); //$NON-NLS-1$

                newBundles = ""; //$NON-NLS-1$

                for (String bundle : bundleArray) {
                    bundle.trim();

                    // Append the bundle to the end.
                    if (newBundles.length() != 0) {
                        newBundles += ",\n "; //$NON-NLS-1$
                    }

                    newBundles += bundle;

                    // remove the found bundle from the list of plugin id's.
                    int idx = bundle.indexOf(";"); //$NON-NLS-1$
                    if (idx > 0) {
                        bundle = bundle.substring(0, idx);
                    }

                    missingPluginIds.remove(bundle);
                }

                // Add any missing ones.
                for (String pluginId : missingPluginIds) {
                    // Append the bundle to the end.
                    if (newBundles.length() != 0) {
                        newBundles += ",\n "; //$NON-NLS-1$
                    }

                    for (String plugin : requiredPlugins) {
                        if (plugin.equals(pluginId)
                                || plugin.startsWith(pluginId + ";")) { //$NON-NLS-1$
                            newBundles += plugin;
                            break;
                        }
                    }
                }
            }

            String strippedNewBundles = newBundles.replaceAll(" ", ""); //$NON-NLS-1$ //$NON-NLS-2$
            strippedNewBundles = strippedNewBundles.replaceAll("\n", ""); //$NON-NLS-1$ //$NON-NLS-2$

            if (!strippedNewBundles.equals(bundles)) {
                String missingPluginsDesc = ""; //$NON-NLS-1$
                for (String id : missingPluginIds) {
                    missingPluginsDesc += "    " + id + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
                }

                String msg =
                        Messages.CreateBaseTestWizard_MissingReuqirePlugins_message;

                if (MessageDialog.openQuestion(getShell(),
                        Messages.CreateBaseTestWizard_title,
                        String.format(msg, missingPluginsDesc))) {

                    // Overwrite the required bundles.
                    manifest.getMainAttributes().putValue(MF_REQUIRE_BUNDLE,
                            newBundles);

                    IFile manifestFile =
                            testProject.getFile(JAVA_META_INF_FOLDERNAME + "/" //$NON-NLS-1$
                                    + JAVA_MANIFEST_FILENAME);

                    ByteArrayOutputStream outStream = null;

                    try {
                        outStream = new ByteArrayOutputStream();
                        manifest.write(outStream);

                        ByteArrayInputStream inputStream =
                                new ByteArrayInputStream(
                                        outStream.toByteArray());

                        manifestFile.setContents(inputStream,
                                IResource.FORCE,
                                new NullProgressMonitor());

                        testProject.refreshLocal(IResource.DEPTH_INFINITE,
                                new NullProgressMonitor());

                    } catch (Exception e) {
                        e.printStackTrace();

                        MessageDialog
                                .openError(getShell(),
                                        Messages.CreateBaseTestWizard_title,
                                        Messages.CreateBaseTestWizard_FailedUpdatingManifest_message
                                                + manifestFile.getFullPath()
                                                        .toFile());

                        throw new RuntimeException(
                                "Failed updating plug-in manifest: " //$NON-NLS-1$
                                        + manifestFile.getFullPath().toFile());

                    } finally {
                        if (outStream != null) {
                            try {
                                outStream.close();
                                manifestFile.refreshLocal(1,
                                        new NullProgressMonitor());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * Add a test.xml file to the plugin (so that it can be run on hudson build
     * test).
     * <p>
     * Only if does not already exist.
     * 
     * @param testProject
     * @param testPluginId
     */
    protected void addTestXml(IProject testProject, String testPluginId) {
        IFile testXmlFile = testProject.getFile(TEST_XML);
        if (!testXmlFile.exists()) {

            TestXmlGeneratorData data = new TestXmlGeneratorData(testPluginId);

            TestXmlGenerator testXmlGenerator = new TestXmlGenerator();

            String testXml = testXmlGenerator.generate(data);

            createFile(testXml, testXmlFile);
        }

        return;
    }

    /**
     * Return a list of plugin's required by the test plugin.
     * <p>
     * The updateManifest() method will check the existance of these plugins and
     * add to the plugin upon confirmation from user.
     * 
     * @return List of plugin's required by the test plugin.
     */
    protected Collection<String> getRequiredPlugins() {
        Set<String> requiredPlugins = new HashSet<String>();

        requiredPlugins.add("com.tibco.xpd.core.test;visibility:=reexport"); //$NON-NLS-1$
        requiredPlugins.add(TestUtilPlugin.PLUGIN_ID);

        String testSuperPlugIn = getTestSuperClassPluginId();
        if (testSuperPlugIn != null && testSuperPlugIn.length() > 0) {
            requiredPlugins.add(testSuperPlugIn);
        }

        return requiredPlugins;
    }

    /**
     * @param testProject
     * @return test resource target folder
     */
    private IFolder getTestResourceFolder(IProject testProject) {
        IPath path = baseTestPage.getTestRelativeBaseResourceFolder();
        if (path != null) {
            IFolder testResourceFolder = testProject.getFolder(path);
            return testResourceFolder;
        }
        return null;
    }

    /**
     * If the java class file exists then get user confirmation of the
     * overwrite.
     * <p>
     * Same thing for the test plugin resource base folder.
     * 
     * @param testProject
     * @return
     */
    private boolean confirmOverwrites(IProject testProject) {
        IFile classFile = getJavaTestClassFile();
        if (classFile.exists()) {
            if (!MessageDialog
                    .openQuestion(getShell(),
                            Messages.CreateBaseTestWizard_title,
                            String.format(Messages.CreateBaseTestWizard_OverwriteClassMessage_message,
                                    classFile.getFullPath().toString()))) {
                return false;
            }
        }

        IFolder testResourceFolder = getTestResourceFolder(testProject);
        if (testResourceFolder != null && testResourceFolder.exists()) {
            if (!MessageDialog
                    .openQuestion(getShell(),
                            Messages.CreateBaseTestWizard_title,
                            String.format(Messages.CreateBaseTestWizard_ResourceFolderExists_message,
                                    testResourceFolder.getFullPath().toString()))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param manifest
     * @param attr
     * @return Main attribute from the given manifest.
     */
    protected String getManifestAttribute(Manifest manifest, String attr) {

        // On some occassions - if the plugin contributes to extension points,
        // the bundle symbolic name ends up with XXX;singleton=true. This when
        // included in thet test plugin may cause the test to fail.
        // Therefore, forming the test plugin id with the symbolic name without
        // the part after the ";"
        String bundleSymbolicName = manifest.getMainAttributes().getValue(attr);
        if (bundleSymbolicName != null && bundleSymbolicName.indexOf(";") != -1) {
            return bundleSymbolicName.substring(0,
                    bundleSymbolicName.indexOf(";"));
        }

        return bundleSymbolicName;
    }

    /**
     * Load the user selected test plugin's manifest file.
     * 
     * @param testProject
     * @return
     */
    private Manifest getManifest(IProject testProject) {
        Manifest manifest = null;

        IFile manifestFile = testProject.getFile(JAVA_META_INF_FOLDERNAME + "/" //$NON-NLS-1$
                + JAVA_MANIFEST_FILENAME);
        if (manifestFile.exists()) {
            InputStream manifestStream = null;
            try {
                manifestStream = manifestFile.getContents();

                manifest = new Manifest(manifestStream);

            } catch (CoreException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (manifestStream != null) {
                    try {
                        manifestStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        if (manifest == null) {
            MessageDialog.openError(getShell(),
                    Messages.CreateBaseTestWizard_FileCreationError_title,
                    Messages.CreateBaseTestWizard_CannotAccessManifest_message
                            + manifestFile.getFullPath());
        }

        return manifest;
    }

    /**
     * @param testClassContent
     * @param testPluginManifest
     */
    protected void saveTestClassContent(String testClassContent,
            Manifest testPluginManifest) {

        IFile javaFile = getJavaTestClassFile();

        createFile(testClassContent, javaFile);

        return;
    }

    /**
     * @return
     */
    private IFile getJavaTestClassFile() {
        IFile javaFile =
                baseTestPage.getTestPackageFolder()
                        .getFile(baseTestPage.getTestClassName()
                                + CreateBaseTestPage.JAVA_EXT);
        return javaFile;
    }

    /**
     * Generate the base junit test class according to the given parameters.
     * 
     * @param testName
     * @param testClassName
     * @param testPackageId
     * @param testPluginId
     * @param baseTestPluginResourcePath
     * @param testResourceInfoPaths
     */
    protected String generateTestClassContent(String testName,
            String testClassName, String testPackageId, String testPluginId,
            IPath baseTestPluginResourcePath,
            Collection<String> testResourceInfoPaths) {

        //
        // Create a class to pass all data to the BaseTestJavaClassGenerator.
        String[] resourceInfoPathArray =
                testResourceInfoPaths.toArray(new String[testResourceInfoPaths
                        .size()]);

        Class testSuperClass = getTestSuperClass();

        BaseTestJavaClassGeneratorData generatorData =
                new BaseTestJavaClassGeneratorData(testName, testClassName,
                        testSuperClass.getSimpleName(), testSuperClass
                                .getPackage().getName(),
                        CreateBaseTestPage.toJavaName(testClassName),
                        testPluginId, testPackageId,
                        baseTestPluginResourcePath.toString(),
                        resourceInfoPathArray);

        //
        // Generate the base test class.
        BaseTestJavaClassGenerator baseTestClassGenerator =
                new BaseTestJavaClassGenerator();

        String baseTestClass = baseTestClassGenerator.generate(generatorData);

        return baseTestClass;
    }

    /**
     * This method returns the super class that should be used by the test class
     * created by the wizard.
     * 
     * @return the super class for test - this MUST subclass
     *         AbstractBaseResourceTest.class
     */
    protected Class getTestSuperClass() {
        return AbstractBaseResourceTest.class;
    }

    /**
     * @return The plugin in which the generated test's super class resides.
     */
    protected String getTestSuperClassPluginId() {
        return TestUtilPlugin.PLUGIN_ID;
    }

    /**
     * Save the given runtime studio resources to the given base test resource
     * folder.
     * 
     * @param studioResources
     * @param testResourceFolder
     * @param testPluginManifest
     * 
     * @return List of runtime project (and therefore test base resource folder)
     *         relative paths for the resources (in special TestResourceInfo
     *         style tokenised path).
     */
    protected Collection<String> saveTestResources(
            Collection<IResource> studioResources, IFolder testResourceFolder,
            Manifest testPluginManifest, boolean includeDotResources) {
        List<String> testResources = new ArrayList<String>();

        if (testResourceFolder != null) {
            //
            // Ensure that the base test resource folder exists.
            if (!testResourceFolder.exists()) {
                createFolderPath(testResourceFolder);
            }

            //
            // Add all the selected studio resources to the test.

            for (IResource resource : studioResources) {
                recursiveSaveTestResources(testResources,
                        resource,
                        testResourceFolder,
                        includeDotResources);
            }
        }

        return testResources;
    }

    /**
     * Save an individual resource. If it is a folder then create equivalent
     * folder in base test resources and recursively add child folders/files.
     * 
     * @param testResourceInfoPaths
     * @param resource
     * @param testResourceFolder
     * @param includeDotResources
     */
    private void recursiveSaveTestResources(
            final List<String> testResourceInfoPaths, IResource resource,
            final IFolder testResourceFolder, final boolean includeDotResources) {

        if (resource.getName().startsWith(".") && !includeDotResources) { //$NON-NLS-1$
            // Ignore ".*" resources unless user wants to include them
            return;
        }

        if (resource instanceof IFile) {
            IFile file = (IFile) resource;

            //
            // Add the file to the test plugin resources.

            // Ensure target directory exists.
            IPath testFolderPath = new Path(file.getProject().getName());
            testFolderPath =
                    testFolderPath.append(file.getParent()
                            .getProjectRelativePath());
            IFolder testFolder = testResourceFolder.getFolder(testFolderPath);

            if (!testFolder.exists()) {
                createFolderPath(testFolder);
            }

            //
            // Copy the file to it.
            saveToTestFolder(file, testFolder);

            //
            // And create a TestResourceInfo style tokenised path to it.
            String testResourceInfoPath =
                    TestResourceInfo.createTokenisedTestResourceInfoPath(file);

            testResourceInfoPaths.add(testResourceInfoPath);

        } else if (resource instanceof IContainer) {
            final IContainer folderOrProject = (IContainer) resource;

            //
            // Create the folder in the test plugin's resource folder.
            // Ensure target directory exists.

            IPath testFolderPath =
                    new Path(folderOrProject.getProject().getName());
            testFolderPath =
                    testFolderPath.append(folderOrProject
                            .getProjectRelativePath());
            IFolder testFolder = testResourceFolder.getFolder(testFolderPath);

            if (!testFolder.exists()) {
                createFolderPath(testFolder);
            }

            if (!testFolder.exists()) {
                createFolderPath(testFolder);
            }

            // Now recurs to include all children.
            try {
                IResourceVisitor visitor = new IResourceVisitor() {
                    public boolean visit(IResource nextResource)
                            throws CoreException {
                        if (nextResource != folderOrProject) {
                            recursiveSaveTestResources(testResourceInfoPaths,
                                    nextResource,
                                    testResourceFolder,
                                    includeDotResources);
                        }

                        return true;
                    }
                };
                folderOrProject.accept(visitor,
                        IResource.DEPTH_ONE,
                        IResource.NONE);

            } catch (CoreException e) {
            }

        }

        return;
    }

    /**
     * @param file
     * @param testFolder
     */
    protected void saveToTestFolder(IFile file, IFolder testFolder) {
        InputStream inputStream = null;

        IFile testResource = testFolder.getFile(file.getName());

        try {
            inputStream = file.getContents(true);

            if (testResource.exists()) {
                testResource.setContents(inputStream,
                        true,
                        true,
                        new NullProgressMonitor());
            } else {
                testResource.create(inputStream,
                        true,
                        new NullProgressMonitor());
            }

        } catch (Exception e) {
            e.printStackTrace();

            String format = Messages.CreateBaseTestWizard_13;

            MessageDialog
                    .openError(getShell(),
                            Messages.CreateBaseTestWizard_ResourceSaveError_title,
                            String.format(format, file.getProjectRelativePath()
                                    .toString(), testResource.getFullPath()
                                    .toString()));
            throw new RuntimeException(
                    "Could not copy test resource to test plugin: " //$NON-NLS-1$
                            + testResource.getFullPath().toString());

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Create project export folders in the workspace
     * 
     * @param folder
     * @throws CoreException
     */
    protected void createFolderPath(IFolder folder) {
        try {
            // Refresh the folder to align it with the file system
            if (!folder.isSynchronized(IResource.DEPTH_ONE)) {
                folder.refreshLocal(IResource.DEPTH_ONE, null);
            }

            if (!folder.exists()) {
                IContainer parent = folder.getParent();

                if (parent instanceof IFolder) {
                    IFolder parentFolder = (IFolder) parent;
                    // Create parent folder
                    createFolderPath(parentFolder);
                }
                // Create current folder
                folder.create(true, true, null);
            }

        } catch (CoreException e) {
            String format =
                    Messages.CreateBaseTestWizard_FailedToCreateTestResourceFolder_message;
            MessageDialog.openError(getShell(),
                    Messages.CreateBaseTestWizard_FileCreationError_title,
                    String.format(format, folder.toString()));

            throw new RuntimeException("Could not create  folder '" //$NON-NLS-1$
                    + folder.toString() + "'."); //$NON-NLS-1$

        }

        return;
    }

    /**
     * Create the AllTests.java class.
     */
    protected void generateAndCreateAllTestsClass() {
        if (baseTestPage.isGenerateAllTestsClass()) {

            Set<String> selectedAllTestsClassNames =
                    baseTestPage.getSelectedAllTestsClassNames();

            if (!selectedAllTestsClassNames.isEmpty()) {

                IFolder packageFolder = baseTestPage.getTestPackageFolder();

                IFile allTestsFile =
                        packageFolder.getFile("AllTests"
                                + CreateBaseTestPage.JAVA_EXT);

                AllTestsClassGeneratorData data =
                        new AllTestsClassGeneratorData(
                                selectedAllTestsClassNames,
                                baseTestPage.getTestPackageId());

                AllTestsClassGenerator allTestsClassGenerator =
                        new AllTestsClassGenerator();

                String content = allTestsClassGenerator.generate(data);

                createFile(content, allTestsFile);
            }
        }
        return;
    }

    /**
     * Create the given file with the given content.
     * 
     * @param content
     * @param file
     */
    private void createFile(String content, IFile file) {
        ByteArrayInputStream inStream =
                new ByteArrayInputStream(content.getBytes());

        try {
            if (file.exists()) {
                file.setContents(inStream,
                        true,
                        true,
                        new NullProgressMonitor());
            } else {
                file.create(inStream, true, new NullProgressMonitor());
            }

        } catch (CoreException e) {
            String format =
                    Messages.CreateBaseTestWizard_FailedToCreateFile_message;

            MessageDialog.openError(getShell(),
                    Messages.CreateBaseTestWizard_FileCreationError_title,
                    String.format(format, file.getFullPath().toString()));

            throw new RuntimeException(
                    "Failed to create file: " + file.getFullPath().toString()); //$NON-NLS-1$
        }

        return;
    }
}
