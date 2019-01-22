/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.daa.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.runtime.Path;

import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.internal.Messages;

/**
 * Util class to help zip up the N2PE plugin and feature files into a war ready
 * for deployment
 * 
 * @author glewis
 * 
 */
public final class N2PEZipAndFileUtils {
    /**
     * Zips a directory and all it's contents
     * 
     * @param dir2zip
     * @param targetZipFile
     * @param currentDirName
     * @param newRootDirName
     * @return
     */
    public static String zipDir(File dir2zip, ZipOutputStream zos,
            String currentDirName, String newRootDirName) {
        String featureXMLContents = ""; //$NON-NLS-1$
        try {
            String dir2zipStr = dir2zip.getAbsoluteFile().toString();
            featureXMLContents = zipDir(dir2zipStr.substring(0, dir2zipStr
                    .lastIndexOf(File.separatorChar)), dir2zip, zos,
                    currentDirName, newRootDirName, null, false);
        } catch (Exception e) {
            Activator.getDefault().getLogger().equals(e);
        }
        return featureXMLContents;
    }

    /**
     * Adds content of input stream to zipped output stream.
     * 
     * @param zip
     *            the zipped output stream.
     * @param in
     *            the input stream. Its content will be added as an entry's
     *            contents.
     * @param path
     *            path of the zip entry.
     * @throws IOException
     *             if there is problem with reading file or writing to stream.
     */
    public static void addEntryToZip(ZipOutputStream zip, InputStream in,
            String path, boolean closeInputStream) throws IOException {
        byte buffer[] = new byte[4000];
        zip.putNextEntry(new ZipEntry(path));
        int len;
        while ((len = in.read(buffer)) > 0) {
            zip.write(buffer, 0, len);
        }
        if (closeInputStream) {
            in.close();
        }
    }

    /**
     * Zips a directory and all it's contents
     * 
     * @param rootDir
     * @param zipDir
     * @param zos
     * @param currentDirName
     * @param newRootDirName
     * @param insideDir
     * @param forceUseRootDirName
     * @return
     */
    private static String zipDir(String rootDir, File zipDir,
            ZipOutputStream zos, String currentDirName, String newRootDirName,
            String insideDir, boolean forceUseRootDirName) {
        String featureXMLContents = ""; //$NON-NLS-1$
        try {
            // get a listing of the directory content
            String[] dirList = zipDir.list();
            byte[] readBuffer = new byte[2156];
            int bytesIn = 0;
            // loop through dirList, and zip the files
            for (int i = 0; i < dirList.length; i++) {
                File f = new File(zipDir, dirList[i]);
                if (f.isDirectory()) {
                    // if the File object is a directory, call this
                    // function again to add its content recursively
                    String tempXMLContents = zipDir(rootDir, f, zos,
                            currentDirName, newRootDirName, f.getName(), false);
                    if (tempXMLContents.trim().length() > 0) {
                        featureXMLContents = tempXMLContents;
                    }
                    // loop again
                    continue;
                }

                if (insideDir != null && insideDir.equalsIgnoreCase("features")) { //$NON-NLS-1$				    
                    String newFolderName = f.getAbsoluteFile().getName()
                            .replace(".jar", ""); //$NON-NLS-1$ //$NON-NLS-2$				    				     
                    String newFolderPath = new Path(newRootDirName).append(
                            insideDir).append(newFolderName).toPortableString();

                    copyZipEntrys(f, zos, "", //$NON-NLS-1$
                            newFolderPath);
                    featureXMLContents = getEntryContents(f, zos,
                            "feature.xml", //$NON-NLS-1$    
                            newFolderPath);
                } else {
                    // if we reached here, the File object f was not
                    // a directory
                    // create a FileInputStream on top of f
                    FileInputStream fis = new FileInputStream(f);
                    // create a new zip entry
                    // We don't want the full path in, just the first directory
                    String relativePath = f.getPath().replace(rootDir, ""); //$NON-NLS-1$
                    if (currentDirName != null && newRootDirName != null) {
                        relativePath = relativePath.replace(currentDirName,
                                newRootDirName);
                    }
                    if (forceUseRootDirName) {
                        relativePath = newRootDirName + File.separatorChar
                                + f.getName();
                    }

                    if (f.getName().equalsIgnoreCase("feature.xml")) { //$NON-NLS-1$
                        featureXMLContents = convertToString(f);
                    }

                    ZipEntry anEntry = new ZipEntry(relativePath);
                    // place the zip entry in the ZipOutputStream object
                    zos.putNextEntry(anEntry);
                    // now write the content of the file to the ZipOutputStream
                    while ((bytesIn = fis.read(readBuffer)) != -1) {
                        zos.write(readBuffer, 0, bytesIn);
                    }
                    // close the Stream
                    fis.close();
                }
            }
        } catch (Exception e) {
            Activator.getDefault().getLogger().equals(e);
        }
        return featureXMLContents;
    }

    private static void copyZipEntrys(File zipFile, ZipOutputStream zos,
            String inputEntryPath, String outputEntryPath) throws IOException {
        ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
        try {
            ZipEntry entry = zin.getNextEntry();
            while (entry != null) {
                String name = entry.getName();
                if (entry.getName().startsWith(inputEntryPath)) {
                    name = name.replace(inputEntryPath, ""); //$NON-NLS-1$
                    addEntryToZip(zos, zin, new Path(outputEntryPath).append(
                            name).toPortableString(), false);
                }
                entry = zin.getNextEntry();
            }
        } finally {
            // Close the streams
            zin.close();
        }
    }

    private static String getEntryContents(File zipFile, ZipOutputStream zos,
            String inputEntryPath, String outputEntryPath) throws IOException {
        ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
        String featureXMLContents = ""; //$NON-NLS-1$
        try {
            ZipEntry entry = zin.getNextEntry();
            while (entry != null) {
                String name = entry.getName();
                if (name.equals(inputEntryPath)) {
                    featureXMLContents = convertToString(zin);
                    break;
                }
                entry = zin.getNextEntry();
            }
        } finally {
            // Close the streams
            zin.close();
        }
        return featureXMLContents;
    }

    /**
     * @param archiveFile
     * @param outputDir
     * @return
     */
    public static File extractFromArchive(File archiveFile, File outputDir) {
        File parentDir = null;
        try {
            byte buffer[] = new byte[4000];
            // Open the archive file
            FileInputStream stream = new FileInputStream(archiveFile);
            ZipInputStream in = new ZipInputStream(stream);

            // Find archive entry
            while (true) {
                ZipEntry zipExtract = in.getNextEntry();
                if (zipExtract == null)
                    break;

                // Create output file and check required directory
                File outFile = new File(outputDir, zipExtract.getName());
                File parent = outFile.getParentFile();
                if (parent != null && !parent.exists())
                    parent.mkdirs();
                parentDir = parent;
                // Extract unzipped file
                FileOutputStream out = new FileOutputStream(outFile);
                while (true) {
                    int nRead = in.read(buffer, 0, buffer.length);
                    if (nRead <= 0)
                        break;
                    out.write(buffer, 0, nRead);
                }
                out.close();
                in.closeEntry();
            }

            in.close();
            stream.close();
        } catch (Exception ex) {
            Activator.getDefault().getLogger().equals(ex);
        }
        return parentDir;
    }

    /**
     * 
     * @param zipFile
     * @param files
     * @param newRootDirName
     * @throws IOException
     */
    public static void addFilesToExistingZip(File zipFile, File[] files,
            String newRootDirName) throws IOException {
        // get a temp file
        File tempFile = File.createTempFile(zipFile.getName(), null);

        // delete it, otherwise you cannot rename your existing zip to it.
        tempFile.delete();

        boolean renameOk = zipFile.renameTo(tempFile);
        if (!renameOk) {
            throw new RuntimeException(String.format(
                    Messages.N2PEZipAndFileUtils_renameError_message, zipFile
                            .getAbsolutePath(), tempFile.getAbsolutePath()));
        }
        byte[] buf = new byte[1024];

        ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

        ZipEntry entry = zin.getNextEntry();
        while (entry != null) {
            String name = entry.getName();
            boolean notInFiles = true;
            for (File f : files) {
                if (f.getName().equals(name)) {
                    notInFiles = false;
                    break;
                }
            }
            if (notInFiles) {
                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(name));
                // Transfer bytes from the ZIP file to the output file
                int len;
                while ((len = zin.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            entry = zin.getNextEntry();
        }
        // Close the streams
        zin.close();
        // Compress the files
        for (int i = 0; i < files.length; i++) {
            InputStream in = new FileInputStream(files[i]);
            // Add ZIP entry to output stream.
            if (newRootDirName != null) {
                out.putNextEntry(new ZipEntry(File.separatorChar
                        + newRootDirName + File.separatorChar
                        + files[i].getName()));
            } else {
                out.putNextEntry(new ZipEntry(files[i].getName()));
            }
            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            // Complete the entry
            out.closeEntry();
            in.close();
        }
        // Complete the ZIP file
        out.close();
        tempFile.delete();
    }

    /**
     * @param is
     * @param file
     */
    public static void writeToFile(InputStream is, File file) {
        try {
            DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(file)));
            int c;
            while ((c = is.read()) != -1) {
                out.writeByte(c);
            }
            is.close();
            out.close();
        } catch (IOException e) {
            Activator.getDefault().getLogger().equals(e);
        }
    }

    /**
     * Gets the contents of the file
     * 
     * @param inputStream
     * @return
     */
    public static String convertToString(InputStream inputStream)
            throws IOException {
        String stringContents = ""; //$NON-NLS-1$

        StringBuffer sb = new StringBuffer();
        char[] buffer = new char[4000];
        int len;

        InputStreamReader reader = new InputStreamReader(inputStream, Charset
                .forName("UTF-8")); //$NON-NLS-1$
        BufferedReader bufReader = new BufferedReader(reader);
        // try {
        while ((len = bufReader.read(buffer)) >= 0) {
            sb.append(buffer, 0, len);
        }
        /*
         * } finally { bufReader.close(); }
         */
        stringContents = sb.toString();

        return stringContents;
    }

    public static String convertToString(File file) throws IOException {
        return convertToString(new FileInputStream(file));
    }

    /**
     * Copy new contents back to file
     * 
     * @param file
     * @param xpdlContents
     */
    public static void setFileContents(File file, String xpdlContents) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(file), Charset.forName("UTF-8")); //$NON-NLS-1$
            writer.write(xpdlContents);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Activator.getDefault().getLogger().equals(e);
        }
    }
}