/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.tibco.xpd.simulation.compare.ComparePlugin;
import com.tibco.xpd.simulation.compare.ComparisonReportEngine;
import com.tibco.xpd.simulation.compare.editor.SimulationReport;

/**
 * An XSLT report engine.
 * 
 * @author nwilson
 */
public class XsltReportEngine implements ComparisonReportEngine {
    /** The size of the buffer used to read the support file. */
    private static final int FILE_BUFFER_SIZE = 128;

    /** The output folder. */
    private IPath outputFolder;

    /** The XSLT template file. */
    private IPath reportFile;

    /** The path to the HTML output file. */
    private IPath htmlOutput;

    /** The zip file containing HTML support files. */
    private IPath zip;

    /**
     * @param report The report descriptor to use for this engine.
     * @see com.tibco.xpd.simulation.compare.ComparisonReportEngine#initialise(
     *      com.tibco.xpd.simulation.compare.editor.SimulationReport)
     */
    public void initialise(final SimulationReport report) {
        IPath temp =
                ComparePlugin.getDefault().getStateLocation().append(".temp"); //$NON-NLS-1$
        htmlOutput = temp.append("report.html"); //$NON-NLS-1$

        outputFolder = temp;
        reportFile = report.getReportFile();
        zip = report.getSupportFile();
    }

    /**
     * @param report The report descriptor to use for this engine.
     * @param outputFile The path to the ouput file.
     * @see com.tibco.xpd.simulation.compare.ComparisonReportEngine#initialise(
     *      com.tibco.xpd.simulation.compare.editor.SimulationReport,
     *      java.lang.String)
     */
    public void initialise(final SimulationReport report,
            final String outputFile) {
        htmlOutput = new Path(outputFile);
        outputFolder = htmlOutput.removeLastSegments(1);
        reportFile = report.getReportFile();
        zip = report.getSupportFile();
    }

    /**
     * @param inputs The input resources for this report (must only be one).
     * @param outputType The type of report to generate (will be HTML).
     * @return The URL of the HTML report file.
     * @see com.tibco.xpd.simulation.compare.ComparisonReportEngine#generate(
     *      org.eclipse.core.resources.IResource[], int)
     */
    public String generate(final IResource[] inputs, final int outputType) {
        outputFolder.toFile().mkdirs();
        String url = null;
        if (inputs.length == 1) {
            try {
                InputStream reportStream;
                try {
                    reportStream =
                            FileLocator.openStream(ComparePlugin.getDefault()
                                    .getBundle(), reportFile, false);
                } catch (IOException e) {
                    reportStream = new FileInputStream(reportFile.toFile());
                }
                Source xsl = new StreamSource(reportStream);
                Source xml =
                        new StreamSource(new FileInputStream(inputs[0]
                                .getRawLocation().toFile()));
                FileOutputStream outputStream =
                        new FileOutputStream(htmlOutput.toFile());
                Result html = new StreamResult(outputStream);
                TransformerFactory factory = TransformerFactory.newInstance();
                try {
                    Transformer transformer = factory.newTransformer(xsl);
                    transformer.transform(xml, html);
                } catch (TransformerConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                outputStream.flush();
                outputStream.close();
                if (zip != null) {
                    addHtmlFiles(outputFolder);
                }
                url = htmlOutput.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    /**
     * @see com.tibco.xpd.simulation.compare.ComparisonReportEngine#dispose()
     */
    public void dispose() {
    }

    /**
     * Unzips the contents of the support file to the output folder.
     * 
     * @param folder The output folder.
     * @throws IOException If there was a problem writing the files.
     */
    private void addHtmlFiles(final IPath folder) throws IOException {
        InputStream inputStream;
        try {
            inputStream =
                    FileLocator.openStream(ComparePlugin.getDefault()
                            .getBundle(), zip, false);
        } catch (IOException e) {
            inputStream = new FileInputStream(zip.toFile());
        }
        ZipInputStream input = new ZipInputStream(inputStream);
        ZipEntry entry;
        while ((entry = input.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                File file = new File(folder.toFile(), entry.getName());
                ensureFolderExists(file.getParentFile());
                FileOutputStream output = new FileOutputStream(file);
                byte[] buffer = new byte[FILE_BUFFER_SIZE];
                int bytesRead = 0;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                output.close();
            }
        }
        input.close();
    }

    /**
     * @param parentFile The parent folder.
     */
    private void ensureFolderExists(final File parentFile) {
        if (parentFile != null && !parentFile.exists()) {
            ensureFolderExists(parentFile.getParentFile());
            parentFile.mkdir();
        }
    }
}
