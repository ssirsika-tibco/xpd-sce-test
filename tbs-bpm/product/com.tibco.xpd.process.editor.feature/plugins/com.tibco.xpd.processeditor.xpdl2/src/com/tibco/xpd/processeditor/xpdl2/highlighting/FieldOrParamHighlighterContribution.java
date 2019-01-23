/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.highlighting;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractReferenceHighlighterContribution;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamResolver;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Contribution to process diagram referencing object highlighter for formal
 * parameters and data fields.
 * 
 * @author aallway
 * @since 24 Jan 2011
 */
public class FieldOrParamHighlighterContribution extends
        AbstractReferenceHighlighterContribution {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractReferenceHighlighterContribution#getInterestingReferencedObjectClass()
     * 
     * @return
     */
    @Override
    public Class<? extends Object> getInterestingReferencedObjectClass() {
        return ProcessRelevantData.class;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractReferenceHighlighterContribution#isInScope(java.lang.Object,
     *      com.tibco.xpd.xpdl2.Process)
     * 
     * @param correctObject
     * @param process
     * @return
     */
    @Override
    public boolean isInScope(Object interestingReferencedObject, Process process) {
        ProcessRelevantData data =
                (ProcessRelevantData) interestingReferencedObject;
        /*
         * We are only interested in objects that are visible to our process.
         */
        boolean inScope = false;

        /* If has process ancestor then must be our process. */
        EObject ancProc = Xpdl2ModelUtil.getAncestor(data, Process.class);
        if (ancProc != null) {
            /* If it's under a process it has be our process! */
            if (ancProc.equals(process)) {
                inScope = true;
            }
        } else {
            /* If it's in a process, it must be in same package! */
            EObject ancPkg = Xpdl2ModelUtil.getAncestor(data, Package.class);
            if (ancPkg != null) {
                if (ancPkg.equals(process.getPackage())) {
                    inScope = true;
                }
            }
        }
        return inScope;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractReferenceHighlighterContribution#getReferencingDiagramModelObjects(java.lang.Object,
     *      com.tibco.xpd.xpdl2.Process)
     * 
     * @param interestingReferencedObject
     * @param diagramProcess
     * @return
     */
    @Override
    public Collection<? extends EObject> getReferencingDiagramModelObjects(
            Object interestingReferencedObject, Process diagramProcess) {
        ProcessRelevantData data =
                (ProcessRelevantData) interestingReferencedObject;

        List<EObject> referencingObjects =
                Xpdl2FieldOrParamResolver.getReferencingObjects(data);

        return referencingObjects;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractReferenceHighlighterContribution#getInterestingObjectLabel(java.lang.Object)
     * 
     * @param interestingReferencedObject
     * @return
     */
    @Override
    public String getInterestingObjectLabel(Object interestingReferencedObject) {
        ProcessRelevantData data =
                (ProcessRelevantData) interestingReferencedObject;

        if (data instanceof FormalParameter) {
            return String
                    .format(Messages.FieldOrParamHighlighterContribution_FormalParamLeader_tooltip,
                            Xpdl2ModelUtil.getDisplayNameOrName(data));
        } else {
            return String
                    .format(Messages.FieldOrParamHighlighterContribution_DataFieldLeader_tooltip,
                            Xpdl2ModelUtil.getDisplayNameOrName(data));
        }

    }
}
