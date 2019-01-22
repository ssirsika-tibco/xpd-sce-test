/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bds.designtime.generator.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.bds.designtime.generator.Context;
import com.tibco.bds.designtime.generator.ModelGenerator;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2ExtensionHelper;

/**
 * This class is a test tool to enable the creation of a BDS package for a
 * single XSD, it is not designed to be automated, just update the name of your
 * XSD file to it to convert and it will do the transformation for you using the
 * BDS code
 * 
 */
public class BDSTestTools extends BaseTest {

    private String bundleName = "UnionUsage"; //$NON-NLS-1$

    private String bundleVersion = "1.0"; //$NON-NLS-1$

    private String xsdFullFileName = "C:\\Temp\\UnionUsage.xsd"; //$NON-NLS-1$

    public void testToolGenerateBDSFromXSD() {

        try {
            // Create an instance of the generator that we can use
            ModelGenerator bg = new ModelGenerator();
            bg.setExtension(BOMGenerator2ExtensionHelper.getInstance()
                    .getExtension("com.tibco.bds.designtime.generator")); //$NON-NLS-1$

            IProject newProject = createEMFProject("." + bundleName); //$NON-NLS-1$
            IFolder modelFolder = (IFolder) newProject.findMember("model"); //$NON-NLS-1$

            File targetXsd =
                    modelFolder.getLocation().append(bundleName + ".xsd") //$NON-NLS-1$
                            .toFile();

            // Copy the XSD into the folder
            FileInputStream in = new FileInputStream(xsdFullFileName);
            FileOutputStream out = new FileOutputStream(targetXsd);

            copyStreamToStream(in, out);

            // Refresh the project
            newProject.refreshLocal(IResource.DEPTH_INFINITE, null);

            Context ctx = new Context();
            ctx.setOutputProject(newProject);
            ctx.setBundleName(bundleName);
            ctx.setBundleVersion(bundleVersion);

            bg.generateModelProjectFromXSDs(ctx, findXSDsInFolder(modelFolder));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
