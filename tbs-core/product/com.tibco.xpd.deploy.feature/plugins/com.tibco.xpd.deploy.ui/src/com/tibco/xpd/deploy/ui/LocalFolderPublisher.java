/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.model.extension.RepositoryPublisher;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Publishes module to local folder.
 * <p>
 * <i>Created: 3 September 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class LocalFolderPublisher implements RepositoryPublisher {

    private static final String URL_PATH_SEPARATOR = "/"; //$NON-NLS-1$

    private static final String PUBLISHING_FOLDER_PARAM = "publishingFolder"; //$NON-NLS-1$

    private static final String INQUIRY_URL_KEY_PARAM = "inquiryUrl"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.model.extension.RepositoryPublisher#publish(com.tibco.xpd.deploy.RepositoryConfig,
     *      java.io.File)
     */
    public void publish(RepositoryConfig config, File file) {
        String moduleName = file.getName();
        File outFolder = new File(config.getConfigParameter(
                PUBLISHING_FOLDER_PARAM).getValue());
        if (!outFolder.exists()) {
            if (!outFolder.mkdirs()) {
                String message = String
                        .format(
                                Messages.LocalFolderPublisher_CantCreateOutput_message,
                                outFolder.toString());
                throw new RuntimeException(message);
            }
        }
        File outFile = new File(outFolder, moduleName);
        try {
            copy(file, outFile);
        } catch (IOException e) {
            String message = Messages.LocalFolderPublisher_PublishingException_message;
            throw new RuntimeException(message);

        }
    }

    /*
     * (non-Javadoc) Copies src file to dst file. If the dst file does not
     * exist, it is created
     */
    private void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.model.extension.RepositoryPublisher#getInquiryUrl(com.tibco.xpd.deploy.RepositoryConfig,
     *      java.io.File)
     */
    public URL getInquiryUrl(RepositoryConfig config, File file) {
        // Determine module repository inquiry URL.
        URL repoFileUrl;
        String moduleFileName = file.getName();
        try {
            String repoBaseUrlStr = config.getConfigParameter(
                    INQUIRY_URL_KEY_PARAM).getValue().trim();
            if (!repoBaseUrlStr.endsWith(URL_PATH_SEPARATOR)) {
                repoBaseUrlStr += URL_PATH_SEPARATOR;
            }
            URL repoBaseUrl = new URL(repoBaseUrlStr);
            repoFileUrl = new URL(repoBaseUrl, moduleFileName);
        } catch (MalformedURLException e) {
            String message = Messages.LocalFolderPublisher_CannotCreateInguiryUrl_message;
            throw new RuntimeException(message);

        }
        return repoFileUrl;
    }

}
