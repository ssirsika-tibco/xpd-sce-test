/**
 * ProcessClipboardUtils.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;

/**
 * ProcessClipboardUtils
 * 
 */
public class ProcessClipboardUtils {

    public static Collection getClipboard() {
        Clipboard clp = new Clipboard(PlatformUI.getWorkbench().getDisplay());
        List<Object> contents = new ArrayList<Object>();
        Object cnt = null;
        Object fragContents = null;
        try {
            LocalTransfer lt = LocalTransfer.getInstance();
            cnt = clp.getContents(lt);
            fragContents =
                    clp.getContents(FragmentLocalSelectionTransfer
                            .getTransfer());
        } finally {
            clp.dispose();
        }

        if (cnt instanceof Collection) {
            contents.addAll((Collection) cnt);

        } else if (fragContents instanceof IStructuredSelection) {

            IStructuredSelection structuredSelection =
                    (IStructuredSelection) fragContents;
            if (structuredSelection.getFirstElement() instanceof Collection) {
                contents.addAll((Collection) structuredSelection
                        .getFirstElement());

            }
        }

        if (contents.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return contents;
    }

    public static void setClipboard(Collection clipboard) {
        setClipboard(clipboard, null, null);
    }

    /**
     * Set the clipboard data. If a sourceProcessOrPackage is provided then it
     * will be available to the 'paste' end of clipboard via the
     * {@link #getSourceProcessOrPackageOfClipboardContent()}
     * 
     * @param clipboard
     * @param sourceProcessOrPackage
     */
    public static void setClipboard(Collection clipboard,
            EObject sourceProcessOrPackage, Object viewerContext) {
        Clipboard clp = new Clipboard(PlatformUI.getWorkbench().getDisplay());
        try {
            LocalTransfer lt = LocalTransfer.getInstance();

            FragmentLocalSelectionTransfer fragLocalSelTransfer =
                    FragmentLocalSelectionTransfer.getTransfer();

            /**
             * Wrap up the clipboard objects in our own ArraList sub-class that
             * will calculate and store the source process/package of the
             * clipboard objects.
             * 
             * This is then accessible thru
             * getSourceProcessOrPackageOfClipboardContent() method.
             * 
             * Because ProcessClipboardDataList sub-classes ArrayList we will
             * not break anything that is expecting teh clipboar data to be a
             * list :)
             */
            Object[] clipObjects =
                    new Object[] {
                            new ProcessClipboardDataList(clipboard,
                                    sourceProcessOrPackage, viewerContext),
                            new ProcessClipboardDataList(clipboard,
                                    sourceProcessOrPackage, viewerContext) };

            Transfer[] clipTransfers =
                    new Transfer[] { lt, fragLocalSelTransfer };

            clp.setContents(clipObjects, clipTransfers);

        } finally {
            clp.dispose();
        }
    }

    /**
     * Provided that the clipboard objects were process objects added to
     * clipboard as a {@link ProcessClipboardDataList} this method will retrieve
     * teh source process /package object of that clipboard data IF that was
     * available when the data was added to clipboard.
     * 
     * @return The source process / package context of the data on the clipboard
     *         or <code>null</code> if it was not available when added to
     *         clipboard.
     */
    public static ProcessClipboardDataList getSourceContextForClipboardContent() {
        Clipboard clp = new Clipboard(PlatformUI.getWorkbench().getDisplay());

        try {
            LocalTransfer lt = LocalTransfer.getInstance();

            Object cnt = clp.getContents(lt);

            if (cnt instanceof ProcessClipboardDataList) {
                return ((ProcessClipboardDataList) cnt);
            }

        } finally {
            clp.dispose();
        }

        return null;
    }

    /**
     * When placed on the clipboard this class wraps up the acutal set of
     * process related objects to copy allows the paste-end of the operation to
     * <p>
     * Because ProcessClipboardDataList sub-classes ArrayList we will not break
     * anything that is expecting the clipboard data to be a list :)
     * 
     * @author aallway
     * @since 20 Oct 2011
     */
    @SuppressWarnings({ "rawtypes", "serial" })
    public static class ProcessClipboardDataList extends ArrayList {

        private EObject sourceProcessOrPackage;

        private Object viewerContext;

        /**
         * @param clipboardObjects
         */
        @SuppressWarnings("unchecked")
        public ProcessClipboardDataList(Collection clipboardObjects,
                EObject sourceProcessOrPackage, Object viewerCOntext) {
            super(clipboardObjects);

            this.sourceProcessOrPackage = sourceProcessOrPackage;
            this.viewerContext = viewerCOntext;

        }

        /**
         * @return the sourceProcessOrPackage of the clipboard objects (if it
         *         could be ascertained during copy.
         */
        public EObject getSourceProcessOrPackage() {
            return sourceProcessOrPackage;
        }

        /**
         * @return the viewerContext of the clipboard objects (if it could be
         *         ascertained during copy.
         */
        public Object getViewerContext() {
            return viewerContext;
        }

    }

}
