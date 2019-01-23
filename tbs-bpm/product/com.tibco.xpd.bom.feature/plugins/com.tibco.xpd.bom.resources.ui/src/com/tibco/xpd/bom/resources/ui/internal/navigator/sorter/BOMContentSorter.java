/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.navigator.sorter;

import java.text.Collator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.ui.providers.DiagramGroupTransientItemProvider;
import com.tibco.xpd.ui.projectexplorer.sorter.IgnoreDirtyMarkerViewerSorter;

/**
 * Viewer sorter for the BOM project explorer contribution.
 * 
 * @author njpatel
 * 
 */
public class BOMContentSorter extends IgnoreDirtyMarkerViewerSorter {

    private static final int CAT_PACKAGE = 1;

    private static final int CAT_PRIMITIVE = 2;

    private static final int CAT_CLASS = 3;

    private static final int CAT_DIAGRAMS = 4;

    // For Eclipse resources
    private static final int CAT_CONTAINER = 1;

    private static final int CAT_FILE = 2;

    private static final int CAT_OTHER = 10;

    public BOMContentSorter() {
        // TODO Auto-generated constructor stub
    }

    public BOMContentSorter(Collator collator) {
        super(collator);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int category(Object element) {
        int cat = CAT_OTHER;

        if (element instanceof Package) {
            cat = CAT_PACKAGE;
        } else if (element instanceof PrimitiveType) {
            cat = CAT_PRIMITIVE;
        } else if (element instanceof Class) {
            cat = CAT_CLASS;
        } else if (element instanceof IContainer) {
            cat = CAT_CONTAINER;
        } else if (element instanceof IFile) {
            cat = CAT_FILE;
        } else if (element instanceof DiagramGroupTransientItemProvider) {
            cat = CAT_DIAGRAMS;
        }

        return cat;
    }

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        if (e1 instanceof Property && e2 instanceof Property) {
            // Preserve the order of the properties
            return -1;
        }

        return super.compare(viewer, e1, e2);
    }

}
