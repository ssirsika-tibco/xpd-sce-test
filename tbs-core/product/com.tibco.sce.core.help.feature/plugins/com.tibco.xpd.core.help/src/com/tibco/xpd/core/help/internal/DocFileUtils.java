package com.tibco.xpd.core.help.internal;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.core.help.CoreHelpActivator;

/**
 * Utility functions used to download help.
 * 
 * @author jarciuch
 * @since 1 Aug 2014
 */
public class DocFileUtils {
    private static final int BUFFER_SIZE = 4096;

    private static void extractFile(ZipInputStream in, File outdir, String name)
            throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedOutputStream out =
                new BufferedOutputStream(new FileOutputStream(new File(outdir,
                        name)));
        int count = -1;
        while ((count = in.read(buffer)) != -1)
            out.write(buffer, 0, count);
        out.close();
    }

    private static void mkdirs(File outdir, String path) {
        File d = new File(outdir, path);
        if (!d.exists())
            d.mkdirs();
    }

    private static String dirpart(String name) {
        int s = name.lastIndexOf(File.separatorChar);
        return s == -1 ? null : name.substring(0, s);
    }

    /**
     * Extract zipfile to outdir with complete directory structure
     * 
     * @param zipfile
     *            Input .zip file
     * @param outdir
     *            Output directory
     */
    public static IStatus extract(File zipfile, File outdir,
            IProgressMonitor monitor) {
        String pluginId = CoreHelpActivator.PLUGIN_ID;
        int zipEntriesSize = -1;
        ZipFile file = null;
        try {
            file = new ZipFile(zipfile);
            zipEntriesSize = file.size();
        } catch (IOException e) {
            return new Status(IStatus.ERROR, pluginId,
                    Messages.DocFileUtils_DocExtractionProblem_message, e);
        } finally {
            try {
                if (file != null)
                    file.close();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
        try {

            monitor.beginTask(Messages.DocFileUtils_extractingFiles_message,
                    (zipEntriesSize == -1) ? IProgressMonitor.UNKNOWN
                            : zipEntriesSize);
            monitor.setTaskName(Messages.DocFileUtils_extractingFiles_message);
            ZipInputStream zin =
                    new ZipInputStream(new FileInputStream(zipfile));
            ZipEntry entry;
            String name, dir;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                if (entry.isDirectory()) {
                    mkdirs(outdir, name);
                    continue;
                }
                /*
                 * this part is necessary because file entry can come before
                 * directory entry where is file located i.e.: /foo/foo.txt
                 * /foo/
                 */
                dir = dirpart(name);
                if (dir != null)
                    mkdirs(outdir, dir);
                extractFile(zin, outdir, name);
                monitor.worked(1);
                if (monitor.isCanceled()) {
                    zin.close();
                    return Status.CANCEL_STATUS;
                }
            }
            zin.close();
            return Status.OK_STATUS;
        } catch (IOException e) {
            return new Status(IStatus.ERROR, pluginId,
                    Messages.DocFileUtils_DocExtractionProblem_message, e);
        } finally {
            monitor.done();
        }
    }

    /**
     * Downloads a file from a given url and writes it to a given File object.
     * 
     * @param url
     *            Input file url
     * @param outputFile
     *            The output file to write to
     * 
     */
    public static IStatus downloadFile(String url, File outputFile,
            IProgressMonitor monitor) {
        String pluginId = CoreHelpActivator.PLUGIN_ID;

        try {
            long startTime = System.currentTimeMillis();
            URL javaUrl = new URL(url);
            URLConnection urlConnection = javaUrl.openConnection();
            long contentLength = urlConnection.getContentLengthLong() / 1024;
            monitor.beginTask(Messages.DocFileUtils_downloadingDocs_label,
                    (int) contentLength);
            InputStream reader = urlConnection.getInputStream();
            FileOutputStream writer = new FileOutputStream(outputFile);
            byte[] buffer = new byte[153600];
            long totalBytesRead = 0;
            int bytesRead = 0;

            while ((bytesRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                monitor.setTaskName(String
                        .format(Messages.DocFileUtils_downloadingDocs_message,
                                (totalBytesRead / 1024),
                                contentLength));
                monitor.worked(bytesRead / 1024);
                if (monitor.isCanceled()) {
                    writer.close();
                    reader.close();
                    return Status.CANCEL_STATUS;
                }
            }

            long endTime = System.currentTimeMillis();

            CoreHelpActivator
                    .getDefault()
                    .logInfo(String.format("Downloading of the documentation has finished (%s). %s bytes read in : %s millseconds", //$NON-NLS-1$
                            url,
                            String.valueOf(totalBytesRead),
                            String.valueOf(endTime - startTime)));

            writer.close();
            reader.close();
            return Status.OK_STATUS;
        } catch (UnknownHostException e) {
            return new Status(
                    IStatus.ERROR,
                    pluginId,
                    Messages.DocFileUtils_DocServerNotFound_message,
                    new RuntimeException(String
                            .format(Messages.DocFileUtils_ServerNotFound_message,
                                    e.getMessage()), e));
        } catch (IOException e) {
            return new Status(
                    IStatus.ERROR,
                    pluginId,
                    Messages.DocFileUtils_DocDownloadFailed_message,
                    e);
        } finally {
            monitor.done();
        }
    }
}