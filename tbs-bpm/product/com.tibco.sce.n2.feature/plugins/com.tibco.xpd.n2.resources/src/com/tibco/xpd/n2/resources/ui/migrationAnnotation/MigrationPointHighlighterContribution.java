/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.ui.migrationAnnotation;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.n2.resources.util.MigrationPointUtil;
import com.tibco.xpd.xpdl2.Process;

/**
 * Process diagram static highlighter contribution for migration point
 * activities.
 * 
 * @author aallway
 * @since 16 Jul 2012
 */
public class MigrationPointHighlighterContribution
        extends
        com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution {

    public MigrationPointHighlighterContribution() {
        super();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#shouldShow(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     * @return
     */
    @Override
    public boolean shouldShow(Process process) {
        /*
         * Only show the migration point highlighter option for N2 business
         * processes.
         */
        if ((MigrationPointUtil.doMigrationPointsApply(process))) {
            /*
             * Sid XPD-4209 : Check for disable migration point decorations
             * 
             * This allows user to prevent execution of the flow analyzer in
             * cases where the flow is so unstructured and large that it takes a
             * prohibitive amount of time to analyze the flow.
             */
            if (!MigrationPointUtil.migrationPointDecorationDisabled(process)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#getHighlightedDiagramModelObjects(com.tibco.xpd.xpdl2.Process)
     * 
     * @param diagramProcess
     * @return
     */
    @Override
    public Collection<? extends EObject> getHighlightedDiagramModelObjects(
            Process diagramProcess) {
        return MigrationPointUtil.getMigrationPointActivities(diagramProcess);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#getMenuText()
     * 
     * @return
     */
    @Override
    public String getMenuText() {
        return Messages.MigrationPointHighlighterContribution_HighlightMigrationPointActivities_menu;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#getActivatedTooltipLabel()
     * 
     * @return
     */
    @Override
    public String getActivatedTooltipLabel() {
        return Messages.MigrationPointHighlighterContribution_MigrationPointHighlighter_tooltip;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution#getMenuImageDescriptor()
     * 
     * @return
     */
    @Override
    public ImageDescriptor getMenuImageDescriptor() {
        return BundleActivator
                .getImageDescriptor(BundleActivator.ICON_MIGRATION_HIGHLIGHTER_MENU);
    }

}
