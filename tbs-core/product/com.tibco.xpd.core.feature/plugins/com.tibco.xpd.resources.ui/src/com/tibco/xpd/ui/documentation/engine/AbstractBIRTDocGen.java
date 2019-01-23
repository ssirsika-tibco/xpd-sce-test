/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.documentation.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.ReportEngine;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.documentation.IDocGenInfo;

/**
 * This class is used for the generation of BIRT specific engine documentation
 * for the passed input {@link IResource}
 * 
 * The documentation is generated in the outputPath
 * 
 * @author mtorres
 */
public abstract class AbstractBIRTDocGen extends AbstractBaseDocGen {

    /** The BIRT report engine. */
    private ReportEngine engine;

    /** Container for the HTML rendering options. */
    private HTMLRenderOption htmlOption;

    /** The rendering task. */
    private IRunAndRenderTask task;

    /** The output folder for the report. */
    private IPath outputFolder;

    /** The file from which to read the report template. */
    private IPath reportFile;

    /** The path for temporary image files. */
    private IPath imagesOutput;

    /** The path to the HTML ouput file. */
    private IPath htmlOutput;

    private boolean useDefaultTemplate = true;

    private IDocGenInfo currentDocGenInfo = null;

    /**
     * Starts the BIRT transformation
     * 
     * @param docGenInfo
     */
    protected void startEngine(IDocGenInfo docGenInfo) {
        if (docGenInfo != null && docGenInfo.getSource() != null) {
            EngineConfig config = new EngineConfig();
            IResource resource = docGenInfo.getSource();
            engine = new ReportEngine(config);
            engine.changeLogLevel(Level.OFF);

            htmlOption = new HTMLRenderOption();
            htmlOption.setOutputFileName(htmlOutput.toString());
            htmlOption.setHtmlPagination(true);
            htmlOption.setImageHandler(new HTMLServerImageHandler());
            htmlOption.setImageDirectory(imagesOutput.toString());
            htmlOption.setBaseImageURL("images"); //$NON-NLS-1$

            InputStream reportStream = null;

            if (useDefaultTemplate) {
                reportStream = getDefaultTemplateStream(config, docGenInfo);
            } else {
                try {
                    reportStream = new FileInputStream(reportFile.toFile());
                } catch (FileNotFoundException e) {
                    XpdResourcesUIActivator.getDefault().getLogger().error(e);
                }
            }

            if (reportStream != null) {
                try {

                    IReportRunnable runnable =
                            engine.openReportDesign(reportStream);
                    task = engine.createRunAndRenderTask(runnable);

                    task.setParameterValue("sourcexml", resource.getLocation() //$NON-NLS-1$
                            .toOSString());
                    task.setParameterValue("imageFolderPath", imagesOutput //$NON-NLS-1$
                            .addTrailingSeparator().toOSString());
                    task.setRenderOption(htmlOption);

                } catch (EngineException e) {
                    XpdResourcesUIActivator.getDefault().getLogger().error(e);
                }
            } else {
                XpdResourcesUIActivator.getDefault().getLogger()
                        .error("No report stream found");
            }
        }
    }

    /**
     * Disposes of the report engine and any associated resources.
     */
    @Override
    protected void disposeEngine(IDocGenInfo docGenInfo) {
        if (engine != null) {
            engine.shutdown();
            engine.destroy();
        }
    }

    /**
     * Sets the report file path 
     * 
     * @param templatePath
     */
    private void setReportFilePath(IPath templatePath) {
        if (templatePath != null) {
            reportFile = templatePath;
            useDefaultTemplate = false;
        } else {
            useDefaultTemplate = true;
        }
    }

    /**
     * Opportunity to generate extra resources that the document
     * generation might need 
     * 
     * @param inputFile
     * @param outputFilePath
     * @param docGenInfo
     */
    protected abstract void generateExtraResources(IFile inputFile,
            IPath outputFilePath, IDocGenInfo docGenInfo);
    
    /**
     * Opportunity to clear old resources that the document
     * generation might have 
     * 
     * @param inputFile
     * @param outputFilePath
     * @param docGenInfo
     */
    protected abstract void clearOldResources(IFile inputFile,
            IPath outputFilePath, IDocGenInfo docGenInfo);

    /**
     * Returns the default template Stream that will be used
     * by the BIRT engine to generate the document
     * 
     * @param config
     * @param docGenInfo
     * @return
     */
    protected abstract InputStream getDefaultTemplateStream(
            EngineConfig config, IDocGenInfo docGenInfo);

    /**
     * Runs the engine
     * 
     * @param docGenInfo
     */
    @Override
    protected void runEngine(IDocGenInfo docGenInfo) {
        if (docGenInfo != null && docGenInfo.getSource() != null
                && docGenInfo.getGeneratorOutputPath() != null) {
            currentDocGenInfo = docGenInfo;
            IPath generatorOutputPath = docGenInfo.getGeneratorOutputPath();
            IResource source = docGenInfo.getSource();
            if (source instanceof IFile) {
                IFile modelFile = (IFile) source;
                IFolder folder = null;
                IPath outputReportFile = null;
                if (generatorOutputPath.getDevice() == null) {
                    folder =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .getFolder(generatorOutputPath);
                }
                if (folder == null
                        || (folder != null && folder.getLocationURI() == null)) {
                    outputReportFile =
                            generatorOutputPath
                                    .append(modelFile
                                            .getName()
                                            .replace("." + modelFile.getFileExtension(), //$NON-NLS-1$
                                                    ".html")); //$NON-NLS-1$
                } else {
                    outputReportFile =
                            modelFile
                                    .getWorkspace()
                                    .getRoot()
                                    .getLocation()
                                    .append(generatorOutputPath)
                                    .append(modelFile
                                            .getName()
                                            .replace("." + modelFile.getFileExtension(), //$NON-NLS-1$
                                                    ".html")); //$NON-NLS-1$
                }
                if (docGenInfo.getGenerationStatus() != null
                        && docGenInfo.getGenerationStatus().isOK()) {
                    htmlOutput = outputReportFile;
                    outputFolder = htmlOutput.removeLastSegments(1);
                    imagesOutput = outputFolder.append("images"); //$NON-NLS-1$

                    setReportFilePath(null);
                    
                    clearOldResources(modelFile, outputReportFile, docGenInfo);
                    generateExtraResources(modelFile, outputReportFile, docGenInfo);
                    
                    startEngine(docGenInfo);

                    if (task != null) {
                        try {
                            task.run();
                        } catch (EngineException e) {
                            XpdResourcesUIActivator.getDefault().getLogger().error(e);
                        }
                    }
                }
            }
        }
    }
    
    protected IPath getImagesOutput() {
        return imagesOutput;
    }
    
    /**
     * Deletes images from the image folder.
     */
    protected void deleteImages() {
        if (getImagesOutput() != null) {
            File imageFolder = getImagesOutput().toFile();
            if (imageFolder.exists()) {
                File[] files = imageFolder.listFiles();
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    if (file.exists()) {
                        file.delete();
                    }
                }
                IContainer folder =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getContainerForLocation(getImagesOutput());
                if (folder != null && folder.exists()) {
                    try {
                        folder.refreshLocal(IResource.DEPTH_INFINITE, null);
                    } catch (CoreException e) {
                        XpdResourcesUIActivator.getDefault().getLogger()
                                .error(e);
                    }
                }
            }
        }
    }
    
    public IPath getHtmlOutput() {
        return htmlOutput;
    }
    
    public IDocGenInfo getCurrentDocGenInfo() {
        return currentDocGenInfo;
    }

}
