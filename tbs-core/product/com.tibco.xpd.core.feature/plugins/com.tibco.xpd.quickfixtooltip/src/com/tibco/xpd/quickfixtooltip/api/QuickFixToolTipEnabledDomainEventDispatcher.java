/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.quickfixtooltip.api;

import org.eclipse.draw2d.ToolTipHelper;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.parts.DomainEventDispatcher;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;

import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipProviderFigure.IQuickFixToolTipContentProvider;

/**
 * This class can be used in a GEF diagram in place of the standard GEF
 * {@link DomainEventDispatcher}.
 * <p>
 * Having done so then any diagram figure that returns an instanceof
 * {@link QuickFixToolTipProviderFigure} will automatically show a marker &
 * quick-fix tooltip.
 * <p>
 * This tooltip will use the {@link IQuickFixToolTipContentProvider} that was
 * used to construct the {@link QuickFixToolTipProviderFigure} to access the
 * list of problem / warning / info markers for the diagram object and a list of
 * resolutions for that if any.
 * <p>
 * You may also specify {@link StickyToolTip} implementing diagram / tooltip
 * figures for 'sticky' behaviour normal tooltips.
 * <p>
 * <b>To use this event dispatcher in your GEF diagram...</b>
 * <li>In your GEF diagram graphical viewer class (that extends
 * {@link GraphicalViewerImpl} add a new field for the quick fix domain event
 * dispatcher (correct as of org.eclipse.gef plugin v3.4.1)...</li>
 * 
 * <pre>
 * private QuickFixToolTipEnabledDomainEventDispatcher stickyToolTipEnabledDomainEventDispatcher;
 * </pre>
 * 
 * <li>Then override the {@link GraphicalViewerImpl#setEditDomain(EditDomain)}
 * method as follows...</li>
 * 
 * <pre>
 * 
 * public void setEditDomain(EditDomain domain) {
 *     // Override to set our own domain event dispatcher, doing so seems to be
 *     // the ONLY way of providing a different ToolTipHelper which seems to be
 *     // the only way of providing a way of not destroying the tooltip if the
 *     // mouse is moved away from original host figure and over the tooltip.
 *     super.setEditDomain(domain);
 * 
 *     stickyToolTipEnabledDomainEventDispatcher =
 *             new QuickFixToolTipEnabledDomainEventDispatcher(domain, this);
 * 
 *     getLightweightSystem()
 *             .setEventDispatcher(stickyToolTipEnabledDomainEventDispatcher);
 * 
 *     return;
 * }
 * 
 * </pre>
 * 
 * <li>And then override the {@link GraphicalViewerImpl#getEventDispatcher()}</li>
 * 
 * <pre>
 * protected DomainEventDispatcher getEventDispatcher() {
 *     // Override to set our own domain event dispatcher, doing so seems to be
 *     // the ONLY way of providing a different ToolTipHelper which seems to be
 *     // the only way of providing a way of not destroying the tooltip if the
 *     // mouse is moved awy from original host figure and over the tooltip.
 *     //
 *     // We overrode the domain event dispatcher so we should get underlying
 *     // class to use ours.
 *     return stickyToolTipEnabledDomainEventDispatcher;
 * }
 * </pre>
 * 
 * 
 * @author aallway
 * @since 3.2
 */
public class QuickFixToolTipEnabledDomainEventDispatcher extends
        DomainEventDispatcher {

    private QuickFixToolTipHelper toolTipHelper;

    public QuickFixToolTipEnabledDomainEventDispatcher(EditDomain editDomain,
            EditPartViewer viewer) {
        super(editDomain, viewer);
    }

    @Override
    protected ToolTipHelper getToolTipHelper() {
        if (toolTipHelper == null)
            toolTipHelper = new QuickFixToolTipHelper(control);
        return toolTipHelper;
    }
}