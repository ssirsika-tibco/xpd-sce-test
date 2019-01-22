/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.procdoc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.model.api.DefaultResourceLocator;
import org.eclipse.birt.report.model.api.ModuleHandle;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.procdoc.internal.Messages;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.documentation.IDocGenInfo;
import com.tibco.xpd.ui.documentation.IDocGenModelInfo;
import com.tibco.xpd.ui.documentation.engine.AbstractBIRTDocGen;

/**
 * 
 * @author mtorres
 */
public class XPDLDocBIRTGen extends AbstractBIRTDocGen {

    @Override
    protected void generateExtraResources(IFile inputFile,
            IPath outputFilePath, IDocGenInfo docGenInfo) {

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
                        ProcdocPlugin.getDefault().getBundle()
                                .getResource(fileName);
                return url;
            }
        });
        try {
            IPath reportFile = new Path("reports").append( //$NON-NLS-1$
                    "xpdlModelReportFlattened.rptdesign");//$NON-NLS-1$
            defaultTemplateStream =
                    FileLocator.openStream(ProcdocPlugin.getDefault()
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
        return Messages.ProcessModelFolderLabel;
    }

    @Override
    protected List<IDocGenModelInfo> createDocGenModelInfo(IResource resource) {
        return null;
    }

    @Override
    protected void clearOldResources(IFile inputFile, IPath outputFilePath,
            IDocGenInfo docGenInfo) {
        // deleteImages();
    }
}
