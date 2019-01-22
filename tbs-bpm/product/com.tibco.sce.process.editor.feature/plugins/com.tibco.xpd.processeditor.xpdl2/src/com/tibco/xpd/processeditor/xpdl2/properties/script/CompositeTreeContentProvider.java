/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * The ScriptableTreeContentProvider allows a tree content provider and a script
 * provider to be combined into a single ITreeContentProvider.
 * 
 * @author nwilson
 */
public class CompositeTreeContentProvider implements ITreeContentProvider {

    /** The content provider. */
    private ITreeContentProvider content;

    /** The script provider. */
    private IStructuredContentProvider scripts;

    /**
     * @param content
     *            The content provider.
     * @param scripts
     *            The script provider.
     */
    public CompositeTreeContentProvider(ITreeContentProvider content,
            IStructuredContentProvider scripts) {
        this.content = content;
        this.scripts = scripts;
    }

    /**
     * @param parentElement
     *            The parent element.
     * @return An array of child elements.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(
     *      java.lang.Object)
     */
    public Object[] getChildren(Object parentElement) {
        return content.getChildren(parentElement);
    }

    /**
     * @param element
     *            The element to get the parent for.
     * @return The parent or null.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(
     *      java.lang.Object)
     */
    public Object getParent(Object element) {
        return content.getParent(element);
    }

    /**
     * @param element
     *            The element to check.
     * @return true if the element has children.
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(
     *      java.lang.Object)
     */
    public boolean hasChildren(Object element) {
        return content.hasChildren(element);
    }

    /**
     * @param inputElement
     * @return
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
     *      java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        Object[] scriptElements = scripts.getElements(inputElement);
        Object[] contentElements = content.getElements(inputElement);
        if (scriptElements == null) {
            scriptElements = new Object[0];
        }
        if (contentElements == null) {
            contentElements = new Object[0];
        }
        int sl = scriptElements.length;
        int cl = contentElements.length;
        Object[] elements = new Object[cl + sl];
        System.arraycopy(scriptElements, 0, elements, 0, sl);
        System.arraycopy(contentElements, 0, elements, sl, cl);

        return elements;
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
        content.dispose();
        scripts.dispose();
    }

    /**
     * @param viewer
     * @param oldInput
     * @param newInput
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        content.inputChanged(viewer, oldInput, newInput);
        scripts.inputChanged(viewer, oldInput, newInput);
    }

}
