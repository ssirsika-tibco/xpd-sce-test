/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.core.help;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represent help content for a product.
 * 
 * @author jarciuch
 * @since 30 Jul 2014
 */
public class ProductHelpContent {

    /**
     * If provided value of this property will replace protocol host and port
     * part of documentaiotn URLs.
     * <p>
     * For example: if property
     * com.tibco.docs.host.replacement=https://a.b.c:8080 and the help URL is:
     * http://d.e.f/x/y/z then effective URL will be: https://a.b.c/x.y.z
     * </p>
     */
    private static final String DOCHOST_REPLACEMENT =
            "com.tibco.docs.host.replacement"; //$NON-NLS-1$

    private static final Pattern HOST_REPLACE_PATTERN = Pattern
            .compile("^(https?:\\/\\/.*?)(\\/.*)?$"); //$NON-NLS-1$

    private String productId;

    private String productName;

    private String version;

    private String productHelpUrl;

    private String webHelpUrl;

    private String downloadUrl;

    private String offlineFolderName;

    private String offlineIndexPath;

    /**
     * 
     */
    public ProductHelpContent(String productId, String productName,
            String version, String productHelpUrl, String webHelpUrl,
            String downloadUrl, String offlineFolderName,
            String offlineIndexPath) {
        this.productId = productId;
        this.productName = productName;
        this.version = version;
        this.webHelpUrl = replaceHost(webHelpUrl);
        this.productHelpUrl = replaceHost(productHelpUrl);
        this.downloadUrl = replaceHost(downloadUrl);
        this.offlineFolderName = offlineFolderName;
        this.offlineIndexPath = offlineIndexPath;
    }

    private static String replaceHost(String url) {
        String dochost = System.getProperty(DOCHOST_REPLACEMENT);
        if (dochost != null && url != null) {
            Matcher matcher = HOST_REPLACE_PATTERN.matcher(url);
            if (matcher.matches()) {
                if (matcher.groupCount() < 2) {
                    return dochost;
                }
                return dochost + matcher.group(2);
            }
        }
        return url;
    }

    /**
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @return the productRelativeUrl
     */
    public String getProductHelpUrl() {
        return productHelpUrl;
    }

    /**
     * @return the webHelpUrl
     */
    public String getWebHelpUrl() {
        return webHelpUrl;
    }

    /**
     * @return the the name of the default folded
     */
    public String getOfflineFolderName() {
        return offlineFolderName;
    }

    /**
     * @return the offline folder relative path to the starting offline index
     *         (starting) html page.
     */
    public String getOfflineIndexPath() {
        return offlineIndexPath;
    }

    public String getProductHelpContentUrl() {
        return getWebHelpUrl();
    }

    /**
     * @return the url used to download the zipped content.
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * @return returns 'true' if there is enough information in the extension to
     *         download the offline content.
     */
    public boolean isDownloadEnabled() {
        return getDownloadUrl() != null && !getDownloadUrl().trim().isEmpty()
                && getOfflineFolderName() != null
                && !getOfflineFolderName().trim().isEmpty();
    }

    /**
     * @return 'true' if product help page URL is available.
     */
    public boolean isProductHelpPageAvailable() {
        return productHelpUrl != null && !productHelpUrl.trim().isEmpty();
    }
}
