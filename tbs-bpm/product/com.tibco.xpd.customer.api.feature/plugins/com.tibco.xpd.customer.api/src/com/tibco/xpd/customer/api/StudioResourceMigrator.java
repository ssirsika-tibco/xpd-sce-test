/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.customer.api;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.customer.api.plugin.BusinessStudioCustomerApiPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.resources.wc.InvalidFileException;

/**
 * <p>
 * This class provides the ability to migrate (upgrade) the content a file
 * created by a previous version of Business Studio so that it becomes
 * compatible with the version of Business Studio that this API is called from.
 * </p>
 * <p>
 * Many file types in Business Studio store a format-version of some kind. The
 * format-version is usually a simple incrementing integer version that is
 * independent of the Business Studio product version. It denotes the expected
 * format (such as an XSD schema) of a given file type and the semantic usage of
 * its content (such as the expected usage of element values, or schema
 * extension elements).
 * </p>
 * <p>
 * When files created by previous versions of Business Studio are imported into
 * later versions, the format-version allows Business Studio to perform an
 * incremental migration ('upgrade') of the file <b>from</b> its current
 * format-version <b>to</b> the format-version of the file in employed Business
 * Studio. The migration is performed incrementally (from 1 to 2, then 2 to 3,
 * then 3 to 4 and so on) and therefore migration is possible from any previous
 * format-version.
 * </p>
 * <p>
 * Normally the migration is performed during import of a project to Studio or
 * by a quick-fix to a problem marker added to files whose format-version is out
 * of date. The {@link StudioResourceMigrator} API allows the migration to be
 * performed programmatically (and hence avoiding the necessity for the user to
 * manually quick-fix files created for older format versions).
 * </p>
 * <p>
 * <b>This means 3rd party tools can be coded to create files in the format
 * version used the version of Studio that is current at the time of writing the
 * tool.</b> Then provided the 3rd party tool uses
 * {@link StudioResourceMigrator} to migrate the file after creation then, in
 * the future, the file will still be compatible with the version of Studio
 * employed at that time.
 * </p>
 * <p>
 * <b>Note: The file provided must be located in a project in a Business Studio
 * workspace within its normally expected special-folder type in order for the
 * migration to function correctly.</b> If this is not the case then appropriate
 * exceptions are provided on constructors / methods (as detailed below).
 * </p>
 * 
 * @author sajain
 * @since Sep 23, 2013
 */

public class StudioResourceMigrator {
    /**
     * Resource file to be migrated.
     */
    private IFile file;

    /**
     * AbstractWorkingCopy instance to store the working copy of the resource
     * file to be migrated.
     */
    AbstractWorkingCopy abstractWc;

    /**
     * <p>
     * Construct a class capable of migrating (upgrading) the content a file
     * created by a previous version of Business Studio so that it becomes
     * compatible with the version of Business Studio that this API is called
     * from.
     * </p>
     * <p>
     * The {@link #requiresMigration()} method can be used to check if a file
     * requires migration.
     * </p>
     * <p>
     * The {@link #migrate()} method can be used to migrate the file if
     * migration is required.
     * </p>
     * 
     * @param file
     *            The file to migrate The file provided must be located in a
     *            project in a Business Studio workspace within its normally
     *            expected special-folder type in order for the migration to
     *            function correctly.
     * 
     * @throws InvalidFileException
     *             The file parameter <code>null</code> <b>or</b> indicates a
     *             file that is not accessible <b>or</b> is not located in its
     *             expected special-folder type in a Studio workspace <b>or</b>
     *             is not in an expected current or previous format for the file
     *             type.
     */
    public StudioResourceMigrator(IFile file) throws IllegalArgumentException {
        super();

        this.file = file;

        /*
         * Check whether the resource file is null or not. If it is, throw an
         * IllegalArgumentException stating the cause.
         */
        if (file == null) {
            IllegalArgumentException e =
                    new IllegalArgumentException(
                            "Resource file passed as an argument is null."); //$NON-NLS-1$
            BusinessStudioCustomerApiPlugin.getDefault().getLogger().error(e);
            throw e;
        }

        /*
         * Check whether the resource file is accessible or not. If not, throw
         * an IllegalArgumentException stating the cause.
         */
        if (!file.isAccessible()) {
            IllegalArgumentException e =
                    new IllegalArgumentException(
                            "Resource file '" + file.getName() + "' passed as an argument to the StudioResourceMigrator constructor is not accessible."); //$NON-NLS-1$
            BusinessStudioCustomerApiPlugin.getDefault().getLogger().error(e);
            throw e;
        }

        /*
         * Get the working copy of the resource.
         */
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(this.file);

        /*
         * Check if wc is an instance of AbstractWorkingCopy. Since
         * getWorkingCopy(IFile) returns underlying interface that does not have
         * the migrate method, we'll have to type-cast it to
         * AbstractWorkingCopy.
         */
        if (wc instanceof AbstractWorkingCopy) {

            /*
             * Cast to AbstractWorkingCopy
             */
            abstractWc = (AbstractWorkingCopy) wc;

            /*
             * Check whether the file is correctly formatted regardless of
             * FormatVersion.
             */
            if (abstractWc.isInvalidFile() && !abstractWc.isInvalidVersion()) {
                IllegalArgumentException e =
                        new IllegalArgumentException(
                                "The file content of the file '" + this.file.getName() + "' is not in the expected format for the file type."); //$NON-NLS-1$
                BusinessStudioCustomerApiPlugin.getDefault().getLogger()
                        .error(e);
                throw e;
            }
        } else {
            /*
             * The fetched working copy doesn't seem to be valid here. Hence
             * throw IllegalArgumentException stating this.
             */
            IllegalArgumentException e =
                    new IllegalArgumentException(
                            "Cannot access the working copy for the file '" + this.file.getName() + "'."); //$NON-NLS-1$
            BusinessStudioCustomerApiPlugin.getDefault().getLogger().error(e);
            throw e;
        }
    }

    /**
     * <p>
     * Method to migrate (upgrade) the format of the Studio file this class
     * instance was constructed with to the format of the file in use by this
     * version of Business Studio.
     * </p>
     * <p>
     * If the file format is already up to date then no migration is performed.
     * 
     * @throws CoreException
     *             A problem was encountered during the migration.
     */
    public void migrate() throws CoreException {

        /*
         * Check if the resource file requires migration. If it does, then
         * migrate it.
         */
        if (requiresMigration()) {
            try {
                abstractWc.migrate();
            } catch (CoreException e) {
                BusinessStudioCustomerApiPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                "Problem in migration: Couldn't migrate the working copy instance as abstractWc.migrate() threw a CoreException."); //$NON-NLS-1$
                throw e;
            }
        }
    }

    /**
     * <p>
     * Checks whether the format of the file that this class was constructed
     * with is up to date with that of the current Business Studio
     * <p>
     * 
     * @return <code>true</code> if the specified resource requires migration,
     *         <code>false</code> otherwise.
     */
    public boolean requiresMigration() {
        return abstractWc.isInvalidVersion();
    }
}
