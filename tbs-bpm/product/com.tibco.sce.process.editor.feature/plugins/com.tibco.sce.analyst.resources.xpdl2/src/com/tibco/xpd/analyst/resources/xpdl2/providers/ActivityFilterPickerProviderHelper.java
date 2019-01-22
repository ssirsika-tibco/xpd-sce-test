/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.providers;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.IActivityPickerProxyItem;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Provider helper class that provides the content for the Activity picker.
 * 
 * @author Miguel Torres
 * 
 */
public final class ActivityFilterPickerProviderHelper implements
        IActivityPickerProvider {

    // Singleton instance of this class
    private final static ActivityFilterPickerProviderHelper INSTANCE =
            new ActivityFilterPickerProviderHelper();

    /**
     * Private constructor. This class cannot be instantiated.
     */
    private ActivityFilterPickerProviderHelper() {

    }

    /**
     * Get singleton instance of this class.
     * 
     * @return <code>BOMPickerProviderUtil</code>
     */
    public static ActivityFilterPickerProviderHelper getInstance() {
        return INSTANCE;
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public IActivityPickerProxyItem[] getContent(ActivityType type,
            EObject scope) {
        Set<IActivityPickerProxyItem> items =
                new HashSet<IActivityPickerProxyItem>();

        switch (type) {
        case ACTIVITY:
            items = getAllActivities(scope);
            break;
        }

        return items.toArray(new IActivityPickerProxyItem[items.size()]);
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public IActivityPickerProxyItem getItem(URI uri, String name, EObject scope) {
        IActivityPickerProxyItem item = null;
        if (uri != null && name != null) {
            EObject eo = ProcessUIUtil.getEObjectFrom(uri, scope);
            if (eo != null) {
                if (eo instanceof Activity) {
                    IProject project = WorkingCopyUtil.getProjectFor(scope);
                    String projectName = ""; //$NON-NLS-1$
                    if (project != null) {
                        projectName = project.getName();
                    }
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(scope);
                    String path = ""; //$NON-NLS-1$
                    if (wc != null) {
                        path = ProcessUIUtil.createPath(wc);
                    }
                    item = getActivityIndexerItem((Activity) eo, path);
                }
            }
        }
        return item;
    }

    /**
     * Get all Activities from the model.
     * 
     * @param monitor
     *            progress monitor
     * @return set of <code>IActivityPickerProxyItem</code> objects. Empty set
     *         if none found.
     */
    private Set<IActivityPickerProxyItem> getAllActivities(EObject scope) {
        Set<IActivityPickerProxyItem> items =
                new HashSet<IActivityPickerProxyItem>();
        if (scope != null) {
            Process process = Xpdl2ModelUtil.getProcess(scope);
            if (process != null) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(scope);
                String path = ""; //$NON-NLS-1$
                if (wc != null) {
                    path = ProcessUIUtil.createPath(wc);
                }

                Collection activityList =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);
                items.addAll(getActivityIndexerItemList(activityList, path));

            }
        }
        return items;
    }

    public static Set<IActivityPickerProxyItem> getActivityIndexerItemList(
            List<EObject> elementList, EObject scope) {

        Set<IActivityPickerProxyItem> items =
                new HashSet<IActivityPickerProxyItem>();
        if (elementList != null && scope != null) {
            String path = ActivityFilterPickerProviderHelper.getPath(scope);
            if (path != null) {
                items =
                        ActivityFilterPickerProviderHelper
                                .getActivityIndexerItemList(elementList, path);
            }
        }
        return items;
    }

    private static Set<IActivityPickerProxyItem> getActivityIndexerItemList(
            Collection<EObject> activityList, String path) {

        Set<IActivityPickerProxyItem> items =
                new HashSet<IActivityPickerProxyItem>();
        if (activityList != null && !activityList.isEmpty()) {
            for (EObject eObject : activityList) {
                if (eObject instanceof Activity) {
                    items.add(getActivityIndexerItem((Activity) eObject, path));
                }
            }
        }
        return items;
    }

    private static IActivityPickerProxyItem getActivityIndexerItem(
            Activity activity, String path) {
        URI uri = ProcessUIUtil.getURI(activity, true);
        URI imageURI = ProcessUIUtil.getImageURI(activity);
        String name = ProcessUIUtil.getActivityQualifiedName(activity);
        IActivityPickerProxyItem item =
                ActivityFilterPickerProviderHelper.getInstance().new ActivityPickerItem(
                        name, uri, imageURI, activity);
        return item;
    }

    public static String getPath(EObject scope) {
        String path = ""; //$NON-NLS-1$
        if (scope != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(scope);
            if (wc != null) {
                path = ProcessUIUtil.createPath(wc);
            }
        }
        return path;
    }

    /**
     * Implementation of the <code>IActivityPickerProxyItem</code>
     * 
     * @author Miguel Torres
     * 
     */
    public class ActivityPickerItem implements IActivityPickerProxyItem {

        private String qualifiedName;

        private String name;

        private URI uri;

        private URI imageUri;

        private Activity activity;

        /**
         * Constructor.
         * 
         * @param qualifiedName
         *            qualified name.
         * @param uri
         *            URI of the item.
         * @param imageUri
         *            URI of the image.
         */
        public ActivityPickerItem(String qualifiedName, URI uri, URI imageUri,
                Activity activity) {

            this.qualifiedName = qualifiedName;
            this.uri = uri;
            this.imageUri = imageUri;
            this.activity = activity;
        }

        /*
         * (non-Javadoc)
         */
        @Override
        public Image getImage() {
            Image img = null;

            if (imageUri != null) {
                img = ExtendedImageRegistry.getInstance().getImage(imageUri);
            }

            return img;
        }

        /*
         * (non-Javadoc)
         */
        @Override
        public String getName() {
            return Xpdl2ModelUtil.getDisplayNameOrName(activity);
        }

        /*
         * (non-Javadoc)
         */
        @Override
        public String getQualifiedName() {
            return qualifiedName;
        }

        /*
         * (non-Javadoc)
         */
        @Override
        public URI getURI() {
            return uri;
        }

        /*
         * (non-Javadoc)
         */
        @Override
        public Object getItem() {
            return activity;
        }

        @Override
        public int hashCode() {
            int code = super.hashCode();

            if (uri != null && qualifiedName != null) {
                code = uri.hashCode() + qualifiedName.hashCode();
            }

            return code;
        }

        @Override
        public boolean equals(Object obj) {
            boolean isEquals = false;

            if (obj == this) {
                isEquals = true;
            } else if (obj instanceof IActivityPickerProxyItem) {
                IActivityPickerProxyItem other = (IActivityPickerProxyItem) obj;
                isEquals =
                        other.getQualifiedName().equals(getQualifiedName())
                                && other.getURI().equals(getURI());
            }

            return isEquals;
        }

    }

}
