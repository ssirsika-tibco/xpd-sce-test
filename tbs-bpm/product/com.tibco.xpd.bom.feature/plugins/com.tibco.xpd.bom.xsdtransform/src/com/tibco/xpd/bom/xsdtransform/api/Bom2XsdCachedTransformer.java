/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.api;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.bom.xsdtransform.exports.BOM2XSDTransformer;

/**
 * BOM 2 XSD transformer.
 * <p>
 * Note that the XSD schemas transformed from boms during this session are
 * cached. Therefore if an XSD is a dependent of multiple files input to the
 * transform method then the depenmdecy XSD will NOT be transformed twice.
 * However it WILL be serialised to the given target folder on each call to
 * transform.
 * <p>
 * <li>For instance if A.bom refs SUB.bom _and_ B.BOM refs SUB.bom</li>
 * <li>If you pass A.bom into transform() then A.xsd and SUB.xsd will be
 * generated and output to the target folder</li>
 * <li>If you then pass B.bom into transform() then B.xsd is generated, SUB.xsd
 * is grabbed from cache from previous transform() and both are output to the
 * target folder.
 * 
 * @author aallway
 * @since 6 Apr 2011
 */
public class Bom2XsdCachedTransformer {

    /**
     * Note that this class is merely a delegating wrapper for
     * {@link BOM2XSDTransformer} that hides everything except the api methods
     * from the caller.
     */
    private BOM2XSDTransformer bom2XsdTransformer;

    /**
     * Construct the BOM2XSDTransformer.
     * <p>
     * 
     * @param usePrefValidation
     *            - If true then only validates resultant XML Schema(s) if
     *            ticked in preferences else it always defaults to validating it
     *            each method call
     * 
     * @param sourceBomValidationRequirements
     *            <code>true</code> to ensure that the source bom has passed
     *            previous validation run successfully.
     * 
     */
    public Bom2XsdCachedTransformer(boolean usePrefValidation,
            Bom2XsdSourceValidationType sourceBomValidationRequirements) {
        bom2XsdTransformer =
                new BOM2XSDTransformer(usePrefValidation,
                        sourceBomValidationRequirements);
    }

    /**
     * <code>true</code> If want all the schemas returned by the transform
     * regardless of whether they were returned by a previous transform
     * performed using this Export data.
     * 
     * This is useful if you are performing multiple transforms with this data
     * BUT outputting to separate folders (and hence need all referenced schemas
     * output also).
     * 
     * @param wantAllReferencedSchemas
     *            the wantAllReferencedSchemas to set
     * @deprecated This method was only ever here so that when BDS called
     *             transform it could take advantage of using the same
     *             transformer instance for multiple transforms (hence re-using
     *             already parsed schemas) BUT needed them to ALWAYS to be
     *             serialized to taget folder - HOWEVER now that we serialise to
     *             bom2xsd if out of date then copy from there to BDS, then
     *             there is no need to ask Bom2Xsd.ext to always return all
     *             schemas because any already previously-parsed schema will
     *             already be in bom2xsd ready to copy to BDS. Can't see any
     *             need to keep this functionality anymore but will leave here
     *             for a while just in case.
     */
    @Deprecated
    public void setWantAllReferencedSchemasEveryTime(
            boolean wantAllReferencedSchemas) {
        bom2XsdTransformer
                .setWantAllReferencedSchemasEveryTime(wantAllReferencedSchemas);
    }

    /**
     * Perform a transformation of the given TOP-LEVEL BOM files (BOM files in a
     * set of BOM files that are not referenced by other BOM files).
     * <p>
     * The OAW transformation to XSD is performed on each and will traverse and
     * output the underlying referenced BOM models.
     * <p>
     * IT IS ASSUMED that the caller has performed all relevant validation of
     * the BOM files (and the files referenced from them). This can be performed
     * with {@link #isDestinationAndNoErrors(IResource, String, String, List)}
     * <p>
     * <b>The transformation is always performed into the .bom2xsd folder
     * appropriate for each BOM file in each set.</b> The generated XSD is then
     * optionally copied on to a final target folder.
     * <p>
     * It is assumed that caller has done any 'out-of-dateness' checking so the
     * transform is always performed (but already parsed schemas from previous
     * calls to same class instance will be re-used).
     * 
     * @param source
     * @param monitor
     * 
     * @return {@link IStatus}
     */
    public List<IStatus> transformToBom2Xsd(final IFile source,
            IProgressMonitor monitor) {
        return bom2XsdTransformer.transformToBom2Xsd(source, monitor);
    }

    /**
     * Perform a transformation of the given TOP-LEVEL BOM files (BOM files in a
     * set of BOM files that are not referenced by other BOM files).
     * <p>
     * The OAW transformation to XSD is performed on each and will traverse and
     * output the underlying referenced BOM models.
     * <p>
     * IT IS ASSUMED that the caller has performed all relevant validation of
     * the BOM files (and the files referenced from them). This can be performed
     * with {@link #isDestinationAndNoErrors(IResource, String, String, List)}
     * <p>
     * <b>The transformation is always performed into the .bom2xsd folder
     * appropriate for each BOM file in each set.</b> The generated XSD is then
     * optionally copied on to a final target folder.
     * <p>
     * If a finalTargetFolder is provided <b>and the XSDs expected to be
     * generated for the given BOM and all the BOMs it is dependent upon are up
     * to date</b> then existing XSDs are copied from .bom2xsd to
     * finalTargetFolder.
     * <p>
     * If finalTargetFolder is NOT provided then the transformation to .bom2xsd
     * will always be performed regardless existing xsds in .bom2xsd folders. If
     * the same transformer class instance is used to perform multiple
     * transforms then previously transformed schemas (in memory) can and will
     * be re-used for further transforms.
     * 
     * @param source
     * @param finalTargetFolder
     *            Final target folder to copy generated XSD files (which are
     *            ALWAYS generated into appropriate .bom2xsd folder) to <b>or
     *            <code>null</code> if no onward copy to other folder
     *            required.</b>
     * @param isLocalWorkspaceTarget
     *            whether finalTargetFolder is in workspace (else in file system
     *            outside of workspace).
     * @param monitor
     * 
     * @return {@link IStatus}
     */
    public List<IStatus> transform(final IFile source,
            final IPath finalTargetFolder, boolean isLocalWorkspaceTarget,
            IProgressMonitor monitor) {
        return bom2XsdTransformer.transform(source,
                finalTargetFolder,
                isLocalWorkspaceTarget,
                monitor);
    }

}
