package com.tibco.bds.designtime.generator.test;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.bds.designtime.generator.ModelGenerator;
import com.tibco.bds.designtime.generator.Context;
import com.tibco.bds.designtime.generator.test.util.diff.Differ;
import com.tibco.bds.designtime.generator.test.util.diff.Difference;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;

public abstract class AbstractXsdBasedTest extends BaseTest {

    private String resourceFolder;

    public AbstractXsdBasedTest(String resourceFolder) {
        this.resourceFolder = resourceFolder;
    }

    protected void generateBDSFromXSD(String coreFileNameString) {

        final String xsdFileString = coreFileNameString + ".xsd"; //$NON-NLS-1$
        final String knownGoodOutput =
                coreFileNameString + "-known-good-output.zip"; //$NON-NLS-1$

        try {

            // Create an instance of the generator that we can use
            ModelGenerator bg = new ModelGenerator();
            bg.setExtension(BOMGenerator2ExtensionHelper.getInstance()
                    .getExtension("com.tibco.bds.designtime.generator")); //$NON-NLS-1$

            IProject newProject = createEMFProject("." + coreFileNameString); //$NON-NLS-1$
            IFolder modelFolder = (IFolder) newProject.findMember("model"); //$NON-NLS-1$

            File xsdFile =
                    modelFolder.getLocation().append(xsdFileString).toFile();

            writeResourceToFile(String.format("%s/%s/%s", //$NON-NLS-1$
                    RESOURCE_ROOT,
                    resourceFolder,
                    xsdFileString), xsdFile);

            newProject.refreshLocal(IResource.DEPTH_INFINITE, null);

            Context ctx = new Context();
            ctx.setOutputProject(newProject);
            ctx.setBundleName(coreFileNameString);
            ctx.setBundleVersion("1.0"); //$NON-NLS-1$
            bg.generateModelProjectFromXSDs(ctx, findXSDsInFolder(modelFolder));

            // Refresh the project
            newProject.refreshLocal(IResource.DEPTH_INFINITE, null);

            // Extract our comparison data
            File zipExportRoot = File.createTempFile("temp", "zip"); //$NON-NLS-1$ //$NON-NLS-2$
            zipExportRoot.deleteOnExit();
            unzipFromResource(String.format("%s/%s/%s", //$NON-NLS-1$
                    RESOURCE_ROOT,
                    resourceFolder,
                    knownGoodOutput), zipExportRoot);

            assertTrue("Output " + newProject + " project doesn't exist", //$NON-NLS-1$ //$NON-NLS-2$
                    newProject.exists());

            // Compare all the files
            List<Difference> diffs =
                    Differ.diff(zipExportRoot, newProject.getLocation()
                            .toFile(), s_ignoreNames, s_ignoreContents);

            assertTrue("Unexpected diffs " + diffs, diffs.isEmpty()); //$NON-NLS-1$

            // Do a test build of the new project
            buildProject(newProject);
            
            // Remove the project
            cleanUp(newProject);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
