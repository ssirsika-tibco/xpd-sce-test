/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.fragments;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardContentsHelper;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardManager;
import org.eclipse.gmf.runtime.common.ui.util.CustomData;
import org.eclipse.gmf.runtime.common.ui.util.CustomDataTransfer;
import org.eclipse.gmf.runtime.common.ui.util.ICustomData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.graphics.ImageData;

import com.tibco.xpd.bom.fragments.internal.BOMFragmentCategory;
import com.tibco.xpd.bom.fragments.internal.BOMFragmentLocalizer;
import com.tibco.xpd.bom.fragments.internal.Messages;
import com.tibco.xpd.bom.fragments.internal.BOMFragmentCategory.BOMFragment;
import com.tibco.xpd.bom.resources.ui.clipboard.BOMCopyPasteCommandFactory;
import com.tibco.xpd.fragments.FragmentsContributor;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;

/**
 * Business Object Model's fragment contributor.
 * 
 * @author njpatel
 * 
 */
public class BOMFragmentsContributor extends FragmentsContributor {

    /**
     * Business Object Model's fragment contributor.
     */
    public BOMFragmentsContributor() {

    }

    @Override
    public String getFragmentVersion() {
        // Version 1: No samples
        // Version 2: Cross Industry added with customer sample
        return "2"; //$NON-NLS-1$
    }

    @Override
    public void initialize(IProgressMonitor monitor) throws CoreException {

        // Get the BOM fragment categories extensions defined in the plugin.xml
        List<BOMFragmentCategory> categories = BOMFragmentCategory
                .getCategories();

        monitor
                .beginTask(
                        Messages.BOMFragmentsContributor_creatingSampleFragments_monitor_shortdesc,
                        categories.size());

        for (BOMFragmentCategory category : categories) {
            initialize(category, null, new SubProgressMonitor(monitor, 1));
        }

        // Dispose all categories so any loaded resources can be unloaded
        for (BOMFragmentCategory category : categories) {
            category.dispose();
        }
    }

    /**
     * Initialize the given BOM fragment category. Create a fragment category
     * and fragments based on the content of the BOM models.
     * 
     * @param bomCategory
     *            the (system) BOM category defined in this plug-in
     * @param category
     *            the fragment category
     * @param monitor
     * @throws CoreException
     */
    private void initialize(BOMFragmentCategory bomCategory,
            IFragmentCategory category, IProgressMonitor monitor)
            throws CoreException {
        if (bomCategory != null) {
            monitor
                    .beginTask(
                            String
                                    .format(
                                            Messages.BOMFragmentsContributor_creatingSampleCategory_monitor_shortdesc,
                                            bomCategory.getName()),
                            IProgressMonitor.UNKNOWN);

            category = createCategory(category, bomCategory.getId(),
                    bomCategory.getName(), bomCategory.getDescription(),
                    monitor);

            List<BOMFragment> fragments = bomCategory.getFragments();

            // Add all sub-categories
            for (BOMFragmentCategory cat : bomCategory.getSubCategories()) {
                initialize(cat, category, monitor);
            }

            // Add fragments
            for (BOMFragment fragment : fragments) {
                createFragment(category, fragment.getKey(), fragment.getName(),
                        fragment.getDescription(), fragment.getData(), fragment
                                .getImageData(), monitor);
            }
        }
    }

    @Override
    public void copyToClipboard(IFragment fragment) {
        if (fragment != null) {
            String data = fragment.getLocalizedData();
            ImageData imgData = fragment.getLocalizedImageData();
            ClipboardManager clipboardManager = ClipboardManager.getInstance();

            if (data != null) {
                CustomData customData = new CustomData(
                        BOMCopyPasteCommandFactory.DRAWING_SURFACE, data
                                .getBytes());

                // Add image to clipboard
                if (imgData != null) {
                    clipboardManager.addToCache(imgData, ImageTransfer
                            .getInstance());
                }

                // Add the fragment data to clipboard
                clipboardManager.addToCache(new ICustomData[] { customData },
                        CustomDataTransfer.getInstance());
            }

            // Add fragment to local transfer so that, on paste in the fragments
            // view, the name can be determined correctly
            FragmentLocalSelectionTransfer.getTransfer().setSelection(
                    new StructuredSelection(fragment));
            clipboardManager.addToCache(fragment,
                    FragmentLocalSelectionTransfer.getTransfer());

            clipboardManager.flushCacheToClipboard();
        }
    }

    @Override
    public ClipboardFragmentData getFromClipboard(IFragmentCategory cat) {
        ClipboardFragmentData ret = null;
        ClipboardManager manager = ClipboardManager.getInstance();
        ImageData imgData = null;

        Object localTransferData = manager.getClipboardContents(
                FragmentLocalSelectionTransfer.getTransfer(),
                ClipboardContentsHelper.getInstance());

        if (localTransferData instanceof IStructuredSelection) {
            Object element = ((IStructuredSelection) localTransferData)
                    .getFirstElement();

            if (element instanceof IFragment) {
                ret = new ClipboardFragmentData((IFragment) element);
            }
        }

        // If there was no local transfer then try image and custom data
        // transfer
        if (ret == null) {
            Object data = manager.getClipboardContents(ImageTransfer
                    .getInstance(), ClipboardContentsHelper.getInstance());

            if (data instanceof ImageData) {
                imgData = (ImageData) data;
            }

            if (imgData != null) {
                String fragmentData = null;
                data = manager.getClipboardContents(CustomDataTransfer
                        .getInstance(), ClipboardContentsHelper.getInstance());

                if (data instanceof ICustomData[]) {
                    ICustomData[] customData = (ICustomData[]) data;
                    if (customData[0] != null
                            && customData[0].getFormatType().equals(
                                    BOMCopyPasteCommandFactory.DRAWING_SURFACE)) {
                        byte[] bytes = customData[0].getData();

                        if (bytes != null) {
                            fragmentData = new String(bytes);
                        }
                    }
                }
                if (fragmentData != null) {
                    ret = new ClipboardFragmentData(fragmentData, imgData);
                }
            }
        }

        return ret;
    }

    @Override
    public String getLocalizedData(IFragment fragment, String NL) {
        BOMFragmentLocalizer localizer = new BOMFragmentLocalizer(fragment, NL);

        if (localizer.canLocalize()) {
            return localizer.getLocalizedData();
        }

        return null;
    }

    @Override
    public ImageData getLocalizedImageData(IFragment fragment, String NL) {
        BOMFragmentLocalizer localizer = new BOMFragmentLocalizer(fragment, NL);
        if (localizer.canLocalize()) {
            return localizer.getLocalizedImage();
        }
        return null;
    }

    @Override
    public String getLocalizedDescription(IFragmentElement element) {
        String desc = null;

        if (element != null && element.getKey() != null) {
            if (element instanceof IFragmentCategory) {
                BOMFragmentCategory cat = BOMFragmentCategory
                        .getCategory(element.getKey());
                if (cat != null) {
                    desc = cat.getDescription();
                    cat.dispose();
                }
            } else {
                // Fragment element - get localized string from the properties
                // file of the fragment BOM model.
                String NL = Platform.getNL();

                while (desc == null && NL != null) {
                    BOMFragmentLocalizer localizer = new BOMFragmentLocalizer(
                            (IFragment) element, Platform.getNL());
                    if (localizer.canLocalize()) {
                        desc = localizer.getLocalizedDescription();
                    }

                    if (NL.indexOf('_') > 0) {
                        NL = NL.substring(NL.lastIndexOf('_'));
                    } else {
                        NL = null;
                    }
                }
            }
        }

        return desc;
    }

    @Override
    public String getLocalizedName(IFragmentElement element) {
        String name = null;

        if (element != null && element.getKey() != null) {
            if (element instanceof IFragmentCategory) {
                // name = getCategoryLabel(relativePath);
                BOMFragmentCategory cat = BOMFragmentCategory
                        .getCategory(element.getKey());
                if (cat != null) {
                    name = cat.getName();
                    cat.dispose();
                }
            } else {
                // Fragment element - get localized string from the properties
                // file of the fragment BOM model.
                String NL = Platform.getNL();

                while (name == null && NL != null) {
                    BOMFragmentLocalizer localizer = new BOMFragmentLocalizer(
                            (IFragment) element, Platform.getNL());
                    if (localizer.canLocalize()) {
                        name = localizer.getLocalizedName();
                    }

                    if (NL.indexOf('_') > 0) {
                        NL = NL.substring(NL.lastIndexOf('_'));
                    } else {
                        NL = null;
                    }
                }
            }
        }

        return name;
    }

    @Override
    public void updateContent(final IFragmentCategory rootCategory,
            String fragmentVersion, IProgressMonitor monitor)
            throws CoreException {
        /*
         * Remove all system (sample) categories and re-create them
         */
        monitor
                .beginTask(
                        Messages.BOMFragmentsContributor_updateFragments_monitor_shortdesc,
                        2);
        try {
            if (rootCategory != null) {
                Collection<IFragmentElement> children = rootCategory
                        .getChildren();
                SubProgressMonitor subMonitor = new SubProgressMonitor(monitor,
                        1);
                subMonitor
                        .beginTask(
                                Messages.BOMFragmentsContributor_deleteExisingCategories_monitor_shortdesc,
                                IProgressMonitor.UNKNOWN);
                for (IFragmentElement child : children) {
                    if (child.isSystem()) {
                        deleteFragmentElement(child, subMonitor);
                    }
                }

                monitor.worked(1);

                // Re-create sample categories
                initialize(new SubProgressMonitor(monitor, 1));
                monitor.worked(1);
            }
        } finally {
            monitor.done();
        }

    }

    @Override
    public UpdateResult updateContent(IFragment fragment, String fragmentVersion) {
        return null;
    }

}
