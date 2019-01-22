package com.tibco.bds.designtime.generator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;

import com.tibco.bds.designtime.generator.internal.Messages;
import com.tibco.xpd.bom.gen.biz.GenerationException;
import com.tibco.xpd.bom.xsdtransform.api.Bom2XsdCachedTransformer;

public class BOMToXSDTransformer {

    private Context ctx;

    public BOMToXSDTransformer(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Transforms the BOM to XSD(s) and returns URIs to the resulting XSD files.
     * 
     * @return
     * @throws GenerationException
     * @throws CoreException
     */
    public List<URI> transformToXSDs(Bom2XsdCachedTransformer transformer,
            IProgressMonitor monitor) throws GenerationException, CoreException {

        IFolder modelFolder = ctx.getOutputProject().getFolder("model");

        // TODO share 'model' constant when rehomed
        // Invoke Gary's BOM to XSD transformer. It produces XSD(s) in the
        // specified folder and returns status objects accumulated during the
        // transformation.
        List<IStatus> statuses =
                transformer.transform(ctx.getBOMFile(),
                        modelFolder.getFullPath(),
                        true,
                        monitor);

        // If the status list includes one or more 'errors',
        // then we have a problem, so filter the list to just errors.
        List<IStatus> errors = new ArrayList<IStatus>();
        if (statuses != null) {
            for (IStatus status : statuses) {
                if (status.getSeverity() == IStatus.ERROR) {
                    errors.add(status);
                }
            }
        }

        // If there were errors, construct and throw an exception
        // containing error details.
        if (!errors.isEmpty()) {
            StringBuffer errorString = new StringBuffer();
            errorString.append(String.format("Source BOM: %s\n", ctx
                    .getBOMFile().getFullPath().toString()));

            for (IStatus error : errors) {
                errorString.append(String.format("\n- %s", error.getMessage()));
            }
            throw new GenerationException(
                    Messages.getString("OawTeneoGenerator_bomXsdTransFail") + errorString.toString()); //$NON-NLS-1$
        }

        // No errors, so search the output folder for XSD URIs
        // to return.
        List<URI> uris = new ArrayList<URI>();
        for (IResource xsdFile : modelFolder.members()) {
            if (xsdFile.getType() == IResource.FILE) {
                // TODO use 'XSD' constant when rehomed
                if (xsdFile.getName().endsWith(".xsd")) {
                    uris.add(URI
                            .createFileURI(xsdFile.getLocation().toString()));
                }
            }
        }

        return uris;
    }

}
