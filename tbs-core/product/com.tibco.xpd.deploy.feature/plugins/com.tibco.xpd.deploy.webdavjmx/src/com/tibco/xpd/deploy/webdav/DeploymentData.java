/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav;

import java.io.IOException;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.resources.util.EncryptionUtil;

/**
 * @author glewis
 *
 */
public class DeploymentData {

    /** The URL to WebDAV site root. */
    public static final String SITE_URL = "siteUrl"; //$NON-NLS-1$

    /** Server user name. */
    public static final String USERNAME = "username"; //$NON-NLS-1$

    /** Server password. */
    public static final String PASSWORD = "password"; //$NON-NLS-1$
    
    private static final String ROOT_FOLDER = "rootFolder"; //$NON-NLS-1$
    
    private final String configSiteURLKey;
    private final String configRootFolderKey;
    private final String configUsernameKey;
    private final String configPasswordKey;

    private final ServerConfig mServerConfig;

    protected DeploymentData(ServerConfig config) throws IOException {
        super();
        mServerConfig = config;
        this.configSiteURLKey = null;
        this.configRootFolderKey = null;
        this.configUsernameKey = null;
        this.configPasswordKey = null;
    }
    
    protected DeploymentData(ServerConfig config, String configSiteURLKey, String configRootFolderKey, String configUsernameKey, String configPasswordKey ) throws IOException {
        super();
        mServerConfig = config;
        this.configSiteURLKey = configSiteURLKey;
        this.configRootFolderKey = configRootFolderKey;
        this.configUsernameKey = configUsernameKey;
        this.configPasswordKey = configPasswordKey;
    }

    /**
     * Get the WebDav decrypted password
     * @return WebDav password
     */
    public String getPassword() {
        String password = null;
        String key = PASSWORD;
        if (configPasswordKey != null){
            key = configPasswordKey;
        }        
        if (mServerConfig != null) {
            ConfigParameter param = mServerConfig.getConfigParameter(key);
            if (param != null && param.getValue() != null){
                password = param.getValue().toString();
                password = EncryptionUtil.decrypt(password);
            }
        }
        return password;
    }

    /**
     * Get the WedDav Site URL
     * @return WebDav Site URL Setting
     */
    public String getSiteUrl() {
        String siteURL = ""; //$NON-NLS-1$
        String key = SITE_URL;
        if (configSiteURLKey != null){
            key = configSiteURLKey;
        }        
        if (mServerConfig != null) {
            ConfigParameter param = mServerConfig.getConfigParameter(key);
            if (param != null && param.getValue() != null){
                siteURL = param.getValue().toString();
            }
        }
        return siteURL;
    }

    /**
     * Get the WebDav username
     * @return WebDav Username
     */
    public String getUsername() {
        String userName = ""; //$NON-NLS-1$
        String key = USERNAME;
        if (configUsernameKey != null){
            key = configUsernameKey;
        }  
        if (mServerConfig != null) {
            ConfigParameter param = mServerConfig.getConfigParameter(key);
            if (param != null &&  param.getValue() != null){
                userName = param.getValue().toString();
            }
        }
        return userName;
    }

    /**
     * Get the WebDav Documet Root Folder
     * @return WebDav Root Folder
     */
    //@Override
    public String getWebDavRootFolder() {
        String folder = ""; //$NON-NLS-1$
        String key = ROOT_FOLDER;
        if (configRootFolderKey != null){
            key = configRootFolderKey;
        }  
        if (mServerConfig != null) {
            ConfigParameter param = mServerConfig.getConfigParameter(key);
            if (param != null && param.getValue() != null){
                folder = param.getValue().toString();
                if (!folder.trim().startsWith(DeploymentConstants.PATH_SEPARATOR)) {
                    folder = DeploymentConstants.PATH_SEPARATOR + folder;
                }
                /*
                 * if( !folder.trim().endsWith(DeploymentConstants.PATH_SEPARATOR)) { folder = folder +
                 * DeploymentConstants.PATH_SEPARATOR; }
                 */
            }
        }
        return folder;
    }

}
