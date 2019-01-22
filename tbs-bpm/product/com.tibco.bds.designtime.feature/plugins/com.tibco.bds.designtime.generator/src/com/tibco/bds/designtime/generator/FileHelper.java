package com.tibco.bds.designtime.generator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;

public class FileHelper {

    public static void copyInputStreamToFile(InputStream source, IFile target)
            throws IOException, CoreException {
        if (!target.exists()) {
            target.create(source, true, null);
        } else {
            target.setContents(source, true, false, null);
        }
    }

    /**
     * Copies the given file to the given target directory. The name of the file
     * is preserved.
     * 
     * @param source
     * @param targetDir
     * @throws IOException
     * @throws CoreException
     */
    public static void copyInputStreamToDir(InputStream source,
            String sourceName, IFolder targetDir) throws IOException,
            CoreException {
        // Create all required directories first
        createDirectoryTree(targetDir);
        copyInputStreamToFile(source, targetDir.getFile(sourceName));
    }

    public static void copyFileToDir(File source, IFolder targetDir)
            throws IOException, CoreException {
        InputStream in = new BufferedInputStream(new FileInputStream(source));
        copyInputStreamToDir(in, source.getName(), targetDir);
    }

    public static File createTempDir(String prefix, String suffix)
            throws IOException {
        File file = File.createTempFile(prefix, suffix);
        if (file.delete()) {
            if (!file.mkdirs()) {
                throw new IOException(
                        String
                                .format("Failed to replace temp file %s with a directory",
                                        file));
            }
        } else {
            throw new IOException(
                    "Failed to delete temp file %s prior to creating temp directory with same name");
        }
        return file;
    }

    public static String readContents(InputStream stream, boolean withNewLines)
            throws IOException {
        StringBuilder str = new StringBuilder();
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(stream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            // Print the content on the console
            str.append(strLine);
            if (withNewLines) {
                str.append("\n");
            }
        }
        // Close the input stream
        in.close();

        return str.toString();
    }

    /**
     * This method takes a string and the name and location of a file and then
     * copies the contents of the string into the given file and location
     * 
     * @param source
     *            String data to copy into the file
     * @param sourceName
     *            Name of the file to copy data to
     * @param targetDir
     *            The directory of the file
     * @throws IOException
     * @throws CoreException
     */
    public static void copyStringToFileDir(String source, String sourceName,
            IFolder targetDir) throws IOException, CoreException {
        // Create all required directories first
        createDirectoryTree(targetDir);

        IFile targetFile = targetDir.getFile(sourceName);

        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(source.getBytes());

        if (!targetFile.exists()) {
            targetFile.create(inputStream, true, null);
        } else {
            targetFile.setContents(inputStream, true, false, null);
        }
    }

    /**
     * This method will take an input stream pointing to a zip file and will
     * extract it to under the target folder
     * 
     * @param resourceIn
     *            Input stream to a zip file
     * @param targetFolder
     *            Where to extract the zip file to
     * @throws IOException
     */
    public static void unzipFromResource(InputStream resourceIn,
            File targetFolder) throws IOException {
        if (targetFolder.isFile()) {
            targetFolder.delete();
        }
        targetFolder.mkdirs();
        ZipInputStream zis = new ZipInputStream(resourceIn);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            File targetFile = new File(targetFolder, entry.getName());
            if (entry.isDirectory()) {
                targetFile.mkdirs();
            } else {
                targetFile.getParentFile().mkdirs();
                OutputStream out =
                        new BufferedOutputStream(new FileOutputStream(
                                targetFile));
                byte[] buf = new byte[1024];
                int len;
                while ((len = zis.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
            }
        }
        zis.close();
    }

    /**
     * Creates an entire directory structure if it does not exist
     * 
     * @param targetDir
     *            Directory to create
     * @throws CoreException
     */
    private static void createDirectoryTree(IFolder targetDir)
            throws CoreException {
        // Create the directory if it does not already exist
        if (!targetDir.exists()) {
            // First check that the parent directory exists
            IContainer parent = targetDir.getParent();
            if (parent.getType() == IFolder.FOLDER) {
                createDirectoryTree((IFolder) parent);
            }
            // Now perform the create
            targetDir.create(true, true, null);
        }
    }
}
