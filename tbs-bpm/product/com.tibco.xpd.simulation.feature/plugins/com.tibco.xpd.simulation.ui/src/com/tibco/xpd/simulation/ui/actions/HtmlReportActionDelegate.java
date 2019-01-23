package com.tibco.xpd.simulation.ui.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.simulation.report.DocumentRoot;
import com.tibco.simulation.report.SimRepExperiment;
import com.tibco.simulation.report.SimRepExperimentData;
import com.tibco.simulation.report.SimRepFactory;
import com.tibco.xpd.simulation.launch.ExperimentParamConstants;
import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.launch.ReportManager;
import com.tibco.xpd.simulation.launch.SimulationControler;
import com.tibco.xpd.simulation.ui.views.SimulationReportView;

public class HtmlReportActionDelegate implements IViewActionDelegate {

    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    private static final String OTHER_FORMAT = "#######0.0000"; //$NON-NLS-1$

    private static final DecimalFormat otherDF =
            getCurrentLocaleDecimalFormat(OTHER_FORMAT);

    private IWorkbenchPage page;

    public void init(IViewPart view) {
        page = view.getSite().getPage();
    }

    /**
     * Returns Decimal Formatter in current Locale
     * 
     * @param format
     * @return
     */
    public static DecimalFormat getCurrentLocaleDecimalFormat(String format) {
        DecimalFormat f =
                (DecimalFormat) DecimalFormat.getInstance(DEFAULT_LOCALE);
        f.applyPattern(format);
        return f;
    }

    public void run(IAction action) {
        SimulationControler controler = LaunchPlugin.getSimulationControler();
        IContainer simFolder = controler.getSimulationFolder();
        ReportManager reportManager = controler.getReportManager();
        if (reportManager != null) {
            SimRepExperiment experiment = reportManager.getSimRepExperiment();

            localiseExperimentData(experiment);

            DocumentRoot root = SimRepFactory.eINSTANCE.createDocumentRoot();
            root.setExperiment(experiment);
            String packageName = experiment.getPackageName();
            String processName = experiment.getProcessName();
            SimpleDateFormat dateFormat =
                    new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss"); //$NON-NLS-1$
            String timeStamp = dateFormat.format(new Date());
            String resultsFolderName =
                    packageName
                            + "_" + processName + "_" + timeStamp + "_report"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            File folder =
                    simFolder.getLocation().append(resultsFolderName).toFile();
            folder.mkdir();
            try {
                if (folder.exists()) {
                    File file = new File(folder, "temp.xml"); //$NON-NLS-1$
                    file.createNewFile();
                    URI uri = URI.createFileURI(file.getPath());
                    ResourceSet resourceSet = new ResourceSetImpl();
                    Resource resource = resourceSet.createResource(uri);
                    resource.getContents().add(root);
                    resource.save(Collections.EMPTY_MAP);

                    // transform
                    Source xsl =
                            new StreamSource(
                                    getClass()
                                            .getResourceAsStream("SimulationReportHtml.xslt")); //$NON-NLS-1$
                    Source xml = new StreamSource(file);
                    String outputFileName =
                            packageName + "_" + processName + "_report.html"; //$NON-NLS-1$ //$NON-NLS-2$
                    File outputFile = new File(folder, outputFileName);
                    FileOutputStream outputStream =
                            new FileOutputStream(outputFile);
                    Result html = new StreamResult(outputStream);
                    TransformerFactory factory =
                            TransformerFactory.newInstance();
                    try {
                        Transformer transformer = factory.newTransformer(xsl);
                        transformer.transform(xml, html);
                    } catch (TransformerConfigurationException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    }
                    outputStream.close();
                    file.delete();
                    addHtmlFiles(folder);
                    simFolder.refreshLocal(IResource.DEPTH_INFINITE, null);

                    SimulationReportView reportView =
                            (SimulationReportView) page
                                    .showView("com.tibco.xpd.simulation.ui.views.SimulationReportView"); //$NON-NLS-1$
                    reportView.setInput(outputFile.toURI().toString()); //$NON-NLS-1$
                } else {
                    throw new IOException(
                            "Could not create folder " + folder.getAbsolutePath()); //$NON-NLS-1$
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    private void addHtmlFiles(File folder) throws IOException {
        InputStream inputStream =
                getClass().getResourceAsStream("html_files.zip"); //$NON-NLS-1$
        ZipInputStream input = new ZipInputStream(inputStream);
        ZipEntry entry;
        while ((entry = input.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                File file = new File(folder, entry.getName());
                ensureFolderExists(file.getParentFile());
                FileOutputStream output = new FileOutputStream(file);
                byte[] buffer = new byte[128];
                int bytesRead = 0;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                output.close();
            }
        }
        input.close();
    }

    private void ensureFolderExists(File parentFile) {
        if (parentFile != null && !parentFile.exists()) {
            ensureFolderExists(parentFile.getParentFile());
            parentFile.mkdir();
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }

    /**
     * @param experiment
     */
    public void localiseExperimentData(SimRepExperiment experiment) {
        SimRepExperimentData data = experiment.getExperimentData();

        String refStartTime = (String) data.getReferenceStartTime();
        Date date = null;
        try {
            date =
                    ExperimentParamConstants.simpleDateFormat
                            .parse(refStartTime);
            SimpleDateFormat format =
                    new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS");
            data.setReferenceStartTime(format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String simTime = otherDF.format(data.getSimulationTime());
        simTime =
                simTime.replace(otherDF.getDecimalFormatSymbols()
                        .getDecimalSeparator(), '.'); //$NON-NLS-1$
        data.setSimulationTime(Double.valueOf(simTime).doubleValue());
    }

}
