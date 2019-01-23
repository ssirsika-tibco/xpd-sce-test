/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.quickfixtooltip.api;

import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.ui.IMarkerResolution;

/**
 * When using the {@link QuickFixToolTipHelper} (via the
 * {@link QuickFixToolTipEnabledDomainEventDispatcher}), any diagram figure that
 * returns an instance of this class (from it's {@link IFigure#getToolTip()}
 * method) will switch to using a special problem marker & quick fixes tooltip.
 * <p>
 * <b>This is a dummy figure that is NOT actually used for any display
 * purpose</b>.
 * <p>
 * This strategy is employed because the GEF mechanism has ingrained the fact
 * that it will ask the figure under mouse for a tooltip figure. This figure is
 * then passed to the ToolTipHelper. Therefore by using a
 * {@link QuickFixToolTipProviderFigure} the {@link QuickFixToolTipHelper} can
 * switch the standard popup behaviour for the new quick fix behaviour.
 * <p>
 * This class is constructed with an {@link IQuickFixToolTipContentProvider} and
 * a 'markerHost'. The markerHost can be any object of the creator's choice - it
 * will be passed to the creator's
 * {@link IQuickFixToolTipContentProvider#getMarkers(Object)} method.
 * <p>
 * The {@link IQuickFixToolTipContentProvider} will be used to access both the
 * markers and the marker resolutions (quick fixes) for the given marker host.
 * 
 * @author aallway
 * @since 3.2
 */
public final class QuickFixToolTipProviderFigure extends Figure implements
        StickyToolTip {

    /**
     * Used by quick fix tooltip helper to access the outstanding markers for a
     * given diagram object.
     * 
     * @author aallway
     * @since 3.2
     */
    public interface IQuickFixToolTipContentProvider {
        /**
         * Return the markers for the gven markerHost.
         * 
         * @param markerHost
         *            This is the markerHost object that the
         *            {@link QuickFixToolTipProviderFigure} was constructed with
         *            initially.
         * 
         * @return The markers for the given markerHost object.
         */
        Collection<IMarker> getMarkers(Object markerHost);

        /**
         * @param marker
         *            The marker to retrn the resolutions for
         * @return The marker resolutions (quick fixes) for the given marker.
         */
        Collection<IMarkerResolution> getMarkerResolutions(IMarker marker);
    }

    private Object markerHost;

    private IQuickFixToolTipContentProvider markersProvider;

    public QuickFixToolTipProviderFigure(Object markerHost,
            IQuickFixToolTipContentProvider markersProvider) {
        this.markerHost = markerHost;
        this.markersProvider = markersProvider;
    }

    /**
     * @return the markerHost
     */
    public Object getMarkerHost() {
        return markerHost;
    }

    /**
     * @return the markersProvider
     */
    public IQuickFixToolTipContentProvider getMarkersProvider() {
        return markersProvider;
    }

}