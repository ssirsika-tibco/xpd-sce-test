/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.compare.nodes;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode;
import com.tibco.xpd.resources.ui.compare.EMFCompareNodeFactory;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;

/**
 * Process artifact object compare node.
 * 
 * @author aallway
 * @since 30 Nov 2010
 */
public class ArtifactCompareNode extends NamedElementCompareNode {

    private Artifact artifact;

    /**
     * @param artifact
     * @param listIndex
     * @param feature
     * @param parentCompareNode
     * @param compareNodeFactory
     */
    public ArtifactCompareNode(Artifact artifact, int listIndex,
            EStructuralFeature feature, Object parentCompareNode,
            EMFCompareNodeFactory compareNodeFactory) {
        super(artifact, listIndex, feature, parentCompareNode,
                compareNodeFactory);

        this.artifact = artifact;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.compare.nodes.base.NamedElementCompareNode#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        if (ArtifactType.ANNOTATION_LITERAL
                .equals((artifact.getArtifactType()))) {
            String annot = artifact.getTextAnnotation();
            if (annot != null) {
                annot = annot.replaceAll("[ \t\r\n]", " "); //$NON-NLS-1$ //$NON-NLS-2$
                if (annot.length() > 50) {
                    annot = annot.substring(0, 50) + "...";//$NON-NLS-1$
                }
                return annot;
            }
            return ""; //$NON-NLS-1$
        }
        return super.getName();
    }
}
