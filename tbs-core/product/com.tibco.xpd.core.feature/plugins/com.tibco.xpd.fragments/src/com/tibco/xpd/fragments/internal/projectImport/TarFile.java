/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
/*******************************************************************************
 * Copyright (c) 2004, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.tibco.xpd.fragments.internal.projectImport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;

/**
 * Reads a .tar or .tar.gz archive file, providing an index enumeration and
 * allows for accessing an InputStream for arbitrary files in the archive.
 * 
 * @since 3.1
 */
public class TarFile {
    private File file;
    private TarInputStream entryEnumerationStream;
    private TarEntry curEntry;
    private TarInputStream entryStream;

    private InputStream internalEntryStream;

    /**
     * Create a new TarFile for the given file.
     * 
     * @param file
     * @throws TarException
     * @throws IOException
     */
    public TarFile(File file) throws TarException, IOException {
        this.file = file;

        InputStream in = new FileInputStream(file);
        // First, check if it's a GZIPInputStream.
        try {
            in = new GZIPInputStream(in);
        } catch (IOException e) {
            // If it is not compressed we close
            // the old one and recreate
            in.close();
            in = new FileInputStream(file);
        }
        try {
            entryEnumerationStream = new TarInputStream(in);
        } catch (TarException ex) {
            in.close();
            throw ex;
        }
        curEntry = entryEnumerationStream.getNextEntry();
    }

    /**
     * Close the tar file input stream.
     * 
     * @throws IOException
     *             if the file cannot be successfully closed
     */
    public void close() throws IOException {
        entryEnumerationStream.close();
        if (internalEntryStream != null)
            internalEntryStream.close();
    }

    /**
     * Create a new TarFile for the given path name.
     * 
     * @param filename
     * @throws TarException
     * @throws IOException
     */
    public TarFile(String filename) throws TarException, IOException {
        this(new File(filename));
    }

    /**
     * Returns an enumeration cataloguing the tar archive.
     * 
     * @return enumeration of all files in the archive
     */
    public Enumeration<TarEntry> entries() {
        return new Enumeration<TarEntry>() {
            public boolean hasMoreElements() {
                return (curEntry != null);
            }

            public TarEntry nextElement() {
                TarEntry oldEntry = curEntry;
                try {
                    curEntry = entryEnumerationStream.getNextEntry();
                } catch (TarException e) {
                    curEntry = null;
                } catch (IOException e) {
                    curEntry = null;
                }
                return oldEntry;
            }
        };
    }

    /**
     * Returns a new InputStream for the given file in the tar archive.
     * 
     * @param entry
     * @return an input stream for the given file
     * @throws TarException
     * @throws IOException
     */
    public InputStream getInputStream(TarEntry entry) throws TarException,
            IOException {
        if (entryStream == null || !entryStream.skipToEntry(entry)) {
            if (internalEntryStream != null) {
                internalEntryStream.close();
            }
            internalEntryStream = new FileInputStream(file);
            // First, check if it's a GZIPInputStream.
            try {
                internalEntryStream = new GZIPInputStream(internalEntryStream);
            } catch (IOException e) {
                // If it is not compressed we close
                // the old one and recreate
                internalEntryStream.close();
                internalEntryStream = new FileInputStream(file);
            }
            entryStream = new TarInputStream(internalEntryStream, entry) {
                public void close() {
                    // Ignore close() since we want to reuse the stream.
                }
            };
        }
        return entryStream;
    }

    /**
     * Returns the path name of the file this archive represents.
     * 
     * @return path
     */
    public String getName() {
        return file.getPath();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.zip.ZipFile#finalize()
     */
    protected void finalize() throws Throwable {
        close();
    }
}
