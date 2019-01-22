package com.tibco.xpd.deploy.ui.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.wizards.RepositoryConfigWizardPage;
import com.tibco.xpd.deploy.ui.wizards.RepositoryConfigWizardPageExt;

/**
 * Generic preference page for showing the appropriate repository page 
 * selected for a particular server.
 *  
 * @author glewis
 *
 */
public class RepositoryInfoPropertyPage extends PropertyPage implements
		IWorkbenchPropertyPage {

    private static final String REPOSITORY_WIZARD_PAGE = "repositoryWizardPage"; //$NON-NLS-1$

    private static final String REPOSITORY_TYPE_ID = "repositoryTypeId"; //$NON-NLS-1$

    private static final String REPOSITORY_CONFIG_WIZARD_PAGE_EXTENSION = "repositoryConfigWizardPage"; //$NON-NLS-1$
		
	private RepositoryConfigWizardPage wizardPage = null;	
    
    /**
	 * Constructor for WorkspaceServerInfoPropertyPage.
	 */
	public RepositoryInfoPropertyPage() {
		super();
	}

	@Override
	protected Control createContents(Composite parent) {
		Server selectedServer = (Server) (getElement())
				.getAdapter(Server.class);
		
		// get repository type and its id so we can later search for its appropriate repository page to use
		RepositoryType repositoryType = selectedServer.getRepository().getRepositoryType();
		String repoTypeId = repositoryType.getId();
		
		// set the config parameter info so we can use it for generating the label and controls on the composite of page later
		RepositoryConfig repoConfig = setConfigParameterInfo(selectedServer, repositoryType);

		// look through the repositoryConfigWizardPage extensions and find the appropriate page to use
        List<RepositoryConfigWizardPageExt> pages = new ArrayList<RepositoryConfigWizardPageExt>();
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] elements = registry
                .getConfigurationElementsFor(DeployUIActivator.PLUGIN_ID,
                        REPOSITORY_CONFIG_WIZARD_PAGE_EXTENSION);
        for (int i = 0; i < elements.length; i++) {
            String extRepoType = getAttribute(elements[i], REPOSITORY_TYPE_ID,
                    null);
            // if repository id of extension matches with our current repo id we are looking for then assign the page
            if (repoTypeId.equals(extRepoType)) {
                wizardPage = null;
                try {
                    // initialise the page and return it so we see it on preference screen
                    wizardPage = (RepositoryConfigWizardPageExt) elements[i]
                            .createExecutableExtension(REPOSITORY_WIZARD_PAGE);
                    wizardPage.init(repositoryType, repoConfig); 
                    wizardPage.createControl(parent);
                    wizardPage.setVisible(true);
                    setDirty(false);                    
                    return wizardPage.getControl();
                    
                } catch (CoreException e) {
                    throw new IllegalArgumentException(String.format(Messages.RepositoryInfoPropertyPage_CannotCreateExtension_message, wizardPage), e);
                }
            }
        }		
		return parent;
	}
	
	/**
	 * Easy way to get a particular parameter from a passed in key for the config
	 * @param config
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
    private ConfigParameter getParamByKey(RepositoryConfig config, String key) {
        for (ConfigParameter param : (List<ConfigParameter>) config
                .getConfigParameters()) {
            if (key.equals(param.getKey())) {
                return param;
            }
        }
        return null;
    }
	
	/**
	 * Sets the config parameter info for the repository config parameters.
	 * @param selectedServer
	 * @param repositoryType
	 */
	private RepositoryConfig setConfigParameterInfo(Server selectedServer, RepositoryType repositoryType){
	    // set config infos on config parameters
        RepositoryConfig repoConfig = selectedServer.getRepository().getRepositoryConfig();
        List<ConfigParameterInfo> parameterInfos = repositoryType.getRepositoryParameterInfos();
        for (ConfigParameterInfo info : parameterInfos) {
            ConfigParameter parameter = getParamByKey(repoConfig, info.getKey());
            if (parameter != null){
                parameter.setConfigParameterInfo(info);                 
            }
        }
        return repoConfig;
	}
	
	/**
	 * Easy way to get a value for a particular attribute
	 * @param configElement
	 * @param attrName
	 * @param defaultValue
	 * @return
	 */
	private static String getAttribute(IConfigurationElement configElement,
            String attrName, String defaultValue) {
        String value = configElement.getAttribute(attrName);
        if (value != null) {
            return value;
        }
        if (defaultValue != null) {
            return defaultValue;
        }
        throw new IllegalArgumentException(String.format(Messages.RepositoryInfoPropertyPage_MissingAttribute_message, attrName));
    }

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 * 
	 * @return
	 */
	@Override
	public boolean performOk() {
	    if (wizardPage != null){
	        wizardPage.transferStateToConfig();
	        setDirty(false);
	    }		
		return true;
	}

	@Override
	protected void performApply() {
	    if (wizardPage != null){
            wizardPage.transferStateToConfig();
            setDirty(false);
        }		
	}
	
	@Override
    protected void performDefaults() {
	    if (wizardPage != null){
    	    Server selectedServer = (Server) (getElement()).getAdapter(Server.class);
            RepositoryConfig repoConfig = selectedServer.getRepository().getRepositoryConfig();
            List<ConfigParameterInfo> parameterInfos = selectedServer.getRepository().getRepositoryType().getRepositoryParameterInfos();
            for (ConfigParameterInfo info : parameterInfos) {
                ConfigParameter parameter = getParamByKey(repoConfig, info.getKey());
                if (parameter != null){                
                    parameter.setValue(info.getDefaultValue());               
                }
            }            
            refresh();
            setDirty(false);
	    }
    }
	
	@Override
    public boolean okToLeave() {
	    if (wizardPage != null){
            if (isDirty()) {
                MessageBox messageBox =
                        new MessageBox(getShell(), SWT.YES | SWT.NO
                                | SWT.ICON_WARNING);
                messageBox
                        .setText(Messages.RepositoryInfoPropertyPage_UnsavedChanges_title);
                messageBox
                        .setMessage(Messages.RepositoryInfoPropertyPage_UnsavedChanges_message);
                boolean result = (messageBox.open() == SWT.YES);
                if (result) {
                    performApply();                
                } else {
                    performDefaults();
                }
            }
	    }
        return true;
    }
	
	/**
	 * @param dirty
	 */
	private void setDirty(boolean dirty){
	    if (wizardPage != null){
	        if (wizardPage instanceof RepositoryConfigWizardPageExt){
                ((RepositoryConfigWizardPageExt)wizardPage).setDirty(dirty);
            }else{
                DeployUIActivator.getDefault().getLogger().info(String.format(Messages.RepositoryInfoPropertyPage_RepositoryConfigWizardPageExt_NeedsToBeUsed_message,wizardPage.getName()));
            }
	    }
	}
	
	/**
     * 
     */
    private boolean isDirty(){
        boolean isDirty = false;
        if (wizardPage != null){
            if (wizardPage instanceof RepositoryConfigWizardPageExt){
                isDirty = ((RepositoryConfigWizardPageExt)wizardPage).isDirty();
            }else{
                DeployUIActivator.getDefault().getLogger().info(String.format(Messages.RepositoryInfoPropertyPage_RepositoryConfigWizardPageExt_NeedsToBeUsed_message,wizardPage.getName()));
            }
        }
        return isDirty;
    }
	
	/**
	 * 
	 */
	private void refresh(){
        if (wizardPage != null){
            if (wizardPage instanceof RepositoryConfigWizardPageExt){
                ((RepositoryConfigWizardPageExt)wizardPage).refresh();
            }else{
                DeployUIActivator.getDefault().getLogger().info(String.format(Messages.RepositoryInfoPropertyPage_RepositoryConfigWizardPageExt_NeedsToBeUsed_message,wizardPage.getName()));
            }
        }
    }
}
