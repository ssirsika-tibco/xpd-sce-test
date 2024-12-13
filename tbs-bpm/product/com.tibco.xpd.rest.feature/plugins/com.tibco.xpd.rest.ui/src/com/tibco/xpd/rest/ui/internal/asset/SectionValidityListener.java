/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.rest.ui.internal.asset;

/**
 * Implement this listener from a page to be informed of page validity changes in RestFilePage
 *
 * @author nkelkar
 * @since Jul 24, 2024
 */
public interface SectionValidityListener
{
	/**
	 * Set the validity of the implementor. Called when consumed control changes the validity of the page.
	 * 
	 * @param isValid
	 * @param errorMsg
	 */
	void validityChanged(boolean isValid, String errorMsg);
}
