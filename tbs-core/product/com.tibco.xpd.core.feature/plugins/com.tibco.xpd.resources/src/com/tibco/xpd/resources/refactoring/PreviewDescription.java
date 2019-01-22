/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.refactoring;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.compare.IEncodedStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;

/**
 * {@link ITypedElement} description for the compare view of the project
 * lifecycle refactor dialog.
 * 
 * @author njpatel
 * 
 */
public class PreviewDescription implements ITypedElement,
        IEncodedStreamContentAccessor {

    private final String content;

    private final Image image;

    private String charSet = "UTF-8"; //$NON-NLS-1$

    private final String name;

    /**
     * Preview input for the compare view of the project version refactor
     * dialog.
     * 
     * @param name
     *            name used in the UI
     * @param content
     *            the contents to display in the preview
     */
    public PreviewDescription(String name, String content) {
        this(name, content, null);
    }

    /**
     * Preview input with the content and image provided for the compare view of
     * the project version refactor dialog.
     * 
     * @param name
     *            name to use in the UI
     * @param content
     *            the contents to display in the preview
     * @param image
     *            image to use in the UI
     */
    public PreviewDescription(String name, String content, Image image) {
        this.name = name;
        this.content = content;
        this.image = image;
    }

    /**
     * Set the character set of the content. Default is UTF-8.
     * 
     * @param charSet
     */
    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.compare.ITypedElement#getImage()
     */
    public Image getImage() {
        return image;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.compare.ITypedElement#getName()
     */
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.compare.ITypedElement#getType()
     */
    public String getType() {
        return TEXT_TYPE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.compare.IEncodedStreamContentAccessor#getCharset()
     */
    public String getCharset() throws CoreException {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.compare.IStreamContentAccessor#getContents()
     */
    public InputStream getContents() throws CoreException {
        if (content != null) {
            return new ByteArrayInputStream(content.getBytes());
        }
        return null;
    }
}