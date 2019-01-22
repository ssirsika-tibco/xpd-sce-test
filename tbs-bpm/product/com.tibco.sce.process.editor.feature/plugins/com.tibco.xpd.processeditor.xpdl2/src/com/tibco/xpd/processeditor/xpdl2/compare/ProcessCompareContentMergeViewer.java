/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.ui.compare.EMFCompareStructureCreator;
import com.tibco.xpd.resources.ui.compare.viewer.EMFCompareMergeViewer;

/**
 * {@link EMFCompareMergeViewer} specialised for process package
 * content.
 * 
 * @author aallway
 * @since 11 Oct 2010
 */
public class ProcessCompareContentMergeViewer extends
        EMFCompareMergeViewer {

    /**
     * @param parent
     * @param style
     * @param cc
     * @param compareSructureCreator
     */
    public ProcessCompareContentMergeViewer(Composite parent, int style,
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
        ProcessCompareStructureCreator creator =
                new ProcessCompareStructureCreator();
        return creator;
    }

}
