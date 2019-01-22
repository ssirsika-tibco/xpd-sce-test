package com.tibco.xpd.n2.resources;

import java.util.List;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.deploy.ui.api.DefaultServerProvider;
import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.resources.util.EncryptionUtil;

/**
 * 
 * This class contributes a default development server 'Local Development
 * Server'
 * 
 * @author agondal
 * @since 1 Sep 2014
 */
public class N2LocalDevelopmentServer extends DefaultServerProvider {

    private static final String ADMIN_SERVER_TYPE_ID =
            "com.tibco.amf.tools.admincligen.adminserver"; //$NON-NLS-1$

    private static final String WORKSPACE_REPO_TYPE_ID =
            "com.tibco.xpd.deploy.ui.WorkspaceRpository"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.deploy.DefaultServer#getServerName()
     * 
     * @return
     */
    @Override
    public String getServerName() {

        return Messages.N2LocalDevelopmentServer_Name;
    }

    /**
     * @see com.tibco.xpd.deploy.DefaultServer#getServerTypeId()
     * 
     * @return
     */
    @Override
    public String getServerTypeId() {
        return ADMIN_SERVER_TYPE_ID;
    }

    /**
     * @see com.tibco.xpd.deploy.DefaultServer#getServerRepoTypeId()
     * 
     * @return
     */
    @Override
    public String getServerRepoTypeId() {
        return WORKSPACE_REPO_TYPE_ID;
    }

    /**
     * @see com.tibco.xpd.deploy.DefaultServer#getServerConfig()
     * 
     * @return
     */
    @Override
    public ServerConfig getServerConfig() {
        DeployFactory factory = DeployFactory.eINSTANCE;

        ServerConfig serverConfig = factory.createServerConfig();
        List<ConfigParameter> configParams = serverConfig.getConfigParameters();
        ConfigParameter configParamUrl = factory.createConfigParameter();
        configParamUrl.setKey("serverUrl"); //$NON-NLS-1$
        configParamUrl.setValue("http://localhost:8120"); //$NON-NLS-1$
        configParams.add(configParamUrl);

        ConfigParameter configParamUser = factory.createConfigParameter();
        configParamUser.setKey("userName"); //$NON-NLS-1$
        configParamUser.setValue("root"); //$NON-NLS-1$
        configParams.add(configParamUser);

        ConfigParameter configParamPassw = factory.createConfigParameter();
        configParamPassw.setKey("userPassword"); //$NON-NLS-1$
        configParamPassw.setValue(EncryptionUtil.encrypt("t")); //$NON-NLS-1$
        configParams.add(configParamPassw);

        ConfigParameter configParamAuthType = factory.createConfigParameter();
        configParamAuthType.setKey("httpAuthType"); //$NON-NLS-1$
        configParamAuthType.setValue(Boolean.FALSE.toString());
        configParams.add(configParamAuthType);

        ConfigParameter configParamFilterUserApps =
                factory.createConfigParameter();
        configParamFilterUserApps.setKey("filterUserApps"); //$NON-NLS-1$
        configParamFilterUserApps.setValue(Boolean.TRUE.toString());
        configParams.add(configParamFilterUserApps);

        ConfigParameter configParamEnvName = factory.createConfigParameter();
        configParamEnvName.setKey("environmentName"); //$NON-NLS-1$
        configParamEnvName.setValue("BPMEnvironment"); //$NON-NLS-1$
        configParams.add(configParamEnvName);

        ConfigParameter configParamFilterEnv = factory.createConfigParameter();
        configParamFilterEnv.setKey("filterEnvironments"); //$NON-NLS-1$
        configParamFilterEnv.setValue(Boolean.TRUE.toString());
        configParams.add(configParamFilterEnv);

        ConfigParameter configParamDefAppFolderName =
                factory.createConfigParameter();
        configParamDefAppFolderName.setKey("defaultAppFolderName"); //$NON-NLS-1$
        configParamDefAppFolderName.setValue("/amx.bpm.app/"); //$NON-NLS-1$
        configParams.add(configParamDefAppFolderName);

        ConfigParameter configParamAppName = factory.createConfigParameter();
        configParamAppName.setKey("applicationName"); //$NON-NLS-1$
        configParamAppName.setValue("amx.bpm.app"); //$NON-NLS-1$
        configParams.add(configParamAppName);

        return serverConfig;
    }
}
