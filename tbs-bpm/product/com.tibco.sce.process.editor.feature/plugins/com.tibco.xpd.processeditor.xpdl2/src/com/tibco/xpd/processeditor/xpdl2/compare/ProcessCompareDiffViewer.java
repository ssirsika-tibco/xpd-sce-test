/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.ui.compare.nodes.emf.EMFCompareNode;
import com.tibco.xpd.resources.ui.compare.viewer.EMFCompareDiffViewer;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * Structured diff viewer for process package content (top window of compare
 * editor).
 * 
 * @author aallway
 * @since 12 Oct 2010
 */
public class ProcessCompareDiffViewer extends EMFCompareDiffViewer {

    /**
     * @param parent
     * @param configuration
     * @param structureCreator
     */
    public ProcessCompareDiffViewer(Composite parent,
            CompareConfiguration configuration) {
        super(parent, configuration, new ProcessCompareStructureCreator());
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
        if (object instanceof Package || object instanceof Process
                || object instanceof ProcessInterface) {
            return true;

        }
        // else if (object instanceof Pool || object instanceof Lane) {
        // return true;
        // }

        else if (object instanceof Participant || object instanceof DataField
                || object instanceof TypeDeclaration) {
            if (((EObject) object).eContainer() instanceof Package) {
                return true;
            } else {
                // return true;
            }
        }
        // else if (object instanceof Activity || object instanceof Artifact ||
        // object instanceof FormalParameter
        // || object instanceof Transition
        // || object instanceof MessageFlow
        // || object instanceof Association) {
        // return true;
        //
        // } else if (object instanceof InterfaceMethod
        // || object instanceof ErrorMethod) {
        // return true;
        // }

        return false;
    }
}
