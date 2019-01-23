/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wm.tasklibrary.editor.compare;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.resources.ui.compare.viewer.EMFCompareDiffViewer;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * Task library diff viewer (top window in task library compare editor).
 * 
 * @author aallway
 * @since 2 Dec 2010
 */
public class TaskLibraryCompareDiffViewer extends EMFCompareDiffViewer {

    /**
     * @param parent
     * @param configuration
     */
    public TaskLibraryCompareDiffViewer(Composite parent,
            CompareConfiguration configuration) {
        super(parent, configuration, new TaskLibraryCompareStructureCreator());
    }

    /**
     * @see com.tibco.xpd.resources.ui.compare.viewer.EMFCompareDiffViewer#showInitially(java.lang.Object)
     * 
     * @param node
     * 
     * @return <code>true</code> if object should be shown initially
     */
    @Override
    protected boolean showInitially(Object node) {
        Object object = node;

        if (node instanceof EMFCompareNode) {
            object = ((EMFCompareNode) node).getObject();
        }

        /*
         * By default don't auto expand below the level of the major object
         * within process that can be seen within project explorer
         */
        if (object instanceof Package || object instanceof Process) {
            return true;

        } else if (object instanceof Pool || object instanceof Lane) {
            return true;

        } else if (object instanceof Participant || object instanceof DataField
                || object instanceof FormalParameter
                || object instanceof TypeDeclaration) {
            return true;

        } else if (object instanceof Activity || object instanceof Artifact
                || object instanceof Association) {
            return true;
        }
        return false;
    }
}
