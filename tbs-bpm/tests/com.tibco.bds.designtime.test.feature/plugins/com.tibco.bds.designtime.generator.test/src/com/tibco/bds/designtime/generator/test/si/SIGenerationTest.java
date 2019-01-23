package com.tibco.bds.designtime.generator.test.si;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.tibco.bds.designtime.generator.AbstractBDSGenerator;
import com.tibco.bds.designtime.generator.ModelGenerator;
import com.tibco.bds.designtime.generator.si.SIGenerator;
import com.tibco.bds.designtime.generator.test.RuntimeGenerationTest;
import com.tibco.bds.designtime.generator.test.util.diff.DifferException;
import com.tibco.xpd.bom.gen.biz.BOMIllegalStateException;
import com.tibco.xpd.bom.gen.biz.GenerationException;

public class SIGenerationTest extends RuntimeGenerationTest {

    private String extendionId = "com.tibco.bds.designtime.generator.si"; //$NON-NLS-1$

    private boolean defaultBuildBdsProjects = true;

    protected AbstractBDSGenerator createGenerator() {
        if( extendionId.compareToIgnoreCase("com.tibco.bds.designtime.generator") == 0 ) { //$NON-NLS-1$
            return new ModelGenerator();
        }
        return new SIGenerator();
    }

    protected String getExtensionId() {
        return extendionId;
    }

    @Override
    public void setUp() throws CoreException {
        super.setUp();
        defaultBuildBdsProjects = buildBdsProjects;
        
        if( !isTargetPlatformSet() ) {
            buildBdsProjects = false;
        }
        
        // These tests depend on the Order Model, therefore if we are going to
        // build we need that model deployed
        if (buildBdsProjects) {
            String oldExtendionId = extendionId;
            extendionId = "com.tibco.bds.designtime.generator"; //$NON-NLS-1$

            try {
                performTest(Collections.singletonList("com.example.ordermodel.bom"), //$NON-NLS-1$
                        new LinkedHashMap<String, String>(),
                        "1.0.0.1"); //$NON-NLS-1$
                // Do a test build of the new project
                IProject outputProject =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getProject(".com.example.ordermodel.bds"); //$NON-NLS-1$
                buildProject(outputProject);

                performTest(Collections.singletonList("com.example.gdcomp.bom"), //$NON-NLS-1$
                        new LinkedHashMap<String, String>(),
                        "1.0.0.1"); //$NON-NLS-1$

                // Do a test build of the new project
                outputProject =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getProject(".com.example.gdcomp.bds"); //$NON-NLS-1$
                buildProject(outputProject);
  
            } catch (Exception e) {
            }

            extendionId = oldExtendionId;
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if (buildBdsProjects && isTargetPlatformSet()) {
            IProject outputProject =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(".com.example.ordermodel.bds"); //$NON-NLS-1$
            cleanUp(outputProject);
            outputProject =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(".com.example.gdcomp.bds"); //$NON-NLS-1$
            cleanUp(outputProject);
        }
        buildBdsProjects = defaultBuildBdsProjects;
    }

    
    public void testOrderModel() throws CoreException, IOException,
            GenerationException, DifferException, BOMIllegalStateException {
        performTest(Collections.singletonList("com.example.ordermodel.bom"), //$NON-NLS-1$
                "si/com.example.ordermodel.si.bds-known-good-output.zip",  //$NON-NLS-1$
                ".com.example.ordermodel.si.bds","1.0.0.1"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testGDComp() throws CoreException, IOException,
            GenerationException, DifferException, BOMIllegalStateException {
        performTest(Collections.singletonList("com.example.gdcomp.bom"), //$NON-NLS-1$
                "si/com.example.gdcomp.si.bds-known-good-output.zip", //$NON-NLS-1$
                ".com.example.gdcomp.si.bds","1.0.0.1"); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
