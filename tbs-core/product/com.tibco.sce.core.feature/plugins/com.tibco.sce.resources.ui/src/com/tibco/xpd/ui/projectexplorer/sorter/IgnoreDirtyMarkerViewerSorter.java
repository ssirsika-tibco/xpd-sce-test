/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.sorter;

import java.text.Collator;
import java.util.Comparator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * Viewer sorter that ignores the dirty (*) prefix marker in resource labels.
 * This is expected to be used by all navigator content sorters.
 * 
 * @author njpatel
 * 
 */
public class IgnoreDirtyMarkerViewerSorter extends ViewerSorter {

    /**
     * Comparator implementation that will compare the file names without the
     * dirty marker. This will be a wrapper around the default comparator used
     * by this viewer sorter.
     * 
     * @author njpatel
     * 
     */
    protected class IgnoreDirtyMarkerComparator implements Comparator<String> {

        private final Comparator<String> comparator;

        private boolean ignoreDirtyMarker = false;

        /**
         * Constructor. Ignore dirty marker comparator.
         * 
         * @param comparator
         *            default comparator used by the sorter.
         */
        public IgnoreDirtyMarkerComparator(Comparator<String> comparator) {
            this.comparator = comparator;
        }

        /**
         * Ignore the dirty marker.
         * 
         * @param ignoreDirtyMarker
         *            <code>true</code> to ignore dirty marker,
         *            <code>false</code> otherwise.
         */
        public void setIgnoreDirtyMarker(boolean ignoreDirtyMarker) {
            this.ignoreDirtyMarker = ignoreDirtyMarker;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(String o1, String o2) {
            return comparator.compare(removeDirtyMarkerIfNecessary(o1),
                    removeDirtyMarkerIfNecessary(o2));
        }

        /**
         * If ignoring dirty marker (* at start of name) then return the string
         * without the leading '*'
         * 
         * @param str
         * @return
         */
        private String removeDirtyMarkerIfNecessary(String str) {
            if (str != null && ignoreDirtyMarker) {
                if (str.length() > 2 && str.substring(0, 2).equals("* ")) { //$NON-NLS-1$
                    str = str.substring(2);
                } else if (str.length() > 1 && str.substring(0, 1).equals("*")) { //$NON-NLS-1$
                    str = str.substring(1);
                }
            }
            return str;
        }
    }

    private final IgnoreDirtyMarkerComparator dirtyMarkerComparator;

    /**
     * Default constructor
     */
    @SuppressWarnings("unchecked")
    public IgnoreDirtyMarkerViewerSorter() {
        super();
        // Create a wrapper comparator that will ignore dirty markers on files
        dirtyMarkerComparator =
                new IgnoreDirtyMarkerComparator((Comparator<String>)super.getComparator());
    }

    /**
     * Constructor.
     * 
     * @param collator
     */
    @SuppressWarnings("unchecked")
    public IgnoreDirtyMarkerViewerSorter(Collator collator) {
        super(collator);
        // Create a wrapper comparator that will ignore dirty markers on files
        dirtyMarkerComparator =
                new IgnoreDirtyMarkerComparator((Comparator<String>)super.getComparator());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Comparator getComparator() {
        // Return the wrapper comparator that will ignore dirty markers on files
        return dirtyMarkerComparator;
    }

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        if (e1 instanceof IFile && e2 instanceof IFile) {
            // When comparing files, ignore dirty marker.
            dirtyMarkerComparator.setIgnoreDirtyMarker(true);
        } else {
            dirtyMarkerComparator.setIgnoreDirtyMarker(false);
        }
        return super.compare(viewer, e1, e2);
    }

}
