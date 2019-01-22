/*
 */
package com.tibco.xpd.bom.resources.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.internal.Messages;

/**
 */
public class ProfileApplicationItemProvider extends AbstractItemProvider {

    private List<IItemPropertyDescriptor> itemPropertyDescriptors;

    /**
     */
    public ProfileApplicationItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     */
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            itemPropertyDescriptors = new ArrayList<IItemPropertyDescriptor>();
            itemPropertyDescriptors
                    .add(new LocationPropertyDescriptor(
                            getAdapterFactory(),
                            null,
                            Messages.ProfileApplicationItemProvider_attributeLocation_label,
                            Messages.ProfileApplicationItemProvider_attributeLocation_shortdesc,
                            GENERAL_CATEGORY));
        }
        return itemPropertyDescriptors;
    }

    /**
     */
    public Object getImage(Object object) {
        return Activator.getDefault().getImageRegistry().get(BOMImages.PROFILE);
    }

    /**
     */
    public String getText(Object object) {
        ProfileApplication profApp = getProfileApplication(object);
        String text =
                profApp == null ? null : profApp.getAppliedProfile().getName();
        return text == null ? "" : text; //$NON-NLS-1$
    }

    private ProfileApplication getProfileApplication(Object object) {
        return ((ProfileApplication) object);
    }

    public Collection<?> getChildren(Object object) {
        return Collections.EMPTY_LIST;
    }

    public Object getParent(Object object) {
        return null;
    }

    @Override
    public Object getEditableValue(Object object) {
        if (object instanceof ProfileApplication) {
            return ((ProfileApplication) object).getAppliedProfile();
        }
        return super.getEditableValue(object);
    }

    private class LocationPropertyDescriptor extends ItemPropertyDescriptor {

        private LabelProvider labelProvider;

        public LocationPropertyDescriptor(AdapterFactory adapterFactory,
                ResourceLocator resourceLocator, String displayName,
                String description, String category) {
            super(adapterFactory, displayName, description,
                    (EStructuralFeature) null, false, null, category);
        }

        @Override
        public Object getPropertyValue(Object object) {
            if (object instanceof ProfileApplication) {
                return ((ProfileApplication) object).getAppliedProfile();
            }
            return super.getPropertyValue(object);
        }

        @Override
        public IItemLabelProvider getLabelProvider(Object object) {
            if (labelProvider == null) {
                labelProvider = new LabelProvider();
            }
            return labelProvider;
        }
    }

    /**
     * The profile location property descriptor's label provider.
     */
    private class LabelProvider implements IItemLabelProvider {

        @Override
        public Object getImage(Object object) {
            IPath path = getPath(object);
            IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
            if (file.exists()) {
                WorkbenchLabelProvider labelProvider =
                        new WorkbenchLabelProvider();
                try {
                    return labelProvider.getImage(file);
                } finally {
                    labelProvider.dispose();
                }
            }
            return null;
        }

        @Override
        public String getText(Object object) {
            IPath path = getPath(object);
            return path != null ? path.makeRelative().toString() : ""; //$NON-NLS-1$
        }

        /**
         * Get the file path of the given Profile.
         * 
         * @param object
         * @return
         */
        private IPath getPath(Object object) {
            if (object instanceof Profile) {
                Resource res = ((Profile) object).eResource();
                if (res != null) {
                    URI uri = res.getURI();
                    if (uri != null && uri.isPlatformResource()) {
                        String location = uri.toPlatformString(true);
                        if (location != null && location.length() > 0) {
                            return new Path(location);
                        }
                    }
                }
            }
            return null;
        }

    }

}