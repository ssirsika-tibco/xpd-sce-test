/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui;

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
 * Global Signal Definition clipboard utils.
 * 
 * @author sajain
 * @since Feb 18, 2015
 */
public class GSDClipboardUtils {

    /**
     * Get clipboard to get hold of copied content.
     * 
     * @return Clipboard to get hold of copied content.
     */
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

    /**
     * Set clipboard.
     * 
     * @param clipboard
     */
    public static void setClipboard(Collection clipboard) {

        setClipboard(clipboard, null, null);
    }

    /**
     * Set the clipboard data.
     * 
     * @param clipboard
     * @param sourceGSDOrSignal
     * @param viewerContext
     */
    public static void setClipboard(Collection clipboard,
            EObject sourceGSDOrSignal, Object viewerContext) {

        Clipboard clp = new Clipboard(PlatformUI.getWorkbench().getDisplay());

        try {

            LocalTransfer lt = LocalTransfer.getInstance();

            FragmentLocalSelectionTransfer fragLocalSelTransfer =
                    FragmentLocalSelectionTransfer.getTransfer();

            /**
             * Wrap up the clipboard objects in our own ArraList sub-class that
             * will calculate and store the source GSD/Signal of the clipboard
             * objects.
             */
            Object[] clipObjects =
                    new Object[] {
                            new GSDClipboardDataList(clipboard,
                                    sourceGSDOrSignal, viewerContext),
                            new GSDClipboardDataList(clipboard,
                                    sourceGSDOrSignal, viewerContext) };

            Transfer[] clipTransfers =
                    new Transfer[] { lt, fragLocalSelTransfer };

            clp.setContents(clipObjects, clipTransfers);

        } finally {
            clp.dispose();
        }
    }

    /**
     * Provided that the clipboard objects were GSD/Signal objects added to
     * clipboard as a {@link GSDClipboardDataList} this method will retrieve the
     * source GSD object of that clipboard data IF that was available when the
     * data was added to clipboard.
     * 
     * @return The source GSD/Signal context of the data on the clipboard or
     *         <code>null</code> if it was not available when added to
     *         clipboard.
     */
    public static GSDClipboardDataList getSourceContextForClipboardContent() {

        Clipboard clp = new Clipboard(PlatformUI.getWorkbench().getDisplay());

        try {

            LocalTransfer lt = LocalTransfer.getInstance();

            Object cnt = clp.getContents(lt);

            if (cnt instanceof GSDClipboardDataList) {

                return ((GSDClipboardDataList) cnt);
            }

        } finally {

            clp.dispose();
        }

        return null;
    }

    /**
     * When placed on the clipboard this class wraps up the actual set of GSD
     * related objects.
     * 
     * @author sajain
     * @since Feb 25, 2015
     */
    @SuppressWarnings({ "rawtypes", "serial" })
    public static class GSDClipboardDataList extends ArrayList {

        /**
         * Source GSD or signal.
         */
        private EObject sourceGSDOrSignal;

        /**
         * Viewer context.
         */
        private Object viewerContext;

        /**
         * @param clipboardObjects
         */
        @SuppressWarnings("unchecked")
        public GSDClipboardDataList(Collection clipboardObjects,
                EObject sourceGSDOrSignal, Object viewerCOntext) {

            super(clipboardObjects);

            this.sourceGSDOrSignal = sourceGSDOrSignal;
            this.viewerContext = viewerCOntext;

        }

        /**
         * @return the sourceGSDOrSignal of the clipboard objects (if it could
         *         be ascertained during copy).
         */
        public EObject getSourceGSDOrSignal() {
            return sourceGSDOrSignal;
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
