/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.procdoc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.TransformerConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.xml.sax.SAXException;

import com.tibco.xpd.procdoc.SvgProcessImageCreator.SvgParam;
import com.tibco.xpd.procdoc.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.Xpdl2ProcessWidgetAdapterFactory;
import com.tibco.xpd.processwidget.HadToShrinkImageException;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidget.ObjectPositionInfo;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.importexport.exportwizard.ExportSubTask;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.XmlUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Used by the procedure documentation export wizard to create images of
 * processes in the selected package.
 * 
 * @author njpatel
 * 
 */
public class ImageCreator implements ExportSubTask {

    public static final String IMAGES_FOLDER = "images"; //$NON-NLS-1$

    public static final String SVG_SUPPORT_FOLDER = "svgSupport"; //$NON-NLS-1$

    public static final String SVG_SUPPORT_ICONS_FOLDER = "svgSupport/icons"; //$NON-NLS-1$

    private ExportWizard parent = null;

    private boolean wantSVG = false;

    private Map<String, IPath> processImagesMap;

    /**
     * Create images for each process in a given package
     * 
     * @param parent
     */
    public ImageCreator(ExportWizard parent) {
        this.parent = parent;
        processImagesMap = new HashMap<String, IPath>();
    }

    public ImageCreator(ExportWizard parent, boolean wantSVG) {
        this.parent = parent;
        this.wantSVG = wantSVG;
        processImagesMap = new HashMap<String, IPath>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.importexport.export.wizards.SubTask#perform(org.eclipse
     * .core.runtime.SubProgressMonitor, org.eclipse.core.resources.IFile,
     * java.io.File)
     */
    @Override
    public void perform(final SubProgressMonitor monitor,
            final IFile inputFile, final File outputFile) throws CoreException {

        final Exception[] wrappedException = new Exception[] { null };

        if (inputFile != null && outputFile != null) {
            final WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(inputFile);

            if (wc != null) {
                final Package pkg = (Package) wc.getRootElement();
                final Display display;
                if (parent == null) {
                    display = XpdResourcesPlugin.getStandardDisplay();
                } else {
                    display = parent.getShell().getDisplay();
                }

                // Continue if there are processes in the package
                if (pkg != null && pkg.getProcesses() != null
                        && !pkg.getProcesses().isEmpty()) {

                    if (monitor != null) {
                        monitor.beginTask("", //$NON-NLS-1$
                                pkg.getProcesses().size());
                    }

                    // Used to do all processes in one single
                    // display.synchExec() but this locks up the UI and doesn't
                    // allow the progress monitor to 'tick'

                    try {
                        final IPath imgDestPath =
                                createImageDestinationPath(outputFile);

                        for (final Process process : pkg.getProcesses()) {
                            if (!display.isDisposed()) {
                                if (monitor != null) {
                                    if (monitor.isCanceled()) {
                                        throw new OperationCanceledException();
                                    }

                                    String name =
                                            Xpdl2ModelUtil
                                                    .getDisplayName(process);
                                    if (name == null || name.length() == 0) {
                                        name = process.getName();
                                    }
                                    monitor.subTask(inputFile.getName()
                                            + ": " //$NON-NLS-1$
                                            + Messages.ImageCreator_GeneratingProcessImagesMonitorMsg
                                            + " - " + name); //$NON-NLS-1$
                                }

                                display.syncExec(new Runnable() {

                                    @Override
                                    public void run() {
                                        if (!display.isDisposed()) {
                                            Shell shell = new Shell(display);

                                            createProcessImage(shell,
                                                    wc.getEditingDomain(),
                                                    process,
                                                    imgDestPath);

                                            shell.dispose();
                                            shell = null;
                                        }
                                    }
                                });

                                if (monitor != null) {
                                    monitor.worked(1);
                                }
                            }
                        }

                    } catch (CoreException e) {
                        wrappedException[0] = e;
                    } catch (Exception e) {
                        wrappedException[0] = e;
                    }

                }
            }
        }

        if (monitor != null) {
            // Just in case of exceptions etc.
            monitor.done();
        }

        /*
         * If exception was raised then wrap it in CoreException and throw it
         */
        if (wrappedException[0] != null) {
            if (wrappedException[0] instanceof CoreException) {
                throw (CoreException) wrappedException[0];
            } else {
                throw new CoreException(new Status(IStatus.ERROR,
                        ProcdocPlugin.PLUGIN_ID, IStatus.OK,
                        wrappedException[0].getLocalizedMessage(),
                        wrappedException[0]));
            }
        }

        return;
    }

    /**
     * Generate the process image and save to given destination path.
     * 
     * @param shell
     *            <code>Shell</code>
     * @param editingDomain
     *            editing domain
     * @param process
     *            to generate images of
     * @param imageDestinationPath
     *            destination path
     */
    protected void createProcessImage(Shell shell, EditingDomain editingDomain,
            Process process, IPath imageDestinationPath) {

        if (shell != null && !shell.isDisposed() && editingDomain != null) {
            ProcessWidget pWidget =
                    new ProcessWidgetImpl(false,
                            Xpdl2ProcessEditorPlugin
                                    .calculateProcessWidgetType(process));

            pWidget.setEditingDomain(editingDomain);
            pWidget.setAdapterFactory(new Xpdl2ProcessWidgetAdapterFactory());
            pWidget.setPreferences(ProcdocPlugin.getDefault()
                    .getPluginPreferences());

            /* Input must now be set before control create. */
            pWidget.setInput(process);
            pWidget.createControl(shell);

            try {
                createImageFiles(pWidget,
                        process.getPackage(),
                        process,
                        imageDestinationPath);
            } catch (IOException e) {
                ProcdocPlugin
                        .getDefault()
                        .getLogger()
                        .warn(e,
                                String.format("Failed to create image/map for package '%1$s' process '%2$s'.", //$NON-NLS-1$
                                        process.getPackage().getName(),
                                        process.getName()));
            }

            pWidget.getControl().dispose();
            pWidget = null;
        }

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.export.wizards.SubTask#dispose()
     */
    @Override
    public void dispose() {
        // Nothing to dispose
    }

    /**
     * Create images of a process and save to a file.
     * 
     * @param monitor
     *            progress monitor
     * @param processImages
     *            List of process images that contain package/process
     *            information and the ProcessWidget
     * @param destPath
     *            destination path
     * @throws IOException
     */
    private void createImageFiles(ProcessWidget pWidget, Package pkg,
            Process process, IPath destPath) throws IOException {

        if (pWidget != null && pkg != null && process != null
                && destPath != null) {
            if (wantSVG) {
                createSVGImage(process, destPath);

            } else {
                createRasterImage(pWidget, pkg, process, destPath);
            }
        }

        return;
    }

    /**
     * @param process
     * @param destPath
     */
    private void createSVGImage(Process process, IPath destPath) {
        String fileName =
                createImageFileName(process.getPackage(), process) + ".svg"; //$NON-NLS-1$

        IPath imagePath = destPath.append(fileName);

        File outFile = imagePath.toFile();

        // xslt params can be added here.
        Map<SvgParam, String> svgParams = new HashMap<SvgParam, String>();

        svgParams.put(SvgParam.javaScriptFile, SVG_SUPPORT_FOLDER
                + "/" + "xpdl2svg.js"); //$NON-NLS-1$ //$NON-NLS-2$);
        svgParams.put(SvgParam.cssFile, SVG_SUPPORT_FOLDER
                + "/" + "xpdl2svg.css"); //$NON-NLS-1$ //$NON-NLS-2$);
        svgParams.put(SvgParam.iconsFolder, SVG_SUPPORT_ICONS_FOLDER);

        svgParams.put(SvgParam.onload, "onLoad()"); //$NON-NLS-1$
        svgParams.put(SvgParam.onmouseover, "objectMouseOver(evt)"); //$NON-NLS-1$
        svgParams.put(SvgParam.onmouseout, "objectMouseOut(evt)"); //$NON-NLS-1$
        svgParams.put(SvgParam.onclick, "objectMouseClick(evt)"); //$NON-NLS-1$

        SvgProcessImageCreator svgCreator =
                new SvgProcessImageCreator(svgParams);

        try {
            svgCreator.createSVGImage(process, outFile);
            addProcessImageToMap(process.getId(), imagePath);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return;
    }

    /**
     * Create the raster (JPEG) image of process.
     * 
     * @param pWidget
     * @param pkg
     * @param process
     * @param destPath
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void createRasterImage(ProcessWidget pWidget, Package pkg,
            Process process, IPath destPath) throws FileNotFoundException,
            IOException {
        Image img = null;
        FileOutputStream imageFileStream = null;
        String fileName = createImageFileName(pkg, process);

        try {
            double scale = 1.0;

            try {
                img = pWidget.createProcessImageEx();
            } catch (HadToShrinkImageException e) {
                // Process widget had to shrtink the image to keep it within
                // system limits.
                img = e.getShrunkenImage();
                scale = e.getScale();
            }

            if (img != null) {
                ImageData imgData = img.getImageData();
                ImageLoader imgLoader = new ImageLoader();
                imgLoader.data = new ImageData[] { imgData };

                // Generate the images file path
                /*
                 * Used to save as BMP but that didn't work on Linux (red and
                 * blue values got swapped on eclipse 3.2.2) SO save as JPG
                 * instead for Linux (unfortunately, JPG save isn't great for
                 * windows) (Note that the Load Fragment code will cope with
                 * both JPG and BMP so it doesn't matter if fragments are being
                 * shared between OS's.
                 */
                String imageFileExt = getImageFileExt();

                IPath imageIPath = destPath.append(fileName + imageFileExt);
                imageFileStream = new FileOutputStream(imageIPath.toFile());

                //
                // Save the image.
                //
                /*
                 * SID: Note that we used to use BMP for Windows and JPG for
                 * Linux (because JPOG on windows wasn't very good prior to
                 * eclipse Europa and visa versa for Linux). However, there is a
                 * bug in WinBMPFileFormat(unloadDataNoCompression()) which
                 * causes infinite loop if image is greater than 8000 or so
                 * pixels wide) - this is too small a limit for processes so we
                 * will saves as JPEG from now on (europa jpeg output is as
                 * goiod as bmp on windows now anyway AND a LOT smaller!).
                 */

                imgLoader.save(imageFileStream, SWT.IMAGE_JPEG);
                imgLoader = null;
                imgData = null;
                addProcessImageToMap(process.getId(), imageIPath);
                /*
                 * Create a map for objects...
                 */
                Collection<ProcessWidget.ObjectPositionInfo> objPositions =
                        pWidget.getObjectPositionInfo();
                if (objPositions != null && objPositions.size() > 0) {
                    /*
                     * Use an output stream with UTF-8 encoding to preserve
                     * valid multiu-byte characters etc.
                     */
                    OutputStream mapOutStream = null;
                    OutputStreamWriter mapOutWriter = null;
                    ByteArrayInputStream mapContentInputStream = null;
                    // FileOutputStream mapFileWriter = null;

                    String mapFileName = "map_" + fileName + ".xml"; //$NON-NLS-1$ //$NON-NLS-2$
                    IPath mapIPath = destPath.append(mapFileName);

                    try {
                        mapOutStream = new FileOutputStream(mapIPath.toFile());
                        mapOutWriter =
                                new OutputStreamWriter(mapOutStream,
                                        Charset.forName("UTF-8")); //$NON-NLS-1$

                        mapOutWriter
                                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); //$NON-NLS-1$
                        mapOutWriter.write("<map>\n"); //$NON-NLS-1$
                        for (ObjectPositionInfo objInfo : objPositions) {
                            Rectangle scaledR = objInfo.bounds.getCopy();
                            scaledR.scale(scale);

                            mapOutWriter
                                    .write("\t<obj id=\"" + objInfo.objId //$NON-NLS-1$
                                            + "\" name=\"" + XmlUtil.escapeXmlCharacters(objInfo.objName) //$NON-NLS-1$
                                            + "\" x=\"" + scaledR.x + "\" y=\"" //$NON-NLS-1$ //$NON-NLS-2$
                                            + scaledR.y + "\" width=\"" //$NON-NLS-1$
                                            + scaledR.width + "\" height=\"" //$NON-NLS-1$
                                            + scaledR.height + "\"/>\n"); //$NON-NLS-1$
                        }
                        mapOutWriter.write("</map>\n"); //$NON-NLS-1$
                        mapOutWriter.flush();

                    } catch (Exception e) {
                        throw new RuntimeException(
                                "Exception creating image map file: " + mapIPath.toString(), e); //$NON-NLS-1$
                    } finally {
                        if (mapOutWriter != null) {
                            mapOutWriter.close();
                            mapOutWriter = null;
                        }

                        if (mapOutStream != null) {
                            mapOutStream.close();
                            mapOutStream = null;
                        }
                    }
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Exception creating image file: " + destPath.toString() + "/" + fileName, e); //$NON-NLS-1$ //$NON-NLS-2$
        } finally {
            try {
                // Close image file stream
                if (imageFileStream != null) {
                    imageFileStream.close();
                    imageFileStream = null;
                }

            } catch (IOException e2) {
                ; // ignore
            }

            // Dispose the image
            if (img != null) {
                img.dispose();
                img = null;
            }
        }
    }

    /**
     * @param pkg
     * @param process
     * @return
     */
    private String createImageFileName(Package pkg, Process process) {
        String fileName = pkg.getId() + "_" + process.getName() + "_" //$NON-NLS-1$ //$NON-NLS-2$
                + process.getId();
        return fileName;
    }

    /**
     * Get the file extension for created image files (.bmp for windows / .jpg
     * for Linux etc)
     * 
     * @return
     */
    public static String getImageFileExt() {
        String imageFileExt;

        //
        // SID: Note that we used to use BMP for Windows and JPG for Linux
        // (because JPOG on windows wasn't very good prior to eclipse Europa and
        // visa versa for Linux).
        // However, there is a bug in
        // WinBMPFileFormat(unloadDataNoCompression()) which causes infinite
        // loop if image is greater than 8000 or so pixels wide) - this is too
        // small a limit for processes so we will saves as JPEG from now on
        // (europa jpeg output is as goiod as bmp on windows now anyway AND a
        // LOT smaller!).
        if (System.getProperty("os.name").toUpperCase().startsWith( //$NON-NLS-1$
        "WIN")) {//$NON-NLS-1$
            // Windows now uses uses jpg too...
            imageFileExt = ".jpg"; //$NON-NLS-1$

        } else {
            // Linux etc use jpg...
            imageFileExt = ".jpg"; //$NON-NLS-1$
        }
        return imageFileExt;
    }

    /**
     * Create the folder to contain the images. This will be the /images folder
     * in the export destination folder.
     * 
     * @param outputFile
     * @return
     * @throws CoreException
     */
    private IPath createImageDestinationPath(File outputFile)
            throws CoreException {
        IPath path = null;

        if (outputFile != null) {
            File imgPath = new File(outputFile.getParentFile(), IMAGES_FOLDER);
            String errMsg = null;

            // If the folder doesn't exist then create it
            if (!imgPath.exists()) {
                // Create folder and any missing parent folders
                if (!imgPath.mkdirs()) {
                    // Failed to create folder
                    errMsg =
                            String.format(Messages.ProcDoc_FailedToCreateFolder,
                                    imgPath.toString());
                }
            } else if (!imgPath.isDirectory()) {
                // A file with the same name already exists so cannot create
                // image folder
                errMsg =
                        String.format(Messages.ProcDoc_UnableToCreateFolder,
                                imgPath.toString());
            }

            // If an error occurred then throw core exception
            if (errMsg != null) {
                throw new CoreException(new Status(IStatus.ERROR,
                        ProcdocPlugin.PLUGIN_ID, IStatus.OK, errMsg, null));
            } else {
                // No error occurred so return path
                path = new Path(imgPath.getAbsolutePath());
            }
        }

        return path;
    }

    public Map<String, IPath> getProcessImagesMap() {
        return processImagesMap;
    }

    private void addProcessImageToMap(String processId, IPath imagePath) {
        if (processId != null && imagePath != null) {
            if (processImagesMap == null) {
                processImagesMap = new HashMap<String, IPath>();
            }
            processImagesMap.put(processId, imagePath);
        }
    }

}
