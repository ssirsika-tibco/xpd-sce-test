/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.pkgtemplates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.ProviderBinding;
import com.tibco.xpd.processeditor.xpdl2.packageeditor.PackageEditor;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class PackageTemplateLoader {

    private static final String PACKAGE_TEMPLATE_ID =
            "com.tibco.xpd.pkgtemplates"; //$NON-NLS-1$

    private static final Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    private static PackageTemplateLoader INSTANCE;

    private List<PackageTemplate> pkgTemplates =
            new ArrayList<PackageTemplate>();

    public static PackageTemplateLoader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PackageTemplateLoader();
            INSTANCE.loadPackageTemplates();
        }
        return INSTANCE;
    }

    /**
     * 
     */
    private void loadPackageTemplates() {
        List<IFragmentCategory> rootCategories = Collections.EMPTY_LIST;
        rootCategories = getProcessEditorRelevantRootCategories();

        if (!(rootCategories.isEmpty())) {
            for (IFragmentCategory rootCategory : rootCategories) {
                Collection<IFragmentElement> children =
                        rootCategory.getChildren();
                for (IFragmentElement fragElement : children) {
                    if (fragElement instanceof IFragment) {
                        Package fragmentPackage =
                                Xpdl2ProcessorUtil
                                        .getFragmentPackage(((IFragment) fragElement));

                        // Only load pageflows if WM feature is installed.
                        boolean ignore = false;
                        if (!Xpdl2ResourcesPlugin.isWmFeatureAvailable()) {
                            for (Process process : fragmentPackage
                                    .getProcesses()) {
                                if (Xpdl2ModelUtil.isPageflow(process)) {
                                    ignore = true;
                                    break;
                                }
                            }
                        }

                        if (!ignore) {
                            pkgTemplates.add(new PackageTemplate(
                                    fragmentPackage));
                        }

                    }
                }
            }

        }
    }

    public List<PackageTemplate> getPackageTemplates() {
        return pkgTemplates;
    }

    /**
     * @return
     */
    private List<IFragmentCategory> getProcessEditorRelevantRootCategories() {
        List<IFragmentCategory> procRootCategories =
                new ArrayList<IFragmentCategory>();

        Collection<ProviderBinding> providerBindings =
                FragmentsActivator.getDefault()
                        .getProviderBindings(PackageEditor.PACKAGE_EDITOR_ID);

        for (ProviderBinding providerBinding : providerBindings) {
            IFragmentCategory rootCategory = null;
            try {
                rootCategory =
                        FragmentsActivator.getDefault()
                                .getRootCategory(providerBinding
                                        .getProviderId());
                procRootCategories.add(rootCategory);
            } catch (CoreException e) {
                LOG.error(e);
            }

        }
        return procRootCategories;
    }
}
