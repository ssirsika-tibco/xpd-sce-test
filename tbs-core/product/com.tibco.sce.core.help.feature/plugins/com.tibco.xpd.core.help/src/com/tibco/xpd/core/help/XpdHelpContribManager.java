/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.core.help;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Version;

/**
 * Manages 'xpdHelpContent' contributions.
 * 
 * @author jarciuch
 * @since 30 Jul 2014
 */
public class XpdHelpContribManager {

    private static final ILog LOG = CoreHelpActivator.getDefault().getLog();

    private static final String PRODUCT_ID_ATTR = "productId"; //$NON-NLS-1$

    private static final String PRODUCT_NAME_ATTR = "productName"; //$NON-NLS-1$

    private static final String VERSION_ATTR = "version"; //$NON-NLS-1$

    private static final String PRODUCT_HELP_URL_ATTR = "productHelpUrl"; //$NON-NLS-1$

    private static final String WEB_HELP_URL_ATTR = "webHelpUrl"; //$NON-NLS-1$

    private static final String DOWNLOAD_URL_ATTR = "downloadUrl"; //$NON-NLS-1$

    private static final String OFFLINE_FOLDER_NAME = "offlineFolderName"; //$NON-NLS-1$

    private static final String OFFLINE_INDEX_PATH = "offlineIndexPath"; //$NON-NLS-1$

    private static final String EXTENSION_NAME = "xpdHelpContent"; //$NON-NLS-1$

    private static final String PRODUCT_HELP_CONTENT_ELEMENT =
            "productHelpContent"; //$NON-NLS-1$

    /** Singleton instance. */
    private static final XpdHelpContribManager INSTANCE =
            new XpdHelpContribManager();

    private List<ProductHelpContent> productHelpContents;

    /**
     * Private constructor to prevent instantiation.
     */
    private XpdHelpContribManager() {
    }

    public static XpdHelpContribManager getInstance() {
        return INSTANCE;
    }

    /**
     * @return list of available product help contents (contributed by the
     *         extensions).
     */
    public List<ProductHelpContent> getProductHelpContents() {
        init();
        return productHelpContents;
    }

    /**
     * Gets the ProductHelpContent for a specific productId and version. If many
     * versions exists for the provided productId (the and the version is
     * 'null') then the content with the highest version will be prefered.
     * 
     * @param productId
     *            product help content id as defined in an extension.
     * @param version
     *            the version as defined in extension or 'null' if the highest
     *            available version should be used (it is assumed that versions
     *            follow the OSGI version schema).
     * @return the product help content for a given productId and version.
     */
    public ProductHelpContent getProductHelpContent(String productId,
            String version) {
        List<ProductHelpContent> cs = new ArrayList<>();
        for (ProductHelpContent p : productHelpContents) {
            if (productId.equals(p.getProductId())) {
                if (version != null && version.equals(p.getVersion())) {
                    return p;
                }
                cs.add(p);
            }
        }
        if (!cs.isEmpty()) {
            // get a higher version.
            Collections.sort(cs, new Comparator<ProductHelpContent>() {
                @Override
                public int compare(ProductHelpContent o1, ProductHelpContent o2) {
                    try {
                        Version o1Version =
                                Version.parseVersion(o1.getVersion());
                        Version o2Version =
                                Version.parseVersion(o2.getVersion());
                        return o1Version.compareTo(o2Version);
                    } catch (Exception e) {
                        // string compare.
                        if (o1.getVersion() != null) {
                            return o1.getVersion().compareTo(o2.getVersion());
                        } else {
                            return 0;
                        }
                    }
                }
            });
            return cs.get(0);
        }
        return null;
    }

    /**
     * Initalizes contribution manager from extensions.
     */
    private void init() {
        synchronized (INSTANCE) {
            if (productHelpContents == null) {
                productHelpContents = createProductHelpContents();
            }
        }
    }

    /**
     * Creates product help contents from "xpdHelpContent" extensions.
     * 
     * @return the ordered list of contributors.
     */
    private List<ProductHelpContent> createProductHelpContents() {
        LinkedHashMap<String, ProductHelpContent> contributions =
                new LinkedHashMap<String, ProductHelpContent>();
        IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
        IConfigurationElement[] configurationElements =
                extensionRegistry
                        .getConfigurationElementsFor(CoreHelpActivator.PLUGIN_ID,
                                EXTENSION_NAME);
        for (IConfigurationElement element : configurationElements) {
            if (PRODUCT_HELP_CONTENT_ELEMENT.equals(element.getName())) {
                String productId = element.getAttribute(PRODUCT_ID_ATTR);
                /* check for duplicates */
                if (!contributions.keySet().contains(productId)) {
                    String productName =
                            element.getAttribute(PRODUCT_NAME_ATTR);
                    String version = element.getAttribute(VERSION_ATTR);
                    String productHelpUrl =
                            element.getAttribute(PRODUCT_HELP_URL_ATTR);
                    String webHelpUrl = element.getAttribute(WEB_HELP_URL_ATTR);
                    String downloadUrl =
                            element.getAttribute(DOWNLOAD_URL_ATTR);
                    String offlineFolderName =
                            element.getAttribute(OFFLINE_FOLDER_NAME);
                    String offlineIndexPath =
                            element.getAttribute(OFFLINE_INDEX_PATH);
                    if (offlineIndexPath != null
                            && offlineIndexPath.trim().isEmpty()) {
                        offlineIndexPath = null;
                    }
                    contributions.put(productId, new ProductHelpContent(
                            productId, productName, version, productHelpUrl,
                            webHelpUrl, downloadUrl, offlineFolderName,
                            offlineIndexPath));
                } else {
                    LOG.log(new Status(
                            IStatus.ERROR,
                            LOG.getBundle().getSymbolicName(),
                            String.format("Duplicate product id for productHelpContent contribution '%1$s'!.", //$NON-NLS-1$
                                    productId)));
                }

            }
        }
        return Collections.unmodifiableList(new ArrayList<ProductHelpContent>(
                contributions.values()));
    }

}
