package com.tibco.xpd.gmf.extensions.hoverinfo;

/**
 * Interface for hover provider, it is used by Tooltip Figure for obtaining
 * 'live' hover info at the time when the tooltip is created.
 */
public interface HoverProvider {
	/**
	 * @return hover text for the item
	 */
	HoverInfo getHoverInfo();
}