/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Fragment properties that keeps track of the properties file and provides a
 * {@link #save() save} option that will save to this file.
 * 
 * @author njpatel
 * 
 */
public class FragmentProperties extends Properties {

    /**
     * UID
     */
    private static final long serialVersionUID = 8506878658657253037L;

    protected final Logger LOG = FragmentsActivator.getDefault().getLogger();

    private IFile propertiesFile;

    private SaveJob saveJob;

    private InputStream inStream;

    /**
     * Required by the save job to get the latest properties to save (SCF-228).
     * 
     */
    private interface IPropertiesProvider {
        FragmentProperties getProperties();
    }

    /**
     * Fragment properties.
     * 
     * @param propertiesFile
     *            properties file. If this file does not exist then it will be
     *            created, otherwise it will be updated with the properties on
     *            save.
     * @throws CoreException
     */
    public FragmentProperties(IFile propertiesFile) throws CoreException {
        this.propertiesFile = propertiesFile;
        Assert.isNotNull(propertiesFile, "Fragment properties file"); //$NON-NLS-1$

        if (propertiesFile.exists()) {

            if (!propertiesFile.isSynchronized(IResource.DEPTH_ZERO)) {
                propertiesFile.refreshLocal(IResource.DEPTH_ZERO, null);
            }

            if (propertiesFile.exists()) {
                InputStream inStream = null;
                try {
                    inStream = propertiesFile.getContents();

                    load(inStream);
                } catch (IOException e) {
                    throw new CoreException(new Status(IStatus.ERROR,
                            FragmentsActivator.PLUGIN_ID,
                            e.getLocalizedMessage(), e));
                } finally {
                    if (inStream != null) {
                        try {
                            inStream.close();
                        } catch (IOException e) {
                            // Do nothing
                        }
                    }
                }
            }
        }
    }

    /**
     * Load the fragment properties from the input stream provided. NOTE:
     * {@link #save()} should not be called when input stream is used to load
     * the properties.
     * 
     * @param in
     * @throws CoreException
     */
    public FragmentProperties(InputStream in) throws CoreException {
        Assert.isNotNull(in);
        this.inStream = in;

        try {
            load(inStream);
        } catch (IOException e) {
            throw new CoreException(new Status(IStatus.ERROR,
                    FragmentsActivator.PLUGIN_ID, e.getLocalizedMessage(), e));
        }

    }

    @Override
    public synchronized Object setProperty(String key, String value) {
        if (value == null) {
            remove(key);
            return null;
        }
        return super.setProperty(key, value);
    }

    /**
     * Get the property key made up of the id and part provided
     * 
     * @param id
     *            fragment element id
     * @param part
     *            key part to store in the properties for this fragment element.
     * @return property key.
     */
    public static String getPropertyKey(String id, String part) {
        return id + "." + part; //$NON-NLS-1$
    }

    /**
     * Save the properties to the properties file.
     * 
     * Tries to avoid carrying out older save jobs: If jobs have previously been
     * scheduled then attempts are made to cancel these and carry out only the
     * most recent one
     * 
     * SCF-228: Change made in XPD-3913 to create a list of all scheduled jobs
     * has been removed as it still didn't resolve the problem of missing saves
     * of properties files. This has been reverted to a single save job but
     * improved to get the latest properties rather than the properties being
     * passed to the save job when it is scheduled. This means that when the
     * save occurs it will always contain the latest change to the properties.
     * 
     * @throws CoreException
     */
    public synchronized void save() throws CoreException {
        if (propertiesFile != null) {
            if (saveJob != null) {
                /*
                 * SCF-228: If the previously scheduled job is complete or can
                 * be cancelled then do so and create a new save job.
                 */
                if (saveJob.getState() == Job.NONE || saveJob.cancel()) {
                    saveJob = null;
                }
            }

            if (saveJob == null) {
                /*
                 * SCF-228: Create a save job that will get the latest fragment
                 * properties when the save eventually happens.
                 */
                saveJob =
                        new SaveJob(propertiesFile, new IPropertiesProvider() {

                            @Override
                            public FragmentProperties getProperties() {
                                return FragmentProperties.this;
                            }
                        });
                saveJob.schedule(500);
            }

        } else {
            throw new IllegalArgumentException(
                    "No properties file specified so cannot save properties."); //$NON-NLS-1$
        }

    }

    /**
     * Save properties job. This will fire off the workspace runnable
     * {@link SavePropertiesAction} to save the properties file.
     * 
     * @author njpatel
     * 
     */
    private class SaveJob extends Job {

        private final IFile propertiesFile;

        private final IPropertiesProvider provider;

        public SaveJob(IFile propertiesFile, IPropertiesProvider provider) {
            super(Messages.FragmentProperties_saveProperties_job_label);
            this.propertiesFile = propertiesFile;
            this.provider = provider;
            setSystem(true);
        }

        @Override
        protected IStatus run(IProgressMonitor monitor) {

            // Make sure the parent is still there as this will run in a
            // background job
            FragmentProperties properties = provider.getProperties();
            if (propertiesFile != null && properties != null
                    && propertiesFile.getParent() != null
                    && propertiesFile.getParent().exists()) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                ByteArrayInputStream source = null;
                try {
                    properties.store(outStream, null);

                    source = new ByteArrayInputStream(outStream.toByteArray());

                    if (propertiesFile.exists()) {
                        propertiesFile.setContents(source, true, true, monitor);
                    } else {
                        propertiesFile.create(source, true, monitor);
                    }
                } catch (IOException e) {
                    return new Status(IStatus.ERROR,
                            FragmentsActivator.PLUGIN_ID,
                            e.getLocalizedMessage(), e);
                } catch (CoreException e) {
                    return e.getStatus();
                } finally {
                    try {
                        outStream.close();
                        if (source != null) {
                            source.close();
                        }
                    } catch (IOException e) {
                        // Do nothing
                    }
                }
            }

            return Status.OK_STATUS;
        }
    }

}
