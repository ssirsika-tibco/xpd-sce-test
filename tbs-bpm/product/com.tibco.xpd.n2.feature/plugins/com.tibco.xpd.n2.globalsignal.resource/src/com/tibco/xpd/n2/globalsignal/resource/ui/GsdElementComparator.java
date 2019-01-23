/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

import com.tibco.xpd.globalSignalDefinition.PayloadDataField;

/**
 * Comparator class to sort the GSD elements (Global Signals/Payload Data) in
 * GSD form editor.
 * 
 * @author aallway
 * @since 24 Jun 2015
 */
public class GsdElementComparator extends ViewerComparator {

    /**
     * GSD Label Provider instance.
     */
    private GsdLabelProvider labelProvider = new GsdLabelProvider();

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
    public int compare(Viewer viewer, Object o1, Object o2) {

        /* IF both are data fields then group by correlation data or not first . */
        if (o1 instanceof PayloadDataField && o1 instanceof PayloadDataField) {

            boolean isCorrelate1 = ((PayloadDataField) o1).isCorrelation();

            boolean isCorrelate2 = ((PayloadDataField) o2).isCorrelation();

            if (isCorrelate1 && !isCorrelate2) {

                return -1;

            } else if (isCorrelate2 && !isCorrelate1) {

                return 1;

            }
        }

        /*
         * And everything else (or for fields with matching correlation type)
         * then sort by label by label .
         */

        String n1 = labelProvider.getText(o1);
        String n2 = labelProvider.getText(o2);

        if (n1 == null || n2 == null) {

            return 0;

        }

        return n1.compareToIgnoreCase(n2);
    }
}
