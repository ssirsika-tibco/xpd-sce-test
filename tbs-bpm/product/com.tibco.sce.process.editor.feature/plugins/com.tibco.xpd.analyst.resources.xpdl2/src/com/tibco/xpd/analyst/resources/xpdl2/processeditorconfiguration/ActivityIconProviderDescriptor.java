package com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.resources.IActivityIconProvider;

/**
 * Wraps a contributed {@link IActivityIconProvider} in order to pre-filter by
 * list of {@link ProcessEditorObjectType}'s in contribution.
 * 
 * @author aallway
 * @since 7 Dec 2011
 */
public class ActivityIconProviderDescriptor {
    private IActivityIconProvider iconProvider;

    Set<ProcessEditorObjectType> objectTypes =
            new HashSet<ProcessEditorObjectType>();

    public ActivityIconProviderDescriptor(IActivityIconProvider iconProvider) {
        super();
        this.iconProvider = iconProvider;
    }

    /**
     * @return the objectTypes
     */
    public Set<ProcessEditorObjectType> getApplicableObjectTypes() {
        return objectTypes;
    }

    /**
     * The provider should return <code>true</code> only for activities that it
     * either wants to set the icon for or define that there is no icon at all.
     * If the default icon is to be used for teh activity then this method
     * <b>must</b> return false.
     * 
     * @param activityModelObject
     * 
     * @return <code>true</code> If this icon provider should be used for the
     *         given activity.
     */
    public boolean isEnabled(Object activityModelObject) {
        if (activityModelObject instanceof Activity) {
            return iconProvider.isEnabled((Activity) activityModelObject);
        }
        return false;
    }

    /**
     * Only called if {@link #isEnabled(Activity)} returns <code>true</code> .
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
    public Image getImage(Object activityModelObject) {
        if (activityModelObject instanceof Activity) {
            return iconProvider.getImage((Activity) activityModelObject);
        }
        return null;
    }
}