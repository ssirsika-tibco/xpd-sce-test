/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.MultiPageSelectionProvider;

import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

/**
 * Selection provider set on the GSD editor site.
 * 
 * @author sajain
 * @since Feb 10, 2015
 */
public class GsdSelectionProvider extends
        MultiPageSelectionProvider {

    /**
     * Selection provider set on the GSD editor site.
     * 
     * @param multiPageEditor
     */
    public GsdSelectionProvider(
            GsdFormEditor editor) {

        super(editor);
    }

    /**
     * List of selection changed listeners.
     */
    private List<ISelectionChangedListener> selectionChangedListeners =
            new ArrayList<ISelectionChangedListener>();

    /**
     * Selection instance.
     */
    private ISelection selection;

    @Override
    public void addSelectionChangedListener(ISelectionChangedListener listener) {

        /*
         * Add selection changed listener.
         */
        selectionChangedListeners.add(listener);
    }

    @Override
    public ISelection getSelection() {

        /*
         * Check selection.
         */
        if (selection == null) {

            /*
             * Get gsd file.
             */
            IFile file =
                    ((IFileEditorInput) getMultiPageEditor().getEditorInput())
                            .getFile();

            /*
             * Get it's working copy.
             */
            WorkingCopy workingCopyObj =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(file);

            if (workingCopyObj instanceof AbstractWorkingCopy) {

                WorkingCopy workingCopy = workingCopyObj;

                /*
                 * Check if working copy's root element is correct and then only
                 * proceed.
                 */
                if (workingCopy != null
                        && workingCopy.getRootElement() instanceof GlobalSignalDefinitions) {

                    /*
                     * Return root element.
                     */
                    return new StructuredSelection(
                            ((workingCopy.getRootElement())));
                }
            }
        }
        return selection;
    }

    @Override
    public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {

        selectionChangedListeners.clear();
    }

    @Override
    public void setSelection(ISelection selection) {
        if (this.selection != selection) {
            this.selection = selection;
            selectionChanged(new SelectionChangedEvent(this, selection));
        }
    }

    /**
     * The selection has changed. Process the event.
     * 
     * @param event
     */
    public void selectionChanged(final SelectionChangedEvent event) {

        /*
         * Pass on the notification to listeners
         */

        Object[] listeners = selectionChangedListeners.toArray();

        for (int i = 0; i < listeners.length; ++i) {

            final ISelectionChangedListener l =
                    (ISelectionChangedListener) listeners[i];

            Platform.run(new SafeRunnable() {

                @Override
                public void run() {

                    l.selectionChanged(event);
                }
            });
        }
    }

}
