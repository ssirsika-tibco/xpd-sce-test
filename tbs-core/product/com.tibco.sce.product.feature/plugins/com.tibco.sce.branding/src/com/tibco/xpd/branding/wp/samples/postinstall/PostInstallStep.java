/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding.wp.samples.postinstall;

import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.intro.IIntroSite;


/**
 * 
 * @author rgreen
 *
 */
public class PostInstallStep {

    private String order;
    private final IConfigurationElement configurationElement;

    public PostInstallStep(IConfigurationElement configurationElement){
        this.configurationElement = configurationElement;
        String attribute = this.configurationElement.getAttribute("order"); //$NON-NLS-1$
        if(attribute==null){
            this.order="ZZZZZZZZ"; //$NON-NLS-1$
        } else {
            this.order=attribute;
        }
    }

    public String getOrder() {
        return order;
    }

    public IConfigurationElement getConfigurationElement() {
        return configurationElement;
    }

    public void execute(IIntroSite site) {
        try {
            PostInstallAction result = (PostInstallAction) configurationElement.createExecutableExtension("class"); //$NON-NLS-1$

            Properties params=new Properties();
            IConfigurationElement[] children = configurationElement.getChildren("parameter"); //$NON-NLS-1$
            for (IConfigurationElement propertyElement : children) {
                String key=propertyElement.getAttribute("name"); //$NON-NLS-1$
                String value=propertyElement.getAttribute("value"); //$NON-NLS-1$
                params.setProperty(key, value);
            }
            result.run(site, params);
        } catch (CoreException e) {
            //log.error(e);
        } catch (Throwable e) {
            //log.error(e);
        }
    }
}
