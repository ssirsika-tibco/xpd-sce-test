/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resources;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.xpdl2.Activity;

/**
 * For support of the
 * <code>com.tibco.xpd.analyst.resources.xpdl2.processEditoConfiguration/ActivityIconOverrides/ActiviyIconProvider</code>
 * element.
 * 
 * @author aallway
 * @since 7 Dec 2011
 */
public interface IActivityIconProvider {

    /**
     * The provider should return <code>true</code> only for activities that it
     * either wants to set the icon for or define that there is no icon at all.
     * If the default icon is to be used for teh activity then this method
     * <b>must</b> return false.
     * 
     * @param activity
     * 
     * @return <code>true</code> If this icon provider should be used for the
     *         given activity.
     */
    boolean isEnabled(Activity activity);

    /**
     * Only called if {@link #isEnabled(Activity)} returns <code>true</code>.
     * <p>
     * <b>NOTE that the returned image will NOT be disposed therefore the
     * provider should return only images that are loaded once and then never
     * disposed (for instance by loading from the host plugin's image
     * registry).</b>
     * 
     * @param activity
     * 
     * @return The image descriptor for the activity or <code>null</code> will
     *         cause the activity to use the default icon
     */
    Image getImage(Activity activity);

}
