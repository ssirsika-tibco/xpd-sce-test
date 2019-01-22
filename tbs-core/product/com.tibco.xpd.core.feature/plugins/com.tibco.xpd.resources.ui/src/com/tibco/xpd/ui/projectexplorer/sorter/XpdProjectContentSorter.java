/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.ui.projectexplorer.sorter;

import java.text.Collator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * 
 * 
 * @author aallway
 * @since 15 Mar 2013
 */
public class XpdProjectContentSorter extends ViewerSorter {

    /**
     * 
     */
    public XpdProjectContentSorter() {
        super();
    }

    /**
     * @param collator
     */
    public XpdProjectContentSorter(Collator collator) {
        super(collator);
    }

    /**
     * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param e1
     * @param e2
     * @return
     */
    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {

        if (viewer instanceof TreeViewer) {
            IBaseLabelProvider labelProvider =
                    ((TreeViewer) viewer).getLabelProvider();

            if (labelProvider instanceof ILabelProvider) {
                boolean e1IsSpecial = isSpecialFolder(e1);
                boolean e2IsSpecial = isSpecialFolder(e2);

                if (e1IsSpecial && !e2IsSpecial) {
                    return -1;
                } else if (e2IsSpecial && !e1IsSpecial) {
                    return 1;
                } else {
                    String label1 =
                            getLabel((ILabelProvider) labelProvider, e1);
                    String label2 =
                            getLabel((ILabelProvider) labelProvider, e2);
                    return getComparator().compare(label1, label2);
                }
            }
        }

        return super.compare(viewer, e1, e2);
    }

    private String getLabel(ILabelProvider prov, Object o) {
        String text = prov.getText(o);

        return text != null ? text : ""; //$NON-NLS-1$
    }

    private boolean isSpecialFolder(Object o) {
        return getSpecialFolder(o) != null;
    }

    private SpecialFolder getSpecialFolder(Object o) {
        if (o instanceof SpecialFolder) {
            return (SpecialFolder) o;
        } else if (o instanceof IAdaptable) {
            return (SpecialFolder) ((IAdaptable) o)
                    .getAdapter(SpecialFolder.class);
        }
        return null;
    }

}
