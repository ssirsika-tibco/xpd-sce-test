package com.tibco.xpd.registry.ui.wizard;

import java.io.IOException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.wsdl.CertificateAcceptanceTracker;

/**
 * Holds source and destination location information during a wsdl import
 */
class WsdlImportMapping {

    private static final char[] INVALID_RESOURCE_CHARACTERS;
    static {
        if (Platform.getOS().equals(Platform.OS_WIN32)) {
            // valid names and characters taken from
            // http://msdn.microsoft.com/library/default.asp?url=/library/en-us/
            // fileio/fs/naming_a_file.asp
            INVALID_RESOURCE_CHARACTERS =
                    new char[] { '\\', '/', ':', '*', '?', '"', '<', '>', '|' };
        } else {
            // only front slash and null char are invalid on UNIXes
            // taken from
            // http://www.faqs.org/faqs/unix-faq/faq/part2/section-2.html
            INVALID_RESOURCE_CHARACTERS = new char[] { '/', '\0', };
        }
    }

    private final String wsdlSourceUrl;

    private String destinationFileName;

    /**
     * @param wsdlSourceUrl
     *            this will be needed during the import
     * @param destinationFileName
     *            an initial name for the destination file, will only get
     *            changed if this is the only
     */
    WsdlImportMapping(String wsdlSourceUrl, String destinationFileName) {
        // this.wsdlSourceUrl = wsdlSourceUrl.replaceAll(" ", "%20");

        this.wsdlSourceUrl = wsdlSourceUrl;

        this.destinationFileName = destinationFileName;
        replaceIllegalDestinationNameCharacters();
    }

    private void replaceIllegalDestinationNameCharacters() {
        for (int index = 0; index < INVALID_RESOURCE_CHARACTERS.length; index++) {
            destinationFileName =
                    destinationFileName
                            .replace(INVALID_RESOURCE_CHARACTERS[index], '_');
        }
    }

    String getDestinationFileName() {
        return destinationFileName;
    }

    void setDestinationFileName(String newDestinationFileName) {
        destinationFileName = newDestinationFileName;
    }

    String getSourceUrl() {
        return this.wsdlSourceUrl;
    }

    /**
     * perform the actual import
     * 
     * @param destinationLocationResource
     *            where to place the imported file
     * @param monitor
     */
    void copyTo(IContainer destinationLocationResource,
            CertificateAcceptanceTracker certificateAcceptanceTracker,
            boolean isOverwriteExistingResources, IProgressMonitor monitor)
            throws IOException {
        final IFile destinationIFile =
                destinationLocationResource.getFile(new Path(
                        destinationFileName));
        com.tibco.xpd.wsdl.Activator.getDefault().copyWsdl(wsdlSourceUrl,
                destinationIFile,
                certificateAcceptanceTracker,
                isOverwriteExistingResources,
                monitor);
    }
}
