/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.documentation.index;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.model.api.DefaultResourceLocator;
import org.eclipse.birt.report.model.api.ModuleHandle;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.documentation.IDocGenInfo;
import com.tibco.xpd.ui.documentation.IDocGenModelInfo;
import com.tibco.xpd.ui.documentation.engine.AbstractBIRTDocGen;

/**
 * This class is responsible for the generation of the index.html file using
 * BIRT templates
 * 
 * @author mtorres
 */
public class IndexDocGen extends AbstractBIRTDocGen {

    @Override
    protected void generateExtraResources(IFile inputFile,
            IPath outputFilePath, IDocGenInfo docGenInfo) {
        IndexResourceCopier resourceCopier = new IndexResourceCopier();
        try {
            resourceCopier.perform(new SubProgressMonitor(
                    new NullProgressMonitor(), 30), inputFile, outputFilePath
                    .toFile());
        } catch (CoreException e) {
            XpdResourcesUIActivator.getDefault().getLogger().error(e);
        }
    }

    @Override
    protected InputStream getDefaultTemplateStream(EngineConfig config,
            IDocGenInfo docGenInfo) {
        InputStream defaultTemplateStream = null;
        // set the resource location for it to find the localised properties
        // for birt reports
        config.setResourceLocator(new DefaultResourceLocator() {
            @Override
            public URL findResource(ModuleHandle moduleHandle, String fileName,
                    int type) {
                URL url =
                        XpdResourcesUIActivator.getDefault().getBundle()
                                .getResource(fileName);
                return url;
            }

            /**
             * @see org.eclipse.birt.report.model.api.DefaultResourceLocator#findResource(org.eclipse.birt.report.model.api.ModuleHandle,
             *      java.lang.String, int, java.util.Map)
             * 
             * @param moduleHandle
             * @param fileName
             * @param type
             * @param appContext
             * @return
             */
            @Override
            public URL findResource(ModuleHandle moduleHandle, String fileName,
                    int type, Map appContext) {
                URL url =
                        XpdResourcesUIActivator.getDefault().getBundle()
                                .getResource(fileName);
                return url;
            }

        });
        try {
            IPath reportFile = new Path("resources").append( //$NON-NLS-1$
                    "indexReportFlattened.rptdesign");//$NON-NLS-1$
            defaultTemplateStream =
                    FileLocator.openStream(XpdResourcesUIActivator.getDefault()
                            .getBundle(), reportFile, false);
        } catch (IOException e) {
            XpdResourcesUIActivator.getDefault().getLogger().equals(e);
            if (docGenInfo != null && docGenInfo.getGenerationStatus() != null) {
                docGenInfo.setGenerationStatus(Status.CANCEL_STATUS);
            }
        }
        return defaultTemplateStream;
    }

    @Override
    protected String getGeneratorRootFolderName() {
        return "";//$NON-NLS-1$
    }

    @Override
    protected List<IDocGenModelInfo> createDocGenModelInfo(IResource resource) {
        // Do nothing
        return Collections.emptyList();
    }

    @Override
    protected void clearOldResources(IFile inputFile, IPath outputFilePath,
            IDocGenInfo docGenInfo) {
        deleteImages();
    }

}
