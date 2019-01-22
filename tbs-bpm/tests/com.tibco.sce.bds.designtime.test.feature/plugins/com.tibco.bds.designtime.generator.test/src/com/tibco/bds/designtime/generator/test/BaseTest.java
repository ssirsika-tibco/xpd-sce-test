package com.tibco.bds.designtime.generator.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IMarkerResolution;

import com.tibco.bds.designtime.generator.AbstractBDSGenerator;
import com.tibco.bds.designtime.generator.ModelGenerator;
import com.tibco.xpd.bom.gen.api.GeneratorData;
import com.tibco.xpd.bom.gen.api.GeneratorData.BuildType;
import com.tibco.xpd.bom.gen.biz.BOMIllegalStateException;
import com.tibco.xpd.bom.gen.biz.GenerationException;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.gen.util.DependencyAnalyzer;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.InvalidFileProblemMarkerResolutionGenerator;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.ui.wizards.newproject.XpdProjectWizard;
import com.tibco.xpd.ui.wizards.newproject.XpdProjectWizard.CreateXpdProjectOperation;

import junit.framework.TestCase;

@SuppressWarnings("restriction")
public abstract class BaseTest extends TestCase {

    /**
     * Studio performance enhancements mean that generated projects will have no
     * builders associated with them. If you intend to run the tests and then
     * open the workspace to examine the results, then it may be useful to set
     * this flag. It causes Java/PDE builders to be added to generated projects.
     * Otherwise, you won't see any error markers, even if incompatible/invalid
     * code has been produced.
     */
    private static final boolean addBuilders = true;

    protected static boolean buildBdsProjects = true;

    protected static final String RESOURCE_ROOT =
            "/com/tibco/bds/designtime/generator/test/resources"; //$NON-NLS-1$

    protected List<String> s_ignoreNames = Arrays.asList(new String[] {
            ".*\\.prefs", ".*\\.classpath", ".*\\\\bin\\\\.*", ".*\\\\bin", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            // Exclude the templates directory
            ".*\\.jetproperties", ".*\\.project", //$NON-NLS-1$ //$NON-NLS-2$
            // Various run-time test JARs have been regenerated and it
            // proved that recent code changes to BDSValidationUtils
            // behave correctly.
            // Therefore, to avoid a full update of all test jars,
            // comparison is temporarily disabled. Next time a full
            // JAR regenerate is performed, this exclusion can be removed.
            ".*\\\\bdsutil\\\\BDSNotifyingCalendar.java", //$NON-NLS-1$
            // For some reason different passes of the XSD will change
            // the order that elements and complex object appear at the
            // top level for this reason we have no choice but to
            // exclude it
            ".*\\.xsd" }); //$NON-NLS-1$

    protected Map<String, List<String>> s_ignoreContents;

    public BaseTest() {
        s_ignoreContents = new HashMap<String, List<String>>();

        // We use GUIDs in index names, so they differ on each run
        s_ignoreContents.put(".*\\.ecore", //$NON-NLS-1$
                Arrays.asList(new String[] {
                        "value=\"@Index\\(name=&quot;idx_[0-9a-f_]{26}&quot;\\)" })); //$NON-NLS-1$

        // As with the ecore file, but the annotations are expressed in a
        // slightly different format.
        // Also, whitespace ignored.
        s_ignoreContents.put(".*\\.java", //$NON-NLS-1$
                Arrays.asList(new String[] {
                        "@Index\\(name=\\\\\"idx_[0-9a-f_]{26}\\\\\"\\)", //$NON-NLS-1$
                        "\\s" })); //$NON-NLS-1$

        // When comparing two schemas, remove the IDs before doing so
        // as these can change within Studio, and have no impact on EMF. Also
        // strip the imports as these can be in different orders
        s_ignoreContents.put(".*\\.xsd", //$NON-NLS-1$
                Arrays.asList(new String[] { "id=\"[A-Za-z0-9_-]*\"", //$NON-NLS-1$
                        "<xsd:import namespace=\"[A-Za-z0-9:./]*\" schemaLocation=\"[A-Za-z0-9.]*\"/>", //$NON-NLS-1$
                        "\\W" })); //$NON-NLS-1$
    }

    @Override
    public void setUp() throws CoreException {
        IWorkspace ws = ResourcesPlugin.getWorkspace();
        IWorkspaceDescription desc = ws.getDescription();
        desc.setAutoBuilding(false);
        ws.setDescription(desc);
    }

    protected void unzipFromResource(String resourceName, File targetFolder)
            throws IOException {
        if (targetFolder.isFile()) {
            targetFolder.delete();
        }
        targetFolder.mkdirs();
        ZipInputStream zis = new ZipInputStream(
                getClass().getResourceAsStream(resourceName));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            File targetFile = new File(targetFolder, entry.getName());
            if (entry.isDirectory()) {
                targetFile.mkdirs();
            } else {
                targetFile.getParentFile().mkdirs();
                copyStreamToStream(zis, new FileOutputStream(targetFile));
            }
            targetFile.deleteOnExit();
        }
    }

    protected void writeResourceToFile(String resourcePath, File file)
            throws IOException {
        InputStream resis = BaseTest.class.getResourceAsStream(resourcePath);
        FileOutputStream fos = new FileOutputStream(file);
        copyStreamToStream(resis, fos);
    }

    protected void copyStreamToStream(InputStream in, OutputStream out)
            throws IOException {
        BufferedInputStream bis = new BufferedInputStream(in);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] buf = new byte[256];
        int byteCount;
        while ((byteCount = bis.read(buf)) != -1) {
            bos.write(buf, 0, byteCount);
        }
        bos.flush();
        bos.close();
    }

    protected IFolder createBusinessObjectsFolderInProject(IProject project)
            throws CoreException {
        IFolder folder = project.getFolder("Business Objects"); //$NON-NLS-1$
        if (!folder.exists()) {
            folder = TestUtil.createSpecialFolder(project,
                    "Business Objects", //$NON-NLS-1$
                    BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND).getFolder();
        }
        return folder;
    }

    protected IProject createBOMProject(String name, String version)
            throws CoreException {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProject bomProject = workspace.getRoot().getProject(name);

        // Create the project, unless it already exists
        if (!bomProject.exists()) {
            final IProjectDescription description =
                    workspace.newProjectDescription(bomProject.getName());
            ProjectDetails details =
                    ProjectConfigFactory.eINSTANCE.createProjectDetails();
            setVersion(version, details);
            CreateXpdProjectOperation op =
                    new XpdProjectWizard.CreateXpdProjectOperation(bomProject,
                            description, null, details);

            try {
                op.run(new NullProgressMonitor());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault().getProjectConfig(bomProject);
        AssetType asset = ProjectConfigFactory.eINSTANCE.createAssetType();
        asset.setId("com.tibco.xpd.asset.bom"); //$NON-NLS-1$
        projectConfig.getAssetTypes().add(asset);
        if (projectConfig.getProjectDetails() == null) {
            ProjectDetails details =
                    ProjectConfigFactory.eINSTANCE.createProjectDetails();
            projectConfig.setProjectDetails(details);
        }

        return bomProject;
    }

    /**
     * Removed the projects generated during the test
     * 
     * @param outputProject
     */
    protected void cleanUp(IProject outputProject) {
        try {
            // Delete the test bds project
            outputProject.delete(true, null);

            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            // Fails silently if project(s) don't exist
            workspace.getRoot().getProject("BOMProj") //$NON-NLS-1$
                    .delete(true, new NullProgressMonitor());
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            root.refreshLocal(IResource.DEPTH_INFINITE, null);

        } catch (CoreException e) {
            // Don't fail the test if there is a problem doing the tidy-up
            // after the test has passed
        }
    }

    protected void configureN2Destination(IProject project, String version)
            throws CoreException {
        ProjectConfig projectConfig = XpdResourcesPlugin.getDefault()

                .getProjectConfig(project);

        if (projectConfig != null) {

            ProjectDetails projDetails =
                    ProjectConfigFactory.eINSTANCE.createProjectDetails();

            setVersion(version, projDetails);
            projectConfig.setProjectDetails(projDetails);

            Destinations createDestinations =
                    ProjectConfigFactory.eINSTANCE.createDestinations();

            projDetails.setGlobalDestinations(createDestinations);

            Destination destination =
                    ProjectConfigFactory.eINSTANCE.createDestination();

            destination.setType("BPM"); //$NON-NLS-1$

            createDestinations.getDestination().add(destination);

        }
        // Already has "com.tibco.xpd.resources.xpdNature"
        ProjectUtil.addNature(project,
                "com.tibco.xpd.bom.xsdtransform.xsdNature"); //$NON-NLS-1$
        ProjectUtil.addNature(project, "com.tibco.xpd.bom.gen.bomGenNature"); //$NON-NLS-1$

    }

    private void setVersion(String version, ProjectDetails details) {
        if (version == null) {

            details.setVersion("1.0.0.qualifier"); //$NON-NLS-1$
        } else {

            details.setVersion(version);
        }
    }

    /**
     * Construct an EMF project
     * 
     * @param projectName
     * @return
     * @throws CoreException
     * @throws GenerationException
     */
    protected IProject createEMFProject(String projectName)
            throws CoreException, GenerationException {
        // Dummy class to allow creation of project
        class TestGenerator extends ModelGenerator {
            @Override
            public void generate(Collection<IFile> bomResources,
                    GeneratorData data, IProgressMonitor monitor)
                    throws CoreException {
            }

            public IProject createProject(String projectName)
                    throws CoreException, GenerationException {
                IProject newProject = ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);
                TestGenerator tg = new TestGenerator();
                return tg.createProject(newProject, null, null); // Sets EMF
                // properties
                // on the
                // project
            }
        }

        TestGenerator tg = new TestGenerator();
        return tg.createProject(projectName);
    }

    /**
     * Migrate the BOM if it is an old BOM
     * 
     * @param file
     * @throws CoreException
     */
    protected void migrateBOM(IFile file) throws CoreException {
        // Check to see if there are any errors in the old BOM
        IMarker[] markers = file.findMarkers(XpdConsts.INVALID_FILE_MARKER,
                true,
                IResource.DEPTH_ZERO);
        // Most probably need to migrate this project
        for (IMarker iMarker : markers) {
            InvalidFileProblemMarkerResolutionGenerator probResolver =
                    new InvalidFileProblemMarkerResolutionGenerator();
            IMarkerResolution[] resolutions =
                    probResolver.getResolutions(iMarker);
            for (IMarkerResolution iMarkerResolution : resolutions) {
                iMarkerResolution.run(iMarker);
            }
        }
    }

    /**
     * Performs the generation of the BDS bundle from a given set of BOM Files
     * 
     * @param bomFiles
     *            The BOM files to generate from
     * @throws CoreException
     * @throws GenerationException
     * @throws BOMIllegalStateException
     */
    protected void doGeneration(IProject project, List<IFile> bomFiles,
            AbstractBDSGenerator bg, String extensionId) throws CoreException,
            GenerationException, BOMIllegalStateException {
        long stamp = System.currentTimeMillis();

        // Need to go through each of the files reloading them so
        // that Studio will mark them as dirty and re-check dependencies
        for (IFile file : bomFiles) {
            BOMWorkingCopy wc =
                    (BOMWorkingCopy) WorkingCopyUtil.getWorkingCopy(file);
            wc.reLoad();
            wc.getSaveable().doSave(null);
            migrateBOM(file);
        }

        BOMGenerator2Extension ext = BOMGenerator2ExtensionHelper.getInstance()
                .getExtension(extensionId);
        bg.setExtension(ext);

        GeneratorData gd = new GeneratorData(BuildType.FULL, bomFiles);
        bg.setDependencyProvider(new DependencyAnalyzer(bomFiles));
        bg.initialize(bomFiles, gd, new NullProgressMonitor());
        bg.prepareForBuildCycle(project, BuildType.FULL);
        bg.generate(bomFiles, gd, new NullProgressMonitor());
        bg.buildCycleComplete(project);
        if (addBuilders) {
            for (IFile bomFile : bomFiles) {
                bg.addJavaPDEBuildersToGeneratedProject(bomFile);
            }
        }
        System.out.println("Generation took " //$NON-NLS-1$
                + (System.currentTimeMillis() - stamp) + "ms for " + bomFiles); //$NON-NLS-1$
    }

    protected List<URI> findXSDsInFolder(IFolder folder) throws CoreException {
        List<URI> uris = new ArrayList<URI>();
        for (IResource xsdFile : folder.members()) {
            if (xsdFile.getType() == IResource.FILE) {
                // TODO use 'XSD' constant when rehomed
                if (xsdFile.getName().endsWith(".xsd")) { //$NON-NLS-1$
                    uris.add(URI
                            .createFileURI(xsdFile.getLocation().toString()));
                }
            }
        }
        return uris;
    }

    /**
     * Builds the given project
     * 
     * @param project
     */
    protected void buildProject(IProject project) {
        // Build the project to make sure there are no problems
        if (buildBdsProjects) {

            // Create a monitor class that we can use to track when a given
            // build has completed
            class BDSBuildMonitor implements IProgressMonitor {
                private boolean isComplete = false;

                @Override
                public void beginTask(String name, int totalWork) {
                }

                @Override
                public void done() {
                    isComplete = true;
                }

                @Override
                public void internalWorked(double work) {
                }

                @Override
                public boolean isCanceled() {
                    return false;
                }

                @Override
                public void setCanceled(boolean value) {
                    isComplete = true;
                }

                @Override
                public void setTaskName(String name) {
                }

                @Override
                public void subTask(String name) {
                }

                @Override
                public void worked(int work) {
                }

                // Method that gets called to wait for the build to finish
                public void waitForComplete() {
                    int i = 0;
                    // Limit the time we wait for the build - don't want to wait
                    // forever if there is a problem
                    while (i < 100) {
                        // If already complete - exit now
                        if (isComplete) {
                            break;
                        }
                        i++;
                        try {
                            // wait a little bit before checking again
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }

            try {
                BDSBuildMonitor myMonitor = new BDSBuildMonitor();
                // Start the build - this is async for doing the actual build
                project.build(IncrementalProjectBuilder.FULL_BUILD, myMonitor);
                // Now wait for the build to complete before exiting
                myMonitor.waitForComplete();
                // Check to see if there are any error markers
                int errorMark = project.findMaxProblemSeverity(null,
                        false,
                        IResource.DEPTH_INFINITE);
                if (errorMark == IMarker.SEVERITY_ERROR) {
                    fail("Build failed due to error marker"); //$NON-NLS-1$
                }
            } catch (Exception e) {
                e.printStackTrace();
                fail("Build failed with error: " + e.getLocalizedMessage()); //$NON-NLS-1$
            }
        }
    }

    /**
     * Checks if the target platform env is set
     * 
     * See the following for more information
     * http://confluence.tibco.com/pages/viewpage.action?pageId=48107100
     * 
     * @return
     */
    protected boolean isTargetPlatformSet() {
        boolean isTargetSet = false;

        String targetPlatEnv =
                System.getProperty("com.tibco.target.platform.location"); //$NON-NLS-1$

        if ((targetPlatEnv != null) && (!targetPlatEnv.isEmpty())) {
            isTargetSet = true;
        }
        return isTargetSet;
    }
}
