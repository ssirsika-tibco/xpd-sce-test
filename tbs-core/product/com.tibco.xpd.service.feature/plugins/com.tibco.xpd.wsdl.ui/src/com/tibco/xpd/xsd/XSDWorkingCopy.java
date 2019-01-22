/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.xsd;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.xsd.XSDSchema;

import com.tibco.xpd.resources.util.UnprotectedWriteOperation;
import com.tibco.xpd.resources.wc.InvalidFileException;
import com.tibco.xpd.wsdl.ui.internal.AbstractExtModelWorkingCopy;
import com.tibco.xpd.wsdl.ui.internal.CheckSumUtil;
import com.tibco.xpd.wsdl.ui.internal.Messages;

/**
 * Working copy for XSD files.
 * <p>
 * Since 3.5 this provides a {@link #getCheckSum() checksum}.
 * </p>
 * 
 * @since 3.1
 * @author dhiraj, njpatel
 */
public class XSDWorkingCopy extends AbstractExtModelWorkingCopy {

    private String checkSum;

    private long lastCheckSumModificationStamp;

    @Override
    protected EObject doLoadModel() throws InvalidFileException {
        /*
         * If an empty xsd file exists in the workspace then an attempt will be
         * made to load it and a SAXParser error (Premature end of file) will be
         * reported in the log. So, to get around this check if the file has
         * content before loading it.
         */
        IResource resource = getFirstResource();
        if (resource != null) {
            IPath location = resource.getLocation();
            if (location != null) {
                File file = new File(location.toOSString());
                if (file != null && file.exists()) {
                    if (file.length() == 0) {
                        throw new InvalidFileException(
                                Messages.XSDWorkingCopy_noContentInFile_error_shortdesc);
                    }
                }
            }
        }
        return super.doLoadModel();
    }

    /**
     * @param resources
     */
    public XSDWorkingCopy(List<IResource> resources) {
        super(resources, XSDPackage.eINSTANCE);
    }

    /**
     * @see com.tibco.xpd.wsdl.ui.internal.AbstractExtModelWorkingCopy#resetSchema()
     * 
     */
    @Override
    protected void resetSchema() {
        final EObject eo = getRootElement();
        if (eo instanceof XSDSchema) {
            UnprotectedWriteOperation op =
                    new UnprotectedWriteOperation(
                            (TransactionalEditingDomain) getEditingDomain()) {

                        @Override
                        protected Object doExecute() {
                            /*
                             * Reset all references. This will cause all
                             * externally referenced elements to re-resolve the
                             * references.
                             */
                            ((XSDSchema) eo).reset();
                            return eo;
                        }
                    };
            op.execute();
            fireWCModelChanged();
        }
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#uninstallAdapters(org.eclipse.emf.ecore.resource.Resource)
     * 
     * @param res
     */
    @Override
    protected void uninstallAdapters(Resource res) {
        // Need to clear all adapters from the resource, otherwise the XSD
        // resource will not be removed from memory.
        res.eAdapters().clear();
    }

    /**
     * Get a checksum for this resource.
     * 
     * @return checksum if it can be calculated from the resource,
     *         <code>null</code> otherwise.
     * @throws IOException
     * @since 3.5
     */
    public String getCheckSum() throws IOException {

        IResource resource = getFirstResource();
        if (resource instanceof IFile) {
            long modStamp = resource.getModificationStamp();
            if (checkSum == null || lastCheckSumModificationStamp != modStamp) {

                IPath location = resource.getLocation();
                if (location != null) {
                    File file = location.toFile();
                    if (file != null && file.exists()) {
                        checkSum = CheckSumUtil.getCheckSum(file);
                        lastCheckSumModificationStamp = modStamp;
                    }
                }
            }
        }

        return checkSum;
    }

    @Override
    protected void cleanup() {
        lastCheckSumModificationStamp = 0;
        checkSum = null;

        super.cleanup();
    }

}
