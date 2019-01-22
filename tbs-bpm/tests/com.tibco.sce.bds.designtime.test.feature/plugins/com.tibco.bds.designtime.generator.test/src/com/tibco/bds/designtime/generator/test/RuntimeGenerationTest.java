package com.tibco.bds.designtime.generator.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.tibco.bds.designtime.generator.AbstractBDSGenerator;
import com.tibco.bds.designtime.generator.test.util.diff.Differ;
import com.tibco.bds.designtime.generator.test.util.diff.DifferException;
import com.tibco.bds.designtime.generator.test.util.diff.Difference;
import com.tibco.xpd.bom.gen.biz.BOMIllegalStateException;
import com.tibco.xpd.bom.gen.biz.GenerationException;

/**
 * Note: Due to the way output is compared to previously-generated known-good
 * output (that contains absolute paths), this test must be run targeting
 * workspace path: E:\junitTest\n2-bom-gen-cds-emf-ws If you have no physical E
 * drive, then create a folder called C:\drive_e and enter the following command
 * from a command prompt: subst E: C:\drive_e
 * 
 * @author smorgan
 * 
 */
public abstract class RuntimeGenerationTest extends BaseTest {

    private static final String PROFILE_RESOURCE_PATH = RESOURCE_ROOT
            + "/profiles"; //$NON-NLS-1$

    private static final String BDS_PROFILE_UML = "bds.profile.uml"; //$NON-NLS-1$

    /**
     * Sub-classes should define this method to return an instance of the
     * BOMGenerator2 generator that they require.
     * 
     * @return
     */
    protected abstract AbstractBDSGenerator createGenerator();

    /**
     * Sub-classes should define this to return the generator extension id for
     * the generator they require (as defined in plugin.xml).
     * 
     * @return
     */
    protected abstract String getExtensionId();

    private void importBDSProfileIfAbsent(IFolder bomFolder)
            throws IOException, CoreException {
        if (bomFolder.findMember(BDS_PROFILE_UML) == null) {
            File file =
                    new File(bomFolder.getLocation().toFile(), BDS_PROFILE_UML);
            writeResourceToFile(String.format("%s/%s", //$NON-NLS-1$
                    PROFILE_RESOURCE_PATH,
                    BDS_PROFILE_UML), file);
            bomFolder.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
        }
    }

    protected void performTest(List<String> bomFileNames,
            String knownGoodOutputZipName, String outputProjectName,
            String version) throws CoreException, IOException,
            GenerationException, BOMIllegalStateException, DifferException {

        // Call the version that takes a Map, adding just the single item to the
        // Map and passing it in
        HashMap<String, String> outputDeatils = new HashMap<String, String>();
        outputDeatils.put(outputProjectName, knownGoodOutputZipName);

        performTest(bomFileNames, outputDeatils, version);
    }

    protected void performTest(List<String> bomFileNames,
            Map<String, String> outputDetails, String version)
            throws CoreException, IOException, GenerationException,
            BOMIllegalStateException, DifferException {
        IProject project = createBOMProject("BOMProj", version); //$NON-NLS-1$
        configureN2Destination(project, version);
        IFolder folder = createBusinessObjectsFolderInProject(project);
        importBDSProfileIfAbsent(folder);

        List<IFile> bomFiles = new ArrayList<IFile>();
        for (String bomFileName : bomFileNames) {
            File file = new File(folder.getLocation().toFile(), bomFileName);
            writeResourceToFile(RESOURCE_ROOT + "/" + bomFileName, file); //$NON-NLS-1$
            // TODO Avoid need for refresh by writing the file the 'eclipse way'
            project.refreshLocal(IResource.DEPTH_INFINITE, null);
            IFile bomFile =
                    (IFile) project.findMember("Business Objects/" //$NON-NLS-1$
                            + bomFileName);
            bomFiles.add(bomFile);
        }

        doGeneration(project, bomFiles, createGenerator(), getExtensionId());

        for (String outputProjectName : outputDetails.keySet()) {
            String knownGoodOutputZipName =
                    outputDetails.get(outputProjectName);

            File zipExportRoot = File.createTempFile("temp", "zip"); //$NON-NLS-1$ //$NON-NLS-2$
            zipExportRoot.deleteOnExit();
            unzipFromResource(RESOURCE_ROOT + "/" + knownGoodOutputZipName, //$NON-NLS-1$
                    zipExportRoot);

            IProject outputProject =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(outputProjectName);
            assertTrue("Output project doesn't exist", outputProject.exists()); //$NON-NLS-1$

            List<Difference> diffs =
                    Differ.diff(zipExportRoot, outputProject.getLocation()
                            .toFile(), s_ignoreNames, s_ignoreContents);

            assertTrue("Unexpected diffs " + diffs, diffs.isEmpty()); //$NON-NLS-1$

            // Do a test build of the new project
            buildProject(outputProject);
        }
        
        // Now all have been checked and built, remove them
        for (String outputProjectName : outputDetails.keySet()) {
            IProject outputProject =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(outputProjectName);
            cleanUp(outputProject);
        }
    }
}
