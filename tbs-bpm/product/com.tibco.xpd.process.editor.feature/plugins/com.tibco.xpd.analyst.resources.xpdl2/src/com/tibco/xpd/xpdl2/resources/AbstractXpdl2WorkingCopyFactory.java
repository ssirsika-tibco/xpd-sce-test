/**
 * Copyright 2006 TIBCO Software Inc.
 */

package com.tibco.xpd.xpdl2.resources;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.xpdExtension.provider.XpdExtensionItemProviderAdapterFactory;
import com.tibco.xpd.xpdl2.provider.Xpdl2ItemProviderAdapterFactory;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl.Xpdl2FileType;

/**
 * Abstract working copy factory for XPDL format files with varying file
 * extensions (provided by sub-class)
 * 
 * @author Wojciech Zurek
 * 
 */
public abstract class AbstractXpdl2WorkingCopyFactory implements
        WorkingCopyFactory {

    /**
     * extension of the xpdl file.
     */
    public abstract String getFileExtension();

    public abstract Xpdl2FileType getXpdl2FileType();

    public abstract String getSpecialFolderKind();

    private static ComposedAdapterFactory ipFactory;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.WorkingCopyFactory#getWorkingCopy(org.eclipse
     * .core.resources.IResource)
     */
    public WorkingCopy getWorkingCopy(IResource resource) {
        Xpdl2WorkingCopyImpl wc =
                new Xpdl2WorkingCopyImpl(resource, getXpdl2FileType());
        return wc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.WorkingCopyFactory#isFactoryFor(org.eclipse.core
     * .resources.IResource)
     */
    public boolean isFactoryFor(IResource resource) {
        ProjectConfig pc =
                XpdResourcesPlugin.getDefault().getProjectConfig(resource
                        .getProject());
        if (pc != null) {
            SpecialFolders sfs = pc.getSpecialFolders();
            SpecialFolder sf = sfs.getFolderContainer(resource);
            if (sf != null && sf.getKind() != null
                    && sf.getKind().equals(getSpecialFolderKind())) {
                return resource.getFileExtension().equals(getFileExtension());
            }
        }
        return false;
    }

    public static synchronized AdapterFactory getAdapterFactory() {
        if (ipFactory == null) {
            ipFactory =
                    new ComposedAdapterFactory(
                            ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
            ipFactory.addAdapterFactory(new Xpdl2ItemProviderAdapterFactory());
            ipFactory
                    .addAdapterFactory(new XpdExtensionItemProviderAdapterFactory());
        }
        return ipFactory;
    }

}
