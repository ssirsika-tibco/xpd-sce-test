package com.tibco.bds.designtime.generator.test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.bds.designtime.generator.ModelGenerator;
import com.tibco.xpd.bom.gen.api.BOMGenerator2;
import com.tibco.xpd.bom.gen.api.GeneratorData;
import com.tibco.xpd.bom.gen.api.GeneratorData.BuildType;
import com.tibco.xpd.bom.gen.biz.BOMIllegalStateException;
import com.tibco.xpd.bom.gen.biz.GenerationException;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;
import com.tibco.xpd.bom.gen.util.DependencyAnalyzer;

public class PerformanceTest extends BaseTest {

    private final long TIME_LIMIT_MS = 120000;

    public void testGeneration() throws CoreException, IOException,
            GenerationException, BOMIllegalStateException {
        IProject project = createBOMProject("BOMProj",null); //$NON-NLS-1$
        configureN2Destination(project,null);
        IFolder folder = createBusinessObjectsFolderInProject(project);
        File file =
                new File(folder.getLocation().toFile(), "Comprehensive.bom"); //$NON-NLS-1$

        writeResourceToFile(RESOURCE_ROOT + "/Comprehensive.bom", file); //$NON-NLS-1$

        project.refreshLocal(IResource.DEPTH_INFINITE, null);

        // Invoke once and ignore result, to absorb initialization overhead.
        final BOMGenerator2 bg = new ModelGenerator();
        BOMGenerator2Extension ext =
                BOMGenerator2ExtensionHelper.getInstance()
                        .getExtension("com.tibco.bds.designtime.generator"); //$NON-NLS-1$
        bg.setExtension(ext);
        final Collection<IFile> bomFile =
                Collections.singleton((IFile) project
                        .findMember("Business Objects/Comprehensive.bom")); //$NON-NLS-1$
        final GeneratorData generatorData =
                new GeneratorData(BuildType.FULL, bomFile);
        bg.setDependencyProvider(new DependencyAnalyzer(bomFile));
        bg.initialize(bomFile, generatorData, new NullProgressMonitor());
        bg.prepareForBuildCycle(project, BuildType.FULL);
        bg.generate(bomFile, generatorData, new NullProgressMonitor());
        // BOMGenerator.generateRuntimeModel(bomResource);

        long totalMilli = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("Test iteration %d", i)); //$NON-NLS-1$
            final IWorkspace workspace = ResourcesPlugin.getWorkspace();
            long startNano = System.nanoTime();
            workspace.run(new IWorkspaceRunnable() {
                public void run(IProgressMonitor monitor) throws CoreException {
                    try {
                        // BOMGenerator.generateRuntimeModel(bomResource);
                        bg.generate(bomFile,
                                generatorData,
                                new NullProgressMonitor());
                    } catch (CoreException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }, null);
            long endNano = System.nanoTime();
            long elapsedMilli = (endNano - startNano) / 1000000;
            totalMilli += elapsedMilli;
            System.out.println(String
                    .format("Iteration %d took %dms. Total now %dms", //$NON-NLS-1$
                            i,
                            elapsedMilli,
                            totalMilli));
        }

        boolean success = totalMilli <= TIME_LIMIT_MS;
        bg.buildCycleComplete(project);
        assertTrue(String
                .format("Generation time exceeded %dms limit. It was %dms", //$NON-NLS-1$
                        TIME_LIMIT_MS,
                        totalMilli), success);
    }
}
