/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderContext;
import org.eclipse.birt.report.engine.api.RenderOptionBase;
import org.eclipse.birt.report.engine.api.ReportEngine;
import org.eclipse.birt.report.model.api.DefaultResourceLocator;
import org.eclipse.birt.report.model.api.ModuleHandle;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.simulation.compare.ComparePlugin;
import com.tibco.xpd.simulation.compare.ComparisonReportEngine;
import com.tibco.xpd.simulation.compare.ReportType;
import com.tibco.xpd.simulation.compare.editor.SimulationReport;
import com.tibco.xpd.simulation.compare.editor.XmlCombiner;

/**
 * A report engine for the BIRT reporting tool.
 * 
 * @author nwilson
 */
public class BirtReportEngine implements ComparisonReportEngine {
    /** The BIRT report engine. */
    private ReportEngine engine;

    /** Container for the HTML rendering options. */
    private HTMLRenderOption htmlOption;

    /** An HTML rendering context. */
    private HTMLRenderContext htmlContext;

    /** Container for the PDF rendering options. */
    private RenderOptionBase pdfOption;

    /** An PDF rendering context. */
    private PDFRenderContext pdfContext;

    /** The rendering task. */
    private IRunAndRenderTask task;

    /** The output folder for the report. */
    private IPath outputFolder;

    /** The file from which to read the report template. */
    private IPath reportFile;

    /** The simualtion results data file. */
    private IPath results;

    /** The path on which to store temporary files. */
    private IPath temp;

    /** The path for temporary image files. */
    private IPath imagesOutput;

    /** The path to the HTML ouput file. */
    private IPath htmlOutput;

    /** The path to the PDF ouput file. */
    private IPath pdfOutput;

    /** Flag to indicate if previously generated images should be deleted. */
    private boolean deleteImages;

    /**
     * Sets up the report engine. This should be called once before calling
     * generate.
     * 
     * @param report
     *            The report to generate.
     */
    @Override
    public void initialise(final SimulationReport report) {
        temp = ComparePlugin.getDefault().getStateLocation().append(".temp"); //$NON-NLS-1$
        imagesOutput = temp.append("images"); //$NON-NLS-1$
        imagesOutput.toFile().mkdirs();
        htmlOutput = temp.append("report.html"); //$NON-NLS-1$
        pdfOutput = temp.append("report.pdf"); //$NON-NLS-1$

        results = temp.append("results.sim"); //$NON-NLS-1$
        outputFolder = temp;
        reportFile = report.getReportFile();
        deleteImages = true;
    }

    /**
     * Sets up the report engine. This should be called once before calling
     * generate.
     * 
     * @param report
     *            The report to generate.
     * @param outputFile
     *            The file to which the report will be saved.
     */
    @Override
    public void initialise(final SimulationReport report,
            final String outputFile) {
        temp = ComparePlugin.getDefault().getStateLocation().append(".temp"); //$NON-NLS-1$
        temp.toFile().mkdirs();
        htmlOutput = new Path(outputFile);
        pdfOutput = htmlOutput;
        outputFolder = htmlOutput.removeLastSegments(1);
        imagesOutput = outputFolder.append("images"); //$NON-NLS-1$
        results = temp.append("results.sim"); //$NON-NLS-1$
        reportFile = report.getReportFile();
        deleteImages = false;
    }

    /**
     * 
     */
    private void startEngine() {
        EngineConfig config = new EngineConfig();

        // set the resource location for it to find the localised properties for
        // birt reports
        config.setResourceLocator(new DefaultResourceLocator() {
            @Override
            public URL findResource(ModuleHandle moduleHandle, String fileName,
                    int type) {
                URL url =
                        ComparePlugin.getDefault().getBundle()
                                .getResource(fileName);
                return url;
            }
        });

        engine = new ReportEngine(config);
        engine.changeLogLevel(Level.OFF);
        htmlOption = new HTMLRenderOption();
        htmlOption.setOutputFileName(htmlOutput.toString());
        htmlContext = new HTMLRenderContext();
        htmlContext.setImageDirectory(imagesOutput.toString());
        htmlContext.setBaseImageURL("images"); //$NON-NLS-1$

        pdfOption = new RenderOptionBase();
        pdfOption.setOutputFileName(pdfOutput.toString());
        pdfOption.setOutputFormat(RenderOptionBase.OUTPUT_FORMAT_PDF);
        pdfContext = new PDFRenderContext();

        try {
            InputStream reportStream;
            try {
                reportStream =
                        FileLocator.openStream(ComparePlugin.getDefault()
                                .getBundle(), reportFile, false);
            } catch (IOException e) {
                reportStream = new FileInputStream(reportFile.toFile());
            }

            IReportRunnable runnable = engine.openReportDesign(reportStream);
            task = engine.createRunAndRenderTask(runnable);
            task.setParameterValue("sourcexml", results.toString()); //$NON-NLS-1$
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EngineException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a report as a file and returns the URL at which the file can be
     * found.
     * 
     * @param inputs
     *            The input files to use.
     * @param outputType
     *            One of the output type constants defined in this interface.
     * @return The URL of the report file.
     */
    @Override
    public String generate(final IResource[] inputs, final int outputType) {
        String url = null;
        if (engine == null) {
            startEngine();
        }
        if (task != null) {
            setOutputType(outputType);
            XmlCombiner.combine(inputs, results, "Experiments"); //$NON-NLS-1$
            if (deleteImages) {
                deleteImages();
            }
            try {
                task.run();
                if (outputType == ReportType.OUTPUT_HTML) {
                    url = htmlOutput.toString();
                } else if (outputType == ReportType.OUTPUT_PDF) {
                    url = pdfOutput.toString();
                }
            } catch (EngineException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    /**
     * Disposes of the report engine and any associated resources.
     */
    @Override
    public void dispose() {
        engine.shutdown();
        engine.destroy();
    }

    /**
     * @param outputType
     *            The type of output file to generate.
     */
    private void setOutputType(final int outputType) {
        if (outputType == ReportType.OUTPUT_HTML) {
            Map<String, HTMLRenderContext> appContext =
                    new HashMap<String, HTMLRenderContext>();
            appContext.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT,
                    htmlContext);
            task.setAppContext(appContext);
            task.setRenderOption(htmlOption);
            imagesOutput.toFile().mkdirs();
        } else if (outputType == ReportType.OUTPUT_PDF) {
            Map<String, PDFRenderContext> appContext =
                    new HashMap<String, PDFRenderContext>();
            appContext.put(EngineConstants.APPCONTEXT_PDF_RENDER_CONTEXT,
                    pdfContext);
            task.setAppContext(appContext);
            task.setRenderOption(pdfOption);
            outputFolder.toFile().mkdirs();
        }
    }

    /**
     * Deletes images from the image folder.
     */
    private void deleteImages() {
        File imageFolder = imagesOutput.toFile();
        if (imageFolder.exists()) {
            String[] files = imageFolder.list();
            for (int i = 0; i < files.length; i++) {
                File file = new File(imageFolder, files[i]);
                file.delete();
            }
        }
    }
}
