/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.exportwizard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * This class represents the output file - this could either be a <code>File</code> or
 * an <code>IFile</code>. If the export destination is set to the export
 * folder in the project then this will be an <code>IFile</code>. This class
 * provides common methods to manipulate either type of object.
 * 
 * 
 */
public class OutputFile {
    
    private final Object oFile;

    private File file = null;

    private OutputStream outStream = null;

    public OutputFile(Object oFile) {
        this.oFile = oFile;
    }

    /**
     * Get the name of the file
     * 
     * @return Name of file
     */
    public String getName() {
        String name = null;

        if (getFile() != null) {
            name = getFile().getName();
        }

        return name;
    }

    /**
     * Get the path to this file
     * 
     * @return
     */
    public IPath getPath() {
        IPath path = null;

        if (oFile instanceof IFile) {
            path = ((IFile) oFile).getFullPath();
        } else if (oFile instanceof File) {
            path = new Path(((File) oFile).getAbsolutePath());
        }

        // Remove the file name from the path
        if (path != null && path.segmentCount() > 0) {
            path = path.removeLastSegments(1);
        }

        return path;
    }

    /**
     * Check if the output file exists
     * 
     * @return
     * @throws CoreException
     */
    public boolean exists() throws CoreException {
        boolean doesExist = false;

        if (oFile != null) {
            if (oFile instanceof IFile) {
                IFile ifile = (IFile) oFile;
                // Make sure the file is in synch with the file system
                if (!ifile.isSynchronized(IResource.DEPTH_ZERO)) {
                    ifile.refreshLocal(IResource.DEPTH_ZERO, null);
                }

                doesExist = ifile.exists();

            } else if (oFile instanceof File) {
                doesExist = ((File) oFile).exists();
            }
        }

        return doesExist;
    }

    /**
     * Returns the output stream of the file
     * <p>
     * <b>NOTE:</b><br/>
     * If this output file is an <code>IFile</code> then the
     * <code>ByteArrayOutputStream</code> will be returned. This is because the
     * <code>IFile</code> will need to be created (using IFile.create(...)).
     * After the output stream has been populated the method
     * <code>close()</code> of this class should be called. This will take care
     * of creating IFile (if the output file is an IFile) and will close the
     * output stream.
     * </p>
     * 
     * @return The following will be returned depending on the type of the
     *         output file:
     *         <ul>
     *         <li><code>File</code> - <code>FileOutputStream</code> will be
     *         returned</li>
     *         <li><code>IFile</code> - <code>ByteArrayOutputStream</code> will
     *         be returned</li>
     *         </ul>
     * @throws FileNotFoundException
     */
    public OutputStream getOutputStream() throws FileNotFoundException {

        if (outStream != null) {
            // Close the output stream.
            try {
                outStream.close();
                outStream = null;
            } catch (IOException e) {
                // Do nothing as we are closing output stream
            }
        }

        if (oFile != null) {
            if (oFile instanceof File) {
                outStream = new FileOutputStream((File) oFile);
            } else if (oFile instanceof IFile) {
                outStream = new ByteArrayOutputStream();
            }
        }

        return outStream;
    }

    /**
     * Will return the <code>IContainer</code> if this output file is an
     * <code>IFile</code>
     * 
     * @return <code>IContainer</code> if the output file is an
     *         <code>IFile</code>, otherwise it will return <b>null</b>.
     */
    public IContainer getParentIContainer() {
        IContainer container = null;

        if (oFile instanceof IFile) {
            container = ((IFile) oFile).getParent();
        }

        return container;
    }

    /**
     * Close the output stream that was created. Also, if this OutputFile
     * represents an IFile then it get's created.
     * 
     * @throws CoreException
     */
    public void close() throws CoreException {
        if (oFile != null) {
            // If this is an IFile then create it
            if (oFile instanceof IFile) {
                IFile iFile = (IFile) oFile;
                if (outStream != null) {
                    if (outStream instanceof ByteArrayOutputStream) {
                        ByteArrayOutputStream byteOutStream =
                                (ByteArrayOutputStream) outStream;
                        ByteArrayInputStream byteInStream =
                                new ByteArrayInputStream(byteOutStream
                                        .toByteArray());

                        // If the ifile exists then update it's content,
                        // otherwise create it
                        if (iFile.exists()) {
                            iFile.setContents(byteInStream, false, true, null);
                        } else {
                            iFile.create(byteInStream, false, null);
                        }
                    }
                }
            }

            // Close the output stream
            try {
                outStream.close();
            } catch (IOException e) {
                // Do nothing, closing stream
            }
            outStream = null;
        }
    }

    /**
     * Get the File
     * 
     * @return The output file in the file system
     */
    public File getFile() {

        // Convert object to file
        if (file == null && oFile != null) {
            if (oFile instanceof IFile) {
                file = ((IFile) oFile).getLocation().toFile();
            } else if (oFile instanceof File) {
                file = (File) oFile;
            }
        }

        return file;
    }
}