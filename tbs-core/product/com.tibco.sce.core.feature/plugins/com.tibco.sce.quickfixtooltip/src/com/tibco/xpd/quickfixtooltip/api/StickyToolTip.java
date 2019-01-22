/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.quickfixtooltip.api;

import org.eclipse.draw2d.IFigure;

/**
 * When using {@link StickyToolTipHelper} or subclass of that for a GEF diagram
 * (via {@link QuickFixToolTipEnabledDomainEventDispatcher} or equivalent, any
 * tooltip figure returned from a GEF diagram figure's
 * {@link IFigure#getToolTip()} or teh diagram figure itself that implements
 * this interface will be given a sticky hover tooltip.
 * <p>
 * A sticky tooltip that appears directly under the mouse initially and
 * <i>unlike</i> the standard GEF tooltip will not disappear until the mouse
 * moves away from it (GEF tooltip disappears on timed basis OR when mouse moves
 * over sit).
 * <p>
 * The user can also select it by clicking anywhere on it - this prevents it
 * from disappearing when mouse moves away.
 * 
 * @author aallway
 * @since 3.2
 */
public interface StickyToolTip {
}