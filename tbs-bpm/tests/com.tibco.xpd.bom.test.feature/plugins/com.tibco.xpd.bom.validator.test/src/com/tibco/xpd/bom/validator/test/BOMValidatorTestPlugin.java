/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.bom.validator.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.IWorkingCopyCreationListener;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.internal.wc.WorkingCopyCreationListenersManager;
import com.tibco.xpd.resources.internal.wc.WorkingCopyFactoriesManager;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.projectconfig.DocumentRoot;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.util.ProjectConfigResourceFactoryImpl;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * The main plugin class to be used in the desktop.
 */
public class BOMValidatorTestPlugin extends Plugin {

    /**
     * The id of the XPD plugin (value <code>"com.tibco.xpd.bom.validator.test"</code>).
     */
    public static final String ID_PLUGIN = "com.tibco.xpd.bom.validator.test"; //$NON-NLS-1$

    /** The shared instance. */
    private static BOMValidatorTestPlugin plugin;

    /** logger instance. */
    private final Logger logger = LoggerFactory.createLogger(ID_PLUGIN);// new

    /**
     * The constructor.
     */
    public BOMValidatorTestPlugin() {
        super();
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     * 
     * @param context
     * @throws Exception
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped
     * 
     * @param context
     * @throws Exception
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);

    }

    /**
     * Returns the shared instance.
     * 
     * @return shared instance
     */
    public static BOMValidatorTestPlugin getDefault() {
        return plugin;
    }



    public static void log(Throwable e){
        getDefault().logger.error(e);
    }
}
