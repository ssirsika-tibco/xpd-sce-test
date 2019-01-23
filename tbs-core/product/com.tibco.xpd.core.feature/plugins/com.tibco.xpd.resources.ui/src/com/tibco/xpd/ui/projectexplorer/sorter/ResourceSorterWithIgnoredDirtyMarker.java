package com.tibco.xpd.ui.projectexplorer.sorter;

import java.text.Collator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;

/**
 * Resource Sorter that sorts resources ignoring the dirty marker '*' on Files.
 * Folders are given order precedence, followed by files. The resources are
 * sorted in an alphabetical order.
 * 
 * @author kthombar
 * @since Jul 14, 2015
 */
public class ResourceSorterWithIgnoredDirtyMarker extends
        IgnoreDirtyMarkerViewerSorter {

    private static final int CATEGORY_FOLDER = 1;

    private static final int CATEGORY_FILE = 2;

    private static final int CATEGORY_OTHER = 100;

    public ResourceSorterWithIgnoredDirtyMarker() {
        // Default construxtor
    }

    public ResourceSorterWithIgnoredDirtyMarker(Collator collator) {

        super(collator);
    }

    @Override
    public int category(Object element) {
        int cat = CATEGORY_OTHER;

        if (element instanceof IFolder) {
            cat = CATEGORY_FOLDER;
        } else if (element instanceof IFile) {
            cat = CATEGORY_FILE;
        }
        return cat;
    }
}
