/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.globalSignalDefinition.workingcopy;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.internal.Messages;
import com.tibco.xpd.globalSignalDefinition.provider.GlobalSignalDefinitionItemProviderAdapterFactory;
import com.tibco.xpd.globalSignalDefinition.util.GsdConstants;
import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;
import com.tibco.xpd.resources.wc.InvalidFileException;
import com.tibco.xpd.resources.wc.InvalidVersionException;

/**
 * Working copy for Global Signal Definition.
 * 
 * @author sajain
 * @since Jan 28, 2015
 */
public class GsdWorkingCopy extends AbstractTransactionalWorkingCopy {

    private final GlobalSignalMigration resourceMigration = new GlobalSignalMigration();

    /**
     * @param resources
     */
    public GsdWorkingCopy(List<IResource> resources) {
        super(resources);
    }

    /**
     * @see com.tibco.xpd.resources.WorkingCopy#getWorkingCopyEPackage()
     * 
     * @return
     */
    @Override
    public EPackage getWorkingCopyEPackage() {
        return GlobalSignalDefinitionPackage.eINSTANCE;
    }

    /**
     * Reverts the WorkingCopy Model changes and fires WorkingCopy Dirty Flag changed notification.
     */
    public void revertModelChanges() {
        /*
         * Reload WorkingCopy
         */
        super.reLoad();
        /*
         * The notification is required to refresh the Project Explorer's dirty flag
         */
        fireWCDirtyFlagChanged();
    }

    /**
     * 
     * @return {@link GlobalSignalDefinitions} root model entry, returns null if the model is empty.
     */
    public GlobalSignalDefinitions getGlobalSignalDefinitions() {
        EObject rootElement = getRootElement();
        if (rootElement instanceof GlobalSignalDefinitions) {
            return ((GlobalSignalDefinitions) rootElement);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#createAdapterFactory()
     * 
     * @return
     */
    @Override
    protected AdapterFactory createAdapterFactory() {
        AdapterFactory af = super.createAdapterFactory();

        if (af instanceof ComposedAdapterFactory) {
            ((ComposedAdapterFactory) af).addAdapterFactory(new GlobalSignalDefinitionItemProviderAdapterFactory());
        }

        return af;
    }

    /**
     * Loads the given resource. Also checks if the resource content requires migration. An InvalidVersionException is
     * raised to indicate that migration is required. This will trigger the creation of a validation marker which, in
     * turn, will trigger the invocation of {@link #doMigrateToLatestVersion()}.
     * 
     * @see com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#loadResource(org.eclipse.core.resources.IResource)
     *
     * @param aFileResource
     *            the resource that identifies the Global Signal Resource to be loaded.
     * @return the Global Signal Resource.
     * @throws InvalidFileException
     *             if a resource error occurred, or if migration is required.
     */
    @Override
    protected Resource loadResource(IResource aFileResource) throws InvalidFileException {
        Resource res = super.loadResource(aFileResource);

        // if resource content requires migration
        if (resourceMigration.migrationRequired(aFileResource, res)) {
            // Unload the resource and throw exception to create validation marker and trigger migration
            res.unload();
            throw new InvalidVersionException(Messages.GsdWorkingCopy_OlderVersionMessage);
        }

        return res;
    }

    /**
     * Change UML version in all payload fields' external references to UML 5.0.0. http://www.eclipse.org/uml2/5.0.0/UML
     * 
     * Also change all integer fields to fixed point number fields with 0 decimals.
     * 
     * @throws CoreException
     */
    @Override
    protected void doMigrateToLatestVersion() throws CoreException {
        try {
            IResource fileResource = getFirstResource();
            Resource docResource = super.loadResource(fileResource);

            TransactionalEditingDomain domain = (TransactionalEditingDomain) getEditingDomain();

            domain.getCommandStack().execute(new RecordingCommand(domain) {
                @Override
                protected void doExecute() {
                    // perform migrations
                    resourceMigration.migrate(fileResource, docResource);

                    // set the latest version in the model document
                    docResource.getAllContents().forEachRemaining(gsd -> {
                        if (gsd instanceof GlobalSignalDefinitions) {
                            // ((GlobalSignalDefinitions) gsd).setVersion(1000);
                        }
                    });
                }
            });

            docResource.save(null);
        } catch (Exception e) {
            if (e.getCause() instanceof CoreException) {
                throw (CoreException) e.getCause();
            } else {
                throw new CoreException(
                        new Status(IStatus.ERROR, GsdConstants.PLUGIN_ID,
                        e.getLocalizedMessage(), e.getCause()));
            }
        }
    }
}
