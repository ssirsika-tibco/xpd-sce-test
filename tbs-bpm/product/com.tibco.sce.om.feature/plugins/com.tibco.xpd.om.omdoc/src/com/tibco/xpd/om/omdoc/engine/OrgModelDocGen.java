/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.om.omdoc.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.eclipse.birt.report.engine.api.DOCRenderContext;
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

import com.tibco.xpd.om.omdoc.Activator;

/**
 * 
 * @author glewis
 * 
 * @deprecated see {@link OMDocGen}
 */
public class OrgModelDocGen implements DocGen {
    /** The BIRT report engine. */
    private ReportEngine engine;

    /** Container for the DOC rendering options. */
    private HTMLRenderOption docOption;

    /** A DOC rendering context. */
    private DOCRenderContext docContext;

    /** Container for the HTML rendering options. */
    private HTMLRenderOption htmlOption;

    /** An HTML rendering context. */
    private HTMLRenderContext htmlContext;

    /** Container for the PDF rendering options. */
    private RenderOptionBase pdfOption;

    /** An PDF rendering context. */
    private PDFRenderContext pdfContext;

    /** Container for the POSTSCRIPT rendering options. */
    private HTMLRenderOption postscriptOption;

    /** A POSTSCRIPT rendering context. */
    private HTMLRenderContext postscriptContext;

    /** Container for the PPT rendering options. */
    private HTMLRenderOption pptOption;

    /** A PPT rendering context. */
    private HTMLRenderContext pptContext;

    /** Container for the XLS rendering options. */
    private HTMLRenderOption xlsOption;

    /** A XLS rendering context. */
    private HTMLRenderContext xlsContext;

    /** The rendering task. */
    private IRunAndRenderTask task;

    /** The output folder for the report. */
    private IPath outputFolder;

    /** The file from which to read the report template. */
    private IPath reportFile;

    /** The path on which to store temporary files. */
    private IPath temp;

    /** The path for temporary image files. */
    private IPath imagesOutput;

    /** The path to the DOC ouput file. */
    private IPath docOutput;

    /** The path to the HTML ouput file. */
    private IPath htmlOutput;

    /** The path to the PDF ouput file. */
    private IPath pdfOutput;

    /** The path to the POSTSCRIPT ouput file. */
    private IPath postscriptOutput;

    /** The path to the PPT ouput file. */
    private IPath pptOutput;

    /** The path to the XLS ouput file. */
    private IPath xlsOutput;

    /** Flag to indicate if previously generated images should be deleted. */
    private boolean deleteImages;

    /**
     * Sets up the report engine. This should be called once before calling
     * generate.
     * 
     * @param report
     *            The report to generate.
     */
    public void initialise(final String strReportFile) {
        temp = Activator.getDefault().getStateLocation().append(".temp"); //$NON-NLS-1$
        imagesOutput = temp.append("images"); //$NON-NLS-1$
        imagesOutput.toFile().mkdirs();
        docOutput = temp.append("report.doc"); //$NON-NLS-1$
        htmlOutput = temp.append("report.html"); //$NON-NLS-1$
        pdfOutput = temp.append("report.pdf"); //$NON-NLS-1$        
        postscriptOutput = temp.append("report.postscript"); //$NON-NLS-1$
        pptOutput = temp.append("report.ppt"); //$NON-NLS-1$
        xlsOutput = temp.append("report.xls"); //$NON-NLS-1$
        outputFolder = temp;
        if (strReportFile != null) {
            reportFile = new Path(strReportFile);
        }
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
    public void initialise(final String strReportFile, final String outputFile) {
        temp = Activator.getDefault().getStateLocation().append(".temp"); //$NON-NLS-1$
        temp.toFile().mkdirs();
        htmlOutput = new Path(outputFile);
        pdfOutput = htmlOutput;
        docOutput = htmlOutput;
        postscriptOutput = htmlOutput;
        pptOutput = htmlOutput;
        xlsOutput = htmlOutput;
        outputFolder = htmlOutput.removeLastSegments(1);
        imagesOutput = outputFolder.append("images"); //$NON-NLS-1$
        if (strReportFile != null) {
            reportFile = new Path(strReportFile);
        }
        deleteImages = false;
    }

    /**
     * 
     */
    private void startEngine(IResource resource) {
        EngineConfig config = new EngineConfig();

        // set the resource location for it to find the localised properties for
        // birt reports
        config.setResourceLocator(new DefaultResourceLocator() {
            @Override
            public URL findResource(ModuleHandle moduleHandle, String fileName,
                    int type) {
                URL url = Activator.getDefault().getBundle().getResource(
                        fileName);
                return url;
            }
        });

        engine = new ReportEngine(config);
        engine.changeLogLevel(Level.OFF);

        docOption = new HTMLRenderOption();
        docOption.setOutputFileName(docOutput.toString());
        docOption.setHtmlPagination(true);
        docOption.setImageDirectory(imagesOutput.toString());
        docOption.setBaseImageURL("images"); //$NON-NLS-1$
        docContext = new DOCRenderContext();

        htmlOption = new HTMLRenderOption();
        htmlOption.setOutputFileName(htmlOutput.toString());
        htmlOption.setHtmlPagination(true);
        htmlContext = new HTMLRenderContext();
        htmlContext.setImageDirectory(imagesOutput.toString());
        htmlContext.setBaseImageURL("images"); //$NON-NLS-1$

        pdfOption = new RenderOptionBase();
        pdfOption.setOutputFileName(pdfOutput.toString());
        pdfOption.setOutputFormat(RenderOptionBase.OUTPUT_FORMAT_PDF);
        pdfOption.setSupportedImageFormats("PNG;GIF;JPG;BMP"); //$NON-NLS-1$
        pdfOption.setBaseURL("images"); //$NON-NLS-1$
        pdfContext = new PDFRenderContext();

        postscriptOption = new HTMLRenderOption();
        postscriptOption.setOutputFileName(postscriptOutput.toString());
        postscriptOption.setHtmlPagination(true);
        postscriptOption.setImageDirectory(imagesOutput.toString());
        postscriptOption.setBaseImageURL("images"); //$NON-NLS-1$
        postscriptContext = new HTMLRenderContext();

        pptOption = new HTMLRenderOption();
        pptOption.setOutputFileName(pptOutput.toString());
        pptOption.setHtmlPagination(true);
        pptOption.setImageDirectory(imagesOutput.toString());
        pptOption.setBaseImageURL("images"); //$NON-NLS-1$
        pptContext = new HTMLRenderContext();

        xlsOption = new HTMLRenderOption();
        xlsOption.setOutputFileName(xlsOutput.toString());
        xlsOption.setHtmlPagination(true);
        xlsOption.setImageDirectory(imagesOutput.toString());
        xlsOption.setBaseImageURL("images"); //$NON-NLS-1$
        xlsContext = new HTMLRenderContext();

        try {
            InputStream reportStream;
            try {
                reportStream = FileLocator.openStream(Activator.getDefault()
                        .getBundle(), reportFile, false);
            } catch (IOException e) {
                reportStream = new FileInputStream(reportFile.toFile());
            }

            IReportRunnable runnable = engine.openReportDesign(reportStream);
            task = engine.createRunAndRenderTask(runnable);

            task.setParameterValue("sourcexml", resource.getLocation() //$NON-NLS-1$
                    .toOSString());
            task.setParameterValue("imageFolderPath", imagesOutput //$NON-NLS-1$
                    .addTrailingSeparator().toOSString());

            /*
             * task .setParameterValue( "sourcexml",
             * "C:\\Data\\runtime-New_configuration(3)\\daaP\\Organizational Model\\OrganizationModel.om"
             * );
             */
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
    public String generate(final IResource[] inputs, final int outputType) {
        String url = null;
        if (engine == null) {
            startEngine(inputs[0]);
        }
        if (task != null) {
            setOutputType(outputType);
            if (deleteImages) {
                deleteImages();
            }
            try {
                task.run();
                if (outputType == DocGenType.OUTPUT_DOC) {
                    url = docOutput.toString();
                } else if (outputType == DocGenType.OUTPUT_HTML) {
                    url = htmlOutput.toString();
                } else if (outputType == DocGenType.OUTPUT_PDF) {
                    url = pdfOutput.toString();
                } else if (outputType == DocGenType.OUTPUT_POSTSCRIPT) {
                    url = postscriptOutput.toString();
                } else if (outputType == DocGenType.OUTPUT_PPT) {
                    url = pptOutput.toString();
                } else if (outputType == DocGenType.OUTPUT_XLS) {
                    url = xlsOutput.toString();
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
    public void dispose() {
        engine.shutdown();
        engine.destroy();
    }

    /**
     * @param outputType
     *            The type of output file to generate.
     */
    private void setOutputType(final int outputType) {
        if (outputType == DocGenType.OUTPUT_DOC) {
            Map<String, DOCRenderContext> appContext = new HashMap<String, DOCRenderContext>();
            appContext.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT,
                    docContext);
            task.setAppContext(appContext);
            task.setRenderOption(docOption);
            imagesOutput.toFile().mkdirs();
        } else if (outputType == DocGenType.OUTPUT_HTML) {
            Map<String, HTMLRenderContext> appContext = new HashMap<String, HTMLRenderContext>();
            appContext.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT,
                    htmlContext);
            task.setAppContext(appContext);
            task.setRenderOption(htmlOption);
            imagesOutput.toFile().mkdirs();
        } else if (outputType == DocGenType.OUTPUT_PDF) {
            Map<String, PDFRenderContext> appContext = new HashMap<String, PDFRenderContext>();
            appContext.put(EngineConstants.APPCONTEXT_PDF_RENDER_CONTEXT,
                    pdfContext);
            task.setAppContext(appContext);
            task.setRenderOption(pdfOption);
            outputFolder.toFile().mkdirs();
        } else if (outputType == DocGenType.OUTPUT_POSTSCRIPT) {
            Map<String, HTMLRenderContext> appContext = new HashMap<String, HTMLRenderContext>();
            appContext.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT,
                    postscriptContext);
            task.setAppContext(appContext);
            task.setRenderOption(postscriptOption);
            imagesOutput.toFile().mkdirs();
        } else if (outputType == DocGenType.OUTPUT_PPT) {
            Map<String, HTMLRenderContext> appContext = new HashMap<String, HTMLRenderContext>();
            appContext.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT,
                    pptContext);
            task.setAppContext(appContext);
            task.setRenderOption(pptOption);
            imagesOutput.toFile().mkdirs();
        } else if (outputType == DocGenType.OUTPUT_XLS) {
            Map<String, HTMLRenderContext> appContext = new HashMap<String, HTMLRenderContext>();
            appContext.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT,
                    xlsContext);
            task.setAppContext(appContext);
            task.setRenderOption(xlsOption);
            imagesOutput.toFile().mkdirs();
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
