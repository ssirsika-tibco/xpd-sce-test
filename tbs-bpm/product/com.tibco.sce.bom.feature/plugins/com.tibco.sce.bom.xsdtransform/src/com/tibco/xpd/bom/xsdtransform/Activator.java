/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author glewis
 * 
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.bom.xsdtransform"; //$NON-NLS-1$

    public static final String EXPORT_FOLDER =
            Messages.XMLSchemaExportFolderLabel;

    public static final String XSD_FILE_EXT = "xsd"; //$NON-NLS-1$

    public static final String ATTRIBUTE_ITEM_ID = "item_id"; //$NON-NLS-1$

    public static final String ATTRIBUTE_TARGET_NAMESPACE = "target_namespace"; //$NON-NLS-1$

    public final static String ATTRIBUTE_BOM_ORIGIN_FILENAME =
            "bom_origin_filename"; //$NON-NLS-1$

    /*
     * just for this constant the bom modeler custom plugin was having a
     * dependency on this plugin. So moved the constant to a plugin visible to
     * bom modeler custom and removed the dependency
     */
    public final static String PATHMAP_XSDNOTATION_PROFILE =
            BOMResourcesPlugin.PATHMAP_XSDNOTATION_PROFILE;

    /**
     * The transformation editing domain. This is for use only by the XSD and
     * WSDL import/export in the BOM.
     */
    private static final String TRANSFORM_EDITING_DOMAIN =
            "com.tibco.xpd.bom.transform.editingdomain"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor
     */
    public Activator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * Returns logger for the plug-in.
     * 
     * @return plug-in's logger.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Get the transformation transactional editing domain.
     * 
     * @return <code>TransactionalEditingDomain</code>
     * 
     */
    public TransactionalEditingDomain getTransformationEditingDomain() {
        TransactionalEditingDomain editingDomain =
                TransactionalEditingDomain.Registry.INSTANCE
                        .getEditingDomain(TRANSFORM_EDITING_DOMAIN);

        editingDomain.getResourceSet().getLoadOptions()
                .put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$)

        return editingDomain;
    }

    /**
     * Start a transaction. If a read-write transaction is already active then
     * this method will do nothing and return <code>null</code>. If the active
     * transaction is read-only then this will start an unprotected write
     * transaction and return. Othewise, this will start a new transaction and
     * return.
     * 
     * @param domain
     *            editing doman
     * @return {@link Transaction} if transaction created, <code>null</code>
     *         otherwise.
     * @throws InterruptedException
     */
    public static Transaction startTransaction(
            InternalTransactionalEditingDomain editingDomain)
            throws InterruptedException {

        Transaction transaction = editingDomain.getActiveTransaction();
        if (transaction != null) {
            if (transaction.isReadOnly()
                    || transaction.getOwner() != Thread.currentThread()) {
                // Start an unprotected transaction
                Map<String, Object> txOptions = new HashMap<String, Object>();
                txOptions.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);
                transaction = editingDomain.startTransaction(false, txOptions);
            } else {
                // This is already a read-write transaction so don't return
                // anything
                transaction = null;
            }
        } else {
            transaction =
                    editingDomain
                            .startTransaction(false, Collections.EMPTY_MAP);
        }

        return transaction;
    }
}
