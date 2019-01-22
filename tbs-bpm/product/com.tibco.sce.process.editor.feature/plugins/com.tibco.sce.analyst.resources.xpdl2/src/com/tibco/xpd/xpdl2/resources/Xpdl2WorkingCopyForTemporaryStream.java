/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */
package com.tibco.xpd.xpdl2.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.eclipse.core.commands.operations.DefaultOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.PlatformResourceURIHandlerImpl;
import org.eclipse.emf.ecore.resource.impl.PlatformResourceURIHandlerImpl.PlatformResourceOutputStream;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.resources.wc.InvalidFileException;
import com.tibco.xpd.resources.wc.gmf.XpdEditingDomainFactory;
import com.tibco.xpd.xpdl2.util.Xpdl2XMLProcessor;

/**
 * XPD-1140: Create working copy for the given resource BUT with an alternate
 * input stream to load from.
 * <p>
 * Hopefully this means that an alternate (say repository version) of the file
 * can be loaded without breaking everything else in working copy (when things
 * expect a resource to be there to locate the actual resource in relation to
 * other resources in workspace). *
 * 
 * @author aallway
 * @since 3.4.2 (21 Sep 2010)
 */
public class Xpdl2WorkingCopyForTemporaryStream extends Xpdl2WorkingCopyImpl {

    /**
     * XPD-1140: Create working copy for the given resource BUT with an
     * alternate input stream to load from.
     * 
     * This is closed after initial load.
     */
    private InputStream temporaryInputStream;

    /**
     * XPD-1140: Create working copy for the given resource BUT with an
     * alternate input stream to load from.
     * <p>
     * Hopefully this means that an alternate (say repository version) of the
     * file can be loaded without breaking everything else in working copy (when
     * things expect a resource to be there to locate the actual resource in
     * relation to other resources in workspace).
     * 
     * @param resource
     *            Resource is optional (for instance, may be loading from SVN
     *            repository stream and not have correct access to local file
     *            resource.
     * @param xpdl2FileType
     * @param alternateInputStream
     */
    public Xpdl2WorkingCopyForTemporaryStream(IResource resource,
            Xpdl2FileType xpdl2FileType, InputStream alternateInputStream) {
        super(resource, xpdl2FileType);

        this.temporaryInputStream = alternateInputStream;
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#createEditingDomain()
     * 
     * @return
     */
    @Override
    protected EditingDomain createEditingDomain() {

        TransactionalEditingDomain editingDomain =
                XpdEditingDomainFactory.getInstance()
                        .createEditingDomain(new DefaultOperationHistory());

        return editingDomain;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#isExist()
     */
    @Override
    public boolean isExist() {
        /*
         * Resource may not 'exist' if it is a remote repository revision. We
         * have to pretend it exists in order to get doLoadModel() to go forward
         * and call loadResource()
         */
        return this.temporaryInputStream != null;
    }

    @Override
    protected Resource loadResource(IResource resource)
            throws InvalidFileException {
        Resource emfResource = null;

        Xpdl2XMLProcessor xpdl2Processor = new Xpdl2XMLProcessor();

        try {
            emfResource = xpdl2Processor.load(temporaryInputStream, null);
            emfResource.setTrackingModification(true);

        } catch (IOException e) {
            throw new InvalidFileException(
                    "Failed to load resource(%s) from alternative InputStream.", e); //$NON-NLS-1$

        } finally {
            try {
                temporaryInputStream.close();
            } catch (IOException e) {
                /* Not much we could do here anyway! */
            }
        }

        return emfResource;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.wc.AbstractWorkingCopy#createResourceChangeListener
     * ()
     */
    @Override
    protected IResourceChangeListener createResourceChangeListener() {
        /*
         * Do not respond to the resources changes, this would be on a save of
         * the actual workspace xpdl file rather than the temporary stream copy
         * that we have so we don't want to attempt to reload etc!
         */
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#
     * registerEditingDomainListeners(org.eclipse.emf.edit.domain.EditingDomain)
     */
    @Override
    protected void registerEditingDomainListeners(EditingDomain ed) {

        ed.getCommandStack().addCommandStackListener(this);
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#isWorkingCopyDirty()
     * 
     * @return
     */
    @Override
    public boolean isWorkingCopyDirty() {
        if (getEditingDomain() != null) {
            if (getEditingDomain().getCommandStack() != null) {
                return ((BasicCommandStack) getEditingDomain()
                        .getCommandStack()).isSaveNeeded();
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy#
     * createOperationHistoryListener()
     */
    @Override
    protected IOperationHistoryListener createOperationHistoryListener() {
        /*
         * Do not respond to the op history changes, this would be on a save of
         * the actual workspace xpdl file rather than the temporary stream copy
         * that we have so we don't want to attempt to reload etc!
         */
        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl#doSave()
     */
    @Override
    protected void doSave() throws IOException {

        IResource resource = getFirstResource();
        if (!(resource instanceof IFile)) {
            throw new RuntimeException(
                    "Cannot save resource that was opened with alternate InputStream and no file resource"); //$NON-NLS-1$
        }

        IFile file = (IFile) resource;

        /*
         * Cannot just call super.save() as the EMF Resource will have a URI or
         * "xml" not the URI of the file resource (because we loaded direct from
         * input stream).
         * 
         * So I had a quick look-see what a normal save ends up as in EMF
         * resource when it DOES have a file and the below is basically it!
         */
        if (!getResources().isEmpty()) {
            Resource xmlResource = getResources().iterator().next();

            PlatformResourceOutputStream outputStream =
                    new PlatformResourceURIHandlerImpl.PlatformResourceOutputStream(
                            file, false, true, null);

            xmlResource.save(outputStream, Collections.emptyMap());

        }

        return;
    }
}
