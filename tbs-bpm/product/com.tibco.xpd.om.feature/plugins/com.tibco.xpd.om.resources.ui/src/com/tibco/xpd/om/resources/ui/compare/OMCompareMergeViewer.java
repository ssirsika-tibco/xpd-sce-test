/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.compare;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator;
import com.tibco.xpd.resources.ui.compare.viewer.EMFCompareMergeViewer;

/**
 * EXPERMIMENTAL P.O.C contribution of Comparison tool for Organisation Model.
 * <p>
 * This works but is incomplete (basic comparison is performed or OrgModel root
 * element but further compare node specialisation is required <b>INCLUDING</b>
 * the encapsulation of graphical property elements (from the Diagram elements
 * corresponding to each OrgModel element) within the children of the nodes for
 * the OrgModel elements).
 * <p>
 * The two contributions for org model diff viewer and merge viewer in
 * comp.tibco.xpd.om.resources.ui are currently commented out (look for
 * XPD-1128)
 * 
 * 
 * @author aallway
 * @since 3 Dec 2010
 */
public class OMCompareMergeViewer extends EMFCompareMergeViewer {

    /**
     * @param parent
     * @param style
     * @param cc
     */
    public OMCompareMergeViewer(Composite parent, int style,
            CompareConfiguration cc) {
        super(parent, style, cc);
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.viewer.EMFCompareMergeViewer#createStructureCreator()
     * 
     * @return
     */
    @Override
    protected EMFCompareStructureCreator createStructureCreator() {
        return new OMCompareStructureCreator();
    }

    public static class OMMergeViewerCreator implements IViewerCreator {

        /**
         * @see org.eclipse.compare.IViewerCreator#createViewer(org.eclipse.swt.widgets.Composite,
         *      org.eclipse.compare.CompareConfiguration)
         * 
         * @param parent
         * @param config
         * @return
         */
        @Override
        public Viewer createViewer(Composite parent, CompareConfiguration config) {
            return new OMCompareMergeViewer(parent, SWT.NONE, config);
        }

    }

}
